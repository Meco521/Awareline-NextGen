/*    */ package awareline.main.utility;
/*    */ 
/*    */ import java.util.Arrays;
/*    */ import java.util.List;
/*    */ import net.minecraft.entity.Entity;
/*    */ import net.minecraft.entity.EntityLivingBase;
/*    */ import net.minecraft.entity.player.EntityPlayer;
/*    */ import net.minecraft.tileentity.TileEntity;
/*    */ import net.minecraft.tileentity.TileEntityChest;
/*    */ import net.minecraft.tileentity.TileEntityEnderChest;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class WorldUtil
/*    */   implements Utils
/*    */ {
/*    */   public static List<EntityLivingBase> getLivingEntities() {
/* 19 */     return Arrays.asList((EntityLivingBase[])mc.theWorld.loadedEntityList
/* 20 */         .stream()
/* 21 */         .filter(entity -> entity instanceof EntityLivingBase)
/* 22 */         .filter(entity -> (entity != mc.thePlayer))
/* 23 */         .map(entity -> (EntityLivingBase)entity)
/* 24 */         .toArray(x$0 -> new EntityLivingBase[x$0]));
/*    */   }
/*    */ 
/*    */   
/*    */   public static List<Entity> getEntities() {
/* 29 */     return Arrays.asList((Entity[])mc.theWorld.loadedEntityList
/* 30 */         .stream()
/* 31 */         .filter(entity -> (entity != mc.thePlayer))
/* 32 */         .toArray(x$0 -> new Entity[x$0]));
/*    */   }
/*    */ 
/*    */   
/*    */   public static List<EntityPlayer> getLivingPlayers() {
/* 37 */     return Arrays.asList((EntityPlayer[])mc.theWorld.loadedEntityList
/* 38 */         .stream()
/* 39 */         .filter(entity -> entity instanceof EntityPlayer)
/* 40 */         .filter(entity -> (entity != mc.thePlayer))
/* 41 */         .map(entity -> (EntityPlayer)entity)
/* 42 */         .toArray(x$0 -> new EntityPlayer[x$0]));
/*    */   }
/*    */ 
/*    */   
/*    */   public static List<TileEntity> getTileEntities() {
/* 47 */     return mc.theWorld.loadedTileEntityList;
/*    */   }
/*    */   
/*    */   public static List<TileEntityChest> getChestTileEntities() {
/* 51 */     return Arrays.asList((TileEntityChest[])mc.theWorld.loadedTileEntityList
/* 52 */         .stream()
/* 53 */         .filter(entity -> entity instanceof TileEntityChest)
/* 54 */         .map(entity -> (TileEntityChest)entity)
/* 55 */         .toArray(x$0 -> new TileEntityChest[x$0]));
/*    */   }
/*    */ 
/*    */   
/*    */   public static List<TileEntityEnderChest> getEnderChestTileEntities() {
/* 60 */     return Arrays.asList((TileEntityEnderChest[])mc.theWorld.loadedTileEntityList
/* 61 */         .stream()
/* 62 */         .filter(entity -> entity instanceof TileEntityEnderChest)
/* 63 */         .map(entity -> (TileEntityEnderChest)entity)
/* 64 */         .toArray(x$0 -> new TileEntityEnderChest[x$0]));
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\awareline\mai\\utility\WorldUtil.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */