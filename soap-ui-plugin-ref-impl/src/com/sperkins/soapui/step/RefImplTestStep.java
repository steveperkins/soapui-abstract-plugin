package com.sperkins.soapui.step;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;

import net.xeoh.plugins.base.annotations.PluginImplementation;

import com.eviware.soapui.SoapUI;
import com.eviware.soapui.config.TestStepConfig;
import com.eviware.soapui.impl.wsdl.testcase.WsdlTestCase;
import com.eviware.soapui.impl.wsdl.teststeps.WsdlTestStepResult;
import com.eviware.soapui.impl.wsdl.teststeps.WsdlTestStepWithProperties;
import com.eviware.soapui.model.PanelBuilder;
import com.eviware.soapui.model.propertyexpansion.PropertyExpander;
import com.eviware.soapui.model.propertyexpansion.PropertyExpansion;
import com.eviware.soapui.model.propertyexpansion.PropertyExpansionContainer;
import com.eviware.soapui.model.propertyexpansion.PropertyExpansionUtils;
import com.eviware.soapui.model.testsuite.TestCaseRunContext;
import com.eviware.soapui.model.testsuite.TestCaseRunner;
import com.eviware.soapui.model.testsuite.TestStepResult;
import com.eviware.soapui.support.xml.XmlObjectConfigurationBuilder;
import com.eviware.soapui.support.xml.XmlObjectConfigurationReader;
import com.sperkins.soapui.step.ui.builder.RefImplPanelBuilder;

@PluginImplementation
public class RefImplTestStep extends WsdlTestStepWithProperties implements PropertyExpansionContainer, RuntimeWsdlTestStep {

	public static final String PROPERTY_1 = "property1";
	public static final String PROPERTY_2 = "property2";

	private String property1;
	private String property2;

	/**
	 * A zero-arg constructor is required so the RuntimeWsdlTestStep loader can inspect this test step type's class name
	 */
	public RefImplTestStep() {
		super(null, null, true, false);
	}

	public RefImplTestStep(WsdlTestCase testCase, TestStepConfig config, boolean forLoadTest) {
		super(testCase, config, true, forLoadTest);
		// Display an icon for this test step
		if (!forLoadTest) {
			setIcon(getIcon());
		}
		// Load configuration properties for this test step
		readConfig(config);
	}

	@Override
	public TestStepResult run(TestCaseRunner testRunner, TestCaseRunContext runContext) {
		WsdlTestStepResult result = new WsdlTestStepResult(this);
		result.startTimer();

		SoapUI.log.info("property1: " + getProperty1());
		SoapUI.log.info("property2: " + getProperty2());

		// Expand any expressions the user has entered in this step's configuration parameters
		String expandedProperty1 = PropertyExpander.expandProperties(runContext, getProperty1());
		String expandedProperty2 = PropertyExpander.expandProperties(runContext, getProperty2());

		SoapUI.log.info("Expanded property1: " + expandedProperty1);
		SoapUI.log.info("Expanded property2: " + expandedProperty2);

		result.stopTimer();
		result.setStatus(TestStepResult.TestStepStatus.OK);

		return result;
	}

	/**
	 * Loads all persisted properties for this test step
	 * 
	 * @param config
	 */
	private void readConfig(TestStepConfig config) {
		XmlObjectConfigurationReader reader = new XmlObjectConfigurationReader(config.getConfig());
		this.property1 = reader.readString(PROPERTY_1, "");
		this.property2 = reader.readString(PROPERTY_2, "");
	}

	/**
	 * Persists all properties for this test step
	 */
	private void updateConfig() {
		XmlObjectConfigurationBuilder builder = new XmlObjectConfigurationBuilder();
		builder.add(PROPERTY_1, this.property1);
		builder.add(PROPERTY_2, this.property2);
		getConfig().setConfig(builder.finish());
	}

	@Override
	/**
	 * Expands all expressions and references in this step's configuration properties
	 */
	public PropertyExpansion[] getPropertyExpansions() {
		List result = new ArrayList();
		result.addAll(PropertyExpansionUtils.extractPropertyExpansions(this, this, PROPERTY_1));
		result.addAll(PropertyExpansionUtils.extractPropertyExpansions(this, this, PROPERTY_2));
		return (PropertyExpansion[]) result.toArray(new PropertyExpansion[result.size()]);
	}

	/**
	 * SoapUI will call this getter to populate the UI
	 * 
	 * @return
	 */
	public String getProperty1() {
		return property1;
	}

	/**
	 * SoapUI will call this setter when this configuration property's value changes
	 * 
	 * @param newValue
	 */
	public void setProperty1(String newValue) {
		String old = this.property1;
		updateConfig();
		this.property1 = newValue;
		notifyPropertyChanged(PROPERTY_1, old, newValue);
	}

	public String getProperty2() {
		return property2;
	}

	public void setProperty2(String newValue) {
		String old = this.property2;
		updateConfig();
		this.property2 = newValue;
		notifyPropertyChanged(PROPERTY_2, old, newValue);
	}

	/***********
	 * These are the methods required by RuntimeWsdlTestStep
	 ***********/
	@Override
	public String getTestStepName() {
		return "RefImpl Step";
	}

	@Override
	public String getTestStepDescription() {
		return "RefImpl Step description";
	}

	@Override
	public ImageIcon getIcon() {
		URL iconUrl = RefImplTestStep.class.getResource("/com/sperkins/soapui/icon/fist.png");
		if (null != iconUrl) {
			return new ImageIcon(iconUrl);
		}
		return null;
	}

	@Override
	public PanelBuilder<RefImplTestStep> getPanelBuilder() {
		return new RefImplPanelBuilder();
	}

}