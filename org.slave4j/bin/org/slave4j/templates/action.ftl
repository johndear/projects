package ${packageName};

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
import ${entityPackageName}.${entityClassName};
import ${servicePackageName}.${serviceClassName};

@Controller
@RequestMapping(value="/${modeName}/${entityObjectName}")
public class ${className}{

	@Resource 
	private ${serviceClassName} ${serviceObjectName};
	

	@RequestMapping(value = "/list")
	public ModelMap list(HttpServletRequest request){
		PageData<${entityClassName}> pageData = new PageData<${entityClassName}>();
		//给pageData设置参数
		WebUtils.setPageDataParameter(request, pageData);
		pageData = ${serviceObjectName}.find(pageData);
		return new ModelMap(pageData);
	}
	
	@RequestMapping(value = "/new")
	public String addForm(Model model){
		return "/${modeName}/${entityObjectName}/input";
	}
	
	@RequestMapping(value = "/edit/{id}")
	public String editForm(@PathVariable("id") Integer id, Model model){
		${entityClassName} ${entityObjectName} = ${serviceObjectName}.find(id);
		model.addAttribute(${entityObjectName});
		return "/${modeName}/${entityObjectName}/input";
	}
	
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public String save(${entityClassName} ${entityObjectName}){
		if (${entityObjectName}.isNew()){
			${serviceObjectName}.save(${entityObjectName});
		}else{
			${serviceObjectName}.update(${entityObjectName});
		}
		return "redirect:/${modeName}/${entityObjectName}/list";//重定向
	}
	
	@RequestMapping(value = "/delete/{id}")
	public String delete(@PathVariable("id") Integer id){
		${serviceObjectName}.delete(id);
		return "redirect:/${modeName}/${entityObjectName}/list";//重定向
	}
	
	@RequestMapping(value = "/visible/{id}")
	public String visible(@PathVariable("id") Integer id){
		${serviceObjectName}.visible(id);
		return "redirect:/${modeName}/${entityObjectName}/list";//重定向
	}
	
	@RequestMapping(value = "/unVisible/{id}")
	public String unVisible(@PathVariable("id") Integer id){
		${serviceObjectName}.unVisible(id);
		return "redirect:/${modeName}/${entityObjectName}/list";//重定向
	}
	
	@InitBinder
	public void InitBinder(WebDataBinder dataBinder){
		dataBinder.registerCustomEditor(Date.class, new CustomDateEditor(new SimpleDateFormat("yyyy-MM-dd"), false));
	}
		

}
