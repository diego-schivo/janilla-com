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

import com.janilla.blanktemplate.Media;
import com.janilla.blanktemplate.UserImpl;
import com.janilla.cms.DocumentStatus;
import com.janilla.cms.Types;
import com.janilla.cms.User;
import com.janilla.cms.Versions;
import com.janilla.persistence.Index;
import com.janilla.persistence.Store;
import com.janilla.websitetemplate.Banner;
import com.janilla.websitetemplate.CallToAction;
import com.janilla.websitetemplate.Category;
import com.janilla.websitetemplate.MediaBlock;
import com.janilla.websitetemplate.Meta;
import com.janilla.websitetemplate.Post;
import com.janilla.websitetemplate.RichText;

@Store
@Versions(drafts = true)
public record PostImpl(Long id, String title, Media heroImage, List<@Types( {
		Banner.class, CallToAction.class, MediaBlock.class, RichText.class }) Object> content,
		List<@Types(PostImpl.class) Post> relatedPosts, List<Category> categories, Meta meta, @Index String slug,
		List<@Types(UserImpl.class) User<?, ?>> authors, Instant createdAt, Instant updatedAt,
		DocumentStatus documentStatus, Instant publishedAt) implements Post{

	@Override
	public PostImpl withRelatedPosts(List<Post> relatedPosts) {
		return new PostImpl(id, title, heroImage, content, relatedPosts, categories, meta, slug, authors, createdAt,
				updatedAt, documentStatus, publishedAt);
	}
}
