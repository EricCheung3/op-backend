package com.openprice;

import com.openprice.process.ProcessSettings;

import lombok.Getter;
import lombok.Setter;


public class InternalApiApplicationSettings extends AbstractApplicationSettings {

    @Getter @Setter
    private ProcessSettings process;
}
