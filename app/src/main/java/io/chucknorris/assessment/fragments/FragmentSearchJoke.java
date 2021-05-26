package io.chucknorris.assessment.fragments;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import io.chucknorris.assessment.R;
import io.chucknorris.assessment.adapter.JokeAdapter;
import io.chucknorris.assessment.models.Joke;
import io.chucknorris.assessment.models.JokeResponse;
import io.chucknorris.assessment.retrofit.RetrofitClient;
import io.chucknorris.assessment.service.JokeService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Project Name - chucknorris-jokes
 * Created by Joesta, on 2021/05/26 at 7:20 PM
 */
public class FragmentSearchJoke extends Fragment {

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private Context mContext;

    public FragmentSearchJoke() {
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = getContext();

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_search_joke, container, false);
        initUI(view);

        setHasOptionsMenu(true);

        searchJoke("test");
        return view;
    }

    private void initUI(View view) {
        mRecyclerView = view.findViewById(R.id.recyclerView);
        // initializing layout manager for recycler view
        mLayoutManager = new LinearLayoutManager(mContext, RecyclerView.VERTICAL, false);
        mRecyclerView.setLayoutManager(mLayoutManager);
    }

    private void searchJoke(String query) {
        RetrofitClient
                .getInstance()
                .create(JokeService.class)
                .searchJoke(query)
                .enqueue(
                        new Callback<JokeResponse>() {
                            @Override
                            public void onResponse(Call<JokeResponse> call, Response<JokeResponse> response) {
                                if (response.isSuccessful()) {
                                    JokeResponse jokeResponse = response.body();

                                    List<Joke> jokes = jokeResponse.jokes;

                                    Log.d("searched Jokes", jokes.toString());

                                    mAdapter = new JokeAdapter(mContext, jokes);
                                    mRecyclerView.setAdapter(mAdapter);
                                    mAdapter.notifyDataSetChanged();
                                }
                            }

                            @Override
                            public void onFailure(Call<JokeResponse> call, Throwable t) {

                            }
                        }
                );
    }

}
