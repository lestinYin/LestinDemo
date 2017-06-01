package com.yin.lestin.lestindemo.utils.net;

import retrofit2.http.GET;

/**
 * Created by Administrator on 2017/6/1.
 */

public interface ApiInterface {
    public interface GitHubService {
        @GET("/app/user/login")
        Call<List<Repo>> listRepos(@Path("user") String user);
    }
}
