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
import com.openprice.parser.generic.ConfigFiles;
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
            baseConfig.load(getStoreConfigResource(ConfigFiles.BASE_CONFIG_FILE_NAME).getInputStream());
        } catch (IOException ex) {
            log.warn("Cannot load config.properties for RCSS store chain!");
        }

        registerStoreChainFromStoreConfig();
        generateParser();
    }

    private void registerStoreChainFromStoreConfig() throws Exception{
        // load branch
        List<StoreBranch> branches = new ArrayList<>();
        TextResourceUtils.loadFromTextResource(getStoreConfigResource(ConfigFiles.BRANCH_FILE_NAME),
                (line)-> branches.add(StoreBranch.fromString(line, baseConfig.getProperty("Slogan")))); // FIXME why slogan?

        chain = StoreChain.fromCodeSelectorCategoriesFieldsBranches(
                        getParserBaseCode().toLowerCase(), // TODO maybe use lower case in all places?
                        this,
                        TextResourceUtils.loadStringArray(getStoreConfigResource(ConfigFiles.CATEGORY_FILE_NAME)),
                        TextResourceUtils.loadStringArray(getStoreConfigResource(ConfigFiles.IDENTIFY_FIELD_FILE_NAME)),
                        branches);
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
        return resourceLoader.getResource(ConfigFiles.CONFIG_PATH_PREFIX + getParserBaseCode() + "/" + parserName + "/" + filename);
    }

    protected StoreConfig loadParserConfig(final String parserName) {
        Properties configProp = new Properties(baseConfig);
        try {
            configProp.load(getParserConfigResource(parserName, ConfigFiles.HEADER_CONFIG_FILE_NAME).getInputStream());
        } catch (IOException ex) {
            log.error("Cannot load headerConfig.properties for store parser {}!", parserName);
        }

        List<String> category=null;
        try{
            category=TextResourceUtils.loadStringArray(getStoreConfigResource(ConfigFiles.CATEGORY_FILE_NAME));
        }catch(Exception e){
            log.warn("cannot load "+ConfigFiles.CATEGORY_FILE_NAME);
            category=new ArrayList<String>();
        }

        List<String> skipBefore=null;
        try{
            skipBefore=TextResourceUtils.loadStringArray(getParserConfigResource(parserName, ConfigFiles.SKIP_BEFORE_FILE_NAME));
        }catch(Exception e){
            log.warn("cannot load "+ConfigFiles.SKIP_BEFORE_FILE_NAME);
            skipBefore=new ArrayList<String>();
        }

        List<String> skipAfter=null;
        try{
            skipAfter=TextResourceUtils.loadStringArray(getParserConfigResource(parserName, ConfigFiles.SKIP_AFTER_FILE_NAME));
        }catch(Exception e){
            log.warn("cannot load "+ConfigFiles.SKIP_AFTER_FILE_NAME);
            skipAfter=new ArrayList<String>();
        }

        List<String> notations=null;
        try{
            notations=TextResourceUtils.loadStringArray(getParserConfigResource(parserName, ConfigFiles.CATALOG_NOTATION_FILE_NAME));
        }catch(Exception e){
            log.warn("cannot load "+ConfigFiles.CATALOG_NOTATION_FILE_NAME);
            notations=new ArrayList<String>();
        }

        List<String> blackList=null;
        try{
            blackList=TextResourceUtils.loadStringArray(getStoreConfigResource(ConfigFiles.CATALOG_BLACK_LIST_FILE_NAME));
        }catch(Exception e){
            log.warn("cannot load "+ConfigFiles.CATALOG_BLACK_LIST_FILE_NAME);
            blackList=new ArrayList<String>();
        }

        //we don't want these to be item names
        blackList.addAll(category);
        blackList.addAll(skipBefore);
        blackList.addAll(skipAfter);
        blackList.addAll(notations);

        return StoreConfig.fromPropCategorySkipBeforeAfterBlack(
                configProp,
                category,
                skipBefore,
                skipAfter,
                blackList
                );
    }

    //use class.getInputAsStream?
    private Resource getStoreConfigResource(final String filename) {
        //return resourceLoader.getResource(ConfigFiles.CONFIG_PATH_PREFIX + getParserBaseCode() + "/" + filename);
        return resourceLoader.getResource(ConfigFiles.CONFIG_PATH_PREFIX + getParserBaseCode() + "/" + filename);
    }

    /**
     * generate a parser with catalog
     * @return a parser with a catalog if the corresponding file is read in successfully; otherwise return an empty catalog
     */
    protected PriceParserWithCatalog loadPriceParserWithCatalog() {
        final Set<Product> catalog=new HashSet<Product>();
        try{
            TextResourceUtils.loadFromTextResource(getStoreConfigResource(ConfigFiles.CATALOG_FILE_NAME),
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
