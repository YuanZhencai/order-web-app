package service;

import common.vo.ExcelDemoVo;
import vo.OrderInfoVo;

import java.util.List;

/**
 * Created by guxuelong on 2014/12/27.
 */
public interface OrderInfoService {

    /**
     * 查询所有的订单列表
     *
     * @return
     */
    public List<OrderInfoVo> findAll(String createTime) throws Exception;

    /**
     * 获取当日订餐清单
     *
     * @return
     */
    public ExcelDemoVo queryDailyOrderList(String createTime) throws Exception;

    /**
     * 获取每日订单明细（按用户排序）
     *
     * @param createTime
     * @return
     * @throws Exception
     */
    public ExcelDemoVo queryDailyOrderDetail(String createTime)throws Exception;

    /**
     * 添加新订单
     *
     * @return
     */
    public Long add(OrderInfoVo orderInfoVo) throws Exception;

    /**
     * 更新订单信息
     *
     * @param orderInfoVo
     */
    public void update(OrderInfoVo orderInfoVo) throws Exception;

    /**
     * 删除订单信息
     *
     * @param orderInfoVo
     */
    public void delete(OrderInfoVo orderInfoVo);
}
