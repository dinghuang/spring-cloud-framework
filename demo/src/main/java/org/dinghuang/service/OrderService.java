package org.dinghuang.service;

import java.math.BigDecimal;

/**
 * @author dinghuang123@gmail.com
 * @since 2019/2/27
 */
public interface OrderService {
    BigDecimal calcTotalAmount(Long id);
}
