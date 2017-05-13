/*      */ package java.awt.font;
/*      */ 
/*      */ import java.io.IOException;
/*      */ import java.io.ObjectOutputStream;
/*      */ import java.io.Serializable;
/*      */ import java.util.Arrays;
/*      */ import java.util.Comparator;
/*      */ import java.util.EnumSet;
/*      */ import java.util.Set;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public final class NumericShaper
/*      */   implements Serializable
/*      */ {
/*      */   private int key;
/*      */   private int mask;
/*      */   private Range shapingRange;
/*      */   private transient Set<Range> rangeSet;
/*      */   private transient Range[] rangeArray;
/*      */   private static final int BSEARCH_THRESHOLD = 3;
/*      */   private static final long serialVersionUID = -8022764705923730308L;
/*      */   public static final int EUROPEAN = 1;
/*      */   public static final int ARABIC = 2;
/*      */   public static final int EASTERN_ARABIC = 4;
/*      */   public static final int DEVANAGARI = 8;
/*      */   public static final int BENGALI = 16;
/*      */   public static final int GURMUKHI = 32;
/*      */   public static final int GUJARATI = 64;
/*      */   public static final int ORIYA = 128;
/*      */   public static final int TAMIL = 256;
/*      */   public static final int TELUGU = 512;
/*      */   public static final int KANNADA = 1024;
/*      */   public static final int MALAYALAM = 2048;
/*      */   public static final int THAI = 4096;
/*      */   public static final int LAO = 8192;
/*      */   public static final int TIBETAN = 16384;
/*      */   public static final int MYANMAR = 32768;
/*      */   public static final int ETHIOPIC = 65536;
/*      */   public static final int KHMER = 131072;
/*      */   public static final int MONGOLIAN = 262144;
/*      */   public static final int ALL_RANGES = 524287;
/*      */   private static final int EUROPEAN_KEY = 0;
/*      */   private static final int ARABIC_KEY = 1;
/*      */   private static final int EASTERN_ARABIC_KEY = 2;
/*      */   private static final int DEVANAGARI_KEY = 3;
/*      */   private static final int BENGALI_KEY = 4;
/*      */   private static final int GURMUKHI_KEY = 5;
/*      */   private static final int GUJARATI_KEY = 6;
/*      */   private static final int ORIYA_KEY = 7;
/*      */   private static final int TAMIL_KEY = 8;
/*      */   private static final int TELUGU_KEY = 9;
/*      */   private static final int KANNADA_KEY = 10;
/*      */   private static final int MALAYALAM_KEY = 11;
/*      */   private static final int THAI_KEY = 12;
/*      */   private static final int LAO_KEY = 13;
/*      */   private static final int TIBETAN_KEY = 14;
/*      */   private static final int MYANMAR_KEY = 15;
/*      */   private static final int ETHIOPIC_KEY = 16;
/*      */   private static final int KHMER_KEY = 17;
/*      */   private static final int MONGOLIAN_KEY = 18;
/*      */   private static final int NUM_KEYS = 19;
/*      */   private static final int CONTEXTUAL_MASK = Integer.MIN_VALUE;
/*      */   
/*      */   public static enum Range
/*      */   {
/*  168 */     EUROPEAN(48, 0, 768), 
/*      */     
/*      */ 
/*      */ 
/*  172 */     ARABIC(1632, 1536, 1920), 
/*      */     
/*      */ 
/*      */ 
/*  176 */     EASTERN_ARABIC(1776, 1536, 1920), 
/*      */     
/*      */ 
/*      */ 
/*  180 */     DEVANAGARI(2406, 2304, 2432), 
/*      */     
/*      */ 
/*      */ 
/*  184 */     BENGALI(2534, 2432, 2560), 
/*      */     
/*      */ 
/*      */ 
/*  188 */     GURMUKHI(2662, 2560, 2688), 
/*      */     
/*      */ 
/*      */ 
/*  192 */     GUJARATI(2790, 2816, 2944), 
/*      */     
/*      */ 
/*      */ 
/*  196 */     ORIYA(2918, 2816, 2944), 
/*      */     
/*      */ 
/*      */ 
/*  200 */     TAMIL(3046, 2944, 3072), 
/*      */     
/*      */ 
/*      */ 
/*  204 */     TELUGU(3174, 3072, 3200), 
/*      */     
/*      */ 
/*      */ 
/*  208 */     KANNADA(3302, 3200, 3328), 
/*      */     
/*      */ 
/*      */ 
/*  212 */     MALAYALAM(3430, 3328, 3456), 
/*      */     
/*      */ 
/*      */ 
/*  216 */     THAI(3664, 3584, 3712), 
/*      */     
/*      */ 
/*      */ 
/*  220 */     LAO(3792, 3712, 3840), 
/*      */     
/*      */ 
/*      */ 
/*  224 */     TIBETAN(3872, 3840, 4096), 
/*      */     
/*      */ 
/*      */ 
/*  228 */     MYANMAR(4160, 4096, 4224), 
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  234 */     ETHIOPIC(4969, 4608, 4992), 
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  241 */     KHMER(6112, 6016, 6144), 
/*      */     
/*      */ 
/*      */ 
/*  245 */     MONGOLIAN(6160, 6144, 6400), 
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  252 */     NKO(1984, 1984, 2048), 
/*      */     
/*      */ 
/*      */ 
/*  256 */     MYANMAR_SHAN(4240, 4096, 4256), 
/*      */     
/*      */ 
/*      */ 
/*  260 */     LIMBU(6470, 6400, 6480), 
/*      */     
/*      */ 
/*      */ 
/*  264 */     NEW_TAI_LUE(6608, 6528, 6624), 
/*      */     
/*      */ 
/*      */ 
/*  268 */     BALINESE(6992, 6912, 7040), 
/*      */     
/*      */ 
/*      */ 
/*  272 */     SUNDANESE(7088, 7040, 7104), 
/*      */     
/*      */ 
/*      */ 
/*  276 */     LEPCHA(7232, 7168, 7248), 
/*      */     
/*      */ 
/*      */ 
/*  280 */     OL_CHIKI(7248, 7248, 7296), 
/*      */     
/*      */ 
/*      */ 
/*  284 */     VAI(42528, 42240, 42560), 
/*      */     
/*      */ 
/*      */ 
/*  288 */     SAURASHTRA(43216, 43136, 43232), 
/*      */     
/*      */ 
/*      */ 
/*  292 */     KAYAH_LI(43264, 43264, 43312), 
/*      */     
/*      */ 
/*      */ 
/*  296 */     CHAM(43600, 43520, 43616), 
/*      */     
/*      */ 
/*      */ 
/*  300 */     TAI_THAM_HORA(6784, 6688, 6832), 
/*      */     
/*      */ 
/*      */ 
/*  304 */     TAI_THAM_THAM(6800, 6688, 6832), 
/*      */     
/*      */ 
/*      */ 
/*  308 */     JAVANESE(43472, 43392, 43488), 
/*      */     
/*      */ 
/*      */ 
/*  312 */     MEETEI_MAYEK(44016, 43968, 44032);
/*      */     
/*      */     private static int toRangeIndex(Range paramRange) {
/*  315 */       int i = paramRange.ordinal();
/*  316 */       return i < 19 ? i : -1;
/*      */     }
/*      */     
/*      */     private static Range indexToRange(int paramInt) {
/*  320 */       return paramInt < 19 ? values()[paramInt] : null;
/*      */     }
/*      */     
/*      */     private static int toRangeMask(Set<Range> paramSet) {
/*  324 */       int i = 0;
/*  325 */       for (Range localRange : paramSet) {
/*  326 */         int j = localRange.ordinal();
/*  327 */         if (j < 19) {
/*  328 */           i |= 1 << j;
/*      */         }
/*      */       }
/*  331 */       return i;
/*      */     }
/*      */     
/*      */     private static Set<Range> maskToRangeSet(int paramInt) {
/*  335 */       EnumSet localEnumSet = EnumSet.noneOf(Range.class);
/*  336 */       Range[] arrayOfRange = values();
/*  337 */       for (int i = 0; i < 19; i++) {
/*  338 */         if ((paramInt & 1 << i) != 0) {
/*  339 */           localEnumSet.add(arrayOfRange[i]);
/*      */         }
/*      */       }
/*  342 */       return localEnumSet;
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     private Range(int paramInt1, int paramInt2, int paramInt3)
/*      */     {
/*  352 */       this.base = (paramInt1 - ('0' + getNumericBase()));
/*  353 */       this.start = paramInt2;
/*  354 */       this.end = paramInt3;
/*      */     }
/*      */     
/*      */     private int getDigitBase() {
/*  358 */       return this.base;
/*      */     }
/*      */     
/*      */     char getNumericBase() {
/*  362 */       return '\000';
/*      */     }
/*      */     
/*      */     private boolean inRange(int paramInt) {
/*  366 */       return (this.start <= paramInt) && (paramInt < this.end);
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     private final int base;
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     private final int start;
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     private final int end;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  498 */   private static final char[] bases = { '\000', 'ذ', 'ۀ', 'श', 'শ', 'ਸ਼', 'શ', 'ଶ', 'ஶ', 'శ', 'ಶ', 'ശ', 'ภ', 'ຠ', '໰', 'တ', 'ጸ', 'ឰ', '០' };
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  522 */   private static final char[] contexts = { '\000', '̀', '؀', 'ހ', '؀', 'ހ', 'ऀ', 'ঀ', 'ঀ', '਀', '਀', '઀', '઀', '଀', '଀', '஀', '஀', 'ఀ', 'ఀ', 'ಀ', 'ಀ', 'ഀ', 'ഀ', '඀', '฀', '຀', '຀', 'ༀ', 'ༀ', 'က', 'က', 'ႀ', 'ሀ', 'ᎀ', 'ក', '᠀', '᠀', 'ᤀ', 65535 };
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  548 */   private static int ctCache = 0;
/*  549 */   private static int ctCacheLimit = contexts.length - 2;
/*      */   
/*      */   private static int getContextKey(char paramChar)
/*      */   {
/*  553 */     if (paramChar < contexts[ctCache])
/*  554 */       while ((ctCache > 0) && (paramChar < contexts[ctCache])) ctCache -= 1;
/*  555 */     if (paramChar >= contexts[(ctCache + 1)]) {
/*  556 */       while ((ctCache < ctCacheLimit) && (paramChar >= contexts[(ctCache + 1)])) { ctCache += 1;
/*      */       }
/*      */     }
/*      */     
/*  560 */     return (ctCache & 0x1) == 0 ? ctCache / 2 : 0;
/*      */   }
/*      */   
/*      */ 
/*  564 */   private volatile transient Range currentRange = Range.EUROPEAN;
/*      */   
/*      */   private Range rangeForCodePoint(int paramInt) {
/*  567 */     if (this.currentRange.inRange(paramInt)) {
/*  568 */       return this.currentRange;
/*      */     }
/*      */     
/*  571 */     Range[] arrayOfRange = this.rangeArray;
/*  572 */     int i; if (arrayOfRange.length > 3) {
/*  573 */       i = 0;
/*  574 */       int j = arrayOfRange.length - 1;
/*  575 */       while (i <= j) {
/*  576 */         int k = (i + j) / 2;
/*  577 */         Range localRange = arrayOfRange[k];
/*  578 */         if (paramInt < localRange.start) {
/*  579 */           j = k - 1;
/*  580 */         } else if (paramInt >= localRange.end) {
/*  581 */           i = k + 1;
/*      */         } else {
/*  583 */           this.currentRange = localRange;
/*  584 */           return localRange;
/*      */         }
/*      */       }
/*      */     } else {
/*  588 */       for (i = 0; i < arrayOfRange.length; i++) {
/*  589 */         if (arrayOfRange[i].inRange(paramInt)) {
/*  590 */           return arrayOfRange[i];
/*      */         }
/*      */       }
/*      */     }
/*  594 */     return Range.EUROPEAN;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  603 */   private static int[] strongTable = { 0, 65, 91, 97, 123, 170, 171, 181, 182, 186, 187, 192, 215, 216, 247, 248, 697, 699, 706, 720, 722, 736, 741, 750, 751, 880, 884, 886, 894, 902, 903, 904, 1014, 1015, 1155, 1162, 1418, 1470, 1471, 1472, 1473, 1475, 1476, 1478, 1479, 1488, 1536, 1544, 1545, 1547, 1548, 1549, 1550, 1563, 1611, 1645, 1648, 1649, 1750, 1765, 1767, 1774, 1776, 1786, 1809, 1810, 1840, 1869, 1958, 1969, 2027, 2036, 2038, 2042, 2070, 2074, 2075, 2084, 2085, 2088, 2089, 2096, 2137, 2142, 2276, 2307, 2362, 2363, 2364, 2365, 2369, 2377, 2381, 2382, 2385, 2392, 2402, 2404, 2433, 2434, 2492, 2493, 2497, 2503, 2509, 2510, 2530, 2534, 2546, 2548, 2555, 2563, 2620, 2622, 2625, 2649, 2672, 2674, 2677, 2691, 2748, 2749, 2753, 2761, 2765, 2768, 2786, 2790, 2801, 2818, 2876, 2877, 2879, 2880, 2881, 2887, 2893, 2903, 2914, 2918, 2946, 2947, 3008, 3009, 3021, 3024, 3059, 3073, 3134, 3137, 3142, 3160, 3170, 3174, 3192, 3199, 3260, 3261, 3276, 3285, 3298, 3302, 3393, 3398, 3405, 3406, 3426, 3430, 3530, 3535, 3538, 3544, 3633, 3634, 3636, 3648, 3655, 3663, 3761, 3762, 3764, 3773, 3784, 3792, 3864, 3866, 3893, 3894, 3895, 3896, 3897, 3902, 3953, 3967, 3968, 3973, 3974, 3976, 3981, 4030, 4038, 4039, 4141, 4145, 4146, 4152, 4153, 4155, 4157, 4159, 4184, 4186, 4190, 4193, 4209, 4213, 4226, 4227, 4229, 4231, 4237, 4238, 4253, 4254, 4957, 4960, 5008, 5024, 5120, 5121, 5760, 5761, 5787, 5792, 5906, 5920, 5938, 5941, 5970, 5984, 6002, 6016, 6068, 6070, 6071, 6078, 6086, 6087, 6089, 6100, 6107, 6108, 6109, 6112, 6128, 6160, 6313, 6314, 6432, 6435, 6439, 6441, 6450, 6451, 6457, 6470, 6622, 6656, 6679, 6681, 6742, 6743, 6744, 6753, 6754, 6755, 6757, 6765, 6771, 6784, 6912, 6916, 6964, 6965, 6966, 6971, 6972, 6973, 6978, 6979, 7019, 7028, 7040, 7042, 7074, 7078, 7080, 7082, 7083, 7084, 7142, 7143, 7144, 7146, 7149, 7150, 7151, 7154, 7212, 7220, 7222, 7227, 7376, 7379, 7380, 7393, 7394, 7401, 7405, 7406, 7412, 7413, 7616, 7680, 8125, 8126, 8127, 8130, 8141, 8144, 8157, 8160, 8173, 8178, 8189, 8206, 8208, 8305, 8308, 8319, 8320, 8336, 8352, 8450, 8451, 8455, 8456, 8458, 8468, 8469, 8470, 8473, 8478, 8484, 8485, 8486, 8487, 8488, 8489, 8490, 8494, 8495, 8506, 8508, 8512, 8517, 8522, 8526, 8528, 8544, 8585, 9014, 9083, 9109, 9110, 9372, 9450, 9900, 9901, 10240, 10496, 11264, 11493, 11499, 11503, 11506, 11513, 11520, 11647, 11648, 11744, 12293, 12296, 12321, 12330, 12337, 12342, 12344, 12349, 12353, 12441, 12445, 12448, 12449, 12539, 12540, 12736, 12784, 12829, 12832, 12880, 12896, 12924, 12927, 12977, 12992, 13004, 13008, 13175, 13179, 13278, 13280, 13311, 13312, 19904, 19968, 42128, 42192, 42509, 42512, 42607, 42624, 42655, 42656, 42736, 42738, 42752, 42786, 42888, 42889, 43010, 43011, 43014, 43015, 43019, 43020, 43045, 43047, 43048, 43056, 43064, 43072, 43124, 43136, 43204, 43214, 43232, 43250, 43302, 43310, 43335, 43346, 43392, 43395, 43443, 43444, 43446, 43450, 43452, 43453, 43561, 43567, 43569, 43571, 43573, 43584, 43587, 43588, 43596, 43597, 43696, 43697, 43698, 43701, 43703, 43705, 43710, 43712, 43713, 43714, 43756, 43758, 43766, 43777, 44005, 44006, 44008, 44009, 44013, 44016, 64286, 64287, 64297, 64298, 64830, 64848, 65021, 65136, 65279, 65313, 65339, 65345, 65371, 65382, 65504, 65536, 65793, 65794, 65856, 66000, 66045, 66176, 67871, 67872, 68097, 68112, 68152, 68160, 68409, 68416, 69216, 69632, 69633, 69634, 69688, 69703, 69714, 69734, 69760, 69762, 69811, 69815, 69817, 69819, 69888, 69891, 69927, 69932, 69933, 69942, 70016, 70018, 70070, 70079, 71339, 71340, 71341, 71342, 71344, 71350, 71351, 71360, 94095, 94099, 119143, 119146, 119155, 119171, 119173, 119180, 119210, 119214, 119296, 119648, 120539, 120540, 120597, 120598, 120655, 120656, 120713, 120714, 120771, 120772, 120782, 126464, 126704, 127248, 127338, 127344, 127744, 128140, 128141, 128292, 128293, 131072, 917505, 983040, 1114110, 1114111 };
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  909 */   private volatile transient int stCache = 0;
/*      */   
/*      */   private boolean isStrongDirectional(char paramChar) {
/*  912 */     int i = this.stCache;
/*  913 */     if (paramChar < strongTable[i]) {
/*  914 */       i = search(paramChar, strongTable, 0, i);
/*  915 */     } else if (paramChar >= strongTable[(i + 1)]) {
/*  916 */       i = search(paramChar, strongTable, i + 1, strongTable.length - i - 1);
/*      */     }
/*      */     
/*  919 */     boolean bool = (i & 0x1) == 1;
/*  920 */     this.stCache = i;
/*  921 */     return bool;
/*      */   }
/*      */   
/*      */   private static int getKeyFromMask(int paramInt) {
/*  925 */     int i = 0;
/*  926 */     while ((i < 19) && ((paramInt & 1 << i) == 0)) {
/*  927 */       i++;
/*      */     }
/*  929 */     if ((i == 19) || ((paramInt & (1 << i ^ 0xFFFFFFFF)) != 0)) {
/*  930 */       throw new IllegalArgumentException("invalid shaper: " + Integer.toHexString(paramInt));
/*      */     }
/*  932 */     return i;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static NumericShaper getShaper(int paramInt)
/*      */   {
/*  944 */     int i = getKeyFromMask(paramInt);
/*  945 */     return new NumericShaper(i, paramInt);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static NumericShaper getShaper(Range paramRange)
/*      */   {
/*  960 */     return new NumericShaper(paramRange, EnumSet.of(paramRange));
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static NumericShaper getContextualShaper(int paramInt)
/*      */   {
/*  978 */     paramInt |= 0x80000000;
/*  979 */     return new NumericShaper(0, paramInt);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static NumericShaper getContextualShaper(Set<Range> paramSet)
/*      */   {
/*  999 */     NumericShaper localNumericShaper = new NumericShaper(Range.EUROPEAN, paramSet);
/* 1000 */     localNumericShaper.mask = Integer.MIN_VALUE;
/* 1001 */     return localNumericShaper;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static NumericShaper getContextualShaper(int paramInt1, int paramInt2)
/*      */   {
/* 1020 */     int i = getKeyFromMask(paramInt2);
/* 1021 */     paramInt1 |= 0x80000000;
/* 1022 */     return new NumericShaper(i, paramInt1);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static NumericShaper getContextualShaper(Set<Range> paramSet, Range paramRange)
/*      */   {
/* 1042 */     if (paramRange == null) {
/* 1043 */       throw new NullPointerException();
/*      */     }
/* 1045 */     NumericShaper localNumericShaper = new NumericShaper(paramRange, paramSet);
/* 1046 */     localNumericShaper.mask = Integer.MIN_VALUE;
/* 1047 */     return localNumericShaper;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   private NumericShaper(int paramInt1, int paramInt2)
/*      */   {
/* 1054 */     this.key = paramInt1;
/* 1055 */     this.mask = paramInt2;
/*      */   }
/*      */   
/*      */   private NumericShaper(Range paramRange, Set<Range> paramSet) {
/* 1059 */     this.shapingRange = paramRange;
/* 1060 */     this.rangeSet = EnumSet.copyOf(paramSet);
/*      */     
/*      */ 
/*      */ 
/* 1064 */     if ((this.rangeSet.contains(Range.EASTERN_ARABIC)) && 
/* 1065 */       (this.rangeSet.contains(Range.ARABIC))) {
/* 1066 */       this.rangeSet.remove(Range.ARABIC);
/*      */     }
/*      */     
/*      */ 
/*      */ 
/* 1071 */     if ((this.rangeSet.contains(Range.TAI_THAM_THAM)) && 
/* 1072 */       (this.rangeSet.contains(Range.TAI_THAM_HORA))) {
/* 1073 */       this.rangeSet.remove(Range.TAI_THAM_HORA);
/*      */     }
/*      */     
/* 1076 */     this.rangeArray = ((Range[])this.rangeSet.toArray(new Range[this.rangeSet.size()]));
/* 1077 */     if (this.rangeArray.length > 3)
/*      */     {
/* 1079 */       Arrays.sort(this.rangeArray, new Comparator()
/*      */       {
/*      */         public int compare(NumericShaper.Range paramAnonymousRange1, NumericShaper.Range paramAnonymousRange2) {
/* 1082 */           return paramAnonymousRange1.base == paramAnonymousRange2.base ? 0 : paramAnonymousRange1.base > paramAnonymousRange2.base ? 1 : -1;
/*      */         }
/*      */       });
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public void shape(char[] paramArrayOfChar, int paramInt1, int paramInt2)
/*      */   {
/* 1101 */     checkParams(paramArrayOfChar, paramInt1, paramInt2);
/* 1102 */     if (isContextual()) {
/* 1103 */       if (this.rangeSet == null) {
/* 1104 */         shapeContextually(paramArrayOfChar, paramInt1, paramInt2, this.key);
/*      */       } else {
/* 1106 */         shapeContextually(paramArrayOfChar, paramInt1, paramInt2, this.shapingRange);
/*      */       }
/*      */     } else {
/* 1109 */       shapeNonContextually(paramArrayOfChar, paramInt1, paramInt2);
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public void shape(char[] paramArrayOfChar, int paramInt1, int paramInt2, int paramInt3)
/*      */   {
/* 1132 */     checkParams(paramArrayOfChar, paramInt1, paramInt2);
/* 1133 */     if (isContextual()) {
/* 1134 */       int i = getKeyFromMask(paramInt3);
/* 1135 */       if (this.rangeSet == null) {
/* 1136 */         shapeContextually(paramArrayOfChar, paramInt1, paramInt2, i);
/*      */       } else {
/* 1138 */         shapeContextually(paramArrayOfChar, paramInt1, paramInt2, Range.values()[i]);
/*      */       }
/*      */     } else {
/* 1141 */       shapeNonContextually(paramArrayOfChar, paramInt1, paramInt2);
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public void shape(char[] paramArrayOfChar, int paramInt1, int paramInt2, Range paramRange)
/*      */   {
/* 1164 */     checkParams(paramArrayOfChar, paramInt1, paramInt2);
/* 1165 */     if (paramRange == null) {
/* 1166 */       throw new NullPointerException("context is null");
/*      */     }
/*      */     
/* 1169 */     if (isContextual()) {
/* 1170 */       if (this.rangeSet != null) {
/* 1171 */         shapeContextually(paramArrayOfChar, paramInt1, paramInt2, paramRange);
/*      */       } else {
/* 1173 */         int i = Range.toRangeIndex(paramRange);
/* 1174 */         if (i >= 0) {
/* 1175 */           shapeContextually(paramArrayOfChar, paramInt1, paramInt2, i);
/*      */         } else {
/* 1177 */           shapeContextually(paramArrayOfChar, paramInt1, paramInt2, this.shapingRange);
/*      */         }
/*      */       }
/*      */     } else {
/* 1181 */       shapeNonContextually(paramArrayOfChar, paramInt1, paramInt2);
/*      */     }
/*      */   }
/*      */   
/*      */   private void checkParams(char[] paramArrayOfChar, int paramInt1, int paramInt2) {
/* 1186 */     if (paramArrayOfChar == null) {
/* 1187 */       throw new NullPointerException("text is null");
/*      */     }
/* 1189 */     if ((paramInt1 < 0) || (paramInt1 > paramArrayOfChar.length) || (paramInt1 + paramInt2 < 0) || (paramInt1 + paramInt2 > paramArrayOfChar.length))
/*      */     {
/*      */ 
/*      */ 
/* 1193 */       throw new IndexOutOfBoundsException("bad start or count for text of length " + paramArrayOfChar.length);
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public boolean isContextual()
/*      */   {
/* 1205 */     return (this.mask & 0x80000000) != 0;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public int getRanges()
/*      */   {
/* 1224 */     return this.mask & 0x7FFFFFFF;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public Set<Range> getRangeSet()
/*      */   {
/* 1235 */     if (this.rangeSet != null) {
/* 1236 */       return EnumSet.copyOf(this.rangeSet);
/*      */     }
/* 1238 */     return Range.maskToRangeSet(this.mask);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   private void shapeNonContextually(char[] paramArrayOfChar, int paramInt1, int paramInt2)
/*      */   {
/* 1246 */     int j = 48;
/* 1247 */     int i; if (this.shapingRange != null) {
/* 1248 */       i = this.shapingRange.getDigitBase();
/* 1249 */       j = (char)(j + this.shapingRange.getNumericBase());
/*      */     } else {
/* 1251 */       i = bases[this.key];
/* 1252 */       if (this.key == 16) {
/* 1253 */         j = (char)(j + 1);
/*      */       }
/*      */     }
/* 1256 */     int k = paramInt1; for (int m = paramInt1 + paramInt2; k < m; k++) {
/* 1257 */       int n = paramArrayOfChar[k];
/* 1258 */       if ((n >= j) && (n <= 57)) {
/* 1259 */         paramArrayOfChar[k] = ((char)(n + i));
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private synchronized void shapeContextually(char[] paramArrayOfChar, int paramInt1, int paramInt2, int paramInt3)
/*      */   {
/* 1271 */     if ((this.mask & 1 << paramInt3) == 0) {
/* 1272 */       paramInt3 = 0;
/*      */     }
/* 1274 */     int i = paramInt3;
/*      */     
/* 1276 */     int j = bases[paramInt3];
/* 1277 */     int k = paramInt3 == 16 ? 49 : 48;
/*      */     
/* 1279 */     synchronized (NumericShaper.class) {
/* 1280 */       int m = paramInt1; for (int n = paramInt1 + paramInt2; m < n; m++) {
/* 1281 */         char c = paramArrayOfChar[m];
/* 1282 */         if ((c >= k) && (c <= '9')) {
/* 1283 */           paramArrayOfChar[m] = ((char)(c + j));
/*      */         }
/*      */         
/* 1286 */         if (isStrongDirectional(c)) {
/* 1287 */           int i1 = getContextKey(c);
/* 1288 */           if (i1 != i) {
/* 1289 */             i = i1;
/*      */             
/* 1291 */             paramInt3 = i1;
/* 1292 */             if (((this.mask & 0x4) != 0) && ((paramInt3 == 1) || (paramInt3 == 2)))
/*      */             {
/*      */ 
/* 1295 */               paramInt3 = 2;
/* 1296 */             } else if (((this.mask & 0x2) != 0) && ((paramInt3 == 1) || (paramInt3 == 2)))
/*      */             {
/*      */ 
/* 1299 */               paramInt3 = 1;
/* 1300 */             } else if ((this.mask & 1 << paramInt3) == 0) {
/* 1301 */               paramInt3 = 0;
/*      */             }
/*      */             
/* 1304 */             j = bases[paramInt3];
/*      */             
/* 1306 */             k = paramInt3 == 16 ? 49 : 48;
/*      */           }
/*      */         }
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */   private void shapeContextually(char[] paramArrayOfChar, int paramInt1, int paramInt2, Range paramRange)
/*      */   {
/* 1315 */     if ((paramRange == null) || (!this.rangeSet.contains(paramRange))) {
/* 1316 */       paramRange = Range.EUROPEAN;
/*      */     }
/*      */     
/* 1319 */     Range localRange = paramRange;
/* 1320 */     int i = paramRange.getDigitBase();
/* 1321 */     int j = (char)('0' + paramRange.getNumericBase());
/* 1322 */     int k = paramInt1 + paramInt2;
/* 1323 */     for (int m = paramInt1; m < k; m++) {
/* 1324 */       int n = paramArrayOfChar[m];
/* 1325 */       if ((n >= j) && (n <= 57)) {
/* 1326 */         paramArrayOfChar[m] = ((char)(n + i));
/*      */ 
/*      */       }
/* 1329 */       else if (isStrongDirectional(n)) {
/* 1330 */         paramRange = rangeForCodePoint(n);
/* 1331 */         if (paramRange != localRange) {
/* 1332 */           localRange = paramRange;
/* 1333 */           i = paramRange.getDigitBase();
/* 1334 */           j = (char)('0' + paramRange.getNumericBase());
/*      */         }
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public int hashCode()
/*      */   {
/* 1346 */     int i = this.mask;
/* 1347 */     if (this.rangeSet != null)
/*      */     {
/*      */ 
/*      */ 
/* 1351 */       i &= 0x80000000;
/* 1352 */       i ^= this.rangeSet.hashCode();
/*      */     }
/* 1354 */     return i;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public boolean equals(Object paramObject)
/*      */   {
/* 1376 */     if (paramObject != null) {
/*      */       try {
/* 1378 */         NumericShaper localNumericShaper = (NumericShaper)paramObject;
/* 1379 */         if (this.rangeSet != null) {
/* 1380 */           if (localNumericShaper.rangeSet != null)
/*      */           {
/* 1382 */             return (isContextual() == localNumericShaper.isContextual()) && (this.rangeSet.equals(localNumericShaper.rangeSet)) && (this.shapingRange == localNumericShaper.shapingRange);
/*      */           }
/*      */           
/*      */ 
/*      */ 
/* 1387 */           return (isContextual() == localNumericShaper.isContextual()) && (this.rangeSet.equals(Range.maskToRangeSet(localNumericShaper.mask))) && (this.shapingRange == Range.indexToRange(localNumericShaper.key)); }
/* 1388 */         if (localNumericShaper.rangeSet != null) {
/* 1389 */           Set localSet = Range.maskToRangeSet(this.mask);
/* 1390 */           Range localRange = Range.indexToRange(this.key);
/*      */           
/* 1392 */           return (isContextual() == localNumericShaper.isContextual()) && (localSet.equals(localNumericShaper.rangeSet)) && (localRange == localNumericShaper.shapingRange);
/*      */         }
/*      */         
/* 1395 */         return (localNumericShaper.mask == this.mask) && (localNumericShaper.key == this.key);
/*      */       }
/*      */       catch (ClassCastException localClassCastException) {}
/*      */     }
/*      */     
/* 1400 */     return false;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public String toString()
/*      */   {
/* 1409 */     StringBuilder localStringBuilder = new StringBuilder(super.toString());
/*      */     
/* 1411 */     localStringBuilder.append("[contextual:").append(isContextual());
/*      */     
/* 1413 */     Object localObject = null;
/* 1414 */     if (isContextual()) {
/* 1415 */       localStringBuilder.append(", context:");
/* 1416 */       localStringBuilder.append(this.shapingRange == null ? Range.values()[this.key] : this.shapingRange);
/*      */     }
/*      */     
/* 1419 */     if (this.rangeSet == null) {
/* 1420 */       localStringBuilder.append(", range(s): ");
/* 1421 */       int i = 1;
/* 1422 */       for (int j = 0; j < 19; j++) {
/* 1423 */         if ((this.mask & 1 << j) != 0) {
/* 1424 */           if (i != 0) {
/* 1425 */             i = 0;
/*      */           } else {
/* 1427 */             localStringBuilder.append(", ");
/*      */           }
/* 1429 */           localStringBuilder.append(Range.values()[j]);
/*      */         }
/*      */       }
/*      */     } else {
/* 1433 */       localStringBuilder.append(", range set: ").append(this.rangeSet);
/*      */     }
/* 1435 */     localStringBuilder.append(']');
/*      */     
/* 1437 */     return localStringBuilder.toString();
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   private static int getHighBit(int paramInt)
/*      */   {
/* 1445 */     if (paramInt <= 0) {
/* 1446 */       return -32;
/*      */     }
/*      */     
/* 1449 */     int i = 0;
/*      */     
/* 1451 */     if (paramInt >= 65536) {
/* 1452 */       paramInt >>= 16;
/* 1453 */       i += 16;
/*      */     }
/*      */     
/* 1456 */     if (paramInt >= 256) {
/* 1457 */       paramInt >>= 8;
/* 1458 */       i += 8;
/*      */     }
/*      */     
/* 1461 */     if (paramInt >= 16) {
/* 1462 */       paramInt >>= 4;
/* 1463 */       i += 4;
/*      */     }
/*      */     
/* 1466 */     if (paramInt >= 4) {
/* 1467 */       paramInt >>= 2;
/* 1468 */       i += 2;
/*      */     }
/*      */     
/* 1471 */     if (paramInt >= 2) {
/* 1472 */       i++;
/*      */     }
/*      */     
/* 1475 */     return i;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   private static int search(int paramInt1, int[] paramArrayOfInt, int paramInt2, int paramInt3)
/*      */   {
/* 1483 */     int i = 1 << getHighBit(paramInt3);
/* 1484 */     int j = paramInt3 - i;
/* 1485 */     int k = i;
/* 1486 */     int m = paramInt2;
/*      */     
/* 1488 */     if (paramInt1 >= paramArrayOfInt[(m + j)]) {
/* 1489 */       m += j;
/*      */     }
/*      */     
/* 1492 */     while (k > 1) {
/* 1493 */       k >>= 1;
/*      */       
/* 1495 */       if (paramInt1 >= paramArrayOfInt[(m + k)]) {
/* 1496 */         m += k;
/*      */       }
/*      */     }
/*      */     
/* 1500 */     return m;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private void writeObject(ObjectOutputStream paramObjectOutputStream)
/*      */     throws IOException
/*      */   {
/* 1514 */     if (this.shapingRange != null) {
/* 1515 */       int i = Range.toRangeIndex(this.shapingRange);
/* 1516 */       if (i >= 0) {
/* 1517 */         this.key = i;
/*      */       }
/*      */     }
/* 1520 */     if (this.rangeSet != null) {
/* 1521 */       this.mask |= Range.toRangeMask(this.rangeSet);
/*      */     }
/* 1523 */     paramObjectOutputStream.defaultWriteObject();
/*      */   }
/*      */ }


/* Location:              /Users/guanxiaoda/Documents/workspace/rt.jar!/java/awt/font/NumericShaper.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */