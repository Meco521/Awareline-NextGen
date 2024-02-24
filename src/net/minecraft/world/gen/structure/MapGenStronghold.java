/*     */ package net.minecraft.world.gen.structure;
/*     */ 
/*     */ import com.google.common.collect.Lists;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Random;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.util.MathHelper;
/*     */ import net.minecraft.world.ChunkCoordIntPair;
/*     */ import net.minecraft.world.World;
/*     */ import net.minecraft.world.biome.BiomeGenBase;
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
/*     */ public class MapGenStronghold
/*     */   extends MapGenStructure
/*     */ {
/*  30 */   private ChunkCoordIntPair[] structureCoords = new ChunkCoordIntPair[3];
/*  31 */   private double field_82671_h = 32.0D;
/*  32 */   private int field_82672_i = 3;
/*  33 */   private List<BiomeGenBase> field_151546_e = Lists.newArrayList();
/*     */   public MapGenStronghold() {
/*  35 */     for (BiomeGenBase biomegenbase : BiomeGenBase.getBiomeGenArray()) {
/*     */       
/*  37 */       if (biomegenbase != null && biomegenbase.minHeight > 0.0F)
/*     */       {
/*  39 */         this.field_151546_e.add(biomegenbase); } 
/*     */     } 
/*     */   }
/*     */   
/*     */   private boolean ranBiomeCheck;
/*     */   
/*     */   public MapGenStronghold(Map<String, String> p_i2068_1_) {
/*  46 */     this();
/*     */     
/*  48 */     for (Map.Entry<String, String> entry : p_i2068_1_.entrySet()) {
/*     */       
/*  50 */       if (((String)entry.getKey()).equals("distance")) {
/*     */         
/*  52 */         this.field_82671_h = MathHelper.parseDoubleWithDefaultAndMax(entry.getValue(), this.field_82671_h, 1.0D); continue;
/*     */       } 
/*  54 */       if (((String)entry.getKey()).equals("count")) {
/*     */         
/*  56 */         this.structureCoords = new ChunkCoordIntPair[MathHelper.parseIntWithDefaultAndMax((String)entry.getValue(), this.structureCoords.length, 1)]; continue;
/*     */       } 
/*  58 */       if (((String)entry.getKey()).equals("spread"))
/*     */       {
/*  60 */         this.field_82672_i = MathHelper.parseIntWithDefaultAndMax(entry.getValue(), this.field_82672_i, 1);
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public String getStructureName() {
/*  67 */     return "Stronghold";
/*     */   }
/*     */ 
/*     */   
/*     */   protected boolean canSpawnStructureAtCoords(int chunkX, int chunkZ) {
/*  72 */     if (!this.ranBiomeCheck) {
/*     */       
/*  74 */       Random random = new Random();
/*  75 */       random.setSeed(this.worldObj.getSeed());
/*  76 */       double d0 = random.nextDouble() * Math.PI * 2.0D;
/*  77 */       int i = 1;
/*     */       
/*  79 */       for (int j = 0; j < this.structureCoords.length; j++) {
/*     */         
/*  81 */         double d1 = (1.25D * i + random.nextDouble()) * this.field_82671_h * i;
/*  82 */         int k = (int)Math.round(Math.cos(d0) * d1);
/*  83 */         int l = (int)Math.round(Math.sin(d0) * d1);
/*  84 */         BlockPos blockpos = this.worldObj.getWorldChunkManager().findBiomePosition((k << 4) + 8, (l << 4) + 8, 112, this.field_151546_e, random);
/*     */         
/*  86 */         if (blockpos != null) {
/*     */           
/*  88 */           k = blockpos.getX() >> 4;
/*  89 */           l = blockpos.getZ() >> 4;
/*     */         } 
/*     */         
/*  92 */         this.structureCoords[j] = new ChunkCoordIntPair(k, l);
/*  93 */         d0 += 6.283185307179586D * i / this.field_82672_i;
/*     */         
/*  95 */         if (j == this.field_82672_i) {
/*     */           
/*  97 */           i += 2 + random.nextInt(5);
/*  98 */           this.field_82672_i += 1 + random.nextInt(2);
/*     */         } 
/*     */       } 
/*     */       
/* 102 */       this.ranBiomeCheck = true;
/*     */     } 
/*     */     
/* 105 */     for (ChunkCoordIntPair chunkcoordintpair : this.structureCoords) {
/*     */       
/* 107 */       if (chunkX == chunkcoordintpair.chunkXPos && chunkZ == chunkcoordintpair.chunkZPos)
/*     */       {
/* 109 */         return true;
/*     */       }
/*     */     } 
/*     */     
/* 113 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   protected List<BlockPos> getCoordList() {
/* 118 */     List<BlockPos> list = Lists.newArrayList();
/*     */     
/* 120 */     for (ChunkCoordIntPair chunkcoordintpair : this.structureCoords) {
/*     */       
/* 122 */       if (chunkcoordintpair != null)
/*     */       {
/* 124 */         list.add(chunkcoordintpair.getCenterBlock(64));
/*     */       }
/*     */     } 
/*     */     
/* 128 */     return list;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected StructureStart getStructureStart(int chunkX, int chunkZ) {
/*     */     Start mapgenstronghold$start;
/* 135 */     for (mapgenstronghold$start = new Start(this.worldObj, this.rand, chunkX, chunkZ); mapgenstronghold$start.getComponents().isEmpty() || ((StructureStrongholdPieces.Stairs2)mapgenstronghold$start.getComponents().get(0)).strongholdPortalRoom == null; mapgenstronghold$start = new Start(this.worldObj, this.rand, chunkX, chunkZ));
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 140 */     return mapgenstronghold$start;
/*     */   }
/*     */ 
/*     */   
/*     */   public static class Start
/*     */     extends StructureStart
/*     */   {
/*     */     public Start() {}
/*     */ 
/*     */     
/*     */     public Start(World worldIn, Random p_i2067_2_, int p_i2067_3_, int p_i2067_4_) {
/* 151 */       super(p_i2067_3_, p_i2067_4_);
/* 152 */       StructureStrongholdPieces.prepareStructurePieces();
/* 153 */       StructureStrongholdPieces.Stairs2 structurestrongholdpieces$stairs2 = new StructureStrongholdPieces.Stairs2(0, p_i2067_2_, (p_i2067_3_ << 4) + 2, (p_i2067_4_ << 4) + 2);
/* 154 */       this.components.add(structurestrongholdpieces$stairs2);
/* 155 */       structurestrongholdpieces$stairs2.buildComponent(structurestrongholdpieces$stairs2, this.components, p_i2067_2_);
/* 156 */       List<StructureComponent> list = structurestrongholdpieces$stairs2.field_75026_c;
/*     */       
/* 158 */       while (!list.isEmpty()) {
/*     */         
/* 160 */         int i = p_i2067_2_.nextInt(list.size());
/* 161 */         StructureComponent structurecomponent = list.remove(i);
/* 162 */         structurecomponent.buildComponent(structurestrongholdpieces$stairs2, this.components, p_i2067_2_);
/*     */       } 
/*     */       
/* 165 */       updateBoundingBox();
/* 166 */       markAvailableHeight(worldIn, p_i2067_2_, 10);
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\minecraft\world\gen\structure\MapGenStronghold.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */