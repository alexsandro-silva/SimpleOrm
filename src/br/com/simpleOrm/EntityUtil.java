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

import br.com.simpleOrm.annotations.*;
import br.com.simpleOrm.database.DB;
import org.jetbrains.annotations.NotNull;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EntityUtil {

    public static <T> EntityMetaData getMetaDataOf(@NotNull T t) {
        if (! isEntity(t.getClass()))
            throw new RuntimeException(String.format("A classe %s não representa uma entidade", t.getClass().getName()));

        String dbName = getDbName(t.getClass());
        String tableName = getTableName(t.getClass());
        List<ColumnMetaData> columns = getColumns(t);

        EntityMetaData modelMetaData = new EntityMetaData(dbName, tableName, columns);

        return modelMetaData;
    }

    private static <T> String getDbName(@NotNull Class<T> tClass) {
        if (tClass.isAnnotationPresent(Database.class)) {
            Annotation annotation = tClass.getAnnotation(Database.class);
            return ((Database) annotation).value();
        }

        return DB.DEFAULT;
    }

    private static <T> String getTableName(@NotNull Class<T> tClass) {
        if (tClass.isAnnotationPresent(Table.class)) {
            Annotation annotation = tClass.getAnnotation(Table.class);
            return ((Table) annotation).value();
        }

        return tClass.getSimpleName();
    }

    private static <T> List<ColumnMetaData> getColumns(@NotNull T t) {
        List<ColumnMetaData> metaData = new ArrayList<>();
        for (Field field : t.getClass().getDeclaredFields()) {
            if (field.isAnnotationPresent(Column.class)) {
                field.setAccessible(true);
                ColumnMetaData columnMetaData = new ColumnMetaData();
                columnMetaData.setName(((Column) field.getAnnotation(Column.class)).name());
                columnMetaData.setJavaName(field.getName());
                try {
                    columnMetaData.setValue(field.get(t));
                } catch (IllegalAccessException e) {
                    throw new RuntimeException(e.getMessage());
                }
                columnMetaData.setId(field.isAnnotationPresent(Id.class));
                metaData.add(columnMetaData);
            }
        }

        return metaData;
    }

    private static <T> boolean isEntity(@NotNull Class<T> tClass) {
        return tClass.isAnnotationPresent(Entity.class);
    }
}