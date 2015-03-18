The SoapUI Abstract Plugin project was created to remove some of the complexity involved with writing custom plugins for the <a href='http://soapui.org'>SoapUI</a> web service testing platform.

<ul><b>Features:</b>
<li>Allows easy creation of custom test steps for SOAP tests</li>
<li>No configuration files required! (SoapUI v4.5+ - see Getting Started guide)</li>
<li>Adds <b>custom test step icon support</b>, which SoapUI 4.0- does not!</li>
<li>Requires <b>just one class</b>!</li>
</ul>

SoapUI Abstract Plugin enables developers to create custom SoapUI TestSteps in just a few steps:
<ol>
<li>Copy soapUiAbstractPlugin-xx.jar and jspf-core-1.0.2.jar to SOAP_UI_HOME/bin/ext</li>
<li>Create a class that extends SoapUI's WsdlTestStep and implements RuntimeWsdlTestStep</li>
<li>Jar up your plugin and deploy it to a new directory named 'teststeps' in SOAP_UI_HOME/bin/ext</li>
</ol>

That's it! SoapUI Abstract Plugin will use the information in your plugin class to create proxy factories and register everything with SoapUI!

Also included is a reference implementation plugin - soapUiPluginRefImpl. This plugin demonstrates the use of SoapUI Abstract Plugin and can be dropped straight into SOAP\_UI\_HOME/bin/ext/teststeps.

Both SoapUI Abstract Plugin and the reference implementation come with Ant build files. To develop against these projects you will need the SoapUI libraries from SoapUI 4.0.1+.

<ul><b>Future features:</b>
<li>Support for non-WSDL test step types</li>
<li>Support for listeners (test step, test case, project, and workspace)</li>
</ul>

More documentation (with screenshots!) to come.