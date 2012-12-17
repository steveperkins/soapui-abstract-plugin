package com.sperkins.soapui.listener;

import java.io.File;
import java.net.URI;
import java.util.Collection;

import javax.swing.ImageIcon;

import net.xeoh.plugins.base.PluginManager;
import net.xeoh.plugins.base.impl.PluginManagerFactory;
import net.xeoh.plugins.base.util.PluginManagerUtil;

import com.eviware.soapui.SoapUI;
import com.eviware.soapui.impl.wsdl.teststeps.WsdlTestStep;
import com.eviware.soapui.impl.wsdl.teststeps.registry.WsdlTestStepRegistry;
import com.eviware.soapui.model.ModelItem;
import com.eviware.soapui.model.support.ProjectListenerAdapter;
import com.eviware.soapui.model.util.PanelBuilderRegistry;
import com.sperkins.soapui.icon.IconManager;
import com.sperkins.soapui.step.RuntimeWsdlTestStep;
import com.sperkins.soapui.step.factory.proxy.ProxyWsdlTestStepFactory;

/**
 * When this listener is instantiated it locates all WsdlTestSteps implementing a specific marker interface on the
 * classpath and registers them with SoapUI
 * 
 * @author sperkins
 * 
 */
public class RuntimeWsdlTestStepLoader extends ProjectListenerAdapter {

	private static int instantiationCount;

	public RuntimeWsdlTestStepLoader() {
		SoapUI.log("RuntimeWsdlTestStepLoader constructor");
		if (instantiationCount == 0) {
			loadTestStepPlugins();
		}
	}

	private void loadTestStepPlugins() {
		URI uri = new File("ext/teststeps/").toURI();
		SoapUI.log("RuntimeWsdlTestStepLoader - loading plugins from " + uri.toString());
		PluginManager pluginManager = PluginManagerFactory.createPluginManager();
		pluginManager.addPluginsFrom(uri);

		PluginManagerUtil pluginManagerUtil = new PluginManagerUtil(pluginManager);
		Collection<RuntimeWsdlTestStep> plugins = pluginManagerUtil.getPlugins(RuntimeWsdlTestStep.class);

		SoapUI.log("RuntimeWsdlTestStepLoader - Found " + (null == plugins ? "no" : plugins.size()) + " plugins");
		WsdlTestStepRegistry registry = WsdlTestStepRegistry.getInstance();

		for (RuntimeWsdlTestStep step : plugins) {
			// Register this test step type's icon so SoapUI won't try to find it within the SoapUI jar
			String pathToIcon = "/" + step.getClass().getName();

			ImageIcon icon = step.getIcon();
			if (null != icon) {
				pathToIcon += "-" + icon.toString();
				IconManager.getInstance().addIcon(pathToIcon, icon);
			} else {
				SoapUI.log("RuntimeWsdlTestStepLoader - No icon provided for Test Step " + step.getTestStepName());
			}
			// Create a test step factory that will build this test step type
			ProxyWsdlTestStepFactory factory = new ProxyWsdlTestStepFactory(step.getClass().getName(), step.getTestStepName(), step.getTestStepDescription(), pathToIcon);
			factory.setTestStepClass(((WsdlTestStep) step).getClass());
			// Register this test step type
			SoapUI.log("RuntimeWsdlTestStepLoader - Registering proxy test step factory for " + factory.getTestStepClass().toString());
			registry.addFactory(factory);

			SoapUI.log("RuntimeWsdlTestStepLoader - Registering panel builder for " + factory.getTestStepClass().toString());
			// Register the desktop panel builder for this test step type
			PanelBuilderRegistry.register(((ModelItem) step).getClass(), step.getPanelBuilder());
		}
	}

}
