// 
// Decompiled by Procyon v0.5.36
// 

package org.bukkit;

import org.apache.commons.lang.Validate;
import com.google.common.collect.Maps;
import java.util.Map;
import java.util.regex.Pattern;

public enum ChatColor
{
    BLACK(0, '0', 0) {
        @Override
        public net.md_5.bungee.api.ChatColor asBungee() {
            return net.md_5.bungee.api.ChatColor.BLACK;
        }
    }, 
    DARK_BLUE(1, '1', 1) {
        @Override
        public net.md_5.bungee.api.ChatColor asBungee() {
            return net.md_5.bungee.api.ChatColor.DARK_BLUE;
        }
    }, 
    DARK_GREEN(2, '2', 2) {
        @Override
        public net.md_5.bungee.api.ChatColor asBungee() {
            return net.md_5.bungee.api.ChatColor.DARK_GREEN;
        }
    }, 
    DARK_AQUA(3, '3', 3) {
        @Override
        public net.md_5.bungee.api.ChatColor asBungee() {
            return net.md_5.bungee.api.ChatColor.DARK_AQUA;
        }
    }, 
    DARK_RED(4, '4', 4) {
        @Override
        public net.md_5.bungee.api.ChatColor asBungee() {
            return net.md_5.bungee.api.ChatColor.DARK_RED;
        }
    }, 
    DARK_PURPLE(5, '5', 5) {
        @Override
        public net.md_5.bungee.api.ChatColor asBungee() {
            return net.md_5.bungee.api.ChatColor.DARK_PURPLE;
        }
    }, 
    GOLD(6, '6', 6) {
        @Override
        public net.md_5.bungee.api.ChatColor asBungee() {
            return net.md_5.bungee.api.ChatColor.GOLD;
        }
    }, 
    GRAY(7, '7', 7) {
        @Override
        public net.md_5.bungee.api.ChatColor asBungee() {
            return net.md_5.bungee.api.ChatColor.GRAY;
        }
    }, 
    DARK_GRAY(8, '8', 8) {
        @Override
        public net.md_5.bungee.api.ChatColor asBungee() {
            return net.md_5.bungee.api.ChatColor.DARK_GRAY;
        }
    }, 
    BLUE(9, '9', 9) {
        @Override
        public net.md_5.bungee.api.ChatColor asBungee() {
            return net.md_5.bungee.api.ChatColor.BLUE;
        }
    }, 
    GREEN(10, 'a', 10) {
        @Override
        public net.md_5.bungee.api.ChatColor asBungee() {
            return net.md_5.bungee.api.ChatColor.GREEN;
        }
    }, 
    AQUA(11, 'b', 11) {
        @Override
        public net.md_5.bungee.api.ChatColor asBungee() {
            return net.md_5.bungee.api.ChatColor.AQUA;
        }
    }, 
    RED(12, 'c', 12) {
        @Override
        public net.md_5.bungee.api.ChatColor asBungee() {
            return net.md_5.bungee.api.ChatColor.RED;
        }
    }, 
    LIGHT_PURPLE(13, 'd', 13) {
        @Override
        public net.md_5.bungee.api.ChatColor asBungee() {
            return net.md_5.bungee.api.ChatColor.LIGHT_PURPLE;
        }
    }, 
    YELLOW(14, 'e', 14) {
        @Override
        public net.md_5.bungee.api.ChatColor asBungee() {
            return net.md_5.bungee.api.ChatColor.YELLOW;
        }
    }, 
    WHITE(15, 'f', 15) {
        @Override
        public net.md_5.bungee.api.ChatColor asBungee() {
            return net.md_5.bungee.api.ChatColor.WHITE;
        }
    }, 
    MAGIC(16, 'k', 16, true) {
        @Override
        public net.md_5.bungee.api.ChatColor asBungee() {
            return net.md_5.bungee.api.ChatColor.MAGIC;
        }
    }, 
    BOLD(17, 'l', 17, true) {
        @Override
        public net.md_5.bungee.api.ChatColor asBungee() {
            return net.md_5.bungee.api.ChatColor.BOLD;
        }
    }, 
    STRIKETHROUGH(18, 'm', 18, true) {
        @Override
        public net.md_5.bungee.api.ChatColor asBungee() {
            return net.md_5.bungee.api.ChatColor.STRIKETHROUGH;
        }
    }, 
    UNDERLINE(19, 'n', 19, true) {
        @Override
        public net.md_5.bungee.api.ChatColor asBungee() {
            return net.md_5.bungee.api.ChatColor.UNDERLINE;
        }
    }, 
    ITALIC(20, 'o', 20, true) {
        @Override
        public net.md_5.bungee.api.ChatColor asBungee() {
            return net.md_5.bungee.api.ChatColor.ITALIC;
        }
    }, 
    RESET(21, 'r', 21) {
        @Override
        public net.md_5.bungee.api.ChatColor asBungee() {
            return net.md_5.bungee.api.ChatColor.RESET;
        }
    };
    
    public static final char COLOR_CHAR = '§';
    private static final Pattern STRIP_COLOR_PATTERN;
    private final int intCode;
    private final char code;
    private final boolean isFormat;
    private final String toString;
    private static final Map<Integer, ChatColor> BY_ID;
    private static final Map<Character, ChatColor> BY_CHAR;
    
    static {
        STRIP_COLOR_PATTERN = Pattern.compile("(?i)" + String.valueOf('§') + "[0-9A-FK-OR]");
        BY_ID = Maps.newHashMap();
        BY_CHAR = Maps.newHashMap();
        ChatColor[] values;
        for (int length = (values = values()).length, i = 0; i < length; ++i) {
            final ChatColor color = values[i];
            ChatColor.BY_ID.put(color.intCode, color);
            ChatColor.BY_CHAR.put(color.code, color);
        }
    }
    
    private ChatColor(final String s, final int n, final char code, final int intCode) {
        this(s, n, code, intCode, false);
    }
    
    private ChatColor(final String name, final int ordinal, final char code, final int intCode, final boolean isFormat) {
        this.code = code;
        this.intCode = intCode;
        this.isFormat = isFormat;
        this.toString = new String(new char[] { '§', code });
    }
    
    public net.md_5.bungee.api.ChatColor asBungee() {
        return net.md_5.bungee.api.ChatColor.RESET;
    }
    
    public char getChar() {
        return this.code;
    }
    
    @Override
    public String toString() {
        return this.toString;
    }
    
    public boolean isFormat() {
        return this.isFormat;
    }
    
    public boolean isColor() {
        return !this.isFormat && this != ChatColor.RESET;
    }
    
    public static ChatColor getByChar(final char code) {
        return ChatColor.BY_CHAR.get(code);
    }
    
    public static ChatColor getByChar(final String code) {
        Validate.notNull(code, "Code cannot be null");
        Validate.isTrue(code.length() > 0, "Code must have at least one char");
        return ChatColor.BY_CHAR.get(code.charAt(0));
    }
    
    public static String stripColor(final String input) {
        if (input == null) {
            return null;
        }
        return ChatColor.STRIP_COLOR_PATTERN.matcher(input).replaceAll("");
    }
    
    public static String translateAlternateColorCodes(final char altColorChar, final String textToTranslate) {
        final char[] b = textToTranslate.toCharArray();
        for (int i = 0; i < b.length - 1; ++i) {
            if (b[i] == altColorChar && "0123456789AaBbCcDdEeFfKkLlMmNnOoRr".indexOf(b[i + 1]) > -1) {
                b[i] = '§';
                b[i + 1] = Character.toLowerCase(b[i + 1]);
            }
        }
        return new String(b);
    }
    
    public static String getLastColors(final String input) {
        String result = "";
        final int length = input.length();
        for (int index = length - 1; index > -1; --index) {
            final char section = input.charAt(index);
            if (section == '§' && index < length - 1) {
                final char c = input.charAt(index + 1);
                final ChatColor color = getByChar(c);
                if (color != null) {
                    result = String.valueOf(color.toString()) + result;
                    if (color.isColor()) {
                        break;
                    }
                    if (color.equals(ChatColor.RESET)) {
                        break;
                    }
                }
            }
        }
        return result;
    }
}
