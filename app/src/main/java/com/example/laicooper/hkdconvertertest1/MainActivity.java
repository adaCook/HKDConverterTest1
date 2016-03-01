package com.example.laicooper.hkdconvertertest1;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;

public class MainActivity extends AppCompatActivity {

    private EditText et1;
    private double HKD2RMB = 0.8385;
    private double HKD2USD = 0.1284;
    private double HKD2POUNDS = 0.0885;
    private double HKD2EUR = 0.114;
    private double HKD2JPY = 14.5323;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final Button butCon = (Button) findViewById(R.id.buttonCon);
        final Button buttonCle = (Button) findViewById(R.id.button_cle);
        final EditText editTextHKD = (EditText) findViewById(R.id.HKDCurrency);
        final EditText editTextRMB = (EditText) findViewById(R.id.RMBCurrency);
        final EditText editTextUSD = (EditText) findViewById(R.id.USDCurrency);
        final EditText editTextPounds = (EditText) findViewById(R.id.GBPCurrency);
        final EditText editTextEUR = (EditText) findViewById(R.id.EURCurrency);
        final EditText editTextJPY = (EditText) findViewById(R.id.JPYCurrency);


        et1=(EditText)findViewById(R.id.HKDCurrency);
        registerForContextMenu(et1);
        SlidingMenu menu=new SlidingMenu(this);
        menu.setMode(SlidingMenu.LEFT);
        menu.setBehindOffsetRes(R.dimen.sliding_menu_offset);
        menu.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
        menu.attachToActivity(this, SlidingMenu.SLIDING_CONTENT);
        menu.setMenu(R.layout.slidingmenu);
        Button slidingButton=(Button)findViewById(R.id.slidingButton);
        slidingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        butCon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    String hkdStr = editTextHKD.getText().toString();
                    double hkd = Double.parseDouble(hkdStr);
                    String rmb = String.valueOf(hkd * HKD2RMB);
                    String usd = String.valueOf(hkd * HKD2USD);
                    String punds = String.valueOf(hkd * HKD2POUNDS);
                    String eur = String.valueOf(hkd * HKD2EUR);
                    String jpy = String.valueOf(hkd * HKD2JPY);


                    editTextRMB.setText(rmb);
                    editTextUSD.setText(usd);
                    editTextPounds.setText(punds);
                    editTextEUR.setText(eur);
                    editTextJPY.setText(jpy);


                } catch (Exception e) {
                    Toast.makeText(v.getContext(), "Invalid Data - Please enter HK dollars again",
                            Toast.LENGTH_SHORT).show();
                }

            }
        });
        buttonCle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editTextRMB.setText("");
                editTextHKD.setText("");
                editTextUSD.setText("");
                editTextPounds.setText("");
                editTextEUR.setText("");
                editTextJPY.setText("");


            }
        });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
// Inflate the menu; this adds items to the aaction bar if it is present
    getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.menu_about:
                Toast.makeText(this, R.string.aboutApp, Toast.LENGTH_LONG).show();
                return true;
            case R.id.menu_cle:
                Toast.makeText(this, R.string.aboutApp, Toast.LENGTH_LONG).show();
                return true;
            case R.id.menu_exit:
                finish();
                return true;
        }
        return false;
    }
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v,
                                    ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.context_menu, menu);
    }
    @Override
    public boolean onContextItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.context_about:
                Toast.makeText(this, R.string.aboutApp, Toast.LENGTH_LONG).show();
                return true;
            case R.id.context_exit:
                finish();
                return true;
            default:
                return super.onContextItemSelected(item);
        }
    }
    public void updateData(View view)
    {
        Intent i=new Intent(this,JsonTest.class);
//        startActivity(i);
        startActivityForResult(i,1);
//        new HttpGetTask().execute();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode==RESULT_OK) {
            Bundle hkdata = data.getExtras();
            HKD2RMB = hkdata.getDouble("CNY");
            HKD2USD = hkdata.getDouble("USD");
            HKD2POUNDS = hkdata.getDouble("GBP");
            HKD2EUR = hkdata.getDouble("EUR");
            HKD2JPY = hkdata.getDouble("JPY");
        }

    }
}

