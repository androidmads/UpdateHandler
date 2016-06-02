package androidmads.updatehandler.app;

import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;

import androidmads.updatehandler.app.app.Config;
import androidmads.updatehandler.app.helper.Alert;
import androidmads.updatehandler.app.helper.Comparator;
import androidmads.updatehandler.app.helper.InternetDetector;
import androidmads.updatehandler.app.manager.PrefManager;

public class UpdateHandler {

    AppCompatActivity activity;
    RequestQueue queue;
    public static String TAG = UpdateHandler.class.getName();
    String[] removingUnUsefulTags;
    Alert alert;
    PrefManager prefManager;

    public UpdateHandler(AppCompatActivity activity) {
        this.activity = activity;
        alert = new Alert(activity);
        prefManager = new PrefManager(activity);
        queue = Volley.newRequestQueue(activity);
    }

    public void start() {

        Log.v(TAG, activity.getPackageName());

        if (new InternetDetector(activity).isConnectingToInternet()) {
            StringRequest request = new StringRequest(Request.Method.GET, Config.PLAY_STORE_ROOT_URL + activity.getPackageName(),
                    new com.android.volley.Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {
                                Log.v("response", response);
                                InputStream is = new ByteArrayInputStream(response.getBytes());
                                BufferedReader reader = new BufferedReader(new InputStreamReader(is));
                                String line;
                                while ((line = reader.readLine()) != null) {
                                    if (line.contains(Config.PLAY_STORE_HTML_TAGS_TO_GET_RIGHT_POSITION)) { // Obtain HTML line contaning version available in Play Store
                                        String containingVersion = line.substring(line.lastIndexOf(Config.PLAY_STORE_HTML_TAGS_TO_GET_RIGHT_POSITION) + 28);  // Get the String starting with version available + Other HTML tags
                                        removingUnUsefulTags = containingVersion.split(Config.PLAY_STORE_HTML_TAGS_TO_REMOVE_USELESS_CONTENT); // Remove useless HTML tags
                                        Log.v(TAG, removingUnUsefulTags[0]); // Obtain version available
                                        if (Comparator.isVersionNewer(activity, removingUnUsefulTags[0])) {
                                            Log.v(TAG, String.valueOf(prefManager.getCount()));
                                            if (prefManager.getCount() == 0) {
                                                alert.showDialog();
                                            }
                                            prefManager.setCount();
                                        }
                                        Log.v(TAG, String.valueOf(Comparator.isVersionNewer(activity, removingUnUsefulTags[0])));
                                    } else if (line.contains(Config.PLAY_STORE_PACKAGE_NOT_PUBLISHED_IDENTIFIER)) { // This packages has not been found in Play Store
                                        Log.v(TAG, Config.PLAY_STORE_PACKAGE_NOT_PUBLISHED_IDENTIFIER);
                                    }
                                }
                            } catch (Exception e) {
                                Log.v("Exception", e.toString());
                            }
                        }
                    }, new com.android.volley.Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.v("Error", error.toString());
                }
            });
            request.setRetryPolicy(new DefaultRetryPolicy(
                    50000,
                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
            queue.add(request);
        } else {
            Log.v(TAG, "Connection Error");
        }
    }

    public void setCount(int count) {
        prefManager.setPref(count);
    }

}
