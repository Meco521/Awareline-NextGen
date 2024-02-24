/*      */ package net.minecraft.network;
/*      */ import com.google.common.primitives.Doubles;
/*      */ import io.netty.util.concurrent.Future;
/*      */ import java.util.List;
/*      */ import java.util.Set;
/*      */ import net.minecraft.command.server.CommandBlockLogic;
/*      */ import net.minecraft.crash.CrashReport;
/*      */ import net.minecraft.entity.Entity;
/*      */ import net.minecraft.entity.item.EntityItem;
/*      */ import net.minecraft.entity.player.EntityPlayer;
/*      */ import net.minecraft.entity.player.EntityPlayerMP;
/*      */ import net.minecraft.init.Items;
/*      */ import net.minecraft.inventory.Container;
/*      */ import net.minecraft.inventory.ContainerBeacon;
/*      */ import net.minecraft.inventory.ContainerRepair;
/*      */ import net.minecraft.inventory.IInventory;
/*      */ import net.minecraft.inventory.Slot;
/*      */ import net.minecraft.item.ItemStack;
/*      */ import net.minecraft.nbt.NBTBase;
/*      */ import net.minecraft.nbt.NBTTagCompound;
/*      */ import net.minecraft.network.play.client.C02PacketUseEntity;
/*      */ import net.minecraft.network.play.client.C03PacketPlayer;
/*      */ import net.minecraft.network.play.client.C07PacketPlayerDigging;
/*      */ import net.minecraft.network.play.client.C08PacketPlayerBlockPlacement;
/*      */ import net.minecraft.network.play.client.C09PacketHeldItemChange;
/*      */ import net.minecraft.network.play.client.C0BPacketEntityAction;
/*      */ import net.minecraft.network.play.client.C0CPacketInput;
/*      */ import net.minecraft.network.play.client.C0EPacketClickWindow;
/*      */ import net.minecraft.network.play.client.C0FPacketConfirmTransaction;
/*      */ import net.minecraft.network.play.client.C10PacketCreativeInventoryAction;
/*      */ import net.minecraft.network.play.client.C11PacketEnchantItem;
/*      */ import net.minecraft.network.play.client.C12PacketUpdateSign;
/*      */ import net.minecraft.network.play.client.C13PacketPlayerAbilities;
/*      */ import net.minecraft.network.play.client.C14PacketTabComplete;
/*      */ import net.minecraft.network.play.client.C16PacketClientStatus;
/*      */ import net.minecraft.network.play.client.C17PacketCustomPayload;
/*      */ import net.minecraft.network.play.server.S02PacketChat;
/*      */ import net.minecraft.network.play.server.S08PacketPlayerPosLook;
/*      */ import net.minecraft.network.play.server.S23PacketBlockChange;
/*      */ import net.minecraft.server.MinecraftServer;
/*      */ import net.minecraft.tileentity.TileEntity;
/*      */ import net.minecraft.tileentity.TileEntitySign;
/*      */ import net.minecraft.util.BlockPos;
/*      */ import net.minecraft.util.ChatComponentText;
/*      */ import net.minecraft.util.ChatComponentTranslation;
/*      */ import net.minecraft.util.EnumChatFormatting;
/*      */ import net.minecraft.util.EnumFacing;
/*      */ import net.minecraft.util.IChatComponent;
/*      */ import net.minecraft.util.IThreadListener;
/*      */ import net.minecraft.world.World;
/*      */ import net.minecraft.world.WorldServer;
/*      */ 
/*      */ public class NetHandlerPlayServer implements INetHandlerPlayServer, ITickable {
/*   54 */   private static final Logger logger = LogManager.getLogger();
/*      */   public final NetworkManager netManager;
/*      */   private final MinecraftServer serverController;
/*   57 */   private final IntHashMap<Short> field_147372_n = new IntHashMap();
/*      */   
/*      */   public EntityPlayerMP playerEntity;
/*      */   
/*      */   private int networkTickCount;
/*      */   
/*      */   private int field_175090_f;
/*      */   
/*      */   private int floatingTickCount;
/*      */   
/*      */   private boolean field_147366_g;
/*      */   
/*      */   private int field_147378_h;
/*      */   
/*      */   private long lastPingTime;
/*      */   
/*      */   private long lastSentPingPacket;
/*      */   private int chatSpamThresholdCount;
/*      */   private int itemDropThreshold;
/*      */   private double lastPosX;
/*      */   private double lastPosY;
/*      */   private double lastPosZ;
/*      */   private boolean hasMoved = true;
/*      */   
/*      */   public NetHandlerPlayServer(MinecraftServer server, NetworkManager networkManagerIn, EntityPlayerMP playerIn) {
/*   82 */     this.serverController = server;
/*   83 */     this.netManager = networkManagerIn;
/*   84 */     networkManagerIn.setNetHandler((INetHandler)this);
/*   85 */     this.playerEntity = playerIn;
/*   86 */     playerIn.playerNetServerHandler = this;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void update() {
/*   93 */     this.field_147366_g = false;
/*   94 */     this.networkTickCount++;
/*   95 */     this.serverController.theProfiler.startSection("keepAlive");
/*      */     
/*   97 */     if (this.networkTickCount - this.lastSentPingPacket > 40L) {
/*   98 */       this.lastSentPingPacket = this.networkTickCount;
/*   99 */       this.lastPingTime = currentTimeMillis();
/*  100 */       this.field_147378_h = (int)this.lastPingTime;
/*  101 */       sendPacket((Packet)new S00PacketKeepAlive(this.field_147378_h));
/*      */     } 
/*      */     
/*  104 */     this.serverController.theProfiler.endSection();
/*      */     
/*  106 */     if (this.chatSpamThresholdCount > 0) {
/*  107 */       this.chatSpamThresholdCount--;
/*      */     }
/*      */     
/*  110 */     if (this.itemDropThreshold > 0) {
/*  111 */       this.itemDropThreshold--;
/*      */     }
/*      */     
/*  114 */     if (this.playerEntity.getLastActiveTime() > 0L && this.serverController.getMaxPlayerIdleMinutes() > 0 && MinecraftServer.getCurrentTimeMillis() - this.playerEntity.getLastActiveTime() > (this.serverController.getMaxPlayerIdleMinutes() * 1000 * 60)) {
/*  115 */       kickPlayerFromServer("You have been idle for too long!");
/*      */     }
/*      */   }
/*      */   
/*      */   public NetworkManager getNetworkManager() {
/*  120 */     return this.netManager;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void kickPlayerFromServer(String reason) {
/*  127 */     final ChatComponentText chatcomponenttext = new ChatComponentText(reason);
/*  128 */     this.netManager.sendPacket((Packet)new S40PacketDisconnect((IChatComponent)chatcomponenttext), new GenericFutureListener<Future<? super Void>>() {
/*      */           public void operationComplete(Future<? super Void> p_operationComplete_1_) {
/*  130 */             NetHandlerPlayServer.this.netManager.closeChannel((IChatComponent)chatcomponenttext);
/*      */           }
/*      */         },  (GenericFutureListener<? extends Future<? super Void>>[])new GenericFutureListener[0]);
/*  133 */     this.netManager.disableAutoRead();
/*  134 */     Futures.getUnchecked((Future)this.serverController.addScheduledTask(new Runnable() {
/*      */             public void run() {
/*  136 */               NetHandlerPlayServer.this.netManager.checkDisconnected();
/*      */             }
/*      */           }));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void processInput(C0CPacketInput packetIn) {
/*  146 */     PacketThreadUtil.checkThreadAndEnqueue((Packet<NetHandlerPlayServer>)packetIn, this, (IThreadListener)this.playerEntity.getServerForPlayer());
/*  147 */     this.playerEntity.setEntityActionState(packetIn.getStrafeSpeed(), packetIn.getForwardSpeed(), packetIn.isJumping(), packetIn.isSneaking());
/*      */   }
/*      */   
/*      */   private boolean func_183006_b(C03PacketPlayer p_183006_1_) {
/*  151 */     return (!Doubles.isFinite(p_183006_1_.getPositionX()) || !Doubles.isFinite(p_183006_1_.getPositionY()) || !Doubles.isFinite(p_183006_1_.getPositionZ()) || !Floats.isFinite(p_183006_1_.getPitch()) || !Floats.isFinite(p_183006_1_.getYaw()));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void processPlayer(C03PacketPlayer packetIn) {
/*  158 */     PacketThreadUtil.checkThreadAndEnqueue((Packet<NetHandlerPlayServer>)packetIn, this, (IThreadListener)this.playerEntity.getServerForPlayer());
/*      */     
/*  160 */     if (func_183006_b(packetIn)) {
/*  161 */       kickPlayerFromServer("Invalid move packet received");
/*      */     } else {
/*  163 */       WorldServer worldserver = this.serverController.worldServerForDimension(this.playerEntity.dimension);
/*  164 */       this.field_147366_g = true;
/*      */       
/*  166 */       if (!this.playerEntity.playerConqueredTheEnd) {
/*  167 */         double d0 = this.playerEntity.posX;
/*  168 */         double d1 = this.playerEntity.posY;
/*  169 */         double d2 = this.playerEntity.posZ;
/*  170 */         double d3 = 0.0D;
/*  171 */         double d4 = packetIn.getPositionX() - this.lastPosX;
/*  172 */         double d5 = packetIn.getPositionY() - this.lastPosY;
/*  173 */         double d6 = packetIn.getPositionZ() - this.lastPosZ;
/*      */         
/*  175 */         if (packetIn.isMoving()) {
/*  176 */           d3 = d4 * d4 + d5 * d5 + d6 * d6;
/*      */           
/*  178 */           if (!this.hasMoved && d3 < 0.25D) {
/*  179 */             this.hasMoved = true;
/*      */           }
/*      */         } 
/*      */         
/*  183 */         if (this.hasMoved) {
/*  184 */           this.field_175090_f = this.networkTickCount;
/*      */           
/*  186 */           if (this.playerEntity.ridingEntity != null) {
/*  187 */             float f4 = this.playerEntity.rotationYaw;
/*  188 */             float f = this.playerEntity.rotationPitch;
/*  189 */             this.playerEntity.ridingEntity.updateRiderPosition();
/*  190 */             double d16 = this.playerEntity.posX;
/*  191 */             double d17 = this.playerEntity.posY;
/*  192 */             double d18 = this.playerEntity.posZ;
/*      */             
/*  194 */             if (packetIn.getRotating()) {
/*  195 */               f4 = packetIn.getYaw();
/*  196 */               f = packetIn.getPitch();
/*      */             } 
/*      */             
/*  199 */             this.playerEntity.onGround = packetIn.isOnGround();
/*  200 */             this.playerEntity.onUpdateEntity();
/*  201 */             this.playerEntity.setPositionAndRotation(d16, d17, d18, f4, f);
/*      */             
/*  203 */             if (this.playerEntity.ridingEntity != null) {
/*  204 */               this.playerEntity.ridingEntity.updateRiderPosition();
/*      */             }
/*      */             
/*  207 */             this.serverController.getConfigurationManager().serverUpdateMountedMovingPlayer(this.playerEntity);
/*      */             
/*  209 */             if (this.playerEntity.ridingEntity != null) {
/*  210 */               if (d3 > 4.0D) {
/*  211 */                 Entity entity = this.playerEntity.ridingEntity;
/*  212 */                 this.playerEntity.playerNetServerHandler.sendPacket((Packet)new S18PacketEntityTeleport(entity));
/*  213 */                 setPlayerLocation(this.playerEntity.posX, this.playerEntity.posY, this.playerEntity.posZ, this.playerEntity.rotationYaw, this.playerEntity.rotationPitch);
/*      */               } 
/*      */               
/*  216 */               this.playerEntity.ridingEntity.isAirBorne = true;
/*      */             } 
/*      */             
/*  219 */             if (this.hasMoved) {
/*  220 */               this.lastPosX = this.playerEntity.posX;
/*  221 */               this.lastPosY = this.playerEntity.posY;
/*  222 */               this.lastPosZ = this.playerEntity.posZ;
/*      */             } 
/*      */             
/*  225 */             worldserver.updateEntity((Entity)this.playerEntity);
/*      */             
/*      */             return;
/*      */           } 
/*  229 */           if (this.playerEntity.isPlayerSleeping()) {
/*  230 */             this.playerEntity.onUpdateEntity();
/*  231 */             this.playerEntity.setPositionAndRotation(this.lastPosX, this.lastPosY, this.lastPosZ, this.playerEntity.rotationYaw, this.playerEntity.rotationPitch);
/*  232 */             worldserver.updateEntity((Entity)this.playerEntity);
/*      */             
/*      */             return;
/*      */           } 
/*  236 */           double d7 = this.playerEntity.posY;
/*  237 */           this.lastPosX = this.playerEntity.posX;
/*  238 */           this.lastPosY = this.playerEntity.posY;
/*  239 */           this.lastPosZ = this.playerEntity.posZ;
/*  240 */           double d8 = this.playerEntity.posX;
/*  241 */           double d9 = this.playerEntity.posY;
/*  242 */           double d10 = this.playerEntity.posZ;
/*  243 */           float f1 = this.playerEntity.rotationYaw;
/*  244 */           float f2 = this.playerEntity.rotationPitch;
/*      */           
/*  246 */           if (packetIn.isMoving() && packetIn.getPositionY() == -999.0D) {
/*  247 */             packetIn.setMoving(false);
/*      */           }
/*      */           
/*  250 */           if (packetIn.isMoving()) {
/*  251 */             d8 = packetIn.getPositionX();
/*  252 */             d9 = packetIn.getPositionY();
/*  253 */             d10 = packetIn.getPositionZ();
/*      */             
/*  255 */             if (Math.abs(packetIn.getPositionX()) > 3.0E7D || Math.abs(packetIn.getPositionZ()) > 3.0E7D) {
/*  256 */               kickPlayerFromServer("Illegal position");
/*      */               
/*      */               return;
/*      */             } 
/*      */           } 
/*  261 */           if (packetIn.getRotating()) {
/*  262 */             f1 = packetIn.getYaw();
/*  263 */             f2 = packetIn.getPitch();
/*      */           } 
/*      */           
/*  266 */           this.playerEntity.onUpdateEntity();
/*  267 */           this.playerEntity.setPositionAndRotation(this.lastPosX, this.lastPosY, this.lastPosZ, f1, f2);
/*      */           
/*  269 */           if (!this.hasMoved) {
/*      */             return;
/*      */           }
/*      */           
/*  273 */           double d11 = d8 - this.playerEntity.posX;
/*  274 */           double d12 = d9 - this.playerEntity.posY;
/*  275 */           double d13 = d10 - this.playerEntity.posZ;
/*  276 */           double d14 = this.playerEntity.motionX * this.playerEntity.motionX + this.playerEntity.motionY * this.playerEntity.motionY + this.playerEntity.motionZ * this.playerEntity.motionZ;
/*  277 */           double d15 = d11 * d11 + d12 * d12 + d13 * d13;
/*      */           
/*  279 */           if (d15 - d14 > 100.0D && (!this.serverController.isSinglePlayer() || !this.serverController.getServerOwner().equals(this.playerEntity.getName()))) {
/*  280 */             logger.warn(this.playerEntity.getName() + " moved too quickly! " + d11 + "," + d12 + "," + d13 + " (" + d11 + ", " + d12 + ", " + d13 + ")");
/*  281 */             setPlayerLocation(this.lastPosX, this.lastPosY, this.lastPosZ, this.playerEntity.rotationYaw, this.playerEntity.rotationPitch);
/*      */             
/*      */             return;
/*      */           } 
/*  285 */           float f3 = 0.0625F;
/*  286 */           boolean flag = worldserver.getCollidingBoundingBoxes((Entity)this.playerEntity, this.playerEntity.getEntityBoundingBox().contract(f3, f3, f3)).isEmpty();
/*      */           
/*  288 */           if (this.playerEntity.onGround && !packetIn.isOnGround() && d12 > 0.0D) {
/*  289 */             this.playerEntity.jump();
/*      */           }
/*      */           
/*  292 */           this.playerEntity.moveEntity(d11, d12, d13);
/*  293 */           this.playerEntity.onGround = packetIn.isOnGround();
/*  294 */           d11 = d8 - this.playerEntity.posX;
/*  295 */           d12 = d9 - this.playerEntity.posY;
/*      */           
/*  297 */           if (d12 > -0.5D || d12 < 0.5D) {
/*  298 */             d12 = 0.0D;
/*      */           }
/*      */           
/*  301 */           d13 = d10 - this.playerEntity.posZ;
/*  302 */           d15 = d11 * d11 + d12 * d12 + d13 * d13;
/*  303 */           boolean flag1 = false;
/*      */           
/*  305 */           if (d15 > 0.0625D && !this.playerEntity.isPlayerSleeping() && !this.playerEntity.theItemInWorldManager.isCreative()) {
/*  306 */             flag1 = true;
/*  307 */             logger.warn(this.playerEntity.getName() + " moved wrongly!");
/*      */           } 
/*      */           
/*  310 */           this.playerEntity.setPositionAndRotation(d8, d9, d10, f1, f2);
/*  311 */           this.playerEntity.addMovementStat(this.playerEntity.posX - d0, this.playerEntity.posY - d1, this.playerEntity.posZ - d2);
/*      */           
/*  313 */           if (!this.playerEntity.noClip) {
/*  314 */             boolean flag2 = worldserver.getCollidingBoundingBoxes((Entity)this.playerEntity, this.playerEntity.getEntityBoundingBox().contract(f3, f3, f3)).isEmpty();
/*      */             
/*  316 */             if (flag && (flag1 || !flag2) && !this.playerEntity.isPlayerSleeping()) {
/*  317 */               setPlayerLocation(this.lastPosX, this.lastPosY, this.lastPosZ, f1, f2);
/*      */               
/*      */               return;
/*      */             } 
/*      */           } 
/*  322 */           AxisAlignedBB axisalignedbb = this.playerEntity.getEntityBoundingBox().expand(f3, f3, f3).addCoord(0.0D, -0.55D, 0.0D);
/*      */           
/*  324 */           if (!this.serverController.isFlightAllowed() && !this.playerEntity.capabilities.allowFlying && !worldserver.checkBlockCollision(axisalignedbb)) {
/*  325 */             if (d12 >= -0.03125D) {
/*  326 */               this.floatingTickCount++;
/*      */               
/*  328 */               if (this.floatingTickCount > 80) {
/*  329 */                 logger.warn(this.playerEntity.getName() + " was kicked for floating too long!");
/*  330 */                 kickPlayerFromServer("Flying is not enabled on this server");
/*      */                 return;
/*      */               } 
/*      */             } 
/*      */           } else {
/*  335 */             this.floatingTickCount = 0;
/*      */           } 
/*      */           
/*  338 */           this.playerEntity.onGround = packetIn.isOnGround();
/*  339 */           this.serverController.getConfigurationManager().serverUpdateMountedMovingPlayer(this.playerEntity);
/*  340 */           this.playerEntity.handleFalling(this.playerEntity.posY - d7, packetIn.isOnGround());
/*  341 */         } else if (this.networkTickCount - this.field_175090_f > 20) {
/*  342 */           setPlayerLocation(this.lastPosX, this.lastPosY, this.lastPosZ, this.playerEntity.rotationYaw, this.playerEntity.rotationPitch);
/*      */         } 
/*      */       } 
/*      */     } 
/*      */   }
/*      */   
/*      */   public void setPlayerLocation(double x, double y, double z, float yaw, float pitch) {
/*  349 */     setPlayerLocation(x, y, z, yaw, pitch, Collections.emptySet());
/*      */   }
/*      */   
/*      */   public void setPlayerLocation(double x, double y, double z, float yaw, float pitch, Set<S08PacketPlayerPosLook.EnumFlags> relativeSet) {
/*  353 */     this.hasMoved = false;
/*  354 */     this.lastPosX = x;
/*  355 */     this.lastPosY = y;
/*  356 */     this.lastPosZ = z;
/*      */     
/*  358 */     if (relativeSet.contains(S08PacketPlayerPosLook.EnumFlags.X)) {
/*  359 */       this.lastPosX += this.playerEntity.posX;
/*      */     }
/*      */     
/*  362 */     if (relativeSet.contains(S08PacketPlayerPosLook.EnumFlags.Y)) {
/*  363 */       this.lastPosY += this.playerEntity.posY;
/*      */     }
/*      */     
/*  366 */     if (relativeSet.contains(S08PacketPlayerPosLook.EnumFlags.Z)) {
/*  367 */       this.lastPosZ += this.playerEntity.posZ;
/*      */     }
/*      */     
/*  370 */     float f = yaw;
/*  371 */     float f1 = pitch;
/*      */     
/*  373 */     if (relativeSet.contains(S08PacketPlayerPosLook.EnumFlags.Y_ROT)) {
/*  374 */       f = yaw + this.playerEntity.rotationYaw;
/*      */     }
/*      */     
/*  377 */     if (relativeSet.contains(S08PacketPlayerPosLook.EnumFlags.X_ROT)) {
/*  378 */       f1 = pitch + this.playerEntity.rotationPitch;
/*      */     }
/*      */     
/*  381 */     this.playerEntity.setPositionAndRotation(this.lastPosX, this.lastPosY, this.lastPosZ, f, f1);
/*  382 */     this.playerEntity.playerNetServerHandler.sendPacket((Packet)new S08PacketPlayerPosLook(x, y, z, yaw, pitch, relativeSet));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void processPlayerDigging(C07PacketPlayerDigging packetIn) {
/*      */     double d0, d1, d2, d3;
/*  391 */     PacketThreadUtil.checkThreadAndEnqueue((Packet<NetHandlerPlayServer>)packetIn, this, (IThreadListener)this.playerEntity.getServerForPlayer());
/*  392 */     WorldServer worldserver = this.serverController.worldServerForDimension(this.playerEntity.dimension);
/*  393 */     BlockPos blockpos = packetIn.getPosition();
/*  394 */     this.playerEntity.markPlayerActive();
/*      */     
/*  396 */     switch (packetIn.getStatus()) {
/*      */       case PERFORM_RESPAWN:
/*  398 */         if (!this.playerEntity.isSpectator()) {
/*  399 */           this.playerEntity.dropOneItem(false);
/*      */         }
/*      */         return;
/*      */ 
/*      */       
/*      */       case REQUEST_STATS:
/*  405 */         if (!this.playerEntity.isSpectator()) {
/*  406 */           this.playerEntity.dropOneItem(true);
/*      */         }
/*      */         return;
/*      */ 
/*      */       
/*      */       case OPEN_INVENTORY_ACHIEVEMENT:
/*  412 */         this.playerEntity.stopUsingItem();
/*      */         return;
/*      */       
/*      */       case null:
/*      */       case null:
/*      */       case null:
/*  418 */         d0 = this.playerEntity.posX - blockpos.getX() + 0.5D;
/*  419 */         d1 = this.playerEntity.posY - blockpos.getY() + 0.5D + 1.5D;
/*  420 */         d2 = this.playerEntity.posZ - blockpos.getZ() + 0.5D;
/*  421 */         d3 = d0 * d0 + d1 * d1 + d2 * d2;
/*      */         
/*  423 */         if (d3 > 36.0D)
/*      */           return; 
/*  425 */         if (blockpos.getY() >= this.serverController.getBuildLimit()) {
/*      */           return;
/*      */         }
/*  428 */         if (packetIn.getStatus() == C07PacketPlayerDigging.Action.START_DESTROY_BLOCK) {
/*  429 */           if (!this.serverController.isBlockProtected((World)worldserver, blockpos, (EntityPlayer)this.playerEntity) && worldserver.getWorldBorder().contains(blockpos)) {
/*  430 */             this.playerEntity.theItemInWorldManager.onBlockClicked(blockpos, packetIn.getFacing());
/*      */           } else {
/*  432 */             this.playerEntity.playerNetServerHandler.sendPacket((Packet)new S23PacketBlockChange((World)worldserver, blockpos));
/*      */           } 
/*      */         } else {
/*  435 */           if (packetIn.getStatus() == C07PacketPlayerDigging.Action.STOP_DESTROY_BLOCK) {
/*  436 */             this.playerEntity.theItemInWorldManager.blockRemoving(blockpos);
/*  437 */           } else if (packetIn.getStatus() == C07PacketPlayerDigging.Action.ABORT_DESTROY_BLOCK) {
/*  438 */             this.playerEntity.theItemInWorldManager.cancelDestroyingBlock();
/*      */           } 
/*      */           
/*  441 */           if (worldserver.getBlockState(blockpos).getBlock().getMaterial() != Material.air) {
/*  442 */             this.playerEntity.playerNetServerHandler.sendPacket((Packet)new S23PacketBlockChange((World)worldserver, blockpos));
/*      */           }
/*      */         } 
/*      */         return;
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/*  450 */     throw new IllegalArgumentException("Invalid player action");
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void processPlayerBlockPlacement(C08PacketPlayerBlockPlacement packetIn) {
/*  458 */     PacketThreadUtil.checkThreadAndEnqueue((Packet<NetHandlerPlayServer>)packetIn, this, (IThreadListener)this.playerEntity.getServerForPlayer());
/*  459 */     WorldServer worldserver = this.serverController.worldServerForDimension(this.playerEntity.dimension);
/*  460 */     ItemStack itemstack = this.playerEntity.inventory.getCurrentItem();
/*  461 */     boolean flag = false;
/*  462 */     BlockPos blockpos = packetIn.getPosition();
/*  463 */     EnumFacing enumfacing = EnumFacing.getFront(packetIn.getPlacedBlockDirection());
/*  464 */     this.playerEntity.markPlayerActive();
/*      */     
/*  466 */     if (packetIn.getPlacedBlockDirection() == 255) {
/*  467 */       if (itemstack == null) {
/*      */         return;
/*      */       }
/*      */       
/*  471 */       this.playerEntity.theItemInWorldManager.tryUseItem((EntityPlayer)this.playerEntity, (World)worldserver, itemstack);
/*  472 */     } else if (blockpos.getY() < this.serverController.getBuildLimit() - 1 || (enumfacing != EnumFacing.UP && blockpos.getY() < this.serverController.getBuildLimit())) {
/*  473 */       if (this.hasMoved && this.playerEntity.getDistanceSq(blockpos.getX() + 0.5D, blockpos.getY() + 0.5D, blockpos.getZ() + 0.5D) < 64.0D && !this.serverController.isBlockProtected((World)worldserver, blockpos, (EntityPlayer)this.playerEntity) && worldserver.getWorldBorder().contains(blockpos)) {
/*  474 */         this.playerEntity.theItemInWorldManager.activateBlockOrUseItem((EntityPlayer)this.playerEntity, (World)worldserver, itemstack, blockpos, enumfacing, packetIn.getPlacedBlockOffsetX(), packetIn.getPlacedBlockOffsetY(), packetIn.getPlacedBlockOffsetZ());
/*      */       }
/*      */       
/*  477 */       flag = true;
/*      */     } else {
/*  479 */       ChatComponentTranslation chatcomponenttranslation = new ChatComponentTranslation("build.tooHigh", new Object[] { Integer.valueOf(this.serverController.getBuildLimit()) });
/*  480 */       chatcomponenttranslation.getChatStyle().setColor(EnumChatFormatting.RED);
/*  481 */       this.playerEntity.playerNetServerHandler.sendPacket((Packet)new S02PacketChat((IChatComponent)chatcomponenttranslation));
/*  482 */       flag = true;
/*      */     } 
/*      */     
/*  485 */     if (flag) {
/*  486 */       this.playerEntity.playerNetServerHandler.sendPacket((Packet)new S23PacketBlockChange((World)worldserver, blockpos));
/*  487 */       this.playerEntity.playerNetServerHandler.sendPacket((Packet)new S23PacketBlockChange((World)worldserver, blockpos.offset(enumfacing)));
/*      */     } 
/*      */     
/*  490 */     itemstack = this.playerEntity.inventory.getCurrentItem();
/*      */     
/*  492 */     if (itemstack != null && itemstack.stackSize == 0) {
/*  493 */       this.playerEntity.inventory.mainInventory[this.playerEntity.inventory.currentItem] = null;
/*  494 */       itemstack = null;
/*      */     } 
/*      */     
/*  497 */     if (itemstack == null || itemstack.getMaxItemUseDuration() == 0) {
/*  498 */       this.playerEntity.isChangingQuantityOnly = true;
/*  499 */       this.playerEntity.inventory.mainInventory[this.playerEntity.inventory.currentItem] = ItemStack.copyItemStack(this.playerEntity.inventory.mainInventory[this.playerEntity.inventory.currentItem]);
/*  500 */       Slot slot = this.playerEntity.openContainer.getSlotFromInventory((IInventory)this.playerEntity.inventory, this.playerEntity.inventory.currentItem);
/*  501 */       this.playerEntity.openContainer.detectAndSendChanges();
/*  502 */       this.playerEntity.isChangingQuantityOnly = false;
/*      */       
/*  504 */       if (!ItemStack.areItemStacksEqual(this.playerEntity.inventory.getCurrentItem(), packetIn.getStack())) {
/*  505 */         sendPacket((Packet)new S2FPacketSetSlot(this.playerEntity.openContainer.windowId, slot.slotNumber, this.playerEntity.inventory.getCurrentItem()));
/*      */       }
/*      */     } 
/*      */   }
/*      */   
/*      */   public void handleSpectate(C18PacketSpectate packetIn) {
/*  511 */     PacketThreadUtil.checkThreadAndEnqueue((Packet<NetHandlerPlayServer>)packetIn, this, (IThreadListener)this.playerEntity.getServerForPlayer());
/*      */     
/*  513 */     if (this.playerEntity.isSpectator()) {
/*  514 */       Entity entity = null;
/*      */       
/*  516 */       for (WorldServer worldserver : this.serverController.worldServers) {
/*  517 */         if (worldserver != null) {
/*  518 */           entity = packetIn.getEntity(worldserver);
/*      */           
/*  520 */           if (entity != null) {
/*      */             break;
/*      */           }
/*      */         } 
/*      */       } 
/*      */       
/*  526 */       if (entity != null) {
/*  527 */         this.playerEntity.setSpectatingEntity((Entity)this.playerEntity);
/*  528 */         this.playerEntity.mountEntity((Entity)null);
/*      */         
/*  530 */         if (entity.worldObj != this.playerEntity.worldObj) {
/*  531 */           WorldServer worldserver1 = this.playerEntity.getServerForPlayer();
/*  532 */           WorldServer worldserver2 = (WorldServer)entity.worldObj;
/*  533 */           this.playerEntity.dimension = entity.dimension;
/*  534 */           sendPacket((Packet)new S07PacketRespawn(this.playerEntity.dimension, worldserver1.getDifficulty(), worldserver1.getWorldInfo().getTerrainType(), this.playerEntity.theItemInWorldManager.getGameType()));
/*  535 */           worldserver1.removePlayerEntityDangerously((Entity)this.playerEntity);
/*  536 */           this.playerEntity.isDead = false;
/*  537 */           this.playerEntity.setLocationAndAngles(entity.posX, entity.posY, entity.posZ, entity.rotationYaw, entity.rotationPitch);
/*      */           
/*  539 */           if (this.playerEntity.isEntityAlive()) {
/*  540 */             worldserver1.updateEntityWithOptionalForce((Entity)this.playerEntity, false);
/*  541 */             worldserver2.spawnEntityInWorld((Entity)this.playerEntity);
/*  542 */             worldserver2.updateEntityWithOptionalForce((Entity)this.playerEntity, false);
/*      */           } 
/*      */           
/*  545 */           this.playerEntity.setWorld((World)worldserver2);
/*  546 */           this.serverController.getConfigurationManager().preparePlayer(this.playerEntity, worldserver1);
/*  547 */           this.playerEntity.setPositionAndUpdate(entity.posX, entity.posY, entity.posZ);
/*  548 */           this.playerEntity.theItemInWorldManager.setWorld(worldserver2);
/*  549 */           this.serverController.getConfigurationManager().updateTimeAndWeatherForPlayer(this.playerEntity, worldserver2);
/*  550 */           this.serverController.getConfigurationManager().syncPlayerInventory(this.playerEntity);
/*      */         } else {
/*  552 */           this.playerEntity.setPositionAndUpdate(entity.posX, entity.posY, entity.posZ);
/*      */         } 
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public void handleResourcePackStatus(C19PacketResourcePackStatus packetIn) {}
/*      */ 
/*      */ 
/*      */   
/*      */   public void onDisconnect(IChatComponent reason) {
/*  565 */     logger.info(this.playerEntity.getName() + " lost connection: " + reason);
/*  566 */     this.serverController.refreshStatusNextTick();
/*  567 */     ChatComponentTranslation chatcomponenttranslation = new ChatComponentTranslation("multiplayer.player.left", new Object[] { this.playerEntity.getDisplayName() });
/*  568 */     chatcomponenttranslation.getChatStyle().setColor(EnumChatFormatting.YELLOW);
/*  569 */     this.serverController.getConfigurationManager().sendChatMsg((IChatComponent)chatcomponenttranslation);
/*  570 */     this.playerEntity.mountEntityAndWakeUp();
/*  571 */     this.serverController.getConfigurationManager().playerLoggedOut(this.playerEntity);
/*      */     
/*  573 */     if (this.serverController.isSinglePlayer() && this.playerEntity.getName().equals(this.serverController.getServerOwner())) {
/*  574 */       logger.info("Stopping singleplayer server as player logged out");
/*  575 */       this.serverController.initiateShutdown();
/*      */     } 
/*      */   }
/*      */   
/*      */   public void sendPacket(final Packet packetIn) {
/*  580 */     if (packetIn instanceof S02PacketChat) {
/*  581 */       S02PacketChat s02packetchat = (S02PacketChat)packetIn;
/*  582 */       EntityPlayer.EnumChatVisibility entityplayer$enumchatvisibility = this.playerEntity.getChatVisibility();
/*      */       
/*  584 */       if (entityplayer$enumchatvisibility == EntityPlayer.EnumChatVisibility.HIDDEN) {
/*      */         return;
/*      */       }
/*      */       
/*  588 */       if (entityplayer$enumchatvisibility == EntityPlayer.EnumChatVisibility.SYSTEM && !s02packetchat.isChat()) {
/*      */         return;
/*      */       }
/*      */     } 
/*      */     
/*      */     try {
/*  594 */       this.netManager.sendPacket(packetIn);
/*  595 */     } catch (Throwable throwable) {
/*  596 */       CrashReport crashreport = CrashReport.makeCrashReport(throwable, "Sending packet");
/*  597 */       CrashReportCategory crashreportcategory = crashreport.makeCategory("Packet being sent");
/*  598 */       crashreportcategory.addCrashSectionCallable("Packet class", new Callable<String>() {
/*      */             public String call() {
/*  600 */               return packetIn.getClass().getCanonicalName();
/*      */             }
/*      */           });
/*  603 */       throw new ReportedException(crashreport);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void processHeldItemChange(C09PacketHeldItemChange packetIn) {
/*  611 */     PacketThreadUtil.checkThreadAndEnqueue((Packet<NetHandlerPlayServer>)packetIn, this, (IThreadListener)this.playerEntity.getServerForPlayer());
/*      */     
/*  613 */     if (packetIn.getSlotId() >= 0 && packetIn.getSlotId() < InventoryPlayer.getHotbarSize()) {
/*  614 */       this.playerEntity.inventory.currentItem = packetIn.getSlotId();
/*  615 */       this.playerEntity.markPlayerActive();
/*      */     } else {
/*  617 */       logger.warn(this.playerEntity.getName() + " tried to set an invalid carried item");
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void processChatMessage(C01PacketChatMessage packetIn) {
/*  625 */     PacketThreadUtil.checkThreadAndEnqueue((Packet<NetHandlerPlayServer>)packetIn, this, (IThreadListener)this.playerEntity.getServerForPlayer());
/*      */     
/*  627 */     if (this.playerEntity.getChatVisibility() == EntityPlayer.EnumChatVisibility.HIDDEN) {
/*  628 */       ChatComponentTranslation chatcomponenttranslation = new ChatComponentTranslation("chat.cannotSend", new Object[0]);
/*  629 */       chatcomponenttranslation.getChatStyle().setColor(EnumChatFormatting.RED);
/*  630 */       sendPacket((Packet)new S02PacketChat((IChatComponent)chatcomponenttranslation));
/*      */     } else {
/*  632 */       this.playerEntity.markPlayerActive();
/*  633 */       String s = packetIn.getMessage();
/*  634 */       s = StringUtils.normalizeSpace(s);
/*      */       
/*  636 */       for (int i = 0; i < s.length(); i++) {
/*  637 */         if (!ChatAllowedCharacters.isAllowedCharacter(s.charAt(i))) {
/*  638 */           kickPlayerFromServer("Illegal characters in chat");
/*      */           
/*      */           return;
/*      */         } 
/*      */       } 
/*  643 */       if (!s.isEmpty() && s.charAt(0) == '/') {
/*  644 */         handleSlashCommand(s);
/*      */       } else {
/*  646 */         ChatComponentTranslation chatComponentTranslation = new ChatComponentTranslation("chat.type.text", new Object[] { this.playerEntity.getDisplayName(), s });
/*  647 */         this.serverController.getConfigurationManager().sendChatMsgImpl((IChatComponent)chatComponentTranslation, false);
/*      */       } 
/*      */       
/*  650 */       this.chatSpamThresholdCount += 20;
/*      */       
/*  652 */       if (this.chatSpamThresholdCount > 200 && !this.serverController.getConfigurationManager().canSendCommands(this.playerEntity.getGameProfile())) {
/*  653 */         kickPlayerFromServer("disconnect.spam");
/*      */       }
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void handleSlashCommand(String command) {
/*  662 */     this.serverController.getCommandManager().executeCommand((ICommandSender)this.playerEntity, command);
/*      */   }
/*      */   
/*      */   public void handleAnimation(C0APacketAnimation packetIn) {
/*  666 */     PacketThreadUtil.checkThreadAndEnqueue((Packet<NetHandlerPlayServer>)packetIn, this, (IThreadListener)this.playerEntity.getServerForPlayer());
/*  667 */     this.playerEntity.markPlayerActive();
/*  668 */     this.playerEntity.swingItem();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void processEntityAction(C0BPacketEntityAction packetIn) {
/*  676 */     PacketThreadUtil.checkThreadAndEnqueue((Packet<NetHandlerPlayServer>)packetIn, this, (IThreadListener)this.playerEntity.getServerForPlayer());
/*  677 */     this.playerEntity.markPlayerActive();
/*      */     
/*  679 */     switch (packetIn.getAction()) {
/*      */       case PERFORM_RESPAWN:
/*  681 */         this.playerEntity.setSneaking(true);
/*      */         return;
/*      */       
/*      */       case REQUEST_STATS:
/*  685 */         this.playerEntity.setSneaking(false);
/*      */         return;
/*      */       
/*      */       case OPEN_INVENTORY_ACHIEVEMENT:
/*  689 */         this.playerEntity.setSprinting(true);
/*      */         return;
/*      */       
/*      */       case null:
/*  693 */         this.playerEntity.setSprinting(false);
/*      */         return;
/*      */       
/*      */       case null:
/*  697 */         this.playerEntity.wakeUpPlayer(false, true, true);
/*  698 */         this.hasMoved = false;
/*      */         return;
/*      */       
/*      */       case null:
/*  702 */         if (this.playerEntity.ridingEntity instanceof EntityHorse) {
/*  703 */           ((EntityHorse)this.playerEntity.ridingEntity).setJumpPower(packetIn.getAuxData());
/*      */         }
/*      */         return;
/*      */ 
/*      */       
/*      */       case null:
/*  709 */         if (this.playerEntity.ridingEntity instanceof EntityHorse) {
/*  710 */           ((EntityHorse)this.playerEntity.ridingEntity).openGUI((EntityPlayer)this.playerEntity);
/*      */         }
/*      */         return;
/*      */     } 
/*      */ 
/*      */     
/*  716 */     throw new IllegalArgumentException("Invalid client command!");
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void processUseEntity(C02PacketUseEntity packetIn) {
/*  725 */     PacketThreadUtil.checkThreadAndEnqueue((Packet<NetHandlerPlayServer>)packetIn, this, (IThreadListener)this.playerEntity.getServerForPlayer());
/*  726 */     WorldServer worldserver = this.serverController.worldServerForDimension(this.playerEntity.dimension);
/*  727 */     Entity entity = packetIn.getEntityFromWorld((World)worldserver);
/*  728 */     this.playerEntity.markPlayerActive();
/*      */     
/*  730 */     if (entity != null) {
/*  731 */       boolean flag = this.playerEntity.canEntityBeSeen(entity);
/*  732 */       double d0 = 36.0D;
/*      */       
/*  734 */       if (!flag) {
/*  735 */         d0 = 9.0D;
/*      */       }
/*      */       
/*  738 */       if (this.playerEntity.getDistanceSqToEntity(entity) < d0) {
/*  739 */         if (packetIn.getAction() == C02PacketUseEntity.Action.INTERACT) {
/*  740 */           this.playerEntity.interactWith(entity);
/*  741 */         } else if (packetIn.getAction() == C02PacketUseEntity.Action.INTERACT_AT) {
/*  742 */           entity.interactAt((EntityPlayer)this.playerEntity, packetIn.getHitVec());
/*  743 */         } else if (packetIn.getAction() == C02PacketUseEntity.Action.ATTACK) {
/*  744 */           if (entity instanceof EntityItem || entity instanceof net.minecraft.entity.item.EntityXPOrb || entity instanceof net.minecraft.entity.projectile.EntityArrow || entity == this.playerEntity) {
/*  745 */             kickPlayerFromServer("Attempting to attack an invalid entity");
/*  746 */             this.serverController.logWarning("Player " + this.playerEntity.getName() + " tried to attack an invalid entity");
/*      */             
/*      */             return;
/*      */           } 
/*  750 */           this.playerEntity.attackTargetEntityWithCurrentItem(entity);
/*      */         } 
/*      */       }
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void processClientStatus(C16PacketClientStatus packetIn) {
/*  761 */     PacketThreadUtil.checkThreadAndEnqueue((Packet<NetHandlerPlayServer>)packetIn, this, (IThreadListener)this.playerEntity.getServerForPlayer());
/*  762 */     this.playerEntity.markPlayerActive();
/*  763 */     C16PacketClientStatus.EnumState c16packetclientstatus$enumstate = packetIn.getStatus();
/*      */     
/*  765 */     switch (c16packetclientstatus$enumstate) {
/*      */       case PERFORM_RESPAWN:
/*  767 */         if (this.playerEntity.playerConqueredTheEnd) {
/*  768 */           this.playerEntity = this.serverController.getConfigurationManager().recreatePlayerEntity(this.playerEntity, 0, true); break;
/*  769 */         }  if (this.playerEntity.getServerForPlayer().getWorldInfo().isHardcoreModeEnabled()) {
/*  770 */           if (this.serverController.isSinglePlayer() && this.playerEntity.getName().equals(this.serverController.getServerOwner())) {
/*  771 */             this.playerEntity.playerNetServerHandler.kickPlayerFromServer("You have died. Game over, man, it's game over!");
/*  772 */             this.serverController.deleteWorldAndStopServer(); break;
/*      */           } 
/*  774 */           UserListBansEntry userlistbansentry = new UserListBansEntry(this.playerEntity.getGameProfile(), (Date)null, "(You just lost the game)", (Date)null, "Death in Hardcore");
/*  775 */           this.serverController.getConfigurationManager().getBannedPlayers().addEntry((UserListEntry)userlistbansentry);
/*  776 */           this.playerEntity.playerNetServerHandler.kickPlayerFromServer("You have died. Game over, man, it's game over!");
/*      */           break;
/*      */         } 
/*  779 */         if (this.playerEntity.getHealth() > 0.0F) {
/*      */           return;
/*      */         }
/*      */         
/*  783 */         this.playerEntity = this.serverController.getConfigurationManager().recreatePlayerEntity(this.playerEntity, 0, false);
/*      */         break;
/*      */ 
/*      */ 
/*      */       
/*      */       case REQUEST_STATS:
/*  789 */         this.playerEntity.getStatFile().func_150876_a(this.playerEntity);
/*      */         break;
/*      */       
/*      */       case OPEN_INVENTORY_ACHIEVEMENT:
/*  793 */         this.playerEntity.triggerAchievement((StatBase)AchievementList.openInventory);
/*      */         break;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public void processCloseWindow(C0DPacketCloseWindow packetIn) {
/*  801 */     PacketThreadUtil.checkThreadAndEnqueue((Packet<NetHandlerPlayServer>)packetIn, this, (IThreadListener)this.playerEntity.getServerForPlayer());
/*  802 */     this.playerEntity.closeContainer();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void processClickWindow(C0EPacketClickWindow packetIn) {
/*  811 */     PacketThreadUtil.checkThreadAndEnqueue((Packet<NetHandlerPlayServer>)packetIn, this, (IThreadListener)this.playerEntity.getServerForPlayer());
/*  812 */     this.playerEntity.markPlayerActive();
/*      */     
/*  814 */     if (this.playerEntity.openContainer.windowId == packetIn.getWindowId() && this.playerEntity.openContainer.getCanCraft((EntityPlayer)this.playerEntity)) {
/*  815 */       if (this.playerEntity.isSpectator()) {
/*  816 */         List<ItemStack> list = Lists.newArrayList();
/*      */         
/*  818 */         for (int i = 0; i < this.playerEntity.openContainer.inventorySlots.size(); i++) {
/*  819 */           list.add(((Slot)this.playerEntity.openContainer.inventorySlots.get(i)).getStack());
/*      */         }
/*      */         
/*  822 */         this.playerEntity.updateCraftingInventory(this.playerEntity.openContainer, list);
/*      */       } else {
/*  824 */         ItemStack itemstack = this.playerEntity.openContainer.slotClick(packetIn.getSlotId(), packetIn.getUsedButton(), packetIn.getMode(), (EntityPlayer)this.playerEntity);
/*      */         
/*  826 */         if (ItemStack.areItemStacksEqual(packetIn.getClickedItem(), itemstack)) {
/*  827 */           this.playerEntity.playerNetServerHandler.sendPacket((Packet)new S32PacketConfirmTransaction(packetIn.getWindowId(), packetIn.getActionNumber(), true));
/*  828 */           this.playerEntity.isChangingQuantityOnly = true;
/*  829 */           this.playerEntity.openContainer.detectAndSendChanges();
/*  830 */           this.playerEntity.updateHeldItem();
/*  831 */           this.playerEntity.isChangingQuantityOnly = false;
/*      */         } else {
/*  833 */           this.field_147372_n.addKey(this.playerEntity.openContainer.windowId, Short.valueOf(packetIn.getActionNumber()));
/*  834 */           this.playerEntity.playerNetServerHandler.sendPacket((Packet)new S32PacketConfirmTransaction(packetIn.getWindowId(), packetIn.getActionNumber(), false));
/*  835 */           this.playerEntity.openContainer.setCanCraft((EntityPlayer)this.playerEntity, false);
/*  836 */           List<ItemStack> list1 = Lists.newArrayList();
/*      */           
/*  838 */           for (int j = 0; j < this.playerEntity.openContainer.inventorySlots.size(); j++) {
/*  839 */             list1.add(((Slot)this.playerEntity.openContainer.inventorySlots.get(j)).getStack());
/*      */           }
/*      */           
/*  842 */           this.playerEntity.updateCraftingInventory(this.playerEntity.openContainer, list1);
/*      */         } 
/*      */       } 
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void processEnchantItem(C11PacketEnchantItem packetIn) {
/*  853 */     PacketThreadUtil.checkThreadAndEnqueue((Packet<NetHandlerPlayServer>)packetIn, this, (IThreadListener)this.playerEntity.getServerForPlayer());
/*  854 */     this.playerEntity.markPlayerActive();
/*      */     
/*  856 */     if (this.playerEntity.openContainer.windowId == packetIn.getWindowId() && this.playerEntity.openContainer.getCanCraft((EntityPlayer)this.playerEntity) && !this.playerEntity.isSpectator()) {
/*  857 */       this.playerEntity.openContainer.enchantItem((EntityPlayer)this.playerEntity, packetIn.getButton());
/*  858 */       this.playerEntity.openContainer.detectAndSendChanges();
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void processCreativeInventoryAction(C10PacketCreativeInventoryAction packetIn) {
/*  866 */     PacketThreadUtil.checkThreadAndEnqueue((Packet<NetHandlerPlayServer>)packetIn, this, (IThreadListener)this.playerEntity.getServerForPlayer());
/*      */     
/*  868 */     if (this.playerEntity.theItemInWorldManager.isCreative()) {
/*  869 */       boolean flag = (packetIn.getSlotId() < 0);
/*  870 */       ItemStack itemstack = packetIn.getStack();
/*      */       
/*  872 */       if (itemstack != null && itemstack.hasTagCompound() && itemstack.getTagCompound().hasKey("BlockEntityTag", 10)) {
/*  873 */         NBTTagCompound nbttagcompound = itemstack.getTagCompound().getCompoundTag("BlockEntityTag");
/*      */         
/*  875 */         if (nbttagcompound.hasKey("x") && nbttagcompound.hasKey("y") && nbttagcompound.hasKey("z")) {
/*  876 */           BlockPos blockpos = new BlockPos(nbttagcompound.getInteger("x"), nbttagcompound.getInteger("y"), nbttagcompound.getInteger("z"));
/*  877 */           TileEntity tileentity = this.playerEntity.worldObj.getTileEntity(blockpos);
/*      */           
/*  879 */           if (tileentity != null) {
/*  880 */             NBTTagCompound nbttagcompound1 = new NBTTagCompound();
/*  881 */             tileentity.writeToNBT(nbttagcompound1);
/*  882 */             nbttagcompound1.removeTag("x");
/*  883 */             nbttagcompound1.removeTag("y");
/*  884 */             nbttagcompound1.removeTag("z");
/*  885 */             itemstack.setTagInfo("BlockEntityTag", (NBTBase)nbttagcompound1);
/*      */           } 
/*      */         } 
/*      */       } 
/*      */       
/*  890 */       boolean flag1 = (packetIn.getSlotId() >= 1 && packetIn.getSlotId() < 36 + InventoryPlayer.getHotbarSize());
/*  891 */       boolean flag2 = (itemstack == null || itemstack.getItem() != null);
/*  892 */       boolean flag3 = (itemstack == null || (itemstack.getMetadata() >= 0 && itemstack.stackSize <= 64 && itemstack.stackSize > 0));
/*      */       
/*  894 */       if (flag1 && flag2 && flag3) {
/*  895 */         if (itemstack == null) {
/*  896 */           this.playerEntity.inventoryContainer.putStackInSlot(packetIn.getSlotId(), (ItemStack)null);
/*      */         } else {
/*  898 */           this.playerEntity.inventoryContainer.putStackInSlot(packetIn.getSlotId(), itemstack);
/*      */         } 
/*      */         
/*  901 */         this.playerEntity.inventoryContainer.setCanCraft((EntityPlayer)this.playerEntity, true);
/*  902 */       } else if (flag && flag2 && flag3 && this.itemDropThreshold < 200) {
/*  903 */         this.itemDropThreshold += 20;
/*  904 */         EntityItem entityitem = this.playerEntity.dropPlayerItemWithRandomChoice(itemstack, true);
/*      */         
/*  906 */         if (entityitem != null) {
/*  907 */           entityitem.setAgeToCreativeDespawnTime();
/*      */         }
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void processConfirmTransaction(C0FPacketConfirmTransaction packetIn) {
/*  919 */     PacketThreadUtil.checkThreadAndEnqueue((Packet<NetHandlerPlayServer>)packetIn, this, (IThreadListener)this.playerEntity.getServerForPlayer());
/*  920 */     Short oshort = (Short)this.field_147372_n.lookup(this.playerEntity.openContainer.windowId);
/*      */     
/*  922 */     if (oshort != null && packetIn.getUid() == oshort.shortValue() && this.playerEntity.openContainer.windowId == packetIn.getWindowId() && !this.playerEntity.openContainer.getCanCraft((EntityPlayer)this.playerEntity) && !this.playerEntity.isSpectator()) {
/*  923 */       this.playerEntity.openContainer.setCanCraft((EntityPlayer)this.playerEntity, true);
/*      */     }
/*      */   }
/*      */   
/*      */   public void processUpdateSign(C12PacketUpdateSign packetIn) {
/*  928 */     PacketThreadUtil.checkThreadAndEnqueue((Packet<NetHandlerPlayServer>)packetIn, this, (IThreadListener)this.playerEntity.getServerForPlayer());
/*  929 */     this.playerEntity.markPlayerActive();
/*  930 */     WorldServer worldserver = this.serverController.worldServerForDimension(this.playerEntity.dimension);
/*  931 */     BlockPos blockpos = packetIn.getPosition();
/*      */     
/*  933 */     if (worldserver.isBlockLoaded(blockpos)) {
/*  934 */       TileEntity tileentity = worldserver.getTileEntity(blockpos);
/*      */       
/*  936 */       if (!(tileentity instanceof TileEntitySign)) {
/*      */         return;
/*      */       }
/*      */       
/*  940 */       TileEntitySign tileentitysign = (TileEntitySign)tileentity;
/*      */       
/*  942 */       if (!tileentitysign.getIsEditable() || tileentitysign.getPlayer() != this.playerEntity) {
/*  943 */         this.serverController.logWarning("Player " + this.playerEntity.getName() + " just tried to change non-editable sign");
/*      */         
/*      */         return;
/*      */       } 
/*  947 */       IChatComponent[] aichatcomponent = packetIn.getLines();
/*      */       
/*  949 */       for (int i = 0; i < aichatcomponent.length; i++) {
/*  950 */         tileentitysign.signText[i] = (IChatComponent)new ChatComponentText(EnumChatFormatting.getTextWithoutFormattingCodes(aichatcomponent[i].getUnformattedText()));
/*      */       }
/*      */       
/*  953 */       tileentitysign.markDirty();
/*  954 */       worldserver.markBlockForUpdate(blockpos);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void processKeepAlive(C00PacketKeepAlive packetIn) {
/*  962 */     if (packetIn.getKey() == this.field_147378_h) {
/*  963 */       int i = (int)(currentTimeMillis() - this.lastPingTime);
/*  964 */       this.playerEntity.ping = (this.playerEntity.ping * 3 + i) / 4;
/*      */     } 
/*      */   }
/*      */   
/*      */   private long currentTimeMillis() {
/*  969 */     return System.nanoTime() / 1000000L;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void processPlayerAbilities(C13PacketPlayerAbilities packetIn) {
/*  976 */     PacketThreadUtil.checkThreadAndEnqueue((Packet<NetHandlerPlayServer>)packetIn, this, (IThreadListener)this.playerEntity.getServerForPlayer());
/*  977 */     this.playerEntity.capabilities.isFlying = (packetIn.isFlying() && this.playerEntity.capabilities.allowFlying);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void processTabComplete(C14PacketTabComplete packetIn) {
/*  984 */     PacketThreadUtil.checkThreadAndEnqueue((Packet<NetHandlerPlayServer>)packetIn, this, (IThreadListener)this.playerEntity.getServerForPlayer());
/*  985 */     List<String> list = Lists.newArrayList();
/*      */     
/*  987 */     list.addAll(this.serverController.getTabCompletions((ICommandSender)this.playerEntity, packetIn.getMessage(), packetIn.getTargetBlock()));
/*      */     
/*  989 */     this.playerEntity.playerNetServerHandler.sendPacket((Packet)new S3APacketTabComplete(list.<String>toArray(new String[0])));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void processClientSettings(C15PacketClientSettings packetIn) {
/*  997 */     PacketThreadUtil.checkThreadAndEnqueue((Packet<NetHandlerPlayServer>)packetIn, this, (IThreadListener)this.playerEntity.getServerForPlayer());
/*  998 */     this.playerEntity.handleClientSettings(packetIn);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void processVanilla250Packet(C17PacketCustomPayload packetIn) {
/* 1005 */     PacketThreadUtil.checkThreadAndEnqueue((Packet<NetHandlerPlayServer>)packetIn, this, (IThreadListener)this.playerEntity.getServerForPlayer());
/*      */     
/* 1007 */     if ("MC|BEdit".equals(packetIn.getChannelName())) {
/* 1008 */       PacketBuffer packetbuffer3 = new PacketBuffer(Unpooled.wrappedBuffer(packetIn.getBufferData()));
/*      */       
/*      */       try {
/* 1011 */         ItemStack itemstack1 = packetbuffer3.readItemStackFromBuffer();
/*      */         
/* 1013 */         if (itemstack1 != null) {
/* 1014 */           if (!ItemWritableBook.isNBTValid(itemstack1.getTagCompound())) {
/* 1015 */             throw new IOException("Invalid book tag!");
/*      */           }
/*      */           
/* 1018 */           ItemStack itemstack3 = this.playerEntity.inventory.getCurrentItem();
/*      */           
/* 1020 */           if (itemstack3 == null) {
/*      */             return;
/*      */           }
/*      */           
/* 1024 */           if (itemstack1.getItem() == Items.writable_book && itemstack1.getItem() == itemstack3.getItem()) {
/* 1025 */             itemstack3.setTagInfo("pages", (NBTBase)itemstack1.getTagCompound().getTagList("pages", 8));
/*      */           }
/*      */           
/*      */           return;
/*      */         } 
/* 1030 */       } catch (Exception exception3) {
/* 1031 */         logger.error("Couldn't handle book info", exception3);
/*      */         return;
/*      */       } finally {
/* 1034 */         packetbuffer3.release();
/*      */       } 
/*      */       return;
/*      */     } 
/* 1038 */     if ("MC|BSign".equals(packetIn.getChannelName())) {
/* 1039 */       PacketBuffer packetbuffer2 = new PacketBuffer(Unpooled.wrappedBuffer(packetIn.getBufferData()));
/*      */       
/*      */       try {
/* 1042 */         ItemStack itemstack = packetbuffer2.readItemStackFromBuffer();
/*      */         
/* 1044 */         if (itemstack != null) {
/* 1045 */           if (!ItemEditableBook.validBookTagContents(itemstack.getTagCompound())) {
/* 1046 */             throw new IOException("Invalid book tag!");
/*      */           }
/*      */           
/* 1049 */           ItemStack itemstack2 = this.playerEntity.inventory.getCurrentItem();
/*      */           
/* 1051 */           if (itemstack2 == null) {
/*      */             return;
/*      */           }
/*      */           
/* 1055 */           if (itemstack.getItem() == Items.written_book && itemstack2.getItem() == Items.writable_book) {
/* 1056 */             itemstack2.setTagInfo("author", (NBTBase)new NBTTagString(this.playerEntity.getName()));
/* 1057 */             itemstack2.setTagInfo("title", (NBTBase)new NBTTagString(itemstack.getTagCompound().getString("title")));
/* 1058 */             itemstack2.setTagInfo("pages", (NBTBase)itemstack.getTagCompound().getTagList("pages", 8));
/* 1059 */             itemstack2.setItem(Items.written_book);
/*      */           } 
/*      */           
/*      */           return;
/*      */         } 
/* 1064 */       } catch (Exception exception4) {
/* 1065 */         logger.error("Couldn't sign book", exception4);
/*      */         return;
/*      */       } finally {
/* 1068 */         packetbuffer2.release();
/*      */       } 
/*      */       return;
/*      */     } 
/* 1072 */     if ("MC|TrSel".equals(packetIn.getChannelName())) {
/*      */       try {
/* 1074 */         int i = packetIn.getBufferData().readInt();
/* 1075 */         Container container = this.playerEntity.openContainer;
/*      */         
/* 1077 */         if (container instanceof ContainerMerchant) {
/* 1078 */           ((ContainerMerchant)container).setCurrentRecipeIndex(i);
/*      */         }
/* 1080 */       } catch (Exception exception2) {
/* 1081 */         logger.error("Couldn't select trade", exception2);
/*      */       } 
/* 1083 */     } else if ("MC|AdvCdm".equals(packetIn.getChannelName())) {
/* 1084 */       if (!this.serverController.isCommandBlockEnabled()) {
/* 1085 */         this.playerEntity.addChatMessage((IChatComponent)new ChatComponentTranslation("advMode.notEnabled", new Object[0]));
/* 1086 */       } else if (this.playerEntity.canCommandSenderUseCommand(2, "") && this.playerEntity.capabilities.isCreativeMode) {
/* 1087 */         PacketBuffer packetbuffer = packetIn.getBufferData();
/*      */         
/*      */         try {
/* 1090 */           int j = packetbuffer.readByte();
/* 1091 */           CommandBlockLogic commandblocklogic = null;
/*      */           
/* 1093 */           if (j == 0) {
/* 1094 */             TileEntity tileentity = this.playerEntity.worldObj.getTileEntity(new BlockPos(packetbuffer.readInt(), packetbuffer.readInt(), packetbuffer.readInt()));
/*      */             
/* 1096 */             if (tileentity instanceof TileEntityCommandBlock) {
/* 1097 */               commandblocklogic = ((TileEntityCommandBlock)tileentity).getCommandBlockLogic();
/*      */             }
/* 1099 */           } else if (j == 1) {
/* 1100 */             Entity entity = this.playerEntity.worldObj.getEntityByID(packetbuffer.readInt());
/*      */             
/* 1102 */             if (entity instanceof EntityMinecartCommandBlock) {
/* 1103 */               commandblocklogic = ((EntityMinecartCommandBlock)entity).getCommandBlockLogic();
/*      */             }
/*      */           } 
/*      */           
/* 1107 */           String s1 = packetbuffer.readStringFromBuffer(packetbuffer.readableBytes());
/* 1108 */           boolean flag = packetbuffer.readBoolean();
/*      */           
/* 1110 */           if (commandblocklogic != null) {
/* 1111 */             commandblocklogic.setCommand(s1);
/* 1112 */             commandblocklogic.setTrackOutput(flag);
/*      */             
/* 1114 */             if (!flag) {
/* 1115 */               commandblocklogic.setLastOutput((IChatComponent)null);
/*      */             }
/*      */             
/* 1118 */             commandblocklogic.updateCommand();
/* 1119 */             this.playerEntity.addChatMessage((IChatComponent)new ChatComponentTranslation("advMode.setCommand.success", new Object[] { s1 }));
/*      */           } 
/* 1121 */         } catch (Exception exception1) {
/* 1122 */           logger.error("Couldn't set command block", exception1);
/*      */         } finally {
/* 1124 */           packetbuffer.release();
/*      */         } 
/*      */       } else {
/* 1127 */         this.playerEntity.addChatMessage((IChatComponent)new ChatComponentTranslation("advMode.notAllowed", new Object[0]));
/*      */       } 
/* 1129 */     } else if ("MC|Beacon".equals(packetIn.getChannelName())) {
/* 1130 */       if (this.playerEntity.openContainer instanceof ContainerBeacon) {
/*      */         try {
/* 1132 */           PacketBuffer packetbuffer1 = packetIn.getBufferData();
/* 1133 */           int k = packetbuffer1.readInt();
/* 1134 */           int l = packetbuffer1.readInt();
/* 1135 */           ContainerBeacon containerbeacon = (ContainerBeacon)this.playerEntity.openContainer;
/* 1136 */           Slot slot = containerbeacon.getSlot(0);
/*      */           
/* 1138 */           if (slot.getHasStack()) {
/* 1139 */             slot.decrStackSize(1);
/* 1140 */             IInventory iinventory = containerbeacon.func_180611_e();
/* 1141 */             iinventory.setField(1, k);
/* 1142 */             iinventory.setField(2, l);
/* 1143 */             iinventory.markDirty();
/*      */           } 
/* 1145 */         } catch (Exception exception) {
/* 1146 */           logger.error("Couldn't set beacon", exception);
/*      */         } 
/*      */       }
/* 1149 */     } else if ("MC|ItemName".equals(packetIn.getChannelName()) && this.playerEntity.openContainer instanceof ContainerRepair) {
/* 1150 */       ContainerRepair containerrepair = (ContainerRepair)this.playerEntity.openContainer;
/*      */       
/* 1152 */       if (packetIn.getBufferData() != null && packetIn.getBufferData().readableBytes() >= 1) {
/* 1153 */         String s = ChatAllowedCharacters.filterAllowedCharacters(packetIn.getBufferData().readStringFromBuffer(32767));
/*      */         
/* 1155 */         if (s.length() <= 30) {
/* 1156 */           containerrepair.updateItemName(s);
/*      */         }
/*      */       } else {
/* 1159 */         containerrepair.updateItemName("");
/*      */       } 
/*      */     } 
/*      */   }
/*      */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\minecraft\network\NetHandlerPlayServer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */