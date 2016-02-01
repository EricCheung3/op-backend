package com.openprice.process;

import java.util.ArrayList;
import java.util.List;

import com.openprice.common.client.ServiceSettings;

import lombok.Data;

@Data
public class ProcessSettings {

    private boolean cloudEngineEnabled = false;

    private List<ServiceSettings> ocrServers = new ArrayList<>();

    private String serverPrefix;

    private int serverPort = 7901;  // default to 7901 for OCR Server port

    private int numberOfServers = 0;

    private String cloudApplicationId;

    private String cloudPassword;

    private int waitSeconds = 0;
}
