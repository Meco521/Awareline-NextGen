/*      */ package net.minecraft.entity.player;
/*      */ import com.google.common.collect.Lists;
/*      */ import com.mojang.authlib.GameProfile;
/*      */ import io.netty.buffer.Unpooled;
/*      */ import java.util.Iterator;
/*      */ import java.util.List;
/*      */ import java.util.Set;
/*      */ import net.minecraft.block.Block;
/*      */ import net.minecraft.crash.CrashReport;
/*      */ import net.minecraft.entity.Entity;
/*      */ import net.minecraft.entity.EntityList;
/*      */ import net.minecraft.entity.EntityLivingBase;
/*      */ import net.minecraft.entity.IMerchant;
/*      */ import net.minecraft.entity.passive.EntityHorse;
/*      */ import net.minecraft.entity.projectile.EntityArrow;
/*      */ import net.minecraft.inventory.Container;
/*      */ import net.minecraft.inventory.IInventory;
/*      */ import net.minecraft.inventory.InventoryMerchant;
/*      */ import net.minecraft.item.Item;
/*      */ import net.minecraft.item.ItemStack;
/*      */ import net.minecraft.nbt.NBTTagCompound;
/*      */ import net.minecraft.network.Packet;
/*      */ import net.minecraft.network.PacketBuffer;
/*      */ import net.minecraft.network.play.client.C15PacketClientSettings;
/*      */ import net.minecraft.network.play.server.S02PacketChat;
/*      */ import net.minecraft.network.play.server.S0APacketUseBed;
/*      */ import net.minecraft.network.play.server.S0BPacketAnimation;
/*      */ import net.minecraft.network.play.server.S13PacketDestroyEntities;
/*      */ import net.minecraft.network.play.server.S1DPacketEntityEffect;
/*      */ import net.minecraft.network.play.server.S2BPacketChangeGameState;
/*      */ import net.minecraft.network.play.server.S2DPacketOpenWindow;
/*      */ import net.minecraft.network.play.server.S2FPacketSetSlot;
/*      */ import net.minecraft.network.play.server.S42PacketCombatEvent;
/*      */ import net.minecraft.potion.PotionEffect;
/*      */ import net.minecraft.scoreboard.IScoreObjectiveCriteria;
/*      */ import net.minecraft.scoreboard.Score;
/*      */ import net.minecraft.scoreboard.ScoreObjective;
/*      */ import net.minecraft.scoreboard.Team;
/*      */ import net.minecraft.server.MinecraftServer;
/*      */ import net.minecraft.server.management.ItemInWorldManager;
/*      */ import net.minecraft.server.management.UserListOpsEntry;
/*      */ import net.minecraft.stats.AchievementList;
/*      */ import net.minecraft.stats.StatBase;
/*      */ import net.minecraft.tileentity.TileEntity;
/*      */ import net.minecraft.tileentity.TileEntitySign;
/*      */ import net.minecraft.util.BlockPos;
/*      */ import net.minecraft.util.DamageSource;
/*      */ import net.minecraft.util.IChatComponent;
/*      */ import net.minecraft.util.JsonSerializableSet;
/*      */ import net.minecraft.util.MathHelper;
/*      */ import net.minecraft.village.MerchantRecipeList;
/*      */ import net.minecraft.world.ChunkCoordIntPair;
/*      */ import net.minecraft.world.IInteractionObject;
/*      */ import net.minecraft.world.ILockableContainer;
/*      */ import net.minecraft.world.WorldServer;
/*      */ import net.minecraft.world.WorldSettings;
/*      */ import net.minecraft.world.biome.BiomeGenBase;
/*      */ import net.minecraft.world.chunk.Chunk;
/*      */ 
/*      */ public class EntityPlayerMP extends EntityPlayer implements ICrafting {
/*   61 */   private static final Logger logger = LogManager.getLogger();
/*   62 */   private String translator = "en_US";
/*      */ 
/*      */   
/*      */   public NetHandlerPlayServer playerNetServerHandler;
/*      */ 
/*      */   
/*      */   public final MinecraftServer mcServer;
/*      */ 
/*      */   
/*      */   public final ItemInWorldManager theItemInWorldManager;
/*      */ 
/*      */   
/*      */   public double managedPosX;
/*      */ 
/*      */   
/*      */   public double managedPosZ;
/*      */ 
/*      */   
/*   80 */   public final List<ChunkCoordIntPair> loadedChunks = Lists.newLinkedList();
/*   81 */   private final List<Integer> destroyedItemsNetCache = Lists.newLinkedList();
/*      */ 
/*      */   
/*      */   private final StatisticsFile statsFile;
/*      */ 
/*      */   
/*   87 */   private float combinedHealth = Float.MIN_VALUE;
/*      */ 
/*      */   
/*   90 */   private float lastHealth = -1.0E8F;
/*      */ 
/*      */   
/*   93 */   private int lastFoodLevel = -99999999;
/*      */ 
/*      */   
/*      */   private boolean wasHungry = true;
/*      */ 
/*      */   
/*   99 */   private int lastExperience = -99999999;
/*  100 */   private int respawnInvulnerabilityTicks = 60;
/*      */   private EntityPlayer.EnumChatVisibility chatVisibility;
/*      */   private boolean chatColours = true;
/*  103 */   private long playerLastActiveTime = System.currentTimeMillis();
/*      */ 
/*      */   
/*  106 */   private Entity spectatingEntity = null;
/*      */ 
/*      */ 
/*      */   
/*      */   private int currentWindowId;
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isChangingQuantityOnly;
/*      */ 
/*      */ 
/*      */   
/*      */   public int ping;
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean playerConqueredTheEnd;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public EntityPlayerMP(MinecraftServer server, WorldServer worldIn, GameProfile profile, ItemInWorldManager interactionManager) {
/*  128 */     super((World)worldIn, profile);
/*  129 */     interactionManager.thisPlayerMP = this;
/*  130 */     this.theItemInWorldManager = interactionManager;
/*  131 */     BlockPos blockpos = worldIn.getSpawnPoint();
/*      */     
/*  133 */     if (!worldIn.provider.getHasNoSky() && worldIn.getWorldInfo().getGameType() != WorldSettings.GameType.ADVENTURE) {
/*      */       
/*  135 */       int i = Math.max(5, server.getSpawnProtectionSize() - 6);
/*  136 */       int j = MathHelper.floor_double(worldIn.getWorldBorder().getClosestDistance(blockpos.getX(), blockpos.getZ()));
/*      */       
/*  138 */       if (j < i)
/*      */       {
/*  140 */         i = j;
/*      */       }
/*      */       
/*  143 */       if (j <= 1)
/*      */       {
/*  145 */         i = 1;
/*      */       }
/*      */       
/*  148 */       blockpos = worldIn.getTopSolidOrLiquidBlock(blockpos.add(this.rand.nextInt(i << 1) - i, 0, this.rand.nextInt(i << 1) - i));
/*      */     } 
/*      */     
/*  151 */     this.mcServer = server;
/*  152 */     this.statsFile = server.getConfigurationManager().getPlayerStatsFile(this);
/*  153 */     this.stepHeight = 0.0F;
/*  154 */     moveToBlockPosAndAngles(blockpos, 0.0F, 0.0F);
/*      */     
/*  156 */     while (!worldIn.getCollidingBoundingBoxes((Entity)this, getEntityBoundingBox()).isEmpty() && this.posY < 255.0D)
/*      */     {
/*  158 */       setPosition(this.posX, this.posY + 1.0D, this.posZ);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void readEntityFromNBT(NBTTagCompound tagCompund) {
/*  167 */     super.readEntityFromNBT(tagCompund);
/*      */     
/*  169 */     if (tagCompund.hasKey("playerGameType", 99))
/*      */     {
/*  171 */       if (MinecraftServer.getServer().getForceGamemode()) {
/*      */         
/*  173 */         this.theItemInWorldManager.setGameType(MinecraftServer.getServer().getGameType());
/*      */       }
/*      */       else {
/*      */         
/*  177 */         this.theItemInWorldManager.setGameType(WorldSettings.GameType.getByID(tagCompund.getInteger("playerGameType")));
/*      */       } 
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void writeEntityToNBT(NBTTagCompound tagCompound) {
/*  187 */     super.writeEntityToNBT(tagCompound);
/*  188 */     tagCompound.setInteger("playerGameType", this.theItemInWorldManager.getGameType().getID());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void addExperienceLevel(int levels) {
/*  196 */     super.addExperienceLevel(levels);
/*  197 */     this.lastExperience = -1;
/*      */   }
/*      */ 
/*      */   
/*      */   public void removeExperienceLevel(int levels) {
/*  202 */     super.removeExperienceLevel(levels);
/*  203 */     this.lastExperience = -1;
/*      */   }
/*      */ 
/*      */   
/*      */   public void addSelfToInternalCraftingInventory() {
/*  208 */     this.openContainer.onCraftGuiOpened(this);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void sendEnterCombat() {
/*  216 */     super.sendEnterCombat();
/*  217 */     this.playerNetServerHandler.sendPacket((Packet)new S42PacketCombatEvent(getCombatTracker(), S42PacketCombatEvent.Event.ENTER_COMBAT));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void sendEndCombat() {
/*  225 */     super.sendEndCombat();
/*  226 */     this.playerNetServerHandler.sendPacket((Packet)new S42PacketCombatEvent(getCombatTracker(), S42PacketCombatEvent.Event.END_COMBAT));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void onUpdate() {
/*  234 */     this.theItemInWorldManager.updateBlockRemoving();
/*  235 */     this.respawnInvulnerabilityTicks--;
/*      */     
/*  237 */     if (this.hurtResistantTime > 0)
/*      */     {
/*  239 */       this.hurtResistantTime--;
/*      */     }
/*      */     
/*  242 */     this.openContainer.detectAndSendChanges();
/*      */     
/*  244 */     if (!this.worldObj.isRemote && !this.openContainer.canInteractWith(this)) {
/*      */       
/*  246 */       closeScreen();
/*  247 */       this.openContainer = this.inventoryContainer;
/*      */     } 
/*      */     
/*  250 */     while (!this.destroyedItemsNetCache.isEmpty()) {
/*      */       
/*  252 */       int i = Math.min(this.destroyedItemsNetCache.size(), 2147483647);
/*  253 */       int[] aint = new int[i];
/*  254 */       Iterator<Integer> iterator = this.destroyedItemsNetCache.iterator();
/*  255 */       int j = 0;
/*      */       
/*  257 */       while (iterator.hasNext() && j < i) {
/*      */         
/*  259 */         aint[j++] = ((Integer)iterator.next()).intValue();
/*  260 */         iterator.remove();
/*      */       } 
/*      */       
/*  263 */       this.playerNetServerHandler.sendPacket((Packet)new S13PacketDestroyEntities(aint));
/*      */     } 
/*      */     
/*  266 */     if (!this.loadedChunks.isEmpty()) {
/*      */       
/*  268 */       List<Chunk> list = Lists.newArrayList();
/*  269 */       Iterator<ChunkCoordIntPair> iterator1 = this.loadedChunks.iterator();
/*  270 */       List<TileEntity> list1 = Lists.newArrayList();
/*      */       
/*  272 */       while (iterator1.hasNext() && list.size() < 10) {
/*      */         
/*  274 */         ChunkCoordIntPair chunkcoordintpair = iterator1.next();
/*      */         
/*  276 */         if (chunkcoordintpair != null) {
/*      */           
/*  278 */           if (this.worldObj.isBlockLoaded(new BlockPos(chunkcoordintpair.chunkXPos << 4, 0, chunkcoordintpair.chunkZPos << 4))) {
/*      */             
/*  280 */             Chunk chunk = this.worldObj.getChunkFromChunkCoords(chunkcoordintpair.chunkXPos, chunkcoordintpair.chunkZPos);
/*      */             
/*  282 */             if (chunk.isPopulated()) {
/*      */               
/*  284 */               list.add(chunk);
/*  285 */               list1.addAll(((WorldServer)this.worldObj).getTileEntitiesIn(chunkcoordintpair.chunkXPos << 4, 0, chunkcoordintpair.chunkZPos << 4, (chunkcoordintpair.chunkXPos << 4) + 16, 256, (chunkcoordintpair.chunkZPos << 4) + 16));
/*  286 */               iterator1.remove();
/*      */             } 
/*      */           } 
/*      */           
/*      */           continue;
/*      */         } 
/*  292 */         iterator1.remove();
/*      */       } 
/*      */ 
/*      */       
/*  296 */       if (!list.isEmpty()) {
/*      */         
/*  298 */         if (list.size() == 1) {
/*      */           
/*  300 */           this.playerNetServerHandler.sendPacket((Packet)new S21PacketChunkData(list.get(0), true, 65535));
/*      */         }
/*      */         else {
/*      */           
/*  304 */           this.playerNetServerHandler.sendPacket((Packet)new S26PacketMapChunkBulk(list));
/*      */         } 
/*      */         
/*  307 */         for (TileEntity tileentity : list1)
/*      */         {
/*  309 */           sendTileEntityUpdate(tileentity);
/*      */         }
/*      */         
/*  312 */         for (Chunk chunk1 : list)
/*      */         {
/*  314 */           getServerForPlayer().getEntityTracker().func_85172_a(this, chunk1);
/*      */         }
/*      */       } 
/*      */     } 
/*      */     
/*  319 */     Entity entity = getSpectatingEntity();
/*      */     
/*  321 */     if (entity != this)
/*      */     {
/*  323 */       if (!entity.isEntityAlive()) {
/*      */         
/*  325 */         setSpectatingEntity((Entity)this);
/*      */       }
/*      */       else {
/*      */         
/*  329 */         setPositionAndRotation(entity.posX, entity.posY, entity.posZ, entity.rotationYaw, entity.rotationPitch);
/*  330 */         this.mcServer.getConfigurationManager().serverUpdateMountedMovingPlayer(this);
/*      */         
/*  332 */         if (isSneaking())
/*      */         {
/*  334 */           setSpectatingEntity((Entity)this);
/*      */         }
/*      */       } 
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public void onUpdateEntity() {
/*      */     try {
/*  344 */       super.onUpdate();
/*      */       
/*  346 */       for (int i = 0; i < this.inventory.getSizeInventory(); i++) {
/*      */         
/*  348 */         ItemStack itemstack = this.inventory.getStackInSlot(i);
/*      */         
/*  350 */         if (itemstack != null && itemstack.getItem().isMap()) {
/*      */           
/*  352 */           Packet packet = ((ItemMapBase)itemstack.getItem()).createMapDataPacket(itemstack, this.worldObj, this);
/*      */           
/*  354 */           if (packet != null)
/*      */           {
/*  356 */             this.playerNetServerHandler.sendPacket(packet);
/*      */           }
/*      */         } 
/*      */       } 
/*      */       
/*  361 */       if (getHealth() != this.lastHealth || this.lastFoodLevel != this.foodStats.getFoodLevel() || ((this.foodStats.getSaturationLevel() == 0.0F)) != this.wasHungry) {
/*      */         
/*  363 */         this.playerNetServerHandler.sendPacket((Packet)new S06PacketUpdateHealth(getHealth(), this.foodStats.getFoodLevel(), this.foodStats.getSaturationLevel()));
/*  364 */         this.lastHealth = getHealth();
/*  365 */         this.lastFoodLevel = this.foodStats.getFoodLevel();
/*  366 */         this.wasHungry = (this.foodStats.getSaturationLevel() == 0.0F);
/*      */       } 
/*      */       
/*  369 */       if (getHealth() + getAbsorptionAmount() != this.combinedHealth) {
/*      */         
/*  371 */         this.combinedHealth = getHealth() + getAbsorptionAmount();
/*      */         
/*  373 */         for (ScoreObjective scoreobjective : getWorldScoreboard().getObjectivesFromCriteria(IScoreObjectiveCriteria.health)) {
/*      */           
/*  375 */           getWorldScoreboard().getValueFromObjective(getName(), scoreobjective).func_96651_a(Arrays.asList(new EntityPlayer[] { this }));
/*      */         } 
/*      */       } 
/*      */       
/*  379 */       if (this.experienceTotal != this.lastExperience) {
/*      */         
/*  381 */         this.lastExperience = this.experienceTotal;
/*  382 */         this.playerNetServerHandler.sendPacket((Packet)new S1FPacketSetExperience(this.experience, this.experienceTotal, this.experienceLevel));
/*      */       } 
/*      */       
/*  385 */       if (this.ticksExisted % 20 * 5 == 0 && !this.statsFile.hasAchievementUnlocked(AchievementList.exploreAllBiomes))
/*      */       {
/*  387 */         updateBiomesExplored();
/*      */       }
/*      */     }
/*  390 */     catch (Throwable throwable) {
/*      */       
/*  392 */       CrashReport crashreport = CrashReport.makeCrashReport(throwable, "Ticking player");
/*  393 */       CrashReportCategory crashreportcategory = crashreport.makeCategory("Player being ticked");
/*  394 */       addEntityCrashInfo(crashreportcategory);
/*  395 */       throw new ReportedException(crashreport);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void updateBiomesExplored() {
/*  404 */     BiomeGenBase biomegenbase = this.worldObj.getBiomeGenForCoords(new BlockPos(MathHelper.floor_double(this.posX), 0, MathHelper.floor_double(this.posZ)));
/*  405 */     String s = biomegenbase.biomeName;
/*  406 */     JsonSerializableSet jsonserializableset = (JsonSerializableSet)this.statsFile.func_150870_b((StatBase)AchievementList.exploreAllBiomes);
/*      */     
/*  408 */     if (jsonserializableset == null)
/*      */     {
/*  410 */       jsonserializableset = (JsonSerializableSet)this.statsFile.func_150872_a((StatBase)AchievementList.exploreAllBiomes, (IJsonSerializable)new JsonSerializableSet());
/*      */     }
/*      */     
/*  413 */     jsonserializableset.add(s);
/*      */     
/*  415 */     if (this.statsFile.canUnlockAchievement(AchievementList.exploreAllBiomes) && jsonserializableset.size() >= BiomeGenBase.explorationBiomesList.size()) {
/*      */       
/*  417 */       Set<BiomeGenBase> set = Sets.newHashSet(BiomeGenBase.explorationBiomesList);
/*      */       
/*  419 */       for (String s1 : jsonserializableset) {
/*      */         
/*  421 */         Iterator<BiomeGenBase> iterator = set.iterator();
/*      */         
/*  423 */         while (iterator.hasNext()) {
/*      */           
/*  425 */           BiomeGenBase biomegenbase1 = iterator.next();
/*      */           
/*  427 */           if (biomegenbase1.biomeName.equals(s1))
/*      */           {
/*  429 */             iterator.remove();
/*      */           }
/*      */         } 
/*      */         
/*  433 */         if (set.isEmpty()) {
/*      */           break;
/*      */         }
/*      */       } 
/*      */ 
/*      */       
/*  439 */       if (set.isEmpty())
/*      */       {
/*  441 */         triggerAchievement((StatBase)AchievementList.exploreAllBiomes);
/*      */       }
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void onDeath(DamageSource cause) {
/*  451 */     if (this.worldObj.getGameRules().getBoolean("showDeathMessages")) {
/*      */       
/*  453 */       Team team = getTeam();
/*      */       
/*  455 */       if (team != null && team.getDeathMessageVisibility() != Team.EnumVisible.ALWAYS) {
/*      */         
/*  457 */         if (team.getDeathMessageVisibility() == Team.EnumVisible.HIDE_FOR_OTHER_TEAMS)
/*      */         {
/*  459 */           this.mcServer.getConfigurationManager().sendMessageToAllTeamMembers(this, getCombatTracker().getDeathMessage());
/*      */         }
/*  461 */         else if (team.getDeathMessageVisibility() == Team.EnumVisible.HIDE_FOR_OWN_TEAM)
/*      */         {
/*  463 */           this.mcServer.getConfigurationManager().sendMessageToTeamOrEvryPlayer(this, getCombatTracker().getDeathMessage());
/*      */         }
/*      */       
/*      */       } else {
/*      */         
/*  468 */         this.mcServer.getConfigurationManager().sendChatMsg(getCombatTracker().getDeathMessage());
/*      */       } 
/*      */     } 
/*      */     
/*  472 */     if (!this.worldObj.getGameRules().getBoolean("keepInventory"))
/*      */     {
/*  474 */       this.inventory.dropAllItems();
/*      */     }
/*      */     
/*  477 */     for (ScoreObjective scoreobjective : this.worldObj.getScoreboard().getObjectivesFromCriteria(IScoreObjectiveCriteria.deathCount)) {
/*      */       
/*  479 */       Score score = getWorldScoreboard().getValueFromObjective(getName(), scoreobjective);
/*  480 */       score.func_96648_a();
/*      */     } 
/*      */     
/*  483 */     EntityLivingBase entitylivingbase = getAttackingEntity();
/*      */     
/*  485 */     if (entitylivingbase != null) {
/*      */       
/*  487 */       EntityList.EntityEggInfo entitylist$entityegginfo = (EntityList.EntityEggInfo)EntityList.entityEggs.get(Integer.valueOf(EntityList.getEntityID((Entity)entitylivingbase)));
/*      */       
/*  489 */       if (entitylist$entityegginfo != null)
/*      */       {
/*  491 */         triggerAchievement(entitylist$entityegginfo.field_151513_e);
/*      */       }
/*      */       
/*  494 */       entitylivingbase.addToPlayerScore((Entity)this, this.scoreValue);
/*      */     } 
/*      */     
/*  497 */     triggerAchievement(StatList.deathsStat);
/*  498 */     func_175145_a(StatList.timeSinceDeathStat);
/*  499 */     getCombatTracker().reset();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean attackEntityFrom(DamageSource source, float amount) {
/*  507 */     if (isEntityInvulnerable(source))
/*      */     {
/*  509 */       return false;
/*      */     }
/*      */ 
/*      */     
/*  513 */     boolean flag = (this.mcServer.isDedicatedServer() && canPlayersAttack() && "fall".equals(source.damageType));
/*      */     
/*  515 */     if (!flag && this.respawnInvulnerabilityTicks > 0 && source != DamageSource.outOfWorld)
/*      */     {
/*  517 */       return false;
/*      */     }
/*      */ 
/*      */     
/*  521 */     if (source instanceof net.minecraft.util.EntityDamageSource) {
/*      */       
/*  523 */       Entity entity = source.getEntity();
/*      */       
/*  525 */       if (entity instanceof EntityPlayer && !canAttackPlayer((EntityPlayer)entity))
/*      */       {
/*  527 */         return false;
/*      */       }
/*      */       
/*  530 */       if (entity instanceof EntityArrow) {
/*      */         
/*  532 */         EntityArrow entityarrow = (EntityArrow)entity;
/*      */         
/*  534 */         if (entityarrow.shootingEntity instanceof EntityPlayer && !canAttackPlayer((EntityPlayer)entityarrow.shootingEntity))
/*      */         {
/*  536 */           return false;
/*      */         }
/*      */       } 
/*      */     } 
/*      */     
/*  541 */     return super.attackEntityFrom(source, amount);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean canAttackPlayer(EntityPlayer other) {
/*  548 */     return !canPlayersAttack() ? false : super.canAttackPlayer(other);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean canPlayersAttack() {
/*  556 */     return this.mcServer.isPVPEnabled();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void travelToDimension(int dimensionId) {
/*  564 */     if (this.dimension == 1 && dimensionId == 1) {
/*      */       
/*  566 */       triggerAchievement((StatBase)AchievementList.theEnd2);
/*  567 */       this.worldObj.removeEntity((Entity)this);
/*  568 */       this.playerConqueredTheEnd = true;
/*  569 */       this.playerNetServerHandler.sendPacket((Packet)new S2BPacketChangeGameState(4, 0.0F));
/*      */     }
/*      */     else {
/*      */       
/*  573 */       if (this.dimension == 0 && dimensionId == 1) {
/*      */         
/*  575 */         triggerAchievement((StatBase)AchievementList.theEnd);
/*  576 */         BlockPos blockpos = this.mcServer.worldServerForDimension(dimensionId).getSpawnCoordinate();
/*      */         
/*  578 */         if (blockpos != null)
/*      */         {
/*  580 */           this.playerNetServerHandler.setPlayerLocation(blockpos.getX(), blockpos.getY(), blockpos.getZ(), 0.0F, 0.0F);
/*      */         }
/*      */         
/*  583 */         dimensionId = 1;
/*      */       }
/*      */       else {
/*      */         
/*  587 */         triggerAchievement((StatBase)AchievementList.portal);
/*      */       } 
/*      */       
/*  590 */       this.mcServer.getConfigurationManager().transferPlayerToDimension(this, dimensionId);
/*  591 */       this.lastExperience = -1;
/*  592 */       this.lastHealth = -1.0F;
/*  593 */       this.lastFoodLevel = -1;
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean isSpectatedByPlayer(EntityPlayerMP player) {
/*  599 */     return player.isSpectator() ? ((getSpectatingEntity() == this)) : (isSpectator() ? false : super.isSpectatedByPlayer(player));
/*      */   }
/*      */ 
/*      */   
/*      */   private void sendTileEntityUpdate(TileEntity p_147097_1_) {
/*  604 */     if (p_147097_1_ != null) {
/*      */       
/*  606 */       Packet packet = p_147097_1_.getDescriptionPacket();
/*      */       
/*  608 */       if (packet != null)
/*      */       {
/*  610 */         this.playerNetServerHandler.sendPacket(packet);
/*      */       }
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void onItemPickup(Entity p_71001_1_, int p_71001_2_) {
/*  620 */     super.onItemPickup(p_71001_1_, p_71001_2_);
/*  621 */     this.openContainer.detectAndSendChanges();
/*      */   }
/*      */ 
/*      */   
/*      */   public EntityPlayer.EnumStatus trySleep(BlockPos bedLocation) {
/*  626 */     EntityPlayer.EnumStatus entityplayer$enumstatus = super.trySleep(bedLocation);
/*      */     
/*  628 */     if (entityplayer$enumstatus == EntityPlayer.EnumStatus.OK) {
/*      */       
/*  630 */       S0APacketUseBed s0APacketUseBed = new S0APacketUseBed(this, bedLocation);
/*  631 */       getServerForPlayer().getEntityTracker().sendToAllTrackingEntity((Entity)this, (Packet)s0APacketUseBed);
/*  632 */       this.playerNetServerHandler.setPlayerLocation(this.posX, this.posY, this.posZ, this.rotationYaw, this.rotationPitch);
/*  633 */       this.playerNetServerHandler.sendPacket((Packet)s0APacketUseBed);
/*      */     } 
/*      */     
/*  636 */     return entityplayer$enumstatus;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void wakeUpPlayer(boolean immediately, boolean updateWorldFlag, boolean setSpawn) {
/*  644 */     if (isPlayerSleeping())
/*      */     {
/*  646 */       getServerForPlayer().getEntityTracker().func_151248_b((Entity)this, (Packet)new S0BPacketAnimation((Entity)this, 2));
/*      */     }
/*      */     
/*  649 */     super.wakeUpPlayer(immediately, updateWorldFlag, setSpawn);
/*      */     
/*  651 */     if (this.playerNetServerHandler != null)
/*      */     {
/*  653 */       this.playerNetServerHandler.setPlayerLocation(this.posX, this.posY, this.posZ, this.rotationYaw, this.rotationPitch);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void mountEntity(Entity entityIn) {
/*  662 */     Entity entity = this.ridingEntity;
/*  663 */     super.mountEntity(entityIn);
/*      */     
/*  665 */     if (entityIn != entity) {
/*      */       
/*  667 */       this.playerNetServerHandler.sendPacket((Packet)new S1BPacketEntityAttach(0, (Entity)this, this.ridingEntity));
/*  668 */       this.playerNetServerHandler.setPlayerLocation(this.posX, this.posY, this.posZ, this.rotationYaw, this.rotationPitch);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void updateFallState(double y, boolean onGroundIn, Block blockIn, BlockPos pos) {}
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void handleFalling(double p_71122_1_, boolean p_71122_3_) {
/*  681 */     int i = MathHelper.floor_double(this.posX);
/*  682 */     int j = MathHelper.floor_double(this.posY - 0.20000000298023224D);
/*  683 */     int k = MathHelper.floor_double(this.posZ);
/*  684 */     BlockPos blockpos = new BlockPos(i, j, k);
/*  685 */     Block block = this.worldObj.getBlockState(blockpos).getBlock();
/*      */     
/*  687 */     if (block.getMaterial() == Material.air) {
/*      */       
/*  689 */       Block block1 = this.worldObj.getBlockState(blockpos.down()).getBlock();
/*      */       
/*  691 */       if (block1 instanceof net.minecraft.block.BlockFence || block1 instanceof net.minecraft.block.BlockWall || block1 instanceof net.minecraft.block.BlockFenceGate) {
/*      */         
/*  693 */         blockpos = blockpos.down();
/*  694 */         block = this.worldObj.getBlockState(blockpos).getBlock();
/*      */       } 
/*      */     } 
/*      */     
/*  698 */     super.updateFallState(p_71122_1_, p_71122_3_, block, blockpos);
/*      */   }
/*      */ 
/*      */   
/*      */   public void openEditSign(TileEntitySign signTile) {
/*  703 */     signTile.setPlayer(this);
/*  704 */     this.playerNetServerHandler.sendPacket((Packet)new S36PacketSignEditorOpen(signTile.getPos()));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void getNextWindowId() {
/*  712 */     this.currentWindowId = this.currentWindowId % 100 + 1;
/*      */   }
/*      */ 
/*      */   
/*      */   public void displayGui(IInteractionObject guiOwner) {
/*  717 */     getNextWindowId();
/*  718 */     this.playerNetServerHandler.sendPacket((Packet)new S2DPacketOpenWindow(this.currentWindowId, guiOwner.getGuiID(), guiOwner.getDisplayName()));
/*  719 */     this.openContainer = guiOwner.createContainer(this.inventory, this);
/*  720 */     this.openContainer.windowId = this.currentWindowId;
/*  721 */     this.openContainer.onCraftGuiOpened(this);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void displayGUIChest(IInventory chestInventory) {
/*  729 */     if (this.openContainer != this.inventoryContainer)
/*      */     {
/*  731 */       closeScreen();
/*      */     }
/*      */     
/*  734 */     if (chestInventory instanceof ILockableContainer) {
/*      */       
/*  736 */       ILockableContainer ilockablecontainer = (ILockableContainer)chestInventory;
/*      */       
/*  738 */       if (ilockablecontainer.isLocked() && !canOpen(ilockablecontainer.getLockCode()) && !isSpectator()) {
/*      */         
/*  740 */         this.playerNetServerHandler.sendPacket((Packet)new S02PacketChat((IChatComponent)new ChatComponentTranslation("container.isLocked", new Object[] { chestInventory.getDisplayName() }), (byte)2));
/*  741 */         this.playerNetServerHandler.sendPacket((Packet)new S29PacketSoundEffect("random.door_close", this.posX, this.posY, this.posZ, 1.0F, 1.0F));
/*      */         
/*      */         return;
/*      */       } 
/*      */     } 
/*  746 */     getNextWindowId();
/*      */     
/*  748 */     if (chestInventory instanceof IInteractionObject) {
/*      */       
/*  750 */       this.playerNetServerHandler.sendPacket((Packet)new S2DPacketOpenWindow(this.currentWindowId, ((IInteractionObject)chestInventory).getGuiID(), chestInventory.getDisplayName(), chestInventory.getSizeInventory()));
/*  751 */       this.openContainer = ((IInteractionObject)chestInventory).createContainer(this.inventory, this);
/*      */     }
/*      */     else {
/*      */       
/*  755 */       this.playerNetServerHandler.sendPacket((Packet)new S2DPacketOpenWindow(this.currentWindowId, "minecraft:container", chestInventory.getDisplayName(), chestInventory.getSizeInventory()));
/*  756 */       this.openContainer = (Container)new ContainerChest(this.inventory, chestInventory, this);
/*      */     } 
/*      */     
/*  759 */     this.openContainer.windowId = this.currentWindowId;
/*  760 */     this.openContainer.onCraftGuiOpened(this);
/*      */   }
/*      */ 
/*      */   
/*      */   public void displayVillagerTradeGui(IMerchant villager) {
/*  765 */     getNextWindowId();
/*  766 */     this.openContainer = (Container)new ContainerMerchant(this.inventory, villager, this.worldObj);
/*  767 */     this.openContainer.windowId = this.currentWindowId;
/*  768 */     this.openContainer.onCraftGuiOpened(this);
/*  769 */     InventoryMerchant inventoryMerchant = ((ContainerMerchant)this.openContainer).getMerchantInventory();
/*  770 */     IChatComponent ichatcomponent = villager.getDisplayName();
/*  771 */     this.playerNetServerHandler.sendPacket((Packet)new S2DPacketOpenWindow(this.currentWindowId, "minecraft:villager", ichatcomponent, inventoryMerchant.getSizeInventory()));
/*  772 */     MerchantRecipeList merchantrecipelist = villager.getRecipes(this);
/*      */     
/*  774 */     if (merchantrecipelist != null) {
/*      */       
/*  776 */       PacketBuffer packetbuffer = new PacketBuffer(Unpooled.buffer());
/*  777 */       packetbuffer.writeInt(this.currentWindowId);
/*  778 */       merchantrecipelist.writeToBuf(packetbuffer);
/*  779 */       this.playerNetServerHandler.sendPacket((Packet)new S3FPacketCustomPayload("MC|TrList", packetbuffer));
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public void displayGUIHorse(EntityHorse horse, IInventory horseInventory) {
/*  785 */     if (this.openContainer != this.inventoryContainer)
/*      */     {
/*  787 */       closeScreen();
/*      */     }
/*      */     
/*  790 */     getNextWindowId();
/*  791 */     this.playerNetServerHandler.sendPacket((Packet)new S2DPacketOpenWindow(this.currentWindowId, "EntityHorse", horseInventory.getDisplayName(), horseInventory.getSizeInventory(), horse.getEntityId()));
/*  792 */     this.openContainer = (Container)new ContainerHorseInventory(this.inventory, horseInventory, horse, this);
/*  793 */     this.openContainer.windowId = this.currentWindowId;
/*  794 */     this.openContainer.onCraftGuiOpened(this);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void displayGUIBook(ItemStack bookStack) {
/*  802 */     Item item = bookStack.getItem();
/*      */     
/*  804 */     if (item == Items.written_book)
/*      */     {
/*  806 */       this.playerNetServerHandler.sendPacket((Packet)new S3FPacketCustomPayload("MC|BOpen", new PacketBuffer(Unpooled.buffer())));
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void sendSlotContents(Container containerToSend, int slotInd, ItemStack stack) {
/*  816 */     if (!(containerToSend.getSlot(slotInd) instanceof net.minecraft.inventory.SlotCrafting))
/*      */     {
/*  818 */       if (!this.isChangingQuantityOnly)
/*      */       {
/*  820 */         this.playerNetServerHandler.sendPacket((Packet)new S2FPacketSetSlot(containerToSend.windowId, slotInd, stack));
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   public void sendContainerToPlayer(Container p_71120_1_) {
/*  827 */     updateCraftingInventory(p_71120_1_, p_71120_1_.getInventory());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void updateCraftingInventory(Container containerToSend, List<ItemStack> itemsList) {
/*  835 */     this.playerNetServerHandler.sendPacket((Packet)new S30PacketWindowItems(containerToSend.windowId, itemsList));
/*  836 */     this.playerNetServerHandler.sendPacket((Packet)new S2FPacketSetSlot(-1, -1, this.inventory.getItemStack()));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void sendProgressBarUpdate(Container containerIn, int varToUpdate, int newValue) {
/*  846 */     this.playerNetServerHandler.sendPacket((Packet)new S31PacketWindowProperty(containerIn.windowId, varToUpdate, newValue));
/*      */   }
/*      */ 
/*      */   
/*      */   public void sendAllWindowProperties(Container p_175173_1_, IInventory p_175173_2_) {
/*  851 */     for (int i = 0; i < p_175173_2_.getFieldCount(); i++)
/*      */     {
/*  853 */       this.playerNetServerHandler.sendPacket((Packet)new S31PacketWindowProperty(p_175173_1_.windowId, i, p_175173_2_.getField(i)));
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void closeScreen() {
/*  862 */     this.playerNetServerHandler.sendPacket((Packet)new S2EPacketCloseWindow(this.openContainer.windowId));
/*  863 */     closeContainer();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void updateHeldItem() {
/*  871 */     if (!this.isChangingQuantityOnly)
/*      */     {
/*  873 */       this.playerNetServerHandler.sendPacket((Packet)new S2FPacketSetSlot(-1, -1, this.inventory.getItemStack()));
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void closeContainer() {
/*  882 */     this.openContainer.onContainerClosed(this);
/*  883 */     this.openContainer = this.inventoryContainer;
/*      */   }
/*      */ 
/*      */   
/*      */   public void setEntityActionState(float p_110430_1_, float p_110430_2_, boolean p_110430_3_, boolean sneaking) {
/*  888 */     if (this.ridingEntity != null) {
/*      */       
/*  890 */       if (p_110430_1_ >= -1.0F && p_110430_1_ <= 1.0F)
/*      */       {
/*  892 */         this.moveStrafing = p_110430_1_;
/*      */       }
/*      */       
/*  895 */       if (p_110430_2_ >= -1.0F && p_110430_2_ <= 1.0F)
/*      */       {
/*  897 */         this.moveForward = p_110430_2_;
/*      */       }
/*      */       
/*  900 */       this.isJumping = p_110430_3_;
/*  901 */       setSneaking(sneaking);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void addStat(StatBase stat, int amount) {
/*  910 */     if (stat != null) {
/*      */       
/*  912 */       this.statsFile.increaseStat(this, stat, amount);
/*      */       
/*  914 */       for (ScoreObjective scoreobjective : getWorldScoreboard().getObjectivesFromCriteria(stat.getCriteria()))
/*      */       {
/*  916 */         getWorldScoreboard().getValueFromObjective(getName(), scoreobjective).increseScore(amount);
/*      */       }
/*      */       
/*  919 */       if (this.statsFile.func_150879_e())
/*      */       {
/*  921 */         this.statsFile.func_150876_a(this);
/*      */       }
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public void func_175145_a(StatBase p_175145_1_) {
/*  928 */     if (p_175145_1_ != null) {
/*      */       
/*  930 */       this.statsFile.unlockAchievement(this, p_175145_1_, 0);
/*      */       
/*  932 */       for (ScoreObjective scoreobjective : getWorldScoreboard().getObjectivesFromCriteria(p_175145_1_.getCriteria()))
/*      */       {
/*  934 */         getWorldScoreboard().getValueFromObjective(getName(), scoreobjective).setScorePoints(0);
/*      */       }
/*      */       
/*  937 */       if (this.statsFile.func_150879_e())
/*      */       {
/*  939 */         this.statsFile.func_150876_a(this);
/*      */       }
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public void mountEntityAndWakeUp() {
/*  946 */     if (this.riddenByEntity != null)
/*      */     {
/*  948 */       this.riddenByEntity.mountEntity((Entity)this);
/*      */     }
/*      */     
/*  951 */     if (this.sleeping)
/*      */     {
/*  953 */       wakeUpPlayer(true, false, false);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setPlayerHealthUpdated() {
/*  963 */     this.lastHealth = -1.0E8F;
/*      */   }
/*      */ 
/*      */   
/*      */   public void addChatComponentMessage(IChatComponent chatComponent) {
/*  968 */     this.playerNetServerHandler.sendPacket((Packet)new S02PacketChat(chatComponent));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void onItemUseFinish() {
/*  976 */     this.playerNetServerHandler.sendPacket((Packet)new S19PacketEntityStatus((Entity)this, (byte)9));
/*  977 */     super.onItemUseFinish();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setItemInUse(ItemStack stack, int duration) {
/*  985 */     super.setItemInUse(stack, duration);
/*      */     
/*  987 */     if (stack != null && stack.getItem() != null && stack.getItem().getItemUseAction(stack) == EnumAction.EAT)
/*      */     {
/*  989 */       getServerForPlayer().getEntityTracker().func_151248_b((Entity)this, (Packet)new S0BPacketAnimation((Entity)this, 3));
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void clonePlayer(EntityPlayer oldPlayer, boolean respawnFromEnd) {
/*  999 */     super.clonePlayer(oldPlayer, respawnFromEnd);
/* 1000 */     this.lastExperience = -1;
/* 1001 */     this.lastHealth = -1.0F;
/* 1002 */     this.lastFoodLevel = -1;
/* 1003 */     this.destroyedItemsNetCache.addAll(((EntityPlayerMP)oldPlayer).destroyedItemsNetCache);
/*      */   }
/*      */ 
/*      */   
/*      */   protected void onNewPotionEffect(PotionEffect id) {
/* 1008 */     super.onNewPotionEffect(id);
/* 1009 */     this.playerNetServerHandler.sendPacket((Packet)new S1DPacketEntityEffect(getEntityId(), id));
/*      */   }
/*      */ 
/*      */   
/*      */   protected void onChangedPotionEffect(PotionEffect id, boolean p_70695_2_) {
/* 1014 */     super.onChangedPotionEffect(id, p_70695_2_);
/* 1015 */     this.playerNetServerHandler.sendPacket((Packet)new S1DPacketEntityEffect(getEntityId(), id));
/*      */   }
/*      */ 
/*      */   
/*      */   protected void onFinishedPotionEffect(PotionEffect effect) {
/* 1020 */     super.onFinishedPotionEffect(effect);
/* 1021 */     this.playerNetServerHandler.sendPacket((Packet)new S1EPacketRemoveEntityEffect(getEntityId(), effect));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setPositionAndUpdate(double x, double y, double z) {
/* 1029 */     this.playerNetServerHandler.setPlayerLocation(x, y, z, this.rotationYaw, this.rotationPitch);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void onCriticalHit(Entity entityHit) {
/* 1037 */     getServerForPlayer().getEntityTracker().func_151248_b((Entity)this, (Packet)new S0BPacketAnimation(entityHit, 4));
/*      */   }
/*      */ 
/*      */   
/*      */   public void onEnchantmentCritical(Entity entityHit) {
/* 1042 */     getServerForPlayer().getEntityTracker().func_151248_b((Entity)this, (Packet)new S0BPacketAnimation(entityHit, 5));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void sendPlayerAbilities() {
/* 1050 */     if (this.playerNetServerHandler != null) {
/*      */       
/* 1052 */       this.playerNetServerHandler.sendPacket((Packet)new S39PacketPlayerAbilities(this.capabilities));
/* 1053 */       updatePotionMetadata();
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public WorldServer getServerForPlayer() {
/* 1059 */     return (WorldServer)this.worldObj;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setGameType(WorldSettings.GameType gameType) {
/* 1067 */     this.theItemInWorldManager.setGameType(gameType);
/* 1068 */     this.playerNetServerHandler.sendPacket((Packet)new S2BPacketChangeGameState(3, gameType.getID()));
/*      */     
/* 1070 */     if (gameType == WorldSettings.GameType.SPECTATOR) {
/*      */       
/* 1072 */       mountEntity((Entity)null);
/*      */     }
/*      */     else {
/*      */       
/* 1076 */       setSpectatingEntity((Entity)this);
/*      */     } 
/*      */     
/* 1079 */     sendPlayerAbilities();
/* 1080 */     markPotionsDirty();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isSpectator() {
/* 1088 */     return (this.theItemInWorldManager.getGameType() == WorldSettings.GameType.SPECTATOR);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void addChatMessage(IChatComponent component) {
/* 1096 */     this.playerNetServerHandler.sendPacket((Packet)new S02PacketChat(component));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean canCommandSenderUseCommand(int permLevel, String commandName) {
/* 1104 */     if ("seed".equals(commandName) && !this.mcServer.isDedicatedServer())
/*      */     {
/* 1106 */       return true;
/*      */     }
/* 1108 */     if (!"tell".equals(commandName) && !"help".equals(commandName) && !"me".equals(commandName) && !"trigger".equals(commandName)) {
/*      */       
/* 1110 */       if (this.mcServer.getConfigurationManager().canSendCommands(getGameProfile())) {
/*      */         
/* 1112 */         UserListOpsEntry userlistopsentry = (UserListOpsEntry)this.mcServer.getConfigurationManager().getOppedPlayers().getEntry(getGameProfile());
/* 1113 */         return (userlistopsentry != null) ? ((userlistopsentry.getPermissionLevel() >= permLevel)) : ((this.mcServer.getOpPermissionLevel() >= permLevel));
/*      */       } 
/*      */ 
/*      */       
/* 1117 */       return false;
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/* 1122 */     return true;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getPlayerIP() {
/* 1131 */     String s = this.playerNetServerHandler.netManager.getRemoteAddress().toString();
/* 1132 */     s = s.substring(s.indexOf('/') + 1);
/* 1133 */     s = s.substring(0, s.indexOf(':'));
/* 1134 */     return s;
/*      */   }
/*      */ 
/*      */   
/*      */   public void handleClientSettings(C15PacketClientSettings packetIn) {
/* 1139 */     this.translator = packetIn.getLang();
/* 1140 */     this.chatVisibility = packetIn.getChatVisibility();
/* 1141 */     this.chatColours = packetIn.isColorsEnabled();
/* 1142 */     getDataWatcher().updateObject(10, Byte.valueOf((byte)packetIn.getModelPartFlags()));
/*      */   }
/*      */ 
/*      */   
/*      */   public EntityPlayer.EnumChatVisibility getChatVisibility() {
/* 1147 */     return this.chatVisibility;
/*      */   }
/*      */ 
/*      */   
/*      */   public void loadResourcePack(String url, String hash) {
/* 1152 */     this.playerNetServerHandler.sendPacket((Packet)new S48PacketResourcePackSend(url, hash));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public BlockPos getPosition() {
/* 1161 */     return new BlockPos(this.posX, this.posY + 0.5D, this.posZ);
/*      */   }
/*      */ 
/*      */   
/*      */   public void markPlayerActive() {
/* 1166 */     this.playerLastActiveTime = MinecraftServer.getCurrentTimeMillis();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public StatisticsFile getStatFile() {
/* 1174 */     return this.statsFile;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void removeEntity(Entity p_152339_1_) {
/* 1182 */     if (p_152339_1_ instanceof EntityPlayer) {
/*      */       
/* 1184 */       this.playerNetServerHandler.sendPacket((Packet)new S13PacketDestroyEntities(new int[] { p_152339_1_.getEntityId() }));
/*      */     }
/*      */     else {
/*      */       
/* 1188 */       this.destroyedItemsNetCache.add(Integer.valueOf(p_152339_1_.getEntityId()));
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void updatePotionMetadata() {
/* 1198 */     if (isSpectator()) {
/*      */       
/* 1200 */       resetPotionEffectMetadata();
/* 1201 */       setInvisible(true);
/*      */     }
/*      */     else {
/*      */       
/* 1205 */       super.updatePotionMetadata();
/*      */     } 
/*      */     
/* 1208 */     getServerForPlayer().getEntityTracker().func_180245_a(this);
/*      */   }
/*      */ 
/*      */   
/*      */   public Entity getSpectatingEntity() {
/* 1213 */     return (this.spectatingEntity == null) ? (Entity)this : this.spectatingEntity;
/*      */   }
/*      */ 
/*      */   
/*      */   public void setSpectatingEntity(Entity entityToSpectate) {
/* 1218 */     Entity entity = getSpectatingEntity();
/* 1219 */     this.spectatingEntity = (entityToSpectate == null) ? (Entity)this : entityToSpectate;
/*      */     
/* 1221 */     if (entity != this.spectatingEntity) {
/*      */       
/* 1223 */       this.playerNetServerHandler.sendPacket((Packet)new S43PacketCamera(this.spectatingEntity));
/* 1224 */       setPositionAndUpdate(this.spectatingEntity.posX, this.spectatingEntity.posY, this.spectatingEntity.posZ);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void attackTargetEntityWithCurrentItem(Entity targetEntity) {
/* 1234 */     if (this.theItemInWorldManager.getGameType() == WorldSettings.GameType.SPECTATOR) {
/*      */       
/* 1236 */       setSpectatingEntity(targetEntity);
/*      */     }
/*      */     else {
/*      */       
/* 1240 */       super.attackTargetEntityWithCurrentItem(targetEntity);
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public long getLastActiveTime() {
/* 1246 */     return this.playerLastActiveTime;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public IChatComponent getTabListDisplayName() {
/* 1255 */     return null;
/*      */   }
/*      */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\minecraft\entity\player\EntityPlayerMP.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */