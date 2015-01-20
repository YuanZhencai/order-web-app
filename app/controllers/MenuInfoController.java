package controllers;

import common.vo.PageVo;
import play.Logger;
import play.db.jpa.Transactional;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;
import service.MenuInfoService;
import service.impl.MenuInfoServiceImpl;
import vo.MenuInfoVo;

import java.util.List;

/**
 * Created by guxuelong on 2014/12/27.
 */
@Transactional
public class MenuInfoController extends Controller {

    private static final String SYSTEM_ERROR = "系统异常，请联系后台服务维护人员！";
    private MenuInfoService service = new MenuInfoServiceImpl();


    /**
     * 查询菜单信息列表
     *
     * @return
     */
    public Result findAll(){
		Controller.response().setHeader("Access-Control-Allow-Origin","*");
        try{
            List<MenuInfoVo> list =  service.findAll();
            PageVo pageVo = new PageVo();
            pageVo.setList(list);
            return ok(Json.toJson(pageVo));
        }catch(Exception e){
            Logger.error("菜单信息查询失败；错误信息：" + e.getMessage());
            return Controller.badRequest(SYSTEM_ERROR);
        }

    }

    /**
     * 添加新菜单
     *
     * @return
     */
    public Result add(){
        try{
            MenuInfoVo menuInfoVo = Json.fromJson(request().body().asJson(), MenuInfoVo.class);
            Long id = service.add(menuInfoVo);
            return ok(Json.toJson(id));
        }catch(Exception e){
            Logger.error("菜单信息添加失败；错误信息：" + e.getMessage());
            return Controller.badRequest(e.getMessage());
        }
    }


    /**
     * 更新菜单信息
     *
     * @return
     */
    public Result update(){
        try{
            MenuInfoVo menuInfoVo = Json.fromJson(request().body().asJson(), MenuInfoVo.class);
            service.update(menuInfoVo);
            return ok();
        }catch(Exception e){
            Logger.error("菜单信息更新失败；错误信息：" + e.getMessage());
            return Controller.badRequest(e.getMessage());
        }
    }

    /**
     * 删除菜单信息（逻辑删除）
     *
     * @return
     */
    public Result delete(){
        try{
            MenuInfoVo menuInfoVo = Json.fromJson(request().body().asJson(), MenuInfoVo.class);
            service.delete(menuInfoVo);
            return ok();
        }catch(Exception e){
            Logger.error("菜单信息删除失败；错误信息：" + e.getMessage());
            return Controller.badRequest(SYSTEM_ERROR);
        }
    }
}
