package com.zhh.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.zhh.configuration.UserSessionMap;
import com.zhh.constants.AppConstants;
import com.zhh.constants.OrderStatusEnum;
import com.zhh.dto.BaseResp;
import com.zhh.configuration.UserSession;
import com.zhh.dto.wx.Pay;
import com.zhh.dto.wx.PayNotify;
import com.zhh.dto.wx.PayNotifyResp;
import com.zhh.dto.wx.PayResp;
import com.zhh.http.OkHttpService;
import com.zhh.repository.OrderRepository;
import com.zhh.repository.ParameterRepository;
import com.zhh.repository.PayLogRepository;
import com.zhh.repository.entity.Order;
import com.zhh.repository.entity.PayLog;
import com.zhh.utils.IpUtil;
import com.zhh.utils.UUIDUtil;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.time.LocalDateTime;
import java.util.*;

@RequestMapping("/pay")
@RestController
public class PayController {

    @Autowired
    private ParameterRepository parameterRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private PayLogRepository payLogRepository;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    private OkHttpService okHttpService;

    @GetMapping("/wxapp/get-pay-data")
    @Transactional
    public Object getPayData(@RequestParam("token") String token,
                             @RequestParam("orderId") Long orderId,
                             @RequestParam("remark") String remark,
                             HttpServletRequest request) {
        UserSession userSession = UserSessionMap.getUserSession(token);
        Order order = orderRepository.findById(orderId).get();
        if(!userSession.getUser().getId().equals(order.getUserId())){
            return BaseResp.ERRORRESP;
        }
        if(!order.getStatus().equals(OrderStatusEnum.START.getStatus())){
            return BaseResp.ERRORRESP;
        }
        List<PayLog> payLogList = payLogRepository.findByOrderIdAndPayStatus(order.getId(),0, Sort.by(Sort.Direction.DESC,"add_time"));
        PayLog payLog = null;
        if (payLogList!=null && payLogList.isEmpty()){
            payLog = payLogList.get(0);
            if (payLog.getAddTime().plusHours(2).isBefore(LocalDateTime.now())){
                payLog = null;
            }
        }
        String appId = parameterRepository.getByParamNameIs(AppConstants.APPID).getParamValue();
        String mch_key = parameterRepository.getByParamNameIs(AppConstants.MCH_KEY).getParamValue();
        if (payLog ==null){
            String mch_id = parameterRepository.getByParamNameIs(AppConstants.MCH_ID).getParamValue();
            String notify_url = parameterRepository.getByParamNameIs(AppConstants.NOTIFY_URL).getParamValue();

            Pay pay = new Pay();
            pay.setAppid(appId);
            pay.setMch_id(mch_id);
            pay.setBody(getPayBody(order));
            pay.setNonce_str(UUIDUtil.genUUID());
            pay.setOut_trade_no(order.getId().toString());
            pay.setTotal_fee(order.getAmount().toString());
            pay.setSpbill_create_ip(IpUtil.getIpAddr(request));
            pay.setNotify_url(notify_url);
            pay.setOpenid(userSession.getUser().getOpenId());
            pay.setSign(genSign(pay,mch_key));
            String payXml = getXml(pay);
            String payRespStr = okHttpService.post(AppConstants.PAY_URL,OkHttpService.XML,payXml);
            PayResp payResp = xmlToPay(payRespStr);
            payLog = new PayLog();
            payLog.setOrderId(order.getId());
            payLog.setPayStatus(1);
            payLog.setTotalFee(order.getAmount());
            payLog.setReqBody(payXml);
            payLog.setRespBody(payRespStr);
            payLog.setAddBy(userSession.getUser().getNickname());
            payLog.setAddTime(LocalDateTime.now());
            if(isSuccessPayResp(payResp)){
                payLog.setPayStatus(0);
                payLog.setPrepayId(payResp.getPrepay_id());
            }
            payLog = payLogRepository.save(payLog);
        }
        if (payLog ==null || !payLog.getPayStatus().equals(0)){
            return BaseResp.ERRORRESP;
        }
        Map<String,Object> map = new TreeMap<>();
        map.put("appId",appId);
        map.put("nonceStr",UUIDUtil.genUUID());
        map.put("package","prepay_id="+payLog.getPrepayId());
        map.put("signType","MD5");
        map.put("timeStamp",System.currentTimeMillis());
        map.put("paySign",genSign(map,mch_key));
        map.remove("appId");
        return BaseResp.SUCCESSRESP.setData(map);
    }

    @PostMapping(value ="/wxapp/notify", consumes = { "text/xml" },
            produces = { "application/xml" })
    public Object notify(@RequestBody PayNotify payNotify) {
        if(!checkSign(payNotify)){
            return BaseResp.ERRORRESP;
        }
        Order order = orderRepository.findById(new Long(payNotify.getOut_trade_no())).get();
        List<PayLog> payLogList = payLogRepository.findByOrderIdAndPayStatus(order.getId(),0, Sort.by(Sort.Direction.DESC,"add_time"));
        if(isSuccessPayNotify(payNotify)){
            if(order.getAmount().equals(payNotify.getTotal_fee())){
                payLogRepository.updatePayStatusById(2,payLogList.get(0).getId(),"sys",LocalDateTime.now(),getXml(payNotify));
                orderRepository.updateStatusById(OrderStatusEnum.PAYED.getStatus(),order.getId(),"sys",LocalDateTime.now());
            }
        } else {
            payLogRepository.updatePayStatusById(1,payLogList.get(0).getId(),"sys",LocalDateTime.now(),getXml(payNotify));
        }
        return new PayNotifyResp(AppConstants.SUCCESS,AppConstants.OK);
    }

    private String genSign(Map pay,String mch_key){
        StringBuilder stringBuffer = new StringBuilder("");
        TreeMap map = new TreeMap(pay);
        for (Object key : map.keySet()) {
            Object value = map.get(key);
            if(!StringUtils.isEmpty(value)){
                stringBuffer.append(key).append("=").append(value).append("&");
            }
        }
        stringBuffer.append("key").append("=").append(mch_key);
        return DigestUtils.md5Hex(stringBuffer.toString()).toUpperCase();
    }

    private String genSign(Pay pay,String mch_key){
        try {
            return genSign(BeanUtils.describe(pay),mch_key);
        } catch (IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
            e.printStackTrace();
        }
        return "";
    }

    private String genSign(PayNotify pay,String mch_key){
        try {
            return genSign(BeanUtils.describe(pay),mch_key);
        } catch (IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
            e.printStackTrace();
        }
        return "";
    }

    private String getPayBody(Order order){
        return "小程序商城：微信支付";
    }

    private String getXml(Object pay){
        XmlMapper xmlMapper = new XmlMapper();
        try {
            return xmlMapper.writeValueAsString(pay);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return "";
    }


    private PayResp xmlToPay(String pay){
        XmlMapper xmlMapper = new XmlMapper();
        try {
            return xmlMapper.readValue(pay,PayResp.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private boolean isSuccessPayResp(PayResp pay){
        if (pay == null){
            return false;
        }
        if (!AppConstants.SUCCESS.equals(pay.getResult_code())){
            return false;
        }
        if (!AppConstants.SUCCESS.equals(pay.getReturn_code())){
            return false;
        } else {
            return true;
        }
    }

    private boolean checkSign(PayNotify payNotify){
        try {
            PayNotify payNotifyCopy = (PayNotify) BeanUtils.cloneBean(payNotify);
            String sign = payNotifyCopy.getSign();
            payNotifyCopy.setSign(null);
            if(!sign.equals(genSign(payNotifyCopy,""))){
                return false;
            }
            return true;
        } catch (IllegalAccessException | InstantiationException | InvocationTargetException | NoSuchMethodException e) {
            e.printStackTrace();
        }
        return false;
    }

    private boolean isSuccessPayNotify(PayNotify payNotify){
        try {
            PayNotify payNotifyCopy = (PayNotify) BeanUtils.cloneBean(payNotify);
            if (AppConstants.SUCCESS.equals(payNotifyCopy.getResult_code())
                    && AppConstants.SUCCESS.equals(payNotifyCopy.getReturn_code())){
                return true;
            }
        } catch (IllegalAccessException | InstantiationException | InvocationTargetException | NoSuchMethodException e) {
            e.printStackTrace();
        }
        return false;
    }
}
