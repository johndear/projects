package org.slave4j.wizards;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.Path;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.pde.internal.ui.elements.TreeContentProvider;
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
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.dialogs.ContainerSelectionDialog;
import org.eclipse.ui.dialogs.ElementListSelectionDialog;
import org.eclipse.ui.dialogs.ElementTreeSelectionDialog;
import org.eclipse.ui.dialogs.FileSelectionDialog;
import org.eclipse.ui.dialogs.FileSystemElement;
import org.eclipse.ui.dialogs.ResourceSelectionDialog;
import org.eclipse.ui.dialogs.TypeFilteringDialog;
import org.eclipse.ui.internal.ide.dialogs.FileFolderSelectionDialog;

public class JspCodeGenerationWizardPage extends WizardPage {
	private Text jspNameText;
	private Text jspPathText;
	private Text jsPathText;
	private static List<String> jspTypes;
	private Shell shell;
	private ISelection selection;

	protected JspCodeGenerationWizardPage(ISelection selection,Shell shell) {
		super("slave4j");
		this.selection = selection;
		this.shell = shell;
		setTitle("代码生成");
		setDescription("相应的包名设置");
	}

	public void createControl(Composite parent) {
		Composite container = new Composite(parent, 0);

		GridLayout layout = new GridLayout();
		container.setLayout(layout);
		layout.numColumns = 3;
		layout.verticalSpacing = 9;
		
		Label label = new Label(container, 0);
		label.setText("java实体:");
		final Text aext = new Text(container, 2060);
		aext.setLayoutData(new GridData(4, 16777216, true, false,1, 1));
		aext.setEditable(true);
		aext.addModifyListener(new MyListener());
		Button openButton = new Button(container, 0);
		openButton.addSelectionListener(new selectionListener(){
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				ContainerSelectionDialog dialog = new ContainerSelectionDialog(shell, ResourcesPlugin.getWorkspace().getRoot(), false, "test");
			    if (dialog.open() == 0) {
				      Object[] result = dialog.getResult();
				      if (result.length == 1){
				    	  aext.setText(((Path)result[0]).toString());
				      }
			    }
			}
		});
		openButton.setLayoutData(new GridData());
		openButton.setText("选择...");
		
		Label label1 = new Label(container, 0);
		label1.setText("jsp类型:");

		//把按钮放在group中，注意new Button(group这里
		final Group group = new Group(container, 0);
//	    group.setText("类型");
//	    group.setBounds(20, 28, 400, 100);
	    group.setLayoutData(new GridData(4, 16777216, true, false, 1, 1));
	    Button button = new Button(group, SWT.CHECK);
	    button.setText("list");
	    button.setBounds(10, 59, 60, 22);
	    button.addSelectionListener(new selectionListener());
	    Button button2 = new Button(group, SWT.CHECK);
	    button2.setText("add");
	    button2.setBounds(90, 59, 60, 22);
	    button2.addSelectionListener(new selectionListener());
	    Button button3 = new Button(group, SWT.CHECK);
	    button3.setText("edit");
	    button3.addSelectionListener(new selectionListener());
	    button3.setBounds(170, 59, 60, 22);
	    Button button4 = new Button(group, SWT.CHECK);
	    button4.setText("basic");
	    button4.setSelection(true);
	    button4.addSelectionListener(new selectionListener());
	    button4.setBounds(250, 59, 60, 22);

	    Label labeltemp = new Label(container, 0);
	    
		Label label2 = new Label(container, 0);
		label2.setText("jsp名称:");
		this.jspNameText = new Text(container, 2060);
		this.jspNameText.setLayoutData(new GridData(4, 16777216, true, false,1, 1));
		this.jspNameText.setEditable(true);
		this.jspNameText.addModifyListener(new MyListener());

		Label labeltemp2 = new Label(container, 0);
		
		label = new Label(container, 0);
		label.setText("jsp路径:");
		this.jspPathText = new Text(container, 2060);
		this.jspPathText.setLayoutData(new GridData(4, 16777216, true, false,1, 1));

		Label labeltemp3 = new Label(container, 0);
		
		label = new Label(container, 0);
		label.setText("js路径:");
		this.jsPathText = new Text(container, 2060);
		this.jsPathText.setLayoutData(new GridData(4, 16777216, true, false, 1,1));

		jspTypes = new ArrayList<String>();
		jspTypes.add("basic");
		parent.setFocus();
		setControl(container);
	}

	private void dialogChanged() {
		if (StringUtils.isEmpty(this.jspNameText.getText())) {
			updateStatus("你没有输入jsp名称！！！");
			return;
		}
		jspPathText.setText("jsp/"+jspNameText.getText()+".jsp");
		jsPathText.setText("jsp/"+jspNameText.getText()+".js");
		updateStatus(null);
	}

	private void updateStatus(String message) {
		setErrorMessage(message);
		setPageComplete(message == null);
	}

	public String getJspName() {
		return jspNameText.getText();
	}
	
	public List<String> getJspTypes() {
		return jspTypes;
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
				 jspTypes.add(selectedJavaType);
			 }else{
				 jspTypes.remove(selectedJavaType);
			 }
		}
		
		@Override
		public void widgetDefaultSelected(SelectionEvent arg0) {
			System.out.println("aaaaaaaaa");
		}
	}
}
