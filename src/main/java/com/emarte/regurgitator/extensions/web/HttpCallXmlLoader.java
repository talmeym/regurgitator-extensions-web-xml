/*
 * Copyright (C) 2017 Miles Talmey.
 * Distributed under the MIT License (license terms are at http://opensource.org/licenses/MIT).
 */
package com.emarte.regurgitator.extensions.web;

import com.emarte.regurgitator.core.*;
import org.w3c.dom.Element;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static com.emarte.regurgitator.core.Log.getLog;
import static com.emarte.regurgitator.core.XmlConfigUtil.*;
import static com.emarte.regurgitator.extensions.web.ExtensionsWebConfigConstants.*;
import static java.lang.Integer.parseInt;

public class HttpCallXmlLoader implements XmlLoader<Step> {
    private static final Log log = getLog(HttpCallXmlLoader.class);
    private static final XmlLoaderUtil<XmlLoader<Step>> loaderUtil = new XmlLoaderUtil<XmlLoader<Step>>();

    @Override
    public Step load(Element element, Set<Object> allIds) throws RegurgitatorException {
        String id = loadId(element, allIds);

        List<Step> steps = new ArrayList<Step>();

        for(Element stepElement: getChildElements(element)) {
            steps.add(loaderUtil.deriveLoader(stepElement).load(stepElement, allIds));
        }

        String protocol = loadOptionalStr(element, PROTOCOL);
        String username = loadOptionalStr(element, USERNAME);
        String password = loadOptionalStr(element, PASSWORD);

        if((username == null && password != null) || (username != null && password == null)) {
            throw new RegurgitatorException("Both username and password (or neither) required");
        }

        log.debug("Loaded HttpCall '{}'", id);
        return new HttpCall(id, new HttpMessageProxy(new HttpClientWrapper(protocol != null ? protocol : "http", loadMandatoryStr(element, HOST), parseInt(loadMandatoryStr(element, PORT)), username, password)), steps);
    }
}
