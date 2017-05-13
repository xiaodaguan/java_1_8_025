/*     */ package java.sql;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.io.InvalidObjectException;
/*     */ import java.io.ObjectInputStream;
/*     */ import java.io.ObjectInputStream.GetField;
/*     */ import java.io.ObjectOutputStream;
/*     */ import java.io.ObjectOutputStream.PutField;
/*     */ import java.util.Arrays;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class BatchUpdateException
/*     */   extends SQLException
/*     */ {
/*     */   private int[] updateCounts;
/*     */   private long[] longUpdateCounts;
/*     */   private static final long serialVersionUID = 5977529877145521757L;
/*     */   
/*     */   public BatchUpdateException(String paramString1, String paramString2, int paramInt, int[] paramArrayOfInt)
/*     */   {
/* 101 */     super(paramString1, paramString2, paramInt);
/* 102 */     this.updateCounts = (paramArrayOfInt == null ? null : Arrays.copyOf(paramArrayOfInt, paramArrayOfInt.length));
/* 103 */     this.longUpdateCounts = (paramArrayOfInt == null ? null : copyUpdateCount(paramArrayOfInt));
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
/*     */   public BatchUpdateException(String paramString1, String paramString2, int[] paramArrayOfInt)
/*     */   {
/* 136 */     this(paramString1, paramString2, 0, paramArrayOfInt);
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
/*     */   public BatchUpdateException(String paramString, int[] paramArrayOfInt)
/*     */   {
/* 167 */     this(paramString, null, 0, paramArrayOfInt);
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
/*     */   public BatchUpdateException(int[] paramArrayOfInt)
/*     */   {
/* 196 */     this(null, null, 0, paramArrayOfInt);
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
/*     */   public BatchUpdateException()
/*     */   {
/* 213 */     this(null, null, 0, null);
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
/*     */   public BatchUpdateException(Throwable paramThrowable)
/*     */   {
/* 233 */     this(paramThrowable == null ? null : paramThrowable.toString(), null, 0, (int[])null, paramThrowable);
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
/*     */   public BatchUpdateException(int[] paramArrayOfInt, Throwable paramThrowable)
/*     */   {
/* 266 */     this(paramThrowable == null ? null : paramThrowable.toString(), null, 0, paramArrayOfInt, paramThrowable);
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
/*     */   public BatchUpdateException(String paramString, int[] paramArrayOfInt, Throwable paramThrowable)
/*     */   {
/* 297 */     this(paramString, null, 0, paramArrayOfInt, paramThrowable);
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
/*     */   public BatchUpdateException(String paramString1, String paramString2, int[] paramArrayOfInt, Throwable paramThrowable)
/*     */   {
/* 331 */     this(paramString1, paramString2, 0, paramArrayOfInt, paramThrowable);
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
/*     */   public BatchUpdateException(String paramString1, String paramString2, int paramInt, int[] paramArrayOfInt, Throwable paramThrowable)
/*     */   {
/* 366 */     super(paramString1, paramString2, paramInt, paramThrowable);
/* 367 */     this.updateCounts = (paramArrayOfInt == null ? null : Arrays.copyOf(paramArrayOfInt, paramArrayOfInt.length));
/* 368 */     this.longUpdateCounts = (paramArrayOfInt == null ? null : copyUpdateCount(paramArrayOfInt));
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
/*     */   public int[] getUpdateCounts()
/*     */   {
/* 403 */     return this.updateCounts == null ? null : Arrays.copyOf(this.updateCounts, this.updateCounts.length);
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
/*     */   public BatchUpdateException(String paramString1, String paramString2, int paramInt, long[] paramArrayOfLong, Throwable paramThrowable)
/*     */   {
/* 433 */     super(paramString1, paramString2, paramInt, paramThrowable);
/* 434 */     this.longUpdateCounts = (paramArrayOfLong == null ? null : Arrays.copyOf(paramArrayOfLong, paramArrayOfLong.length));
/* 435 */     this.updateCounts = (this.longUpdateCounts == null ? null : copyUpdateCount(this.longUpdateCounts));
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
/*     */   public long[] getLargeUpdateCounts()
/*     */   {
/* 467 */     return this.longUpdateCounts == null ? null : Arrays.copyOf(this.longUpdateCounts, this.longUpdateCounts.length);
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
/*     */   private static long[] copyUpdateCount(int[] paramArrayOfInt)
/*     */   {
/* 510 */     long[] arrayOfLong = new long[paramArrayOfInt.length];
/* 511 */     for (int i = 0; i < paramArrayOfInt.length; i++) {
/* 512 */       arrayOfLong[i] = paramArrayOfInt[i];
/*     */     }
/* 514 */     return arrayOfLong;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private static int[] copyUpdateCount(long[] paramArrayOfLong)
/*     */   {
/* 523 */     int[] arrayOfInt = new int[paramArrayOfLong.length];
/* 524 */     for (int i = 0; i < paramArrayOfLong.length; i++) {
/* 525 */       arrayOfInt[i] = ((int)paramArrayOfLong[i]);
/*     */     }
/* 527 */     return arrayOfInt;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   private void readObject(ObjectInputStream paramObjectInputStream)
/*     */     throws IOException, ClassNotFoundException
/*     */   {
/* 536 */     ObjectInputStream.GetField localGetField = paramObjectInputStream.readFields();
/* 537 */     int[] arrayOfInt = (int[])localGetField.get("updateCounts", null);
/* 538 */     long[] arrayOfLong = (long[])localGetField.get("longUpdateCounts", null);
/* 539 */     if ((arrayOfInt != null) && (arrayOfLong != null) && (arrayOfInt.length != arrayOfLong.length))
/* 540 */       throw new InvalidObjectException("update counts are not the expected size");
/* 541 */     if (arrayOfInt != null)
/* 542 */       this.updateCounts = ((int[])arrayOfInt.clone());
/* 543 */     if (arrayOfLong != null)
/* 544 */       this.longUpdateCounts = ((long[])arrayOfLong.clone());
/* 545 */     if ((this.updateCounts == null) && (this.longUpdateCounts != null))
/* 546 */       this.updateCounts = copyUpdateCount(this.longUpdateCounts);
/* 547 */     if ((this.longUpdateCounts == null) && (this.updateCounts != null)) {
/* 548 */       this.longUpdateCounts = copyUpdateCount(this.updateCounts);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private void writeObject(ObjectOutputStream paramObjectOutputStream)
/*     */     throws IOException, ClassNotFoundException
/*     */   {
/* 559 */     ObjectOutputStream.PutField localPutField = paramObjectOutputStream.putFields();
/* 560 */     localPutField.put("updateCounts", this.updateCounts);
/* 561 */     localPutField.put("longUpdateCounts", this.longUpdateCounts);
/* 562 */     paramObjectOutputStream.writeFields();
/*     */   }
/*     */ }


/* Location:              /Users/guanxiaoda/Documents/workspace/rt.jar!/java/sql/BatchUpdateException.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */