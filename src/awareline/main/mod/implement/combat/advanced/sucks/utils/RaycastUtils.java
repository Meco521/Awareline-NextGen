/*    */ package awareline.main.mod.implement.combat.advanced.sucks.utils;
/*    */ 
/*    */ import awareline.main.mod.implement.combat.advanced.sucks.utils.liquidbounce.RotationUtils;
/*    */ import com.google.common.base.Predicates;
/*    */ import java.util.List;
/*    */ import net.minecraft.client.Minecraft;
/*    */ import net.minecraft.entity.Entity;
/*    */ import net.minecraft.util.AxisAlignedBB;
/*    */ import net.minecraft.util.EntitySelectors;
/*    */ import net.minecraft.util.MathHelper;
/*    */ import net.minecraft.util.MovingObjectPosition;
/*    */ import net.minecraft.util.Vec3;
/*    */ 
/*    */ public final class RaycastUtils
/*    */ {
/* 16 */   private static final Minecraft mc = Minecraft.getMinecraft();
/*    */   
/*    */   public static Entity raycastEntity(double range, IEntityFilter entityFilter) {
/* 19 */     return raycastEntity(range, RotationUtils.serverRotation.getYaw(), RotationUtils.serverRotation.getPitch(), entityFilter);
/*    */   }
/*    */ 
/*    */   
/*    */   public static Entity raycastEntity(double range, float yaw, float pitch, IEntityFilter entityFilter) {
/* 24 */     Entity renderViewEntity = mc.getRenderViewEntity();
/*    */     
/* 26 */     if (renderViewEntity != null && mc.theWorld != null) {
/* 27 */       double blockReachDistance = range;
/* 28 */       Vec3 eyePosition = renderViewEntity.getPositionEyes(1.0F);
/*    */       
/* 30 */       float yawCos = MathHelper.cos(-yaw * 0.017453292F - 3.1415927F);
/* 31 */       float yawSin = MathHelper.sin(-yaw * 0.017453292F - 3.1415927F);
/* 32 */       float pitchCos = -MathHelper.cos(-pitch * 0.017453292F);
/* 33 */       float pitchSin = MathHelper.sin(-pitch * 0.017453292F);
/*    */       
/* 35 */       Vec3 entityLook = new Vec3((yawSin * pitchCos), pitchSin, (yawCos * pitchCos));
/* 36 */       Vec3 vector = eyePosition.addVector(entityLook.xCoord * blockReachDistance, entityLook.yCoord * blockReachDistance, entityLook.zCoord * blockReachDistance);
/* 37 */       List<Entity> entityList = mc.theWorld.getEntitiesInAABBexcluding(renderViewEntity, renderViewEntity.getEntityBoundingBox().addCoord(entityLook.xCoord * blockReachDistance, entityLook.yCoord * blockReachDistance, entityLook.zCoord * blockReachDistance).expand(1.0D, 1.0D, 1.0D), Predicates.and(EntitySelectors.NOT_SPECTATING, Entity::canBeCollidedWith));
/*    */       
/* 39 */       Entity pointedEntity = null;
/*    */       
/* 41 */       for (Entity entity : entityList) {
/* 42 */         if (!entityFilter.canRaycast(entity)) {
/*    */           continue;
/*    */         }
/* 45 */         float collisionBorderSize = entity.getCollisionBorderSize();
/* 46 */         AxisAlignedBB axisAlignedBB = entity.getEntityBoundingBox().expand(collisionBorderSize, collisionBorderSize, collisionBorderSize);
/* 47 */         MovingObjectPosition movingObjectPosition = axisAlignedBB.calculateIntercept(eyePosition, vector);
/*    */         
/* 49 */         if (axisAlignedBB.isVecInside(eyePosition)) {
/* 50 */           if (blockReachDistance >= 0.0D) {
/* 51 */             pointedEntity = entity;
/* 52 */             blockReachDistance = 0.0D;
/*    */           }  continue;
/* 54 */         }  if (movingObjectPosition != null) {
/* 55 */           double eyeDistance = eyePosition.distanceTo(movingObjectPosition.hitVec);
/*    */           
/* 57 */           if (eyeDistance < blockReachDistance || blockReachDistance == 0.0D) {
/* 58 */             if (entity == renderViewEntity.ridingEntity) {
/* 59 */               if (blockReachDistance == 0.0D)
/* 60 */                 pointedEntity = entity;  continue;
/*    */             } 
/* 62 */             pointedEntity = entity;
/* 63 */             blockReachDistance = eyeDistance;
/*    */           } 
/*    */         } 
/*    */       } 
/*    */ 
/*    */       
/* 69 */       return pointedEntity;
/*    */     } 
/*    */     
/* 72 */     return null;
/*    */   }
/*    */   
/*    */   public static interface IEntityFilter {
/*    */     boolean canRaycast(Entity param1Entity);
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\awareline\main\mod\implement\combat\advanced\suck\\utils\RaycastUtils.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */