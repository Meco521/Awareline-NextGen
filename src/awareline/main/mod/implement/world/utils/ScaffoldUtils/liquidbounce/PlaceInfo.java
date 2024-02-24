/*    */ package awareline.main.mod.implement.world.utils.ScaffoldUtils.liquidbounce;
/*    */ 
/*    */ import awareline.main.utility.BlockUtils;
/*    */ import net.minecraft.block.Block;
/*    */ import net.minecraft.block.state.IBlockState;
/*    */ import net.minecraft.client.Minecraft;
/*    */ import net.minecraft.util.BlockPos;
/*    */ import net.minecraft.util.EnumFacing;
/*    */ import net.minecraft.util.Vec3;
/*    */ 
/*    */ public final class PlaceInfo {
/* 12 */   private static final Minecraft mc = Minecraft.getMinecraft();
/*    */   private final BlockPos blockPos;
/*    */   private EnumFacing enumFacing;
/*    */   private Vec3 vec3;
/*    */   
/*    */   public PlaceInfo(BlockPos blockPos, EnumFacing enumFacing, Vec3 vec3) {
/* 18 */     this.blockPos = blockPos;
/* 19 */     this.enumFacing = enumFacing;
/* 20 */     this.vec3 = vec3;
/*    */   }
/*    */   
/*    */   public BlockPos getBlockPos() {
/* 24 */     return this.blockPos;
/*    */   }
/*    */   
/*    */   public EnumFacing getEnumFacing() {
/* 28 */     return this.enumFacing;
/*    */   }
/*    */   
/*    */   public void setEnumFacing(EnumFacing enumFacing1) {
/* 32 */     this.enumFacing = enumFacing1;
/*    */   }
/*    */   
/*    */   public Vec3 getVec3() {
/* 36 */     return this.vec3;
/*    */   }
/*    */   
/*    */   public void setVec3(Vec3 vec3) {
/* 40 */     this.vec3 = vec3;
/*    */   }
/*    */   
/*    */   public PlaceInfo(BlockPos blockPos, EnumFacing enumFacing, Vec3 vec3, int n) {
/* 44 */     this(blockPos, enumFacing, vec3);
/* 45 */     if ((n & 0x4) != 0) {
/* 46 */       vec3 = new Vec3(blockPos.getX() + 0.5D, blockPos.getY() + 0.5D, blockPos.getZ() + 0.5D);
/*    */     }
/*    */   }
/*    */   
/*    */   public static PlaceInfo get(BlockPos blockPos) {
/* 51 */     if (BlockUtils.canBeClicked(blockPos.add(0, -1, 0)))
/* 52 */       return new PlaceInfo(blockPos.add(0, -1, 0), EnumFacing.UP, null, 4); 
/* 53 */     if (BlockUtils.canBeClicked(blockPos.add(0, 0, 1)))
/* 54 */       return new PlaceInfo(blockPos.add(0, 0, 1), EnumFacing.NORTH, null, 4); 
/* 55 */     if (BlockUtils.canBeClicked(blockPos.add(-1, 0, 0)))
/* 56 */       return new PlaceInfo(blockPos.add(-1, 0, 0), EnumFacing.EAST, null, 4); 
/* 57 */     if (BlockUtils.canBeClicked(blockPos.add(0, 0, -1))) {
/* 58 */       return new PlaceInfo(blockPos.add(0, 0, -1), EnumFacing.SOUTH, null, 4);
/*    */     }
/* 60 */     return BlockUtils.canBeClicked(blockPos.add(1, 0, 0)) ? new PlaceInfo(blockPos.add(1, 0, 0), EnumFacing.WEST, null, 4) : null;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public static Block getBlock(BlockPos blockPos) {
/*    */     IBlockState blockState;
/* 67 */     if (mc.theWorld != null && (blockState = mc.theWorld.getBlockState(blockPos)) != null) {
/* 68 */       return blockState.getBlock();
/*    */     }
/* 70 */     return null;
/*    */   }
/*    */   
/*    */   public static IBlockState getState(BlockPos blockPos) {
/* 74 */     return mc.theWorld.getBlockState(blockPos);
/*    */   }
/*    */   
/*    */   public static boolean canBeClicked(BlockPos blockPos) {
/* 78 */     Block var10000 = getBlock(blockPos);
/* 79 */     if (var10000 != null && var10000.canCollideCheck(getState(blockPos), false)) {
/* 80 */       return mc.theWorld.getWorldBorder().contains(blockPos);
/*    */     }
/* 82 */     return false;
/*    */   }
/*    */   
/*    */   public void setFacing(EnumFacing enumFacing) {
/* 86 */     this.enumFacing = enumFacing;
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\awareline\main\mod\implement\worl\\utils\ScaffoldUtils\liquidbounce\PlaceInfo.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */