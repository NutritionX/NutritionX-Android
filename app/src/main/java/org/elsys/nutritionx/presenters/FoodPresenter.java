package org.elsys.nutritionx.presenters;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.otaliastudios.autocomplete.RecyclerViewPresenter;

import org.elsys.nutritionx.R;
import org.elsys.nutritionx.models.Food;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class FoodPresenter extends RecyclerViewPresenter<Food> {

    private Adapter adapter;

    public FoodPresenter(Context context) {
        super(context);
    }

    @Override
    protected RecyclerView.Adapter instantiateAdapter() {
        adapter = new Adapter();
        return  adapter;
    }

    @Override
    protected PopupDimensions getPopupDimensions() {
        PopupDimensions dims = new PopupDimensions();
        dims.width = 500;
        dims.height = RecyclerView.LayoutParams.WRAP_CONTENT;
        return dims;
    }

    @Override
    protected void onQuery(@Nullable CharSequence query) {
        Log.d("triggered", "onQuery");

        List<Food> foods = Food.findByName();

        if (TextUtils.isEmpty(query)) {
            adapter.setData(foods);
        } else {
            List<Food> foundFoodsList = foods.stream()
                    .filter(f -> f.getName().toLowerCase().contains(query.toString().toLowerCase()))
                    .collect(Collectors.toList());
            Log.d("foundFoodsList", foundFoodsList.toString());
            adapter.setData(foundFoodsList);
        }
        adapter.notifyDataSetChanged();
    }

    class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder> {
        private List<Food> foodsList = new ArrayList<>();

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new ViewHolder(LayoutInflater.from(getContext()).inflate(R.layout.food, parent, false));
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            if (!hasItems()) {
                return;
            }

            final Food food = foodsList.get(position);
            holder.mFoodName.setText(food.getName());
            holder.root.setOnClickListener(view -> {
                Log.d("clicked", "true");
                dispatchClick(food);
            });
        }

        public void setData(List<Food> foodsList) {
            this.foodsList = foodsList;
        }

        @Override
        public int getItemCount() {
            return foodsList.size();
        }

        private boolean hasItems() {
            return foodsList != null && !foodsList.isEmpty();
        }

        class ViewHolder extends RecyclerView.ViewHolder {
            private View root;
            private TextView mFoodName;

            public ViewHolder(View itemView) {
                super(itemView);
                root = itemView;
                mFoodName = itemView.findViewById(R.id.food_name);
            }
        }
    }
}
