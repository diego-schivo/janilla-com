package com.janilla.janillacom.fullstack;

import java.net.URISyntaxException;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Stream;

import com.janilla.blanktemplate.fullstack.BlankFullstack;
import com.janilla.http.HttpExchange;
import com.janilla.http.HttpHandler;
import com.janilla.ioc.DiFactory;
import com.janilla.janillacom.backend.Application;
import com.janilla.janillacom.backend.JanillaBackend;
import com.janilla.janillacom.frontend.JanillaFrontend;
import com.janilla.java.Java;
import com.janilla.java.Reflection;
import com.janilla.websitetemplate.fullstack.WebsiteFullstack;

public class JanillaFullstack extends WebsiteFullstack {

	public static void main(String[] args) {
		IO.println(ProcessHandle.current().pid());
		var f = new DiFactory(Stream
				.of(BlankFullstack.class.getPackageName(), WebsiteFullstack.class.getPackageName(),
						JanillaFullstack.class.getPackageName())
				.flatMap(x -> Java.getPackageClasses(x, false).stream()).toList(), "fullstack");
		serve(f, JanillaFullstack.class, args.length > 0 ? args[0] : null);
	}

	protected final Map<String, Object> applications = new ConcurrentHashMap<>();

	public JanillaFullstack(DiFactory diFactory, Path configurationFile) {
		this(diFactory, configurationFile, "janilla-com");
	}

	public JanillaFullstack(DiFactory diFactory, Path configurationFile, String configurationKey) {
		super(diFactory, configurationFile, configurationKey);
	}

	public JanillaFullstack application() {
		return this;
	}

	public Object application(String authority) {
		var s = "." + configuration.getProperty(configurationKey + ".authority");
		if (!authority.endsWith(s))
			return this;
		return applications.computeIfAbsent(authority.substring(0, authority.length() - s.length()), k -> {
//			IO.println("JanillaFullstack.application, k=" + k);
			Application a;
			{
				var c = backend().persistence().crud(Application.class);
				a = c.read(c.find("slug", k));
			}
//			IO.println("JanillaFullstack.application, a=" + a);
			if (a != null)
				try {
					var c = Class.forName(a.mainClass());
					var f = new DiFactory(Stream.of("com.janilla.web", c.getPackageName())
							.flatMap(x -> Java.getPackageClasses(x, false).stream()).toList(), "fullstack");
					return f.create(c, Java.hashMap("diFactory", f, "configurationFile",
							Optional.ofNullable(configurationFile).orElseGet(() -> {
								try {
									return Path
											.of(JanillaFullstack.class.getResource("configuration.properties").toURI());
								} catch (URISyntaxException e) {
									throw new RuntimeException(e);
								}
							})));
				} catch (ReflectiveOperationException e) {
					throw new RuntimeException(e);
				}
			return this;
		});
	}

	@Override
	protected List<Class<?>> backendTypes() {
		return Stream.concat(super.backendTypes().stream(),
				Stream.of(JanillaBackend.class.getPackageName(), JanillaFullstack.class.getPackageName())
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

	@Override
	protected boolean handle(HttpExchange exchange) {
		var a = application(exchange.request().getAuthority());
		return a == this ? super.handle(exchange)
				: ((HttpHandler) Reflection.property(a.getClass(), "handler").get(a)).handle(exchange);
	}
}
