/*      */ package net.minecraft.block;
/*      */ import awareline.main.event.Event;
/*      */ import awareline.main.event.events.world.BBSetEvent;
/*      */ import com.google.common.collect.UnmodifiableIterator;
/*      */ import java.util.List;
/*      */ import java.util.Random;
/*      */ import net.minecraft.block.material.MapColor;
/*      */ import net.minecraft.block.material.Material;
/*      */ import net.minecraft.block.properties.IProperty;
/*      */ import net.minecraft.block.state.BlockState;
/*      */ import net.minecraft.block.state.IBlockState;
/*      */ import net.minecraft.creativetab.CreativeTabs;
/*      */ import net.minecraft.enchantment.EnchantmentHelper;
/*      */ import net.minecraft.entity.Entity;
/*      */ import net.minecraft.entity.EntityLivingBase;
/*      */ import net.minecraft.entity.item.EntityItem;
/*      */ import net.minecraft.entity.item.EntityXPOrb;
/*      */ import net.minecraft.entity.player.EntityPlayer;
/*      */ import net.minecraft.item.Item;
/*      */ import net.minecraft.item.ItemStack;
/*      */ import net.minecraft.util.AxisAlignedBB;
/*      */ import net.minecraft.util.BlockPos;
/*      */ import net.minecraft.util.EnumFacing;
/*      */ import net.minecraft.util.EnumWorldBlockLayer;
/*      */ import net.minecraft.util.MovingObjectPosition;
/*      */ import net.minecraft.util.ObjectIntIdentityMap;
/*      */ import net.minecraft.util.RegistryNamespacedDefaultedByKey;
/*      */ import net.minecraft.util.ResourceLocation;
/*      */ import net.minecraft.util.Vec3;
/*      */ import net.minecraft.world.Explosion;
/*      */ import net.minecraft.world.IBlockAccess;
/*      */ import net.minecraft.world.World;
/*      */ 
/*      */ public class Block {
/*   35 */   private static final ResourceLocation AIR_ID = new ResourceLocation("air");
/*   36 */   public static final RegistryNamespacedDefaultedByKey<ResourceLocation, Block> blockRegistry = new RegistryNamespacedDefaultedByKey(AIR_ID);
/*   37 */   public static final ObjectIntIdentityMap<IBlockState> BLOCK_STATE_IDS = new ObjectIntIdentityMap();
/*      */   private CreativeTabs displayOnCreativeTab;
/*   39 */   public static final SoundType soundTypeStone = new SoundType("stone", 1.0F, 1.0F);
/*      */ 
/*      */   
/*   42 */   public static final SoundType soundTypeWood = new SoundType("wood", 1.0F, 1.0F);
/*      */ 
/*      */   
/*   45 */   public static final SoundType soundTypeGravel = new SoundType("gravel", 1.0F, 1.0F);
/*   46 */   public static final SoundType soundTypeGrass = new SoundType("grass", 1.0F, 1.0F);
/*   47 */   public static final SoundType soundTypePiston = new SoundType("stone", 1.0F, 1.0F);
/*   48 */   public static final SoundType soundTypeMetal = new SoundType("stone", 1.0F, 1.5F);
/*   49 */   public static final SoundType soundTypeGlass = new SoundType("stone", 1.0F, 1.0F)
/*      */     {
/*      */       public String getBreakSound()
/*      */       {
/*   53 */         return "dig.glass";
/*      */       }
/*      */       
/*      */       public String getPlaceSound() {
/*   57 */         return "step.stone";
/*      */       }
/*      */     };
/*   60 */   public static final SoundType soundTypeCloth = new SoundType("cloth", 1.0F, 1.0F);
/*   61 */   public static final SoundType soundTypeSand = new SoundType("sand", 1.0F, 1.0F);
/*   62 */   public static final SoundType soundTypeSnow = new SoundType("snow", 1.0F, 1.0F);
/*   63 */   public static final SoundType soundTypeLadder = new SoundType("ladder", 1.0F, 1.0F)
/*      */     {
/*      */       public String getBreakSound()
/*      */       {
/*   67 */         return "dig.wood";
/*      */       }
/*      */     };
/*   70 */   public static final SoundType soundTypeAnvil = new SoundType("anvil", 0.3F, 1.0F)
/*      */     {
/*      */       public String getBreakSound()
/*      */       {
/*   74 */         return "dig.stone";
/*      */       }
/*      */       
/*      */       public String getPlaceSound() {
/*   78 */         return "random.anvil_land";
/*      */       }
/*      */     };
/*   81 */   public static final SoundType SLIME_SOUND = new SoundType("slime", 1.0F, 1.0F)
/*      */     {
/*      */       public String getBreakSound()
/*      */       {
/*   85 */         return "mob.slime.big";
/*      */       }
/*      */       
/*      */       public String getPlaceSound() {
/*   89 */         return "mob.slime.big";
/*      */       }
/*      */       
/*      */       public String getStepSound() {
/*   93 */         return "mob.slime.small";
/*      */       }
/*      */     };
/*      */ 
/*      */ 
/*      */   
/*      */   protected boolean fullBlock;
/*      */ 
/*      */   
/*      */   protected int lightOpacity;
/*      */   
/*      */   protected boolean translucent;
/*      */   
/*      */   protected int lightValue;
/*      */   
/*      */   protected boolean useNeighborBrightness;
/*      */   
/*      */   protected float blockHardness;
/*      */   
/*      */   protected float blockResistance;
/*      */   
/*      */   protected boolean enableStats;
/*      */   
/*      */   protected boolean needsRandomTick;
/*      */   
/*      */   protected boolean isBlockContainer;
/*      */   
/*      */   protected double minX;
/*      */   
/*      */   protected double minY;
/*      */   
/*      */   protected double minZ;
/*      */   
/*      */   protected double maxX;
/*      */   
/*      */   protected double maxY;
/*      */   
/*      */   protected double maxZ;
/*      */   
/*      */   public SoundType stepSound;
/*      */   
/*      */   public float blockParticleGravity;
/*      */   
/*      */   protected final Material blockMaterial;
/*      */   
/*      */   protected final MapColor blockMapColor;
/*      */   
/*      */   public float slipperiness;
/*      */   
/*      */   protected final BlockState blockState;
/*      */   
/*      */   private IBlockState defaultBlockState;
/*      */   
/*      */   private String unlocalizedName;
/*      */ 
/*      */   
/*      */   public static int getIdFromBlock(Block blockIn) {
/*  150 */     return blockRegistry.getIDForObject(blockIn);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static int getStateId(IBlockState state) {
/*  158 */     Block block = state.getBlock();
/*  159 */     return getIdFromBlock(block) + (block.getMetaFromState(state) << 12);
/*      */   }
/*      */ 
/*      */   
/*      */   public static Block getBlockById(int id) {
/*  164 */     return (Block)blockRegistry.getObjectById(id);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static IBlockState getStateById(int id) {
/*  172 */     int i = id & 0xFFF;
/*  173 */     int j = id >> 12 & 0xF;
/*  174 */     return getBlockById(i).getStateFromMeta(j);
/*      */   }
/*      */ 
/*      */   
/*      */   public static Block getBlockFromItem(Item itemIn) {
/*  179 */     return (itemIn instanceof ItemBlock) ? ((ItemBlock)itemIn).getBlock() : null;
/*      */   }
/*      */ 
/*      */   
/*      */   public static Block getBlockFromName(String name) {
/*  184 */     ResourceLocation resourcelocation = new ResourceLocation(name);
/*      */     
/*  186 */     if (blockRegistry.containsKey(resourcelocation))
/*      */     {
/*  188 */       return (Block)blockRegistry.getObject(resourcelocation);
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     try {
/*  194 */       return (Block)blockRegistry.getObjectById(Integer.parseInt(name));
/*      */     }
/*  196 */     catch (NumberFormatException var3) {
/*      */       
/*  198 */       return null;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isFullBlock() {
/*  205 */     return this.fullBlock;
/*      */   }
/*      */ 
/*      */   
/*      */   public int getLightOpacity() {
/*  210 */     return this.lightOpacity;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isTranslucent() {
/*  218 */     return this.translucent;
/*      */   }
/*      */ 
/*      */   
/*      */   public int getLightValue() {
/*  223 */     return this.lightValue;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean getUseNeighborBrightness() {
/*  231 */     return this.useNeighborBrightness;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Material getMaterial() {
/*  239 */     return this.blockMaterial;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public MapColor getMapColor(IBlockState state) {
/*  247 */     return this.blockMapColor;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public IBlockState getStateFromMeta(int meta) {
/*  255 */     return getDefaultState();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getMetaFromState(IBlockState state) {
/*  263 */     if (state != null && !state.getPropertyNames().isEmpty())
/*      */     {
/*  265 */       throw new IllegalArgumentException("Don't know how to convert " + state + " back into data...");
/*      */     }
/*      */ 
/*      */     
/*  269 */     return 0;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public IBlockState getActualState(IBlockState state, IBlockAccess worldIn, BlockPos pos) {
/*  279 */     return state;
/*      */   }
/*      */ 
/*      */   
/*      */   public Block(Material blockMaterialIn, MapColor blockMapColorIn) {
/*  284 */     this.enableStats = true;
/*  285 */     this.stepSound = soundTypeStone;
/*  286 */     this.blockParticleGravity = 1.0F;
/*  287 */     this.slipperiness = 0.6F;
/*  288 */     this.blockMaterial = blockMaterialIn;
/*  289 */     this.blockMapColor = blockMapColorIn;
/*  290 */     setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
/*  291 */     this.fullBlock = isOpaqueCube();
/*  292 */     this.fullBlock = isOpaqueCube();
/*  293 */     this.lightOpacity = isOpaqueCube() ? 255 : 0;
/*  294 */     this.translucent = !blockMaterialIn.blocksLight();
/*  295 */     this.blockState = createBlockState();
/*  296 */     setDefaultState(this.blockState.getBaseState());
/*      */   }
/*      */ 
/*      */   
/*      */   protected Block(Material materialIn) {
/*  301 */     this(materialIn, materialIn.getMaterialMapColor());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected Block setStepSound(SoundType sound) {
/*  309 */     this.stepSound = sound;
/*  310 */     return this;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected Block setLightOpacity(int opacity) {
/*  318 */     this.lightOpacity = opacity;
/*  319 */     return this;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected Block setLightLevel(float value) {
/*  328 */     this.lightValue = (int)(15.0F * value);
/*  329 */     return this;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected Block setResistance(float resistance) {
/*  337 */     this.blockResistance = resistance * 3.0F;
/*  338 */     return this;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isBlockNormalCube() {
/*  346 */     return (this.blockMaterial.blocksMovement() && isFullCube());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isNormalCube() {
/*  355 */     return (this.blockMaterial.isOpaque() && isFullCube() && !canProvidePower());
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean isVisuallyOpaque() {
/*  360 */     return (this.blockMaterial.blocksMovement() && isFullCube());
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean isFullCube() {
/*  365 */     return true;
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean isPassable(IBlockAccess worldIn, BlockPos pos) {
/*  370 */     return !this.blockMaterial.blocksMovement();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getRenderType() {
/*  378 */     return 3;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isReplaceable(World worldIn, BlockPos pos) {
/*  386 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected Block setHardness(float hardness) {
/*  394 */     this.blockHardness = hardness;
/*      */     
/*  396 */     if (this.blockResistance < hardness * 5.0F)
/*      */     {
/*  398 */       this.blockResistance = hardness * 5.0F;
/*      */     }
/*      */     
/*  401 */     return this;
/*      */   }
/*      */ 
/*      */   
/*      */   protected Block setBlockUnbreakable() {
/*  406 */     setHardness(-1.0F);
/*  407 */     return this;
/*      */   }
/*      */ 
/*      */   
/*      */   public float getBlockHardness(World worldIn, BlockPos pos) {
/*  412 */     return this.blockHardness;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected Block setTickRandomly(boolean shouldTick) {
/*  420 */     this.needsRandomTick = shouldTick;
/*  421 */     return this;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean getTickRandomly() {
/*  430 */     return this.needsRandomTick;
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean hasTileEntity() {
/*  435 */     return this.isBlockContainer;
/*      */   }
/*      */ 
/*      */   
/*      */   protected final void setBlockBounds(float minX, float minY, float minZ, float maxX, float maxY, float maxZ) {
/*  440 */     this.minX = minX;
/*  441 */     this.minY = minY;
/*  442 */     this.minZ = minZ;
/*  443 */     this.maxX = maxX;
/*  444 */     this.maxY = maxY;
/*  445 */     this.maxZ = maxZ;
/*      */   }
/*      */ 
/*      */   
/*      */   public int getMixedBrightnessForBlock(IBlockAccess worldIn, BlockPos pos) {
/*  450 */     Block block = worldIn.getBlockState(pos).getBlock();
/*  451 */     int i = worldIn.getCombinedLight(pos, block.getLightValue());
/*      */     
/*  453 */     if (i == 0 && block instanceof BlockSlab) {
/*      */       
/*  455 */       pos = pos.down();
/*  456 */       block = worldIn.getBlockState(pos).getBlock();
/*  457 */       return worldIn.getCombinedLight(pos, block.getLightValue());
/*      */     } 
/*      */ 
/*      */     
/*  461 */     return i;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean shouldSideBeRendered(IBlockAccess worldIn, BlockPos pos, EnumFacing side) {
/*  468 */     return (side == EnumFacing.DOWN && this.minY > 0.0D) ? true : ((side == EnumFacing.UP && this.maxY < 1.0D) ? true : ((side == EnumFacing.NORTH && this.minZ > 0.0D) ? true : ((side == EnumFacing.SOUTH && this.maxZ < 1.0D) ? true : ((side == EnumFacing.WEST && this.minX > 0.0D) ? true : ((side == EnumFacing.EAST && this.maxX < 1.0D) ? true : (!worldIn.getBlockState(pos).getBlock().isOpaqueCube()))))));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isBlockSolid(IBlockAccess worldIn, BlockPos pos, EnumFacing side) {
/*  476 */     return worldIn.getBlockState(pos).getBlock().getMaterial().isSolid();
/*      */   }
/*      */ 
/*      */   
/*      */   public AxisAlignedBB getSelectedBoundingBox(World worldIn, BlockPos pos) {
/*  481 */     return new AxisAlignedBB(pos.getX() + this.minX, pos.getY() + this.minY, pos.getZ() + this.minZ, pos.getX() + this.maxX, pos.getY() + this.maxY, pos.getZ() + this.maxZ);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void addCollisionBoxesToList(World worldIn, BlockPos pos, IBlockState state, AxisAlignedBB mask, List<AxisAlignedBB> list, Entity collidingEntity) {
/*  490 */     AxisAlignedBB axisalignedbb = getCollisionBoundingBox(worldIn, pos, state);
/*      */     
/*  492 */     if (collidingEntity == (Minecraft.getMinecraft()).thePlayer) {
/*  493 */       BBSetEvent event = new BBSetEvent(this, pos, getCollisionBoundingBox(worldIn, pos, state), list);
/*  494 */       EventManager.call((Event)event);
/*      */       
/*  496 */       if (event.isCancelled()) {
/*      */         return;
/*      */       }
/*  499 */       axisalignedbb = event.getBoundingBox();
/*      */     } 
/*      */     
/*  502 */     if (axisalignedbb != null && mask.intersectsWith(axisalignedbb)) {
/*  503 */       list.add(axisalignedbb);
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   public AxisAlignedBB getCollisionBoundingBox(World worldIn, BlockPos pos, IBlockState state) {
/*  509 */     return new AxisAlignedBB(pos.getX() + this.minX, pos.getY() + this.minY, pos.getZ() + this.minZ, pos.getX() + this.maxX, pos.getY() + this.maxY, pos.getZ() + this.maxZ);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isOpaqueCube() {
/*  517 */     return true;
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean canCollideCheck(IBlockState state, boolean hitIfLiquid) {
/*  522 */     return isCollidable();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isCollidable() {
/*  532 */     return true;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void randomTick(World worldIn, BlockPos pos, IBlockState state, Random random) {
/*  539 */     updateTick(worldIn, pos, state, random);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void updateTick(World worldIn, BlockPos pos, IBlockState state, Random rand) {}
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void randomDisplayTick(World worldIn, BlockPos pos, IBlockState state, Random rand) {}
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void onBlockDestroyedByPlayer(World worldIn, BlockPos pos, IBlockState state) {}
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void onNeighborBlockChange(World worldIn, BlockPos pos, IBlockState state, Block neighborBlock) {}
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int tickRate(World worldIn) {
/*  569 */     return 10;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void onBlockAdded(World worldIn, BlockPos pos, IBlockState state) {}
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void breakBlock(World worldIn, BlockPos pos, IBlockState state) {}
/*      */ 
/*      */ 
/*      */   
/*      */   public int quantityDropped(Random random) {
/*  585 */     return 1;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Item getItemDropped(IBlockState state, Random rand, int fortune) {
/*  593 */     return Item.getItemFromBlock(this);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void onBlockDamaghe(BlockDamageEvent e) {}
/*      */ 
/*      */ 
/*      */   
/*      */   public float getPlayerRelativeBlockHardness(EntityPlayer playerIn, World worldIn, BlockPos pos) {
/*  604 */     float f = getBlockHardness(worldIn, pos);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  612 */     return (f < 0.0F) ? 0.0F : (!playerIn.canHarvestBlock(this) ? (playerIn.getToolDigEfficiency(this) / f / 100.0F) : (playerIn.getToolDigEfficiency(this) / f / 30.0F));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void dropBlockAsItem(World worldIn, BlockPos pos, IBlockState state, int forture) {
/*  620 */     dropBlockAsItemWithChance(worldIn, pos, state, 1.0F, forture);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void dropBlockAsItemWithChance(World worldIn, BlockPos pos, IBlockState state, float chance, int fortune) {
/*  628 */     if (!worldIn.isRemote) {
/*      */       
/*  630 */       int i = quantityDroppedWithBonus(fortune, worldIn.rand);
/*      */       
/*  632 */       for (int j = 0; j < i; j++) {
/*      */         
/*  634 */         if (worldIn.rand.nextFloat() <= chance) {
/*      */           
/*  636 */           Item item = getItemDropped(state, worldIn.rand, fortune);
/*      */           
/*  638 */           if (item != null)
/*      */           {
/*  640 */             spawnAsEntity(worldIn, pos, new ItemStack(item, 1, damageDropped(state)));
/*      */           }
/*      */         } 
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void spawnAsEntity(World worldIn, BlockPos pos, ItemStack stack) {
/*  652 */     if (!worldIn.isRemote && worldIn.getGameRules().getBoolean("doTileDrops")) {
/*      */       
/*  654 */       float f = 0.5F;
/*  655 */       double d0 = (worldIn.rand.nextFloat() * f) + (1.0F - f) * 0.5D;
/*  656 */       double d1 = (worldIn.rand.nextFloat() * f) + (1.0F - f) * 0.5D;
/*  657 */       double d2 = (worldIn.rand.nextFloat() * f) + (1.0F - f) * 0.5D;
/*  658 */       EntityItem entityitem = new EntityItem(worldIn, pos.getX() + d0, pos.getY() + d1, pos.getZ() + d2, stack);
/*  659 */       entityitem.setDefaultPickupDelay();
/*  660 */       worldIn.spawnEntityInWorld((Entity)entityitem);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void dropXpOnBlockBreak(World worldIn, BlockPos pos, int amount) {
/*  669 */     if (!worldIn.isRemote)
/*      */     {
/*  671 */       while (amount > 0) {
/*      */         
/*  673 */         int i = EntityXPOrb.getXPSplit(amount);
/*  674 */         amount -= i;
/*  675 */         worldIn.spawnEntityInWorld((Entity)new EntityXPOrb(worldIn, pos.getX() + 0.5D, pos.getY() + 0.5D, pos.getZ() + 0.5D, i));
/*      */       } 
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int damageDropped(IBlockState state) {
/*  686 */     return 0;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public float getExplosionResistance(Entity exploder) {
/*  694 */     return this.blockResistance / 5.0F;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public MovingObjectPosition collisionRayTrace(World worldIn, BlockPos pos, Vec3 start, Vec3 end) {
/*  702 */     setBlockBoundsBasedOnState((IBlockAccess)worldIn, pos);
/*  703 */     start = start.addVector(-pos.getX(), -pos.getY(), -pos.getZ());
/*  704 */     end = end.addVector(-pos.getX(), -pos.getY(), -pos.getZ());
/*  705 */     Vec3 vec3 = start.getIntermediateWithXValue(end, this.minX);
/*  706 */     Vec3 vec31 = start.getIntermediateWithXValue(end, this.maxX);
/*  707 */     Vec3 vec32 = start.getIntermediateWithYValue(end, this.minY);
/*  708 */     Vec3 vec33 = start.getIntermediateWithYValue(end, this.maxY);
/*  709 */     Vec3 vec34 = start.getIntermediateWithZValue(end, this.minZ);
/*  710 */     Vec3 vec35 = start.getIntermediateWithZValue(end, this.maxZ);
/*      */     
/*  712 */     if (!isVecInsideYZBounds(vec3))
/*      */     {
/*  714 */       vec3 = null;
/*      */     }
/*      */     
/*  717 */     if (!isVecInsideYZBounds(vec31))
/*      */     {
/*  719 */       vec31 = null;
/*      */     }
/*      */     
/*  722 */     if (!isVecInsideXZBounds(vec32))
/*      */     {
/*  724 */       vec32 = null;
/*      */     }
/*      */     
/*  727 */     if (!isVecInsideXZBounds(vec33))
/*      */     {
/*  729 */       vec33 = null;
/*      */     }
/*      */     
/*  732 */     if (!isVecInsideXYBounds(vec34))
/*      */     {
/*  734 */       vec34 = null;
/*      */     }
/*      */     
/*  737 */     if (!isVecInsideXYBounds(vec35))
/*      */     {
/*  739 */       vec35 = null;
/*      */     }
/*      */     
/*  742 */     Vec3 vec36 = null;
/*      */     
/*  744 */     if (vec3 != null && (vec36 == null || start.squareDistanceTo(vec3) < start.squareDistanceTo(vec36)))
/*      */     {
/*  746 */       vec36 = vec3;
/*      */     }
/*      */     
/*  749 */     if (vec31 != null && (vec36 == null || start.squareDistanceTo(vec31) < start.squareDistanceTo(vec36)))
/*      */     {
/*  751 */       vec36 = vec31;
/*      */     }
/*      */     
/*  754 */     if (vec32 != null && (vec36 == null || start.squareDistanceTo(vec32) < start.squareDistanceTo(vec36)))
/*      */     {
/*  756 */       vec36 = vec32;
/*      */     }
/*      */     
/*  759 */     if (vec33 != null && (vec36 == null || start.squareDistanceTo(vec33) < start.squareDistanceTo(vec36)))
/*      */     {
/*  761 */       vec36 = vec33;
/*      */     }
/*      */     
/*  764 */     if (vec34 != null && (vec36 == null || start.squareDistanceTo(vec34) < start.squareDistanceTo(vec36)))
/*      */     {
/*  766 */       vec36 = vec34;
/*      */     }
/*      */     
/*  769 */     if (vec35 != null && (vec36 == null || start.squareDistanceTo(vec35) < start.squareDistanceTo(vec36)))
/*      */     {
/*  771 */       vec36 = vec35;
/*      */     }
/*      */     
/*  774 */     if (vec36 == null)
/*      */     {
/*  776 */       return null;
/*      */     }
/*      */ 
/*      */     
/*  780 */     EnumFacing enumfacing = null;
/*      */     
/*  782 */     if (vec36 == vec3)
/*      */     {
/*  784 */       enumfacing = EnumFacing.WEST;
/*      */     }
/*      */     
/*  787 */     if (vec36 == vec31)
/*      */     {
/*  789 */       enumfacing = EnumFacing.EAST;
/*      */     }
/*      */     
/*  792 */     if (vec36 == vec32)
/*      */     {
/*  794 */       enumfacing = EnumFacing.DOWN;
/*      */     }
/*      */     
/*  797 */     if (vec36 == vec33)
/*      */     {
/*  799 */       enumfacing = EnumFacing.UP;
/*      */     }
/*      */     
/*  802 */     if (vec36 == vec34)
/*      */     {
/*  804 */       enumfacing = EnumFacing.NORTH;
/*      */     }
/*      */     
/*  807 */     if (vec36 == vec35)
/*      */     {
/*  809 */       enumfacing = EnumFacing.SOUTH;
/*      */     }
/*      */     
/*  812 */     return new MovingObjectPosition(vec36.addVector(pos.getX(), pos.getY(), pos.getZ()), enumfacing, pos);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean isVecInsideYZBounds(Vec3 point) {
/*  821 */     return (point == null) ? false : ((point.yCoord >= this.minY && point.yCoord <= this.maxY && point.zCoord >= this.minZ && point.zCoord <= this.maxZ));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean isVecInsideXZBounds(Vec3 point) {
/*  829 */     return (point == null) ? false : ((point.xCoord >= this.minX && point.xCoord <= this.maxX && point.zCoord >= this.minZ && point.zCoord <= this.maxZ));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean isVecInsideXYBounds(Vec3 point) {
/*  837 */     return (point == null) ? false : ((point.xCoord >= this.minX && point.xCoord <= this.maxX && point.yCoord >= this.minY && point.yCoord <= this.maxY));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void onBlockDestroyedByExplosion(World worldIn, BlockPos pos, Explosion explosionIn) {}
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public EnumWorldBlockLayer getBlockLayer() {
/*  850 */     return EnumWorldBlockLayer.SOLID;
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean canReplace(World worldIn, BlockPos pos, EnumFacing side, ItemStack stack) {
/*  855 */     return canPlaceBlockOnSide(worldIn, pos, side);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean canPlaceBlockOnSide(World worldIn, BlockPos pos, EnumFacing side) {
/*  863 */     return canPlaceBlockAt(worldIn, pos);
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean canPlaceBlockAt(World worldIn, BlockPos pos) {
/*  868 */     return (worldIn.getBlockState(pos).getBlock()).blockMaterial.isReplaceable();
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumFacing side, float hitX, float hitY, float hitZ) {
/*  873 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void onEntityCollidedWithBlock(World worldIn, BlockPos pos, Entity entityIn) {}
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public IBlockState onBlockPlaced(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer) {
/*  889 */     return getStateFromMeta(meta);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public void onBlockClicked(World worldIn, BlockPos pos, EntityPlayer playerIn) {}
/*      */ 
/*      */   
/*      */   public Vec3 modifyAcceleration(World worldIn, BlockPos pos, Entity entityIn, Vec3 motion) {
/*  898 */     return motion;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setBlockBoundsBasedOnState(IBlockAccess worldIn, BlockPos pos) {}
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final double getBlockBoundsMinX() {
/*  910 */     return this.minX;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final double getBlockBoundsMaxX() {
/*  918 */     return this.maxX;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final double getBlockBoundsMinY() {
/*  926 */     return this.minY;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final double getBlockBoundsMaxY() {
/*  934 */     return this.maxY;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final double getBlockBoundsMinZ() {
/*  942 */     return this.minZ;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final double getBlockBoundsMaxZ() {
/*  950 */     return this.maxZ;
/*      */   }
/*      */ 
/*      */   
/*      */   public int getBlockColor() {
/*  955 */     return 16777215;
/*      */   }
/*      */ 
/*      */   
/*      */   public int getRenderColor(IBlockState state) {
/*  960 */     return 16777215;
/*      */   }
/*      */ 
/*      */   
/*      */   public int colorMultiplier(IBlockAccess worldIn, BlockPos pos, int renderPass) {
/*  965 */     return 16777215;
/*      */   }
/*      */ 
/*      */   
/*      */   public final int colorMultiplier(IBlockAccess worldIn, BlockPos pos) {
/*  970 */     return colorMultiplier(worldIn, pos, 0);
/*      */   }
/*      */ 
/*      */   
/*      */   public int getWeakPower(IBlockAccess worldIn, BlockPos pos, IBlockState state, EnumFacing side) {
/*  975 */     return 0;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean canProvidePower() {
/*  983 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void onEntityCollidedWithBlock(World worldIn, BlockPos pos, IBlockState state, Entity entityIn) {}
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getStrongPower(IBlockAccess worldIn, BlockPos pos, IBlockState state, EnumFacing side) {
/*  995 */     return 0;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setBlockBoundsForItemRender() {}
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void harvestBlock(World worldIn, EntityPlayer player, BlockPos pos, IBlockState state, TileEntity te) {
/* 1007 */     player.triggerAchievement(StatList.mineBlockStatArray[getIdFromBlock(this)]);
/* 1008 */     player.addExhaustion(0.025F);
/*      */     
/* 1010 */     if (canSilkHarvest() && EnchantmentHelper.getSilkTouchModifier((EntityLivingBase)player)) {
/*      */       
/* 1012 */       ItemStack itemstack = createStackedBlock(state);
/*      */       
/* 1014 */       if (itemstack != null)
/*      */       {
/* 1016 */         spawnAsEntity(worldIn, pos, itemstack);
/*      */       }
/*      */     }
/*      */     else {
/*      */       
/* 1021 */       int i = EnchantmentHelper.getFortuneModifier((EntityLivingBase)player);
/* 1022 */       dropBlockAsItem(worldIn, pos, state, i);
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   protected boolean canSilkHarvest() {
/* 1028 */     return (isFullCube() && !this.isBlockContainer);
/*      */   }
/*      */ 
/*      */   
/*      */   protected ItemStack createStackedBlock(IBlockState state) {
/* 1033 */     int i = 0;
/* 1034 */     Item item = Item.getItemFromBlock(this);
/*      */     
/* 1036 */     if (item != null && item.getHasSubtypes())
/*      */     {
/* 1038 */       i = getMetaFromState(state);
/*      */     }
/*      */     
/* 1041 */     return new ItemStack(item, 1, i);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int quantityDroppedWithBonus(int fortune, Random random) {
/* 1049 */     return quantityDropped(random);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void onBlockPlacedBy(World worldIn, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack) {}
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean canSpawnInBlock() {
/* 1064 */     return (!this.blockMaterial.isSolid() && !this.blockMaterial.isLiquid());
/*      */   }
/*      */ 
/*      */   
/*      */   public Block setUnlocalizedName(String name) {
/* 1069 */     this.unlocalizedName = name;
/* 1070 */     return this;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getLocalizedName() {
/* 1078 */     return StatCollector.translateToLocal(getUnlocalizedName() + ".name");
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getUnlocalizedName() {
/* 1086 */     return "tile." + this.unlocalizedName;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean onBlockEventReceived(World worldIn, BlockPos pos, IBlockState state, int eventID, int eventParam) {
/* 1094 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean getEnableStats() {
/* 1102 */     return this.enableStats;
/*      */   }
/*      */ 
/*      */   
/*      */   protected Block disableStats() {
/* 1107 */     this.enableStats = false;
/* 1108 */     return this;
/*      */   }
/*      */ 
/*      */   
/*      */   public int getMobilityFlag() {
/* 1113 */     return this.blockMaterial.getMaterialMobility();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public float getAmbientOcclusionLightValue() {
/* 1122 */     return isBlockNormalCube() ? 0.2F : 1.0F;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void onFallenUpon(World worldIn, BlockPos pos, Entity entityIn, float fallDistance) {
/* 1130 */     entityIn.fall(fallDistance, 1.0F);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void onLanded(World worldIn, Entity entityIn) {
/* 1139 */     entityIn.motionY = 0.0D;
/*      */   }
/*      */ 
/*      */   
/*      */   public Item getItem(World worldIn, BlockPos pos) {
/* 1144 */     return Item.getItemFromBlock(this);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getDamageValue(World worldIn, BlockPos pos) {
/* 1152 */     return damageDropped(worldIn.getBlockState(pos));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void getSubBlocks(Item itemIn, CreativeTabs tab, List<ItemStack> list) {
/* 1160 */     list.add(new ItemStack(itemIn, 1, 0));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public CreativeTabs getCreativeTabToDisplayOn() {
/* 1168 */     return this.displayOnCreativeTab;
/*      */   }
/*      */ 
/*      */   
/*      */   public Block setCreativeTab(CreativeTabs tab) {
/* 1173 */     this.displayOnCreativeTab = tab;
/* 1174 */     return this;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void onBlockHarvested(World worldIn, BlockPos pos, IBlockState state, EntityPlayer player) {}
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void fillWithRain(World worldIn, BlockPos pos) {}
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isFlowerPot() {
/* 1193 */     return false;
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean requiresUpdates() {
/* 1198 */     return true;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean canDropFromExplosion(Explosion explosionIn) {
/* 1206 */     return true;
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean isAssociatedBlock(Block other) {
/* 1211 */     return (this == other);
/*      */   }
/*      */ 
/*      */   
/*      */   public static boolean isEqualTo(Block blockIn, Block other) {
/* 1216 */     return (blockIn != null && other != null) ? ((blockIn == other) ? true : blockIn.isAssociatedBlock(other)) : false;
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean hasComparatorInputOverride() {
/* 1221 */     return false;
/*      */   }
/*      */ 
/*      */   
/*      */   public int getComparatorInputOverride(World worldIn, BlockPos pos) {
/* 1226 */     return 0;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public IBlockState getStateForEntityRender(IBlockState state) {
/* 1234 */     return state;
/*      */   }
/*      */ 
/*      */   
/*      */   protected BlockState createBlockState() {
/* 1239 */     return new BlockState(this, new IProperty[0]);
/*      */   }
/*      */ 
/*      */   
/*      */   public BlockState getBlockState() {
/* 1244 */     return this.blockState;
/*      */   }
/*      */ 
/*      */   
/*      */   protected final void setDefaultState(IBlockState state) {
/* 1249 */     this.defaultBlockState = state;
/*      */   }
/*      */ 
/*      */   
/*      */   public final IBlockState getDefaultState() {
/* 1254 */     return this.defaultBlockState;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public EnumOffsetType getOffsetType() {
/* 1262 */     return EnumOffsetType.NONE;
/*      */   }
/*      */ 
/*      */   
/*      */   public String toString() {
/* 1267 */     return "Block{" + blockRegistry.getNameForObject(this) + "}";
/*      */   }
/*      */ 
/*      */   
/*      */   public static void registerBlocks() {
/* 1272 */     registerBlock(0, AIR_ID, (new BlockAir()).setUnlocalizedName("air"));
/* 1273 */     registerBlock(1, "stone", (new BlockStone()).setHardness(1.5F).setResistance(10.0F).setStepSound(soundTypePiston).setUnlocalizedName("stone"));
/* 1274 */     registerBlock(2, "grass", (new BlockGrass()).setHardness(0.6F).setStepSound(soundTypeGrass).setUnlocalizedName("grass"));
/* 1275 */     registerBlock(3, "dirt", (new BlockDirt()).setHardness(0.5F).setStepSound(soundTypeGravel).setUnlocalizedName("dirt"));
/* 1276 */     Block block = (new Block(Material.rock)).setHardness(2.0F).setResistance(10.0F).setStepSound(soundTypePiston).setUnlocalizedName("stonebrick").setCreativeTab(CreativeTabs.tabBlock);
/* 1277 */     registerBlock(4, "cobblestone", block);
/* 1278 */     Block block1 = (new BlockPlanks()).setHardness(2.0F).setResistance(5.0F).setStepSound(soundTypeWood).setUnlocalizedName("wood");
/* 1279 */     registerBlock(5, "planks", block1);
/* 1280 */     registerBlock(6, "sapling", (new BlockSapling()).setHardness(0.0F).setStepSound(soundTypeGrass).setUnlocalizedName("sapling"));
/* 1281 */     registerBlock(7, "bedrock", (new Block(Material.rock)).setBlockUnbreakable().setResistance(6000000.0F).setStepSound(soundTypePiston).setUnlocalizedName("bedrock").disableStats().setCreativeTab(CreativeTabs.tabBlock));
/* 1282 */     registerBlock(8, "flowing_water", (new BlockDynamicLiquid(Material.water)).setHardness(100.0F).setLightOpacity(3).setUnlocalizedName("water").disableStats());
/* 1283 */     registerBlock(9, "water", (new BlockStaticLiquid(Material.water)).setHardness(100.0F).setLightOpacity(3).setUnlocalizedName("water").disableStats());
/* 1284 */     registerBlock(10, "flowing_lava", (new BlockDynamicLiquid(Material.lava)).setHardness(100.0F).setLightLevel(1.0F).setUnlocalizedName("lava").disableStats());
/* 1285 */     registerBlock(11, "lava", (new BlockStaticLiquid(Material.lava)).setHardness(100.0F).setLightLevel(1.0F).setUnlocalizedName("lava").disableStats());
/* 1286 */     registerBlock(12, "sand", (new BlockSand()).setHardness(0.5F).setStepSound(soundTypeSand).setUnlocalizedName("sand"));
/* 1287 */     registerBlock(13, "gravel", (new BlockGravel()).setHardness(0.6F).setStepSound(soundTypeGravel).setUnlocalizedName("gravel"));
/* 1288 */     registerBlock(14, "gold_ore", (new BlockOre()).setHardness(3.0F).setResistance(5.0F).setStepSound(soundTypePiston).setUnlocalizedName("oreGold"));
/* 1289 */     registerBlock(15, "iron_ore", (new BlockOre()).setHardness(3.0F).setResistance(5.0F).setStepSound(soundTypePiston).setUnlocalizedName("oreIron"));
/* 1290 */     registerBlock(16, "coal_ore", (new BlockOre()).setHardness(3.0F).setResistance(5.0F).setStepSound(soundTypePiston).setUnlocalizedName("oreCoal"));
/* 1291 */     registerBlock(17, "log", (new BlockOldLog()).setUnlocalizedName("log"));
/* 1292 */     registerBlock(18, "leaves", (new BlockOldLeaf()).setUnlocalizedName("leaves"));
/* 1293 */     registerBlock(19, "sponge", (new BlockSponge()).setHardness(0.6F).setStepSound(soundTypeGrass).setUnlocalizedName("sponge"));
/* 1294 */     registerBlock(20, "glass", (new BlockGlass(Material.glass, false)).setHardness(0.3F).setStepSound(soundTypeGlass).setUnlocalizedName("glass"));
/* 1295 */     registerBlock(21, "lapis_ore", (new BlockOre()).setHardness(3.0F).setResistance(5.0F).setStepSound(soundTypePiston).setUnlocalizedName("oreLapis"));
/* 1296 */     registerBlock(22, "lapis_block", (new Block(Material.iron, MapColor.lapisColor)).setHardness(3.0F).setResistance(5.0F).setStepSound(soundTypePiston).setUnlocalizedName("blockLapis").setCreativeTab(CreativeTabs.tabBlock));
/* 1297 */     registerBlock(23, "dispenser", (new BlockDispenser()).setHardness(3.5F).setStepSound(soundTypePiston).setUnlocalizedName("dispenser"));
/* 1298 */     Block block2 = (new BlockSandStone()).setStepSound(soundTypePiston).setHardness(0.8F).setUnlocalizedName("sandStone");
/* 1299 */     registerBlock(24, "sandstone", block2);
/* 1300 */     registerBlock(25, "noteblock", (new BlockNote()).setHardness(0.8F).setUnlocalizedName("musicBlock"));
/* 1301 */     registerBlock(26, "bed", (new BlockBed()).setStepSound(soundTypeWood).setHardness(0.2F).setUnlocalizedName("bed").disableStats());
/* 1302 */     registerBlock(27, "golden_rail", (new BlockRailPowered()).setHardness(0.7F).setStepSound(soundTypeMetal).setUnlocalizedName("goldenRail"));
/* 1303 */     registerBlock(28, "detector_rail", (new BlockRailDetector()).setHardness(0.7F).setStepSound(soundTypeMetal).setUnlocalizedName("detectorRail"));
/* 1304 */     registerBlock(29, "sticky_piston", (new BlockPistonBase(true)).setUnlocalizedName("pistonStickyBase"));
/* 1305 */     registerBlock(30, "web", (new BlockWeb()).setLightOpacity(1).setHardness(4.0F).setUnlocalizedName("web"));
/* 1306 */     registerBlock(31, "tallgrass", (new BlockTallGrass()).setHardness(0.0F).setStepSound(soundTypeGrass).setUnlocalizedName("tallgrass"));
/* 1307 */     registerBlock(32, "deadbush", (new BlockDeadBush()).setHardness(0.0F).setStepSound(soundTypeGrass).setUnlocalizedName("deadbush"));
/* 1308 */     registerBlock(33, "piston", (new BlockPistonBase(false)).setUnlocalizedName("pistonBase"));
/* 1309 */     registerBlock(34, "piston_head", (new BlockPistonExtension()).setUnlocalizedName("pistonBase"));
/* 1310 */     registerBlock(35, "wool", (new BlockColored(Material.cloth)).setHardness(0.8F).setStepSound(soundTypeCloth).setUnlocalizedName("cloth"));
/* 1311 */     registerBlock(36, "piston_extension", new BlockPistonMoving());
/* 1312 */     registerBlock(37, "yellow_flower", (new BlockYellowFlower()).setHardness(0.0F).setStepSound(soundTypeGrass).setUnlocalizedName("flower1"));
/* 1313 */     registerBlock(38, "red_flower", (new BlockRedFlower()).setHardness(0.0F).setStepSound(soundTypeGrass).setUnlocalizedName("flower2"));
/* 1314 */     Block block3 = (new BlockMushroom()).setHardness(0.0F).setStepSound(soundTypeGrass).setLightLevel(0.125F).setUnlocalizedName("mushroom");
/* 1315 */     registerBlock(39, "brown_mushroom", block3);
/* 1316 */     Block block4 = (new BlockMushroom()).setHardness(0.0F).setStepSound(soundTypeGrass).setUnlocalizedName("mushroom");
/* 1317 */     registerBlock(40, "red_mushroom", block4);
/* 1318 */     registerBlock(41, "gold_block", (new Block(Material.iron, MapColor.goldColor)).setHardness(3.0F).setResistance(10.0F).setStepSound(soundTypeMetal).setUnlocalizedName("blockGold").setCreativeTab(CreativeTabs.tabBlock));
/* 1319 */     registerBlock(42, "iron_block", (new Block(Material.iron, MapColor.ironColor)).setHardness(5.0F).setResistance(10.0F).setStepSound(soundTypeMetal).setUnlocalizedName("blockIron").setCreativeTab(CreativeTabs.tabBlock));
/* 1320 */     registerBlock(43, "double_stone_slab", (new BlockDoubleStoneSlab()).setHardness(2.0F).setResistance(10.0F).setStepSound(soundTypePiston).setUnlocalizedName("stoneSlab"));
/* 1321 */     registerBlock(44, "stone_slab", (new BlockHalfStoneSlab()).setHardness(2.0F).setResistance(10.0F).setStepSound(soundTypePiston).setUnlocalizedName("stoneSlab"));
/* 1322 */     Block block5 = (new Block(Material.rock, MapColor.redColor)).setHardness(2.0F).setResistance(10.0F).setStepSound(soundTypePiston).setUnlocalizedName("brick").setCreativeTab(CreativeTabs.tabBlock);
/* 1323 */     registerBlock(45, "brick_block", block5);
/* 1324 */     registerBlock(46, "tnt", (new BlockTNT()).setHardness(0.0F).setStepSound(soundTypeGrass).setUnlocalizedName("tnt"));
/* 1325 */     registerBlock(47, "bookshelf", (new BlockBookshelf()).setHardness(1.5F).setStepSound(soundTypeWood).setUnlocalizedName("bookshelf"));
/* 1326 */     registerBlock(48, "mossy_cobblestone", (new Block(Material.rock)).setHardness(2.0F).setResistance(10.0F).setStepSound(soundTypePiston).setUnlocalizedName("stoneMoss").setCreativeTab(CreativeTabs.tabBlock));
/* 1327 */     registerBlock(49, "obsidian", (new BlockObsidian()).setHardness(50.0F).setResistance(2000.0F).setStepSound(soundTypePiston).setUnlocalizedName("obsidian"));
/* 1328 */     registerBlock(50, "torch", (new BlockTorch()).setHardness(0.0F).setLightLevel(0.9375F).setStepSound(soundTypeWood).setUnlocalizedName("torch"));
/* 1329 */     registerBlock(51, "fire", (new BlockFire()).setHardness(0.0F).setLightLevel(1.0F).setStepSound(soundTypeCloth).setUnlocalizedName("fire").disableStats());
/* 1330 */     registerBlock(52, "mob_spawner", (new BlockMobSpawner()).setHardness(5.0F).setStepSound(soundTypeMetal).setUnlocalizedName("mobSpawner").disableStats());
/* 1331 */     registerBlock(53, "oak_stairs", (new BlockStairs(block1.getDefaultState().withProperty((IProperty)BlockPlanks.VARIANT, BlockPlanks.EnumType.OAK))).setUnlocalizedName("stairsWood"));
/* 1332 */     registerBlock(54, "chest", (new BlockChest(0)).setHardness(2.5F).setStepSound(soundTypeWood).setUnlocalizedName("chest"));
/* 1333 */     registerBlock(55, "redstone_wire", (new BlockRedstoneWire()).setHardness(0.0F).setStepSound(soundTypeStone).setUnlocalizedName("redstoneDust").disableStats());
/* 1334 */     registerBlock(56, "diamond_ore", (new BlockOre()).setHardness(3.0F).setResistance(5.0F).setStepSound(soundTypePiston).setUnlocalizedName("oreDiamond"));
/* 1335 */     registerBlock(57, "diamond_block", (new Block(Material.iron, MapColor.diamondColor)).setHardness(5.0F).setResistance(10.0F).setStepSound(soundTypeMetal).setUnlocalizedName("blockDiamond").setCreativeTab(CreativeTabs.tabBlock));
/* 1336 */     registerBlock(58, "crafting_table", (new BlockWorkbench()).setHardness(2.5F).setStepSound(soundTypeWood).setUnlocalizedName("workbench"));
/* 1337 */     registerBlock(59, "wheat", (new BlockCrops()).setUnlocalizedName("crops"));
/* 1338 */     Block block6 = (new BlockFarmland()).setHardness(0.6F).setStepSound(soundTypeGravel).setUnlocalizedName("farmland");
/* 1339 */     registerBlock(60, "farmland", block6);
/* 1340 */     registerBlock(61, "furnace", (new BlockFurnace(false)).setHardness(3.5F).setStepSound(soundTypePiston).setUnlocalizedName("furnace").setCreativeTab(CreativeTabs.tabDecorations));
/* 1341 */     registerBlock(62, "lit_furnace", (new BlockFurnace(true)).setHardness(3.5F).setStepSound(soundTypePiston).setLightLevel(0.875F).setUnlocalizedName("furnace"));
/* 1342 */     registerBlock(63, "standing_sign", (new BlockStandingSign()).setHardness(1.0F).setStepSound(soundTypeWood).setUnlocalizedName("sign").disableStats());
/* 1343 */     registerBlock(64, "wooden_door", (new BlockDoor(Material.wood)).setHardness(3.0F).setStepSound(soundTypeWood).setUnlocalizedName("doorOak").disableStats());
/* 1344 */     registerBlock(65, "ladder", (new BlockLadder()).setHardness(0.4F).setStepSound(soundTypeLadder).setUnlocalizedName("ladder"));
/* 1345 */     registerBlock(66, "rail", (new BlockRail()).setHardness(0.7F).setStepSound(soundTypeMetal).setUnlocalizedName("rail"));
/* 1346 */     registerBlock(67, "stone_stairs", (new BlockStairs(block.getDefaultState())).setUnlocalizedName("stairsStone"));
/* 1347 */     registerBlock(68, "wall_sign", (new BlockWallSign()).setHardness(1.0F).setStepSound(soundTypeWood).setUnlocalizedName("sign").disableStats());
/* 1348 */     registerBlock(69, "lever", (new BlockLever()).setHardness(0.5F).setStepSound(soundTypeWood).setUnlocalizedName("lever"));
/* 1349 */     registerBlock(70, "stone_pressure_plate", (new BlockPressurePlate(Material.rock, BlockPressurePlate.Sensitivity.MOBS)).setHardness(0.5F).setStepSound(soundTypePiston).setUnlocalizedName("pressurePlateStone"));
/* 1350 */     registerBlock(71, "iron_door", (new BlockDoor(Material.iron)).setHardness(5.0F).setStepSound(soundTypeMetal).setUnlocalizedName("doorIron").disableStats());
/* 1351 */     registerBlock(72, "wooden_pressure_plate", (new BlockPressurePlate(Material.wood, BlockPressurePlate.Sensitivity.EVERYTHING)).setHardness(0.5F).setStepSound(soundTypeWood).setUnlocalizedName("pressurePlateWood"));
/* 1352 */     registerBlock(73, "redstone_ore", (new BlockRedstoneOre(false)).setHardness(3.0F).setResistance(5.0F).setStepSound(soundTypePiston).setUnlocalizedName("oreRedstone").setCreativeTab(CreativeTabs.tabBlock));
/* 1353 */     registerBlock(74, "lit_redstone_ore", (new BlockRedstoneOre(true)).setLightLevel(0.625F).setHardness(3.0F).setResistance(5.0F).setStepSound(soundTypePiston).setUnlocalizedName("oreRedstone"));
/* 1354 */     registerBlock(75, "unlit_redstone_torch", (new BlockRedstoneTorch(false)).setHardness(0.0F).setStepSound(soundTypeWood).setUnlocalizedName("notGate"));
/* 1355 */     registerBlock(76, "redstone_torch", (new BlockRedstoneTorch(true)).setHardness(0.0F).setLightLevel(0.5F).setStepSound(soundTypeWood).setUnlocalizedName("notGate").setCreativeTab(CreativeTabs.tabRedstone));
/* 1356 */     registerBlock(77, "stone_button", (new BlockButtonStone()).setHardness(0.5F).setStepSound(soundTypePiston).setUnlocalizedName("button"));
/* 1357 */     registerBlock(78, "snow_layer", (new BlockSnow()).setHardness(0.1F).setStepSound(soundTypeSnow).setUnlocalizedName("snow").setLightOpacity(0));
/* 1358 */     registerBlock(79, "ice", (new BlockIce()).setHardness(0.5F).setLightOpacity(3).setStepSound(soundTypeGlass).setUnlocalizedName("ice"));
/* 1359 */     registerBlock(80, "snow", (new BlockSnowBlock()).setHardness(0.2F).setStepSound(soundTypeSnow).setUnlocalizedName("snow"));
/* 1360 */     registerBlock(81, "cactus", (new BlockCactus()).setHardness(0.4F).setStepSound(soundTypeCloth).setUnlocalizedName("cactus"));
/* 1361 */     registerBlock(82, "clay", (new BlockClay()).setHardness(0.6F).setStepSound(soundTypeGravel).setUnlocalizedName("clay"));
/* 1362 */     registerBlock(83, "reeds", (new BlockReed()).setHardness(0.0F).setStepSound(soundTypeGrass).setUnlocalizedName("reeds").disableStats());
/* 1363 */     registerBlock(84, "jukebox", (new BlockJukebox()).setHardness(2.0F).setResistance(10.0F).setStepSound(soundTypePiston).setUnlocalizedName("jukebox"));
/* 1364 */     registerBlock(85, "fence", (new BlockFence(Material.wood, BlockPlanks.EnumType.OAK.getMapColor())).setHardness(2.0F).setResistance(5.0F).setStepSound(soundTypeWood).setUnlocalizedName("fence"));
/* 1365 */     Block block7 = (new BlockPumpkin()).setHardness(1.0F).setStepSound(soundTypeWood).setUnlocalizedName("pumpkin");
/* 1366 */     registerBlock(86, "pumpkin", block7);
/* 1367 */     registerBlock(87, "netherrack", (new BlockNetherrack()).setHardness(0.4F).setStepSound(soundTypePiston).setUnlocalizedName("hellrock"));
/* 1368 */     registerBlock(88, "soul_sand", (new BlockSoulSand()).setHardness(0.5F).setStepSound(soundTypeSand).setUnlocalizedName("hellsand"));
/* 1369 */     registerBlock(89, "glowstone", (new BlockGlowstone(Material.glass)).setHardness(0.3F).setStepSound(soundTypeGlass).setLightLevel(1.0F).setUnlocalizedName("lightgem"));
/* 1370 */     registerBlock(90, "portal", (new BlockPortal()).setHardness(-1.0F).setStepSound(soundTypeGlass).setLightLevel(0.75F).setUnlocalizedName("portal"));
/* 1371 */     registerBlock(91, "lit_pumpkin", (new BlockPumpkin()).setHardness(1.0F).setStepSound(soundTypeWood).setLightLevel(1.0F).setUnlocalizedName("litpumpkin"));
/* 1372 */     registerBlock(92, "cake", (new BlockCake()).setHardness(0.5F).setStepSound(soundTypeCloth).setUnlocalizedName("cake").disableStats());
/* 1373 */     registerBlock(93, "unpowered_repeater", (new BlockRedstoneRepeater(false)).setHardness(0.0F).setStepSound(soundTypeWood).setUnlocalizedName("diode").disableStats());
/* 1374 */     registerBlock(94, "powered_repeater", (new BlockRedstoneRepeater(true)).setHardness(0.0F).setStepSound(soundTypeWood).setUnlocalizedName("diode").disableStats());
/* 1375 */     registerBlock(95, "stained_glass", (new BlockStainedGlass(Material.glass)).setHardness(0.3F).setStepSound(soundTypeGlass).setUnlocalizedName("stainedGlass"));
/* 1376 */     registerBlock(96, "trapdoor", (new BlockTrapDoor(Material.wood)).setHardness(3.0F).setStepSound(soundTypeWood).setUnlocalizedName("trapdoor").disableStats());
/* 1377 */     registerBlock(97, "monster_egg", (new BlockSilverfish()).setHardness(0.75F).setUnlocalizedName("monsterStoneEgg"));
/* 1378 */     Block block8 = (new BlockStoneBrick()).setHardness(1.5F).setResistance(10.0F).setStepSound(soundTypePiston).setUnlocalizedName("stonebricksmooth");
/* 1379 */     registerBlock(98, "stonebrick", block8);
/* 1380 */     registerBlock(99, "brown_mushroom_block", (new BlockHugeMushroom(Material.wood, MapColor.dirtColor, block3)).setHardness(0.2F).setStepSound(soundTypeWood).setUnlocalizedName("mushroom"));
/* 1381 */     registerBlock(100, "red_mushroom_block", (new BlockHugeMushroom(Material.wood, MapColor.redColor, block4)).setHardness(0.2F).setStepSound(soundTypeWood).setUnlocalizedName("mushroom"));
/* 1382 */     registerBlock(101, "iron_bars", (new BlockPane(Material.iron, true)).setHardness(5.0F).setResistance(10.0F).setStepSound(soundTypeMetal).setUnlocalizedName("fenceIron"));
/* 1383 */     registerBlock(102, "glass_pane", (new BlockPane(Material.glass, false)).setHardness(0.3F).setStepSound(soundTypeGlass).setUnlocalizedName("thinGlass"));
/* 1384 */     Block block9 = (new BlockMelon()).setHardness(1.0F).setStepSound(soundTypeWood).setUnlocalizedName("melon");
/* 1385 */     registerBlock(103, "melon_block", block9);
/* 1386 */     registerBlock(104, "pumpkin_stem", (new BlockStem(block7)).setHardness(0.0F).setStepSound(soundTypeWood).setUnlocalizedName("pumpkinStem"));
/* 1387 */     registerBlock(105, "melon_stem", (new BlockStem(block9)).setHardness(0.0F).setStepSound(soundTypeWood).setUnlocalizedName("pumpkinStem"));
/* 1388 */     registerBlock(106, "vine", (new BlockVine()).setHardness(0.2F).setStepSound(soundTypeGrass).setUnlocalizedName("vine"));
/* 1389 */     registerBlock(107, "fence_gate", (new BlockFenceGate(BlockPlanks.EnumType.OAK)).setHardness(2.0F).setResistance(5.0F).setStepSound(soundTypeWood).setUnlocalizedName("fenceGate"));
/* 1390 */     registerBlock(108, "brick_stairs", (new BlockStairs(block5.getDefaultState())).setUnlocalizedName("stairsBrick"));
/* 1391 */     registerBlock(109, "stone_brick_stairs", (new BlockStairs(block8.getDefaultState().withProperty((IProperty)BlockStoneBrick.VARIANT, BlockStoneBrick.EnumType.DEFAULT))).setUnlocalizedName("stairsStoneBrickSmooth"));
/* 1392 */     registerBlock(110, "mycelium", (new BlockMycelium()).setHardness(0.6F).setStepSound(soundTypeGrass).setUnlocalizedName("mycel"));
/* 1393 */     registerBlock(111, "waterlily", (new BlockLilyPad()).setHardness(0.0F).setStepSound(soundTypeGrass).setUnlocalizedName("waterlily"));
/* 1394 */     Block block10 = (new BlockNetherBrick()).setHardness(2.0F).setResistance(10.0F).setStepSound(soundTypePiston).setUnlocalizedName("netherBrick").setCreativeTab(CreativeTabs.tabBlock);
/* 1395 */     registerBlock(112, "nether_brick", block10);
/* 1396 */     registerBlock(113, "nether_brick_fence", (new BlockFence(Material.rock, MapColor.netherrackColor)).setHardness(2.0F).setResistance(10.0F).setStepSound(soundTypePiston).setUnlocalizedName("netherFence"));
/* 1397 */     registerBlock(114, "nether_brick_stairs", (new BlockStairs(block10.getDefaultState())).setUnlocalizedName("stairsNetherBrick"));
/* 1398 */     registerBlock(115, "nether_wart", (new BlockNetherWart()).setUnlocalizedName("netherStalk"));
/* 1399 */     registerBlock(116, "enchanting_table", (new BlockEnchantmentTable()).setHardness(5.0F).setResistance(2000.0F).setUnlocalizedName("enchantmentTable"));
/* 1400 */     registerBlock(117, "brewing_stand", (new BlockBrewingStand()).setHardness(0.5F).setLightLevel(0.125F).setUnlocalizedName("brewingStand"));
/* 1401 */     registerBlock(118, "cauldron", (new BlockCauldron()).setHardness(2.0F).setUnlocalizedName("cauldron"));
/* 1402 */     registerBlock(119, "end_portal", (new BlockEndPortal(Material.portal)).setHardness(-1.0F).setResistance(6000000.0F));
/* 1403 */     registerBlock(120, "end_portal_frame", (new BlockEndPortalFrame()).setStepSound(soundTypeGlass).setLightLevel(0.125F).setHardness(-1.0F).setUnlocalizedName("endPortalFrame").setResistance(6000000.0F).setCreativeTab(CreativeTabs.tabDecorations));
/* 1404 */     registerBlock(121, "end_stone", (new Block(Material.rock, MapColor.sandColor)).setHardness(3.0F).setResistance(15.0F).setStepSound(soundTypePiston).setUnlocalizedName("whiteStone").setCreativeTab(CreativeTabs.tabBlock));
/* 1405 */     registerBlock(122, "dragon_egg", (new BlockDragonEgg()).setHardness(3.0F).setResistance(15.0F).setStepSound(soundTypePiston).setLightLevel(0.125F).setUnlocalizedName("dragonEgg"));
/* 1406 */     registerBlock(123, "redstone_lamp", (new BlockRedstoneLight(false)).setHardness(0.3F).setStepSound(soundTypeGlass).setUnlocalizedName("redstoneLight").setCreativeTab(CreativeTabs.tabRedstone));
/* 1407 */     registerBlock(124, "lit_redstone_lamp", (new BlockRedstoneLight(true)).setHardness(0.3F).setStepSound(soundTypeGlass).setUnlocalizedName("redstoneLight"));
/* 1408 */     registerBlock(125, "double_wooden_slab", (new BlockDoubleWoodSlab()).setHardness(2.0F).setResistance(5.0F).setStepSound(soundTypeWood).setUnlocalizedName("woodSlab"));
/* 1409 */     registerBlock(126, "wooden_slab", (new BlockHalfWoodSlab()).setHardness(2.0F).setResistance(5.0F).setStepSound(soundTypeWood).setUnlocalizedName("woodSlab"));
/* 1410 */     registerBlock(127, "cocoa", (new BlockCocoa()).setHardness(0.2F).setResistance(5.0F).setStepSound(soundTypeWood).setUnlocalizedName("cocoa"));
/* 1411 */     registerBlock(128, "sandstone_stairs", (new BlockStairs(block2.getDefaultState().withProperty((IProperty)BlockSandStone.TYPE, BlockSandStone.EnumType.SMOOTH))).setUnlocalizedName("stairsSandStone"));
/* 1412 */     registerBlock(129, "emerald_ore", (new BlockOre()).setHardness(3.0F).setResistance(5.0F).setStepSound(soundTypePiston).setUnlocalizedName("oreEmerald"));
/* 1413 */     registerBlock(130, "ender_chest", (new BlockEnderChest()).setHardness(22.5F).setResistance(1000.0F).setStepSound(soundTypePiston).setUnlocalizedName("enderChest").setLightLevel(0.5F));
/* 1414 */     registerBlock(131, "tripwire_hook", (new BlockTripWireHook()).setUnlocalizedName("tripWireSource"));
/* 1415 */     registerBlock(132, "tripwire", (new BlockTripWire()).setUnlocalizedName("tripWire"));
/* 1416 */     registerBlock(133, "emerald_block", (new Block(Material.iron, MapColor.emeraldColor)).setHardness(5.0F).setResistance(10.0F).setStepSound(soundTypeMetal).setUnlocalizedName("blockEmerald").setCreativeTab(CreativeTabs.tabBlock));
/* 1417 */     registerBlock(134, "spruce_stairs", (new BlockStairs(block1.getDefaultState().withProperty((IProperty)BlockPlanks.VARIANT, BlockPlanks.EnumType.SPRUCE))).setUnlocalizedName("stairsWoodSpruce"));
/* 1418 */     registerBlock(135, "birch_stairs", (new BlockStairs(block1.getDefaultState().withProperty((IProperty)BlockPlanks.VARIANT, BlockPlanks.EnumType.BIRCH))).setUnlocalizedName("stairsWoodBirch"));
/* 1419 */     registerBlock(136, "jungle_stairs", (new BlockStairs(block1.getDefaultState().withProperty((IProperty)BlockPlanks.VARIANT, BlockPlanks.EnumType.JUNGLE))).setUnlocalizedName("stairsWoodJungle"));
/* 1420 */     registerBlock(137, "command_block", (new BlockCommandBlock()).setBlockUnbreakable().setResistance(6000000.0F).setUnlocalizedName("commandBlock"));
/* 1421 */     registerBlock(138, "beacon", (new BlockBeacon()).setUnlocalizedName("beacon").setLightLevel(1.0F));
/* 1422 */     registerBlock(139, "cobblestone_wall", (new BlockWall(block)).setUnlocalizedName("cobbleWall"));
/* 1423 */     registerBlock(140, "flower_pot", (new BlockFlowerPot()).setHardness(0.0F).setStepSound(soundTypeStone).setUnlocalizedName("flowerPot"));
/* 1424 */     registerBlock(141, "carrots", (new BlockCarrot()).setUnlocalizedName("carrots"));
/* 1425 */     registerBlock(142, "potatoes", (new BlockPotato()).setUnlocalizedName("potatoes"));
/* 1426 */     registerBlock(143, "wooden_button", (new BlockButtonWood()).setHardness(0.5F).setStepSound(soundTypeWood).setUnlocalizedName("button"));
/* 1427 */     registerBlock(144, "skull", (new BlockSkull()).setHardness(1.0F).setStepSound(soundTypePiston).setUnlocalizedName("skull"));
/* 1428 */     registerBlock(145, "anvil", (new BlockAnvil()).setHardness(5.0F).setStepSound(soundTypeAnvil).setResistance(2000.0F).setUnlocalizedName("anvil"));
/* 1429 */     registerBlock(146, "trapped_chest", (new BlockChest(1)).setHardness(2.5F).setStepSound(soundTypeWood).setUnlocalizedName("chestTrap"));
/* 1430 */     registerBlock(147, "light_weighted_pressure_plate", (new BlockPressurePlateWeighted(Material.iron, 15, MapColor.goldColor)).setHardness(0.5F).setStepSound(soundTypeWood).setUnlocalizedName("weightedPlate_light"));
/* 1431 */     registerBlock(148, "heavy_weighted_pressure_plate", (new BlockPressurePlateWeighted(Material.iron, 150)).setHardness(0.5F).setStepSound(soundTypeWood).setUnlocalizedName("weightedPlate_heavy"));
/* 1432 */     registerBlock(149, "unpowered_comparator", (new BlockRedstoneComparator(false)).setHardness(0.0F).setStepSound(soundTypeWood).setUnlocalizedName("comparator").disableStats());
/* 1433 */     registerBlock(150, "powered_comparator", (new BlockRedstoneComparator(true)).setHardness(0.0F).setLightLevel(0.625F).setStepSound(soundTypeWood).setUnlocalizedName("comparator").disableStats());
/* 1434 */     registerBlock(151, "daylight_detector", new BlockDaylightDetector(false));
/* 1435 */     registerBlock(152, "redstone_block", (new BlockCompressedPowered(Material.iron, MapColor.tntColor)).setHardness(5.0F).setResistance(10.0F).setStepSound(soundTypeMetal).setUnlocalizedName("blockRedstone").setCreativeTab(CreativeTabs.tabRedstone));
/* 1436 */     registerBlock(153, "quartz_ore", (new BlockOre(MapColor.netherrackColor)).setHardness(3.0F).setResistance(5.0F).setStepSound(soundTypePiston).setUnlocalizedName("netherquartz"));
/* 1437 */     registerBlock(154, "hopper", (new BlockHopper()).setHardness(3.0F).setResistance(8.0F).setStepSound(soundTypeMetal).setUnlocalizedName("hopper"));
/* 1438 */     Block block11 = (new BlockQuartz()).setStepSound(soundTypePiston).setHardness(0.8F).setUnlocalizedName("quartzBlock");
/* 1439 */     registerBlock(155, "quartz_block", block11);
/* 1440 */     registerBlock(156, "quartz_stairs", (new BlockStairs(block11.getDefaultState().withProperty((IProperty)BlockQuartz.VARIANT, BlockQuartz.EnumType.DEFAULT))).setUnlocalizedName("stairsQuartz"));
/* 1441 */     registerBlock(157, "activator_rail", (new BlockRailPowered()).setHardness(0.7F).setStepSound(soundTypeMetal).setUnlocalizedName("activatorRail"));
/* 1442 */     registerBlock(158, "dropper", (new BlockDropper()).setHardness(3.5F).setStepSound(soundTypePiston).setUnlocalizedName("dropper"));
/* 1443 */     registerBlock(159, "stained_hardened_clay", (new BlockColored(Material.rock)).setHardness(1.25F).setResistance(7.0F).setStepSound(soundTypePiston).setUnlocalizedName("clayHardenedStained"));
/* 1444 */     registerBlock(160, "stained_glass_pane", (new BlockStainedGlassPane()).setHardness(0.3F).setStepSound(soundTypeGlass).setUnlocalizedName("thinStainedGlass"));
/* 1445 */     registerBlock(161, "leaves2", (new BlockNewLeaf()).setUnlocalizedName("leaves"));
/* 1446 */     registerBlock(162, "log2", (new BlockNewLog()).setUnlocalizedName("log"));
/* 1447 */     registerBlock(163, "acacia_stairs", (new BlockStairs(block1.getDefaultState().withProperty((IProperty)BlockPlanks.VARIANT, BlockPlanks.EnumType.ACACIA))).setUnlocalizedName("stairsWoodAcacia"));
/* 1448 */     registerBlock(164, "dark_oak_stairs", (new BlockStairs(block1.getDefaultState().withProperty((IProperty)BlockPlanks.VARIANT, BlockPlanks.EnumType.DARK_OAK))).setUnlocalizedName("stairsWoodDarkOak"));
/* 1449 */     registerBlock(165, "slime", (new BlockSlime()).setUnlocalizedName("slime").setStepSound(SLIME_SOUND));
/* 1450 */     registerBlock(166, "barrier", (new BlockBarrier()).setUnlocalizedName("barrier"));
/* 1451 */     registerBlock(167, "iron_trapdoor", (new BlockTrapDoor(Material.iron)).setHardness(5.0F).setStepSound(soundTypeMetal).setUnlocalizedName("ironTrapdoor").disableStats());
/* 1452 */     registerBlock(168, "prismarine", (new BlockPrismarine()).setHardness(1.5F).setResistance(10.0F).setStepSound(soundTypePiston).setUnlocalizedName("prismarine"));
/* 1453 */     registerBlock(169, "sea_lantern", (new BlockSeaLantern(Material.glass)).setHardness(0.3F).setStepSound(soundTypeGlass).setLightLevel(1.0F).setUnlocalizedName("seaLantern"));
/* 1454 */     registerBlock(170, "hay_block", (new BlockHay()).setHardness(0.5F).setStepSound(soundTypeGrass).setUnlocalizedName("hayBlock").setCreativeTab(CreativeTabs.tabBlock));
/* 1455 */     registerBlock(171, "carpet", (new BlockCarpet()).setHardness(0.1F).setStepSound(soundTypeCloth).setUnlocalizedName("woolCarpet").setLightOpacity(0));
/* 1456 */     registerBlock(172, "hardened_clay", (new BlockHardenedClay()).setHardness(1.25F).setResistance(7.0F).setStepSound(soundTypePiston).setUnlocalizedName("clayHardened"));
/* 1457 */     registerBlock(173, "coal_block", (new Block(Material.rock, MapColor.blackColor)).setHardness(5.0F).setResistance(10.0F).setStepSound(soundTypePiston).setUnlocalizedName("blockCoal").setCreativeTab(CreativeTabs.tabBlock));
/* 1458 */     registerBlock(174, "packed_ice", (new BlockPackedIce()).setHardness(0.5F).setStepSound(soundTypeGlass).setUnlocalizedName("icePacked"));
/* 1459 */     registerBlock(175, "double_plant", new BlockDoublePlant());
/* 1460 */     registerBlock(176, "standing_banner", (new BlockBanner.BlockBannerStanding()).setHardness(1.0F).setStepSound(soundTypeWood).setUnlocalizedName("banner").disableStats());
/* 1461 */     registerBlock(177, "wall_banner", (new BlockBanner.BlockBannerHanging()).setHardness(1.0F).setStepSound(soundTypeWood).setUnlocalizedName("banner").disableStats());
/* 1462 */     registerBlock(178, "daylight_detector_inverted", new BlockDaylightDetector(true));
/* 1463 */     Block block12 = (new BlockRedSandstone()).setStepSound(soundTypePiston).setHardness(0.8F).setUnlocalizedName("redSandStone");
/* 1464 */     registerBlock(179, "red_sandstone", block12);
/* 1465 */     registerBlock(180, "red_sandstone_stairs", (new BlockStairs(block12.getDefaultState().withProperty((IProperty)BlockRedSandstone.TYPE, BlockRedSandstone.EnumType.SMOOTH))).setUnlocalizedName("stairsRedSandStone"));
/* 1466 */     registerBlock(181, "double_stone_slab2", (new BlockDoubleStoneSlabNew()).setHardness(2.0F).setResistance(10.0F).setStepSound(soundTypePiston).setUnlocalizedName("stoneSlab2"));
/* 1467 */     registerBlock(182, "stone_slab2", (new BlockHalfStoneSlabNew()).setHardness(2.0F).setResistance(10.0F).setStepSound(soundTypePiston).setUnlocalizedName("stoneSlab2"));
/* 1468 */     registerBlock(183, "spruce_fence_gate", (new BlockFenceGate(BlockPlanks.EnumType.SPRUCE)).setHardness(2.0F).setResistance(5.0F).setStepSound(soundTypeWood).setUnlocalizedName("spruceFenceGate"));
/* 1469 */     registerBlock(184, "birch_fence_gate", (new BlockFenceGate(BlockPlanks.EnumType.BIRCH)).setHardness(2.0F).setResistance(5.0F).setStepSound(soundTypeWood).setUnlocalizedName("birchFenceGate"));
/* 1470 */     registerBlock(185, "jungle_fence_gate", (new BlockFenceGate(BlockPlanks.EnumType.JUNGLE)).setHardness(2.0F).setResistance(5.0F).setStepSound(soundTypeWood).setUnlocalizedName("jungleFenceGate"));
/* 1471 */     registerBlock(186, "dark_oak_fence_gate", (new BlockFenceGate(BlockPlanks.EnumType.DARK_OAK)).setHardness(2.0F).setResistance(5.0F).setStepSound(soundTypeWood).setUnlocalizedName("darkOakFenceGate"));
/* 1472 */     registerBlock(187, "acacia_fence_gate", (new BlockFenceGate(BlockPlanks.EnumType.ACACIA)).setHardness(2.0F).setResistance(5.0F).setStepSound(soundTypeWood).setUnlocalizedName("acaciaFenceGate"));
/* 1473 */     registerBlock(188, "spruce_fence", (new BlockFence(Material.wood, BlockPlanks.EnumType.SPRUCE.getMapColor())).setHardness(2.0F).setResistance(5.0F).setStepSound(soundTypeWood).setUnlocalizedName("spruceFence"));
/* 1474 */     registerBlock(189, "birch_fence", (new BlockFence(Material.wood, BlockPlanks.EnumType.BIRCH.getMapColor())).setHardness(2.0F).setResistance(5.0F).setStepSound(soundTypeWood).setUnlocalizedName("birchFence"));
/* 1475 */     registerBlock(190, "jungle_fence", (new BlockFence(Material.wood, BlockPlanks.EnumType.JUNGLE.getMapColor())).setHardness(2.0F).setResistance(5.0F).setStepSound(soundTypeWood).setUnlocalizedName("jungleFence"));
/* 1476 */     registerBlock(191, "dark_oak_fence", (new BlockFence(Material.wood, BlockPlanks.EnumType.DARK_OAK.getMapColor())).setHardness(2.0F).setResistance(5.0F).setStepSound(soundTypeWood).setUnlocalizedName("darkOakFence"));
/* 1477 */     registerBlock(192, "acacia_fence", (new BlockFence(Material.wood, BlockPlanks.EnumType.ACACIA.getMapColor())).setHardness(2.0F).setResistance(5.0F).setStepSound(soundTypeWood).setUnlocalizedName("acaciaFence"));
/* 1478 */     registerBlock(193, "spruce_door", (new BlockDoor(Material.wood)).setHardness(3.0F).setStepSound(soundTypeWood).setUnlocalizedName("doorSpruce").disableStats());
/* 1479 */     registerBlock(194, "birch_door", (new BlockDoor(Material.wood)).setHardness(3.0F).setStepSound(soundTypeWood).setUnlocalizedName("doorBirch").disableStats());
/* 1480 */     registerBlock(195, "jungle_door", (new BlockDoor(Material.wood)).setHardness(3.0F).setStepSound(soundTypeWood).setUnlocalizedName("doorJungle").disableStats());
/* 1481 */     registerBlock(196, "acacia_door", (new BlockDoor(Material.wood)).setHardness(3.0F).setStepSound(soundTypeWood).setUnlocalizedName("doorAcacia").disableStats());
/* 1482 */     registerBlock(197, "dark_oak_door", (new BlockDoor(Material.wood)).setHardness(3.0F).setStepSound(soundTypeWood).setUnlocalizedName("doorDarkOak").disableStats());
/* 1483 */     blockRegistry.validateKey();
/*      */     
/* 1485 */     for (Block block13 : blockRegistry) {
/*      */       
/* 1487 */       if (block13.blockMaterial == Material.air) {
/*      */         
/* 1489 */         block13.useNeighborBrightness = false;
/*      */         
/*      */         continue;
/*      */       } 
/* 1493 */       boolean flag = false;
/* 1494 */       boolean flag1 = block13 instanceof BlockStairs;
/* 1495 */       boolean flag2 = block13 instanceof BlockSlab;
/* 1496 */       boolean flag3 = (block13 == block6);
/* 1497 */       boolean flag4 = block13.translucent;
/* 1498 */       boolean flag5 = (block13.lightOpacity == 0);
/*      */       
/* 1500 */       if (flag1 || flag2 || flag3 || flag4 || flag5)
/*      */       {
/* 1502 */         flag = true;
/*      */       }
/*      */       
/* 1505 */       block13.useNeighborBrightness = flag;
/*      */     } 
/*      */ 
/*      */     
/* 1509 */     for (Block block14 : blockRegistry) {
/*      */       
/* 1511 */       for (UnmodifiableIterator<IBlockState> unmodifiableIterator = block14.getBlockState().getValidStates().iterator(); unmodifiableIterator.hasNext(); ) { IBlockState iblockstate = unmodifiableIterator.next();
/*      */         
/* 1513 */         int i = blockRegistry.getIDForObject(block14) << 4 | block14.getMetaFromState(iblockstate);
/* 1514 */         BLOCK_STATE_IDS.put(iblockstate, i); }
/*      */     
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   private static void registerBlock(int id, ResourceLocation textualID, Block block_) {
/* 1521 */     blockRegistry.register(id, textualID, block_);
/*      */   }
/*      */ 
/*      */   
/*      */   private static void registerBlock(int id, String textualID, Block block_) {
/* 1526 */     registerBlock(id, new ResourceLocation(textualID), block_);
/*      */   }
/*      */   
/*      */   public boolean isSolidFullCube() {
/* 1530 */     return (this.blockMaterial.isSolid() && isFullCube());
/*      */   }
/*      */   
/*      */   public enum EnumOffsetType
/*      */   {
/* 1535 */     NONE,
/* 1536 */     XZ,
/* 1537 */     XYZ;
/*      */   }
/*      */ 
/*      */   
/*      */   public static class SoundType
/*      */   {
/*      */     public final String soundName;
/*      */     public final float volume;
/*      */     public final float frequency;
/*      */     
/*      */     public SoundType(String name, float volume, float frequency) {
/* 1548 */       this.soundName = name;
/* 1549 */       this.volume = volume;
/* 1550 */       this.frequency = frequency;
/*      */     }
/*      */ 
/*      */     
/*      */     public float getVolume() {
/* 1555 */       return this.volume;
/*      */     }
/*      */ 
/*      */     
/*      */     public float getFrequency() {
/* 1560 */       return this.frequency;
/*      */     }
/*      */ 
/*      */     
/*      */     public String getBreakSound() {
/* 1565 */       return "dig." + this.soundName;
/*      */     }
/*      */ 
/*      */     
/*      */     public String getStepSound() {
/* 1570 */       return "step." + this.soundName;
/*      */     }
/*      */ 
/*      */     
/*      */     public String getPlaceSound() {
/* 1575 */       return getBreakSound();
/*      */     }
/*      */   }
/*      */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\minecraft\block\Block.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */