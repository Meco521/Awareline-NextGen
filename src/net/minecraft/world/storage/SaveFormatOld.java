/*     */ package net.minecraft.world.storage;
/*     */ 
/*     */ import com.google.common.collect.Lists;
/*     */ import java.io.File;
/*     */ import java.io.FileInputStream;
/*     */ import java.io.FileOutputStream;
/*     */ import java.util.List;
/*     */ import net.minecraft.client.AnvilConverterException;
/*     */ import net.minecraft.nbt.CompressedStreamTools;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.util.IProgressUpdate;
/*     */ import org.apache.logging.log4j.LogManager;
/*     */ import org.apache.logging.log4j.Logger;
/*     */ 
/*     */ public class SaveFormatOld
/*     */   implements ISaveFormat
/*     */ {
/*  18 */   private static final Logger logger = LogManager.getLogger();
/*     */ 
/*     */ 
/*     */   
/*     */   protected final File savesDirectory;
/*     */ 
/*     */ 
/*     */   
/*     */   public SaveFormatOld(File savesDirectoryIn) {
/*  27 */     if (!savesDirectoryIn.exists())
/*     */     {
/*  29 */       savesDirectoryIn.mkdirs();
/*     */     }
/*     */     
/*  32 */     this.savesDirectory = savesDirectoryIn;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getName() {
/*  40 */     return "Old Format";
/*     */   }
/*     */ 
/*     */   
/*     */   public List<SaveFormatComparator> getSaveList() throws AnvilConverterException {
/*  45 */     List<SaveFormatComparator> list = Lists.newArrayList();
/*     */     
/*  47 */     for (int i = 0; i < 5; i++) {
/*     */       
/*  49 */       String s = "World" + (i + 1);
/*  50 */       WorldInfo worldinfo = getWorldInfo(s);
/*     */       
/*  52 */       if (worldinfo != null)
/*     */       {
/*  54 */         list.add(new SaveFormatComparator(s, "", worldinfo.getLastTimePlayed(), worldinfo.getSizeOnDisk(), worldinfo.getGameType(), false, worldinfo.isHardcoreModeEnabled(), worldinfo.areCommandsAllowed()));
/*     */       }
/*     */     } 
/*     */     
/*  58 */     return list;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void flushCache() {}
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public WorldInfo getWorldInfo(String saveName) {
/*  70 */     File file1 = new File(this.savesDirectory, saveName);
/*     */     
/*  72 */     if (!file1.exists())
/*     */     {
/*  74 */       return null;
/*     */     }
/*     */ 
/*     */     
/*  78 */     File file2 = new File(file1, "level.dat");
/*     */     
/*  80 */     if (file2.exists()) {
/*     */       
/*     */       try {
/*     */         
/*  84 */         NBTTagCompound nbttagcompound2 = CompressedStreamTools.readCompressed(new FileInputStream(file2));
/*  85 */         NBTTagCompound nbttagcompound3 = nbttagcompound2.getCompoundTag("Data");
/*  86 */         return new WorldInfo(nbttagcompound3);
/*     */       }
/*  88 */       catch (Exception exception1) {
/*     */         
/*  90 */         logger.error("Exception reading " + file2, exception1);
/*     */       } 
/*     */     }
/*     */     
/*  94 */     file2 = new File(file1, "level.dat_old");
/*     */     
/*  96 */     if (file2.exists()) {
/*     */       
/*     */       try {
/*     */         
/* 100 */         NBTTagCompound nbttagcompound = CompressedStreamTools.readCompressed(new FileInputStream(file2));
/* 101 */         NBTTagCompound nbttagcompound1 = nbttagcompound.getCompoundTag("Data");
/* 102 */         return new WorldInfo(nbttagcompound1);
/*     */       }
/* 104 */       catch (Exception exception) {
/*     */         
/* 106 */         logger.error("Exception reading " + file2, exception);
/*     */       } 
/*     */     }
/*     */     
/* 110 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void renameWorld(String dirName, String newName) {
/* 120 */     File file1 = new File(this.savesDirectory, dirName);
/*     */     
/* 122 */     if (file1.exists()) {
/*     */       
/* 124 */       File file2 = new File(file1, "level.dat");
/*     */       
/* 126 */       if (file2.exists()) {
/*     */         
/*     */         try {
/*     */           
/* 130 */           NBTTagCompound nbttagcompound = CompressedStreamTools.readCompressed(new FileInputStream(file2));
/* 131 */           NBTTagCompound nbttagcompound1 = nbttagcompound.getCompoundTag("Data");
/* 132 */           nbttagcompound1.setString("LevelName", newName);
/* 133 */           CompressedStreamTools.writeCompressed(nbttagcompound, new FileOutputStream(file2));
/*     */         }
/* 135 */         catch (Exception exception) {
/*     */           
/* 137 */           exception.printStackTrace();
/*     */         } 
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isNewLevelIdAcceptable(String saveName) {
/* 145 */     File file1 = new File(this.savesDirectory, saveName);
/*     */     
/* 147 */     if (file1.exists())
/*     */     {
/* 149 */       return false;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     try {
/* 155 */       file1.mkdir();
/* 156 */       file1.delete();
/* 157 */       return true;
/*     */     }
/* 159 */     catch (Throwable throwable) {
/*     */       
/* 161 */       logger.warn("Couldn't make new level", throwable);
/* 162 */       return false;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean deleteWorldDirectory(String saveName) {
/* 175 */     File file1 = new File(this.savesDirectory, saveName);
/*     */     
/* 177 */     if (!file1.exists())
/*     */     {
/* 179 */       return true;
/*     */     }
/*     */ 
/*     */     
/* 183 */     logger.info("Deleting level " + saveName);
/*     */     
/* 185 */     for (int i = 1; i <= 5; i++) {
/*     */       
/* 187 */       logger.info("Attempt " + i + "...");
/*     */       
/* 189 */       if (deleteFiles(file1.listFiles())) {
/*     */         break;
/*     */       }
/*     */ 
/*     */       
/* 194 */       logger.warn("Unsuccessful in deleting contents.");
/*     */       
/* 196 */       if (i < 5) {
/*     */         
/*     */         try {
/*     */           
/* 200 */           Thread.sleep(500L);
/*     */         }
/* 202 */         catch (InterruptedException interruptedException) {}
/*     */       }
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 209 */     return file1.delete();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected static boolean deleteFiles(File[] files) {
/* 219 */     for (int i = 0; i < files.length; i++) {
/*     */       
/* 221 */       File file1 = files[i];
/* 222 */       logger.debug("Deleting " + file1);
/*     */       
/* 224 */       if (file1.isDirectory() && !deleteFiles(file1.listFiles())) {
/*     */         
/* 226 */         logger.warn("Couldn't delete directory " + file1);
/* 227 */         return false;
/*     */       } 
/*     */       
/* 230 */       if (!file1.delete()) {
/*     */         
/* 232 */         logger.warn("Couldn't delete file " + file1);
/* 233 */         return false;
/*     */       } 
/*     */     } 
/*     */     
/* 237 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ISaveHandler getSaveLoader(String saveName, boolean storePlayerdata) {
/* 245 */     return new SaveHandler(this.savesDirectory, saveName, storePlayerdata);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isConvertible(String saveName) {
/* 250 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isOldMapFormat(String saveName) {
/* 258 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean convertMapFormat(String filename, IProgressUpdate progressCallback) {
/* 266 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean canLoadWorld(String saveName) {
/* 276 */     File file1 = new File(this.savesDirectory, saveName);
/* 277 */     return file1.isDirectory();
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\minecraft\world\storage\SaveFormatOld.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */