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

package br.com.simpleOrm;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EntityMetaData {

    private String dbName;
    private String tableName;
    private List<ColumnMetaData> columns;

    public EntityMetaData(String dbName, String tableName, List<ColumnMetaData> columns) {
        this.dbName = dbName;
        this.tableName = tableName;
        this.columns = columns;
    }

    public String getDbName() {
        return dbName;
    }

    public void setDbName(String dbName) {
        this.dbName = dbName;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public List<ColumnMetaData> getColumns() {
        return columns;
    }

    public void setColumns(List<ColumnMetaData> columns) {
        this.columns = columns;
    }

    public Map<String, Object> getColumnsAndValues() {
        Map<String, Object> columnsAndValues = new HashMap<>();
        getColumns().forEach(column -> {
            columnsAndValues.put(column.getName(), column.getValue());
        });

        return columnsAndValues;
    }

    public List<String> getColumnsList() {
        List<String> columns = new ArrayList<>();
        getColumns().forEach(column -> {
            columns.add(column.getName());
        });

        return columns;
    }

    public String getattributeNameByDbColumnName(String dbColumnName) {
        String javaName = "";
        for (int i = 0; i < getColumns().size(); i++) {
            if (getColumns().get(i).getName().equals(dbColumnName)) {
                javaName = getColumns().get(i).getJavaName();
                break;
            }
        }
        return javaName;
    }
}
