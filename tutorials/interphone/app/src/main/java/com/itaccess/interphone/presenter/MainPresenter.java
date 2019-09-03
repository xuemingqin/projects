package com.itaccess.interphone.presenter;

import com.itaccess.interphone.model.Message;

import java.util.List;

/**
 * Created by linxi on 2019/01/17.
 */

public interface MainPresenter {

    List<Message> getMessages();

    void loadMessages();

    List<Message> getMessagesRe();
}
