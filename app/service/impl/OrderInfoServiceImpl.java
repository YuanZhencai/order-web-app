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
        return getExcelDemoVo("订单统计", "当日订餐清单", getTotalContainerFiledName(), filedContexts, getTotalFooterFiledName(), footerContexts);
    }

    /**
     * 获取每日订单明细（按用户排序）
     *
     * @param createTime
     * @return
     * @throws Exception
     */
    @Override
    public ExcelDemoVo queryDailyOrderDetail(String createTime) throws Exception {
        List<OrderInfo> list = dao.findAll(createTime);
        return getExcelDemoVo("订单统计", "当日订餐明细", getDetailContailnerFiledName(),
                getDetailContainerContext(list), null, null);
    }

    private List<List<String>> getDetailContainerContext(List<OrderInfo> list) {
        List<List<String>> fieldContexts = new ArrayList<>();
        int size = 0;
        OrderInfo temp = list.get(0);
        for (OrderInfo orderInfo : list) {
            size++;
            if (!temp.getUserName().equals(orderInfo.getUserName())) {
                setEmptyRow(fieldContexts);
                temp = orderInfo;
            }
            List<String> fieldContext = new ArrayList<>();
            fieldContext.add(orderInfo.getUserName());
            fieldContext.add(orderInfo.getFoodName());
            fieldContext.add(orderInfo.getSource());
            fieldContexts.add(fieldContext);
            if (size == list.size()) {
                setEmptyRow(fieldContexts);
            }
        }
        return fieldContexts;
    }

    private List<String> getDetailContailnerFiledName() {
        List<String> fieldName = new ArrayList<>();
        fieldName.add("用户名");
        fieldName.add("菜肴名称");
        fieldName.add("饭店名");
        return fieldName;
    }

    private List<OrderExportVo> getExportOrderVo(List<OrderInfo> list) throws Exception {
        int count = 0;
        int size = 0;
        List<OrderExportVo> listVo = new ArrayList<>();
        OrderInfo temp = list.get(0);
        for (OrderInfo orderInfo : list) {
            size++;
            if (temp.getSource().equals(orderInfo.getSource()) &&
                    temp.getFoodName().equals(orderInfo.getFoodName())) {
                count++;
            } else {
                totalCountAndTotalPrice(count, listVo, temp);
                temp = orderInfo;
                count = 1;
            }
            if (size == list.size()) {
                totalCountAndTotalPrice(count, listVo, temp);
            }
        }
        return listVo;
    }

    private void totalCountAndTotalPrice(int count, List<OrderExportVo> listVo, OrderInfo temp) throws Exception {
        OrderExportVo exportOrderVo = new OrderExportVo();
        BeanUtils.copyProperties(exportOrderVo, temp);
        exportOrderVo.setCount(new BigDecimal(count));
        exportOrderVo.setTotalPrice(new BigDecimal(count).multiply(temp.getPrice()));
        exportOrderVo.setRestaurantName(temp.getSource());
        listVo.add(exportOrderVo);
    }

    private void getFiledContexts(List<OrderExportVo> listVo, List<List<String>> filedContexts, List<List<String>> footerContexts) {
        int size = 0;
        OrderExportVo tempExport = listVo.get(0);
        BigDecimal totalCount = new BigDecimal(0);
        BigDecimal totalAmount = new BigDecimal(0);
        for (OrderExportVo exportOrder : listVo) {
            size++;
            String restaurantName = exportOrder.getRestaurantName();
            if (!restaurantName.equals(tempExport.getRestaurantName())) {
                setEmptyRow(filedContexts);
                setContainerContext(restaurantName, exportOrder, filedContexts);
                setFooterContext(footerContexts, tempExport, totalCount, totalAmount);

                totalCount = exportOrder.getCount();
                totalAmount = exportOrder.getTotalPrice();
            } else {
                setContainerContext(size != 1 ? "" : restaurantName, exportOrder, filedContexts);
                totalCount = totalCount.add(exportOrder.getCount());
                totalAmount = totalAmount.add(exportOrder.getTotalPrice());
            }
            tempExport = exportOrder;
            if (size == listVo.size()) {
                setEmptyRow(filedContexts);
                setFooterContext(footerContexts, tempExport, totalCount, totalAmount);
            }
        }
    }

    private void setContainerContext(String restaurantName, OrderExportVo exportOrder, List<List<String>> filedContexts) {
        List<String> filedContext = new ArrayList<>();
        filedContext.add(restaurantName);
        filedContext.add(exportOrder.getFoodName());
        filedContext.add(exportOrder.getCount().toPlainString());
        filedContext.add(exportOrder.getPrice().toPlainString());
        filedContext.add(exportOrder.getTotalPrice().toPlainString());
        filedContexts.add(filedContext);
    }

    private void setFooterContext(List<List<String>> footerContexts, OrderExportVo tempExport, BigDecimal totalCount, BigDecimal totalAmount) {
        List<String> filedContext;
        filedContext = new ArrayList<>();
        filedContext.add(tempExport.getRestaurantName());
        filedContext.add(totalCount.toPlainString());
        filedContext.add(totalAmount.toPlainString());
        footerContexts.add(filedContext);
    }

    private void setEmptyRow(List<List<String>> filedContexts) {
        List<String> filedContext;
        filedContext = new ArrayList<>();
        filedContext.add("");
        filedContexts.add(filedContext);
    }

    private ExcelDemoVo getExcelDemoVo(String sheetName, String title, List<String> cFiledName, List<List<String>> filedContexts, List<String> fFiledName, List<List<String>> footerContexts) {
        ExcelDemoVo excelDemoVo = new ExcelDemoVo();
        excelDemoVo.setHeader(getExcelHeader(sheetName, title));
        excelDemoVo.setContainer(getExcelContainer(cFiledName, filedContexts));
        excelDemoVo.setFooter(getExcelFooter(fFiledName, footerContexts));
        return excelDemoVo;
    }

    private ExcelHeader getExcelHeader(String sheetName, String title) {
        ExcelHeader header = new ExcelHeader();
        header.setSheetName(sheetName);
        header.setTitles(title);
        return header;
    }

    private ExcelContainer getExcelContainer(List<String> filedName, List<List<String>> filedContexts) {
        ExcelContainer container = new ExcelContainer();
        container.setFieldName(filedName);
        container.setFieldContext(filedContexts);
        return container;
    }

    private ExcelFooter getExcelFooter(List<String> filedName, List<List<String>> footerContexts) {
        ExcelFooter footer = new ExcelFooter();
        if(filedName != null) {
            footer.setFieldName(filedName);
            footer.setFieldContext(footerContexts);
        }
        return footer;
    }

    private List<String> getTotalContainerFiledName() {
        List<String> filedName = new ArrayList<>();
        filedName.add("饭店名");
        filedName.add("菜肴名称");
        filedName.add("数量");
        filedName.add("单价");
        filedName.add("小计");
        return filedName;
    }

    private List<String> getTotalFooterFiledName() {
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
