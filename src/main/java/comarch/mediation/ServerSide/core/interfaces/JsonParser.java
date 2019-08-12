package comarch.mediation.ServerSide.core.interfaces;

import com.fasterxml.jackson.core.type.TypeReference;

import comarch.mediation.ServerSide.core.Json;
import io.undertow.server.HttpServerExchange;

public interface JsonParser {
	default <T> T parseJson(HttpServerExchange exchange, TypeReference<T> typeRef) {
        return Json.serializer().fromInputStream(exchange.getInputStream(), typeRef);
    }
}
