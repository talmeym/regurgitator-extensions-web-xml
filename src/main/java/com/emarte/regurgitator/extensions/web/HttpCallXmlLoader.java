package com.emarte.regurgitator.extensions.web;

import com.emarte.regurgitator.core.*;
import org.dom4j.Element;

import java.util.*;

import static com.emarte.regurgitator.core.XmlConfigUtil.*;
import static com.emarte.regurgitator.extensions.web.WebConfigConstants.*;
import static java.lang.Integer.parseInt;

public class HttpCallXmlLoader implements XmlLoader<Step> {
    private static final Log log = Log.getLog(HttpCallXmlLoader.class);
	private static final XmlLoaderUtil<XmlLoader<Step>> loaderUtil = new XmlLoaderUtil<XmlLoader<Step>>();

    @Override
    public Step load(Element element, Set<Object> allIds) throws RegurgitatorException {
        String id = loadId(element, allIds);

		List<Step> steps = new ArrayList<Step>();

		for(Iterator<Element> iterator = element.elementIterator(); iterator.hasNext(); ) {
			Element stepElement = iterator.next();
			steps.add(loaderUtil.deriveLoader(stepElement).load(stepElement, allIds));
		}

		String username = element.attributeValue(USERNAME);
		String password = element.attributeValue(PASSWORD);

		if((username == null && password != null) || (username != null && password == null)) {
			throw new RegurgitatorException("Both username and password (or neither) required");
		}

		log.debug("Loaded HttpCall '" + id + "'");
        return new HttpCall(id, new HttpMessageProxy(new HttpClientWrapper(element.attributeValue(HOST), parseInt(element.attributeValue(PORT)), username, password)), steps);
    }
}
