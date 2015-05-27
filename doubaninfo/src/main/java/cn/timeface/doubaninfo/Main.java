package cn.timeface.doubaninfo;

import com.google.gson.Gson;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import cn.timeface.doubaninfo.beans.PhotoObj;
import cn.timeface.doubaninfo.beans.PhotoResponse;
import cn.timeface.doubaninfo.beans.SearchResponse;
import okio.BufferedSink;
import okio.Okio;

public class Main {

    static final String downloadPath = "/Users/rayboot/Downloads/info/";

    static final String INFO_FILE_NAME = "info";
    static final String PHOTO_INFO_FILE_NAME = "photoinfo";
    static final String apikey = "0df993c66c0c636e29ecbb5344252a4a";
    static final String searchUrl =
            "https://api.douban.com/v2/movie/search?q=%s";
    static final String infoUrl = "https://api.douban.com/v2/movie/subject/%s";
    static final String imageContentUrl =
            "https://api.douban.com/v2/movie/subject/%s/photos?apikey="
                    + apikey
                    + "&count=100";
    static OkHttpClient client = new OkHttpClient();

    public static void doGet(String name) throws IOException
    {
        String movieId = doSearch(name);
        String movieInfo = doGetInfo(movieId);
        File downloadDir = checkDownloadPath(name);
        writeSDFile(downloadDir.getAbsolutePath() + "/" + INFO_FILE_NAME, movieInfo);
        //download image
        System.out.println("111111   movieId = " + movieId);
        doGetImage(movieId, name);
    }

    public static String doSearch(String name) throws IOException
    {
        String url = String.format(searchUrl, name);
        SearchResponse response =
                new Gson().fromJson(new Main().run(url), SearchResponse.class);

        if (response.getSubjects() != null && response.getSubjects().size() > 0)
        {
            return response.getSubjects().get(0).getId();
        }
        else
        {
            return null;
        }
    }

    public static String doGetInfo(String movieId) throws IOException
    {
        if (movieId == null || movieId.equals(""))
        {
            return null;
        }
        String url = String.format(infoUrl, movieId);
        String response = new Main().run(url);
        System.out.println("111111  response info = " + response);
        return response;
    }

    public static void doGetImage(String movieId, String movieName) throws IOException
    {
        if (movieId == null || movieId.equals(""))
        {
            return;
        }
        String url = String.format(imageContentUrl, movieId);
        String photoResponseString = new Main().run(url);
        PhotoResponse response =
                new Gson().fromJson(photoResponseString, PhotoResponse.class);

        File downloadDir = checkDownloadPath(movieName);
        writeSDFile(downloadDir.getAbsolutePath() + "/" + PHOTO_INFO_FILE_NAME,
                photoResponseString);

        String coverImgUrl = response.getSubject().getImages().get("large");
        doDownloadImage(coverImgUrl, downloadDir, coverImgUrl.hashCode()
                + ".jpg");

        for (PhotoObj photoObj : response.getPhotos())
        {
            doDownloadImage(photoObj.getImage(), downloadDir, photoObj.getImage().hashCode() + ".jpg");
        }
    }

    static void doDownloadImage(String url, File dir, String fileName)
            throws IOException
    {
        Request request =
                new Request.Builder().url(url).build();

        System.out.println("111111  image url = " + url);
        Response imgResp = client.newCall(request).execute();
        File downloadedFile = new File(dir,fileName);

        BufferedSink sink = Okio.buffer(Okio.sink(downloadedFile));
        sink.writeAll(imgResp.body().source());
        sink.close();
    }

    static File checkDownloadPath(String movieName)
    {
        File downloadDir = new File(downloadPath, movieName);
        if (!downloadDir.exists())
        {
            downloadDir.mkdirs();
        }
        return downloadDir;
    }

    String run(String url) throws IOException
    {
        Request request = new Request.Builder().url(url).build();

        Response response = client.newCall(request).execute();
        return response.body().string();
    }

    //写文件
    public static void writeSDFile(String fileName, String write_str)
            throws IOException
    {
        File file = new File(fileName);

        FileOutputStream fos = new FileOutputStream(file);

        byte[] bytes = write_str.getBytes();

        fos.write(bytes);

        fos.close();
    }

    public static void main(String args[])
    {
        String name = "生活大爆炸";
        System.out.println(name + "   start");
        try
        {
            doGet(name);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        System.out.println(name + "   end");
    }
}
