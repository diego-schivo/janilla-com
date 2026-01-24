/*
 * MIT License
 *
 * Copyright (c) 2018-2025 Payload CMS, Inc. <info@payloadcms.com>
 * Copyright (c) 2024-2026 Diego Schivo <diego.schivo@janilla.com>
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package com.janilla.janillacom.backend;

import java.time.Instant;
import java.util.List;

import com.janilla.backend.cms.DocumentStatus;
import com.janilla.backend.cms.Types;
import com.janilla.backend.cms.Versions;
import com.janilla.backend.persistence.Index;
import com.janilla.backend.persistence.Store;
import com.janilla.websitetemplate.backend.Banner;
import com.janilla.websitetemplate.backend.CallToAction;
import com.janilla.websitetemplate.backend.Category;
import com.janilla.websitetemplate.backend.Media;
import com.janilla.websitetemplate.backend.MediaBlock;
import com.janilla.websitetemplate.backend.Meta;
import com.janilla.websitetemplate.backend.Post0;
import com.janilla.websitetemplate.backend.RichText;
import com.janilla.websitetemplate.backend.User;

@Store
@Versions(drafts = true)
public record Post(Long id, String title, @Types(Media.class) Long heroImage, List<@Types( {
		Banner.class, CallToAction.class, MediaBlock.class, RichText.class }) Object> content,
		List<@Types(Post.class) Long> relatedPosts, List<@Types(Category.class) Long> categories, Meta meta,
		@Index String slug, List<@Types(User.class) Long> authors, Instant createdAt, Instant updatedAt,
		DocumentStatus documentStatus, Instant publishedAt) implements Post0{

	@Override
	public Post withRelatedPosts(List<Long> relatedPosts) {
		return new Post(id, title, heroImage, content, relatedPosts, categories, meta, slug, authors, createdAt,
				updatedAt, documentStatus, publishedAt);
	}
}
