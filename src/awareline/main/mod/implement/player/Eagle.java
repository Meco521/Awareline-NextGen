/*    */ package awareline.main.mod.implement.player;
/*    */ 
/*    */ import awareline.main.event.EventHandler;
/*    */ import awareline.main.event.events.world.updateEvents.EventPreUpdate;
/*    */ import awareline.main.mod.Module;
/*    */ import awareline.main.mod.ModuleType;
/*    */ import net.minecraft.block.Block;
/*    */ import net.minecraft.client.settings.KeyBinding;
/*    */ import net.minecraft.entity.player.EntityPlayer;
/*    */ import net.minecraft.util.BlockPos;
/*    */ 
/*    */ public class Eagle
/*    */   extends Module {
/*    */   public Eagle() {
/* 15 */     super("Eagle", new String[] { "safewalk" }, ModuleType.Movement);
/*    */   }
/*    */   
/*    */   public static Block getBlock(BlockPos pos) {
/* 19 */     return mc.theWorld.getBlockState(pos).getBlock();
/*    */   }
/*    */   
/*    */   public static Block getBlockUnderPlayer(EntityPlayer player) {
/* 23 */     return getBlock(new BlockPos(player.posX, player.posY - 1.0D, player.posZ));
/*    */   }
/*    */   
/*    */   @EventHandler
/*    */   public void onUpdate(EventPreUpdate event) {
/* 28 */     if (getBlockUnderPlayer((EntityPlayer)mc.thePlayer) instanceof net.minecraft.block.BlockAir) {
/* 29 */       if (mc.thePlayer.onGround) {
/* 30 */         KeyBinding.setKeyBindState(mc.gameSettings.keyBindSneak.getKeyCode(), true);
/*    */       }
/* 32 */     } else if (mc.thePlayer.onGround) {
/* 33 */       KeyBinding.setKeyBindState(mc.gameSettings.keyBindSneak.getKeyCode(), false);
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public void onEnable() {
/* 39 */     mc.thePlayer.setSneaking(false);
/* 40 */     super.onEnable();
/*    */   }
/*    */ 
/*    */   
/*    */   public void onDisable() {
/* 45 */     KeyBinding.setKeyBindState(mc.gameSettings.keyBindSneak.getKeyCode(), false);
/* 46 */     super.onDisable();
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\awareline\main\mod\implement\player\Eagle.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */