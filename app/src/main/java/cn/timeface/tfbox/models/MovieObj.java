package cn.timeface.tfbox.models;

import android.text.TextUtils;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Select;
import com.google.gson.Gson;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import cn.timeface.tfbox.utils.PinyinUtils;
import cn.timeface.tfbox.utils.StoreUtil;

/**
 * Created by rayboot on 15/5/22.
 */
@Table(name = "Movies")
public class MovieObj extends Model implements Serializable {
    @Column(name = "moviePath", unique = true)
    String moviePath;
    @Column(name = "movieId")
    String movieId;
    @Column(name = "movieName")
    String movieName;
    @Column(name = "pinyin")
    String pinyinName;
    @Column(name = "movieCover")
    String movieCover;
    @Column(name = "directors")
    String directors;//多个,分割
    @Column(name = "casts")
    String casts;  //多个,分割
    @Column(name = "genres")
    String genres;//多个,分割
    @Column(name = "year")
    String year;
    @Column(name = "summary")
    String summary;
    @Column(name = "photos")
    String photos;//多个,分割

    String infoPath;

    public MovieObj() {
    }

    public MovieObj(String moviePath) {
        this.moviePath = moviePath;
        this.movieName = moviePath.substring(moviePath.lastIndexOf("/") + 1, moviePath.lastIndexOf("."));
        this.pinyinName = PinyinUtils.cn2FirstSpell(movieName).toUpperCase();

        this.infoPath = getInfoPath();
        String infoFile = infoPath + "/" + movieName + "/" + "info";
        String photoinfoFile = infoPath + "/" + movieName + "/" + "photoinfo";

        try {
            if (!new File(infoFile).exists() || !new File(photoinfoFile).exists()) {
                return;
            }
            InfoResponse infoResponse = new Gson().fromJson(StoreUtil.readSDFile(infoFile), InfoResponse.class);
            PhotoResponse photoResponse = new Gson().fromJson(StoreUtil.readSDFile(photoinfoFile), PhotoResponse.class);
            this.movieId = infoResponse.getId();
            this.movieCover = infoResponse.getImages().get("large").hashCode() + ".jpg";
            this.directors = infoResponse.getDirectorsString();
            this.casts = infoResponse.getCastsString();
            this.genres = infoResponse.getGenresString();
            this.year = infoResponse.getYear();
            this.summary = infoResponse.getSummary();
            this.photos = photoResponse.getPhotosString();

            for (String type : infoResponse.genres) {
                new TypeObj(type).save();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean isSuccessLoadData() {
        return !TextUtils.isEmpty(this.movieId);
    }

    public String getInfoPath() {
        if (TextUtils.isEmpty(moviePath)) {
            return null;
        }
        if (!TextUtils.isEmpty(infoPath)) {
            return infoPath;
        }
        this.infoPath = this.moviePath.substring(0, moviePath.lastIndexOf("/")) + "/info/";

        return infoPath;
    }

    public String getMoviePath() {
        return moviePath;
    }

    public void setMoviePath(String moviePath) {
        this.moviePath = moviePath;
    }

    public String getMovieName() {
        return movieName;
    }

    public void setMovieName(String movieName) {
        this.movieName = movieName;
    }

    public String getMovieCover() {
        return getRealFilePath(movieCover);
    }

    public String getRealFilePath(String fileName) {
        return getInfoPath() + this.movieName + "/" + fileName;
    }

    public void setMovieCover(String movieCover) {
        this.movieCover = movieCover;
    }

    public String[] getDirectors() {
        return directors.split(",");
    }

    public void setDirectors(String directors) {
        this.directors = directors;
    }

    public String[] getCasts() {
        return casts.split(",");
    }

    public void setCasts(String casts) {
        this.casts = casts;
    }

    public String[] getGenres() {
        return genres.split(",");
    }

    public void setGenres(String genres) {
        this.genres = genres;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String[] getPhotos() {
        return photos.split(",");
    }

    public void setPhotos(String photos) {
        this.photos = photos;
    }


    public String getPinyinName() {
        return pinyinName;
    }

    public void setPinyinName(String pinyinName) {
        this.pinyinName = pinyinName;
    }

    public String getMovieId() {
        return movieId;
    }

    public void setMovieId(String movieId) {
        this.movieId = movieId;
    }

    public void setInfoPath(String infoPath) {
        this.infoPath = infoPath;
    }

    public static List<MovieObj> getAllFromType(String type) {
        return new Select()
                .from(MovieObj.class)
                .where("genres like ?", "%" + type + "%")
                .orderBy("movieName ASC")
                .execute();
    }

    public static List<MovieObj> getAllFromSearchKey(String key) {
        if (TextUtils.isEmpty(key)) {
            return getAll();
        } else {
            return new Select()
                    .from(MovieObj.class)
                    .where("pinyin like ?", "%" + key + "%")
                    .orderBy("movieName ASC")
                    .execute();
        }
    }

    public static MovieObj getItemById(String movieId) {
        return new Select()
                .from(MovieObj.class)
                .where("movieId == ?", movieId)
                .executeSingle();
    }

    public static List<MovieObj> getAll() {
        return new Select()
                .from(MovieObj.class)
                .orderBy("movieName ASC")
                .execute();
    }

    public static List<MovieObj> getAllFromCollect() {
        List<CollectObj> collectObjs = CollectObj.getAll();
        List<MovieObj> result = new ArrayList<>(10);

        for (CollectObj obj : collectObjs) {
            MovieObj movieObj = getItemById(obj.getMovieId());
            if (movieObj != null) {
                result.add(movieObj);
            }
        }
        return result;
    }
}
