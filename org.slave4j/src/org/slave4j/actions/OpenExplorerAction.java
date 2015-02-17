package org.slave4j.actions;

import java.io.File;
import java.io.IOException;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IPath;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.internal.core.JarPackageFragmentRoot;
import org.eclipse.jdt.internal.core.PackageFragment;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.IWorkbenchWindowActionDelegate;
import org.eclipse.ui.handlers.HandlerUtil;

public class OpenExplorerAction implements IWorkbenchWindowActionDelegate
{
  private Object selected = null;

  private Class selectedClass = null;

  public void init(IWorkbenchWindow window)
  {
  }

  public void run(IAction action)
  {
    if (!"unknown".equals(this.selected))
    {
      File directory = null;
      if ((this.selected instanceof IResource))
      {
        directory = new File(((IResource)this.selected).getLocation().toOSString());
      } else if ((this.selected instanceof File))
      {
        directory = (File)this.selected;
      }

      if ((this.selected instanceof IFile))
      {
        directory = directory.getParentFile();
      }
      if ((this.selected instanceof File))
      {
        directory = directory.getParentFile();
      }
      String target = "explorer.exe " + directory.toString();
      try
      {
        Runtime.getRuntime().exec(target);
      }
      catch (IOException e) {
        e.printStackTrace();
      }
    }
  }

  public void selectionChanged(IAction action, ISelection selection)
  {
    if ((selection instanceof IStructuredSelection))
    {
      Object obj = ((IStructuredSelection)selection).getFirstElement();
      if ((obj instanceof IAdaptable))
      {
        IAdaptable adaptable = (IAdaptable)obj;
        this.selectedClass = adaptable.getClass();
        if ((adaptable instanceof IResource))
        {
          this.selected = ((IResource)adaptable);
        } else if (((adaptable instanceof PackageFragment)) && ((((PackageFragment)adaptable).getPackageFragmentRoot() instanceof JarPackageFragmentRoot)))
        {
          this.selected = getJarFile(((PackageFragment)adaptable).getPackageFragmentRoot());
        } else if ((adaptable instanceof JarPackageFragmentRoot))
        {
          this.selected = getJarFile(adaptable);
        }
        else
          this.selected = ((IResource)adaptable.getAdapter(IResource.class));
      }
      else
      {
        this.selected = "unknown";
      }
    }
    else {
      this.selected = "unknown";
    }
  }

  protected File getJarFile(IAdaptable adaptable)
  {
    JarPackageFragmentRoot jpfr = (JarPackageFragmentRoot)adaptable;
    File selected = jpfr.getPath().makeAbsolute().toFile();
    if (!selected.exists())
    {
      File projectFile = new File(jpfr.getJavaProject().getProject().getLocation().toOSString());
      selected = new File(projectFile.getParent() + selected.toString());
    }
    return selected;
  }

  public void dispose()
  {
  }
}

//public class OpenExplorerAction extends AbstractHandler {
//	
//	/**
//	 * The constructor.
//	 */
//	public OpenExplorerAction() {
//	}
//
//	/**
//	 * the command has been executed, so extract extract the needed information
//	 * from the application context.
//	 */
//	public Object execute(ExecutionEvent event) throws ExecutionException {
//		ISelection selection = HandlerUtil.getCurrentSelection(event);
//		Shell shell = HandlerUtil.getActivePartChecked(event).getSite().getShell();
//		Object selected = null;
//		Class selectedClass = null;
//		if ((selection instanceof IStructuredSelection))
//	    {
//	      Object obj = ((IStructuredSelection)selection).getFirstElement();
//	      if ((obj instanceof IAdaptable))
//	      {
//	        IAdaptable adaptable = (IAdaptable)obj;
//	        selectedClass = adaptable.getClass();
//	        if ((adaptable instanceof IResource))
//	        {
//	          selected = ((IResource)adaptable);
//	        } else if (((adaptable instanceof PackageFragment)) && ((((PackageFragment)adaptable).getPackageFragmentRoot() instanceof JarPackageFragmentRoot)))
//	        {
//	          selected = getJarFile(((PackageFragment)adaptable).getPackageFragmentRoot());
//	        } else if ((adaptable instanceof JarPackageFragmentRoot))
//	        {
//	          selected = getJarFile(adaptable);
//	        }
//	        else
//	          selected = ((IResource)adaptable.getAdapter(IResource.class));
//	      }
//	      else
//	      {
//	        selected = "unknown";
//	      }
//	    }
//	    else {
//	      selected = "unknown";
//	    }
//		
//		if (!"unknown".equals(selected))
//	    {
//	      File directory = null;
//	      if ((selected instanceof IResource))
//	      {
//	        directory = new File(((IResource)selected).getLocation().toOSString());
//	      } else if ((selected instanceof File))
//	      {
//	        directory = (File)selected;
//	      }
//
//	      if ((selected instanceof IFile))
//	      {
//	        directory = directory.getParentFile();
//	      }
//	      if ((selected instanceof File))
//	      {
//	        directory = directory.getParentFile();
//	      }
//	      String target = "explorer.exe " + directory.toString();
//	      try
//	      {
//	        Runtime.getRuntime().exec(target);
//	      }
//	      catch (IOException e) {
//	        e.printStackTrace();
//	      }
//	    }
//		return null;
//	}
//	
//	protected File getJarFile(IAdaptable adaptable)
//	  {
//	    JarPackageFragmentRoot jpfr = (JarPackageFragmentRoot)adaptable;
//	    File selected = jpfr.getPath().makeAbsolute().toFile();
//	    if (!selected.exists())
//	    {
//	      File projectFile = new File(jpfr.getJavaProject().getProject().getLocation().toOSString());
//	      selected = new File(projectFile.getParent() + selected.toString());
//	    }
//	    return selected;
//	  }
//}