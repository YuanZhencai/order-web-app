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
import views.html.orderLogin;
import vo.UserVo;
@Transactional
public class Application extends Controller {

    private static Form<UserVo> userForm = Form.form(UserVo.class);

    public static Result index() {
        return ok(index.render());
    }

    public static Result login() {
        UserInfos userInfos = getUserInfos();
        if (userInfos != null && "sunlights035".equals(userInfos.getUserId())) {
            session().clear();
            session("user", userInfos.getUserId());
            session("userName", userInfos.getUserName());
            return redirect(routes.Application.index());
        } else {
            return ok(login.render("登录失败：用户名密码错误！！！"));
        }

    }

    public static Result orderLogin() {
        UserInfos userInfos = getUserInfos();
        if (userInfos != null) {
            session().clear();
            session("user", userInfos.getUserId());
            session("userName", userInfos.getUserName());
            return ok(order.render("",userInfos.getUserName()));
        } else {
            return ok(orderLogin.render("登录失败：用户名密码错误！！！"));
        }

    }

    private static UserInfos getUserInfos() {
        UserVo userVo = new UserVo();
        Http.RequestBody body = request().body();
        if (body.asFormUrlEncoded() != null) {
            userVo = userForm.bindFromRequest().get();
        }
        UserInfos userInfo = new UserInfos();
        userInfo.setUserId(userVo.getUsername());
        userInfo.setPassword(userVo.getPassword());
        UserInfoDao dao = new UserInfoDaoImpl();
        UserInfos userInfos = dao.verify(userInfo);
        return userInfos;
    }

    public static Result logout() {
        session().clear();
        return ok(login.render(""));
    }

    public static Result order() {
        return ok(order.render("",session().get("userName")));
    }


}
