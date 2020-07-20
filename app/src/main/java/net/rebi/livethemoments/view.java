package net.rebi.livethemoments;

import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;

public class view extends AppCompatActivity {

    ConstraintLayout linearLayout;
    int swapNumber = 1;
    private FrameLayout camera1, camera2;

    @Override
    protected void onCreate ( Bundle savedInstanceState ) {
        super.onCreate ( savedInstanceState );
        setContentView ( R.layout.view );
        getWindow ( ).addFlags ( WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON );
        getWindow ( ).addFlags ( WindowManager.LayoutParams.FLAG_FULLSCREEN );

        camera1 = findViewById ( R.id.camera1 );
        camera2 = findViewById ( R.id.camera2 );
        linearLayout = findViewById ( R.id.linearLayout );
        ImageButton swap = findViewById ( R.id.swap );


        swap.setOnClickListener ( new View.OnClickListener ( ) {
            @Override
            public void onClick ( View v ) {
                switch ( swapNumber ) {
                    case 1:
                        view1 ( );

                        break;
                    case 2:
                        view2 ( );

                        break;
                    case 3:
//                        view3 ( );

                        break;
                }
            }


        } );


    }

    private void view1 ( ) {
        ConstraintSet constraintSet = new ConstraintSet ( );
        constraintSet.clone ( linearLayout );
        constraintSet.connect ( R.id.camera1 , ConstraintSet.TOP , R.id.linearLayout ,
                                ConstraintSet.TOP , 0 );
        constraintSet.connect ( R.id.camera1 , ConstraintSet.BOTTOM , R.id.camera2 ,
                                ConstraintSet.TOP , 0 );
        constraintSet.connect ( R.id.camera1 , ConstraintSet.START , R.id.linearLayout ,
                                ConstraintSet.START , 0 );
        constraintSet.connect ( R.id.camera1 , ConstraintSet.END , R.id.linearLayout ,
                                ConstraintSet.END , 0 );

        constraintSet.connect ( R.id.camera2 , ConstraintSet.TOP , R.id.camera1 ,
                                ConstraintSet.BOTTOM , 0 );
        constraintSet.connect ( R.id.camera2 , ConstraintSet.BOTTOM , R.id.linearLayout ,
                                ConstraintSet.BOTTOM , 0 );
        constraintSet.connect ( R.id.camera2 , ConstraintSet.START , R.id.linearLayout ,
                                ConstraintSet.START , 0 );
        constraintSet.connect ( R.id.camera2 , ConstraintSet.END , R.id.linearLayout ,
                                ConstraintSet.END , 0 );
        constraintSet.applyTo ( linearLayout );

        swapNumber = 2;
    }


    private void view2 ( ) {
        ConstraintSet constraintSet = new ConstraintSet ( );
        constraintSet.clone ( linearLayout );
        constraintSet.connect ( R.id.camera1 , ConstraintSet.TOP , R.id.camera2 ,
                                ConstraintSet.BOTTOM , 0 );
        constraintSet.connect ( R.id.camera1 , ConstraintSet.BOTTOM , R.id.linearLayout ,
                                ConstraintSet.BOTTOM , 0 );

        constraintSet.connect ( R.id.camera1 , ConstraintSet.START , R.id.linearLayout ,
                                ConstraintSet.START , 0 );
        constraintSet.connect ( R.id.camera1 , ConstraintSet.END , R.id.linearLayout ,
                                ConstraintSet.END , 0 );

        constraintSet.connect ( R.id.camera2 , ConstraintSet.TOP , R.id.linearLayout ,
                                ConstraintSet.TOP , 0 );

        constraintSet.connect ( R.id.camera2 , ConstraintSet.BOTTOM , R.id.camera2 ,
                                ConstraintSet.TOP , 0 );

        constraintSet.connect ( R.id.camera2 , ConstraintSet.START , R.id.linearLayout ,
                                ConstraintSet.START , 0 );
        constraintSet.connect ( R.id.camera2 , ConstraintSet.END , R.id.linearLayout ,
                                ConstraintSet.END , 0 );
        constraintSet.applyTo ( linearLayout );

        swapNumber = 1;
    }

//    private void view3 ( ) {
//        ConstraintSet constraintSet = new ConstraintSet ( );
//        constraintSet.clone ( linearLayout );
//        constraintSet.connect ( R.id.camera1 , ConstraintSet.TOP , R.id.linearLayout ,
//                                ConstraintSet.TOP , 0 );
//        constraintSet.connect ( R.id.camera1 , ConstraintSet.BOTTOM , R.id.camera2 ,
//                                ConstraintSet.TOP , 0 );
//        constraintSet.connect ( R.id.camera1 , ConstraintSet.START , R.id.linearLayout ,
//                                ConstraintSet.START , 0 );
//        constraintSet.connect ( R.id.camera1 , ConstraintSet.END , R.id.linearLayout ,
//                                ConstraintSet.END , 0 );
//
//        constraintSet.connect ( R.id.camera2 , ConstraintSet.TOP , R.id.camera1 ,
//                                ConstraintSet.BOTTOM , 0 );
//        constraintSet.connect ( R.id.camera2 , ConstraintSet.BOTTOM , R.id.linearLayout ,
//                                ConstraintSet.BOTTOM , 0 );
//        constraintSet.connect ( R.id.camera2 , ConstraintSet.START , R.id.linearLayout ,
//                                ConstraintSet.START , 0 );
//        constraintSet.connect ( R.id.camera2 , ConstraintSet.END , R.id.linearLayout ,
//                                ConstraintSet.END , 0 );
//        constraintSet.applyTo ( linearLayout );
//
//        swapNumber = 1;
//    }

}
