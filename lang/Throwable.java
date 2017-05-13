/*      */ package java.lang;
/*      */ 
/*      */ import java.io.IOException;
/*      */ import java.io.ObjectInputStream;
/*      */ import java.io.PrintStream;
/*      */ import java.io.PrintWriter;
/*      */ import java.io.Serializable;
/*      */ import java.util.ArrayList;
/*      */ import java.util.Collections;
/*      */ import java.util.IdentityHashMap;
/*      */ import java.util.List;
/*      */ import java.util.Objects;
/*      */ import java.util.Set;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class Throwable
/*      */   implements Serializable
/*      */ {
/*      */   private static final long serialVersionUID = -3042686055658047285L;
/*      */   private transient Object backtrace;
/*      */   private String detailMessage;
/*      */   
/*      */   private static class SentinelHolder
/*      */   {
/*  145 */     public static final StackTraceElement STACK_TRACE_ELEMENT_SENTINEL = new StackTraceElement("", "", null, Integer.MIN_VALUE);
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  152 */     public static final StackTraceElement[] STACK_TRACE_SENTINEL = { STACK_TRACE_ELEMENT_SENTINEL };
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  159 */   private static final StackTraceElement[] UNASSIGNED_STACK = new StackTraceElement[0];
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  197 */   private Throwable cause = this;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  210 */   private StackTraceElement[] stackTrace = UNASSIGNED_STACK;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*  215 */   private static final List<Throwable> SUPPRESSED_SENTINEL = Collections.unmodifiableList(new ArrayList(0));
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  227 */   private List<Throwable> suppressedExceptions = SUPPRESSED_SENTINEL;
/*      */   
/*      */ 
/*      */ 
/*      */   private static final String NULL_CAUSE_MESSAGE = "Cannot suppress a null exception.";
/*      */   
/*      */ 
/*      */ 
/*      */   private static final String SELF_SUPPRESSION_MESSAGE = "Self-suppression not permitted";
/*      */   
/*      */ 
/*      */ 
/*      */   private static final String CAUSE_CAPTION = "Caused by: ";
/*      */   
/*      */ 
/*      */ 
/*      */   private static final String SUPPRESSED_CAPTION = "Suppressed: ";
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public Throwable()
/*      */   {
/*  250 */     fillInStackTrace();
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public Throwable(String paramString)
/*      */   {
/*  265 */     fillInStackTrace();
/*  266 */     this.detailMessage = paramString;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public Throwable(String paramString, Throwable paramThrowable)
/*      */   {
/*  287 */     fillInStackTrace();
/*  288 */     this.detailMessage = paramString;
/*  289 */     this.cause = paramThrowable;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public Throwable(Throwable paramThrowable)
/*      */   {
/*  310 */     fillInStackTrace();
/*  311 */     this.detailMessage = (paramThrowable == null ? null : paramThrowable.toString());
/*  312 */     this.cause = paramThrowable;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   protected Throwable(String paramString, Throwable paramThrowable, boolean paramBoolean1, boolean paramBoolean2)
/*      */   {
/*  359 */     if (paramBoolean2) {
/*  360 */       fillInStackTrace();
/*      */     } else {
/*  362 */       this.stackTrace = null;
/*      */     }
/*  364 */     this.detailMessage = paramString;
/*  365 */     this.cause = paramThrowable;
/*  366 */     if (!paramBoolean1) {
/*  367 */       this.suppressedExceptions = null;
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public String getMessage()
/*      */   {
/*  377 */     return this.detailMessage;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public String getLocalizedMessage()
/*      */   {
/*  391 */     return getMessage();
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public synchronized Throwable getCause()
/*      */   {
/*  415 */     return this.cause == this ? null : this.cause;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public synchronized Throwable initCause(Throwable paramThrowable)
/*      */   {
/*  455 */     if (this.cause != this)
/*      */     {
/*  457 */       throw new IllegalStateException("Can't overwrite cause with " + Objects.toString(paramThrowable, "a null"), this); }
/*  458 */     if (paramThrowable == this)
/*  459 */       throw new IllegalArgumentException("Self-causation not permitted", this);
/*  460 */     this.cause = paramThrowable;
/*  461 */     return this;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public String toString()
/*      */   {
/*  479 */     String str1 = getClass().getName();
/*  480 */     String str2 = getLocalizedMessage();
/*  481 */     return str2 != null ? str1 + ": " + str2 : str1;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public void printStackTrace()
/*      */   {
/*  634 */     printStackTrace(System.err);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public void printStackTrace(PrintStream paramPrintStream)
/*      */   {
/*  643 */     printStackTrace(new WrappedPrintStream(paramPrintStream));
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   private void printStackTrace(PrintStreamOrWriter paramPrintStreamOrWriter)
/*      */   {
/*  650 */     Set localSet = Collections.newSetFromMap(new IdentityHashMap());
/*  651 */     localSet.add(this);
/*      */     
/*  653 */     synchronized (paramPrintStreamOrWriter.lock())
/*      */     {
/*  655 */       paramPrintStreamOrWriter.println(this);
/*  656 */       StackTraceElement[] arrayOfStackTraceElement = getOurStackTrace();
/*  657 */       Object localObject2; for (localObject2 : arrayOfStackTraceElement) {
/*  658 */         paramPrintStreamOrWriter.println("\tat " + localObject2);
/*      */       }
/*      */       
/*  661 */       for (localObject2 : getSuppressed()) {
/*  662 */         ((Throwable)localObject2).printEnclosedStackTrace(paramPrintStreamOrWriter, arrayOfStackTraceElement, "Suppressed: ", "\t", localSet);
/*      */       }
/*      */       
/*  665 */       ??? = getCause();
/*  666 */       if (??? != null) {
/*  667 */         ((Throwable)???).printEnclosedStackTrace(paramPrintStreamOrWriter, arrayOfStackTraceElement, "Caused by: ", "", localSet);
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private void printEnclosedStackTrace(PrintStreamOrWriter paramPrintStreamOrWriter, StackTraceElement[] paramArrayOfStackTraceElement, String paramString1, String paramString2, Set<Throwable> paramSet)
/*      */   {
/*  680 */     assert (Thread.holdsLock(paramPrintStreamOrWriter.lock()));
/*  681 */     if (paramSet.contains(this)) {
/*  682 */       paramPrintStreamOrWriter.println("\t[CIRCULAR REFERENCE:" + this + "]");
/*      */     } else {
/*  684 */       paramSet.add(this);
/*      */       
/*  686 */       StackTraceElement[] arrayOfStackTraceElement = getOurStackTrace();
/*  687 */       int i = arrayOfStackTraceElement.length - 1;
/*  688 */       for (int j = paramArrayOfStackTraceElement.length - 1; 
/*  689 */           (i >= 0) && (j >= 0) && (arrayOfStackTraceElement[i].equals(paramArrayOfStackTraceElement[j])); 
/*  690 */           j--) { i--;
/*      */       }
/*  692 */       int k = arrayOfStackTraceElement.length - 1 - i;
/*      */       
/*      */ 
/*  695 */       paramPrintStreamOrWriter.println(paramString2 + paramString1 + this);
/*  696 */       for (int m = 0; m <= i; m++)
/*  697 */         paramPrintStreamOrWriter.println(paramString2 + "\tat " + arrayOfStackTraceElement[m]);
/*  698 */       if (k != 0) {
/*  699 */         paramPrintStreamOrWriter.println(paramString2 + "\t... " + k + " more");
/*      */       }
/*      */       
/*  702 */       for (Object localObject2 : getSuppressed()) {
/*  703 */         ((Throwable)localObject2).printEnclosedStackTrace(paramPrintStreamOrWriter, arrayOfStackTraceElement, "Suppressed: ", paramString2 + "\t", paramSet);
/*      */       }
/*      */       
/*      */ 
/*  707 */       ??? = getCause();
/*  708 */       if (??? != null) {
/*  709 */         ((Throwable)???).printEnclosedStackTrace(paramPrintStreamOrWriter, arrayOfStackTraceElement, "Caused by: ", paramString2, paramSet);
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public void printStackTrace(PrintWriter paramPrintWriter)
/*      */   {
/*  721 */     printStackTrace(new WrappedPrintWriter(paramPrintWriter));
/*      */   }
/*      */   
/*      */ 
/*      */   private static abstract class PrintStreamOrWriter
/*      */   {
/*      */     abstract Object lock();
/*      */     
/*      */     abstract void println(Object paramObject);
/*      */   }
/*      */   
/*      */   private static class WrappedPrintStream
/*      */     extends Throwable.PrintStreamOrWriter
/*      */   {
/*      */     private final PrintStream printStream;
/*      */     
/*      */     WrappedPrintStream(PrintStream paramPrintStream)
/*      */     {
/*  739 */       super();
/*  740 */       this.printStream = paramPrintStream;
/*      */     }
/*      */     
/*      */     Object lock() {
/*  744 */       return this.printStream;
/*      */     }
/*      */     
/*      */ 
/*  748 */     void println(Object paramObject) { this.printStream.println(paramObject); }
/*      */   }
/*      */   
/*      */   private static class WrappedPrintWriter extends Throwable.PrintStreamOrWriter {
/*      */     private final PrintWriter printWriter;
/*      */     
/*      */     WrappedPrintWriter(PrintWriter paramPrintWriter) {
/*  755 */       super();
/*  756 */       this.printWriter = paramPrintWriter;
/*      */     }
/*      */     
/*      */     Object lock() {
/*  760 */       return this.printWriter;
/*      */     }
/*      */     
/*      */     void println(Object paramObject) {
/*  764 */       this.printWriter.println(paramObject);
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public synchronized Throwable fillInStackTrace()
/*      */   {
/*  781 */     if ((this.stackTrace != null) || (this.backtrace != null))
/*      */     {
/*  783 */       fillInStackTrace(0);
/*  784 */       this.stackTrace = UNASSIGNED_STACK;
/*      */     }
/*  786 */     return this;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private native Throwable fillInStackTrace(int paramInt);
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public StackTraceElement[] getStackTrace()
/*      */   {
/*  816 */     return (StackTraceElement[])getOurStackTrace().clone();
/*      */   }
/*      */   
/*      */ 
/*      */   private synchronized StackTraceElement[] getOurStackTrace()
/*      */   {
/*  822 */     if ((this.stackTrace == UNASSIGNED_STACK) || ((this.stackTrace == null) && (this.backtrace != null)))
/*      */     {
/*  824 */       int i = getStackTraceDepth();
/*  825 */       this.stackTrace = new StackTraceElement[i];
/*  826 */       for (int j = 0; j < i; j++)
/*  827 */         this.stackTrace[j] = getStackTraceElement(j);
/*  828 */     } else if (this.stackTrace == null) {
/*  829 */       return UNASSIGNED_STACK;
/*      */     }
/*  831 */     return this.stackTrace;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public void setStackTrace(StackTraceElement[] paramArrayOfStackTraceElement)
/*      */   {
/*  864 */     StackTraceElement[] arrayOfStackTraceElement = (StackTraceElement[])paramArrayOfStackTraceElement.clone();
/*  865 */     for (int i = 0; i < arrayOfStackTraceElement.length; i++) {
/*  866 */       if (arrayOfStackTraceElement[i] == null) {
/*  867 */         throw new NullPointerException("stackTrace[" + i + "]");
/*      */       }
/*      */     }
/*  870 */     synchronized (this) {
/*  871 */       if ((this.stackTrace == null) && (this.backtrace == null))
/*      */       {
/*  873 */         return; }
/*  874 */       this.stackTrace = arrayOfStackTraceElement;
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   native int getStackTraceDepth();
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   native StackTraceElement getStackTraceElement(int paramInt);
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private void readObject(ObjectInputStream paramObjectInputStream)
/*      */     throws IOException, ClassNotFoundException
/*      */   {
/*  914 */     paramObjectInputStream.defaultReadObject();
/*  915 */     Object localObject1; if (this.suppressedExceptions != null) {
/*  916 */       localObject1 = null;
/*  917 */       if (this.suppressedExceptions.isEmpty())
/*      */       {
/*  919 */         localObject1 = SUPPRESSED_SENTINEL;
/*      */       } else {
/*  921 */         localObject1 = new ArrayList(1);
/*  922 */         for (Throwable localThrowable : this.suppressedExceptions)
/*      */         {
/*      */ 
/*  925 */           if (localThrowable == null)
/*  926 */             throw new NullPointerException("Cannot suppress a null exception.");
/*  927 */           if (localThrowable == this)
/*  928 */             throw new IllegalArgumentException("Self-suppression not permitted");
/*  929 */           ((List)localObject1).add(localThrowable);
/*      */         }
/*      */       }
/*  932 */       this.suppressedExceptions = ((List)localObject1);
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  944 */     if (this.stackTrace != null) {
/*  945 */       if (this.stackTrace.length == 0) {
/*  946 */         this.stackTrace = ((StackTraceElement[])UNASSIGNED_STACK.clone());
/*  947 */       } else { if (this.stackTrace.length == 1)
/*      */         {
/*  949 */           if (SentinelHolder.STACK_TRACE_ELEMENT_SENTINEL.equals(this.stackTrace[0])) {
/*  950 */             this.stackTrace = null; return;
/*      */           } }
/*  952 */         for (Object localObject2 : this.stackTrace) {
/*  953 */           if (localObject2 == null) {
/*  954 */             throw new NullPointerException("null StackTraceElement in serial stream. ");
/*      */           }
/*      */           
/*      */         }
/*      */         
/*      */       }
/*      */     }
/*      */     else {
/*  962 */       this.stackTrace = ((StackTraceElement[])UNASSIGNED_STACK.clone());
/*      */     }
/*      */   }
/*      */   
/*      */   /* Error */
/*      */   private synchronized void writeObject(java.io.ObjectOutputStream paramObjectOutputStream)
/*      */     throws IOException
/*      */   {
/*      */     // Byte code:
/*      */     //   0: aload_0
/*      */     //   1: invokespecial 38	java/lang/Throwable:getOurStackTrace	()[Ljava/lang/StackTraceElement;
/*      */     //   4: pop
/*      */     //   5: aload_0
/*      */     //   6: getfield 4	java/lang/Throwable:stackTrace	[Ljava/lang/StackTraceElement;
/*      */     //   9: astore_2
/*      */     //   10: aload_0
/*      */     //   11: getfield 4	java/lang/Throwable:stackTrace	[Ljava/lang/StackTraceElement;
/*      */     //   14: ifnonnull +10 -> 24
/*      */     //   17: aload_0
/*      */     //   18: getstatic 88	java/lang/Throwable$SentinelHolder:STACK_TRACE_SENTINEL	[Ljava/lang/StackTraceElement;
/*      */     //   21: putfield 4	java/lang/Throwable:stackTrace	[Ljava/lang/StackTraceElement;
/*      */     //   24: aload_1
/*      */     //   25: invokevirtual 89	java/io/ObjectOutputStream:defaultWriteObject	()V
/*      */     //   28: aload_0
/*      */     //   29: aload_2
/*      */     //   30: putfield 4	java/lang/Throwable:stackTrace	[Ljava/lang/StackTraceElement;
/*      */     //   33: goto +11 -> 44
/*      */     //   36: astore_3
/*      */     //   37: aload_0
/*      */     //   38: aload_2
/*      */     //   39: putfield 4	java/lang/Throwable:stackTrace	[Ljava/lang/StackTraceElement;
/*      */     //   42: aload_3
/*      */     //   43: athrow
/*      */     //   44: return
/*      */     // Line number table:
/*      */     //   Java source line #979	-> byte code offset #0
/*      */     //   Java source line #981	-> byte code offset #5
/*      */     //   Java source line #983	-> byte code offset #10
/*      */     //   Java source line #984	-> byte code offset #17
/*      */     //   Java source line #985	-> byte code offset #24
/*      */     //   Java source line #987	-> byte code offset #28
/*      */     //   Java source line #988	-> byte code offset #33
/*      */     //   Java source line #987	-> byte code offset #36
/*      */     //   Java source line #989	-> byte code offset #44
/*      */     // Local variable table:
/*      */     //   start	length	slot	name	signature
/*      */     //   0	45	0	this	Throwable
/*      */     //   0	45	1	paramObjectOutputStream	java.io.ObjectOutputStream
/*      */     //   9	30	2	arrayOfStackTraceElement	StackTraceElement[]
/*      */     //   36	7	3	localObject	Object
/*      */     // Exception table:
/*      */     //   from	to	target	type
/*      */     //   10	28	36	finally
/*      */   }
/*      */   
/*      */   public final synchronized void addSuppressed(Throwable paramThrowable)
/*      */   {
/* 1042 */     if (paramThrowable == this) {
/* 1043 */       throw new IllegalArgumentException("Self-suppression not permitted", paramThrowable);
/*      */     }
/* 1045 */     if (paramThrowable == null) {
/* 1046 */       throw new NullPointerException("Cannot suppress a null exception.");
/*      */     }
/* 1048 */     if (this.suppressedExceptions == null) {
/* 1049 */       return;
/*      */     }
/* 1051 */     if (this.suppressedExceptions == SUPPRESSED_SENTINEL) {
/* 1052 */       this.suppressedExceptions = new ArrayList(1);
/*      */     }
/* 1054 */     this.suppressedExceptions.add(paramThrowable);
/*      */   }
/*      */   
/* 1057 */   private static final Throwable[] EMPTY_THROWABLE_ARRAY = new Throwable[0];
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public final synchronized Throwable[] getSuppressed()
/*      */   {
/* 1075 */     if ((this.suppressedExceptions == SUPPRESSED_SENTINEL) || (this.suppressedExceptions == null))
/*      */     {
/* 1077 */       return EMPTY_THROWABLE_ARRAY;
/*      */     }
/* 1079 */     return (Throwable[])this.suppressedExceptions.toArray(EMPTY_THROWABLE_ARRAY);
/*      */   }
/*      */ }


/* Location:              /Users/guanxiaoda/Documents/workspace/rt.jar!/java/lang/Throwable.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */