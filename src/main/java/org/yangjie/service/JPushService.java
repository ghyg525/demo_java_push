package org.yangjie.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.yangjie.config.JPushConfig;
import org.yangjie.entity.PushBean;

import cn.jpush.api.common.resp.APIConnectionException;
import cn.jpush.api.common.resp.APIRequestException;
import cn.jpush.api.push.PushResult;
import cn.jpush.api.push.model.Options;
import cn.jpush.api.push.model.Platform;
import cn.jpush.api.push.model.PushPayload;
import cn.jpush.api.push.model.audience.Audience;
import cn.jpush.api.push.model.notification.Notification;

/**
 * 极光推送
 * 封装第三方api相关
 * @author YangJie [2016年6月17日 下午3:47:55]
 */
@Service
public class JPushService {
	
	private final Logger logger = LoggerFactory.getLogger(JPushService.class);
	
	@Autowired
	private JPushConfig jPushConfig;
	
	
	/**
	 * 广播 (所有平台，所有设备, 不支持附加信息)
	 * @author YangJie [2016年6月17日 下午4:12:08]
	 * @param pushBean 推送内容
	 * @return
	 */
	public boolean pushAll(PushBean pushBean){
		return sendPush(PushPayload.newBuilder()
	            .setPlatform(Platform.all())
	            .setAudience(Audience.all())
	            .setNotification(Notification.alert(pushBean.getAlert()))
	            .setOptions(Options.newBuilder().setApnsProduction(jPushConfig.getApns()).build())
	            .build());
	}
	
	/**
	 * ios广播
	 * @author YangJie [2016年6月17日 下午3:59:21]
	 * @param pushBean 推送内容
	 * @return
	 */
	public boolean pushIos(PushBean pushBean){
		return sendPush(PushPayload.newBuilder()
				.setPlatform(Platform.ios())
				.setAudience(Audience.all())
				.setNotification(Notification.ios(pushBean.getAlert(), pushBean.getExtras()))
				.setOptions(Options.newBuilder().setApnsProduction(jPushConfig.getApns()).build())
                .build());
	}
	
	/**
	 * ios通过registid推送 (一次推送最多 1000 个)
	 * @author YangJie [2016年6月17日 下午3:59:21]
	 * @param pushBean 推送内容
	 * @param registids 推送id
	 * @return
	 */
	public boolean pushIos(PushBean pushBean, String ... registids){
		return sendPush(PushPayload.newBuilder()
				.setPlatform(Platform.ios())
				.setAudience(Audience.registrationId(registids))
				.setNotification(Notification.ios(pushBean.getAlert(), pushBean.getExtras()))
				.setOptions(Options.newBuilder().setApnsProduction(jPushConfig.getApns()).build())
				.build());
	}
	
	
	/**
	 * android广播
	 * @author YangJie [2016年6月17日 下午3:59:21]
	 * @param pushBean 推送内容
	 * @return
	 */
	public boolean pushAndroid(PushBean pushBean){
		return sendPush(PushPayload.newBuilder()
                .setPlatform(Platform.android())
                .setAudience(Audience.all())
                .setNotification(Notification.android(pushBean.getAlert(), pushBean.getTitle(), pushBean.getExtras()))
                .setOptions(Options.newBuilder().setApnsProduction(jPushConfig.getApns()).build())
                .build());
	}
	
	/**
	 * android通过registid推送 (一次推送最多 1000 个)
	 * @author YangJie [2016年6月17日 下午3:59:21]
	 * @param pushBean 推送内容
	 * @param registids 推送id
	 * @return
	 */
	public boolean pushAndroid(PushBean pushBean, String ... registids){
		return sendPush(PushPayload.newBuilder()
                .setPlatform(Platform.android())
                .setAudience(Audience.registrationId(registids))
                .setNotification(Notification.android(pushBean.getAlert(), pushBean.getTitle(), pushBean.getExtras()))
                .setOptions(Options.newBuilder().setApnsProduction(jPushConfig.getApns()).build())
                .build());
	}

	/**
	 * 调用api推送
	 * @author YangJie [2016年6月17日 下午4:19:03]
	 * @param pushPayload 推送实体
	 * @return
	 */
	private boolean sendPush(PushPayload pushPayload){
		logger.info("发送极光推送请求: {}", pushPayload);
		PushResult result = null;
		try{
			result = jPushConfig.getJPushClient().sendPush(pushPayload);
		} catch (APIConnectionException e) {
			logger.error("极光推送连接异常: ", e);
		} catch (APIRequestException e) {
			logger.error("极光推送请求异常: ", e);
		}
		if (result!=null && result.isResultOK()) {
			logger.info("极光推送请求成功: {}", result);
			return true;
		}else {
			logger.info("极光推送请求失败: {}", result);
			return false;
		}
	}
	
}