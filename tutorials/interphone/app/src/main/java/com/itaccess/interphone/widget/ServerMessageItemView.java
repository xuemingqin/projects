package com.itaccess.interphone.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.itaccess.interphone.R;
import com.itaccess.interphone.model.Message;
import com.itaccess.interphone.model.MessageBody;
import com.itaccess.interphone.utils.StringUtil;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by linxi on 2019/01/17.
 */

public class ServerMessageItemView extends RelativeLayout {

    private static final String TYPE_CLIENT = "00";
    private static final String TYPE_SERVER_INFO = "01";
    @BindView(R.id.timestamp)
    TextView mTimestamp;
    @BindView(R.id.server_message)
    TextView mServerMessage;
    @BindView(R.id.server_image)
    ImageView mServerImage;
    @BindView(R.id.server_icon)
    ImageView mServerIcon;
    @BindView(R.id.server_image_layout)
    LinearLayout mServerImgLayout;

    private Context mContext;
    private StartNoticeListActivityListener mListener;

    public void setStartNoticeListActivityListener(StartNoticeListActivityListener listener) {
        mListener = listener;
    }

    public ServerMessageItemView(Context mContext) {
        this(mContext, null);
        this.mContext = mContext;
    }

    public ServerMessageItemView(Context mContext, AttributeSet attributeSet) {
        super(mContext, attributeSet);
        init();
    }

    private void init() {
        LayoutInflater.from(getContext()).inflate(R.layout.view_server_message_item, this);
        ButterKnife.bind(this, this);
    }

    public void bindView(Message mMessage, String timestamp) {
        MessageBody mBody = mMessage.getBody();
        if (TYPE_CLIENT.equals(mBody.getType())) {
            mServerImgLayout.setVisibility(View.GONE);
            mServerMessage.setVisibility(View.VISIBLE);
            mServerMessage.setText(mBody.getMessage());
        } else if (TYPE_SERVER_INFO.equals(mBody.getType())) {
            //お知らせ
            mServerImgLayout.setVisibility(View.GONE);
            mServerMessage.setVisibility(View.VISIBLE);
            mServerMessage.setText(mBody.getMessage());
            mServerIcon.setImageResource(R.mipmap.icon_server_1);
            //メッセージのURLにはクリックすると、WebActivityで開く
            mServerMessage.setMovementMethod(LinkMovementMethod.getInstance());
            // メッセージをタップしたときはお知らせ一覧画面へ遷移する
            mServerMessage.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View view) {
                   mListener.startNoticeActivity();
                }
            });
        } else {
            mServerMessage.setVisibility(View.GONE);
            mServerImgLayout.setVisibility(View.VISIBLE);
            Glide.with(mContext)
                    .load(mBody.getUrl())
                    .placeholder(R.mipmap.ic_launcher)
                    .error(R.mipmap.ic_launcher)
                    .into(mServerImage);
        }
        if (!StringUtil.isNull(timestamp)) {
            mTimestamp.setText(timestamp);
        }
    }

    public interface StartNoticeListActivityListener {
        void startNoticeActivity();
    }


}
