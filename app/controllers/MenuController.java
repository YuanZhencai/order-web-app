package controllers;

import common.MsgCode;
import common.Severity;
import common.util.MessageUtil;
import common.vo.Message;
import common.vo.PageVo;
import play.db.jpa.Transactional;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Http;
import play.mvc.Result;
import service.MenuService;
import service.impl.MenuServiceImpl;
import vo.MenuInfoVo;

import java.util.List;

/**
 * Created by Yuan on 2015/2/10.
 */
@Transactional
public class MenuController extends Controller {

	private MessageUtil messageUtil = MessageUtil.getInstance();

	private MenuService menuService = new MenuServiceImpl();

	public Result findMenusBy() {
		Controller.response().setHeader("Access-Control-Allow-Origin", "*");
		PageVo pageVo = new PageVo();
		Http.RequestBody body = request().body();

		if (body.asJson() != null) {
			pageVo = Json.fromJson(body.asJson(), PageVo.class);
		}

		List<MenuInfoVo> menuInfoVos = menuService.findMenusBy(pageVo);
		pageVo.setList(menuInfoVos);
		messageUtil.setMessage(new Message(Severity.INFO, MsgCode.OPERATE_SUCCESS), pageVo);
		return ok(messageUtil.toJson());
	}
}
