package com.example.wiktoriasalamon.flashlight;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraManager;
import android.media.MediaPlayer;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class FlashlightActivity extends AppCompatActivity {

  private int mode;
  private TextView textMode;
  private Hexadecimal number;
  private TextView numberToConvert;
  protected EditText edittext;
  private  TextView timeTextView;

  private CameraManager cameramanager;
  private String cameraID;
  private Boolean isTorchOn;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_flashlight);

    textMode = findViewById(R.id.textViewMode);
    numberToConvert = findViewById(R.id.textView_numberToConvert);
    number = new Hexadecimal();
    mode = getIntent().getIntExtra("mode",1);
    timeTextView = findViewById(R.id.time);
    isTorchOn = false;

    if(mode == 1) {
      textMode.setText("Zamień poniższą liczbę w systemie szesnastkowym na liczbę w systemie dziesiętnym.");
      numberToConvert.setText(number.getHexadecimal());
    } else {
      textMode.setText("Zamień poniższą liczbę w systemie dziesiętnym na liczbę w systemie szesnastkowym.");
      numberToConvert.setText(number.getDecimalString());
    }


    Boolean isFlashAvailable = getApplicationContext().getPackageManager()
        .hasSystemFeature(PackageManager.FEATURE_CAMERA_FLASH);

    if (!isFlashAvailable) {

      AlertDialog alert = new AlertDialog.Builder(FlashlightActivity.this)
          .create();
      alert.setTitle("Error !!");
      alert.setMessage("Your device doesn't support flash light!");
      alert.setButton(DialogInterface.BUTTON_POSITIVE, "OK", new DialogInterface.OnClickListener() {
        public void onClick(DialogInterface dialog, int which) {
          // closing the application
          finish();
          System.exit(0);
        }
      });
      alert.show();
      return;
    }

   cameramanager = (CameraManager) getSystemService(Context.CAMERA_SERVICE);
    try {
      cameraID = cameramanager.getCameraIdList()[0];
    } catch (CameraAccessException e) {
      e.printStackTrace();
    }




    configureCheckButton(this);
    configureBackButton();
  }

  private void configureBackButton() {
    Button button = findViewById(R.id.backButton);
    button.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        finish();
      }
    });
  }

  private void configureCheckButton(final Context context){
    Button button = findViewById(R.id.checkButton);

    button.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {

        EditText editText = findViewById(R.id.editText);
        String numberToCheckString = editText.getText().toString();
        boolean result = number.numberEquals(numberToCheckString, mode);
        String textToShow;
        if(result) textToShow = "Dobrze!";
        else textToShow = "Źle!";
        if(result) {
            try {
              if (isTorchOn==false) {
                turnOnFlashLight();
                isTorchOn = true;
                Thread.sleep(10000);
                turnOffFlashLight();
                isTorchOn = false;
              }
            } catch (Exception e) {
              e.printStackTrace();
            }

            number = new Hexadecimal();
            numberToConvert.clearComposingText();

            if(mode == 1){
              numberToConvert.setText(number.getHexadecimal());
            } else {
              numberToConvert.setText(number.getDecimalString());
            }
          }

        editText.setText("");

      }
    });
  }
  public void turnOnFlashLight() {

    try {
      if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
        cameramanager.setTorchMode(cameraID, true);
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public void turnOffFlashLight() {

    try {
      if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
        cameramanager.setTorchMode(cameraID, false);
      }

    } catch (Exception e) {
      e.printStackTrace();
    }
  }


}
