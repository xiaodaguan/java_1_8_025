/*     */ package java.nio.file.attribute;
/*     */ 
/*     */ import java.util.Collections;
/*     */ import java.util.EnumSet;
/*     */ import java.util.HashSet;
/*     */ import java.util.Iterator;
/*     */ import java.util.Set;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class AclEntry
/*     */ {
/*     */   private final AclEntryType type;
/*     */   private final UserPrincipal who;
/*     */   private final Set<AclEntryPermission> perms;
/*     */   private final Set<AclEntryFlag> flags;
/*     */   private volatile int hash;
/*     */   
/*     */   private AclEntry(AclEntryType paramAclEntryType, UserPrincipal paramUserPrincipal, Set<AclEntryPermission> paramSet, Set<AclEntryFlag> paramSet1)
/*     */   {
/*  80 */     this.type = paramAclEntryType;
/*  81 */     this.who = paramUserPrincipal;
/*  82 */     this.perms = paramSet;
/*  83 */     this.flags = paramSet1;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public static final class Builder
/*     */   {
/*     */     private AclEntryType type;
/*     */     
/*     */ 
/*     */ 
/*     */     private UserPrincipal who;
/*     */     
/*     */ 
/*     */ 
/*     */     private Set<AclEntryPermission> perms;
/*     */     
/*     */ 
/*     */ 
/*     */     private Set<AclEntryFlag> flags;
/*     */     
/*     */ 
/*     */ 
/*     */     private Builder(AclEntryType paramAclEntryType, UserPrincipal paramUserPrincipal, Set<AclEntryPermission> paramSet, Set<AclEntryFlag> paramSet1)
/*     */     {
/* 109 */       assert ((paramSet != null) && (paramSet1 != null));
/* 110 */       this.type = paramAclEntryType;
/* 111 */       this.who = paramUserPrincipal;
/* 112 */       this.perms = paramSet;
/* 113 */       this.flags = paramSet1;
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
/*     */     public AclEntry build()
/*     */     {
/* 127 */       if (this.type == null)
/* 128 */         throw new IllegalStateException("Missing type component");
/* 129 */       if (this.who == null)
/* 130 */         throw new IllegalStateException("Missing who component");
/* 131 */       return new AclEntry(this.type, this.who, this.perms, this.flags, null);
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     public Builder setType(AclEntryType paramAclEntryType)
/*     */     {
/* 141 */       if (paramAclEntryType == null)
/* 142 */         throw new NullPointerException();
/* 143 */       this.type = paramAclEntryType;
/* 144 */       return this;
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     public Builder setPrincipal(UserPrincipal paramUserPrincipal)
/*     */     {
/* 154 */       if (paramUserPrincipal == null)
/* 155 */         throw new NullPointerException();
/* 156 */       this.who = paramUserPrincipal;
/* 157 */       return this;
/*     */     }
/*     */     
/*     */     private static void checkSet(Set<?> paramSet, Class<?> paramClass)
/*     */     {
/* 162 */       for (Object localObject : paramSet) {
/* 163 */         if (localObject == null)
/* 164 */           throw new NullPointerException();
/* 165 */         paramClass.cast(localObject);
/*     */       }
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
/*     */     public Builder setPermissions(Set<AclEntryPermission> paramSet)
/*     */     {
/* 181 */       if (paramSet.isEmpty())
/*     */       {
/* 183 */         paramSet = Collections.emptySet();
/*     */       }
/*     */       else {
/* 186 */         paramSet = EnumSet.copyOf(paramSet);
/* 187 */         checkSet(paramSet, AclEntryPermission.class);
/*     */       }
/*     */       
/* 190 */       this.perms = paramSet;
/* 191 */       return this;
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     public Builder setPermissions(AclEntryPermission... paramVarArgs)
/*     */     {
/* 203 */       EnumSet localEnumSet = EnumSet.noneOf(AclEntryPermission.class);
/*     */       
/* 205 */       for (AclEntryPermission localAclEntryPermission : paramVarArgs) {
/* 206 */         if (localAclEntryPermission == null)
/* 207 */           throw new NullPointerException();
/* 208 */         localEnumSet.add(localAclEntryPermission);
/*     */       }
/* 210 */       this.perms = localEnumSet;
/* 211 */       return this;
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
/*     */     public Builder setFlags(Set<AclEntryFlag> paramSet)
/*     */     {
/* 226 */       if (paramSet.isEmpty())
/*     */       {
/* 228 */         paramSet = Collections.emptySet();
/*     */       }
/*     */       else {
/* 231 */         paramSet = EnumSet.copyOf(paramSet);
/* 232 */         checkSet(paramSet, AclEntryFlag.class);
/*     */       }
/*     */       
/* 235 */       this.flags = paramSet;
/* 236 */       return this;
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     public Builder setFlags(AclEntryFlag... paramVarArgs)
/*     */     {
/* 248 */       EnumSet localEnumSet = EnumSet.noneOf(AclEntryFlag.class);
/*     */       
/* 250 */       for (AclEntryFlag localAclEntryFlag : paramVarArgs) {
/* 251 */         if (localAclEntryFlag == null)
/* 252 */           throw new NullPointerException();
/* 253 */         localEnumSet.add(localAclEntryFlag);
/*     */       }
/* 255 */       this.flags = localEnumSet;
/* 256 */       return this;
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static Builder newBuilder()
/*     */   {
/* 268 */     Set localSet1 = Collections.emptySet();
/* 269 */     Set localSet2 = Collections.emptySet();
/* 270 */     return new Builder(null, null, localSet1, localSet2, null);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static Builder newBuilder(AclEntry paramAclEntry)
/*     */   {
/* 280 */     return new Builder(paramAclEntry.type, paramAclEntry.who, paramAclEntry.perms, paramAclEntry.flags, null);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public AclEntryType type()
/*     */   {
/* 289 */     return this.type;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public UserPrincipal principal()
/*     */   {
/* 298 */     return this.who;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public Set<AclEntryPermission> permissions()
/*     */   {
/* 309 */     return new HashSet(this.perms);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public Set<AclEntryFlag> flags()
/*     */   {
/* 320 */     return new HashSet(this.flags);
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
/*     */   public boolean equals(Object paramObject)
/*     */   {
/* 343 */     if (paramObject == this)
/* 344 */       return true;
/* 345 */     if ((paramObject == null) || (!(paramObject instanceof AclEntry)))
/* 346 */       return false;
/* 347 */     AclEntry localAclEntry = (AclEntry)paramObject;
/* 348 */     if (this.type != localAclEntry.type)
/* 349 */       return false;
/* 350 */     if (!this.who.equals(localAclEntry.who))
/* 351 */       return false;
/* 352 */     if (!this.perms.equals(localAclEntry.perms))
/* 353 */       return false;
/* 354 */     if (!this.flags.equals(localAclEntry.flags))
/* 355 */       return false;
/* 356 */     return true;
/*     */   }
/*     */   
/*     */   private static int hash(int paramInt, Object paramObject) {
/* 360 */     return paramInt * 127 + paramObject.hashCode();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public int hashCode()
/*     */   {
/* 372 */     if (this.hash != 0)
/* 373 */       return this.hash;
/* 374 */     int i = this.type.hashCode();
/* 375 */     i = hash(i, this.who);
/* 376 */     i = hash(i, this.perms);
/* 377 */     i = hash(i, this.flags);
/* 378 */     this.hash = i;
/* 379 */     return this.hash;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public String toString()
/*     */   {
/* 389 */     StringBuilder localStringBuilder = new StringBuilder();
/*     */     
/*     */ 
/* 392 */     localStringBuilder.append(this.who.getName());
/* 393 */     localStringBuilder.append(':');
/*     */     
/*     */ 
/* 396 */     for (Iterator localIterator = this.perms.iterator(); localIterator.hasNext();) { localObject = (AclEntryPermission)localIterator.next();
/* 397 */       localStringBuilder.append(((AclEntryPermission)localObject).name());
/* 398 */       localStringBuilder.append('/'); }
/*     */     Object localObject;
/* 400 */     localStringBuilder.setLength(localStringBuilder.length() - 1);
/* 401 */     localStringBuilder.append(':');
/*     */     
/*     */ 
/* 404 */     if (!this.flags.isEmpty()) {
/* 405 */       for (localIterator = this.flags.iterator(); localIterator.hasNext();) { localObject = (AclEntryFlag)localIterator.next();
/* 406 */         localStringBuilder.append(((AclEntryFlag)localObject).name());
/* 407 */         localStringBuilder.append('/');
/*     */       }
/* 409 */       localStringBuilder.setLength(localStringBuilder.length() - 1);
/* 410 */       localStringBuilder.append(':');
/*     */     }
/*     */     
/*     */ 
/* 414 */     localStringBuilder.append(this.type.name());
/* 415 */     return localStringBuilder.toString();
/*     */   }
/*     */ }


/* Location:              /Users/guanxiaoda/Documents/workspace/rt.jar!/java/nio/file/attribute/AclEntry.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */