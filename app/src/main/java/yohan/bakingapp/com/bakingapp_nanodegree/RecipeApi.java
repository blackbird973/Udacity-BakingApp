package yohan.bakingapp.com.bakingapp_nanodegree;


/**
 * Created by Yohan on 20/05/2018.
 */

import yohan.bakingapp.com.bakingapp_nanodegree.Recipe;
import java.util.List;
import retrofit.Callback;
import retrofit.http.GET;

public class RecipeApi {
    public interface RecipesApi {
        @GET("/topher/2017/May/59121517_baking/baking.json")
        void getRecipes(Callback<List<Recipe>> callback);
    }
}