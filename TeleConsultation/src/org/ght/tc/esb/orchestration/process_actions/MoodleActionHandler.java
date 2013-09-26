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

import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import java.io.*;

import org.xml.sax.*;
import org.w3c.dom.*;
import org.apache.commons.lang.StringEscapeUtils;
import java.util.*;


public class MoodleActionHandler implements ActionHandler 
{
	private static final long serialVersionUID = 1L;

	Long startFrom;
	DocumentBuilder parser = null;
	
	public void execute(ExecutionContext exCtx) throws Exception 
	{
		System.out.println("MoodleActionHandler:1");
		Token token = exCtx.getToken();
		ContextInstance context = token.getProcessInstance().getContextInstance();
		//LinkedHashMap theBodyHM = (LinkedHashMap) context.getVariable("theBody",token);
		String theBodyMoodleSetUp = (String) context.getVariable("theBodyMoodleSetUp",token);
		//String theBody	= theBodyHM.toString();

		System.out.println("Inizio MoodleActionHandler: theBodyMoodleSetUp:"+theBodyMoodleSetUp );
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder parser = factory.newDocumentBuilder();

		StringReader reader = new StringReader( theBodyMoodleSetUp );
		InputSource inputSource = new InputSource( reader );
		Document doc = parser.parse( inputSource );
		
		String emailAddressesSpec = "";
		String emailAddressesTeam = "";
		
		int intCountSpec=0;
		int intCountTeam=0;
		
		while (intCountSpec < doc.getElementsByTagName("specialist").getLength()){
			System.out.println("MoodleActionHandler:2:"+doc.getElementsByTagName("specialist").item(intCountSpec).getFirstChild().getTextContent());
			emailAddressesSpec += doc.getElementsByTagName("specialist").item(intCountSpec).getFirstChild().getTextContent()+";";
			intCountSpec += 1;
		}
		System.out.println("MoodleActionHandler:1.1:"+ new Integer(doc.getElementsByTagName("team").getLength()).toString());
		while (intCountTeam < doc.getElementsByTagName("team").getLength()){
			System.out.println("MoodleActionHandler:2:"+doc.getElementsByTagName("team").item(intCountTeam).getFirstChild().getTextContent());
			emailAddressesTeam += doc.getElementsByTagName("team").item(intCountTeam).getFirstChild().getTextContent()+";";
			intCountTeam += 1;
		}		

		System.out.println("MoodleActionHandler: emailAddressesSpec:"+emailAddressesSpec);
		System.out.println("MoodleActionHandler: emailAddressesTeam:"+emailAddressesTeam);
		context.setVariable("emailAddressesSpec", emailAddressesSpec, token);
		context.setVariable("emailAddressesTeam", emailAddressesTeam, token);
		//context.setVariable("emailTeachAddress", emailTeachAddress, token);
		
	}

}
