package comarch.mediation.ServerSide.handlers;

import comarch.mediation.ServerSide.core.MiddlewareBuilder;
import comarch.mediation.ServerSide.handlers.ReferrerPolicyHandlers.ReferrerPolicy;
import io.undertow.server.HttpHandler;
import io.undertow.server.handlers.BlockingHandler;

public class Middleware {
	public static HttpHandler common(HttpHandler root) {
		return MiddlewareBuilder.begin(
				handler -> CustomHandlers.securityHeaders(handler, ReferrerPolicy.STRICT_ORIGIN_WHEN_CROSS_ORIGIN))
				.next(BlockingHandler::new)
				.complete(root);
	}
}
