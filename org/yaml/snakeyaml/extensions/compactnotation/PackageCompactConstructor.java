//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\User\Desktop\1.12 stable mappings"!

//Decompiled by Procyon!

package org.yaml.snakeyaml.extensions.compactnotation;

public class PackageCompactConstructor extends CompactConstructor
{
    private String packageName;
    
    public PackageCompactConstructor(final String packageName) {
        this.packageName = packageName;
    }
    
    protected Class<?> getClassForName(final String name) throws ClassNotFoundException {
        if (name.indexOf(46) < 0) {
            try {
                final Class<?> clazz = Class.forName(this.packageName + "." + name);
                return clazz;
            }
            catch (ClassNotFoundException ex) {}
        }
        return (Class<?>)super.getClassForName(name);
    }
}
