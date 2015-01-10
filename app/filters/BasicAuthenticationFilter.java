package filters;

import play.api.libs.iteratee.Done;
import play.api.libs.iteratee.Iteratee;
import play.api.mvc.EssentialAction;
import play.api.mvc.EssentialFilter;
import play.api.mvc.RequestHeader;
import play.api.mvc.Result;
import play.data.Form;
import scala.Option;
import views.html.login;
import views.html.orderLogin;
import vo.OrderUserVo;

import static play.mvc.Results.ok;


/**
 * Created by loki on 12/9/14.
 */
public class BasicAuthenticationFilter implements EssentialFilter {

    private static Form<OrderUserVo> orderUserForm = Form.form(OrderUserVo.class);
    public BasicAuthenticationFilter() {
        // Left empty
    }

    @Override
    public EssentialAction apply(final EssentialAction next) {
        return new JavaEssentialAction() {
            @Override
            public EssentialAction apply() {
                return next.apply();
            }

            @Override
            public Iteratee<byte[], Result> apply(RequestHeader rh) {
                String uri = rh.uri();
                if ("/login".equals(uri) || "/addUserInfo".equals(uri) || "/order".equals(uri) || "/findFood".equals(uri) || uri.contains("orderLogin") || uri.contains("assets") || uri.contains("webjars")) {
                    return next.apply(rh);
                }
                Option<String> user = rh.session().get("user");
                //TODO add shiro
                if (user.isEmpty()) {
                    if("/order".equals(uri)) {
                        return Done.apply(ok(orderLogin.render("")).toScala(), null);
                    }
                    return Done.apply(ok(login.render("")).toScala(), null);
                } else {
                    return next.apply(rh);
                }

            }
        };
    }

}
