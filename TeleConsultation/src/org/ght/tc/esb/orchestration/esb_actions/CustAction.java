

package org.ght.tc.esb.orchestration.esb_actions;


 

import org.jboss.soa.esb.helpers.ConfigTree;


import org.jbpm.graph.def.*;
import org.jbpm.graph.exe.ExecutionContext;


import org.apache.log4j.Logger;




public class CustAction implements ActionHandler {

	protected ConfigTree	_config;
	private Logger logger = Logger.getLogger(CustAction.class);
	String queueName = "quickstart_helloworld_Request_gw";

	  public static boolean isExecuted = false;  

	  // The action will set the isExecuted to true so the 
	  // unit test will be able to show when the action
	  // is being executed.
	  public void execute(ExecutionContext executionContext) {
	    isExecuted = true;
	  } 
}	