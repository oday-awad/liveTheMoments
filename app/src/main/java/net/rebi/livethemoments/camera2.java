package net.rebi.livethemoments;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.ImageFormat;
import android.graphics.SurfaceTexture;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraCaptureSession;
import android.hardware.camera2.CameraCharacteristics;
import android.hardware.camera2.CameraDevice;
import android.hardware.camera2.CameraManager;
import android.hardware.camera2.CameraMetadata;
import android.hardware.camera2.CaptureRequest;
import android.hardware.camera2.TotalCaptureResult;
import android.hardware.camera2.params.StreamConfigurationMap;
import android.media.Image;
import android.media.ImageReader;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.HandlerThread;
import android.util.Size;
import android.util.SparseIntArray;
import android.view.LayoutInflater;
import android.view.Surface;
import android.view.TextureView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


@SuppressWarnings ( "ALL" )
public class camera2 extends Fragment {

    private final static SparseIntArray ORIENTATIONS = new SparseIntArray ( );

    static {
        ORIENTATIONS.append ( Surface.ROTATION_0 , 90 );
        ORIENTATIONS.append ( Surface.ROTATION_90 , 0 );
        ORIENTATIONS.append ( Surface.ROTATION_180 , 270 );
        ORIENTATIONS.append ( Surface.ROTATION_270 , 180 );
    }

    TextureView            textureView;
    CameraDevice           cameraDevice;
    CameraCaptureSession   cameraCaptureSession;
    CaptureRequest         captureRequest;
    CaptureRequest.Builder captureRequestBuilder;
    Handler                mBackgroundHandler;
    HandlerThread          mBackgroundThread;
    private String cameraId;
    private Size   imageDimensions;
    private ImageReader                imageReader;
    private File                       file;
    private int                        cameraIndex   = 1;
    private int                        outputSize    = 0;
    TextureView.SurfaceTextureListener surfaceTextureListener;
    CameraDevice.StateCallback stateCallback;


    public camera2 ( ) {
        // Required empty public constructor
    }


    @Override
    public View onCreateView (
            LayoutInflater inflater , ViewGroup container , Bundle savedInstanceState
    ) {
        // Inflate the layout for this fragment
        View root = inflater.inflate ( R.layout.camera2 , container , false );


        textureView = root.findViewById ( R.id.textureView );

         stateCallback = new CameraDevice.StateCallback ( ) {
            @Override
            public void onOpened ( CameraDevice camera ) {

                System.err.println ("onOpened" );
                cameraDevice = camera;
                try {
                    createCameraPreview ( );
                }
                catch ( CameraAccessException e ) {
                    e.printStackTrace ( );
                }
            }

            @Override
            public void onDisconnected ( CameraDevice camera ) {
                System.err.println ("onDisconnected" );

                cameraDevice.close ( );
            }

            @Override
            public void onError ( CameraDevice camera , int error ) {
                System.err.println ("onError" );
                cameraDevice.close ( );
                cameraDevice = null;
            }
        };

        surfaceTextureListener =
                new TextureView.SurfaceTextureListener ( ) {
                    @Override
                    public void onSurfaceTextureAvailable (
                            SurfaceTexture surface , int width , int height
                    ) {
                        try {
                            openCamera ( );
                        }
                        catch ( CameraAccessException e ) {
                            e.printStackTrace ( );
                        }
                    }

                    @Override
                    public void onSurfaceTextureSizeChanged (
                            SurfaceTexture surface , int width , int height
                    ) {

                    }

                    @Override
                    public boolean onSurfaceTextureDestroyed ( SurfaceTexture surface ) {
                        return false;
                    }

                    @Override
                    public void onSurfaceTextureUpdated ( SurfaceTexture surface ) {

                    }
                };


        return root;
    }

    @Override
    public void onResume ( ) {
        super.onResume ( );

        startBackgroundThread ( );
        if ( textureView.isAvailable ( ) ) {
            try {
                openCamera ( );
            }
            catch ( CameraAccessException e ) {
                e.printStackTrace ( );
            }
        }
        else {
            textureView.setSurfaceTextureListener ( surfaceTextureListener );
        }

    }

    @Override
    public void onPause ( ) {
        try {
            stopBackgroundThread ( );
        }
        catch ( InterruptedException e ) {
            e.printStackTrace ( );
        }

        super.onPause ( );

    }


    private void createCameraPreview ( ) throws CameraAccessException {
        SurfaceTexture texture = textureView.getSurfaceTexture ( );
        texture.setDefaultBufferSize ( imageDimensions.getWidth ( ) ,
                                       imageDimensions.getHeight ( ) );
        Surface surface = new Surface ( texture );
        captureRequestBuilder = cameraDevice.createCaptureRequest ( CameraDevice.TEMPLATE_PREVIEW );
        captureRequestBuilder.addTarget ( surface );

        cameraDevice.createCaptureSession ( Arrays.asList ( surface ) ,
                                            new CameraCaptureSession.StateCallback ( ) {
            @Override
            public void onConfigured ( CameraCaptureSession session ) {
                if ( cameraDevice == null ) {
                    return;
                }
                cameraCaptureSession = session;
                try {
                    updatePreview ( );
                }
                catch ( CameraAccessException e ) {
                    e.printStackTrace ( );
                }
            }

            @Override
            public void onConfigureFailed ( CameraCaptureSession session ) {
                Toast.makeText ( getContext ( ) , "Configure Failed" , Toast.LENGTH_SHORT ).show ( );

            }
        } , null );
    }

    private void updatePreview ( ) throws CameraAccessException {
        if ( cameraDevice == null ) {
            return;
        }
        captureRequestBuilder.set ( CaptureRequest.CONTROL_MODE ,
                                    CameraMetadata.CONTROL_MODE_AUTO );
        try {
            cameraCaptureSession.setRepeatingRequest ( captureRequestBuilder.build ( ) , null ,
                                                       mBackgroundHandler );
        }
        catch ( Exception e ) {
            e.printStackTrace ( );
        }
    }

    private void openCamera ( ) throws CameraAccessException {
        CameraManager cameraManager =
                ( CameraManager ) getActivity ( ).getSystemService ( Context.CAMERA_SERVICE );

        String[] s = cameraManager.getCameraIdList ( );

        System.err.println ( " \n\n\n\n\n ****************************************************" + s.length + " \n\n\n\n\n ****************************************************" );

        cameraId = cameraManager.getCameraIdList ( )[ cameraIndex ];

        CameraCharacteristics cameraCharacteristics =
                cameraManager.getCameraCharacteristics ( cameraId );

        StreamConfigurationMap streamConfigurationMap =
                cameraCharacteristics.get ( CameraCharacteristics.SCALER_STREAM_CONFIGURATION_MAP );

        Size[] d = streamConfigurationMap.getOutputSizes ( SurfaceTexture.class );

        System.err.println ( " \n\n\n\n\n ****************************************************" + d.length + " \n\n\n\n\n ****************************************************" );

        for ( int i = 0 ; i < d.length ; i++ ) {
            System.err.println ( d[ i ] );
            System.err.println ( "**************************" );
        }


        imageDimensions =
                streamConfigurationMap.getOutputSizes ( SurfaceTexture.class )[ outputSize ];

        if ( Build.VERSION.SDK_INT >= Build.VERSION_CODES.M ) {
            if ( getActivity ( ).checkSelfPermission ( Manifest.permission.CAMERA ) != PackageManager.PERMISSION_GRANTED ) {

                return;
            }
        }
        cameraManager.openCamera ( cameraId , stateCallback , null );

    }

    private void takePicture ( ) throws CameraAccessException {

        if ( cameraDevice == null ) {
            return;
        }
        CameraManager manager =
                ( CameraManager ) getActivity ( ).getSystemService ( Context.CAMERA_SERVICE );

        CameraCharacteristics characteristics =
                manager.getCameraCharacteristics ( cameraDevice.getId ( ) );

        Size[] jpegSizes = null;

        jpegSizes =
                characteristics.get ( CameraCharacteristics.SCALER_STREAM_CONFIGURATION_MAP ).getOutputSizes ( ImageFormat.JPEG );

        int width  = 640;
        int height = 480;

        if ( jpegSizes != null && jpegSizes.length > 0 ) {
            width = jpegSizes[ 0 ].getWidth ( );
            height = jpegSizes[ 0 ].getHeight ( );
        }

        Size        largest;
        ImageReader reader = ImageReader.newInstance ( width , height , ImageFormat.JPEG , 1 );

        List < Surface > outputSurfaces = new ArrayList <> ( 2 );
        outputSurfaces.add ( reader.getSurface ( ) );
        outputSurfaces.add ( new Surface ( textureView.getSurfaceTexture ( ) ) );

        final CaptureRequest.Builder captureBuilder =
                cameraDevice.createCaptureRequest ( CameraDevice.TEMPLATE_STILL_CAPTURE );

        captureBuilder.addTarget ( reader.getSurface ( ) );
        captureBuilder.set ( CaptureRequest.CONTROL_MODE , CameraMetadata.CONTROL_MODE_AUTO );

        int rotation = getActivity ( ).getWindowManager ( ).getDefaultDisplay ( ).getRotation ( );
        captureBuilder.set ( CaptureRequest.JPEG_ORIENTATION , ORIENTATIONS.get ( rotation ) );

        Long   tsLong = System.currentTimeMillis ( ) / 1000;
        String ts     = tsLong.toString ( );

        file = new File ( Environment.getExternalStorageDirectory ( ) + "/" + ts + ".jpg" );


        ImageReader.OnImageAvailableListener readerListener =
                new ImageReader.OnImageAvailableListener ( ) {
                    @Override
                    public void onImageAvailable ( ImageReader reader ) {
                        Image image = null;

                        image = reader.acquireLatestImage ( );
                        ByteBuffer buffer = image.getPlanes ( )[ 0 ].getBuffer ( );

                        byte[] bytes = new byte[ buffer.capacity ( ) ];
                        buffer.get ( bytes );
                        try {
                            save ( bytes );
                        }
                        catch ( IOException e ) {
                            e.printStackTrace ( );
                        } finally {
                            if ( image != null ) {
                                image.close ( );
                            }
                        }


                    }
                };

        reader.setOnImageAvailableListener ( readerListener , mBackgroundHandler );

        final CameraCaptureSession.CaptureCallback captureListener =
                new CameraCaptureSession.CaptureCallback ( ) {
                    @Override
                    public void onCaptureCompleted (
                            CameraCaptureSession session , CaptureRequest request ,
                            TotalCaptureResult result
                    ) {
                        super.onCaptureCompleted ( session , request , result );
                        Toast.makeText ( getActivity ( ) , "Saved" , Toast.LENGTH_SHORT ).show ( );
                        try {
                            createCameraPreview ( );
                        }
                        catch ( CameraAccessException e ) {
                            e.printStackTrace ( );
                        }
                    }
                };


        cameraDevice.createCaptureSession ( outputSurfaces ,
                                            new CameraCaptureSession.StateCallback ( ) {
            @Override
            public void onConfigured ( CameraCaptureSession session ) {
                try {
                    session.capture ( captureBuilder.build ( ) , captureListener ,
                                      mBackgroundHandler );
                }
                catch ( CameraAccessException e ) {
                    e.printStackTrace ( );
                }
            }

            @Override
            public void onConfigureFailed ( CameraCaptureSession session ) {

            }
        } , mBackgroundHandler );

    }

    private void save ( byte[] bytes ) throws IOException {
        OutputStream outputStream = null;

        outputStream = new FileOutputStream ( file );

        outputStream.write ( bytes );

        outputStream.close ( );
    }


    private void startBackgroundThread ( ) {
        mBackgroundThread = new HandlerThread ( "Camera Background2" );
        mBackgroundThread.start ( );

        mBackgroundHandler = new Handler ( mBackgroundThread.getLooper ( ) );
    }


    private void stopBackgroundThread ( ) throws InterruptedException {
        mBackgroundThread.quitSafely ( );
        mBackgroundThread.join ( );
        mBackgroundThread = null;
        mBackgroundHandler = null;
    }




}

