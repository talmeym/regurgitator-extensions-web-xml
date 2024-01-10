/*
 * Copyright (C) 2017 Miles Talmey.
 * Distributed under the MIT License (license terms are at http://opensource.org/licenses/MIT).
 */
package uk.emarte.regurgitator.extensions.web;

import org.w3c.dom.Element;
import uk.emarte.regurgitator.core.*;

import java.util.Set;

import static uk.emarte.regurgitator.core.CoreConfigConstants.SOURCE;
import static uk.emarte.regurgitator.core.XmlConfigUtil.loadId;
import static uk.emarte.regurgitator.core.XmlConfigUtil.loadOptionalStr;
import static uk.emarte.regurgitator.extensions.web.ExtensionsWebConfigConstants.PATH_PREFIX;

public class CreateFileResponseXmlLoader implements XmlLoader<CreateFileResponse> {
    private static final Log log = Log.getLog(CreateFileResponseXmlLoader.class);

    @Override
    public CreateFileResponse load(Element element, Set<Object> allIds) throws RegurgitatorException {
        String id = loadId(element, allIds);
        String source = loadOptionalStr(element, SOURCE);
        String pathPrefix = loadOptionalStr(element, PATH_PREFIX);
        ContextLocation location = source != null ? new ContextLocation(source) : null;
        log.debug("Loaded file response '{}'", id);
        return new CreateFileResponse(id, new ValueSource(location, null), pathPrefix);
    }
}
