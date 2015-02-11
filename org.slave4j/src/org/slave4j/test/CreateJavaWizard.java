package org.slave4j.test;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Path;
import org.eclipse.jdt.core.IPackageFragment;
import org.eclipse.jdt.core.IPackageFragmentRoot;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.ui.INewWizard;
import org.eclipse.ui.IWorkbench;
import org.slave4j.Activator;
import org.slave4j.ProjectBean;
import org.slave4j.bean.ClassNameInfo;
import org.slave4j.bean.EntityFieldInfo;
import org.slave4j.bean.JavaTemplateArgs;
import org.slave4j.bean.JavaTemplateType;
import org.slave4j.bean.PackageNameInfo;
import org.slave4j.templates.ActionTemplate;
import org.slave4j.templates.DaoTemplate;
import org.slave4j.templates.EntityTemplate;
import org.slave4j.templates.JavaBeanTemplate;
import org.slave4j.templates.ServiceTemplate;

public class CreateJavaWizard extends Wizard
  implements INewWizard
{
	
	  private CreateJavaWizardPage page;
	  private ISelection selection;
	  private ProjectBean projectVo;
	  private List<JavaTemplateArgs> javaTemplateArgsList = new ArrayList<JavaTemplateArgs>();

	  public CreateJavaWizard()
	  {
	    setNeedsProgressMonitor(true);
	    setDefaultPageImageDescriptor(Activator.getImageDescriptor("icons/wizard.jpg"));
	    setWindowTitle("代码生成");
	  }

	  public void addPages()
	  {
	    this.page = new CreateJavaWizardPage(this.selection);
	    addPage(this.page);
	  }

	
	@Override
	public boolean performFinish(){
		final String javaPackage = this.page.getPackageName();// 获取填写的包名
		final String javaName = this.page.getJavaName();// 获取填写的类名
		final List<String> javaTypes = this.page.getJavaTypes();// 获取填写的类型（dao、action、controller）
		final boolean sameFordler = this.page.getSameFordler();
		
		// TODO Auto-generated method stub
		IRunnableWithProgress op = new IRunnableWithProgress(){
			DaoTemplate daoTemplate = new DaoTemplate();
    		ServiceTemplate serviceTemplate = new ServiceTemplate();
    	    ActionTemplate actionTemplate = new ActionTemplate();
    	    EntityTemplate entityTemplate = new EntityTemplate();
    	    JavaBeanTemplate javaBeanTemplate = new JavaBeanTemplate();
    	    
			public void run(IProgressMonitor monitor) throws InvocationTargetException{
		        monitor.beginTask("代码生成", 100);
		        monitor.worked(1);
		        monitor.subTask("java代码生成");
		        
			    initJavaTemplateArgsList(javaPackage, javaName, javaTypes);// 初始化javaTemplateArgsList
			    for (JavaTemplateArgs javaTemplateArgs : javaTemplateArgsList){
	         	      // 是否在同一包路径下，liusu+
				      if(sameFordler){
				    	  javaTemplateArgs.getPackageNameInfo().setSameEntityPackageName(sameFordler);// 不创建entity子包
				    	  javaTemplateArgs.getPackageNameInfo().setEntityPackageName(javaPackage);
				    	  javaTemplateArgs.getClassNameInfo().setFlag(true); // 引用的是xxEntity类，而不是xx类
				    	  if(javaTemplateArgs.getType().ordinal() == 3){
				    		  javaTemplateArgs.getClassNameInfo().setFlag(false); // 是xx类
				    	  }
				      }else{
				    	  if(javaTemplateArgs.getType().ordinal() != 3){// dao、service、action、entity类
				    		  javaTemplateArgs.getClassNameInfo().setFlag(true); // 引用的是xxEntity类，而不是xx类
					    	  javaTemplateArgs.getPackageNameInfo().setEntityPackageName(javaPackage+".entity"); 
					      }else{ // javabean类
					    	  javaTemplateArgs.getClassNameInfo().setFlag(false); // 是xx类
					    	  javaTemplateArgs.getPackageNameInfo().setSameEntityPackageName(true);// 不创建entity子包
					    	  javaTemplateArgs.getPackageNameInfo().setEntityPackageName(javaPackage);
					      }
				      }
				      
			          try{
			        	// 在基本包下创建子包路径
			            IPackageFragmentRoot packageFragmentRoot = projectVo.getJavaProject().findPackageFragmentRoot(new Path("/" + projectVo.getName() + "/src"));
			            if(packageFragmentRoot==null){ // liusu+
			        		packageFragmentRoot = projectVo.getJavaProject().findPackageFragmentRoot(new Path("/" + projectVo.getName() + "/src/main/java"));
			        	}
			            IPackageFragment packageFragment = packageFragmentRoot.getPackageFragment(javaTemplateArgs.getPackageName());
			            if (!packageFragment.exists()){
			              packageFragment = packageFragmentRoot.createPackageFragment(javaTemplateArgs.getPackageName(), true, null);
			            }
			            
			        	String javaCode = "";
			            switch (javaTemplateArgs.getType().ordinal()){
				            case 0:
				              javaCode = daoTemplate.generate(javaTemplateArgs);
				              break;
				            case 1:
				              javaCode = serviceTemplate.generate(javaTemplateArgs);
				              break;
				            case 2:
				              javaCode = actionTemplate.generate(javaTemplateArgs);
				              break;
				            case 3:
				              javaCode = javaBeanTemplate.generate(javaTemplateArgs);
				              break;
				            case 4:
					          javaCode = entityTemplate.generate(javaTemplateArgs);
			            }
			            
				        packageFragment.createCompilationUnit(javaTemplateArgs.getClassName() + ".java", javaCode, true, new NullProgressMonitor());
			          } catch (Exception e) {
			        	  e.printStackTrace();
			          }
				    
			          monitor.done();
			    }
		    }
	    };
	    
	    try{
	      getContainer().run(true, false, op);
	      this.projectVo.refresh();
		  return true;
	    }catch (Exception e) {
	      e.printStackTrace();
	      return false;
	    }
	}

	@Override
	public void init(IWorkbench arg0, IStructuredSelection selection) {
		// TODO Auto-generated method stub
		this.selection = selection;
	    this.projectVo = new ProjectBean(selection);
	}
	
	private void initJavaTemplateArgsList(final String javaPackage, final String javaName, final List<String> javaTypes) {
		for(String javaType : javaTypes){
	        try
	          {
	        	//创建基本包路径
	        	IPackageFragmentRoot packageFragmentRoot = projectVo.getJavaProject().findPackageFragmentRoot(new Path("/" + projectVo.getName() + "/src"));
	        	if(packageFragmentRoot==null){ // liusu+
	        		packageFragmentRoot = projectVo.getJavaProject().findPackageFragmentRoot(new Path("/" + projectVo.getName() + "/src/main/java"));
	        	}
	        	IPackageFragment packageFragment = packageFragmentRoot.getPackageFragment(javaPackage);
	            if (!packageFragment.exists())
	            {
	            	packageFragment = packageFragmentRoot.createPackageFragment(javaPackage, true, null);//javaTemplateArgs.getPackageName()
	            }
            
				PackageNameInfo packageNameInfo = new PackageNameInfo(packageFragment);
				ClassNameInfo classNameInfo = new ClassNameInfo(javaName);
				List<EntityFieldInfo> entityFieldInfoList = null;
					
		        JavaTemplateArgs javaTemplateArgs = new JavaTemplateArgs();
				javaTemplateArgs.setPackageNameInfo(packageNameInfo);// 包名
				javaTemplateArgs.setClassNameInfo(classNameInfo); // 类名
				javaTemplateArgs.setEntityFieldInfoList(entityFieldInfoList); // 字段列表
				javaTemplateArgs.setType(JavaTemplateType.valueOf(javaType.toUpperCase())); // dao类型
				javaTemplateArgsList.add(javaTemplateArgs);
		    }catch (Exception e) {
	            e.printStackTrace();
            }
	    }
	}
	
}
