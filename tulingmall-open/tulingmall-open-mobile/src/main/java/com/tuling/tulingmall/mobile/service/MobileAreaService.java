package com.tuling.tulingmall.mobile.service;

import com.tuling.tulingmall.mobile.utils.HttpUtil;
import org.springframework.stereotype.Service;

/**
 * @author roy
 * @desc
 */
@Service
public class MobileAreaService {
    public String getMobileArea(String mobile) {
        try{
            String tagSearchUrl = "https://www.sogou.com/websearch/phoneAddress.jsp?phoneNumber=${mobile}&cb=handlenumber&isSogoDomain=0";
            String s = HttpUtil.httpGet(tagSearchUrl.replace("${mobile}",mobile));
            return s.substring(s.indexOf("(")+1,s.lastIndexOf(")"));
        }catch (Exception e){
            return "unknown";
        }
    }
}
