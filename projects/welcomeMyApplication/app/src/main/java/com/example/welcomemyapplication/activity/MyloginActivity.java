package com.example.welcomemyapplication.activity;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.welcomemyapplication.R;
import com.example.welcomemyapplication.adapter.ShopAdapter;
import com.example.welcomemyapplication.data.Person;
import com.example.welcomemyapplication.data.model.ShopInfo;
import com.example.welcomemyapplication.helper.LoginHelper;
import com.example.welcomemyapplication.helper.TuanOpenHelper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MyloginActivity extends Activity implements View.OnClickListener{
    private ImageView ivback;
    private EditText edname, yanzm, edpass;
    private TextView tvweixin, tvqq, reginster;
    private Button login;
    private SQLiteDatabase db;
    LoginHelper helper = new LoginHelper( this );
    private TextView loadData;
    private boolean isLoaded;
    private List<Person> list = new ArrayList<Person>( );
    private ListView mListView;
    private ShopAdapter mAdapter = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_login );




        ivback = (ImageView) findViewById( R.id.login_back );
        edname = (EditText) findViewById( R.id.edsms );
        tvqq = (TextView) findViewById( R.id.qq);
        tvweixin = (TextView) findViewById( R.id.weixin );
        login = (Button) findViewById( R.id.login );
        reginster = (TextView) findViewById( R.id.register );
        yanzm = (EditText) findViewById( R.id.edyan );
        edpass = (EditText) findViewById( R.id.edpass );
        ivback.setOnClickListener( this );
        tvweixin.setOnClickListener( this );
        tvqq.setOnClickListener( this );
        login.setOnClickListener( this );
        reginster.setOnClickListener( this );
        yanzm.setOnClickListener( this );
        edpass.setOnClickListener( this );
    }

    @Override
    public void onClick(View v) {
        switch (v.getId( )) {
            case R.id.edpass:

                break;
            case R.id.login:
                init( );
                break;
            case R.id.register:
                chickReginste( );
                break;
            case R.id.qq:
                break;
            case R.id.weixin:
                break;
            case R.id.login_back:
                finish( );
                break;

        }

    }

    private void init() {
        insert();
        String name = edname.getText( ).toString( ).trim( );
        String pass = edpass.getText( ).toString( ).trim( );
        List<Person> list2 = readData();
        if (TextUtils.isEmpty( name ) || TextUtils.isEmpty( pass )) {
            return;
        } else if (name.equals( list2.get( 0 ).getName() ) && pass.equals( list2.get( 0 ).getPassword() )) {
            Toast.makeText( this, "登入成功", Toast.LENGTH_SHORT ).show( );
        } else {
            Toast.makeText( this, "登入失败", Toast.LENGTH_SHORT ).show( );
        }
    }

    private void chickReginste() {
        Intent intent = new Intent( this, RegisterActivity.class );
        startActivity( intent );
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



    public void insert(){
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





        /**
         * 隐藏软键盘(只适用于Activity，不适用于Fragment)
//         */
//    public void hideSoftKeyboard(Activity activity) {
//        View view = activity.getCurrentFocus ( );
//        if (view != null) {
//            InputMethodManager inputMethodManager = (InputMethodManager) activity.getSystemService ( Activity.INPUT_METHOD_SERVICE );
//            inputMethodManager.hideSoftInputFromWindow ( view.getWindowToken ( ), InputMethodManager.HIDE_NOT_ALWAYS );
//        }

}



