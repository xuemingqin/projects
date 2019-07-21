package com.example.welcomemyapplication;

import android.app.Activity;
import android.net.http.HttpResponseCache;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ListView;

import org.xml.sax.Parser;
import org.xml.sax.SAXException;
import org.xml.sax.SAXNotRecognizedException;
import org.xml.sax.SAXNotSupportedException;
import org.xml.sax.XMLReader;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;


public class CityActivity extends Activity {
    private List<City> list;
    private ListView lv;
    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate ( savedInstanceState );
        list=new ArrayList<City>();
        lv=(ListView)findViewById ( R.id.city_list_lv ) ;
        View view= LayoutInflater.from ( this).inflate ( R.layout.header_city_list,null );
        //lv.addHeaderView ( view );
        initCityList();//将数据加载到list中
    }
//    public class CityDataTask extends AsyncTask<Void,Void,List<City>>{
//
//        @Override
//        protected List<City> doInBackground(Void... params) {
//            HttpClient client=new DefaultHttpClient();
//            HttpPost httpPost=new Httpost();
//            try {
//                HttpResponse httpResponse =client.execute(httpPost);
//                if (httpResponseCache.getS)
//
//
//            } catch (Exception e) {
//                e.printStackTrace ( );
//            }
//            return null;
//        }
//    }
      private void  initCityList()  //本项目中 city 列表存在assets/citys.xml文件中，通过xml解析得到想要的city数据
      {
          //②得到SAX解析工厂
          SAXParserFactory factory = SAXParserFactory.newInstance();
          try {
              SaxHelper helper = new SaxHelper ();
              SAXParser parser = factory.newSAXParser();
              InputStream inputStream=getAssets().open("citys.xml");
              parser.parse(inputStream, helper);
              inputStream.close ();
              list =  helper.getCitys ();
          } catch (ParserConfigurationException e) {
              // TODO Auto-generated catch block
              e.printStackTrace();
          } catch (SAXException e) {
              // TODO Auto-generated catch block
              e.printStackTrace();
          } catch (IOException e) {
              // TODO Auto-generated catch block
              e.printStackTrace();
          }


      }

}
