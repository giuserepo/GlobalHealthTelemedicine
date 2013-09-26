package org.ght.tc.esb.orchestration.store;

import java.io.Serializable;

public class SocMessage implements Serializable {
	   private static final long serialVersionUID = 1L;

		private int incidentSeverity;
		private String incidentType;
		private String incidentAction;
		
		public int getIncidentSeverity() {
			return incidentSeverity;
		}
		public void setIncidentSeverity(int incidentSeverity) {
			//System.out.println("**** position: " + position);
			this.incidentSeverity = incidentSeverity;
		}
		
		public String getIncidentType() {
			return incidentType;
		}
		public void setIncidentType(String incidentType) {
			//System.out.println("**** position: " + position);
			this.incidentType = incidentType;
		}
		public String getIncidentAction() {
			return incidentAction;
		}
		public void setIncidentAction(String incidentAction) {
			//System.out.println("**** position: " + position);
			this.incidentAction = incidentAction;
		}		
		
}
