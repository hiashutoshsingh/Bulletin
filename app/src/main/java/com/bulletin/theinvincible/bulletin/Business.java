package com.bulletin.theinvincible.bulletin;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
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

public class Business extends Fragment  {

    public List<StringList> businessNews = new ArrayList<>();
    TransferValue SendData;
    private RecyclerView recyclerView;

    public Business() {
        Log.d("ashu", "default constructor is found: ");

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
            SendData = (TransferValue) context;
        } catch (ClassCastException e) {
            Log.d("ashu", "implement the methods");
            throw new ClassCastException("implemented the methods");
        }
    }

    public interface TransferValue {

        public void sendValue(StringList value, int positionValue);

    }

    public class FetchLists extends AsyncTask<Integer, Void, List<StringList>> {

        @Override
        protected List<StringList> doInBackground(Integer... params) {

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

                    StringList stringList = new StringList();
                    stringList.authorName = listData.getString("author");
                    stringList.headline = listData.getString("title");
                    stringList.publishedTime = listData.getString("publishedAt");
                    stringList.newsDetail = listData.getString("description");

                    businessNews.add(stringList);
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
            return businessNews;
        }

        @Override
        protected void onPostExecute(List<StringList> result) {
            super.onPostExecute(result);

            recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
            BusinessAdapter adapter = new BusinessAdapter(Business.this, result);
            recyclerView.setAdapter(adapter);

        }
    }

    public class BusinessAdapter extends RecyclerView.Adapter<BusinessHolder> {

        int prevposition = 0;
        private List<StringList> c;

        public BusinessAdapter(Business context, List<StringList> result) {
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

            StringList m = c.get(position);
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

        public BusinessHolder(View itemView) {
            super(itemView);

            headlineTextview = (TextView) itemView.findViewById(R.id.id_headline);
            authorTextview = (TextView) itemView.findViewById(R.id.id_author);
            timeTextview = (TextView) itemView.findViewById(R.id.id_time);

        }

        public void bindListName(final StringList stringList, final int position) {

            headlineTextview.setText(stringList.headline);
            authorTextview.setText(stringList.authorName);
            timeTextview.setText(stringList.publishedTime);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    Log.d("ashu", "Business author name is: " + stringList.authorName);
                    Log.d("ashu", "business position is: " + position);

                    SendData.sendValue(stringList, position);

//                    Business business = new Business();
//                    Bundle bundle = new Bundle();
//                    bundle.putString("news", stringList.authorName);
//                    business.setArguments(bundle);

                    BusinessDetail s = new BusinessDetail();
                    FragmentTransaction transaction = getFragmentManager().beginTransaction();
                    transaction.replace(R.id.content_frame, s);
                    transaction.addToBackStack(null);
                    transaction.commit();
                }
            });
        }
    }


}
