package org.slave4j.wizards;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Path;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.IPackageFragment;
import org.eclipse.jdt.core.IPackageFragmentRoot;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.IWizardContainer;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.ui.INewWizard;
import org.eclipse.ui.IWorkbench;
import org.slave4j.Activator;
import org.slave4j.ProjectBean;
import org.slave4j.Tools;
import org.slave4j.bean.ClassNameInfo;
import org.slave4j.bean.JavaTemplateArgs;
import org.slave4j.bean.JspTemplateArgs;
import org.slave4j.templates.ActionTemplate;
import org.slave4j.templates.DaoTemplate;
import org.slave4j.templates.JspInputTemplate;
import org.slave4j.templates.JspListTemplate;
import org.slave4j.templates.ServiceTemplate;

public class CodeGenerationWizard extends Wizard
  implements INewWizard
{
  private CodeGenerationWizardPage page;
  private ISelection selection;
  private ProjectBean projectVo;
  private List<JavaTemplateArgs> javaTemplateArgsList;
  private List<JspTemplateArgs> jspTemplateArgsList;

  public CodeGenerationWizard()
  {
    setNeedsProgressMonitor(true);
    setDefaultPageImageDescriptor(Activator.getImageDescriptor("icons/wizard.jpg"));
    setWindowTitle("代码生成");
  }

  public void addPages()
  {
    this.page = new CodeGenerationWizardPage(this.selection);
    addPage(this.page);
  }

  public boolean performFinish()
  {
    IRunnableWithProgress op = new IRunnableWithProgress()
    {
      public void run(IProgressMonitor monitor) throws InvocationTargetException
      {
        monitor.beginTask("代码生成", 100);

        DaoTemplate daoTemplate = new DaoTemplate();
        ServiceTemplate serviceTemplate = new ServiceTemplate();
        ActionTemplate actionTemplate = new ActionTemplate();
        JspListTemplate jspListTemplate = new JspListTemplate();
        JspInputTemplate jspInputTemplate = new JspInputTemplate();

        monitor.worked(1);
        monitor.subTask("java代码生成");
        for (JavaTemplateArgs javaTemplateArgs : javaTemplateArgsList)
        {
          try
          {
            IPackageFragmentRoot packageFragmentRoot = CodeGenerationWizard.this.projectVo.getJavaProject().findPackageFragmentRoot(new Path("/" + CodeGenerationWizard.this.projectVo.getName() + "/src"));
            IPackageFragment packageFragment = packageFragmentRoot.getPackageFragment(javaTemplateArgs.getPackageName());
            if (!packageFragment.exists())
            {
              packageFragment = packageFragmentRoot.createPackageFragment(javaTemplateArgs.getPackageName(), true, null);
            }

            String javaCode = "";
            switch (javaTemplateArgs.getType().ordinal())
            {
            case 0:
              javaCode = daoTemplate.generate(javaTemplateArgs);
              break;
            case 1:
              javaCode = serviceTemplate.generate(javaTemplateArgs);
              break;
            case 2:
              javaCode = actionTemplate.generate(javaTemplateArgs);
            }

            packageFragment.createCompilationUnit(javaTemplateArgs.getClassName() + ".java", javaCode, true, new NullProgressMonitor());
          }
          catch (Exception e) {
            e.printStackTrace();
          }
        }

        monitor.worked(1);
        monitor.subTask("jsp代码生成");
        for (JspTemplateArgs jspTemplateArgs : jspTemplateArgsList)
        {
          String directoryPath = CodeGenerationWizard.this.projectVo.getWebInf() + "/jsp/" + jspTemplateArgs.getModeName() + "/" + jspTemplateArgs.getClassNameInfo().getEntityObjectName();
          Tools.createDirectory(directoryPath);

          String filePath = directoryPath + "/" + jspTemplateArgs.getJspName();
          Tools.createFile(filePath);

          String jspCode = "";
          switch (jspTemplateArgs.getType().ordinal())
          {
          case 1:
            jspCode = jspListTemplate.generate(jspTemplateArgs);
            break;
          case 2:
            jspCode = jspInputTemplate.generate(jspTemplateArgs);
            break;
          }

          Tools.writeStringToFile(filePath, jspCode);
        }
        monitor.done();
      }

    };
    try
    {
      getContainer().run(true, false, op);
    }
    catch (InvocationTargetException e) {
      e.printStackTrace();
      return false;
    }
    catch (InterruptedException e) {
      e.printStackTrace();
      return false;
    }

    this.projectVo.refresh();

    return true;
  }

  public void init(IWorkbench workbench, IStructuredSelection selection)
  {
    this.selection = selection;
    this.projectVo = new ProjectBean(selection);
    try
    {
      this.javaTemplateArgsList = Tools.createJavaTemplateArgsList(selection);
      this.jspTemplateArgsList = Tools.createJspTemplateArgsList(selection);
    }
    catch (Exception e) {
      e.printStackTrace();
    }
  }
}