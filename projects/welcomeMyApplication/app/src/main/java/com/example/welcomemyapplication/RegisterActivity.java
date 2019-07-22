package com.example.welcomemyapplication;

import android.app.Activity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class RegisterActivity extends Activity implements View.OnClickListener{
    private ImageView ivback;
    private EditText edname,password,pass;
    private Button reginster;
    private TextWatcher watcher = new TextWatcher ( ){
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
          if (!TextUtils.isEmpty ( edname.getText () ) && !TextUtils.isEmpty ( pass.getText () )&&
          !TextUtils.isEmpty ( password.getText () )){
              reginster.setBackgroundColor(getResources ().getColor ( R.color.normal_orange  )   );
             reginster.setEnabled ( true );
          }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate ( savedInstanceState );
        setContentView ( R.layout.activity_regisiten );
       ivback=(ImageView)findViewById ( R.id.register_back) ;
       edname=(EditText)findViewById ( R.id.edname );
       password=(EditText)findViewById ( R.id.edpassword );
       pass=(EditText)findViewById ( R.id.edpass ) ;
       reginster=(Button)findViewById ( R.id.register ) ;

       ivback.setOnClickListener ( this );
       reginster.setOnClickListener ( this );
       password.setOnClickListener ( this );
       pass.setOnClickListener ( this );
       edname.setOnClickListener ( this );
       edname.addTextChangedListener ( watcher );
       password.addTextChangedListener ( watcher );
       pass.addTextChangedListener ( watcher );
    }

    @Override
    public void onClick(View v) {
        switch (v.getId ()){
            case R.id.edyan:

                break;
            case R.id.register:

                break;
            case R.id.qq:
                break;
            case R.id.weixin:
                break;

        }

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
        if (userName.equals ( userName="user001")&&password.equals (password="12345")){
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


