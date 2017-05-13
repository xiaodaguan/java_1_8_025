/*     */ package java.nio.channels.spi;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.nio.channels.SelectionKey;
/*     */ import java.nio.channels.Selector;
/*     */ import java.util.HashSet;
/*     */ import java.util.Set;
/*     */ import java.util.concurrent.atomic.AtomicBoolean;
/*     */ import sun.nio.ch.Interruptible;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class AbstractSelector
/*     */   extends Selector
/*     */ {
/*  73 */   private AtomicBoolean selectorOpen = new AtomicBoolean(true);
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   private final SelectorProvider provider;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   protected AbstractSelector(SelectorProvider paramSelectorProvider)
/*     */   {
/*  85 */     this.provider = paramSelectorProvider;
/*     */   }
/*     */   
/*  88 */   private final Set<SelectionKey> cancelledKeys = new HashSet();
/*     */   
/*     */   void cancel(SelectionKey paramSelectionKey) {
/*  91 */     synchronized (this.cancelledKeys) {
/*  92 */       this.cancelledKeys.add(paramSelectionKey);
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
/*     */   public final void close()
/*     */     throws IOException
/*     */   {
/* 108 */     boolean bool = this.selectorOpen.getAndSet(false);
/* 109 */     if (!bool)
/* 110 */       return;
/* 111 */     implCloseSelector();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   protected abstract void implCloseSelector()
/*     */     throws IOException;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public final boolean isOpen()
/*     */   {
/* 133 */     return this.selectorOpen.get();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public final SelectorProvider provider()
/*     */   {
/* 142 */     return this.provider;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   protected final Set<SelectionKey> cancelledKeys()
/*     */   {
/* 153 */     return this.cancelledKeys;
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
/*     */   protected abstract SelectionKey register(AbstractSelectableChannel paramAbstractSelectableChannel, int paramInt, Object paramObject);
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   protected final void deregister(AbstractSelectionKey paramAbstractSelectionKey)
/*     */   {
/* 188 */     ((AbstractSelectableChannel)paramAbstractSelectionKey.channel()).removeKey(paramAbstractSelectionKey);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/* 194 */   private Interruptible interruptor = null;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   protected final void begin()
/*     */   {
/* 210 */     if (this.interruptor == null)
/* 211 */       this.interruptor = new Interruptible() {
/*     */         public void interrupt(Thread paramAnonymousThread) {
/* 213 */           AbstractSelector.this.wakeup();
/*     */         }
/*     */       };
/* 216 */     AbstractInterruptibleChannel.blockedOn(this.interruptor);
/* 217 */     Thread localThread = Thread.currentThread();
/* 218 */     if (localThread.isInterrupted()) {
/* 219 */       this.interruptor.interrupt(localThread);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   protected final void end()
/*     */   {
/* 231 */     AbstractInterruptibleChannel.blockedOn(null);
/*     */   }
/*     */ }


/* Location:              /Users/guanxiaoda/Documents/workspace/rt.jar!/java/nio/channels/spi/AbstractSelector.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */