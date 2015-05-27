package cn.timeface.doubaninfo.beans;

import java.util.List;
import java.util.Map;

/**
 * @author rayboot
 * @from 15/5/21 15:57
 * @TODO
 */
public class InfoResponse
{
    RatingObj rating;
    int reviews_count;
    int wish_count;
    int collect_count;
    String douban_site;
    String year;
    Map<String, String> images;
    String alt;
    String id;
    String mobile_url;
    String title;
    String do_count;
    String seasons_count;
    String schedule_url;
    String episodes_count;
    List<String> genres;
    List<String> countries;
    List<UserObj> casts;
    String current_season;
    String original_title;
    String summary;
    String subtype;
    List<UserObj> directors;
    int comments_count;
    int ratings_count;
    List<String> aka;

    public RatingObj getRating()
    {
        return rating;
    }

    public void setRating(RatingObj rating)
    {
        this.rating = rating;
    }

    public int getReviews_count()
    {
        return reviews_count;
    }

    public void setReviews_count(int reviews_count)
    {
        this.reviews_count = reviews_count;
    }

    public int getWish_count()
    {
        return wish_count;
    }

    public void setWish_count(int wish_count)
    {
        this.wish_count = wish_count;
    }

    public int getCollect_count()
    {
        return collect_count;
    }

    public void setCollect_count(int collect_count)
    {
        this.collect_count = collect_count;
    }

    public String getDouban_site()
    {
        return douban_site;
    }

    public void setDouban_site(String douban_site)
    {
        this.douban_site = douban_site;
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

    public String getMobile_url()
    {
        return mobile_url;
    }

    public void setMobile_url(String mobile_url)
    {
        this.mobile_url = mobile_url;
    }

    public String getTitle()
    {
        return title;
    }

    public void setTitle(String title)
    {
        this.title = title;
    }

    public String getDo_count()
    {
        return do_count;
    }

    public void setDo_count(String do_count)
    {
        this.do_count = do_count;
    }

    public String getSeasons_count()
    {
        return seasons_count;
    }

    public void setSeasons_count(String seasons_count)
    {
        this.seasons_count = seasons_count;
    }

    public String getSchedule_url()
    {
        return schedule_url;
    }

    public void setSchedule_url(String schedule_url)
    {
        this.schedule_url = schedule_url;
    }

    public String getEpisodes_count()
    {
        return episodes_count;
    }

    public void setEpisodes_count(String episodes_count)
    {
        this.episodes_count = episodes_count;
    }

    public List<String> getGenres()
    {
        return genres;
    }

    public void setGenres(List<String> genres)
    {
        this.genres = genres;
    }

    public List<String> getCountries()
    {
        return countries;
    }

    public void setCountries(List<String> countries)
    {
        this.countries = countries;
    }

    public List<UserObj> getCasts()
    {
        return casts;
    }

    public void setCasts(List<UserObj> casts)
    {
        this.casts = casts;
    }

    public String getCurrent_season()
    {
        return current_season;
    }

    public void setCurrent_season(String current_season)
    {
        this.current_season = current_season;
    }

    public String getOriginal_title()
    {
        return original_title;
    }

    public void setOriginal_title(String original_title)
    {
        this.original_title = original_title;
    }

    public String getSummary()
    {
        return summary;
    }

    public void setSummary(String summary)
    {
        this.summary = summary;
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

    public int getComments_count()
    {
        return comments_count;
    }

    public void setComments_count(int comments_count)
    {
        this.comments_count = comments_count;
    }

    public int getRatings_count()
    {
        return ratings_count;
    }

    public void setRatings_count(int ratings_count)
    {
        this.ratings_count = ratings_count;
    }

    public List<String> getAka()
    {
        return aka;
    }

    public void setAka(List<String> aka)
    {
        this.aka = aka;
    }
}
