package com.spotify.oauth2.api;

public enum StatusCode {
    CODE_200(200,""),
    CODE_201(201,""),
    CODE_400(400,"Missing required field: name"),
    CODE_401(401,"The access token expired");

    private final int code;
    private final String msg;

    StatusCode(int code,String msg){
        this.code=code;
        this.msg=msg;
    }

    //Since we are declared variable code as private variable, we need getter to access this variable
    public int getCode()
    {
        return code;
    }

    //Since we are declared variable msg as private variable, we need getter to access this variable
    public String getMsg()
    {
        return msg;
    }
}
