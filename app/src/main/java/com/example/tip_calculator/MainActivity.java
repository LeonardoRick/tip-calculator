package com.example.tip_calculator;

import android.app.Activity;
import android.os.Bundle;

import com.google.android.material.textfield.TextInputEditText;

import androidx.appcompat.app.AppCompatActivity;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private TextInputEditText inputAccValue;
    private SeekBar tipBar;
    private TextView percentValue, tipValue, totalValue;

    private Toast toastAccInfo;
//    private double percent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//      create toast to show if user doesn't input a value
        toastAccInfo = new Toast(this).makeText(
                this,
                "Digite o valor da conta primeiro",
                Toast.LENGTH_SHORT
        );

        inputAccValue = findViewById(R.id.accValueInput);
        tipBar = findViewById(R.id.tipBar);

        percentValue = findViewById(R.id.percetValue);
        tipValue = findViewById(R.id.tipValue);
        totalValue = findViewById(R.id.totalValue);
        tipBarListener();
        accValueInputListener();
    }

    public void tipBarListener() {
        tipBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                hideKeyboard(MainActivity.this);
                // set the visual percentage
                percentValue.setText(progress + "%");
                if(inputAccValue.getText().toString().equals("")) {
                   // if account value is null, we ask the user to input a value first
                    toastAccInfo.show();
                } else {
                    calculateTip(progress);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

    public void calculateTip(int percent) {
        double accValue =  Double.parseDouble(inputAccValue.getText().toString());
        double tip = percent * 0.01 * accValue;
        tipValue.setText(String.format("R$ %.2f", tip));
        totalValue.setText(String.format("R$ %.2f", accValue + tip));
    }

    // Method to listen the input value
    public void accValueInputListener() {
        inputAccValue.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                // 1)Parse result to string; 2)Parse result do double; 3)Format with two decimals; 4)Set input value on totalValue
                totalValue.setText(String.format("R$ %.2f", Double.parseDouble(s.toString())));
            }
        });

        inputAccValue.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(!hasFocus) {

                }
            }
        });

    }

    public static void hideKeyboard(Activity activity) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        // Find the currently focused view, so we can grab the correct window token from it.
        View view = activity.getCurrentFocus();
        // If no view currently has focus, create a new one, just so we can grab a window token from it
        if (view == null) {
            view = new View(activity);
        }
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

}