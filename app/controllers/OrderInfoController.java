package controllers;

import common.vo.PageVo;
import play.Logger;
import play.db.jpa.Transactional;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;
import service.OrderInfoService;
import service.impl.OrderInfoServiceImpl;
import vo.OrderInfoVo;

import java.util.List;

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
    public Result findAll(){
        try{
            List<OrderInfoVo> list =  service.findAll();
            PageVo pageVo = new PageVo();
            pageVo.setList(list);
            return ok(Json.toJson(pageVo));
        }catch(Exception e){
            Logger.error("订单信息查询失败；错误信息：" + e.getMessage());
            return Controller.badRequest(SYSTEM_ERROR);
        }

    }

    /**
     * 添加新订单
     *
     * @return
     */
    public Result add(){
        try{
            OrderInfoVo orderInfoVo = Json.fromJson(request().body().asJson(), OrderInfoVo.class);
            Long id = service.add(orderInfoVo);
            return ok(Json.toJson(id));
        }catch(Exception e){
            Logger.error("订单信息添加失败；错误信息：" + e.getMessage());
            return Controller.badRequest(e.getMessage());
        }
    }


    /**
     * 更新订单信息
     *
     * @return
     */
    public Result update(){
        try{
            OrderInfoVo orderInfoVo = Json.fromJson(request().body().asJson(), OrderInfoVo.class);
            service.update(orderInfoVo);
            return ok();
        }catch(Exception e){
            Logger.error("订单信息更新失败；错误信息：" + e.getMessage());
            return Controller.badRequest(e.getMessage());
        }
    }

    /**
     * 删除订单信息（逻辑删除）
     *
     * @return
     */
    public Result delete(){
        try{
            OrderInfoVo orderInfoVo = Json.fromJson(request().body().asJson(), OrderInfoVo.class);
            service.delete(orderInfoVo);
            return ok();
        }catch(Exception e){
            Logger.error("订单信息删除失败；错误信息：" + e.getMessage());
            return Controller.badRequest(SYSTEM_ERROR);
        }
    }
}
