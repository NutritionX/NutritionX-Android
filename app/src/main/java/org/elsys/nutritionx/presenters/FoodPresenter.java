package org.elsys.nutritionx.presenters;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.otaliastudios.autocomplete.RecyclerViewPresenter;

import org.elsys.nutritionx.R;
import org.elsys.nutritionx.models.Food;

import java.util.List;

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
        List<Food> foodList = Food.findByName();

        if (TextUtils.isEmpty(query)) {
            adapter.setData(foodList);
        } else {

        }
    }

    class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder> {
        private List<Food> foodList;

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new ViewHolder(LayoutInflater.from(getContext()).inflate(R.layout.food, parent, false));
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            if (!hasItems()) {
                return;
            }

            final Food food = foodList.get(position);
            holder.mFoodName.setText(food.getName());
            holder.root.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dispatchClick(food);
                }
            });
        }

        public void setData(List<Food> foodList) {
            this.foodList = foodList;
        }

        @Override
        public int getItemCount() {
            return foodList.size();
        }

        private boolean hasItems() {
            return foodList != null && !foodList.isEmpty();
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
