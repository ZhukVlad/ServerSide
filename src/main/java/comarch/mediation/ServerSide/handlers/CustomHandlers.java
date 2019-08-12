package comarch.mediation.ServerSide.handlers;

import comarch.mediation.ServerSide.core.MiddlewareBuilder;
import comarch.mediation.ServerSide.handlers.ReferrerPolicyHandlers.ReferrerPolicy;
import comarch.mediation.ServerSide.util.LoggerUtil;
import io.undertow.Handlers;
import io.undertow.server.HttpHandler;
import io.undertow.server.HttpServerExchange;
import io.undertow.server.handlers.ExceptionHandler;

public class CustomHandlers {

	public static TimingHttpHandler timed(String name, HttpHandler next) {
		return new TimingHttpHandler(next, "routes." + name);
	}
	
	public static ExceptionHandler exception(HttpHandler handler) {
        return Handlers.exceptionHandler((HttpServerExchange exchange) -> {
            try {
                handler.handleRequest(exchange);
            } catch (Throwable th) {
                LoggerUtil.error("exception thrown at " + exchange.getRequestURI(), th);
                throw th;
            }
        });
    }
	
	public static HttpHandler securityHeaders(HttpHandler next, ReferrerPolicy policy) {
        MiddlewareBuilder security = MiddlewareBuilder
            .begin(XFrameOptionsHandlers::deny)
            .next(XXssProtectionHandlers::enableAndBlock)
            .next(XContentTypeOptionsHandler::nosniff)
            .next(handler -> ReferrerPolicyHandlers.policy(handler, policy));

        return security.complete(next);
    }
}
