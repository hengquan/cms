package com.hj.wxmp.mobile.services.impl;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import com.hj.web.filter.EmojiFilter;
import com.hj.wxmp.core.WxUser;
import com.hj.wxmp.mobile.entity.UserInfo;
import com.hj.wxmp.mobile.mapping.UserInfoMapper;
import com.hj.wxmp.mobile.services.IKeyGen;
import com.hj.wxmp.mobile.services.WxLoginService;

@Component
public class WxLoginServiceImpl implements WxLoginService {

	@Resource
	private IKeyGen keyGen;
	
	@Resource
	private JdbcTemplate jdbcTemplate;
	
	@Resource
	private UserInfoMapper sysUserMapper;
	

	@Override
	public UserInfo bandingUser(WxUser wxUser,String curUrl) {
		if(null==wxUser)return null;
		
		boolean isNewUser = false;
	
		UserInfo user = sysUserMapper.selectByOpenid(wxUser.getOpenid());
		
		if(null==user){
			user = new UserInfo();
			isNewUser = true;
		}
		
		user.setOpenid(wxUser.getOpenid());
		user.setSubscribe(NumberUtils.toInt(wxUser.getSubscribe()));
		String nickName = EmojiFilter.filterEmoji(wxUser.getNickname());
		user.setNickname(nickName);
		if(StringUtils.isBlank(user.getSex())){
			user.setSex(wxUser.getSex());
		}
		user.setLanguage(wxUser.getLanguage());
		user.setCity(wxUser.getCity());
		user.setCountry(wxUser.getCountry());
		user.setProvince(wxUser.getProvince());
		if(StringUtils.isBlank(user.getHeadimgurl())){
			user.setHeadimgurl(wxUser.getHeadimgurl());
		}
		user.setUnionid(wxUser.getUnionid());
		if(StringUtils.isBlank(user.getRemark())){
			user.setRemark(wxUser.getRemark());
		}
		user.setGroupid(wxUser.getGroupid());
		user.setSubscribeTime(wxUser.getSubscribe_time());
		
		int result = 0;
		if(isNewUser){
			user.setId(keyGen.getUUIDKey());
			result = sysUserMapper.insertSelective(user);
		}else{
			result = sysUserMapper.updateByPrimaryKeySelective(user);
		}
		
		System.out.println("result---------zhq1234"+result);
		System.out.println("user---------zhq1234"+user);
		
		if(result>0){
			return user;
		}
		
		return null;
	}

	public static void main(String[] args) {
		String curUrl = "/myproblembyid.az?ooie=dsfad122121&openid=orvMHwUnDCkzIITAXA9BsXnUS6wQ&from=singlemessage&isappinstalled=0";
		
		String parentId = curUrl.substring(curUrl.indexOf("openid=")+7);
		System.out.println("index:"+curUrl.indexOf("openid=")+"--parentId1:"+parentId);
		
		System.out.println(parentId);
		
		if(curUrl.contains("&")){
			
			System.out.println("aa:"+curUrl.indexOf("&",curUrl.indexOf("openid=")));
			
			System.out.println("parentId:"+curUrl.substring(curUrl.indexOf("openid=")+7, curUrl.indexOf("&from")));
		}
		
	}
}
