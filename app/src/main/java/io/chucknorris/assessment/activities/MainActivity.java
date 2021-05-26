package io.chucknorris.assessment.activities;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;

import io.chucknorris.assessment.R;
import io.chucknorris.assessment.adapter.ViewPagerAdapter;
import io.chucknorris.assessment.fragments.FragmentRandomJoke;
import io.chucknorris.assessment.fragments.FragmentSearchJoke;

public class MainActivity extends AppCompatActivity {


    private TabLayout tabLayout;
    private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        viewPager = findViewById(R.id.view_pager);

        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new FragmentRandomJoke(), "Random Joke");
        adapter.addFragment(new FragmentSearchJoke(), "Search Joke");

        viewPager.setAdapter(adapter);


        tabLayout = findViewById(R.id.tab_layout);
        tabLayout.setupWithViewPager(viewPager);

    }

}
