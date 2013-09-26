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
import java.util.Map;

import org.xml.sax.*;
import org.w3c.dom.*;
import org.apache.commons.lang.StringEscapeUtils;



public class MoodlePrepareSetup implements ActionHandler 
{
	private static final long serialVersionUID = 1L;

	Long startFrom;
	DocumentBuilder parser = null;
	
	public void execute(ExecutionContext exCtx) throws Exception 
	{
		Token token = exCtx.getToken();
		ContextInstance context = token.getProcessInstance().getContextInstance();
		String theBody	= (String)context.getVariable("theBody",token);
		System.out.println("MoodlePrepareSetup:theBody:"+theBody);
		String theBodySpec	= (String)context.getVariable("theBodySpec",token);
		System.out.println("MoodlePrepareSetup:theBodySpec:"+theBodySpec);
		Object theBodyMoodle =(Map) context.getVariable("theBodyMoodle",token);
		System.out.println("MoodlePrepareSetup:theBodyMoodle:"+theBodyMoodle.toString());
		
		
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder parser = factory.newDocumentBuilder();

		StringReader reader = new StringReader( theBody );
		InputSource inputSource = new InputSource( reader );
		Document doc = parser.parse( inputSource ); 
		
		StringReader readerSpec = new StringReader( theBodySpec );
		InputSource inputSourceSpec = new InputSource( readerSpec );
		Document docSpec = parser.parse( inputSourceSpec ); 
		
	
		int intCountSpec=0;
		int intCountSpecItem=0;
		String theBodyMoodleSetUp= "<env:Envelope xmlns:env='http://schemas.xmlsoap.org/soap/envelope/'><env:Header></env:Header><env:Body>";

		while (intCountSpec < docSpec.getElementsByTagName("ruleAction").getLength()){
			theBodyMoodleSetUp += "<specialist>";
			while (intCountSpecItem < docSpec.getElementsByTagName("ruleAction").item(intCountSpec).getChildNodes().getLength()){
				theBodyMoodleSetUp += "<"+docSpec.getElementsByTagName("ruleAction").item(intCountSpec).getChildNodes().item(intCountSpecItem).getNodeName().toString()+">";
				theBodyMoodleSetUp += docSpec.getElementsByTagName("ruleAction").item(intCountSpec).getChildNodes().item(intCountSpecItem).getTextContent().toString();
				theBodyMoodleSetUp += "</"+docSpec.getElementsByTagName("ruleAction").item(intCountSpec).getChildNodes().item(intCountSpecItem).getNodeName().toString()+">";
				intCountSpecItem += 1;
			}
			intCountSpecItem=0;
			intCountSpec += 1;
			theBodyMoodleSetUp += "</specialist>";
		}


		//System.out.println("MoodlePrepareSetup:theBodyMoodleSetUp:"+theBodyMoodleSetUp);
		int intCountTeam=0;
		int intCountTeamItem=0;
		System.out.println("MoodlePrepareSetup:theBodyMoodleSetUp:"+new Integer(intCountTeam).toString()+","+new Integer(doc.getElementsByTagName("ruleAction").getLength()).toString());
		while (intCountTeam < doc.getElementsByTagName("ruleAction").getLength()){
			theBodyMoodleSetUp += "<team>";
			while (intCountTeamItem < doc.getElementsByTagName("ruleAction").item(intCountTeam).getChildNodes().getLength()){
				theBodyMoodleSetUp += "<"+doc.getElementsByTagName("ruleAction").item(intCountTeam).getChildNodes().item(intCountTeamItem).getNodeName().toString()+">";
				theBodyMoodleSetUp += doc.getElementsByTagName("ruleAction").item(intCountTeam).getChildNodes().item(intCountTeamItem).getTextContent().toString();
				theBodyMoodleSetUp += "</"+doc.getElementsByTagName("ruleAction").item(intCountTeam).getChildNodes().item(intCountTeamItem).getNodeName().toString()+">";
				intCountTeamItem += 1;
			}
			System.out.println("MoodlePrepareSetup:theBodyMoodleSetUp:team:"+new Integer(intCountTeam).toString()+","+new Integer(intCountTeamItem));
			intCountTeamItem=0;
			intCountTeam += 1;
			theBodyMoodleSetUp += "</team>";
		}		

		theBodyMoodleSetUp += "</env:Body></env:Envelope>";
		
		System.out.println("MoodlePrepareSetup:theBodyMoodleSetUp:"+theBodyMoodleSetUp);
		
		context.setVariable("theBodyMoodleSetUp", theBodyMoodleSetUp, token);

		context.setVariable("emailOutAddress", "giuseppe.repole@itconnectitalia.it", token);

		//context.createVariable("theBodyMoodle","",token);
		
	}

}
