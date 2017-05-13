/*     */ package java.net;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.security.cert.Certificate;
/*     */ import java.util.jar.Attributes;
/*     */ import java.util.jar.JarEntry;
/*     */ import java.util.jar.JarFile;
/*     */ import java.util.jar.Manifest;
/*     */ import sun.net.www.ParseUtil;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class JarURLConnection
/*     */   extends URLConnection
/*     */ {
/*     */   private URL jarFileURL;
/*     */   private String entryName;
/*     */   protected URLConnection jarFileURLConnection;
/*     */   
/*     */   protected JarURLConnection(URL paramURL)
/*     */     throws MalformedURLException
/*     */   {
/* 157 */     super(paramURL);
/* 158 */     parseSpecs(paramURL);
/*     */   }
/*     */   
/*     */ 
/*     */   private void parseSpecs(URL paramURL)
/*     */     throws MalformedURLException
/*     */   {
/* 165 */     String str = paramURL.getFile();
/*     */     
/* 167 */     int i = str.indexOf("!/");
/*     */     
/*     */ 
/*     */ 
/* 171 */     if (i == -1) {
/* 172 */       throw new MalformedURLException("no !/ found in url spec:" + str);
/*     */     }
/*     */     
/* 175 */     this.jarFileURL = new URL(str.substring(0, i++));
/* 176 */     this.entryName = null;
/*     */     
/*     */ 
/* 179 */     i++; if (i != str.length()) {
/* 180 */       this.entryName = str.substring(i, str.length());
/* 181 */       this.entryName = ParseUtil.decode(this.entryName);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public URL getJarFileURL()
/*     */   {
/* 191 */     return this.jarFileURL;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public String getEntryName()
/*     */   {
/* 202 */     return this.entryName;
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
/*     */   public abstract JarFile getJarFile()
/*     */     throws IOException;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public Manifest getManifest()
/*     */     throws IOException
/*     */   {
/* 231 */     return getJarFile().getManifest();
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
/*     */   public JarEntry getJarEntry()
/*     */     throws IOException
/*     */   {
/* 249 */     return getJarFile().getJarEntry(this.entryName);
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
/*     */   public Attributes getAttributes()
/*     */     throws IOException
/*     */   {
/* 265 */     JarEntry localJarEntry = getJarEntry();
/* 266 */     return localJarEntry != null ? localJarEntry.getAttributes() : null;
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
/*     */   public Attributes getMainAttributes()
/*     */     throws IOException
/*     */   {
/* 283 */     Manifest localManifest = getManifest();
/* 284 */     return localManifest != null ? localManifest.getMainAttributes() : null;
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
/*     */   public Certificate[] getCertificates()
/*     */     throws IOException
/*     */   {
/* 306 */     JarEntry localJarEntry = getJarEntry();
/* 307 */     return localJarEntry != null ? localJarEntry.getCertificates() : null;
/*     */   }
/*     */ }


/* Location:              /Users/guanxiaoda/Documents/workspace/rt.jar!/java/net/JarURLConnection.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */