package comarch.mediation.ServerSide.service;

import com.fasterxml.jackson.core.type.TypeReference;

import comarch.mediation.ServerSide.core.Exchange;
import io.undertow.server.HttpServerExchange;

public class AlarmRequests {
	
	public String clientHost(HttpServerExchange exchange) {
		String test = Exchange.body().parseJson(exchange, new TypeReference<String>() {
		});
		return test;
	}
	
	public void exception(HttpServerExchange exchange) {
        boolean exception = Exchange.queryParams()
                                    .queryParamAsBoolean(exchange, "exception")
                                    .orElse(false);
        if (exception) {
            throw new RuntimeException("RuntimeException!");
        }
    }
}
