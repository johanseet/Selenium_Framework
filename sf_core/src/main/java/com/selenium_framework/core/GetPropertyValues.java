/*
 * Copyright 2020 Johanseet Ramírez (https://github.com/johanseet)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.selenium_framework.core;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class GetPropertyValues {
    private static Logger logger = LogManager.getLogger(GetPropertyValues.class);

    /**
     * Método que devuelve el valor de una propiedad en el archivo de configuración solicitado
     *
     * @param propertyFile Nombre del archivo de propiedades
     * @param propertyName Nombre de la propiedad
     * @return Valor de la propiedad
     * @throws IOException
     */
    public static String getPropertyValue(String propertyFile, String propertyName) throws Throwable {
        String property_value = null;
        InputStream inputStream = null;

        try {
            Properties prop = new Properties();

            inputStream = GetPropertyValues.class.getClassLoader().getResourceAsStream(propertyFile);

            if (inputStream != null) {
                prop.load(inputStream);
            } else {
                throw new FileNotFoundException("El archivo de propiedades '" + propertyFile + "' no se encuentra en el classpath");
            }

            property_value = prop.getProperty(propertyName);

            if (property_value == null) {
                throw new NullPointerException("El archivo de propiedades '" + propertyFile + "' no tiene la propiedad: " + propertyName);
            }

        } catch (Throwable t) {
            throw logger.throwing(t);
        } finally {
            inputStream.close();
        }
        return property_value;
    }
}