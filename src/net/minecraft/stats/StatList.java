/*     */ package net.minecraft.stats;
/*     */ 
/*     */ import com.google.common.collect.Lists;
/*     */ import com.google.common.collect.Maps;
/*     */ import com.google.common.collect.Sets;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.entity.EntityList;
/*     */ import net.minecraft.init.Blocks;
/*     */ import net.minecraft.item.Item;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.item.crafting.CraftingManager;
/*     */ import net.minecraft.item.crafting.FurnaceRecipes;
/*     */ import net.minecraft.item.crafting.IRecipe;
/*     */ import net.minecraft.util.ChatComponentTranslation;
/*     */ import net.minecraft.util.IChatComponent;
/*     */ import net.minecraft.util.ResourceLocation;
/*     */ 
/*     */ 
/*     */ public class StatList
/*     */ {
/*  24 */   protected static Map<String, StatBase> oneShotStats = Maps.newHashMap();
/*  25 */   public static List<StatBase> allStats = Lists.newArrayList();
/*  26 */   public static List<StatBase> generalStats = Lists.newArrayList();
/*  27 */   public static List<StatCrafting> itemStats = Lists.newArrayList();
/*  28 */   public static List<StatCrafting> objectMineStats = Lists.newArrayList();
/*     */ 
/*     */   
/*  31 */   public static StatBase leaveGameStat = (new StatBasic("stat.leaveGame", (IChatComponent)new ChatComponentTranslation("stat.leaveGame", new Object[0]))).initIndependentStat().registerStat();
/*     */ 
/*     */   
/*  34 */   public static StatBase minutesPlayedStat = (new StatBasic("stat.playOneMinute", (IChatComponent)new ChatComponentTranslation("stat.playOneMinute", new Object[0]), StatBase.timeStatType)).initIndependentStat().registerStat();
/*  35 */   public static StatBase timeSinceDeathStat = (new StatBasic("stat.timeSinceDeath", (IChatComponent)new ChatComponentTranslation("stat.timeSinceDeath", new Object[0]), StatBase.timeStatType)).initIndependentStat().registerStat();
/*     */ 
/*     */   
/*  38 */   public static StatBase distanceWalkedStat = (new StatBasic("stat.walkOneCm", (IChatComponent)new ChatComponentTranslation("stat.walkOneCm", new Object[0]), StatBase.distanceStatType)).initIndependentStat().registerStat();
/*  39 */   public static StatBase distanceCrouchedStat = (new StatBasic("stat.crouchOneCm", (IChatComponent)new ChatComponentTranslation("stat.crouchOneCm", new Object[0]), StatBase.distanceStatType)).initIndependentStat().registerStat();
/*  40 */   public static StatBase distanceSprintedStat = (new StatBasic("stat.sprintOneCm", (IChatComponent)new ChatComponentTranslation("stat.sprintOneCm", new Object[0]), StatBase.distanceStatType)).initIndependentStat().registerStat();
/*     */ 
/*     */   
/*  43 */   public static StatBase distanceSwumStat = (new StatBasic("stat.swimOneCm", (IChatComponent)new ChatComponentTranslation("stat.swimOneCm", new Object[0]), StatBase.distanceStatType)).initIndependentStat().registerStat();
/*     */ 
/*     */   
/*  46 */   public static StatBase distanceFallenStat = (new StatBasic("stat.fallOneCm", (IChatComponent)new ChatComponentTranslation("stat.fallOneCm", new Object[0]), StatBase.distanceStatType)).initIndependentStat().registerStat();
/*     */ 
/*     */   
/*  49 */   public static StatBase distanceClimbedStat = (new StatBasic("stat.climbOneCm", (IChatComponent)new ChatComponentTranslation("stat.climbOneCm", new Object[0]), StatBase.distanceStatType)).initIndependentStat().registerStat();
/*     */ 
/*     */   
/*  52 */   public static StatBase distanceFlownStat = (new StatBasic("stat.flyOneCm", (IChatComponent)new ChatComponentTranslation("stat.flyOneCm", new Object[0]), StatBase.distanceStatType)).initIndependentStat().registerStat();
/*     */ 
/*     */   
/*  55 */   public static StatBase distanceDoveStat = (new StatBasic("stat.diveOneCm", (IChatComponent)new ChatComponentTranslation("stat.diveOneCm", new Object[0]), StatBase.distanceStatType)).initIndependentStat().registerStat();
/*     */ 
/*     */   
/*  58 */   public static StatBase distanceByMinecartStat = (new StatBasic("stat.minecartOneCm", (IChatComponent)new ChatComponentTranslation("stat.minecartOneCm", new Object[0]), StatBase.distanceStatType)).initIndependentStat().registerStat();
/*     */ 
/*     */   
/*  61 */   public static StatBase distanceByBoatStat = (new StatBasic("stat.boatOneCm", (IChatComponent)new ChatComponentTranslation("stat.boatOneCm", new Object[0]), StatBase.distanceStatType)).initIndependentStat().registerStat();
/*     */ 
/*     */   
/*  64 */   public static StatBase distanceByPigStat = (new StatBasic("stat.pigOneCm", (IChatComponent)new ChatComponentTranslation("stat.pigOneCm", new Object[0]), StatBase.distanceStatType)).initIndependentStat().registerStat();
/*  65 */   public static StatBase distanceByHorseStat = (new StatBasic("stat.horseOneCm", (IChatComponent)new ChatComponentTranslation("stat.horseOneCm", new Object[0]), StatBase.distanceStatType)).initIndependentStat().registerStat();
/*     */ 
/*     */   
/*  68 */   public static StatBase jumpStat = (new StatBasic("stat.jump", (IChatComponent)new ChatComponentTranslation("stat.jump", new Object[0]))).initIndependentStat().registerStat();
/*     */ 
/*     */   
/*  71 */   public static StatBase dropStat = (new StatBasic("stat.drop", (IChatComponent)new ChatComponentTranslation("stat.drop", new Object[0]))).initIndependentStat().registerStat();
/*     */ 
/*     */   
/*  74 */   public static StatBase damageDealtStat = (new StatBasic("stat.damageDealt", (IChatComponent)new ChatComponentTranslation("stat.damageDealt", new Object[0]), StatBase.field_111202_k)).registerStat();
/*     */ 
/*     */   
/*  77 */   public static StatBase damageTakenStat = (new StatBasic("stat.damageTaken", (IChatComponent)new ChatComponentTranslation("stat.damageTaken", new Object[0]), StatBase.field_111202_k)).registerStat();
/*     */ 
/*     */   
/*  80 */   public static StatBase deathsStat = (new StatBasic("stat.deaths", (IChatComponent)new ChatComponentTranslation("stat.deaths", new Object[0]))).registerStat();
/*     */ 
/*     */   
/*  83 */   public static StatBase mobKillsStat = (new StatBasic("stat.mobKills", (IChatComponent)new ChatComponentTranslation("stat.mobKills", new Object[0]))).registerStat();
/*     */ 
/*     */   
/*  86 */   public static StatBase animalsBredStat = (new StatBasic("stat.animalsBred", (IChatComponent)new ChatComponentTranslation("stat.animalsBred", new Object[0]))).registerStat();
/*     */ 
/*     */   
/*  89 */   public static StatBase playerKillsStat = (new StatBasic("stat.playerKills", (IChatComponent)new ChatComponentTranslation("stat.playerKills", new Object[0]))).registerStat();
/*  90 */   public static StatBase fishCaughtStat = (new StatBasic("stat.fishCaught", (IChatComponent)new ChatComponentTranslation("stat.fishCaught", new Object[0]))).registerStat();
/*  91 */   public static StatBase junkFishedStat = (new StatBasic("stat.junkFished", (IChatComponent)new ChatComponentTranslation("stat.junkFished", new Object[0]))).registerStat();
/*  92 */   public static StatBase treasureFishedStat = (new StatBasic("stat.treasureFished", (IChatComponent)new ChatComponentTranslation("stat.treasureFished", new Object[0]))).registerStat();
/*  93 */   public static StatBase timesTalkedToVillagerStat = (new StatBasic("stat.talkedToVillager", (IChatComponent)new ChatComponentTranslation("stat.talkedToVillager", new Object[0]))).registerStat();
/*  94 */   public static StatBase timesTradedWithVillagerStat = (new StatBasic("stat.tradedWithVillager", (IChatComponent)new ChatComponentTranslation("stat.tradedWithVillager", new Object[0]))).registerStat();
/*  95 */   public static StatBase field_181724_H = (new StatBasic("stat.cakeSlicesEaten", (IChatComponent)new ChatComponentTranslation("stat.cakeSlicesEaten", new Object[0]))).registerStat();
/*  96 */   public static StatBase field_181725_I = (new StatBasic("stat.cauldronFilled", (IChatComponent)new ChatComponentTranslation("stat.cauldronFilled", new Object[0]))).registerStat();
/*  97 */   public static StatBase field_181726_J = (new StatBasic("stat.cauldronUsed", (IChatComponent)new ChatComponentTranslation("stat.cauldronUsed", new Object[0]))).registerStat();
/*  98 */   public static StatBase field_181727_K = (new StatBasic("stat.armorCleaned", (IChatComponent)new ChatComponentTranslation("stat.armorCleaned", new Object[0]))).registerStat();
/*  99 */   public static StatBase field_181728_L = (new StatBasic("stat.bannerCleaned", (IChatComponent)new ChatComponentTranslation("stat.bannerCleaned", new Object[0]))).registerStat();
/* 100 */   public static StatBase field_181729_M = (new StatBasic("stat.brewingstandInteraction", (IChatComponent)new ChatComponentTranslation("stat.brewingstandInteraction", new Object[0]))).registerStat();
/* 101 */   public static StatBase field_181730_N = (new StatBasic("stat.beaconInteraction", (IChatComponent)new ChatComponentTranslation("stat.beaconInteraction", new Object[0]))).registerStat();
/* 102 */   public static StatBase field_181731_O = (new StatBasic("stat.dropperInspected", (IChatComponent)new ChatComponentTranslation("stat.dropperInspected", new Object[0]))).registerStat();
/* 103 */   public static StatBase field_181732_P = (new StatBasic("stat.hopperInspected", (IChatComponent)new ChatComponentTranslation("stat.hopperInspected", new Object[0]))).registerStat();
/* 104 */   public static StatBase field_181733_Q = (new StatBasic("stat.dispenserInspected", (IChatComponent)new ChatComponentTranslation("stat.dispenserInspected", new Object[0]))).registerStat();
/* 105 */   public static StatBase field_181734_R = (new StatBasic("stat.noteblockPlayed", (IChatComponent)new ChatComponentTranslation("stat.noteblockPlayed", new Object[0]))).registerStat();
/* 106 */   public static StatBase field_181735_S = (new StatBasic("stat.noteblockTuned", (IChatComponent)new ChatComponentTranslation("stat.noteblockTuned", new Object[0]))).registerStat();
/* 107 */   public static StatBase field_181736_T = (new StatBasic("stat.flowerPotted", (IChatComponent)new ChatComponentTranslation("stat.flowerPotted", new Object[0]))).registerStat();
/* 108 */   public static StatBase field_181737_U = (new StatBasic("stat.trappedChestTriggered", (IChatComponent)new ChatComponentTranslation("stat.trappedChestTriggered", new Object[0]))).registerStat();
/* 109 */   public static StatBase field_181738_V = (new StatBasic("stat.enderchestOpened", (IChatComponent)new ChatComponentTranslation("stat.enderchestOpened", new Object[0]))).registerStat();
/* 110 */   public static StatBase field_181739_W = (new StatBasic("stat.itemEnchanted", (IChatComponent)new ChatComponentTranslation("stat.itemEnchanted", new Object[0]))).registerStat();
/* 111 */   public static StatBase field_181740_X = (new StatBasic("stat.recordPlayed", (IChatComponent)new ChatComponentTranslation("stat.recordPlayed", new Object[0]))).registerStat();
/* 112 */   public static StatBase field_181741_Y = (new StatBasic("stat.furnaceInteraction", (IChatComponent)new ChatComponentTranslation("stat.furnaceInteraction", new Object[0]))).registerStat();
/* 113 */   public static StatBase field_181742_Z = (new StatBasic("stat.craftingTableInteraction", (IChatComponent)new ChatComponentTranslation("stat.workbenchInteraction", new Object[0]))).registerStat();
/* 114 */   public static StatBase field_181723_aa = (new StatBasic("stat.chestOpened", (IChatComponent)new ChatComponentTranslation("stat.chestOpened", new Object[0]))).registerStat();
/* 115 */   public static final StatBase[] mineBlockStatArray = new StatBase[4096];
/*     */ 
/*     */   
/* 118 */   public static final StatBase[] objectCraftStats = new StatBase[32000];
/*     */ 
/*     */   
/* 121 */   public static final StatBase[] objectUseStats = new StatBase[32000];
/*     */ 
/*     */   
/* 124 */   public static final StatBase[] objectBreakStats = new StatBase[32000];
/*     */ 
/*     */   
/*     */   public static void init() {
/* 128 */     initMiningStats();
/* 129 */     initStats();
/* 130 */     initItemDepleteStats();
/* 131 */     initCraftableStats();
/* 132 */     AchievementList.init();
/* 133 */     EntityList.func_151514_a();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static void initCraftableStats() {
/* 142 */     Set<Item> set = Sets.newHashSet();
/*     */     
/* 144 */     for (IRecipe irecipe : CraftingManager.getInstance().getRecipeList()) {
/*     */       
/* 146 */       if (irecipe.getRecipeOutput() != null)
/*     */       {
/* 148 */         set.add(irecipe.getRecipeOutput().getItem());
/*     */       }
/*     */     } 
/*     */     
/* 152 */     for (ItemStack itemstack : FurnaceRecipes.instance().getSmeltingList().values())
/*     */     {
/* 154 */       set.add(itemstack.getItem());
/*     */     }
/*     */     
/* 157 */     for (Item item : set) {
/*     */       
/* 159 */       if (item != null) {
/*     */         
/* 161 */         int i = Item.getIdFromItem(item);
/* 162 */         String s = func_180204_a(item);
/*     */         
/* 164 */         if (s != null)
/*     */         {
/* 166 */           objectCraftStats[i] = (new StatCrafting("stat.craftItem.", s, (IChatComponent)new ChatComponentTranslation("stat.craftItem", new Object[] { (new ItemStack(item)).getChatComponent() }), item)).registerStat();
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/* 171 */     replaceAllSimilarBlocks(objectCraftStats);
/*     */   }
/*     */ 
/*     */   
/*     */   private static void initMiningStats() {
/* 176 */     for (Block block : Block.blockRegistry) {
/*     */       
/* 178 */       Item item = Item.getItemFromBlock(block);
/*     */       
/* 180 */       if (item != null) {
/*     */         
/* 182 */         int i = Block.getIdFromBlock(block);
/* 183 */         String s = func_180204_a(item);
/*     */         
/* 185 */         if (s != null && block.getEnableStats()) {
/*     */           
/* 187 */           mineBlockStatArray[i] = (new StatCrafting("stat.mineBlock.", s, (IChatComponent)new ChatComponentTranslation("stat.mineBlock", new Object[] { (new ItemStack(block)).getChatComponent() }), item)).registerStat();
/* 188 */           objectMineStats.add((StatCrafting)mineBlockStatArray[i]);
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 193 */     replaceAllSimilarBlocks(mineBlockStatArray);
/*     */   }
/*     */ 
/*     */   
/*     */   private static void initStats() {
/* 198 */     for (Item item : Item.itemRegistry) {
/*     */       
/* 200 */       if (item != null) {
/*     */         
/* 202 */         int i = Item.getIdFromItem(item);
/* 203 */         String s = func_180204_a(item);
/*     */         
/* 205 */         if (s != null) {
/*     */           
/* 207 */           objectUseStats[i] = (new StatCrafting("stat.useItem.", s, (IChatComponent)new ChatComponentTranslation("stat.useItem", new Object[] { (new ItemStack(item)).getChatComponent() }), item)).registerStat();
/*     */           
/* 209 */           if (!(item instanceof net.minecraft.item.ItemBlock))
/*     */           {
/* 211 */             itemStats.add((StatCrafting)objectUseStats[i]);
/*     */           }
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 217 */     replaceAllSimilarBlocks(objectUseStats);
/*     */   }
/*     */ 
/*     */   
/*     */   private static void initItemDepleteStats() {
/* 222 */     for (Item item : Item.itemRegistry) {
/*     */       
/* 224 */       if (item != null) {
/*     */         
/* 226 */         int i = Item.getIdFromItem(item);
/* 227 */         String s = func_180204_a(item);
/*     */         
/* 229 */         if (s != null && item.isDamageable())
/*     */         {
/* 231 */           objectBreakStats[i] = (new StatCrafting("stat.breakItem.", s, (IChatComponent)new ChatComponentTranslation("stat.breakItem", new Object[] { (new ItemStack(item)).getChatComponent() }), item)).registerStat();
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/* 236 */     replaceAllSimilarBlocks(objectBreakStats);
/*     */   }
/*     */ 
/*     */   
/*     */   private static String func_180204_a(Item p_180204_0_) {
/* 241 */     ResourceLocation resourcelocation = (ResourceLocation)Item.itemRegistry.getNameForObject(p_180204_0_);
/* 242 */     return (resourcelocation != null) ? resourcelocation.toString().replace(':', '.') : null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static void replaceAllSimilarBlocks(StatBase[] p_75924_0_) {
/* 250 */     mergeStatBases(p_75924_0_, (Block)Blocks.water, (Block)Blocks.flowing_water);
/* 251 */     mergeStatBases(p_75924_0_, (Block)Blocks.lava, (Block)Blocks.flowing_lava);
/* 252 */     mergeStatBases(p_75924_0_, Blocks.lit_pumpkin, Blocks.pumpkin);
/* 253 */     mergeStatBases(p_75924_0_, Blocks.lit_furnace, Blocks.furnace);
/* 254 */     mergeStatBases(p_75924_0_, Blocks.lit_redstone_ore, Blocks.redstone_ore);
/* 255 */     mergeStatBases(p_75924_0_, (Block)Blocks.powered_repeater, (Block)Blocks.unpowered_repeater);
/* 256 */     mergeStatBases(p_75924_0_, (Block)Blocks.powered_comparator, (Block)Blocks.unpowered_comparator);
/* 257 */     mergeStatBases(p_75924_0_, Blocks.redstone_torch, Blocks.unlit_redstone_torch);
/* 258 */     mergeStatBases(p_75924_0_, Blocks.lit_redstone_lamp, Blocks.redstone_lamp);
/* 259 */     mergeStatBases(p_75924_0_, (Block)Blocks.double_stone_slab, (Block)Blocks.stone_slab);
/* 260 */     mergeStatBases(p_75924_0_, (Block)Blocks.double_wooden_slab, (Block)Blocks.wooden_slab);
/* 261 */     mergeStatBases(p_75924_0_, (Block)Blocks.double_stone_slab2, (Block)Blocks.stone_slab2);
/* 262 */     mergeStatBases(p_75924_0_, (Block)Blocks.grass, Blocks.dirt);
/* 263 */     mergeStatBases(p_75924_0_, Blocks.farmland, Blocks.dirt);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static void mergeStatBases(StatBase[] statBaseIn, Block p_151180_1_, Block p_151180_2_) {
/* 271 */     int i = Block.getIdFromBlock(p_151180_1_);
/* 272 */     int j = Block.getIdFromBlock(p_151180_2_);
/*     */     
/* 274 */     if (statBaseIn[i] != null && statBaseIn[j] == null) {
/*     */       
/* 276 */       statBaseIn[j] = statBaseIn[i];
/*     */     }
/*     */     else {
/*     */       
/* 280 */       allStats.remove(statBaseIn[i]);
/* 281 */       objectMineStats.remove(statBaseIn[i]);
/* 282 */       generalStats.remove(statBaseIn[i]);
/* 283 */       statBaseIn[i] = statBaseIn[j];
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public static StatBase getStatKillEntity(EntityList.EntityEggInfo eggInfo) {
/* 289 */     String s = EntityList.getStringFromID(eggInfo.spawnedID);
/* 290 */     return (s == null) ? null : (new StatBase("stat.killEntity." + s, (IChatComponent)new ChatComponentTranslation("stat.entityKill", new Object[] { new ChatComponentTranslation("entity." + s + ".name", new Object[0]) }))).registerStat();
/*     */   }
/*     */ 
/*     */   
/*     */   public static StatBase getStatEntityKilledBy(EntityList.EntityEggInfo eggInfo) {
/* 295 */     String s = EntityList.getStringFromID(eggInfo.spawnedID);
/* 296 */     return (s == null) ? null : (new StatBase("stat.entityKilledBy." + s, (IChatComponent)new ChatComponentTranslation("stat.entityKilledBy", new Object[] { new ChatComponentTranslation("entity." + s + ".name", new Object[0]) }))).registerStat();
/*     */   }
/*     */ 
/*     */   
/*     */   public static StatBase getOneShotStat(String p_151177_0_) {
/* 301 */     return oneShotStats.get(p_151177_0_);
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\minecraft\stats\StatList.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */