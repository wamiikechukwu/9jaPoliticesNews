package wami.ikechukwu.kanu;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

public class news_detail extends AppCompatActivity {

    //TODO: REMOVE THIS LINE IF THERE IS NO NEED FOR THE AUTHOR NAME IN THE APP
    // private final String KEY_AUTHOR = "author";
    private final String KEY_TITLE = "title";
    //private final String KEY_DESCRIPTION = "description";
    private final String KEY_URL = "url";
    private final String KEY_URL_TO_IMAGE = "urlToImage";
    private final String KEY_PUBLISHED_AT = "publishedAt";
    int itemPosition;
    //this string is appended to the url
    String urlLink = "buhari";
    String url;
    TextView newsDetail_Title, newDetail_Time_Posted, newsDetail_News;
    ImageView newsDetail_Image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_detail);

        itemPosition = getIntent().getIntExtra("POSITION", 0);

        newsDetail_Title = findViewById(R.id.newsDetail_Title);
        newDetail_Time_Posted = findViewById(R.id.newDetail_Time_Posted);
        newsDetail_News = findViewById(R.id.newsDetail_News);
        newsDetail_Image = findViewById(R.id.newsDetail_Image);

        newsRequest();
        // new backgroundTask().execute();
    }

    public void newsRequest() {

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, "https://newsapi.org/v2/everything?q=" + urlLink + "&language=en&sortBy=publishedAt&pageSize=100&apiKey=a5f976b34089493abc8f97f088e5df64", null, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {

                try {
                    JSONArray jsonArray = response.getJSONArray("articles");

                    //Using a for loop to get the object (data) in the JSON
                    JSONObject jsonObject = jsonArray.getJSONObject(itemPosition);

                    newsDetail_Title.setText(jsonObject.getString(KEY_TITLE));
                    //newsDetail_News.setText(jsonObject.getString(KEY_TITLE));
                    url = jsonObject.getString(KEY_URL);
                    Glide.with(getApplicationContext()).load(jsonObject.getString(KEY_URL_TO_IMAGE)).into(newsDetail_Image);

                } catch (JSONException e) {
                    e.printStackTrace();

                }
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(jsonObjectRequest);

        new Thread(new Runnable() {

            final StringBuilder builder = new StringBuilder();
            String title;

            @Override
            public void run() {

                try {
                    Document document = Jsoup.connect("https://www.vanguardngr.com/2019/09/three-women-others-set-free-by-katsina-bandits/").get();
                    // Elements element = document.select("p");
                   /* for (Element paragraph : element) {
                        builder.append(paragraph.text());
                        Toast.makeText(getApplicationContext(), "why it didnt work",
                                Toast.LENGTH_SHORT).show();
                    }*/
                    title = document.title();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                runOnUiThread(new Runnable() {

                    @Override
                    public void run() {

                        // newsDetail_News.setText(builder.toString());
                        newsDetail_News.setText(title);
                        Toast.makeText(getApplicationContext(), "why it didnt work", Toast.LENGTH_SHORT).show();

                    }
                });
            }
        }).start();
    }

    public class backgroundTask extends AsyncTask<Void, Void, Void> {

        String text;

        @Override
        protected void onPreExecute() {

            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(Void aVoid) {

            newsDetail_News.setText(text);
            super.onPostExecute(aVoid);
        }

        @Override
        protected Void doInBackground(Void... voids) {

            try {
                Document document = Jsoup.connect(url).get();
                Elements element = document.select("p");
                for (Element paragraph : element) {
                    text = paragraph.text();
                    Toast.makeText(getApplicationContext(), "why it didnt work",
                            Toast.LENGTH_SHORT).show();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

    }

}

