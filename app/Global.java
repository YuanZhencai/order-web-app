import com.fasterxml.jackson.databind.JsonNode;
import common.Severity;
import common.exceptions.BusinessRuntimeException;
import common.util.MessageUtil;
import common.vo.Message;
import filters.BasicAuthenticationFilter;
import play.Application;
import play.GlobalSettings;
import play.Logger;
import play.api.mvc.EssentialFilter;
import play.libs.F;
import play.mvc.Http;
import play.mvc.Result;
import views.html.pageNotFound;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.MessageFormat;

import static play.mvc.Results.badRequest;
import static play.mvc.Results.notFound;

public class Global extends GlobalSettings {
    private static final String PATH_APP = "/.*";

    @Override
    public void onStart(Application application) {
        super.onStart(application);
    }

    @Override
    public F.Promise<Result> onError(Http.RequestHeader reqHeader, Throwable reqthrow) {
        String uri = reqHeader.uri();
        String path = reqHeader.path();
        String host = reqHeader.host();
        String method = reqHeader.method();
        String clientAddress = reqHeader.remoteAddress();

        String pattern = "onError {0} {1} {2} {3}\n{4}";
        Logger.error(MessageFormat.format(pattern, clientAddress, method, host, path, uri), reqthrow);

        if (path.matches(PATH_APP)) {
            // 将异常转成Json返回
            JsonNode json = null;
            String errorCode = null;
            String errorMessage = null;
            Severity severity = null;
            String detailMsg = null;

            Throwable cause = reqthrow.getCause();
            if (cause != null && cause instanceof BusinessRuntimeException) {
                errorMessage = cause.getMessage();
                detailMsg = ((BusinessRuntimeException) cause).getDetailMsg();
                Message message = ((BusinessRuntimeException) cause).getBusinessMessage();
                json = MessageUtil.getInstance().msgToJson(message);
            } else {
                errorCode = "fatal";
                errorMessage = cause.toString();

                StringWriter sw = new StringWriter();
                PrintWriter pw = new PrintWriter(sw);
                cause.printStackTrace(pw);
                pw.flush();
                sw.flush();
                if (sw.toString().length() >= 200) {
                    detailMsg = sw.toString().substring(0, 200);
                }
                try {
                    sw.close();
                    pw.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                cause.printStackTrace();
                json = MessageUtil.getInstance().msgToJson(new Message(Severity.FATAL, errorCode, "操作失败", "请重新操作。"));
            }

            Logger.error(errorMessage, detailMsg);

            Result result = badRequest(json);
            return F.Promise.pure(result);
        }
        return super.onError(reqHeader, reqthrow);
    }

    @Override
    public <T extends EssentialFilter> Class<T>[] filters() {
        Class[] filters = {BasicAuthenticationFilter.class};
        return filters;
    }

    public F.Promise<Result> onHandlerNotFound(Http.RequestHeader request) {
        return F.Promise.<Result>pure(notFound(pageNotFound.render()));
    }
}
