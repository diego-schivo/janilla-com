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

import java.util.List;

import com.janilla.blanktemplate.backend.Media;
import com.janilla.blanktemplate.backend.UserImpl;
import com.janilla.websitetemplate.backend.Category;
import com.janilla.websitetemplate.backend.Footer;
import com.janilla.websitetemplate.backend.Form;
import com.janilla.websitetemplate.backend.FormSubmission;
import com.janilla.websitetemplate.backend.Header;
import com.janilla.websitetemplate.backend.Page;
import com.janilla.websitetemplate.backend.Redirect;
import com.janilla.websitetemplate.backend.SearchResult;

public record SeedData(List<Application> applications, List<Category> categories, Footer footer,
		List<FormSubmission> formSubmissions, List<Form> forms, Header header, List<Media> media, List<Page> pages,
		List<PostImpl> posts, List<Redirect> redirects, List<SearchResult> searchResults, List<UserImpl> users) {
}
