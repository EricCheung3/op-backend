package com.openprice.parser.store.rcss;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Service;

import com.openprice.parser.store.ChainRegistry;
import com.openprice.parser.store.StoreBranch;
import com.openprice.parser.store.StoreChain;
import com.openprice.parser.store.StoreParser;
import com.openprice.parser.store.StoreParserSelector;
import com.openprice.parser.store.rcss.rcss1.RCSS1;

@Service
public class RCSSSelector implements StoreParserSelector, InitializingBean {

    private final ChainRegistry chainRegistry;

    @Inject
    public RCSSSelector(final ChainRegistry chainRegistry) {
        this.chainRegistry = chainRegistry;
    }

    @Override
    public StoreParser selectParser() {
        // just one parser
        return new RCSS1();
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        final StoreChain chain =
                StoreChain
                .builder()
                .code("RCSS")
                .categories("Grocery")
                .identifyFields("RCSS,RCSS Superstore,Big on Fresh Low on Price,REAL CANADIAN") //TODO move to text file
                .branches(loadBranches())
                .selector(this)
                .build();
        chainRegistry.addChain(chain);
    }

    List<StoreBranch> loadBranches() {
        //TODO load branch info from branches.csv file
        return new ArrayList<>();
    }
}
