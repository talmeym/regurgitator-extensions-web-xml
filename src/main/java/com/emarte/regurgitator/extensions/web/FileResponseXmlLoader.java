package com.emarte.regurgitator.extensions.web;

import com.emarte.regurgitator.core.*;
import org.dom4j.Element;

import java.util.Set;

import static com.emarte.regurgitator.core.CoreConfigConstants.*;
import static com.emarte.regurgitator.core.XmlConfigUtil.loadId;
import static com.emarte.regurgitator.extensions.web.WebConfigConstants.PATH_PREFIX;

public class FileResponseXmlLoader implements XmlLoader<FileResponse> {
	private static Log log = Log.getLog(FileResponseXmlLoader.class);

	@Override
	public FileResponse load(Element element, Set<Object> allIds) throws RegurgitatorException {
		String id = loadId(element, allIds);
		String source = element.attributeValue(SOURCE);
		String pathPrefix = element.attributeValue(PATH_PREFIX);

		ContextLocation location = source != null ? new ContextLocation(source) : null;
		log.debug("Loaded file response '" + id + "'");
		return new FileResponse(id, new ValueSource(location, null), pathPrefix);
	}
}
