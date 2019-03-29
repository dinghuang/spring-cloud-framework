package ${package_name}.infra.repository;

import ${package_name}.infra.dataobject.${table_name}DO;
import org.dinghuang.core.annotation.Repository;
import org.dinghuang.core.mybatis.BaseRepository;

/**
 * ${table_annotation}Repository
 *
 * @author ${author}
 * @since ${date}
 */
@Repository
public interface ${table_name}Repository extends BaseRepository<${table_name}DO> {

}