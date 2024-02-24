/*    */ package net.optifine;
/*    */ 
/*    */ import net.minecraft.src.Config;
/*    */ import net.minecraft.tileentity.TileEntity;
/*    */ import net.minecraft.util.BlockPos;
/*    */ import net.minecraft.world.biome.BiomeGenBase;
/*    */ import net.optifine.util.TileEntityUtils;
/*    */ 
/*    */ public class RandomTileEntity
/*    */   implements IRandomEntity
/*    */ {
/*    */   private TileEntity tileEntity;
/*    */   
/*    */   public int getId() {
/* 15 */     return Config.getRandom(this.tileEntity.getPos(), this.tileEntity.getBlockMetadata());
/*    */   }
/*    */ 
/*    */   
/*    */   public BlockPos getSpawnPosition() {
/* 20 */     return this.tileEntity.getPos();
/*    */   }
/*    */ 
/*    */   
/*    */   public String getName() {
/* 25 */     String s = TileEntityUtils.getTileEntityName(this.tileEntity);
/* 26 */     return s;
/*    */   }
/*    */ 
/*    */   
/*    */   public BiomeGenBase getSpawnBiome() {
/* 31 */     return this.tileEntity.getWorld().getBiomeGenForCoords(this.tileEntity.getPos());
/*    */   }
/*    */ 
/*    */   
/*    */   public int getHealth() {
/* 36 */     return -1;
/*    */   }
/*    */ 
/*    */   
/*    */   public int getMaxHealth() {
/* 41 */     return -1;
/*    */   }
/*    */ 
/*    */   
/*    */   public TileEntity getTileEntity() {
/* 46 */     return this.tileEntity;
/*    */   }
/*    */ 
/*    */   
/*    */   public void setTileEntity(TileEntity tileEntity) {
/* 51 */     this.tileEntity = tileEntity;
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\optifine\RandomTileEntity.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */