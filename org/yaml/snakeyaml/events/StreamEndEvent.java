//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\User\Desktop\1.12 stable mappings"!

//Decompiled by Procyon!

package org.yaml.snakeyaml.events;

import org.yaml.snakeyaml.error.*;

public final class StreamEndEvent extends Event
{
    public StreamEndEvent(final Mark startMark, final Mark endMark) {
        super(startMark, endMark);
    }
    
    public boolean is(final Event.ID id) {
        return Event.ID.StreamEnd == id;
    }
}
