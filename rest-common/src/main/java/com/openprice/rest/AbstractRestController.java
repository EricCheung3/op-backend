package com.openprice.rest;

import org.springframework.hateoas.MediaTypes;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping( value = UtilConstants.API_ROOT, produces = MediaTypes.HAL_JSON_VALUE )
public abstract class AbstractRestController {
}
