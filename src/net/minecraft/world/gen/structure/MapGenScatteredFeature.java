/*     */ package net.minecraft.world.gen.structure;
/*     */ 
/*     */ import com.google.common.collect.Lists;
/*     */ import java.util.Arrays;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Random;
/*     */ import net.minecraft.entity.monster.EntityWitch;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.util.MathHelper;
/*     */ import net.minecraft.world.World;
/*     */ import net.minecraft.world.biome.BiomeGenBase;
/*     */ 
/*     */ 
/*     */ public class MapGenScatteredFeature
/*     */   extends MapGenStructure
/*     */ {
/*  18 */   private static final List<BiomeGenBase> biomelist = Arrays.asList(new BiomeGenBase[] { BiomeGenBase.desert, BiomeGenBase.desertHills, BiomeGenBase.jungle, BiomeGenBase.jungleHills, BiomeGenBase.swampland });
/*     */ 
/*     */   
/*     */   private final List<BiomeGenBase.SpawnListEntry> scatteredFeatureSpawnList;
/*     */   
/*     */   private int maxDistanceBetweenScatteredFeatures;
/*     */   
/*     */   private final int minDistanceBetweenScatteredFeatures;
/*     */ 
/*     */   
/*     */   public MapGenScatteredFeature() {
/*  29 */     this.scatteredFeatureSpawnList = Lists.newArrayList();
/*  30 */     this.maxDistanceBetweenScatteredFeatures = 32;
/*  31 */     this.minDistanceBetweenScatteredFeatures = 8;
/*  32 */     this.scatteredFeatureSpawnList.add(new BiomeGenBase.SpawnListEntry(EntityWitch.class, 1, 1, 1));
/*     */   }
/*     */ 
/*     */   
/*     */   public MapGenScatteredFeature(Map<String, String> p_i2061_1_) {
/*  37 */     this();
/*     */     
/*  39 */     for (Map.Entry<String, String> entry : p_i2061_1_.entrySet()) {
/*     */       
/*  41 */       if (((String)entry.getKey()).equals("distance"))
/*     */       {
/*  43 */         this.maxDistanceBetweenScatteredFeatures = MathHelper.parseIntWithDefaultAndMax(entry.getValue(), this.maxDistanceBetweenScatteredFeatures, this.minDistanceBetweenScatteredFeatures + 1);
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public String getStructureName() {
/*  50 */     return "Temple";
/*     */   }
/*     */ 
/*     */   
/*     */   protected boolean canSpawnStructureAtCoords(int chunkX, int chunkZ) {
/*  55 */     int i = chunkX;
/*  56 */     int j = chunkZ;
/*     */     
/*  58 */     if (chunkX < 0)
/*     */     {
/*  60 */       chunkX -= this.maxDistanceBetweenScatteredFeatures - 1;
/*     */     }
/*     */     
/*  63 */     if (chunkZ < 0)
/*     */     {
/*  65 */       chunkZ -= this.maxDistanceBetweenScatteredFeatures - 1;
/*     */     }
/*     */     
/*  68 */     int k = chunkX / this.maxDistanceBetweenScatteredFeatures;
/*  69 */     int l = chunkZ / this.maxDistanceBetweenScatteredFeatures;
/*  70 */     Random random = this.worldObj.setRandomSeed(k, l, 14357617);
/*  71 */     k *= this.maxDistanceBetweenScatteredFeatures;
/*  72 */     l *= this.maxDistanceBetweenScatteredFeatures;
/*  73 */     k += random.nextInt(this.maxDistanceBetweenScatteredFeatures - this.minDistanceBetweenScatteredFeatures);
/*  74 */     l += random.nextInt(this.maxDistanceBetweenScatteredFeatures - this.minDistanceBetweenScatteredFeatures);
/*     */     
/*  76 */     if (i == k && j == l) {
/*     */       
/*  78 */       BiomeGenBase biomegenbase = this.worldObj.getWorldChunkManager().getBiomeGenerator(new BlockPos((i << 4) + 8, 0, (j << 4) + 8));
/*     */       
/*  80 */       if (biomegenbase == null)
/*     */       {
/*  82 */         return false;
/*     */       }
/*     */       
/*  85 */       for (BiomeGenBase biomegenbase1 : biomelist) {
/*     */         
/*  87 */         if (biomegenbase == biomegenbase1)
/*     */         {
/*  89 */           return true;
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/*  94 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   protected StructureStart getStructureStart(int chunkX, int chunkZ) {
/*  99 */     return new Start(this.worldObj, this.rand, chunkX, chunkZ);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean func_175798_a(BlockPos p_175798_1_) {
/* 104 */     StructureStart structurestart = func_175797_c(p_175798_1_);
/*     */     
/* 106 */     if (structurestart != null && structurestart instanceof Start && !structurestart.components.isEmpty()) {
/*     */       
/* 108 */       StructureComponent structurecomponent = structurestart.components.getFirst();
/* 109 */       return structurecomponent instanceof ComponentScatteredFeaturePieces.SwampHut;
/*     */     } 
/*     */ 
/*     */     
/* 113 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public List<BiomeGenBase.SpawnListEntry> getScatteredFeatureSpawnList() {
/* 119 */     return this.scatteredFeatureSpawnList;
/*     */   }
/*     */ 
/*     */   
/*     */   public static class Start
/*     */     extends StructureStart
/*     */   {
/*     */     public Start() {}
/*     */ 
/*     */     
/*     */     public Start(World worldIn, Random p_i2060_2_, int p_i2060_3_, int p_i2060_4_) {
/* 130 */       super(p_i2060_3_, p_i2060_4_);
/* 131 */       BiomeGenBase biomegenbase = worldIn.getBiomeGenForCoords(new BlockPos((p_i2060_3_ << 4) + 8, 0, (p_i2060_4_ << 4) + 8));
/*     */       
/* 133 */       if (biomegenbase != BiomeGenBase.jungle && biomegenbase != BiomeGenBase.jungleHills) {
/*     */         
/* 135 */         if (biomegenbase == BiomeGenBase.swampland)
/*     */         {
/* 137 */           ComponentScatteredFeaturePieces.SwampHut componentscatteredfeaturepieces$swamphut = new ComponentScatteredFeaturePieces.SwampHut(p_i2060_2_, p_i2060_3_ << 4, p_i2060_4_ << 4);
/* 138 */           this.components.add(componentscatteredfeaturepieces$swamphut);
/*     */         }
/* 140 */         else if (biomegenbase == BiomeGenBase.desert || biomegenbase == BiomeGenBase.desertHills)
/*     */         {
/* 142 */           ComponentScatteredFeaturePieces.DesertPyramid componentscatteredfeaturepieces$desertpyramid = new ComponentScatteredFeaturePieces.DesertPyramid(p_i2060_2_, p_i2060_3_ << 4, p_i2060_4_ << 4);
/* 143 */           this.components.add(componentscatteredfeaturepieces$desertpyramid);
/*     */         }
/*     */       
/*     */       } else {
/*     */         
/* 148 */         ComponentScatteredFeaturePieces.JunglePyramid componentscatteredfeaturepieces$junglepyramid = new ComponentScatteredFeaturePieces.JunglePyramid(p_i2060_2_, p_i2060_3_ << 4, p_i2060_4_ << 4);
/* 149 */         this.components.add(componentscatteredfeaturepieces$junglepyramid);
/*     */       } 
/*     */       
/* 152 */       updateBoundingBox();
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\minecraft\world\gen\structure\MapGenScatteredFeature.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */