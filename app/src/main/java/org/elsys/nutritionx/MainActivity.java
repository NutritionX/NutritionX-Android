package org.elsys.nutritionx;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.widget.EditText;
import android.widget.TextView;

import com.fatsecret.platform.model.CompactFood;
import com.otaliastudios.autocomplete.Autocomplete;
import com.otaliastudios.autocomplete.AutocompleteCallback;
import com.otaliastudios.autocomplete.AutocompletePresenter;

import org.elsys.nutritionx.presenters.FoodPresenter;

public class MainActivity extends AppCompatActivity {

    private Autocomplete mFoodAutoComplete;

    private TextView mEnterFoodLabel;
    private EditText mEnterFoodEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mEnterFoodLabel = findViewById(R.id.enter_food_label);
        mEnterFoodEditText = findViewById(R.id.enter_food_edit_text);

        Drawable backgroundDrawable = new ColorDrawable(Color.WHITE);
        float elevation = 2f;

        AutocompletePresenter<CompactFood> foodPresenter = new FoodPresenter(this);

        AutocompleteCallback<CompactFood> callback = new AutocompleteCallback<CompactFood>() {
            @Override
            public boolean onPopupItemClicked(Editable editable, CompactFood item) {
                editable.clear();
                editable.append(item.getName());
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
}
