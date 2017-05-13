/*     */ package java.awt.datatransfer;
/*     */ 
/*     */ import java.awt.EventQueue;
/*     */ import java.io.IOException;
/*     */ import java.util.Arrays;
/*     */ import java.util.HashSet;
/*     */ import java.util.Set;
/*     */ import sun.awt.EventListenerAggregate;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class Clipboard
/*     */ {
/*     */   String name;
/*     */   protected ClipboardOwner owner;
/*     */   protected Transferable contents;
/*     */   private EventListenerAggregate flavorListeners;
/*     */   private Set<DataFlavor> currentDataFlavors;
/*     */   
/*     */   public Clipboard(String paramString)
/*     */   {
/*  82 */     this.name = paramString;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public String getName()
/*     */   {
/*  91 */     return this.name;
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
/*     */   public synchronized void setContents(Transferable paramTransferable, ClipboardOwner paramClipboardOwner)
/*     */   {
/* 120 */     final ClipboardOwner localClipboardOwner = this.owner;
/* 121 */     final Transferable localTransferable = this.contents;
/*     */     
/* 123 */     this.owner = paramClipboardOwner;
/* 124 */     this.contents = paramTransferable;
/*     */     
/* 126 */     if ((localClipboardOwner != null) && (localClipboardOwner != paramClipboardOwner)) {
/* 127 */       EventQueue.invokeLater(new Runnable() {
/*     */         public void run() {
/* 129 */           localClipboardOwner.lostOwnership(Clipboard.this, localTransferable);
/*     */         }
/*     */       });
/*     */     }
/* 133 */     fireFlavorsChanged();
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
/*     */   public synchronized Transferable getContents(Object paramObject)
/*     */   {
/* 151 */     return this.contents;
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
/*     */   public DataFlavor[] getAvailableDataFlavors()
/*     */   {
/* 169 */     Transferable localTransferable = getContents(null);
/* 170 */     if (localTransferable == null) {
/* 171 */       return new DataFlavor[0];
/*     */     }
/* 173 */     return localTransferable.getTransferDataFlavors();
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
/*     */   public boolean isDataFlavorAvailable(DataFlavor paramDataFlavor)
/*     */   {
/* 192 */     if (paramDataFlavor == null) {
/* 193 */       throw new NullPointerException("flavor");
/*     */     }
/*     */     
/* 196 */     Transferable localTransferable = getContents(null);
/* 197 */     if (localTransferable == null) {
/* 198 */       return false;
/*     */     }
/* 200 */     return localTransferable.isDataFlavorSupported(paramDataFlavor);
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
/*     */   public Object getData(DataFlavor paramDataFlavor)
/*     */     throws UnsupportedFlavorException, IOException
/*     */   {
/* 227 */     if (paramDataFlavor == null) {
/* 228 */       throw new NullPointerException("flavor");
/*     */     }
/*     */     
/* 231 */     Transferable localTransferable = getContents(null);
/* 232 */     if (localTransferable == null) {
/* 233 */       throw new UnsupportedFlavorException(paramDataFlavor);
/*     */     }
/* 235 */     return localTransferable.getTransferData(paramDataFlavor);
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
/*     */   public synchronized void addFlavorListener(FlavorListener paramFlavorListener)
/*     */   {
/* 254 */     if (paramFlavorListener == null) {
/* 255 */       return;
/*     */     }
/* 257 */     if (this.flavorListeners == null) {
/* 258 */       this.currentDataFlavors = getAvailableDataFlavorSet();
/* 259 */       this.flavorListeners = new EventListenerAggregate(FlavorListener.class);
/*     */     }
/* 261 */     this.flavorListeners.add(paramFlavorListener);
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
/*     */   public synchronized void removeFlavorListener(FlavorListener paramFlavorListener)
/*     */   {
/* 282 */     if ((paramFlavorListener == null) || (this.flavorListeners == null)) {
/* 283 */       return;
/*     */     }
/* 285 */     this.flavorListeners.remove(paramFlavorListener);
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
/*     */   public synchronized FlavorListener[] getFlavorListeners()
/*     */   {
/* 302 */     return this.flavorListeners == null ? new FlavorListener[0] : (FlavorListener[])this.flavorListeners.getListenersCopy();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private void fireFlavorsChanged()
/*     */   {
/* 313 */     if (this.flavorListeners == null) {
/* 314 */       return;
/*     */     }
/* 316 */     Set localSet = this.currentDataFlavors;
/* 317 */     this.currentDataFlavors = getAvailableDataFlavorSet();
/* 318 */     if (localSet.equals(this.currentDataFlavors)) {
/* 319 */       return;
/*     */     }
/*     */     
/* 322 */     FlavorListener[] arrayOfFlavorListener = (FlavorListener[])this.flavorListeners.getListenersInternal();
/* 323 */     for (int i = 0; i < arrayOfFlavorListener.length; i++) {
/* 324 */       final FlavorListener localFlavorListener = arrayOfFlavorListener[i];
/* 325 */       EventQueue.invokeLater(new Runnable() {
/*     */         public void run() {
/* 327 */           localFlavorListener.flavorsChanged(new FlavorEvent(Clipboard.this));
/*     */         }
/*     */       });
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
/*     */   private Set<DataFlavor> getAvailableDataFlavorSet()
/*     */   {
/* 343 */     HashSet localHashSet = new HashSet();
/* 344 */     Transferable localTransferable = getContents(null);
/* 345 */     if (localTransferable != null) {
/* 346 */       DataFlavor[] arrayOfDataFlavor = localTransferable.getTransferDataFlavors();
/* 347 */       if (arrayOfDataFlavor != null) {
/* 348 */         localHashSet.addAll(Arrays.asList(arrayOfDataFlavor));
/*     */       }
/*     */     }
/* 351 */     return localHashSet;
/*     */   }
/*     */ }


/* Location:              /Users/guanxiaoda/Documents/workspace/rt.jar!/java/awt/datatransfer/Clipboard.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */