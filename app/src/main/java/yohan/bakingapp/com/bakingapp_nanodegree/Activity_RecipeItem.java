package yohan.bakingapp.com.bakingapp_nanodegree;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Yohan on 20/05/2018.
 */

public class Activity_RecipeItem extends AppCompatActivity {
    private List<Ingredients> ingredients;
    private List<Steps> steps;
    private Boolean mTwoPane;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_recipe);






        if (savedInstanceState != null) {
            return;
        }



        if (getIntent().getExtras().get("ingredients") != null || getIntent().getExtras().get("steps") != null) {
            ingredients = this.getIntent().getParcelableArrayListExtra("ingredients");
            steps = this.getIntent().getParcelableArrayListExtra("steps");
        } else {
            Toast.makeText(getApplicationContext(), R.string.error_data, Toast.LENGTH_LONG).show();
        }

        if (findViewById(R.id.linear_layout_tablet_holder) != null) {
            if (savedInstanceState == null) {
                Fragment_Detailed_Steps fragmentDetailedSteps = new Fragment_Detailed_Steps();
                Bundle bundle = new Bundle();
                bundle.putParcelable("steps", steps.get(0));
                fragmentDetailedSteps.setArguments(bundle);

                FragmentManager fragmentManager = getSupportFragmentManager();

                fragmentManager.beginTransaction()
                        .add(R.id.detailed_steps_fragment_holder, fragmentDetailedSteps)
                        .commit();
            }
            mTwoPane = true;
        } else {
            mTwoPane = false;
        }




        Fragment_Ingredients fragmentIngredients = new Fragment_Ingredients();
        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList("ingredients", (ArrayList<Ingredients>)ingredients);
        fragmentIngredients.setArguments(bundle);

        StepsFragment stepsFragment = new StepsFragment();
        Bundle bundle2 = new Bundle();
        bundle2.putParcelableArrayList("steps", (ArrayList<Steps>) steps);
        stepsFragment.setArguments(bundle2);


        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .add(R.id.ingredients_fragment_holder, fragmentIngredients)
                .add(R.id.steps_fragment_holder, stepsFragment)
                .commit();




        if (!isOnline()) {
            new AlertDialog.Builder(this)
                    .setTitle(R.string.no_internet_text)
                    .setCancelable(false)
                    .setMessage(R.string.title_no_internet)
                    .setIcon(R.drawable.ic_signal_cellular_connected_no_internet_0_bar_black_24dp)
                    .setPositiveButton(R.string.go_to_settings, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {

                            Intent intent = new Intent(Settings.ACTION_WIRELESS_SETTINGS);
                            startActivity(intent);

                            dialog.cancel();
                        }
                    }).show();
        }
    }

    private boolean isOnline() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        assert cm != null;
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnected();
    }
}

