// 
// Decompiled by Procyon v0.5.36
// 

package org.bukkit.map;

public class MinecraftFont extends MapFont
{
    private static final int spaceSize = 2;
    private static final String fontChars = " !\"#$%&'()*+,-./0123456789:;<=>?@ABCDEFGHIJKLMNOPQRSTUVWXYZ[\\]^_'abcdefghijklmnopqrstuvwxyz{|}~\u007f\u00c7\u00fc\u00e9\u00e2\u00e4\u00e0\u00e5\u00e7\u00ea\u00eb\u00e8\u00ef\u00ee\u00ec\u00c4\u00c5\u00c9\u00e6\u00c6\u00f4\u00f6\u00f2\u00fb\u00f9\u00ff\u00d6\u00dc\u00f8£\u00d8\u00d7\u0191\u00e1\u00ed\u00f3\u00fa\u00f1\u00d1ªº¿®¬½¼¡«»";
    private static final int[][] fontData;
    public static final MinecraftFont Font;
    
    static {
        final int[][] fontData2 = new int[256][];
        fontData2[0] = new int[8];
        fontData2[1] = new int[] { 126, 129, 165, 129, 189, 153, 129, 126 };
        fontData2[2] = new int[] { 126, 255, 219, 255, 195, 231, 255, 126 };
        fontData2[3] = new int[] { 54, 127, 127, 127, 62, 28, 8, 0 };
        fontData2[4] = new int[] { 8, 28, 62, 127, 62, 28, 8, 0 };
        fontData2[5] = new int[] { 28, 62, 28, 127, 127, 62, 28, 62 };
        fontData2[6] = new int[] { 8, 8, 28, 62, 127, 62, 28, 62 };
        fontData2[7] = new int[] { 0, 0, 24, 60, 60, 24, 0, 0 };
        fontData2[8] = new int[] { 255, 255, 231, 195, 195, 231, 255, 255 };
        fontData2[9] = new int[] { 0, 60, 102, 66, 66, 102, 60, 0 };
        fontData2[10] = new int[] { 255, 195, 153, 189, 189, 153, 195, 255 };
        fontData2[11] = new int[] { 240, 224, 240, 190, 51, 51, 51, 30 };
        fontData2[12] = new int[] { 60, 102, 102, 102, 60, 24, 126, 24 };
        fontData2[13] = new int[] { 252, 204, 252, 12, 12, 14, 15, 7 };
        fontData2[14] = new int[] { 254, 198, 254, 198, 198, 230, 103, 3 };
        fontData2[15] = new int[] { 153, 90, 60, 231, 231, 60, 90, 153 };
        fontData2[16] = new int[] { 1, 7, 31, 127, 31, 7, 1, 0 };
        fontData2[17] = new int[] { 64, 112, 124, 127, 124, 112, 64, 0 };
        fontData2[18] = new int[] { 24, 60, 126, 24, 24, 126, 60, 24 };
        fontData2[19] = new int[] { 102, 102, 102, 102, 102, 0, 102, 0 };
        fontData2[20] = new int[] { 254, 219, 219, 222, 216, 216, 216, 0 };
        fontData2[21] = new int[] { 124, 198, 28, 54, 54, 28, 51, 30 };
        fontData2[22] = new int[] { 0, 0, 0, 0, 126, 126, 126, 0 };
        fontData2[23] = new int[] { 24, 60, 126, 24, 126, 60, 24, 255 };
        fontData2[24] = new int[] { 24, 60, 126, 24, 24, 24, 24, 0 };
        fontData2[25] = new int[] { 24, 24, 24, 24, 126, 60, 24, 0 };
        fontData2[26] = new int[] { 0, 24, 48, 127, 48, 24, 0, 0 };
        fontData2[27] = new int[] { 0, 12, 6, 127, 6, 12, 0, 0 };
        fontData2[28] = new int[] { 0, 0, 3, 3, 3, 127, 0, 0 };
        fontData2[29] = new int[] { 0, 36, 102, 255, 102, 36, 0, 0 };
        fontData2[30] = new int[] { 0, 24, 60, 126, 255, 255, 0, 0 };
        fontData2[31] = new int[] { 0, 255, 255, 126, 60, 24, 0, 0 };
        fontData2[32] = new int[8];
        fontData2[33] = new int[] { 1, 1, 1, 1, 1, 0, 1, 0 };
        final int n = 34;
        final int[] array = new int[8];
        array[1] = (array[0] = 10);
        array[2] = 5;
        fontData2[n] = array;
        fontData2[35] = new int[] { 10, 10, 31, 10, 31, 10, 10, 0 };
        fontData2[36] = new int[] { 4, 30, 1, 14, 16, 15, 4, 0 };
        fontData2[37] = new int[] { 17, 9, 8, 4, 2, 18, 17, 0 };
        fontData2[38] = new int[] { 4, 10, 4, 22, 13, 9, 22, 0 };
        final int n2 = 39;
        final int[] array2 = new int[8];
        array2[0] = 2;
        array2[array2[1] = 2] = 1;
        fontData2[n2] = array2;
        fontData2[40] = new int[] { 12, 2, 1, 1, 1, 2, 12, 0 };
        fontData2[41] = new int[] { 3, 4, 8, 8, 8, 4, 3, 0 };
        fontData2[42] = new int[] { 0, 0, 9, 6, 9, 0, 0, 0 };
        fontData2[43] = new int[] { 0, 4, 4, 31, 4, 4, 0, 0 };
        fontData2[44] = new int[] { 0, 0, 0, 0, 0, 1, 1, 1 };
        fontData2[45] = new int[] { 0, 0, 0, 31, 0, 0, 0, 0 };
        fontData2[46] = new int[] { 0, 0, 0, 0, 0, 1, 1, 0 };
        fontData2[47] = new int[] { 16, 8, 8, 4, 2, 2, 1, 0 };
        fontData2[48] = new int[] { 14, 17, 25, 21, 19, 17, 14, 0 };
        fontData2[49] = new int[] { 4, 6, 4, 4, 4, 4, 31, 0 };
        fontData2[50] = new int[] { 14, 17, 16, 12, 2, 17, 31, 0 };
        fontData2[51] = new int[] { 14, 17, 16, 12, 16, 17, 14, 0 };
        fontData2[52] = new int[] { 24, 20, 18, 17, 31, 16, 16, 0 };
        fontData2[53] = new int[] { 31, 1, 15, 16, 16, 17, 14, 0 };
        fontData2[54] = new int[] { 12, 2, 1, 15, 17, 17, 14, 0 };
        fontData2[55] = new int[] { 31, 17, 16, 8, 4, 4, 4, 0 };
        fontData2[56] = new int[] { 14, 17, 17, 14, 17, 17, 14, 0 };
        fontData2[57] = new int[] { 14, 17, 17, 30, 16, 8, 6, 0 };
        fontData2[58] = new int[] { 0, 1, 1, 0, 0, 1, 1, 0 };
        fontData2[59] = new int[] { 0, 1, 1, 0, 0, 1, 1, 1 };
        fontData2[60] = new int[] { 8, 4, 2, 1, 2, 4, 8, 0 };
        fontData2[61] = new int[] { 0, 0, 31, 0, 0, 31, 0, 0 };
        fontData2[62] = new int[] { 1, 2, 4, 8, 4, 2, 1, 0 };
        fontData2[63] = new int[] { 14, 17, 16, 8, 4, 0, 4, 0 };
        fontData2[64] = new int[] { 30, 33, 45, 45, 61, 1, 30, 0 };
        fontData2[65] = new int[] { 14, 17, 31, 17, 17, 17, 17, 0 };
        fontData2[66] = new int[] { 15, 17, 15, 17, 17, 17, 15, 0 };
        fontData2[67] = new int[] { 14, 17, 1, 1, 1, 17, 14, 0 };
        fontData2[68] = new int[] { 15, 17, 17, 17, 17, 17, 15, 0 };
        fontData2[69] = new int[] { 31, 1, 7, 1, 1, 1, 31, 0 };
        fontData2[70] = new int[] { 31, 1, 7, 1, 1, 1, 1, 0 };
        fontData2[71] = new int[] { 30, 1, 25, 17, 17, 17, 14, 0 };
        fontData2[72] = new int[] { 17, 17, 31, 17, 17, 17, 17, 0 };
        fontData2[73] = new int[] { 7, 2, 2, 2, 2, 2, 7, 0 };
        fontData2[74] = new int[] { 16, 16, 16, 16, 16, 17, 14, 0 };
        fontData2[75] = new int[] { 17, 9, 7, 9, 17, 17, 17, 0 };
        fontData2[76] = new int[] { 1, 1, 1, 1, 1, 1, 31, 0 };
        fontData2[77] = new int[] { 17, 27, 21, 17, 17, 17, 17, 0 };
        fontData2[78] = new int[] { 17, 19, 21, 25, 17, 17, 17, 0 };
        fontData2[79] = new int[] { 14, 17, 17, 17, 17, 17, 14, 0 };
        fontData2[80] = new int[] { 15, 17, 15, 1, 1, 1, 1, 0 };
        fontData2[81] = new int[] { 14, 17, 17, 17, 17, 9, 22, 0 };
        fontData2[82] = new int[] { 15, 17, 15, 17, 17, 17, 17, 0 };
        fontData2[83] = new int[] { 30, 1, 14, 16, 16, 17, 14, 0 };
        fontData2[84] = new int[] { 31, 4, 4, 4, 4, 4, 4, 0 };
        fontData2[85] = new int[] { 17, 17, 17, 17, 17, 17, 14, 0 };
        fontData2[86] = new int[] { 17, 17, 17, 17, 10, 10, 4, 0 };
        fontData2[87] = new int[] { 17, 17, 17, 17, 21, 27, 17, 0 };
        fontData2[88] = new int[] { 17, 10, 4, 10, 17, 17, 17, 0 };
        fontData2[89] = new int[] { 17, 10, 4, 4, 4, 4, 4, 0 };
        fontData2[90] = new int[] { 31, 16, 8, 4, 2, 1, 31, 0 };
        fontData2[91] = new int[] { 7, 1, 1, 1, 1, 1, 7, 0 };
        fontData2[92] = new int[] { 1, 2, 2, 4, 8, 8, 16, 0 };
        fontData2[93] = new int[] { 7, 4, 4, 4, 4, 4, 7, 0 };
        final int n3 = 94;
        final int[] array3 = new int[8];
        array3[0] = 4;
        array3[1] = 10;
        array3[2] = 17;
        fontData2[n3] = array3;
        fontData2[95] = new int[] { 0, 0, 0, 0, 0, 0, 0, 31 };
        final int n4 = 96;
        final int[] array4 = new int[8];
        array4[array4[0] = 1] = 1;
        array4[2] = 2;
        fontData2[n4] = array4;
        fontData2[97] = new int[] { 0, 0, 14, 16, 30, 17, 30, 0 };
        fontData2[98] = new int[] { 1, 1, 13, 19, 17, 17, 15, 0 };
        fontData2[99] = new int[] { 0, 0, 14, 17, 1, 17, 14, 0 };
        fontData2[100] = new int[] { 16, 16, 22, 25, 17, 17, 30, 0 };
        fontData2[101] = new int[] { 0, 0, 14, 17, 31, 1, 30, 0 };
        fontData2[102] = new int[] { 12, 2, 15, 2, 2, 2, 2, 0 };
        fontData2[103] = new int[] { 0, 0, 30, 17, 17, 30, 16, 15 };
        fontData2[104] = new int[] { 1, 1, 13, 19, 17, 17, 17, 0 };
        fontData2[105] = new int[] { 1, 0, 1, 1, 1, 1, 1, 0 };
        fontData2[106] = new int[] { 16, 0, 16, 16, 16, 17, 17, 14 };
        fontData2[107] = new int[] { 1, 1, 9, 5, 3, 5, 9, 0 };
        fontData2[108] = new int[] { 1, 1, 1, 1, 1, 1, 2, 0 };
        fontData2[109] = new int[] { 0, 0, 11, 21, 21, 17, 17, 0 };
        fontData2[110] = new int[] { 0, 0, 15, 17, 17, 17, 17, 0 };
        fontData2[111] = new int[] { 0, 0, 14, 17, 17, 17, 14, 0 };
        fontData2[112] = new int[] { 0, 0, 13, 19, 17, 15, 1, 1 };
        fontData2[113] = new int[] { 0, 0, 22, 25, 17, 30, 16, 16 };
        fontData2[114] = new int[] { 0, 0, 13, 19, 1, 1, 1, 0 };
        fontData2[115] = new int[] { 0, 0, 30, 1, 14, 16, 15, 0 };
        fontData2[116] = new int[] { 2, 2, 7, 2, 2, 2, 4, 0 };
        fontData2[117] = new int[] { 0, 0, 17, 17, 17, 17, 30, 0 };
        fontData2[118] = new int[] { 0, 0, 17, 17, 17, 10, 4, 0 };
        fontData2[119] = new int[] { 0, 0, 17, 17, 21, 21, 30, 0 };
        fontData2[120] = new int[] { 0, 0, 17, 10, 4, 10, 17, 0 };
        fontData2[121] = new int[] { 0, 0, 17, 17, 17, 30, 16, 15 };
        fontData2[122] = new int[] { 0, 0, 31, 8, 4, 2, 31, 0 };
        fontData2[123] = new int[] { 12, 2, 2, 1, 2, 2, 12, 0 };
        fontData2[124] = new int[] { 1, 1, 1, 0, 1, 1, 1, 0 };
        fontData2[125] = new int[] { 3, 4, 4, 8, 4, 4, 3, 0 };
        final int n5 = 126;
        final int[] array5 = new int[8];
        array5[0] = 38;
        array5[1] = 25;
        fontData2[n5] = array5;
        fontData2[127] = new int[] { 0, 0, 4, 10, 17, 17, 31, 0 };
        fontData2[128] = new int[] { 14, 17, 1, 1, 17, 14, 16, 12 };
        fontData2[129] = new int[] { 10, 0, 17, 17, 17, 17, 30, 0 };
        fontData2[130] = new int[] { 24, 0, 14, 17, 31, 1, 30, 0 };
        fontData2[131] = new int[] { 14, 17, 14, 16, 30, 17, 30, 0 };
        fontData2[132] = new int[] { 10, 0, 14, 16, 30, 17, 30, 0 };
        fontData2[133] = new int[] { 3, 0, 14, 16, 30, 17, 30, 0 };
        fontData2[134] = new int[] { 4, 0, 14, 16, 30, 17, 30, 0 };
        fontData2[135] = new int[] { 0, 14, 17, 1, 17, 14, 16, 12 };
        fontData2[136] = new int[] { 14, 17, 14, 17, 31, 1, 30, 0 };
        fontData2[137] = new int[] { 10, 0, 14, 17, 31, 1, 30, 0 };
        fontData2[138] = new int[] { 3, 0, 14, 17, 31, 1, 30, 0 };
        fontData2[139] = new int[] { 5, 0, 2, 2, 2, 2, 2, 0 };
        fontData2[140] = new int[] { 14, 17, 4, 4, 4, 4, 4, 0 };
        fontData2[141] = new int[] { 3, 0, 2, 2, 2, 2, 2, 0 };
        fontData2[142] = new int[] { 17, 14, 17, 31, 17, 17, 17, 0 };
        fontData2[143] = new int[] { 4, 0, 14, 17, 31, 17, 17, 0 };
        fontData2[144] = new int[] { 24, 0, 31, 1, 7, 1, 31, 0 };
        fontData2[145] = new int[] { 0, 0, 10, 20, 30, 5, 30, 0 };
        fontData2[146] = new int[] { 30, 5, 15, 5, 5, 5, 29, 0 };
        fontData2[147] = new int[] { 14, 17, 14, 17, 17, 17, 14, 0 };
        fontData2[148] = new int[] { 10, 0, 14, 17, 17, 17, 14, 0 };
        fontData2[149] = new int[] { 3, 0, 14, 17, 17, 17, 14, 0 };
        fontData2[150] = new int[] { 14, 17, 0, 17, 17, 17, 30, 0 };
        fontData2[151] = new int[] { 3, 0, 17, 17, 17, 17, 30, 0 };
        fontData2[152] = new int[] { 10, 0, 17, 17, 17, 30, 16, 15 };
        fontData2[153] = new int[] { 17, 14, 17, 17, 17, 17, 14, 0 };
        fontData2[154] = new int[] { 17, 0, 17, 17, 17, 17, 14, 0 };
        fontData2[155] = new int[] { 0, 0, 14, 25, 21, 19, 14, 4 };
        fontData2[156] = new int[] { 12, 18, 2, 15, 2, 2, 31, 0 };
        fontData2[157] = new int[] { 14, 17, 25, 21, 19, 17, 14, 0 };
        fontData2[158] = new int[] { 0, 0, 5, 2, 5, 0, 0, 0 };
        fontData2[159] = new int[] { 8, 20, 4, 14, 4, 4, 5, 2 };
        fontData2[160] = new int[] { 24, 0, 14, 16, 30, 17, 30, 0 };
        fontData2[161] = new int[] { 3, 0, 1, 1, 1, 1, 1, 0 };
        fontData2[162] = new int[] { 24, 0, 14, 17, 17, 17, 14, 0 };
        fontData2[163] = new int[] { 24, 0, 17, 17, 17, 17, 30, 0 };
        fontData2[164] = new int[] { 31, 0, 15, 17, 17, 17, 17, 0 };
        fontData2[165] = new int[] { 31, 0, 17, 19, 21, 25, 17, 0 };
        fontData2[166] = new int[] { 14, 16, 31, 30, 0, 31, 0, 0 };
        fontData2[167] = new int[] { 14, 17, 17, 14, 0, 31, 0, 0 };
        fontData2[168] = new int[] { 4, 0, 4, 2, 1, 17, 14, 0 };
        fontData2[169] = new int[] { 0, 30, 45, 37, 43, 30, 0, 0 };
        fontData2[170] = new int[] { 0, 0, 0, 31, 16, 16, 0, 0 };
        fontData2[171] = new int[] { 17, 9, 8, 4, 18, 10, 25, 0 };
        fontData2[172] = new int[] { 17, 9, 8, 4, 26, 26, 17, 0 };
        fontData2[173] = new int[] { 0, 1, 0, 1, 1, 1, 1, 0 };
        fontData2[174] = new int[] { 0, 20, 10, 5, 10, 20, 0, 0 };
        fontData2[175] = new int[] { 0, 5, 10, 20, 10, 5, 0, 0 };
        fontData2[176] = new int[] { 68, 17, 68, 17, 68, 17, 68, 17 };
        fontData2[177] = new int[] { 170, 85, 170, 85, 170, 85, 170, 85 };
        fontData2[178] = new int[] { 219, 238, 219, 119, 219, 238, 219, 119 };
        fontData2[179] = new int[] { 24, 24, 24, 24, 24, 24, 24, 24 };
        fontData2[180] = new int[] { 24, 24, 24, 24, 31, 24, 24, 24 };
        fontData2[181] = new int[] { 24, 24, 31, 24, 31, 24, 24, 24 };
        fontData2[182] = new int[] { 108, 108, 108, 108, 111, 108, 108, 108 };
        fontData2[183] = new int[] { 0, 0, 0, 0, 127, 108, 108, 108 };
        fontData2[184] = new int[] { 0, 0, 31, 24, 31, 24, 24, 24 };
        fontData2[185] = new int[] { 108, 108, 111, 96, 111, 108, 108, 108 };
        fontData2[186] = new int[] { 108, 108, 108, 108, 108, 108, 108, 108 };
        fontData2[187] = new int[] { 0, 0, 127, 96, 111, 108, 108, 108 };
        fontData2[188] = new int[] { 108, 108, 111, 96, 127, 0, 0, 0 };
        fontData2[189] = new int[] { 108, 108, 108, 108, 127, 0, 0, 0 };
        fontData2[190] = new int[] { 24, 24, 31, 24, 31, 0, 0, 0 };
        fontData2[191] = new int[] { 0, 0, 0, 0, 31, 24, 24, 24 };
        fontData2[192] = new int[] { 24, 24, 24, 24, 248, 0, 0, 0 };
        fontData2[193] = new int[] { 24, 24, 24, 24, 255, 0, 0, 0 };
        fontData2[194] = new int[] { 0, 0, 0, 0, 255, 24, 24, 24 };
        fontData2[195] = new int[] { 24, 24, 24, 24, 248, 24, 24, 24 };
        fontData2[196] = new int[] { 0, 0, 0, 0, 255, 0, 0, 0 };
        fontData2[197] = new int[] { 24, 24, 24, 24, 255, 24, 24, 24 };
        fontData2[198] = new int[] { 24, 24, 248, 24, 248, 24, 24, 24 };
        fontData2[199] = new int[] { 108, 108, 108, 108, 236, 108, 108, 108 };
        fontData2[200] = new int[] { 108, 108, 236, 12, 252, 0, 0, 0 };
        fontData2[201] = new int[] { 0, 0, 252, 12, 236, 108, 108, 108 };
        fontData2[202] = new int[] { 108, 108, 239, 0, 255, 0, 0, 0 };
        fontData2[203] = new int[] { 0, 0, 255, 0, 239, 108, 108, 108 };
        fontData2[204] = new int[] { 108, 108, 236, 12, 236, 108, 108, 108 };
        fontData2[205] = new int[] { 0, 0, 255, 0, 255, 0, 0, 0 };
        fontData2[206] = new int[] { 108, 108, 239, 0, 239, 108, 108, 108 };
        fontData2[207] = new int[] { 24, 24, 255, 0, 255, 0, 0, 0 };
        fontData2[208] = new int[] { 108, 108, 108, 108, 255, 0, 0, 0 };
        fontData2[209] = new int[] { 0, 0, 255, 0, 255, 24, 24, 24 };
        fontData2[210] = new int[] { 0, 0, 0, 0, 255, 108, 108, 108 };
        fontData2[211] = new int[] { 108, 108, 108, 108, 252, 0, 0, 0 };
        fontData2[212] = new int[] { 24, 24, 248, 24, 248, 0, 0, 0 };
        fontData2[213] = new int[] { 0, 0, 248, 24, 248, 24, 24, 24 };
        fontData2[214] = new int[] { 0, 0, 0, 0, 252, 108, 108, 108 };
        fontData2[215] = new int[] { 108, 108, 108, 108, 255, 108, 108, 108 };
        fontData2[216] = new int[] { 24, 24, 255, 24, 255, 24, 24, 24 };
        fontData2[217] = new int[] { 24, 24, 24, 24, 31, 0, 0, 0 };
        fontData2[218] = new int[] { 0, 0, 0, 0, 248, 24, 24, 24 };
        fontData2[219] = new int[] { 255, 255, 255, 255, 255, 255, 255, 255 };
        fontData2[220] = new int[] { 0, 0, 0, 0, 255, 255, 255, 255 };
        fontData2[221] = new int[] { 15, 15, 15, 15, 15, 15, 15, 15 };
        fontData2[222] = new int[] { 240, 240, 240, 240, 240, 240, 240, 240 };
        fontData2[223] = new int[] { 255, 255, 255, 255, 0, 0, 0, 0 };
        fontData2[224] = new int[] { 0, 0, 110, 59, 19, 59, 110, 0 };
        fontData2[225] = new int[] { 0, 30, 51, 31, 51, 31, 3, 3 };
        fontData2[226] = new int[] { 0, 63, 51, 3, 3, 3, 3, 0 };
        fontData2[227] = new int[] { 0, 127, 54, 54, 54, 54, 54, 0 };
        fontData2[228] = new int[] { 63, 51, 6, 12, 6, 51, 63, 0 };
        fontData2[229] = new int[] { 0, 0, 126, 27, 27, 27, 14, 0 };
        fontData2[230] = new int[] { 0, 102, 102, 102, 102, 62, 6, 3 };
        fontData2[231] = new int[] { 0, 110, 59, 24, 24, 24, 24, 0 };
        fontData2[232] = new int[] { 63, 12, 30, 51, 51, 30, 12, 63 };
        fontData2[233] = new int[] { 28, 54, 99, 127, 99, 54, 28, 0 };
        fontData2[234] = new int[] { 28, 54, 99, 99, 54, 54, 119, 0 };
        fontData2[235] = new int[] { 56, 12, 24, 62, 51, 51, 30, 0 };
        fontData2[236] = new int[] { 0, 0, 126, 219, 219, 126, 0, 0 };
        fontData2[237] = new int[] { 96, 48, 126, 219, 219, 126, 6, 3 };
        fontData2[238] = new int[] { 28, 6, 3, 31, 3, 6, 28, 0 };
        fontData2[239] = new int[] { 30, 51, 51, 51, 51, 51, 51, 0 };
        fontData2[240] = new int[] { 0, 63, 0, 63, 0, 63, 0, 0 };
        fontData2[241] = new int[] { 12, 12, 63, 12, 12, 0, 63, 0 };
        fontData2[242] = new int[] { 6, 12, 24, 12, 6, 0, 63, 0 };
        fontData2[243] = new int[] { 24, 12, 6, 12, 24, 0, 63, 0 };
        fontData2[244] = new int[] { 112, 216, 216, 24, 24, 24, 24, 24 };
        fontData2[245] = new int[] { 24, 24, 24, 24, 24, 27, 27, 14 };
        fontData2[246] = new int[] { 12, 12, 0, 63, 0, 12, 12, 0 };
        fontData2[247] = new int[] { 0, 110, 59, 0, 110, 59, 0, 0 };
        fontData2[248] = new int[] { 28, 54, 54, 28, 0, 0, 0, 0 };
        fontData2[249] = new int[] { 0, 0, 0, 24, 24, 0, 0, 0 };
        fontData2[250] = new int[] { 0, 0, 0, 0, 24, 0, 0, 0 };
        fontData2[251] = new int[] { 240, 48, 48, 48, 55, 54, 60, 56 };
        fontData2[252] = new int[] { 30, 54, 54, 54, 54, 0, 0, 0 };
        fontData2[253] = new int[] { 14, 24, 12, 6, 30, 0, 0, 0 };
        fontData2[254] = new int[] { 0, 0, 60, 60, 60, 60, 0, 0 };
        fontData2[255] = new int[8];
        fontData = fontData2;
        Font = new MinecraftFont(false);
    }
    
    public MinecraftFont() {
        this(true);
    }
    
    private MinecraftFont(final boolean malleable) {
        for (int i = 1; i < MinecraftFont.fontData.length; ++i) {
            char ch = (char)i;
            if (i >= 32 && i < 32 + " !\"#$%&'()*+,-./0123456789:;<=>?@ABCDEFGHIJKLMNOPQRSTUVWXYZ[\\]^_'abcdefghijklmnopqrstuvwxyz{|}~\u007f\u00c7\u00fc\u00e9\u00e2\u00e4\u00e0\u00e5\u00e7\u00ea\u00eb\u00e8\u00ef\u00ee\u00ec\u00c4\u00c5\u00c9\u00e6\u00c6\u00f4\u00f6\u00f2\u00fb\u00f9\u00ff\u00d6\u00dc\u00f8£\u00d8\u00d7\u0191\u00e1\u00ed\u00f3\u00fa\u00f1\u00d1ªº¿®¬½¼¡«»".length()) {
                ch = " !\"#$%&'()*+,-./0123456789:;<=>?@ABCDEFGHIJKLMNOPQRSTUVWXYZ[\\]^_'abcdefghijklmnopqrstuvwxyz{|}~\u007f\u00c7\u00fc\u00e9\u00e2\u00e4\u00e0\u00e5\u00e7\u00ea\u00eb\u00e8\u00ef\u00ee\u00ec\u00c4\u00c5\u00c9\u00e6\u00c6\u00f4\u00f6\u00f2\u00fb\u00f9\u00ff\u00d6\u00dc\u00f8£\u00d8\u00d7\u0191\u00e1\u00ed\u00f3\u00fa\u00f1\u00d1ªº¿®¬½¼¡«»".charAt(i - 32);
            }
            if (ch == ' ') {
                this.setChar(ch, new CharacterSprite(2, 8, new boolean[16]));
            }
            else {
                final int[] rows = MinecraftFont.fontData[i];
                int width = 0;
                for (int r = 0; r < 8; ++r) {
                    for (int c = 0; c < 8; ++c) {
                        if ((rows[r] & 1 << c) != 0x0 && c > width) {
                            width = c;
                        }
                    }
                }
                final boolean[] data = new boolean[++width * 8];
                for (int r2 = 0; r2 < 8; ++r2) {
                    for (int c2 = 0; c2 < width; ++c2) {
                        data[r2 * width + c2] = ((rows[r2] & 1 << c2) != 0x0);
                    }
                }
                this.setChar(ch, new CharacterSprite(width, 8, data));
            }
        }
        this.malleable = malleable;
    }
}
