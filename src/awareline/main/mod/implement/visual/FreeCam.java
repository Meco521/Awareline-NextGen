/*    */ package awareline.main.mod.implement.visual;
/*    */ 
/*    */ import awareline.main.mod.Module;
/*    */ import awareline.main.mod.ModuleType;
/*    */ import com.mojang.authlib.GameProfile;
/*    */ import java.util.UUID;
/*    */ import net.minecraft.client.entity.EntityOtherPlayerMP;
/*    */ import net.minecraft.entity.Entity;
/*    */ import net.minecraft.world.World;
/*    */ 
/*    */ public class FreeCam
/*    */   extends Module {
/*    */   public FreeCam() {
/* 14 */     super("FreeCam", ModuleType.Render);
/*    */   }
/*    */   private EntityOtherPlayerMP freecamEntity;
/*    */   
/*    */   public void onDisable() {
/* 19 */     mc.thePlayer.setPositionAndRotation(this.freecamEntity.posX, this.freecamEntity.posY, this.freecamEntity.posZ, this.freecamEntity.rotationYaw, this.freecamEntity.rotationPitch);
/* 20 */     mc.theWorld.removeEntityFromWorld(this.freecamEntity.getEntityId());
/* 21 */     mc.renderGlobal.loadRenderers();
/* 22 */     mc.thePlayer.noClip = false;
/* 23 */     super.onDisable();
/*    */   }
/*    */ 
/*    */   
/*    */   public void onEnable() {
/* 28 */     if (mc.thePlayer != null) {
/* 29 */       this.freecamEntity = new EntityOtherPlayerMP((World)mc.theWorld, new GameProfile(new UUID(69L, 96L), mc.thePlayer.getName()));
/* 30 */       this.freecamEntity.inventory = mc.thePlayer.inventory;
/* 31 */       this.freecamEntity.inventoryContainer = mc.thePlayer.inventoryContainer;
/* 32 */       this.freecamEntity.setPositionAndRotation(mc.thePlayer.posX, mc.thePlayer.posY, mc.thePlayer.posZ, mc.thePlayer.rotationYaw, mc.thePlayer.rotationPitch);
/* 33 */       this.freecamEntity.rotationYawHead = mc.thePlayer.rotationYawHead;
/* 34 */       mc.theWorld.addEntityToWorld(this.freecamEntity.getEntityId(), (Entity)this.freecamEntity);
/* 35 */       mc.renderGlobal.loadRenderers();
/* 36 */       super.onEnable();
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\awareline\main\mod\implement\visual\FreeCam.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */