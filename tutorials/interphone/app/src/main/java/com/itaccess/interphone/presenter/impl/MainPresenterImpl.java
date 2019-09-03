package com.itaccess.interphone.presenter.impl;

import com.itaccess.interphone.model.Message;
import com.itaccess.interphone.model.MessageBody;
import com.itaccess.interphone.presenter.MainPresenter;
import com.itaccess.interphone.utils.ThreadUtils;
import com.itaccess.interphone.view.MainView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by linxi on 2019/01/17.
 */

public class MainPresenterImpl implements MainPresenter{

    private MainView mMainView;

    public MainPresenterImpl(MainView mMainView) {
        this.mMainView = mMainView;

    }
    //TODO
    @Override
    public void loadMessages() {
        mMainView.onStartRequest();
        ThreadUtils.runOnBackgroundThread(new Runnable() {
            @Override
            public void run() {

                getMessages();
                ThreadUtils.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mMainView.onMessagesLoaded();
                    }
                });
            }
        });
    }
    //TODO
    @Override
    public List<Message> getMessages() {
        List<Message> mMessageList = new ArrayList<Message>();
        Message mMessage1 = new Message();
        MessageBody mBody1 = new MessageBody();
        mBody1.setType("00");
        mBody1.setMessage("aaaaa");
        mMessage1.setBody(mBody1);
        mMessage1.setFrom("01");
        mMessage1.setMsgTime("1999/09/09");
        mMessageList.add(mMessage1);

        Message mMessage2 = new Message();
        MessageBody mBody2 = new MessageBody();
        mBody2.setType("00");
        mBody2.setMessage("yyyyy");
        mMessage2.setBody(mBody2);
        mMessage2.setFrom("02");
        mMessage2.setMsgTime("1999/09/09");
        mMessageList.add(mMessage2);

        Message mMessage3 = new Message();
        MessageBody mBody3 = new MessageBody();
        mBody3.setType("02");
        mBody3.setUrl("http://ojyz0c8un.bkt.clouddn.com/b_1.jpg");
        mMessage3.setBody(mBody3);
        mMessage3.setFrom("02");
        mMessage3.setMsgTime("1999/09/09");
        mMessageList.add(mMessage3);

        Message mMessage4 = new Message();
        MessageBody mBody4 = new MessageBody();
        mBody4.setType("01");
        mBody4.setMessage("uuuuuu: \nhttp://ojyz0c8un.bkt.clouddn.com/b_1.jpg");
        mMessage4.setBody(mBody4);
        mMessage4.setFrom("02");
        mMessage4.setMsgTime("1999/09/09");
        mMessageList.add(mMessage4);

        Message mMessage5 = new Message();
        MessageBody mBody5 = new MessageBody();
        mBody5.setType("00");
        mBody5.setMessage("cccccc");
        mMessage5.setBody(mBody5);
        mMessage5.setFrom("01");
        mMessage5.setMsgTime("1999/09/10");
        mMessageList.add(mMessage5);
        return mMessageList;
    }

    //TODO
    @Override
    public List<Message> getMessagesRe() {
        List<Message> mMessageList = new ArrayList<Message>();
        Message mMessage1 = new Message();
        MessageBody mBody1 = new MessageBody();
        mBody1.setType("00");
        mBody1.setMessage("aaaaa");
        mMessage1.setBody(mBody1);
        mMessage1.setFrom("01");
        mMessage1.setMsgTime("1999/09/09");
        mMessageList.add(mMessage1);

        Message mMessage2 = new Message();
        MessageBody mBody2 = new MessageBody();
        mBody2.setType("00");
        mBody2.setMessage("yyyyy");
        mMessage2.setBody(mBody2);
        mMessage2.setFrom("02");
        mMessage2.setMsgTime("1999/09/09");
        mMessageList.add(mMessage2);

        Message mMessage3 = new Message();
        MessageBody mBody3 = new MessageBody();
        mBody3.setType("02");
        mBody3.setUrl("http://ojyz0c8un.bkt.clouddn.com/b_1.jpg");
        mMessage3.setBody(mBody3);
        mMessage3.setFrom("02");
        mMessage3.setMsgTime("1999/09/09");
        mMessageList.add(mMessage3);

        Message mMessage4 = new Message();
        MessageBody mBody4 = new MessageBody();
        mBody4.setType("01");
        mBody4.setMessage("uuuuuu: \nhttp://ojyz0c8un.bkt.clouddn.com/b_1.jpg");
        mMessage4.setBody(mBody4);
        mMessage4.setFrom("02");
        mMessage4.setMsgTime("1999/09/09");
        mMessageList.add(mMessage4);

        Message mMessage5 = new Message();
        MessageBody mBody5 = new MessageBody();
        mBody5.setType("00");
        mBody5.setMessage("cccccc");
        mMessage5.setBody(mBody5);
        mMessage5.setFrom("01");
        mMessage5.setMsgTime("1999/09/10");
        mMessageList.add(mMessage5);

        Message mMessage6 = new Message();
        MessageBody mBody6 = new MessageBody();
        mBody6.setType("00");
        mBody6.setMessage("dddddd");
        mMessage6.setBody(mBody6);
        mMessage6.setFrom("01");
        mMessage6.setMsgTime("1999/09/10");
        mMessageList.add(mMessage6);

        Message mMessage7 = new Message();
        MessageBody mBody7 = new MessageBody();
        mBody7.setType("00");
        mBody7.setMessage("dddddd");
        mMessage7.setBody(mBody7);
        mMessage7.setFrom("01");
        mMessage7.setMsgTime("1999/09/10");
        mMessageList.add(mMessage7);
        return mMessageList;
    }
}
