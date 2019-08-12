package comarch.mediation.ServerSide.service;

import java.util.List;

import comarch.mediation.ServerSide.core.AlarmGenerator;
import comarch.mediation.ServerSide.core.Exchange;
import comarch.mediation.ServerSide.core.Json;
import comarch.mediation.ServerSide.model.Alarm;
import comarch.mediation.ServerSide.util.ConfigUtil;
import io.undertow.server.HttpServerExchange;
import io.undertow.util.StatusCodes;

public class AlarmRoutes {
	
	private static AlarmRequests alarmRequests = new AlarmRequests();
	
	public static void listAlarms(HttpServerExchange exchange) {
		List<Alarm> alarms = AlarmGenerator.getCache();
		Exchange.body().sendJson(exchange, alarms);
	}
	
	public static void subscribe(HttpServerExchange exchange) {
		ConfigUtil.clientHost = alarmRequests.clientHost(exchange);
		if (ConfigUtil.clientHost == null) {
			ConfigUtil.isClientRegistered = Boolean.FALSE;
			exchange.setStatusCode(StatusCodes.NOT_FOUND);
			Exchange.body().sendJson(exchange, "Host is empty, not registered");
		} else {
			ConfigUtil.isClientRegistered = Boolean.TRUE;
			exchange.setStatusCode(StatusCodes.OK);
			Exchange.body().sendJson(exchange, Json.serializer().toPrettyString("Subscription successful"));
		}
	}
	
	public static void status(HttpServerExchange exchange) {
		exchange.setStatusCode(StatusCodes.OK);
		Exchange.body().sendJson(exchange, Json.serializer().toPrettyString("Server is running"));
	}
}
