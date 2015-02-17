package test.org.slave4j.actions.commands;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.dialogs.ProgressMonitorDialog;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.handlers.HandlerUtil;
import org.slave4j.bean.PluginBean;
import org.slave4j.bean.ProjectBean;
import org.slave4j.util.Tools;

public class BuildEnvironmentAction extends AbstractHandler {
	/**
	 * The constructor.
	 */
	public BuildEnvironmentAction() {
	}

	/**
	 * the command has been executed, so extract extract the needed information
	 * from the application context.
	 */
	public Object execute(ExecutionEvent event) throws ExecutionException {
		ISelection selection = HandlerUtil.getCurrentSelection(event);
		Shell shell = HandlerUtil.getActivePartChecked(event).getSite().getShell();
		final ProjectBean projectBean = new ProjectBean(selection);
		
		boolean flag = MessageDialog.openQuestion(shell, "slave4j", "是否搭建开发环境？");
	    if (flag)
	    {
	      ProgressMonitorDialog progressDialog = new ProgressMonitorDialog(shell);

	      IRunnableWithProgress runnable = new IRunnableWithProgress()
	      {
	        public void run(IProgressMonitor monitor) throws InvocationTargetException, InterruptedException
	        {
	          monitor.beginTask("搭建开发环境,请稍等......", 2);
	          try
	          {
	            monitor.worked(1);
	            monitor.subTask("向" + projectBean.getWebRootName() + "中导入所需的文件");
	            Tools.copyFileToProject(PluginBean.getWebRoot(), projectBean.getWebRoot());

	            monitor.worked(1);
	            monitor.subTask("向src中导入所需的文件");
	            Tools.copyFileToProject(PluginBean.getSrc(), projectBean.getSrc());
	          }
	          catch (IOException e) {
	            e.printStackTrace();
	          }

	          monitor.done();
	        }

	      };
	      try
	      {
	        progressDialog.run(true, false, runnable);
	      }
	      catch (InvocationTargetException e) {
	        e.printStackTrace();
	      }
	      catch (InterruptedException e) {
	        e.printStackTrace();
	      }

	      projectBean.refresh();
	    }
	    
		return null;
	}
}