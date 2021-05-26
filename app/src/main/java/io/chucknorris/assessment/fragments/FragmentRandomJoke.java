package io.chucknorris.assessment.fragments;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.bumptech.glide.Glide;

import io.chucknorris.assessment.R;
import io.chucknorris.assessment.models.Joke;
import io.chucknorris.assessment.retrofit.RetrofitClient;
import io.chucknorris.assessment.service.JokeService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Project Name - chucknorris-jokes
 * Created on 2021/05/26 at 7:14 PM
 */
public class FragmentRandomJoke extends Fragment implements SwipeRefreshLayout.OnRefreshListener {

    private ImageView mChuckImage;
    private TextView mText_createdAt;
    private TextView mText_updatedAt;
    private TextView mText_category;
    private TextView mText_joke;
    private SwipeRefreshLayout mSwipeContainer;

    public FragmentRandomJoke() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_random_joke, container, false);

        initUI(view); // initializes view

        getRandomJoke();

        return view;
    }

    private void initUI(View view) {
        mChuckImage = view.findViewById(R.id.chuck_image);
        mText_createdAt = view.findViewById(R.id.text_created_at);
        mText_updatedAt = view.findViewById(R.id.text_updated_at);
        mText_category = view.findViewById(R.id.text_category);
        mText_joke = view.findViewById(R.id.text_joke);
        mSwipeContainer = view.findViewById(R.id.swipeContainer);
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

    private void showJokeDetails(Joke joke) {
        Context context = getContext();
        assert context != null;
        Glide.with(context)
                .load(joke.getIconURL())
                .into(mChuckImage);

        String category = "";
        if (!joke.getCategories().isEmpty()) {
            for (String currentCategory : joke.getCategories()) {
                category += currentCategory + "\n"; // if there are more than one category, then append the category local member
            }
        }

        if (!TextUtils.isEmpty(category))
            mText_category.setText(category);

        mText_createdAt.setText(joke.getCreatedAt().toString());
        mText_updatedAt.setText(joke.getUpdatedAt().toString());
        mText_joke.setText(joke.getValue());
    }


    private void getRandomJoke() {
        RetrofitClient
                .getInstance()
                .create(JokeService.class)
                .getRandomJoke()
                .enqueue(
                new Callback<Joke>() {
                    @Override
                    public void onResponse(Call<Joke> call, Response<Joke> response) {
                        if (response.isSuccessful()) {
                            Joke joke = response.body();
                            Log.d("randomJoke", joke.toString());

                            showJokeDetails(joke);
                        }
                    }

                    @Override
                    public void onFailure(Call<Joke> call, Throwable t) {
                        t.printStackTrace();
                    }
                }
        );
    }

    @Override
    public void onRefresh() {
        getRandomJoke();
        mSwipeContainer.setRefreshing(false);
    }
}
