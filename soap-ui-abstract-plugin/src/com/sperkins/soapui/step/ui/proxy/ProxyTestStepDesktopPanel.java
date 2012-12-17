package com.sperkins.soapui.step.ui.proxy;

import com.eviware.soapui.impl.wsdl.teststeps.WsdlTestStep;
import com.eviware.soapui.ui.support.ModelItemDesktopPanel;

public class ProxyTestStepDesktopPanel<T extends WsdlTestStep> extends ModelItemDesktopPanel<T> {

	private static final long serialVersionUID = 2456754645016182446L;

	public ProxyTestStepDesktopPanel(T modelItem) {
		super(modelItem);
	}

}