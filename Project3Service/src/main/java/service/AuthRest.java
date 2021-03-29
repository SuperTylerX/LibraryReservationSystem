package service;


import impl.UserManagerImpl;
import interfacedef.UserManager;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import tools.network.Http;
import tools.network.ReCaptcha;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.HashMap;
import java.util.Map;

@Path("auth")
public class AuthRest {


    @POST
    @Path("login")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.APPLICATION_JSON)
    public Response login(@FormParam("username") String username, @FormParam("password") String password, @FormParam("captchaToken") String captchaToken) {

        // TODO 1: Check Captcha
        ReCaptcha.captchaValidate(captchaToken); // (Not Completed...)

        // TODO 2: Check Username and password
        UserManager userManager = UserManagerImpl.getInstance();
        userManager.login(username, password); // (Not Completed...)

        // TODO 3: Return JSON
        // success: {
        //      code: 200,
        //      token: String
        // }

        // fail: {
        //      code: 400/401,
        //      message: "Username Password incorrect/ Challenge failed"
        //}

        return Response.ok()
                .entity(null)
                .header("Access-Control-Allow-Origin", "*")
                .build();

    }

    @GET
    @Path("user")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getUserInfo(@HeaderParam("Authorization") String token) {

        /*
         * return json
         *  success: {
         *      code: 200
         *      data: {
         *          userId: number,
         *          userRole: string
         *      }
         * }
         *
         * fail: {
         *      code: 400,
         *      message: "Token expired/ Token not validated"
         * }
         *
         * */
        return Response.ok()
                .entity(null)
                .header("Access-Control-Allow-Origin", "*")
                .build();
    }

}
