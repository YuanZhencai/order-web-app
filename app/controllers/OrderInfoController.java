package controllers;

import common.util.CommonUtil;
import common.util.ExcelUtil;
import common.vo.*;
import org.apache.commons.beanutils.BeanUtils;
import play.Logger;
import play.db.jpa.Transactional;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;
import service.OrderInfoService;
import service.impl.OrderInfoServiceImpl;
import vo.OrderInfoVo;

import javax.swing.*;
import java.io.File;
import java.util.*;

/**
 * Created by guxuelong on 2014/12/27.
 */
@Transactional
public class OrderInfoController extends Controller {

    private static final String SYSTEM_ERROR = "系统异常，请联系后台服务维护人员！";
    private OrderInfoService service = new OrderInfoServiceImpl();


    /**
     * 查询订单信息列表
     *
     * @return
     */
    public Result findAll() {
        try {
            PageVo pageVo = Json.fromJson(request().body().asJson(), PageVo.class);
            List<OrderInfoVo> list = service.findAll(getDateString(pageVo));
            PageVo vo = new PageVo();
            vo.setList(list);
            return ok(Json.toJson(vo));
        } catch (Exception e) {
            Logger.error("订单信息查询失败；错误信息：" + e.getMessage());
            return Controller.badRequest(SYSTEM_ERROR);
        }

    }

    private String getDateString(PageVo pageVo) throws Exception {
        String chkDate = ((String) pageVo.getFilter().get("createTime")).substring(0, 10);
        return getDate(chkDate);
    }

    private String getDate(String chkDate) {
        Date date = CommonUtil.stringToDate(chkDate);
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.DATE, 1);
        chkDate = CommonUtil.dateToString(cal.getTime());
        return chkDate;
    }

    /**
     * 添加新订单
     *
     * @return
     */
    public Result add() {
        try {
            PageVo pageVo = Json.fromJson(request().body().asJson(), PageVo.class);
            List<Map> orderInfoVoList = pageVo.getList();
            for (Map map : orderInfoVoList) {
                OrderInfoVo orderInfoVo = new OrderInfoVo();
                BeanUtils.copyProperties(orderInfoVo, map);
                orderInfoVo.setId(null);
                orderInfoVo.setUserId(session().get("user"));
                orderInfoVo.setUserName(session().get("userName"));
                service.add(orderInfoVo);
            }
            return ok();
        } catch (Exception e) {
            Logger.error("订单信息添加失败；错误信息：" + e.getMessage());
            return Controller.badRequest(e.getMessage());
        }
    }


    /**
     * 更新订单信息
     *
     * @return
     */
    public Result update() {
        try {
            OrderInfoVo orderInfoVo = Json.fromJson(request().body().asJson(), OrderInfoVo.class);
            service.update(orderInfoVo);
            return ok();
        } catch (Exception e) {
            Logger.error("订单信息更新失败；错误信息：" + e.getMessage());
            return Controller.badRequest(e.getMessage());
        }
    }

    /**
     * 删除订单信息（逻辑删除）
     *
     * @return
     */
    public Result delete() {
        try {
            OrderInfoVo orderInfoVo = Json.fromJson(request().body().asJson(), OrderInfoVo.class);
            service.delete(orderInfoVo);
            return ok();
        } catch (Exception e) {
            Logger.error("订单信息删除失败；错误信息：" + e.getMessage());
            return Controller.badRequest(SYSTEM_ERROR);
        }
    }

    /**
     * 导出
     */
    public Result exportDailyOrderList() {
        Logger.info("--------------export SettleAccounts start--------------");
        try {
            String fileName = "当日订餐清单.xls";
            JFileChooser fd = new JFileChooser();
            fd.setSelectedFile(new File(fileName));
            int i = fd.showSaveDialog(null);
            if (i == 1) {
                Logger.info("--------------export SettleAccounts cancel--------------");
                return ok();
            }
            String path = fd.getCurrentDirectory().getAbsolutePath();
            PageVo pageVo = Json.fromJson(request().body().asJson(), PageVo.class);

            ExcelDemoVo excelDemoVo = service.queryDailyOrderList(getChkDateString(pageVo));

//            List<String> filedName = new ArrayList<>();
//            List<List<String>> filedContexts = new ArrayList<>();
//            List<String> filedContext = new ArrayList<>();
//            ExcelDemoVo excelDemoVo = new ExcelDemoVo();
//            ExcelHeader header = new ExcelHeader();
//            header.setSheetName("订单统计");
//            header.setTitles("当日订餐清单");
//            ExcelContainer container = new ExcelContainer();
//            filedName.add("饭店名");
//            filedName.add("菜肴名称");
//            filedName.add("数量");
//            filedName.add("单价");
//            filedName.add("小计");
//
//            filedContext.add("金华快餐");
//            filedContext.add("鱼香茄子");
//            filedContext.add("1");
//            filedContext.add("10");
//            filedContext.add("10");
//            filedContexts.add(filedContext);
//
//            filedContext = new ArrayList<>();
//            filedContext.add("");
//            filedContext.add("红烧大排");
//            filedContext.add("1");
//            filedContext.add("10");
//            filedContext.add("10");
//            filedContexts.add(filedContext);
//
//            filedContext = new ArrayList<>();
//            filedContext.add("");
//            filedContext.add("");
//            filedContext.add("");
//            filedContext.add("");
//            filedContext.add("");
//            filedContexts.add(filedContext);
//
//
//            filedContext = new ArrayList<>();
//            filedContext.add("天天快餐");
//            filedContext.add("鱼香茄子");
//            filedContext.add("1");
//            filedContext.add("10");
//            filedContext.add("10");
//            filedContexts.add(filedContext);
//
//            filedContext = new ArrayList<>();
//            filedContext.add("");
//            filedContext.add("红烧大排");
//            filedContext.add("1");
//            filedContext.add("10");
//            filedContext.add("10");
//            filedContexts.add(filedContext);
//
//            filedContext = new ArrayList<>();
//            filedContext.add("");
//            filedContext.add("");
//            filedContext.add("");
//            filedContext.add("");
//            filedContext.add("");
//            filedContexts.add(filedContext);
//
//            container.setFieldName(filedName);
//            container.setFieldContext(filedContexts);
//
//
//            ExcelFooter footer = new ExcelFooter();
//            filedName = new ArrayList<>();
//            filedName.add("总计");
//            filedName.add("数量");
//            filedName.add("总金额");
//            filedContexts = new ArrayList<>();
//            filedContext = new ArrayList<>();
//            filedContext.add("金华快餐");
//            filedContext.add("2");
//            filedContext.add("46");
//            filedContexts.add(filedContext);
//
//            filedContext = new ArrayList<>();
//            filedContext.add("天天快餐");
//            filedContext.add("2");
//            filedContext.add("46");
//            filedContexts.add(filedContext);
//
//            footer.setFieldName(filedName);
//            footer.setFieldContext(filedContexts);
//
//
//            excelDemoVo.setHeader(header);
//            excelDemoVo.setContainer(container);
//            excelDemoVo.setFooter(footer);
            excelDemoVo.setFileName(path + "\\" + fileName);
            ExcelUtil.exportExcel(excelDemoVo);
            Logger.info("--------------export SettleAccounts end--------------");
            return ok();
        } catch (Exception e) {
            Logger.error("订单信息查询失败；错误信息：" + e.getMessage());
            return Controller.badRequest(SYSTEM_ERROR);
        }
    }
    private String getChkDateString(PageVo pageVo) throws Exception {
        String chkDate = ((String) pageVo.getFilter().get("createTime")).substring(0, 10);
        Date date = CommonUtil.stringToDate(chkDate);
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.DATE, 1);
        chkDate = CommonUtil.dateToString(cal.getTime());
        return chkDate;
    }
}
