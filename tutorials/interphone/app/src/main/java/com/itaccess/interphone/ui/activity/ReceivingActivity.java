package com.itaccess.interphone.ui.activity;

import android.Manifest;
import android.app.Activity;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.ColorDrawable;
import android.media.AudioManager;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Vibrator;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.itaccess.interphone.R;
import com.itaccess.interphone.model.AutoResponseSet;
import com.itaccess.interphone.presenter.AutoAnswerPresenter;
import com.itaccess.interphone.presenter.impl.AutoAnswerPresenterImpl;
import com.itaccess.interphone.service.FirebaseMessagingService;
import com.itaccess.interphone.service.RegistrationIntentService;
import com.itaccess.interphone.utils.PreferencesUtil;
import com.itaccess.interphone.utils.StringUtil;
import com.itaccess.interphone.utils.WindowUtil;
import com.itaccess.interphone.view.AutoAnswerView;


import butterknife.BindView;
import io.skyway.Peer.Browser.Canvas;
import io.skyway.Peer.Browser.MediaConstraints;
import io.skyway.Peer.Browser.MediaStream;
import io.skyway.Peer.Browser.Navigator;
import io.skyway.Peer.ConnectOption;
import io.skyway.Peer.DataConnection;
import io.skyway.Peer.MediaConnection;
import io.skyway.Peer.OnCallback;
import io.skyway.Peer.Peer;
import io.skyway.Peer.PeerError;
import io.skyway.Peer.PeerOption;
import io.skyway.Peer.Room;
import io.skyway.Peer.RoomOption;

/**
 * Created by linxi on 2019/01/26.
 */

public class ReceivingActivity extends BaseActivity implements AutoAnswerView{

    private static final String TAG = "ReceivingActivity";
    private static final String API_KEY = "6ca1e0d5-e128-4e2b-a746-87028e70d219";
    private static final String DOMAIN = "nttcom.github.io";
    private static final int TALK_VIEW_ID = 0;
    private static final int END_VIEW_ID = 1;
    private static final int AUTO_ANSWER_VIEW_ID = 2;
    private static final int OPEN_VIEW_ID = 3;
    private static final int QUIET_VIEW_ID = 4;
    private static final String PLACE_INTERCOM = "01";
    private static final String PLACE_LOBBY = "02";

    private Peer _peer;
    private Room _room;
    private MediaStream _localStream;
    private MediaStream		_remoteStream;
    private MediaConnection _mediaConnection;
    private DataConnection _dataConnection;
    private Handler _handler;
    private Ringtone mRingtone;
    private RingtoneManager mRingtoneManager;
    private AudioManager aManager;
    private Vibrator mVibrator;
    private PopupWindow mPopupWindow;
    private AutoAnswerPresenter mAutoAnswerPresenter;
    private MediaConstraints constraints;
    private View popwindowView;

    private String			_strOwnId;
    private String          strPlace;
    private boolean			_bConnected;
    private boolean			_dataConnected;
    private boolean         _isOpened;
    private boolean         isPopContentTouched;
    private boolean         isQuieted;
    private boolean         isTalkClicked;
    private boolean         isFirst;
    private String          _peerId;

    @BindView(R.id.talk_image)
    ImageView talkImage;
    @BindView(R.id.end_image)
    ImageView endImage;
    @BindView(R.id.open_image)
    ImageView openImage;
    @BindView(R.id.quiet_image)
    ImageView quietImage;
    @BindView(R.id.auto_answer_image)
    ImageView autoAnswerImage;
    @BindView(R.id.talk_image_txt)
    TextView talkImageTxt;
    @BindView(R.id.end_image_txt)
    TextView endImageTxt;
    @BindView(R.id.open_image_txt)
    TextView openImageTxt;
    @BindView(R.id.quiet_image_txt)
    TextView quietImageTxt;
    @BindView(R.id.auto_answer_image_txt)
    TextView autoAnswerImageTxt;
    @BindView(R.id.sv_remote_view)
    Canvas svRemoteView;

    @Override
    public int getLayoutRes() {
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED|WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD);
        getWindow().addFlags(Window.FEATURE_NO_TITLE);
        return R.layout.activity_receiving;
    }

    @Override
    protected void init() {
        super.init();
        setToolBarTitle(getResStr(R.string.receiving_title));
        _handler = new Handler(Looper.getMainLooper());
        //着信音、通話音量などの設定
        setAudioToRing();
        initPeer();

        mAutoAnswerPresenter = new AutoAnswerPresenterImpl(this);
        Uri uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_RINGTONE);
        mRingtone = RingtoneManager.getRingtone(getApplicationContext(), uri);

        isQuieted = false;
        isTalkClicked = false;
        isFirst = true;

        //該当画面でお知らせ通知が出ないよう
        unregisterReceiver(mNotificationReceiver);
        mNotificationReceiver = null;
    }

    private void setAudioToRing() {
        ringtonePlay();
        setVibrator();
        setAudio();
    }

    public void initPeer() {
        final Activity activity = this;
        // Initialize Peer
        PeerOption option = new PeerOption();
        option.key = API_KEY;
        option.domain = DOMAIN;
        _peer = new Peer(this, "linxiaoxing", option);

        // OPEN Set Peer event callbacks
        _peer.on(Peer.PeerEventEnum.OPEN, new OnCallback() {
            @Override
            public void onCallback(Object object) {
                // Request permissions
                _strOwnId = (String) object;
                if (ContextCompat.checkSelfPermission(activity,
                        Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(activity,
                        Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(activity,new String[]{Manifest.permission.CAMERA, Manifest.permission.RECORD_AUDIO},0);
                } else {
                    startLocalStream();
                }
                joinOrleave();
            }
        });


        // Data CALL (Incoming call)
        _peer.on(Peer.PeerEventEnum.CONNECTION, new OnCallback() {
            @Override
            public void onCallback(Object object) {
                if (!(object instanceof DataConnection)){
                    return;
                }

                _dataConnection = (DataConnection)object;
                //setDataCallbacks();
            }
        });

        _peer.on(Peer.PeerEventEnum.CLOSE, new OnCallback()	{
            @Override
            public void onCallback(Object object) {
                Log.d(TAG, "[On/Close]");
            }
        });
        _peer.on(Peer.PeerEventEnum.DISCONNECTED, new OnCallback() {
            @Override
            public void onCallback(Object object) {
                Log.d(TAG, "[On/Disconnected]");
            }
        });
        _peer.on(Peer.PeerEventEnum.ERROR, new OnCallback() {
            @Override
            public void onCallback(Object object) {
                PeerError error = (PeerError) object;
                Log.d(TAG, "[On/Error]" + error);
            }
        });

        initButton();
        initRemoteView();

        Intent intent = new Intent(this, RegistrationIntentService.class);
        startService(intent);

    }

    private void initButton() {

        setLayoutForFirst();
        initListener();
    }

    private void initRemoteView() {

        float otherHeight = WindowUtil.getStatusHeight(this) +
                WindowUtil.getActionBarHeight(this);
        float footerHeight = WindowUtil.getWindowHeight(this) - otherHeight;
        int height = (int) (footerHeight * 2)/3;
        int width = (height * 2)/3;
        ViewGroup.LayoutParams mParams = svRemoteView.getLayoutParams();
        mParams.height = height;
        mParams.width = width;
        svRemoteView.setLayoutParams(mParams);
    }

    private void initListener() {
        talkImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mRingtone != null) {
                    mRingtone.stop();
                }
                if (mVibrator != null) {
                    mVibrator.cancel();
                }
                //aManager.setMode(AudioManager.MODE_IN_COMMUNICATION);
                changeAudioMediaStream(_localStream, true);
                changeVideoMediaStream(_localStream, true);
                setLayoutForTalkClicked();


                if (null == _peer) {
                    return;
                }

                if (null != _dataConnection) {
                    _dataConnection.close();
                }

                ConnectOption option = new ConnectOption();
                option.label = "chat";
                _dataConnection = _peer.connect(_peerId, option);
                changeAudioMediaStream(_localStream, true);
                isTalkClicked = true;
            }
        });
        endImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeAudioMediaStream(_localStream, false);
                changeVideoMediaStream(_localStream, false);
                changeAudioMediaStream(_remoteStream, false);
                changeVideoMediaStream(_remoteStream, false);
                closeRemoteStream();
                setLayoutForEnd();
            }
        });

        autoAnswerImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mRingtone != null) {
                    mRingtone.stop();
                }
                if (mVibrator != null) {
                    mVibrator.cancel();
                }
                setLayoutForAutoAnswerClicked();
                initPopWindow(null);
            }
        });

        openImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (null != _dataConnection) {
                    _dataConnected = true;
                }

                if (_dataConnected) {

                    _handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            if (_isOpened == false) {
                                sendData("01");
                                _isOpened = true;
                            } else {
                                sendData("00");
                                _isOpened = false;
                            }
                        }
                    }, 1000);

                }
                updateImageView(openImage, R.drawable.icon_padlock_filled_50);
                updateTextColor(openImageTxt, false);
            }
        });

        quietImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isQuieted) {
                    updateImageView(quietImage, R.drawable.icon_un_quiet_filled);
                    quietImageTxt.setText(getResStr(R.string.receiving_title_un_quiet_txt));
                    changeAudioMediaStream(_remoteStream, false);
                } else {
                    updateImageView(quietImage, R.drawable.icon_quiet_filled);
                    quietImageTxt.setText(getResStr(R.string.receiving_title_quiet_txt));
                    changeAudioMediaStream(_remoteStream, true);
                }
                isQuieted = !isQuieted;
            }
        });

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case 0: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    startLocalStream();
                }
                else {
                    Toast.makeText(this,"Failed to access the camera and microphone.\nclick allow when asked for permission.", Toast.LENGTH_LONG).show();
                }
                break;
            }
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        // Disable Sleep and Screen Lock
        Window wnd = getWindow();
        wnd.addFlags(WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON);
        wnd.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        //フォアグラウンドの場合、通話音声を聞かせる
        changeAudioMediaStream(_remoteStream, true);
    }

    @Override
    protected void onResume() {
        super.onResume();
        //自動応答設定の確認
        if (isFirst) {
            AutoResponseSet mAutoResponseSet = PreferencesUtil.getAutoResponseSeting(this);
            if (mAutoResponseSet.getDecided()) {
                String contentNum = mAutoResponseSet.getContentNum();
                initPopWindow(contentNum);
            }
            isFirst = false;
        }
        setVolumeControlStream(AudioManager.STREAM_VOICE_CALL);
    }

    @Override
    protected void onPause() {
        // Set default volume control stream type.
        setVolumeControlStream(AudioManager.USE_DEFAULT_STREAM_TYPE);
        super.onPause();
    }

    @Override
    protected void onStop()	{
        // Enable Sleep and Screen Lock
        Window wnd = getWindow();
        wnd.clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        wnd.clearFlags(WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON);
        //バックグラウンドの場合、通話音声が出ないように
        changeAudioMediaStream(_remoteStream, false);
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        destroyPeer();
        super.onDestroy();
    }


    private void startLocalStream() {
        Navigator.initialize(_peer);
        constraints = new MediaConstraints();
        _localStream = Navigator.getUserMedia(constraints);
    }

    private void joinRoom() {
        if ((null == _peer) || (null == _strOwnId) || (0 == _strOwnId.length())) {
            return;
        }

        // Get room name

        String roomName = "testroom";
        if (TextUtils.isEmpty(roomName)) {
            return;
        }

        RoomOption option = new RoomOption();
        option.mode = RoomOption.RoomModeEnum.MESH;
        option.stream = _localStream;

        // Join Room
        _room = _peer.joinRoom(roomName, option);
        _bConnected = true;

        //
        // Set Callbacks
        //
        _room.on(Room.RoomEventEnum.OPEN, new OnCallback() {
            @Override
            public void onCallback(Object object) {
                if (!(object instanceof String)) return;

                String roomName = (String)object;
                Log.i(TAG, "Enter Room: " + roomName);
            }
        });

        _room.on(Room.RoomEventEnum.CLOSE, new OnCallback() {
            @Override
            public void onCallback(Object object) {
                String roomName = (String)object;
                Log.i(TAG, "Leave Room: " + roomName);

                // Unset callbacks
                _room.on(Room.RoomEventEnum.OPEN, null);
                _room.on(Room.RoomEventEnum.CLOSE, null);
                _room.on(Room.RoomEventEnum.ERROR, null);
                _room.on(Room.RoomEventEnum.PEER_JOIN, null);
                _room.on(Room.RoomEventEnum.PEER_LEAVE, null);
                _room.on(Room.RoomEventEnum.STREAM, null);
                _room.on(Room.RoomEventEnum.REMOVE_STREAM, null);

                _room = null;
                _bConnected = false;
            }
        });

        _room.on(Room.RoomEventEnum.ERROR, new OnCallback()
        {
            @Override
            public void onCallback(Object object)
            {
                PeerError error = (PeerError) object;
                Log.d(TAG, "RoomEventEnum.ERROR:" + error);
            }
        });

        _room.on(Room.RoomEventEnum.PEER_JOIN, new OnCallback()
        {
            @Override
            public void onCallback(Object object)
            {
                Log.d(TAG, "RoomEventEnum.PEER_JOIN:");

                if (!(object instanceof String)) return;

                String peerId = (String)object;
                Log.i(TAG, "Join Room: " + peerId);
            }
        });
        _room.on(Room.RoomEventEnum.PEER_LEAVE, new OnCallback()
        {
            @Override
            public void onCallback(Object object)
            {
                Log.d(TAG, "RoomEventEnum.PEER_LEAVE:");

                if (!(object instanceof String)) return;

                String peerId = (String)object;
                Log.i(TAG, "Leave Room: " + peerId);

            }
        });

        _room.on(Room.RoomEventEnum.STREAM, new OnCallback()
        {
            @Override
            public void onCallback(Object object)
            {
                Log.d(TAG, "RoomEventEnum.STREAM: + " + object);

                if (!(object instanceof MediaStream)) return;

                final MediaStream stream = (MediaStream)object;
                Log.d(TAG, "peer = " + stream.getPeerId() + ", label = " + stream.getLabel());

                if (_peerId.equals(stream.getPeerId())) {
                    _remoteStream = stream;
                    _remoteStream.addVideoRenderer(svRemoteView,0);
                    setLayoutInCalling();

                    changeAudioMediaStream(_localStream, false);
                    changeVideoMediaStream(_localStream, false);
                }
            }
        });

        _room.on(Room.RoomEventEnum.REMOVE_STREAM, new OnCallback()
        {
            @Override
            public void onCallback(Object object)
            {
                Log.d(TAG, "RoomEventEnum.REMOVE_STREAM: " + object);

                if (!(object instanceof MediaStream)) return;

                final MediaStream stream = (MediaStream)object;
                Log.d(TAG, "peer = " + stream.getPeerId() + ", label = " + stream.getLabel());

            }
        });

    }

    private void leaveRoom() {
        if (null == _peer || null == _room) {
            return;
        }
        _room.close();
    }

    private void sendData(String data) {
        if (null == _peer || null == _room) {
            return;
        }
        if (TextUtils.isEmpty(data)) return;
        _room.send(data);
    }

    private void joinOrleave() {
        Intent mIntent = getIntent();
        //if (mIntent != null) {
            String peerId = mIntent.getStringExtra(FirebaseMessagingService.EXTRA_PEER_ID);
            strPlace = mIntent.getStringExtra(FirebaseMessagingService.EXTRA_CALL_LOCATION);
            peerId = "N1dwMZHR8MW2EpNH";
            if (peerId != null) {
                _peerId = peerId;
                if (!_bConnected) {
                    // Join room
                    joinRoom();
                }
                else {
                    // Leave room
                    leaveRoom();
                }
            }
       // }
    }

    private void closeRemoteStream(){
        if (null == _remoteStream) {
            return;
        }
        _remoteStream.removeVideoRenderer(svRemoteView,0);
        _remoteStream.close();
    }

    private void unsetPeerCallback(Peer peer) {
        if(null == _peer){
            return;
        }

        peer.on(Peer.PeerEventEnum.OPEN, null);
        peer.on(Peer.PeerEventEnum.CONNECTION, null);
        peer.on(Peer.PeerEventEnum.CALL, null);
        peer.on(Peer.PeerEventEnum.CLOSE, null);
        peer.on(Peer.PeerEventEnum.DISCONNECTED, null);
        peer.on(Peer.PeerEventEnum.ERROR, null);
    }

    private void unsetMediaCallbacks() {
        if(null == _mediaConnection){
            return;
        }

        _mediaConnection.on(MediaConnection.MediaEventEnum.STREAM, null);
        _mediaConnection.on(MediaConnection.MediaEventEnum.CLOSE, null);
        _mediaConnection.on(MediaConnection.MediaEventEnum.ERROR, null);
    }

    private void unsetDataCallbacks() {
        if(null == _dataConnection){
            return;
        }
        _dataConnection.on(DataConnection.DataEventEnum.OPEN, null);
        _dataConnection.on(DataConnection.DataEventEnum.CLOSE, null);
        _dataConnection.on(DataConnection.DataEventEnum.DATA, null);
        _dataConnection.on(DataConnection.DataEventEnum.ERROR, null);
    }

    private void changeAudioMediaStream(MediaStream prev, boolean enable)
    {
        int iCount = 0;
        if (null != prev)
        {
            iCount = prev.getAudioTracks();
            for (int i = 0 ; iCount > i ; i++)
            {
                prev.setEnableAudioTrack(i, enable);
            }
        }
    }

    private void changeVideoMediaStream(MediaStream prev, boolean enable)
    {
        int iCount = 0;
        if (null != prev)
        {
            iCount = prev.getVideoTracks();
            for (int i = 0 ; iCount > i ; i++)
            {
                prev.setEnableVideoTrack(i, enable);
            }
        }
    }

    private void destroyPeer() {
        closeRemoteStream();

        if (null != _localStream) {
            _localStream.close();
        }

        if (null != _mediaConnection)	{
            if (_mediaConnection.isOpen()) {
                _mediaConnection.close();
            }
            unsetMediaCallbacks();

        }

        if (null != _dataConnection)	{
            if (_dataConnection.isOpen()) {
                _dataConnection.close();
            }
            unsetDataCallbacks();
        }

        Navigator.terminate();

        if (null != _peer) {
            unsetPeerCallback(_peer);
            if (!_peer.isDisconnected()) {
                _peer.disconnect();
            }

            if (!_peer.isDestroyed()) {
                _peer.destroy();
            }

            _peer = null;
        }
    }

    private void setViewEnabled(int viewId, boolean enabled) {
        switch (viewId) {
            case TALK_VIEW_ID:
                talkImage.setEnabled(enabled);
                talkImageTxt.setEnabled(enabled);
                break;
            case END_VIEW_ID:
                endImage.setEnabled(enabled);
                endImageTxt.setEnabled(enabled);
                break;
            case AUTO_ANSWER_VIEW_ID:
                autoAnswerImage.setEnabled(enabled);
                autoAnswerImageTxt.setEnabled(enabled);
                break;
            case OPEN_VIEW_ID:
                openImage.setEnabled(enabled);
                openImageTxt.setEnabled(enabled);
            case QUIET_VIEW_ID:
                quietImage.setEnabled(enabled);
                quietImageTxt.setEnabled(enabled);
                break;
        }
    }
    //TODO
    private void initPopWindow(String contentNum) {
        popwindowView = LayoutInflater.from(this).inflate(R.layout.receiving_popwindow, null, false);
        LinearLayout mCancelLayout = popwindowView.findViewById(R.id.receiving_pop_cancel_layout);
        LinearLayout mContentLayout1 = popwindowView.findViewById(R.id.receiving_pop_layout_content_1);
        LinearLayout mContentLayout2 = popwindowView.findViewById(R.id.receiving_pop_layout_content_2);
        LinearLayout mContentLayout3 = popwindowView.findViewById(R.id.receiving_pop_layout_content_3);

        mPopupWindow = new PopupWindow(popwindowView,
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);
        mPopupWindow.setAnimationStyle(R.anim.anim_pop);
        mPopupWindow.setBackgroundDrawable(new ColorDrawable(0x00000000));
        post(new Runnable() {
            @Override
            public void run() {
                mPopupWindow.showAtLocation(popwindowView, Gravity.CENTER_VERTICAL,0, 0);
            }
        });
        mPopupWindow.setOutsideTouchable(true);
        mPopupWindow.setTouchInterceptor(popTouchInterceptor);
        isPopContentTouched = false;
        mCancelLayout.setOnClickListener(popClickListener);
        mContentLayout1.setOnClickListener(popClickListener);
        mContentLayout2.setOnClickListener(popClickListener);
        mCancelLayout.setOnTouchListener(popTouchListener);
        mContentLayout1.setOnTouchListener(popTouchListener);
        mContentLayout2.setOnTouchListener(popTouchListener);

        if (PLACE_LOBBY.equals(strPlace) || !StringUtil.isNull(contentNum)) {
            mContentLayout3.setOnClickListener(popClickListener);
            mContentLayout3.setOnTouchListener(popTouchListener);
        } else {
            mContentLayout3.setVisibility(View.GONE);
            ((View)popwindowView.findViewById(R.id.talk_image_txt3)).setVisibility(View.GONE);
            ((TextView)popwindowView.findViewById(R.id.talk_image_txt1))
                    .setText(getResStr(R.string.receiving_auto_entrance_answer_txt_1));
            ((TextView)popwindowView.findViewById(R.id.talk_image_txt2))
                    .setText(getResStr(R.string.receiving_auto_entrance_answer_txt_2));
        }

        if (!StringUtil.isNull(contentNum)) {
            if (contentNum.equals(AutoResponseActivity.CONTENT_TXT_1)) {
                mContentLayout1.setBackgroundResource(R.color.receiving_popwindow_layout_clicked_color);
            } else if (contentNum.equals(AutoResponseActivity.CONTENT_TXT_2)) {
                mContentLayout2.setBackgroundResource(R.color.receiving_popwindow_layout_clicked_color);
            } else if (contentNum.equals(AutoResponseActivity.CONTENT_TXT_3)) {
                mContentLayout3.setBackgroundResource(R.color.receiving_popwindow_layout_clicked_color);
            }
        }
    }

    private View.OnClickListener popClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            int id = view.getId();
            switch (id) {
                case R.id.receiving_pop_cancel_layout:
                    if (mPopupWindow.isShowing()) {
                        mPopupWindow.dismiss();
                        setLayoutAfterPopCanceled();
                    }
                    break;
                case R.id.receiving_pop_layout_content_1:
                    setLayoutPopContentClicked();
                    break;
                case R.id.receiving_pop_layout_content_2:
                    setLayoutPopContentClicked();
                    break;
                case R.id.receiving_pop_layout_content_3:
                    setLayoutPopContentClicked();
                    break;
            }
        }
    };

    private View.OnTouchListener popTouchListener = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View view, MotionEvent motionEvent) {
            int id = view.getId();
            switch (id) {
                case R.id.receiving_pop_cancel_layout:
                    if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                        view.setBackgroundResource(R.color.receiving_popwindow_layout_clicked_color);
                    }
                    break;
                case R.id.receiving_pop_layout_content_1:
                    //他の内容が選択された場合、こちらの内容をタッチできないようにする
                    if (isPopContentTouched == true) {
                        return true;
                    }
                    if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                        view.setBackgroundResource(R.color.receiving_popwindow_layout_clicked_color);
                        mAutoAnswerPresenter.autoAnswer("00000", "01");
                        isPopContentTouched = true;
                    }
                    break;
                case R.id.receiving_pop_layout_content_2:
                    //他の内容が選択された場合、こちらの内容をタッチできないようにする
                    if (isPopContentTouched == true) {
                        return true;
                    }
                    if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                        view.setBackgroundResource(R.color.receiving_popwindow_layout_clicked_color);
                        mAutoAnswerPresenter.autoAnswer("00000", "02");
                        isPopContentTouched = true;
                    }
                    break;
                case R.id.receiving_pop_layout_content_3:
                    //他の内容が選択された場合、こちらの内容をタッチできないようにする
                    if (isPopContentTouched == true) {
                        return true;
                    }
                    if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                        view.setBackgroundResource(R.color.receiving_popwindow_layout_clicked_color);
                        mAutoAnswerPresenter.autoAnswer("00000", "03");
                        isPopContentTouched = true;
                    }
                    break;
            }
            return false;
        }
    };

    private View.OnTouchListener popTouchInterceptor = new View.OnTouchListener() {
        public boolean onTouch(View v, MotionEvent event) {

            final int x = (int) event.getX();
            final int y = (int) event.getY();
            int viewHeight = popwindowView.getHeight();
            if ((event.getAction() == MotionEvent.ACTION_DOWN)
                    && ((y < 0) || (y >= viewHeight))) {
                //Log.d(TAG,"ccccccccccccc"+ x +":" + y + "---"+ viewHeight);
                //Log
                return true;
            } else if (event.getAction() == MotionEvent.ACTION_OUTSIDE) {
                return true;
            }
            return false;
        }
    };

    @Override
    public void onStartRequest() {

    }

    @Override
    public void onSuccess() {
        postDelay(new Runnable() {
            @Override
            public void run() {
                mPopupWindow.dismiss();
                setViewEnabled(AUTO_ANSWER_VIEW_ID, true);
                updateTextColor(autoAnswerImageTxt, true);
                updateImageView(autoAnswerImage, R.drawable.icon_voice_filled_50);
            }
        }, 2000);

    }

    @Override
    public void onFail() {
        setViewEnabled(AUTO_ANSWER_VIEW_ID, true);
        updateTextColor(autoAnswerImageTxt, true);
        updateImageView(autoAnswerImage, R.drawable.icon_voice_filled_50);
        mPopupWindow.dismiss();
    }

    private void updateImageView(final ImageView btn, final int id) {
        _handler.post(new Runnable() {
            @Override
            public void run() {
                Bitmap bitmap = BitmapFactory.decodeResource(getResources(), id);
                btn.setImageBitmap(bitmap);
            }
        });
    }

    private void updateTextColor(final TextView textView, final boolean isActive) {
        _handler.post(new Runnable() {
            @Override
            public void run() {
                if (isActive) {
                    textView.setTextColor(getResources().getColor(R.color.receiving_text_active_color));
                } else {
                    textView.setTextColor(getResources().getColor(R.color.receiving_text_un_active_color));
                }
            }
        });
    }

    private void ringtonePlay() {
        int position = 1;
        mRingtoneManager = new RingtoneManager(this);
        String preRingtone = PreferencesUtil.getRingtone(this);
        if (!StringUtil.isNull(preRingtone)) {
            Cursor mCursor = mRingtoneManager.getCursor();


            String ringtone = "";
            int i = 0;
            while (mCursor.moveToNext()) {
                ringtone = mCursor.getString(RingtoneManager.TITLE_COLUMN_INDEX);
                if(ringtone.equals(preRingtone)) {
                    position = i;
                }
                i++;
            }
        }
        mRingtone = mRingtoneManager.getRingtone(position);
        post(new Runnable() {
            @Override
            public void run() {
                mRingtone.play();
            }
        });

    }

    private void setAudio() {
        aManager = (AudioManager)getSystemService(Context.AUDIO_SERVICE);

        //Prferencesに保存された音量設定を取得して、設定する(設定ない場合、0にする)
        int talkVolume = PreferencesUtil.getCallVolume(this);
        aManager.setStreamVolume(AudioManager.STREAM_VOICE_CALL,talkVolume,0);
        //RingToneモード
        aManager.setMode(AudioManager.MODE_RINGTONE);

        //Prferencesに保存された音声出力先を取得して、設定する(設定ない場合、falseにする)
        Boolean isSpeakerPhoneOn = PreferencesUtil.getSpeakerphoneOn(this);
        //スピーカーの設定
        aManager.setSpeakerphoneOn(isSpeakerPhoneOn);

    }

    private void setVibrator() {
        //振動の設定
        mVibrator = (Vibrator) getSystemService(Service.VIBRATOR_SERVICE);
        mVibrator.cancel();
        post(new Runnable() {
            @Override
            public void run() {
                mVibrator.vibrate(new long[]{500, 100, 500, 100, 500, 100}, 0);
            }
        });
    }

    private void setLayoutForFirst() {
        setViewEnabled(TALK_VIEW_ID, true);
        setViewEnabled(END_VIEW_ID, false);
        setViewEnabled(AUTO_ANSWER_VIEW_ID, true);
        setViewEnabled(OPEN_VIEW_ID, false);
        setViewEnabled(QUIET_VIEW_ID, true);
        updateTextColor(talkImageTxt, true);
        updateTextColor(endImageTxt, false);
        updateTextColor(autoAnswerImageTxt, true);
        updateTextColor(openImageTxt, false);
        updateTextColor(quietImageTxt, true);
        if (PLACE_INTERCOM.equals(strPlace)) {
           openImage.setVisibility(View.INVISIBLE);
           openImageTxt.setVisibility(View.INVISIBLE);
        }
    }

    private void setLayoutInCalling() {
        setViewEnabled(TALK_VIEW_ID, true);
        setViewEnabled(AUTO_ANSWER_VIEW_ID, true);
        setViewEnabled(QUIET_VIEW_ID, true);
    }

    private void setLayoutForTalkClicked() {
        setViewEnabled(END_VIEW_ID, true);
        setViewEnabled(OPEN_VIEW_ID, true);
        setViewEnabled(AUTO_ANSWER_VIEW_ID, true);
        updateImageView(talkImage, R.drawable.icon_call_50);
        updateImageView(endImage, R.drawable.icon_no_phones_filled_50);
        updateImageView(openImage, R.drawable.icon_lock_filled_50);
        updateTextColor(talkImageTxt, false);
        updateTextColor(endImageTxt, true);
        updateTextColor(autoAnswerImageTxt, true);
        updateTextColor(openImageTxt, true);
    }

    private void setLayoutForAutoAnswerClicked() {
        setViewEnabled(TALK_VIEW_ID, false);
        setViewEnabled(END_VIEW_ID, true);
        setViewEnabled(AUTO_ANSWER_VIEW_ID, false);
        setViewEnabled(OPEN_VIEW_ID, false);
        setViewEnabled(QUIET_VIEW_ID, true);
        updateImageView(talkImage, R.drawable.icon_call_50);
        updateImageView(endImage, R.drawable.icon_no_phones_filled_50);
        updateImageView(autoAnswerImage, R.drawable.icon_voice_50);
        updateImageView(openImage, R.drawable.icon_lock_50);
        updateTextColor(talkImageTxt, false);
        updateTextColor(endImageTxt, true);
        updateTextColor(autoAnswerImageTxt, false);
        updateTextColor(openImageTxt, false);
    }

    private void setLayoutForEnd() {
        setViewEnabled(TALK_VIEW_ID, false);
        setViewEnabled(END_VIEW_ID, false);
        setViewEnabled(AUTO_ANSWER_VIEW_ID, false);
        setViewEnabled(OPEN_VIEW_ID, false);
        setViewEnabled(QUIET_VIEW_ID, false);
        updateImageView(talkImage, R.drawable.icon_call_50);
        updateImageView(endImage, R.drawable.icon_no_phones_50);
        updateImageView(autoAnswerImage, R.drawable.icon_voice_50);
        updateImageView(openImage, R.drawable.icon_lock_50);
        updateImageView(quietImage, R.drawable.icons_quiet);
        updateTextColor(talkImageTxt, false);
        updateTextColor(endImageTxt, false);
        updateTextColor(autoAnswerImageTxt, false);
        updateTextColor(openImageTxt, false);
        updateTextColor(quietImageTxt, false);

        Bundle mBundle = new Bundle();
        mBundle.putBoolean(IS_LOAD, true);
        startActivity(MainActivity.class, mBundle);
    }

    private void setLayoutPopContentClicked() {
        setViewEnabled(AUTO_ANSWER_VIEW_ID, true);
        updateTextColor(autoAnswerImageTxt, true);
        updateImageView(autoAnswerImage, R.drawable.icon_voice_filled_50);
        updateTextColor(openImageTxt, true);
        updateImageView(openImage, R.drawable.icon_lock_filled_50);
    }

    private void setLayoutAfterPopCanceled() {
        if (!isTalkClicked) {
            setViewEnabled(END_VIEW_ID, false);
            setViewEnabled(TALK_VIEW_ID, true);
            setViewEnabled(AUTO_ANSWER_VIEW_ID, true);
            updateTextColor(talkImageTxt, true);
            updateTextColor(autoAnswerImageTxt, true);
            updateTextColor(endImageTxt, false);
            updateImageView(endImage, R.drawable.icon_no_phones_50);
            updateImageView(talkImage, R.drawable.icon_call_filled_50);
            updateImageView(autoAnswerImage, R.drawable.icon_voice_filled_50);
        } else {
            setViewEnabled(END_VIEW_ID, true);
            setViewEnabled(AUTO_ANSWER_VIEW_ID, true);
            setViewEnabled(OPEN_VIEW_ID, true);
            updateImageView(endImage, R.drawable.icon_no_phones_filled_50);
            updateImageView(autoAnswerImage, R.drawable.icon_voice_filled_50);
            updateImageView(openImage, R.drawable.icon_lock_filled_50);
            updateTextColor(endImageTxt, true);
            updateTextColor(autoAnswerImageTxt, true);
            updateTextColor(openImageTxt, true);
        }
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event){
        if(mPopupWindow != null && mPopupWindow.isShowing()){
            return false;
        }
        return super.dispatchTouchEvent(event);
    }

}