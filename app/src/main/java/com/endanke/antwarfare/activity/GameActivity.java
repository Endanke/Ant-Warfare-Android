package com.endanke.antwarfare.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

import com.endanke.antwarfare.R;
import com.endanke.antwarfare.view.BackgroundView;

/**
 * Created by endankehome on 14. 10. 01..
 */
public class GameActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        // setContentView(R.layout.game_layout);
        //setContentView(new BackgroundView(this, null));
    }

}
