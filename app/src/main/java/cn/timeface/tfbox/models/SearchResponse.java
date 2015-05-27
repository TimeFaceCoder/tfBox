package cn.timeface.tfbox.models;

import java.util.List;

/**
 * @author rayboot
 * @from 15/5/21 15:12
 * @TODO
 */
public class SearchResponse
{
    int count;
    int start;
    int total;
    List<SearchItem> subjects;
    String title;

    public int getCount()
    {
        return count;
    }

    public void setCount(int count)
    {
        this.count = count;
    }

    public int getStart()
    {
        return start;
    }

    public void setStart(int start)
    {
        this.start = start;
    }

    public int getTotal()
    {
        return total;
    }

    public void setTotal(int total)
    {
        this.total = total;
    }

    public List<SearchItem> getSubjects()
    {
        return subjects;
    }

    public void setSubjects(List<SearchItem> subjects)
    {
        this.subjects = subjects;
    }

    public String getTitle()
    {
        return title;
    }

    public void setTitle(String title)
    {
        this.title = title;
    }
}
