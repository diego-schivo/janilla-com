package com.janilla.janillacom.frontend;

import java.util.Map;
import java.util.Properties;
import java.util.stream.Stream;

import com.janilla.web.ResourceMap;
import com.janilla.websitetemplate.frontend.DataFetching;
import com.janilla.websitetemplate.frontend.IndexFactory;

public class CustomIndexFactory extends IndexFactory {

	public CustomIndexFactory(Properties configuration, String configurationKey, DataFetching dataFetching,
			ResourceMap resourceMap) {
		super(configuration, configurationKey, dataFetching, resourceMap);
	}

	@Override
	protected void putImports(Map<String, String> map) {
		super.putImports(map);
		Stream.of("link", "post").forEach(x -> map.put(x, "/custom-" + x + ".js"));
	}
}
