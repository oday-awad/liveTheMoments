package net.rebi.livethemoments;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.WindowManager;

public class view extends AppCompatActivity {

    @Override
    protected void onCreate ( Bundle savedInstanceState ) {
        super.onCreate ( savedInstanceState );
        setContentView ( R.layout.view );
        getWindow ( ).addFlags ( WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON );
        getWindow ( ).addFlags ( WindowManager.LayoutParams.FLAG_FULLSCREEN );
    }
}
