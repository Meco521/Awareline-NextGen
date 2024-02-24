/*    */ package net.minecraft.world.gen.structure;
/*    */ 
/*    */ import com.google.common.collect.Lists;
/*    */ import java.util.List;
/*    */ import java.util.Random;
/*    */ import net.minecraft.entity.monster.EntityBlaze;
/*    */ import net.minecraft.entity.monster.EntityMagmaCube;
/*    */ import net.minecraft.entity.monster.EntityPigZombie;
/*    */ import net.minecraft.entity.monster.EntitySkeleton;
/*    */ import net.minecraft.world.World;
/*    */ import net.minecraft.world.biome.BiomeGenBase;
/*    */ 
/*    */ public class MapGenNetherBridge
/*    */   extends MapGenStructure
/*    */ {
/* 16 */   private final List<BiomeGenBase.SpawnListEntry> spawnList = Lists.newArrayList();
/*    */ 
/*    */   
/*    */   public MapGenNetherBridge() {
/* 20 */     this.spawnList.add(new BiomeGenBase.SpawnListEntry(EntityBlaze.class, 10, 2, 3));
/* 21 */     this.spawnList.add(new BiomeGenBase.SpawnListEntry(EntityPigZombie.class, 5, 4, 4));
/* 22 */     this.spawnList.add(new BiomeGenBase.SpawnListEntry(EntitySkeleton.class, 10, 4, 4));
/* 23 */     this.spawnList.add(new BiomeGenBase.SpawnListEntry(EntityMagmaCube.class, 3, 4, 4));
/*    */   }
/*    */ 
/*    */   
/*    */   public String getStructureName() {
/* 28 */     return "Fortress";
/*    */   }
/*    */ 
/*    */   
/*    */   public List<BiomeGenBase.SpawnListEntry> getSpawnList() {
/* 33 */     return this.spawnList;
/*    */   }
/*    */ 
/*    */   
/*    */   protected boolean canSpawnStructureAtCoords(int chunkX, int chunkZ) {
/* 38 */     int i = chunkX >> 4;
/* 39 */     int j = chunkZ >> 4;
/* 40 */     this.rand.setSeed((i ^ j << 4) ^ this.worldObj.getSeed());
/* 41 */     this.rand.nextInt();
/* 42 */     return (this.rand.nextInt(3) != 0) ? false : ((chunkX != (i << 4) + 4 + this.rand.nextInt(8)) ? false : ((chunkZ == (j << 4) + 4 + this.rand.nextInt(8))));
/*    */   }
/*    */ 
/*    */   
/*    */   protected StructureStart getStructureStart(int chunkX, int chunkZ) {
/* 47 */     return new Start(this.worldObj, this.rand, chunkX, chunkZ);
/*    */   }
/*    */ 
/*    */   
/*    */   public static class Start
/*    */     extends StructureStart
/*    */   {
/*    */     public Start() {}
/*    */ 
/*    */     
/*    */     public Start(World worldIn, Random p_i2040_2_, int p_i2040_3_, int p_i2040_4_) {
/* 58 */       super(p_i2040_3_, p_i2040_4_);
/* 59 */       StructureNetherBridgePieces.Start structurenetherbridgepieces$start = new StructureNetherBridgePieces.Start(p_i2040_2_, (p_i2040_3_ << 4) + 2, (p_i2040_4_ << 4) + 2);
/* 60 */       this.components.add(structurenetherbridgepieces$start);
/* 61 */       structurenetherbridgepieces$start.buildComponent(structurenetherbridgepieces$start, this.components, p_i2040_2_);
/* 62 */       List<StructureComponent> list = structurenetherbridgepieces$start.field_74967_d;
/*    */       
/* 64 */       while (!list.isEmpty()) {
/*    */         
/* 66 */         int i = p_i2040_2_.nextInt(list.size());
/* 67 */         StructureComponent structurecomponent = list.remove(i);
/* 68 */         structurecomponent.buildComponent(structurenetherbridgepieces$start, this.components, p_i2040_2_);
/*    */       } 
/*    */       
/* 71 */       updateBoundingBox();
/* 72 */       setRandomHeight(worldIn, p_i2040_2_, 48, 70);
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\minecraft\world\gen\structure\MapGenNetherBridge.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */