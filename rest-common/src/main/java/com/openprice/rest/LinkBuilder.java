package com.openprice.rest;

import org.springframework.hateoas.Link;
import org.springframework.hateoas.ResourceSupport;
import org.springframework.hateoas.UriTemplate;
import org.springframework.hateoas.mvc.BasicLinkBuilder;

public class LinkBuilder {

    private final String baseUri;

    private final ResourceSupport resource;

    public LinkBuilder(final ResourceSupport resource) {
        this.baseUri = BasicLinkBuilder.linkToCurrentMapping().toString();
        this.resource = resource;
    }

    public LinkBuilder addLink(final String rel, final String apiUrl, final boolean appendPagination, final String[] pairs) {
        String url = buildUrl(apiUrl, appendPagination, pairs);
        final Link link = new Link(new UriTemplate(url), rel);
        resource.add(link);
        return this;
    }

    private String buildUrl(final String apiUrl, final boolean appendPagination, final String[] pairs) {
        final StringBuilder strBuilder = new StringBuilder();
        strBuilder.append(baseUri)
                  .append(UtilConstants.API_ROOT)
                  .append(apiUrl);
        if (appendPagination) {
            strBuilder.append(UtilConstants.PAGINATION_TEMPLATES);
        }
        String url = strBuilder.toString();
        if (pairs != null) {
            for (int index=0; index<pairs.length; index += 2) {
                url = url.replace("{"+pairs[index]+"}", pairs[index+1]);
            }
        }
        return url;
    }
}
