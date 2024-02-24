/*     */ package net.minecraft.client.renderer;
/*     */ 
/*     */ import java.nio.ByteBuffer;
/*     */ import java.nio.FloatBuffer;
/*     */ import java.nio.IntBuffer;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.settings.GameSettings;
/*     */ import net.minecraft.src.Config;
/*     */ import org.lwjgl.opengl.ARBCopyBuffer;
/*     */ import org.lwjgl.opengl.ARBFramebufferObject;
/*     */ import org.lwjgl.opengl.ARBMultitexture;
/*     */ import org.lwjgl.opengl.ARBShaderObjects;
/*     */ import org.lwjgl.opengl.ARBVertexBufferObject;
/*     */ import org.lwjgl.opengl.ARBVertexShader;
/*     */ import org.lwjgl.opengl.ContextCapabilities;
/*     */ import org.lwjgl.opengl.EXTBlendFuncSeparate;
/*     */ import org.lwjgl.opengl.EXTFramebufferObject;
/*     */ import org.lwjgl.opengl.GL11;
/*     */ import org.lwjgl.opengl.GL13;
/*     */ import org.lwjgl.opengl.GL14;
/*     */ import org.lwjgl.opengl.GL15;
/*     */ import org.lwjgl.opengl.GL20;
/*     */ import org.lwjgl.opengl.GL30;
/*     */ import org.lwjgl.opengl.GL31;
/*     */ import org.lwjgl.opengl.GLContext;
/*     */ import oshi.SystemInfo;
/*     */ import oshi.hardware.Processor;
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
/*     */ public class OpenGlHelper
/*     */ {
/*     */   public static boolean nvidia;
/*     */   public static boolean ati;
/*     */   public static int GL_FRAMEBUFFER;
/*     */   public static int GL_RENDERBUFFER;
/*     */   public static int GL_COLOR_ATTACHMENT0;
/*     */   public static int GL_DEPTH_ATTACHMENT;
/*     */   public static int GL_FRAMEBUFFER_COMPLETE;
/*     */   public static int GL_FB_INCOMPLETE_ATTACHMENT;
/*     */   public static int GL_FB_INCOMPLETE_MISS_ATTACH;
/*     */   public static int GL_FB_INCOMPLETE_DRAW_BUFFER;
/*     */   public static int GL_FB_INCOMPLETE_READ_BUFFER;
/*     */   private static int framebufferType;
/*     */   public static boolean framebufferSupported;
/*     */   private static boolean shadersAvailable;
/*     */   private static boolean arbShaders;
/*     */   public static int GL_LINK_STATUS;
/*     */   public static int GL_COMPILE_STATUS;
/*     */   public static int GL_VERTEX_SHADER;
/*     */   public static int GL_FRAGMENT_SHADER;
/*     */   private static boolean arbMultitexture;
/*     */   public static int defaultTexUnit;
/*     */   public static int lightmapTexUnit;
/*     */   public static int GL_TEXTURE2;
/*  75 */   private static String logText = ""; private static boolean arbTextureEnvCombine; public static int GL_COMBINE; public static int GL_INTERPOLATE; public static int GL_PRIMARY_COLOR; public static int GL_CONSTANT; public static int GL_PREVIOUS; public static int GL_COMBINE_RGB; public static int GL_SOURCE0_RGB; public static int GL_SOURCE1_RGB; public static int GL_SOURCE2_RGB; public static int GL_OPERAND0_RGB; public static int GL_OPERAND1_RGB; public static int GL_OPERAND2_RGB; public static int GL_COMBINE_ALPHA; public static int GL_SOURCE0_ALPHA; public static int GL_SOURCE1_ALPHA; public static int GL_SOURCE2_ALPHA; public static int GL_OPERAND0_ALPHA; public static int GL_OPERAND1_ALPHA; public static int GL_OPERAND2_ALPHA; private static boolean openGL14; public static boolean extBlendFuncSeparate; public static boolean openGL21; public static boolean shadersSupported;
/*     */   private static String cpu;
/*     */   public static boolean vboSupported;
/*     */   public static boolean vboSupportedAti;
/*     */   private static boolean arbVbo;
/*     */   public static int GL_ARRAY_BUFFER;
/*     */   public static int GL_STATIC_DRAW;
/*  82 */   public static float lastBrightnessX = 0.0F;
/*  83 */   public static float lastBrightnessY = 0.0F;
/*     */   
/*     */   public static boolean openGL31;
/*     */   
/*     */   public static boolean vboRegions;
/*     */   
/*     */   public static int GL_COPY_READ_BUFFER;
/*     */   
/*     */   public static int GL_COPY_WRITE_BUFFER;
/*     */   public static final int GL_QUADS = 7;
/*     */   public static final int GL_TRIANGLES = 4;
/*     */   
/*     */   public static void initializeTextures() {
/*  96 */     Config.initDisplay();
/*  97 */     ContextCapabilities contextcapabilities = GLContext.getCapabilities();
/*  98 */     arbMultitexture = (contextcapabilities.GL_ARB_multitexture && !contextcapabilities.OpenGL13);
/*  99 */     arbTextureEnvCombine = (contextcapabilities.GL_ARB_texture_env_combine && !contextcapabilities.OpenGL13);
/* 100 */     openGL31 = contextcapabilities.OpenGL31;
/*     */     
/* 102 */     if (openGL31) {
/*     */       
/* 104 */       GL_COPY_READ_BUFFER = 36662;
/* 105 */       GL_COPY_WRITE_BUFFER = 36663;
/*     */     }
/*     */     else {
/*     */       
/* 109 */       GL_COPY_READ_BUFFER = 36662;
/* 110 */       GL_COPY_WRITE_BUFFER = 36663;
/*     */     } 
/*     */     
/* 113 */     boolean flag = (openGL31 || contextcapabilities.GL_ARB_copy_buffer);
/* 114 */     boolean flag1 = contextcapabilities.OpenGL14;
/* 115 */     vboRegions = (flag && flag1);
/*     */     
/* 117 */     if (!vboRegions) {
/*     */       
/* 119 */       List<String> list = new ArrayList<>();
/*     */       
/* 121 */       if (!flag)
/*     */       {
/* 123 */         list.add("OpenGL 1.3, ARB_copy_buffer");
/*     */       }
/*     */       
/* 126 */       if (!flag1)
/*     */       {
/* 128 */         list.add("OpenGL 1.4");
/*     */       }
/*     */       
/* 131 */       String s = "VboRegions not supported, missing: " + Config.listToString(list);
/* 132 */       Config.dbg(s);
/* 133 */       logText += s + "\n";
/*     */     } 
/*     */     
/* 136 */     if (arbMultitexture) {
/*     */       
/* 138 */       logText += "Using ARB_multitexture.\n";
/* 139 */       defaultTexUnit = 33984;
/* 140 */       lightmapTexUnit = 33985;
/* 141 */       GL_TEXTURE2 = 33986;
/*     */     }
/*     */     else {
/*     */       
/* 145 */       logText += "Using GL 1.3 multitexturing.\n";
/* 146 */       defaultTexUnit = 33984;
/* 147 */       lightmapTexUnit = 33985;
/* 148 */       GL_TEXTURE2 = 33986;
/*     */     } 
/*     */     
/* 151 */     if (arbTextureEnvCombine) {
/*     */       
/* 153 */       logText += "Using ARB_texture_env_combine.\n";
/* 154 */       GL_COMBINE = 34160;
/* 155 */       GL_INTERPOLATE = 34165;
/* 156 */       GL_PRIMARY_COLOR = 34167;
/* 157 */       GL_CONSTANT = 34166;
/* 158 */       GL_PREVIOUS = 34168;
/* 159 */       GL_COMBINE_RGB = 34161;
/* 160 */       GL_SOURCE0_RGB = 34176;
/* 161 */       GL_SOURCE1_RGB = 34177;
/* 162 */       GL_SOURCE2_RGB = 34178;
/* 163 */       GL_OPERAND0_RGB = 34192;
/* 164 */       GL_OPERAND1_RGB = 34193;
/* 165 */       GL_OPERAND2_RGB = 34194;
/* 166 */       GL_COMBINE_ALPHA = 34162;
/* 167 */       GL_SOURCE0_ALPHA = 34184;
/* 168 */       GL_SOURCE1_ALPHA = 34185;
/* 169 */       GL_SOURCE2_ALPHA = 34186;
/* 170 */       GL_OPERAND0_ALPHA = 34200;
/* 171 */       GL_OPERAND1_ALPHA = 34201;
/* 172 */       GL_OPERAND2_ALPHA = 34202;
/*     */     }
/*     */     else {
/*     */       
/* 176 */       logText += "Using GL 1.3 texture combiners.\n";
/* 177 */       GL_COMBINE = 34160;
/* 178 */       GL_INTERPOLATE = 34165;
/* 179 */       GL_PRIMARY_COLOR = 34167;
/* 180 */       GL_CONSTANT = 34166;
/* 181 */       GL_PREVIOUS = 34168;
/* 182 */       GL_COMBINE_RGB = 34161;
/* 183 */       GL_SOURCE0_RGB = 34176;
/* 184 */       GL_SOURCE1_RGB = 34177;
/* 185 */       GL_SOURCE2_RGB = 34178;
/* 186 */       GL_OPERAND0_RGB = 34192;
/* 187 */       GL_OPERAND1_RGB = 34193;
/* 188 */       GL_OPERAND2_RGB = 34194;
/* 189 */       GL_COMBINE_ALPHA = 34162;
/* 190 */       GL_SOURCE0_ALPHA = 34184;
/* 191 */       GL_SOURCE1_ALPHA = 34185;
/* 192 */       GL_SOURCE2_ALPHA = 34186;
/* 193 */       GL_OPERAND0_ALPHA = 34200;
/* 194 */       GL_OPERAND1_ALPHA = 34201;
/* 195 */       GL_OPERAND2_ALPHA = 34202;
/*     */     } 
/*     */     
/* 198 */     extBlendFuncSeparate = (contextcapabilities.GL_EXT_blend_func_separate && !contextcapabilities.OpenGL14);
/* 199 */     openGL14 = (contextcapabilities.OpenGL14 || contextcapabilities.GL_EXT_blend_func_separate);
/* 200 */     framebufferSupported = (openGL14 && (contextcapabilities.GL_ARB_framebuffer_object || contextcapabilities.GL_EXT_framebuffer_object || contextcapabilities.OpenGL30));
/*     */     
/* 202 */     if (framebufferSupported) {
/*     */       
/* 204 */       logText += "Using framebuffer objects because ";
/*     */       
/* 206 */       if (contextcapabilities.OpenGL30)
/*     */       {
/* 208 */         logText += "OpenGL 3.0 is supported and separate blending is supported.\n";
/* 209 */         framebufferType = 0;
/* 210 */         GL_FRAMEBUFFER = 36160;
/* 211 */         GL_RENDERBUFFER = 36161;
/* 212 */         GL_COLOR_ATTACHMENT0 = 36064;
/* 213 */         GL_DEPTH_ATTACHMENT = 36096;
/* 214 */         GL_FRAMEBUFFER_COMPLETE = 36053;
/* 215 */         GL_FB_INCOMPLETE_ATTACHMENT = 36054;
/* 216 */         GL_FB_INCOMPLETE_MISS_ATTACH = 36055;
/* 217 */         GL_FB_INCOMPLETE_DRAW_BUFFER = 36059;
/* 218 */         GL_FB_INCOMPLETE_READ_BUFFER = 36060;
/*     */       }
/* 220 */       else if (contextcapabilities.GL_ARB_framebuffer_object)
/*     */       {
/* 222 */         logText += "ARB_framebuffer_object is supported and separate blending is supported.\n";
/* 223 */         framebufferType = 1;
/* 224 */         GL_FRAMEBUFFER = 36160;
/* 225 */         GL_RENDERBUFFER = 36161;
/* 226 */         GL_COLOR_ATTACHMENT0 = 36064;
/* 227 */         GL_DEPTH_ATTACHMENT = 36096;
/* 228 */         GL_FRAMEBUFFER_COMPLETE = 36053;
/* 229 */         GL_FB_INCOMPLETE_MISS_ATTACH = 36055;
/* 230 */         GL_FB_INCOMPLETE_ATTACHMENT = 36054;
/* 231 */         GL_FB_INCOMPLETE_DRAW_BUFFER = 36059;
/* 232 */         GL_FB_INCOMPLETE_READ_BUFFER = 36060;
/*     */       }
/* 234 */       else if (contextcapabilities.GL_EXT_framebuffer_object)
/*     */       {
/* 236 */         logText += "EXT_framebuffer_object is supported.\n";
/* 237 */         framebufferType = 2;
/* 238 */         GL_FRAMEBUFFER = 36160;
/* 239 */         GL_RENDERBUFFER = 36161;
/* 240 */         GL_COLOR_ATTACHMENT0 = 36064;
/* 241 */         GL_DEPTH_ATTACHMENT = 36096;
/* 242 */         GL_FRAMEBUFFER_COMPLETE = 36053;
/* 243 */         GL_FB_INCOMPLETE_MISS_ATTACH = 36055;
/* 244 */         GL_FB_INCOMPLETE_ATTACHMENT = 36054;
/* 245 */         GL_FB_INCOMPLETE_DRAW_BUFFER = 36059;
/* 246 */         GL_FB_INCOMPLETE_READ_BUFFER = 36060;
/*     */       }
/*     */     
/*     */     } else {
/*     */       
/* 251 */       logText += "Not using framebuffer objects because ";
/* 252 */       logText += "OpenGL 1.4 is " + (contextcapabilities.OpenGL14 ? "" : "not ") + "supported, ";
/* 253 */       logText += "EXT_blend_func_separate is " + (contextcapabilities.GL_EXT_blend_func_separate ? "" : "not ") + "supported, ";
/* 254 */       logText += "OpenGL 3.0 is " + (contextcapabilities.OpenGL30 ? "" : "not ") + "supported, ";
/* 255 */       logText += "ARB_framebuffer_object is " + (contextcapabilities.GL_ARB_framebuffer_object ? "" : "not ") + "supported, and ";
/* 256 */       logText += "EXT_framebuffer_object is " + (contextcapabilities.GL_EXT_framebuffer_object ? "" : "not ") + "supported.\n";
/*     */     } 
/*     */     
/* 259 */     openGL21 = contextcapabilities.OpenGL21;
/* 260 */     shadersAvailable = (openGL21 || (contextcapabilities.GL_ARB_vertex_shader && contextcapabilities.GL_ARB_fragment_shader && contextcapabilities.GL_ARB_shader_objects));
/* 261 */     logText += "Shaders are " + (shadersAvailable ? "" : "not ") + "available because ";
/*     */     
/* 263 */     if (shadersAvailable) {
/*     */       
/* 265 */       if (contextcapabilities.OpenGL21)
/*     */       {
/* 267 */         logText += "OpenGL 2.1 is supported.\n";
/* 268 */         arbShaders = false;
/* 269 */         GL_LINK_STATUS = 35714;
/* 270 */         GL_COMPILE_STATUS = 35713;
/* 271 */         GL_VERTEX_SHADER = 35633;
/* 272 */         GL_FRAGMENT_SHADER = 35632;
/*     */       }
/*     */       else
/*     */       {
/* 276 */         logText += "ARB_shader_objects, ARB_vertex_shader, and ARB_fragment_shader are supported.\n";
/* 277 */         arbShaders = true;
/* 278 */         GL_LINK_STATUS = 35714;
/* 279 */         GL_COMPILE_STATUS = 35713;
/* 280 */         GL_VERTEX_SHADER = 35633;
/* 281 */         GL_FRAGMENT_SHADER = 35632;
/*     */       }
/*     */     
/*     */     } else {
/*     */       
/* 286 */       logText += "OpenGL 2.1 is " + (contextcapabilities.OpenGL21 ? "" : "not ") + "supported, ";
/* 287 */       logText += "ARB_shader_objects is " + (contextcapabilities.GL_ARB_shader_objects ? "" : "not ") + "supported, ";
/* 288 */       logText += "ARB_vertex_shader is " + (contextcapabilities.GL_ARB_vertex_shader ? "" : "not ") + "supported, and ";
/* 289 */       logText += "ARB_fragment_shader is " + (contextcapabilities.GL_ARB_fragment_shader ? "" : "not ") + "supported.\n";
/*     */     } 
/*     */     
/* 292 */     shadersSupported = (framebufferSupported && shadersAvailable);
/* 293 */     String s1 = GL11.glGetString(7936).toLowerCase();
/* 294 */     nvidia = s1.contains("nvidia");
/* 295 */     arbVbo = (!contextcapabilities.OpenGL15 && contextcapabilities.GL_ARB_vertex_buffer_object);
/* 296 */     vboSupported = (contextcapabilities.OpenGL15 || arbVbo);
/* 297 */     logText += "VBOs are " + (vboSupported ? "" : "not ") + "available because ";
/*     */     
/* 299 */     if (vboSupported)
/*     */     {
/* 301 */       if (arbVbo) {
/*     */         
/* 303 */         logText += "ARB_vertex_buffer_object is supported.\n";
/* 304 */         GL_STATIC_DRAW = 35044;
/* 305 */         GL_ARRAY_BUFFER = 34962;
/*     */       }
/*     */       else {
/*     */         
/* 309 */         logText += "OpenGL 1.5 is supported.\n";
/* 310 */         GL_STATIC_DRAW = 35044;
/* 311 */         GL_ARRAY_BUFFER = 34962;
/*     */       } 
/*     */     }
/*     */     
/* 315 */     ati = s1.contains("ati");
/*     */     
/* 317 */     if (ati)
/*     */     {
/* 319 */       if (vboSupported) {
/*     */         
/* 321 */         vboSupportedAti = true;
/*     */       }
/*     */       else {
/*     */         
/* 325 */         GameSettings.Options.RENDER_DISTANCE.setValueMax(16.0F);
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     try {
/* 331 */       Processor[] aprocessor = (new SystemInfo()).getHardware().getProcessors();
/* 332 */       cpu = String.format("%dx %s", new Object[] { Integer.valueOf(aprocessor.length), aprocessor[0] }).replaceAll("\\s+", " ");
/*     */     }
/* 334 */     catch (Throwable throwable) {}
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean areShadersSupported() {
/* 342 */     return shadersSupported;
/*     */   }
/*     */ 
/*     */   
/*     */   public static String getLogText() {
/* 347 */     return logText;
/*     */   }
/*     */ 
/*     */   
/*     */   public static int glGetProgrami(int program, int pname) {
/* 352 */     return arbShaders ? ARBShaderObjects.glGetObjectParameteriARB(program, pname) : GL20.glGetProgrami(program, pname);
/*     */   }
/*     */ 
/*     */   
/*     */   public static void glAttachShader(int program, int shaderIn) {
/* 357 */     if (arbShaders) {
/*     */       
/* 359 */       ARBShaderObjects.glAttachObjectARB(program, shaderIn);
/*     */     }
/*     */     else {
/*     */       
/* 363 */       GL20.glAttachShader(program, shaderIn);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public static void glDeleteShader(int p_153180_0_) {
/* 369 */     if (arbShaders) {
/*     */       
/* 371 */       ARBShaderObjects.glDeleteObjectARB(p_153180_0_);
/*     */     }
/*     */     else {
/*     */       
/* 375 */       GL20.glDeleteShader(p_153180_0_);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static int glCreateShader(int type) {
/* 384 */     return arbShaders ? ARBShaderObjects.glCreateShaderObjectARB(type) : GL20.glCreateShader(type);
/*     */   }
/*     */ 
/*     */   
/*     */   public static void glShaderSource(int shaderIn, ByteBuffer string) {
/* 389 */     if (arbShaders) {
/*     */       
/* 391 */       ARBShaderObjects.glShaderSourceARB(shaderIn, string);
/*     */     }
/*     */     else {
/*     */       
/* 395 */       GL20.glShaderSource(shaderIn, string);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public static void glCompileShader(int shaderIn) {
/* 401 */     if (arbShaders) {
/*     */       
/* 403 */       ARBShaderObjects.glCompileShaderARB(shaderIn);
/*     */     }
/*     */     else {
/*     */       
/* 407 */       GL20.glCompileShader(shaderIn);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public static int glGetShaderi(int shaderIn, int pname) {
/* 413 */     return arbShaders ? ARBShaderObjects.glGetObjectParameteriARB(shaderIn, pname) : GL20.glGetShaderi(shaderIn, pname);
/*     */   }
/*     */ 
/*     */   
/*     */   public static String glGetShaderInfoLog(int shaderIn, int maxLength) {
/* 418 */     return arbShaders ? ARBShaderObjects.glGetInfoLogARB(shaderIn, maxLength) : GL20.glGetShaderInfoLog(shaderIn, maxLength);
/*     */   }
/*     */ 
/*     */   
/*     */   public static String glGetProgramInfoLog(int program, int maxLength) {
/* 423 */     return arbShaders ? ARBShaderObjects.glGetInfoLogARB(program, maxLength) : GL20.glGetProgramInfoLog(program, maxLength);
/*     */   }
/*     */ 
/*     */   
/*     */   public static void glUseProgram(int program) {
/* 428 */     if (arbShaders) {
/*     */       
/* 430 */       ARBShaderObjects.glUseProgramObjectARB(program);
/*     */     }
/*     */     else {
/*     */       
/* 434 */       GL20.glUseProgram(program);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public static int glCreateProgram() {
/* 440 */     return arbShaders ? ARBShaderObjects.glCreateProgramObjectARB() : GL20.glCreateProgram();
/*     */   }
/*     */ 
/*     */   
/*     */   public static void glDeleteProgram(int program) {
/* 445 */     if (arbShaders) {
/*     */       
/* 447 */       ARBShaderObjects.glDeleteObjectARB(program);
/*     */     }
/*     */     else {
/*     */       
/* 451 */       GL20.glDeleteProgram(program);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public static void glLinkProgram(int program) {
/* 457 */     if (arbShaders) {
/*     */       
/* 459 */       ARBShaderObjects.glLinkProgramARB(program);
/*     */     }
/*     */     else {
/*     */       
/* 463 */       GL20.glLinkProgram(program);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public static int glGetUniformLocation(int programObj, CharSequence name) {
/* 469 */     return arbShaders ? ARBShaderObjects.glGetUniformLocationARB(programObj, name) : GL20.glGetUniformLocation(programObj, name);
/*     */   }
/*     */ 
/*     */   
/*     */   public static void glUniform1(int location, IntBuffer values) {
/* 474 */     if (arbShaders) {
/*     */       
/* 476 */       ARBShaderObjects.glUniform1ARB(location, values);
/*     */     }
/*     */     else {
/*     */       
/* 480 */       GL20.glUniform1(location, values);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public static void glUniform1i(int location, int v0) {
/* 486 */     if (arbShaders) {
/*     */       
/* 488 */       ARBShaderObjects.glUniform1iARB(location, v0);
/*     */     }
/*     */     else {
/*     */       
/* 492 */       GL20.glUniform1i(location, v0);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public static void glUniform1(int location, FloatBuffer values) {
/* 498 */     if (arbShaders) {
/*     */       
/* 500 */       ARBShaderObjects.glUniform1ARB(location, values);
/*     */     }
/*     */     else {
/*     */       
/* 504 */       GL20.glUniform1(location, values);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public static void glUniform2(int location, IntBuffer values) {
/* 510 */     if (arbShaders) {
/*     */       
/* 512 */       ARBShaderObjects.glUniform2ARB(location, values);
/*     */     }
/*     */     else {
/*     */       
/* 516 */       GL20.glUniform2(location, values);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public static void glUniform2(int location, FloatBuffer values) {
/* 522 */     if (arbShaders) {
/*     */       
/* 524 */       ARBShaderObjects.glUniform2ARB(location, values);
/*     */     }
/*     */     else {
/*     */       
/* 528 */       GL20.glUniform2(location, values);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public static void glUniform3(int location, IntBuffer values) {
/* 534 */     if (arbShaders) {
/*     */       
/* 536 */       ARBShaderObjects.glUniform3ARB(location, values);
/*     */     }
/*     */     else {
/*     */       
/* 540 */       GL20.glUniform3(location, values);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public static void glUniform3(int location, FloatBuffer values) {
/* 546 */     if (arbShaders) {
/*     */       
/* 548 */       ARBShaderObjects.glUniform3ARB(location, values);
/*     */     }
/*     */     else {
/*     */       
/* 552 */       GL20.glUniform3(location, values);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public static void glUniform4(int location, IntBuffer values) {
/* 558 */     if (arbShaders) {
/*     */       
/* 560 */       ARBShaderObjects.glUniform4ARB(location, values);
/*     */     }
/*     */     else {
/*     */       
/* 564 */       GL20.glUniform4(location, values);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public static void glUniform4(int location, FloatBuffer values) {
/* 570 */     if (arbShaders) {
/*     */       
/* 572 */       ARBShaderObjects.glUniform4ARB(location, values);
/*     */     }
/*     */     else {
/*     */       
/* 576 */       GL20.glUniform4(location, values);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public static void glUniformMatrix2(int location, boolean transpose, FloatBuffer matrices) {
/* 582 */     if (arbShaders) {
/*     */       
/* 584 */       ARBShaderObjects.glUniformMatrix2ARB(location, transpose, matrices);
/*     */     }
/*     */     else {
/*     */       
/* 588 */       GL20.glUniformMatrix2(location, transpose, matrices);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public static void glUniformMatrix3(int location, boolean transpose, FloatBuffer matrices) {
/* 594 */     if (arbShaders) {
/*     */       
/* 596 */       ARBShaderObjects.glUniformMatrix3ARB(location, transpose, matrices);
/*     */     }
/*     */     else {
/*     */       
/* 600 */       GL20.glUniformMatrix3(location, transpose, matrices);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public static void glUniformMatrix4(int location, boolean transpose, FloatBuffer matrices) {
/* 606 */     if (arbShaders) {
/*     */       
/* 608 */       ARBShaderObjects.glUniformMatrix4ARB(location, transpose, matrices);
/*     */     }
/*     */     else {
/*     */       
/* 612 */       GL20.glUniformMatrix4(location, transpose, matrices);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public static int glGetAttribLocation(int p_153164_0_, CharSequence p_153164_1_) {
/* 618 */     return arbShaders ? ARBVertexShader.glGetAttribLocationARB(p_153164_0_, p_153164_1_) : GL20.glGetAttribLocation(p_153164_0_, p_153164_1_);
/*     */   }
/*     */ 
/*     */   
/*     */   public static int glGenBuffers() {
/* 623 */     return arbVbo ? ARBVertexBufferObject.glGenBuffersARB() : GL15.glGenBuffers();
/*     */   }
/*     */ 
/*     */   
/*     */   public static void glBindBuffer(int target, int buffer) {
/* 628 */     if (arbVbo) {
/*     */       
/* 630 */       ARBVertexBufferObject.glBindBufferARB(target, buffer);
/*     */     }
/*     */     else {
/*     */       
/* 634 */       GL15.glBindBuffer(target, buffer);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public static void glBufferData(int target, ByteBuffer data, int usage) {
/* 640 */     if (arbVbo) {
/*     */       
/* 642 */       ARBVertexBufferObject.glBufferDataARB(target, data, usage);
/*     */     }
/*     */     else {
/*     */       
/* 646 */       GL15.glBufferData(target, data, usage);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public static void glDeleteBuffers(int buffer) {
/* 652 */     if (arbVbo) {
/*     */       
/* 654 */       ARBVertexBufferObject.glDeleteBuffersARB(buffer);
/*     */     }
/*     */     else {
/*     */       
/* 658 */       GL15.glDeleteBuffers(buffer);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public static boolean useVbo() {
/* 664 */     return Config.isMultiTexture() ? false : ((Config.isRenderRegions() && !vboRegions) ? false : ((vboSupported && (Minecraft.getMinecraft()).gameSettings.useVbo)));
/*     */   }
/*     */ 
/*     */   
/*     */   public static void glBindFramebuffer(int target, int framebufferIn) {
/* 669 */     if (framebufferSupported)
/*     */     {
/* 671 */       switch (framebufferType) {
/*     */         
/*     */         case 0:
/* 674 */           GL30.glBindFramebuffer(target, framebufferIn);
/*     */           break;
/*     */         
/*     */         case 1:
/* 678 */           ARBFramebufferObject.glBindFramebuffer(target, framebufferIn);
/*     */           break;
/*     */         
/*     */         case 2:
/* 682 */           EXTFramebufferObject.glBindFramebufferEXT(target, framebufferIn);
/*     */           break;
/*     */       } 
/*     */     }
/*     */   }
/*     */   
/*     */   public static void glBindRenderbuffer(int target, int renderbuffer) {
/* 689 */     if (framebufferSupported)
/*     */     {
/* 691 */       switch (framebufferType) {
/*     */         
/*     */         case 0:
/* 694 */           GL30.glBindRenderbuffer(target, renderbuffer);
/*     */           break;
/*     */         
/*     */         case 1:
/* 698 */           ARBFramebufferObject.glBindRenderbuffer(target, renderbuffer);
/*     */           break;
/*     */         
/*     */         case 2:
/* 702 */           EXTFramebufferObject.glBindRenderbufferEXT(target, renderbuffer);
/*     */           break;
/*     */       } 
/*     */     }
/*     */   }
/*     */   
/*     */   public static void glDeleteRenderbuffers(int renderbuffer) {
/* 709 */     if (framebufferSupported)
/*     */     {
/* 711 */       switch (framebufferType) {
/*     */         
/*     */         case 0:
/* 714 */           GL30.glDeleteRenderbuffers(renderbuffer);
/*     */           break;
/*     */         
/*     */         case 1:
/* 718 */           ARBFramebufferObject.glDeleteRenderbuffers(renderbuffer);
/*     */           break;
/*     */         
/*     */         case 2:
/* 722 */           EXTFramebufferObject.glDeleteRenderbuffersEXT(renderbuffer);
/*     */           break;
/*     */       } 
/*     */     }
/*     */   }
/*     */   
/*     */   public static void glDeleteFramebuffers(int framebufferIn) {
/* 729 */     if (framebufferSupported)
/*     */     {
/* 731 */       switch (framebufferType) {
/*     */         
/*     */         case 0:
/* 734 */           GL30.glDeleteFramebuffers(framebufferIn);
/*     */           break;
/*     */         
/*     */         case 1:
/* 738 */           ARBFramebufferObject.glDeleteFramebuffers(framebufferIn);
/*     */           break;
/*     */         
/*     */         case 2:
/* 742 */           EXTFramebufferObject.glDeleteFramebuffersEXT(framebufferIn);
/*     */           break;
/*     */       } 
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static int glGenFramebuffers() {
/* 752 */     if (!framebufferSupported)
/*     */     {
/* 754 */       return -1;
/*     */     }
/*     */ 
/*     */     
/* 758 */     switch (framebufferType) {
/*     */       
/*     */       case 0:
/* 761 */         return GL30.glGenFramebuffers();
/*     */       
/*     */       case 1:
/* 764 */         return ARBFramebufferObject.glGenFramebuffers();
/*     */       
/*     */       case 2:
/* 767 */         return EXTFramebufferObject.glGenFramebuffersEXT();
/*     */     } 
/*     */     
/* 770 */     return -1;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static int glGenRenderbuffers() {
/* 777 */     if (!framebufferSupported)
/*     */     {
/* 779 */       return -1;
/*     */     }
/*     */ 
/*     */     
/* 783 */     switch (framebufferType) {
/*     */       
/*     */       case 0:
/* 786 */         return GL30.glGenRenderbuffers();
/*     */       
/*     */       case 1:
/* 789 */         return ARBFramebufferObject.glGenRenderbuffers();
/*     */       
/*     */       case 2:
/* 792 */         return EXTFramebufferObject.glGenRenderbuffersEXT();
/*     */     } 
/*     */     
/* 795 */     return -1;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void glRenderbufferStorage(int target, int internalFormat, int width, int height) {
/* 802 */     if (framebufferSupported)
/*     */     {
/* 804 */       switch (framebufferType) {
/*     */         
/*     */         case 0:
/* 807 */           GL30.glRenderbufferStorage(target, internalFormat, width, height);
/*     */           break;
/*     */         
/*     */         case 1:
/* 811 */           ARBFramebufferObject.glRenderbufferStorage(target, internalFormat, width, height);
/*     */           break;
/*     */         
/*     */         case 2:
/* 815 */           EXTFramebufferObject.glRenderbufferStorageEXT(target, internalFormat, width, height);
/*     */           break;
/*     */       } 
/*     */     }
/*     */   }
/*     */   
/*     */   public static void glFramebufferRenderbuffer(int target, int attachment, int renderBufferTarget, int renderBuffer) {
/* 822 */     if (framebufferSupported)
/*     */     {
/* 824 */       switch (framebufferType) {
/*     */         
/*     */         case 0:
/* 827 */           GL30.glFramebufferRenderbuffer(target, attachment, renderBufferTarget, renderBuffer);
/*     */           break;
/*     */         
/*     */         case 1:
/* 831 */           ARBFramebufferObject.glFramebufferRenderbuffer(target, attachment, renderBufferTarget, renderBuffer);
/*     */           break;
/*     */         
/*     */         case 2:
/* 835 */           EXTFramebufferObject.glFramebufferRenderbufferEXT(target, attachment, renderBufferTarget, renderBuffer);
/*     */           break;
/*     */       } 
/*     */     }
/*     */   }
/*     */   
/*     */   public static int glCheckFramebufferStatus(int target) {
/* 842 */     if (!framebufferSupported)
/*     */     {
/* 844 */       return -1;
/*     */     }
/*     */ 
/*     */     
/* 848 */     switch (framebufferType) {
/*     */       
/*     */       case 0:
/* 851 */         return GL30.glCheckFramebufferStatus(target);
/*     */       
/*     */       case 1:
/* 854 */         return ARBFramebufferObject.glCheckFramebufferStatus(target);
/*     */       
/*     */       case 2:
/* 857 */         return EXTFramebufferObject.glCheckFramebufferStatusEXT(target);
/*     */     } 
/*     */     
/* 860 */     return -1;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void glFramebufferTexture2D(int target, int attachment, int textarget, int texture, int level) {
/* 867 */     if (framebufferSupported)
/*     */     {
/* 869 */       switch (framebufferType) {
/*     */         
/*     */         case 0:
/* 872 */           GL30.glFramebufferTexture2D(target, attachment, textarget, texture, level);
/*     */           break;
/*     */         
/*     */         case 1:
/* 876 */           ARBFramebufferObject.glFramebufferTexture2D(target, attachment, textarget, texture, level);
/*     */           break;
/*     */         
/*     */         case 2:
/* 880 */           EXTFramebufferObject.glFramebufferTexture2DEXT(target, attachment, textarget, texture, level);
/*     */           break;
/*     */       } 
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void setActiveTexture(int texture) {
/* 890 */     if (arbMultitexture) {
/*     */       
/* 892 */       ARBMultitexture.glActiveTextureARB(texture);
/*     */     }
/*     */     else {
/*     */       
/* 896 */       GL13.glActiveTexture(texture);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void setClientActiveTexture(int texture) {
/* 905 */     if (arbMultitexture) {
/*     */       
/* 907 */       ARBMultitexture.glClientActiveTextureARB(texture);
/*     */     }
/*     */     else {
/*     */       
/* 911 */       GL13.glClientActiveTexture(texture);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void setLightmapTextureCoords(int target, float p_77475_1_, float p_77475_2_) {
/* 920 */     if (arbMultitexture) {
/*     */       
/* 922 */       ARBMultitexture.glMultiTexCoord2fARB(target, p_77475_1_, p_77475_2_);
/*     */     }
/*     */     else {
/*     */       
/* 926 */       GL13.glMultiTexCoord2f(target, p_77475_1_, p_77475_2_);
/*     */     } 
/*     */     
/* 929 */     if (target == lightmapTexUnit) {
/*     */       
/* 931 */       lastBrightnessX = p_77475_1_;
/* 932 */       lastBrightnessY = p_77475_2_;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public static void glBlendFunc(int sFactorRGB, int dFactorRGB, int sfactorAlpha, int dfactorAlpha) {
/* 938 */     if (openGL14) {
/*     */       
/* 940 */       if (extBlendFuncSeparate)
/*     */       {
/* 942 */         EXTBlendFuncSeparate.glBlendFuncSeparateEXT(sFactorRGB, dFactorRGB, sfactorAlpha, dfactorAlpha);
/*     */       }
/*     */       else
/*     */       {
/* 946 */         GL14.glBlendFuncSeparate(sFactorRGB, dFactorRGB, sfactorAlpha, dfactorAlpha);
/*     */       }
/*     */     
/*     */     } else {
/*     */       
/* 951 */       GL11.glBlendFunc(sFactorRGB, dFactorRGB);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public static boolean isFramebufferEnabled() {
/* 957 */     return Config.isFastRender() ? false : (Config.isAntialiasing() ? false : ((framebufferSupported && (Minecraft.getMinecraft()).gameSettings.fboEnable)));
/*     */   }
/*     */ 
/*     */   
/*     */   public static void glBufferData(int p_glBufferData_0_, long p_glBufferData_1_, int p_glBufferData_3_) {
/* 962 */     if (arbVbo) {
/*     */       
/* 964 */       ARBVertexBufferObject.glBufferDataARB(p_glBufferData_0_, p_glBufferData_1_, p_glBufferData_3_);
/*     */     }
/*     */     else {
/*     */       
/* 968 */       GL15.glBufferData(p_glBufferData_0_, p_glBufferData_1_, p_glBufferData_3_);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public static void glBufferSubData(int p_glBufferSubData_0_, long p_glBufferSubData_1_, ByteBuffer p_glBufferSubData_3_) {
/* 974 */     if (arbVbo) {
/*     */       
/* 976 */       ARBVertexBufferObject.glBufferSubDataARB(p_glBufferSubData_0_, p_glBufferSubData_1_, p_glBufferSubData_3_);
/*     */     }
/*     */     else {
/*     */       
/* 980 */       GL15.glBufferSubData(p_glBufferSubData_0_, p_glBufferSubData_1_, p_glBufferSubData_3_);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public static void glCopyBufferSubData(int p_glCopyBufferSubData_0_, int p_glCopyBufferSubData_1_, long p_glCopyBufferSubData_2_, long p_glCopyBufferSubData_4_, long p_glCopyBufferSubData_6_) {
/* 986 */     if (openGL31) {
/*     */       
/* 988 */       GL31.glCopyBufferSubData(p_glCopyBufferSubData_0_, p_glCopyBufferSubData_1_, p_glCopyBufferSubData_2_, p_glCopyBufferSubData_4_, p_glCopyBufferSubData_6_);
/*     */     }
/*     */     else {
/*     */       
/* 992 */       ARBCopyBuffer.glCopyBufferSubData(p_glCopyBufferSubData_0_, p_glCopyBufferSubData_1_, p_glCopyBufferSubData_2_, p_glCopyBufferSubData_4_, p_glCopyBufferSubData_6_);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public static String getCpu() {
/* 998 */     return (cpu == null) ? "<unknown>" : cpu;
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\minecraft\client\renderer\OpenGlHelper.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */