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



public class TransformInternalMessage implements ActionHandler 
{
	private static final long serialVersionUID = 1L;

	Long startFrom;
	DocumentBuilder parser = null;
	
	public void execute(ExecutionContext exCtx) throws Exception 
	{
		Token token = exCtx.getToken();
		ContextInstance context = token.getProcessInstance().getContextInstance();
		String theBody	= (String)context.getVariable("theBody",token);
		System.out.println("TransformInternalMessage:theBody:"+theBody);
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder parser = factory.newDocumentBuilder();

		StringReader reader = new StringReader( theBody );
		InputSource inputSource = new InputSource( reader );
		Document doc = parser.parse( inputSource ); 
		String incidentType= doc.getElementsByTagName("incidentType").item(0).getTextContent();
		String incidentSeverity= doc.getElementsByTagName("incidentSeverity").item(0).getTextContent();
		String incidentAction= doc.getElementsByTagName("incidentAction").item(0).getTextContent();

		String incidentActionAppo= (String)context.getVariable("incidentAction",token);
		String incidentTypeAppo= (String)context.getVariable("incidentType",token);
		String incidentSeverityAppo= (String)context.getVariable("incidentSeverity",token);
		String TicketTypeAppo= (String)context.getVariable("TicketType",token);
		

		String theBodyAppo	= "<env:Envelope xmlns:env='http://schemas.xmlsoap.org/soap/envelope/'><env:Header></env:Header><env:Body><incidentType>"+incidentTypeAppo+"</incidentType><incidentSeverity>"+incidentSeverityAppo+"</incidentSeverity><TicketType>"+TicketTypeAppo+"</TicketType><incidentAction>"+incidentActionAppo+"</incidentAction></env:Body></env:Envelope>";
		
		System.out.println("TransformInternalMessage:theBodyAppo:"+theBodyAppo);		
		//context.setVariable("counter", counter , token);
		//context.setVariable("incidentSeverity", "101");
		context.createVariable("incidentSeverityVer", incidentSeverity, token);
		context.createVariable("incidentTypeVer", incidentType, token);
		context.createVariable("incidentSeverityVer", incidentSeverity, token);
		context.createVariable("TicketTypeVer", TicketTypeAppo, token);		
		context.createVariable("incidentActionVer", incidentActionAppo, token);
		
	}

}
