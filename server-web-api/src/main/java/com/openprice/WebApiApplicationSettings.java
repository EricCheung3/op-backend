package com.openprice;

import com.openprice.common.client.ServiceSettings;

import lombok.Getter;
import lombok.Setter;

public class WebApiApplicationSettings extends AbstractApplicationSettings {

    @Getter @Setter
    private ServiceSettings internal;
}
