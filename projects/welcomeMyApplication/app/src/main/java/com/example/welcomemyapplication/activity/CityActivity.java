package com.example.welcomemyapplication.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.welcomemyapplication.data.City;
import com.example.welcomemyapplication.R;
import com.example.welcomemyapplication.helper.SaxXMLParser;
import com.example.welcomemyapplication.views.SideBar;

import org.xml.sax.SAXException;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;


public class CityActivity extends Activity {
    private SideBar sidebar;

    private TextView dialog;

    private RadioButton rb1;
    private RadioButton rb2;

    private TextView tv_back;

    private List<City> list;
    private ListView lv;
    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate ( savedInstanceState );
        setContentView( R.layout.activity_city_list );

        rb1 = (RadioButton) findViewById( R.id.city_list_rb1 );
        rb2 = (RadioButton) findViewById( R.id.city_list_rb1 );
        dialog = (TextView) findViewById( R.id.dialog ) ;
        sidebar = (SideBar) findViewById( R.id.sidebar );
        tv_back = (TextView) findViewById( R.id.activity_city_list_tv_back );

        list = new ArrayList<City>();
        lv = (ListView)findViewById ( R.id.city_list_lv ) ;
        View view= LayoutInflater.from ( this).inflate ( R.layout.header_city_list,null );

        rb1.setChecked( true );

        lv.addHeaderView(view);
        initCityList();//将数据加载到list中

        CityListAdapter adapter=new CityListAdapter(list, this);//新建适配器
        lv.setAdapter(adapter);//绑定适配器

        sidebar.setTextView(dialog);

        //sidebar设置监听
        sidebar.setOnTouchingLetterChangedListener(new SideBar.OnTouchingLetterChangedListener() {

            @Override
            public void onTouchingLetterChanged(String sortKey) {
                // TODO Auto-generated method stub
                lv.setSelection(findIndexBySortKey(list, sortKey)+1); //因为有header 所以+1
            }
        });

        lv.setOnItemClickListener( new ListView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent it=new Intent();
                Toast.makeText(CityActivity.this, "position:"+position+" id:"+id, Toast.LENGTH_SHORT).show();
                if(position!=0){   //position==0 证明是点击了 listview的header ，这里暂时不处理点击了header的情况
                    TextView textView=(TextView) view.findViewById(R.id.city_list_item_tv_cityName);
                    it.putExtra("cityName", textView.getText().toString());
                    Log.e("jhd1", "onItemClick:"+textView.getText().toString());
                    setResult(RESULT_OK, it);
                    finish();
                }
            }
        } );

        tv_back.setOnClickListener( new View.OnClickListener( ){
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.activity_city_list_tv_back://按了返回键 不用处理直接finish
                        finish();
                        break;
                    default:
                        break;
                }
            }
        } );
    }

    private void initCityList()  //本项目中 city 列表存在assets/citys.xml文件中，通过xml解析得到想要的city数据
    {
        SaxXMLParser parser=new SaxXMLParser();
        try {
            InputStream inputStream= getAssets().open("citys.xml");
            list = parser.getListBySaxXMLParser(inputStream);
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


    //根据sortkey找到索引位置
    private int findIndexBySortKey(List<City> list, String sortKey)
    {
        if(list!=null)
        {
            for(int i=0;i<list.size();i++){
                City c=list.get(i);
                if(sortKey.equals(c.getSortKey())){
                    return i;
                }
            }
        }
        else{
            Log.e("jhd", "没有根据传来的sortKey找到对应的索引值");
        }
        return -2; ///由于有header 所以不能返回-1，返回-2
    }

    private StringBuffer buffer=new StringBuffer();//y用来第一次保存首字母索引
    private List<String> firstList=new ArrayList<String>();//保存索引值对面的城市名
    //内部类 适配器
    class CityListAdapter extends BaseAdapter{
        private List<City> listdata;
        private LayoutInflater inflater;


        public CityListAdapter(List<City> list, Context context) {

            this.listdata = list;
            inflater=LayoutInflater.from(context);
        }

        @Override
        public int getCount() {
            // TODO Auto-generated method stub
            return listdata.size();
        }

        @Override
        public Object getItem(int arg0) {
            // TODO Auto-generated method stub
            return listdata.get(arg0);
        }

        @Override
        public long getItemId(int arg0) {
            // TODO Auto-generated method stub
            return arg0;
        }

        @Override
        public View getView(int arg0, View converView, ViewGroup arg2) {
            // TODO Auto-generated method stub
            ViewHolder vh=null;
            if(converView==null)
            {
                vh = new ViewHolder();
                converView=inflater.inflate(R.layout.activity_city_list_item, null);
                vh.sortKey=(TextView) converView.findViewById(R.id.city_list_item_tv_sortKey);
                vh.cityName=(TextView) converView.findViewById(R.id.city_list_item_tv_cityName);
                converView.setTag(vh);
            }
            else
            {
                vh=(ViewHolder) converView.getTag();
            }
            //数据显示处理
            City c=listdata.get(arg0);
            String sort=c.getSortKey();
            String name=c.getName();

            if(buffer.indexOf(sort)==-1){   //以下俩个if 设置显示首字母索引
                buffer.append(sort);
                firstList.add(name);
            }
            if(firstList.contains(name)){
                vh.sortKey.setText(sort);
                vh.sortKey.setVisibility(View.VISIBLE);//显示
            }else {
                vh.sortKey.setVisibility(View.GONE);//不显示
            }
            vh.cityName.setText(name);

            return converView;
        }

        class ViewHolder{
            TextView sortKey;
            TextView cityName;
        }

    }

    @Override
    public void finish() {
        // TODO Auto-generated method stub

        super.finish();
        //设置activity跳转的动画
        this.overridePendingTransition(R.anim.activity_slide_in_right,R.anim.activity_slide_out_left);

    }

}
