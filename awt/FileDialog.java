/*     */ package java.awt;
/*     */ 
/*     */ import java.awt.peer.FileDialogPeer;
/*     */ import java.io.File;
/*     */ import java.io.FilenameFilter;
/*     */ import java.io.IOException;
/*     */ import java.io.ObjectInputStream;
/*     */ import sun.awt.AWTAccessor;
/*     */ import sun.awt.AWTAccessor.FileDialogAccessor;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class FileDialog
/*     */   extends Dialog
/*     */ {
/*     */   public static final int LOAD = 0;
/*     */   public static final int SAVE = 1;
/*     */   int mode;
/*     */   String dir;
/*     */   String file;
/*     */   private File[] files;
/* 115 */   private boolean multipleMode = false;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   FilenameFilter filter;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   private static final String base = "filedlg";
/*     */   
/*     */ 
/*     */ 
/*     */ 
/* 131 */   private static int nameCounter = 0;
/*     */   
/*     */ 
/*     */ 
/*     */   private static final long serialVersionUID = 5035145889651310422L;
/*     */   
/*     */ 
/*     */ 
/*     */   static
/*     */   {
/* 141 */     Toolkit.loadLibraries();
/* 142 */     if (!GraphicsEnvironment.isHeadless()) {
/* 143 */       initIDs();
/*     */     }
/*     */     
/*     */ 
/*     */ 
/* 148 */     AWTAccessor.setFileDialogAccessor(new AWTAccessor.FileDialogAccessor()
/*     */     {
/*     */       public void setFiles(FileDialog paramAnonymousFileDialog, File[] paramAnonymousArrayOfFile) {
/* 151 */         paramAnonymousFileDialog.setFiles(paramAnonymousArrayOfFile);
/*     */       }
/*     */       
/* 154 */       public void setFile(FileDialog paramAnonymousFileDialog, String paramAnonymousString) { paramAnonymousFileDialog.file = ("".equals(paramAnonymousString) ? null : paramAnonymousString); }
/*     */       
/*     */       public void setDirectory(FileDialog paramAnonymousFileDialog, String paramAnonymousString) {
/* 157 */         paramAnonymousFileDialog.dir = ("".equals(paramAnonymousString) ? null : paramAnonymousString);
/*     */       }
/*     */       
/*     */       /* Error */
/*     */       public boolean isMultipleMode(FileDialog paramAnonymousFileDialog)
/*     */       {
/*     */         // Byte code:
/*     */         //   0: aload_1
/*     */         //   1: invokevirtual 7	java/awt/FileDialog:getObjectLock	()Ljava/lang/Object;
/*     */         //   4: dup
/*     */         //   5: astore_2
/*     */         //   6: monitorenter
/*     */         //   7: aload_1
/*     */         //   8: invokestatic 8	java/awt/FileDialog:access$100	(Ljava/awt/FileDialog;)Z
/*     */         //   11: aload_2
/*     */         //   12: monitorexit
/*     */         //   13: ireturn
/*     */         //   14: astore_3
/*     */         //   15: aload_2
/*     */         //   16: monitorexit
/*     */         //   17: aload_3
/*     */         //   18: athrow
/*     */         // Line number table:
/*     */         //   Java source line #160	-> byte code offset #0
/*     */         //   Java source line #161	-> byte code offset #7
/*     */         //   Java source line #162	-> byte code offset #14
/*     */         // Local variable table:
/*     */         //   start	length	slot	name	signature
/*     */         //   0	19	0	this	1
/*     */         //   0	19	1	paramAnonymousFileDialog	FileDialog
/*     */         //   5	11	2	Ljava/lang/Object;	Object
/*     */         //   14	4	3	localObject1	Object
/*     */         // Exception table:
/*     */         //   from	to	target	type
/*     */         //   7	13	14	finally
/*     */         //   14	17	14	finally
/*     */       }
/*     */     });
/*     */   }
/*     */   
/*     */   public FileDialog(Frame paramFrame)
/*     */   {
/* 182 */     this(paramFrame, "", 0);
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
/*     */   public FileDialog(Frame paramFrame, String paramString)
/*     */   {
/* 195 */     this(paramFrame, paramString, 0);
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
/*     */   public FileDialog(Frame paramFrame, String paramString, int paramInt)
/*     */   {
/* 218 */     super(paramFrame, paramString, true);
/* 219 */     setMode(paramInt);
/* 220 */     setLayout(null);
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
/*     */   public FileDialog(Dialog paramDialog)
/*     */   {
/* 240 */     this(paramDialog, "", 0);
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
/*     */   public FileDialog(Dialog paramDialog, String paramString)
/*     */   {
/* 264 */     this(paramDialog, paramString, 0);
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
/*     */   public FileDialog(Dialog paramDialog, String paramString, int paramInt)
/*     */   {
/* 298 */     super(paramDialog, paramString, true);
/* 299 */     setMode(paramInt);
/* 300 */     setLayout(null);
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
/*     */   public void addNotify()
/*     */   {
/* 318 */     synchronized (getTreeLock()) {
/* 319 */       if ((this.parent != null) && (this.parent.getPeer() == null)) {
/* 320 */         this.parent.addNotify();
/*     */       }
/* 322 */       if (this.peer == null)
/* 323 */         this.peer = getToolkit().createFileDialog(this);
/* 324 */       super.addNotify();
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
/*     */   public int getMode()
/*     */   {
/* 340 */     return this.mode;
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
/*     */   public void setMode(int paramInt)
/*     */   {
/* 359 */     switch (paramInt) {
/*     */     case 0: 
/*     */     case 1: 
/* 362 */       this.mode = paramInt;
/* 363 */       break;
/*     */     default: 
/* 365 */       throw new IllegalArgumentException("illegal file dialog mode");
/*     */     }
/*     */     
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public String getDirectory()
/*     */   {
/* 377 */     return this.dir;
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
/*     */   public void setDirectory(String paramString)
/*     */   {
/* 395 */     this.dir = ((paramString != null) && (paramString.equals("")) ? null : paramString);
/* 396 */     FileDialogPeer localFileDialogPeer = (FileDialogPeer)this.peer;
/* 397 */     if (localFileDialogPeer != null) {
/* 398 */       localFileDialogPeer.setDirectory(this.dir);
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
/*     */   public String getFile()
/*     */   {
/* 411 */     return this.file;
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
/*     */   public File[] getFiles()
/*     */   {
/* 427 */     synchronized (getObjectLock()) {
/* 428 */       if (this.files != null) {
/* 429 */         return (File[])this.files.clone();
/*     */       }
/* 431 */       return new File[0];
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
/*     */   private void setFiles(File[] paramArrayOfFile)
/*     */   {
/* 449 */     synchronized (getObjectLock()) {
/* 450 */       this.files = paramArrayOfFile;
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
/*     */   public void setFile(String paramString)
/*     */   {
/* 475 */     this.file = ((paramString != null) && (paramString.equals("")) ? null : paramString);
/* 476 */     FileDialogPeer localFileDialogPeer = (FileDialogPeer)this.peer;
/* 477 */     if (localFileDialogPeer != null) {
/* 478 */       localFileDialogPeer.setFile(this.file);
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
/*     */   public void setMultipleMode(boolean paramBoolean)
/*     */   {
/* 491 */     synchronized (getObjectLock()) {
/* 492 */       this.multipleMode = paramBoolean;
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
/*     */   public FilenameFilter getFilenameFilter()
/*     */   {
/* 521 */     return this.filter;
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
/*     */   public synchronized void setFilenameFilter(FilenameFilter paramFilenameFilter)
/*     */   {
/* 535 */     this.filter = paramFilenameFilter;
/* 536 */     FileDialogPeer localFileDialogPeer = (FileDialogPeer)this.peer;
/* 537 */     if (localFileDialogPeer != null) {
/* 538 */       localFileDialogPeer.setFilenameFilter(paramFilenameFilter);
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
/*     */   private void readObject(ObjectInputStream paramObjectInputStream)
/*     */     throws ClassNotFoundException, IOException
/*     */   {
/* 553 */     paramObjectInputStream.defaultReadObject();
/*     */     
/*     */ 
/* 556 */     if ((this.dir != null) && (this.dir.equals(""))) {
/* 557 */       this.dir = null;
/*     */     }
/* 559 */     if ((this.file != null) && (this.file.equals(""))) {
/* 560 */       this.file = null;
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
/*     */   protected String paramString()
/*     */   {
/* 574 */     String str = super.paramString();
/* 575 */     str = str + ",dir= " + this.dir;
/* 576 */     str = str + ",file= " + this.file;
/* 577 */     return str + (this.mode == 0 ? ",load" : ",save");
/*     */   }
/*     */   
/*     */   boolean postsOldMouseEvents() {
/* 581 */     return false;
/*     */   }
/*     */   
/*     */   private static native void initIDs();
/*     */   
/*     */   /* Error */
/*     */   String constructComponentName()
/*     */   {
/*     */     // Byte code:
/*     */     //   0: ldc 4
/*     */     //   2: dup
/*     */     //   3: astore_1
/*     */     //   4: monitorenter
/*     */     //   5: new 11	java/lang/StringBuilder
/*     */     //   8: dup
/*     */     //   9: invokespecial 12	java/lang/StringBuilder:<init>	()V
/*     */     //   12: ldc 13
/*     */     //   14: invokevirtual 14	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
/*     */     //   17: getstatic 15	java/awt/FileDialog:nameCounter	I
/*     */     //   20: dup
/*     */     //   21: iconst_1
/*     */     //   22: iadd
/*     */     //   23: putstatic 15	java/awt/FileDialog:nameCounter	I
/*     */     //   26: invokevirtual 16	java/lang/StringBuilder:append	(I)Ljava/lang/StringBuilder;
/*     */     //   29: invokevirtual 17	java/lang/StringBuilder:toString	()Ljava/lang/String;
/*     */     //   32: aload_1
/*     */     //   33: monitorexit
/*     */     //   34: areturn
/*     */     //   35: astore_2
/*     */     //   36: aload_1
/*     */     //   37: monitorexit
/*     */     //   38: aload_2
/*     */     //   39: athrow
/*     */     // Line number table:
/*     */     //   Java source line #308	-> byte code offset #0
/*     */     //   Java source line #309	-> byte code offset #5
/*     */     //   Java source line #310	-> byte code offset #35
/*     */     // Local variable table:
/*     */     //   start	length	slot	name	signature
/*     */     //   0	40	0	this	FileDialog
/*     */     //   3	34	1	Ljava/lang/Object;	Object
/*     */     //   35	4	2	localObject1	Object
/*     */     // Exception table:
/*     */     //   from	to	target	type
/*     */     //   5	34	35	finally
/*     */     //   35	38	35	finally
/*     */   }
/*     */   
/*     */   /* Error */
/*     */   public boolean isMultipleMode()
/*     */   {
/*     */     // Byte code:
/*     */     //   0: aload_0
/*     */     //   1: invokevirtual 35	java/awt/FileDialog:getObjectLock	()Ljava/lang/Object;
/*     */     //   4: dup
/*     */     //   5: astore_1
/*     */     //   6: monitorenter
/*     */     //   7: aload_0
/*     */     //   8: getfield 1	java/awt/FileDialog:multipleMode	Z
/*     */     //   11: aload_1
/*     */     //   12: monitorexit
/*     */     //   13: ireturn
/*     */     //   14: astore_2
/*     */     //   15: aload_1
/*     */     //   16: monitorexit
/*     */     //   17: aload_2
/*     */     //   18: athrow
/*     */     // Line number table:
/*     */     //   Java source line #505	-> byte code offset #0
/*     */     //   Java source line #506	-> byte code offset #7
/*     */     //   Java source line #507	-> byte code offset #14
/*     */     // Local variable table:
/*     */     //   start	length	slot	name	signature
/*     */     //   0	19	0	this	FileDialog
/*     */     //   5	11	1	Ljava/lang/Object;	Object
/*     */     //   14	4	2	localObject1	Object
/*     */     // Exception table:
/*     */     //   from	to	target	type
/*     */     //   7	13	14	finally
/*     */     //   14	17	14	finally
/*     */   }
/*     */ }


/* Location:              /Users/guanxiaoda/Documents/workspace/rt.jar!/java/awt/FileDialog.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */