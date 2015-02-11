package org.slave4j;

import java.io.File;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.jdt.core.IJavaElement;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;

public class ProjectBean
{
  private String name;
  private String root;
  private String src;
  private String webRoot;
  private String webRootName;
  private String webInf;
  private String lib;
  private IProject project;
  private IJavaProject javaProject;

  public ProjectBean(ISelection selection)
  {
    try {
		Object obj = ((IStructuredSelection)selection).getFirstElement();
		IJavaElement t = (IJavaElement)obj;
		this.javaProject = t.getJavaProject();
		this.project = t.getResource().getProject();

		this.name = this.project.getName();
		this.root = this.project.getLocation().toString();

		this.src = (this.root + "/src");

		this.webInf = getWebInf(this.root).replaceAll("\\\\", "/");

		this.webRootName = getWebRootName(this.webInf);
		this.webRoot = (this.root + "/" + getWebRootName(this.webInf));

		this.lib = (this.webInf + "/lib");
	} catch (Exception e) {
		System.out.println("你选中的资源不是一个web项目或者不在src目录下！ls");
	}
  }

  private String getWebInf(String root)
  {
    String webinfo = null;
    File f = new File(root);
    if (f.isDirectory())
    {
      File[] files = f.listFiles();
      for (File file : files)
      {
        if (file.isDirectory())
        {
          if (file.getName().equals("WEB-INF"))
          {
            webinfo = file.getPath();
          }
          else {
            webinfo = getWebInf(file.getPath());
          }
        }
      }
    }
    return webinfo;
  }

  private String getWebRootName(String webInf)
  {
    return webInf.split("/")[(webInf.split("/").length - 2)];
  }

  public void refresh()
  {
    try
    {
      this.project.refreshLocal(2, null);
    }
    catch (CoreException e) {
      e.printStackTrace();
    }
  }

  public String getRoot()
  {
    return this.root;
  }

  public void setRoot(String root)
  {
    this.root = root;
  }

  public String getSrc()
  {
    return this.src;
  }

  public void setSrc(String src)
  {
    this.src = src;
  }

  public String getWebRoot()
  {
    return this.webRoot;
  }

  public void setWebRoot(String webRoot)
  {
    this.webRoot = webRoot;
  }

  public String getWebInf()
  {
    return this.webInf;
  }

  public void setWebInf(String webInf)
  {
    this.webInf = webInf;
  }

  public String getLib()
  {
    return this.lib;
  }

  public void setLib(String lib)
  {
    this.lib = lib;
  }

  public IProject getProject()
  {
    return this.project;
  }

  public void setProject(IProject project)
  {
    this.project = project;
  }

  public String getName()
  {
    return this.name;
  }

  public void setName(String name)
  {
    this.name = name;
  }

  public IJavaProject getJavaProject()
  {
    return this.javaProject;
  }

  public void setJavaProject(IJavaProject javaProject)
  {
    this.javaProject = javaProject;
  }

  public String getWebRootName()
  {
    return this.webRootName;
  }
}