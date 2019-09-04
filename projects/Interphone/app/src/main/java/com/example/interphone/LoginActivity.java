package com.example.interphone;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import java.util.ArrayList;
import java.util.List;

public class LoginActivity extends Activity implements View.OnClickListener{
    LoginHelper helper = new LoginHelper( this );
    private long exitTime=0;
    private SQLiteDatabase db;
    Boolean isDispaly=false;
    private EditText ed_name,ed_password;
    private TextView idforgeter;
    private Button btn_login;
    private Toolbar toolbar_login;

    private List<Person> list = new ArrayList<Person>( );
    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_login );
        requestWindowFeature( Window.FEATURE_NO_TITLE );




        initUI();
        TextWatcher textWatcher=new TextWatcher( ){
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (ed_name.getText().length()==0||ed_password.getText().length()==0){
                    btn_login.setEnabled( false );
                }else {
                    btn_login.setEnabled( true );
                }

            }
        };
        ed_password.addTextChangedListener( textWatcher );
       ed_name.addTextChangedListener( textWatcher );
    }


    private void initUI() {
        ed_name=(EditText) findViewById( R.id.edit_id );
        ed_password=(EditText)findViewById( R.id.edit_password );
        idforgeter=(TextView)findViewById( R.id.forget );
        btn_login=(Button)findViewById( R.id.btn_login );
        toolbar_login=findViewById( R.id.tool_bar_login );

        btn_login.setOnClickListener( this );
        idforgeter.setOnClickListener( this );
    }


    private void setlogin() {
        hideSoftKeyboard(this);
        insert();
        String mName =ed_name.getText( ).toString( ).trim( );
        String pass = ed_password.getText( ).toString( ).trim( );
        List<Person> list = readData();
        if (TextUtils.isEmpty( mName ) || TextUtils.isEmpty( pass )) {
            return;
        } else if (mName.equals( list.get( 0 ).getName() ) && pass.equals( list.get( 0 ).getPassword() )) {
            Toast.makeText( this, "登入成功", Toast.LENGTH_SHORT ).show( );
            Intent intent=new Intent( LoginActivity.this, TermsOfUseActivity.class );
            startActivity( intent );
        } else {
            Toast.makeText( this, "登入失败", Toast.LENGTH_SHORT ).show( );
        }
    }


    private void insert() {
        Cursor cursor = db.query(
                LoginHelper.TABLE_NAME,
                null,
                null,
                null,
                null,
                null,
                null
        );
        if (cursor.moveToNext()) {
            return;
        }
        ContentValues values=new ContentValues(  );
        values.put( "name","abcde" );
        values.put( "password",12345 );
        db.insert( LoginHelper.TABLE_NAME,null,values );

    }
    public List<Person> readData() {

        if (db == null) {
            db = helper.getReadableDatabase( );

        }

        String[] cols = {"name", "password"};
        Cursor cursor = db.query(
                LoginHelper.TABLE_NAME,
                cols,
                null,
                null,
                null,
                null,
                null
        );


        Person person = new Person( );
        while (cursor.moveToNext( )) {
            person.setName( cursor.getString( 0 ) );
            person.setPassword( cursor.getString( 1 ) );
            list.add( person );

        }
        return list;
    }

    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        if (event.getKeyCode() == KeyEvent.KEYCODE_ENTER) {
            //���ؼ���
            hideSoftKeyboard(this);
            return true;
        }
        return super.dispatchKeyEvent(event);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        View view = this.getCurrentFocus();
        if (view != null) {
            hideSoftKeyboard(this);
            return true;
        } else {
            return super.onTouchEvent(event);
        }
    }


    public void hideSoftKeyboard(Activity activity) {
        View view = activity.getCurrentFocus();
        if (view != null) {
            InputMethodManager inputMethodManager = (InputMethodManager)activity.getSystemService( Activity.INPUT_METHOD_SERVICE );
            inputMethodManager.hideSoftInputFromWindow ( view.getWindowToken ( ), InputMethodManager.HIDE_NOT_ALWAYS );
        }
    }

    @Override
    public void onClick(View v) {
      switch (v.getId()){
          case R.id.btn_login:
              setlogin();
             break;
          case R.id.forget:
              Intent intent_web=new Intent( this, WebViewActivity.class );
              intent_web.putExtra( "url", "https://id.smt.docomo.ne.jp/src/utility/detail_01_01.html");
              startActivity( intent_web );

      }
    }
}


