package com.itaccess.interphone.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.itaccess.interphone.R;
import com.itaccess.interphone.model.Message;
import com.itaccess.interphone.model.MessageBody;
import com.itaccess.interphone.utils.StringUtil;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by linxi on 2019/01/17.
 */

public class ClientMessageItemView extends RelativeLayout{

    @BindView(R.id.timestamp)
    TextView mTimestamp;
    @BindView(R.id.client_message)
    TextView mClientMessage;

    public ClientMessageItemView(Context mContext) {
        this(mContext, null);
    }

    public ClientMessageItemView(Context mContext, AttributeSet attributeSet) {
        super(mContext, attributeSet);
        init();
    }

    private void init() {
        LayoutInflater.from(getContext()).inflate(R.layout.view_client_message_item, this);
        ButterKnife.bind(this, this);
    }

    public void bindView(Message mMessage, String timestamp) {
        MessageBody mBody = mMessage.getBody();
        mClientMessage.setText(mBody.getMessage());
        if (!StringUtil.isNull(timestamp)) {
            mTimestamp.setText(timestamp);
        }
    }
}
