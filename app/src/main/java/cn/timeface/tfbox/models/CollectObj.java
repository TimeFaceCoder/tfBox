package cn.timeface.tfbox.models;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Select;

import java.util.List;

/**
 * Created by rayboot on 15/5/26.
 */
@Table(name = "Collects")
public class CollectObj extends Model {

    @Column(name = "movieId", unique = true)
    String movieId;

    public CollectObj() {
    }

    public CollectObj(String movieId) {
        this.movieId = movieId;
    }

    public String getMovieId() {
        return movieId;
    }

    public void setMovieId(String movieId) {
        this.movieId = movieId;
    }

    public static CollectObj getById(String movieId) {
        return new Select()
                .from(CollectObj.class)
                .where("movieId == ?", movieId)
                .executeSingle();
    }
    public static List<CollectObj> getAll() {
        return new Select()
                .from(CollectObj.class)
                .execute();
    }
}
