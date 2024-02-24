/*     */ package net.optifine.shaders.config;
/*     */ 
/*     */ import net.minecraft.src.Config;
/*     */ import net.minecraft.util.Util;
/*     */ import net.optifine.shaders.Shaders;
/*     */ 
/*     */ 
/*     */ public class ShaderMacros
/*     */ {
/*     */   private static final String PREFIX_MACRO = "MC_";
/*     */   public static final String MC_VERSION = "MC_VERSION";
/*     */   public static final String MC_GL_VERSION = "MC_GL_VERSION";
/*     */   public static final String MC_GLSL_VERSION = "MC_GLSL_VERSION";
/*     */   public static final String MC_OS_WINDOWS = "MC_OS_WINDOWS";
/*     */   public static final String MC_OS_MAC = "MC_OS_MAC";
/*     */   public static final String MC_OS_LINUX = "MC_OS_LINUX";
/*     */   public static final String MC_OS_OTHER = "MC_OS_OTHER";
/*     */   public static final String MC_GL_VENDOR_ATI = "MC_GL_VENDOR_ATI";
/*     */   public static final String MC_GL_VENDOR_INTEL = "MC_GL_VENDOR_INTEL";
/*     */   public static final String MC_GL_VENDOR_NVIDIA = "MC_GL_VENDOR_NVIDIA";
/*     */   public static final String MC_GL_VENDOR_XORG = "MC_GL_VENDOR_XORG";
/*     */   public static final String MC_GL_VENDOR_OTHER = "MC_GL_VENDOR_OTHER";
/*     */   public static final String MC_GL_RENDERER_RADEON = "MC_GL_RENDERER_RADEON";
/*     */   public static final String MC_GL_RENDERER_GEFORCE = "MC_GL_RENDERER_GEFORCE";
/*     */   public static final String MC_GL_RENDERER_QUADRO = "MC_GL_RENDERER_QUADRO";
/*     */   public static final String MC_GL_RENDERER_INTEL = "MC_GL_RENDERER_INTEL";
/*     */   public static final String MC_GL_RENDERER_GALLIUM = "MC_GL_RENDERER_GALLIUM";
/*     */   public static final String MC_GL_RENDERER_MESA = "MC_GL_RENDERER_MESA";
/*     */   public static final String MC_GL_RENDERER_OTHER = "MC_GL_RENDERER_OTHER";
/*     */   public static final String MC_FXAA_LEVEL = "MC_FXAA_LEVEL";
/*     */   public static final String MC_NORMAL_MAP = "MC_NORMAL_MAP";
/*     */   public static final String MC_SPECULAR_MAP = "MC_SPECULAR_MAP";
/*     */   public static final String MC_RENDER_QUALITY = "MC_RENDER_QUALITY";
/*     */   public static final String MC_SHADOW_QUALITY = "MC_SHADOW_QUALITY";
/*     */   public static final String MC_HAND_DEPTH = "MC_HAND_DEPTH";
/*     */   public static final String MC_OLD_HAND_LIGHT = "MC_OLD_HAND_LIGHT";
/*     */   public static final String MC_OLD_LIGHTING = "MC_OLD_LIGHTING";
/*     */   private static ShaderMacro[] extensionMacros;
/*     */   
/*     */   public static String getOs() {
/*  41 */     Util.EnumOS util$enumos = Util.getOSType();
/*     */     
/*  43 */     switch (util$enumos) {
/*     */       
/*     */       case WINDOWS:
/*  46 */         return "MC_OS_WINDOWS";
/*     */       
/*     */       case OSX:
/*  49 */         return "MC_OS_MAC";
/*     */       
/*     */       case LINUX:
/*  52 */         return "MC_OS_LINUX";
/*     */     } 
/*     */     
/*  55 */     return "MC_OS_OTHER";
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static String getVendor() {
/*  61 */     String s = Config.openGlVendor;
/*     */     
/*  63 */     if (s == null)
/*     */     {
/*  65 */       return "MC_GL_VENDOR_OTHER";
/*     */     }
/*     */ 
/*     */     
/*  69 */     s = s.toLowerCase();
/*  70 */     return s.startsWith("ati") ? "MC_GL_VENDOR_ATI" : (s.startsWith("intel") ? "MC_GL_VENDOR_INTEL" : (s.startsWith("nvidia") ? "MC_GL_VENDOR_NVIDIA" : (s.startsWith("x.org") ? "MC_GL_VENDOR_XORG" : "MC_GL_VENDOR_OTHER")));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static String getRenderer() {
/*  76 */     String s = Config.openGlRenderer;
/*     */     
/*  78 */     if (s == null)
/*     */     {
/*  80 */       return "MC_GL_RENDERER_OTHER";
/*     */     }
/*     */ 
/*     */     
/*  84 */     s = s.toLowerCase();
/*  85 */     return s.startsWith("amd") ? "MC_GL_RENDERER_RADEON" : (s.startsWith("ati") ? "MC_GL_RENDERER_RADEON" : (s.startsWith("radeon") ? "MC_GL_RENDERER_RADEON" : (s.startsWith("gallium") ? "MC_GL_RENDERER_GALLIUM" : (s.startsWith("intel") ? "MC_GL_RENDERER_INTEL" : (s.startsWith("geforce") ? "MC_GL_RENDERER_GEFORCE" : (s.startsWith("nvidia") ? "MC_GL_RENDERER_GEFORCE" : (s.startsWith("quadro") ? "MC_GL_RENDERER_QUADRO" : (s.startsWith("nvs") ? "MC_GL_RENDERER_QUADRO" : (s.startsWith("mesa") ? "MC_GL_RENDERER_MESA" : "MC_GL_RENDERER_OTHER")))))))));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static String getPrefixMacro() {
/*  91 */     return "MC_";
/*     */   }
/*     */ 
/*     */   
/*     */   public static ShaderMacro[] getExtensions() {
/*  96 */     if (extensionMacros == null) {
/*     */       
/*  98 */       String[] astring = Config.getOpenGlExtensions();
/*  99 */       ShaderMacro[] ashadermacro = new ShaderMacro[astring.length];
/*     */       
/* 101 */       for (int i = 0; i < astring.length; i++)
/*     */       {
/* 103 */         ashadermacro[i] = new ShaderMacro("MC_" + astring[i], "");
/*     */       }
/*     */       
/* 106 */       extensionMacros = ashadermacro;
/*     */     } 
/*     */     
/* 109 */     return extensionMacros;
/*     */   }
/*     */ 
/*     */   
/*     */   public static String getFixedMacroLines() {
/* 114 */     StringBuilder stringbuilder = new StringBuilder();
/* 115 */     addMacroLine(stringbuilder, "MC_VERSION", Config.getMinecraftVersionInt());
/* 116 */     addMacroLine(stringbuilder, "MC_GL_VERSION " + Config.getGlVersion().toInt());
/* 117 */     addMacroLine(stringbuilder, "MC_GLSL_VERSION " + Config.getGlslVersion().toInt());
/* 118 */     addMacroLine(stringbuilder, getOs());
/* 119 */     addMacroLine(stringbuilder, getVendor());
/* 120 */     addMacroLine(stringbuilder, getRenderer());
/* 121 */     return stringbuilder.toString();
/*     */   }
/*     */ 
/*     */   
/*     */   public static String getOptionMacroLines() {
/* 126 */     StringBuilder stringbuilder = new StringBuilder();
/*     */     
/* 128 */     if (Shaders.configAntialiasingLevel > 0)
/*     */     {
/* 130 */       addMacroLine(stringbuilder, "MC_FXAA_LEVEL", Shaders.configAntialiasingLevel);
/*     */     }
/*     */     
/* 133 */     if (Shaders.configNormalMap)
/*     */     {
/* 135 */       addMacroLine(stringbuilder, "MC_NORMAL_MAP");
/*     */     }
/*     */     
/* 138 */     if (Shaders.configSpecularMap)
/*     */     {
/* 140 */       addMacroLine(stringbuilder, "MC_SPECULAR_MAP");
/*     */     }
/*     */     
/* 143 */     addMacroLine(stringbuilder, "MC_RENDER_QUALITY", Shaders.configRenderResMul);
/* 144 */     addMacroLine(stringbuilder, "MC_SHADOW_QUALITY", Shaders.configShadowResMul);
/* 145 */     addMacroLine(stringbuilder, "MC_HAND_DEPTH", Shaders.configHandDepthMul);
/*     */     
/* 147 */     if (Shaders.isOldHandLight())
/*     */     {
/* 149 */       addMacroLine(stringbuilder, "MC_OLD_HAND_LIGHT");
/*     */     }
/*     */     
/* 152 */     if (Shaders.isOldLighting())
/*     */     {
/* 154 */       addMacroLine(stringbuilder, "MC_OLD_LIGHTING");
/*     */     }
/*     */     
/* 157 */     return stringbuilder.toString();
/*     */   }
/*     */ 
/*     */   
/*     */   private static void addMacroLine(StringBuilder sb, String name, int value) {
/* 162 */     sb.append("#define ");
/* 163 */     sb.append(name);
/* 164 */     sb.append(" ");
/* 165 */     sb.append(value);
/* 166 */     sb.append("\n");
/*     */   }
/*     */ 
/*     */   
/*     */   private static void addMacroLine(StringBuilder sb, String name, float value) {
/* 171 */     sb.append("#define ");
/* 172 */     sb.append(name);
/* 173 */     sb.append(" ");
/* 174 */     sb.append(value);
/* 175 */     sb.append("\n");
/*     */   }
/*     */ 
/*     */   
/*     */   private static void addMacroLine(StringBuilder sb, String name) {
/* 180 */     sb.append("#define ");
/* 181 */     sb.append(name);
/* 182 */     sb.append("\n");
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\optifine\shaders\config\ShaderMacros.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */