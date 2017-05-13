/*     */ package java.awt.datatransfer;
/*     */ 
/*     */ import java.io.Externalizable;
/*     */ import java.io.IOException;
/*     */ import java.io.ObjectInput;
/*     */ import java.io.ObjectOutput;
/*     */ import java.util.Locale;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ class MimeType
/*     */   implements Externalizable, Cloneable
/*     */ {
/*     */   static final long serialVersionUID = -6568722458793895906L;
/*     */   private String primaryType;
/*     */   private String subType;
/*     */   private MimeTypeParameterList parameters;
/*     */   private static final String TSPECIALS = "()<>@,;:\\\"/[]?=";
/*     */   
/*     */   public MimeType() {}
/*     */   
/*     */   public MimeType(String paramString)
/*     */     throws MimeTypeParseException
/*     */   {
/*  67 */     parse(paramString);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public MimeType(String paramString1, String paramString2)
/*     */     throws MimeTypeParseException
/*     */   {
/*  80 */     this(paramString1, paramString2, new MimeTypeParameterList());
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
/*     */   public MimeType(String paramString1, String paramString2, MimeTypeParameterList paramMimeTypeParameterList)
/*     */     throws MimeTypeParseException
/*     */   {
/*  96 */     if (isValidToken(paramString1)) {
/*  97 */       this.primaryType = paramString1.toLowerCase(Locale.ENGLISH);
/*     */     } else {
/*  99 */       throw new MimeTypeParseException("Primary type is invalid.");
/*     */     }
/*     */     
/*     */ 
/* 103 */     if (isValidToken(paramString2)) {
/* 104 */       this.subType = paramString2.toLowerCase(Locale.ENGLISH);
/*     */     } else {
/* 106 */       throw new MimeTypeParseException("Sub type is invalid.");
/*     */     }
/*     */     
/* 109 */     this.parameters = ((MimeTypeParameterList)paramMimeTypeParameterList.clone());
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public int hashCode()
/*     */   {
/* 116 */     int i = 0;
/* 117 */     i += this.primaryType.hashCode();
/* 118 */     i += this.subType.hashCode();
/* 119 */     i += this.parameters.hashCode();
/* 120 */     return i;
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
/*     */   public boolean equals(Object paramObject)
/*     */   {
/* 133 */     if (!(paramObject instanceof MimeType)) {
/* 134 */       return false;
/*     */     }
/* 136 */     MimeType localMimeType = (MimeType)paramObject;
/*     */     
/*     */ 
/*     */ 
/* 140 */     boolean bool = (this.primaryType.equals(localMimeType.primaryType)) && (this.subType.equals(localMimeType.subType)) && (this.parameters.equals(localMimeType.parameters));
/* 141 */     return bool;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   private void parse(String paramString)
/*     */     throws MimeTypeParseException
/*     */   {
/* 150 */     int i = paramString.indexOf('/');
/* 151 */     int j = paramString.indexOf(';');
/* 152 */     if ((i < 0) && (j < 0))
/*     */     {
/*     */ 
/* 155 */       throw new MimeTypeParseException("Unable to find a sub type."); }
/* 156 */     if ((i < 0) && (j >= 0))
/*     */     {
/*     */ 
/* 159 */       throw new MimeTypeParseException("Unable to find a sub type."); }
/* 160 */     if ((i >= 0) && (j < 0))
/*     */     {
/*     */ 
/* 163 */       this.primaryType = paramString.substring(0, i).trim().toLowerCase(Locale.ENGLISH);
/*     */       
/* 165 */       this.subType = paramString.substring(i + 1).trim().toLowerCase(Locale.ENGLISH);
/* 166 */       this.parameters = new MimeTypeParameterList();
/* 167 */     } else if (i < j)
/*     */     {
/*     */ 
/* 170 */       this.primaryType = paramString.substring(0, i).trim().toLowerCase(Locale.ENGLISH);
/*     */       
/* 172 */       this.subType = paramString.substring(i + 1, j).trim().toLowerCase(Locale.ENGLISH);
/*     */       
/* 174 */       this.parameters = new MimeTypeParameterList(paramString.substring(j));
/*     */     }
/*     */     else
/*     */     {
/* 178 */       throw new MimeTypeParseException("Unable to find a sub type.");
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */ 
/* 184 */     if (!isValidToken(this.primaryType)) {
/* 185 */       throw new MimeTypeParseException("Primary type is invalid.");
/*     */     }
/*     */     
/*     */ 
/* 189 */     if (!isValidToken(this.subType)) {
/* 190 */       throw new MimeTypeParseException("Sub type is invalid.");
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public String getPrimaryType()
/*     */   {
/* 198 */     return this.primaryType;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public String getSubType()
/*     */   {
/* 205 */     return this.subType;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public MimeTypeParameterList getParameters()
/*     */   {
/* 212 */     return (MimeTypeParameterList)this.parameters.clone();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public String getParameter(String paramString)
/*     */   {
/* 220 */     return this.parameters.get(paramString);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setParameter(String paramString1, String paramString2)
/*     */   {
/* 230 */     this.parameters.set(paramString1, paramString2);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void removeParameter(String paramString)
/*     */   {
/* 239 */     this.parameters.remove(paramString);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public String toString()
/*     */   {
/* 246 */     return getBaseType() + this.parameters.toString();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public String getBaseType()
/*     */   {
/* 254 */     return this.primaryType + "/" + this.subType;
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
/*     */   public boolean match(MimeType paramMimeType)
/*     */   {
/* 269 */     if (paramMimeType == null) {
/* 270 */       return false;
/*     */     }
/*     */     
/*     */ 
/* 274 */     return (this.primaryType.equals(paramMimeType.getPrimaryType())) && ((this.subType.equals("*")) || (paramMimeType.getSubType().equals("*")) || (this.subType.equals(paramMimeType.getSubType())));
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
/*     */   public boolean match(String paramString)
/*     */     throws MimeTypeParseException
/*     */   {
/* 291 */     if (paramString == null)
/* 292 */       return false;
/* 293 */     return match(new MimeType(paramString));
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void writeExternal(ObjectOutput paramObjectOutput)
/*     */     throws IOException
/*     */   {
/* 304 */     String str = toString();
/*     */     
/* 306 */     if (str.length() <= 65535) {
/* 307 */       paramObjectOutput.writeUTF(str);
/*     */     } else {
/* 309 */       paramObjectOutput.writeByte(0);
/* 310 */       paramObjectOutput.writeByte(0);
/* 311 */       paramObjectOutput.writeInt(str.length());
/* 312 */       paramObjectOutput.write(str.getBytes());
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
/*     */   public void readExternal(ObjectInput paramObjectInput)
/*     */     throws IOException, ClassNotFoundException
/*     */   {
/* 327 */     String str = paramObjectInput.readUTF();
/* 328 */     if ((str == null) || (str.length() == 0)) {
/* 329 */       byte[] arrayOfByte = new byte[paramObjectInput.readInt()];
/* 330 */       paramObjectInput.readFully(arrayOfByte);
/* 331 */       str = new String(arrayOfByte);
/*     */     }
/*     */     try {
/* 334 */       parse(str);
/*     */     } catch (MimeTypeParseException localMimeTypeParseException) {
/* 336 */       throw new IOException(localMimeTypeParseException.toString());
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public Object clone()
/*     */   {
/* 346 */     MimeType localMimeType = null;
/*     */     try {
/* 348 */       localMimeType = (MimeType)super.clone();
/*     */     }
/*     */     catch (CloneNotSupportedException localCloneNotSupportedException) {}
/* 351 */     localMimeType.parameters = ((MimeTypeParameterList)this.parameters.clone());
/* 352 */     return localMimeType;
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
/*     */   private static boolean isTokenChar(char paramChar)
/*     */   {
/* 365 */     return (paramChar > ' ') && (paramChar < '') && ("()<>@,;:\\\"/[]?=".indexOf(paramChar) < 0);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private boolean isValidToken(String paramString)
/*     */   {
/* 374 */     int i = paramString.length();
/* 375 */     if (i > 0) {
/* 376 */       for (int j = 0; j < i; j++) {
/* 377 */         char c = paramString.charAt(j);
/* 378 */         if (!isTokenChar(c)) {
/* 379 */           return false;
/*     */         }
/*     */       }
/* 382 */       return true;
/*     */     }
/* 384 */     return false;
/*     */   }
/*     */ }


/* Location:              /Users/guanxiaoda/Documents/workspace/rt.jar!/java/awt/datatransfer/MimeType.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */