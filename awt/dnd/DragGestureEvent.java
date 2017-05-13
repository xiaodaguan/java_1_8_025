/*     */ package java.awt.dnd;
/*     */ 
/*     */ import java.awt.Component;
/*     */ import java.awt.Cursor;
/*     */ import java.awt.Image;
/*     */ import java.awt.Point;
/*     */ import java.awt.datatransfer.Transferable;
/*     */ import java.awt.event.InputEvent;
/*     */ import java.io.IOException;
/*     */ import java.io.InvalidObjectException;
/*     */ import java.io.ObjectInputStream;
/*     */ import java.io.ObjectInputStream.GetField;
/*     */ import java.io.ObjectOutputStream;
/*     */ import java.util.Collections;
/*     */ import java.util.EventObject;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class DragGestureEvent
/*     */   extends EventObject
/*     */ {
/*     */   private static final long serialVersionUID = 9080172649166731306L;
/*     */   private transient List events;
/*     */   private DragSource dragSource;
/*     */   private Component component;
/*     */   private Point origin;
/*     */   private int action;
/*     */   
/*     */   public DragGestureEvent(DragGestureRecognizer paramDragGestureRecognizer, int paramInt, Point paramPoint, List<? extends InputEvent> paramList)
/*     */   {
/* 103 */     super(paramDragGestureRecognizer);
/*     */     
/* 105 */     if ((this.component = paramDragGestureRecognizer.getComponent()) == null)
/* 106 */       throw new IllegalArgumentException("null component");
/* 107 */     if ((this.dragSource = paramDragGestureRecognizer.getDragSource()) == null) {
/* 108 */       throw new IllegalArgumentException("null DragSource");
/*     */     }
/* 110 */     if ((paramList == null) || (paramList.isEmpty())) {
/* 111 */       throw new IllegalArgumentException("null or empty list of events");
/*     */     }
/* 113 */     if ((paramInt != 1) && (paramInt != 2) && (paramInt != 1073741824))
/*     */     {
/*     */ 
/* 116 */       throw new IllegalArgumentException("bad action");
/*     */     }
/* 118 */     if (paramPoint == null) { throw new IllegalArgumentException("null origin");
/*     */     }
/* 120 */     this.events = paramList;
/* 121 */     this.action = paramInt;
/* 122 */     this.origin = paramPoint;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public DragGestureRecognizer getSourceAsDragGestureRecognizer()
/*     */   {
/* 132 */     return (DragGestureRecognizer)getSource();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public Component getComponent()
/*     */   {
/* 142 */     return this.component;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public DragSource getDragSource()
/*     */   {
/* 150 */     return this.dragSource;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public Point getDragOrigin()
/*     */   {
/* 160 */     return this.origin;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public Iterator<InputEvent> iterator()
/*     */   {
/* 170 */     return this.events.iterator();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public Object[] toArray()
/*     */   {
/* 179 */     return this.events.toArray();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public Object[] toArray(Object[] paramArrayOfObject)
/*     */   {
/* 189 */     return this.events.toArray(paramArrayOfObject);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public int getDragAction()
/*     */   {
/* 198 */     return this.action;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public InputEvent getTriggerEvent()
/*     */   {
/* 207 */     return getSourceAsDragGestureRecognizer().getTriggerEvent();
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
/*     */   public void startDrag(Cursor paramCursor, Transferable paramTransferable)
/*     */     throws InvalidDnDOperationException
/*     */   {
/* 238 */     this.dragSource.startDrag(this, paramCursor, paramTransferable, null);
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
/*     */   public void startDrag(Cursor paramCursor, Transferable paramTransferable, DragSourceListener paramDragSourceListener)
/*     */     throws InvalidDnDOperationException
/*     */   {
/* 263 */     this.dragSource.startDrag(this, paramCursor, paramTransferable, paramDragSourceListener);
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
/*     */   public void startDrag(Cursor paramCursor, Image paramImage, Point paramPoint, Transferable paramTransferable, DragSourceListener paramDragSourceListener)
/*     */     throws InvalidDnDOperationException
/*     */   {
/* 292 */     this.dragSource.startDrag(this, paramCursor, paramImage, paramPoint, paramTransferable, paramDragSourceListener);
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
/*     */   private void writeObject(ObjectOutputStream paramObjectOutputStream)
/*     */     throws IOException
/*     */   {
/* 309 */     paramObjectOutputStream.defaultWriteObject();
/*     */     
/* 311 */     paramObjectOutputStream.writeObject(SerializationTester.test(this.events) ? this.events : null);
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
/*     */   private void readObject(ObjectInputStream paramObjectInputStream)
/*     */     throws ClassNotFoundException, IOException
/*     */   {
/* 331 */     ObjectInputStream.GetField localGetField = paramObjectInputStream.readFields();
/*     */     
/* 333 */     DragSource localDragSource = (DragSource)localGetField.get("dragSource", null);
/* 334 */     if (localDragSource == null) {
/* 335 */       throw new InvalidObjectException("null DragSource");
/*     */     }
/* 337 */     this.dragSource = localDragSource;
/*     */     
/* 339 */     Component localComponent = (Component)localGetField.get("component", null);
/* 340 */     if (localComponent == null) {
/* 341 */       throw new InvalidObjectException("null component");
/*     */     }
/* 343 */     this.component = localComponent;
/*     */     
/* 345 */     Point localPoint = (Point)localGetField.get("origin", null);
/* 346 */     if (localPoint == null) {
/* 347 */       throw new InvalidObjectException("null origin");
/*     */     }
/* 349 */     this.origin = localPoint;
/*     */     
/* 351 */     int i = localGetField.get("action", 0);
/* 352 */     if ((i != 1) && (i != 2) && (i != 1073741824))
/*     */     {
/*     */ 
/* 355 */       throw new InvalidObjectException("bad action");
/*     */     }
/* 357 */     this.action = i;
/*     */     
/*     */     List localList;
/*     */     try
/*     */     {
/* 362 */       localList = (List)localGetField.get("events", null);
/*     */     }
/*     */     catch (IllegalArgumentException localIllegalArgumentException) {
/* 365 */       localList = (List)paramObjectInputStream.readObject();
/*     */     }
/*     */     
/*     */ 
/* 369 */     if ((localList != null) && (localList.isEmpty()))
/*     */     {
/*     */ 
/* 372 */       throw new InvalidObjectException("empty list of events"); }
/* 373 */     if (localList == null) {
/* 374 */       localList = Collections.emptyList();
/*     */     }
/* 376 */     this.events = localList;
/*     */   }
/*     */ }


/* Location:              /Users/guanxiaoda/Documents/workspace/rt.jar!/java/awt/dnd/DragGestureEvent.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */