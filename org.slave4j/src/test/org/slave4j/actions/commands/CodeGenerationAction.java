package test.org.slave4j.actions.commands;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IObjectActionDelegate;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.IWorkbenchPartSite;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.IWorkbenchWindowActionDelegate;
import org.eclipse.ui.handlers.HandlerUtil;
import org.slave4j.wizards.CodeGenerationWizard;
import org.slave4j.wizards.JspCodeGenerationWizard;

//public class CodeGenerationAction implements IObjectActionDelegate,
//		IWorkbenchWindowActionDelegate {
//	private Shell shell;
//	private IStructuredSelection selection;
//
//	public void setActivePart(IAction action, IWorkbenchPart targetPart) {
//		this.shell = targetPart.getSite().getShell();
//	}
//
//	public void run(IAction action) {
//		CodeGenerationWizard wizard = new CodeGenerationWizard();
//		wizard.init(null, this.selection);
//		WizardDialog dialog = new WizardDialog(this.shell, wizard);
//		dialog.open();
//	}
//
//	public void selectionChanged(IAction action, ISelection selection) {
//		this.selection = ((IStructuredSelection) selection);
//	}
//
//	@Override
//	public void dispose() {
//		// TODO Auto-generated method stub
//
//	}
//
//	@Override
//	public void init(IWorkbenchWindow arg0) {
//		// TODO Auto-generated method stub
//
//	}
//}
public class CodeGenerationAction extends AbstractHandler {
	/**
	 * The constructor.
	 */
	public CodeGenerationAction() {
	}

	/**
	 * the command has been executed, so extract extract the needed information
	 * from the application context.
	 */
	public Object execute(ExecutionEvent event) throws ExecutionException {
		ISelection selection = HandlerUtil.getCurrentSelection(event);
		Shell shell = HandlerUtil.getActivePartChecked(event).getSite().getShell();
		
		CodeGenerationWizard wizard = new CodeGenerationWizard();
		wizard.init(null, (IStructuredSelection)selection);
		WizardDialog dialog = new WizardDialog(shell, wizard);
		dialog.open();
		return null;
	}
}