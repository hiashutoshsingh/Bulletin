package com.bulletin.theinvincible.bulletin;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
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


public class Entertainment extends Fragment {


    public List<StringList> entertainmentNews = new ArrayList<>();
    private RecyclerView recyclerView;

    public Entertainment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_entertainment, container, false);
        recyclerView = (RecyclerView) view.findViewById(R.id.entertainment_recycler_view);

        FetchLists f = new FetchLists();
        f.execute(10, 0);
        return view;
    }

    public class FetchLists extends AsyncTask<Integer, Void, List<StringList>> {

        @Override
        protected List<StringList> doInBackground(Integer... params) {

            int count = params[0];
            int offset = params[1];

            String urlString = "https://newsapi.org/v1/articles?source=buzzfeed&sortBy=top&apiKey=50e2460a44c54f4aba5a5f476ff528a8";
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

                    entertainmentNews.add(stringList);
                }

            } catch (Exception e) {
                e.printStackTrace();

            }

            return entertainmentNews;
        }

        @Override
        protected void onPostExecute(List<StringList> result) {
            super.onPostExecute(result);

            recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
            EntertainmentAdapter adapter = new EntertainmentAdapter(Entertainment.this, result);
            recyclerView.setAdapter(adapter);

        }
    }

    public class EntertainmentAdapter extends RecyclerView.Adapter<EntertainmentHolder> {

        int prevposition = 0;
        private List<StringList> c;

        public EntertainmentAdapter(Entertainment context, List<StringList> result) {
            c = context.entertainmentNews;

        }

        @Override
        public EntertainmentHolder onCreateViewHolder(ViewGroup parent, int viewType) {

            Context context = parent.getContext();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View view = inflater.inflate(R.layout.layout_news, parent, false);

            return new EntertainmentHolder(view);
        }

        @Override
        public void onBindViewHolder(EntertainmentHolder holder, int position) {

            StringList m = c.get(position);
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


    public class EntertainmentHolder extends RecyclerView.ViewHolder {

        public TextView headlineTextview;
        public TextView authorTextview;
        public TextView timeTextview;
        private StringList chimpList;

        public EntertainmentHolder(View itemView) {
            super(itemView);

            headlineTextview = (TextView) itemView.findViewById(R.id.id_headline);
            authorTextview = (TextView) itemView.findViewById(R.id.id_author);
            timeTextview = (TextView) itemView.findViewById(R.id.id_time);

//            itemView.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//
//                    BusinessDetail s = new BusinessDetail();
//
//                    FragmentTransaction transaction = getFragmentManager().beginTransaction();
//                    transaction.replace(R.id.content_frame, s);
//                    transaction.addToBackStack(null);
//                    transaction.commit();
//
//
//                }
//            });

        }

        public void bindListName(StringList stringList) {

            chimpList = stringList;
            headlineTextview.setText(stringList.headline);
            authorTextview.setText(stringList.authorName);
            timeTextview.setText(stringList.publishedTime);

        }


    }

}
