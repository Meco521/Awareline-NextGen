/*     */ package net.minecraft.world.gen.structure;
/*     */ 
/*     */ import com.google.common.collect.Lists;
/*     */ import com.google.common.collect.Sets;
/*     */ import java.util.Arrays;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Random;
/*     */ import java.util.Set;
/*     */ import net.minecraft.entity.monster.EntityGuardian;
/*     */ import net.minecraft.nbt.NBTBase;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.nbt.NBTTagList;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ import net.minecraft.util.MathHelper;
/*     */ import net.minecraft.world.ChunkCoordIntPair;
/*     */ import net.minecraft.world.World;
/*     */ import net.minecraft.world.biome.BiomeGenBase;
/*     */ 
/*     */ public class StructureOceanMonument extends MapGenStructure {
/*  22 */   public static final List<BiomeGenBase> field_175802_d = Arrays.asList(new BiomeGenBase[] { BiomeGenBase.ocean, BiomeGenBase.deepOcean, BiomeGenBase.river, BiomeGenBase.frozenOcean, BiomeGenBase.frozenRiver });
/*  23 */   private static final List<BiomeGenBase.SpawnListEntry> field_175803_h = Lists.newArrayList();
/*     */ 
/*     */ 
/*     */   
/*  27 */   private int field_175800_f = 32;
/*  28 */   private int field_175801_g = 5;
/*     */   
/*     */   public StructureOceanMonument() {}
/*     */   
/*     */   public StructureOceanMonument(Map<String, String> p_i45608_1_) {
/*  33 */     this();
/*     */     
/*  35 */     for (Map.Entry<String, String> entry : p_i45608_1_.entrySet()) {
/*     */       
/*  37 */       if (((String)entry.getKey()).equals("spacing")) {
/*     */         
/*  39 */         this.field_175800_f = MathHelper.parseIntWithDefaultAndMax(entry.getValue(), this.field_175800_f, 1); continue;
/*     */       } 
/*  41 */       if (((String)entry.getKey()).equals("separation"))
/*     */       {
/*  43 */         this.field_175801_g = MathHelper.parseIntWithDefaultAndMax(entry.getValue(), this.field_175801_g, 1);
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public String getStructureName() {
/*  50 */     return "Monument";
/*     */   }
/*     */ 
/*     */   
/*     */   protected boolean canSpawnStructureAtCoords(int chunkX, int chunkZ) {
/*  55 */     int i = chunkX;
/*  56 */     int j = chunkZ;
/*     */     
/*  58 */     if (chunkX < 0)
/*     */     {
/*  60 */       chunkX -= this.field_175800_f - 1;
/*     */     }
/*     */     
/*  63 */     if (chunkZ < 0)
/*     */     {
/*  65 */       chunkZ -= this.field_175800_f - 1;
/*     */     }
/*     */     
/*  68 */     int k = chunkX / this.field_175800_f;
/*  69 */     int l = chunkZ / this.field_175800_f;
/*  70 */     Random random = this.worldObj.setRandomSeed(k, l, 10387313);
/*  71 */     k *= this.field_175800_f;
/*  72 */     l *= this.field_175800_f;
/*  73 */     k += (random.nextInt(this.field_175800_f - this.field_175801_g) + random.nextInt(this.field_175800_f - this.field_175801_g)) / 2;
/*  74 */     l += (random.nextInt(this.field_175800_f - this.field_175801_g) + random.nextInt(this.field_175800_f - this.field_175801_g)) / 2;
/*     */     
/*  76 */     if (i == k && j == l) {
/*     */       
/*  78 */       if (this.worldObj.getWorldChunkManager().getBiomeGenerator(new BlockPos((i << 4) + 8, 64, (j << 4) + 8), (BiomeGenBase)null) != BiomeGenBase.deepOcean)
/*     */       {
/*  80 */         return false;
/*     */       }
/*     */       
/*  83 */       boolean flag = this.worldObj.getWorldChunkManager().areBiomesViable((i << 4) + 8, (j << 4) + 8, 29, field_175802_d);
/*     */       
/*  85 */       if (flag)
/*     */       {
/*  87 */         return true;
/*     */       }
/*     */     } 
/*     */     
/*  91 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   protected StructureStart getStructureStart(int chunkX, int chunkZ) {
/*  96 */     return new StartMonument(this.worldObj, this.rand, chunkX, chunkZ);
/*     */   }
/*     */ 
/*     */   
/*     */   public List<BiomeGenBase.SpawnListEntry> getScatteredFeatureSpawnList() {
/* 101 */     return field_175803_h;
/*     */   }
/*     */ 
/*     */   
/*     */   static {
/* 106 */     field_175803_h.add(new BiomeGenBase.SpawnListEntry(EntityGuardian.class, 1, 2, 4));
/*     */   }
/*     */   
/*     */   public static class StartMonument
/*     */     extends StructureStart {
/* 111 */     private final Set<ChunkCoordIntPair> field_175791_c = Sets.newHashSet();
/*     */     
/*     */     private boolean field_175790_d;
/*     */ 
/*     */     
/*     */     public StartMonument() {}
/*     */ 
/*     */     
/*     */     public StartMonument(World worldIn, Random p_i45607_2_, int p_i45607_3_, int p_i45607_4_) {
/* 120 */       super(p_i45607_3_, p_i45607_4_);
/* 121 */       func_175789_b(worldIn, p_i45607_2_, p_i45607_3_, p_i45607_4_);
/*     */     }
/*     */ 
/*     */     
/*     */     private void func_175789_b(World worldIn, Random p_175789_2_, int p_175789_3_, int p_175789_4_) {
/* 126 */       p_175789_2_.setSeed(worldIn.getSeed());
/* 127 */       long i = p_175789_2_.nextLong();
/* 128 */       long j = p_175789_2_.nextLong();
/* 129 */       long k = p_175789_3_ * i;
/* 130 */       long l = p_175789_4_ * j;
/* 131 */       p_175789_2_.setSeed(k ^ l ^ worldIn.getSeed());
/* 132 */       int i1 = (p_175789_3_ << 4) + 8 - 29;
/* 133 */       int j1 = (p_175789_4_ << 4) + 8 - 29;
/* 134 */       EnumFacing enumfacing = EnumFacing.Plane.HORIZONTAL.random(p_175789_2_);
/* 135 */       this.components.add(new StructureOceanMonumentPieces.MonumentBuilding(p_175789_2_, i1, j1, enumfacing));
/* 136 */       updateBoundingBox();
/* 137 */       this.field_175790_d = true;
/*     */     }
/*     */ 
/*     */     
/*     */     public void generateStructure(World worldIn, Random rand, StructureBoundingBox structurebb) {
/* 142 */       if (!this.field_175790_d) {
/*     */         
/* 144 */         this.components.clear();
/* 145 */         func_175789_b(worldIn, rand, getChunkPosX(), getChunkPosZ());
/*     */       } 
/*     */       
/* 148 */       super.generateStructure(worldIn, rand, structurebb);
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean func_175788_a(ChunkCoordIntPair pair) {
/* 153 */       return this.field_175791_c.contains(pair) ? false : super.func_175788_a(pair);
/*     */     }
/*     */ 
/*     */     
/*     */     public void func_175787_b(ChunkCoordIntPair pair) {
/* 158 */       super.func_175787_b(pair);
/* 159 */       this.field_175791_c.add(pair);
/*     */     }
/*     */ 
/*     */     
/*     */     public void writeToNBT(NBTTagCompound tagCompound) {
/* 164 */       super.writeToNBT(tagCompound);
/* 165 */       NBTTagList nbttaglist = new NBTTagList();
/*     */       
/* 167 */       for (ChunkCoordIntPair chunkcoordintpair : this.field_175791_c) {
/*     */         
/* 169 */         NBTTagCompound nbttagcompound = new NBTTagCompound();
/* 170 */         nbttagcompound.setInteger("X", chunkcoordintpair.chunkXPos);
/* 171 */         nbttagcompound.setInteger("Z", chunkcoordintpair.chunkZPos);
/* 172 */         nbttaglist.appendTag((NBTBase)nbttagcompound);
/*     */       } 
/*     */       
/* 175 */       tagCompound.setTag("Processed", (NBTBase)nbttaglist);
/*     */     }
/*     */ 
/*     */     
/*     */     public void readFromNBT(NBTTagCompound tagCompound) {
/* 180 */       super.readFromNBT(tagCompound);
/*     */       
/* 182 */       if (tagCompound.hasKey("Processed", 9)) {
/*     */         
/* 184 */         NBTTagList nbttaglist = tagCompound.getTagList("Processed", 10);
/*     */         
/* 186 */         for (int i = 0; i < nbttaglist.tagCount(); i++) {
/*     */           
/* 188 */           NBTTagCompound nbttagcompound = nbttaglist.getCompoundTagAt(i);
/* 189 */           this.field_175791_c.add(new ChunkCoordIntPair(nbttagcompound.getInteger("X"), nbttagcompound.getInteger("Z")));
/*     */         } 
/*     */       } 
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\minecraft\world\gen\structure\StructureOceanMonument.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */