/*      */ package java.net;
/*      */ 
/*      */ import java.io.IOException;
/*      */ import java.io.ObjectInputStream;
/*      */ import java.io.ObjectInputStream.GetField;
/*      */ import java.io.ObjectOutputStream;
/*      */ import java.io.ObjectOutputStream.PutField;
/*      */ import java.io.ObjectStreamException;
/*      */ import java.io.ObjectStreamField;
/*      */ import java.io.PrintStream;
/*      */ import java.io.Serializable;
/*      */ import java.security.AccessController;
/*      */ import java.security.PrivilegedAction;
/*      */ import java.security.PrivilegedActionException;
/*      */ import java.security.PrivilegedExceptionAction;
/*      */ import java.util.ArrayList;
/*      */ import java.util.HashMap;
/*      */ import java.util.Iterator;
/*      */ import java.util.LinkedHashMap;
/*      */ import java.util.LinkedList;
/*      */ import java.util.List;
/*      */ import java.util.ServiceLoader;
/*      */ import java.util.Set;
/*      */ import sun.misc.Unsafe;
/*      */ import sun.net.InetAddressCachePolicy;
/*      */ import sun.net.spi.nameservice.NameService;
/*      */ import sun.net.spi.nameservice.NameServiceDescriptor;
/*      */ import sun.net.util.IPAddressUtil;
/*      */ import sun.security.action.GetBooleanAction;
/*      */ import sun.security.action.GetPropertyAction;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class InetAddress
/*      */   implements Serializable
/*      */ {
/*      */   static final int IPv4 = 1;
/*      */   static final int IPv6 = 2;
/*      */   static transient boolean preferIPv6Address;
/*      */   final transient InetAddressHolder holder;
/*      */   private static List<NameService> nameServices;
/*      */   
/*      */   static class InetAddressHolder
/*      */   {
/*      */     String hostName;
/*      */     int address;
/*      */     int family;
/*      */     
/*      */     InetAddressHolder() {}
/*      */     
/*      */     InetAddressHolder(String paramString, int paramInt1, int paramInt2)
/*      */     {
/*  210 */       this.hostName = paramString;
/*  211 */       this.address = paramInt1;
/*  212 */       this.family = paramInt2;
/*      */     }
/*      */     
/*      */     void init(String paramString, int paramInt) {
/*  216 */       this.hostName = paramString;
/*  217 */       if (paramInt != -1) {
/*  218 */         this.family = paramInt;
/*      */       }
/*      */     }
/*      */     
/*      */ 
/*      */     String getHostName()
/*      */     {
/*  225 */       return this.hostName;
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     int getAddress()
/*      */     {
/*  234 */       return this.address;
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     int getFamily()
/*      */     {
/*  244 */       return this.family;
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   InetAddressHolder holder()
/*      */   {
/*  252 */     return this.holder;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  259 */   private transient String canonicalHostName = null;
/*      */   
/*      */   private static final long serialVersionUID = 3286316764910316507L;
/*      */   
/*      */   private static Cache addressCache;
/*      */   
/*      */   private static Cache negativeCache;
/*      */   
/*      */   private static boolean addressCacheInit;
/*      */   
/*      */   static InetAddress[] unknown_array;
/*      */   
/*      */   static InetAddressImpl impl;
/*      */   
/*      */   private static final HashMap<String, Void> lookupTable;
/*      */   
/*      */   private static InetAddress cachedLocalHost;
/*      */   
/*      */   private static long cacheTime;
/*      */   
/*      */   private static final long maxCacheTime = 5000L;
/*      */   
/*      */   private static final Object cacheLock;
/*      */   private static final long FIELDS_OFFSET;
/*      */   private static final Unsafe UNSAFE;
/*      */   
/*      */   InetAddress()
/*      */   {
/*  287 */     this.holder = new InetAddressHolder();
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private Object readResolve()
/*      */     throws ObjectStreamException
/*      */   {
/*  300 */     return new Inet4Address(holder().getHostName(), holder().getAddress());
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public boolean isMulticastAddress()
/*      */   {
/*  311 */     return false;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public boolean isAnyLocalAddress()
/*      */   {
/*  321 */     return false;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public boolean isLoopbackAddress()
/*      */   {
/*  332 */     return false;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public boolean isLinkLocalAddress()
/*      */   {
/*  343 */     return false;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public boolean isSiteLocalAddress()
/*      */   {
/*  354 */     return false;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public boolean isMCGlobal()
/*      */   {
/*  366 */     return false;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public boolean isMCNodeLocal()
/*      */   {
/*  378 */     return false;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public boolean isMCLinkLocal()
/*      */   {
/*  390 */     return false;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public boolean isMCSiteLocal()
/*      */   {
/*  402 */     return false;
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
/*      */   public boolean isMCOrgLocal()
/*      */   {
/*  415 */     return false;
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
/*      */   public boolean isReachable(int paramInt)
/*      */     throws IOException
/*      */   {
/*  440 */     return isReachable(null, 0, paramInt);
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
/*      */   public boolean isReachable(NetworkInterface paramNetworkInterface, int paramInt1, int paramInt2)
/*      */     throws IOException
/*      */   {
/*  476 */     if (paramInt1 < 0)
/*  477 */       throw new IllegalArgumentException("ttl can't be negative");
/*  478 */     if (paramInt2 < 0) {
/*  479 */       throw new IllegalArgumentException("timeout can't be negative");
/*      */     }
/*  481 */     return impl.isReachable(this, paramInt2, paramNetworkInterface, paramInt1);
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
/*      */   public String getHostName()
/*      */   {
/*  510 */     return getHostName(true);
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
/*      */   String getHostName(boolean paramBoolean)
/*      */   {
/*  537 */     if (holder().getHostName() == null) {
/*  538 */       holder().hostName = getHostFromNameService(this, paramBoolean);
/*      */     }
/*  540 */     return holder().getHostName();
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
/*      */   public String getCanonicalHostName()
/*      */   {
/*  565 */     if (this.canonicalHostName == null)
/*      */     {
/*  567 */       this.canonicalHostName = getHostFromNameService(this, true);
/*      */     }
/*  569 */     return this.canonicalHostName;
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
/*      */   private static String getHostFromNameService(InetAddress paramInetAddress, boolean paramBoolean)
/*      */   {
/*  592 */     String str = null;
/*  593 */     for (NameService localNameService : nameServices) {
/*      */       try
/*      */       {
/*  596 */         str = localNameService.getHostByAddr(paramInetAddress.getAddress());
/*      */         
/*      */ 
/*      */ 
/*      */ 
/*  601 */         if (paramBoolean) {
/*  602 */           localObject = System.getSecurityManager();
/*  603 */           if (localObject != null) {
/*  604 */             ((SecurityManager)localObject).checkConnect(str, -1);
/*      */           }
/*      */         }
/*      */         
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  613 */         Object localObject = getAllByName0(str, paramBoolean);
/*  614 */         boolean bool = false;
/*      */         
/*  616 */         if (localObject != null) {
/*  617 */           for (int i = 0; (!bool) && (i < localObject.length); i++) {
/*  618 */             bool = paramInetAddress.equals(localObject[i]);
/*      */           }
/*      */         }
/*      */         
/*      */ 
/*  623 */         if (!bool) {
/*  624 */           return paramInetAddress.getHostAddress();
/*      */         }
/*      */         
/*      */ 
/*      */       }
/*      */       catch (SecurityException localSecurityException)
/*      */       {
/*  631 */         str = paramInetAddress.getHostAddress();
/*      */       }
/*      */       catch (UnknownHostException localUnknownHostException) {
/*  634 */         str = paramInetAddress.getHostAddress();
/*      */       }
/*      */     }
/*      */     
/*      */ 
/*  639 */     return str;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public byte[] getAddress()
/*      */   {
/*  650 */     return null;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public String getHostAddress()
/*      */   {
/*  660 */     return null;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public int hashCode()
/*      */   {
/*  669 */     return -1;
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
/*      */   public boolean equals(Object paramObject)
/*      */   {
/*  689 */     return false;
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
/*      */   public String toString()
/*      */   {
/*  703 */     String str = holder().getHostName();
/*      */     
/*  705 */     return (str != null ? str : "") + "/" + getHostAddress();
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   static final class CacheEntry
/*      */   {
/*      */     InetAddress[] addresses;
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     long expiration;
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     CacheEntry(InetAddress[] paramArrayOfInetAddress, long paramLong)
/*      */     {
/*  729 */       this.addresses = paramArrayOfInetAddress;
/*  730 */       this.expiration = paramLong;
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   static final class Cache
/*      */   {
/*      */     private LinkedHashMap<String, InetAddress.CacheEntry> cache;
/*      */     
/*      */     private Type type;
/*      */     
/*      */ 
/*      */     static enum Type
/*      */     {
/*  745 */       Positive,  Negative;
/*      */       
/*      */       private Type() {}
/*      */     }
/*      */     
/*      */     public Cache(Type paramType) {
/*  751 */       this.type = paramType;
/*  752 */       this.cache = new LinkedHashMap();
/*      */     }
/*      */     
/*      */     private int getPolicy() {
/*  756 */       if (this.type == Type.Positive) {
/*  757 */         return InetAddressCachePolicy.get();
/*      */       }
/*  759 */       return InetAddressCachePolicy.getNegative();
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     public Cache put(String paramString, InetAddress[] paramArrayOfInetAddress)
/*      */     {
/*  769 */       int i = getPolicy();
/*  770 */       if (i == 0) {
/*  771 */         return this;
/*      */       }
/*      */       
/*      */       Iterator localIterator;
/*      */       String str;
/*  776 */       if (i != -1)
/*      */       {
/*      */ 
/*      */ 
/*  780 */         LinkedList localLinkedList = new LinkedList();
/*  781 */         long l2 = System.currentTimeMillis();
/*  782 */         for (localIterator = this.cache.keySet().iterator(); localIterator.hasNext();) { str = (String)localIterator.next();
/*  783 */           InetAddress.CacheEntry localCacheEntry2 = (InetAddress.CacheEntry)this.cache.get(str);
/*      */           
/*  785 */           if ((localCacheEntry2.expiration < 0L) || (localCacheEntry2.expiration >= l2)) break;
/*  786 */           localLinkedList.add(str);
/*      */         }
/*      */         
/*      */ 
/*      */ 
/*      */ 
/*  792 */         for (localIterator = localLinkedList.iterator(); localIterator.hasNext();) { str = (String)localIterator.next();
/*  793 */           this.cache.remove(str);
/*      */         }
/*      */       }
/*      */       
/*      */ 
/*      */ 
/*      */       long l1;
/*      */       
/*      */ 
/*  802 */       if (i == -1) {
/*  803 */         l1 = -1L;
/*      */       } else {
/*  805 */         l1 = System.currentTimeMillis() + i * 1000;
/*      */       }
/*  807 */       InetAddress.CacheEntry localCacheEntry1 = new InetAddress.CacheEntry(paramArrayOfInetAddress, l1);
/*  808 */       this.cache.put(paramString, localCacheEntry1);
/*  809 */       return this;
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */     public InetAddress.CacheEntry get(String paramString)
/*      */     {
/*  817 */       int i = getPolicy();
/*  818 */       if (i == 0) {
/*  819 */         return null;
/*      */       }
/*  821 */       InetAddress.CacheEntry localCacheEntry = (InetAddress.CacheEntry)this.cache.get(paramString);
/*      */       
/*      */ 
/*  824 */       if ((localCacheEntry != null) && (i != -1) && 
/*  825 */         (localCacheEntry.expiration >= 0L) && 
/*  826 */         (localCacheEntry.expiration < System.currentTimeMillis())) {
/*  827 */         this.cache.remove(paramString);
/*  828 */         localCacheEntry = null;
/*      */       }
/*      */       
/*      */ 
/*  832 */       return localCacheEntry;
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   private static void cacheInitIfNeeded()
/*      */   {
/*  841 */     assert (Thread.holdsLock(addressCache));
/*  842 */     if (addressCacheInit) {
/*  843 */       return;
/*      */     }
/*  845 */     unknown_array = new InetAddress[1];
/*  846 */     unknown_array[0] = impl.anyLocalAddress();
/*      */     
/*  848 */     addressCache.put(impl.anyLocalAddress().getHostName(), unknown_array);
/*      */     
/*      */ 
/*  851 */     addressCacheInit = true;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private static void cacheAddresses(String paramString, InetAddress[] paramArrayOfInetAddress, boolean paramBoolean)
/*      */   {
/*  860 */     paramString = paramString.toLowerCase();
/*  861 */     synchronized (addressCache) {
/*  862 */       cacheInitIfNeeded();
/*  863 */       if (paramBoolean) {
/*  864 */         addressCache.put(paramString, paramArrayOfInetAddress);
/*      */       } else {
/*  866 */         negativeCache.put(paramString, paramArrayOfInetAddress);
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   private static InetAddress[] getCachedAddresses(String paramString)
/*      */   {
/*  876 */     paramString = paramString.toLowerCase();
/*      */     
/*      */ 
/*      */ 
/*  880 */     synchronized (addressCache) {
/*  881 */       cacheInitIfNeeded();
/*      */       
/*  883 */       CacheEntry localCacheEntry = addressCache.get(paramString);
/*  884 */       if (localCacheEntry == null) {
/*  885 */         localCacheEntry = negativeCache.get(paramString);
/*      */       }
/*      */       
/*  888 */       if (localCacheEntry != null) {
/*  889 */         return localCacheEntry.addresses;
/*      */       }
/*      */     }
/*      */     
/*      */ 
/*  894 */     return null;
/*      */   }
/*      */   
/*      */   private static NameService createNSProvider(String paramString) {
/*  898 */     if (paramString == null) {
/*  899 */       return null;
/*      */     }
/*  901 */     Object localObject = null;
/*  902 */     if (paramString.equals("default"))
/*      */     {
/*  904 */       localObject = new NameService()
/*      */       {
/*      */         public InetAddress[] lookupAllHostAddr(String paramAnonymousString) throws UnknownHostException {
/*  907 */           return InetAddress.impl.lookupAllHostAddr(paramAnonymousString);
/*      */         }
/*      */         
/*      */         public String getHostByAddr(byte[] paramAnonymousArrayOfByte) throws UnknownHostException {
/*  911 */           return InetAddress.impl.getHostByAddr(paramAnonymousArrayOfByte);
/*      */         }
/*      */       };
/*      */     } else {
/*  915 */       String str = paramString;
/*      */       try {
/*  917 */         localObject = (NameService)AccessController.doPrivileged(new PrivilegedExceptionAction()
/*      */         {
/*      */ 
/*      */           public NameService run()
/*      */           {
/*  922 */             Iterator localIterator = ServiceLoader.load(NameServiceDescriptor.class).iterator();
/*  923 */             while (localIterator.hasNext()) {
/*  924 */               NameServiceDescriptor localNameServiceDescriptor = (NameServiceDescriptor)localIterator.next();
/*      */               
/*  926 */               if (this.val$providerName.equalsIgnoreCase(localNameServiceDescriptor.getType() + "," + localNameServiceDescriptor
/*  927 */                 .getProviderName())) {
/*      */                 try {
/*  929 */                   return localNameServiceDescriptor.createNameService();
/*      */                 } catch (Exception localException) {
/*  931 */                   localException.printStackTrace();
/*  932 */                   System.err.println("Cannot create name service:" + this.val$providerName + ": " + localException);
/*      */                 }
/*      */               }
/*      */             }
/*      */             
/*      */ 
/*      */ 
/*  939 */             return null;
/*      */           }
/*      */         });
/*      */       }
/*      */       catch (PrivilegedActionException localPrivilegedActionException) {}
/*      */     }
/*      */     
/*      */ 
/*  947 */     return (NameService)localObject;
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
/*      */   public static InetAddress getByAddress(String paramString, byte[] paramArrayOfByte)
/*      */     throws UnknownHostException
/*      */   {
/* 1003 */     if ((paramString != null) && (paramString.length() > 0) && (paramString.charAt(0) == '[') && 
/* 1004 */       (paramString.charAt(paramString.length() - 1) == ']')) {
/* 1005 */       paramString = paramString.substring(1, paramString.length() - 1);
/*      */     }
/*      */     
/* 1008 */     if (paramArrayOfByte != null) {
/* 1009 */       if (paramArrayOfByte.length == 4)
/* 1010 */         return new Inet4Address(paramString, paramArrayOfByte);
/* 1011 */       if (paramArrayOfByte.length == 16)
/*      */       {
/* 1013 */         byte[] arrayOfByte = IPAddressUtil.convertFromIPv4MappedAddress(paramArrayOfByte);
/* 1014 */         if (arrayOfByte != null) {
/* 1015 */           return new Inet4Address(paramString, arrayOfByte);
/*      */         }
/* 1017 */         return new Inet6Address(paramString, paramArrayOfByte);
/*      */       }
/*      */     }
/*      */     
/* 1021 */     throw new UnknownHostException("addr is of illegal length");
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
/*      */   public static InetAddress getByName(String paramString)
/*      */     throws UnknownHostException
/*      */   {
/* 1055 */     return getAllByName(paramString)[0];
/*      */   }
/*      */   
/*      */   private static InetAddress getByName(String paramString, InetAddress paramInetAddress)
/*      */     throws UnknownHostException
/*      */   {
/* 1061 */     return getAllByName(paramString, paramInetAddress)[0];
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
/*      */   public static InetAddress[] getAllByName(String paramString)
/*      */     throws UnknownHostException
/*      */   {
/* 1105 */     return getAllByName(paramString, null);
/*      */   }
/*      */   
/*      */   private static InetAddress[] getAllByName(String paramString, InetAddress paramInetAddress)
/*      */     throws UnknownHostException
/*      */   {
/* 1111 */     if ((paramString == null) || (paramString.length() == 0)) {
/* 1112 */       InetAddress[] arrayOfInetAddress1 = new InetAddress[1];
/* 1113 */       arrayOfInetAddress1[0] = impl.loopbackAddress();
/* 1114 */       return arrayOfInetAddress1;
/*      */     }
/*      */     
/* 1117 */     int i = 0;
/* 1118 */     if (paramString.charAt(0) == '[')
/*      */     {
/* 1120 */       if ((paramString.length() > 2) && (paramString.charAt(paramString.length() - 1) == ']')) {
/* 1121 */         paramString = paramString.substring(1, paramString.length() - 1);
/* 1122 */         i = 1;
/*      */       }
/*      */       else {
/* 1125 */         throw new UnknownHostException(paramString + ": invalid IPv6 address");
/*      */       }
/*      */     }
/*      */     
/*      */ 
/* 1130 */     if ((Character.digit(paramString.charAt(0), 16) != -1) || 
/* 1131 */       (paramString.charAt(0) == ':')) {
/* 1132 */       byte[] arrayOfByte = null;
/* 1133 */       int j = -1;
/* 1134 */       String str = null;
/*      */       
/* 1136 */       arrayOfByte = IPAddressUtil.textToNumericFormatV4(paramString);
/* 1137 */       if (arrayOfByte == null)
/*      */       {
/*      */         int k;
/*      */         
/* 1141 */         if ((k = paramString.indexOf("%")) != -1) {
/* 1142 */           j = checkNumericZone(paramString);
/* 1143 */           if (j == -1) {
/* 1144 */             str = paramString.substring(k + 1);
/*      */           }
/*      */         }
/* 1147 */         if (((arrayOfByte = IPAddressUtil.textToNumericFormatV6(paramString)) == null) && (paramString.contains(":"))) {
/* 1148 */           throw new UnknownHostException(paramString + ": invalid IPv6 address");
/*      */         }
/* 1150 */       } else if (i != 0)
/*      */       {
/* 1152 */         throw new UnknownHostException("[" + paramString + "]");
/*      */       }
/* 1154 */       InetAddress[] arrayOfInetAddress2 = new InetAddress[1];
/* 1155 */       if (arrayOfByte != null) {
/* 1156 */         if (arrayOfByte.length == 4) {
/* 1157 */           arrayOfInetAddress2[0] = new Inet4Address(null, arrayOfByte);
/*      */         }
/* 1159 */         else if (str != null) {
/* 1160 */           arrayOfInetAddress2[0] = new Inet6Address(null, arrayOfByte, str);
/*      */         } else {
/* 1162 */           arrayOfInetAddress2[0] = new Inet6Address(null, arrayOfByte, j);
/*      */         }
/*      */         
/* 1165 */         return arrayOfInetAddress2;
/*      */       }
/* 1167 */     } else if (i != 0)
/*      */     {
/* 1169 */       throw new UnknownHostException("[" + paramString + "]");
/*      */     }
/* 1171 */     return getAllByName0(paramString, paramInetAddress, true);
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
/*      */   public static InetAddress getLoopbackAddress()
/*      */   {
/* 1186 */     return impl.loopbackAddress();
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private static int checkNumericZone(String paramString)
/*      */     throws UnknownHostException
/*      */   {
/* 1198 */     int i = paramString.indexOf('%');
/* 1199 */     int j = paramString.length();
/* 1200 */     int m = 0;
/* 1201 */     if (i == -1) {
/* 1202 */       return -1;
/*      */     }
/* 1204 */     for (int n = i + 1; n < j; n++) {
/* 1205 */       char c = paramString.charAt(n);
/* 1206 */       if (c == ']') {
/* 1207 */         if (n != i + 1)
/*      */           break;
/* 1209 */         return -1;
/*      */       }
/*      */       
/*      */       int k;
/* 1213 */       if ((k = Character.digit(c, 10)) < 0) {
/* 1214 */         return -1;
/*      */       }
/* 1216 */       m = m * 10 + k;
/*      */     }
/* 1218 */     return m;
/*      */   }
/*      */   
/*      */   private static InetAddress[] getAllByName0(String paramString)
/*      */     throws UnknownHostException
/*      */   {
/* 1224 */     return getAllByName0(paramString, true);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   static InetAddress[] getAllByName0(String paramString, boolean paramBoolean)
/*      */     throws UnknownHostException
/*      */   {
/* 1232 */     return getAllByName0(paramString, null, paramBoolean);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private static InetAddress[] getAllByName0(String paramString, InetAddress paramInetAddress, boolean paramBoolean)
/*      */     throws UnknownHostException
/*      */   {
/* 1244 */     if (paramBoolean) {
/* 1245 */       localObject = System.getSecurityManager();
/* 1246 */       if (localObject != null) {
/* 1247 */         ((SecurityManager)localObject).checkConnect(paramString, -1);
/*      */       }
/*      */     }
/*      */     
/* 1251 */     Object localObject = getCachedAddresses(paramString);
/*      */     
/*      */ 
/* 1254 */     if (localObject == null) {
/* 1255 */       localObject = getAddressesFromNameService(paramString, paramInetAddress);
/*      */     }
/*      */     
/* 1258 */     if (localObject == unknown_array) {
/* 1259 */       throw new UnknownHostException(paramString);
/*      */     }
/* 1261 */     return (InetAddress[])((InetAddress[])localObject).clone();
/*      */   }
/*      */   
/*      */   private static InetAddress[] getAddressesFromNameService(String paramString, InetAddress paramInetAddress)
/*      */     throws UnknownHostException
/*      */   {
/* 1267 */     Object localObject1 = null;
/* 1268 */     boolean bool = false;
/* 1269 */     Object localObject2 = null;
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1289 */     if ((localObject1 = checkLookupTable(paramString)) == null)
/*      */     {
/*      */       try
/*      */       {
/*      */ 
/* 1294 */         for (Iterator localIterator = nameServices.iterator(); localIterator.hasNext();) { localNameService = (NameService)localIterator.next();
/*      */           
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */           try
/*      */           {
/* 1302 */             localObject1 = localNameService.lookupAllHostAddr(paramString);
/* 1303 */             bool = true;
/*      */           }
/*      */           catch (UnknownHostException localUnknownHostException) {
/* 1306 */             if (paramString.equalsIgnoreCase("localhost")) {
/* 1307 */               InetAddress[] arrayOfInetAddress = { impl.loopbackAddress() };
/* 1308 */               localObject1 = arrayOfInetAddress;
/* 1309 */               bool = true;
/* 1310 */               break;
/*      */             }
/*      */             
/* 1313 */             localObject1 = unknown_array;
/* 1314 */             bool = false;
/* 1315 */             localObject2 = localUnknownHostException;
/*      */           }
/*      */         }
/*      */         
/*      */         NameService localNameService;
/*      */         
/* 1321 */         if ((paramInetAddress != null) && (localObject1.length > 1) && (!localObject1[0].equals(paramInetAddress)))
/*      */         {
/* 1323 */           for (int i = 1; 
/* 1324 */               i < localObject1.length; i++) {
/* 1325 */             if (localObject1[i].equals(paramInetAddress)) {
/*      */               break;
/*      */             }
/*      */           }
/*      */           
/* 1330 */           if (i < localObject1.length) {
/* 1331 */             Object localObject3 = paramInetAddress;
/* 1332 */             for (int j = 0; j < i; j++) {
/* 1333 */               localNameService = localObject1[j];
/* 1334 */               localObject1[j] = localObject3;
/* 1335 */               localObject3 = localNameService;
/*      */             }
/* 1337 */             localObject1[i] = localObject3;
/*      */           }
/*      */         }
/*      */         
/* 1341 */         cacheAddresses(paramString, (InetAddress[])localObject1, bool);
/*      */         
/* 1343 */         if ((!bool) && (localObject2 != null)) {
/* 1344 */           throw ((Throwable)localObject2);
/*      */         }
/*      */       }
/*      */       finally
/*      */       {
/* 1349 */         updateLookupTable(paramString);
/*      */       }
/*      */     }
/*      */     
/* 1353 */     return (InetAddress[])localObject1;
/*      */   }
/*      */   
/*      */   private static InetAddress[] checkLookupTable(String paramString)
/*      */   {
/* 1358 */     synchronized (lookupTable)
/*      */     {
/*      */ 
/*      */ 
/* 1362 */       if (!lookupTable.containsKey(paramString)) {
/* 1363 */         lookupTable.put(paramString, null);
/* 1364 */         return null;
/*      */       }
/*      */       
/*      */ 
/*      */ 
/*      */ 
/* 1370 */       while (lookupTable.containsKey(paramString)) {
/*      */         try {
/* 1372 */           lookupTable.wait();
/*      */         }
/*      */         catch (InterruptedException localInterruptedException) {}
/*      */       }
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1382 */     ??? = getCachedAddresses(paramString);
/* 1383 */     if (??? == null) {
/* 1384 */       synchronized (lookupTable) {
/* 1385 */         lookupTable.put(paramString, null);
/* 1386 */         return null;
/*      */       }
/*      */     }
/*      */     
/* 1390 */     return (InetAddress[])???;
/*      */   }
/*      */   
/*      */   private static void updateLookupTable(String paramString) {
/* 1394 */     synchronized (lookupTable) {
/* 1395 */       lookupTable.remove(paramString);
/* 1396 */       lookupTable.notifyAll();
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
/*      */   public static InetAddress getByAddress(byte[] paramArrayOfByte)
/*      */     throws UnknownHostException
/*      */   {
/* 1418 */     return getByAddress(null, paramArrayOfByte);
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
/*      */   public static InetAddress getLocalHost()
/*      */     throws UnknownHostException
/*      */   {
/* 1451 */     SecurityManager localSecurityManager = System.getSecurityManager();
/*      */     try {
/* 1453 */       String str = impl.getLocalHostName();
/*      */       
/* 1455 */       if (localSecurityManager != null) {
/* 1456 */         localSecurityManager.checkConnect(str, -1);
/*      */       }
/*      */       
/* 1459 */       if (str.equals("localhost")) {
/* 1460 */         return impl.loopbackAddress();
/*      */       }
/*      */       
/* 1463 */       InetAddress localInetAddress = null;
/* 1464 */       synchronized (cacheLock) {
/* 1465 */         long l = System.currentTimeMillis();
/* 1466 */         if (cachedLocalHost != null) {
/* 1467 */           if (l - cacheTime < 5000L) {
/* 1468 */             localInetAddress = cachedLocalHost;
/*      */           } else {
/* 1470 */             cachedLocalHost = null;
/*      */           }
/*      */         }
/*      */         
/*      */ 
/* 1475 */         if (localInetAddress == null)
/*      */         {
/*      */           InetAddress[] arrayOfInetAddress;
/*      */           try {
/* 1479 */             arrayOfInetAddress = getAddressesFromNameService(str, null);
/*      */ 
/*      */           }
/*      */           catch (UnknownHostException localUnknownHostException1)
/*      */           {
/* 1484 */             UnknownHostException localUnknownHostException2 = new UnknownHostException(str + ": " + localUnknownHostException1.getMessage());
/* 1485 */             localUnknownHostException2.initCause(localUnknownHostException1);
/* 1486 */             throw localUnknownHostException2;
/*      */           }
/* 1488 */           cachedLocalHost = arrayOfInetAddress[0];
/* 1489 */           cacheTime = l;
/* 1490 */           localInetAddress = arrayOfInetAddress[0];
/*      */         }
/*      */       }
/* 1493 */       return localInetAddress;
/*      */     } catch (SecurityException localSecurityException) {}
/* 1495 */     return impl.loopbackAddress();
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
/*      */   static InetAddress anyLocalAddress()
/*      */   {
/* 1510 */     return impl.anyLocalAddress();
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   static InetAddressImpl loadImpl(String paramString)
/*      */   {
/* 1517 */     Object localObject = null;
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1526 */     String str = (String)AccessController.doPrivileged(new GetPropertyAction("impl.prefix", ""));
/*      */     try
/*      */     {
/* 1529 */       localObject = Class.forName("java.net." + str + paramString).newInstance();
/*      */     } catch (ClassNotFoundException localClassNotFoundException) {
/* 1531 */       System.err.println("Class not found: java.net." + str + paramString + ":\ncheck impl.prefix property " + "in your properties file.");
/*      */     }
/*      */     catch (InstantiationException localInstantiationException)
/*      */     {
/* 1535 */       System.err.println("Could not instantiate: java.net." + str + paramString + ":\ncheck impl.prefix property " + "in your properties file.");
/*      */     }
/*      */     catch (IllegalAccessException localIllegalAccessException)
/*      */     {
/* 1539 */       System.err.println("Cannot access class: java.net." + str + paramString + ":\ncheck impl.prefix property " + "in your properties file.");
/*      */     }
/*      */     
/*      */ 
/*      */ 
/* 1544 */     if (localObject == null) {
/*      */       try {
/* 1546 */         localObject = Class.forName(paramString).newInstance();
/*      */       } catch (Exception localException) {
/* 1548 */         throw new Error("System property impl.prefix incorrect");
/*      */       }
/*      */     }
/*      */     
/* 1552 */     return (InetAddressImpl)localObject;
/*      */   }
/*      */   
/*      */   private void readObjectNoData(ObjectInputStream paramObjectInputStream) throws IOException, ClassNotFoundException
/*      */   {
/* 1557 */     if (getClass().getClassLoader() != null) {
/* 1558 */       throw new SecurityException("invalid address type");
/*      */     }
/*      */   }
/*      */   
/*      */   static
/*      */   {
/*  203 */     preferIPv6Address = false;
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  256 */     nameServices = null;
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  269 */     preferIPv6Address = ((Boolean)AccessController.doPrivileged(new GetBooleanAction("java.net.preferIPv6Addresses"))).booleanValue();
/*  270 */     AccessController.doPrivileged(new PrivilegedAction()
/*      */     {
/*      */       public Void run() {
/*  273 */         System.loadLibrary("net");
/*  274 */         return null;
/*      */       }
/*  276 */     });
/*  277 */     init();
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  711 */     addressCache = new Cache(InetAddress.Cache.Type.Positive);
/*      */     
/*  713 */     negativeCache = new Cache(InetAddress.Cache.Type.Negative);
/*      */     
/*  715 */     addressCacheInit = false;
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  721 */     lookupTable = new HashMap();
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  952 */     impl = InetAddressImplFactory.create();
/*      */     
/*      */ 
/*  955 */     Object localObject = null;
/*  956 */     String str = "sun.net.spi.nameservice.provider.";
/*  957 */     int i = 1;
/*  958 */     nameServices = new ArrayList();
/*  959 */     localObject = (String)AccessController.doPrivileged(new GetPropertyAction(str + i));
/*      */     NameService localNameService;
/*  961 */     while (localObject != null) {
/*  962 */       localNameService = createNSProvider((String)localObject);
/*  963 */       if (localNameService != null) {
/*  964 */         nameServices.add(localNameService);
/*      */       }
/*  966 */       i++;
/*  967 */       localObject = (String)AccessController.doPrivileged(new GetPropertyAction(str + i));
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*  973 */     if (nameServices.size() == 0) {
/*  974 */       localNameService = createNSProvider("default");
/*  975 */       nameServices.add(localNameService);
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1421 */     cachedLocalHost = null;
/* 1422 */     cacheTime = 0L;
/*      */     
/* 1424 */     cacheLock = new Object();
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     try
/*      */     {
/* 1567 */       localObject = Unsafe.getUnsafe();
/* 1568 */       FIELDS_OFFSET = ((Unsafe)localObject).objectFieldOffset(InetAddress.class
/* 1569 */         .getDeclaredField("holder"));
/*      */       
/* 1571 */       UNSAFE = (Unsafe)localObject;
/*      */     } catch (ReflectiveOperationException localReflectiveOperationException) {
/* 1573 */       throw new Error(localReflectiveOperationException);
/*      */     }
/*      */   }
/*      */   
/*      */   private void readObject(ObjectInputStream paramObjectInputStream) throws IOException, ClassNotFoundException
/*      */   {
/* 1579 */     if (getClass().getClassLoader() != null) {
/* 1580 */       throw new SecurityException("invalid address type");
/*      */     }
/* 1582 */     ObjectInputStream.GetField localGetField = paramObjectInputStream.readFields();
/* 1583 */     String str = (String)localGetField.get("hostName", null);
/* 1584 */     int i = localGetField.get("address", 0);
/* 1585 */     int j = localGetField.get("family", 0);
/* 1586 */     InetAddressHolder localInetAddressHolder = new InetAddressHolder(str, i, j);
/* 1587 */     UNSAFE.putObject(this, FIELDS_OFFSET, localInetAddressHolder);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1597 */   private static final ObjectStreamField[] serialPersistentFields = { new ObjectStreamField("hostName", String.class), new ObjectStreamField("address", Integer.TYPE), new ObjectStreamField("family", Integer.TYPE) };
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   private void writeObject(ObjectOutputStream paramObjectOutputStream)
/*      */     throws IOException
/*      */   {
/* 1605 */     if (getClass().getClassLoader() != null) {
/* 1606 */       throw new SecurityException("invalid address type");
/*      */     }
/* 1608 */     ObjectOutputStream.PutField localPutField = paramObjectOutputStream.putFields();
/* 1609 */     localPutField.put("hostName", holder().getHostName());
/* 1610 */     localPutField.put("address", holder().getAddress());
/* 1611 */     localPutField.put("family", holder().getFamily());
/* 1612 */     paramObjectOutputStream.writeFields();
/*      */   }
/*      */   
/*      */   private static native void init();
/*      */ }


/* Location:              /Users/guanxiaoda/Documents/workspace/rt.jar!/java/net/InetAddress.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */