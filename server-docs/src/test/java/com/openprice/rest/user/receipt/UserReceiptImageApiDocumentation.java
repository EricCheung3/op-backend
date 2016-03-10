package com.openprice.rest.user.receipt;

import static org.springframework.restdocs.hypermedia.HypermediaDocumentation.linkWithRel;
import static org.springframework.restdocs.hypermedia.HypermediaDocumentation.links;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.fileUpload;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessResponse;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.requestParameters;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Test;
import org.springframework.mock.web.MockMultipartFile;

import com.google.common.io.ByteStreams;

public class UserReceiptImageApiDocumentation extends UserReceiptApiDocumentationBase {

    @Test
    public void receiptImageListExample() throws Exception {
        mockMvc
        .perform(get(userReceiptImagesUrl()).with(user(USERNAME)))
        .andExpect(status().isOk())
        .andDo(document("user-receipt-image-list-example",
            preprocessResponse(prettyPrint()),
            links(
                linkWithRel("self").description("The self link")
            ),
            responseFields(
                fieldWithPath("_embedded.receiptImages").description("An array of <<resources-user-receipt-image, ReceiptImage resources>>"),
                fieldWithPath("page").description("Pagination data"),
                fieldWithPath("_links").description("<<resources-user-receipt-image-list-links,Links>> to other resources")
            )
        ));
    }

    @Test
    public void receiptImageRetrieveExample() throws Exception {
        mockMvc
        .perform(get(userReceiptImageUrl()).with(user(USERNAME)))
        .andExpect(status().isOk())
        .andDo(document("user-receipt-image-retrieve-example",
            preprocessResponse(prettyPrint()),
            links(
                linkWithRel("self").description("The self link"),
                linkWithRel("receipt").description("<<resources-user-receipt,Link>> to owner receipt resource"),
                linkWithRel("download").description("URL for downloading image as jpg file"),
                linkWithRel("base64").description("URL for downloading image as base64 string")
            ),
            responseFields(
                fieldWithPath("id").description("Primary ID"),
                fieldWithPath("status").description("Receipt image process status"),
                fieldWithPath("fileName").description("Receipt image file name"),
                fieldWithPath("downloadUrl").description("Receipt image JPEG file download URL"),
                fieldWithPath("base64Url").description("Receipt image Base64 data download URL"),
                fieldWithPath("base64").description("Receipt image Base64 data for mobile display"),
                fieldWithPath("_links").description("<<resources-user-receipt-image-links, Links>> to other resources")
            )
        ));

    }

    @Test
    public void receiptImageUploadExample() throws Exception {
        final MockMultipartFile file = new MockMultipartFile("file", "image2.jpg", "image/jpeg", ByteStreams.toByteArray(sampleImage.getInputStream()));
        mockMvc
        .perform(
            fileUpload(userReceiptImageUploadUrl())
            .file(file)
            .param("filename", "test.jpg")
            .with(user(USERNAME))
        )
        .andExpect(status().isCreated())
        .andDo(document("user-receipt-image-upload-example",
            requestParameters(
                parameterWithName("filename").description("The uploaded image file name")
            )
        ));
    }

}
