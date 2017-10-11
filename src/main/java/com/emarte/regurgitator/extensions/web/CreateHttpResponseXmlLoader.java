/*
 * Copyright (C) 2017 Miles Talmey.
 * Distributed under the MIT License (license terms are at http://opensource.org/licenses/MIT).
 */
package com.emarte.regurgitator.extensions.web;

import com.emarte.regurgitator.core.*;
import org.w3c.dom.Element;

import java.util.Set;

import static com.emarte.regurgitator.core.XmlConfigUtil.getAttribute;
import static com.emarte.regurgitator.extensions.web.ExtensionsWebConfigConstants.CONTENT_TYPE;
import static com.emarte.regurgitator.extensions.web.ExtensionsWebConfigConstants.STATUS_CODE;

public class CreateHttpResponseXmlLoader implements XmlLoader<CreateHttpResponse> {
    private final CreateResponseXmlLoader createResponseXmlLoader = new CreateResponseXmlLoader();

    @Override
    public CreateHttpResponse load(Element element, Set<Object> allIds) throws RegurgitatorException {
        CreateResponse response = (CreateResponse) createResponseXmlLoader.load(element, allIds);
        String statusCodeStr = getAttribute(element, STATUS_CODE);
        long statusCode = statusCodeStr != null ? Long.parseLong(statusCodeStr) : -1L;
        String contentType = getAttribute(element, CONTENT_TYPE);
        return new CreateHttpResponse(response, statusCode, contentType);
    }
}
