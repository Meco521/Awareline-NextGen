/*    */ package net.minecraft.world.chunk.storage;
/*    */ 
/*    */ import java.io.File;
/*    */ import net.minecraft.nbt.NBTTagCompound;
/*    */ import net.minecraft.world.WorldProvider;
/*    */ import net.minecraft.world.storage.SaveHandler;
/*    */ import net.minecraft.world.storage.ThreadedFileIOBase;
/*    */ import net.minecraft.world.storage.WorldInfo;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class AnvilSaveHandler
/*    */   extends SaveHandler
/*    */ {
/*    */   public AnvilSaveHandler(File savesDirectory, String directoryName, boolean storePlayerdata) {
/* 17 */     super(savesDirectory, directoryName, storePlayerdata);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public IChunkLoader getChunkLoader(WorldProvider provider) {
/* 25 */     File file1 = getWorldDirectory();
/*    */     
/* 27 */     if (provider instanceof net.minecraft.world.WorldProviderHell) {
/*    */       
/* 29 */       File file3 = new File(file1, "DIM-1");
/* 30 */       file3.mkdirs();
/* 31 */       return new AnvilChunkLoader(file3);
/*    */     } 
/* 33 */     if (provider instanceof net.minecraft.world.WorldProviderEnd) {
/*    */       
/* 35 */       File file2 = new File(file1, "DIM1");
/* 36 */       file2.mkdirs();
/* 37 */       return new AnvilChunkLoader(file2);
/*    */     } 
/*    */ 
/*    */     
/* 41 */     return new AnvilChunkLoader(file1);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void saveWorldInfoWithPlayer(WorldInfo worldInformation, NBTTagCompound tagCompound) {
/* 50 */     worldInformation.setSaveVersion(19133);
/* 51 */     super.saveWorldInfoWithPlayer(worldInformation, tagCompound);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void flush() {
/*    */     try {
/* 61 */       ThreadedFileIOBase.getThreadedIOInstance().waitForFinish();
/*    */     }
/* 63 */     catch (InterruptedException interruptedexception) {
/*    */       
/* 65 */       interruptedexception.printStackTrace();
/*    */     } 
/*    */     
/* 68 */     RegionFileCache.clearRegionFileReferences();
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\minecraft\world\chunk\storage\AnvilSaveHandler.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */