package wami.ikechukwu.kanu;
//TODO add cache functions to volley and glide

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private final String KEY_AUTHOR = "author";
    private final String KEY_TITLE = "title";
    private final String KEY_DESCRIPTION = "description";
    private final String KEY_URL = "url";
    private final String KEY_URL_TO_IMAGE = "urlToImage";
    private final String KEY_PUBLISHED_AT = "publishedAt";

    //this string is appended to the url
    String urlLink = "buhari";
    TextView mTextView;

    ArrayList<dataModel> list;

    private RecyclerView recyclerView;
    private newsAdapter mAdapter;
    private RecyclerView.LayoutManager mLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_main);


        list = new ArrayList<>();
        mTextView = findViewById(R.id.layout_text);
        recyclerView = findViewById(R.id.recyclerView);
        mAdapter = new newsAdapter(getApplicationContext(), list);
        mLayout = new LinearLayoutManager(this);

        recyclerView.setLayoutManager(mLayout);
        recyclerView.setAdapter(mAdapter);

        jsonParser();
    }

    private void jsonParser() {

        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading...");
        progressDialog.show();

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, "https://newsapi.org/v2/everything?q=" + urlLink + "&language=en&sortBy=publishedAt&pageSize=100&apiKey=655446a36e784e79b2b62adcad45be09", null, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {

                try {
                    JSONArray jsonArray = response.getJSONArray("articles");

                    //Using a for loop to get the object (data) in the JSON
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);

                        dataModel dataModel = new dataModel();
                        dataModel.setTitle(jsonObject.getString(KEY_AUTHOR));
                        dataModel.setImage(jsonObject.getString(KEY_URL_TO_IMAGE));
                        list.add(dataModel);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();

                }
                mAdapter.notifyDataSetChanged();
                progressDialog.dismiss();
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {

                Log.e("Volley", error.toString());
                progressDialog.dismiss();
            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(jsonObjectRequest);
    }

}
