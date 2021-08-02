// 
// Decompiled by Procyon v0.5.36
// 

package org.bukkit.conversations;

public class ManuallyAbandonedConversationCanceller implements ConversationCanceller
{
    @Override
    public void setConversation(final Conversation conversation) {
        throw new UnsupportedOperationException();
    }
    
    @Override
    public boolean cancelBasedOnInput(final ConversationContext context, final String input) {
        throw new UnsupportedOperationException();
    }
    
    @Override
    public ConversationCanceller clone() {
        throw new UnsupportedOperationException();
    }
}
