package com.example.welcomemyapplication.fragment;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.welcomemyapplication.R;
import com.example.welcomemyapplication.adapter.ShopAdapter;
import com.example.welcomemyapplication.data.model.ShopInfo;
import com.example.welcomemyapplication.helper.TuanOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class Fragmenttuan extends Fragment {

    private TextView tv;
    private TextView loadData;
    private boolean isLoaded;
    private List<ShopInfo> list = new ArrayList<ShopInfo>();
    private ListView mListView;
    private ShopAdapter mAdapter = null;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        View view= inflater.inflate( R.layout.index_tuan, null);

        initView(view);
        loadData.setOnClickListener( new View.OnClickListener( ){
            @Override
            public void onClick(View v) {
                if (!isLoaded) {
                    insertData( );
                    readData();
                    mAdapter = new ShopAdapter(list, getActivity());
                    mListView.setAdapter(mAdapter);
                    isLoaded = true;
                    loadData.setEnabled( false );
                    loadData.setTextColor(getActivity().getResources().getColor(R.color.gray ));
                }
            }
        } );
        mListView.setOnItemClickListener( new ListOnItemClickListener());
        return view;
    }

    private void initView(View view) {
        mListView = (ListView) view.findViewById(R.id.ShopListView);
        tv = (TextView) view.findViewById( R.id.index_tuan_list6_tv );
        loadData = (TextView) view.findViewById( R.id.loadData );
//        tv.getPaint().setFlags( Paint.STRIKE_THRU_TEXT_FLAG);
    }

    private class ListOnItemClickListener implements AdapterView.OnItemClickListener{
        public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
                                long arg3) {
            Toast.makeText( getActivity(), "点击了第 " + arg2 + " Item", Toast.LENGTH_SHORT ).show( );
        }
    }


    private TuanOpenHelper helper;
    private SQLiteDatabase db;
    public void readData(){
        if(helper == null){
            helper = new TuanOpenHelper(getActivity().getApplicationContext());
        }

        if(db == null){
            db = helper.getReadableDatabase();
        }
        Log.d("debug","**********Cursor");

        String[] cols = {"sid", "iid", "sname", "stype", "saddress", "snear", "stel",
                "stime", "szhekou", "smembercard", "sper", "smoney", "snum", "slevel",
                "sflag_tuan", "sflag_quan", "sflag_ding", "sflag_ka", "longitude",
                "latitude", "sintroduction", "sdetails", "stips", "sflag_promise"};
        Cursor cursor = db.query(
                TuanOpenHelper.TABLE_NAME,
                cols,
                null,
                null,
                null,
                null,
                null
        );

        cursor.moveToFirst();

        while(cursor.moveToNext()){
            ShopInfo shop = new ShopInfo();
            shop.setSid( cursor.getString(0) );
            shop.setSname( cursor.getString(1) );
            shop.setSaddress( cursor.getString(2) );
            shop.setSnear( cursor.getString(3) );
            shop.setStel( cursor.getString(4) );
            shop.setStime( cursor.getString(5) );
            shop.setSzhekou( cursor.getString(6) );
            shop.setSmembercard( cursor.getString(7) );
            shop.setSper( cursor.getString(8) );
            shop.setSmoney( cursor.getString(9) );
            shop.setSnum( cursor.getString(10) );
            shop.setSlevel( cursor.getString(11) );
            shop.setSflag_tuan( cursor.getString(12) );
            shop.setSflag_quan( cursor.getString(13) );
            shop.setSflag_ding( cursor.getString(14) );
            shop.setSflag_ka( cursor.getString(15) );
            shop.setLongitude( cursor.getString(16) );
            shop.setLatitude( cursor.getString(17) );
            shop.setSintroduction( cursor.getString(18) );
            shop.setSdetails( cursor.getString(19) );
            shop.setStips( cursor.getString(20) );
            shop.setSflag_promise( cursor.getString(21) );
            shop.setIname( cursor.getString(22) );
            list.add(shop);
        }
        db.close();
    }

    private void insertData(){

//        ContentValues values = new ContentValues();
//        values.put("company", com);
//        values.put("stockprice", price);
//
//        db.insert("testdb", null, values);
        if(helper == null) {
            helper = new TuanOpenHelper( getActivity( ).getApplicationContext( ) );

            if (db == null) {
                db = helper.getReadableDatabase();
                Cursor cursor = db.query(
                        TuanOpenHelper.TABLE_NAME,
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

                String sql = "INSERT INTO "+ TuanOpenHelper.TABLE_NAME +" ('sid', 'iid', 'sname', 'stype', 'saddress', 'snear', 'stel', 'stime', 'szhekou', 'smembercard', 'sper', 'smoney', 'snum', 'slevel', 'sflag_tuan', 'sflag_quan', 'sflag_ding', 'sflag_ka', 'longitude', 'latitude', 'sintroduction', 'sdetails', 'stips', 'sflag_promise') VALUES\n" +
                        "(1, 20, '兴农食府', '团购', '南兴街', '工业大学', '1337289294', '每日/8:00-23:00', '会员专享5折优惠', '0', '25', '12', '200', '2', '1', '0', '0', '0', '45.7222', '126.6666', '热爱烘焙的人都有一个快乐的梦想——让更多人尝到自己做的美味，让更多人幸福和感动。巴黎贝甜就一直走在这', '适用范围：店内产品，除月饼、圣诞节蛋糕、粽子、储值卡外通用店内人均消费：20元/人', '购买须知有效期：2013.8.4 至 2014.2.4（周末、法定节假日通用使用时间：09:00-2', '0'),\n" +
                        "(2, 21, '如家饭庄', '生活服务', '学府头道街', '工业大学', '1381894841', '每日/9:00-20:00', '会员专享6折优惠', '0', '25', '66', '200', '5', '0', '1', '0', '1', '45.7202', '126.6070', '热爱烘焙的人都有一个快乐的梦想——让更多人尝到自己做的美味，让更多人幸福和感动。巴黎贝甜就一直走在这', '适用范围：店内产品，除月饼、圣诞节蛋糕、粽子、储值卡外通用店内人均消费：20元/人', '购买须知有效期：2013.8.4 至 2014.2.4（周末、法定节假日通用使用时间：09:00-2', '1'),\n" +
                        "(3, 22, '天津万源龙顺度假庄园', '团购', '测绘路', '工业大学', '1357901931', '每日/7:00-23:00', '会员专享5折优惠', '0', '25', '55', '200', '5', '1', '0', '0', '0', '45.7119', '126.6622', '热爱烘焙的人都有一个快乐的梦想——让更多人尝到自己做的美味，让更多人幸福和感动。巴黎贝甜就一直走在这', '适用范围：店内产品，除月饼、圣诞节蛋糕、粽子、储值卡外通用店内人均消费：20元/人', '购买须知有效期：2013.8.4 至 2014.2.4（周末、法定节假日通用使用时间：09:00-2', '1'),\n" +
                        "(4, 23, '集贤大酒家', '生活服务', '正义路', '爱建路', '1361904732', '每日/7:00-22:00', '会员专享5折优惠', '0', '25', '44', '200', '5', '1', '0', '1', '0', '45.7888', '126.1025', '热爱烘焙的人都有一个快乐的梦想——让更多人尝到自己做的美味，让更多人幸福和感动。巴黎贝甜就一直走在这', '适用范围：店内产品，除月饼、圣诞节蛋糕、粽子、储值卡外通用店内人均消费：20元/人', '购买须知有效期：2013.8.4 至 2014.2.4（周末、法定节假日通用使用时间：09:00-2', '1'),\n" +
                        "(5, 24, '苏浙酒楼', '休闲娱乐', '保健路', '爱建路', '1358183413', '每日/8:00-22:00', '会员专享8折优惠', '0', '25', '89', '200', '5', '1', '0', '0', '0', '45.6993', '126.5993', '热爱烘焙的人都有一个快乐的梦想——让更多人尝到自己做的美味，让更多人幸福和感动。巴黎贝甜就一直走在这', '适用范围：店内产品，除月饼、圣诞节蛋糕、粽子、储值卡外通用店内人均消费：20元/人', '购买须知有效期：2013.8.4 至 2014.2.4（周末、法定节假日通用使用时间：09:00-2', '1'),\n" +
                        "(6, 25, '马记烧卖海鲜城', '生活服务', '宁安路', '爱建路', '1581289347', '每日/9:00-23:00', '会员专享5折优惠', '0', '25', '23', '200', '4', '1', '0', '0', '0', '45.6921', '126.6126', '热爱烘焙的人都有一个快乐的梦想——让更多人尝到自己做的美味，让更多人幸福和感动。巴黎贝甜就一直走在这', '适用范围：店内产品，除月饼、圣诞节蛋糕、粽子、储值卡外通用店内人均消费：20元/人', '购买须知有效期：2013.8.4 至 2014.2.4（周末、法定节假日通用使用时间：09:00-2', '0'),\n" +
                        "(7, 26, '原野人家', '团购', '科研街', '爱建路', '1563412344', '每日/8:00-21:00', '会员专享9折优惠', '0', '25', '56', '200', '5', '1', '0', '0', '0', '45.6987', '126.6256', '热爱烘焙的人都有一个快乐的梦想——让更多人尝到自己做的美味，让更多人幸福和感动。巴黎贝甜就一直走在这', '适用范围：店内产品，除月饼、圣诞节蛋糕、粽子、储值卡外通用店内人均消费：20元/人', '购买须知有效期：2013.8.4 至 2014.2.4（周末、法定节假日通用使用时间：09:00-2', '1'),\n" +
                        "(8, 27, '华特美酒楼', '酒店', '哈机街', '爱建路', '1396723842', '每日/7:00-22:00', '会员专享6折优惠', '1', '25', '89', '200', '5', '1', '0', '0', '1', '45.6870', '126.6659', '热爱烘焙的人都有一个快乐的梦想——让更多人尝到自己做的美味，让更多人幸福和感动。巴黎贝甜就一直走在这', '适用范围：店内产品，除月饼、圣诞节蛋糕、粽子、储值卡外通用\\r\\n店内人均消费：20元/人', '购买须知有效期：2013.8.4 至 2014.2.4（周末、法定节假日通用使用时间：09:00-2', '0'),\n" +
                        "(9, 28, '七天', '酒店', '绥化街', '爱建路', '1324324662', '每日/8:00-22:00', '会员专享8折优惠', '1', '25', '78', '200', '2', '1', '0', '0', '0', '45.6849', '126.6250', '热爱烘焙的人都有一个快乐的梦想——让更多人尝到自己做的美味，让更多人幸福和感动。巴黎贝甜就一直走在这', '适用范围：店内产品，除月饼、圣诞节蛋糕、粽子、储值卡外通用\\r\\n店内人均消费：20元/人', '购买须知有效期：2013.8.4 至 2014.2.4（周末、法定节假日通用使用时间：09:00-2', '0'),\n" +
                        "(10, 29, '天域酒家', '酒店', '绿荣街', '林兴路', '1332362456', '每日/7:00-23:00', '会员专享9折优惠', '1', '25', '172', '200', '5', '1', '0', '0', '0', '45.7183', '126.5979', '热爱烘焙的人都有一个快乐的梦想——让更多人尝到自己做的美味，让更多人幸福和感动。巴黎贝甜就一直走在这', '适用范围：店内产品，除月饼、圣诞节蛋糕、粽子、储值卡外通用店内人均消费：20元/人', '购买须知有效期：2013.8.4 至 2014.2.4（周末、法定节假日通用使用时间：09:00-2', '1'),\n" +
                        "(11, 30, '新干线海鲜城', '酒店', '清华大街', '林兴路', '1375430523', '每日/9:00-21:00', '会员专享6折优惠', '1', '25', '102', '200', '5', '1', '0', '0', '0', '45.7163', '126.6001', '热爱烘焙的人都有一个快乐的梦想——让更多人尝到自己做的美味，让更多人幸福和感动。巴黎贝甜就一直走在这', '适用范围：店内产品，除月饼、圣诞节蛋糕、粽子、储值卡外通用店内人均消费：20元/人', '购买须知有效期：2013.8.4 至 2014.2.4（周末、法定节假日通用使用时间：09:00-2', '0');\n";
                db = helper.getWritableDatabase( );
                db.execSQL( sql );
            }

        }
    }

}
