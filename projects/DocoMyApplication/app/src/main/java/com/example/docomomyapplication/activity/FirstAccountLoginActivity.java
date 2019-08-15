package com.example.docomomyapplication.activity;

import android.app.Activity;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.example.docomomyapplication.R;
import com.example.docomomyapplication.utils.LoginHelper;
import com.example.docomomyapplication.data.Person;

import java.util.ArrayList;
import java.util.List;


public class FirstAccountLoginActivity extends Activity implements View.OnClickListener{
    private EditText name;
    private EditText password;
    private CheckBox checkBox;
    private Button login;
    private List<Person> list = new ArrayList<Person>( );
    private SQLiteDatabase db;
    LoginHelper helper = new LoginHelper( this );

    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_d_login );
        db = helper.getWritableDatabase( );
        initUI();
    }

    private void initUI() {
        name=(EditText) findViewById( R.id.myid );
        password=(EditText) findViewById( R.id.password );
        checkBox=(CheckBox)findViewById( R.id.checkbox1 );
        login=(Button)findViewById( R.id.login );
        login.setOnClickListener( this );
        checkBox.setOnClickListener( this );
        password.setOnClickListener( this );

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.checkbox1:
                break;
            case R.id.login:
                setlogin();
                break;
        }

    }

    private void setlogin() {
        insert();
        String Mname =name.getText( ).toString( ).trim( );
        String pass = password.getText( ).toString( ).trim( );
        List<Person> list2 = readData();
        if (TextUtils.isEmpty( Mname ) || TextUtils.isEmpty( pass )) {
            return;
        } else if (name.equals( list2.get( 0 ).getName() ) && pass.equals( list2.get( 0 ).getPassword() )) {
            Toast.makeText( this, "登入成功", Toast.LENGTH_SHORT ).show( );
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

}
