/*     */ package java.awt.dnd;
/*     */ 
/*     */ import java.awt.Component;
/*     */ import java.awt.event.InputEvent;
/*     */ import java.io.IOException;
/*     */ import java.io.InvalidObjectException;
/*     */ import java.io.ObjectInputStream;
/*     */ import java.io.ObjectInputStream.GetField;
/*     */ import java.io.ObjectOutputStream;
/*     */ import java.io.Serializable;
/*     */ import java.util.ArrayList;
/*     */ import java.util.TooManyListenersException;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class DragGestureRecognizer
/*     */   implements Serializable
/*     */ {
/*     */   private static final long serialVersionUID = 8996673345831063337L;
/*     */   protected DragSource dragSource;
/*     */   protected Component component;
/*     */   protected transient DragGestureListener dragGestureListener;
/*     */   protected int sourceActions;
/*     */   
/*     */   protected DragGestureRecognizer(DragSource paramDragSource, Component paramComponent, int paramInt, DragGestureListener paramDragGestureListener)
/*     */   {
/* 126 */     if (paramDragSource == null) { throw new IllegalArgumentException("null DragSource");
/*     */     }
/* 128 */     this.dragSource = paramDragSource;
/* 129 */     this.component = paramComponent;
/* 130 */     this.sourceActions = (paramInt & 0x40000003);
/*     */     try
/*     */     {
/* 133 */       if (paramDragGestureListener != null) { addDragGestureListener(paramDragGestureListener);
/*     */       }
/*     */     }
/*     */     catch (TooManyListenersException localTooManyListenersException) {}
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
/*     */   protected DragGestureRecognizer(DragSource paramDragSource, Component paramComponent, int paramInt)
/*     */   {
/* 167 */     this(paramDragSource, paramComponent, paramInt, null);
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
/*     */   protected DragGestureRecognizer(DragSource paramDragSource, Component paramComponent)
/*     */   {
/* 195 */     this(paramDragSource, paramComponent, 0);
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
/*     */   protected DragGestureRecognizer(DragSource paramDragSource)
/*     */   {
/* 212 */     this(paramDragSource, null);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   protected abstract void registerListeners();
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   protected abstract void unregisterListeners();
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public DragSource getDragSource()
/*     */   {
/* 240 */     return this.dragSource;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public synchronized Component getComponent()
/*     */   {
/* 252 */     return this.component;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public synchronized void setComponent(Component paramComponent)
/*     */   {
/* 264 */     if ((this.component != null) && (this.dragGestureListener != null)) {
/* 265 */       unregisterListeners();
/*     */     }
/* 267 */     this.component = paramComponent;
/*     */     
/* 269 */     if ((this.component != null) && (this.dragGestureListener != null)) {
/* 270 */       registerListeners();
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public synchronized int getSourceActions()
/*     */   {
/* 281 */     return this.sourceActions;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public synchronized void setSourceActions(int paramInt)
/*     */   {
/* 291 */     this.sourceActions = (paramInt & 0x40000003);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public InputEvent getTriggerEvent()
/*     */   {
/* 302 */     return this.events.isEmpty() ? null : (InputEvent)this.events.get(0);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public void resetRecognizer()
/*     */   {
/* 309 */     this.events.clear();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public synchronized void addDragGestureListener(DragGestureListener paramDragGestureListener)
/*     */     throws TooManyListenersException
/*     */   {
/* 322 */     if (this.dragGestureListener != null) {
/* 323 */       throw new TooManyListenersException();
/*     */     }
/* 325 */     this.dragGestureListener = paramDragGestureListener;
/*     */     
/* 327 */     if (this.component != null) { registerListeners();
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
/*     */   public synchronized void removeDragGestureListener(DragGestureListener paramDragGestureListener)
/*     */   {
/* 342 */     if ((this.dragGestureListener == null) || (!this.dragGestureListener.equals(paramDragGestureListener))) {
/* 343 */       throw new IllegalArgumentException();
/*     */     }
/* 345 */     this.dragGestureListener = null;
/*     */     
/* 347 */     if (this.component != null) unregisterListeners();
/*     */   }
/*     */   
/*     */   /* Error */
/*     */   protected synchronized void fireDragGestureRecognized(int paramInt, java.awt.Point paramPoint)
/*     */   {
/*     */     // Byte code:
/*     */     //   0: aload_0
/*     */     //   1: getfield 18	java/awt/dnd/DragGestureRecognizer:dragGestureListener	Ljava/awt/dnd/DragGestureListener;
/*     */     //   4: ifnull +26 -> 30
/*     */     //   7: aload_0
/*     */     //   8: getfield 18	java/awt/dnd/DragGestureRecognizer:dragGestureListener	Ljava/awt/dnd/DragGestureListener;
/*     */     //   11: new 28	java/awt/dnd/DragGestureEvent
/*     */     //   14: dup
/*     */     //   15: aload_0
/*     */     //   16: iload_1
/*     */     //   17: aload_2
/*     */     //   18: aload_0
/*     */     //   19: getfield 4	java/awt/dnd/DragGestureRecognizer:events	Ljava/util/ArrayList;
/*     */     //   22: invokespecial 29	java/awt/dnd/DragGestureEvent:<init>	(Ljava/awt/dnd/DragGestureRecognizer;ILjava/awt/Point;Ljava/util/List;)V
/*     */     //   25: invokeinterface 30 2 0
/*     */     //   30: aload_0
/*     */     //   31: getfield 4	java/awt/dnd/DragGestureRecognizer:events	Ljava/util/ArrayList;
/*     */     //   34: invokevirtual 24	java/util/ArrayList:clear	()V
/*     */     //   37: goto +13 -> 50
/*     */     //   40: astore_3
/*     */     //   41: aload_0
/*     */     //   42: getfield 4	java/awt/dnd/DragGestureRecognizer:events	Ljava/util/ArrayList;
/*     */     //   45: invokevirtual 24	java/util/ArrayList:clear	()V
/*     */     //   48: aload_3
/*     */     //   49: athrow
/*     */     //   50: return
/*     */     // Line number table:
/*     */     //   Java source line #360	-> byte code offset #0
/*     */     //   Java source line #361	-> byte code offset #7
/*     */     //   Java source line #364	-> byte code offset #30
/*     */     //   Java source line #365	-> byte code offset #37
/*     */     //   Java source line #364	-> byte code offset #40
/*     */     //   Java source line #366	-> byte code offset #50
/*     */     // Local variable table:
/*     */     //   start	length	slot	name	signature
/*     */     //   0	51	0	this	DragGestureRecognizer
/*     */     //   0	51	1	paramInt	int
/*     */     //   0	51	2	paramPoint	java.awt.Point
/*     */     //   40	9	3	localObject	Object
/*     */     // Exception table:
/*     */     //   from	to	target	type
/*     */     //   0	30	40	finally
/*     */   }
/*     */   
/*     */   protected synchronized void appendEvent(InputEvent paramInputEvent)
/*     */   {
/* 387 */     this.events.add(paramInputEvent);
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
/*     */   private void writeObject(ObjectOutputStream paramObjectOutputStream)
/*     */     throws IOException
/*     */   {
/* 402 */     paramObjectOutputStream.defaultWriteObject();
/*     */     
/* 404 */     paramObjectOutputStream.writeObject(SerializationTester.test(this.dragGestureListener) ? this.dragGestureListener : null);
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
/*     */   private void readObject(ObjectInputStream paramObjectInputStream)
/*     */     throws ClassNotFoundException, IOException
/*     */   {
/* 420 */     ObjectInputStream.GetField localGetField = paramObjectInputStream.readFields();
/*     */     
/* 422 */     DragSource localDragSource = (DragSource)localGetField.get("dragSource", null);
/* 423 */     if (localDragSource == null) {
/* 424 */       throw new InvalidObjectException("null DragSource");
/*     */     }
/* 426 */     this.dragSource = localDragSource;
/*     */     
/* 428 */     this.component = ((Component)localGetField.get("component", null));
/* 429 */     this.sourceActions = (localGetField.get("sourceActions", 0) & 0x40000003);
/* 430 */     this.events = ((ArrayList)localGetField.get("events", new ArrayList(1)));
/*     */     
/* 432 */     this.dragGestureListener = ((DragGestureListener)paramObjectInputStream.readObject());
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
/* 478 */   protected ArrayList<InputEvent> events = new ArrayList(1);
/*     */ }


/* Location:              /Users/guanxiaoda/Documents/workspace/rt.jar!/java/awt/dnd/DragGestureRecognizer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */