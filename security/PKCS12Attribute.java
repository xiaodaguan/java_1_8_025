/*     */ package java.security;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.math.BigInteger;
/*     */ import java.util.Arrays;
/*     */ import java.util.Date;
/*     */ import java.util.regex.Matcher;
/*     */ import java.util.regex.Pattern;
/*     */ import sun.security.util.Debug;
/*     */ import sun.security.util.DerInputStream;
/*     */ import sun.security.util.DerOutputStream;
/*     */ import sun.security.util.DerValue;
/*     */ import sun.security.util.ObjectIdentifier;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class PKCS12Attribute
/*     */   implements KeyStore.Entry.Attribute
/*     */ {
/*  44 */   private static final Pattern COLON_SEPARATED_HEX_PAIRS = Pattern.compile("^[0-9a-fA-F]{2}(:[0-9a-fA-F]{2})+$");
/*     */   private String name;
/*     */   private String value;
/*     */   private byte[] encoded;
/*  48 */   private int hashValue = -1;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public PKCS12Attribute(String paramString1, String paramString2)
/*     */   {
/*  73 */     if ((paramString1 == null) || (paramString2 == null)) {
/*  74 */       throw new NullPointerException();
/*     */     }
/*     */     ObjectIdentifier localObjectIdentifier;
/*     */     try
/*     */     {
/*  79 */       localObjectIdentifier = new ObjectIdentifier(paramString1);
/*     */     } catch (IOException localIOException1) {
/*  81 */       throw new IllegalArgumentException("Incorrect format: name", localIOException1);
/*     */     }
/*  83 */     this.name = paramString1;
/*     */     
/*     */ 
/*  86 */     int i = paramString2.length();
/*     */     String[] arrayOfString;
/*  88 */     if ((paramString2.charAt(0) == '[') && (paramString2.charAt(i - 1) == ']')) {
/*  89 */       arrayOfString = paramString2.substring(1, i - 1).split(", ");
/*     */     } else {
/*  91 */       arrayOfString = new String[] { paramString2 };
/*     */     }
/*  93 */     this.value = paramString2;
/*     */     try
/*     */     {
/*  96 */       this.encoded = encode(localObjectIdentifier, arrayOfString);
/*     */     } catch (IOException localIOException2) {
/*  98 */       throw new IllegalArgumentException("Incorrect format: value", localIOException2);
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
/*     */   public PKCS12Attribute(byte[] paramArrayOfByte)
/*     */   {
/* 125 */     if (paramArrayOfByte == null) {
/* 126 */       throw new NullPointerException();
/*     */     }
/* 128 */     this.encoded = ((byte[])paramArrayOfByte.clone());
/*     */     try
/*     */     {
/* 131 */       parse(paramArrayOfByte);
/*     */     } catch (IOException localIOException) {
/* 133 */       throw new IllegalArgumentException("Incorrect format: encoded", localIOException);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public String getName()
/*     */   {
/* 145 */     return this.name;
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
/*     */   public String getValue()
/*     */   {
/* 172 */     return this.value;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public byte[] getEncoded()
/*     */   {
/* 181 */     return (byte[])this.encoded.clone();
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
/*     */   public boolean equals(Object paramObject)
/*     */   {
/* 195 */     if (this == paramObject) {
/* 196 */       return true;
/*     */     }
/* 198 */     if (!(paramObject instanceof PKCS12Attribute)) {
/* 199 */       return false;
/*     */     }
/* 201 */     return Arrays.equals(this.encoded, ((PKCS12Attribute)paramObject).getEncoded());
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public int hashCode()
/*     */   {
/* 212 */     if (this.hashValue == -1) {
/* 213 */       Arrays.hashCode(this.encoded);
/*     */     }
/* 215 */     return this.hashValue;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public String toString()
/*     */   {
/* 225 */     return this.name + "=" + this.value;
/*     */   }
/*     */   
/*     */   private byte[] encode(ObjectIdentifier paramObjectIdentifier, String[] paramArrayOfString) throws IOException
/*     */   {
/* 230 */     DerOutputStream localDerOutputStream1 = new DerOutputStream();
/* 231 */     localDerOutputStream1.putOID(paramObjectIdentifier);
/* 232 */     DerOutputStream localDerOutputStream2 = new DerOutputStream();
/* 233 */     for (CharSequence localCharSequence : paramArrayOfString) {
/* 234 */       if (COLON_SEPARATED_HEX_PAIRS.matcher(localCharSequence).matches())
/*     */       {
/* 236 */         byte[] arrayOfByte = new BigInteger(localCharSequence.replace(":", ""), 16).toByteArray();
/* 237 */         if (arrayOfByte[0] == 0) {
/* 238 */           arrayOfByte = Arrays.copyOfRange(arrayOfByte, 1, arrayOfByte.length);
/*     */         }
/* 240 */         localDerOutputStream2.putOctetString(arrayOfByte);
/*     */       } else {
/* 242 */         localDerOutputStream2.putUTF8String(localCharSequence);
/*     */       }
/*     */     }
/* 245 */     localDerOutputStream1.write((byte)49, localDerOutputStream2);
/* 246 */     ??? = new DerOutputStream();
/* 247 */     ((DerOutputStream)???).write((byte)48, localDerOutputStream1);
/*     */     
/* 249 */     return ((DerOutputStream)???).toByteArray();
/*     */   }
/*     */   
/*     */   private void parse(byte[] paramArrayOfByte) throws IOException {
/* 253 */     DerInputStream localDerInputStream1 = new DerInputStream(paramArrayOfByte);
/* 254 */     DerValue[] arrayOfDerValue1 = localDerInputStream1.getSequence(2);
/* 255 */     ObjectIdentifier localObjectIdentifier = arrayOfDerValue1[0].getOID();
/*     */     
/* 257 */     DerInputStream localDerInputStream2 = new DerInputStream(arrayOfDerValue1[1].toByteArray());
/* 258 */     DerValue[] arrayOfDerValue2 = localDerInputStream2.getSet(1);
/* 259 */     String[] arrayOfString = new String[arrayOfDerValue2.length];
/*     */     
/* 261 */     for (int i = 0; i < arrayOfDerValue2.length; i++) {
/* 262 */       if (arrayOfDerValue2[i].tag == 4) {
/* 263 */         arrayOfString[i] = Debug.toString(arrayOfDerValue2[i].getOctetString()); } else { String str;
/* 264 */         if ((str = arrayOfDerValue2[i].getAsString()) != null)
/*     */         {
/* 266 */           arrayOfString[i] = str;
/* 267 */         } else if (arrayOfDerValue2[i].tag == 6) {
/* 268 */           arrayOfString[i] = arrayOfDerValue2[i].getOID().toString();
/* 269 */         } else if (arrayOfDerValue2[i].tag == 24) {
/* 270 */           arrayOfString[i] = arrayOfDerValue2[i].getGeneralizedTime().toString();
/* 271 */         } else if (arrayOfDerValue2[i].tag == 23) {
/* 272 */           arrayOfString[i] = arrayOfDerValue2[i].getUTCTime().toString();
/* 273 */         } else if (arrayOfDerValue2[i].tag == 2) {
/* 274 */           arrayOfString[i] = arrayOfDerValue2[i].getBigInteger().toString();
/* 275 */         } else if (arrayOfDerValue2[i].tag == 1) {
/* 276 */           arrayOfString[i] = String.valueOf(arrayOfDerValue2[i].getBoolean());
/*     */         } else {
/* 278 */           arrayOfString[i] = Debug.toString(arrayOfDerValue2[i].getDataBytes());
/*     */         }
/*     */       }
/*     */     }
/* 282 */     this.name = localObjectIdentifier.toString();
/* 283 */     this.value = (arrayOfString.length == 1 ? arrayOfString[0] : Arrays.toString(arrayOfString));
/*     */   }
/*     */ }


/* Location:              /Users/guanxiaoda/Documents/workspace/rt.jar!/java/security/PKCS12Attribute.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */