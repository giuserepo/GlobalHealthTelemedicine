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



public class TransformExternalMessage implements ActionHandler 
{
	private static final long serialVersionUID = 1L;

	Long startFrom;
	DocumentBuilder parser = null;
	public void execute(ExecutionContext exCtx) throws Exception 
	{
		Document docIn;
		StringReader readerIn=null;
		Token token = exCtx.getToken();
		
		String incidentType= "";
		String incidentSeverity= "";
		String incidentAction= "";	
		String TicketType= "";
		String SocOwner= "";
		ContextInstance context = token.getProcessInstance().getContextInstance();
		String theBody	= (String)context.getVariable("theBody",token);
		
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder parser = factory.newDocumentBuilder();

		StringReader reader = new StringReader( theBody );
		InputSource inputSource = new InputSource( reader );
		Document doc = parser.parse( inputSource );
		//System.out.println("TransformExternalMessage:doc.getElementsByTagName(return):"+Integer.toString(doc.getElementsByTagName("return").getLength()));
		incidentType= doc.getElementsByTagName("incidentType").item(0).getTextContent();
		incidentSeverity= doc.getElementsByTagName("incidentSeverity").item(0).getTextContent();
		if (doc.getElementsByTagName("incidentAction").getLength() > 0 ) {
			incidentAction= doc.getElementsByTagName("incidentAction").item(0).getTextContent();	
		 } else {
			incidentAction= "";
		 }
		if (doc.getElementsByTagName("TicketType").getLength() > 0 ) {
			TicketType= doc.getElementsByTagName("TicketType").item(0).getTextContent();	
		 } else {
			TicketType= "";
		}
		if (doc.getElementsByTagName("SocOwner").getLength() > 0 ) {
			SocOwner= doc.getElementsByTagName("SocOwner").item(0).getTextContent();	
		 } else {
			SocOwner= "";
		}		
		String newTheBody = "<env:Envelope xmlns:env='http://schemas.xmlsoap.org/soap/envelope/'><env:Header></env:Header><env:Body>";
		newTheBody += "<incidentType>"+incidentType+"</incidentType>";
		newTheBody += "<incidentSeverity>"+incidentSeverity+"</incidentSeverity>";
		newTheBody += "<TicketType>"+TicketType+"</TicketType>";
		newTheBody += "<SocOwner>"+SocOwner+"</SocOwner>";		
		newTheBody += "</env:Body></env:Envelope>";
		reader.close();
		if ( readerIn != null){
			readerIn.close();
		}
		System.out.println("TransformExternalMessage:newTheBody:"+newTheBody);
		context.setVariable("theBody", newTheBody , token);
		//context.setVariable("incidentSeverity", "101");
		context.createVariable("incidentSeverity", incidentSeverity, token);
		context.createVariable("incidentType", incidentType, token);
		context.createVariable("incidentAction", incidentAction, token);
		context.createVariable("TicketType", TicketType, token);
		context.createVariable("SocOwner", SocOwner, token);		
	
	}
/*	
	public void execute(ExecutionContext exCtx) throws Exception 
	{
		Document docIn;
		StringReader readerIn=null;
		Token token = exCtx.getToken();
		
		String incidentType= "";
		String incidentSeverity= "";
		String incidentAction= "";		
		ContextInstance context = token.getProcessInstance().getContextInstance();
		String theBody	= (String)context.getVariable("theBody",token);
		
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder parser = factory.newDocumentBuilder();

		StringReader reader = new StringReader( theBody );
		InputSource inputSource = new InputSource( reader );
		Document doc = parser.parse( inputSource );
		System.out.println("TransformActionHandler:doc.getElementsByTagName(return):"+Integer.toString(doc.getElementsByTagName("return").getLength()));
		if (doc.getElementsByTagName("return").getLength()>0) {
			 DocumentBuilder parserIn = factory.newDocumentBuilder();
			 System.out.println("TransformActionHandler:theBody:"+theBody);		 
			 String returnString= StringEscapeUtils.unescapeHtml(doc.getElementsByTagName("return").item(0).getTextContent());
	
			 System.out.println("TransformActionHandler:returnString:"+returnString);
			
			 
			 String messageString = "<body>"+returnString+"</body>";
			 //String messageString = returnString;
			 
			 System.out.println("TransformActionHandler:messageString:"+messageString);
			 
			 readerIn = new StringReader( messageString );
			 System.out.println("TransformActionHandler:readerIn:"+readerIn.toString());
			 InputSource inputSourceIn = new InputSource( readerIn );
			 System.out.println("TransformActionHandler:inputSourceIn:"+inputSourceIn.toString());
			 docIn = parserIn.parse( inputSourceIn );
			 
			 System.out.println("TransformActionHandler:docIn:"+docIn.getTextContent());

				incidentType= docIn.getElementsByTagName("incident-type").item(0).getTextContent();
				incidentSeverity= docIn.getElementsByTagName("risk-scale").item(0).getTextContent();
				incidentAction= "";	
		} else {
			incidentType= doc.getElementsByTagName("incidentType").item(0).getTextContent();
			incidentSeverity= doc.getElementsByTagName("incidentSeverity").item(0).getTextContent();
			if (doc.getElementsByTagName("incidentAction").getLength() > 0 ) {
				incidentAction= doc.getElementsByTagName("incidentAction").item(0).getTextContent();	
			 } else {
				incidentAction= "";
			 }
			
		}
		
		String newTheBody = "<env:Envelope xmlns:env='http://schemas.xmlsoap.org/soap/envelope/'><env:Header></env:Header><env:Body>";
		newTheBody += "<incidentType>"+incidentType+"</incidentType>";
		newTheBody += "<incidentSeverity>"+incidentSeverity+"</incidentSeverity>";
		newTheBody += "</env:Body></env:Envelope>";
		reader.close();
		if ( readerIn != null){
			readerIn.close();
		}
		
		context.setVariable("theBody", newTheBody , token);
		//context.setVariable("incidentSeverity", "101");
		context.createVariable("incidentSeverity", incidentSeverity, token);
		context.createVariable("incidentType", incidentType, token);
		context.createVariable("incidentAction", incidentAction, token);
		
	}
*/
}
