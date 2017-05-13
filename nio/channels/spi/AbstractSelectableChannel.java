/*     */ package java.nio.channels.spi;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.nio.channels.ClosedChannelException;
/*     */ import java.nio.channels.IllegalBlockingModeException;
/*     */ import java.nio.channels.SelectableChannel;
/*     */ import java.nio.channels.SelectionKey;
/*     */ import java.nio.channels.Selector;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class AbstractSelectableChannel
/*     */   extends SelectableChannel
/*     */ {
/*     */   private final SelectorProvider provider;
/*  61 */   private SelectionKey[] keys = null;
/*  62 */   private int keyCount = 0;
/*     */   
/*     */ 
/*  65 */   private final Object keyLock = new Object();
/*     */   
/*     */ 
/*  68 */   private final Object regLock = new Object();
/*     */   
/*     */ 
/*  71 */   boolean blocking = true;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   protected AbstractSelectableChannel(SelectorProvider paramSelectorProvider)
/*     */   {
/*  80 */     this.provider = paramSelectorProvider;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public final SelectorProvider provider()
/*     */   {
/*  89 */     return this.provider;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   private void addKey(SelectionKey paramSelectionKey)
/*     */   {
/*  96 */     assert (Thread.holdsLock(this.keyLock));
/*  97 */     int i = 0;
/*  98 */     if ((this.keys != null) && (this.keyCount < this.keys.length))
/*     */     {
/* 100 */       for (i = 0; i < this.keys.length;)
/* 101 */         if (this.keys[i] != null)
/*     */         {
/* 100 */           i++; continue;
/*     */           
/*     */ 
/* 103 */           if (this.keys == null) {
/* 104 */             this.keys = new SelectionKey[3];
/*     */           }
/*     */           else {
/* 107 */             int j = this.keys.length * 2;
/* 108 */             SelectionKey[] arrayOfSelectionKey = new SelectionKey[j];
/* 109 */             for (i = 0; i < this.keys.length; i++)
/* 110 */               arrayOfSelectionKey[i] = this.keys[i];
/* 111 */             this.keys = arrayOfSelectionKey;
/* 112 */             i = this.keyCount;
/*     */           } } }
/* 114 */     this.keys[i] = paramSelectionKey;
/* 115 */     this.keyCount += 1;
/*     */   }
/*     */   
/*     */   private SelectionKey findKey(Selector paramSelector) {
/* 119 */     synchronized (this.keyLock) {
/* 120 */       if (this.keys == null)
/* 121 */         return null;
/* 122 */       for (int i = 0; i < this.keys.length; i++)
/* 123 */         if ((this.keys[i] != null) && (this.keys[i].selector() == paramSelector))
/* 124 */           return this.keys[i];
/* 125 */       return null;
/*     */     }
/*     */   }
/*     */   
/*     */   void removeKey(SelectionKey paramSelectionKey) {
/* 130 */     synchronized (this.keyLock) {
/* 131 */       for (int i = 0; i < this.keys.length; i++)
/* 132 */         if (this.keys[i] == paramSelectionKey) {
/* 133 */           this.keys[i] = null;
/* 134 */           this.keyCount -= 1;
/*     */         }
/* 136 */       ((AbstractSelectionKey)paramSelectionKey).invalidate();
/*     */     }
/*     */   }
/*     */   
/*     */   private boolean haveValidKeys() {
/* 141 */     synchronized (this.keyLock) {
/* 142 */       if (this.keyCount == 0)
/* 143 */         return false;
/* 144 */       for (int i = 0; i < this.keys.length; i++) {
/* 145 */         if ((this.keys[i] != null) && (this.keys[i].isValid()))
/* 146 */           return true;
/*     */       }
/* 148 */       return false;
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public final boolean isRegistered()
/*     */   {
/* 156 */     synchronized (this.keyLock) {
/* 157 */       return this.keyCount != 0;
/*     */     }
/*     */   }
/*     */   
/*     */   public final SelectionKey keyFor(Selector paramSelector) {
/* 162 */     return findKey(paramSelector);
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
/*     */   public final SelectionKey register(Selector paramSelector, int paramInt, Object paramObject)
/*     */     throws ClosedChannelException
/*     */   {
/* 195 */     synchronized (this.regLock) {
/* 196 */       if (!isOpen())
/* 197 */         throw new ClosedChannelException();
/* 198 */       if ((paramInt & (validOps() ^ 0xFFFFFFFF)) != 0)
/* 199 */         throw new IllegalArgumentException();
/* 200 */       if (this.blocking)
/* 201 */         throw new IllegalBlockingModeException();
/* 202 */       SelectionKey localSelectionKey = findKey(paramSelector);
/* 203 */       if (localSelectionKey != null) {
/* 204 */         localSelectionKey.interestOps(paramInt);
/* 205 */         localSelectionKey.attach(paramObject);
/*     */       }
/* 207 */       if (localSelectionKey == null)
/*     */       {
/* 209 */         synchronized (this.keyLock) {
/* 210 */           if (!isOpen())
/* 211 */             throw new ClosedChannelException();
/* 212 */           localSelectionKey = ((AbstractSelector)paramSelector).register(this, paramInt, paramObject);
/* 213 */           addKey(localSelectionKey);
/*     */         }
/*     */       }
/* 216 */       return localSelectionKey;
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
/*     */   protected final void implCloseChannel()
/*     */     throws IOException
/*     */   {
/* 234 */     implCloseSelectableChannel();
/* 235 */     synchronized (this.keyLock) {
/* 236 */       int i = this.keys == null ? 0 : this.keys.length;
/* 237 */       for (int j = 0; j < i; j++) {
/* 238 */         SelectionKey localSelectionKey = this.keys[j];
/* 239 */         if (localSelectionKey != null) {
/* 240 */           localSelectionKey.cancel();
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   protected abstract void implCloseSelectableChannel()
/*     */     throws IOException;
/*     */   
/*     */   /* Error */
/*     */   public final boolean isBlocking()
/*     */   {
/*     */     // Byte code:
/*     */     //   0: aload_0
/*     */     //   1: getfield 7	java/nio/channels/spi/AbstractSelectableChannel:regLock	Ljava/lang/Object;
/*     */     //   4: dup
/*     */     //   5: astore_1
/*     */     //   6: monitorenter
/*     */     //   7: aload_0
/*     */     //   8: getfield 8	java/nio/channels/spi/AbstractSelectableChannel:blocking	Z
/*     */     //   11: aload_1
/*     */     //   12: monitorexit
/*     */     //   13: ireturn
/*     */     //   14: astore_2
/*     */     //   15: aload_1
/*     */     //   16: monitorexit
/*     */     //   17: aload_2
/*     */     //   18: athrow
/*     */     // Line number table:
/*     */     //   Java source line #267	-> byte code offset #0
/*     */     //   Java source line #268	-> byte code offset #7
/*     */     //   Java source line #269	-> byte code offset #14
/*     */     // Local variable table:
/*     */     //   start	length	slot	name	signature
/*     */     //   0	19	0	this	AbstractSelectableChannel
/*     */     //   5	11	1	Ljava/lang/Object;	Object
/*     */     //   14	4	2	localObject1	Object
/*     */     // Exception table:
/*     */     //   from	to	target	type
/*     */     //   7	13	14	finally
/*     */     //   14	17	14	finally
/*     */   }
/*     */   
/*     */   public final Object blockingLock()
/*     */   {
/* 273 */     return this.regLock;
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
/*     */   public final SelectableChannel configureBlocking(boolean paramBoolean)
/*     */     throws IOException
/*     */   {
/* 287 */     synchronized (this.regLock) {
/* 288 */       if (!isOpen())
/* 289 */         throw new ClosedChannelException();
/* 290 */       if (this.blocking == paramBoolean)
/* 291 */         return this;
/* 292 */       if ((paramBoolean) && (haveValidKeys()))
/* 293 */         throw new IllegalBlockingModeException();
/* 294 */       implConfigureBlocking(paramBoolean);
/* 295 */       this.blocking = paramBoolean;
/*     */     }
/* 297 */     return this;
/*     */   }
/*     */   
/*     */   protected abstract void implConfigureBlocking(boolean paramBoolean)
/*     */     throws IOException;
/*     */ }


/* Location:              /Users/guanxiaoda/Documents/workspace/rt.jar!/java/nio/channels/spi/AbstractSelectableChannel.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */