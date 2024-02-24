/*    */ package awareline.main.mod.implement.combat;
/*    */ import awareline.main.event.EventHandler;
/*    */ import awareline.main.event.events.world.updateEvents.EventPreUpdate;
/*    */ import awareline.main.mod.ModuleType;
/*    */ import net.minecraft.entity.Entity;
/*    */ import net.minecraft.entity.item.EntityTNTPrimed;
/*    */ import net.minecraft.entity.player.EntityPlayer;
/*    */ import net.minecraft.network.Packet;
/*    */ import net.minecraft.network.play.client.C07PacketPlayerDigging;
/*    */ import net.minecraft.network.play.client.C08PacketPlayerBlockPlacement;
/*    */ import net.minecraft.util.EnumFacing;
/*    */ 
/*    */ public class TNTBlock extends Module {
/* 14 */   final int ticks = 10;
/*    */   private boolean hasBlocked;
/*    */   
/*    */   public TNTBlock() {
/* 18 */     super("TNTBlock", ModuleType.Combat);
/*    */   }
/*    */   
/*    */   @EventHandler
/*    */   public void onCombat(EventPreUpdate e2) {
/* 23 */     boolean foundTnt = false;
/* 24 */     if (!mc.thePlayer.isDead) {
/* 25 */       for (Entity e1 : mc.theWorld.loadedEntityList) {
/* 26 */         if (!(e1 instanceof EntityTNTPrimed))
/* 27 */           continue;  EntityTNTPrimed entityTNTPrimed = (EntityTNTPrimed)e1;
/* 28 */         if (mc.thePlayer.getDistanceToEntity(e1) > 4.0D)
/* 29 */           continue;  foundTnt = true;
/* 30 */         if (entityTNTPrimed.fuse != 10.0D || entityTNTPrimed.isDead)
/* 31 */           continue;  blockItem();
/*    */       } 
/* 33 */       if (!foundTnt && this.hasBlocked) {
/* 34 */         unblockItem();
/*    */       }
/*    */     } 
/*    */   }
/*    */   
/*    */   private void unblockItem() {
/* 40 */     sendPacket((Packet)new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.RELEASE_USE_ITEM, mc.thePlayer.getPosition(), EnumFacing.DOWN));
/* 41 */     mc.playerController.onStoppedUsingItem((EntityPlayer)mc.thePlayer);
/* 42 */     this.hasBlocked = false;
/*    */   }
/*    */   
/*    */   private void blockItem() {
/* 46 */     if (hasSword()) {
/* 47 */       mc.playerController.sendUseItem((EntityPlayer)mc.thePlayer, (World)mc.theWorld, mc.thePlayer.inventory.getCurrentItem());
/* 48 */       sendPacket((Packet)new C08PacketPlayerBlockPlacement(mc.thePlayer.getPosition(), 0, mc.thePlayer.getCurrentEquippedItem(), 0.0F, 0.0F, 0.0F));
/* 49 */       this.hasBlocked = true;
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\awareline\main\mod\implement\combat\TNTBlock.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */