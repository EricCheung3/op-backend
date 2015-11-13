package com.openprice.rest.user.receipt;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.springframework.hateoas.Link;
import org.springframework.hateoas.ResourceAssembler;
import org.springframework.hateoas.UriTemplate;
import org.springframework.hateoas.mvc.BasicLinkBuilder;
import org.springframework.stereotype.Component;

import com.openprice.domain.receipt.ParserResult;
import com.openprice.domain.receipt.Receipt;
import com.openprice.domain.receipt.ReceiptItem;
import com.openprice.rest.UtilConstants;

@Component
public class UserReceiptParserResultResourceAssembler implements ResourceAssembler<ParserResult, UserReceiptParserResultResource> {

    private final UserReceiptItemResourceAssembler itemResourceAssembler;

    @Inject
    public UserReceiptParserResultResourceAssembler(final UserReceiptItemResourceAssembler itemResourceAssembler) {
        this.itemResourceAssembler = itemResourceAssembler;
    }

    @Override
    public UserReceiptParserResultResource toResource(final ParserResult parserResult) {
        final UserReceiptParserResultResource resource = new UserReceiptParserResultResource(parserResult);
        final Receipt receipt = parserResult.getReceipt();

        resource.add(linkTo(methodOn(UserReceiptParserResultRestController.class).getUserReceiptParserResult(receipt.getId()))
                .withSelfRel());

        // Hack solution to build template links for "item"
        // Monitor https://github.com/spring-projects/spring-hateoas/issues/169 for nice solution from Spring HATEOAS
        final String baseUri = BasicLinkBuilder.linkToCurrentMapping().toString();
        final Link itemsLink = new Link(
                new UriTemplate(baseUri + UtilConstants.API_ROOT + "/user/receipts/"+ receipt.getId() + "/result/items" + UtilConstants.PAGINATION_TEMPLATES),
                UserReceiptResource.LINK_NAME_ITEMS);
        resource.add(itemsLink);

        final Link itemLink = new Link(
                new UriTemplate(baseUri + UtilConstants.API_ROOT + "/user/receipts/"+ receipt.getId() + "/result/items/{itemId}"),
                UserReceiptResource.LINK_NAME_ITEM);
        resource.add(itemLink);

        // TODO fix _embedded issue
        List<UserReceiptItemResource> items = new ArrayList<>();
        for (ReceiptItem item : parserResult.getItems()) {
            items.add(itemResourceAssembler.toResource(item));
        }
        resource.setItems(items);

        return resource;
    }
}
