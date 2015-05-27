package cn.timeface.tfbox.events;

/**
 * Created by rayboot on 15/5/22.
 */
public class ChangeTypeEvent {
    String typeName;

    public ChangeTypeEvent(String typeName) {
        this.typeName = typeName;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public boolean isAllType() {
        return this.typeName.equals("全部");
    }
}
