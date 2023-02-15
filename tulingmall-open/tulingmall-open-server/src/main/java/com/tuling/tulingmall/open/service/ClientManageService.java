package com.tuling.tulingmall.open.service;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.tuling.tulingmall.open.entity.ClientInfo;
import com.tuling.tulingmall.open.mapper.ClientInfoMapper;
import com.tuling.tulingmall.open.util.RsaUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author 楼兰
 * @desc
 */
@Service
public class ClientManageService {

    @Resource
    private ClientInfoMapper clientInfoMapper;

    public int createSys(ClientInfo client) {
        if(StringUtils.isEmpty(client.getPrivatekey()) || StringUtils.isEmpty(client.getPublickey())){
            JSONObject newRsaKey = RsaUtils.generateKeyPairForJava();
            client.setPublickey(newRsaKey.getString("publicKey"));
            client.setPrivatekey(newRsaKey.getString("privateKey"));
        }
        int res = 0 ;
        try{
            res = clientInfoMapper.insert(client);
        }catch(Exception e){
            res = clientInfoMapper.updateById(client);
        }
        return res;
    }

    public List<ClientInfo> queryAllSys() {
        List<ClientInfo> res;
        QueryWrapper<ClientInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByAsc("sysId");
        res = clientInfoMapper.selectList(queryWrapper);
        return res;
    }

    public int deleteSysById(String sysid) {
        return clientInfoMapper.deleteById(sysid);
    }
}
