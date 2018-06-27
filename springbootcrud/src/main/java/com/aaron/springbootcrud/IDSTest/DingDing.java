package com.aaron.springbootcrud.IDSTest;


import net.sf.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
@Controller
public class DingDing {
    @GetMapping("/loginFromDing")
    public String loginFromDing(String code, HttpSession session, String state) {
        System.out.println("ding");
        String appId = "dingoarfrildadydajco5f";
        String 	appSecret = "4W33OFMorIYBtGR_8HNKaCvVAt1f6YnkgUZpR73lNlmMcw2ktED7caXm-FhWVvZT";
        String urll = "https://oapi.dingtalk.com/sns/gettoken?appid=" + appId + "&appsecret=" + appSecret;
//		String redirect_url = "http://119.29.9.243:8080/loginFromDing";
        String res = NetUtil.doGet(urll);
        //获取access_token
        String access_token = JSONObject.fromObject(res).getString("access_token");


        //获取持久授权码
        String url = "https://oapi.dingtalk.com/sns/get_persistent_code?access_token=" + access_token;
        HashMap<String, Object> map = new HashMap();
        map.put("tmp_auth_code", code);
        String res1 = NetUtil.doPost(url, map);
        String persistent_code = JSONObject.fromObject(res1).getString("persistent_code");
        String openid = JSONObject.fromObject(res1).getString("openid");

        //获取sns_token
//		String getSnsUrl = "https://oapi.dingtalk.com/sns/get_sns_token?access_token=" + session.getAttribute("access_token");
        String getSnsUrl = "https://oapi.dingtalk.com/sns/get_sns_token?access_token=" + access_token;
        HashMap<String, Object> snsMap = new HashMap();
        snsMap.put("openid", openid);
        snsMap.put("persistent_code", persistent_code);
        String snsRes = NetUtil.doPost(getSnsUrl,  snsMap);
        String sns_token = JSONObject.fromObject(snsRes).getString("sns_token");

        //获取用户信息
        String getUserInfoUrl = "https://oapi.dingtalk.com/sns/getuserinfo?sns_token=" + sns_token;
        JSONObject userInfo = JSONObject.fromObject(NetUtil.doGet(getUserInfoUrl));
        System.out.println(userInfo);
        session.setAttribute("userName", userInfo.getJSONObject("user_info").getString("nick"));
        return "railway_investment";
    }
}
