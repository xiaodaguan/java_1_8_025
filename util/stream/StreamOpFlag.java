/*     */ package java.util.stream;
/*     */ 
/*     */ import java.util.EnumMap;
/*     */ import java.util.Map;
/*     */ import java.util.Spliterator;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */  enum StreamOpFlag
/*     */ {
/* 247 */   DISTINCT(0, 
/* 248 */     set(Type.SPLITERATOR).set(Type.STREAM).setAndClear(Type.OP)), 
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
/* 268 */   SORTED(1, 
/* 269 */     set(Type.SPLITERATOR).set(Type.STREAM).setAndClear(Type.OP)), 
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
/* 281 */   ORDERED(2, 
/* 282 */     set(Type.SPLITERATOR).set(Type.STREAM).setAndClear(Type.OP).clear(Type.TERMINAL_OP)
/* 283 */     .clear(Type.UPSTREAM_TERMINAL_OP)), 
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
/* 296 */   SIZED(3, 
/* 297 */     set(Type.SPLITERATOR).set(Type.STREAM).clear(Type.OP)), 
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
/* 327 */   SHORT_CIRCUIT(12, 
/* 328 */     set(Type.OP).set(Type.TERMINAL_OP));
/*     */   
/*     */ 
/*     */   private static final int SET_BITS = 1;
/*     */   
/*     */   private static final int CLEAR_BITS = 2;
/*     */   
/*     */   private static final int PRESERVE_BITS = 3;
/*     */   private final Map<Type, Integer> maskTable;
/*     */   private final int bitPosition;
/*     */   private final int set;
/*     */   private final int clear;
/*     */   private final int preserve;
/*     */   
/*     */   static enum Type
/*     */   {
/* 344 */     SPLITERATOR, 
/*     */     
/*     */ 
/*     */ 
/*     */ 
/* 349 */     STREAM, 
/*     */     
/*     */ 
/*     */ 
/*     */ 
/* 354 */     OP, 
/*     */     
/*     */ 
/*     */ 
/*     */ 
/* 359 */     TERMINAL_OP, 
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 365 */     UPSTREAM_TERMINAL_OP;
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     private Type() {}
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private static MaskBuilder set(Type paramType)
/*     */   {
/* 384 */     return new MaskBuilder(new EnumMap(Type.class)).set(paramType);
/*     */   }
/*     */   
/*     */   private static class MaskBuilder {
/*     */     final Map<StreamOpFlag.Type, Integer> map;
/*     */     
/*     */     MaskBuilder(Map<StreamOpFlag.Type, Integer> paramMap) {
/* 391 */       this.map = paramMap;
/*     */     }
/*     */     
/*     */     MaskBuilder mask(StreamOpFlag.Type paramType, Integer paramInteger) {
/* 395 */       this.map.put(paramType, paramInteger);
/* 396 */       return this;
/*     */     }
/*     */     
/*     */     MaskBuilder set(StreamOpFlag.Type paramType) {
/* 400 */       return mask(paramType, Integer.valueOf(1));
/*     */     }
/*     */     
/*     */     MaskBuilder clear(StreamOpFlag.Type paramType) {
/* 404 */       return mask(paramType, Integer.valueOf(2));
/*     */     }
/*     */     
/*     */     MaskBuilder setAndClear(StreamOpFlag.Type paramType) {
/* 408 */       return mask(paramType, Integer.valueOf(3));
/*     */     }
/*     */     
/*     */     Map<StreamOpFlag.Type, Integer> build() {
/* 412 */       for (StreamOpFlag.Type localType : ) {
/* 413 */         this.map.putIfAbsent(localType, Integer.valueOf(0));
/*     */       }
/* 415 */       return this.map;
/*     */     }
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private StreamOpFlag(int paramInt, MaskBuilder paramMaskBuilder)
/*     */   {
/* 446 */     this.maskTable = paramMaskBuilder.build();
/*     */     
/* 448 */     paramInt *= 2;
/* 449 */     this.bitPosition = paramInt;
/* 450 */     this.set = (1 << paramInt);
/* 451 */     this.clear = (2 << paramInt);
/* 452 */     this.preserve = (3 << paramInt);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   int set()
/*     */   {
/* 461 */     return this.set;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   int clear()
/*     */   {
/* 470 */     return this.clear;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   boolean isStreamFlag()
/*     */   {
/* 479 */     return ((Integer)this.maskTable.get(Type.STREAM)).intValue() > 0;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   boolean isKnown(int paramInt)
/*     */   {
/* 491 */     return (paramInt & this.preserve) == this.set;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   boolean isCleared(int paramInt)
/*     */   {
/* 502 */     return (paramInt & this.preserve) == this.clear;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   boolean isPreserved(int paramInt)
/*     */   {
/* 512 */     return (paramInt & this.preserve) == this.preserve;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   boolean canSet(Type paramType)
/*     */   {
/* 522 */     return (((Integer)this.maskTable.get(paramType)).intValue() & 0x1) > 0;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/* 528 */   static final int SPLITERATOR_CHARACTERISTICS_MASK = createMask(Type.SPLITERATOR);
/*     */   
/*     */ 
/*     */ 
/*     */ 
/* 533 */   static final int STREAM_MASK = createMask(Type.STREAM);
/*     */   
/*     */ 
/*     */ 
/*     */ 
/* 538 */   static final int OP_MASK = createMask(Type.OP);
/*     */   
/*     */ 
/*     */ 
/*     */ 
/* 543 */   static final int TERMINAL_OP_MASK = createMask(Type.TERMINAL_OP);
/*     */   
/*     */ 
/*     */ 
/*     */ 
/* 548 */   static final int UPSTREAM_TERMINAL_OP_MASK = createMask(Type.UPSTREAM_TERMINAL_OP);
/*     */   
/*     */   private static int createMask(Type paramType) {
/* 551 */     int i = 0;
/* 552 */     for (StreamOpFlag localStreamOpFlag : values()) {
/* 553 */       i |= ((Integer)localStreamOpFlag.maskTable.get(paramType)).intValue() << localStreamOpFlag.bitPosition;
/*     */     }
/* 555 */     return i;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/* 561 */   private static final int FLAG_MASK = createFlagMask();
/*     */   
/*     */   private static int createFlagMask() {
/* 564 */     int i = 0;
/* 565 */     for (StreamOpFlag localStreamOpFlag : values()) {
/* 566 */       i |= localStreamOpFlag.preserve;
/*     */     }
/* 568 */     return i;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/* 574 */   private static final int FLAG_MASK_IS = STREAM_MASK;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/* 579 */   private static final int FLAG_MASK_NOT = STREAM_MASK << 1;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 585 */   static final int INITIAL_OPS_VALUE = FLAG_MASK_IS | FLAG_MASK_NOT;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/* 590 */   static final int IS_DISTINCT = DISTINCT.set;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/* 595 */   static final int NOT_DISTINCT = DISTINCT.clear;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/* 600 */   static final int IS_SORTED = SORTED.set;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/* 605 */   static final int NOT_SORTED = SORTED.clear;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/* 610 */   static final int IS_ORDERED = ORDERED.set;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/* 615 */   static final int NOT_ORDERED = ORDERED.clear;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/* 620 */   static final int IS_SIZED = SIZED.set;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/* 625 */   static final int NOT_SIZED = SIZED.clear;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/* 630 */   static final int IS_SHORT_CIRCUIT = SHORT_CIRCUIT.set;
/*     */   
/*     */   private static int getMask(int paramInt) {
/* 633 */     return paramInt == 0 ? FLAG_MASK : (paramInt | (FLAG_MASK_IS & paramInt) << 1 | (FLAG_MASK_NOT & paramInt) >> 1) ^ 0xFFFFFFFF;
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
/*     */   static int combineOpFlags(int paramInt1, int paramInt2)
/*     */   {
/* 691 */     return paramInt2 & getMask(paramInt1) | paramInt1;
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
/*     */   static int toStreamFlags(int paramInt)
/*     */   {
/* 706 */     return (paramInt ^ 0xFFFFFFFF) >> 1 & FLAG_MASK_IS & paramInt;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   static int toCharacteristics(int paramInt)
/*     */   {
/* 716 */     return paramInt & SPLITERATOR_CHARACTERISTICS_MASK;
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
/*     */   static int fromCharacteristics(Spliterator<?> paramSpliterator)
/*     */   {
/* 733 */     int i = paramSpliterator.characteristics();
/* 734 */     if (((i & 0x4) != 0) && (paramSpliterator.getComparator() != null))
/*     */     {
/*     */ 
/* 737 */       return i & SPLITERATOR_CHARACTERISTICS_MASK & 0xFFFFFFFB;
/*     */     }
/*     */     
/* 740 */     return i & SPLITERATOR_CHARACTERISTICS_MASK;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   static int fromCharacteristics(int paramInt)
/*     */   {
/* 751 */     return paramInt & SPLITERATOR_CHARACTERISTICS_MASK;
/*     */   }
/*     */ }


/* Location:              /Users/guanxiaoda/Documents/workspace/rt.jar!/java/util/stream/StreamOpFlag.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */