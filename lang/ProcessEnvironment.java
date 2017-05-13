/*     */ package java.lang;
/*     */ 
/*     */ import java.util.AbstractCollection;
/*     */ import java.util.AbstractMap;
/*     */ import java.util.AbstractSet;
/*     */ import java.util.Collection;
/*     */ import java.util.Collections;
/*     */ import java.util.HashMap;
/*     */ import java.util.Iterator;
/*     */ import java.util.Map;
/*     */ import java.util.Map.Entry;
/*     */ import java.util.Set;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ final class ProcessEnvironment
/*     */ {
/*     */   private static final HashMap<Variable, Value> theEnvironment;
/*     */   
/*     */   static
/*     */   {
/*  70 */     byte[][] arrayOfByte = environ();
/*  71 */     theEnvironment = new HashMap(arrayOfByte.length / 2 + 3);
/*     */     
/*     */ 
/*  74 */     for (int i = arrayOfByte.length - 1; i > 0; i -= 2) {
/*  75 */       theEnvironment.put(Variable.valueOf(arrayOfByte[(i - 1)]), 
/*  76 */         Value.valueOf(arrayOfByte[i]));
/*     */     }
/*     */   }
/*     */   
/*  80 */   private static final Map<String, String> theUnmodifiableEnvironment = Collections.unmodifiableMap(new StringEnvironment(theEnvironment));
/*     */   static final int MIN_NAME_LENGTH = 0;
/*     */   
/*     */   static String getenv(String paramString)
/*     */   {
/*  85 */     return (String)theUnmodifiableEnvironment.get(paramString);
/*     */   }
/*     */   
/*     */   static Map<String, String> getenv()
/*     */   {
/*  90 */     return theUnmodifiableEnvironment;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   static Map<String, String> environment()
/*     */   {
/*  97 */     return new StringEnvironment((Map)theEnvironment.clone());
/*     */   }
/*     */   
/*     */   static Map<String, String> emptyEnvironment(int paramInt)
/*     */   {
/* 102 */     return new StringEnvironment(new HashMap(paramInt));
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private static void validateVariable(String paramString)
/*     */   {
/* 112 */     if ((paramString.indexOf('=') != -1) || 
/* 113 */       (paramString.indexOf(0) != -1)) {
/* 114 */       throw new IllegalArgumentException("Invalid environment variable name: \"" + paramString + "\"");
/*     */     }
/*     */   }
/*     */   
/*     */   private static void validateValue(String paramString)
/*     */   {
/* 120 */     if (paramString.indexOf(0) != -1) {
/* 121 */       throw new IllegalArgumentException("Invalid environment variable value: \"" + paramString + "\"");
/*     */     }
/*     */   }
/*     */   
/*     */   private static abstract class ExternalData
/*     */   {
/*     */     protected final String str;
/*     */     protected final byte[] bytes;
/*     */     
/*     */     protected ExternalData(String paramString, byte[] paramArrayOfByte)
/*     */     {
/* 132 */       this.str = paramString;
/* 133 */       this.bytes = paramArrayOfByte;
/*     */     }
/*     */     
/*     */     public byte[] getBytes() {
/* 137 */       return this.bytes;
/*     */     }
/*     */     
/*     */     public String toString() {
/* 141 */       return this.str;
/*     */     }
/*     */     
/*     */     public boolean equals(Object paramObject)
/*     */     {
/* 146 */       return ((paramObject instanceof ExternalData)) && (ProcessEnvironment.arrayEquals(getBytes(), ((ExternalData)paramObject).getBytes()));
/*     */     }
/*     */     
/*     */     public int hashCode() {
/* 150 */       return ProcessEnvironment.arrayHash(getBytes());
/*     */     }
/*     */   }
/*     */   
/*     */   private static class Variable extends ProcessEnvironment.ExternalData implements Comparable<Variable>
/*     */   {
/*     */     protected Variable(String paramString, byte[] paramArrayOfByte)
/*     */     {
/* 158 */       super(paramArrayOfByte);
/*     */     }
/*     */     
/*     */     public static Variable valueOfQueryOnly(Object paramObject) {
/* 162 */       return valueOfQueryOnly((String)paramObject);
/*     */     }
/*     */     
/*     */     public static Variable valueOfQueryOnly(String paramString) {
/* 166 */       return new Variable(paramString, paramString.getBytes());
/*     */     }
/*     */     
/*     */     public static Variable valueOf(String paramString) {
/* 170 */       ProcessEnvironment.validateVariable(paramString);
/* 171 */       return valueOfQueryOnly(paramString);
/*     */     }
/*     */     
/*     */     public static Variable valueOf(byte[] paramArrayOfByte) {
/* 175 */       return new Variable(new String(paramArrayOfByte), paramArrayOfByte);
/*     */     }
/*     */     
/*     */     public int compareTo(Variable paramVariable) {
/* 179 */       return ProcessEnvironment.arrayCompare(getBytes(), paramVariable.getBytes());
/*     */     }
/*     */     
/*     */     public boolean equals(Object paramObject) {
/* 183 */       return ((paramObject instanceof Variable)) && (super.equals(paramObject));
/*     */     }
/*     */   }
/*     */   
/*     */   private static class Value extends ProcessEnvironment.ExternalData implements Comparable<Value>
/*     */   {
/*     */     protected Value(String paramString, byte[] paramArrayOfByte)
/*     */     {
/* 191 */       super(paramArrayOfByte);
/*     */     }
/*     */     
/*     */     public static Value valueOfQueryOnly(Object paramObject) {
/* 195 */       return valueOfQueryOnly((String)paramObject);
/*     */     }
/*     */     
/*     */     public static Value valueOfQueryOnly(String paramString) {
/* 199 */       return new Value(paramString, paramString.getBytes());
/*     */     }
/*     */     
/*     */     public static Value valueOf(String paramString) {
/* 203 */       ProcessEnvironment.validateValue(paramString);
/* 204 */       return valueOfQueryOnly(paramString);
/*     */     }
/*     */     
/*     */     public static Value valueOf(byte[] paramArrayOfByte) {
/* 208 */       return new Value(new String(paramArrayOfByte), paramArrayOfByte);
/*     */     }
/*     */     
/*     */     public int compareTo(Value paramValue) {
/* 212 */       return ProcessEnvironment.arrayCompare(getBytes(), paramValue.getBytes());
/*     */     }
/*     */     
/*     */     public boolean equals(Object paramObject) {
/* 216 */       return ((paramObject instanceof Value)) && (super.equals(paramObject));
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */   private static class StringEnvironment
/*     */     extends AbstractMap<String, String>
/*     */   {
/*     */     private Map<ProcessEnvironment.Variable, ProcessEnvironment.Value> m;
/*     */     
/* 226 */     private static String toString(ProcessEnvironment.Value paramValue) { return paramValue == null ? null : paramValue.toString(); }
/*     */     
/* 228 */     public StringEnvironment(Map<ProcessEnvironment.Variable, ProcessEnvironment.Value> paramMap) { this.m = paramMap; }
/* 229 */     public int size() { return this.m.size(); }
/* 230 */     public boolean isEmpty() { return this.m.isEmpty(); }
/* 231 */     public void clear() { this.m.clear(); }
/*     */     
/* 233 */     public boolean containsKey(Object paramObject) { return this.m.containsKey(ProcessEnvironment.Variable.valueOfQueryOnly(paramObject)); }
/*     */     
/*     */     public boolean containsValue(Object paramObject) {
/* 236 */       return this.m.containsValue(ProcessEnvironment.Value.valueOfQueryOnly(paramObject));
/*     */     }
/*     */     
/* 239 */     public String get(Object paramObject) { return toString((ProcessEnvironment.Value)this.m.get(ProcessEnvironment.Variable.valueOfQueryOnly(paramObject))); }
/*     */     
/*     */     public String put(String paramString1, String paramString2) {
/* 242 */       return toString((ProcessEnvironment.Value)this.m.put(ProcessEnvironment.Variable.valueOf(paramString1), 
/* 243 */         ProcessEnvironment.Value.valueOf(paramString2)));
/*     */     }
/*     */     
/* 246 */     public String remove(Object paramObject) { return toString((ProcessEnvironment.Value)this.m.remove(ProcessEnvironment.Variable.valueOfQueryOnly(paramObject))); }
/*     */     
/*     */     public Set<String> keySet() {
/* 249 */       return new ProcessEnvironment.StringKeySet(this.m.keySet());
/*     */     }
/*     */     
/* 252 */     public Set<Map.Entry<String, String>> entrySet() { return new ProcessEnvironment.StringEntrySet(this.m.entrySet()); }
/*     */     
/*     */     public Collection<String> values() {
/* 255 */       return new ProcessEnvironment.StringValues(this.m.values());
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     public byte[] toEnvironmentBlock(int[] paramArrayOfInt)
/*     */     {
/* 271 */       int i = this.m.size() * 2;
/* 272 */       for (Object localObject = this.m.entrySet().iterator(); ((Iterator)localObject).hasNext();) { Map.Entry localEntry1 = (Map.Entry)((Iterator)localObject).next();
/* 273 */         i += ((ProcessEnvironment.Variable)localEntry1.getKey()).getBytes().length;
/* 274 */         i += ((ProcessEnvironment.Value)localEntry1.getValue()).getBytes().length;
/*     */       }
/*     */       
/* 277 */       localObject = new byte[i];
/*     */       
/* 279 */       int j = 0;
/* 280 */       for (Map.Entry localEntry2 : this.m.entrySet()) {
/* 281 */         byte[] arrayOfByte1 = ((ProcessEnvironment.Variable)localEntry2.getKey()).getBytes();
/* 282 */         byte[] arrayOfByte2 = ((ProcessEnvironment.Value)localEntry2.getValue()).getBytes();
/* 283 */         System.arraycopy(arrayOfByte1, 0, localObject, j, arrayOfByte1.length);
/* 284 */         j += arrayOfByte1.length;
/* 285 */         localObject[(j++)] = 61;
/* 286 */         System.arraycopy(arrayOfByte2, 0, localObject, j, arrayOfByte2.length);
/* 287 */         j += arrayOfByte2.length + 1;
/*     */       }
/*     */       
/*     */ 
/* 291 */       paramArrayOfInt[0] = this.m.size();
/* 292 */       return (byte[])localObject;
/*     */     }
/*     */   }
/*     */   
/*     */   static byte[] toEnvironmentBlock(Map<String, String> paramMap, int[] paramArrayOfInt)
/*     */   {
/* 298 */     return paramMap == null ? null : ((StringEnvironment)paramMap).toEnvironmentBlock(paramArrayOfInt);
/*     */   }
/*     */   
/*     */   private static class StringEntry
/*     */     implements Map.Entry<String, String>
/*     */   {
/*     */     private final Map.Entry<ProcessEnvironment.Variable, ProcessEnvironment.Value> e;
/*     */     
/* 306 */     public StringEntry(Map.Entry<ProcessEnvironment.Variable, ProcessEnvironment.Value> paramEntry) { this.e = paramEntry; }
/* 307 */     public String getKey() { return ((ProcessEnvironment.Variable)this.e.getKey()).toString(); }
/* 308 */     public String getValue() { return ((ProcessEnvironment.Value)this.e.getValue()).toString(); }
/*     */     
/* 310 */     public String setValue(String paramString) { return ((ProcessEnvironment.Value)this.e.setValue(ProcessEnvironment.Value.valueOf(paramString))).toString(); }
/*     */     
/* 312 */     public String toString() { return getKey() + "=" + getValue(); }
/*     */     
/*     */ 
/* 315 */     public boolean equals(Object paramObject) { return ((paramObject instanceof StringEntry)) && (this.e.equals(((StringEntry)paramObject).e)); }
/*     */     
/* 317 */     public int hashCode() { return this.e.hashCode(); }
/*     */   }
/*     */   
/*     */   private static class StringEntrySet
/*     */     extends AbstractSet<Map.Entry<String, String>> {
/*     */     private final Set<Map.Entry<ProcessEnvironment.Variable, ProcessEnvironment.Value>> s;
/*     */     
/* 324 */     public StringEntrySet(Set<Map.Entry<ProcessEnvironment.Variable, ProcessEnvironment.Value>> paramSet) { this.s = paramSet; }
/* 325 */     public int size() { return this.s.size(); }
/* 326 */     public boolean isEmpty() { return this.s.isEmpty(); }
/* 327 */     public void clear() { this.s.clear(); }
/*     */     
/* 329 */     public Iterator<Map.Entry<String, String>> iterator() { new Iterator() {
/* 330 */         Iterator<Map.Entry<ProcessEnvironment.Variable, ProcessEnvironment.Value>> i = ProcessEnvironment.StringEntrySet.this.s.iterator();
/* 331 */         public boolean hasNext() { return this.i.hasNext(); }
/*     */         
/* 333 */         public Map.Entry<String, String> next() { return new ProcessEnvironment.StringEntry((Map.Entry)this.i.next()); }
/*     */         
/* 335 */         public void remove() { this.i.remove(); }
/*     */       }; }
/*     */     
/*     */     private static Map.Entry<ProcessEnvironment.Variable, ProcessEnvironment.Value> vvEntry(Object paramObject) {
/* 339 */       if ((paramObject instanceof ProcessEnvironment.StringEntry))
/* 340 */         return ProcessEnvironment.StringEntry.access$600((ProcessEnvironment.StringEntry)paramObject);
/* 341 */       new Map.Entry() {
/*     */         public ProcessEnvironment.Variable getKey() {
/* 343 */           return ProcessEnvironment.Variable.valueOfQueryOnly(((Map.Entry)this.val$o).getKey());
/*     */         }
/*     */         
/* 346 */         public ProcessEnvironment.Value getValue() { return ProcessEnvironment.Value.valueOfQueryOnly(((Map.Entry)this.val$o).getValue()); }
/*     */         
/*     */ 
/* 349 */         public ProcessEnvironment.Value setValue(ProcessEnvironment.Value paramAnonymousValue) { throw new UnsupportedOperationException(); }
/*     */       };
/*     */     }
/*     */     
/* 353 */     public boolean contains(Object paramObject) { return this.s.contains(vvEntry(paramObject)); }
/* 354 */     public boolean remove(Object paramObject) { return this.s.remove(vvEntry(paramObject)); }
/*     */     
/*     */ 
/* 357 */     public boolean equals(Object paramObject) { return ((paramObject instanceof StringEntrySet)) && (this.s.equals(((StringEntrySet)paramObject).s)); }
/*     */     
/* 359 */     public int hashCode() { return this.s.hashCode(); }
/*     */   }
/*     */   
/*     */   private static class StringValues
/*     */     extends AbstractCollection<String> {
/*     */     private final Collection<ProcessEnvironment.Value> c;
/*     */     
/* 366 */     public StringValues(Collection<ProcessEnvironment.Value> paramCollection) { this.c = paramCollection; }
/* 367 */     public int size() { return this.c.size(); }
/* 368 */     public boolean isEmpty() { return this.c.isEmpty(); }
/* 369 */     public void clear() { this.c.clear(); }
/*     */     
/* 371 */     public Iterator<String> iterator() { new Iterator() {
/* 372 */         Iterator<ProcessEnvironment.Value> i = ProcessEnvironment.StringValues.this.c.iterator();
/* 373 */         public boolean hasNext() { return this.i.hasNext(); }
/* 374 */         public String next() { return ((ProcessEnvironment.Value)this.i.next()).toString(); }
/* 375 */         public void remove() { this.i.remove(); }
/*     */       }; }
/*     */     
/*     */     public boolean contains(Object paramObject) {
/* 379 */       return this.c.contains(ProcessEnvironment.Value.valueOfQueryOnly(paramObject));
/*     */     }
/*     */     
/* 382 */     public boolean remove(Object paramObject) { return this.c.remove(ProcessEnvironment.Value.valueOfQueryOnly(paramObject)); }
/*     */     
/*     */ 
/*     */ 
/* 386 */     public boolean equals(Object paramObject) { return ((paramObject instanceof StringValues)) && (this.c.equals(((StringValues)paramObject).c)); }
/*     */     
/* 388 */     public int hashCode() { return this.c.hashCode(); }
/*     */   }
/*     */   
/*     */   private static class StringKeySet extends AbstractSet<String> { private final Set<ProcessEnvironment.Variable> s;
/*     */     
/* 393 */     public StringKeySet(Set<ProcessEnvironment.Variable> paramSet) { this.s = paramSet; }
/* 394 */     public int size() { return this.s.size(); }
/* 395 */     public boolean isEmpty() { return this.s.isEmpty(); }
/* 396 */     public void clear() { this.s.clear(); }
/*     */     
/* 398 */     public Iterator<String> iterator() { new Iterator() {
/* 399 */         Iterator<ProcessEnvironment.Variable> i = ProcessEnvironment.StringKeySet.this.s.iterator();
/* 400 */         public boolean hasNext() { return this.i.hasNext(); }
/* 401 */         public String next() { return ((ProcessEnvironment.Variable)this.i.next()).toString(); }
/* 402 */         public void remove() { this.i.remove(); }
/*     */       }; }
/*     */     
/*     */     public boolean contains(Object paramObject) {
/* 406 */       return this.s.contains(ProcessEnvironment.Variable.valueOfQueryOnly(paramObject));
/*     */     }
/*     */     
/* 409 */     public boolean remove(Object paramObject) { return this.s.remove(ProcessEnvironment.Variable.valueOfQueryOnly(paramObject)); }
/*     */   }
/*     */   
/*     */ 
/*     */   private static int arrayCompare(byte[] paramArrayOfByte1, byte[] paramArrayOfByte2)
/*     */   {
/* 415 */     int i = paramArrayOfByte1.length < paramArrayOfByte2.length ? paramArrayOfByte1.length : paramArrayOfByte2.length;
/* 416 */     for (int j = 0; j < i; j++)
/* 417 */       if (paramArrayOfByte1[j] != paramArrayOfByte2[j])
/* 418 */         return paramArrayOfByte1[j] - paramArrayOfByte2[j];
/* 419 */     return paramArrayOfByte1.length - paramArrayOfByte2.length;
/*     */   }
/*     */   
/*     */   private static boolean arrayEquals(byte[] paramArrayOfByte1, byte[] paramArrayOfByte2)
/*     */   {
/* 424 */     if (paramArrayOfByte1.length != paramArrayOfByte2.length)
/* 425 */       return false;
/* 426 */     for (int i = 0; i < paramArrayOfByte1.length; i++)
/* 427 */       if (paramArrayOfByte1[i] != paramArrayOfByte2[i])
/* 428 */         return false;
/* 429 */     return true;
/*     */   }
/*     */   
/*     */   private static int arrayHash(byte[] paramArrayOfByte)
/*     */   {
/* 434 */     int i = 0;
/* 435 */     for (int j = 0; j < paramArrayOfByte.length; j++)
/* 436 */       i = 31 * i + paramArrayOfByte[j];
/* 437 */     return i;
/*     */   }
/*     */   
/*     */   private static native byte[][] environ();
/*     */ }


/* Location:              /Users/guanxiaoda/Documents/workspace/rt.jar!/java/lang/ProcessEnvironment.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */