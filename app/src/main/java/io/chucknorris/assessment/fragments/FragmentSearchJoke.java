package io.chucknorris.assessment.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

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
import io.chucknorris.assessment.util.KeyboardUtil;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Project Name - chucknorris-jokes
 * Created by Joesta, on 2021/05/26 at 7:20 PM
 */
public class FragmentSearchJoke extends Fragment implements View.OnClickListener {

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private Activity mActivity;
    private ImageButton btnSearch;
    private EditText etSearch;

    public FragmentSearchJoke() {
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivity = getActivity();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_search_joke, container, false);
        initUI(view);

        setHasOptionsMenu(true);
        return view;
    }

    private void initUI(View view) {
        mRecyclerView = view.findViewById(R.id.recyclerView);
        etSearch = view.findViewById(R.id.et_search);
        btnSearch = view.findViewById(R.id.btn_search);
        btnSearch.setOnClickListener(this);

        // initializing layout manager for recycler view
        mLayoutManager = new LinearLayoutManager(mActivity, RecyclerView.VERTICAL, false);
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

                                    if (jokes.isEmpty())
                                        Toast.makeText(getContext(), "No results found for " + query, Toast.LENGTH_LONG).show();

                                    Log.d("searched Jokes", jokes.toString());

                                    mAdapter = new JokeAdapter(mActivity, jokes);
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

    private void clearSearchText() {
        etSearch.setText("");
    }

    @Override
    public void onClick(View view) {
        final String query = etSearch.getText().toString();

        // search joke
        searchJoke(query);

        // hide soft keyboard
        KeyboardUtil.hideKeyboard(mActivity);

        // clear search query/text after search
        clearSearchText();
    }
}
