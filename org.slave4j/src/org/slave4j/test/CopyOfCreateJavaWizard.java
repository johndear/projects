package org.slave4j.test;

import java.lang.reflect.InvocationTargetException;
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

public class CopyOfCreateJavaWizard extends Wizard
  implements INewWizard
{
	
	  private CreateJavaWizardPage page;
	  private ISelection selection;
	  private ProjectBean projectVo;

	  public CopyOfCreateJavaWizard()
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
	public boolean performFinish() {
		final String javaPackage = this.page.getPackageName();// 获取填写的包名
		final String javaName = this.page.getJavaName();// 获取填写的类名
		final List<String> javaTypes = this.page.getJavaTypes();// 获取填写的类型（dao、action、controller）
		
		// TODO Auto-generated method stub
		IRunnableWithProgress op = new IRunnableWithProgress()
	    {
			public void run(IProgressMonitor monitor) throws InvocationTargetException
		      {
		        monitor.beginTask("代码生成", 100);
		        monitor.worked(1);
		        monitor.subTask("java代码生成");
		        
			    for(String javaType : javaTypes){
			        try
			          {
			        	IPackageFragmentRoot packageFragmentRoot = CopyOfCreateJavaWizard.this.projectVo.getJavaProject().findPackageFragmentRoot(new Path("/" + CopyOfCreateJavaWizard.this.projectVo.getName() + "/src"));
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
				        try
				          {
				        	DaoTemplate daoTemplate = new DaoTemplate();
			        		ServiceTemplate serviceTemplate = new ServiceTemplate();
			        	    ActionTemplate actionTemplate = new ActionTemplate();
			        	    EntityTemplate entityTemplate = new EntityTemplate();
			        	    JavaBeanTemplate javaBeanTemplate = new JavaBeanTemplate();
			        	        
				        	String javaCode = "";
				            switch (javaTemplateArgs.getType().ordinal())
				            {
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
				          }
				          catch (Exception e) {
				        	  e.printStackTrace();
				          }
				    }catch (Exception e) {
			            e.printStackTrace();
		            }
			      }
			    
			    monitor.done();
		      }
	    };
	    
	    try
	    {
	      getContainer().run(true, false, op);
	    }
	    catch (InvocationTargetException e) {
	      e.printStackTrace();
	      return false;
	    }
	    catch (InterruptedException e) {
	      e.printStackTrace();
	      return false;
	    }

	    this.projectVo.refresh();

	    return true;
	}

	@Override
	public void init(IWorkbench arg0, IStructuredSelection selection) {
		// TODO Auto-generated method stub
		this.selection = selection;
	    this.projectVo = new ProjectBean(selection);
	}
	
//	public static void main(String[] args) {
//		IPackageFragmentRoot packageFragmentRoot = CodeGenerationWizard.this.projectVo.getJavaProject().findPackageFragmentRoot(new Path("/" + CodeGenerationWizard.this.projectVo.getName() + "/src"));
//		IPackageFragment packageFragment = packageFragmentRoot.getPackageFragment(javaTemplateArgs.getPackageName());
//        if (!packageFragment.exists())
//        {
//          packageFragment = packageFragmentRoot.createPackageFragment(javaTemplateArgs.getPackageName(), true, null);
//        }
//
//        String javaCode = "";
//        switch (javaTemplateArgs.getType().ordinal())
//        {
//        case 0:
//          javaCode = daoTemplate.generate(javaTemplateArgs);
//          break;
//        case 1:
//          javaCode = serviceTemplate.generate(javaTemplateArgs);
//          break;
//        case 2:
//          javaCode = actionTemplate.generate(javaTemplateArgs);
//        }
//
//        packageFragment.createCompilationUnit(javaTemplateArgs.getClassName() + ".java", javaCode, true, new NullProgressMonitor());
//      }

}
