/*      */ package java.time.zone;
/*      */ 
/*      */ import java.io.DataInput;
/*      */ import java.io.DataOutput;
/*      */ import java.io.IOException;
/*      */ import java.io.InvalidObjectException;
/*      */ import java.io.ObjectInputStream;
/*      */ import java.io.Serializable;
/*      */ import java.time.Duration;
/*      */ import java.time.Instant;
/*      */ import java.time.LocalDate;
/*      */ import java.time.LocalDateTime;
/*      */ import java.time.ZoneOffset;
/*      */ import java.util.ArrayList;
/*      */ import java.util.Arrays;
/*      */ import java.util.Collections;
/*      */ import java.util.List;
/*      */ import java.util.Objects;
/*      */ import java.util.concurrent.ConcurrentHashMap;
/*      */ import java.util.concurrent.ConcurrentMap;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public final class ZoneRules
/*      */   implements Serializable
/*      */ {
/*      */   private static final long serialVersionUID = 3044319355680032515L;
/*      */   private static final int LAST_CACHED_YEAR = 2100;
/*      */   private final long[] standardTransitions;
/*      */   private final ZoneOffset[] standardOffsets;
/*      */   private final long[] savingsInstantTransitions;
/*      */   private final LocalDateTime[] savingsLocalTransitions;
/*      */   private final ZoneOffset[] wallOffsets;
/*      */   private final ZoneOffsetTransitionRule[] lastRules;
/*  150 */   private final transient ConcurrentMap<Integer, ZoneOffsetTransition[]> lastRulesCache = new ConcurrentHashMap();
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*  155 */   private static final long[] EMPTY_LONG_ARRAY = new long[0];
/*      */   
/*      */ 
/*      */ 
/*  159 */   private static final ZoneOffsetTransitionRule[] EMPTY_LASTRULES = new ZoneOffsetTransitionRule[0];
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*  164 */   private static final LocalDateTime[] EMPTY_LDT_ARRAY = new LocalDateTime[0];
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static ZoneRules of(ZoneOffset paramZoneOffset1, ZoneOffset paramZoneOffset2, List<ZoneOffsetTransition> paramList1, List<ZoneOffsetTransition> paramList2, List<ZoneOffsetTransitionRule> paramList)
/*      */   {
/*  181 */     Objects.requireNonNull(paramZoneOffset1, "baseStandardOffset");
/*  182 */     Objects.requireNonNull(paramZoneOffset2, "baseWallOffset");
/*  183 */     Objects.requireNonNull(paramList1, "standardOffsetTransitionList");
/*  184 */     Objects.requireNonNull(paramList2, "transitionList");
/*  185 */     Objects.requireNonNull(paramList, "lastRules");
/*  186 */     return new ZoneRules(paramZoneOffset1, paramZoneOffset2, paramList1, paramList2, paramList);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static ZoneRules of(ZoneOffset paramZoneOffset)
/*      */   {
/*  198 */     Objects.requireNonNull(paramZoneOffset, "offset");
/*  199 */     return new ZoneRules(paramZoneOffset);
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
/*      */   ZoneRules(ZoneOffset paramZoneOffset1, ZoneOffset paramZoneOffset2, List<ZoneOffsetTransition> paramList1, List<ZoneOffsetTransition> paramList2, List<ZoneOffsetTransitionRule> paramList)
/*      */   {
/*  220 */     this.standardTransitions = new long[paramList1.size()];
/*      */     
/*  222 */     this.standardOffsets = new ZoneOffset[paramList1.size() + 1];
/*  223 */     this.standardOffsets[0] = paramZoneOffset1;
/*  224 */     for (int i = 0; i < paramList1.size(); i++) {
/*  225 */       this.standardTransitions[i] = ((ZoneOffsetTransition)paramList1.get(i)).toEpochSecond();
/*  226 */       this.standardOffsets[(i + 1)] = ((ZoneOffsetTransition)paramList1.get(i)).getOffsetAfter();
/*      */     }
/*      */     
/*      */ 
/*  230 */     ArrayList localArrayList1 = new ArrayList();
/*  231 */     ArrayList localArrayList2 = new ArrayList();
/*  232 */     localArrayList2.add(paramZoneOffset2);
/*  233 */     for (ZoneOffsetTransition localZoneOffsetTransition : paramList2) {
/*  234 */       if (localZoneOffsetTransition.isGap()) {
/*  235 */         localArrayList1.add(localZoneOffsetTransition.getDateTimeBefore());
/*  236 */         localArrayList1.add(localZoneOffsetTransition.getDateTimeAfter());
/*      */       } else {
/*  238 */         localArrayList1.add(localZoneOffsetTransition.getDateTimeAfter());
/*  239 */         localArrayList1.add(localZoneOffsetTransition.getDateTimeBefore());
/*      */       }
/*  241 */       localArrayList2.add(localZoneOffsetTransition.getOffsetAfter());
/*      */     }
/*  243 */     this.savingsLocalTransitions = ((LocalDateTime[])localArrayList1.toArray(new LocalDateTime[localArrayList1.size()]));
/*  244 */     this.wallOffsets = ((ZoneOffset[])localArrayList2.toArray(new ZoneOffset[localArrayList2.size()]));
/*      */     
/*      */ 
/*  247 */     this.savingsInstantTransitions = new long[paramList2.size()];
/*  248 */     for (int j = 0; j < paramList2.size(); j++) {
/*  249 */       this.savingsInstantTransitions[j] = ((ZoneOffsetTransition)paramList2.get(j)).toEpochSecond();
/*      */     }
/*      */     
/*      */ 
/*  253 */     if (paramList.size() > 16) {
/*  254 */       throw new IllegalArgumentException("Too many transition rules");
/*      */     }
/*  256 */     this.lastRules = ((ZoneOffsetTransitionRule[])paramList.toArray(new ZoneOffsetTransitionRule[paramList.size()]));
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
/*      */   private ZoneRules(long[] paramArrayOfLong1, ZoneOffset[] paramArrayOfZoneOffset1, long[] paramArrayOfLong2, ZoneOffset[] paramArrayOfZoneOffset2, ZoneOffsetTransitionRule[] paramArrayOfZoneOffsetTransitionRule)
/*      */   {
/*  275 */     this.standardTransitions = paramArrayOfLong1;
/*  276 */     this.standardOffsets = paramArrayOfZoneOffset1;
/*  277 */     this.savingsInstantTransitions = paramArrayOfLong2;
/*  278 */     this.wallOffsets = paramArrayOfZoneOffset2;
/*  279 */     this.lastRules = paramArrayOfZoneOffsetTransitionRule;
/*      */     
/*  281 */     if (paramArrayOfLong2.length == 0) {
/*  282 */       this.savingsLocalTransitions = EMPTY_LDT_ARRAY;
/*      */     }
/*      */     else {
/*  285 */       ArrayList localArrayList = new ArrayList();
/*  286 */       for (int i = 0; i < paramArrayOfLong2.length; i++) {
/*  287 */         ZoneOffset localZoneOffset1 = paramArrayOfZoneOffset2[i];
/*  288 */         ZoneOffset localZoneOffset2 = paramArrayOfZoneOffset2[(i + 1)];
/*  289 */         ZoneOffsetTransition localZoneOffsetTransition = new ZoneOffsetTransition(paramArrayOfLong2[i], localZoneOffset1, localZoneOffset2);
/*  290 */         if (localZoneOffsetTransition.isGap()) {
/*  291 */           localArrayList.add(localZoneOffsetTransition.getDateTimeBefore());
/*  292 */           localArrayList.add(localZoneOffsetTransition.getDateTimeAfter());
/*      */         } else {
/*  294 */           localArrayList.add(localZoneOffsetTransition.getDateTimeAfter());
/*  295 */           localArrayList.add(localZoneOffsetTransition.getDateTimeBefore());
/*      */         }
/*      */       }
/*  298 */       this.savingsLocalTransitions = ((LocalDateTime[])localArrayList.toArray(new LocalDateTime[localArrayList.size()]));
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private ZoneRules(ZoneOffset paramZoneOffset)
/*      */   {
/*  310 */     this.standardOffsets = new ZoneOffset[1];
/*  311 */     this.standardOffsets[0] = paramZoneOffset;
/*  312 */     this.standardTransitions = EMPTY_LONG_ARRAY;
/*  313 */     this.savingsInstantTransitions = EMPTY_LONG_ARRAY;
/*  314 */     this.savingsLocalTransitions = EMPTY_LDT_ARRAY;
/*  315 */     this.wallOffsets = this.standardOffsets;
/*  316 */     this.lastRules = EMPTY_LASTRULES;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private void readObject(ObjectInputStream paramObjectInputStream)
/*      */     throws InvalidObjectException
/*      */   {
/*  326 */     throw new InvalidObjectException("Deserialization via serialization delegate");
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private Object writeReplace()
/*      */   {
/*  392 */     return new Ser((byte)1, this);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   void writeExternal(DataOutput paramDataOutput)
/*      */     throws IOException
/*      */   {
/*  402 */     paramDataOutput.writeInt(this.standardTransitions.length);
/*  403 */     for (long l1 : this.standardTransitions) {
/*  404 */       Ser.writeEpochSec(l1, paramDataOutput);
/*      */     }
/*  406 */     for (ZoneOffset localZoneOffset1 : this.standardOffsets) {
/*  407 */       Ser.writeOffset(localZoneOffset1, paramDataOutput);
/*      */     }
/*  409 */     paramDataOutput.writeInt(this.savingsInstantTransitions.length);
/*  410 */     for (long l2 : this.savingsInstantTransitions)
/*  411 */       Ser.writeEpochSec(l2, paramDataOutput);
/*      */     ZoneOffset localZoneOffset2;
/*  413 */     for (localZoneOffset2 : this.wallOffsets) {
/*  414 */       Ser.writeOffset(localZoneOffset2, paramDataOutput);
/*      */     }
/*  416 */     paramDataOutput.writeByte(this.lastRules.length);
/*  417 */     for (localZoneOffset2 : this.lastRules) {
/*  418 */       localZoneOffset2.writeExternal(paramDataOutput);
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   static ZoneRules readExternal(DataInput paramDataInput)
/*      */     throws IOException, ClassNotFoundException
/*      */   {
/*  430 */     int i = paramDataInput.readInt();
/*  431 */     long[] arrayOfLong1 = i == 0 ? EMPTY_LONG_ARRAY : new long[i];
/*      */     
/*  433 */     for (int j = 0; j < i; j++) {
/*  434 */       arrayOfLong1[j] = Ser.readEpochSec(paramDataInput);
/*      */     }
/*  436 */     ZoneOffset[] arrayOfZoneOffset1 = new ZoneOffset[i + 1];
/*  437 */     for (int k = 0; k < arrayOfZoneOffset1.length; k++) {
/*  438 */       arrayOfZoneOffset1[k] = Ser.readOffset(paramDataInput);
/*      */     }
/*  440 */     k = paramDataInput.readInt();
/*  441 */     long[] arrayOfLong2 = k == 0 ? EMPTY_LONG_ARRAY : new long[k];
/*      */     
/*  443 */     for (int m = 0; m < k; m++) {
/*  444 */       arrayOfLong2[m] = Ser.readEpochSec(paramDataInput);
/*      */     }
/*  446 */     ZoneOffset[] arrayOfZoneOffset2 = new ZoneOffset[k + 1];
/*  447 */     for (int n = 0; n < arrayOfZoneOffset2.length; n++) {
/*  448 */       arrayOfZoneOffset2[n] = Ser.readOffset(paramDataInput);
/*      */     }
/*  450 */     n = paramDataInput.readByte();
/*  451 */     ZoneOffsetTransitionRule[] arrayOfZoneOffsetTransitionRule = n == 0 ? EMPTY_LASTRULES : new ZoneOffsetTransitionRule[n];
/*      */     
/*  453 */     for (int i1 = 0; i1 < n; i1++) {
/*  454 */       arrayOfZoneOffsetTransitionRule[i1] = ZoneOffsetTransitionRule.readExternal(paramDataInput);
/*      */     }
/*  456 */     return new ZoneRules(arrayOfLong1, arrayOfZoneOffset1, arrayOfLong2, arrayOfZoneOffset2, arrayOfZoneOffsetTransitionRule);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public boolean isFixedOffset()
/*      */   {
/*  465 */     return this.savingsInstantTransitions.length == 0;
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
/*      */   public ZoneOffset getOffset(Instant paramInstant)
/*      */   {
/*  480 */     if (this.savingsInstantTransitions.length == 0) {
/*  481 */       return this.standardOffsets[0];
/*      */     }
/*  483 */     long l = paramInstant.getEpochSecond();
/*      */     
/*  485 */     if ((this.lastRules.length > 0) && (l > this.savingsInstantTransitions[(this.savingsInstantTransitions.length - 1)]))
/*      */     {
/*  487 */       i = findYear(l, this.wallOffsets[(this.wallOffsets.length - 1)]);
/*  488 */       ZoneOffsetTransition[] arrayOfZoneOffsetTransition = findTransitionArray(i);
/*  489 */       ZoneOffsetTransition localZoneOffsetTransition = null;
/*  490 */       for (int j = 0; j < arrayOfZoneOffsetTransition.length; j++) {
/*  491 */         localZoneOffsetTransition = arrayOfZoneOffsetTransition[j];
/*  492 */         if (l < localZoneOffsetTransition.toEpochSecond()) {
/*  493 */           return localZoneOffsetTransition.getOffsetBefore();
/*      */         }
/*      */       }
/*  496 */       return localZoneOffsetTransition.getOffsetAfter();
/*      */     }
/*      */     
/*      */ 
/*  500 */     int i = Arrays.binarySearch(this.savingsInstantTransitions, l);
/*  501 */     if (i < 0)
/*      */     {
/*  503 */       i = -i - 2;
/*      */     }
/*  505 */     return this.wallOffsets[(i + 1)];
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
/*      */   public ZoneOffset getOffset(LocalDateTime paramLocalDateTime)
/*      */   {
/*  537 */     Object localObject = getOffsetInfo(paramLocalDateTime);
/*  538 */     if ((localObject instanceof ZoneOffsetTransition)) {
/*  539 */       return ((ZoneOffsetTransition)localObject).getOffsetBefore();
/*      */     }
/*  541 */     return (ZoneOffset)localObject;
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public List<ZoneOffset> getValidOffsets(LocalDateTime paramLocalDateTime)
/*      */   {
/*  588 */     Object localObject = getOffsetInfo(paramLocalDateTime);
/*  589 */     if ((localObject instanceof ZoneOffsetTransition)) {
/*  590 */       return ((ZoneOffsetTransition)localObject).getValidOffsets();
/*      */     }
/*  592 */     return Collections.singletonList((ZoneOffset)localObject);
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
/*      */ 
/*      */ 
/*      */   public ZoneOffsetTransition getTransition(LocalDateTime paramLocalDateTime)
/*      */   {
/*  630 */     Object localObject = getOffsetInfo(paramLocalDateTime);
/*  631 */     return (localObject instanceof ZoneOffsetTransition) ? (ZoneOffsetTransition)localObject : null;
/*      */   }
/*      */   
/*      */   private Object getOffsetInfo(LocalDateTime paramLocalDateTime) {
/*  635 */     if (this.savingsInstantTransitions.length == 0) {
/*  636 */       return this.standardOffsets[0];
/*      */     }
/*      */     Object localObject1;
/*  639 */     if ((this.lastRules.length > 0) && 
/*  640 */       (paramLocalDateTime.isAfter(this.savingsLocalTransitions[(this.savingsLocalTransitions.length - 1)]))) {
/*  641 */       ZoneOffsetTransition[] arrayOfZoneOffsetTransition = findTransitionArray(paramLocalDateTime.getYear());
/*  642 */       localObject1 = null;
/*  643 */       for (ZoneOffsetTransition localZoneOffsetTransition : arrayOfZoneOffsetTransition) {
/*  644 */         localObject1 = findOffsetInfo(paramLocalDateTime, localZoneOffsetTransition);
/*  645 */         if (((localObject1 instanceof ZoneOffsetTransition)) || (localObject1.equals(localZoneOffsetTransition.getOffsetBefore()))) {
/*  646 */           return localObject1;
/*      */         }
/*      */       }
/*  649 */       return localObject1;
/*      */     }
/*      */     
/*      */ 
/*  653 */     int i = Arrays.binarySearch(this.savingsLocalTransitions, paramLocalDateTime);
/*  654 */     if (i == -1)
/*      */     {
/*  656 */       return this.wallOffsets[0];
/*      */     }
/*  658 */     if (i < 0)
/*      */     {
/*  660 */       i = -i - 2;
/*  661 */     } else if ((i < this.savingsLocalTransitions.length - 1) && 
/*  662 */       (this.savingsLocalTransitions[i].equals(this.savingsLocalTransitions[(i + 1)])))
/*      */     {
/*  664 */       i++;
/*      */     }
/*  666 */     if ((i & 0x1) == 0)
/*      */     {
/*  668 */       localObject1 = this.savingsLocalTransitions[i];
/*  669 */       ??? = this.savingsLocalTransitions[(i + 1)];
/*  670 */       ZoneOffset localZoneOffset1 = this.wallOffsets[(i / 2)];
/*  671 */       ZoneOffset localZoneOffset2 = this.wallOffsets[(i / 2 + 1)];
/*  672 */       if (localZoneOffset2.getTotalSeconds() > localZoneOffset1.getTotalSeconds())
/*      */       {
/*  674 */         return new ZoneOffsetTransition((LocalDateTime)localObject1, localZoneOffset1, localZoneOffset2);
/*      */       }
/*      */       
/*  677 */       return new ZoneOffsetTransition((LocalDateTime)???, localZoneOffset1, localZoneOffset2);
/*      */     }
/*      */     
/*      */ 
/*  681 */     return this.wallOffsets[(i / 2 + 1)];
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private Object findOffsetInfo(LocalDateTime paramLocalDateTime, ZoneOffsetTransition paramZoneOffsetTransition)
/*      */   {
/*  693 */     LocalDateTime localLocalDateTime = paramZoneOffsetTransition.getDateTimeBefore();
/*  694 */     if (paramZoneOffsetTransition.isGap()) {
/*  695 */       if (paramLocalDateTime.isBefore(localLocalDateTime)) {
/*  696 */         return paramZoneOffsetTransition.getOffsetBefore();
/*      */       }
/*  698 */       if (paramLocalDateTime.isBefore(paramZoneOffsetTransition.getDateTimeAfter())) {
/*  699 */         return paramZoneOffsetTransition;
/*      */       }
/*  701 */       return paramZoneOffsetTransition.getOffsetAfter();
/*      */     }
/*      */     
/*  704 */     if (!paramLocalDateTime.isBefore(localLocalDateTime)) {
/*  705 */       return paramZoneOffsetTransition.getOffsetAfter();
/*      */     }
/*  707 */     if (paramLocalDateTime.isBefore(paramZoneOffsetTransition.getDateTimeAfter())) {
/*  708 */       return paramZoneOffsetTransition.getOffsetBefore();
/*      */     }
/*  710 */     return paramZoneOffsetTransition;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private ZoneOffsetTransition[] findTransitionArray(int paramInt)
/*      */   {
/*  722 */     Integer localInteger = Integer.valueOf(paramInt);
/*  723 */     ZoneOffsetTransition[] arrayOfZoneOffsetTransition = (ZoneOffsetTransition[])this.lastRulesCache.get(localInteger);
/*  724 */     if (arrayOfZoneOffsetTransition != null) {
/*  725 */       return arrayOfZoneOffsetTransition;
/*      */     }
/*  727 */     ZoneOffsetTransitionRule[] arrayOfZoneOffsetTransitionRule = this.lastRules;
/*  728 */     arrayOfZoneOffsetTransition = new ZoneOffsetTransition[arrayOfZoneOffsetTransitionRule.length];
/*  729 */     for (int i = 0; i < arrayOfZoneOffsetTransitionRule.length; i++) {
/*  730 */       arrayOfZoneOffsetTransition[i] = arrayOfZoneOffsetTransitionRule[i].createTransition(paramInt);
/*      */     }
/*  732 */     if (paramInt < 2100) {
/*  733 */       this.lastRulesCache.putIfAbsent(localInteger, arrayOfZoneOffsetTransition);
/*      */     }
/*  735 */     return arrayOfZoneOffsetTransition;
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
/*      */   public ZoneOffset getStandardOffset(Instant paramInstant)
/*      */   {
/*  751 */     if (this.savingsInstantTransitions.length == 0) {
/*  752 */       return this.standardOffsets[0];
/*      */     }
/*  754 */     long l = paramInstant.getEpochSecond();
/*  755 */     int i = Arrays.binarySearch(this.standardTransitions, l);
/*  756 */     if (i < 0)
/*      */     {
/*  758 */       i = -i - 2;
/*      */     }
/*  760 */     return this.standardOffsets[(i + 1)];
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
/*      */   public Duration getDaylightSavings(Instant paramInstant)
/*      */   {
/*  781 */     if (this.savingsInstantTransitions.length == 0) {
/*  782 */       return Duration.ZERO;
/*      */     }
/*  784 */     ZoneOffset localZoneOffset1 = getStandardOffset(paramInstant);
/*  785 */     ZoneOffset localZoneOffset2 = getOffset(paramInstant);
/*  786 */     return Duration.ofSeconds(localZoneOffset2.getTotalSeconds() - localZoneOffset1.getTotalSeconds());
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
/*      */   public boolean isDaylightSavings(Instant paramInstant)
/*      */   {
/*  804 */     return !getStandardOffset(paramInstant).equals(getOffset(paramInstant));
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
/*      */   public boolean isValidOffset(LocalDateTime paramLocalDateTime, ZoneOffset paramZoneOffset)
/*      */   {
/*  822 */     return getValidOffsets(paramLocalDateTime).contains(paramZoneOffset);
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
/*      */   public ZoneOffsetTransition nextTransition(Instant paramInstant)
/*      */   {
/*  837 */     if (this.savingsInstantTransitions.length == 0) {
/*  838 */       return null;
/*      */     }
/*  840 */     long l = paramInstant.getEpochSecond();
/*      */     
/*  842 */     if (l >= this.savingsInstantTransitions[(this.savingsInstantTransitions.length - 1)]) {
/*  843 */       if (this.lastRules.length == 0) {
/*  844 */         return null;
/*      */       }
/*      */       
/*  847 */       i = findYear(l, this.wallOffsets[(this.wallOffsets.length - 1)]);
/*  848 */       ZoneOffsetTransition[] arrayOfZoneOffsetTransition1 = findTransitionArray(i);
/*  849 */       for (ZoneOffsetTransition localZoneOffsetTransition : arrayOfZoneOffsetTransition1) {
/*  850 */         if (l < localZoneOffsetTransition.toEpochSecond()) {
/*  851 */           return localZoneOffsetTransition;
/*      */         }
/*      */       }
/*      */       
/*  855 */       if (i < 999999999) {
/*  856 */         arrayOfZoneOffsetTransition1 = findTransitionArray(i + 1);
/*  857 */         return arrayOfZoneOffsetTransition1[0];
/*      */       }
/*  859 */       return null;
/*      */     }
/*      */     
/*      */ 
/*  863 */     int i = Arrays.binarySearch(this.savingsInstantTransitions, l);
/*  864 */     if (i < 0) {
/*  865 */       i = -i - 1;
/*      */     } else {
/*  867 */       i++;
/*      */     }
/*  869 */     return new ZoneOffsetTransition(this.savingsInstantTransitions[i], this.wallOffsets[i], this.wallOffsets[(i + 1)]);
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
/*      */   public ZoneOffsetTransition previousTransition(Instant paramInstant)
/*      */   {
/*  884 */     if (this.savingsInstantTransitions.length == 0) {
/*  885 */       return null;
/*      */     }
/*  887 */     long l1 = paramInstant.getEpochSecond();
/*  888 */     if ((paramInstant.getNano() > 0) && (l1 < Long.MAX_VALUE)) {
/*  889 */       l1 += 1L;
/*      */     }
/*      */     
/*      */ 
/*  893 */     long l2 = this.savingsInstantTransitions[(this.savingsInstantTransitions.length - 1)];
/*  894 */     if ((this.lastRules.length > 0) && (l1 > l2))
/*      */     {
/*  896 */       ZoneOffset localZoneOffset = this.wallOffsets[(this.wallOffsets.length - 1)];
/*  897 */       int j = findYear(l1, localZoneOffset);
/*  898 */       ZoneOffsetTransition[] arrayOfZoneOffsetTransition = findTransitionArray(j);
/*  899 */       for (int k = arrayOfZoneOffsetTransition.length - 1; k >= 0; k--) {
/*  900 */         if (l1 > arrayOfZoneOffsetTransition[k].toEpochSecond()) {
/*  901 */           return arrayOfZoneOffsetTransition[k];
/*      */         }
/*      */       }
/*      */       
/*  905 */       k = findYear(l2, localZoneOffset);
/*  906 */       j--; if (j > k) {
/*  907 */         arrayOfZoneOffsetTransition = findTransitionArray(j);
/*  908 */         return arrayOfZoneOffsetTransition[(arrayOfZoneOffsetTransition.length - 1)];
/*      */       }
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*  914 */     int i = Arrays.binarySearch(this.savingsInstantTransitions, l1);
/*  915 */     if (i < 0) {
/*  916 */       i = -i - 1;
/*      */     }
/*  918 */     if (i <= 0) {
/*  919 */       return null;
/*      */     }
/*  921 */     return new ZoneOffsetTransition(this.savingsInstantTransitions[(i - 1)], this.wallOffsets[(i - 1)], this.wallOffsets[i]);
/*      */   }
/*      */   
/*      */   private int findYear(long paramLong, ZoneOffset paramZoneOffset)
/*      */   {
/*  926 */     long l1 = paramLong + paramZoneOffset.getTotalSeconds();
/*  927 */     long l2 = Math.floorDiv(l1, 86400L);
/*  928 */     return LocalDate.ofEpochDay(l2).getYear();
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
/*      */   public List<ZoneOffsetTransition> getTransitions()
/*      */   {
/*  944 */     ArrayList localArrayList = new ArrayList();
/*  945 */     for (int i = 0; i < this.savingsInstantTransitions.length; i++) {
/*  946 */       localArrayList.add(new ZoneOffsetTransition(this.savingsInstantTransitions[i], this.wallOffsets[i], this.wallOffsets[(i + 1)]));
/*      */     }
/*  948 */     return Collections.unmodifiableList(localArrayList);
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
/*      */   public List<ZoneOffsetTransitionRule> getTransitionRules()
/*      */   {
/*  973 */     return Collections.unmodifiableList(Arrays.asList(this.lastRules));
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
/*      */   public boolean equals(Object paramObject)
/*      */   {
/*  990 */     if (this == paramObject) {
/*  991 */       return true;
/*      */     }
/*  993 */     if ((paramObject instanceof ZoneRules)) {
/*  994 */       ZoneRules localZoneRules = (ZoneRules)paramObject;
/*      */       
/*      */ 
/*      */ 
/*      */ 
/*  999 */       return (Arrays.equals(this.standardTransitions, localZoneRules.standardTransitions)) && (Arrays.equals(this.standardOffsets, localZoneRules.standardOffsets)) && (Arrays.equals(this.savingsInstantTransitions, localZoneRules.savingsInstantTransitions)) && (Arrays.equals(this.wallOffsets, localZoneRules.wallOffsets)) && (Arrays.equals(this.lastRules, localZoneRules.lastRules));
/*      */     }
/* 1001 */     return false;
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
/*      */   public int hashCode()
/*      */   {
/* 1015 */     return Arrays.hashCode(this.standardTransitions) ^ Arrays.hashCode(this.standardOffsets) ^ Arrays.hashCode(this.savingsInstantTransitions) ^ Arrays.hashCode(this.wallOffsets) ^ Arrays.hashCode(this.lastRules);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public String toString()
/*      */   {
/* 1025 */     return "ZoneRules[currentStandardOffset=" + this.standardOffsets[(this.standardOffsets.length - 1)] + "]";
/*      */   }
/*      */ }


/* Location:              /Users/guanxiaoda/Documents/workspace/rt.jar!/java/time/zone/ZoneRules.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */