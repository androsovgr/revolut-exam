package ru.revolut.androsovgr.exception.dao;

public class RevolutJdbcConnector extends JdbcConnector {
    public RevolutJdbcConnector() {
        super("jdbc:mysql://localhost:3306/revolut", "revolut", "revolut");
    }
}
