package vo;

import models.MenuInfo;

import java.math.BigDecimal;

/**
 * Created by guxuelong on 2014/12/27.
 */
public class MenuInfoVo {
    private Long id;
    private String foodId;
    private String foodName;
    private String foodType;
    private String source;
    private String priceScope;
    private BigDecimal price;
    private BigDecimal count;
    private String picture;

	public MenuInfoVo () {
	}

	public MenuInfoVo (MenuInfo menuInfo) {
		inMenuInfoVo(menuInfo);
	}

	public void inMenuInfoVo (MenuInfo menuInfo) {
		this.setFoodId(menuInfo.getFoodId());
		this.setFoodName(menuInfo.getFoodName());
		this.setFoodType(menuInfo.getFoodType());
		this.setSource(menuInfo.getSource());
		this.setPrice(menuInfo.getPrice());
		this.setCount(menuInfo.getCount());
		this.setPicture(menuInfo.getPicture());
	}

	public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getFoodType() {
        return foodType;
    }

    public void setFoodType(String foodType) {
        this.foodType = foodType;
    }

    public String getPriceScope() {
        return priceScope;
    }

    public void setPriceScope(String priceScope) {
        this.priceScope = priceScope;
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

    public String getPriceStr() {
        return price.toPlainString();
    }

    public void setPriceStr(String priceStr) {
        this.price = new BigDecimal(priceStr);
    }

    public String getCountStr() {
        return count.toPlainString();
    }

    public void setCountStr(String countStr) {
        this.count = new BigDecimal(countStr);
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }
}
