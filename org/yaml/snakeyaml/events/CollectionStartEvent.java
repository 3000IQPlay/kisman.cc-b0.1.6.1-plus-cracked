//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\User\Desktop\1.12 stable mappings"!

//Decompiled by Procyon!

package org.yaml.snakeyaml.events;

import org.yaml.snakeyaml.error.*;

public abstract class CollectionStartEvent extends NodeEvent
{
    private final String tag;
    private final boolean implicit;
    private final Boolean flowStyle;
    
    public CollectionStartEvent(final String anchor, final String tag, final boolean implicit, final Mark startMark, final Mark endMark, final Boolean flowStyle) {
        super(anchor, startMark, endMark);
        this.tag = tag;
        this.implicit = implicit;
        this.flowStyle = flowStyle;
    }
    
    public String getTag() {
        return this.tag;
    }
    
    public boolean getImplicit() {
        return this.implicit;
    }
    
    public Boolean getFlowStyle() {
        return this.flowStyle;
    }
    
    @Override
    protected String getArguments() {
        return super.getArguments() + ", tag=" + this.tag + ", implicit=" + this.implicit;
    }
}
