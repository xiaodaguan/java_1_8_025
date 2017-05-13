/*      */ package java.util.regex;
/*      */ 
/*      */ import java.util.Map;
/*      */ import java.util.Objects;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public final class Matcher
/*      */   implements MatchResult
/*      */ {
/*      */   Pattern parentPattern;
/*      */   int[] groups;
/*      */   int from;
/*      */   int to;
/*      */   int lookbehindTo;
/*      */   CharSequence text;
/*      */   static final int ENDANCHOR = 1;
/*      */   static final int NOANCHOR = 0;
/*  143 */   int acceptMode = 0;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  151 */   int first = -1; int last = 0;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*  156 */   int oldLast = -1;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*  161 */   int lastAppendPosition = 0;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   int[] locals;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   boolean hitEnd;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   boolean requireEnd;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  202 */   boolean transparentBounds = false;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  208 */   boolean anchoringBounds = true;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   Matcher() {}
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   Matcher(Pattern paramPattern, CharSequence paramCharSequence)
/*      */   {
/*  220 */     this.parentPattern = paramPattern;
/*  221 */     this.text = paramCharSequence;
/*      */     
/*      */ 
/*  224 */     int i = Math.max(paramPattern.capturingGroupCount, 10);
/*  225 */     this.groups = new int[i * 2];
/*  226 */     this.locals = new int[paramPattern.localCount];
/*      */     
/*      */ 
/*  229 */     reset();
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public Pattern pattern()
/*      */   {
/*  238 */     return this.parentPattern;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public MatchResult toMatchResult()
/*      */   {
/*  250 */     Matcher localMatcher = new Matcher(this.parentPattern, this.text.toString());
/*  251 */     localMatcher.first = this.first;
/*  252 */     localMatcher.last = this.last;
/*  253 */     localMatcher.groups = ((int[])this.groups.clone());
/*  254 */     return localMatcher;
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
/*      */   public Matcher usePattern(Pattern paramPattern)
/*      */   {
/*  274 */     if (paramPattern == null)
/*  275 */       throw new IllegalArgumentException("Pattern cannot be null");
/*  276 */     this.parentPattern = paramPattern;
/*      */     
/*      */ 
/*  279 */     int i = Math.max(paramPattern.capturingGroupCount, 10);
/*  280 */     this.groups = new int[i * 2];
/*  281 */     this.locals = new int[paramPattern.localCount];
/*  282 */     for (int j = 0; j < this.groups.length; j++)
/*  283 */       this.groups[j] = -1;
/*  284 */     for (j = 0; j < this.locals.length; j++)
/*  285 */       this.locals[j] = -1;
/*  286 */     return this;
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
/*      */   public Matcher reset()
/*      */   {
/*  300 */     this.first = -1;
/*  301 */     this.last = 0;
/*  302 */     this.oldLast = -1;
/*  303 */     for (int i = 0; i < this.groups.length; i++)
/*  304 */       this.groups[i] = -1;
/*  305 */     for (i = 0; i < this.locals.length; i++)
/*  306 */       this.locals[i] = -1;
/*  307 */     this.lastAppendPosition = 0;
/*  308 */     this.from = 0;
/*  309 */     this.to = getTextLength();
/*  310 */     return this;
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
/*      */   public Matcher reset(CharSequence paramCharSequence)
/*      */   {
/*  328 */     this.text = paramCharSequence;
/*  329 */     return reset();
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
/*      */   public int start()
/*      */   {
/*  342 */     if (this.first < 0)
/*  343 */       throw new IllegalStateException("No match available");
/*  344 */     return this.first;
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
/*      */   public int start(int paramInt)
/*      */   {
/*  372 */     if (this.first < 0)
/*  373 */       throw new IllegalStateException("No match available");
/*  374 */     if ((paramInt < 0) || (paramInt > groupCount()))
/*  375 */       throw new IndexOutOfBoundsException("No group " + paramInt);
/*  376 */     return this.groups[(paramInt * 2)];
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
/*      */   public int start(String paramString)
/*      */   {
/*  401 */     return this.groups[(getMatchedGroupIndex(paramString) * 2)];
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
/*      */   public int end()
/*      */   {
/*  414 */     if (this.first < 0)
/*  415 */       throw new IllegalStateException("No match available");
/*  416 */     return this.last;
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
/*      */   public int end(int paramInt)
/*      */   {
/*  444 */     if (this.first < 0)
/*  445 */       throw new IllegalStateException("No match available");
/*  446 */     if ((paramInt < 0) || (paramInt > groupCount()))
/*  447 */       throw new IndexOutOfBoundsException("No group " + paramInt);
/*  448 */     return this.groups[(paramInt * 2 + 1)];
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
/*      */   public int end(String paramString)
/*      */   {
/*  473 */     return this.groups[(getMatchedGroupIndex(paramString) * 2 + 1)];
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
/*      */   public String group()
/*      */   {
/*  496 */     return group(0);
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
/*      */   public String group(int paramInt)
/*      */   {
/*  535 */     if (this.first < 0)
/*  536 */       throw new IllegalStateException("No match found");
/*  537 */     if ((paramInt < 0) || (paramInt > groupCount()))
/*  538 */       throw new IndexOutOfBoundsException("No group " + paramInt);
/*  539 */     if ((this.groups[(paramInt * 2)] == -1) || (this.groups[(paramInt * 2 + 1)] == -1))
/*  540 */       return null;
/*  541 */     return getSubSequence(this.groups[(paramInt * 2)], this.groups[(paramInt * 2 + 1)]).toString();
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
/*      */   public String group(String paramString)
/*      */   {
/*  572 */     int i = getMatchedGroupIndex(paramString);
/*  573 */     if ((this.groups[(i * 2)] == -1) || (this.groups[(i * 2 + 1)] == -1))
/*  574 */       return null;
/*  575 */     return getSubSequence(this.groups[(i * 2)], this.groups[(i * 2 + 1)]).toString();
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
/*      */   public int groupCount()
/*      */   {
/*  591 */     return this.parentPattern.capturingGroupCount - 1;
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
/*      */   public boolean matches()
/*      */   {
/*  604 */     return match(this.from, 1);
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
/*      */   public boolean find()
/*      */   {
/*  623 */     int i = this.last;
/*  624 */     if (i == this.first) {
/*  625 */       i++;
/*      */     }
/*      */     
/*  628 */     if (i < this.from) {
/*  629 */       i = this.from;
/*      */     }
/*      */     
/*  632 */     if (i > this.to) {
/*  633 */       for (int j = 0; j < this.groups.length; j++)
/*  634 */         this.groups[j] = -1;
/*  635 */       return false;
/*      */     }
/*  637 */     return search(i);
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
/*      */   public boolean find(int paramInt)
/*      */   {
/*  660 */     int i = getTextLength();
/*  661 */     if ((paramInt < 0) || (paramInt > i))
/*  662 */       throw new IndexOutOfBoundsException("Illegal start index");
/*  663 */     reset();
/*  664 */     return search(paramInt);
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
/*      */   public boolean lookingAt()
/*      */   {
/*  682 */     return match(this.from, 0);
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
/*      */   public static String quoteReplacement(String paramString)
/*      */   {
/*  701 */     if ((paramString.indexOf('\\') == -1) && (paramString.indexOf('$') == -1))
/*  702 */       return paramString;
/*  703 */     StringBuilder localStringBuilder = new StringBuilder();
/*  704 */     for (int i = 0; i < paramString.length(); i++) {
/*  705 */       char c = paramString.charAt(i);
/*  706 */       if ((c == '\\') || (c == '$')) {
/*  707 */         localStringBuilder.append('\\');
/*      */       }
/*  709 */       localStringBuilder.append(c);
/*      */     }
/*  711 */     return localStringBuilder.toString();
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
/*      */   public Matcher appendReplacement(StringBuffer paramStringBuffer, String paramString)
/*      */   {
/*  797 */     if (this.first < 0) {
/*  798 */       throw new IllegalStateException("No match available");
/*      */     }
/*      */     
/*  801 */     int i = 0;
/*  802 */     StringBuilder localStringBuilder1 = new StringBuilder();
/*      */     
/*  804 */     while (i < paramString.length()) {
/*  805 */       char c = paramString.charAt(i);
/*  806 */       if (c == '\\') {
/*  807 */         i++;
/*  808 */         if (i == paramString.length()) {
/*  809 */           throw new IllegalArgumentException("character to be escaped is missing");
/*      */         }
/*  811 */         c = paramString.charAt(i);
/*  812 */         localStringBuilder1.append(c);
/*  813 */         i++;
/*  814 */       } else if (c == '$')
/*      */       {
/*  816 */         i++;
/*      */         
/*  818 */         if (i == paramString.length()) {
/*  819 */           throw new IllegalArgumentException("Illegal group reference: group index is missing");
/*      */         }
/*  821 */         c = paramString.charAt(i);
/*  822 */         int j = -1;
/*  823 */         if (c == '{') {
/*  824 */           i++;
/*  825 */           StringBuilder localStringBuilder2 = new StringBuilder();
/*  826 */           while (i < paramString.length()) {
/*  827 */             c = paramString.charAt(i);
/*  828 */             if ((!ASCII.isLower(c)) && 
/*  829 */               (!ASCII.isUpper(c)) && 
/*  830 */               (!ASCII.isDigit(c))) break;
/*  831 */             localStringBuilder2.append(c);
/*  832 */             i++;
/*      */           }
/*      */           
/*      */ 
/*      */ 
/*  837 */           if (localStringBuilder2.length() == 0) {
/*  838 */             throw new IllegalArgumentException("named capturing group has 0 length name");
/*      */           }
/*  840 */           if (c != '}') {
/*  841 */             throw new IllegalArgumentException("named capturing group is missing trailing '}'");
/*      */           }
/*  843 */           String str = localStringBuilder2.toString();
/*  844 */           if (ASCII.isDigit(str.charAt(0))) {
/*  845 */             throw new IllegalArgumentException("capturing group name {" + str + "} starts with digit character");
/*      */           }
/*      */           
/*  848 */           if (!this.parentPattern.namedGroups().containsKey(str)) {
/*  849 */             throw new IllegalArgumentException("No group with name {" + str + "}");
/*      */           }
/*  851 */           j = ((Integer)this.parentPattern.namedGroups().get(str)).intValue();
/*  852 */           i++;
/*      */         }
/*      */         else {
/*  855 */           j = c - '0';
/*  856 */           if ((j < 0) || (j > 9)) {
/*  857 */             throw new IllegalArgumentException("Illegal group reference");
/*      */           }
/*  859 */           i++;
/*      */           
/*  861 */           int k = 0;
/*  862 */           while ((k == 0) && 
/*  863 */             (i < paramString.length()))
/*      */           {
/*      */ 
/*  866 */             int m = paramString.charAt(i) - '0';
/*  867 */             if ((m < 0) || (m > 9)) {
/*      */               break;
/*      */             }
/*  870 */             int n = j * 10 + m;
/*  871 */             if (groupCount() < n) {
/*  872 */               k = 1;
/*      */             } else {
/*  874 */               j = n;
/*  875 */               i++;
/*      */             }
/*      */           }
/*      */         }
/*      */         
/*  880 */         if ((start(j) != -1) && (end(j) != -1))
/*  881 */           localStringBuilder1.append(this.text, start(j), end(j));
/*      */       } else {
/*  883 */         localStringBuilder1.append(c);
/*  884 */         i++;
/*      */       }
/*      */     }
/*      */     
/*  888 */     paramStringBuffer.append(this.text, this.lastAppendPosition, this.first);
/*      */     
/*  890 */     paramStringBuffer.append(localStringBuilder1);
/*      */     
/*  892 */     this.lastAppendPosition = this.last;
/*  893 */     return this;
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
/*      */   public StringBuffer appendTail(StringBuffer paramStringBuffer)
/*      */   {
/*  911 */     paramStringBuffer.append(this.text, this.lastAppendPosition, getTextLength());
/*  912 */     return paramStringBuffer;
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
/*      */   public String replaceAll(String paramString)
/*      */   {
/*  950 */     reset();
/*  951 */     boolean bool = find();
/*  952 */     if (bool) {
/*  953 */       StringBuffer localStringBuffer = new StringBuffer();
/*      */       do {
/*  955 */         appendReplacement(localStringBuffer, paramString);
/*  956 */         bool = find();
/*  957 */       } while (bool);
/*  958 */       appendTail(localStringBuffer);
/*  959 */       return localStringBuffer.toString();
/*      */     }
/*  961 */     return this.text.toString();
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
/*      */   public String replaceFirst(String paramString)
/*      */   {
/*  998 */     if (paramString == null)
/*  999 */       throw new NullPointerException("replacement");
/* 1000 */     reset();
/* 1001 */     if (!find())
/* 1002 */       return this.text.toString();
/* 1003 */     StringBuffer localStringBuffer = new StringBuffer();
/* 1004 */     appendReplacement(localStringBuffer, paramString);
/* 1005 */     appendTail(localStringBuffer);
/* 1006 */     return localStringBuffer.toString();
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
/*      */   public Matcher region(int paramInt1, int paramInt2)
/*      */   {
/* 1035 */     if ((paramInt1 < 0) || (paramInt1 > getTextLength()))
/* 1036 */       throw new IndexOutOfBoundsException("start");
/* 1037 */     if ((paramInt2 < 0) || (paramInt2 > getTextLength()))
/* 1038 */       throw new IndexOutOfBoundsException("end");
/* 1039 */     if (paramInt1 > paramInt2)
/* 1040 */       throw new IndexOutOfBoundsException("start > end");
/* 1041 */     reset();
/* 1042 */     this.from = paramInt1;
/* 1043 */     this.to = paramInt2;
/* 1044 */     return this;
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
/*      */   public int regionStart()
/*      */   {
/* 1057 */     return this.from;
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
/*      */   public int regionEnd()
/*      */   {
/* 1070 */     return this.to;
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
/*      */   public boolean hasTransparentBounds()
/*      */   {
/* 1091 */     return this.transparentBounds;
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
/*      */   public Matcher useTransparentBounds(boolean paramBoolean)
/*      */   {
/* 1121 */     this.transparentBounds = paramBoolean;
/* 1122 */     return this;
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
/*      */   public boolean hasAnchoringBounds()
/*      */   {
/* 1142 */     return this.anchoringBounds;
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
/*      */   public Matcher useAnchoringBounds(boolean paramBoolean)
/*      */   {
/* 1167 */     this.anchoringBounds = paramBoolean;
/* 1168 */     return this;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public String toString()
/*      */   {
/* 1180 */     StringBuilder localStringBuilder = new StringBuilder();
/* 1181 */     localStringBuilder.append("java.util.regex.Matcher");
/* 1182 */     localStringBuilder.append("[pattern=" + pattern());
/* 1183 */     localStringBuilder.append(" region=");
/* 1184 */     localStringBuilder.append(regionStart() + "," + regionEnd());
/* 1185 */     localStringBuilder.append(" lastmatch=");
/* 1186 */     if ((this.first >= 0) && (group() != null)) {
/* 1187 */       localStringBuilder.append(group());
/*      */     }
/* 1189 */     localStringBuilder.append("]");
/* 1190 */     return localStringBuilder.toString();
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
/*      */   public boolean hitEnd()
/*      */   {
/* 1205 */     return this.hitEnd;
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
/*      */   public boolean requireEnd()
/*      */   {
/* 1223 */     return this.requireEnd;
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
/*      */   boolean search(int paramInt)
/*      */   {
/* 1240 */     this.hitEnd = false;
/* 1241 */     this.requireEnd = false;
/* 1242 */     paramInt = paramInt < 0 ? 0 : paramInt;
/* 1243 */     this.first = paramInt;
/* 1244 */     this.oldLast = (this.oldLast < 0 ? paramInt : this.oldLast);
/* 1245 */     for (int i = 0; i < this.groups.length; i++)
/* 1246 */       this.groups[i] = -1;
/* 1247 */     this.acceptMode = 0;
/* 1248 */     boolean bool = this.parentPattern.root.match(this, paramInt, this.text);
/* 1249 */     if (!bool)
/* 1250 */       this.first = -1;
/* 1251 */     this.oldLast = this.last;
/* 1252 */     return bool;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   boolean match(int paramInt1, int paramInt2)
/*      */   {
/* 1262 */     this.hitEnd = false;
/* 1263 */     this.requireEnd = false;
/* 1264 */     paramInt1 = paramInt1 < 0 ? 0 : paramInt1;
/* 1265 */     this.first = paramInt1;
/* 1266 */     this.oldLast = (this.oldLast < 0 ? paramInt1 : this.oldLast);
/* 1267 */     for (int i = 0; i < this.groups.length; i++)
/* 1268 */       this.groups[i] = -1;
/* 1269 */     this.acceptMode = paramInt2;
/* 1270 */     boolean bool = this.parentPattern.matchRoot.match(this, paramInt1, this.text);
/* 1271 */     if (!bool)
/* 1272 */       this.first = -1;
/* 1273 */     this.oldLast = this.last;
/* 1274 */     return bool;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   int getTextLength()
/*      */   {
/* 1283 */     return this.text.length();
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   CharSequence getSubSequence(int paramInt1, int paramInt2)
/*      */   {
/* 1294 */     return this.text.subSequence(paramInt1, paramInt2);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   char charAt(int paramInt)
/*      */   {
/* 1303 */     return this.text.charAt(paramInt);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   int getMatchedGroupIndex(String paramString)
/*      */   {
/* 1312 */     Objects.requireNonNull(paramString, "Group name");
/* 1313 */     if (this.first < 0)
/* 1314 */       throw new IllegalStateException("No match found");
/* 1315 */     if (!this.parentPattern.namedGroups().containsKey(paramString))
/* 1316 */       throw new IllegalArgumentException("No group with name <" + paramString + ">");
/* 1317 */     return ((Integer)this.parentPattern.namedGroups().get(paramString)).intValue();
/*      */   }
/*      */ }


/* Location:              /Users/guanxiaoda/Documents/workspace/rt.jar!/java/util/regex/Matcher.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */