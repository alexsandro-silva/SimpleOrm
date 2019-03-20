/*
 * Copyright 2019 alexs.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

package br.com.simpleOrm.database;

import br.com.simpleOrm.logging.LogManager;
import br.com.simpleOrm.sql.Select;

import java.sql.*;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DB {

    private static final Logger logger = LogManager.getLogger(DB.class.getName());
    private final String dbName;

    public static final String DEFAULT = "DEFAULT";

    public DB(String dbName) {
        this.dbName = dbName;
    }

    public DB() {
        this.dbName = DEFAULT;
    }

    public DB open(String driver, String url, String user, String password) {
        try {
            Class.forName(driver);
            Connection connection = DriverManager.getConnection(url, user, password);
            ConnectionManager.addConnection(dbName, connection);
        } catch (ClassNotFoundException | SQLException e) {
            logger.log(Level.SEVERE, "Falha na conexão com a base {0}", dbName);
            throw new RuntimeException(String.format("Falha na conexão com a base %s", dbName));
        }

        return this;
    }

    public ResultSet executeSelect(String sql, List<Object> parameters) {
        Connection connection = ConnectionManager.getConnection(dbName);
        ResultSet rs = null;
        PreparedStatement preparedStatement = this.prepareStatement(sql, parameters);
        try {
            logger.log(Level.INFO, String.format("Executando a query %s na base %s", sql, this.dbName));
            rs = preparedStatement.executeQuery();
        } catch (SQLException e) {
            logger.log(Level.SEVERE, e.getMessage());
        }

        return rs;
    }

    PreparedStatement prepareStatement(String sql, List<Object> parameters) {
        PreparedStatement ps = null;
        try {
            ps = ConnectionManager.getConnection(dbName).prepareStatement(sql);
            for (int i = 0; i < parameters.size(); i++) {
                ps.setObject(i + 1, parameters.get(i));
            }
        } catch (SQLException e) {
            logger.log(Level.SEVERE, e.getMessage());
        }

        return ps;
    }

    public Select select(Class<? extends Object> tClass) {
        Select select = new Select(tClass);
        return select;
    }
}
