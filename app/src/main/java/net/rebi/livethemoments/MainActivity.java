package net.rebi.livethemoments;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

public class MainActivity extends AppCompatActivity {

    Button   openView;

    @Override
    protected void onCreate ( Bundle savedInstanceState ) {
        super.onCreate ( savedInstanceState );
        setContentView ( R.layout.activity_main );

        openView = findViewById ( R.id.openView );

        checkPermissions ();





        openView.setOnClickListener ( new View.OnClickListener ( ) {
            @Override
            public void onClick ( View v ) {
                Intent intent = new Intent ( getBaseContext ( ) , view.class );

                startActivity ( intent );
            }
        } );
    }


    public void checkPermissions(){
        // check permission
        if (
                ActivityCompat.checkSelfPermission ( this , Manifest.permission.CAMERA ) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission ( this , Manifest.permission.WRITE_EXTERNAL_STORAGE ) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission ( this , Manifest.permission.RECORD_AUDIO ) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission ( this , Manifest.permission.ACCESS_FINE_LOCATION ) != PackageManager.PERMISSION_GRANTED ) {
            // request for permission
            ActivityCompat.requestPermissions ( this ,
                                                new String[] {
                                                        Manifest.permission.CAMERA
                                                        , Manifest.permission.WRITE_EXTERNAL_STORAGE
                                                        , Manifest.permission.RECORD_AUDIO
                                                        , Manifest.permission.ACCESS_FINE_LOCATION } , 1 );
        }
    }


}



