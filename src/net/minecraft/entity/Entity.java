/*      */ package net.minecraft.entity;
/*      */ import awareline.main.event.Event;
/*      */ import awareline.main.event.EventManager;
/*      */ import awareline.main.event.events.world.EventEntityBorderSize;
/*      */ import awareline.main.event.events.world.moveEvents.EventMove;
/*      */ import awareline.main.event.events.world.moveEvents.EventStrafe;
/*      */ import awareline.main.utility.vec.Vec3f;
/*      */ import java.util.List;
/*      */ import java.util.Random;
/*      */ import java.util.UUID;
/*      */ import java.util.concurrent.Callable;
/*      */ import net.minecraft.block.Block;
/*      */ import net.minecraft.block.material.Material;
/*      */ import net.minecraft.block.state.IBlockState;
/*      */ import net.minecraft.block.state.pattern.BlockPattern;
/*      */ import net.minecraft.client.Minecraft;
/*      */ import net.minecraft.command.CommandResultStats;
/*      */ import net.minecraft.crash.CrashReport;
/*      */ import net.minecraft.crash.CrashReportCategory;
/*      */ import net.minecraft.entity.item.EntityItem;
/*      */ import net.minecraft.entity.player.EntityPlayer;
/*      */ import net.minecraft.event.HoverEvent;
/*      */ import net.minecraft.init.Blocks;
/*      */ import net.minecraft.item.Item;
/*      */ import net.minecraft.item.ItemStack;
/*      */ import net.minecraft.nbt.NBTBase;
/*      */ import net.minecraft.nbt.NBTTagCompound;
/*      */ import net.minecraft.nbt.NBTTagList;
/*      */ import net.minecraft.server.MinecraftServer;
/*      */ import net.minecraft.util.AxisAlignedBB;
/*      */ import net.minecraft.util.BlockPos;
/*      */ import net.minecraft.util.ChatComponentText;
/*      */ import net.minecraft.util.DamageSource;
/*      */ import net.minecraft.util.EnumFacing;
/*      */ import net.minecraft.util.EnumParticleTypes;
/*      */ import net.minecraft.util.IChatComponent;
/*      */ import net.minecraft.util.MathHelper;
/*      */ import net.minecraft.util.MovingObjectPosition;
/*      */ import net.minecraft.util.ReportedException;
/*      */ import net.minecraft.util.Vec3;
/*      */ import net.minecraft.world.World;
/*      */ import net.minecraft.world.WorldServer;
/*      */ 
/*      */ public abstract class Entity implements ICommandSender {
/*   45 */   private static final AxisAlignedBB ZERO_AABB = new AxisAlignedBB(0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D);
/*      */ 
/*      */   
/*      */   private static int nextEntityID;
/*      */ 
/*      */   
/*      */   private final CommandResultStats cmdResultStats;
/*      */ 
/*      */   
/*      */   public double renderDistanceWeight;
/*      */ 
/*      */   
/*      */   public boolean preventEntitySpawning;
/*      */ 
/*      */   
/*      */   public Entity riddenByEntity;
/*      */ 
/*      */   
/*      */   public Entity ridingEntity;
/*      */ 
/*      */   
/*      */   public boolean forceSpawn;
/*      */ 
/*      */   
/*      */   public World worldObj;
/*      */ 
/*      */   
/*      */   public double prevPosX;
/*      */ 
/*      */   
/*      */   public double prevPosY;
/*      */ 
/*      */   
/*      */   public double prevPosZ;
/*      */ 
/*      */   
/*      */   public double posX;
/*      */ 
/*      */   
/*      */   public double posY;
/*      */ 
/*      */   
/*      */   public double posZ;
/*      */ 
/*      */   
/*      */   public double motionX;
/*      */ 
/*      */   
/*      */   public double motionY;
/*      */ 
/*      */   
/*      */   public double motionZ;
/*      */ 
/*      */   
/*      */   public float rotationYaw;
/*      */ 
/*      */   
/*      */   public float rotationPitch;
/*      */ 
/*      */   
/*      */   public float prevRotationYaw;
/*      */ 
/*      */   
/*      */   public float prevRotationPitch;
/*      */ 
/*      */   
/*      */   public AxisAlignedBB boundingBox;
/*      */ 
/*      */   
/*      */   public boolean onGround;
/*      */ 
/*      */   
/*      */   public boolean isCollidedHorizontally;
/*      */ 
/*      */   
/*      */   public boolean isCollidedVertically;
/*      */ 
/*      */   
/*      */   public boolean isCollided;
/*      */ 
/*      */   
/*      */   public boolean velocityChanged;
/*      */ 
/*      */   
/*      */   public boolean isInWeb;
/*      */ 
/*      */   
/*      */   public boolean isDead;
/*      */ 
/*      */   
/*      */   public float width;
/*      */ 
/*      */   
/*      */   public float height;
/*      */ 
/*      */   
/*      */   public float prevDistanceWalkedModified;
/*      */ 
/*      */   
/*      */   public float distanceWalkedModified;
/*      */ 
/*      */   
/*      */   public float distanceWalkedOnStepModified;
/*      */ 
/*      */   
/*      */   public float fallDistance;
/*      */ 
/*      */   
/*      */   public double lastTickPosX;
/*      */ 
/*      */   
/*      */   public double lastTickPosY;
/*      */ 
/*      */   
/*      */   public double lastTickPosZ;
/*      */ 
/*      */   
/*      */   public float stepHeight;
/*      */ 
/*      */   
/*      */   public boolean noClip;
/*      */ 
/*      */   
/*      */   public float entityCollisionReduction;
/*      */ 
/*      */   
/*      */   public int ticksExisted;
/*      */ 
/*      */   
/*      */   public int ticksSinceVelocity;
/*      */ 
/*      */   
/*      */   public int fireResistance;
/*      */ 
/*      */   
/*      */   public int hurtResistantTime;
/*      */ 
/*      */   
/*      */   public boolean addedToChunk;
/*      */ 
/*      */   
/*      */   public int chunkCoordX;
/*      */ 
/*      */   
/*      */   public int chunkCoordY;
/*      */ 
/*      */   
/*      */   public int chunkCoordZ;
/*      */ 
/*      */   
/*      */   public int serverPosX;
/*      */ 
/*      */   
/*      */   public int serverPosY;
/*      */ 
/*      */   
/*      */   public int serverPosZ;
/*      */ 
/*      */   
/*      */   public boolean ignoreFrustumCheck;
/*      */ 
/*      */   
/*      */   public boolean isAirBorne;
/*      */ 
/*      */   
/*      */   public int timeUntilPortal;
/*      */ 
/*      */   
/*      */   public int dimension;
/*      */ 
/*      */   
/*      */   protected Random rand;
/*      */ 
/*      */   
/*      */   protected boolean inWater;
/*      */ 
/*      */   
/*      */   protected boolean firstUpdate;
/*      */ 
/*      */   
/*      */   protected boolean isImmuneToFire;
/*      */ 
/*      */   
/*      */   protected DataWatcher dataWatcher;
/*      */ 
/*      */   
/*      */   protected boolean inPortal;
/*      */   
/*      */   protected int portalCounter;
/*      */   
/*      */   protected BlockPos lastPortalPos;
/*      */   
/*      */   protected Vec3 lastPortalVec;
/*      */   
/*      */   protected EnumFacing teleportDirection;
/*      */   
/*      */   protected UUID entityUniqueID;
/*      */   
/*      */   private int entityId;
/*      */   
/*      */   private boolean isOutsideBorder;
/*      */   
/*      */   private int nextStepDistance;
/*      */   
/*      */   private int fire;
/*      */   
/*      */   private double entityRiderPitchDelta;
/*      */   
/*      */   private double entityRiderYawDelta;
/*      */   
/*      */   private Vec3f pos;
/*      */   
/*      */   private boolean invulnerable;
/*      */   
/*      */   private Vec3f lastTickPos;
/*      */ 
/*      */   
/*      */   public Entity(World worldIn) {
/*  263 */     this.entityId = nextEntityID++;
/*  264 */     this.renderDistanceWeight = 1.0D;
/*  265 */     this.boundingBox = ZERO_AABB;
/*  266 */     this.width = 0.6F;
/*  267 */     this.height = 1.8F;
/*  268 */     this.nextStepDistance = 1;
/*  269 */     this.rand = new Random();
/*  270 */     this.fireResistance = 1;
/*  271 */     this.firstUpdate = true;
/*  272 */     this.entityUniqueID = MathHelper.getRandomUuid(this.rand);
/*  273 */     this.cmdResultStats = new CommandResultStats();
/*  274 */     this.worldObj = worldIn;
/*  275 */     setPosition(0.0D, 0.0D, 0.0D);
/*      */     
/*  277 */     if (worldIn != null) {
/*  278 */       this.dimension = worldIn.provider.getDimensionId();
/*      */     }
/*      */     
/*  281 */     this.dataWatcher = new DataWatcher(this);
/*  282 */     this.dataWatcher.addObject(0, Byte.valueOf((byte)0));
/*  283 */     this.dataWatcher.addObject(1, Short.valueOf((short)300));
/*  284 */     this.dataWatcher.addObject(3, Byte.valueOf((byte)0));
/*  285 */     this.dataWatcher.addObject(2, "");
/*  286 */     this.dataWatcher.addObject(4, Byte.valueOf((byte)0));
/*  287 */     entityInit();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected final Vec3 getVectorForRotation(float pitch, float yaw) {
/*  294 */     float f = MathHelper.cos(-yaw * 0.017453292F - 3.1415927F);
/*  295 */     float f1 = MathHelper.sin(-yaw * 0.017453292F - 3.1415927F);
/*  296 */     float f2 = -MathHelper.cos(-pitch * 0.017453292F);
/*  297 */     float f3 = MathHelper.sin(-pitch * 0.017453292F);
/*  298 */     return new Vec3((f1 * f2), f3, (f * f2));
/*      */   }
/*      */   
/*      */   public int getEntityId() {
/*  302 */     return this.entityId;
/*      */   }
/*      */   
/*      */   public void setEntityId(int id) {
/*  306 */     this.entityId = id;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void onKillCommand() {
/*  313 */     setDead();
/*      */   }
/*      */   
/*      */   protected abstract void entityInit();
/*      */   
/*      */   public DataWatcher getDataWatcher() {
/*  319 */     return this.dataWatcher;
/*      */   }
/*      */   
/*      */   public boolean equals(Object p_equals_1_) {
/*  323 */     return (p_equals_1_ instanceof Entity) ? ((((Entity)p_equals_1_).entityId == this.entityId)) : false;
/*      */   }
/*      */   
/*      */   public int hashCode() {
/*  327 */     return this.entityId;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void preparePlayerToSpawn() {
/*  335 */     if (this.worldObj != null) {
/*  336 */       while (this.posY > 0.0D && this.posY < 256.0D) {
/*  337 */         setPosition(this.posX, this.posY, this.posZ);
/*      */         
/*  339 */         if (this.worldObj.getCollidingBoundingBoxes(this, this.boundingBox).isEmpty()) {
/*      */           break;
/*      */         }
/*      */         
/*  343 */         this.posY++;
/*      */       } 
/*      */       
/*  346 */       this.motionX = this.motionY = this.motionZ = 0.0D;
/*  347 */       this.rotationPitch = 0.0F;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setDead() {
/*  355 */     this.isDead = true;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void setSize(float width, float height) {
/*  362 */     if (width != this.width || height != this.height) {
/*  363 */       float f = this.width;
/*  364 */       this.width = width;
/*  365 */       this.height = height;
/*  366 */       this.boundingBox = new AxisAlignedBB(this.boundingBox.minX, this.boundingBox.minY, this.boundingBox.minZ, this.boundingBox.minX + this.width, this.boundingBox.minY + this.height, this.boundingBox.minZ + this.width);
/*      */       
/*  368 */       if (this.width > f && !this.firstUpdate && !this.worldObj.isRemote) {
/*  369 */         moveEntity((f - this.width), 0.0D, (f - this.width));
/*      */       }
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void setRotation(float yaw, float pitch) {
/*  378 */     this.rotationYaw = yaw % 360.0F;
/*  379 */     this.rotationPitch = pitch % 360.0F;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setPosition(double x, double y, double z) {
/*  386 */     this.posX = x;
/*  387 */     this.posY = y;
/*  388 */     this.posZ = z;
/*  389 */     float f = this.width / 2.0F;
/*  390 */     float f1 = this.height;
/*  391 */     this.boundingBox = new AxisAlignedBB(x - f, y, z - f, x + f, y + f1, z + f);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setAngles(float yaw, float pitch) {
/*  399 */     float f = this.rotationPitch;
/*  400 */     float f1 = this.rotationYaw;
/*  401 */     this.rotationYaw = (float)(this.rotationYaw + yaw * 0.15D);
/*  402 */     this.rotationPitch = (float)(this.rotationPitch - pitch * 0.15D);
/*  403 */     this.rotationPitch = MathHelper.clamp_float(this.rotationPitch, -90.0F, 90.0F);
/*  404 */     this.prevRotationPitch += this.rotationPitch - f;
/*  405 */     this.prevRotationYaw += this.rotationYaw - f1;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void onUpdate() {
/*  412 */     onEntityUpdate();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void onEntityUpdate() {
/*  419 */     this.worldObj.theProfiler.startSection("entityBaseTick");
/*      */     
/*  421 */     if (this.ridingEntity != null && this.ridingEntity.isDead) {
/*  422 */       this.ridingEntity = null;
/*      */     }
/*      */     
/*  425 */     this.prevDistanceWalkedModified = this.distanceWalkedModified;
/*  426 */     this.prevPosX = this.posX;
/*  427 */     this.prevPosY = this.posY;
/*  428 */     this.prevPosZ = this.posZ;
/*  429 */     this.prevRotationPitch = this.rotationPitch;
/*  430 */     this.prevRotationYaw = this.rotationYaw;
/*      */     
/*  432 */     if (!this.worldObj.isRemote && this.worldObj instanceof WorldServer) {
/*  433 */       this.worldObj.theProfiler.startSection("portal");
/*  434 */       MinecraftServer minecraftserver = ((WorldServer)this.worldObj).getMinecraftServer();
/*  435 */       int i = getMaxInPortalTime();
/*      */       
/*  437 */       if (this.inPortal) {
/*  438 */         if (minecraftserver.getAllowNether()) {
/*  439 */           if (this.ridingEntity == null && this.portalCounter++ >= i) {
/*  440 */             int j; this.portalCounter = i;
/*  441 */             this.timeUntilPortal = getPortalCooldown();
/*      */ 
/*      */             
/*  444 */             if (this.worldObj.provider.getDimensionId() == -1) {
/*  445 */               j = 0;
/*      */             } else {
/*  447 */               j = -1;
/*      */             } 
/*      */             
/*  450 */             travelToDimension(j);
/*      */           } 
/*      */           
/*  453 */           this.inPortal = false;
/*      */         } 
/*      */       } else {
/*  456 */         if (this.portalCounter > 0) {
/*  457 */           this.portalCounter -= 4;
/*      */         }
/*      */         
/*  460 */         if (this.portalCounter < 0) {
/*  461 */           this.portalCounter = 0;
/*      */         }
/*      */       } 
/*      */       
/*  465 */       if (this.timeUntilPortal > 0) {
/*  466 */         this.timeUntilPortal--;
/*      */       }
/*      */       
/*  469 */       this.worldObj.theProfiler.endSection();
/*      */     } 
/*      */     
/*  472 */     spawnRunningParticles();
/*  473 */     handleWaterMovement();
/*      */     
/*  475 */     if (this.worldObj.isRemote) {
/*  476 */       this.fire = 0;
/*  477 */     } else if (this.fire > 0) {
/*  478 */       if (this.isImmuneToFire) {
/*  479 */         this.fire -= 4;
/*      */         
/*  481 */         if (this.fire < 0) {
/*  482 */           this.fire = 0;
/*      */         }
/*      */       } else {
/*  485 */         if (this.fire % 20 == 0) {
/*  486 */           attackEntityFrom(DamageSource.onFire, 1.0F);
/*      */         }
/*      */         
/*  489 */         this.fire--;
/*      */       } 
/*      */     } 
/*      */     
/*  493 */     if (isInLava()) {
/*  494 */       setOnFireFromLava();
/*  495 */       this.fallDistance *= 0.5F;
/*      */     } 
/*      */     
/*  498 */     if (this.posY < -64.0D) {
/*  499 */       kill();
/*      */     }
/*      */     
/*  502 */     if (!this.worldObj.isRemote) {
/*  503 */       setFlag(0, (this.fire > 0));
/*      */     }
/*      */     
/*  506 */     this.firstUpdate = false;
/*  507 */     this.worldObj.theProfiler.endSection();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getMaxInPortalTime() {
/*  514 */     return 0;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void setOnFireFromLava() {
/*  521 */     if (!this.isImmuneToFire) {
/*  522 */       attackEntityFrom(DamageSource.lava, 4.0F);
/*  523 */       setFire(15);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setFire(int seconds) {
/*  531 */     int i = seconds * 20;
/*  532 */     i = EnchantmentProtection.getFireTimeForEntity(this, i);
/*      */     
/*  534 */     if (this.fire < i) {
/*  535 */       this.fire = i;
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void extinguish() {
/*  543 */     this.fire = 0;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void kill() {
/*  550 */     setDead();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isOffsetPositionInLiquid(double x, double y, double z) {
/*  557 */     AxisAlignedBB axisalignedbb = this.boundingBox.offset(x, y, z);
/*  558 */     return isLiquidPresentInAABB(axisalignedbb);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean isLiquidPresentInAABB(AxisAlignedBB bb) {
/*  565 */     return (this.worldObj.getCollidingBoundingBoxes(this, bb).isEmpty() && !this.worldObj.isAnyLiquid(bb));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void moveEntity(double x, double y, double z) {
/*  573 */     if (this == (Minecraft.getMinecraft()).thePlayer) {
/*  574 */       EventMove moveEvent = new EventMove(x, y, z);
/*  575 */       EventManager.call((Event)moveEvent);
/*  576 */       if (moveEvent.isCancelled()) {
/*      */         return;
/*      */       }
/*  579 */       x = moveEvent.getX();
/*  580 */       y = moveEvent.getY();
/*  581 */       z = moveEvent.getZ();
/*      */     } 
/*  583 */     if (this.noClip) {
/*  584 */       this.boundingBox = this.boundingBox.offset(x, y, z);
/*  585 */       resetPositionToBB();
/*      */     } else {
/*  587 */       this.worldObj.theProfiler.startSection("move");
/*  588 */       double d0 = this.posX;
/*  589 */       double d1 = this.posY;
/*  590 */       double d2 = this.posZ;
/*      */       
/*  592 */       if (this.isInWeb) {
/*  593 */         this.isInWeb = false;
/*  594 */         x *= 0.25D;
/*  595 */         y *= 0.05000000074505806D;
/*  596 */         z *= 0.25D;
/*  597 */         this.motionX = 0.0D;
/*  598 */         this.motionY = 0.0D;
/*  599 */         this.motionZ = 0.0D;
/*      */       } 
/*      */       
/*  602 */       double d3 = x;
/*  603 */       double d4 = y;
/*  604 */       double d5 = z;
/*  605 */       boolean flag = (this.onGround && isSneaking() && this instanceof EntityPlayer && this.onGround);
/*  606 */       if (SafeWalkUtil.flag) {
/*  607 */         flag = true;
/*      */       }
/*  609 */       if (flag) {
/*      */         double d6;
/*      */         
/*  612 */         for (d6 = 0.05D; x != 0.0D && this.worldObj.getCollidingBoundingBoxes(this, this.boundingBox.offset(x, -1.0D, 0.0D)).isEmpty(); d3 = x) {
/*  613 */           if (x < d6 && x >= -d6) {
/*  614 */             x = 0.0D;
/*  615 */           } else if (x > 0.0D) {
/*  616 */             x -= d6;
/*      */           } else {
/*  618 */             x += d6;
/*      */           } 
/*      */         } 
/*      */         
/*  622 */         for (; z != 0.0D && this.worldObj.getCollidingBoundingBoxes(this, this.boundingBox.offset(0.0D, -1.0D, z)).isEmpty(); d5 = z) {
/*  623 */           if (z < d6 && z >= -d6) {
/*  624 */             z = 0.0D;
/*  625 */           } else if (z > 0.0D) {
/*  626 */             z -= d6;
/*      */           } else {
/*  628 */             z += d6;
/*      */           } 
/*      */         } 
/*      */         
/*  632 */         for (; x != 0.0D && z != 0.0D && this.worldObj.getCollidingBoundingBoxes(this, this.boundingBox.offset(x, -1.0D, z)).isEmpty(); d5 = z) {
/*  633 */           if (x < d6 && x >= -d6) {
/*  634 */             x = 0.0D;
/*  635 */           } else if (x > 0.0D) {
/*  636 */             x -= d6;
/*      */           } else {
/*  638 */             x += d6;
/*      */           } 
/*      */           
/*  641 */           d3 = x;
/*      */           
/*  643 */           if (z < d6 && z >= -d6) {
/*  644 */             z = 0.0D;
/*  645 */           } else if (z > 0.0D) {
/*  646 */             z -= d6;
/*      */           } else {
/*  648 */             z += d6;
/*      */           } 
/*      */         } 
/*      */       } 
/*      */       
/*  653 */       List<AxisAlignedBB> list1 = this.worldObj.getCollidingBoundingBoxes(this, this.boundingBox.addCoord(x, y, z));
/*  654 */       AxisAlignedBB axisalignedbb = this.boundingBox;
/*      */       
/*  656 */       for (AxisAlignedBB axisalignedbb1 : list1) {
/*  657 */         y = axisalignedbb1.calculateYOffset(this.boundingBox, y);
/*      */       }
/*      */       
/*  660 */       this.boundingBox = this.boundingBox.offset(0.0D, y, 0.0D);
/*  661 */       boolean flag1 = (this.onGround || (d4 != y && d4 < 0.0D));
/*      */       
/*  663 */       for (AxisAlignedBB axisalignedbb2 : list1) {
/*  664 */         x = axisalignedbb2.calculateXOffset(this.boundingBox, x);
/*      */       }
/*      */       
/*  667 */       this.boundingBox = this.boundingBox.offset(x, 0.0D, 0.0D);
/*      */       
/*  669 */       for (AxisAlignedBB axisalignedbb13 : list1) {
/*  670 */         z = axisalignedbb13.calculateZOffset(this.boundingBox, z);
/*      */       }
/*      */       
/*  673 */       this.boundingBox = this.boundingBox.offset(0.0D, 0.0D, z);
/*      */       
/*  675 */       if (this.stepHeight > 0.0F && flag1 && (d3 != x || d5 != z)) {
/*  676 */         double d11 = x;
/*  677 */         double d7 = y;
/*  678 */         double d8 = z;
/*  679 */         AxisAlignedBB axisalignedbb3 = this.boundingBox;
/*  680 */         this.boundingBox = axisalignedbb;
/*  681 */         y = this.stepHeight;
/*  682 */         List<AxisAlignedBB> list = this.worldObj.getCollidingBoundingBoxes(this, this.boundingBox.addCoord(d3, y, d5));
/*  683 */         AxisAlignedBB axisalignedbb4 = this.boundingBox;
/*  684 */         AxisAlignedBB axisalignedbb5 = axisalignedbb4.addCoord(d3, 0.0D, d5);
/*  685 */         double d9 = y;
/*      */         
/*  687 */         for (AxisAlignedBB axisalignedbb6 : list) {
/*  688 */           d9 = axisalignedbb6.calculateYOffset(axisalignedbb5, d9);
/*      */         }
/*      */         
/*  691 */         axisalignedbb4 = axisalignedbb4.offset(0.0D, d9, 0.0D);
/*  692 */         double d15 = d3;
/*      */         
/*  694 */         for (AxisAlignedBB axisalignedbb7 : list) {
/*  695 */           d15 = axisalignedbb7.calculateXOffset(axisalignedbb4, d15);
/*      */         }
/*      */         
/*  698 */         axisalignedbb4 = axisalignedbb4.offset(d15, 0.0D, 0.0D);
/*  699 */         double d16 = d5;
/*      */         
/*  701 */         for (AxisAlignedBB axisalignedbb8 : list) {
/*  702 */           d16 = axisalignedbb8.calculateZOffset(axisalignedbb4, d16);
/*      */         }
/*      */         
/*  705 */         axisalignedbb4 = axisalignedbb4.offset(0.0D, 0.0D, d16);
/*  706 */         AxisAlignedBB axisalignedbb14 = this.boundingBox;
/*  707 */         double d17 = y;
/*      */         
/*  709 */         for (AxisAlignedBB axisalignedbb9 : list) {
/*  710 */           d17 = axisalignedbb9.calculateYOffset(axisalignedbb14, d17);
/*      */         }
/*      */         
/*  713 */         axisalignedbb14 = axisalignedbb14.offset(0.0D, d17, 0.0D);
/*  714 */         double d18 = d3;
/*      */         
/*  716 */         for (AxisAlignedBB axisalignedbb10 : list) {
/*  717 */           d18 = axisalignedbb10.calculateXOffset(axisalignedbb14, d18);
/*      */         }
/*      */         
/*  720 */         axisalignedbb14 = axisalignedbb14.offset(d18, 0.0D, 0.0D);
/*  721 */         double d19 = d5;
/*      */         
/*  723 */         for (AxisAlignedBB axisalignedbb11 : list) {
/*  724 */           d19 = axisalignedbb11.calculateZOffset(axisalignedbb14, d19);
/*      */         }
/*      */         
/*  727 */         axisalignedbb14 = axisalignedbb14.offset(0.0D, 0.0D, d19);
/*  728 */         double d20 = d15 * d15 + d16 * d16;
/*  729 */         double d10 = d18 * d18 + d19 * d19;
/*      */         
/*  731 */         if (d20 > d10) {
/*  732 */           x = d15;
/*  733 */           z = d16;
/*  734 */           y = -d9;
/*  735 */           this.boundingBox = axisalignedbb4;
/*      */         } else {
/*  737 */           x = d18;
/*  738 */           z = d19;
/*  739 */           y = -d17;
/*  740 */           this.boundingBox = axisalignedbb14;
/*      */         } 
/*      */         
/*  743 */         for (AxisAlignedBB axisalignedbb12 : list) {
/*  744 */           y = axisalignedbb12.calculateYOffset(this.boundingBox, y);
/*      */         }
/*      */         
/*  747 */         this.boundingBox = this.boundingBox.offset(0.0D, y, 0.0D);
/*      */         
/*  749 */         if (d11 * d11 + d8 * d8 >= x * x + z * z) {
/*  750 */           x = d11;
/*  751 */           y = d7;
/*  752 */           z = d8;
/*  753 */           this.boundingBox = axisalignedbb3;
/*  754 */         } else if (this.onGround && this == (Minecraft.getMinecraft()).thePlayer) {
/*  755 */           EventManager.call((Event)new EventStep(this.stepHeight));
/*      */         } 
/*      */       } 
/*      */       
/*  759 */       this.worldObj.theProfiler.endSection();
/*  760 */       this.worldObj.theProfiler.startSection("rest");
/*  761 */       resetPositionToBB();
/*  762 */       this.isCollidedHorizontally = (d3 != x || d5 != z);
/*  763 */       this.isCollidedVertically = (d4 != y);
/*  764 */       this.onGround = (this.isCollidedVertically && d4 < 0.0D);
/*  765 */       this.isCollided = (this.isCollidedHorizontally || this.isCollidedVertically);
/*  766 */       int i = MathHelper.floor_double(this.posX);
/*  767 */       int j = MathHelper.floor_double(this.posY - 0.20000000298023224D);
/*  768 */       int k = MathHelper.floor_double(this.posZ);
/*  769 */       BlockPos blockpos = new BlockPos(i, j, k);
/*  770 */       Block block1 = this.worldObj.getBlockState(blockpos).getBlock();
/*      */       
/*  772 */       if (block1.getMaterial() == Material.air) {
/*  773 */         Block block = this.worldObj.getBlockState(blockpos.down()).getBlock();
/*      */         
/*  775 */         if (block instanceof net.minecraft.block.BlockFence || block instanceof net.minecraft.block.BlockWall || block instanceof net.minecraft.block.BlockFenceGate) {
/*  776 */           block1 = block;
/*  777 */           blockpos = blockpos.down();
/*      */         } 
/*      */       } 
/*      */       
/*  781 */       updateFallState(y, this.onGround, block1, blockpos);
/*      */       
/*  783 */       if (d3 != x) {
/*  784 */         this.motionX = 0.0D;
/*      */       }
/*      */       
/*  787 */       if (d5 != z) {
/*  788 */         this.motionZ = 0.0D;
/*      */       }
/*      */       
/*  791 */       if (d4 != y) {
/*  792 */         block1.onLanded(this.worldObj, this);
/*      */       }
/*      */       
/*  795 */       if (canTriggerWalking() && (!flag || SafeWalkUtil.flag) && this.ridingEntity == null) {
/*  796 */         double d12 = this.posX - d0;
/*  797 */         double d13 = this.posY - d1;
/*  798 */         double d14 = this.posZ - d2;
/*      */         
/*  800 */         if (block1 != Blocks.ladder) {
/*  801 */           d13 = 0.0D;
/*      */         }
/*      */         
/*  804 */         if (block1 != null && this.onGround) {
/*  805 */           block1.onEntityCollidedWithBlock(this.worldObj, blockpos, this);
/*      */         }
/*      */         
/*  808 */         this.distanceWalkedModified = (float)(this.distanceWalkedModified + MathHelper.sqrt_double(d12 * d12 + d14 * d14) * 0.6D);
/*  809 */         this.distanceWalkedOnStepModified = (float)(this.distanceWalkedOnStepModified + MathHelper.sqrt_double(d12 * d12 + d13 * d13 + d14 * d14) * 0.6D);
/*      */         
/*  811 */         if (this.distanceWalkedOnStepModified > this.nextStepDistance && block1.getMaterial() != Material.air) {
/*  812 */           this.nextStepDistance = (int)this.distanceWalkedOnStepModified + 1;
/*      */           
/*  814 */           if (isInWater()) {
/*  815 */             float f = MathHelper.sqrt_double(this.motionX * this.motionX * 0.20000000298023224D + this.motionY * this.motionY + this.motionZ * this.motionZ * 0.20000000298023224D) * 0.35F;
/*      */             
/*  817 */             if (f > 1.0F) {
/*  818 */               f = 1.0F;
/*      */             }
/*      */             
/*  821 */             playSound(getSwimSound(), f, 1.0F + (this.rand.nextFloat() - this.rand.nextFloat()) * 0.4F);
/*      */           } 
/*      */           
/*  824 */           playStepSound(blockpos, block1);
/*      */         } 
/*      */       } 
/*      */       
/*      */       try {
/*  829 */         doBlockCollisions();
/*  830 */       } catch (Throwable throwable) {
/*  831 */         CrashReport crashreport = CrashReport.makeCrashReport(throwable, "Checking entity block collision");
/*  832 */         CrashReportCategory crashreportcategory = crashreport.makeCategory("Entity being checked for collision");
/*  833 */         addEntityCrashInfo(crashreportcategory);
/*  834 */         throw new ReportedException(crashreport);
/*      */       } 
/*      */       
/*  837 */       boolean flag2 = isWet();
/*      */       
/*  839 */       if (this.worldObj.isFlammableWithin(this.boundingBox.contract(0.001D, 0.001D, 0.001D))) {
/*  840 */         dealFireDamage(1);
/*      */         
/*  842 */         if (!flag2) {
/*  843 */           this.fire++;
/*      */           
/*  845 */           if (this.fire == 0) {
/*  846 */             setFire(8);
/*      */           }
/*      */         } 
/*  849 */       } else if (this.fire <= 0) {
/*  850 */         this.fire = -this.fireResistance;
/*      */       } 
/*      */       
/*  853 */       if (flag2 && this.fire > 0) {
/*  854 */         playSound("random.fizz", 0.7F, 1.6F + (this.rand.nextFloat() - this.rand.nextFloat()) * 0.4F);
/*  855 */         this.fire = -this.fireResistance;
/*      */       } 
/*      */       
/*  858 */       this.worldObj.theProfiler.endSection();
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void resetPositionToBB() {
/*  866 */     this.posX = (this.boundingBox.minX + this.boundingBox.maxX) / 2.0D;
/*  867 */     this.posY = this.boundingBox.minY;
/*  868 */     this.posZ = (this.boundingBox.minZ + this.boundingBox.maxZ) / 2.0D;
/*      */   }
/*      */   
/*      */   protected String getSwimSound() {
/*  872 */     return "game.neutral.swim";
/*      */   }
/*      */   
/*      */   protected void doBlockCollisions() {
/*  876 */     BlockPos blockpos = new BlockPos(this.boundingBox.minX + 0.001D, this.boundingBox.minY + 0.001D, this.boundingBox.minZ + 0.001D);
/*  877 */     BlockPos blockpos1 = new BlockPos(this.boundingBox.maxX - 0.001D, this.boundingBox.maxY - 0.001D, this.boundingBox.maxZ - 0.001D);
/*      */     
/*  879 */     if (this.worldObj.isAreaLoaded(blockpos, blockpos1)) {
/*  880 */       for (int i = blockpos.getX(); i <= blockpos1.getX(); i++) {
/*  881 */         for (int j = blockpos.getY(); j <= blockpos1.getY(); j++) {
/*  882 */           for (int k = blockpos.getZ(); k <= blockpos1.getZ(); k++) {
/*  883 */             BlockPos blockpos2 = new BlockPos(i, j, k);
/*  884 */             IBlockState iblockstate = this.worldObj.getBlockState(blockpos2);
/*      */             
/*      */             try {
/*  887 */               iblockstate.getBlock().onEntityCollidedWithBlock(this.worldObj, blockpos2, iblockstate, this);
/*  888 */             } catch (Throwable throwable) {
/*  889 */               CrashReport crashreport = CrashReport.makeCrashReport(throwable, "Colliding entity with block");
/*  890 */               CrashReportCategory crashreportcategory = crashreport.makeCategory("Block being collided with");
/*  891 */               CrashReportCategory.addBlockInfo(crashreportcategory, blockpos2, iblockstate);
/*  892 */               throw new ReportedException(crashreport);
/*      */             } 
/*      */           } 
/*      */         } 
/*      */       } 
/*      */     }
/*      */   }
/*      */   
/*      */   protected void playStepSound(BlockPos pos, Block blockIn) {
/*  901 */     Block.SoundType block$soundtype = blockIn.stepSound;
/*      */     
/*  903 */     if (this.worldObj.getBlockState(pos.up()).getBlock() == Blocks.snow_layer) {
/*  904 */       block$soundtype = Blocks.snow_layer.stepSound;
/*  905 */       playSound(block$soundtype.getStepSound(), block$soundtype.getVolume() * 0.15F, block$soundtype.getFrequency());
/*  906 */     } else if (!blockIn.getMaterial().isLiquid()) {
/*  907 */       playSound(block$soundtype.getStepSound(), block$soundtype.getVolume() * 0.15F, block$soundtype.getFrequency());
/*      */     } 
/*      */   }
/*      */   
/*      */   public void playSound(String name, float volume, float pitch) {
/*  912 */     if (!isSilent()) {
/*  913 */       this.worldObj.playSoundAtEntity(this, name, volume, pitch);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isSilent() {
/*  921 */     return (this.dataWatcher.getWatchableObjectByte(4) == 1);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setSilent(boolean isSilent) {
/*  928 */     this.dataWatcher.updateObject(4, Byte.valueOf((byte)(isSilent ? 1 : 0)));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected boolean canTriggerWalking() {
/*  936 */     return true;
/*      */   }
/*      */   
/*      */   protected void updateFallState(double y, boolean onGroundIn, Block blockIn, BlockPos pos) {
/*  940 */     if (onGroundIn) {
/*  941 */       if (this.fallDistance > 0.0F) {
/*  942 */         if (blockIn != null) {
/*  943 */           blockIn.onFallenUpon(this.worldObj, pos, this, this.fallDistance);
/*      */         } else {
/*  945 */           fall(this.fallDistance, 1.0F);
/*      */         } 
/*      */         
/*  948 */         this.fallDistance = 0.0F;
/*      */       } 
/*  950 */     } else if (y < 0.0D) {
/*  951 */       this.fallDistance = (float)(this.fallDistance - y);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public AxisAlignedBB getCollisionBoundingBox() {
/*  959 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void dealFireDamage(int amount) {
/*  967 */     if (!this.isImmuneToFire) {
/*  968 */       attackEntityFrom(DamageSource.inFire, amount);
/*      */     }
/*      */   }
/*      */   
/*      */   public final boolean isImmuneToFire() {
/*  973 */     return this.isImmuneToFire;
/*      */   }
/*      */   
/*      */   public void fall(float distance, float damageMultiplier) {
/*  977 */     if (this.riddenByEntity != null) {
/*  978 */       this.riddenByEntity.fall(distance, damageMultiplier);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isWet() {
/*  986 */     return (this.inWater || this.worldObj.isRainingAt(new BlockPos(this.posX, this.posY, this.posZ)) || this.worldObj.isRainingAt(new BlockPos(this.posX, this.posY + this.height, this.posZ)));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isInWater() {
/*  994 */     return this.inWater;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean handleWaterMovement() {
/* 1001 */     if (this.worldObj.handleMaterialAcceleration(this.boundingBox.expand(0.0D, -0.4000000059604645D, 0.0D).contract(0.001D, 0.001D, 0.001D), Material.water, this)) {
/* 1002 */       if (!this.inWater && !this.firstUpdate) {
/* 1003 */         resetHeight();
/*      */       }
/*      */       
/* 1006 */       this.fallDistance = 0.0F;
/* 1007 */       this.inWater = true;
/* 1008 */       this.fire = 0;
/*      */     } else {
/* 1010 */       this.inWater = false;
/*      */     } 
/*      */     
/* 1013 */     return this.inWater;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void resetHeight() {
/* 1020 */     float f = MathHelper.sqrt_double(this.motionX * this.motionX * 0.20000000298023224D + this.motionY * this.motionY + this.motionZ * this.motionZ * 0.20000000298023224D) * 0.2F;
/*      */     
/* 1022 */     if (f > 1.0F) {
/* 1023 */       f = 1.0F;
/*      */     }
/*      */     
/* 1026 */     playSound(getSplashSound(), f, 1.0F + (this.rand.nextFloat() - this.rand.nextFloat()) * 0.4F);
/* 1027 */     float f1 = MathHelper.floor_double(this.boundingBox.minY);
/*      */     
/* 1029 */     for (int i = 0; i < 1.0F + this.width * 20.0F; i++) {
/* 1030 */       float f2 = (this.rand.nextFloat() * 2.0F - 1.0F) * this.width;
/* 1031 */       float f3 = (this.rand.nextFloat() * 2.0F - 1.0F) * this.width;
/* 1032 */       this.worldObj.spawnParticle(EnumParticleTypes.WATER_BUBBLE, this.posX + f2, (f1 + 1.0F), this.posZ + f3, this.motionX, this.motionY - (this.rand.nextFloat() * 0.2F), this.motionZ, new int[0]);
/*      */     } 
/*      */     
/* 1035 */     for (int j = 0; j < 1.0F + this.width * 20.0F; j++) {
/* 1036 */       float f4 = (this.rand.nextFloat() * 2.0F - 1.0F) * this.width;
/* 1037 */       float f5 = (this.rand.nextFloat() * 2.0F - 1.0F) * this.width;
/* 1038 */       this.worldObj.spawnParticle(EnumParticleTypes.WATER_SPLASH, this.posX + f4, (f1 + 1.0F), this.posZ + f5, this.motionX, this.motionY, this.motionZ, new int[0]);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void spawnRunningParticles() {
/* 1046 */     if (isSprinting() && !isInWater()) {
/* 1047 */       createRunningParticles();
/*      */     }
/*      */   }
/*      */   
/*      */   protected void createRunningParticles() {
/* 1052 */     int i = MathHelper.floor_double(this.posX);
/* 1053 */     int j = MathHelper.floor_double(this.posY - 0.20000000298023224D);
/* 1054 */     int k = MathHelper.floor_double(this.posZ);
/* 1055 */     BlockPos blockpos = new BlockPos(i, j, k);
/* 1056 */     IBlockState iblockstate = this.worldObj.getBlockState(blockpos);
/* 1057 */     Block block = iblockstate.getBlock();
/*      */     
/* 1059 */     if (block.getRenderType() != -1) {
/* 1060 */       this.worldObj.spawnParticle(EnumParticleTypes.BLOCK_CRACK, this.posX + (this.rand.nextFloat() - 0.5D) * this.width, this.boundingBox.minY + 0.1D, this.posZ + (this.rand.nextFloat() - 0.5D) * this.width, -this.motionX * 4.0D, 1.5D, -this.motionZ * 4.0D, new int[] { Block.getStateId(iblockstate) });
/*      */     }
/*      */   }
/*      */   
/*      */   protected String getSplashSound() {
/* 1065 */     return "game.neutral.swim.splash";
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isInsideOfMaterial(Material materialIn) {
/* 1072 */     double d0 = this.posY + getEyeHeight();
/* 1073 */     BlockPos blockpos = new BlockPos(this.posX, d0, this.posZ);
/* 1074 */     IBlockState iblockstate = this.worldObj.getBlockState(blockpos);
/* 1075 */     Block block = iblockstate.getBlock();
/*      */     
/* 1077 */     if (block.getMaterial() == materialIn) {
/* 1078 */       float f = BlockLiquid.getLiquidHeightPercent(iblockstate.getBlock().getMetaFromState(iblockstate)) - 0.11111111F;
/* 1079 */       float f1 = (blockpos.getY() + 1) - f;
/* 1080 */       boolean flag = (d0 < f1);
/* 1081 */       return (!flag && this instanceof EntityPlayer) ? false : flag;
/*      */     } 
/* 1083 */     return false;
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean isInLava() {
/* 1088 */     return this.worldObj.isMaterialInBB(this.boundingBox.expand(-0.10000000149011612D, -0.4000000059604645D, -0.10000000149011612D), Material.lava);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void moveFlying(float strafe, float forward, float friction) {
/* 1095 */     if (this == (Minecraft.getMinecraft()).thePlayer) {
/* 1096 */       EventStrafe strafeEvent = new EventStrafe(strafe, forward, friction);
/* 1097 */       EventManager.call((Event)strafeEvent);
/* 1098 */       if (strafeEvent.isCancelled()) {
/*      */         return;
/*      */       }
/*      */     } 
/* 1102 */     float f = strafe * strafe + forward * forward;
/*      */     
/* 1104 */     if (f >= 1.0E-4F) {
/* 1105 */       f = MathHelper.sqrt_float(f);
/*      */       
/* 1107 */       if (f < 1.0F) {
/* 1108 */         f = 1.0F;
/*      */       }
/*      */       
/* 1111 */       f = friction / f;
/* 1112 */       strafe *= f;
/* 1113 */       forward *= f;
/* 1114 */       float f1 = MathHelper.sin(this.rotationYaw * 3.1415927F / 180.0F);
/* 1115 */       float f2 = MathHelper.cos(this.rotationYaw * 3.1415927F / 180.0F);
/* 1116 */       this.motionX += (strafe * f2 - forward * f1);
/* 1117 */       this.motionZ += (forward * f2 + strafe * f1);
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public int getBrightnessForRender(float partialTicks) {
/* 1123 */     BlockPos blockpos = new BlockPos(this.posX, this.posY + getEyeHeight(), this.posZ);
/* 1124 */     return this.worldObj.isBlockLoaded(blockpos) ? this.worldObj.getCombinedLight(blockpos, 0) : 0;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public float getBrightness(float partialTicks) {
/* 1131 */     BlockPos blockpos = new BlockPos(this.posX, this.posY + getEyeHeight(), this.posZ);
/* 1132 */     return this.worldObj.isBlockLoaded(blockpos) ? this.worldObj.getLightBrightness(blockpos) : 0.0F;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setWorld(World worldIn) {
/* 1139 */     this.worldObj = worldIn;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setPositionAndRotation(double x, double y, double z, float yaw, float pitch) {
/* 1146 */     this.prevPosX = this.posX = x;
/* 1147 */     this.prevPosY = this.posY = y;
/* 1148 */     this.prevPosZ = this.posZ = z;
/* 1149 */     this.prevRotationYaw = this.rotationYaw = yaw;
/* 1150 */     this.prevRotationPitch = this.rotationPitch = pitch;
/* 1151 */     double d0 = (this.prevRotationYaw - yaw);
/*      */     
/* 1153 */     if (d0 < -180.0D) {
/* 1154 */       this.prevRotationYaw += 360.0F;
/*      */     }
/*      */     
/* 1157 */     if (d0 >= 180.0D) {
/* 1158 */       this.prevRotationYaw -= 360.0F;
/*      */     }
/*      */     
/* 1161 */     setPosition(this.posX, this.posY, this.posZ);
/* 1162 */     setRotation(yaw, pitch);
/*      */   }
/*      */   
/*      */   public void moveToBlockPosAndAngles(BlockPos pos, float rotationYawIn, float rotationPitchIn) {
/* 1166 */     setLocationAndAngles(pos.getX() + 0.5D, pos.getY(), pos.getZ() + 0.5D, rotationYawIn, rotationPitchIn);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setLocationAndAngles(double x, double y, double z, float yaw, float pitch) {
/* 1173 */     this.lastTickPosX = this.prevPosX = this.posX = x;
/* 1174 */     this.lastTickPosY = this.prevPosY = this.posY = y;
/* 1175 */     this.lastTickPosZ = this.prevPosZ = this.posZ = z;
/* 1176 */     this.rotationYaw = yaw;
/* 1177 */     this.rotationPitch = pitch;
/* 1178 */     setPosition(this.posX, this.posY, this.posZ);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public float getDistanceToEntity(Entity entityIn) {
/* 1185 */     float f = (float)(this.posX - entityIn.posX);
/* 1186 */     float f1 = (float)(this.posY - entityIn.posY);
/* 1187 */     float f2 = (float)(this.posZ - entityIn.posZ);
/* 1188 */     return MathHelper.sqrt_float(f * f + f1 * f1 + f2 * f2);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public double getDistanceSq(double x, double y, double z) {
/* 1195 */     double d0 = this.posX - x;
/* 1196 */     double d1 = this.posY - y;
/* 1197 */     double d2 = this.posZ - z;
/* 1198 */     return d0 * d0 + d1 * d1 + d2 * d2;
/*      */   }
/*      */   
/*      */   public double getDistanceSq(BlockPos pos) {
/* 1202 */     return pos.distanceSq(this.posX, this.posY, this.posZ);
/*      */   }
/*      */   
/*      */   public double getDistanceSqToCenter(BlockPos pos) {
/* 1206 */     return pos.distanceSqToCenter(this.posX, this.posY, this.posZ);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public double getDistance(double x, double y, double z) {
/* 1213 */     double d0 = this.posX - x;
/* 1214 */     double d1 = this.posY - y;
/* 1215 */     double d2 = this.posZ - z;
/* 1216 */     return MathHelper.sqrt_double(d0 * d0 + d1 * d1 + d2 * d2);
/*      */   }
/*      */   
/*      */   public double getDistance(BlockPos pos) {
/* 1220 */     double x = this.posX - pos.getX(), y = this.posY - pos.getY(), z = this.posZ - pos.getZ();
/* 1221 */     return MathHelper.sqrt_double(x * x + y * y + z * z);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public double getDistanceSqToEntity(Entity entityIn) {
/* 1228 */     double d0 = this.posX - entityIn.posX;
/* 1229 */     double d1 = this.posY - entityIn.posY;
/* 1230 */     double d2 = this.posZ - entityIn.posZ;
/* 1231 */     return d0 * d0 + d1 * d1 + d2 * d2;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void onCollideWithPlayer(EntityPlayer entityIn) {}
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void applyEntityCollision(Entity entityIn) {
/* 1244 */     if (entityIn.riddenByEntity != this && entityIn.ridingEntity != this && 
/* 1245 */       !entityIn.noClip && !this.noClip) {
/* 1246 */       double d0 = entityIn.posX - this.posX;
/* 1247 */       double d1 = entityIn.posZ - this.posZ;
/* 1248 */       double d2 = MathHelper.abs_max(d0, d1);
/*      */       
/* 1250 */       if (d2 >= 0.009999999776482582D) {
/* 1251 */         d2 = MathHelper.sqrt_double(d2);
/* 1252 */         d0 /= d2;
/* 1253 */         d1 /= d2;
/* 1254 */         double d3 = 1.0D / d2;
/*      */         
/* 1256 */         if (d3 > 1.0D) {
/* 1257 */           d3 = 1.0D;
/*      */         }
/*      */         
/* 1260 */         d0 *= d3;
/* 1261 */         d1 *= d3;
/* 1262 */         d0 *= 0.05000000074505806D;
/* 1263 */         d1 *= 0.05000000074505806D;
/* 1264 */         d0 *= (1.0F - this.entityCollisionReduction);
/* 1265 */         d1 *= (1.0F - this.entityCollisionReduction);
/*      */         
/* 1267 */         if (this.riddenByEntity == null) {
/* 1268 */           addVelocity(-d0, 0.0D, -d1);
/*      */         }
/*      */         
/* 1271 */         if (entityIn.riddenByEntity == null) {
/* 1272 */           entityIn.addVelocity(d0, 0.0D, d1);
/*      */         }
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void addVelocity(double x, double y, double z) {
/* 1283 */     this.motionX += x;
/* 1284 */     this.motionY += y;
/* 1285 */     this.motionZ += z;
/* 1286 */     this.isAirBorne = true;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void setBeenAttacked() {
/* 1293 */     this.velocityChanged = true;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean attackEntityFrom(DamageSource source, float amount) {
/* 1300 */     if (isEntityInvulnerable(source)) {
/* 1301 */       return false;
/*      */     }
/* 1303 */     setBeenAttacked();
/* 1304 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Vec3 getLook(float partialTicks) {
/* 1312 */     if (partialTicks == 1.0F) {
/* 1313 */       return getVectorForRotation(this.rotationPitch, this.rotationYaw);
/*      */     }
/* 1315 */     float f = this.prevRotationPitch + (this.rotationPitch - this.prevRotationPitch) * partialTicks;
/* 1316 */     float f1 = this.prevRotationYaw + (this.rotationYaw - this.prevRotationYaw) * partialTicks;
/* 1317 */     return getVectorForRotation(f, f1);
/*      */   }
/*      */ 
/*      */   
/*      */   public Vec3 getPositionEyes(float partialTicks) {
/* 1322 */     if (partialTicks == 1.0F) {
/* 1323 */       return new Vec3(this.posX, this.posY + getEyeHeight(), this.posZ);
/*      */     }
/* 1325 */     double d0 = this.prevPosX + (this.posX - this.prevPosX) * partialTicks;
/* 1326 */     double d1 = this.prevPosY + (this.posY - this.prevPosY) * partialTicks + getEyeHeight();
/* 1327 */     double d2 = this.prevPosZ + (this.posZ - this.prevPosZ) * partialTicks;
/* 1328 */     return new Vec3(d0, d1, d2);
/*      */   }
/*      */ 
/*      */   
/*      */   public MovingObjectPosition rayTrace(double blockReachDistance, float partialTicks) {
/* 1333 */     Vec3 vec3 = getPositionEyes(partialTicks);
/* 1334 */     Vec3 vec31 = getLook(partialTicks);
/* 1335 */     Vec3 vec32 = vec3.addVector(vec31.xCoord * blockReachDistance, vec31.yCoord * blockReachDistance, vec31.zCoord * blockReachDistance);
/* 1336 */     return this.worldObj.rayTraceBlocks(vec3, vec32, false, false, true);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean canBeCollidedWith() {
/* 1343 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean canBePushed() {
/* 1350 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void addToPlayerScore(Entity entityIn, int amount) {}
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isInRangeToRender3d(double x, double y, double z) {
/* 1361 */     double d0 = this.posX - x;
/* 1362 */     double d1 = this.posY - y;
/* 1363 */     double d2 = this.posZ - z;
/* 1364 */     double d3 = d0 * d0 + d1 * d1 + d2 * d2;
/* 1365 */     return isInRangeToRenderDist(d3);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isInRangeToRenderDist(double distance) {
/* 1373 */     double d0 = this.boundingBox.getAverageEdgeLength();
/*      */     
/* 1375 */     if (Double.isNaN(d0)) {
/* 1376 */       d0 = 1.0D;
/*      */     }
/*      */     
/* 1379 */     d0 = d0 * 64.0D * this.renderDistanceWeight;
/* 1380 */     return (distance < d0 * d0);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean writeMountToNBT(NBTTagCompound tagCompund) {
/* 1388 */     String s = getEntityString();
/*      */     
/* 1390 */     if (!this.isDead && s != null) {
/* 1391 */       tagCompund.setString("id", s);
/* 1392 */       writeToNBT(tagCompund);
/* 1393 */       return true;
/*      */     } 
/* 1395 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean writeToNBTOptional(NBTTagCompound tagCompund) {
/* 1405 */     String s = getEntityString();
/*      */     
/* 1407 */     if (!this.isDead && s != null && this.riddenByEntity == null) {
/* 1408 */       tagCompund.setString("id", s);
/* 1409 */       writeToNBT(tagCompund);
/* 1410 */       return true;
/*      */     } 
/* 1412 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void writeToNBT(NBTTagCompound tagCompund) {
/*      */     try {
/* 1421 */       tagCompund.setTag("Pos", (NBTBase)newDoubleNBTList(new double[] { this.posX, this.posY, this.posZ }));
/* 1422 */       tagCompund.setTag("Motion", (NBTBase)newDoubleNBTList(new double[] { this.motionX, this.motionY, this.motionZ }));
/* 1423 */       tagCompund.setTag("Rotation", (NBTBase)newFloatNBTList(new float[] { this.rotationYaw, this.rotationPitch }));
/* 1424 */       tagCompund.setFloat("FallDistance", this.fallDistance);
/* 1425 */       tagCompund.setShort("Fire", (short)this.fire);
/* 1426 */       tagCompund.setShort("Air", (short)getAir());
/* 1427 */       tagCompund.setBoolean("OnGround", this.onGround);
/* 1428 */       tagCompund.setInteger("Dimension", this.dimension);
/* 1429 */       tagCompund.setBoolean("Invulnerable", this.invulnerable);
/* 1430 */       tagCompund.setInteger("PortalCooldown", this.timeUntilPortal);
/* 1431 */       tagCompund.setLong("UUIDMost", this.entityUniqueID.getMostSignificantBits());
/* 1432 */       tagCompund.setLong("UUIDLeast", this.entityUniqueID.getLeastSignificantBits());
/*      */       
/* 1434 */       if (getCustomNameTag() != null && !getCustomNameTag().isEmpty()) {
/* 1435 */         tagCompund.setString("CustomName", getCustomNameTag());
/* 1436 */         tagCompund.setBoolean("CustomNameVisible", getAlwaysRenderNameTag());
/*      */       } 
/*      */       
/* 1439 */       this.cmdResultStats.writeStatsToNBT(tagCompund);
/*      */       
/* 1441 */       if (isSilent()) {
/* 1442 */         tagCompund.setBoolean("Silent", isSilent());
/*      */       }
/*      */       
/* 1445 */       writeEntityToNBT(tagCompund);
/*      */       
/* 1447 */       if (this.ridingEntity != null) {
/* 1448 */         NBTTagCompound nbttagcompound = new NBTTagCompound();
/*      */         
/* 1450 */         if (this.ridingEntity.writeMountToNBT(nbttagcompound)) {
/* 1451 */           tagCompund.setTag("Riding", (NBTBase)nbttagcompound);
/*      */         }
/*      */       } 
/* 1454 */     } catch (Throwable throwable) {
/* 1455 */       CrashReport crashreport = CrashReport.makeCrashReport(throwable, "Saving entity NBT");
/* 1456 */       CrashReportCategory crashreportcategory = crashreport.makeCategory("Entity being saved");
/* 1457 */       addEntityCrashInfo(crashreportcategory);
/* 1458 */       throw new ReportedException(crashreport);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void readFromNBT(NBTTagCompound tagCompund) {
/*      */     try {
/* 1467 */       NBTTagList nbttaglist = tagCompund.getTagList("Pos", 6);
/* 1468 */       NBTTagList nbttaglist1 = tagCompund.getTagList("Motion", 6);
/* 1469 */       NBTTagList nbttaglist2 = tagCompund.getTagList("Rotation", 5);
/* 1470 */       this.motionX = nbttaglist1.getDoubleAt(0);
/* 1471 */       this.motionY = nbttaglist1.getDoubleAt(1);
/* 1472 */       this.motionZ = nbttaglist1.getDoubleAt(2);
/*      */       
/* 1474 */       if (Math.abs(this.motionX) > 10.0D) {
/* 1475 */         this.motionX = 0.0D;
/*      */       }
/*      */       
/* 1478 */       if (Math.abs(this.motionY) > 10.0D) {
/* 1479 */         this.motionY = 0.0D;
/*      */       }
/*      */       
/* 1482 */       if (Math.abs(this.motionZ) > 10.0D) {
/* 1483 */         this.motionZ = 0.0D;
/*      */       }
/*      */       
/* 1486 */       this.prevPosX = this.lastTickPosX = this.posX = nbttaglist.getDoubleAt(0);
/* 1487 */       this.prevPosY = this.lastTickPosY = this.posY = nbttaglist.getDoubleAt(1);
/* 1488 */       this.prevPosZ = this.lastTickPosZ = this.posZ = nbttaglist.getDoubleAt(2);
/* 1489 */       this.prevRotationYaw = this.rotationYaw = nbttaglist2.getFloatAt(0);
/* 1490 */       this.prevRotationPitch = this.rotationPitch = nbttaglist2.getFloatAt(1);
/* 1491 */       setRotationYawHead(this.rotationYaw);
/* 1492 */       setRenderYawOffset(this.rotationYaw);
/* 1493 */       this.fallDistance = tagCompund.getFloat("FallDistance");
/* 1494 */       this.fire = tagCompund.getShort("Fire");
/* 1495 */       setAir(tagCompund.getShort("Air"));
/* 1496 */       this.onGround = tagCompund.getBoolean("OnGround");
/* 1497 */       this.dimension = tagCompund.getInteger("Dimension");
/* 1498 */       this.invulnerable = tagCompund.getBoolean("Invulnerable");
/* 1499 */       this.timeUntilPortal = tagCompund.getInteger("PortalCooldown");
/*      */       
/* 1501 */       if (tagCompund.hasKey("UUIDMost", 4) && tagCompund.hasKey("UUIDLeast", 4)) {
/* 1502 */         this.entityUniqueID = new UUID(tagCompund.getLong("UUIDMost"), tagCompund.getLong("UUIDLeast"));
/* 1503 */       } else if (tagCompund.hasKey("UUID", 8)) {
/* 1504 */         this.entityUniqueID = UUID.fromString(tagCompund.getString("UUID"));
/*      */       } 
/*      */       
/* 1507 */       setPosition(this.posX, this.posY, this.posZ);
/* 1508 */       setRotation(this.rotationYaw, this.rotationPitch);
/*      */       
/* 1510 */       if (tagCompund.hasKey("CustomName", 8) && !tagCompund.getString("CustomName").isEmpty()) {
/* 1511 */         setCustomNameTag(tagCompund.getString("CustomName"));
/*      */       }
/*      */       
/* 1514 */       setAlwaysRenderNameTag(tagCompund.getBoolean("CustomNameVisible"));
/* 1515 */       this.cmdResultStats.readStatsFromNBT(tagCompund);
/* 1516 */       setSilent(tagCompund.getBoolean("Silent"));
/* 1517 */       readEntityFromNBT(tagCompund);
/*      */       
/* 1519 */       if (shouldSetPosAfterLoading()) {
/* 1520 */         setPosition(this.posX, this.posY, this.posZ);
/*      */       }
/* 1522 */     } catch (Throwable throwable) {
/* 1523 */       CrashReport crashreport = CrashReport.makeCrashReport(throwable, "Loading entity NBT");
/* 1524 */       CrashReportCategory crashreportcategory = crashreport.makeCategory("Entity being loaded");
/* 1525 */       addEntityCrashInfo(crashreportcategory);
/* 1526 */       throw new ReportedException(crashreport);
/*      */     } 
/*      */   }
/*      */   
/*      */   protected boolean shouldSetPosAfterLoading() {
/* 1531 */     return true;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected final String getEntityString() {
/* 1538 */     return EntityList.getEntityString(this);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected abstract void readEntityFromNBT(NBTTagCompound paramNBTTagCompound);
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected abstract void writeEntityToNBT(NBTTagCompound paramNBTTagCompound);
/*      */ 
/*      */ 
/*      */   
/*      */   public void onChunkLoad() {}
/*      */ 
/*      */ 
/*      */   
/*      */   protected NBTTagList newDoubleNBTList(double... numbers) {
/* 1558 */     NBTTagList nbttaglist = new NBTTagList();
/*      */     
/* 1560 */     for (double d0 : numbers) {
/* 1561 */       nbttaglist.appendTag((NBTBase)new NBTTagDouble(d0));
/*      */     }
/*      */     
/* 1564 */     return nbttaglist;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected NBTTagList newFloatNBTList(float... numbers) {
/* 1571 */     NBTTagList nbttaglist = new NBTTagList();
/*      */     
/* 1573 */     for (float f : numbers) {
/* 1574 */       nbttaglist.appendTag((NBTBase)new NBTTagFloat(f));
/*      */     }
/*      */     
/* 1577 */     return nbttaglist;
/*      */   }
/*      */   
/*      */   public EntityItem dropItem(Item itemIn, int size) {
/* 1581 */     return dropItemWithOffset(itemIn, size, 0.0F);
/*      */   }
/*      */   
/*      */   public EntityItem dropItemWithOffset(Item itemIn, int size, float offsetY) {
/* 1585 */     return entityDropItem(new ItemStack(itemIn, size, 0), offsetY);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public EntityItem entityDropItem(ItemStack itemStackIn, float offsetY) {
/* 1592 */     if (itemStackIn.stackSize != 0 && itemStackIn.getItem() != null) {
/* 1593 */       EntityItem entityitem = new EntityItem(this.worldObj, this.posX, this.posY + offsetY, this.posZ, itemStackIn);
/* 1594 */       entityitem.setDefaultPickupDelay();
/* 1595 */       this.worldObj.spawnEntityInWorld((Entity)entityitem);
/* 1596 */       return entityitem;
/*      */     } 
/* 1598 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isEntityAlive() {
/* 1606 */     return !this.isDead;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isEntityInsideOpaqueBlock() {
/* 1613 */     if (this.noClip) {
/* 1614 */       return false;
/*      */     }
/* 1616 */     BlockPos.MutableBlockPos blockpos$mutableblockpos = new BlockPos.MutableBlockPos(-2147483648, -2147483648, -2147483648);
/*      */     
/* 1618 */     for (int i = 0; i < 8; i++) {
/* 1619 */       int j = MathHelper.floor_double(this.posY + ((((i >> 0) % 2) - 0.5F) * 0.1F) + getEyeHeight());
/* 1620 */       int k = MathHelper.floor_double(this.posX + ((((i >> 1) % 2) - 0.5F) * this.width * 0.8F));
/* 1621 */       int l = MathHelper.floor_double(this.posZ + ((((i >> 2) % 2) - 0.5F) * this.width * 0.8F));
/*      */       
/* 1623 */       if (blockpos$mutableblockpos.getX() != k || blockpos$mutableblockpos.getY() != j || blockpos$mutableblockpos.getZ() != l) {
/* 1624 */         blockpos$mutableblockpos.set(k, j, l);
/*      */         
/* 1626 */         if (this.worldObj.getBlockState((BlockPos)blockpos$mutableblockpos).getBlock().isVisuallyOpaque()) {
/* 1627 */           return true;
/*      */         }
/*      */       } 
/*      */     } 
/*      */     
/* 1632 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean interactFirst(EntityPlayer playerIn) {
/* 1640 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public AxisAlignedBB getCollisionBox(Entity entityIn) {
/* 1648 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void updateRidden() {
/* 1655 */     if (this.ridingEntity.isDead) {
/* 1656 */       this.ridingEntity = null;
/*      */     } else {
/* 1658 */       this.motionX = 0.0D;
/* 1659 */       this.motionY = 0.0D;
/* 1660 */       this.motionZ = 0.0D;
/* 1661 */       onUpdate();
/*      */       
/* 1663 */       if (this.ridingEntity != null) {
/* 1664 */         this.ridingEntity.updateRiderPosition();
/* 1665 */         this.entityRiderYawDelta += (this.ridingEntity.rotationYaw - this.ridingEntity.prevRotationYaw);
/*      */         
/* 1667 */         for (this.entityRiderPitchDelta += (this.ridingEntity.rotationPitch - this.ridingEntity.prevRotationPitch); this.entityRiderYawDelta >= 180.0D; this.entityRiderYawDelta -= 360.0D);
/*      */ 
/*      */ 
/*      */         
/* 1671 */         while (this.entityRiderYawDelta < -180.0D) {
/* 1672 */           this.entityRiderYawDelta += 360.0D;
/*      */         }
/*      */         
/* 1675 */         while (this.entityRiderPitchDelta >= 180.0D) {
/* 1676 */           this.entityRiderPitchDelta -= 360.0D;
/*      */         }
/*      */         
/* 1679 */         while (this.entityRiderPitchDelta < -180.0D) {
/* 1680 */           this.entityRiderPitchDelta += 360.0D;
/*      */         }
/*      */         
/* 1683 */         double d0 = this.entityRiderYawDelta * 0.5D;
/* 1684 */         double d1 = this.entityRiderPitchDelta * 0.5D;
/* 1685 */         float f = 10.0F;
/*      */         
/* 1687 */         if (d0 > f) {
/* 1688 */           d0 = f;
/*      */         }
/*      */         
/* 1691 */         if (d0 < -f) {
/* 1692 */           d0 = -f;
/*      */         }
/*      */         
/* 1695 */         if (d1 > f) {
/* 1696 */           d1 = f;
/*      */         }
/*      */         
/* 1699 */         if (d1 < -f) {
/* 1700 */           d1 = -f;
/*      */         }
/*      */         
/* 1703 */         this.entityRiderYawDelta -= d0;
/* 1704 */         this.entityRiderPitchDelta -= d1;
/*      */       } 
/*      */     } 
/*      */   }
/*      */   
/*      */   public void updateRiderPosition() {
/* 1710 */     if (this.riddenByEntity != null) {
/* 1711 */       this.riddenByEntity.setPosition(this.posX, this.posY + getMountedYOffset() + this.riddenByEntity.getYOffset(), this.posZ);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public double getYOffset() {
/* 1719 */     return 0.0D;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public double getMountedYOffset() {
/* 1726 */     return this.height * 0.75D;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void mountEntity(Entity entityIn) {
/* 1733 */     this.entityRiderPitchDelta = 0.0D;
/* 1734 */     this.entityRiderYawDelta = 0.0D;
/*      */     
/* 1736 */     if (entityIn == null) {
/* 1737 */       if (this.ridingEntity != null) {
/* 1738 */         setLocationAndAngles(this.ridingEntity.posX, this.ridingEntity.boundingBox.minY + this.ridingEntity.height, this.ridingEntity.posZ, this.rotationYaw, this.rotationPitch);
/* 1739 */         this.ridingEntity.riddenByEntity = null;
/*      */       } 
/*      */       
/* 1742 */       this.ridingEntity = null;
/*      */     } else {
/* 1744 */       if (this.ridingEntity != null) {
/* 1745 */         this.ridingEntity.riddenByEntity = null;
/*      */       }
/*      */       
/* 1748 */       if (entityIn != null) {
/* 1749 */         for (Entity entity = entityIn.ridingEntity; entity != null; entity = entity.ridingEntity) {
/* 1750 */           if (entity == this) {
/*      */             return;
/*      */           }
/*      */         } 
/*      */       }
/*      */       
/* 1756 */       this.ridingEntity = entityIn;
/* 1757 */       entityIn.riddenByEntity = this;
/*      */     } 
/*      */   }
/*      */   
/*      */   public void setPositionAndRotation2(double x, double y, double z, float yaw, float pitch, int posRotationIncrements, boolean p_180426_10_) {
/* 1762 */     setPosition(x, y, z);
/* 1763 */     setRotation(yaw, pitch);
/* 1764 */     List<AxisAlignedBB> list = this.worldObj.getCollidingBoundingBoxes(this, this.boundingBox.contract(0.03125D, 0.0D, 0.03125D));
/*      */     
/* 1766 */     if (!list.isEmpty()) {
/* 1767 */       double d0 = 0.0D;
/*      */       
/* 1769 */       for (AxisAlignedBB axisalignedbb : list) {
/* 1770 */         if (axisalignedbb.maxY > d0) {
/* 1771 */           d0 = axisalignedbb.maxY;
/*      */         }
/*      */       } 
/*      */       
/* 1775 */       y += d0 - this.boundingBox.minY;
/* 1776 */       setPosition(x, y, z);
/*      */     } 
/*      */   }
/*      */   
/*      */   public float getCollisionBorderSize() {
/* 1781 */     EventEntityBorderSize e = new EventEntityBorderSize(0.1F);
/* 1782 */     EventManager.call((Event)e);
/* 1783 */     return e.getBorderSize();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Vec3 getLookVec() {
/* 1790 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setPortal(BlockPos pos) {
/* 1800 */     if (this.timeUntilPortal > 0) {
/* 1801 */       this.timeUntilPortal = getPortalCooldown();
/*      */     } else {
/* 1803 */       if (!this.worldObj.isRemote && !pos.equals(this.lastPortalPos)) {
/* 1804 */         this.lastPortalPos = pos;
/* 1805 */         BlockPattern.PatternHelper blockpattern$patternhelper = Blocks.portal.func_181089_f(this.worldObj, pos);
/* 1806 */         double d0 = (blockpattern$patternhelper.getFinger().getAxis() == EnumFacing.Axis.X) ? blockpattern$patternhelper.getPos().getZ() : blockpattern$patternhelper.getPos().getX();
/* 1807 */         double d1 = (blockpattern$patternhelper.getFinger().getAxis() == EnumFacing.Axis.X) ? this.posZ : this.posX;
/* 1808 */         d1 = Math.abs(MathHelper.func_181160_c(d1 - ((blockpattern$patternhelper.getFinger().rotateY().getAxisDirection() == EnumFacing.AxisDirection.NEGATIVE) ? true : false), d0, d0 - blockpattern$patternhelper.func_181118_d()));
/* 1809 */         double d2 = MathHelper.func_181160_c(this.posY - 1.0D, blockpattern$patternhelper.getPos().getY(), (blockpattern$patternhelper.getPos().getY() - blockpattern$patternhelper.func_181119_e()));
/* 1810 */         this.lastPortalVec = new Vec3(d1, d2, 0.0D);
/* 1811 */         this.teleportDirection = blockpattern$patternhelper.getFinger();
/*      */       } 
/*      */       
/* 1814 */       this.inPortal = true;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getPortalCooldown() {
/* 1822 */     return 300;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setVelocity(double x, double y, double z) {
/* 1829 */     this.motionX = x;
/* 1830 */     this.motionY = y;
/* 1831 */     this.motionZ = z;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void handleStatusUpdate(byte id) {}
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void performHurtAnimation() {}
/*      */ 
/*      */ 
/*      */   
/*      */   public ItemStack[] getInventory() {
/* 1847 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setCurrentItemOrArmor(int slotIn, ItemStack stack) {}
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isBurning() {
/* 1860 */     boolean flag = (this.worldObj != null && this.worldObj.isRemote);
/* 1861 */     return (!this.isImmuneToFire && (this.fire > 0 || (flag && getFlag(0))));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isRiding() {
/* 1869 */     return (this.ridingEntity != null);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isSneaking() {
/* 1876 */     return getFlag(1);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setSneaking(boolean sneaking) {
/* 1883 */     setFlag(1, sneaking);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isSprinting() {
/* 1890 */     return getFlag(3);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setSprinting(boolean sprinting) {
/* 1897 */     setFlag(3, sprinting);
/*      */   }
/*      */   
/*      */   public boolean isInvisible() {
/* 1901 */     return getFlag(5);
/*      */   }
/*      */   
/*      */   public void setInvisible(boolean invisible) {
/* 1905 */     setFlag(5, invisible);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isInvisibleToPlayer(EntityPlayer player) {
/* 1914 */     return player.isSpectator() ? false : isInvisible();
/*      */   }
/*      */   
/*      */   public boolean isEating() {
/* 1918 */     return getFlag(4);
/*      */   }
/*      */   
/*      */   public void setEating(boolean eating) {
/* 1922 */     setFlag(4, eating);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected boolean getFlag(int flag) {
/* 1930 */     return ((this.dataWatcher.getWatchableObjectByte(0) & 1 << flag) != 0);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void setFlag(int flag, boolean set) {
/* 1937 */     byte b0 = this.dataWatcher.getWatchableObjectByte(0);
/*      */     
/* 1939 */     if (set) {
/* 1940 */       this.dataWatcher.updateObject(0, Byte.valueOf((byte)(b0 | 1 << flag)));
/*      */     } else {
/* 1942 */       this.dataWatcher.updateObject(0, Byte.valueOf((byte)(b0 & (1 << flag ^ 0xFFFFFFFF))));
/*      */     } 
/*      */   }
/*      */   
/*      */   public int getAir() {
/* 1947 */     return this.dataWatcher.getWatchableObjectShort(1);
/*      */   }
/*      */   
/*      */   public void setAir(int air) {
/* 1951 */     this.dataWatcher.updateObject(1, Short.valueOf((short)air));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void onStruckByLightning(EntityLightningBolt lightningBolt) {
/* 1958 */     attackEntityFrom(DamageSource.lightningBolt, 5.0F);
/* 1959 */     this.fire++;
/*      */     
/* 1961 */     if (this.fire == 0) {
/* 1962 */       setFire(8);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public void onKillEntity(EntityLivingBase entityLivingIn) {}
/*      */ 
/*      */ 
/*      */   
/*      */   protected boolean pushOutOfBlocks(double x, double y, double z) {
/* 1973 */     BlockPos blockpos = new BlockPos(x, y, z);
/* 1974 */     double d0 = x - blockpos.getX();
/* 1975 */     double d1 = y - blockpos.getY();
/* 1976 */     double d2 = z - blockpos.getZ();
/* 1977 */     List<AxisAlignedBB> list = this.worldObj.getCollisionBoxes(this.boundingBox);
/*      */     
/* 1979 */     if (list.isEmpty() && !this.worldObj.isBlockFullCube(blockpos)) {
/* 1980 */       return false;
/*      */     }
/* 1982 */     int i = 3;
/* 1983 */     double d3 = 9999.0D;
/*      */     
/* 1985 */     if (!this.worldObj.isBlockFullCube(blockpos.west()) && d0 < d3) {
/* 1986 */       d3 = d0;
/* 1987 */       i = 0;
/*      */     } 
/*      */     
/* 1990 */     if (!this.worldObj.isBlockFullCube(blockpos.east()) && 1.0D - d0 < d3) {
/* 1991 */       d3 = 1.0D - d0;
/* 1992 */       i = 1;
/*      */     } 
/*      */     
/* 1995 */     if (!this.worldObj.isBlockFullCube(blockpos.up()) && 1.0D - d1 < d3) {
/* 1996 */       d3 = 1.0D - d1;
/* 1997 */       i = 3;
/*      */     } 
/*      */     
/* 2000 */     if (!this.worldObj.isBlockFullCube(blockpos.north()) && d2 < d3) {
/* 2001 */       d3 = d2;
/* 2002 */       i = 4;
/*      */     } 
/*      */     
/* 2005 */     if (!this.worldObj.isBlockFullCube(blockpos.south()) && 1.0D - d2 < d3) {
/* 2006 */       d3 = 1.0D - d2;
/* 2007 */       i = 5;
/*      */     } 
/*      */     
/* 2010 */     float f = this.rand.nextFloat() * 0.2F + 0.1F;
/*      */     
/* 2012 */     if (i == 0) {
/* 2013 */       this.motionX = -f;
/*      */     }
/*      */     
/* 2016 */     if (i == 1) {
/* 2017 */       this.motionX = f;
/*      */     }
/*      */     
/* 2020 */     if (i == 3) {
/* 2021 */       this.motionY = f;
/*      */     }
/*      */     
/* 2024 */     if (i == 4) {
/* 2025 */       this.motionZ = -f;
/*      */     }
/*      */     
/* 2028 */     if (i == 5) {
/* 2029 */       this.motionZ = f;
/*      */     }
/*      */     
/* 2032 */     return true;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setInWeb() {
/* 2040 */     this.isInWeb = true;
/* 2041 */     this.fallDistance = 0.0F;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getName() {
/* 2048 */     if (hasCustomName()) {
/* 2049 */       return getCustomNameTag();
/*      */     }
/* 2051 */     String s = EntityList.getEntityString(this);
/*      */     
/* 2053 */     if (s == null) {
/* 2054 */       s = "generic";
/*      */     }
/*      */     
/* 2057 */     return StatCollector.translateToLocal("entity." + s + ".name");
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Entity[] getParts() {
/* 2065 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isEntityEqual(Entity entityIn) {
/* 2072 */     return (this == entityIn);
/*      */   }
/*      */   
/*      */   public float getRotationYawHead() {
/* 2076 */     return 0.0F;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setRotationYawHead(float rotation) {}
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setRenderYawOffset(float offset) {}
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean canAttackWithItem() {
/* 2097 */     return true;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean hitByEntity(Entity entityIn) {
/* 2104 */     return false;
/*      */   }
/*      */   
/*      */   public String toString() {
/* 2108 */     return String.format("%s['%s'/%d, l='%s', x=%.2f, y=%.2f, z=%.2f]", new Object[] { getClass().getSimpleName(), getName(), Integer.valueOf(this.entityId), (this.worldObj == null) ? "~NULL~" : this.worldObj.getWorldInfo().getWorldName(), Double.valueOf(this.posX), Double.valueOf(this.posY), Double.valueOf(this.posZ) });
/*      */   }
/*      */   
/*      */   public boolean isEntityInvulnerable(DamageSource source) {
/* 2112 */     return (this.invulnerable && source != DamageSource.outOfWorld && !source.isCreativePlayer());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void copyLocationAndAnglesFrom(Entity entityIn) {
/* 2119 */     setLocationAndAngles(entityIn.posX, entityIn.posY, entityIn.posZ, entityIn.rotationYaw, entityIn.rotationPitch);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void copyDataFromOld(Entity entityIn) {
/* 2126 */     NBTTagCompound nbttagcompound = new NBTTagCompound();
/* 2127 */     entityIn.writeToNBT(nbttagcompound);
/* 2128 */     readFromNBT(nbttagcompound);
/* 2129 */     this.timeUntilPortal = entityIn.timeUntilPortal;
/* 2130 */     this.lastPortalPos = entityIn.lastPortalPos;
/* 2131 */     this.lastPortalVec = entityIn.lastPortalVec;
/* 2132 */     this.teleportDirection = entityIn.teleportDirection;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void travelToDimension(int dimensionId) {
/* 2139 */     if (!this.worldObj.isRemote && !this.isDead) {
/* 2140 */       this.worldObj.theProfiler.startSection("changeDimension");
/* 2141 */       MinecraftServer minecraftserver = MinecraftServer.getServer();
/* 2142 */       int i = this.dimension;
/* 2143 */       WorldServer worldserver = minecraftserver.worldServerForDimension(i);
/* 2144 */       WorldServer worldserver1 = minecraftserver.worldServerForDimension(dimensionId);
/* 2145 */       this.dimension = dimensionId;
/*      */       
/* 2147 */       if (i == 1 && dimensionId == 1) {
/* 2148 */         worldserver1 = minecraftserver.worldServerForDimension(0);
/* 2149 */         this.dimension = 0;
/*      */       } 
/*      */       
/* 2152 */       this.worldObj.removeEntity(this);
/* 2153 */       this.isDead = false;
/* 2154 */       this.worldObj.theProfiler.startSection("reposition");
/* 2155 */       minecraftserver.getConfigurationManager().transferEntityToWorld(this, i, worldserver, worldserver1);
/* 2156 */       this.worldObj.theProfiler.endStartSection("reloading");
/* 2157 */       Entity entity = EntityList.createEntityByName(EntityList.getEntityString(this), (World)worldserver1);
/*      */       
/* 2159 */       if (entity != null) {
/* 2160 */         entity.copyDataFromOld(this);
/*      */         
/* 2162 */         if (i == 1 && dimensionId == 1) {
/* 2163 */           BlockPos blockpos = this.worldObj.getTopSolidOrLiquidBlock(worldserver1.getSpawnPoint());
/* 2164 */           entity.moveToBlockPosAndAngles(blockpos, entity.rotationYaw, entity.rotationPitch);
/*      */         } 
/*      */         
/* 2167 */         worldserver1.spawnEntityInWorld(entity);
/*      */       } 
/*      */       
/* 2170 */       this.isDead = true;
/* 2171 */       this.worldObj.theProfiler.endSection();
/* 2172 */       worldserver.resetUpdateEntityTick();
/* 2173 */       worldserver1.resetUpdateEntityTick();
/* 2174 */       this.worldObj.theProfiler.endSection();
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public float getExplosionResistance(Explosion explosionIn, World worldIn, BlockPos pos, IBlockState blockStateIn) {
/* 2182 */     return blockStateIn.getBlock().getExplosionResistance(this);
/*      */   }
/*      */   
/*      */   public boolean verifyExplosion(Explosion explosionIn, World worldIn, BlockPos pos, IBlockState blockStateIn, float p_174816_5_) {
/* 2186 */     return true;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getMaxFallHeight() {
/* 2193 */     return 3;
/*      */   }
/*      */   
/*      */   public Vec3 func_181014_aG() {
/* 2197 */     return this.lastPortalVec;
/*      */   }
/*      */   
/*      */   public EnumFacing getTeleportDirection() {
/* 2201 */     return this.teleportDirection;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean doesEntityNotTriggerPressurePlate() {
/* 2208 */     return false;
/*      */   }
/*      */   
/*      */   public void addEntityCrashInfo(CrashReportCategory category) {
/* 2212 */     category.addCrashSectionCallable("Entity Type", new Callable<String>() {
/*      */           public String call() {
/* 2214 */             return EntityList.getEntityString(Entity.this) + " (" + Entity.this.getClass().getCanonicalName() + ")";
/*      */           }
/*      */         });
/* 2217 */     category.addCrashSection("Entity ID", Integer.valueOf(this.entityId));
/* 2218 */     category.addCrashSectionCallable("Entity Name", new Callable<String>() {
/*      */           public String call() {
/* 2220 */             return Entity.this.getName();
/*      */           }
/*      */         });
/* 2223 */     category.addCrashSection("Entity's Exact location", String.format("%.2f, %.2f, %.2f", new Object[] { Double.valueOf(this.posX), Double.valueOf(this.posY), Double.valueOf(this.posZ) }));
/* 2224 */     category.addCrashSection("Entity's Block location", CrashReportCategory.getCoordinateInfo(MathHelper.floor_double(this.posX), MathHelper.floor_double(this.posY), MathHelper.floor_double(this.posZ)));
/* 2225 */     category.addCrashSection("Entity's Momentum", String.format("%.2f, %.2f, %.2f", new Object[] { Double.valueOf(this.motionX), Double.valueOf(this.motionY), Double.valueOf(this.motionZ) }));
/* 2226 */     category.addCrashSectionCallable("Entity's Rider", new Callable<String>() {
/*      */           public String call() {
/* 2228 */             return Entity.this.riddenByEntity.toString();
/*      */           }
/*      */         });
/* 2231 */     category.addCrashSectionCallable("Entity's Vehicle", new Callable<String>() {
/*      */           public String call() {
/* 2233 */             return Entity.this.ridingEntity.toString();
/*      */           }
/*      */         });
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean canRenderOnFire() {
/* 2242 */     return isBurning();
/*      */   }
/*      */   
/*      */   public UUID getUniqueID() {
/* 2246 */     return this.entityUniqueID;
/*      */   }
/*      */   
/*      */   public boolean isPushedByWater() {
/* 2250 */     return true;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public IChatComponent getDisplayName() {
/* 2257 */     ChatComponentText chatcomponenttext = new ChatComponentText(getName());
/* 2258 */     chatcomponenttext.getChatStyle().setChatHoverEvent(getHoverEvent());
/* 2259 */     chatcomponenttext.getChatStyle().setInsertion(this.entityUniqueID.toString());
/* 2260 */     return (IChatComponent)chatcomponenttext;
/*      */   }
/*      */   
/*      */   public String getCustomNameTag() {
/* 2264 */     return this.dataWatcher.getWatchableObjectString(2);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setCustomNameTag(String name) {
/* 2271 */     this.dataWatcher.updateObject(2, name);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean hasCustomName() {
/* 2278 */     return !this.dataWatcher.getWatchableObjectString(2).isEmpty();
/*      */   }
/*      */   
/*      */   public boolean getAlwaysRenderNameTag() {
/* 2282 */     return (this.dataWatcher.getWatchableObjectByte(3) == 1);
/*      */   }
/*      */   
/*      */   public void setAlwaysRenderNameTag(boolean alwaysRenderNameTag) {
/* 2286 */     this.dataWatcher.updateObject(3, Byte.valueOf((byte)(alwaysRenderNameTag ? 1 : 0)));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setPositionAndUpdate(double x, double y, double z) {
/* 2293 */     setLocationAndAngles(x, y, z, this.rotationYaw, this.rotationPitch);
/*      */   }
/*      */   
/*      */   public boolean getAlwaysRenderNameTagForRender() {
/* 2297 */     return getAlwaysRenderNameTag();
/*      */   }
/*      */ 
/*      */   
/*      */   public void onDataWatcherUpdate(int dataID) {}
/*      */   
/*      */   public EnumFacing getHorizontalFacing() {
/* 2304 */     return EnumFacing.getHorizontal(MathHelper.floor_double((this.rotationYaw * 4.0F / 360.0F) + 0.5D) & 0x3);
/*      */   }
/*      */   
/*      */   protected HoverEvent getHoverEvent() {
/* 2308 */     NBTTagCompound nbttagcompound = new NBTTagCompound();
/* 2309 */     String s = EntityList.getEntityString(this);
/* 2310 */     nbttagcompound.setString("id", this.entityUniqueID.toString());
/*      */     
/* 2312 */     if (s != null) {
/* 2313 */       nbttagcompound.setString("type", s);
/*      */     }
/*      */     
/* 2316 */     nbttagcompound.setString("name", getName());
/* 2317 */     return new HoverEvent(HoverEvent.Action.SHOW_ENTITY, (IChatComponent)new ChatComponentText(nbttagcompound.toString()));
/*      */   }
/*      */   
/*      */   public boolean isSpectatedByPlayer(EntityPlayerMP player) {
/* 2321 */     return true;
/*      */   }
/*      */   
/*      */   public AxisAlignedBB getEntityBoundingBox() {
/* 2325 */     return this.boundingBox;
/*      */   }
/*      */   
/*      */   public void setEntityBoundingBox(AxisAlignedBB bb) {
/* 2329 */     this.boundingBox = bb;
/*      */   }
/*      */   
/*      */   public float getEyeHeight() {
/* 2333 */     return this.height * 0.85F;
/*      */   }
/*      */   
/*      */   public boolean isOutsideBorder() {
/* 2337 */     return this.isOutsideBorder;
/*      */   }
/*      */   
/*      */   public void setOutsideBorder(boolean outsideBorder) {
/* 2341 */     this.isOutsideBorder = outsideBorder;
/*      */   }
/*      */   
/*      */   public boolean replaceItemInInventory(int inventorySlot, ItemStack itemStackIn) {
/* 2345 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void addChatMessage(IChatComponent component) {}
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean canCommandSenderUseCommand(int permLevel, String commandName) {
/* 2358 */     return true;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public BlockPos getPosition() {
/* 2366 */     return new BlockPos(this.posX, this.posY + 0.5D, this.posZ);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Vec3 getPositionVector() {
/* 2374 */     return new Vec3(this.posX, this.posY, this.posZ);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public World getEntityWorld() {
/* 2382 */     return this.worldObj;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Entity getCommandSenderEntity() {
/* 2389 */     return this;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean sendCommandFeedback() {
/* 2396 */     return false;
/*      */   }
/*      */   
/*      */   public void setCommandStat(CommandResultStats.Type type, int amount) {
/* 2400 */     this.cmdResultStats.setCommandStatScore(this, type, amount);
/*      */   }
/*      */   
/*      */   public CommandResultStats getCommandStats() {
/* 2404 */     return this.cmdResultStats;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setCommandStats(Entity entityIn) {
/* 2411 */     this.cmdResultStats.addAllStats(entityIn.cmdResultStats);
/*      */   }
/*      */   
/*      */   public NBTTagCompound getNBTTagCompound() {
/* 2415 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void clientUpdateEntityNBT(NBTTagCompound compound) {}
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean interactAt(EntityPlayer player, Vec3 targetVec3) {
/* 2428 */     return false;
/*      */   }
/*      */   
/*      */   public boolean isImmuneToExplosions() {
/* 2432 */     return false;
/*      */   }
/*      */   
/*      */   protected void applyEnchantments(EntityLivingBase entityLivingBaseIn, Entity entityIn) {
/* 2436 */     if (entityIn instanceof EntityLivingBase) {
/* 2437 */       EnchantmentHelper.applyThornEnchantments((EntityLivingBase)entityIn, entityLivingBaseIn);
/*      */     }
/*      */     
/* 2440 */     EnchantmentHelper.applyArthropodEnchantments(entityLivingBaseIn, entityIn);
/*      */   }
/*      */   
/*      */   public final Vec3f getPos() {
/* 2444 */     if (this.pos == null) {
/* 2445 */       this.pos = new Vec3f();
/*      */     }
/* 2447 */     return this.pos.setX(this.posX).setY(this.posY).setZ(this.posZ);
/*      */   }
/*      */   
/*      */   public double getDistanceY(double y) {
/* 2451 */     double d1 = this.posY - y;
/* 2452 */     return MathHelper.sqrt_double(d1 * d1);
/*      */   }
/*      */   
/*      */   public double getLastTickDistance() {
/* 2456 */     return Math.hypot(this.posX - this.prevPosX, this.posZ - this.prevPosZ);
/*      */   }
/*      */   
/*      */   public final Vec3f getLastTickPos() {
/* 2460 */     if (this.lastTickPos == null) {
/* 2461 */       this.lastTickPos = new Vec3f();
/*      */     }
/* 2463 */     return this.lastTickPos.setX(this.lastTickPosX).setY(this.lastTickPosY).setZ(this.lastTickPosZ);
/*      */   }
/*      */   
/*      */   public final Vec3f interpolate(float ticks) {
/* 2467 */     return getLastTickPos().add(getPos().sub(getLastTickPos()).scale(ticks));
/*      */   }
/*      */   
/*      */   public double getDistance2D(Entity entityIn) {
/* 2471 */     double x = Math.abs(entityIn.posX - this.posX);
/* 2472 */     double z = Math.abs(entityIn.posZ - this.posZ);
/* 2473 */     return Math.sqrt(x * x + z * z);
/*      */   }
/*      */   
/*      */   public Vec3 getLookCustom(float yaw, float pitch) {
/* 2477 */     return getVectorForRotation(pitch, yaw);
/*      */   }
/*      */   
/*      */   public MovingObjectPosition rayTraceCustom(double blockReachDistance, float partialTicks, float yaw, float pitch) {
/* 2481 */     Vec3 vec3 = getPositionEyes(partialTicks);
/* 2482 */     Vec3 vec31 = getLookCustom(yaw, pitch);
/* 2483 */     Vec3 vec32 = vec3.addVector(vec31.xCoord * blockReachDistance, vec31.yCoord * blockReachDistance, vec31.zCoord * blockReachDistance);
/* 2484 */     return this.worldObj.rayTraceBlocks(vec3, vec32, false, false, true);
/*      */   }
/*      */   
/*      */   public MovingObjectPosition rayTraceCustom(double blockReachDistance, float yaw, float pitch) {
/* 2488 */     Vec3 vec3 = getPositionEyes(1.0F);
/* 2489 */     Vec3 vec31 = getLookCustom(yaw, pitch);
/* 2490 */     Vec3 vec32 = vec3.addVector(vec31.xCoord * blockReachDistance, vec31.yCoord * blockReachDistance, vec31.zCoord * blockReachDistance);
/* 2491 */     return this.worldObj.rayTraceBlocks(vec3, vec32, false, false, true);
/*      */   }
/*      */   
/*      */   public int getEntityID() {
/* 2495 */     return this.entityId;
/*      */   }
/*      */   
/*      */   public Vector3d getCustomPositionVector() {
/* 2499 */     return new Vector3d(this.posX, this.posY, this.posZ);
/*      */   }
/*      */ 
/*      */   
/*      */   public Vec3f getCustomPositionVector2() {
/* 2504 */     return new Vec3f(this.posX, this.posY, this.posZ);
/*      */   }
/*      */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\minecraft\entity\Entity.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */