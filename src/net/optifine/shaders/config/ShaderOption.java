/*     */ package net.optifine.shaders.config;
/*     */ 
/*     */ import java.util.Arrays;
/*     */ import java.util.List;
/*     */ import net.minecraft.src.Config;
/*     */ import net.optifine.shaders.Shaders;
/*     */ import net.optifine.util.StrUtils;
/*     */ 
/*     */ 
/*     */ public abstract class ShaderOption
/*     */ {
/*  12 */   private String name = null;
/*  13 */   private String description = null;
/*  14 */   private String value = null;
/*  15 */   private String[] values = null;
/*  16 */   private String valueDefault = null;
/*  17 */   private String[] paths = null;
/*     */   
/*     */   private boolean enabled = true;
/*     */   private boolean visible = true;
/*     */   public static final String COLOR_GREEN = "§a";
/*     */   public static final String COLOR_RED = "§c";
/*     */   public static final String COLOR_BLUE = "§9";
/*     */   
/*     */   public ShaderOption(String name, String description, String value, String[] values, String valueDefault, String path) {
/*  26 */     this.name = name;
/*  27 */     this.description = description;
/*  28 */     this.value = value;
/*  29 */     this.values = values;
/*  30 */     this.valueDefault = valueDefault;
/*     */     
/*  32 */     if (path != null)
/*     */     {
/*  34 */       this.paths = new String[] { path };
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public String getName() {
/*  40 */     return this.name;
/*     */   }
/*     */ 
/*     */   
/*     */   public String getDescription() {
/*  45 */     return this.description;
/*     */   }
/*     */ 
/*     */   
/*     */   public String getDescriptionText() {
/*  50 */     String s = Config.normalize(this.description);
/*  51 */     s = StrUtils.removePrefix(s, "//");
/*  52 */     s = Shaders.translate("option." + this.name + ".comment", s);
/*  53 */     return s;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setDescription(String description) {
/*  58 */     this.description = description;
/*     */   }
/*     */ 
/*     */   
/*     */   public String getValue() {
/*  63 */     return this.value;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean setValue(String value) {
/*  68 */     int i = getIndex(value, this.values);
/*     */     
/*  70 */     if (i < 0)
/*     */     {
/*  72 */       return false;
/*     */     }
/*     */ 
/*     */     
/*  76 */     this.value = value;
/*  77 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String getValueDefault() {
/*  83 */     return this.valueDefault;
/*     */   }
/*     */ 
/*     */   
/*     */   public void resetValue() {
/*  88 */     this.value = this.valueDefault;
/*     */   }
/*     */ 
/*     */   
/*     */   public void nextValue() {
/*  93 */     int i = getIndex(this.value, this.values);
/*     */     
/*  95 */     if (i >= 0) {
/*     */       
/*  97 */       i = (i + 1) % this.values.length;
/*  98 */       this.value = this.values[i];
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void prevValue() {
/* 104 */     int i = getIndex(this.value, this.values);
/*     */     
/* 106 */     if (i >= 0) {
/*     */       
/* 108 */       i = (i - 1 + this.values.length) % this.values.length;
/* 109 */       this.value = this.values[i];
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private static int getIndex(String str, String[] strs) {
/* 115 */     for (int i = 0; i < strs.length; i++) {
/*     */       
/* 117 */       String s = strs[i];
/*     */       
/* 119 */       if (s.equals(str))
/*     */       {
/* 121 */         return i;
/*     */       }
/*     */     } 
/*     */     
/* 125 */     return -1;
/*     */   }
/*     */ 
/*     */   
/*     */   public String[] getPaths() {
/* 130 */     return this.paths;
/*     */   }
/*     */ 
/*     */   
/*     */   public void addPaths(String[] newPaths) {
/* 135 */     List<String> list = Arrays.asList(this.paths);
/*     */     
/* 137 */     for (int i = 0; i < newPaths.length; i++) {
/*     */       
/* 139 */       String s = newPaths[i];
/*     */       
/* 141 */       if (!list.contains(s))
/*     */       {
/* 143 */         this.paths = (String[])Config.addObjectToArray((Object[])this.paths, s);
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isEnabled() {
/* 150 */     return this.enabled;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setEnabled(boolean enabled) {
/* 155 */     this.enabled = enabled;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isChanged() {
/* 160 */     return !Config.equals(this.value, this.valueDefault);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isVisible() {
/* 165 */     return this.visible;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setVisible(boolean visible) {
/* 170 */     this.visible = visible;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isValidValue(String val) {
/* 175 */     return (getIndex(val, this.values) >= 0);
/*     */   }
/*     */ 
/*     */   
/*     */   public String getNameText() {
/* 180 */     return Shaders.translate("option." + this.name, this.name);
/*     */   }
/*     */ 
/*     */   
/*     */   public String getValueText(String val) {
/* 185 */     return Shaders.translate("value." + this.name + "." + val, val);
/*     */   }
/*     */ 
/*     */   
/*     */   public String getValueColor(String val) {
/* 190 */     return "";
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean matchesLine(String line) {
/* 195 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean checkUsed() {
/* 200 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isUsedInLine(String line) {
/* 205 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public String getSourceLine() {
/* 210 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   public String[] getValues() {
/* 215 */     return (String[])this.values.clone();
/*     */   }
/*     */ 
/*     */   
/*     */   public float getIndexNormalized() {
/* 220 */     if (this.values.length <= 1)
/*     */     {
/* 222 */       return 0.0F;
/*     */     }
/*     */ 
/*     */     
/* 226 */     int i = getIndex(this.value, this.values);
/*     */     
/* 228 */     if (i < 0)
/*     */     {
/* 230 */       return 0.0F;
/*     */     }
/*     */ 
/*     */     
/* 234 */     float f = i / (this.values.length - 1.0F);
/* 235 */     return f;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setIndexNormalized(float f) {
/* 242 */     if (this.values.length > 1) {
/*     */       
/* 244 */       f = Config.limit(f, 0.0F, 1.0F);
/* 245 */       int i = Math.round(f * (this.values.length - 1));
/* 246 */       this.value = this.values[i];
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/* 252 */     return this.name + ", value: " + this.value + ", valueDefault: " + this.valueDefault + ", paths: " + Config.arrayToString((Object[])this.paths);
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\optifine\shaders\config\ShaderOption.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */