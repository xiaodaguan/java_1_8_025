/*      */ package java.io;
/*      */ 
/*      */ import java.lang.ref.ReferenceQueue;
/*      */ import java.security.AccessController;
/*      */ import java.security.PrivilegedAction;
/*      */ import java.util.ArrayList;
/*      */ import java.util.Arrays;
/*      */ import java.util.List;
/*      */ import java.util.concurrent.ConcurrentHashMap;
/*      */ import java.util.concurrent.ConcurrentMap;
/*      */ import sun.reflect.misc.ReflectUtil;
/*      */ import sun.security.action.GetBooleanAction;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class ObjectOutputStream
/*      */   extends OutputStream
/*      */   implements ObjectOutput, ObjectStreamConstants
/*      */ {
/*      */   private final BlockDataOutputStream bout;
/*      */   private final HandleTable handles;
/*      */   private final ReplaceTable subs;
/*      */   
/*      */   private static class Caches
/*      */   {
/*  168 */     static final ConcurrentMap<ObjectStreamClass.WeakClassKey, Boolean> subclassAudits = new ConcurrentHashMap();
/*      */     
/*      */ 
/*      */ 
/*  172 */     static final ReferenceQueue<Class<?>> subclassAuditsQueue = new ReferenceQueue();
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
/*  183 */   private int protocol = 2;
/*      */   
/*      */ 
/*      */ 
/*      */   private int depth;
/*      */   
/*      */ 
/*      */ 
/*      */   private byte[] primVals;
/*      */   
/*      */ 
/*      */ 
/*      */   private final boolean enableOverride;
/*      */   
/*      */ 
/*      */ 
/*      */   private boolean enableReplace;
/*      */   
/*      */ 
/*      */ 
/*      */   private SerialCallbackContext curContext;
/*      */   
/*      */ 
/*      */ 
/*      */   private PutFieldImpl curPut;
/*      */   
/*      */ 
/*      */   private final DebugTraceInfoStack debugInfoStack;
/*      */   
/*      */ 
/*  213 */   private static final boolean extendedDebugInfo = ((Boolean)AccessController.doPrivileged(new GetBooleanAction("sun.io.serialization.extendedDebugInfo")))
/*      */   
/*  215 */     .booleanValue();
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public ObjectOutputStream(OutputStream paramOutputStream)
/*      */     throws IOException
/*      */   {
/*  241 */     verifySubclass();
/*  242 */     this.bout = new BlockDataOutputStream(paramOutputStream);
/*  243 */     this.handles = new HandleTable(10, 3.0F);
/*  244 */     this.subs = new ReplaceTable(10, 3.0F);
/*  245 */     this.enableOverride = false;
/*  246 */     writeStreamHeader();
/*  247 */     this.bout.setBlockDataMode(true);
/*  248 */     if (extendedDebugInfo) {
/*  249 */       this.debugInfoStack = new DebugTraceInfoStack();
/*      */     } else {
/*  251 */       this.debugInfoStack = null;
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   protected ObjectOutputStream()
/*      */     throws IOException, SecurityException
/*      */   {
/*  273 */     SecurityManager localSecurityManager = System.getSecurityManager();
/*  274 */     if (localSecurityManager != null) {
/*  275 */       localSecurityManager.checkPermission(SUBCLASS_IMPLEMENTATION_PERMISSION);
/*      */     }
/*  277 */     this.bout = null;
/*  278 */     this.handles = null;
/*  279 */     this.subs = null;
/*  280 */     this.enableOverride = true;
/*  281 */     this.debugInfoStack = null;
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
/*      */   public void useProtocolVersion(int paramInt)
/*      */     throws IOException
/*      */   {
/*  305 */     if (this.handles.size() != 0)
/*      */     {
/*  307 */       throw new IllegalStateException("stream non-empty");
/*      */     }
/*  309 */     switch (paramInt) {
/*      */     case 1: 
/*      */     case 2: 
/*  312 */       this.protocol = paramInt;
/*  313 */       break;
/*      */     
/*      */     default: 
/*  316 */       throw new IllegalArgumentException("unknown version: " + paramInt);
/*      */     }
/*      */     
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
/*      */   public final void writeObject(Object paramObject)
/*      */     throws IOException
/*      */   {
/*  343 */     if (this.enableOverride) {
/*  344 */       writeObjectOverride(paramObject);
/*  345 */       return;
/*      */     }
/*      */     try {
/*  348 */       writeObject0(paramObject, false);
/*      */     } catch (IOException localIOException) {
/*  350 */       if (this.depth == 0) {
/*  351 */         writeFatalException(localIOException);
/*      */       }
/*  353 */       throw localIOException;
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   protected void writeObjectOverride(Object paramObject)
/*      */     throws IOException
/*      */   {}
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public void writeUnshared(Object paramObject)
/*      */     throws IOException
/*      */   {
/*      */     try
/*      */     {
/*  415 */       writeObject0(paramObject, true);
/*      */     } catch (IOException localIOException) {
/*  417 */       if (this.depth == 0) {
/*  418 */         writeFatalException(localIOException);
/*      */       }
/*  420 */       throw localIOException;
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
/*      */   public void defaultWriteObject()
/*      */     throws IOException
/*      */   {
/*  434 */     SerialCallbackContext localSerialCallbackContext = this.curContext;
/*  435 */     if (localSerialCallbackContext == null) {
/*  436 */       throw new NotActiveException("not in call to writeObject");
/*      */     }
/*  438 */     Object localObject = localSerialCallbackContext.getObj();
/*  439 */     ObjectStreamClass localObjectStreamClass = localSerialCallbackContext.getDesc();
/*  440 */     this.bout.setBlockDataMode(false);
/*  441 */     defaultWriteFields(localObject, localObjectStreamClass);
/*  442 */     this.bout.setBlockDataMode(true);
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
/*      */   public PutField putFields()
/*      */     throws IOException
/*      */   {
/*  456 */     if (this.curPut == null) {
/*  457 */       SerialCallbackContext localSerialCallbackContext = this.curContext;
/*  458 */       if (localSerialCallbackContext == null) {
/*  459 */         throw new NotActiveException("not in call to writeObject");
/*      */       }
/*  461 */       Object localObject = localSerialCallbackContext.getObj();
/*  462 */       ObjectStreamClass localObjectStreamClass = localSerialCallbackContext.getDesc();
/*  463 */       this.curPut = new PutFieldImpl(localObjectStreamClass);
/*      */     }
/*  465 */     return this.curPut;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public void writeFields()
/*      */     throws IOException
/*      */   {
/*  478 */     if (this.curPut == null) {
/*  479 */       throw new NotActiveException("no current PutField object");
/*      */     }
/*  481 */     this.bout.setBlockDataMode(false);
/*  482 */     this.curPut.writeFields();
/*  483 */     this.bout.setBlockDataMode(true);
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
/*      */   public void reset()
/*      */     throws IOException
/*      */   {
/*  497 */     if (this.depth != 0) {
/*  498 */       throw new IOException("stream active");
/*      */     }
/*  500 */     this.bout.setBlockDataMode(false);
/*  501 */     this.bout.writeByte(121);
/*  502 */     clear();
/*  503 */     this.bout.setBlockDataMode(true);
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
/*      */   protected void annotateClass(Class<?> paramClass)
/*      */     throws IOException
/*      */   {}
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   protected void annotateProxyClass(Class<?> paramClass)
/*      */     throws IOException
/*      */   {}
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   protected Object replaceObject(Object paramObject)
/*      */     throws IOException
/*      */   {
/*  588 */     return paramObject;
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
/*      */   protected boolean enableReplaceObject(boolean paramBoolean)
/*      */     throws SecurityException
/*      */   {
/*  614 */     if (paramBoolean == this.enableReplace) {
/*  615 */       return paramBoolean;
/*      */     }
/*  617 */     if (paramBoolean) {
/*  618 */       SecurityManager localSecurityManager = System.getSecurityManager();
/*  619 */       if (localSecurityManager != null) {
/*  620 */         localSecurityManager.checkPermission(SUBSTITUTION_PERMISSION);
/*      */       }
/*      */     }
/*  623 */     this.enableReplace = paramBoolean;
/*  624 */     return !this.enableReplace;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   protected void writeStreamHeader()
/*      */     throws IOException
/*      */   {
/*  636 */     this.bout.writeShort(44269);
/*  637 */     this.bout.writeShort(5);
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
/*      */   protected void writeClassDescriptor(ObjectStreamClass paramObjectStreamClass)
/*      */     throws IOException
/*      */   {
/*  668 */     paramObjectStreamClass.writeNonProxy(this);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public void write(int paramInt)
/*      */     throws IOException
/*      */   {
/*  679 */     this.bout.write(paramInt);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public void write(byte[] paramArrayOfByte)
/*      */     throws IOException
/*      */   {
/*  690 */     this.bout.write(paramArrayOfByte, 0, paramArrayOfByte.length, false);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public void write(byte[] paramArrayOfByte, int paramInt1, int paramInt2)
/*      */     throws IOException
/*      */   {
/*  702 */     if (paramArrayOfByte == null) {
/*  703 */       throw new NullPointerException();
/*      */     }
/*  705 */     int i = paramInt1 + paramInt2;
/*  706 */     if ((paramInt1 < 0) || (paramInt2 < 0) || (i > paramArrayOfByte.length) || (i < 0)) {
/*  707 */       throw new IndexOutOfBoundsException();
/*      */     }
/*  709 */     this.bout.write(paramArrayOfByte, paramInt1, paramInt2, false);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public void flush()
/*      */     throws IOException
/*      */   {
/*  719 */     this.bout.flush();
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   protected void drain()
/*      */     throws IOException
/*      */   {
/*  730 */     this.bout.drain();
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public void close()
/*      */     throws IOException
/*      */   {
/*  740 */     flush();
/*  741 */     clear();
/*  742 */     this.bout.close();
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public void writeBoolean(boolean paramBoolean)
/*      */     throws IOException
/*      */   {
/*  753 */     this.bout.writeBoolean(paramBoolean);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public void writeByte(int paramInt)
/*      */     throws IOException
/*      */   {
/*  764 */     this.bout.writeByte(paramInt);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public void writeShort(int paramInt)
/*      */     throws IOException
/*      */   {
/*  775 */     this.bout.writeShort(paramInt);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public void writeChar(int paramInt)
/*      */     throws IOException
/*      */   {
/*  786 */     this.bout.writeChar(paramInt);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public void writeInt(int paramInt)
/*      */     throws IOException
/*      */   {
/*  797 */     this.bout.writeInt(paramInt);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public void writeLong(long paramLong)
/*      */     throws IOException
/*      */   {
/*  808 */     this.bout.writeLong(paramLong);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public void writeFloat(float paramFloat)
/*      */     throws IOException
/*      */   {
/*  819 */     this.bout.writeFloat(paramFloat);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public void writeDouble(double paramDouble)
/*      */     throws IOException
/*      */   {
/*  830 */     this.bout.writeDouble(paramDouble);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public void writeBytes(String paramString)
/*      */     throws IOException
/*      */   {
/*  841 */     this.bout.writeBytes(paramString);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public void writeChars(String paramString)
/*      */     throws IOException
/*      */   {
/*  852 */     this.bout.writeChars(paramString);
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
/*      */   public void writeUTF(String paramString)
/*      */     throws IOException
/*      */   {
/*  869 */     this.bout.writeUTF(paramString);
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
/*      */   int getProtocolVersion()
/*      */   {
/* 1016 */     return this.protocol;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   void writeTypeString(String paramString)
/*      */     throws IOException
/*      */   {
/* 1025 */     if (paramString == null) {
/* 1026 */       writeNull(); } else { int i;
/* 1027 */       if ((i = this.handles.lookup(paramString)) != -1) {
/* 1028 */         writeHandle(i);
/*      */       } else {
/* 1030 */         writeString(paramString, false);
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private void verifySubclass()
/*      */   {
/* 1041 */     Class localClass = getClass();
/* 1042 */     if (localClass == ObjectOutputStream.class) {
/* 1043 */       return;
/*      */     }
/* 1045 */     SecurityManager localSecurityManager = System.getSecurityManager();
/* 1046 */     if (localSecurityManager == null) {
/* 1047 */       return;
/*      */     }
/* 1049 */     ObjectStreamClass.processQueue(Caches.subclassAuditsQueue, Caches.subclassAudits);
/* 1050 */     ObjectStreamClass.WeakClassKey localWeakClassKey = new ObjectStreamClass.WeakClassKey(localClass, Caches.subclassAuditsQueue);
/* 1051 */     Boolean localBoolean = (Boolean)Caches.subclassAudits.get(localWeakClassKey);
/* 1052 */     if (localBoolean == null) {
/* 1053 */       localBoolean = Boolean.valueOf(auditSubclass(localClass));
/* 1054 */       Caches.subclassAudits.putIfAbsent(localWeakClassKey, localBoolean);
/*      */     }
/* 1056 */     if (localBoolean.booleanValue()) {
/* 1057 */       return;
/*      */     }
/* 1059 */     localSecurityManager.checkPermission(SUBCLASS_IMPLEMENTATION_PERMISSION);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private static boolean auditSubclass(Class<?> paramClass)
/*      */   {
/* 1068 */     Boolean localBoolean = (Boolean)AccessController.doPrivileged(new PrivilegedAction()
/*      */     {
/*      */       public Boolean run() {
/* 1071 */         for (Class localClass = this.val$subcl; 
/* 1072 */             localClass != ObjectOutputStream.class; 
/* 1073 */             localClass = localClass.getSuperclass()) {
/*      */           try
/*      */           {
/* 1076 */             localClass.getDeclaredMethod("writeUnshared", new Class[] { Object.class });
/*      */             
/* 1078 */             return Boolean.FALSE;
/*      */           }
/*      */           catch (NoSuchMethodException localNoSuchMethodException1) {
/*      */             try {
/* 1082 */               localClass.getDeclaredMethod("putFields", (Class[])null);
/* 1083 */               return Boolean.FALSE;
/*      */             } catch (NoSuchMethodException localNoSuchMethodException2) {}
/*      */           }
/*      */         }
/* 1087 */         return Boolean.TRUE;
/*      */       }
/*      */       
/* 1090 */     });
/* 1091 */     return localBoolean.booleanValue();
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   private void clear()
/*      */   {
/* 1098 */     this.subs.clear();
/* 1099 */     this.handles.clear();
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   private void writeObject0(Object paramObject, boolean paramBoolean)
/*      */     throws IOException
/*      */   {
/* 1108 */     boolean bool = this.bout.setBlockDataMode(false);
/* 1109 */     this.depth += 1;
/*      */     
/*      */     try
/*      */     {
/* 1113 */       if ((paramObject = this.subs.lookup(paramObject)) == null) {
/* 1114 */         writeNull(); return; }
/*      */       int i;
/* 1116 */       if ((!paramBoolean) && ((i = this.handles.lookup(paramObject)) != -1)) {
/* 1117 */         writeHandle(i);
/* 1118 */         return; }
/* 1119 */       if ((paramObject instanceof Class)) {
/* 1120 */         writeClass((Class)paramObject, paramBoolean);
/* 1121 */         return; }
/* 1122 */       if ((paramObject instanceof ObjectStreamClass)) {
/* 1123 */         writeClassDesc((ObjectStreamClass)paramObject, paramBoolean);
/* 1124 */         return;
/*      */       }
/*      */       
/*      */ 
/* 1128 */       Object localObject1 = paramObject;
/* 1129 */       Object localObject2 = paramObject.getClass();
/*      */       ObjectStreamClass localObjectStreamClass;
/*      */       Object localObject3;
/*      */       for (;;)
/*      */       {
/* 1134 */         localObjectStreamClass = ObjectStreamClass.lookup((Class)localObject2, true);
/* 1135 */         if ((!localObjectStreamClass.hasWriteReplaceMethod()) || 
/* 1136 */           ((paramObject = localObjectStreamClass.invokeWriteReplace(paramObject)) == null) || 
/* 1137 */           ((localObject3 = paramObject.getClass()) == localObject2)) {
/*      */           break;
/*      */         }
/*      */         
/* 1141 */         localObject2 = localObject3;
/*      */       }
/* 1143 */       if (this.enableReplace) {
/* 1144 */         localObject3 = replaceObject(paramObject);
/* 1145 */         if ((localObject3 != paramObject) && (localObject3 != null)) {
/* 1146 */           localObject2 = localObject3.getClass();
/* 1147 */           localObjectStreamClass = ObjectStreamClass.lookup((Class)localObject2, true);
/*      */         }
/* 1149 */         paramObject = localObject3;
/*      */       }
/*      */       
/*      */ 
/* 1153 */       if (paramObject != localObject1) {
/* 1154 */         this.subs.assign(localObject1, paramObject);
/* 1155 */         if (paramObject == null) {
/* 1156 */           writeNull();
/* 1157 */           return; }
/* 1158 */         if ((!paramBoolean) && ((i = this.handles.lookup(paramObject)) != -1)) {
/* 1159 */           writeHandle(i);
/* 1160 */           return; }
/* 1161 */         if ((paramObject instanceof Class)) {
/* 1162 */           writeClass((Class)paramObject, paramBoolean);
/* 1163 */           return; }
/* 1164 */         if ((paramObject instanceof ObjectStreamClass)) {
/* 1165 */           writeClassDesc((ObjectStreamClass)paramObject, paramBoolean);
/* 1166 */           return;
/*      */         }
/*      */       }
/*      */       
/*      */ 
/* 1171 */       if ((paramObject instanceof String)) {
/* 1172 */         writeString((String)paramObject, paramBoolean);
/* 1173 */       } else if (((Class)localObject2).isArray()) {
/* 1174 */         writeArray(paramObject, localObjectStreamClass, paramBoolean);
/* 1175 */       } else if ((paramObject instanceof Enum)) {
/* 1176 */         writeEnum((Enum)paramObject, localObjectStreamClass, paramBoolean);
/* 1177 */       } else if ((paramObject instanceof Serializable)) {
/* 1178 */         writeOrdinaryObject(paramObject, localObjectStreamClass, paramBoolean);
/*      */       } else {
/* 1180 */         if (extendedDebugInfo)
/*      */         {
/* 1182 */           throw new NotSerializableException(((Class)localObject2).getName() + "\n" + this.debugInfoStack.toString());
/*      */         }
/* 1184 */         throw new NotSerializableException(((Class)localObject2).getName());
/*      */       }
/*      */     }
/*      */     finally {
/* 1188 */       this.depth -= 1;
/* 1189 */       this.bout.setBlockDataMode(bool);
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */   private void writeNull()
/*      */     throws IOException
/*      */   {
/* 1197 */     this.bout.writeByte(112);
/*      */   }
/*      */   
/*      */ 
/*      */   private void writeHandle(int paramInt)
/*      */     throws IOException
/*      */   {
/* 1204 */     this.bout.writeByte(113);
/* 1205 */     this.bout.writeInt(8257536 + paramInt);
/*      */   }
/*      */   
/*      */ 
/*      */   private void writeClass(Class<?> paramClass, boolean paramBoolean)
/*      */     throws IOException
/*      */   {
/* 1212 */     this.bout.writeByte(118);
/* 1213 */     writeClassDesc(ObjectStreamClass.lookup(paramClass, true), false);
/* 1214 */     this.handles.assign(paramBoolean ? null : paramClass);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private void writeClassDesc(ObjectStreamClass paramObjectStreamClass, boolean paramBoolean)
/*      */     throws IOException
/*      */   {
/* 1224 */     if (paramObjectStreamClass == null) {
/* 1225 */       writeNull(); } else { int i;
/* 1226 */       if ((!paramBoolean) && ((i = this.handles.lookup(paramObjectStreamClass)) != -1)) {
/* 1227 */         writeHandle(i);
/* 1228 */       } else if (paramObjectStreamClass.isProxy()) {
/* 1229 */         writeProxyDesc(paramObjectStreamClass, paramBoolean);
/*      */       } else {
/* 1231 */         writeNonProxyDesc(paramObjectStreamClass, paramBoolean);
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */   private boolean isCustomSubclass()
/*      */   {
/* 1238 */     return getClass().getClassLoader() != ObjectOutputStream.class.getClassLoader();
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   private void writeProxyDesc(ObjectStreamClass paramObjectStreamClass, boolean paramBoolean)
/*      */     throws IOException
/*      */   {
/* 1247 */     this.bout.writeByte(125);
/* 1248 */     this.handles.assign(paramBoolean ? null : paramObjectStreamClass);
/*      */     
/* 1250 */     Class localClass = paramObjectStreamClass.forClass();
/* 1251 */     Class[] arrayOfClass = localClass.getInterfaces();
/* 1252 */     this.bout.writeInt(arrayOfClass.length);
/* 1253 */     for (int i = 0; i < arrayOfClass.length; i++) {
/* 1254 */       this.bout.writeUTF(arrayOfClass[i].getName());
/*      */     }
/*      */     
/* 1257 */     this.bout.setBlockDataMode(true);
/* 1258 */     if ((localClass != null) && (isCustomSubclass())) {
/* 1259 */       ReflectUtil.checkPackageAccess(localClass);
/*      */     }
/* 1261 */     annotateProxyClass(localClass);
/* 1262 */     this.bout.setBlockDataMode(false);
/* 1263 */     this.bout.writeByte(120);
/*      */     
/* 1265 */     writeClassDesc(paramObjectStreamClass.getSuperDesc(), false);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private void writeNonProxyDesc(ObjectStreamClass paramObjectStreamClass, boolean paramBoolean)
/*      */     throws IOException
/*      */   {
/* 1275 */     this.bout.writeByte(114);
/* 1276 */     this.handles.assign(paramBoolean ? null : paramObjectStreamClass);
/*      */     
/* 1278 */     if (this.protocol == 1)
/*      */     {
/* 1280 */       paramObjectStreamClass.writeNonProxy(this);
/*      */     } else {
/* 1282 */       writeClassDescriptor(paramObjectStreamClass);
/*      */     }
/*      */     
/* 1285 */     Class localClass = paramObjectStreamClass.forClass();
/* 1286 */     this.bout.setBlockDataMode(true);
/* 1287 */     if ((localClass != null) && (isCustomSubclass())) {
/* 1288 */       ReflectUtil.checkPackageAccess(localClass);
/*      */     }
/* 1290 */     annotateClass(localClass);
/* 1291 */     this.bout.setBlockDataMode(false);
/* 1292 */     this.bout.writeByte(120);
/*      */     
/* 1294 */     writeClassDesc(paramObjectStreamClass.getSuperDesc(), false);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   private void writeString(String paramString, boolean paramBoolean)
/*      */     throws IOException
/*      */   {
/* 1302 */     this.handles.assign(paramBoolean ? null : paramString);
/* 1303 */     long l = this.bout.getUTFLength(paramString);
/* 1304 */     if (l <= 65535L) {
/* 1305 */       this.bout.writeByte(116);
/* 1306 */       this.bout.writeUTF(paramString, l);
/*      */     } else {
/* 1308 */       this.bout.writeByte(124);
/* 1309 */       this.bout.writeLongUTF(paramString, l);
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private void writeArray(Object paramObject, ObjectStreamClass paramObjectStreamClass, boolean paramBoolean)
/*      */     throws IOException
/*      */   {
/* 1321 */     this.bout.writeByte(117);
/* 1322 */     writeClassDesc(paramObjectStreamClass, false);
/* 1323 */     this.handles.assign(paramBoolean ? null : paramObject);
/*      */     
/* 1325 */     Class localClass = paramObjectStreamClass.forClass().getComponentType();
/* 1326 */     Object localObject1; if (localClass.isPrimitive()) {
/* 1327 */       if (localClass == Integer.TYPE) {
/* 1328 */         localObject1 = (int[])paramObject;
/* 1329 */         this.bout.writeInt(localObject1.length);
/* 1330 */         this.bout.writeInts((int[])localObject1, 0, localObject1.length);
/* 1331 */       } else if (localClass == Byte.TYPE) {
/* 1332 */         localObject1 = (byte[])paramObject;
/* 1333 */         this.bout.writeInt(localObject1.length);
/* 1334 */         this.bout.write((byte[])localObject1, 0, localObject1.length, true);
/* 1335 */       } else if (localClass == Long.TYPE) {
/* 1336 */         localObject1 = (long[])paramObject;
/* 1337 */         this.bout.writeInt(localObject1.length);
/* 1338 */         this.bout.writeLongs((long[])localObject1, 0, localObject1.length);
/* 1339 */       } else if (localClass == Float.TYPE) {
/* 1340 */         localObject1 = (float[])paramObject;
/* 1341 */         this.bout.writeInt(localObject1.length);
/* 1342 */         this.bout.writeFloats((float[])localObject1, 0, localObject1.length);
/* 1343 */       } else if (localClass == Double.TYPE) {
/* 1344 */         localObject1 = (double[])paramObject;
/* 1345 */         this.bout.writeInt(localObject1.length);
/* 1346 */         this.bout.writeDoubles((double[])localObject1, 0, localObject1.length);
/* 1347 */       } else if (localClass == Short.TYPE) {
/* 1348 */         localObject1 = (short[])paramObject;
/* 1349 */         this.bout.writeInt(localObject1.length);
/* 1350 */         this.bout.writeShorts((short[])localObject1, 0, localObject1.length);
/* 1351 */       } else if (localClass == Character.TYPE) {
/* 1352 */         localObject1 = (char[])paramObject;
/* 1353 */         this.bout.writeInt(localObject1.length);
/* 1354 */         this.bout.writeChars((char[])localObject1, 0, localObject1.length);
/* 1355 */       } else if (localClass == Boolean.TYPE) {
/* 1356 */         localObject1 = (boolean[])paramObject;
/* 1357 */         this.bout.writeInt(localObject1.length);
/* 1358 */         this.bout.writeBooleans((boolean[])localObject1, 0, localObject1.length);
/*      */       } else {
/* 1360 */         throw new InternalError();
/*      */       }
/*      */     } else {
/* 1363 */       localObject1 = (Object[])paramObject;
/* 1364 */       int i = localObject1.length;
/* 1365 */       this.bout.writeInt(i);
/* 1366 */       if (extendedDebugInfo) {
/* 1367 */         this.debugInfoStack.push("array (class \"" + paramObject
/* 1368 */           .getClass().getName() + "\", size: " + i + ")");
/*      */       }
/*      */       try
/*      */       {
/* 1372 */         for (int j = 0; j < i; j++) {
/* 1373 */           if (extendedDebugInfo) {
/* 1374 */             this.debugInfoStack.push("element of array (index: " + j + ")");
/*      */           }
/*      */           try
/*      */           {
/* 1378 */             writeObject0(localObject1[j], false);
/*      */ 
/*      */           }
/*      */           finally {}
/*      */         }
/*      */       }
/*      */       finally
/*      */       {
/* 1386 */         if (extendedDebugInfo) {
/* 1387 */           this.debugInfoStack.pop();
/*      */         }
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private void writeEnum(Enum<?> paramEnum, ObjectStreamClass paramObjectStreamClass, boolean paramBoolean)
/*      */     throws IOException
/*      */   {
/* 1401 */     this.bout.writeByte(126);
/* 1402 */     ObjectStreamClass localObjectStreamClass = paramObjectStreamClass.getSuperDesc();
/* 1403 */     writeClassDesc(localObjectStreamClass.forClass() == Enum.class ? paramObjectStreamClass : localObjectStreamClass, false);
/* 1404 */     this.handles.assign(paramBoolean ? null : paramEnum);
/* 1405 */     writeString(paramEnum.name(), false);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private void writeOrdinaryObject(Object paramObject, ObjectStreamClass paramObjectStreamClass, boolean paramBoolean)
/*      */     throws IOException
/*      */   {
/* 1418 */     if (extendedDebugInfo) {
/* 1419 */       this.debugInfoStack.push((this.depth == 1 ? "root " : "") + "object (class \"" + paramObject
/*      */       
/* 1421 */         .getClass().getName() + "\", " + paramObject.toString() + ")");
/*      */     }
/*      */     try {
/* 1424 */       paramObjectStreamClass.checkSerialize();
/*      */       
/* 1426 */       this.bout.writeByte(115);
/* 1427 */       writeClassDesc(paramObjectStreamClass, false);
/* 1428 */       this.handles.assign(paramBoolean ? null : paramObject);
/* 1429 */       if ((paramObjectStreamClass.isExternalizable()) && (!paramObjectStreamClass.isProxy())) {
/* 1430 */         writeExternalData((Externalizable)paramObject);
/*      */       } else {
/* 1432 */         writeSerialData(paramObject, paramObjectStreamClass);
/*      */       }
/*      */     } finally {
/* 1435 */       if (extendedDebugInfo) {
/* 1436 */         this.debugInfoStack.pop();
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   private void writeExternalData(Externalizable paramExternalizable)
/*      */     throws IOException
/*      */   {
/* 1446 */     PutFieldImpl localPutFieldImpl = this.curPut;
/* 1447 */     this.curPut = null;
/*      */     
/* 1449 */     if (extendedDebugInfo) {
/* 1450 */       this.debugInfoStack.push("writeExternal data");
/*      */     }
/* 1452 */     SerialCallbackContext localSerialCallbackContext = this.curContext;
/*      */     try {
/* 1454 */       this.curContext = null;
/* 1455 */       if (this.protocol == 1) {
/* 1456 */         paramExternalizable.writeExternal(this);
/*      */       } else {
/* 1458 */         this.bout.setBlockDataMode(true);
/* 1459 */         paramExternalizable.writeExternal(this);
/* 1460 */         this.bout.setBlockDataMode(false);
/* 1461 */         this.bout.writeByte(120);
/*      */       }
/*      */     } finally {
/* 1464 */       this.curContext = localSerialCallbackContext;
/* 1465 */       if (extendedDebugInfo) {
/* 1466 */         this.debugInfoStack.pop();
/*      */       }
/*      */     }
/*      */     
/* 1470 */     this.curPut = localPutFieldImpl;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private void writeSerialData(Object paramObject, ObjectStreamClass paramObjectStreamClass)
/*      */     throws IOException
/*      */   {
/* 1480 */     ObjectStreamClass.ClassDataSlot[] arrayOfClassDataSlot = paramObjectStreamClass.getClassDataLayout();
/* 1481 */     for (int i = 0; i < arrayOfClassDataSlot.length; i++) {
/* 1482 */       ObjectStreamClass localObjectStreamClass = arrayOfClassDataSlot[i].desc;
/* 1483 */       if (localObjectStreamClass.hasWriteObjectMethod()) {
/* 1484 */         PutFieldImpl localPutFieldImpl = this.curPut;
/* 1485 */         this.curPut = null;
/* 1486 */         SerialCallbackContext localSerialCallbackContext = this.curContext;
/*      */         
/* 1488 */         if (extendedDebugInfo) {
/* 1489 */           this.debugInfoStack.push("custom writeObject data (class \"" + localObjectStreamClass
/*      */           
/* 1491 */             .getName() + "\")");
/*      */         }
/*      */         try {
/* 1494 */           this.curContext = new SerialCallbackContext(paramObject, localObjectStreamClass);
/* 1495 */           this.bout.setBlockDataMode(true);
/* 1496 */           localObjectStreamClass.invokeWriteObject(paramObject, this);
/* 1497 */           this.bout.setBlockDataMode(false);
/* 1498 */           this.bout.writeByte(120);
/*      */         } finally {
/* 1500 */           this.curContext.setUsed();
/* 1501 */           this.curContext = localSerialCallbackContext;
/* 1502 */           if (extendedDebugInfo) {
/* 1503 */             this.debugInfoStack.pop();
/*      */           }
/*      */         }
/*      */         
/* 1507 */         this.curPut = localPutFieldImpl;
/*      */       } else {
/* 1509 */         defaultWriteFields(paramObject, localObjectStreamClass);
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private void defaultWriteFields(Object paramObject, ObjectStreamClass paramObjectStreamClass)
/*      */     throws IOException
/*      */   {
/* 1522 */     Class localClass = paramObjectStreamClass.forClass();
/* 1523 */     if ((localClass != null) && (paramObject != null) && (!localClass.isInstance(paramObject))) {
/* 1524 */       throw new ClassCastException();
/*      */     }
/*      */     
/* 1527 */     paramObjectStreamClass.checkDefaultSerialize();
/*      */     
/* 1529 */     int i = paramObjectStreamClass.getPrimDataSize();
/* 1530 */     if ((this.primVals == null) || (this.primVals.length < i)) {
/* 1531 */       this.primVals = new byte[i];
/*      */     }
/* 1533 */     paramObjectStreamClass.getPrimFieldValues(paramObject, this.primVals);
/* 1534 */     this.bout.write(this.primVals, 0, i, false);
/*      */     
/* 1536 */     ObjectStreamField[] arrayOfObjectStreamField = paramObjectStreamClass.getFields(false);
/* 1537 */     Object[] arrayOfObject = new Object[paramObjectStreamClass.getNumObjFields()];
/* 1538 */     int j = arrayOfObjectStreamField.length - arrayOfObject.length;
/* 1539 */     paramObjectStreamClass.getObjFieldValues(paramObject, arrayOfObject);
/* 1540 */     for (int k = 0; k < arrayOfObject.length; k++) {
/* 1541 */       if (extendedDebugInfo) {
/* 1542 */         this.debugInfoStack.push("field (class \"" + paramObjectStreamClass
/* 1543 */           .getName() + "\", name: \"" + arrayOfObjectStreamField[(j + k)]
/* 1544 */           .getName() + "\", type: \"" + arrayOfObjectStreamField[(j + k)]
/* 1545 */           .getType() + "\")");
/*      */       }
/*      */       try {
/* 1548 */         writeObject0(arrayOfObject[k], arrayOfObjectStreamField[(j + k)]
/* 1549 */           .isUnshared());
/*      */       } finally {
/* 1551 */         if (extendedDebugInfo) {
/* 1552 */           this.debugInfoStack.pop();
/*      */         }
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */   /* Error */
/*      */   private void writeFatalException(IOException paramIOException)
/*      */     throws IOException
/*      */   {
/*      */     // Byte code:
/*      */     //   0: aload_0
/*      */     //   1: invokespecial 59	java/io/ObjectOutputStream:clear	()V
/*      */     //   4: aload_0
/*      */     //   5: getfield 6	java/io/ObjectOutputStream:bout	Ljava/io/ObjectOutputStream$BlockDataOutputStream;
/*      */     //   8: iconst_0
/*      */     //   9: invokevirtual 22	java/io/ObjectOutputStream$BlockDataOutputStream:setBlockDataMode	(Z)Z
/*      */     //   12: istore_2
/*      */     //   13: aload_0
/*      */     //   14: getfield 6	java/io/ObjectOutputStream:bout	Ljava/io/ObjectOutputStream$BlockDataOutputStream;
/*      */     //   17: bipush 123
/*      */     //   19: invokevirtual 58	java/io/ObjectOutputStream$BlockDataOutputStream:writeByte	(I)V
/*      */     //   22: aload_0
/*      */     //   23: aload_1
/*      */     //   24: iconst_0
/*      */     //   25: invokespecial 3	java/io/ObjectOutputStream:writeObject0	(Ljava/lang/Object;Z)V
/*      */     //   28: aload_0
/*      */     //   29: invokespecial 59	java/io/ObjectOutputStream:clear	()V
/*      */     //   32: aload_0
/*      */     //   33: getfield 6	java/io/ObjectOutputStream:bout	Ljava/io/ObjectOutputStream$BlockDataOutputStream;
/*      */     //   36: iload_2
/*      */     //   37: invokevirtual 22	java/io/ObjectOutputStream$BlockDataOutputStream:setBlockDataMode	(Z)Z
/*      */     //   40: pop
/*      */     //   41: goto +15 -> 56
/*      */     //   44: astore_3
/*      */     //   45: aload_0
/*      */     //   46: getfield 6	java/io/ObjectOutputStream:bout	Ljava/io/ObjectOutputStream$BlockDataOutputStream;
/*      */     //   49: iload_2
/*      */     //   50: invokevirtual 22	java/io/ObjectOutputStream$BlockDataOutputStream:setBlockDataMode	(Z)Z
/*      */     //   53: pop
/*      */     //   54: aload_3
/*      */     //   55: athrow
/*      */     //   56: return
/*      */     // Line number table:
/*      */     //   Java source line #1573	-> byte code offset #0
/*      */     //   Java source line #1574	-> byte code offset #4
/*      */     //   Java source line #1576	-> byte code offset #13
/*      */     //   Java source line #1577	-> byte code offset #22
/*      */     //   Java source line #1578	-> byte code offset #28
/*      */     //   Java source line #1580	-> byte code offset #32
/*      */     //   Java source line #1581	-> byte code offset #41
/*      */     //   Java source line #1580	-> byte code offset #44
/*      */     //   Java source line #1582	-> byte code offset #56
/*      */     // Local variable table:
/*      */     //   start	length	slot	name	signature
/*      */     //   0	57	0	this	ObjectOutputStream
/*      */     //   0	57	1	paramIOException	IOException
/*      */     //   12	38	2	bool	boolean
/*      */     //   44	11	3	localObject	Object
/*      */     // Exception table:
/*      */     //   from	to	target	type
/*      */     //   13	32	44	finally
/*      */   }
/*      */   
/*      */   private static native void floatsToBytes(float[] paramArrayOfFloat, int paramInt1, byte[] paramArrayOfByte, int paramInt2, int paramInt3);
/*      */   
/*      */   private static native void doublesToBytes(double[] paramArrayOfDouble, int paramInt1, byte[] paramArrayOfByte, int paramInt2, int paramInt3);
/*      */   
/*      */   public static abstract class PutField
/*      */   {
/*      */     public abstract void put(String paramString, boolean paramBoolean);
/*      */     
/*      */     public abstract void put(String paramString, byte paramByte);
/*      */     
/*      */     public abstract void put(String paramString, char paramChar);
/*      */     
/*      */     public abstract void put(String paramString, short paramShort);
/*      */     
/*      */     public abstract void put(String paramString, int paramInt);
/*      */     
/*      */     public abstract void put(String paramString, long paramLong);
/*      */     
/*      */     public abstract void put(String paramString, float paramFloat);
/*      */     
/*      */     public abstract void put(String paramString, double paramDouble);
/*      */     
/*      */     public abstract void put(String paramString, Object paramObject);
/*      */     
/*      */     @Deprecated
/*      */     public abstract void write(ObjectOutput paramObjectOutput)
/*      */       throws IOException;
/*      */   }
/*      */   
/*      */   private class PutFieldImpl
/*      */     extends ObjectOutputStream.PutField
/*      */   {
/*      */     private final ObjectStreamClass desc;
/*      */     private final byte[] primVals;
/*      */     private final Object[] objVals;
/*      */     
/*      */     PutFieldImpl(ObjectStreamClass paramObjectStreamClass)
/*      */     {
/* 1617 */       this.desc = paramObjectStreamClass;
/* 1618 */       this.primVals = new byte[paramObjectStreamClass.getPrimDataSize()];
/* 1619 */       this.objVals = new Object[paramObjectStreamClass.getNumObjFields()];
/*      */     }
/*      */     
/*      */     public void put(String paramString, boolean paramBoolean) {
/* 1623 */       Bits.putBoolean(this.primVals, getFieldOffset(paramString, Boolean.TYPE), paramBoolean);
/*      */     }
/*      */     
/*      */     public void put(String paramString, byte paramByte) {
/* 1627 */       this.primVals[getFieldOffset(paramString, Byte.TYPE)] = paramByte;
/*      */     }
/*      */     
/*      */     public void put(String paramString, char paramChar) {
/* 1631 */       Bits.putChar(this.primVals, getFieldOffset(paramString, Character.TYPE), paramChar);
/*      */     }
/*      */     
/*      */     public void put(String paramString, short paramShort) {
/* 1635 */       Bits.putShort(this.primVals, getFieldOffset(paramString, Short.TYPE), paramShort);
/*      */     }
/*      */     
/*      */     public void put(String paramString, int paramInt) {
/* 1639 */       Bits.putInt(this.primVals, getFieldOffset(paramString, Integer.TYPE), paramInt);
/*      */     }
/*      */     
/*      */     public void put(String paramString, float paramFloat) {
/* 1643 */       Bits.putFloat(this.primVals, getFieldOffset(paramString, Float.TYPE), paramFloat);
/*      */     }
/*      */     
/*      */     public void put(String paramString, long paramLong) {
/* 1647 */       Bits.putLong(this.primVals, getFieldOffset(paramString, Long.TYPE), paramLong);
/*      */     }
/*      */     
/*      */     public void put(String paramString, double paramDouble) {
/* 1651 */       Bits.putDouble(this.primVals, getFieldOffset(paramString, Double.TYPE), paramDouble);
/*      */     }
/*      */     
/*      */     public void put(String paramString, Object paramObject) {
/* 1655 */       this.objVals[getFieldOffset(paramString, Object.class)] = paramObject;
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     public void write(ObjectOutput paramObjectOutput)
/*      */       throws IOException
/*      */     {
/* 1675 */       if (ObjectOutputStream.this != paramObjectOutput) {
/* 1676 */         throw new IllegalArgumentException("wrong stream");
/*      */       }
/* 1678 */       paramObjectOutput.write(this.primVals, 0, this.primVals.length);
/*      */       
/* 1680 */       ObjectStreamField[] arrayOfObjectStreamField = this.desc.getFields(false);
/* 1681 */       int i = arrayOfObjectStreamField.length - this.objVals.length;
/*      */       
/* 1683 */       for (int j = 0; j < this.objVals.length; j++) {
/* 1684 */         if (arrayOfObjectStreamField[(i + j)].isUnshared()) {
/* 1685 */           throw new IOException("cannot write unshared object");
/*      */         }
/* 1687 */         paramObjectOutput.writeObject(this.objVals[j]);
/*      */       }
/*      */     }
/*      */     
/*      */ 
/*      */     void writeFields()
/*      */       throws IOException
/*      */     {
/* 1695 */       ObjectOutputStream.this.bout.write(this.primVals, 0, this.primVals.length, false);
/*      */       
/* 1697 */       ObjectStreamField[] arrayOfObjectStreamField = this.desc.getFields(false);
/* 1698 */       int i = arrayOfObjectStreamField.length - this.objVals.length;
/* 1699 */       for (int j = 0; j < this.objVals.length; j++) {
/* 1700 */         if (ObjectOutputStream.extendedDebugInfo) {
/* 1701 */           ObjectOutputStream.this.debugInfoStack.push("field (class \"" + this.desc
/* 1702 */             .getName() + "\", name: \"" + arrayOfObjectStreamField[(i + j)]
/* 1703 */             .getName() + "\", type: \"" + arrayOfObjectStreamField[(i + j)]
/* 1704 */             .getType() + "\")");
/*      */         }
/*      */         try {
/* 1707 */           ObjectOutputStream.this.writeObject0(this.objVals[j], arrayOfObjectStreamField[(i + j)]
/* 1708 */             .isUnshared());
/*      */         } finally {
/* 1710 */           if (ObjectOutputStream.extendedDebugInfo) {
/* 1711 */             ObjectOutputStream.this.debugInfoStack.pop();
/*      */           }
/*      */         }
/*      */       }
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     private int getFieldOffset(String paramString, Class<?> paramClass)
/*      */     {
/* 1724 */       ObjectStreamField localObjectStreamField = this.desc.getField(paramString, paramClass);
/* 1725 */       if (localObjectStreamField == null) {
/* 1726 */         throw new IllegalArgumentException("no such field " + paramString + " with type " + paramClass);
/*      */       }
/*      */       
/* 1729 */       return localObjectStreamField.getOffset();
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   private static class BlockDataOutputStream
/*      */     extends OutputStream
/*      */     implements DataOutput
/*      */   {
/*      */     private static final int MAX_BLOCK_SIZE = 1024;
/*      */     
/*      */ 
/*      */ 
/*      */     private static final int MAX_HEADER_SIZE = 5;
/*      */     
/*      */ 
/*      */     private static final int CHAR_BUF_SIZE = 256;
/*      */     
/*      */ 
/* 1750 */     private final byte[] buf = new byte['Ѐ'];
/*      */     
/* 1752 */     private final byte[] hbuf = new byte[5];
/*      */     
/* 1754 */     private final char[] cbuf = new char['Ā'];
/*      */     
/*      */ 
/* 1757 */     private boolean blkmode = false;
/*      */     
/* 1759 */     private int pos = 0;
/*      */     
/*      */ 
/*      */     private final OutputStream out;
/*      */     
/*      */ 
/*      */     private final DataOutputStream dout;
/*      */     
/*      */ 
/*      */ 
/*      */     BlockDataOutputStream(OutputStream paramOutputStream)
/*      */     {
/* 1771 */       this.out = paramOutputStream;
/* 1772 */       this.dout = new DataOutputStream(this);
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     boolean setBlockDataMode(boolean paramBoolean)
/*      */       throws IOException
/*      */     {
/* 1783 */       if (this.blkmode == paramBoolean) {
/* 1784 */         return this.blkmode;
/*      */       }
/* 1786 */       drain();
/* 1787 */       this.blkmode = paramBoolean;
/* 1788 */       return !this.blkmode;
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */     boolean getBlockDataMode()
/*      */     {
/* 1796 */       return this.blkmode;
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     public void write(int paramInt)
/*      */       throws IOException
/*      */     {
/* 1807 */       if (this.pos >= 1024) {
/* 1808 */         drain();
/*      */       }
/* 1810 */       this.buf[(this.pos++)] = ((byte)paramInt);
/*      */     }
/*      */     
/*      */     public void write(byte[] paramArrayOfByte) throws IOException {
/* 1814 */       write(paramArrayOfByte, 0, paramArrayOfByte.length, false);
/*      */     }
/*      */     
/*      */     public void write(byte[] paramArrayOfByte, int paramInt1, int paramInt2) throws IOException {
/* 1818 */       write(paramArrayOfByte, paramInt1, paramInt2, false);
/*      */     }
/*      */     
/*      */     public void flush() throws IOException {
/* 1822 */       drain();
/* 1823 */       this.out.flush();
/*      */     }
/*      */     
/*      */     public void close() throws IOException {
/* 1827 */       flush();
/* 1828 */       this.out.close();
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     void write(byte[] paramArrayOfByte, int paramInt1, int paramInt2, boolean paramBoolean)
/*      */       throws IOException
/*      */     {
/* 1840 */       if ((!paramBoolean) && (!this.blkmode)) {
/* 1841 */         drain();
/* 1842 */         this.out.write(paramArrayOfByte, paramInt1, paramInt2);
/* 1843 */         return;
/*      */       }
/*      */       
/* 1846 */       while (paramInt2 > 0) {
/* 1847 */         if (this.pos >= 1024) {
/* 1848 */           drain();
/*      */         }
/* 1850 */         if ((paramInt2 >= 1024) && (!paramBoolean) && (this.pos == 0))
/*      */         {
/* 1852 */           writeBlockHeader(1024);
/* 1853 */           this.out.write(paramArrayOfByte, paramInt1, 1024);
/* 1854 */           paramInt1 += 1024;
/* 1855 */           paramInt2 -= 1024;
/*      */         } else {
/* 1857 */           int i = Math.min(paramInt2, 1024 - this.pos);
/* 1858 */           System.arraycopy(paramArrayOfByte, paramInt1, this.buf, this.pos, i);
/* 1859 */           this.pos += i;
/* 1860 */           paramInt1 += i;
/* 1861 */           paramInt2 -= i;
/*      */         }
/*      */       }
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */     void drain()
/*      */       throws IOException
/*      */     {
/* 1871 */       if (this.pos == 0) {
/* 1872 */         return;
/*      */       }
/* 1874 */       if (this.blkmode) {
/* 1875 */         writeBlockHeader(this.pos);
/*      */       }
/* 1877 */       this.out.write(this.buf, 0, this.pos);
/* 1878 */       this.pos = 0;
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */     private void writeBlockHeader(int paramInt)
/*      */       throws IOException
/*      */     {
/* 1887 */       if (paramInt <= 255) {
/* 1888 */         this.hbuf[0] = 119;
/* 1889 */         this.hbuf[1] = ((byte)paramInt);
/* 1890 */         this.out.write(this.hbuf, 0, 2);
/*      */       } else {
/* 1892 */         this.hbuf[0] = 122;
/* 1893 */         Bits.putInt(this.hbuf, 1, paramInt);
/* 1894 */         this.out.write(this.hbuf, 0, 5);
/*      */       }
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     public void writeBoolean(boolean paramBoolean)
/*      */       throws IOException
/*      */     {
/* 1907 */       if (this.pos >= 1024) {
/* 1908 */         drain();
/*      */       }
/* 1910 */       Bits.putBoolean(this.buf, this.pos++, paramBoolean);
/*      */     }
/*      */     
/*      */     public void writeByte(int paramInt) throws IOException {
/* 1914 */       if (this.pos >= 1024) {
/* 1915 */         drain();
/*      */       }
/* 1917 */       this.buf[(this.pos++)] = ((byte)paramInt);
/*      */     }
/*      */     
/*      */     public void writeChar(int paramInt) throws IOException {
/* 1921 */       if (this.pos + 2 <= 1024) {
/* 1922 */         Bits.putChar(this.buf, this.pos, (char)paramInt);
/* 1923 */         this.pos += 2;
/*      */       } else {
/* 1925 */         this.dout.writeChar(paramInt);
/*      */       }
/*      */     }
/*      */     
/*      */     public void writeShort(int paramInt) throws IOException {
/* 1930 */       if (this.pos + 2 <= 1024) {
/* 1931 */         Bits.putShort(this.buf, this.pos, (short)paramInt);
/* 1932 */         this.pos += 2;
/*      */       } else {
/* 1934 */         this.dout.writeShort(paramInt);
/*      */       }
/*      */     }
/*      */     
/*      */     public void writeInt(int paramInt) throws IOException {
/* 1939 */       if (this.pos + 4 <= 1024) {
/* 1940 */         Bits.putInt(this.buf, this.pos, paramInt);
/* 1941 */         this.pos += 4;
/*      */       } else {
/* 1943 */         this.dout.writeInt(paramInt);
/*      */       }
/*      */     }
/*      */     
/*      */     public void writeFloat(float paramFloat) throws IOException {
/* 1948 */       if (this.pos + 4 <= 1024) {
/* 1949 */         Bits.putFloat(this.buf, this.pos, paramFloat);
/* 1950 */         this.pos += 4;
/*      */       } else {
/* 1952 */         this.dout.writeFloat(paramFloat);
/*      */       }
/*      */     }
/*      */     
/*      */     public void writeLong(long paramLong) throws IOException {
/* 1957 */       if (this.pos + 8 <= 1024) {
/* 1958 */         Bits.putLong(this.buf, this.pos, paramLong);
/* 1959 */         this.pos += 8;
/*      */       } else {
/* 1961 */         this.dout.writeLong(paramLong);
/*      */       }
/*      */     }
/*      */     
/*      */     public void writeDouble(double paramDouble) throws IOException {
/* 1966 */       if (this.pos + 8 <= 1024) {
/* 1967 */         Bits.putDouble(this.buf, this.pos, paramDouble);
/* 1968 */         this.pos += 8;
/*      */       } else {
/* 1970 */         this.dout.writeDouble(paramDouble);
/*      */       }
/*      */     }
/*      */     
/*      */     public void writeBytes(String paramString) throws IOException {
/* 1975 */       int i = paramString.length();
/* 1976 */       int j = 0;
/* 1977 */       int k = 0;
/* 1978 */       for (int m = 0; m < i;) {
/* 1979 */         if (j >= k) {
/* 1980 */           j = 0;
/* 1981 */           k = Math.min(i - m, 256);
/* 1982 */           paramString.getChars(m, m + k, this.cbuf, 0);
/*      */         }
/* 1984 */         if (this.pos >= 1024) {
/* 1985 */           drain();
/*      */         }
/* 1987 */         int n = Math.min(k - j, 1024 - this.pos);
/* 1988 */         int i1 = this.pos + n;
/* 1989 */         while (this.pos < i1) {
/* 1990 */           this.buf[(this.pos++)] = ((byte)this.cbuf[(j++)]);
/*      */         }
/* 1992 */         m += n;
/*      */       }
/*      */     }
/*      */     
/*      */     public void writeChars(String paramString) throws IOException {
/* 1997 */       int i = paramString.length();
/* 1998 */       for (int j = 0; j < i;) {
/* 1999 */         int k = Math.min(i - j, 256);
/* 2000 */         paramString.getChars(j, j + k, this.cbuf, 0);
/* 2001 */         writeChars(this.cbuf, 0, k);
/* 2002 */         j += k;
/*      */       }
/*      */     }
/*      */     
/*      */     public void writeUTF(String paramString) throws IOException {
/* 2007 */       writeUTF(paramString, getUTFLength(paramString));
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     void writeBooleans(boolean[] paramArrayOfBoolean, int paramInt1, int paramInt2)
/*      */       throws IOException
/*      */     {
/* 2020 */       int i = paramInt1 + paramInt2;
/* 2021 */       while (paramInt1 < i) {
/* 2022 */         if (this.pos >= 1024) {
/* 2023 */           drain();
/*      */         }
/* 2025 */         int j = Math.min(i, paramInt1 + (1024 - this.pos));
/* 2026 */         while (paramInt1 < j) {
/* 2027 */           Bits.putBoolean(this.buf, this.pos++, paramArrayOfBoolean[(paramInt1++)]);
/*      */         }
/*      */       }
/*      */     }
/*      */     
/*      */     void writeChars(char[] paramArrayOfChar, int paramInt1, int paramInt2) throws IOException {
/* 2033 */       int i = 1022;
/* 2034 */       int j = paramInt1 + paramInt2;
/* 2035 */       while (paramInt1 < j) {
/* 2036 */         if (this.pos <= i) {
/* 2037 */           int k = 1024 - this.pos >> 1;
/* 2038 */           int m = Math.min(j, paramInt1 + k);
/* 2039 */           while (paramInt1 < m) {
/* 2040 */             Bits.putChar(this.buf, this.pos, paramArrayOfChar[(paramInt1++)]);
/* 2041 */             this.pos += 2;
/*      */           }
/*      */         } else {
/* 2044 */           this.dout.writeChar(paramArrayOfChar[(paramInt1++)]);
/*      */         }
/*      */       }
/*      */     }
/*      */     
/*      */     void writeShorts(short[] paramArrayOfShort, int paramInt1, int paramInt2) throws IOException {
/* 2050 */       int i = 1022;
/* 2051 */       int j = paramInt1 + paramInt2;
/* 2052 */       while (paramInt1 < j) {
/* 2053 */         if (this.pos <= i) {
/* 2054 */           int k = 1024 - this.pos >> 1;
/* 2055 */           int m = Math.min(j, paramInt1 + k);
/* 2056 */           while (paramInt1 < m) {
/* 2057 */             Bits.putShort(this.buf, this.pos, paramArrayOfShort[(paramInt1++)]);
/* 2058 */             this.pos += 2;
/*      */           }
/*      */         } else {
/* 2061 */           this.dout.writeShort(paramArrayOfShort[(paramInt1++)]);
/*      */         }
/*      */       }
/*      */     }
/*      */     
/*      */     void writeInts(int[] paramArrayOfInt, int paramInt1, int paramInt2) throws IOException {
/* 2067 */       int i = 1020;
/* 2068 */       int j = paramInt1 + paramInt2;
/* 2069 */       while (paramInt1 < j) {
/* 2070 */         if (this.pos <= i) {
/* 2071 */           int k = 1024 - this.pos >> 2;
/* 2072 */           int m = Math.min(j, paramInt1 + k);
/* 2073 */           while (paramInt1 < m) {
/* 2074 */             Bits.putInt(this.buf, this.pos, paramArrayOfInt[(paramInt1++)]);
/* 2075 */             this.pos += 4;
/*      */           }
/*      */         } else {
/* 2078 */           this.dout.writeInt(paramArrayOfInt[(paramInt1++)]);
/*      */         }
/*      */       }
/*      */     }
/*      */     
/*      */     void writeFloats(float[] paramArrayOfFloat, int paramInt1, int paramInt2) throws IOException {
/* 2084 */       int i = 1020;
/* 2085 */       int j = paramInt1 + paramInt2;
/* 2086 */       while (paramInt1 < j) {
/* 2087 */         if (this.pos <= i) {
/* 2088 */           int k = 1024 - this.pos >> 2;
/* 2089 */           int m = Math.min(j - paramInt1, k);
/* 2090 */           ObjectOutputStream.floatsToBytes(paramArrayOfFloat, paramInt1, this.buf, this.pos, m);
/* 2091 */           paramInt1 += m;
/* 2092 */           this.pos += (m << 2);
/*      */         } else {
/* 2094 */           this.dout.writeFloat(paramArrayOfFloat[(paramInt1++)]);
/*      */         }
/*      */       }
/*      */     }
/*      */     
/*      */     void writeLongs(long[] paramArrayOfLong, int paramInt1, int paramInt2) throws IOException {
/* 2100 */       int i = 1016;
/* 2101 */       int j = paramInt1 + paramInt2;
/* 2102 */       while (paramInt1 < j) {
/* 2103 */         if (this.pos <= i) {
/* 2104 */           int k = 1024 - this.pos >> 3;
/* 2105 */           int m = Math.min(j, paramInt1 + k);
/* 2106 */           while (paramInt1 < m) {
/* 2107 */             Bits.putLong(this.buf, this.pos, paramArrayOfLong[(paramInt1++)]);
/* 2108 */             this.pos += 8;
/*      */           }
/*      */         } else {
/* 2111 */           this.dout.writeLong(paramArrayOfLong[(paramInt1++)]);
/*      */         }
/*      */       }
/*      */     }
/*      */     
/*      */     void writeDoubles(double[] paramArrayOfDouble, int paramInt1, int paramInt2) throws IOException {
/* 2117 */       int i = 1016;
/* 2118 */       int j = paramInt1 + paramInt2;
/* 2119 */       while (paramInt1 < j) {
/* 2120 */         if (this.pos <= i) {
/* 2121 */           int k = 1024 - this.pos >> 3;
/* 2122 */           int m = Math.min(j - paramInt1, k);
/* 2123 */           ObjectOutputStream.doublesToBytes(paramArrayOfDouble, paramInt1, this.buf, this.pos, m);
/* 2124 */           paramInt1 += m;
/* 2125 */           this.pos += (m << 3);
/*      */         } else {
/* 2127 */           this.dout.writeDouble(paramArrayOfDouble[(paramInt1++)]);
/*      */         }
/*      */       }
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */     long getUTFLength(String paramString)
/*      */     {
/* 2136 */       int i = paramString.length();
/* 2137 */       long l = 0L;
/* 2138 */       for (int j = 0; j < i;) {
/* 2139 */         int k = Math.min(i - j, 256);
/* 2140 */         paramString.getChars(j, j + k, this.cbuf, 0);
/* 2141 */         for (int m = 0; m < k; m++) {
/* 2142 */           int n = this.cbuf[m];
/* 2143 */           if ((n >= 1) && (n <= 127)) {
/* 2144 */             l += 1L;
/* 2145 */           } else if (n > 2047) {
/* 2146 */             l += 3L;
/*      */           } else {
/* 2148 */             l += 2L;
/*      */           }
/*      */         }
/* 2151 */         j += k;
/*      */       }
/* 2153 */       return l;
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     void writeUTF(String paramString, long paramLong)
/*      */       throws IOException
/*      */     {
/* 2163 */       if (paramLong > 65535L) {
/* 2164 */         throw new UTFDataFormatException();
/*      */       }
/* 2166 */       writeShort((int)paramLong);
/* 2167 */       if (paramLong == paramString.length()) {
/* 2168 */         writeBytes(paramString);
/*      */       } else {
/* 2170 */         writeUTFBody(paramString);
/*      */       }
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */     void writeLongUTF(String paramString)
/*      */       throws IOException
/*      */     {
/* 2180 */       writeLongUTF(paramString, getUTFLength(paramString));
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */     void writeLongUTF(String paramString, long paramLong)
/*      */       throws IOException
/*      */     {
/* 2188 */       writeLong(paramLong);
/* 2189 */       if (paramLong == paramString.length()) {
/* 2190 */         writeBytes(paramString);
/*      */       } else {
/* 2192 */         writeUTFBody(paramString);
/*      */       }
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */     private void writeUTFBody(String paramString)
/*      */       throws IOException
/*      */     {
/* 2201 */       int i = 1021;
/* 2202 */       int j = paramString.length();
/* 2203 */       for (int k = 0; k < j;) {
/* 2204 */         int m = Math.min(j - k, 256);
/* 2205 */         paramString.getChars(k, k + m, this.cbuf, 0);
/* 2206 */         for (int n = 0; n < m; n++) {
/* 2207 */           int i1 = this.cbuf[n];
/* 2208 */           if (this.pos <= i) {
/* 2209 */             if ((i1 <= 127) && (i1 != 0)) {
/* 2210 */               this.buf[(this.pos++)] = ((byte)i1);
/* 2211 */             } else if (i1 > 2047) {
/* 2212 */               this.buf[(this.pos + 2)] = ((byte)(0x80 | i1 >> 0 & 0x3F));
/* 2213 */               this.buf[(this.pos + 1)] = ((byte)(0x80 | i1 >> 6 & 0x3F));
/* 2214 */               this.buf[(this.pos + 0)] = ((byte)(0xE0 | i1 >> 12 & 0xF));
/* 2215 */               this.pos += 3;
/*      */             } else {
/* 2217 */               this.buf[(this.pos + 1)] = ((byte)(0x80 | i1 >> 0 & 0x3F));
/* 2218 */               this.buf[(this.pos + 0)] = ((byte)(0xC0 | i1 >> 6 & 0x1F));
/* 2219 */               this.pos += 2;
/*      */             }
/*      */           }
/* 2222 */           else if ((i1 <= 127) && (i1 != 0)) {
/* 2223 */             write(i1);
/* 2224 */           } else if (i1 > 2047) {
/* 2225 */             write(0xE0 | i1 >> 12 & 0xF);
/* 2226 */             write(0x80 | i1 >> 6 & 0x3F);
/* 2227 */             write(0x80 | i1 >> 0 & 0x3F);
/*      */           } else {
/* 2229 */             write(0xC0 | i1 >> 6 & 0x1F);
/* 2230 */             write(0x80 | i1 >> 0 & 0x3F);
/*      */           }
/*      */         }
/*      */         
/* 2234 */         k += m;
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   private static class HandleTable
/*      */   {
/*      */     private int size;
/*      */     
/*      */ 
/*      */     private int threshold;
/*      */     
/*      */ 
/*      */     private final float loadFactor;
/*      */     
/*      */ 
/*      */     private int[] spine;
/*      */     
/*      */ 
/*      */     private int[] next;
/*      */     
/*      */     private Object[] objs;
/*      */     
/*      */ 
/*      */     HandleTable(int paramInt, float paramFloat)
/*      */     {
/* 2262 */       this.loadFactor = paramFloat;
/* 2263 */       this.spine = new int[paramInt];
/* 2264 */       this.next = new int[paramInt];
/* 2265 */       this.objs = new Object[paramInt];
/* 2266 */       this.threshold = ((int)(paramInt * paramFloat));
/* 2267 */       clear();
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */     int assign(Object paramObject)
/*      */     {
/* 2275 */       if (this.size >= this.next.length) {
/* 2276 */         growEntries();
/*      */       }
/* 2278 */       if (this.size >= this.threshold) {
/* 2279 */         growSpine();
/*      */       }
/* 2281 */       insert(paramObject, this.size);
/* 2282 */       return this.size++;
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */     int lookup(Object paramObject)
/*      */     {
/* 2290 */       if (this.size == 0) {
/* 2291 */         return -1;
/*      */       }
/* 2293 */       int i = hash(paramObject) % this.spine.length;
/* 2294 */       for (int j = this.spine[i]; j >= 0; j = this.next[j]) {
/* 2295 */         if (this.objs[j] == paramObject) {
/* 2296 */           return j;
/*      */         }
/*      */       }
/* 2299 */       return -1;
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */     void clear()
/*      */     {
/* 2306 */       Arrays.fill(this.spine, -1);
/* 2307 */       Arrays.fill(this.objs, 0, this.size, null);
/* 2308 */       this.size = 0;
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */     int size()
/*      */     {
/* 2315 */       return this.size;
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */     private void insert(Object paramObject, int paramInt)
/*      */     {
/* 2323 */       int i = hash(paramObject) % this.spine.length;
/* 2324 */       this.objs[paramInt] = paramObject;
/* 2325 */       this.next[paramInt] = this.spine[i];
/* 2326 */       this.spine[i] = paramInt;
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */     private void growSpine()
/*      */     {
/* 2334 */       this.spine = new int[(this.spine.length << 1) + 1];
/* 2335 */       this.threshold = ((int)(this.spine.length * this.loadFactor));
/* 2336 */       Arrays.fill(this.spine, -1);
/* 2337 */       for (int i = 0; i < this.size; i++) {
/* 2338 */         insert(this.objs[i], i);
/*      */       }
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */     private void growEntries()
/*      */     {
/* 2346 */       int i = (this.next.length << 1) + 1;
/* 2347 */       int[] arrayOfInt = new int[i];
/* 2348 */       System.arraycopy(this.next, 0, arrayOfInt, 0, this.size);
/* 2349 */       this.next = arrayOfInt;
/*      */       
/* 2351 */       Object[] arrayOfObject = new Object[i];
/* 2352 */       System.arraycopy(this.objs, 0, arrayOfObject, 0, this.size);
/* 2353 */       this.objs = arrayOfObject;
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */     private int hash(Object paramObject)
/*      */     {
/* 2360 */       return System.identityHashCode(paramObject) & 0x7FFFFFFF;
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   private static class ReplaceTable
/*      */   {
/*      */     private final ObjectOutputStream.HandleTable htab;
/*      */     
/*      */ 
/*      */ 
/*      */     private Object[] reps;
/*      */     
/*      */ 
/*      */ 
/*      */     ReplaceTable(int paramInt, float paramFloat)
/*      */     {
/* 2379 */       this.htab = new ObjectOutputStream.HandleTable(paramInt, paramFloat);
/* 2380 */       this.reps = new Object[paramInt];
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */     void assign(Object paramObject1, Object paramObject2)
/*      */     {
/* 2387 */       int i = this.htab.assign(paramObject1);
/* 2388 */       while (i >= this.reps.length) {
/* 2389 */         grow();
/*      */       }
/* 2391 */       this.reps[i] = paramObject2;
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */     Object lookup(Object paramObject)
/*      */     {
/* 2399 */       int i = this.htab.lookup(paramObject);
/* 2400 */       return i >= 0 ? this.reps[i] : paramObject;
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */     void clear()
/*      */     {
/* 2407 */       Arrays.fill(this.reps, 0, this.htab.size(), null);
/* 2408 */       this.htab.clear();
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */     int size()
/*      */     {
/* 2415 */       return this.htab.size();
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */     private void grow()
/*      */     {
/* 2422 */       Object[] arrayOfObject = new Object[(this.reps.length << 1) + 1];
/* 2423 */       System.arraycopy(this.reps, 0, arrayOfObject, 0, this.reps.length);
/* 2424 */       this.reps = arrayOfObject;
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */   private static class DebugTraceInfoStack
/*      */   {
/*      */     private final List<String> stack;
/*      */     
/*      */ 
/*      */     DebugTraceInfoStack()
/*      */     {
/* 2436 */       this.stack = new ArrayList();
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */     void clear()
/*      */     {
/* 2443 */       this.stack.clear();
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */     void pop()
/*      */     {
/* 2450 */       this.stack.remove(this.stack.size() - 1);
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */     void push(String paramString)
/*      */     {
/* 2457 */       this.stack.add("\t- " + paramString);
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */     public String toString()
/*      */     {
/* 2464 */       StringBuilder localStringBuilder = new StringBuilder();
/* 2465 */       if (!this.stack.isEmpty()) {
/* 2466 */         for (int i = this.stack.size(); i > 0; i--) {
/* 2467 */           localStringBuilder.append((String)this.stack.get(i - 1) + (i != 1 ? "\n" : ""));
/*      */         }
/*      */       }
/* 2470 */       return localStringBuilder.toString();
/*      */     }
/*      */   }
/*      */ }


/* Location:              /Users/guanxiaoda/Documents/workspace/rt.jar!/java/io/ObjectOutputStream.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */