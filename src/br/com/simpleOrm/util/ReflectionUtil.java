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

package br.com.simpleOrm.util;

import java.lang.reflect.Constructor;

public class ReflectionUtil {

    public static <T> T createInstance(Class<T> clazz) {
        T instance;
        try {
            Class.forName(clazz.getName());
            Constructor<T> constructor = clazz.getConstructor();
            instance = constructor.newInstance();
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }

        return instance;
    }

    public static <T> T callMethod(T object, String method, Object... arguments) {
        T _return = null;

        try {
            Class<?> clazz = Class.forName(object.getClass().getName());
            if (arguments != null) {
                Object[] params = new Object[arguments.length];
                Class<?>[] paramTypes = new Class<?>[arguments.length];
                for(int i = 0; i < arguments.length; i++) {
                    params[i] = arguments[i];
                    paramTypes[i] = arguments[i].getClass();
                }

                _return = (T)clazz.getMethod(method, paramTypes).invoke(object, arguments);
            }
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }

        return _return;
    }
}
