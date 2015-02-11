package org.slave4j.test;

import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IObjectActionDelegate;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.IWorkbenchWindowActionDelegate;

public class CreateJavaAction implements IObjectActionDelegate,
		IWorkbenchWindowActionDelegate {
	private Shell shell;
	private IStructuredSelection selection;

	public void setActivePart(IAction action, IWorkbenchPart targetPart) {
		this.shell = targetPart.getSite().getShell();
	}

	public void run(IAction action) {
		CreateJavaWizard wizard = new CreateJavaWizard();
		wizard.init(null, this.selection);
		WizardDialog dialog = new WizardDialog(this.shell, wizard);
		dialog.open();
	}

	public void selectionChanged(IAction action, ISelection selection) {
		this.selection = ((IStructuredSelection) selection);
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub

	}

	@Override
	public void init(IWorkbenchWindow arg0) {
		// TODO Auto-generated method stub

	}
}