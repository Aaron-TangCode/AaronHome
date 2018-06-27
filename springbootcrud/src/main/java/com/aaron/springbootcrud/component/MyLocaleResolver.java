package com.aaron.springbootcrud.component;




import org.springframework.web.servlet.LocaleResolver;
import org.thymeleaf.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Locale;
import java.util.Set;

/**
 * 可以在连接上携带区域信息
 */
public class MyLocaleResolver implements LocaleResolver {


    @Override
    public Locale resolveLocale(HttpServletRequest httpServletRequest) {
        String l = httpServletRequest.getParameter("l");
        Locale locale = Locale.getDefault();//获取系统默认的locale
        if (!StringUtils.isEmpty(l)){
            String[] spilt = l.split("_");//要把语言和国家分开，zh_CN中间有下划线，所以用下划线划分
            locale = new Locale(spilt[0],spilt[1]);//前一个是语言；后一个是国家
        }
        return locale;
    }

    @Override
    public void setLocale(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Locale locale) {

    }
}
