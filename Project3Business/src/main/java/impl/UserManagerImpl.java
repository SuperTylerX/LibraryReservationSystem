package impl;

import interfacedef.UserManager;
import pojo.User;

public class UserManagerImpl implements UserManager {

    private static final UserManager userManager = new UserManagerImpl();

    public static UserManager getInstance() {
        return userManager;
    }

    /**
     * @param username is the entered username
     * @param password is the entered password
     * @return String is token
     * @description this method should verify the username and password.
     * generate the token if validated
     * otherwise return null
     */
    @Override
    public String login(String username, String password) {

        // TODO 1: validate username and password in database

        // TODO 2: generate token if validate, otherwise return null
        //          may call tokenGen()
        return null;
    }

    @Override
    public User getUserInfo(int userId) {
        return null;
    }

    /**
     * @param token is the token
     * @return int is the userID, -1 is not validated
     * @description this method is used to validate the token
     * return the userId if the token is validated
     * otherwise return -1
     */
    @Override
    public int verifyToken(String token) {

        // TODO 1: use sha256 algorithm to recompute the signature and compare two signatures
        //      sha256EncryptSalt(Header + "." + Payload, secret)   ==  Signature

        // TODO 2: convert base64 payload to plain text
        //      check the token is expired or not

        // TODO 3: return userId
        return 0;
    }

    private String tokenGen(int userId) {
        /*
        * Header: Header part is a json, need to Stringify
        * {
              "alg": "HS256",
              "typ": "JWT"
            }
        * Payload: Payload part is a json, need to Stringify
        *
        * {
              "userId": number,
              "exp": number     // the exp date. should be validated in 1h( new Date() + 1h )
             }
        * Signature: Signature is
              sha256EncryptSalt(
                  string2Base64(header) + "." + string2Base64(payload),
                  JWT_SECRET
              )
          *
          * Final Token is like
          *
          *             Header + "." + Payload + "." + Signature
          *
        * */
        return null;
    }

}
