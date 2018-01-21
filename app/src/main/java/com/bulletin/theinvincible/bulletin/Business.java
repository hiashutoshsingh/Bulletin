package com.bulletin.theinvincible.bulletin;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class Business extends Fragment {

    public List<ModelString> businessNews = new ArrayList<>();
    ModelString modelString;
    private RecyclerView recyclerView;

    public Business() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_business, container, false);
        recyclerView = (RecyclerView) view.findViewById(R.id.business_recycler_view);

        FetchLists f = new FetchLists();
        f.execute(10, 0);
        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try {
        } catch (ClassCastException e) {
            throw new ClassCastException("implemented the methods");
        }
    }



    public class FetchLists extends AsyncTask<Integer, Void, List<ModelString>> {

        @Override
        protected List<ModelString> doInBackground(Integer... params) {

            int count = params[0];
            int offset = params[1];

            String urlString = "https://newsapi.org/v1/articles?source=bloomberg&sortBy=top&apiKey=50e2460a44c54f4aba5a5f476ff528a8";
            urlString = urlString + "&count=" + count + "&offset=" + offset;

            try {
                URL url = new URL(urlString);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");
                InputStream stream = connection.getInputStream();
                BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
                String line = reader.readLine();
                String response = "";
                while (line != null) {
                    response += line;
                    line = reader.readLine();
                }

                JSONObject object = new JSONObject(response);
                JSONArray emailLists = object.getJSONArray("articles");

                for (int i = 0; i < emailLists.length(); i++) {
                    JSONObject listData = (JSONObject) emailLists.get(i);

                    modelString = new ModelString();
                    modelString.authorName = listData.getString("author");
                    modelString.headline = listData.getString("title");
                    modelString.publishedTime = listData.getString("publishedAt");
                    modelString.newsDetail = listData.getString("description");

                    businessNews.add(modelString);
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
            return businessNews;
        }

        @Override
        protected void onPostExecute(List<ModelString> result) {
            super.onPostExecute(result);

            recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
            BusinessAdapter adapter = new BusinessAdapter(Business.this, result);
            recyclerView.setAdapter(adapter);

        }
    }

    public class BusinessAdapter extends RecyclerView.Adapter<BusinessHolder> {

        int prevposition = 0;
        private List<ModelString> c;

        public BusinessAdapter(Business context, List<ModelString> result) {
            c = context.businessNews;

        }

        @Override
        public BusinessHolder onCreateViewHolder(ViewGroup parent, int viewType) {

            Context context = parent.getContext();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View view = inflater.inflate(R.layout.layout_news, parent, false);
            return new BusinessHolder(view);
        }

        @Override
        public void onBindViewHolder(BusinessHolder holder, int position) {

            ModelString m = c.get(position);
            holder.bindListName(m, position);


            if (position > prevposition) {

                AnimationClass.animate(holder, true);
            } else {
                AnimationClass.animate(holder, false);

            }
        }

        @Override
        public int getItemCount() {
            return c.size();
        }
    }

    public class BusinessHolder extends RecyclerView.ViewHolder {

        public TextView headlineTextview;
        public TextView authorTextview;
        public TextView timeTextview;

        public BusinessHolder(final View itemView) {
            super(itemView);

            headlineTextview = (TextView) itemView.findViewById(R.id.id_headline);
            authorTextview = (TextView) itemView.findViewById(R.id.id_author);
            timeTextview = (TextView) itemView.findViewById(R.id.id_time);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

              String author=authorTextview.getText().toString().trim();
              String headline=headlineTextview.getText().toString().trim();
              String time=timeTextview.getText().toString().trim();

              BusinessDetail businessDetail=new BusinessDetail();
              Bundle bundle=new Bundle();
              bundle.putString("author",author);
              bundle.putString("headline",headline);
              bundle.putString("time",time);
              businessDetail.setArguments(bundle);
              getFragmentManager().beginTransaction().add(R.id.content_frame,businessDetail).addToBackStack(null).commit();


                }
            });

        }

        public void bindListName(final ModelString modelString, final int position) {

            headlineTextview.setText(modelString.headline);
            authorTextview.setText(modelString.authorName);
            timeTextview.setText(modelString.publishedTime);

        }
    }


}
