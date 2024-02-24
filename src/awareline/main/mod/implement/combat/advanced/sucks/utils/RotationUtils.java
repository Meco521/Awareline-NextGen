/*     */ package awareline.main.mod.implement.combat.advanced.sucks.utils;
/*     */ 
/*     */ import javax.vecmath.Vector3d;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.entity.EntityPlayerSP;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.EntityLivingBase;
/*     */ import net.minecraft.util.AxisAlignedBB;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ import net.minecraft.util.MathHelper;
/*     */ import net.minecraft.util.Vec3;
/*     */ 
/*     */ public class RotationUtils {
/*  15 */   public static final Minecraft mc = Minecraft.getMinecraft();
/*     */ 
/*     */   
/*     */   public static boolean isVisibleFOV(Entity e, float fov) {
/*  19 */     return (((Math.abs(getRotations(e)[0] - mc.thePlayer.rotationYaw) % 360.0F > 180.0F) ? (360.0F - Math.abs(getRotations(e)[0] - mc.thePlayer.rotationYaw) % 360.0F) : (Math.abs(getRotations(e)[0] - mc.thePlayer.rotationYaw) % 360.0F)) <= fov);
/*     */   }
/*     */   
/*     */   public static float getYawToEntity(Entity e) {
/*  23 */     return (Math.abs(getRotations(e)[0] - mc.thePlayer.rotationYaw) % 360.0F > 180.0F) ? (360.0F - Math.abs(getRotations(e)[0] - mc.thePlayer.rotationYaw) % 360.0F) : (Math.abs(getRotations(e)[0] - mc.thePlayer.rotationYaw) % 360.0F);
/*     */   }
/*     */   public static float[] getRotations(Entity entity) {
/*     */     double diffY;
/*  27 */     if (entity == null) {
/*  28 */       return null;
/*     */     }
/*  30 */     double diffX = entity.posX - mc.thePlayer.posX;
/*  31 */     double diffZ = entity.posZ - mc.thePlayer.posZ;
/*     */     
/*  33 */     if (entity instanceof EntityLivingBase) {
/*  34 */       EntityLivingBase elb = (EntityLivingBase)entity;
/*  35 */       diffY = elb.posY + elb.getEyeHeight() - mc.thePlayer.posY + mc.thePlayer.getEyeHeight();
/*     */     } else {
/*  37 */       diffY = ((entity.getEntityBoundingBox()).minY + (entity.getEntityBoundingBox()).maxY) / 2.0D - mc.thePlayer.posY + mc.thePlayer.getEyeHeight();
/*     */     } 
/*  39 */     double dist = MathHelper.sqrt_double(diffX * diffX + diffZ * diffZ);
/*  40 */     float yaw = (float)(Math.atan2(diffZ, diffX) * 180.0D / Math.PI) - 90.0F;
/*  41 */     float pitch = (float)-(Math.atan2(diffY, dist) * 180.0D / Math.PI);
/*  42 */     return new float[] { yaw, pitch };
/*     */   }
/*     */   
/*     */   public static float getYawDifference(float currentYaw, double targetX, double targetY, double targetZ) {
/*  46 */     double deltaX = targetX - mc.thePlayer.posX;
/*  47 */     double deltaZ = targetZ - mc.thePlayer.posZ;
/*  48 */     double yawToEntity = 0.0D;
/*  49 */     double degrees = Math.toDegrees(Math.atan(deltaZ / deltaX));
/*  50 */     if (deltaZ < 0.0D && deltaX < 0.0D) {
/*  51 */       yawToEntity = 90.0D + degrees;
/*  52 */     } else if (deltaZ < 0.0D && deltaX > 0.0D) {
/*  53 */       yawToEntity = -90.0D + degrees;
/*  54 */     } else if (deltaZ != 0.0D) {
/*  55 */       yawToEntity = Math.toDegrees(-Math.atan(deltaX / deltaZ));
/*     */     } 
/*  57 */     return MathHelper.wrapAngleTo180_float(-(currentYaw - (float)yawToEntity));
/*     */   }
/*     */   
/*     */   public static float[] getRotations(EntityLivingBase ent) {
/*  61 */     double x = ent.posX;
/*  62 */     double z = ent.posZ;
/*  63 */     double y = ent.posY + (ent.getEyeHeight() / 2.0F);
/*  64 */     return getRotationFromPosition(x, z, y);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static float[] getRotationFromPosition(double x, double z, double y) {
/*  70 */     double xDiff = x - mc.thePlayer.posX;
/*     */     
/*  72 */     double zDiff = z - mc.thePlayer.posZ;
/*     */     
/*  74 */     double yDiff = y - mc.thePlayer.posY - 1.2D;
/*  75 */     double dist = MathHelper.sqrt_double(xDiff * xDiff + zDiff * zDiff);
/*  76 */     float yaw = (float)(Math.atan2(zDiff, xDiff) * 180.0D / Math.PI) - 90.0F;
/*  77 */     float pitch = (float)-(Math.atan2(yDiff, dist) * 180.0D / Math.PI);
/*  78 */     return new float[] { yaw, pitch };
/*     */   }
/*     */   
/*     */   public static float getTrajAngleSolutionLow(float d3, float d1, float velocity) {
/*  82 */     float g = 0.006F;
/*  83 */     float sqrt = velocity * velocity * velocity * velocity - 0.006F * (0.006F * d3 * d3 + 2.0F * d1 * velocity * velocity);
/*  84 */     return (float)Math.toDegrees(Math.atan(((velocity * velocity) - Math.sqrt(sqrt)) / (0.006F * d3)));
/*     */   }
/*     */   
/*     */   public static boolean canEntityBeSeen(Entity e) {
/*  88 */     Vec3 vec1 = new Vec3(mc.thePlayer.posX, mc.thePlayer.posY + mc.thePlayer.getEyeHeight(), mc.thePlayer.posZ);
/*  89 */     AxisAlignedBB box = e.getEntityBoundingBox();
/*  90 */     Vec3 vec2 = new Vec3(e.posX, e.posY + (e.getEyeHeight() / 1.32F), e.posZ);
/*  91 */     double minx = e.posX - 0.25D;
/*  92 */     double maxx = e.posX + 0.25D;
/*  93 */     double miny = e.posY;
/*  94 */     double maxy = e.posY + Math.abs(e.posY - box.maxY);
/*  95 */     double minz = e.posZ - 0.25D;
/*  96 */     double maxz = e.posZ + 0.25D;
/*  97 */     boolean see = (mc.theWorld.rayTraceBlocks(vec1, vec2) == null);
/*  98 */     if (see) {
/*  99 */       return true;
/*     */     }
/* 101 */     vec2 = new Vec3(maxx, miny, minz);
/* 102 */     see = (mc.theWorld.rayTraceBlocks(vec1, vec2) == null);
/* 103 */     if (see) {
/* 104 */       return true;
/*     */     }
/* 106 */     vec2 = new Vec3(minx, miny, minz);
/* 107 */     see = (mc.theWorld.rayTraceBlocks(vec1, vec2) == null);
/* 108 */     if (see) {
/* 109 */       return true;
/*     */     }
/* 111 */     vec2 = new Vec3(minx, miny, maxz);
/* 112 */     see = (mc.theWorld.rayTraceBlocks(vec1, vec2) == null);
/* 113 */     if (see) {
/* 114 */       return true;
/*     */     }
/* 116 */     vec2 = new Vec3(maxx, miny, maxz);
/* 117 */     see = (mc.theWorld.rayTraceBlocks(vec1, vec2) == null);
/* 118 */     if (see) {
/* 119 */       return true;
/*     */     }
/* 121 */     vec2 = new Vec3(maxx, maxy, minz);
/* 122 */     see = (mc.theWorld.rayTraceBlocks(vec1, vec2) == null);
/* 123 */     if (see) {
/* 124 */       return true;
/*     */     }
/* 126 */     vec2 = new Vec3(minx, maxy, minz);
/* 127 */     see = (mc.theWorld.rayTraceBlocks(vec1, vec2) == null);
/* 128 */     if (see) {
/* 129 */       return true;
/*     */     }
/* 131 */     vec2 = new Vec3(minx, maxy, maxz - 0.1D);
/* 132 */     see = (mc.theWorld.rayTraceBlocks(vec1, vec2) == null);
/* 133 */     if (see) {
/* 134 */       return true;
/*     */     }
/* 136 */     vec2 = new Vec3(maxx, maxy, maxz);
/* 137 */     see = (mc.theWorld.rayTraceBlocks(vec1, vec2) == null);
/* 138 */     return see;
/*     */   }
/*     */ 
/*     */   
/*     */   public float[] getRotations(double posX, double posY, double posZ) {
/* 143 */     EntityPlayerSP player = mc.thePlayer;
/* 144 */     double x = posX - player.posX;
/* 145 */     double y = posY - player.posY + player.getEyeHeight();
/* 146 */     double z = posZ - player.posZ;
/* 147 */     double dist = MathHelper.sqrt_double(x * x + z * z);
/* 148 */     float yaw = (float)(Math.atan2(z, x) * 180.0D / Math.PI) - 90.0F;
/* 149 */     float pitch = (float)-(Math.atan2(y, dist) * 180.0D / Math.PI);
/* 150 */     return new float[] { yaw, pitch };
/*     */   }
/*     */   
/*     */   public boolean isOnHypixel() {
/* 154 */     return (!mc.isSingleplayer() && (mc.getCurrentServerData()).serverIP.contains("hypixel"));
/*     */   }
/*     */   
/*     */   public Vec3 getVectorForRotation(float pitch, float yaw) {
/* 158 */     float f = MathHelper.cos(-yaw * 0.017453292F - 3.1415927F);
/* 159 */     float f2 = MathHelper.sin(-yaw * 0.017453292F - 3.1415927F);
/* 160 */     float f3 = -MathHelper.cos(-pitch * 0.017453292F);
/* 161 */     float f4 = MathHelper.sin(-pitch * 0.017453292F);
/* 162 */     return new Vec3((f2 * f3), f4, (f * f3));
/*     */   }
/*     */   
/*     */   public static float[] getNeededRotations(Vector3d current, Vector3d target) {
/* 166 */     double diffX = target.x - current.x;
/* 167 */     double diffY = target.y - current.y;
/* 168 */     double diffZ = target.z - current.z;
/* 169 */     double diffXZ = Math.sqrt(diffX * diffX + diffZ * diffZ);
/* 170 */     float yaw = (float)Math.toDegrees(Math.atan2(diffZ, diffX)) - 90.0F;
/* 171 */     float pitch = (float)-Math.toDegrees(Math.atan2(diffY, diffXZ));
/* 172 */     return new float[] { MathHelper.wrapAngleTo180_float(yaw), MathHelper.wrapAngleTo180_float(pitch) };
/*     */   }
/*     */   
/*     */   public float[] getRotations(BlockPos block, EnumFacing face) {
/* 176 */     double x = block.getX() + 0.5D - mc.thePlayer.posX + face.getFrontOffsetX() / 2.0D;
/* 177 */     double z = block.getZ() + 0.5D - mc.thePlayer.posZ + face.getFrontOffsetZ() / 2.0D;
/* 178 */     double y = block.getY() + 0.5D;
/* 179 */     double d1 = mc.thePlayer.posY + mc.thePlayer.getEyeHeight() - y;
/* 180 */     double d2 = Math.sqrt(x * x + z * z);
/* 181 */     float yaw = (float)(Math.atan2(z, x) * 180.0D / Math.PI) - 90.0F;
/* 182 */     float pitch = (float)(Math.atan2(d1, d2) * 180.0D / Math.PI);
/* 183 */     if (yaw < 0.0F) {
/* 184 */       yaw += 360.0F;
/*     */     }
/* 186 */     return new float[] { yaw, pitch };
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\awareline\main\mod\implement\combat\advanced\suck\\utils\RotationUtils.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */