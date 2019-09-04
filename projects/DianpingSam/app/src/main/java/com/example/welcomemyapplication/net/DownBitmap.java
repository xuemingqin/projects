package com.example.welcomemyapplication.net;


import java.io.InputStream;

public class DownBitmap {

    private DownBitmap(){}
    private static DownBitmap my = null;

    public static synchronized DownBitmap getInstance(){
        if(my == null)
            my = new DownBitmap();
        return my;
    }

    public InputStream getInputStream(String Biturl){

//        HttpGet get = new HttpGet(Biturl);
//        HttpParams httpParams = new BasicHttpParams();
//        HttpConnectionParams.setConnectionTimeout(httpParams, 5*1000);
//        HttpConnectionParams.setSoTimeout(httpParams, 30*1000);
//        HttpClient httpClient = new DefaultHttpClient(httpParams);
//        try {
//            HttpResponse hr = httpClient.execute(get);
//            if(hr.getStatusLine().getStatusCode() == 200){
//                return hr.getEntity().getContent();
//            }
//        } catch (ClientProtocolException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        } catch (IOException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        }


        return null;
    }

}
