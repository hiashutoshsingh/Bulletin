package com.bulletin.theinvincible.bulletin;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class BusinessDetail extends Fragment {


    public TextView authorSecond;
    StringList stringList;
    private TextView headlineSecond;
    private TextView detailsSecond;


    public BusinessDetail() {
//        BusinessDetail businessDetail=new BusinessDetail(bundle);
//        businessDetail.setArguments(bundle);
//        return businessDetail;
    }



    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        stringList = (StringList) bundle.getParcelable("news");
        Log.d("ashu","see this "+stringList.authorName);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_business_detail, container, false);
        return view;
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        headlineSecond = (TextView) view.findViewById(R.id.id_headline_second);
        authorSecond = (TextView) view.findViewById(R.id.id_author_second);
        detailsSecond = (TextView) view.findViewById(R.id.id_details_second);

        Log.d("ashu","see this again+ "+stringList.authorName);

//        Bundle bundle = getArguments();
//
//        if (bundle == null || !bundle.containsKey("businessnews")) {
//            throw new IllegalArgumentException("stringlist should not be null");
//        }
//        StringList stringList = bundle.getParcelable("businessnews");
//        StringList stringList = ((MainActivity) getActivity()).getStringList();
//        Log.d("ashu", "stringlist value business detail" + stringList);


        authorSecond.setText(stringList.authorName);
        headlineSecond.setText(stringList.headline);
        detailsSecond.setText(stringList.newsDetail);
    }


}




