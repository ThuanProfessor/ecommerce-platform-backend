package com.htw.formatters;

import com.htw.pojo.SaleOrder;
import java.text.ParseException;
import java.util.Locale;
import org.springframework.format.Formatter;

/**
 *
 * @author Trung Hau
 */
public class OrderFormatter implements Formatter<SaleOrder> {

    @Override
    public String print(SaleOrder order, Locale locale) {
        return String.valueOf(order.getId());
    }

    @Override
    public SaleOrder parse(String orderId, Locale locale) throws ParseException {
        SaleOrder order = new SaleOrder(Integer.valueOf(orderId));

        return order;
    }

}
