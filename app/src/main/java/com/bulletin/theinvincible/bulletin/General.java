package com.bulletin.theinvincible.bulletin;


import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Parcel;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
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


public class General extends Fragment {

    public List<ModelString> generalNews = new ArrayList<>();
    Transfer transfer;
    ModelString modelString;
    private RecyclerView recyclerView;

    public General() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        View view = inflater.inflate(R.layout.fragment_general, container, false);
        recyclerView = (RecyclerView) view.findViewById(R.id.general_recycler_view);

        FetchLists f = new FetchLists();
        f.execute(10, 0);
        return view;

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            transfer = (Transfer) context;

        } catch (ClassCastException e) {
            throw new ClassCastException("null object refernece");

        }
    }

    public interface Transfer {
        public void senda(ModelString modelString);
    }

    public class FetchLists extends AsyncTask<Integer, Void, List<ModelString>> {

        @Override
        protected List<ModelString> doInBackground(Integer... params) {

            int count = params[0];
            int offset = params[1];

            String urlString = "https://newsapi.org/v1/articles?source=bbc-news&sortBy=top&apiKey=50e2460a44c54f4aba5a5f476ff528a8";
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
                    modelString = new ModelString(Parcel.obtain());
                    modelString.authorName = listData.getString("author");
                    modelString.headline = listData.getString("title");
                    modelString.publishedTime = listData.getString("publishedAt");

                    generalNews.add(modelString);
                }

            } catch (Exception e) {
                e.printStackTrace();

            }

            return generalNews;
        }

        @Override
        protected void onPostExecute(List<ModelString> result) {
            super.onPostExecute(result);

            recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
            GeneralAdapter adapter = new GeneralAdapter(General.this, result);
            recyclerView.setAdapter(adapter);

        }
    }

    public class GeneralAdapter extends RecyclerView.Adapter<GeneralHolder> {

        int prevposition = 0;
        private List<ModelString> c;

        public GeneralAdapter(General context, List<ModelString> result) {
            c = context.generalNews;

        }

        @Override
        public GeneralHolder onCreateViewHolder(ViewGroup parent, int viewType) {

            Context context = parent.getContext();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View view = inflater.inflate(R.layout.layout_news, parent, false);
            return new GeneralHolder(view);
        }

        @Override
        public void onBindViewHolder(GeneralHolder holder, int position) {

            ModelString m = c.get(position);
            holder.bindListName(m);

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

    public class GeneralHolder extends RecyclerView.ViewHolder {

        public TextView headlineTextview;
        public TextView authorTextview;
        public TextView timeTextview;


        public GeneralHolder(View itemView) {
            super(itemView);

            headlineTextview = (TextView) itemView.findViewById(R.id.id_headline);
            authorTextview = (TextView) itemView.findViewById(R.id.id_author);
            timeTextview = (TextView) itemView.findViewById(R.id.id_time);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    transfer.senda(modelString);
                    Log.d("ashu","general onclick stringlist value: "+ modelString);

                }
            });

        }

        public void bindListName(ModelString modelString) {

            headlineTextview.setText(modelString.headline);
            authorTextview.setText(modelString.authorName);
            timeTextview.setText(modelString.publishedTime);

        }
    }
}



