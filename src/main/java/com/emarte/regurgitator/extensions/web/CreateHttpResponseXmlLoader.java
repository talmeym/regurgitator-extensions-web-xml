package com.emarte.regurgitator.extensions.web;

import com.emarte.regurgitator.core.*;
import org.dom4j.Element;

import java.util.Set;

import static com.emarte.regurgitator.extensions.web.ExtensionsWebConfigConstants.CONTENT_TYPE;
import static com.emarte.regurgitator.extensions.web.ExtensionsWebConfigConstants.STATUS_CODE;

public class CreateHttpResponseXmlLoader implements XmlLoader<CreateHttpResponse> {
	CreateResponseXmlLoader createResponseXmlLoader = new CreateResponseXmlLoader();

	@Override
	public CreateHttpResponse load(Element element, Set<Object> allIds) throws RegurgitatorException {
		CreateResponse response = (CreateResponse) createResponseXmlLoader.load(element, allIds);
		String statusCodeStr = element.attributeValue(STATUS_CODE);
		long statusCode = statusCodeStr != null ? Long.parseLong(statusCodeStr) : -1l;
		String contentType = element.attributeValue(CONTENT_TYPE);
		return new CreateHttpResponse(response, statusCode, contentType);
	}
}
