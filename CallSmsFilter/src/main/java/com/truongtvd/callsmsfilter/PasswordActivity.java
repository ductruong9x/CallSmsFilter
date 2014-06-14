package com.truongtvd.callsmsfilter;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


public class PasswordActivity extends Activity implements View.OnClickListener {

    private EditText edPass;
    private Button btn1, btn2, btn3, btn4, btn5, btn6, btn7, btn8, btn9, btn0, btnDelete;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_password);
        edPass = (EditText) findViewById(R.id.ed_pass);
        btn1 = (Button) findViewById(R.id.btn1);
        btn2 = (Button) findViewById(R.id.btn2);
        btn3 = (Button) findViewById(R.id.btn3);
        btn4 = (Button) findViewById(R.id.btn4);
        btn5 = (Button) findViewById(R.id.btn5);
        btn6 = (Button) findViewById(R.id.btn6);
        btn7 = (Button) findViewById(R.id.btn7);
        btn8 = (Button) findViewById(R.id.btn8);
        btn9 = (Button) findViewById(R.id.btn9);
        btn0 = (Button) findViewById(R.id.btn0);
        btnDelete = (Button) findViewById(R.id.btnDelete);
        btn0.setOnClickListener(this);
        btn1.setOnClickListener(this);
        btn2.setOnClickListener(this);
        btn3.setOnClickListener(this);
        btn4.setOnClickListener(this);
        btn5.setOnClickListener(this);
        btn6.setOnClickListener(this);
        btn7.setOnClickListener(this);
        btn8.setOnClickListener(this);
        btn9.setOnClickListener(this);
        btnDelete.setOnClickListener(this);

        edPass.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(count==4){
                    SharedPreferences sharedPreferences=getSharedPreferences("SETUP",MODE_PRIVATE);
                    String pass=sharedPreferences.getString("PASSCODE","");
                    if(TextUtils.equals(pass,s+"")){
                        Intent intent=new Intent(PasswordActivity.this,MainActivity.class);
                        startActivity(intent);
                        finish();

                    }else{
                        edPass.setText("");
                        Toast.makeText(PasswordActivity.this,getString(R.string.pass_error),Toast.LENGTH_SHORT).show();
                    }
                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }


    @Override
    public void onClick(View v) {
        String pass = edPass.getText() + "";

        switch (v.getId()) {
            case R.id.btn0:
                edPass.setText(pass + "0");
                break;
            case R.id.btn1:
                edPass.setText(pass + "1");
                break;
            case R.id.btn2:
                edPass.setText(pass + "2");
                break;
            case R.id.btn3:
                edPass.setText(pass + "3");
                break;
            case R.id.btn4:
                edPass.setText(pass + "4");
                break;
            case R.id.btn5:
                edPass.setText(pass + "5");
                break;
            case R.id.btn6:
                edPass.setText(pass + "6");
                break;
            case R.id.btn7:
                edPass.setText(pass + "7");
                break;
            case R.id.btn8:
                edPass.setText(pass + "8");
                break;
            case R.id.btn9:
                edPass.setText(pass + "9");
                break;
            case R.id.btnDelete:
                if (pass.length() == 0) {

                }else {
                    pass = pass.substring(0, pass.length() - 1);
                    edPass.setText(pass);
                }
                break;
        }

    }
}
