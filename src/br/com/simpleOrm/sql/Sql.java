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

import br.com.simpleOrm.util.ReflectionUtil;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public abstract class Sql {

    protected String where = new String();
    protected List<Object> whereValues = new ArrayList<>();

    public Sql where(String condition, Object... parameters) {
        if (condition == null || condition.isEmpty() || parameters.length == 0) {
            throw new IllegalArgumentException("Parâmetros da cláusula where não fornecidos");
        }

        where = condition;
        whereValues.addAll(Arrays.asList(parameters));

        return this;
    }

    protected Object setObjectValue(String attributeName, Object object, Object value) {
        String setter = getSetterName(attributeName);
        return ReflectionUtil.callMethod(object, setter, value);
    }

    protected String getSetterName(String name) {
        return "set".concat(name.substring(0, 1).toUpperCase()).concat(name.substring(1, name.length()));
    }

    public abstract List<? extends Object> exec();
}
