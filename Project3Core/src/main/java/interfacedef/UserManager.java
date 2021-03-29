package interfacedef;

import pojo.User;

public interface UserManager {

    public String login(String username, String password);

    public User getUserInfo(int userId);

    public int verifyToken(String token);

}
