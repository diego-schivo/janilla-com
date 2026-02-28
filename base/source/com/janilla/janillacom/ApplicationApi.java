package com.janilla.janillacom;

import com.janilla.cms.CollectionApi;
import com.janilla.persistence.ListPortion;

public interface ApplicationApi extends CollectionApi<Long, Application> {

	ListPortion<Application> read(String slug, String search, Boolean reverse, Long skip, Long limit, Integer depth);
}
