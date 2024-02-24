/*     */ package net.minecraft.profiler;
/*     */ 
/*     */ import com.google.common.collect.Lists;
/*     */ import com.google.common.collect.Maps;
/*     */ import java.util.Collections;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import net.minecraft.client.renderer.GlStateManager;
/*     */ import net.minecraft.src.Config;
/*     */ import net.optifine.Lagometer;
/*     */ import org.apache.logging.log4j.LogManager;
/*     */ import org.apache.logging.log4j.Logger;
/*     */ 
/*     */ public class Profiler
/*     */ {
/*  16 */   private static final Logger logger = LogManager.getLogger();
/*  17 */   private final List<String> sectionList = Lists.newArrayList();
/*  18 */   private final List<Long> timestampList = Lists.newArrayList();
/*     */ 
/*     */   
/*     */   public boolean profilingEnabled;
/*     */ 
/*     */   
/*  24 */   private String profilingSection = "";
/*  25 */   private final Map<String, Long> profilingMap = Maps.newHashMap();
/*     */   public boolean profilerGlobalEnabled = true;
/*     */   private boolean profilerLocalEnabled;
/*     */   private static final String SCHEDULED_EXECUTABLES = "scheduledExecutables";
/*     */   private static final String TICK = "tick";
/*     */   private static final String PRE_RENDER_ERRORS = "preRenderErrors";
/*     */   private static final String RENDER = "render";
/*     */   private static final String DISPLAY = "display";
/*  33 */   private static final int HASH_SCHEDULED_EXECUTABLES = "scheduledExecutables".hashCode();
/*  34 */   private static final int HASH_TICK = "tick".hashCode();
/*  35 */   private static final int HASH_PRE_RENDER_ERRORS = "preRenderErrors".hashCode();
/*  36 */   private static final int HASH_RENDER = "render".hashCode();
/*  37 */   private static final int HASH_DISPLAY = "display".hashCode();
/*     */ 
/*     */   
/*     */   public Profiler() {
/*  41 */     this.profilerLocalEnabled = this.profilerGlobalEnabled;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void clearProfiling() {
/*  49 */     this.profilingMap.clear();
/*  50 */     this.profilingSection = "";
/*  51 */     this.sectionList.clear();
/*  52 */     this.profilerLocalEnabled = this.profilerGlobalEnabled;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void startSection(String name) {
/*  60 */     if (Lagometer.isActive()) {
/*     */       
/*  62 */       int i = name.hashCode();
/*     */       
/*  64 */       if (i == HASH_SCHEDULED_EXECUTABLES && name.equals("scheduledExecutables")) {
/*     */         
/*  66 */         Lagometer.timerScheduledExecutables.start();
/*     */       }
/*  68 */       else if (i == HASH_TICK && name.equals("tick") && Config.isMinecraftThread()) {
/*     */         
/*  70 */         Lagometer.timerScheduledExecutables.end();
/*  71 */         Lagometer.timerTick.start();
/*     */       }
/*  73 */       else if (i == HASH_PRE_RENDER_ERRORS && name.equals("preRenderErrors")) {
/*     */         
/*  75 */         Lagometer.timerTick.end();
/*     */       } 
/*     */     } 
/*     */     
/*  79 */     if (Config.isFastRender()) {
/*     */       
/*  81 */       int j = name.hashCode();
/*     */       
/*  83 */       if (j == HASH_RENDER && name.equals("render")) {
/*     */         
/*  85 */         GlStateManager.clearEnabled = false;
/*     */       }
/*  87 */       else if (j == HASH_DISPLAY && name.equals("display")) {
/*     */         
/*  89 */         GlStateManager.clearEnabled = true;
/*     */       } 
/*     */     } 
/*     */     
/*  93 */     if (this.profilerLocalEnabled)
/*     */     {
/*  95 */       if (this.profilingEnabled) {
/*     */         
/*  97 */         if (this.profilingSection.length() > 0)
/*     */         {
/*  99 */           this.profilingSection += ".";
/*     */         }
/*     */         
/* 102 */         this.profilingSection += name;
/* 103 */         this.sectionList.add(this.profilingSection);
/* 104 */         this.timestampList.add(Long.valueOf(System.nanoTime()));
/*     */       } 
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void endSection() {
/* 114 */     if (this.profilerLocalEnabled)
/*     */     {
/* 116 */       if (this.profilingEnabled) {
/*     */         
/* 118 */         long i = System.nanoTime();
/* 119 */         long j = ((Long)this.timestampList.remove(this.timestampList.size() - 1)).longValue();
/* 120 */         this.sectionList.remove(this.sectionList.size() - 1);
/* 121 */         long k = i - j;
/*     */         
/* 123 */         if (this.profilingMap.containsKey(this.profilingSection)) {
/*     */           
/* 125 */           this.profilingMap.put(this.profilingSection, Long.valueOf(((Long)this.profilingMap.get(this.profilingSection)).longValue() + k));
/*     */         }
/*     */         else {
/*     */           
/* 129 */           this.profilingMap.put(this.profilingSection, Long.valueOf(k));
/*     */         } 
/*     */         
/* 132 */         if (k > 100000000L)
/*     */         {
/* 134 */           logger.warn("Something's taking too long! '" + this.profilingSection + "' took aprox " + (k / 1000000.0D) + " ms");
/*     */         }
/*     */         
/* 137 */         this.profilingSection = !this.sectionList.isEmpty() ? this.sectionList.get(this.sectionList.size() - 1) : "";
/*     */       } 
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public List<Result> getProfilingData(String profilerName) {
/* 144 */     if (!this.profilingEnabled)
/*     */     {
/* 146 */       return null;
/*     */     }
/*     */ 
/*     */     
/* 150 */     long i = this.profilingMap.containsKey("root") ? ((Long)this.profilingMap.get("root")).longValue() : 0L;
/* 151 */     long j = this.profilingMap.containsKey(profilerName) ? ((Long)this.profilingMap.get(profilerName)).longValue() : -1L;
/* 152 */     List<Result> list = Lists.newArrayList();
/*     */     
/* 154 */     if (profilerName.length() > 0)
/*     */     {
/* 156 */       profilerName = profilerName + ".";
/*     */     }
/*     */     
/* 159 */     long k = 0L;
/*     */     
/* 161 */     for (String s : this.profilingMap.keySet()) {
/*     */       
/* 163 */       if (s.length() > profilerName.length() && s.startsWith(profilerName) && s.indexOf(".", profilerName.length() + 1) < 0)
/*     */       {
/* 165 */         k += ((Long)this.profilingMap.get(s)).longValue();
/*     */       }
/*     */     } 
/*     */     
/* 169 */     float f = (float)k;
/*     */     
/* 171 */     if (k < j)
/*     */     {
/* 173 */       k = j;
/*     */     }
/*     */     
/* 176 */     if (i < k)
/*     */     {
/* 178 */       i = k;
/*     */     }
/*     */     
/* 181 */     for (String s1 : this.profilingMap.keySet()) {
/*     */       
/* 183 */       if (s1.length() > profilerName.length() && s1.startsWith(profilerName) && s1.indexOf(".", profilerName.length() + 1) < 0) {
/*     */         
/* 185 */         long l = ((Long)this.profilingMap.get(s1)).longValue();
/* 186 */         double d0 = l * 100.0D / k;
/* 187 */         double d1 = l * 100.0D / i;
/* 188 */         String s2 = s1.substring(profilerName.length());
/* 189 */         list.add(new Result(s2, d0, d1));
/*     */       } 
/*     */     } 
/*     */     
/* 193 */     for (String s3 : this.profilingMap.keySet())
/*     */     {
/* 195 */       this.profilingMap.put(s3, Long.valueOf(((Long)this.profilingMap.get(s3)).longValue() * 950L / 1000L));
/*     */     }
/*     */     
/* 198 */     if ((float)k > f)
/*     */     {
/* 200 */       list.add(new Result("unspecified", ((float)k - f) * 100.0D / k, ((float)k - f) * 100.0D / i));
/*     */     }
/*     */     
/* 203 */     Collections.sort(list);
/* 204 */     list.add(0, new Result(profilerName, 100.0D, k * 100.0D / i));
/* 205 */     return list;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void endStartSection(String name) {
/* 214 */     if (this.profilerLocalEnabled) {
/*     */       
/* 216 */       endSection();
/* 217 */       startSection(name);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public String getNameOfLastSection() {
/* 223 */     return (this.sectionList.size() == 0) ? "[UNKNOWN]" : this.sectionList.get(this.sectionList.size() - 1);
/*     */   }
/*     */ 
/*     */   
/*     */   public void startSection(Class<?> p_startSection_1_) {
/* 228 */     if (this.profilingEnabled)
/*     */     {
/* 230 */       startSection(p_startSection_1_.getSimpleName());
/*     */     }
/*     */   }
/*     */   
/*     */   public static final class Result
/*     */     implements Comparable<Result>
/*     */   {
/*     */     public double field_76332_a;
/*     */     public double field_76330_b;
/*     */     public String field_76331_c;
/*     */     
/*     */     public Result(String profilerName, double usePercentage, double totalUsePercentage) {
/* 242 */       this.field_76331_c = profilerName;
/* 243 */       this.field_76332_a = usePercentage;
/* 244 */       this.field_76330_b = totalUsePercentage;
/*     */     }
/*     */ 
/*     */     
/*     */     public int compareTo(Result p_compareTo_1_) {
/* 249 */       return (p_compareTo_1_.field_76332_a < this.field_76332_a) ? -1 : ((p_compareTo_1_.field_76332_a > this.field_76332_a) ? 1 : p_compareTo_1_.field_76331_c.compareTo(this.field_76331_c));
/*     */     }
/*     */ 
/*     */     
/*     */     public int getColor() {
/* 254 */       return (this.field_76331_c.hashCode() & 0xAAAAAA) + 4473924;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\minecraft\profiler\Profiler.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */