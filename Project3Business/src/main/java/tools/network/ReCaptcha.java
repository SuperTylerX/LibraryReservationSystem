package tools.network;

import config.AppConfig;

import java.util.HashMap;
import java.util.Map;

public class ReCaptcha {
    public static boolean captchaValidate(String captchaToken) {

        Map<String, String> map = new HashMap<>();
        map.put("secret", AppConfig.RECAPTCHA_SERVER_SECRET);
        map.put("response", captchaToken);
        String result = Http.sendFormPost("https://www.recaptcha.net/recaptcha/api/siteverify", map);
        System.out.println(result);
        // JSON may look like this
        //{
        //  "success": true,
        //  "challenge_ts": "2021-03-29T01:13:30Z",
        //  "hostname": "localhost"
        //}
        // TODO: return json.success

//        JSONObject json = new JSONObject(result);
        return true;
    }
}
