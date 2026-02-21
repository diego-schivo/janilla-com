package com.janilla.janillacom.fullstack;

import java.nio.file.Path;
import java.util.List;
import java.util.stream.Stream;

import com.janilla.blanktemplate.fullstack.BlankFullstack;
import com.janilla.ioc.DiFactory;
import com.janilla.janillacom.backend.JanillaBackend;
import com.janilla.janillacom.frontend.JanillaFrontend;
import com.janilla.java.Java;
import com.janilla.websitetemplate.fullstack.WebsiteFullstack;

public class JanillaFullstack extends WebsiteFullstack {

	public static void main(String[] args) {
		IO.println(ProcessHandle.current().pid());
		var f = new DiFactory(Stream
				.of(BlankFullstack.class.getPackageName(), WebsiteFullstack.class.getPackageName(),
						JanillaFullstack.class.getPackageName())
				.flatMap(x -> Java.getPackageClasses(x, false).stream()).toList(), "fullstack");
		try {
			serve(f, JanillaFullstack.class, args.length > 0 ? args[0] : null);
		} catch (Throwable t) {
			t.printStackTrace();
		}
	}

	public JanillaFullstack(DiFactory diFactory, Path configurationFile) {
		super(diFactory, configurationFile, "janilla-com");
	}

	@Override
	protected List<Class<?>> backendTypes() {
		return Stream.concat(super.backendTypes().stream(),
				Stream.of("com.janilla.janillacom.base", JanillaBackend.class.getPackageName(),
						JanillaFullstack.class.getPackageName())
						.flatMap(x -> Java.getPackageClasses(x, false).stream()))
				.toList();
	}

	@Override
	protected List<Class<?>> frontendTypes() {
		return Stream.concat(super.frontendTypes().stream(),
				Stream.of(JanillaFrontend.class.getPackageName(), JanillaFullstack.class.getPackageName())
						.flatMap(x -> Java.getPackageClasses(x, false).stream()))
				.toList();
	}
}
