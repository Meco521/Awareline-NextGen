/*      */ package net.minecraft.item;
/*      */ import com.google.common.base.Function;
/*      */ import com.google.common.collect.Multimap;
/*      */ import java.util.List;
/*      */ import java.util.Random;
/*      */ import net.minecraft.block.Block;
/*      */ import net.minecraft.block.BlockDirt;
/*      */ import net.minecraft.block.BlockDoublePlant;
/*      */ import net.minecraft.block.BlockFlower;
/*      */ import net.minecraft.block.BlockPlanks;
/*      */ import net.minecraft.block.BlockRedSandstone;
/*      */ import net.minecraft.block.BlockSand;
/*      */ import net.minecraft.block.BlockSilverfish;
/*      */ import net.minecraft.block.BlockStone;
/*      */ import net.minecraft.block.BlockStoneBrick;
/*      */ import net.minecraft.creativetab.CreativeTabs;
/*      */ import net.minecraft.entity.EntityLivingBase;
/*      */ import net.minecraft.entity.ai.attributes.AttributeModifier;
/*      */ import net.minecraft.entity.item.EntityMinecart;
/*      */ import net.minecraft.entity.player.EntityPlayer;
/*      */ import net.minecraft.init.Blocks;
/*      */ import net.minecraft.init.Items;
/*      */ import net.minecraft.potion.Potion;
/*      */ import net.minecraft.util.MathHelper;
/*      */ import net.minecraft.util.RegistryNamespaced;
/*      */ import net.minecraft.util.ResourceLocation;
/*      */ import net.minecraft.util.Vec3;
/*      */ import net.minecraft.world.World;
/*      */ 
/*      */ public class Item {
/*   31 */   public static final RegistryNamespaced<ResourceLocation, Item> itemRegistry = new RegistryNamespaced();
/*   32 */   private static final Map<Block, Item> BLOCK_TO_ITEM = Maps.newHashMap();
/*   33 */   protected static final UUID itemModifierUUID = UUID.fromString("CB3F55D3-645C-4F38-A497-9C13A33DB5CF");
/*      */   
/*      */   private CreativeTabs tabToDisplayOn;
/*      */   
/*   37 */   protected static Random itemRand = new Random();
/*      */ 
/*      */   
/*   40 */   protected int maxStackSize = 64;
/*      */ 
/*      */ 
/*      */   
/*      */   private int maxDamage;
/*      */ 
/*      */   
/*      */   protected boolean bFull3D;
/*      */ 
/*      */   
/*      */   protected boolean hasSubtypes;
/*      */ 
/*      */   
/*      */   private Item containerItem;
/*      */ 
/*      */   
/*      */   private String potionEffect;
/*      */ 
/*      */   
/*      */   private String unlocalizedName;
/*      */ 
/*      */ 
/*      */   
/*      */   public static int getIdFromItem(Item itemIn) {
/*   64 */     return (itemIn == null) ? 0 : itemRegistry.getIDForObject(itemIn);
/*      */   }
/*      */ 
/*      */   
/*      */   public static Item getItemById(int id) {
/*   69 */     return (Item)itemRegistry.getObjectById(id);
/*      */   }
/*      */ 
/*      */   
/*      */   public static Item getItemFromBlock(Block blockIn) {
/*   74 */     return BLOCK_TO_ITEM.get(blockIn);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static Item getByNameOrId(String id) {
/*   83 */     Item item = (Item)itemRegistry.getObject(new ResourceLocation(id));
/*      */     
/*   85 */     if (item == null) {
/*      */       
/*      */       try {
/*      */         
/*   89 */         return getItemById(Integer.parseInt(id));
/*      */       }
/*   91 */       catch (NumberFormatException numberFormatException) {}
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*   97 */     return item;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean updateItemStackNBT(NBTTagCompound nbt) {
/*  105 */     return false;
/*      */   }
/*      */ 
/*      */   
/*      */   public Item setMaxStackSize(int maxStackSize) {
/*  110 */     this.maxStackSize = maxStackSize;
/*  111 */     return this;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean onItemUse(ItemStack stack, EntityPlayer playerIn, World worldIn, BlockPos pos, EnumFacing side, float hitX, float hitY, float hitZ) {
/*  119 */     return false;
/*      */   }
/*      */ 
/*      */   
/*      */   public float getStrVsBlock(ItemStack stack, Block state) {
/*  124 */     return 1.0F;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public ItemStack onItemRightClick(ItemStack itemStackIn, World worldIn, EntityPlayer playerIn) {
/*  132 */     return itemStackIn;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public ItemStack onItemUseFinish(ItemStack stack, World worldIn, EntityPlayer playerIn) {
/*  141 */     return stack;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getItemStackLimit() {
/*  149 */     return this.maxStackSize;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getMetadata(int damage) {
/*  158 */     return 0;
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean getHasSubtypes() {
/*  163 */     return this.hasSubtypes;
/*      */   }
/*      */ 
/*      */   
/*      */   protected Item setHasSubtypes(boolean hasSubtypes) {
/*  168 */     this.hasSubtypes = hasSubtypes;
/*  169 */     return this;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getMaxDamage() {
/*  177 */     return this.maxDamage;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected Item setMaxDamage(int maxDamageIn) {
/*  185 */     this.maxDamage = maxDamageIn;
/*  186 */     return this;
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean isDamageable() {
/*  191 */     return (this.maxDamage > 0 && !this.hasSubtypes);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean hitEntity(ItemStack stack, EntityLivingBase target, EntityLivingBase attacker) {
/*  200 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean onBlockDestroyed(ItemStack stack, World worldIn, Block blockIn, BlockPos pos, EntityLivingBase playerIn) {
/*  208 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean canHarvestBlock(Block blockIn) {
/*  216 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean itemInteractionForEntity(ItemStack stack, EntityPlayer playerIn, EntityLivingBase target) {
/*  224 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Item setFull3D() {
/*  232 */     this.bFull3D = true;
/*  233 */     return this;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isFull3D() {
/*  241 */     return this.bFull3D;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean shouldRotateAroundWhenRendering() {
/*  250 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Item setUnlocalizedName(String unlocalizedName) {
/*  258 */     this.unlocalizedName = unlocalizedName;
/*  259 */     return this;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getUnlocalizedNameInefficiently(ItemStack stack) {
/*  268 */     String s = getUnlocalizedName(stack);
/*  269 */     return (s == null) ? "" : StatCollector.translateToLocal(s);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getUnlocalizedName() {
/*  277 */     return "item." + this.unlocalizedName;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getUnlocalizedName(ItemStack stack) {
/*  286 */     return "item." + this.unlocalizedName;
/*      */   }
/*      */ 
/*      */   
/*      */   public Item setContainerItem(Item containerItem) {
/*  291 */     this.containerItem = containerItem;
/*  292 */     return this;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean getShareTag() {
/*  300 */     return true;
/*      */   }
/*      */ 
/*      */   
/*      */   public Item getContainerItem() {
/*  305 */     return this.containerItem;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean hasContainerItem() {
/*  313 */     return (this.containerItem != null);
/*      */   }
/*      */ 
/*      */   
/*      */   public int getColorFromItemStack(ItemStack stack, int renderPass) {
/*  318 */     return 16777215;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void onUpdate(ItemStack stack, World worldIn, Entity entityIn, int itemSlot, boolean isSelected) {}
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void onCreated(ItemStack stack, World worldIn, EntityPlayer playerIn) {}
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isMap() {
/*  341 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public EnumAction getItemUseAction(ItemStack stack) {
/*  349 */     return EnumAction.NONE;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getMaxItemUseDuration(ItemStack stack) {
/*  357 */     return 0;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void onPlayerStoppedUsing(ItemStack stack, World worldIn, EntityPlayer playerIn, int timeLeft) {}
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected Item setPotionEffect(String potionEffect) {
/*  372 */     this.potionEffect = potionEffect;
/*  373 */     return this;
/*      */   }
/*      */ 
/*      */   
/*      */   public String getPotionEffect(ItemStack stack) {
/*  378 */     return this.potionEffect;
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean isPotionIngredient(ItemStack stack) {
/*  383 */     return (getPotionEffect(stack) != null);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void addInformation(ItemStack stack, EntityPlayer playerIn, List<String> tooltip, boolean advanced) {}
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getItemStackDisplayName(ItemStack stack) {
/*  395 */     return StatCollector.translateToLocal(getUnlocalizedNameInefficiently(stack) + ".name").trim();
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean hasEffect(ItemStack stack) {
/*  400 */     return stack.isItemEnchanted();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public EnumRarity getRarity(ItemStack stack) {
/*  408 */     return stack.isItemEnchanted() ? EnumRarity.RARE : EnumRarity.COMMON;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isItemTool(ItemStack stack) {
/*  416 */     return (this.maxStackSize == 1 && isDamageable());
/*      */   }
/*      */ 
/*      */   
/*      */   protected MovingObjectPosition getMovingObjectPositionFromPlayer(World worldIn, EntityPlayer playerIn, boolean useLiquids) {
/*  421 */     float f = playerIn.rotationPitch;
/*  422 */     float f1 = playerIn.rotationYaw;
/*  423 */     double d0 = playerIn.posX;
/*  424 */     double d1 = playerIn.posY + playerIn.getEyeHeight();
/*  425 */     double d2 = playerIn.posZ;
/*  426 */     Vec3 vec3 = new Vec3(d0, d1, d2);
/*  427 */     float f2 = MathHelper.cos(-f1 * 0.017453292F - 3.1415927F);
/*  428 */     float f3 = MathHelper.sin(-f1 * 0.017453292F - 3.1415927F);
/*  429 */     float f4 = -MathHelper.cos(-f * 0.017453292F);
/*  430 */     float f5 = MathHelper.sin(-f * 0.017453292F);
/*  431 */     float f6 = f3 * f4;
/*  432 */     float f7 = f2 * f4;
/*  433 */     double d3 = 5.0D;
/*  434 */     Vec3 vec31 = vec3.addVector(f6 * d3, f5 * d3, f7 * d3);
/*  435 */     return worldIn.rayTraceBlocks(vec3, vec31, useLiquids, !useLiquids, false);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getItemEnchantability() {
/*  443 */     return 0;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void getSubItems(Item itemIn, CreativeTabs tab, List<ItemStack> subItems) {
/*  451 */     subItems.add(new ItemStack(itemIn, 1, 0));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public CreativeTabs getCreativeTab() {
/*  459 */     return this.tabToDisplayOn;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Item setCreativeTab(CreativeTabs tab) {
/*  467 */     this.tabToDisplayOn = tab;
/*  468 */     return this;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean canItemEditBlocks() {
/*  477 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean getIsRepairable(ItemStack toRepair, ItemStack repair) {
/*  485 */     return false;
/*      */   }
/*      */ 
/*      */   
/*      */   public Multimap<String, AttributeModifier> getItemAttributeModifiers() {
/*  490 */     return (Multimap<String, AttributeModifier>)HashMultimap.create();
/*      */   }
/*      */ 
/*      */   
/*      */   public static void registerItems() {
/*  495 */     registerItemBlock(Blocks.stone, (new ItemMultiTexture(Blocks.stone, Blocks.stone, new Function<ItemStack, String>()
/*      */           {
/*      */             public String apply(ItemStack p_apply_1_)
/*      */             {
/*  499 */               return BlockStone.EnumType.byMetadata(p_apply_1_.getMetadata()).getUnlocalizedName();
/*      */             }
/*  501 */           })).setUnlocalizedName("stone"));
/*  502 */     registerItemBlock((Block)Blocks.grass, new ItemColored((Block)Blocks.grass, false));
/*  503 */     registerItemBlock(Blocks.dirt, (new ItemMultiTexture(Blocks.dirt, Blocks.dirt, new Function<ItemStack, String>()
/*      */           {
/*      */             public String apply(ItemStack p_apply_1_)
/*      */             {
/*  507 */               return BlockDirt.DirtType.byMetadata(p_apply_1_.getMetadata()).getUnlocalizedName();
/*      */             }
/*  509 */           })).setUnlocalizedName("dirt"));
/*  510 */     registerItemBlock(Blocks.cobblestone);
/*  511 */     registerItemBlock(Blocks.planks, (new ItemMultiTexture(Blocks.planks, Blocks.planks, new Function<ItemStack, String>()
/*      */           {
/*      */             public String apply(ItemStack p_apply_1_)
/*      */             {
/*  515 */               return BlockPlanks.EnumType.byMetadata(p_apply_1_.getMetadata()).getUnlocalizedName();
/*      */             }
/*  517 */           })).setUnlocalizedName("wood"));
/*  518 */     registerItemBlock(Blocks.sapling, (new ItemMultiTexture(Blocks.sapling, Blocks.sapling, new Function<ItemStack, String>()
/*      */           {
/*      */             public String apply(ItemStack p_apply_1_)
/*      */             {
/*  522 */               return BlockPlanks.EnumType.byMetadata(p_apply_1_.getMetadata()).getUnlocalizedName();
/*      */             }
/*  524 */           })).setUnlocalizedName("sapling"));
/*  525 */     registerItemBlock(Blocks.bedrock);
/*  526 */     registerItemBlock((Block)Blocks.sand, (new ItemMultiTexture((Block)Blocks.sand, (Block)Blocks.sand, new Function<ItemStack, String>()
/*      */           {
/*      */             public String apply(ItemStack p_apply_1_)
/*      */             {
/*  530 */               return BlockSand.EnumType.byMetadata(p_apply_1_.getMetadata()).getUnlocalizedName();
/*      */             }
/*  532 */           })).setUnlocalizedName("sand"));
/*  533 */     registerItemBlock(Blocks.gravel);
/*  534 */     registerItemBlock(Blocks.gold_ore);
/*  535 */     registerItemBlock(Blocks.iron_ore);
/*  536 */     registerItemBlock(Blocks.coal_ore);
/*  537 */     registerItemBlock(Blocks.log, (new ItemMultiTexture(Blocks.log, Blocks.log, new Function<ItemStack, String>()
/*      */           {
/*      */             public String apply(ItemStack p_apply_1_)
/*      */             {
/*  541 */               return BlockPlanks.EnumType.byMetadata(p_apply_1_.getMetadata()).getUnlocalizedName();
/*      */             }
/*  543 */           })).setUnlocalizedName("log"));
/*  544 */     registerItemBlock(Blocks.log2, (new ItemMultiTexture(Blocks.log2, Blocks.log2, new Function<ItemStack, String>()
/*      */           {
/*      */             public String apply(ItemStack p_apply_1_)
/*      */             {
/*  548 */               return BlockPlanks.EnumType.byMetadata(p_apply_1_.getMetadata() + 4).getUnlocalizedName();
/*      */             }
/*  550 */           })).setUnlocalizedName("log"));
/*  551 */     registerItemBlock((Block)Blocks.leaves, (new ItemLeaves(Blocks.leaves)).setUnlocalizedName("leaves"));
/*  552 */     registerItemBlock((Block)Blocks.leaves2, (new ItemLeaves(Blocks.leaves2)).setUnlocalizedName("leaves"));
/*  553 */     registerItemBlock(Blocks.sponge, (new ItemMultiTexture(Blocks.sponge, Blocks.sponge, new Function<ItemStack, String>()
/*      */           {
/*      */             public String apply(ItemStack p_apply_1_)
/*      */             {
/*  557 */               return ((p_apply_1_.getMetadata() & 0x1) == 1) ? "wet" : "dry";
/*      */             }
/*  559 */           })).setUnlocalizedName("sponge"));
/*  560 */     registerItemBlock(Blocks.glass);
/*  561 */     registerItemBlock(Blocks.lapis_ore);
/*  562 */     registerItemBlock(Blocks.lapis_block);
/*  563 */     registerItemBlock(Blocks.dispenser);
/*  564 */     registerItemBlock(Blocks.sandstone, (new ItemMultiTexture(Blocks.sandstone, Blocks.sandstone, new Function<ItemStack, String>()
/*      */           {
/*      */             public String apply(ItemStack p_apply_1_)
/*      */             {
/*  568 */               return BlockSandStone.EnumType.byMetadata(p_apply_1_.getMetadata()).getUnlocalizedName();
/*      */             }
/*  570 */           })).setUnlocalizedName("sandStone"));
/*  571 */     registerItemBlock(Blocks.noteblock);
/*  572 */     registerItemBlock(Blocks.golden_rail);
/*  573 */     registerItemBlock(Blocks.detector_rail);
/*  574 */     registerItemBlock((Block)Blocks.sticky_piston, new ItemPiston((Block)Blocks.sticky_piston));
/*  575 */     registerItemBlock(Blocks.web);
/*  576 */     registerItemBlock((Block)Blocks.tallgrass, (new ItemColored((Block)Blocks.tallgrass, true)).setSubtypeNames(new String[] { "shrub", "grass", "fern" }));
/*  577 */     registerItemBlock((Block)Blocks.deadbush);
/*  578 */     registerItemBlock((Block)Blocks.piston, new ItemPiston((Block)Blocks.piston));
/*  579 */     registerItemBlock(Blocks.wool, (new ItemCloth(Blocks.wool)).setUnlocalizedName("cloth"));
/*  580 */     registerItemBlock((Block)Blocks.yellow_flower, (new ItemMultiTexture((Block)Blocks.yellow_flower, (Block)Blocks.yellow_flower, new Function<ItemStack, String>()
/*      */           {
/*      */             public String apply(ItemStack p_apply_1_)
/*      */             {
/*  584 */               return BlockFlower.EnumFlowerType.getType(BlockFlower.EnumFlowerColor.YELLOW, p_apply_1_.getMetadata()).getUnlocalizedName();
/*      */             }
/*  586 */           })).setUnlocalizedName("flower"));
/*  587 */     registerItemBlock((Block)Blocks.red_flower, (new ItemMultiTexture((Block)Blocks.red_flower, (Block)Blocks.red_flower, new Function<ItemStack, String>()
/*      */           {
/*      */             public String apply(ItemStack p_apply_1_)
/*      */             {
/*  591 */               return BlockFlower.EnumFlowerType.getType(BlockFlower.EnumFlowerColor.RED, p_apply_1_.getMetadata()).getUnlocalizedName();
/*      */             }
/*  593 */           })).setUnlocalizedName("rose"));
/*  594 */     registerItemBlock((Block)Blocks.brown_mushroom);
/*  595 */     registerItemBlock((Block)Blocks.red_mushroom);
/*  596 */     registerItemBlock(Blocks.gold_block);
/*  597 */     registerItemBlock(Blocks.iron_block);
/*  598 */     registerItemBlock((Block)Blocks.stone_slab, (new ItemSlab((Block)Blocks.stone_slab, Blocks.stone_slab, Blocks.double_stone_slab)).setUnlocalizedName("stoneSlab"));
/*  599 */     registerItemBlock(Blocks.brick_block);
/*  600 */     registerItemBlock(Blocks.tnt);
/*  601 */     registerItemBlock(Blocks.bookshelf);
/*  602 */     registerItemBlock(Blocks.mossy_cobblestone);
/*  603 */     registerItemBlock(Blocks.obsidian);
/*  604 */     registerItemBlock(Blocks.torch);
/*  605 */     registerItemBlock(Blocks.mob_spawner);
/*  606 */     registerItemBlock(Blocks.oak_stairs);
/*  607 */     registerItemBlock((Block)Blocks.chest);
/*  608 */     registerItemBlock(Blocks.diamond_ore);
/*  609 */     registerItemBlock(Blocks.diamond_block);
/*  610 */     registerItemBlock(Blocks.crafting_table);
/*  611 */     registerItemBlock(Blocks.farmland);
/*  612 */     registerItemBlock(Blocks.furnace);
/*  613 */     registerItemBlock(Blocks.lit_furnace);
/*  614 */     registerItemBlock(Blocks.ladder);
/*  615 */     registerItemBlock(Blocks.rail);
/*  616 */     registerItemBlock(Blocks.stone_stairs);
/*  617 */     registerItemBlock(Blocks.lever);
/*  618 */     registerItemBlock(Blocks.stone_pressure_plate);
/*  619 */     registerItemBlock(Blocks.wooden_pressure_plate);
/*  620 */     registerItemBlock(Blocks.redstone_ore);
/*  621 */     registerItemBlock(Blocks.redstone_torch);
/*  622 */     registerItemBlock(Blocks.stone_button);
/*  623 */     registerItemBlock(Blocks.snow_layer, new ItemSnow(Blocks.snow_layer));
/*  624 */     registerItemBlock(Blocks.ice);
/*  625 */     registerItemBlock(Blocks.snow);
/*  626 */     registerItemBlock((Block)Blocks.cactus);
/*  627 */     registerItemBlock(Blocks.clay);
/*  628 */     registerItemBlock(Blocks.jukebox);
/*  629 */     registerItemBlock(Blocks.oak_fence);
/*  630 */     registerItemBlock(Blocks.spruce_fence);
/*  631 */     registerItemBlock(Blocks.birch_fence);
/*  632 */     registerItemBlock(Blocks.jungle_fence);
/*  633 */     registerItemBlock(Blocks.dark_oak_fence);
/*  634 */     registerItemBlock(Blocks.acacia_fence);
/*  635 */     registerItemBlock(Blocks.pumpkin);
/*  636 */     registerItemBlock(Blocks.netherrack);
/*  637 */     registerItemBlock(Blocks.soul_sand);
/*  638 */     registerItemBlock(Blocks.glowstone);
/*  639 */     registerItemBlock(Blocks.lit_pumpkin);
/*  640 */     registerItemBlock(Blocks.trapdoor);
/*  641 */     registerItemBlock(Blocks.monster_egg, (new ItemMultiTexture(Blocks.monster_egg, Blocks.monster_egg, new Function<ItemStack, String>()
/*      */           {
/*      */             public String apply(ItemStack p_apply_1_)
/*      */             {
/*  645 */               return BlockSilverfish.EnumType.byMetadata(p_apply_1_.getMetadata()).getUnlocalizedName();
/*      */             }
/*  647 */           })).setUnlocalizedName("monsterStoneEgg"));
/*  648 */     registerItemBlock(Blocks.stonebrick, (new ItemMultiTexture(Blocks.stonebrick, Blocks.stonebrick, new Function<ItemStack, String>()
/*      */           {
/*      */             public String apply(ItemStack p_apply_1_)
/*      */             {
/*  652 */               return BlockStoneBrick.EnumType.byMetadata(p_apply_1_.getMetadata()).getUnlocalizedName();
/*      */             }
/*  654 */           })).setUnlocalizedName("stonebricksmooth"));
/*  655 */     registerItemBlock(Blocks.brown_mushroom_block);
/*  656 */     registerItemBlock(Blocks.red_mushroom_block);
/*  657 */     registerItemBlock(Blocks.iron_bars);
/*  658 */     registerItemBlock(Blocks.glass_pane);
/*  659 */     registerItemBlock(Blocks.melon_block);
/*  660 */     registerItemBlock(Blocks.vine, new ItemColored(Blocks.vine, false));
/*  661 */     registerItemBlock(Blocks.oak_fence_gate);
/*  662 */     registerItemBlock(Blocks.spruce_fence_gate);
/*  663 */     registerItemBlock(Blocks.birch_fence_gate);
/*  664 */     registerItemBlock(Blocks.jungle_fence_gate);
/*  665 */     registerItemBlock(Blocks.dark_oak_fence_gate);
/*  666 */     registerItemBlock(Blocks.acacia_fence_gate);
/*  667 */     registerItemBlock(Blocks.brick_stairs);
/*  668 */     registerItemBlock(Blocks.stone_brick_stairs);
/*  669 */     registerItemBlock((Block)Blocks.mycelium);
/*  670 */     registerItemBlock(Blocks.waterlily, new ItemLilyPad(Blocks.waterlily));
/*  671 */     registerItemBlock(Blocks.nether_brick);
/*  672 */     registerItemBlock(Blocks.nether_brick_fence);
/*  673 */     registerItemBlock(Blocks.nether_brick_stairs);
/*  674 */     registerItemBlock(Blocks.enchanting_table);
/*  675 */     registerItemBlock(Blocks.end_portal_frame);
/*  676 */     registerItemBlock(Blocks.end_stone);
/*  677 */     registerItemBlock(Blocks.dragon_egg);
/*  678 */     registerItemBlock(Blocks.redstone_lamp);
/*  679 */     registerItemBlock((Block)Blocks.wooden_slab, (new ItemSlab((Block)Blocks.wooden_slab, Blocks.wooden_slab, Blocks.double_wooden_slab)).setUnlocalizedName("woodSlab"));
/*  680 */     registerItemBlock(Blocks.sandstone_stairs);
/*  681 */     registerItemBlock(Blocks.emerald_ore);
/*  682 */     registerItemBlock(Blocks.ender_chest);
/*  683 */     registerItemBlock((Block)Blocks.tripwire_hook);
/*  684 */     registerItemBlock(Blocks.emerald_block);
/*  685 */     registerItemBlock(Blocks.spruce_stairs);
/*  686 */     registerItemBlock(Blocks.birch_stairs);
/*  687 */     registerItemBlock(Blocks.jungle_stairs);
/*  688 */     registerItemBlock(Blocks.command_block);
/*  689 */     registerItemBlock((Block)Blocks.beacon);
/*  690 */     registerItemBlock(Blocks.cobblestone_wall, (new ItemMultiTexture(Blocks.cobblestone_wall, Blocks.cobblestone_wall, new Function<ItemStack, String>()
/*      */           {
/*      */             public String apply(ItemStack p_apply_1_)
/*      */             {
/*  694 */               return BlockWall.EnumType.byMetadata(p_apply_1_.getMetadata()).getUnlocalizedName();
/*      */             }
/*  696 */           })).setUnlocalizedName("cobbleWall"));
/*  697 */     registerItemBlock(Blocks.wooden_button);
/*  698 */     registerItemBlock(Blocks.anvil, (new ItemAnvilBlock(Blocks.anvil)).setUnlocalizedName("anvil"));
/*  699 */     registerItemBlock(Blocks.trapped_chest);
/*  700 */     registerItemBlock(Blocks.light_weighted_pressure_plate);
/*  701 */     registerItemBlock(Blocks.heavy_weighted_pressure_plate);
/*  702 */     registerItemBlock((Block)Blocks.daylight_detector);
/*  703 */     registerItemBlock(Blocks.redstone_block);
/*  704 */     registerItemBlock(Blocks.quartz_ore);
/*  705 */     registerItemBlock((Block)Blocks.hopper);
/*  706 */     registerItemBlock(Blocks.quartz_block, (new ItemMultiTexture(Blocks.quartz_block, Blocks.quartz_block, new String[] { "default", "chiseled", "lines" })).setUnlocalizedName("quartzBlock"));
/*  707 */     registerItemBlock(Blocks.quartz_stairs);
/*  708 */     registerItemBlock(Blocks.activator_rail);
/*  709 */     registerItemBlock(Blocks.dropper);
/*  710 */     registerItemBlock(Blocks.stained_hardened_clay, (new ItemCloth(Blocks.stained_hardened_clay)).setUnlocalizedName("clayHardenedStained"));
/*  711 */     registerItemBlock(Blocks.barrier);
/*  712 */     registerItemBlock(Blocks.iron_trapdoor);
/*  713 */     registerItemBlock(Blocks.hay_block);
/*  714 */     registerItemBlock(Blocks.carpet, (new ItemCloth(Blocks.carpet)).setUnlocalizedName("woolCarpet"));
/*  715 */     registerItemBlock(Blocks.hardened_clay);
/*  716 */     registerItemBlock(Blocks.coal_block);
/*  717 */     registerItemBlock(Blocks.packed_ice);
/*  718 */     registerItemBlock(Blocks.acacia_stairs);
/*  719 */     registerItemBlock(Blocks.dark_oak_stairs);
/*  720 */     registerItemBlock(Blocks.slime_block);
/*  721 */     registerItemBlock((Block)Blocks.double_plant, (new ItemDoublePlant((Block)Blocks.double_plant, (Block)Blocks.double_plant, new Function<ItemStack, String>()
/*      */           {
/*      */             public String apply(ItemStack p_apply_1_)
/*      */             {
/*  725 */               return BlockDoublePlant.EnumPlantType.byMetadata(p_apply_1_.getMetadata()).getUnlocalizedName();
/*      */             }
/*  727 */           })).setUnlocalizedName("doublePlant"));
/*  728 */     registerItemBlock((Block)Blocks.stained_glass, (new ItemCloth((Block)Blocks.stained_glass)).setUnlocalizedName("stainedGlass"));
/*  729 */     registerItemBlock((Block)Blocks.stained_glass_pane, (new ItemCloth((Block)Blocks.stained_glass_pane)).setUnlocalizedName("stainedGlassPane"));
/*  730 */     registerItemBlock(Blocks.prismarine, (new ItemMultiTexture(Blocks.prismarine, Blocks.prismarine, new Function<ItemStack, String>()
/*      */           {
/*      */             public String apply(ItemStack p_apply_1_)
/*      */             {
/*  734 */               return BlockPrismarine.EnumType.byMetadata(p_apply_1_.getMetadata()).getUnlocalizedName();
/*      */             }
/*  736 */           })).setUnlocalizedName("prismarine"));
/*  737 */     registerItemBlock(Blocks.sea_lantern);
/*  738 */     registerItemBlock(Blocks.red_sandstone, (new ItemMultiTexture(Blocks.red_sandstone, Blocks.red_sandstone, new Function<ItemStack, String>()
/*      */           {
/*      */             public String apply(ItemStack p_apply_1_)
/*      */             {
/*  742 */               return BlockRedSandstone.EnumType.byMetadata(p_apply_1_.getMetadata()).getUnlocalizedName();
/*      */             }
/*  744 */           })).setUnlocalizedName("redSandStone"));
/*  745 */     registerItemBlock(Blocks.red_sandstone_stairs);
/*  746 */     registerItemBlock((Block)Blocks.stone_slab2, (new ItemSlab((Block)Blocks.stone_slab2, Blocks.stone_slab2, Blocks.double_stone_slab2)).setUnlocalizedName("stoneSlab2"));
/*  747 */     registerItem(256, "iron_shovel", (new ItemSpade(ToolMaterial.IRON)).setUnlocalizedName("shovelIron"));
/*  748 */     registerItem(257, "iron_pickaxe", (new ItemPickaxe(ToolMaterial.IRON)).setUnlocalizedName("pickaxeIron"));
/*  749 */     registerItem(258, "iron_axe", (new ItemAxe(ToolMaterial.IRON)).setUnlocalizedName("hatchetIron"));
/*  750 */     registerItem(259, "flint_and_steel", (new ItemFlintAndSteel()).setUnlocalizedName("flintAndSteel"));
/*  751 */     registerItem(260, "apple", (new ItemFood(4, 0.3F, false)).setUnlocalizedName("apple"));
/*  752 */     registerItem(261, "bow", (new ItemBow()).setUnlocalizedName("bow"));
/*  753 */     registerItem(262, "arrow", (new Item()).setUnlocalizedName("arrow").setCreativeTab(CreativeTabs.tabCombat));
/*  754 */     registerItem(263, "coal", (new ItemCoal()).setUnlocalizedName("coal"));
/*  755 */     registerItem(264, "diamond", (new Item()).setUnlocalizedName("diamond").setCreativeTab(CreativeTabs.tabMaterials));
/*  756 */     registerItem(265, "iron_ingot", (new Item()).setUnlocalizedName("ingotIron").setCreativeTab(CreativeTabs.tabMaterials));
/*  757 */     registerItem(266, "gold_ingot", (new Item()).setUnlocalizedName("ingotGold").setCreativeTab(CreativeTabs.tabMaterials));
/*  758 */     registerItem(267, "iron_sword", (new ItemSword(ToolMaterial.IRON)).setUnlocalizedName("swordIron"));
/*  759 */     registerItem(268, "wooden_sword", (new ItemSword(ToolMaterial.WOOD)).setUnlocalizedName("swordWood"));
/*  760 */     registerItem(269, "wooden_shovel", (new ItemSpade(ToolMaterial.WOOD)).setUnlocalizedName("shovelWood"));
/*  761 */     registerItem(270, "wooden_pickaxe", (new ItemPickaxe(ToolMaterial.WOOD)).setUnlocalizedName("pickaxeWood"));
/*  762 */     registerItem(271, "wooden_axe", (new ItemAxe(ToolMaterial.WOOD)).setUnlocalizedName("hatchetWood"));
/*  763 */     registerItem(272, "stone_sword", (new ItemSword(ToolMaterial.STONE)).setUnlocalizedName("swordStone"));
/*  764 */     registerItem(273, "stone_shovel", (new ItemSpade(ToolMaterial.STONE)).setUnlocalizedName("shovelStone"));
/*  765 */     registerItem(274, "stone_pickaxe", (new ItemPickaxe(ToolMaterial.STONE)).setUnlocalizedName("pickaxeStone"));
/*  766 */     registerItem(275, "stone_axe", (new ItemAxe(ToolMaterial.STONE)).setUnlocalizedName("hatchetStone"));
/*  767 */     registerItem(276, "diamond_sword", (new ItemSword(ToolMaterial.EMERALD)).setUnlocalizedName("swordDiamond"));
/*  768 */     registerItem(277, "diamond_shovel", (new ItemSpade(ToolMaterial.EMERALD)).setUnlocalizedName("shovelDiamond"));
/*  769 */     registerItem(278, "diamond_pickaxe", (new ItemPickaxe(ToolMaterial.EMERALD)).setUnlocalizedName("pickaxeDiamond"));
/*  770 */     registerItem(279, "diamond_axe", (new ItemAxe(ToolMaterial.EMERALD)).setUnlocalizedName("hatchetDiamond"));
/*  771 */     registerItem(280, "stick", (new Item()).setFull3D().setUnlocalizedName("stick").setCreativeTab(CreativeTabs.tabMaterials));
/*  772 */     registerItem(281, "bowl", (new Item()).setUnlocalizedName("bowl").setCreativeTab(CreativeTabs.tabMaterials));
/*  773 */     registerItem(282, "mushroom_stew", (new ItemSoup(6)).setUnlocalizedName("mushroomStew"));
/*  774 */     registerItem(283, "golden_sword", (new ItemSword(ToolMaterial.GOLD)).setUnlocalizedName("swordGold"));
/*  775 */     registerItem(284, "golden_shovel", (new ItemSpade(ToolMaterial.GOLD)).setUnlocalizedName("shovelGold"));
/*  776 */     registerItem(285, "golden_pickaxe", (new ItemPickaxe(ToolMaterial.GOLD)).setUnlocalizedName("pickaxeGold"));
/*  777 */     registerItem(286, "golden_axe", (new ItemAxe(ToolMaterial.GOLD)).setUnlocalizedName("hatchetGold"));
/*  778 */     registerItem(287, "string", (new ItemReed(Blocks.tripwire)).setUnlocalizedName("string").setCreativeTab(CreativeTabs.tabMaterials));
/*  779 */     registerItem(288, "feather", (new Item()).setUnlocalizedName("feather").setCreativeTab(CreativeTabs.tabMaterials));
/*  780 */     registerItem(289, "gunpowder", (new Item()).setUnlocalizedName("sulphur").setPotionEffect("+14&13-13").setCreativeTab(CreativeTabs.tabMaterials));
/*  781 */     registerItem(290, "wooden_hoe", (new ItemHoe(ToolMaterial.WOOD)).setUnlocalizedName("hoeWood"));
/*  782 */     registerItem(291, "stone_hoe", (new ItemHoe(ToolMaterial.STONE)).setUnlocalizedName("hoeStone"));
/*  783 */     registerItem(292, "iron_hoe", (new ItemHoe(ToolMaterial.IRON)).setUnlocalizedName("hoeIron"));
/*  784 */     registerItem(293, "diamond_hoe", (new ItemHoe(ToolMaterial.EMERALD)).setUnlocalizedName("hoeDiamond"));
/*  785 */     registerItem(294, "golden_hoe", (new ItemHoe(ToolMaterial.GOLD)).setUnlocalizedName("hoeGold"));
/*  786 */     registerItem(295, "wheat_seeds", (new ItemSeeds(Blocks.wheat, Blocks.farmland)).setUnlocalizedName("seeds"));
/*  787 */     registerItem(296, "wheat", (new Item()).setUnlocalizedName("wheat").setCreativeTab(CreativeTabs.tabMaterials));
/*  788 */     registerItem(297, "bread", (new ItemFood(5, 0.6F, false)).setUnlocalizedName("bread"));
/*  789 */     registerItem(298, "leather_helmet", (new ItemArmor(ItemArmor.ArmorMaterial.LEATHER, 0, 0)).setUnlocalizedName("helmetCloth"));
/*  790 */     registerItem(299, "leather_chestplate", (new ItemArmor(ItemArmor.ArmorMaterial.LEATHER, 0, 1)).setUnlocalizedName("chestplateCloth"));
/*  791 */     registerItem(300, "leather_leggings", (new ItemArmor(ItemArmor.ArmorMaterial.LEATHER, 0, 2)).setUnlocalizedName("leggingsCloth"));
/*  792 */     registerItem(301, "leather_boots", (new ItemArmor(ItemArmor.ArmorMaterial.LEATHER, 0, 3)).setUnlocalizedName("bootsCloth"));
/*  793 */     registerItem(302, "chainmail_helmet", (new ItemArmor(ItemArmor.ArmorMaterial.CHAIN, 1, 0)).setUnlocalizedName("helmetChain"));
/*  794 */     registerItem(303, "chainmail_chestplate", (new ItemArmor(ItemArmor.ArmorMaterial.CHAIN, 1, 1)).setUnlocalizedName("chestplateChain"));
/*  795 */     registerItem(304, "chainmail_leggings", (new ItemArmor(ItemArmor.ArmorMaterial.CHAIN, 1, 2)).setUnlocalizedName("leggingsChain"));
/*  796 */     registerItem(305, "chainmail_boots", (new ItemArmor(ItemArmor.ArmorMaterial.CHAIN, 1, 3)).setUnlocalizedName("bootsChain"));
/*  797 */     registerItem(306, "iron_helmet", (new ItemArmor(ItemArmor.ArmorMaterial.IRON, 2, 0)).setUnlocalizedName("helmetIron"));
/*  798 */     registerItem(307, "iron_chestplate", (new ItemArmor(ItemArmor.ArmorMaterial.IRON, 2, 1)).setUnlocalizedName("chestplateIron"));
/*  799 */     registerItem(308, "iron_leggings", (new ItemArmor(ItemArmor.ArmorMaterial.IRON, 2, 2)).setUnlocalizedName("leggingsIron"));
/*  800 */     registerItem(309, "iron_boots", (new ItemArmor(ItemArmor.ArmorMaterial.IRON, 2, 3)).setUnlocalizedName("bootsIron"));
/*  801 */     registerItem(310, "diamond_helmet", (new ItemArmor(ItemArmor.ArmorMaterial.DIAMOND, 3, 0)).setUnlocalizedName("helmetDiamond"));
/*  802 */     registerItem(311, "diamond_chestplate", (new ItemArmor(ItemArmor.ArmorMaterial.DIAMOND, 3, 1)).setUnlocalizedName("chestplateDiamond"));
/*  803 */     registerItem(312, "diamond_leggings", (new ItemArmor(ItemArmor.ArmorMaterial.DIAMOND, 3, 2)).setUnlocalizedName("leggingsDiamond"));
/*  804 */     registerItem(313, "diamond_boots", (new ItemArmor(ItemArmor.ArmorMaterial.DIAMOND, 3, 3)).setUnlocalizedName("bootsDiamond"));
/*  805 */     registerItem(314, "golden_helmet", (new ItemArmor(ItemArmor.ArmorMaterial.GOLD, 4, 0)).setUnlocalizedName("helmetGold"));
/*  806 */     registerItem(315, "golden_chestplate", (new ItemArmor(ItemArmor.ArmorMaterial.GOLD, 4, 1)).setUnlocalizedName("chestplateGold"));
/*  807 */     registerItem(316, "golden_leggings", (new ItemArmor(ItemArmor.ArmorMaterial.GOLD, 4, 2)).setUnlocalizedName("leggingsGold"));
/*  808 */     registerItem(317, "golden_boots", (new ItemArmor(ItemArmor.ArmorMaterial.GOLD, 4, 3)).setUnlocalizedName("bootsGold"));
/*  809 */     registerItem(318, "flint", (new Item()).setUnlocalizedName("flint").setCreativeTab(CreativeTabs.tabMaterials));
/*  810 */     registerItem(319, "porkchop", (new ItemFood(3, 0.3F, true)).setUnlocalizedName("porkchopRaw"));
/*  811 */     registerItem(320, "cooked_porkchop", (new ItemFood(8, 0.8F, true)).setUnlocalizedName("porkchopCooked"));
/*  812 */     registerItem(321, "painting", (new ItemHangingEntity((Class)EntityPainting.class)).setUnlocalizedName("painting"));
/*  813 */     registerItem(322, "golden_apple", (new ItemAppleGold(4, 1.2F, false)).setAlwaysEdible().setPotionEffect(Potion.regeneration.id, 5, 1, 1.0F).setUnlocalizedName("appleGold"));
/*  814 */     registerItem(323, "sign", (new ItemSign()).setUnlocalizedName("sign"));
/*  815 */     registerItem(324, "wooden_door", (new ItemDoor(Blocks.oak_door)).setUnlocalizedName("doorOak"));
/*  816 */     Item item = (new ItemBucket(Blocks.air)).setUnlocalizedName("bucket").setMaxStackSize(16);
/*  817 */     registerItem(325, "bucket", item);
/*  818 */     registerItem(326, "water_bucket", (new ItemBucket((Block)Blocks.flowing_water)).setUnlocalizedName("bucketWater").setContainerItem(item));
/*  819 */     registerItem(327, "lava_bucket", (new ItemBucket((Block)Blocks.flowing_lava)).setUnlocalizedName("bucketLava").setContainerItem(item));
/*  820 */     registerItem(328, "minecart", (new ItemMinecart(EntityMinecart.EnumMinecartType.RIDEABLE)).setUnlocalizedName("minecart"));
/*  821 */     registerItem(329, "saddle", (new ItemSaddle()).setUnlocalizedName("saddle"));
/*  822 */     registerItem(330, "iron_door", (new ItemDoor(Blocks.iron_door)).setUnlocalizedName("doorIron"));
/*  823 */     registerItem(331, "redstone", (new ItemRedstone()).setUnlocalizedName("redstone").setPotionEffect("-5+6-7"));
/*  824 */     registerItem(332, "snowball", (new ItemSnowball()).setUnlocalizedName("snowball"));
/*  825 */     registerItem(333, "boat", (new ItemBoat()).setUnlocalizedName("boat"));
/*  826 */     registerItem(334, "leather", (new Item()).setUnlocalizedName("leather").setCreativeTab(CreativeTabs.tabMaterials));
/*  827 */     registerItem(335, "milk_bucket", (new ItemBucketMilk()).setUnlocalizedName("milk").setContainerItem(item));
/*  828 */     registerItem(336, "brick", (new Item()).setUnlocalizedName("brick").setCreativeTab(CreativeTabs.tabMaterials));
/*  829 */     registerItem(337, "clay_ball", (new Item()).setUnlocalizedName("clay").setCreativeTab(CreativeTabs.tabMaterials));
/*  830 */     registerItem(338, "reeds", (new ItemReed((Block)Blocks.reeds)).setUnlocalizedName("reeds").setCreativeTab(CreativeTabs.tabMaterials));
/*  831 */     registerItem(339, "paper", (new Item()).setUnlocalizedName("paper").setCreativeTab(CreativeTabs.tabMisc));
/*  832 */     registerItem(340, "book", (new ItemBook()).setUnlocalizedName("book").setCreativeTab(CreativeTabs.tabMisc));
/*  833 */     registerItem(341, "slime_ball", (new Item()).setUnlocalizedName("slimeball").setCreativeTab(CreativeTabs.tabMisc));
/*  834 */     registerItem(342, "chest_minecart", (new ItemMinecart(EntityMinecart.EnumMinecartType.CHEST)).setUnlocalizedName("minecartChest"));
/*  835 */     registerItem(343, "furnace_minecart", (new ItemMinecart(EntityMinecart.EnumMinecartType.FURNACE)).setUnlocalizedName("minecartFurnace"));
/*  836 */     registerItem(344, "egg", (new ItemEgg()).setUnlocalizedName("egg"));
/*  837 */     registerItem(345, "compass", (new Item()).setUnlocalizedName("compass").setCreativeTab(CreativeTabs.tabTools));
/*  838 */     registerItem(346, "fishing_rod", (new ItemFishingRod()).setUnlocalizedName("fishingRod"));
/*  839 */     registerItem(347, "clock", (new Item()).setUnlocalizedName("clock").setCreativeTab(CreativeTabs.tabTools));
/*  840 */     registerItem(348, "glowstone_dust", (new Item()).setUnlocalizedName("yellowDust").setPotionEffect("+5-6-7").setCreativeTab(CreativeTabs.tabMaterials));
/*  841 */     registerItem(349, "fish", (new ItemFishFood(false)).setUnlocalizedName("fish").setHasSubtypes(true));
/*  842 */     registerItem(350, "cooked_fish", (new ItemFishFood(true)).setUnlocalizedName("fish").setHasSubtypes(true));
/*  843 */     registerItem(351, "dye", (new ItemDye()).setUnlocalizedName("dyePowder"));
/*  844 */     registerItem(352, "bone", (new Item()).setUnlocalizedName("bone").setFull3D().setCreativeTab(CreativeTabs.tabMisc));
/*  845 */     registerItem(353, "sugar", (new Item()).setUnlocalizedName("sugar").setPotionEffect("-0+1-2-3&4-4+13").setCreativeTab(CreativeTabs.tabMaterials));
/*  846 */     registerItem(354, "cake", (new ItemReed(Blocks.cake)).setMaxStackSize(1).setUnlocalizedName("cake").setCreativeTab(CreativeTabs.tabFood));
/*  847 */     registerItem(355, "bed", (new ItemBed()).setMaxStackSize(1).setUnlocalizedName("bed"));
/*  848 */     registerItem(356, "repeater", (new ItemReed((Block)Blocks.unpowered_repeater)).setUnlocalizedName("diode").setCreativeTab(CreativeTabs.tabRedstone));
/*  849 */     registerItem(357, "cookie", (new ItemFood(2, 0.1F, false)).setUnlocalizedName("cookie"));
/*  850 */     registerItem(358, "filled_map", (new ItemMap()).setUnlocalizedName("map"));
/*  851 */     registerItem(359, "shears", (new ItemShears()).setUnlocalizedName("shears"));
/*  852 */     registerItem(360, "melon", (new ItemFood(2, 0.3F, false)).setUnlocalizedName("melon"));
/*  853 */     registerItem(361, "pumpkin_seeds", (new ItemSeeds(Blocks.pumpkin_stem, Blocks.farmland)).setUnlocalizedName("seeds_pumpkin"));
/*  854 */     registerItem(362, "melon_seeds", (new ItemSeeds(Blocks.melon_stem, Blocks.farmland)).setUnlocalizedName("seeds_melon"));
/*  855 */     registerItem(363, "beef", (new ItemFood(3, 0.3F, true)).setUnlocalizedName("beefRaw"));
/*  856 */     registerItem(364, "cooked_beef", (new ItemFood(8, 0.8F, true)).setUnlocalizedName("beefCooked"));
/*  857 */     registerItem(365, "chicken", (new ItemFood(2, 0.3F, true)).setPotionEffect(Potion.hunger.id, 30, 0, 0.3F).setUnlocalizedName("chickenRaw"));
/*  858 */     registerItem(366, "cooked_chicken", (new ItemFood(6, 0.6F, true)).setUnlocalizedName("chickenCooked"));
/*  859 */     registerItem(367, "rotten_flesh", (new ItemFood(4, 0.1F, true)).setPotionEffect(Potion.hunger.id, 30, 0, 0.8F).setUnlocalizedName("rottenFlesh"));
/*  860 */     registerItem(368, "ender_pearl", (new ItemEnderPearl()).setUnlocalizedName("enderPearl"));
/*  861 */     registerItem(369, "blaze_rod", (new Item()).setUnlocalizedName("blazeRod").setCreativeTab(CreativeTabs.tabMaterials).setFull3D());
/*  862 */     registerItem(370, "ghast_tear", (new Item()).setUnlocalizedName("ghastTear").setPotionEffect("+0-1-2-3&4-4+13").setCreativeTab(CreativeTabs.tabBrewing));
/*  863 */     registerItem(371, "gold_nugget", (new Item()).setUnlocalizedName("goldNugget").setCreativeTab(CreativeTabs.tabMaterials));
/*  864 */     registerItem(372, "nether_wart", (new ItemSeeds(Blocks.nether_wart, Blocks.soul_sand)).setUnlocalizedName("netherStalkSeeds").setPotionEffect("+4"));
/*  865 */     registerItem(373, "potion", (new ItemPotion()).setUnlocalizedName("potion"));
/*  866 */     registerItem(374, "glass_bottle", (new ItemGlassBottle()).setUnlocalizedName("glassBottle"));
/*  867 */     registerItem(375, "spider_eye", (new ItemFood(2, 0.8F, false)).setPotionEffect(Potion.poison.id, 5, 0, 1.0F).setUnlocalizedName("spiderEye").setPotionEffect("-0-1+2-3&4-4+13"));
/*  868 */     registerItem(376, "fermented_spider_eye", (new Item()).setUnlocalizedName("fermentedSpiderEye").setPotionEffect("-0+3-4+13").setCreativeTab(CreativeTabs.tabBrewing));
/*  869 */     registerItem(377, "blaze_powder", (new Item()).setUnlocalizedName("blazePowder").setPotionEffect("+0-1-2+3&4-4+13").setCreativeTab(CreativeTabs.tabBrewing));
/*  870 */     registerItem(378, "magma_cream", (new Item()).setUnlocalizedName("magmaCream").setPotionEffect("+0+1-2-3&4-4+13").setCreativeTab(CreativeTabs.tabBrewing));
/*  871 */     registerItem(379, "brewing_stand", (new ItemReed(Blocks.brewing_stand)).setUnlocalizedName("brewingStand").setCreativeTab(CreativeTabs.tabBrewing));
/*  872 */     registerItem(380, "cauldron", (new ItemReed((Block)Blocks.cauldron)).setUnlocalizedName("cauldron").setCreativeTab(CreativeTabs.tabBrewing));
/*  873 */     registerItem(381, "ender_eye", (new ItemEnderEye()).setUnlocalizedName("eyeOfEnder"));
/*  874 */     registerItem(382, "speckled_melon", (new Item()).setUnlocalizedName("speckledMelon").setPotionEffect("+0-1+2-3&4-4+13").setCreativeTab(CreativeTabs.tabBrewing));
/*  875 */     registerItem(383, "spawn_egg", (new ItemMonsterPlacer()).setUnlocalizedName("monsterPlacer"));
/*  876 */     registerItem(384, "experience_bottle", (new ItemExpBottle()).setUnlocalizedName("expBottle"));
/*  877 */     registerItem(385, "fire_charge", (new ItemFireball()).setUnlocalizedName("fireball"));
/*  878 */     registerItem(386, "writable_book", (new ItemWritableBook()).setUnlocalizedName("writingBook").setCreativeTab(CreativeTabs.tabMisc));
/*  879 */     registerItem(387, "written_book", (new ItemEditableBook()).setUnlocalizedName("writtenBook").setMaxStackSize(16));
/*  880 */     registerItem(388, "emerald", (new Item()).setUnlocalizedName("emerald").setCreativeTab(CreativeTabs.tabMaterials));
/*  881 */     registerItem(389, "item_frame", (new ItemHangingEntity((Class)EntityItemFrame.class)).setUnlocalizedName("frame"));
/*  882 */     registerItem(390, "flower_pot", (new ItemReed(Blocks.flower_pot)).setUnlocalizedName("flowerPot").setCreativeTab(CreativeTabs.tabDecorations));
/*  883 */     registerItem(391, "carrot", (new ItemSeedFood(3, 0.6F, Blocks.carrots, Blocks.farmland)).setUnlocalizedName("carrots"));
/*  884 */     registerItem(392, "potato", (new ItemSeedFood(1, 0.3F, Blocks.potatoes, Blocks.farmland)).setUnlocalizedName("potato"));
/*  885 */     registerItem(393, "baked_potato", (new ItemFood(5, 0.6F, false)).setUnlocalizedName("potatoBaked"));
/*  886 */     registerItem(394, "poisonous_potato", (new ItemFood(2, 0.3F, false)).setPotionEffect(Potion.poison.id, 5, 0, 0.6F).setUnlocalizedName("potatoPoisonous"));
/*  887 */     registerItem(395, "map", (new ItemEmptyMap()).setUnlocalizedName("emptyMap"));
/*  888 */     registerItem(396, "golden_carrot", (new ItemFood(6, 1.2F, false)).setUnlocalizedName("carrotGolden").setPotionEffect("-0+1+2-3+13&4-4").setCreativeTab(CreativeTabs.tabBrewing));
/*  889 */     registerItem(397, "skull", (new ItemSkull()).setUnlocalizedName("skull"));
/*  890 */     registerItem(398, "carrot_on_a_stick", (new ItemCarrotOnAStick()).setUnlocalizedName("carrotOnAStick"));
/*  891 */     registerItem(399, "nether_star", (new ItemSimpleFoiled()).setUnlocalizedName("netherStar").setCreativeTab(CreativeTabs.tabMaterials));
/*  892 */     registerItem(400, "pumpkin_pie", (new ItemFood(8, 0.3F, false)).setUnlocalizedName("pumpkinPie").setCreativeTab(CreativeTabs.tabFood));
/*  893 */     registerItem(401, "fireworks", (new ItemFirework()).setUnlocalizedName("fireworks"));
/*  894 */     registerItem(402, "firework_charge", (new ItemFireworkCharge()).setUnlocalizedName("fireworksCharge").setCreativeTab(CreativeTabs.tabMisc));
/*  895 */     registerItem(403, "enchanted_book", (new ItemEnchantedBook()).setMaxStackSize(1).setUnlocalizedName("enchantedBook"));
/*  896 */     registerItem(404, "comparator", (new ItemReed((Block)Blocks.unpowered_comparator)).setUnlocalizedName("comparator").setCreativeTab(CreativeTabs.tabRedstone));
/*  897 */     registerItem(405, "netherbrick", (new Item()).setUnlocalizedName("netherbrick").setCreativeTab(CreativeTabs.tabMaterials));
/*  898 */     registerItem(406, "quartz", (new Item()).setUnlocalizedName("netherquartz").setCreativeTab(CreativeTabs.tabMaterials));
/*  899 */     registerItem(407, "tnt_minecart", (new ItemMinecart(EntityMinecart.EnumMinecartType.TNT)).setUnlocalizedName("minecartTnt"));
/*  900 */     registerItem(408, "hopper_minecart", (new ItemMinecart(EntityMinecart.EnumMinecartType.HOPPER)).setUnlocalizedName("minecartHopper"));
/*  901 */     registerItem(409, "prismarine_shard", (new Item()).setUnlocalizedName("prismarineShard").setCreativeTab(CreativeTabs.tabMaterials));
/*  902 */     registerItem(410, "prismarine_crystals", (new Item()).setUnlocalizedName("prismarineCrystals").setCreativeTab(CreativeTabs.tabMaterials));
/*  903 */     registerItem(411, "rabbit", (new ItemFood(3, 0.3F, true)).setUnlocalizedName("rabbitRaw"));
/*  904 */     registerItem(412, "cooked_rabbit", (new ItemFood(5, 0.6F, true)).setUnlocalizedName("rabbitCooked"));
/*  905 */     registerItem(413, "rabbit_stew", (new ItemSoup(10)).setUnlocalizedName("rabbitStew"));
/*  906 */     registerItem(414, "rabbit_foot", (new Item()).setUnlocalizedName("rabbitFoot").setPotionEffect("+0+1-2+3&4-4+13").setCreativeTab(CreativeTabs.tabBrewing));
/*  907 */     registerItem(415, "rabbit_hide", (new Item()).setUnlocalizedName("rabbitHide").setCreativeTab(CreativeTabs.tabMaterials));
/*  908 */     registerItem(416, "armor_stand", (new ItemArmorStand()).setUnlocalizedName("armorStand").setMaxStackSize(16));
/*  909 */     registerItem(417, "iron_horse_armor", (new Item()).setUnlocalizedName("horsearmormetal").setMaxStackSize(1).setCreativeTab(CreativeTabs.tabMisc));
/*  910 */     registerItem(418, "golden_horse_armor", (new Item()).setUnlocalizedName("horsearmorgold").setMaxStackSize(1).setCreativeTab(CreativeTabs.tabMisc));
/*  911 */     registerItem(419, "diamond_horse_armor", (new Item()).setUnlocalizedName("horsearmordiamond").setMaxStackSize(1).setCreativeTab(CreativeTabs.tabMisc));
/*  912 */     registerItem(420, "lead", (new ItemLead()).setUnlocalizedName("leash"));
/*  913 */     registerItem(421, "name_tag", (new ItemNameTag()).setUnlocalizedName("nameTag"));
/*  914 */     registerItem(422, "command_block_minecart", (new ItemMinecart(EntityMinecart.EnumMinecartType.COMMAND_BLOCK)).setUnlocalizedName("minecartCommandBlock").setCreativeTab((CreativeTabs)null));
/*  915 */     registerItem(423, "mutton", (new ItemFood(2, 0.3F, true)).setUnlocalizedName("muttonRaw"));
/*  916 */     registerItem(424, "cooked_mutton", (new ItemFood(6, 0.8F, true)).setUnlocalizedName("muttonCooked"));
/*  917 */     registerItem(425, "banner", (new ItemBanner()).setUnlocalizedName("banner"));
/*  918 */     registerItem(427, "spruce_door", (new ItemDoor(Blocks.spruce_door)).setUnlocalizedName("doorSpruce"));
/*  919 */     registerItem(428, "birch_door", (new ItemDoor(Blocks.birch_door)).setUnlocalizedName("doorBirch"));
/*  920 */     registerItem(429, "jungle_door", (new ItemDoor(Blocks.jungle_door)).setUnlocalizedName("doorJungle"));
/*  921 */     registerItem(430, "acacia_door", (new ItemDoor(Blocks.acacia_door)).setUnlocalizedName("doorAcacia"));
/*  922 */     registerItem(431, "dark_oak_door", (new ItemDoor(Blocks.dark_oak_door)).setUnlocalizedName("doorDarkOak"));
/*  923 */     registerItem(2256, "record_13", (new ItemRecord("13")).setUnlocalizedName("record"));
/*  924 */     registerItem(2257, "record_cat", (new ItemRecord("cat")).setUnlocalizedName("record"));
/*  925 */     registerItem(2258, "record_blocks", (new ItemRecord("blocks")).setUnlocalizedName("record"));
/*  926 */     registerItem(2259, "record_chirp", (new ItemRecord("chirp")).setUnlocalizedName("record"));
/*  927 */     registerItem(2260, "record_far", (new ItemRecord("far")).setUnlocalizedName("record"));
/*  928 */     registerItem(2261, "record_mall", (new ItemRecord("mall")).setUnlocalizedName("record"));
/*  929 */     registerItem(2262, "record_mellohi", (new ItemRecord("mellohi")).setUnlocalizedName("record"));
/*  930 */     registerItem(2263, "record_stal", (new ItemRecord("stal")).setUnlocalizedName("record"));
/*  931 */     registerItem(2264, "record_strad", (new ItemRecord("strad")).setUnlocalizedName("record"));
/*  932 */     registerItem(2265, "record_ward", (new ItemRecord("ward")).setUnlocalizedName("record"));
/*  933 */     registerItem(2266, "record_11", (new ItemRecord("11")).setUnlocalizedName("record"));
/*  934 */     registerItem(2267, "record_wait", (new ItemRecord("wait")).setUnlocalizedName("record"));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static void registerItemBlock(Block blockIn) {
/*  942 */     registerItemBlock(blockIn, new ItemBlock(blockIn));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected static void registerItemBlock(Block blockIn, Item itemIn) {
/*  950 */     registerItem(Block.getIdFromBlock(blockIn), (ResourceLocation)Block.blockRegistry.getNameForObject(blockIn), itemIn);
/*  951 */     BLOCK_TO_ITEM.put(blockIn, itemIn);
/*      */   }
/*      */ 
/*      */   
/*      */   private static void registerItem(int id, String textualID, Item itemIn) {
/*  956 */     registerItem(id, new ResourceLocation(textualID), itemIn);
/*      */   }
/*      */ 
/*      */   
/*      */   private static void registerItem(int id, ResourceLocation textualID, Item itemIn) {
/*  961 */     itemRegistry.register(id, textualID, itemIn);
/*      */   }
/*      */   
/*      */   public enum ToolMaterial
/*      */   {
/*  966 */     WOOD(0, 59, 2.0F, 0.0F, 15),
/*  967 */     STONE(1, 131, 4.0F, 1.0F, 5),
/*  968 */     IRON(2, 250, 6.0F, 2.0F, 14),
/*  969 */     EMERALD(3, 1561, 8.0F, 3.0F, 10),
/*  970 */     GOLD(0, 32, 12.0F, 0.0F, 22);
/*      */     
/*      */     private final int harvestLevel;
/*      */     
/*      */     private final int maxUses;
/*      */     private final float efficiencyOnProperMaterial;
/*      */     private final float damageVsEntity;
/*      */     private final int enchantability;
/*      */     
/*      */     ToolMaterial(int harvestLevel, int maxUses, float efficiency, float damageVsEntity, int enchantability) {
/*  980 */       this.harvestLevel = harvestLevel;
/*  981 */       this.maxUses = maxUses;
/*  982 */       this.efficiencyOnProperMaterial = efficiency;
/*  983 */       this.damageVsEntity = damageVsEntity;
/*  984 */       this.enchantability = enchantability;
/*      */     }
/*      */ 
/*      */     
/*      */     public int getMaxUses() {
/*  989 */       return this.maxUses;
/*      */     }
/*      */ 
/*      */     
/*      */     public float getEfficiencyOnProperMaterial() {
/*  994 */       return this.efficiencyOnProperMaterial;
/*      */     }
/*      */ 
/*      */     
/*      */     public float getDamageVsEntity() {
/*  999 */       return this.damageVsEntity;
/*      */     }
/*      */ 
/*      */     
/*      */     public int getHarvestLevel() {
/* 1004 */       return this.harvestLevel;
/*      */     }
/*      */ 
/*      */     
/*      */     public int getEnchantability() {
/* 1009 */       return this.enchantability;
/*      */     }
/*      */ 
/*      */     
/*      */     public Item getRepairItem() {
/* 1014 */       return (this == WOOD) ? Item.getItemFromBlock(Blocks.planks) : ((this == STONE) ? Item.getItemFromBlock(Blocks.cobblestone) : ((this == GOLD) ? Items.gold_ingot : ((this == IRON) ? Items.iron_ingot : ((this == EMERALD) ? Items.diamond : null))));
/*      */     }
/*      */   }
/*      */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\minecraft\item\Item.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */