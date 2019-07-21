package com.example.welcomemyapplication;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;

import java.util.List;

public class FragmentMy extends Fragment implements View.OnClickListener{
    TextView tv;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate ( R.layout.my_index ,null);
        tv = (TextView)view.findViewById ( R.id.mytv );
        tv.setOnClickListener ( this );
        return view;
    }

    @Override
    public void onClick(View v) {
        Intent intent=new Intent ( getActivity (),MyloginActivity.class );
        startActivity ( intent );
    }




}
