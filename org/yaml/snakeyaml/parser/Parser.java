//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\User\Desktop\1.12 stable mappings"!

//Decompiled by Procyon!

package org.yaml.snakeyaml.parser;

import org.yaml.snakeyaml.events.*;

public interface Parser
{
    boolean checkEvent(final Event.ID p0);
    
    Event peekEvent();
    
    Event getEvent();
}
