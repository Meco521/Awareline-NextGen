/*    */ package awareline.main.utility.render;
/*    */ 
/*    */ import awareline.main.utility.Utils;
/*    */ import awareline.main.utility.math.MathUtil;
/*    */ import java.nio.FloatBuffer;
/*    */ import java.nio.IntBuffer;
/*    */ import java.util.Arrays;
/*    */ import java.util.List;
/*    */ import net.minecraft.client.gui.ScaledResolution;
/*    */ import net.minecraft.client.renderer.GLAllocation;
/*    */ import net.minecraft.client.renderer.culling.Frustum;
/*    */ import net.minecraft.entity.Entity;
/*    */ import net.minecraft.util.AxisAlignedBB;
/*    */ import org.lwjgl.BufferUtils;
/*    */ import org.lwjgl.opengl.GL11;
/*    */ import org.lwjgl.util.glu.GLU;
/*    */ import org.lwjgl.util.vector.Vector3f;
/*    */ import org.lwjgl.util.vector.Vector4f;
/*    */ 
/*    */ public class ESPUtil
/*    */   implements Utils
/*    */ {
/* 23 */   private static final Frustum frustum = new Frustum();
/* 24 */   private static final FloatBuffer windPos = BufferUtils.createFloatBuffer(4);
/* 25 */   private static final IntBuffer intBuffer = GLAllocation.createDirectIntBuffer(16);
/* 26 */   private static final FloatBuffer floatBuffer1 = GLAllocation.createDirectFloatBuffer(16);
/* 27 */   private static final FloatBuffer floatBuffer2 = GLAllocation.createDirectFloatBuffer(16);
/*    */   
/*    */   public static boolean isInView(Entity ent) {
/* 30 */     frustum.setPosition((mc.getRenderViewEntity()).posX, (mc.getRenderViewEntity()).posY, (mc.getRenderViewEntity()).posZ);
/* 31 */     return (frustum.isBoundingBoxInFrustum(ent.getEntityBoundingBox()) || ent.ignoreFrustumCheck);
/*    */   }
/*    */   
/*    */   public static Vector3f projectOn2D(float x, float y, float z, int scaleFactor) {
/* 35 */     GL11.glGetFloat(2982, floatBuffer1);
/* 36 */     GL11.glGetFloat(2983, floatBuffer2);
/* 37 */     GL11.glGetInteger(2978, intBuffer);
/* 38 */     if (GLU.gluProject(x, y, z, floatBuffer1, floatBuffer2, intBuffer, windPos)) {
/* 39 */       return new Vector3f(windPos.get(0) / scaleFactor, (mc.displayHeight - windPos.get(1)) / scaleFactor, windPos.get(2));
/*    */     }
/* 41 */     return null;
/*    */   }
/*    */   
/*    */   public static double[] getInterpolatedPos(Entity entity) {
/* 45 */     float ticks = mc.timer.renderPartialTicks;
/* 46 */     return new double[] {
/* 47 */         MathUtil.interpolate(entity.lastTickPosX, entity.posX, ticks).doubleValue() - (mc.getRenderManager()).viewerPosX, 
/* 48 */         MathUtil.interpolate(entity.lastTickPosY, entity.posY, ticks).doubleValue() - (mc.getRenderManager()).viewerPosY, 
/* 49 */         MathUtil.interpolate(entity.lastTickPosZ, entity.posZ, ticks).doubleValue() - (mc.getRenderManager()).viewerPosZ
/*    */       };
/*    */   }
/*    */   
/*    */   public static AxisAlignedBB getInterpolatedBoundingBox(Entity entity) {
/* 54 */     double[] renderingEntityPos = getInterpolatedPos(entity);
/* 55 */     double entityRenderWidth = entity.width / 1.5D;
/* 56 */     return (new AxisAlignedBB(renderingEntityPos[0] - entityRenderWidth, renderingEntityPos[1], renderingEntityPos[2] - entityRenderWidth, renderingEntityPos[0] + entityRenderWidth, renderingEntityPos[1] + entity.height + (
/*    */         
/* 58 */         entity.isSneaking() ? -0.3D : 0.18D), renderingEntityPos[2] + entityRenderWidth)).expand(0.15D, 0.15D, 0.15D);
/*    */   }
/*    */   
/*    */   public static Vector4f getEntityPositionsOn2D(Entity entity) {
/* 62 */     AxisAlignedBB bb = getInterpolatedBoundingBox(entity);
/*    */     
/* 64 */     float yOffset = 0.0F;
/*    */ 
/*    */     
/* 67 */     List<Vector3f> vectors = Arrays.asList(new Vector3f[] { new Vector3f((float)bb.minX, (float)bb.minY, (float)bb.minZ), new Vector3f((float)bb.minX, (float)bb.maxY - yOffset, (float)bb.minZ), new Vector3f((float)bb.maxX, (float)bb.minY, (float)bb.minZ), new Vector3f((float)bb.maxX, (float)bb.maxY - yOffset, (float)bb.minZ), new Vector3f((float)bb.minX, (float)bb.minY, (float)bb.maxZ), new Vector3f((float)bb.minX, (float)bb.maxY - yOffset, (float)bb.maxZ), new Vector3f((float)bb.maxX, (float)bb.minY, (float)bb.maxZ), new Vector3f((float)bb.maxX, (float)bb.maxY - yOffset, (float)bb.maxZ) });
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */     
/* 77 */     Vector4f entityPos = new Vector4f(Float.MAX_VALUE, Float.MAX_VALUE, -1.0F, -1.0F);
/* 78 */     ScaledResolution sr = new ScaledResolution(mc);
/* 79 */     for (Vector3f vector3f : vectors) {
/* 80 */       vector3f = projectOn2D(vector3f.x, vector3f.y, vector3f.z, sr.getScaleFactor());
/* 81 */       if (vector3f != null && vector3f.z >= 0.0D && vector3f.z < 1.0D) {
/* 82 */         entityPos.x = Math.min(vector3f.x, entityPos.x);
/* 83 */         entityPos.y = Math.min(vector3f.y, entityPos.y);
/* 84 */         entityPos.z = Math.max(vector3f.x, entityPos.z);
/* 85 */         entityPos.w = Math.max(vector3f.y, entityPos.w);
/*    */       } 
/*    */     } 
/* 88 */     return entityPos;
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\awareline\mai\\utility\render\ESPUtil.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */