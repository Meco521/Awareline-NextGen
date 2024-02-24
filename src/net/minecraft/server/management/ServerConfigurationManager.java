/*      */ package net.minecraft.server.management;
/*      */ import com.google.common.collect.Lists;
/*      */ import com.google.common.collect.Maps;
/*      */ import com.mojang.authlib.GameProfile;
/*      */ import java.io.File;
/*      */ import java.net.SocketAddress;
/*      */ import java.text.SimpleDateFormat;
/*      */ import java.util.List;
/*      */ import java.util.Map;
/*      */ import java.util.Set;
/*      */ import java.util.UUID;
/*      */ import net.minecraft.entity.Entity;
/*      */ import net.minecraft.entity.player.EntityPlayer;
/*      */ import net.minecraft.entity.player.EntityPlayerMP;
/*      */ import net.minecraft.nbt.NBTTagCompound;
/*      */ import net.minecraft.network.NetHandlerPlayServer;
/*      */ import net.minecraft.network.NetworkManager;
/*      */ import net.minecraft.network.Packet;
/*      */ import net.minecraft.network.play.server.S05PacketSpawnPosition;
/*      */ import net.minecraft.network.play.server.S07PacketRespawn;
/*      */ import net.minecraft.network.play.server.S09PacketHeldItemChange;
/*      */ import net.minecraft.network.play.server.S1DPacketEntityEffect;
/*      */ import net.minecraft.network.play.server.S2BPacketChangeGameState;
/*      */ import net.minecraft.network.play.server.S38PacketPlayerListItem;
/*      */ import net.minecraft.network.play.server.S44PacketWorldBorder;
/*      */ import net.minecraft.potion.PotionEffect;
/*      */ import net.minecraft.scoreboard.ScoreObjective;
/*      */ import net.minecraft.scoreboard.ScorePlayerTeam;
/*      */ import net.minecraft.scoreboard.ServerScoreboard;
/*      */ import net.minecraft.scoreboard.Team;
/*      */ import net.minecraft.server.MinecraftServer;
/*      */ import net.minecraft.stats.StatisticsFile;
/*      */ import net.minecraft.util.BlockPos;
/*      */ import net.minecraft.util.ChatComponentTranslation;
/*      */ import net.minecraft.util.EnumChatFormatting;
/*      */ import net.minecraft.util.IChatComponent;
/*      */ import net.minecraft.util.MathHelper;
/*      */ import net.minecraft.world.World;
/*      */ import net.minecraft.world.WorldServer;
/*      */ import net.minecraft.world.WorldSettings;
/*      */ import net.minecraft.world.border.IBorderListener;
/*      */ import net.minecraft.world.border.WorldBorder;
/*      */ import net.minecraft.world.demo.DemoWorldManager;
/*      */ import net.minecraft.world.storage.WorldInfo;
/*      */ import org.apache.logging.log4j.LogManager;
/*      */ 
/*      */ public abstract class ServerConfigurationManager {
/*   48 */   public static final File FILE_PLAYERBANS = new File("banned-players.json");
/*   49 */   public static final File FILE_IPBANS = new File("banned-ips.json");
/*   50 */   public static final File FILE_OPS = new File("ops.json");
/*   51 */   public static final File FILE_WHITELIST = new File("whitelist.json");
/*   52 */   private static final Logger logger = LogManager.getLogger();
/*   53 */   private static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd 'at' HH:mm:ss z");
/*      */   
/*      */   private final MinecraftServer mcServer;
/*      */   
/*   57 */   private final List<EntityPlayerMP> playerEntityList = Lists.newArrayList();
/*   58 */   private final Map<UUID, EntityPlayerMP> uuidToPlayerMap = Maps.newHashMap();
/*      */ 
/*      */   
/*      */   private final UserListBans bannedPlayers;
/*      */ 
/*      */   
/*      */   private final BanList bannedIPs;
/*      */ 
/*      */   
/*      */   private final UserListOps ops;
/*      */ 
/*      */   
/*      */   private final UserListWhitelist whiteListedPlayers;
/*      */ 
/*      */   
/*      */   private final Map<UUID, StatisticsFile> playerStatFiles;
/*      */ 
/*      */   
/*      */   private IPlayerFileData playerNBTManagerObj;
/*      */   
/*      */   private boolean whiteListEnforced;
/*      */   
/*      */   protected int maxPlayers;
/*      */   
/*      */   private int viewDistance;
/*      */   
/*      */   private WorldSettings.GameType gameType;
/*      */   
/*      */   private boolean commandsAllowedForAll;
/*      */   
/*      */   private int playerPingIndex;
/*      */ 
/*      */   
/*      */   public ServerConfigurationManager(MinecraftServer server) {
/*   92 */     this.bannedPlayers = new UserListBans(FILE_PLAYERBANS);
/*   93 */     this.bannedIPs = new BanList(FILE_IPBANS);
/*   94 */     this.ops = new UserListOps(FILE_OPS);
/*   95 */     this.whiteListedPlayers = new UserListWhitelist(FILE_WHITELIST);
/*   96 */     this.playerStatFiles = Maps.newHashMap();
/*   97 */     this.mcServer = server;
/*   98 */     this.bannedPlayers.setLanServer(false);
/*   99 */     this.bannedIPs.setLanServer(false);
/*  100 */     this.maxPlayers = 8;
/*      */   }
/*      */   
/*      */   public void initializeConnectionToPlayer(NetworkManager netManager, EntityPlayerMP playerIn) {
/*      */     ChatComponentTranslation chatcomponenttranslation;
/*  105 */     GameProfile gameprofile = playerIn.getGameProfile();
/*  106 */     PlayerProfileCache playerprofilecache = this.mcServer.getPlayerProfileCache();
/*  107 */     GameProfile gameprofile1 = playerprofilecache.getProfileByUUID(gameprofile.getId());
/*  108 */     String s = (gameprofile1 == null) ? gameprofile.getName() : gameprofile1.getName();
/*  109 */     playerprofilecache.addEntry(gameprofile);
/*  110 */     NBTTagCompound nbttagcompound = readPlayerDataFromFile(playerIn);
/*  111 */     playerIn.setWorld((World)this.mcServer.worldServerForDimension(playerIn.dimension));
/*  112 */     playerIn.theItemInWorldManager.setWorld((WorldServer)playerIn.worldObj);
/*  113 */     String s1 = "local";
/*      */     
/*  115 */     if (netManager.getRemoteAddress() != null)
/*      */     {
/*  117 */       s1 = netManager.getRemoteAddress().toString();
/*      */     }
/*      */     
/*  120 */     logger.info(playerIn.getName() + "[" + s1 + "] logged in with entity id " + playerIn.getEntityId() + " at (" + playerIn.posX + ", " + playerIn.posY + ", " + playerIn.posZ + ")");
/*  121 */     WorldServer worldserver = this.mcServer.worldServerForDimension(playerIn.dimension);
/*  122 */     WorldInfo worldinfo = worldserver.getWorldInfo();
/*  123 */     BlockPos blockpos = worldserver.getSpawnPoint();
/*  124 */     setPlayerGameTypeBasedOnOther(playerIn, (EntityPlayerMP)null, (World)worldserver);
/*  125 */     NetHandlerPlayServer nethandlerplayserver = new NetHandlerPlayServer(this.mcServer, netManager, playerIn);
/*  126 */     nethandlerplayserver.sendPacket((Packet)new S01PacketJoinGame(playerIn.getEntityId(), playerIn.theItemInWorldManager.getGameType(), worldinfo.isHardcoreModeEnabled(), worldserver.provider.getDimensionId(), worldserver.getDifficulty(), this.maxPlayers, worldinfo.getTerrainType(), worldserver.getGameRules().getBoolean("reducedDebugInfo")));
/*  127 */     nethandlerplayserver.sendPacket((Packet)new S3FPacketCustomPayload("MC|Brand", (new PacketBuffer(Unpooled.buffer())).writeString(getServerInstance().getServerModName())));
/*  128 */     nethandlerplayserver.sendPacket((Packet)new S41PacketServerDifficulty(worldinfo.getDifficulty(), worldinfo.isDifficultyLocked()));
/*  129 */     nethandlerplayserver.sendPacket((Packet)new S05PacketSpawnPosition(blockpos));
/*  130 */     nethandlerplayserver.sendPacket((Packet)new S39PacketPlayerAbilities(playerIn.capabilities));
/*  131 */     nethandlerplayserver.sendPacket((Packet)new S09PacketHeldItemChange(playerIn.inventory.currentItem));
/*  132 */     playerIn.getStatFile().func_150877_d();
/*  133 */     playerIn.getStatFile().sendAchievements(playerIn);
/*  134 */     sendScoreboard((ServerScoreboard)worldserver.getScoreboard(), playerIn);
/*  135 */     this.mcServer.refreshStatusNextTick();
/*      */ 
/*      */     
/*  138 */     if (!playerIn.getName().equalsIgnoreCase(s)) {
/*      */       
/*  140 */       chatcomponenttranslation = new ChatComponentTranslation("multiplayer.player.joined.renamed", new Object[] { playerIn.getDisplayName(), s });
/*      */     }
/*      */     else {
/*      */       
/*  144 */       chatcomponenttranslation = new ChatComponentTranslation("multiplayer.player.joined", new Object[] { playerIn.getDisplayName() });
/*      */     } 
/*      */     
/*  147 */     chatcomponenttranslation.getChatStyle().setColor(EnumChatFormatting.YELLOW);
/*  148 */     sendChatMsg((IChatComponent)chatcomponenttranslation);
/*  149 */     playerLoggedIn(playerIn);
/*  150 */     nethandlerplayserver.setPlayerLocation(playerIn.posX, playerIn.posY, playerIn.posZ, playerIn.rotationYaw, playerIn.rotationPitch);
/*  151 */     updateTimeAndWeatherForPlayer(playerIn, worldserver);
/*      */     
/*  153 */     if (!this.mcServer.getResourcePackUrl().isEmpty())
/*      */     {
/*  155 */       playerIn.loadResourcePack(this.mcServer.getResourcePackUrl(), this.mcServer.getResourcePackHash());
/*      */     }
/*      */     
/*  158 */     for (PotionEffect potioneffect : playerIn.getActivePotionEffects())
/*      */     {
/*  160 */       nethandlerplayserver.sendPacket((Packet)new S1DPacketEntityEffect(playerIn.getEntityId(), potioneffect));
/*      */     }
/*      */     
/*  163 */     playerIn.addSelfToInternalCraftingInventory();
/*      */     
/*  165 */     if (nbttagcompound != null && nbttagcompound.hasKey("Riding", 10)) {
/*      */       
/*  167 */       Entity entity = EntityList.createEntityFromNBT(nbttagcompound.getCompoundTag("Riding"), (World)worldserver);
/*      */       
/*  169 */       if (entity != null) {
/*      */         
/*  171 */         entity.forceSpawn = true;
/*  172 */         worldserver.spawnEntityInWorld(entity);
/*  173 */         playerIn.mountEntity(entity);
/*  174 */         entity.forceSpawn = false;
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   protected void sendScoreboard(ServerScoreboard scoreboardIn, EntityPlayerMP playerIn) {
/*  181 */     Set<ScoreObjective> set = Sets.newHashSet();
/*      */     
/*  183 */     for (ScorePlayerTeam scoreplayerteam : scoreboardIn.getTeams())
/*      */     {
/*  185 */       playerIn.playerNetServerHandler.sendPacket((Packet)new S3EPacketTeams(scoreplayerteam, 0));
/*      */     }
/*      */     
/*  188 */     for (int i = 0; i < 19; i++) {
/*      */       
/*  190 */       ScoreObjective scoreobjective = scoreboardIn.getObjectiveInDisplaySlot(i);
/*      */       
/*  192 */       if (scoreobjective != null && !set.contains(scoreobjective)) {
/*      */         
/*  194 */         for (Packet packet : scoreboardIn.func_96550_d(scoreobjective))
/*      */         {
/*  196 */           playerIn.playerNetServerHandler.sendPacket(packet);
/*      */         }
/*      */         
/*  199 */         set.add(scoreobjective);
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setPlayerManager(WorldServer[] worldServers) {
/*  209 */     this.playerNBTManagerObj = worldServers[0].getSaveHandler().getPlayerNBTManager();
/*  210 */     worldServers[0].getWorldBorder().addListener(new IBorderListener()
/*      */         {
/*      */           public void onSizeChanged(WorldBorder border, double newSize)
/*      */           {
/*  214 */             ServerConfigurationManager.this.sendPacketToAllPlayers((Packet)new S44PacketWorldBorder(border, S44PacketWorldBorder.Action.SET_SIZE));
/*      */           }
/*      */           
/*      */           public void onTransitionStarted(WorldBorder border, double oldSize, double newSize, long time) {
/*  218 */             ServerConfigurationManager.this.sendPacketToAllPlayers((Packet)new S44PacketWorldBorder(border, S44PacketWorldBorder.Action.LERP_SIZE));
/*      */           }
/*      */           
/*      */           public void onCenterChanged(WorldBorder border, double x, double z) {
/*  222 */             ServerConfigurationManager.this.sendPacketToAllPlayers((Packet)new S44PacketWorldBorder(border, S44PacketWorldBorder.Action.SET_CENTER));
/*      */           }
/*      */           
/*      */           public void onWarningTimeChanged(WorldBorder border, int newTime) {
/*  226 */             ServerConfigurationManager.this.sendPacketToAllPlayers((Packet)new S44PacketWorldBorder(border, S44PacketWorldBorder.Action.SET_WARNING_TIME));
/*      */           }
/*      */           
/*      */           public void onWarningDistanceChanged(WorldBorder border, int newDistance) {
/*  230 */             ServerConfigurationManager.this.sendPacketToAllPlayers((Packet)new S44PacketWorldBorder(border, S44PacketWorldBorder.Action.SET_WARNING_BLOCKS));
/*      */           }
/*      */ 
/*      */           
/*      */           public void onDamageAmountChanged(WorldBorder border, double newAmount) {}
/*      */ 
/*      */           
/*      */           public void onDamageBufferChanged(WorldBorder border, double newSize) {}
/*      */         });
/*      */   }
/*      */ 
/*      */   
/*      */   public void preparePlayer(EntityPlayerMP playerIn, WorldServer worldIn) {
/*  243 */     WorldServer worldserver = playerIn.getServerForPlayer();
/*      */     
/*  245 */     if (worldIn != null)
/*      */     {
/*  247 */       worldIn.getPlayerManager().removePlayer(playerIn);
/*      */     }
/*      */     
/*  250 */     worldserver.getPlayerManager().addPlayer(playerIn);
/*  251 */     worldserver.theChunkProviderServer.loadChunk((int)playerIn.posX >> 4, (int)playerIn.posZ >> 4);
/*      */   }
/*      */ 
/*      */   
/*      */   public int getEntityViewDistance() {
/*  256 */     return PlayerManager.getFurthestViewableBlock(this.viewDistance);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public NBTTagCompound readPlayerDataFromFile(EntityPlayerMP playerIn) {
/*  264 */     NBTTagCompound nbttagcompound1, nbttagcompound = this.mcServer.worldServers[0].getWorldInfo().getPlayerNBTTagCompound();
/*      */ 
/*      */     
/*  267 */     if (playerIn.getName().equals(this.mcServer.getServerOwner()) && nbttagcompound != null) {
/*      */       
/*  269 */       playerIn.readFromNBT(nbttagcompound);
/*  270 */       nbttagcompound1 = nbttagcompound;
/*  271 */       logger.debug("loading single player");
/*      */     }
/*      */     else {
/*      */       
/*  275 */       nbttagcompound1 = this.playerNBTManagerObj.readPlayerData((EntityPlayer)playerIn);
/*      */     } 
/*      */     
/*  278 */     return nbttagcompound1;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void writePlayerData(EntityPlayerMP playerIn) {
/*  286 */     this.playerNBTManagerObj.writePlayerData((EntityPlayer)playerIn);
/*  287 */     StatisticsFile statisticsfile = this.playerStatFiles.get(playerIn.getUniqueID());
/*      */     
/*  289 */     if (statisticsfile != null)
/*      */     {
/*  291 */       statisticsfile.saveStatFile();
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void playerLoggedIn(EntityPlayerMP playerIn) {
/*  300 */     this.playerEntityList.add(playerIn);
/*  301 */     this.uuidToPlayerMap.put(playerIn.getUniqueID(), playerIn);
/*  302 */     sendPacketToAllPlayers((Packet)new S38PacketPlayerListItem(S38PacketPlayerListItem.Action.ADD_PLAYER, new EntityPlayerMP[] { playerIn }));
/*  303 */     WorldServer worldserver = this.mcServer.worldServerForDimension(playerIn.dimension);
/*  304 */     worldserver.spawnEntityInWorld((Entity)playerIn);
/*  305 */     preparePlayer(playerIn, (WorldServer)null);
/*      */     
/*  307 */     for (int i = 0; i < this.playerEntityList.size(); i++) {
/*      */       
/*  309 */       EntityPlayerMP entityplayermp = this.playerEntityList.get(i);
/*  310 */       playerIn.playerNetServerHandler.sendPacket((Packet)new S38PacketPlayerListItem(S38PacketPlayerListItem.Action.ADD_PLAYER, new EntityPlayerMP[] { entityplayermp }));
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void serverUpdateMountedMovingPlayer(EntityPlayerMP playerIn) {
/*  319 */     playerIn.getServerForPlayer().getPlayerManager().updateMountedMovingPlayer(playerIn);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void playerLoggedOut(EntityPlayerMP playerIn) {
/*  327 */     playerIn.triggerAchievement(StatList.leaveGameStat);
/*  328 */     writePlayerData(playerIn);
/*  329 */     WorldServer worldserver = playerIn.getServerForPlayer();
/*      */     
/*  331 */     if (playerIn.ridingEntity != null) {
/*      */       
/*  333 */       worldserver.removePlayerEntityDangerously(playerIn.ridingEntity);
/*  334 */       logger.debug("removing player mount");
/*      */     } 
/*      */     
/*  337 */     worldserver.removeEntity((Entity)playerIn);
/*  338 */     worldserver.getPlayerManager().removePlayer(playerIn);
/*  339 */     this.playerEntityList.remove(playerIn);
/*  340 */     UUID uuid = playerIn.getUniqueID();
/*  341 */     EntityPlayerMP entityplayermp = this.uuidToPlayerMap.get(uuid);
/*      */     
/*  343 */     if (entityplayermp == playerIn) {
/*      */       
/*  345 */       this.uuidToPlayerMap.remove(uuid);
/*  346 */       this.playerStatFiles.remove(uuid);
/*      */     } 
/*      */     
/*  349 */     sendPacketToAllPlayers((Packet)new S38PacketPlayerListItem(S38PacketPlayerListItem.Action.REMOVE_PLAYER, new EntityPlayerMP[] { playerIn }));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String allowUserToConnect(SocketAddress address, GameProfile profile) {
/*  357 */     if (this.bannedPlayers.isBanned(profile)) {
/*      */       
/*  359 */       UserListBansEntry userlistbansentry = this.bannedPlayers.getEntry(profile);
/*  360 */       String s1 = "You are banned from this server!\nReason: " + userlistbansentry.getBanReason();
/*      */       
/*  362 */       if (userlistbansentry.getBanEndDate() != null)
/*      */       {
/*  364 */         s1 = s1 + "\nYour ban will be removed on " + dateFormat.format(userlistbansentry.getBanEndDate());
/*      */       }
/*      */       
/*  367 */       return s1;
/*      */     } 
/*  369 */     if (!canJoin(profile))
/*      */     {
/*  371 */       return "You are not white-listed on this server!";
/*      */     }
/*  373 */     if (this.bannedIPs.isBanned(address)) {
/*      */       
/*  375 */       IPBanEntry ipbanentry = this.bannedIPs.getBanEntry(address);
/*  376 */       String s = "Your IP address is banned from this server!\nReason: " + ipbanentry.getBanReason();
/*      */       
/*  378 */       if (ipbanentry.getBanEndDate() != null)
/*      */       {
/*  380 */         s = s + "\nYour ban will be removed on " + dateFormat.format(ipbanentry.getBanEndDate());
/*      */       }
/*      */       
/*  383 */       return s;
/*      */     } 
/*      */ 
/*      */     
/*  387 */     return (this.playerEntityList.size() >= this.maxPlayers && !bypassesPlayerLimit(profile)) ? "The server is full!" : null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public EntityPlayerMP createPlayerForUser(GameProfile profile) {
/*      */     ItemInWorldManager iteminworldmanager;
/*  396 */     UUID uuid = EntityPlayer.getUUID(profile);
/*  397 */     List<EntityPlayerMP> list = Lists.newArrayList();
/*      */     
/*  399 */     for (int i = 0; i < this.playerEntityList.size(); i++) {
/*      */       
/*  401 */       EntityPlayerMP entityplayermp = this.playerEntityList.get(i);
/*      */       
/*  403 */       if (entityplayermp.getUniqueID().equals(uuid))
/*      */       {
/*  405 */         list.add(entityplayermp);
/*      */       }
/*      */     } 
/*      */     
/*  409 */     EntityPlayerMP entityplayermp2 = this.uuidToPlayerMap.get(profile.getId());
/*      */     
/*  411 */     if (entityplayermp2 != null && !list.contains(entityplayermp2))
/*      */     {
/*  413 */       list.add(entityplayermp2);
/*      */     }
/*      */     
/*  416 */     for (EntityPlayerMP entityplayermp1 : list)
/*      */     {
/*  418 */       entityplayermp1.playerNetServerHandler.kickPlayerFromServer("You logged in from another location");
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*  423 */     if (this.mcServer.isDemo()) {
/*      */       
/*  425 */       DemoWorldManager demoWorldManager = new DemoWorldManager((World)this.mcServer.worldServerForDimension(0));
/*      */     }
/*      */     else {
/*      */       
/*  429 */       iteminworldmanager = new ItemInWorldManager((World)this.mcServer.worldServerForDimension(0));
/*      */     } 
/*      */     
/*  432 */     return new EntityPlayerMP(this.mcServer, this.mcServer.worldServerForDimension(0), profile, iteminworldmanager);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public EntityPlayerMP recreatePlayerEntity(EntityPlayerMP playerIn, int dimension, boolean conqueredEnd) {
/*      */     ItemInWorldManager iteminworldmanager;
/*  440 */     playerIn.getServerForPlayer().getEntityTracker().removePlayerFromTrackers(playerIn);
/*  441 */     playerIn.getServerForPlayer().getEntityTracker().untrackEntity((Entity)playerIn);
/*  442 */     playerIn.getServerForPlayer().getPlayerManager().removePlayer(playerIn);
/*  443 */     this.playerEntityList.remove(playerIn);
/*  444 */     this.mcServer.worldServerForDimension(playerIn.dimension).removePlayerEntityDangerously((Entity)playerIn);
/*  445 */     BlockPos blockpos = playerIn.getBedLocation();
/*  446 */     boolean flag = playerIn.isSpawnForced();
/*  447 */     playerIn.dimension = dimension;
/*      */ 
/*      */     
/*  450 */     if (this.mcServer.isDemo()) {
/*      */       
/*  452 */       DemoWorldManager demoWorldManager = new DemoWorldManager((World)this.mcServer.worldServerForDimension(playerIn.dimension));
/*      */     }
/*      */     else {
/*      */       
/*  456 */       iteminworldmanager = new ItemInWorldManager((World)this.mcServer.worldServerForDimension(playerIn.dimension));
/*      */     } 
/*      */     
/*  459 */     EntityPlayerMP entityplayermp = new EntityPlayerMP(this.mcServer, this.mcServer.worldServerForDimension(playerIn.dimension), playerIn.getGameProfile(), iteminworldmanager);
/*  460 */     entityplayermp.playerNetServerHandler = playerIn.playerNetServerHandler;
/*  461 */     entityplayermp.clonePlayer((EntityPlayer)playerIn, conqueredEnd);
/*  462 */     entityplayermp.setEntityId(playerIn.getEntityId());
/*  463 */     entityplayermp.setCommandStats((Entity)playerIn);
/*  464 */     WorldServer worldserver = this.mcServer.worldServerForDimension(playerIn.dimension);
/*  465 */     setPlayerGameTypeBasedOnOther(entityplayermp, playerIn, (World)worldserver);
/*      */     
/*  467 */     if (blockpos != null) {
/*      */       
/*  469 */       BlockPos blockpos1 = EntityPlayer.getBedSpawnLocation((World)this.mcServer.worldServerForDimension(playerIn.dimension), blockpos, flag);
/*      */       
/*  471 */       if (blockpos1 != null) {
/*      */         
/*  473 */         entityplayermp.setLocationAndAngles((blockpos1.getX() + 0.5F), (blockpos1.getY() + 0.1F), (blockpos1.getZ() + 0.5F), 0.0F, 0.0F);
/*  474 */         entityplayermp.setSpawnPoint(blockpos, flag);
/*      */       }
/*      */       else {
/*      */         
/*  478 */         entityplayermp.playerNetServerHandler.sendPacket((Packet)new S2BPacketChangeGameState(0, 0.0F));
/*      */       } 
/*      */     } 
/*      */     
/*  482 */     worldserver.theChunkProviderServer.loadChunk((int)entityplayermp.posX >> 4, (int)entityplayermp.posZ >> 4);
/*      */     
/*  484 */     while (!worldserver.getCollidingBoundingBoxes((Entity)entityplayermp, entityplayermp.getEntityBoundingBox()).isEmpty() && entityplayermp.posY < 256.0D)
/*      */     {
/*  486 */       entityplayermp.setPosition(entityplayermp.posX, entityplayermp.posY + 1.0D, entityplayermp.posZ);
/*      */     }
/*      */     
/*  489 */     entityplayermp.playerNetServerHandler.sendPacket((Packet)new S07PacketRespawn(entityplayermp.dimension, entityplayermp.worldObj.getDifficulty(), entityplayermp.worldObj.getWorldInfo().getTerrainType(), entityplayermp.theItemInWorldManager.getGameType()));
/*  490 */     BlockPos blockpos2 = worldserver.getSpawnPoint();
/*  491 */     entityplayermp.playerNetServerHandler.setPlayerLocation(entityplayermp.posX, entityplayermp.posY, entityplayermp.posZ, entityplayermp.rotationYaw, entityplayermp.rotationPitch);
/*  492 */     entityplayermp.playerNetServerHandler.sendPacket((Packet)new S05PacketSpawnPosition(blockpos2));
/*  493 */     entityplayermp.playerNetServerHandler.sendPacket((Packet)new S1FPacketSetExperience(entityplayermp.experience, entityplayermp.experienceTotal, entityplayermp.experienceLevel));
/*  494 */     updateTimeAndWeatherForPlayer(entityplayermp, worldserver);
/*  495 */     worldserver.getPlayerManager().addPlayer(entityplayermp);
/*  496 */     worldserver.spawnEntityInWorld((Entity)entityplayermp);
/*  497 */     this.playerEntityList.add(entityplayermp);
/*  498 */     this.uuidToPlayerMap.put(entityplayermp.getUniqueID(), entityplayermp);
/*  499 */     entityplayermp.addSelfToInternalCraftingInventory();
/*  500 */     entityplayermp.setHealth(entityplayermp.getHealth());
/*  501 */     return entityplayermp;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void transferPlayerToDimension(EntityPlayerMP playerIn, int dimension) {
/*  509 */     int i = playerIn.dimension;
/*  510 */     WorldServer worldserver = this.mcServer.worldServerForDimension(playerIn.dimension);
/*  511 */     playerIn.dimension = dimension;
/*  512 */     WorldServer worldserver1 = this.mcServer.worldServerForDimension(playerIn.dimension);
/*  513 */     playerIn.playerNetServerHandler.sendPacket((Packet)new S07PacketRespawn(playerIn.dimension, playerIn.worldObj.getDifficulty(), playerIn.worldObj.getWorldInfo().getTerrainType(), playerIn.theItemInWorldManager.getGameType()));
/*  514 */     worldserver.removePlayerEntityDangerously((Entity)playerIn);
/*  515 */     playerIn.isDead = false;
/*  516 */     transferEntityToWorld((Entity)playerIn, i, worldserver, worldserver1);
/*  517 */     preparePlayer(playerIn, worldserver);
/*  518 */     playerIn.playerNetServerHandler.setPlayerLocation(playerIn.posX, playerIn.posY, playerIn.posZ, playerIn.rotationYaw, playerIn.rotationPitch);
/*  519 */     playerIn.theItemInWorldManager.setWorld(worldserver1);
/*  520 */     updateTimeAndWeatherForPlayer(playerIn, worldserver1);
/*  521 */     syncPlayerInventory(playerIn);
/*      */     
/*  523 */     for (PotionEffect potioneffect : playerIn.getActivePotionEffects())
/*      */     {
/*  525 */       playerIn.playerNetServerHandler.sendPacket((Packet)new S1DPacketEntityEffect(playerIn.getEntityId(), potioneffect));
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void transferEntityToWorld(Entity entityIn, int p_82448_2_, WorldServer oldWorldIn, WorldServer toWorldIn) {
/*  537 */     double d0 = entityIn.posX;
/*  538 */     double d1 = entityIn.posZ;
/*  539 */     double d2 = 8.0D;
/*  540 */     float f = entityIn.rotationYaw;
/*  541 */     oldWorldIn.theProfiler.startSection("moving");
/*      */     
/*  543 */     if (entityIn.dimension == -1) {
/*      */       
/*  545 */       d0 = MathHelper.clamp_double(d0 / d2, toWorldIn.getWorldBorder().minX() + 16.0D, toWorldIn.getWorldBorder().maxX() - 16.0D);
/*  546 */       d1 = MathHelper.clamp_double(d1 / d2, toWorldIn.getWorldBorder().minZ() + 16.0D, toWorldIn.getWorldBorder().maxZ() - 16.0D);
/*  547 */       entityIn.setLocationAndAngles(d0, entityIn.posY, d1, entityIn.rotationYaw, entityIn.rotationPitch);
/*      */       
/*  549 */       if (entityIn.isEntityAlive())
/*      */       {
/*  551 */         oldWorldIn.updateEntityWithOptionalForce(entityIn, false);
/*      */       }
/*      */     }
/*  554 */     else if (entityIn.dimension == 0) {
/*      */       
/*  556 */       d0 = MathHelper.clamp_double(d0 * d2, toWorldIn.getWorldBorder().minX() + 16.0D, toWorldIn.getWorldBorder().maxX() - 16.0D);
/*  557 */       d1 = MathHelper.clamp_double(d1 * d2, toWorldIn.getWorldBorder().minZ() + 16.0D, toWorldIn.getWorldBorder().maxZ() - 16.0D);
/*  558 */       entityIn.setLocationAndAngles(d0, entityIn.posY, d1, entityIn.rotationYaw, entityIn.rotationPitch);
/*      */       
/*  560 */       if (entityIn.isEntityAlive())
/*      */       {
/*  562 */         oldWorldIn.updateEntityWithOptionalForce(entityIn, false);
/*      */       }
/*      */     } else {
/*      */       BlockPos blockpos;
/*      */ 
/*      */ 
/*      */       
/*  569 */       if (p_82448_2_ == 1) {
/*      */         
/*  571 */         blockpos = toWorldIn.getSpawnPoint();
/*      */       }
/*      */       else {
/*      */         
/*  575 */         blockpos = toWorldIn.getSpawnCoordinate();
/*      */       } 
/*      */       
/*  578 */       d0 = blockpos.getX();
/*  579 */       entityIn.posY = blockpos.getY();
/*  580 */       d1 = blockpos.getZ();
/*  581 */       entityIn.setLocationAndAngles(d0, entityIn.posY, d1, 90.0F, 0.0F);
/*      */       
/*  583 */       if (entityIn.isEntityAlive())
/*      */       {
/*  585 */         oldWorldIn.updateEntityWithOptionalForce(entityIn, false);
/*      */       }
/*      */     } 
/*      */     
/*  589 */     oldWorldIn.theProfiler.endSection();
/*      */     
/*  591 */     if (p_82448_2_ != 1) {
/*      */       
/*  593 */       oldWorldIn.theProfiler.startSection("placing");
/*  594 */       d0 = MathHelper.clamp_int((int)d0, -29999872, 29999872);
/*  595 */       d1 = MathHelper.clamp_int((int)d1, -29999872, 29999872);
/*      */       
/*  597 */       if (entityIn.isEntityAlive()) {
/*      */         
/*  599 */         entityIn.setLocationAndAngles(d0, entityIn.posY, d1, entityIn.rotationYaw, entityIn.rotationPitch);
/*  600 */         toWorldIn.getDefaultTeleporter().placeInPortal(entityIn, f);
/*  601 */         toWorldIn.spawnEntityInWorld(entityIn);
/*  602 */         toWorldIn.updateEntityWithOptionalForce(entityIn, false);
/*      */       } 
/*      */       
/*  605 */       oldWorldIn.theProfiler.endSection();
/*      */     } 
/*      */     
/*  608 */     entityIn.setWorld((World)toWorldIn);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void onTick() {
/*  616 */     if (++this.playerPingIndex > 600) {
/*      */       
/*  618 */       sendPacketToAllPlayers((Packet)new S38PacketPlayerListItem(S38PacketPlayerListItem.Action.UPDATE_LATENCY, this.playerEntityList));
/*  619 */       this.playerPingIndex = 0;
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public void sendPacketToAllPlayers(Packet packetIn) {
/*  625 */     for (int i = 0; i < this.playerEntityList.size(); i++)
/*      */     {
/*  627 */       ((EntityPlayerMP)this.playerEntityList.get(i)).playerNetServerHandler.sendPacket(packetIn);
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   public void sendPacketToAllPlayersInDimension(Packet packetIn, int dimension) {
/*  633 */     for (int i = 0; i < this.playerEntityList.size(); i++) {
/*      */       
/*  635 */       EntityPlayerMP entityplayermp = this.playerEntityList.get(i);
/*      */       
/*  637 */       if (entityplayermp.dimension == dimension)
/*      */       {
/*  639 */         entityplayermp.playerNetServerHandler.sendPacket(packetIn);
/*      */       }
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public void sendMessageToAllTeamMembers(EntityPlayer player, IChatComponent message) {
/*  646 */     Team team = player.getTeam();
/*      */     
/*  648 */     if (team != null)
/*      */     {
/*  650 */       for (String s : team.getMembershipCollection()) {
/*      */         
/*  652 */         EntityPlayerMP entityplayermp = getPlayerByUsername(s);
/*      */         
/*  654 */         if (entityplayermp != null && entityplayermp != player)
/*      */         {
/*  656 */           entityplayermp.addChatMessage(message);
/*      */         }
/*      */       } 
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   public void sendMessageToTeamOrEvryPlayer(EntityPlayer player, IChatComponent message) {
/*  664 */     Team team = player.getTeam();
/*      */     
/*  666 */     if (team == null) {
/*      */       
/*  668 */       sendChatMsg(message);
/*      */     }
/*      */     else {
/*      */       
/*  672 */       for (int i = 0; i < this.playerEntityList.size(); i++) {
/*      */         
/*  674 */         EntityPlayerMP entityplayermp = this.playerEntityList.get(i);
/*      */         
/*  676 */         if (entityplayermp.getTeam() != team)
/*      */         {
/*  678 */           entityplayermp.addChatMessage(message);
/*      */         }
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public String func_181058_b(boolean p_181058_1_) {
/*  686 */     StringBuilder s = new StringBuilder();
/*  687 */     List<EntityPlayerMP> list = Lists.newArrayList(this.playerEntityList);
/*      */     
/*  689 */     for (int i = 0; i < list.size(); i++) {
/*      */       
/*  691 */       if (i > 0)
/*      */       {
/*  693 */         s.append(", ");
/*      */       }
/*      */       
/*  696 */       s.append(((EntityPlayerMP)list.get(i)).getName());
/*      */       
/*  698 */       if (p_181058_1_)
/*      */       {
/*  700 */         s.append(" (").append(((EntityPlayerMP)list.get(i)).getUniqueID().toString()).append(")");
/*      */       }
/*      */     } 
/*      */     
/*  704 */     return s.toString();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String[] getAllUsernames() {
/*  712 */     String[] astring = new String[this.playerEntityList.size()];
/*      */     
/*  714 */     for (int i = 0; i < this.playerEntityList.size(); i++)
/*      */     {
/*  716 */       astring[i] = ((EntityPlayerMP)this.playerEntityList.get(i)).getName();
/*      */     }
/*      */     
/*  719 */     return astring;
/*      */   }
/*      */ 
/*      */   
/*      */   public GameProfile[] getAllProfiles() {
/*  724 */     GameProfile[] agameprofile = new GameProfile[this.playerEntityList.size()];
/*      */     
/*  726 */     for (int i = 0; i < this.playerEntityList.size(); i++)
/*      */     {
/*  728 */       agameprofile[i] = ((EntityPlayerMP)this.playerEntityList.get(i)).getGameProfile();
/*      */     }
/*      */     
/*  731 */     return agameprofile;
/*      */   }
/*      */ 
/*      */   
/*      */   public UserListBans getBannedPlayers() {
/*  736 */     return this.bannedPlayers;
/*      */   }
/*      */ 
/*      */   
/*      */   public BanList getBannedIPs() {
/*  741 */     return this.bannedIPs;
/*      */   }
/*      */ 
/*      */   
/*      */   public void addOp(GameProfile profile) {
/*  746 */     this.ops.addEntry(new UserListOpsEntry(profile, this.mcServer.getOpPermissionLevel(), this.ops.bypassesPlayerLimit(profile)));
/*      */   }
/*      */ 
/*      */   
/*      */   public void removeOp(GameProfile profile) {
/*  751 */     this.ops.removeEntry(profile);
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean canJoin(GameProfile profile) {
/*  756 */     return (!this.whiteListEnforced || this.ops.hasEntry(profile) || this.whiteListedPlayers.hasEntry(profile));
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean canSendCommands(GameProfile profile) {
/*  761 */     return (this.ops.hasEntry(profile) || (this.mcServer.isSinglePlayer() && this.mcServer.worldServers[0].getWorldInfo().areCommandsAllowed() && this.mcServer.getServerOwner().equalsIgnoreCase(profile.getName())) || this.commandsAllowedForAll);
/*      */   }
/*      */ 
/*      */   
/*      */   public EntityPlayerMP getPlayerByUsername(String username) {
/*  766 */     for (EntityPlayerMP entityplayermp : this.playerEntityList) {
/*      */       
/*  768 */       if (entityplayermp.getName().equalsIgnoreCase(username))
/*      */       {
/*  770 */         return entityplayermp;
/*      */       }
/*      */     } 
/*      */     
/*  774 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void sendToAllNear(double x, double y, double z, double radius, int dimension, Packet packetIn) {
/*  782 */     sendToAllNearExcept((EntityPlayer)null, x, y, z, radius, dimension, packetIn);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void sendToAllNearExcept(EntityPlayer p_148543_1_, double x, double y, double z, double radius, int dimension, Packet p_148543_11_) {
/*  791 */     for (int i = 0; i < this.playerEntityList.size(); i++) {
/*      */       
/*  793 */       EntityPlayerMP entityplayermp = this.playerEntityList.get(i);
/*      */       
/*  795 */       if (entityplayermp != p_148543_1_ && entityplayermp.dimension == dimension) {
/*      */         
/*  797 */         double d0 = x - entityplayermp.posX;
/*  798 */         double d1 = y - entityplayermp.posY;
/*  799 */         double d2 = z - entityplayermp.posZ;
/*      */         
/*  801 */         if (d0 * d0 + d1 * d1 + d2 * d2 < radius * radius)
/*      */         {
/*  803 */           entityplayermp.playerNetServerHandler.sendPacket(p_148543_11_);
/*      */         }
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void saveAllPlayerData() {
/*  814 */     for (int i = 0; i < this.playerEntityList.size(); i++)
/*      */     {
/*  816 */       writePlayerData(this.playerEntityList.get(i));
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   public void addWhitelistedPlayer(GameProfile profile) {
/*  822 */     this.whiteListedPlayers.addEntry(new UserListWhitelistEntry(profile));
/*      */   }
/*      */ 
/*      */   
/*      */   public void removePlayerFromWhitelist(GameProfile profile) {
/*  827 */     this.whiteListedPlayers.removeEntry(profile);
/*      */   }
/*      */ 
/*      */   
/*      */   public UserListWhitelist getWhitelistedPlayers() {
/*  832 */     return this.whiteListedPlayers;
/*      */   }
/*      */ 
/*      */   
/*      */   public String[] getWhitelistedPlayerNames() {
/*  837 */     return this.whiteListedPlayers.getKeys();
/*      */   }
/*      */ 
/*      */   
/*      */   public UserListOps getOppedPlayers() {
/*  842 */     return this.ops;
/*      */   }
/*      */ 
/*      */   
/*      */   public String[] getOppedPlayerNames() {
/*  847 */     return this.ops.getKeys();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void loadWhiteList() {}
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void updateTimeAndWeatherForPlayer(EntityPlayerMP playerIn, WorldServer worldIn) {
/*  862 */     WorldBorder worldborder = this.mcServer.worldServers[0].getWorldBorder();
/*  863 */     playerIn.playerNetServerHandler.sendPacket((Packet)new S44PacketWorldBorder(worldborder, S44PacketWorldBorder.Action.INITIALIZE));
/*  864 */     playerIn.playerNetServerHandler.sendPacket((Packet)new S03PacketTimeUpdate(worldIn.getTotalWorldTime(), worldIn.getWorldTime(), worldIn.getGameRules().getBoolean("doDaylightCycle")));
/*      */     
/*  866 */     if (worldIn.isRaining()) {
/*      */       
/*  868 */       playerIn.playerNetServerHandler.sendPacket((Packet)new S2BPacketChangeGameState(1, 0.0F));
/*  869 */       playerIn.playerNetServerHandler.sendPacket((Packet)new S2BPacketChangeGameState(7, worldIn.getRainStrength(1.0F)));
/*  870 */       playerIn.playerNetServerHandler.sendPacket((Packet)new S2BPacketChangeGameState(8, worldIn.getThunderStrength(1.0F)));
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void syncPlayerInventory(EntityPlayerMP playerIn) {
/*  879 */     playerIn.sendContainerToPlayer(playerIn.inventoryContainer);
/*  880 */     playerIn.setPlayerHealthUpdated();
/*  881 */     playerIn.playerNetServerHandler.sendPacket((Packet)new S09PacketHeldItemChange(playerIn.inventory.currentItem));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getCurrentPlayerCount() {
/*  889 */     return this.playerEntityList.size();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getMaxPlayers() {
/*  897 */     return this.maxPlayers;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String[] getAvailablePlayerDat() {
/*  905 */     return this.mcServer.worldServers[0].getSaveHandler().getPlayerNBTManager().getAvailablePlayerDat();
/*      */   }
/*      */ 
/*      */   
/*      */   public void setWhiteListEnabled(boolean whitelistEnabled) {
/*  910 */     this.whiteListEnforced = whitelistEnabled;
/*      */   }
/*      */ 
/*      */   
/*      */   public List<EntityPlayerMP> getPlayersMatchingAddress(String address) {
/*  915 */     List<EntityPlayerMP> list = Lists.newArrayList();
/*      */     
/*  917 */     for (EntityPlayerMP entityplayermp : this.playerEntityList) {
/*      */       
/*  919 */       if (entityplayermp.getPlayerIP().equals(address))
/*      */       {
/*  921 */         list.add(entityplayermp);
/*      */       }
/*      */     } 
/*      */     
/*  925 */     return list;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getViewDistance() {
/*  933 */     return this.viewDistance;
/*      */   }
/*      */ 
/*      */   
/*      */   public MinecraftServer getServerInstance() {
/*  938 */     return this.mcServer;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public NBTTagCompound getHostPlayerData() {
/*  946 */     return null;
/*      */   }
/*      */ 
/*      */   
/*      */   public void setGameType(WorldSettings.GameType p_152604_1_) {
/*  951 */     this.gameType = p_152604_1_;
/*      */   }
/*      */ 
/*      */   
/*      */   private void setPlayerGameTypeBasedOnOther(EntityPlayerMP p_72381_1_, EntityPlayerMP p_72381_2_, World worldIn) {
/*  956 */     if (p_72381_2_ != null) {
/*      */       
/*  958 */       p_72381_1_.theItemInWorldManager.setGameType(p_72381_2_.theItemInWorldManager.getGameType());
/*      */     }
/*  960 */     else if (this.gameType != null) {
/*      */       
/*  962 */       p_72381_1_.theItemInWorldManager.setGameType(this.gameType);
/*      */     } 
/*      */     
/*  965 */     p_72381_1_.theItemInWorldManager.initializeGameType(worldIn.getWorldInfo().getGameType());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setCommandsAllowedForAll(boolean p_72387_1_) {
/*  973 */     this.commandsAllowedForAll = p_72387_1_;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void removeAllPlayers() {
/*  981 */     for (int i = 0; i < this.playerEntityList.size(); i++)
/*      */     {
/*  983 */       ((EntityPlayerMP)this.playerEntityList.get(i)).playerNetServerHandler.kickPlayerFromServer("Server closed");
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   public void sendChatMsgImpl(IChatComponent component, boolean isChat) {
/*  989 */     this.mcServer.addChatMessage(component);
/*  990 */     byte b0 = (byte)(isChat ? 1 : 0);
/*  991 */     sendPacketToAllPlayers((Packet)new S02PacketChat(component, b0));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void sendChatMsg(IChatComponent component) {
/*  999 */     sendChatMsgImpl(component, true);
/*      */   }
/*      */ 
/*      */   
/*      */   public StatisticsFile getPlayerStatsFile(EntityPlayer playerIn) {
/* 1004 */     UUID uuid = playerIn.getUniqueID();
/* 1005 */     StatisticsFile statisticsfile = (uuid == null) ? null : this.playerStatFiles.get(uuid);
/*      */     
/* 1007 */     if (statisticsfile == null) {
/*      */       
/* 1009 */       File file1 = new File(this.mcServer.worldServerForDimension(0).getSaveHandler().getWorldDirectory(), "stats");
/* 1010 */       File file2 = new File(file1, uuid.toString() + ".json");
/*      */       
/* 1012 */       if (!file2.exists()) {
/*      */         
/* 1014 */         File file3 = new File(file1, playerIn.getName() + ".json");
/*      */         
/* 1016 */         if (file3.exists() && file3.isFile())
/*      */         {
/* 1018 */           file3.renameTo(file2);
/*      */         }
/*      */       } 
/*      */       
/* 1022 */       statisticsfile = new StatisticsFile(this.mcServer, file2);
/* 1023 */       statisticsfile.readStatFile();
/* 1024 */       this.playerStatFiles.put(uuid, statisticsfile);
/*      */     } 
/*      */     
/* 1027 */     return statisticsfile;
/*      */   }
/*      */ 
/*      */   
/*      */   public void setViewDistance(int distance) {
/* 1032 */     this.viewDistance = distance;
/*      */     
/* 1034 */     if (this.mcServer.worldServers != null)
/*      */     {
/* 1036 */       for (WorldServer worldserver : this.mcServer.worldServers) {
/*      */         
/* 1038 */         if (worldserver != null)
/*      */         {
/* 1040 */           worldserver.getPlayerManager().setPlayerViewRadius(distance);
/*      */         }
/*      */       } 
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   public List<EntityPlayerMP> getPlayerList() {
/* 1048 */     return this.playerEntityList;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public EntityPlayerMP getPlayerByUUID(UUID playerUUID) {
/* 1056 */     return this.uuidToPlayerMap.get(playerUUID);
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean bypassesPlayerLimit(GameProfile p_183023_1_) {
/* 1061 */     return false;
/*      */   }
/*      */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\minecraft\server\management\ServerConfigurationManager.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */