package com.janilla.janillacom.fullstack;

import java.net.InetSocketAddress;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Stream;

import javax.net.ssl.SSLContext;

import com.janilla.http.HttpExchange;
import com.janilla.http.HttpHandler;
import com.janilla.http.HttpServer;
import com.janilla.ioc.DiFactory;
import com.janilla.janillacom.backend.Application;
import com.janilla.janillacom.backend.JanillaBackend;
import com.janilla.janillacom.frontend.JanillaFrontend;
import com.janilla.java.Java;
import com.janilla.java.Reflection;
import com.janilla.websitetemplate.fullstack.WebsiteFullstack;

public class JanillaFullstack extends WebsiteFullstack {

	public static void main(String[] args) {
		try {
			IO.println(ProcessHandle.current().pid());

			JanillaFullstack a;
			{
				var f = new DiFactory(
						Stream.of(WebsiteFullstack.class, JanillaFullstack.class)
								.flatMap(x -> Java.getPackageClasses(x.getPackageName()).stream()).toList(),
						"fullstack");
				a = f.create(JanillaFullstack.class,
						Java.hashMap("diFactory", f, "configurationFile",
								args.length > 0 ? Path.of(
										args[0].startsWith("~") ? System.getProperty("user.home") + args[0].substring(1)
												: args[0])
										: null));
			}

			SSLContext c;
			{
				var p = a.configuration.getProperty(a.configurationKey() + ".fullstack.server.keystore.path");
				var w = a.configuration.getProperty(a.configurationKey() + ".fullstack.server.keystore.password");
				if (p.startsWith("~"))
					p = System.getProperty("user.home") + p.substring(1);
				var f = Path.of(p);
				if (!Files.exists(f))
					Java.generateKeyPair(f, w);
				try (var s = Files.newInputStream(f)) {
					c = Java.sslContext(s, w.toCharArray());
				}
			}

			HttpServer s;
			{
				var p = Integer.parseInt(a.configuration.getProperty(a.configurationKey() + ".fullstack.server.port"));
				s = a.diFactory.create(HttpServer.class,
						Map.of("sslContext", c, "endpoint", new InetSocketAddress(p), "handler", a.handler));
			}
			s.serve();
		} catch (Throwable e) {
			e.printStackTrace();
		}
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
					var c = Class.forName(a.fullstack());
					var f = new DiFactory(Stream.of("com.janilla.web", c.getPackageName())
							.flatMap(x -> Java.getPackageClasses(x).stream()).toList(), "fullstack");
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
						.flatMap(x -> Java.getPackageClasses(x).stream()))
				.toList();
	}

	@Override
	protected List<Class<?>> frontendTypes() {
		return Stream.concat(super.frontendTypes().stream(),
				Stream.of(JanillaFrontend.class.getPackageName(), JanillaFullstack.class.getPackageName())
						.flatMap(x -> Java.getPackageClasses(x).stream()))
				.toList();
	}

	@Override
	protected boolean handle(HttpExchange exchange) {
		var a = application(exchange.request().getAuthority());
		return a == this ? super.handle(exchange)
				: ((HttpHandler) Reflection.property(a.getClass(), "handler").get(a)).handle(exchange);
	}
}
