/*     */ package net.minecraft.world.gen.structure;
/*     */ 
/*     */ import java.util.Arrays;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Random;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.util.MathHelper;
/*     */ import net.minecraft.world.World;
/*     */ import net.minecraft.world.biome.BiomeGenBase;
/*     */ 
/*     */ 
/*     */ public class MapGenVillage
/*     */   extends MapGenStructure
/*     */ {
/*  16 */   public static final List<BiomeGenBase> villageSpawnBiomes = Arrays.asList(new BiomeGenBase[] { BiomeGenBase.plains, BiomeGenBase.desert, BiomeGenBase.savanna });
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private int terrainType;
/*     */ 
/*     */ 
/*     */   
/*  25 */   private int field_82665_g = 32;
/*  26 */   private final int field_82666_h = 8;
/*     */   
/*     */   public MapGenVillage() {}
/*     */   
/*     */   public MapGenVillage(Map<String, String> p_i2093_1_) {
/*  31 */     this();
/*     */     
/*  33 */     for (Map.Entry<String, String> entry : p_i2093_1_.entrySet()) {
/*     */       
/*  35 */       if (((String)entry.getKey()).equals("size")) {
/*     */         
/*  37 */         this.terrainType = MathHelper.parseIntWithDefaultAndMax(entry.getValue(), this.terrainType, 0); continue;
/*     */       } 
/*  39 */       if (((String)entry.getKey()).equals("distance"))
/*     */       {
/*  41 */         this.field_82665_g = MathHelper.parseIntWithDefaultAndMax(entry.getValue(), this.field_82665_g, this.field_82666_h + 1);
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public String getStructureName() {
/*  48 */     return "Village";
/*     */   }
/*     */ 
/*     */   
/*     */   protected boolean canSpawnStructureAtCoords(int chunkX, int chunkZ) {
/*  53 */     int i = chunkX;
/*  54 */     int j = chunkZ;
/*     */     
/*  56 */     if (chunkX < 0)
/*     */     {
/*  58 */       chunkX -= this.field_82665_g - 1;
/*     */     }
/*     */     
/*  61 */     if (chunkZ < 0)
/*     */     {
/*  63 */       chunkZ -= this.field_82665_g - 1;
/*     */     }
/*     */     
/*  66 */     int k = chunkX / this.field_82665_g;
/*  67 */     int l = chunkZ / this.field_82665_g;
/*  68 */     Random random = this.worldObj.setRandomSeed(k, l, 10387312);
/*  69 */     k *= this.field_82665_g;
/*  70 */     l *= this.field_82665_g;
/*  71 */     k += random.nextInt(this.field_82665_g - this.field_82666_h);
/*  72 */     l += random.nextInt(this.field_82665_g - this.field_82666_h);
/*     */     
/*  74 */     if (i == k && j == l) {
/*     */       
/*  76 */       boolean flag = this.worldObj.getWorldChunkManager().areBiomesViable((i << 4) + 8, (j << 4) + 8, 0, villageSpawnBiomes);
/*     */       
/*  78 */       if (flag)
/*     */       {
/*  80 */         return true;
/*     */       }
/*     */     } 
/*     */     
/*  84 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   protected StructureStart getStructureStart(int chunkX, int chunkZ) {
/*  89 */     return new Start(this.worldObj, this.rand, chunkX, chunkZ, this.terrainType);
/*     */   }
/*     */ 
/*     */   
/*     */   public static class Start
/*     */     extends StructureStart
/*     */   {
/*     */     private boolean hasMoreThanTwoComponents;
/*     */ 
/*     */     
/*     */     public Start() {}
/*     */     
/*     */     public Start(World worldIn, Random rand, int x, int z, int size) {
/* 102 */       super(x, z);
/* 103 */       List<StructureVillagePieces.PieceWeight> list = StructureVillagePieces.getStructureVillageWeightedPieceList(rand, size);
/* 104 */       StructureVillagePieces.Start structurevillagepieces$start = new StructureVillagePieces.Start(worldIn.getWorldChunkManager(), 0, rand, (x << 4) + 2, (z << 4) + 2, list, size);
/* 105 */       this.components.add(structurevillagepieces$start);
/* 106 */       structurevillagepieces$start.buildComponent(structurevillagepieces$start, this.components, rand);
/* 107 */       List<StructureComponent> list1 = structurevillagepieces$start.field_74930_j;
/* 108 */       List<StructureComponent> list2 = structurevillagepieces$start.field_74932_i;
/*     */       
/* 110 */       while (!list1.isEmpty() || !list2.isEmpty()) {
/*     */         
/* 112 */         if (list1.isEmpty()) {
/*     */           
/* 114 */           int i = rand.nextInt(list2.size());
/* 115 */           StructureComponent structurecomponent = list2.remove(i);
/* 116 */           structurecomponent.buildComponent(structurevillagepieces$start, this.components, rand);
/*     */           
/*     */           continue;
/*     */         } 
/* 120 */         int j = rand.nextInt(list1.size());
/* 121 */         StructureComponent structurecomponent2 = list1.remove(j);
/* 122 */         structurecomponent2.buildComponent(structurevillagepieces$start, this.components, rand);
/*     */       } 
/*     */ 
/*     */       
/* 126 */       updateBoundingBox();
/* 127 */       int k = 0;
/*     */       
/* 129 */       for (StructureComponent structurecomponent1 : this.components) {
/*     */         
/* 131 */         if (!(structurecomponent1 instanceof StructureVillagePieces.Road))
/*     */         {
/* 133 */           k++;
/*     */         }
/*     */       } 
/*     */       
/* 137 */       this.hasMoreThanTwoComponents = (k > 2);
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean isSizeableStructure() {
/* 142 */       return this.hasMoreThanTwoComponents;
/*     */     }
/*     */ 
/*     */     
/*     */     public void writeToNBT(NBTTagCompound tagCompound) {
/* 147 */       super.writeToNBT(tagCompound);
/* 148 */       tagCompound.setBoolean("Valid", this.hasMoreThanTwoComponents);
/*     */     }
/*     */ 
/*     */     
/*     */     public void readFromNBT(NBTTagCompound tagCompound) {
/* 153 */       super.readFromNBT(tagCompound);
/* 154 */       this.hasMoreThanTwoComponents = tagCompound.getBoolean("Valid");
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\minecraft\world\gen\structure\MapGenVillage.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */