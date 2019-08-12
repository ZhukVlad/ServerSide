package comarch.mediation.ServerSide.main;

import static comarch.mediation.ServerSide.util.ConfigUtil.CACHE_SIZE;
import static comarch.mediation.ServerSide.util.ConfigUtil.RATE;
import static comarch.mediation.ServerSide.handlers.CustomHandlers.timed;

import java.util.Map;
import java.util.Timer;

import org.jooq.lambda.Unchecked;

import comarch.mediation.ServerSide.util.ConfigUtil;
import comarch.mediation.ServerSide.core.AlarmGenerator;
import comarch.mediation.ServerSide.core.HttpClient;
import comarch.mediation.ServerSide.core.Json;
import comarch.mediation.ServerSide.core.SimpleServer;
import comarch.mediation.ServerSide.handlers.CustomHandlers;
import comarch.mediation.ServerSide.handlers.Middleware;
import comarch.mediation.ServerSide.model.Alarm;
import comarch.mediation.ServerSide.service.AlarmRequests;
import comarch.mediation.ServerSide.service.AlarmRoutes;
import comarch.mediation.ServerSide.util.LoggerUtil;
import io.undertow.server.HttpHandler;
import io.undertow.server.HttpServerExchange;
import io.undertow.server.RoutingHandler;
import io.undertow.util.StatusCodes;
import okhttp3.HttpUrl;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class RestServer {
	
	private final String host;
	private final OkHttpClient client;
	
	public RestServer(String host, OkHttpClient client) {
        super();
        this.host = host;
        this.client = client;
    }
	
	public Alarm sendAlarm(Alarm generatedAlarm) {
		HttpUrl route = HttpUrl.parse(host + "/alarm");
		Request request = new Request.Builder()
				.url(route)
				.post(RequestBody.create(MediaType.parse("application/json"), Json.serializer().toString(generatedAlarm)))
				.build();
		return Unchecked.supplier(() -> {
            try (Response response = client.newCall(request).execute()) {
                if (response.isSuccessful()) {
                    return generatedAlarm;
                }

                if (response.code() == StatusCodes.NOT_FOUND) {
                    return null;
                }
                throw HttpClient.unknownException(response);
            }
        }).get();
	}
	
	public static final RoutingHandler ROUTES = new RoutingHandler()
			.get("/alarms", timed("listUsers", AlarmRoutes::listAlarms))
			.post("/subscription", timed("subscribe", AlarmRoutes::subscribe))
			.get("/status", timed("status", AlarmRoutes::status));

	private static final HttpHandler EXCEPTION_THROWER = (HttpServerExchange exchange) -> {
		new AlarmRequests().exception(exchange);
		ROUTES.handleRequest(exchange);
	};

	public static final HttpHandler ROOT = CustomHandlers.exception(EXCEPTION_THROWER);

	public static void main(String[] args) {
		SimpleServer server = SimpleServer.simpleServer(Middleware.common(ROOT));
		server.start();
		LoggerUtil.info("Server started...");
		
		Map<String, Object> config = new ConfigUtil().getConfig(ConfigUtil.MEDIATION_FILE);
		Timer timer = new Timer(true);
		timer.schedule(new AlarmGenerator(Long.parseLong(config.get(CACHE_SIZE).toString())), 0,
				Long.parseLong(config.get(RATE).toString()));
	}
}
