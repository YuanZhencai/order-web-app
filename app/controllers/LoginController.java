package controllers;

import common.MsgCode;
import common.Severity;
import common.util.MessageUtil;
import common.vo.Message;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Http;
import play.mvc.Result;
import vo.UserVo;

/**
 * Created by Yuan on 2015/2/11.
 */
public class LoginController extends Controller {

	private MessageUtil messageUtil = MessageUtil.getInstance();

	public Result login() {
		UserVo userVo = new UserVo();
		Http.RequestBody body = request().body();
		if (body.asJson() != null) {
			userVo = Json.fromJson(body.asJson(), UserVo.class);
		}
		if ("admin".equals(userVo.getUsername()) && "admin".equals(userVo.getPassword())) {
			userVo.setUsername("admin");
			userVo.setPassword("admin");
			messageUtil.setMessage(new Message(Severity.INFO, MsgCode.LOGIN_SUCCESS), userVo);
		} else {
			messageUtil.setMessage(new Message(Severity.INFO, MsgCode.LOGIN_ERROR));
		}
		return ok(messageUtil.toJson());
	}
}
