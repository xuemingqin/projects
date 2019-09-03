package com.itaccess.interphone.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.TextView;

import com.itaccess.interphone.R;
import com.itaccess.interphone.utils.PreferencesUtil;
import com.itaccess.interphone.utils.StringUtil;

import java.util.List;

/**
 * Created by linxi on 2019/01/24.
 */

public class RingtoneDialogAdapter extends RecyclerView.Adapter<RingtoneDialogAdapter.ViewHolder> {

    private LayoutInflater mInflater;
    private List<String> mData;
    private Context mContext;
    private OnRecyclerListener mListener;
    private int selectedRadioBtnPosition;
    private boolean isFirst;

    public RingtoneDialogAdapter(Context context, List<String> data, OnRecyclerListener listener) {
        mInflater = LayoutInflater.from(context);
        mContext = context;
        mData = data;
        mListener = listener;
        isFirst = true;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int position) {
        // 表示するレイアウトを設定
        return new ViewHolder(mInflater.inflate(R.layout.ringtone_dialog_setting_item, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {
        // データ表示
        if (mData != null && mData.size() > position && mData.get(position) != null) {
            viewHolder.mItemName.setText(mData.get(position));
            clearRadioButtonCheck(viewHolder);
            //初回起動の場合、Preferenceから保存された着信音を取得して、設定する
            if (isFirst) {
                getSelectedPosition();
                isFirst = false;
            }
            if (selectedRadioBtnPosition == position) {
                viewHolder.mRadioButton.setChecked(true);
            }
        }

        // クリック処理
        viewHolder.mRadioButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mListener != null) {
                    mListener.onRecyclerClicked(position);
                }
                selectedRadioBtnPosition = position;
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

    private void getSelectedPosition() {
        String ringtone = PreferencesUtil.getRingtone(mContext);
        if (!StringUtil.isNull(ringtone)) {
            for (int i = 0; i< mData.size(); i++) {
                if (ringtone.equals(mData.get(i))) {
                    selectedRadioBtnPosition = i;
                }
            }
        } else {
            selectedRadioBtnPosition = 0;
        }
    }

    private void clearRadioButtonCheck(ViewHolder viewHolder) {
        viewHolder.mRadioButton.setChecked(false);
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        TextView mItemName;
        RadioButton mRadioButton;

        public ViewHolder(View itemView) {
            super(itemView);
            mItemName = (TextView) itemView.findViewById(R.id.ringtone_recycler_item_name);
            mRadioButton = (RadioButton) itemView.findViewById(R.id.ringtone_recycler_radiobutton);
        }
    }

    public interface OnRecyclerListener {

        void onRecyclerClicked(int position);

    }

}
