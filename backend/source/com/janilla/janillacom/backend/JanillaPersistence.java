package com.janilla.janillacom.backend;

import java.util.List;
import java.util.Properties;

import com.janilla.backend.sqlite.SqliteDatabase;
import com.janilla.ioc.DiFactory;
import com.janilla.java.TypeResolver;
import com.janilla.persistence.Entity;
import com.janilla.websitetemplate.backend.WebsitePersistence;

public class JanillaPersistence extends WebsitePersistence {

	public JanillaPersistence(SqliteDatabase database, List<Class<? extends Entity<?>>> storables,
			TypeResolver typeResolver, DiFactory diFactory, Properties configuration, String configurationKey) {
		super(database, storables, typeResolver, diFactory, configuration, configurationKey);
	}

	@Override
	protected Class<?> seedDataClass() {
		return SeedData.class;
	}
}
