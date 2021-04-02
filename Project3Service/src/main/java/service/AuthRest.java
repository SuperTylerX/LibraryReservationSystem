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
        // 1: Check Captcha
        if (!ReCaptcha.captchaValidate(captchaToken)) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("code", 401);
            jsonObject.put("message", "Challenge failed");
            return Response.ok().entity(jsonObject).header("Access-Control-Allow-Origin", "*")
                    .build();
        }
        // 2: Check Username and password
        UserManager userManager = UserManagerImpl.getInstance();
        String token = userManager.login(username, password);
        if (token == null) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("code", 400);
            jsonObject.put("message", "Username Password incorrect");
            return Response.ok().entity(jsonObject).header("Access-Control-Allow-Origin", "*")
                    .build();
        } else {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("code", 200);
            jsonObject.put("token", token);
            return Response.ok().entity(jsonObject).header("Access-Control-Allow-Origin", "*")
                    .build();
        }
    }

    @GET
    @Path("user")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getUserInfo(@HeaderParam("Authorization") String token) {
        UserManager userManager = UserManagerImpl.getInstance();
        int userId = userManager.verifyToken(token);
        if (userId < 0) {
            JSONObject jsonObjectFailed = new JSONObject();
            jsonObjectFailed.put("code", 400);
            jsonObjectFailed.put("message", "Token expired/ Token not validated");
            return Response.ok()
                    .entity(jsonObjectFailed)
                    .header("Access-Control-Allow-Origin", "*")
                    .build();
        }
        User user = userManager.getUserInfo(userId);
        JSONObject jsonObjectSuccess = new JSONObject();
        jsonObjectSuccess.put("code", 200);
        jsonObjectSuccess.put("data", user);
        return Response.ok()
                .entity(jsonObjectSuccess)
                .header("Access-Control-Allow-Origin", "*")
                .build();
    }

}
