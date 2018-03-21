package com.zhh.controller;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.zhh.configuration.UserSessionMap;
import com.zhh.constants.OrderConstants;
import com.zhh.constants.OrderStatusEnum;
import com.zhh.dto.BaseResp;
import com.zhh.dto.UserSession;
import com.zhh.dto.order.GoodsJson;
import com.zhh.dto.order.OrderReq;
import com.zhh.repository.GoodsRepository;
import com.zhh.repository.OrderGoodsRepository;
import com.zhh.repository.OrderRepository;
import com.zhh.repository.entity.Goods;
import com.zhh.repository.entity.Order;
import com.zhh.repository.entity.OrderGoods;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;

@RequestMapping("/order")
@RestController
public class OrderController {

    @Autowired
    private GoodsRepository goodsRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderGoodsRepository orderGoodsRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @PostMapping("/create")
    @Transactional
    public Object create(@RequestBody OrderReq orderReq) {
        UserSession userSession = UserSessionMap.getUserSession(orderReq.getToken());
        String goodsJsonStr = orderReq.getGoodsJsonStr();
        JavaType javaType = getCollectionType(ArrayList.class, GoodsJson.class);
        try {
            List<GoodsJson> goodsJsonList = objectMapper.readValue(goodsJsonStr,javaType);
            List<OrderGoods> orderGoodsList = new ArrayList<>(goodsJsonList.size());
            Long amount = 0L;
            Long amountGoods = 0L;
            Long amountLogistics = 0L;
            Integer goodsNumber = 0;
            for (GoodsJson goodsJson : goodsJsonList){
                Long goodsid = goodsJson.getGoodsId();
                Goods goods = goodsRepository.findById(goodsid).get();
                //是否上架
                if(goods.getState().equals(1)){
                    return null;
                }

                //库存 todo

                OrderGoods orderGoods = new OrderGoods();
                orderGoods.setUserId(userSession.getUser().getId());
                orderGoods.setGoodsId(goodsid);
                //收货人
                orderGoods.setAddress(orderReq.getAddress());
                orderGoods.setLinkman(orderReq.getLinkMan());
                orderGoods.setMobile(orderReq.getMobile());
                orderGoods.setCode(orderReq.getCode());
                //金额
                Long goodsAmount;
                Long goodsAmountGoods = goods.getPrice()*goodsJson.getNumber();
                Long goodsAmountLogistics = goods.getLogistics()*goodsJson.getNumber();
                goodsAmount = goodsAmountGoods+goodsAmountLogistics;
                amountGoods += goodsAmountGoods;
                amountLogistics += goodsAmountLogistics;
                goodsNumber += goodsJson.getNumber();
                orderGoods.setAmount(goodsAmount);
                orderGoods.setAmountGoods(goodsAmountGoods);
                orderGoods.setAmountLogistics(goodsAmountLogistics);
                orderGoods.setGoodsNumber(goodsJson.getNumber());
                orderGoods.setStatus(OrderConstants.ORDER_STATUS_START);
                orderGoods.setRemark(orderReq.getRemark());
                orderGoods.setAddBy(userSession.getUser().getNickname());
                orderGoods.setAddTime(LocalDateTime.now());
                orderGoodsList.add(orderGoods);
            }
            amount = amountGoods + amountLogistics;
            Order order = new Order();
            order.setUserId(userSession.getUser().getId());
            //收货人
            order.setAddress(orderReq.getAddress());
            order.setLinkman(orderReq.getLinkMan());
            order.setMobile(orderReq.getMobile());
            order.setCode(orderReq.getCode());

            order.setAmount(amount);
            order.setAmountGoods(amountGoods);
            order.setAmountLogistics(amountLogistics);
            order.setGoodsNumber(goodsNumber);

            order.setStatus(OrderConstants.ORDER_STATUS_START);
            order.setRemark(orderReq.getRemark());
            order.setAddBy(userSession.getUser().getNickname());
            order.setAddTime(LocalDateTime.now());

            if(orderReq.getCalculate()==null || !orderReq.getCalculate()) {
                order = orderRepository.save(order);
                for (OrderGoods orderGoods : orderGoodsList) {
                    orderGoods.setOrderId(order.getId());
                    orderGoodsRepository.save(orderGoods);
                }
            }
            Map map = new HashMap(4);
            map.put("isNeedLogistics",amountLogistics>0?1:0);
            map.put("amountTotle", BigDecimal.valueOf(amount,2).toString());
            map.put("amountLogistics",BigDecimal.valueOf(amountLogistics,2).toString());
            map.put("amountGoods",BigDecimal.valueOf(amountGoods,2).toString());
            return BaseResp.SUCCESSRESP.setData(map);
        } catch (IOException e) {
            e.printStackTrace();
            return BaseResp.ERRORRESP;
        }
    }

    @GetMapping("/list")
    @Transactional
    public Object list(@RequestParam("token") String token, @RequestParam("status") Integer status) {
        UserSession userSession = UserSessionMap.getUserSession(token);
        List<Order> orderList;
        if (status==null || status<0){
            orderList = orderRepository.findByUserId(userSession.getUser().getId(),Sort.by(Sort.Direction.DESC, "add_time"));
        } else {
            orderList = orderRepository.findByUserIdAndStatus(userSession.getUser().getId(),status,Sort.by(Sort.Direction.DESC, "add_time"));
        }
        Map map = new HashMap(4);
        map.put("orderList",orderList);
        return BaseResp.SUCCESSRESP.setData(map);
    }

    @GetMapping("/close")
    @Transactional
    public Object close(@RequestParam("token") String token, @RequestParam("orderId") Long orderId) {
        UserSession userSession = UserSessionMap.getUserSession(token);
        Order order = orderRepository.findById(orderId).get();
        if(!order.getUserId().equals(userSession.getUser().getId())){
            return BaseResp.ERRORRESP;
        }
        if(order.getStatus().equals(OrderStatusEnum.START.getStatus())){
            orderRepository.updateStatusById(OrderStatusEnum.CANCEL.getStatus(),order.getId(),
                    userSession.getUser().getNickname(),LocalDateTime.now());
            return BaseResp.SUCCESSRESP;
        }
        return BaseResp.ERRORRESP;
    }

    @GetMapping("/detail")
    @Transactional
    public Object detail(@RequestParam("token") String token, @RequestParam("id") Long id) {
        UserSession userSession = UserSessionMap.getUserSession(token);
        Order order = orderRepository.findById(id).get();
        if(!order.getUserId().equals(userSession.getUser().getId())){
            return BaseResp.ERRORRESP;
        }
        return BaseResp.ERRORRESP.setData(order);
    }

    @GetMapping("/delivery")
    @Transactional
    public Object delivery(@RequestParam("token") String token, @RequestParam("orderId") Long orderId) {
        UserSession userSession = UserSessionMap.getUserSession(token);
        Order order = orderRepository.findById(orderId).get();
        if(!order.getUserId().equals(userSession.getUser().getId())){
            return BaseResp.ERRORRESP;
        }
        if(order.getStatus().equals(OrderStatusEnum.SENDED.getStatus())){
            orderRepository.updateStatusById(OrderStatusEnum.RECEIVED.getStatus(),order.getId(),
                    userSession.getUser().getNickname(),LocalDateTime.now());
            return BaseResp.SUCCESSRESP;
        }
        return BaseResp.ERRORRESP.setData(order);
    }

    @GetMapping("/statistics")
    @Transactional
    public Object statistics(@RequestParam("token") String token) {
        Map map = new HashMap(5);
        map.put("count_id_no_pay",0);
        map.put("count_id_no_transfer",0);
        map.put("count_id_no_confirm",0);
        map.put("count_id_no_reputation",0);
        map.put("count_id_success",0);
        return BaseResp.SUCCESSRESP.setData(map);
    }

    /**
     * 获取泛型的Collection Type
     * @param collectionClass 泛型的Collection
     * @param elementClasses 元素类
     * @return JavaType Java类型
     */
    private JavaType getCollectionType(Class<?> collectionClass, Class<?>... elementClasses) {
        return objectMapper.getTypeFactory().constructParametricType(collectionClass, elementClasses);
    }
}
