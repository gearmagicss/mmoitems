// 
// Decompiled by Procyon v0.5.36
// 

package org.bukkit.conversations;

import org.apache.commons.lang.BooleanUtils;
import org.apache.commons.lang.ArrayUtils;

public abstract class BooleanPrompt extends ValidatingPrompt
{
    @Override
    protected boolean isInputValid(final ConversationContext context, final String input) {
        final String[] accepted = { "true", "false", "on", "off", "yes", "no", "y", "n", "1", "0", "right", "wrong", "correct", "incorrect", "valid", "invalid" };
        return ArrayUtils.contains(accepted, input.toLowerCase());
    }
    
    @Override
    protected Prompt acceptValidatedInput(final ConversationContext context, String input) {
        if (input.equalsIgnoreCase("y") || input.equals("1") || input.equalsIgnoreCase("right") || input.equalsIgnoreCase("correct") || input.equalsIgnoreCase("valid")) {
            input = "true";
        }
        return this.acceptValidatedInput(context, BooleanUtils.toBoolean(input));
    }
    
    protected abstract Prompt acceptValidatedInput(final ConversationContext p0, final boolean p1);
}
