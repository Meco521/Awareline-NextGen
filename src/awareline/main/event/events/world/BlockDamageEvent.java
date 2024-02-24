/*    */ package awareline.main.event.events.world;
/*    */ 
/*    */ import awareline.main.event.Event;
/*    */ import net.minecraft.block.Block;
/*    */ import net.minecraft.client.entity.EntityPlayerSP;
/*    */ import net.minecraft.entity.player.EntityPlayer;
/*    */ import net.minecraft.util.BlockPos;
/*    */ import net.minecraft.world.World;
/*    */ 
/*    */ public final class BlockDamageEvent extends Event {
/*    */   private float relativeBlockHardness;
/*    */   private final Block block;
/*    */   
/* 14 */   public float getRelativeBlockHardness() { return this.relativeBlockHardness; } private final EntityPlayerSP player; private final World world; private final BlockPos blockPos; public Block getBlock() {
/* 15 */     return this.block; }
/* 16 */   public EntityPlayerSP getPlayer() { return this.player; }
/* 17 */   public World getWorld() { return this.world; } public BlockPos getBlockPos() {
/* 18 */     return this.blockPos;
/*    */   }
/*    */   public BlockDamageEvent(Block block, EntityPlayerSP player, World world, BlockPos blockPos) {
/* 21 */     this.block = block;
/* 22 */     this.player = player;
/* 23 */     this.world = world;
/* 24 */     this.blockPos = blockPos;
/* 25 */     this.relativeBlockHardness = block.getPlayerRelativeBlockHardness((EntityPlayer)player, world, blockPos);
/*    */   }
/*    */   
/*    */   public void setRelativeBlockHardness(float relativeBlockHardness) {
/* 29 */     this.relativeBlockHardness = relativeBlockHardness;
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\awareline\main\event\events\world\BlockDamageEvent.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */