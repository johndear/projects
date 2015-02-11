package org.slave4j.wizards;

import org.apache.commons.lang.StringUtils;
import org.eclipse.jdt.internal.core.PackageFragment;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.slave4j.bean.PackageNameInfo;

public class CodeGenerationWizardPage extends WizardPage
{
  private Text entityPackageText;
  private Text daoPackageText;
  private Text servicPackageText;
  private Text actionPackageText;
  private ISelection selection;

  protected CodeGenerationWizardPage(ISelection selection)
  {
    super("slave4j");
    setTitle("代码生成");
    setDescription("相应的包名设置");
    this.selection = selection;
  }

  public void createControl(Composite parent)
  {
    Composite container = new Composite(parent, 0);

    GridLayout layout = new GridLayout();
    container.setLayout(layout);
    layout.numColumns = 2;
    layout.verticalSpacing = 9;

    Label label = new Label(container, 0);
    label.setText("entity包路径:");
    this.entityPackageText = new Text(container, 2060);
    this.entityPackageText.setLayoutData(new GridData(4, 16777216, true, false, 1, 1));

    label = new Label(container, 0);
    label.setText("dao包路径:");
    this.daoPackageText = new Text(container, 2060);
    this.daoPackageText.setLayoutData(new GridData(4, 16777216, true, false, 1, 1));

    label = new Label(container, 0);
    label.setText("service包路径:");
    this.servicPackageText = new Text(container, 2060);
    this.servicPackageText.setLayoutData(new GridData(4, 16777216, true, false, 1, 1));

    label = new Label(container, 0);
    label.setText("action包路径:");
    this.actionPackageText = new Text(container, 2060);
    this.actionPackageText.setLayoutData(new GridData(4, 16777216, true, false, 1, 1));

    parent.setFocus();

    initialize();

    dialogChanged();

    setControl(container);
  }

  private void initialize()
  {
    if ((this.selection != null) && (!this.selection.isEmpty()) && ((this.selection instanceof IStructuredSelection)))
    {
      IStructuredSelection ssel = (IStructuredSelection)this.selection;

      Object obj = ssel.getFirstElement();
      if ((obj instanceof PackageFragment))
      {
        PackageFragment packageFragment = (PackageFragment)obj;
        PackageNameInfo packageNameInfo = new PackageNameInfo(packageFragment);
        this.entityPackageText.setText(packageNameInfo.getEntityPackageName());
        this.daoPackageText.setText(packageNameInfo.getDaoPackageName());
        this.servicPackageText.setText(packageNameInfo.getServicPackageName());
        this.actionPackageText.setText(packageNameInfo.getActionPackageName());
      }
    }
  }

  private void dialogChanged()
  {
    String entityPackageName = this.entityPackageText.getText();
    if (entityPackageName.length() == 0)
    {
      updateStatus("你没有选择正确的实体包！！！");
      return;
    }
    if (!entityPackageName.endsWith(".entity"))
    {
      updateStatus("你选择的实体包名必须为entity，如“com.google.blog.entity”中的entity");
      return;
    }
    if (StringUtils.countMatches(entityPackageName, ".") < 3)
    {
      updateStatus("实体包必须在4层以上，注意实体包名的上一层包名为模块名，如“org.slave4j.blog.entity”中“blog”为博客模块");
      return;
    }
    updateStatus(null);
  }

  private void updateStatus(String message)
  {
    setErrorMessage(message);
    setPageComplete(message == null);
  }
}