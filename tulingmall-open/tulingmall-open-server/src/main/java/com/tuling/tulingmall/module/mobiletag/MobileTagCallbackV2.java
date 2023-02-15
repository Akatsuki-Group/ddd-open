package com.tuling.tulingmall.module.mobiletag;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Maps;
import com.tuling.tulingmall.module.mobiletag.api.MobileTagFeignApi;
import com.tuling.tulingmall.module.mobilearea.PhoneInfo;
import com.tuling.tulingmall.open.config.GsLogConfig;
import com.tuling.tulingmall.open.entity.RequestMsg;
import com.tuling.tulingmall.open.entity.ServiceResponseHeader;
import com.tuling.tulingmall.open.entity.ServiceResponseMsg;
import com.tuling.tulingmall.open.service.HisRequestService;
import com.tuling.tulingmall.open.util.ServiceUtils;
import com.tuling.tulingmall.open.util.SpringContextUtil;
import org.apache.log4j.Logger;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

/**
 * 手机标注回调 根据sysId进行推送
 * 
 */
public class MobileTagCallbackV2 implements Runnable {

	private final PhoneInfo phoneInfo;
	private final HisRequestService hisRequestService;
	private final String sysId;
	private final MobileTagFeignApi mobileTagService;

	private final Logger logger = Logger.getLogger(this.getClass());

	public MobileTagCallbackV2(String sysId, PhoneInfo phoneInfo, HisRequestService hisRequestService, MobileTagFeignApi mobileTagService) {
		this.phoneInfo = phoneInfo;
		this.hisRequestService = hisRequestService;
		this.sysId = sysId;
		this.mobileTagService = mobileTagService;
	}

	@Override
	public void run() {
		SpringContextUtil.getBean(GsLogConfig.class).addTransLogAppender(logger, phoneInfo.getTransId());
		logger.info("start thread: phoneInfo  = " + JSONObject.toJSON(phoneInfo));

		// 获取数据
		String desc;
		try {
			desc = mobileTagService.getMobileTag(phoneInfo.getMobile());
			logger.debug("desc = " + desc);
			// 整理返回数据
			ServiceResponseMsg serviceResponse = new ServiceResponseMsg();
			
			ServiceResponseHeader serviceResponseHeader = new ServiceResponseHeader();
			serviceResponseHeader.setTransId(phoneInfo.getTransId());
			serviceResponseHeader.setIntime(new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()));
			serviceResponseHeader.setServiceCode(RequestMsg.MOBILE_MARK_SERVICE);
			serviceResponse.setHeader(serviceResponseHeader);
			
			JSONObject rspBody = new JSONObject();
			rspBody.put("mobile", phoneInfo.getMobile());
			rspBody.put("desc", desc);
			serviceResponse.setBody(rspBody);
			
			// 本地保存服务记录
			Map<String, Object> rspInfo = Maps.newHashMap();
			rspInfo.put("transId", phoneInfo.getTransId());
			rspInfo.put("reqbody", JSON.toJSONString(phoneInfo));
			rspInfo.put("rspBody", JSONObject.toJSONString(serviceResponse));
			rspInfo.put("inTime", new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()));
			rspInfo.put("serviceCode", RequestMsg.MOBILE_MARK_SERVICE);
			hisRequestService.addRequestHis(rspInfo);

			// 发送数据
			ServiceUtils.sendAsyn(this.sysId, JSONObject.toJSONString(serviceResponse));
		}catch(Exception e) {
			logger.error("查询手机号码标注出错：",e);
		}
	}
}
