package org.slave4j.wizards;

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
import org.slave4j.bean.JavaBean;
import org.slave4j.bean.JavaBeanType;
import org.slave4j.bean.ProjectBean;
import org.slave4j.util.JavaTemplateGenerator;
import org.slave4j.util.Tools;

public class CreateJavaWizard extends Wizard implements INewWizard {

	private CreateJavaWizardPage page;
	private ISelection selection;
	private ProjectBean projectVo;

	public CreateJavaWizard() {
		setNeedsProgressMonitor(true);
		setDefaultPageImageDescriptor(Activator.getImageDescriptor("icons/wizard.jpg"));
		setWindowTitle("代码生成");
	}

	public void addPages() {
		this.page = new CreateJavaWizardPage(this.selection);
		addPage(this.page);
	}
	
	@Override
	public void init(IWorkbench arg0, IStructuredSelection selection) {
		this.selection = selection;
		this.projectVo = new ProjectBean(selection);
	}

	@Override
	public boolean performFinish() {
		final String javaPackage = this.page.getPackageName();// 获取填写的包名
		final String javaName = this.page.getJavaName();// 获取填写的类名
		final List<String> javaTypes = this.page.getJavaTypes();// 获取填写的类型（dao、action、controller）
		final boolean sameFordler = this.page.getSameFordler();

		IRunnableWithProgress op = new IRunnableWithProgress() {
			JavaTemplateGenerator generator = new JavaTemplateGenerator();

			public void run(IProgressMonitor monitor) throws InvocationTargetException {
				monitor.beginTask("代码生成", 100);
				monitor.worked(1);
				monitor.subTask("java代码生成");

				List<JavaBean> javaTemplateArgsList = initJavaTemplateList(javaPackage, javaName, javaTypes);// 初始化javaTemplateList
				for (JavaBean javaTemplateArgs : javaTemplateArgsList) {
					// 是否在同一包路径下，liusu+
					javaTemplateArgs.setSubpackage(!sameFordler);
					String javaCode = generator.generate(javaTemplateArgs);
					try {
						// 在基本包下创建子包路径
						IPackageFragmentRoot packageFragmentRoot = projectVo.getJavaProject().findPackageFragmentRoot(new Path("/" + projectVo.getName() + "/src"));
						if (packageFragmentRoot == null) { // liusu+
							packageFragmentRoot = projectVo.getJavaProject().findPackageFragmentRoot(new Path("/" + projectVo.getName() + "/src/main/java"));
						}
						IPackageFragment packageFragment = packageFragmentRoot.getPackageFragment(javaTemplateArgs.getPackageName());
						if (!packageFragment.exists()) {
							packageFragment = packageFragmentRoot.createPackageFragment(javaTemplateArgs.getPackageName(),true, null);
						}
						packageFragment.createCompilationUnit(javaTemplateArgs.getClassName() + ".java",javaCode, true, new NullProgressMonitor());
					} catch (Exception e) {
						e.printStackTrace();
					}

					monitor.done();
				}
			}
		};

		try {
			getContainer().run(true, false, op);
			this.projectVo.refresh();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	private List<JavaBean> initJavaTemplateList(final String javaPackage, final String javaName, final List<String> javaTypes) {
		List<JavaBean> javaTemplateArgsList = new ArrayList<JavaBean>();
		for (String javaType : javaTypes) {
			try {
				JavaBean javaTemplateArgs = new JavaBean();
				javaTemplateArgs.setEntityPackageName(javaPackage);// 包名
				javaTemplateArgs.setEntityClassName(javaName); // javabean类名
				javaTemplateArgs.setModeName(Tools.getModeName(javaPackage));
				javaTemplateArgs.setType(JavaBeanType.valueOf(javaType.toUpperCase())); // dao类型
				javaTemplateArgsList.add(javaTemplateArgs);
//				if(javaTypes.contains("entity")){
//					javaTemplateArgs.setEntityClassName(javaTemplateArgs.getClassName(4)); // 修正实体类名
//				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return javaTemplateArgsList;
	}

}
