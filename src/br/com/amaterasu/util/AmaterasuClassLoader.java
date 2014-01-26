/*
 * To change this template, choose Tools | Templates and open the template in
 * the editor.
 */
package br.com.amaterasu.util;

import java.lang.reflect.InvocationTargetException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;

/**
 *
 * @author Maykon
 */
public class AmaterasuClassLoader {

    private String jarFile, classe;

    public AmaterasuClassLoader(String jarFile, String classe) {
        this.jarFile = jarFile;
        this.classe = classe;
    }

    public Object getInstance() throws ClassNotFoundException, IllegalAccessException, InstantiationException, NoSuchMethodException, IllegalArgumentException, InvocationTargetException, MalformedURLException {
        return newInstance();
    }

    private Object newInstance() throws ClassNotFoundException, IllegalAccessException, InstantiationException, NoSuchMethodException, IllegalArgumentException, InvocationTargetException, MalformedURLException {
        URL[] url = {new URL(jarFile)};
        URLClassLoader classLoader = URLClassLoader.newInstance(url);
        Class c = classLoader.loadClass(classe);
        return c.getConstructor().newInstance();
    }
}
