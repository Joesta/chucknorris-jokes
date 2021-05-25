package io.chucknorris.assessment.activities;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import java.util.HashMap;
import java.util.Map;

import io.chucknorris.assessment.R;
import io.chucknorris.assessment.models.Joke;
import io.chucknorris.assessment.retrofit.RetrofitClient;
import io.chucknorris.assessment.service.JokeService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        JokeService service =
                RetrofitClient
                        .getInstance()
                        .create(JokeService.class);


        service.getRandomJoke().enqueue(new Callback<Joke>() {
            @Override
            public void onResponse(Call<Joke> call, Response<Joke> response) {
                if (response.isSuccessful()) {
                    Joke body = response.body();
                    Log.d("joke", body.toString());
                }
            }

            @Override
            public void onFailure(Call<Joke> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }
}
