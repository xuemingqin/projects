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
import com.example.docomomyapplication.data.Data;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class OkhttpFragment extends Fragment{

    private static final String TAG = OkhttpFragment.class.getSimpleName();
    private OkHttpClient client;
    private ListView listView;
    private List<Data> listok;
    private MbaseAdapter madapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        client=new OkHttpClient();
        getAndroidArchitectureComponentsContributors();
        View view=inflater.inflate( R.layout.fragment_okhttp,null );
        listok = new ArrayList<>( );
        listView=(ListView)view.findViewById( R.id.lv_ok );
        madapter =new MbaseAdapter();
        listView.setAdapter( madapter );
        return view;



    }
    final Handler mHandler = new Handler()
    {
        @Override
        //重写handleMessage方法,根据msg中what的值判断是否执行后续操作
        public void handleMessage(Message msg) {
            if(msg.what == 0x123)
            {
                madapter.notifyDataSetChanged();
            }
        }
    };

    private void getAndroidArchitectureComponentsContributors() {
        Request request=new Request.Builder()
                .url( "https://api.github.com/repos/googlesamples/android-architecture-components/contributors" )
                .build();
        Call call=client.newCall( request );
        call.enqueue( new Callback( ){
            @Override
            public void onFailure(Call call, IOException e) {
                Log.d(TAG, "onFailure: ");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                try{
                JSONArray jsonArray=new JSONArray( response.body().string() );
                for (int i=0; i<jsonArray.length();i++){
                    JSONObject jsonObject=jsonArray.getJSONObject( i );

                    Log.d( TAG,"login:"+jsonObject.getString( "login" ));
                    Log.d( TAG,"html_url"+ jsonObject.getString( "html_url" ));

                    Data data  = new Data();
                    data.setLogin( jsonObject.getString( "login" ) );
                    data.setHtmlurl( jsonObject.getString( "html_url") );
                    listok.add( data );
                }
                mHandler.sendEmptyMessage(0x123);
            }catch (JSONException e){
                    e.printStackTrace();
                }
            }
        } );
    }
    class MbaseAdapter extends BaseAdapter{


        @Override
        public int getCount() {
            return listok.size();
        }

        @Override
        public Object getItem(int position) {
            return listok.get( position );
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
           OkViewHodler hodler=null;
            if (convertView == null){
                convertView = LayoutInflater.from( getActivity() ).inflate( R.layout.ok_list_item,null );
                hodler= new OkViewHodler();
                hodler.tvbig = (TextView)convertView.findViewById( R.id.ok_title);
                hodler.tvsml = (TextView)convertView.findViewById( R.id.ok_content);
                convertView.setTag( hodler );
            }else {
                hodler=(OkViewHodler)convertView.getTag();
            }
            hodler.tvbig.setText( listok.get( position ).getLogin() );
            hodler.tvsml.setText( listok.get( position ).getHtmlurl() );
            return convertView;
        }
    }
    class OkViewHodler{
        TextView tvbig,tvsml;
    }

}