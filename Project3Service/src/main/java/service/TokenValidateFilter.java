package service;

import com.alibaba.fastjson.JSONObject;
import impl.UserManagerImpl;
import interfacedef.UserManager;
import pojo.User;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.container.ContainerResponseFilter;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriInfo;
import java.io.IOException;

public class TokenValidateFilter implements ContainerResponseFilter {

    @Override
    public void filter(ContainerRequestContext containerRequestContext, ContainerResponseContext containerResponseContext) {
        JSONObject responseJson = new JSONObject();
        UriInfo uriInfo = containerRequestContext.getUriInfo();
        if (uriInfo.getPath().contains("auth")) {
            return;
        }
        String token = containerRequestContext.getHeaderString("Authorization");
        UserManager userManager = UserManagerImpl.getInstance();
        int res = userManager.verifyToken(token);
        if (res == -1) {
            containerResponseContext.setStatus(200);
            responseJson.put("code", 400);
            responseJson.put("message", "Token not validated");
            containerResponseContext.getHeaders().add("Content-Type", MediaType.APPLICATION_JSON);
            containerResponseContext.setEntity(responseJson.toJSONString());
            return;
        }
        if (res == -2) {
            containerResponseContext.setStatus(200);
            responseJson.put("code", 401);
            responseJson.put("message", "Token expired");
            containerResponseContext.getHeaders().add("Content-Type", MediaType.APPLICATION_JSON);
            containerResponseContext.setEntity(responseJson.toJSONString());
            return;
        }
        User user = userManager.getUserInfo(res);
        containerRequestContext.getHeaders().add("userId",String.valueOf(user.getUserId()));
        containerRequestContext.getHeaders().add("role",user.getRole());
    }
}
