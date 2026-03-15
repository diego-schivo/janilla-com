//package com.janilla.janillacom.frontend;
//
//import java.net.URI;
//import java.util.List;
//import java.util.Properties;
//
//import com.janilla.cms.Version;
//import com.janilla.http.HttpClient;
//import com.janilla.http.HttpExchange;
//import com.janilla.http.HttpRequest;
//import com.janilla.janillacom.Application;
//import com.janilla.janillacom.ApplicationApi;
//import com.janilla.java.Converter;
//import com.janilla.java.SimpleParameterizedType;
//import com.janilla.java.UriQueryBuilder;
//import com.janilla.persistence.ListPortion;
//
//public class FrontendApplicationApi implements ApplicationApi {
//
//	protected final Properties configuration;
//
//	protected final HttpClient httpClient;
//
//	public FrontendApplicationApi(Properties configuration, HttpClient httpClient) {
//		this.configuration = configuration;
//		this.httpClient = httpClient;
//	}
//
//	@Override
//	public Application create(Application document) {
//		throw new UnsupportedOperationException();
//	}
//
//	@Override
//	public Application read(Long id, Integer depth, HttpExchange exchange) {
//		throw new UnsupportedOperationException();
//	}
//
//	@Override
//	public ListPortion<Application> read(String search, Boolean reverse, Long skip, Long limit, Integer depth) {
//		throw new UnsupportedOperationException();
//	}
//
//	@Override
//	public Application update(Long id, Application document, Boolean draft, Boolean autosave) {
//		throw new UnsupportedOperationException();
//	}
//
//	@Override
//	public Application delete(Long id) {
//		throw new UnsupportedOperationException();
//	}
//
//	@Override
//	public List<Application> delete(List<Long> ids) {
//		throw new UnsupportedOperationException();
//	}
//
//	@Override
//	public Application patch(Long id, Application document) {
//		throw new UnsupportedOperationException();
//	}
//
//	@Override
//	public List<Application> patch(Application document, List<Long> ids) {
//		throw new UnsupportedOperationException();
//	}
//
//	@Override
//	public List<Version<Long, Application>> readVersions(Long id) {
//		throw new UnsupportedOperationException();
//	}
//
//	@Override
//	public Version<Long, Application> readVersion(Long versionId) {
//		throw new UnsupportedOperationException();
//	}
//
//	@Override
//	public Application restoreVersion(Long versionId, Boolean draft) {
//		throw new UnsupportedOperationException();
//	}
//
//	@Override
//	public ListPortion<Application> read(String slug, String search, Boolean reverse, Long skip, Long limit,
//			Integer depth) {
//		var u = URI.create(configuration.getProperty("janilla-com.api.url") + "/applications?"
//				+ new UriQueryBuilder().append("slug", slug).append("search", search)
//						.append("reverse", reverse != null ? reverse.toString() : null)
//						.append("skip", skip != null ? skip.toString() : null)
//						.append("limit", limit != null ? limit.toString() : null)
//						.append("depth", depth != null ? depth.toString() : null));
//		var o = httpClient.send(new HttpRequest("GET", u), HttpClient.JSON);
//		return new Converter().convert(o, new SimpleParameterizedType(ListPortion.class, List.of(Application.class)));
//	}
//}
