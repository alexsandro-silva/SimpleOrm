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

import br.com.simpleOrm.ColumnMetaData;
import br.com.simpleOrm.EntityMetaData;
import br.com.simpleOrm.EntityUtil;

public class Run {

    public static void main(String[] args) {
        Hero hero = new Hero();
        hero.setId(1);
        hero.setCivilianName("Peter Parker");
        hero.setHeroName("Spiderman");
        EntityMetaData mmd = EntityUtil.getMetaDataOf(hero);

        System.out.println(mmd.getDbName());
        System.out.println(mmd.getTableName());

        for (ColumnMetaData cmd : mmd.getColumns()) {
            System.out.println(cmd.getName() + " - " + cmd.getValue() + " - " + cmd.isId());
        }

        mmd.getColumnsAndValues().forEach((key, value) -> System.out.println(key + " = " + value));
    }
}
