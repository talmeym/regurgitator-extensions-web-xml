package com.emarte.regurgitator.extensions.web;

import com.emarte.regurgitator.core.*;
import org.dom4j.Element;

import java.util.Set;

import static com.emarte.regurgitator.core.Log.getLog;
import static com.emarte.regurgitator.extensions.web.ExtensionsWebConfigConstants.KEY;

public class QueryParamProcessorXmlLoader implements XmlLoader<QueryParamProcessor> {
	public static final Log log = getLog(QueryParamProcessorXmlLoader.class);

	@Override
	public QueryParamProcessor load(Element element, Set<Object> allIds) throws RegurgitatorException {
		log.debug("Loaded QueryParamProcessor");
		return new QueryParamProcessor(element.attributeValue(KEY));
	}
}
