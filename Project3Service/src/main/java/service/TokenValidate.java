package service;

import com.alibaba.fastjson.JSONObject;
import impl.UserManagerImpl;
import pojo.User;

import javax.ws.rs.core.Response;

public class TokenValidate {
    public static User tokenValidate(String token) throws PermissionException {
        JSONObject responseJson = new JSONObject();
        if (token == null) {
            responseJson.put("code", 400);
            responseJson.put("message", "Invalid token");
            throw new PermissionException(Response.ok()
                    .entity(responseJson.toJSONString())
                    .build());
        }
        int res = UserManagerImpl.getInstance().verifyToken(token);
        if (res == -1) {
            responseJson.put("code", 400);
            responseJson.put("message", "Invalid token");
            throw new PermissionException(Response.ok()
                    .entity(responseJson.toJSONString())
                    .build());
        }
        if (res == -2) {
            responseJson.put("code", 401);
            responseJson.put("message", "Token expired");
            throw new PermissionException(Response.ok()
                    .entity(responseJson.toJSONString())
                    .build());
        }
        return UserManagerImpl.getInstance().getUserInfo(res);
    }
}
