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
            PageVo pageVo = Json.fromJson(request().body().asJson(), PageVo.class);
            String createTime = getChkDateString(pageVo);
            String fileName = "订餐清单"+ createTime +".xls";
            JFileChooser fd = new JFileChooser();
            fd.setSelectedFile(new File(fileName));
            int i = fd.showSaveDialog(null);
            if (i == 1) {
                Logger.info("--------------export SettleAccounts cancel--------------");
                return ok();
            }
            String path = fd.getCurrentDirectory().getAbsolutePath();
            ExcelDemoVo excelDemoVo = service.queryDailyOrderList(createTime);
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
