/*     */ package java.time;
/*     */ 
/*     */ import java.io.DataInput;
/*     */ import java.io.DataOutput;
/*     */ import java.io.IOException;
/*     */ import java.io.InvalidObjectException;
/*     */ import java.io.ObjectInputStream;
/*     */ import java.io.Serializable;
/*     */ import java.time.zone.ZoneRules;
/*     */ import java.time.zone.ZoneRulesException;
/*     */ import java.time.zone.ZoneRulesProvider;
/*     */ import java.util.Objects;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ final class ZoneRegion
/*     */   extends ZoneId
/*     */   implements Serializable
/*     */ {
/*     */   private static final long serialVersionUID = 8386373296231747096L;
/*     */   private final String id;
/*     */   private final transient ZoneRules rules;
/*     */   
/*     */   static ZoneRegion ofId(String paramString, boolean paramBoolean)
/*     */   {
/* 115 */     Objects.requireNonNull(paramString, "zoneId");
/* 116 */     checkName(paramString);
/* 117 */     ZoneRules localZoneRules = null;
/*     */     try
/*     */     {
/* 120 */       localZoneRules = ZoneRulesProvider.getRules(paramString, true);
/*     */     } catch (ZoneRulesException localZoneRulesException) {
/* 122 */       if (paramBoolean) {
/* 123 */         throw localZoneRulesException;
/*     */       }
/*     */     }
/* 126 */     return new ZoneRegion(paramString, localZoneRules);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private static void checkName(String paramString)
/*     */   {
/* 136 */     int i = paramString.length();
/* 137 */     if (i < 2) {
/* 138 */       throw new DateTimeException("Invalid ID for region-based ZoneId, invalid format: " + paramString);
/*     */     }
/* 140 */     for (int j = 0; j < i; j++) {
/* 141 */       int k = paramString.charAt(j);
/* 142 */       if (((k < 97) || (k > 122)) && 
/* 143 */         ((k < 65) || (k > 90)) && 
/* 144 */         ((k != 47) || (j == 0)) && 
/* 145 */         ((k < 48) || (k > 57) || (j == 0)) && 
/* 146 */         ((k != 126) || (j == 0)) && 
/* 147 */         ((k != 46) || (j == 0)) && 
/* 148 */         ((k != 95) || (j == 0)) && 
/* 149 */         ((k != 43) || (j == 0)) && (
/* 150 */         (k != 45) || (j == 0))) {
/* 151 */         throw new DateTimeException("Invalid ID for region-based ZoneId, invalid format: " + paramString);
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   ZoneRegion(String paramString, ZoneRules paramZoneRules)
/*     */   {
/* 163 */     this.id = paramString;
/* 164 */     this.rules = paramZoneRules;
/*     */   }
/*     */   
/*     */ 
/*     */   public String getId()
/*     */   {
/* 170 */     return this.id;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public ZoneRules getRules()
/*     */   {
/* 177 */     return this.rules != null ? this.rules : ZoneRulesProvider.getRules(this.id, false);
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
/*     */   private Object writeReplace()
/*     */   {
/* 193 */     return new Ser((byte)7, this);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private void readObject(ObjectInputStream paramObjectInputStream)
/*     */     throws InvalidObjectException
/*     */   {
/* 203 */     throw new InvalidObjectException("Deserialization via serialization delegate");
/*     */   }
/*     */   
/*     */   void write(DataOutput paramDataOutput) throws IOException
/*     */   {
/* 208 */     paramDataOutput.writeByte(7);
/* 209 */     writeExternal(paramDataOutput);
/*     */   }
/*     */   
/*     */   void writeExternal(DataOutput paramDataOutput) throws IOException {
/* 213 */     paramDataOutput.writeUTF(this.id);
/*     */   }
/*     */   
/*     */   static ZoneId readExternal(DataInput paramDataInput) throws IOException {
/* 217 */     String str = paramDataInput.readUTF();
/* 218 */     return ZoneId.of(str, false);
/*     */   }
/*     */ }


/* Location:              /Users/guanxiaoda/Documents/workspace/rt.jar!/java/time/ZoneRegion.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */