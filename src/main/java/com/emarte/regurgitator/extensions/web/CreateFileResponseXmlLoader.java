/*
 * Copyright (C) 2017 Miles Talmey.
 * Distributed under the MIT License (license terms are at http://opensource.org/licenses/MIT).
 */
package com.emarte.regurgitator.extensions.web;

import com.emarte.regurgitator.core.*;
import org.w3c.dom.Element;

import java.util.Set;

import static com.emarte.regurgitator.core.CoreConfigConstants.SOURCE;
import static com.emarte.regurgitator.core.XmlConfigUtil.getAttribute;
import static com.emarte.regurgitator.core.XmlConfigUtil.loadId;
import static com.emarte.regurgitator.extensions.web.WebConfigConstants.PATH_PREFIX;

public class CreateFileResponseXmlLoader implements XmlLoader<CreateFileResponse> {
    private static final Log log = Log.getLog(CreateFileResponseXmlLoader.class);

    @Override
    public CreateFileResponse load(Element element, Set<Object> allIds) throws RegurgitatorException {
        String id = loadId(element, allIds);
        String source = getAttribute(element, SOURCE);
        String pathPrefix = getAttribute(element, PATH_PREFIX);
        ContextLocation location = source != null ? new ContextLocation(source) : null;
        log.debug("Loaded file response '{}'", id);
        return new CreateFileResponse(id, new ValueSource(location, null), pathPrefix);
    }
}
