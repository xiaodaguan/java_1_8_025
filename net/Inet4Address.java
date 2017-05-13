/*     */ package java.net;
/*     */ 
/*     */ import java.io.ObjectStreamException;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class Inet4Address
/*     */   extends InetAddress
/*     */ {
/*     */   static final int INADDRSZ = 4;
/*     */   private static final long serialVersionUID = 3286316764910316507L;
/*     */   
/*     */   Inet4Address()
/*     */   {
/* 103 */     holder().hostName = null;
/* 104 */     holder().address = 0;
/* 105 */     holder().family = 1;
/*     */   }
/*     */   
/*     */   Inet4Address(String paramString, byte[] paramArrayOfByte) {
/* 109 */     holder().hostName = paramString;
/* 110 */     holder().family = 1;
/* 111 */     if ((paramArrayOfByte != null) && 
/* 112 */       (paramArrayOfByte.length == 4)) {
/* 113 */       int i = paramArrayOfByte[3] & 0xFF;
/* 114 */       i |= paramArrayOfByte[2] << 8 & 0xFF00;
/* 115 */       i |= paramArrayOfByte[1] << 16 & 0xFF0000;
/* 116 */       i |= paramArrayOfByte[0] << 24 & 0xFF000000;
/* 117 */       holder().address = i;
/*     */     }
/*     */   }
/*     */   
/*     */   Inet4Address(String paramString, int paramInt) {
/* 122 */     holder().hostName = paramString;
/* 123 */     holder().family = 1;
/* 124 */     holder().address = paramInt;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private Object writeReplace()
/*     */     throws ObjectStreamException
/*     */   {
/* 137 */     InetAddress localInetAddress = new InetAddress();
/* 138 */     localInetAddress.holder().hostName = holder().getHostName();
/* 139 */     localInetAddress.holder().address = holder().getAddress();
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 147 */     localInetAddress.holder().family = 2;
/*     */     
/* 149 */     return localInetAddress;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public boolean isMulticastAddress()
/*     */   {
/* 161 */     return (holder().getAddress() & 0xF0000000) == -536870912;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public boolean isAnyLocalAddress()
/*     */   {
/* 171 */     return holder().getAddress() == 0;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public boolean isLoopbackAddress()
/*     */   {
/* 183 */     byte[] arrayOfByte = getAddress();
/* 184 */     return arrayOfByte[0] == Byte.MAX_VALUE;
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
/*     */   public boolean isLinkLocalAddress()
/*     */   {
/* 199 */     int i = holder().getAddress();
/* 200 */     return ((i >>> 24 & 0xFF) == 169) && ((i >>> 16 & 0xFF) == 254);
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
/*     */   public boolean isSiteLocalAddress()
/*     */   {
/* 216 */     int i = holder().getAddress();
/* 217 */     return ((i >>> 24 & 0xFF) == 10) || (((i >>> 24 & 0xFF) == 172) && ((i >>> 16 & 0xF0) == 16)) || (((i >>> 24 & 0xFF) == 192) && ((i >>> 16 & 0xFF) == 168));
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
/*     */   public boolean isMCGlobal()
/*     */   {
/* 234 */     byte[] arrayOfByte = getAddress();
/* 235 */     return ((arrayOfByte[0] & 0xFF) >= 224) && ((arrayOfByte[0] & 0xFF) <= 238) && (((arrayOfByte[0] & 0xFF) != 224) || (arrayOfByte[1] != 0) || (arrayOfByte[2] != 0));
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
/*     */   public boolean isMCNodeLocal()
/*     */   {
/* 250 */     return false;
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
/*     */   public boolean isMCLinkLocal()
/*     */   {
/* 263 */     int i = holder().getAddress();
/* 264 */     return ((i >>> 24 & 0xFF) == 224) && ((i >>> 16 & 0xFF) == 0) && ((i >>> 8 & 0xFF) == 0);
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
/*     */   public boolean isMCSiteLocal()
/*     */   {
/* 279 */     int i = holder().getAddress();
/* 280 */     return ((i >>> 24 & 0xFF) == 239) && ((i >>> 16 & 0xFF) == 255);
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
/*     */   public boolean isMCOrgLocal()
/*     */   {
/* 295 */     int i = holder().getAddress();
/* 296 */     return ((i >>> 24 & 0xFF) == 239) && ((i >>> 16 & 0xFF) >= 192) && ((i >>> 16 & 0xFF) <= 195);
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
/*     */   public byte[] getAddress()
/*     */   {
/* 309 */     int i = holder().getAddress();
/* 310 */     byte[] arrayOfByte = new byte[4];
/*     */     
/* 312 */     arrayOfByte[0] = ((byte)(i >>> 24 & 0xFF));
/* 313 */     arrayOfByte[1] = ((byte)(i >>> 16 & 0xFF));
/* 314 */     arrayOfByte[2] = ((byte)(i >>> 8 & 0xFF));
/* 315 */     arrayOfByte[3] = ((byte)(i & 0xFF));
/* 316 */     return arrayOfByte;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public String getHostAddress()
/*     */   {
/* 326 */     return numericToTextFormat(getAddress());
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public int hashCode()
/*     */   {
/* 335 */     return holder().getAddress();
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
/*     */   public boolean equals(Object paramObject)
/*     */   {
/* 356 */     return (paramObject != null) && ((paramObject instanceof Inet4Address)) && (((InetAddress)paramObject).holder().getAddress() == holder().getAddress());
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
/*     */   static String numericToTextFormat(byte[] paramArrayOfByte)
/*     */   {
/* 371 */     return (paramArrayOfByte[0] & 0xFF) + "." + (paramArrayOfByte[1] & 0xFF) + "." + (paramArrayOfByte[2] & 0xFF) + "." + (paramArrayOfByte[3] & 0xFF);
/*     */   }
/*     */   
/*     */   private static native void init();
/*     */   
/*     */   static {}
/*     */ }


/* Location:              /Users/guanxiaoda/Documents/workspace/rt.jar!/java/net/Inet4Address.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */