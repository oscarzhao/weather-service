package com.weather.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.ErrorAttributes;
import org.springframework.boot.autoconfigure.web.ErrorController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.weather.model.StatusResponse;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/*
 * SystemError is used to override whitelabel page
 * the idea is from: https://gist.github.com/jonikarppinen/662c38fb57a23de61c8b
 */

@RestController
public class SystemError implements ErrorController {

    private static final String PATH = "/error";
    
    @Autowired
    private ErrorAttributes errorAttributes;

    @RequestMapping(value = PATH, produces = "application/json")
    StatusResponse error(HttpServletRequest request, HttpServletResponse response) {
    	Map<String, Object> errAttr = getErrorAttributes(request, false);
        return new StatusResponse(null, (String) errAttr.get("error"));
    }

    @Override
    public String getErrorPath() {
        return PATH;
    }
    
    private Map<String, Object> getErrorAttributes(HttpServletRequest request, boolean includeStackTrace) {
        RequestAttributes requestAttributes = new ServletRequestAttributes(request);
        return errorAttributes.getErrorAttributes(requestAttributes, includeStackTrace);
    }
}