package com.bulletin.theinvincible.bulletin;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


public class GeneralDetail extends Fragment {


    public TextView authorSecond;
    StringList stringList;
    private TextView headlineSecond;
    private TextView detailsSecond;


    public GeneralDetail() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_general_detail, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        headlineSecond = (TextView) view.findViewById(R.id.id_headline_second_business);
        authorSecond = (TextView) view.findViewById(R.id.id_author_second_business);
        detailsSecond = (TextView) view.findViewById(R.id.id_details_second_business);

        Bundle bundle = getArguments();

        if (bundle == null || !bundle.containsKey("generalnews")) {
            throw new IllegalArgumentException("exception is thrown");
        }

        stringList = bundle.getParcelable("generalnews");
        Log.d("ashu", "stringlist value business detail" + stringList);
        authorSecond.setText(stringList.authorName);
        headlineSecond.setText(stringList.headline);
        detailsSecond.setText(stringList.newsDetail);
    }
}
