package org.slave4j.wizards;

import org.apache.commons.lang.StringUtils;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

public class JspCodeGenerationWizardPage extends WizardPage {
	private Text jspNameText;
	private Text jspPathText;
	private Text jsPathText;

	protected JspCodeGenerationWizardPage(ISelection selection) {
		super("slave4j");
		setTitle("代码生成");
		setDescription("相应的包名设置");
	}

	public void createControl(Composite parent) {
		Composite container = new Composite(parent, 0);

		GridLayout layout = new GridLayout();
		container.setLayout(layout);
		layout.numColumns = 2;
		layout.verticalSpacing = 9;

		Label label = new Label(container, 0);
		label.setText("jsp名称:");
		this.jspNameText = new Text(container, 2060);
		this.jspNameText.setLayoutData(new GridData(4, 16777216, true, false,1, 1));
		this.jspNameText.setEditable(true);
		this.jspNameText.addModifyListener(new MyListener());

		label = new Label(container, 0);
		label.setText("jsp路径:");
		this.jspPathText = new Text(container, 2060);
		this.jspPathText.setLayoutData(new GridData(4, 16777216, true, false,1, 1));

		label = new Label(container, 0);
		label.setText("js路径:");
		this.jsPathText = new Text(container, 2060);
		this.jsPathText.setLayoutData(new GridData(4, 16777216, true, false, 1,1));

		parent.setFocus();
		setControl(container);
	}

	private void dialogChanged() {
		if (StringUtils.isEmpty(this.jspNameText.getText())) {
			updateStatus("你没有输入jsp名称！！！");
			return;
		}
		updateStatus(null);
	}

	private void updateStatus(String message) {
		setErrorMessage(message);
		setPageComplete(message == null);
	}

	public String getJspName() {
		return jspNameText.getText();
	}
	
	
	class MyListener implements ModifyListener{
		@Override
		public void modifyText(ModifyEvent arg0) {
			dialogChanged();
		}
		
	}
}
