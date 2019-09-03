package com.itaccess.interphone.adapter;

import android.content.Context;
import android.support.v4.widget.ContentLoadingProgressBar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.itaccess.interphone.R;
import com.itaccess.interphone.model.Message;
import com.itaccess.interphone.widget.ClientMessageItemView;
import com.itaccess.interphone.widget.ServerMessageItemView;

import java.util.List;

/**
 * Created by linxi on 2019/01/17.
 */

public class MessageListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements ServerMessageItemView.StartNoticeListActivityListener{

    public static final String TAG = "MessageListAdapter";
    private static final int TYPE_FOOTER = 0;
    private static final int ITEM_TYPE_CLIENT_MESSAGE = 1;
    private static final int ITEM_TYPE_SERVICE_MESSAGE = 2;

    private Context mContext;
    private List<Message> mMessages;
    private LinearLayoutManager mLinearLayoutManager;
    private StartActivityListener mListener;
    private String theDifferentDay;

    public MessageListAdapter(Context mContext, List<Message> mMessages, RecyclerView mRecyclerView) {
        this.mContext = mContext;
        this.mMessages = mMessages;
        mLinearLayoutManager = (LinearLayoutManager) mRecyclerView.getLayoutManager();
        theDifferentDay = "";
    }

    public void setStartActivityListener(StartActivityListener listener) {
        mListener = listener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case TYPE_FOOTER:
                View view = LayoutInflater.from(mContext).inflate(R.layout.recyclerview_footer, parent, false);
                return new FootViewHolder(view);
            case ITEM_TYPE_CLIENT_MESSAGE:
                return new ClientItemViewHolder(new ClientMessageItemView(mContext));
            case ITEM_TYPE_SERVICE_MESSAGE:
                return new ServerItemViewHolder(new ServerMessageItemView(mContext));
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        if (holder instanceof ClientItemViewHolder) {
            ((ClientItemViewHolder) holder).mClientMessageItemView.bindView(mMessages.get(position),
                    getMsgTimeForNorepeat(position));
        } else if (holder instanceof ServerItemViewHolder){
            ((ServerItemViewHolder) holder).mServerMessageItemView.bindView(mMessages.get(position),
                    getMsgTimeForNorepeat(position));
            ((ServerItemViewHolder) holder).mServerMessageItemView.setStartNoticeListActivityListener(this);
        }

        mLinearLayoutManager.getChildCount();
        mLinearLayoutManager.getItemCount();
        mLinearLayoutManager.findLastVisibleItemPosition();
    }

    @Override
    public int getItemViewType(int position) {
        if (position==mMessages.size()){
            return TYPE_FOOTER;
        }
        Message message = mMessages.get(position);
        return message.direct() == Message.Direct.CLIENT ? ITEM_TYPE_CLIENT_MESSAGE : ITEM_TYPE_SERVICE_MESSAGE;
    }

    @Override
    public int getItemCount() {
        return mMessages.size() + 1;
    }

    private class ClientItemViewHolder extends RecyclerView.ViewHolder {
        public ClientMessageItemView mClientMessageItemView;

        public ClientItemViewHolder(ClientMessageItemView mClientMessageItemView) {
            super(mClientMessageItemView);
            this.mClientMessageItemView = mClientMessageItemView;
        }
    }

    private class ServerItemViewHolder extends RecyclerView.ViewHolder {
        public ServerMessageItemView mServerMessageItemView;

        public ServerItemViewHolder(ServerMessageItemView mServerMessageItemView) {
            super(mServerMessageItemView);
            this.mServerMessageItemView = mServerMessageItemView;
        }
    }

    private class FootViewHolder extends RecyclerView.ViewHolder{
        ContentLoadingProgressBar contentLoadingProgressBar;
        public FootViewHolder(View itemView) {
            super(itemView);
            contentLoadingProgressBar = itemView.findViewById(R.id.pb_progress);
        }
    }

    @Override
    public void startNoticeActivity() {
       mListener.start();
    }

    public interface StartActivityListener {
        void start();
    }

    private String getMsgTimeForNorepeat(int position) {
        String msgTime = mMessages.get(position).getMsgTime();
        if (!msgTime.equals(theDifferentDay))  {
            theDifferentDay = msgTime;
        } else {
            msgTime = "";
        }
        return msgTime;
    }
}
