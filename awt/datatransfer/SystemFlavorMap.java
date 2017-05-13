/*      */ package java.awt.datatransfer;
/*      */ 
/*      */ import java.awt.Toolkit;
/*      */ import java.io.BufferedReader;
/*      */ import java.io.File;
/*      */ import java.io.IOException;
/*      */ import java.io.InputStreamReader;
/*      */ import java.io.PrintStream;
/*      */ import java.lang.ref.SoftReference;
/*      */ import java.net.MalformedURLException;
/*      */ import java.net.URI;
/*      */ import java.net.URL;
/*      */ import java.security.AccessController;
/*      */ import java.security.PrivilegedAction;
/*      */ import java.util.ArrayList;
/*      */ import java.util.Collection;
/*      */ import java.util.HashMap;
/*      */ import java.util.HashSet;
/*      */ import java.util.Iterator;
/*      */ import java.util.LinkedHashSet;
/*      */ import java.util.List;
/*      */ import java.util.Map;
/*      */ import java.util.Set;
/*      */ import sun.awt.AppContext;
/*      */ import sun.awt.datatransfer.DataTransferer;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public final class SystemFlavorMap
/*      */   implements FlavorMap, FlavorTable
/*      */ {
/*   67 */   private static String JavaMIME = "JAVA_DATAFLAVOR:";
/*      */   
/*   69 */   private static final Object FLAVOR_MAP_KEY = new Object();
/*      */   
/*      */ 
/*      */ 
/*      */   private static final String keyValueSeparators = "=: \t\r\n\f";
/*      */   
/*      */ 
/*      */   private static final String strictKeyValueSeparators = "=:";
/*      */   
/*      */ 
/*      */   private static final String whiteSpaceChars = " \t\r\n\f";
/*      */   
/*      */ 
/*   82 */   private static final String[] UNICODE_TEXT_CLASSES = { "java.io.Reader", "java.lang.String", "java.nio.CharBuffer", "\"[C\"" };
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*   90 */   private static final String[] ENCODED_TEXT_CLASSES = { "java.io.InputStream", "java.nio.ByteBuffer", "\"[B\"" };
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private static final String TEXT_PLAIN_BASE_TYPE = "text/plain";
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private static final String HTML_TEXT_BASE_TYPE = "text/html";
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private static final boolean SYNTHESIZE_IF_NOT_FOUND = true;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  117 */   private final Map<String, List<DataFlavor>> nativeToFlavor = new HashMap();
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private Map<String, List<DataFlavor>> getNativeToFlavor()
/*      */   {
/*  127 */     if (!this.isMapInitialized) {
/*  128 */       initSystemFlavorMap();
/*      */     }
/*  130 */     return this.nativeToFlavor;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  138 */   private final Map<DataFlavor, List<String>> flavorToNative = new HashMap();
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private synchronized Map<DataFlavor, List<String>> getFlavorToNative()
/*      */   {
/*  148 */     if (!this.isMapInitialized) {
/*  149 */       initSystemFlavorMap();
/*      */     }
/*  151 */     return this.flavorToNative;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*  157 */   private boolean isMapInitialized = false;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  163 */   private Map<DataFlavor, SoftReference<List<String>>> getNativesForFlavorCache = new HashMap();
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  169 */   private Map<String, SoftReference<List<DataFlavor>>> getFlavorsForNativeCache = new HashMap();
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  177 */   private Set disabledMappingGenerationKeys = new HashSet();
/*      */   
/*      */ 
/*      */ 
/*      */   public static FlavorMap getDefaultFlavorMap()
/*      */   {
/*  183 */     AppContext localAppContext = AppContext.getAppContext();
/*  184 */     Object localObject = (FlavorMap)localAppContext.get(FLAVOR_MAP_KEY);
/*  185 */     if (localObject == null) {
/*  186 */       localObject = new SystemFlavorMap();
/*  187 */       localAppContext.put(FLAVOR_MAP_KEY, localObject);
/*      */     }
/*  189 */     return (FlavorMap)localObject;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private void initSystemFlavorMap()
/*      */   {
/*  201 */     if (this.isMapInitialized) {
/*  202 */       return;
/*      */     }
/*      */     
/*  205 */     this.isMapInitialized = true;
/*      */     
/*  207 */     BufferedReader localBufferedReader1 = (BufferedReader)AccessController.doPrivileged(new PrivilegedAction()
/*      */     {
/*      */ 
/*      */       public BufferedReader run()
/*      */       {
/*  211 */         String str = System.getProperty("java.home") + File.separator + "lib" + File.separator + "flavormap.properties";
/*      */         
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         try
/*      */         {
/*  219 */           return new BufferedReader(new InputStreamReader(new File(str).toURI().toURL().openStream(), "ISO-8859-1"));
/*      */         } catch (MalformedURLException localMalformedURLException) {
/*  221 */           System.err.println("MalformedURLException:" + localMalformedURLException + " while loading default flavormap.properties file:" + str);
/*      */         } catch (IOException localIOException) {
/*  223 */           System.err.println("IOException:" + localIOException + " while loading default flavormap.properties file:" + str);
/*      */         }
/*  225 */         return null;
/*      */       }
/*      */       
/*      */ 
/*  229 */     });
/*  230 */     String str = (String)AccessController.doPrivileged(new PrivilegedAction()
/*      */     {
/*      */       public String run()
/*      */       {
/*  233 */         return Toolkit.getProperty("AWT.DnD.flavorMapFileURL", null);
/*      */       }
/*      */     });
/*      */     
/*  237 */     if (localBufferedReader1 != null) {
/*      */       try {
/*  239 */         parseAndStoreReader(localBufferedReader1);
/*      */       } catch (IOException localIOException1) {
/*  241 */         System.err.println("IOException:" + localIOException1 + " while parsing default flavormap.properties file");
/*      */       }
/*      */     }
/*      */     
/*  245 */     BufferedReader localBufferedReader2 = null;
/*  246 */     if (str != null) {
/*      */       try {
/*  248 */         localBufferedReader2 = new BufferedReader(new InputStreamReader(new URL(str).openStream(), "ISO-8859-1"));
/*      */       } catch (MalformedURLException localMalformedURLException) {
/*  250 */         System.err.println("MalformedURLException:" + localMalformedURLException + " while reading AWT.DnD.flavorMapFileURL:" + str);
/*      */       } catch (IOException localIOException2) {
/*  252 */         System.err.println("IOException:" + localIOException2 + " while reading AWT.DnD.flavorMapFileURL:" + str);
/*      */       }
/*      */       catch (SecurityException localSecurityException) {}
/*      */     }
/*      */     
/*      */ 
/*  258 */     if (localBufferedReader2 != null) {
/*      */       try {
/*  260 */         parseAndStoreReader(localBufferedReader2);
/*      */       } catch (IOException localIOException3) {
/*  262 */         System.err.println("IOException:" + localIOException3 + " while parsing AWT.DnD.flavorMapFileURL");
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */   private void parseAndStoreReader(BufferedReader paramBufferedReader)
/*      */     throws IOException
/*      */   {
/*      */     String str4;
/*      */     for (;;)
/*      */     {
/*  273 */       String str1 = paramBufferedReader.readLine();
/*  274 */       if (str1 == null) {
/*  275 */         return;
/*      */       }
/*      */       
/*  278 */       if (str1.length() > 0)
/*      */       {
/*  280 */         int i = str1.charAt(0);
/*  281 */         if ((i != 35) && (i != 33)) { int m;
/*  282 */           while (continueLine(str1)) {
/*  283 */             String str2 = paramBufferedReader.readLine();
/*  284 */             if (str2 == null) {
/*  285 */               str2 = "";
/*      */             }
/*      */             
/*  288 */             String str3 = str1.substring(0, str1.length() - 1);
/*      */             
/*  290 */             for (m = 0; 
/*  291 */                 m < str2.length(); m++)
/*      */             {
/*  293 */               if (" \t\r\n\f".indexOf(str2.charAt(m)) == -1) {
/*      */                 break;
/*      */               }
/*      */             }
/*      */             
/*  298 */             str2 = str2.substring(m, str2
/*  299 */               .length());
/*  300 */             str1 = str3 + str2;
/*      */           }
/*      */           
/*      */ 
/*  304 */           int j = str1.length();
/*  305 */           for (int k = 0; 
/*  306 */               k < j; k++)
/*      */           {
/*  308 */             if (" \t\r\n\f".indexOf(str1.charAt(k)) == -1) {
/*      */               break;
/*      */             }
/*      */           }
/*      */           
/*      */ 
/*  314 */           if (k != j)
/*      */           {
/*      */ 
/*      */ 
/*      */ 
/*  319 */             for (m = k; 
/*  320 */                 m < j; m++) {
/*  321 */               n = str1.charAt(m);
/*  322 */               if (n == 92) {
/*  323 */                 m++;
/*      */               } else {
/*  325 */                 if ("=: \t\r\n\f".indexOf(n) != -1) {
/*      */                   break;
/*      */                 }
/*      */               }
/*      */             }
/*      */             
/*  331 */             for (int n = m; 
/*  332 */                 n < j; n++)
/*      */             {
/*  334 */               if (" \t\r\n\f".indexOf(str1.charAt(n)) == -1) {
/*      */                 break;
/*      */               }
/*      */             }
/*      */             
/*      */ 
/*  340 */             if (n < j)
/*      */             {
/*  342 */               if ("=:".indexOf(str1.charAt(n)) != -1) {
/*  343 */                 n++;
/*      */               }
/*      */             }
/*      */             
/*      */ 
/*  348 */             while (n < j)
/*      */             {
/*  350 */               if (" \t\r\n\f".indexOf(str1.charAt(n)) == -1) {
/*      */                 break;
/*      */               }
/*  353 */               n++;
/*      */             }
/*      */             
/*  356 */             str4 = str1.substring(k, m);
/*      */             
/*  358 */             String str5 = m < j ? str1.substring(n, j) : "";
/*      */             
/*      */ 
/*      */ 
/*  362 */             str4 = loadConvert(str4);
/*  363 */             str5 = loadConvert(str5);
/*      */             try
/*      */             {
/*  366 */               MimeType localMimeType = new MimeType(str5);
/*  367 */               if ("text".equals(localMimeType.getPrimaryType())) {
/*  368 */                 String str6 = localMimeType.getParameter("charset");
/*      */                 
/*  370 */                 if (DataTransferer.doesSubtypeSupportCharset(localMimeType.getSubType(), str6))
/*      */                 {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  377 */                   DataTransferer localDataTransferer = DataTransferer.getInstance();
/*  378 */                   if (localDataTransferer != null)
/*      */                   {
/*  380 */                     localDataTransferer.registerTextFlavorProperties(str4, str6, localMimeType
/*  381 */                       .getParameter("eoln"), localMimeType
/*  382 */                       .getParameter("terminators"));
/*      */                   }
/*      */                 }
/*      */                 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  390 */                 localMimeType.removeParameter("charset");
/*  391 */                 localMimeType.removeParameter("class");
/*  392 */                 localMimeType.removeParameter("eoln");
/*  393 */                 localMimeType.removeParameter("terminators");
/*  394 */                 str5 = localMimeType.toString();
/*      */               }
/*      */             } catch (MimeTypeParseException localMimeTypeParseException) {
/*  397 */               localMimeTypeParseException.printStackTrace(); }
/*  398 */             continue;
/*      */             
/*      */             DataFlavor localDataFlavor1;
/*      */             try
/*      */             {
/*  403 */               localDataFlavor1 = new DataFlavor(str5);
/*      */             } catch (Exception localException1) {
/*      */               try {
/*  406 */                 localDataFlavor1 = new DataFlavor(str5, (String)null);
/*      */               } catch (Exception localException2) {
/*  408 */                 localException2.printStackTrace(); } }
/*  409 */             continue;
/*      */             
/*      */ 
/*      */ 
/*  413 */             LinkedHashSet localLinkedHashSet = new LinkedHashSet();
/*      */             
/*  415 */             localLinkedHashSet.add(localDataFlavor1);
/*      */             
/*  417 */             if ("text".equals(localDataFlavor1.getPrimaryType())) {
/*  418 */               localLinkedHashSet.addAll(convertMimeTypeToDataFlavors(str5));
/*      */             }
/*      */             
/*  421 */             for (DataFlavor localDataFlavor2 : localLinkedHashSet) {
/*  422 */               store(localDataFlavor2, str4, getFlavorToNative());
/*  423 */               store(str4, localDataFlavor2, getNativeToFlavor());
/*      */             }
/*      */           }
/*      */         }
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */   private boolean continueLine(String paramString)
/*      */   {
/*  434 */     int i = 0;
/*  435 */     int j = paramString.length() - 1;
/*  436 */     while ((j >= 0) && (paramString.charAt(j--) == '\\')) {
/*  437 */       i++;
/*      */     }
/*  439 */     return i % 2 == 1;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   private String loadConvert(String paramString)
/*      */   {
/*  447 */     int j = paramString.length();
/*  448 */     StringBuilder localStringBuilder = new StringBuilder(j);
/*      */     
/*  450 */     for (int k = 0; k < j;) {
/*  451 */       int i = paramString.charAt(k++);
/*  452 */       if (i == 92) {
/*  453 */         i = paramString.charAt(k++);
/*  454 */         if (i == 117)
/*      */         {
/*  456 */           int m = 0;
/*  457 */           for (int n = 0; n < 4; n++) {
/*  458 */             i = paramString.charAt(k++);
/*  459 */             switch (i) {
/*      */             case 48: case 49: case 50: case 51: case 52: 
/*      */             case 53: case 54: case 55: case 56: case 57: 
/*  462 */               m = (m << 4) + i - 48;
/*  463 */               break;
/*      */             case 97: case 98: 
/*      */             case 99: case 100: 
/*      */             case 101: case 102: 
/*  467 */               m = (m << 4) + 10 + i - 97;
/*  468 */               break;
/*      */             case 65: case 66: 
/*      */             case 67: case 68: 
/*      */             case 69: case 70: 
/*  472 */               m = (m << 4) + 10 + i - 65;
/*  473 */               break;
/*      */             case 58: case 59: case 60: case 61: case 62: case 63: case 64: case 71: case 72: case 73: case 74: case 75: case 76: case 77: case 78: case 79: case 80: 
/*      */             case 81: case 82: case 83: case 84: case 85: case 86: case 87: case 88: case 89: case 90: case 91: case 92: case 93: case 94: case 95: case 96: default: 
/*  476 */               throw new IllegalArgumentException("Malformed \\uxxxx encoding.");
/*      */             }
/*      */             
/*      */           }
/*      */           
/*  481 */           localStringBuilder.append((char)m);
/*      */         } else {
/*  483 */           if (i == 116) {
/*  484 */             i = 9;
/*  485 */           } else if (i == 114) {
/*  486 */             i = 13;
/*  487 */           } else if (i == 110) {
/*  488 */             i = 10;
/*  489 */           } else if (i == 102) {
/*  490 */             i = 12;
/*      */           }
/*  492 */           localStringBuilder.append(i);
/*      */         }
/*      */       } else {
/*  495 */         localStringBuilder.append(i);
/*      */       }
/*      */     }
/*  498 */     return localStringBuilder.toString();
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private <H, L> void store(H paramH, L paramL, Map<H, List<L>> paramMap)
/*      */   {
/*  508 */     Object localObject = (List)paramMap.get(paramH);
/*  509 */     if (localObject == null) {
/*  510 */       localObject = new ArrayList(1);
/*  511 */       paramMap.put(paramH, localObject);
/*      */     }
/*  513 */     if (!((List)localObject).contains(paramL)) {
/*  514 */       ((List)localObject).add(paramL);
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private List<DataFlavor> nativeToFlavorLookup(String paramString)
/*      */   {
/*  525 */     Object localObject1 = (List)getNativeToFlavor().get(paramString);
/*      */     Object localObject2;
/*  527 */     Object localObject3; if ((paramString != null) && (!this.disabledMappingGenerationKeys.contains(paramString))) {
/*  528 */       localObject2 = DataTransferer.getInstance();
/*  529 */       if (localObject2 != null)
/*      */       {
/*  531 */         localObject3 = ((DataTransferer)localObject2).getPlatformMappingsForNative(paramString);
/*  532 */         if (!((List)localObject3).isEmpty()) {
/*  533 */           if (localObject1 != null) {
/*  534 */             ((List)localObject3).removeAll(new HashSet((Collection)localObject1));
/*      */             
/*      */ 
/*      */ 
/*      */ 
/*  539 */             ((List)localObject3).addAll((Collection)localObject1);
/*      */           }
/*  541 */           localObject1 = localObject3;
/*      */         }
/*      */       }
/*      */     }
/*      */     
/*  546 */     if ((localObject1 == null) && (isJavaMIMEType(paramString))) {
/*  547 */       localObject2 = decodeJavaMIMEType(paramString);
/*  548 */       localObject3 = null;
/*      */       try
/*      */       {
/*  551 */         localObject3 = new DataFlavor((String)localObject2);
/*      */       } catch (Exception localException) {
/*  553 */         System.err.println("Exception \"" + localException.getClass().getName() + ": " + localException
/*  554 */           .getMessage() + "\"while constructing DataFlavor for: " + (String)localObject2);
/*      */       }
/*      */       
/*      */ 
/*      */ 
/*  559 */       if (localObject3 != null) {
/*  560 */         localObject1 = new ArrayList(1);
/*  561 */         getNativeToFlavor().put(paramString, localObject1);
/*  562 */         ((List)localObject1).add(localObject3);
/*  563 */         this.getFlavorsForNativeCache.remove(paramString);
/*  564 */         this.getFlavorsForNativeCache.remove(null);
/*      */         
/*  566 */         Object localObject4 = (List)getFlavorToNative().get(localObject3);
/*  567 */         if (localObject4 == null) {
/*  568 */           localObject4 = new ArrayList(1);
/*  569 */           getFlavorToNative().put(localObject3, localObject4);
/*      */         }
/*  571 */         ((List)localObject4).add(paramString);
/*  572 */         this.getNativesForFlavorCache.remove(localObject3);
/*  573 */         this.getNativesForFlavorCache.remove(null);
/*      */       }
/*      */     }
/*      */     
/*  577 */     return (List<DataFlavor>)(localObject1 != null ? localObject1 : new ArrayList(0));
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
/*      */   private List<String> flavorToNativeLookup(DataFlavor paramDataFlavor, boolean paramBoolean)
/*      */   {
/*  590 */     Object localObject1 = (List)getFlavorToNative().get(paramDataFlavor);
/*      */     Object localObject2;
/*  592 */     Object localObject3; if ((paramDataFlavor != null) && (!this.disabledMappingGenerationKeys.contains(paramDataFlavor))) {
/*  593 */       localObject2 = DataTransferer.getInstance();
/*  594 */       if (localObject2 != null)
/*      */       {
/*  596 */         localObject3 = ((DataTransferer)localObject2).getPlatformMappingsForFlavor(paramDataFlavor);
/*  597 */         if (!((List)localObject3).isEmpty()) {
/*  598 */           if (localObject1 != null) {
/*  599 */             ((List)localObject3).removeAll(new HashSet((Collection)localObject1));
/*      */             
/*      */ 
/*      */ 
/*      */ 
/*  604 */             ((List)localObject3).addAll((Collection)localObject1);
/*      */           }
/*  606 */           localObject1 = localObject3;
/*      */         }
/*      */       }
/*      */     }
/*      */     
/*  611 */     if (localObject1 == null) {
/*  612 */       if (paramBoolean) {
/*  613 */         localObject2 = encodeDataFlavor(paramDataFlavor);
/*  614 */         localObject1 = new ArrayList(1);
/*  615 */         getFlavorToNative().put(paramDataFlavor, localObject1);
/*  616 */         ((List)localObject1).add(localObject2);
/*  617 */         this.getNativesForFlavorCache.remove(paramDataFlavor);
/*  618 */         this.getNativesForFlavorCache.remove(null);
/*      */         
/*  620 */         localObject3 = (List)getNativeToFlavor().get(localObject2);
/*  621 */         if (localObject3 == null) {
/*  622 */           localObject3 = new ArrayList(1);
/*  623 */           getNativeToFlavor().put(localObject2, localObject3);
/*      */         }
/*  625 */         ((List)localObject3).add(paramDataFlavor);
/*  626 */         this.getFlavorsForNativeCache.remove(localObject2);
/*  627 */         this.getFlavorsForNativeCache.remove(null);
/*      */       } else {
/*  629 */         localObject1 = new ArrayList(0);
/*      */       }
/*      */     }
/*      */     
/*  633 */     return (List<String>)localObject1;
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
/*      */   public synchronized List<String> getNativesForFlavor(DataFlavor paramDataFlavor)
/*      */   {
/*  662 */     Object localObject1 = null;
/*      */     
/*      */ 
/*  665 */     SoftReference localSoftReference = (SoftReference)this.getNativesForFlavorCache.get(paramDataFlavor);
/*  666 */     if (localSoftReference != null) {
/*  667 */       localObject1 = (List)localSoftReference.get();
/*  668 */       if (localObject1 != null)
/*      */       {
/*      */ 
/*  671 */         return new ArrayList((Collection)localObject1);
/*      */       }
/*      */     }
/*      */     
/*  675 */     if (paramDataFlavor == null) {
/*  676 */       localObject1 = new ArrayList(getNativeToFlavor().keySet());
/*  677 */     } else if (this.disabledMappingGenerationKeys.contains(paramDataFlavor))
/*      */     {
/*      */ 
/*  680 */       localObject1 = flavorToNativeLookup(paramDataFlavor, false); } else { Object localObject2;
/*  681 */       if (DataTransferer.isFlavorCharsetTextType(paramDataFlavor))
/*      */       {
/*      */ 
/*      */ 
/*  685 */         if ("text".equals(paramDataFlavor.getPrimaryType())) {
/*  686 */           localObject1 = getAllNativesForType(paramDataFlavor.mimeType.getBaseType());
/*  687 */           if (localObject1 != null)
/*      */           {
/*  689 */             localObject1 = new ArrayList((Collection)localObject1);
/*      */           }
/*      */         }
/*      */         
/*      */ 
/*  694 */         localObject2 = getAllNativesForType("text/plain");
/*      */         
/*  696 */         if ((localObject2 != null) && (!((List)localObject2).isEmpty()))
/*      */         {
/*      */ 
/*  699 */           localObject2 = new ArrayList((Collection)localObject2);
/*  700 */           if ((localObject1 != null) && (!((List)localObject1).isEmpty()))
/*      */           {
/*  702 */             ((List)localObject2).removeAll(new HashSet((Collection)localObject1));
/*  703 */             ((List)localObject1).addAll((Collection)localObject2);
/*      */           } else {
/*  705 */             localObject1 = localObject2;
/*      */           }
/*      */         }
/*      */         
/*  709 */         if ((localObject1 == null) || (((List)localObject1).isEmpty())) {
/*  710 */           localObject1 = flavorToNativeLookup(paramDataFlavor, true);
/*      */ 
/*      */         }
/*      */         else
/*      */         {
/*      */ 
/*  716 */           Object localObject3 = flavorToNativeLookup(paramDataFlavor, false);
/*      */           
/*      */ 
/*      */ 
/*  720 */           if (!((List)localObject3).isEmpty())
/*      */           {
/*      */ 
/*  723 */             localObject3 = new ArrayList((Collection)localObject3);
/*      */             
/*  725 */             ((List)localObject3).removeAll(new HashSet((Collection)localObject1));
/*  726 */             ((List)localObject1).addAll((Collection)localObject3);
/*      */           }
/*      */         }
/*  729 */       } else if (DataTransferer.isFlavorNoncharsetTextType(paramDataFlavor)) {
/*  730 */         localObject1 = getAllNativesForType(paramDataFlavor.mimeType.getBaseType());
/*      */         
/*  732 */         if ((localObject1 == null) || (((List)localObject1).isEmpty())) {
/*  733 */           localObject1 = flavorToNativeLookup(paramDataFlavor, true);
/*      */ 
/*      */         }
/*      */         else
/*      */         {
/*      */ 
/*  739 */           localObject2 = flavorToNativeLookup(paramDataFlavor, false);
/*      */           
/*      */ 
/*      */ 
/*  743 */           if (!((List)localObject2).isEmpty())
/*      */           {
/*      */ 
/*  746 */             localObject1 = new ArrayList((Collection)localObject1);
/*  747 */             localObject2 = new ArrayList((Collection)localObject2);
/*      */             
/*  749 */             ((List)localObject2).removeAll(new HashSet((Collection)localObject1));
/*  750 */             ((List)localObject1).addAll((Collection)localObject2);
/*      */           }
/*      */         }
/*      */       } else {
/*  754 */         localObject1 = flavorToNativeLookup(paramDataFlavor, true);
/*      */       }
/*      */     }
/*  757 */     this.getNativesForFlavorCache.put(paramDataFlavor, new SoftReference(localObject1));
/*      */     
/*  759 */     return new ArrayList((Collection)localObject1);
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
/*      */   public synchronized List<DataFlavor> getFlavorsForNative(String paramString)
/*      */   {
/*  796 */     SoftReference localSoftReference = (SoftReference)this.getFlavorsForNativeCache.get(paramString);
/*  797 */     if (localSoftReference != null) {
/*  798 */       localObject1 = (List)localSoftReference.get();
/*  799 */       if (localObject1 != null) {
/*  800 */         return new ArrayList((Collection)localObject1);
/*      */       }
/*      */     }
/*      */     
/*  804 */     Object localObject1 = new LinkedHashSet();
/*      */     Object localObject3;
/*      */     Object localObject4;
/*  807 */     Object localObject5; if (paramString == null) {
/*  808 */       localObject2 = getNativesForFlavor(null);
/*      */       
/*  810 */       for (localObject3 = ((List)localObject2).iterator(); ((Iterator)localObject3).hasNext();) { localObject4 = (String)((Iterator)localObject3).next();
/*      */         
/*  812 */         localObject5 = getFlavorsForNative((String)localObject4);
/*      */         
/*  814 */         for (DataFlavor localDataFlavor : (List)localObject5)
/*      */         {
/*  816 */           ((LinkedHashSet)localObject1).add(localDataFlavor);
/*      */         }
/*      */       }
/*      */     }
/*      */     else {
/*  821 */       localObject2 = nativeToFlavorLookup(paramString);
/*      */       
/*  823 */       if (this.disabledMappingGenerationKeys.contains(paramString)) {
/*  824 */         return (List<DataFlavor>)localObject2;
/*      */       }
/*      */       
/*      */ 
/*  828 */       localObject3 = nativeToFlavorLookup(paramString);
/*      */       
/*  830 */       for (localObject4 = ((List)localObject3).iterator(); ((Iterator)localObject4).hasNext();) { localObject5 = (DataFlavor)((Iterator)localObject4).next();
/*  831 */         ((LinkedHashSet)localObject1).add(localObject5);
/*  832 */         if ("text".equals(((DataFlavor)localObject5).getPrimaryType())) {
/*      */           try {
/*  834 */             ((LinkedHashSet)localObject1).addAll(
/*  835 */               convertMimeTypeToDataFlavors(new MimeType(((DataFlavor)localObject5)
/*  836 */               .getMimeType())
/*  837 */               .getBaseType()));
/*      */           } catch (MimeTypeParseException localMimeTypeParseException) {
/*  839 */             localMimeTypeParseException.printStackTrace();
/*      */           }
/*      */         }
/*      */       }
/*      */     }
/*      */     
/*      */ 
/*  846 */     Object localObject2 = new ArrayList((Collection)localObject1);
/*  847 */     this.getFlavorsForNativeCache.put(paramString, new SoftReference(localObject2));
/*  848 */     return new ArrayList((Collection)localObject2);
/*      */   }
/*      */   
/*      */ 
/*      */   private static Set<DataFlavor> convertMimeTypeToDataFlavors(String paramString)
/*      */   {
/*  854 */     LinkedHashSet localLinkedHashSet = new LinkedHashSet();
/*      */     
/*  856 */     String str1 = null;
/*      */     try
/*      */     {
/*  859 */       MimeType localMimeType = new MimeType(paramString);
/*  860 */       str1 = localMimeType.getSubType();
/*      */     }
/*      */     catch (MimeTypeParseException localMimeTypeParseException)
/*      */     {
/*  864 */       if (!$assertionsDisabled) { throw new AssertionError();
/*      */       }
/*      */     }
/*  867 */     if (DataTransferer.doesSubtypeSupportCharset(str1, null)) {
/*  868 */       if ("text/plain".equals(paramString))
/*      */       {
/*  870 */         localLinkedHashSet.add(DataFlavor.stringFlavor); }
/*      */       Object localObject2;
/*      */       Object localObject3;
/*  873 */       Object localObject4; Object localObject5; for (String str3 : UNICODE_TEXT_CLASSES) {
/*  874 */         String str5 = paramString + ";charset=Unicode;class=" + str3;
/*      */         
/*      */ 
/*      */ 
/*  878 */         localObject2 = handleHtmlMimeTypes(paramString, str5);
/*  879 */         for (localObject3 = ((LinkedHashSet)localObject2).iterator(); ((Iterator)localObject3).hasNext();) { localObject4 = (String)((Iterator)localObject3).next();
/*  880 */           localObject5 = null;
/*      */           try {
/*  882 */             localObject5 = new DataFlavor((String)localObject4);
/*      */           }
/*      */           catch (ClassNotFoundException localClassNotFoundException2) {}
/*  885 */           localLinkedHashSet.add(localObject5);
/*      */         }
/*      */       }
/*      */       
/*  889 */       for (??? = DataTransferer.standardEncodings().iterator(); ((Iterator)???).hasNext();) { String str2 = (String)((Iterator)???).next();
/*      */         
/*  891 */         for (localObject2 : ENCODED_TEXT_CLASSES) {
/*  892 */           localObject3 = paramString + ";charset=" + str2 + ";class=" + (String)localObject2;
/*      */           
/*      */ 
/*      */ 
/*      */ 
/*  897 */           localObject4 = handleHtmlMimeTypes(paramString, (String)localObject3);
/*      */           
/*  899 */           for (localObject5 = ((LinkedHashSet)localObject4).iterator(); ((Iterator)localObject5).hasNext();) { String str6 = (String)((Iterator)localObject5).next();
/*      */             
/*  901 */             DataFlavor localDataFlavor2 = null;
/*      */             try
/*      */             {
/*  904 */               localDataFlavor2 = new DataFlavor(str6);
/*      */               
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  910 */               if (localDataFlavor2.equals(DataFlavor.plainTextFlavor)) {
/*  911 */                 localDataFlavor2 = DataFlavor.plainTextFlavor;
/*      */               }
/*      */             }
/*      */             catch (ClassNotFoundException localClassNotFoundException3) {}
/*      */             
/*  916 */             localLinkedHashSet.add(localDataFlavor2);
/*      */           }
/*      */         }
/*      */       }
/*      */       
/*  921 */       if ("text/plain".equals(paramString))
/*      */       {
/*  923 */         localLinkedHashSet.add(DataFlavor.plainTextFlavor);
/*      */       }
/*      */       
/*      */     }
/*      */     else
/*      */     {
/*  929 */       for (String str4 : ENCODED_TEXT_CLASSES) {
/*  930 */         DataFlavor localDataFlavor1 = null;
/*      */         try {
/*  932 */           localDataFlavor1 = new DataFlavor(paramString + ";class=" + str4);
/*      */         }
/*      */         catch (ClassNotFoundException localClassNotFoundException1) {}
/*      */         
/*  936 */         localLinkedHashSet.add(localDataFlavor1);
/*      */       }
/*      */     }
/*  939 */     return localLinkedHashSet;
/*      */   }
/*      */   
/*  942 */   private static final String[] htmlDocumntTypes = { "all", "selection", "fragment" };
/*      */   
/*      */ 
/*      */ 
/*      */   private static LinkedHashSet<String> handleHtmlMimeTypes(String paramString1, String paramString2)
/*      */   {
/*  948 */     LinkedHashSet localLinkedHashSet = new LinkedHashSet();
/*      */     
/*  950 */     if ("text/html".equals(paramString1)) {
/*  951 */       for (String str : htmlDocumntTypes) {
/*  952 */         localLinkedHashSet.add(paramString2 + ";document=" + str);
/*      */       }
/*      */     } else {
/*  955 */       localLinkedHashSet.add(paramString2);
/*      */     }
/*      */     
/*  958 */     return localLinkedHashSet;
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
/*      */   public synchronized Map<DataFlavor, String> getNativesForFlavors(DataFlavor[] paramArrayOfDataFlavor)
/*      */   {
/*  989 */     if (paramArrayOfDataFlavor == null) {
/*  990 */       localObject = getFlavorsForNative(null);
/*  991 */       paramArrayOfDataFlavor = new DataFlavor[((List)localObject).size()];
/*  992 */       ((List)localObject).toArray(paramArrayOfDataFlavor);
/*      */     }
/*      */     
/*  995 */     Object localObject = new HashMap(paramArrayOfDataFlavor.length, 1.0F);
/*  996 */     for (DataFlavor localDataFlavor : paramArrayOfDataFlavor) {
/*  997 */       List localList = getNativesForFlavor(localDataFlavor);
/*  998 */       String str = localList.isEmpty() ? null : (String)localList.get(0);
/*  999 */       ((Map)localObject).put(localDataFlavor, str);
/*      */     }
/*      */     
/* 1002 */     return (Map<DataFlavor, String>)localObject;
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
/*      */   public synchronized Map<String, DataFlavor> getFlavorsForNatives(String[] paramArrayOfString)
/*      */   {
/* 1034 */     if (paramArrayOfString == null) {
/* 1035 */       localObject = getNativesForFlavor(null);
/* 1036 */       paramArrayOfString = new String[((List)localObject).size()];
/* 1037 */       ((List)localObject).toArray(paramArrayOfString);
/*      */     }
/*      */     
/* 1040 */     Object localObject = new HashMap(paramArrayOfString.length, 1.0F);
/* 1041 */     for (String str : paramArrayOfString) {
/* 1042 */       List localList = getFlavorsForNative(str);
/* 1043 */       DataFlavor localDataFlavor = localList.isEmpty() ? null : (DataFlavor)localList.get(0);
/* 1044 */       ((Map)localObject).put(str, localDataFlavor);
/*      */     }
/*      */     
/* 1047 */     return (Map<String, DataFlavor>)localObject;
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
/*      */   public synchronized void addUnencodedNativeForFlavor(DataFlavor paramDataFlavor, String paramString)
/*      */   {
/* 1072 */     if ((paramDataFlavor == null) || (paramString == null)) {
/* 1073 */       throw new NullPointerException("null arguments not permitted");
/*      */     }
/*      */     
/* 1076 */     Object localObject = (List)getFlavorToNative().get(paramDataFlavor);
/* 1077 */     if (localObject == null) {
/* 1078 */       localObject = new ArrayList(1);
/* 1079 */       getFlavorToNative().put(paramDataFlavor, localObject);
/* 1080 */     } else if (((List)localObject).contains(paramString)) {
/* 1081 */       return;
/*      */     }
/* 1083 */     ((List)localObject).add(paramString);
/* 1084 */     this.getNativesForFlavorCache.remove(paramDataFlavor);
/* 1085 */     this.getNativesForFlavorCache.remove(null);
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
/*      */   public synchronized void setNativesForFlavor(DataFlavor paramDataFlavor, String[] paramArrayOfString)
/*      */   {
/* 1118 */     if ((paramDataFlavor == null) || (paramArrayOfString == null)) {
/* 1119 */       throw new NullPointerException("null arguments not permitted");
/*      */     }
/*      */     
/* 1122 */     getFlavorToNative().remove(paramDataFlavor);
/* 1123 */     for (String str : paramArrayOfString) {
/* 1124 */       addUnencodedNativeForFlavor(paramDataFlavor, str);
/*      */     }
/* 1126 */     this.disabledMappingGenerationKeys.add(paramDataFlavor);
/*      */     
/* 1128 */     this.getNativesForFlavorCache.remove(paramDataFlavor);
/* 1129 */     this.getNativesForFlavorCache.remove(null);
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
/*      */   public synchronized void addFlavorForUnencodedNative(String paramString, DataFlavor paramDataFlavor)
/*      */   {
/* 1152 */     if ((paramString == null) || (paramDataFlavor == null)) {
/* 1153 */       throw new NullPointerException("null arguments not permitted");
/*      */     }
/*      */     
/* 1156 */     Object localObject = (List)getNativeToFlavor().get(paramString);
/* 1157 */     if (localObject == null) {
/* 1158 */       localObject = new ArrayList(1);
/* 1159 */       getNativeToFlavor().put(paramString, localObject);
/* 1160 */     } else if (((List)localObject).contains(paramDataFlavor)) {
/* 1161 */       return;
/*      */     }
/* 1163 */     ((List)localObject).add(paramDataFlavor);
/* 1164 */     this.getFlavorsForNativeCache.remove(paramString);
/* 1165 */     this.getFlavorsForNativeCache.remove(null);
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
/*      */   public synchronized void setFlavorsForNative(String paramString, DataFlavor[] paramArrayOfDataFlavor)
/*      */   {
/* 1197 */     if ((paramString == null) || (paramArrayOfDataFlavor == null)) {
/* 1198 */       throw new NullPointerException("null arguments not permitted");
/*      */     }
/*      */     
/* 1201 */     getNativeToFlavor().remove(paramString);
/* 1202 */     for (DataFlavor localDataFlavor : paramArrayOfDataFlavor) {
/* 1203 */       addFlavorForUnencodedNative(paramString, localDataFlavor);
/*      */     }
/* 1205 */     this.disabledMappingGenerationKeys.add(paramString);
/*      */     
/* 1207 */     this.getFlavorsForNativeCache.remove(paramString);
/* 1208 */     this.getFlavorsForNativeCache.remove(null);
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
/*      */   public static String encodeJavaMIMEType(String paramString)
/*      */   {
/* 1231 */     return paramString != null ? JavaMIME + paramString : null;
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
/*      */   public static String encodeDataFlavor(DataFlavor paramDataFlavor)
/*      */   {
/* 1261 */     return paramDataFlavor != null ? encodeJavaMIMEType(paramDataFlavor.getMimeType()) : null;
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
/*      */   public static boolean isJavaMIMEType(String paramString)
/*      */   {
/* 1274 */     return (paramString != null) && (paramString.startsWith(JavaMIME, 0));
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static String decodeJavaMIMEType(String paramString)
/*      */   {
/* 1286 */     return isJavaMIMEType(paramString) ? paramString.substring(JavaMIME.length(), paramString.length()).trim() : null;
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
/*      */   public static DataFlavor decodeDataFlavor(String paramString)
/*      */     throws ClassNotFoundException
/*      */   {
/* 1301 */     String str = decodeJavaMIMEType(paramString);
/* 1302 */     return str != null ? new DataFlavor(str) : null;
/*      */   }
/*      */   
/*      */ 
/*      */   private List<String> getAllNativesForType(String paramString)
/*      */   {
/* 1308 */     LinkedHashSet localLinkedHashSet = null;
/* 1309 */     for (DataFlavor localDataFlavor : convertMimeTypeToDataFlavors(paramString)) {
/* 1310 */       List localList = (List)getFlavorToNative().get(localDataFlavor);
/* 1311 */       if ((localList != null) && (!localList.isEmpty())) {
/* 1312 */         if (localLinkedHashSet == null) {
/* 1313 */           localLinkedHashSet = new LinkedHashSet();
/*      */         }
/* 1315 */         localLinkedHashSet.addAll(localList);
/*      */       }
/*      */     }
/* 1318 */     return localLinkedHashSet == null ? null : new ArrayList(localLinkedHashSet);
/*      */   }
/*      */ }


/* Location:              /Users/guanxiaoda/Documents/workspace/rt.jar!/java/awt/datatransfer/SystemFlavorMap.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */