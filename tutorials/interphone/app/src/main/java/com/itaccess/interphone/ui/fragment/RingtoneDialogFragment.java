package com.itaccess.interphone.ui.fragment;


import android.app.DialogFragment;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import com.itaccess.interphone.R;
import com.itaccess.interphone.adapter.RingtoneDialogAdapter;
import com.itaccess.interphone.utils.StringUtil;


/**
 * Created by linxi on 2019/01/24.
 */

public class RingtoneDialogFragment extends DialogFragment implements View.OnClickListener{

    public static final String TAG = "RingtoneDialogFragment";
    private static final String DATA_LIST = "data_list";

    private OnRingtoneDialogListener mDialogListener;
    private boolean isRingtoneChanged;
    private int changedPosition;

    public static RingtoneDialogFragment newInstance(final String[] data) {
        RingtoneDialogFragment mFragment = new RingtoneDialogFragment();
        Bundle args = new Bundle();
        args.putStringArray(DATA_LIST, data);
        mFragment.setArguments(args);
        return mFragment;
    }

    public void setOnRingtoneDialogListener (OnRingtoneDialogListener dialogListener) {
        mDialogListener = dialogListener;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View mRootview = inflater.inflate(R.layout.ringtone_dialog_setting, container, false);
        setDialogHeightAndWidth();
        setContentRecycler(mRootview);
        mRootview.findViewById(R.id.ringtone_cancel).setOnClickListener(this);
        mRootview.findViewById(R.id.ringtone_ok).setOnClickListener(this);
        setCancelable(false);
        isRingtoneChanged = false;
        return mRootview;
    }

    private void setDialogHeightAndWidth() {
        final Window window = getDialog().getWindow();
        int size = (int)getActivity().getResources().getDimension(R.dimen.ringtone_dialog_padding);
        window.getDecorView().setPadding(size, size, size, size);
        WindowManager.LayoutParams wlp = window.getAttributes();
        wlp.gravity = Gravity.CENTER;
        wlp.width = (int)getActivity().getResources().getDimension(R.dimen.ringtone_dialog_width);
        wlp.height = (int)getActivity().getResources().getDimension(R.dimen.ringtone_dialog_height);
        window.setAttributes(wlp);
    }

    private void setContentRecycler(View mRootview) {
        Bundle mBundle = getArguments();
        String[] data = mBundle.getStringArray(DATA_LIST);
        if (data.length != 0) {
            RecyclerView mRecyclerView = (RecyclerView)mRootview.findViewById(R.id.ringtone_recycler);
            final LinearLayoutManager mLinearLayoutManager = new LinearLayoutManager(getActivity());
            mLinearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
            mRecyclerView.setLayoutManager(mLinearLayoutManager);
            RingtoneDialogAdapter mRingtoneDialogAdapter = new RingtoneDialogAdapter(getActivity(), StringUtil.asList(data), mRecyclerListener);
            mRecyclerView.setAdapter(mRingtoneDialogAdapter);
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ringtone_cancel:
                dismiss();
                mDialogListener.dialogDismiss(true);
                isRingtoneChanged = false;

                break;
            case R.id.ringtone_ok:
                dismiss();
                mDialogListener.dialogDismiss(true);
                if (isRingtoneChanged) {
                   mDialogListener.dialogOkClicked(changedPosition);
                }
                break;
        }
    }

    private RingtoneDialogAdapter.OnRecyclerListener mRecyclerListener = new RingtoneDialogAdapter.OnRecyclerListener()
    {
        @Override
        public void onRecyclerClicked (int position){
            mDialogListener.onRingtoneClicked(position);
            isRingtoneChanged = true;
            changedPosition = position;
        }
    };

    public interface OnRingtoneDialogListener {

        void onRingtoneClicked(int position);
        void dialogDismiss(boolean isDismiss);
        void dialogOkClicked(int position);

    }
}
