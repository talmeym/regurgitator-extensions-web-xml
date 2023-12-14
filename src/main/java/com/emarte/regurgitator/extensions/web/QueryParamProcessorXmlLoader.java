/*
 * Copyright (C) 2017 Miles Talmey.
 * Distributed under the MIT License (license terms are at http://opensource.org/licenses/MIT).
 */
package com.emarte.regurgitator.extensions.web;

import com.emarte.regurgitator.core.Log;
import com.emarte.regurgitator.core.XmlLoader;
import org.w3c.dom.Element;

import java.util.Set;

import static com.emarte.regurgitator.core.Log.getLog;
import static com.emarte.regurgitator.core.XmlConfigUtil.loadOptionalStr;
import static com.emarte.regurgitator.extensions.web.ExtensionsWebConfigConstants.KEY;

public class QueryParamProcessorXmlLoader implements XmlLoader<QueryParamProcessor> {
    private static final Log log = getLog(QueryParamProcessorXmlLoader.class);

    @Override
    public QueryParamProcessor load(Element element, Set<Object> allIds) {
        log.debug("Loaded QueryParamProcessor");
        return new QueryParamProcessor(loadOptionalStr(element, KEY));
    }
}
