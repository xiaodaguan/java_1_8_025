/*     */ package java.text;
/*     */ 
/*     */ import java.util.Vector;
/*     */ import sun.text.IntHashtable;
/*     */ import sun.text.UCompactIntArray;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ final class RBCollationTables
/*     */ {
/*     */   static final int EXPANDCHARINDEX = 2113929216;
/*     */   static final int CONTRACTCHARINDEX = 2130706432;
/*     */   static final int UNMAPPED = -1;
/*     */   static final int PRIMARYORDERMASK = -65536;
/*     */   static final int SECONDARYORDERMASK = 65280;
/*     */   static final int TERTIARYORDERMASK = 255;
/*     */   static final int PRIMARYDIFFERENCEONLY = -65536;
/*     */   static final int SECONDARYDIFFERENCEONLY = -256;
/*     */   static final int PRIMARYORDERSHIFT = 16;
/*     */   static final int SECONDARYORDERSHIFT = 8;
/*     */   
/*     */   public RBCollationTables(String paramString, int paramInt)
/*     */     throws ParseException
/*     */   {
/*  80 */     this.rules = paramString;
/*     */     
/*  82 */     RBTableBuilder localRBTableBuilder = new RBTableBuilder(new BuildAPI(null));
/*  83 */     localRBTableBuilder.build(paramString, paramInt);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   final class BuildAPI
/*     */   {
/*     */     private BuildAPI() {}
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     void fillInTables(boolean paramBoolean1, boolean paramBoolean2, UCompactIntArray paramUCompactIntArray, Vector<Vector<EntryPair>> paramVector, Vector<int[]> paramVector1, IntHashtable paramIntHashtable, short paramShort1, short paramShort2)
/*     */     {
/* 120 */       RBCollationTables.this.frenchSec = paramBoolean1;
/* 121 */       RBCollationTables.this.seAsianSwapping = paramBoolean2;
/* 122 */       RBCollationTables.this.mapping = paramUCompactIntArray;
/* 123 */       RBCollationTables.this.contractTable = paramVector;
/* 124 */       RBCollationTables.this.expandTable = paramVector1;
/* 125 */       RBCollationTables.this.contractFlags = paramIntHashtable;
/* 126 */       RBCollationTables.this.maxSecOrder = paramShort1;
/* 127 */       RBCollationTables.this.maxTerOrder = paramShort2;
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public String getRules()
/*     */   {
/* 138 */     return this.rules;
/*     */   }
/*     */   
/*     */   public boolean isFrenchSec() {
/* 142 */     return this.frenchSec;
/*     */   }
/*     */   
/*     */   public boolean isSEAsianSwapping() {
/* 146 */     return this.seAsianSwapping;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   Vector<EntryPair> getContractValues(int paramInt)
/*     */   {
/* 160 */     int i = this.mapping.elementAt(paramInt);
/* 161 */     return getContractValuesImpl(i - 2130706432);
/*     */   }
/*     */   
/*     */ 
/*     */   private Vector<EntryPair> getContractValuesImpl(int paramInt)
/*     */   {
/* 167 */     if (paramInt >= 0)
/*     */     {
/* 169 */       return (Vector)this.contractTable.elementAt(paramInt);
/*     */     }
/*     */     
/*     */ 
/* 173 */     return null;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   boolean usedInContractSeq(int paramInt)
/*     */   {
/* 182 */     return this.contractFlags.get(paramInt) == 1;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   int getMaxExpansion(int paramInt)
/*     */   {
/* 196 */     int i = 1;
/*     */     
/* 198 */     if (this.expandTable != null)
/*     */     {
/*     */ 
/*     */ 
/*     */ 
/* 203 */       for (int j = 0; j < this.expandTable.size(); j++) {
/* 204 */         int[] arrayOfInt = (int[])this.expandTable.elementAt(j);
/* 205 */         int k = arrayOfInt.length;
/*     */         
/* 207 */         if ((k > i) && (arrayOfInt[(k - 1)] == paramInt)) {
/* 208 */           i = k;
/*     */         }
/*     */       }
/*     */     }
/*     */     
/* 213 */     return i;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   final int[] getExpandValueList(int paramInt)
/*     */   {
/* 222 */     return (int[])this.expandTable.elementAt(paramInt - 2113929216);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   int getUnicodeOrder(int paramInt)
/*     */   {
/* 230 */     return this.mapping.elementAt(paramInt);
/*     */   }
/*     */   
/*     */   short getMaxSecOrder() {
/* 234 */     return this.maxSecOrder;
/*     */   }
/*     */   
/*     */   short getMaxTerOrder() {
/* 238 */     return this.maxTerOrder;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   static void reverse(StringBuffer paramStringBuffer, int paramInt1, int paramInt2)
/*     */   {
/* 248 */     int i = paramInt1;
/*     */     
/*     */ 
/* 251 */     int j = paramInt2 - 1;
/* 252 */     while (i < j) {
/* 253 */       char c = paramStringBuffer.charAt(i);
/* 254 */       paramStringBuffer.setCharAt(i, paramStringBuffer.charAt(j));
/* 255 */       paramStringBuffer.setCharAt(j, c);
/* 256 */       i++;
/* 257 */       j--;
/*     */     }
/*     */   }
/*     */   
/*     */   static final int getEntry(Vector<EntryPair> paramVector, String paramString, boolean paramBoolean) {
/* 262 */     for (int i = 0; i < paramVector.size(); i++) {
/* 263 */       EntryPair localEntryPair = (EntryPair)paramVector.elementAt(i);
/* 264 */       if ((localEntryPair.fwd == paramBoolean) && (localEntryPair.entryName.equals(paramString))) {
/* 265 */         return i;
/*     */       }
/*     */     }
/* 268 */     return -1;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 290 */   private String rules = null;
/* 291 */   private boolean frenchSec = false;
/* 292 */   private boolean seAsianSwapping = false;
/*     */   
/* 294 */   private UCompactIntArray mapping = null;
/* 295 */   private Vector<Vector<EntryPair>> contractTable = null;
/* 296 */   private Vector<int[]> expandTable = null;
/* 297 */   private IntHashtable contractFlags = null;
/*     */   
/* 299 */   private short maxSecOrder = 0;
/* 300 */   private short maxTerOrder = 0;
/*     */ }


/* Location:              /Users/guanxiaoda/Documents/workspace/rt.jar!/java/text/RBCollationTables.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */