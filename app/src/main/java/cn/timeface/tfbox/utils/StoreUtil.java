package cn.timeface.tfbox.utils;

import org.apache.http.util.EncodingUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by rayboot on 15/5/25.
 */
public class StoreUtil {
    //读文件
    public static String readSDFile(String fileName) throws IOException {

        File file = new File(fileName);

        FileInputStream fis = new FileInputStream(file);

        int length = fis.available();

        byte [] buffer = new byte[length];
        fis.read(buffer);

        String res = EncodingUtils.getString(buffer, "UTF-8");

        fis.close();
        return res;
    }

    //写文件
    public static void writeSDFile(String fileName, String write_str) throws IOException{

        File file = new File(fileName);

        FileOutputStream fos = new FileOutputStream(file);

        byte [] bytes = write_str.getBytes();

        fos.write(bytes);

        fos.close();
    }
}
