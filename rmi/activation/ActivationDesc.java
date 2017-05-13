/*     */ package java.rmi.activation;
/*     */ 
/*     */ import java.io.Serializable;
/*     */ import java.rmi.MarshalledObject;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class ActivationDesc
/*     */   implements Serializable
/*     */ {
/*     */   private ActivationGroupID groupID;
/*     */   private String className;
/*     */   private String location;
/*     */   private MarshalledObject<?> data;
/*     */   private boolean restart;
/*     */   private static final long serialVersionUID = 7455834104417690957L;
/*     */   
/*     */   public ActivationDesc(String paramString1, String paramString2, MarshalledObject<?> paramMarshalledObject)
/*     */     throws ActivationException
/*     */   {
/* 117 */     this(ActivationGroup.internalCurrentGroupID(), paramString1, paramString2, paramMarshalledObject, false);
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
/*     */   public ActivationDesc(String paramString1, String paramString2, MarshalledObject<?> paramMarshalledObject, boolean paramBoolean)
/*     */     throws ActivationException
/*     */   {
/* 157 */     this(ActivationGroup.internalCurrentGroupID(), paramString1, paramString2, paramMarshalledObject, paramBoolean);
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
/*     */   public ActivationDesc(ActivationGroupID paramActivationGroupID, String paramString1, String paramString2, MarshalledObject<?> paramMarshalledObject)
/*     */   {
/* 192 */     this(paramActivationGroupID, paramString1, paramString2, paramMarshalledObject, false);
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
/*     */   public ActivationDesc(ActivationGroupID paramActivationGroupID, String paramString1, String paramString2, MarshalledObject<?> paramMarshalledObject, boolean paramBoolean)
/*     */   {
/* 227 */     if (paramActivationGroupID == null)
/* 228 */       throw new IllegalArgumentException("groupID can't be null");
/* 229 */     this.groupID = paramActivationGroupID;
/* 230 */     this.className = paramString1;
/* 231 */     this.location = paramString2;
/* 232 */     this.data = paramMarshalledObject;
/* 233 */     this.restart = paramBoolean;
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
/*     */   public ActivationGroupID getGroupID()
/*     */   {
/* 246 */     return this.groupID;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public String getClassName()
/*     */   {
/* 256 */     return this.className;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public String getLocation()
/*     */   {
/* 266 */     return this.location;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public MarshalledObject<?> getData()
/*     */   {
/* 276 */     return this.data;
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
/*     */   public boolean getRestartMode()
/*     */   {
/* 294 */     return this.restart;
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
/* 307 */     if ((paramObject instanceof ActivationDesc)) {
/* 308 */       ActivationDesc localActivationDesc = (ActivationDesc)paramObject;
/*     */       
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 317 */       return (this.groupID == null ? localActivationDesc.groupID == null : this.groupID.equals(localActivationDesc.groupID)) && (this.className == null ? localActivationDesc.className == null : this.className.equals(localActivationDesc.className)) && (this.location == null ? localActivationDesc.location == null : this.location.equals(localActivationDesc.location)) && (this.data == null ? localActivationDesc.data == null : this.data.equals(localActivationDesc.data)) && (this.restart == localActivationDesc.restart);
/*     */     }
/*     */     
/*     */ 
/* 321 */     return false;
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
/*     */   public int hashCode()
/*     */   {
/* 342 */     return (this.location == null ? 0 : this.location.hashCode() << 24) ^ (this.groupID == null ? 0 : this.groupID.hashCode() << 16) ^ (this.className == null ? 0 : this.className.hashCode() << 9) ^ (this.data == null ? 0 : this.data.hashCode() << 1) ^ (this.restart ? 1 : 0);
/*     */   }
/*     */ }


/* Location:              /Users/guanxiaoda/Documents/workspace/rt.jar!/java/rmi/activation/ActivationDesc.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */