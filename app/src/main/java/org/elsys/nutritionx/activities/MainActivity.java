package org.elsys.nutritionx.activities;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.fatsecret.platform.model.CompactFood;
import com.fatsecret.platform.model.Food;
import com.fatsecret.platform.model.Serving;
import com.fatsecret.platform.services.android.Request;
import com.fatsecret.platform.services.android.ResponseListener;
import com.otaliastudios.autocomplete.Autocomplete;
import com.otaliastudios.autocomplete.AutocompleteCallback;
import com.otaliastudios.autocomplete.AutocompletePresenter;

import org.elsys.nutritionx.R;
import org.elsys.nutritionx.presenters.FoodPresenter;
import org.elsys.nutritionx.services.FoodService;

import java.io.IOException;
import java.util.Locale;
import java.util.Optional;

import okhttp3.OkHttpClient;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {

    private String mServerAddress;

    private FoodService mFoodService;

    private Autocomplete mFoodAutoComplete;
    private TextView mEnterFoodLabel;
    private EditText mEnterFoodEditText;
    private TextView mSummaryText;
    private TextView mProteinText;
    private TextView mCarbsText;
    private TextView mFatsText;
    private Button mMeasureButton;

    private String mFoodName;
    private Float mProtein;
    private Float mCarbs;
    private Float mFats;
    private Boolean mUpdated = true;

    private OkHttpClient mClient;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        Bundle bundle = getIntent().getExtras();

        if (bundle != null) {
            mServerAddress = bundle.getString("serverAddress");
        }

        mClient = new OkHttpClient();


        mFoodService = new FoodService(this, "5cb365f9eb8c44af82af28216dd142ce", "d71d4d49329f4048a4e808737d3b7a74");

        mEnterFoodLabel = findViewById(R.id.enter_food_label);
        mEnterFoodEditText = findViewById(R.id.enter_food_edit_text);
        mSummaryText = findViewById(R.id.summary_text);
        mProteinText = findViewById(R.id.protein_text);
        mCarbsText = findViewById(R.id.carbs_text);
        mFatsText = findViewById(R.id.fats_text);
        mMeasureButton = findViewById(R.id.measure_button);

        mMeasureButton.setOnClickListener(view -> {
            try {
                updateNutrition();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });

        Drawable backgroundDrawable = new ColorDrawable(Color.WHITE);
        float elevation = 2f;

        AutocompletePresenter<CompactFood> foodPresenter = new FoodPresenter(this, mFoodService);

        AutocompleteCallback<CompactFood> callback = new AutocompleteCallback<CompactFood>() {
            @Override
            public boolean onPopupItemClicked(Editable editable, CompactFood item) {
                editable.clear();
                editable.append(item.getName());

                SingleFoodListener listener = new SingleFoodListener();
                Request singleFoodRequest = mFoodService.newRequest(listener);
                singleFoodRequest.getFood(mFoodService.getRequestQueue(), item.getId());
                return true;
            }

            @Override
            public void onPopupVisibilityChanged(boolean shown) { }
        };

        mFoodAutoComplete = Autocomplete.<CompactFood>on(mEnterFoodEditText)
                .with(new Autocomplete.SimplePolicy())
                .with(elevation)
                .with(backgroundDrawable)
                .with(foodPresenter)
                .with(callback)
                .build();
    }

    private void updateNutrition() throws IOException {
        if (mUpdated) {
            return;
        }

        okhttp3.Request request = new okhttp3.Request.Builder()
                .url(String.format("http://%s/measure", mServerAddress))
                .build();

        Response response = mClient.newCall(request).execute();

        float grams = Integer.valueOf(response.body().string());
        mSummaryText.setText(String.format(Locale.US, "Nutrition for %.2fg of %s", grams, mFoodName));
        mProteinText.setText(String.format("Protein: %sg", grams * mProtein));
        mCarbsText.setText(String.format("Carbs: %sg", grams * mCarbs));
        mFatsText.setText(String.format("Fats: %sg", grams * mFats));

        mUpdated = true;
    }

    class SingleFoodListener implements ResponseListener {
        @Override
        public void onFoodResponse(Food food) {
            mUpdated = false;

            Optional<Serving> standardServing = food.getServings().stream()
                    .filter(s -> s.getNumberOfUnits().toPlainString().equals("100.000"))
                    .findFirst();

            standardServing.ifPresent(serving -> {
                mFoodName = food.getName();

                mSummaryText.setText(String.format("Nutrition for 100g of %s", mFoodName));

                mProteinText.setText(String.format("Protein: %sg", serving.getProtein()));
                mProtein = Float.valueOf(serving.getProtein().toString()) / 100f;

                mCarbsText.setText(String.format("Carbs: %sg", serving.getCarbohydrate()));
                mCarbs = Float.valueOf(serving.getCarbohydrate().toString()) / 100f;

                mFatsText.setText(String.format("Fats: %sg", serving.getFat()));
                mFats = Float.valueOf(serving.getFat().toString()) / 100f;
            });
        }
    }
}