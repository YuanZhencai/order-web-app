package service.impl;

import common.vo.ExcelContainer;
import common.vo.ExcelDemoVo;
import common.vo.ExcelFooter;
import common.vo.ExcelHeader;
import dao.OrderInfoDao;
import dao.impl.OrderInfoDaoImpl;
import models.OrderInfo;
import org.apache.commons.beanutils.BeanUtils;
import org.joda.time.DateTime;
import service.OrderInfoService;
import vo.OrderExportVo;
import vo.OrderInfoVo;

import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by guxuelong on 2014/12/27.
 */
public class OrderInfoServiceImpl implements OrderInfoService {

    private OrderInfoDao dao = new OrderInfoDaoImpl();

    /**
     * 查询所有的订单列表
     *
     * @return
     */
    @Override
    public List<OrderInfoVo> findAll(String createTime) throws Exception {
        List<OrderInfo> list = dao.findAll(createTime);
        List<OrderInfoVo> voList = new ArrayList<>();
        for (OrderInfo orderInfos : list) {
            OrderInfoVo orderInfoVo = new OrderInfoVo();
            BeanUtils.copyProperties(orderInfoVo, orderInfos);
            voList.add(orderInfoVo);
        }
        return voList;
    }

    /**
     * 获取当日订餐清单
     *
     * @param createTime
     * @return
     */
    @Override
    public ExcelDemoVo queryDailyOrderList(String createTime) throws Exception {
        // query orderList by restaurantName & foodName
        List<OrderInfo> list = dao.findOrderByUserName(createTime);
        // total the total price and the total quantity of a single dish meal in the ordering list
        List<OrderExportVo> listVo = getExportOrderVo(list);
        // set the filedContexts of container and footer
        List<List<String>> filedContexts = new ArrayList<>();
        List<List<String>> footerContexts = new ArrayList<>();
        getFiledContexts(listVo, filedContexts, footerContexts);
        return getExcelDemoVo(filedContexts, footerContexts);
    }

    private List<OrderExportVo> getExportOrderVo(List<OrderInfo> list) throws IllegalAccessException, InvocationTargetException {
        int count = 0;
        int size = 0;
        List<OrderExportVo> listVo = new ArrayList<>();
        OrderExportVo exportOrderVo;
        OrderInfo temp = list.get(0);
        for (OrderInfo orderInfo : list) {
            size++;
            if (temp.getSource().equals(orderInfo.getSource())) {
                if (temp.getFoodName().equals(orderInfo.getFoodName())) {
                    count++;
                } else {
                    exportOrderVo = new OrderExportVo();
                    BeanUtils.copyProperties(exportOrderVo, temp);
                    exportOrderVo.setCount(new BigDecimal(count));
                    exportOrderVo.setTotalPrice(new BigDecimal(count).multiply(temp.getPrice()));
                    exportOrderVo.setRestaurantName(temp.getSource());
                    listVo.add(exportOrderVo);
                    temp = orderInfo;
                    count = 1;
                }
            } else {
                exportOrderVo = new OrderExportVo();
                BeanUtils.copyProperties(exportOrderVo, temp);
                exportOrderVo.setCount(new BigDecimal(count));
                exportOrderVo.setTotalPrice(new BigDecimal(count).multiply(temp.getPrice()));
                exportOrderVo.setRestaurantName(temp.getSource());
                listVo.add(exportOrderVo);
                temp = orderInfo;
                count = 1;
            }

            if (size == list.size()) {
                exportOrderVo = new OrderExportVo();
                BeanUtils.copyProperties(exportOrderVo, temp);
                exportOrderVo.setCount(new BigDecimal(count));
                exportOrderVo.setTotalPrice(new BigDecimal(count).multiply(temp.getPrice()));
                exportOrderVo.setRestaurantName(temp.getSource());
                listVo.add(exportOrderVo);
            }

        }
        return listVo;
    }

    private void getFiledContexts(List<OrderExportVo> listVo, List<List<String>> filedContexts, List<List<String>> footerContexts) {
        int size = 0;
        List<String> filedContext;
        OrderExportVo tempExport = listVo.get(0);
        BigDecimal totalCount = new BigDecimal(0);
        BigDecimal totalAmount = new BigDecimal(0);
        for (OrderExportVo exportOrder : listVo) {
            size++;
            if (!exportOrder.getRestaurantName().equals(tempExport.getRestaurantName())) {
                filedContext = new ArrayList<>();
                filedContext.add("");
                filedContext.add("");
                filedContext.add("");
                filedContext.add("");
                filedContext.add("");
                filedContexts.add(filedContext);

                filedContext = new ArrayList<>();
                filedContext.add(exportOrder.getRestaurantName());
                filedContext.add(exportOrder.getFoodName());
                filedContext.add(exportOrder.getCount().toPlainString());
                filedContext.add(exportOrder.getPrice().toPlainString());
                filedContext.add(exportOrder.getTotalPrice().toPlainString());
                filedContexts.add(filedContext);


                filedContext = new ArrayList<>();
                filedContext.add(tempExport.getRestaurantName());
                filedContext.add(totalCount.toPlainString());
                filedContext.add(totalAmount.toPlainString());
                footerContexts.add(filedContext);
                totalCount = exportOrder.getCount();
                totalAmount = exportOrder.getTotalPrice();
            } else {
                totalCount = totalCount.add(exportOrder.getCount());
                totalAmount = totalAmount.add(exportOrder.getTotalPrice());
                filedContext = new ArrayList<>();
                if(size == 1){
                    filedContext.add(exportOrder.getRestaurantName());
                }else{
                    filedContext.add("");
                }
                filedContext.add(exportOrder.getFoodName());
                filedContext.add(exportOrder.getCount().toPlainString());
                filedContext.add(exportOrder.getPrice().toPlainString());
                filedContext.add(exportOrder.getTotalPrice().toPlainString());
                filedContexts.add(filedContext);
            }
            tempExport = exportOrder;
            if (size == listVo.size()) {
                filedContext = new ArrayList<>();
                filedContext.add("");
                filedContext.add("");
                filedContext.add("");
                filedContext.add("");
                filedContext.add("");
                filedContexts.add(filedContext);

                filedContext = new ArrayList<>();
                filedContext.add(tempExport.getRestaurantName());
                filedContext.add(totalCount.toPlainString());
                filedContext.add(totalAmount.toPlainString());
                footerContexts.add(filedContext);
            }

        }
    }

    private ExcelDemoVo getExcelDemoVo(List<List<String>> filedContexts, List<List<String>> footerContexts) {
        ExcelDemoVo excelDemoVo = new ExcelDemoVo();
        excelDemoVo.setHeader(getExcelHeader());
        excelDemoVo.setContainer(getExcelContainer(filedContexts));
        excelDemoVo.setFooter(getExcelFooter(footerContexts));
        return excelDemoVo;
    }

    private ExcelHeader getExcelHeader() {
        ExcelHeader header = new ExcelHeader();
        header.setSheetName("订单统计");
        header.setTitles("当日订餐清单");
        return header;
    }

    private ExcelContainer getExcelContainer(List<List<String>> filedContexts) {
        ExcelContainer container = new ExcelContainer();
        container.setFieldName(setContainerFiledName());
        container.setFieldContext(filedContexts);
        return container;
    }

    private ExcelFooter getExcelFooter(List<List<String>> footerContexts) {
        ExcelFooter footer = new ExcelFooter();
        footer.setFieldName(setFooterFiledName());
        footer.setFieldContext(footerContexts);
        return footer;
    }

    private List<String> setContainerFiledName() {
        List<String> filedName = new ArrayList<>();
        filedName.add("饭店名");
        filedName.add("菜肴名称");
        filedName.add("数量");
        filedName.add("单价");
        filedName.add("小计");
        return filedName;
    }

    private List<String> setFooterFiledName() {
        List<String> filedName = new ArrayList<>();
        filedName.add("总计");
        filedName.add("数量");
        filedName.add("总金额");
        return filedName;
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
        BeanUtils.copyProperties(orderInfo, orderInfoVo);
        orderInfo.setCreateTime(new DateTime().toDate());
        orderInfo.setDelFlg("N");
        orderInfo.setCreateBy("sunlights");
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
    public void delete(OrderInfoVo orderInfoVo) {

        OrderInfo orderInfo = dao.findById(orderInfoVo.getId());
        if (orderInfo == null) {
            return;
        }
        orderInfo.setDelFlg("Y");
        orderInfo.setUpdateTime(new Timestamp(System.currentTimeMillis()));
        dao.doDelete(orderInfo);
    }


}
