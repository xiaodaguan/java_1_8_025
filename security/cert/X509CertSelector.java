/*      */ package java.security.cert;
/*      */ 
/*      */ import java.io.IOException;
/*      */ import java.math.BigInteger;
/*      */ import java.security.PublicKey;
/*      */ import java.util.ArrayList;
/*      */ import java.util.Arrays;
/*      */ import java.util.Collection;
/*      */ import java.util.Collections;
/*      */ import java.util.Date;
/*      */ import java.util.HashSet;
/*      */ import java.util.Iterator;
/*      */ import java.util.List;
/*      */ import java.util.Set;
/*      */ import java.util.Vector;
/*      */ import javax.security.auth.x500.X500Principal;
/*      */ import sun.misc.HexDumpEncoder;
/*      */ import sun.security.util.Debug;
/*      */ import sun.security.util.DerInputStream;
/*      */ import sun.security.util.DerValue;
/*      */ import sun.security.util.ObjectIdentifier;
/*      */ import sun.security.x509.AlgorithmId;
/*      */ import sun.security.x509.CertificatePoliciesExtension;
/*      */ import sun.security.x509.CertificatePolicyId;
/*      */ import sun.security.x509.CertificatePolicySet;
/*      */ import sun.security.x509.DNSName;
/*      */ import sun.security.x509.EDIPartyName;
/*      */ import sun.security.x509.ExtendedKeyUsageExtension;
/*      */ import sun.security.x509.GeneralName;
/*      */ import sun.security.x509.GeneralNameInterface;
/*      */ import sun.security.x509.GeneralNames;
/*      */ import sun.security.x509.GeneralSubtree;
/*      */ import sun.security.x509.GeneralSubtrees;
/*      */ import sun.security.x509.IPAddressName;
/*      */ import sun.security.x509.NameConstraintsExtension;
/*      */ import sun.security.x509.OIDName;
/*      */ import sun.security.x509.OtherName;
/*      */ import sun.security.x509.PolicyInformation;
/*      */ import sun.security.x509.PrivateKeyUsageExtension;
/*      */ import sun.security.x509.RFC822Name;
/*      */ import sun.security.x509.SubjectAlternativeNameExtension;
/*      */ import sun.security.x509.URIName;
/*      */ import sun.security.x509.X400Address;
/*      */ import sun.security.x509.X500Name;
/*      */ import sun.security.x509.X509CertImpl;
/*      */ import sun.security.x509.X509Key;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class X509CertSelector
/*      */   implements CertSelector
/*      */ {
/*   88 */   private static final Debug debug = Debug.getInstance("certpath");
/*      */   
/*      */ 
/*   91 */   private static final ObjectIdentifier ANY_EXTENDED_KEY_USAGE = ObjectIdentifier.newInternal(new int[] { 2, 5, 29, 37, 0 });
/*      */   
/*      */   private BigInteger serialNumber;
/*      */   
/*      */   private X500Principal issuer;
/*      */   
/*      */   private X500Principal subject;
/*      */   
/*      */   private byte[] subjectKeyID;
/*      */   
/*      */   private byte[] authorityKeyID;
/*      */   private Date certificateValid;
/*      */   private Date privateKeyValid;
/*      */   private ObjectIdentifier subjectPublicKeyAlgID;
/*      */   private PublicKey subjectPublicKey;
/*      */   private byte[] subjectPublicKeyBytes;
/*      */   private boolean[] keyUsage;
/*      */   private Set<String> keyPurposeSet;
/*      */   private Set<ObjectIdentifier> keyPurposeOIDSet;
/*      */   private Set<List<?>> subjectAlternativeNames;
/*      */   private Set<GeneralNameInterface> subjectAlternativeGeneralNames;
/*      */   private CertificatePolicySet policy;
/*      */   private Set<String> policySet;
/*      */   private Set<List<?>> pathToNames;
/*      */   private Set<GeneralNameInterface> pathToGeneralNames;
/*      */   private NameConstraintsExtension nc;
/*      */   private byte[] ncBytes;
/*  118 */   private int basicConstraints = -1;
/*      */   private X509Certificate x509Cert;
/*  120 */   private boolean matchAllSubjectAltNames = true;
/*      */   private static final Boolean FALSE;
/*      */   private static final int PRIVATE_KEY_USAGE_ID = 0;
/*      */   private static final int SUBJECT_ALT_NAME_ID = 1;
/*      */   private static final int NAME_CONSTRAINTS_ID = 2;
/*      */   private static final int CERT_POLICIES_ID = 3;
/*      */   private static final int EXTENDED_KEY_USAGE_ID = 4;
/*      */   private static final int NUM_OF_EXTENSIONS = 5;
/*      */   private static final String[] EXTENSION_OIDS;
/*      */   static final int NAME_ANY = 0;
/*      */   static final int NAME_RFC822 = 1;
/*      */   static final int NAME_DNS = 2;
/*      */   static final int NAME_X400 = 3;
/*      */   static final int NAME_DIRECTORY = 4;
/*      */   static final int NAME_EDI = 5;
/*      */   static final int NAME_URI = 6;
/*      */   static final int NAME_IP = 7;
/*      */   static final int NAME_OID = 8;
/*      */   
/*      */   static
/*      */   {
/*   94 */     CertPathHelperImpl.initialize();
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  122 */     FALSE = Boolean.FALSE;
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  130 */     EXTENSION_OIDS = new String[5];
/*      */     
/*      */ 
/*  133 */     EXTENSION_OIDS[0] = "2.5.29.16";
/*  134 */     EXTENSION_OIDS[1] = "2.5.29.17";
/*  135 */     EXTENSION_OIDS[2] = "2.5.29.30";
/*  136 */     EXTENSION_OIDS[3] = "2.5.29.32";
/*  137 */     EXTENSION_OIDS[4] = "2.5.29.37";
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
/*      */   public void setCertificate(X509Certificate paramX509Certificate)
/*      */   {
/*  175 */     this.x509Cert = paramX509Certificate;
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
/*      */   public void setSerialNumber(BigInteger paramBigInteger)
/*      */   {
/*  189 */     this.serialNumber = paramBigInteger;
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
/*      */   public void setIssuer(X500Principal paramX500Principal)
/*      */   {
/*  203 */     this.issuer = paramX500Principal;
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
/*      */   public void setIssuer(String paramString)
/*      */     throws IOException
/*      */   {
/*  227 */     if (paramString == null) {
/*  228 */       this.issuer = null;
/*      */     } else {
/*  230 */       this.issuer = new X500Name(paramString).asX500Principal();
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
/*      */   public void setIssuer(byte[] paramArrayOfByte)
/*      */     throws IOException
/*      */   {
/*      */     try
/*      */     {
/*  278 */       this.issuer = (paramArrayOfByte == null ? null : new X500Principal(paramArrayOfByte));
/*      */     } catch (IllegalArgumentException localIllegalArgumentException) {
/*  280 */       throw new IOException("Invalid name", localIllegalArgumentException);
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
/*      */   public void setSubject(X500Principal paramX500Principal)
/*      */   {
/*  295 */     this.subject = paramX500Principal;
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
/*      */   public void setSubject(String paramString)
/*      */     throws IOException
/*      */   {
/*  318 */     if (paramString == null) {
/*  319 */       this.subject = null;
/*      */     } else {
/*  321 */       this.subject = new X500Name(paramString).asX500Principal();
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
/*      */   public void setSubject(byte[] paramArrayOfByte)
/*      */     throws IOException
/*      */   {
/*      */     try
/*      */     {
/*  342 */       this.subject = (paramArrayOfByte == null ? null : new X500Principal(paramArrayOfByte));
/*      */     } catch (IllegalArgumentException localIllegalArgumentException) {
/*  344 */       throw new IOException("Invalid name", localIllegalArgumentException);
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
/*      */   public void setSubjectKeyIdentifier(byte[] paramArrayOfByte)
/*      */   {
/*  381 */     if (paramArrayOfByte == null) {
/*  382 */       this.subjectKeyID = null;
/*      */     } else {
/*  384 */       this.subjectKeyID = ((byte[])paramArrayOfByte.clone());
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
/*      */   public void setAuthorityKeyIdentifier(byte[] paramArrayOfByte)
/*      */   {
/*  442 */     if (paramArrayOfByte == null) {
/*  443 */       this.authorityKeyID = null;
/*      */     } else {
/*  445 */       this.authorityKeyID = ((byte[])paramArrayOfByte.clone());
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
/*      */   public void setCertificateValid(Date paramDate)
/*      */   {
/*  462 */     if (paramDate == null) {
/*  463 */       this.certificateValid = null;
/*      */     } else {
/*  465 */       this.certificateValid = ((Date)paramDate.clone());
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
/*      */   public void setPrivateKeyValid(Date paramDate)
/*      */   {
/*  483 */     if (paramDate == null) {
/*  484 */       this.privateKeyValid = null;
/*      */     } else {
/*  486 */       this.privateKeyValid = ((Date)paramDate.clone());
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
/*      */   public void setSubjectPublicKeyAlgID(String paramString)
/*      */     throws IOException
/*      */   {
/*  506 */     if (paramString == null) {
/*  507 */       this.subjectPublicKeyAlgID = null;
/*      */     } else {
/*  509 */       this.subjectPublicKeyAlgID = new ObjectIdentifier(paramString);
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
/*      */   public void setSubjectPublicKey(PublicKey paramPublicKey)
/*      */   {
/*  522 */     if (paramPublicKey == null) {
/*  523 */       this.subjectPublicKey = null;
/*  524 */       this.subjectPublicKeyBytes = null;
/*      */     } else {
/*  526 */       this.subjectPublicKey = paramPublicKey;
/*  527 */       this.subjectPublicKeyBytes = paramPublicKey.getEncoded();
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
/*      */   public void setSubjectPublicKey(byte[] paramArrayOfByte)
/*      */     throws IOException
/*      */   {
/*  565 */     if (paramArrayOfByte == null) {
/*  566 */       this.subjectPublicKey = null;
/*  567 */       this.subjectPublicKeyBytes = null;
/*      */     } else {
/*  569 */       this.subjectPublicKeyBytes = ((byte[])paramArrayOfByte.clone());
/*  570 */       this.subjectPublicKey = X509Key.parse(new DerValue(this.subjectPublicKeyBytes));
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
/*      */   public void setKeyUsage(boolean[] paramArrayOfBoolean)
/*      */   {
/*  590 */     if (paramArrayOfBoolean == null) {
/*  591 */       this.keyUsage = null;
/*      */     } else {
/*  593 */       this.keyUsage = ((boolean[])paramArrayOfBoolean.clone());
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
/*      */   public void setExtendedKeyUsage(Set<String> paramSet)
/*      */     throws IOException
/*      */   {
/*  617 */     if ((paramSet == null) || (paramSet.isEmpty())) {
/*  618 */       this.keyPurposeSet = null;
/*  619 */       this.keyPurposeOIDSet = null;
/*      */     }
/*      */     else {
/*  622 */       this.keyPurposeSet = Collections.unmodifiableSet(new HashSet(paramSet));
/*  623 */       this.keyPurposeOIDSet = new HashSet();
/*  624 */       for (String str : this.keyPurposeSet) {
/*  625 */         this.keyPurposeOIDSet.add(new ObjectIdentifier(str));
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
/*      */   public void setMatchAllSubjectAltNames(boolean paramBoolean)
/*      */   {
/*  647 */     this.matchAllSubjectAltNames = paramBoolean;
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
/*      */   public void setSubjectAlternativeNames(Collection<List<?>> paramCollection)
/*      */     throws IOException
/*      */   {
/*  699 */     if (paramCollection == null) {
/*  700 */       this.subjectAlternativeNames = null;
/*  701 */       this.subjectAlternativeGeneralNames = null;
/*      */     } else {
/*  703 */       if (paramCollection.isEmpty()) {
/*  704 */         this.subjectAlternativeNames = null;
/*  705 */         this.subjectAlternativeGeneralNames = null;
/*  706 */         return;
/*      */       }
/*  708 */       Set localSet = cloneAndCheckNames(paramCollection);
/*      */       
/*  710 */       this.subjectAlternativeGeneralNames = parseNames(localSet);
/*  711 */       this.subjectAlternativeNames = localSet;
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
/*      */   public void addSubjectAlternativeName(int paramInt, String paramString)
/*      */     throws IOException
/*      */   {
/*  755 */     addSubjectAlternativeNameInternal(paramInt, paramString);
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
/*      */   public void addSubjectAlternativeName(int paramInt, byte[] paramArrayOfByte)
/*      */     throws IOException
/*      */   {
/*  800 */     addSubjectAlternativeNameInternal(paramInt, paramArrayOfByte.clone());
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
/*      */   private void addSubjectAlternativeNameInternal(int paramInt, Object paramObject)
/*      */     throws IOException
/*      */   {
/*  816 */     GeneralNameInterface localGeneralNameInterface = makeGeneralNameInterface(paramInt, paramObject);
/*  817 */     if (this.subjectAlternativeNames == null) {
/*  818 */       this.subjectAlternativeNames = new HashSet();
/*      */     }
/*  820 */     if (this.subjectAlternativeGeneralNames == null) {
/*  821 */       this.subjectAlternativeGeneralNames = new HashSet();
/*      */     }
/*  823 */     ArrayList localArrayList = new ArrayList(2);
/*  824 */     localArrayList.add(Integer.valueOf(paramInt));
/*  825 */     localArrayList.add(paramObject);
/*  826 */     this.subjectAlternativeNames.add(localArrayList);
/*  827 */     this.subjectAlternativeGeneralNames.add(localGeneralNameInterface);
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
/*      */   private static Set<GeneralNameInterface> parseNames(Collection<List<?>> paramCollection)
/*      */     throws IOException
/*      */   {
/*  848 */     HashSet localHashSet = new HashSet();
/*  849 */     for (List localList : paramCollection) {
/*  850 */       if (localList.size() != 2) {
/*  851 */         throw new IOException("name list size not 2");
/*      */       }
/*  853 */       Object localObject = localList.get(0);
/*  854 */       if (!(localObject instanceof Integer)) {
/*  855 */         throw new IOException("expected an Integer");
/*      */       }
/*  857 */       int i = ((Integer)localObject).intValue();
/*  858 */       localObject = localList.get(1);
/*  859 */       localHashSet.add(makeGeneralNameInterface(i, localObject));
/*      */     }
/*      */     
/*  862 */     return localHashSet;
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
/*      */   static boolean equalNames(Collection<?> paramCollection1, Collection<?> paramCollection2)
/*      */   {
/*  876 */     if ((paramCollection1 == null) || (paramCollection2 == null)) {
/*  877 */       return paramCollection1 == paramCollection2;
/*      */     }
/*  879 */     return paramCollection1.equals(paramCollection2);
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
/*      */   static GeneralNameInterface makeGeneralNameInterface(int paramInt, Object paramObject)
/*      */     throws IOException
/*      */   {
/*  900 */     if (debug != null) {
/*  901 */       debug.println("X509CertSelector.makeGeneralNameInterface(" + paramInt + ")...");
/*      */     }
/*      */     
/*      */     Object localObject;
/*  905 */     if ((paramObject instanceof String)) {
/*  906 */       if (debug != null) {
/*  907 */         debug.println("X509CertSelector.makeGeneralNameInterface() name is String: " + paramObject);
/*      */       }
/*      */       
/*  910 */       switch (paramInt) {
/*      */       case 1: 
/*  912 */         localObject = new RFC822Name((String)paramObject);
/*  913 */         break;
/*      */       case 2: 
/*  915 */         localObject = new DNSName((String)paramObject);
/*  916 */         break;
/*      */       case 4: 
/*  918 */         localObject = new X500Name((String)paramObject);
/*  919 */         break;
/*      */       case 6: 
/*  921 */         localObject = new URIName((String)paramObject);
/*  922 */         break;
/*      */       case 7: 
/*  924 */         localObject = new IPAddressName((String)paramObject);
/*  925 */         break;
/*      */       case 8: 
/*  927 */         localObject = new OIDName((String)paramObject);
/*  928 */         break;
/*      */       case 3: case 5: default: 
/*  930 */         throw new IOException("unable to parse String names of type " + paramInt);
/*      */       }
/*      */       
/*  933 */       if (debug != null) {
/*  934 */         debug.println("X509CertSelector.makeGeneralNameInterface() result: " + localObject
/*  935 */           .toString());
/*      */       }
/*  937 */     } else if ((paramObject instanceof byte[])) {
/*  938 */       DerValue localDerValue = new DerValue((byte[])paramObject);
/*  939 */       if (debug != null)
/*      */       {
/*  941 */         debug.println("X509CertSelector.makeGeneralNameInterface() is byte[]");
/*      */       }
/*      */       
/*  944 */       switch (paramInt) {
/*      */       case 0: 
/*  946 */         localObject = new OtherName(localDerValue);
/*  947 */         break;
/*      */       case 1: 
/*  949 */         localObject = new RFC822Name(localDerValue);
/*  950 */         break;
/*      */       case 2: 
/*  952 */         localObject = new DNSName(localDerValue);
/*  953 */         break;
/*      */       case 3: 
/*  955 */         localObject = new X400Address(localDerValue);
/*  956 */         break;
/*      */       case 4: 
/*  958 */         localObject = new X500Name(localDerValue);
/*  959 */         break;
/*      */       case 5: 
/*  961 */         localObject = new EDIPartyName(localDerValue);
/*  962 */         break;
/*      */       case 6: 
/*  964 */         localObject = new URIName(localDerValue);
/*  965 */         break;
/*      */       case 7: 
/*  967 */         localObject = new IPAddressName(localDerValue);
/*  968 */         break;
/*      */       case 8: 
/*  970 */         localObject = new OIDName(localDerValue);
/*  971 */         break;
/*      */       default: 
/*  973 */         throw new IOException("unable to parse byte array names of type " + paramInt);
/*      */       }
/*      */       
/*  976 */       if (debug != null) {
/*  977 */         debug.println("X509CertSelector.makeGeneralNameInterface() result: " + localObject
/*  978 */           .toString());
/*      */       }
/*      */     } else {
/*  981 */       if (debug != null) {
/*  982 */         debug.println("X509CertSelector.makeGeneralName() input name not String or byte array");
/*      */       }
/*      */       
/*  985 */       throw new IOException("name not String or byte array");
/*      */     }
/*  987 */     return (GeneralNameInterface)localObject;
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
/*      */   public void setNameConstraints(byte[] paramArrayOfByte)
/*      */     throws IOException
/*      */   {
/* 1040 */     if (paramArrayOfByte == null) {
/* 1041 */       this.ncBytes = null;
/* 1042 */       this.nc = null;
/*      */     } else {
/* 1044 */       this.ncBytes = ((byte[])paramArrayOfByte.clone());
/* 1045 */       this.nc = new NameConstraintsExtension(FALSE, paramArrayOfByte);
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
/*      */   public void setBasicConstraints(int paramInt)
/*      */   {
/* 1066 */     if (paramInt < -2) {
/* 1067 */       throw new IllegalArgumentException("basic constraints less than -2");
/*      */     }
/* 1069 */     this.basicConstraints = paramInt;
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
/*      */   public void setPolicy(Set<String> paramSet)
/*      */     throws IOException
/*      */   {
/* 1093 */     if (paramSet == null) {
/* 1094 */       this.policySet = null;
/* 1095 */       this.policy = null;
/*      */     }
/*      */     else
/*      */     {
/* 1099 */       Set localSet = Collections.unmodifiableSet(new HashSet(paramSet));
/*      */       
/* 1101 */       Iterator localIterator = localSet.iterator();
/* 1102 */       Vector localVector = new Vector();
/* 1103 */       while (localIterator.hasNext()) {
/* 1104 */         Object localObject = localIterator.next();
/* 1105 */         if (!(localObject instanceof String)) {
/* 1106 */           throw new IOException("non String in certPolicySet");
/*      */         }
/* 1108 */         localVector.add(new CertificatePolicyId(new ObjectIdentifier((String)localObject)));
/*      */       }
/*      */       
/*      */ 
/* 1112 */       this.policySet = localSet;
/* 1113 */       this.policy = new CertificatePolicySet(localVector);
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
/*      */   public void setPathToNames(Collection<List<?>> paramCollection)
/*      */     throws IOException
/*      */   {
/* 1169 */     if ((paramCollection == null) || (paramCollection.isEmpty())) {
/* 1170 */       this.pathToNames = null;
/* 1171 */       this.pathToGeneralNames = null;
/*      */     } else {
/* 1173 */       Set localSet = cloneAndCheckNames(paramCollection);
/* 1174 */       this.pathToGeneralNames = parseNames(localSet);
/*      */       
/* 1176 */       this.pathToNames = localSet;
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   void setPathToNamesInternal(Set<GeneralNameInterface> paramSet)
/*      */   {
/* 1184 */     this.pathToNames = Collections.emptySet();
/* 1185 */     this.pathToGeneralNames = paramSet;
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
/*      */   public void addPathToName(int paramInt, String paramString)
/*      */     throws IOException
/*      */   {
/* 1222 */     addPathToNameInternal(paramInt, paramString);
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
/*      */   public void addPathToName(int paramInt, byte[] paramArrayOfByte)
/*      */     throws IOException
/*      */   {
/* 1252 */     addPathToNameInternal(paramInt, paramArrayOfByte.clone());
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
/*      */   private void addPathToNameInternal(int paramInt, Object paramObject)
/*      */     throws IOException
/*      */   {
/* 1268 */     GeneralNameInterface localGeneralNameInterface = makeGeneralNameInterface(paramInt, paramObject);
/* 1269 */     if (this.pathToGeneralNames == null) {
/* 1270 */       this.pathToNames = new HashSet();
/* 1271 */       this.pathToGeneralNames = new HashSet();
/*      */     }
/* 1273 */     ArrayList localArrayList = new ArrayList(2);
/* 1274 */     localArrayList.add(Integer.valueOf(paramInt));
/* 1275 */     localArrayList.add(paramObject);
/* 1276 */     this.pathToNames.add(localArrayList);
/* 1277 */     this.pathToGeneralNames.add(localGeneralNameInterface);
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
/*      */   public X509Certificate getCertificate()
/*      */   {
/* 1290 */     return this.x509Cert;
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
/*      */   public BigInteger getSerialNumber()
/*      */   {
/* 1304 */     return this.serialNumber;
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
/*      */   public X500Principal getIssuer()
/*      */   {
/* 1318 */     return this.issuer;
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
/*      */   public String getIssuerAsString()
/*      */   {
/* 1340 */     return this.issuer == null ? null : this.issuer.getName();
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
/*      */   public byte[] getIssuerAsBytes()
/*      */     throws IOException
/*      */   {
/* 1363 */     return this.issuer == null ? null : this.issuer.getEncoded();
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
/*      */   public X500Principal getSubject()
/*      */   {
/* 1377 */     return this.subject;
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
/*      */   public String getSubjectAsString()
/*      */   {
/* 1399 */     return this.subject == null ? null : this.subject.getName();
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
/*      */   public byte[] getSubjectAsBytes()
/*      */     throws IOException
/*      */   {
/* 1422 */     return this.subject == null ? null : this.subject.getEncoded();
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
/*      */   public byte[] getSubjectKeyIdentifier()
/*      */   {
/* 1438 */     if (this.subjectKeyID == null) {
/* 1439 */       return null;
/*      */     }
/* 1441 */     return (byte[])this.subjectKeyID.clone();
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
/*      */   public byte[] getAuthorityKeyIdentifier()
/*      */   {
/* 1457 */     if (this.authorityKeyID == null) {
/* 1458 */       return null;
/*      */     }
/* 1460 */     return (byte[])this.authorityKeyID.clone();
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
/*      */   public Date getCertificateValid()
/*      */   {
/* 1476 */     if (this.certificateValid == null) {
/* 1477 */       return null;
/*      */     }
/* 1479 */     return (Date)this.certificateValid.clone();
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
/*      */   public Date getPrivateKeyValid()
/*      */   {
/* 1495 */     if (this.privateKeyValid == null) {
/* 1496 */       return null;
/*      */     }
/* 1498 */     return (Date)this.privateKeyValid.clone();
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
/*      */   public String getSubjectPublicKeyAlgID()
/*      */   {
/* 1513 */     if (this.subjectPublicKeyAlgID == null) {
/* 1514 */       return null;
/*      */     }
/* 1516 */     return this.subjectPublicKeyAlgID.toString();
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public PublicKey getSubjectPublicKey()
/*      */   {
/* 1528 */     return this.subjectPublicKey;
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
/*      */   public boolean[] getKeyUsage()
/*      */   {
/* 1546 */     if (this.keyUsage == null) {
/* 1547 */       return null;
/*      */     }
/* 1549 */     return (boolean[])this.keyUsage.clone();
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
/*      */   public Set<String> getExtendedKeyUsage()
/*      */   {
/* 1565 */     return this.keyPurposeSet;
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
/*      */   public boolean getMatchAllSubjectAltNames()
/*      */   {
/* 1585 */     return this.matchAllSubjectAltNames;
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
/*      */   public Collection<List<?>> getSubjectAlternativeNames()
/*      */   {
/* 1622 */     if (this.subjectAlternativeNames == null) {
/* 1623 */       return null;
/*      */     }
/* 1625 */     return cloneNames(this.subjectAlternativeNames);
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
/*      */   private static Set<List<?>> cloneNames(Collection<List<?>> paramCollection)
/*      */   {
/*      */     try
/*      */     {
/* 1650 */       return cloneAndCheckNames(paramCollection);
/*      */     }
/*      */     catch (IOException localIOException) {
/* 1653 */       throw new RuntimeException("cloneNames encountered IOException: " + localIOException.getMessage());
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
/*      */   private static Set<List<?>> cloneAndCheckNames(Collection<List<?>> paramCollection)
/*      */     throws IOException
/*      */   {
/* 1674 */     HashSet localHashSet = new HashSet();
/* 1675 */     for (Iterator localIterator = paramCollection.iterator(); localIterator.hasNext();) { localList1 = (List)localIterator.next();
/*      */       
/* 1677 */       localHashSet.add(new ArrayList(localList1));
/*      */     }
/*      */     
/*      */     List localList1;
/* 1681 */     for (localIterator = localHashSet.iterator(); localIterator.hasNext();) { localList1 = (List)localIterator.next();
/*      */       
/* 1683 */       List localList2 = localList1;
/* 1684 */       if (localList2.size() != 2) {
/* 1685 */         throw new IOException("name list size not 2");
/*      */       }
/* 1687 */       Object localObject1 = localList2.get(0);
/* 1688 */       if (!(localObject1 instanceof Integer)) {
/* 1689 */         throw new IOException("expected an Integer");
/*      */       }
/* 1691 */       int i = ((Integer)localObject1).intValue();
/* 1692 */       if ((i < 0) || (i > 8)) {
/* 1693 */         throw new IOException("name type not 0-8");
/*      */       }
/* 1695 */       Object localObject2 = localList2.get(1);
/* 1696 */       if ((!(localObject2 instanceof byte[])) && (!(localObject2 instanceof String)))
/*      */       {
/* 1698 */         if (debug != null) {
/* 1699 */           debug.println("X509CertSelector.cloneAndCheckNames() name not byte array");
/*      */         }
/*      */         
/* 1702 */         throw new IOException("name not byte array or String");
/*      */       }
/* 1704 */       if ((localObject2 instanceof byte[])) {
/* 1705 */         localList2.set(1, ((byte[])localObject2).clone());
/*      */       }
/*      */     }
/* 1708 */     return localHashSet;
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
/*      */   public byte[] getNameConstraints()
/*      */   {
/* 1732 */     if (this.ncBytes == null) {
/* 1733 */       return null;
/*      */     }
/* 1735 */     return (byte[])this.ncBytes.clone();
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
/*      */   public int getBasicConstraints()
/*      */   {
/* 1750 */     return this.basicConstraints;
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
/*      */   public Set<String> getPolicy()
/*      */   {
/* 1766 */     return this.policySet;
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
/*      */   public Collection<List<?>> getPathToNames()
/*      */   {
/* 1801 */     if (this.pathToNames == null) {
/* 1802 */       return null;
/*      */     }
/* 1804 */     return cloneNames(this.pathToNames);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public String toString()
/*      */   {
/* 1814 */     StringBuffer localStringBuffer = new StringBuffer();
/* 1815 */     localStringBuffer.append("X509CertSelector: [\n");
/* 1816 */     if (this.x509Cert != null) {
/* 1817 */       localStringBuffer.append("  Certificate: " + this.x509Cert.toString() + "\n");
/*      */     }
/* 1819 */     if (this.serialNumber != null) {
/* 1820 */       localStringBuffer.append("  Serial Number: " + this.serialNumber.toString() + "\n");
/*      */     }
/* 1822 */     if (this.issuer != null) {
/* 1823 */       localStringBuffer.append("  Issuer: " + getIssuerAsString() + "\n");
/*      */     }
/* 1825 */     if (this.subject != null) {
/* 1826 */       localStringBuffer.append("  Subject: " + getSubjectAsString() + "\n");
/*      */     }
/* 1828 */     localStringBuffer.append("  matchAllSubjectAltNames flag: " + 
/* 1829 */       String.valueOf(this.matchAllSubjectAltNames) + "\n");
/* 1830 */     Object localObject; if (this.subjectAlternativeNames != null) {
/* 1831 */       localStringBuffer.append("  SubjectAlternativeNames:\n");
/* 1832 */       localObject = this.subjectAlternativeNames.iterator();
/* 1833 */       while (((Iterator)localObject).hasNext()) {
/* 1834 */         List localList = (List)((Iterator)localObject).next();
/* 1835 */         localStringBuffer.append("    type " + localList.get(0) + ", name " + localList
/* 1836 */           .get(1) + "\n");
/*      */       }
/*      */     }
/* 1839 */     if (this.subjectKeyID != null) {
/* 1840 */       localObject = new HexDumpEncoder();
/* 1841 */       localStringBuffer.append("  Subject Key Identifier: " + ((HexDumpEncoder)localObject)
/* 1842 */         .encodeBuffer(this.subjectKeyID) + "\n");
/*      */     }
/* 1844 */     if (this.authorityKeyID != null) {
/* 1845 */       localObject = new HexDumpEncoder();
/* 1846 */       localStringBuffer.append("  Authority Key Identifier: " + ((HexDumpEncoder)localObject)
/* 1847 */         .encodeBuffer(this.authorityKeyID) + "\n");
/*      */     }
/* 1849 */     if (this.certificateValid != null) {
/* 1850 */       localStringBuffer.append("  Certificate Valid: " + this.certificateValid
/* 1851 */         .toString() + "\n");
/*      */     }
/* 1853 */     if (this.privateKeyValid != null) {
/* 1854 */       localStringBuffer.append("  Private Key Valid: " + this.privateKeyValid
/* 1855 */         .toString() + "\n");
/*      */     }
/* 1857 */     if (this.subjectPublicKeyAlgID != null) {
/* 1858 */       localStringBuffer.append("  Subject Public Key AlgID: " + this.subjectPublicKeyAlgID
/* 1859 */         .toString() + "\n");
/*      */     }
/* 1861 */     if (this.subjectPublicKey != null) {
/* 1862 */       localStringBuffer.append("  Subject Public Key: " + this.subjectPublicKey
/* 1863 */         .toString() + "\n");
/*      */     }
/* 1865 */     if (this.keyUsage != null) {
/* 1866 */       localStringBuffer.append("  Key Usage: " + keyUsageToString(this.keyUsage) + "\n");
/*      */     }
/* 1868 */     if (this.keyPurposeSet != null) {
/* 1869 */       localStringBuffer.append("  Extended Key Usage: " + this.keyPurposeSet
/* 1870 */         .toString() + "\n");
/*      */     }
/* 1872 */     if (this.policy != null) {
/* 1873 */       localStringBuffer.append("  Policy: " + this.policy.toString() + "\n");
/*      */     }
/* 1875 */     if (this.pathToGeneralNames != null) {
/* 1876 */       localStringBuffer.append("  Path to names:\n");
/* 1877 */       localObject = this.pathToGeneralNames.iterator();
/* 1878 */       while (((Iterator)localObject).hasNext()) {
/* 1879 */         localStringBuffer.append("    " + ((Iterator)localObject).next() + "\n");
/*      */       }
/*      */     }
/* 1882 */     localStringBuffer.append("]");
/* 1883 */     return localStringBuffer.toString();
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private static String keyUsageToString(boolean[] paramArrayOfBoolean)
/*      */   {
/* 1892 */     String str = "KeyUsage [\n";
/*      */     try {
/* 1894 */       if (paramArrayOfBoolean[0] != 0) {
/* 1895 */         str = str + "  DigitalSignature\n";
/*      */       }
/* 1897 */       if (paramArrayOfBoolean[1] != 0) {
/* 1898 */         str = str + "  Non_repudiation\n";
/*      */       }
/* 1900 */       if (paramArrayOfBoolean[2] != 0) {
/* 1901 */         str = str + "  Key_Encipherment\n";
/*      */       }
/* 1903 */       if (paramArrayOfBoolean[3] != 0) {
/* 1904 */         str = str + "  Data_Encipherment\n";
/*      */       }
/* 1906 */       if (paramArrayOfBoolean[4] != 0) {
/* 1907 */         str = str + "  Key_Agreement\n";
/*      */       }
/* 1909 */       if (paramArrayOfBoolean[5] != 0) {
/* 1910 */         str = str + "  Key_CertSign\n";
/*      */       }
/* 1912 */       if (paramArrayOfBoolean[6] != 0) {
/* 1913 */         str = str + "  Crl_Sign\n";
/*      */       }
/* 1915 */       if (paramArrayOfBoolean[7] != 0) {
/* 1916 */         str = str + "  Encipher_Only\n";
/*      */       }
/* 1918 */       if (paramArrayOfBoolean[8] != 0) {
/* 1919 */         str = str + "  Decipher_Only\n";
/*      */       }
/*      */     }
/*      */     catch (ArrayIndexOutOfBoundsException localArrayIndexOutOfBoundsException) {}
/* 1923 */     str = str + "]\n";
/*      */     
/* 1925 */     return str;
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
/*      */   private static Extension getExtensionObject(X509Certificate paramX509Certificate, int paramInt)
/*      */     throws IOException
/*      */   {
/* 1949 */     if ((paramX509Certificate instanceof X509CertImpl)) {
/* 1950 */       localObject = (X509CertImpl)paramX509Certificate;
/* 1951 */       switch (paramInt) {
/*      */       case 0: 
/* 1953 */         return ((X509CertImpl)localObject).getPrivateKeyUsageExtension();
/*      */       case 1: 
/* 1955 */         return ((X509CertImpl)localObject).getSubjectAlternativeNameExtension();
/*      */       case 2: 
/* 1957 */         return ((X509CertImpl)localObject).getNameConstraintsExtension();
/*      */       case 3: 
/* 1959 */         return ((X509CertImpl)localObject).getCertificatePoliciesExtension();
/*      */       case 4: 
/* 1961 */         return ((X509CertImpl)localObject).getExtendedKeyUsageExtension();
/*      */       }
/* 1963 */       return null;
/*      */     }
/*      */     
/* 1966 */     Object localObject = paramX509Certificate.getExtensionValue(EXTENSION_OIDS[paramInt]);
/* 1967 */     if (localObject == null) {
/* 1968 */       return null;
/*      */     }
/* 1970 */     DerInputStream localDerInputStream = new DerInputStream((byte[])localObject);
/* 1971 */     byte[] arrayOfByte = localDerInputStream.getOctetString();
/* 1972 */     switch (paramInt) {
/*      */     case 0: 
/*      */       try {
/* 1975 */         return new PrivateKeyUsageExtension(FALSE, arrayOfByte);
/*      */       } catch (CertificateException localCertificateException) {
/* 1977 */         throw new IOException(localCertificateException.getMessage());
/*      */       }
/*      */     case 1: 
/* 1980 */       return new SubjectAlternativeNameExtension(FALSE, arrayOfByte);
/*      */     case 2: 
/* 1982 */       return new NameConstraintsExtension(FALSE, arrayOfByte);
/*      */     case 3: 
/* 1984 */       return new CertificatePoliciesExtension(FALSE, arrayOfByte);
/*      */     case 4: 
/* 1986 */       return new ExtendedKeyUsageExtension(FALSE, arrayOfByte);
/*      */     }
/* 1988 */     return null;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public boolean match(Certificate paramCertificate)
/*      */   {
/* 2000 */     if (!(paramCertificate instanceof X509Certificate)) {
/* 2001 */       return false;
/*      */     }
/* 2003 */     X509Certificate localX509Certificate = (X509Certificate)paramCertificate;
/*      */     
/* 2005 */     if (debug != null) {
/* 2006 */       debug.println("X509CertSelector.match(SN: " + localX509Certificate
/* 2007 */         .getSerialNumber().toString(16) + "\n  Issuer: " + localX509Certificate
/* 2008 */         .getIssuerDN() + "\n  Subject: " + localX509Certificate.getSubjectDN() + ")");
/*      */     }
/*      */     
/*      */ 
/*      */ 
/* 2013 */     if ((this.x509Cert != null) && 
/* 2014 */       (!this.x509Cert.equals(localX509Certificate))) {
/* 2015 */       if (debug != null) {
/* 2016 */         debug.println("X509CertSelector.match: certs don't match");
/*      */       }
/*      */       
/* 2019 */       return false;
/*      */     }
/*      */     
/*      */ 
/*      */ 
/* 2024 */     if ((this.serialNumber != null) && 
/* 2025 */       (!this.serialNumber.equals(localX509Certificate.getSerialNumber()))) {
/* 2026 */       if (debug != null) {
/* 2027 */         debug.println("X509CertSelector.match: serial numbers don't match");
/*      */       }
/*      */       
/* 2030 */       return false;
/*      */     }
/*      */     
/*      */ 
/*      */ 
/* 2035 */     if ((this.issuer != null) && 
/* 2036 */       (!this.issuer.equals(localX509Certificate.getIssuerX500Principal()))) {
/* 2037 */       if (debug != null) {
/* 2038 */         debug.println("X509CertSelector.match: issuer DNs don't match");
/*      */       }
/*      */       
/* 2041 */       return false;
/*      */     }
/*      */     
/*      */ 
/*      */ 
/* 2046 */     if ((this.subject != null) && 
/* 2047 */       (!this.subject.equals(localX509Certificate.getSubjectX500Principal()))) {
/* 2048 */       if (debug != null) {
/* 2049 */         debug.println("X509CertSelector.match: subject DNs don't match");
/*      */       }
/*      */       
/* 2052 */       return false;
/*      */     }
/*      */     
/*      */ 
/*      */ 
/* 2057 */     if (this.certificateValid != null) {
/*      */       try {
/* 2059 */         localX509Certificate.checkValidity(this.certificateValid);
/*      */       } catch (CertificateException localCertificateException) {
/* 2061 */         if (debug != null) {
/* 2062 */           debug.println("X509CertSelector.match: certificate not within validity period");
/*      */         }
/*      */         
/* 2065 */         return false;
/*      */       }
/*      */     }
/*      */     
/*      */ 
/* 2070 */     if (this.subjectPublicKeyBytes != null) {
/* 2071 */       byte[] arrayOfByte = localX509Certificate.getPublicKey().getEncoded();
/* 2072 */       if (!Arrays.equals(this.subjectPublicKeyBytes, arrayOfByte)) {
/* 2073 */         if (debug != null) {
/* 2074 */           debug.println("X509CertSelector.match: subject public keys don't match");
/*      */         }
/*      */         
/* 2077 */         return false;
/*      */       }
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
/* 2091 */     boolean bool = (matchBasicConstraints(localX509Certificate)) && (matchKeyUsage(localX509Certificate)) && (matchExtendedKeyUsage(localX509Certificate)) && (matchSubjectKeyID(localX509Certificate)) && (matchAuthorityKeyID(localX509Certificate)) && (matchPrivateKeyValid(localX509Certificate)) && (matchSubjectPublicKeyAlgID(localX509Certificate)) && (matchPolicy(localX509Certificate)) && (matchSubjectAlternativeNames(localX509Certificate)) && (matchPathToNames(localX509Certificate)) && (matchNameConstraints(localX509Certificate));
/*      */     
/* 2093 */     if ((bool) && (debug != null)) {
/* 2094 */       debug.println("X509CertSelector.match returning: true");
/*      */     }
/* 2096 */     return bool;
/*      */   }
/*      */   
/*      */   private boolean matchSubjectKeyID(X509Certificate paramX509Certificate)
/*      */   {
/* 2101 */     if (this.subjectKeyID == null) {
/* 2102 */       return true;
/*      */     }
/*      */     try {
/* 2105 */       byte[] arrayOfByte1 = paramX509Certificate.getExtensionValue("2.5.29.14");
/* 2106 */       if (arrayOfByte1 == null) {
/* 2107 */         if (debug != null) {
/* 2108 */           debug.println("X509CertSelector.match: no subject key ID extension");
/*      */         }
/*      */         
/* 2111 */         return false;
/*      */       }
/* 2113 */       DerInputStream localDerInputStream = new DerInputStream(arrayOfByte1);
/* 2114 */       byte[] arrayOfByte2 = localDerInputStream.getOctetString();
/* 2115 */       if ((arrayOfByte2 == null) || 
/* 2116 */         (!Arrays.equals(this.subjectKeyID, arrayOfByte2))) {
/* 2117 */         if (debug != null) {
/* 2118 */           debug.println("X509CertSelector.match: subject key IDs don't match");
/*      */         }
/*      */         
/* 2121 */         return false;
/*      */       }
/*      */     } catch (IOException localIOException) {
/* 2124 */       if (debug != null) {
/* 2125 */         debug.println("X509CertSelector.match: exception in subject key ID check");
/*      */       }
/*      */       
/* 2128 */       return false;
/*      */     }
/* 2130 */     return true;
/*      */   }
/*      */   
/*      */   private boolean matchAuthorityKeyID(X509Certificate paramX509Certificate)
/*      */   {
/* 2135 */     if (this.authorityKeyID == null) {
/* 2136 */       return true;
/*      */     }
/*      */     try {
/* 2139 */       byte[] arrayOfByte1 = paramX509Certificate.getExtensionValue("2.5.29.35");
/* 2140 */       if (arrayOfByte1 == null) {
/* 2141 */         if (debug != null) {
/* 2142 */           debug.println("X509CertSelector.match: no authority key ID extension");
/*      */         }
/*      */         
/* 2145 */         return false;
/*      */       }
/* 2147 */       DerInputStream localDerInputStream = new DerInputStream(arrayOfByte1);
/* 2148 */       byte[] arrayOfByte2 = localDerInputStream.getOctetString();
/* 2149 */       if ((arrayOfByte2 == null) || 
/* 2150 */         (!Arrays.equals(this.authorityKeyID, arrayOfByte2))) {
/* 2151 */         if (debug != null) {
/* 2152 */           debug.println("X509CertSelector.match: authority key IDs don't match");
/*      */         }
/*      */         
/* 2155 */         return false;
/*      */       }
/*      */     } catch (IOException localIOException) {
/* 2158 */       if (debug != null) {
/* 2159 */         debug.println("X509CertSelector.match: exception in authority key ID check");
/*      */       }
/*      */       
/* 2162 */       return false;
/*      */     }
/* 2164 */     return true;
/*      */   }
/*      */   
/*      */   private boolean matchPrivateKeyValid(X509Certificate paramX509Certificate)
/*      */   {
/* 2169 */     if (this.privateKeyValid == null) {
/* 2170 */       return true;
/*      */     }
/* 2172 */     PrivateKeyUsageExtension localPrivateKeyUsageExtension = null;
/*      */     try
/*      */     {
/* 2175 */       localPrivateKeyUsageExtension = (PrivateKeyUsageExtension)getExtensionObject(paramX509Certificate, 0);
/* 2176 */       if (localPrivateKeyUsageExtension != null) {
/* 2177 */         localPrivateKeyUsageExtension.valid(this.privateKeyValid);
/*      */       }
/*      */     } catch (CertificateExpiredException localCertificateExpiredException) {
/* 2180 */       if (debug != null) {
/* 2181 */         str = "n/a";
/*      */         try {
/* 2183 */           Date localDate1 = localPrivateKeyUsageExtension.get("not_after");
/* 2184 */           str = localDate1.toString();
/*      */         }
/*      */         catch (CertificateException localCertificateException1) {}
/*      */         
/* 2188 */         debug.println("X509CertSelector.match: private key usage not within validity date; ext.NOT_After: " + str + "; X509CertSelector: " + 
/*      */         
/*      */ 
/* 2191 */           toString());
/* 2192 */         localCertificateExpiredException.printStackTrace();
/*      */       }
/* 2194 */       return false;
/*      */     } catch (CertificateNotYetValidException localCertificateNotYetValidException) { String str;
/* 2196 */       if (debug != null) {
/* 2197 */         str = "n/a";
/*      */         try {
/* 2199 */           Date localDate2 = localPrivateKeyUsageExtension.get("not_before");
/* 2200 */           str = localDate2.toString();
/*      */         }
/*      */         catch (CertificateException localCertificateException2) {}
/*      */         
/* 2204 */         debug.println("X509CertSelector.match: private key usage not within validity date; ext.NOT_BEFORE: " + str + "; X509CertSelector: " + 
/*      */         
/*      */ 
/* 2207 */           toString());
/* 2208 */         localCertificateNotYetValidException.printStackTrace();
/*      */       }
/* 2210 */       return false;
/*      */     } catch (IOException localIOException) {
/* 2212 */       if (debug != null) {
/* 2213 */         debug.println("X509CertSelector.match: IOException in private key usage check; X509CertSelector: " + 
/*      */         
/* 2215 */           toString());
/* 2216 */         localIOException.printStackTrace();
/*      */       }
/* 2218 */       return false;
/*      */     }
/* 2220 */     return true;
/*      */   }
/*      */   
/*      */   private boolean matchSubjectPublicKeyAlgID(X509Certificate paramX509Certificate)
/*      */   {
/* 2225 */     if (this.subjectPublicKeyAlgID == null) {
/* 2226 */       return true;
/*      */     }
/*      */     try {
/* 2229 */       byte[] arrayOfByte = paramX509Certificate.getPublicKey().getEncoded();
/* 2230 */       DerValue localDerValue = new DerValue(arrayOfByte);
/* 2231 */       if (localDerValue.tag != 48) {
/* 2232 */         throw new IOException("invalid key format");
/*      */       }
/*      */       
/* 2235 */       AlgorithmId localAlgorithmId = AlgorithmId.parse(localDerValue.data.getDerValue());
/* 2236 */       if (debug != null) {
/* 2237 */         debug.println("X509CertSelector.match: subjectPublicKeyAlgID = " + this.subjectPublicKeyAlgID + ", xcert subjectPublicKeyAlgID = " + localAlgorithmId
/*      */         
/* 2239 */           .getOID());
/*      */       }
/* 2241 */       if (!this.subjectPublicKeyAlgID.equals(localAlgorithmId.getOID())) {
/* 2242 */         if (debug != null) {
/* 2243 */           debug.println("X509CertSelector.match: subject public key alg IDs don't match");
/*      */         }
/*      */         
/* 2246 */         return false;
/*      */       }
/*      */     } catch (IOException localIOException) {
/* 2249 */       if (debug != null) {
/* 2250 */         debug.println("X509CertSelector.match: IOException in subject public key algorithm OID check");
/*      */       }
/*      */       
/* 2253 */       return false;
/*      */     }
/* 2255 */     return true;
/*      */   }
/*      */   
/*      */   private boolean matchKeyUsage(X509Certificate paramX509Certificate)
/*      */   {
/* 2260 */     if (this.keyUsage == null) {
/* 2261 */       return true;
/*      */     }
/* 2263 */     boolean[] arrayOfBoolean = paramX509Certificate.getKeyUsage();
/* 2264 */     if (arrayOfBoolean != null) {
/* 2265 */       for (int i = 0; i < this.keyUsage.length; i++) {
/* 2266 */         if ((this.keyUsage[i] != 0) && ((i >= arrayOfBoolean.length) || (arrayOfBoolean[i] == 0)))
/*      */         {
/* 2268 */           if (debug != null) {
/* 2269 */             debug.println("X509CertSelector.match: key usage bits don't match");
/*      */           }
/*      */           
/* 2272 */           return false;
/*      */         }
/*      */       }
/*      */     }
/* 2276 */     return true;
/*      */   }
/*      */   
/*      */   private boolean matchExtendedKeyUsage(X509Certificate paramX509Certificate)
/*      */   {
/* 2281 */     if ((this.keyPurposeSet == null) || (this.keyPurposeSet.isEmpty())) {
/* 2282 */       return true;
/*      */     }
/*      */     try
/*      */     {
/* 2286 */       ExtendedKeyUsageExtension localExtendedKeyUsageExtension = (ExtendedKeyUsageExtension)getExtensionObject(paramX509Certificate, 4);
/*      */       
/* 2288 */       if (localExtendedKeyUsageExtension != null)
/*      */       {
/* 2290 */         Vector localVector = localExtendedKeyUsageExtension.get("usages");
/* 2291 */         if ((!localVector.contains(ANY_EXTENDED_KEY_USAGE)) && 
/* 2292 */           (!localVector.containsAll(this.keyPurposeOIDSet))) {
/* 2293 */           if (debug != null) {
/* 2294 */             debug.println("X509CertSelector.match: cert failed extendedKeyUsage criterion");
/*      */           }
/*      */           
/* 2297 */           return false;
/*      */         }
/*      */       }
/*      */     } catch (IOException localIOException) {
/* 2301 */       if (debug != null) {
/* 2302 */         debug.println("X509CertSelector.match: IOException in extended key usage check");
/*      */       }
/*      */       
/* 2305 */       return false;
/*      */     }
/* 2307 */     return true;
/*      */   }
/*      */   
/*      */   private boolean matchSubjectAlternativeNames(X509Certificate paramX509Certificate)
/*      */   {
/* 2312 */     if ((this.subjectAlternativeNames == null) || (this.subjectAlternativeNames.isEmpty())) {
/* 2313 */       return true;
/*      */     }
/*      */     try
/*      */     {
/* 2317 */       SubjectAlternativeNameExtension localSubjectAlternativeNameExtension = (SubjectAlternativeNameExtension)getExtensionObject(paramX509Certificate, 1);
/*      */       
/* 2319 */       if (localSubjectAlternativeNameExtension == null) {
/* 2320 */         if (debug != null) {
/* 2321 */           debug.println("X509CertSelector.match: no subject alternative name extension");
/*      */         }
/*      */         
/* 2324 */         return false;
/*      */       }
/*      */       
/* 2327 */       GeneralNames localGeneralNames = localSubjectAlternativeNameExtension.get("subject_name");
/*      */       
/* 2329 */       Iterator localIterator1 = this.subjectAlternativeGeneralNames.iterator();
/* 2330 */       while (localIterator1.hasNext()) {
/* 2331 */         GeneralNameInterface localGeneralNameInterface1 = (GeneralNameInterface)localIterator1.next();
/* 2332 */         boolean bool = false;
/* 2333 */         Iterator localIterator2 = localGeneralNames.iterator();
/* 2334 */         while ((localIterator2.hasNext()) && (!bool)) {
/* 2335 */           GeneralNameInterface localGeneralNameInterface2 = ((GeneralName)localIterator2.next()).getName();
/* 2336 */           bool = localGeneralNameInterface2.equals(localGeneralNameInterface1);
/*      */         }
/* 2338 */         if ((!bool) && ((this.matchAllSubjectAltNames) || (!localIterator1.hasNext()))) {
/* 2339 */           if (debug != null) {
/* 2340 */             debug.println("X509CertSelector.match: subject alternative name " + localGeneralNameInterface1 + " not found");
/*      */           }
/*      */           
/* 2343 */           return false; }
/* 2344 */         if ((bool) && (!this.matchAllSubjectAltNames)) {
/*      */           break;
/*      */         }
/*      */       }
/*      */     } catch (IOException localIOException) {
/* 2349 */       if (debug != null) {
/* 2350 */         debug.println("X509CertSelector.match: IOException in subject alternative name check");
/*      */       }
/* 2352 */       return false;
/*      */     }
/* 2354 */     return true;
/*      */   }
/*      */   
/*      */   private boolean matchNameConstraints(X509Certificate paramX509Certificate)
/*      */   {
/* 2359 */     if (this.nc == null) {
/* 2360 */       return true;
/*      */     }
/*      */     try {
/* 2363 */       if (!this.nc.verify(paramX509Certificate)) {
/* 2364 */         if (debug != null) {
/* 2365 */           debug.println("X509CertSelector.match: name constraints not satisfied");
/*      */         }
/*      */         
/* 2368 */         return false;
/*      */       }
/*      */     } catch (IOException localIOException) {
/* 2371 */       if (debug != null) {
/* 2372 */         debug.println("X509CertSelector.match: IOException in name constraints check");
/*      */       }
/*      */       
/* 2375 */       return false;
/*      */     }
/* 2377 */     return true;
/*      */   }
/*      */   
/*      */   private boolean matchPolicy(X509Certificate paramX509Certificate)
/*      */   {
/* 2382 */     if (this.policy == null) {
/* 2383 */       return true;
/*      */     }
/*      */     try
/*      */     {
/* 2387 */       CertificatePoliciesExtension localCertificatePoliciesExtension = (CertificatePoliciesExtension)getExtensionObject(paramX509Certificate, 3);
/* 2388 */       if (localCertificatePoliciesExtension == null) {
/* 2389 */         if (debug != null) {
/* 2390 */           debug.println("X509CertSelector.match: no certificate policy extension");
/*      */         }
/*      */         
/* 2393 */         return false;
/*      */       }
/* 2395 */       List localList = localCertificatePoliciesExtension.get("policies");
/*      */       
/*      */ 
/*      */ 
/*      */ 
/* 2400 */       ArrayList localArrayList = new ArrayList(localList.size());
/* 2401 */       for (Iterator localIterator = localList.iterator(); localIterator.hasNext();) { localObject = (PolicyInformation)localIterator.next();
/* 2402 */         localArrayList.add(((PolicyInformation)localObject).getPolicyIdentifier()); }
/*      */       Object localObject;
/* 2404 */       if (this.policy != null) {
/* 2405 */         int i = 0;
/*      */         
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 2411 */         if (this.policy.getCertPolicyIds().isEmpty()) {
/* 2412 */           if (localArrayList.isEmpty()) {
/* 2413 */             if (debug != null) {
/* 2414 */               debug.println("X509CertSelector.match: cert failed policyAny criterion");
/*      */             }
/*      */             
/* 2417 */             return false;
/*      */           }
/*      */         } else {
/* 2420 */           for (localObject = this.policy.getCertPolicyIds().iterator(); ((Iterator)localObject).hasNext();) { CertificatePolicyId localCertificatePolicyId = (CertificatePolicyId)((Iterator)localObject).next();
/* 2421 */             if (localArrayList.contains(localCertificatePolicyId)) {
/* 2422 */               i = 1;
/* 2423 */               break;
/*      */             }
/*      */           }
/* 2426 */           if (i == 0) {
/* 2427 */             if (debug != null) {
/* 2428 */               debug.println("X509CertSelector.match: cert failed policyAny criterion");
/*      */             }
/*      */             
/* 2431 */             return false;
/*      */           }
/*      */         }
/*      */       }
/*      */     } catch (IOException localIOException) {
/* 2436 */       if (debug != null) {
/* 2437 */         debug.println("X509CertSelector.match: IOException in certificate policy ID check");
/*      */       }
/*      */       
/* 2440 */       return false;
/*      */     }
/* 2442 */     return true;
/*      */   }
/*      */   
/*      */   private boolean matchPathToNames(X509Certificate paramX509Certificate)
/*      */   {
/* 2447 */     if (this.pathToGeneralNames == null) {
/* 2448 */       return true;
/*      */     }
/*      */     try
/*      */     {
/* 2452 */       NameConstraintsExtension localNameConstraintsExtension = (NameConstraintsExtension)getExtensionObject(paramX509Certificate, 2);
/* 2453 */       if (localNameConstraintsExtension == null) {
/* 2454 */         return true;
/*      */       }
/* 2456 */       if ((debug != null) && (Debug.isOn("certpath"))) {
/* 2457 */         debug.println("X509CertSelector.match pathToNames:\n");
/*      */         
/* 2459 */         localObject = this.pathToGeneralNames.iterator();
/* 2460 */         while (((Iterator)localObject).hasNext()) {
/* 2461 */           debug.println("    " + ((Iterator)localObject).next() + "\n");
/*      */         }
/*      */       }
/*      */       
/*      */ 
/* 2466 */       Object localObject = localNameConstraintsExtension.get("permitted_subtrees");
/*      */       
/* 2468 */       GeneralSubtrees localGeneralSubtrees = localNameConstraintsExtension.get("excluded_subtrees");
/* 2469 */       if ((localGeneralSubtrees != null) && 
/* 2470 */         (!matchExcluded(localGeneralSubtrees))) {
/* 2471 */         return false;
/*      */       }
/*      */       
/* 2474 */       if ((localObject != null) && 
/* 2475 */         (!matchPermitted((GeneralSubtrees)localObject))) {
/* 2476 */         return false;
/*      */       }
/*      */     }
/*      */     catch (IOException localIOException) {
/* 2480 */       if (debug != null) {
/* 2481 */         debug.println("X509CertSelector.match: IOException in name constraints check");
/*      */       }
/*      */       
/* 2484 */       return false;
/*      */     }
/* 2486 */     return true;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private boolean matchExcluded(GeneralSubtrees paramGeneralSubtrees)
/*      */   {
/* 2495 */     for (Iterator localIterator1 = paramGeneralSubtrees.iterator(); localIterator1.hasNext();) {
/* 2496 */       GeneralSubtree localGeneralSubtree = (GeneralSubtree)localIterator1.next();
/* 2497 */       GeneralNameInterface localGeneralNameInterface1 = localGeneralSubtree.getName().getName();
/* 2498 */       Iterator localIterator2 = this.pathToGeneralNames.iterator();
/* 2499 */       while (localIterator2.hasNext()) {
/* 2500 */         GeneralNameInterface localGeneralNameInterface2 = (GeneralNameInterface)localIterator2.next();
/* 2501 */         if (localGeneralNameInterface1.getType() == localGeneralNameInterface2.getType()) {
/* 2502 */           switch (localGeneralNameInterface2.constrains(localGeneralNameInterface1)) {
/*      */           case 0: 
/*      */           case 2: 
/* 2505 */             if (debug != null) {
/* 2506 */               debug.println("X509CertSelector.match: name constraints inhibit path to specified name");
/*      */               
/* 2508 */               debug.println("X509CertSelector.match: excluded name: " + localGeneralNameInterface2);
/*      */             }
/*      */             
/* 2511 */             return false;
/*      */           }
/*      */           
/*      */         }
/*      */       }
/*      */     }
/* 2517 */     return true;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private boolean matchPermitted(GeneralSubtrees paramGeneralSubtrees)
/*      */   {
/* 2527 */     Iterator localIterator1 = this.pathToGeneralNames.iterator();
/* 2528 */     while (localIterator1.hasNext()) {
/* 2529 */       GeneralNameInterface localGeneralNameInterface1 = (GeneralNameInterface)localIterator1.next();
/* 2530 */       Iterator localIterator2 = paramGeneralSubtrees.iterator();
/* 2531 */       int i = 0;
/* 2532 */       int j = 0;
/* 2533 */       String str = "";
/* 2534 */       while ((localIterator2.hasNext()) && (i == 0)) {
/* 2535 */         GeneralSubtree localGeneralSubtree = (GeneralSubtree)localIterator2.next();
/* 2536 */         GeneralNameInterface localGeneralNameInterface2 = localGeneralSubtree.getName().getName();
/* 2537 */         if (localGeneralNameInterface2.getType() == localGeneralNameInterface1.getType()) {
/* 2538 */           j = 1;
/* 2539 */           str = str + "  " + localGeneralNameInterface2;
/* 2540 */           switch (localGeneralNameInterface1.constrains(localGeneralNameInterface2)) {
/*      */           case 0: 
/*      */           case 2: 
/* 2543 */             i = 1;
/*      */           }
/*      */           
/*      */         }
/*      */       }
/*      */       
/* 2549 */       if ((i == 0) && (j != 0)) {
/* 2550 */         if (debug != null) {
/* 2551 */           debug.println("X509CertSelector.match: name constraints inhibit path to specified name; permitted names of type " + localGeneralNameInterface1
/*      */           
/* 2553 */             .getType() + ": " + str);
/*      */         }
/* 2555 */         return false;
/*      */       }
/*      */     }
/* 2558 */     return true;
/*      */   }
/*      */   
/*      */   private boolean matchBasicConstraints(X509Certificate paramX509Certificate)
/*      */   {
/* 2563 */     if (this.basicConstraints == -1) {
/* 2564 */       return true;
/*      */     }
/* 2566 */     int i = paramX509Certificate.getBasicConstraints();
/* 2567 */     if (this.basicConstraints == -2) {
/* 2568 */       if (i != -1) {
/* 2569 */         if (debug != null) {
/* 2570 */           debug.println("X509CertSelector.match: not an EE cert");
/*      */         }
/* 2572 */         return false;
/*      */       }
/*      */     }
/* 2575 */     else if (i < this.basicConstraints) {
/* 2576 */       if (debug != null) {
/* 2577 */         debug.println("X509CertSelector.match: maxPathLen too small (" + i + " < " + this.basicConstraints + ")");
/*      */       }
/*      */       
/* 2580 */       return false;
/*      */     }
/*      */     
/* 2583 */     return true;
/*      */   }
/*      */   
/*      */   private static <T> Set<T> cloneSet(Set<T> paramSet)
/*      */   {
/* 2588 */     if ((paramSet instanceof HashSet)) {
/* 2589 */       Object localObject = ((HashSet)paramSet).clone();
/* 2590 */       return (Set)localObject;
/*      */     }
/* 2592 */     return new HashSet(paramSet);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public Object clone()
/*      */   {
/*      */     try
/*      */     {
/* 2603 */       X509CertSelector localX509CertSelector = (X509CertSelector)super.clone();
/*      */       
/* 2605 */       if (this.subjectAlternativeNames != null)
/*      */       {
/* 2607 */         localX509CertSelector.subjectAlternativeNames = cloneSet(this.subjectAlternativeNames);
/*      */         
/* 2609 */         localX509CertSelector.subjectAlternativeGeneralNames = cloneSet(this.subjectAlternativeGeneralNames);
/*      */       }
/* 2611 */       if (this.pathToGeneralNames != null) {
/* 2612 */         localX509CertSelector.pathToNames = cloneSet(this.pathToNames);
/* 2613 */         localX509CertSelector.pathToGeneralNames = cloneSet(this.pathToGeneralNames);
/*      */       }
/* 2615 */       return localX509CertSelector;
/*      */     }
/*      */     catch (CloneNotSupportedException localCloneNotSupportedException) {
/* 2618 */       throw new InternalError(localCloneNotSupportedException.toString(), localCloneNotSupportedException);
/*      */     }
/*      */   }
/*      */ }


/* Location:              /Users/guanxiaoda/Documents/workspace/rt.jar!/java/security/cert/X509CertSelector.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */