// 
// Decompiled by Procyon v0.5.36
// 

package org.bukkit.conversations;

public abstract class StringPrompt implements Prompt
{
    @Override
    public boolean blocksForInput(final ConversationContext context) {
        return true;
    }
}
