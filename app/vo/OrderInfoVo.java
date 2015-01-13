package vo;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by guxuelong on 2014/12/27.
 */
public class OrderInfoVo {
    private Long id;
    private String userId;
    private String userName;
    private String foodId;
    private String foodName;
    private String source;
    private BigDecimal price;
    private String priceStr;
    private String delFlg;
    private Date createTime;
    private String createBy;
    private Date updateTime;
    private String updateBy;
    private String canBeToDel = "N";

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

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
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPriceStr() {
        return price.toPlainString();
    }

    public void setPriceStr(String priceStr) {
        this.price = new BigDecimal(priceStr);
    }

    public String getCanBeToDel() {
        return canBeToDel;
    }

    public void setCanBeToDel(String canBeToDel) {
        this.canBeToDel = canBeToDel;
    }
}
