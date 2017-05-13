/*     */ package java.awt.dnd;
/*     */ 
/*     */ import java.awt.Component;
/*     */ import java.awt.Cursor;
/*     */ import java.awt.GraphicsEnvironment;
/*     */ import java.awt.HeadlessException;
/*     */ import java.awt.Image;
/*     */ import java.awt.Point;
/*     */ import java.awt.Toolkit;
/*     */ import java.awt.datatransfer.FlavorMap;
/*     */ import java.awt.datatransfer.SystemFlavorMap;
/*     */ import java.awt.datatransfer.Transferable;
/*     */ import java.awt.dnd.peer.DragSourceContextPeer;
/*     */ import java.io.IOException;
/*     */ import java.io.ObjectInputStream;
/*     */ import java.io.ObjectOutputStream;
/*     */ import java.io.Serializable;
/*     */ import java.security.AccessController;
/*     */ import java.util.EventListener;
/*     */ import sun.awt.dnd.SunDragSourceContextPeer;
/*     */ import sun.security.action.GetIntegerAction;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class DragSource
/*     */   implements Serializable
/*     */ {
/*     */   private static final long serialVersionUID = 6236096958971414066L;
/*     */   
/*     */   private static Cursor load(String paramString)
/*     */   {
/* 126 */     if (GraphicsEnvironment.isHeadless()) {
/* 127 */       return null;
/*     */     }
/*     */     try
/*     */     {
/* 131 */       return (Cursor)Toolkit.getDefaultToolkit().getDesktopProperty(paramString);
/*     */     } catch (Exception localException) {
/* 133 */       localException.printStackTrace();
/*     */       
/* 135 */       throw new RuntimeException("failed to load system cursor: " + paramString + " : " + localException.getMessage());
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
/* 148 */   public static final Cursor DefaultCopyDrop = load("DnD.Cursor.CopyDrop");
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 158 */   public static final Cursor DefaultMoveDrop = load("DnD.Cursor.MoveDrop");
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 168 */   public static final Cursor DefaultLinkDrop = load("DnD.Cursor.LinkDrop");
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 178 */   public static final Cursor DefaultCopyNoDrop = load("DnD.Cursor.CopyNoDrop");
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 188 */   public static final Cursor DefaultMoveNoDrop = load("DnD.Cursor.MoveNoDrop");
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 198 */   public static final Cursor DefaultLinkNoDrop = load("DnD.Cursor.LinkNoDrop");
/*     */   
/*     */ 
/* 201 */   private static final DragSource dflt = GraphicsEnvironment.isHeadless() ? null : new DragSource();
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   static final String dragSourceListenerK = "dragSourceL";
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   static final String dragSourceMotionListenerK = "dragSourceMotionL";
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static DragSource getDefaultDragSource()
/*     */   {
/* 219 */     if (GraphicsEnvironment.isHeadless()) {
/* 220 */       throw new HeadlessException();
/*     */     }
/* 222 */     return dflt;
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
/*     */   public static boolean isDragImageSupported()
/*     */   {
/* 236 */     Toolkit localToolkit = Toolkit.getDefaultToolkit();
/*     */     
/*     */ 
/*     */     try
/*     */     {
/* 241 */       Boolean localBoolean = (Boolean)Toolkit.getDefaultToolkit().getDesktopProperty("DnD.isDragImageSupported");
/*     */       
/* 243 */       return localBoolean.booleanValue();
/*     */     } catch (Exception localException) {}
/* 245 */     return false;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public DragSource()
/*     */     throws HeadlessException
/*     */   {
/* 257 */     if (GraphicsEnvironment.isHeadless()) {
/* 258 */       throw new HeadlessException();
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
/*     */ 
/*     */ 
/*     */   public void startDrag(DragGestureEvent paramDragGestureEvent, Cursor paramCursor, Image paramImage, Point paramPoint, Transferable paramTransferable, DragSourceListener paramDragSourceListener, FlavorMap paramFlavorMap)
/*     */     throws InvalidDnDOperationException
/*     */   {
/* 301 */     SunDragSourceContextPeer.setDragDropInProgress(true);
/*     */     try
/*     */     {
/* 304 */       if (paramFlavorMap != null) { this.flavorMap = paramFlavorMap;
/*     */       }
/* 306 */       DragSourceContextPeer localDragSourceContextPeer = Toolkit.getDefaultToolkit().createDragSourceContextPeer(paramDragGestureEvent);
/*     */       
/* 308 */       DragSourceContext localDragSourceContext = createDragSourceContext(localDragSourceContextPeer, paramDragGestureEvent, paramCursor, paramImage, paramPoint, paramTransferable, paramDragSourceListener);
/*     */       
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 317 */       if (localDragSourceContext == null) {
/* 318 */         throw new InvalidDnDOperationException();
/*     */       }
/*     */       
/* 321 */       localDragSourceContextPeer.startDrag(localDragSourceContext, localDragSourceContext.getCursor(), paramImage, paramPoint);
/*     */     } catch (RuntimeException localRuntimeException) {
/* 323 */       SunDragSourceContextPeer.setDragDropInProgress(false);
/* 324 */       throw localRuntimeException;
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
/*     */   public void startDrag(DragGestureEvent paramDragGestureEvent, Cursor paramCursor, Transferable paramTransferable, DragSourceListener paramDragSourceListener, FlavorMap paramFlavorMap)
/*     */     throws InvalidDnDOperationException
/*     */   {
/* 358 */     startDrag(paramDragGestureEvent, paramCursor, null, null, paramTransferable, paramDragSourceListener, paramFlavorMap);
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
/*     */   public void startDrag(DragGestureEvent paramDragGestureEvent, Cursor paramCursor, Image paramImage, Point paramPoint, Transferable paramTransferable, DragSourceListener paramDragSourceListener)
/*     */     throws InvalidDnDOperationException
/*     */   {
/* 396 */     startDrag(paramDragGestureEvent, paramCursor, paramImage, paramPoint, paramTransferable, paramDragSourceListener, null);
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
/*     */   public void startDrag(DragGestureEvent paramDragGestureEvent, Cursor paramCursor, Transferable paramTransferable, DragSourceListener paramDragSourceListener)
/*     */     throws InvalidDnDOperationException
/*     */   {
/* 426 */     startDrag(paramDragGestureEvent, paramCursor, null, null, paramTransferable, paramDragSourceListener, null);
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
/*     */   protected DragSourceContext createDragSourceContext(DragSourceContextPeer paramDragSourceContextPeer, DragGestureEvent paramDragGestureEvent, Cursor paramCursor, Image paramImage, Point paramPoint, Transferable paramTransferable, DragSourceListener paramDragSourceListener)
/*     */   {
/* 477 */     return new DragSourceContext(paramDragSourceContextPeer, paramDragGestureEvent, paramCursor, paramImage, paramPoint, paramTransferable, paramDragSourceListener);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public FlavorMap getFlavorMap()
/*     */   {
/* 487 */     return this.flavorMap;
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
/*     */   public <T extends DragGestureRecognizer> T createDragGestureRecognizer(Class<T> paramClass, Component paramComponent, int paramInt, DragGestureListener paramDragGestureListener)
/*     */   {
/* 515 */     return Toolkit.getDefaultToolkit().createDragGestureRecognizer(paramClass, this, paramComponent, paramInt, paramDragGestureListener);
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
/*     */   public DragGestureRecognizer createDefaultDragGestureRecognizer(Component paramComponent, int paramInt, DragGestureListener paramDragGestureListener)
/*     */   {
/* 543 */     return Toolkit.getDefaultToolkit().createDragGestureRecognizer(MouseDragGestureRecognizer.class, this, paramComponent, paramInt, paramDragGestureListener);
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
/*     */   public void addDragSourceListener(DragSourceListener paramDragSourceListener)
/*     */   {
/* 560 */     if (paramDragSourceListener != null) {
/* 561 */       synchronized (this) {
/* 562 */         this.listener = DnDEventMulticaster.add(this.listener, paramDragSourceListener);
/*     */       }
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
/*     */   public void removeDragSourceListener(DragSourceListener paramDragSourceListener)
/*     */   {
/* 583 */     if (paramDragSourceListener != null) {
/* 584 */       synchronized (this) {
/* 585 */         this.listener = DnDEventMulticaster.remove(this.listener, paramDragSourceListener);
/*     */       }
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
/*     */   public DragSourceListener[] getDragSourceListeners()
/*     */   {
/* 603 */     return (DragSourceListener[])getListeners(DragSourceListener.class);
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
/*     */   public void addDragSourceMotionListener(DragSourceMotionListener paramDragSourceMotionListener)
/*     */   {
/* 620 */     if (paramDragSourceMotionListener != null) {
/* 621 */       synchronized (this) {
/* 622 */         this.motionListener = DnDEventMulticaster.add(this.motionListener, paramDragSourceMotionListener);
/*     */       }
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
/*     */   public void removeDragSourceMotionListener(DragSourceMotionListener paramDragSourceMotionListener)
/*     */   {
/* 643 */     if (paramDragSourceMotionListener != null) {
/* 644 */       synchronized (this) {
/* 645 */         this.motionListener = DnDEventMulticaster.remove(this.motionListener, paramDragSourceMotionListener);
/*     */       }
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
/*     */   public DragSourceMotionListener[] getDragSourceMotionListeners()
/*     */   {
/* 663 */     return (DragSourceMotionListener[])getListeners(DragSourceMotionListener.class);
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
/*     */   public <T extends EventListener> T[] getListeners(Class<T> paramClass)
/*     */   {
/* 688 */     Object localObject = null;
/* 689 */     if (paramClass == DragSourceListener.class) {
/* 690 */       localObject = this.listener;
/* 691 */     } else if (paramClass == DragSourceMotionListener.class) {
/* 692 */       localObject = this.motionListener;
/*     */     }
/* 694 */     return DnDEventMulticaster.getListeners((EventListener)localObject, paramClass);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   void processDragEnter(DragSourceDragEvent paramDragSourceDragEvent)
/*     */   {
/* 706 */     DragSourceListener localDragSourceListener = this.listener;
/* 707 */     if (localDragSourceListener != null) {
/* 708 */       localDragSourceListener.dragEnter(paramDragSourceDragEvent);
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
/*     */   void processDragOver(DragSourceDragEvent paramDragSourceDragEvent)
/*     */   {
/* 721 */     DragSourceListener localDragSourceListener = this.listener;
/* 722 */     if (localDragSourceListener != null) {
/* 723 */       localDragSourceListener.dragOver(paramDragSourceDragEvent);
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
/*     */   void processDropActionChanged(DragSourceDragEvent paramDragSourceDragEvent)
/*     */   {
/* 736 */     DragSourceListener localDragSourceListener = this.listener;
/* 737 */     if (localDragSourceListener != null) {
/* 738 */       localDragSourceListener.dropActionChanged(paramDragSourceDragEvent);
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
/*     */   void processDragExit(DragSourceEvent paramDragSourceEvent)
/*     */   {
/* 751 */     DragSourceListener localDragSourceListener = this.listener;
/* 752 */     if (localDragSourceListener != null) {
/* 753 */       localDragSourceListener.dragExit(paramDragSourceEvent);
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
/*     */   void processDragDropEnd(DragSourceDropEvent paramDragSourceDropEvent)
/*     */   {
/* 766 */     DragSourceListener localDragSourceListener = this.listener;
/* 767 */     if (localDragSourceListener != null) {
/* 768 */       localDragSourceListener.dragDropEnd(paramDragSourceDropEvent);
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
/*     */   void processDragMouseMoved(DragSourceDragEvent paramDragSourceDragEvent)
/*     */   {
/* 781 */     DragSourceMotionListener localDragSourceMotionListener = this.motionListener;
/* 782 */     if (localDragSourceMotionListener != null) {
/* 783 */       localDragSourceMotionListener.dragMouseMoved(paramDragSourceDragEvent);
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
/*     */   private void writeObject(ObjectOutputStream paramObjectOutputStream)
/*     */     throws IOException
/*     */   {
/* 819 */     paramObjectOutputStream.defaultWriteObject();
/*     */     
/* 821 */     paramObjectOutputStream.writeObject(SerializationTester.test(this.flavorMap) ? this.flavorMap : null);
/*     */     
/* 823 */     DnDEventMulticaster.save(paramObjectOutputStream, "dragSourceL", this.listener);
/* 824 */     DnDEventMulticaster.save(paramObjectOutputStream, "dragSourceMotionL", this.motionListener);
/* 825 */     paramObjectOutputStream.writeObject(null);
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
/*     */   private void readObject(ObjectInputStream paramObjectInputStream)
/*     */     throws ClassNotFoundException, IOException
/*     */   {
/* 855 */     paramObjectInputStream.defaultReadObject();
/*     */     
/*     */ 
/* 858 */     this.flavorMap = ((FlavorMap)paramObjectInputStream.readObject());
/*     */     
/*     */ 
/* 861 */     if (this.flavorMap == null) {
/* 862 */       this.flavorMap = SystemFlavorMap.getDefaultFlavorMap();
/*     */     }
/*     */     
/*     */     Object localObject;
/* 866 */     while (null != (localObject = paramObjectInputStream.readObject())) {
/* 867 */       String str = ((String)localObject).intern();
/*     */       
/* 869 */       if ("dragSourceL" == str) {
/* 870 */         addDragSourceListener((DragSourceListener)paramObjectInputStream.readObject());
/* 871 */       } else if ("dragSourceMotionL" == str) {
/* 872 */         addDragSourceMotionListener(
/* 873 */           (DragSourceMotionListener)paramObjectInputStream.readObject());
/*     */       }
/*     */       else {
/* 876 */         paramObjectInputStream.readObject();
/*     */       }
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
/*     */   public static int getDragThreshold()
/*     */   {
/* 899 */     int i = ((Integer)AccessController.doPrivileged(new GetIntegerAction("awt.dnd.drag.threshold", 0))).intValue();
/* 900 */     if (i > 0) {
/* 901 */       return i;
/*     */     }
/*     */     
/* 904 */     Integer localInteger = (Integer)Toolkit.getDefaultToolkit().getDesktopProperty("DnD.gestureMotionThreshold");
/* 905 */     if (localInteger != null) {
/* 906 */       return localInteger.intValue();
/*     */     }
/*     */     
/* 909 */     return 5;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 916 */   private transient FlavorMap flavorMap = SystemFlavorMap.getDefaultFlavorMap();
/*     */   private transient DragSourceListener listener;
/*     */   private transient DragSourceMotionListener motionListener;
/*     */ }


/* Location:              /Users/guanxiaoda/Documents/workspace/rt.jar!/java/awt/dnd/DragSource.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */