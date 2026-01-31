package com.janilla.janillacom.frontend;

import java.util.Map;
import java.util.Properties;
import java.util.stream.Stream;

import com.janilla.blanktemplate.frontend.Index.Template;
import com.janilla.web.ResourceMap;
import com.janilla.websitetemplate.frontend.WebsiteDataFetching;
import com.janilla.websitetemplate.frontend.WebsiteIndexFactory;

public class IndexFactory extends WebsiteIndexFactory {

	public IndexFactory(Properties configuration, String configurationKey, WebsiteDataFetching dataFetching,
			ResourceMap resourceMap) {
		super(configuration, configurationKey, dataFetching, resourceMap);
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
		Stream.of("link", "post").map(this::janillaImportKey).forEach(x -> map.put(x, "/" + x + ".js"));
	}

	@Override
	protected String websiteImportKey(String name) {
		return "website/" + name;
	}

	protected String janillaImportKey(String name) {
		return name;
	}
}
