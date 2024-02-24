/*    */ package awareline.main.utility.render.gl;
/*    */ 
/*    */ import awareline.main.utility.vec.Vec3f;
/*    */ import java.nio.FloatBuffer;
/*    */ import java.nio.IntBuffer;
/*    */ import java.util.HashMap;
/*    */ import java.util.Iterator;
/*    */ import java.util.Map;
/*    */ import net.minecraft.client.renderer.GlStateManager;
/*    */ import org.lwjgl.BufferUtils;
/*    */ import org.lwjgl.opengl.Display;
/*    */ import org.lwjgl.opengl.GL11;
/*    */ import org.lwjgl.util.glu.GLU;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public final class GLUtils
/*    */ {
/*    */   public static float[] getColor(int hex) {
/* 22 */     return new float[] { (hex >> 16 & 0xFF) / 255.0F, (hex >> 8 & 0xFF) / 255.0F, (hex & 0xFF) / 255.0F, (hex >> 24 & 0xFF) / 255.0F };
/*    */   }
/*    */   
/*    */   public static void glColor(int hex) {
/* 26 */     float[] color = getColor(hex);
/* 27 */     GlStateManager.color(color[0], color[1], color[2], color[3]);
/*    */   }
/*    */   
/* 30 */   private static final Map<Integer, Boolean> glCapMap = new HashMap<>();
/*    */   
/*    */   public static void setGLCap(int cap, boolean flag) {
/* 33 */     glCapMap.put(Integer.valueOf(cap), Boolean.valueOf(GL11.glGetBoolean(cap)));
/* 34 */     if (flag) {
/* 35 */       GL11.glEnable(cap);
/*    */     } else {
/* 37 */       GL11.glDisable(cap);
/*    */     } 
/*    */   }
/*    */   
/*    */   public static void revertGLCap(int cap) {
/* 42 */     Boolean origCap = glCapMap.get(Integer.valueOf(cap));
/* 43 */     if (origCap != null) {
/* 44 */       if (origCap.booleanValue()) {
/* 45 */         GL11.glEnable(cap);
/*    */       } else {
/* 47 */         GL11.glDisable(cap);
/*    */       } 
/*    */     }
/*    */   }
/*    */   
/*    */   public static void glEnable(int cap) {
/* 53 */     setGLCap(cap, true);
/*    */   }
/*    */   
/*    */   public static void glDisable(int cap) {
/* 57 */     setGLCap(cap, false);
/*    */   }
/*    */   
/*    */   public static void revertAllCaps() {
/* 61 */     for (Iterator<Integer> iterator = glCapMap.keySet().iterator(); iterator.hasNext(); ) { int cap = ((Integer)iterator.next()).intValue();
/* 62 */       revertGLCap(cap); }
/*    */   
/*    */   }
/*    */   
/*    */   public static Vec3f toWorld(Vec3f pos) {
/* 67 */     return toWorld(pos.getX(), pos.getY(), pos.getZ());
/*    */   }
/*    */   
/* 70 */   public static final FloatBuffer MODELVIEW = BufferUtils.createFloatBuffer(16);
/* 71 */   public static final FloatBuffer PROJECTION = BufferUtils.createFloatBuffer(16);
/* 72 */   public static final IntBuffer VIEWPORT = BufferUtils.createIntBuffer(16);
/* 73 */   public static final FloatBuffer TO_SCREEN_BUFFER = BufferUtils.createFloatBuffer(3);
/* 74 */   public static final FloatBuffer TO_WORLD_BUFFER = BufferUtils.createFloatBuffer(3);
/*    */   
/*    */   public static Vec3f toWorld(double x, double y, double z) {
/* 77 */     boolean result = GLU.gluUnProject((float)x, (float)y, (float)z, MODELVIEW, PROJECTION, VIEWPORT, (FloatBuffer)TO_WORLD_BUFFER.clear());
/* 78 */     if (result) {
/* 79 */       return new Vec3f(TO_WORLD_BUFFER.get(0), TO_WORLD_BUFFER.get(1), TO_WORLD_BUFFER.get(2));
/*    */     }
/* 81 */     return null;
/*    */   }
/*    */   
/*    */   public static Vec3f toScreen(Vec3f pos) {
/* 85 */     return toScreen(pos.getX(), pos.getY(), pos.getZ());
/*    */   }
/*    */   
/*    */   public static Vec3f toScreen(double x, double y, double z) {
/* 89 */     boolean result = GLU.gluProject((float)x, (float)y, (float)z, MODELVIEW, PROJECTION, VIEWPORT, (FloatBuffer)TO_SCREEN_BUFFER.clear());
/* 90 */     if (result) {
/* 91 */       return new Vec3f(TO_SCREEN_BUFFER.get(0), (Display.getHeight() - TO_SCREEN_BUFFER.get(1)), TO_SCREEN_BUFFER.get(2));
/*    */     }
/* 93 */     return null;
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\awareline\mai\\utility\render\gl\GLUtils.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */