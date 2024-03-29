package com.example.welcomemyapplication.fragment;

import android.app.Activity;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.welcomemyapplication.helper.Constants;
import com.example.welcomemyapplication.helper.MyUtils;
import com.example.welcomemyapplication.R;
import com.example.welcomemyapplication.activity.AllCategoryActivity;
import com.example.welcomemyapplication.activity.CityActivity;

public class FragmentHome extends Fragment implements LocationListener{

    private TextView topCity;
    private String cityName;
    private LocationManager locationManager;
    private GridView gridView;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate ( R.layout.fragment_home, null );
        topCity = (TextView) view.findViewById ( R.id.home_top_city );
        gridView = (GridView) view.findViewById ( R.id.gv );
        topCity.setOnClickListener ( new View.OnClickListener ( ){
            @Override
            public void onClick(View view) {
                switch (view.getId ( )) {
                    case R.id.home_top_city:
                        startActivityForResult ( new Intent ( getActivity ( ), CityActivity.class ), Constants.RequestCityCode );
                        break;
                    default:
                }
            }
        } );
        gridView.setAdapter ( new myAdapter ( ) );
        return view;
    }

    @Override
    public void onLocationChanged(Location location) {

    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }

    public class myAdapter extends BaseAdapter{

        @Override
        public int getCount() {
            return MyUtils.navsSort.length;
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
                convertView = LayoutInflater.from ( parent.getContext ( ) ).
                        inflate ( R.layout.home_index_nav_item, null );
                hoder.iv=(ImageView)convertView.findViewById ( R.id.iv00);
                hoder.tv=(TextView)convertView.findViewById ( R.id.tv01 );
                convertView.setTag ( hoder );

            }else{
                hoder =(ViewHoder) convertView.getTag ( );

            }
            hoder.tv.setText ( MyUtils.navsSort[position] );
            hoder.iv.setImageResource ( MyUtils.navsSortImages[position] );
            if (position==MyUtils.navsSort.length-1)
                hoder.iv.setOnClickListener ( new View.OnClickListener ( ){
                    @Override
                    public void onClick(View v) {
                        startActivity ( new Intent ( getActivity (), AllCategoryActivity.class ) );
                    }
                } );
                return convertView;
            }

        }

        class ViewHoder{
            TextView tv;
            ImageView iv;
        }

    //处理返回值结果
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult( requestCode, resultCode, data );
        if(requestCode == Constants.RequestCityCode && resultCode == Activity.RESULT_OK){
            cityName= data.getStringExtra("cityName");
            topCity.setText(cityName);
            Log.e("jhd1", "onActivityResult:"+cityName);
        }
    }

    }