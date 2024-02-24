/*     */ package net.minecraft.world.chunk.storage;
/*     */ import com.google.common.collect.Lists;
/*     */ import java.io.DataInputStream;
/*     */ import java.io.DataOutputStream;
/*     */ import java.io.File;
/*     */ import java.io.FilenameFilter;
/*     */ import java.io.IOException;
/*     */ import java.util.Collection;
/*     */ import java.util.List;
/*     */ import net.minecraft.client.AnvilConverterException;
/*     */ import net.minecraft.nbt.CompressedStreamTools;
/*     */ import net.minecraft.nbt.NBTBase;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.util.IProgressUpdate;
/*     */ import net.minecraft.world.WorldType;
/*     */ import net.minecraft.world.biome.BiomeGenBase;
/*     */ import net.minecraft.world.biome.WorldChunkManager;
/*     */ import net.minecraft.world.biome.WorldChunkManagerHell;
/*     */ import net.minecraft.world.storage.ISaveHandler;
/*     */ import net.minecraft.world.storage.SaveFormatComparator;
/*     */ import net.minecraft.world.storage.SaveFormatOld;
/*     */ import net.minecraft.world.storage.WorldInfo;
/*     */ import org.apache.logging.log4j.LogManager;
/*     */ import org.apache.logging.log4j.Logger;
/*     */ 
/*     */ public class AnvilSaveConverter extends SaveFormatOld {
/*  27 */   private static final Logger logger = LogManager.getLogger();
/*     */ 
/*     */   
/*     */   public AnvilSaveConverter(File savesDirectoryIn) {
/*  31 */     super(savesDirectoryIn);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getName() {
/*  39 */     return "Anvil";
/*     */   }
/*     */ 
/*     */   
/*     */   public List<SaveFormatComparator> getSaveList() throws AnvilConverterException {
/*  44 */     if (this.savesDirectory != null && this.savesDirectory.exists() && this.savesDirectory.isDirectory()) {
/*     */       
/*  46 */       List<SaveFormatComparator> list = Lists.newArrayList();
/*  47 */       File[] afile = this.savesDirectory.listFiles();
/*     */       
/*  49 */       for (File file1 : afile) {
/*     */         
/*  51 */         if (file1.isDirectory()) {
/*     */           
/*  53 */           String s = file1.getName();
/*  54 */           WorldInfo worldinfo = getWorldInfo(s);
/*     */           
/*  56 */           if (worldinfo != null && (worldinfo.getSaveVersion() == 19132 || worldinfo.getSaveVersion() == 19133)) {
/*     */             
/*  58 */             boolean flag = (worldinfo.getSaveVersion() != getSaveVersion());
/*  59 */             String s1 = worldinfo.getWorldName();
/*     */             
/*  61 */             if (StringUtils.isEmpty(s1))
/*     */             {
/*  63 */               s1 = s;
/*     */             }
/*     */             
/*  66 */             long i = 0L;
/*  67 */             list.add(new SaveFormatComparator(s, s1, worldinfo.getLastTimePlayed(), i, worldinfo.getGameType(), flag, worldinfo.isHardcoreModeEnabled(), worldinfo.areCommandsAllowed()));
/*     */           } 
/*     */         } 
/*     */       } 
/*     */       
/*  72 */       return list;
/*     */     } 
/*     */ 
/*     */     
/*  76 */     throw new AnvilConverterException("Unable to read or access folder where game worlds are saved!");
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected int getSaveVersion() {
/*  82 */     return 19133;
/*     */   }
/*     */ 
/*     */   
/*     */   public void flushCache() {
/*  87 */     RegionFileCache.clearRegionFileReferences();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ISaveHandler getSaveLoader(String saveName, boolean storePlayerdata) {
/*  95 */     return (ISaveHandler)new AnvilSaveHandler(this.savesDirectory, saveName, storePlayerdata);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isConvertible(String saveName) {
/* 100 */     WorldInfo worldinfo = getWorldInfo(saveName);
/* 101 */     return (worldinfo != null && worldinfo.getSaveVersion() == 19132);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isOldMapFormat(String saveName) {
/* 109 */     WorldInfo worldinfo = getWorldInfo(saveName);
/* 110 */     return (worldinfo != null && worldinfo.getSaveVersion() != getSaveVersion());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean convertMapFormat(String filename, IProgressUpdate progressCallback) {
/* 118 */     progressCallback.setLoadingProgress(0);
/* 119 */     List<File> list = Lists.newArrayList();
/* 120 */     List<File> list1 = Lists.newArrayList();
/* 121 */     List<File> list2 = Lists.newArrayList();
/* 122 */     File file1 = new File(this.savesDirectory, filename);
/* 123 */     File file2 = new File(file1, "DIM-1");
/* 124 */     File file3 = new File(file1, "DIM1");
/* 125 */     logger.info("Scanning folders...");
/* 126 */     addRegionFilesToCollection(file1, list);
/*     */     
/* 128 */     if (file2.exists())
/*     */     {
/* 130 */       addRegionFilesToCollection(file2, list1);
/*     */     }
/*     */     
/* 133 */     if (file3.exists())
/*     */     {
/* 135 */       addRegionFilesToCollection(file3, list2);
/*     */     }
/*     */     
/* 138 */     int i = list.size() + list1.size() + list2.size();
/* 139 */     logger.info("Total conversion count is " + i);
/* 140 */     WorldInfo worldinfo = getWorldInfo(filename);
/* 141 */     WorldChunkManager worldchunkmanager = null;
/*     */     
/* 143 */     if (worldinfo.getTerrainType() == WorldType.FLAT) {
/*     */       
/* 145 */       WorldChunkManagerHell worldChunkManagerHell = new WorldChunkManagerHell(BiomeGenBase.plains, 0.5F);
/*     */     }
/*     */     else {
/*     */       
/* 149 */       worldchunkmanager = new WorldChunkManager(worldinfo.getSeed(), worldinfo.getTerrainType(), worldinfo.getGeneratorOptions());
/*     */     } 
/*     */     
/* 152 */     convertFile(new File(file1, "region"), list, worldchunkmanager, 0, i, progressCallback);
/* 153 */     convertFile(new File(file2, "region"), list1, (WorldChunkManager)new WorldChunkManagerHell(BiomeGenBase.hell, 0.0F), list.size(), i, progressCallback);
/* 154 */     convertFile(new File(file3, "region"), list2, (WorldChunkManager)new WorldChunkManagerHell(BiomeGenBase.sky, 0.0F), list.size() + list1.size(), i, progressCallback);
/* 155 */     worldinfo.setSaveVersion(19133);
/*     */     
/* 157 */     if (worldinfo.getTerrainType() == WorldType.DEFAULT_1_1)
/*     */     {
/* 159 */       worldinfo.setTerrainType(WorldType.DEFAULT);
/*     */     }
/*     */     
/* 162 */     createFile(filename);
/* 163 */     ISaveHandler isavehandler = getSaveLoader(filename, false);
/* 164 */     isavehandler.saveWorldInfo(worldinfo);
/* 165 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void createFile(String filename) {
/* 173 */     File file1 = new File(this.savesDirectory, filename);
/*     */     
/* 175 */     if (!file1.exists()) {
/*     */       
/* 177 */       logger.warn("Unable to create level.dat_mcr backup");
/*     */     }
/*     */     else {
/*     */       
/* 181 */       File file2 = new File(file1, "level.dat");
/*     */       
/* 183 */       if (!file2.exists()) {
/*     */         
/* 185 */         logger.warn("Unable to create level.dat_mcr backup");
/*     */       }
/*     */       else {
/*     */         
/* 189 */         File file3 = new File(file1, "level.dat_mcr");
/*     */         
/* 191 */         if (!file2.renameTo(file3))
/*     */         {
/* 193 */           logger.warn("Unable to create level.dat_mcr backup");
/*     */         }
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private void convertFile(File p_75813_1_, Iterable<File> p_75813_2_, WorldChunkManager p_75813_3_, int p_75813_4_, int p_75813_5_, IProgressUpdate p_75813_6_) {
/* 201 */     for (File file1 : p_75813_2_) {
/*     */       
/* 203 */       convertChunks(p_75813_1_, file1, p_75813_3_, p_75813_4_, p_75813_5_, p_75813_6_);
/* 204 */       p_75813_4_++;
/* 205 */       int i = (int)Math.round(100.0D * p_75813_4_ / p_75813_5_);
/* 206 */       p_75813_6_.setLoadingProgress(i);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void convertChunks(File p_75811_1_, File p_75811_2_, WorldChunkManager p_75811_3_, int p_75811_4_, int p_75811_5_, IProgressUpdate progressCallback) {
/*     */     try {
/* 217 */       String s = p_75811_2_.getName();
/* 218 */       RegionFile regionfile = new RegionFile(p_75811_2_);
/* 219 */       RegionFile regionfile1 = new RegionFile(new File(p_75811_1_, s.substring(0, s.length() - ".mcr".length()) + ".mca"));
/*     */       
/* 221 */       for (int i = 0; i < 32; i++) {
/*     */         
/* 223 */         for (int j = 0; j < 32; j++) {
/*     */           
/* 225 */           if (regionfile.isChunkSaved(i, j) && !regionfile1.isChunkSaved(i, j)) {
/*     */             
/* 227 */             DataInputStream datainputstream = regionfile.getChunkDataInputStream(i, j);
/*     */             
/* 229 */             if (datainputstream == null) {
/*     */               
/* 231 */               logger.warn("Failed to fetch input stream");
/*     */             }
/*     */             else {
/*     */               
/* 235 */               NBTTagCompound nbttagcompound = CompressedStreamTools.read(datainputstream);
/* 236 */               datainputstream.close();
/* 237 */               NBTTagCompound nbttagcompound1 = nbttagcompound.getCompoundTag("Level");
/* 238 */               ChunkLoader.AnvilConverterData chunkloader$anvilconverterdata = ChunkLoader.load(nbttagcompound1);
/* 239 */               NBTTagCompound nbttagcompound2 = new NBTTagCompound();
/* 240 */               NBTTagCompound nbttagcompound3 = new NBTTagCompound();
/* 241 */               nbttagcompound2.setTag("Level", (NBTBase)nbttagcompound3);
/* 242 */               ChunkLoader.convertToAnvilFormat(chunkloader$anvilconverterdata, nbttagcompound3, p_75811_3_);
/* 243 */               DataOutputStream dataoutputstream = regionfile1.getChunkDataOutputStream(i, j);
/* 244 */               CompressedStreamTools.write(nbttagcompound2, dataoutputstream);
/* 245 */               dataoutputstream.close();
/*     */             } 
/*     */           } 
/*     */         } 
/*     */         
/* 250 */         int k = (int)Math.round(100.0D * (p_75811_4_ << 10) / (p_75811_5_ << 10));
/* 251 */         int l = (int)Math.round(100.0D * ((i + 1 << 5) + (p_75811_4_ << 10)) / (p_75811_5_ << 10));
/*     */         
/* 253 */         if (l > k)
/*     */         {
/* 255 */           progressCallback.setLoadingProgress(l);
/*     */         }
/*     */       } 
/*     */       
/* 259 */       regionfile.close();
/* 260 */       regionfile1.close();
/*     */     }
/* 262 */     catch (IOException ioexception) {
/*     */       
/* 264 */       ioexception.printStackTrace();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void addRegionFilesToCollection(File worldDir, Collection<File> collection) {
/* 273 */     File file1 = new File(worldDir, "region");
/* 274 */     File[] afile = file1.listFiles(new FilenameFilter()
/*     */         {
/*     */           public boolean accept(File p_accept_1_, String p_accept_2_)
/*     */           {
/* 278 */             return p_accept_2_.endsWith(".mcr");
/*     */           }
/*     */         });
/*     */     
/* 282 */     if (afile != null)
/*     */     {
/* 284 */       Collections.addAll(collection, afile);
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\minecraft\world\chunk\storage\AnvilSaveConverter.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */