/*     */ package java.awt;
/*     */ 
/*     */ import java.awt.event.KeyEvent;
/*     */ import java.io.ObjectStreamException;
/*     */ import java.io.Serializable;
/*     */ import java.lang.reflect.Constructor;
/*     */ import java.lang.reflect.Field;
/*     */ import java.lang.reflect.InvocationTargetException;
/*     */ import java.security.AccessController;
/*     */ import java.security.PrivilegedAction;
/*     */ import java.util.Collections;
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
/*     */ import java.util.StringTokenizer;
/*     */ import sun.awt.AppContext;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class AWTKeyStroke
/*     */   implements Serializable
/*     */ {
/*     */   static final long serialVersionUID = -6430539691155161871L;
/*     */   private static Map<String, Integer> modifierKeywords;
/*     */   private static VKCollection vks;
/*     */   private static Object APP_CONTEXT_CACHE_KEY;
/*     */   private static AWTKeyStroke APP_CONTEXT_KEYSTROKE_KEY;
/*     */   
/*     */   private static Class<AWTKeyStroke> getAWTKeyStrokeClass()
/*     */   {
/*  89 */     Class localClass = (Class)AppContext.getAppContext().get(AWTKeyStroke.class);
/*  90 */     if (localClass == null) {
/*  91 */       localClass = AWTKeyStroke.class;
/*  92 */       AppContext.getAppContext().put(AWTKeyStroke.class, AWTKeyStroke.class);
/*     */     }
/*  94 */     return localClass;
/*     */   }
/*     */   
/*  97 */   private char keyChar = 65535;
/*  98 */   private int keyCode = 0;
/*     */   private int modifiers;
/*     */   private boolean onKeyRelease;
/*     */   
/*     */   static
/*     */   {
/*  79 */     APP_CONTEXT_CACHE_KEY = new Object();
/*     */     
/*  81 */     APP_CONTEXT_KEYSTROKE_KEY = new AWTKeyStroke();
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 104 */     Toolkit.loadLibraries();
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
/*     */   protected AWTKeyStroke(char paramChar, int paramInt1, int paramInt2, boolean paramBoolean)
/*     */   {
/* 155 */     this.keyChar = paramChar;
/* 156 */     this.keyCode = paramInt1;
/* 157 */     this.modifiers = paramInt2;
/* 158 */     this.onKeyRelease = paramBoolean;
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
/*     */   protected static void registerSubclass(Class<?> paramClass)
/*     */   {
/* 181 */     if (paramClass == null) {
/* 182 */       throw new IllegalArgumentException("subclass cannot be null");
/*     */     }
/* 184 */     synchronized (AWTKeyStroke.class) {
/* 185 */       localObject1 = (Class)AppContext.getAppContext().get(AWTKeyStroke.class);
/* 186 */       if ((localObject1 != null) && (localObject1.equals(paramClass)))
/*     */       {
/* 188 */         return;
/*     */       }
/*     */     }
/* 191 */     if (!AWTKeyStroke.class.isAssignableFrom(paramClass)) {
/* 192 */       throw new ClassCastException("subclass is not derived from AWTKeyStroke");
/*     */     }
/*     */     
/* 195 */     ??? = getCtor(paramClass);
/*     */     
/* 197 */     Object localObject1 = "subclass could not be instantiated";
/*     */     
/* 199 */     if (??? == null) {
/* 200 */       throw new IllegalArgumentException((String)localObject1);
/*     */     }
/*     */     try {
/* 203 */       AWTKeyStroke localAWTKeyStroke = (AWTKeyStroke)((Constructor)???).newInstance((Object[])null);
/* 204 */       if (localAWTKeyStroke == null) {
/* 205 */         throw new IllegalArgumentException((String)localObject1);
/*     */       }
/*     */     } catch (NoSuchMethodError localNoSuchMethodError) {
/* 208 */       throw new IllegalArgumentException((String)localObject1);
/*     */     } catch (ExceptionInInitializerError localExceptionInInitializerError) {
/* 210 */       throw new IllegalArgumentException((String)localObject1);
/*     */     } catch (InstantiationException localInstantiationException) {
/* 212 */       throw new IllegalArgumentException((String)localObject1);
/*     */     } catch (IllegalAccessException localIllegalAccessException) {
/* 214 */       throw new IllegalArgumentException((String)localObject1);
/*     */     } catch (InvocationTargetException localInvocationTargetException) {
/* 216 */       throw new IllegalArgumentException((String)localObject1);
/*     */     }
/*     */     
/* 219 */     synchronized (AWTKeyStroke.class) {
/* 220 */       AppContext.getAppContext().put(AWTKeyStroke.class, paramClass);
/* 221 */       AppContext.getAppContext().remove(APP_CONTEXT_CACHE_KEY);
/* 222 */       AppContext.getAppContext().remove(APP_CONTEXT_KEYSTROKE_KEY);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private static Constructor getCtor(Class paramClass)
/*     */   {
/* 232 */     Constructor localConstructor = (Constructor)AccessController.doPrivileged(new PrivilegedAction() {
/*     */       public Constructor run() {
/*     */         try {
/* 235 */           Constructor localConstructor = this.val$clazz.getDeclaredConstructor((Class[])null);
/* 236 */           if (localConstructor != null) {
/* 237 */             localConstructor.setAccessible(true);
/*     */           }
/* 239 */           return localConstructor;
/*     */         }
/*     */         catch (SecurityException localSecurityException) {}catch (NoSuchMethodException localNoSuchMethodException) {}
/*     */         
/* 243 */         return null;
/*     */       }
/* 245 */     });
/* 246 */     return localConstructor;
/*     */   }
/*     */   
/*     */ 
/*     */   private static synchronized AWTKeyStroke getCachedStroke(char paramChar, int paramInt1, int paramInt2, boolean paramBoolean)
/*     */   {
/* 252 */     Object localObject = (Map)AppContext.getAppContext().get(APP_CONTEXT_CACHE_KEY);
/* 253 */     AWTKeyStroke localAWTKeyStroke1 = (AWTKeyStroke)AppContext.getAppContext().get(APP_CONTEXT_KEYSTROKE_KEY);
/*     */     
/* 255 */     if (localObject == null) {
/* 256 */       localObject = new HashMap();
/* 257 */       AppContext.getAppContext().put(APP_CONTEXT_CACHE_KEY, localObject);
/*     */     }
/*     */     
/* 260 */     if (localAWTKeyStroke1 == null) {
/*     */       try {
/* 262 */         Class localClass = getAWTKeyStrokeClass();
/* 263 */         localAWTKeyStroke1 = (AWTKeyStroke)getCtor(localClass).newInstance((Object[])null);
/* 264 */         AppContext.getAppContext().put(APP_CONTEXT_KEYSTROKE_KEY, localAWTKeyStroke1);
/*     */       } catch (InstantiationException localInstantiationException) {
/* 266 */         if (!$assertionsDisabled) throw new AssertionError();
/*     */       } catch (IllegalAccessException localIllegalAccessException) {
/* 268 */         if (!$assertionsDisabled) throw new AssertionError();
/*     */       } catch (InvocationTargetException localInvocationTargetException) {
/* 270 */         if (!$assertionsDisabled) throw new AssertionError();
/*     */       }
/*     */     }
/* 273 */     localAWTKeyStroke1.keyChar = paramChar;
/* 274 */     localAWTKeyStroke1.keyCode = paramInt1;
/* 275 */     localAWTKeyStroke1.modifiers = mapNewModifiers(mapOldModifiers(paramInt2));
/* 276 */     localAWTKeyStroke1.onKeyRelease = paramBoolean;
/*     */     
/* 278 */     AWTKeyStroke localAWTKeyStroke2 = (AWTKeyStroke)((Map)localObject).get(localAWTKeyStroke1);
/* 279 */     if (localAWTKeyStroke2 == null) {
/* 280 */       localAWTKeyStroke2 = localAWTKeyStroke1;
/* 281 */       ((Map)localObject).put(localAWTKeyStroke2, localAWTKeyStroke2);
/* 282 */       AppContext.getAppContext().remove(APP_CONTEXT_KEYSTROKE_KEY);
/*     */     }
/* 284 */     return localAWTKeyStroke2;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static AWTKeyStroke getAWTKeyStroke(char paramChar)
/*     */   {
/* 296 */     return getCachedStroke(paramChar, 0, 0, false);
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
/*     */   public static AWTKeyStroke getAWTKeyStroke(Character paramCharacter, int paramInt)
/*     */   {
/* 338 */     if (paramCharacter == null) {
/* 339 */       throw new IllegalArgumentException("keyChar cannot be null");
/*     */     }
/* 341 */     return getCachedStroke(paramCharacter.charValue(), 0, paramInt, false);
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
/*     */   public static AWTKeyStroke getAWTKeyStroke(int paramInt1, int paramInt2, boolean paramBoolean)
/*     */   {
/* 391 */     return getCachedStroke(65535, paramInt1, paramInt2, paramBoolean);
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
/*     */   public static AWTKeyStroke getAWTKeyStroke(int paramInt1, int paramInt2)
/*     */   {
/* 435 */     return getCachedStroke(65535, paramInt1, paramInt2, false);
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
/*     */   public static AWTKeyStroke getAWTKeyStrokeForEvent(KeyEvent paramKeyEvent)
/*     */   {
/* 454 */     int i = paramKeyEvent.getID();
/* 455 */     switch (i) {
/*     */     case 401: 
/*     */     case 402: 
/* 458 */       return getCachedStroke(65535, paramKeyEvent
/* 459 */         .getKeyCode(), paramKeyEvent
/* 460 */         .getModifiers(), i == 402);
/*     */     
/*     */     case 400: 
/* 463 */       return getCachedStroke(paramKeyEvent.getKeyChar(), 0, paramKeyEvent
/*     */       
/* 465 */         .getModifiers(), false);
/*     */     }
/*     */     
/*     */     
/* 469 */     return null;
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
/*     */   public static AWTKeyStroke getAWTKeyStroke(String paramString)
/*     */   {
/* 501 */     if (paramString == null) {
/* 502 */       throw new IllegalArgumentException("String cannot be null");
/*     */     }
/*     */     
/*     */ 
/*     */ 
/* 507 */     StringTokenizer localStringTokenizer = new StringTokenizer(paramString, " ");
/*     */     
/* 509 */     int i = 0;
/* 510 */     boolean bool = false;
/* 511 */     int j = 0;
/* 512 */     int k = 0;
/*     */     
/* 514 */     synchronized (AWTKeyStroke.class) {
/* 515 */       if (modifierKeywords == null) {
/* 516 */         HashMap localHashMap = new HashMap(8, 1.0F);
/* 517 */         localHashMap.put("shift", 
/* 518 */           Integer.valueOf(65));
/*     */         
/* 520 */         localHashMap.put("control", 
/* 521 */           Integer.valueOf(130));
/*     */         
/* 523 */         localHashMap.put("ctrl", 
/* 524 */           Integer.valueOf(130));
/*     */         
/* 526 */         localHashMap.put("meta", 
/* 527 */           Integer.valueOf(260));
/*     */         
/* 529 */         localHashMap.put("alt", 
/* 530 */           Integer.valueOf(520));
/*     */         
/* 532 */         localHashMap.put("altGraph", 
/* 533 */           Integer.valueOf(8224));
/*     */         
/* 535 */         localHashMap.put("button1", 
/* 536 */           Integer.valueOf(1024));
/* 537 */         localHashMap.put("button2", 
/* 538 */           Integer.valueOf(2048));
/* 539 */         localHashMap.put("button3", 
/* 540 */           Integer.valueOf(4096));
/*     */         
/* 542 */         modifierKeywords = Collections.synchronizedMap(localHashMap);
/*     */       }
/*     */     }
/*     */     
/* 546 */     ??? = localStringTokenizer.countTokens();
/*     */     
/* 548 */     for (Object localObject1 = 1; localObject1 <= ???; localObject1++) {
/* 549 */       String str = localStringTokenizer.nextToken();
/*     */       
/* 551 */       if (j != 0) {
/* 552 */         if ((str.length() != 1) || (localObject1 != ???)) {
/* 553 */           throw new IllegalArgumentException("String formatted incorrectly");
/*     */         }
/* 555 */         return getCachedStroke(str.charAt(0), 0, i, false);
/*     */       }
/*     */       
/*     */       Object localObject3;
/* 559 */       if ((k != 0) || (bool) || (localObject1 == ???)) {
/* 560 */         if (localObject1 != ???) {
/* 561 */           throw new IllegalArgumentException("String formatted incorrectly");
/*     */         }
/*     */         
/* 564 */         localObject3 = "VK_" + str;
/* 565 */         int m = getVKValue((String)localObject3);
/*     */         
/* 567 */         return getCachedStroke(65535, m, i, bool);
/*     */       }
/*     */       
/*     */ 
/* 571 */       if (str.equals("released")) {
/* 572 */         bool = true;
/*     */ 
/*     */       }
/* 575 */       else if (str.equals("pressed")) {
/* 576 */         k = 1;
/*     */ 
/*     */       }
/* 579 */       else if (str.equals("typed")) {
/* 580 */         j = 1;
/*     */       }
/*     */       else
/*     */       {
/* 584 */         localObject3 = (Integer)modifierKeywords.get(str);
/* 585 */         if (localObject3 != null) {
/* 586 */           i |= ((Integer)localObject3).intValue();
/*     */         } else {
/* 588 */           throw new IllegalArgumentException("String formatted incorrectly");
/*     */         }
/*     */       }
/*     */     }
/* 592 */     throw new IllegalArgumentException("String formatted incorrectly");
/*     */   }
/*     */   
/*     */   private static VKCollection getVKCollection() {
/* 596 */     if (vks == null) {
/* 597 */       vks = new VKCollection();
/*     */     }
/* 599 */     return vks;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private static int getVKValue(String paramString)
/*     */   {
/* 608 */     VKCollection localVKCollection = getVKCollection();
/*     */     
/* 610 */     Integer localInteger = localVKCollection.findCode(paramString);
/*     */     
/* 612 */     if (localInteger == null) {
/* 613 */       int i = 0;
/*     */       
/*     */       try
/*     */       {
/* 617 */         i = KeyEvent.class.getField(paramString).getInt(KeyEvent.class);
/*     */       } catch (NoSuchFieldException localNoSuchFieldException) {
/* 619 */         throw new IllegalArgumentException("String formatted incorrectly");
/*     */       } catch (IllegalAccessException localIllegalAccessException) {
/* 621 */         throw new IllegalArgumentException("String formatted incorrectly");
/*     */       }
/* 623 */       localInteger = Integer.valueOf(i);
/* 624 */       localVKCollection.put(paramString, localInteger);
/*     */     }
/* 626 */     return localInteger.intValue();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public final char getKeyChar()
/*     */   {
/* 637 */     return this.keyChar;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public final int getKeyCode()
/*     */   {
/* 648 */     return this.keyCode;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public final int getModifiers()
/*     */   {
/* 658 */     return this.modifiers;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public final boolean isOnKeyRelease()
/*     */   {
/* 669 */     return this.onKeyRelease;
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
/*     */   public final int getKeyEventType()
/*     */   {
/* 682 */     if (this.keyCode == 0) {
/* 683 */       return 400;
/*     */     }
/* 685 */     return this.onKeyRelease ? 402 : 401;
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
/*     */   public int hashCode()
/*     */   {
/* 698 */     return (this.keyChar + '\001') * (2 * (this.keyCode + 1)) * (this.modifiers + 1) + (this.onKeyRelease ? 1 : 2);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public final boolean equals(Object paramObject)
/*     */   {
/* 709 */     if ((paramObject instanceof AWTKeyStroke)) {
/* 710 */       AWTKeyStroke localAWTKeyStroke = (AWTKeyStroke)paramObject;
/* 711 */       return (localAWTKeyStroke.keyChar == this.keyChar) && (localAWTKeyStroke.keyCode == this.keyCode) && (localAWTKeyStroke.onKeyRelease == this.onKeyRelease) && (localAWTKeyStroke.modifiers == this.modifiers);
/*     */     }
/*     */     
/*     */ 
/* 715 */     return false;
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
/*     */   public String toString()
/*     */   {
/* 728 */     if (this.keyCode == 0) {
/* 729 */       return getModifiersText(this.modifiers) + "typed " + this.keyChar;
/*     */     }
/*     */     
/*     */ 
/* 733 */     return getModifiersText(this.modifiers) + (this.onKeyRelease ? "released" : "pressed") + " " + getVKText(this.keyCode);
/*     */   }
/*     */   
/*     */   static String getModifiersText(int paramInt)
/*     */   {
/* 738 */     StringBuilder localStringBuilder = new StringBuilder();
/*     */     
/* 740 */     if ((paramInt & 0x40) != 0) {
/* 741 */       localStringBuilder.append("shift ");
/*     */     }
/* 743 */     if ((paramInt & 0x80) != 0) {
/* 744 */       localStringBuilder.append("ctrl ");
/*     */     }
/* 746 */     if ((paramInt & 0x100) != 0) {
/* 747 */       localStringBuilder.append("meta ");
/*     */     }
/* 749 */     if ((paramInt & 0x200) != 0) {
/* 750 */       localStringBuilder.append("alt ");
/*     */     }
/* 752 */     if ((paramInt & 0x2000) != 0) {
/* 753 */       localStringBuilder.append("altGraph ");
/*     */     }
/* 755 */     if ((paramInt & 0x400) != 0) {
/* 756 */       localStringBuilder.append("button1 ");
/*     */     }
/* 758 */     if ((paramInt & 0x800) != 0) {
/* 759 */       localStringBuilder.append("button2 ");
/*     */     }
/* 761 */     if ((paramInt & 0x1000) != 0) {
/* 762 */       localStringBuilder.append("button3 ");
/*     */     }
/*     */     
/* 765 */     return localStringBuilder.toString();
/*     */   }
/*     */   
/*     */   static String getVKText(int paramInt) {
/* 769 */     VKCollection localVKCollection = getVKCollection();
/* 770 */     Integer localInteger = Integer.valueOf(paramInt);
/* 771 */     String str = localVKCollection.findName(localInteger);
/* 772 */     if (str != null) {
/* 773 */       return str.substring(3);
/*     */     }
/* 775 */     int i = 25;
/*     */     
/*     */ 
/* 778 */     Field[] arrayOfField = KeyEvent.class.getDeclaredFields();
/* 779 */     for (int j = 0; j < arrayOfField.length; j++) {
/*     */       try {
/* 781 */         if ((arrayOfField[j].getModifiers() == i) && 
/* 782 */           (arrayOfField[j].getType() == Integer.TYPE) && 
/* 783 */           (arrayOfField[j].getName().startsWith("VK_")) && 
/* 784 */           (arrayOfField[j].getInt(KeyEvent.class) == paramInt))
/*     */         {
/* 786 */           str = arrayOfField[j].getName();
/* 787 */           localVKCollection.put(str, localInteger);
/* 788 */           return str.substring(3);
/*     */         }
/*     */       } catch (IllegalAccessException localIllegalAccessException) {
/* 791 */         if (!$assertionsDisabled) throw new AssertionError();
/*     */       }
/*     */     }
/* 794 */     return "UNKNOWN";
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   protected Object readResolve()
/*     */     throws ObjectStreamException
/*     */   {
/* 804 */     synchronized (AWTKeyStroke.class) {
/* 805 */       if (getClass().equals(getAWTKeyStrokeClass())) {
/* 806 */         return getCachedStroke(this.keyChar, this.keyCode, this.modifiers, this.onKeyRelease);
/*     */       }
/*     */     }
/* 809 */     return this;
/*     */   }
/*     */   
/*     */   private static int mapOldModifiers(int paramInt) {
/* 813 */     if ((paramInt & 0x1) != 0) {
/* 814 */       paramInt |= 0x40;
/*     */     }
/* 816 */     if ((paramInt & 0x8) != 0) {
/* 817 */       paramInt |= 0x200;
/*     */     }
/* 819 */     if ((paramInt & 0x20) != 0) {
/* 820 */       paramInt |= 0x2000;
/*     */     }
/* 822 */     if ((paramInt & 0x2) != 0) {
/* 823 */       paramInt |= 0x80;
/*     */     }
/* 825 */     if ((paramInt & 0x4) != 0) {
/* 826 */       paramInt |= 0x100;
/*     */     }
/*     */     
/* 829 */     paramInt &= 0x3FC0;
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 838 */     return paramInt;
/*     */   }
/*     */   
/*     */   private static int mapNewModifiers(int paramInt) {
/* 842 */     if ((paramInt & 0x40) != 0) {
/* 843 */       paramInt |= 0x1;
/*     */     }
/* 845 */     if ((paramInt & 0x200) != 0) {
/* 846 */       paramInt |= 0x8;
/*     */     }
/* 848 */     if ((paramInt & 0x2000) != 0) {
/* 849 */       paramInt |= 0x20;
/*     */     }
/* 851 */     if ((paramInt & 0x80) != 0) {
/* 852 */       paramInt |= 0x2;
/*     */     }
/* 854 */     if ((paramInt & 0x100) != 0) {
/* 855 */       paramInt |= 0x4;
/*     */     }
/*     */     
/* 858 */     return paramInt;
/*     */   }
/*     */   
/*     */   protected AWTKeyStroke() {}
/*     */ }


/* Location:              /Users/guanxiaoda/Documents/workspace/rt.jar!/java/awt/AWTKeyStroke.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */