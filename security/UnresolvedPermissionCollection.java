/*     */ package java.security;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.io.ObjectInputStream;
/*     */ import java.io.ObjectInputStream.GetField;
/*     */ import java.io.ObjectOutputStream;
/*     */ import java.io.ObjectOutputStream.PutField;
/*     */ import java.io.ObjectStreamField;
/*     */ import java.io.Serializable;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collections;
/*     */ import java.util.Enumeration;
/*     */ import java.util.HashMap;
/*     */ import java.util.Hashtable;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Map.Entry;
/*     */ import java.util.Set;
/*     */ import java.util.Vector;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ final class UnresolvedPermissionCollection
/*     */   extends PermissionCollection
/*     */   implements Serializable
/*     */ {
/*     */   private transient Map<String, List<UnresolvedPermission>> perms;
/*     */   private static final long serialVersionUID = -7176153071733132400L;
/*     */   
/*     */   public UnresolvedPermissionCollection()
/*     */   {
/*  64 */     this.perms = new HashMap(11);
/*     */   }
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
/*  76 */     if (!(paramPermission instanceof UnresolvedPermission)) {
/*  77 */       throw new IllegalArgumentException("invalid permission: " + paramPermission);
/*     */     }
/*  79 */     UnresolvedPermission localUnresolvedPermission = (UnresolvedPermission)paramPermission;
/*     */     
/*     */     Object localObject1;
/*  82 */     synchronized (this) {
/*  83 */       localObject1 = (List)this.perms.get(localUnresolvedPermission.getName());
/*  84 */       if (localObject1 == null) {
/*  85 */         localObject1 = new ArrayList();
/*  86 */         this.perms.put(localUnresolvedPermission.getName(), localObject1);
/*     */       }
/*     */     }
/*  89 */     synchronized (localObject1) {
/*  90 */       ((List)localObject1).add(localUnresolvedPermission);
/*     */     }
/*     */   }
/*     */   
/*     */   /* Error */
/*     */   List<UnresolvedPermission> getUnresolvedPermissions(Permission paramPermission)
/*     */   {
/*     */     // Byte code:
/*     */     //   0: aload_0
/*     */     //   1: dup
/*     */     //   2: astore_2
/*     */     //   3: monitorenter
/*     */     //   4: aload_0
/*     */     //   5: getfield 4	java/security/UnresolvedPermissionCollection:perms	Ljava/util/Map;
/*     */     //   8: aload_1
/*     */     //   9: invokevirtual 21	java/lang/Object:getClass	()Ljava/lang/Class;
/*     */     //   12: invokevirtual 22	java/lang/Class:getName	()Ljava/lang/String;
/*     */     //   15: invokeinterface 15 2 0
/*     */     //   20: checkcast 16	java/util/List
/*     */     //   23: aload_2
/*     */     //   24: monitorexit
/*     */     //   25: areturn
/*     */     //   26: astore_3
/*     */     //   27: aload_2
/*     */     //   28: monitorexit
/*     */     //   29: aload_3
/*     */     //   30: athrow
/*     */     // Line number table:
/*     */     //   Java source line #99	-> byte code offset #0
/*     */     //   Java source line #100	-> byte code offset #4
/*     */     //   Java source line #101	-> byte code offset #26
/*     */     // Local variable table:
/*     */     //   start	length	slot	name	signature
/*     */     //   0	31	0	this	UnresolvedPermissionCollection
/*     */     //   0	31	1	paramPermission	Permission
/*     */     //   2	26	2	Ljava/lang/Object;	Object
/*     */     //   26	4	3	localObject1	Object
/*     */     // Exception table:
/*     */     //   from	to	target	type
/*     */     //   4	25	26	finally
/*     */     //   26	29	26	finally
/*     */   }
/*     */   
/*     */   public boolean implies(Permission paramPermission)
/*     */   {
/* 110 */     return false;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public Enumeration<Permission> elements()
/*     */   {
/* 121 */     ArrayList localArrayList = new ArrayList();
/*     */     
/*     */ 
/*     */ 
/* 125 */     synchronized (this) {
/* 126 */       for (List localList : this.perms.values()) {
/* 127 */         synchronized (localList) {
/* 128 */           localArrayList.addAll(localList);
/*     */         }
/*     */       }
/*     */     }
/*     */     
/* 133 */     return Collections.enumeration(localArrayList);
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
/* 147 */   private static final ObjectStreamField[] serialPersistentFields = { new ObjectStreamField("permissions", Hashtable.class) };
/*     */   
/*     */ 
/*     */ 
/*     */ 
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
/* 164 */     Hashtable localHashtable = new Hashtable(this.perms.size() * 2);
/*     */     
/*     */ 
/* 167 */     synchronized (this) {
/* 168 */       Set localSet = this.perms.entrySet();
/* 169 */       for (Map.Entry localEntry : localSet)
/*     */       {
/* 171 */         List localList = (List)localEntry.getValue();
/* 172 */         Vector localVector = new Vector(localList.size());
/* 173 */         synchronized (localList) {
/* 174 */           localVector.addAll(localList);
/*     */         }
/*     */         
/*     */ 
/* 178 */         localHashtable.put(localEntry.getKey(), localVector);
/*     */       }
/*     */     }
/*     */     
/*     */ 
/* 183 */     ??? = paramObjectOutputStream.putFields();
/* 184 */     ((ObjectOutputStream.PutField)???).put("permissions", localHashtable);
/* 185 */     paramObjectOutputStream.writeFields();
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
/* 197 */     ObjectInputStream.GetField localGetField = paramObjectInputStream.readFields();
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 205 */     Hashtable localHashtable = (Hashtable)localGetField.get("permissions", null);
/* 206 */     this.perms = new HashMap(localHashtable.size() * 2);
/*     */     
/*     */ 
/* 209 */     Set localSet = localHashtable.entrySet();
/* 210 */     for (Map.Entry localEntry : localSet)
/*     */     {
/* 212 */       Vector localVector = (Vector)localEntry.getValue();
/* 213 */       ArrayList localArrayList = new ArrayList(localVector.size());
/* 214 */       localArrayList.addAll(localVector);
/*     */       
/*     */ 
/* 217 */       this.perms.put(localEntry.getKey(), localArrayList);
/*     */     }
/*     */   }
/*     */ }


/* Location:              /Users/guanxiaoda/Documents/workspace/rt.jar!/java/security/UnresolvedPermissionCollection.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */