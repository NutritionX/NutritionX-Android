package org.elsys.nutritionx.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import org.elsys.nutritionx.R;
import org.elsys.nutritionx.callbacks.Callback;
import org.elsys.nutritionx.tasks.NetworkSniffTask;

public class ConnectActivity extends AppCompatActivity {

    private View mLoader;
    private TextView mMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_connect);

        mLoader = findViewById(R.id.loader);
        mMessage = findViewById(R.id.message);

        new NetworkSniffTask(this).execute((Callback) ipAddress -> {
            if (ipAddress != null) {
                Intent intent = new Intent(ConnectActivity.this, MainActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("serverAddress", (String) ipAddress);
                intent.putExtras(bundle);
                startActivity(intent);
                finish();
            } else {
                mLoader.setVisibility(View.GONE);
                mMessage.setText(R.string.unable_to_connect);
            }
        });
    }
}
