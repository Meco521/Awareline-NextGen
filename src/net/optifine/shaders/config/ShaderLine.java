/*     */ package net.optifine.shaders.config;
/*     */ 
/*     */ import net.minecraft.src.Config;
/*     */ import net.optifine.util.StrUtils;
/*     */ import org.lwjgl.util.vector.Vector4f;
/*     */ 
/*     */ 
/*     */ public class ShaderLine
/*     */ {
/*     */   private final int type;
/*     */   private final String name;
/*     */   private final String value;
/*     */   private final String line;
/*     */   public static final int TYPE_UNIFORM = 1;
/*     */   public static final int TYPE_ATTRIBUTE = 2;
/*     */   public static final int TYPE_CONST_INT = 3;
/*     */   public static final int TYPE_CONST_FLOAT = 4;
/*     */   public static final int TYPE_CONST_BOOL = 5;
/*     */   public static final int TYPE_PROPERTY = 6;
/*     */   public static final int TYPE_EXTENSION = 7;
/*     */   public static final int TYPE_CONST_VEC4 = 8;
/*     */   
/*     */   public ShaderLine(int type, String name, String value, String line) {
/*  24 */     this.type = type;
/*  25 */     this.name = name;
/*  26 */     this.value = value;
/*  27 */     this.line = line;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getType() {
/*  32 */     return this.type;
/*     */   }
/*     */ 
/*     */   
/*     */   public String getName() {
/*  37 */     return this.name;
/*     */   }
/*     */ 
/*     */   
/*     */   public String getValue() {
/*  42 */     return this.value;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isUniform() {
/*  47 */     return (this.type == 1);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isUniform(String name) {
/*  52 */     return (isUniform() && name.equals(this.name));
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isAttribute() {
/*  57 */     return (this.type == 2);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isAttribute(String name) {
/*  62 */     return (isAttribute() && name.equals(this.name));
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isProperty() {
/*  67 */     return (this.type == 6);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isConstInt() {
/*  72 */     return (this.type == 3);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isConstFloat() {
/*  77 */     return (this.type == 4);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isConstBool() {
/*  82 */     return (this.type == 5);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isExtension() {
/*  87 */     return (this.type == 7);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isConstVec4() {
/*  92 */     return (this.type == 8);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isProperty(String name) {
/*  97 */     return (isProperty() && name.equals(this.name));
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isProperty(String name, String value) {
/* 102 */     return (isProperty(name) && value.equals(this.value));
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isConstInt(String name) {
/* 107 */     return (isConstInt() && name.equals(this.name));
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isConstIntSuffix(String suffix) {
/* 112 */     return (isConstInt() && this.name.endsWith(suffix));
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isConstFloat(String name) {
/* 117 */     return (isConstFloat() && name.equals(this.name));
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isConstBool(String name) {
/* 122 */     return (isConstBool() && name.equals(this.name));
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isExtension(String name) {
/* 127 */     return (isExtension() && name.equals(this.name));
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isConstBoolSuffix(String suffix) {
/* 132 */     return (isConstBool() && this.name.endsWith(suffix));
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isConstBoolSuffix(String suffix, boolean val) {
/* 137 */     return (isConstBoolSuffix(suffix) && getValueBool() == val);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isConstBool(String name1, String name2) {
/* 142 */     return (isConstBool(name1) || isConstBool(name2));
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isConstBool(String name1, String name2, String name3) {
/* 147 */     return (isConstBool(name1) || isConstBool(name2) || isConstBool(name3));
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isConstBool(String name, boolean val) {
/* 152 */     return (isConstBool(name) && getValueBool() == val);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isConstBool(String name1, String name2, boolean val) {
/* 157 */     return (isConstBool(name1, name2) && getValueBool() == val);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isConstBool(String name1, String name2, String name3, boolean val) {
/* 162 */     return (isConstBool(name1, name2, name3) && getValueBool() == val);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isConstVec4Suffix(String suffix) {
/* 167 */     return (isConstVec4() && this.name.endsWith(suffix));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int getValueInt() {
/*     */     try {
/* 174 */       return Integer.parseInt(this.value);
/*     */     }
/* 176 */     catch (NumberFormatException var2) {
/*     */       
/* 178 */       throw new NumberFormatException("Invalid integer: " + this.value + ", line: " + this.line);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public float getValueFloat() {
/*     */     try {
/* 186 */       return Float.parseFloat(this.value);
/*     */     }
/* 188 */     catch (NumberFormatException var2) {
/*     */       
/* 190 */       throw new NumberFormatException("Invalid float: " + this.value + ", line: " + this.line);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public Vector4f getValueVec4() {
/* 196 */     if (this.value == null)
/*     */     {
/* 198 */       return null;
/*     */     }
/*     */ 
/*     */     
/* 202 */     String s = this.value.trim();
/* 203 */     s = StrUtils.removePrefix(s, "vec4");
/* 204 */     s = StrUtils.trim(s, " ()");
/* 205 */     String[] astring = Config.tokenize(s, ", ");
/*     */     
/* 207 */     if (astring.length != 4)
/*     */     {
/* 209 */       return null;
/*     */     }
/*     */ 
/*     */     
/* 213 */     float[] afloat = new float[4];
/*     */     
/* 215 */     for (int i = 0; i < astring.length; i++) {
/*     */       
/* 217 */       String s1 = astring[i];
/* 218 */       s1 = StrUtils.removeSuffix(s1, new String[] { "F", "f" });
/* 219 */       float f = Config.parseFloat(s1, Float.MAX_VALUE);
/*     */       
/* 221 */       if (f == Float.MAX_VALUE)
/*     */       {
/* 223 */         return null;
/*     */       }
/*     */       
/* 226 */       afloat[i] = f;
/*     */     } 
/*     */     
/* 229 */     return new Vector4f(afloat[0], afloat[1], afloat[2], afloat[3]);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean getValueBool() {
/* 236 */     String s = this.value.toLowerCase();
/*     */     
/* 238 */     if (!s.equals("true") && !s.equals("false"))
/*     */     {
/* 240 */       throw new RuntimeException("Invalid boolean: " + this.value + ", line: " + this.line);
/*     */     }
/*     */ 
/*     */     
/* 244 */     return Boolean.valueOf(this.value).booleanValue();
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\optifine\shaders\config\ShaderLine.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */