/*     */ package java.util;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.io.ObjectInputStream;
/*     */ import java.io.ObjectInputStream.GetField;
/*     */ import java.io.ObjectOutputStream;
/*     */ import java.io.ObjectOutputStream.PutField;
/*     */ import java.io.ObjectStreamField;
/*     */ import java.io.Serializable;
/*     */ import java.security.Permission;
/*     */ import java.security.PermissionCollection;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ final class PropertyPermissionCollection
/*     */   extends PermissionCollection
/*     */   implements Serializable
/*     */ {
/*     */   private transient Map<String, PropertyPermission> perms;
/*     */   private boolean all_allowed;
/*     */   private static final long serialVersionUID = 7015263904581634791L;
/*     */   
/*     */   public PropertyPermissionCollection()
/*     */   {
/* 447 */     this.perms = new HashMap(32);
/* 448 */     this.all_allowed = false;
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
/*     */   public void add(Permission paramPermission)
/*     */   {
/* 464 */     if (!(paramPermission instanceof PropertyPermission)) {
/* 465 */       throw new IllegalArgumentException("invalid permission: " + paramPermission);
/*     */     }
/* 467 */     if (isReadOnly()) {
/* 468 */       throw new SecurityException("attempt to add a Permission to a readonly PermissionCollection");
/*     */     }
/*     */     
/* 471 */     PropertyPermission localPropertyPermission1 = (PropertyPermission)paramPermission;
/* 472 */     String str1 = localPropertyPermission1.getName();
/*     */     
/* 474 */     synchronized (this) {
/* 475 */       PropertyPermission localPropertyPermission2 = (PropertyPermission)this.perms.get(str1);
/*     */       
/* 477 */       if (localPropertyPermission2 != null) {
/* 478 */         int i = localPropertyPermission2.getMask();
/* 479 */         int j = localPropertyPermission1.getMask();
/* 480 */         if (i != j) {
/* 481 */           int k = i | j;
/* 482 */           String str2 = PropertyPermission.getActions(k);
/* 483 */           this.perms.put(str1, new PropertyPermission(str1, str2));
/*     */         }
/*     */       } else {
/* 486 */         this.perms.put(str1, localPropertyPermission1);
/*     */       }
/*     */     }
/*     */     
/* 490 */     if ((!this.all_allowed) && 
/* 491 */       (str1.equals("*"))) {
/* 492 */       this.all_allowed = true;
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
/* 506 */     if (!(paramPermission instanceof PropertyPermission)) {
/* 507 */       return false;
/*     */     }
/* 509 */     PropertyPermission localPropertyPermission1 = (PropertyPermission)paramPermission;
/*     */     
/*     */ 
/* 512 */     int i = localPropertyPermission1.getMask();
/* 513 */     int j = 0;
/*     */     
/*     */     PropertyPermission localPropertyPermission2;
/* 516 */     if (this.all_allowed) {
/* 517 */       synchronized (this) {
/* 518 */         localPropertyPermission2 = (PropertyPermission)this.perms.get("*");
/*     */       }
/* 520 */       if (localPropertyPermission2 != null) {
/* 521 */         j |= localPropertyPermission2.getMask();
/* 522 */         if ((j & i) == i) {
/* 523 */           return true;
/*     */         }
/*     */       }
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */ 
/* 531 */     ??? = localPropertyPermission1.getName();
/*     */     
/*     */ 
/* 534 */     synchronized (this) {
/* 535 */       localPropertyPermission2 = (PropertyPermission)this.perms.get(???);
/*     */     }
/*     */     
/* 538 */     if (localPropertyPermission2 != null)
/*     */     {
/* 540 */       j |= localPropertyPermission2.getMask();
/* 541 */       if ((j & i) == i) {
/* 542 */         return true;
/*     */       }
/*     */     }
/*     */     
/*     */ 
/*     */ 
/* 548 */     int m = ((String)???).length() - 1;
/*     */     int k;
/* 550 */     while ((k = ((String)???).lastIndexOf(".", m)) != -1)
/*     */     {
/* 552 */       ??? = ((String)???).substring(0, k + 1) + "*";
/*     */       
/* 554 */       synchronized (this) {
/* 555 */         localPropertyPermission2 = (PropertyPermission)this.perms.get(???);
/*     */       }
/*     */       
/* 558 */       if (localPropertyPermission2 != null) {
/* 559 */         j |= localPropertyPermission2.getMask();
/* 560 */         if ((j & i) == i)
/* 561 */           return true;
/*     */       }
/* 563 */       m = k - 1;
/*     */     }
/*     */     
/*     */ 
/*     */ 
/* 568 */     return false;
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
/*     */ 
/*     */ 
/* 605 */   private static final ObjectStreamField[] serialPersistentFields = { new ObjectStreamField("permissions", Hashtable.class), new ObjectStreamField("all_allowed", Boolean.TYPE) };
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
/*     */     //   5: getfield 4	java/util/PropertyPermissionCollection:perms	Ljava/util/Map;
/*     */     //   8: invokeinterface 31 1 0
/*     */     //   13: invokestatic 32	java/util/Collections:enumeration	(Ljava/util/Collection;)Ljava/util/Enumeration;
/*     */     //   16: aload_1
/*     */     //   17: monitorexit
/*     */     //   18: areturn
/*     */     //   19: astore_2
/*     */     //   20: aload_1
/*     */     //   21: monitorexit
/*     */     //   22: aload_2
/*     */     //   23: athrow
/*     */     // Line number table:
/*     */     //   Java source line #580	-> byte code offset #0
/*     */     //   Java source line #585	-> byte code offset #4
/*     */     //   Java source line #586	-> byte code offset #19
/*     */     // Local variable table:
/*     */     //   start	length	slot	name	signature
/*     */     //   0	24	0	this	PropertyPermissionCollection
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
/* 623 */     Hashtable localHashtable = new Hashtable(this.perms.size() * 2);
/* 624 */     synchronized (this) {
/* 625 */       localHashtable.putAll(this.perms);
/*     */     }
/*     */     
/*     */ 
/* 629 */     ??? = paramObjectOutputStream.putFields();
/* 630 */     ((ObjectOutputStream.PutField)???).put("all_allowed", this.all_allowed);
/* 631 */     ((ObjectOutputStream.PutField)???).put("permissions", localHashtable);
/* 632 */     paramObjectOutputStream.writeFields();
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
/* 645 */     ObjectInputStream.GetField localGetField = paramObjectInputStream.readFields();
/*     */     
/*     */ 
/* 648 */     this.all_allowed = localGetField.get("all_allowed", false);
/*     */     
/*     */ 
/*     */ 
/*     */ 
/* 653 */     Hashtable localHashtable = (Hashtable)localGetField.get("permissions", null);
/* 654 */     this.perms = new HashMap(localHashtable.size() * 2);
/* 655 */     this.perms.putAll(localHashtable);
/*     */   }
/*     */ }


/* Location:              /Users/guanxiaoda/Documents/workspace/rt.jar!/java/util/PropertyPermissionCollection.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */