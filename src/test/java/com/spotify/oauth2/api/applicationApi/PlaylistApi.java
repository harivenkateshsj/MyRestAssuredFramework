package com.spotify.oauth2.api.applicationApi;

import com.spotify.oauth2.api.RestResource;

import com.spotify.oauth2.api.Route;
import com.spotify.oauth2.api.TokenManager;
import com.spotify.oauth2.pojo.Playlist;
import com.spotify.oauth2.utils.ConfigLoader;
import io.qameta.allure.Step;
import io.restassured.response.Response;


public class PlaylistApi {

    @Step
    public static Response post(Playlist requestPlaylist)
    {
        return RestResource.post(Route.USERS + "/" + ConfigLoader.getInstance().getUser() + Route.PLAYLISTS, TokenManager.getToken() ,requestPlaylist);
    }

    @Step
    public static Response post(String token, Playlist requestPlaylist)
    {
        return RestResource.post(Route.USERS+ "/" + ConfigLoader.getInstance().getUser() + Route.PLAYLISTS,token,requestPlaylist);
    }

    @Step
    public static Response get(String playlistId)
    {
        return RestResource.get(Route.PLAYLISTS + "/" + playlistId, TokenManager.getToken());
    }

    @Step
    public static Response update(String playlistId, Playlist requestPlaylist)
    {
        return RestResource.update(Route.PLAYLISTS + "/" + playlistId, TokenManager.getToken(), requestPlaylist);
    }

}
