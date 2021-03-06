//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\User\Desktop\1.12 stable mappings"!

//Decompiled by Procyon!

package javassist;

import java.lang.ref.*;
import java.io.*;
import java.net.*;

public class LoaderClassPath implements ClassPath
{
    private WeakReference clref;
    
    public LoaderClassPath(final ClassLoader cl) {
        this.clref = new WeakReference((T)cl);
    }
    
    @Override
    public String toString() {
        Object cl = null;
        if (this.clref != null) {
            cl = this.clref.get();
        }
        return (cl == null) ? "<null>" : cl.toString();
    }
    
    public InputStream openClassfile(final String classname) {
        final String cname = classname.replace('.', '/') + ".class";
        final ClassLoader cl = (ClassLoader)this.clref.get();
        if (cl == null) {
            return null;
        }
        return cl.getResourceAsStream(cname);
    }
    
    public URL find(final String classname) {
        final String cname = classname.replace('.', '/') + ".class";
        final ClassLoader cl = (ClassLoader)this.clref.get();
        if (cl == null) {
            return null;
        }
        return cl.getResource(cname);
    }
    
    public void close() {
        this.clref = null;
    }
}
