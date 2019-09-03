package com.itaccess.interphone.ui.activity;

import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.itaccess.interphone.R;
import com.itaccess.interphone.adapter.NoticeListAdapter;
import com.itaccess.interphone.model.Notice;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by linxi on 2019/01/25.
 */

public class NoticeListActivity extends BaseActivity {

    public static final String TAG = "NoticeListActivity";

    @BindView(R.id.notice_list_recycler_view)
    RecyclerView mNoticeRecyclerView;

    private LinearLayoutManager mLayoutManager;
    private NoticeListAdapter mNoticeListAdapter;
    private List<Notice> mDataList;

    @Override
    public int getLayoutRes(){
        return R.layout.activity_notice_list;
    }

    @Override
    public void init() {
        super.init();
        setToolBarTitle(getResStr(R.string.notice_list_title));
        showTitleLeftIcon(true);
        setTestData();
        setRecyclerView();
    }

    private void setRecyclerView() {
        mLayoutManager = new LinearLayoutManager(this);
        mNoticeRecyclerView.setLayoutManager(mLayoutManager);
        mNoticeListAdapter = new NoticeListAdapter(mDataList);
        mNoticeRecyclerView.setAdapter(mNoticeListAdapter);

        RecyclerView.ItemDecoration dividerItemDecoration = new DividerItemDecoration(mNoticeRecyclerView.getContext(),
                mLayoutManager.getOrientation());
        mNoticeRecyclerView.addItemDecoration(dividerItemDecoration);
    }

    @OnClick(R.id.txt_left)
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.txt_left:
                startActivity(MainActivity.class);
                break;
        }
    }

    private void setTestData() {
        mDataList = new ArrayList<>();
        Notice mNotice1 = new Notice();
        mNotice1.setTimestamp("2017/09/01");
        mNotice1.setTitle("システムメンテナンス");
        mNotice1.setContent("08:00-09:00システムメンテナンスの為、利用できません;08:00-09:00システムメンテナンスの為、利用できません;" +
                "08:00-09:00システムメンテナンスの為、利用できません;" +
                "08:00-09:00システムメンテナンスの為、http://ojyz0c8un.bkt.clouddn.com/b_1.jpg");
        mDataList.add(mNotice1);

        Notice mNotice2 = new Notice();
        mNotice2.setTimestamp("2017/09/02");
        mNotice2.setTitle("マンションメンテナンス");
        mNotice2.setContent("08:00-09:00エレベーターの検定のため、エレベーターが利用できません　http://ojyz0c8un.bkt.clouddn.com/b_1.jpg");
        mDataList.add(mNotice2);

        Notice mNotice3 = new Notice();
        mNotice3.setTimestamp("2017/09/03");
        mNotice3.setTitle("システムメンテナンス");
        mNotice3.setContent("08:00-09:00システムメンテナンスの為利用できません");
        mDataList.add(mNotice3);

        Notice mNotice4 = new Notice();
        mNotice4.setTimestamp("2017/09/04");
        mNotice4.setTitle("システムメンテナンス");
        mNotice4.setContent("08:00-09:00システムメンテナンスの為、利用できません");
        mDataList.add(mNotice4);

        Notice mNotice5 = new Notice();
        mNotice5.setTimestamp("2017/09/05");
        mNotice5.setTitle("システムメンテナンス");
        mNotice5.setContent("08:00-09:00システムメンテナンスの為、利用できません");
        mDataList.add(mNotice5);

        Notice mNotice6 = new Notice();
        mNotice6.setTimestamp("2017/09/06");
        mNotice6.setTitle("システムメンテナンス");
        mNotice6.setContent("08:00-09:00システムメンテナンスの為、利用できません");
        mDataList.add(mNotice6);

        Notice mNotice7 = new Notice();
        mNotice7.setTimestamp("2017/09/07");
        mNotice7.setTitle("システムメンテナンス");
        mNotice7.setContent("08:00-09:00システムメンテナンスの為、利用できません");
        mDataList.add(mNotice7);

        Notice mNotice8 = new Notice();
        mNotice8.setTimestamp("2017/09/08");
        mNotice8.setTitle("システムメンテナンス");
        mNotice8.setContent("08:00-09:00システムメンテナンスの為、利用できません");
        mDataList.add(mNotice8);

    }
}
