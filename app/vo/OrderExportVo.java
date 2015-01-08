package vo;

import java.math.BigDecimal;

/**
 * Created by guxuelong on 2015/1/8.
 */
public class OrderExportVo {
    //用户名
    private String userName;
    // 菜肴名称
    private String foodName;
    // 饭店名称
    private String restaurantName;
    // 菜肴单价
    private BigDecimal price;
    // 菜肴点菜数量
    private BigDecimal count;
    // 总价格
    private BigDecimal totalPrice;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getFoodName() {
        return foodName;
    }

    public void setFoodName(String foodName) {
        this.foodName = foodName;
    }

    public String getRestaurantName() {
        return restaurantName;
    }

    public void setRestaurantName(String restaurantName) {
        this.restaurantName = restaurantName;
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

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }
}
