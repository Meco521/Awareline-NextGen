/*     */ package awareline.main.ui.gui.guimainmenu.shader;
/*     */ 
/*     */ import java.io.BufferedReader;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.InputStreamReader;
/*     */ import java.util.Objects;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.gui.ScaledResolution;
/*     */ import org.lwjgl.opengl.GL11;
/*     */ import org.lwjgl.opengl.GL20;
/*     */ 
/*     */ 
/*     */ public class ShaderProgram
/*     */ {
/*  16 */   protected static final Minecraft mc = Minecraft.getMinecraft(); private final String vertexName;
/*     */   private final String fragmentName;
/*     */   private final int vertexShaderID;
/*     */   
/*     */   public int getProgramID() {
/*  21 */     return this.programID;
/*     */   }
/*     */   
/*     */   private final int fragmentShaderID;
/*     */   
/*     */   public ShaderProgram(String vertexName, String fragmentName) {
/*  27 */     this.vertexName = vertexName;
/*  28 */     this.fragmentName = fragmentName;
/*     */     
/*  30 */     int program = GL20.glCreateProgram();
/*     */     
/*  32 */     String vertexSource = readShader(vertexName);
/*  33 */     this.vertexShaderID = GL20.glCreateShader(35633);
/*  34 */     GL20.glShaderSource(this.vertexShaderID, vertexSource);
/*  35 */     GL20.glCompileShader(this.vertexShaderID);
/*     */     
/*  37 */     if (GL20.glGetShaderi(this.vertexShaderID, 35713) == 0) {
/*  38 */       System.err.println(GL20.glGetShaderInfoLog(this.vertexShaderID, 4096));
/*  39 */       throw new IllegalStateException(String.format("Vertex Shader (%s) failed to compile!", new Object[] { Integer.valueOf(35633) }));
/*     */     } 
/*     */     
/*  42 */     String fragmentSource = readShader(fragmentName);
/*  43 */     this.fragmentShaderID = GL20.glCreateShader(35632);
/*  44 */     GL20.glShaderSource(this.fragmentShaderID, fragmentSource);
/*  45 */     GL20.glCompileShader(this.fragmentShaderID);
/*     */     
/*  47 */     if (GL20.glGetShaderi(this.fragmentShaderID, 35713) == 0) {
/*  48 */       System.err.println(GL20.glGetShaderInfoLog(this.fragmentShaderID, 4096));
/*  49 */       throw new IllegalStateException(String.format("Fragment Shader (%s) failed to compile!", new Object[] { Integer.valueOf(35632) }));
/*     */     } 
/*     */     
/*  52 */     GL20.glAttachShader(program, this.vertexShaderID);
/*  53 */     GL20.glAttachShader(program, this.fragmentShaderID);
/*  54 */     GL20.glLinkProgram(program);
/*  55 */     this.programID = program;
/*     */   }
/*     */   private final int programID; private boolean initiated;
/*     */   public ShaderProgram(String fragmentName) {
/*  59 */     this("vertex/vertex.vert", fragmentName);
/*     */   }
/*     */   
/*     */   private static String readShader(String fileName) {
/*  63 */     StringBuilder stringBuilder = new StringBuilder();
/*     */     try {
/*  65 */       InputStreamReader inputStreamReader = new InputStreamReader(Objects.<InputStream>requireNonNull(ShaderProgram.class.getClassLoader().getResourceAsStream(
/*  66 */               String.format("assets/minecraft/freeshaders/shaders/%s", new Object[] { fileName }))));
/*  67 */       BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
/*     */       String line;
/*  69 */       while ((line = bufferedReader.readLine()) != null)
/*  70 */         stringBuilder.append(line).append('\n'); 
/*  71 */       bufferedReader.close();
/*  72 */       inputStreamReader.close();
/*  73 */     } catch (IOException e) {
/*  74 */       e.printStackTrace();
/*     */     } 
/*     */     
/*  77 */     return stringBuilder.toString();
/*     */   }
/*     */   
/*     */   public void renderCanvas(ScaledResolution scaledResolution) {
/*  81 */     float width = scaledResolution.getScaledWidth();
/*  82 */     float height = scaledResolution.getScaledHeight();
/*  83 */     renderCanvas(0.0F, 0.0F, width, height);
/*     */   }
/*     */   
/*     */   public void renderCanvas(float x, float y, float width, float height) {
/*  87 */     GL11.glDisable(3008);
/*  88 */     GL11.glEnable(3042);
/*  89 */     GL11.glBegin(7);
/*  90 */     GL11.glTexCoord2f(0.0F, 1.0F);
/*  91 */     GL11.glVertex2f(x, y);
/*  92 */     GL11.glTexCoord2f(0.0F, 0.0F);
/*  93 */     GL11.glVertex2f(x, height);
/*  94 */     GL11.glTexCoord2f(1.0F, 0.0F);
/*  95 */     GL11.glVertex2f(width, height);
/*  96 */     GL11.glTexCoord2f(1.0F, 1.0F);
/*  97 */     GL11.glVertex2f(width, y);
/*  98 */     GL11.glEnd();
/*  99 */     GL11.glDisable(3042);
/* 100 */     GL11.glEnable(3008);
/*     */   }
/*     */   
/*     */   public void deleteShaderProgram() {
/* 104 */     GL20.glDeleteShader(this.vertexShaderID);
/* 105 */     GL20.glDeleteShader(this.fragmentShaderID);
/* 106 */     GL20.glDeleteProgram(this.programID);
/*     */   }
/*     */   
/*     */   public void init() {
/* 110 */     GL20.glUseProgram(this.programID);
/*     */   }
/*     */   
/*     */   public void uninit() {
/* 114 */     GL20.glUseProgram(0);
/*     */   }
/*     */   
/*     */   public int getUniform(String name) {
/* 118 */     return GL20.glGetUniformLocation(this.programID, name);
/*     */   }
/*     */   
/*     */   public void setUniformf(String name, float... args) {
/* 122 */     int loc = GL20.glGetUniformLocation(this.programID, name);
/* 123 */     if (args.length > 1)
/* 124 */     { if (args.length > 2)
/* 125 */       { if (args.length > 3) { GL20.glUniform4f(loc, args[0], args[1], args[2], args[3]); }
/* 126 */         else { GL20.glUniform3f(loc, args[0], args[1], args[2]); }  }
/* 127 */       else { GL20.glUniform2f(loc, args[0], args[1]); }  }
/* 128 */     else { GL20.glUniform1f(loc, args[0]); }
/*     */   
/*     */   }
/*     */   
/*     */   public void setUniformi(String name, int... args) {
/* 133 */     int loc = GL20.glGetUniformLocation(this.programID, name);
/* 134 */     if (args.length > 1) { GL20.glUniform2i(loc, args[0], args[1]); }
/* 135 */     else { GL20.glUniform1i(loc, args[0]); }
/*     */   
/*     */   }
/*     */   
/*     */   public String toString() {
/* 140 */     return "ShaderProgram{programID=" + this.programID + ", vertexName='" + this.vertexName + '\'' + ", fragmentName='" + this.fragmentName + '\'' + '}';
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\awareline\mai\\ui\gui\guimainmenu\shader\ShaderProgram.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */