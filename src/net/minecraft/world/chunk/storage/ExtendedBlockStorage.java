/*     */ package net.minecraft.world.chunk.storage;
/*     */ 
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.init.Blocks;
/*     */ import net.minecraft.world.chunk.NibbleArray;
/*     */ import net.optifine.reflect.Reflector;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ExtendedBlockStorage
/*     */ {
/*     */   private final int yBase;
/*     */   private int blockRefCount;
/*     */   private int tickRefCount;
/*     */   private char[] data;
/*     */   private NibbleArray blocklightArray;
/*     */   private NibbleArray skylightArray;
/*     */   
/*     */   public ExtendedBlockStorage(int y, boolean storeSkylight) {
/*  36 */     this.yBase = y;
/*  37 */     this.data = new char[4096];
/*  38 */     this.blocklightArray = new NibbleArray();
/*     */     
/*  40 */     if (storeSkylight)
/*     */     {
/*  42 */       this.skylightArray = new NibbleArray();
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public IBlockState get(int x, int y, int z) {
/*  48 */     IBlockState iblockstate = (IBlockState)Block.BLOCK_STATE_IDS.getByValue(this.data[y << 8 | z << 4 | x]);
/*  49 */     return (iblockstate != null) ? iblockstate : Blocks.air.getDefaultState();
/*     */   }
/*     */ 
/*     */   
/*     */   public void set(int x, int y, int z, IBlockState state) {
/*  54 */     if (Reflector.IExtendedBlockState.isInstance(state))
/*     */     {
/*  56 */       state = (IBlockState)Reflector.call(state, Reflector.IExtendedBlockState_getClean, new Object[0]);
/*     */     }
/*     */     
/*  59 */     IBlockState iblockstate = get(x, y, z);
/*  60 */     Block block = iblockstate.getBlock();
/*  61 */     Block block1 = state.getBlock();
/*     */     
/*  63 */     if (block != Blocks.air) {
/*     */       
/*  65 */       this.blockRefCount--;
/*     */       
/*  67 */       if (block.getTickRandomly())
/*     */       {
/*  69 */         this.tickRefCount--;
/*     */       }
/*     */     } 
/*     */     
/*  73 */     if (block1 != Blocks.air) {
/*     */       
/*  75 */       this.blockRefCount++;
/*     */       
/*  77 */       if (block1.getTickRandomly())
/*     */       {
/*  79 */         this.tickRefCount++;
/*     */       }
/*     */     } 
/*     */     
/*  83 */     this.data[y << 8 | z << 4 | x] = (char)Block.BLOCK_STATE_IDS.get(state);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Block getBlockByExtId(int x, int y, int z) {
/*  92 */     return get(x, y, z).getBlock();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getExtBlockMetadata(int x, int y, int z) {
/* 100 */     IBlockState iblockstate = get(x, y, z);
/* 101 */     return iblockstate.getBlock().getMetaFromState(iblockstate);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isEmpty() {
/* 109 */     return (this.blockRefCount == 0);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean getNeedsRandomTick() {
/* 118 */     return (this.tickRefCount > 0);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getYLocation() {
/* 126 */     return this.yBase;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setExtSkylightValue(int x, int y, int z, int value) {
/* 134 */     this.skylightArray.set(x, y, z, value);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getExtSkylightValue(int x, int y, int z) {
/* 142 */     return this.skylightArray.get(x, y, z);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setExtBlocklightValue(int x, int y, int z, int value) {
/* 150 */     this.blocklightArray.set(x, y, z, value);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getExtBlocklightValue(int x, int y, int z) {
/* 158 */     return this.blocklightArray.get(x, y, z);
/*     */   }
/*     */ 
/*     */   
/*     */   public void removeInvalidBlocks() {
/* 163 */     IBlockState iblockstate = Blocks.air.getDefaultState();
/* 164 */     int i = 0;
/* 165 */     int j = 0;
/*     */     
/* 167 */     for (int k = 0; k < 16; k++) {
/*     */       
/* 169 */       for (int l = 0; l < 16; l++) {
/*     */         
/* 171 */         for (int i1 = 0; i1 < 16; i1++) {
/*     */           
/* 173 */           Block block = getBlockByExtId(i1, k, l);
/*     */           
/* 175 */           if (block != Blocks.air) {
/*     */             
/* 177 */             i++;
/*     */             
/* 179 */             if (block.getTickRandomly())
/*     */             {
/* 181 */               j++;
/*     */             }
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 188 */     this.blockRefCount = i;
/* 189 */     this.tickRefCount = j;
/*     */   }
/*     */ 
/*     */   
/*     */   public char[] getData() {
/* 194 */     return this.data;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setData(char[] dataArray) {
/* 199 */     this.data = dataArray;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public NibbleArray getBlocklightArray() {
/* 207 */     return this.blocklightArray;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public NibbleArray getSkylightArray() {
/* 215 */     return this.skylightArray;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setBlocklightArray(NibbleArray newBlocklightArray) {
/* 223 */     this.blocklightArray = newBlocklightArray;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setSkylightArray(NibbleArray newSkylightArray) {
/* 231 */     this.skylightArray = newSkylightArray;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getBlockRefCount() {
/* 236 */     return this.blockRefCount;
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\minecraft\world\chunk\storage\ExtendedBlockStorage.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */