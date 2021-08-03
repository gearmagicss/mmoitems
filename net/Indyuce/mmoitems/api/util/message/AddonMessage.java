// 
// Decompiled by Procyon v0.5.36
// 

package net.Indyuce.mmoitems.api.util.message;

import net.Indyuce.mmoitems.MMOItems;

public class AddonMessage extends PlayerMessage
{
    public AddonMessage(final String s) {
        super(MMOItems.plugin.getLanguage().getMessage(s));
    }
}
