package service;

import javax.ws.rs.core.Response;

public class PermissionException extends Exception{
    Response response;

    public PermissionException(Response response){
        this.response = response;
    }
}
