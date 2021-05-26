package io.chucknorris.assessment.activities;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;

import io.chucknorris.assessment.Constants;
import io.chucknorris.assessment.R;
import io.chucknorris.assessment.adapter.ViewPagerAdapter;
import io.chucknorris.assessment.fragments.FragmentRandomJoke;
import io.chucknorris.assessment.fragments.FragmentSearchJoke;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final ViewPager viewPager = findViewById(R.id.view_pager);

        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new FragmentRandomJoke(), Constants.FragmentTabTitle.RANDOM_JOKE);
        adapter.addFragment(new FragmentSearchJoke(), Constants.FragmentTabTitle.SEARCH_JOKE);

        viewPager.setAdapter(adapter);

        final TabLayout tabLayout = findViewById(R.id.tab_layout);
        tabLayout.setupWithViewPager(viewPager);
    }
}
