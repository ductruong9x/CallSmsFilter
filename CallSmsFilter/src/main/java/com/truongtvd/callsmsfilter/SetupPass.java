package com.truongtvd.callsmsfilter;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.readystatesoftware.systembartint.SystemBarTintManager;


public class SetupPass extends Activity {

    private EditText edPass,edConfirmPass;
    private Button btnConfirm;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            SystemBarTintManager tintManager = new SystemBarTintManager(this);
            tintManager.setStatusBarTintEnabled(true);
            tintManager.setNavigationBarTintEnabled(true);
            tintManager.setStatusBarTintResource(R.color.white);
            tintManager.setNavigationBarTintResource(R.color.white);
        }
        setContentView(R.layout.activity_setup_pass);
        SharedPreferences sharedPreferences=getSharedPreferences("SETUP",MODE_PRIVATE);
        String pass=sharedPreferences.getString("PASSCODE","");
        final SharedPreferences.Editor edit=sharedPreferences.edit();
        if(!TextUtils.isEmpty(pass)){
            Intent intent=new Intent(SetupPass.this,PasswordActivity.class);
            startActivity(intent);
            finish();
            return;
        }
        edPass=(EditText)findViewById(R.id.ed_pass);
        edConfirmPass=(EditText)findViewById(R.id.ed_confirm_pass);
        btnConfirm=(Button)findViewById(R.id.btnComfirm);


        btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(TextUtils.isEmpty(edPass.getText()+"")){
                    Toast.makeText(SetupPass.this,getString(R.string.pass_null),Toast.LENGTH_SHORT).show();

                    return;
                }else if(TextUtils.isEmpty(edConfirmPass.getText()+"")){
                    Toast.makeText(SetupPass.this,getString(R.string.pass_confirm_null),Toast.LENGTH_SHORT).show();

                    return;
                }
                if(edPass.getText().length()<4){
                    Toast.makeText(SetupPass.this,getString(R.string.pass_lenght),Toast.LENGTH_SHORT).show();
                    return;
                }
                if(TextUtils.equals(edPass.getText()+"",edConfirmPass.getText()+"")){
                    edit.putString("PASSCODE",edConfirmPass.getText()+"");
                    edit.commit();
                    Intent intent=new Intent(SetupPass.this,PasswordActivity.class);
                    startActivity(intent);
                    finish();
                }else{
                    Toast.makeText(SetupPass.this,getString(R.string.confirm_pass_safe),Toast.LENGTH_SHORT).show();

                }
            }
        });


    }



}
