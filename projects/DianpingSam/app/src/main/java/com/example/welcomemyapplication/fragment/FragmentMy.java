package com.example.welcomemyapplication.fragment;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.example.welcomemyapplication.R;
import com.example.welcomemyapplication.activity.MyloginActivity;
import com.example.welcomemyapplication.adapter.ShopAdapter;
import com.example.welcomemyapplication.data.model.ShopInfo;
import com.example.welcomemyapplication.helper.LoginHelper;

import java.util.ArrayList;
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
        Intent intent=new Intent ( getActivity (), MyloginActivity.class );
        startActivity ( intent );
    }



}
