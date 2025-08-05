package com.htw.formatters;

import com.htw.pojo.Store;
import java.text.ParseException;
import java.util.Locale;
import org.springframework.format.Formatter;

/**
 *
 * @author Trung Hau
 */
public class StoreFormatter implements Formatter<Store> {

    @Override
    public String print(Store store, Locale locale) {
        return String.valueOf(store.getId());
    }

    @Override
    public Store parse(String storeId, Locale locale) throws ParseException {
        Store store = new Store(Integer.valueOf(storeId));

        return store;
    }

}
