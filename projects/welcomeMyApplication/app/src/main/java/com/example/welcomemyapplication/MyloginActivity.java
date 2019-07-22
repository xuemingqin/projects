package com.example.welcomemyapplication;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class MyloginActivity extends Activity implements View.OnClickListener{
    private ImageView ivback;
    private EditText edname,yanzm,pass;
    private TextView tvweixin,tvqq,reginster;
    private Button login;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate ( savedInstanceState );
        setContentView ( R.layout.activity_login );
       ivback=(ImageView)findViewById ( R.id.login_back) ;
       edname=(EditText)findViewById ( R.id.edsms );
       tvqq=(TextView)findViewById ( R.id.qq );
       tvweixin=(TextView)findViewById ( R.id.weixin );
       login=(Button)findViewById ( R.id.login );
       reginster=(TextView)findViewById ( R.id.register ) ;
       yanzm=(EditText)findViewById ( R.id.edyan ) ;
       pass=(EditText)findViewById ( R.id.edpass ) ;
       ivback.setOnClickListener ( this );
       tvweixin.setOnClickListener ( this );
       tvqq.setOnClickListener ( this );
       login.setOnClickListener ( this );
       reginster.setOnClickListener ( this );
       yanzm.setOnClickListener ( this );
       pass.setOnClickListener ( this );
    }

    @Override
    public void onClick(View v) {
        switch (v.getId ()){
            case R.id.edyan:

                break;
            case R.id.login:
                handlelogin();
                break;
            case R.id.register:
             chickReginste ();
                break;
            case R.id.qq:
                break;
            case R.id.weixin:
                break;

        }

    }

    private void  chickReginste (){
        Intent intent=new Intent (this,RegisterActivity.class );
        startActivity ( intent );
    }

    private void handlelogin() {
        hideSoftKeyboard(this);
      String userName=edname.getText ().toString ();
      String password= pass.getText ().toString ();
        if (TextUtils.isEmpty (userName )&& TextUtils.isEmpty ( password )) {
            Toast.makeText ( this,"密码和用户名未输入",Toast.LENGTH_SHORT ).show ();
        } else if (TextUtils.isEmpty ( password )) {
           Toast.makeText ( this,"密码未输入",Toast.LENGTH_SHORT ).show ();

       } else if (TextUtils.isEmpty ( userName )) {
            Toast.makeText ( this,"用户名未输入",Toast.LENGTH_SHORT).show ();
        }

        if (password.length()<5){
            Toast.makeText ( this,"密码不能小于5个字",Toast.LENGTH_SHORT ).show ();

        }
        if (userName.equals ( "user001")&&password.equals ("12345")){
            Toast.makeText ( this,"登录成功",Toast.LENGTH_SHORT ).show ();
            finish ();
        } else {
            Toast.makeText ( this,"登录失败",Toast.LENGTH_SHORT ).show ();
        }
    }


    /**
     * 隐藏软键盘(只适用于Activity，不适用于Fragment)
     */
    public void hideSoftKeyboard(Activity activity) {
        View view = activity.getCurrentFocus ( );
        if (view != null) {
            InputMethodManager inputMethodManager = (InputMethodManager) activity.getSystemService ( Activity.INPUT_METHOD_SERVICE );
            inputMethodManager.hideSoftInputFromWindow ( view.getWindowToken ( ), InputMethodManager.HIDE_NOT_ALWAYS );
        }
    }

}


