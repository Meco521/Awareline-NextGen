/*     */ package net.minecraft.entity;
/*     */ import java.util.Collection;
/*     */ import java.util.List;
/*     */ import java.util.Set;
/*     */ import net.minecraft.entity.ai.attributes.IAttributeInstance;
/*     */ import net.minecraft.entity.ai.attributes.ServersideAttributeMap;
/*     */ import net.minecraft.entity.item.EntityFallingBlock;
/*     */ import net.minecraft.entity.item.EntityItemFrame;
/*     */ import net.minecraft.entity.item.EntityMinecart;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.entity.player.EntityPlayerMP;
/*     */ import net.minecraft.entity.projectile.EntityArrow;
/*     */ import net.minecraft.entity.projectile.EntityFireball;
/*     */ import net.minecraft.init.Items;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.network.Packet;
/*     */ import net.minecraft.network.play.server.S0EPacketSpawnObject;
/*     */ import net.minecraft.network.play.server.S12PacketEntityVelocity;
/*     */ import net.minecraft.network.play.server.S14PacketEntity;
/*     */ import net.minecraft.network.play.server.S18PacketEntityTeleport;
/*     */ import net.minecraft.network.play.server.S1BPacketEntityAttach;
/*     */ import net.minecraft.network.play.server.S1CPacketEntityMetadata;
/*     */ import net.minecraft.network.play.server.S20PacketEntityProperties;
/*     */ import net.minecraft.potion.PotionEffect;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.util.MathHelper;
/*     */ import net.minecraft.world.storage.MapData;
/*     */ 
/*     */ public class EntityTrackerEntry {
/*  31 */   private static final Logger logger = LogManager.getLogger();
/*     */   
/*     */   public Entity trackedEntity;
/*     */   
/*     */   public int trackingDistanceThreshold;
/*     */   
/*     */   public int updateFrequency;
/*     */   
/*     */   public int encodedPosX;
/*     */   
/*     */   public int encodedPosY;
/*     */   
/*     */   public int encodedPosZ;
/*     */   
/*     */   public int encodedRotationYaw;
/*     */   
/*     */   public int encodedRotationPitch;
/*     */   
/*     */   public int lastHeadMotion;
/*     */   
/*     */   public double lastTrackedEntityMotionX;
/*     */   
/*     */   public double lastTrackedEntityMotionY;
/*     */   
/*     */   public double motionZ;
/*     */   
/*     */   public int updateCounter;
/*     */   
/*     */   private double lastTrackedEntityPosX;
/*     */   
/*     */   private double lastTrackedEntityPosY;
/*     */   
/*     */   private double lastTrackedEntityPosZ;
/*     */   
/*     */   private boolean firstUpdateDone;
/*     */   
/*     */   private final boolean sendVelocityUpdates;
/*     */   
/*     */   private int ticksSinceLastForcedTeleport;
/*     */   private Entity field_85178_v;
/*     */   private boolean ridingEntity;
/*     */   private boolean onGround;
/*     */   public boolean playerEntitiesUpdated;
/*  74 */   public Set<EntityPlayerMP> trackingPlayers = Sets.newHashSet();
/*     */ 
/*     */   
/*     */   public EntityTrackerEntry(Entity trackedEntityIn, int trackingDistanceThresholdIn, int updateFrequencyIn, boolean sendVelocityUpdatesIn) {
/*  78 */     this.trackedEntity = trackedEntityIn;
/*  79 */     this.trackingDistanceThreshold = trackingDistanceThresholdIn;
/*  80 */     this.updateFrequency = updateFrequencyIn;
/*  81 */     this.sendVelocityUpdates = sendVelocityUpdatesIn;
/*  82 */     this.encodedPosX = MathHelper.floor_double(trackedEntityIn.posX * 32.0D);
/*  83 */     this.encodedPosY = MathHelper.floor_double(trackedEntityIn.posY * 32.0D);
/*  84 */     this.encodedPosZ = MathHelper.floor_double(trackedEntityIn.posZ * 32.0D);
/*  85 */     this.encodedRotationYaw = MathHelper.floor_float(trackedEntityIn.rotationYaw * 256.0F / 360.0F);
/*  86 */     this.encodedRotationPitch = MathHelper.floor_float(trackedEntityIn.rotationPitch * 256.0F / 360.0F);
/*  87 */     this.lastHeadMotion = MathHelper.floor_float(trackedEntityIn.getRotationYawHead() * 256.0F / 360.0F);
/*  88 */     this.onGround = trackedEntityIn.onGround;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object p_equals_1_) {
/*  93 */     return (p_equals_1_ instanceof EntityTrackerEntry) ? ((((EntityTrackerEntry)p_equals_1_).trackedEntity.getEntityId() == this.trackedEntity.getEntityId())) : false;
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/*  98 */     return this.trackedEntity.getEntityId();
/*     */   }
/*     */ 
/*     */   
/*     */   public void updatePlayerList(List<EntityPlayer> players) {
/* 103 */     this.playerEntitiesUpdated = false;
/*     */     
/* 105 */     if (!this.firstUpdateDone || this.trackedEntity.getDistanceSq(this.lastTrackedEntityPosX, this.lastTrackedEntityPosY, this.lastTrackedEntityPosZ) > 16.0D) {
/*     */       
/* 107 */       this.lastTrackedEntityPosX = this.trackedEntity.posX;
/* 108 */       this.lastTrackedEntityPosY = this.trackedEntity.posY;
/* 109 */       this.lastTrackedEntityPosZ = this.trackedEntity.posZ;
/* 110 */       this.firstUpdateDone = true;
/* 111 */       this.playerEntitiesUpdated = true;
/* 112 */       updatePlayerEntities(players);
/*     */     } 
/*     */     
/* 115 */     if (this.field_85178_v != this.trackedEntity.ridingEntity || (this.trackedEntity.ridingEntity != null && this.updateCounter % 60 == 0)) {
/*     */       
/* 117 */       this.field_85178_v = this.trackedEntity.ridingEntity;
/* 118 */       sendPacketToTrackedPlayers((Packet)new S1BPacketEntityAttach(0, this.trackedEntity, this.trackedEntity.ridingEntity));
/*     */     } 
/*     */     
/* 121 */     if (this.trackedEntity instanceof EntityItemFrame && this.updateCounter % 10 == 0) {
/*     */       
/* 123 */       EntityItemFrame entityitemframe = (EntityItemFrame)this.trackedEntity;
/* 124 */       ItemStack itemstack = entityitemframe.getDisplayedItem();
/*     */       
/* 126 */       if (itemstack != null && itemstack.getItem() instanceof net.minecraft.item.ItemMap) {
/*     */         
/* 128 */         MapData mapdata = Items.filled_map.getMapData(itemstack, this.trackedEntity.worldObj);
/*     */         
/* 130 */         for (EntityPlayer entityplayer : players) {
/*     */           
/* 132 */           EntityPlayerMP entityplayermp = (EntityPlayerMP)entityplayer;
/* 133 */           mapdata.updateVisiblePlayers((EntityPlayer)entityplayermp, itemstack);
/* 134 */           Packet packet = Items.filled_map.createMapDataPacket(itemstack, this.trackedEntity.worldObj, (EntityPlayer)entityplayermp);
/*     */           
/* 136 */           if (packet != null)
/*     */           {
/* 138 */             entityplayermp.playerNetServerHandler.sendPacket(packet);
/*     */           }
/*     */         } 
/*     */       } 
/*     */       
/* 143 */       sendMetadataToAllAssociatedPlayers();
/*     */     } 
/*     */     
/* 146 */     if (this.updateCounter % this.updateFrequency == 0 || this.trackedEntity.isAirBorne || this.trackedEntity.getDataWatcher().hasObjectChanged()) {
/*     */       
/* 148 */       if (this.trackedEntity.ridingEntity == null) {
/*     */         S18PacketEntityTeleport s18PacketEntityTeleport;
/* 150 */         this.ticksSinceLastForcedTeleport++;
/* 151 */         int k = MathHelper.floor_double(this.trackedEntity.posX * 32.0D);
/* 152 */         int j1 = MathHelper.floor_double(this.trackedEntity.posY * 32.0D);
/* 153 */         int k1 = MathHelper.floor_double(this.trackedEntity.posZ * 32.0D);
/* 154 */         int l1 = MathHelper.floor_float(this.trackedEntity.rotationYaw * 256.0F / 360.0F);
/* 155 */         int i2 = MathHelper.floor_float(this.trackedEntity.rotationPitch * 256.0F / 360.0F);
/* 156 */         int j2 = k - this.encodedPosX;
/* 157 */         int k2 = j1 - this.encodedPosY;
/* 158 */         int i = k1 - this.encodedPosZ;
/* 159 */         Packet packet1 = null;
/* 160 */         boolean flag = (Math.abs(j2) >= 4 || Math.abs(k2) >= 4 || Math.abs(i) >= 4 || this.updateCounter % 60 == 0);
/* 161 */         boolean flag1 = (Math.abs(l1 - this.encodedRotationYaw) >= 4 || Math.abs(i2 - this.encodedRotationPitch) >= 4);
/*     */         
/* 163 */         if (this.updateCounter > 0 || this.trackedEntity instanceof EntityArrow)
/*     */         {
/* 165 */           if (j2 >= -128 && j2 < 128 && k2 >= -128 && k2 < 128 && i >= -128 && i < 128 && this.ticksSinceLastForcedTeleport <= 400 && !this.ridingEntity && this.onGround == this.trackedEntity.onGround) {
/*     */             
/* 167 */             if ((!flag || !flag1) && !(this.trackedEntity instanceof EntityArrow)) {
/*     */               
/* 169 */               if (flag)
/*     */               {
/* 171 */                 S14PacketEntity.S15PacketEntityRelMove s15PacketEntityRelMove = new S14PacketEntity.S15PacketEntityRelMove(this.trackedEntity.getEntityId(), (byte)j2, (byte)k2, (byte)i, this.trackedEntity.onGround);
/*     */               }
/* 173 */               else if (flag1)
/*     */               {
/* 175 */                 S14PacketEntity.S16PacketEntityLook s16PacketEntityLook = new S14PacketEntity.S16PacketEntityLook(this.trackedEntity.getEntityId(), (byte)l1, (byte)i2, this.trackedEntity.onGround);
/*     */               }
/*     */             
/*     */             } else {
/*     */               
/* 180 */               S14PacketEntity.S17PacketEntityLookMove s17PacketEntityLookMove = new S14PacketEntity.S17PacketEntityLookMove(this.trackedEntity.getEntityId(), (byte)j2, (byte)k2, (byte)i, (byte)l1, (byte)i2, this.trackedEntity.onGround);
/*     */             }
/*     */           
/*     */           } else {
/*     */             
/* 185 */             this.onGround = this.trackedEntity.onGround;
/* 186 */             this.ticksSinceLastForcedTeleport = 0;
/* 187 */             s18PacketEntityTeleport = new S18PacketEntityTeleport(this.trackedEntity.getEntityId(), k, j1, k1, (byte)l1, (byte)i2, this.trackedEntity.onGround);
/*     */           } 
/*     */         }
/*     */         
/* 191 */         if (this.sendVelocityUpdates) {
/*     */           
/* 193 */           double d0 = this.trackedEntity.motionX - this.lastTrackedEntityMotionX;
/* 194 */           double d1 = this.trackedEntity.motionY - this.lastTrackedEntityMotionY;
/* 195 */           double d2 = this.trackedEntity.motionZ - this.motionZ;
/* 196 */           double d3 = 0.02D;
/* 197 */           double d4 = d0 * d0 + d1 * d1 + d2 * d2;
/*     */           
/* 199 */           if (d4 > d3 * d3 || (d4 > 0.0D && this.trackedEntity.motionX == 0.0D && this.trackedEntity.motionY == 0.0D && this.trackedEntity.motionZ == 0.0D)) {
/*     */             
/* 201 */             this.lastTrackedEntityMotionX = this.trackedEntity.motionX;
/* 202 */             this.lastTrackedEntityMotionY = this.trackedEntity.motionY;
/* 203 */             this.motionZ = this.trackedEntity.motionZ;
/* 204 */             sendPacketToTrackedPlayers((Packet)new S12PacketEntityVelocity(this.trackedEntity.getEntityId(), this.lastTrackedEntityMotionX, this.lastTrackedEntityMotionY, this.motionZ));
/*     */           } 
/*     */         } 
/*     */         
/* 208 */         if (s18PacketEntityTeleport != null)
/*     */         {
/* 210 */           sendPacketToTrackedPlayers((Packet)s18PacketEntityTeleport);
/*     */         }
/*     */         
/* 213 */         sendMetadataToAllAssociatedPlayers();
/*     */         
/* 215 */         if (flag) {
/*     */           
/* 217 */           this.encodedPosX = k;
/* 218 */           this.encodedPosY = j1;
/* 219 */           this.encodedPosZ = k1;
/*     */         } 
/*     */         
/* 222 */         if (flag1) {
/*     */           
/* 224 */           this.encodedRotationYaw = l1;
/* 225 */           this.encodedRotationPitch = i2;
/*     */         } 
/*     */         
/* 228 */         this.ridingEntity = false;
/*     */       }
/*     */       else {
/*     */         
/* 232 */         int j = MathHelper.floor_float(this.trackedEntity.rotationYaw * 256.0F / 360.0F);
/* 233 */         int i1 = MathHelper.floor_float(this.trackedEntity.rotationPitch * 256.0F / 360.0F);
/* 234 */         boolean flag2 = (Math.abs(j - this.encodedRotationYaw) >= 4 || Math.abs(i1 - this.encodedRotationPitch) >= 4);
/*     */         
/* 236 */         if (flag2) {
/*     */           
/* 238 */           sendPacketToTrackedPlayers((Packet)new S14PacketEntity.S16PacketEntityLook(this.trackedEntity.getEntityId(), (byte)j, (byte)i1, this.trackedEntity.onGround));
/* 239 */           this.encodedRotationYaw = j;
/* 240 */           this.encodedRotationPitch = i1;
/*     */         } 
/*     */         
/* 243 */         this.encodedPosX = MathHelper.floor_double(this.trackedEntity.posX * 32.0D);
/* 244 */         this.encodedPosY = MathHelper.floor_double(this.trackedEntity.posY * 32.0D);
/* 245 */         this.encodedPosZ = MathHelper.floor_double(this.trackedEntity.posZ * 32.0D);
/* 246 */         sendMetadataToAllAssociatedPlayers();
/* 247 */         this.ridingEntity = true;
/*     */       } 
/*     */       
/* 250 */       int l = MathHelper.floor_float(this.trackedEntity.getRotationYawHead() * 256.0F / 360.0F);
/*     */       
/* 252 */       if (Math.abs(l - this.lastHeadMotion) >= 4) {
/*     */         
/* 254 */         sendPacketToTrackedPlayers((Packet)new S19PacketEntityHeadLook(this.trackedEntity, (byte)l));
/* 255 */         this.lastHeadMotion = l;
/*     */       } 
/*     */       
/* 258 */       this.trackedEntity.isAirBorne = false;
/*     */     } 
/*     */     
/* 261 */     this.updateCounter++;
/*     */     
/* 263 */     if (this.trackedEntity.velocityChanged) {
/*     */       
/* 265 */       func_151261_b((Packet)new S12PacketEntityVelocity(this.trackedEntity));
/* 266 */       this.trackedEntity.velocityChanged = false;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void sendMetadataToAllAssociatedPlayers() {
/* 276 */     DataWatcher datawatcher = this.trackedEntity.getDataWatcher();
/*     */     
/* 278 */     if (datawatcher.hasObjectChanged())
/*     */     {
/* 280 */       func_151261_b((Packet)new S1CPacketEntityMetadata(this.trackedEntity.getEntityId(), datawatcher, false));
/*     */     }
/*     */     
/* 283 */     if (this.trackedEntity instanceof EntityLivingBase) {
/*     */       
/* 285 */       ServersideAttributeMap serversideattributemap = (ServersideAttributeMap)((EntityLivingBase)this.trackedEntity).getAttributeMap();
/* 286 */       Set<IAttributeInstance> set = serversideattributemap.getAttributeInstanceSet();
/*     */       
/* 288 */       if (!set.isEmpty())
/*     */       {
/* 290 */         func_151261_b((Packet)new S20PacketEntityProperties(this.trackedEntity.getEntityId(), set));
/*     */       }
/*     */       
/* 293 */       set.clear();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void sendPacketToTrackedPlayers(Packet packetIn) {
/* 302 */     for (EntityPlayerMP entityplayermp : this.trackingPlayers)
/*     */     {
/* 304 */       entityplayermp.playerNetServerHandler.sendPacket(packetIn);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_151261_b(Packet packetIn) {
/* 310 */     sendPacketToTrackedPlayers(packetIn);
/*     */     
/* 312 */     if (this.trackedEntity instanceof EntityPlayerMP)
/*     */     {
/* 314 */       ((EntityPlayerMP)this.trackedEntity).playerNetServerHandler.sendPacket(packetIn);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void sendDestroyEntityPacketToTrackedPlayers() {
/* 320 */     for (EntityPlayerMP entityplayermp : this.trackingPlayers)
/*     */     {
/* 322 */       entityplayermp.removeEntity(this.trackedEntity);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void removeFromTrackedPlayers(EntityPlayerMP playerMP) {
/* 328 */     if (this.trackingPlayers.contains(playerMP)) {
/*     */       
/* 330 */       playerMP.removeEntity(this.trackedEntity);
/* 331 */       this.trackingPlayers.remove(playerMP);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void updatePlayerEntity(EntityPlayerMP playerMP) {
/* 337 */     if (playerMP != this.trackedEntity)
/*     */     {
/* 339 */       if (func_180233_c(playerMP)) {
/*     */         
/* 341 */         if (!this.trackingPlayers.contains(playerMP) && (isPlayerWatchingThisChunk(playerMP) || this.trackedEntity.forceSpawn)) {
/*     */           
/* 343 */           this.trackingPlayers.add(playerMP);
/* 344 */           Packet packet = createSpawnPacket();
/* 345 */           playerMP.playerNetServerHandler.sendPacket(packet);
/*     */           
/* 347 */           if (!this.trackedEntity.getDataWatcher().getIsBlank())
/*     */           {
/* 349 */             playerMP.playerNetServerHandler.sendPacket((Packet)new S1CPacketEntityMetadata(this.trackedEntity.getEntityId(), this.trackedEntity.getDataWatcher(), true));
/*     */           }
/*     */           
/* 352 */           NBTTagCompound nbttagcompound = this.trackedEntity.getNBTTagCompound();
/*     */           
/* 354 */           if (nbttagcompound != null)
/*     */           {
/* 356 */             playerMP.playerNetServerHandler.sendPacket((Packet)new S49PacketUpdateEntityNBT(this.trackedEntity.getEntityId(), nbttagcompound));
/*     */           }
/*     */           
/* 359 */           if (this.trackedEntity instanceof EntityLivingBase) {
/*     */             
/* 361 */             ServersideAttributeMap serversideattributemap = (ServersideAttributeMap)((EntityLivingBase)this.trackedEntity).getAttributeMap();
/* 362 */             Collection<IAttributeInstance> collection = serversideattributemap.getWatchedAttributes();
/*     */             
/* 364 */             if (!collection.isEmpty())
/*     */             {
/* 366 */               playerMP.playerNetServerHandler.sendPacket((Packet)new S20PacketEntityProperties(this.trackedEntity.getEntityId(), collection));
/*     */             }
/*     */           } 
/*     */           
/* 370 */           this.lastTrackedEntityMotionX = this.trackedEntity.motionX;
/* 371 */           this.lastTrackedEntityMotionY = this.trackedEntity.motionY;
/* 372 */           this.motionZ = this.trackedEntity.motionZ;
/*     */           
/* 374 */           if (this.sendVelocityUpdates && !(packet instanceof S0FPacketSpawnMob))
/*     */           {
/* 376 */             playerMP.playerNetServerHandler.sendPacket((Packet)new S12PacketEntityVelocity(this.trackedEntity.getEntityId(), this.trackedEntity.motionX, this.trackedEntity.motionY, this.trackedEntity.motionZ));
/*     */           }
/*     */           
/* 379 */           if (this.trackedEntity.ridingEntity != null)
/*     */           {
/* 381 */             playerMP.playerNetServerHandler.sendPacket((Packet)new S1BPacketEntityAttach(0, this.trackedEntity, this.trackedEntity.ridingEntity));
/*     */           }
/*     */           
/* 384 */           if (this.trackedEntity instanceof EntityLiving && ((EntityLiving)this.trackedEntity).getLeashedToEntity() != null)
/*     */           {
/* 386 */             playerMP.playerNetServerHandler.sendPacket((Packet)new S1BPacketEntityAttach(1, this.trackedEntity, ((EntityLiving)this.trackedEntity).getLeashedToEntity()));
/*     */           }
/*     */           
/* 389 */           if (this.trackedEntity instanceof EntityLivingBase)
/*     */           {
/* 391 */             for (int i = 0; i < 5; i++) {
/*     */               
/* 393 */               ItemStack itemstack = ((EntityLivingBase)this.trackedEntity).getEquipmentInSlot(i);
/*     */               
/* 395 */               if (itemstack != null)
/*     */               {
/* 397 */                 playerMP.playerNetServerHandler.sendPacket((Packet)new S04PacketEntityEquipment(this.trackedEntity.getEntityId(), i, itemstack));
/*     */               }
/*     */             } 
/*     */           }
/*     */           
/* 402 */           if (this.trackedEntity instanceof EntityPlayer) {
/*     */             
/* 404 */             EntityPlayer entityplayer = (EntityPlayer)this.trackedEntity;
/*     */             
/* 406 */             if (entityplayer.isPlayerSleeping())
/*     */             {
/* 408 */               playerMP.playerNetServerHandler.sendPacket((Packet)new S0APacketUseBed(entityplayer, new BlockPos(this.trackedEntity)));
/*     */             }
/*     */           } 
/*     */           
/* 412 */           if (this.trackedEntity instanceof EntityLivingBase)
/*     */           {
/* 414 */             EntityLivingBase entitylivingbase = (EntityLivingBase)this.trackedEntity;
/*     */             
/* 416 */             for (PotionEffect potioneffect : entitylivingbase.getActivePotionEffects())
/*     */             {
/* 418 */               playerMP.playerNetServerHandler.sendPacket((Packet)new S1DPacketEntityEffect(this.trackedEntity.getEntityId(), potioneffect));
/*     */             }
/*     */           }
/*     */         
/*     */         } 
/* 423 */       } else if (this.trackingPlayers.contains(playerMP)) {
/*     */         
/* 425 */         this.trackingPlayers.remove(playerMP);
/* 426 */         playerMP.removeEntity(this.trackedEntity);
/*     */       } 
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean func_180233_c(EntityPlayerMP playerMP) {
/* 433 */     double d0 = playerMP.posX - (this.encodedPosX / 32);
/* 434 */     double d1 = playerMP.posZ - (this.encodedPosZ / 32);
/* 435 */     return (d0 >= -this.trackingDistanceThreshold && d0 <= this.trackingDistanceThreshold && d1 >= -this.trackingDistanceThreshold && d1 <= this.trackingDistanceThreshold && this.trackedEntity.isSpectatedByPlayer(playerMP));
/*     */   }
/*     */ 
/*     */   
/*     */   private boolean isPlayerWatchingThisChunk(EntityPlayerMP playerMP) {
/* 440 */     return playerMP.getServerForPlayer().getPlayerManager().isPlayerWatchingChunk(playerMP, this.trackedEntity.chunkCoordX, this.trackedEntity.chunkCoordZ);
/*     */   }
/*     */ 
/*     */   
/*     */   public void updatePlayerEntities(List<EntityPlayer> players) {
/* 445 */     for (int i = 0; i < players.size(); i++)
/*     */     {
/* 447 */       updatePlayerEntity((EntityPlayerMP)players.get(i));
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private Packet createSpawnPacket() {
/* 456 */     if (this.trackedEntity.isDead)
/*     */     {
/* 458 */       logger.warn("Fetching addPacket for removed entity");
/*     */     }
/*     */     
/* 461 */     if (this.trackedEntity instanceof net.minecraft.entity.item.EntityItem)
/*     */     {
/* 463 */       return (Packet)new S0EPacketSpawnObject(this.trackedEntity, 2, 1);
/*     */     }
/* 465 */     if (this.trackedEntity instanceof EntityPlayerMP)
/*     */     {
/* 467 */       return (Packet)new S0CPacketSpawnPlayer((EntityPlayer)this.trackedEntity);
/*     */     }
/* 469 */     if (this.trackedEntity instanceof EntityMinecart) {
/*     */       
/* 471 */       EntityMinecart entityminecart = (EntityMinecart)this.trackedEntity;
/* 472 */       return (Packet)new S0EPacketSpawnObject(this.trackedEntity, 10, entityminecart.getMinecartType().getNetworkID());
/*     */     } 
/* 474 */     if (this.trackedEntity instanceof net.minecraft.entity.item.EntityBoat)
/*     */     {
/* 476 */       return (Packet)new S0EPacketSpawnObject(this.trackedEntity, 1);
/*     */     }
/* 478 */     if (this.trackedEntity instanceof net.minecraft.entity.passive.IAnimals) {
/*     */       
/* 480 */       this.lastHeadMotion = MathHelper.floor_float(this.trackedEntity.getRotationYawHead() * 256.0F / 360.0F);
/* 481 */       return (Packet)new S0FPacketSpawnMob((EntityLivingBase)this.trackedEntity);
/*     */     } 
/* 483 */     if (this.trackedEntity instanceof EntityFishHook) {
/*     */       
/* 485 */       EntityPlayer entityPlayer = ((EntityFishHook)this.trackedEntity).angler;
/* 486 */       return (Packet)new S0EPacketSpawnObject(this.trackedEntity, 90, (entityPlayer != null) ? entityPlayer.getEntityId() : this.trackedEntity.getEntityId());
/*     */     } 
/* 488 */     if (this.trackedEntity instanceof EntityArrow) {
/*     */       
/* 490 */       Entity entity = ((EntityArrow)this.trackedEntity).shootingEntity;
/* 491 */       return (Packet)new S0EPacketSpawnObject(this.trackedEntity, 60, (entity != null) ? entity.getEntityId() : this.trackedEntity.getEntityId());
/*     */     } 
/* 493 */     if (this.trackedEntity instanceof net.minecraft.entity.projectile.EntitySnowball)
/*     */     {
/* 495 */       return (Packet)new S0EPacketSpawnObject(this.trackedEntity, 61);
/*     */     }
/* 497 */     if (this.trackedEntity instanceof EntityPotion)
/*     */     {
/* 499 */       return (Packet)new S0EPacketSpawnObject(this.trackedEntity, 73, ((EntityPotion)this.trackedEntity).getPotionDamage());
/*     */     }
/* 501 */     if (this.trackedEntity instanceof net.minecraft.entity.item.EntityExpBottle)
/*     */     {
/* 503 */       return (Packet)new S0EPacketSpawnObject(this.trackedEntity, 75);
/*     */     }
/* 505 */     if (this.trackedEntity instanceof net.minecraft.entity.item.EntityEnderPearl)
/*     */     {
/* 507 */       return (Packet)new S0EPacketSpawnObject(this.trackedEntity, 65);
/*     */     }
/* 509 */     if (this.trackedEntity instanceof net.minecraft.entity.item.EntityEnderEye)
/*     */     {
/* 511 */       return (Packet)new S0EPacketSpawnObject(this.trackedEntity, 72);
/*     */     }
/* 513 */     if (this.trackedEntity instanceof net.minecraft.entity.item.EntityFireworkRocket)
/*     */     {
/* 515 */       return (Packet)new S0EPacketSpawnObject(this.trackedEntity, 76);
/*     */     }
/* 517 */     if (this.trackedEntity instanceof EntityFireball) {
/*     */       
/* 519 */       EntityFireball entityfireball = (EntityFireball)this.trackedEntity;
/* 520 */       S0EPacketSpawnObject s0epacketspawnobject2 = null;
/* 521 */       int i = 63;
/*     */       
/* 523 */       if (this.trackedEntity instanceof net.minecraft.entity.projectile.EntitySmallFireball) {
/*     */         
/* 525 */         i = 64;
/*     */       }
/* 527 */       else if (this.trackedEntity instanceof net.minecraft.entity.projectile.EntityWitherSkull) {
/*     */         
/* 529 */         i = 66;
/*     */       } 
/*     */       
/* 532 */       if (entityfireball.shootingEntity != null) {
/*     */         
/* 534 */         s0epacketspawnobject2 = new S0EPacketSpawnObject(this.trackedEntity, i, ((EntityFireball)this.trackedEntity).shootingEntity.getEntityId());
/*     */       }
/*     */       else {
/*     */         
/* 538 */         s0epacketspawnobject2 = new S0EPacketSpawnObject(this.trackedEntity, i, 0);
/*     */       } 
/*     */       
/* 541 */       s0epacketspawnobject2.setSpeedX((int)(entityfireball.accelerationX * 8000.0D));
/* 542 */       s0epacketspawnobject2.setSpeedY((int)(entityfireball.accelerationY * 8000.0D));
/* 543 */       s0epacketspawnobject2.setSpeedZ((int)(entityfireball.accelerationZ * 8000.0D));
/* 544 */       return (Packet)s0epacketspawnobject2;
/*     */     } 
/* 546 */     if (this.trackedEntity instanceof net.minecraft.entity.projectile.EntityEgg)
/*     */     {
/* 548 */       return (Packet)new S0EPacketSpawnObject(this.trackedEntity, 62);
/*     */     }
/* 550 */     if (this.trackedEntity instanceof net.minecraft.entity.item.EntityTNTPrimed)
/*     */     {
/* 552 */       return (Packet)new S0EPacketSpawnObject(this.trackedEntity, 50);
/*     */     }
/* 554 */     if (this.trackedEntity instanceof net.minecraft.entity.item.EntityEnderCrystal)
/*     */     {
/* 556 */       return (Packet)new S0EPacketSpawnObject(this.trackedEntity, 51);
/*     */     }
/* 558 */     if (this.trackedEntity instanceof EntityFallingBlock) {
/*     */       
/* 560 */       EntityFallingBlock entityfallingblock = (EntityFallingBlock)this.trackedEntity;
/* 561 */       return (Packet)new S0EPacketSpawnObject(this.trackedEntity, 70, Block.getStateId(entityfallingblock.getBlock()));
/*     */     } 
/* 563 */     if (this.trackedEntity instanceof net.minecraft.entity.item.EntityArmorStand)
/*     */     {
/* 565 */       return (Packet)new S0EPacketSpawnObject(this.trackedEntity, 78);
/*     */     }
/* 567 */     if (this.trackedEntity instanceof EntityPainting)
/*     */     {
/* 569 */       return (Packet)new S10PacketSpawnPainting((EntityPainting)this.trackedEntity);
/*     */     }
/* 571 */     if (this.trackedEntity instanceof EntityItemFrame) {
/*     */       
/* 573 */       EntityItemFrame entityitemframe = (EntityItemFrame)this.trackedEntity;
/* 574 */       S0EPacketSpawnObject s0epacketspawnobject1 = new S0EPacketSpawnObject(this.trackedEntity, 71, entityitemframe.facingDirection.getHorizontalIndex());
/* 575 */       BlockPos blockpos1 = entityitemframe.getHangingPosition();
/* 576 */       s0epacketspawnobject1.setX(MathHelper.floor_float((blockpos1.getX() << 5)));
/* 577 */       s0epacketspawnobject1.setY(MathHelper.floor_float((blockpos1.getY() << 5)));
/* 578 */       s0epacketspawnobject1.setZ(MathHelper.floor_float((blockpos1.getZ() << 5)));
/* 579 */       return (Packet)s0epacketspawnobject1;
/*     */     } 
/* 581 */     if (this.trackedEntity instanceof EntityLeashKnot) {
/*     */       
/* 583 */       EntityLeashKnot entityleashknot = (EntityLeashKnot)this.trackedEntity;
/* 584 */       S0EPacketSpawnObject s0epacketspawnobject = new S0EPacketSpawnObject(this.trackedEntity, 77);
/* 585 */       BlockPos blockpos = entityleashknot.getHangingPosition();
/* 586 */       s0epacketspawnobject.setX(MathHelper.floor_float((blockpos.getX() << 5)));
/* 587 */       s0epacketspawnobject.setY(MathHelper.floor_float((blockpos.getY() << 5)));
/* 588 */       s0epacketspawnobject.setZ(MathHelper.floor_float((blockpos.getZ() << 5)));
/* 589 */       return (Packet)s0epacketspawnobject;
/*     */     } 
/* 591 */     if (this.trackedEntity instanceof EntityXPOrb)
/*     */     {
/* 593 */       return (Packet)new S11PacketSpawnExperienceOrb((EntityXPOrb)this.trackedEntity);
/*     */     }
/*     */ 
/*     */     
/* 597 */     throw new IllegalArgumentException("Don't know how to add " + this.trackedEntity.getClass() + "!");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void removeTrackedPlayerSymmetric(EntityPlayerMP playerMP) {
/* 606 */     if (this.trackingPlayers.contains(playerMP)) {
/*     */       
/* 608 */       this.trackingPlayers.remove(playerMP);
/* 609 */       playerMP.removeEntity(this.trackedEntity);
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\minecraft\entity\EntityTrackerEntry.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */