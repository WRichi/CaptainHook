package at.hagenberg.captainhook.model.spotify;

import at.hagenberg.captainhook.model.spotify.spotify_model.AccessToken;
import at.hagenberg.captainhook.model.spotify.spotify_model.SpotifyData;
import at.hagenberg.captainhook.model.spotify.spotify_model.tracksinplaylist.Tracks;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface SpotifyService {

    @POST("/api/token")
    @FormUrlEncoded
    @Headers({
            "Accept: application/json"
    })
    Call<AccessToken> getAccessToken(@Field("grant_type") String grantType,
                                     @Header("Authorization") String authorization);

    @GET("/v1/search")
    Call<SpotifyData> searchSpotify(@Query("q") String query, @Query("type") String type, @Header("Authorization") String authorization);

    @GET("/v1/{playlistURL}")
    Call<Tracks> getTracks(@Path(value = "playlistURL", encoded = true) String playlistURL, @Header("Authorization") String authorization);

}

