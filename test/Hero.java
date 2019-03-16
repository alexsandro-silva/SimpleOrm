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

import br.com.simpleOrm.annotations.Column;
import br.com.simpleOrm.annotations.Id;
import br.com.simpleOrm.annotations.Table;

@Table("Hero")
public class Hero {
    @Id
    @Column(name = "id")
    private int id;
    @Column(name = "civilian_name")
    private String civilianName;
    @Column(name = "hero_name")
    private String heroName;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCivilianName() {
        return civilianName;
    }

    public void setCivilianName(String civilianName) {
        this.civilianName = civilianName;
    }

    public String getHeroName() {
        return heroName;
    }

    public void setHeroName(String heroName) {
        this.heroName = heroName;
    }
}
