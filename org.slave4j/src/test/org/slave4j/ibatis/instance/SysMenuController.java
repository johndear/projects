package test.org.slave4j.ibatis.instance;

import java.text.SimpleDateFormat;
import java.util.Date; 
import javax.annotation.Resource; 
import javax.servlet.http.HttpServletRequest; 
import org.slave4j.orm.PageData;
import org.slave4j.utils.WebUtils;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import SysMenu.sysMenu;

@Controller
@RequestMapping(value="/SysMenu/sysMenu")
public class SysMenuController{

	@Resource 
	private ISysMenuService sysMenuService;
	

	@RequestMapping(value = "/list")
	public void list(HttpServletRequest request){
	}
	
	@RequestMapping(value = "/new")
	public String addForm(Model model){
		return "/SysMenu/sysMenu/input";
	}
	
	@RequestMapping(value = "/edit/{id}")
	public String editForm(@PathVariable("id") Integer id, Model model){
		return "/SysMenu/sysMenu/input";
	}
	
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public String save(SysMenu sysMenu){
		if (sysMenu.isNew()){
			sysMenu.save(sysMenu);
		}else{
			sysMenu.update(sysMenu);
		}
		return "redirect:/SysMenu/sysMenu/list";//重定向
	}
	
	@RequestMapping(value = "/delete/{id}")
	public String delete(@PathVariable("id") Integer id){
		sysMenu.delete(id);
		return "redirect:/SysMenu/sysMenu/list";//重定向
	}
	
	@RequestMapping(value = "/visible/{id}")
	public String visible(@PathVariable("id") Integer id){
		sysMenu.visible(id);
		return "redirect:/SysMenu/sysMenu/list";//重定向
	}
	
	@RequestMapping(value = "/unVisible/{id}")
	public String unVisible(@PathVariable("id") Integer id){
		sysMenu.unVisible(id);
		return "redirect:/SysMenu/sysMenu/list";//重定向
	}
	
	@InitBinder
	public void InitBinder(WebDataBinder dataBinder){
		dataBinder.registerCustomEditor(Date.class, new CustomDateEditor(new SimpleDateFormat("yyyy-MM-dd"), false));
	}
		

}
