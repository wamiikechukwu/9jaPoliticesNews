package wami.ikechukwu.kanu;

import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;

public class newsReader extends AppCompatActivity {

    String getUrl;
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_news_reader);

        getUrl = getIntent().getStringExtra("URL");

        //GET THE INSTANCE OF THE VIEW ID
        textView = findViewById(R.id.news_text);

        new experiment().execute();
    }


    //THIS PART OF THIS CODE WAS COMMENTED BECAUSE IT WOULD BE USED LATER

    public class experiment extends AsyncTask<Void, Void, String> {

        String title;
        String newUrl;

        @Override
        protected void onPreExecute() {

            if (!getUrl.contains("http")) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                    newUrl = "https://" + getUrl;
                } else {
                    newUrl = "http" + getUrl;
                }
            } else {
                newUrl = getUrl;
            }

            super.onPreExecute();

        }

        @Override
        protected String doInBackground(Void... voids) {

            try {
                Document document =
                        Jsoup.connect(newUrl).followRedirects(true).timeout(600000).get();
                Elements element = document.select("p").nextAll();


                title = element.text();

            } catch (IOException e) {
                e.printStackTrace();
            }
            return title;
        }

        @Override
        protected void onPostExecute(String title) {

            super.onPostExecute(title);
            textView.setText(title);

        }

    }


}
