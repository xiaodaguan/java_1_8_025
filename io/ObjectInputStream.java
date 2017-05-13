/*      */ package java.io;
/*      */ 
/*      */ import java.lang.ref.ReferenceQueue;
/*      */ import java.lang.reflect.Array;
/*      */ import java.lang.reflect.Proxy;
/*      */ import java.security.AccessControlContext;
/*      */ import java.security.AccessController;
/*      */ import java.security.PrivilegedAction;
/*      */ import java.security.PrivilegedActionException;
/*      */ import java.security.PrivilegedExceptionAction;
/*      */ import java.util.Arrays;
/*      */ import java.util.HashMap;
/*      */ import java.util.concurrent.ConcurrentHashMap;
/*      */ import java.util.concurrent.ConcurrentMap;
/*      */ import sun.misc.VM;
/*      */ import sun.reflect.misc.ReflectUtil;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class ObjectInputStream
/*      */   extends InputStream
/*      */   implements ObjectInput, ObjectStreamConstants
/*      */ {
/*      */   private static final int NULL_HANDLE = -1;
/*  213 */   private static final Object unsharedMarker = new Object();
/*      */   
/*      */ 
/*  216 */   private static final HashMap<String, Class<?>> primClasses = new HashMap(8, 1.0F);
/*      */   private final BlockDataInputStream bin;
/*      */   
/*  219 */   static { primClasses.put("boolean", Boolean.TYPE);
/*  220 */     primClasses.put("byte", Byte.TYPE);
/*  221 */     primClasses.put("char", Character.TYPE);
/*  222 */     primClasses.put("short", Short.TYPE);
/*  223 */     primClasses.put("int", Integer.TYPE);
/*  224 */     primClasses.put("long", Long.TYPE);
/*  225 */     primClasses.put("float", Float.TYPE);
/*  226 */     primClasses.put("double", Double.TYPE);
/*  227 */     primClasses.put("void", Void.TYPE);
/*      */   }
/*      */   
/*      */   private static class Caches
/*      */   {
/*  232 */     static final ConcurrentMap<ObjectStreamClass.WeakClassKey, Boolean> subclassAudits = new ConcurrentHashMap();
/*      */     
/*      */ 
/*      */ 
/*  236 */     static final ReferenceQueue<Class<?>> subclassAuditsQueue = new ReferenceQueue();
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   private final ValidationList vlist;
/*      */   
/*      */ 
/*      */   private int depth;
/*      */   
/*      */ 
/*      */   private boolean closed;
/*      */   
/*      */ 
/*      */   private final HandleTable handles;
/*      */   
/*  252 */   private int passHandle = -1;
/*      */   
/*  254 */   private boolean defaultDataEnd = false;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private byte[] primVals;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private final boolean enableOverride;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private boolean enableResolve;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private SerialCallbackContext curContext;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public ObjectInputStream(InputStream paramInputStream)
/*      */     throws IOException
/*      */   {
/*  294 */     verifySubclass();
/*  295 */     this.bin = new BlockDataInputStream(paramInputStream);
/*  296 */     this.handles = new HandleTable(10);
/*  297 */     this.vlist = new ValidationList();
/*  298 */     this.enableOverride = false;
/*  299 */     readStreamHeader();
/*  300 */     this.bin.setBlockDataMode(true);
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
/*      */   protected ObjectInputStream()
/*      */     throws IOException, SecurityException
/*      */   {
/*  321 */     SecurityManager localSecurityManager = System.getSecurityManager();
/*  322 */     if (localSecurityManager != null) {
/*  323 */       localSecurityManager.checkPermission(SUBCLASS_IMPLEMENTATION_PERMISSION);
/*      */     }
/*  325 */     this.bin = null;
/*  326 */     this.handles = null;
/*  327 */     this.vlist = null;
/*  328 */     this.enableOverride = true;
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
/*      */   public final Object readObject()
/*      */     throws IOException, ClassNotFoundException
/*      */   {
/*  364 */     if (this.enableOverride) {
/*  365 */       return readObjectOverride();
/*      */     }
/*      */     
/*      */ 
/*  369 */     int i = this.passHandle;
/*      */     try {
/*  371 */       Object localObject1 = readObject0(false);
/*  372 */       this.handles.markDependency(i, this.passHandle);
/*  373 */       ClassNotFoundException localClassNotFoundException = this.handles.lookupException(this.passHandle);
/*  374 */       if (localClassNotFoundException != null) {
/*  375 */         throw localClassNotFoundException;
/*      */       }
/*  377 */       if (this.depth == 0) {
/*  378 */         this.vlist.doCallbacks();
/*      */       }
/*  380 */       return localObject1;
/*      */     } finally {
/*  382 */       this.passHandle = i;
/*  383 */       if ((this.closed) && (this.depth == 0)) {
/*  384 */         clear();
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   protected Object readObjectOverride()
/*      */     throws IOException, ClassNotFoundException
/*      */   {
/*  409 */     return null;
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
/*      */   public Object readUnshared()
/*      */     throws IOException, ClassNotFoundException
/*      */   {
/*  459 */     int i = this.passHandle;
/*      */     try {
/*  461 */       Object localObject1 = readObject0(true);
/*  462 */       this.handles.markDependency(i, this.passHandle);
/*  463 */       ClassNotFoundException localClassNotFoundException = this.handles.lookupException(this.passHandle);
/*  464 */       if (localClassNotFoundException != null) {
/*  465 */         throw localClassNotFoundException;
/*      */       }
/*  467 */       if (this.depth == 0) {
/*  468 */         this.vlist.doCallbacks();
/*      */       }
/*  470 */       return localObject1;
/*      */     } finally {
/*  472 */       this.passHandle = i;
/*  473 */       if ((this.closed) && (this.depth == 0)) {
/*  474 */         clear();
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public void defaultReadObject()
/*      */     throws IOException, ClassNotFoundException
/*      */   {
/*  494 */     SerialCallbackContext localSerialCallbackContext = this.curContext;
/*  495 */     if (localSerialCallbackContext == null) {
/*  496 */       throw new NotActiveException("not in call to readObject");
/*      */     }
/*  498 */     Object localObject = localSerialCallbackContext.getObj();
/*  499 */     ObjectStreamClass localObjectStreamClass = localSerialCallbackContext.getDesc();
/*  500 */     this.bin.setBlockDataMode(false);
/*  501 */     defaultReadFields(localObject, localObjectStreamClass);
/*  502 */     this.bin.setBlockDataMode(true);
/*  503 */     if (!localObjectStreamClass.hasWriteObjectData())
/*      */     {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  509 */       this.defaultDataEnd = true;
/*      */     }
/*  511 */     ClassNotFoundException localClassNotFoundException = this.handles.lookupException(this.passHandle);
/*  512 */     if (localClassNotFoundException != null) {
/*  513 */       throw localClassNotFoundException;
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
/*      */   public GetField readFields()
/*      */     throws IOException, ClassNotFoundException
/*      */   {
/*  533 */     SerialCallbackContext localSerialCallbackContext = this.curContext;
/*  534 */     if (localSerialCallbackContext == null) {
/*  535 */       throw new NotActiveException("not in call to readObject");
/*      */     }
/*  537 */     Object localObject = localSerialCallbackContext.getObj();
/*  538 */     ObjectStreamClass localObjectStreamClass = localSerialCallbackContext.getDesc();
/*  539 */     this.bin.setBlockDataMode(false);
/*  540 */     GetFieldImpl localGetFieldImpl = new GetFieldImpl(localObjectStreamClass);
/*  541 */     localGetFieldImpl.readFields();
/*  542 */     this.bin.setBlockDataMode(true);
/*  543 */     if (!localObjectStreamClass.hasWriteObjectData())
/*      */     {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  549 */       this.defaultDataEnd = true;
/*      */     }
/*      */     
/*  552 */     return localGetFieldImpl;
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
/*      */   public void registerValidation(ObjectInputValidation paramObjectInputValidation, int paramInt)
/*      */     throws NotActiveException, InvalidObjectException
/*      */   {
/*  574 */     if (this.depth == 0) {
/*  575 */       throw new NotActiveException("stream inactive");
/*      */     }
/*  577 */     this.vlist.register(paramObjectInputValidation, paramInt);
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
/*      */   protected Class<?> resolveClass(ObjectStreamClass paramObjectStreamClass)
/*      */     throws IOException, ClassNotFoundException
/*      */   {
/*  624 */     String str = paramObjectStreamClass.getName();
/*      */     try {
/*  626 */       return Class.forName(str, false, latestUserDefinedLoader());
/*      */     } catch (ClassNotFoundException localClassNotFoundException) {
/*  628 */       Class localClass = (Class)primClasses.get(str);
/*  629 */       if (localClass != null) {
/*  630 */         return localClass;
/*      */       }
/*  632 */       throw localClassNotFoundException;
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   protected Class<?> resolveProxyClass(String[] paramArrayOfString)
/*      */     throws IOException, ClassNotFoundException
/*      */   {
/*  691 */     ClassLoader localClassLoader1 = latestUserDefinedLoader();
/*  692 */     ClassLoader localClassLoader2 = null;
/*  693 */     int i = 0;
/*      */     
/*      */ 
/*  696 */     Class[] arrayOfClass = new Class[paramArrayOfString.length];
/*  697 */     for (int j = 0; j < paramArrayOfString.length; j++) {
/*  698 */       Class localClass = Class.forName(paramArrayOfString[j], false, localClassLoader1);
/*  699 */       if ((localClass.getModifiers() & 0x1) == 0) {
/*  700 */         if (i != 0) {
/*  701 */           if (localClassLoader2 != localClass.getClassLoader()) {
/*  702 */             throw new IllegalAccessError("conflicting non-public interface class loaders");
/*      */           }
/*      */         }
/*      */         else {
/*  706 */           localClassLoader2 = localClass.getClassLoader();
/*  707 */           i = 1;
/*      */         }
/*      */       }
/*  710 */       arrayOfClass[j] = localClass;
/*      */     }
/*      */     try {
/*  713 */       return Proxy.getProxyClass(i != 0 ? localClassLoader2 : localClassLoader1, arrayOfClass);
/*      */     }
/*      */     catch (IllegalArgumentException localIllegalArgumentException)
/*      */     {
/*  717 */       throw new ClassNotFoundException(null, localIllegalArgumentException);
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
/*      */   protected Object resolveObject(Object paramObject)
/*      */     throws IOException
/*      */   {
/*  749 */     return paramObject;
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
/*      */   protected boolean enableResolveObject(boolean paramBoolean)
/*      */     throws SecurityException
/*      */   {
/*  776 */     if (paramBoolean == this.enableResolve) {
/*  777 */       return paramBoolean;
/*      */     }
/*  779 */     if (paramBoolean) {
/*  780 */       SecurityManager localSecurityManager = System.getSecurityManager();
/*  781 */       if (localSecurityManager != null) {
/*  782 */         localSecurityManager.checkPermission(SUBSTITUTION_PERMISSION);
/*      */       }
/*      */     }
/*  785 */     this.enableResolve = paramBoolean;
/*  786 */     return !this.enableResolve;
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
/*      */   protected void readStreamHeader()
/*      */     throws IOException, StreamCorruptedException
/*      */   {
/*  802 */     short s1 = this.bin.readShort();
/*  803 */     short s2 = this.bin.readShort();
/*  804 */     if ((s1 != 44269) || (s2 != 5))
/*      */     {
/*  806 */       throw new StreamCorruptedException(String.format("invalid stream header: %04X%04X", new Object[] {Short.valueOf(s1), Short.valueOf(s2) }));
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
/*      */   protected ObjectStreamClass readClassDescriptor()
/*      */     throws IOException, ClassNotFoundException
/*      */   {
/*  830 */     ObjectStreamClass localObjectStreamClass = new ObjectStreamClass();
/*  831 */     localObjectStreamClass.readNonProxy(this);
/*  832 */     return localObjectStreamClass;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public int read()
/*      */     throws IOException
/*      */   {
/*  842 */     return this.bin.read();
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
/*      */   public int read(byte[] paramArrayOfByte, int paramInt1, int paramInt2)
/*      */     throws IOException
/*      */   {
/*  859 */     if (paramArrayOfByte == null) {
/*  860 */       throw new NullPointerException();
/*      */     }
/*  862 */     int i = paramInt1 + paramInt2;
/*  863 */     if ((paramInt1 < 0) || (paramInt2 < 0) || (i > paramArrayOfByte.length) || (i < 0)) {
/*  864 */       throw new IndexOutOfBoundsException();
/*      */     }
/*  866 */     return this.bin.read(paramArrayOfByte, paramInt1, paramInt2, false);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public int available()
/*      */     throws IOException
/*      */   {
/*  877 */     return this.bin.available();
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
/*      */   public void close()
/*      */     throws IOException
/*      */   {
/*  891 */     this.closed = true;
/*  892 */     if (this.depth == 0) {
/*  893 */       clear();
/*      */     }
/*  895 */     this.bin.close();
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public boolean readBoolean()
/*      */     throws IOException
/*      */   {
/*  906 */     return this.bin.readBoolean();
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public byte readByte()
/*      */     throws IOException
/*      */   {
/*  917 */     return this.bin.readByte();
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public int readUnsignedByte()
/*      */     throws IOException
/*      */   {
/*  928 */     return this.bin.readUnsignedByte();
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public char readChar()
/*      */     throws IOException
/*      */   {
/*  939 */     return this.bin.readChar();
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public short readShort()
/*      */     throws IOException
/*      */   {
/*  950 */     return this.bin.readShort();
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public int readUnsignedShort()
/*      */     throws IOException
/*      */   {
/*  961 */     return this.bin.readUnsignedShort();
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public int readInt()
/*      */     throws IOException
/*      */   {
/*  972 */     return this.bin.readInt();
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public long readLong()
/*      */     throws IOException
/*      */   {
/*  983 */     return this.bin.readLong();
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public float readFloat()
/*      */     throws IOException
/*      */   {
/*  994 */     return this.bin.readFloat();
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public double readDouble()
/*      */     throws IOException
/*      */   {
/* 1005 */     return this.bin.readDouble();
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public void readFully(byte[] paramArrayOfByte)
/*      */     throws IOException
/*      */   {
/* 1016 */     this.bin.readFully(paramArrayOfByte, 0, paramArrayOfByte.length, false);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public void readFully(byte[] paramArrayOfByte, int paramInt1, int paramInt2)
/*      */     throws IOException
/*      */   {
/* 1029 */     int i = paramInt1 + paramInt2;
/* 1030 */     if ((paramInt1 < 0) || (paramInt2 < 0) || (i > paramArrayOfByte.length) || (i < 0)) {
/* 1031 */       throw new IndexOutOfBoundsException();
/*      */     }
/* 1033 */     this.bin.readFully(paramArrayOfByte, paramInt1, paramInt2, false);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public int skipBytes(int paramInt)
/*      */     throws IOException
/*      */   {
/* 1044 */     return this.bin.skipBytes(paramInt);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   @Deprecated
/*      */   public String readLine()
/*      */     throws IOException
/*      */   {
/* 1058 */     return this.bin.readLine();
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
/*      */   public String readUTF()
/*      */     throws IOException
/*      */   {
/* 1073 */     return this.bin.readUTF();
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
/*      */   public static abstract class GetField
/*      */   {
/*      */     public abstract ObjectStreamClass getObjectStreamClass();
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     public abstract boolean defaulted(String paramString)
/*      */       throws IOException;
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     public abstract boolean get(String paramString, boolean paramBoolean)
/*      */       throws IOException;
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     public abstract byte get(String paramString, byte paramByte)
/*      */       throws IOException;
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     public abstract char get(String paramString, char paramChar)
/*      */       throws IOException;
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     public abstract short get(String paramString, short paramShort)
/*      */       throws IOException;
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     public abstract int get(String paramString, int paramInt)
/*      */       throws IOException;
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     public abstract long get(String paramString, long paramLong)
/*      */       throws IOException;
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     public abstract float get(String paramString, float paramFloat)
/*      */       throws IOException;
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     public abstract double get(String paramString, double paramDouble)
/*      */       throws IOException;
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     public abstract Object get(String paramString, Object paramObject)
/*      */       throws IOException;
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
/*      */   private void verifySubclass()
/*      */   {
/* 1236 */     Class localClass = getClass();
/* 1237 */     if (localClass == ObjectInputStream.class) {
/* 1238 */       return;
/*      */     }
/* 1240 */     SecurityManager localSecurityManager = System.getSecurityManager();
/* 1241 */     if (localSecurityManager == null) {
/* 1242 */       return;
/*      */     }
/* 1244 */     ObjectStreamClass.processQueue(Caches.subclassAuditsQueue, Caches.subclassAudits);
/* 1245 */     ObjectStreamClass.WeakClassKey localWeakClassKey = new ObjectStreamClass.WeakClassKey(localClass, Caches.subclassAuditsQueue);
/* 1246 */     Boolean localBoolean = (Boolean)Caches.subclassAudits.get(localWeakClassKey);
/* 1247 */     if (localBoolean == null) {
/* 1248 */       localBoolean = Boolean.valueOf(auditSubclass(localClass));
/* 1249 */       Caches.subclassAudits.putIfAbsent(localWeakClassKey, localBoolean);
/*      */     }
/* 1251 */     if (localBoolean.booleanValue()) {
/* 1252 */       return;
/*      */     }
/* 1254 */     localSecurityManager.checkPermission(SUBCLASS_IMPLEMENTATION_PERMISSION);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private static boolean auditSubclass(Class<?> paramClass)
/*      */   {
/* 1263 */     Boolean localBoolean = (Boolean)AccessController.doPrivileged(new PrivilegedAction()
/*      */     {
/*      */       public Boolean run() {
/* 1266 */         for (Class localClass = this.val$subcl; 
/* 1267 */             localClass != ObjectInputStream.class; 
/* 1268 */             localClass = localClass.getSuperclass()) {
/*      */           try
/*      */           {
/* 1271 */             localClass.getDeclaredMethod("readUnshared", (Class[])null);
/*      */             
/* 1273 */             return Boolean.FALSE;
/*      */           }
/*      */           catch (NoSuchMethodException localNoSuchMethodException1) {
/*      */             try {
/* 1277 */               localClass.getDeclaredMethod("readFields", (Class[])null);
/* 1278 */               return Boolean.FALSE;
/*      */             } catch (NoSuchMethodException localNoSuchMethodException2) {}
/*      */           }
/*      */         }
/* 1282 */         return Boolean.TRUE;
/*      */       }
/*      */       
/* 1285 */     });
/* 1286 */     return localBoolean.booleanValue();
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   private void clear()
/*      */   {
/* 1293 */     this.handles.clear();
/* 1294 */     this.vlist.clear();
/*      */   }
/*      */   
/*      */ 
/*      */   private Object readObject0(boolean paramBoolean)
/*      */     throws IOException
/*      */   {
/* 1301 */     boolean bool = this.bin.getBlockDataMode();
/* 1302 */     int i; if (bool) {
/* 1303 */       i = this.bin.currentBlockRemaining();
/* 1304 */       if (i > 0)
/* 1305 */         throw new OptionalDataException(i);
/* 1306 */       if (this.defaultDataEnd)
/*      */       {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1313 */         throw new OptionalDataException(true);
/*      */       }
/* 1315 */       this.bin.setBlockDataMode(false);
/*      */     }
/*      */     
/*      */ 
/* 1319 */     while ((i = this.bin.peekByte()) == 121) {
/* 1320 */       this.bin.readByte();
/* 1321 */       handleReset();
/*      */     }
/*      */     
/* 1324 */     this.depth += 1;
/*      */     try { Object localObject1;
/* 1326 */       switch (i) {
/*      */       case 112: 
/* 1328 */         return readNull();
/*      */       
/*      */       case 113: 
/* 1331 */         return readHandle(paramBoolean);
/*      */       
/*      */       case 118: 
/* 1334 */         return readClass(paramBoolean);
/*      */       
/*      */       case 114: 
/*      */       case 125: 
/* 1338 */         return readClassDesc(paramBoolean);
/*      */       
/*      */       case 116: 
/*      */       case 124: 
/* 1342 */         return checkResolve(readString(paramBoolean));
/*      */       
/*      */       case 117: 
/* 1345 */         return checkResolve(readArray(paramBoolean));
/*      */       
/*      */       case 126: 
/* 1348 */         return checkResolve(readEnum(paramBoolean));
/*      */       
/*      */       case 115: 
/* 1351 */         return checkResolve(readOrdinaryObject(paramBoolean));
/*      */       
/*      */       case 123: 
/* 1354 */         localObject1 = readFatalException();
/* 1355 */         throw new WriteAbortedException("writing aborted", (Exception)localObject1);
/*      */       
/*      */       case 119: 
/*      */       case 122: 
/* 1359 */         if (bool) {
/* 1360 */           this.bin.setBlockDataMode(true);
/* 1361 */           this.bin.peek();
/*      */           
/* 1363 */           throw new OptionalDataException(this.bin.currentBlockRemaining());
/*      */         }
/* 1365 */         throw new StreamCorruptedException("unexpected block data");
/*      */       
/*      */ 
/*      */ 
/*      */       case 120: 
/* 1370 */         if (bool) {
/* 1371 */           throw new OptionalDataException(true);
/*      */         }
/* 1373 */         throw new StreamCorruptedException("unexpected end of block data");
/*      */       }
/*      */       
/*      */       
/*      */ 
/*      */ 
/* 1379 */       throw new StreamCorruptedException(String.format("invalid type code: %02X", new Object[] {Byte.valueOf(i) }));
/*      */     }
/*      */     finally {
/* 1382 */       this.depth -= 1;
/* 1383 */       this.bin.setBlockDataMode(bool);
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private Object checkResolve(Object paramObject)
/*      */     throws IOException
/*      */   {
/* 1396 */     if ((!this.enableResolve) || (this.handles.lookupException(this.passHandle) != null)) {
/* 1397 */       return paramObject;
/*      */     }
/* 1399 */     Object localObject = resolveObject(paramObject);
/* 1400 */     if (localObject != paramObject) {
/* 1401 */       this.handles.setObject(this.passHandle, localObject);
/*      */     }
/* 1403 */     return localObject;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   String readTypeString()
/*      */     throws IOException
/*      */   {
/* 1411 */     int i = this.passHandle;
/*      */     try {
/* 1413 */       byte b = this.bin.peekByte();
/* 1414 */       String str; switch (b) {
/*      */       case 112: 
/* 1416 */         return (String)readNull();
/*      */       
/*      */       case 113: 
/* 1419 */         return (String)readHandle(false);
/*      */       
/*      */       case 116: 
/*      */       case 124: 
/* 1423 */         return readString(false);
/*      */       }
/*      */       
/*      */       
/* 1427 */       throw new StreamCorruptedException(String.format("invalid type code: %02X", new Object[] {Byte.valueOf(b) }));
/*      */     }
/*      */     finally {
/* 1430 */       this.passHandle = i;
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */   private Object readNull()
/*      */     throws IOException
/*      */   {
/* 1438 */     if (this.bin.readByte() != 112) {
/* 1439 */       throw new InternalError();
/*      */     }
/* 1441 */     this.passHandle = -1;
/* 1442 */     return null;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   private Object readHandle(boolean paramBoolean)
/*      */     throws IOException
/*      */   {
/* 1450 */     if (this.bin.readByte() != 113) {
/* 1451 */       throw new InternalError();
/*      */     }
/* 1453 */     this.passHandle = (this.bin.readInt() - 8257536);
/* 1454 */     if ((this.passHandle < 0) || (this.passHandle >= this.handles.size()))
/*      */     {
/* 1456 */       throw new StreamCorruptedException(String.format("invalid handle value: %08X", new Object[] {Integer.valueOf(this.passHandle + 8257536) }));
/*      */     }
/*      */     
/* 1459 */     if (paramBoolean)
/*      */     {
/* 1461 */       throw new InvalidObjectException("cannot read back reference as unshared");
/*      */     }
/*      */     
/*      */ 
/* 1465 */     Object localObject = this.handles.lookupObject(this.passHandle);
/* 1466 */     if (localObject == unsharedMarker)
/*      */     {
/* 1468 */       throw new InvalidObjectException("cannot read back reference to unshared object");
/*      */     }
/*      */     
/* 1471 */     return localObject;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private Class<?> readClass(boolean paramBoolean)
/*      */     throws IOException
/*      */   {
/* 1481 */     if (this.bin.readByte() != 118) {
/* 1482 */       throw new InternalError();
/*      */     }
/* 1484 */     ObjectStreamClass localObjectStreamClass = readClassDesc(false);
/* 1485 */     Class localClass = localObjectStreamClass.forClass();
/* 1486 */     this.passHandle = this.handles.assign(paramBoolean ? unsharedMarker : localClass);
/*      */     
/* 1488 */     ClassNotFoundException localClassNotFoundException = localObjectStreamClass.getResolveException();
/* 1489 */     if (localClassNotFoundException != null) {
/* 1490 */       this.handles.markException(this.passHandle, localClassNotFoundException);
/*      */     }
/*      */     
/* 1493 */     this.handles.finish(this.passHandle);
/* 1494 */     return localClass;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private ObjectStreamClass readClassDesc(boolean paramBoolean)
/*      */     throws IOException
/*      */   {
/* 1506 */     byte b = this.bin.peekByte();
/* 1507 */     switch (b) {
/*      */     case 112: 
/* 1509 */       return (ObjectStreamClass)readNull();
/*      */     
/*      */     case 113: 
/* 1512 */       return (ObjectStreamClass)readHandle(paramBoolean);
/*      */     
/*      */     case 125: 
/* 1515 */       return readProxyDesc(paramBoolean);
/*      */     
/*      */     case 114: 
/* 1518 */       return readNonProxyDesc(paramBoolean);
/*      */     }
/*      */     
/*      */     
/* 1522 */     throw new StreamCorruptedException(String.format("invalid type code: %02X", new Object[] {Byte.valueOf(b) }));
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   private boolean isCustomSubclass()
/*      */   {
/* 1529 */     return getClass().getClassLoader() != ObjectInputStream.class.getClassLoader();
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private ObjectStreamClass readProxyDesc(boolean paramBoolean)
/*      */     throws IOException
/*      */   {
/* 1541 */     if (this.bin.readByte() != 125) {
/* 1542 */       throw new InternalError();
/*      */     }
/*      */     
/* 1545 */     ObjectStreamClass localObjectStreamClass = new ObjectStreamClass();
/* 1546 */     int i = this.handles.assign(paramBoolean ? unsharedMarker : localObjectStreamClass);
/* 1547 */     this.passHandle = -1;
/*      */     
/* 1549 */     int j = this.bin.readInt();
/* 1550 */     String[] arrayOfString = new String[j];
/* 1551 */     for (int k = 0; k < j; k++) {
/* 1552 */       arrayOfString[k] = this.bin.readUTF();
/*      */     }
/*      */     
/* 1555 */     Class localClass = null;
/* 1556 */     Object localObject = null;
/* 1557 */     this.bin.setBlockDataMode(true);
/*      */     try {
/* 1559 */       if ((localClass = resolveProxyClass(arrayOfString)) == null) {
/* 1560 */         localObject = new ClassNotFoundException("null class");
/* 1561 */       } else { if (!Proxy.isProxyClass(localClass)) {
/* 1562 */           throw new InvalidClassException("Not a proxy");
/*      */         }
/*      */         
/*      */ 
/*      */ 
/* 1567 */         ReflectUtil.checkProxyPackageAccess(
/* 1568 */           getClass().getClassLoader(), localClass
/* 1569 */           .getInterfaces());
/*      */       }
/*      */     } catch (ClassNotFoundException localClassNotFoundException) {
/* 1572 */       localObject = localClassNotFoundException;
/*      */     }
/* 1574 */     skipCustomData();
/*      */     
/* 1576 */     localObjectStreamClass.initProxy(localClass, (ClassNotFoundException)localObject, readClassDesc(false));
/*      */     
/* 1578 */     this.handles.finish(i);
/* 1579 */     this.passHandle = i;
/* 1580 */     return localObjectStreamClass;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private ObjectStreamClass readNonProxyDesc(boolean paramBoolean)
/*      */     throws IOException
/*      */   {
/* 1592 */     if (this.bin.readByte() != 114) {
/* 1593 */       throw new InternalError();
/*      */     }
/*      */     
/* 1596 */     ObjectStreamClass localObjectStreamClass1 = new ObjectStreamClass();
/* 1597 */     int i = this.handles.assign(paramBoolean ? unsharedMarker : localObjectStreamClass1);
/* 1598 */     this.passHandle = -1;
/*      */     
/* 1600 */     ObjectStreamClass localObjectStreamClass2 = null;
/*      */     try {
/* 1602 */       localObjectStreamClass2 = readClassDescriptor();
/*      */     }
/*      */     catch (ClassNotFoundException localClassNotFoundException1) {
/* 1605 */       throw ((IOException)new InvalidClassException("failed to read class descriptor").initCause(localClassNotFoundException1));
/*      */     }
/*      */     
/* 1608 */     Class localClass = null;
/* 1609 */     Object localObject = null;
/* 1610 */     this.bin.setBlockDataMode(true);
/* 1611 */     boolean bool = isCustomSubclass();
/*      */     try {
/* 1613 */       if ((localClass = resolveClass(localObjectStreamClass2)) == null) {
/* 1614 */         localObject = new ClassNotFoundException("null class");
/* 1615 */       } else if (bool) {
/* 1616 */         ReflectUtil.checkPackageAccess(localClass);
/*      */       }
/*      */     } catch (ClassNotFoundException localClassNotFoundException2) {
/* 1619 */       localObject = localClassNotFoundException2;
/*      */     }
/* 1621 */     skipCustomData();
/*      */     
/* 1623 */     localObjectStreamClass1.initNonProxy(localObjectStreamClass2, localClass, (ClassNotFoundException)localObject, readClassDesc(false));
/*      */     
/* 1625 */     this.handles.finish(i);
/* 1626 */     this.passHandle = i;
/* 1627 */     return localObjectStreamClass1;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   private String readString(boolean paramBoolean)
/*      */     throws IOException
/*      */   {
/* 1636 */     byte b = this.bin.readByte();
/* 1637 */     String str; switch (b) {
/*      */     case 116: 
/* 1639 */       str = this.bin.readUTF();
/* 1640 */       break;
/*      */     
/*      */     case 124: 
/* 1643 */       str = this.bin.readLongUTF();
/* 1644 */       break;
/*      */     
/*      */ 
/*      */     default: 
/* 1648 */       throw new StreamCorruptedException(String.format("invalid type code: %02X", new Object[] {Byte.valueOf(b) }));
/*      */     }
/* 1650 */     this.passHandle = this.handles.assign(paramBoolean ? unsharedMarker : str);
/* 1651 */     this.handles.finish(this.passHandle);
/* 1652 */     return str;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   private Object readArray(boolean paramBoolean)
/*      */     throws IOException
/*      */   {
/* 1660 */     if (this.bin.readByte() != 117) {
/* 1661 */       throw new InternalError();
/*      */     }
/*      */     
/* 1664 */     ObjectStreamClass localObjectStreamClass = readClassDesc(false);
/* 1665 */     int i = this.bin.readInt();
/*      */     
/* 1667 */     Object localObject = null;
/* 1668 */     Class localClass2 = null;
/* 1669 */     Class localClass1; if ((localClass1 = localObjectStreamClass.forClass()) != null) {
/* 1670 */       localClass2 = localClass1.getComponentType();
/* 1671 */       localObject = Array.newInstance(localClass2, i);
/*      */     }
/*      */     
/* 1674 */     int j = this.handles.assign(paramBoolean ? unsharedMarker : localObject);
/* 1675 */     ClassNotFoundException localClassNotFoundException = localObjectStreamClass.getResolveException();
/* 1676 */     if (localClassNotFoundException != null) {
/* 1677 */       this.handles.markException(j, localClassNotFoundException);
/*      */     }
/*      */     
/* 1680 */     if (localClass2 == null) {
/* 1681 */       for (int k = 0; k < i; k++) {
/* 1682 */         readObject0(false);
/*      */       }
/* 1684 */     } else if (localClass2.isPrimitive()) {
/* 1685 */       if (localClass2 == Integer.TYPE) {
/* 1686 */         this.bin.readInts((int[])localObject, 0, i);
/* 1687 */       } else if (localClass2 == Byte.TYPE) {
/* 1688 */         this.bin.readFully((byte[])localObject, 0, i, true);
/* 1689 */       } else if (localClass2 == Long.TYPE) {
/* 1690 */         this.bin.readLongs((long[])localObject, 0, i);
/* 1691 */       } else if (localClass2 == Float.TYPE) {
/* 1692 */         this.bin.readFloats((float[])localObject, 0, i);
/* 1693 */       } else if (localClass2 == Double.TYPE) {
/* 1694 */         this.bin.readDoubles((double[])localObject, 0, i);
/* 1695 */       } else if (localClass2 == Short.TYPE) {
/* 1696 */         this.bin.readShorts((short[])localObject, 0, i);
/* 1697 */       } else if (localClass2 == Character.TYPE) {
/* 1698 */         this.bin.readChars((char[])localObject, 0, i);
/* 1699 */       } else if (localClass2 == Boolean.TYPE) {
/* 1700 */         this.bin.readBooleans((boolean[])localObject, 0, i);
/*      */       } else {
/* 1702 */         throw new InternalError();
/*      */       }
/*      */     } else {
/* 1705 */       Object[] arrayOfObject = (Object[])localObject;
/* 1706 */       for (int m = 0; m < i; m++) {
/* 1707 */         arrayOfObject[m] = readObject0(false);
/* 1708 */         this.handles.markDependency(j, this.passHandle);
/*      */       }
/*      */     }
/*      */     
/* 1712 */     this.handles.finish(j);
/* 1713 */     this.passHandle = j;
/* 1714 */     return localObject;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   private Enum<?> readEnum(boolean paramBoolean)
/*      */     throws IOException
/*      */   {
/* 1722 */     if (this.bin.readByte() != 126) {
/* 1723 */       throw new InternalError();
/*      */     }
/*      */     
/* 1726 */     ObjectStreamClass localObjectStreamClass = readClassDesc(false);
/* 1727 */     if (!localObjectStreamClass.isEnum()) {
/* 1728 */       throw new InvalidClassException("non-enum class: " + localObjectStreamClass);
/*      */     }
/*      */     
/* 1731 */     int i = this.handles.assign(paramBoolean ? unsharedMarker : null);
/* 1732 */     ClassNotFoundException localClassNotFoundException = localObjectStreamClass.getResolveException();
/* 1733 */     if (localClassNotFoundException != null) {
/* 1734 */       this.handles.markException(i, localClassNotFoundException);
/*      */     }
/*      */     
/* 1737 */     String str = readString(false);
/* 1738 */     Object localObject = null;
/* 1739 */     Class localClass = localObjectStreamClass.forClass();
/* 1740 */     if (localClass != null)
/*      */     {
/*      */       try {
/* 1743 */         Enum localEnum = Enum.valueOf(localClass, str);
/* 1744 */         localObject = localEnum;
/*      */       }
/*      */       catch (IllegalArgumentException localIllegalArgumentException)
/*      */       {
/* 1748 */         throw ((IOException)new InvalidObjectException("enum constant " + str + " does not exist in " + localClass).initCause(localIllegalArgumentException));
/*      */       }
/* 1750 */       if (!paramBoolean) {
/* 1751 */         this.handles.setObject(i, localObject);
/*      */       }
/*      */     }
/*      */     
/* 1755 */     this.handles.finish(i);
/* 1756 */     this.passHandle = i;
/* 1757 */     return (Enum<?>)localObject;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private Object readOrdinaryObject(boolean paramBoolean)
/*      */     throws IOException
/*      */   {
/* 1770 */     if (this.bin.readByte() != 115) {
/* 1771 */       throw new InternalError();
/*      */     }
/*      */     
/* 1774 */     ObjectStreamClass localObjectStreamClass = readClassDesc(false);
/* 1775 */     localObjectStreamClass.checkDeserialize();
/*      */     
/* 1777 */     Class localClass = localObjectStreamClass.forClass();
/* 1778 */     if ((localClass == String.class) || (localClass == Class.class) || (localClass == ObjectStreamClass.class))
/*      */     {
/* 1780 */       throw new InvalidClassException("invalid class descriptor");
/*      */     }
/*      */     Object localObject1;
/*      */     try
/*      */     {
/* 1785 */       localObject1 = localObjectStreamClass.isInstantiable() ? localObjectStreamClass.newInstance() : null;
/*      */     }
/*      */     catch (Exception localException)
/*      */     {
/* 1789 */       throw ((IOException)new InvalidClassException(localObjectStreamClass.forClass().getName(), "unable to create instance").initCause(localException));
/*      */     }
/*      */     
/* 1792 */     this.passHandle = this.handles.assign(paramBoolean ? unsharedMarker : localObject1);
/* 1793 */     ClassNotFoundException localClassNotFoundException = localObjectStreamClass.getResolveException();
/* 1794 */     if (localClassNotFoundException != null) {
/* 1795 */       this.handles.markException(this.passHandle, localClassNotFoundException);
/*      */     }
/*      */     
/* 1798 */     if (localObjectStreamClass.isExternalizable()) {
/* 1799 */       readExternalData((Externalizable)localObject1, localObjectStreamClass);
/*      */     } else {
/* 1801 */       readSerialData(localObject1, localObjectStreamClass);
/*      */     }
/*      */     
/* 1804 */     this.handles.finish(this.passHandle);
/*      */     
/* 1806 */     if ((localObject1 != null) && 
/* 1807 */       (this.handles.lookupException(this.passHandle) == null) && 
/* 1808 */       (localObjectStreamClass.hasReadResolveMethod()))
/*      */     {
/* 1810 */       Object localObject2 = localObjectStreamClass.invokeReadResolve(localObject1);
/* 1811 */       if ((paramBoolean) && (localObject2.getClass().isArray())) {
/* 1812 */         localObject2 = cloneArray(localObject2);
/*      */       }
/* 1814 */       if (localObject2 != localObject1) {
/* 1815 */         this.handles.setObject(this.passHandle, localObject1 = localObject2);
/*      */       }
/*      */     }
/*      */     
/* 1819 */     return localObject1;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private void readExternalData(Externalizable paramExternalizable, ObjectStreamClass paramObjectStreamClass)
/*      */     throws IOException
/*      */   {
/* 1831 */     SerialCallbackContext localSerialCallbackContext = this.curContext;
/* 1832 */     this.curContext = null;
/*      */     try {
/* 1834 */       boolean bool = paramObjectStreamClass.hasBlockExternalData();
/* 1835 */       if (bool) {
/* 1836 */         this.bin.setBlockDataMode(true);
/*      */       }
/* 1838 */       if (paramExternalizable != null) {
/*      */         try {
/* 1840 */           paramExternalizable.readExternal(this);
/*      */ 
/*      */ 
/*      */ 
/*      */         }
/*      */         catch (ClassNotFoundException localClassNotFoundException)
/*      */         {
/*      */ 
/*      */ 
/* 1849 */           this.handles.markException(this.passHandle, localClassNotFoundException);
/*      */         }
/*      */       }
/* 1852 */       if (bool) {
/* 1853 */         skipCustomData();
/*      */       }
/*      */     } finally {
/* 1856 */       this.curContext = localSerialCallbackContext;
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
/*      */   private void readSerialData(Object paramObject, ObjectStreamClass paramObjectStreamClass)
/*      */     throws IOException
/*      */   {
/* 1881 */     ObjectStreamClass.ClassDataSlot[] arrayOfClassDataSlot = paramObjectStreamClass.getClassDataLayout();
/* 1882 */     for (int i = 0; i < arrayOfClassDataSlot.length; i++) {
/* 1883 */       ObjectStreamClass localObjectStreamClass = arrayOfClassDataSlot[i].desc;
/*      */       
/* 1885 */       if (arrayOfClassDataSlot[i].hasData) {
/* 1886 */         if ((paramObject != null) && 
/* 1887 */           (localObjectStreamClass.hasReadObjectMethod()) && 
/* 1888 */           (this.handles.lookupException(this.passHandle) == null))
/*      */         {
/* 1890 */           SerialCallbackContext localSerialCallbackContext = this.curContext;
/*      */           try
/*      */           {
/* 1893 */             this.curContext = new SerialCallbackContext(paramObject, localObjectStreamClass);
/*      */             
/* 1895 */             this.bin.setBlockDataMode(true);
/* 1896 */             localObjectStreamClass.invokeReadObject(paramObject, this);
/*      */ 
/*      */ 
/*      */ 
/*      */           }
/*      */           catch (ClassNotFoundException localClassNotFoundException)
/*      */           {
/*      */ 
/*      */ 
/* 1905 */             this.handles.markException(this.passHandle, localClassNotFoundException);
/*      */           } finally {
/* 1907 */             this.curContext.setUsed();
/* 1908 */             this.curContext = localSerialCallbackContext;
/*      */           }
/*      */           
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1916 */           this.defaultDataEnd = false;
/*      */         } else {
/* 1918 */           defaultReadFields(paramObject, localObjectStreamClass);
/*      */         }
/* 1920 */         if (localObjectStreamClass.hasWriteObjectData()) {
/* 1921 */           skipCustomData();
/*      */         } else {
/* 1923 */           this.bin.setBlockDataMode(false);
/*      */         }
/*      */       }
/* 1926 */       else if ((paramObject != null) && 
/* 1927 */         (localObjectStreamClass.hasReadObjectNoDataMethod()) && 
/* 1928 */         (this.handles.lookupException(this.passHandle) == null))
/*      */       {
/* 1930 */         localObjectStreamClass.invokeReadObjectNoData(paramObject);
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   private void skipCustomData()
/*      */     throws IOException
/*      */   {
/* 1941 */     int i = this.passHandle;
/*      */     for (;;) {
/* 1943 */       if (this.bin.getBlockDataMode()) {
/* 1944 */         this.bin.skipBlockData();
/* 1945 */         this.bin.setBlockDataMode(false);
/*      */       }
/* 1947 */       switch (this.bin.peekByte()) {
/*      */       case 119: 
/*      */       case 122: 
/* 1950 */         this.bin.setBlockDataMode(true);
/* 1951 */         break;
/*      */       
/*      */       case 120: 
/* 1954 */         this.bin.readByte();
/* 1955 */         this.passHandle = i;
/* 1956 */         return;
/*      */       case 121: 
/*      */       default: 
/* 1959 */         readObject0(false);
/*      */       }
/*      */       
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private void defaultReadFields(Object paramObject, ObjectStreamClass paramObjectStreamClass)
/*      */     throws IOException
/*      */   {
/* 1973 */     Class localClass = paramObjectStreamClass.forClass();
/* 1974 */     if ((localClass != null) && (paramObject != null) && (!localClass.isInstance(paramObject))) {
/* 1975 */       throw new ClassCastException();
/*      */     }
/*      */     
/* 1978 */     int i = paramObjectStreamClass.getPrimDataSize();
/* 1979 */     if ((this.primVals == null) || (this.primVals.length < i)) {
/* 1980 */       this.primVals = new byte[i];
/*      */     }
/* 1982 */     this.bin.readFully(this.primVals, 0, i, false);
/* 1983 */     if (paramObject != null) {
/* 1984 */       paramObjectStreamClass.setPrimFieldValues(paramObject, this.primVals);
/*      */     }
/*      */     
/* 1987 */     int j = this.passHandle;
/* 1988 */     ObjectStreamField[] arrayOfObjectStreamField = paramObjectStreamClass.getFields(false);
/* 1989 */     Object[] arrayOfObject = new Object[paramObjectStreamClass.getNumObjFields()];
/* 1990 */     int k = arrayOfObjectStreamField.length - arrayOfObject.length;
/* 1991 */     for (int m = 0; m < arrayOfObject.length; m++) {
/* 1992 */       ObjectStreamField localObjectStreamField = arrayOfObjectStreamField[(k + m)];
/* 1993 */       arrayOfObject[m] = readObject0(localObjectStreamField.isUnshared());
/* 1994 */       if (localObjectStreamField.getField() != null) {
/* 1995 */         this.handles.markDependency(j, this.passHandle);
/*      */       }
/*      */     }
/* 1998 */     if (paramObject != null) {
/* 1999 */       paramObjectStreamClass.setObjFieldValues(paramObject, arrayOfObject);
/*      */     }
/* 2001 */     this.passHandle = j;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   private IOException readFatalException()
/*      */     throws IOException
/*      */   {
/* 2010 */     if (this.bin.readByte() != 123) {
/* 2011 */       throw new InternalError();
/*      */     }
/* 2013 */     clear();
/* 2014 */     return (IOException)readObject0(false);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   private void handleReset()
/*      */     throws StreamCorruptedException
/*      */   {
/* 2023 */     if (this.depth > 0) {
/* 2024 */       throw new StreamCorruptedException("unexpected reset; recursion depth: " + this.depth);
/*      */     }
/*      */     
/* 2027 */     clear();
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
/*      */   private static ClassLoader latestUserDefinedLoader()
/*      */   {
/* 2058 */     return VM.latestUserDefinedLoader();
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
/*      */   private class GetFieldImpl
/*      */     extends ObjectInputStream.GetField
/*      */   {
/*      */     GetFieldImpl(ObjectStreamClass paramObjectStreamClass)
/*      */     {
/* 2080 */       this.desc = paramObjectStreamClass;
/* 2081 */       this.primVals = new byte[paramObjectStreamClass.getPrimDataSize()];
/* 2082 */       this.objVals = new Object[paramObjectStreamClass.getNumObjFields()];
/* 2083 */       this.objHandles = new int[this.objVals.length];
/*      */     }
/*      */     
/*      */     public ObjectStreamClass getObjectStreamClass() {
/* 2087 */       return this.desc;
/*      */     }
/*      */     
/*      */     public boolean defaulted(String paramString) throws IOException {
/* 2091 */       return getFieldOffset(paramString, null) < 0;
/*      */     }
/*      */     
/*      */     public boolean get(String paramString, boolean paramBoolean) throws IOException {
/* 2095 */       int i = getFieldOffset(paramString, Boolean.TYPE);
/* 2096 */       return i >= 0 ? Bits.getBoolean(this.primVals, i) : paramBoolean;
/*      */     }
/*      */     
/*      */     public byte get(String paramString, byte paramByte) throws IOException {
/* 2100 */       int i = getFieldOffset(paramString, Byte.TYPE);
/* 2101 */       return i >= 0 ? this.primVals[i] : paramByte;
/*      */     }
/*      */     
/*      */     public char get(String paramString, char paramChar) throws IOException {
/* 2105 */       int i = getFieldOffset(paramString, Character.TYPE);
/* 2106 */       return i >= 0 ? Bits.getChar(this.primVals, i) : paramChar;
/*      */     }
/*      */     
/*      */     public short get(String paramString, short paramShort) throws IOException {
/* 2110 */       int i = getFieldOffset(paramString, Short.TYPE);
/* 2111 */       return i >= 0 ? Bits.getShort(this.primVals, i) : paramShort;
/*      */     }
/*      */     
/*      */     public int get(String paramString, int paramInt) throws IOException {
/* 2115 */       int i = getFieldOffset(paramString, Integer.TYPE);
/* 2116 */       return i >= 0 ? Bits.getInt(this.primVals, i) : paramInt;
/*      */     }
/*      */     
/*      */     public float get(String paramString, float paramFloat) throws IOException {
/* 2120 */       int i = getFieldOffset(paramString, Float.TYPE);
/* 2121 */       return i >= 0 ? Bits.getFloat(this.primVals, i) : paramFloat;
/*      */     }
/*      */     
/*      */     public long get(String paramString, long paramLong) throws IOException {
/* 2125 */       int i = getFieldOffset(paramString, Long.TYPE);
/* 2126 */       return i >= 0 ? Bits.getLong(this.primVals, i) : paramLong;
/*      */     }
/*      */     
/*      */     public double get(String paramString, double paramDouble) throws IOException {
/* 2130 */       int i = getFieldOffset(paramString, Double.TYPE);
/* 2131 */       return i >= 0 ? Bits.getDouble(this.primVals, i) : paramDouble;
/*      */     }
/*      */     
/*      */     public Object get(String paramString, Object paramObject) throws IOException {
/* 2135 */       int i = getFieldOffset(paramString, Object.class);
/* 2136 */       if (i >= 0) {
/* 2137 */         int j = this.objHandles[i];
/* 2138 */         ObjectInputStream.this.handles.markDependency(ObjectInputStream.this.passHandle, j);
/* 2139 */         return ObjectInputStream.this.handles.lookupException(j) == null ? this.objVals[i] : null;
/*      */       }
/*      */       
/* 2142 */       return paramObject;
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */     void readFields()
/*      */       throws IOException
/*      */     {
/* 2150 */       ObjectInputStream.this.bin.readFully(this.primVals, 0, this.primVals.length, false);
/*      */       
/* 2152 */       int i = ObjectInputStream.this.passHandle;
/* 2153 */       ObjectStreamField[] arrayOfObjectStreamField = this.desc.getFields(false);
/* 2154 */       int j = arrayOfObjectStreamField.length - this.objVals.length;
/* 2155 */       for (int k = 0; k < this.objVals.length; k++)
/*      */       {
/* 2157 */         this.objVals[k] = ObjectInputStream.this.readObject0(arrayOfObjectStreamField[(j + k)].isUnshared());
/* 2158 */         this.objHandles[k] = ObjectInputStream.this.passHandle;
/*      */       }
/* 2160 */       ObjectInputStream.this.passHandle = i;
/*      */     }
/*      */     
/*      */ 
/*      */     private final ObjectStreamClass desc;
/*      */     
/*      */     private final byte[] primVals;
/*      */     
/*      */     private final Object[] objVals;
/*      */     
/*      */     private final int[] objHandles;
/*      */     private int getFieldOffset(String paramString, Class<?> paramClass)
/*      */     {
/* 2173 */       ObjectStreamField localObjectStreamField = this.desc.getField(paramString, paramClass);
/* 2174 */       if (localObjectStreamField != null)
/* 2175 */         return localObjectStreamField.getOffset();
/* 2176 */       if (this.desc.getLocalDesc().getField(paramString, paramClass) != null) {
/* 2177 */         return -1;
/*      */       }
/* 2179 */       throw new IllegalArgumentException("no such field " + paramString + " with type " + paramClass);
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */   private static class ValidationList
/*      */   {
/*      */     private Callback list;
/*      */     
/*      */ 
/*      */     private static class Callback
/*      */     {
/*      */       final ObjectInputValidation obj;
/*      */       
/*      */       final int priority;
/*      */       
/*      */       Callback next;
/*      */       final AccessControlContext acc;
/*      */       
/*      */       Callback(ObjectInputValidation paramObjectInputValidation, int paramInt, Callback paramCallback, AccessControlContext paramAccessControlContext)
/*      */       {
/* 2200 */         this.obj = paramObjectInputValidation;
/* 2201 */         this.priority = paramInt;
/* 2202 */         this.next = paramCallback;
/* 2203 */         this.acc = paramAccessControlContext;
/*      */       }
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
/*      */     void register(ObjectInputValidation paramObjectInputValidation, int paramInt)
/*      */       throws InvalidObjectException
/*      */     {
/* 2223 */       if (paramObjectInputValidation == null) {
/* 2224 */         throw new InvalidObjectException("null callback");
/*      */       }
/*      */       
/* 2227 */       Object localObject = null;Callback localCallback = this.list;
/* 2228 */       while ((localCallback != null) && (paramInt < localCallback.priority)) {
/* 2229 */         localObject = localCallback;
/* 2230 */         localCallback = localCallback.next;
/*      */       }
/* 2232 */       AccessControlContext localAccessControlContext = AccessController.getContext();
/* 2233 */       if (localObject != null) {
/* 2234 */         ((Callback)localObject).next = new Callback(paramObjectInputValidation, paramInt, localCallback, localAccessControlContext);
/*      */       } else {
/* 2236 */         this.list = new Callback(paramObjectInputValidation, paramInt, this.list, localAccessControlContext);
/*      */       }
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     void doCallbacks()
/*      */       throws InvalidObjectException
/*      */     {
/*      */       try
/*      */       {
/* 2249 */         while (this.list != null) {
/* 2250 */           AccessController.doPrivileged(new PrivilegedExceptionAction()
/*      */           {
/*      */             public Void run() throws InvalidObjectException
/*      */             {
/* 2254 */               ObjectInputStream.ValidationList.this.list.obj.validateObject();
/* 2255 */               return null; } }, this.list.acc);
/*      */           
/*      */ 
/* 2258 */           this.list = this.list.next;
/*      */         }
/*      */       } catch (PrivilegedActionException localPrivilegedActionException) {
/* 2261 */         this.list = null;
/* 2262 */         throw ((InvalidObjectException)localPrivilegedActionException.getException());
/*      */       }
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */     public void clear()
/*      */     {
/* 2270 */       this.list = null;
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   private static class PeekInputStream
/*      */     extends InputStream
/*      */   {
/*      */     private final InputStream in;
/*      */     
/*      */ 
/* 2282 */     private int peekb = -1;
/*      */     
/*      */ 
/*      */ 
/*      */     PeekInputStream(InputStream paramInputStream)
/*      */     {
/* 2288 */       this.in = paramInputStream;
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */     int peek()
/*      */       throws IOException
/*      */     {
/* 2296 */       return this.peekb >= 0 ? this.peekb : (this.peekb = this.in.read());
/*      */     }
/*      */     
/*      */     public int read() throws IOException {
/* 2300 */       if (this.peekb >= 0) {
/* 2301 */         int i = this.peekb;
/* 2302 */         this.peekb = -1;
/* 2303 */         return i;
/*      */       }
/* 2305 */       return this.in.read();
/*      */     }
/*      */     
/*      */     public int read(byte[] paramArrayOfByte, int paramInt1, int paramInt2) throws IOException
/*      */     {
/* 2310 */       if (paramInt2 == 0)
/* 2311 */         return 0;
/* 2312 */       if (this.peekb < 0) {
/* 2313 */         return this.in.read(paramArrayOfByte, paramInt1, paramInt2);
/*      */       }
/* 2315 */       paramArrayOfByte[(paramInt1++)] = ((byte)this.peekb);
/* 2316 */       paramInt2--;
/* 2317 */       this.peekb = -1;
/* 2318 */       int i = this.in.read(paramArrayOfByte, paramInt1, paramInt2);
/* 2319 */       return i >= 0 ? i + 1 : 1;
/*      */     }
/*      */     
/*      */     void readFully(byte[] paramArrayOfByte, int paramInt1, int paramInt2) throws IOException
/*      */     {
/* 2324 */       int i = 0;
/* 2325 */       while (i < paramInt2) {
/* 2326 */         int j = read(paramArrayOfByte, paramInt1 + i, paramInt2 - i);
/* 2327 */         if (j < 0) {
/* 2328 */           throw new EOFException();
/*      */         }
/* 2330 */         i += j;
/*      */       }
/*      */     }
/*      */     
/*      */     public long skip(long paramLong) throws IOException {
/* 2335 */       if (paramLong <= 0L) {
/* 2336 */         return 0L;
/*      */       }
/* 2338 */       int i = 0;
/* 2339 */       if (this.peekb >= 0) {
/* 2340 */         this.peekb = -1;
/* 2341 */         i++;
/* 2342 */         paramLong -= 1L;
/*      */       }
/* 2344 */       return i + skip(paramLong);
/*      */     }
/*      */     
/*      */     public int available() throws IOException {
/* 2348 */       return this.in.available() + (this.peekb >= 0 ? 1 : 0);
/*      */     }
/*      */     
/*      */     public void close() throws IOException {
/* 2352 */       this.in.close();
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   private class BlockDataInputStream
/*      */     extends InputStream
/*      */     implements DataInput
/*      */   {
/*      */     private static final int MAX_BLOCK_SIZE = 1024;
/*      */     
/*      */ 
/*      */ 
/*      */     private static final int MAX_HEADER_SIZE = 5;
/*      */     
/*      */ 
/*      */ 
/*      */     private static final int CHAR_BUF_SIZE = 256;
/*      */     
/*      */ 
/*      */     private static final int HEADER_BLOCKED = -2;
/*      */     
/*      */ 
/* 2377 */     private final byte[] buf = new byte['Ѐ'];
/*      */     
/* 2379 */     private final byte[] hbuf = new byte[5];
/*      */     
/* 2381 */     private final char[] cbuf = new char['Ā'];
/*      */     
/*      */ 
/* 2384 */     private boolean blkmode = false;
/*      */     
/*      */ 
/*      */ 
/* 2388 */     private int pos = 0;
/*      */     
/* 2390 */     private int end = -1;
/*      */     
/* 2392 */     private int unread = 0;
/*      */     
/*      */ 
/*      */     private final ObjectInputStream.PeekInputStream in;
/*      */     
/*      */ 
/*      */     private final DataInputStream din;
/*      */     
/*      */ 
/*      */ 
/*      */     BlockDataInputStream(InputStream paramInputStream)
/*      */     {
/* 2404 */       this.in = new ObjectInputStream.PeekInputStream(paramInputStream);
/* 2405 */       this.din = new DataInputStream(this);
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
/* 2416 */       if (this.blkmode == paramBoolean) {
/* 2417 */         return this.blkmode;
/*      */       }
/* 2419 */       if (paramBoolean) {
/* 2420 */         this.pos = 0;
/* 2421 */         this.end = 0;
/* 2422 */         this.unread = 0;
/* 2423 */       } else if (this.pos < this.end) {
/* 2424 */         throw new IllegalStateException("unread block data");
/*      */       }
/* 2426 */       this.blkmode = paramBoolean;
/* 2427 */       return !this.blkmode;
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */     boolean getBlockDataMode()
/*      */     {
/* 2435 */       return this.blkmode;
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */     void skipBlockData()
/*      */       throws IOException
/*      */     {
/* 2444 */       if (!this.blkmode) {
/* 2445 */         throw new IllegalStateException("not in block data mode");
/*      */       }
/* 2447 */       while (this.end >= 0) {
/* 2448 */         refill();
/*      */       }
/*      */     }
/*      */     
/*      */     /* Error */
/*      */     private int readBlockHeader(boolean paramBoolean)
/*      */       throws IOException
/*      */     {
/*      */       // Byte code:
/*      */       //   0: aload_0
/*      */       //   1: getfield 2	java/io/ObjectInputStream$BlockDataInputStream:this$0	Ljava/io/ObjectInputStream;
/*      */       //   4: invokestatic 23	java/io/ObjectInputStream:access$500	(Ljava/io/ObjectInputStream;)Z
/*      */       //   7: ifeq +5 -> 12
/*      */       //   10: iconst_m1
/*      */       //   11: ireturn
/*      */       //   12: iload_1
/*      */       //   13: ifeq +8 -> 21
/*      */       //   16: ldc 25
/*      */       //   18: goto +10 -> 28
/*      */       //   21: aload_0
/*      */       //   22: getfield 14	java/io/ObjectInputStream$BlockDataInputStream:in	Ljava/io/ObjectInputStream$PeekInputStream;
/*      */       //   25: invokevirtual 26	java/io/ObjectInputStream$PeekInputStream:available	()I
/*      */       //   28: istore_2
/*      */       //   29: iload_2
/*      */       //   30: ifne +6 -> 36
/*      */       //   33: bipush -2
/*      */       //   35: ireturn
/*      */       //   36: aload_0
/*      */       //   37: getfield 14	java/io/ObjectInputStream$BlockDataInputStream:in	Ljava/io/ObjectInputStream$PeekInputStream;
/*      */       //   40: invokevirtual 27	java/io/ObjectInputStream$PeekInputStream:peek	()I
/*      */       //   43: istore_3
/*      */       //   44: iload_3
/*      */       //   45: tableswitch	default:+148->193, 119:+31->76, 120:+148->193, 121:+130->175, 122:+63->108
/*      */       //   76: iload_2
/*      */       //   77: iconst_2
/*      */       //   78: if_icmpge +6 -> 84
/*      */       //   81: bipush -2
/*      */       //   83: ireturn
/*      */       //   84: aload_0
/*      */       //   85: getfield 14	java/io/ObjectInputStream$BlockDataInputStream:in	Ljava/io/ObjectInputStream$PeekInputStream;
/*      */       //   88: aload_0
/*      */       //   89: getfield 6	java/io/ObjectInputStream$BlockDataInputStream:hbuf	[B
/*      */       //   92: iconst_0
/*      */       //   93: iconst_2
/*      */       //   94: invokevirtual 28	java/io/ObjectInputStream$PeekInputStream:readFully	([BII)V
/*      */       //   97: aload_0
/*      */       //   98: getfield 6	java/io/ObjectInputStream$BlockDataInputStream:hbuf	[B
/*      */       //   101: iconst_1
/*      */       //   102: baload
/*      */       //   103: sipush 255
/*      */       //   106: iand
/*      */       //   107: ireturn
/*      */       //   108: iload_2
/*      */       //   109: iconst_5
/*      */       //   110: if_icmpge +6 -> 116
/*      */       //   113: bipush -2
/*      */       //   115: ireturn
/*      */       //   116: aload_0
/*      */       //   117: getfield 14	java/io/ObjectInputStream$BlockDataInputStream:in	Ljava/io/ObjectInputStream$PeekInputStream;
/*      */       //   120: aload_0
/*      */       //   121: getfield 6	java/io/ObjectInputStream$BlockDataInputStream:hbuf	[B
/*      */       //   124: iconst_0
/*      */       //   125: iconst_5
/*      */       //   126: invokevirtual 28	java/io/ObjectInputStream$PeekInputStream:readFully	([BII)V
/*      */       //   129: aload_0
/*      */       //   130: getfield 6	java/io/ObjectInputStream$BlockDataInputStream:hbuf	[B
/*      */       //   133: iconst_1
/*      */       //   134: invokestatic 29	java/io/Bits:getInt	([BI)I
/*      */       //   137: istore 4
/*      */       //   139: iload 4
/*      */       //   141: ifge +31 -> 172
/*      */       //   144: new 30	java/io/StreamCorruptedException
/*      */       //   147: dup
/*      */       //   148: new 31	java/lang/StringBuilder
/*      */       //   151: dup
/*      */       //   152: invokespecial 32	java/lang/StringBuilder:<init>	()V
/*      */       //   155: ldc 33
/*      */       //   157: invokevirtual 34	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
/*      */       //   160: iload 4
/*      */       //   162: invokevirtual 35	java/lang/StringBuilder:append	(I)Ljava/lang/StringBuilder;
/*      */       //   165: invokevirtual 36	java/lang/StringBuilder:toString	()Ljava/lang/String;
/*      */       //   168: invokespecial 37	java/io/StreamCorruptedException:<init>	(Ljava/lang/String;)V
/*      */       //   171: athrow
/*      */       //   172: iload 4
/*      */       //   174: ireturn
/*      */       //   175: aload_0
/*      */       //   176: getfield 14	java/io/ObjectInputStream$BlockDataInputStream:in	Ljava/io/ObjectInputStream$PeekInputStream;
/*      */       //   179: invokevirtual 38	java/io/ObjectInputStream$PeekInputStream:read	()I
/*      */       //   182: pop
/*      */       //   183: aload_0
/*      */       //   184: getfield 2	java/io/ObjectInputStream$BlockDataInputStream:this$0	Ljava/io/ObjectInputStream;
/*      */       //   187: invokestatic 39	java/io/ObjectInputStream:access$600	(Ljava/io/ObjectInputStream;)V
/*      */       //   190: goto +45 -> 235
/*      */       //   193: iload_3
/*      */       //   194: iflt +39 -> 233
/*      */       //   197: iload_3
/*      */       //   198: bipush 112
/*      */       //   200: if_icmplt +9 -> 209
/*      */       //   203: iload_3
/*      */       //   204: bipush 126
/*      */       //   206: if_icmple +27 -> 233
/*      */       //   209: new 30	java/io/StreamCorruptedException
/*      */       //   212: dup
/*      */       //   213: ldc 40
/*      */       //   215: iconst_1
/*      */       //   216: anewarray 41	java/lang/Object
/*      */       //   219: dup
/*      */       //   220: iconst_0
/*      */       //   221: iload_3
/*      */       //   222: invokestatic 42	java/lang/Integer:valueOf	(I)Ljava/lang/Integer;
/*      */       //   225: aastore
/*      */       //   226: invokestatic 43	java/lang/String:format	(Ljava/lang/String;[Ljava/lang/Object;)Ljava/lang/String;
/*      */       //   229: invokespecial 37	java/io/StreamCorruptedException:<init>	(Ljava/lang/String;)V
/*      */       //   232: athrow
/*      */       //   233: iconst_m1
/*      */       //   234: ireturn
/*      */       //   235: goto -223 -> 12
/*      */       //   238: astore_2
/*      */       //   239: new 30	java/io/StreamCorruptedException
/*      */       //   242: dup
/*      */       //   243: ldc 45
/*      */       //   245: invokespecial 37	java/io/StreamCorruptedException:<init>	(Ljava/lang/String;)V
/*      */       //   248: athrow
/*      */       // Line number table:
/*      */       //   Java source line #2460	-> byte code offset #0
/*      */       //   Java source line #2467	-> byte code offset #10
/*      */       //   Java source line #2471	-> byte code offset #12
/*      */       //   Java source line #2472	-> byte code offset #29
/*      */       //   Java source line #2473	-> byte code offset #33
/*      */       //   Java source line #2476	-> byte code offset #36
/*      */       //   Java source line #2477	-> byte code offset #44
/*      */       //   Java source line #2479	-> byte code offset #76
/*      */       //   Java source line #2480	-> byte code offset #81
/*      */       //   Java source line #2482	-> byte code offset #84
/*      */       //   Java source line #2483	-> byte code offset #97
/*      */       //   Java source line #2486	-> byte code offset #108
/*      */       //   Java source line #2487	-> byte code offset #113
/*      */       //   Java source line #2489	-> byte code offset #116
/*      */       //   Java source line #2490	-> byte code offset #129
/*      */       //   Java source line #2491	-> byte code offset #139
/*      */       //   Java source line #2492	-> byte code offset #144
/*      */       //   Java source line #2496	-> byte code offset #172
/*      */       //   Java source line #2505	-> byte code offset #175
/*      */       //   Java source line #2506	-> byte code offset #183
/*      */       //   Java source line #2507	-> byte code offset #190
/*      */       //   Java source line #2510	-> byte code offset #193
/*      */       //   Java source line #2511	-> byte code offset #209
/*      */       //   Java source line #2513	-> byte code offset #222
/*      */       //   Java source line #2512	-> byte code offset #226
/*      */       //   Java source line #2515	-> byte code offset #233
/*      */       //   Java source line #2517	-> byte code offset #235
/*      */       //   Java source line #2518	-> byte code offset #238
/*      */       //   Java source line #2519	-> byte code offset #239
/*      */       // Local variable table:
/*      */       //   start	length	slot	name	signature
/*      */       //   0	249	0	this	BlockDataInputStream
/*      */       //   0	249	1	paramBoolean	boolean
/*      */       //   28	83	2	i	int
/*      */       //   238	1	2	localEOFException	EOFException
/*      */       //   43	179	3	j	int
/*      */       //   137	36	4	k	int
/*      */       // Exception table:
/*      */       //   from	to	target	type
/*      */       //   12	35	238	java/io/EOFException
/*      */       //   36	83	238	java/io/EOFException
/*      */       //   84	107	238	java/io/EOFException
/*      */       //   108	115	238	java/io/EOFException
/*      */       //   116	174	238	java/io/EOFException
/*      */       //   175	234	238	java/io/EOFException
/*      */       //   235	238	238	java/io/EOFException
/*      */     }
/*      */     
/*      */     private void refill()
/*      */       throws IOException
/*      */     {
/*      */       try
/*      */       {
/*      */         do
/*      */         {
/* 2534 */           this.pos = 0;
/* 2535 */           int i; if (this.unread > 0)
/*      */           {
/* 2537 */             i = this.in.read(this.buf, 0, Math.min(this.unread, 1024));
/* 2538 */             if (i >= 0) {
/* 2539 */               this.end = i;
/* 2540 */               this.unread -= i;
/*      */             } else {
/* 2542 */               throw new StreamCorruptedException("unexpected EOF in middle of data block");
/*      */             }
/*      */           }
/*      */           else {
/* 2546 */             i = readBlockHeader(true);
/* 2547 */             if (i >= 0) {
/* 2548 */               this.end = 0;
/* 2549 */               this.unread = i;
/*      */             } else {
/* 2551 */               this.end = -1;
/* 2552 */               this.unread = 0;
/*      */             }
/*      */           }
/* 2555 */         } while (this.pos == this.end);
/*      */       } catch (IOException localIOException) {
/* 2557 */         this.pos = 0;
/* 2558 */         this.end = -1;
/* 2559 */         this.unread = 0;
/* 2560 */         throw localIOException;
/*      */       }
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     int currentBlockRemaining()
/*      */     {
/* 2570 */       if (this.blkmode) {
/* 2571 */         return this.end >= 0 ? this.end - this.pos + this.unread : 0;
/*      */       }
/* 2573 */       throw new IllegalStateException();
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     int peek()
/*      */       throws IOException
/*      */     {
/* 2583 */       if (this.blkmode) {
/* 2584 */         if (this.pos == this.end) {
/* 2585 */           refill();
/*      */         }
/* 2587 */         return this.end >= 0 ? this.buf[this.pos] & 0xFF : -1;
/*      */       }
/* 2589 */       return this.in.peek();
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     byte peekByte()
/*      */       throws IOException
/*      */     {
/* 2599 */       int i = peek();
/* 2600 */       if (i < 0) {
/* 2601 */         throw new EOFException();
/*      */       }
/* 2603 */       return (byte)i;
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     public int read()
/*      */       throws IOException
/*      */     {
/* 2616 */       if (this.blkmode) {
/* 2617 */         if (this.pos == this.end) {
/* 2618 */           refill();
/*      */         }
/* 2620 */         return this.end >= 0 ? this.buf[(this.pos++)] & 0xFF : -1;
/*      */       }
/* 2622 */       return this.in.read();
/*      */     }
/*      */     
/*      */     public int read(byte[] paramArrayOfByte, int paramInt1, int paramInt2) throws IOException
/*      */     {
/* 2627 */       return read(paramArrayOfByte, paramInt1, paramInt2, false);
/*      */     }
/*      */     
/*      */     public long skip(long paramLong) throws IOException {
/* 2631 */       long l = paramLong;
/* 2632 */       while (l > 0L) { int i;
/* 2633 */         if (this.blkmode) {
/* 2634 */           if (this.pos == this.end) {
/* 2635 */             refill();
/*      */           }
/* 2637 */           if (this.end < 0) {
/*      */             break;
/*      */           }
/* 2640 */           i = (int)Math.min(l, this.end - this.pos);
/* 2641 */           l -= i;
/* 2642 */           this.pos += i;
/*      */         } else {
/* 2644 */           i = (int)Math.min(l, 1024L);
/* 2645 */           if ((i = this.in.read(this.buf, 0, i)) < 0) {
/*      */             break;
/*      */           }
/* 2648 */           l -= i;
/*      */         }
/*      */       }
/* 2651 */       return paramLong - l;
/*      */     }
/*      */     
/*      */     public int available() throws IOException {
/* 2655 */       if (this.blkmode) {
/* 2656 */         if ((this.pos == this.end) && (this.unread == 0))
/*      */         {
/* 2658 */           while ((i = readBlockHeader(false)) == 0) {}
/* 2659 */           switch (i)
/*      */           {
/*      */           case -2: 
/*      */             break;
/*      */           case -1: 
/* 2664 */             this.pos = 0;
/* 2665 */             this.end = -1;
/* 2666 */             break;
/*      */           
/*      */           default: 
/* 2669 */             this.pos = 0;
/* 2670 */             this.end = 0;
/* 2671 */             this.unread = i;
/*      */           }
/*      */           
/*      */         }
/*      */         
/*      */ 
/* 2677 */         int i = this.unread > 0 ? Math.min(this.in.available(), this.unread) : 0;
/* 2678 */         return this.end >= 0 ? this.end - this.pos + i : 0;
/*      */       }
/* 2680 */       return this.in.available();
/*      */     }
/*      */     
/*      */     public void close() throws IOException
/*      */     {
/* 2685 */       if (this.blkmode) {
/* 2686 */         this.pos = 0;
/* 2687 */         this.end = -1;
/* 2688 */         this.unread = 0;
/*      */       }
/* 2690 */       this.in.close();
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     int read(byte[] paramArrayOfByte, int paramInt1, int paramInt2, boolean paramBoolean)
/*      */       throws IOException
/*      */     {
/* 2701 */       if (paramInt2 == 0)
/* 2702 */         return 0;
/* 2703 */       int i; if (this.blkmode) {
/* 2704 */         if (this.pos == this.end) {
/* 2705 */           refill();
/*      */         }
/* 2707 */         if (this.end < 0) {
/* 2708 */           return -1;
/*      */         }
/* 2710 */         i = Math.min(paramInt2, this.end - this.pos);
/* 2711 */         System.arraycopy(this.buf, this.pos, paramArrayOfByte, paramInt1, i);
/* 2712 */         this.pos += i;
/* 2713 */         return i; }
/* 2714 */       if (paramBoolean) {
/* 2715 */         i = this.in.read(this.buf, 0, Math.min(paramInt2, 1024));
/* 2716 */         if (i > 0) {
/* 2717 */           System.arraycopy(this.buf, 0, paramArrayOfByte, paramInt1, i);
/*      */         }
/* 2719 */         return i;
/*      */       }
/* 2721 */       return this.in.read(paramArrayOfByte, paramInt1, paramInt2);
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     public void readFully(byte[] paramArrayOfByte)
/*      */       throws IOException
/*      */     {
/* 2734 */       readFully(paramArrayOfByte, 0, paramArrayOfByte.length, false);
/*      */     }
/*      */     
/*      */     public void readFully(byte[] paramArrayOfByte, int paramInt1, int paramInt2) throws IOException {
/* 2738 */       readFully(paramArrayOfByte, paramInt1, paramInt2, false);
/*      */     }
/*      */     
/*      */     public void readFully(byte[] paramArrayOfByte, int paramInt1, int paramInt2, boolean paramBoolean)
/*      */       throws IOException
/*      */     {
/* 2744 */       while (paramInt2 > 0) {
/* 2745 */         int i = read(paramArrayOfByte, paramInt1, paramInt2, paramBoolean);
/* 2746 */         if (i < 0) {
/* 2747 */           throw new EOFException();
/*      */         }
/* 2749 */         paramInt1 += i;
/* 2750 */         paramInt2 -= i;
/*      */       }
/*      */     }
/*      */     
/*      */     public int skipBytes(int paramInt) throws IOException {
/* 2755 */       return this.din.skipBytes(paramInt);
/*      */     }
/*      */     
/*      */     public boolean readBoolean() throws IOException {
/* 2759 */       int i = read();
/* 2760 */       if (i < 0) {
/* 2761 */         throw new EOFException();
/*      */       }
/* 2763 */       return i != 0;
/*      */     }
/*      */     
/*      */     public byte readByte() throws IOException {
/* 2767 */       int i = read();
/* 2768 */       if (i < 0) {
/* 2769 */         throw new EOFException();
/*      */       }
/* 2771 */       return (byte)i;
/*      */     }
/*      */     
/*      */     public int readUnsignedByte() throws IOException {
/* 2775 */       int i = read();
/* 2776 */       if (i < 0) {
/* 2777 */         throw new EOFException();
/*      */       }
/* 2779 */       return i;
/*      */     }
/*      */     
/*      */     public char readChar() throws IOException {
/* 2783 */       if (!this.blkmode) {
/* 2784 */         this.pos = 0;
/* 2785 */         this.in.readFully(this.buf, 0, 2);
/* 2786 */       } else if (this.end - this.pos < 2) {
/* 2787 */         return this.din.readChar();
/*      */       }
/* 2789 */       char c = Bits.getChar(this.buf, this.pos);
/* 2790 */       this.pos += 2;
/* 2791 */       return c;
/*      */     }
/*      */     
/*      */     public short readShort() throws IOException {
/* 2795 */       if (!this.blkmode) {
/* 2796 */         this.pos = 0;
/* 2797 */         this.in.readFully(this.buf, 0, 2);
/* 2798 */       } else if (this.end - this.pos < 2) {
/* 2799 */         return this.din.readShort();
/*      */       }
/* 2801 */       short s = Bits.getShort(this.buf, this.pos);
/* 2802 */       this.pos += 2;
/* 2803 */       return s;
/*      */     }
/*      */     
/*      */     public int readUnsignedShort() throws IOException {
/* 2807 */       if (!this.blkmode) {
/* 2808 */         this.pos = 0;
/* 2809 */         this.in.readFully(this.buf, 0, 2);
/* 2810 */       } else if (this.end - this.pos < 2) {
/* 2811 */         return this.din.readUnsignedShort();
/*      */       }
/* 2813 */       int i = Bits.getShort(this.buf, this.pos) & 0xFFFF;
/* 2814 */       this.pos += 2;
/* 2815 */       return i;
/*      */     }
/*      */     
/*      */     public int readInt() throws IOException {
/* 2819 */       if (!this.blkmode) {
/* 2820 */         this.pos = 0;
/* 2821 */         this.in.readFully(this.buf, 0, 4);
/* 2822 */       } else if (this.end - this.pos < 4) {
/* 2823 */         return this.din.readInt();
/*      */       }
/* 2825 */       int i = Bits.getInt(this.buf, this.pos);
/* 2826 */       this.pos += 4;
/* 2827 */       return i;
/*      */     }
/*      */     
/*      */     public float readFloat() throws IOException {
/* 2831 */       if (!this.blkmode) {
/* 2832 */         this.pos = 0;
/* 2833 */         this.in.readFully(this.buf, 0, 4);
/* 2834 */       } else if (this.end - this.pos < 4) {
/* 2835 */         return this.din.readFloat();
/*      */       }
/* 2837 */       float f = Bits.getFloat(this.buf, this.pos);
/* 2838 */       this.pos += 4;
/* 2839 */       return f;
/*      */     }
/*      */     
/*      */     public long readLong() throws IOException {
/* 2843 */       if (!this.blkmode) {
/* 2844 */         this.pos = 0;
/* 2845 */         this.in.readFully(this.buf, 0, 8);
/* 2846 */       } else if (this.end - this.pos < 8) {
/* 2847 */         return this.din.readLong();
/*      */       }
/* 2849 */       long l = Bits.getLong(this.buf, this.pos);
/* 2850 */       this.pos += 8;
/* 2851 */       return l;
/*      */     }
/*      */     
/*      */     public double readDouble() throws IOException {
/* 2855 */       if (!this.blkmode) {
/* 2856 */         this.pos = 0;
/* 2857 */         this.in.readFully(this.buf, 0, 8);
/* 2858 */       } else if (this.end - this.pos < 8) {
/* 2859 */         return this.din.readDouble();
/*      */       }
/* 2861 */       double d = Bits.getDouble(this.buf, this.pos);
/* 2862 */       this.pos += 8;
/* 2863 */       return d;
/*      */     }
/*      */     
/*      */     public String readUTF() throws IOException {
/* 2867 */       return readUTFBody(readUnsignedShort());
/*      */     }
/*      */     
/*      */     public String readLine() throws IOException
/*      */     {
/* 2872 */       return this.din.readLine();
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     void readBooleans(boolean[] paramArrayOfBoolean, int paramInt1, int paramInt2)
/*      */       throws IOException
/*      */     {
/* 2884 */       int j = paramInt1 + paramInt2;
/* 2885 */       while (paramInt1 < j) { int i;
/* 2886 */         if (!this.blkmode) {
/* 2887 */           int k = Math.min(j - paramInt1, 1024);
/* 2888 */           this.in.readFully(this.buf, 0, k);
/* 2889 */           i = paramInt1 + k;
/* 2890 */           this.pos = 0;
/* 2891 */         } else { if (this.end - this.pos < 1) {
/* 2892 */             paramArrayOfBoolean[(paramInt1++)] = this.din.readBoolean();
/* 2893 */             continue;
/*      */           }
/* 2895 */           i = Math.min(j, paramInt1 + this.end - this.pos);
/*      */         }
/*      */         
/* 2898 */         while (paramInt1 < i) {
/* 2899 */           paramArrayOfBoolean[(paramInt1++)] = Bits.getBoolean(this.buf, this.pos++);
/*      */         }
/*      */       }
/*      */     }
/*      */     
/*      */     void readChars(char[] paramArrayOfChar, int paramInt1, int paramInt2) throws IOException {
/* 2905 */       int j = paramInt1 + paramInt2;
/* 2906 */       while (paramInt1 < j) { int i;
/* 2907 */         if (!this.blkmode) {
/* 2908 */           int k = Math.min(j - paramInt1, 512);
/* 2909 */           this.in.readFully(this.buf, 0, k << 1);
/* 2910 */           i = paramInt1 + k;
/* 2911 */           this.pos = 0;
/* 2912 */         } else { if (this.end - this.pos < 2) {
/* 2913 */             paramArrayOfChar[(paramInt1++)] = this.din.readChar();
/* 2914 */             continue;
/*      */           }
/* 2916 */           i = Math.min(j, paramInt1 + (this.end - this.pos >> 1));
/*      */         }
/*      */         
/* 2919 */         while (paramInt1 < i) {
/* 2920 */           paramArrayOfChar[(paramInt1++)] = Bits.getChar(this.buf, this.pos);
/* 2921 */           this.pos += 2;
/*      */         }
/*      */       }
/*      */     }
/*      */     
/*      */     void readShorts(short[] paramArrayOfShort, int paramInt1, int paramInt2) throws IOException {
/* 2927 */       int j = paramInt1 + paramInt2;
/* 2928 */       while (paramInt1 < j) { int i;
/* 2929 */         if (!this.blkmode) {
/* 2930 */           int k = Math.min(j - paramInt1, 512);
/* 2931 */           this.in.readFully(this.buf, 0, k << 1);
/* 2932 */           i = paramInt1 + k;
/* 2933 */           this.pos = 0;
/* 2934 */         } else { if (this.end - this.pos < 2) {
/* 2935 */             paramArrayOfShort[(paramInt1++)] = this.din.readShort();
/* 2936 */             continue;
/*      */           }
/* 2938 */           i = Math.min(j, paramInt1 + (this.end - this.pos >> 1));
/*      */         }
/*      */         
/* 2941 */         while (paramInt1 < i) {
/* 2942 */           paramArrayOfShort[(paramInt1++)] = Bits.getShort(this.buf, this.pos);
/* 2943 */           this.pos += 2;
/*      */         }
/*      */       }
/*      */     }
/*      */     
/*      */     void readInts(int[] paramArrayOfInt, int paramInt1, int paramInt2) throws IOException {
/* 2949 */       int j = paramInt1 + paramInt2;
/* 2950 */       while (paramInt1 < j) { int i;
/* 2951 */         if (!this.blkmode) {
/* 2952 */           int k = Math.min(j - paramInt1, 256);
/* 2953 */           this.in.readFully(this.buf, 0, k << 2);
/* 2954 */           i = paramInt1 + k;
/* 2955 */           this.pos = 0;
/* 2956 */         } else { if (this.end - this.pos < 4) {
/* 2957 */             paramArrayOfInt[(paramInt1++)] = this.din.readInt();
/* 2958 */             continue;
/*      */           }
/* 2960 */           i = Math.min(j, paramInt1 + (this.end - this.pos >> 2));
/*      */         }
/*      */         
/* 2963 */         while (paramInt1 < i) {
/* 2964 */           paramArrayOfInt[(paramInt1++)] = Bits.getInt(this.buf, this.pos);
/* 2965 */           this.pos += 4;
/*      */         }
/*      */       }
/*      */     }
/*      */     
/*      */     void readFloats(float[] paramArrayOfFloat, int paramInt1, int paramInt2) throws IOException {
/* 2971 */       int j = paramInt1 + paramInt2;
/* 2972 */       while (paramInt1 < j) { int i;
/* 2973 */         if (!this.blkmode) {
/* 2974 */           i = Math.min(j - paramInt1, 256);
/* 2975 */           this.in.readFully(this.buf, 0, i << 2);
/* 2976 */           this.pos = 0;
/* 2977 */         } else { if (this.end - this.pos < 4) {
/* 2978 */             paramArrayOfFloat[(paramInt1++)] = this.din.readFloat();
/* 2979 */             continue;
/*      */           }
/* 2981 */           i = Math.min(j - paramInt1, this.end - this.pos >> 2);
/*      */         }
/*      */         
/* 2984 */         ObjectInputStream.bytesToFloats(this.buf, this.pos, paramArrayOfFloat, paramInt1, i);
/* 2985 */         paramInt1 += i;
/* 2986 */         this.pos += (i << 2);
/*      */       }
/*      */     }
/*      */     
/*      */     void readLongs(long[] paramArrayOfLong, int paramInt1, int paramInt2) throws IOException {
/* 2991 */       int j = paramInt1 + paramInt2;
/* 2992 */       while (paramInt1 < j) { int i;
/* 2993 */         if (!this.blkmode) {
/* 2994 */           int k = Math.min(j - paramInt1, 128);
/* 2995 */           this.in.readFully(this.buf, 0, k << 3);
/* 2996 */           i = paramInt1 + k;
/* 2997 */           this.pos = 0;
/* 2998 */         } else { if (this.end - this.pos < 8) {
/* 2999 */             paramArrayOfLong[(paramInt1++)] = this.din.readLong();
/* 3000 */             continue;
/*      */           }
/* 3002 */           i = Math.min(j, paramInt1 + (this.end - this.pos >> 3));
/*      */         }
/*      */         
/* 3005 */         while (paramInt1 < i) {
/* 3006 */           paramArrayOfLong[(paramInt1++)] = Bits.getLong(this.buf, this.pos);
/* 3007 */           this.pos += 8;
/*      */         }
/*      */       }
/*      */     }
/*      */     
/*      */     void readDoubles(double[] paramArrayOfDouble, int paramInt1, int paramInt2) throws IOException {
/* 3013 */       int j = paramInt1 + paramInt2;
/* 3014 */       while (paramInt1 < j) { int i;
/* 3015 */         if (!this.blkmode) {
/* 3016 */           i = Math.min(j - paramInt1, 128);
/* 3017 */           this.in.readFully(this.buf, 0, i << 3);
/* 3018 */           this.pos = 0;
/* 3019 */         } else { if (this.end - this.pos < 8) {
/* 3020 */             paramArrayOfDouble[(paramInt1++)] = this.din.readDouble();
/* 3021 */             continue;
/*      */           }
/* 3023 */           i = Math.min(j - paramInt1, this.end - this.pos >> 3);
/*      */         }
/*      */         
/* 3026 */         ObjectInputStream.bytesToDoubles(this.buf, this.pos, paramArrayOfDouble, paramInt1, i);
/* 3027 */         paramInt1 += i;
/* 3028 */         this.pos += (i << 3);
/*      */       }
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */     String readLongUTF()
/*      */       throws IOException
/*      */     {
/* 3038 */       return readUTFBody(readLong());
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */     private String readUTFBody(long paramLong)
/*      */       throws IOException
/*      */     {
/* 3047 */       StringBuilder localStringBuilder = new StringBuilder();
/* 3048 */       if (!this.blkmode) {
/* 3049 */         this.end = (this.pos = 0);
/*      */       }
/*      */       
/* 3052 */       while (paramLong > 0L) {
/* 3053 */         int i = this.end - this.pos;
/* 3054 */         if ((i >= 3) || (i == paramLong)) {
/* 3055 */           paramLong -= readUTFSpan(localStringBuilder, paramLong);
/*      */         }
/* 3057 */         else if (this.blkmode)
/*      */         {
/* 3059 */           paramLong -= readUTFChar(localStringBuilder, paramLong);
/*      */         }
/*      */         else {
/* 3062 */           if (i > 0) {
/* 3063 */             System.arraycopy(this.buf, this.pos, this.buf, 0, i);
/*      */           }
/* 3065 */           this.pos = 0;
/* 3066 */           this.end = ((int)Math.min(1024L, paramLong));
/* 3067 */           this.in.readFully(this.buf, i, this.end - i);
/*      */         }
/*      */       }
/*      */       
/*      */ 
/* 3072 */       return localStringBuilder.toString();
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     private long readUTFSpan(StringBuilder paramStringBuilder, long paramLong)
/*      */       throws IOException
/*      */     {
/* 3084 */       int i = 0;
/* 3085 */       int j = this.pos;
/* 3086 */       int k = Math.min(this.end - this.pos, 256);
/*      */       
/* 3088 */       int m = this.pos + (paramLong > k ? k - 2 : (int)paramLong);
/* 3089 */       int n = 0;
/*      */       try
/*      */       {
/* 3092 */         while (this.pos < m)
/*      */         {
/* 3094 */           int i1 = this.buf[(this.pos++)] & 0xFF;
/* 3095 */           int i2; switch (i1 >> 4) {
/*      */           case 0: 
/*      */           case 1: 
/*      */           case 2: 
/*      */           case 3: 
/*      */           case 4: 
/*      */           case 5: 
/*      */           case 6: 
/*      */           case 7: 
/* 3104 */             this.cbuf[(i++)] = ((char)i1);
/* 3105 */             break;
/*      */           
/*      */           case 12: 
/*      */           case 13: 
/* 3109 */             i2 = this.buf[(this.pos++)];
/* 3110 */             if ((i2 & 0xC0) != 128) {
/* 3111 */               throw new UTFDataFormatException();
/*      */             }
/* 3113 */             this.cbuf[(i++)] = ((char)((i1 & 0x1F) << 6 | (i2 & 0x3F) << 0));
/*      */             
/* 3115 */             break;
/*      */           
/*      */           case 14: 
/* 3118 */             int i3 = this.buf[(this.pos + 1)];
/* 3119 */             i2 = this.buf[(this.pos + 0)];
/* 3120 */             this.pos += 2;
/* 3121 */             if (((i2 & 0xC0) != 128) || ((i3 & 0xC0) != 128)) {
/* 3122 */               throw new UTFDataFormatException();
/*      */             }
/* 3124 */             this.cbuf[(i++)] = ((char)((i1 & 0xF) << 12 | (i2 & 0x3F) << 6 | (i3 & 0x3F) << 0));
/*      */             
/*      */ 
/* 3127 */             break;
/*      */           case 8: case 9: case 10: 
/*      */           case 11: default: 
/* 3130 */             throw new UTFDataFormatException();
/*      */           }
/*      */         }
/*      */       } catch (ArrayIndexOutOfBoundsException localArrayIndexOutOfBoundsException) {
/* 3134 */         n = 1;
/*      */       } finally {
/* 3136 */         if ((n != 0) || (this.pos - j > paramLong))
/*      */         {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 3142 */           this.pos = (j + (int)paramLong);
/* 3143 */           throw new UTFDataFormatException();
/*      */         }
/*      */       }
/*      */       
/* 3147 */       paramStringBuilder.append(this.cbuf, 0, i);
/* 3148 */       return this.pos - j;
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
/*      */     private int readUTFChar(StringBuilder paramStringBuilder, long paramLong)
/*      */       throws IOException
/*      */     {
/* 3162 */       int i = readByte() & 0xFF;
/* 3163 */       int j; switch (i >> 4) {
/*      */       case 0: 
/*      */       case 1: 
/*      */       case 2: 
/*      */       case 3: 
/*      */       case 4: 
/*      */       case 5: 
/*      */       case 6: 
/*      */       case 7: 
/* 3172 */         paramStringBuilder.append((char)i);
/* 3173 */         return 1;
/*      */       
/*      */       case 12: 
/*      */       case 13: 
/* 3177 */         if (paramLong < 2L) {
/* 3178 */           throw new UTFDataFormatException();
/*      */         }
/* 3180 */         j = readByte();
/* 3181 */         if ((j & 0xC0) != 128) {
/* 3182 */           throw new UTFDataFormatException();
/*      */         }
/* 3184 */         paramStringBuilder.append((char)((i & 0x1F) << 6 | (j & 0x3F) << 0));
/*      */         
/* 3186 */         return 2;
/*      */       
/*      */       case 14: 
/* 3189 */         if (paramLong < 3L) {
/* 3190 */           if (paramLong == 2L) {
/* 3191 */             readByte();
/*      */           }
/* 3193 */           throw new UTFDataFormatException();
/*      */         }
/* 3195 */         j = readByte();
/* 3196 */         int k = readByte();
/* 3197 */         if (((j & 0xC0) != 128) || ((k & 0xC0) != 128)) {
/* 3198 */           throw new UTFDataFormatException();
/*      */         }
/* 3200 */         paramStringBuilder.append((char)((i & 0xF) << 12 | (j & 0x3F) << 6 | (k & 0x3F) << 0));
/*      */         
/*      */ 
/* 3203 */         return 3;
/*      */       }
/*      */       
/* 3206 */       throw new UTFDataFormatException();
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private static class HandleTable
/*      */   {
/*      */     private static final byte STATUS_OK = 1;
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     private static final byte STATUS_UNKNOWN = 2;
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     private static final byte STATUS_EXCEPTION = 3;
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     byte[] status;
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     Object[] entries;
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     HandleList[] deps;
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 3254 */     int lowDep = -1;
/*      */     
/* 3256 */     int size = 0;
/*      */     
/*      */ 
/*      */ 
/*      */     HandleTable(int paramInt)
/*      */     {
/* 3262 */       this.status = new byte[paramInt];
/* 3263 */       this.entries = new Object[paramInt];
/* 3264 */       this.deps = new HandleList[paramInt];
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     int assign(Object paramObject)
/*      */     {
/* 3274 */       if (this.size >= this.entries.length) {
/* 3275 */         grow();
/*      */       }
/* 3277 */       this.status[this.size] = 2;
/* 3278 */       this.entries[this.size] = paramObject;
/* 3279 */       return this.size++;
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     void markDependency(int paramInt1, int paramInt2)
/*      */     {
/* 3289 */       if ((paramInt1 == -1) || (paramInt2 == -1)) {
/* 3290 */         return;
/*      */       }
/* 3292 */       switch (this.status[paramInt1])
/*      */       {
/*      */       case 2: 
/* 3295 */         switch (this.status[paramInt2])
/*      */         {
/*      */         case 1: 
/*      */           break;
/*      */         
/*      */ 
/*      */         case 3: 
/* 3302 */           markException(paramInt1, (ClassNotFoundException)this.entries[paramInt2]);
/*      */           
/* 3304 */           break;
/*      */         
/*      */ 
/*      */         case 2: 
/* 3308 */           if (this.deps[paramInt2] == null) {
/* 3309 */             this.deps[paramInt2] = new HandleList();
/*      */           }
/* 3311 */           this.deps[paramInt2].add(paramInt1);
/*      */           
/*      */ 
/* 3314 */           if ((this.lowDep < 0) || (this.lowDep > paramInt2)) {
/* 3315 */             this.lowDep = paramInt2;
/*      */           }
/*      */           
/*      */           break;
/*      */         default: 
/* 3320 */           throw new InternalError();
/*      */         }
/*      */         
/*      */         
/*      */         break;
/*      */       case 3: 
/*      */         break;
/*      */       default: 
/* 3328 */         throw new InternalError();
/*      */       }
/*      */       
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     void markException(int paramInt, ClassNotFoundException paramClassNotFoundException)
/*      */     {
/* 3339 */       switch (this.status[paramInt]) {
/*      */       case 2: 
/* 3341 */         this.status[paramInt] = 3;
/* 3342 */         this.entries[paramInt] = paramClassNotFoundException;
/*      */         
/*      */ 
/* 3345 */         HandleList localHandleList = this.deps[paramInt];
/* 3346 */         if (localHandleList != null) {
/* 3347 */           int i = localHandleList.size();
/* 3348 */           for (int j = 0; j < i; j++) {
/* 3349 */             markException(localHandleList.get(j), paramClassNotFoundException);
/*      */           }
/* 3351 */           this.deps[paramInt] = null; }
/* 3352 */         break;
/*      */       
/*      */       case 3: 
/*      */         break;
/*      */       
/*      */ 
/*      */       default: 
/* 3359 */         throw new InternalError();
/*      */       }
/*      */       
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */     void finish(int paramInt)
/*      */     {
/*      */       int i;
/*      */       
/* 3370 */       if (this.lowDep < 0)
/*      */       {
/* 3372 */         i = paramInt + 1;
/* 3373 */       } else if (this.lowDep >= paramInt)
/*      */       {
/* 3375 */         i = this.size;
/* 3376 */         this.lowDep = -1;
/*      */       }
/*      */       else {
/* 3379 */         return;
/*      */       }
/*      */       
/*      */ 
/* 3383 */       for (int j = paramInt; j < i; j++) {
/* 3384 */         switch (this.status[j]) {
/*      */         case 2: 
/* 3386 */           this.status[j] = 1;
/* 3387 */           this.deps[j] = null;
/* 3388 */           break;
/*      */         
/*      */         case 1: 
/*      */         case 3: 
/*      */           break;
/*      */         
/*      */         default: 
/* 3395 */           throw new InternalError();
/*      */         }
/*      */         
/*      */       }
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     void setObject(int paramInt, Object paramObject)
/*      */     {
/* 3407 */       switch (this.status[paramInt]) {
/*      */       case 1: 
/*      */       case 2: 
/* 3410 */         this.entries[paramInt] = paramObject;
/* 3411 */         break;
/*      */       
/*      */       case 3: 
/*      */         break;
/*      */       
/*      */       default: 
/* 3417 */         throw new InternalError();
/*      */       }
/*      */       
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */     Object lookupObject(int paramInt)
/*      */     {
/* 3427 */       return (paramInt != -1) && (this.status[paramInt] != 3) ? this.entries[paramInt] : null;
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     ClassNotFoundException lookupException(int paramInt)
/*      */     {
/* 3438 */       return (paramInt != -1) && (this.status[paramInt] == 3) ? (ClassNotFoundException)this.entries[paramInt] : null;
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     void clear()
/*      */     {
/* 3447 */       Arrays.fill(this.status, 0, this.size, (byte)0);
/* 3448 */       Arrays.fill(this.entries, 0, this.size, null);
/* 3449 */       Arrays.fill(this.deps, 0, this.size, null);
/* 3450 */       this.lowDep = -1;
/* 3451 */       this.size = 0;
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */     int size()
/*      */     {
/* 3458 */       return this.size;
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */     private void grow()
/*      */     {
/* 3465 */       int i = (this.entries.length << 1) + 1;
/*      */       
/* 3467 */       byte[] arrayOfByte = new byte[i];
/* 3468 */       Object[] arrayOfObject = new Object[i];
/* 3469 */       HandleList[] arrayOfHandleList = new HandleList[i];
/*      */       
/* 3471 */       System.arraycopy(this.status, 0, arrayOfByte, 0, this.size);
/* 3472 */       System.arraycopy(this.entries, 0, arrayOfObject, 0, this.size);
/* 3473 */       System.arraycopy(this.deps, 0, arrayOfHandleList, 0, this.size);
/*      */       
/* 3475 */       this.status = arrayOfByte;
/* 3476 */       this.entries = arrayOfObject;
/* 3477 */       this.deps = arrayOfHandleList;
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */     private static class HandleList
/*      */     {
/* 3484 */       private int[] list = new int[4];
/* 3485 */       private int size = 0;
/*      */       
/*      */ 
/*      */ 
/*      */       public void add(int paramInt)
/*      */       {
/* 3491 */         if (this.size >= this.list.length) {
/* 3492 */           int[] arrayOfInt = new int[this.list.length << 1];
/* 3493 */           System.arraycopy(this.list, 0, arrayOfInt, 0, this.list.length);
/* 3494 */           this.list = arrayOfInt;
/*      */         }
/* 3496 */         this.list[(this.size++)] = paramInt;
/*      */       }
/*      */       
/*      */       public int get(int paramInt) {
/* 3500 */         if (paramInt >= this.size) {
/* 3501 */           throw new ArrayIndexOutOfBoundsException();
/*      */         }
/* 3503 */         return this.list[paramInt];
/*      */       }
/*      */       
/*      */       public int size() {
/* 3507 */         return this.size;
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   private static Object cloneArray(Object paramObject)
/*      */   {
/* 3516 */     if ((paramObject instanceof Object[]))
/* 3517 */       return ((Object[])paramObject).clone();
/* 3518 */     if ((paramObject instanceof boolean[]))
/* 3519 */       return ((boolean[])paramObject).clone();
/* 3520 */     if ((paramObject instanceof byte[]))
/* 3521 */       return ((byte[])paramObject).clone();
/* 3522 */     if ((paramObject instanceof char[]))
/* 3523 */       return ((char[])paramObject).clone();
/* 3524 */     if ((paramObject instanceof double[]))
/* 3525 */       return ((double[])paramObject).clone();
/* 3526 */     if ((paramObject instanceof float[]))
/* 3527 */       return ((float[])paramObject).clone();
/* 3528 */     if ((paramObject instanceof int[]))
/* 3529 */       return ((int[])paramObject).clone();
/* 3530 */     if ((paramObject instanceof long[]))
/* 3531 */       return ((long[])paramObject).clone();
/* 3532 */     if ((paramObject instanceof short[])) {
/* 3533 */       return ((short[])paramObject).clone();
/*      */     }
/* 3535 */     throw new AssertionError();
/*      */   }
/*      */   
/*      */   private static native void bytesToFloats(byte[] paramArrayOfByte, int paramInt1, float[] paramArrayOfFloat, int paramInt2, int paramInt3);
/*      */   
/*      */   private static native void bytesToDoubles(byte[] paramArrayOfByte, int paramInt1, double[] paramArrayOfDouble, int paramInt2, int paramInt3);
/*      */ }


/* Location:              /Users/guanxiaoda/Documents/workspace/rt.jar!/java/io/ObjectInputStream.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */