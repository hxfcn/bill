package com.sdi.bill.common;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import javax.servlet.http.HttpServletRequest;

import com.alibaba.fastjson.JSONObject;

public class BaseController {

	public JSONObject getJSONParam(HttpServletRequest request) {
	    JSONObject jsonParam = null;
	    try {
	        // 获取输入流
	        BufferedReader streamReader = new BufferedReader(new InputStreamReader(request.getInputStream(), "UTF-8"));

	        // 写入数据到Stringbuilder
	        StringBuilder sb = new StringBuilder();
	        String line = null;
	        while ((line = streamReader.readLine()) != null) {
	            sb.append(line);
	        }
	        jsonParam = JSONObject.parseObject(sb.toString());
	        // 直接将json信息打印出来
	        //System.out.println(jsonParam.toJSONString());
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	    return jsonParam;
	}
}
