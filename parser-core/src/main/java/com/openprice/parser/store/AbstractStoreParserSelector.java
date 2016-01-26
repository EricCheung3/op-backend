package com.openprice.parser.store;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Properties;
import java.util.Set;

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
import com.openprice.parser.price.PriceParserWithCatalog;

import lombok.extern.slf4j.Slf4j;

/**
 * Super class for all StoreParserSelector implementation classes.
 * During initialization (afterPropertiesSet()), it will load config for the store chain and register store chain to
 * chain registry. It will also create store parsers under the chain.
 *
 */
@Slf4j
public abstract class AbstractStoreParserSelector implements StoreParserSelector, InitializingBean, ResourceLoaderAware {
    static final String CONFIG_PATH_PREFIX = "classpath:config/";

    static final String BRANCH_FILE_NAME = "branch.csv";
    static final String CATEGORY_FILE_NAME = "category.txt";
    static final String IDENTIFY_FIELD_FILE_NAME = "identify.txt";
    static final String CATALOG_FILE_NAME = "catalog.txt";
    static final String CATALOG_BLACK_LIST_FILE_NAME="notCatalogItemNames.txt";
    static final String CATALOG_NOTATION_FILE_NAME="notations.txt";

    static final String SKIP_BEFORE_FILE_NAME = "skipBeforeItemsFinish.txt";
    static final String SKIP_AFTER_FILE_NAME = "skipAfterItemsFinish.txt";

    static final String BASE_CONFIG_FILE_NAME = "config.properties";
    static final String HEADER_CONFIG_FILE_NAME = "headerConfig.properties";

    private final ChainRegistry chainRegistry;
    private ResourceLoader resourceLoader;

    protected final Properties baseConfig = new Properties();
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

        registerStoreChainFromStoreConfig();
        generateParser();
    }

    private void registerStoreChainFromStoreConfig() throws Exception{
        // load branch
        List<StoreBranch> branches = new ArrayList<>();
        TextResourceUtils.loadFromTextResource(getStoreConfigResource(BRANCH_FILE_NAME),
                (line)-> branches.add(StoreBranch.fromString(line, baseConfig.getProperty("Slogan")))); // FIXME why slogan?

        chain =
                StoreChain
                .builder()
                .code(getParserBaseCode().toLowerCase()) // TODO maybe use lower case in all places?
                .categories(TextResourceUtils.loadStringArray(getStoreConfigResource(CATEGORY_FILE_NAME)))
                .identifyFields(TextResourceUtils.loadStringArray(getStoreConfigResource(IDENTIFY_FIELD_FILE_NAME)))
                .branches(branches)
                .selector(this)
                .build();
        chainRegistry.addChain(chain);
    }

    /**
     * The parser code, like "RCSS", used as config folder name.
     * @return
     */
    protected abstract String getParserBaseCode();

    /**
     * Subclass will create specific store parsers for different type if receipt format.
     */
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

        List<String> category=null;
        try{
            category=TextResourceUtils.loadStringArray(getStoreConfigResource(CATEGORY_FILE_NAME));
        }catch(Exception e){
            log.warn("cannot load "+CATEGORY_FILE_NAME);
            category=new ArrayList<String>();
        }

        List<String> skipBefore=null;
        try{
            skipBefore=TextResourceUtils.loadStringArray(getParserConfigResource(parserName, SKIP_BEFORE_FILE_NAME));
        }catch(Exception e){
            log.warn("cannot load "+SKIP_BEFORE_FILE_NAME);
            skipBefore=new ArrayList<String>();
        }

        List<String> skipAfter=null;
        try{
            skipAfter=TextResourceUtils.loadStringArray(getParserConfigResource(parserName, SKIP_AFTER_FILE_NAME));
        }catch(Exception e){
            log.warn("cannot load "+SKIP_AFTER_FILE_NAME);
            skipAfter=new ArrayList<String>();
        }

        List<String> notations=null;
        try{
            notations=TextResourceUtils.loadStringArray(getParserConfigResource(parserName, CATALOG_NOTATION_FILE_NAME));
        }catch(Exception e){
            log.warn("cannot load "+CATALOG_NOTATION_FILE_NAME);
            notations=new ArrayList<String>();
        }

        List<String> blackList=null;
        try{
            blackList=TextResourceUtils.loadStringArray(getStoreConfigResource(CATALOG_BLACK_LIST_FILE_NAME));
        }catch(Exception e){
            log.warn("cannot load "+CATALOG_BLACK_LIST_FILE_NAME);
            blackList=new ArrayList<String>();
        }

        //we don't want these to be item names
        blackList.addAll(category);
        blackList.addAll(skipBefore);
        blackList.addAll(skipAfter);
        blackList.addAll(notations);

        return new StoreConfig(
                configProp,
                category,
                skipBefore,
                skipAfter,
                blackList
                );
    }

    private Resource getStoreConfigResource(final String filename) {
        return resourceLoader.getResource(CONFIG_PATH_PREFIX + getParserBaseCode() + "/" + filename);
    }

    /**
     * generate a parser with catalog
     * @return a parser with a catalog if the corresponding file is read in successfully; otherwise return an empty catalog
     */
    protected PriceParserWithCatalog loadPriceParserWithCatalog() {
        final Set<Product> catalog=new HashSet<Product>();
        try{
            TextResourceUtils.loadFromTextResource(getStoreConfigResource(CATALOG_FILE_NAME),
                line -> {
                    if (!StringUtils.isEmpty(line)) {
                        catalog.add(Product.fromString(line));
                    }
                });
        }catch(Exception e){
            return PriceParserWithCatalog.withCatalog(new HashSet<Product>());
        }
        //return PriceParserWithCatalog.builder().catalog(catalog).priceParser(new PriceParserFromStringTuple()).build();
        return PriceParserWithCatalog.withCatalog(catalog);
    }
}
