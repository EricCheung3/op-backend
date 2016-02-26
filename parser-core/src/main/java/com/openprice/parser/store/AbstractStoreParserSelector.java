package com.openprice.parser.store;

import java.util.List;
import java.util.Properties;
import java.util.Set;
import java.util.stream.Collectors;

import javax.inject.Inject;

import org.springframework.beans.factory.InitializingBean;

import com.google.common.collect.ImmutableSet;
import com.openprice.parser.ChainRegistry;
import com.openprice.parser.StoreConfigImpl;
import com.openprice.parser.api.Product;
import com.openprice.parser.api.StoreParserSelector;
import com.openprice.parser.data.ProductImpl;
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
    final protected StoreMetadata metadata;

    protected Properties baseConfig;
    protected StoreChain chain;

    @Inject
    public AbstractStoreParserSelector(final ChainRegistry chainRegistry, final StoreMetadata metadata) {
        this.chainRegistry = chainRegistry;
        this.metadata=metadata;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        chain=metadata.getStoreChainByCode(getParserBaseCode().toLowerCase());
        chainRegistry.addChain(chain, this);
        System.out.println("afterPropertiesSet: chain.getCode()="+chain.getCode());
        baseConfig=metadata.getStoreChainByCode(chain.getCode()).getNonHeaderProperties();
        generateParser();
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

    protected StoreConfigImpl loadParserConfig(final String parserName) {
        Properties allConfig = new Properties();
        allConfig.putAll(metadata.getStoreChainByCode(chain.getCode()).getHeaderProperties());
        //System.out.println("allConfig.size"+allConfig.size());
        final int size1=allConfig.size();
        allConfig.putAll(baseConfig);
        final int size2=allConfig.size();
        if(size2!=size1+baseConfig.size())
            log.warn("there are repeating properties in the two config files for parser "+parserName);

        final List<String> notCatalogItemNames=metadata.getStoreChainByCode(chain.getCode()).getNotCatalogItemNames();
        final List<String> category=metadata.getStoreChainByCode(chain.getCode()).getReceiptCategories();
        final List<String> skipBefore=metadata.getStoreChainByCode(chain.getCode()).getSkipBefore();
        final List<String> skipAfter=metadata.getStoreChainByCode(chain.getCode()).getSkipAfter();
        final List<String> notations=metadata.getStoreChainByCode(chain.getCode()).getNotations();
        final ImmutableSet.Builder<String> blackListAll=new  ImmutableSet.Builder<>();
        //we don't want these to be item names
        blackListAll.addAll(notCatalogItemNames);
        blackListAll.addAll(category);
        blackListAll.addAll(skipBefore);
        blackListAll.addAll(skipAfter);
        blackListAll.addAll(notations);
        blackListAll.add(chain.getName());//chain name is not an item

        return StoreConfigImpl.fromPropCategorySkipBeforeAfterBlack(
                allConfig,
                category,
                skipBefore,
                skipAfter,
                blackListAll.build().stream().collect(Collectors.toList())
                );
    }

    /**
     * generate a parser with catalog
     * @return a parser with a catalog if the corresponding file is read in successfully; otherwise return an empty catalog
     */
    protected PriceParserWithCatalog loadPriceParserWithCatalog() {
        final Set<Product> catalog=metadata
                .getStoreChainByCode(chain.getCode())
                .getProducts()
                .stream()
                .map(c->ProductImpl.fromNameNumber(c.getReceiptName(), c.getNumber()))
                .collect(Collectors.toSet());
        System.out.println("chain:"+chain.getCode()+", catalog size="+catalog.size());
        return PriceParserWithCatalog.withCatalog(catalog);
    }
}
