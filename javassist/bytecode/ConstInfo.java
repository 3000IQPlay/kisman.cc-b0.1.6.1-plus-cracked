//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\User\Desktop\1.12 stable mappings"!

//Decompiled by Procyon!

package javassist.bytecode;

import java.util.*;
import java.io.*;

abstract class ConstInfo
{
    int index;
    
    public ConstInfo(final int i) {
        this.index = i;
    }
    
    public abstract int getTag();
    
    public String getClassName(final ConstPool cp) {
        return null;
    }
    
    public void renameClass(final ConstPool cp, final String oldName, final String newName, final HashMap cache) {
    }
    
    public void renameClass(final ConstPool cp, final Map classnames, final HashMap cache) {
    }
    
    public abstract int copy(final ConstPool p0, final ConstPool p1, final Map p2);
    
    public abstract void write(final DataOutputStream p0) throws IOException;
    
    public abstract void print(final PrintWriter p0);
    
    @Override
    public String toString() {
        final ByteArrayOutputStream bout = new ByteArrayOutputStream();
        final PrintWriter out = new PrintWriter(bout);
        this.print(out);
        return bout.toString();
    }
}
