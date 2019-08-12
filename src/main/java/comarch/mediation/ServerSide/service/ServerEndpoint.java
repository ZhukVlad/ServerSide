/*package comarch.mediation.ServerSide.service;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import comarch.mediation.ServerSide.core.AlarmGenerator;
import comarch.mediation.ServerSide.model.Alarm;

@Path("serverendpoint")
public class ServerEndpoint {
	
	@GET
	@Path("/alarms")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response sendAllAlarms(){
		return generateResponse(AlarmGenerator.getCache());
	}
	
	@POST
	@Path("/subscribe")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response subscribe() {
		return generateResponse("Registered");
	}
	
	@GET
	@Path("/status")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response status(){
		return generateResponse("Server is running...");
	}
	
	private Response generateResponse(List<Alarm> alarms) {
		final GenericEntity<List<Alarm>> entity = new GenericEntity<List<Alarm>>(
				alarms) {
		};
		return Response.ok().type(MediaType.APPLICATION_JSON).entity(entity).build();
	}
	
	private Response generateResponse(String status) {
		final GenericEntity<String> entity = new GenericEntity<String>(
				status) {
		};
		return Response.ok().type(MediaType.APPLICATION_JSON).entity(entity).build();
	}
}
*/