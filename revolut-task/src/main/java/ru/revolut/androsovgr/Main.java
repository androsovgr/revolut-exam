package ru.revolut.androsovgr;

import lombok.extern.slf4j.Slf4j;
import ru.revolut.androsovgr.exception.dao.JdbcConnector;
import ru.revolut.androsovgr.exception.dao.RevolutJdbcConnector;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

@Slf4j
public class Main {
    public static void main(String[] args) {
        JdbcConnector jdbcConnector = new RevolutJdbcConnector();
        jdbcConnector.doWork(con -> {
            try (PreparedStatement ps = con.prepareStatement("INSERT INTO revolut.test_table (tt_value) values(?)")) {
                ps.setString(1, "value");
                ps.executeUpdate();
            }
            try (PreparedStatement ps = con.prepareStatement("SELECT * FROM revolut.test_table")) {
                try (ResultSet rs = ps.executeQuery()) {
                    while (rs.next()) {
                        log.info("pk={}, value={}", rs.getInt(1), rs.getString(2));
                    }
                }
            }
        });
    }
}
