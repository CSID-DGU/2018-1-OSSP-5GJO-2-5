package com.example.alex.dg_chatbot.UI.Main.home;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.alex.dg_chatbot.R;
import com.example.alex.dg_chatbot.UI.Main.MainActivity;
import com.example.alex.dg_chatbot.Util.U;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import me.relex.circleindicator.CircleIndicator;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {

    SharedPreferences sharedPreferences;

    private RecyclerView homeRecyclerview;
    private LinearLayoutManager linearLayoutManager;
    private GridLayoutManager gridLayoutManager;

    private HomeAdapter homeAdapter;

    private ArrayList<HomeModel> data = new ArrayList<>();

    private int NUM_OF_PAGES = 5;
    private final long DELAY_MS = 500;
    private final long PERIOD_MS = 3000;
    private Timer timer;

    private int currentPage = 0;


    private FrameLayout flHomeVPWrapper;
    private ViewPager viewPager;
    private CircleIndicator circleIndicator;

    private MyPagerAdapter myPagerAdapter;

    private int[] resources = {
            R.drawable.vp1,
            R.drawable.vp2,
            R.drawable.vp3,
            R.drawable.vp4,
            R.drawable.vp5
    };

    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public void onStart() {
        super.onStart();
        if(sharedPreferences.getBoolean("news",false)) data.add(new HomeModel( R.drawable.ic_news, R.drawable.txt_news, ""));
        if(sharedPreferences.getBoolean("food",false)) data.add(new HomeModel(R.drawable.ic_food, R.drawable.txt_food, ""));
        if(sharedPreferences.getBoolean("lib",false)) data.add(new HomeModel(R.drawable.ic_books, R.drawable.txt_lib, ""));
        if(sharedPreferences.getBoolean("sche",false)) data.add(new HomeModel(R.drawable.ic_sche, R.drawable.txt_sche, ""));
        if(sharedPreferences.getBoolean("job",false)) data.add(new HomeModel(R.drawable.ic_job, R.drawable.txt_job, ""));
        if(sharedPreferences.getBoolean("vol",false)) data.add(new HomeModel(R.drawable.ic_charity, R.drawable.txt_vol, ""));
        if(sharedPreferences.getBoolean("map",false)) data.add(new HomeModel(R.drawable.ic_map, R.drawable.txt_map, ""));
        if(sharedPreferences.getBoolean("timetable",false)) data.add(new HomeModel(R.drawable.ic_timetable, R.drawable.txt_timetable, ""));
        if(sharedPreferences.getBoolean("man",false)) data.add(new HomeModel(R.drawable.ic_man, R.drawable.txt_man, ""));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = (View) inflater.inflate(R.layout.fragment_home, container, false);
        flHomeVPWrapper = view.findViewById(R.id.flHomeVPWrapper);
        sharedPreferences = getActivity().getSharedPreferences("fav", Context.MODE_PRIVATE);

        myPagerAdapter = new MyPagerAdapter(view.getContext());

        viewPager = view.findViewById(R.id.homeViewPager);
        viewPager.setAdapter(myPagerAdapter);
        viewPager.setCurrentItem(0);

        circleIndicator = view.findViewById(R.id.circleIndicator);
        circleIndicator.setViewPager(viewPager);
        myPagerAdapter.registerDataSetObserver(circleIndicator.getDataSetObserver());

        final Handler handler = new Handler();
        final Runnable update = new Runnable() {
            @Override
            public void run() {
                if(currentPage == NUM_OF_PAGES){
                    currentPage = 0;
                }
                viewPager.setCurrentItem(currentPage++, true);
            }
        };

        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                handler.post(update);
            }
        },DELAY_MS,PERIOD_MS);

        homeRecyclerview = view.findViewById(R.id.homeRecyclerview);
        homeRecyclerview.setHasFixedSize(true);

        gridLayoutManager = new GridLayoutManager(getContext(), 2);

        linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        homeRecyclerview.setLayoutManager(gridLayoutManager);

        homeAdapter = new HomeAdapter(data);
        homeRecyclerview.setAdapter(homeAdapter);

        return view;
    }

    private class MyPagerAdapter extends PagerAdapter {

        Context adContext;
        LayoutInflater adLayoutInflater;

        public MyPagerAdapter(Context context) {
            adContext = context;
            adLayoutInflater = (LayoutInflater) adContext.
                    getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }


        @Override
        public int getCount() {
            return resources.length;
        }

        @Override
        public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
            return view == ((LinearLayout) object);
        }

        @NonNull
        @Override
        public Object instantiateItem(@NonNull ViewGroup container, int position) {
            View itemView = adLayoutInflater.inflate(R.layout.home_vp1, container, false);
            ImageView imageView = itemView.findViewById(R.id.vpImage);

            imageView.setImageResource(resources[position]);
            container.addView(itemView);

            return itemView;
        }

        @Override
        public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
            container.removeView((LinearLayout)object);
        }
    }

    //***********************************************
    //          RECYCLER ADAPTER & VIEW HOLDER
    //***********************************************
    private class HomeViewHolder extends RecyclerView.ViewHolder{

        ImageView ivHomeCardImg;
        ImageView ivHomeCardText;
        public HomeViewHolder(View itemView) {
            super(itemView);
            ivHomeCardImg = itemView.findViewById(R.id.ivHomeCardImg);
            ivHomeCardText = itemView.findViewById(R.id.ivHomeCardText);
        }
    }

    private class HomeAdapter extends  RecyclerView.Adapter{

        ArrayList<HomeModel> data = new ArrayList<>();

        public HomeAdapter(ArrayList<HomeModel> data) {
            this.data = data;
        }

        @NonNull
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View item = LayoutInflater.from(parent.getContext()).inflate(R.layout.home_cardview, parent, false);
            return new HomeViewHolder(item);
        }

        @Override
        public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
            HomeModel homeModel = data.get(position);
            HomeViewHolder homeViewHolder = (HomeViewHolder) holder;

            homeViewHolder.ivHomeCardText.setImageResource(homeModel.getTitle());
            homeViewHolder.ivHomeCardImg.setImageResource(homeModel.getImg());
        }

        @Override
        public int getItemCount() {
            return data.size();
        }
    }
}
