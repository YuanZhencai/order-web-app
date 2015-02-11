package service.impl;

import common.dao.EntityBaseDao;
import models.OrderInfo;
import service.OrderService;
import vo.OrderInfoVo;

import java.util.Date;

/**
 * Created by Administrator on 2015/2/11.
 */
public class OrderServiceImpl extends EntityBaseDao implements OrderService {
	@Override
	public void create (OrderInfoVo orderInfoVo) {
		OrderInfo orderInfo = orderInfoVo.convertOrderInfo();
		orderInfo.setCreateTime(new Date());
		create(orderInfo);
	}
}
