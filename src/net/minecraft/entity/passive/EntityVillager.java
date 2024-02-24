/*      */ package net.minecraft.entity.passive;
/*      */ import java.util.Random;
/*      */ import net.minecraft.enchantment.Enchantment;
/*      */ import net.minecraft.enchantment.EnchantmentData;
/*      */ import net.minecraft.enchantment.EnchantmentHelper;
/*      */ import net.minecraft.entity.Entity;
/*      */ import net.minecraft.entity.EntityAgeable;
/*      */ import net.minecraft.entity.EntityCreature;
/*      */ import net.minecraft.entity.EntityLiving;
/*      */ import net.minecraft.entity.EntityLivingBase;
/*      */ import net.minecraft.entity.IEntityLivingData;
/*      */ import net.minecraft.entity.IMerchant;
/*      */ import net.minecraft.entity.INpc;
/*      */ import net.minecraft.entity.SharedMonsterAttributes;
/*      */ import net.minecraft.entity.ai.EntityAIAvoidEntity;
/*      */ import net.minecraft.entity.ai.EntityAIBase;
/*      */ import net.minecraft.entity.ai.EntityAIHarvestFarmland;
/*      */ import net.minecraft.entity.ai.EntityAILookAtTradePlayer;
/*      */ import net.minecraft.entity.ai.EntityAIMoveIndoors;
/*      */ import net.minecraft.entity.ai.EntityAIMoveTowardsRestriction;
/*      */ import net.minecraft.entity.ai.EntityAIOpenDoor;
/*      */ import net.minecraft.entity.ai.EntityAIPlay;
/*      */ import net.minecraft.entity.ai.EntityAIRestrictOpenDoor;
/*      */ import net.minecraft.entity.ai.EntityAISwimming;
/*      */ import net.minecraft.entity.ai.EntityAITradePlayer;
/*      */ import net.minecraft.entity.ai.EntityAIVillagerInteract;
/*      */ import net.minecraft.entity.ai.EntityAIVillagerMate;
/*      */ import net.minecraft.entity.ai.EntityAIWander;
/*      */ import net.minecraft.entity.ai.EntityAIWatchClosest;
/*      */ import net.minecraft.entity.ai.EntityAIWatchClosest2;
/*      */ import net.minecraft.entity.effect.EntityLightningBolt;
/*      */ import net.minecraft.entity.item.EntityItem;
/*      */ import net.minecraft.entity.item.EntityXPOrb;
/*      */ import net.minecraft.entity.monster.EntityWitch;
/*      */ import net.minecraft.entity.monster.EntityZombie;
/*      */ import net.minecraft.entity.player.EntityPlayer;
/*      */ import net.minecraft.init.Blocks;
/*      */ import net.minecraft.init.Items;
/*      */ import net.minecraft.inventory.InventoryBasic;
/*      */ import net.minecraft.item.EnumDyeColor;
/*      */ import net.minecraft.item.Item;
/*      */ import net.minecraft.item.ItemStack;
/*      */ import net.minecraft.nbt.NBTBase;
/*      */ import net.minecraft.nbt.NBTTagCompound;
/*      */ import net.minecraft.nbt.NBTTagList;
/*      */ import net.minecraft.pathfinding.PathNavigateGround;
/*      */ import net.minecraft.potion.Potion;
/*      */ import net.minecraft.potion.PotionEffect;
/*      */ import net.minecraft.stats.StatList;
/*      */ import net.minecraft.util.BlockPos;
/*      */ import net.minecraft.util.ChatComponentText;
/*      */ import net.minecraft.util.ChatComponentTranslation;
/*      */ import net.minecraft.util.DamageSource;
/*      */ import net.minecraft.util.EnumParticleTypes;
/*      */ import net.minecraft.util.IChatComponent;
/*      */ import net.minecraft.util.MathHelper;
/*      */ import net.minecraft.village.MerchantRecipe;
/*      */ import net.minecraft.village.MerchantRecipeList;
/*      */ import net.minecraft.village.Village;
/*      */ import net.minecraft.world.DifficultyInstance;
/*      */ import net.minecraft.world.World;
/*      */ 
/*      */ public class EntityVillager extends EntityAgeable implements IMerchant, INpc {
/*      */   private int randomTickDivider;
/*      */   private boolean isMating;
/*      */   private boolean isPlaying;
/*      */   Village villageObj;
/*   68 */   private static final ITradeList[][][][] DEFAULT_TRADE_LIST_MAP = new ITradeList[][][][] { { { { new EmeraldForItems(Items.wheat, new PriceInfo(18, 22)), new EmeraldForItems(Items.potato, new PriceInfo(15, 19)), new EmeraldForItems(Items.carrot, new PriceInfo(15, 19)), new ListItemForEmeralds(Items.bread, new PriceInfo(-4, -2)) }, { new EmeraldForItems(Item.getItemFromBlock(Blocks.pumpkin), new PriceInfo(8, 13)), new ListItemForEmeralds(Items.pumpkin_pie, new PriceInfo(-3, -2)) }, { new EmeraldForItems(Item.getItemFromBlock(Blocks.melon_block), new PriceInfo(7, 12)), new ListItemForEmeralds(Items.apple, new PriceInfo(-5, -7)) }, { new ListItemForEmeralds(Items.cookie, new PriceInfo(-6, -10)), new ListItemForEmeralds(Items.cake, new PriceInfo(1, 1)) } }, { { new EmeraldForItems(Items.string, new PriceInfo(15, 20)), new EmeraldForItems(Items.coal, new PriceInfo(16, 24)), new ItemAndEmeraldToItem(Items.fish, new PriceInfo(6, 6), Items.cooked_fish, new PriceInfo(6, 6)) }, { new ListEnchantedItemForEmeralds((Item)Items.fishing_rod, new PriceInfo(7, 8)) } }, { { new EmeraldForItems(Item.getItemFromBlock(Blocks.wool), new PriceInfo(16, 22)), new ListItemForEmeralds((Item)Items.shears, new PriceInfo(3, 4)) }, { new ListItemForEmeralds(new ItemStack(Item.getItemFromBlock(Blocks.wool), 1, 0), new PriceInfo(1, 2)), new ListItemForEmeralds(new ItemStack(Item.getItemFromBlock(Blocks.wool), 1, 1), new PriceInfo(1, 2)), new ListItemForEmeralds(new ItemStack(Item.getItemFromBlock(Blocks.wool), 1, 2), new PriceInfo(1, 2)), new ListItemForEmeralds(new ItemStack(Item.getItemFromBlock(Blocks.wool), 1, 3), new PriceInfo(1, 2)), new ListItemForEmeralds(new ItemStack(Item.getItemFromBlock(Blocks.wool), 1, 4), new PriceInfo(1, 2)), new ListItemForEmeralds(new ItemStack(Item.getItemFromBlock(Blocks.wool), 1, 5), new PriceInfo(1, 2)), new ListItemForEmeralds(new ItemStack(Item.getItemFromBlock(Blocks.wool), 1, 6), new PriceInfo(1, 2)), new ListItemForEmeralds(new ItemStack(Item.getItemFromBlock(Blocks.wool), 1, 7), new PriceInfo(1, 2)), new ListItemForEmeralds(new ItemStack(Item.getItemFromBlock(Blocks.wool), 1, 8), new PriceInfo(1, 2)), new ListItemForEmeralds(new ItemStack(Item.getItemFromBlock(Blocks.wool), 1, 9), new PriceInfo(1, 2)), new ListItemForEmeralds(new ItemStack(Item.getItemFromBlock(Blocks.wool), 1, 10), new PriceInfo(1, 2)), new ListItemForEmeralds(new ItemStack(Item.getItemFromBlock(Blocks.wool), 1, 11), new PriceInfo(1, 2)), new ListItemForEmeralds(new ItemStack(Item.getItemFromBlock(Blocks.wool), 1, 12), new PriceInfo(1, 2)), new ListItemForEmeralds(new ItemStack(Item.getItemFromBlock(Blocks.wool), 1, 13), new PriceInfo(1, 2)), new ListItemForEmeralds(new ItemStack(Item.getItemFromBlock(Blocks.wool), 1, 14), new PriceInfo(1, 2)), new ListItemForEmeralds(new ItemStack(Item.getItemFromBlock(Blocks.wool), 1, 15), new PriceInfo(1, 2)) } }, { { new EmeraldForItems(Items.string, new PriceInfo(15, 20)), new ListItemForEmeralds(Items.arrow, new PriceInfo(-12, -8)) }, { new ListItemForEmeralds((Item)Items.bow, new PriceInfo(2, 3)), new ItemAndEmeraldToItem(Item.getItemFromBlock(Blocks.gravel), new PriceInfo(10, 10), Items.flint, new PriceInfo(6, 10)) } } }, { { { new EmeraldForItems(Items.paper, new PriceInfo(24, 36)), new ListEnchantedBookForEmeralds() }, { new EmeraldForItems(Items.book, new PriceInfo(8, 10)), new ListItemForEmeralds(Items.compass, new PriceInfo(10, 12)), new ListItemForEmeralds(Item.getItemFromBlock(Blocks.bookshelf), new PriceInfo(3, 4)) }, { new EmeraldForItems(Items.written_book, new PriceInfo(2, 2)), new ListItemForEmeralds(Items.clock, new PriceInfo(10, 12)), new ListItemForEmeralds(Item.getItemFromBlock(Blocks.glass), new PriceInfo(-5, -3)) }, { new ListEnchantedBookForEmeralds() }, { new ListEnchantedBookForEmeralds() }, { new ListItemForEmeralds(Items.name_tag, new PriceInfo(20, 22)) } } }, { { { new EmeraldForItems(Items.rotten_flesh, new PriceInfo(36, 40)), new EmeraldForItems(Items.gold_ingot, new PriceInfo(8, 10)) }, { new ListItemForEmeralds(Items.redstone, new PriceInfo(-4, -1)), new ListItemForEmeralds(new ItemStack(Items.dye, 1, EnumDyeColor.BLUE.getDyeDamage()), new PriceInfo(-2, -1)) }, { new ListItemForEmeralds(Items.ender_eye, new PriceInfo(7, 11)), new ListItemForEmeralds(Item.getItemFromBlock(Blocks.glowstone), new PriceInfo(-3, -1)) }, { new ListItemForEmeralds(Items.experience_bottle, new PriceInfo(3, 11)) } } }, { { { new EmeraldForItems(Items.coal, new PriceInfo(16, 24)), new ListItemForEmeralds((Item)Items.iron_helmet, new PriceInfo(4, 6)) }, { new EmeraldForItems(Items.iron_ingot, new PriceInfo(7, 9)), new ListItemForEmeralds((Item)Items.iron_chestplate, new PriceInfo(10, 14)) }, { new EmeraldForItems(Items.diamond, new PriceInfo(3, 4)), new ListEnchantedItemForEmeralds((Item)Items.diamond_chestplate, new PriceInfo(16, 19)) }, { new ListItemForEmeralds((Item)Items.chainmail_boots, new PriceInfo(5, 7)), new ListItemForEmeralds((Item)Items.chainmail_leggings, new PriceInfo(9, 11)), new ListItemForEmeralds((Item)Items.chainmail_helmet, new PriceInfo(5, 7)), new ListItemForEmeralds((Item)Items.chainmail_chestplate, new PriceInfo(11, 15)) } }, { { new EmeraldForItems(Items.coal, new PriceInfo(16, 24)), new ListItemForEmeralds(Items.iron_axe, new PriceInfo(6, 8)) }, { new EmeraldForItems(Items.iron_ingot, new PriceInfo(7, 9)), new ListEnchantedItemForEmeralds(Items.iron_sword, new PriceInfo(9, 10)) }, { new EmeraldForItems(Items.diamond, new PriceInfo(3, 4)), new ListEnchantedItemForEmeralds(Items.diamond_sword, new PriceInfo(12, 15)), new ListEnchantedItemForEmeralds(Items.diamond_axe, new PriceInfo(9, 12)) } }, { { new EmeraldForItems(Items.coal, new PriceInfo(16, 24)), new ListEnchantedItemForEmeralds(Items.iron_shovel, new PriceInfo(5, 7)) }, { new EmeraldForItems(Items.iron_ingot, new PriceInfo(7, 9)), new ListEnchantedItemForEmeralds(Items.iron_pickaxe, new PriceInfo(9, 11)) }, { new EmeraldForItems(Items.diamond, new PriceInfo(3, 4)), new ListEnchantedItemForEmeralds(Items.diamond_pickaxe, new PriceInfo(12, 15)) } } }, { { { new EmeraldForItems(Items.porkchop, new PriceInfo(14, 18)), new EmeraldForItems(Items.chicken, new PriceInfo(14, 18)) }, { new EmeraldForItems(Items.coal, new PriceInfo(16, 24)), new ListItemForEmeralds(Items.cooked_porkchop, new PriceInfo(-7, -5)), new ListItemForEmeralds(Items.cooked_chicken, new PriceInfo(-8, -6)) } }, { { new EmeraldForItems(Items.leather, new PriceInfo(9, 12)), new ListItemForEmeralds((Item)Items.leather_leggings, new PriceInfo(2, 4)) }, { new ListEnchantedItemForEmeralds((Item)Items.leather_chestplate, new PriceInfo(7, 12)) }, { new ListItemForEmeralds(Items.saddle, new PriceInfo(8, 10)) } } } }; private EntityPlayer buyingPlayer; private MerchantRecipeList buyingList; private int timeUntilReset; private boolean needsInitilization; private boolean isWillingToMate; private int wealth; private String lastBuyingPlayer; private int careerId; private int careerLevel; private boolean isLookingForHome; private boolean areAdditionalTasksSet;
/*      */   private final InventoryBasic villagerInventory;
/*      */   
/*      */   public EntityVillager(World worldIn) {
/*   72 */     this(worldIn, 0);
/*      */   }
/*      */ 
/*      */   
/*      */   public EntityVillager(World worldIn, int professionId) {
/*   77 */     super(worldIn);
/*   78 */     this.villagerInventory = new InventoryBasic("Items", false, 8);
/*   79 */     setProfession(professionId);
/*   80 */     setSize(0.6F, 1.8F);
/*   81 */     ((PathNavigateGround)getNavigator()).setBreakDoors(true);
/*   82 */     ((PathNavigateGround)getNavigator()).setAvoidsWater(true);
/*   83 */     this.tasks.addTask(0, (EntityAIBase)new EntityAISwimming((EntityLiving)this));
/*   84 */     this.tasks.addTask(1, (EntityAIBase)new EntityAIAvoidEntity((EntityCreature)this, EntityZombie.class, 8.0F, 0.6D, 0.6D));
/*   85 */     this.tasks.addTask(1, (EntityAIBase)new EntityAITradePlayer(this));
/*   86 */     this.tasks.addTask(1, (EntityAIBase)new EntityAILookAtTradePlayer(this));
/*   87 */     this.tasks.addTask(2, (EntityAIBase)new EntityAIMoveIndoors((EntityCreature)this));
/*   88 */     this.tasks.addTask(3, (EntityAIBase)new EntityAIRestrictOpenDoor((EntityCreature)this));
/*   89 */     this.tasks.addTask(4, (EntityAIBase)new EntityAIOpenDoor((EntityLiving)this, true));
/*   90 */     this.tasks.addTask(5, (EntityAIBase)new EntityAIMoveTowardsRestriction((EntityCreature)this, 0.6D));
/*   91 */     this.tasks.addTask(6, (EntityAIBase)new EntityAIVillagerMate(this));
/*   92 */     this.tasks.addTask(7, (EntityAIBase)new EntityAIFollowGolem(this));
/*   93 */     this.tasks.addTask(9, (EntityAIBase)new EntityAIWatchClosest2((EntityLiving)this, EntityPlayer.class, 3.0F, 1.0F));
/*   94 */     this.tasks.addTask(9, (EntityAIBase)new EntityAIVillagerInteract(this));
/*   95 */     this.tasks.addTask(9, (EntityAIBase)new EntityAIWander((EntityCreature)this, 0.6D));
/*   96 */     this.tasks.addTask(10, (EntityAIBase)new EntityAIWatchClosest((EntityLiving)this, EntityLiving.class, 8.0F));
/*   97 */     setCanPickUpLoot(true);
/*      */   }
/*      */ 
/*      */   
/*      */   private void setAdditionalAItasks() {
/*  102 */     if (!this.areAdditionalTasksSet) {
/*      */       
/*  104 */       this.areAdditionalTasksSet = true;
/*      */       
/*  106 */       if (isChild()) {
/*      */         
/*  108 */         this.tasks.addTask(8, (EntityAIBase)new EntityAIPlay(this, 0.32D));
/*      */       }
/*  110 */       else if (getProfession() == 0) {
/*      */         
/*  112 */         this.tasks.addTask(6, (EntityAIBase)new EntityAIHarvestFarmland(this, 0.6D));
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void onGrowingAdult() {
/*  123 */     if (getProfession() == 0)
/*      */     {
/*  125 */       this.tasks.addTask(8, (EntityAIBase)new EntityAIHarvestFarmland(this, 0.6D));
/*      */     }
/*      */     
/*  128 */     super.onGrowingAdult();
/*      */   }
/*      */ 
/*      */   
/*      */   protected void applyEntityAttributes() {
/*  133 */     super.applyEntityAttributes();
/*  134 */     getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.5D);
/*      */   }
/*      */ 
/*      */   
/*      */   protected void updateAITasks() {
/*  139 */     if (--this.randomTickDivider <= 0) {
/*      */       
/*  141 */       BlockPos blockpos = new BlockPos((Entity)this);
/*  142 */       this.worldObj.getVillageCollection().addToVillagerPositionList(blockpos);
/*  143 */       this.randomTickDivider = 70 + this.rand.nextInt(50);
/*  144 */       this.villageObj = this.worldObj.getVillageCollection().getNearestVillage(blockpos, 32);
/*      */       
/*  146 */       if (this.villageObj == null) {
/*      */         
/*  148 */         detachHome();
/*      */       }
/*      */       else {
/*      */         
/*  152 */         BlockPos blockpos1 = this.villageObj.getCenter();
/*  153 */         setHomePosAndDistance(blockpos1, (int)this.villageObj.getVillageRadius());
/*      */         
/*  155 */         if (this.isLookingForHome) {
/*      */           
/*  157 */           this.isLookingForHome = false;
/*  158 */           this.villageObj.setDefaultPlayerReputation(5);
/*      */         } 
/*      */       } 
/*      */     } 
/*      */     
/*  163 */     if (!isTrading() && this.timeUntilReset > 0) {
/*      */       
/*  165 */       this.timeUntilReset--;
/*      */       
/*  167 */       if (this.timeUntilReset <= 0) {
/*      */         
/*  169 */         if (this.needsInitilization) {
/*      */           
/*  171 */           for (MerchantRecipe merchantrecipe : this.buyingList) {
/*      */             
/*  173 */             if (merchantrecipe.isRecipeDisabled())
/*      */             {
/*  175 */               merchantrecipe.increaseMaxTradeUses(this.rand.nextInt(6) + this.rand.nextInt(6) + 2);
/*      */             }
/*      */           } 
/*      */           
/*  179 */           populateBuyingList();
/*  180 */           this.needsInitilization = false;
/*      */           
/*  182 */           if (this.villageObj != null && this.lastBuyingPlayer != null) {
/*      */             
/*  184 */             this.worldObj.setEntityState((Entity)this, (byte)14);
/*  185 */             this.villageObj.setReputationForPlayer(this.lastBuyingPlayer, 1);
/*      */           } 
/*      */         } 
/*      */         
/*  189 */         addPotionEffect(new PotionEffect(Potion.regeneration.id, 200, 0));
/*      */       } 
/*      */     } 
/*      */     
/*  193 */     super.updateAITasks();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean interact(EntityPlayer player) {
/*  201 */     ItemStack itemstack = player.inventory.getCurrentItem();
/*  202 */     boolean flag = (itemstack != null && itemstack.getItem() == Items.spawn_egg);
/*      */     
/*  204 */     if (!flag && isEntityAlive() && !isTrading() && !isChild()) {
/*      */       
/*  206 */       if (!this.worldObj.isRemote && (this.buyingList == null || !this.buyingList.isEmpty())) {
/*      */         
/*  208 */         this.buyingPlayer = player;
/*  209 */         player.displayVillagerTradeGui(this);
/*      */       } 
/*      */       
/*  212 */       player.triggerAchievement(StatList.timesTalkedToVillagerStat);
/*  213 */       return true;
/*      */     } 
/*      */ 
/*      */     
/*  217 */     return super.interact(player);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   protected void entityInit() {
/*  223 */     super.entityInit();
/*  224 */     this.dataWatcher.addObject(16, Integer.valueOf(0));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void writeEntityToNBT(NBTTagCompound tagCompound) {
/*  232 */     super.writeEntityToNBT(tagCompound);
/*  233 */     tagCompound.setInteger("Profession", getProfession());
/*  234 */     tagCompound.setInteger("Riches", this.wealth);
/*  235 */     tagCompound.setInteger("Career", this.careerId);
/*  236 */     tagCompound.setInteger("CareerLevel", this.careerLevel);
/*  237 */     tagCompound.setBoolean("Willing", this.isWillingToMate);
/*      */     
/*  239 */     if (this.buyingList != null)
/*      */     {
/*  241 */       tagCompound.setTag("Offers", (NBTBase)this.buyingList.getRecipiesAsTags());
/*      */     }
/*      */     
/*  244 */     NBTTagList nbttaglist = new NBTTagList();
/*      */     
/*  246 */     for (int i = 0; i < this.villagerInventory.getSizeInventory(); i++) {
/*      */       
/*  248 */       ItemStack itemstack = this.villagerInventory.getStackInSlot(i);
/*      */       
/*  250 */       if (itemstack != null)
/*      */       {
/*  252 */         nbttaglist.appendTag((NBTBase)itemstack.writeToNBT(new NBTTagCompound()));
/*      */       }
/*      */     } 
/*      */     
/*  256 */     tagCompound.setTag("Inventory", (NBTBase)nbttaglist);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void readEntityFromNBT(NBTTagCompound tagCompund) {
/*  264 */     super.readEntityFromNBT(tagCompund);
/*  265 */     setProfession(tagCompund.getInteger("Profession"));
/*  266 */     this.wealth = tagCompund.getInteger("Riches");
/*  267 */     this.careerId = tagCompund.getInteger("Career");
/*  268 */     this.careerLevel = tagCompund.getInteger("CareerLevel");
/*  269 */     this.isWillingToMate = tagCompund.getBoolean("Willing");
/*      */     
/*  271 */     if (tagCompund.hasKey("Offers", 10)) {
/*      */       
/*  273 */       NBTTagCompound nbttagcompound = tagCompund.getCompoundTag("Offers");
/*  274 */       this.buyingList = new MerchantRecipeList(nbttagcompound);
/*      */     } 
/*      */     
/*  277 */     NBTTagList nbttaglist = tagCompund.getTagList("Inventory", 10);
/*      */     
/*  279 */     for (int i = 0; i < nbttaglist.tagCount(); i++) {
/*      */       
/*  281 */       ItemStack itemstack = ItemStack.loadItemStackFromNBT(nbttaglist.getCompoundTagAt(i));
/*      */       
/*  283 */       if (itemstack != null)
/*      */       {
/*  285 */         this.villagerInventory.func_174894_a(itemstack);
/*      */       }
/*      */     } 
/*      */     
/*  289 */     setCanPickUpLoot(true);
/*  290 */     setAdditionalAItasks();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected boolean canDespawn() {
/*  298 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected String getLivingSound() {
/*  306 */     return isTrading() ? "mob.villager.haggle" : "mob.villager.idle";
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected String getHurtSound() {
/*  314 */     return "mob.villager.hit";
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected String getDeathSound() {
/*  322 */     return "mob.villager.death";
/*      */   }
/*      */ 
/*      */   
/*      */   public void setProfession(int professionId) {
/*  327 */     this.dataWatcher.updateObject(16, Integer.valueOf(professionId));
/*      */   }
/*      */ 
/*      */   
/*      */   public int getProfession() {
/*  332 */     return Math.max(this.dataWatcher.getWatchableObjectInt(16) % 5, 0);
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean isMating() {
/*  337 */     return this.isMating;
/*      */   }
/*      */ 
/*      */   
/*      */   public void setMating(boolean mating) {
/*  342 */     this.isMating = mating;
/*      */   }
/*      */ 
/*      */   
/*      */   public void setPlaying(boolean playing) {
/*  347 */     this.isPlaying = playing;
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean isPlaying() {
/*  352 */     return this.isPlaying;
/*      */   }
/*      */ 
/*      */   
/*      */   public void setRevengeTarget(EntityLivingBase livingBase) {
/*  357 */     super.setRevengeTarget(livingBase);
/*      */     
/*  359 */     if (this.villageObj != null && livingBase != null) {
/*      */       
/*  361 */       this.villageObj.addOrRenewAgressor(livingBase);
/*      */       
/*  363 */       if (livingBase instanceof EntityPlayer) {
/*      */         
/*  365 */         int i = -1;
/*      */         
/*  367 */         if (isChild())
/*      */         {
/*  369 */           i = -3;
/*      */         }
/*      */         
/*  372 */         this.villageObj.setReputationForPlayer(livingBase.getName(), i);
/*      */         
/*  374 */         if (isEntityAlive())
/*      */         {
/*  376 */           this.worldObj.setEntityState((Entity)this, (byte)13);
/*      */         }
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void onDeath(DamageSource cause) {
/*  387 */     if (this.villageObj != null) {
/*      */       
/*  389 */       Entity entity = cause.getEntity();
/*      */       
/*  391 */       if (entity != null) {
/*      */         
/*  393 */         if (entity instanceof EntityPlayer)
/*      */         {
/*  395 */           this.villageObj.setReputationForPlayer(entity.getName(), -2);
/*      */         }
/*  397 */         else if (entity instanceof net.minecraft.entity.monster.IMob)
/*      */         {
/*  399 */           this.villageObj.endMatingSeason();
/*      */         }
/*      */       
/*      */       } else {
/*      */         
/*  404 */         EntityPlayer entityplayer = this.worldObj.getClosestPlayerToEntity((Entity)this, 16.0D);
/*      */         
/*  406 */         if (entityplayer != null)
/*      */         {
/*  408 */           this.villageObj.endMatingSeason();
/*      */         }
/*      */       } 
/*      */     } 
/*      */     
/*  413 */     super.onDeath(cause);
/*      */   }
/*      */ 
/*      */   
/*      */   public void setCustomer(EntityPlayer p_70932_1_) {
/*  418 */     this.buyingPlayer = p_70932_1_;
/*      */   }
/*      */ 
/*      */   
/*      */   public EntityPlayer getCustomer() {
/*  423 */     return this.buyingPlayer;
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean isTrading() {
/*  428 */     return (this.buyingPlayer != null);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean getIsWillingToMate(boolean updateFirst) {
/*  436 */     if (!this.isWillingToMate && updateFirst && func_175553_cp()) {
/*      */       
/*  438 */       boolean flag = false;
/*      */       
/*  440 */       for (int i = 0; i < this.villagerInventory.getSizeInventory(); i++) {
/*      */         
/*  442 */         ItemStack itemstack = this.villagerInventory.getStackInSlot(i);
/*      */         
/*  444 */         if (itemstack != null)
/*      */         {
/*  446 */           if (itemstack.getItem() == Items.bread && itemstack.stackSize >= 3) {
/*      */             
/*  448 */             flag = true;
/*  449 */             this.villagerInventory.decrStackSize(i, 3);
/*      */           }
/*  451 */           else if ((itemstack.getItem() == Items.potato || itemstack.getItem() == Items.carrot) && itemstack.stackSize >= 12) {
/*      */             
/*  453 */             flag = true;
/*  454 */             this.villagerInventory.decrStackSize(i, 12);
/*      */           } 
/*      */         }
/*      */         
/*  458 */         if (flag) {
/*      */           
/*  460 */           this.worldObj.setEntityState((Entity)this, (byte)18);
/*  461 */           this.isWillingToMate = true;
/*      */           
/*      */           break;
/*      */         } 
/*      */       } 
/*      */     } 
/*  467 */     return this.isWillingToMate;
/*      */   }
/*      */ 
/*      */   
/*      */   public void setIsWillingToMate(boolean willingToTrade) {
/*  472 */     this.isWillingToMate = willingToTrade;
/*      */   }
/*      */ 
/*      */   
/*      */   public void useRecipe(MerchantRecipe recipe) {
/*  477 */     recipe.incrementToolUses();
/*  478 */     this.livingSoundTime = -getTalkInterval();
/*  479 */     playSound("mob.villager.yes", getSoundVolume(), getSoundPitch());
/*  480 */     int i = 3 + this.rand.nextInt(4);
/*      */     
/*  482 */     if (recipe.getToolUses() == 1 || this.rand.nextInt(5) == 0) {
/*      */       
/*  484 */       this.timeUntilReset = 40;
/*  485 */       this.needsInitilization = true;
/*  486 */       this.isWillingToMate = true;
/*      */       
/*  488 */       if (this.buyingPlayer != null) {
/*      */         
/*  490 */         this.lastBuyingPlayer = this.buyingPlayer.getName();
/*      */       }
/*      */       else {
/*      */         
/*  494 */         this.lastBuyingPlayer = null;
/*      */       } 
/*      */       
/*  497 */       i += 5;
/*      */     } 
/*      */     
/*  500 */     if (recipe.getItemToBuy().getItem() == Items.emerald)
/*      */     {
/*  502 */       this.wealth += (recipe.getItemToBuy()).stackSize;
/*      */     }
/*      */     
/*  505 */     if (recipe.getRewardsExp())
/*      */     {
/*  507 */       this.worldObj.spawnEntityInWorld((Entity)new EntityXPOrb(this.worldObj, this.posX, this.posY + 0.5D, this.posZ, i));
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void verifySellingItem(ItemStack stack) {
/*  517 */     if (!this.worldObj.isRemote && this.livingSoundTime > -getTalkInterval() + 20) {
/*      */       
/*  519 */       this.livingSoundTime = -getTalkInterval();
/*      */       
/*  521 */       if (stack != null) {
/*      */         
/*  523 */         playSound("mob.villager.yes", getSoundVolume(), getSoundPitch());
/*      */       }
/*      */       else {
/*      */         
/*  527 */         playSound("mob.villager.no", getSoundVolume(), getSoundPitch());
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public MerchantRecipeList getRecipes(EntityPlayer p_70934_1_) {
/*  534 */     if (this.buyingList == null)
/*      */     {
/*  536 */       populateBuyingList();
/*      */     }
/*      */     
/*  539 */     return this.buyingList;
/*      */   }
/*      */ 
/*      */   
/*      */   private void populateBuyingList() {
/*  544 */     ITradeList[][][] aentityvillager$itradelist = DEFAULT_TRADE_LIST_MAP[getProfession()];
/*      */     
/*  546 */     if (this.careerId != 0 && this.careerLevel != 0) {
/*      */       
/*  548 */       this.careerLevel++;
/*      */     }
/*      */     else {
/*      */       
/*  552 */       this.careerId = this.rand.nextInt(aentityvillager$itradelist.length) + 1;
/*  553 */       this.careerLevel = 1;
/*      */     } 
/*      */     
/*  556 */     if (this.buyingList == null)
/*      */     {
/*  558 */       this.buyingList = new MerchantRecipeList();
/*      */     }
/*      */     
/*  561 */     int i = this.careerId - 1;
/*  562 */     int j = this.careerLevel - 1;
/*  563 */     ITradeList[][] aentityvillager$itradelist1 = aentityvillager$itradelist[i];
/*      */     
/*  565 */     if (j >= 0 && j < aentityvillager$itradelist1.length) {
/*      */       
/*  567 */       ITradeList[] aentityvillager$itradelist2 = aentityvillager$itradelist1[j];
/*      */       
/*  569 */       for (ITradeList entityvillager$itradelist : aentityvillager$itradelist2)
/*      */       {
/*  571 */         entityvillager$itradelist.modifyMerchantRecipeList(this.buyingList, this.rand);
/*      */       }
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setRecipes(MerchantRecipeList recipeList) {}
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public IChatComponent getDisplayName() {
/*  585 */     String s = getCustomNameTag();
/*      */     
/*  587 */     if (s != null && !s.isEmpty()) {
/*      */       
/*  589 */       ChatComponentText chatcomponenttext = new ChatComponentText(s);
/*  590 */       chatcomponenttext.getChatStyle().setChatHoverEvent(getHoverEvent());
/*  591 */       chatcomponenttext.getChatStyle().setInsertion(getUniqueID().toString());
/*  592 */       return (IChatComponent)chatcomponenttext;
/*      */     } 
/*      */ 
/*      */     
/*  596 */     if (this.buyingList == null)
/*      */     {
/*  598 */       populateBuyingList();
/*      */     }
/*      */     
/*  601 */     String s1 = null;
/*      */     
/*  603 */     switch (getProfession()) {
/*      */       
/*      */       case 0:
/*  606 */         if (this.careerId == 1) {
/*      */           
/*  608 */           s1 = "farmer"; break;
/*      */         } 
/*  610 */         if (this.careerId == 2) {
/*      */           
/*  612 */           s1 = "fisherman"; break;
/*      */         } 
/*  614 */         if (this.careerId == 3) {
/*      */           
/*  616 */           s1 = "shepherd"; break;
/*      */         } 
/*  618 */         if (this.careerId == 4)
/*      */         {
/*  620 */           s1 = "fletcher";
/*      */         }
/*      */         break;
/*      */ 
/*      */       
/*      */       case 1:
/*  626 */         s1 = "librarian";
/*      */         break;
/*      */       
/*      */       case 2:
/*  630 */         s1 = "cleric";
/*      */         break;
/*      */       
/*      */       case 3:
/*  634 */         if (this.careerId == 1) {
/*      */           
/*  636 */           s1 = "armor"; break;
/*      */         } 
/*  638 */         if (this.careerId == 2) {
/*      */           
/*  640 */           s1 = "weapon"; break;
/*      */         } 
/*  642 */         if (this.careerId == 3)
/*      */         {
/*  644 */           s1 = "tool";
/*      */         }
/*      */         break;
/*      */ 
/*      */       
/*      */       case 4:
/*  650 */         if (this.careerId == 1) {
/*      */           
/*  652 */           s1 = "butcher"; break;
/*      */         } 
/*  654 */         if (this.careerId == 2)
/*      */         {
/*  656 */           s1 = "leather";
/*      */         }
/*      */         break;
/*      */     } 
/*  660 */     if (s1 != null) {
/*      */       
/*  662 */       ChatComponentTranslation chatcomponenttranslation = new ChatComponentTranslation("entity.Villager." + s1, new Object[0]);
/*  663 */       chatcomponenttranslation.getChatStyle().setChatHoverEvent(getHoverEvent());
/*  664 */       chatcomponenttranslation.getChatStyle().setInsertion(getUniqueID().toString());
/*  665 */       return (IChatComponent)chatcomponenttranslation;
/*      */     } 
/*      */ 
/*      */     
/*  669 */     return super.getDisplayName();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public float getEyeHeight() {
/*  676 */     float f = 1.62F;
/*      */     
/*  678 */     if (isChild())
/*      */     {
/*  680 */       f = (float)(f - 0.81D);
/*      */     }
/*      */     
/*  683 */     return f;
/*      */   }
/*      */ 
/*      */   
/*      */   public void handleStatusUpdate(byte id) {
/*  688 */     if (id == 12) {
/*      */       
/*  690 */       spawnParticles(EnumParticleTypes.HEART);
/*      */     }
/*  692 */     else if (id == 13) {
/*      */       
/*  694 */       spawnParticles(EnumParticleTypes.VILLAGER_ANGRY);
/*      */     }
/*  696 */     else if (id == 14) {
/*      */       
/*  698 */       spawnParticles(EnumParticleTypes.VILLAGER_HAPPY);
/*      */     }
/*      */     else {
/*      */       
/*  702 */       super.handleStatusUpdate(id);
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   private void spawnParticles(EnumParticleTypes particleType) {
/*  708 */     for (int i = 0; i < 5; i++) {
/*      */       
/*  710 */       double d0 = this.rand.nextGaussian() * 0.02D;
/*  711 */       double d1 = this.rand.nextGaussian() * 0.02D;
/*  712 */       double d2 = this.rand.nextGaussian() * 0.02D;
/*  713 */       this.worldObj.spawnParticle(particleType, this.posX + (this.rand.nextFloat() * this.width * 2.0F) - this.width, this.posY + 1.0D + (this.rand.nextFloat() * this.height), this.posZ + (this.rand.nextFloat() * this.width * 2.0F) - this.width, d0, d1, d2, new int[0]);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public IEntityLivingData onInitialSpawn(DifficultyInstance difficulty, IEntityLivingData livingdata) {
/*  723 */     livingdata = super.onInitialSpawn(difficulty, livingdata);
/*  724 */     setProfession(this.worldObj.rand.nextInt(5));
/*  725 */     setAdditionalAItasks();
/*  726 */     return livingdata;
/*      */   }
/*      */ 
/*      */   
/*      */   public void setLookingForHome() {
/*  731 */     this.isLookingForHome = true;
/*      */   }
/*      */ 
/*      */   
/*      */   public EntityVillager createChild(EntityAgeable ageable) {
/*  736 */     EntityVillager entityvillager = new EntityVillager(this.worldObj);
/*  737 */     entityvillager.onInitialSpawn(this.worldObj.getDifficultyForLocation(new BlockPos((Entity)entityvillager)), (IEntityLivingData)null);
/*  738 */     return entityvillager;
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean allowLeashing() {
/*  743 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void onStruckByLightning(EntityLightningBolt lightningBolt) {
/*  751 */     if (!this.worldObj.isRemote && !this.isDead) {
/*      */       
/*  753 */       EntityWitch entitywitch = new EntityWitch(this.worldObj);
/*  754 */       entitywitch.setLocationAndAngles(this.posX, this.posY, this.posZ, this.rotationYaw, this.rotationPitch);
/*  755 */       entitywitch.onInitialSpawn(this.worldObj.getDifficultyForLocation(new BlockPos((Entity)entitywitch)), (IEntityLivingData)null);
/*  756 */       entitywitch.setNoAI(isAIDisabled());
/*      */       
/*  758 */       if (hasCustomName()) {
/*      */         
/*  760 */         entitywitch.setCustomNameTag(getCustomNameTag());
/*  761 */         entitywitch.setAlwaysRenderNameTag(getAlwaysRenderNameTag());
/*      */       } 
/*      */       
/*  764 */       this.worldObj.spawnEntityInWorld((Entity)entitywitch);
/*  765 */       setDead();
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public InventoryBasic getVillagerInventory() {
/*  771 */     return this.villagerInventory;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void updateEquipmentIfNeeded(EntityItem itemEntity) {
/*  780 */     ItemStack itemstack = itemEntity.getEntityItem();
/*  781 */     Item item = itemstack.getItem();
/*      */     
/*  783 */     if (canVillagerPickupItem(item)) {
/*      */       
/*  785 */       ItemStack itemstack1 = this.villagerInventory.func_174894_a(itemstack);
/*      */       
/*  787 */       if (itemstack1 == null) {
/*      */         
/*  789 */         itemEntity.setDead();
/*      */       }
/*      */       else {
/*      */         
/*  793 */         itemstack.stackSize = itemstack1.stackSize;
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   private boolean canVillagerPickupItem(Item itemIn) {
/*  800 */     return (itemIn == Items.bread || itemIn == Items.potato || itemIn == Items.carrot || itemIn == Items.wheat || itemIn == Items.wheat_seeds);
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean func_175553_cp() {
/*  805 */     return hasEnoughItems(1);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean canAbondonItems() {
/*  814 */     return hasEnoughItems(2);
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean func_175557_cr() {
/*  819 */     boolean flag = (getProfession() == 0);
/*  820 */     return flag ? (!hasEnoughItems(5)) : (!hasEnoughItems(1));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean hasEnoughItems(int multiplier) {
/*  828 */     boolean flag = (getProfession() == 0);
/*      */     
/*  830 */     for (int i = 0; i < this.villagerInventory.getSizeInventory(); i++) {
/*      */       
/*  832 */       ItemStack itemstack = this.villagerInventory.getStackInSlot(i);
/*      */       
/*  834 */       if (itemstack != null) {
/*      */         
/*  836 */         if ((itemstack.getItem() == Items.bread && itemstack.stackSize >= 3 * multiplier) || (itemstack.getItem() == Items.potato && itemstack.stackSize >= 12 * multiplier) || (itemstack.getItem() == Items.carrot && itemstack.stackSize >= 12 * multiplier))
/*      */         {
/*  838 */           return true;
/*      */         }
/*      */         
/*  841 */         if (flag && itemstack.getItem() == Items.wheat && itemstack.stackSize >= 9 * multiplier)
/*      */         {
/*  843 */           return true;
/*      */         }
/*      */       } 
/*      */     } 
/*      */     
/*  848 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isFarmItemInInventory() {
/*  856 */     for (int i = 0; i < this.villagerInventory.getSizeInventory(); i++) {
/*      */       
/*  858 */       ItemStack itemstack = this.villagerInventory.getStackInSlot(i);
/*      */       
/*  860 */       if (itemstack != null && (itemstack.getItem() == Items.wheat_seeds || itemstack.getItem() == Items.potato || itemstack.getItem() == Items.carrot))
/*      */       {
/*  862 */         return true;
/*      */       }
/*      */     } 
/*      */     
/*  866 */     return false;
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean replaceItemInInventory(int inventorySlot, ItemStack itemStackIn) {
/*  871 */     if (super.replaceItemInInventory(inventorySlot, itemStackIn))
/*      */     {
/*  873 */       return true;
/*      */     }
/*      */ 
/*      */     
/*  877 */     int i = inventorySlot - 300;
/*      */     
/*  879 */     if (i >= 0 && i < this.villagerInventory.getSizeInventory()) {
/*      */       
/*  881 */       this.villagerInventory.setInventorySlotContents(i, itemStackIn);
/*  882 */       return true;
/*      */     } 
/*      */ 
/*      */     
/*  886 */     return false;
/*      */   }
/*      */ 
/*      */   
/*      */   static class EmeraldForItems
/*      */     implements ITradeList
/*      */   {
/*      */     public Item sellItem;
/*      */     
/*      */     public EntityVillager.PriceInfo price;
/*      */     
/*      */     public EmeraldForItems(Item itemIn, EntityVillager.PriceInfo priceIn) {
/*  898 */       this.sellItem = itemIn;
/*  899 */       this.price = priceIn;
/*      */     }
/*      */ 
/*      */     
/*      */     public void modifyMerchantRecipeList(MerchantRecipeList recipeList, Random random) {
/*  904 */       int i = 1;
/*      */       
/*  906 */       if (this.price != null)
/*      */       {
/*  908 */         i = this.price.getPrice(random);
/*      */       }
/*      */       
/*  911 */       recipeList.add(new MerchantRecipe(new ItemStack(this.sellItem, i, 0), Items.emerald));
/*      */     }
/*      */   }
/*      */   
/*      */   static interface ITradeList
/*      */   {
/*      */     void modifyMerchantRecipeList(MerchantRecipeList param1MerchantRecipeList, Random param1Random);
/*      */   }
/*      */   
/*      */   static class ItemAndEmeraldToItem
/*      */     implements ITradeList
/*      */   {
/*      */     public ItemStack buyingItemStack;
/*      */     public EntityVillager.PriceInfo buyingPriceInfo;
/*      */     public ItemStack sellingItemstack;
/*      */     public EntityVillager.PriceInfo field_179408_d;
/*      */     
/*      */     public ItemAndEmeraldToItem(Item p_i45813_1_, EntityVillager.PriceInfo p_i45813_2_, Item p_i45813_3_, EntityVillager.PriceInfo p_i45813_4_) {
/*  929 */       this.buyingItemStack = new ItemStack(p_i45813_1_);
/*  930 */       this.buyingPriceInfo = p_i45813_2_;
/*  931 */       this.sellingItemstack = new ItemStack(p_i45813_3_);
/*  932 */       this.field_179408_d = p_i45813_4_;
/*      */     }
/*      */ 
/*      */     
/*      */     public void modifyMerchantRecipeList(MerchantRecipeList recipeList, Random random) {
/*  937 */       int i = 1;
/*      */       
/*  939 */       if (this.buyingPriceInfo != null)
/*      */       {
/*  941 */         i = this.buyingPriceInfo.getPrice(random);
/*      */       }
/*      */       
/*  944 */       int j = 1;
/*      */       
/*  946 */       if (this.field_179408_d != null)
/*      */       {
/*  948 */         j = this.field_179408_d.getPrice(random);
/*      */       }
/*      */       
/*  951 */       recipeList.add(new MerchantRecipe(new ItemStack(this.buyingItemStack.getItem(), i, this.buyingItemStack.getMetadata()), new ItemStack(Items.emerald), new ItemStack(this.sellingItemstack.getItem(), j, this.sellingItemstack.getMetadata())));
/*      */     }
/*      */   }
/*      */   
/*      */   static class ListEnchantedBookForEmeralds
/*      */     implements ITradeList
/*      */   {
/*      */     public void modifyMerchantRecipeList(MerchantRecipeList recipeList, Random random) {
/*  959 */       Enchantment enchantment = Enchantment.enchantmentsBookList[random.nextInt(Enchantment.enchantmentsBookList.length)];
/*  960 */       int i = MathHelper.getRandomIntegerInRange(random, enchantment.getMinLevel(), enchantment.getMaxLevel());
/*  961 */       ItemStack itemstack = Items.enchanted_book.getEnchantedItemStack(new EnchantmentData(enchantment, i));
/*  962 */       int j = 2 + random.nextInt(5 + i * 10) + 3 * i;
/*      */       
/*  964 */       if (j > 64)
/*      */       {
/*  966 */         j = 64;
/*      */       }
/*      */       
/*  969 */       recipeList.add(new MerchantRecipe(new ItemStack(Items.book), new ItemStack(Items.emerald, j), itemstack));
/*      */     }
/*      */   }
/*      */   
/*      */   static class ListEnchantedItemForEmeralds
/*      */     implements ITradeList
/*      */   {
/*      */     public ItemStack enchantedItemStack;
/*      */     public EntityVillager.PriceInfo priceInfo;
/*      */     
/*      */     public ListEnchantedItemForEmeralds(Item p_i45814_1_, EntityVillager.PriceInfo p_i45814_2_) {
/*  980 */       this.enchantedItemStack = new ItemStack(p_i45814_1_);
/*  981 */       this.priceInfo = p_i45814_2_;
/*      */     }
/*      */ 
/*      */     
/*      */     public void modifyMerchantRecipeList(MerchantRecipeList recipeList, Random random) {
/*  986 */       int i = 1;
/*      */       
/*  988 */       if (this.priceInfo != null)
/*      */       {
/*  990 */         i = this.priceInfo.getPrice(random);
/*      */       }
/*      */       
/*  993 */       ItemStack itemstack = new ItemStack(Items.emerald, i, 0);
/*  994 */       ItemStack itemstack1 = new ItemStack(this.enchantedItemStack.getItem(), 1, this.enchantedItemStack.getMetadata());
/*  995 */       itemstack1 = EnchantmentHelper.addRandomEnchantment(random, itemstack1, 5 + random.nextInt(15));
/*  996 */       recipeList.add(new MerchantRecipe(itemstack, itemstack1));
/*      */     }
/*      */   }
/*      */   
/*      */   static class ListItemForEmeralds
/*      */     implements ITradeList
/*      */   {
/*      */     public ItemStack itemToBuy;
/*      */     public EntityVillager.PriceInfo priceInfo;
/*      */     
/*      */     public ListItemForEmeralds(Item par1Item, EntityVillager.PriceInfo priceInfo) {
/* 1007 */       this.itemToBuy = new ItemStack(par1Item);
/* 1008 */       this.priceInfo = priceInfo;
/*      */     }
/*      */ 
/*      */     
/*      */     public ListItemForEmeralds(ItemStack stack, EntityVillager.PriceInfo priceInfo) {
/* 1013 */       this.itemToBuy = stack;
/* 1014 */       this.priceInfo = priceInfo;
/*      */     }
/*      */     
/*      */     public void modifyMerchantRecipeList(MerchantRecipeList recipeList, Random random) {
/*      */       ItemStack itemstack, itemstack1;
/* 1019 */       int i = 1;
/*      */       
/* 1021 */       if (this.priceInfo != null)
/*      */       {
/* 1023 */         i = this.priceInfo.getPrice(random);
/*      */       }
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1029 */       if (i < 0) {
/*      */         
/* 1031 */         itemstack = new ItemStack(Items.emerald, 1, 0);
/* 1032 */         itemstack1 = new ItemStack(this.itemToBuy.getItem(), -i, this.itemToBuy.getMetadata());
/*      */       }
/*      */       else {
/*      */         
/* 1036 */         itemstack = new ItemStack(Items.emerald, i, 0);
/* 1037 */         itemstack1 = new ItemStack(this.itemToBuy.getItem(), 1, this.itemToBuy.getMetadata());
/*      */       } 
/*      */       
/* 1040 */       recipeList.add(new MerchantRecipe(itemstack, itemstack1));
/*      */     }
/*      */   }
/*      */   
/*      */   static class PriceInfo
/*      */     extends Tuple<Integer, Integer>
/*      */   {
/*      */     public PriceInfo(int p_i45810_1_, int p_i45810_2_) {
/* 1048 */       super(Integer.valueOf(p_i45810_1_), Integer.valueOf(p_i45810_2_));
/*      */     }
/*      */ 
/*      */     
/*      */     public int getPrice(Random rand) {
/* 1053 */       return (((Integer)getFirst()).intValue() >= ((Integer)getSecond()).intValue()) ? ((Integer)getFirst()).intValue() : (((Integer)getFirst()).intValue() + rand.nextInt(((Integer)getSecond()).intValue() - ((Integer)getFirst()).intValue() + 1));
/*      */     }
/*      */   }
/*      */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\minecraft\entity\passive\EntityVillager.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */