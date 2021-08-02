// 
// Decompiled by Procyon v0.5.36
// 

package org.bukkit.conversations;

public abstract class MessagePrompt implements Prompt
{
    @Override
    public boolean blocksForInput(final ConversationContext context) {
        return false;
    }
    
    @Override
    public Prompt acceptInput(final ConversationContext context, final String input) {
        return this.getNextPrompt(context);
    }
    
    protected abstract Prompt getNextPrompt(final ConversationContext p0);
}
