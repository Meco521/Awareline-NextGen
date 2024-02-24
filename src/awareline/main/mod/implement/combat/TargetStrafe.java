/*     */ package awareline.main.mod.implement.combat;
/*     */ import awareline.main.Client;
/*     */ import awareline.main.InstanceAccess;
/*     */ import awareline.main.event.EventHandler;
/*     */ import awareline.main.event.events.world.EventTick;
/*     */ import awareline.main.event.events.world.moveEvents.EventMove;
/*     */ import awareline.main.event.events.world.renderEvents.EventRender3D;
/*     */ import awareline.main.mod.ModuleType;
/*     */ import awareline.main.mod.implement.combat.advanced.sucks.utils.RotationUtils;
/*     */ import awareline.main.mod.implement.move.Flight;
/*     */ import awareline.main.mod.implement.move.Speed;
/*     */ import awareline.main.mod.values.Mode;
/*     */ import awareline.main.mod.values.Numbers;
/*     */ import awareline.main.mod.values.Option;
/*     */ import awareline.main.mod.values.Value;
/*     */ import awareline.main.utility.math.RotationUtil;
/*     */ import awareline.main.utility.render.RenderUtil;
/*     */ import java.awt.Color;
/*     */ import java.util.Comparator;
/*     */ import java.util.List;
/*     */ import java.util.concurrent.CopyOnWriteArrayList;
/*     */ import java.util.stream.Collector;
/*     */ import java.util.stream.Collectors;
/*     */ import javax.vecmath.Vector3d;
/*     */ import net.minecraft.client.renderer.GlStateManager;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.EntityLivingBase;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.init.Blocks;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.util.MovingObjectPosition;
/*     */ import net.minecraft.util.Vec3;
/*     */ import org.lwjgl.opengl.GL11;
/*     */ 
/*     */ public class TargetStrafe extends Module {
/*  36 */   public final Mode<String> mode = new Mode("Mode", new String[] { "Normal", "Advanced" }, "Normal");
/*     */   
/*  38 */   private final Mode<String> render = new Mode("Render", new String[] { "Matrix", "Circle", "CirclePoint" }, "Circle");
/*     */ 
/*     */   
/*  41 */   private final Numbers<Double> radius = new Numbers("Radius", 
/*  42 */       Double.valueOf(2.0D), Double.valueOf(0.5D), Double.valueOf(6.0D), Double.valueOf(0.25D));
/*     */   
/*  44 */   private final Option<Boolean> dynamicRange = new Option("DynamicRange", 
/*  45 */       Boolean.valueOf(true), () -> Boolean.valueOf(this.mode.is("Advanced")));
/*  46 */   private final Numbers<Double> safeRadius = new Numbers("SafeRadius", 
/*  47 */       Double.valueOf(2.0D), Double.valueOf(0.25D), Double.valueOf(6.0D), Double.valueOf(0.25D), this.dynamicRange::get, () -> Boolean.valueOf(this.mode.is("Advanced")));
/*  48 */   private final Numbers<Double> attackRadius = new Numbers("AttackRadius", 
/*  49 */       Double.valueOf(2.0D), Double.valueOf(0.5D), Double.valueOf(4.5D), Double.valueOf(0.5D), this.dynamicRange::get, () -> Boolean.valueOf(this.mode.is("Advanced")));
/*  50 */   private final Numbers<Double> switchHurtTick = new Numbers("SwitchHurtTick", 
/*  51 */       Double.valueOf(9.0D), Double.valueOf(5.0D), Double.valueOf(20.0D), Double.valueOf(1.0D), this.dynamicRange::get, () -> Boolean.valueOf(this.mode.is("Advanced")));
/*  52 */   private final Numbers<Double> height = new Numbers("Height", 
/*  53 */       Double.valueOf(5.0D), Double.valueOf(5.0D), Double.valueOf(10.0D), Double.valueOf(1.0D), () -> Boolean.valueOf(this.mode.is("Advanced")));
/*  54 */   private final Option<Boolean> target = new Option("Target", Boolean.valueOf(false), () -> Boolean.valueOf(this.mode.is("Advanced")));
/*  55 */   private final Option<Boolean> points = new Option("Point", Boolean.valueOf(false), () -> Boolean.valueOf(this.mode.is("Advanced")));
/*  56 */   private final Numbers<Double> pointsMultiplier = new Numbers("PointsMultiplier", 
/*  57 */       Double.valueOf(2.0D), Double.valueOf(0.5D), Double.valueOf(3.5D), Double.valueOf(0.25D), this.points::get, () -> Boolean.valueOf(this.mode.is("Advanced")));
/*  58 */   private final Option<Boolean> onlySpace = new Option("OnlySpace", Boolean.valueOf(false), () -> Boolean.valueOf(this.mode.is("Advanced")));
/*  59 */   private final Option<Boolean> controllable = new Option("Controllable", Boolean.valueOf(true), () -> Boolean.valueOf(this.mode.is("Advanced")));
/*  60 */   private final Option<Boolean> behind = new Option("Behind", Boolean.valueOf(false), () -> Boolean.valueOf(this.mode.is("Advanced")));
/*  61 */   private final Option<Boolean> autoThirdPerson = new Option("AutoThirdPerson", Boolean.valueOf(false), () -> Boolean.valueOf(this.mode.is("Advanced")));
/*  62 */   private final Option<Boolean> afkCheck = new Option("AFKCheck", Boolean.valueOf(false), () -> Boolean.valueOf(this.mode.is("Advanced")));
/*  63 */   private final Option<Boolean> avoidEdges = new Option("AvoidEdges", Boolean.valueOf(true), () -> Boolean.valueOf(this.mode.is("Advanced")));
/*     */   
/*     */   private boolean inverted;
/*     */   private static final double DOUBLED_PI = 6.283185307179586D;
/*     */   private int position;
/*  68 */   private int direction = 1; public static TargetStrafe getInstance; private float dist;
/*     */   boolean direction2;
/*     */   
/*     */   public TargetStrafe() {
/*  72 */     super("TargetStrafe", new String[] { "ts" }, ModuleType.Combat);
/*  73 */     addSettings(new Value[] { (Value)this.mode, (Value)this.radius, (Value)this.render, (Value)this.safeRadius, (Value)this.attackRadius, (Value)this.pointsMultiplier, (Value)this.switchHurtTick, (Value)this.height, (Value)this.dynamicRange, (Value)this.target, (Value)this.points, (Value)this.onlySpace, (Value)this.controllable, (Value)this.behind, (Value)this.autoThirdPerson, (Value)this.afkCheck, (Value)this.avoidEdges });
/*     */ 
/*     */     
/*  76 */     getInstance = this;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private void switchDirection() {
/*  82 */     if (this.direction == 1) {
/*  83 */       this.direction = -1;
/*     */     } else {
/*  85 */       this.direction = 1;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private boolean isBlockUnder(Entity entity) {
/*  91 */     for (int i = (int)(entity.posY - 1.0D); i > 0; ) {
/*  92 */       BlockPos pos = new BlockPos(entity.posX, i, entity.posZ);
/*  93 */       if (mc.theWorld.getBlockState(pos).getBlock() instanceof net.minecraft.block.BlockAir) { i--; continue; }
/*  94 */        return true;
/*     */     } 
/*  96 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void exhiStrafe(EventMove event, double speed, Entity entity) {
/* 103 */     double range = ((Double)this.radius.get()).doubleValue();
/* 104 */     if (!isBlockUnder(entity)) {
/* 105 */       mc.thePlayer.motionZ = 0.0D;
/* 106 */       mc.thePlayer.motionX = 0.0D;
/* 107 */       if (event != null) {
/* 108 */         event.setX(0.0D);
/* 109 */         event.setZ(0.0D);
/*     */       } 
/*     */       return;
/*     */     } 
/* 113 */     if (!isBlockUnder((Entity)mc.thePlayer) && !Flight.getInstance.isEnabled()) {
/* 114 */       this.direction2 = !this.direction2;
/*     */     }
/* 116 */     if (mc.thePlayer.isCollidedHorizontally) {
/* 117 */       this.direction2 = !this.direction2;
/*     */     }
/* 119 */     float strafe = this.direction2 ? 1.0F : -1.0F;
/* 120 */     if (mc.gameSettings.keyBindLeft.isPressed()) strafe = 1.0F; 
/* 121 */     if (mc.gameSettings.keyBindRight.isPressed()) strafe = -1.0F; 
/* 122 */     float diff = (float)(speed / range * Math.PI * 2.0D) * 360.0F * strafe;
/* 123 */     float[] rotation = RotationUtils.getNeededRotations(new Vector3d(entity.posX, entity.posY, entity.posZ), new Vector3d(mc.thePlayer.posX, mc.thePlayer.posY, mc.thePlayer.posZ)), arrf = rotation;
/* 124 */     arrf[0] = arrf[0] + diff;
/* 125 */     float dir = rotation[0] * 0.017453292F;
/* 126 */     double x = entity.posX - Math.sin(dir) * range;
/* 127 */     double z = entity.posZ + Math.cos(dir) * range;
/* 128 */     float yaw = RotationUtils.getNeededRotations(new Vector3d(mc.thePlayer.posX, mc.thePlayer.posY, mc.thePlayer.posZ), new Vector3d(x, entity.posY, z))[0] * 0.017453292F;
/*     */     
/* 130 */     mc.thePlayer.motionX = -Math.sin(yaw) * speed;
/* 131 */     mc.thePlayer.motionZ = Math.cos(yaw) * speed;
/* 132 */     if (event != null) {
/* 133 */       event.setX(mc.thePlayer.motionX);
/* 134 */       event.setZ(mc.thePlayer.motionZ);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   @EventHandler
/*     */   public void onUpdate(EventTick eventTick) {
/* 141 */     if (this.mode.is("Normal")) {
/*     */       return;
/*     */     }
/* 144 */     if (mc.thePlayer.movementInput.getMoveStrafe() < 0.0F) {
/* 145 */       this.direction = -1;
/* 146 */     } else if (mc.thePlayer.movementInput.getMoveStrafe() > 0.0F) {
/* 147 */       this.direction = 1;
/*     */     } 
/* 149 */     if (isCollided() || !isBlockUnder((Entity)KillAura.getInstance.getTarget())) {
/* 150 */       this.inverted = !this.inverted;
/* 151 */       this.position = this.inverted ? (this.position - 1) : (this.position + 1);
/* 152 */     } else if (mc.thePlayer.isCollidedHorizontally) {
/* 153 */       switchDirection();
/*     */     } 
/*     */     
/* 156 */     KillAura killAura = KillAura.getInstance;
/* 157 */     EntityLivingBase entityLivingBase = killAura.getTarget();
/* 158 */     double rad = getRadius((Entity)entityLivingBase);
/* 159 */     int positionsCount = (int)(Math.PI * rad);
/*     */     
/* 161 */     double radianPerPosition = 6.283185307179586D / positionsCount;
/* 162 */     double posX = Math.sin(radianPerPosition * (this.position + 1) * rad * (((Boolean)this.controllable.get()).booleanValue() ? this.direction : true));
/* 163 */     double posY = Math.cos(radianPerPosition * (this.position + 1)) * rad;
/*     */     
/* 165 */     if (!isVoidBelow(((Entity)entityLivingBase).posX + posX, ((Entity)entityLivingBase).posY, ((Entity)entityLivingBase).posZ + posY)) {
/* 166 */       this.inverted = !this.inverted;
/* 167 */       this.position = this.inverted ? (this.position - 1) : (this.position + 1);
/*     */     } 
/*     */     
/* 170 */     this.dist = 0.7F;
/*     */     
/* 172 */     if (((Boolean)this.autoThirdPerson.get()).booleanValue() && (
/* 173 */       !killAura.isEnabled() || killAura.getTarget() == null || !shouldTarget())) {
/* 174 */       mc.gameSettings.thirdPersonView = 0;
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void circleStrafe(EventMove event, double movementSpeed, Entity target) {
/* 181 */     if (target == null) {
/*     */       return;
/*     */     }
/*     */     
/* 185 */     if (((Boolean)this.afkCheck.isEnabled()).booleanValue() && 
/* 186 */       target.onGround && target.hurtResistantTime == 0) {
/* 187 */       target = null;
/*     */     }
/*     */ 
/*     */     
/* 191 */     if (this.mode.is("Normal")) {
/*     */       return;
/*     */     }
/*     */     
/* 195 */     double rad = getRadius(target);
/* 196 */     int positionsCount = (int)((int)(Math.PI * rad) * ((Double)this.pointsMultiplier.get()).doubleValue());
/*     */     
/* 198 */     double radianPerPosition = 6.283185307179586D / positionsCount;
/* 199 */     double posX = Math.sin(radianPerPosition * this.position) * rad * (((Boolean)this.controllable.get()).booleanValue() ? this.direction : true);
/* 200 */     double posY = Math.cos(radianPerPosition * this.position) * rad;
/*     */     
/* 202 */     Vec3 myPos = new Vec3(mc.thePlayer.posX, mc.thePlayer.posY + mc.thePlayer.getEyeHeight(), mc.thePlayer.posZ);
/* 203 */     Vec3 rotVec = getVectorForRotation(90.0F, 0.0F);
/* 204 */     Vec3 multiplied = myPos.addVector(rotVec.xCoord * ((Double)this.height.get()).doubleValue(), rotVec.yCoord * ((Double)this.height.get()).doubleValue(), rotVec.zCoord * ((Double)this.height.get()).doubleValue());
/* 205 */     MovingObjectPosition movingObjectPosition = mc.theWorld.rayTraceBlocks(myPos, multiplied, false, false, false);
/*     */     
/* 207 */     if (!((Boolean)this.avoidEdges.get()).booleanValue() || movingObjectPosition != null) {
/* 208 */       if (((Boolean)this.autoThirdPerson.get()).booleanValue()) {
/* 209 */         mc.gameSettings.thirdPersonView = 1;
/*     */       }
/* 211 */       if (((Boolean)this.behind.get()).booleanValue()) {
/* 212 */         double xPos = target.posX + -Math.sin(Math.toRadians(target.rotationYaw)) * -2.0D, zPos = target.posZ + Math.cos(Math.toRadians(target.rotationYaw)) * -2.0D;
/* 213 */         event.setX(movementSpeed * -Math.sin(Math.toRadians(RotationUtil.getRotations(xPos, target.posY, zPos)[0])));
/* 214 */         event.setZ(movementSpeed * Math.cos(Math.toRadians(RotationUtil.getRotations(xPos, target.posY, zPos)[0])));
/*     */       } else {
/* 216 */         event.setX(movementSpeed * 
/* 217 */             -Math.sin(Math.toRadians(RotationUtil.getRotations(target.posX + posX, target.posY, target.posZ + posY)[0])));
/* 218 */         event.setZ(movementSpeed * 
/* 219 */             Math.cos(Math.toRadians(RotationUtil.getRotations(target.posX + posX, target.posY, target.posZ + posY)[0])));
/*     */       } 
/*     */     } else {
/* 222 */       event.setX(0.0D);
/* 223 */       event.setZ(0.0D);
/*     */     } 
/*     */     
/* 226 */     double x = Math.abs(target.posX + posX - mc.thePlayer.posX);
/* 227 */     double z = Math.abs(target.posZ + posY - mc.thePlayer.posZ);
/* 228 */     double sqrt = Math.sqrt(x * x + z * z);
/*     */     
/* 230 */     if (sqrt <= this.dist) {
/* 231 */       this.position += (this.inverted ? -1 : 1) % positionsCount;
/* 232 */     } else if (sqrt > 3.0D) {
/* 233 */       this.position = getClosestPoint(target);
/*     */     } 
/*     */   }
/*     */   
/*     */   private double getRadius(Entity target) {
/* 238 */     if (((Boolean)this.dynamicRange.get()).booleanValue()) {
/* 239 */       if (target.hurtResistantTime <= ((Double)this.switchHurtTick.get()).intValue()) {
/* 240 */         return ((Double)this.attackRadius.get()).doubleValue();
/*     */       }
/* 242 */       return ((Double)this.safeRadius.get()).doubleValue();
/*     */     } 
/*     */     
/* 245 */     return ((Double)this.radius.get()).doubleValue();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected final Vec3 getVectorForRotation(float pitch, float yaw) {
/* 251 */     double f = Math.cos(Math.toRadians(-yaw) - 3.1415927410125732D);
/* 252 */     double f1 = Math.sin(Math.toRadians(-yaw) - 3.1415927410125732D);
/* 253 */     double f2 = -Math.cos(Math.toRadians(-pitch));
/* 254 */     double f3 = Math.sin(Math.toRadians(-pitch));
/* 255 */     return new Vec3(f1 * f2, f3, f * f2);
/*     */   }
/*     */   
/*     */   private boolean isVoidBelow(double x, double y, double z) {
/* 259 */     for (int i = (int)y; i > 0; i--) {
/* 260 */       if (mc.theWorld.getBlockState(new BlockPos(x, i, z)).getBlock() != Blocks.air) {
/* 261 */         return true;
/*     */       }
/* 263 */       if (mc.theWorld.getBlockState(new BlockPos(x, i, z)).getBlock() != Blocks.lava) {
/* 264 */         return true;
/*     */       }
/* 266 */       if (mc.theWorld.getBlockState(new BlockPos(x, i, z)).getBlock() != Blocks.flowing_lava) {
/* 267 */         return true;
/*     */       }
/* 269 */       if (mc.theWorld.getBlockState(new BlockPos(x, i, z)).getBlock() != Blocks.flowing_water) {
/* 270 */         return true;
/*     */       }
/* 272 */       if (mc.theWorld.getBlockState(new BlockPos(x, i, z)).getBlock() != Blocks.cactus) {
/* 273 */         return true;
/*     */       }
/* 275 */       if (mc.theWorld.getBlockState(new BlockPos(x, i, z)).getBlock() != Blocks.fire) {
/* 276 */         return true;
/*     */       }
/* 278 */       if (mc.theWorld.getBlockState(new BlockPos(x, i, z)).getBlock() != Blocks.web) {
/* 279 */         return true;
/*     */       }
/*     */     } 
/*     */     
/* 283 */     return false;
/*     */   }
/*     */   
/*     */   private int getClosestPoint(Entity target) {
/* 287 */     return ((Point)((List)getPoints(target).stream().sorted(Comparator.comparingDouble(Point::getDistanceToPlayer)).collect((Collector)Collectors.toList())).get(0)).poscount;
/*     */   }
/*     */   
/*     */   private boolean isCollided() {
/* 291 */     return (
/*     */       
/* 293 */       !mc.theWorld.getCollidingBoundingBoxes((Entity)mc.thePlayer, mc.thePlayer.getEntityBoundingBox().offset(0.0D, 0.0D, -0.5D)).isEmpty() || 
/*     */       
/* 295 */       !mc.theWorld.getCollidingBoundingBoxes((Entity)mc.thePlayer, mc.thePlayer.getEntityBoundingBox().offset(0.5D, 0.0D, 0.0D)).isEmpty() || 
/*     */       
/* 297 */       !mc.theWorld.getCollidingBoundingBoxes((Entity)mc.thePlayer, mc.thePlayer.getEntityBoundingBox().offset(0.0D, 0.0D, 0.5D)).isEmpty() || 
/*     */       
/* 299 */       !mc.theWorld.getCollidingBoundingBoxes((Entity)mc.thePlayer, mc.thePlayer.getEntityBoundingBox().offset(-0.5D, 0.0D, 0.0D)).isEmpty());
/*     */   }
/*     */   
/*     */   private boolean space() {
/* 303 */     return (!((Boolean)this.onlySpace.get()).booleanValue() || mc.gameSettings.keyBindJump.isKeyDown());
/*     */   }
/*     */   
/*     */   public boolean support() {
/* 307 */     return (space() && Speed.getInstance.isEnabled());
/*     */   }
/*     */   
/*     */   public boolean shouldTarget() {
/* 311 */     return (KillAura.getInstance.isEnabled() && KillAura.getInstance.getTarget() != null && mc.thePlayer.canEntityBeSeen((Entity)KillAura.getInstance.getTarget()) && (!((Boolean)this.target.get()).booleanValue() || 
/* 312 */       !Client.instance.friendManager.isFriend(KillAura.getInstance.getTarget().getName())) && mc.thePlayer
/*     */       
/* 314 */       .getDistance2D((Entity)KillAura.getInstance.getTarget()) < ((Double)KillAura.getInstance.range.get()).doubleValue() + 2.0D && mc.thePlayer.posY >= 
/* 315 */       (KillAura.getInstance.getTarget()).posY - 3.4D && KillAura.getInstance
/* 316 */       .getTarget().isEntityAlive() && mc.thePlayer
/* 317 */       .isMoving() && mc.thePlayer.posY <= 
/* 318 */       (KillAura.getInstance.getTarget()).posY + 3.4D && 
/* 319 */       support());
/*     */   }
/*     */   
/*     */   public void setInverted(boolean inverted) {
/* 323 */     this.inverted = inverted;
/*     */   }
/*     */   
/*     */   public boolean isInverted() {
/* 327 */     return this.inverted;
/*     */   }
/*     */   
/*     */   private List<Point> getPoints(Entity target) {
/* 331 */     List<Point> points = new CopyOnWriteArrayList<>();
/* 332 */     double rad = getRadius(target);
/* 333 */     int positionsCount = (int)((int)(Math.PI * rad) * ((Double)this.pointsMultiplier.get()).doubleValue());
/* 334 */     for (int i = 0; i <= positionsCount; i++) {
/* 335 */       double radianPerPosition = 6.283185307179586D / positionsCount;
/* 336 */       double posX = Math.sin(radianPerPosition * i) * rad;
/* 337 */       double posY = Math.cos(radianPerPosition * i) * rad;
/* 338 */       points.add(new Point(target.posX + posX, target.posZ + posY, i));
/*     */     } 
/* 340 */     return points;
/*     */   }
/*     */   
/*     */   public void renderExhiStrafeCircle(EntityPlayer entity, float partialTicks, float range, int color) {
/* 344 */     if (entity != mc.thePlayer && !entity.isInvisible() && mc.thePlayer.canEntityBeSeen((Entity)entity) && entity.isEntityAlive() && entity.getDistanceToEntity((Entity)mc.thePlayer) < 4.0F) {
/* 345 */       GL11.glPushMatrix();
/* 346 */       mc.entityRenderer.disableLightmap();
/* 347 */       GL11.glDisable(3553);
/* 348 */       GL11.glEnable(3042);
/* 349 */       GL11.glBlendFunc(770, 771);
/* 350 */       GL11.glDisable(2929);
/* 351 */       GL11.glEnable(2848);
/* 352 */       GL11.glDepthMask(false);
/*     */       
/* 354 */       double posX = entity.lastTickPosX + (entity.posX - entity.lastTickPosX) * partialTicks - (mc.getRenderManager()).viewerPosX;
/* 355 */       double posY = entity.lastTickPosY + (entity.posY - entity.lastTickPosY) * partialTicks - (mc.getRenderManager()).viewerPosY;
/* 356 */       double posZ = entity.lastTickPosZ + (entity.posZ - entity.lastTickPosZ) * partialTicks - (mc.getRenderManager()).viewerPosZ;
/*     */       
/* 358 */       GL11.glPushMatrix();
/* 359 */       GL11.glLineWidth(2.0F);
/* 360 */       if (this.render.is("CirclePoint")) {
/* 361 */         GL11.glEnable(2832);
/* 362 */         GL11.glPointSize(7.0F);
/* 363 */         GL11.glBegin(0);
/* 364 */         for (int i = 0; i <= 90; i++) {
/* 365 */           RenderUtil.color(color);
/* 366 */           GL11.glVertex3d(posX + range * Math.cos(i * Math.PI * 2.0D / 45.0D), posY, posZ + range * Math.sin(i * Math.PI * 2.0D / 45.0D));
/*     */         } 
/* 368 */         GL11.glEnd();
/*     */       } else {
/* 370 */         GL11.glBegin(1);
/* 371 */         for (int i = 0; i <= 90; i++) {
/* 372 */           RenderUtil.color(color);
/* 373 */           GL11.glVertex3d(posX + range * Math.cos(i * Math.PI * 2.0D / 45.0D), posY, posZ + range * Math.sin(i * Math.PI * 2.0D / 45.0D));
/*     */         } 
/* 375 */         GL11.glEnd();
/*     */       } 
/*     */       
/* 378 */       GL11.glPopMatrix();
/*     */       
/* 380 */       GL11.glDepthMask(true);
/* 381 */       GL11.glDisable(2848);
/* 382 */       GL11.glEnable(2929);
/* 383 */       GL11.glDisable(3042);
/* 384 */       GL11.glEnable(3553);
/* 385 */       mc.entityRenderer.enableLightmap();
/* 386 */       GL11.glPopMatrix();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   @EventHandler
/*     */   public void onRender3D(EventRender3D render3DEvent) {
/* 393 */     if (this.render.is("Matrix")) {
/* 394 */       Color color = Client.instance.getClientColorNoRainbowRGB(255);
/* 395 */       KillAura aura = KillAura.getInstance;
/* 396 */       RenderUtil.pre3D();
/*     */       
/* 398 */       if (aura.getTarget() != null && ((Boolean)this.points
/* 399 */         .get()).booleanValue() && mc.thePlayer
/* 400 */         .getDistanceToEntity((Entity)aura.getTarget()) < ((Double)aura.range.get()).doubleValue() && aura
/* 401 */         .getTarget().isEntityAlive()) {
/*     */         
/* 403 */         GL11.glColor4f(0.0F, 0.0F, 0.0F, 1.0F);
/* 404 */         renderCicle(5L, aura, render3DEvent);
/* 405 */         GL11.glColor4f(color.getRed() / 255.0F, color.getGreen() / 255.0F, color.getBlue() / 255.0F, 1.0F);
/* 406 */         renderCicle(2L, aura, render3DEvent);
/*     */       } 
/* 408 */       GlStateManager.disableBlend();
/* 409 */       RenderUtil.post3D();
/* 410 */     } else if (this.render.is("CirclePoint") || this.render.is("Circle")) {
/* 411 */       renderExhiStrafeCircle((EntityPlayer)KillAura.getInstance.getTarget(), render3DEvent.getPartialTicks(), ((Double)this.radius.get()).intValue(), Client.instance.getClientColor(255));
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private void renderCicle(long lw, KillAura aura, EventRender3D render3DEvent) {
/* 417 */     GL11.glLineWidth((float)lw);
/* 418 */     GL11.glBegin(3);
/* 419 */     double rad = getRadius((Entity)aura.getTarget());
/* 420 */     double piDivider = 6.283185307179586D / Math.PI * rad * ((Double)this.pointsMultiplier.get()).doubleValue(); double d;
/* 421 */     for (d = 0.0D; d < 6.283185307179586D; d += piDivider) {
/* 422 */       double d1 = (aura.getTarget()).lastTickPosX + ((aura.getTarget()).posX - (aura.getTarget()).lastTickPosX) * render3DEvent.getPartialTicks() + Math.sin(d) * rad - (mc.getRenderManager()).renderPosX;
/* 423 */       double d2 = (aura.getTarget()).lastTickPosY + ((aura.getTarget()).posY - (aura.getTarget()).lastTickPosY) * render3DEvent.getPartialTicks() - (mc.getRenderManager()).renderPosY;
/* 424 */       double d3 = (aura.getTarget()).lastTickPosZ + ((aura.getTarget()).posZ - (aura.getTarget()).lastTickPosZ) * render3DEvent.getPartialTicks() + Math.cos(d) * rad - (mc.getRenderManager()).renderPosZ;
/* 425 */       GL11.glVertex3d(d1, d2, d3);
/*     */     } 
/* 427 */     double x = (aura.getTarget()).lastTickPosX + ((aura.getTarget()).posX - (aura.getTarget()).lastTickPosX) * render3DEvent.getPartialTicks() + 0.0D * rad - (mc.getRenderManager()).renderPosX;
/* 428 */     double y = (aura.getTarget()).lastTickPosY + ((aura.getTarget()).posY - (aura.getTarget()).lastTickPosY) * render3DEvent.getPartialTicks() - (mc.getRenderManager()).renderPosY;
/* 429 */     double z = (aura.getTarget()).lastTickPosZ + ((aura.getTarget()).posZ - (aura.getTarget()).lastTickPosZ) * render3DEvent.getPartialTicks() + rad - (mc.getRenderManager()).renderPosZ;
/* 430 */     GL11.glVertex3d(x, y, z);
/* 431 */     GL11.glEnd();
/*     */   }
/*     */ 
/*     */   
/*     */   public static class Point
/*     */   {
/*     */     public final double x;
/*     */     public final double z;
/*     */     public final int poscount;
/*     */     
/*     */     public Point(double x, double z, int poscount) {
/* 442 */       this.x = x;
/* 443 */       this.z = z;
/* 444 */       this.poscount = poscount;
/*     */     }
/*     */     
/*     */     public double getDistanceToPlayer() {
/* 448 */       double x2 = Math.abs(InstanceAccess.mc.thePlayer.posX - this.x);
/* 449 */       double z2 = Math.abs(InstanceAccess.mc.thePlayer.posZ - this.z);
/* 450 */       return Math.sqrt(x2 * x2 + z2 * z2);
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\awareline\main\mod\implement\combat\TargetStrafe.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */