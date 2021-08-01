// 
// Decompiled by Procyon v0.5.36
// 

package net.Indyuce.mmoitems.api.util;

import java.text.DecimalFormat;

public class StatFormat extends DecimalFormat
{
    private static final long serialVersionUID = -2880611307522719877L;
    
    public StatFormat(final String str) {
        super("0." + str);
    }
}
