/*
 *  Copyright 2019 alexsandro.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 *
 */

package br.com.simpleOrm.database;

import br.com.simpleOrm.sql.Select;

public class Base {

    public static DB open(String driver, String url, String user, String password) {
        DB db = new DB();
        return db.open(driver, url, user, password);
    }

    public static Select select(Class<? extends Object> tClass) {
        return new DB().select(tClass);
    }
}
