package org.slave4j.actions;

import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.IWorkbenchWindowActionDelegate;
import org.slave4j.wizards.JspCodeGenerationWizard;

public class JspCodeGenerationAction implements IWorkbenchWindowActionDelegate {
	private Shell shell;
	private IStructuredSelection selection;

	public void setActivePart(IAction action, IWorkbenchPart targetPart) {
		this.shell = targetPart.getSite().getShell();
	}

	public void run(IAction action) {
		JspCodeGenerationWizard wizard = new JspCodeGenerationWizard(this.shell);
		wizard.init(null, this.selection);
		WizardDialog dialog = new WizardDialog(this.shell, wizard);
		dialog.open();
	}

	public void selectionChanged(IAction action, ISelection selection) {
		if(selection instanceof IStructuredSelection){
			this.selection = ((IStructuredSelection) selection);
		}
	}

	@Override
	public void dispose() {
	}

	@Override
	public void init(IWorkbenchWindow arg0) {
	}
}

