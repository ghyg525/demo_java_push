package org.yangjie.config;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import cn.jpush.api.JPushClient;

@Configuration
@ConfigurationProperties
public class JPushConfig {
	
	@Value("${push.appkey}")
	private String appkey;
	@Value("${push.secret}")
	private String secret;
	@Value("${push.apns}")
	private boolean apns;
	
	private JPushClient jPushClient;
	

	/**
	 * 推送客户端
	 * @return
	 */
	@PostConstruct
	public void initJPushClient() {
		jPushClient = new JPushClient(secret, appkey);
	}
	
	/**
	 * 获取推送客户端
	 * @return
	 */
	public JPushClient getJPushClient() {
		return jPushClient;
	}
	
	/**
	 * 区分开发和线上环境
	 * @return
	 */
	public boolean getApns() {
		return apns;
	}
	
}
