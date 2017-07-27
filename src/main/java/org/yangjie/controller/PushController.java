package org.yangjie.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.yangjie.entity.PushBean;
import org.yangjie.service.PushService;

@RestController
@RequestMapping("/push")
public class PushController {
	
	@Autowired
	private PushService pushService;
	
	/**
	 * 推送全部(包括ios和android)
	 * @param pushBean
	 * @return
	 */
	@RequestMapping(value="/all", method=RequestMethod.POST)
	public boolean pushAll(@RequestBody PushBean pushBean) {
		return pushService.pushAll(pushBean);
	}

	/**
	 * 推送全部ios
	 * @param pushBean
	 * @return
	 */
	@RequestMapping(value="/ios/all", method=RequestMethod.POST)
	public boolean pushIos(PushBean pushBean){
		return pushService.pushIos(pushBean);
	}
	
	/**
	 * 推送指定ios
	 * @param pushBean
	 * @return
	 */
	@RequestMapping(value="/ios", method=RequestMethod.POST)
	public boolean pushIos(PushBean pushBean, String[] registerids){
		return pushService.pushIos(pushBean, registerids);
	}
	
	/**
	 * 推送全部android
	 * @param pushBean
	 * @return
	 */
	@RequestMapping(value="/android/all", method=RequestMethod.POST)
	public boolean pushAndroid(PushBean pushBean){
		return pushService.pushAndroid(pushBean);
	}
	
	/**
	 * 推送指定android
	 * @param pushBean
	 * @return
	 */
	@RequestMapping(value="/android", method=RequestMethod.POST)
	public boolean pushAndroid(PushBean pushBean, String[] registerids){
		return pushService.pushAndroid(pushBean, registerids);
	}

}
