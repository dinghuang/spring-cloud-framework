package org.dinghuang.demo.application.mapper;

import org.dinghuang.demo.application.infra.dataobject.OrderDO;
import org.dinghuang.demo.application.dto.OrderDTO;
import org.dinghuang.demo.application.dto.OrderCreateDTO;
import org.dinghuang.demo.application.dto.OrderUpdateDTO;
import org.dinghuang.demo.application.dto.OrderCreateOrUpdateDTO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.dinghuang.core.dto.PageableDTO;
import org.dinghuang.core.mybatis.model.Pageable;

import java.util.List;

/**
 * 订单Mapper
 *
 * @author dinghuang123@gmail.com
 * @since 2019/06/07
 */
@Mapper
public interface OrderMapper {

    OrderMapper INSTANCE = Mappers.getMapper(OrderMapper.class);

    /**
     * createDto到do的转换
     *
     * @param orderCreateDTO orderCreateDTO
     * @return OrderDO
     */
    OrderDO createDtoToDo(OrderCreateDTO orderCreateDTO);

    /**
     * updateDto到do的转换
     *
     * @param orderUpdateDTO orderUpdateDTO
     * @return OrderDO
     */
    OrderDO updateDtoToDo(OrderUpdateDTO orderUpdateDTO);

    /**
     * createOrUpdateDto到dto的转换
     *
     * @param orderCreateOrUpdateDTO orderCreateOrUpdateDTO
     * @return OrderDO
     */
    OrderDO createOrUpdateDtoToDo(OrderCreateOrUpdateDTO orderCreateOrUpdateDTO);

    /**
     * do到dto的转换
     *
     * @param orderDO orderDO
     * @return OrderDTO
     */
    OrderDTO doToDto(OrderDO orderDO);

    /**
     * do到dto列表转换
     *
     * @param orderDOS orderDOS
     * @return OrderDTO
     */
    List<OrderDTO> doToDtos(List<OrderDO> orderDOS);

    /**
     * do到dto分页转换
     *
     * @param orderDOPageable orderDOPageable
     * @return OrderDTO
     */
    PageableDTO<OrderDTO> pageDoToDto(Pageable<OrderDO> orderDOPageable);
}