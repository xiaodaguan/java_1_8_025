/*     */ package java.applet;
/*     */ 
/*     */ import java.awt.AWTPermission;
/*     */ import java.awt.Dimension;
/*     */ import java.awt.GraphicsEnvironment;
/*     */ import java.awt.HeadlessException;
/*     */ import java.awt.Image;
/*     */ import java.awt.Panel;
/*     */ import java.awt.Panel.AccessibleAWTPanel;
/*     */ import java.io.IOException;
/*     */ import java.io.ObjectInputStream;
/*     */ import java.net.MalformedURLException;
/*     */ import java.net.URL;
/*     */ import java.util.Locale;
/*     */ import javax.accessibility.AccessibleContext;
/*     */ import javax.accessibility.AccessibleRole;
/*     */ import javax.accessibility.AccessibleState;
/*     */ import javax.accessibility.AccessibleStateSet;
/*     */ import sun.applet.AppletAudioClip;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class Applet
/*     */   extends Panel
/*     */ {
/*     */   private transient AppletStub stub;
/*     */   private static final long serialVersionUID = -5836846270535785031L;
/*     */   
/*     */   public Applet()
/*     */     throws HeadlessException
/*     */   {
/*  66 */     if (GraphicsEnvironment.isHeadless()) {
/*  67 */       throw new HeadlessException();
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private void readObject(ObjectInputStream paramObjectInputStream)
/*     */     throws ClassNotFoundException, IOException, HeadlessException
/*     */   {
/*  98 */     if (GraphicsEnvironment.isHeadless()) {
/*  99 */       throw new HeadlessException();
/*     */     }
/* 101 */     paramObjectInputStream.defaultReadObject();
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
/*     */   public final void setStub(AppletStub paramAppletStub)
/*     */   {
/* 114 */     if (this.stub != null) {
/* 115 */       SecurityManager localSecurityManager = System.getSecurityManager();
/* 116 */       if (localSecurityManager != null) {
/* 117 */         localSecurityManager.checkPermission(new AWTPermission("setAppletStub"));
/*     */       }
/*     */     }
/* 120 */     this.stub = paramAppletStub;
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
/*     */   public boolean isActive()
/*     */   {
/* 134 */     if (this.stub != null) {
/* 135 */       return this.stub.isActive();
/*     */     }
/* 137 */     return false;
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
/*     */   public URL getDocumentBase()
/*     */   {
/* 158 */     return this.stub.getDocumentBase();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public URL getCodeBase()
/*     */   {
/* 169 */     return this.stub.getCodeBase();
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
/*     */   public String getParameter(String paramString)
/*     */   {
/* 191 */     return this.stub.getParameter(paramString);
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
/*     */   public AppletContext getAppletContext()
/*     */   {
/* 204 */     return this.stub.getAppletContext();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void resize(int paramInt1, int paramInt2)
/*     */   {
/* 215 */     Dimension localDimension = size();
/* 216 */     if ((localDimension.width != paramInt1) || (localDimension.height != paramInt2)) {
/* 217 */       super.resize(paramInt1, paramInt2);
/* 218 */       if (this.stub != null) {
/* 219 */         this.stub.appletResize(paramInt1, paramInt2);
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void resize(Dimension paramDimension)
/*     */   {
/* 231 */     resize(paramDimension.width, paramDimension.height);
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
/*     */   public boolean isValidateRoot()
/*     */   {
/* 246 */     return true;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void showStatus(String paramString)
/*     */   {
/* 258 */     getAppletContext().showStatus(paramString);
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
/*     */   public Image getImage(URL paramURL)
/*     */   {
/* 276 */     return getAppletContext().getImage(paramURL);
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
/*     */   public Image getImage(URL paramURL, String paramString)
/*     */   {
/*     */     try
/*     */     {
/* 298 */       return getImage(new URL(paramURL, paramString));
/*     */     } catch (MalformedURLException localMalformedURLException) {}
/* 300 */     return null;
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
/*     */   public static final AudioClip newAudioClip(URL paramURL)
/*     */   {
/* 313 */     return new AppletAudioClip(paramURL);
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
/*     */   public AudioClip getAudioClip(URL paramURL)
/*     */   {
/* 329 */     return getAppletContext().getAudioClip(paramURL);
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
/*     */   public AudioClip getAudioClip(URL paramURL, String paramString)
/*     */   {
/*     */     try
/*     */     {
/* 349 */       return getAudioClip(new URL(paramURL, paramString));
/*     */     } catch (MalformedURLException localMalformedURLException) {}
/* 351 */     return null;
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
/*     */   public String getAppletInfo()
/*     */   {
/* 367 */     return null;
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
/*     */   public Locale getLocale()
/*     */   {
/* 380 */     Locale localLocale = super.getLocale();
/* 381 */     if (localLocale == null) {
/* 382 */       return Locale.getDefault();
/*     */     }
/* 384 */     return localLocale;
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
/*     */   public String[][] getParameterInfo()
/*     */   {
/* 409 */     return (String[][])null;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void play(URL paramURL)
/*     */   {
/* 419 */     AudioClip localAudioClip = getAudioClip(paramURL);
/* 420 */     if (localAudioClip != null) {
/* 421 */       localAudioClip.play();
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
/*     */   public void play(URL paramURL, String paramString)
/*     */   {
/* 435 */     AudioClip localAudioClip = getAudioClip(paramURL, paramString);
/* 436 */     if (localAudioClip != null) {
/* 437 */       localAudioClip.play();
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 542 */   AccessibleContext accessibleContext = null;
/*     */   
/*     */ 
/*     */   public void init() {}
/*     */   
/*     */   public void start() {}
/*     */   
/*     */   public void stop() {}
/*     */   
/*     */   public void destroy() {}
/*     */   
/*     */   public AccessibleContext getAccessibleContext()
/*     */   {
/* 555 */     if (this.accessibleContext == null) {
/* 556 */       this.accessibleContext = new AccessibleApplet();
/*     */     }
/* 558 */     return this.accessibleContext;
/*     */   }
/*     */   
/*     */   protected class AccessibleApplet extends Panel.AccessibleAWTPanel
/*     */   {
/*     */     private static final long serialVersionUID = 8127374778187708896L;
/*     */     
/*     */     protected AccessibleApplet()
/*     */     {
/* 567 */       super();
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     public AccessibleRole getAccessibleRole()
/*     */     {
/* 578 */       return AccessibleRole.FRAME;
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     public AccessibleStateSet getAccessibleStateSet()
/*     */     {
/* 589 */       AccessibleStateSet localAccessibleStateSet = super.getAccessibleStateSet();
/* 590 */       localAccessibleStateSet.add(AccessibleState.ACTIVE);
/* 591 */       return localAccessibleStateSet;
/*     */     }
/*     */   }
/*     */ }


/* Location:              /Users/guanxiaoda/Documents/workspace/rt.jar!/java/applet/Applet.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */