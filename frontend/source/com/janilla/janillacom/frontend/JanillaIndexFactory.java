package com.janilla.janillacom.frontend;

import java.util.Map;
import java.util.Properties;
import java.util.stream.Stream;

import com.janilla.frontend.Template;
import com.janilla.frontend.cms.CmsDataFetching;
import com.janilla.web.ResourceMap;
import com.janilla.websitetemplate.frontend.WebsiteIndexFactory;

public class JanillaIndexFactory extends WebsiteIndexFactory {

	public JanillaIndexFactory(ResourceMap resourceMap, CmsDataFetching dataFetching, Properties configuration,
			String configurationKey) {
		super(resourceMap, dataFetching, configuration, configurationKey);
	}

	@Override
	public Template websiteTemplate(String name) {
		return template("website/" + name);
	}

	public Template janillaTemplate(String name) {
		return template(name);
	}

	@Override
	protected void putImports(Map<String, String> map) {
		super.putImports(map);
		Stream.of("header", "link", "post").map(this::janillaImportKey).forEach(x -> map.put(x, "/" + x + ".js"));
	}

	@Override
	protected String websiteImportKey(String name) {
		return "website/" + name;
	}

	protected String janillaImportKey(String name) {
		return name;
	}
}
