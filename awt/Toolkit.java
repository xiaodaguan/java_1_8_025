/*      */ package java.awt;
/*      */ 
/*      */ import java.awt.datatransfer.Clipboard;
/*      */ import java.awt.dnd.DragGestureEvent;
/*      */ import java.awt.dnd.DragGestureListener;
/*      */ import java.awt.dnd.DragGestureRecognizer;
/*      */ import java.awt.dnd.DragSource;
/*      */ import java.awt.dnd.InvalidDnDOperationException;
/*      */ import java.awt.dnd.peer.DragSourceContextPeer;
/*      */ import java.awt.event.AWTEventListener;
/*      */ import java.awt.event.AWTEventListenerProxy;
/*      */ import java.awt.font.TextAttribute;
/*      */ import java.awt.im.InputMethodHighlight;
/*      */ import java.awt.image.ColorModel;
/*      */ import java.awt.image.ImageObserver;
/*      */ import java.awt.image.ImageProducer;
/*      */ import java.awt.peer.ButtonPeer;
/*      */ import java.awt.peer.CanvasPeer;
/*      */ import java.awt.peer.CheckboxMenuItemPeer;
/*      */ import java.awt.peer.CheckboxPeer;
/*      */ import java.awt.peer.ChoicePeer;
/*      */ import java.awt.peer.DesktopPeer;
/*      */ import java.awt.peer.DialogPeer;
/*      */ import java.awt.peer.FileDialogPeer;
/*      */ import java.awt.peer.FontPeer;
/*      */ import java.awt.peer.FramePeer;
/*      */ import java.awt.peer.LabelPeer;
/*      */ import java.awt.peer.LightweightPeer;
/*      */ import java.awt.peer.ListPeer;
/*      */ import java.awt.peer.MenuBarPeer;
/*      */ import java.awt.peer.MenuItemPeer;
/*      */ import java.awt.peer.MenuPeer;
/*      */ import java.awt.peer.MouseInfoPeer;
/*      */ import java.awt.peer.PanelPeer;
/*      */ import java.awt.peer.PopupMenuPeer;
/*      */ import java.awt.peer.ScrollPanePeer;
/*      */ import java.awt.peer.ScrollbarPeer;
/*      */ import java.awt.peer.TextAreaPeer;
/*      */ import java.awt.peer.TextFieldPeer;
/*      */ import java.awt.peer.WindowPeer;
/*      */ import java.beans.PropertyChangeEvent;
/*      */ import java.beans.PropertyChangeListener;
/*      */ import java.beans.PropertyChangeSupport;
/*      */ import java.io.File;
/*      */ import java.io.FileInputStream;
/*      */ import java.io.InputStream;
/*      */ import java.net.URL;
/*      */ import java.security.AccessController;
/*      */ import java.security.PrivilegedAction;
/*      */ import java.util.ArrayList;
/*      */ import java.util.EventListener;
/*      */ import java.util.HashMap;
/*      */ import java.util.Map;
/*      */ import java.util.MissingResourceException;
/*      */ import java.util.Properties;
/*      */ import java.util.ResourceBundle;
/*      */ import java.util.StringTokenizer;
/*      */ import java.util.WeakHashMap;
/*      */ import sun.awt.AWTAccessor;
/*      */ import sun.awt.AWTAccessor.ToolkitAccessor;
/*      */ import sun.awt.AppContext;
/*      */ import sun.awt.HeadlessToolkit;
/*      */ import sun.awt.NullComponentPeer;
/*      */ import sun.awt.PeerEvent;
/*      */ import sun.awt.SunToolkit;
/*      */ import sun.awt.UngrabEvent;
/*      */ import sun.security.util.SecurityConstants.AWT;
/*      */ import sun.util.CoreResourceBundleControl;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public abstract class Toolkit
/*      */ {
/*      */   private static LightweightPeer lightweightMarker;
/*      */   private static Toolkit toolkit;
/*      */   private static String atNames;
/*      */   private static ResourceBundle resources;
/*      */   private static ResourceBundle platformResources;
/*      */   private static boolean loaded;
/*      */   protected final Map<String, Object> desktopProperties;
/*      */   protected final PropertyChangeSupport desktopPropsSupport;
/*      */   private static final int LONG_BITS = 64;
/*      */   private int[] calls;
/*      */   private static volatile long enabledOnToolkitMask;
/*      */   private AWTEventListener eventListener;
/*      */   private WeakHashMap<AWTEventListener, SelectiveAWTEventListener> listener2SelectiveListener;
/*      */   
/*      */   protected MouseInfoPeer getMouseInfoPeer()
/*      */   {
/*  415 */     throw new UnsupportedOperationException("Not implemented");
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   protected LightweightPeer createComponent(Component paramComponent)
/*      */   {
/*  428 */     if (lightweightMarker == null) {
/*  429 */       lightweightMarker = new NullComponentPeer();
/*      */     }
/*  431 */     return lightweightMarker;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public void setDynamicLayout(boolean paramBoolean)
/*      */     throws HeadlessException
/*      */   {
/*      */     
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  500 */     if (this != getDefaultToolkit()) {
/*  501 */       getDefaultToolkit().setDynamicLayout(paramBoolean);
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   protected boolean isDynamicLayoutSet()
/*      */     throws HeadlessException
/*      */   {
/*      */     
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  528 */     if (this != getDefaultToolkit()) {
/*  529 */       return getDefaultToolkit().isDynamicLayoutSet();
/*      */     }
/*  531 */     return false;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public boolean isDynamicLayoutActive()
/*      */     throws HeadlessException
/*      */   {
/*      */     
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  565 */     if (this != getDefaultToolkit()) {
/*  566 */       return getDefaultToolkit().isDynamicLayoutActive();
/*      */     }
/*  568 */     return false;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public Insets getScreenInsets(GraphicsConfiguration paramGraphicsConfiguration)
/*      */     throws HeadlessException
/*      */   {
/*      */     
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  609 */     if (this != getDefaultToolkit()) {
/*  610 */       return getDefaultToolkit().getScreenInsets(paramGraphicsConfiguration);
/*      */     }
/*  612 */     return new Insets(0, 0, 0, 0);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private static void initAssistiveTechnologies()
/*      */   {
/*  703 */     String str = File.separator;
/*  704 */     final Properties localProperties = new Properties();
/*      */     
/*      */ 
/*  707 */     atNames = (String)AccessController.doPrivileged(new PrivilegedAction()
/*      */     {
/*      */ 
/*      */       public String run()
/*      */       {
/*      */         try
/*      */         {
/*  714 */           File localFile1 = new File(System.getProperty("user.home") + this.val$sep + ".accessibility.properties");
/*      */           
/*  716 */           localObject = new FileInputStream(localFile1);
/*      */           
/*      */ 
/*      */ 
/*  720 */           localProperties.load((InputStream)localObject);
/*  721 */           ((FileInputStream)localObject).close();
/*      */         }
/*      */         catch (Exception localException1) {}
/*      */         
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  729 */         if (localProperties.size() == 0) {
/*      */           try
/*      */           {
/*  732 */             File localFile2 = new File(System.getProperty("java.home") + this.val$sep + "lib" + this.val$sep + "accessibility.properties");
/*      */             
/*  734 */             localObject = new FileInputStream(localFile2);
/*      */             
/*      */ 
/*      */ 
/*  738 */             localProperties.load((InputStream)localObject);
/*  739 */             ((FileInputStream)localObject).close();
/*      */           }
/*      */           catch (Exception localException2) {}
/*      */         }
/*      */         
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  748 */         String str = System.getProperty("javax.accessibility.screen_magnifier_present");
/*  749 */         if (str == null) {
/*  750 */           str = localProperties.getProperty("screen_magnifier_present", null);
/*  751 */           if (str != null) {
/*  752 */             System.setProperty("javax.accessibility.screen_magnifier_present", str);
/*      */           }
/*      */         }
/*      */         
/*      */ 
/*      */ 
/*      */ 
/*  759 */         Object localObject = System.getProperty("javax.accessibility.assistive_technologies");
/*  760 */         if (localObject == null) {
/*  761 */           localObject = localProperties.getProperty("assistive_technologies", null);
/*  762 */           if (localObject != null) {
/*  763 */             System.setProperty("javax.accessibility.assistive_technologies", (String)localObject);
/*      */           }
/*      */         }
/*  766 */         return (String)localObject;
/*      */       }
/*      */     });
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private static void loadAssistiveTechnologies()
/*      */   {
/*  792 */     if (atNames != null) {
/*  793 */       ClassLoader localClassLoader = ClassLoader.getSystemClassLoader();
/*  794 */       StringTokenizer localStringTokenizer = new StringTokenizer(atNames, " ,");
/*      */       
/*  796 */       while (localStringTokenizer.hasMoreTokens()) {
/*  797 */         String str = localStringTokenizer.nextToken();
/*      */         try {
/*      */           Class localClass;
/*  800 */           if (localClassLoader != null) {
/*  801 */             localClass = localClassLoader.loadClass(str);
/*      */           } else {
/*  803 */             localClass = Class.forName(str);
/*      */           }
/*  805 */           localClass.newInstance();
/*      */         } catch (ClassNotFoundException localClassNotFoundException) {
/*  807 */           throw new AWTError("Assistive Technology not found: " + str);
/*      */         }
/*      */         catch (InstantiationException localInstantiationException) {
/*  810 */           throw new AWTError("Could not instantiate Assistive Technology: " + str);
/*      */         }
/*      */         catch (IllegalAccessException localIllegalAccessException) {
/*  813 */           throw new AWTError("Could not access Assistive Technology: " + str);
/*      */         }
/*      */         catch (Exception localException) {
/*  816 */           throw new AWTError("Error trying to install Assistive Technology: " + str + " " + localException);
/*      */         }
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static synchronized Toolkit getDefaultToolkit()
/*      */   {
/*  853 */     if (toolkit == null) {
/*  854 */       AccessController.doPrivileged(new PrivilegedAction()
/*      */       {
/*      */         public Void run() {
/*  857 */           Class localClass = null;
/*  858 */           String str = System.getProperty("awt.toolkit");
/*      */           try {
/*  860 */             localClass = Class.forName(str);
/*      */           } catch (ClassNotFoundException localClassNotFoundException1) {
/*  862 */             ClassLoader localClassLoader = ClassLoader.getSystemClassLoader();
/*  863 */             if (localClassLoader != null) {
/*      */               try {
/*  865 */                 localClass = localClassLoader.loadClass(str);
/*      */               } catch (ClassNotFoundException localClassNotFoundException2) {
/*  867 */                 throw new AWTError("Toolkit not found: " + str);
/*      */               }
/*      */             }
/*      */           }
/*      */           try {
/*  872 */             if (localClass != null) {
/*  873 */               Toolkit.access$002((Toolkit)localClass.newInstance());
/*  874 */               if (GraphicsEnvironment.isHeadless()) {
/*  875 */                 Toolkit.access$002(new HeadlessToolkit(Toolkit.toolkit));
/*      */               }
/*      */             }
/*      */           } catch (InstantiationException localInstantiationException) {
/*  879 */             throw new AWTError("Could not instantiate Toolkit: " + str);
/*      */           } catch (IllegalAccessException localIllegalAccessException) {
/*  881 */             throw new AWTError("Could not access Toolkit: " + str);
/*      */           }
/*  883 */           return null;
/*      */         }
/*  885 */       });
/*  886 */       loadAssistiveTechnologies();
/*      */     }
/*  888 */     return toolkit;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public Image createImage(byte[] paramArrayOfByte)
/*      */   {
/* 1111 */     return createImage(paramArrayOfByte, 0, paramArrayOfByte.length);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public PrintJob getPrintJob(Frame paramFrame, String paramString, JobAttributes paramJobAttributes, PageAttributes paramPageAttributes)
/*      */   {
/* 1221 */     if (this != getDefaultToolkit()) {
/* 1222 */       return getDefaultToolkit().getPrintJob(paramFrame, paramString, paramJobAttributes, paramPageAttributes);
/*      */     }
/*      */     
/*      */ 
/* 1226 */     return getPrintJob(paramFrame, paramString, null);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public Clipboard getSystemSelection()
/*      */     throws HeadlessException
/*      */   {
/*      */     
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1330 */     if (this != getDefaultToolkit()) {
/* 1331 */       return getDefaultToolkit().getSystemSelection();
/*      */     }
/* 1333 */     GraphicsEnvironment.checkHeadless();
/* 1334 */     return null;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public int getMenuShortcutKeyMask()
/*      */     throws HeadlessException
/*      */   {
/* 1359 */     GraphicsEnvironment.checkHeadless();
/*      */     
/* 1361 */     return 2;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public boolean getLockingKeyState(int paramInt)
/*      */     throws UnsupportedOperationException
/*      */   {
/*      */     
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1388 */     if ((paramInt != 20) && (paramInt != 144) && (paramInt != 145) && (paramInt != 262))
/*      */     {
/* 1390 */       throw new IllegalArgumentException("invalid key for Toolkit.getLockingKeyState");
/*      */     }
/* 1392 */     throw new UnsupportedOperationException("Toolkit.getLockingKeyState");
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public void setLockingKeyState(int paramInt, boolean paramBoolean)
/*      */     throws UnsupportedOperationException
/*      */   {
/*      */     
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1422 */     if ((paramInt != 20) && (paramInt != 144) && (paramInt != 145) && (paramInt != 262))
/*      */     {
/* 1424 */       throw new IllegalArgumentException("invalid key for Toolkit.setLockingKeyState");
/*      */     }
/* 1426 */     throw new UnsupportedOperationException("Toolkit.setLockingKeyState");
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   protected static Container getNativeContainer(Component paramComponent)
/*      */   {
/* 1434 */     return paramComponent.getNativeContainer();
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public Cursor createCustomCursor(Image paramImage, Point paramPoint, String paramString)
/*      */     throws IndexOutOfBoundsException, HeadlessException
/*      */   {
/* 1461 */     if (this != getDefaultToolkit())
/*      */     {
/* 1463 */       return getDefaultToolkit().createCustomCursor(paramImage, paramPoint, paramString);
/*      */     }
/* 1465 */     return new Cursor(0);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public Dimension getBestCursorSize(int paramInt1, int paramInt2)
/*      */     throws HeadlessException
/*      */   {
/*      */     
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1498 */     if (this != getDefaultToolkit())
/*      */     {
/* 1500 */       return getDefaultToolkit().getBestCursorSize(paramInt1, paramInt2);
/*      */     }
/* 1502 */     return new Dimension(0, 0);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public int getMaximumCursorColors()
/*      */     throws HeadlessException
/*      */   {
/*      */     
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1527 */     if (this != getDefaultToolkit()) {
/* 1528 */       return getDefaultToolkit().getMaximumCursorColors();
/*      */     }
/* 1530 */     return 0;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public boolean isFrameStateSupported(int paramInt)
/*      */     throws HeadlessException
/*      */   {
/*      */     
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1577 */     if (this != getDefaultToolkit())
/*      */     {
/* 1579 */       return getDefaultToolkit().isFrameStateSupported(paramInt);
/*      */     }
/* 1581 */     return paramInt == 0;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private static void setPlatformResources(ResourceBundle paramResourceBundle)
/*      */   {
/* 1595 */     platformResources = paramResourceBundle;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   static void loadLibraries()
/*      */   {
/* 1632 */     if (!loaded) {
/* 1633 */       AccessController.doPrivileged(new PrivilegedAction()
/*      */       {
/*      */         public Void run() {
/* 1636 */           System.loadLibrary("awt");
/* 1637 */           return null;
/*      */         }
/* 1639 */       });
/* 1640 */       loaded = true;
/*      */     }
/*      */   }
/*      */   
/*      */   static
/*      */   {
/* 1630 */     loaded = false;
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1645 */     AWTAccessor.setToolkitAccessor(new AWTAccessor.ToolkitAccessor()
/*      */     {
/*      */       public void setPlatformResources(ResourceBundle paramAnonymousResourceBundle)
/*      */       {
/* 1649 */         Toolkit.setPlatformResources(paramAnonymousResourceBundle);
/*      */       }
/*      */       
/* 1652 */     });
/* 1653 */     AccessController.doPrivileged(new PrivilegedAction()
/*      */     {
/*      */       public Void run() {
/*      */         try {
/* 1657 */           Toolkit.access$202(
/* 1658 */             ResourceBundle.getBundle("sun.awt.resources.awt", 
/* 1659 */             CoreResourceBundleControl.getRBControlInstance()));
/*      */         }
/*      */         catch (MissingResourceException localMissingResourceException) {}
/*      */         
/* 1663 */         return null;
/*      */       }
/*      */       
/*      */ 
/* 1667 */     });
/* 1668 */     loadLibraries();
/* 1669 */     initAssistiveTechnologies();
/* 1670 */     if (!GraphicsEnvironment.isHeadless()) {
/* 1671 */       initIDs();
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static String getProperty(String paramString1, String paramString2)
/*      */   {
/* 1681 */     if (platformResources != null) {
/*      */       try {
/* 1683 */         return platformResources.getString(paramString1);
/*      */       }
/*      */       catch (MissingResourceException localMissingResourceException1) {}
/*      */     }
/*      */     
/*      */ 
/* 1689 */     if (resources != null) {
/*      */       try {
/* 1691 */         return resources.getString(paramString1);
/*      */       }
/*      */       catch (MissingResourceException localMissingResourceException2) {}
/*      */     }
/*      */     
/* 1696 */     return paramString2;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public final EventQueue getSystemEventQueue()
/*      */   {
/* 1717 */     SecurityManager localSecurityManager = System.getSecurityManager();
/* 1718 */     if (localSecurityManager != null) {
/* 1719 */       localSecurityManager.checkPermission(SecurityConstants.AWT.CHECK_AWT_EVENTQUEUE_PERMISSION);
/*      */     }
/* 1721 */     return getSystemEventQueueImpl();
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   static EventQueue getEventQueue()
/*      */   {
/* 1734 */     return getDefaultToolkit().getSystemEventQueueImpl();
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public <T extends DragGestureRecognizer> T createDragGestureRecognizer(Class<T> paramClass, DragSource paramDragSource, Component paramComponent, int paramInt, DragGestureListener paramDragGestureListener)
/*      */   {
/* 1767 */     return null;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public final synchronized Object getDesktopProperty(String paramString)
/*      */   {
/* 1784 */     if ((this instanceof HeadlessToolkit))
/*      */     {
/* 1786 */       return ((HeadlessToolkit)this).getUnderlyingToolkit().getDesktopProperty(paramString);
/*      */     }
/*      */     
/* 1789 */     if (this.desktopProperties.isEmpty()) {
/* 1790 */       initializeDesktopProperties();
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/* 1796 */     if (paramString.equals("awt.dynamicLayoutSupported")) {
/* 1797 */       return getDefaultToolkit().lazilyLoadDesktopProperty(paramString);
/*      */     }
/*      */     
/* 1800 */     Object localObject = this.desktopProperties.get(paramString);
/*      */     
/* 1802 */     if (localObject == null) {
/* 1803 */       localObject = lazilyLoadDesktopProperty(paramString);
/*      */       
/* 1805 */       if (localObject != null) {
/* 1806 */         setDesktopProperty(paramString, localObject);
/*      */       }
/*      */     }
/*      */     
/*      */ 
/* 1811 */     if ((localObject instanceof RenderingHints)) {
/* 1812 */       localObject = ((RenderingHints)localObject).clone();
/*      */     }
/*      */     
/* 1815 */     return localObject;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   protected final void setDesktopProperty(String paramString, Object paramObject)
/*      */   {
/* 1827 */     if ((this instanceof HeadlessToolkit))
/*      */     {
/* 1829 */       ((HeadlessToolkit)this).getUnderlyingToolkit().setDesktopProperty(paramString, paramObject); return;
/*      */     }
/*      */     
/*      */     Object localObject1;
/*      */     
/* 1834 */     synchronized (this) {
/* 1835 */       localObject1 = this.desktopProperties.get(paramString);
/* 1836 */       this.desktopProperties.put(paramString, paramObject);
/*      */     }
/*      */     
/*      */ 
/*      */ 
/* 1841 */     if ((localObject1 != null) || (paramObject != null)) {
/* 1842 */       this.desktopPropsSupport.firePropertyChange(paramString, localObject1, paramObject);
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   protected Object lazilyLoadDesktopProperty(String paramString)
/*      */   {
/* 1850 */     return null;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public void addPropertyChangeListener(String paramString, PropertyChangeListener paramPropertyChangeListener)
/*      */   {
/* 1873 */     this.desktopPropsSupport.addPropertyChangeListener(paramString, paramPropertyChangeListener);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public void removePropertyChangeListener(String paramString, PropertyChangeListener paramPropertyChangeListener)
/*      */   {
/* 1891 */     this.desktopPropsSupport.removePropertyChangeListener(paramString, paramPropertyChangeListener);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public PropertyChangeListener[] getPropertyChangeListeners()
/*      */   {
/* 1908 */     return this.desktopPropsSupport.getPropertyChangeListeners();
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public PropertyChangeListener[] getPropertyChangeListeners(String paramString)
/*      */   {
/* 1924 */     return this.desktopPropsSupport.getPropertyChangeListeners(paramString);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public boolean isAlwaysOnTopSupported()
/*      */   {
/* 1943 */     return true;
/*      */   }
/*      */   
/*      */   public Toolkit()
/*      */   {
/* 1927 */     this.desktopProperties = new HashMap();
/*      */     
/*      */ 
/* 1930 */     this.desktopPropsSupport = createPropertyChangeSupport(this);
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1985 */     this.calls = new int[64];
/*      */     
/* 1987 */     this.eventListener = null;
/* 1988 */     this.listener2SelectiveListener = new WeakHashMap();
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   private static AWTEventListener deProxyAWTEventListener(AWTEventListener paramAWTEventListener)
/*      */   {
/* 1996 */     AWTEventListener localAWTEventListener = paramAWTEventListener;
/*      */     
/* 1998 */     if (localAWTEventListener == null) {
/* 1999 */       return null;
/*      */     }
/*      */     
/*      */ 
/* 2003 */     if ((paramAWTEventListener instanceof AWTEventListenerProxy)) {
/* 2004 */       localAWTEventListener = (AWTEventListener)((AWTEventListenerProxy)paramAWTEventListener).getListener();
/*      */     }
/* 2006 */     return localAWTEventListener;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public void addAWTEventListener(AWTEventListener paramAWTEventListener, long paramLong)
/*      */   {
/* 2044 */     AWTEventListener localAWTEventListener = deProxyAWTEventListener(paramAWTEventListener);
/*      */     
/* 2046 */     if (localAWTEventListener == null) {
/* 2047 */       return;
/*      */     }
/* 2049 */     SecurityManager localSecurityManager = System.getSecurityManager();
/* 2050 */     if (localSecurityManager != null) {
/* 2051 */       localSecurityManager.checkPermission(SecurityConstants.AWT.ALL_AWT_EVENTS_PERMISSION);
/*      */     }
/* 2053 */     synchronized (this)
/*      */     {
/* 2055 */       SelectiveAWTEventListener localSelectiveAWTEventListener = (SelectiveAWTEventListener)this.listener2SelectiveListener.get(localAWTEventListener);
/*      */       
/* 2057 */       if (localSelectiveAWTEventListener == null)
/*      */       {
/* 2059 */         localSelectiveAWTEventListener = new SelectiveAWTEventListener(localAWTEventListener, paramLong);
/*      */         
/* 2061 */         this.listener2SelectiveListener.put(localAWTEventListener, localSelectiveAWTEventListener);
/* 2062 */         this.eventListener = ToolkitEventMulticaster.add(this.eventListener, localSelectiveAWTEventListener);
/*      */       }
/*      */       
/*      */ 
/* 2066 */       localSelectiveAWTEventListener.orEventMasks(paramLong);
/*      */       
/* 2068 */       enabledOnToolkitMask |= paramLong;
/*      */       
/* 2070 */       long l = paramLong;
/* 2071 */       for (int i = 0; i < 64; i++)
/*      */       {
/* 2073 */         if (l == 0L) {
/*      */           break;
/*      */         }
/* 2076 */         if ((l & 1L) != 0L) {
/* 2077 */           this.calls[i] += 1;
/*      */         }
/* 2079 */         l >>>= 1;
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public void removeAWTEventListener(AWTEventListener paramAWTEventListener)
/*      */   {
/* 2113 */     AWTEventListener localAWTEventListener = deProxyAWTEventListener(paramAWTEventListener);
/*      */     
/* 2115 */     if (paramAWTEventListener == null) {
/* 2116 */       return;
/*      */     }
/* 2118 */     SecurityManager localSecurityManager = System.getSecurityManager();
/* 2119 */     if (localSecurityManager != null) {
/* 2120 */       localSecurityManager.checkPermission(SecurityConstants.AWT.ALL_AWT_EVENTS_PERMISSION);
/*      */     }
/*      */     
/* 2123 */     synchronized (this)
/*      */     {
/* 2125 */       SelectiveAWTEventListener localSelectiveAWTEventListener = (SelectiveAWTEventListener)this.listener2SelectiveListener.get(localAWTEventListener);
/*      */       
/* 2127 */       if (localSelectiveAWTEventListener != null) {
/* 2128 */         this.listener2SelectiveListener.remove(localAWTEventListener);
/* 2129 */         int[] arrayOfInt = localSelectiveAWTEventListener.getCalls();
/* 2130 */         for (int i = 0; i < 64; i++) {
/* 2131 */           this.calls[i] -= arrayOfInt[i];
/* 2132 */           assert (this.calls[i] >= 0) : "Negative Listeners count";
/*      */           
/* 2134 */           if (this.calls[i] == 0) {
/* 2135 */             enabledOnToolkitMask &= (1L << i ^ 0xFFFFFFFFFFFFFFFF);
/*      */           }
/*      */         }
/*      */       }
/* 2139 */       this.eventListener = ToolkitEventMulticaster.remove(this.eventListener, localSelectiveAWTEventListener == null ? localAWTEventListener : localSelectiveAWTEventListener);
/*      */     }
/*      */   }
/*      */   
/*      */   static boolean enabledOnToolkit(long paramLong)
/*      */   {
/* 2145 */     return (enabledOnToolkitMask & paramLong) != 0L;
/*      */   }
/*      */   
/*      */   synchronized int countAWTEventListeners(long paramLong) {
/* 2149 */     for (int i = 0; 
/* 2150 */         paramLong != 0L; i++) { paramLong >>>= 1;
/*      */     }
/* 2152 */     i--;
/* 2153 */     return this.calls[i];
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public AWTEventListener[] getAWTEventListeners()
/*      */   {
/* 2183 */     SecurityManager localSecurityManager = System.getSecurityManager();
/* 2184 */     if (localSecurityManager != null) {
/* 2185 */       localSecurityManager.checkPermission(SecurityConstants.AWT.ALL_AWT_EVENTS_PERMISSION);
/*      */     }
/* 2187 */     synchronized (this) {
/* 2188 */       EventListener[] arrayOfEventListener = ToolkitEventMulticaster.getListeners(this.eventListener, AWTEventListener.class);
/*      */       
/* 2190 */       AWTEventListener[] arrayOfAWTEventListener = new AWTEventListener[arrayOfEventListener.length];
/* 2191 */       for (int i = 0; i < arrayOfEventListener.length; i++) {
/* 2192 */         SelectiveAWTEventListener localSelectiveAWTEventListener = (SelectiveAWTEventListener)arrayOfEventListener[i];
/* 2193 */         AWTEventListener localAWTEventListener = localSelectiveAWTEventListener.getListener();
/*      */         
/*      */ 
/*      */ 
/* 2197 */         arrayOfAWTEventListener[i] = new AWTEventListenerProxy(localSelectiveAWTEventListener.getEventMask(), localAWTEventListener);
/*      */       }
/* 2199 */       return arrayOfAWTEventListener;
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public AWTEventListener[] getAWTEventListeners(long paramLong)
/*      */   {
/* 2235 */     SecurityManager localSecurityManager = System.getSecurityManager();
/* 2236 */     if (localSecurityManager != null) {
/* 2237 */       localSecurityManager.checkPermission(SecurityConstants.AWT.ALL_AWT_EVENTS_PERMISSION);
/*      */     }
/* 2239 */     synchronized (this) {
/* 2240 */       EventListener[] arrayOfEventListener = ToolkitEventMulticaster.getListeners(this.eventListener, AWTEventListener.class);
/*      */       
/* 2242 */       ArrayList localArrayList = new ArrayList(arrayOfEventListener.length);
/*      */       
/* 2244 */       for (int i = 0; i < arrayOfEventListener.length; i++) {
/* 2245 */         SelectiveAWTEventListener localSelectiveAWTEventListener = (SelectiveAWTEventListener)arrayOfEventListener[i];
/* 2246 */         if ((localSelectiveAWTEventListener.getEventMask() & paramLong) == paramLong)
/*      */         {
/* 2248 */           localArrayList.add(new AWTEventListenerProxy(localSelectiveAWTEventListener.getEventMask(), localSelectiveAWTEventListener
/* 2249 */             .getListener()));
/*      */         }
/*      */       }
/* 2252 */       return (AWTEventListener[])localArrayList.toArray(new AWTEventListener[0]);
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   void notifyAWTEventListeners(AWTEvent paramAWTEvent)
/*      */   {
/* 2267 */     if ((this instanceof HeadlessToolkit))
/*      */     {
/* 2269 */       ((HeadlessToolkit)this).getUnderlyingToolkit().notifyAWTEventListeners(paramAWTEvent);
/* 2270 */       return;
/*      */     }
/*      */     
/* 2273 */     AWTEventListener localAWTEventListener = this.eventListener;
/* 2274 */     if (localAWTEventListener != null) {
/* 2275 */       localAWTEventListener.eventDispatched(paramAWTEvent);
/*      */     }
/*      */   }
/*      */   
/*      */   private static class ToolkitEventMulticaster
/*      */     extends AWTEventMulticaster implements AWTEventListener
/*      */   {
/*      */     ToolkitEventMulticaster(AWTEventListener paramAWTEventListener1, AWTEventListener paramAWTEventListener2)
/*      */     {
/* 2284 */       super(paramAWTEventListener2);
/*      */     }
/*      */     
/*      */     static AWTEventListener add(AWTEventListener paramAWTEventListener1, AWTEventListener paramAWTEventListener2)
/*      */     {
/* 2289 */       if (paramAWTEventListener1 == null) return paramAWTEventListener2;
/* 2290 */       if (paramAWTEventListener2 == null) return paramAWTEventListener1;
/* 2291 */       return new ToolkitEventMulticaster(paramAWTEventListener1, paramAWTEventListener2);
/*      */     }
/*      */     
/*      */     static AWTEventListener remove(AWTEventListener paramAWTEventListener1, AWTEventListener paramAWTEventListener2)
/*      */     {
/* 2296 */       return (AWTEventListener)removeInternal(paramAWTEventListener1, paramAWTEventListener2);
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     protected EventListener remove(EventListener paramEventListener)
/*      */     {
/* 2305 */       if (paramEventListener == this.a) return this.b;
/* 2306 */       if (paramEventListener == this.b) return this.a;
/* 2307 */       AWTEventListener localAWTEventListener1 = (AWTEventListener)removeInternal(this.a, paramEventListener);
/* 2308 */       AWTEventListener localAWTEventListener2 = (AWTEventListener)removeInternal(this.b, paramEventListener);
/* 2309 */       if ((localAWTEventListener1 == this.a) && (localAWTEventListener2 == this.b)) {
/* 2310 */         return this;
/*      */       }
/* 2312 */       return add(localAWTEventListener1, localAWTEventListener2);
/*      */     }
/*      */     
/*      */     public void eventDispatched(AWTEvent paramAWTEvent) {
/* 2316 */       ((AWTEventListener)this.a).eventDispatched(paramAWTEvent);
/* 2317 */       ((AWTEventListener)this.b).eventDispatched(paramAWTEvent);
/*      */     }
/*      */   }
/*      */   
/*      */   private class SelectiveAWTEventListener
/*      */     implements AWTEventListener
/*      */   {
/*      */     AWTEventListener listener;
/*      */     private long eventMask;
/* 2326 */     int[] calls = new int[64];
/*      */     
/* 2328 */     public AWTEventListener getListener() { return this.listener; }
/* 2329 */     public long getEventMask() { return this.eventMask; }
/* 2330 */     public int[] getCalls() { return this.calls; }
/*      */     
/*      */     public void orEventMasks(long paramLong) {
/* 2333 */       this.eventMask |= paramLong;
/*      */       
/* 2335 */       for (int i = 0; i < 64; i++)
/*      */       {
/* 2337 */         if (paramLong == 0L) {
/*      */           break;
/*      */         }
/* 2340 */         if ((paramLong & 1L) != 0L) {
/* 2341 */           this.calls[i] += 1;
/*      */         }
/* 2343 */         paramLong >>>= 1;
/*      */       }
/*      */     }
/*      */     
/*      */     SelectiveAWTEventListener(AWTEventListener paramAWTEventListener, long paramLong) {
/* 2348 */       this.listener = paramAWTEventListener;
/* 2349 */       this.eventMask = paramLong;
/*      */     }
/*      */     
/*      */     public void eventDispatched(AWTEvent paramAWTEvent) {
/* 2353 */       long l1 = 0L;
/* 2354 */       if ((((l1 = this.eventMask & 1L) != 0L) && (paramAWTEvent.id >= 100) && (paramAWTEvent.id <= 103)) || (((l1 = this.eventMask & 0x2) != 0L) && (paramAWTEvent.id >= 300) && (paramAWTEvent.id <= 301)) || (((l1 = this.eventMask & 0x4) != 0L) && (paramAWTEvent.id >= 1004) && (paramAWTEvent.id <= 1005)) || (((l1 = this.eventMask & 0x8) != 0L) && (paramAWTEvent.id >= 400) && (paramAWTEvent.id <= 402)) || (((l1 = this.eventMask & 0x20000) != 0L) && (paramAWTEvent.id == 507)) || (((l1 = this.eventMask & 0x20) != 0L) && ((paramAWTEvent.id == 503) || (paramAWTEvent.id == 506))) || (((l1 = this.eventMask & 0x10) != 0L) && (paramAWTEvent.id != 503) && (paramAWTEvent.id != 506) && (paramAWTEvent.id != 507) && (paramAWTEvent.id >= 500) && (paramAWTEvent.id <= 507)) || (((l1 = this.eventMask & 0x40) != 0L) && (paramAWTEvent.id >= 200) && (paramAWTEvent.id <= 209)) || (((l1 = this.eventMask & 0x80) != 0L) && (paramAWTEvent.id >= 1001) && (paramAWTEvent.id <= 1001)) || (((l1 = this.eventMask & 0x100) != 0L) && (paramAWTEvent.id >= 601) && (paramAWTEvent.id <= 601)) || (((l1 = this.eventMask & 0x200) != 0L) && (paramAWTEvent.id >= 701) && (paramAWTEvent.id <= 701)) || (((l1 = this.eventMask & 0x400) != 0L) && (paramAWTEvent.id >= 900) && (paramAWTEvent.id <= 900)) || (((l1 = this.eventMask & 0x800) != 0L) && (paramAWTEvent.id >= 1100) && (paramAWTEvent.id <= 1101)) || (((l1 = this.eventMask & 0x2000) != 0L) && (paramAWTEvent.id >= 800) && (paramAWTEvent.id <= 801)) || (((l1 = this.eventMask & 0x4000) != 0L) && (paramAWTEvent.id >= 1200) && (paramAWTEvent.id <= 1200)) || (((l1 = this.eventMask & 0x8000) != 0L) && (paramAWTEvent.id == 1400)) || (((l1 = this.eventMask & 0x10000) != 0L) && ((paramAWTEvent.id == 1401) || (paramAWTEvent.id == 1402))) || (((l1 = this.eventMask & 0x40000) != 0L) && (paramAWTEvent.id == 209)) || (((l1 = this.eventMask & 0x80000) != 0L) && ((paramAWTEvent.id == 207) || (paramAWTEvent.id == 208))) || (((l1 = this.eventMask & 0xFFFFFFFF80000000) != 0L) && ((paramAWTEvent instanceof UngrabEvent))))
/*      */       {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 2418 */         int i = 0;
/* 2419 */         for (long l2 = l1; l2 != 0L; i++) { l2 >>>= 1;
/*      */         }
/* 2421 */         i--;
/*      */         
/*      */ 
/* 2424 */         for (int j = 0; j < this.calls[i]; j++) {
/* 2425 */           this.listener.eventDispatched(paramAWTEvent);
/*      */         }
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private static PropertyChangeSupport createPropertyChangeSupport(Toolkit paramToolkit)
/*      */   {
/* 2448 */     if (((paramToolkit instanceof SunToolkit)) || ((paramToolkit instanceof HeadlessToolkit))) {
/* 2449 */       return new DesktopPropertyChangeSupport(paramToolkit);
/*      */     }
/* 2451 */     return new PropertyChangeSupport(paramToolkit);
/*      */   }
/*      */   
/*      */ 
/*      */   private static class DesktopPropertyChangeSupport
/*      */     extends PropertyChangeSupport
/*      */   {
/* 2458 */     private static final StringBuilder PROP_CHANGE_SUPPORT_KEY = new StringBuilder("desktop property change support key");
/*      */     private final Object source;
/*      */     
/*      */     public DesktopPropertyChangeSupport(Object paramObject)
/*      */     {
/* 2463 */       super();
/* 2464 */       this.source = paramObject;
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     public synchronized void addPropertyChangeListener(String paramString, PropertyChangeListener paramPropertyChangeListener)
/*      */     {
/* 2473 */       PropertyChangeSupport localPropertyChangeSupport = (PropertyChangeSupport)AppContext.getAppContext().get(PROP_CHANGE_SUPPORT_KEY);
/* 2474 */       if (null == localPropertyChangeSupport) {
/* 2475 */         localPropertyChangeSupport = new PropertyChangeSupport(this.source);
/* 2476 */         AppContext.getAppContext().put(PROP_CHANGE_SUPPORT_KEY, localPropertyChangeSupport);
/*      */       }
/* 2478 */       localPropertyChangeSupport.addPropertyChangeListener(paramString, paramPropertyChangeListener);
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     public synchronized void removePropertyChangeListener(String paramString, PropertyChangeListener paramPropertyChangeListener)
/*      */     {
/* 2487 */       PropertyChangeSupport localPropertyChangeSupport = (PropertyChangeSupport)AppContext.getAppContext().get(PROP_CHANGE_SUPPORT_KEY);
/* 2488 */       if (null != localPropertyChangeSupport) {
/* 2489 */         localPropertyChangeSupport.removePropertyChangeListener(paramString, paramPropertyChangeListener);
/*      */       }
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */     public synchronized PropertyChangeListener[] getPropertyChangeListeners()
/*      */     {
/* 2497 */       PropertyChangeSupport localPropertyChangeSupport = (PropertyChangeSupport)AppContext.getAppContext().get(PROP_CHANGE_SUPPORT_KEY);
/* 2498 */       if (null != localPropertyChangeSupport) {
/* 2499 */         return localPropertyChangeSupport.getPropertyChangeListeners();
/*      */       }
/* 2501 */       return new PropertyChangeListener[0];
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */     public synchronized PropertyChangeListener[] getPropertyChangeListeners(String paramString)
/*      */     {
/* 2509 */       PropertyChangeSupport localPropertyChangeSupport = (PropertyChangeSupport)AppContext.getAppContext().get(PROP_CHANGE_SUPPORT_KEY);
/* 2510 */       if (null != localPropertyChangeSupport) {
/* 2511 */         return localPropertyChangeSupport.getPropertyChangeListeners(paramString);
/*      */       }
/* 2513 */       return new PropertyChangeListener[0];
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */     public synchronized void addPropertyChangeListener(PropertyChangeListener paramPropertyChangeListener)
/*      */     {
/* 2520 */       PropertyChangeSupport localPropertyChangeSupport = (PropertyChangeSupport)AppContext.getAppContext().get(PROP_CHANGE_SUPPORT_KEY);
/* 2521 */       if (null == localPropertyChangeSupport) {
/* 2522 */         localPropertyChangeSupport = new PropertyChangeSupport(this.source);
/* 2523 */         AppContext.getAppContext().put(PROP_CHANGE_SUPPORT_KEY, localPropertyChangeSupport);
/*      */       }
/* 2525 */       localPropertyChangeSupport.addPropertyChangeListener(paramPropertyChangeListener);
/*      */     }
/*      */     
/*      */ 
/*      */     public synchronized void removePropertyChangeListener(PropertyChangeListener paramPropertyChangeListener)
/*      */     {
/* 2531 */       PropertyChangeSupport localPropertyChangeSupport = (PropertyChangeSupport)AppContext.getAppContext().get(PROP_CHANGE_SUPPORT_KEY);
/* 2532 */       if (null != localPropertyChangeSupport) {
/* 2533 */         localPropertyChangeSupport.removePropertyChangeListener(paramPropertyChangeListener);
/*      */       }
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     public void firePropertyChange(final PropertyChangeEvent paramPropertyChangeEvent)
/*      */     {
/* 2543 */       Object localObject1 = paramPropertyChangeEvent.getOldValue();
/* 2544 */       Object localObject2 = paramPropertyChangeEvent.getNewValue();
/* 2545 */       String str = paramPropertyChangeEvent.getPropertyName();
/* 2546 */       if ((localObject1 != null) && (localObject2 != null) && (localObject1.equals(localObject2))) {
/* 2547 */         return;
/*      */       }
/* 2549 */       Runnable local1 = new Runnable()
/*      */       {
/*      */         public void run() {
/* 2552 */           PropertyChangeSupport localPropertyChangeSupport = (PropertyChangeSupport)AppContext.getAppContext().get(Toolkit.DesktopPropertyChangeSupport.PROP_CHANGE_SUPPORT_KEY);
/* 2553 */           if (null != localPropertyChangeSupport) {
/* 2554 */             localPropertyChangeSupport.firePropertyChange(paramPropertyChangeEvent);
/*      */           }
/*      */         }
/* 2557 */       };
/* 2558 */       AppContext localAppContext1 = AppContext.getAppContext();
/* 2559 */       for (AppContext localAppContext2 : AppContext.getAppContexts()) {
/* 2560 */         if ((null != localAppContext2) && (!localAppContext2.isDisposed()))
/*      */         {
/*      */ 
/* 2563 */           if (localAppContext1 == localAppContext2) {
/* 2564 */             local1.run();
/*      */           } else {
/* 2566 */             PeerEvent localPeerEvent = new PeerEvent(this.source, local1, 2L);
/* 2567 */             SunToolkit.postEvent(localAppContext2, localPeerEvent);
/*      */           }
/*      */         }
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public boolean areExtraMouseButtonsEnabled()
/*      */     throws HeadlessException
/*      */   {
/* 2601 */     GraphicsEnvironment.checkHeadless();
/*      */     
/* 2603 */     return getDefaultToolkit().areExtraMouseButtonsEnabled();
/*      */   }
/*      */   
/*      */   protected abstract DesktopPeer createDesktopPeer(Desktop paramDesktop)
/*      */     throws HeadlessException;
/*      */   
/*      */   protected abstract ButtonPeer createButton(Button paramButton)
/*      */     throws HeadlessException;
/*      */   
/*      */   protected abstract TextFieldPeer createTextField(TextField paramTextField)
/*      */     throws HeadlessException;
/*      */   
/*      */   protected abstract LabelPeer createLabel(Label paramLabel)
/*      */     throws HeadlessException;
/*      */   
/*      */   protected abstract ListPeer createList(List paramList)
/*      */     throws HeadlessException;
/*      */   
/*      */   protected abstract CheckboxPeer createCheckbox(Checkbox paramCheckbox)
/*      */     throws HeadlessException;
/*      */   
/*      */   protected abstract ScrollbarPeer createScrollbar(Scrollbar paramScrollbar)
/*      */     throws HeadlessException;
/*      */   
/*      */   protected abstract ScrollPanePeer createScrollPane(ScrollPane paramScrollPane)
/*      */     throws HeadlessException;
/*      */   
/*      */   protected abstract TextAreaPeer createTextArea(TextArea paramTextArea)
/*      */     throws HeadlessException;
/*      */   
/*      */   protected abstract ChoicePeer createChoice(Choice paramChoice)
/*      */     throws HeadlessException;
/*      */   
/*      */   protected abstract FramePeer createFrame(Frame paramFrame)
/*      */     throws HeadlessException;
/*      */   
/*      */   protected abstract CanvasPeer createCanvas(Canvas paramCanvas);
/*      */   
/*      */   protected abstract PanelPeer createPanel(Panel paramPanel);
/*      */   
/*      */   protected abstract WindowPeer createWindow(Window paramWindow)
/*      */     throws HeadlessException;
/*      */   
/*      */   protected abstract DialogPeer createDialog(Dialog paramDialog)
/*      */     throws HeadlessException;
/*      */   
/*      */   protected abstract MenuBarPeer createMenuBar(MenuBar paramMenuBar)
/*      */     throws HeadlessException;
/*      */   
/*      */   protected abstract MenuPeer createMenu(Menu paramMenu)
/*      */     throws HeadlessException;
/*      */   
/*      */   protected abstract PopupMenuPeer createPopupMenu(PopupMenu paramPopupMenu)
/*      */     throws HeadlessException;
/*      */   
/*      */   protected abstract MenuItemPeer createMenuItem(MenuItem paramMenuItem)
/*      */     throws HeadlessException;
/*      */   
/*      */   protected abstract FileDialogPeer createFileDialog(FileDialog paramFileDialog)
/*      */     throws HeadlessException;
/*      */   
/*      */   protected abstract CheckboxMenuItemPeer createCheckboxMenuItem(CheckboxMenuItem paramCheckboxMenuItem)
/*      */     throws HeadlessException;
/*      */   
/*      */   @Deprecated
/*      */   protected abstract FontPeer getFontPeer(String paramString, int paramInt);
/*      */   
/*      */   protected void loadSystemColors(int[] paramArrayOfInt)
/*      */     throws HeadlessException
/*      */   {}
/*      */   
/*      */   public abstract Dimension getScreenSize()
/*      */     throws HeadlessException;
/*      */   
/*      */   public abstract int getScreenResolution()
/*      */     throws HeadlessException;
/*      */   
/*      */   public abstract ColorModel getColorModel()
/*      */     throws HeadlessException;
/*      */   
/*      */   @Deprecated
/*      */   public abstract String[] getFontList();
/*      */   
/*      */   @Deprecated
/*      */   public abstract FontMetrics getFontMetrics(Font paramFont);
/*      */   
/*      */   public abstract void sync();
/*      */   
/*      */   public abstract Image getImage(String paramString);
/*      */   
/*      */   public abstract Image getImage(URL paramURL);
/*      */   
/*      */   public abstract Image createImage(String paramString);
/*      */   
/*      */   public abstract Image createImage(URL paramURL);
/*      */   
/*      */   public abstract boolean prepareImage(Image paramImage, int paramInt1, int paramInt2, ImageObserver paramImageObserver);
/*      */   
/*      */   public abstract int checkImage(Image paramImage, int paramInt1, int paramInt2, ImageObserver paramImageObserver);
/*      */   
/*      */   public abstract Image createImage(ImageProducer paramImageProducer);
/*      */   
/*      */   public abstract Image createImage(byte[] paramArrayOfByte, int paramInt1, int paramInt2);
/*      */   
/*      */   public abstract PrintJob getPrintJob(Frame paramFrame, String paramString, Properties paramProperties);
/*      */   
/*      */   public abstract void beep();
/*      */   
/*      */   public abstract Clipboard getSystemClipboard()
/*      */     throws HeadlessException;
/*      */   
/*      */   private static native void initIDs();
/*      */   
/*      */   protected abstract EventQueue getSystemEventQueueImpl();
/*      */   
/*      */   public abstract DragSourceContextPeer createDragSourceContextPeer(DragGestureEvent paramDragGestureEvent)
/*      */     throws InvalidDnDOperationException;
/*      */   
/*      */   protected void initializeDesktopProperties() {}
/*      */   
/*      */   public abstract boolean isModalityTypeSupported(Dialog.ModalityType paramModalityType);
/*      */   
/*      */   public abstract boolean isModalExclusionTypeSupported(Dialog.ModalExclusionType paramModalExclusionType);
/*      */   
/*      */   public abstract Map<TextAttribute, ?> mapInputMethodHighlight(InputMethodHighlight paramInputMethodHighlight)
/*      */     throws HeadlessException;
/*      */ }


/* Location:              /Users/guanxiaoda/Documents/workspace/rt.jar!/java/awt/Toolkit.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */