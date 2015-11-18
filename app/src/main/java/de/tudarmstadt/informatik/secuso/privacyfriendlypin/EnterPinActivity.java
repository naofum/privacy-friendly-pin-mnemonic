package de.tudarmstadt.informatik.secuso.privacyfriendlypin;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.text.Html;
import android.text.InputType;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class EnterPinActivity extends ActionBarActivity {

    private String pin;
    private String visiblePin;
    EditText pinEditText;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enter_pin);

        //ActionBar
        //TODO Logo einfügen
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setLogo(R.mipmap.ic_launcher);
        actionBar.setTitle(R.string.app_name);
        actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#024265")));

        pinEditText = (EditText) findViewById(R.id.displayPin);
        pinEditText.setInputType(InputType.TYPE_NULL);

        //Buttons
        Button[] numpad = new Button[10];
        numpad[0] = (Button) findViewById(R.id.button_zero);
        numpad[1] = (Button) findViewById(R.id.button_one);
        numpad[2] = (Button) findViewById(R.id.button_two);
        numpad[3] = (Button) findViewById(R.id.button_three);
        numpad[4] = (Button) findViewById(R.id.button_four);
        numpad[5] = (Button) findViewById(R.id.button_five);
        numpad[6] = (Button) findViewById(R.id.button_six);
        numpad[7] = (Button) findViewById(R.id.button_seven);
        numpad[8] = (Button) findViewById(R.id.button_eight);
        numpad[9] = (Button) findViewById(R.id.button_nine);

        visiblePin = "";
        pin = "";

        for (int i = 0; i < numpad.length; i++) {
            final Button temp = numpad[i];
            final int tempInt = i;
            temp.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (pinEditText.getText().length() < 4) {
                        visiblePin = visiblePin += tempInt;
                        pin = pin += tempInt;
                        if (pin.length() == 2) {
                            pin += "-";
                        }
                        Log.d("PIN:", visiblePin);
                        pinEditText.setText(visiblePin, TextView.BufferType.EDITABLE);
                    }
                }
            });
        }

        Button deleteButton = (Button) findViewById(R.id.button_delete);
        deleteButton.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                pinEditText.setText(deletePinDigits(visiblePin));
                visiblePin = deletePinDigits(visiblePin);
                pin = deletePinDigits(visiblePin);
            }
        });

        Button doneButton = (Button) findViewById(R.id.button_done);
        doneButton.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                clickDoneButton();
            }
        });

        Button cancelButton = (Button) findViewById(R.id.button_cancel);
        cancelButton.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                pinEditText.getText().clear();
                visiblePin = "";
                pin = "";
            }
        });

    }

    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        Intent intent = new Intent();
        switch (item.getItemId()) {
            case R.id.about:
                intent.setClass(this, AboutActivity.class);
                startActivityForResult(intent, 0);
                return true;
            case R.id.action_help:
                intent.setClass(this, HelpActivity.class);
                startActivityForResult(intent, 0);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void clickDoneButton() {

        if (pinEditText.getText().length() == 4) {
            Intent intent = new Intent(EnterPinActivity.this, ShowHintActivity.class);
            intent.putExtra("currentPin", pin);
            intent.putExtra("currentVisiblePin", visiblePin);
            EnterPinActivity.this.startActivity(intent);
        }
    }

    public String deletePinDigits(String visiblePin) {

        if (visiblePin.length() > 0) {
            visiblePin = visiblePin.substring(0, visiblePin.length()-1);
        }

        return visiblePin;
    }

}
