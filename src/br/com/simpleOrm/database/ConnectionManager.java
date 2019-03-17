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

import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

class ConnectionManager {

    private static final Logger logger = LogManager.getLogger(ConnectionManager.class.getName());

    private static ThreadLocal<Map<String, Connection>> connections = new ThreadLocal<>();

    static Map<String, Connection> getConnections() {
        if (connections.get() == null) {
            connections.set(new HashMap<>());
        }

        return connections.get();
    }

    static Connection getConnection(String dbName) {
        return getConnections().get(dbName);
    }

    static void addConnection(String dbName, Connection connection) {
        if (getConnections().get(dbName) != null) {
            throw new RuntimeException(String.format("A conexão %s já existe na Thread local", dbName));
        }
        getConnections().put(dbName, connection);
        logger.log(Level.INFO, String.format("A conexão %s foi incluída na Thread local", dbName));
    }

    static void removeConnection(String dbName) {
        logger.log(Level.INFO, String.format("A conexão %s foi removida da Thread local", dbName));
        getConnections().remove(dbName);
    }
}
