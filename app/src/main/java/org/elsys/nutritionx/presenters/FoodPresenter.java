package org.elsys.nutritionx.presenters;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.fatsecret.platform.model.CompactFood;
import com.fatsecret.platform.services.Response;
import com.fatsecret.platform.services.android.Request;
import com.fatsecret.platform.services.android.ResponseListener;
import com.otaliastudios.autocomplete.RecyclerViewPresenter;

import org.elsys.nutritionx.R;
import org.elsys.nutritionx.services.FoodService;
import org.elsys.nutritionx.utils.Screen;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class FoodPresenter extends RecyclerViewPresenter<CompactFood> {

    private final Context mContext;
    private Adapter mAdapter;

    private FoodService mFoodService;
    private final RequestQueue mRequestQueue;
    private final Listener mListener;
    private final Request mRequest;
    private Timer mRequestTimer = new Timer();
    private boolean mTimerSet = false;

    public FoodPresenter(Context context, FoodService foodService) {
        super(context);
        this.mContext = context;
        this.mRequestQueue = Volley.newRequestQueue(mContext);
        this.mFoodService = foodService;
        this.mListener = new Listener();
        this.mRequest = mFoodService.newRequest(mListener);
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
        assert query != null;
        if (mTimerSet) {
            mRequestTimer.purge();
            mRequestTimer = new Timer();
        }
        mRequestTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                mRequest.getFoods(mRequestQueue, query.toString(), 0);
            }
        }, 1500);
        mTimerSet = true;
    }

    class Listener implements ResponseListener {
        @Override
        public void onFoodListRespone(Response<CompactFood> foods) {
            mAdapter.setData(foods.getResults());
            mAdapter.notifyDataSetChanged();
        }
    }

    class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder> {
        private List<CompactFood> foodsList = new ArrayList<>();

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new ViewHolder(LayoutInflater.from(getContext()).inflate(R.layout.food, parent, false));
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            if (!hasItems()) {
                return;
            }

            final CompactFood food = foodsList.get(position);
            holder.mFoodName.setText(food.getName());
            holder.root.setOnClickListener(view -> dispatchClick(food));
        }

        public void setData(List<CompactFood> foodsList) {
            this.foodsList.clear();
            this.foodsList.addAll(foodsList);
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
