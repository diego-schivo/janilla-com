package com.janilla.janillacom.base;

import java.util.List;

import com.janilla.cms.CollectionApi;

public interface ApplicationApi extends CollectionApi<Long, Application> {

	List<Application> read(String slug, Long skip, Long limit);
}
