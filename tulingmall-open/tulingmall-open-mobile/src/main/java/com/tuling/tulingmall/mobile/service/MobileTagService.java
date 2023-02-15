package com.tuling.tulingmall.mobile.service;

import com.tuling.tulingmall.mobile.utils.CodeUtil;
import com.tuling.tulingmall.mobile.utils.HttpUtil;
import org.springframework.stereotype.Service;

/**
 * @author roy
 * @desc
 */
@Service
public class MobileTagService {

    public String getMobileTag(String mobile) {
        try{
            String tagSearchUrl = "https://www.sogou.com/reventondc/inner/vrapi?number=${mobile}&callback=show&isSogoDomain=0&objid=10001001";
            String s1 = HttpUtil.httpGet(tagSearchUrl.replace("${mobile}", mobile));
            return CodeUtil.decodeUnicode(s1.substring(s1.indexOf(":") + 1, s1.indexOf(",")).replace("\"", "").replace("\\\\", "\\"));
        }catch (Exception e){
            return "unknown";
        }

    }
}
