/*    */ package java.awt;
/*    */ 
/*    */ import sun.awt.AppContext;
/*    */ import sun.awt.SunToolkit;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ class SentEvent
/*    */   extends AWTEvent
/*    */   implements ActiveEvent
/*    */ {
/*    */   private static final long serialVersionUID = -383615247028828931L;
/*    */   static final int ID = 1007;
/*    */   boolean dispatched;
/*    */   private AWTEvent nested;
/*    */   private AppContext toNotify;
/*    */   
/*    */   SentEvent()
/*    */   {
/* 53 */     this(null);
/*    */   }
/*    */   
/* 56 */   SentEvent(AWTEvent paramAWTEvent) { this(paramAWTEvent, null); }
/*    */   
/*    */   SentEvent(AWTEvent paramAWTEvent, AppContext paramAppContext) {
/* 59 */     super(paramAWTEvent != null ? paramAWTEvent
/* 60 */       .getSource() : 
/* 61 */       Toolkit.getDefaultToolkit(), 1007);
/*    */     
/* 63 */     this.nested = paramAWTEvent;
/* 64 */     this.toNotify = paramAppContext;
/*    */   }
/*    */   
/*    */   /* Error */
/*    */   public void dispatch()
/*    */   {
/*    */     // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: getfield 7	java/awt/SentEvent:nested	Ljava/awt/AWTEvent;
/*    */     //   4: ifnull +13 -> 17
/*    */     //   7: invokestatic 9	java/awt/Toolkit:getEventQueue	()Ljava/awt/EventQueue;
/*    */     //   10: aload_0
/*    */     //   11: getfield 7	java/awt/SentEvent:nested	Ljava/awt/AWTEvent;
/*    */     //   14: invokevirtual 10	java/awt/EventQueue:dispatchEvent	(Ljava/awt/AWTEvent;)V
/*    */     //   17: aload_0
/*    */     //   18: iconst_1
/*    */     //   19: putfield 11	java/awt/SentEvent:dispatched	Z
/*    */     //   22: aload_0
/*    */     //   23: getfield 8	java/awt/SentEvent:toNotify	Lsun/awt/AppContext;
/*    */     //   26: ifnull +17 -> 43
/*    */     //   29: aload_0
/*    */     //   30: getfield 8	java/awt/SentEvent:toNotify	Lsun/awt/AppContext;
/*    */     //   33: new 5	java/awt/SentEvent
/*    */     //   36: dup
/*    */     //   37: invokespecial 12	java/awt/SentEvent:<init>	()V
/*    */     //   40: invokestatic 13	sun/awt/SunToolkit:postEvent	(Lsun/awt/AppContext;Ljava/awt/AWTEvent;)V
/*    */     //   43: aload_0
/*    */     //   44: dup
/*    */     //   45: astore_1
/*    */     //   46: monitorenter
/*    */     //   47: aload_0
/*    */     //   48: invokevirtual 14	java/lang/Object:notifyAll	()V
/*    */     //   51: aload_1
/*    */     //   52: monitorexit
/*    */     //   53: goto +8 -> 61
/*    */     //   56: astore_2
/*    */     //   57: aload_1
/*    */     //   58: monitorexit
/*    */     //   59: aload_2
/*    */     //   60: athrow
/*    */     //   61: goto +55 -> 116
/*    */     //   64: astore_3
/*    */     //   65: aload_0
/*    */     //   66: iconst_1
/*    */     //   67: putfield 11	java/awt/SentEvent:dispatched	Z
/*    */     //   70: aload_0
/*    */     //   71: getfield 8	java/awt/SentEvent:toNotify	Lsun/awt/AppContext;
/*    */     //   74: ifnull +17 -> 91
/*    */     //   77: aload_0
/*    */     //   78: getfield 8	java/awt/SentEvent:toNotify	Lsun/awt/AppContext;
/*    */     //   81: new 5	java/awt/SentEvent
/*    */     //   84: dup
/*    */     //   85: invokespecial 12	java/awt/SentEvent:<init>	()V
/*    */     //   88: invokestatic 13	sun/awt/SunToolkit:postEvent	(Lsun/awt/AppContext;Ljava/awt/AWTEvent;)V
/*    */     //   91: aload_0
/*    */     //   92: dup
/*    */     //   93: astore 4
/*    */     //   95: monitorenter
/*    */     //   96: aload_0
/*    */     //   97: invokevirtual 14	java/lang/Object:notifyAll	()V
/*    */     //   100: aload 4
/*    */     //   102: monitorexit
/*    */     //   103: goto +11 -> 114
/*    */     //   106: astore 5
/*    */     //   108: aload 4
/*    */     //   110: monitorexit
/*    */     //   111: aload 5
/*    */     //   113: athrow
/*    */     //   114: aload_3
/*    */     //   115: athrow
/*    */     //   116: return
/*    */     // Line number table:
/*    */     //   Java source line #69	-> byte code offset #0
/*    */     //   Java source line #70	-> byte code offset #7
/*    */     //   Java source line #73	-> byte code offset #17
/*    */     //   Java source line #74	-> byte code offset #22
/*    */     //   Java source line #75	-> byte code offset #29
/*    */     //   Java source line #77	-> byte code offset #43
/*    */     //   Java source line #78	-> byte code offset #47
/*    */     //   Java source line #79	-> byte code offset #51
/*    */     //   Java source line #80	-> byte code offset #61
/*    */     //   Java source line #73	-> byte code offset #64
/*    */     //   Java source line #74	-> byte code offset #70
/*    */     //   Java source line #75	-> byte code offset #77
/*    */     //   Java source line #77	-> byte code offset #91
/*    */     //   Java source line #78	-> byte code offset #96
/*    */     //   Java source line #79	-> byte code offset #100
/*    */     //   Java source line #81	-> byte code offset #116
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	signature
/*    */     //   0	117	0	this	SentEvent
/*    */     //   56	4	2	localObject1	Object
/*    */     //   64	51	3	localObject2	Object
/*    */     //   106	6	5	localObject3	Object
/*    */     // Exception table:
/*    */     //   from	to	target	type
/*    */     //   47	53	56	finally
/*    */     //   56	59	56	finally
/*    */     //   0	17	64	finally
/*    */     //   96	103	106	finally
/*    */     //   106	111	106	finally
/*    */   }
/*    */   
/*    */   final void dispose()
/*    */   {
/* 83 */     this.dispatched = true;
/* 84 */     if (this.toNotify != null) {
/* 85 */       SunToolkit.postEvent(this.toNotify, new SentEvent());
/*    */     }
/* 87 */     synchronized (this) {
/* 88 */       notifyAll();
/*    */     }
/*    */   }
/*    */ }


/* Location:              /Users/guanxiaoda/Documents/workspace/rt.jar!/java/awt/SentEvent.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */