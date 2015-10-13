package com.openprice.parser.store;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ResourceLoaderAware;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.util.StringUtils;

import com.openprice.parser.ChainRegistry;
import com.openprice.parser.StoreBranch;
import com.openprice.parser.StoreChain;
import com.openprice.parser.StoreConfig;
import com.openprice.parser.StoreParserSelector;
import com.openprice.parser.common.TextResourceUtils;
import com.openprice.parser.data.Product;
import com.openprice.parser.price.PriceParser;
import com.openprice.parser.price.PriceParserWithCatalog;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public abstract class AbstractStoreParserSelector implements StoreParserSelector, InitializingBean, ResourceLoaderAware {
    static final String CONFIG_PATH_PREFIX = "classpath:config/";

    static final String BRANCH_FILE_NAME = "branch.csv";
    static final String CATEGORY_FILE_NAME = "category.txt";
    static final String IDENTIFY_FIELD_FILE_NAME = "identify.txt";
    static final String CATALOG_FILE_NAME = "catalog.txt";

    static final String SKIP_BEFORE_FILE_NAME = "skipBeforeItemsFinish.txt";
    static final String SKIP_AFTER_FILE_NAME = "skipAfterItemsFinish.txt";

    static final String BASE_CONFIG_FILE_NAME = "config.properties";
    static final String HEADER_CONFIG_FILE_NAME = "headerConfig.properties";

    private final ChainRegistry chainRegistry;
    private ResourceLoader resourceLoader;

    protected Properties baseConfig = new Properties();
    protected StoreChain chain;

    public AbstractStoreParserSelector(final ChainRegistry chainRegistry) {
        this.chainRegistry = chainRegistry;
    }

    @Override
    public void setResourceLoader(ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        // load base config
        try {
            baseConfig.load(getStoreConfigResource(BASE_CONFIG_FILE_NAME).getInputStream());
        } catch (IOException ex) {
            log.warn("Cannot load config.properties for RCSS store chain!");
        }

        chain = loadStoreConfig();
        chainRegistry.addChain(chain);

        generateParser();
    }

    /**
     * The parser code, like "RCSS", used as config folder name.
     * @return
     */
    protected abstract String getParserBaseCode();

    protected abstract void generateParser();

    protected Resource getParserConfigResource(final String parserName, final String filename) {
        return resourceLoader.getResource(CONFIG_PATH_PREFIX + getParserBaseCode() + "/" + parserName + "/" + filename);
    }

    protected StoreConfig loadParserConfig(final String parserName) {
        Properties configProp = new Properties(baseConfig);
        try {
            configProp.load(getParserConfigResource(parserName, HEADER_CONFIG_FILE_NAME).getInputStream());
        } catch (IOException ex) {
            log.error("Cannot load headerConfig.properties for store parser {}!", parserName);
        }

        return new StoreConfig(
                configProp,
                TextResourceUtils.loadStringArray(getStoreConfigResource(CATEGORY_FILE_NAME)),
                TextResourceUtils.loadStringArray(getParserConfigResource(parserName, SKIP_BEFORE_FILE_NAME)),
                TextResourceUtils.loadStringArray(getParserConfigResource(parserName, SKIP_AFTER_FILE_NAME)));
    }

    private Resource getStoreConfigResource(final String filename) {
        return resourceLoader.getResource(CONFIG_PATH_PREFIX + getParserBaseCode() + "/" + filename);
    }

    private StoreChain loadStoreConfig() {
        // load branch
        List<StoreBranch> branches = new ArrayList<>();
        TextResourceUtils.loadFromTextResource(getStoreConfigResource(BRANCH_FILE_NAME),
                (line)-> branches.add(StoreBranch.fromString(line, baseConfig.getProperty("Slogan")))); // FIXME why slogan?

        return
            StoreChain
            .builder()
            .code(getParserBaseCode())
            .categories(TextResourceUtils.loadStringArray(getStoreConfigResource(CATEGORY_FILE_NAME)))
            .identifyFields(TextResourceUtils.loadStringArray(getStoreConfigResource(IDENTIFY_FIELD_FILE_NAME)))
            .branches(branches)
            .selector(this)
            .build();
    }

    protected PriceParserWithCatalog loadPriceParserWithCatalog() {
        PriceParser priceParser = getStorePriceParser();
        if (priceParser == null) {
            log.error("No PriceParser for {} yet!", getParserBaseCode());
            return null; // No price parser yet!
        }
        // load catalog from file
        final List<Product> catalog=new ArrayList<Product>();
        TextResourceUtils.loadFromTextResource(getStoreConfigResource(CATALOG_FILE_NAME),
                line -> {if (!StringUtils.isEmpty(line)) {catalog.add(Product.fromString(line));}});
        return PriceParserWithCatalog.builder().catalog(catalog).priceParser(getStorePriceParser()).build();
    }

    protected abstract PriceParser getStorePriceParser();
}
