public interface CustomRepositoryOperations<T> {
    String getTableName();
    String getSchemaName();
    void restartSequence();
    void truncateTable();
}
