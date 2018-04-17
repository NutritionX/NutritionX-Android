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
import org.elsys.nutritionx.utils.Screen;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class FoodPresenter extends RecyclerViewPresenter<Food> {

    private Context mContext;
    private Adapter mAdapter;

    public FoodPresenter(Context context) {
        super(context);
        mContext = context;
    }

    @Override
    protected RecyclerView.Adapter instantiateAdapter() {
        mAdapter = new Adapter();
        return mAdapter;
    }

    @Override
    protected PopupDimensions getPopupDimensions() {
        PopupDimensions dims = new PopupDimensions();
        dims.width = (int) Screen.dpToPx(350f, mContext);
        dims.height = RecyclerView.LayoutParams.WRAP_CONTENT;
        return dims;
    }

    @Override
    protected void onQuery(@Nullable CharSequence query) {
        List<Food> foods = Food.findByName();

        if (TextUtils.isEmpty(query)) {
            mAdapter.setData(foods);
        } else {
            List<Food> foundFoodsList = foods.stream()
                    .filter(f -> f.getName().toLowerCase().contains(query.toString().toLowerCase()))
                    .collect(Collectors.toList());
            mAdapter.setData(foundFoodsList);
        }
        mAdapter.notifyDataSetChanged();
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
            holder.root.setOnClickListener(view -> dispatchClick(food));
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

            ViewHolder(View itemView) {
                super(itemView);
                root = itemView;
                mFoodName = itemView.findViewById(R.id.food_name);
            }
        }
    }
}
