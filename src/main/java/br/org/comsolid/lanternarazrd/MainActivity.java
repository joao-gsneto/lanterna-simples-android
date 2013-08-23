package br.org.comsolid.lanternarazrd;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.hardware.Camera;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import br.org.comsolid.lanternarazrd.lib.LanternaControl;

public class MainActivity extends Activity {
    private boolean hasFlash;
    private LanternaControl lc;
    private Camera camera;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initializeCamera();
        lc = new LanternaControl(camera);
        final Button botao_liga_desliga = (Button) findViewById(R.id.button_lanterna_on_off);

        botao_liga_desliga.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                hasFlash = getApplicationContext().getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA_FLASH);

                if (hasFlash) {

                    lc.toggleLanterna();

                    if (lc.getLanternaStatus()) {
                        //ligada
                        botao_liga_desliga.setText(R.string.lanterna_on);
                    } else {
                        //desligada
                        botao_liga_desliga.setText(R.string.lanterna_off);
                    }
                } else {
                    Toast toast;
                    CharSequence text = "Seu dispositivo não suporta FlashLight";
                    toast = Toast.makeText(getApplicationContext(), text, Toast.LENGTH_LONG);
                    toast.show();
                }
            }
        });
    }

    private void initializeCamera() {
        camera = Camera.open();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.action_about:
                abrirAbout();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void abrirAbout() {
        AlertDialog.Builder alert = new AlertDialog.Builder(MainActivity.this);
        alert.setTitle("Sobre - Lanterna Simples");
        alert.setMessage(R.string.sobre_lanterna_simples );
        alert.setPositiveButton("OK", null);
        alert.show();
    }

    @Override
    public void onResume() {
        super.onResume();  // Always call the superclass method first

        // Get the Camera instance as the activity achieves full user focus
        if (camera == null) {
            initializeCamera(); // Local method to handle camera init
        }
    }
    @Override
    public void onPause() {
        super.onPause();  // Always call the superclass method first

        // Release the Camera because we don't need it when paused
        // and other activities might need to use it.
        if (camera != null) {
            camera.release();
            camera = null;
        }
    }
}
