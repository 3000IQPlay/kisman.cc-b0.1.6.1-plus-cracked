//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\User\Desktop\1.12 stable mappings"!

//Decompiled by Procyon!

package javassist.scopedpool;

import javassist.*;
import java.util.*;

public class ScopedClassPoolRepositoryImpl implements ScopedClassPoolRepository
{
    private static final ScopedClassPoolRepositoryImpl instance;
    private boolean prune;
    boolean pruneWhenCached;
    protected Map registeredCLs;
    protected ClassPool classpool;
    protected ScopedClassPoolFactory factory;
    
    public static ScopedClassPoolRepository getInstance() {
        return (ScopedClassPoolRepository)ScopedClassPoolRepositoryImpl.instance;
    }
    
    private ScopedClassPoolRepositoryImpl() {
        this.prune = true;
        this.registeredCLs = Collections.synchronizedMap(new WeakHashMap<Object, Object>());
        this.factory = (ScopedClassPoolFactory)new ScopedClassPoolFactoryImpl();
        this.classpool = ClassPool.getDefault();
        final ClassLoader cl = Thread.currentThread().getContextClassLoader();
        this.classpool.insertClassPath((ClassPath)new LoaderClassPath(cl));
    }
    
    public boolean isPrune() {
        return this.prune;
    }
    
    public void setPrune(final boolean prune) {
        this.prune = prune;
    }
    
    public ScopedClassPool createScopedClassPool(final ClassLoader cl, final ClassPool src) {
        return this.factory.create(cl, src, (ScopedClassPoolRepository)this);
    }
    
    public ClassPool findClassPool(final ClassLoader cl) {
        if (cl == null) {
            return this.registerClassLoader(ClassLoader.getSystemClassLoader());
        }
        return this.registerClassLoader(cl);
    }
    
    public ClassPool registerClassLoader(final ClassLoader ucl) {
        synchronized (this.registeredCLs) {
            if (this.registeredCLs.containsKey(ucl)) {
                return this.registeredCLs.get(ucl);
            }
            final ScopedClassPool pool = this.createScopedClassPool(ucl, this.classpool);
            this.registeredCLs.put(ucl, pool);
            return (ClassPool)pool;
        }
    }
    
    public Map getRegisteredCLs() {
        this.clearUnregisteredClassLoaders();
        return this.registeredCLs;
    }
    
    public void clearUnregisteredClassLoaders() {
        ArrayList toUnregister = null;
        synchronized (this.registeredCLs) {
            final Iterator it = this.registeredCLs.values().iterator();
            while (it.hasNext()) {
                final ScopedClassPool pool = it.next();
                if (pool.isUnloadedClassLoader()) {
                    it.remove();
                    final ClassLoader cl = pool.getClassLoader();
                    if (cl == null) {
                        continue;
                    }
                    if (toUnregister == null) {
                        toUnregister = new ArrayList();
                    }
                    toUnregister.add(cl);
                }
            }
            if (toUnregister != null) {
                for (int i = 0; i < toUnregister.size(); ++i) {
                    this.unregisterClassLoader(toUnregister.get(i));
                }
            }
        }
    }
    
    public void unregisterClassLoader(final ClassLoader cl) {
        synchronized (this.registeredCLs) {
            final ScopedClassPool pool = this.registeredCLs.remove(cl);
            if (pool != null) {
                pool.close();
            }
        }
    }
    
    public void insertDelegate(final ScopedClassPoolRepository delegate) {
    }
    
    public void setClassPoolFactory(final ScopedClassPoolFactory factory) {
        this.factory = factory;
    }
    
    public ScopedClassPoolFactory getClassPoolFactory() {
        return this.factory;
    }
    
    static {
        instance = new ScopedClassPoolRepositoryImpl();
    }
}
