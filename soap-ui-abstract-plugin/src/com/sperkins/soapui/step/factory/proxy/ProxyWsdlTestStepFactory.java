package com.sperkins.soapui.step.factory.proxy;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import com.eviware.soapui.config.TestStepConfig;
import com.eviware.soapui.impl.wsdl.testcase.WsdlTestCase;
import com.eviware.soapui.impl.wsdl.teststeps.WsdlTestStep;
import com.eviware.soapui.impl.wsdl.teststeps.registry.WsdlTestStepFactory;

/**
 * Proxies a WsdlTestStepFactory. Requires that the test step ID and test step class are set before buildTestStep() is
 * called.
 * 
 * @author sperkins
 * 
 */
public class ProxyWsdlTestStepFactory extends WsdlTestStepFactory {

	private Class<? extends WsdlTestStep> testStepClass;

	public ProxyWsdlTestStepFactory(String typeName, String name, String description, String pathToIcon) {
		super(typeName, name, description, pathToIcon);
	}

	@Override
	public WsdlTestStep buildTestStep(WsdlTestCase testCase, TestStepConfig config, boolean forLoadTest) {
		WsdlTestStep testStep = null;
		try {
			Constructor<? extends WsdlTestStep> constructor = getTestStepClass().getConstructor(WsdlTestCase.class, TestStepConfig.class, Boolean.TYPE);
			testStep = constructor.newInstance(testCase, config, forLoadTest);
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
		return testStep;
	}

	@Override
	public boolean canCreate() {
		return true;
	}

	@Override
	public TestStepConfig createNewTestStep(WsdlTestCase testCase, String name) {
		TestStepConfig testStepConfig = TestStepConfig.Factory.newInstance();
		testStepConfig.setType(getType());
		testStepConfig.setName(name);

		return testStepConfig;
	}

	public Class<? extends WsdlTestStep> getTestStepClass() {
		return testStepClass;
	}

	public void setTestStepClass(Class<? extends WsdlTestStep> testStepClass) {
		this.testStepClass = testStepClass;
	}
}