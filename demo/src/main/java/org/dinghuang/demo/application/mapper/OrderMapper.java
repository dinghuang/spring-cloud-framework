package org.dinghuang.demo.application.mapper;

import org.dinghuang.demo.application.dto.OrderCreateDTO;
import org.dinghuang.demo.application.dto.OrderDTO;
import org.dinghuang.demo.application.dto.OrderUpdateDTO;
import org.dinghuang.demo.infra.dataobject.OrderDO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * 官方文档地址：
 * <a http://mapstruct.org/documentation/dev/reference/html/ />
 *
 * @author dinghuang123@gmail.com
 * @since 2019/3/4
 */
@Mapper
public interface OrderMapper {

    OrderMapper INSTANCE = Mappers.getMapper(OrderMapper.class);

    @Mapping(source = "uuid", target = "uuid")
    @Mapping(source = "customerName", target = "customerName")
    @Mapping(source = "description", target = "description")
    @Mapping(source = "version", target = "version")
    OrderDTO doToDto(OrderDO orderDO);

    @Mapping(source = "customerName", target = "customerName")
    @Mapping(source = "description", target = "description")
    OrderDO createToDo(OrderCreateDTO orderDO);

    @Mapping(source = "customerName", target = "customerName")
    @Mapping(source = "description", target = "description")
    @Mapping(source = "version", target = "version")
    OrderDO updateToDo(OrderUpdateDTO orderUpdateDTO);

    /**
     * 这个会根据你之前绑定的实现自动映射
     *
     * @param orderDOList orderDOList
     * @return List
     */
    List<OrderDTO> doToDtos(List<OrderDO> orderDOList);

}
