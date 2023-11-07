package com.spotify.oauth2.tests;

import com.spotify.oauth2.api.StatusCode;
import com.spotify.oauth2.api.applicationApi.PlaylistApi;
import com.spotify.oauth2.pojo.Error;
import com.spotify.oauth2.pojo.Playlist;
import com.spotify.oauth2.utils.DataLoader;
import com.spotify.oauth2.utils.FakerUtils;
import io.qameta.allure.*;
import io.restassured.response.Response;

import org.testng.annotations.Test;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;
@Epic("Spotify Oauth 2.0")
@Feature("Playlist API")
public class PlaylistTests extends BaseTest{
    @Story("Create a playlist user story")
    @Link("https://example.org")
    @Link(name="common link",type="my link") //Linking common link for any reference
    @TmsLink("Manual Testcase ID 1012") //Linking manual testcase id
    @Issue("Defect ID 1024") //Linking defect id
    @Description("This is description to create playlist") //Description annotation from Allure is used to add description section in report
    @Test(description = "Create Playlist") //What we enter inside the double quotes is displayed in report instead of this method name
    public void createPlaylist()
    {
        Playlist requestPlaylist = playlistBuilder(FakerUtils.generateName(), FakerUtils.generateDescription(),false);
        Response response = PlaylistApi.post(requestPlaylist);
        assertPlaylistStatusCode(response.statusCode(),StatusCode.CODE_201); //201
        assertPlaylistEqual(response.as(Playlist.class),requestPlaylist);
    }

    //We are going to get the playlist which we created through Rest Assured(Above Testcase)
    @Story("Get a playlist user story")
    @Description("This is description to get playlist")
    @Test(description = "Get Playlist")
    public void getPlaylist()
    {
        Playlist requestPlaylist = playlistBuilder("Harivenkatesh","Created by using Rest Assured on October 19, 2023 and updated by using Rest Assured on October 20, 2023",false);
        Response response = PlaylistApi.get(DataLoader.getInstance().getGetPlaylistId());
        assertPlaylistStatusCode(response.statusCode(),StatusCode.CODE_200);
        assertPlaylistEqual(response.as(Playlist.class),requestPlaylist);
    }

    //We are going to update/change one of the playlists which we created
    @Story("Update a playlist user story")
    @Description("This is description to update playlist")
    @Test(description = "Update Playlist")
    public void updatePlaylist()
    {
        Playlist requestPlaylist = playlistBuilder(FakerUtils.generateName(), FakerUtils.generateDescription(), false);
        Response response = PlaylistApi.update(DataLoader.getInstance().getUpdatePlaylistId(),requestPlaylist);
        assertPlaylistStatusCode(response.statusCode(),StatusCode.CODE_200);
    }

    @Story("Create a playlist user story")
    @Description("This is description NOT to create playlist without name")
    @Test(description = "Should NOT create playlist without name")
    public void ShouldNotCreatePlaylistWithoutName()
    {
        Playlist requestPlaylist = playlistBuilder("",FakerUtils.generateDescription(),false);
        Response response = PlaylistApi.post(requestPlaylist);
        assertPlaylistStatusCode(response.statusCode(),StatusCode.CODE_400);
        assertError(response.as(Error.class),StatusCode.CODE_400);//400
    }

    @Story("Create a playlist user story")
    @Description("This is description NOT to create playlist with expired token")
    @Test(description = "Should NOT create playlist with expired token")
    public void ShouldNotCreatePlaylistWithExpiredToken()
    {
        String invalid_token="BQB2xP2PYPod5qq-gh2caCwSb1SfvbQiv3RblE9Ji7p1E6PrCsvN1otvnor8Uxyk5X6Ze3R93WpXlhI14fgoAZUK3Vq6Fe3hjwKwsP8DjCFzfbURd77vVy7QmSSDStYdoj_PA0z_rK34HfN17OCUQBRaylw1EM8izrGIFDW9Go_HALf98Tlnj7nFrXk9b3Ua4DYFlbUceu_cl6K1AeIJFVFyljRUrz59LlEVhnBMSvaoPKQVmL7AIvHfqnGwxQCy6Aqsh_hhKfvq4R8j";
        Playlist requestPlaylist = playlistBuilder(FakerUtils.generateName(), FakerUtils.generateDescription(),false);
        Response response = PlaylistApi.post(invalid_token, requestPlaylist);
        assertPlaylistStatusCode(response.statusCode(),StatusCode.CODE_401);
        assertError(response.as(Error.class),StatusCode.CODE_401);
    }

    //Reusable methods
    @Step
    public Playlist playlistBuilder(String name, String description, boolean _public){
        return Playlist.builder().
                name(name).
                description(description).
                _public(_public).
                build();// this code is following builder pattern provided by lombok.
    }

    @Step
    public void assertPlaylistEqual(Playlist responsePlaylist, Playlist requestPlaylist){
        assertThat(responsePlaylist.getName(),equalTo(requestPlaylist.getName()));
        assertThat(responsePlaylist.getDescription(),equalTo(requestPlaylist.getDescription()));
        assertThat(responsePlaylist.get_public(),equalTo(requestPlaylist.get_public()));
    }

    @Step
    public void assertPlaylistStatusCode(int actualStatusCode, StatusCode statusCode){
        assertThat(actualStatusCode,equalTo(statusCode.getCode()));
    }

    @Step
    public void assertError(Error responseErr, StatusCode statusCode){
        assertThat(responseErr.getError().getStatus(),equalTo(statusCode.getCode()));
        assertThat(responseErr.getError().getMessage(),equalTo(statusCode.getMsg()));
    }
}
