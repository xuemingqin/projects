package com.itaccess.interphone.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.itaccess.interphone.R;
import com.itaccess.interphone.utils.WindowUtil;

import java.util.List;


/**
 * Created by linxi on 2019/01/19.
 */

public class SettingAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    public static final String TAG = "SettingAdapter";
    public static final String HEADER = "header";
    public static final int TYPE_HEADER = 1;
    public static final int TYPE_CONTENT = 2;
    public static final int TYPE_FOOTER = 3;

    private Context mContext;
    private List<String> mList;
    private OnItemClickListener mOnItemClickListener = null;


    public SettingAdapter (Context mContext, List<String> mList) {
        this.mContext = mContext;
        this.mList = mList;
        mOnItemClickListener = (OnItemClickListener)mContext;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View mView;
        switch (viewType) {
            case TYPE_HEADER:
                mView = LayoutInflater.from(mContext).inflate(R.layout.recyclerview_setting_header, parent, false);
                return new SettingHeaderViewHolder(mView);
            case TYPE_CONTENT:
                mView = LayoutInflater.from(mContext).inflate(R.layout.recyclerview_setting, parent, false);
                return new SettingViewHolder(mView);
            case TYPE_FOOTER:
                mView = LayoutInflater.from(mContext).inflate(R.layout.recyclerview_setting_footer, parent, false);
                return new SettingFooterViewHolder(mView);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int positiion) {
        if (holder instanceof SettingViewHolder) {
            ((SettingViewHolder) holder).mSettingTitle.setText(mList.get(positiion));
            setNormalItemColor(((SettingViewHolder) holder).mLayout);
            //itemタッチ押下すると、itemの背景を変える
            ((SettingViewHolder) holder).mLayout.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View view, MotionEvent motionEvent) {
                    int action = motionEvent.getAction();
                    switch (action) {
                        case MotionEvent.ACTION_DOWN:
                            view.setBackgroundColor(mContext.getResources().getColor(R.color.common_light_grey));
                            Log.d(TAG, "ACTION_DOWN");
                            break;
                        case MotionEvent.ACTION_UP:
                            mOnItemClickListener.onItemClick(positiion);
                            Log.d(TAG, "ACTION_UP");
                            break;
                        case MotionEvent.AXIS_SIZE:
                            view.setBackgroundColor(mContext.getResources().
                                    getColor(R.color.setting_recycler_item_background_color));
                            Log.d(TAG, "AXIS_SIZE");
                            break;
                    }
                    return true;
                }
            });
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (isPositionFooter(position)) {
            return TYPE_FOOTER;
        }

        if (HEADER.equals(mList.get(position))) {
            return TYPE_HEADER;
        } else {
            return TYPE_CONTENT;
        }
    }

    @Override
    public int getItemCount() {
        return mList.size() + 1;
    }


    private Boolean isPositionFooter(int position) {
        return position == mList.size() ? true : false;
    }

    private void setNormalItemColor(final RelativeLayout mLayout) {
        mLayout.setBackgroundColor(mContext.getResources().getColor(R.color.setting_recycler_item_background_color));
    }

    private class SettingViewHolder extends RecyclerView.ViewHolder {

        private TextView mSettingTitle;

        private RelativeLayout mLayout;

        public SettingViewHolder(View mView) {
            super(mView);

            mSettingTitle = mView.findViewById(R.id.setting_title);
            mLayout = mView.findViewById(R.id.setting_item_layout);
        }
    }

    private class SettingHeaderViewHolder extends RecyclerView.ViewHolder {

        public SettingHeaderViewHolder(View mView) {
            super(mView);
        }
    }

    private class SettingFooterViewHolder extends RecyclerView.ViewHolder {

        private RelativeLayout mLayout;

        public SettingFooterViewHolder(View mView) {
            super(mView);
            mLayout = mView.findViewById(R.id.setting_recycler_footer);
            setFooterLayoutHeight();
        }

        /**
         *  footerLayoutの高さを設定
         * */
        private void setFooterLayoutHeight() {
            float itemHeight = mContext.getResources().getDimension(R.dimen.setting_recyclerview_height);
            float otherHeight = itemHeight * mList.size() + WindowUtil.getStatusHeight(mContext) +
                    WindowUtil.getActionBarHeight(mContext);
            float footerHeight = WindowUtil.getWindowHeight(mContext) - otherHeight;
            ViewGroup.LayoutParams mParams = mLayout.getLayoutParams();
            mParams.height = (int) footerHeight;
            mLayout.setLayoutParams(mParams);
        }
    }

    public interface OnItemClickListener {
        void onItemClick(int position);
    }


}
