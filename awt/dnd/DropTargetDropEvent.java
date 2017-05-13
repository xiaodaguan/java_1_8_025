/*     */ package java.awt.dnd;
/*     */ 
/*     */ import java.awt.Point;
/*     */ import java.awt.datatransfer.DataFlavor;
/*     */ import java.awt.datatransfer.Transferable;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class DropTargetDropEvent
/*     */   extends DropTargetEvent
/*     */ {
/*     */   private static final long serialVersionUID = -1721911170440459322L;
/*     */   
/*     */   public DropTargetDropEvent(DropTargetContext paramDropTargetContext, Point paramPoint, int paramInt1, int paramInt2)
/*     */   {
/* 105 */     super(paramDropTargetContext);
/*     */     
/* 107 */     if (paramPoint == null) { throw new NullPointerException("cursorLocn");
/*     */     }
/* 109 */     if ((paramInt1 != 0) && (paramInt1 != 1) && (paramInt1 != 2) && (paramInt1 != 1073741824))
/*     */     {
/*     */ 
/*     */ 
/* 113 */       throw new IllegalArgumentException("dropAction = " + paramInt1);
/*     */     }
/* 115 */     if ((paramInt2 & 0xBFFFFFFC) != 0) { throw new IllegalArgumentException("srcActions");
/*     */     }
/* 117 */     this.location = paramPoint;
/* 118 */     this.actions = paramInt2;
/* 119 */     this.dropAction = paramInt1;
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
/*     */   public DropTargetDropEvent(DropTargetContext paramDropTargetContext, Point paramPoint, int paramInt1, int paramInt2, boolean paramBoolean)
/*     */   {
/* 148 */     this(paramDropTargetContext, paramPoint, paramInt1, paramInt2);
/*     */     
/* 150 */     this.isLocalTx = paramBoolean;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public Point getLocation()
/*     */   {
/* 162 */     return this.location;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public DataFlavor[] getCurrentDataFlavors()
/*     */   {
/* 173 */     return getDropTargetContext().getCurrentDataFlavors();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public List<DataFlavor> getCurrentDataFlavorsAsList()
/*     */   {
/* 184 */     return getDropTargetContext().getCurrentDataFlavorsAsList();
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
/*     */   public boolean isDataFlavorSupported(DataFlavor paramDataFlavor)
/*     */   {
/* 198 */     return getDropTargetContext().isDataFlavorSupported(paramDataFlavor);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public int getSourceActions()
/*     */   {
/* 206 */     return this.actions;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public int getDropAction()
/*     */   {
/* 213 */     return this.dropAction;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public Transferable getTransferable()
/*     */   {
/* 223 */     return getDropTargetContext().getTransferable();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void acceptDrop(int paramInt)
/*     */   {
/* 233 */     getDropTargetContext().acceptDrop(paramInt);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public void rejectDrop()
/*     */   {
/* 241 */     getDropTargetContext().rejectDrop();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void dropComplete(boolean paramBoolean)
/*     */   {
/* 252 */     getDropTargetContext().dropComplete(paramBoolean);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public boolean isLocalTransfer()
/*     */   {
/* 263 */     return this.isLocalTx;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 270 */   private static final Point zero = new Point(0, 0);
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 277 */   private Point location = zero;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 284 */   private int actions = 0;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 291 */   private int dropAction = 0;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 298 */   private boolean isLocalTx = false;
/*     */ }


/* Location:              /Users/guanxiaoda/Documents/workspace/rt.jar!/java/awt/dnd/DropTargetDropEvent.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */