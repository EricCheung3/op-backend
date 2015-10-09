package com.openprice.parser.store.rcss;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.inject.Inject;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import com.openprice.parser.common.TextResourceUtils;
import com.openprice.parser.store.ChainRegistry;
import com.openprice.parser.store.StoreBranch;
import com.openprice.parser.store.StoreChain;
import com.openprice.parser.store.StoreConfig;
import com.openprice.parser.store.StoreParser;
import com.openprice.parser.store.StoreParserSelector;
import com.openprice.parser.store.rcss.rcss1.RCSS1;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class RCSSSelector implements StoreParserSelector, InitializingBean {

    @Value("classpath:/com/openprice/parser/store/rcss/branch.csv")
    private Resource branchResource;

    private final ChainRegistry chainRegistry;
    private StoreChain chain;
    private List<StoreBranch> branches;
    private Properties baseConfig = new Properties();

    @Inject
    public RCSSSelector(final ChainRegistry chainRegistry) {
        this.chainRegistry = chainRegistry;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        loadBranch();

        try {
            baseConfig.load(RCSSSelector.class.getResourceAsStream(BASE_CONFIG_FILE_NAME));
        } catch (IOException ex) {
            log.warn("Cannot load config.properties for RCSS store chain!");
        }

        chain =
            StoreChain
            .builder()
            .code("RCSS")
            .categories("Grocery") // TODO load from file
            .identifyFields("RCSS,RCSS Superstore,Big on Fresh Low on Price,REAL CANADIAN") //TODO move to text file
            .branches(branches)
            .selector(this)
            .build();
        chainRegistry.addChain(chain);
    }

    @Override
    public StoreParser selectParser() {
        Properties configProp = new Properties(baseConfig);
        try {
            configProp.load(RCSS1.class.getResourceAsStream(HEADER_CONFIG_FILE_NAME));
        } catch (IOException ex) {
            log.warn("Cannot load config.properties for RCSS1 store parser!");
        }

        StoreConfig config = new StoreConfig(configProp,
                                             loadStringArray(RCSS1.class.getResourceAsStream(CATEGORY_FILE_NAME)),
                                             loadStringArray(RCSS1.class.getResourceAsStream(SKIP_BEFORE_FILE_NAME)),
                                             loadStringArray(RCSS1.class.getResourceAsStream(SKIP_AFTER_FILE_NAME)));
        // just one parser
        return new RCSS1(config, chain);
    }

    private void loadBranch() {
        branches = new ArrayList<>();
        InputStream is = RCSSSelector.class.getResourceAsStream(BRANCH_FILE_NAME);

        TextResourceUtils.loadFromInputStream(is,
                (line)-> branches.add(StoreBranch.fromString(line, "Big on Fresh, Low on Price"))); // TODO slogan?);
//        TextResourceUtils.loadFromTextResource(branchResource,
//                (line)-> branches.add(StoreBranch.fromString(line, "Big on Fresh, Low on Price")));
    }

    private List<String> loadStringArray(InputStream is) {
        final List<String> result = new ArrayList<>();
        TextResourceUtils.loadFromInputStream(is, (line)-> result.add(line));
        return result;
    }
}
