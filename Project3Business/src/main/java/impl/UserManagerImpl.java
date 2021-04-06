package impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import config.AppConfig;
import dao.UserDao;
import interfacedef.UserManager;

import pojo.User;
import tools.encryption.Encrypt;

import java.util.Date;

public class UserManagerImpl implements UserManager {

    private static final UserManager userManager = new UserManagerImpl();

    public static UserManager getInstance() {
        return userManager;
    }

    private UserManagerImpl() {

    }

    /**
     * @param username is the entered username
     * @param password is the entered password
     * @return String is token
     * this method should verify the username and password.
     * generate the token if validated
     * otherwise return null
     */
    @Override
    public String login(String username, String password) {
        // 1: validate username and password in database
        UserDao userDao = new UserDao();
        String DBPassword = Encrypt.sha256EncryptSalt(password, AppConfig.PASSWORD_SALT);
        User user = userDao.checkPassword(username, DBPassword);
        // 2: generate token if validate, otherwise return null
        if (user != null) {
            return tokenGen(user.getUserId());
        } else {
            return null;
        }
    }

    public static void main(String[] args) {
        System.out.println(userManager.login("tianxiang","123456"));
    }
    @Override
    public User getUserInfo(int userId) {
        UserDao userDao = new UserDao();
        return userDao.getUserById(userId);
    }

    /**
     * @param token is the token
     * @return int is the userID, -1 is not validated
     * this method is used to validate the token
     * return the userId if the token is validated
     * otherwise return -1
     */
    @Override
    public int verifyToken(String token) {
        //   use sha256 algorithm to recompute the signature and compare two signatures
        //      sha256EncryptSalt(Header + "." + Payload, secret)   ==  Signature
        String[] tokenArr = token.split("\\.");
        String newSignature = Encrypt.sha256EncryptSalt(tokenArr[0] + "." + tokenArr[1], AppConfig.JWT_SECRET);
        assert newSignature != null;
        if (!newSignature.equals(tokenArr[2])) {
            return -1;  // signature doesn't match
        }
        String payload = Encrypt.base642string(tokenArr[1]);
        JSONObject jsonObject = JSON.parseObject(payload);
        // check the token is expired or not
        Date date = new Date();
        if (date.getTime() > jsonObject.getLong("exp")) {
            return -2;  // token Expired
        }
        return jsonObject.getIntValue("userId");
    }


    private String tokenGen(int userId) {
        final long HOUR = 3600 * 1000; // in milli-seconds.
        String header = "{\"alg\": \"HS256\",\"typ\": \"JWT\"}";
        String payload = "{\"userId\":" + userId + ",\"exp\":" + (new Date().getTime() + HOUR) + "}";
        String signature = Encrypt.sha256EncryptSalt(Encrypt.string2Base64(header) + "." + Encrypt.string2Base64(payload), AppConfig.JWT_SECRET);
        return Encrypt.string2Base64(header) + "." + Encrypt.string2Base64(payload) + "." + signature;
    }
}
