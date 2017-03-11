package cn.itcast.core.web.springmvc;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import cn.itcast.common.web.session.SessionProvider;
import cn.itcast.core.web.Constants;

/**
 * 前台拦截器
 * 
 * @author Administrator
 *
 */
public class SpringmvcInterceptor implements HandlerInterceptor {

	private static final String URI_INTERCEPTOR = "/buyer";
	@Autowired
	private SessionProvider sessionProvider;

	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		// 请求执行前判断，进入handler之前
		String username = sessionProvider.getAttribute(request, response, Constants.USER_SESSION);
		// 判断
		String requestURI = request.getRequestURI();
		if (requestURI.startsWith(URI_INTERCEPTOR)) {
			//是buyer路径请求开头的就拦截必须要登陆
			
			if (username != null) {
				//证明已经登陆了
				request.setAttribute("isLogin", true);
				
			}else {
				request.setAttribute("isLogin",false);
				//重定向到登陆页面
				response.sendRedirect("/shopping/login.shtml?returnUrl=" + 
							request.getParameter("returnUrl"));
				//拦截
				return false;
			}
		}else {
			//不是buyer路径开头的就可登录可不登陆
			//可登可不登陆
			if(null != username){
				//没有Model  但有Request
				//传递页面一个标记 （是否登陆）
				request.setAttribute("isLogin", true);
			}else{
				//传递页面一个标记 （是否登陆）
				request.setAttribute("isLogin", false);
			}
		}
		return true;
	}

	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {

	}

	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {

	}

}
