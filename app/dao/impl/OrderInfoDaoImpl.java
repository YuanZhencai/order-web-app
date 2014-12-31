package dao.impl;

import dao.EntityBaseDao;
import dao.OrderInfoDao;
import models.OrderInfo;

import java.sql.Timestamp;
import java.util.List;

/**
 * Created by guxuelong on 2014/12/26.
 */
public class OrderInfoDaoImpl extends EntityBaseDao implements OrderInfoDao {
    /**
     *insert into database
     */
    @Override
    public  Long doInsert(OrderInfo orderInfo){
        OrderInfo result = create(orderInfo);

        return result.getId();
    }

    /**
     *delete from database
     */
    @Override
    public  boolean doDelete(OrderInfo orderInfo){
        update(orderInfo);
        return true;
    }

    /**
     *update in database
     */
    @Override
    public  boolean doUpdate( OrderInfo orderInfo) {
        OrderInfo OrderInfoOrg = findById(orderInfo.getId());
        if ( OrderInfoOrg==null){
            return false;
        }
        orderInfo.setDelFlg(OrderInfoOrg.getDelFlg());
        orderInfo.setCreateTime(OrderInfoOrg.getCreateTime());
        orderInfo.setUpdateTime(new Timestamp(System.currentTimeMillis()));
        update(orderInfo);
        return true;
    }

    @Override
    public List<OrderInfo> findAll(){
        return findAll(OrderInfo.class);
    }

    /**
     * in database
     * fetch a set of bank from database
     * @param end
     * @param start
     * @return OrderInfo<list>
     */
    @Override
    public  List<OrderInfo> findSubset ( int start,int end ) {
        return findAll(OrderInfo.class).subList(start, end);
    }

    /**
     * in database
     * select a piece of bank info via ID
     * @param id
     * @return orderInfo<OrderInfo>
     */
    @Override
    public OrderInfo findById ( Long id ) {
        List<OrderInfo> orderInfo = findBy(OrderInfo.class, "id", id);
        if ( orderInfo.isEmpty() ) {
            return null;
        }else{
            return orderInfo.get(0);
        }
    }

    /**
     * count the num of OrderInfo
     * @return <int>
     */
    @Override
    public int  num() {
        return findAll(OrderInfo.class).size();
    }

    /**
     * in database
     * query to see it if exist
     * @param orderInfo <OrderInfo>
     * @return <boolean>
     */
    @Override
    public  boolean isExist( OrderInfo orderInfo) {
        List<OrderInfo> userList = (List<OrderInfo>) findBy(OrderInfo.class,"foodId",orderInfo.getFoodId());
        if(userList!=null && !userList.isEmpty() && "N".equals(userList.get(0).getDelFlg())){
            return true;
        }
        return false;
    }
}
