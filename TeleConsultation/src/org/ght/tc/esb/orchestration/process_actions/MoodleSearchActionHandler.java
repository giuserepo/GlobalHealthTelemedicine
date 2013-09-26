/*
 * JBoss, Home of Professional Open Source
 * Copyright 2006, JBoss Inc., and others contributors as indicated 
 * by the @authors tag. All rights reserved. 
 * See the copyright.txt in the distribution for a
 * full listing of individual contributors. 
 * This copyrighted material is made available to anyone wishing to use,
 * modify, copy, or redistribute it subject to the terms and conditions
 * of the GNU Lesser General Public License, v. 2.1.
 * This program is distributed in the hope that it will be useful, but WITHOUT A 
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A 
 * PARTICULAR PURPOSE.  See the GNU Lesser General Public License for more details.
 * You should have received a copy of the GNU Lesser General Public License,
 * v.2.1 along with this distribution; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, 
 * MA  02110-1301, USA.
 * 
 * (C) 2005-2006,
 * @author JBoss Inc.
 */
package org.ght.tc.esb.orchestration.process_actions;

import org.jbpm.graph.def.ActionHandler;
import org.jbpm.graph.exe.ExecutionContext;
import org.jbpm.graph.exe.Token;
import org.jbpm.context.exe.ContextInstance;
import javax.xml.parsers.*;
import org.xml.sax.SAXException;
import java.io.*;
import org.xml.sax.*;
import org.w3c.dom.*;
import org.apache.commons.lang.StringEscapeUtils;
import java.util.*;


public class MoodleSearchActionHandler implements ActionHandler 
{
	private static final long serialVersionUID = 1L;

	Long startFrom;
	DocumentBuilder parser = null;
	
	public void execute(ExecutionContext exCtx) throws Exception 
	{
		System.out.println("Inizio MoodleSearchActionHandler");
		Token token = exCtx.getToken();
		ContextInstance context = token.getProcessInstance().getContextInstance();		
		String incidentAction = (String) context.getVariable("incidentAction",token);
		String incidentSeverity = (String) context.getVariable("incidentSeverity",token);
		String incidentType = (String) context.getVariable("incidentType",token);
		System.out.println("MoodleSearchActionHandler:incidentAction:"+incidentAction);
		System.out.println("MoodleSearchActionHandler:incidentSeverity:"+incidentSeverity);
		System.out.println("MoodleSearchActionHandler:incidentType:"+incidentType);		
/* Al momento questo handler � inutile in quanto la variabile di contesto incidentAction, � gi� impostata e theBody ha gi� i parametri di sessione di Moodle 		
		Token token = exCtx.getToken();
		ContextInstance context = token.getProcessInstance().getContextInstance();
        Object objBody = context.getVariable("theBody",token);
        String theBodyString;
        Map theBodyHM;
        if (objBody instanceof String) {
        	theBodyString=(String) context.getVariable("theBody",token);
        } else if (objBody instanceof Map) {
        	theBodyHM=(Map) context.getVariable("theBody",token);
        } else {
        	System.out.println("MoodleSearchActionHandler:il messaggio ha un body di un tipo non corretto (String o Map)");
        }

		String incidentAction = (String) context.getVariable("incidentAction",token);
		context.setVariable("incidentAction", incidentAction, token);
 */
	}

}
