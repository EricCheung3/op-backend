package com.openprice.rest;

import org.springframework.hateoas.MediaTypes;
import org.springframework.web.bind.annotation.RequestMapping;

import com.openprice.common.ApiConstants;

@RequestMapping( value = ApiConstants.EXTERNAL_API_ROOT, produces = MediaTypes.HAL_JSON_VALUE )
public abstract class AbstractExternalRestController {
}
