package service;


import com.alibaba.fastjson.JSONObject;
import impl.UserManagerImpl;
import interfacedef.UserManager;

import pojo.User;
import tools.network.ReCaptcha;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;


@Path("auth")
public class AuthRest {


    @POST
    @Path("login")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.APPLICATION_JSON)
    public Response login(@FormParam("username") String username, @FormParam("password") String password, @FormParam("captchaToken") String captchaToken) {
        JSONObject responseJson = new JSONObject();
        // 1: Check Captcha
        if (!ReCaptcha.captchaValidate(captchaToken)) {
            responseJson.put("code", 401);
            responseJson.put("message", "Challenge failed");
            return Response.ok().entity(responseJson.toJSONString()).build();
        }
        // 2: Check Username and password
        UserManager userManager = UserManagerImpl.getInstance();
        String token = userManager.login(username, password);
        if (token == null) {
            responseJson.put("code", 400);
            responseJson.put("message", "Username or Password is incorrect");
        } else {
            responseJson.put("code", 200);
            responseJson.put("token", token);
        }
        return Response.ok().entity(responseJson.toJSONString()).build();

    }

    @GET
    @Path("user")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getUserInfo(@HeaderParam("Authorization") String token) {
        UserManager userManager = UserManagerImpl.getInstance();
        int userId = userManager.verifyToken(token);
        JSONObject responseJson = new JSONObject();
        if (userId == -1) {
            responseJson.put("code", 400);
            responseJson.put("message", "Token not validated");
            return Response.ok()
                    .entity(responseJson.toJSONString())
                    .build();
        }
        if (userId == -2) {
            responseJson.put("code", 401);
            responseJson.put("message", "Token expired");
            return Response.ok()
                    .entity(responseJson.toJSONString())
                    .build();
        }
        User user = userManager.getUserInfo(userId);
        responseJson.put("code", 200);
        responseJson.put("data", user);
        return Response.ok()
                .entity(responseJson.toJSONString())
                .build();
    }

}
