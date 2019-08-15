package com.example.docomomyapplication.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.TracingConfig;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.docomomyapplication.R;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;
import com.youth.banner.listener.OnBannerListener;
import com.youth.banner.loader.ImageLoader;

import java.util.ArrayList;

public class SetingFragment extends Fragment{
    private Banner banner;
    private ArrayList<String> list_path;
    private ArrayList<String> list_title;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view= inflater.inflate( R.layout.activity_seting,null );
        initData();
        initView(view);

        return view;

    }

    private void initData() {
        list_path=new ArrayList<>(  );
        list_title=new ArrayList<>(  );
        list_path.add("http://img.lanrentuku.com/img/allimg/1707/14988864745279.jpg");

        list_path.add("http://img.lanrentuku.com/img/allimg/1706/14987452973642.jpg");

        list_path.add("http://img.lanrentuku.com/img/allimg/1605/1464490533194.jpg");
        list_path.add("http://img.lanrentuku.com/img/allimg/1605/14644914727589.jpg");
        list_title.add("我爱NBA");
        list_title.add("我爱科比布莱恩特");
        list_title.add("我爱NBA");
        list_title.add("我爱科比布莱恩特");

    }

    private void initView(View view) {
        banner=view.findViewById( R.id.banner1 );

        banner.setBannerStyle( BannerConfig.CIRCLE_INDICATOR_TITLE_INSIDE );
        banner.setImageLoader( new MyLoader() );
        banner.setBannerAnimation( Transformer.Default );
        banner.setBannerTitles( list_title );
        banner.setDelayTime( 3000 );
        banner.isAutoPlay( true );
        banner.setIndicatorGravity( BannerConfig.CENTER );
        banner.setImages( list_path ).setOnBannerListener( new OnBannerListener( ){
            @Override
            public void OnBannerClick(int position) {
                Toast.makeText(getActivity()
                        , "你点了第" + (position + 1) + "张轮播图", Toast.LENGTH_SHORT).show();
            }
        } );
         banner.start();
    }
    private  class MyLoader extends ImageLoader{

        @Override
        public void displayImage(Context context, Object path, ImageView imageView) {
            Glide.with( context).load(path ).into( imageView );
        }
    }
}
