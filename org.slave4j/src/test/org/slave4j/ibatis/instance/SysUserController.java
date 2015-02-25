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
import SysUser.sysUser;

@Controller
@RequestMapping(value="/SysUser/sysUser")
public class SysUserController{

	@Resource 
	private ISysUserService sysUserService;
	

	@RequestMapping(value = "/list")
	public void list(HttpServletRequest request){
	}
	
	@RequestMapping(value = "/new")
	public String addForm(Model model){
		return "/SysUser/sysUser/input";
	}
	
	@RequestMapping(value = "/edit/{id}")
	public String editForm(@PathVariable("id") Integer id, Model model){
		return "/SysUser/sysUser/input";
	}
	
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public String save(SysUser sysUser){
		if (sysUser.isNew()){
			sysUser.save(sysUser);
		}else{
			sysUser.update(sysUser);
		}
		return "redirect:/SysUser/sysUser/list";//重定向
	}
	
	@RequestMapping(value = "/delete/{id}")
	public String delete(@PathVariable("id") Integer id){
		sysUser.delete(id);
		return "redirect:/SysUser/sysUser/list";//重定向
	}
	
	@RequestMapping(value = "/visible/{id}")
	public String visible(@PathVariable("id") Integer id){
		sysUser.visible(id);
		return "redirect:/SysUser/sysUser/list";//重定向
	}
	
	@RequestMapping(value = "/unVisible/{id}")
	public String unVisible(@PathVariable("id") Integer id){
		sysUser.unVisible(id);
		return "redirect:/SysUser/sysUser/list";//重定向
	}
	
	@InitBinder
	public void InitBinder(WebDataBinder dataBinder){
		dataBinder.registerCustomEditor(Date.class, new CustomDateEditor(new SimpleDateFormat("yyyy-MM-dd"), false));
	}
		

}
