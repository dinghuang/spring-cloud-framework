package ${package_name}.mapper;

import ${package_name}.infra.dataobject.${table_name}DO;
import ${package_name}.dto.${table_name}DTO;
import ${package_name}.dto.${table_name}CreateDTO;
import ${package_name}.dto.${table_name}UpdateDTO;
import ${package_name}.dto.${table_name}CreateOrUpdateDTO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.dinghuang.core.dto.PageableDTO;
import org.dinghuang.core.mybatis.model.Pageable;

import java.util.List;

/**
 * ${table_annotation}Mapper
 *
 * @author ${author}
 * @since ${date}
 */
@Mapper
public interface ${table_name}Mapper {

    ${table_name}Mapper INSTANCE = Mappers.getMapper(${table_name}Mapper.class);

    /**
     * createDto到do的转换
     *
     * @param ${table_name_first_low}CreateDTO ${table_name_first_low}CreateDTO
     * @return ${table_name}DO
     */
    ${table_name}DO createDtoToDo(${table_name}CreateDTO ${table_name_first_low}CreateDTO);

    /**
     * updateDto到do的转换
     *
     * @param ${table_name_first_low}UpdateDTO ${table_name_first_low}UpdateDTO
     * @return ${table_name}DO
     */
    ${table_name}DO updateDtoToDo(${table_name}UpdateDTO ${table_name_first_low}UpdateDTO);

    /**
     * createOrUpdateDto到dto的转换
     *
     * @param ${table_name_first_low}CreateOrUpdateDTO ${table_name_first_low}CreateOrUpdateDTO
     * @return ${table_name}DO
     */
    ${table_name}DO createOrUpdateDtoToDo(${table_name}CreateOrUpdateDTO ${table_name_first_low}CreateOrUpdateDTO);

    /**
     * do到dto的转换
     *
     * @param ${table_name_first_low}DO ${table_name_first_low}DO
     * @return ${table_name}DTO
     */
    ${table_name}DTO doToDto(${table_name}DO ${table_name_first_low}DO);

    /**
     * do到dto列表转换
     *
     * @param ${table_name_first_low}DOS ${table_name_first_low}DOS
     * @return ${table_name}DTO
     */
    List<${table_name}DTO> doToDtos(List<${table_name}DO> ${table_name_first_low}DOS);

    /**
     * do到dto分页转换
     *
     * @param ${table_name_first_low}DOPageable ${table_name_first_low}DOPageable
     * @return ${table_name}DTO
     */
    PageableDTO<${table_name}DTO> pageDoToDto(Pageable<${table_name}DO> ${table_name_first_low}DOPageable);
}