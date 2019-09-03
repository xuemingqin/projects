package com.example.welcomemyapplication.activity;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.welcomemyapplication.R;
import com.example.welcomemyapplication.data.model.ShopInfo;
import com.example.welcomemyapplication.model.Model;
import com.example.welcomemyapplication.views.LoadImg;
import com.example.welcomemyapplication.views.MyScrollView;

public class TuanDetailsActivity extends AppCompatActivity implements MyScrollView.OnScrollListener{

    private ShopInfo Values;
    private LoadImg loadImg;
    private ImageView mTuan_details_back, mTuan_details_share,
            mTuan_details_off, mTuan_details_img;
    private MyScrollView mTuan_details_scroll;
    private TextView mTuan_details_money1, mTuan_details_money2,
            mTuan_details_money3, mTuan_details_money4, mTuan_details_qianggou,
            mTuan_details_qianggou2;
    private RelativeLayout Tuan_details_tuangou;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature( Window.FEATURE_NO_TITLE);
        setContentView( R.layout.activity_tuan_details);
        Bundle bund = getIntent().getBundleExtra("value");
        Values = (ShopInfo) bund.getSerializable("ShopInfo");
        initView();
        loadImg = new LoadImg(TuanDetailsActivity.this);
        mTuan_details_img.setTag( Model.SHOPLISTIMGURL + Values.getIname());
//        Bitmap bit = loadImg.loadImage(mTuan_details_img, Model.SHOPLISTIMGURL
//                + Values.getIname(), new ImageDownloadCallBack() {
//            @Override
//            public void onImageDownload(ImageView imageView, Bitmap bitmap) {
//                mTuan_details_img.setImageBitmap(bitmap);
//            }
//        });
//        if (bit != null) {
//            mTuan_details_img.setImageBitmap(bit);
//        }
    }

    private void initView() {
        mTuan_details_back = (ImageView) findViewById(R.id.Tuan_details_back);
        mTuan_details_share = (ImageView) findViewById(R.id.Tuan_details_share);
        mTuan_details_off = (ImageView) findViewById(R.id.Tuan_details_off);
        mTuan_details_img = (ImageView) findViewById(R.id.Tuan_details_img);
        Tuan_details_tuangou = (RelativeLayout) findViewById(R.id.Tuan_details_tuangou);
        mTuan_details_scroll = (MyScrollView) findViewById(R.id.Tuan_details_scroll);
        mTuan_details_money1 = (TextView) findViewById(R.id.Tuan_details_money1);
        mTuan_details_money2 = (TextView) findViewById(R.id.Tuan_details_money2);
        mTuan_details_qianggou = (TextView) findViewById(R.id.Tuan_details_qianggou);
        mTuan_details_money3 = (TextView) findViewById(R.id.Tuan_details_money3);
        mTuan_details_money4 = (TextView) findViewById(R.id.Tuan_details_money4);
        mTuan_details_qianggou2 = (TextView) findViewById(R.id.Tuan_details_qianggou2);
        MyOnClickListener myonclick = new MyOnClickListener();
        mTuan_details_back.setOnClickListener(myonclick);
        mTuan_details_share.setOnClickListener(myonclick);
        mTuan_details_off.setOnClickListener(myonclick);
        mTuan_details_qianggou.setOnClickListener(myonclick);
        mTuan_details_qianggou2.setOnClickListener(myonclick);
        mTuan_details_scroll.setOnScrollListener(this);
    }

    private class MyOnClickListener implements View.OnClickListener {
        public void onClick(View v) {
            int mID = v.getId();
            switch (mID) {
                case R.id.Tuan_details_back:
                    TuanDetailsActivity.this.finish();
                    break;
            }

        }
    }

    @Override
    public void onScroll(int scrollY) {
        if (scrollY >= mTuan_details_img.getHeight()) {
            Tuan_details_tuangou.setVisibility( View.VISIBLE);
        } else {
            Tuan_details_tuangou.setVisibility(View.GONE);
        }
    }
}
