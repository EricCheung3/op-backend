package com.openprice.parser.store;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Properties;
import java.util.Set;

import javax.inject.Inject;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.util.StringUtils;

import com.openprice.common.TextResourceUtils;
import com.openprice.parser.ChainRegistry;
import com.openprice.parser.StoreConfigImpl;
import com.openprice.parser.api.Product;
import com.openprice.parser.api.StoreParserSelector;
import com.openprice.parser.data.ProductImpl;
import com.openprice.parser.generic.ConfigFiles;
import com.openprice.parser.price.PriceParserWithCatalog;
import com.openprice.store.StoreChain;
import com.openprice.store.StoreMetadata;

import lombok.extern.slf4j.Slf4j;

/**
 * Super class for all StoreParserSelector implementation classes.
 * During initialization (afterPropertiesSet()), it will load config for the store chain and register store chain to
 * chain registry. It will also create store parsers under the chain.
 *
 */
@Slf4j
public abstract class AbstractStoreParserSelector implements StoreParserSelector, InitializingBean {

    private final ChainRegistry chainRegistry;
    private final StoreMetadata metadata;

    protected final Properties baseConfig = new Properties();
    protected StoreChain chain;

    @Inject
    public AbstractStoreParserSelector(final ChainRegistry chainRegistry, final StoreMetadata metadata) {
        this.chainRegistry = chainRegistry;
        this.metadata = metadata;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        try {
            baseConfig.load(getChainResource(ConfigFiles.CONFIG_PROPERTY_FILE_NAME));
            log.debug("baseConfig.entrySet().size()="+baseConfig.entrySet().size());
        } catch (IOException ex) {
            log.warn("Cannot load config.properties for {} store chain!", getParserBaseCode());
        }
        registerStoreChainFromStoreConfig();
        generateParser();
    }

    private void registerStoreChainFromStoreConfig() throws Exception{
        // load branch
//        List<StoreBranchMatch> branches = new ArrayList<>();
//        TextResourceUtils.loadFromInputStream(getChainResource(ConfigFiles.BRANCH_FILE_NAME),
//                line ->
//                    branches.add(StoreBranchMatch.fromString(line, baseConfig.getProperty("Slogan")))); // FIXME why slogan?

//        chain = StoreChain.fromCodeSelectorCategoriesFieldsBranches(
//                        getParserBaseCode().toLowerCase(), // TODO maybe use lower case in all places?
//                        this,
//                        TextResourceUtils.loadStringArray(getChainResource(ConfigFiles.CATEGORY_FILE_NAME)),
//                        TextResourceUtils.loadStringArray(getChainResource(ConfigFiles.IDENTIFY_FIELD_FILE_NAME)),
//                        branches));
        chain=metadata.getStoreChainByCode(getParserBaseCode().toLowerCase());
        chainRegistry.addChain(chain, this);
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

    private InputStream getChainParserResource(final String parserName, final String filename) {
        return AbstractStoreParserSelector.class.getResourceAsStream(ConfigFiles.getFileUnderChainParser(getParserBaseCode(), parserName, filename));
    }

    private InputStream getChainResource(final String filename) {
        return AbstractStoreParserSelector.class.getResourceAsStream(ConfigFiles.getFileUnderChain(getParserBaseCode(), filename));
    }

    protected StoreConfigImpl loadParserConfig(final String parserName) {
        Properties allConfig = new Properties();
        try {
            allConfig.load(getChainParserResource(parserName, ConfigFiles.HEADER_CONFIG_FILE_NAME));
            //System.out.println("allConfig.size"+allConfig.size());
            final int size1=allConfig.size();
            allConfig.putAll(baseConfig);
            final int size2=allConfig.size();
            if(size2!=size1+baseConfig.size())
                log.warn("there are repeating properties in the two config files for parser "+parserName);
        } catch (IOException ex) {
            log.error("Cannot load headerConfig.properties for store parser {}!", parserName);
        }

        List<String> category=null;
        try{
            category=TextResourceUtils.loadStringArray(getChainResource(ConfigFiles.CATEGORY_FILE_NAME));
        }catch(Exception e){
            log.warn("cannot load {} for {} store chain.", ConfigFiles.CATEGORY_FILE_NAME, getParserBaseCode());
            category=new ArrayList<String>();
        }

        List<String> skipBefore=null;
        try{
            skipBefore=TextResourceUtils.loadStringArray(getChainParserResource(parserName, ConfigFiles.SKIP_BEFORE_FILE_NAME));
        }catch(Exception e){
            log.warn("cannot load {} for {} store chain.", ConfigFiles.SKIP_BEFORE_FILE_NAME, getParserBaseCode());
            skipBefore=new ArrayList<String>();
        }

        List<String> skipAfter=null;
        try{
            skipAfter=TextResourceUtils.loadStringArray(getChainParserResource(parserName, ConfigFiles.SKIP_AFTER_FILE_NAME));
        }catch(Exception e){
            log.warn("cannot load {} for {} store chain.", ConfigFiles.SKIP_AFTER_FILE_NAME, getParserBaseCode());
            skipAfter=new ArrayList<String>();
        }

        List<String> notations=null;
        try{
            notations=TextResourceUtils.loadStringArray(getChainParserResource(parserName, ConfigFiles.CATALOG_NOTATION_FILE_NAME));
        }catch(Exception e){
            log.warn("cannot load {} for {} store chain.", ConfigFiles.CATALOG_NOTATION_FILE_NAME, getParserBaseCode());
            notations=new ArrayList<String>();
        }

        List<String> blackList=null;
        try{
            blackList=TextResourceUtils.loadStringArray(getChainResource(ConfigFiles.CATALOG_BLACK_LIST_FILE_NAME));
        }catch(Exception e){
            log.warn("cannot load {} for {} store chain.", ConfigFiles.CATALOG_BLACK_LIST_FILE_NAME, getParserBaseCode());
            blackList=new ArrayList<String>();
        }

        //we don't want these to be item names
        blackList.addAll(category);
        blackList.addAll(skipBefore);
        blackList.addAll(skipAfter);
        blackList.addAll(notations);

        return StoreConfigImpl.fromPropCategorySkipBeforeAfterBlack(
                allConfig,
                category,
                skipBefore,
                skipAfter,
                blackList
                );
    }

    /**
     * generate a parser with catalog
     * @return a parser with a catalog if the corresponding file is read in successfully; otherwise return an empty catalog
     */
    protected PriceParserWithCatalog loadPriceParserWithCatalog() {
        final Set<Product> catalog=new HashSet<Product>();
        try{
            TextResourceUtils.loadFromInputStream(getChainResource(ConfigFiles.CATALOG_FILE_NAME),
                line -> {
                    if (!StringUtils.isEmpty(line)) {
                        catalog.add(ProductImpl.fromString(line));
                    }
                });
        }catch(Exception e){
            return PriceParserWithCatalog.withCatalog(new HashSet<Product>());
        }
        return PriceParserWithCatalog.withCatalog(catalog);
    }
}
