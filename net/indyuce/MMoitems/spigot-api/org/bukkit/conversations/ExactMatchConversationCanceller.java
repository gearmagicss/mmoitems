// 
// Decompiled by Procyon v0.5.36
// 

package org.bukkit.conversations;

public class ExactMatchConversationCanceller implements ConversationCanceller
{
    private String escapeSequence;
    
    public ExactMatchConversationCanceller(final String escapeSequence) {
        this.escapeSequence = escapeSequence;
    }
    
    @Override
    public void setConversation(final Conversation conversation) {
    }
    
    @Override
    public boolean cancelBasedOnInput(final ConversationContext context, final String input) {
        return input.equals(this.escapeSequence);
    }
    
    @Override
    public ConversationCanceller clone() {
        return new ExactMatchConversationCanceller(this.escapeSequence);
    }
}
