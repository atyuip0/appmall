package test;

import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.zhh.dto.wx.Pay;
import com.zhh.utils.IpUtil;
import com.zhh.utils.UUIDUtil;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.codec.digest.DigestUtils;
import org.junit.Test;
import org.springframework.util.StringUtils;

import java.lang.reflect.InvocationTargetException;
import java.util.Iterator;
import java.util.TreeMap;

public class StringTest {

    @Test
    public void name() throws Exception {

        String url="https://api.weixin.qq.com/sns/oauth2/access_token?appid=%s&secret=%s&js_code=%s&grant_type=%s";
        System.out.println(String.format(url,"11","112","113","114"));
    }

    @Test
    public void md5() throws Exception {
        String s = "appid=wxd930ea5d5a258f4f&body=test&device_info=1000&mch_id=10000100&nonce_str=ibuaiVcKdpRxkhJA";
        s+="&key=192006250b4c09247ec02edce69f6a2d";
        new String(DigestUtils.md5(s));

    }

    @Test
    public void xml() throws Exception {
        Pay pay = new Pay();
        pay.setAppid("111");
        pay.setMch_id("222");
        pay.setBody("3333");
        pay.setNonce_str(UUIDUtil.genUUID());
        pay.setOut_trade_no("22313213");
        pay.setTotal_fee("1111");
        pay.setSpbill_create_ip("112.112.22");
        pay.setNotify_url("33122222");
        pay.setOpenid("321321ewqewqewq");
        pay.setSign(genSign(pay,"3321ddqq"));
        XmlMapper xmlMapper = new XmlMapper();
        System.out.println(xmlMapper.writeValueAsString(pay));
    }

    private String genSign(Pay pay,String mch_key){
        StringBuffer stringBuffer = new StringBuffer("");
        try {
            TreeMap map = new TreeMap(BeanUtils.describe(pay));
            map.remove("class");
            Iterator<String> iterator = map.keySet().iterator();
            while (iterator.hasNext()){
                String key = iterator.next();
                Object value = map.get(key);
                if(!StringUtils.isEmpty(value)){
                    stringBuffer.append(key).append("=").append(value).append("&");
                }
            }
            stringBuffer.append("key").append("=").append(mch_key);
            return DigestUtils.md5Hex(stringBuffer.toString()).toUpperCase();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
        return "";
    }
}
