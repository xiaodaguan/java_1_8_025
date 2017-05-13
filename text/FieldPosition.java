/*     */ package java.text;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class FieldPosition
/*     */ {
/*  79 */   int field = 0;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*  85 */   int endIndex = 0;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*  91 */   int beginIndex = 0;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private Format.Field attribute;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public FieldPosition(int paramInt)
/*     */   {
/* 110 */     this.field = paramInt;
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
/*     */   public FieldPosition(Format.Field paramField)
/*     */   {
/* 123 */     this(paramField, -1);
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
/*     */   public FieldPosition(Format.Field paramField, int paramInt)
/*     */   {
/* 143 */     this.attribute = paramField;
/* 144 */     this.field = paramInt;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public Format.Field getFieldAttribute()
/*     */   {
/* 156 */     return this.attribute;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public int getField()
/*     */   {
/* 165 */     return this.field;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public int getBeginIndex()
/*     */   {
/* 174 */     return this.beginIndex;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public int getEndIndex()
/*     */   {
/* 184 */     return this.endIndex;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setBeginIndex(int paramInt)
/*     */   {
/* 194 */     this.beginIndex = paramInt;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setEndIndex(int paramInt)
/*     */   {
/* 204 */     this.endIndex = paramInt;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   Format.FieldDelegate getFieldDelegate()
/*     */   {
/* 214 */     return new Delegate(null);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public boolean equals(Object paramObject)
/*     */   {
/* 222 */     if (paramObject == null) return false;
/* 223 */     if (!(paramObject instanceof FieldPosition))
/* 224 */       return false;
/* 225 */     FieldPosition localFieldPosition = (FieldPosition)paramObject;
/* 226 */     if (this.attribute == null) {
/* 227 */       if (localFieldPosition.attribute != null) {
/* 228 */         return false;
/*     */       }
/*     */     }
/* 231 */     else if (!this.attribute.equals(localFieldPosition.attribute)) {
/* 232 */       return false;
/*     */     }
/* 234 */     return (this.beginIndex == localFieldPosition.beginIndex) && (this.endIndex == localFieldPosition.endIndex) && (this.field == localFieldPosition.field);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public int hashCode()
/*     */   {
/* 244 */     return this.field << 24 | this.beginIndex << 16 | this.endIndex;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public String toString()
/*     */   {
/* 252 */     return getClass().getName() + "[field=" + this.field + ",attribute=" + this.attribute + ",beginIndex=" + this.beginIndex + ",endIndex=" + this.endIndex + ']';
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private boolean matchesField(Format.Field paramField)
/*     */   {
/* 264 */     if (this.attribute != null) {
/* 265 */       return this.attribute.equals(paramField);
/*     */     }
/* 267 */     return false;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private boolean matchesField(Format.Field paramField, int paramInt)
/*     */   {
/* 276 */     if (this.attribute != null) {
/* 277 */       return this.attribute.equals(paramField);
/*     */     }
/* 279 */     return paramInt == this.field;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   private class Delegate
/*     */     implements Format.FieldDelegate
/*     */   {
/*     */     private boolean encounteredField;
/*     */     
/*     */ 
/*     */ 
/*     */     private Delegate() {}
/*     */     
/*     */ 
/*     */ 
/*     */     public void formatted(Format.Field paramField, Object paramObject, int paramInt1, int paramInt2, StringBuffer paramStringBuffer)
/*     */     {
/* 298 */       if ((!this.encounteredField) && (FieldPosition.this.matchesField(paramField))) {
/* 299 */         FieldPosition.this.setBeginIndex(paramInt1);
/* 300 */         FieldPosition.this.setEndIndex(paramInt2);
/* 301 */         this.encounteredField = (paramInt1 != paramInt2);
/*     */       }
/*     */     }
/*     */     
/*     */     public void formatted(int paramInt1, Format.Field paramField, Object paramObject, int paramInt2, int paramInt3, StringBuffer paramStringBuffer)
/*     */     {
/* 307 */       if ((!this.encounteredField) && (FieldPosition.this.matchesField(paramField, paramInt1))) {
/* 308 */         FieldPosition.this.setBeginIndex(paramInt2);
/* 309 */         FieldPosition.this.setEndIndex(paramInt3);
/* 310 */         this.encounteredField = (paramInt2 != paramInt3);
/*     */       }
/*     */     }
/*     */   }
/*     */ }


/* Location:              /Users/guanxiaoda/Documents/workspace/rt.jar!/java/text/FieldPosition.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */