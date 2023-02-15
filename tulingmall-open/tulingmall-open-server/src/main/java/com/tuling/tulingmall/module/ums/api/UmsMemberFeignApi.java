package com.tuling.tulingmall.module.ums.api;

import com.tuling.tulingmall.common.CommonResult;
import com.tuling.tulingmall.module.ums.UmsMember;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
* @desc: 类的描述:远程调用 会员中心获取具体收获地址
*/
@FeignClient(name = "tulingmall-member",path = "/member")
public interface UmsMemberFeignApi {

    @RequestMapping(value = "/center/loadUmsMember", method = RequestMethod.GET)
    @ResponseBody
    CommonResult<UmsMember> loadUserByUsername(String username);
}
