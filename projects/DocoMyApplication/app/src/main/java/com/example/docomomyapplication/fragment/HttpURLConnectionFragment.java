package com.example.docomomyapplication.fragment;

import android.content.Context;
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

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class HttpURLConnectionFragment extends Fragment{

    private static final String TAG = HttpURLConnectionFragment.class.getSimpleName( );
    private ListView listView;
    private List<Data> list;
    MybaseAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate( R.layout.fragment_httpurl,null );
        list = new ArrayList<>( );
        listView=(ListView)view.findViewById( R.id.listview );
        adapter =new MybaseAdapter();
        listView.setAdapter( adapter );
        return view;

    }

    @Override
    public void onResume() {
        super.onResume( );
        //setThread();
    }

    @Override
    public void onStop() {
        super.onStop( );
    }

    @Override
    public void onDetach () {
        super.onDetach( );
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            list.clear();
            setThread();
        }
    }

    public void setThread() {
        new Thread( new Runnable( ){
            @Override
            public void run() {
                try {
                    getAndroidArchitectureComponentsContributors( );
                } catch (JSONException e) {
                    e.printStackTrace( );
                } catch (IOException e1) {
                    e1.printStackTrace( );
                }
            }
        } ).start( );

    }

    final Handler myHandler = new Handler()
    {
        @Override
        //重写handleMessage方法,根据msg中what的值判断是否执行后续操作
        public void handleMessage(Message msg) {
            if(msg.what == 0x123)
            {
                adapter.notifyDataSetChanged();
            }
        }
    };

    private void getAndroidArchitectureComponentsContributors() throws JSONException, IOException {
        String urlStr = "https://api.github.com/repos/googlesamples/android-architecture-components/contributors";
        try {
            URL url = new URL( urlStr );
            HttpURLConnection connection = (HttpURLConnection) url.openConnection( );
            connection.setRequestMethod( "GET" );
            connection.connect( );

            if (connection.getResponseCode( ) == HttpURLConnection.HTTP_OK) {
                InputStream inputStream = connection.getInputStream( );
                String result = is2String( inputStream );
                Log.d( TAG, "getUserData" + result );

                JSONArray jsonArray = new JSONArray( result );
                list =new ArrayList<>( );
                for (int i = 0; i < jsonArray.length( ); i++) {
                    JSONObject jsonObject = jsonArray.getJSONObject( i );

                    Log.d( TAG, "login:" + jsonObject.getString( "login" ) );
                    Log.d( TAG, "html_url:" + jsonObject.getString( "html_url" ) );
                    Data data  = new Data();
                    data.setLogin( jsonObject.getString( "login" ) );
                    data.setHtmlurl( jsonObject.getString( "html_url") );
                    list.add( data );
                }
                myHandler.sendEmptyMessage(0x123);
            } else {
                Log.d( TAG, "getAndroidArchitectureComponentsContributors: " + "ERROR" );
            }
        } catch (IOException | JSONException e) {
            e.printStackTrace( );
        }

    }

    private String is2String(InputStream inputStream) {
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream( );
            byte[] buffer = new byte[1024];
            int len = 0;
            while ((len = inputStream.read( buffer )) != -1) {
                baos.write( buffer, 0, len );
            }
            baos.close( );
            inputStream.close( );
            byte[] byteArray = baos.toByteArray( );
            return new String( byteArray );
        } catch (IOException e) {
            e.printStackTrace( );
            return null;
        }
    }


      class MybaseAdapter extends BaseAdapter{


          @Override
          public int getCount() {
              return list.size();
          }

          @Override
          public Object getItem(int position) {
              return list.get( position );
          }

          @Override
          public long getItemId(int position) {
              return position;
          }

          @Override
          public View getView(int position, View convertView, ViewGroup parent) {
             ViewHodler hodle=null;
             if (convertView == null){
             convertView = LayoutInflater.from( getActivity() ).inflate( R.layout.list_item,null );
              hodle= new ViewHodler();
              hodle.tv_title = (TextView)convertView.findViewById( R.id.url_title);
              hodle.tv_content= (TextView)convertView.findViewById( R.id.url_content);
              convertView.setTag( hodle );
             }else {
                 hodle=(ViewHodler)convertView.getTag();
             }
             hodle.tv_title.setText( list.get( position ).getLogin() );
             hodle.tv_content.setText( list.get( position ).getHtmlurl() );
              return convertView;
          }
      }
      class ViewHodler{
        TextView tv_title,tv_content;
      }

}

