package tools.network;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import config.AppConfig;

import java.util.HashMap;
import java.util.Map;

public class ReCaptcha {
    public static boolean captchaValidate(String captchaToken) {

        Map<String, String> map = new HashMap<>();
        map.put("secret", AppConfig.RECAPTCHA_SERVER_SECRET);
        map.put("response", captchaToken);
        String result = Http.sendFormPost("https://www.recaptcha.net/recaptcha/api/siteverify", map);
        JSONObject jsonObject = JSON.parseObject(result);
        return jsonObject.getBooleanValue("success");
    }
}
