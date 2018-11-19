package com.example.wiktoriasalamon.flashlight;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class FlashlightActivity extends AppCompatActivity {

  private int mode;
  TextView textMode;
  private Hexadecimal number;
  TextView numberToConvert;
  EditText edittext;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_flashlight);

    textMode = findViewById(R.id.textViewMode);

    Intent intent = getIntent();
    mode = intent.getIntExtra("mode",1);

    if(mode == 1) {
      textMode.setText("Zamień poniższą liczbę w systemie szesnastkowym na liczbę w systemie dziesiętnym.");
    } else {
      textMode.setText("Zamień poniższą liczbę w systemie dziesiętnym na liczbę w systemie szesnastkowym.");
    }

    numberToConvert = findViewById(R.id.textView_numberToConvert);


    configureBackButton();
    number = new Hexadecimal();

    if (mode == 1) {
      numberToConvert.setText(number.getHexadecimal());
    } else if (mode == 2) {
      numberToConvert.setText(number.getDecimalString());
    }

    configureCheckButton();
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
        EditText editText = findViewById(R.id.editText);
        String numberToCheckString = editText.getText().toString();
        boolean result = number.numberEquals(numberToCheckString, mode);
        String textToShow;
        if(result) textToShow = "Dobrze!";
        else textToShow = "Źle!";

        Toast.makeText(FlashlightActivity.this, textToShow, Toast.LENGTH_SHORT).show();

        if(result) {
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

}
