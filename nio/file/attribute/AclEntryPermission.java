/*     */ package java.nio.file.attribute;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public enum AclEntryPermission
/*     */ {
/*  40 */   READ_DATA, 
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*  45 */   WRITE_DATA, 
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*  50 */   APPEND_DATA, 
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*  59 */   READ_NAMED_ATTRS, 
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*  68 */   WRITE_NAMED_ATTRS, 
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*  73 */   EXECUTE, 
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*  78 */   DELETE_CHILD, 
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*  83 */   READ_ATTRIBUTES, 
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*  88 */   WRITE_ATTRIBUTES, 
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*  93 */   DELETE, 
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*  98 */   READ_ACL, 
/*     */   
/*     */ 
/*     */ 
/*     */ 
/* 103 */   WRITE_ACL, 
/*     */   
/*     */ 
/*     */ 
/*     */ 
/* 108 */   WRITE_OWNER, 
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 114 */   SYNCHRONIZE;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/* 119 */   public static final AclEntryPermission LIST_DIRECTORY = READ_DATA;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/* 124 */   public static final AclEntryPermission ADD_FILE = WRITE_DATA;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/* 129 */   public static final AclEntryPermission ADD_SUBDIRECTORY = APPEND_DATA;
/*     */   
/*     */   private AclEntryPermission() {}
/*     */ }


/* Location:              /Users/guanxiaoda/Documents/workspace/rt.jar!/java/nio/file/attribute/AclEntryPermission.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */