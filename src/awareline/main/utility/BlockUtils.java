/*     */ package awareline.main.utility;
/*     */ import awareline.main.utility.object.WorldBlockObject;
/*     */ import java.util.List;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.block.material.Material;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.entity.EntityPlayerSP;
/*     */ import net.minecraft.entity.projectile.EntityEgg;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.util.AxisAlignedBB;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ import net.minecraft.util.MathHelper;
/*     */ import net.minecraft.util.MovingObjectPosition;
/*     */ import net.minecraft.util.Vec3;
/*     */ import net.minecraft.world.World;
/*     */ 
/*     */ public class BlockUtils implements Utils {
/*     */   public static Block getBlock(int x2, int y2, int z2) {
/*  21 */     return mc.theWorld.getBlockState(new BlockPos(x2, y2, z2)).getBlock();
/*     */   }
/*     */   
/*     */   public static Block getBlock(double x2, double y2, double z2) {
/*  25 */     return mc.theWorld.getBlockState(new BlockPos((int)x2, (int)y2, (int)z2)).getBlock();
/*     */   }
/*     */   
/*     */   public static boolean lookingAtBlock(BlockPos blockFace, float yaw, float pitch, EnumFacing enumFacing, boolean strict) {
/*  29 */     MovingObjectPosition movingObjectPosition = mc.thePlayer.rayTraceCustom(mc.playerController.getBlockReachDistance(), mc.timer.renderPartialTicks, yaw, pitch);
/*  30 */     if (movingObjectPosition == null) return false; 
/*  31 */     Vec3 hitVec = movingObjectPosition.hitVec;
/*  32 */     if (hitVec == null) return false; 
/*  33 */     if (hitVec.xCoord - blockFace.getX() > 1.0D || hitVec.xCoord - blockFace.getX() < 0.0D) return false; 
/*  34 */     if (hitVec.yCoord - blockFace.getY() > 1.0D || hitVec.yCoord - blockFace.getY() < 0.0D) return false; 
/*  35 */     return (hitVec.zCoord - blockFace.getZ() <= 1.0D && hitVec.zCoord - blockFace.getZ() >= 0.0D && (movingObjectPosition.sideHit == enumFacing || !strict));
/*     */   }
/*     */   public static WorldBlockObject getWillFallInBlock(double startX, double startY, double startZ) {
/*     */     double i;
/*  39 */     for (i = startY; i > -1.0D; i--) {
/*  40 */       Block currentBlock = mc.theWorld.getBlock(startX, i, startZ);
/*     */       
/*  42 */       if (!(currentBlock instanceof net.minecraft.block.BlockAir)) {
/*  43 */         return new WorldBlockObject(currentBlock, new BlockPos(startX, i, startZ));
/*     */       }
/*     */     } 
/*     */     
/*  47 */     return new WorldBlockObject(mc.theWorld.getBlock(startX, 0.0D, startZ), new BlockPos(startX, 0.0D, startZ));
/*     */   }
/*     */   
/*     */   public static boolean collideBlock2(AxisAlignedBB axisAlignedBB, Collidable collide) {
/*  51 */     int x = MathHelper.floor_double((mc.thePlayer.getEntityBoundingBox()).minX);
/*  52 */     for (int var3 = MathHelper.floor_double((mc.thePlayer.getEntityBoundingBox()).maxX) + 1; x < var3; x++) {
/*  53 */       int z = MathHelper.floor_double((mc.thePlayer.getEntityBoundingBox()).minZ);
/*  54 */       for (int var5 = MathHelper.floor_double((mc.thePlayer.getEntityBoundingBox()).maxZ) + 1; z < var5; z++) {
/*  55 */         Block block = getBlock(new BlockPos(x, axisAlignedBB.minY, z));
/*  56 */         if (!collide.collideBlock(block)) {
/*  57 */           return false;
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/*  62 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public static boolean collideBlock(AxisAlignedBB axisAlignedBB, Collidable collide) {
/*  67 */     for (double x = MathHelper.floor_double((Minecraft.getMinecraft()).thePlayer.boundingBox.minX); x < (MathHelper.floor_double((Minecraft.getMinecraft()).thePlayer.boundingBox.maxX) + 1); x++) {
/*  68 */       double z; for (z = MathHelper.floor_double((Minecraft.getMinecraft()).thePlayer.boundingBox.minZ); z < (MathHelper.floor_double((Minecraft.getMinecraft()).thePlayer.boundingBox.maxZ) + 1); z++) {
/*  69 */         Block block = getBlock(x, axisAlignedBB.minY, z);
/*  70 */         if (!collide.collideBlock(block))
/*  71 */           return false; 
/*     */       } 
/*     */     } 
/*  74 */     return true;
/*     */   }
/*     */   
/*     */   public static boolean isOnLadder() {
/*  78 */     if (mc.thePlayer == null) {
/*  79 */       return false;
/*     */     }
/*  81 */     boolean onLadder = false;
/*  82 */     int y2 = (int)(mc.thePlayer.getEntityBoundingBox().offset(0.0D, 1.0D, 0.0D)).minY;
/*  83 */     int x2 = MathHelper.floor_double((mc.thePlayer.getEntityBoundingBox()).minX);
/*  84 */     while (x2 < MathHelper.floor_double((mc.thePlayer.getEntityBoundingBox()).maxX) + 1) {
/*  85 */       int z2 = MathHelper.floor_double((mc.thePlayer.getEntityBoundingBox()).minZ);
/*  86 */       while (z2 < MathHelper.floor_double((mc.thePlayer.getEntityBoundingBox()).maxZ) + 1) {
/*  87 */         Block block = getBlock(x2, y2, z2);
/*  88 */         if (block != null && !(block instanceof net.minecraft.block.BlockAir)) {
/*  89 */           if (!(block instanceof net.minecraft.block.BlockLadder) && !(block instanceof net.minecraft.block.BlockVine)) {
/*  90 */             return false;
/*     */           }
/*  92 */           onLadder = true;
/*     */         } 
/*  94 */         z2++;
/*     */       } 
/*  96 */       x2++;
/*     */     } 
/*  98 */     return (onLadder || mc.thePlayer.isOnLadder());
/*     */   }
/*     */   
/*     */   public static boolean isInsideBlock() {
/* 102 */     int x2 = MathHelper.floor_double((mc.thePlayer.getEntityBoundingBox()).minX);
/* 103 */     while (x2 < MathHelper.floor_double((mc.thePlayer.getEntityBoundingBox()).maxX) + 1) {
/* 104 */       int y2 = MathHelper.floor_double((mc.thePlayer.getEntityBoundingBox()).minY);
/* 105 */       while (y2 < MathHelper.floor_double((mc.thePlayer.getEntityBoundingBox()).maxY) + 1) {
/* 106 */         int z2 = MathHelper.floor_double((mc.thePlayer.getEntityBoundingBox()).minZ);
/* 107 */         while (z2 < MathHelper.floor_double((mc.thePlayer.getEntityBoundingBox()).maxZ) + 1) {
/*     */           
/* 109 */           Block block = mc.theWorld.getBlockState(new BlockPos(x2, y2, z2)).getBlock(); AxisAlignedBB boundingBox;
/* 110 */           if (block != null && !(block instanceof net.minecraft.block.BlockAir) && (boundingBox = block.getCollisionBoundingBox((World)mc.theWorld, new BlockPos(x2, y2, z2), mc.theWorld.getBlockState(new BlockPos(x2, y2, z2)))) != null && mc.thePlayer.getEntityBoundingBox().intersectsWith(boundingBox)) {
/* 111 */             return true;
/*     */           }
/* 113 */           z2++;
/*     */         } 
/* 115 */         y2++;
/*     */       } 
/* 117 */       x2++;
/*     */     } 
/* 119 */     return false;
/*     */   }
/*     */   
/*     */   public static Block getBlock(BlockPos pos) {
/* 123 */     return mc.theWorld.getBlockState(pos).getBlock();
/*     */   }
/*     */   
/*     */   public static boolean isInLiquid() {
/* 127 */     return mc.thePlayer.isInWater();
/*     */   }
/*     */   
/*     */   public static void updateTool(BlockPos pos) {
/* 131 */     Block block = mc.theWorld.getBlockState(pos).getBlock();
/* 132 */     float strength = 1.0F;
/* 133 */     int bestItemIndex = -1;
/* 134 */     for (int i = 0; i < 9; i++) {
/* 135 */       ItemStack itemStack = mc.thePlayer.inventory.mainInventory[i];
/* 136 */       if (itemStack != null)
/*     */       {
/*     */         
/* 139 */         if (itemStack.getStrVsBlock(block) > strength) {
/* 140 */           strength = itemStack.getStrVsBlock(block);
/* 141 */           bestItemIndex = i;
/*     */         }  } 
/*     */     } 
/* 144 */     if (bestItemIndex != -1) {
/* 145 */       mc.thePlayer.inventory.currentItem = bestItemIndex;
/*     */     }
/*     */   }
/*     */   
/*     */   public static boolean isOnLiquid() {
/* 150 */     AxisAlignedBB boundingBox = mc.thePlayer.getEntityBoundingBox();
/* 151 */     if (boundingBox == null) {
/* 152 */       return false;
/*     */     }
/* 154 */     boundingBox = boundingBox.contract(0.01D, 0.0D, 0.01D).offset(0.0D, -0.01D, 0.0D);
/* 155 */     boolean onLiquid = false;
/* 156 */     int y = (int)boundingBox.minY;
/* 157 */     int x = MathHelper.floor_double(boundingBox.minX); for (; x < 
/* 158 */       MathHelper.floor_double(boundingBox.maxX + 1.0D); x++) {
/* 159 */       int z = MathHelper.floor_double(boundingBox.minZ); for (; z < 
/* 160 */         MathHelper.floor_double(boundingBox.maxZ + 1.0D); z++) {
/* 161 */         Block block = mc.theWorld.getBlockState(new BlockPos(x, y, z)).getBlock();
/* 162 */         if (block != Blocks.air) {
/* 163 */           if (!(block instanceof net.minecraft.block.BlockLiquid)) {
/* 164 */             return false;
/*     */           }
/* 166 */           onLiquid = true;
/*     */         } 
/*     */       } 
/*     */     } 
/* 170 */     return onLiquid;
/*     */   }
/*     */   
/*     */   public static boolean canBeClicked(BlockPos pos) {
/* 174 */     return getBlock(pos).canCollideCheck(getState(pos), false);
/*     */   }
/*     */   
/*     */   public static IBlockState getState(BlockPos pos) {
/* 178 */     return mc.theWorld.getBlockState(pos);
/*     */   }
/*     */   
/*     */   public static double getDistanceToFall() {
/* 182 */     double distance = 0.0D;
/* 183 */     for (double i = mc.thePlayer.posY; i > 0.0D; i--) {
/* 184 */       Block block = getBlock(new BlockPos(mc.thePlayer.posX, i, mc.thePlayer.posZ));
/* 185 */       if (block.getMaterial() != Material.air && block.isBlockNormalCube() && block.isCollidable()) {
/* 186 */         distance = i;
/*     */         break;
/*     */       } 
/* 189 */       if (i < 0.0D) {
/*     */         break;
/*     */       }
/*     */     } 
/* 193 */     return mc.thePlayer.posY - distance - 1.0D;
/*     */   }
/*     */ 
/*     */   
/*     */   public static List<ItemStack> getHotbarContent() {
/* 198 */     List<ItemStack> result = new ArrayList<>();
/* 199 */     result.addAll(Arrays.<ItemStack>asList(mc.thePlayer.inventory.mainInventory).subList(0, 9));
/* 200 */     return result;
/*     */   }
/*     */   
/*     */   public static float[] getRotations(double posX, double posY, double posZ) {
/* 204 */     EntityPlayerSP player = mc.thePlayer;
/* 205 */     double x = posX - player.posX;
/* 206 */     double y = posY - player.posY + player.getEyeHeight();
/* 207 */     double z = posZ - player.posZ;
/* 208 */     double dist = MathHelper.sqrt_double(x * x + z * z);
/* 209 */     float yaw = (float)(Math.atan2(z, x) * 180.0D / Math.PI) - 90.0F;
/* 210 */     float pitch = (float)-(Math.atan2(y, dist) * 180.0D / Math.PI);
/* 211 */     return new float[] { yaw, pitch };
/*     */   }
/*     */   
/*     */   public static float[] getDirectionToBlock(double x, double y, double z, EnumFacing enumfacing) {
/* 215 */     EntityEgg var4 = new EntityEgg((World)mc.theWorld);
/* 216 */     var4.posX = x + 0.5D;
/* 217 */     var4.posY = y + 0.5D;
/* 218 */     var4.posZ = z + 0.5D;
/* 219 */     var4.posX += enumfacing.getDirectionVec().getX() * 0.5D;
/* 220 */     var4.posY += enumfacing.getDirectionVec().getY() * 0.5D;
/* 221 */     var4.posZ += enumfacing.getDirectionVec().getZ() * 0.5D;
/* 222 */     return getRotations(var4.posX, var4.posY, var4.posZ);
/*     */   }
/*     */   
/*     */   public static boolean isReplaceable(BlockPos blockPosition) {
/* 226 */     return getMaterial(blockPosition).isReplaceable();
/*     */   }
/*     */   
/*     */   public static Material getMaterial(BlockPos blockPosition) {
/* 230 */     return getBlock(blockPosition).getMaterial();
/*     */   }
/*     */   
/*     */   public static boolean isReplaceable() {
/* 234 */     return false;
/*     */   }
/*     */   
/*     */   public static interface Collidable {
/*     */     boolean collideBlock(Block param1Block);
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\awareline\mai\\utility\BlockUtils.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */