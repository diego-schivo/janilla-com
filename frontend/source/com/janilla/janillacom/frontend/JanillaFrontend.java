package com.janilla.janillacom.frontend;

import java.net.InetSocketAddress;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import javax.net.ssl.SSLContext;

import com.janilla.blanktemplate.frontend.BlankFrontend;
import com.janilla.http.HttpServer;
import com.janilla.ioc.DiFactory;
import com.janilla.java.Java;
import com.janilla.websitetemplate.frontend.WebsiteFrontend;

public class JanillaFrontend extends WebsiteFrontend {

	public static void main(String[] args) {
		try {
			JanillaFrontend a;
			{
				var f = new DiFactory(Stream.of(WebsiteFrontend.class, JanillaFrontend.class)
						.flatMap(x -> Java.getPackageClasses(x.getPackageName(), true).stream()).toList());
//						INSTANCE::get);
				a = f.create(JanillaFrontend.class,
						Java.hashMap("diFactory", f, "configurationFile",
								args.length > 0 ? Path.of(
										args[0].startsWith("~") ? System.getProperty("user.home") + args[0].substring(1)
												: args[0])
										: null));
			}

			SSLContext c;
			{
				var p = a.configuration.getProperty(a.configurationKey() + ".frontend.server.keystore.path");
				var w = a.configuration.getProperty(a.configurationKey() + ".frontend.server.keystore.password");
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
				var p = Integer.parseInt(a.configuration.getProperty(a.configurationKey() + ".frontend.server.port"));
				s = a.diFactory.create(HttpServer.class,
						Map.of("sslContext", c, "endpoint", new InetSocketAddress(p), "handler", a.handler));
			}
			s.serve();
		} catch (Throwable e) {
			e.printStackTrace();
		}
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
