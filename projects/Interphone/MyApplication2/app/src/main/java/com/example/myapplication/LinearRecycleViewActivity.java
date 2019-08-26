package com.example.myapplication;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class LinearRecycleViewActivity extends AppCompatActivity{
    private RecyclerView recyclerView;
    private LinesrAdapter adapter;
    private Context context;
    private List<String> list;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_linear_recycle_view );
        list = new ArrayList<>();
        for (int i=0;i<10;i++)
            list.add("这是第"+i+"个测试");
        context=this;
        adapter=new LinesrAdapter( context,list );
        recyclerView=(RecyclerView)findViewById( R.id.rv_main );
        recyclerView.setLayoutManager( new LinearLayoutManager(context) );
         recyclerView.setAdapter( adapter);

    }

}
