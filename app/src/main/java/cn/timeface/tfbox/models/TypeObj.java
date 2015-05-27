package cn.timeface.tfbox.models;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Select;

import java.util.List;

/**
 * Created by rayboot on 15/5/26.
 */

@Table(name = "Types")
public class TypeObj extends Model {

    @Column(name = "typeName", unique = true)
    String name;

    public TypeObj() {
    }

    public TypeObj(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public static List<TypeObj> getAll() {
        return new Select()
                .from(TypeObj.class)
                .orderBy("typeName ASC")
                .execute();
    }
}
