package org.elsys.nutritionx.services;

import android.content.Context;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.fatsecret.platform.services.android.Request;
import com.fatsecret.platform.services.android.ResponseListener;

public class FoodService {
    private final Context context;
    private final String key;
    private final String secret;
    private final RequestQueue requestQueue;

    public FoodService(Context context, String key, String secret) {
        this.context = context;
        this.key = key;
        this.secret = secret;
        this.requestQueue = Volley.newRequestQueue(context);
    }

    public RequestQueue getRequestQueue() {
        return requestQueue;
    }

    public Request newRequest(ResponseListener listener) {
        return new Request(key, secret, listener);
    }
}
