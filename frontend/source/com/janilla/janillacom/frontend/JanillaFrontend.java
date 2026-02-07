package com.janilla.janillacom.frontend;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import com.janilla.blanktemplate.frontend.BlankFrontend;
import com.janilla.ioc.DiFactory;
import com.janilla.java.Java;
import com.janilla.websitetemplate.frontend.WebsiteFrontend;

public class JanillaFrontend extends WebsiteFrontend {

	public static void main(String[] args) {
		IO.println(ProcessHandle.current().pid());
		var f = new DiFactory(Stream
				.of("com.janilla.web", BlankFrontend.class.getPackageName(), WebsiteFrontend.class.getPackageName(),
						JanillaFrontend.class.getPackageName())
				.flatMap(x -> Java.getPackageClasses(x, false).stream()).toList());
		serve(f, JanillaFrontend.class, args.length > 0 ? args[0] : null);
	}

	public JanillaFrontend(DiFactory diFactory, Path configurationFile) {
		this(diFactory, configurationFile, "janilla-com");
	}

	public JanillaFrontend(DiFactory diFactory, Path configurationFile, String configurationKey) {
		super(diFactory, configurationFile, configurationKey);
	}

	@Override
	protected Map<String, List<Path>> resourcePaths() {
		var pp1 = Java.getPackagePaths("com.janilla.frontend.cms", false).filter(Files::isRegularFile).toList();
		var pp2 = Java.getPackagePaths(BlankFrontend.class.getPackageName(), false).filter(Files::isRegularFile)
				.toList();
		var pp3 = Java.getPackagePaths(WebsiteFrontend.class.getPackageName(), false).filter(Files::isRegularFile)
				.toList();
		var pp4 = Stream
				.of("com.janilla.frontend", "com.janilla.frontend.resources", JanillaFrontend.class.getPackageName())
				.flatMap(x -> Java.getPackagePaths(x, false).filter(Files::isRegularFile)).toList();
		return Map.of("/cms", pp1, "/blank", pp2, "/website", pp3, "", pp4);
	}
}
