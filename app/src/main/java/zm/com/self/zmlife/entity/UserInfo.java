package zm.com.self.zmlife.entity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Index;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by Administrator on 2018/4/3.
 */
@Entity
public class UserInfo {
    @Id(autoincrement = true)
    private Long id;
    private String name_yang;
    private float price_yang;
    private String name_shen;
    private float price_shen;
    private String name_zhang;
    private float price_zhang;
    private String date;
    @Generated(hash = 415305411)
    public UserInfo(Long id, String name_yang, float price_yang, String name_shen,
            float price_shen, String name_zhang, float price_zhang, String date) {
        this.id = id;
        this.name_yang = name_yang;
        this.price_yang = price_yang;
        this.name_shen = name_shen;
        this.price_shen = price_shen;
        this.name_zhang = name_zhang;
        this.price_zhang = price_zhang;
        this.date = date;
    }
    @Generated(hash = 1279772520)
    public UserInfo() {
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getName_yang() {
        return this.name_yang;
    }
    public void setName_yang(String name_yang) {
        this.name_yang = name_yang;
    }
    public float getPrice_yang() {
        return this.price_yang;
    }
    public void setPrice_yang(float price_yang) {
        this.price_yang = price_yang;
    }
    public String getName_shen() {
        return this.name_shen;
    }
    public void setName_shen(String name_shen) {
        this.name_shen = name_shen;
    }
    public float getPrice_shen() {
        return this.price_shen;
    }
    public void setPrice_shen(float price_shen) {
        this.price_shen = price_shen;
    }
    public String getName_zhang() {
        return this.name_zhang;
    }
    public void setName_zhang(String name_zhang) {
        this.name_zhang = name_zhang;
    }
    public float getPrice_zhang() {
        return this.price_zhang;
    }
    public void setPrice_zhang(float price_zhang) {
        this.price_zhang = price_zhang;
    }
    public String getDate() {
        return this.date;
    }
    public void setDate(String date) {
        this.date = date;
    }
}
