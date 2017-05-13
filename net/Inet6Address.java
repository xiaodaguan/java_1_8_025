/*     */ package java.net;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.io.InvalidObjectException;
/*     */ import java.io.ObjectInputStream;
/*     */ import java.io.ObjectInputStream.GetField;
/*     */ import java.io.ObjectOutputStream;
/*     */ import java.io.ObjectOutputStream.PutField;
/*     */ import java.io.ObjectStreamField;
/*     */ import java.util.Arrays;
/*     */ import java.util.Enumeration;
/*     */ import sun.misc.Unsafe;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class Inet6Address
/*     */   extends InetAddress
/*     */ {
/*     */   static final int INADDRSZ = 16;
/*     */   private transient int cached_scope_id;
/*     */   private final transient Inet6AddressHolder holder6;
/*     */   private static final long serialVersionUID = 6880410070516793377L;
/*     */   private static final ObjectStreamField[] serialPersistentFields;
/*     */   private static final long FIELDS_OFFSET;
/*     */   private static final Unsafe UNSAFE;
/*     */   private static final int INT16SZ = 2;
/*     */   
/*     */   private class Inet6AddressHolder
/*     */   {
/*     */     byte[] ipaddress;
/*     */     int scope_id;
/*     */     boolean scope_id_set;
/*     */     NetworkInterface scope_ifname;
/*     */     boolean scope_ifname_set;
/*     */     
/*     */     private Inet6AddressHolder()
/*     */     {
/* 186 */       this.ipaddress = new byte[16];
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */     private Inet6AddressHolder(byte[] paramArrayOfByte, int paramInt, boolean paramBoolean1, NetworkInterface paramNetworkInterface, boolean paramBoolean2)
/*     */     {
/* 193 */       this.ipaddress = paramArrayOfByte;
/* 194 */       this.scope_id = paramInt;
/* 195 */       this.scope_id_set = paramBoolean1;
/* 196 */       this.scope_ifname_set = paramBoolean2;
/* 197 */       this.scope_ifname = paramNetworkInterface;
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     void setAddr(byte[] paramArrayOfByte)
/*     */     {
/* 231 */       if (paramArrayOfByte.length == 16) {
/* 232 */         System.arraycopy(paramArrayOfByte, 0, this.ipaddress, 0, 16);
/*     */       }
/*     */     }
/*     */     
/*     */     void init(byte[] paramArrayOfByte, int paramInt) {
/* 237 */       setAddr(paramArrayOfByte);
/*     */       
/* 239 */       if (paramInt >= 0) {
/* 240 */         this.scope_id = paramInt;
/* 241 */         this.scope_id_set = true;
/*     */       }
/*     */     }
/*     */     
/*     */     void init(byte[] paramArrayOfByte, NetworkInterface paramNetworkInterface)
/*     */       throws UnknownHostException
/*     */     {
/* 248 */       setAddr(paramArrayOfByte);
/*     */       
/* 250 */       if (paramNetworkInterface != null) {
/* 251 */         this.scope_id = Inet6Address.deriveNumericScope(this.ipaddress, paramNetworkInterface);
/* 252 */         this.scope_id_set = true;
/* 253 */         this.scope_ifname = paramNetworkInterface;
/* 254 */         this.scope_ifname_set = true;
/*     */       }
/*     */     }
/*     */     
/*     */     String getHostAddress() {
/* 259 */       String str = Inet6Address.numericToTextFormat(this.ipaddress);
/* 260 */       if (this.scope_ifname != null) {
/* 261 */         str = str + "%" + this.scope_ifname.getName();
/* 262 */       } else if (this.scope_id_set) {
/* 263 */         str = str + "%" + this.scope_id;
/*     */       }
/* 265 */       return str;
/*     */     }
/*     */     
/*     */     public boolean equals(Object paramObject) {
/* 269 */       if (!(paramObject instanceof Inet6AddressHolder)) {
/* 270 */         return false;
/*     */       }
/* 272 */       Inet6AddressHolder localInet6AddressHolder = (Inet6AddressHolder)paramObject;
/*     */       
/* 274 */       return Arrays.equals(this.ipaddress, localInet6AddressHolder.ipaddress);
/*     */     }
/*     */     
/*     */     public int hashCode() {
/* 278 */       if (this.ipaddress != null)
/*     */       {
/* 280 */         int i = 0;
/* 281 */         int j = 0;
/* 282 */         while (j < 16) {
/* 283 */           int k = 0;
/* 284 */           int m = 0;
/* 285 */           while ((k < 4) && (j < 16)) {
/* 286 */             m = (m << 8) + this.ipaddress[j];
/* 287 */             k++;
/* 288 */             j++;
/*     */           }
/* 290 */           i += m;
/*     */         }
/* 292 */         return i;
/*     */       }
/*     */       
/* 295 */       return 0;
/*     */     }
/*     */     
/*     */     boolean isIPv4CompatibleAddress()
/*     */     {
/* 300 */       if ((this.ipaddress[0] == 0) && (this.ipaddress[1] == 0) && (this.ipaddress[2] == 0) && (this.ipaddress[3] == 0) && (this.ipaddress[4] == 0) && (this.ipaddress[5] == 0) && (this.ipaddress[6] == 0) && (this.ipaddress[7] == 0) && (this.ipaddress[8] == 0) && (this.ipaddress[9] == 0) && (this.ipaddress[10] == 0) && (this.ipaddress[11] == 0))
/*     */       {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 306 */         return true;
/*     */       }
/* 308 */       return false;
/*     */     }
/*     */     
/*     */     boolean isMulticastAddress() {
/* 312 */       return (this.ipaddress[0] & 0xFF) == 255;
/*     */     }
/*     */     
/*     */     boolean isAnyLocalAddress() {
/* 316 */       int i = 0;
/* 317 */       for (int j = 0; j < 16; j++) {
/* 318 */         i = (byte)(i | this.ipaddress[j]);
/*     */       }
/* 320 */       return i == 0;
/*     */     }
/*     */     
/*     */     boolean isLoopbackAddress() {
/* 324 */       int i = 0;
/* 325 */       for (int j = 0; j < 15; j++) {
/* 326 */         i = (byte)(i | this.ipaddress[j]);
/*     */       }
/* 328 */       return (i == 0) && (this.ipaddress[15] == 1);
/*     */     }
/*     */     
/*     */     boolean isLinkLocalAddress() {
/* 332 */       return ((this.ipaddress[0] & 0xFF) == 254) && ((this.ipaddress[1] & 0xC0) == 128);
/*     */     }
/*     */     
/*     */ 
/*     */     boolean isSiteLocalAddress()
/*     */     {
/* 338 */       return ((this.ipaddress[0] & 0xFF) == 254) && ((this.ipaddress[1] & 0xC0) == 192);
/*     */     }
/*     */     
/*     */     boolean isMCGlobal()
/*     */     {
/* 343 */       return ((this.ipaddress[0] & 0xFF) == 255) && ((this.ipaddress[1] & 0xF) == 14);
/*     */     }
/*     */     
/*     */     boolean isMCNodeLocal()
/*     */     {
/* 348 */       return ((this.ipaddress[0] & 0xFF) == 255) && ((this.ipaddress[1] & 0xF) == 1);
/*     */     }
/*     */     
/*     */     boolean isMCLinkLocal()
/*     */     {
/* 353 */       return ((this.ipaddress[0] & 0xFF) == 255) && ((this.ipaddress[1] & 0xF) == 2);
/*     */     }
/*     */     
/*     */     boolean isMCSiteLocal()
/*     */     {
/* 358 */       return ((this.ipaddress[0] & 0xFF) == 255) && ((this.ipaddress[1] & 0xF) == 5);
/*     */     }
/*     */     
/*     */     boolean isMCOrgLocal()
/*     */     {
/* 363 */       return ((this.ipaddress[0] & 0xFF) == 255) && ((this.ipaddress[1] & 0xF) == 8);
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
/*     */   Inet6Address()
/*     */   {
/* 377 */     this.holder.init(null, 2);
/* 378 */     this.holder6 = new Inet6AddressHolder(null);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   Inet6Address(String paramString, byte[] paramArrayOfByte, int paramInt)
/*     */   {
/* 385 */     this.holder.init(paramString, 2);
/* 386 */     this.holder6 = new Inet6AddressHolder(null);
/* 387 */     this.holder6.init(paramArrayOfByte, paramInt);
/*     */   }
/*     */   
/*     */   Inet6Address(String paramString, byte[] paramArrayOfByte) {
/* 391 */     this.holder6 = new Inet6AddressHolder(null);
/*     */     try {
/* 393 */       initif(paramString, paramArrayOfByte, null);
/*     */     }
/*     */     catch (UnknownHostException localUnknownHostException) {}
/*     */   }
/*     */   
/*     */   Inet6Address(String paramString, byte[] paramArrayOfByte, NetworkInterface paramNetworkInterface) throws UnknownHostException
/*     */   {
/* 400 */     this.holder6 = new Inet6AddressHolder(null);
/* 401 */     initif(paramString, paramArrayOfByte, paramNetworkInterface);
/*     */   }
/*     */   
/*     */   Inet6Address(String paramString1, byte[] paramArrayOfByte, String paramString2)
/*     */     throws UnknownHostException
/*     */   {
/* 407 */     this.holder6 = new Inet6AddressHolder(null);
/* 408 */     initstr(paramString1, paramArrayOfByte, paramString2);
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
/*     */   public static Inet6Address getByAddress(String paramString, byte[] paramArrayOfByte, NetworkInterface paramNetworkInterface)
/*     */     throws UnknownHostException
/*     */   {
/* 435 */     if ((paramString != null) && (paramString.length() > 0) && (paramString.charAt(0) == '[') && 
/* 436 */       (paramString.charAt(paramString.length() - 1) == ']')) {
/* 437 */       paramString = paramString.substring(1, paramString.length() - 1);
/*     */     }
/*     */     
/* 440 */     if ((paramArrayOfByte != null) && 
/* 441 */       (paramArrayOfByte.length == 16)) {
/* 442 */       return new Inet6Address(paramString, paramArrayOfByte, paramNetworkInterface);
/*     */     }
/*     */     
/* 445 */     throw new UnknownHostException("addr is of illegal length");
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
/*     */   public static Inet6Address getByAddress(String paramString, byte[] paramArrayOfByte, int paramInt)
/*     */     throws UnknownHostException
/*     */   {
/* 468 */     if ((paramString != null) && (paramString.length() > 0) && (paramString.charAt(0) == '[') && 
/* 469 */       (paramString.charAt(paramString.length() - 1) == ']')) {
/* 470 */       paramString = paramString.substring(1, paramString.length() - 1);
/*     */     }
/*     */     
/* 473 */     if ((paramArrayOfByte != null) && 
/* 474 */       (paramArrayOfByte.length == 16)) {
/* 475 */       return new Inet6Address(paramString, paramArrayOfByte, paramInt);
/*     */     }
/*     */     
/* 478 */     throw new UnknownHostException("addr is of illegal length");
/*     */   }
/*     */   
/*     */   private void initstr(String paramString1, byte[] paramArrayOfByte, String paramString2) throws UnknownHostException
/*     */   {
/*     */     try
/*     */     {
/* 485 */       NetworkInterface localNetworkInterface = NetworkInterface.getByName(paramString2);
/* 486 */       if (localNetworkInterface == null) {
/* 487 */         throw new UnknownHostException("no such interface " + paramString2);
/*     */       }
/* 489 */       initif(paramString1, paramArrayOfByte, localNetworkInterface);
/*     */     } catch (SocketException localSocketException) {
/* 491 */       throw new UnknownHostException("SocketException thrown" + paramString2);
/*     */     }
/*     */   }
/*     */   
/*     */   private void initif(String paramString, byte[] paramArrayOfByte, NetworkInterface paramNetworkInterface)
/*     */     throws UnknownHostException
/*     */   {
/* 498 */     int i = -1;
/* 499 */     this.holder6.init(paramArrayOfByte, paramNetworkInterface);
/*     */     
/* 501 */     if (paramArrayOfByte.length == 16) {
/* 502 */       i = 2;
/*     */     }
/* 504 */     this.holder.init(paramString, i);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private static boolean isDifferentLocalAddressType(byte[] paramArrayOfByte1, byte[] paramArrayOfByte2)
/*     */   {
/* 516 */     if ((isLinkLocalAddress(paramArrayOfByte1)) && 
/* 517 */       (!isLinkLocalAddress(paramArrayOfByte2))) {
/* 518 */       return false;
/*     */     }
/* 520 */     if ((isSiteLocalAddress(paramArrayOfByte1)) && 
/* 521 */       (!isSiteLocalAddress(paramArrayOfByte2))) {
/* 522 */       return false;
/*     */     }
/* 524 */     return true;
/*     */   }
/*     */   
/*     */   private static int deriveNumericScope(byte[] paramArrayOfByte, NetworkInterface paramNetworkInterface) throws UnknownHostException {
/* 528 */     Enumeration localEnumeration = paramNetworkInterface.getInetAddresses();
/* 529 */     while (localEnumeration.hasMoreElements()) {
/* 530 */       InetAddress localInetAddress = (InetAddress)localEnumeration.nextElement();
/* 531 */       if ((localInetAddress instanceof Inet6Address))
/*     */       {
/*     */ 
/* 534 */         Inet6Address localInet6Address = (Inet6Address)localInetAddress;
/*     */         
/* 536 */         if (isDifferentLocalAddressType(paramArrayOfByte, localInet6Address.getAddress()))
/*     */         {
/*     */ 
/*     */ 
/*     */ 
/* 541 */           return localInet6Address.getScopeId(); }
/*     */       } }
/* 543 */     throw new UnknownHostException("no scope_id found");
/*     */   }
/*     */   
/*     */   private int deriveNumericScope(String paramString) throws UnknownHostException {
/*     */     Enumeration localEnumeration;
/*     */     try {
/* 549 */       localEnumeration = NetworkInterface.getNetworkInterfaces();
/*     */     } catch (SocketException localSocketException) {
/* 551 */       throw new UnknownHostException("could not enumerate local network interfaces");
/*     */     }
/* 553 */     while (localEnumeration.hasMoreElements()) {
/* 554 */       NetworkInterface localNetworkInterface = (NetworkInterface)localEnumeration.nextElement();
/* 555 */       if (localNetworkInterface.getName().equals(paramString)) {
/* 556 */         return deriveNumericScope(this.holder6.ipaddress, localNetworkInterface);
/*     */       }
/*     */     }
/* 559 */     throw new UnknownHostException("No matching address found for interface : " + paramString);
/*     */   }
/*     */   
/*     */   static
/*     */   {
/* 373 */     init();
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 570 */     serialPersistentFields = new ObjectStreamField[] { new ObjectStreamField("ipaddress", byte[].class), new ObjectStreamField("scope_id", Integer.TYPE), new ObjectStreamField("scope_id_set", Boolean.TYPE), new ObjectStreamField("scope_ifname_set", Boolean.TYPE), new ObjectStreamField("ifname", String.class) };
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     try
/*     */     {
/* 583 */       Unsafe localUnsafe = Unsafe.getUnsafe();
/* 584 */       FIELDS_OFFSET = localUnsafe.objectFieldOffset(Inet6Address.class
/* 585 */         .getDeclaredField("holder6"));
/* 586 */       UNSAFE = localUnsafe;
/*     */     } catch (ReflectiveOperationException localReflectiveOperationException) {
/* 588 */       throw new Error(localReflectiveOperationException);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private void readObject(ObjectInputStream paramObjectInputStream)
/*     */     throws IOException, ClassNotFoundException
/*     */   {
/* 599 */     NetworkInterface localNetworkInterface = null;
/*     */     
/* 601 */     if (getClass().getClassLoader() != null) {
/* 602 */       throw new SecurityException("invalid address type");
/*     */     }
/*     */     
/* 605 */     ObjectInputStream.GetField localGetField = paramObjectInputStream.readFields();
/* 606 */     byte[] arrayOfByte = (byte[])localGetField.get("ipaddress", null);
/* 607 */     int i = localGetField.get("scope_id", -1);
/* 608 */     boolean bool1 = localGetField.get("scope_id_set", false);
/* 609 */     boolean bool2 = localGetField.get("scope_ifname_set", false);
/* 610 */     String str = (String)localGetField.get("ifname", null);
/*     */     
/* 612 */     if ((str != null) && (!"".equals(str))) {
/*     */       try {
/* 614 */         localNetworkInterface = NetworkInterface.getByName(str);
/* 615 */         if (localNetworkInterface == null)
/*     */         {
/*     */ 
/* 618 */           bool1 = false;
/* 619 */           bool2 = false;
/* 620 */           i = 0;
/*     */         } else {
/* 622 */           bool2 = true;
/*     */           try {
/* 624 */             i = deriveNumericScope(arrayOfByte, localNetworkInterface);
/*     */           }
/*     */           catch (UnknownHostException localUnknownHostException) {}
/*     */         }
/*     */       }
/*     */       catch (SocketException localSocketException) {}
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 636 */     arrayOfByte = (byte[])arrayOfByte.clone();
/*     */     
/*     */ 
/* 639 */     if (arrayOfByte.length != 16) {
/* 640 */       throw new InvalidObjectException("invalid address length: " + arrayOfByte.length);
/*     */     }
/*     */     
/*     */ 
/* 644 */     if (this.holder.getFamily() != 2) {
/* 645 */       throw new InvalidObjectException("invalid address family type");
/*     */     }
/*     */     
/* 648 */     Inet6AddressHolder localInet6AddressHolder = new Inet6AddressHolder(arrayOfByte, i, bool1, localNetworkInterface, bool2, null);
/*     */     
/*     */ 
/*     */ 
/* 652 */     UNSAFE.putObject(this, FIELDS_OFFSET, localInet6AddressHolder);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private synchronized void writeObject(ObjectOutputStream paramObjectOutputStream)
/*     */     throws IOException
/*     */   {
/* 663 */     String str = null;
/*     */     
/* 665 */     if (this.holder6.scope_ifname != null) {
/* 666 */       str = this.holder6.scope_ifname.getName();
/* 667 */       this.holder6.scope_ifname_set = true;
/*     */     }
/* 669 */     ObjectOutputStream.PutField localPutField = paramObjectOutputStream.putFields();
/* 670 */     localPutField.put("ipaddress", this.holder6.ipaddress);
/* 671 */     localPutField.put("scope_id", this.holder6.scope_id);
/* 672 */     localPutField.put("scope_id_set", this.holder6.scope_id_set);
/* 673 */     localPutField.put("scope_ifname_set", this.holder6.scope_ifname_set);
/* 674 */     localPutField.put("ifname", str);
/* 675 */     paramObjectOutputStream.writeFields();
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
/*     */   public boolean isMulticastAddress()
/*     */   {
/* 690 */     return this.holder6.isMulticastAddress();
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
/*     */   public boolean isAnyLocalAddress()
/*     */   {
/* 703 */     return this.holder6.isAnyLocalAddress();
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
/*     */   public boolean isLoopbackAddress()
/*     */   {
/* 716 */     return this.holder6.isLoopbackAddress();
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
/*     */   public boolean isLinkLocalAddress()
/*     */   {
/* 729 */     return this.holder6.isLinkLocalAddress();
/*     */   }
/*     */   
/*     */   static boolean isLinkLocalAddress(byte[] paramArrayOfByte)
/*     */   {
/* 734 */     return ((paramArrayOfByte[0] & 0xFF) == 254) && ((paramArrayOfByte[1] & 0xC0) == 128);
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
/*     */   public boolean isSiteLocalAddress()
/*     */   {
/* 748 */     return this.holder6.isSiteLocalAddress();
/*     */   }
/*     */   
/*     */   static boolean isSiteLocalAddress(byte[] paramArrayOfByte)
/*     */   {
/* 753 */     return ((paramArrayOfByte[0] & 0xFF) == 254) && ((paramArrayOfByte[1] & 0xC0) == 192);
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
/*     */   public boolean isMCGlobal()
/*     */   {
/* 768 */     return this.holder6.isMCGlobal();
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
/*     */   public boolean isMCNodeLocal()
/*     */   {
/* 782 */     return this.holder6.isMCNodeLocal();
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
/*     */   public boolean isMCLinkLocal()
/*     */   {
/* 796 */     return this.holder6.isMCLinkLocal();
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
/*     */   public boolean isMCSiteLocal()
/*     */   {
/* 810 */     return this.holder6.isMCSiteLocal();
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
/*     */   public boolean isMCOrgLocal()
/*     */   {
/* 824 */     return this.holder6.isMCOrgLocal();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public byte[] getAddress()
/*     */   {
/* 835 */     return (byte[])this.holder6.ipaddress.clone();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public int getScopeId()
/*     */   {
/* 847 */     return this.holder6.scope_id;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public NetworkInterface getScopedInterface()
/*     */   {
/* 858 */     return this.holder6.scope_ifname;
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
/*     */   public String getHostAddress()
/*     */   {
/* 872 */     return this.holder6.getHostAddress();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public int hashCode()
/*     */   {
/* 882 */     return this.holder6.hashCode();
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
/* 903 */     if ((paramObject == null) || (!(paramObject instanceof Inet6Address))) {
/* 904 */       return false;
/*     */     }
/* 906 */     Inet6Address localInet6Address = (Inet6Address)paramObject;
/*     */     
/* 908 */     return this.holder6.equals(localInet6Address.holder6);
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
/*     */   public boolean isIPv4CompatibleAddress()
/*     */   {
/* 921 */     return this.holder6.isIPv4CompatibleAddress();
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
/* 936 */     StringBuilder localStringBuilder = new StringBuilder(39);
/* 937 */     for (int i = 0; i < 8; i++) {
/* 938 */       localStringBuilder.append(Integer.toHexString(paramArrayOfByte[(i << 1)] << 8 & 0xFF00 | paramArrayOfByte[((i << 1) + 1)] & 0xFF));
/*     */       
/* 940 */       if (i < 7) {
/* 941 */         localStringBuilder.append(":");
/*     */       }
/*     */     }
/* 944 */     return localStringBuilder.toString();
/*     */   }
/*     */   
/*     */   private static native void init();
/*     */ }


/* Location:              /Users/guanxiaoda/Documents/workspace/rt.jar!/java/net/Inet6Address.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */