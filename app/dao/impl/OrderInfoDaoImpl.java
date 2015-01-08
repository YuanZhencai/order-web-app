package dao.impl;

import common.util.CommonUtil;
import dao.EntityBaseDao;
import dao.OrderInfoDao;
import models.OrderInfo;
import org.joda.time.DateTime;

import javax.persistence.Query;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

/**
 * Created by guxuelong on 2014/12/26.
 */
public class OrderInfoDaoImpl extends EntityBaseDao implements OrderInfoDao {
    private static final String QUERY_ORDER_ALL = "select t from OrderInfo t where t.createTime >= :timeStart  and t.createTime <= :timeEnd and t.delFlg = 'N'";
    private static final String QUERY_ORDER_BY_USER_NM = "select t from OrderInfo t where t.createTime >= :timeStart  and t.createTime <= :timeEnd and t.delFlg = 'N' order by source,foodName";
    private static final String QUERY_ORDER = "select t from OrderInfo t where t.createTime >= :timeStart  and t.createTime <= :timeEnd and t.userName = :userName and t.delFlg = 'N'";
    public static final String START_TIME = " 00:00:00";
    public static final String END_TIME = " 23:59:59";

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
    public List<OrderInfo> findAll(String createTime){
        Query query = super.createQuery(QUERY_ORDER_ALL);
        setTimePara(createTime,query);
        List<OrderInfo> result = query.getResultList();
        return result;
    }

    @Override
    public List<OrderInfo> findOrderByUserName(String createTime){
        Query query = super.createQuery(QUERY_ORDER_BY_USER_NM);
        setTimePara(createTime,query);
        List<OrderInfo> result = query.getResultList();
        return result;
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
    public  boolean isExist( OrderInfo orderInfo ,boolean updFlag) {
        Query query = super.createQuery(QUERY_ORDER);
        query.setParameter("userName", orderInfo.getUserName());
        setTimePara(new DateTime(orderInfo.getCreateTime()).toString(CommonUtil.DATE_FORMAT_SHORT),query);
        List<OrderInfo> result = query.getResultList();
        if(result != null && !result.isEmpty()){
            if(updFlag){
                result.get(0).setDelFlg("Y");
                doUpdate(result.get(0));
            }
            return true;
        }
        return false;
    }

   private void setTimePara(String time, Query query) {
        query.setParameter("timeStart", getDate(time, START_TIME));
        query.setParameter("timeEnd", getDate(time, END_TIME));
    }

    private Date getDate(String orgTime, String time) {
            return CommonUtil.stringToDate(orgTime + time, CommonUtil.DATE_FORMAT_LONG);

    }
}
