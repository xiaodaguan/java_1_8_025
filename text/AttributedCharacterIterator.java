/*     */ package java.text;
/*     */ 
/*     */ import java.io.InvalidObjectException;
/*     */ import java.io.Serializable;
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract interface AttributedCharacterIterator
/*     */   extends CharacterIterator
/*     */ {
/*     */   public abstract int getRunStart();
/*     */   
/*     */   public abstract int getRunStart(Attribute paramAttribute);
/*     */   
/*     */   public abstract int getRunStart(Set<? extends Attribute> paramSet);
/*     */   
/*     */   public abstract int getRunLimit();
/*     */   
/*     */   public abstract int getRunLimit(Attribute paramAttribute);
/*     */   
/*     */   public abstract int getRunLimit(Set<? extends Attribute> paramSet);
/*     */   
/*     */   public abstract Map<Attribute, Object> getAttributes();
/*     */   
/*     */   public abstract Object getAttribute(Attribute paramAttribute);
/*     */   
/*     */   public abstract Set<Attribute> getAllAttributeKeys();
/*     */   
/*     */   public static class Attribute
/*     */     implements Serializable
/*     */   {
/*     */     private String name;
/* 100 */     private static final Map<String, Attribute> instanceMap = new HashMap(7);
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     protected Attribute(String paramString)
/*     */     {
/* 108 */       this.name = paramString;
/* 109 */       if (getClass() == Attribute.class) {
/* 110 */         instanceMap.put(paramString, this);
/*     */       }
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     public final boolean equals(Object paramObject)
/*     */     {
/* 120 */       return super.equals(paramObject);
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */     public final int hashCode()
/*     */     {
/* 128 */       return super.hashCode();
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     public String toString()
/*     */     {
/* 137 */       return getClass().getName() + "(" + this.name + ")";
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     protected String getName()
/*     */     {
/* 146 */       return this.name;
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     protected Object readResolve()
/*     */       throws InvalidObjectException
/*     */     {
/* 157 */       if (getClass() != Attribute.class) {
/* 158 */         throw new InvalidObjectException("subclass didn't correctly implement readResolve");
/*     */       }
/*     */       
/* 161 */       Attribute localAttribute = (Attribute)instanceMap.get(getName());
/* 162 */       if (localAttribute != null) {
/* 163 */         return localAttribute;
/*     */       }
/* 165 */       throw new InvalidObjectException("unknown attribute name");
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 174 */     public static final Attribute LANGUAGE = new Attribute("language");
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 186 */     public static final Attribute READING = new Attribute("reading");
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 194 */     public static final Attribute INPUT_METHOD_SEGMENT = new Attribute("input_method_segment");
/*     */     private static final long serialVersionUID = -9142742483513960612L;
/*     */   }
/*     */ }


/* Location:              /Users/guanxiaoda/Documents/workspace/rt.jar!/java/text/AttributedCharacterIterator.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */