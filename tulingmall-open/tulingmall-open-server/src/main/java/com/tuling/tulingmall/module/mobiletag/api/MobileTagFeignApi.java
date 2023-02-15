package com.tuling.tulingmall.module.mobiletag.api;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author roy
 * @desc
 */
@FeignClient(name = "tulingmall-open-mobile", path = "/mobile")
public interface MobileTagFeignApi {
    @RequestMapping(value = "/mobiletag", method = RequestMethod.POST)
    String getMobileTag(@RequestParam("mobile") String mobile) throws Exception;
}
