package com.example.interphone;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

public class TermsOfUseActivity extends AppCompatActivity {
    private Button btn_ok;
    private CheckBox checkBox;
    private TextView text_ok;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_terms_of_use );
        initId();
    }

    private void initId() {
        btn_ok=(Button)findViewById( R.id.btn_ok );
        checkBox=(CheckBox)findViewById( R.id.checkbox );
        text_ok=(TextView)findViewById( R.id.text_ok );

        btn_ok.setOnClickListener( new View.OnClickListener( ){
            @Override
            public void onClick(View v) {
                Intent intent=new Intent( TermsOfUseActivity.this,MainActivity.class );
                startActivity( intent );
            }
        } );
        checkBox.setOnCheckedChangeListener( new CompoundButton.OnCheckedChangeListener( ){
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    text_ok.setEnabled( true );
                    btn_ok.setEnabled( true );
                }else{
                    text_ok.setEnabled( false );
                    btn_ok.setEnabled( false );
                }
            }
        } );
    }

}
