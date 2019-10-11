package com.yin.lestin.lestindemo.utils.net;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by Administrator on 2017/6/1.
 */

public interface ApiInterface {
    public interface GitHubService {
        @GET("/app/user/login")
        Call<List<String>> listRepos(@Path("user") String user);
    }
}
