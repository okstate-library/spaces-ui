package com.okstatelibrary.spacesui.controllers;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class DebugController {

    @GetMapping("/debug/request")
    @ResponseBody
    public String debug(HttpServletRequest request) {

        StringBuilder sb = new StringBuilder();

        sb.append("Request URL : ").append(request.getRequestURL()).append("<br>");
        sb.append("Scheme      : ").append(request.getScheme()).append("<br>");
        sb.append("ServerName  : ").append(request.getServerName()).append("<br>");
        sb.append("ServerPort  : ").append(request.getServerPort()).append("<br>");
        sb.append("Host Header : ").append(request.getHeader("Host")).append("<br>");
        sb.append("Forwarded   : ").append(request.getHeader("X-Forwarded-Host")).append("<br>");
        sb.append("Forwarded Proto : ").append(request.getHeader("X-Forwarded-Proto")).append("<br>");

        return sb.toString();
    }
}
