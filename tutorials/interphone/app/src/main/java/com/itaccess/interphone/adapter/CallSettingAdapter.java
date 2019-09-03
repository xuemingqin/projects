package com.itaccess.interphone.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.itaccess.interphone.R;
import com.itaccess.interphone.utils.PreferencesUtil;

import java.util.List;

/**
 * Created by linxi on 2019/01/23.
 */

public class CallSettingAdapter extends RecyclerView.Adapter<CallSettingAdapter.ViewHolder> {

    private Context mContext;
    private LayoutInflater mInflater;
    private List<String> mData;
    private OnRecyclerListener mListener;
    private int clickedItemPosition;

    public CallSettingAdapter(Context context, List<String> data, OnRecyclerListener listener) {
        mContext = context;
        mInflater = LayoutInflater.from(context);
        mData = data;
        mListener = listener;

        clickedItemPosition = getItemSelectedPosition();
    }

    private int getItemSelectedPosition() {
        boolean isSpeakerphoneOn = PreferencesUtil.getSpeakerphoneOn(mContext);
        return isSpeakerphoneOn ? 0 : 1;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        // 表示するレイアウトを設定
        return new ViewHolder(mInflater.inflate(R.layout.call_setting_item_list, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        // データ表示
        if (mData != null && mData.size() > position && mData.get(position) != null) {
            holder.textView.setText(mData.get(position));
            clearImageAndTextColor(holder);
            //TODO
            if (clickedItemPosition == position) {
                setImageAndTextColor(holder);
            }
        }

        // クリック処理
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onRecyclerClicked(position);
                clickedItemPosition = position;
                notifyDataSetChanged();
            }
        });

    }

    @Override
    public int getItemCount() {
        if (mData != null) {
            return mData.size();
        } else {
            return 0;
        }
    }

    private void clearImageAndTextColor(ViewHolder holder) {
        holder.imageView.setVisibility(View.INVISIBLE);
        holder.textView.setTextColor(mContext.getResources().
                getColor(R.color.common_black));
    }

    private void setImageAndTextColor(ViewHolder holder) {
        holder.imageView.setVisibility(View.VISIBLE);
        holder.textView.setTextColor(mContext.getResources().
                getColor(R.color.call_setting_voice_output_text_selected));
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        public TextView textView;
        public ImageView imageView;

        public ViewHolder(View itemView) {
            super(itemView);
            textView = (TextView) itemView.findViewById(R.id.call_setting_list_item_text);
            imageView = (ImageView) itemView.findViewById(R.id.call_setting_recycler_img);
        }
    }

    public interface OnRecyclerListener {

        void onRecyclerClicked(int position);

    }
}
