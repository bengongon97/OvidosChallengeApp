package com.example.menes.bune;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface GetDataService {

    @GET("/albums")
    Call<List<RetroAlbum>> getAllAlbums();
    @GET("/albums/{id}/photos")
    Call<List<RetroPhoto>> getAlbumPhotos(@Path("id") Integer groupId);
}