package com.example.docomomyapplication.activity;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.InputType;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethod;
import android.view.inputmethod.InputMethodManager;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.docomomyapplication.R;
import com.example.docomomyapplication.utils.LoginHelper;
import com.example.docomomyapplication.data.Person;

import java.util.ArrayList;
import java.util.List;


public class FirstAccountLoginActivity extends Activity implements View.OnClickListener{

    private static String ED_NAME_KEY = "name";
    private static String ED_PASSWORD_KEY = "password";
    private EditText ed_name;
    private EditText ed_password;
    private CheckBox checkBox;
    private Button login;
    private SharedPreferences sp;
    private List<Person> list = new ArrayList<Person>( );
    private SQLiteDatabase db;
    private ImageView iv_back;
    private Button btn_see;
    private TextView tvbom;
    private TextView tv_notice;
    private long exitTime=0;
    LoginHelper helper = new LoginHelper( this );
    Boolean isDispaly=false;


    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_d_login );

        db = helper.getWritableDatabase( );
        sp=getSharedPreferences( "login",MODE_PRIVATE );
        initUI();
        String nameValue=sp.getString( ED_NAME_KEY,"" );
        String passwordValue=sp.getString( ED_PASSWORD_KEY,"" );
        ed_name.setText( nameValue );
        ed_password.setText( passwordValue );
        if (!TextUtils.isEmpty(nameValue) || !TextUtils.isEmpty( passwordValue )){
            checkBox.setChecked( true );

        }
    }

//    @Override
//    public void onBackPressed() {
//        if (webView.canGoBack( )) {
//            webView.goBack( );
//        } else {
//            webView.setVisibility( View.GONE );
////            if ((System.currentTimeMillis( ) - exitTime) > 2000) {
////                Toast.makeText( getApplicationContext( ), "再按一次退出程序", Toast.LENGTH_SHORT ).show( );
////                exitTime = System.currentTimeMillis( );
////            } else {
////                super.onBackPressed( );
////            }
//        }
//    }

    private void initUI() {
        ed_name=(EditText) findViewById( R.id.myid );
        ed_password=(EditText) findViewById( R.id.password );
        checkBox=(CheckBox)findViewById( R.id.checkbox1 );
        login=(Button)findViewById( R.id.login );
        iv_back=(ImageView)findViewById( R.id.iv_reback);
        btn_see=(Button)findViewById( R.id.btn_see );
        tvbom=(TextView)findViewById( R.id.tvdtowa );
        tv_notice = (TextView)findViewById( R.id.tv_notice );

        login.setOnClickListener( this );
        checkBox.setOnClickListener( this );
        ed_password.setOnClickListener( this );
        btn_see.setOnClickListener( this );
        iv_back.setOnClickListener( this );
        tvbom.setOnClickListener( this );
        tv_notice.setOnClickListener( this );
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.checkbox1:
                checkon();
                break;
            case R.id.login:
                setlogin();
                break;
            case R.id.btn_see:
               setWatch();
                break;
            case R.id.iv_reback:
                Intent intent = new Intent(getApplicationContext(), TutorialActivity.class);
                startActivity(intent);
                finish();
                break;
            case R.id.tvdtowa:
                Intent intent_web=new Intent( getApplicationContext(), WebViewActivity.class );
                intent_web.putExtra( "url", "https://id.smt.docomo.ne.jp/src/utility/index.html");
                startActivity( intent_web );
//                webView.setWebViewClient( new WebViewClient( ){
//                    @Override
//                    public boolean shouldOverrideUrlLoading(WebView view,String url) {
//                        view.loadUrl( url );
//                        return true;
//                    }
//                } );
//                webView.getSettings( ).setJavaScriptEnabled( true );
//                webView.loadUrl( "https://id.smt.docomo.ne.jp/src/utility/index.html" );
                //this.addContentView( webView, new LinearLayout.LayoutParams( ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT ) );
//                webView.setVisibility( View.VISIBLE );
                break;
            case R.id.tv_notice:
                Intent intent_notice=new Intent( getApplicationContext(), WebViewActivity.class );
                intent_notice.putExtra( "url", "https://id.smt.docomo.ne.jp/src/utility/notice.html" );
                startActivity( intent_notice );
//                webView.setWebViewClient( new WebViewClient( ){
//                    @Override
//                    public boolean shouldOverrideUrlLoading(WebView view,String url) {
//                        view.loadUrl( url );
//                        return true;
//                    }
//                } );
//                webView.getSettings( ).setJavaScriptEnabled( true );
//                webView.loadUrl( "https://id.smt.docomo.ne.jp/src/utility/notice.html" );
//                webView.setVisibility( View.VISIBLE );
//                break;

        }

    }

    private void setWatch() {
        int cursorPos=ed_password.getSelectionStart();
        if (isDispaly==false){
            ed_password.setInputType( InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD );
            ed_password.setSelection( cursorPos );
            btn_see.setBackgroundResource( R.drawable.icon_display_non);
            isDispaly=true;

        }else {
            ed_password.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
            ed_password.setSelection(cursorPos);
            btn_see.setBackgroundResource( R.drawable.icon_display );
            isDispaly=false;
        }
    }

    private void checkon() {

        SharedPreferences.Editor edit = sp.edit( );
        boolean checked=checkBox.isChecked();
        if (checked){
            String name=ed_name.getText().toString().trim();
            String password=ed_password.getText().toString().trim();
            if (TextUtils.isEmpty( name ) || TextUtils.isEmpty( password )){
                return;
            }

            edit.putString( ED_NAME_KEY,name );
            edit.putString( ED_PASSWORD_KEY,password );

        } else {
            edit.putString( ED_NAME_KEY,"" );
            edit.putString( ED_PASSWORD_KEY,"" );
        }
        boolean commit=edit.commit();
        if (commit){
            Toast.makeText( this,"パスワード保存しました" ,Toast.LENGTH_SHORT).show();
        }

    }

    private void setlogin() {
        hideSoftKeyboard(this);
        insert();
        String mName =ed_name.getText( ).toString( ).trim( );
        String pass = ed_password.getText( ).toString( ).trim( );
        List<Person> list2 = readData();
        if (TextUtils.isEmpty( mName ) || TextUtils.isEmpty( pass )) {
            return;
        } else if (mName.equals( list2.get( 0 ).getName() ) && pass.equals( list2.get( 0 ).getPassword() )) {
            Toast.makeText( this, "登入成功", Toast.LENGTH_SHORT ).show( );
            Intent intent=new Intent( FirstAccountLoginActivity.this,PassCodeActivity.class );
            startActivity( intent );
            overridePendingTransition( R.anim.push_in,R.anim.push_out );
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
            //隐藏键盘
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

    /**
     * 隐藏软键盘(只适用于Activity，不适用于Fragment)
     */
    public void hideSoftKeyboard(Activity activity) {
        View view = activity.getCurrentFocus();
        if (view != null) {
            InputMethodManager inputMethodManager = (InputMethodManager)activity.getSystemService( Activity.INPUT_METHOD_SERVICE );
            inputMethodManager.hideSoftInputFromWindow ( view.getWindowToken ( ), InputMethodManager.HIDE_NOT_ALWAYS );
        }
    }

}
