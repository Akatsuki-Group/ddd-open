package com.tuling.tulingmall.open.util;

import com.tuling.tulingmall.open.entity.ClientInfo;
import com.tuling.tulingmall.open.service.ClientManageService;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 配置工具类
 *
 * @author
 */
@Component
public class ConfigUtils {
    /**
     * 接口服务管理集合
     */
    private static Map<String, ClientInfo> clientMap = new HashMap<String, ClientInfo>();

    @Resource
    private ClientManageService clientManageService;

    private static ClientManageService clientManageServiceStatic;

    /**
     * 初始化配置
     */
    @PostConstruct
    public void init() {
        clientManageServiceStatic = clientManageService;
        List<ClientInfo> clientInfos = clientManageService.queryAllSys();
        if (clientInfos != null && !clientInfos.isEmpty()) {
            for (ClientInfo clientInfo : clientInfos) {
                clientMap.put(clientInfo.getSysId(), clientInfo);
            }
        }
    }

    /**
     * 重新加载配置
     */
    public static void reLoadConfig() {
        clientMap.clear();
        List<ClientInfo> clientInfos = clientManageServiceStatic.queryAllSys();
        if (clientInfos != null && !clientInfos.isEmpty()) {
            for (ClientInfo clientInfo : clientInfos) {
                clientMap.put(clientInfo.getSysId(), clientInfo);
            }
        }
    }

    /**
     * 获取接口服务配置
     *
     * @param sysId 系统标识
     * @return
     */
    public static ClientInfo getClientInfo(String sysId) {
        return clientMap.get(sysId);
    }

    /**
     * 检查系统标识是否有效
     *
     * @param sysId 系统标识
     * @return 有效返回true, 无效返回false
     */
    public static boolean checkSysId(String sysId) {
        return clientMap.containsKey(sysId);
    }
}
