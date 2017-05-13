/*     */ package java.util;
/*     */ 
/*     */ import sun.util.ResourceBundleEnumeration;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class ListResourceBundle
/*     */   extends ResourceBundle
/*     */ {
/*     */   public final Object handleGetObject(String paramString)
/*     */   {
/* 129 */     if (this.lookup == null) {
/* 130 */       loadLookup();
/*     */     }
/* 132 */     if (paramString == null) {
/* 133 */       throw new NullPointerException();
/*     */     }
/* 135 */     return this.lookup.get(paramString);
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
/*     */   public Enumeration<String> getKeys()
/*     */   {
/* 148 */     if (this.lookup == null) {
/* 149 */       loadLookup();
/*     */     }
/*     */     
/* 152 */     ResourceBundle localResourceBundle = this.parent;
/*     */     
/* 154 */     return new ResourceBundleEnumeration(this.lookup.keySet(), localResourceBundle != null ? localResourceBundle.getKeys() : null);
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
/*     */   protected Set<String> handleKeySet()
/*     */   {
/* 167 */     if (this.lookup == null) {
/* 168 */       loadLookup();
/*     */     }
/* 170 */     return this.lookup.keySet();
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
/*     */   protected abstract Object[][] getContents();
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private synchronized void loadLookup()
/*     */   {
/* 192 */     if (this.lookup != null) {
/* 193 */       return;
/*     */     }
/* 195 */     Object[][] arrayOfObject = getContents();
/* 196 */     HashMap localHashMap = new HashMap(arrayOfObject.length);
/* 197 */     for (int i = 0; i < arrayOfObject.length; i++)
/*     */     {
/* 199 */       String str = (String)arrayOfObject[i][0];
/* 200 */       Object localObject = arrayOfObject[i][1];
/* 201 */       if ((str == null) || (localObject == null)) {
/* 202 */         throw new NullPointerException();
/*     */       }
/* 204 */       localHashMap.put(str, localObject);
/*     */     }
/* 206 */     this.lookup = localHashMap;
/*     */   }
/*     */   
/* 209 */   private Map<String, Object> lookup = null;
/*     */ }


/* Location:              /Users/guanxiaoda/Documents/workspace/rt.jar!/java/util/ListResourceBundle.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */