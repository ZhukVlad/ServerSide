package comarch.mediation.ServerSide.model;

import java.util.Date;

import com.fasterxml.jackson.core.type.TypeReference;

import comarch.mediation.ServerSide.util.ConfigUtil;
import comarch.mediation.ServerSide.util.FormattingUtil;

public class Alarm {
	private Long id;
	private Date eventTime;
	private int severity;
	private String probablyCause;
	private String managedObject;
	
	public Alarm(PerceivedSeverity severity, String probablyCause, String managedObject) {
		id = ConfigUtil.id ++;
		eventTime = FormattingUtil.getCurrentDate();
		this.severity = severity.getValue();
		this.probablyCause = probablyCause;
		this.managedObject = managedObject;
	}
	
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	public Date getEventTime() {
		return eventTime;
	}
	
	public void setEventTime(Date eventTime) {
		this.eventTime = eventTime;
	}
	
	public int getSeverity() {
		return severity;
	}
	
	public void setSeverity(int severity) {
		this.severity = severity;
	}
	
	public String getProbableCause() {
		return probablyCause;
	}
	
	public void setProbableCause(String probableCause) {
		this.probablyCause = probableCause;
	}
	
	public String getManagedObject() {
		return managedObject;
	}
	
	public void setManagedObject(String managedObject) {
		this.managedObject = managedObject;
	}
	
	private static final TypeReference<Alarm> typeRef = new TypeReference<Alarm>() {};
    public static TypeReference<Alarm> typeRef() {
        return typeRef;
    }

	@Override
	public String toString() {
		return "[id=" + id + ", eventTime=" + eventTime + ", severity=" + severity + ", probablyCause="
				+ probablyCause + ", managedObject=" + managedObject + "]";
	}
}
