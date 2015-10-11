package com.openprice.parser.store;

import org.springframework.util.StringUtils;

import com.openprice.parser.StoreConfig;
import com.openprice.parser.StoreParser;
import com.openprice.parser.common.Levenshtein;
import com.openprice.parser.common.StringCommon;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public abstract class AbstractStoreParser implements StoreParser {
    protected final StoreConfig config;

    public AbstractStoreParser(final StoreConfig config) {
        this.config = config;
    }

    @Override
    public StoreConfig getStoreConfig() {
        return config;
    }

    /**
     * get the value string after the header
     * @param lineString receipt line string (clean text)
     * @param header matched header text
     * @return value string
     * @throws Exception
     */
    protected String getValueAtTail(final String lineString, final String header) {
        if(StringUtils.isEmpty(header)) {
            return lineString;
        }

        // check header string in the receipt line
        String headerStringFromReceiptString = StringCommon.firstNonSpaceChars(lineString, header.length());
        if(Levenshtein.compare(header, headerStringFromReceiptString) <= config.similarityThresholdOfTwoStrings()){
            log.warn("Head of receipt string '{}' and header '{}' not similar ", headerStringFromReceiptString, header);
        }
        final String tail = StringCommon.removeFirstNonSpaceChars(lineString, header).trim();
        return tail;
    }

    public String getTailOnlyDigitsDots(final String lineString, final String header)throws Exception{
        return StringCommon.getOnlyDigitsDots(getValueAtTail(lineString, header));
    }

}
