package com.janilla.janillacom.backend;

import java.nio.file.Path;
import java.util.stream.Stream;

import com.janilla.blanktemplate.backend.BlankBackend;
import com.janilla.ioc.DiFactory;
import com.janilla.java.Java;
import com.janilla.websitetemplate.backend.WebsiteBackend;

public class JanillaBackend extends WebsiteBackend {

	public static void main(String[] args) {
		IO.println(ProcessHandle.current().pid());
		var f = new DiFactory(Stream
				.of("com.janilla.web", BlankBackend.class.getPackageName(), WebsiteBackend.class.getPackageName(),
						JanillaBackend.class.getPackageName())
				.flatMap(x -> Java.getPackageClasses(x, false).stream()).toList());
		serve(f, JanillaBackend.class, args.length > 0 ? args[0] : null);
	}

	public JanillaBackend(DiFactory diFactory, Path configurationFile) {
		this(diFactory, configurationFile, "janilla-com");
	}

	public JanillaBackend(DiFactory diFactory, Path configurationFile, String configurationKey) {
		super(diFactory, configurationFile, configurationKey);
	}

	@Override
	protected Class<?> dataClass() {
		return Data.class;
	}
}
