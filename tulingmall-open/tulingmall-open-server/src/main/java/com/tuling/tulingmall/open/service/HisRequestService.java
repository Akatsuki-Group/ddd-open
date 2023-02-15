package com.tuling.tulingmall.open.service;

import com.tuling.tulingmall.open.entity.GsHisInfo;
import com.tuling.tulingmall.open.entity.HisRequest;
import com.tuling.tulingmall.open.mapper.HisRequestMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author 楼兰
 * @desc
 */
@Service
public class HisRequestService {

    @Resource
    private HisRequestMapper hisRequestMapper;

    /**
     * mysql连接默认如果闲置超过8小时，连接会断掉。已经将数据库连接配置为断后重连。 但是断掉后第一次访问还是会报错。所以需要有这个方法调用一下数据库连接。
     */
    public void connectCheck() {
        try {
            hisRequestMapper.connectCheck();
        } catch (Exception e) {

        }
    }

    public String getHisResp(String transId, String serviceCode) {
        String res = null;
        Map<String, Object> paras = new HashMap<>(4);
        paras.put("transId", transId);
        paras.put("serviceCode", serviceCode);
        Map<String, Object> hisData = hisRequestMapper.getSingleById(paras);
        // 查询历史记录，以transId为唯一标识，查过的就给历史记录，没查过的就去获取新记录。
        if (null != hisData && hisData.size() > 0) {
            // 返回历史记录
            res = hisData.containsKey("rspBody") ? hisData.get("rspBody").toString() : null;
        }
        return res;
    }

    public HisRequestMapper gethisRequestMapper() {
        return hisRequestMapper;
    }

    public int addRequestHis(Map<String, Object> paras) {
        return hisRequestMapper.add(paras);
    }

    public int addRequestHis(GsHisInfo gsHisInfo) {
        return hisRequestMapper.addV2(gsHisInfo);
    }

    public List<Map<String, Object>> getAll(Map<String, Object> paras) {
        return hisRequestMapper.getAll(paras);
    }

    public Map<String, Object> getRequestHis(Map<String, Object> paras) {
        return hisRequestMapper.getSingleById(paras);
    }

    public int updateResp(Map<String, Object> paras) {
        return hisRequestMapper.updateRespByTransId(paras);
    }

    public int updateRespByPK(Map<String, Object> paras) {
        return hisRequestMapper.updateRespByPK(paras);
    }

    public int updateRespByTransId(Map<String, Object> paras) {
        return hisRequestMapper.updateRespByTransId(paras);
    }

    public int deleteByPK(Map<String, Object> rspInfo) {
        return hisRequestMapper.deleteByPK(rspInfo);
    }

}
