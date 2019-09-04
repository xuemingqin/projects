package com.example.docomomyapplication.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.docomomyapplication.R;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;
import com.youth.banner.listener.OnBannerListener;
import com.youth.banner.loader.ImageLoader;

import java.util.ArrayList;

public class CustomFragment extends Fragment{
    private Banner banner;
    private ArrayList<Integer> imagePath;
    private ArrayList<String> imageTitle;
    private MyImageLoader myImageLoader;

    public static CustomFragment newInstance() {
        CustomFragment fragment = new CustomFragment();
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate( R.layout.activity_custom, null );
        initView( );
        initData( view );
        return view;

    }

    private void initView() {
        imagePath = new ArrayList<>( );
        imageTitle = new ArrayList<>( );
        imagePath.add( R.drawable.timg );
        imagePath.add( R.drawable.timg );
        imagePath.add( R.drawable.timg);
        imageTitle.add( "我是海鸟一号" );
        imageTitle.add( "我是海鸟二号" );
        imageTitle.add( "我是海鸟三号" );


    }

    private void initData(View view) {
        myImageLoader=new MyImageLoader();
        banner= view.findViewById( R.id.banner);
        banner.setBannerStyle( BannerConfig.CIRCLE_INDICATOR_TITLE );
        banner.setBannerAnimation( Transformer.ZoomOutSlide );
        banner.setImageLoader( myImageLoader );
        banner.setBannerTitles(imageTitle);
        banner.setDelayTime( 3000 );
        banner.isAutoPlay( true );
        banner.setIndicatorGravity( BannerConfig.CENTER );
        banner.setImages( imagePath ).setOnBannerListener(new OnBannerListener() {
            @Override
            public void OnBannerClick(int position) {
                Toast.makeText( getActivity(),"你点了第"+(position + 1)+"张轮播图",Toast.LENGTH_SHORT).show();
            }
        });
        banner.start();

    }
//    //设置样式，里面有很多种样式可以自己都看看效果
//        mBanner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR_TITLE);
//    //设置图片加载器
//        mBanner.setImageLoader(mMyImageLoader);
//    //设置轮播的动画效果,里面有很多种特效,可以都看看效果。
//        mBanner.setBannerAnimation(Transformer.ZoomOutSlide);
//    //轮播图片的文字
//        mBanner.setBannerTitles(imageTitle);
//    //设置轮播间隔时间
//        mBanner.setDelayTime(3000);
//    //设置是否为自动轮播，默认是true
//        mBanner.isAutoPlay(true);
//    //设置指示器的位置，小点点，居中显示
//        mBanner.setIndicatorGravity(BannerConfig.CENTER);
//    //设置图片加载地址
//        mBanner.setImages(imagePath)
//            //轮播图的监听
//            .setOnBannerListener(this)
//    //开始调用的方法，启动轮播图。
//                .start();


//    轮播图的监听


//    图片加载类
    private class MyImageLoader extends ImageLoader{
        @Override
        public void displayImage(Context context, Object path, ImageView imageView) {
            Glide.with( context.getApplicationContext( ) )
                    .load( path )
                    .into( imageView );

        }
    }
}
