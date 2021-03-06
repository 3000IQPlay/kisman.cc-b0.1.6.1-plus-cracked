//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\User\Desktop\1.12 stable mappings"!

//Decompiled by Procyon!

package org.yaml.snakeyaml.serializer;

import org.yaml.snakeyaml.nodes.*;
import java.text.*;

public class NumberAnchorGenerator implements AnchorGenerator
{
    private int lastAnchorId;
    
    public NumberAnchorGenerator(final int lastAnchorId) {
        this.lastAnchorId = 0;
        this.lastAnchorId = lastAnchorId;
    }
    
    public String nextAnchor(final Node node) {
        ++this.lastAnchorId;
        final NumberFormat format = NumberFormat.getNumberInstance();
        format.setMinimumIntegerDigits(3);
        format.setMaximumFractionDigits(0);
        format.setGroupingUsed(false);
        final String anchorId = format.format(this.lastAnchorId);
        return "id" + anchorId;
    }
}
