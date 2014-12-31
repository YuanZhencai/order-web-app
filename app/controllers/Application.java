package controllers;

import dao.UserInfoDao;
import dao.impl.UserInfoDaoImpl;
import models.UserInfos;
import play.data.Form;
import play.db.jpa.Transactional;
import play.mvc.Controller;
import play.mvc.Http;
import play.mvc.Result;
import views.html.index;
import views.html.login;
import views.html.order;
import vo.UserVo;
@Transactional
public class Application extends Controller {

    private static Form<UserVo> userForm = Form.form(UserVo.class);




    public static Result index() {
        return ok(index.render());
    }

    public static Result login() {
        UserVo userVo = new UserVo();
        Http.RequestBody body = request().body();
        if (body.asFormUrlEncoded() != null) {
            userVo = userForm.bindFromRequest().get();
        }
        UserInfos userInfo = new UserInfos();
        userInfo.setUserId(userVo.getUsername());
        userInfo.setPassword(userVo.getPassword());
        UserInfoDao dao = new UserInfoDaoImpl();
        if (dao.verify(userInfo)) {
            session().clear();
            session("user", userVo.getUsername());
            return redirect(routes.Application.index());
        } else {
            return ok(login.render("登录失败：用户名密码错误！！！"));
        }

    }

    public static Result logout() {
        session().clear();
        return ok(login.render(""));
    }

    public static Result order() {
        session("user", "sunlights");
        return ok(order.render(""));
    }
}
