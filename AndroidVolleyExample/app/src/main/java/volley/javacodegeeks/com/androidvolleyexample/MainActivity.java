package volley.javacodegeeks.com.androidvolleyexample;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

public class MainActivity extends AppCompatActivity {
    OkHttpClient client = new OkHttpClient();

    Request request = new Request.Builder()
            .url("https://api.yelp.com/v3/businesses/search?term=jackbox&latitude=33.9188589&longitude=-118.3483256")
            .get()
            .addHeader("Authorization", "Bearer,Bearer 2LDFBclwVmzMFhe11RbTXELWmIkv74zZl3cFQGGV_zLOUZDtLE6-plW5O4AStCHBv-8XTpxNKDSyCOwAQkyjdbRKLRa7EmmCY2OgUkEUbI9JApedJuNsAi80ofppXXYx")
            .addHeader("cache-control", "no-cache")
            .addHeader("Postman-Token", "249ffbaa-4ea0-4e02-b8d6-77159d91740b")
            .build();

    Response response = client.newCall(request).execute();





    private TextView txtShowTextResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txtShowTextResult = findViewById(R.id.txtDisplay);

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        final String url = "https://api.yelp.com/v3/businesses/search?term=jackbox&latitude=33.9188589&longitude=-118.3483256";

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                try {
                    StringBuilder formattedResult = new StringBuilder();
                    JSONArray responseJSONArray = response.getJSONArray("businesses");
                    for (int i = 0; i < responseJSONArray.length(); i++) {
                        formattedResult.append("\n" + responseJSONArray.getJSONObject(i).get("name") + "=> \t" + responseJSONArray.getJSONObject(i).get("rating"));
                    }
                    txtShowTextResult.setText("List of Restaurants \n" + " Name" + "\t Rating \n" + formattedResult);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                findViewById(R.id.progressBar).setVisibility(View.GONE);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                txtShowTextResult.setText("An Error occured while making the request");
            }
        });
        requestQueue.add(jsonObjectRequest);
    }
}
