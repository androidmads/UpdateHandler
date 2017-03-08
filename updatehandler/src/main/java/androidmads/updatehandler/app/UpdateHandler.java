package androidmads.updatehandler.app;

import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import androidmads.updatehandler.app.app.Config;
import androidmads.updatehandler.app.helper.Alert;
import androidmads.updatehandler.app.helper.Comparator;
import androidmads.updatehandler.app.helper.InternetDetector;
import androidmads.updatehandler.app.manager.PrefManager;

import static androidmads.updatehandler.app.app.Config.PLAY_STORE_HTML_TAGS_TO_GET_RIGHT_POSITION;
import static androidmads.updatehandler.app.app.Config.PLAY_STORE_HTML_TAGS_TO_REMOVE_USELESS_CONTENT;
import static androidmads.updatehandler.app.app.Config.PLAY_STORE_VARIES_W_DEVICE;

public class UpdateHandler {

    private AppCompatActivity activity;
    private RequestQueue queue;
    public static String TAG = UpdateHandler.class.getName();
    private Alert alert;
    private PrefManager prefManager;
    private boolean info = true;
    private boolean showAlert = true;
    private UpdateListener updateListener;

    public UpdateHandler(AppCompatActivity activity) {
        this.activity = activity;
        alert = new Alert(activity);
        prefManager = new PrefManager(activity);
        queue = Volley.newRequestQueue(activity);
    }

    public void setOnUpdateListener(UpdateListener updateListener) {
        this.updateListener = updateListener;
    }

    public void setCount(int count) {
        prefManager.setPref(count);
    }

    public void setWhatsNew(boolean info) {
        this.info = info;
    }

    public void showDefaultAlert(boolean showAlert) {
        this.showAlert = showAlert;
    }

    public void start() {
        try {
            if (new InternetDetector(activity).isConnectingToInternet()) {
                StringRequest request = new StringRequest(
                        Request.Method.GET,
                        Config.PLAY_STORE_ROOT_URL + activity.getPackageName(),
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                try {
                                    checker(response);
                                } catch (Exception e) {
                                    updateListener.onUpdateFound(false, "No Update");
                                }
                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        updateListener.onUpdateFound(false, "");
                    }
                });
                request.setRetryPolicy(new DefaultRetryPolicy(
                        50000,
                        DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                        DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
                queue.add(request);
            } else {
                updateListener.onUpdateFound(false, "");
            }
        } catch (Exception e) {
            updateListener.onUpdateFound(false, "");
        }
    }

    private void checker(String response) throws IOException {
        InputStream is = new ByteArrayInputStream(response.getBytes());
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        String line;
        while ((line = reader.readLine()) != null) {
            if (line.contains(PLAY_STORE_HTML_TAGS_TO_GET_RIGHT_POSITION)) {
                String containingVersion = line.substring(line.lastIndexOf(PLAY_STORE_HTML_TAGS_TO_GET_RIGHT_POSITION) + 28);
                String[] removingUnUsefulTags = containingVersion.split(PLAY_STORE_HTML_TAGS_TO_REMOVE_USELESS_CONTENT);
                if (!removingUnUsefulTags[0].toUpperCase().equals(PLAY_STORE_VARIES_W_DEVICE)) {
                    if (Comparator.isVersionNewer(activity, removingUnUsefulTags[0])) {
                        if (prefManager.getCount() == 0) {
                            if (showAlert)
                                alert.showDialog(response, info, removingUnUsefulTags[0]);
                            this.updateListener.onUpdateFound(true, alert.whatNew(response));
                        }
                        prefManager.setCount();
                    }
                }
            } else if (line.contains(Config.PLAY_STORE_PACKAGE_NOT_PUBLISHED_IDENTIFIER)) {
                Log.v(TAG, Config.PLAY_STORE_PACKAGE_NOT_PUBLISHED_IDENTIFIER);
                this.updateListener.onUpdateFound(false, "");
            }
        }
    }

}
