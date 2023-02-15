package com.tuling.tulingmall.mobile.controller;

import com.tuling.tulingmall.mobile.service.MobileAreaService;
import com.tuling.tulingmall.mobile.service.MobileTagService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

/**
 * @author roy
 * @desc
 */
@Controller
@RequestMapping("/mobile")
public class MobileInfoController {

    @Resource
    private MobileTagService mobileTagService;

    @Resource
    private MobileAreaService mobileAreaService;

    @RequestMapping(value = "/mobilearea",method = RequestMethod.POST)
    @ResponseBody
    public String getMobileArea(@RequestParam String mobile) throws Exception {
        return mobileAreaService.getMobileArea(mobile);
    }

    @RequestMapping(value = "/mobiletag",method = RequestMethod.POST)
    @ResponseBody
    public String getMobileTag(@RequestParam String mobile) throws Exception {
        return mobileTagService.getMobileTag(mobile);
    }
}
