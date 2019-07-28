package com.example.welcomemyapplication.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.welcomemyapplication.helper.MyUtils;
import com.example.welcomemyapplication.R;

public class AllCategoryActivity extends Activity{
    private ListView lv;

    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate ( savedInstanceState );
        setContentView ( R.layout.home_index_all );
        lv=(ListView)findViewById ( R.id.listv );
        lv.setAdapter ( new MyAdapter () );
    }
    public class MyAdapter extends BaseAdapter{

        @Override
        public int getCount() {
            return MyUtils.allCategray.length;
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHoder hoder = null;
            if (convertView == null) {
                hoder=new ViewHoder ();
                convertView = LayoutInflater.from ( AllCategoryActivity.this ).
                        inflate ( R.layout.home_index_all_item, null );
                hoder.ivall=(ImageView)convertView.findViewById ( R.id.iv00);
                hoder.tvall=(TextView)convertView.findViewById ( R.id.tv01 );
                hoder.tv01=(TextView)convertView.findViewById ( R.id.tv1000 );
                convertView.setTag ( hoder );

            }else{
                hoder =(ViewHoder) convertView.getTag ( );

            }
            hoder.tvall.setText ( MyUtils.allCategray[position] );
            hoder.ivall.setImageResource ( MyUtils.allCategrayImages[position] );
            return convertView;
        }

    }

     public class ViewHoder{
        TextView tvall,tv01;
        ImageView ivall;
    }
     public void  back_click(View view){
        switch (view.getId ()){
            case R.id.ivallp:
                finish ();
                break;
        }
     }
        }


