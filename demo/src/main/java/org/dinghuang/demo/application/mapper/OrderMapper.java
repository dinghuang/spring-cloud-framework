package org.dinghuang.demo.application.mapper;

import org.dinghuang.demo.application.dto.OrderCreateDTO;
import org.dinghuang.demo.application.dto.OrderDTO;
import org.dinghuang.demo.application.dto.OrderUpdateDTO;
import org.dinghuang.demo.infra.dataobject.OrderDO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

/**
 * @author dinghuang123@gmail.com
 * @since 2019/3/4
 */
@Mapper(componentModel = "spring")
public interface OrderMapper {

    OrderMapper INSTANCE = Mappers.getMapper(OrderMapper.class);

    @Mapping(source = "uuid", target = "uuid")
    @Mapping(source = "customerName", target = "customerName")
    @Mapping(source = "description", target = "description")
    @Mapping(source = "version", target = "version")
    OrderDTO toDTO(OrderDO orderDO);

    @Mapping(source = "customerName", target = "customerName")
    @Mapping(source = "description", target = "description")
    OrderDO toDO(OrderCreateDTO orderDO);

    @Mapping(source = "customerName", target = "customerName")
    @Mapping(source = "description", target = "description")
    @Mapping(source = "version", target = "version")
    OrderDO toDO(OrderUpdateDTO orderUpdateDTO);

}
