# Introduction #

SoapUI Abstract Plugin makes it easier to write custom test steps for the SoapUI automated testing platform. Without SoapUI Abstract Plugin, a developer must create boilerplate factories and perform tedious XML configuration. SoapUI Abstract Plugin abstracts away the common parts of a SoapUI plugin so developers can focus on the _functionality_ of their custom plugin rather than its configuration.


# Getting Started #

**Preliminary note:** This guide references SOAP\_UI\_HOME, which is just your SoapUI installation directory. It is assumed that the reader is proficient with a Java IDE and has already created a Java project for custom plugin development.

## 1. Creating the Plugin ##
Head over to the Downloads page (http://code.google.com/p/soapui-abstract-plugin/downloads/list) and get the latest version of SoapUI Abstract Plugin and JSPF. Both JARs need to be copied into SOAP\_UI\_HOME/bin/ext **and** your plugin project's `lib` folder.


**Create a new class** that extends `WsdlTestStep` (or a variant - e.g. `WsdlTestStepWithProperties`) and implements RuntimeWsdlTestStep. **A zero-arg constructor is required**, but you can pass anything you want to the super constructor. Example:
```
public RefImplTestStep() {
	super(null, null, true, false);
}
```

**Add a constructor that complies with WsdlTestStep**. Example:
```
public RefImplTestStep(WsdlTestCase testCase, TestStepConfig config, boolean forLoadTest) {
		super(testCase, config, true, forLoadTest);
		// Display an icon for this test step
		if (!forLoadTest) {
			setIcon(getIcon());
		}
	}
```

**Implement the RuntimeWsdlTestStep methods**. Example:
```
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
		// This can return null if you don't need a user interface for your custom test step
		return null;
	}
```


**Override the `run(WsdlTestCase, TestStepConfig, boolean)` method**. This is where your custom functionality comes into play. This example is an extremely simple (and not very useful) custom test step:
```
	@Override
	public TestStepResult run(TestCaseRunner testRunner, TestCaseRunContext runContext) {
		WsdlTestStepResult result = new WsdlTestStepResult(this);
		result.startTimer();

		SoapUI.log.info("Custom test step ran!");

		result.stopTimer();
		result.setStatus(TestStepResult.TestStepStatus.OK);

		return result;
	}
```

## Deploying the Plugin ##
**Package your custom plugin into a JAR**. The name doesn't matter.

**Copy the JAR to `SOAP_UI_HOME/bin/ext/teststeps`**. If this is your first custom plugin with SoapUI Abstract Plugin, you'll need to create the `teststeps` folder.

If you're deploying to a version of SoapUI earlier than 4.5, you'll need to download (from the Downloads page) `abstract-plugin-listeners.xml` to `SOAP_UI_HOME/bin/listeners`. SoapUI 4.5+ is supposed to have a mechanism that looks for this file in a JAR's META-INF folder.

**Start SoapUI**. If SoapUI Abstract Plugin found your custom plugin, the SoapUI log will display messages like this:
```
RuntimeWsdlTestStepLoader - loading plugins from C:/soapui/bin/ext/teststeps
RuntimeWsdlTestStepLoader - Found 1 plugins
RuntimeWsdlTestStepLoader - Registering proxy test step factory for RefImplTestStep
RuntimeWsdlTestStepLoader - Registering panel builder for RefImplTestStep
```

## Troubleshooting ##
If things did not go as planned, check the following:
<ul>
<li>Is there already a project in your SoapUI workspace? Since SoapUI doesn't create an event when the application or a workspace opens, SoapUI Abstract Plugin relies upon a project-level load listener to scan for and register plugins. It won't work unless you load a workspace that already has at least one project.</li>
<li>Is your custom plugin jar in <code>SOAP_UI_HOME/bin/ext/teststeps</code>?</li>
<li>Does your custom plugin class extend WsdlTestStep and implement RuntimeWsdlTestStep?</li>
<li>Does your custom plugin class override the <code>run(WsdlTestCase, TestStepConfig, boolean)</code> method? Your IDE probably created the signature for you, but make sure the last parameter is a primitive <code>boolean</code>, not an object wrapper.</li>
<li>Are soap-ui-abstract-plugin-xx.jar and jspf.core-xx.jar in <code>SOAP_UI_HOME/bin/ext</code>?</li>
<li>Try copying <code>abstract-plugin-listeners.xml</code> to <code>SOAP_UI_HOME/bin/listeners</code> and restarting SoapUI</li>
<li>Drop the reference implementation JAR into <code>SOAP_UI_HOME/bin/ext/teststeps</code> and restart SoapUI. If you go to add a test step and do not see RefImplTestStep on the list, you're probably missing <code>soap-ui-abstract-plugin-xx.jar</code> or <code>abstract-plugin-listeners.xml</code>.</li>
<li>Compare your custom plugin class against the RefImplTestStep class in the reference implementation download</li>
</ul>