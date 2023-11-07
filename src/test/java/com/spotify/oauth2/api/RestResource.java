package com.spotify.oauth2.api;

import com.spotify.oauth2.api.SpecBuilder;
import com.spotify.oauth2.pojo.Playlist;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

import java.util.HashMap;

import static com.spotify.oauth2.api.SpecBuilder.getResponseSpec;
import static io.restassured.RestAssured.given;

public class RestResource {
    public static Response post(String path, String access_token, Object requestPlaylist)
    {
        return given(SpecBuilder.getRequestSpec()). //className.methodName
                body(requestPlaylist).
                auth().oauth2(access_token). //this code replaces header("Authorization","Bearer " + access_token).
                when().post(path).
                then().spec(SpecBuilder.getResponseSpec()).
                extract().
                response();
    }

    public static Response get(String path,String access_token)
    {
        return given(SpecBuilder.getRequestSpec()).
                auth().oauth2(access_token). //this code replaces header("Authorization","Bearer " + access_token).
                when().get(path).
                then().spec(SpecBuilder.getResponseSpec()).
                extract().
                response();
    }

    public static Response update(String path, String access_token, Object requestPlaylist)
    {
        return given(SpecBuilder.getRequestSpec()).
                auth().oauth2(access_token). //this code replaces header("Authorization","Bearer " + access_token).
                body(requestPlaylist).
                when().put(path).
                then().spec(SpecBuilder.getResponseSpec()).
                extract().
                response();
    }

    public static Response postAccount(HashMap<String, String> formParams)
    {
        return given(SpecBuilder.getAccountRequestSpec()).
                formParams(formParams).
                when().post(Route.API + Route.TOKEN).
                then().spec(getResponseSpec()).
                extract().
                response();
    }

}
