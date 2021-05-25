package io.chucknorris.assessment.retrofit;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import io.chucknorris.assessment.Constants;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Project Name - chucknorris-jokes
 * Created by Joesta, on 2021/05/24 at 9:12 PM
 */
public class RetrofitClient {
    private static Retrofit instance;

    public static Retrofit getInstance() {
        if (instance == null) {

            Gson gson = new GsonBuilder()
                    .setDateFormat("yyyy-MM-dd")
                    .create();

            instance = new retrofit2.Retrofit.Builder()
                    .baseUrl(Constants.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build();
        }
        return instance;
    }
}
