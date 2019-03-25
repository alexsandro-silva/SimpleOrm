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

import br.com.simpleOrm.database.Base;
import br.com.simpleOrm.database.DB;

import java.util.List;

public class TesteDB {

    public static void main(String[] args) {
        Base.open("org.mariadb.jdbc.Driver", "jdbc:mariadb://localhost/heroes", "root", "");

        List<? extends Object> heroes = Base.select(Hero.class).exec();

        for (int i = 1; i <heroes.size(); i++) {
            String.format("ID: %s - %s - %s", heroes.get(i), heroes.get(2), heroes.get((3)));
        }
        heroes.forEach((h) -> {
            Hero hero = (Hero) h;
            System.out.println(String.format("ID: %s - %s - %s", hero.getId(), hero.getCivilianName(), hero.getHeroName()));
        });



    }
}
