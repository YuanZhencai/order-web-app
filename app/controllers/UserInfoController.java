package controllers;

import common.vo.PageVo;
import play.Logger;
import play.db.jpa.Transactional;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;
import service.UserInfoService;
import service.impl.UserInfoServiceImpl;
import vo.UserInfoVo;

import java.util.List;

/**
 * Created by guxuelong on 2014/12/27.
 */
@Transactional
public class UserInfoController extends Controller {

    private static final String SYSTEM_ERROR = "系统异常，请联系后台服务维护人员！";
    private  UserInfoService service = new UserInfoServiceImpl();


    /**
     * 查询用户信息列表
     *
     * @return
     */
    public Result findAll(){
        try{
            List<UserInfoVo> list =  service.findAll();
            PageVo pageVo = new PageVo();
            pageVo.setList(list);
            return ok(Json.toJson(pageVo));
        }catch(Exception e){
            Logger.error("用户信息查询失败；错误信息：" + e.getMessage());
            return Controller.badRequest(SYSTEM_ERROR);
        }

    }

    /**
     * 添加新用户
     *
     * @return
     */
    public Result add(){
        try{
            UserInfoVo userInfoVo = Json.fromJson(request().body().asJson(), UserInfoVo.class);
            Long id = service.add(userInfoVo);
            return ok(Json.toJson(id));
        }catch(Exception e){
            Logger.error("用户信息查询失败；错误信息：" + e.getMessage());
            return Controller.badRequest(e.getMessage());
        }
    }


    /**
     * 更新用户信息
     *
     * @return
     */
    public Result update(){
        try{
            UserInfoVo userInfoVo = Json.fromJson(request().body().asJson(), UserInfoVo.class);
            service.update(userInfoVo);
            return ok();
        }catch(Exception e){
            Logger.error("用户信息查询失败；错误信息：" + e.getMessage());
            return Controller.badRequest(e.getMessage());
        }
    }

    /**
     * 删除用户信息（逻辑删除）
     *
     * @return
     */
    public Result delete(){
        try{
            UserInfoVo userInfoVo = Json.fromJson(request().body().asJson(), UserInfoVo.class);
            service.delete(userInfoVo);
            return ok();
        }catch(Exception e){
            Logger.error("用户信息查询失败；错误信息：" + e.getMessage());
            return Controller.badRequest(SYSTEM_ERROR);
        }
    }
}
