package org.elsys.nutritionx.tasks;

import android.content.Context;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.AsyncTask;
import android.telecom.Call;
import android.text.format.Formatter;
import android.util.Log;

import org.elsys.nutritionx.callbacks.Callback;
import org.elsys.nutritionx.utils.Network;

import java.lang.ref.WeakReference;
import java.net.InetAddress;

public class NetworkSniffTask extends AsyncTask<Callback, Void, String> {

    private static final String TAG = "SNIFF";

    private WeakReference<Context> mContextRef;
    private Callback mCallback;

    public NetworkSniffTask(Context context) {
        mContextRef = new WeakReference<>(context);
    }

    @Override
    protected String doInBackground(Callback... callbacks) {
        mCallback = callbacks[0];

        try {
            Context context = mContextRef.get();

            if (context != null) {
                WifiManager wm = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);

                WifiInfo connectionInfo = wm.getConnectionInfo();
                int ipAddress = connectionInfo.getIpAddress();
                String ipString = Formatter.formatIpAddress(ipAddress);

                String prefix = ipString.substring(0, ipString.lastIndexOf(".") + 1);

                for (int i = 0; i < 255; i++) {
                    String testIp = prefix + String.valueOf(i);

                    InetAddress address = InetAddress.getByName(testIp);
                    boolean reachable = address.isReachable(200);

                    if (reachable) {
                        if (Network.isPortOpen(String.valueOf(testIp), 5000, 300)) {
                            Log.d("found", address + ":5000");
                            return String.valueOf(testIp) + ":5000";
                        }
                    }
                }
            }
        } catch (Throwable error) {
            Log.e(TAG, "An error has occurred.", error);
        }
        return null;
    }

    @Override
    protected void onPostExecute(String server) {
        mCallback.onDone(server);
    }
}
