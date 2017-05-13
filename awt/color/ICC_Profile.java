/*      */ package java.awt.color;
/*      */ 
/*      */ import java.io.File;
/*      */ import java.io.FileInputStream;
/*      */ import java.io.FileNotFoundException;
/*      */ import java.io.FileOutputStream;
/*      */ import java.io.IOException;
/*      */ import java.io.InputStream;
/*      */ import java.io.ObjectInputStream;
/*      */ import java.io.ObjectOutputStream;
/*      */ import java.io.ObjectStreamException;
/*      */ import java.io.OutputStream;
/*      */ import java.io.Serializable;
/*      */ import java.security.AccessController;
/*      */ import java.security.PrivilegedAction;
/*      */ import java.util.StringTokenizer;
/*      */ import sun.java2d.cmm.CMSManager;
/*      */ import sun.java2d.cmm.PCMM;
/*      */ import sun.java2d.cmm.Profile;
/*      */ import sun.java2d.cmm.ProfileActivator;
/*      */ import sun.java2d.cmm.ProfileDataVerifier;
/*      */ import sun.java2d.cmm.ProfileDeferralInfo;
/*      */ import sun.java2d.cmm.ProfileDeferralMgr;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class ICC_Profile
/*      */   implements Serializable
/*      */ {
/*      */   private static final long serialVersionUID = -3938515861990936766L;
/*      */   private transient Profile cmmProfile;
/*      */   private transient ProfileDeferralInfo deferralInfo;
/*      */   private transient ProfileActivator profileActivator;
/*      */   private static ICC_Profile sRGBprofile;
/*      */   private static ICC_Profile XYZprofile;
/*      */   private static ICC_Profile PYCCprofile;
/*      */   private static ICC_Profile GRAYprofile;
/*      */   private static ICC_Profile LINEAR_RGBprofile;
/*      */   public static final int CLASS_INPUT = 0;
/*      */   public static final int CLASS_DISPLAY = 1;
/*      */   public static final int CLASS_OUTPUT = 2;
/*      */   public static final int CLASS_DEVICELINK = 3;
/*      */   public static final int CLASS_COLORSPACECONVERSION = 4;
/*      */   public static final int CLASS_ABSTRACT = 5;
/*      */   public static final int CLASS_NAMEDCOLOR = 6;
/*      */   public static final int icSigXYZData = 1482250784;
/*      */   public static final int icSigLabData = 1281450528;
/*      */   public static final int icSigLuvData = 1282766368;
/*      */   public static final int icSigYCbCrData = 1497588338;
/*      */   public static final int icSigYxyData = 1501067552;
/*      */   public static final int icSigRgbData = 1380401696;
/*      */   public static final int icSigGrayData = 1196573017;
/*      */   public static final int icSigHsvData = 1213421088;
/*      */   public static final int icSigHlsData = 1212961568;
/*      */   public static final int icSigCmykData = 1129142603;
/*      */   public static final int icSigCmyData = 1129142560;
/*      */   public static final int icSigSpace2CLR = 843271250;
/*      */   public static final int icSigSpace3CLR = 860048466;
/*      */   public static final int icSigSpace4CLR = 876825682;
/*      */   public static final int icSigSpace5CLR = 893602898;
/*      */   public static final int icSigSpace6CLR = 910380114;
/*      */   public static final int icSigSpace7CLR = 927157330;
/*      */   public static final int icSigSpace8CLR = 943934546;
/*      */   public static final int icSigSpace9CLR = 960711762;
/*      */   public static final int icSigSpaceACLR = 1094929490;
/*      */   public static final int icSigSpaceBCLR = 1111706706;
/*      */   public static final int icSigSpaceCCLR = 1128483922;
/*      */   public static final int icSigSpaceDCLR = 1145261138;
/*      */   public static final int icSigSpaceECLR = 1162038354;
/*      */   public static final int icSigSpaceFCLR = 1178815570;
/*      */   public static final int icSigInputClass = 1935896178;
/*      */   public static final int icSigDisplayClass = 1835955314;
/*      */   public static final int icSigOutputClass = 1886549106;
/*      */   public static final int icSigLinkClass = 1818848875;
/*      */   public static final int icSigAbstractClass = 1633842036;
/*      */   public static final int icSigColorSpaceClass = 1936744803;
/*      */   public static final int icSigNamedColorClass = 1852662636;
/*      */   public static final int icPerceptual = 0;
/*      */   public static final int icRelativeColorimetric = 1;
/*      */   public static final int icMediaRelativeColorimetric = 1;
/*      */   public static final int icSaturation = 2;
/*      */   public static final int icAbsoluteColorimetric = 3;
/*      */   public static final int icICCAbsoluteColorimetric = 3;
/*      */   public static final int icSigHead = 1751474532;
/*      */   public static final int icSigAToB0Tag = 1093812784;
/*      */   public static final int icSigAToB1Tag = 1093812785;
/*      */   public static final int icSigAToB2Tag = 1093812786;
/*      */   public static final int icSigBlueColorantTag = 1649957210;
/*      */   public static final int icSigBlueMatrixColumnTag = 1649957210;
/*      */   public static final int icSigBlueTRCTag = 1649693251;
/*      */   public static final int icSigBToA0Tag = 1110589744;
/*      */   public static final int icSigBToA1Tag = 1110589745;
/*      */   public static final int icSigBToA2Tag = 1110589746;
/*      */   public static final int icSigCalibrationDateTimeTag = 1667329140;
/*      */   public static final int icSigCharTargetTag = 1952543335;
/*      */   public static final int icSigCopyrightTag = 1668313716;
/*      */   public static final int icSigCrdInfoTag = 1668441193;
/*      */   public static final int icSigDeviceMfgDescTag = 1684893284;
/*      */   public static final int icSigDeviceModelDescTag = 1684890724;
/*      */   public static final int icSigDeviceSettingsTag = 1684371059;
/*      */   public static final int icSigGamutTag = 1734438260;
/*      */   public static final int icSigGrayTRCTag = 1800688195;
/*      */   public static final int icSigGreenColorantTag = 1733843290;
/*      */   public static final int icSigGreenMatrixColumnTag = 1733843290;
/*      */   public static final int icSigGreenTRCTag = 1733579331;
/*      */   public static final int icSigLuminanceTag = 1819635049;
/*      */   public static final int icSigMeasurementTag = 1835360627;
/*      */   public static final int icSigMediaBlackPointTag = 1651208308;
/*      */   public static final int icSigMediaWhitePointTag = 2004119668;
/*      */   public static final int icSigNamedColor2Tag = 1852009522;
/*      */   public static final int icSigOutputResponseTag = 1919251312;
/*      */   public static final int icSigPreview0Tag = 1886545200;
/*      */   public static final int icSigPreview1Tag = 1886545201;
/*      */   public static final int icSigPreview2Tag = 1886545202;
/*      */   public static final int icSigProfileDescriptionTag = 1684370275;
/*      */   public static final int icSigProfileSequenceDescTag = 1886610801;
/*      */   public static final int icSigPs2CRD0Tag = 1886610480;
/*      */   public static final int icSigPs2CRD1Tag = 1886610481;
/*      */   public static final int icSigPs2CRD2Tag = 1886610482;
/*      */   public static final int icSigPs2CRD3Tag = 1886610483;
/*      */   public static final int icSigPs2CSATag = 1886597747;
/*      */   public static final int icSigPs2RenderingIntentTag = 1886597737;
/*      */   public static final int icSigRedColorantTag = 1918392666;
/*      */   public static final int icSigRedMatrixColumnTag = 1918392666;
/*      */   public static final int icSigRedTRCTag = 1918128707;
/*      */   public static final int icSigScreeningDescTag = 1935897188;
/*      */   public static final int icSigScreeningTag = 1935897198;
/*      */   public static final int icSigTechnologyTag = 1952801640;
/*      */   public static final int icSigUcrBgTag = 1650877472;
/*      */   public static final int icSigViewingCondDescTag = 1987405156;
/*      */   public static final int icSigViewingConditionsTag = 1986618743;
/*      */   public static final int icSigChromaticityTag = 1667789421;
/*      */   public static final int icSigChromaticAdaptationTag = 1667785060;
/*      */   public static final int icSigColorantOrderTag = 1668051567;
/*      */   public static final int icSigColorantTableTag = 1668051572;
/*      */   public static final int icHdrSize = 0;
/*      */   public static final int icHdrCmmId = 4;
/*      */   public static final int icHdrVersion = 8;
/*      */   public static final int icHdrDeviceClass = 12;
/*      */   public static final int icHdrColorSpace = 16;
/*      */   public static final int icHdrPcs = 20;
/*      */   public static final int icHdrDate = 24;
/*      */   public static final int icHdrMagic = 36;
/*      */   public static final int icHdrPlatform = 40;
/*      */   public static final int icHdrFlags = 44;
/*      */   public static final int icHdrManufacturer = 48;
/*      */   public static final int icHdrModel = 52;
/*      */   public static final int icHdrAttributes = 56;
/*      */   public static final int icHdrRenderingIntent = 64;
/*      */   public static final int icHdrIlluminant = 68;
/*      */   public static final int icHdrCreator = 80;
/*      */   public static final int icHdrProfileID = 84;
/*      */   public static final int icTagType = 0;
/*      */   public static final int icTagReserved = 4;
/*      */   public static final int icCurveCount = 8;
/*      */   public static final int icCurveData = 12;
/*      */   public static final int icXYZNumberX = 8;
/*      */   
/*      */   ICC_Profile(Profile paramProfile)
/*      */   {
/*  732 */     this.cmmProfile = paramProfile;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   ICC_Profile(ProfileDeferralInfo paramProfileDeferralInfo)
/*      */   {
/*  741 */     this.deferralInfo = paramProfileDeferralInfo;
/*  742 */     this.profileActivator = new ProfileActivator() {
/*      */       public void activate() throws ProfileDataException {
/*  744 */         ICC_Profile.this.activateDeferredProfile();
/*      */       }
/*  746 */     };
/*  747 */     ProfileDeferralMgr.registerDeferral(this.profileActivator);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   protected void finalize()
/*      */   {
/*  755 */     if (this.cmmProfile != null) {
/*  756 */       CMSManager.getModule().freeProfile(this.cmmProfile);
/*  757 */     } else if (this.profileActivator != null) {
/*  758 */       ProfileDeferralMgr.unregisterDeferral(this.profileActivator);
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
/*      */   public static ICC_Profile getInstance(byte[] paramArrayOfByte)
/*      */   {
/*  774 */     Profile localProfile = null;
/*      */     
/*  776 */     if (ProfileDeferralMgr.deferring) {
/*  777 */       ProfileDeferralMgr.activateProfiles();
/*      */     }
/*      */     
/*  780 */     ProfileDataVerifier.verify(paramArrayOfByte);
/*      */     try
/*      */     {
/*  783 */       localProfile = CMSManager.getModule().loadProfile(paramArrayOfByte);
/*      */     } catch (CMMException localCMMException1) {
/*  785 */       throw new IllegalArgumentException("Invalid ICC Profile Data");
/*      */     }
/*      */     Object localObject;
/*      */     try {
/*  789 */       if ((getColorSpaceType(localProfile) == 6) && 
/*  790 */         (getData(localProfile, 2004119668) != null) && 
/*  791 */         (getData(localProfile, 1800688195) != null)) {
/*  792 */         localObject = new ICC_ProfileGray(localProfile);
/*      */       }
/*  794 */       else if ((getColorSpaceType(localProfile) == 5) && 
/*  795 */         (getData(localProfile, 2004119668) != null) && 
/*  796 */         (getData(localProfile, 1918392666) != null) && 
/*  797 */         (getData(localProfile, 1733843290) != null) && 
/*  798 */         (getData(localProfile, 1649957210) != null) && 
/*  799 */         (getData(localProfile, 1918128707) != null) && 
/*  800 */         (getData(localProfile, 1733579331) != null) && 
/*  801 */         (getData(localProfile, 1649693251) != null)) {
/*  802 */         localObject = new ICC_ProfileRGB(localProfile);
/*      */       }
/*      */       else {
/*  805 */         localObject = new ICC_Profile(localProfile);
/*      */       }
/*      */     } catch (CMMException localCMMException2) {
/*  808 */       localObject = new ICC_Profile(localProfile);
/*      */     }
/*  810 */     return (ICC_Profile)localObject;
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
/*      */   public static ICC_Profile getInstance(int paramInt)
/*      */   {
/*  831 */     ICC_Profile localICC_Profile = null;
/*      */     
/*      */     ProfileDeferralInfo localProfileDeferralInfo;
/*  834 */     switch (paramInt) {
/*      */     case 1000: 
/*  836 */       synchronized (ICC_Profile.class) {
/*  837 */         if (sRGBprofile == null)
/*      */         {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  843 */           localProfileDeferralInfo = new ProfileDeferralInfo("sRGB.pf", 5, 3, 1);
/*      */           
/*      */ 
/*      */ 
/*  847 */           sRGBprofile = getDeferredInstance(localProfileDeferralInfo);
/*      */         }
/*  849 */         localICC_Profile = sRGBprofile;
/*      */       }
/*      */       
/*  852 */       break;
/*      */     
/*      */     case 1001: 
/*  855 */       synchronized (ICC_Profile.class) {
/*  856 */         if (XYZprofile == null) {
/*  857 */           localProfileDeferralInfo = new ProfileDeferralInfo("CIEXYZ.pf", 0, 3, 1);
/*      */           
/*      */ 
/*      */ 
/*  861 */           XYZprofile = getDeferredInstance(localProfileDeferralInfo);
/*      */         }
/*  863 */         localICC_Profile = XYZprofile;
/*      */       }
/*      */       
/*  866 */       break;
/*      */     
/*      */     case 1002: 
/*  869 */       synchronized (ICC_Profile.class) {
/*  870 */         if (PYCCprofile == null) {
/*  871 */           if (standardProfileExists("PYCC.pf"))
/*      */           {
/*  873 */             localProfileDeferralInfo = new ProfileDeferralInfo("PYCC.pf", 13, 3, 1);
/*      */             
/*      */ 
/*      */ 
/*  877 */             PYCCprofile = getDeferredInstance(localProfileDeferralInfo);
/*      */           } else {
/*  879 */             throw new IllegalArgumentException("Can't load standard profile: PYCC.pf");
/*      */           }
/*      */         }
/*      */         
/*  883 */         localICC_Profile = PYCCprofile;
/*      */       }
/*      */       
/*  886 */       break;
/*      */     
/*      */     case 1003: 
/*  889 */       synchronized (ICC_Profile.class) {
/*  890 */         if (GRAYprofile == null) {
/*  891 */           localProfileDeferralInfo = new ProfileDeferralInfo("GRAY.pf", 6, 1, 1);
/*      */           
/*      */ 
/*      */ 
/*  895 */           GRAYprofile = getDeferredInstance(localProfileDeferralInfo);
/*      */         }
/*  897 */         localICC_Profile = GRAYprofile;
/*      */       }
/*      */       
/*  900 */       break;
/*      */     
/*      */     case 1004: 
/*  903 */       synchronized (ICC_Profile.class) {
/*  904 */         if (LINEAR_RGBprofile == null) {
/*  905 */           localProfileDeferralInfo = new ProfileDeferralInfo("LINEAR_RGB.pf", 5, 3, 1);
/*      */           
/*      */ 
/*      */ 
/*  909 */           LINEAR_RGBprofile = getDeferredInstance(localProfileDeferralInfo);
/*      */         }
/*  911 */         localICC_Profile = LINEAR_RGBprofile;
/*      */       }
/*      */       
/*  914 */       break;
/*      */     
/*      */     default: 
/*  917 */       throw new IllegalArgumentException("Unknown color space");
/*      */     }
/*      */     
/*  920 */     return localICC_Profile;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   private static ICC_Profile getStandardProfile(String paramString)
/*      */   {
/*  928 */     (ICC_Profile)AccessController.doPrivileged(new PrivilegedAction()
/*      */     {
/*      */       public ICC_Profile run() {
/*  931 */         ICC_Profile localICC_Profile = null;
/*      */         try {
/*  933 */           localICC_Profile = ICC_Profile.getInstance(this.val$name);
/*      */         } catch (IOException localIOException) {
/*  935 */           throw new IllegalArgumentException("Can't load standard profile: " + this.val$name);
/*      */         }
/*      */         
/*  938 */         return localICC_Profile;
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static ICC_Profile getInstance(String paramString)
/*      */     throws IOException
/*      */   {
/*  971 */     FileInputStream localFileInputStream = null;
/*      */     
/*      */ 
/*  974 */     File localFile = getProfileFile(paramString);
/*  975 */     if (localFile != null) {
/*  976 */       localFileInputStream = new FileInputStream(localFile);
/*      */     }
/*  978 */     if (localFileInputStream == null) {
/*  979 */       throw new IOException("Cannot open file " + paramString);
/*      */     }
/*      */     
/*  982 */     ICC_Profile localICC_Profile = getInstance(localFileInputStream);
/*      */     
/*  984 */     localFileInputStream.close();
/*      */     
/*  986 */     return localICC_Profile;
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
/*      */   public static ICC_Profile getInstance(InputStream paramInputStream)
/*      */     throws IOException
/*      */   {
/* 1008 */     if ((paramInputStream instanceof ProfileDeferralInfo))
/*      */     {
/* 1010 */       return getDeferredInstance((ProfileDeferralInfo)paramInputStream);
/*      */     }
/*      */     byte[] arrayOfByte;
/* 1013 */     if ((arrayOfByte = getProfileDataFromStream(paramInputStream)) == null) {
/* 1014 */       throw new IllegalArgumentException("Invalid ICC Profile Data");
/*      */     }
/*      */     
/* 1017 */     return getInstance(arrayOfByte);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   static byte[] getProfileDataFromStream(InputStream paramInputStream)
/*      */     throws IOException
/*      */   {
/* 1025 */     byte[] arrayOfByte2 = new byte[''];
/* 1026 */     int j = 128;
/* 1027 */     int k = 0;
/*      */     
/*      */     int m;
/* 1030 */     while (j != 0) {
/* 1031 */       if ((m = paramInputStream.read(arrayOfByte2, k, j)) < 0) {
/* 1032 */         return null;
/*      */       }
/* 1034 */       k += m;
/* 1035 */       j -= m;
/*      */     }
/* 1037 */     if ((arrayOfByte2[36] != 97) || (arrayOfByte2[37] != 99) || (arrayOfByte2[38] != 115) || (arrayOfByte2[39] != 112))
/*      */     {
/* 1039 */       return null;
/*      */     }
/* 1041 */     int i = (arrayOfByte2[0] & 0xFF) << 24 | (arrayOfByte2[1] & 0xFF) << 16 | (arrayOfByte2[2] & 0xFF) << 8 | arrayOfByte2[3] & 0xFF;
/*      */     
/*      */ 
/*      */ 
/* 1045 */     byte[] arrayOfByte1 = new byte[i];
/* 1046 */     System.arraycopy(arrayOfByte2, 0, arrayOfByte1, 0, 128);
/* 1047 */     j = i - 128;
/* 1048 */     k = 128;
/* 1049 */     while (j != 0) {
/* 1050 */       if ((m = paramInputStream.read(arrayOfByte1, k, j)) < 0) {
/* 1051 */         return null;
/*      */       }
/* 1053 */       k += m;
/* 1054 */       j -= m;
/*      */     }
/*      */     
/* 1057 */     return arrayOfByte1;
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
/*      */   static ICC_Profile getDeferredInstance(ProfileDeferralInfo paramProfileDeferralInfo)
/*      */   {
/* 1074 */     if (!ProfileDeferralMgr.deferring) {
/* 1075 */       return getStandardProfile(paramProfileDeferralInfo.filename);
/*      */     }
/* 1077 */     if (paramProfileDeferralInfo.colorSpaceType == 5)
/* 1078 */       return new ICC_ProfileRGB(paramProfileDeferralInfo);
/* 1079 */     if (paramProfileDeferralInfo.colorSpaceType == 6) {
/* 1080 */       return new ICC_ProfileGray(paramProfileDeferralInfo);
/*      */     }
/* 1082 */     return new ICC_Profile(paramProfileDeferralInfo);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   void activateDeferredProfile()
/*      */     throws ProfileDataException
/*      */   {
/* 1090 */     final String str = this.deferralInfo.filename;
/*      */     
/* 1092 */     this.profileActivator = null;
/* 1093 */     this.deferralInfo = null;
/* 1094 */     PrivilegedAction local3 = new PrivilegedAction() {
/*      */       public FileInputStream run() {
/* 1096 */         File localFile = ICC_Profile.getStandardProfileFile(str);
/* 1097 */         if (localFile != null) {
/*      */           try {
/* 1099 */             return new FileInputStream(localFile);
/*      */           } catch (FileNotFoundException localFileNotFoundException) {}
/*      */         }
/* 1102 */         return null;
/*      */       } };
/*      */     FileInputStream localFileInputStream;
/* 1105 */     if ((localFileInputStream = (FileInputStream)AccessController.doPrivileged(local3)) == null)
/* 1106 */       throw new ProfileDataException("Cannot open file " + str);
/*      */     byte[] arrayOfByte;
/*      */     ProfileDataException localProfileDataException;
/* 1109 */     try { arrayOfByte = getProfileDataFromStream(localFileInputStream);
/* 1110 */       localFileInputStream.close();
/*      */     }
/*      */     catch (IOException localIOException) {
/* 1113 */       localProfileDataException = new ProfileDataException("Invalid ICC Profile Data" + str);
/*      */       
/* 1115 */       localProfileDataException.initCause(localIOException);
/* 1116 */       throw localProfileDataException;
/*      */     }
/* 1118 */     if (arrayOfByte == null) {
/* 1119 */       throw new ProfileDataException("Invalid ICC Profile Data" + str);
/*      */     }
/*      */     try
/*      */     {
/* 1123 */       this.cmmProfile = CMSManager.getModule().loadProfile(arrayOfByte);
/*      */     } catch (CMMException localCMMException) {
/* 1125 */       localProfileDataException = new ProfileDataException("Invalid ICC Profile Data" + str);
/*      */       
/* 1127 */       localProfileDataException.initCause(localCMMException);
/* 1128 */       throw localProfileDataException;
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public int getMajorVersion()
/*      */   {
/* 1140 */     byte[] arrayOfByte = getData(1751474532);
/*      */     
/*      */ 
/* 1143 */     return arrayOfByte[8];
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public int getMinorVersion()
/*      */   {
/* 1153 */     byte[] arrayOfByte = getData(1751474532);
/*      */     
/*      */ 
/* 1156 */     return arrayOfByte[9];
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public int getProfileClass()
/*      */   {
/* 1167 */     if (this.deferralInfo != null) {
/* 1168 */       return this.deferralInfo.profileClass;
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/* 1174 */     byte[] arrayOfByte = getData(1751474532);
/*      */     
/* 1176 */     int i = intFromBigEndian(arrayOfByte, 12);
/*      */     int j;
/* 1178 */     switch (i) {
/*      */     case 1935896178: 
/* 1180 */       j = 0;
/* 1181 */       break;
/*      */     
/*      */     case 1835955314: 
/* 1184 */       j = 1;
/* 1185 */       break;
/*      */     
/*      */     case 1886549106: 
/* 1188 */       j = 2;
/* 1189 */       break;
/*      */     
/*      */     case 1818848875: 
/* 1192 */       j = 3;
/* 1193 */       break;
/*      */     
/*      */     case 1936744803: 
/* 1196 */       j = 4;
/* 1197 */       break;
/*      */     
/*      */     case 1633842036: 
/* 1200 */       j = 5;
/* 1201 */       break;
/*      */     
/*      */     case 1852662636: 
/* 1204 */       j = 6;
/* 1205 */       break;
/*      */     
/*      */     default: 
/* 1208 */       throw new IllegalArgumentException("Unknown profile class");
/*      */     }
/*      */     
/* 1211 */     return j;
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
/*      */   public int getColorSpaceType()
/*      */   {
/* 1227 */     if (this.deferralInfo != null) {
/* 1228 */       return this.deferralInfo.colorSpaceType;
/*      */     }
/*      */     
/*      */ 
/*      */ 
/* 1233 */     return getColorSpaceType(this.cmmProfile);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   static int getColorSpaceType(Profile paramProfile)
/*      */   {
/* 1240 */     byte[] arrayOfByte = getData(paramProfile, 1751474532);
/* 1241 */     int i = intFromBigEndian(arrayOfByte, 16);
/* 1242 */     int j = iccCStoJCS(i);
/* 1243 */     return j;
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
/*      */   public int getPCSType()
/*      */   {
/* 1259 */     if (ProfileDeferralMgr.deferring) {
/* 1260 */       ProfileDeferralMgr.activateProfiles();
/*      */     }
/* 1262 */     return getPCSType(this.cmmProfile);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   static int getPCSType(Profile paramProfile)
/*      */   {
/* 1270 */     byte[] arrayOfByte = getData(paramProfile, 1751474532);
/* 1271 */     int i = intFromBigEndian(arrayOfByte, 20);
/* 1272 */     int j = iccCStoJCS(i);
/* 1273 */     return j;
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
/*      */   public void write(String paramString)
/*      */     throws IOException
/*      */   {
/* 1289 */     byte[] arrayOfByte = getData();
/*      */     
/* 1291 */     FileOutputStream localFileOutputStream = new FileOutputStream(paramString);
/* 1292 */     localFileOutputStream.write(arrayOfByte);
/* 1293 */     localFileOutputStream.close();
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
/*      */   public void write(OutputStream paramOutputStream)
/*      */     throws IOException
/*      */   {
/* 1308 */     byte[] arrayOfByte = getData();
/*      */     
/* 1310 */     paramOutputStream.write(arrayOfByte);
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
/*      */   public byte[] getData()
/*      */   {
/* 1323 */     if (ProfileDeferralMgr.deferring) {
/* 1324 */       ProfileDeferralMgr.activateProfiles();
/*      */     }
/*      */     
/* 1327 */     PCMM localPCMM = CMSManager.getModule();
/*      */     
/*      */ 
/* 1330 */     int i = localPCMM.getProfileSize(this.cmmProfile);
/*      */     
/* 1332 */     byte[] arrayOfByte = new byte[i];
/*      */     
/*      */ 
/* 1335 */     localPCMM.getProfileData(this.cmmProfile, arrayOfByte);
/*      */     
/* 1337 */     return arrayOfByte;
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
/*      */   public byte[] getData(int paramInt)
/*      */   {
/* 1358 */     if (ProfileDeferralMgr.deferring) {
/* 1359 */       ProfileDeferralMgr.activateProfiles();
/*      */     }
/*      */     
/* 1362 */     return getData(this.cmmProfile, paramInt);
/*      */   }
/*      */   
/*      */ 
/*      */   static byte[] getData(Profile paramProfile, int paramInt)
/*      */   {
/*      */     byte[] arrayOfByte;
/*      */     try
/*      */     {
/* 1371 */       PCMM localPCMM = CMSManager.getModule();
/*      */       
/*      */ 
/* 1374 */       int i = localPCMM.getTagSize(paramProfile, paramInt);
/*      */       
/* 1376 */       arrayOfByte = new byte[i];
/*      */       
/*      */ 
/* 1379 */       localPCMM.getTagData(paramProfile, paramInt, arrayOfByte);
/*      */     } catch (CMMException localCMMException) {
/* 1381 */       arrayOfByte = null;
/*      */     }
/*      */     
/* 1384 */     return arrayOfByte;
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
/*      */   public void setData(int paramInt, byte[] paramArrayOfByte)
/*      */   {
/* 1406 */     if (ProfileDeferralMgr.deferring) {
/* 1407 */       ProfileDeferralMgr.activateProfiles();
/*      */     }
/*      */     
/* 1410 */     CMSManager.getModule().setTagData(this.cmmProfile, paramInt, paramArrayOfByte);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   void setRenderingIntent(int paramInt)
/*      */   {
/* 1419 */     byte[] arrayOfByte = getData(1751474532);
/*      */     
/* 1421 */     intToBigEndian(paramInt, arrayOfByte, 64);
/*      */     
/* 1423 */     setData(1751474532, arrayOfByte);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   int getRenderingIntent()
/*      */   {
/* 1434 */     byte[] arrayOfByte = getData(1751474532);
/*      */     
/*      */ 
/* 1437 */     int i = intFromBigEndian(arrayOfByte, 64);
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1447 */     return 0xFFFF & i;
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
/*      */   public int getNumComponents()
/*      */   {
/* 1466 */     if (this.deferralInfo != null) {
/* 1467 */       return this.deferralInfo.numComponents;
/*      */     }
/*      */     
/*      */ 
/*      */ 
/* 1472 */     byte[] arrayOfByte = getData(1751474532);
/*      */     
/* 1474 */     int i = intFromBigEndian(arrayOfByte, 16);
/*      */     int j;
/* 1476 */     switch (i) {
/*      */     case 1196573017: 
/* 1478 */       j = 1;
/* 1479 */       break;
/*      */     
/*      */     case 843271250: 
/* 1482 */       j = 2;
/* 1483 */       break;
/*      */     
/*      */     case 860048466: 
/*      */     case 1129142560: 
/*      */     case 1212961568: 
/*      */     case 1213421088: 
/*      */     case 1281450528: 
/*      */     case 1282766368: 
/*      */     case 1380401696: 
/*      */     case 1482250784: 
/*      */     case 1497588338: 
/*      */     case 1501067552: 
/* 1495 */       j = 3;
/* 1496 */       break;
/*      */     
/*      */     case 876825682: 
/*      */     case 1129142603: 
/* 1500 */       j = 4;
/* 1501 */       break;
/*      */     
/*      */     case 893602898: 
/* 1504 */       j = 5;
/* 1505 */       break;
/*      */     
/*      */     case 910380114: 
/* 1508 */       j = 6;
/* 1509 */       break;
/*      */     
/*      */     case 927157330: 
/* 1512 */       j = 7;
/* 1513 */       break;
/*      */     
/*      */     case 943934546: 
/* 1516 */       j = 8;
/* 1517 */       break;
/*      */     
/*      */     case 960711762: 
/* 1520 */       j = 9;
/* 1521 */       break;
/*      */     
/*      */     case 1094929490: 
/* 1524 */       j = 10;
/* 1525 */       break;
/*      */     
/*      */     case 1111706706: 
/* 1528 */       j = 11;
/* 1529 */       break;
/*      */     
/*      */     case 1128483922: 
/* 1532 */       j = 12;
/* 1533 */       break;
/*      */     
/*      */     case 1145261138: 
/* 1536 */       j = 13;
/* 1537 */       break;
/*      */     
/*      */     case 1162038354: 
/* 1540 */       j = 14;
/* 1541 */       break;
/*      */     
/*      */     case 1178815570: 
/* 1544 */       j = 15;
/* 1545 */       break;
/*      */     
/*      */     default: 
/* 1548 */       throw new ProfileDataException("invalid ICC color space");
/*      */     }
/*      */     
/* 1551 */     return j;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   float[] getMediaWhitePoint()
/*      */   {
/* 1560 */     return getXYZTag(2004119668);
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
/*      */   float[] getXYZTag(int paramInt)
/*      */   {
/* 1574 */     byte[] arrayOfByte = getData(paramInt);
/*      */     
/*      */ 
/*      */ 
/* 1578 */     float[] arrayOfFloat = new float[3];
/*      */     
/*      */ 
/* 1581 */     int i = 0; for (int j = 8; i < 3; j += 4) {
/* 1582 */       int k = intFromBigEndian(arrayOfByte, j);
/* 1583 */       arrayOfFloat[i] = (k / 65536.0F);i++;
/*      */     }
/*      */     
/* 1585 */     return arrayOfFloat;
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
/*      */   float getGamma(int paramInt)
/*      */   {
/* 1605 */     byte[] arrayOfByte = getData(paramInt);
/*      */     
/*      */ 
/*      */ 
/* 1609 */     if (intFromBigEndian(arrayOfByte, 8) != 1) {
/* 1610 */       throw new ProfileDataException("TRC is not a gamma");
/*      */     }
/*      */     
/*      */ 
/* 1614 */     int i = shortFromBigEndian(arrayOfByte, 12) & 0xFFFF;
/*      */     
/* 1616 */     float f = i / 256.0F;
/*      */     
/* 1618 */     return f;
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
/*      */   short[] getTRC(int paramInt)
/*      */   {
/* 1647 */     byte[] arrayOfByte = getData(paramInt);
/*      */     
/*      */ 
/*      */ 
/* 1651 */     int k = intFromBigEndian(arrayOfByte, 8);
/*      */     
/* 1653 */     if (k == 1) {
/* 1654 */       throw new ProfileDataException("TRC is not a table");
/*      */     }
/*      */     
/*      */ 
/* 1658 */     short[] arrayOfShort = new short[k];
/*      */     
/* 1660 */     int i = 0; for (int j = 12; i < k; j += 2) {
/* 1661 */       arrayOfShort[i] = shortFromBigEndian(arrayOfByte, j);i++;
/*      */     }
/*      */     
/* 1664 */     return arrayOfShort;
/*      */   }
/*      */   
/*      */ 
/*      */   static int iccCStoJCS(int paramInt)
/*      */   {
/*      */     int i;
/*      */     
/* 1672 */     switch (paramInt) {
/*      */     case 1482250784: 
/* 1674 */       i = 0;
/* 1675 */       break;
/*      */     
/*      */     case 1281450528: 
/* 1678 */       i = 1;
/* 1679 */       break;
/*      */     
/*      */     case 1282766368: 
/* 1682 */       i = 2;
/* 1683 */       break;
/*      */     
/*      */     case 1497588338: 
/* 1686 */       i = 3;
/* 1687 */       break;
/*      */     
/*      */     case 1501067552: 
/* 1690 */       i = 4;
/* 1691 */       break;
/*      */     
/*      */     case 1380401696: 
/* 1694 */       i = 5;
/* 1695 */       break;
/*      */     
/*      */     case 1196573017: 
/* 1698 */       i = 6;
/* 1699 */       break;
/*      */     
/*      */     case 1213421088: 
/* 1702 */       i = 7;
/* 1703 */       break;
/*      */     
/*      */     case 1212961568: 
/* 1706 */       i = 8;
/* 1707 */       break;
/*      */     
/*      */     case 1129142603: 
/* 1710 */       i = 9;
/* 1711 */       break;
/*      */     
/*      */     case 1129142560: 
/* 1714 */       i = 11;
/* 1715 */       break;
/*      */     
/*      */     case 843271250: 
/* 1718 */       i = 12;
/* 1719 */       break;
/*      */     
/*      */     case 860048466: 
/* 1722 */       i = 13;
/* 1723 */       break;
/*      */     
/*      */     case 876825682: 
/* 1726 */       i = 14;
/* 1727 */       break;
/*      */     
/*      */     case 893602898: 
/* 1730 */       i = 15;
/* 1731 */       break;
/*      */     
/*      */     case 910380114: 
/* 1734 */       i = 16;
/* 1735 */       break;
/*      */     
/*      */     case 927157330: 
/* 1738 */       i = 17;
/* 1739 */       break;
/*      */     
/*      */     case 943934546: 
/* 1742 */       i = 18;
/* 1743 */       break;
/*      */     
/*      */     case 960711762: 
/* 1746 */       i = 19;
/* 1747 */       break;
/*      */     
/*      */     case 1094929490: 
/* 1750 */       i = 20;
/* 1751 */       break;
/*      */     
/*      */     case 1111706706: 
/* 1754 */       i = 21;
/* 1755 */       break;
/*      */     
/*      */     case 1128483922: 
/* 1758 */       i = 22;
/* 1759 */       break;
/*      */     
/*      */     case 1145261138: 
/* 1762 */       i = 23;
/* 1763 */       break;
/*      */     
/*      */     case 1162038354: 
/* 1766 */       i = 24;
/* 1767 */       break;
/*      */     
/*      */     case 1178815570: 
/* 1770 */       i = 25;
/* 1771 */       break;
/*      */     
/*      */     default: 
/* 1774 */       throw new IllegalArgumentException("Unknown color space");
/*      */     }
/*      */     
/* 1777 */     return i;
/*      */   }
/*      */   
/*      */   static int intFromBigEndian(byte[] paramArrayOfByte, int paramInt)
/*      */   {
/* 1782 */     return (paramArrayOfByte[paramInt] & 0xFF) << 24 | (paramArrayOfByte[(paramInt + 1)] & 0xFF) << 16 | (paramArrayOfByte[(paramInt + 2)] & 0xFF) << 8 | paramArrayOfByte[(paramInt + 3)] & 0xFF;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   static void intToBigEndian(int paramInt1, byte[] paramArrayOfByte, int paramInt2)
/*      */   {
/* 1790 */     paramArrayOfByte[paramInt2] = ((byte)(paramInt1 >> 24));
/* 1791 */     paramArrayOfByte[(paramInt2 + 1)] = ((byte)(paramInt1 >> 16));
/* 1792 */     paramArrayOfByte[(paramInt2 + 2)] = ((byte)(paramInt1 >> 8));
/* 1793 */     paramArrayOfByte[(paramInt2 + 3)] = ((byte)paramInt1);
/*      */   }
/*      */   
/*      */   static short shortFromBigEndian(byte[] paramArrayOfByte, int paramInt)
/*      */   {
/* 1798 */     return (short)((paramArrayOfByte[paramInt] & 0xFF) << 8 | paramArrayOfByte[(paramInt + 1)] & 0xFF);
/*      */   }
/*      */   
/*      */ 
/*      */   static void shortToBigEndian(short paramShort, byte[] paramArrayOfByte, int paramInt)
/*      */   {
/* 1804 */     paramArrayOfByte[paramInt] = ((byte)(paramShort >> 8));
/* 1805 */     paramArrayOfByte[(paramInt + 1)] = ((byte)paramShort);
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
/*      */   private static File getProfileFile(String paramString)
/*      */   {
/* 1821 */     File localFile = new File(paramString);
/* 1822 */     if (localFile.isAbsolute())
/*      */     {
/*      */ 
/* 1825 */       return localFile.isFile() ? localFile : null; }
/*      */     String str1;
/* 1827 */     StringTokenizer localStringTokenizer; String str2; String str3; if ((!localFile.isFile()) && 
/* 1828 */       ((str1 = System.getProperty("java.iccprofile.path")) != null))
/*      */     {
/* 1830 */       localStringTokenizer = new StringTokenizer(str1, File.pathSeparator);
/*      */       
/* 1832 */       while ((localStringTokenizer.hasMoreTokens()) && ((localFile == null) || (!localFile.isFile()))) {
/* 1833 */         str2 = localStringTokenizer.nextToken();
/* 1834 */         str3 = str2 + File.separatorChar + paramString;
/* 1835 */         localFile = new File(str3);
/* 1836 */         if (!isChildOf(localFile, str2)) {
/* 1837 */           localFile = null;
/*      */         }
/*      */       }
/*      */     }
/*      */     
/* 1842 */     if (((localFile == null) || (!localFile.isFile())) && 
/* 1843 */       ((str1 = System.getProperty("java.class.path")) != null))
/*      */     {
/* 1845 */       localStringTokenizer = new StringTokenizer(str1, File.pathSeparator);
/*      */       
/* 1847 */       while ((localStringTokenizer.hasMoreTokens()) && ((localFile == null) || (!localFile.isFile()))) {
/* 1848 */         str2 = localStringTokenizer.nextToken();
/* 1849 */         str3 = str2 + File.separatorChar + paramString;
/* 1850 */         localFile = new File(str3);
/*      */       }
/*      */     }
/*      */     
/* 1854 */     if ((localFile == null) || (!localFile.isFile()))
/*      */     {
/* 1856 */       localFile = getStandardProfileFile(paramString);
/*      */     }
/* 1858 */     if ((localFile != null) && (localFile.isFile())) {
/* 1859 */       return localFile;
/*      */     }
/* 1861 */     return null;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private static File getStandardProfileFile(String paramString)
/*      */   {
/* 1871 */     String str1 = System.getProperty("java.home") + File.separatorChar + "lib" + File.separatorChar + "cmm";
/*      */     
/* 1873 */     String str2 = str1 + File.separatorChar + paramString;
/* 1874 */     File localFile = new File(str2);
/* 1875 */     return (localFile.isFile()) && (isChildOf(localFile, str1)) ? localFile : null;
/*      */   }
/*      */   
/*      */ 
/*      */   private static boolean isChildOf(File paramFile, String paramString)
/*      */   {
/*      */     try
/*      */     {
/* 1883 */       File localFile = new File(paramString);
/* 1884 */       String str1 = localFile.getCanonicalPath();
/* 1885 */       if (!str1.endsWith(File.separator)) {
/* 1886 */         str1 = str1 + File.separator;
/*      */       }
/* 1888 */       String str2 = paramFile.getCanonicalPath();
/* 1889 */       return str2.startsWith(str1);
/*      */     }
/*      */     catch (IOException localIOException) {}
/*      */     
/*      */ 
/* 1894 */     return false;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   private static boolean standardProfileExists(String paramString)
/*      */   {
/* 1902 */     ((Boolean)AccessController.doPrivileged(new PrivilegedAction() {
/*      */       public Boolean run() {
/* 1904 */         return Boolean.valueOf(ICC_Profile.getStandardProfileFile(this.val$fileName) != null);
/*      */       }
/*      */     })).booleanValue();
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
/* 1945 */   private int iccProfileSerializedDataVersion = 1;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private transient ICC_Profile resolvedDeserializedProfile;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private void writeObject(ObjectOutputStream paramObjectOutputStream)
/*      */     throws IOException
/*      */   {
/* 1975 */     paramObjectOutputStream.defaultWriteObject();
/*      */     
/* 1977 */     String str = null;
/* 1978 */     if (this == sRGBprofile) {
/* 1979 */       str = "CS_sRGB";
/* 1980 */     } else if (this == XYZprofile) {
/* 1981 */       str = "CS_CIEXYZ";
/* 1982 */     } else if (this == PYCCprofile) {
/* 1983 */       str = "CS_PYCC";
/* 1984 */     } else if (this == GRAYprofile) {
/* 1985 */       str = "CS_GRAY";
/* 1986 */     } else if (this == LINEAR_RGBprofile) {
/* 1987 */       str = "CS_LINEAR_RGB";
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1994 */     byte[] arrayOfByte = null;
/* 1995 */     if (str == null)
/*      */     {
/* 1997 */       arrayOfByte = getData();
/*      */     }
/*      */     
/* 2000 */     paramObjectOutputStream.writeObject(str);
/* 2001 */     paramObjectOutputStream.writeObject(arrayOfByte);
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
/*      */   private void readObject(ObjectInputStream paramObjectInputStream)
/*      */     throws IOException, ClassNotFoundException
/*      */   {
/* 2043 */     paramObjectInputStream.defaultReadObject();
/*      */     
/* 2045 */     String str = (String)paramObjectInputStream.readObject();
/* 2046 */     byte[] arrayOfByte = (byte[])paramObjectInputStream.readObject();
/*      */     
/* 2048 */     int i = 0;
/* 2049 */     int j = 0;
/* 2050 */     if (str != null) {
/* 2051 */       j = 1;
/* 2052 */       if (str.equals("CS_sRGB")) {
/* 2053 */         i = 1000;
/* 2054 */       } else if (str.equals("CS_CIEXYZ")) {
/* 2055 */         i = 1001;
/* 2056 */       } else if (str.equals("CS_PYCC")) {
/* 2057 */         i = 1002;
/* 2058 */       } else if (str.equals("CS_GRAY")) {
/* 2059 */         i = 1003;
/* 2060 */       } else if (str.equals("CS_LINEAR_RGB")) {
/* 2061 */         i = 1004;
/*      */       } else {
/* 2063 */         j = 0;
/*      */       }
/*      */     }
/*      */     
/* 2067 */     if (j != 0) {
/* 2068 */       this.resolvedDeserializedProfile = getInstance(i);
/*      */     } else {
/* 2070 */       this.resolvedDeserializedProfile = getInstance(arrayOfByte);
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   protected Object readResolve()
/*      */     throws ObjectStreamException
/*      */   {
/* 2083 */     return this.resolvedDeserializedProfile;
/*      */   }
/*      */ }


/* Location:              /Users/guanxiaoda/Documents/workspace/rt.jar!/java/awt/color/ICC_Profile.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */