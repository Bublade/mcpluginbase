/*
 * Copyright (c) 2021 bublade
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package com.bubladecoding.developertools;

import com.bubladecoding.developertools.database.SqlManager;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import java.sql.Connection;
import java.sql.SQLException;

public class HikariSqlManager  implements SqlManager {

    private final DeveloperToolsPlugin plugin;

    private final String host, database, username, password;
    private final int port;
    private HikariDataSource hikariDataSource;

    public HikariSqlManager(DeveloperToolsPlugin plugin) {
        this.plugin = plugin;
        this.host = "localhost";
        this.database = "developertools";
        this.username = "root";
        this.password = "";
        this.port = 3306;
    }


    @Override
    public void openConnection() {
        if (isConnected()) {
            plugin.getServer().getConsoleSender().sendMessage("Already connected to the database!");
            return;
        }

        HikariConfig hikariConfig = new HikariConfig();
        hikariConfig.setDataSourceClassName("org.mariadb.jdbc.MariaDbDataSource");
        hikariConfig.addDataSourceProperty("serverName", host);
        hikariConfig.addDataSourceProperty("port", port);
        hikariConfig.addDataSourceProperty("databaseName", database);
        hikariConfig.setUsername(username);
        hikariConfig.setPassword(password);
        hikariConfig.setPoolName("developertools");

        // Other Settings
        hikariConfig.setMinimumIdle(2);
        hikariConfig.setMaximumPoolSize(10);
        hikariConfig.setIdleTimeout(600000);
        hikariConfig.setConnectionTimeout(30000);

        hikariDataSource = new HikariDataSource(hikariConfig);
        plugin.getServer().getConsoleSender().sendMessage("Successful connected with the database!");

    }

    @Override
    public Connection getConnection() throws SQLException {
        return hikariDataSource.getConnection();
    }

    @Override
    public boolean isConnected() {
        return (hikariDataSource != null && !hikariDataSource.isClosed());
    }
}
