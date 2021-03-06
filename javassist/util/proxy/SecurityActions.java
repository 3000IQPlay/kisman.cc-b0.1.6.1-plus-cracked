//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\User\Desktop\1.12 stable mappings"!

//Decompiled by Procyon!

package javassist.util.proxy;

import java.security.*;
import java.lang.reflect.*;

class SecurityActions
{
    static Method[] getDeclaredMethods(final Class clazz) {
        if (System.getSecurityManager() == null) {
            return clazz.getDeclaredMethods();
        }
        return AccessController.doPrivileged((PrivilegedAction<Method[]>)new PrivilegedAction() {
            @Override
            public Object run() {
                return clazz.getDeclaredMethods();
            }
        });
    }
    
    static Constructor[] getDeclaredConstructors(final Class clazz) {
        if (System.getSecurityManager() == null) {
            return clazz.getDeclaredConstructors();
        }
        return AccessController.doPrivileged((PrivilegedAction<Constructor[]>)new PrivilegedAction() {
            @Override
            public Object run() {
                return clazz.getDeclaredConstructors();
            }
        });
    }
    
    static Method getDeclaredMethod(final Class clazz, final String name, final Class[] types) throws NoSuchMethodException {
        if (System.getSecurityManager() == null) {
            return clazz.getDeclaredMethod(name, (Class[])types);
        }
        try {
            return AccessController.doPrivileged((PrivilegedExceptionAction<Method>)new PrivilegedExceptionAction() {
                @Override
                public Object run() throws Exception {
                    return clazz.getDeclaredMethod(name, (Class[])types);
                }
            });
        }
        catch (PrivilegedActionException e) {
            if (e.getCause() instanceof NoSuchMethodException) {
                throw (NoSuchMethodException)e.getCause();
            }
            throw new RuntimeException(e.getCause());
        }
    }
    
    static Constructor getDeclaredConstructor(final Class clazz, final Class[] types) throws NoSuchMethodException {
        if (System.getSecurityManager() == null) {
            return clazz.getDeclaredConstructor((Class[])types);
        }
        try {
            return AccessController.doPrivileged((PrivilegedExceptionAction<Constructor>)new PrivilegedExceptionAction() {
                @Override
                public Object run() throws Exception {
                    return clazz.getDeclaredConstructor((Class[])types);
                }
            });
        }
        catch (PrivilegedActionException e) {
            if (e.getCause() instanceof NoSuchMethodException) {
                throw (NoSuchMethodException)e.getCause();
            }
            throw new RuntimeException(e.getCause());
        }
    }
    
    static void setAccessible(final AccessibleObject ao, final boolean accessible) {
        if (System.getSecurityManager() == null) {
            ao.setAccessible(accessible);
        }
        else {
            AccessController.doPrivileged((PrivilegedAction<Object>)new PrivilegedAction() {
                @Override
                public Object run() {
                    ao.setAccessible(accessible);
                    return null;
                }
            });
        }
    }
    
    static void set(final Field fld, final Object target, final Object value) throws IllegalAccessException {
        if (System.getSecurityManager() == null) {
            fld.set(target, value);
        }
        else {
            try {
                AccessController.doPrivileged((PrivilegedExceptionAction<Object>)new PrivilegedExceptionAction() {
                    @Override
                    public Object run() throws Exception {
                        fld.set(target, value);
                        return null;
                    }
                });
            }
            catch (PrivilegedActionException e) {
                if (e.getCause() instanceof NoSuchMethodException) {
                    throw (IllegalAccessException)e.getCause();
                }
                throw new RuntimeException(e.getCause());
            }
        }
    }
}
