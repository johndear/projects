package org.slave4j.wizards;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.INewWizard;
import org.eclipse.ui.IWorkbench;
import org.slave4j.Activator;
import org.slave4j.bean.JavaBean;
import org.slave4j.bean.JavaBeanType;
import org.slave4j.bean.JspBean;
import org.slave4j.bean.JspBeanType;
import org.slave4j.bean.ProjectBean;
import org.slave4j.util.JspTemplateGenerator;
import org.slave4j.util.Tools;

public class JspCodeGenerationWizard extends Wizard implements INewWizard {
	private JspCodeGenerationWizardPage page;
	private ISelection selection;
	private ProjectBean projectVo;
	private Shell shell;

	public JspCodeGenerationWizard(Shell shell) {
		this.shell = shell;
		setNeedsProgressMonitor(true);
		setDefaultPageImageDescriptor(Activator.getImageDescriptor("icons/wizard.jpg"));
		setWindowTitle("代码生成");
	}

	public void addPages() {
		this.page = new JspCodeGenerationWizardPage(this.selection,this.shell);
		addPage(this.page);
	}
	
	@Override
	public void init(IWorkbench workbench, IStructuredSelection selection) {
		this.selection = selection;
		this.projectVo = new ProjectBean(selection);
	}

	public boolean performFinish() {
		final String jspName = page.getJspName();
		final List<String> jspTypes = page.getJspTypes();
		final JspTemplateGenerator generator = new JspTemplateGenerator();

		IRunnableWithProgress op = new IRunnableWithProgress() {
			public void run(IProgressMonitor monitor) throws InvocationTargetException {
				monitor.beginTask("代码生成", 100);
				monitor.worked(1);
				monitor.subTask("jsp代码生成");
				
				List<JspBean> jspTemplateArgsList = initJspTemplateList(jspName, jspTypes);// 初始化jspTemplateList
				for (JspBean jspTemplateArgs : jspTemplateArgsList) {
					if (jspTemplateArgs != null) {
						String directoryPath = JspCodeGenerationWizard.this.projectVo.getWebInf() + "/jsp/";
						Tools.createDirectory(directoryPath);
						
						String jspFilePath = directoryPath + "/" + jspTemplateArgs.getJspName() + ".jsp";
						String jsFilePath = directoryPath + "/" + jspTemplateArgs.getJspName() + ".js";
						Tools.createFile(jspFilePath);
						Tools.createFile(jsFilePath);
						String jspCode = generator.generateJsp(jspTemplateArgs);
						String jsCode = generator.generateJs(jspTemplateArgs);
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
	
	private List<JspBean> initJspTemplateList(String jspName, List<String> jspTypes) {
		List<JspBean> jspTemplateArgsList = new ArrayList<JspBean>();
		for (String jspType : jspTypes) {
			final JspBean jspTemplateArgs = new JspBean();
			jspTemplateArgs.setJspType(JspBeanType.valueOf(jspType.toUpperCase()));
			jspTemplateArgs.setJspName(jspName);
			jspTemplateArgs.setEntityClassName(jspName);
//			jspTemplateArgs.setEntityPackageName("com.it");
//			jspTemplateArgs.setModeName("aaa");
			jspTemplateArgsList.add(jspTemplateArgs);
		}
		return jspTemplateArgsList;
	}
}