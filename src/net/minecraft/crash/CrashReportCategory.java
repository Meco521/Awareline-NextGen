/*     */ package net.minecraft.crash;
/*     */ 
/*     */ import com.google.common.collect.Lists;
/*     */ import java.util.List;
/*     */ import java.util.concurrent.Callable;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.util.BlockPos;
/*     */ 
/*     */ 
/*     */ public class CrashReportCategory
/*     */ {
/*     */   private final CrashReport crashReport;
/*     */   private final String name;
/*  15 */   private final List<Entry> children = Lists.newArrayList();
/*  16 */   private StackTraceElement[] stackTrace = new StackTraceElement[0];
/*     */ 
/*     */   
/*     */   public CrashReportCategory(CrashReport report, String name) {
/*  20 */     this.crashReport = report;
/*  21 */     this.name = name;
/*     */   }
/*     */ 
/*     */   
/*     */   public static String getCoordinateInfo(double x, double y, double z) {
/*  26 */     return String.format("%.2f,%.2f,%.2f - %s", new Object[] { Double.valueOf(x), Double.valueOf(y), Double.valueOf(z), getCoordinateInfo(new BlockPos(x, y, z)) });
/*     */   }
/*     */ 
/*     */   
/*     */   public static String getCoordinateInfo(BlockPos pos) {
/*  31 */     int i = pos.getX();
/*  32 */     int j = pos.getY();
/*  33 */     int k = pos.getZ();
/*  34 */     StringBuilder stringbuilder = new StringBuilder();
/*     */ 
/*     */     
/*     */     try {
/*  38 */       stringbuilder.append(String.format("World: (%d,%d,%d)", new Object[] { Integer.valueOf(i), Integer.valueOf(j), Integer.valueOf(k) }));
/*     */     }
/*  40 */     catch (Throwable var17) {
/*     */       
/*  42 */       stringbuilder.append("(Error finding world loc)");
/*     */     } 
/*     */     
/*  45 */     stringbuilder.append(", ");
/*     */ 
/*     */     
/*     */     try {
/*  49 */       int l = i >> 4;
/*  50 */       int i1 = k >> 4;
/*  51 */       int j1 = i & 0xF;
/*  52 */       int k1 = j >> 4;
/*  53 */       int l1 = k & 0xF;
/*  54 */       int i2 = l << 4;
/*  55 */       int j2 = i1 << 4;
/*  56 */       int k2 = (l + 1 << 4) - 1;
/*  57 */       int l2 = (i1 + 1 << 4) - 1;
/*  58 */       stringbuilder.append(String.format("Chunk: (at %d,%d,%d in %d,%d; contains blocks %d,0,%d to %d,255,%d)", new Object[] { Integer.valueOf(j1), Integer.valueOf(k1), Integer.valueOf(l1), Integer.valueOf(l), Integer.valueOf(i1), Integer.valueOf(i2), Integer.valueOf(j2), Integer.valueOf(k2), Integer.valueOf(l2) }));
/*     */     }
/*  60 */     catch (Throwable var16) {
/*     */       
/*  62 */       stringbuilder.append("(Error finding chunk loc)");
/*     */     } 
/*     */     
/*  65 */     stringbuilder.append(", ");
/*     */ 
/*     */     
/*     */     try {
/*  69 */       int j3 = i >> 9;
/*  70 */       int k3 = k >> 9;
/*  71 */       int l3 = j3 << 5;
/*  72 */       int i4 = k3 << 5;
/*  73 */       int j4 = (j3 + 1 << 5) - 1;
/*  74 */       int k4 = (k3 + 1 << 5) - 1;
/*  75 */       int l4 = j3 << 9;
/*  76 */       int i5 = k3 << 9;
/*  77 */       int j5 = (j3 + 1 << 9) - 1;
/*  78 */       int i3 = (k3 + 1 << 9) - 1;
/*  79 */       stringbuilder.append(String.format("Region: (%d,%d; contains chunks %d,%d to %d,%d, blocks %d,0,%d to %d,255,%d)", new Object[] { Integer.valueOf(j3), Integer.valueOf(k3), Integer.valueOf(l3), Integer.valueOf(i4), Integer.valueOf(j4), Integer.valueOf(k4), Integer.valueOf(l4), Integer.valueOf(i5), Integer.valueOf(j5), Integer.valueOf(i3) }));
/*     */     }
/*  81 */     catch (Throwable var15) {
/*     */       
/*  83 */       stringbuilder.append("(Error finding world loc)");
/*     */     } 
/*     */     
/*  86 */     return stringbuilder.toString();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addCrashSectionCallable(String sectionName, Callable<String> callable) {
/*     */     try {
/*  96 */       addCrashSection(sectionName, callable.call());
/*     */     }
/*  98 */     catch (Throwable throwable) {
/*     */       
/* 100 */       addCrashSectionThrowable(sectionName, throwable);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addCrashSection(String sectionName, Object value) {
/* 109 */     this.children.add(new Entry(sectionName, value));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addCrashSectionThrowable(String sectionName, Throwable throwable) {
/* 117 */     addCrashSection(sectionName, throwable);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getPrunedStackTrace(int size) {
/* 126 */     StackTraceElement[] astacktraceelement = Thread.currentThread().getStackTrace();
/*     */     
/* 128 */     if (astacktraceelement.length <= 0)
/*     */     {
/* 130 */       return 0;
/*     */     }
/*     */ 
/*     */     
/* 134 */     this.stackTrace = new StackTraceElement[astacktraceelement.length - 3 - size];
/* 135 */     System.arraycopy(astacktraceelement, 3 + size, this.stackTrace, 0, this.stackTrace.length);
/* 136 */     return this.stackTrace.length;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean firstTwoElementsOfStackTraceMatch(StackTraceElement s1, StackTraceElement s2) {
/* 145 */     if (this.stackTrace.length != 0 && s1 != null) {
/*     */       
/* 147 */       StackTraceElement stacktraceelement = this.stackTrace[0];
/*     */       
/* 149 */       if (stacktraceelement.isNativeMethod() == s1.isNativeMethod() && stacktraceelement.getClassName().equals(s1.getClassName()) && stacktraceelement.getFileName().equals(s1.getFileName()) && stacktraceelement.getMethodName().equals(s1.getMethodName())) {
/*     */         
/* 151 */         if (((s2 != null) ? true : false) != ((this.stackTrace.length > 1) ? true : false))
/*     */         {
/* 153 */           return false;
/*     */         }
/* 155 */         if (s2 != null && !this.stackTrace[1].equals(s2))
/*     */         {
/* 157 */           return false;
/*     */         }
/*     */ 
/*     */         
/* 161 */         this.stackTrace[0] = s1;
/* 162 */         return true;
/*     */       } 
/*     */ 
/*     */ 
/*     */       
/* 167 */       return false;
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 172 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void trimStackTraceEntriesFromBottom(int amount) {
/* 181 */     StackTraceElement[] astacktraceelement = new StackTraceElement[this.stackTrace.length - amount];
/* 182 */     System.arraycopy(this.stackTrace, 0, astacktraceelement, 0, astacktraceelement.length);
/* 183 */     this.stackTrace = astacktraceelement;
/*     */   }
/*     */ 
/*     */   
/*     */   public void appendToStringBuilder(StringBuilder builder) {
/* 188 */     builder.append("-- ").append(this.name).append(" --\n");
/* 189 */     builder.append("Details:");
/*     */     
/* 191 */     for (Entry crashreportcategory$entry : this.children) {
/*     */       
/* 193 */       builder.append("\n\t");
/* 194 */       builder.append(crashreportcategory$entry.getKey());
/* 195 */       builder.append(": ");
/* 196 */       builder.append(crashreportcategory$entry.getValue());
/*     */     } 
/*     */     
/* 199 */     if (this.stackTrace != null && this.stackTrace.length > 0) {
/*     */       
/* 201 */       builder.append("\nStacktrace:");
/*     */       
/* 203 */       for (StackTraceElement stacktraceelement : this.stackTrace) {
/*     */         
/* 205 */         builder.append("\n\tat ");
/* 206 */         builder.append(stacktraceelement.toString());
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public StackTraceElement[] getStackTrace() {
/* 213 */     return this.stackTrace;
/*     */   }
/*     */ 
/*     */   
/*     */   public static void addBlockInfo(CrashReportCategory category, final BlockPos pos, final Block blockIn, final int blockData) {
/* 218 */     final int i = Block.getIdFromBlock(blockIn);
/* 219 */     category.addCrashSectionCallable("Block type", new Callable<String>()
/*     */         {
/*     */           public String call()
/*     */           {
/*     */             try {
/* 224 */               return String.format("ID #%d (%s // %s)", new Object[] { Integer.valueOf(this.val$i), this.val$blockIn.getUnlocalizedName(), this.val$blockIn.getClass().getCanonicalName() });
/*     */             }
/* 226 */             catch (Throwable var2) {
/*     */               
/* 228 */               return "ID #" + i;
/*     */             } 
/*     */           }
/*     */         });
/* 232 */     category.addCrashSectionCallable("Block data value", new Callable<String>()
/*     */         {
/*     */           public String call() {
/* 235 */             if (blockData < 0)
/*     */             {
/* 237 */               return "Unknown? (Got " + blockData + ")";
/*     */             }
/*     */ 
/*     */             
/* 241 */             String s = String.format("%4s", new Object[] { Integer.toBinaryString(this.val$blockData) }).replace(" ", "0");
/* 242 */             return String.format("%1$d / 0x%1$X / 0b%2$s", new Object[] { Integer.valueOf(this.val$blockData), s });
/*     */           }
/*     */         });
/*     */     
/* 246 */     category.addCrashSectionCallable("Block location", new Callable<String>()
/*     */         {
/*     */           public String call() {
/* 249 */             return CrashReportCategory.getCoordinateInfo(pos);
/*     */           }
/*     */         });
/*     */   }
/*     */ 
/*     */   
/*     */   public static void addBlockInfo(CrashReportCategory category, final BlockPos pos, final IBlockState state) {
/* 256 */     category.addCrashSectionCallable("Block", new Callable<String>()
/*     */         {
/*     */           public String call() {
/* 259 */             return state.toString();
/*     */           }
/*     */         });
/* 262 */     category.addCrashSectionCallable("Block location", new Callable<String>()
/*     */         {
/*     */           public String call() {
/* 265 */             return CrashReportCategory.getCoordinateInfo(pos);
/*     */           }
/*     */         });
/*     */   }
/*     */ 
/*     */   
/*     */   static class Entry
/*     */   {
/*     */     private final String key;
/*     */     private final String value;
/*     */     
/*     */     public Entry(String key, Object value) {
/* 277 */       this.key = key;
/*     */       
/* 279 */       if (value == null) {
/*     */         
/* 281 */         this.value = "~~NULL~~";
/*     */       }
/* 283 */       else if (value instanceof Throwable) {
/*     */         
/* 285 */         Throwable throwable = (Throwable)value;
/* 286 */         this.value = "~~ERROR~~ " + throwable.getClass().getSimpleName() + ": " + throwable.getMessage();
/*     */       }
/*     */       else {
/*     */         
/* 290 */         this.value = value.toString();
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public String getKey() {
/* 296 */       return this.key;
/*     */     }
/*     */ 
/*     */     
/*     */     public String getValue() {
/* 301 */       return this.value;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\minecraft\crash\CrashReportCategory.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */