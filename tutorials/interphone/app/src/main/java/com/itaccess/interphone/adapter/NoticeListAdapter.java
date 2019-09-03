package com.itaccess.interphone.adapter;

import android.support.v7.widget.RecyclerView;
import android.text.InputFilter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.itaccess.interphone.R;
import com.itaccess.interphone.model.Notice;
import com.itaccess.interphone.utils.StringUtil;
import com.itaccess.interphone.widget.LinkMovementMethod;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by linxi on 2019/01/25.
 */

public class NoticeListAdapter extends RecyclerView.Adapter<NoticeListAdapter.ViewHolder> {

    public static final String TAG = "NoticeListAdapter";
    public static final int CONTENT_TEXT_MAXLENTH = 100;

    private List<Notice> mDataset = new ArrayList<>();

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public TextView mTime1;
        public TextView mTime2;
        public TextView mTitle;
        public TextView mContent;

        public ViewHolder(View v) {
            super(v);
            mTime1 = (TextView)v.findViewById(R.id.notice_list_time_1);
            mTime2 = (TextView)v.findViewById(R.id.notice_list_time_2);
            mTitle = (TextView)v.findViewById(R.id.notice_list_title);
            mContent = (TextView)v.findViewById(R.id.notice_list_content);
        }
    }

    public NoticeListAdapter(List<Notice> mData) {
        mDataset = mData;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.notice_list_item, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.mTime1.setText(mDataset.get(position).getTimestamp());
        holder.mTime2.setText(mDataset.get(position).getTimestamp());
        holder.mTitle.setText(mDataset.get(position).getTitle());
        String content = mDataset.get(position).getContent();
        holder.mContent.setText(content);

        //文字数制限：100文字、リンクは文字数に含まない
        List<String> mUrlList = StringUtil.getUrlFromString(content);
        int urlLenth = 0;
        InputFilter[] filters = new InputFilter[1];
        if (mUrlList.size() != 0) {
            for (int i = 0; i < mUrlList.size(); i++) {
                urlLenth = mUrlList.get(i).length();
            }
            filters[0] = new InputFilter.LengthFilter(CONTENT_TEXT_MAXLENTH + urlLenth);
        } else {
            filters[0] = new InputFilter.LengthFilter(CONTENT_TEXT_MAXLENTH);
        }
        holder.mContent.setFilters(filters);
        holder.mContent.setMovementMethod(LinkMovementMethod.getInstance());
    }
    @Override
    public int getItemCount() {
        return mDataset.size();
    }
}
