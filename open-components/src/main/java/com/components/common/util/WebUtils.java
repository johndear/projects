package com.components.common.util;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.components.common.metatype.Dto;
import com.components.common.metatype.impl.BaseDto;

/**
 * 和Web层相关的实用工具�? * 
 * @author 熊春
 * @since 2008-09-22
 */
public class WebUtils {
	/**
	 * 获取�?��SessionContainer容器,如果为null则创建之
	 * 
	 * @param form
	 * @param obj
	 */
	public static SessionContainer getSessionContainer(
			HttpServletRequest request) {
		SessionContainer sessionContainer = (SessionContainer) request
				.getSession().getAttribute("SessionContainer");
		if (sessionContainer == null) {
			sessionContainer = new SessionContainer();
			HttpSession session = request.getSession(true);
			session.setAttribute("SessionContainer", sessionContainer);
		}
		return sessionContainer;
	}

	/**
	 * 获取�?��SessionContainer容器,如果为null则创建之
	 * 
	 * @param form
	 * @param obj
	 */
	public static SessionContainer getSessionContainer(HttpSession session) {
		SessionContainer sessionContainer = (SessionContainer) session
				.getAttribute("SessionContainer");
		if (sessionContainer == null) {
			sessionContainer = new SessionContainer();
			session.setAttribute("SessionContainer", sessionContainer);
		}
		return sessionContainer;
	}

	/**
	 * 获取�?��Session属�?对象
	 * 
	 * @param request
	 * @param sessionName
	 * @return
	 */
	public static Object getSessionAttribute(HttpServletRequest request,
			String sessionKey) {
		Object objSessionAttribute = null;
		HttpSession session = request.getSession(false);
		if (session != null) {
			objSessionAttribute = session.getAttribute(sessionKey);
		}
		return objSessionAttribute;
	}

	/**
	 * 设置�?��Session属�?对象
	 * 
	 * @param request
	 * @param sessionName
	 * @return
	 */
	public static void setSessionAttribute(HttpServletRequest request,
			String sessionKey, Object objSessionAttribute) {
		HttpSession session = request.getSession();
		if (session != null)
			session.setAttribute(sessionKey, objSessionAttribute);
	}

	/**
	 * 移除Session对象属�?�?	 * 
	 * @param request
	 * @param sessionName
	 * @return
	 */
	public static void removeSessionAttribute(HttpServletRequest request,
			String sessionKey) {
		HttpSession session = request.getSession();
		if (session != null)
			session.removeAttribute(sessionKey);
	}

	/**
	 * 将请求参数封装为Dto
	 * 
	 * @param request
	 * @return
	 */
	public static Dto getParamAsDto(HttpServletRequest request) {
		Dto dto = new BaseDto();
		Map map = request.getParameterMap();
		Iterator keyIterator = (Iterator) map.keySet().iterator();
		while (keyIterator.hasNext()) {
			String key = (String) keyIterator.next();
			String value = ((String[]) (map.get(key)))[0];
			dto.put(key, value);
		}
		return dto;
	}

	/**
	 * 获取代码对照�?	 * 
	 * @param field
	 *            代码类别
	 * @param code
	 *            代码�?	 * @param request
	 * @return
	 */
	public static String getCodeDesc(String pField, String pCode,
			HttpServletRequest request) {
		List codeList = (List) request.getSession().getServletContext()
				.getAttribute("EACODELIST");
		String codedesc = null;
		for (int i = 0; i < codeList.size(); i++) {
			Dto codeDto = (BaseDto) codeList.get(i);
			if (pField.equalsIgnoreCase(codeDto.getAsString("field"))
					&& pCode.equalsIgnoreCase(codeDto.getAsString("code")))
				codedesc = codeDto.getAsString("codedesc");
		}
		return codedesc;
	}

	/**
	 * 根据代码类别获取代码表列�?	 * 
	 * @param codeType
	 * @param request
	 * @return
	 */
	public static List getCodeListByField(String pField,
			HttpServletRequest request) {
		List codeList = (List) request.getSession().getServletContext()
				.getAttribute("EACODELIST");
		List lst = new ArrayList();
		for (int i = 0; i < codeList.size(); i++) {
			Dto codeDto = (BaseDto) codeList.get(i);
			if (codeDto.getAsString("field").equalsIgnoreCase(pField)) {
				lst.add(codeDto);
			}
		}
		return lst;
	}

	/**
	 * 获取全局参数�?	 * 
	 * @param pParamKey
	 *            参数键名
	 * @return
	 */
	public static String getParamValue(String pParamKey,
			HttpServletRequest request) {
		String paramValue = "";
		ServletContext context = request.getSession().getServletContext();
		if (G4Utils.isEmpty(context)) {
			return "";
		}
		List paramList = (List) context.getAttribute("EAPARAMLIST");
		for (int i = 0; i < paramList.size(); i++) {
			Dto paramDto = (BaseDto) paramList.get(i);
			if (pParamKey.equals(paramDto.getAsString("paramkey"))) {
				paramValue = paramDto.getAsString("paramvalue");
			}
		}
		return paramValue;
	}

	/**
	 * 获取全局参数
	 * 
	 * @return
	 */
	public static List getParamList(HttpServletRequest request) {
		ServletContext context = request.getSession().getServletContext();
		if (G4Utils.isEmpty(context)) {
			return new ArrayList();
		}
		return (List) context.getAttribute("EAPARAMLIST");
	}

	/**
	 * 获取指定Cookie的�?
	 * 
	 * @param cookies
	 *            cookie集合
	 * @param cookieName
	 *            cookie名字
	 * @param defaultValue
	 *            缺省�?	 * @return
	 */
	public static String getCookieValue(Cookie[] cookies, String cookieName,
			String defaultValue) {
		if (cookies == null) {
			return defaultValue;
		}
		for (int i = 0; i < cookies.length; i++) {
			Cookie cookie = cookies[i];
			if (cookieName.equals(cookie.getName()))
				return (cookie.getValue());
		}
		return defaultValue;
	}

}
