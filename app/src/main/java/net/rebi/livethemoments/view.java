package net.rebi.livethemoments;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;

public class view extends AppCompatActivity {

    ConstraintLayout constraintLayout;
    int              swapNumber = 1;
    private FrameLayout camera1, camera2;

    @Override
    protected void onCreate ( Bundle savedInstanceState ) {
        super.onCreate ( savedInstanceState );
        setContentView ( R.layout.view );
        getWindow ( ).addFlags ( WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON );
        getWindow ( ).addFlags ( WindowManager.LayoutParams.FLAG_FULLSCREEN );
        getWindow ( ).addFlags ( WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN );

        camera1 = findViewById ( R.id.camera1 );
        camera2 = findViewById ( R.id.camera2 );

        getSupportFragmentManager ().beginTransaction ().add ( R.id.camera1 ,new camera ( 1) ).commit ();
        getSupportFragmentManager ().beginTransaction ().add ( R.id.camera2 ,new camera ( 0) ).commit ();


        constraintLayout = findViewById ( R.id.constraintLayout );
        ImageButton swap       = findViewById ( R.id.swap );


        swap.setOnClickListener ( new View.OnClickListener ( ) {
            @Override
            public void onClick ( View v ) {
                switch ( swapNumber ) {
                    case 1:
                        view1 ( );
                        swapNumber = 2;
                        break;
                    case 2:
                        view2 ( );
                        swapNumber = 1;

                        break;
                    case 3:
                        view3 ( );
                        swapNumber = 1;

                        break;
                }
            }


        } );


    }

    private void view1 ( ) {
        ConstraintSet constraintSet = new ConstraintSet ( );
        constraintSet.clone ( constraintLayout );
        constraintSet.connect ( R.id.camera1 , ConstraintSet.TOP , ConstraintSet.PARENT_ID ,
                                ConstraintSet.TOP , 0 );
        constraintSet.connect ( R.id.camera1 , ConstraintSet.BOTTOM , R.id.camera2 ,
                                ConstraintSet.TOP , 0 );
        constraintSet.connect ( R.id.camera1 , ConstraintSet.START , ConstraintSet.PARENT_ID ,
                                ConstraintSet.START , 0 );
        constraintSet.connect ( R.id.camera1 , ConstraintSet.END , ConstraintSet.PARENT_ID ,
                                ConstraintSet.END , 0 );

        constraintSet.connect ( R.id.camera2 , ConstraintSet.TOP , R.id.camera1 ,
                                ConstraintSet.BOTTOM , 0 );
        constraintSet.connect ( R.id.camera2 , ConstraintSet.BOTTOM , ConstraintSet.PARENT_ID ,
                                ConstraintSet.BOTTOM , 0 );
        constraintSet.connect ( R.id.camera2 , ConstraintSet.START , ConstraintSet.PARENT_ID ,
                                ConstraintSet.START , 0 );
        constraintSet.connect ( R.id.camera2 , ConstraintSet.END , ConstraintSet.PARENT_ID ,
                                ConstraintSet.END , 0 );
        constraintSet.applyTo ( constraintLayout );


    }


    private void view2 ( ) {
        ConstraintSet constraintSet = new ConstraintSet ( );
        constraintSet.clone ( constraintLayout );
        constraintSet.connect ( R.id.camera1 , ConstraintSet.TOP , R.id.camera2 ,
                                ConstraintSet.BOTTOM , 0 );
        constraintSet.connect ( R.id.camera1 , ConstraintSet.BOTTOM , ConstraintSet.PARENT_ID ,
                                ConstraintSet.BOTTOM , 0 );

        constraintSet.connect ( R.id.camera1 , ConstraintSet.START , ConstraintSet.PARENT_ID ,
                                ConstraintSet.START , 0 );
        constraintSet.connect ( R.id.camera1 , ConstraintSet.END , ConstraintSet.PARENT_ID ,
                                ConstraintSet.END , 0 );

        constraintSet.connect ( R.id.camera2 , ConstraintSet.TOP , ConstraintSet.PARENT_ID ,
                                ConstraintSet.TOP , 0 );

        constraintSet.connect ( R.id.camera2 , ConstraintSet.BOTTOM , R.id.camera2 ,
                                ConstraintSet.TOP , 0 );

        constraintSet.connect ( R.id.camera2 , ConstraintSet.START , ConstraintSet.PARENT_ID ,
                                ConstraintSet.START , 0 );
        constraintSet.connect ( R.id.camera2 , ConstraintSet.END , ConstraintSet.PARENT_ID ,
                                ConstraintSet.END , 0 );
        constraintSet.applyTo ( constraintLayout );


    }

    private void view3 ( ) {
        ConstraintSet constraintSet = new ConstraintSet ( );
        constraintSet.clone ( constraintLayout );
        constraintSet.connect ( R.id.camera1 , ConstraintSet.TOP , ConstraintSet.PARENT_ID ,
                                ConstraintSet.TOP , 0 );
        constraintSet.connect ( R.id.camera1 , ConstraintSet.BOTTOM , ConstraintSet.PARENT_ID ,
                                ConstraintSet.BOTTOM , 0 );
        constraintSet.connect ( R.id.camera1 , ConstraintSet.START , ConstraintSet.PARENT_ID ,
                                ConstraintSet.START , 0 );
        constraintSet.connect ( R.id.camera1 , ConstraintSet.END , ConstraintSet.PARENT_ID ,
                                ConstraintSet.END , 0 );

//        constraintSet.clear ( R.id.camera2 );

        ViewGroup.LayoutParams params = camera2.getLayoutParams ( );
        params.height = 100;
        params.width = 100;
        camera2.setLayoutParams ( params );


        constraintSet.connect ( R.id.camera2 , ConstraintSet.TOP , ConstraintSet.PARENT_ID ,
                                ConstraintSet.TOP , 0 );

        constraintSet.connect ( R.id.camera2 , ConstraintSet.START , ConstraintSet.PARENT_ID ,
                                ConstraintSet.START , 0 );

        constraintSet.connect ( R.id.camera2 , ConstraintSet.BOTTOM , ConstraintSet.PARENT_ID ,
                                ConstraintSet.BOTTOM , 0 );
        constraintSet.connect ( R.id.camera2 , ConstraintSet.END , ConstraintSet.PARENT_ID ,
                                ConstraintSet.END , 0 );

        constraintSet.applyTo ( constraintLayout );


    }

}
