package org.slave4j.wizards;

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
import org.slave4j.bean.JavaBean;
import org.slave4j.bean.JspBean;
import org.slave4j.bean.ProjectBean;
import org.slave4j.util.JavaTemplateGenerator;
import org.slave4j.util.JspTemplateGenerator;
import org.slave4j.util.Tools;

public class CodeGenerationWizard extends Wizard implements INewWizard {
	private ISelection selection;
	private ProjectBean projectVo;
	private List<JavaBean> javaTemplateArgsList;
	private List<JspBean> jspTemplateArgsList;

	public CodeGenerationWizard() {
		setNeedsProgressMonitor(true);
		setDefaultPageImageDescriptor(Activator.getImageDescriptor("icons/wizard.jpg"));
		setWindowTitle("代码生成");
	}

	@Override
	public void addPages() {
		CodeGenerationWizardPage page = new CodeGenerationWizardPage(this.selection);
		page.setTitle("代码生成");
		page.setDescription("相应的包名设置");
		addPage(page);
	}
	
	@Override
	public void init(IWorkbench arg0, IStructuredSelection selection) {
		try {
			this.selection = selection;
			this.projectVo = new ProjectBean(selection);
			this.javaTemplateArgsList = Tools.loadJavaTemplateListFromEntity(selection);
			this.jspTemplateArgsList = Tools.loadJspTemplateListFromEntity(selection);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public boolean performFinish() {
		final JavaTemplateGenerator generator = new JavaTemplateGenerator();
		final JspTemplateGenerator jspGenerator = new JspTemplateGenerator();
		
		IRunnableWithProgress op = new IRunnableWithProgress() {
			public void run(IProgressMonitor monitor) throws InvocationTargetException {
				monitor.beginTask("代码生成", 100);
				monitor.worked(1);
				monitor.subTask("java代码生成");
				for (JavaBean javaTemplateArgs : javaTemplateArgsList) {
					String javaCode = generator.generate(javaTemplateArgs);

					try {
						// 生成java类
						IPackageFragmentRoot packageFragmentRoot = projectVo.getJavaProject().findPackageFragmentRoot(new Path("/" + projectVo.getName() + "/src"));
						IPackageFragment packageFragment = packageFragmentRoot.getPackageFragment(javaTemplateArgs.getPackageName());
						if (!packageFragment.exists()) {
							packageFragment = packageFragmentRoot.createPackageFragment(javaTemplateArgs.getPackageName(),true, null);
						}
						packageFragment.createCompilationUnit(javaTemplateArgs.getClassName() + ".java",javaCode, true, new NullProgressMonitor());
					} catch (Exception e) {
						e.printStackTrace();
					}
				}

				monitor.worked(1);
				monitor.subTask("jsp和js代码生成");
				for (JspBean jspTemplateArgs : jspTemplateArgsList) {
					if (jspTemplateArgs != null) {
						String directoryPath = projectVo.getWebInf() + "/jsp/";
						Tools.createDirectory(directoryPath);

						String jspFilePath = directoryPath + "/" + jspTemplateArgs.getModeName() + "/" + jspTemplateArgs.getJspName() + ".jsp";
						String jsFilePath = directoryPath + "/" + jspTemplateArgs.getModeName() + "/" + jspTemplateArgs.getJspName() + ".js";
						Tools.createFile(jspFilePath);
						Tools.createFile(jsFilePath);

						String jspCode = jspGenerator.generateJsp(jspTemplateArgs);
						String jsCode = jspGenerator.generateJs(jspTemplateArgs);
						Tools.writeStringToFile(jspFilePath, jspCode);
						Tools.writeStringToFile(jsFilePath, jsCode);
					}
				}
				
				monitor.done();
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
}