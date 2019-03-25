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

package br.com.simpleOrm.sql;

import br.com.simpleOrm.EntityMetaData;
import br.com.simpleOrm.database.DB;
import br.com.simpleOrm.logging.LogManager;
import br.com.simpleOrm.util.ReflectionUtil;

import static br.com.simpleOrm.EntityUtil.getMetaDataOf;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Select extends Sql {

    private static final Logger logger = LogManager.getLogger(Select.class.getName());
    private Class<? extends Object> entityClass;
    private EntityMetaData emd;

    public Select(Class<? extends Object> entityClass) {
        this.entityClass = entityClass;
        this.emd = getMetaDataOf(entityClass);
    }

    @Override
    public List<? extends Object> exec() {
        StringBuilder sql = new StringBuilder("SELECT ");
        sql.append(String.join(", ", emd.getColumnsList())).append(" ");
        sql.append("FROM ").append(emd.getTableName());
        sql.append(! where.isEmpty() ? " WHERE " + where : " ");

        ResultSet rs = new DB(emd.getDbName()).executeSelect(sql.toString(), whereValues);
        List<Object> result = new ArrayList<>();
        try {
            //Constructor<T> constructor = entityClass.getConstructor();
            while (rs.next()) {
                Object entity = ReflectionUtil.createInstance(entityClass);
                for (int i = 1; i <= rs.getMetaData().getColumnCount(); i++) {
                    //todo implementar lógica
                    setObjectValue(
                            emd.getattributeNameByDbColumnName(rs.getMetaData().getColumnLabel(i)),
                            entity, rs.getObject(i)
                    );
                }
                result.add(entity);
            }
        } catch (Exception e) {
            logger.log(Level.SEVERE, e.getMessage());
        }
        return result;
    }
}
