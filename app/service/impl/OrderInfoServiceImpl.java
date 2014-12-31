package service.impl;
import dao.OrderInfoDao;
import dao.impl.OrderInfoDaoImpl;
import models.OrderInfo;
import org.apache.commons.beanutils.BeanUtils;
import org.joda.time.DateTime;
import service.OrderInfoService;
import vo.OrderInfoVo;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by guxuelong on 2014/12/27.
 */
public class OrderInfoServiceImpl implements OrderInfoService {

    private  OrderInfoDao dao = new OrderInfoDaoImpl();

    /**
     * 查询所有的订单列表
     *
     * @return
     */
    @Override
    public List<OrderInfoVo> findAll() throws Exception {
        List<OrderInfo> list = dao.findAll();
        List<OrderInfoVo> voList = new ArrayList<>();
        for (OrderInfo orderInfos : list) {
            if("N".equals(orderInfos.getDelFlg())) {
                OrderInfoVo orderInfoVo = new OrderInfoVo();
                BeanUtils.copyProperties(orderInfoVo, orderInfos);
                voList.add(orderInfoVo);
            }
        }
        return voList;
    }

    /**
     * 添加新订单
     *
     * @param orderInfoVo
     * @return
     */
    @Override
    public Long add(OrderInfoVo orderInfoVo) throws Exception {
        OrderInfo orderInfo = new OrderInfo();
        BeanUtils.copyProperties(orderInfo,orderInfoVo);
        if(dao.isExist(orderInfo)){
            throw new Exception("订单已存在");
        }
        orderInfo.setDelFlg("N");
        orderInfo.setCreateBy("sunlights");
        orderInfo.setCreateTime(new DateTime().toDate());
        orderInfo.setUpdateBy("sunlights");
        orderInfo.setUpdateTime(new DateTime().toDate());
        Long id = dao.doInsert(orderInfo);
        return id;
    }

    /**
     * 更新订单信息
     *
     * @param orderInfoVo
     */
    @Override
    public void update(OrderInfoVo orderInfoVo) throws Exception {
        OrderInfo orderInfo = dao.findById(orderInfoVo.getId());
        orderInfo.setFoodName(orderInfoVo.getFoodName());
        orderInfo.setPrice(orderInfoVo.getPrice());
        orderInfo.setSource(orderInfoVo.getSource());
        orderInfo.setUpdateTime(new DateTime().toDate());
        dao.doUpdate(orderInfo);
    }

    /**
     * 删除订单信息
     *
     * @param orderInfoVo
     */
    @Override
    public void delete(OrderInfoVo orderInfoVo){

        OrderInfo orderInfo = dao.findById(orderInfoVo.getId());
        if ( orderInfo==null){
            return;
        }
        orderInfo.setDelFlg("Y");
        orderInfo.setUpdateTime(new Timestamp(System.currentTimeMillis()));
        dao.doDelete(orderInfo);
    }
}
