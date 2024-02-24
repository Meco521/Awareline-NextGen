/*      */ package net.minecraft.client.network;
/*      */ import com.google.common.util.concurrent.FutureCallback;
/*      */ import com.google.common.util.concurrent.Futures;
/*      */ import java.io.File;
/*      */ import java.util.List;
/*      */ import net.minecraft.client.Minecraft;
/*      */ import net.minecraft.client.entity.EntityOtherPlayerMP;
/*      */ import net.minecraft.client.entity.EntityPlayerSP;
/*      */ import net.minecraft.client.gui.GuiScreen;
/*      */ import net.minecraft.client.multiplayer.ServerData;
/*      */ import net.minecraft.client.settings.GameSettings;
/*      */ import net.minecraft.entity.Entity;
/*      */ import net.minecraft.entity.EntityLivingBase;
/*      */ import net.minecraft.entity.ai.attributes.IAttributeInstance;
/*      */ import net.minecraft.entity.effect.EntityLightningBolt;
/*      */ import net.minecraft.entity.item.EntityFallingBlock;
/*      */ import net.minecraft.entity.item.EntityXPOrb;
/*      */ import net.minecraft.entity.player.EntityPlayer;
/*      */ import net.minecraft.item.ItemStack;
/*      */ import net.minecraft.network.INetHandler;
/*      */ import net.minecraft.network.Packet;
/*      */ import net.minecraft.network.PacketThreadUtil;
/*      */ import net.minecraft.network.play.client.C19PacketResourcePackStatus;
/*      */ import net.minecraft.network.play.server.S01PacketJoinGame;
/*      */ import net.minecraft.network.play.server.S07PacketRespawn;
/*      */ import net.minecraft.network.play.server.S08PacketPlayerPosLook;
/*      */ import net.minecraft.network.play.server.S0BPacketAnimation;
/*      */ import net.minecraft.network.play.server.S0CPacketSpawnPlayer;
/*      */ import net.minecraft.network.play.server.S0EPacketSpawnObject;
/*      */ import net.minecraft.network.play.server.S0FPacketSpawnMob;
/*      */ import net.minecraft.network.play.server.S10PacketSpawnPainting;
/*      */ import net.minecraft.network.play.server.S11PacketSpawnExperienceOrb;
/*      */ import net.minecraft.network.play.server.S12PacketEntityVelocity;
/*      */ import net.minecraft.network.play.server.S14PacketEntity;
/*      */ import net.minecraft.network.play.server.S18PacketEntityTeleport;
/*      */ import net.minecraft.network.play.server.S1BPacketEntityAttach;
/*      */ import net.minecraft.network.play.server.S1DPacketEntityEffect;
/*      */ import net.minecraft.network.play.server.S20PacketEntityProperties;
/*      */ import net.minecraft.network.play.server.S21PacketChunkData;
/*      */ import net.minecraft.network.play.server.S24PacketBlockAction;
/*      */ import net.minecraft.network.play.server.S26PacketMapChunkBulk;
/*      */ import net.minecraft.network.play.server.S27PacketExplosion;
/*      */ import net.minecraft.network.play.server.S28PacketEffect;
/*      */ import net.minecraft.network.play.server.S29PacketSoundEffect;
/*      */ import net.minecraft.network.play.server.S2APacketParticles;
/*      */ import net.minecraft.network.play.server.S2BPacketChangeGameState;
/*      */ import net.minecraft.network.play.server.S2CPacketSpawnGlobalEntity;
/*      */ import net.minecraft.network.play.server.S2DPacketOpenWindow;
/*      */ import net.minecraft.network.play.server.S2FPacketSetSlot;
/*      */ import net.minecraft.network.play.server.S30PacketWindowItems;
/*      */ import net.minecraft.network.play.server.S32PacketConfirmTransaction;
/*      */ import net.minecraft.network.play.server.S33PacketUpdateSign;
/*      */ import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
/*      */ import net.minecraft.network.play.server.S38PacketPlayerListItem;
/*      */ import net.minecraft.network.play.server.S39PacketPlayerAbilities;
/*      */ import net.minecraft.network.play.server.S3BPacketScoreboardObjective;
/*      */ import net.minecraft.network.play.server.S3CPacketUpdateScore;
/*      */ import net.minecraft.network.play.server.S3EPacketTeams;
/*      */ import net.minecraft.network.play.server.S3FPacketCustomPayload;
/*      */ import net.minecraft.network.play.server.S45PacketTitle;
/*      */ import net.minecraft.scoreboard.ScoreObjective;
/*      */ import net.minecraft.scoreboard.ScorePlayerTeam;
/*      */ import net.minecraft.scoreboard.Scoreboard;
/*      */ import net.minecraft.stats.StatBase;
/*      */ import net.minecraft.tileentity.TileEntity;
/*      */ import net.minecraft.tileentity.TileEntitySign;
/*      */ import net.minecraft.util.IChatComponent;
/*      */ import net.minecraft.util.IThreadListener;
/*      */ import net.minecraft.util.MathHelper;
/*      */ import net.minecraft.world.World;
/*      */ import net.minecraft.world.chunk.Chunk;
/*      */ 
/*      */ public class NetHandlerPlayClient implements INetHandlerPlayClient {
/*   74 */   private static final Logger logger = LogManager.getLogger();
/*      */ 
/*      */ 
/*      */   
/*      */   final NetworkManager netManager;
/*      */ 
/*      */ 
/*      */   
/*      */   private final GameProfile profile;
/*      */ 
/*      */   
/*      */   private final GuiScreen guiScreenServer;
/*      */ 
/*      */   
/*   88 */   private final Map<UUID, NetworkPlayerInfo> playerInfoMap = Maps.newHashMap();
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*   93 */   private final Random avRandomizer = new Random();
/*   94 */   public int currentServerMaxPlayers = 20;
/*      */ 
/*      */   
/*      */   Minecraft gameController;
/*      */ 
/*      */   
/*      */   private WorldClient clientWorldController;
/*      */ 
/*      */   
/*      */   private boolean doneLoadingTerrain;
/*      */ 
/*      */   
/*      */   private boolean field_147308_k = false;
/*      */ 
/*      */ 
/*      */   
/*      */   public NetHandlerPlayClient(Minecraft mcIn, GuiScreen p_i46300_2_, NetworkManager p_i46300_3_, GameProfile p_i46300_4_) {
/*  111 */     this.gameController = mcIn;
/*  112 */     this.guiScreenServer = p_i46300_2_;
/*  113 */     this.netManager = p_i46300_3_;
/*  114 */     this.profile = p_i46300_4_;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void cleanup() {
/*  121 */     this.clientWorldController = null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void handleJoinGame(S01PacketJoinGame packetIn) {
/*  129 */     PacketThreadUtil.checkThreadAndEnqueue((Packet)packetIn, (INetHandler)this, (IThreadListener)this.gameController);
/*  130 */     this.gameController.playerController = new PlayerControllerMP(this.gameController, this);
/*  131 */     this.clientWorldController = new WorldClient(this, new WorldSettings(0L, packetIn.getGameType(), false, packetIn.isHardcoreMode(), packetIn.getWorldType()), packetIn.getDimension(), packetIn.getDifficulty(), this.gameController.mcProfiler);
/*  132 */     this.gameController.gameSettings.difficulty = packetIn.getDifficulty();
/*  133 */     this.gameController.loadWorld(this.clientWorldController);
/*  134 */     this.gameController.thePlayer.dimension = packetIn.getDimension();
/*  135 */     this.gameController.displayGuiScreen((GuiScreen)new GuiDownloadTerrain(this));
/*  136 */     this.gameController.thePlayer.setEntityId(packetIn.getEntityId());
/*  137 */     this.currentServerMaxPlayers = packetIn.getMaxPlayers();
/*  138 */     this.gameController.thePlayer.setReducedDebug(packetIn.isReducedDebugInfo());
/*  139 */     this.gameController.playerController.setGameType(packetIn.getGameType());
/*  140 */     this.gameController.gameSettings.sendSettingsToServer();
/*  141 */     this.netManager.sendPacket((Packet)new C17PacketCustomPayload("MC|Brand", (new PacketBuffer(Unpooled.buffer())).writeString(ClientBrandRetriever.getClientModName())));
/*  142 */     EventManager.call((Event)new RespawnEvent());
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public void handleSpawnObject(S0EPacketSpawnObject packetIn) {
/*      */     EntityFallingBlock entityFallingBlock;
/*  149 */     PacketThreadUtil.checkThreadAndEnqueue((Packet)packetIn, (INetHandler)this, (IThreadListener)this.gameController);
/*  150 */     double d0 = packetIn.getX() / 32.0D;
/*  151 */     double d1 = packetIn.getY() / 32.0D;
/*  152 */     double d2 = packetIn.getZ() / 32.0D;
/*  153 */     Entity entity = null;
/*      */     
/*  155 */     if (packetIn.getType() == 10) {
/*  156 */       EntityMinecart entityMinecart = EntityMinecart.getMinecart((World)this.clientWorldController, d0, d1, d2, EntityMinecart.EnumMinecartType.byNetworkID(packetIn.func_149009_m()));
/*  157 */     } else if (packetIn.getType() == 90) {
/*  158 */       Entity entity1 = this.clientWorldController.getEntityByID(packetIn.func_149009_m());
/*      */       
/*  160 */       if (entity1 instanceof EntityPlayer) {
/*  161 */         EntityFishHook entityFishHook = new EntityFishHook((World)this.clientWorldController, d0, d1, d2, (EntityPlayer)entity1);
/*      */       }
/*      */       
/*  164 */       packetIn.func_149002_g(0);
/*  165 */     } else if (packetIn.getType() == 60) {
/*  166 */       EntityArrow entityArrow = new EntityArrow((World)this.clientWorldController, d0, d1, d2);
/*  167 */     } else if (packetIn.getType() == 61) {
/*  168 */       EntitySnowball entitySnowball = new EntitySnowball((World)this.clientWorldController, d0, d1, d2);
/*  169 */     } else if (packetIn.getType() == 71) {
/*  170 */       EntityItemFrame entityItemFrame = new EntityItemFrame((World)this.clientWorldController, new BlockPos(MathHelper.floor_double(d0), MathHelper.floor_double(d1), MathHelper.floor_double(d2)), EnumFacing.getHorizontal(packetIn.func_149009_m()));
/*  171 */       packetIn.func_149002_g(0);
/*  172 */     } else if (packetIn.getType() == 77) {
/*  173 */       EntityLeashKnot entityLeashKnot = new EntityLeashKnot((World)this.clientWorldController, new BlockPos(MathHelper.floor_double(d0), MathHelper.floor_double(d1), MathHelper.floor_double(d2)));
/*  174 */       packetIn.func_149002_g(0);
/*  175 */     } else if (packetIn.getType() == 65) {
/*  176 */       EntityEnderPearl entityEnderPearl = new EntityEnderPearl((World)this.clientWorldController, d0, d1, d2);
/*  177 */     } else if (packetIn.getType() == 72) {
/*  178 */       EntityEnderEye entityEnderEye = new EntityEnderEye((World)this.clientWorldController, d0, d1, d2);
/*  179 */     } else if (packetIn.getType() == 76) {
/*  180 */       EntityFireworkRocket entityFireworkRocket = new EntityFireworkRocket((World)this.clientWorldController, d0, d1, d2, (ItemStack)null);
/*  181 */     } else if (packetIn.getType() == 63) {
/*  182 */       EntityLargeFireball entityLargeFireball = new EntityLargeFireball((World)this.clientWorldController, d0, d1, d2, packetIn.getSpeedX() / 8000.0D, packetIn.getSpeedY() / 8000.0D, packetIn.getSpeedZ() / 8000.0D);
/*  183 */       packetIn.func_149002_g(0);
/*  184 */     } else if (packetIn.getType() == 64) {
/*  185 */       EntitySmallFireball entitySmallFireball = new EntitySmallFireball((World)this.clientWorldController, d0, d1, d2, packetIn.getSpeedX() / 8000.0D, packetIn.getSpeedY() / 8000.0D, packetIn.getSpeedZ() / 8000.0D);
/*  186 */       packetIn.func_149002_g(0);
/*  187 */     } else if (packetIn.getType() == 66) {
/*  188 */       EntityWitherSkull entityWitherSkull = new EntityWitherSkull((World)this.clientWorldController, d0, d1, d2, packetIn.getSpeedX() / 8000.0D, packetIn.getSpeedY() / 8000.0D, packetIn.getSpeedZ() / 8000.0D);
/*  189 */       packetIn.func_149002_g(0);
/*  190 */     } else if (packetIn.getType() == 62) {
/*  191 */       EntityEgg entityEgg = new EntityEgg((World)this.clientWorldController, d0, d1, d2);
/*  192 */     } else if (packetIn.getType() == 73) {
/*  193 */       EntityPotion entityPotion = new EntityPotion((World)this.clientWorldController, d0, d1, d2, packetIn.func_149009_m());
/*  194 */       packetIn.func_149002_g(0);
/*  195 */     } else if (packetIn.getType() == 75) {
/*  196 */       EntityExpBottle entityExpBottle = new EntityExpBottle((World)this.clientWorldController, d0, d1, d2);
/*  197 */       packetIn.func_149002_g(0);
/*  198 */     } else if (packetIn.getType() == 1) {
/*  199 */       EntityBoat entityBoat = new EntityBoat((World)this.clientWorldController, d0, d1, d2);
/*  200 */     } else if (packetIn.getType() == 50) {
/*  201 */       EntityTNTPrimed entityTNTPrimed = new EntityTNTPrimed((World)this.clientWorldController, d0, d1, d2, (EntityLivingBase)null);
/*  202 */     } else if (packetIn.getType() == 78) {
/*  203 */       EntityArmorStand entityArmorStand = new EntityArmorStand((World)this.clientWorldController, d0, d1, d2);
/*  204 */     } else if (packetIn.getType() == 51) {
/*  205 */       EntityEnderCrystal entityEnderCrystal = new EntityEnderCrystal((World)this.clientWorldController, d0, d1, d2);
/*  206 */     } else if (packetIn.getType() == 2) {
/*  207 */       EntityItem entityItem = new EntityItem((World)this.clientWorldController, d0, d1, d2);
/*  208 */     } else if (packetIn.getType() == 70) {
/*  209 */       entityFallingBlock = new EntityFallingBlock((World)this.clientWorldController, d0, d1, d2, Block.getStateById(packetIn.func_149009_m() & 0xFFFF));
/*  210 */       packetIn.func_149002_g(0);
/*      */     } 
/*      */     
/*  213 */     if (entityFallingBlock != null) {
/*  214 */       ((Entity)entityFallingBlock).serverPosX = packetIn.getX();
/*  215 */       ((Entity)entityFallingBlock).serverPosY = packetIn.getY();
/*  216 */       ((Entity)entityFallingBlock).serverPosZ = packetIn.getZ();
/*  217 */       ((Entity)entityFallingBlock).rotationPitch = (packetIn.getPitch() * 360) / 256.0F;
/*  218 */       ((Entity)entityFallingBlock).rotationYaw = (packetIn.getYaw() * 360) / 256.0F;
/*  219 */       Entity[] aentity = entityFallingBlock.getParts();
/*      */       
/*  221 */       if (aentity != null) {
/*  222 */         int i = packetIn.getEntityID() - entityFallingBlock.getEntityId();
/*      */         
/*  224 */         for (int j = 0; j < aentity.length; j++) {
/*  225 */           aentity[j].setEntityId(aentity[j].getEntityId() + i);
/*      */         }
/*      */       } 
/*      */       
/*  229 */       entityFallingBlock.setEntityId(packetIn.getEntityID());
/*  230 */       this.clientWorldController.addEntityToWorld(packetIn.getEntityID(), (Entity)entityFallingBlock);
/*      */       
/*  232 */       if (packetIn.func_149009_m() > 0) {
/*  233 */         if (packetIn.getType() == 60) {
/*  234 */           Entity entity2 = this.clientWorldController.getEntityByID(packetIn.func_149009_m());
/*      */           
/*  236 */           if (entity2 instanceof EntityLivingBase && entityFallingBlock instanceof EntityArrow) {
/*  237 */             ((EntityArrow)entityFallingBlock).shootingEntity = entity2;
/*      */           }
/*      */         } 
/*      */         
/*  241 */         entityFallingBlock.setVelocity(packetIn.getSpeedX() / 8000.0D, packetIn.getSpeedY() / 8000.0D, packetIn.getSpeedZ() / 8000.0D);
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void handleSpawnExperienceOrb(S11PacketSpawnExperienceOrb packetIn) {
/*  250 */     PacketThreadUtil.checkThreadAndEnqueue((Packet)packetIn, (INetHandler)this, (IThreadListener)this.gameController);
/*  251 */     EntityXPOrb entityXPOrb = new EntityXPOrb((World)this.clientWorldController, packetIn.getX() / 32.0D, packetIn.getY() / 32.0D, packetIn.getZ() / 32.0D, packetIn.getXPValue());
/*  252 */     ((Entity)entityXPOrb).serverPosX = packetIn.getX();
/*  253 */     ((Entity)entityXPOrb).serverPosY = packetIn.getY();
/*  254 */     ((Entity)entityXPOrb).serverPosZ = packetIn.getZ();
/*  255 */     ((Entity)entityXPOrb).rotationYaw = 0.0F;
/*  256 */     ((Entity)entityXPOrb).rotationPitch = 0.0F;
/*  257 */     entityXPOrb.setEntityId(packetIn.getEntityID());
/*  258 */     this.clientWorldController.addEntityToWorld(packetIn.getEntityID(), (Entity)entityXPOrb);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public void handleSpawnGlobalEntity(S2CPacketSpawnGlobalEntity packetIn) {
/*      */     EntityLightningBolt entityLightningBolt;
/*  265 */     PacketThreadUtil.checkThreadAndEnqueue((Packet)packetIn, (INetHandler)this, (IThreadListener)this.gameController);
/*  266 */     double d0 = packetIn.func_149051_d() / 32.0D;
/*  267 */     double d1 = packetIn.func_149050_e() / 32.0D;
/*  268 */     double d2 = packetIn.func_149049_f() / 32.0D;
/*  269 */     Entity entity = null;
/*      */     
/*  271 */     if (packetIn.func_149053_g() == 1) {
/*  272 */       entityLightningBolt = new EntityLightningBolt((World)this.clientWorldController, d0, d1, d2);
/*      */     }
/*      */     
/*  275 */     if (entityLightningBolt != null) {
/*  276 */       ((Entity)entityLightningBolt).serverPosX = packetIn.func_149051_d();
/*  277 */       ((Entity)entityLightningBolt).serverPosY = packetIn.func_149050_e();
/*  278 */       ((Entity)entityLightningBolt).serverPosZ = packetIn.func_149049_f();
/*  279 */       ((Entity)entityLightningBolt).rotationYaw = 0.0F;
/*  280 */       ((Entity)entityLightningBolt).rotationPitch = 0.0F;
/*  281 */       entityLightningBolt.setEntityId(packetIn.func_149052_c());
/*  282 */       this.clientWorldController.addWeatherEffect((Entity)entityLightningBolt);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void handleSpawnPainting(S10PacketSpawnPainting packetIn) {
/*  290 */     PacketThreadUtil.checkThreadAndEnqueue((Packet)packetIn, (INetHandler)this, (IThreadListener)this.gameController);
/*  291 */     EntityPainting entitypainting = new EntityPainting((World)this.clientWorldController, packetIn.getPosition(), packetIn.getFacing(), packetIn.getTitle());
/*  292 */     this.clientWorldController.addEntityToWorld(packetIn.getEntityID(), (Entity)entitypainting);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void handleEntityVelocity(S12PacketEntityVelocity packetIn) {
/*  299 */     PacketThreadUtil.checkThreadAndEnqueue((Packet)packetIn, (INetHandler)this, (IThreadListener)this.gameController);
/*  300 */     Entity entity = this.clientWorldController.getEntityByID(packetIn.getEntityID());
/*      */     
/*  302 */     if (entity != null) {
/*  303 */       entity.setVelocity(packetIn.getMotionX() / 8000.0D, packetIn.getMotionY() / 8000.0D, packetIn.getMotionZ() / 8000.0D);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void handleEntityMetadata(S1CPacketEntityMetadata packetIn) {
/*  312 */     PacketThreadUtil.checkThreadAndEnqueue((Packet)packetIn, (INetHandler)this, (IThreadListener)this.gameController);
/*  313 */     Entity entity = this.clientWorldController.getEntityByID(packetIn.getEntityId());
/*      */     
/*  315 */     if (entity != null && packetIn.func_149376_c() != null) {
/*  316 */       entity.getDataWatcher().updateWatchedObjectsFromList(packetIn.func_149376_c());
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void handleSpawnPlayer(S0CPacketSpawnPlayer packetIn) {
/*  324 */     PacketThreadUtil.checkThreadAndEnqueue((Packet)packetIn, (INetHandler)this, (IThreadListener)this.gameController);
/*  325 */     double d0 = packetIn.getX() / 32.0D;
/*  326 */     double d1 = packetIn.getY() / 32.0D;
/*  327 */     double d2 = packetIn.getZ() / 32.0D;
/*  328 */     float f = (packetIn.getYaw() * 360) / 256.0F;
/*  329 */     float f1 = (packetIn.getPitch() * 360) / 256.0F;
/*  330 */     EntityOtherPlayerMP entityotherplayermp = new EntityOtherPlayerMP((World)this.gameController.theWorld, getPlayerInfo(packetIn.getPlayer()).getGameProfile());
/*  331 */     entityotherplayermp.prevPosX = entityotherplayermp.lastTickPosX = (entityotherplayermp.serverPosX = packetIn.getX());
/*  332 */     entityotherplayermp.prevPosY = entityotherplayermp.lastTickPosY = (entityotherplayermp.serverPosY = packetIn.getY());
/*  333 */     entityotherplayermp.prevPosZ = entityotherplayermp.lastTickPosZ = (entityotherplayermp.serverPosZ = packetIn.getZ());
/*  334 */     int i = packetIn.getCurrentItemID();
/*      */     
/*  336 */     if (i == 0) {
/*  337 */       entityotherplayermp.inventory.mainInventory[entityotherplayermp.inventory.currentItem] = null;
/*      */     } else {
/*  339 */       entityotherplayermp.inventory.mainInventory[entityotherplayermp.inventory.currentItem] = new ItemStack(Item.getItemById(i), 1, 0);
/*      */     } 
/*      */     
/*  342 */     entityotherplayermp.setPositionAndRotation(d0, d1, d2, f, f1);
/*  343 */     this.clientWorldController.addEntityToWorld(packetIn.getEntityID(), (Entity)entityotherplayermp);
/*  344 */     List<DataWatcher.WatchableObject> list = packetIn.func_148944_c();
/*      */     
/*  346 */     if (list != null) {
/*  347 */       entityotherplayermp.getDataWatcher().updateWatchedObjectsFromList(list);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void handleEntityTeleport(S18PacketEntityTeleport packetIn) {
/*  355 */     PacketThreadUtil.checkThreadAndEnqueue((Packet)packetIn, (INetHandler)this, (IThreadListener)this.gameController);
/*  356 */     Entity entity = this.clientWorldController.getEntityByID(packetIn.getEntityId());
/*      */     
/*  358 */     if (entity != null) {
/*  359 */       entity.serverPosX = packetIn.getX();
/*  360 */       entity.serverPosY = packetIn.getY();
/*  361 */       entity.serverPosZ = packetIn.getZ();
/*  362 */       double d0 = entity.serverPosX / 32.0D;
/*  363 */       double d1 = entity.serverPosY / 32.0D;
/*  364 */       double d2 = entity.serverPosZ / 32.0D;
/*  365 */       float f = (packetIn.getYaw() * 360) / 256.0F;
/*  366 */       float f1 = (packetIn.getPitch() * 360) / 256.0F;
/*      */       
/*  368 */       if (Math.abs(entity.posX - d0) < 0.03125D && Math.abs(entity.posY - d1) < 0.015625D && Math.abs(entity.posZ - d2) < 0.03125D) {
/*  369 */         entity.setPositionAndRotation2(entity.posX, entity.posY, entity.posZ, f, f1, 3, true);
/*      */       } else {
/*  371 */         entity.setPositionAndRotation2(d0, d1, d2, f, f1, 3, true);
/*      */       } 
/*      */       
/*  374 */       entity.onGround = packetIn.getOnGround();
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void handleHeldItemChange(S09PacketHeldItemChange packetIn) {
/*  382 */     PacketThreadUtil.checkThreadAndEnqueue((Packet)packetIn, (INetHandler)this, (IThreadListener)this.gameController);
/*      */     
/*  384 */     if (packetIn.getHeldItemHotbarIndex() >= 0 && packetIn.getHeldItemHotbarIndex() < InventoryPlayer.getHotbarSize()) {
/*  385 */       this.gameController.thePlayer.inventory.currentItem = packetIn.getHeldItemHotbarIndex();
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void handleEntityMovement(S14PacketEntity packetIn) {
/*  395 */     PacketThreadUtil.checkThreadAndEnqueue((Packet)packetIn, (INetHandler)this, (IThreadListener)this.gameController);
/*  396 */     Entity entity = packetIn.getEntity((World)this.clientWorldController);
/*      */     
/*  398 */     if (entity != null) {
/*  399 */       entity.serverPosX += packetIn.func_149062_c();
/*  400 */       entity.serverPosY += packetIn.func_149061_d();
/*  401 */       entity.serverPosZ += packetIn.func_149064_e();
/*  402 */       double d0 = entity.serverPosX / 32.0D;
/*  403 */       double d1 = entity.serverPosY / 32.0D;
/*  404 */       double d2 = entity.serverPosZ / 32.0D;
/*  405 */       float f = packetIn.func_149060_h() ? ((packetIn.func_149066_f() * 360) / 256.0F) : entity.rotationYaw;
/*  406 */       float f1 = packetIn.func_149060_h() ? ((packetIn.func_149063_g() * 360) / 256.0F) : entity.rotationPitch;
/*  407 */       entity.setPositionAndRotation2(d0, d1, d2, f, f1, 3, false);
/*  408 */       entity.onGround = packetIn.getOnGround();
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void handleEntityHeadLook(S19PacketEntityHeadLook packetIn) {
/*  417 */     PacketThreadUtil.checkThreadAndEnqueue((Packet)packetIn, (INetHandler)this, (IThreadListener)this.gameController);
/*  418 */     Entity entity = packetIn.getEntity((World)this.clientWorldController);
/*      */     
/*  420 */     if (entity != null) {
/*  421 */       float f = (packetIn.getYaw() * 360) / 256.0F;
/*  422 */       entity.setRotationYawHead(f);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void handleDestroyEntities(S13PacketDestroyEntities packetIn) {
/*  432 */     PacketThreadUtil.checkThreadAndEnqueue((Packet)packetIn, (INetHandler)this, (IThreadListener)this.gameController);
/*      */     
/*  434 */     for (int i = 0; i < (packetIn.getEntityIDs()).length; i++) {
/*  435 */       this.clientWorldController.removeEntityFromWorld(packetIn.getEntityIDs()[i]);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void handlePlayerPosLook(S08PacketPlayerPosLook packetIn) {
/*  445 */     PacketThreadUtil.checkThreadAndEnqueue((Packet)packetIn, (INetHandler)this, (IThreadListener)this.gameController);
/*  446 */     EntityPlayerSP entityPlayerSP = this.gameController.thePlayer;
/*  447 */     double d0 = packetIn.getX();
/*  448 */     double d1 = packetIn.getY();
/*  449 */     double d2 = packetIn.getZ();
/*  450 */     float f = packetIn.getYaw();
/*  451 */     float f1 = packetIn.getPitch();
/*      */     
/*  453 */     if (packetIn.func_179834_f().contains(S08PacketPlayerPosLook.EnumFlags.X)) {
/*  454 */       d0 += ((EntityPlayer)entityPlayerSP).posX;
/*      */     } else {
/*  456 */       ((EntityPlayer)entityPlayerSP).motionX = 0.0D;
/*      */     } 
/*      */     
/*  459 */     if (packetIn.func_179834_f().contains(S08PacketPlayerPosLook.EnumFlags.Y)) {
/*  460 */       d1 += ((EntityPlayer)entityPlayerSP).posY;
/*      */     } else {
/*  462 */       ((EntityPlayer)entityPlayerSP).motionY = 0.0D;
/*      */     } 
/*      */     
/*  465 */     if (packetIn.func_179834_f().contains(S08PacketPlayerPosLook.EnumFlags.Z)) {
/*  466 */       d2 += ((EntityPlayer)entityPlayerSP).posZ;
/*      */     } else {
/*  468 */       ((EntityPlayer)entityPlayerSP).motionZ = 0.0D;
/*      */     } 
/*      */     
/*  471 */     if (packetIn.func_179834_f().contains(S08PacketPlayerPosLook.EnumFlags.X_ROT)) {
/*  472 */       f1 += ((EntityPlayer)entityPlayerSP).rotationPitch;
/*      */     }
/*      */     
/*  475 */     if (packetIn.func_179834_f().contains(S08PacketPlayerPosLook.EnumFlags.Y_ROT)) {
/*  476 */       f += ((EntityPlayer)entityPlayerSP).rotationYaw;
/*      */     }
/*      */     
/*  479 */     entityPlayerSP.setPositionAndRotation(d0, d1, d2, f, f1);
/*  480 */     this.netManager.sendPacket((Packet)new C03PacketPlayer.C06PacketPlayerPosLook(((EntityPlayer)entityPlayerSP).posX, (entityPlayerSP.getEntityBoundingBox()).minY, ((EntityPlayer)entityPlayerSP).posZ, ((EntityPlayer)entityPlayerSP).rotationYaw, ((EntityPlayer)entityPlayerSP).rotationPitch, false));
/*      */     
/*  482 */     if (!this.doneLoadingTerrain) {
/*  483 */       this.gameController.thePlayer.prevPosX = this.gameController.thePlayer.posX;
/*  484 */       this.gameController.thePlayer.prevPosY = this.gameController.thePlayer.posY;
/*  485 */       this.gameController.thePlayer.prevPosZ = this.gameController.thePlayer.posZ;
/*  486 */       this.doneLoadingTerrain = true;
/*  487 */       this.gameController.displayGuiScreen((GuiScreen)null);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void handleMultiBlockChange(S22PacketMultiBlockChange packetIn) {
/*  497 */     PacketThreadUtil.checkThreadAndEnqueue((Packet)packetIn, (INetHandler)this, (IThreadListener)this.gameController);
/*      */     
/*  499 */     for (S22PacketMultiBlockChange.BlockUpdateData s22packetmultiblockchange$blockupdatedata : packetIn.getChangedBlocks()) {
/*  500 */       this.clientWorldController.invalidateRegionAndSetBlock(s22packetmultiblockchange$blockupdatedata.getPos(), s22packetmultiblockchange$blockupdatedata.getBlockState());
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void handleChunkData(S21PacketChunkData packetIn) {
/*  508 */     PacketThreadUtil.checkThreadAndEnqueue((Packet)packetIn, (INetHandler)this, (IThreadListener)this.gameController);
/*      */     
/*  510 */     if (packetIn.func_149274_i()) {
/*  511 */       if (packetIn.getExtractedSize() == 0) {
/*  512 */         this.clientWorldController.doPreChunk(packetIn.getChunkX(), packetIn.getChunkZ(), false);
/*      */         
/*      */         return;
/*      */       } 
/*  516 */       this.clientWorldController.doPreChunk(packetIn.getChunkX(), packetIn.getChunkZ(), true);
/*      */     } 
/*      */     
/*  519 */     this.clientWorldController.invalidateBlockReceiveRegion(packetIn.getChunkX() << 4, 0, packetIn.getChunkZ() << 4, (packetIn.getChunkX() << 4) + 15, 256, (packetIn.getChunkZ() << 4) + 15);
/*  520 */     Chunk chunk = this.clientWorldController.getChunkFromChunkCoords(packetIn.getChunkX(), packetIn.getChunkZ());
/*  521 */     chunk.fillChunk(packetIn.getExtractedDataBytes(), packetIn.getExtractedSize(), packetIn.func_149274_i());
/*  522 */     this.clientWorldController.markBlockRangeForRenderUpdate(packetIn.getChunkX() << 4, 0, packetIn.getChunkZ() << 4, (packetIn.getChunkX() << 4) + 15, 256, (packetIn.getChunkZ() << 4) + 15);
/*      */     
/*  524 */     if (!packetIn.func_149274_i() || !(this.clientWorldController.provider instanceof net.minecraft.world.WorldProviderSurface)) {
/*  525 */       chunk.resetRelightChecks();
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void handleBlockChange(S23PacketBlockChange packetIn) {
/*  533 */     PacketThreadUtil.checkThreadAndEnqueue((Packet)packetIn, (INetHandler)this, (IThreadListener)this.gameController);
/*  534 */     this.clientWorldController.invalidateRegionAndSetBlock(packetIn.getBlockPosition(), packetIn.getBlockState());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void handleDisconnect(S40PacketDisconnect packetIn) {
/*  541 */     this.netManager.closeChannel(packetIn.getReason());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void onDisconnect(IChatComponent reason) {
/*  548 */     this.gameController.loadWorld((WorldClient)null);
/*      */     
/*  550 */     if (this.guiScreenServer != null) {
/*  551 */       this.gameController.displayGuiScreen((GuiScreen)new GuiDisconnected(this.guiScreenServer, "disconnect.lost", reason));
/*      */     } else {
/*  553 */       this.gameController.displayGuiScreen((GuiScreen)new GuiDisconnected((GuiScreen)new GuiMultiplayer((GuiScreen)new ClientMainMenu()), "disconnect.lost", reason));
/*      */     } 
/*      */   }
/*      */   
/*      */   public void addToSendQueue(Packet p_147297_1_) {
/*  558 */     this.netManager.sendPacket(p_147297_1_);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void handleCollectItem(S0DPacketCollectItem packetIn) {
/*      */     EntityPlayerSP entityPlayerSP;
/*  579 */     PacketThreadUtil.checkThreadAndEnqueue((Packet)packetIn, (INetHandler)this, (IThreadListener)this.gameController);
/*  580 */     Entity entity = this.clientWorldController.getEntityByID(packetIn.getCollectedItemEntityID());
/*  581 */     EntityLivingBase entitylivingbase = (EntityLivingBase)this.clientWorldController.getEntityByID(packetIn.getEntityID());
/*      */     
/*  583 */     if (entitylivingbase == null) {
/*  584 */       entityPlayerSP = this.gameController.thePlayer;
/*      */     }
/*      */     
/*  587 */     if (entity != null) {
/*  588 */       if (entity instanceof EntityXPOrb) {
/*  589 */         this.clientWorldController.playSoundAtEntity(entity, "random.orb", 0.2F, ((this.avRandomizer.nextFloat() - this.avRandomizer.nextFloat()) * 0.7F + 1.0F) * 2.0F);
/*      */       } else {
/*  591 */         this.clientWorldController.playSoundAtEntity(entity, "random.pop", 0.2F, ((this.avRandomizer.nextFloat() - this.avRandomizer.nextFloat()) * 0.7F + 1.0F) * 2.0F);
/*      */       } 
/*      */       
/*  594 */       this.gameController.effectRenderer.addEffect((EntityFX)new EntityPickupFX((World)this.clientWorldController, entity, (Entity)entityPlayerSP, 0.5F));
/*  595 */       this.clientWorldController.removeEntityFromWorld(packetIn.getCollectedItemEntityID());
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void handleChat(S02PacketChat packetIn) {
/*  603 */     PacketThreadUtil.checkThreadAndEnqueue((Packet)packetIn, (INetHandler)this, (IThreadListener)this.gameController);
/*      */     
/*  605 */     if (packetIn.getType() == 2) {
/*  606 */       this.gameController.ingameGUI.setRecordPlaying(packetIn.getChatComponent(), false);
/*      */     } else {
/*  608 */       this.gameController.ingameGUI.getChatGUI().printChatMessage(packetIn.getChatComponent());
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void handleAnimation(S0BPacketAnimation packetIn) {
/*  617 */     PacketThreadUtil.checkThreadAndEnqueue((Packet)packetIn, (INetHandler)this, (IThreadListener)this.gameController);
/*  618 */     Entity entity = this.clientWorldController.getEntityByID(packetIn.getEntityID());
/*      */     
/*  620 */     if (entity != null) {
/*  621 */       if (packetIn.getAnimationType() == 0) {
/*  622 */         EntityLivingBase entitylivingbase = (EntityLivingBase)entity;
/*  623 */         entitylivingbase.swingItem();
/*  624 */       } else if (packetIn.getAnimationType() == 1) {
/*  625 */         entity.performHurtAnimation();
/*  626 */       } else if (packetIn.getAnimationType() == 2) {
/*  627 */         EntityPlayer entityplayer = (EntityPlayer)entity;
/*  628 */         entityplayer.wakeUpPlayer(false, false, false);
/*  629 */       } else if (packetIn.getAnimationType() == 4) {
/*  630 */         this.gameController.effectRenderer.emitParticleAtEntity(entity, EnumParticleTypes.CRIT);
/*  631 */       } else if (packetIn.getAnimationType() == 5) {
/*  632 */         this.gameController.effectRenderer.emitParticleAtEntity(entity, EnumParticleTypes.CRIT_MAGIC);
/*      */       } 
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void handleUseBed(S0APacketUseBed packetIn) {
/*  642 */     PacketThreadUtil.checkThreadAndEnqueue((Packet)packetIn, (INetHandler)this, (IThreadListener)this.gameController);
/*  643 */     packetIn.getPlayer((World)this.clientWorldController).trySleep(packetIn.getBedPosition());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void handleSpawnMob(S0FPacketSpawnMob packetIn) {
/*  651 */     PacketThreadUtil.checkThreadAndEnqueue((Packet)packetIn, (INetHandler)this, (IThreadListener)this.gameController);
/*  652 */     double d0 = packetIn.getX() / 32.0D;
/*  653 */     double d1 = packetIn.getY() / 32.0D;
/*  654 */     double d2 = packetIn.getZ() / 32.0D;
/*  655 */     float f = (packetIn.getYaw() * 360) / 256.0F;
/*  656 */     float f1 = (packetIn.getPitch() * 360) / 256.0F;
/*  657 */     EntityLivingBase entitylivingbase = (EntityLivingBase)EntityList.createEntityByID(packetIn.getEntityType(), (World)this.gameController.theWorld);
/*  658 */     entitylivingbase.serverPosX = packetIn.getX();
/*  659 */     entitylivingbase.serverPosY = packetIn.getY();
/*  660 */     entitylivingbase.serverPosZ = packetIn.getZ();
/*  661 */     entitylivingbase.renderYawOffset = entitylivingbase.rotationYawHead = (packetIn.getHeadPitch() * 360) / 256.0F;
/*  662 */     Entity[] aentity = entitylivingbase.getParts();
/*      */     
/*  664 */     if (aentity != null) {
/*  665 */       int i = packetIn.getEntityID() - entitylivingbase.getEntityId();
/*      */       
/*  667 */       for (int j = 0; j < aentity.length; j++) {
/*  668 */         aentity[j].setEntityId(aentity[j].getEntityId() + i);
/*      */       }
/*      */     } 
/*      */     
/*  672 */     entitylivingbase.setEntityId(packetIn.getEntityID());
/*  673 */     entitylivingbase.setPositionAndRotation(d0, d1, d2, f, f1);
/*  674 */     entitylivingbase.motionX = (packetIn.getVelocityX() / 8000.0F);
/*  675 */     entitylivingbase.motionY = (packetIn.getVelocityY() / 8000.0F);
/*  676 */     entitylivingbase.motionZ = (packetIn.getVelocityZ() / 8000.0F);
/*  677 */     this.clientWorldController.addEntityToWorld(packetIn.getEntityID(), (Entity)entitylivingbase);
/*  678 */     List<DataWatcher.WatchableObject> list = packetIn.func_149027_c();
/*      */     
/*  680 */     if (list != null) {
/*  681 */       entitylivingbase.getDataWatcher().updateWatchedObjectsFromList(list);
/*      */     }
/*      */   }
/*      */   
/*      */   public void handleTimeUpdate(S03PacketTimeUpdate packetIn) {
/*  686 */     PacketThreadUtil.checkThreadAndEnqueue((Packet)packetIn, (INetHandler)this, (IThreadListener)this.gameController);
/*  687 */     this.gameController.theWorld.setTotalWorldTime(packetIn.getTotalWorldTime());
/*  688 */     this.gameController.theWorld.setWorldTime(packetIn.getWorldTime());
/*      */   }
/*      */   
/*      */   public void handleSpawnPosition(S05PacketSpawnPosition packetIn) {
/*  692 */     PacketThreadUtil.checkThreadAndEnqueue((Packet)packetIn, (INetHandler)this, (IThreadListener)this.gameController);
/*  693 */     this.gameController.thePlayer.setSpawnPoint(packetIn.getSpawnPos(), true);
/*  694 */     this.gameController.theWorld.getWorldInfo().setSpawn(packetIn.getSpawnPos());
/*      */   }
/*      */   public void handleEntityAttach(S1BPacketEntityAttach packetIn) {
/*      */     EntityPlayerSP entityPlayerSP;
/*  698 */     PacketThreadUtil.checkThreadAndEnqueue((Packet)packetIn, (INetHandler)this, (IThreadListener)this.gameController);
/*  699 */     Entity entity = this.clientWorldController.getEntityByID(packetIn.getEntityId());
/*  700 */     Entity entity1 = this.clientWorldController.getEntityByID(packetIn.getVehicleEntityId());
/*      */     
/*  702 */     if (packetIn.getLeash() == 0) {
/*  703 */       boolean flag = false;
/*      */       
/*  705 */       if (packetIn.getEntityId() == this.gameController.thePlayer.getEntityId()) {
/*  706 */         entityPlayerSP = this.gameController.thePlayer;
/*      */         
/*  708 */         if (entity1 instanceof EntityBoat) {
/*  709 */           ((EntityBoat)entity1).setIsBoatEmpty(false);
/*      */         }
/*      */         
/*  712 */         flag = (((Entity)entityPlayerSP).ridingEntity == null && entity1 != null);
/*  713 */       } else if (entity1 instanceof EntityBoat) {
/*  714 */         ((EntityBoat)entity1).setIsBoatEmpty(true);
/*      */       } 
/*      */       
/*  717 */       if (entityPlayerSP == null) {
/*      */         return;
/*      */       }
/*      */       
/*  721 */       entityPlayerSP.mountEntity(entity1);
/*      */       
/*  723 */       if (flag) {
/*  724 */         GameSettings gamesettings = this.gameController.gameSettings;
/*  725 */         this.gameController.ingameGUI.setRecordPlaying(I18n.format("mount.onboard", new Object[] { GameSettings.getKeyDisplayString(gamesettings.keyBindSneak.getKeyCode()) }), false);
/*      */       } 
/*  727 */     } else if (packetIn.getLeash() == 1 && entityPlayerSP instanceof EntityLiving) {
/*  728 */       if (entity1 != null) {
/*  729 */         ((EntityLiving)entityPlayerSP).setLeashedToEntity(entity1, false);
/*      */       } else {
/*  731 */         ((EntityLiving)entityPlayerSP).clearLeashed(false, false);
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void handleEntityStatus(S19PacketEntityStatus packetIn) {
/*  743 */     PacketThreadUtil.checkThreadAndEnqueue((Packet)packetIn, (INetHandler)this, (IThreadListener)this.gameController);
/*  744 */     Entity entity = packetIn.getEntity((World)this.clientWorldController);
/*      */     
/*  746 */     if (entity != null) {
/*  747 */       if (packetIn.getOpCode() == 21) {
/*  748 */         this.gameController.getSoundHandler().playSound((ISound)new GuardianSound((EntityGuardian)entity));
/*      */       } else {
/*  750 */         entity.handleStatusUpdate(packetIn.getOpCode());
/*      */       } 
/*      */     }
/*      */   }
/*      */   
/*      */   public void handleUpdateHealth(S06PacketUpdateHealth packetIn) {
/*  756 */     PacketThreadUtil.checkThreadAndEnqueue((Packet)packetIn, (INetHandler)this, (IThreadListener)this.gameController);
/*  757 */     this.gameController.thePlayer.setPlayerSPHealth(packetIn.getHealth());
/*  758 */     this.gameController.thePlayer.getFoodStats().setFoodLevel(packetIn.getFoodLevel());
/*  759 */     this.gameController.thePlayer.getFoodStats().setFoodSaturationLevel(packetIn.getSaturationLevel());
/*      */   }
/*      */   
/*      */   public void handleSetExperience(S1FPacketSetExperience packetIn) {
/*  763 */     PacketThreadUtil.checkThreadAndEnqueue((Packet)packetIn, (INetHandler)this, (IThreadListener)this.gameController);
/*  764 */     this.gameController.thePlayer.setXPStats(packetIn.func_149397_c(), packetIn.getTotalExperience(), packetIn.getLevel());
/*      */   }
/*      */   
/*      */   public void handleRespawn(S07PacketRespawn packetIn) {
/*  768 */     PacketThreadUtil.checkThreadAndEnqueue((Packet)packetIn, (INetHandler)this, (IThreadListener)this.gameController);
/*      */     
/*  770 */     if (packetIn.getDimensionID() != this.gameController.thePlayer.dimension) {
/*  771 */       this.doneLoadingTerrain = false;
/*  772 */       Scoreboard scoreboard = this.clientWorldController.getScoreboard();
/*  773 */       this.clientWorldController = new WorldClient(this, new WorldSettings(0L, packetIn.getGameType(), false, this.gameController.theWorld.getWorldInfo().isHardcoreModeEnabled(), packetIn.getWorldType()), packetIn.getDimensionID(), packetIn.getDifficulty(), this.gameController.mcProfiler);
/*  774 */       this.clientWorldController.setWorldScoreboard(scoreboard);
/*  775 */       this.gameController.loadWorld(this.clientWorldController);
/*  776 */       this.gameController.thePlayer.dimension = packetIn.getDimensionID();
/*  777 */       this.gameController.displayGuiScreen((GuiScreen)new GuiDownloadTerrain(this));
/*      */     } 
/*      */     
/*  780 */     this.gameController.setDimensionAndSpawnPlayer(packetIn.getDimensionID());
/*  781 */     this.gameController.playerController.setGameType(packetIn.getGameType());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void handleExplosion(S27PacketExplosion packetIn) {
/*  788 */     PacketThreadUtil.checkThreadAndEnqueue((Packet)packetIn, (INetHandler)this, (IThreadListener)this.gameController);
/*  789 */     Explosion explosion = new Explosion((World)this.gameController.theWorld, (Entity)null, packetIn.getX(), packetIn.getY(), packetIn.getZ(), packetIn.getStrength(), packetIn.getAffectedBlockPositions());
/*  790 */     explosion.doExplosionB(true);
/*  791 */     this.gameController.thePlayer.motionX += packetIn.func_149149_c();
/*  792 */     this.gameController.thePlayer.motionY += packetIn.func_149144_d();
/*  793 */     this.gameController.thePlayer.motionZ += packetIn.func_149147_e();
/*  794 */     if (packetIn.func_149149_c() != 0.0F && packetIn.func_149144_d() != 0.0F && packetIn.func_149147_e() != 0.0F) {
/*  795 */       this.gameController.thePlayer.ticksSinceVelocity = 0;
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void handleOpenWindow(S2DPacketOpenWindow packetIn) {
/*  804 */     PacketThreadUtil.checkThreadAndEnqueue((Packet)packetIn, (INetHandler)this, (IThreadListener)this.gameController);
/*  805 */     EntityPlayerSP entityplayersp = this.gameController.thePlayer;
/*      */     
/*  807 */     if ("minecraft:container".equals(packetIn.getGuiId())) {
/*  808 */       entityplayersp.displayGUIChest((IInventory)new InventoryBasic(packetIn.getWindowTitle(), packetIn.getSlotCount()));
/*  809 */       entityplayersp.openContainer.windowId = packetIn.getWindowId();
/*  810 */     } else if ("minecraft:villager".equals(packetIn.getGuiId())) {
/*  811 */       entityplayersp.displayVillagerTradeGui((IMerchant)new NpcMerchant((EntityPlayer)entityplayersp, packetIn.getWindowTitle()));
/*  812 */       entityplayersp.openContainer.windowId = packetIn.getWindowId();
/*  813 */     } else if ("EntityHorse".equals(packetIn.getGuiId())) {
/*  814 */       Entity entity = this.clientWorldController.getEntityByID(packetIn.getEntityId());
/*      */       
/*  816 */       if (entity instanceof EntityHorse) {
/*  817 */         entityplayersp.displayGUIHorse((EntityHorse)entity, (IInventory)new AnimalChest(packetIn.getWindowTitle(), packetIn.getSlotCount()));
/*  818 */         entityplayersp.openContainer.windowId = packetIn.getWindowId();
/*      */       } 
/*  820 */     } else if (!packetIn.hasSlots()) {
/*  821 */       entityplayersp.displayGui((IInteractionObject)new LocalBlockIntercommunication(packetIn.getGuiId(), packetIn.getWindowTitle()));
/*  822 */       entityplayersp.openContainer.windowId = packetIn.getWindowId();
/*      */     } else {
/*  824 */       ContainerLocalMenu containerlocalmenu = new ContainerLocalMenu(packetIn.getGuiId(), packetIn.getWindowTitle(), packetIn.getSlotCount());
/*  825 */       entityplayersp.displayGUIChest((IInventory)containerlocalmenu);
/*  826 */       entityplayersp.openContainer.windowId = packetIn.getWindowId();
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void handleSetSlot(S2FPacketSetSlot packetIn) {
/*  834 */     PacketThreadUtil.checkThreadAndEnqueue((Packet)packetIn, (INetHandler)this, (IThreadListener)this.gameController);
/*  835 */     EntityPlayerSP entityPlayerSP = this.gameController.thePlayer;
/*      */     
/*  837 */     if (packetIn.func_149175_c() == -1) {
/*  838 */       ((EntityPlayer)entityPlayerSP).inventory.setItemStack(packetIn.func_149174_e());
/*      */     } else {
/*  840 */       boolean flag = false;
/*      */       
/*  842 */       if (this.gameController.currentScreen instanceof GuiContainerCreative) {
/*  843 */         GuiContainerCreative guicontainercreative = (GuiContainerCreative)this.gameController.currentScreen;
/*  844 */         flag = (guicontainercreative.getSelectedTabIndex() != CreativeTabs.tabInventory.getTabIndex());
/*      */       } 
/*      */       
/*  847 */       if (packetIn.func_149175_c() == 0 && packetIn.func_149173_d() >= 36 && packetIn.func_149173_d() < 45) {
/*  848 */         ItemStack itemstack = ((EntityPlayer)entityPlayerSP).inventoryContainer.getSlot(packetIn.func_149173_d()).getStack();
/*      */         
/*  850 */         if (packetIn.func_149174_e() != null && (itemstack == null || itemstack.stackSize < (packetIn.func_149174_e()).stackSize)) {
/*  851 */           (packetIn.func_149174_e()).animationsToGo = 5;
/*      */         }
/*      */         
/*  854 */         ((EntityPlayer)entityPlayerSP).inventoryContainer.putStackInSlot(packetIn.func_149173_d(), packetIn.func_149174_e());
/*  855 */       } else if (packetIn.func_149175_c() == ((EntityPlayer)entityPlayerSP).openContainer.windowId && (packetIn.func_149175_c() != 0 || !flag)) {
/*  856 */         ((EntityPlayer)entityPlayerSP).openContainer.putStackInSlot(packetIn.func_149173_d(), packetIn.func_149174_e());
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void handleConfirmTransaction(S32PacketConfirmTransaction packetIn) {
/*  866 */     PacketThreadUtil.checkThreadAndEnqueue((Packet)packetIn, (INetHandler)this, (IThreadListener)this.gameController);
/*  867 */     Container container = null;
/*  868 */     EntityPlayerSP entityPlayerSP = this.gameController.thePlayer;
/*      */     
/*  870 */     if (packetIn.getWindowId() == 0) {
/*  871 */       container = ((EntityPlayer)entityPlayerSP).inventoryContainer;
/*  872 */     } else if (packetIn.getWindowId() == ((EntityPlayer)entityPlayerSP).openContainer.windowId) {
/*  873 */       container = ((EntityPlayer)entityPlayerSP).openContainer;
/*      */     } 
/*      */     
/*  876 */     if (container != null && !packetIn.func_148888_e()) {
/*  877 */       addToSendQueue((Packet)new C0FPacketConfirmTransaction(packetIn.getWindowId(), packetIn.getActionNumber(), true));
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void handleWindowItems(S30PacketWindowItems packetIn) {
/*  885 */     PacketThreadUtil.checkThreadAndEnqueue((Packet)packetIn, (INetHandler)this, (IThreadListener)this.gameController);
/*  886 */     EntityPlayerSP entityPlayerSP = this.gameController.thePlayer;
/*      */     
/*  888 */     if (packetIn.func_148911_c() == 0) {
/*  889 */       ((EntityPlayer)entityPlayerSP).inventoryContainer.putStacksInSlots(packetIn.getItemStacks());
/*  890 */     } else if (packetIn.func_148911_c() == ((EntityPlayer)entityPlayerSP).openContainer.windowId) {
/*  891 */       ((EntityPlayer)entityPlayerSP).openContainer.putStacksInSlots(packetIn.getItemStacks());
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public void handleSignEditorOpen(S36PacketSignEditorOpen packetIn) {
/*      */     TileEntitySign tileEntitySign;
/*  899 */     PacketThreadUtil.checkThreadAndEnqueue((Packet)packetIn, (INetHandler)this, (IThreadListener)this.gameController);
/*  900 */     TileEntity tileentity = this.clientWorldController.getTileEntity(packetIn.getSignPosition());
/*      */     
/*  902 */     if (!(tileentity instanceof TileEntitySign)) {
/*  903 */       tileEntitySign = new TileEntitySign();
/*  904 */       tileEntitySign.setWorldObj((World)this.clientWorldController);
/*  905 */       tileEntitySign.setPos(packetIn.getSignPosition());
/*      */     } 
/*      */     
/*  908 */     this.gameController.thePlayer.openEditSign(tileEntitySign);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void handleUpdateSign(S33PacketUpdateSign packetIn) {
/*  915 */     PacketThreadUtil.checkThreadAndEnqueue((Packet)packetIn, (INetHandler)this, (IThreadListener)this.gameController);
/*  916 */     boolean flag = false;
/*      */     
/*  918 */     if (this.gameController.theWorld.isBlockLoaded(packetIn.getPos())) {
/*  919 */       TileEntity tileentity = this.gameController.theWorld.getTileEntity(packetIn.getPos());
/*      */       
/*  921 */       if (tileentity instanceof TileEntitySign) {
/*  922 */         TileEntitySign tileentitysign = (TileEntitySign)tileentity;
/*      */         
/*  924 */         if (tileentitysign.getIsEditable()) {
/*  925 */           System.arraycopy(packetIn.getLines(), 0, tileentitysign.signText, 0, 4);
/*  926 */           tileentitysign.markDirty();
/*      */         } 
/*      */         
/*  929 */         flag = true;
/*      */       } 
/*      */     } 
/*      */     
/*  933 */     if (!flag && this.gameController.thePlayer != null) {
/*  934 */       this.gameController.thePlayer.addChatMessage((IChatComponent)new ChatComponentText("Unable to locate sign at " + packetIn.getPos().getX() + ", " + packetIn.getPos().getY() + ", " + packetIn.getPos().getZ()));
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void handleUpdateTileEntity(S35PacketUpdateTileEntity packetIn) {
/*  943 */     PacketThreadUtil.checkThreadAndEnqueue((Packet)packetIn, (INetHandler)this, (IThreadListener)this.gameController);
/*      */     
/*  945 */     if (this.gameController.theWorld.isBlockLoaded(packetIn.getPos())) {
/*  946 */       TileEntity tileentity = this.gameController.theWorld.getTileEntity(packetIn.getPos());
/*  947 */       int i = packetIn.getTileEntityType();
/*      */       
/*  949 */       if ((i == 1 && tileentity instanceof net.minecraft.tileentity.TileEntityMobSpawner) || (i == 2 && tileentity instanceof net.minecraft.tileentity.TileEntityCommandBlock) || (i == 3 && tileentity instanceof net.minecraft.tileentity.TileEntityBeacon) || (i == 4 && tileentity instanceof net.minecraft.tileentity.TileEntitySkull) || (i == 5 && tileentity instanceof net.minecraft.tileentity.TileEntityFlowerPot) || (i == 6 && tileentity instanceof net.minecraft.tileentity.TileEntityBanner)) {
/*  950 */         tileentity.readFromNBT(packetIn.getNbtCompound());
/*      */       }
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void handleWindowProperty(S31PacketWindowProperty packetIn) {
/*  959 */     PacketThreadUtil.checkThreadAndEnqueue((Packet)packetIn, (INetHandler)this, (IThreadListener)this.gameController);
/*  960 */     EntityPlayerSP entityPlayerSP = this.gameController.thePlayer;
/*      */     
/*  962 */     if (((EntityPlayer)entityPlayerSP).openContainer != null && ((EntityPlayer)entityPlayerSP).openContainer.windowId == packetIn.getWindowId()) {
/*  963 */       ((EntityPlayer)entityPlayerSP).openContainer.updateProgressBar(packetIn.getVarIndex(), packetIn.getVarValue());
/*      */     }
/*      */   }
/*      */   
/*      */   public void handleEntityEquipment(S04PacketEntityEquipment packetIn) {
/*  968 */     PacketThreadUtil.checkThreadAndEnqueue((Packet)packetIn, (INetHandler)this, (IThreadListener)this.gameController);
/*  969 */     Entity entity = this.clientWorldController.getEntityByID(packetIn.getEntityID());
/*      */     
/*  971 */     if (entity != null) {
/*  972 */       entity.setCurrentItemOrArmor(packetIn.getEquipmentSlot(), packetIn.getItemStack());
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void handleCloseWindow(S2EPacketCloseWindow packetIn) {
/*  980 */     PacketThreadUtil.checkThreadAndEnqueue((Packet)packetIn, (INetHandler)this, (IThreadListener)this.gameController);
/*  981 */     this.gameController.thePlayer.closeScreenAndDropStack();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void handleBlockAction(S24PacketBlockAction packetIn) {
/*  990 */     PacketThreadUtil.checkThreadAndEnqueue((Packet)packetIn, (INetHandler)this, (IThreadListener)this.gameController);
/*  991 */     this.gameController.theWorld.addBlockEvent(packetIn.getBlockPosition(), packetIn.getBlockType(), packetIn.getData1(), packetIn.getData2());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void handleBlockBreakAnim(S25PacketBlockBreakAnim packetIn) {
/*  998 */     PacketThreadUtil.checkThreadAndEnqueue((Packet)packetIn, (INetHandler)this, (IThreadListener)this.gameController);
/*  999 */     this.gameController.theWorld.sendBlockBreakProgress(packetIn.getBreakerId(), packetIn.getPosition(), packetIn.getProgress());
/*      */   }
/*      */   
/*      */   public void handleMapChunkBulk(S26PacketMapChunkBulk packetIn) {
/* 1003 */     PacketThreadUtil.checkThreadAndEnqueue((Packet)packetIn, (INetHandler)this, (IThreadListener)this.gameController);
/*      */     
/* 1005 */     for (int i = 0; i < packetIn.getChunkCount(); i++) {
/* 1006 */       int j = packetIn.getChunkX(i);
/* 1007 */       int k = packetIn.getChunkZ(i);
/* 1008 */       this.clientWorldController.doPreChunk(j, k, true);
/* 1009 */       this.clientWorldController.invalidateBlockReceiveRegion(j << 4, 0, k << 4, (j << 4) + 15, 256, (k << 4) + 15);
/* 1010 */       Chunk chunk = this.clientWorldController.getChunkFromChunkCoords(j, k);
/* 1011 */       chunk.fillChunk(packetIn.getChunkBytes(i), packetIn.getChunkSize(i), true);
/* 1012 */       this.clientWorldController.markBlockRangeForRenderUpdate(j << 4, 0, k << 4, (j << 4) + 15, 256, (k << 4) + 15);
/*      */       
/* 1014 */       if (!(this.clientWorldController.provider instanceof net.minecraft.world.WorldProviderSurface)) {
/* 1015 */         chunk.resetRelightChecks();
/*      */       }
/*      */     } 
/*      */   }
/*      */   
/*      */   public void handleChangeGameState(S2BPacketChangeGameState packetIn) {
/* 1021 */     PacketThreadUtil.checkThreadAndEnqueue((Packet)packetIn, (INetHandler)this, (IThreadListener)this.gameController);
/* 1022 */     EntityPlayerSP entityPlayerSP = this.gameController.thePlayer;
/* 1023 */     int i = packetIn.getGameState();
/* 1024 */     float f = packetIn.func_149137_d();
/* 1025 */     int j = MathHelper.floor_float(f + 0.5F);
/*      */     
/* 1027 */     if (i >= 0 && i < S2BPacketChangeGameState.MESSAGE_NAMES.length && S2BPacketChangeGameState.MESSAGE_NAMES[i] != null) {
/* 1028 */       entityPlayerSP.addChatComponentMessage((IChatComponent)new ChatComponentTranslation(S2BPacketChangeGameState.MESSAGE_NAMES[i], new Object[0]));
/*      */     }
/*      */     
/* 1031 */     if (i == 1) {
/* 1032 */       this.clientWorldController.getWorldInfo().setRaining(true);
/* 1033 */       this.clientWorldController.setRainStrength(0.0F);
/* 1034 */     } else if (i == 2) {
/* 1035 */       this.clientWorldController.getWorldInfo().setRaining(false);
/* 1036 */       this.clientWorldController.setRainStrength(1.0F);
/* 1037 */     } else if (i == 3) {
/* 1038 */       this.gameController.playerController.setGameType(WorldSettings.GameType.getByID(j));
/* 1039 */     } else if (i == 4) {
/* 1040 */       this.gameController.displayGuiScreen((GuiScreen)new GuiWinGame());
/* 1041 */     } else if (i == 5) {
/* 1042 */       GameSettings gamesettings = this.gameController.gameSettings;
/*      */       
/* 1044 */       if (f == 0.0F) {
/* 1045 */         this.gameController.displayGuiScreen((GuiScreen)new GuiScreenDemo());
/* 1046 */       } else if (f == 101.0F) {
/* 1047 */         this.gameController.ingameGUI.getChatGUI().printChatMessage((IChatComponent)new ChatComponentTranslation("demo.help.movement", new Object[] { GameSettings.getKeyDisplayString(gamesettings.keyBindForward.getKeyCode()), GameSettings.getKeyDisplayString(gamesettings.keyBindLeft.getKeyCode()), GameSettings.getKeyDisplayString(gamesettings.keyBindBack.getKeyCode()), GameSettings.getKeyDisplayString(gamesettings.keyBindRight.getKeyCode()) }));
/* 1048 */       } else if (f == 102.0F) {
/* 1049 */         this.gameController.ingameGUI.getChatGUI().printChatMessage((IChatComponent)new ChatComponentTranslation("demo.help.jump", new Object[] { GameSettings.getKeyDisplayString(gamesettings.keyBindJump.getKeyCode()) }));
/* 1050 */       } else if (f == 103.0F) {
/* 1051 */         this.gameController.ingameGUI.getChatGUI().printChatMessage((IChatComponent)new ChatComponentTranslation("demo.help.inventory", new Object[] { GameSettings.getKeyDisplayString(gamesettings.keyBindInventory.getKeyCode()) }));
/*      */       } 
/* 1053 */     } else if (i == 6) {
/* 1054 */       this.clientWorldController.playSound(((EntityPlayer)entityPlayerSP).posX, ((EntityPlayer)entityPlayerSP).posY + entityPlayerSP.getEyeHeight(), ((EntityPlayer)entityPlayerSP).posZ, "random.successful_hit", 0.18F, 0.45F, false);
/* 1055 */     } else if (i == 7) {
/* 1056 */       this.clientWorldController.setRainStrength(f);
/* 1057 */     } else if (i == 8) {
/* 1058 */       this.clientWorldController.setThunderStrength(f);
/* 1059 */     } else if (i == 10) {
/* 1060 */       this.clientWorldController.spawnParticle(EnumParticleTypes.MOB_APPEARANCE, ((EntityPlayer)entityPlayerSP).posX, ((EntityPlayer)entityPlayerSP).posY, ((EntityPlayer)entityPlayerSP).posZ, 0.0D, 0.0D, 0.0D, new int[0]);
/* 1061 */       this.clientWorldController.playSound(((EntityPlayer)entityPlayerSP).posX, ((EntityPlayer)entityPlayerSP).posY, ((EntityPlayer)entityPlayerSP).posZ, "mob.guardian.curse", 1.0F, 1.0F, false);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void handleMaps(S34PacketMaps packetIn) {
/* 1070 */     PacketThreadUtil.checkThreadAndEnqueue((Packet)packetIn, (INetHandler)this, (IThreadListener)this.gameController);
/* 1071 */     MapData mapdata = ItemMap.loadMapData(packetIn.getMapId(), (World)this.gameController.theWorld);
/* 1072 */     packetIn.setMapdataTo(mapdata);
/* 1073 */     this.gameController.entityRenderer.getMapItemRenderer().updateMapTexture(mapdata);
/*      */   }
/*      */   
/*      */   public void handleEffect(S28PacketEffect packetIn) {
/* 1077 */     PacketThreadUtil.checkThreadAndEnqueue((Packet)packetIn, (INetHandler)this, (IThreadListener)this.gameController);
/*      */     
/* 1079 */     if (packetIn.isSoundServerwide()) {
/* 1080 */       this.gameController.theWorld.playBroadcastSound(packetIn.getSoundType(), packetIn.getSoundPos(), packetIn.getSoundData());
/*      */     } else {
/* 1082 */       this.gameController.theWorld.playAuxSFX(packetIn.getSoundType(), packetIn.getSoundPos(), packetIn.getSoundData());
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void handleStatistics(S37PacketStatistics packetIn) {
/* 1090 */     PacketThreadUtil.checkThreadAndEnqueue((Packet)packetIn, (INetHandler)this, (IThreadListener)this.gameController);
/* 1091 */     boolean flag = false;
/*      */     
/* 1093 */     for (Map.Entry<StatBase, Integer> entry : (Iterable<Map.Entry<StatBase, Integer>>)packetIn.func_148974_c().entrySet()) {
/* 1094 */       StatBase statbase = entry.getKey();
/* 1095 */       int i = ((Integer)entry.getValue()).intValue();
/*      */       
/* 1097 */       if (statbase.isAchievement() && i > 0) {
/* 1098 */         if (this.field_147308_k && this.gameController.thePlayer.getStatFileWriter().readStat(statbase) == 0) {
/* 1099 */           Achievement achievement = (Achievement)statbase;
/* 1100 */           this.gameController.guiAchievement.displayAchievement(achievement);
/*      */           
/* 1102 */           if (statbase == AchievementList.openInventory) {
/* 1103 */             this.gameController.gameSettings.showInventoryAchievementHint = false;
/* 1104 */             this.gameController.gameSettings.saveOptions();
/*      */           } 
/*      */         } 
/*      */         
/* 1108 */         flag = true;
/*      */       } 
/*      */       
/* 1111 */       this.gameController.thePlayer.getStatFileWriter().unlockAchievement((EntityPlayer)this.gameController.thePlayer, statbase, i);
/*      */     } 
/*      */     
/* 1114 */     if (!this.field_147308_k && !flag && this.gameController.gameSettings.showInventoryAchievementHint) {
/* 1115 */       this.gameController.guiAchievement.displayUnformattedAchievement(AchievementList.openInventory);
/*      */     }
/*      */     
/* 1118 */     this.field_147308_k = true;
/*      */     
/* 1120 */     if (this.gameController.currentScreen instanceof IProgressMeter) {
/* 1121 */       ((IProgressMeter)this.gameController.currentScreen).doneLoading();
/*      */     }
/*      */   }
/*      */   
/*      */   public void handleEntityEffect(S1DPacketEntityEffect packetIn) {
/* 1126 */     PacketThreadUtil.checkThreadAndEnqueue((Packet)packetIn, (INetHandler)this, (IThreadListener)this.gameController);
/* 1127 */     Entity entity = this.clientWorldController.getEntityByID(packetIn.getEntityId());
/*      */     
/* 1129 */     if (entity instanceof EntityLivingBase) {
/* 1130 */       PotionEffect potioneffect = new PotionEffect(packetIn.getEffectId(), packetIn.getDuration(), packetIn.getAmplifier(), false, packetIn.func_179707_f());
/* 1131 */       potioneffect.setPotionDurationMax(packetIn.func_149429_c());
/* 1132 */       ((EntityLivingBase)entity).addPotionEffect(potioneffect);
/*      */     } 
/*      */   }
/*      */   
/*      */   public void handleCombatEvent(S42PacketCombatEvent packetIn) {
/* 1137 */     PacketThreadUtil.checkThreadAndEnqueue((Packet)packetIn, (INetHandler)this, (IThreadListener)this.gameController);
/* 1138 */     Entity entity = this.clientWorldController.getEntityByID(packetIn.field_179775_c);
/* 1139 */     EntityLivingBase entitylivingbase = (entity instanceof EntityLivingBase) ? (EntityLivingBase)entity : null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void handleServerDifficulty(S41PacketServerDifficulty packetIn) {
/* 1150 */     PacketThreadUtil.checkThreadAndEnqueue((Packet)packetIn, (INetHandler)this, (IThreadListener)this.gameController);
/* 1151 */     this.gameController.theWorld.getWorldInfo().setDifficulty(packetIn.getDifficulty());
/* 1152 */     this.gameController.theWorld.getWorldInfo().setDifficultyLocked(packetIn.isDifficultyLocked());
/*      */   }
/*      */   
/*      */   public void handleCamera(S43PacketCamera packetIn) {
/* 1156 */     PacketThreadUtil.checkThreadAndEnqueue((Packet)packetIn, (INetHandler)this, (IThreadListener)this.gameController);
/* 1157 */     Entity entity = packetIn.getEntity((World)this.clientWorldController);
/*      */     
/* 1159 */     if (entity != null) {
/* 1160 */       this.gameController.setRenderViewEntity(entity);
/*      */     }
/*      */   }
/*      */   
/*      */   public void handleWorldBorder(S44PacketWorldBorder packetIn) {
/* 1165 */     PacketThreadUtil.checkThreadAndEnqueue((Packet)packetIn, (INetHandler)this, (IThreadListener)this.gameController);
/* 1166 */     packetIn.func_179788_a(this.clientWorldController.getWorldBorder());
/*      */   }
/*      */ 
/*      */   
/*      */   public void handleTitle(S45PacketTitle packetIn) {
/* 1171 */     PacketThreadUtil.checkThreadAndEnqueue((Packet)packetIn, (INetHandler)this, (IThreadListener)this.gameController);
/* 1172 */     S45PacketTitle.Type s45packettitle$type = packetIn.getType();
/* 1173 */     String s = null;
/* 1174 */     String s1 = null;
/* 1175 */     String s2 = (packetIn.getMessage() != null) ? packetIn.getMessage().getFormattedText() : "";
/*      */     
/* 1177 */     switch (s45packettitle$type) {
/*      */       case ADD_PLAYER:
/* 1179 */         s = s2;
/*      */         break;
/*      */       
/*      */       case UPDATE_GAME_MODE:
/* 1183 */         s1 = s2;
/*      */         break;
/*      */       
/*      */       case UPDATE_LATENCY:
/* 1187 */         this.gameController.ingameGUI.displayTitle("", "", -1, -1, -1);
/* 1188 */         this.gameController.ingameGUI.setDefaultTitlesTimes();
/*      */         return;
/*      */     } 
/*      */     
/* 1192 */     this.gameController.ingameGUI.displayTitle(s, s1, packetIn.getFadeInTime(), packetIn.getDisplayTime(), packetIn.getFadeOutTime());
/*      */   }
/*      */   
/*      */   public void handleSetCompressionLevel(S46PacketSetCompressionLevel packetIn) {
/* 1196 */     if (!this.netManager.isLocalChannel()) {
/* 1197 */       this.netManager.setCompressionTreshold(packetIn.getThreshold());
/*      */     }
/*      */   }
/*      */   
/*      */   public void handlePlayerListHeaderFooter(S47PacketPlayerListHeaderFooter packetIn) {
/* 1202 */     this.gameController.ingameGUI.getTabList().setHeader(packetIn.getHeader().getFormattedText().isEmpty() ? null : packetIn.getHeader());
/* 1203 */     this.gameController.ingameGUI.getTabList().setFooter(packetIn.getFooter().getFormattedText().isEmpty() ? null : packetIn.getFooter());
/*      */   }
/*      */   
/*      */   public void handleRemoveEntityEffect(S1EPacketRemoveEntityEffect packetIn) {
/* 1207 */     PacketThreadUtil.checkThreadAndEnqueue((Packet)packetIn, (INetHandler)this, (IThreadListener)this.gameController);
/* 1208 */     Entity entity = this.clientWorldController.getEntityByID(packetIn.getEntityId());
/*      */     
/* 1210 */     if (entity instanceof EntityLivingBase) {
/* 1211 */       ((EntityLivingBase)entity).removePotionEffectClient(packetIn.getEffectId());
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   public void handlePlayerListItem(S38PacketPlayerListItem packetIn) {
/* 1217 */     PacketThreadUtil.checkThreadAndEnqueue((Packet)packetIn, (INetHandler)this, (IThreadListener)this.gameController);
/*      */     
/* 1219 */     for (S38PacketPlayerListItem.AddPlayerData s38packetplayerlistitem$addplayerdata : packetIn.getEntries()) {
/* 1220 */       if (packetIn.getAction() == S38PacketPlayerListItem.Action.REMOVE_PLAYER) {
/* 1221 */         this.playerInfoMap.remove(s38packetplayerlistitem$addplayerdata.getProfile().getId()); continue;
/*      */       } 
/* 1223 */       NetworkPlayerInfo networkplayerinfo = this.playerInfoMap.get(s38packetplayerlistitem$addplayerdata.getProfile().getId());
/*      */       
/* 1225 */       if (packetIn.getAction() == S38PacketPlayerListItem.Action.ADD_PLAYER) {
/* 1226 */         networkplayerinfo = new NetworkPlayerInfo(s38packetplayerlistitem$addplayerdata);
/* 1227 */         this.playerInfoMap.put(networkplayerinfo.getGameProfile().getId(), networkplayerinfo);
/*      */       } 
/*      */       
/* 1230 */       if (networkplayerinfo != null) {
/* 1231 */         switch (packetIn.getAction()) {
/*      */           case ADD_PLAYER:
/* 1233 */             networkplayerinfo.setGameType(s38packetplayerlistitem$addplayerdata.getGameMode());
/* 1234 */             networkplayerinfo.setResponseTime(s38packetplayerlistitem$addplayerdata.getPing());
/*      */ 
/*      */           
/*      */           case UPDATE_GAME_MODE:
/* 1238 */             networkplayerinfo.setGameType(s38packetplayerlistitem$addplayerdata.getGameMode());
/*      */ 
/*      */           
/*      */           case UPDATE_LATENCY:
/* 1242 */             networkplayerinfo.setResponseTime(s38packetplayerlistitem$addplayerdata.getPing());
/*      */ 
/*      */           
/*      */           case UPDATE_DISPLAY_NAME:
/* 1246 */             networkplayerinfo.setDisplayName(s38packetplayerlistitem$addplayerdata.getDisplayName());
/*      */         } 
/*      */       }
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public void handleKeepAlive(S00PacketKeepAlive packetIn) {
/* 1254 */     addToSendQueue((Packet)new C00PacketKeepAlive(packetIn.func_149134_c()));
/*      */   }
/*      */   
/*      */   public void handlePlayerAbilities(S39PacketPlayerAbilities packetIn) {
/* 1258 */     PacketThreadUtil.checkThreadAndEnqueue((Packet)packetIn, (INetHandler)this, (IThreadListener)this.gameController);
/* 1259 */     EntityPlayerSP entityPlayerSP = this.gameController.thePlayer;
/* 1260 */     ((EntityPlayer)entityPlayerSP).capabilities.isFlying = packetIn.isFlying();
/* 1261 */     ((EntityPlayer)entityPlayerSP).capabilities.isCreativeMode = packetIn.isCreativeMode();
/* 1262 */     ((EntityPlayer)entityPlayerSP).capabilities.disableDamage = packetIn.isInvulnerable();
/* 1263 */     ((EntityPlayer)entityPlayerSP).capabilities.allowFlying = packetIn.isAllowFlying();
/* 1264 */     ((EntityPlayer)entityPlayerSP).capabilities.setFlySpeed(packetIn.getFlySpeed());
/* 1265 */     ((EntityPlayer)entityPlayerSP).capabilities.setPlayerWalkSpeed(packetIn.getWalkSpeed());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void handleTabComplete(S3APacketTabComplete packetIn) {
/* 1272 */     PacketThreadUtil.checkThreadAndEnqueue((Packet)packetIn, (INetHandler)this, (IThreadListener)this.gameController);
/* 1273 */     String[] astring = packetIn.func_149630_c();
/*      */     
/* 1275 */     if (this.gameController.currentScreen instanceof GuiChat) {
/* 1276 */       GuiChat guichat = (GuiChat)this.gameController.currentScreen;
/* 1277 */       guichat.onAutocompleteResponse(astring);
/*      */     } 
/*      */   }
/*      */   
/*      */   public void handleSoundEffect(S29PacketSoundEffect packetIn) {
/* 1282 */     PacketThreadUtil.checkThreadAndEnqueue((Packet)packetIn, (INetHandler)this, (IThreadListener)this.gameController);
/* 1283 */     this.gameController.theWorld.playSound(packetIn.getX(), packetIn.getY(), packetIn.getZ(), packetIn.getSoundName(), packetIn.getVolume(), packetIn.getPitch(), false);
/*      */   }
/*      */   
/*      */   public void handleResourcePack(S48PacketResourcePackSend packetIn) {
/* 1287 */     final String s = packetIn.getURL();
/* 1288 */     final String s1 = packetIn.getHash();
/*      */     
/* 1290 */     if (s.startsWith("level://")) {
/* 1291 */       String s2 = s.substring("level://".length());
/* 1292 */       File file1 = new File(this.gameController.mcDataDir, "saves");
/* 1293 */       File file2 = new File(file1, s2);
/*      */       
/* 1295 */       if (file2.isFile()) {
/* 1296 */         this.netManager.sendPacket((Packet)new C19PacketResourcePackStatus(s1, C19PacketResourcePackStatus.Action.ACCEPTED));
/* 1297 */         Futures.addCallback(this.gameController.getResourcePackRepository().setResourcePackInstance(file2), new FutureCallback<Object>() {
/*      */               public void onSuccess(Object p_onSuccess_1_) {
/* 1299 */                 NetHandlerPlayClient.this.netManager.sendPacket((Packet)new C19PacketResourcePackStatus(s1, C19PacketResourcePackStatus.Action.SUCCESSFULLY_LOADED));
/*      */               }
/*      */               
/*      */               public void onFailure(Throwable p_onFailure_1_) {
/* 1303 */                 NetHandlerPlayClient.this.netManager.sendPacket((Packet)new C19PacketResourcePackStatus(s1, C19PacketResourcePackStatus.Action.FAILED_DOWNLOAD));
/*      */               }
/*      */             });
/*      */       } else {
/* 1307 */         this.netManager.sendPacket((Packet)new C19PacketResourcePackStatus(s1, C19PacketResourcePackStatus.Action.FAILED_DOWNLOAD));
/*      */       }
/*      */     
/* 1310 */     } else if (this.gameController.getCurrentServerData() != null && this.gameController.getCurrentServerData().getResourceMode() == ServerData.ServerResourceMode.ENABLED) {
/* 1311 */       this.netManager.sendPacket((Packet)new C19PacketResourcePackStatus(s1, C19PacketResourcePackStatus.Action.ACCEPTED));
/* 1312 */       Futures.addCallback(this.gameController.getResourcePackRepository().downloadResourcePack(s, s1), new FutureCallback<Object>() {
/*      */             public void onSuccess(Object p_onSuccess_1_) {
/* 1314 */               NetHandlerPlayClient.this.netManager.sendPacket((Packet)new C19PacketResourcePackStatus(s1, C19PacketResourcePackStatus.Action.SUCCESSFULLY_LOADED));
/*      */             }
/*      */             
/*      */             public void onFailure(Throwable p_onFailure_1_) {
/* 1318 */               NetHandlerPlayClient.this.netManager.sendPacket((Packet)new C19PacketResourcePackStatus(s1, C19PacketResourcePackStatus.Action.FAILED_DOWNLOAD));
/*      */             }
/*      */           });
/* 1321 */     } else if (this.gameController.getCurrentServerData() != null && this.gameController.getCurrentServerData().getResourceMode() != ServerData.ServerResourceMode.PROMPT) {
/* 1322 */       this.netManager.sendPacket((Packet)new C19PacketResourcePackStatus(s1, C19PacketResourcePackStatus.Action.DECLINED));
/*      */     } else {
/* 1324 */       this.gameController.addScheduledTask(new Runnable() {
/*      */             public void run() {
/* 1326 */               NetHandlerPlayClient.this.gameController.displayGuiScreen((GuiScreen)new GuiYesNo(new GuiYesNoCallback() {
/*      */                       public void confirmClicked(boolean result, int id) {
/* 1328 */                         NetHandlerPlayClient.this.gameController = Minecraft.getMinecraft();
/*      */                         
/* 1330 */                         if (result) {
/* 1331 */                           if (NetHandlerPlayClient.this.gameController.getCurrentServerData() != null) {
/* 1332 */                             NetHandlerPlayClient.this.gameController.getCurrentServerData().setResourceMode(ServerData.ServerResourceMode.ENABLED);
/*      */                           }
/*      */                           
/* 1335 */                           NetHandlerPlayClient.this.netManager.sendPacket((Packet)new C19PacketResourcePackStatus(s1, C19PacketResourcePackStatus.Action.ACCEPTED));
/* 1336 */                           Futures.addCallback(NetHandlerPlayClient.this.gameController.getResourcePackRepository().downloadResourcePack(s, s1), new FutureCallback<Object>() {
/*      */                                 public void onSuccess(Object p_onSuccess_1_) {
/* 1338 */                                   NetHandlerPlayClient.this.netManager.sendPacket((Packet)new C19PacketResourcePackStatus(s1, C19PacketResourcePackStatus.Action.SUCCESSFULLY_LOADED));
/*      */                                 }
/*      */                                 
/*      */                                 public void onFailure(Throwable p_onFailure_1_) {
/* 1342 */                                   NetHandlerPlayClient.this.netManager.sendPacket((Packet)new C19PacketResourcePackStatus(s1, C19PacketResourcePackStatus.Action.FAILED_DOWNLOAD));
/*      */                                 }
/*      */                               });
/*      */                         } else {
/* 1346 */                           if (NetHandlerPlayClient.this.gameController.getCurrentServerData() != null) {
/* 1347 */                             NetHandlerPlayClient.this.gameController.getCurrentServerData().setResourceMode(ServerData.ServerResourceMode.DISABLED);
/*      */                           }
/*      */                           
/* 1350 */                           NetHandlerPlayClient.this.netManager.sendPacket((Packet)new C19PacketResourcePackStatus(s1, C19PacketResourcePackStatus.Action.DECLINED));
/*      */                         } 
/*      */                         
/* 1353 */                         ServerList.func_147414_b(NetHandlerPlayClient.this.gameController.getCurrentServerData());
/* 1354 */                         NetHandlerPlayClient.this.gameController.displayGuiScreen((GuiScreen)null);
/*      */                       }
/* 1356 */                     }I18n.format("multiplayer.texturePrompt.line1", new Object[0]), I18n.format("multiplayer.texturePrompt.line2", new Object[0]), 0));
/*      */             }
/*      */           });
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public void handleEntityNBT(S49PacketUpdateEntityNBT packetIn) {
/* 1364 */     PacketThreadUtil.checkThreadAndEnqueue((Packet)packetIn, (INetHandler)this, (IThreadListener)this.gameController);
/* 1365 */     Entity entity = packetIn.getEntity((World)this.clientWorldController);
/*      */     
/* 1367 */     if (entity != null) {
/* 1368 */       entity.clientUpdateEntityNBT(packetIn.getTagCompound());
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void handleCustomPayload(S3FPacketCustomPayload packetIn) {
/* 1379 */     PacketThreadUtil.checkThreadAndEnqueue((Packet)packetIn, (INetHandler)this, (IThreadListener)this.gameController);
/*      */     
/* 1381 */     if ("MC|TrList".equals(packetIn.getChannelName())) {
/* 1382 */       PacketBuffer packetbuffer = packetIn.getBufferData();
/*      */       
/*      */       try {
/* 1385 */         int i = packetbuffer.readInt();
/* 1386 */         GuiScreen guiscreen = this.gameController.currentScreen;
/*      */         
/* 1388 */         if (guiscreen != null && guiscreen instanceof GuiMerchant && i == this.gameController.thePlayer.openContainer.windowId) {
/* 1389 */           IMerchant imerchant = ((GuiMerchant)guiscreen).getMerchant();
/* 1390 */           MerchantRecipeList merchantrecipelist = MerchantRecipeList.readFromBuf(packetbuffer);
/* 1391 */           imerchant.setRecipes(merchantrecipelist);
/*      */         } 
/* 1393 */       } catch (IOException ioexception) {
/* 1394 */         logger.error("Couldn't load trade info", ioexception);
/*      */       } finally {
/* 1396 */         packetbuffer.release();
/*      */       } 
/* 1398 */     } else if ("MC|Brand".equals(packetIn.getChannelName())) {
/* 1399 */       this.gameController.thePlayer.setClientBrand(packetIn.getBufferData().readStringFromBuffer(32767));
/* 1400 */     } else if ("MC|BOpen".equals(packetIn.getChannelName())) {
/* 1401 */       ItemStack itemstack = this.gameController.thePlayer.getCurrentEquippedItem();
/*      */       
/* 1403 */       if (itemstack != null && itemstack.getItem() == Items.written_book) {
/* 1404 */         this.gameController.displayGuiScreen((GuiScreen)new GuiScreenBook((EntityPlayer)this.gameController.thePlayer, itemstack, false));
/*      */       }
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void handleScoreboardObjective(S3BPacketScoreboardObjective packetIn) {
/* 1413 */     PacketThreadUtil.checkThreadAndEnqueue((Packet)packetIn, (INetHandler)this, (IThreadListener)this.gameController);
/* 1414 */     Scoreboard scoreboard = this.clientWorldController.getScoreboard();
/*      */     
/* 1416 */     if (packetIn.func_149338_e() == 0) {
/* 1417 */       ScoreObjective scoreobjective = scoreboard.addScoreObjective(packetIn.func_149339_c(), IScoreObjectiveCriteria.DUMMY);
/* 1418 */       scoreobjective.setDisplayName(packetIn.func_149337_d());
/* 1419 */       scoreobjective.setRenderType(packetIn.func_179817_d());
/*      */     } else {
/* 1421 */       ScoreObjective scoreobjective1 = scoreboard.getObjective(packetIn.func_149339_c());
/*      */       
/* 1423 */       if (packetIn.func_149338_e() == 1) {
/* 1424 */         scoreboard.removeObjective(scoreobjective1);
/* 1425 */       } else if (packetIn.func_149338_e() == 2) {
/* 1426 */         scoreobjective1.setDisplayName(packetIn.func_149337_d());
/* 1427 */         scoreobjective1.setRenderType(packetIn.func_179817_d());
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void handleUpdateScore(S3CPacketUpdateScore packetIn) {
/* 1436 */     PacketThreadUtil.checkThreadAndEnqueue((Packet)packetIn, (INetHandler)this, (IThreadListener)this.gameController);
/* 1437 */     Scoreboard scoreboard = this.clientWorldController.getScoreboard();
/* 1438 */     ScoreObjective scoreobjective = scoreboard.getObjective(packetIn.getObjectiveName());
/*      */     
/* 1440 */     if (packetIn.getScoreAction() == S3CPacketUpdateScore.Action.CHANGE) {
/* 1441 */       Score score = scoreboard.getValueFromObjective(packetIn.getPlayerName(), scoreobjective);
/* 1442 */       score.setScorePoints(packetIn.getScoreValue());
/* 1443 */     } else if (packetIn.getScoreAction() == S3CPacketUpdateScore.Action.REMOVE) {
/* 1444 */       if (StringUtils.isNullOrEmpty(packetIn.getObjectiveName())) {
/* 1445 */         scoreboard.removeObjectiveFromEntity(packetIn.getPlayerName(), (ScoreObjective)null);
/* 1446 */       } else if (scoreobjective != null) {
/* 1447 */         scoreboard.removeObjectiveFromEntity(packetIn.getPlayerName(), scoreobjective);
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void handleDisplayScoreboard(S3DPacketDisplayScoreboard packetIn) {
/* 1457 */     PacketThreadUtil.checkThreadAndEnqueue((Packet)packetIn, (INetHandler)this, (IThreadListener)this.gameController);
/* 1458 */     Scoreboard scoreboard = this.clientWorldController.getScoreboard();
/*      */     
/* 1460 */     if (packetIn.func_149370_d().isEmpty()) {
/* 1461 */       scoreboard.setObjectiveInDisplaySlot(packetIn.func_149371_c(), (ScoreObjective)null);
/*      */     } else {
/* 1463 */       ScoreObjective scoreobjective = scoreboard.getObjective(packetIn.func_149370_d());
/* 1464 */       scoreboard.setObjectiveInDisplaySlot(packetIn.func_149371_c(), scoreobjective);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void handleTeams(S3EPacketTeams packetIn) {
/*      */     ScorePlayerTeam scoreplayerteam;
/* 1473 */     PacketThreadUtil.checkThreadAndEnqueue((Packet)packetIn, (INetHandler)this, (IThreadListener)this.gameController);
/* 1474 */     Scoreboard scoreboard = this.clientWorldController.getScoreboard();
/*      */ 
/*      */     
/* 1477 */     if (packetIn.getAction() == 0) {
/* 1478 */       scoreplayerteam = scoreboard.createTeam(packetIn.getName());
/*      */     } else {
/* 1480 */       scoreplayerteam = scoreboard.getTeam(packetIn.getName());
/*      */     } 
/*      */     
/* 1483 */     if (packetIn.getAction() == 0 || packetIn.getAction() == 2) {
/* 1484 */       scoreplayerteam.setTeamName(packetIn.getDisplayName());
/* 1485 */       scoreplayerteam.setNamePrefix(packetIn.getPrefix());
/* 1486 */       scoreplayerteam.setNameSuffix(packetIn.getSuffix());
/* 1487 */       scoreplayerteam.setChatFormat(EnumChatFormatting.func_175744_a(packetIn.getColor()));
/* 1488 */       scoreplayerteam.func_98298_a(packetIn.getFriendlyFlags());
/* 1489 */       Team.EnumVisible team$enumvisible = Team.EnumVisible.func_178824_a(packetIn.getNameTagVisibility());
/*      */       
/* 1491 */       if (team$enumvisible != null) {
/* 1492 */         scoreplayerteam.setNameTagVisibility(team$enumvisible);
/*      */       }
/*      */     } 
/*      */     
/* 1496 */     if (packetIn.getAction() == 0 || packetIn.getAction() == 3) {
/* 1497 */       for (String s : packetIn.getPlayers()) {
/* 1498 */         scoreboard.addPlayerToTeam(s, packetIn.getName());
/*      */       }
/*      */     }
/*      */     
/* 1502 */     if (packetIn.getAction() == 4) {
/* 1503 */       for (String s1 : packetIn.getPlayers()) {
/* 1504 */         scoreboard.removePlayerFromTeam(s1, scoreplayerteam);
/*      */       }
/*      */     }
/*      */     
/* 1508 */     if (packetIn.getAction() == 1) {
/* 1509 */       scoreboard.removeTeam(scoreplayerteam);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void handleParticles(S2APacketParticles packetIn) {
/* 1518 */     PacketThreadUtil.checkThreadAndEnqueue((Packet)packetIn, (INetHandler)this, (IThreadListener)this.gameController);
/*      */     
/* 1520 */     if (packetIn.getParticleCount() == 0) {
/* 1521 */       double d0 = (packetIn.getParticleSpeed() * packetIn.getXOffset());
/* 1522 */       double d2 = (packetIn.getParticleSpeed() * packetIn.getYOffset());
/* 1523 */       double d4 = (packetIn.getParticleSpeed() * packetIn.getZOffset());
/*      */       
/*      */       try {
/* 1526 */         this.clientWorldController.spawnParticle(packetIn.getParticleType(), packetIn.isLongDistance(), packetIn.getXCoordinate(), packetIn.getYCoordinate(), packetIn.getZCoordinate(), d0, d2, d4, packetIn.getParticleArgs());
/* 1527 */       } catch (Throwable var17) {
/* 1528 */         logger.warn("Could not spawn particle effect " + packetIn.getParticleType());
/*      */       } 
/*      */     } else {
/* 1531 */       for (int i = 0; i < packetIn.getParticleCount(); i++) {
/* 1532 */         double d1 = this.avRandomizer.nextGaussian() * packetIn.getXOffset();
/* 1533 */         double d3 = this.avRandomizer.nextGaussian() * packetIn.getYOffset();
/* 1534 */         double d5 = this.avRandomizer.nextGaussian() * packetIn.getZOffset();
/* 1535 */         double d6 = this.avRandomizer.nextGaussian() * packetIn.getParticleSpeed();
/* 1536 */         double d7 = this.avRandomizer.nextGaussian() * packetIn.getParticleSpeed();
/* 1537 */         double d8 = this.avRandomizer.nextGaussian() * packetIn.getParticleSpeed();
/*      */         
/*      */         try {
/* 1540 */           this.clientWorldController.spawnParticle(packetIn.getParticleType(), packetIn.isLongDistance(), packetIn.getXCoordinate() + d1, packetIn.getYCoordinate() + d3, packetIn.getZCoordinate() + d5, d6, d7, d8, packetIn.getParticleArgs());
/* 1541 */         } catch (Throwable var16) {
/* 1542 */           logger.warn("Could not spawn particle effect " + packetIn.getParticleType());
/*      */           return;
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
/*      */   public void handleEntityProperties(S20PacketEntityProperties packetIn) {
/* 1555 */     PacketThreadUtil.checkThreadAndEnqueue((Packet)packetIn, (INetHandler)this, (IThreadListener)this.gameController);
/* 1556 */     Entity entity = this.clientWorldController.getEntityByID(packetIn.getEntityId());
/*      */     
/* 1558 */     if (entity != null) {
/* 1559 */       if (!(entity instanceof EntityLivingBase)) {
/* 1560 */         throw new IllegalStateException("Server tried to update attributes of a non-living entity (actually: " + entity + ")");
/*      */       }
/* 1562 */       BaseAttributeMap baseattributemap = ((EntityLivingBase)entity).getAttributeMap();
/*      */       
/* 1564 */       for (S20PacketEntityProperties.Snapshot s20packetentityproperties$snapshot : packetIn.func_149441_d()) {
/* 1565 */         IAttributeInstance iattributeinstance = baseattributemap.getAttributeInstanceByName(s20packetentityproperties$snapshot.func_151409_a());
/*      */         
/* 1567 */         if (iattributeinstance == null) {
/* 1568 */           iattributeinstance = baseattributemap.registerAttribute((IAttribute)new RangedAttribute((IAttribute)null, s20packetentityproperties$snapshot.func_151409_a(), 0.0D, 2.2250738585072014E-308D, Double.MAX_VALUE));
/*      */         }
/*      */         
/* 1571 */         iattributeinstance.setBaseValue(s20packetentityproperties$snapshot.func_151410_b());
/* 1572 */         iattributeinstance.removeAllModifiers();
/*      */         
/* 1574 */         for (AttributeModifier attributemodifier : s20packetentityproperties$snapshot.func_151408_c()) {
/* 1575 */           iattributeinstance.applyModifier(attributemodifier);
/*      */         }
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public NetworkManager getNetworkManager() {
/* 1586 */     return this.netManager;
/*      */   }
/*      */   
/*      */   public Collection<NetworkPlayerInfo> getPlayerInfoMap() {
/* 1590 */     return this.playerInfoMap.values();
/*      */   }
/*      */   
/*      */   public NetworkPlayerInfo getPlayerInfo(UUID p_175102_1_) {
/* 1594 */     return this.playerInfoMap.get(p_175102_1_);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public NetworkPlayerInfo getPlayerInfo(String p_175104_1_) {
/* 1601 */     for (NetworkPlayerInfo networkplayerinfo : this.playerInfoMap.values()) {
/* 1602 */       if (networkplayerinfo.getGameProfile().getName().equals(p_175104_1_)) {
/* 1603 */         return networkplayerinfo;
/*      */       }
/*      */     } 
/*      */     
/* 1607 */     return null;
/*      */   }
/*      */   
/*      */   public GameProfile getGameProfile() {
/* 1611 */     return this.profile;
/*      */   }
/*      */   
/*      */   public void sendQueueWithoutEvent(Packet p_147297_1_) {
/* 1615 */     this.netManager.sendPacketNoEvent(p_147297_1_);
/*      */   }
/*      */   
/*      */   public void addToSendQueueSilent(Packet<?> p_147297_1_) {
/* 1619 */     this.netManager.sendPacket(p_147297_1_);
/*      */   }
/*      */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\minecraft\client\network\NetHandlerPlayClient.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */