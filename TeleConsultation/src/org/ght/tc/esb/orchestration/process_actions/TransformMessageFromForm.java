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



public class TransformMessageFromForm implements ActionHandler 
{
	private static final long serialVersionUID = 1L;

	Long startFrom;
	DocumentBuilder parser = null;
	
	public void execute(ExecutionContext exCtx) throws Exception 
	{
		Token token = exCtx.getToken();
		ContextInstance context = token.getProcessInstance().getContextInstance();		
		String incidentAction= (String)context.getVariable("incidentAction",token);
		String incidentType= (String)context.getVariable("incidentType",token);
		String incidentSeverity= (String)context.getVariable("incidentSeverity",token);
		String TicketType= (String)context.getVariable("TicketType",token);
		String devicePlatform= (String)context.getVariable("devicePlatform",token);
		String deviceOS= (String)context.getVariable("deviceOS",token);
		String deviceIP= (String)context.getVariable("deviceIP",token);
		String weekDay= (String)context.getVariable("weekDay",token);
		String timeTable= (String)context.getVariable("timeTable",token);
		
		
		
		
		

		String theBody	= "<env:Envelope xmlns:env='http://schemas.xmlsoap.org/soap/envelope/'><env:Header></env:Header><env:Body>";
		theBody	+= "<incidentType>"+incidentType+"</incidentType>";
		theBody	+= "<incidentSeverity>"+incidentSeverity+"</incidentSeverity>";
		theBody	+= "<TicketType>"+TicketType+"</TicketType>";
		theBody	+= "<incidentAction>"+incidentAction+"</incidentAction>";
		theBody	+= "<devicePlatform>"+devicePlatform+"</devicePlatform>";
		theBody	+= "<deviceOS>"+deviceOS+"</deviceOS>";
		theBody	+= "<deviceIP>"+deviceIP+"</deviceIP>";
		theBody	+= "<timeTable>"+timeTable+"</timeTable>";
		theBody	+= "<weekDay>"+weekDay+"</weekDay>";
		theBody	+= "</env:Body></env:Envelope>";
		
		System.out.println("TransformMessageFromForm:theBody:"+theBody);
		
		context.setVariable("theBody", theBody, token);
		
	}

}
