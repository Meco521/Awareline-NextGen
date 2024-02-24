/*     */ package awareline.main.mod.implement.world.utils;
/*     */ 
/*     */ import awareline.main.InstanceAccess;
/*     */ import com.google.common.base.Predicates;
/*     */ import java.util.List;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.util.AxisAlignedBB;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.util.EntitySelectors;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ import net.minecraft.util.MovingObjectPosition;
/*     */ import net.minecraft.util.Vec3;
/*     */ 
/*     */ public class RayCastUtil
/*     */   implements InstanceAccess
/*     */ {
/*     */   public static MovingObjectPosition rayCast(Vector2f rotation, double range) {
/*  18 */     return rayCast(rotation, range, 0.0F);
/*     */   }
/*     */   
/*     */   private static Vec3 getVectorForRotation(float pitch, float yaw) {
/*  22 */     double f = Math.cos(Math.toRadians(-yaw) - 3.1415927410125732D);
/*  23 */     double f1 = Math.sin(Math.toRadians(-yaw) - 3.1415927410125732D);
/*  24 */     double f2 = -Math.cos(Math.toRadians(-pitch));
/*  25 */     double f3 = Math.sin(Math.toRadians(-pitch));
/*  26 */     return new Vec3(f1 * f2, f3, f * f2);
/*     */   }
/*     */   
/*     */   public static MovingObjectPosition rayCast(Vector2f rotation, double range, float expand) {
/*  30 */     float partialTicks = mc.timer.renderPartialTicks;
/*  31 */     Entity entity = mc.getRenderViewEntity();
/*     */ 
/*     */     
/*  34 */     if (entity != null && mc.theWorld != null) {
/*  35 */       MovingObjectPosition objectMouseOver = entity.rayTraceCustom(range, rotation.x, rotation.y);
/*  36 */       double d1 = range;
/*  37 */       Vec3 vec3 = entity.getPositionEyes(partialTicks);
/*     */       
/*  39 */       if (objectMouseOver != null) {
/*  40 */         d1 = objectMouseOver.hitVec.distanceTo(vec3);
/*     */       }
/*     */       
/*  43 */       Vec3 vec31 = getVectorForRotation(rotation.y, rotation.x);
/*  44 */       Vec3 vec32 = vec3.addVector(vec31.xCoord * range, vec31.yCoord * range, vec31.zCoord * range);
/*  45 */       Entity pointedEntity = null;
/*  46 */       Vec3 vec33 = null;
/*  47 */       float f = 1.0F;
/*  48 */       List<Entity> list = mc.theWorld.getEntitiesInAABBexcluding(entity, entity.getEntityBoundingBox().addCoord(vec31.xCoord * range, vec31.yCoord * range, vec31.zCoord * range).expand(1.0D, 1.0D, 1.0D), Predicates.and(EntitySelectors.NOT_SPECTATING, Entity::canBeCollidedWith));
/*  49 */       double d2 = d1;
/*     */       
/*  51 */       for (Entity entity1 : list) {
/*  52 */         float f1 = entity1.getCollisionBorderSize() + expand;
/*  53 */         AxisAlignedBB axisalignedbb = entity1.getEntityBoundingBox().expand(f1, f1, f1);
/*  54 */         MovingObjectPosition movingobjectposition = axisalignedbb.calculateIntercept(vec3, vec32);
/*     */         
/*  56 */         if (axisalignedbb.isVecInside(vec3)) {
/*  57 */           if (d2 >= 0.0D) {
/*  58 */             pointedEntity = entity1;
/*  59 */             vec33 = (movingobjectposition == null) ? vec3 : movingobjectposition.hitVec;
/*  60 */             d2 = 0.0D;
/*     */           }  continue;
/*  62 */         }  if (movingobjectposition != null) {
/*  63 */           double d3 = vec3.distanceTo(movingobjectposition.hitVec);
/*     */           
/*  65 */           if (d3 < d2 || d2 == 0.0D) {
/*  66 */             pointedEntity = entity1;
/*  67 */             vec33 = movingobjectposition.hitVec;
/*  68 */             d2 = d3;
/*     */           } 
/*     */         } 
/*     */       } 
/*     */       
/*  73 */       if (pointedEntity != null && (d2 < d1 || objectMouseOver == null)) {
/*  74 */         objectMouseOver = new MovingObjectPosition(pointedEntity, vec33);
/*     */       }
/*     */       
/*  77 */       return objectMouseOver;
/*     */     } 
/*     */     
/*  80 */     return null;
/*     */   }
/*     */   
/*     */   public static boolean overBlock(Vector2f rotation, EnumFacing enumFacing, BlockPos pos, boolean strict) {
/*     */     try {
/*  85 */       MovingObjectPosition movingObjectPosition = mc.thePlayer.rayTraceCustom(4.5D, mc.timer.renderPartialTicks, rotation.x, rotation.y);
/*     */       
/*  87 */       if (movingObjectPosition == null) return false;
/*     */       
/*  89 */       Vec3 hitVec = movingObjectPosition.hitVec;
/*  90 */       if (hitVec == null) return false;
/*     */       
/*  92 */       return (movingObjectPosition.getBlockPos().equals(pos) && (!strict || movingObjectPosition.sideHit == enumFacing));
/*  93 */     } catch (RuntimeException e) {
/*  94 */       e.printStackTrace();
/*     */       
/*  96 */       return false;
/*     */     } 
/*     */   }
/*     */   public static boolean overBlock(EnumFacing enumFacing, BlockPos pos, boolean strict) {
/* 100 */     MovingObjectPosition movingObjectPosition = mc.objectMouseOver;
/*     */     
/* 102 */     if (movingObjectPosition == null) return false;
/*     */     
/* 104 */     Vec3 hitVec = movingObjectPosition.hitVec;
/* 105 */     if (hitVec == null) return false;
/*     */     
/* 107 */     return (movingObjectPosition.getBlockPos().equals(pos) && (!strict || movingObjectPosition.sideHit == enumFacing));
/*     */   }
/*     */   
/*     */   public static Boolean overBlock(Vector2f rotation, BlockPos pos) {
/* 111 */     return Boolean.valueOf(overBlock(rotation, EnumFacing.UP, pos, false));
/*     */   }
/*     */   
/*     */   public static Boolean overBlock(Vector2f rotation, BlockPos pos, EnumFacing enumFacing) {
/* 115 */     return Boolean.valueOf(overBlock(rotation, enumFacing, pos, true));
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\awareline\main\mod\implement\worl\\utils\RayCastUtil.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */