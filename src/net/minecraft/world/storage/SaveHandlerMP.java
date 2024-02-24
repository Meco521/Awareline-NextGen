/*    */ package net.minecraft.world.storage;
/*    */ 
/*    */ import java.io.File;
/*    */ import net.minecraft.nbt.NBTTagCompound;
/*    */ import net.minecraft.world.WorldProvider;
/*    */ import net.minecraft.world.chunk.storage.IChunkLoader;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class SaveHandlerMP
/*    */   implements ISaveHandler
/*    */ {
/*    */   public WorldInfo loadWorldInfo() {
/* 16 */     return null;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void checkSessionLock() {}
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public IChunkLoader getChunkLoader(WorldProvider provider) {
/* 30 */     return null;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void saveWorldInfoWithPlayer(WorldInfo worldInformation, NBTTagCompound tagCompound) {}
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void saveWorldInfo(WorldInfo worldInformation) {}
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public IPlayerFileData getPlayerNBTManager() {
/* 49 */     return null;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void flush() {}
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public File getMapFileFromName(String mapName) {
/* 64 */     return null;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public String getWorldDirectoryName() {
/* 72 */     return "none";
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public File getWorldDirectory() {
/* 80 */     return null;
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\minecraft\world\storage\SaveHandlerMP.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */