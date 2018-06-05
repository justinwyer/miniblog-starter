package mcb.blogs.publisher;

import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface UserListRepository extends CrudRepository<UserList, Long> {
    @Override
    <S extends UserList> S save(S s);

    @Override
    <S extends UserList> Iterable<S> saveAll(Iterable<S> iterable);

    @Override
    Optional<UserList> findById(Long aLong);

    @Override
    boolean existsById(Long aLong);

    @Override
    Iterable<UserList> findAll();

    @Override
    Iterable<UserList> findAllById(Iterable<Long> iterable);

    @Override
    long count();

    @Override
    void deleteById(Long aLong);

    @Override
    void delete(UserList userList);

    @Override
    void deleteAll(Iterable<? extends UserList> iterable);

    @Override
    void deleteAll();
}
