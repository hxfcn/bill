package com.sdi.bill.login;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.alibaba.fastjson.JSONObject;
import com.sdi.bill.common.BaseService;
import com.sdi.bill.common.HttpUtil;
import com.sdi.bill.common.RET;
import com.sdi.bill.session.WxSession;

@Service
public class LoginService extends BaseService {
	
//	private String appid = "wx7c0a317b8abc0518";
//	private String appSecret = "123";
	
	@Autowired
	LoginDao _dao;
	
	@Value("${app.id}")
	private String appid;

	@Value("${app.secret}")
	private String appSecret;
	

	@Value("${app.timeout}")
	private int appTimeout;
	
	
	public String login(String encryptedData, String iv, String code) {

		WxSession.timeout = appTimeout;
		String apiUrl="https://api.weixin.qq.com/sns/jscode2session?appid="+appid+"&secret="+appSecret+"&js_code="+code+"&grant_type=authorization_code";
		String wxres = null;
		for(int i = 0; i< 3; i++) {
			wxres = HttpUtil.get(apiUrl);
			if(wxres!= null) {
				break;
			}
		}
		
		if(wxres == null) {
			return RET.error(101, "Can't get weixin login info");
		}
		
		JSONObject json = JSONObject.parseObject(wxres);
		if(json == null) {
			return RET.error(102, "weixin login info error.");
		}
		
		String openid = json.getString("openid");
		String session_key = json.getString("session_key");
		
		if(StringUtils.isEmpty(openid) || StringUtils.isEmpty(session_key)) {
			return RET.error(103, "weixin login error:" +  wxres);
		}
		
		String sessionid = WxSession.instance().getUUID(openid);
		if(!WxSession.noLogin(sessionid) && !WxSession.loginTimeout(sessionid)) {
			JSONObject obj = new JSONObject();
			obj.put("id", sessionid);
			return RET.data(obj);
		}else {
			if(!_dao.hasInfo(openid)){
				JSONObject userInfoJSON=WechatGetUserInfoUtil.getUserInfo(encryptedData,session_key,iv);
				_dao.register(userInfoJSON);
			}
			sessionid = WxSession.instance().login(openid);
			JSONObject obj = new JSONObject();
			obj.put("id", sessionid);
			return RET.data(obj);
		}

	}
	
	
	
}
