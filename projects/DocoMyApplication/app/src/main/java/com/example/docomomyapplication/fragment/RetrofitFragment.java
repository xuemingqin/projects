package com.example.docomomyapplication.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.docomomyapplication.R;
import com.example.docomomyapplication.service.RetrofitListService;
import com.example.docomomyapplication.data.Data;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RetrofitFragment extends Fragment{
    private static final String TAG=RetrofitFragment.class.getSimpleName();
    private RetrofitListService service;
    private ListView lv_re;
    private List<Data> list_re;
    private RebaseAdapter rebaseAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        service=new RetrofitListClient().getService();
        getAndroidArchitectureComponentsContributors();
        View view=inflater.inflate( R.layout.fragment_okhttp,null);
        lv_re=view.findViewById( R.id.lv_ok );
        list_re=new ArrayList<>( );
        rebaseAdapter =new RebaseAdapter();
        lv_re.setAdapter(rebaseAdapter );
        return view;

    }
    final Handler mHandler = new Handler()
    {
        @Override
        //重写handleMessage方法,根据msg中what的值判断是否执行后续操作
        public void handleMessage(Message msg) {
            if(msg.what == 0x125)
            {
                rebaseAdapter.notifyDataSetChanged();
            }
        }
    };


    private void getAndroidArchitectureComponentsContributors() {
       Call<List<Data>> call= service.getContributors();
        call.enqueue( new Callback<List<Data>>( ){
            @Override
            public void onResponse(Call<List<Data>> call, Response<List<Data>> response) {
                Log.d( TAG,"onResponse:");

                if (response.isSuccessful()){
                    list_re=response.body();
                    mHandler.sendEmptyMessage(0x125);
                }
            }

            @Override
            public void onFailure(Call<List<Data>> call, Throwable t) {
                Log.d( TAG,"onFailure" );

            }
        } );
    }
    class RebaseAdapter extends BaseAdapter{


        @Override
        public int getCount() {
            return list_re.size();
        }

        @Override
        public Object getItem(int position) {
            return list_re.get( position );
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ReViewHodler hodler=null;
            if (convertView == null){
                convertView = LayoutInflater.from( getActivity() ).inflate( R.layout.ok_list_item,null );
                hodler= new ReViewHodler();
                hodler.tv_title = (TextView)convertView.findViewById( R.id.ok_title);
                hodler.tv_content = (TextView)convertView.findViewById( R.id.ok_content);
                convertView.setTag( hodler );
            }else {
                hodler=(ReViewHodler)convertView.getTag();
            }
            hodler.tv_title.setText( list_re.get( position ).getLogin() );
            hodler.tv_content.setText( list_re.get( position ).getHtmlurl() );
            return convertView;
        }
    }
    class ReViewHodler{
        TextView tv_title,tv_content;
    }

}

