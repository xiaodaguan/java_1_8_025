/*     */ package java.security;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.io.ObjectInputStream;
/*     */ import java.io.ObjectInputStream.GetField;
/*     */ import java.io.ObjectOutputStream;
/*     */ import java.io.ObjectOutputStream.PutField;
/*     */ import java.io.ObjectStreamField;
/*     */ import java.io.Serializable;
/*     */ import java.util.Enumeration;
/*     */ import java.util.HashMap;
/*     */ import java.util.Hashtable;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ final class BasicPermissionCollection
/*     */   extends PermissionCollection
/*     */   implements Serializable
/*     */ {
/*     */   private static final long serialVersionUID = 739301742472979399L;
/*     */   private transient Map<String, Permission> perms;
/*     */   private boolean all_allowed;
/*     */   private Class<?> permClass;
/*     */   
/*     */   public BasicPermissionCollection(Class<?> paramClass)
/*     */   {
/* 335 */     this.perms = new HashMap(11);
/* 336 */     this.all_allowed = false;
/* 337 */     this.permClass = paramClass;
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
/*     */   public void add(Permission paramPermission)
/*     */   {
/* 356 */     if (!(paramPermission instanceof BasicPermission)) {
/* 357 */       throw new IllegalArgumentException("invalid permission: " + paramPermission);
/*     */     }
/* 359 */     if (isReadOnly()) {
/* 360 */       throw new SecurityException("attempt to add a Permission to a readonly PermissionCollection");
/*     */     }
/* 362 */     BasicPermission localBasicPermission = (BasicPermission)paramPermission;
/*     */     
/*     */ 
/*     */ 
/*     */ 
/* 367 */     if (this.permClass == null)
/*     */     {
/* 369 */       this.permClass = localBasicPermission.getClass();
/*     */     }
/* 371 */     else if (localBasicPermission.getClass() != this.permClass) {
/* 372 */       throw new IllegalArgumentException("invalid permission: " + paramPermission);
/*     */     }
/*     */     
/*     */ 
/* 376 */     synchronized (this) {
/* 377 */       this.perms.put(localBasicPermission.getCanonicalName(), paramPermission);
/*     */     }
/*     */     
/*     */ 
/* 381 */     if ((!this.all_allowed) && 
/* 382 */       (localBasicPermission.getCanonicalName().equals("*"))) {
/* 383 */       this.all_allowed = true;
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
/*     */   public boolean implies(Permission paramPermission)
/*     */   {
/* 397 */     if (!(paramPermission instanceof BasicPermission)) {
/* 398 */       return false;
/*     */     }
/* 400 */     BasicPermission localBasicPermission = (BasicPermission)paramPermission;
/*     */     
/*     */ 
/* 403 */     if (localBasicPermission.getClass() != this.permClass) {
/* 404 */       return false;
/*     */     }
/*     */     
/* 407 */     if (this.all_allowed) {
/* 408 */       return true;
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */ 
/* 414 */     String str = localBasicPermission.getCanonicalName();
/*     */     
/*     */ 
/*     */     Permission localPermission;
/*     */     
/* 419 */     synchronized (this) {
/* 420 */       localPermission = (Permission)this.perms.get(str);
/*     */     }
/*     */     
/* 423 */     if (localPermission != null)
/*     */     {
/* 425 */       return localPermission.implies(paramPermission);
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */ 
/* 431 */     int j = str.length() - 1;
/*     */     int i;
/* 433 */     while ((i = str.lastIndexOf(".", j)) != -1)
/*     */     {
/* 435 */       str = str.substring(0, i + 1) + "*";
/*     */       
/*     */ 
/* 438 */       synchronized (this) {
/* 439 */         localPermission = (Permission)this.perms.get(str);
/*     */       }
/*     */       
/* 442 */       if (localPermission != null) {
/* 443 */         return localPermission.implies(paramPermission);
/*     */       }
/* 445 */       j = i - 1;
/*     */     }
/*     */     
/*     */ 
/*     */ 
/* 450 */     return false;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 485 */   private static final ObjectStreamField[] serialPersistentFields = { new ObjectStreamField("permissions", Hashtable.class), new ObjectStreamField("all_allowed", Boolean.TYPE), new ObjectStreamField("permClass", Class.class) };
/*     */   
/*     */   /* Error */
/*     */   public Enumeration<Permission> elements()
/*     */   {
/*     */     // Byte code:
/*     */     //   0: aload_0
/*     */     //   1: dup
/*     */     //   2: astore_1
/*     */     //   3: monitorenter
/*     */     //   4: aload_0
/*     */     //   5: getfield 4	java/security/BasicPermissionCollection:perms	Ljava/util/Map;
/*     */     //   8: invokeinterface 32 1 0
/*     */     //   13: invokestatic 33	java/util/Collections:enumeration	(Ljava/util/Collection;)Ljava/util/Enumeration;
/*     */     //   16: aload_1
/*     */     //   17: monitorexit
/*     */     //   18: areturn
/*     */     //   19: astore_2
/*     */     //   20: aload_1
/*     */     //   21: monitorexit
/*     */     //   22: aload_2
/*     */     //   23: athrow
/*     */     // Line number table:
/*     */     //   Java source line #461	-> byte code offset #0
/*     */     //   Java source line #462	-> byte code offset #4
/*     */     //   Java source line #463	-> byte code offset #19
/*     */     // Local variable table:
/*     */     //   start	length	slot	name	signature
/*     */     //   0	24	0	this	BasicPermissionCollection
/*     */     //   2	19	1	Ljava/lang/Object;	Object
/*     */     //   19	4	2	localObject1	Object
/*     */     // Exception table:
/*     */     //   from	to	target	type
/*     */     //   4	18	19	finally
/*     */     //   19	22	19	finally
/*     */   }
/*     */   
/*     */   private void writeObject(ObjectOutputStream paramObjectOutputStream)
/*     */     throws IOException
/*     */   {
/* 504 */     Hashtable localHashtable = new Hashtable(this.perms.size() * 2);
/*     */     
/* 506 */     synchronized (this) {
/* 507 */       localHashtable.putAll(this.perms);
/*     */     }
/*     */     
/*     */ 
/* 511 */     ??? = paramObjectOutputStream.putFields();
/* 512 */     ((ObjectOutputStream.PutField)???).put("all_allowed", this.all_allowed);
/* 513 */     ((ObjectOutputStream.PutField)???).put("permissions", localHashtable);
/* 514 */     ((ObjectOutputStream.PutField)???).put("permClass", this.permClass);
/* 515 */     paramObjectOutputStream.writeFields();
/*     */   }
/*     */   
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
/* 528 */     ObjectInputStream.GetField localGetField = paramObjectInputStream.readFields();
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 535 */     Hashtable localHashtable = (Hashtable)localGetField.get("permissions", null);
/* 536 */     this.perms = new HashMap(localHashtable.size() * 2);
/* 537 */     this.perms.putAll(localHashtable);
/*     */     
/*     */ 
/* 540 */     this.all_allowed = localGetField.get("all_allowed", false);
/*     */     
/*     */ 
/* 543 */     this.permClass = ((Class)localGetField.get("permClass", null));
/*     */     
/* 545 */     if (this.permClass == null)
/*     */     {
/* 547 */       Enumeration localEnumeration = localHashtable.elements();
/* 548 */       if (localEnumeration.hasMoreElements()) {
/* 549 */         Permission localPermission = (Permission)localEnumeration.nextElement();
/* 550 */         this.permClass = localPermission.getClass();
/*     */       }
/*     */     }
/*     */   }
/*     */ }


/* Location:              /Users/guanxiaoda/Documents/workspace/rt.jar!/java/security/BasicPermissionCollection.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */