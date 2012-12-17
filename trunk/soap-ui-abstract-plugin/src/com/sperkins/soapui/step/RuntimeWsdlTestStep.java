package com.sperkins.soapui.step;

import javax.swing.ImageIcon;

import net.xeoh.plugins.base.Plugin;

import com.eviware.soapui.model.PanelBuilder;

/**
 * Interface for test steps that will be loaded at runtime. Test steps must extend WsdlTestStep.
 * 
 * @author sperkins
 * 
 */

public interface RuntimeWsdlTestStep extends Plugin {

	/**
	 * The test step type to display to the user
	 * 
	 * @return
	 */
	public String getTestStepName();

	/**
	 * The long description of this test step type
	 * 
	 * @return
	 */
	public String getTestStepDescription();

	/**
	 * The icon to display in the UI for this test step. Return <code>null</code> if you don't want an icon to be
	 * displayed.
	 * 
	 * @return
	 */
	public ImageIcon getIcon();

	/**
	 * The panel builder to be used by SoapUI for creating the desktop and overview panels
	 * 
	 * @return
	 */
	public PanelBuilder getPanelBuilder();
}
