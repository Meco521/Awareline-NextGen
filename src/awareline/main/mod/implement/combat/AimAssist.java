/*     */ package awareline.main.mod.implement.combat;
/*     */ 
/*     */ import awareline.main.event.EventHandler;
/*     */ import awareline.main.event.events.world.updateEvents.EventPreUpdate;
/*     */ import awareline.main.mod.Module;
/*     */ import awareline.main.mod.ModuleType;
/*     */ import awareline.main.mod.values.Numbers;
/*     */ import awareline.main.mod.values.Option;
/*     */ import awareline.main.mod.values.Value;
/*     */ import awareline.main.utility.math.MathUtil;
/*     */ import java.util.List;
/*     */ import java.util.Objects;
/*     */ import java.util.Random;
/*     */ import net.minecraft.client.entity.EntityPlayerSP;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.util.AxisAlignedBB;
/*     */ import net.minecraft.util.MathHelper;
/*     */ import net.minecraft.util.MovingObjectPosition;
/*     */ import net.minecraft.util.Vec3;
/*     */ import net.optifine.reflect.Reflector;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class AimAssist
/*     */   extends Module
/*     */ {
/*  30 */   protected final Random rand = new Random();
/*  31 */   private final Numbers<Double> Horizontal = new Numbers("HorizontalSpeed", Double.valueOf(4.2D), Double.valueOf(0.0D), Double.valueOf(10.0D), Double.valueOf(0.1D));
/*  32 */   private final Numbers<Double> vertical = new Numbers("VerticalSpeed", Double.valueOf(2.4D), Double.valueOf(0.0D), Double.valueOf(10.0D), Double.valueOf(0.1D));
/*  33 */   private final Numbers<Double> maxTurnSpeed = new Numbers("MaxTurnSpeed", Double.valueOf(0.2D), Double.valueOf(0.0D), Double.valueOf(1.0D), Double.valueOf(0.01D));
/*  34 */   private final Numbers<Double> minTurnSpeed = new Numbers("MinTurnSpeed", Double.valueOf(0.2D), Double.valueOf(0.0D), Double.valueOf(1.0D), Double.valueOf(0.01D));
/*  35 */   private final Numbers<Double> aimRange = new Numbers("Range", Double.valueOf(4.2D), Double.valueOf(1.0D), Double.valueOf(8.1D), Double.valueOf(0.1D));
/*  36 */   private final Numbers<Double> minAngle = new Numbers("MinAngle", Double.valueOf(0.0D), Double.valueOf(0.0D), Double.valueOf(1.0D), Double.valueOf(1.0D));
/*  37 */   private final Numbers<Double> maxAngle = new Numbers("MaxAngle", Double.valueOf(100.0D), Double.valueOf(20.0D), Double.valueOf(360.0D), Double.valueOf(1.0D));
/*  38 */   private final Option<Boolean> clickAim = new Option("OnlyClick", Boolean.valueOf(false));
/*  39 */   private final Option<Boolean> strafeIncrease = new Option("StrafeIncrease", Boolean.valueOf(false));
/*  40 */   private final Option<Boolean> teamsCheck = new Option("TeamsCheck", Boolean.valueOf(true));
/*  41 */   private final Option<Boolean> onlyHeldItem = new Option("OnlySword", Boolean.valueOf(false));
/*     */   
/*     */   public AimAssist() {
/*  44 */     super("AimAssist", new String[] { "aimbot" }, ModuleType.Combat);
/*  45 */     addSettings(new Value[] { (Value)this.Horizontal, (Value)this.vertical, (Value)this.maxTurnSpeed, (Value)this.minTurnSpeed, (Value)this.aimRange, (Value)this.minAngle, (Value)this.maxAngle, (Value)this.clickAim, (Value)this.strafeIncrease, (Value)this.teamsCheck, (Value)this.onlyHeldItem });
/*     */   }
/*     */   
/*     */   @EventHandler
/*     */   public void onUpdate(EventPreUpdate event) {
/*  50 */     if (((Boolean)this.clickAim.get()).booleanValue() && !mc.gameSettings.keyBindAttack.isKeyDown()) {
/*     */       return;
/*     */     }
/*  53 */     if (mc.thePlayer.getHeldItem() == null && ((Boolean)this.onlyHeldItem.get()).booleanValue()) {
/*     */       return;
/*     */     }
/*  56 */     Entity entity = null;
/*  57 */     double maxDistance = 360.0D;
/*  58 */     double maxAngle = ((Double)this.maxAngle.get()).doubleValue();
/*  59 */     double minAngle = ((Double)this.minAngle.get()).doubleValue();
/*  60 */     for (Entity e : mc.theWorld.getLoadedEntityList()) {
/*     */       double yawdistance;
/*  62 */       if (e == mc.thePlayer || !isValid(e) || maxDistance <= (
/*  63 */         yawdistance = getDistanceBetweenAngles(getAngles(e)[1], mc.thePlayer.rotationYaw))) {
/*     */         continue;
/*     */       }
/*  66 */       entity = e;
/*  67 */       maxDistance = yawdistance;
/*     */     } 
/*  69 */     if (entity != null) {
/*  70 */       float yaw = getAngles(entity)[1];
/*  71 */       float pitch = getAngles(entity)[0];
/*  72 */       double yawdistance = getDistanceBetweenAngles(yaw, mc.thePlayer.rotationYaw);
/*  73 */       double pitchdistance = getDistanceBetweenAngles(pitch, mc.thePlayer.rotationPitch);
/*  74 */       if (pitchdistance <= maxAngle && yawdistance >= minAngle && yawdistance <= maxAngle) {
/*  75 */         double horizontalSpeed = ((Double)this.Horizontal.get()).doubleValue() * 3.0D + ((((Double)this.Horizontal.get()).doubleValue() > 0.0D) ? this.rand.nextDouble() : 0.0D);
/*  76 */         double verticalSpeed = ((Double)this.vertical.get()).doubleValue() * 3.0D + ((((Double)this.vertical.get()).doubleValue() > 0.0D) ? this.rand.nextDouble() : 0.0D);
/*  77 */         if (((Boolean)this.strafeIncrease.get()).booleanValue() && mc.thePlayer.moveStrafing != 0.0F) {
/*  78 */           horizontalSpeed *= 1.25D;
/*     */         }
/*  80 */         if (getEntity(24.0D) != null && Objects.equals(getEntity(24.0D), entity)) {
/*  81 */           horizontalSpeed *= MathUtil.randomNumber(((Double)this.maxTurnSpeed.get()).doubleValue(), ((Double)this.minTurnSpeed.get()).doubleValue());
/*  82 */           verticalSpeed *= MathUtil.randomNumber(((Double)this.maxTurnSpeed.get()).doubleValue(), ((Double)this.minTurnSpeed.get()).doubleValue());
/*     */         } 
/*  84 */         faceTarget(entity, 0.0F, (float)verticalSpeed);
/*  85 */         faceTarget(entity, (float)horizontalSpeed, 0.0F);
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   protected static float getRotation(float currentRotation, float targetRotation, float maxIncrement) {
/*  91 */     float deltaAngle = MathHelper.wrapAngleTo180_float(targetRotation - currentRotation);
/*  92 */     if (deltaAngle > maxIncrement) {
/*  93 */       deltaAngle = maxIncrement;
/*     */     }
/*  95 */     if (deltaAngle < -maxIncrement) {
/*  96 */       deltaAngle = -maxIncrement;
/*     */     }
/*  98 */     return currentRotation + deltaAngle / 2.0F;
/*     */   }
/*     */   
/*     */   private void faceTarget(Entity target, float yawspeed, float pitchspeed) {
/* 102 */     EntityPlayerSP player = mc.thePlayer;
/* 103 */     float yaw = getAngles(target)[1];
/* 104 */     float pitch = getAngles(target)[0];
/* 105 */     player.rotationYaw = getRotation(player.rotationYaw, yaw, yawspeed);
/* 106 */     player.rotationPitch = getRotation(player.rotationPitch, pitch, pitchspeed);
/*     */   }
/*     */   
/*     */   public float[] getAngles(Entity entity) {
/* 110 */     double x = entity.posX - mc.thePlayer.posX;
/* 111 */     double z = entity.posZ - mc.thePlayer.posZ;
/*     */ 
/*     */     
/* 114 */     double y = (entity instanceof net.minecraft.entity.monster.EntityEnderman) ? (entity.posY - mc.thePlayer.posY) : (entity.posY + entity.getEyeHeight() - 1.9D - mc.thePlayer.posY + mc.thePlayer.getEyeHeight() - 1.9D);
/* 115 */     double helper = MathHelper.sqrt_double(x * x + z * z);
/* 116 */     float newYaw = (float)Math.toDegrees(-Math.atan(x / z));
/* 117 */     float newPitch = (float)-Math.toDegrees(Math.atan(y / helper));
/* 118 */     if (z < 0.0D && x < 0.0D) {
/* 119 */       newYaw = (float)(90.0D + Math.toDegrees(Math.atan(z / x)));
/* 120 */     } else if (z < 0.0D && x > 0.0D) {
/* 121 */       newYaw = (float)(-90.0D + Math.toDegrees(Math.atan(z / x)));
/*     */     } 
/* 123 */     return new float[] { newPitch, newYaw };
/*     */   }
/*     */   
/*     */   public double getDistanceBetweenAngles(float angle1, float angle2) {
/* 127 */     float distance = Math.abs(angle1 - angle2) % 360.0F;
/* 128 */     if (distance > 180.0F) {
/* 129 */       distance = 360.0F - distance;
/*     */     }
/* 131 */     return distance;
/*     */   }
/*     */   
/*     */   public Object[] getEntity(double distance, double expand) {
/* 135 */     Entity var2 = mc.getRenderViewEntity();
/* 136 */     Entity entity = null;
/* 137 */     if (var2 != null && mc.theWorld != null) {
/*     */       
/* 139 */       mc.mcProfiler.startSection("pick");
/* 140 */       double var3 = distance, var5 = var3;
/* 141 */       Vec3 var7 = var2.getPositionEyes(0.0F);
/* 142 */       Vec3 var8 = var2.getLook(0.0F);
/* 143 */       Vec3 var9 = var7.addVector(var8.xCoord * var3, var8.yCoord * var3, var8.zCoord * var3);
/* 144 */       Vec3 var10 = null;
/* 145 */       float var11 = 1.0F;
/* 146 */       List<Entity> var12 = mc.theWorld.getEntitiesWithinAABBExcludingEntity(var2, var2.getEntityBoundingBox()
/* 147 */           .addCoord(var8.xCoord * var3, var8.yCoord * var3, var8.zCoord * var3).expand(var11, var11, var11));
/* 148 */       double var13 = var5;
/* 149 */       for (Entity o : var12) {
/*     */         
/* 151 */         if (!o.canBeCollidedWith())
/*     */           continue; 
/* 153 */         float var17 = o.getCollisionBorderSize();
/* 154 */         AxisAlignedBB var18 = o.getEntityBoundingBox().expand(var17, var17, var17);
/* 155 */         var18 = var18.expand(expand, expand, expand);
/* 156 */         MovingObjectPosition var19 = var18.calculateIntercept(var7, var9);
/* 157 */         if (var18.isVecInside(var7)) {
/* 158 */           if (0.0D >= var13 && var13 != 0.0D)
/*     */             continue; 
/* 160 */           entity = o;
/* 161 */           var10 = (var19 == null) ? var7 : var19.hitVec;
/* 162 */           var13 = 0.0D; continue;
/*     */         } 
/*     */         double var20;
/* 165 */         if (var19 == null || ((var20 = var7.distanceTo(var19.hitVec)) >= var13 && var13 != 0.0D))
/*     */           continue; 
/* 167 */         boolean canRiderInteract = false;
/* 168 */         if (Reflector.ForgeEntity_canRiderInteract.exists()) {
/* 169 */           canRiderInteract = Reflector.callBoolean(o, Reflector.ForgeEntity_canRiderInteract, new Object[0]);
/*     */         }
/*     */         
/* 172 */         if (o == var2.ridingEntity && !canRiderInteract) {
/* 173 */           if (var13 != 0.0D)
/*     */             continue; 
/* 175 */           entity = o;
/* 176 */           var10 = var19.hitVec;
/*     */           continue;
/*     */         } 
/* 179 */         entity = o;
/* 180 */         var10 = var19.hitVec;
/* 181 */         var13 = var20;
/*     */       } 
/* 183 */       if (var13 < var5 && !(entity instanceof net.minecraft.entity.EntityLivingBase) && !(entity instanceof net.minecraft.entity.item.EntityItemFrame)) {
/* 184 */         entity = null;
/*     */       }
/* 186 */       mc.mcProfiler.endSection();
/* 187 */       if (entity == null || var10 == null) {
/* 188 */         return null;
/*     */       }
/* 190 */       return new Object[] { entity, var10 };
/*     */     } 
/* 192 */     return null;
/*     */   }
/*     */   
/*     */   public Entity getEntity(double distance) {
/* 196 */     if (getEntity(distance, 0.0D) == null) {
/* 197 */       return null;
/*     */     }
/* 199 */     return (Entity)getEntity(distance, 0.0D)[0];
/*     */   }
/*     */   
/*     */   public boolean isValid(Entity e) {
/*     */     // Byte code:
/*     */     //   0: iconst_1
/*     */     //   1: istore_2
/*     */     //   2: getstatic awareline/main/mod/implement/combat/AntiBot.getInstance : Lawareline/main/mod/implement/combat/AntiBot;
/*     */     //   5: astore_3
/*     */     //   6: aload_3
/*     */     //   7: invokevirtual isEnabled : ()Z
/*     */     //   10: ifeq -> 29
/*     */     //   13: aload_1
/*     */     //   14: checkcast net/minecraft/entity/EntityLivingBase
/*     */     //   17: invokestatic isBot : (Lnet/minecraft/entity/EntityLivingBase;)Z
/*     */     //   20: ifne -> 27
/*     */     //   23: iconst_1
/*     */     //   24: goto -> 28
/*     */     //   27: iconst_0
/*     */     //   28: istore_2
/*     */     //   29: aload_1
/*     */     //   30: instanceof net/minecraft/entity/EntityLivingBase
/*     */     //   33: ifeq -> 189
/*     */     //   36: aload_1
/*     */     //   37: instanceof net/minecraft/entity/item/EntityArmorStand
/*     */     //   40: ifne -> 189
/*     */     //   43: aload_1
/*     */     //   44: instanceof net/minecraft/entity/passive/EntityAnimal
/*     */     //   47: ifne -> 189
/*     */     //   50: aload_1
/*     */     //   51: instanceof net/minecraft/entity/monster/EntityMob
/*     */     //   54: ifne -> 189
/*     */     //   57: aload_1
/*     */     //   58: getstatic awareline/main/mod/implement/combat/AimAssist.mc : Lnet/minecraft/client/Minecraft;
/*     */     //   61: getfield thePlayer : Lnet/minecraft/client/entity/EntityPlayerSP;
/*     */     //   64: if_acmpeq -> 189
/*     */     //   67: aload_1
/*     */     //   68: instanceof net/minecraft/entity/passive/EntityVillager
/*     */     //   71: ifne -> 189
/*     */     //   74: getstatic awareline/main/mod/implement/combat/AimAssist.mc : Lnet/minecraft/client/Minecraft;
/*     */     //   77: getfield thePlayer : Lnet/minecraft/client/entity/EntityPlayerSP;
/*     */     //   80: aload_1
/*     */     //   81: invokevirtual getDistanceToEntity : (Lnet/minecraft/entity/Entity;)F
/*     */     //   84: f2d
/*     */     //   85: aload_0
/*     */     //   86: getfield aimRange : Lawareline/main/mod/values/Numbers;
/*     */     //   89: invokevirtual get : ()Ljava/lang/Object;
/*     */     //   92: checkcast java/lang/Double
/*     */     //   95: invokevirtual doubleValue : ()D
/*     */     //   98: dcmpl
/*     */     //   99: ifgt -> 189
/*     */     //   102: aload_1
/*     */     //   103: invokevirtual getName : ()Ljava/lang/String;
/*     */     //   106: ldc '#'
/*     */     //   108: invokevirtual contains : (Ljava/lang/CharSequence;)Z
/*     */     //   111: ifne -> 189
/*     */     //   114: aload_0
/*     */     //   115: getfield teamsCheck : Lawareline/main/mod/values/Option;
/*     */     //   118: invokevirtual get : ()Ljava/lang/Object;
/*     */     //   121: checkcast java/lang/Boolean
/*     */     //   124: invokevirtual booleanValue : ()Z
/*     */     //   127: ifeq -> 181
/*     */     //   130: aload_1
/*     */     //   131: invokevirtual getDisplayName : ()Lnet/minecraft/util/IChatComponent;
/*     */     //   134: invokeinterface getFormattedText : ()Ljava/lang/String;
/*     */     //   139: new java/lang/StringBuilder
/*     */     //   142: dup
/*     */     //   143: invokespecial <init> : ()V
/*     */     //   146: ldc '§'
/*     */     //   148: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
/*     */     //   151: getstatic awareline/main/mod/implement/combat/AimAssist.mc : Lnet/minecraft/client/Minecraft;
/*     */     //   154: getfield thePlayer : Lnet/minecraft/client/entity/EntityPlayerSP;
/*     */     //   157: invokevirtual getDisplayName : ()Lnet/minecraft/util/IChatComponent;
/*     */     //   160: invokeinterface getFormattedText : ()Ljava/lang/String;
/*     */     //   165: iconst_1
/*     */     //   166: invokevirtual charAt : (I)C
/*     */     //   169: invokevirtual append : (C)Ljava/lang/StringBuilder;
/*     */     //   172: invokevirtual toString : ()Ljava/lang/String;
/*     */     //   175: invokevirtual startsWith : (Ljava/lang/String;)Z
/*     */     //   178: ifne -> 189
/*     */     //   181: iload_2
/*     */     //   182: ifeq -> 189
/*     */     //   185: iconst_1
/*     */     //   186: goto -> 190
/*     */     //   189: iconst_0
/*     */     //   190: ireturn
/*     */     // Line number table:
/*     */     //   Java source line number -> byte code offset
/*     */     //   #203	-> 0
/*     */     //   #204	-> 2
/*     */     //   #205	-> 6
/*     */     //   #206	-> 13
/*     */     //   #208	-> 29
/*     */     //   #209	-> 89
/*     */     //   #208	-> 95
/*     */     //   #209	-> 103
/*     */     //   #210	-> 131
/*     */     //   #211	-> 134
/*     */     //   #213	-> 157
/*     */     //   #214	-> 160
/*     */     //   #215	-> 166
/*     */     //   #211	-> 175
/*     */     //   #208	-> 190
/*     */     // Local variable table:
/*     */     //   start	length	slot	name	descriptor
/*     */     //   0	191	0	this	Lawareline/main/mod/implement/combat/AimAssist;
/*     */     //   0	191	1	e	Lnet/minecraft/entity/Entity;
/*     */     //   2	189	2	flag1	Z
/*     */     //   6	185	3	ab2	Lawareline/main/mod/Module;
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\awareline\main\mod\implement\combat\AimAssist.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */