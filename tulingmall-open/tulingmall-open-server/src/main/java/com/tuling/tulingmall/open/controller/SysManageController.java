package com.tuling.tulingmall.open.controller;

import com.tuling.tulingmall.open.entity.ClientInfo;
import com.tuling.tulingmall.open.service.ClientManageService;
import com.tuling.tulingmall.open.util.ConfigUtils;
import com.tuling.tulingmall.open.util.GsConstants;
import org.apache.log4j.Logger;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/sysManage")
@ApiIgnore
public class SysManageController {
    @Resource
    private ClientManageService clientManageService;

    private final Logger logger = Logger.getLogger(this.getClass());

    @RequestMapping(value = "/newSys", produces = "application/json;charset=UTF-8", method = {RequestMethod.POST})
    public Object newSys(@RequestBody ClientInfo gsmanage) {
        logger.info("SysManageAction.newSys: gsmanage=> " + gsmanage);
        Map<String, Object> res = new HashMap<>();
        int opRes = clientManageService.createSys(gsmanage);
        if (opRes > 0) {
            res.put("code", 1);
            res.put("desc", "外围系统创建成功");
        } else {
            res.put("code", 0);
            res.put("desc", "外围系统创建失败。");
        }
        ConfigUtils.reLoadConfig();
        return res;
    }

    @RequestMapping(value = "/deleteSys", produces = "application/json;charset=UTF-8", method = {RequestMethod.POST})
    public Object deleteSys(String sysid) {
        logger.info("SysManageAction.deleteSys: sysid=> " + sysid);
        return clientManageService.deleteSysById(sysid);
    }

    @RequestMapping(value = "/querySys", produces = "application/json;charset=UTF-8", method = {RequestMethod.POST})
    public Object querySys() {
        logger.info("SysManageAction.querySys: ");
        return clientManageService.queryAllSys();
    }

    @RequestMapping(value = "/passKey", produces = "application/json;charset=UTF-8", method = {RequestMethod.POST})
    public Object passKey(String passKey) {
        Map<String, Object> res = new HashMap<>();
        if (passKey.equals(GsConstants.PASSKEY)) {
            res.put("result", "0");
        } else {
            res.put("result", "1");
        }
        return res;
    }


}
