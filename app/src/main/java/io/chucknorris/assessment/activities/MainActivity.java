package io.chucknorris.assessment.activities;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import io.chucknorris.assessment.R;
import io.chucknorris.assessment.adapter.JokeAdapter;
import io.chucknorris.assessment.models.Joke;
import io.chucknorris.assessment.retrofit.RetrofitClient;
import io.chucknorris.assessment.service.JokeService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initUI();
        getJokes();
    }

    private void getJokes() {
        RetrofitClient.getInstance()
                .create(JokeService.class)
                .getRandomJoke()
                .enqueue(new Callback<Joke>() {
                    @Override
                    public void onResponse(Call<Joke> call, Response<Joke> response) {
                        if (response.isSuccessful()) {
                            Joke joke = response.body();
                            List<Joke> jokes = new ArrayList<>();
                            jokes.add(joke);
                            mAdapter = new JokeAdapter(MainActivity.this, jokes);
                            recyclerView.setAdapter(mAdapter);
                        }
                    }

                    @Override
                    public void onFailure(Call<Joke> call, Throwable t) {
                        t.printStackTrace();
                    }
                });
    }

    private void initUI() {
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);

        // initializing layout manager for recycler view
        layoutManager = new LinearLayoutManager(MainActivity.this, RecyclerView.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
    }
}
