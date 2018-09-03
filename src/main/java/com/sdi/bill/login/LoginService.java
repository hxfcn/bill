package com.sdi.bill.login;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.alibaba.fastjson.JSONObject;
import com.sdi.bill.common.BaseService;
import com.sdi.bill.common.HttpUtil;
import com.sdi.bill.common.Ret;
import com.sdi.bill.session.WxSession;

@Service
public class LoginService extends BaseService {
	
	private String appid = "wx7c0a317b8abc0518";
	private String appSecret = "123";
	
	@Autowired
	LoginDao _dao;
	
	public String login(String encryptedData, String iv, String code) {

		String apiUrl="https://api.weixin.qq.com/sns/jscode2session?appid="+appid+"&secret="+appSecret+"&js_code="+code+"&grant_type=authorization_code";
		String wxres = null;
		for(int i = 0; i< 3; i++) {
			wxres = HttpUtil.get(apiUrl);
			if(wxres!= null) {
				break;
			}
		}
		
		if(wxres == null) {
			return Ret.error(101, "Can't get weixin login info");
		}
		
		JSONObject json = JSONObject.parseObject(wxres);
		if(json == null) {
			return Ret.error(102, "weixin login info error.");
		}
		
		String openid = json.getString("openid");
		String session_key = json.getString("session_key");
		
		if(StringUtils.isEmpty(openid) || StringUtils.isEmpty(session_key)) {
			return Ret.error(102, "weixin login info error.");
		}
		
		if(!_dao.hasInfo(openid)){
			JSONObject userInfoJSON=WechatGetUserInfoUtil.getUserInfo(encryptedData,session_key,iv);
			_dao.register(userInfoJSON);
		}
		
		String sessionid = WxSession.instance().login(openid);
		return String.format("{code:0,data:{id:\"%s\"}", sessionid);
	}
	
	
	
}
