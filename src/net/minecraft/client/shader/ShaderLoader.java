/*     */ package net.minecraft.client.shader;
/*     */ 
/*     */ import com.google.common.collect.Maps;
/*     */ import java.io.BufferedInputStream;
/*     */ import java.io.IOException;
/*     */ import java.nio.ByteBuffer;
/*     */ import java.util.Map;
/*     */ import net.minecraft.client.renderer.OpenGlHelper;
/*     */ import net.minecraft.client.resources.IResourceManager;
/*     */ import net.minecraft.client.util.JsonException;
/*     */ import net.minecraft.util.ResourceLocation;
/*     */ import org.apache.commons.io.IOUtils;
/*     */ import org.apache.commons.lang3.StringUtils;
/*     */ import org.lwjgl.BufferUtils;
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ShaderLoader
/*     */ {
/*     */   private final ShaderType shaderType;
/*     */   private final String shaderFilename;
/*     */   private final int shader;
/*  23 */   private int shaderAttachCount = 0;
/*     */ 
/*     */   
/*     */   private ShaderLoader(ShaderType type, int shaderId, String filename) {
/*  27 */     this.shaderType = type;
/*  28 */     this.shader = shaderId;
/*  29 */     this.shaderFilename = filename;
/*     */   }
/*     */ 
/*     */   
/*     */   public void attachShader(ShaderManager manager) {
/*  34 */     this.shaderAttachCount++;
/*  35 */     OpenGlHelper.glAttachShader(manager.getProgram(), this.shader);
/*     */   }
/*     */ 
/*     */   
/*     */   public void deleteShader(ShaderManager manager) {
/*  40 */     this.shaderAttachCount--;
/*     */     
/*  42 */     if (this.shaderAttachCount <= 0) {
/*     */       
/*  44 */       OpenGlHelper.glDeleteShader(this.shader);
/*  45 */       this.shaderType.getLoadedShaders().remove(this.shaderFilename);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public String getShaderFilename() {
/*  51 */     return this.shaderFilename;
/*     */   }
/*     */ 
/*     */   
/*     */   public static ShaderLoader loadShader(IResourceManager resourceManager, ShaderType type, String filename) throws IOException {
/*  56 */     ShaderLoader shaderloader = type.getLoadedShaders().get(filename);
/*     */     
/*  58 */     if (shaderloader == null) {
/*     */       
/*  60 */       ResourceLocation resourcelocation = new ResourceLocation("shaders/program/" + filename + type.getShaderExtension());
/*  61 */       BufferedInputStream bufferedinputstream = new BufferedInputStream(resourceManager.getResource(resourcelocation).getInputStream());
/*  62 */       byte[] abyte = toByteArray(bufferedinputstream);
/*  63 */       ByteBuffer bytebuffer = BufferUtils.createByteBuffer(abyte.length);
/*  64 */       bytebuffer.put(abyte);
/*  65 */       bytebuffer.position(0);
/*  66 */       int i = OpenGlHelper.glCreateShader(type.getShaderMode());
/*  67 */       OpenGlHelper.glShaderSource(i, bytebuffer);
/*  68 */       OpenGlHelper.glCompileShader(i);
/*     */       
/*  70 */       if (OpenGlHelper.glGetShaderi(i, OpenGlHelper.GL_COMPILE_STATUS) == 0) {
/*     */         
/*  72 */         String s = StringUtils.trim(OpenGlHelper.glGetShaderInfoLog(i, 32768));
/*  73 */         JsonException jsonexception = new JsonException("Couldn't compile " + type.getShaderName() + " program: " + s);
/*  74 */         jsonexception.func_151381_b(resourcelocation.getResourcePath());
/*  75 */         throw jsonexception;
/*     */       } 
/*     */       
/*  78 */       shaderloader = new ShaderLoader(type, i, filename);
/*  79 */       type.getLoadedShaders().put(filename, shaderloader);
/*     */     } 
/*     */     
/*  82 */     return shaderloader;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected static byte[] toByteArray(BufferedInputStream p_177064_0_) throws IOException {
/*     */     byte[] abyte;
/*     */     try {
/*  91 */       abyte = IOUtils.toByteArray(p_177064_0_);
/*     */     }
/*     */     finally {
/*     */       
/*  95 */       p_177064_0_.close();
/*     */     } 
/*     */     
/*  98 */     return abyte;
/*     */   }
/*     */   
/*     */   public enum ShaderType
/*     */   {
/* 103 */     VERTEX("vertex", ".vsh", OpenGlHelper.GL_VERTEX_SHADER),
/* 104 */     FRAGMENT("fragment", ".fsh", OpenGlHelper.GL_FRAGMENT_SHADER);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 109 */     private final Map<String, ShaderLoader> loadedShaders = Maps.newHashMap();
/*     */     private final String shaderName;
/*     */     
/*     */     ShaderType(String p_i45090_3_, String p_i45090_4_, int p_i45090_5_) {
/* 113 */       this.shaderName = p_i45090_3_;
/* 114 */       this.shaderExtension = p_i45090_4_;
/* 115 */       this.shaderMode = p_i45090_5_;
/*     */     }
/*     */     private final String shaderExtension; private final int shaderMode;
/*     */     
/*     */     public String getShaderName() {
/* 120 */       return this.shaderName;
/*     */     }
/*     */ 
/*     */     
/*     */     protected String getShaderExtension() {
/* 125 */       return this.shaderExtension;
/*     */     }
/*     */ 
/*     */     
/*     */     protected int getShaderMode() {
/* 130 */       return this.shaderMode;
/*     */     }
/*     */ 
/*     */     
/*     */     protected Map<String, ShaderLoader> getLoadedShaders() {
/* 135 */       return this.loadedShaders;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\minecraft\client\shader\ShaderLoader.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */