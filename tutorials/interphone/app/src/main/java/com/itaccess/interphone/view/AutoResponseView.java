package com.itaccess.interphone.view;

/**
 * Created by linxi on 2019/01/25.
 */

public interface AutoResponseView extends BaseView{
    public static final String TAG = "AutoResponseView";

    void onSuccess(boolean isDecided);

}
