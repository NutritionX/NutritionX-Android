package org.elsys.nutritionx;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Spannable;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.otaliastudios.autocomplete.Autocomplete;
import com.otaliastudios.autocomplete.AutocompletePolicy;
import com.otaliastudios.autocomplete.AutocompletePresenter;
import com.otaliastudios.autocomplete.CharPolicy;

import org.elsys.nutritionx.models.Food;
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
        float elevation = 6f;
        AutocompletePresenter<Food> foodPresenter = new FoodPresenter(this);

        mFoodAutoComplete = Autocomplete.<Food>on(mEnterFoodEditText)
                .with(new Autocomplete.SimplePolicy())
                .with(elevation)
                .with(backgroundDrawable)
                .with(foodPresenter)
                .build();
    }
}
