package com.tuling.tulingmall.module.mobilearea;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.tuling.tulingmall.module.mobilearea.api.MobileAreaFeignApi;
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
import java.util.HashMap;
import java.util.Map;

public class MobileAreaCallbackV2 implements Runnable{

	private final PhoneInfo phoneInfo;
	private final HisRequestService hisRequestService;
	private final String sysId;
	private final MobileAreaFeignApi mobileAreaService;
	
	private final Logger logger = Logger.getLogger(this.getClass());
	
	public MobileAreaCallbackV2(String sysId, PhoneInfo phoneInfo, HisRequestService hisRequestService, MobileAreaFeignApi mobileAreaService) {
		this.phoneInfo = phoneInfo;
		this.hisRequestService = hisRequestService;
		this.sysId = sysId;
		this.mobileAreaService = mobileAreaService;
	}
	@Override
	public void run() {
		SpringContextUtil.getBean(GsLogConfig.class).addTransLogAppender(logger, phoneInfo.getTransId());
		logger.info("start thread MobileAreaCallbackV2: phoneTagInfo  = " + JSONObject.toJSON(phoneInfo));

		// 获取数据
		String desc;
		try {
			desc = mobileAreaService.getMobileArea(phoneInfo.getMobile());
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
			Map<String, Object> rspInfo = new HashMap<>();
			rspInfo.put("transId", phoneInfo.getTransId());
			rspInfo.put("reqbody", JSON.toJSONString(phoneInfo));
			rspInfo.put("rspBody", JSONObject.toJSONString(serviceResponse));
			rspInfo.put("inTime", new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()));
			rspInfo.put("serviceCode", RequestMsg.MOBILE_AREA_SERVICE);
			hisRequestService.addRequestHis(rspInfo);

			// 发送数据
			ServiceUtils.send(this.sysId, JSONObject.toJSONString(serviceResponse));
		}catch(Exception e) {
			logger.error("查询手机号码归属地出错：",e);
		}
	}

}
