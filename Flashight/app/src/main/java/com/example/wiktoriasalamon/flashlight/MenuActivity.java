package com.example.wiktoriasalamon.flashlight;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

public class MenuActivity extends AppCompatActivity {
  private int mode;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_menu);

    mode = 1;

    configureButton();
    configureRadioGroup();

  }

  private void configureButton() {
    Button button = findViewById(R.id.nextButton);
    button.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        Intent intent = new Intent(MenuActivity.this, FlashlightActivity.class);
        intent.putExtra("mode",mode);
        startActivity(intent);
      }
    });
  }

  /**
   * Zmienia wartość mode w zależności od wybranego trybu zamiany
   */
  private void configureRadioGroup() {
    RadioGroup radioGroup = findViewById(R.id.radioGroup);
    radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
      @Override
      public void onCheckedChanged(RadioGroup group, int checkedId) {
        if (checkedId == R.id.radio_hexToDec) {
          mode = 1;
        } else if (checkedId == R.id.radio_DecToHex) {
          mode = 2;
        }
      }
    });
  }
}
