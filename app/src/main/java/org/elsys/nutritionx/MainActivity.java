package org.elsys.nutritionx;

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

public class MainActivity extends AppCompatActivity {

    private TextView mEnterFoodLabel;
    private EditText mEnterFoodEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mEnterFoodLabel = findViewById(R.id.enter_food_label);
        mEnterFoodEditText = findViewById(R.id.enter_food_edit_text);

        AutocompletePolicy policy = new AutocompletePolicy() {
            @Override
            public boolean shouldShowPopup(Spannable text, int cursorPos) {
                return false;
            }

            @Override
            public boolean shouldDismissPopup(Spannable text, int cursorPos) {
                return false;
            }

            @Override
            public CharSequence getQuery(Spannable text) {
                return null;
            }

            @Override
            public void onDismiss(Spannable text) {

            }
        };

        AutocompletePresenter presenter = new AutocompletePresenter(this) {
            @Override
            protected ViewGroup getView() {
                return null;
            }

            @Override
            protected void onViewShown() {

            }

            @Override
            protected void onQuery(@Nullable CharSequence query) {

            }

            @Override
            protected void onViewHidden() {

            }
        };

        Autocomplete.on(mEnterFoodEditText)
                .with(policy)
                .with(presenter)
                .build();
    }
}
