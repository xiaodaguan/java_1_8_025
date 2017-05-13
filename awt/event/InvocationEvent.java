/*     */ package java.awt.event;
/*     */ 
/*     */ import java.awt.AWTEvent;
/*     */ import java.awt.ActiveEvent;
/*     */ import sun.awt.AWTAccessor;
/*     */ import sun.awt.AWTAccessor.InvocationEventAccessor;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class InvocationEvent
/*     */   extends AWTEvent
/*     */   implements ActiveEvent
/*     */ {
/*     */   public static final int INVOCATION_FIRST = 1200;
/*     */   public static final int INVOCATION_DEFAULT = 1200;
/*     */   public static final int INVOCATION_LAST = 1200;
/*     */   protected Runnable runnable;
/*     */   protected volatile Object notifier;
/*     */   private final Runnable listener;
/*     */   
/*     */   static
/*     */   {
/*  62 */     AWTAccessor.setInvocationEventAccessor(new AWTAccessor.InvocationEventAccessor()
/*     */     {
/*     */       public void dispose(InvocationEvent paramAnonymousInvocationEvent) {
/*  65 */         paramAnonymousInvocationEvent.finishedDispatching(false);
/*     */       }
/*     */     });
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
/* 115 */   private volatile boolean dispatched = false;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   protected boolean catchExceptions;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 129 */   private Exception exception = null;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 136 */   private Throwable throwable = null;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private long when;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private static final long serialVersionUID = 436056344909459450L;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public InvocationEvent(Object paramObject, Runnable paramRunnable)
/*     */   {
/* 171 */     this(paramObject, 1200, paramRunnable, null, null, false);
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
/*     */   public InvocationEvent(Object paramObject1, Runnable paramRunnable, Object paramObject2, boolean paramBoolean)
/*     */   {
/* 210 */     this(paramObject1, 1200, paramRunnable, paramObject2, null, paramBoolean);
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
/*     */   public InvocationEvent(Object paramObject, Runnable paramRunnable1, Runnable paramRunnable2, boolean paramBoolean)
/*     */   {
/* 242 */     this(paramObject, 1200, paramRunnable1, null, paramRunnable2, paramBoolean);
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
/*     */   protected InvocationEvent(Object paramObject1, int paramInt, Runnable paramRunnable, Object paramObject2, boolean paramBoolean)
/*     */   {
/* 279 */     this(paramObject1, paramInt, paramRunnable, paramObject2, null, paramBoolean);
/*     */   }
/*     */   
/*     */   private InvocationEvent(Object paramObject1, int paramInt, Runnable paramRunnable1, Object paramObject2, Runnable paramRunnable2, boolean paramBoolean)
/*     */   {
/* 284 */     super(paramObject1, paramInt);
/* 285 */     this.runnable = paramRunnable1;
/* 286 */     this.notifier = paramObject2;
/* 287 */     this.listener = paramRunnable2;
/* 288 */     this.catchExceptions = paramBoolean;
/* 289 */     this.when = System.currentTimeMillis();
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
/*     */   public Exception getException()
/*     */   {
/* 327 */     return this.catchExceptions ? this.exception : null;
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
/*     */   public Throwable getThrowable()
/*     */   {
/* 340 */     return this.catchExceptions ? this.throwable : null;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public long getWhen()
/*     */   {
/* 350 */     return this.when;
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
/*     */   public boolean isDispatched()
/*     */   {
/* 384 */     return this.dispatched;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private void finishedDispatching(boolean paramBoolean)
/*     */   {
/* 393 */     this.dispatched = paramBoolean;
/*     */     
/* 395 */     if (this.notifier != null) {
/* 396 */       synchronized (this.notifier) {
/* 397 */         this.notifier.notifyAll();
/*     */       }
/*     */     }
/*     */     
/* 401 */     if (this.listener != null) {
/* 402 */       this.listener.run();
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public String paramString()
/*     */   {
/*     */     String str;
/*     */     
/*     */ 
/* 414 */     switch (this.id) {
/*     */     case 1200: 
/* 416 */       str = "INVOCATION_DEFAULT";
/* 417 */       break;
/*     */     default: 
/* 419 */       str = "unknown type";
/*     */     }
/* 421 */     return str + ",runnable=" + this.runnable + ",notifier=" + this.notifier + ",catchExceptions=" + this.catchExceptions + ",when=" + this.when;
/*     */   }
/*     */   
/*     */   /* Error */
/*     */   public void dispatch()
/*     */   {
/*     */     // Byte code:
/*     */     //   0: aload_0
/*     */     //   1: getfield 11	java/awt/event/InvocationEvent:catchExceptions	Z
/*     */     //   4: ifeq +39 -> 43
/*     */     //   7: aload_0
/*     */     //   8: getfield 8	java/awt/event/InvocationEvent:runnable	Ljava/lang/Runnable;
/*     */     //   11: invokeinterface 14 1 0
/*     */     //   16: goto +36 -> 52
/*     */     //   19: astore_1
/*     */     //   20: aload_1
/*     */     //   21: instanceof 16
/*     */     //   24: ifeq +11 -> 35
/*     */     //   27: aload_0
/*     */     //   28: aload_1
/*     */     //   29: checkcast 16	java/lang/Exception
/*     */     //   32: putfield 6	java/awt/event/InvocationEvent:exception	Ljava/lang/Exception;
/*     */     //   35: aload_0
/*     */     //   36: aload_1
/*     */     //   37: putfield 7	java/awt/event/InvocationEvent:throwable	Ljava/lang/Throwable;
/*     */     //   40: goto +12 -> 52
/*     */     //   43: aload_0
/*     */     //   44: getfield 8	java/awt/event/InvocationEvent:runnable	Ljava/lang/Runnable;
/*     */     //   47: invokeinterface 14 1 0
/*     */     //   52: aload_0
/*     */     //   53: iconst_1
/*     */     //   54: invokespecial 1	java/awt/event/InvocationEvent:finishedDispatching	(Z)V
/*     */     //   57: goto +11 -> 68
/*     */     //   60: astore_2
/*     */     //   61: aload_0
/*     */     //   62: iconst_1
/*     */     //   63: invokespecial 1	java/awt/event/InvocationEvent:finishedDispatching	(Z)V
/*     */     //   66: aload_2
/*     */     //   67: athrow
/*     */     //   68: return
/*     */     // Line number table:
/*     */     //   Java source line #299	-> byte code offset #0
/*     */     //   Java source line #301	-> byte code offset #7
/*     */     //   Java source line #308	-> byte code offset #16
/*     */     //   Java source line #303	-> byte code offset #19
/*     */     //   Java source line #304	-> byte code offset #20
/*     */     //   Java source line #305	-> byte code offset #27
/*     */     //   Java source line #307	-> byte code offset #35
/*     */     //   Java source line #308	-> byte code offset #40
/*     */     //   Java source line #311	-> byte code offset #43
/*     */     //   Java source line #314	-> byte code offset #52
/*     */     //   Java source line #315	-> byte code offset #57
/*     */     //   Java source line #314	-> byte code offset #60
/*     */     //   Java source line #316	-> byte code offset #68
/*     */     // Local variable table:
/*     */     //   start	length	slot	name	signature
/*     */     //   0	69	0	this	InvocationEvent
/*     */     //   19	18	1	localThrowable	Throwable
/*     */     //   60	7	2	localObject	Object
/*     */     // Exception table:
/*     */     //   from	to	target	type
/*     */     //   7	16	19	java/lang/Throwable
/*     */     //   0	52	60	finally
/*     */   }
/*     */ }


/* Location:              /Users/guanxiaoda/Documents/workspace/rt.jar!/java/awt/event/InvocationEvent.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */