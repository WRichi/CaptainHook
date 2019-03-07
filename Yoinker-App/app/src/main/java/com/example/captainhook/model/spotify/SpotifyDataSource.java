package com.example.captainhook.model.spotify;

import android.util.Log;

import com.example.captainhook.model.spotify.spotify_model.AccessToken;
import com.example.captainhook.model.spotify.spotify_model.SpotifyData;
import com.example.captainhook.model.spotify.spotify_model.tracksinplaylist.Tracks;

import java.util.Base64;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SpotifyDataSource{

    private final String TAG = getClass().getName();

    private final String CLIENT_ID = "276eca70a98c46b6a22ff0223f38c8fc";
    private final String CLIENT_SECRET = "320f4cc6b8db4373a54a92e398bad45b";
    private final String SPOTIFY_WEB_API_ADRESS="https://api.spotify.com"; //searching spotify
    private final String SPOTIFY_ACCOUNTS_ADRESS= "https://accounts.spotify.com"; //for access token

    public void searchSpotify(String query, SpotifySearchType searchType, AccessToken accessToken, final SpotifySearchResultCallback spotifySearchResultCallback) {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(SPOTIFY_WEB_API_ADRESS)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            SpotifyService service = retrofit.create(SpotifyService.class);
            Call<SpotifyData> accessTokenCall = service.searchSpotify(query, searchType.name().toLowerCase(), "Bearer " + accessToken.getAccessToken());

            Callback<SpotifyData> spotifyDataCallback = new Callback<SpotifyData>() {
                @Override
                public void onResponse(Call<SpotifyData> call, Response<SpotifyData> response) {
                    SpotifyData spotifyData = response.body();
                    spotifySearchResultCallback.onSearchResult(spotifyData);
                }

                @Override
                public void onFailure(Call<SpotifyData> call, Throwable t) {
                    Log.d(TAG, t.getMessage());
                }
            };
            accessTokenCall.enqueue(spotifyDataCallback);
        }

    public void getAccessToken(final SpotifyAccessTokenCallback accessTokenCallback){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(SPOTIFY_ACCOUNTS_ADRESS)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        SpotifyService service = retrofit.create(SpotifyService.class);
        byte[] credentials = (CLIENT_ID + ":" + CLIENT_SECRET).getBytes();
        String basicAuth = "Basic " + Base64.getEncoder().encodeToString(credentials);

        final Call<AccessToken> accessTokenCall = service.getAccessToken("client_credentials", basicAuth);
        accessTokenCall.enqueue(new Callback<AccessToken>() {
            @Override
            public void onResponse(Call<AccessToken> call, Response<AccessToken> response) {
                accessTokenCallback.onAccessTokenRecieved(response.body());
                Log.d(TAG, "Token="+response.body().getAccessToken());
            }

            @Override
            public void onFailure(Call<AccessToken> call, Throwable t) {
                Log.d(TAG, t.getMessage());
            }
        });
    }

    public void getTracksForUrl(String url, AccessToken accessToken, final SpotifyGetTracksFromPlaylistCallback resultCallback){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(SPOTIFY_WEB_API_ADRESS)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        SpotifyService service = retrofit.create(SpotifyService.class);
        Call<Tracks> accessTokenCall = service.getTracks(url,"Bearer " + accessToken.getAccessToken());

        Callback<Tracks> spotifyDataCallback = new Callback<Tracks>() {
            @Override
            public void onResponse(Call<Tracks> call, Response<Tracks> response) {
               Tracks tracks = response.body();
                resultCallback.onTrackResult(tracks);
            }

            @Override
            public void onFailure(Call<Tracks> call, Throwable t) {
                Log.d(TAG, t.getMessage());
            }
        };

        accessTokenCall.enqueue(spotifyDataCallback);
    }
}
