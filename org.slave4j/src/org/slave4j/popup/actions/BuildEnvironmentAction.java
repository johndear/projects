package org.slave4j.popup.actions;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.dialogs.ProgressMonitorDialog;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IObjectActionDelegate;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.IWorkbenchPartSite;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.IWorkbenchWindowActionDelegate;
import org.slave4j.PluginBean;
import org.slave4j.ProjectBean;
import org.slave4j.Tools;

public class BuildEnvironmentAction
  implements IObjectActionDelegate, IWorkbenchWindowActionDelegate
{
  private Shell shell;
  private ProjectBean projectBean;

  public void setActivePart(IAction action, IWorkbenchPart targetPart)
  {
    this.shell = targetPart.getSite().getShell();
  }

  public void run(IAction action)
  {
    boolean flag = MessageDialog.openQuestion(this.shell, "slave4j", "是否搭建开发环境？");
    if (flag)
    {
      ProgressMonitorDialog progressDialog = new ProgressMonitorDialog(this.shell);

      IRunnableWithProgress runnable = new IRunnableWithProgress()
      {
        public void run(IProgressMonitor monitor) throws InvocationTargetException, InterruptedException
        {
          monitor.beginTask("搭建开发环境,请稍等......", 2);
          try
          {
            monitor.worked(1);
            monitor.subTask("向" + BuildEnvironmentAction.this.projectBean.getWebRootName() + "中导入所需的文件");
            Tools.copyFileToProject(PluginBean.getWebRoot(), BuildEnvironmentAction.this.projectBean.getWebRoot());

            monitor.worked(1);
            monitor.subTask("向src中导入所需的文件");
            Tools.copyFileToProject(PluginBean.getSrc(), BuildEnvironmentAction.this.projectBean.getSrc());
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

      this.projectBean.refresh();
    }
  }

  public void selectionChanged(IAction action, ISelection selection)
  {
    this.projectBean = new ProjectBean(selection);
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