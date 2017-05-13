/*      */ package java.awt.event;
/*      */ 
/*      */ import java.awt.Component;
/*      */ import java.awt.GraphicsEnvironment;
/*      */ import java.awt.IllegalComponentStateException;
/*      */ import java.awt.Point;
/*      */ import java.awt.Toolkit;
/*      */ import java.io.IOException;
/*      */ import java.io.ObjectInputStream;
/*      */ import sun.awt.SunToolkit;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class MouseEvent
/*      */   extends InputEvent
/*      */ {
/*      */   public static final int MOUSE_FIRST = 500;
/*      */   public static final int MOUSE_LAST = 507;
/*      */   public static final int MOUSE_CLICKED = 500;
/*      */   public static final int MOUSE_PRESSED = 501;
/*      */   public static final int MOUSE_RELEASED = 502;
/*      */   public static final int MOUSE_MOVED = 503;
/*      */   public static final int MOUSE_ENTERED = 504;
/*      */   public static final int MOUSE_EXITED = 505;
/*      */   public static final int MOUSE_DRAGGED = 506;
/*      */   public static final int MOUSE_WHEEL = 507;
/*      */   public static final int NOBUTTON = 0;
/*      */   public static final int BUTTON1 = 1;
/*      */   public static final int BUTTON2 = 2;
/*      */   public static final int BUTTON3 = 3;
/*      */   int x;
/*      */   int y;
/*      */   private int xAbs;
/*      */   private int yAbs;
/*      */   int clickCount;
/*      */   int button;
/*  376 */   boolean popupTrigger = false;
/*      */   
/*      */ 
/*      */   private static final long serialVersionUID = -991214153494842848L;
/*      */   
/*      */ 
/*      */   private static int cachedNumberOfButtons;
/*      */   
/*      */ 
/*      */ 
/*      */   static
/*      */   {
/*      */     
/*      */     
/*      */ 
/*  391 */     if (!GraphicsEnvironment.isHeadless()) {
/*  392 */       initIDs();
/*      */     }
/*  394 */     Toolkit localToolkit = Toolkit.getDefaultToolkit();
/*  395 */     if ((localToolkit instanceof SunToolkit)) {
/*  396 */       cachedNumberOfButtons = ((SunToolkit)localToolkit).getNumberOfButtons();
/*      */     }
/*      */     else
/*      */     {
/*  400 */       cachedNumberOfButtons = 3;
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
/*      */   public Point getLocationOnScreen()
/*      */   {
/*  425 */     return new Point(this.xAbs, this.yAbs);
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
/*      */   public int getXOnScreen()
/*      */   {
/*  442 */     return this.xAbs;
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
/*      */   public int getYOnScreen()
/*      */   {
/*  459 */     return this.yAbs;
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
/*      */   public MouseEvent(Component paramComponent, int paramInt1, long paramLong, int paramInt2, int paramInt3, int paramInt4, int paramInt5, boolean paramBoolean, int paramInt6)
/*      */   {
/*  555 */     this(paramComponent, paramInt1, paramLong, paramInt2, paramInt3, paramInt4, 0, 0, paramInt5, paramBoolean, paramInt6);
/*  556 */     Point localPoint = new Point(0, 0);
/*      */     try {
/*  558 */       localPoint = paramComponent.getLocationOnScreen();
/*  559 */       this.xAbs = (localPoint.x + paramInt3);
/*  560 */       this.yAbs = (localPoint.y + paramInt4);
/*      */     } catch (IllegalComponentStateException localIllegalComponentStateException) {
/*  562 */       this.xAbs = 0;
/*  563 */       this.yAbs = 0;
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public MouseEvent(Component paramComponent, int paramInt1, long paramLong, int paramInt2, int paramInt3, int paramInt4, int paramInt5, boolean paramBoolean)
/*      */   {
/*  620 */     this(paramComponent, paramInt1, paramLong, paramInt2, paramInt3, paramInt4, paramInt5, paramBoolean, 0);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*  626 */   private transient boolean shouldExcludeButtonFromExtModifiers = false;
/*      */   
/*      */ 
/*      */ 
/*      */   public int getModifiersEx()
/*      */   {
/*  632 */     int i = this.modifiers;
/*  633 */     if (this.shouldExcludeButtonFromExtModifiers) {
/*  634 */       i &= (InputEvent.getMaskForButton(getButton()) ^ 0xFFFFFFFF);
/*      */     }
/*  636 */     return i & 0xFFFFFFC0;
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
/*      */   public MouseEvent(Component paramComponent, int paramInt1, long paramLong, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6, int paramInt7, boolean paramBoolean, int paramInt8)
/*      */   {
/*  736 */     super(paramComponent, paramInt1, paramLong, paramInt2);
/*  737 */     this.x = paramInt3;
/*  738 */     this.y = paramInt4;
/*  739 */     this.xAbs = paramInt5;
/*  740 */     this.yAbs = paramInt6;
/*  741 */     this.clickCount = paramInt7;
/*  742 */     this.popupTrigger = paramBoolean;
/*  743 */     if (paramInt8 < 0) {
/*  744 */       throw new IllegalArgumentException("Invalid button value :" + paramInt8);
/*      */     }
/*  746 */     if (paramInt8 > 3) {
/*  747 */       if (!Toolkit.getDefaultToolkit().areExtraMouseButtonsEnabled()) {
/*  748 */         throw new IllegalArgumentException("Extra mouse events are disabled " + paramInt8);
/*      */       }
/*  750 */       if (paramInt8 > cachedNumberOfButtons) {
/*  751 */         throw new IllegalArgumentException("Nonexistent button " + paramInt8);
/*      */       }
/*      */       
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  762 */       if ((getModifiersEx() != 0) && (
/*  763 */         (paramInt1 == 502) || (paramInt1 == 500))) {
/*  764 */         this.shouldExcludeButtonFromExtModifiers = true;
/*      */       }
/*      */     }
/*      */     
/*      */ 
/*  769 */     this.button = paramInt8;
/*      */     
/*  771 */     if ((getModifiers() != 0) && (getModifiersEx() == 0)) {
/*  772 */       setNewModifiers();
/*  773 */     } else if ((getModifiers() == 0) && 
/*  774 */       ((getModifiersEx() != 0) || (paramInt8 != 0)) && (paramInt8 <= 3))
/*      */     {
/*      */ 
/*  777 */       setOldModifiers();
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public int getX()
/*      */   {
/*  789 */     return this.x;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public int getY()
/*      */   {
/*  800 */     return this.y;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   public Point getPoint()
/*      */   {
/*      */     int i;
/*      */     
/*      */ 
/*      */     int j;
/*      */     
/*      */ 
/*  813 */     synchronized (this) {
/*  814 */       i = this.x;
/*  815 */       j = this.y;
/*      */     }
/*  817 */     return new Point(i, j);
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
/*      */   public synchronized void translatePoint(int paramInt1, int paramInt2)
/*      */   {
/*  831 */     this.x += paramInt1;
/*  832 */     this.y += paramInt2;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public int getClickCount()
/*      */   {
/*  841 */     return this.clickCount;
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
/*      */   public int getButton()
/*      */   {
/*  897 */     return this.button;
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
/*      */   public boolean isPopupTrigger()
/*      */   {
/*  913 */     return this.popupTrigger;
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
/*      */   public static String getMouseModifiersText(int paramInt)
/*      */   {
/*  942 */     StringBuilder localStringBuilder = new StringBuilder();
/*  943 */     if ((paramInt & 0x8) != 0) {
/*  944 */       localStringBuilder.append(Toolkit.getProperty("AWT.alt", "Alt"));
/*  945 */       localStringBuilder.append("+");
/*      */     }
/*  947 */     if ((paramInt & 0x4) != 0) {
/*  948 */       localStringBuilder.append(Toolkit.getProperty("AWT.meta", "Meta"));
/*  949 */       localStringBuilder.append("+");
/*      */     }
/*  951 */     if ((paramInt & 0x2) != 0) {
/*  952 */       localStringBuilder.append(Toolkit.getProperty("AWT.control", "Ctrl"));
/*  953 */       localStringBuilder.append("+");
/*      */     }
/*  955 */     if ((paramInt & 0x1) != 0) {
/*  956 */       localStringBuilder.append(Toolkit.getProperty("AWT.shift", "Shift"));
/*  957 */       localStringBuilder.append("+");
/*      */     }
/*  959 */     if ((paramInt & 0x20) != 0) {
/*  960 */       localStringBuilder.append(Toolkit.getProperty("AWT.altGraph", "Alt Graph"));
/*  961 */       localStringBuilder.append("+");
/*      */     }
/*  963 */     if ((paramInt & 0x10) != 0) {
/*  964 */       localStringBuilder.append(Toolkit.getProperty("AWT.button1", "Button1"));
/*  965 */       localStringBuilder.append("+");
/*      */     }
/*  967 */     if ((paramInt & 0x8) != 0) {
/*  968 */       localStringBuilder.append(Toolkit.getProperty("AWT.button2", "Button2"));
/*  969 */       localStringBuilder.append("+");
/*      */     }
/*  971 */     if ((paramInt & 0x4) != 0) {
/*  972 */       localStringBuilder.append(Toolkit.getProperty("AWT.button3", "Button3"));
/*  973 */       localStringBuilder.append("+");
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  983 */     for (int j = 1; j <= cachedNumberOfButtons; j++) {
/*  984 */       int i = InputEvent.getMaskForButton(j);
/*  985 */       if (((paramInt & i) != 0) && 
/*  986 */         (localStringBuilder.indexOf(Toolkit.getProperty("AWT.button" + j, "Button" + j)) == -1))
/*      */       {
/*  988 */         localStringBuilder.append(Toolkit.getProperty("AWT.button" + j, "Button" + j));
/*  989 */         localStringBuilder.append("+");
/*      */       }
/*      */     }
/*      */     
/*  993 */     if (localStringBuilder.length() > 0) {
/*  994 */       localStringBuilder.setLength(localStringBuilder.length() - 1);
/*      */     }
/*  996 */     return localStringBuilder.toString();
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public String paramString()
/*      */   {
/* 1006 */     StringBuilder localStringBuilder = new StringBuilder(80);
/*      */     
/* 1008 */     switch (this.id) {
/*      */     case 501: 
/* 1010 */       localStringBuilder.append("MOUSE_PRESSED");
/* 1011 */       break;
/*      */     case 502: 
/* 1013 */       localStringBuilder.append("MOUSE_RELEASED");
/* 1014 */       break;
/*      */     case 500: 
/* 1016 */       localStringBuilder.append("MOUSE_CLICKED");
/* 1017 */       break;
/*      */     case 504: 
/* 1019 */       localStringBuilder.append("MOUSE_ENTERED");
/* 1020 */       break;
/*      */     case 505: 
/* 1022 */       localStringBuilder.append("MOUSE_EXITED");
/* 1023 */       break;
/*      */     case 503: 
/* 1025 */       localStringBuilder.append("MOUSE_MOVED");
/* 1026 */       break;
/*      */     case 506: 
/* 1028 */       localStringBuilder.append("MOUSE_DRAGGED");
/* 1029 */       break;
/*      */     case 507: 
/* 1031 */       localStringBuilder.append("MOUSE_WHEEL");
/* 1032 */       break;
/*      */     default: 
/* 1034 */       localStringBuilder.append("unknown type");
/*      */     }
/*      */     
/*      */     
/* 1038 */     localStringBuilder.append(",(").append(this.x).append(",").append(this.y).append(")");
/* 1039 */     localStringBuilder.append(",absolute(").append(this.xAbs).append(",").append(this.yAbs).append(")");
/*      */     
/* 1041 */     if ((this.id != 506) && (this.id != 503)) {
/* 1042 */       localStringBuilder.append(",button=").append(getButton());
/*      */     }
/*      */     
/* 1045 */     if (getModifiers() != 0) {
/* 1046 */       localStringBuilder.append(",modifiers=").append(getMouseModifiersText(this.modifiers));
/*      */     }
/*      */     
/* 1049 */     if (getModifiersEx() != 0)
/*      */     {
/*      */ 
/* 1052 */       localStringBuilder.append(",extModifiers=").append(getModifiersExText(getModifiersEx()));
/*      */     }
/*      */     
/* 1055 */     localStringBuilder.append(",clickCount=").append(this.clickCount);
/*      */     
/* 1057 */     return localStringBuilder.toString();
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   private void setNewModifiers()
/*      */   {
/* 1065 */     if ((this.modifiers & 0x10) != 0) {
/* 1066 */       this.modifiers |= 0x400;
/*      */     }
/* 1068 */     if ((this.modifiers & 0x8) != 0) {
/* 1069 */       this.modifiers |= 0x800;
/*      */     }
/* 1071 */     if ((this.modifiers & 0x4) != 0) {
/* 1072 */       this.modifiers |= 0x1000;
/*      */     }
/* 1074 */     if ((this.id == 501) || (this.id == 502) || (this.id == 500))
/*      */     {
/*      */ 
/*      */ 
/* 1078 */       if ((this.modifiers & 0x10) != 0) {
/* 1079 */         this.button = 1;
/* 1080 */         this.modifiers &= 0xFFFFFFF3;
/* 1081 */         if (this.id != 501) {
/* 1082 */           this.modifiers &= 0xFBFF;
/*      */         }
/* 1084 */       } else if ((this.modifiers & 0x8) != 0) {
/* 1085 */         this.button = 2;
/* 1086 */         this.modifiers &= 0xFFFFFFEB;
/* 1087 */         if (this.id != 501) {
/* 1088 */           this.modifiers &= 0xF7FF;
/*      */         }
/* 1090 */       } else if ((this.modifiers & 0x4) != 0) {
/* 1091 */         this.button = 3;
/* 1092 */         this.modifiers &= 0xFFFFFFE7;
/* 1093 */         if (this.id != 501) {
/* 1094 */           this.modifiers &= 0xEFFF;
/*      */         }
/*      */       }
/*      */     }
/* 1098 */     if ((this.modifiers & 0x8) != 0) {
/* 1099 */       this.modifiers |= 0x200;
/*      */     }
/* 1101 */     if ((this.modifiers & 0x4) != 0) {
/* 1102 */       this.modifiers |= 0x100;
/*      */     }
/* 1104 */     if ((this.modifiers & 0x1) != 0) {
/* 1105 */       this.modifiers |= 0x40;
/*      */     }
/* 1107 */     if ((this.modifiers & 0x2) != 0) {
/* 1108 */       this.modifiers |= 0x80;
/*      */     }
/* 1110 */     if ((this.modifiers & 0x20) != 0) {
/* 1111 */       this.modifiers |= 0x2000;
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   private void setOldModifiers()
/*      */   {
/* 1119 */     if ((this.id == 501) || (this.id == 502) || (this.id == 500))
/*      */     {
/*      */ 
/*      */ 
/* 1123 */       switch (this.button) {
/*      */       case 1: 
/* 1125 */         this.modifiers |= 0x10;
/* 1126 */         break;
/*      */       case 2: 
/* 1128 */         this.modifiers |= 0x8;
/* 1129 */         break;
/*      */       case 3: 
/* 1131 */         this.modifiers |= 0x4;
/*      */       }
/*      */     }
/*      */     else {
/* 1135 */       if ((this.modifiers & 0x400) != 0) {
/* 1136 */         this.modifiers |= 0x10;
/*      */       }
/* 1138 */       if ((this.modifiers & 0x800) != 0) {
/* 1139 */         this.modifiers |= 0x8;
/*      */       }
/* 1141 */       if ((this.modifiers & 0x1000) != 0) {
/* 1142 */         this.modifiers |= 0x4;
/*      */       }
/*      */     }
/* 1145 */     if ((this.modifiers & 0x200) != 0) {
/* 1146 */       this.modifiers |= 0x8;
/*      */     }
/* 1148 */     if ((this.modifiers & 0x100) != 0) {
/* 1149 */       this.modifiers |= 0x4;
/*      */     }
/* 1151 */     if ((this.modifiers & 0x40) != 0) {
/* 1152 */       this.modifiers |= 0x1;
/*      */     }
/* 1154 */     if ((this.modifiers & 0x80) != 0) {
/* 1155 */       this.modifiers |= 0x2;
/*      */     }
/* 1157 */     if ((this.modifiers & 0x2000) != 0) {
/* 1158 */       this.modifiers |= 0x20;
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   private void readObject(ObjectInputStream paramObjectInputStream)
/*      */     throws IOException, ClassNotFoundException
/*      */   {
/* 1168 */     paramObjectInputStream.defaultReadObject();
/* 1169 */     if ((getModifiers() != 0) && (getModifiersEx() == 0)) {
/* 1170 */       setNewModifiers();
/*      */     }
/*      */   }
/*      */   
/*      */   private static native void initIDs();
/*      */ }


/* Location:              /Users/guanxiaoda/Documents/workspace/rt.jar!/java/awt/event/MouseEvent.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */