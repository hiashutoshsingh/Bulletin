package com.bulletin.theinvincible.bulletin;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class BusinessDetail extends Fragment {


    public TextView authorSecond;
    public TextView headlineSecond;
    public TextView detailsSecond;

    String author;
    String headline;
    String time;


    public BusinessDetail() {

    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_business_detail, container, false);

        author = getArguments().getString("author");
        headline =getArguments().getString("headline");
        time =getArguments().getString("time");


        return view;
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        headlineSecond = (TextView) view.findViewById(R.id.id_headline_second_business);
        authorSecond = (TextView) view.findViewById(R.id.id_author_second_business);
        detailsSecond = (TextView) view.findViewById(R.id.id_details_second_business);


        authorSecond.setText(author);
        headlineSecond.setText(headline);
        detailsSecond.setText(time);


    }




}




