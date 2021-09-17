package cn.itcast.zuul.filter;



import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

/**
 * 自定义的zuulFilter   过滤器
 * 继承抽象父类
 */
@Component
public class LoginFilter  extends ZuulFilter {

    /**
     * 定义过滤器类型
     * pre : 转发到微服务之前执行的过滤器
     * post : 执行微服务获取返回值之后执行的过滤器
     * routing : 在路由请求时执行的过滤器
     * error : 在整个阶段抛出异常的时候执行的过滤器
     *
     * @return
     */
    public String filterType() {
        return "pre";
    }

    /**
     * 定义过滤器执行顺序，数值越小，执行顺序越高
     */
    public int filterOrder() {
        return 0;
    }


    /**
     * 定义过滤器是否生效
     */
    public boolean shouldFilter() {
        return true;
    }

    /**
     * 执行过滤器的逻辑
     *
     * @return
     * @throws ZuulException 指定过滤器中的业务逻辑
     *                       身份认证:
     *                       1.所有的请求需要携带一个参数 : access-token
     *                       2.获取request请求
     *                       3.通过request获取参数access-token
     *                       4.判断token是否为空
     *                       4.1 token==null : 身份验证失败
     *                       4.2 token!=null : 执行后续操作
     *                       在zuul网关中,通过RequestContext的上下问对象,可以获取对象request对象
     */
    public Object run() throws ZuulException {
        System.out.println("***********************过滤器启动******************");
        {
            //1.获取zuul提供的上下文对象RequestContext
            RequestContext ctx = RequestContext.getCurrentContext();
            //2.从RequestContext中获取request
            HttpServletRequest request = ctx.getRequest();
            //3.获取请求参数access-token
            String token = request.getParameter("access-token");
            //4.判断
            if (token == null) {
                //4.1 如果token==null ,拦截请求,返回认证失败
                ctx.setSendZuulResponse(false); // 拦截请求
                ctx.setResponseStatusCode(HttpStatus.UNAUTHORIZED.value());
            }
            //4.2 如果token!=null ,继续后续操作
            return null;

        }

    }
}