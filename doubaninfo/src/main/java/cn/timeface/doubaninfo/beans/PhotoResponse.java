package cn.timeface.doubaninfo.beans;

import java.util.List;

/**
 * @author rayboot
 * @from 15/5/21 18:05
 * @TODO
 */
public class PhotoResponse
{
    int count;
    List<PhotoObj> photos;
    int total;
    InfoResponse subject;
    int start;

    public int getCount()
    {
        return count;
    }

    public void setCount(int count)
    {
        this.count = count;
    }

    public List<PhotoObj> getPhotos()
    {
        return photos;
    }

    public void setPhotos(List<PhotoObj> photos)
    {
        this.photos = photos;
    }

    public int getTotal()
    {
        return total;
    }

    public void setTotal(int total)
    {
        this.total = total;
    }

    public InfoResponse getSubject()
    {
        return subject;
    }

    public void setSubject(InfoResponse subject)
    {
        this.subject = subject;
    }

    public int getStart()
    {
        return start;
    }

    public void setStart(int start)
    {
        this.start = start;
    }
}
