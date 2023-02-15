package com.tuling.tulingmall.open.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.tuling.tulingmall.open.entity.GsHisInfo;
import com.tuling.tulingmall.open.entity.HisRequest;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

/**
 * @author 楼兰
 * @desc
 */
public interface HisRequestMapper {

    @Select("select 1")
    String connectCheck();

    int add(Map<String, Object> paras);

    int addV2(GsHisInfo ftoulHisInfo);

    List<Map<String, Object>> getAll(Map<String, Object> paras);

    Map<String, Object> getById(Map<String, Object> paras);

    Map<String, Object> getSingleById(Map<String, Object> paras);

    int updateRespByTransId(Map<String, Object> paras);

    int updateRespByTransIdV2(GsHisInfo ftoulHisInfo);

    int updateRespByPK(Map<String, Object> paras);

    int updateRespByPKV2(GsHisInfo ftoulHisInfo);

    int deleteByPK(Map<String, Object> rspInfo);
}
