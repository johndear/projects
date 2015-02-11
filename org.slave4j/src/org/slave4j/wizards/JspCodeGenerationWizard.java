package org.slave4j.wizards;

import java.lang.reflect.InvocationTargetException;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.dialogs.IDialogSettings;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.ui.INewWizard;
import org.eclipse.ui.IWorkbench;
import org.slave4j.Activator;
import org.slave4j.ProjectBean;
import org.slave4j.Tools;
import org.slave4j.bean.JsTemplateArgs;
import org.slave4j.bean.JspTemplateArgs;
import org.slave4j.templates.JsTemplate;
import org.slave4j.templates.JspListTemplate;

public class JspCodeGenerationWizard extends Wizard implements INewWizard {
	private JspCodeGenerationWizardPage page;
	private ISelection selection;
	private ProjectBean projectVo;
	private JspTemplateArgs jspTemplateArgs;
	private JsTemplateArgs jsTemplateArgs;

	public JspCodeGenerationWizard() {
		setNeedsProgressMonitor(true);
		setDefaultPageImageDescriptor(Activator.getImageDescriptor("icons/wizard.jpg"));
		setWindowTitle("代码生成");
	}

	@Override
	public IDialogSettings getDialogSettings() {
		// TODO Auto-generated method stub
		return super.getDialogSettings();
	}

	public void addPages() {
		this.page = new JspCodeGenerationWizardPage(this.selection);
		addPage(this.page);
	}

	public boolean performFinish() {
		final String jspName = page.getJspName();
		jspTemplateArgs.setJspName(jspName);
		jsTemplateArgs.setJsName(jspName);

		IRunnableWithProgress op = new IRunnableWithProgress() {
			public void run(IProgressMonitor monitor)
					throws InvocationTargetException {
				monitor.beginTask("代码生成", 100);

				JspListTemplate jspListTemplate = new JspListTemplate();
				JsTemplate jsTemplate = new JsTemplate();

				monitor.worked(1);
				monitor.subTask("jsp代码生成");
				if (jspTemplateArgs != null) {
					String directoryPath = JspCodeGenerationWizard.this.projectVo.getWebInf() + "/jsp/";
					Tools.createDirectory(directoryPath);

					String filePath = directoryPath + "/" + jspName + ".jsp";
					Tools.createFile(filePath);

					String jspCode = jspListTemplate.generate(jspTemplateArgs);
					Tools.writeStringToFile(filePath, jspCode);
				}

				monitor.worked(1);
				monitor.subTask("js代码生成");
				if (jsTemplateArgs != null) {
					String directoryPath = JspCodeGenerationWizard.this.projectVo.getWebInf() + "/jsp/";
					Tools.createDirectory(directoryPath);

					String filePath = directoryPath + "/" + jspName + ".js";
					Tools.createFile(filePath);

					String jsCode = jsTemplate.generate(jsTemplateArgs);
					Tools.writeStringToFile(filePath, jsCode);
				}

				monitor.done();
			}

		};
		try {
			getContainer().run(true, false, op);
		} catch (InvocationTargetException e) {
			e.printStackTrace();
			return false;
		} catch (InterruptedException e) {
			e.printStackTrace();
			return false;
		}

		this.projectVo.refresh();

		return true;
	}

	public void init(IWorkbench workbench, IStructuredSelection selection) {
		this.selection = selection;
		this.projectVo = new ProjectBean(selection);
		try {
			this.jspTemplateArgs = Tools.createCustomJspTemplateArgsList(selection);
			this.jsTemplateArgs = Tools.createCustomJsTemplateArgsList(selection);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}