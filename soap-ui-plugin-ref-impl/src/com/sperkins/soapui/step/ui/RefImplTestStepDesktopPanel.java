package com.sperkins.soapui.step.ui;

import com.eviware.soapui.ui.support.ModelItemDesktopPanel;
import com.sperkins.soapui.step.RefImplTestStep;

/**
 * A default, empty desktop panel. This is only used so SoapUI will display the overview panel.
 * 
 * @author sperkins
 * 
 */
public class RefImplTestStepDesktopPanel extends ModelItemDesktopPanel<RefImplTestStep> {

	private static final long serialVersionUID = 2456754645016182446L;

	public RefImplTestStepDesktopPanel(RefImplTestStep modelItem) {
		super(modelItem);
	}
}