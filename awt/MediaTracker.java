/*     */ package java.awt;
/*     */ 
/*     */ import java.io.Serializable;
/*     */ import sun.awt.image.MultiResolutionToolkitImage;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class MediaTracker
/*     */   implements Serializable
/*     */ {
/*     */   Component target;
/*     */   MediaEntry head;
/*     */   private static final long serialVersionUID = -483174189758638095L;
/*     */   public static final int LOADING = 1;
/*     */   public static final int ABORTED = 2;
/*     */   public static final int ERRORED = 4;
/*     */   public static final int COMPLETE = 8;
/*     */   static final int DONE = 14;
/*     */   
/*     */   public MediaTracker(Component paramComponent)
/*     */   {
/* 201 */     this.target = paramComponent;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void addImage(Image paramImage, int paramInt)
/*     */   {
/* 212 */     addImage(paramImage, paramInt, -1, -1);
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
/*     */   public synchronized void addImage(Image paramImage, int paramInt1, int paramInt2, int paramInt3)
/*     */   {
/* 226 */     addImageImpl(paramImage, paramInt1, paramInt2, paramInt3);
/* 227 */     Image localImage = getResolutionVariant(paramImage);
/* 228 */     if (localImage != null) {
/* 229 */       addImageImpl(localImage, paramInt1, paramInt2 == -1 ? -1 : 2 * paramInt2, paramInt3 == -1 ? -1 : 2 * paramInt3);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */   private void addImageImpl(Image paramImage, int paramInt1, int paramInt2, int paramInt3)
/*     */   {
/* 236 */     this.head = MediaEntry.insert(this.head, new ImageMediaEntry(this, paramImage, paramInt1, paramInt2, paramInt3));
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public boolean checkAll()
/*     */   {
/* 291 */     return checkAll(false, true);
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
/*     */   public boolean checkAll(boolean paramBoolean)
/*     */   {
/* 317 */     return checkAll(paramBoolean, true);
/*     */   }
/*     */   
/*     */   private synchronized boolean checkAll(boolean paramBoolean1, boolean paramBoolean2) {
/* 321 */     MediaEntry localMediaEntry = this.head;
/* 322 */     boolean bool = true;
/* 323 */     while (localMediaEntry != null) {
/* 324 */       if ((localMediaEntry.getStatus(paramBoolean1, paramBoolean2) & 0xE) == 0) {
/* 325 */         bool = false;
/*     */       }
/* 327 */       localMediaEntry = localMediaEntry.next;
/*     */     }
/* 329 */     return bool;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public synchronized boolean isErrorAny()
/*     */   {
/* 341 */     MediaEntry localMediaEntry = this.head;
/* 342 */     while (localMediaEntry != null) {
/* 343 */       if ((localMediaEntry.getStatus(false, true) & 0x4) != 0) {
/* 344 */         return true;
/*     */       }
/* 346 */       localMediaEntry = localMediaEntry.next;
/*     */     }
/* 348 */     return false;
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
/*     */   public synchronized Object[] getErrorsAny()
/*     */   {
/* 361 */     MediaEntry localMediaEntry = this.head;
/* 362 */     int i = 0;
/* 363 */     while (localMediaEntry != null) {
/* 364 */       if ((localMediaEntry.getStatus(false, true) & 0x4) != 0) {
/* 365 */         i++;
/*     */       }
/* 367 */       localMediaEntry = localMediaEntry.next;
/*     */     }
/* 369 */     if (i == 0) {
/* 370 */       return null;
/*     */     }
/* 372 */     Object[] arrayOfObject = new Object[i];
/* 373 */     localMediaEntry = this.head;
/* 374 */     i = 0;
/* 375 */     while (localMediaEntry != null) {
/* 376 */       if ((localMediaEntry.getStatus(false, false) & 0x4) != 0) {
/* 377 */         arrayOfObject[(i++)] = localMediaEntry.getMedia();
/*     */       }
/* 379 */       localMediaEntry = localMediaEntry.next;
/*     */     }
/* 381 */     return arrayOfObject;
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
/*     */   public void waitForAll()
/*     */     throws InterruptedException
/*     */   {
/* 401 */     waitForAll(0L);
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
/*     */   public synchronized boolean waitForAll(long paramLong)
/*     */     throws InterruptedException
/*     */   {
/* 428 */     long l1 = System.currentTimeMillis() + paramLong;
/* 429 */     boolean bool = true;
/*     */     for (;;) {
/* 431 */       int i = statusAll(bool, bool);
/* 432 */       if ((i & 0x1) == 0) {
/* 433 */         return i == 8;
/*     */       }
/* 435 */       bool = false;
/*     */       long l2;
/* 437 */       if (paramLong == 0L) {
/* 438 */         l2 = 0L;
/*     */       } else {
/* 440 */         l2 = l1 - System.currentTimeMillis();
/* 441 */         if (l2 <= 0L) {
/* 442 */           return false;
/*     */         }
/*     */       }
/* 445 */       wait(l2);
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
/*     */   public int statusAll(boolean paramBoolean)
/*     */   {
/* 473 */     return statusAll(paramBoolean, true);
/*     */   }
/*     */   
/*     */   private synchronized int statusAll(boolean paramBoolean1, boolean paramBoolean2) {
/* 477 */     MediaEntry localMediaEntry = this.head;
/* 478 */     int i = 0;
/* 479 */     while (localMediaEntry != null) {
/* 480 */       i |= localMediaEntry.getStatus(paramBoolean1, paramBoolean2);
/* 481 */       localMediaEntry = localMediaEntry.next;
/*     */     }
/* 483 */     return i;
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
/*     */   public boolean checkID(int paramInt)
/*     */   {
/* 507 */     return checkID(paramInt, false, true);
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
/*     */   public boolean checkID(int paramInt, boolean paramBoolean)
/*     */   {
/* 534 */     return checkID(paramInt, paramBoolean, true);
/*     */   }
/*     */   
/*     */   private synchronized boolean checkID(int paramInt, boolean paramBoolean1, boolean paramBoolean2)
/*     */   {
/* 539 */     MediaEntry localMediaEntry = this.head;
/* 540 */     boolean bool = true;
/* 541 */     while (localMediaEntry != null) {
/* 542 */       if ((localMediaEntry.getID() == paramInt) && 
/* 543 */         ((localMediaEntry.getStatus(paramBoolean1, paramBoolean2) & 0xE) == 0))
/*     */       {
/* 545 */         bool = false;
/*     */       }
/* 547 */       localMediaEntry = localMediaEntry.next;
/*     */     }
/* 549 */     return bool;
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
/*     */   public synchronized boolean isErrorID(int paramInt)
/*     */   {
/* 563 */     MediaEntry localMediaEntry = this.head;
/* 564 */     while (localMediaEntry != null) {
/* 565 */       if ((localMediaEntry.getID() == paramInt) && 
/* 566 */         ((localMediaEntry.getStatus(false, true) & 0x4) != 0))
/*     */       {
/* 568 */         return true;
/*     */       }
/* 570 */       localMediaEntry = localMediaEntry.next;
/*     */     }
/* 572 */     return false;
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
/*     */   public synchronized Object[] getErrorsID(int paramInt)
/*     */   {
/* 588 */     MediaEntry localMediaEntry = this.head;
/* 589 */     int i = 0;
/* 590 */     while (localMediaEntry != null) {
/* 591 */       if ((localMediaEntry.getID() == paramInt) && 
/* 592 */         ((localMediaEntry.getStatus(false, true) & 0x4) != 0))
/*     */       {
/* 594 */         i++;
/*     */       }
/* 596 */       localMediaEntry = localMediaEntry.next;
/*     */     }
/* 598 */     if (i == 0) {
/* 599 */       return null;
/*     */     }
/* 601 */     Object[] arrayOfObject = new Object[i];
/* 602 */     localMediaEntry = this.head;
/* 603 */     i = 0;
/* 604 */     while (localMediaEntry != null) {
/* 605 */       if ((localMediaEntry.getID() == paramInt) && 
/* 606 */         ((localMediaEntry.getStatus(false, false) & 0x4) != 0))
/*     */       {
/* 608 */         arrayOfObject[(i++)] = localMediaEntry.getMedia();
/*     */       }
/* 610 */       localMediaEntry = localMediaEntry.next;
/*     */     }
/* 612 */     return arrayOfObject;
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
/*     */   public void waitForID(int paramInt)
/*     */     throws InterruptedException
/*     */   {
/* 632 */     waitForID(paramInt, 0L);
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
/*     */   public synchronized boolean waitForID(int paramInt, long paramLong)
/*     */     throws InterruptedException
/*     */   {
/* 660 */     long l1 = System.currentTimeMillis() + paramLong;
/* 661 */     boolean bool = true;
/*     */     for (;;) {
/* 663 */       int i = statusID(paramInt, bool, bool);
/* 664 */       if ((i & 0x1) == 0) {
/* 665 */         return i == 8;
/*     */       }
/* 667 */       bool = false;
/*     */       long l2;
/* 669 */       if (paramLong == 0L) {
/* 670 */         l2 = 0L;
/*     */       } else {
/* 672 */         l2 = l1 - System.currentTimeMillis();
/* 673 */         if (l2 <= 0L) {
/* 674 */           return false;
/*     */         }
/*     */       }
/* 677 */       wait(l2);
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
/*     */   public int statusID(int paramInt, boolean paramBoolean)
/*     */   {
/* 707 */     return statusID(paramInt, paramBoolean, true);
/*     */   }
/*     */   
/*     */   private synchronized int statusID(int paramInt, boolean paramBoolean1, boolean paramBoolean2) {
/* 711 */     MediaEntry localMediaEntry = this.head;
/* 712 */     int i = 0;
/* 713 */     while (localMediaEntry != null) {
/* 714 */       if (localMediaEntry.getID() == paramInt) {
/* 715 */         i |= localMediaEntry.getStatus(paramBoolean1, paramBoolean2);
/*     */       }
/* 717 */       localMediaEntry = localMediaEntry.next;
/*     */     }
/* 719 */     return i;
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
/*     */   public synchronized void removeImage(Image paramImage)
/*     */   {
/* 732 */     removeImageImpl(paramImage);
/* 733 */     Image localImage = getResolutionVariant(paramImage);
/* 734 */     if (localImage != null) {
/* 735 */       removeImageImpl(localImage);
/*     */     }
/* 737 */     notifyAll();
/*     */   }
/*     */   
/*     */   private void removeImageImpl(Image paramImage) {
/* 741 */     Object localObject1 = this.head;
/* 742 */     Object localObject2 = null;
/* 743 */     while (localObject1 != null) {
/* 744 */       MediaEntry localMediaEntry = ((MediaEntry)localObject1).next;
/* 745 */       if (((MediaEntry)localObject1).getMedia() == paramImage) {
/* 746 */         if (localObject2 == null) {
/* 747 */           this.head = localMediaEntry;
/*     */         } else {
/* 749 */           ((MediaEntry)localObject2).next = localMediaEntry;
/*     */         }
/* 751 */         ((MediaEntry)localObject1).cancel();
/*     */       } else {
/* 753 */         localObject2 = localObject1;
/*     */       }
/* 755 */       localObject1 = localMediaEntry;
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
/*     */   public synchronized void removeImage(Image paramImage, int paramInt)
/*     */   {
/* 771 */     removeImageImpl(paramImage, paramInt);
/* 772 */     Image localImage = getResolutionVariant(paramImage);
/* 773 */     if (localImage != null) {
/* 774 */       removeImageImpl(localImage, paramInt);
/*     */     }
/* 776 */     notifyAll();
/*     */   }
/*     */   
/*     */   private void removeImageImpl(Image paramImage, int paramInt) {
/* 780 */     Object localObject1 = this.head;
/* 781 */     Object localObject2 = null;
/* 782 */     while (localObject1 != null) {
/* 783 */       MediaEntry localMediaEntry = ((MediaEntry)localObject1).next;
/* 784 */       if ((((MediaEntry)localObject1).getID() == paramInt) && (((MediaEntry)localObject1).getMedia() == paramImage)) {
/* 785 */         if (localObject2 == null) {
/* 786 */           this.head = localMediaEntry;
/*     */         } else {
/* 788 */           ((MediaEntry)localObject2).next = localMediaEntry;
/*     */         }
/* 790 */         ((MediaEntry)localObject1).cancel();
/*     */       } else {
/* 792 */         localObject2 = localObject1;
/*     */       }
/* 794 */       localObject1 = localMediaEntry;
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
/*     */   public synchronized void removeImage(Image paramImage, int paramInt1, int paramInt2, int paramInt3)
/*     */   {
/* 812 */     removeImageImpl(paramImage, paramInt1, paramInt2, paramInt3);
/* 813 */     Image localImage = getResolutionVariant(paramImage);
/* 814 */     if (localImage != null) {
/* 815 */       removeImageImpl(localImage, paramInt1, paramInt2 == -1 ? -1 : 2 * paramInt2, paramInt3 == -1 ? -1 : 2 * paramInt3);
/*     */     }
/*     */     
/*     */ 
/* 819 */     notifyAll();
/*     */   }
/*     */   
/*     */   private void removeImageImpl(Image paramImage, int paramInt1, int paramInt2, int paramInt3) {
/* 823 */     Object localObject1 = this.head;
/* 824 */     Object localObject2 = null;
/* 825 */     while (localObject1 != null) {
/* 826 */       MediaEntry localMediaEntry = ((MediaEntry)localObject1).next;
/* 827 */       if ((((MediaEntry)localObject1).getID() == paramInt1) && ((localObject1 instanceof ImageMediaEntry)) && 
/* 828 */         (((ImageMediaEntry)localObject1).matches(paramImage, paramInt2, paramInt3)))
/*     */       {
/* 830 */         if (localObject2 == null) {
/* 831 */           this.head = localMediaEntry;
/*     */         } else {
/* 833 */           ((MediaEntry)localObject2).next = localMediaEntry;
/*     */         }
/* 835 */         ((MediaEntry)localObject1).cancel();
/*     */       } else {
/* 837 */         localObject2 = localObject1;
/*     */       }
/* 839 */       localObject1 = localMediaEntry;
/*     */     }
/*     */   }
/*     */   
/*     */   synchronized void setDone() {
/* 844 */     notifyAll();
/*     */   }
/*     */   
/*     */   private static Image getResolutionVariant(Image paramImage) {
/* 848 */     if ((paramImage instanceof MultiResolutionToolkitImage)) {
/* 849 */       return ((MultiResolutionToolkitImage)paramImage).getResolutionVariant();
/*     */     }
/* 851 */     return null;
/*     */   }
/*     */ }


/* Location:              /Users/guanxiaoda/Documents/workspace/rt.jar!/java/awt/MediaTracker.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */