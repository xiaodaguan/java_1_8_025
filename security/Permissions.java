/*     */ package java.security;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.io.ObjectInputStream;
/*     */ import java.io.ObjectInputStream.GetField;
/*     */ import java.io.ObjectOutputStream;
/*     */ import java.io.ObjectOutputStream.PutField;
/*     */ import java.io.ObjectStreamField;
/*     */ import java.io.Serializable;
/*     */ import java.security.cert.Certificate;
/*     */ import java.util.Enumeration;
/*     */ import java.util.HashMap;
/*     */ import java.util.Hashtable;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class Permissions
/*     */   extends PermissionCollection
/*     */   implements Serializable
/*     */ {
/*     */   private transient Map<Class<?>, PermissionCollection> permsMap;
/*  92 */   private transient boolean hasUnresolved = false;
/*     */   
/*     */ 
/*     */   PermissionCollection allPermission;
/*     */   
/*     */   private static final long serialVersionUID = 4858622370623524688L;
/*     */   
/*     */ 
/*     */   public Permissions()
/*     */   {
/* 102 */     this.permsMap = new HashMap(11);
/* 103 */     this.allPermission = null;
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
/*     */   public void add(Permission paramPermission)
/*     */   {
/* 125 */     if (isReadOnly()) {
/* 126 */       throw new SecurityException("attempt to add a Permission to a readonly Permissions object");
/*     */     }
/*     */     
/*     */     PermissionCollection localPermissionCollection;
/*     */     
/* 131 */     synchronized (this) {
/* 132 */       localPermissionCollection = getPermissionCollection(paramPermission, true);
/* 133 */       localPermissionCollection.add(paramPermission);
/*     */     }
/*     */     
/*     */ 
/* 137 */     if ((paramPermission instanceof AllPermission)) {
/* 138 */       this.allPermission = localPermissionCollection;
/*     */     }
/* 140 */     if ((paramPermission instanceof UnresolvedPermission)) {
/* 141 */       this.hasUnresolved = true;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public boolean implies(Permission paramPermission)
/*     */   {
/* 175 */     if (this.allPermission != null) {
/* 176 */       return true;
/*     */     }
/* 178 */     synchronized (this) {
/* 179 */       PermissionCollection localPermissionCollection = getPermissionCollection(paramPermission, false);
/*     */       
/* 181 */       if (localPermissionCollection != null) {
/* 182 */         return localPermissionCollection.implies(paramPermission);
/*     */       }
/*     */       
/* 185 */       return false;
/*     */     }
/*     */   }
/*     */   
/*     */   /* Error */
/*     */   public Enumeration<Permission> elements()
/*     */   {
/*     */     // Byte code:
/*     */     //   0: aload_0
/*     */     //   1: dup
/*     */     //   2: astore_1
/*     */     //   3: monitorenter
/*     */     //   4: new 16	java/security/PermissionsEnumerator
/*     */     //   7: dup
/*     */     //   8: aload_0
/*     */     //   9: getfield 5	java/security/Permissions:permsMap	Ljava/util/Map;
/*     */     //   12: invokeinterface 17 1 0
/*     */     //   17: invokeinterface 18 1 0
/*     */     //   22: invokespecial 19	java/security/PermissionsEnumerator:<init>	(Ljava/util/Iterator;)V
/*     */     //   25: aload_1
/*     */     //   26: monitorexit
/*     */     //   27: areturn
/*     */     //   28: astore_2
/*     */     //   29: aload_1
/*     */     //   30: monitorexit
/*     */     //   31: aload_2
/*     */     //   32: athrow
/*     */     // Line number table:
/*     */     //   Java source line #202	-> byte code offset #0
/*     */     //   Java source line #203	-> byte code offset #4
/*     */     //   Java source line #204	-> byte code offset #28
/*     */     // Local variable table:
/*     */     //   start	length	slot	name	signature
/*     */     //   0	33	0	this	Permissions
/*     */     //   2	28	1	Ljava/lang/Object;	Object
/*     */     //   28	4	2	localObject1	Object
/*     */     // Exception table:
/*     */     //   from	to	target	type
/*     */     //   4	27	28	finally
/*     */     //   28	31	28	finally
/*     */   }
/*     */   
/*     */   private PermissionCollection getPermissionCollection(Permission paramPermission, boolean paramBoolean)
/*     */   {
/* 240 */     Class localClass = paramPermission.getClass();
/*     */     
/* 242 */     Object localObject = (PermissionCollection)this.permsMap.get(localClass);
/*     */     
/* 244 */     if ((!this.hasUnresolved) && (!paramBoolean))
/* 245 */       return (PermissionCollection)localObject;
/* 246 */     if (localObject == null)
/*     */     {
/*     */ 
/* 249 */       localObject = this.hasUnresolved ? getUnresolvedPermissions(paramPermission) : null;
/*     */       
/*     */ 
/* 252 */       if ((localObject == null) && (paramBoolean))
/*     */       {
/* 254 */         localObject = paramPermission.newPermissionCollection();
/*     */         
/*     */ 
/*     */ 
/* 258 */         if (localObject == null) {
/* 259 */           localObject = new PermissionsHash();
/*     */         }
/*     */       }
/* 262 */       if (localObject != null) {
/* 263 */         this.permsMap.put(localClass, localObject);
/*     */       }
/*     */     }
/* 266 */     return (PermissionCollection)localObject;
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
/*     */   private PermissionCollection getUnresolvedPermissions(Permission paramPermission)
/*     */   {
/* 283 */     UnresolvedPermissionCollection localUnresolvedPermissionCollection = (UnresolvedPermissionCollection)this.permsMap.get(UnresolvedPermission.class);
/*     */     
/*     */ 
/* 286 */     if (localUnresolvedPermissionCollection == null) {
/* 287 */       return null;
/*     */     }
/*     */     
/* 290 */     List localList = localUnresolvedPermissionCollection.getUnresolvedPermissions(paramPermission);
/*     */     
/*     */ 
/* 293 */     if (localList == null) {
/* 294 */       return null;
/*     */     }
/* 296 */     Certificate[] arrayOfCertificate = null;
/*     */     
/* 298 */     Object[] arrayOfObject = paramPermission.getClass().getSigners();
/*     */     
/* 300 */     int i = 0;
/* 301 */     if (arrayOfObject != null) {
/* 302 */       for (int j = 0; j < arrayOfObject.length; j++) {
/* 303 */         if ((arrayOfObject[j] instanceof Certificate)) {
/* 304 */           i++;
/*     */         }
/*     */       }
/* 307 */       arrayOfCertificate = new Certificate[i];
/* 308 */       i = 0;
/* 309 */       for (j = 0; j < arrayOfObject.length; j++) {
/* 310 */         if ((arrayOfObject[j] instanceof Certificate)) {
/* 311 */           arrayOfCertificate[(i++)] = ((Certificate)arrayOfObject[j]);
/*     */         }
/*     */       }
/*     */     }
/*     */     
/* 316 */     Object localObject1 = null;
/* 317 */     synchronized (localList) {
/* 318 */       int k = localList.size();
/* 319 */       for (int m = 0; m < k; m++) {
/* 320 */         UnresolvedPermission localUnresolvedPermission = (UnresolvedPermission)localList.get(m);
/* 321 */         Permission localPermission = localUnresolvedPermission.resolve(paramPermission, arrayOfCertificate);
/* 322 */         if (localPermission != null) {
/* 323 */           if (localObject1 == null) {
/* 324 */             localObject1 = paramPermission.newPermissionCollection();
/* 325 */             if (localObject1 == null)
/* 326 */               localObject1 = new PermissionsHash();
/*     */           }
/* 328 */           ((PermissionCollection)localObject1).add(localPermission);
/*     */         }
/*     */       }
/*     */     }
/* 332 */     return (PermissionCollection)localObject1;
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
/* 346 */   private static final ObjectStreamField[] serialPersistentFields = { new ObjectStreamField("perms", Hashtable.class), new ObjectStreamField("allPermission", PermissionCollection.class) };
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private void writeObject(ObjectOutputStream paramObjectOutputStream)
/*     */     throws IOException
/*     */   {
/* 364 */     Hashtable localHashtable = new Hashtable(this.permsMap.size() * 2);
/* 365 */     synchronized (this) {
/* 366 */       localHashtable.putAll(this.permsMap);
/*     */     }
/*     */     
/*     */ 
/* 370 */     ??? = paramObjectOutputStream.putFields();
/*     */     
/* 372 */     ((ObjectOutputStream.PutField)???).put("allPermission", this.allPermission);
/* 373 */     ((ObjectOutputStream.PutField)???).put("perms", localHashtable);
/* 374 */     paramObjectOutputStream.writeFields();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private void readObject(ObjectInputStream paramObjectInputStream)
/*     */     throws IOException, ClassNotFoundException
/*     */   {
/* 386 */     ObjectInputStream.GetField localGetField = paramObjectInputStream.readFields();
/*     */     
/*     */ 
/* 389 */     this.allPermission = ((PermissionCollection)localGetField.get("allPermission", null));
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 396 */     Hashtable localHashtable = (Hashtable)localGetField.get("perms", null);
/* 397 */     this.permsMap = new HashMap(localHashtable.size() * 2);
/* 398 */     this.permsMap.putAll(localHashtable);
/*     */     
/*     */ 
/*     */ 
/* 402 */     UnresolvedPermissionCollection localUnresolvedPermissionCollection = (UnresolvedPermissionCollection)this.permsMap.get(UnresolvedPermission.class);
/* 403 */     this.hasUnresolved = ((localUnresolvedPermissionCollection != null) && (localUnresolvedPermissionCollection.elements().hasMoreElements()));
/*     */   }
/*     */ }


/* Location:              /Users/guanxiaoda/Documents/workspace/rt.jar!/java/security/Permissions.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */