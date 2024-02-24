/*    */ package net.minecraft.entity.ai;
/*    */ 
/*    */ import com.google.common.collect.Lists;
/*    */ import java.util.List;
/*    */ import net.minecraft.entity.Entity;
/*    */ import net.minecraft.entity.EntityLiving;
/*    */ 
/*    */ 
/*    */ public class EntitySenses
/*    */ {
/*    */   EntityLiving entityObj;
/* 12 */   List<Entity> seenEntities = Lists.newArrayList();
/* 13 */   List<Entity> unseenEntities = Lists.newArrayList();
/*    */ 
/*    */   
/*    */   public EntitySenses(EntityLiving entityObjIn) {
/* 17 */     this.entityObj = entityObjIn;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void clearSensingCache() {
/* 25 */     this.seenEntities.clear();
/* 26 */     this.unseenEntities.clear();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean canSee(Entity entityIn) {
/* 34 */     if (this.seenEntities.contains(entityIn))
/*    */     {
/* 36 */       return true;
/*    */     }
/* 38 */     if (this.unseenEntities.contains(entityIn))
/*    */     {
/* 40 */       return false;
/*    */     }
/*    */ 
/*    */     
/* 44 */     this.entityObj.worldObj.theProfiler.startSection("canSee");
/* 45 */     boolean flag = this.entityObj.canEntityBeSeen(entityIn);
/* 46 */     this.entityObj.worldObj.theProfiler.endSection();
/*    */     
/* 48 */     if (flag) {
/*    */       
/* 50 */       this.seenEntities.add(entityIn);
/*    */     }
/*    */     else {
/*    */       
/* 54 */       this.unseenEntities.add(entityIn);
/*    */     } 
/*    */     
/* 57 */     return flag;
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\minecraft\entity\ai\EntitySenses.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */