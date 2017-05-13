/*     */ package java.util.concurrent;
/*     */ 
/*     */ import java.util.AbstractQueue;
/*     */ import java.util.Collection;
/*     */ import java.util.Iterator;
/*     */ import java.util.NoSuchElementException;
/*     */ import java.util.PriorityQueue;
/*     */ import java.util.concurrent.locks.Condition;
/*     */ import java.util.concurrent.locks.ReentrantLock;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class DelayQueue<E extends Delayed>
/*     */   extends AbstractQueue<E>
/*     */   implements BlockingQueue<E>
/*     */ {
/*  73 */   private final transient ReentrantLock lock = new ReentrantLock();
/*  74 */   private final PriorityQueue<E> q = new PriorityQueue();
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*  92 */   private Thread leader = null;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*  99 */   private final Condition available = this.lock.newCondition();
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public DelayQueue() {}
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public DelayQueue(Collection<? extends E> paramCollection)
/*     */   {
/* 115 */     addAll(paramCollection);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public boolean add(E paramE)
/*     */   {
/* 126 */     return offer(paramE);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public boolean offer(E paramE)
/*     */   {
/* 137 */     ReentrantLock localReentrantLock = this.lock;
/* 138 */     localReentrantLock.lock();
/*     */     try {
/* 140 */       this.q.offer(paramE);
/* 141 */       if (this.q.peek() == paramE) {
/* 142 */         this.leader = null;
/* 143 */         this.available.signal();
/*     */       }
/* 145 */       return true;
/*     */     } finally {
/* 147 */       localReentrantLock.unlock();
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void put(E paramE)
/*     */   {
/* 159 */     offer(paramE);
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
/*     */   public boolean offer(E paramE, long paramLong, TimeUnit paramTimeUnit)
/*     */   {
/* 173 */     return offer(paramE);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public E poll()
/*     */   {
/* 184 */     ReentrantLock localReentrantLock = this.lock;
/* 185 */     localReentrantLock.lock();
/*     */     try {
/* 187 */       Delayed localDelayed = (Delayed)this.q.peek();
/* 188 */       Object localObject1; if ((localDelayed == null) || (localDelayed.getDelay(TimeUnit.NANOSECONDS) > 0L)) {
/* 189 */         return null;
/*     */       }
/* 191 */       return (Delayed)this.q.poll();
/*     */     } finally {
/* 193 */       localReentrantLock.unlock();
/*     */     }
/*     */   }
/*     */   
/*     */   /* Error */
/*     */   public E take()
/*     */     throws java.lang.InterruptedException
/*     */   {
/*     */     // Byte code:
/*     */     //   0: aload_0
/*     */     //   1: getfield 4	java/util/concurrent/DelayQueue:lock	Ljava/util/concurrent/locks/ReentrantLock;
/*     */     //   4: astore_1
/*     */     //   5: aload_1
/*     */     //   6: invokevirtual 22	java/util/concurrent/locks/ReentrantLock:lockInterruptibly	()V
/*     */     //   9: aload_0
/*     */     //   10: getfield 7	java/util/concurrent/DelayQueue:q	Ljava/util/PriorityQueue;
/*     */     //   13: invokevirtual 15	java/util/PriorityQueue:peek	()Ljava/lang/Object;
/*     */     //   16: checkcast 18	java/util/concurrent/Delayed
/*     */     //   19: astore_2
/*     */     //   20: aload_2
/*     */     //   21: ifnonnull +15 -> 36
/*     */     //   24: aload_0
/*     */     //   25: getfield 10	java/util/concurrent/DelayQueue:available	Ljava/util/concurrent/locks/Condition;
/*     */     //   28: invokeinterface 23 1 0
/*     */     //   33: goto +143 -> 176
/*     */     //   36: aload_2
/*     */     //   37: getstatic 19	java/util/concurrent/TimeUnit:NANOSECONDS	Ljava/util/concurrent/TimeUnit;
/*     */     //   40: invokeinterface 20 2 0
/*     */     //   45: lstore_3
/*     */     //   46: lload_3
/*     */     //   47: lconst_0
/*     */     //   48: lcmp
/*     */     //   49: ifgt +48 -> 97
/*     */     //   52: aload_0
/*     */     //   53: getfield 7	java/util/concurrent/DelayQueue:q	Ljava/util/PriorityQueue;
/*     */     //   56: invokevirtual 21	java/util/PriorityQueue:poll	()Ljava/lang/Object;
/*     */     //   59: checkcast 18	java/util/concurrent/Delayed
/*     */     //   62: astore 5
/*     */     //   64: aload_0
/*     */     //   65: getfield 8	java/util/concurrent/DelayQueue:leader	Ljava/lang/Thread;
/*     */     //   68: ifnonnull +22 -> 90
/*     */     //   71: aload_0
/*     */     //   72: getfield 7	java/util/concurrent/DelayQueue:q	Ljava/util/PriorityQueue;
/*     */     //   75: invokevirtual 15	java/util/PriorityQueue:peek	()Ljava/lang/Object;
/*     */     //   78: ifnull +12 -> 90
/*     */     //   81: aload_0
/*     */     //   82: getfield 10	java/util/concurrent/DelayQueue:available	Ljava/util/concurrent/locks/Condition;
/*     */     //   85: invokeinterface 16 1 0
/*     */     //   90: aload_1
/*     */     //   91: invokevirtual 17	java/util/concurrent/locks/ReentrantLock:unlock	()V
/*     */     //   94: aload 5
/*     */     //   96: areturn
/*     */     //   97: aconst_null
/*     */     //   98: astore_2
/*     */     //   99: aload_0
/*     */     //   100: getfield 8	java/util/concurrent/DelayQueue:leader	Ljava/lang/Thread;
/*     */     //   103: ifnull +15 -> 118
/*     */     //   106: aload_0
/*     */     //   107: getfield 10	java/util/concurrent/DelayQueue:available	Ljava/util/concurrent/locks/Condition;
/*     */     //   110: invokeinterface 23 1 0
/*     */     //   115: goto +61 -> 176
/*     */     //   118: invokestatic 24	java/lang/Thread:currentThread	()Ljava/lang/Thread;
/*     */     //   121: astore 5
/*     */     //   123: aload_0
/*     */     //   124: aload 5
/*     */     //   126: putfield 8	java/util/concurrent/DelayQueue:leader	Ljava/lang/Thread;
/*     */     //   129: aload_0
/*     */     //   130: getfield 10	java/util/concurrent/DelayQueue:available	Ljava/util/concurrent/locks/Condition;
/*     */     //   133: lload_3
/*     */     //   134: invokeinterface 25 3 0
/*     */     //   139: pop2
/*     */     //   140: aload_0
/*     */     //   141: getfield 8	java/util/concurrent/DelayQueue:leader	Ljava/lang/Thread;
/*     */     //   144: aload 5
/*     */     //   146: if_acmpne +30 -> 176
/*     */     //   149: aload_0
/*     */     //   150: aconst_null
/*     */     //   151: putfield 8	java/util/concurrent/DelayQueue:leader	Ljava/lang/Thread;
/*     */     //   154: goto +22 -> 176
/*     */     //   157: astore 6
/*     */     //   159: aload_0
/*     */     //   160: getfield 8	java/util/concurrent/DelayQueue:leader	Ljava/lang/Thread;
/*     */     //   163: aload 5
/*     */     //   165: if_acmpne +8 -> 173
/*     */     //   168: aload_0
/*     */     //   169: aconst_null
/*     */     //   170: putfield 8	java/util/concurrent/DelayQueue:leader	Ljava/lang/Thread;
/*     */     //   173: aload 6
/*     */     //   175: athrow
/*     */     //   176: goto -167 -> 9
/*     */     //   179: astore 7
/*     */     //   181: aload_0
/*     */     //   182: getfield 8	java/util/concurrent/DelayQueue:leader	Ljava/lang/Thread;
/*     */     //   185: ifnonnull +22 -> 207
/*     */     //   188: aload_0
/*     */     //   189: getfield 7	java/util/concurrent/DelayQueue:q	Ljava/util/PriorityQueue;
/*     */     //   192: invokevirtual 15	java/util/PriorityQueue:peek	()Ljava/lang/Object;
/*     */     //   195: ifnull +12 -> 207
/*     */     //   198: aload_0
/*     */     //   199: getfield 10	java/util/concurrent/DelayQueue:available	Ljava/util/concurrent/locks/Condition;
/*     */     //   202: invokeinterface 16 1 0
/*     */     //   207: aload_1
/*     */     //   208: invokevirtual 17	java/util/concurrent/locks/ReentrantLock:unlock	()V
/*     */     //   211: aload 7
/*     */     //   213: athrow
/*     */     // Line number table:
/*     */     //   Java source line #205	-> byte code offset #0
/*     */     //   Java source line #206	-> byte code offset #5
/*     */     //   Java source line #209	-> byte code offset #9
/*     */     //   Java source line #210	-> byte code offset #20
/*     */     //   Java source line #211	-> byte code offset #24
/*     */     //   Java source line #213	-> byte code offset #36
/*     */     //   Java source line #214	-> byte code offset #46
/*     */     //   Java source line #215	-> byte code offset #52
/*     */     //   Java source line #232	-> byte code offset #64
/*     */     //   Java source line #233	-> byte code offset #81
/*     */     //   Java source line #234	-> byte code offset #90
/*     */     //   Java source line #216	-> byte code offset #97
/*     */     //   Java source line #217	-> byte code offset #99
/*     */     //   Java source line #218	-> byte code offset #106
/*     */     //   Java source line #220	-> byte code offset #118
/*     */     //   Java source line #221	-> byte code offset #123
/*     */     //   Java source line #223	-> byte code offset #129
/*     */     //   Java source line #225	-> byte code offset #140
/*     */     //   Java source line #226	-> byte code offset #149
/*     */     //   Java source line #225	-> byte code offset #157
/*     */     //   Java source line #226	-> byte code offset #168
/*     */     //   Java source line #230	-> byte code offset #176
/*     */     //   Java source line #232	-> byte code offset #179
/*     */     //   Java source line #233	-> byte code offset #198
/*     */     //   Java source line #234	-> byte code offset #207
/*     */     // Local variable table:
/*     */     //   start	length	slot	name	signature
/*     */     //   0	214	0	this	DelayQueue
/*     */     //   4	204	1	localReentrantLock	ReentrantLock
/*     */     //   19	80	2	localDelayed	Delayed
/*     */     //   45	89	3	l	long
/*     */     //   62	102	5	localObject1	Object
/*     */     //   157	17	6	localObject2	Object
/*     */     //   179	33	7	localObject3	Object
/*     */     // Exception table:
/*     */     //   from	to	target	type
/*     */     //   129	140	157	finally
/*     */     //   157	159	157	finally
/*     */     //   9	64	179	finally
/*     */     //   97	181	179	finally
/*     */   }
/*     */   
/*     */   /* Error */
/*     */   public E poll(long paramLong, TimeUnit paramTimeUnit)
/*     */     throws java.lang.InterruptedException
/*     */   {
/*     */     // Byte code:
/*     */     //   0: aload_3
/*     */     //   1: lload_1
/*     */     //   2: invokevirtual 26	java/util/concurrent/TimeUnit:toNanos	(J)J
/*     */     //   5: lstore 4
/*     */     //   7: aload_0
/*     */     //   8: getfield 4	java/util/concurrent/DelayQueue:lock	Ljava/util/concurrent/locks/ReentrantLock;
/*     */     //   11: astore 6
/*     */     //   13: aload 6
/*     */     //   15: invokevirtual 22	java/util/concurrent/locks/ReentrantLock:lockInterruptibly	()V
/*     */     //   18: aload_0
/*     */     //   19: getfield 7	java/util/concurrent/DelayQueue:q	Ljava/util/PriorityQueue;
/*     */     //   22: invokevirtual 15	java/util/PriorityQueue:peek	()Ljava/lang/Object;
/*     */     //   25: checkcast 18	java/util/concurrent/Delayed
/*     */     //   28: astore 7
/*     */     //   30: aload 7
/*     */     //   32: ifnonnull +63 -> 95
/*     */     //   35: lload 4
/*     */     //   37: lconst_0
/*     */     //   38: lcmp
/*     */     //   39: ifgt +40 -> 79
/*     */     //   42: aconst_null
/*     */     //   43: astore 8
/*     */     //   45: aload_0
/*     */     //   46: getfield 8	java/util/concurrent/DelayQueue:leader	Ljava/lang/Thread;
/*     */     //   49: ifnonnull +22 -> 71
/*     */     //   52: aload_0
/*     */     //   53: getfield 7	java/util/concurrent/DelayQueue:q	Ljava/util/PriorityQueue;
/*     */     //   56: invokevirtual 15	java/util/PriorityQueue:peek	()Ljava/lang/Object;
/*     */     //   59: ifnull +12 -> 71
/*     */     //   62: aload_0
/*     */     //   63: getfield 10	java/util/concurrent/DelayQueue:available	Ljava/util/concurrent/locks/Condition;
/*     */     //   66: invokeinterface 16 1 0
/*     */     //   71: aload 6
/*     */     //   73: invokevirtual 17	java/util/concurrent/locks/ReentrantLock:unlock	()V
/*     */     //   76: aload 8
/*     */     //   78: areturn
/*     */     //   79: aload_0
/*     */     //   80: getfield 10	java/util/concurrent/DelayQueue:available	Ljava/util/concurrent/locks/Condition;
/*     */     //   83: lload 4
/*     */     //   85: invokeinterface 25 3 0
/*     */     //   90: lstore 4
/*     */     //   92: goto +216 -> 308
/*     */     //   95: aload 7
/*     */     //   97: getstatic 19	java/util/concurrent/TimeUnit:NANOSECONDS	Ljava/util/concurrent/TimeUnit;
/*     */     //   100: invokeinterface 20 2 0
/*     */     //   105: lstore 8
/*     */     //   107: lload 8
/*     */     //   109: lconst_0
/*     */     //   110: lcmp
/*     */     //   111: ifgt +49 -> 160
/*     */     //   114: aload_0
/*     */     //   115: getfield 7	java/util/concurrent/DelayQueue:q	Ljava/util/PriorityQueue;
/*     */     //   118: invokevirtual 21	java/util/PriorityQueue:poll	()Ljava/lang/Object;
/*     */     //   121: checkcast 18	java/util/concurrent/Delayed
/*     */     //   124: astore 10
/*     */     //   126: aload_0
/*     */     //   127: getfield 8	java/util/concurrent/DelayQueue:leader	Ljava/lang/Thread;
/*     */     //   130: ifnonnull +22 -> 152
/*     */     //   133: aload_0
/*     */     //   134: getfield 7	java/util/concurrent/DelayQueue:q	Ljava/util/PriorityQueue;
/*     */     //   137: invokevirtual 15	java/util/PriorityQueue:peek	()Ljava/lang/Object;
/*     */     //   140: ifnull +12 -> 152
/*     */     //   143: aload_0
/*     */     //   144: getfield 10	java/util/concurrent/DelayQueue:available	Ljava/util/concurrent/locks/Condition;
/*     */     //   147: invokeinterface 16 1 0
/*     */     //   152: aload 6
/*     */     //   154: invokevirtual 17	java/util/concurrent/locks/ReentrantLock:unlock	()V
/*     */     //   157: aload 10
/*     */     //   159: areturn
/*     */     //   160: lload 4
/*     */     //   162: lconst_0
/*     */     //   163: lcmp
/*     */     //   164: ifgt +40 -> 204
/*     */     //   167: aconst_null
/*     */     //   168: astore 10
/*     */     //   170: aload_0
/*     */     //   171: getfield 8	java/util/concurrent/DelayQueue:leader	Ljava/lang/Thread;
/*     */     //   174: ifnonnull +22 -> 196
/*     */     //   177: aload_0
/*     */     //   178: getfield 7	java/util/concurrent/DelayQueue:q	Ljava/util/PriorityQueue;
/*     */     //   181: invokevirtual 15	java/util/PriorityQueue:peek	()Ljava/lang/Object;
/*     */     //   184: ifnull +12 -> 196
/*     */     //   187: aload_0
/*     */     //   188: getfield 10	java/util/concurrent/DelayQueue:available	Ljava/util/concurrent/locks/Condition;
/*     */     //   191: invokeinterface 16 1 0
/*     */     //   196: aload 6
/*     */     //   198: invokevirtual 17	java/util/concurrent/locks/ReentrantLock:unlock	()V
/*     */     //   201: aload 10
/*     */     //   203: areturn
/*     */     //   204: aconst_null
/*     */     //   205: astore 7
/*     */     //   207: lload 4
/*     */     //   209: lload 8
/*     */     //   211: lcmp
/*     */     //   212: iflt +10 -> 222
/*     */     //   215: aload_0
/*     */     //   216: getfield 8	java/util/concurrent/DelayQueue:leader	Ljava/lang/Thread;
/*     */     //   219: ifnull +19 -> 238
/*     */     //   222: aload_0
/*     */     //   223: getfield 10	java/util/concurrent/DelayQueue:available	Ljava/util/concurrent/locks/Condition;
/*     */     //   226: lload 4
/*     */     //   228: invokeinterface 25 3 0
/*     */     //   233: lstore 4
/*     */     //   235: goto +73 -> 308
/*     */     //   238: invokestatic 24	java/lang/Thread:currentThread	()Ljava/lang/Thread;
/*     */     //   241: astore 10
/*     */     //   243: aload_0
/*     */     //   244: aload 10
/*     */     //   246: putfield 8	java/util/concurrent/DelayQueue:leader	Ljava/lang/Thread;
/*     */     //   249: aload_0
/*     */     //   250: getfield 10	java/util/concurrent/DelayQueue:available	Ljava/util/concurrent/locks/Condition;
/*     */     //   253: lload 8
/*     */     //   255: invokeinterface 25 3 0
/*     */     //   260: lstore 11
/*     */     //   262: lload 4
/*     */     //   264: lload 8
/*     */     //   266: lload 11
/*     */     //   268: lsub
/*     */     //   269: lsub
/*     */     //   270: lstore 4
/*     */     //   272: aload_0
/*     */     //   273: getfield 8	java/util/concurrent/DelayQueue:leader	Ljava/lang/Thread;
/*     */     //   276: aload 10
/*     */     //   278: if_acmpne +30 -> 308
/*     */     //   281: aload_0
/*     */     //   282: aconst_null
/*     */     //   283: putfield 8	java/util/concurrent/DelayQueue:leader	Ljava/lang/Thread;
/*     */     //   286: goto +22 -> 308
/*     */     //   289: astore 13
/*     */     //   291: aload_0
/*     */     //   292: getfield 8	java/util/concurrent/DelayQueue:leader	Ljava/lang/Thread;
/*     */     //   295: aload 10
/*     */     //   297: if_acmpne +8 -> 305
/*     */     //   300: aload_0
/*     */     //   301: aconst_null
/*     */     //   302: putfield 8	java/util/concurrent/DelayQueue:leader	Ljava/lang/Thread;
/*     */     //   305: aload 13
/*     */     //   307: athrow
/*     */     //   308: goto -290 -> 18
/*     */     //   311: astore 14
/*     */     //   313: aload_0
/*     */     //   314: getfield 8	java/util/concurrent/DelayQueue:leader	Ljava/lang/Thread;
/*     */     //   317: ifnonnull +22 -> 339
/*     */     //   320: aload_0
/*     */     //   321: getfield 7	java/util/concurrent/DelayQueue:q	Ljava/util/PriorityQueue;
/*     */     //   324: invokevirtual 15	java/util/PriorityQueue:peek	()Ljava/lang/Object;
/*     */     //   327: ifnull +12 -> 339
/*     */     //   330: aload_0
/*     */     //   331: getfield 10	java/util/concurrent/DelayQueue:available	Ljava/util/concurrent/locks/Condition;
/*     */     //   334: invokeinterface 16 1 0
/*     */     //   339: aload 6
/*     */     //   341: invokevirtual 17	java/util/concurrent/locks/ReentrantLock:unlock	()V
/*     */     //   344: aload 14
/*     */     //   346: athrow
/*     */     // Line number table:
/*     */     //   Java source line #249	-> byte code offset #0
/*     */     //   Java source line #250	-> byte code offset #7
/*     */     //   Java source line #251	-> byte code offset #13
/*     */     //   Java source line #254	-> byte code offset #18
/*     */     //   Java source line #255	-> byte code offset #30
/*     */     //   Java source line #256	-> byte code offset #35
/*     */     //   Java source line #257	-> byte code offset #42
/*     */     //   Java source line #283	-> byte code offset #45
/*     */     //   Java source line #284	-> byte code offset #62
/*     */     //   Java source line #285	-> byte code offset #71
/*     */     //   Java source line #259	-> byte code offset #79
/*     */     //   Java source line #261	-> byte code offset #95
/*     */     //   Java source line #262	-> byte code offset #107
/*     */     //   Java source line #263	-> byte code offset #114
/*     */     //   Java source line #283	-> byte code offset #126
/*     */     //   Java source line #284	-> byte code offset #143
/*     */     //   Java source line #285	-> byte code offset #152
/*     */     //   Java source line #264	-> byte code offset #160
/*     */     //   Java source line #265	-> byte code offset #167
/*     */     //   Java source line #283	-> byte code offset #170
/*     */     //   Java source line #284	-> byte code offset #187
/*     */     //   Java source line #285	-> byte code offset #196
/*     */     //   Java source line #266	-> byte code offset #204
/*     */     //   Java source line #267	-> byte code offset #207
/*     */     //   Java source line #268	-> byte code offset #222
/*     */     //   Java source line #270	-> byte code offset #238
/*     */     //   Java source line #271	-> byte code offset #243
/*     */     //   Java source line #273	-> byte code offset #249
/*     */     //   Java source line #274	-> byte code offset #262
/*     */     //   Java source line #276	-> byte code offset #272
/*     */     //   Java source line #277	-> byte code offset #281
/*     */     //   Java source line #276	-> byte code offset #289
/*     */     //   Java source line #277	-> byte code offset #300
/*     */     //   Java source line #281	-> byte code offset #308
/*     */     //   Java source line #283	-> byte code offset #311
/*     */     //   Java source line #284	-> byte code offset #330
/*     */     //   Java source line #285	-> byte code offset #339
/*     */     // Local variable table:
/*     */     //   start	length	slot	name	signature
/*     */     //   0	347	0	this	DelayQueue
/*     */     //   0	347	1	paramLong	long
/*     */     //   0	347	3	paramTimeUnit	TimeUnit
/*     */     //   5	266	4	l1	long
/*     */     //   11	329	6	localReentrantLock	ReentrantLock
/*     */     //   28	178	7	localDelayed	Delayed
/*     */     //   43	34	8	?	E
/*     */     //   105	160	8	l2	long
/*     */     //   124	172	10	localObject1	Object
/*     */     //   260	7	11	l3	long
/*     */     //   289	17	13	localObject2	Object
/*     */     //   311	34	14	localObject3	Object
/*     */     // Exception table:
/*     */     //   from	to	target	type
/*     */     //   249	272	289	finally
/*     */     //   289	291	289	finally
/*     */     //   18	45	311	finally
/*     */     //   79	126	311	finally
/*     */     //   160	170	311	finally
/*     */     //   204	313	311	finally
/*     */   }
/*     */   
/*     */   public E peek()
/*     */   {
/* 300 */     ReentrantLock localReentrantLock = this.lock;
/* 301 */     localReentrantLock.lock();
/*     */     try {
/* 303 */       return (Delayed)this.q.peek();
/*     */     } finally {
/* 305 */       localReentrantLock.unlock();
/*     */     }
/*     */   }
/*     */   
/*     */   public int size() {
/* 310 */     ReentrantLock localReentrantLock = this.lock;
/* 311 */     localReentrantLock.lock();
/*     */     try {
/* 313 */       return this.q.size();
/*     */     } finally {
/* 315 */       localReentrantLock.unlock();
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private E peekExpired()
/*     */   {
/* 325 */     Delayed localDelayed = (Delayed)this.q.peek();
/* 326 */     return (localDelayed == null) || (localDelayed.getDelay(TimeUnit.NANOSECONDS) > 0L) ? null : localDelayed;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public int drainTo(Collection<? super E> paramCollection)
/*     */   {
/* 337 */     if (paramCollection == null)
/* 338 */       throw new NullPointerException();
/* 339 */     if (paramCollection == this)
/* 340 */       throw new IllegalArgumentException();
/* 341 */     ReentrantLock localReentrantLock = this.lock;
/* 342 */     localReentrantLock.lock();
/*     */     try {
/* 344 */       Delayed localDelayed1 = 0;
/* 345 */       Delayed localDelayed2; while ((localDelayed2 = peekExpired()) != null) {
/* 346 */         paramCollection.add(localDelayed2);
/* 347 */         this.q.poll();
/* 348 */         localDelayed1++;
/*     */       }
/* 350 */       return localDelayed1;
/*     */     } finally {
/* 352 */       localReentrantLock.unlock();
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public int drainTo(Collection<? super E> paramCollection, int paramInt)
/*     */   {
/* 363 */     if (paramCollection == null)
/* 364 */       throw new NullPointerException();
/* 365 */     if (paramCollection == this)
/* 366 */       throw new IllegalArgumentException();
/* 367 */     if (paramInt <= 0)
/* 368 */       return 0;
/* 369 */     ReentrantLock localReentrantLock = this.lock;
/* 370 */     localReentrantLock.lock();
/*     */     try {
/* 372 */       int i = 0;
/* 373 */       Delayed localDelayed; while ((i < paramInt) && ((localDelayed = peekExpired()) != null)) {
/* 374 */         paramCollection.add(localDelayed);
/* 375 */         this.q.poll();
/* 376 */         i++;
/*     */       }
/* 378 */       return i;
/*     */     } finally {
/* 380 */       localReentrantLock.unlock();
/*     */     }
/*     */   }
/*     */   
/*     */   /* Error */
/*     */   public void clear()
/*     */   {
/*     */     // Byte code:
/*     */     //   0: aload_0
/*     */     //   1: getfield 4	java/util/concurrent/DelayQueue:lock	Ljava/util/concurrent/locks/ReentrantLock;
/*     */     //   4: astore_1
/*     */     //   5: aload_1
/*     */     //   6: invokevirtual 13	java/util/concurrent/locks/ReentrantLock:lock	()V
/*     */     //   9: aload_0
/*     */     //   10: getfield 7	java/util/concurrent/DelayQueue:q	Ljava/util/PriorityQueue;
/*     */     //   13: invokevirtual 34	java/util/PriorityQueue:clear	()V
/*     */     //   16: aload_1
/*     */     //   17: invokevirtual 17	java/util/concurrent/locks/ReentrantLock:unlock	()V
/*     */     //   20: goto +10 -> 30
/*     */     //   23: astore_2
/*     */     //   24: aload_1
/*     */     //   25: invokevirtual 17	java/util/concurrent/locks/ReentrantLock:unlock	()V
/*     */     //   28: aload_2
/*     */     //   29: athrow
/*     */     //   30: return
/*     */     // Line number table:
/*     */     //   Java source line #391	-> byte code offset #0
/*     */     //   Java source line #392	-> byte code offset #5
/*     */     //   Java source line #394	-> byte code offset #9
/*     */     //   Java source line #396	-> byte code offset #16
/*     */     //   Java source line #397	-> byte code offset #20
/*     */     //   Java source line #396	-> byte code offset #23
/*     */     //   Java source line #398	-> byte code offset #30
/*     */     // Local variable table:
/*     */     //   start	length	slot	name	signature
/*     */     //   0	31	0	this	DelayQueue
/*     */     //   4	21	1	localReentrantLock	ReentrantLock
/*     */     //   23	6	2	localObject	Object
/*     */     // Exception table:
/*     */     //   from	to	target	type
/*     */     //   9	16	23	finally
/*     */   }
/*     */   
/*     */   public int remainingCapacity()
/*     */   {
/* 407 */     return Integer.MAX_VALUE;
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
/*     */   public Object[] toArray()
/*     */   {
/* 424 */     ReentrantLock localReentrantLock = this.lock;
/* 425 */     localReentrantLock.lock();
/*     */     try {
/* 427 */       return this.q.toArray();
/*     */     } finally {
/* 429 */       localReentrantLock.unlock();
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public <T> T[] toArray(T[] paramArrayOfT)
/*     */   {
/* 469 */     ReentrantLock localReentrantLock = this.lock;
/* 470 */     localReentrantLock.lock();
/*     */     try {
/* 472 */       return this.q.toArray(paramArrayOfT);
/*     */     } finally {
/* 474 */       localReentrantLock.unlock();
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public boolean remove(Object paramObject)
/*     */   {
/* 483 */     ReentrantLock localReentrantLock = this.lock;
/* 484 */     localReentrantLock.lock();
/*     */     try {
/* 486 */       return this.q.remove(paramObject);
/*     */     } finally {
/* 488 */       localReentrantLock.unlock();
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   void removeEQ(Object paramObject)
/*     */   {
/* 496 */     ReentrantLock localReentrantLock = this.lock;
/* 497 */     localReentrantLock.lock();
/*     */     try {
/* 499 */       for (localIterator = this.q.iterator(); localIterator.hasNext();) {
/* 500 */         if (paramObject == localIterator.next()) {
/* 501 */           localIterator.remove();
/*     */         }
/*     */       }
/*     */     } finally {
/*     */       Iterator localIterator;
/* 506 */       localReentrantLock.unlock();
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
/*     */   public Iterator<E> iterator()
/*     */   {
/* 521 */     return new Itr(toArray());
/*     */   }
/*     */   
/*     */   private class Itr
/*     */     implements Iterator<E>
/*     */   {
/*     */     final Object[] array;
/*     */     int cursor;
/*     */     int lastRet;
/*     */     
/*     */     Itr(Object[] paramArrayOfObject)
/*     */     {
/* 533 */       this.lastRet = -1;
/* 534 */       this.array = paramArrayOfObject;
/*     */     }
/*     */     
/*     */     public boolean hasNext() {
/* 538 */       return this.cursor < this.array.length;
/*     */     }
/*     */     
/*     */     public E next()
/*     */     {
/* 543 */       if (this.cursor >= this.array.length)
/* 544 */         throw new NoSuchElementException();
/* 545 */       this.lastRet = this.cursor;
/* 546 */       return (Delayed)this.array[(this.cursor++)];
/*     */     }
/*     */     
/*     */     public void remove() {
/* 550 */       if (this.lastRet < 0)
/* 551 */         throw new IllegalStateException();
/* 552 */       DelayQueue.this.removeEQ(this.array[this.lastRet]);
/* 553 */       this.lastRet = -1;
/*     */     }
/*     */   }
/*     */ }


/* Location:              /Users/guanxiaoda/Documents/workspace/rt.jar!/java/util/concurrent/DelayQueue.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */