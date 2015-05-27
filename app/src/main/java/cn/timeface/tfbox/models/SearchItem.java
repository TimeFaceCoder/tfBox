package cn.timeface.tfbox.models;

import java.util.List;
import java.util.Map;

/**
 * @author rayboot
 * @from 15/5/21 15:13
 * @TODO
 */
public class SearchItem
{
    List<String> genres;
    int collect_count;
    List<UserObj> casts;
    String title;
    String original_title;
    String subtype;
    List<UserObj> directors;
    String year;
    Map<String, String> images;
    String alt;
    String id;

    public List<String> getGenres()
    {
        return genres;
    }

    public void setGenres(List<String> genres)
    {
        this.genres = genres;
    }

    public int getCollect_count()
    {
        return collect_count;
    }

    public void setCollect_count(int collect_count)
    {
        this.collect_count = collect_count;
    }

    public List<UserObj> getCasts()
    {
        return casts;
    }

    public void setCasts(List<UserObj> casts)
    {
        this.casts = casts;
    }

    public String getTitle()
    {
        return title;
    }

    public void setTitle(String title)
    {
        this.title = title;
    }

    public String getOriginal_title()
    {
        return original_title;
    }

    public void setOriginal_title(String original_title)
    {
        this.original_title = original_title;
    }

    public String getSubtype()
    {
        return subtype;
    }

    public void setSubtype(String subtype)
    {
        this.subtype = subtype;
    }

    public List<UserObj> getDirectors()
    {
        return directors;
    }

    public void setDirectors(List<UserObj> directors)
    {
        this.directors = directors;
    }

    public String getYear()
    {
        return year;
    }

    public void setYear(String year)
    {
        this.year = year;
    }

    public Map<String, String> getImages()
    {
        return images;
    }

    public void setImages(Map<String, String> images)
    {
        this.images = images;
    }

    public String getAlt()
    {
        return alt;
    }

    public void setAlt(String alt)
    {
        this.alt = alt;
    }

    public String getId()
    {
        return id;
    }

    public void setId(String id)
    {
        this.id = id;
    }
}
