/*     */ package java.time.zone;
/*     */ 
/*     */ import java.io.BufferedInputStream;
/*     */ import java.io.ByteArrayInputStream;
/*     */ import java.io.DataInputStream;
/*     */ import java.io.File;
/*     */ import java.io.FileInputStream;
/*     */ import java.io.StreamCorruptedException;
/*     */ import java.util.Arrays;
/*     */ import java.util.HashSet;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.NavigableMap;
/*     */ import java.util.Set;
/*     */ import java.util.TreeMap;
/*     */ import java.util.concurrent.ConcurrentHashMap;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ final class TzdbZoneRulesProvider
/*     */   extends ZoneRulesProvider
/*     */ {
/*     */   private List<String> regionIds;
/*     */   private String versionId;
/*  99 */   private final Map<String, Object> regionToRules = new ConcurrentHashMap();
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public TzdbZoneRulesProvider()
/*     */   {
/*     */     try
/*     */     {
/* 109 */       String str = System.getProperty("java.home") + File.separator + "lib";
/* 110 */       DataInputStream localDataInputStream = new DataInputStream(new BufferedInputStream(new FileInputStream(new File(str, "tzdb.dat"))));Object localObject1 = null;
/*     */       try
/*     */       {
/* 113 */         load(localDataInputStream);
/*     */       }
/*     */       catch (Throwable localThrowable2)
/*     */       {
/* 110 */         localObject1 = localThrowable2;throw localThrowable2;
/*     */       }
/*     */       finally
/*     */       {
/* 114 */         if (localDataInputStream != null) if (localObject1 != null) try { localDataInputStream.close(); } catch (Throwable localThrowable3) { ((Throwable)localObject1).addSuppressed(localThrowable3); } else localDataInputStream.close();
/*     */       }
/* 116 */     } catch (Exception localException) { throw new ZoneRulesException("Unable to load TZDB time-zone rules", localException);
/*     */     }
/*     */   }
/*     */   
/*     */   protected Set<String> provideZoneIds()
/*     */   {
/* 122 */     return new HashSet(this.regionIds);
/*     */   }
/*     */   
/*     */ 
/*     */   protected ZoneRules provideRules(String paramString, boolean paramBoolean)
/*     */   {
/* 128 */     Object localObject = this.regionToRules.get(paramString);
/* 129 */     if (localObject == null) {
/* 130 */       throw new ZoneRulesException("Unknown time-zone ID: " + paramString);
/*     */     }
/*     */     try {
/* 133 */       if ((localObject instanceof byte[])) {
/* 134 */         byte[] arrayOfByte = (byte[])localObject;
/* 135 */         DataInputStream localDataInputStream = new DataInputStream(new ByteArrayInputStream(arrayOfByte));
/* 136 */         localObject = Ser.read(localDataInputStream);
/* 137 */         this.regionToRules.put(paramString, localObject);
/*     */       }
/* 139 */       return (ZoneRules)localObject;
/*     */     } catch (Exception localException) {
/* 141 */       throw new ZoneRulesException("Invalid binary time-zone data: TZDB:" + paramString + ", version: " + this.versionId, localException);
/*     */     }
/*     */   }
/*     */   
/*     */   protected NavigableMap<String, ZoneRules> provideVersions(String paramString)
/*     */   {
/* 147 */     TreeMap localTreeMap = new TreeMap();
/* 148 */     ZoneRules localZoneRules = getRules(paramString, false);
/* 149 */     if (localZoneRules != null) {
/* 150 */       localTreeMap.put(this.versionId, localZoneRules);
/*     */     }
/* 152 */     return localTreeMap;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private void load(DataInputStream paramDataInputStream)
/*     */     throws Exception
/*     */   {
/* 162 */     if (paramDataInputStream.readByte() != 1) {
/* 163 */       throw new StreamCorruptedException("File format not recognised");
/*     */     }
/*     */     
/* 166 */     String str1 = paramDataInputStream.readUTF();
/* 167 */     if (!"TZDB".equals(str1)) {
/* 168 */       throw new StreamCorruptedException("File format not recognised");
/*     */     }
/*     */     
/* 171 */     int i = paramDataInputStream.readShort();
/* 172 */     for (int j = 0; j < i; j++) {
/* 173 */       this.versionId = paramDataInputStream.readUTF();
/*     */     }
/*     */     
/* 176 */     j = paramDataInputStream.readShort();
/* 177 */     String[] arrayOfString = new String[j];
/* 178 */     for (int k = 0; k < j; k++) {
/* 179 */       arrayOfString[k] = paramDataInputStream.readUTF();
/*     */     }
/* 181 */     this.regionIds = Arrays.asList(arrayOfString);
/*     */     
/* 183 */     k = paramDataInputStream.readShort();
/* 184 */     Object[] arrayOfObject = new Object[k];
/* 185 */     for (int m = 0; m < k; m++) {
/* 186 */       byte[] arrayOfByte = new byte[paramDataInputStream.readShort()];
/* 187 */       paramDataInputStream.readFully(arrayOfByte);
/* 188 */       arrayOfObject[m] = arrayOfByte;
/*     */     }
/*     */     
/* 191 */     for (m = 0; m < i; m++) {
/* 192 */       int n = paramDataInputStream.readShort();
/* 193 */       this.regionToRules.clear();
/* 194 */       for (int i1 = 0; i1 < n; i1++) {
/* 195 */         String str2 = arrayOfString[paramDataInputStream.readShort()];
/* 196 */         Object localObject = arrayOfObject[(paramDataInputStream.readShort() & 0xFFFF)];
/* 197 */         this.regionToRules.put(str2, localObject);
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   public String toString()
/*     */   {
/* 204 */     return "TZDB[" + this.versionId + "]";
/*     */   }
/*     */ }


/* Location:              /Users/guanxiaoda/Documents/workspace/rt.jar!/java/time/zone/TzdbZoneRulesProvider.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */