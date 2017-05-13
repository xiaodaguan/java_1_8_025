/*      */ package java.awt;
/*      */ 
/*      */ import java.awt.font.FontRenderContext;
/*      */ import java.awt.font.GlyphVector;
/*      */ import java.awt.font.LineMetrics;
/*      */ import java.awt.font.TextAttribute;
/*      */ import java.awt.font.TextLayout;
/*      */ import java.awt.geom.AffineTransform;
/*      */ import java.awt.geom.Point2D.Float;
/*      */ import java.awt.geom.Rectangle2D;
/*      */ import java.awt.geom.Rectangle2D.Float;
/*      */ import java.awt.peer.FontPeer;
/*      */ import java.io.File;
/*      */ import java.io.FileOutputStream;
/*      */ import java.io.FilePermission;
/*      */ import java.io.IOException;
/*      */ import java.io.InputStream;
/*      */ import java.io.ObjectInputStream;
/*      */ import java.io.ObjectOutputStream;
/*      */ import java.io.OutputStream;
/*      */ import java.io.Serializable;
/*      */ import java.lang.ref.SoftReference;
/*      */ import java.nio.file.Files;
/*      */ import java.nio.file.Path;
/*      */ import java.nio.file.attribute.FileAttribute;
/*      */ import java.security.PrivilegedExceptionAction;
/*      */ import java.text.AttributedCharacterIterator.Attribute;
/*      */ import java.text.CharacterIterator;
/*      */ import java.util.Hashtable;
/*      */ import java.util.Locale;
/*      */ import java.util.Map;
/*      */ import sun.font.AttributeMap;
/*      */ import sun.font.AttributeValues;
/*      */ import sun.font.CompositeFont;
/*      */ import sun.font.CoreMetrics;
/*      */ import sun.font.CreatedFontTracker;
/*      */ import sun.font.EAttribute;
/*      */ import sun.font.Font2D;
/*      */ import sun.font.Font2DHandle;
/*      */ import sun.font.FontAccess;
/*      */ import sun.font.FontLineMetrics;
/*      */ import sun.font.FontManager;
/*      */ import sun.font.FontManagerFactory;
/*      */ import sun.font.FontUtilities;
/*      */ import sun.font.GlyphLayout;
/*      */ import sun.font.StandardGlyphVector;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class Font
/*      */   implements Serializable
/*      */ {
/*      */   private Hashtable<Object, Object> fRequestedAttributes;
/*      */   public static final String DIALOG = "Dialog";
/*      */   public static final String DIALOG_INPUT = "DialogInput";
/*      */   public static final String SANS_SERIF = "SansSerif";
/*      */   public static final String SERIF = "Serif";
/*      */   public static final String MONOSPACED = "Monospaced";
/*      */   public static final int PLAIN = 0;
/*      */   public static final int BOLD = 1;
/*      */   public static final int ITALIC = 2;
/*      */   public static final int ROMAN_BASELINE = 0;
/*      */   public static final int CENTER_BASELINE = 1;
/*      */   public static final int HANGING_BASELINE = 2;
/*      */   public static final int TRUETYPE_FONT = 0;
/*      */   public static final int TYPE1_FONT = 1;
/*      */   protected String name;
/*      */   protected int style;
/*      */   protected int size;
/*      */   protected float pointSize;
/*      */   private transient FontPeer peer;
/*      */   private transient long pData;
/*      */   private transient Font2DHandle font2DHandle;
/*      */   private transient AttributeValues values;
/*      */   private transient boolean hasLayoutAttributes;
/*      */   
/*      */   private static class FontAccessImpl
/*      */     extends FontAccess
/*      */   {
/*      */     public Font2D getFont2D(Font paramFont)
/*      */     {
/*  228 */       return paramFont.getFont2D();
/*      */     }
/*      */     
/*      */     public void setFont2D(Font paramFont, Font2DHandle paramFont2DHandle) {
/*  232 */       paramFont.font2DHandle = paramFont2DHandle;
/*      */     }
/*      */     
/*      */     public void setCreatedFont(Font paramFont) {
/*  236 */       paramFont.createdFont = true;
/*      */     }
/*      */     
/*      */     public boolean isCreatedFont(Font paramFont) {
/*  240 */       return paramFont.createdFont;
/*      */     }
/*      */   }
/*      */   
/*      */   static
/*      */   {
/*  246 */     Toolkit.loadLibraries();
/*  247 */     initIDs();
/*  248 */     FontAccess.setFontAccess(new FontAccessImpl(null));
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
/*  416 */   private transient boolean createdFont = false;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private transient boolean nonIdentityTx;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  429 */   private static final AffineTransform identityTx = new AffineTransform();
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private static final long serialVersionUID = -4206021311591459213L;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   @Deprecated
/*      */   public FontPeer getPeer()
/*      */   {
/*  444 */     return getPeer_NoClientCode();
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   final FontPeer getPeer_NoClientCode()
/*      */   {
/*  452 */     if (this.peer == null) {
/*  453 */       Toolkit localToolkit = Toolkit.getDefaultToolkit();
/*  454 */       this.peer = localToolkit.getFontPeer(this.name, this.style);
/*      */     }
/*  456 */     return this.peer;
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
/*      */   private AttributeValues getAttributeValues()
/*      */   {
/*  471 */     if (this.values == null) {
/*  472 */       AttributeValues localAttributeValues = new AttributeValues();
/*  473 */       localAttributeValues.setFamily(this.name);
/*  474 */       localAttributeValues.setSize(this.pointSize);
/*      */       
/*  476 */       if ((this.style & 0x1) != 0) {
/*  477 */         localAttributeValues.setWeight(2.0F);
/*      */       }
/*      */       
/*  480 */       if ((this.style & 0x2) != 0) {
/*  481 */         localAttributeValues.setPosture(0.2F);
/*      */       }
/*  483 */       localAttributeValues.defineAll(PRIMARY_MASK);
/*  484 */       this.values = localAttributeValues;
/*      */     }
/*      */     
/*  487 */     return this.values;
/*      */   }
/*      */   
/*      */   private Font2D getFont2D() {
/*  491 */     FontManager localFontManager = FontManagerFactory.getInstance();
/*  492 */     if ((localFontManager.usingPerAppContextComposites()) && (this.font2DHandle != null) && ((this.font2DHandle.font2D instanceof CompositeFont)))
/*      */     {
/*      */ 
/*  495 */       if (((CompositeFont)this.font2DHandle.font2D).isStdComposite())
/*  496 */         return localFontManager.findFont2D(this.name, this.style, 2);
/*      */     }
/*  498 */     if (this.font2DHandle == null)
/*      */     {
/*  500 */       this.font2DHandle = localFontManager.findFont2D(this.name, this.style, 2).handle;
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  507 */     return this.font2DHandle.font2D;
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
/*      */   public Font(String paramString, int paramInt1, int paramInt2)
/*      */   {
/*  568 */     this.name = (paramString != null ? paramString : "Default");
/*  569 */     this.style = ((paramInt1 & 0xFFFFFFFC) == 0 ? paramInt1 : 0);
/*  570 */     this.size = paramInt2;
/*  571 */     this.pointSize = paramInt2;
/*      */   }
/*      */   
/*      */   private Font(String paramString, int paramInt, float paramFloat) {
/*  575 */     this.name = (paramString != null ? paramString : "Default");
/*  576 */     this.style = ((paramInt & 0xFFFFFFFC) == 0 ? paramInt : 0);
/*  577 */     this.size = ((int)(paramFloat + 0.5D));
/*  578 */     this.pointSize = paramFloat;
/*      */   }
/*      */   
/*      */ 
/*      */   private Font(String paramString, int paramInt, float paramFloat, boolean paramBoolean, Font2DHandle paramFont2DHandle)
/*      */   {
/*  584 */     this(paramString, paramInt, paramFloat);
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  595 */     if (paramBoolean) {
/*  596 */       if (((paramFont2DHandle.font2D instanceof CompositeFont)) && 
/*  597 */         (paramFont2DHandle.font2D.getStyle() != paramInt)) {
/*  598 */         FontManager localFontManager = FontManagerFactory.getInstance();
/*  599 */         this.font2DHandle = localFontManager.getNewComposite(null, paramInt, paramFont2DHandle);
/*      */       } else {
/*  601 */         this.font2DHandle = paramFont2DHandle;
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */   private Font(File paramFile, int paramInt, boolean paramBoolean, CreatedFontTracker paramCreatedFontTracker)
/*      */     throws FontFormatException
/*      */   {
/*  610 */     this.createdFont = true;
/*      */     
/*      */ 
/*      */ 
/*  614 */     FontManager localFontManager = FontManagerFactory.getInstance();
/*  615 */     this.font2DHandle = localFontManager.createFont2D(paramFile, paramInt, paramBoolean, paramCreatedFontTracker).handle;
/*      */     
/*  617 */     this.name = this.font2DHandle.font2D.getFontName(Locale.getDefault());
/*  618 */     this.style = 0;
/*  619 */     this.size = 1;
/*  620 */     this.pointSize = 1.0F;
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
/*      */   private Font(AttributeValues paramAttributeValues, String paramString, int paramInt, boolean paramBoolean, Font2DHandle paramFont2DHandle)
/*      */   {
/*  649 */     this.createdFont = paramBoolean;
/*  650 */     if (paramBoolean) {
/*  651 */       this.font2DHandle = paramFont2DHandle;
/*      */       
/*  653 */       String str = null;
/*  654 */       if (paramString != null) {
/*  655 */         str = paramAttributeValues.getFamily();
/*  656 */         if (paramString.equals(str)) str = null;
/*      */       }
/*  658 */       int i = 0;
/*  659 */       if (paramInt == -1) {
/*  660 */         i = -1;
/*      */       } else {
/*  662 */         if (paramAttributeValues.getWeight() >= 2.0F) i = 1;
/*  663 */         if (paramAttributeValues.getPosture() >= 0.2F) i |= 0x2;
/*  664 */         if (paramInt == i) i = -1;
/*      */       }
/*  666 */       if ((paramFont2DHandle.font2D instanceof CompositeFont)) {
/*  667 */         if ((i != -1) || (str != null)) {
/*  668 */           FontManager localFontManager = FontManagerFactory.getInstance();
/*      */           
/*  670 */           this.font2DHandle = localFontManager.getNewComposite(str, i, paramFont2DHandle);
/*      */         }
/*  672 */       } else if (str != null) {
/*  673 */         this.createdFont = false;
/*  674 */         this.font2DHandle = null;
/*      */       }
/*      */     }
/*  677 */     initFromValues(paramAttributeValues);
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
/*      */   public Font(Map<? extends AttributedCharacterIterator.Attribute, ?> paramMap)
/*      */   {
/*  695 */     initFromValues(AttributeValues.fromMap(paramMap, RECOGNIZED_MASK));
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   protected Font(Font paramFont)
/*      */   {
/*  706 */     if (paramFont.values != null) {
/*  707 */       initFromValues(paramFont.getAttributeValues().clone());
/*      */     } else {
/*  709 */       this.name = paramFont.name;
/*  710 */       this.style = paramFont.style;
/*  711 */       this.size = paramFont.size;
/*  712 */       this.pointSize = paramFont.pointSize;
/*      */     }
/*  714 */     this.font2DHandle = paramFont.font2DHandle;
/*  715 */     this.createdFont = paramFont.createdFont;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*  721 */   private static final int RECOGNIZED_MASK = AttributeValues.MASK_ALL & 
/*  722 */     (AttributeValues.getMask(EAttribute.EFONT) ^ 0xFFFFFFFF);
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  728 */   private static final int PRIMARY_MASK = AttributeValues.getMask(new EAttribute[] { EAttribute.EFAMILY, EAttribute.EWEIGHT, EAttribute.EWIDTH, EAttribute.EPOSTURE, EAttribute.ESIZE, EAttribute.ETRANSFORM, EAttribute.ESUPERSCRIPT, EAttribute.ETRACKING });
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  734 */   private static final int SECONDARY_MASK = RECOGNIZED_MASK & (PRIMARY_MASK ^ 0xFFFFFFFF);
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  741 */   private static final int LAYOUT_MASK = AttributeValues.getMask(new EAttribute[] { EAttribute.ECHAR_REPLACEMENT, EAttribute.EFOREGROUND, EAttribute.EBACKGROUND, EAttribute.EUNDERLINE, EAttribute.ESTRIKETHROUGH, EAttribute.ERUN_DIRECTION, EAttribute.EBIDI_EMBEDDING, EAttribute.EJUSTIFICATION, EAttribute.EINPUT_METHOD_HIGHLIGHT, EAttribute.EINPUT_METHOD_UNDERLINE, EAttribute.ESWAP_COLORS, EAttribute.ENUMERIC_SHAPING, EAttribute.EKERNING, EAttribute.ELIGATURES, EAttribute.ETRACKING, EAttribute.ESUPERSCRIPT });
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  749 */   private static final int EXTRA_MASK = AttributeValues.getMask(new EAttribute[] { EAttribute.ETRANSFORM, EAttribute.ESUPERSCRIPT, EAttribute.EWIDTH });
/*      */   
/*      */ 
/*      */ 
/*      */   private void initFromValues(AttributeValues paramAttributeValues)
/*      */   {
/*  755 */     this.values = paramAttributeValues;
/*  756 */     paramAttributeValues.defineAll(PRIMARY_MASK);
/*      */     
/*  758 */     this.name = paramAttributeValues.getFamily();
/*  759 */     this.pointSize = paramAttributeValues.getSize();
/*  760 */     this.size = ((int)(paramAttributeValues.getSize() + 0.5D));
/*  761 */     if (paramAttributeValues.getWeight() >= 2.0F) this.style |= 0x1;
/*  762 */     if (paramAttributeValues.getPosture() >= 0.2F) { this.style |= 0x2;
/*      */     }
/*  764 */     this.nonIdentityTx = paramAttributeValues.anyNonDefault(EXTRA_MASK);
/*  765 */     this.hasLayoutAttributes = paramAttributeValues.anyNonDefault(LAYOUT_MASK);
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
/*      */   public static Font getFont(Map<? extends AttributedCharacterIterator.Attribute, ?> paramMap)
/*      */   {
/*      */     Object localObject2;
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  790 */     if (((paramMap instanceof AttributeMap)) && 
/*  791 */       (((AttributeMap)paramMap).getValues() != null)) {
/*  792 */       localObject1 = ((AttributeMap)paramMap).getValues();
/*  793 */       if (((AttributeValues)localObject1).isNonDefault(EAttribute.EFONT)) {
/*  794 */         localObject2 = ((AttributeValues)localObject1).getFont();
/*  795 */         if (!((AttributeValues)localObject1).anyDefined(SECONDARY_MASK)) {
/*  796 */           return (Font)localObject2;
/*      */         }
/*      */         
/*  799 */         localObject1 = ((Font)localObject2).getAttributeValues().clone();
/*  800 */         ((AttributeValues)localObject1).merge(paramMap, SECONDARY_MASK);
/*  801 */         return new Font((AttributeValues)localObject1, ((Font)localObject2).name, ((Font)localObject2).style, ((Font)localObject2).createdFont, ((Font)localObject2).font2DHandle);
/*      */       }
/*      */       
/*  804 */       return new Font(paramMap);
/*      */     }
/*      */     
/*  807 */     Object localObject1 = (Font)paramMap.get(TextAttribute.FONT);
/*  808 */     if (localObject1 != null) {
/*  809 */       if (paramMap.size() > 1) {
/*  810 */         localObject2 = ((Font)localObject1).getAttributeValues().clone();
/*  811 */         ((AttributeValues)localObject2).merge(paramMap, SECONDARY_MASK);
/*  812 */         return new Font((AttributeValues)localObject2, ((Font)localObject1).name, ((Font)localObject1).style, ((Font)localObject1).createdFont, ((Font)localObject1).font2DHandle);
/*      */       }
/*      */       
/*      */ 
/*  816 */       return (Font)localObject1;
/*      */     }
/*      */     
/*  819 */     return new Font(paramMap);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private static boolean hasTempPermission()
/*      */   {
/*  829 */     if (System.getSecurityManager() == null) {
/*  830 */       return true;
/*      */     }
/*  832 */     File localFile = null;
/*  833 */     boolean bool = false;
/*      */     try {
/*  835 */       localFile = Files.createTempFile("+~JT", ".tmp", new FileAttribute[0]).toFile();
/*  836 */       localFile.delete();
/*  837 */       localFile = null;
/*  838 */       bool = true;
/*      */     }
/*      */     catch (Throwable localThrowable) {}
/*      */     
/*  842 */     return bool;
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
/*      */   public static Font createFont(int paramInt, InputStream paramInputStream)
/*      */     throws FontFormatException, IOException
/*      */   {
/*  876 */     if (hasTempPermission()) {
/*  877 */       return createFont0(paramInt, paramInputStream, null);
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*  882 */     CreatedFontTracker localCreatedFontTracker = CreatedFontTracker.getTracker();
/*  883 */     boolean bool = false;
/*      */     try {
/*  885 */       bool = localCreatedFontTracker.acquirePermit();
/*  886 */       if (!bool) {
/*  887 */         throw new IOException("Timed out waiting for resources.");
/*      */       }
/*  889 */       return createFont0(paramInt, paramInputStream, localCreatedFontTracker);
/*      */     } catch (InterruptedException localInterruptedException) {
/*  891 */       throw new IOException("Problem reading font data.");
/*      */     } finally {
/*  893 */       if (bool) {
/*  894 */         localCreatedFontTracker.releasePermit();
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static Font createFont(int paramInt, File paramFile)
/*      */     throws FontFormatException, IOException
/*      */   {
/* 1041 */     paramFile = new File(paramFile.getPath());
/*      */     
/* 1043 */     if ((paramInt != 0) && (paramInt != 1))
/*      */     {
/* 1045 */       throw new IllegalArgumentException("font format not recognized");
/*      */     }
/* 1047 */     SecurityManager localSecurityManager = System.getSecurityManager();
/* 1048 */     if (localSecurityManager != null)
/*      */     {
/* 1050 */       FilePermission localFilePermission = new FilePermission(paramFile.getPath(), "read");
/* 1051 */       localSecurityManager.checkPermission(localFilePermission);
/*      */     }
/* 1053 */     if (!paramFile.canRead()) {
/* 1054 */       throw new IOException("Can't read " + paramFile);
/*      */     }
/* 1056 */     return new Font(paramFile, paramInt, false, null);
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
/*      */   public AffineTransform getTransform()
/*      */   {
/* 1087 */     if (this.nonIdentityTx) {
/* 1088 */       AttributeValues localAttributeValues = getAttributeValues();
/*      */       
/*      */ 
/* 1091 */       AffineTransform localAffineTransform = localAttributeValues.isNonDefault(EAttribute.ETRANSFORM) ? new AffineTransform(localAttributeValues.getTransform()) : new AffineTransform();
/*      */       
/*      */ 
/* 1094 */       if (localAttributeValues.getSuperscript() != 0)
/*      */       {
/*      */ 
/*      */ 
/*      */ 
/* 1099 */         int i = localAttributeValues.getSuperscript();
/*      */         
/* 1101 */         double d1 = 0.0D;
/* 1102 */         int j = 0;
/* 1103 */         int k = i > 0 ? 1 : 0;
/* 1104 */         int m = k != 0 ? -1 : 1;
/* 1105 */         int n = k != 0 ? i : -i;
/*      */         
/* 1107 */         while ((n & 0x7) > j) {
/* 1108 */           int i1 = n & 0x7;
/* 1109 */           d1 += m * (ssinfo[i1] - ssinfo[j]);
/* 1110 */           n >>= 3;
/* 1111 */           m = -m;
/* 1112 */           j = i1;
/*      */         }
/* 1114 */         d1 *= this.pointSize;
/* 1115 */         double d2 = Math.pow(0.6666666666666666D, j);
/*      */         
/* 1117 */         localAffineTransform.preConcatenate(AffineTransform.getTranslateInstance(0.0D, d1));
/* 1118 */         localAffineTransform.scale(d2, d2);
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1134 */       if (localAttributeValues.isNonDefault(EAttribute.EWIDTH)) {
/* 1135 */         localAffineTransform.scale(localAttributeValues.getWidth(), 1.0D);
/*      */       }
/*      */       
/* 1138 */       return localAffineTransform;
/*      */     }
/*      */     
/* 1141 */     return new AffineTransform();
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
/* 1155 */   private static final float[] ssinfo = { 0.0F, 0.375F, 0.625F, 0.7916667F, 0.9027778F, 0.9768519F, 1.0262346F, 1.0591564F };
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   transient int hash;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public String getFamily()
/*      */   {
/* 1186 */     return getFamily_NoClientCode();
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   final String getFamily_NoClientCode()
/*      */   {
/* 1194 */     return getFamily(Locale.getDefault());
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
/*      */   public String getFamily(Locale paramLocale)
/*      */   {
/* 1217 */     if (paramLocale == null) {
/* 1218 */       throw new NullPointerException("null locale doesn't mean default");
/*      */     }
/* 1220 */     return getFont2D().getFamilyName(paramLocale);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public String getPSName()
/*      */   {
/* 1232 */     return getFont2D().getPostscriptName();
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
/*      */   public String getName()
/*      */   {
/* 1246 */     return this.name;
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
/*      */   public String getFontName()
/*      */   {
/* 1261 */     return getFontName(Locale.getDefault());
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
/*      */   public String getFontName(Locale paramLocale)
/*      */   {
/* 1276 */     if (paramLocale == null) {
/* 1277 */       throw new NullPointerException("null locale doesn't mean default");
/*      */     }
/* 1279 */     return getFont2D().getFontName(paramLocale);
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
/*      */   public int getStyle()
/*      */   {
/* 1292 */     return this.style;
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
/*      */   public int getSize()
/*      */   {
/* 1318 */     return this.size;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public float getSize2D()
/*      */   {
/* 1330 */     return this.pointSize;
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
/*      */   public boolean isPlain()
/*      */   {
/* 1343 */     return this.style == 0;
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
/*      */   public boolean isBold()
/*      */   {
/* 1356 */     return (this.style & 0x1) != 0;
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
/*      */   public boolean isItalic()
/*      */   {
/* 1369 */     return (this.style & 0x2) != 0;
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
/*      */   public boolean isTransformed()
/*      */   {
/* 1383 */     return this.nonIdentityTx;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public boolean hasLayoutAttributes()
/*      */   {
/* 1393 */     return this.hasLayoutAttributes;
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
/*      */   public static Font getFont(String paramString)
/*      */   {
/* 1413 */     return getFont(paramString, null);
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
/*      */   public static Font decode(String paramString)
/*      */   {
/* 1490 */     String str1 = paramString;
/* 1491 */     String str2 = "";
/* 1492 */     int i = 12;
/* 1493 */     int j = 0;
/*      */     
/* 1495 */     if (paramString == null) {
/* 1496 */       return new Font("Dialog", j, i);
/*      */     }
/*      */     
/* 1499 */     int k = paramString.lastIndexOf('-');
/* 1500 */     int m = paramString.lastIndexOf(' ');
/* 1501 */     int n = k > m ? 45 : 32;
/* 1502 */     NumberFormatException localNumberFormatException1 = paramString.lastIndexOf(n);
/* 1503 */     NumberFormatException localNumberFormatException2 = paramString.lastIndexOf(n, localNumberFormatException1 - 1);
/* 1504 */     NumberFormatException localNumberFormatException3 = paramString.length();
/*      */     
/* 1506 */     if ((localNumberFormatException1 > 0) && (localNumberFormatException1 + 1 < localNumberFormatException3)) {
/*      */       try
/*      */       {
/* 1509 */         i = Integer.valueOf(paramString.substring(localNumberFormatException1 + 1)).intValue();
/* 1510 */         if (i <= 0) {
/* 1511 */           i = 12;
/*      */         }
/*      */       }
/*      */       catch (NumberFormatException localNumberFormatException4)
/*      */       {
/* 1516 */         localNumberFormatException2 = localNumberFormatException1;
/* 1517 */         localNumberFormatException1 = localNumberFormatException3;
/* 1518 */         if (paramString.charAt(localNumberFormatException1 - 1) == n) {
/* 1519 */           localNumberFormatException1--;
/*      */         }
/*      */       }
/*      */     }
/*      */     
/* 1524 */     if ((localNumberFormatException2 >= 0) && (localNumberFormatException2 + 1 < localNumberFormatException3)) {
/* 1525 */       str2 = paramString.substring(localNumberFormatException2 + 1, localNumberFormatException1);
/* 1526 */       str2 = str2.toLowerCase(Locale.ENGLISH);
/* 1527 */       if (str2.equals("bolditalic")) {
/* 1528 */         j = 3;
/* 1529 */       } else if (str2.equals("italic")) {
/* 1530 */         j = 2;
/* 1531 */       } else if (str2.equals("bold")) {
/* 1532 */         j = 1;
/* 1533 */       } else if (str2.equals("plain")) {
/* 1534 */         j = 0;
/*      */ 
/*      */       }
/*      */       else
/*      */       {
/* 1539 */         localNumberFormatException2 = localNumberFormatException1;
/* 1540 */         if (paramString.charAt(localNumberFormatException2 - 1) == n) {
/* 1541 */           localNumberFormatException2--;
/*      */         }
/*      */       }
/* 1544 */       str1 = paramString.substring(0, localNumberFormatException2);
/*      */     }
/*      */     else {
/* 1547 */       localNumberFormatException4 = localNumberFormatException3;
/* 1548 */       if (localNumberFormatException2 > 0) {
/* 1549 */         localNumberFormatException4 = localNumberFormatException2;
/* 1550 */       } else if (localNumberFormatException1 > 0) {
/* 1551 */         localNumberFormatException4 = localNumberFormatException1;
/*      */       }
/* 1553 */       if ((localNumberFormatException4 > 0) && (paramString.charAt(localNumberFormatException4 - 1) == n)) {
/* 1554 */         localNumberFormatException4--;
/*      */       }
/* 1556 */       str1 = paramString.substring(0, localNumberFormatException4);
/*      */     }
/*      */     
/* 1559 */     return new Font(str1, j, i);
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
/*      */   public static Font getFont(String paramString, Font paramFont)
/*      */   {
/* 1583 */     String str = null;
/*      */     try {
/* 1585 */       str = System.getProperty(paramString);
/*      */     }
/*      */     catch (SecurityException localSecurityException) {}
/* 1588 */     if (str == null) {
/* 1589 */       return paramFont;
/*      */     }
/* 1591 */     return decode(str);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public int hashCode()
/*      */   {
/* 1601 */     if (this.hash == 0) {
/* 1602 */       this.hash = (this.name.hashCode() ^ this.style ^ this.size);
/*      */       
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1609 */       if ((this.nonIdentityTx) && (this.values != null) && 
/* 1610 */         (this.values.getTransform() != null)) {
/* 1611 */         this.hash ^= this.values.getTransform().hashCode();
/*      */       }
/*      */     }
/* 1614 */     return this.hash;
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
/*      */   public boolean equals(Object paramObject)
/*      */   {
/* 1628 */     if (paramObject == this) {
/* 1629 */       return true;
/*      */     }
/*      */     
/* 1632 */     if (paramObject != null) {
/*      */       try {
/* 1634 */         Font localFont = (Font)paramObject;
/* 1635 */         if ((this.size == localFont.size) && (this.style == localFont.style) && (this.nonIdentityTx == localFont.nonIdentityTx) && (this.hasLayoutAttributes == localFont.hasLayoutAttributes) && (this.pointSize == localFont.pointSize))
/*      */         {
/*      */ 
/*      */ 
/*      */ 
/* 1640 */           if (this.name.equals(localFont.name))
/*      */           {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1648 */             if (this.values == null) {
/* 1649 */               if (localFont.values == null) {
/* 1650 */                 return true;
/*      */               }
/* 1652 */               return getAttributeValues().equals(localFont.values);
/*      */             }
/*      */             
/* 1655 */             return this.values.equals(localFont.getAttributeValues());
/*      */           }
/*      */         }
/*      */       }
/*      */       catch (ClassCastException localClassCastException) {}
/*      */     }
/*      */     
/* 1662 */     return false;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public String toString()
/*      */   {
/*      */     String str;
/*      */     
/*      */ 
/*      */ 
/*      */ 
/* 1677 */     if (isBold()) {
/* 1678 */       str = isItalic() ? "bolditalic" : "bold";
/*      */     } else {
/* 1680 */       str = isItalic() ? "italic" : "plain";
/*      */     }
/*      */     
/* 1683 */     return getClass().getName() + "[family=" + getFamily() + ",name=" + this.name + ",style=" + str + ",size=" + this.size + "]";
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
/* 1702 */   private int fontSerializedDataVersion = 1;
/*      */   
/*      */   private transient SoftReference<FontLineMetrics> flmref;
/*      */   
/*      */   public static final int LAYOUT_LEFT_TO_RIGHT = 0;
/*      */   
/*      */   public static final int LAYOUT_RIGHT_TO_LEFT = 1;
/*      */   public static final int LAYOUT_NO_START_CONTEXT = 2;
/*      */   public static final int LAYOUT_NO_LIMIT_CONTEXT = 4;
/*      */   
/*      */   private void writeObject(ObjectOutputStream paramObjectOutputStream)
/*      */     throws ClassNotFoundException, IOException
/*      */   {
/* 1715 */     if (this.values != null) {
/* 1716 */       synchronized (this.values)
/*      */       {
/* 1718 */         this.fRequestedAttributes = this.values.toSerializableHashtable();
/* 1719 */         paramObjectOutputStream.defaultWriteObject();
/* 1720 */         this.fRequestedAttributes = null;
/*      */       }
/*      */     } else {
/* 1723 */       paramObjectOutputStream.defaultWriteObject();
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
/*      */   private void readObject(ObjectInputStream paramObjectInputStream)
/*      */     throws ClassNotFoundException, IOException
/*      */   {
/* 1739 */     paramObjectInputStream.defaultReadObject();
/* 1740 */     if (this.pointSize == 0.0F) {
/* 1741 */       this.pointSize = this.size;
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1752 */     if (this.fRequestedAttributes != null) {
/* 1753 */       this.values = getAttributeValues();
/*      */       
/* 1755 */       AttributeValues localAttributeValues = AttributeValues.fromSerializableHashtable(this.fRequestedAttributes);
/* 1756 */       if (!AttributeValues.is16Hashtable(this.fRequestedAttributes)) {
/* 1757 */         localAttributeValues.unsetDefault();
/*      */       }
/* 1759 */       this.values = getAttributeValues().merge(localAttributeValues);
/* 1760 */       this.nonIdentityTx = this.values.anyNonDefault(EXTRA_MASK);
/* 1761 */       this.hasLayoutAttributes = this.values.anyNonDefault(LAYOUT_MASK);
/*      */       
/* 1763 */       this.fRequestedAttributes = null;
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public int getNumGlyphs()
/*      */   {
/* 1775 */     return getFont2D().getNumGlyphs();
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public int getMissingGlyphCode()
/*      */   {
/* 1785 */     return getFont2D().getMissingGlyphCode();
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
/*      */   public byte getBaselineFor(char paramChar)
/*      */   {
/* 1805 */     return getFont2D().getBaselineFor(paramChar);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public Map<TextAttribute, ?> getAttributes()
/*      */   {
/* 1815 */     return new AttributeMap(getAttributeValues());
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
/*      */   public AttributedCharacterIterator.Attribute[] getAvailableAttributes()
/*      */   {
/* 1829 */     AttributedCharacterIterator.Attribute[] arrayOfAttribute = { TextAttribute.FAMILY, TextAttribute.WEIGHT, TextAttribute.WIDTH, TextAttribute.POSTURE, TextAttribute.SIZE, TextAttribute.TRANSFORM, TextAttribute.SUPERSCRIPT, TextAttribute.CHAR_REPLACEMENT, TextAttribute.FOREGROUND, TextAttribute.BACKGROUND, TextAttribute.UNDERLINE, TextAttribute.STRIKETHROUGH, TextAttribute.RUN_DIRECTION, TextAttribute.BIDI_EMBEDDING, TextAttribute.JUSTIFICATION, TextAttribute.INPUT_METHOD_HIGHLIGHT, TextAttribute.INPUT_METHOD_UNDERLINE, TextAttribute.SWAP_COLORS, TextAttribute.NUMERIC_SHAPING, TextAttribute.KERNING, TextAttribute.LIGATURES, TextAttribute.TRACKING };
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1854 */     return arrayOfAttribute;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public Font deriveFont(int paramInt, float paramFloat)
/*      */   {
/* 1866 */     if (this.values == null) {
/* 1867 */       return new Font(this.name, paramInt, paramFloat, this.createdFont, this.font2DHandle);
/*      */     }
/* 1869 */     AttributeValues localAttributeValues = getAttributeValues().clone();
/* 1870 */     int i = this.style != paramInt ? this.style : -1;
/* 1871 */     applyStyle(paramInt, localAttributeValues);
/* 1872 */     localAttributeValues.setSize(paramFloat);
/* 1873 */     return new Font(localAttributeValues, null, i, this.createdFont, this.font2DHandle);
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
/*      */   public Font deriveFont(int paramInt, AffineTransform paramAffineTransform)
/*      */   {
/* 1888 */     AttributeValues localAttributeValues = getAttributeValues().clone();
/* 1889 */     int i = this.style != paramInt ? this.style : -1;
/* 1890 */     applyStyle(paramInt, localAttributeValues);
/* 1891 */     applyTransform(paramAffineTransform, localAttributeValues);
/* 1892 */     return new Font(localAttributeValues, null, i, this.createdFont, this.font2DHandle);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public Font deriveFont(float paramFloat)
/*      */   {
/* 1903 */     if (this.values == null) {
/* 1904 */       return new Font(this.name, this.style, paramFloat, this.createdFont, this.font2DHandle);
/*      */     }
/* 1906 */     AttributeValues localAttributeValues = getAttributeValues().clone();
/* 1907 */     localAttributeValues.setSize(paramFloat);
/* 1908 */     return new Font(localAttributeValues, null, -1, this.createdFont, this.font2DHandle);
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
/*      */   public Font deriveFont(AffineTransform paramAffineTransform)
/*      */   {
/* 1922 */     AttributeValues localAttributeValues = getAttributeValues().clone();
/* 1923 */     applyTransform(paramAffineTransform, localAttributeValues);
/* 1924 */     return new Font(localAttributeValues, null, -1, this.createdFont, this.font2DHandle);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public Font deriveFont(int paramInt)
/*      */   {
/* 1935 */     if (this.values == null) {
/* 1936 */       return new Font(this.name, paramInt, this.size, this.createdFont, this.font2DHandle);
/*      */     }
/* 1938 */     AttributeValues localAttributeValues = getAttributeValues().clone();
/* 1939 */     int i = this.style != paramInt ? this.style : -1;
/* 1940 */     applyStyle(paramInt, localAttributeValues);
/* 1941 */     return new Font(localAttributeValues, null, i, this.createdFont, this.font2DHandle);
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
/*      */   public Font deriveFont(Map<? extends AttributedCharacterIterator.Attribute, ?> paramMap)
/*      */   {
/* 1955 */     if (paramMap == null) {
/* 1956 */       return this;
/*      */     }
/* 1958 */     AttributeValues localAttributeValues = getAttributeValues().clone();
/* 1959 */     localAttributeValues.merge(paramMap, RECOGNIZED_MASK);
/*      */     
/* 1961 */     return new Font(localAttributeValues, this.name, this.style, this.createdFont, this.font2DHandle);
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
/*      */   public boolean canDisplay(char paramChar)
/*      */   {
/* 1980 */     return getFont2D().canDisplay(paramChar);
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
/*      */   public boolean canDisplay(int paramInt)
/*      */   {
/* 1997 */     if (!Character.isValidCodePoint(paramInt))
/*      */     {
/* 1999 */       throw new IllegalArgumentException("invalid code point: " + Integer.toHexString(paramInt));
/*      */     }
/* 2001 */     return getFont2D().canDisplay(paramInt);
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
/*      */   public int canDisplayUpTo(String paramString)
/*      */   {
/* 2022 */     Font2D localFont2D = getFont2D();
/* 2023 */     int i = paramString.length();
/* 2024 */     for (int j = 0; j < i; j++) {
/* 2025 */       char c = paramString.charAt(j);
/* 2026 */       if (!localFont2D.canDisplay(c))
/*      */       {
/*      */ 
/* 2029 */         if (!Character.isHighSurrogate(c)) {
/* 2030 */           return j;
/*      */         }
/* 2032 */         if (!localFont2D.canDisplay(paramString.codePointAt(j))) {
/* 2033 */           return j;
/*      */         }
/* 2035 */         j++;
/*      */       } }
/* 2037 */     return -1;
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
/*      */   public int canDisplayUpTo(char[] paramArrayOfChar, int paramInt1, int paramInt2)
/*      */   {
/* 2060 */     Font2D localFont2D = getFont2D();
/* 2061 */     for (int i = paramInt1; i < paramInt2; i++) {
/* 2062 */       char c = paramArrayOfChar[i];
/* 2063 */       if (!localFont2D.canDisplay(c))
/*      */       {
/*      */ 
/* 2066 */         if (!Character.isHighSurrogate(c)) {
/* 2067 */           return i;
/*      */         }
/* 2069 */         if (!localFont2D.canDisplay(Character.codePointAt(paramArrayOfChar, i, paramInt2))) {
/* 2070 */           return i;
/*      */         }
/* 2072 */         i++;
/*      */       } }
/* 2074 */     return -1;
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
/*      */   public int canDisplayUpTo(CharacterIterator paramCharacterIterator, int paramInt1, int paramInt2)
/*      */   {
/* 2095 */     Font2D localFont2D = getFont2D();
/* 2096 */     char c1 = paramCharacterIterator.setIndex(paramInt1);
/* 2097 */     for (int i = paramInt1; i < paramInt2; c1 = paramCharacterIterator.next()) {
/* 2098 */       if (!localFont2D.canDisplay(c1))
/*      */       {
/*      */ 
/* 2101 */         if (!Character.isHighSurrogate(c1)) {
/* 2102 */           return i;
/*      */         }
/* 2104 */         char c2 = paramCharacterIterator.next();
/*      */         
/* 2106 */         if (!Character.isLowSurrogate(c2)) {
/* 2107 */           return i;
/*      */         }
/* 2109 */         if (!localFont2D.canDisplay(Character.toCodePoint(c1, c2))) {
/* 2110 */           return i;
/*      */         }
/* 2112 */         i++;
/*      */       }
/* 2097 */       i++;
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 2114 */     return -1;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public float getItalicAngle()
/*      */   {
/* 2125 */     return getItalicAngle(null);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   private float getItalicAngle(FontRenderContext paramFontRenderContext)
/*      */   {
/*      */     Object localObject1;
/*      */     
/*      */ 
/*      */     Object localObject2;
/*      */     
/*      */ 
/* 2138 */     if (paramFontRenderContext == null) {
/* 2139 */       localObject1 = RenderingHints.VALUE_TEXT_ANTIALIAS_OFF;
/* 2140 */       localObject2 = RenderingHints.VALUE_FRACTIONALMETRICS_OFF;
/*      */     } else {
/* 2142 */       localObject1 = paramFontRenderContext.getAntiAliasingHint();
/* 2143 */       localObject2 = paramFontRenderContext.getFractionalMetricsHint();
/*      */     }
/* 2145 */     return getFont2D().getItalicAngle(this, identityTx, localObject1, localObject2);
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
/*      */   public boolean hasUniformLineMetrics()
/*      */   {
/* 2160 */     return false;
/*      */   }
/*      */   
/*      */   private FontLineMetrics defaultLineMetrics(FontRenderContext paramFontRenderContext)
/*      */   {
/* 2165 */     FontLineMetrics localFontLineMetrics = null;
/* 2166 */     if ((this.flmref == null) || 
/* 2167 */       ((localFontLineMetrics = (FontLineMetrics)this.flmref.get()) == null) || 
/* 2168 */       (!localFontLineMetrics.frc.equals(paramFontRenderContext)))
/*      */     {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 2175 */       float[] arrayOfFloat1 = new float[8];
/* 2176 */       getFont2D().getFontMetrics(this, identityTx, paramFontRenderContext
/* 2177 */         .getAntiAliasingHint(), paramFontRenderContext
/* 2178 */         .getFractionalMetricsHint(), arrayOfFloat1);
/*      */       
/* 2180 */       float f1 = arrayOfFloat1[0];
/* 2181 */       float f2 = arrayOfFloat1[1];
/* 2182 */       float f3 = arrayOfFloat1[2];
/* 2183 */       float f4 = 0.0F;
/* 2184 */       if ((this.values != null) && (this.values.getSuperscript() != 0)) {
/* 2185 */         f4 = (float)getTransform().getTranslateY();
/* 2186 */         f1 -= f4;
/* 2187 */         f2 += f4;
/*      */       }
/* 2189 */       float f5 = f1 + f2 + f3;
/*      */       
/* 2191 */       int i = 0;
/*      */       
/* 2193 */       float[] arrayOfFloat2 = { 0.0F, (f2 / 2.0F - f1) / 2.0F, -f1 };
/*      */       
/* 2195 */       float f6 = arrayOfFloat1[4];
/* 2196 */       float f7 = arrayOfFloat1[5];
/*      */       
/* 2198 */       float f8 = arrayOfFloat1[6];
/* 2199 */       float f9 = arrayOfFloat1[7];
/*      */       
/* 2201 */       float f10 = getItalicAngle(paramFontRenderContext);
/*      */       
/* 2203 */       if (isTransformed()) {
/* 2204 */         localObject = this.values.getCharTransform();
/* 2205 */         if (localObject != null) {
/* 2206 */           Point2D.Float localFloat = new Point2D.Float();
/* 2207 */           localFloat.setLocation(0.0F, f6);
/* 2208 */           ((AffineTransform)localObject).deltaTransform(localFloat, localFloat);
/* 2209 */           f6 = localFloat.y;
/* 2210 */           localFloat.setLocation(0.0F, f7);
/* 2211 */           ((AffineTransform)localObject).deltaTransform(localFloat, localFloat);
/* 2212 */           f7 = localFloat.y;
/* 2213 */           localFloat.setLocation(0.0F, f8);
/* 2214 */           ((AffineTransform)localObject).deltaTransform(localFloat, localFloat);
/* 2215 */           f8 = localFloat.y;
/* 2216 */           localFloat.setLocation(0.0F, f9);
/* 2217 */           ((AffineTransform)localObject).deltaTransform(localFloat, localFloat);
/* 2218 */           f9 = localFloat.y;
/*      */         }
/*      */       }
/* 2221 */       f6 += f4;
/* 2222 */       f8 += f4;
/*      */       
/* 2224 */       Object localObject = new CoreMetrics(f1, f2, f3, f5, i, arrayOfFloat2, f6, f7, f8, f9, f4, f10);
/*      */       
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 2230 */       localFontLineMetrics = new FontLineMetrics(0, (CoreMetrics)localObject, paramFontRenderContext);
/* 2231 */       this.flmref = new SoftReference(localFontLineMetrics);
/*      */     }
/*      */     
/* 2234 */     return (FontLineMetrics)localFontLineMetrics.clone();
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public LineMetrics getLineMetrics(String paramString, FontRenderContext paramFontRenderContext)
/*      */   {
/* 2246 */     FontLineMetrics localFontLineMetrics = defaultLineMetrics(paramFontRenderContext);
/* 2247 */     localFontLineMetrics.numchars = paramString.length();
/* 2248 */     return localFontLineMetrics;
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
/*      */   public LineMetrics getLineMetrics(String paramString, int paramInt1, int paramInt2, FontRenderContext paramFontRenderContext)
/*      */   {
/* 2264 */     FontLineMetrics localFontLineMetrics = defaultLineMetrics(paramFontRenderContext);
/* 2265 */     int i = paramInt2 - paramInt1;
/* 2266 */     localFontLineMetrics.numchars = (i < 0 ? 0 : i);
/* 2267 */     return localFontLineMetrics;
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
/*      */   public LineMetrics getLineMetrics(char[] paramArrayOfChar, int paramInt1, int paramInt2, FontRenderContext paramFontRenderContext)
/*      */   {
/* 2283 */     FontLineMetrics localFontLineMetrics = defaultLineMetrics(paramFontRenderContext);
/* 2284 */     int i = paramInt2 - paramInt1;
/* 2285 */     localFontLineMetrics.numchars = (i < 0 ? 0 : i);
/* 2286 */     return localFontLineMetrics;
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
/*      */   public LineMetrics getLineMetrics(CharacterIterator paramCharacterIterator, int paramInt1, int paramInt2, FontRenderContext paramFontRenderContext)
/*      */   {
/* 2302 */     FontLineMetrics localFontLineMetrics = defaultLineMetrics(paramFontRenderContext);
/* 2303 */     int i = paramInt2 - paramInt1;
/* 2304 */     localFontLineMetrics.numchars = (i < 0 ? 0 : i);
/* 2305 */     return localFontLineMetrics;
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
/*      */   public Rectangle2D getStringBounds(String paramString, FontRenderContext paramFontRenderContext)
/*      */   {
/* 2330 */     char[] arrayOfChar = paramString.toCharArray();
/* 2331 */     return getStringBounds(arrayOfChar, 0, arrayOfChar.length, paramFontRenderContext);
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
/*      */   public Rectangle2D getStringBounds(String paramString, int paramInt1, int paramInt2, FontRenderContext paramFontRenderContext)
/*      */   {
/* 2364 */     String str = paramString.substring(paramInt1, paramInt2);
/* 2365 */     return getStringBounds(str, paramFontRenderContext);
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
/*      */   public Rectangle2D getStringBounds(char[] paramArrayOfChar, int paramInt1, int paramInt2, FontRenderContext paramFontRenderContext)
/*      */   {
/* 2399 */     if (paramInt1 < 0) {
/* 2400 */       throw new IndexOutOfBoundsException("beginIndex: " + paramInt1);
/*      */     }
/* 2402 */     if (paramInt2 > paramArrayOfChar.length) {
/* 2403 */       throw new IndexOutOfBoundsException("limit: " + paramInt2);
/*      */     }
/* 2405 */     if (paramInt1 > paramInt2) {
/* 2406 */       throw new IndexOutOfBoundsException("range length: " + (paramInt2 - paramInt1));
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 2415 */     int i = (this.values == null) || ((this.values.getKerning() == 0) && (this.values.getLigatures() == 0) && (this.values.getBaselineTransform() == null)) ? 1 : 0;
/* 2416 */     if (i != 0) {
/* 2417 */       i = !FontUtilities.isComplexText(paramArrayOfChar, paramInt1, paramInt2) ? 1 : 0;
/*      */     }
/*      */     
/* 2420 */     if (i != 0) {
/* 2421 */       localObject = new StandardGlyphVector(this, paramArrayOfChar, paramInt1, paramInt2 - paramInt1, paramFontRenderContext);
/*      */       
/* 2423 */       return ((GlyphVector)localObject).getLogicalBounds();
/*      */     }
/*      */     
/* 2426 */     Object localObject = new String(paramArrayOfChar, paramInt1, paramInt2 - paramInt1);
/* 2427 */     TextLayout localTextLayout = new TextLayout((String)localObject, this, paramFontRenderContext);
/*      */     
/*      */ 
/* 2430 */     return new Rectangle2D.Float(0.0F, -localTextLayout.getAscent(), localTextLayout.getAdvance(), localTextLayout.getAscent() + localTextLayout.getDescent() + localTextLayout.getLeading());
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
/*      */   public Rectangle2D getStringBounds(CharacterIterator paramCharacterIterator, int paramInt1, int paramInt2, FontRenderContext paramFontRenderContext)
/*      */   {
/* 2466 */     int i = paramCharacterIterator.getBeginIndex();
/* 2467 */     int j = paramCharacterIterator.getEndIndex();
/*      */     
/* 2469 */     if (paramInt1 < i) {
/* 2470 */       throw new IndexOutOfBoundsException("beginIndex: " + paramInt1);
/*      */     }
/* 2472 */     if (paramInt2 > j) {
/* 2473 */       throw new IndexOutOfBoundsException("limit: " + paramInt2);
/*      */     }
/* 2475 */     if (paramInt1 > paramInt2) {
/* 2476 */       throw new IndexOutOfBoundsException("range length: " + (paramInt2 - paramInt1));
/*      */     }
/*      */     
/*      */ 
/* 2480 */     char[] arrayOfChar = new char[paramInt2 - paramInt1];
/*      */     
/* 2482 */     paramCharacterIterator.setIndex(paramInt1);
/* 2483 */     for (int k = 0; k < arrayOfChar.length; k++) {
/* 2484 */       arrayOfChar[k] = paramCharacterIterator.current();
/* 2485 */       paramCharacterIterator.next();
/*      */     }
/*      */     
/* 2488 */     return getStringBounds(arrayOfChar, 0, arrayOfChar.length, paramFontRenderContext);
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
/*      */   public Rectangle2D getMaxCharBounds(FontRenderContext paramFontRenderContext)
/*      */   {
/* 2501 */     float[] arrayOfFloat = new float[4];
/*      */     
/* 2503 */     getFont2D().getFontMetrics(this, paramFontRenderContext, arrayOfFloat);
/*      */     
/* 2505 */     return new Rectangle2D.Float(0.0F, -arrayOfFloat[0], arrayOfFloat[3], arrayOfFloat[0] + arrayOfFloat[1] + arrayOfFloat[2]);
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
/*      */   public GlyphVector createGlyphVector(FontRenderContext paramFontRenderContext, String paramString)
/*      */   {
/* 2526 */     return new StandardGlyphVector(this, paramString, paramFontRenderContext);
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
/*      */   public GlyphVector createGlyphVector(FontRenderContext paramFontRenderContext, char[] paramArrayOfChar)
/*      */   {
/* 2545 */     return new StandardGlyphVector(this, paramArrayOfChar, paramFontRenderContext);
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
/*      */   public GlyphVector createGlyphVector(FontRenderContext paramFontRenderContext, CharacterIterator paramCharacterIterator)
/*      */   {
/* 2565 */     return new StandardGlyphVector(this, paramCharacterIterator, paramFontRenderContext);
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
/*      */   public GlyphVector createGlyphVector(FontRenderContext paramFontRenderContext, int[] paramArrayOfInt)
/*      */   {
/* 2585 */     return new StandardGlyphVector(this, paramArrayOfInt, paramFontRenderContext);
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
/*      */   public GlyphVector layoutGlyphVector(FontRenderContext paramFontRenderContext, char[] paramArrayOfChar, int paramInt1, int paramInt2, int paramInt3)
/*      */   {
/* 2636 */     GlyphLayout localGlyphLayout = GlyphLayout.get(null);
/* 2637 */     StandardGlyphVector localStandardGlyphVector = localGlyphLayout.layout(this, paramFontRenderContext, paramArrayOfChar, paramInt1, paramInt2 - paramInt1, paramInt3, null);
/*      */     
/* 2639 */     GlyphLayout.done(localGlyphLayout);
/* 2640 */     return localStandardGlyphVector;
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
/*      */   private static void applyTransform(AffineTransform paramAffineTransform, AttributeValues paramAttributeValues)
/*      */   {
/* 2669 */     if (paramAffineTransform == null) {
/* 2670 */       throw new IllegalArgumentException("transform must not be null");
/*      */     }
/* 2672 */     paramAttributeValues.setTransform(paramAffineTransform);
/*      */   }
/*      */   
/*      */   private static void applyStyle(int paramInt, AttributeValues paramAttributeValues)
/*      */   {
/* 2677 */     paramAttributeValues.setWeight((paramInt & 0x1) != 0 ? 2.0F : 1.0F);
/*      */     
/* 2679 */     paramAttributeValues.setPosture((paramInt & 0x2) != 0 ? 0.2F : 0.0F);
/*      */   }
/*      */   
/*      */   /* Error */
/*      */   private static Font createFont0(int paramInt, InputStream paramInputStream, CreatedFontTracker paramCreatedFontTracker)
/*      */     throws FontFormatException, IOException
/*      */   {
/*      */     // Byte code:
/*      */     //   0: iload_0
/*      */     //   1: ifeq +18 -> 19
/*      */     //   4: iload_0
/*      */     //   5: iconst_1
/*      */     //   6: if_icmpeq +13 -> 19
/*      */     //   9: new 88	java/lang/IllegalArgumentException
/*      */     //   12: dup
/*      */     //   13: ldc 89
/*      */     //   15: invokespecial 90	java/lang/IllegalArgumentException:<init>	(Ljava/lang/String;)V
/*      */     //   18: athrow
/*      */     //   19: iconst_0
/*      */     //   20: istore_3
/*      */     //   21: new 91	java/awt/Font$1
/*      */     //   24: dup
/*      */     //   25: invokespecial 92	java/awt/Font$1:<init>	()V
/*      */     //   28: invokestatic 93	java/security/AccessController:doPrivileged	(Ljava/security/PrivilegedExceptionAction;)Ljava/lang/Object;
/*      */     //   31: checkcast 94	java/io/File
/*      */     //   34: astore 4
/*      */     //   36: aload_2
/*      */     //   37: ifnull +9 -> 46
/*      */     //   40: aload_2
/*      */     //   41: aload 4
/*      */     //   43: invokevirtual 95	sun/font/CreatedFontTracker:add	(Ljava/io/File;)V
/*      */     //   46: iconst_0
/*      */     //   47: istore 5
/*      */     //   49: new 96	java/awt/Font$2
/*      */     //   52: dup
/*      */     //   53: aload 4
/*      */     //   55: invokespecial 97	java/awt/Font$2:<init>	(Ljava/io/File;)V
/*      */     //   58: invokestatic 93	java/security/AccessController:doPrivileged	(Ljava/security/PrivilegedExceptionAction;)Ljava/lang/Object;
/*      */     //   61: checkcast 98	java/io/OutputStream
/*      */     //   64: astore 6
/*      */     //   66: aload_2
/*      */     //   67: ifnull +11 -> 78
/*      */     //   70: aload_2
/*      */     //   71: aload 4
/*      */     //   73: aload 6
/*      */     //   75: invokevirtual 99	sun/font/CreatedFontTracker:set	(Ljava/io/File;Ljava/io/OutputStream;)V
/*      */     //   78: sipush 8192
/*      */     //   81: newarray <illegal type>
/*      */     //   83: astore 7
/*      */     //   85: aload_1
/*      */     //   86: aload 7
/*      */     //   88: invokevirtual 100	java/io/InputStream:read	([B)I
/*      */     //   91: istore 8
/*      */     //   93: iload 8
/*      */     //   95: ifge +6 -> 101
/*      */     //   98: goto +75 -> 173
/*      */     //   101: aload_2
/*      */     //   102: ifnull +58 -> 160
/*      */     //   105: iload 5
/*      */     //   107: iload 8
/*      */     //   109: iadd
/*      */     //   110: ldc 102
/*      */     //   112: if_icmple +13 -> 125
/*      */     //   115: new 82	java/io/IOException
/*      */     //   118: dup
/*      */     //   119: ldc 103
/*      */     //   121: invokespecial 84	java/io/IOException:<init>	(Ljava/lang/String;)V
/*      */     //   124: athrow
/*      */     //   125: iload 5
/*      */     //   127: aload_2
/*      */     //   128: invokevirtual 104	sun/font/CreatedFontTracker:getNumBytes	()I
/*      */     //   131: iadd
/*      */     //   132: ldc 105
/*      */     //   134: if_icmple +13 -> 147
/*      */     //   137: new 82	java/io/IOException
/*      */     //   140: dup
/*      */     //   141: ldc 106
/*      */     //   143: invokespecial 84	java/io/IOException:<init>	(Ljava/lang/String;)V
/*      */     //   146: athrow
/*      */     //   147: iload 5
/*      */     //   149: iload 8
/*      */     //   151: iadd
/*      */     //   152: istore 5
/*      */     //   154: aload_2
/*      */     //   155: iload 8
/*      */     //   157: invokevirtual 107	sun/font/CreatedFontTracker:addBytes	(I)V
/*      */     //   160: aload 6
/*      */     //   162: aload 7
/*      */     //   164: iconst_0
/*      */     //   165: iload 8
/*      */     //   167: invokevirtual 108	java/io/OutputStream:write	([BII)V
/*      */     //   170: goto -85 -> 85
/*      */     //   173: aload 6
/*      */     //   175: invokevirtual 109	java/io/OutputStream:close	()V
/*      */     //   178: goto +13 -> 191
/*      */     //   181: astore 9
/*      */     //   183: aload 6
/*      */     //   185: invokevirtual 109	java/io/OutputStream:close	()V
/*      */     //   188: aload 9
/*      */     //   190: athrow
/*      */     //   191: iconst_1
/*      */     //   192: istore_3
/*      */     //   193: new 16	java/awt/Font
/*      */     //   196: dup
/*      */     //   197: aload 4
/*      */     //   199: iload_0
/*      */     //   200: iconst_1
/*      */     //   201: aload_2
/*      */     //   202: invokespecial 110	java/awt/Font:<init>	(Ljava/io/File;IZLsun/font/CreatedFontTracker;)V
/*      */     //   205: astore 7
/*      */     //   207: aload 7
/*      */     //   209: astore 8
/*      */     //   211: aload_2
/*      */     //   212: ifnull +9 -> 221
/*      */     //   215: aload_2
/*      */     //   216: aload 4
/*      */     //   218: invokevirtual 111	sun/font/CreatedFontTracker:remove	(Ljava/io/File;)V
/*      */     //   221: iload_3
/*      */     //   222: ifne +26 -> 248
/*      */     //   225: aload_2
/*      */     //   226: ifnull +9 -> 235
/*      */     //   229: aload_2
/*      */     //   230: iload 5
/*      */     //   232: invokevirtual 112	sun/font/CreatedFontTracker:subBytes	(I)V
/*      */     //   235: new 113	java/awt/Font$3
/*      */     //   238: dup
/*      */     //   239: aload 4
/*      */     //   241: invokespecial 114	java/awt/Font$3:<init>	(Ljava/io/File;)V
/*      */     //   244: invokestatic 93	java/security/AccessController:doPrivileged	(Ljava/security/PrivilegedExceptionAction;)Ljava/lang/Object;
/*      */     //   247: pop
/*      */     //   248: aload 8
/*      */     //   250: areturn
/*      */     //   251: astore 10
/*      */     //   253: aload_2
/*      */     //   254: ifnull +9 -> 263
/*      */     //   257: aload_2
/*      */     //   258: aload 4
/*      */     //   260: invokevirtual 111	sun/font/CreatedFontTracker:remove	(Ljava/io/File;)V
/*      */     //   263: iload_3
/*      */     //   264: ifne +26 -> 290
/*      */     //   267: aload_2
/*      */     //   268: ifnull +9 -> 277
/*      */     //   271: aload_2
/*      */     //   272: iload 5
/*      */     //   274: invokevirtual 112	sun/font/CreatedFontTracker:subBytes	(I)V
/*      */     //   277: new 113	java/awt/Font$3
/*      */     //   280: dup
/*      */     //   281: aload 4
/*      */     //   283: invokespecial 114	java/awt/Font$3:<init>	(Ljava/io/File;)V
/*      */     //   286: invokestatic 93	java/security/AccessController:doPrivileged	(Ljava/security/PrivilegedExceptionAction;)Ljava/lang/Object;
/*      */     //   289: pop
/*      */     //   290: aload 10
/*      */     //   292: athrow
/*      */     //   293: astore 4
/*      */     //   295: aload 4
/*      */     //   297: instanceof 115
/*      */     //   300: ifeq +9 -> 309
/*      */     //   303: aload 4
/*      */     //   305: checkcast 115	java/awt/FontFormatException
/*      */     //   308: athrow
/*      */     //   309: aload 4
/*      */     //   311: instanceof 82
/*      */     //   314: ifeq +9 -> 323
/*      */     //   317: aload 4
/*      */     //   319: checkcast 82	java/io/IOException
/*      */     //   322: athrow
/*      */     //   323: aload 4
/*      */     //   325: invokevirtual 116	java/lang/Throwable:getCause	()Ljava/lang/Throwable;
/*      */     //   328: astore 5
/*      */     //   330: aload 5
/*      */     //   332: instanceof 115
/*      */     //   335: ifeq +9 -> 344
/*      */     //   338: aload 5
/*      */     //   340: checkcast 115	java/awt/FontFormatException
/*      */     //   343: athrow
/*      */     //   344: new 82	java/io/IOException
/*      */     //   347: dup
/*      */     //   348: ldc 87
/*      */     //   350: invokespecial 84	java/io/IOException:<init>	(Ljava/lang/String;)V
/*      */     //   353: athrow
/*      */     // Line number table:
/*      */     //   Java source line #903	-> byte code offset #0
/*      */     //   Java source line #905	-> byte code offset #9
/*      */     //   Java source line #907	-> byte code offset #19
/*      */     //   Java source line #909	-> byte code offset #21
/*      */     //   Java source line #916	-> byte code offset #36
/*      */     //   Java source line #917	-> byte code offset #40
/*      */     //   Java source line #920	-> byte code offset #46
/*      */     //   Java source line #922	-> byte code offset #49
/*      */     //   Java source line #923	-> byte code offset #58
/*      */     //   Java source line #930	-> byte code offset #66
/*      */     //   Java source line #931	-> byte code offset #70
/*      */     //   Java source line #934	-> byte code offset #78
/*      */     //   Java source line #936	-> byte code offset #85
/*      */     //   Java source line #937	-> byte code offset #93
/*      */     //   Java source line #938	-> byte code offset #98
/*      */     //   Java source line #940	-> byte code offset #101
/*      */     //   Java source line #941	-> byte code offset #105
/*      */     //   Java source line #942	-> byte code offset #115
/*      */     //   Java source line #944	-> byte code offset #125
/*      */     //   Java source line #947	-> byte code offset #137
/*      */     //   Java source line #949	-> byte code offset #147
/*      */     //   Java source line #950	-> byte code offset #154
/*      */     //   Java source line #952	-> byte code offset #160
/*      */     //   Java source line #953	-> byte code offset #170
/*      */     //   Java source line #956	-> byte code offset #173
/*      */     //   Java source line #957	-> byte code offset #178
/*      */     //   Java source line #956	-> byte code offset #181
/*      */     //   Java source line #968	-> byte code offset #191
/*      */     //   Java source line #969	-> byte code offset #193
/*      */     //   Java source line #970	-> byte code offset #207
/*      */     //   Java source line #972	-> byte code offset #211
/*      */     //   Java source line #973	-> byte code offset #215
/*      */     //   Java source line #975	-> byte code offset #221
/*      */     //   Java source line #976	-> byte code offset #225
/*      */     //   Java source line #977	-> byte code offset #229
/*      */     //   Java source line #979	-> byte code offset #235
/*      */     //   Java source line #972	-> byte code offset #251
/*      */     //   Java source line #973	-> byte code offset #257
/*      */     //   Java source line #975	-> byte code offset #263
/*      */     //   Java source line #976	-> byte code offset #267
/*      */     //   Java source line #977	-> byte code offset #271
/*      */     //   Java source line #979	-> byte code offset #277
/*      */     //   Java source line #989	-> byte code offset #293
/*      */     //   Java source line #990	-> byte code offset #295
/*      */     //   Java source line #991	-> byte code offset #303
/*      */     //   Java source line #993	-> byte code offset #309
/*      */     //   Java source line #994	-> byte code offset #317
/*      */     //   Java source line #996	-> byte code offset #323
/*      */     //   Java source line #997	-> byte code offset #330
/*      */     //   Java source line #998	-> byte code offset #338
/*      */     //   Java source line #1000	-> byte code offset #344
/*      */     // Local variable table:
/*      */     //   start	length	slot	name	signature
/*      */     //   0	354	0	paramInt	int
/*      */     //   0	354	1	paramInputStream	InputStream
/*      */     //   0	354	2	paramCreatedFontTracker	CreatedFontTracker
/*      */     //   20	244	3	i	int
/*      */     //   34	248	4	localFile	File
/*      */     //   293	31	4	localThrowable1	Throwable
/*      */     //   47	226	5	j	int
/*      */     //   328	11	5	localThrowable2	Throwable
/*      */     //   64	120	6	localOutputStream	OutputStream
/*      */     //   83	125	7	localObject1	Object
/*      */     //   91	75	8	k	int
/*      */     //   181	8	9	localObject3	Object
/*      */     //   251	40	10	localObject4	Object
/*      */     // Exception table:
/*      */     //   from	to	target	type
/*      */     //   78	173	181	finally
/*      */     //   181	183	181	finally
/*      */     //   49	211	251	finally
/*      */     //   251	253	251	finally
/*      */     //   21	248	293	java/lang/Throwable
/*      */     //   251	293	293	java/lang/Throwable
/*      */   }
/*      */   
/*      */   private static native void initIDs();
/*      */ }


/* Location:              /Users/guanxiaoda/Documents/workspace/rt.jar!/java/awt/Font.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */