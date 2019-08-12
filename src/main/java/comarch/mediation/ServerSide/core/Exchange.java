package comarch.mediation.ServerSide.core;

import comarch.mediation.ServerSide.core.interfaces.ContentTypeSenders;
import comarch.mediation.ServerSide.core.interfaces.JsonParser;
import comarch.mediation.ServerSide.core.interfaces.JsonSender;
import comarch.mediation.ServerSide.core.interfaces.QueryParams;

public class Exchange {
	public static interface BodyImpl extends ContentTypeSenders, JsonSender, JsonParser {
	};

	private static final BodyImpl BODY = new BodyImpl() {
	};

	public static BodyImpl body() {
		return BODY;
	}

	public static interface QueryParamImpl extends QueryParams {
	};

	private static final QueryParamImpl QUERYPARAMS = new QueryParamImpl() {
	};

	public static QueryParamImpl queryParams() {
		return QUERYPARAMS;
	}

}
