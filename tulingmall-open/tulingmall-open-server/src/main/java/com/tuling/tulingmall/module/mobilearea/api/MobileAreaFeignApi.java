package com.tuling.tulingmall.module.mobilearea.api;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author roy
 * @desc
 */
@FeignClient(name = "tulingmall-open-mobile", path = "/mobile")
public interface MobileAreaFeignApi {

    @RequestMapping(value = "/mobilearea", method = RequestMethod.POST)
    String getMobileArea(@RequestParam("mobile") String mobile) throws Exception;
}
