/*     */ package java.util;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.Reader;
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
/*     */ 
/*     */ 
/*     */ public class PropertyResourceBundle
/*     */   extends ResourceBundle
/*     */ {
/*     */   private Map<String, Object> lookup;
/*     */   
/*     */   public PropertyResourceBundle(InputStream paramInputStream)
/*     */     throws IOException
/*     */   {
/* 137 */     Properties localProperties = new Properties();
/* 138 */     localProperties.load(paramInputStream);
/* 139 */     this.lookup = new HashMap(localProperties);
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
/*     */   public PropertyResourceBundle(Reader paramReader)
/*     */     throws IOException
/*     */   {
/* 158 */     Properties localProperties = new Properties();
/* 159 */     localProperties.load(paramReader);
/* 160 */     this.lookup = new HashMap(localProperties);
/*     */   }
/*     */   
/*     */   public Object handleGetObject(String paramString)
/*     */   {
/* 165 */     if (paramString == null) {
/* 166 */       throw new NullPointerException();
/*     */     }
/* 168 */     return this.lookup.get(paramString);
/*     */   }
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
/* 180 */     ResourceBundle localResourceBundle = this.parent;
/*     */     
/* 182 */     return new ResourceBundleEnumeration(this.lookup.keySet(), localResourceBundle != null ? localResourceBundle.getKeys() : null);
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
/* 195 */     return this.lookup.keySet();
/*     */   }
/*     */ }


/* Location:              /Users/guanxiaoda/Documents/workspace/rt.jar!/java/util/PropertyResourceBundle.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */