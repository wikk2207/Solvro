package com.example.wiktoriasalamon.flashlight;

import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraManager;
import android.os.Build;
import android.os.CountDownTimer;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


public class FlashlightActivity extends AppCompatActivity {

  private int mode;

  private TextView textMode;
  private  TextView timeTextView;
  private TextView numberToConvert;
  private EditText edittext;

  private Hexadecimal number;

  private CameraManager cameramanager;
  private String cameraID;
  private Boolean isTorchOn;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_flashlight);

    mode = getIntent().getIntExtra("mode",1);

    textMode = findViewById(R.id.textViewMode);
    timeTextView = findViewById(R.id.time);
    numberToConvert = findViewById(R.id.textView_numberToConvert);
    edittext = findViewById(R.id.editText);

    number = new Hexadecimal(256);

    cameramanager = (CameraManager) getSystemService(Context.CAMERA_SERVICE);
    try {
      cameraID = cameramanager.getCameraIdList()[0];
    } catch (CameraAccessException e) {
      e.printStackTrace();
    }
    isTorchOn = false;

    if(mode == 1) {
      textMode.setText("Zamień poniższą liczbę w systemie szesnastkowym na liczbę w systemie dziesiętnym.");
      numberToConvert.setText(number.getHexadecimal());
    } else {
      textMode.setText("Zamień poniższą liczbę w systemie dziesiętnym na liczbę w systemie szesnastkowym.");
      numberToConvert.setText(number.getDecimalString());
    }

    isFlashAvailable();
    configureEnterKey();
    configureCheckButton();
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

  private void configureCheckButton(){
    Button button = findViewById(R.id.checkButton);
    button.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        check();
      }
    });
  }

  /**
   * Dzięki tej funkcji po kliknięciu "gotowe" na klawiaturze wykona się identyczne działanie jak po kliknięciu przycisku "sprawdż".
   */
  private void configureEnterKey() {
    edittext.setOnKeyListener(new View.OnKeyListener() {
      @Override
      public boolean onKey(View v, int keyCode, KeyEvent event) {
        if(event.getAction() == KeyEvent.ACTION_DOWN) {
          switch (keyCode) {
            case KeyEvent.KEYCODE_DPAD_CENTER:
            case KeyEvent.KEYCODE_ENTER:
              check();
              return true;
            default:
                break;
          }
        }
        return false;
      }
    });
  }

  /**
   * Sprawdza, czy podana odpowiedź jest prawidłowa.
   */
  private void check() {
    String numberToCheckString = edittext.getText().toString();
    boolean result = number.numberEquals(numberToCheckString, mode);

    if(result) {
      lightCounter(5);
      number = new Hexadecimal(256);
      numberToConvert.clearComposingText();

      if(mode == 1){
        numberToConvert.setText(number.getHexadecimal());
      } else {
        numberToConvert.setText(number.getDecimalString());
      }
    } else {
      Toast.makeText(FlashlightActivity.this,"Źle",Toast.LENGTH_LONG).show();
    }
    edittext.setText("");
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

  /**
   * Zapala latarkę i jednocześnie odlicza czas do jej zgaśnięcia.
   * @param seconds Czas (w sekundach), w którym latarka ma być zapalona.
   */
  private void lightCounter(int seconds) {
   CountDownTimer timer = new CountDownTimer(seconds*1000, 1000) {
     @Override
     public void onFinish() {
       timeTextView.setText("");
       turnOffFlashLight();
       isTorchOn = false;
     }
     @Override
     public void onTick(long millisUntilFinished) {
       timeTextView.setText(Long.toString(millisUntilFinished/1000));
     }
   };

    if(isTorchOn==false) {
      turnOnFlashLight();
      isTorchOn = true;
    }
    timer.start();

  }

  /**
   * Sprawdza, czy urządzenie ma dostęp do latarki. Jeśli nie - wyłącza aplikację i wyświetla komunikat.
   */
  private void isFlashAvailable() {

    Boolean isFlashAvailable = getApplicationContext().getPackageManager()
        .hasSystemFeature(PackageManager.FEATURE_CAMERA_FLASH);

    if (!isFlashAvailable) {
      AlertDialog alert = new AlertDialog.Builder(FlashlightActivity.this).create();
      alert.setTitle("Error !!");
      alert.setMessage("Twoje urządzenie nie ma latarki!");
      alert.setButton(DialogInterface.BUTTON_POSITIVE, "OK", new DialogInterface.OnClickListener() {
        public void onClick(DialogInterface dialog, int which) {
          finish();
          System.exit(0);
        }
      });
      alert.show();
      return;
    }
  }


}
