/*     */ package awareline.main.mod.implement.world;
/*     */ 
/*     */ import awareline.main.event.EventHandler;
/*     */ import awareline.main.event.events.world.updateEvents.EventPreUpdate;
/*     */ import awareline.main.mod.Module;
/*     */ import awareline.main.mod.ModuleType;
/*     */ import awareline.main.mod.implement.world.utils.breaktimer.TimerUtil;
/*     */ import awareline.main.mod.values.Numbers;
/*     */ import awareline.main.mod.values.Option;
/*     */ import awareline.main.mod.values.Value;
/*     */ import java.util.ArrayList;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.block.material.Material;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.client.multiplayer.WorldClient;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.item.EntityItem;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ import net.minecraft.util.MathHelper;
/*     */ import net.minecraft.util.MovingObjectPosition;
/*     */ import net.minecraft.util.Vec3;
/*     */ 
/*     */ public class AutoBreak extends Module {
/*     */   public static final Numbers<Double> blockhight;
/*     */   
/*     */   static {
/*     */     int height;
/*  29 */     if (mc.theWorld == null) {
/*  30 */       height = 256;
/*     */     } else {
/*  32 */       height = mc.theWorld.getHeight();
/*     */     } 
/*  34 */     max = height;
/*  35 */     blockhight = new Numbers("BlockHeight", Double.valueOf(16.0D), Double.valueOf(1.0D), Double.valueOf(max), Double.valueOf(1.0D));
/*     */   }
/*     */   
/*     */   static final double max;
/*  39 */   private final Option<Boolean> auto = new Option("BetterBreak", Boolean.valueOf(true)); private final TimerUtil skipCheckTimer;
/*     */   private EnumFacing facing;
/*     */   
/*     */   public AutoBreak() {
/*  43 */     super("AutoBreak", new String[] { "automine" }, ModuleType.World);
/*  44 */     addSettings(new Value[] { (Value)this.auto, (Value)blockhight });
/*  45 */     this.facing = EnumFacing.EAST;
/*  46 */     this.skipCheckTimer = new TimerUtil();
/*     */   }
/*     */ 
/*     */   
/*     */   public void onEnable() {
/*  51 */     this.facing = EnumFacing.fromAngle(mc.thePlayer.rotationYaw);
/*  52 */     this.skipCheckTimer.delay(5000.0F);
/*  53 */     super.onEnable();
/*     */   }
/*     */ 
/*     */   
/*     */   public void onDisable() {
/*  58 */     mc.gameSettings.keyBindForward.pressed = false;
/*  59 */     mc.gameSettings.keyBindAttack.pressed = false;
/*  60 */     super.onDisable();
/*     */   }
/*     */   
/*     */   @EventHandler
/*     */   public void onUpdate(EventPreUpdate event) {
/*  65 */     if (((Boolean)this.auto.getValue()).booleanValue()) {
/*  66 */       double posX = mc.thePlayer.posX;
/*  67 */       double posY = mc.thePlayer.posY;
/*  68 */       double y2 = posY + mc.thePlayer.getEyeHeight();
/*  69 */       BlockPos toFace = (new BlockPos(posX, y2, mc.thePlayer.posZ)).offset(this.facing);
/*  70 */       boolean foundItem = false;
/*  71 */       boolean foundOre = false;
/*  72 */       if (this.skipCheckTimer.hasReached(5000.0D)) {
/*  73 */         IBlockState stateUnder = getBlockState(getBlockPosRelativeToEntity((Entity)mc.thePlayer, -0.01D));
/*  74 */         EntityItem theItem = null;
/*  75 */         for (EntityItem item : getNearbyItems(5)) {
/*  76 */           if (mc.thePlayer.canEntityBeSeen((Entity)item) && item.ticksExisted > 20 && item.ticksExisted < 150) {
/*  77 */             foundItem = true;
/*  78 */             theItem = item;
/*     */           } 
/*     */         } 
/*  81 */         if (foundItem) {
/*  82 */           faceEntity((Entity)theItem);
/*  83 */           mc.thePlayer.moveFlying(0.0F, 1.0F, 0.1F);
/*  84 */           double posY2 = theItem.posY;
/*  85 */           if (posY2 > mc.thePlayer.posY && 
/*  86 */             mc.thePlayer.onGround) {
/*  87 */             mc.thePlayer.jump();
/*     */           }
/*     */         } else {
/*     */           
/*  91 */           for (int x = -3; x <= 3; x++) {
/*  92 */             for (int y = -3; y <= 5; y++) {
/*  93 */               for (int z = -3; z <= 3; z++) {
/*  94 */                 double x2 = mc.thePlayer.posX + x;
/*  95 */                 double y3 = mc.thePlayer.posY + y;
/*  96 */                 BlockPos blockPos = new BlockPos(x2, y3, mc.thePlayer.posZ + z);
/*  97 */                 Block block = getBlock(blockPos);
/*  98 */                 IBlockState state = getBlockState(blockPos);
/*  99 */                 if (state.getBlock().getMaterial() != Material.air) {
/* 100 */                   if (block instanceof net.minecraft.block.BlockLiquid) {
/* 101 */                     WorldClient theWorld = mc.theWorld;
/* 102 */                     double posX2 = mc.thePlayer.posX;
/* 103 */                     double posY3 = mc.thePlayer.posY;
/* 104 */                     double y4 = posY3 + mc.thePlayer.getEyeHeight();
/* 105 */                     MovingObjectPosition trace0 = theWorld.rayTraceBlocks(new Vec3(posX2, y4, mc.thePlayer.posZ), new Vec3(blockPos.getX() + 0.5D, blockPos.getY() + 0.5D, blockPos.getZ() + 0.5D), true, false, true);
/* 106 */                     if (trace0.getBlockPos() == null) {
/*     */                       continue;
/*     */                     }
/* 109 */                     BlockPos blockPosTrace = trace0.getBlockPos();
/* 110 */                     Block blockTrace = getBlock(blockPosTrace);
/* 111 */                     if (blockTrace instanceof net.minecraft.block.BlockLiquid) {
/* 112 */                       this.facing = this.facing.getOpposite();
/* 113 */                       double posX3 = mc.thePlayer.posX;
/* 114 */                       double posY4 = mc.thePlayer.posY;
/* 115 */                       double y5 = posY4 + mc.thePlayer.getEyeHeight();
/* 116 */                       toFace = (new BlockPos(posX3, y5, mc.thePlayer.posZ)).offset(this.facing);
/* 117 */                       if (isBlockPosAir(toFace)) {
/* 118 */                         toFace = toFace.down();
/*     */                       }
/* 120 */                       this.skipCheckTimer.reset();
/*     */                       break;
/*     */                     } 
/*     */                   } 
/* 124 */                   if (block instanceof net.minecraft.block.BlockOre || block instanceof net.minecraft.block.BlockRedstoneOre) {
/* 125 */                     WorldClient theWorld2 = mc.theWorld;
/* 126 */                     double posX4 = mc.thePlayer.posX;
/* 127 */                     double posY5 = mc.thePlayer.posY;
/* 128 */                     double y6 = posY5 + mc.thePlayer.getEyeHeight();
/* 129 */                     MovingObjectPosition trace0 = theWorld2.rayTraceBlocks(new Vec3(posX4, y6, mc.thePlayer.posZ), new Vec3(blockPos.getX() + 0.5D, blockPos.getY() + 0.5D, blockPos.getZ() + 0.5D), true, false, true);
/* 130 */                     if (trace0.getBlockPos() != null) {
/* 131 */                       BlockPos blockPosTrace = trace0.getBlockPos();
/* 132 */                       double dist = getVec3(blockPos).distanceTo(getVec3(blockPosTrace));
/* 133 */                       double dist2 = Math.sqrt((x * x + y * y + z * z));
/* 134 */                       if (dist2 >= 1.5D) {
/* 135 */                         float yaw = getFacePos(getVec3(blockPosTrace))[0];
/* 136 */                         yaw = normalizeAngle(yaw);
/* 137 */                         if (yaw == 45.0F) {
/*     */                           continue;
/*     */                         }
/* 140 */                         if (yaw == -45.0F) {
/*     */                           continue;
/*     */                         }
/* 143 */                         if (yaw == 135.0F) {
/*     */                           continue;
/*     */                         }
/* 146 */                         if (yaw == -135.0F) {
/*     */                           continue;
/*     */                         }
/*     */                       } 
/* 150 */                       if (dist <= 0.0D) {
/* 151 */                         toFace = blockPosTrace;
/* 152 */                         foundOre = true; break;
/*     */                       } 
/*     */                     } 
/*     */                   } 
/*     */                 } 
/*     */                 continue;
/*     */               } 
/*     */             } 
/*     */           } 
/* 161 */           if (foundOre) {
/* 162 */             faceBlock(toFace);
/* 163 */             if (!isBlockPosAir(toFace)) {
/* 164 */               mineBlock(toFace);
/*     */             }
/*     */           }
/* 167 */           else if (mc.thePlayer.posY > ((Double)blockhight.getValue()).doubleValue()) {
/* 168 */             if (stateUnder.getBlock().getMaterial() != Material.air) {
/* 169 */               mineBlockUnderPlayer();
/*     */             }
/* 171 */             else if (mc.thePlayer.onGround) {
/* 172 */               mc.thePlayer.moveFlying(0.0F, 0.5F, 0.1F);
/*     */             } 
/*     */           } else {
/*     */             
/* 176 */             if (isBlockPosAir(toFace)) {
/* 177 */               toFace = toFace.down();
/* 178 */               if (isBlockPosSafe(toFace)) {
/* 179 */                 if (isBlockPosAir(toFace) && 
/* 180 */                   mc.thePlayer.onGround) {
/* 181 */                   mc.thePlayer.moveFlying(0.0F, 1.0F, 0.1F);
/*     */                 }
/*     */               } else {
/*     */                 
/* 185 */                 this.facing = EnumFacing.fromAngle((mc.thePlayer.rotationYaw + 90.0F));
/* 186 */                 double posX5 = mc.thePlayer.posX;
/* 187 */                 double posY7 = mc.thePlayer.posY;
/* 188 */                 double y7 = posY7 + mc.thePlayer.getEyeHeight();
/* 189 */                 toFace = (new BlockPos(posX5, y7, mc.thePlayer.posZ)).offset(this.facing);
/* 190 */                 if (!isBlockPosSafe(toFace)) {
/* 191 */                   this.facing = EnumFacing.fromAngle((mc.thePlayer.rotationYaw - 90.0F));
/* 192 */                   double posX6 = mc.thePlayer.posX;
/* 193 */                   double posY8 = mc.thePlayer.posY;
/* 194 */                   double y8 = posY8 + mc.thePlayer.getEyeHeight();
/* 195 */                   toFace = (new BlockPos(posX6, y8, mc.thePlayer.posZ)).offset(this.facing);
/* 196 */                   if (!isBlockPosSafe(toFace.down())) {
/* 197 */                     this.facing = EnumFacing.fromAngle((mc.thePlayer.rotationYaw + 180.0F));
/* 198 */                     double posX7 = mc.thePlayer.posX;
/* 199 */                     double posY9 = mc.thePlayer.posY;
/* 200 */                     double y9 = posY9 + mc.thePlayer.getEyeHeight();
/* 201 */                     toFace = (new BlockPos(posX7, y9, mc.thePlayer.posZ)).offset(this.facing);
/*     */                   } 
/*     */                 } 
/*     */               } 
/*     */             } 
/* 206 */             if (isBlockPosAir(toFace)) {
/* 207 */               double posX8 = mc.thePlayer.posX;
/* 208 */               toFace = (new BlockPos(posX8, mc.thePlayer.posY, mc.thePlayer.posZ)).offset(this.facing);
/*     */             } 
/* 210 */             faceBlock(toFace);
/* 211 */             if (!isBlockPosAir(toFace)) {
/* 212 */               mineBlock(toFace);
/*     */             }
/*     */           } 
/*     */         } 
/*     */       } else {
/*     */         
/* 218 */         if (mc.thePlayer.onGround) {
/* 219 */           mc.thePlayer.moveFlying(0.0F, 1.0F, 0.1F);
/*     */         }
/* 221 */         if (isBlockPosAir(toFace)) {
/* 222 */           toFace = toFace.down();
/*     */         }
/* 224 */         faceBlock(toFace);
/* 225 */         if (!isBlockPosAir(toFace)) {
/* 226 */           mineBlock(toFace);
/*     */         }
/*     */       } 
/*     */     } else {
/* 230 */       mc.gameSettings.keyBindForward.pressed = true;
/* 231 */       mc.gameSettings.keyBindAttack.pressed = true;
/*     */     } 
/*     */   }
/*     */   
/*     */   public void mineBlockUnderPlayer() {
/* 236 */     BlockPos pos = getBlockPosRelativeToEntity((Entity)mc.thePlayer, -0.01D);
/* 237 */     mineBlock(pos);
/*     */   }
/*     */   
/*     */   public void mineBlock(BlockPos pos) {
/* 241 */     faceBlock(pos);
/* 242 */     mc.thePlayer.swingItem();
/* 243 */     mc.playerController.onPlayerDamageBlock(pos, EnumFacing.UP);
/*     */   }
/*     */   
/*     */   public boolean isBlockPosSafe(BlockPos pos) {
/* 247 */     return checkBlockPos(pos, 10);
/*     */   }
/*     */   
/*     */   public boolean checkBlockPos(BlockPos pos, int checkHeight) {
/* 251 */     boolean safe = true;
/* 252 */     boolean blockInWay = false;
/* 253 */     int fallDist = 0;
/* 254 */     if (getBlock(pos).getMaterial() == Material.lava || getBlock(pos).getMaterial() == Material.water) {
/* 255 */       return false;
/*     */     }
/* 257 */     if (getBlock(pos.up(1)).getMaterial() == Material.lava || getBlock(pos.up(1)).getMaterial() == Material.water) {
/* 258 */       return false;
/*     */     }
/* 260 */     if (getBlock(pos.up(2)).getMaterial() == Material.lava || getBlock(pos.up(2)).getMaterial() == Material.water) {
/* 261 */       return false;
/*     */     }
/* 263 */     for (int i = 1; i < checkHeight + 1; i++) {
/* 264 */       BlockPos pos2 = pos.down(i);
/* 265 */       Block block = getBlock(pos2);
/* 266 */       if (block.getMaterial() == Material.air) {
/* 267 */         if (!blockInWay) {
/* 268 */           fallDist++;
/*     */         }
/*     */       } else {
/* 271 */         if (!blockInWay && (block.getMaterial() == Material.lava || block.getMaterial() == Material.water)) {
/* 272 */           return false;
/*     */         }
/* 274 */         if (!blockInWay) {
/* 275 */           blockInWay = true;
/*     */         }
/*     */       } 
/*     */     } 
/* 279 */     if (fallDist > 2) {
/* 280 */       safe = false;
/*     */     }
/* 282 */     return safe;
/*     */   }
/*     */   
/*     */   public static Block getBlock(BlockPos pos) {
/* 286 */     return mc.theWorld.getBlockState(pos).getBlock();
/*     */   }
/*     */   
/*     */   public static BlockPos getBlockPosRelativeToEntity(Entity en, double d) {
/* 290 */     return new BlockPos(en.posX, en.posY + d, en.posZ);
/*     */   }
/*     */   
/*     */   public static IBlockState getBlockState(BlockPos blockPos) {
/* 294 */     return mc.theWorld.getBlockState(blockPos);
/*     */   }
/*     */   
/*     */   public static ArrayList<EntityItem> getNearbyItems(int range) {
/* 298 */     ArrayList<EntityItem> eList = new ArrayList<>();
/*     */     
/* 300 */     for (Object o : mc.theWorld.getLoadedEntityList()) {
/* 301 */       if (!(o instanceof EntityItem)) {
/*     */         continue;
/*     */       }
/* 304 */       EntityItem e = (EntityItem)o;
/*     */       
/* 306 */       if (mc.thePlayer.getDistanceToEntity((Entity)e) >= range) {
/*     */         continue;
/*     */       }
/* 309 */       eList.add(e);
/*     */     } 
/* 311 */     return eList;
/*     */   }
/*     */   
/*     */   public static void faceEntity(Entity en) {
/* 315 */     facePos(new Vec3(en.posX - 0.5D, en.posY + en.getEyeHeight() - en.height / 1.5D, en.posZ - 0.5D));
/*     */   }
/*     */   
/*     */   public static void facePos(Vec3 vec) {
/* 319 */     double n = vec.xCoord + 0.5D;
/* 320 */     double diffX = n - mc.thePlayer.posX;
/* 321 */     double n2 = vec.yCoord + 0.5D;
/* 322 */     double posY = mc.thePlayer.posY;
/* 323 */     double diffY = n2 - posY + mc.thePlayer.getEyeHeight();
/* 324 */     double n3 = vec.zCoord + 0.5D;
/* 325 */     double diffZ = n3 - mc.thePlayer.posZ;
/* 326 */     double dist = MathHelper.sqrt_double(diffX * diffX + diffZ * diffZ);
/* 327 */     float yaw = (float)(Math.atan2(diffZ, diffX) * 180.0D / Math.PI) - 90.0F;
/* 328 */     float pitch = (float)-(Math.atan2(diffY, dist) * 180.0D / Math.PI);
/* 329 */     float rotationYaw = mc.thePlayer.rotationYaw;
/* 330 */     mc.thePlayer.rotationYaw = rotationYaw + MathHelper.wrapAngleTo180_float(yaw - mc.thePlayer.rotationYaw);
/* 331 */     float rotationPitch = mc.thePlayer.rotationPitch;
/* 332 */     mc.thePlayer.rotationPitch = rotationPitch + MathHelper.wrapAngleTo180_float(pitch - mc.thePlayer.rotationPitch);
/*     */   }
/*     */   
/*     */   public static boolean isBlockPosAir(BlockPos blockPos) {
/* 336 */     return (mc.theWorld.getBlockState(blockPos).getBlock().getMaterial() == Material.air);
/*     */   }
/*     */   
/*     */   public static Vec3 getVec3(BlockPos blockPos) {
/* 340 */     return new Vec3(blockPos.getX(), blockPos.getY(), blockPos.getZ());
/*     */   }
/*     */   
/*     */   public static float[] getFacePos(Vec3 vec) {
/* 344 */     double n = vec.xCoord + 0.5D;
/* 345 */     double diffX = n - mc.thePlayer.posX;
/* 346 */     double n2 = vec.yCoord + 0.5D;
/* 347 */     double posY = mc.thePlayer.posY;
/* 348 */     double diffY = n2 - posY + mc.thePlayer.getEyeHeight();
/* 349 */     double n3 = vec.zCoord + 0.5D;
/* 350 */     double diffZ = n3 - mc.thePlayer.posZ;
/* 351 */     double dist = MathHelper.sqrt_double(diffX * diffX + diffZ * diffZ);
/* 352 */     float yaw = (float)(Math.atan2(diffZ, diffX) * 180.0D / Math.PI) - 90.0F;
/* 353 */     float pitch = (float)-(Math.atan2(diffY, dist) * 180.0D / Math.PI);
/* 354 */     float[] array = new float[2];
/* 355 */     int n4 = 0;
/* 356 */     float rotationYaw = mc.thePlayer.rotationYaw;
/* 357 */     array[n4] = rotationYaw + MathHelper.wrapAngleTo180_float(yaw - mc.thePlayer.rotationYaw);
/* 358 */     int n6 = 1;
/*     */     
/* 360 */     float rotationPitch = mc.thePlayer.rotationPitch;
/*     */     
/* 362 */     array[n6] = rotationPitch + MathHelper.wrapAngleTo180_float(pitch - mc.thePlayer.rotationPitch);
/* 363 */     return array;
/*     */   }
/*     */   
/*     */   public static float normalizeAngle(float angle) {
/* 367 */     return (angle + 360.0F) % 360.0F;
/*     */   }
/*     */   
/*     */   public static void faceBlock(BlockPos blockPos) {
/* 371 */     facePos(getVec3(blockPos));
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\awareline\main\mod\implement\world\AutoBreak.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */