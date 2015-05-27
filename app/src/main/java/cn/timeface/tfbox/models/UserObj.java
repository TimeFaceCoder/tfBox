package cn.timeface.tfbox.models;

import java.util.Map;

/**
 * @author rayboot
 * @from 15/5/21 15:14
 * @TODO
 */
public class UserObj
{
    Map<String, String> avatars;
    String alt;
    String id;
    String name;

    public Map<String, String> getAvatars()
    {
        return avatars;
    }

    public void setAvatars(Map<String, String> avatars)
    {
        this.avatars = avatars;
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

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }
}
