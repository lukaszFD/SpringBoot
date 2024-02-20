@Repository
public interface CustomRepository<T> extends JpaRepository<T, Long>, CustomRepositoryOperations<T> {
}
