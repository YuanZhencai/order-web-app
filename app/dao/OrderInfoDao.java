package dao;

import models.OrderInfo;

import java.util.List;

/**
 * Created by guxuelong on 2014/12/26.
 */
public interface OrderInfoDao {
    public Long doInsert(OrderInfo orderInfo);

    public boolean doDelete(OrderInfo orderInfo);

    public boolean doUpdate(OrderInfo orderInfo);

    public List<OrderInfo> findAll();

    public List<OrderInfo> findSubset(int start, int end);

    public OrderInfo findById(Long id);

    public int num();

    public boolean isExist(OrderInfo orderInfo);
}
