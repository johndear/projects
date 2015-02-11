package org.slave4j.test;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.eclipse.jdt.internal.core.PackageFragment;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

public class CreateJavaWizardPage extends WizardPage
{
  private Text packageText;
  private Text javaText;
  private ISelection selection;
  private static List<String> javaTypes;
  private boolean sameFordler = true;

  protected CreateJavaWizardPage(ISelection selection)
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
	label.setText("java包名:");
	this.packageText = new Text(container, 2060);
	this.packageText.setLayoutData(new GridData(4, 16777216, true, false, 1, 1));
	this.packageText.setEditable(true);
	this.packageText.addModifyListener(new MyListener());
	
	Label label2 = new Label(container, 0);
	label2.setText("java类名:");
	this.javaText = new Text(container, 2060);
	this.javaText.setLayoutData(new GridData(4, 16777216, true, false, 1, 1));
	this.javaText.setEditable(true);
	this.javaText.addModifyListener(new MyListener());
	
	Button sameFordlerButton = new Button(container, SWT.CHECK);
	sameFordlerButton.setText("是否在同一目录中生成");
	sameFordlerButton.setLayoutData(new GridData(4, 16777216, true, false, 1, 1));
	sameFordlerButton.setSelection(true);
	sameFordlerButton.addSelectionListener(new SameFordlerButtonSelectionListener());

	//把按钮放在group中，注意new Button(group这里
	final Group group = new Group(container, 0);
    group.setText("类型");
//    group.setBounds(20, 28, 400, 100);
    group.setLayoutData(new GridData(4, 16777216, true, false, 1, 1));
    Button button = new Button(group, SWT.CHECK);
    button.setText("action");
    button.setBounds(10, 59, 60, 22);
    button.addSelectionListener(new selectionListener());
    Button button2 = new Button(group, SWT.CHECK);
    button2.setText("service");
    button2.setBounds(90, 59, 60, 22);
    button2.addSelectionListener(new selectionListener());
    Button button3 = new Button(group, SWT.CHECK);
    button3.setText("dao");
    button3.addSelectionListener(new selectionListener());
    button3.setBounds(170, 59, 60, 22);
    Button button4 = new Button(group, SWT.CHECK);
    button4.setText("entity");
    button4.setBounds(250, 59, 60, 22);
    button4.addSelectionListener(new selectionListener());
    Button button5 = new Button(group, SWT.CHECK);
    button5.setText("javabean");
    button5.setBounds(330, 59, 60, 22);
    button5.setSelection(true);
    button5.addSelectionListener(new selectionListener());
    
    // 初始化
    javaTypes = new ArrayList<String>();
    javaTypes.add("javabean");
    initialize();
    
//    Button button5 = new Button(container, SWT.NONE);
//    button5.addSelectionListener(new SelectionAdapter() {
//    	  //按钮单击事件，显示两个组的选择情况
//    	   public void widgetSelected(final SelectionEvent e) {
//	    	    //Button类是Control的子类，可以直接强转
//	    	    Control[] c= group.getChildren();
//	    	    Button tmpb=null;
//	    	    StringBuffer result=new StringBuffer();
//	    	    result.append("分组选择的结果是:");
//	    	    for (Control control : c) {
//		    	     tmpb=(Button) control;
//		    	     if(tmpb.getSelection()){
//		    	    	 javaType = tmpb.getText();
//		    	    	 result.append(tmpb.getText());
//		    	     }
//	    	    }
//	    	    System.out.println(result.toString());
//    	   }
//    });
    
    parent.setFocus();
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
        this.packageText.setText(packageFragment.getElementName());
      }
    }
  }

private void dialogChanged()
  {
	  if (StringUtils.isEmpty(this.javaText.getText())) {
			updateStatus("你没有输入java名称！！！");
			return;
		}
	  updateStatus(null);
  }

  private void updateStatus(String message)
  {
    setErrorMessage(message);
    setPageComplete(message == null);
  }
  
  class MyListener implements ModifyListener{
	  @Override
	  public void modifyText(ModifyEvent arg0) {
		  dialogChanged();
	  }
  }
  
  class selectionListener implements SelectionListener{
		
		@Override
		public void widgetSelected(SelectionEvent arg0) {
			Button btn = ((Button)arg0.widget);
			String selectedJavaType = btn.getText();
			 if(btn.getSelection()){
				 javaTypes.add(selectedJavaType);
			 }else{
				 javaTypes.remove(selectedJavaType);
			 }
		}
		
		@Override
		public void widgetDefaultSelected(SelectionEvent arg0) {
			System.out.println("aaaaaaaaa");
		}
	}
  
  class SameFordlerButtonSelectionListener implements SelectionListener{
		
		@Override
		public void widgetSelected(SelectionEvent arg0) {
			Button btn = ((Button)arg0.widget);
			 if(btn.getSelection()){
				 sameFordler = true;
			 }else{
				 sameFordler = false;
			 }
		}
		
		@Override
		public void widgetDefaultSelected(SelectionEvent arg0) {
			System.out.println("aaaaaaaaa");
		}
	}
  
  public String getPackageName(){
	  return packageText.getText();
  }
  
  public String getJavaName(){
	  return javaText.getText();
  }
  
  public List<String> getJavaTypes(){
	  return javaTypes;
  }
  
  public boolean getSameFordler(){
	  return sameFordler;
  }
  
}
