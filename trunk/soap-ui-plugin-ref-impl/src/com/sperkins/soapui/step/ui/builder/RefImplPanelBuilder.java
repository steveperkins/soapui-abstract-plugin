package com.sperkins.soapui.step.ui.builder;

import javax.swing.JPanel;

import com.eviware.soapui.impl.EmptyPanelBuilder;
import com.eviware.soapui.support.components.JPropertiesTable;
import com.eviware.soapui.ui.desktop.DesktopPanel;
import com.sperkins.soapui.step.RefImplTestStep;
import com.sperkins.soapui.step.ui.RefImplTestStepDesktopPanel;

public class RefImplPanelBuilder extends EmptyPanelBuilder<RefImplTestStep> {

	@Override
	public DesktopPanel buildDesktopPanel(RefImplTestStep modelItem) {
		return new RefImplTestStepDesktopPanel(modelItem);
	}

	@Override
	public boolean hasDesktopPanel() {
		return true;
	}

	@Override
	public JPanel buildOverviewPanel(RefImplTestStep modelItem) {
		// The JPanel you return will be displayed when a user clicks this test step
		JPropertiesTable<RefImplTestStep> table = new JPropertiesTable<RefImplTestStep>("RefImpl Properties");
		table.addProperty("Prop Name 1", RefImplTestStep.PROPERTY_1, true);
		table.addProperty("Prop Name 2", RefImplTestStep.PROPERTY_2, true);
		table.setPropertyObject(modelItem);
		return table;
	}

	@Override
	public boolean hasOverviewPanel() {
		return true;
	}
}
