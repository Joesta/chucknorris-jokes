package io.chucknorris.assessment.service;

import java.util.Map;

import io.chucknorris.assessment.models.Joke;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.QueryMap;

/**
 * Project Name - chucknorris-jokes
 * Created by Joesta, on 2021/05/24 at 8:40 PM
 */
public interface JokeService {

    @GET("jokes/random")
    Call<Joke> getRandomJoke();

    @GET("jokes/random")
    Call<Joke> getJokesByCategory(@QueryMap Map<String, String> queryOptions);

}
