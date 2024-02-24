/*     */ package net.minecraft.world.gen;
/*     */ 
/*     */ import com.google.common.collect.Lists;
/*     */ import java.util.Collection;
/*     */ import java.util.List;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.entity.EnumCreatureType;
/*     */ import net.minecraft.init.Blocks;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.util.IProgressUpdate;
/*     */ import net.minecraft.util.MathHelper;
/*     */ import net.minecraft.world.World;
/*     */ import net.minecraft.world.biome.BiomeGenBase;
/*     */ import net.minecraft.world.chunk.Chunk;
/*     */ import net.minecraft.world.chunk.ChunkPrimer;
/*     */ import net.minecraft.world.chunk.IChunkProvider;
/*     */ 
/*     */ public class ChunkProviderDebug
/*     */   implements IChunkProvider {
/*  21 */   private static final List<IBlockState> field_177464_a = Lists.newArrayList();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ChunkProviderDebug(World worldIn) {
/*  28 */     this.world = worldIn;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Chunk provideChunk(int x, int z) {
/*  37 */     ChunkPrimer chunkprimer = new ChunkPrimer();
/*     */     
/*  39 */     for (int i = 0; i < 16; i++) {
/*     */       
/*  41 */       for (int j = 0; j < 16; j++) {
/*     */         
/*  43 */         int k = (x << 4) + i;
/*  44 */         int l = (z << 4) + j;
/*  45 */         chunkprimer.setBlockState(i, 60, j, Blocks.barrier.getDefaultState());
/*  46 */         IBlockState iblockstate = func_177461_b(k, l);
/*     */         
/*  48 */         if (iblockstate != null)
/*     */         {
/*  50 */           chunkprimer.setBlockState(i, 70, j, iblockstate);
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/*  55 */     Chunk chunk = new Chunk(this.world, chunkprimer, x, z);
/*  56 */     chunk.generateSkylightMap();
/*  57 */     BiomeGenBase[] abiomegenbase = this.world.getWorldChunkManager().loadBlockGeneratorData((BiomeGenBase[])null, x << 4, z << 4, 16, 16);
/*  58 */     byte[] abyte = chunk.getBiomeArray();
/*     */     
/*  60 */     for (int i1 = 0; i1 < abyte.length; i1++)
/*     */     {
/*  62 */       abyte[i1] = (byte)(abiomegenbase[i1]).biomeID;
/*     */     }
/*     */     
/*  65 */     chunk.generateSkylightMap();
/*  66 */     return chunk;
/*     */   }
/*     */ 
/*     */   
/*     */   public static IBlockState func_177461_b(int p_177461_0_, int p_177461_1_) {
/*  71 */     IBlockState iblockstate = null;
/*     */     
/*  73 */     if (p_177461_0_ > 0 && p_177461_1_ > 0 && p_177461_0_ % 2 != 0 && p_177461_1_ % 2 != 0) {
/*     */       
/*  75 */       p_177461_0_ /= 2;
/*  76 */       p_177461_1_ /= 2;
/*     */       
/*  78 */       if (p_177461_0_ <= field_177462_b && p_177461_1_ <= field_181039_c) {
/*     */         
/*  80 */         int i = MathHelper.abs_int(p_177461_0_ * field_177462_b + p_177461_1_);
/*     */         
/*  82 */         if (i < field_177464_a.size())
/*     */         {
/*  84 */           iblockstate = field_177464_a.get(i);
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/*  89 */     return iblockstate;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean chunkExists(int x, int z) {
/*  97 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void populate(IChunkProvider chunkProvider, int x, int z) {}
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean populateChunk(IChunkProvider chunkProvider, Chunk chunkIn, int x, int z) {
/* 109 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean saveChunks(boolean saveAllChunks, IProgressUpdate progressCallback) {
/* 118 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void saveExtraData() {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean unloadQueuedChunks() {
/* 134 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean canSave() {
/* 142 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String makeString() {
/* 150 */     return "DebugLevelSource";
/*     */   }
/*     */ 
/*     */   
/*     */   public List<BiomeGenBase.SpawnListEntry> getPossibleCreatures(EnumCreatureType creatureType, BlockPos pos) {
/* 155 */     BiomeGenBase biomegenbase = this.world.getBiomeGenForCoords(pos);
/* 156 */     return biomegenbase.getSpawnableList(creatureType);
/*     */   }
/*     */ 
/*     */   
/*     */   public BlockPos getStrongholdGen(World worldIn, String structureName, BlockPos position) {
/* 161 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getLoadedChunkCount() {
/* 166 */     return 0;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void recreateStructures(Chunk chunkIn, int x, int z) {}
/*     */ 
/*     */   
/*     */   public Chunk provideChunk(BlockPos blockPosIn) {
/* 175 */     return provideChunk(blockPosIn.getX() >> 4, blockPosIn.getZ() >> 4);
/*     */   }
/*     */ 
/*     */   
/*     */   static {
/* 180 */     for (Block block : Block.blockRegistry)
/*     */     {
/* 182 */       field_177464_a.addAll((Collection<? extends IBlockState>)block.getBlockState().getValidStates()); } 
/*     */   }
/*     */   
/* 185 */   private static final int field_177462_b = MathHelper.ceiling_float_int(MathHelper.sqrt_float(field_177464_a.size()));
/* 186 */   private static final int field_181039_c = MathHelper.ceiling_float_int(field_177464_a.size() / field_177462_b);
/*     */   private final World world;
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\minecraft\world\gen\ChunkProviderDebug.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */