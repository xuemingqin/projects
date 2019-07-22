package com.example.welcomemyapplication;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class Fragmenttuan extends Fragment{
    private ListView lv;
    private Adapter adapter;
    private List<Goods> listGoods;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate ( R.layout.activity_tuan ,null);
        lv=(ListView)view.findViewById ( R.id.lvbiao );

        listGoods = new ArrayList<> ();
        loadDatas ();

        lv.setAdapter ( new MyApater () );
        return view;

    }

    public void loadDatas() {
        for (int i=0; i < 4; i++) {
            Goods goods = new Goods ();
            goods.setBought ( 2 );
            goods.setCount ( 22 );
            goods.setPage ( 1 );
            goods.setPrice ( "24" );
            goods.setSize ( 10 );
            goods.setSortTitle ( "芋圆:" + i );
            listGoods.add(goods);
        }
    }


    public class MyApater extends BaseAdapter{

        @Override
        public int getCount() {
            return listGoods.size ();
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
                hoder = new ViewHoder ( );
                convertView = LayoutInflater.from ( parent.getContext ( ) ).inflate ( R.layout.item_tuan, null );
                hoder.tvstit = (TextView) convertView.findViewById ( R.id.smlple_title );
                hoder.tvdtit = (TextView) convertView.findViewById ( R.id.detail );
                hoder.tvp = (TextView) convertView.findViewById ( R.id.item_price );
                hoder.tvyuan = (TextView) convertView.findViewById ( R.id.item_yuanjia );
                hoder.tvheji = (TextView) convertView.findViewById ( R.id.item_heji );
                hoder.iv = (ImageView) convertView.findViewById ( R.id.iv_item );
                convertView.setTag ( hoder );

            } else{
                hoder =(ViewHoder) convertView.getTag ( );
            }
            Goods goods=listGoods.get ( position );
            StringBuffer abf=new StringBuffer ( "¥230" );
            hoder.tvheji.setText ( goods.getBought ()+"份" );
            hoder.tvp.setText ( "¥"+goods.getPrice () );
            hoder.tvstit.setText ( goods.getSortTitle () );
            hoder.tvyuan.setText ( goods.getTitle () );
            return convertView;
        }
        class ViewHoder{
            TextView tvstit,tvdtit,tvp,tvyuan,tvheji;
            ImageView iv;
        }
    }



}
