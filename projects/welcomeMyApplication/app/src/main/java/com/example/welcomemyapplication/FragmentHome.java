package com.example.welcomemyapplication;

import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

public class FragmentHome extends Fragment implements LocationListener{

    private TextView topCity;
    private String cityName;
    private LocationManager locationManager;
    private GridView gridView;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate ( R.layout.fragment_home, null );
        topCity = (TextView) view.findViewById ( R.id.topCity );
        gridView = (GridView) view.findViewById ( R.id.gv );
        topCity.setOnClickListener ( new View.OnClickListener ( ){
            @Override
            public void onClick(View view) {
                switch (view.getId ( )) {
                    case R.id.topCity:
                        startActivity ( new Intent ( getActivity ( ), CityActivity.class ) );
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

    }