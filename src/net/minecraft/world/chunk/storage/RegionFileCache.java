/*    */ package net.minecraft.world.chunk.storage;
/*    */ 
/*    */ import com.google.common.collect.Maps;
/*    */ import java.io.DataInputStream;
/*    */ import java.io.DataOutputStream;
/*    */ import java.io.File;
/*    */ import java.io.IOException;
/*    */ import java.util.Map;
/*    */ 
/*    */ 
/*    */ public class RegionFileCache
/*    */ {
/* 13 */   private static final Map<File, RegionFile> regionsByFilename = Maps.newHashMap();
/*    */ 
/*    */   
/*    */   public static synchronized RegionFile createOrLoadRegionFile(File worldDir, int chunkX, int chunkZ) {
/* 17 */     File file1 = new File(worldDir, "region");
/* 18 */     File file2 = new File(file1, "r." + (chunkX >> 5) + "." + (chunkZ >> 5) + ".mca");
/* 19 */     RegionFile regionfile = regionsByFilename.get(file2);
/*    */     
/* 21 */     if (regionfile != null)
/*    */     {
/* 23 */       return regionfile;
/*    */     }
/*    */ 
/*    */     
/* 27 */     if (!file1.exists())
/*    */     {
/* 29 */       file1.mkdirs();
/*    */     }
/*    */     
/* 32 */     if (regionsByFilename.size() >= 256)
/*    */     {
/* 34 */       clearRegionFileReferences();
/*    */     }
/*    */     
/* 37 */     RegionFile regionfile1 = new RegionFile(file2);
/* 38 */     regionsByFilename.put(file2, regionfile1);
/* 39 */     return regionfile1;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static synchronized void clearRegionFileReferences() {
/* 48 */     for (RegionFile regionfile : regionsByFilename.values()) {
/*    */ 
/*    */       
/*    */       try {
/* 52 */         if (regionfile != null)
/*    */         {
/* 54 */           regionfile.close();
/*    */         }
/*    */       }
/* 57 */       catch (IOException ioexception) {
/*    */         
/* 59 */         ioexception.printStackTrace();
/*    */       } 
/*    */     } 
/*    */     
/* 63 */     regionsByFilename.clear();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static DataInputStream getChunkInputStream(File worldDir, int chunkX, int chunkZ) {
/* 71 */     RegionFile regionfile = createOrLoadRegionFile(worldDir, chunkX, chunkZ);
/* 72 */     return regionfile.getChunkDataInputStream(chunkX & 0x1F, chunkZ & 0x1F);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static DataOutputStream getChunkOutputStream(File worldDir, int chunkX, int chunkZ) {
/* 80 */     RegionFile regionfile = createOrLoadRegionFile(worldDir, chunkX, chunkZ);
/* 81 */     return regionfile.getChunkDataOutputStream(chunkX & 0x1F, chunkZ & 0x1F);
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\minecraft\world\chunk\storage\RegionFileCache.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */