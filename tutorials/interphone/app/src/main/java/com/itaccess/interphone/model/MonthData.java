package com.itaccess.interphone.model;

import android.content.Context;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.List;

/**
 * Created by linxi on 2019/02/09.
 */

public class MonthData {

    private static final String TAG = "MonthData";

    /** 保存ファイルディレクトリ */
    public static String mFileSaveDir = "/month/";

    private byte[] monthData;

    /**
     * Monthデータ情報保存
     * @param context コンテキスト
     * @param fileName 保存ファイル名
     * @return 処理結果
     */
    public final boolean save(final Context context, final String fileName) {
        FileOutputStream fos = null;
        try {
            File dir = new File(new StringBuffer(context.getFilesDir().getPath()).append(mFileSaveDir).toString());
            if (!dir.exists()) {
                dir.mkdir();
            }
            File file = new File(dir, fileName);
            if (file.exists()) {
                file.delete();
            }
            fos = new FileOutputStream(file);
            fos.write(this.monthData);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if( fos != null ){
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return true;
    }

    /**
     * Monthデータ情報読み込み
     * @param context コンテキスト
     * @param fileName 読み込みファイル名
     * @return 処理結果
     */
     public final boolean load(final Context context, final String fileName) {
         FileInputStream fis = null;
         try {
             File dir = new File(new StringBuffer(context.getFilesDir().getPath()).append(mFileSaveDir).toString());
             File file = new File(dir, fileName);
             fis = new FileInputStream(file);

             byte[] readBytes = new byte[fis.available()];
             fis.read(readBytes, 0, fis.available());
             this.monthData = readBytes;
         } catch (IOException e) {
             e.printStackTrace();
         }
         return true;
     }

    public void SetMonthData(List<Message> mMessageList) {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ObjectOutputStream oos = null;
        try {
            oos = new ObjectOutputStream(bos);
            oos.writeObject(mMessageList);
            this.monthData = bos.toByteArray();
            oos.close();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (bos != null) {
                try {
                    bos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public List<Message> getMonthData() {
        ByteArrayInputStream bis = new ByteArrayInputStream(this.monthData);
        ObjectInputStream ois = null;
        List<Message> messages = null;
        try {
            ois = new ObjectInputStream(bis);
            messages = (List<Message>)ois.readObject();
            ois.close();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            if (bis != null) {
                try {
                    bis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return messages;
    }

}
