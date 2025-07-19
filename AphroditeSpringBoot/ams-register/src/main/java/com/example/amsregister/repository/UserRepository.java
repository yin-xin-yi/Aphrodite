package com.example.amsregister.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.amsregister.model.Users;

/**
 * @Repository 数据仓库组件
 *
 * 1. Spring 的组件扫描 扫描到这个类，并将它的实例创建出来，放入 Spring 的 IoC 容器中进行管理 2. 一个 JDBC 异常
 * DataIntegrityViolationException 通用异常 好处理
 *
 * JpaRepository<Users, Long> 白嫖方法 save(S entity): 保存一个实体（新增或更新）。
 * saveAll(Iterable<S> entities): 保存多个实体。 findById(ID id): 根据主键 ID 查找一个实体，返回
 * Optional<T>。 existsById(ID id): 判断指定 ID 的实体是否存在。 findAll(): 查找所有实体。
 * findAllById(Iterable<ID> ids): 根据多个 ID 查找实体。 count(): 统计实体总数。 deleteById(ID
 * id): 根据 ID 删除一个实体。 delete(T entity): 删除一个实体。 deleteAll(): 删除所有实体。 b. 来自
 * PagingAndSortingRepository 的分页和排序功能 findAll(Sort sort): 查找所有实体并排序。
 * findAll(Pageable pageable): 查找所有实体并进行分页（返回一个 Page<T> 对象，其中包含了总页数、总记录数等信息）。 c.
 * 来自 JpaRepository 的 JPA 特定功能 findAll(): (覆盖了 CrudRepository 的方法) 返回
 * List<T>，更常用。 flush(): 将缓存中的更改立刻同步到数据库。 saveAndFlush(S entity): 保存并立刻同步。
 * deleteInBatch(Iterable<T> entities): 批量删除，性能更高。 getOne(ID id) / getById(ID
 * id): 获取一个实体的代理引用（懒加载）
 *
 */
/**
 * 1. Long 这个对应地 主键
 */
@Repository
public interface UserRepository extends JpaRepository<Users, Long> {

    Optional<Users> findByUsername(String username);

    Optional<Users> findByEmail(String email);
}
