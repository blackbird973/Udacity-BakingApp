package yohan.bakingapp.com.bakingapp_nanodegree;

import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Handler;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by Yohan on 21/05/2018.
 */

public class Fragment_Ingredients extends Fragment {

    private RecyclerView recyclerView;
    private GridLayoutManager layoutManager;

    private final String KEY_RECYCLER_STATE = "recycler_state";
    private static Bundle mBundleRecyclerViewState;
    private Parcelable mListState = null;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_ingredients, container, false);

        recyclerView = rootView.findViewById(R.id.recipe_ingredients_recycler_view);
        layoutManager = new GridLayoutManager(getContext(), 1);
        Adapter_Ingredients adapterIngredients = new Adapter_Ingredients(getContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapterIngredients);
        List<Ingredients> ingredients;

        Bundle bundle = this.getArguments();
        if (bundle != null) {
            ingredients = bundle.getParcelableArrayList("ingredients");
            adapterIngredients.setIngredientsList(ingredients);
        }

        return rootView;
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);

        if (mBundleRecyclerViewState != null) {
            new Handler().postDelayed(new Runnable() {

                @Override
                public void run() {
                    mListState = mBundleRecyclerViewState.getParcelable(KEY_RECYCLER_STATE);
                    recyclerView.getLayoutManager().onRestoreInstanceState(mListState);
                }
            }, 50);
        }

        layoutManager.setSpanCount(2);
        recyclerView.setLayoutManager(layoutManager);
    }

    @Override
    public void onPause() {
        super.onPause();
        mBundleRecyclerViewState = new Bundle();
        mListState = recyclerView.getLayoutManager().onSaveInstanceState();
        mBundleRecyclerViewState.putParcelable(KEY_RECYCLER_STATE, mListState);
    }
}