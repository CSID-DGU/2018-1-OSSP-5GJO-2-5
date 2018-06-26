package com.example.alex.dg_chatbot.UI.Tutorial;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.Toast;

import com.example.alex.dg_chatbot.R;
import com.example.alex.dg_chatbot.UI.Login.RootActivity;
import com.example.alex.dg_chatbot.UI.Main.MainActivity;

import java.util.Timer;

import me.relex.circleindicator.CircleIndicator;

public class TutorialActivity extends RootActivity {

    private static final int NUM_OF_FRAGMENT = 3;
    private final long DELAY_MS = 500;
    private final long PERIOD_MS = 3000;
    private Timer timer;

    private int currentPage = 0;

    private CircleIndicator circleIndicator;
    private ViewPager tutorialViewPager;
    private MyPagerAdapter myPagerAdapter;


    public TutorialActivity() {
    }

    @Override
    protected void onStart() {
        super.onStart();
        if(getUser() != null){
            Toast.makeText(this, "로그인 되었습니다.", Toast.LENGTH_LONG).show();
            Intent intent = new Intent(TutorialActivity.this, MainActivity.class);
            startActivity(intent);
        }
    }

    @SuppressLint("ResourceAsColor")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tutorial);
        myPagerAdapter = new MyPagerAdapter(getSupportFragmentManager());
        circleIndicator = (CircleIndicator) findViewById(R.id.circleIndicator);

        tutorialViewPager = (ViewPager) findViewById(R.id.tutoViewPager);
        tutorialViewPager.setAdapter(myPagerAdapter);
        tutorialViewPager.setCurrentItem(0);

        circleIndicator.setViewPager(tutorialViewPager);
        circleIndicator.setBackgroundColor(R.color.lightOrange);
        circleIndicator.setBackgroundResource(R.color.lightOrange);
        myPagerAdapter.registerDataSetObserver(circleIndicator.getDataSetObserver());
        //auto viewpager
//        final Handler handler = new Handler();
//        final Runnable update = new Runnable() {
//            @Override
//            public void run() {
//                if(currentPage == NUM_OF_FRAGMENT){
//                    currentPage = 0;
//                }
//                tutorialViewPager.setCurrentItem(currentPage++, true);
//            }
//        };
//
//        timer = new Timer();
//        timer.schedule(new TimerTask() {
//            @Override
//            public void run() {
//                handler.post(update);
//            }
//        },DELAY_MS,PERIOD_MS);

    }

    View.OnClickListener movePageListener = new View.OnClickListener(){
        @Override
        public void onClick(View view) {
            int tag = (int) view.getTag();
            tutorialViewPager.setCurrentItem(tag);
        }
    };

    private class MyPagerAdapter extends FragmentStatePagerAdapter {

        public MyPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    return new Tuto1Fragment();
                case 1:
                    return new Tuto2Fragment();
                case 2:
                    return new Tuto3Fragment();
                default:
                    return null;

            }
        }

        @Override
        public int getCount() {
            return NUM_OF_FRAGMENT;
        }
    }
}
