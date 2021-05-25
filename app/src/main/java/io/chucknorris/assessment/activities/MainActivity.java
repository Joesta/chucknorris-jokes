package io.chucknorris.assessment.activities;

import android.os.Bundle;
import android.os.Handler;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

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

public class MainActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener {

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private SwipeRefreshLayout mSwipeContainer;

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
                            mRecyclerView.setAdapter(mAdapter);
                            mAdapter.notifyDataSetChanged();
                        }
                    }

                    @Override
                    public void onFailure(Call<Joke> call, Throwable t) {
                        t.printStackTrace();
                    }
                });
    }

    private void initUI() {
        mRecyclerView = findViewById(R.id.recyclerView);
        mRecyclerView.setHasFixedSize(true);

        // initializing layout manager for recycler view
        mLayoutManager = new LinearLayoutManager(MainActivity.this, RecyclerView.VERTICAL, false);
        mRecyclerView.setLayoutManager(mLayoutManager);

        mSwipeContainer = findViewById(R.id.swipeContainer);
        mSwipeContainer.setOnRefreshListener(this);
        configureSwipeContainerColors();
    }

    private void configureSwipeContainerColors() {
        // Configure the refreshing colors
        mSwipeContainer.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);
    }


    @Override
    public void onRefresh() {
        getJokes();
        mSwipeContainer.setRefreshing(false /* stop refreshing*/);

    }

}
