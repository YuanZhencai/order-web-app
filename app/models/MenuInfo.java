package models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.util.Date;


/**
 * Created by guxuelong on 2014/11/12.
 */
@Entity
@Table(name = "MENU_INFO")
public class MenuInfo extends IdEntity {

    @Column(name = "FOOD_ID")
    private String foodId;
    @Column(name = "FOOD_NAME")
    private String foodName;
    @Column(name = "FOOD_TYPE")
    private String foodType;
    @Column(name = "SOURCE")
    private String source;
    @Column(name = "PRICE")
    private BigDecimal price;
    @Column(name = "COUNT")
    private BigDecimal count;
    @Column(name = "PICTURE")
    private String picture;
    @Column(name = "DEL_FLG")
    private String delFlg;
    @Column(name = "CREATE_TIME")
    private Date createTime;
    @Column(name = "CREATE_BY")
    private String createBy;
    @Column(name = "UPDATE_TIME")
    private Date updateTime;
    @Column(name = "UPDATE_BY")
    private String updateBy;

    public String getFoodId() {
        return foodId;
    }

    public void setFoodId(String foodId) {
        this.foodId = foodId;
    }

    public String getFoodName() {
        return foodName;
    }

    public void setFoodName(String foodName) {
        this.foodName = foodName;
    }

    public String getFoodType() {
        return foodType;
    }

    public void setFoodType(String foodType) {
        this.foodType = foodType;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public BigDecimal getCount() {
        return count;
    }

    public void setCount(BigDecimal count) {
        this.count = count;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public String getDelFlg() {
        return delFlg;
    }

    public void setDelFlg(String delFlg) {
        this.delFlg = delFlg;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getUpdateBy() {
        return updateBy;
    }

    public void setUpdateBy(String updateBy) {
        this.updateBy = updateBy;
    }
}
