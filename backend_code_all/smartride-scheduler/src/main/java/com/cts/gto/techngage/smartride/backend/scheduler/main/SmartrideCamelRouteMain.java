package com.cts.gto.techngage.smartride.backend.scheduler.main;

import org.apache.camel.spring.Main;

public class SmartrideCamelRouteMain 
{
    private Main main;
    
    public static void main(String[] args) throws Exception
    {
    	//String configFileUri = CommonUtil.toUnixPathSeparator(args[0]);
    	//System.setProperty("sys_prop_uri", configFileUri);
        SmartrideCamelRouteMain poc = new SmartrideCamelRouteMain();
        poc.boot();
    }
    
    public void boot() throws Exception {
        main = new Main();
        main.setApplicationContextUri ("My-Context.xml");
        main.enableHangupSupport();
        System.out.println("Starting Camel. Use ctrl + c to terminate the JVM.\n");
        main.run();
    }
}
