package ${package_name}.service;

import ${package_name}.dto.${table_name}CreateDTO;
import ${package_name}.dto.${table_name}DTO;
import ${package_name}.dto.${table_name}UpdateDTO;
import ${package_name}.dto.${table_name}CreateOrUpdateDTO;
import ${package_name}.infra.dataobject.${table_name}DO;
import ${package_name}.infra.repository.${table_name}Repository;
import ${package_name}.mapper.${table_name}Mapper;
import org.dinghuang.core.dto.PageableDTO;
import org.dinghuang.core.dto.PageableSearchDTO;
import org.dinghuang.core.exception.DeleteException;
import org.dinghuang.core.exception.InsertException;
import org.dinghuang.core.exception.UpdateException;
import org.dinghuang.core.mapper.PageableMapper;
import org.dinghuang.core.mybatis.model.Pageable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * ${table_annotation}Service
 *
 * @author ${author}
 * @since ${date}
 */
@Service
public class ${table_name}Service {

    @Autowired
    private ${table_name}Repository ${table_name_first_low}Repository;

    @Transactional(rollbackFor = Exception.class)
    public ${table_name}DTO create(${table_name}CreateDTO ${table_name_first_low}CreateDTO) {
        ${table_name}DO ${table_name_first_low}DO = ${table_name}Mapper.INSTANCE.createDtoToDo(${table_name_first_low}CreateDTO);
        if (${table_name_first_low}Repository.insert(${table_name_first_low}DO) == 1) {
            return queryById(${table_name_first_low}DO.get${priMaryName}());
        } else {
            throw new InsertException("error.${table_name}.create");
        }
    }

    @Transactional(rollbackFor = Exception.class)
    public ${table_name}DTO update(${table_name}UpdateDTO ${table_name_first_low}UpdateDTO) {
        ${table_name}DO ${table_name_first_low}DO = ${table_name}Mapper.INSTANCE.updateDtoToDo(${table_name_first_low}UpdateDTO);
        if (${table_name_first_low}Repository.updateById(${table_name_first_low}DO) == 1) {
            return queryById(${table_name_first_low}DO.get${priMaryName}());
        } else {
            throw new UpdateException("error.${table_name}.update");
        }
    }

    public ${table_name}DTO queryById(Long id) {
        return ${table_name}Mapper.INSTANCE.doToDto(${table_name_first_low}Repository.selectById(id));
    }

    @Transactional(rollbackFor = Exception.class)
    public void deleteById(Long id) {
        if (${table_name_first_low}Repository.deleteById(id) != 1) {
            throw new DeleteException("error.${table_name}.deleteById");
        }
    }

    @SuppressWarnings("unchecked")
    public PageableDTO<${table_name}DTO> queryByPage(PageableSearchDTO pageableSearchDTO) {
        Pageable<${table_name}DO> ${table_name_first_low}DOPageable = ${table_name_first_low}Repository.selectPageCondition(PageableMapper.INSTANCE.searchDtoToDo(pageableSearchDTO), null);
        return ${table_name}Mapper.INSTANCE.pageDoToDto(${table_name_first_low}DOPageable);
    }

    @Transactional(rollbackFor = Exception.class)
    public List<${table_name}DTO> batchCreateOrUpdate(List<${table_name}CreateOrUpdateDTO> ${table_name_first_low}CreateOrUpdateDTOS) {
        List<${table_name}DTO> ${table_name_first_low}DTOS = Collections.synchronizedList(new ArrayList<>(${table_name_first_low}CreateOrUpdateDTOS.size()));
        ${table_name_first_low}CreateOrUpdateDTOS.forEach(${table_name_first_low}UpdateDTO -> {
            ${table_name}DO ${table_name_first_low}DO = ${table_name}Mapper.INSTANCE.createOrUpdateDtoToDo(${table_name_first_low}UpdateDTO);
            ${table_name_first_low}Repository.insertOrUpdate(${table_name_first_low}DO);
            ${table_name_first_low}DTOS.add(queryById(${table_name_first_low}DO.getId()));
        });
        return ${table_name_first_low}DTOS;
    }

    @Transactional(rollbackFor = Exception.class)
    public void batchDeleted(List<Long> ids) {
        ids.forEach(this::deleteById);
    }
}
