/*      */ package java.awt.datatransfer;
/*      */ 
/*      */ import java.io.ByteArrayInputStream;
/*      */ import java.io.CharArrayReader;
/*      */ import java.io.Externalizable;
/*      */ import java.io.IOException;
/*      */ import java.io.InputStream;
/*      */ import java.io.InputStreamReader;
/*      */ import java.io.ObjectInput;
/*      */ import java.io.ObjectOutput;
/*      */ import java.io.OptionalDataException;
/*      */ import java.io.Reader;
/*      */ import java.io.Serializable;
/*      */ import java.io.StringReader;
/*      */ import java.nio.ByteBuffer;
/*      */ import java.nio.CharBuffer;
/*      */ import java.util.Arrays;
/*      */ import java.util.Collections;
/*      */ import java.util.Comparator;
/*      */ import java.util.List;
/*      */ import java.util.Objects;
/*      */ import sun.awt.datatransfer.DataTransferer;
/*      */ import sun.awt.datatransfer.DataTransferer.DataFlavorComparator;
/*      */ import sun.reflect.misc.ReflectUtil;
/*      */ import sun.security.util.SecurityConstants;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class DataFlavor
/*      */   implements Externalizable, Cloneable
/*      */ {
/*      */   private static final long serialVersionUID = 8367026044764648243L;
/*  122 */   private static final Class<InputStream> ioInputStreamClass = InputStream.class;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   protected static final Class<?> tryToLoadClass(String paramString, ClassLoader paramClassLoader)
/*      */     throws ClassNotFoundException
/*      */   {
/*  137 */     ReflectUtil.checkPackageAccess(paramString);
/*      */     try {
/*  139 */       SecurityManager localSecurityManager = System.getSecurityManager();
/*  140 */       if (localSecurityManager != null) {
/*  141 */         localSecurityManager.checkPermission(SecurityConstants.GET_CLASSLOADER_PERMISSION);
/*      */       }
/*  143 */       ClassLoader localClassLoader = ClassLoader.getSystemClassLoader();
/*      */       try
/*      */       {
/*  146 */         return Class.forName(paramString, true, localClassLoader);
/*      */       }
/*      */       catch (ClassNotFoundException localClassNotFoundException1)
/*      */       {
/*  150 */         localClassLoader = Thread.currentThread().getContextClassLoader();
/*  151 */         if (localClassLoader != null) {
/*      */           try {
/*  153 */             return Class.forName(paramString, true, localClassLoader);
/*      */           }
/*      */           catch (ClassNotFoundException localClassNotFoundException2) {}
/*      */         }
/*      */       }
/*      */       
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  163 */       return Class.forName(paramString, true, paramClassLoader);
/*      */     }
/*      */     catch (SecurityException localSecurityException) {}
/*      */   }
/*      */   
/*      */   private static DataFlavor createConstant(Class<?> paramClass, String paramString)
/*      */   {
/*      */     try {
/*  171 */       return new DataFlavor(paramClass, paramString);
/*      */     } catch (Exception localException) {}
/*  173 */     return null;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   private static DataFlavor createConstant(String paramString1, String paramString2)
/*      */   {
/*      */     try
/*      */     {
/*  182 */       return new DataFlavor(paramString1, paramString2);
/*      */     } catch (Exception localException) {}
/*  184 */     return null;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   private static DataFlavor initHtmlDataFlavor(String paramString)
/*      */   {
/*      */     try
/*      */     {
/*  193 */       return new DataFlavor("text/html; class=java.lang.String;document=" + paramString + ";charset=Unicode");
/*      */     }
/*      */     catch (Exception localException) {}
/*  196 */     return null;
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
/*  208 */   public static final DataFlavor stringFlavor = createConstant(String.class, "Unicode String");
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  218 */   public static final DataFlavor imageFlavor = createConstant("image/x-java-image; class=java.awt.Image", "Image");
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   @Deprecated
/*  237 */   public static final DataFlavor plainTextFlavor = createConstant("text/plain; charset=unicode; class=java.io.InputStream", "Plain Text");
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static final String javaSerializedObjectMimeType = "application/x-java-serialized-object";
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  256 */   public static final DataFlavor javaFileListFlavor = createConstant("application/x-java-file-list;class=java.util.List", null);
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static final String javaJVMLocalObjectMimeType = "application/x-java-jvm-local-objectref";
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static final String javaRemoteObjectMimeType = "application/x-java-remote-object";
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  296 */   public static DataFlavor selectionHtmlFlavor = initHtmlDataFlavor("selection");
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  308 */   public static DataFlavor fragmentHtmlFlavor = initHtmlDataFlavor("fragment");
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  321 */   public static DataFlavor allHtmlFlavor = initHtmlDataFlavor("all");
/*      */   
/*      */ 
/*      */   private static Comparator<DataFlavor> textFlavorComparator;
/*      */   
/*      */ 
/*      */   transient int atom;
/*      */   
/*      */ 
/*      */   MimeType mimeType;
/*      */   
/*      */ 
/*      */   private String humanPresentableName;
/*      */   
/*      */   private Class<?> representationClass;
/*      */   
/*      */ 
/*      */   public DataFlavor() {}
/*      */   
/*      */ 
/*      */   private DataFlavor(String paramString1, String paramString2, MimeTypeParameterList paramMimeTypeParameterList, Class<?> paramClass, String paramString3)
/*      */   {
/*  343 */     if (paramString1 == null) {
/*  344 */       throw new NullPointerException("primaryType");
/*      */     }
/*  346 */     if (paramString2 == null) {
/*  347 */       throw new NullPointerException("subType");
/*      */     }
/*  349 */     if (paramClass == null) {
/*  350 */       throw new NullPointerException("representationClass");
/*      */     }
/*      */     
/*  353 */     if (paramMimeTypeParameterList == null) { paramMimeTypeParameterList = new MimeTypeParameterList();
/*      */     }
/*  355 */     paramMimeTypeParameterList.set("class", paramClass.getName());
/*      */     
/*  357 */     if (paramString3 == null) {
/*  358 */       paramString3 = paramMimeTypeParameterList.get("humanPresentableName");
/*      */       
/*  360 */       if (paramString3 == null) {
/*  361 */         paramString3 = paramString1 + "/" + paramString2;
/*      */       }
/*      */     }
/*      */     try {
/*  365 */       this.mimeType = new MimeType(paramString1, paramString2, paramMimeTypeParameterList);
/*      */     } catch (MimeTypeParseException localMimeTypeParseException) {
/*  367 */       throw new IllegalArgumentException("MimeType Parse Exception: " + localMimeTypeParseException.getMessage());
/*      */     }
/*      */     
/*  370 */     this.representationClass = paramClass;
/*  371 */     this.humanPresentableName = paramString3;
/*      */     
/*  373 */     this.mimeType.removeParameter("humanPresentableName");
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
/*      */   public DataFlavor(Class<?> paramClass, String paramString)
/*      */   {
/*  392 */     this("application", "x-java-serialized-object", null, paramClass, paramString);
/*  393 */     if (paramClass == null) {
/*  394 */       throw new NullPointerException("representationClass");
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
/*      */   public DataFlavor(String paramString1, String paramString2)
/*      */   {
/*  429 */     if (paramString1 == null) {
/*  430 */       throw new NullPointerException("mimeType");
/*      */     }
/*      */     try {
/*  433 */       initialize(paramString1, paramString2, getClass().getClassLoader());
/*      */     } catch (MimeTypeParseException localMimeTypeParseException) {
/*  435 */       throw new IllegalArgumentException("failed to parse:" + paramString1);
/*      */     } catch (ClassNotFoundException localClassNotFoundException) {
/*  437 */       throw new IllegalArgumentException("can't find specified class: " + localClassNotFoundException.getMessage());
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
/*      */   public DataFlavor(String paramString1, String paramString2, ClassLoader paramClassLoader)
/*      */     throws ClassNotFoundException
/*      */   {
/*  469 */     if (paramString1 == null) {
/*  470 */       throw new NullPointerException("mimeType");
/*      */     }
/*      */     try {
/*  473 */       initialize(paramString1, paramString2, paramClassLoader);
/*      */     } catch (MimeTypeParseException localMimeTypeParseException) {
/*  475 */       throw new IllegalArgumentException("failed to parse:" + paramString1);
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
/*      */   public DataFlavor(String paramString)
/*      */     throws ClassNotFoundException
/*      */   {
/*  497 */     if (paramString == null) {
/*  498 */       throw new NullPointerException("mimeType");
/*      */     }
/*      */     try {
/*  501 */       initialize(paramString, null, getClass().getClassLoader());
/*      */     } catch (MimeTypeParseException localMimeTypeParseException) {
/*  503 */       throw new IllegalArgumentException("failed to parse:" + paramString);
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
/*      */   private void initialize(String paramString1, String paramString2, ClassLoader paramClassLoader)
/*      */     throws MimeTypeParseException, ClassNotFoundException
/*      */   {
/*  522 */     if (paramString1 == null) {
/*  523 */       throw new NullPointerException("mimeType");
/*      */     }
/*      */     
/*  526 */     this.mimeType = new MimeType(paramString1);
/*      */     
/*  528 */     String str = getParameter("class");
/*      */     
/*  530 */     if (str == null) {
/*  531 */       if ("application/x-java-serialized-object".equals(this.mimeType.getBaseType()))
/*      */       {
/*  533 */         throw new IllegalArgumentException("no representation class specified for:" + paramString1);
/*      */       }
/*  535 */       this.representationClass = InputStream.class;
/*      */     } else {
/*  537 */       this.representationClass = tryToLoadClass(str, paramClassLoader);
/*      */     }
/*      */     
/*  540 */     this.mimeType.setParameter("class", this.representationClass.getName());
/*      */     
/*  542 */     if (paramString2 == null) {
/*  543 */       paramString2 = this.mimeType.getParameter("humanPresentableName");
/*  544 */       if (paramString2 == null) {
/*  545 */         paramString2 = this.mimeType.getPrimaryType() + "/" + this.mimeType.getSubType();
/*      */       }
/*      */     }
/*  548 */     this.humanPresentableName = paramString2;
/*      */     
/*  550 */     this.mimeType.removeParameter("humanPresentableName");
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
/*      */   public String toString()
/*      */   {
/*  566 */     String str = getClass().getName();
/*  567 */     str = str + "[" + paramString() + "]";
/*  568 */     return str;
/*      */   }
/*      */   
/*      */   private String paramString() {
/*  572 */     String str = "";
/*  573 */     str = str + "mimetype=";
/*  574 */     if (this.mimeType == null) {
/*  575 */       str = str + "null";
/*      */     } else {
/*  577 */       str = str + this.mimeType.getBaseType();
/*      */     }
/*  579 */     str = str + ";representationclass=";
/*  580 */     if (this.representationClass == null) {
/*  581 */       str = str + "null";
/*      */     } else {
/*  583 */       str = str + this.representationClass.getName();
/*      */     }
/*  585 */     if ((DataTransferer.isFlavorCharsetTextType(this)) && (
/*  586 */       (isRepresentationClassInputStream()) || 
/*  587 */       (isRepresentationClassByteBuffer()) || 
/*  588 */       (byte[].class.equals(this.representationClass))))
/*      */     {
/*  590 */       str = str + ";charset=" + DataTransferer.getTextCharset(this);
/*      */     }
/*  592 */     return str;
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
/*      */   public static final DataFlavor getTextPlainUnicodeFlavor()
/*      */   {
/*  612 */     String str = null;
/*  613 */     DataTransferer localDataTransferer = DataTransferer.getInstance();
/*  614 */     if (localDataTransferer != null) {
/*  615 */       str = localDataTransferer.getDefaultUnicodeEncoding();
/*      */     }
/*  617 */     return new DataFlavor("text/plain;charset=" + str + ";class=java.io.InputStream", "Plain Text");
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
/*      */   public static final DataFlavor selectBestTextFlavor(DataFlavor[] paramArrayOfDataFlavor)
/*      */   {
/*  740 */     if ((paramArrayOfDataFlavor == null) || (paramArrayOfDataFlavor.length == 0)) {
/*  741 */       return null;
/*      */     }
/*      */     
/*  744 */     if (textFlavorComparator == null) {
/*  745 */       textFlavorComparator = new TextFlavorComparator();
/*      */     }
/*      */     
/*      */ 
/*  749 */     DataFlavor localDataFlavor = (DataFlavor)Collections.max(Arrays.asList(paramArrayOfDataFlavor), textFlavorComparator);
/*      */     
/*      */ 
/*  752 */     if (!localDataFlavor.isFlavorTextType()) {
/*  753 */       return null;
/*      */     }
/*      */     
/*  756 */     return localDataFlavor;
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
/*      */   static class TextFlavorComparator
/*      */     extends DataTransferer.DataFlavorComparator
/*      */   {
/*      */     public int compare(Object paramObject1, Object paramObject2)
/*      */     {
/*  785 */       DataFlavor localDataFlavor1 = (DataFlavor)paramObject1;
/*  786 */       DataFlavor localDataFlavor2 = (DataFlavor)paramObject2;
/*      */       
/*  788 */       if (localDataFlavor1.isFlavorTextType()) {
/*  789 */         if (localDataFlavor2.isFlavorTextType()) {
/*  790 */           return super.compare(paramObject1, paramObject2);
/*      */         }
/*  792 */         return 1;
/*      */       }
/*  794 */       if (localDataFlavor2.isFlavorTextType()) {
/*  795 */         return -1;
/*      */       }
/*  797 */       return 0;
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
/*      */   public Reader getReaderForText(Transferable paramTransferable)
/*      */     throws UnsupportedFlavorException, IOException
/*      */   {
/*  845 */     Object localObject1 = paramTransferable.getTransferData(this);
/*  846 */     if (localObject1 == null) {
/*  847 */       throw new IllegalArgumentException("getTransferData() returned null");
/*      */     }
/*      */     
/*      */ 
/*  851 */     if ((localObject1 instanceof Reader))
/*  852 */       return (Reader)localObject1;
/*  853 */     if ((localObject1 instanceof String))
/*  854 */       return new StringReader((String)localObject1);
/*  855 */     if ((localObject1 instanceof CharBuffer)) {
/*  856 */       localObject2 = (CharBuffer)localObject1;
/*  857 */       int i = ((CharBuffer)localObject2).remaining();
/*  858 */       char[] arrayOfChar = new char[i];
/*  859 */       ((CharBuffer)localObject2).get(arrayOfChar, 0, i);
/*  860 */       return new CharArrayReader(arrayOfChar); }
/*  861 */     if ((localObject1 instanceof char[])) {
/*  862 */       return new CharArrayReader((char[])localObject1);
/*      */     }
/*      */     
/*  865 */     Object localObject2 = null;
/*      */     
/*  867 */     if ((localObject1 instanceof InputStream)) {
/*  868 */       localObject2 = (InputStream)localObject1;
/*  869 */     } else if ((localObject1 instanceof ByteBuffer)) {
/*  870 */       localObject3 = (ByteBuffer)localObject1;
/*  871 */       int j = ((ByteBuffer)localObject3).remaining();
/*  872 */       byte[] arrayOfByte = new byte[j];
/*  873 */       ((ByteBuffer)localObject3).get(arrayOfByte, 0, j);
/*  874 */       localObject2 = new ByteArrayInputStream(arrayOfByte);
/*  875 */     } else if ((localObject1 instanceof byte[])) {
/*  876 */       localObject2 = new ByteArrayInputStream((byte[])localObject1);
/*      */     }
/*      */     
/*  879 */     if (localObject2 == null) {
/*  880 */       throw new IllegalArgumentException("transfer data is not Reader, String, CharBuffer, char array, InputStream, ByteBuffer, or byte array");
/*      */     }
/*      */     
/*  883 */     Object localObject3 = getParameter("charset");
/*  884 */     return localObject3 == null ? new InputStreamReader((InputStream)localObject2) : new InputStreamReader((InputStream)localObject2, (String)localObject3);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public String getMimeType()
/*      */   {
/*  894 */     return this.mimeType != null ? this.mimeType.toString() : null;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public Class<?> getRepresentationClass()
/*      */   {
/*  906 */     return this.representationClass;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public String getHumanPresentableName()
/*      */   {
/*  917 */     return this.humanPresentableName;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public String getPrimaryType()
/*      */   {
/*  925 */     return this.mimeType != null ? this.mimeType.getPrimaryType() : null;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public String getSubType()
/*      */   {
/*  933 */     return this.mimeType != null ? this.mimeType.getSubType() : null;
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
/*      */   public String getParameter(String paramString)
/*      */   {
/*  946 */     if (paramString.equals("humanPresentableName")) {
/*  947 */       return this.humanPresentableName;
/*      */     }
/*      */     
/*  950 */     return this.mimeType != null ? this.mimeType.getParameter(paramString) : null;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public void setHumanPresentableName(String paramString)
/*      */   {
/*  961 */     this.humanPresentableName = paramString;
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
/*      */   public boolean equals(Object paramObject)
/*      */   {
/*  986 */     return ((paramObject instanceof DataFlavor)) && (equals((DataFlavor)paramObject));
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
/*      */   public boolean equals(DataFlavor paramDataFlavor)
/*      */   {
/* 1001 */     if (paramDataFlavor == null) {
/* 1002 */       return false;
/*      */     }
/* 1004 */     if (this == paramDataFlavor) {
/* 1005 */       return true;
/*      */     }
/*      */     
/* 1008 */     if (!Objects.equals(getRepresentationClass(), paramDataFlavor.getRepresentationClass())) {
/* 1009 */       return false;
/*      */     }
/*      */     
/* 1012 */     if (this.mimeType == null) {
/* 1013 */       if (paramDataFlavor.mimeType != null) {
/* 1014 */         return false;
/*      */       }
/*      */     } else {
/* 1017 */       if (!this.mimeType.match(paramDataFlavor.mimeType)) {
/* 1018 */         return false;
/*      */       }
/*      */       
/* 1021 */       if ("text".equals(getPrimaryType())) { String str1;
/* 1022 */         String str2; if ((DataTransferer.doesSubtypeSupportCharset(this)) && (this.representationClass != null))
/*      */         {
/* 1024 */           if (!isStandardTextRepresentationClass())
/*      */           {
/* 1026 */             str1 = DataTransferer.canonicalName(getParameter("charset"));
/*      */             
/* 1028 */             str2 = DataTransferer.canonicalName(paramDataFlavor.getParameter("charset"));
/* 1029 */             if (!Objects.equals(str1, str2)) {
/* 1030 */               return false;
/*      */             }
/*      */           }
/*      */         }
/* 1034 */         if ("html".equals(getSubType())) {
/* 1035 */           str1 = getParameter("document");
/* 1036 */           str2 = paramDataFlavor.getParameter("document");
/* 1037 */           if (!Objects.equals(str1, str2)) {
/* 1038 */             return false;
/*      */           }
/*      */         }
/*      */       }
/*      */     }
/*      */     
/* 1044 */     return true;
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
/*      */   @Deprecated
/*      */   public boolean equals(String paramString)
/*      */   {
/* 1062 */     if ((paramString == null) || (this.mimeType == null))
/* 1063 */       return false;
/* 1064 */     return isMimeTypeEqual(paramString);
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
/*      */   public int hashCode()
/*      */   {
/* 1078 */     int i = 0;
/*      */     
/* 1080 */     if (this.representationClass != null) {
/* 1081 */       i += this.representationClass.hashCode();
/*      */     }
/*      */     
/* 1084 */     if (this.mimeType != null) {
/* 1085 */       String str1 = this.mimeType.getPrimaryType();
/* 1086 */       if (str1 != null) {
/* 1087 */         i += str1.hashCode();
/*      */       }
/*      */       
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1094 */       if ("text".equals(str1)) { String str2;
/* 1095 */         if ((DataTransferer.doesSubtypeSupportCharset(this)) && (this.representationClass != null))
/*      */         {
/* 1097 */           if (!isStandardTextRepresentationClass()) {
/* 1098 */             str2 = DataTransferer.canonicalName(getParameter("charset"));
/* 1099 */             if (str2 != null) {
/* 1100 */               i += str2.hashCode();
/*      */             }
/*      */           }
/*      */         }
/* 1104 */         if ("html".equals(getSubType())) {
/* 1105 */           str2 = getParameter("document");
/* 1106 */           if (str2 != null) {
/* 1107 */             i += str2.hashCode();
/*      */           }
/*      */         }
/*      */       }
/*      */     }
/*      */     
/* 1113 */     return i;
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
/*      */   public boolean match(DataFlavor paramDataFlavor)
/*      */   {
/* 1127 */     return equals(paramDataFlavor);
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
/*      */   public boolean isMimeTypeEqual(String paramString)
/*      */   {
/* 1143 */     if (paramString == null) {
/* 1144 */       throw new NullPointerException("mimeType");
/*      */     }
/* 1146 */     if (this.mimeType == null) {
/* 1147 */       return false;
/*      */     }
/*      */     try {
/* 1150 */       return this.mimeType.match(new MimeType(paramString));
/*      */     } catch (MimeTypeParseException localMimeTypeParseException) {}
/* 1152 */     return false;
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
/*      */   public final boolean isMimeTypeEqual(DataFlavor paramDataFlavor)
/*      */   {
/* 1166 */     return isMimeTypeEqual(paramDataFlavor.mimeType);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private boolean isMimeTypeEqual(MimeType paramMimeType)
/*      */   {
/* 1178 */     if (this.mimeType == null) {
/* 1179 */       return paramMimeType == null;
/*      */     }
/* 1181 */     return this.mimeType.match(paramMimeType);
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
/*      */   private boolean isStandardTextRepresentationClass()
/*      */   {
/* 1195 */     return (isRepresentationClassReader()) || (String.class.equals(this.representationClass)) || (isRepresentationClassCharBuffer()) || (char[].class.equals(this.representationClass));
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public boolean isMimeTypeSerializedObject()
/*      */   {
/* 1203 */     return isMimeTypeEqual("application/x-java-serialized-object");
/*      */   }
/*      */   
/*      */   public final Class<?> getDefaultRepresentationClass() {
/* 1207 */     return ioInputStreamClass;
/*      */   }
/*      */   
/*      */   public final String getDefaultRepresentationClassAsString() {
/* 1211 */     return getDefaultRepresentationClass().getName();
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public boolean isRepresentationClassInputStream()
/*      */   {
/* 1220 */     return ioInputStreamClass.isAssignableFrom(this.representationClass);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public boolean isRepresentationClassReader()
/*      */   {
/* 1231 */     return Reader.class.isAssignableFrom(this.representationClass);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public boolean isRepresentationClassCharBuffer()
/*      */   {
/* 1242 */     return CharBuffer.class.isAssignableFrom(this.representationClass);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public boolean isRepresentationClassByteBuffer()
/*      */   {
/* 1253 */     return ByteBuffer.class.isAssignableFrom(this.representationClass);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public boolean isRepresentationClassSerializable()
/*      */   {
/* 1262 */     return Serializable.class.isAssignableFrom(this.representationClass);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public boolean isRepresentationClassRemote()
/*      */   {
/* 1271 */     return DataTransferer.isRemote(this.representationClass);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public boolean isFlavorSerializedObjectType()
/*      */   {
/* 1282 */     return (isRepresentationClassSerializable()) && (isMimeTypeEqual("application/x-java-serialized-object"));
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
/*      */   public boolean isFlavorRemoteObjectType()
/*      */   {
/* 1295 */     return (isRepresentationClassRemote()) && (isRepresentationClassSerializable()) && (isMimeTypeEqual("application/x-java-remote-object"));
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public boolean isFlavorJavaFileListType()
/*      */   {
/* 1307 */     if ((this.mimeType == null) || (this.representationClass == null)) {
/* 1308 */       return false;
/*      */     }
/* 1310 */     return (List.class.isAssignableFrom(this.representationClass)) && (this.mimeType.match(javaFileListFlavor.mimeType));
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
/*      */   public boolean isFlavorTextType()
/*      */   {
/* 1346 */     return (DataTransferer.isFlavorCharsetTextType(this)) || (DataTransferer.isFlavorNoncharsetTextType(this));
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   public synchronized void writeExternal(ObjectOutput paramObjectOutput)
/*      */     throws IOException
/*      */   {
/* 1354 */     if (this.mimeType != null) {
/* 1355 */       this.mimeType.setParameter("humanPresentableName", this.humanPresentableName);
/* 1356 */       paramObjectOutput.writeObject(this.mimeType);
/* 1357 */       this.mimeType.removeParameter("humanPresentableName");
/*      */     } else {
/* 1359 */       paramObjectOutput.writeObject(null);
/*      */     }
/*      */     
/* 1362 */     paramObjectOutput.writeObject(this.representationClass);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   public synchronized void readExternal(ObjectInput paramObjectInput)
/*      */     throws IOException, ClassNotFoundException
/*      */   {
/* 1370 */     String str = null;
/* 1371 */     this.mimeType = ((MimeType)paramObjectInput.readObject());
/*      */     
/* 1373 */     if (this.mimeType != null)
/*      */     {
/* 1375 */       this.humanPresentableName = this.mimeType.getParameter("humanPresentableName");
/* 1376 */       this.mimeType.removeParameter("humanPresentableName");
/* 1377 */       str = this.mimeType.getParameter("class");
/* 1378 */       if (str == null) {
/* 1379 */         throw new IOException("no class parameter specified in: " + this.mimeType);
/*      */       }
/*      */     }
/*      */     
/*      */     try
/*      */     {
/* 1385 */       this.representationClass = ((Class)paramObjectInput.readObject());
/*      */     } catch (OptionalDataException localOptionalDataException) {
/* 1387 */       if ((!localOptionalDataException.eof) || (localOptionalDataException.length != 0)) {
/* 1388 */         throw localOptionalDataException;
/*      */       }
/*      */       
/*      */ 
/* 1392 */       if (str != null)
/*      */       {
/* 1394 */         this.representationClass = tryToLoadClass(str, getClass().getClassLoader());
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public Object clone()
/*      */     throws CloneNotSupportedException
/*      */   {
/* 1405 */     Object localObject = super.clone();
/* 1406 */     if (this.mimeType != null) {
/* 1407 */       ((DataFlavor)localObject).mimeType = ((MimeType)this.mimeType.clone());
/*      */     }
/* 1409 */     return localObject;
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
/*      */   @Deprecated
/*      */   protected String normalizeMimeTypeParameter(String paramString1, String paramString2)
/*      */   {
/* 1428 */     return paramString2;
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
/*      */   @Deprecated
/*      */   protected String normalizeMimeType(String paramString)
/*      */   {
/* 1444 */     return paramString;
/*      */   }
/*      */ }


/* Location:              /Users/guanxiaoda/Documents/workspace/rt.jar!/java/awt/datatransfer/DataFlavor.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */