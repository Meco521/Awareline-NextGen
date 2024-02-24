/*     */ package net.minecraft.block;
/*     */ import java.util.Random;
/*     */ import net.minecraft.block.properties.IProperty;
/*     */ import net.minecraft.block.properties.PropertyBool;
/*     */ import net.minecraft.block.properties.PropertyEnum;
/*     */ import net.minecraft.block.state.BlockState;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.init.Items;
/*     */ import net.minecraft.item.Item;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.util.ChatComponentTranslation;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ import net.minecraft.util.EnumWorldBlockLayer;
/*     */ import net.minecraft.util.IChatComponent;
/*     */ import net.minecraft.util.IStringSerializable;
/*     */ import net.minecraft.world.IBlockAccess;
/*     */ import net.minecraft.world.World;
/*     */ import net.minecraft.world.biome.BiomeGenBase;
/*     */ 
/*     */ public class BlockBed extends BlockDirectional {
/*  22 */   public static final PropertyEnum<EnumPartType> PART = PropertyEnum.create("part", EnumPartType.class);
/*  23 */   public static final PropertyBool OCCUPIED = PropertyBool.create("occupied");
/*     */ 
/*     */   
/*     */   public BlockBed() {
/*  27 */     super(Material.cloth);
/*  28 */     setDefaultState(this.blockState.getBaseState().withProperty((IProperty)PART, EnumPartType.FOOT).withProperty((IProperty)OCCUPIED, Boolean.valueOf(false)));
/*  29 */     setBedBounds();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumFacing side, float hitX, float hitY, float hitZ) {
/*  34 */     if (worldIn.isRemote)
/*     */     {
/*  36 */       return true;
/*     */     }
/*     */ 
/*     */     
/*  40 */     if (state.getValue((IProperty)PART) != EnumPartType.HEAD) {
/*     */       
/*  42 */       pos = pos.offset((EnumFacing)state.getValue((IProperty)FACING));
/*  43 */       state = worldIn.getBlockState(pos);
/*     */       
/*  45 */       if (state.getBlock() != this)
/*     */       {
/*  47 */         return true;
/*     */       }
/*     */     } 
/*     */     
/*  51 */     if (worldIn.provider.canRespawnHere() && worldIn.getBiomeGenForCoords(pos) != BiomeGenBase.hell) {
/*     */       
/*  53 */       if (((Boolean)state.getValue((IProperty)OCCUPIED)).booleanValue()) {
/*     */         
/*  55 */         EntityPlayer entityplayer = getPlayerInBed(worldIn, pos);
/*     */         
/*  57 */         if (entityplayer != null) {
/*     */           
/*  59 */           playerIn.addChatComponentMessage((IChatComponent)new ChatComponentTranslation("tile.bed.occupied", new Object[0]));
/*  60 */           return true;
/*     */         } 
/*     */         
/*  63 */         state = state.withProperty((IProperty)OCCUPIED, Boolean.valueOf(false));
/*  64 */         worldIn.setBlockState(pos, state, 4);
/*     */       } 
/*     */       
/*  67 */       EntityPlayer.EnumStatus entityplayer$enumstatus = playerIn.trySleep(pos);
/*     */       
/*  69 */       if (entityplayer$enumstatus == EntityPlayer.EnumStatus.OK) {
/*     */         
/*  71 */         state = state.withProperty((IProperty)OCCUPIED, Boolean.valueOf(true));
/*  72 */         worldIn.setBlockState(pos, state, 4);
/*  73 */         return true;
/*     */       } 
/*     */ 
/*     */       
/*  77 */       if (entityplayer$enumstatus == EntityPlayer.EnumStatus.NOT_POSSIBLE_NOW) {
/*     */         
/*  79 */         playerIn.addChatComponentMessage((IChatComponent)new ChatComponentTranslation("tile.bed.noSleep", new Object[0]));
/*     */       }
/*  81 */       else if (entityplayer$enumstatus == EntityPlayer.EnumStatus.NOT_SAFE) {
/*     */         
/*  83 */         playerIn.addChatComponentMessage((IChatComponent)new ChatComponentTranslation("tile.bed.notSafe", new Object[0]));
/*     */       } 
/*     */       
/*  86 */       return true;
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/*  91 */     worldIn.setBlockToAir(pos);
/*  92 */     BlockPos blockpos = pos.offset(((EnumFacing)state.getValue((IProperty)FACING)).getOpposite());
/*     */     
/*  94 */     if (worldIn.getBlockState(blockpos).getBlock() == this)
/*     */     {
/*  96 */       worldIn.setBlockToAir(blockpos);
/*     */     }
/*     */     
/*  99 */     worldIn.newExplosion((Entity)null, pos.getX() + 0.5D, pos.getY() + 0.5D, pos.getZ() + 0.5D, 5.0F, true, true);
/* 100 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private EntityPlayer getPlayerInBed(World worldIn, BlockPos pos) {
/* 107 */     for (EntityPlayer entityplayer : worldIn.playerEntities) {
/*     */       
/* 109 */       if (entityplayer.isPlayerSleeping() && entityplayer.playerLocation.equals(pos))
/*     */       {
/* 111 */         return entityplayer;
/*     */       }
/*     */     } 
/*     */     
/* 115 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isFullCube() {
/* 120 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isOpaqueCube() {
/* 128 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setBlockBoundsBasedOnState(IBlockAccess worldIn, BlockPos pos) {
/* 133 */     setBedBounds();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onNeighborBlockChange(World worldIn, BlockPos pos, IBlockState state, Block neighborBlock) {
/* 141 */     EnumFacing enumfacing = (EnumFacing)state.getValue((IProperty)FACING);
/*     */     
/* 143 */     if (state.getValue((IProperty)PART) == EnumPartType.HEAD) {
/*     */       
/* 145 */       if (worldIn.getBlockState(pos.offset(enumfacing.getOpposite())).getBlock() != this)
/*     */       {
/* 147 */         worldIn.setBlockToAir(pos);
/*     */       }
/*     */     }
/* 150 */     else if (worldIn.getBlockState(pos.offset(enumfacing)).getBlock() != this) {
/*     */       
/* 152 */       worldIn.setBlockToAir(pos);
/*     */       
/* 154 */       if (!worldIn.isRemote)
/*     */       {
/* 156 */         dropBlockAsItem(worldIn, pos, state, 0);
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Item getItemDropped(IBlockState state, Random rand, int fortune) {
/* 166 */     return (state.getValue((IProperty)PART) == EnumPartType.HEAD) ? null : Items.bed;
/*     */   }
/*     */ 
/*     */   
/*     */   private void setBedBounds() {
/* 171 */     setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.5625F, 1.0F);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static BlockPos getSafeExitLocation(World worldIn, BlockPos pos, int tries) {
/* 179 */     EnumFacing enumfacing = (EnumFacing)worldIn.getBlockState(pos).getValue((IProperty)FACING);
/* 180 */     int i = pos.getX();
/* 181 */     int j = pos.getY();
/* 182 */     int k = pos.getZ();
/*     */     
/* 184 */     for (int l = 0; l <= 1; l++) {
/*     */       
/* 186 */       int i1 = i - enumfacing.getFrontOffsetX() * l - 1;
/* 187 */       int j1 = k - enumfacing.getFrontOffsetZ() * l - 1;
/* 188 */       int k1 = i1 + 2;
/* 189 */       int l1 = j1 + 2;
/*     */       
/* 191 */       for (int i2 = i1; i2 <= k1; i2++) {
/*     */         
/* 193 */         for (int j2 = j1; j2 <= l1; j2++) {
/*     */           
/* 195 */           BlockPos blockpos = new BlockPos(i2, j, j2);
/*     */           
/* 197 */           if (hasRoomForPlayer(worldIn, blockpos)) {
/*     */             
/* 199 */             if (tries <= 0)
/*     */             {
/* 201 */               return blockpos;
/*     */             }
/*     */             
/* 204 */             tries--;
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 210 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   protected static boolean hasRoomForPlayer(World worldIn, BlockPos pos) {
/* 215 */     return (World.doesBlockHaveSolidTopSurface((IBlockAccess)worldIn, pos.down()) && !worldIn.getBlockState(pos).getBlock().getMaterial().isSolid() && !worldIn.getBlockState(pos.up()).getBlock().getMaterial().isSolid());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void dropBlockAsItemWithChance(World worldIn, BlockPos pos, IBlockState state, float chance, int fortune) {
/* 223 */     if (state.getValue((IProperty)PART) == EnumPartType.FOOT)
/*     */     {
/* 225 */       super.dropBlockAsItemWithChance(worldIn, pos, state, chance, 0);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public int getMobilityFlag() {
/* 231 */     return 1;
/*     */   }
/*     */ 
/*     */   
/*     */   public EnumWorldBlockLayer getBlockLayer() {
/* 236 */     return EnumWorldBlockLayer.CUTOUT;
/*     */   }
/*     */ 
/*     */   
/*     */   public Item getItem(World worldIn, BlockPos pos) {
/* 241 */     return Items.bed;
/*     */   }
/*     */ 
/*     */   
/*     */   public void onBlockHarvested(World worldIn, BlockPos pos, IBlockState state, EntityPlayer player) {
/* 246 */     if (player.capabilities.isCreativeMode && state.getValue((IProperty)PART) == EnumPartType.HEAD) {
/*     */       
/* 248 */       BlockPos blockpos = pos.offset(((EnumFacing)state.getValue((IProperty)FACING)).getOpposite());
/*     */       
/* 250 */       if (worldIn.getBlockState(blockpos).getBlock() == this)
/*     */       {
/* 252 */         worldIn.setBlockToAir(blockpos);
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IBlockState getStateFromMeta(int meta) {
/* 262 */     EnumFacing enumfacing = EnumFacing.getHorizontal(meta);
/* 263 */     return ((meta & 0x8) > 0) ? getDefaultState().withProperty((IProperty)PART, EnumPartType.HEAD).withProperty((IProperty)FACING, (Comparable)enumfacing).withProperty((IProperty)OCCUPIED, Boolean.valueOf(((meta & 0x4) > 0))) : getDefaultState().withProperty((IProperty)PART, EnumPartType.FOOT).withProperty((IProperty)FACING, (Comparable)enumfacing);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IBlockState getActualState(IBlockState state, IBlockAccess worldIn, BlockPos pos) {
/* 272 */     if (state.getValue((IProperty)PART) == EnumPartType.FOOT) {
/*     */       
/* 274 */       IBlockState iblockstate = worldIn.getBlockState(pos.offset((EnumFacing)state.getValue((IProperty)FACING)));
/*     */       
/* 276 */       if (iblockstate.getBlock() == this)
/*     */       {
/* 278 */         state = state.withProperty((IProperty)OCCUPIED, iblockstate.getValue((IProperty)OCCUPIED));
/*     */       }
/*     */     } 
/*     */     
/* 282 */     return state;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getMetaFromState(IBlockState state) {
/* 290 */     int i = 0;
/* 291 */     i |= ((EnumFacing)state.getValue((IProperty)FACING)).getHorizontalIndex();
/*     */     
/* 293 */     if (state.getValue((IProperty)PART) == EnumPartType.HEAD) {
/*     */       
/* 295 */       i |= 0x8;
/*     */       
/* 297 */       if (((Boolean)state.getValue((IProperty)OCCUPIED)).booleanValue())
/*     */       {
/* 299 */         i |= 0x4;
/*     */       }
/*     */     } 
/*     */     
/* 303 */     return i;
/*     */   }
/*     */ 
/*     */   
/*     */   protected BlockState createBlockState() {
/* 308 */     return new BlockState(this, new IProperty[] { (IProperty)FACING, (IProperty)PART, (IProperty)OCCUPIED });
/*     */   }
/*     */   
/*     */   public enum EnumPartType
/*     */     implements IStringSerializable {
/* 313 */     HEAD("head"),
/* 314 */     FOOT("foot");
/*     */     
/*     */     private final String name;
/*     */ 
/*     */     
/*     */     EnumPartType(String name) {
/* 320 */       this.name = name;
/*     */     }
/*     */ 
/*     */     
/*     */     public String toString() {
/* 325 */       return this.name;
/*     */     }
/*     */ 
/*     */     
/*     */     public String getName() {
/* 330 */       return this.name;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\minecraft\block\BlockBed.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */