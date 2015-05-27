package cn.timeface.tfbox.events;

/**
 * Created by rayboot on 15/5/25.
 */
public class SearchKeyEvent {
    String key;

    public SearchKeyEvent(String key) {
        this.key = key;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}
