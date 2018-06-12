package yohan.bakingapp.com.bakingapp_nanodegree;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by Yohan on 21/05/2018.
 */

public class Activity_Detailed_Steps extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment_steps);

        if (!isOnline()) {
            new AlertDialog.Builder(this)
                    .setTitle(R.string.no_internet_text)
                    .setCancelable(false)
                    .setMessage("Oops, internet signal was lost, please try again")
                    .setIcon(R.drawable.ic_signal_cellular_connected_no_internet_0_bar_black_24dp)
                    .setPositiveButton("Go to Connection Settings", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {

                            Intent intent = new Intent(Settings.ACTION_WIRELESS_SETTINGS);
                            startActivity(intent);

                            dialog.cancel();
                        }
                    }).show();
        }

        if (savedInstanceState != null) {
            return;
        }

        Fragment_Detailed_Steps fragmentDetailedSteps = new Fragment_Detailed_Steps();
        fragmentDetailedSteps.setArguments(getIntent().getBundleExtra("steps"));

        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .add(R.id.recipe_step_detail_framelayout, fragmentDetailedSteps)
                .commit();
    }



    private boolean isOnline() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        assert cm != null;
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnected();
    }
}
