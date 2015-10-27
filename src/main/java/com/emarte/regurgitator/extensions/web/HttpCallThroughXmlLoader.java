package com.emarte.regurgitator.extensions.web;

import com.emarte.regurgitator.core.*;
import org.dom4j.Element;

import java.util.Set;

import static com.emarte.regurgitator.core.XmlConfigUtil.*;
import static com.emarte.regurgitator.extensions.web.WebConfigConstants.*;
import static java.lang.Integer.parseInt;

public class HttpCallThroughXmlLoader implements XmlLoader<Step> {
    private static Log log = Log.getLog(HttpCallThroughXmlLoader.class);
	private static final XmlLoaderUtil<XmlLoader<Step>> loaderUtil = new XmlLoaderUtil<XmlLoader<Step>>();

    @Override
    public Step load(Element element, Set<Object> allIds) throws RegurgitatorException {
        String id = loadId(element, allIds);

		String host = loadStringFromElementOrAttribute(element, HOST);
		String port = loadStringFromElementOrAttribute(element, PORT);

		Element processResponseElement = element.element(PROCESS_RESPONSE);
		Step responseProcessing = null;

		if(processResponseElement != null) {
			Element innerElement = getChild(processResponseElement);
			responseProcessing = loaderUtil.deriveLoader(innerElement).load(innerElement, allIds);
		}

		log.debug("Loaded HttpCallThrough '" + id + "'");
        return new HttpCallThrough(id, new HttpMessageProxy(host, parseInt(port)), responseProcessing);
    }
}
