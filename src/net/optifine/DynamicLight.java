/*     */ package net.optifine;
/*     */ 
/*     */ import java.util.HashSet;
/*     */ import java.util.Set;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.client.multiplayer.WorldClient;
/*     */ import net.minecraft.client.renderer.RenderGlobal;
/*     */ import net.minecraft.client.renderer.chunk.CompiledChunk;
/*     */ import net.minecraft.client.renderer.chunk.RenderChunk;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.init.Blocks;
/*     */ import net.minecraft.src.Config;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ import net.minecraft.util.MathHelper;
/*     */ 
/*     */ 
/*     */ public class DynamicLight
/*     */ {
/*  21 */   private Entity entity = null;
/*  22 */   private double offsetY = 0.0D;
/*  23 */   private double lastPosX = -2.147483648E9D;
/*  24 */   private double lastPosY = -2.147483648E9D;
/*  25 */   private double lastPosZ = -2.147483648E9D;
/*  26 */   private int lastLightLevel = 0;
/*     */   private boolean underwater = false;
/*  28 */   private long timeCheckMs = 0L;
/*  29 */   private Set<BlockPos> setLitChunkPos = new HashSet<>();
/*  30 */   private final BlockPos.MutableBlockPos blockPosMutable = new BlockPos.MutableBlockPos();
/*     */ 
/*     */   
/*     */   public DynamicLight(Entity entity) {
/*  34 */     this.entity = entity;
/*  35 */     this.offsetY = entity.getEyeHeight();
/*     */   }
/*     */ 
/*     */   
/*     */   public void update(RenderGlobal renderGlobal) {
/*  40 */     if (Config.isDynamicLightsFast()) {
/*     */       
/*  42 */       long i = System.currentTimeMillis();
/*     */       
/*  44 */       if (i < this.timeCheckMs + 500L) {
/*     */         return;
/*     */       }
/*     */ 
/*     */       
/*  49 */       this.timeCheckMs = i;
/*     */     } 
/*     */     
/*  52 */     double d6 = this.entity.posX - 0.5D;
/*  53 */     double d0 = this.entity.posY - 0.5D + this.offsetY;
/*  54 */     double d1 = this.entity.posZ - 0.5D;
/*  55 */     int j = DynamicLights.getLightLevel(this.entity);
/*  56 */     double d2 = d6 - this.lastPosX;
/*  57 */     double d3 = d0 - this.lastPosY;
/*  58 */     double d4 = d1 - this.lastPosZ;
/*  59 */     double d5 = 0.1D;
/*     */     
/*  61 */     if (Math.abs(d2) > d5 || Math.abs(d3) > d5 || Math.abs(d4) > d5 || this.lastLightLevel != j) {
/*     */       
/*  63 */       this.lastPosX = d6;
/*  64 */       this.lastPosY = d0;
/*  65 */       this.lastPosZ = d1;
/*  66 */       this.lastLightLevel = j;
/*  67 */       this.underwater = false;
/*  68 */       WorldClient worldClient = renderGlobal.getWorld();
/*     */       
/*  70 */       if (worldClient != null) {
/*     */         
/*  72 */         this.blockPosMutable.set(MathHelper.floor_double(d6), MathHelper.floor_double(d0), MathHelper.floor_double(d1));
/*  73 */         IBlockState iblockstate = worldClient.getBlockState((BlockPos)this.blockPosMutable);
/*  74 */         Block block = iblockstate.getBlock();
/*  75 */         this.underwater = (block == Blocks.water);
/*     */       } 
/*     */       
/*  78 */       Set<BlockPos> set = new HashSet<>();
/*     */       
/*  80 */       if (j > 0) {
/*     */         
/*  82 */         EnumFacing enumfacing2 = ((MathHelper.floor_double(d6) & 0xF) >= 8) ? EnumFacing.EAST : EnumFacing.WEST;
/*  83 */         EnumFacing enumfacing = ((MathHelper.floor_double(d0) & 0xF) >= 8) ? EnumFacing.UP : EnumFacing.DOWN;
/*  84 */         EnumFacing enumfacing1 = ((MathHelper.floor_double(d1) & 0xF) >= 8) ? EnumFacing.SOUTH : EnumFacing.NORTH;
/*  85 */         BlockPos blockpos = new BlockPos(d6, d0, d1);
/*  86 */         RenderChunk renderchunk = renderGlobal.getRenderChunk(blockpos);
/*  87 */         BlockPos blockpos1 = getChunkPos(renderchunk, blockpos, enumfacing2);
/*  88 */         RenderChunk renderchunk1 = renderGlobal.getRenderChunk(blockpos1);
/*  89 */         BlockPos blockpos2 = getChunkPos(renderchunk, blockpos, enumfacing1);
/*  90 */         RenderChunk renderchunk2 = renderGlobal.getRenderChunk(blockpos2);
/*  91 */         BlockPos blockpos3 = getChunkPos(renderchunk1, blockpos1, enumfacing1);
/*  92 */         RenderChunk renderchunk3 = renderGlobal.getRenderChunk(blockpos3);
/*  93 */         BlockPos blockpos4 = getChunkPos(renderchunk, blockpos, enumfacing);
/*  94 */         RenderChunk renderchunk4 = renderGlobal.getRenderChunk(blockpos4);
/*  95 */         BlockPos blockpos5 = getChunkPos(renderchunk4, blockpos4, enumfacing2);
/*  96 */         RenderChunk renderchunk5 = renderGlobal.getRenderChunk(blockpos5);
/*  97 */         BlockPos blockpos6 = getChunkPos(renderchunk4, blockpos4, enumfacing1);
/*  98 */         RenderChunk renderchunk6 = renderGlobal.getRenderChunk(blockpos6);
/*  99 */         BlockPos blockpos7 = getChunkPos(renderchunk5, blockpos5, enumfacing1);
/* 100 */         RenderChunk renderchunk7 = renderGlobal.getRenderChunk(blockpos7);
/* 101 */         updateChunkLight(renderchunk, this.setLitChunkPos, set);
/* 102 */         updateChunkLight(renderchunk1, this.setLitChunkPos, set);
/* 103 */         updateChunkLight(renderchunk2, this.setLitChunkPos, set);
/* 104 */         updateChunkLight(renderchunk3, this.setLitChunkPos, set);
/* 105 */         updateChunkLight(renderchunk4, this.setLitChunkPos, set);
/* 106 */         updateChunkLight(renderchunk5, this.setLitChunkPos, set);
/* 107 */         updateChunkLight(renderchunk6, this.setLitChunkPos, set);
/* 108 */         updateChunkLight(renderchunk7, this.setLitChunkPos, set);
/*     */       } 
/*     */       
/* 111 */       updateLitChunks(renderGlobal);
/* 112 */       this.setLitChunkPos = set;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private BlockPos getChunkPos(RenderChunk renderChunk, BlockPos pos, EnumFacing facing) {
/* 118 */     return (renderChunk != null) ? renderChunk.getBlockPosOffset16(facing) : pos.offset(facing, 16);
/*     */   }
/*     */ 
/*     */   
/*     */   private void updateChunkLight(RenderChunk renderChunk, Set<BlockPos> setPrevPos, Set<BlockPos> setNewPos) {
/* 123 */     if (renderChunk != null) {
/*     */       
/* 125 */       CompiledChunk compiledchunk = renderChunk.getCompiledChunk();
/*     */       
/* 127 */       if (compiledchunk != null && !compiledchunk.isEmpty())
/*     */       {
/* 129 */         renderChunk.setNeedsUpdate(true);
/*     */       }
/*     */       
/* 132 */       BlockPos blockpos = renderChunk.getPosition();
/*     */       
/* 134 */       if (setPrevPos != null)
/*     */       {
/* 136 */         setPrevPos.remove(blockpos);
/*     */       }
/*     */       
/* 139 */       if (setNewPos != null)
/*     */       {
/* 141 */         setNewPos.add(blockpos);
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void updateLitChunks(RenderGlobal renderGlobal) {
/* 148 */     for (BlockPos blockpos : this.setLitChunkPos) {
/*     */       
/* 150 */       RenderChunk renderchunk = renderGlobal.getRenderChunk(blockpos);
/* 151 */       updateChunkLight(renderchunk, (Set<BlockPos>)null, (Set<BlockPos>)null);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public Entity getEntity() {
/* 157 */     return this.entity;
/*     */   }
/*     */ 
/*     */   
/*     */   public double getLastPosX() {
/* 162 */     return this.lastPosX;
/*     */   }
/*     */ 
/*     */   
/*     */   public double getLastPosY() {
/* 167 */     return this.lastPosY;
/*     */   }
/*     */ 
/*     */   
/*     */   public double getLastPosZ() {
/* 172 */     return this.lastPosZ;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getLastLightLevel() {
/* 177 */     return this.lastLightLevel;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isUnderwater() {
/* 182 */     return this.underwater;
/*     */   }
/*     */ 
/*     */   
/*     */   public double getOffsetY() {
/* 187 */     return this.offsetY;
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/* 192 */     return "Entity: " + this.entity + ", offsetY: " + this.offsetY;
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\optifine\DynamicLight.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */