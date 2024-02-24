/*    */ package awareline.main.mod.implement.player.anti;
/*    */ 
/*    */ import awareline.main.event.EventHandler;
/*    */ import awareline.main.event.events.world.updateEvents.MotionUpdateEvent;
/*    */ import awareline.main.mod.Module;
/*    */ import awareline.main.mod.ModuleType;
/*    */ import java.util.concurrent.ThreadLocalRandom;
/*    */ import net.minecraft.block.Block;
/*    */ import net.minecraft.entity.player.EntityPlayer;
/*    */ import net.minecraft.init.Blocks;
/*    */ import net.minecraft.network.Packet;
/*    */ import net.minecraft.network.play.client.C07PacketPlayerDigging;
/*    */ import net.minecraft.network.play.client.C0APacketAnimation;
/*    */ import net.minecraft.util.BlockPos;
/*    */ import net.minecraft.util.EnumFacing;
/*    */ import net.minecraft.world.World;
/*    */ 
/*    */ public class AntiObbyTrap extends Module {
/*    */   private float currentDamage;
/*    */   
/*    */   public AntiObbyTrap() {
/* 22 */     super("AntiObbyTrap", new String[] { "antitrap" }, ModuleType.Player);
/*    */   }
/*    */   private boolean digging;
/*    */   @EventHandler
/*    */   public void onPre(MotionUpdateEvent event) {
/* 27 */     if (event.getState().equals(MotionUpdateEvent.State.PRE)) {
/* 28 */       this;
/* 29 */       this;
/* 30 */       this; if (mc.theWorld.getBlockState(new BlockPos(event.getX(), event.getY() + 1.0D, event.getZ())).getBlock() == Blocks.obsidian || mc.theWorld.getBlockState(new BlockPos(event.getX(), event.getY() + 1.0D, event.getZ())).getBlock() == Blocks.cobblestone || mc.theWorld.getBlockState(new BlockPos(event.getX(), event.getY() + 2.0D, event.getZ()))
/* 31 */         .getBlock() instanceof net.minecraft.block.BlockFurnace) {
/* 32 */         event.setPitch(89.0F + ThreadLocalRandom.current().nextFloat());
/* 33 */         this; Block currentBlock = mc.theWorld.getBlockState(new BlockPos(event.getX(), event.getY() - 1.0D, event.getZ())).getBlock();
/* 34 */         BlockPos pos = new BlockPos(event.getX(), event.getY() - 1.0D, event.getZ());
/*    */         
/* 36 */         if (this.currentDamage == 0.0F) {
/* 37 */           this.digging = true;
/* 38 */           sendPacket((Packet)new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.START_DESTROY_BLOCK, pos, EnumFacing.UP));
/*    */         } 
/*    */         
/* 41 */         mc.thePlayer.updateTool(pos);
/* 42 */         sendPacket((Packet)new C0APacketAnimation());
/* 43 */         this; this; this.currentDamage += currentBlock.getPlayerRelativeBlockHardness((EntityPlayer)mc.thePlayer, (World)mc.theWorld, pos);
/* 44 */         this; this; mc.theWorld.sendBlockBreakProgress(mc.thePlayer.getEntityID(), pos, (int)(this.currentDamage * 10.0F) - 1);
/*    */         
/* 46 */         if (this.currentDamage >= 1.0F) {
/* 47 */           sendPacket((Packet)new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.STOP_DESTROY_BLOCK, pos, EnumFacing.UP));
/* 48 */           this; mc.playerController.onPlayerDestroyBlock(pos, EnumFacing.UP);
/* 49 */           this.currentDamage = 0.0F;
/* 50 */           this.digging = false;
/*    */         } 
/*    */       } else {
/* 53 */         this.currentDamage = 0.0F;
/* 54 */         this.digging = false;
/*    */       } 
/*    */     } 
/*    */   }
/*    */   
/*    */   public boolean isDigging() {
/* 60 */     return this.digging;
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\awareline\main\mod\implement\player\anti\AntiObbyTrap.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */