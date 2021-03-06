//package com.components.common.util;
//
//import java.io.IOException;
//import java.math.BigDecimal;
//
//import javax.servlet.Filter;
//import javax.servlet.FilterChain;
//import javax.servlet.FilterConfig;
//import javax.servlet.ServletException;
//import javax.servlet.ServletRequest;
//import javax.servlet.ServletResponse;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//
//import org.apache.commons.logging.Log;
//import org.apache.commons.logging.LogFactory;
//
//import com.components.common.dao.Dao;
//import com.components.common.metatype.Dto;
//import com.components.common.metatype.impl.BaseDto;
//import com.components.common.properties.PropertiesFactory;
//import com.components.common.properties.PropertiesFile;
//import com.components.common.properties.PropertiesHelper;
//import com.components.system.UserInfoVo;
//import com.tgyt.common.id.util.IDHelper;
//
///**
// * 请求拦截过滤�? * 
// * @author XiongChun
// * @since 2010-04-13
// */
//public class RequestFilter implements Filter {
//
//	private Log log = LogFactory.getLog(RequestFilter.class);
//	protected FilterConfig filterConfig;
//	protected boolean enabled;
//
//	/**
//	 * 构�?
//	 */
//	public RequestFilter() {
//		filterConfig = null;
//		enabled = true;
//	}
//
//	/**
//	 * 初始�?	 */
//	public void init(FilterConfig pFilterConfig) throws ServletException {
//		this.filterConfig = pFilterConfig;
//		String value = filterConfig.getInitParameter("enabled");
//		if (G4Utils.isEmpty(value)) {
//			this.enabled = true;
//		} else if (value.equalsIgnoreCase("true")) {
//			this.enabled = true;
//		} else {
//			this.enabled = false;
//		}
//	}
//
//	/**
//	 * 过滤处理
//	 */
//	public void doFilter(ServletRequest pRequest, ServletResponse pResponse, FilterChain fc) throws IOException,
//			ServletException {
//		HttpServletRequest request = (HttpServletRequest) pRequest;
//		HttpServletResponse response = (HttpServletResponse) pResponse;
//		String ctxPath = request.getContextPath();
//		String requestUri = request.getRequestURI();
//		String uri = requestUri.substring(ctxPath.length());
//		UserInfoVo userInfo = WebUtils.getSessionContainer(request).getUserInfo();
//		BigDecimal costTime = null;
//		PropertiesHelper pHelper = PropertiesFactory.getPropertiesHelper(PropertiesFile.G4);
//		String eventMonitorEnabel = pHelper.getValue("requestMonitor", "1");
//		String isAjax = request.getHeader("x-requested-with");
//		if (G4Utils.isEmpty(userInfo) && !uri.equals("/login.do") && enabled) {
//			if (G4Utils.isEmpty(isAjax)) {
//				response.getWriter().write(
//						"<script type=\"text/javascript\">parent.location.href='" + ctxPath
//								+ "/login.do?reqCode=init'</script>");
//				response.getWriter().flush();
//				response.getWriter().close();
//			} else {
//				response.sendError(G4Constants.Ajax_Timeout);
//			}
//			log.warn("警告:非法的URL请求已被成功拦截,请求已被强制重定向到了登录页�?访问来源IP锁定:" + request.getRemoteAddr() + " 试图访问的URL:"
//					+ request.getRequestURL().toString() + "?reqCode=" + request.getParameter("reqCode"));
//			return;
//		}
//		if (G4Utils.isNotEmpty(isAjax) && !uri.equals("/login.do")) {
//			String loginuserid = request.getParameter("loginuserid");
//			if (G4Utils.isEmpty(loginuserid)) {
//				response.sendError(G4Constants.Ajax_Unknow);
//				log.error("请求非法,[loginuserid]参数缺失");
//				return;
//			}
//			if (!loginuserid.equals(userInfo.getUserid())) {
//				response.sendError(G4Constants.Ajax_Session_Unavaliable);
//				log.error("当前会话和登录用户会话不�?��,请求被重定向到了登录页面");
//				return;
//			}
//		}
//		// if(){.... return;}
//		long start = System.currentTimeMillis();
//		fc.doFilter(request, response);
//		if (eventMonitorEnabel.equalsIgnoreCase(SystemConstants.EVENTMONITOR_ENABLE_Y)) {
//			costTime = new BigDecimal(System.currentTimeMillis() - start);
//			saveEvent(request, costTime);
//		}
//	}
//
//	/**
//	 * 写操作员事件�?	 * 
//	 * @param request
//	 */
//	private void saveEvent(HttpServletRequest request, BigDecimal costTime) {
//		UserInfoVo userInfo = WebUtils.getSessionContainer(request).getUserInfo();
//		if (G4Utils.isEmpty(userInfo)) {
//			return;
//		}
//		String menuid = request.getParameter("menuid4Log");
//		Dto dto = new BaseDto();
//		dto.put("account", userInfo.getAccount());
//		dto.put("activetime", G4Utils.getCurrentTimeAsNumber());
//		dto.put("userid", userInfo.getUserid());
//		dto.put("username", userInfo.getUsername());
//		dto.put("requestpath", request.getRequestURI());
//		dto.put("methodname", request.getParameter("reqCode"));
//		dto.put("eventid", IDHelper.getEventID());
//		dto.put("costtime", costTime);
//		if (G4Utils.isNotEmpty(menuid)) {
//			Dao g4Dao = (Dao) SpringBeanLoader.getSpringBean("g4Dao");
//			String menuname = ((BaseDto) g4Dao.queryForObject("Resource.queryEamenuByMenuID", menuid)).getAsString("menuname");
//			String msg = userInfo.getUsername() + "[" + userInfo.getAccount() + "]打开了菜单[" + menuname + "]";
//			dto.put("description", msg);
//			log.info(msg);
//		} else {
//			String msg = userInfo.getUsername() + "[" + userInfo.getAccount() + "]调用了Action方法["
//					+ request.getParameter("reqCode") + "]";
//			dto.put("description", msg);
//			log.info(msg + ";请求路径[" + request.getRequestURI() + "]");
//		}
//		MonitorService monitorService = (MonitorService) SpringBeanLoader.getSpringBean("monitorService");
//		monitorService.saveEvent(dto);
//
//	}
//
//	/**
//	 * �?��
//	 */
//	public void destroy() {
//		filterConfig = null;
//	}
//
//}
