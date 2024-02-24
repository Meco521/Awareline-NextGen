/*     */ package net.minecraft.world.storage;
/*     */ import java.io.DataInputStream;
/*     */ import java.io.DataOutputStream;
/*     */ import java.io.File;
/*     */ import java.io.FileInputStream;
/*     */ import java.io.FileOutputStream;
/*     */ import java.io.IOException;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.nbt.CompressedStreamTools;
/*     */ import net.minecraft.nbt.NBTBase;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.world.MinecraftException;
/*     */ import net.minecraft.world.chunk.storage.IChunkLoader;
/*     */ import org.apache.logging.log4j.Logger;
/*     */ 
/*     */ public class SaveHandler implements ISaveHandler, IPlayerFileData {
/*  17 */   private static final Logger logger = LogManager.getLogger();
/*     */ 
/*     */   
/*     */   private final File worldDirectory;
/*     */ 
/*     */   
/*     */   private final File playersDirectory;
/*     */ 
/*     */   
/*     */   private final File mapDataDir;
/*     */ 
/*     */   
/*  29 */   private final long initializationTime = MinecraftServer.getCurrentTimeMillis();
/*     */ 
/*     */   
/*     */   private final String saveDirectoryName;
/*     */ 
/*     */   
/*     */   public SaveHandler(File savesDirectory, String directoryName, boolean playersDirectoryIn) {
/*  36 */     this.worldDirectory = new File(savesDirectory, directoryName);
/*  37 */     this.worldDirectory.mkdirs();
/*  38 */     this.playersDirectory = new File(this.worldDirectory, "playerdata");
/*  39 */     this.mapDataDir = new File(this.worldDirectory, "data");
/*  40 */     this.mapDataDir.mkdirs();
/*  41 */     this.saveDirectoryName = directoryName;
/*     */     
/*  43 */     if (playersDirectoryIn)
/*     */     {
/*  45 */       this.playersDirectory.mkdirs();
/*     */     }
/*     */     
/*  48 */     setSessionLock();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void setSessionLock() {
/*     */     try {
/*  58 */       File file1 = new File(this.worldDirectory, "session.lock");
/*  59 */       DataOutputStream dataoutputstream = new DataOutputStream(new FileOutputStream(file1));
/*     */ 
/*     */       
/*     */       try {
/*  63 */         dataoutputstream.writeLong(this.initializationTime);
/*     */       }
/*     */       finally {
/*     */         
/*  67 */         dataoutputstream.close();
/*     */       }
/*     */     
/*  70 */     } catch (IOException ioexception) {
/*     */       
/*  72 */       ioexception.printStackTrace();
/*  73 */       throw new RuntimeException("Failed to check session lock, aborting");
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public File getWorldDirectory() {
/*  82 */     return this.worldDirectory;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void checkSessionLock() throws MinecraftException {
/*     */     try {
/*  92 */       File file1 = new File(this.worldDirectory, "session.lock");
/*  93 */       DataInputStream datainputstream = new DataInputStream(new FileInputStream(file1));
/*     */ 
/*     */       
/*     */       try {
/*  97 */         if (datainputstream.readLong() != this.initializationTime)
/*     */         {
/*  99 */           throw new MinecraftException("The save is being accessed from another location, aborting");
/*     */         }
/*     */       }
/*     */       finally {
/*     */         
/* 104 */         datainputstream.close();
/*     */       }
/*     */     
/* 107 */     } catch (IOException var7) {
/*     */       
/* 109 */       throw new MinecraftException("Failed to check session lock, aborting");
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IChunkLoader getChunkLoader(WorldProvider provider) {
/* 118 */     throw new RuntimeException("Old Chunk Storage is no longer supported.");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public WorldInfo loadWorldInfo() {
/* 126 */     File file1 = new File(this.worldDirectory, "level.dat");
/*     */     
/* 128 */     if (file1.exists()) {
/*     */       
/*     */       try {
/*     */         
/* 132 */         NBTTagCompound nbttagcompound2 = CompressedStreamTools.readCompressed(new FileInputStream(file1));
/* 133 */         NBTTagCompound nbttagcompound3 = nbttagcompound2.getCompoundTag("Data");
/* 134 */         return new WorldInfo(nbttagcompound3);
/*     */       }
/* 136 */       catch (Exception exception1) {
/*     */         
/* 138 */         exception1.printStackTrace();
/*     */       } 
/*     */     }
/*     */     
/* 142 */     file1 = new File(this.worldDirectory, "level.dat_old");
/*     */     
/* 144 */     if (file1.exists()) {
/*     */       
/*     */       try {
/*     */         
/* 148 */         NBTTagCompound nbttagcompound = CompressedStreamTools.readCompressed(new FileInputStream(file1));
/* 149 */         NBTTagCompound nbttagcompound1 = nbttagcompound.getCompoundTag("Data");
/* 150 */         return new WorldInfo(nbttagcompound1);
/*     */       }
/* 152 */       catch (Exception exception) {
/*     */         
/* 154 */         exception.printStackTrace();
/*     */       } 
/*     */     }
/*     */     
/* 158 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void saveWorldInfoWithPlayer(WorldInfo worldInformation, NBTTagCompound tagCompound) {
/* 166 */     NBTTagCompound nbttagcompound = worldInformation.cloneNBTCompound(tagCompound);
/* 167 */     NBTTagCompound nbttagcompound1 = new NBTTagCompound();
/* 168 */     nbttagcompound1.setTag("Data", (NBTBase)nbttagcompound);
/*     */ 
/*     */     
/*     */     try {
/* 172 */       File file1 = new File(this.worldDirectory, "level.dat_new");
/* 173 */       File file2 = new File(this.worldDirectory, "level.dat_old");
/* 174 */       File file3 = new File(this.worldDirectory, "level.dat");
/* 175 */       CompressedStreamTools.writeCompressed(nbttagcompound1, new FileOutputStream(file1));
/*     */       
/* 177 */       if (file2.exists())
/*     */       {
/* 179 */         file2.delete();
/*     */       }
/*     */       
/* 182 */       file3.renameTo(file2);
/*     */       
/* 184 */       if (file3.exists())
/*     */       {
/* 186 */         file3.delete();
/*     */       }
/*     */       
/* 189 */       file1.renameTo(file3);
/*     */       
/* 191 */       if (file1.exists())
/*     */       {
/* 193 */         file1.delete();
/*     */       }
/*     */     }
/* 196 */     catch (Exception exception) {
/*     */       
/* 198 */       exception.printStackTrace();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void saveWorldInfo(WorldInfo worldInformation) {
/* 207 */     NBTTagCompound nbttagcompound = worldInformation.getNBTTagCompound();
/* 208 */     NBTTagCompound nbttagcompound1 = new NBTTagCompound();
/* 209 */     nbttagcompound1.setTag("Data", (NBTBase)nbttagcompound);
/*     */ 
/*     */     
/*     */     try {
/* 213 */       File file1 = new File(this.worldDirectory, "level.dat_new");
/* 214 */       File file2 = new File(this.worldDirectory, "level.dat_old");
/* 215 */       File file3 = new File(this.worldDirectory, "level.dat");
/* 216 */       CompressedStreamTools.writeCompressed(nbttagcompound1, new FileOutputStream(file1));
/*     */       
/* 218 */       if (file2.exists())
/*     */       {
/* 220 */         file2.delete();
/*     */       }
/*     */       
/* 223 */       file3.renameTo(file2);
/*     */       
/* 225 */       if (file3.exists())
/*     */       {
/* 227 */         file3.delete();
/*     */       }
/*     */       
/* 230 */       file1.renameTo(file3);
/*     */       
/* 232 */       if (file1.exists())
/*     */       {
/* 234 */         file1.delete();
/*     */       }
/*     */     }
/* 237 */     catch (Exception exception) {
/*     */       
/* 239 */       exception.printStackTrace();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void writePlayerData(EntityPlayer player) {
/*     */     try {
/* 250 */       NBTTagCompound nbttagcompound = new NBTTagCompound();
/* 251 */       player.writeToNBT(nbttagcompound);
/* 252 */       File file1 = new File(this.playersDirectory, player.getUniqueID().toString() + ".dat.tmp");
/* 253 */       File file2 = new File(this.playersDirectory, player.getUniqueID().toString() + ".dat");
/* 254 */       CompressedStreamTools.writeCompressed(nbttagcompound, new FileOutputStream(file1));
/*     */       
/* 256 */       if (file2.exists())
/*     */       {
/* 258 */         file2.delete();
/*     */       }
/*     */       
/* 261 */       file1.renameTo(file2);
/*     */     }
/* 263 */     catch (Exception var5) {
/*     */       
/* 265 */       logger.warn("Failed to save player data for " + player.getName());
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public NBTTagCompound readPlayerData(EntityPlayer player) {
/* 274 */     NBTTagCompound nbttagcompound = null;
/*     */ 
/*     */     
/*     */     try {
/* 278 */       File file1 = new File(this.playersDirectory, player.getUniqueID().toString() + ".dat");
/*     */       
/* 280 */       if (file1.exists() && file1.isFile())
/*     */       {
/* 282 */         nbttagcompound = CompressedStreamTools.readCompressed(new FileInputStream(file1));
/*     */       }
/*     */     }
/* 285 */     catch (Exception var4) {
/*     */       
/* 287 */       logger.warn("Failed to load player data for " + player.getName());
/*     */     } 
/*     */     
/* 290 */     if (nbttagcompound != null)
/*     */     {
/* 292 */       player.readFromNBT(nbttagcompound);
/*     */     }
/*     */     
/* 295 */     return nbttagcompound;
/*     */   }
/*     */ 
/*     */   
/*     */   public IPlayerFileData getPlayerNBTManager() {
/* 300 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String[] getAvailablePlayerDat() {
/* 308 */     String[] astring = this.playersDirectory.list();
/*     */     
/* 310 */     if (astring == null)
/*     */     {
/* 312 */       astring = new String[0];
/*     */     }
/*     */     
/* 315 */     for (int i = 0; i < astring.length; i++) {
/*     */       
/* 317 */       if (astring[i].endsWith(".dat"))
/*     */       {
/* 319 */         astring[i] = astring[i].substring(0, astring[i].length() - 4);
/*     */       }
/*     */     } 
/*     */     
/* 323 */     return astring;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void flush() {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public File getMapFileFromName(String mapName) {
/* 338 */     return new File(this.mapDataDir, mapName + ".dat");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getWorldDirectoryName() {
/* 346 */     return this.saveDirectoryName;
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\minecraft\world\storage\SaveHandler.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */