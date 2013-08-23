package br.org.comsolid.lanternarazrd.lib;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.hardware.Camera;
import android.os.Build;
import android.util.Log;

import java.io.IOException;

/**
 * Created by joaoneto on 23/08/13.
 */
public class LanternaControl {
    private Camera camera;
    private Camera.Parameters params;

    public LanternaControl( Camera camera ) {
        this.camera = camera;
        this.params = camera.getParameters();
    }

    public boolean getLanternaStatus() {
        params = camera.getParameters();

        String flashMode = params.getFlashMode();


        Log.w("RazrD", "FlashMode atual " + flashMode );
        Log.w("RazrD", "Boolean " +  flashMode.equals(Camera.Parameters.FLASH_MODE_TORCH) );

        return flashMode.equals(Camera.Parameters.FLASH_MODE_TORCH);

    }

    public void toggleLanterna() {
        if (!getLanternaStatus()) {
            ligaLanterna();
        } else {
            desligaLanterna();
        }
    }

    public void ligaLanterna() {
        params = camera.getParameters();
        params.setFlashMode(Camera.Parameters.FLASH_MODE_TORCH);
        camera.setParameters(params);
        camera.startPreview();
    }

    public void desligaLanterna() {
            params = camera.getParameters();
            params.setFlashMode(Camera.Parameters.FLASH_MODE_OFF);
            camera.setParameters(params);
            camera.stopPreview();
    }
}
