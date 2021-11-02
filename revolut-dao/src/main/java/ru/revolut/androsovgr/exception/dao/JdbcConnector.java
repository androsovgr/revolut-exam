package ru.revolut.androsovgr.exception.dao;

import com.mysql.cj.jdbc.MysqlDataSource;
import ru.revolut.androsovgr.exception.RevolutException;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

public class JdbcConnector {

    private final DataSource ds;

    public JdbcConnector(String url, String username, String password) {
        MysqlDataSource ds = new MysqlDataSource();
        ds.setURL(url);
        ds.setUser(username);
        ds.setPassword(password);
        this.ds = ds;
    }

    public void doWork(Work w) {
        try (Connection con = ds.getConnection()) {
            con.setAutoCommit(false);
            doWork(w, con);
        } catch (Exception e) {
            if (e instanceof RevolutException) {
                throw (RevolutException) e;
            } else {
                throw new RevolutException("Ошибка при взаимодействии с БД", e);
            }
        }
    }

    private void doWork(Work w, Connection con) throws SQLException {
        try {
            w.doWork(con);
            con.commit();
        } catch (Exception e) {
            try {
                con.rollback();
            } catch (Exception e1) {
                e.addSuppressed(e);
            }
        }
    }

    public interface Work {
        void doWork(Connection con) throws SQLException;
    }
}
