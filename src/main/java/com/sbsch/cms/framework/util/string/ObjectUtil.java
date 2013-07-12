/*
 * Copyright (c) SBSContentsHub.
 * All right reserved.
 *
 * This software is the confidential and proprietary information of SBSContentsHub.
 * You shall not disclose such Confidential Information and
 * shall use it only in accordance with the terms of the license agreement
 * you entered into with SBSContentsHub.
 */
package com.sbsch.cms.framework.util.string;

import java.lang.reflect.Constructor;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * The Class ObjectUtil.
 */
public class ObjectUtil {

    /** The log. */
    private static Log log = LogFactory.getLog(ObjectUtil.class);

    /**
	 * Instantiates a new object util.
	 */
    private ObjectUtil() {

    }

    /**
	 * Load class.
	 * 
	 * @param className the class name
	 * @return the class
	 * @throws ClassNotFoundException the class not found exception
	 * @throws Exception the exception
	 */
    public static Class<?> loadClass(String className)
            throws ClassNotFoundException, Exception {

        Class<?> clazz = null;
        try {
            clazz =
                Thread.currentThread().getContextClassLoader().loadClass(
                    className);
        } catch (ClassNotFoundException e) {
            throw new ClassNotFoundException();
        } catch (Exception e) {
            throw new Exception(e);
        }

        if (clazz == null) {
            clazz = Class.forName(className);
        }

        return clazz;

    }

    /**
	 * Instantiate.
	 * 
	 * @param className the class name
	 * @return the object
	 * @throws ClassNotFoundException the class not found exception
	 * @throws InstantiationException the instantiation exception
	 * @throws IllegalAccessException the illegal access exception
	 * @throws Exception the exception
	 */
    public static Object instantiate(String className)
            throws ClassNotFoundException, InstantiationException,
            IllegalAccessException, Exception {
        Class<?> clazz;

        try {
            clazz = loadClass(className);
            return clazz.newInstance();
        } catch (ClassNotFoundException e) {
            if (log.isErrorEnabled())
                log.error(className + " : Class is can not instantialized.");
            throw new ClassNotFoundException();
        } catch (InstantiationException e) {
            if (log.isErrorEnabled())
                log.error(className + " : Class is can not instantialized.");
            throw new InstantiationException();
        } catch (IllegalAccessException e) {
            if (log.isErrorEnabled())
                log.error(className + " : Class is not accessed.");
            throw new IllegalAccessException();
        } catch (Exception e) {
            if (log.isErrorEnabled())
                log.error(className + " : Class is not accessed.");
            throw new Exception(e);
        }
    }

    /**
	 * Instantiate.
	 * 
	 * @param className the class name
	 * @param types the types
	 * @param values the values
	 * @return the object
	 * @throws ClassNotFoundException the class not found exception
	 * @throws InstantiationException the instantiation exception
	 * @throws IllegalAccessException the illegal access exception
	 * @throws Exception the exception
	 */
    public static Object instantiate(String className, String[] types,
            Object[] values) throws ClassNotFoundException,
            InstantiationException, IllegalAccessException, Exception {
        Class<?> clazz;
        Class<?>[] classParams = new Class[values.length];
        Object[] objectParams = new Object[values.length];

        try {
            clazz = loadClass(className);

            for (int i = 0; i < values.length; i++) {
                classParams[i] = loadClass(types[i]);
                objectParams[i] = values[i];
            }

            Constructor<?> constructor = clazz.getConstructor(classParams);
            return constructor.newInstance(values);

        } catch (ClassNotFoundException e) {
            if (log.isErrorEnabled())
                log.error(className + " : Class is can not instantialized.");
            throw new ClassNotFoundException();
        } catch (InstantiationException e) {
            if (log.isErrorEnabled())
                log.error(className + " : Class is can not instantialized.");
            throw new InstantiationException();
        } catch (IllegalAccessException e) {
            if (log.isErrorEnabled())
                log.error(className + " : Class is not accessed.");
            throw new IllegalAccessException();
        } catch (Exception e) {
            if (log.isErrorEnabled())
                log.error(className + " : Class is not accessed.");
            throw new Exception(e);
        }
    }

    /**
	 * Checks if is null.
	 * 
	 * @param object the object
	 * @return true, if is null
	 */
    public static boolean isNull(Object object) {
        return ((object == null) || object.equals(null));
    }
}
