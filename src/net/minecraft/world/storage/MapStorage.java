/*     */ package net.minecraft.world.storage;
/*     */ import com.google.common.collect.Lists;
/*     */ import com.google.common.collect.Maps;
/*     */ import java.io.DataInputStream;
/*     */ import java.io.DataOutputStream;
/*     */ import java.io.File;
/*     */ import java.io.FileInputStream;
/*     */ import java.io.FileOutputStream;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import net.minecraft.nbt.CompressedStreamTools;
/*     */ import net.minecraft.nbt.NBTBase;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.nbt.NBTTagShort;
/*     */ import net.minecraft.world.WorldSavedData;
/*     */ 
/*     */ public class MapStorage {
/*  18 */   protected Map<String, WorldSavedData> loadedDataMap = Maps.newHashMap(); private final ISaveHandler saveHandler;
/*  19 */   private final List<WorldSavedData> loadedDataList = Lists.newArrayList();
/*  20 */   private final Map<String, Short> idCounts = Maps.newHashMap();
/*     */ 
/*     */   
/*     */   public MapStorage(ISaveHandler saveHandlerIn) {
/*  24 */     this.saveHandler = saveHandlerIn;
/*  25 */     loadIdCounts();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public WorldSavedData loadData(Class<? extends WorldSavedData> clazz, String dataIdentifier) {
/*  34 */     WorldSavedData worldsaveddata = this.loadedDataMap.get(dataIdentifier);
/*     */     
/*  36 */     if (worldsaveddata != null)
/*     */     {
/*  38 */       return worldsaveddata;
/*     */     }
/*     */ 
/*     */     
/*  42 */     if (this.saveHandler != null) {
/*     */       
/*     */       try {
/*     */         
/*  46 */         File file1 = this.saveHandler.getMapFileFromName(dataIdentifier);
/*     */         
/*  48 */         if (file1 != null && file1.exists())
/*     */         {
/*     */           
/*     */           try {
/*  52 */             worldsaveddata = clazz.getConstructor(new Class[] { String.class }).newInstance(new Object[] { dataIdentifier });
/*     */           }
/*  54 */           catch (Exception exception) {
/*     */             
/*  56 */             throw new RuntimeException("Failed to instantiate " + clazz.toString(), exception);
/*     */           } 
/*     */           
/*  59 */           FileInputStream fileinputstream = new FileInputStream(file1);
/*  60 */           NBTTagCompound nbttagcompound = CompressedStreamTools.readCompressed(fileinputstream);
/*  61 */           fileinputstream.close();
/*  62 */           worldsaveddata.readFromNBT(nbttagcompound.getCompoundTag("data"));
/*     */         }
/*     */       
/*  65 */       } catch (Exception exception1) {
/*     */         
/*  67 */         exception1.printStackTrace();
/*     */       } 
/*     */     }
/*     */     
/*  71 */     if (worldsaveddata != null) {
/*     */       
/*  73 */       this.loadedDataMap.put(dataIdentifier, worldsaveddata);
/*  74 */       this.loadedDataList.add(worldsaveddata);
/*     */     } 
/*     */     
/*  77 */     return worldsaveddata;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setData(String dataIdentifier, WorldSavedData data) {
/*  86 */     if (this.loadedDataMap.containsKey(dataIdentifier))
/*     */     {
/*  88 */       this.loadedDataList.remove(this.loadedDataMap.remove(dataIdentifier));
/*     */     }
/*     */     
/*  91 */     this.loadedDataMap.put(dataIdentifier, data);
/*  92 */     this.loadedDataList.add(data);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void saveAllData() {
/* 100 */     for (int i = 0; i < this.loadedDataList.size(); i++) {
/*     */       
/* 102 */       WorldSavedData worldsaveddata = this.loadedDataList.get(i);
/*     */       
/* 104 */       if (worldsaveddata.isDirty()) {
/*     */         
/* 106 */         saveData(worldsaveddata);
/* 107 */         worldsaveddata.setDirty(false);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void saveData(WorldSavedData p_75747_1_) {
/* 117 */     if (this.saveHandler != null) {
/*     */       
/*     */       try {
/*     */         
/* 121 */         File file1 = this.saveHandler.getMapFileFromName(p_75747_1_.mapName);
/*     */         
/* 123 */         if (file1 != null)
/*     */         {
/* 125 */           NBTTagCompound nbttagcompound = new NBTTagCompound();
/* 126 */           p_75747_1_.writeToNBT(nbttagcompound);
/* 127 */           NBTTagCompound nbttagcompound1 = new NBTTagCompound();
/* 128 */           nbttagcompound1.setTag("data", (NBTBase)nbttagcompound);
/* 129 */           FileOutputStream fileoutputstream = new FileOutputStream(file1);
/* 130 */           CompressedStreamTools.writeCompressed(nbttagcompound1, fileoutputstream);
/* 131 */           fileoutputstream.close();
/*     */         }
/*     */       
/* 134 */       } catch (Exception exception) {
/*     */         
/* 136 */         exception.printStackTrace();
/*     */       } 
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void loadIdCounts() {
/*     */     try {
/* 148 */       this.idCounts.clear();
/*     */       
/* 150 */       if (this.saveHandler == null) {
/*     */         return;
/*     */       }
/*     */ 
/*     */       
/* 155 */       File file1 = this.saveHandler.getMapFileFromName("idcounts");
/*     */       
/* 157 */       if (file1 != null && file1.exists()) {
/*     */         
/* 159 */         DataInputStream datainputstream = new DataInputStream(new FileInputStream(file1));
/* 160 */         NBTTagCompound nbttagcompound = CompressedStreamTools.read(datainputstream);
/* 161 */         datainputstream.close();
/*     */         
/* 163 */         for (String s : nbttagcompound.getKeySet()) {
/*     */           
/* 165 */           NBTBase nbtbase = nbttagcompound.getTag(s);
/*     */           
/* 167 */           if (nbtbase instanceof NBTTagShort)
/*     */           {
/* 169 */             NBTTagShort nbttagshort = (NBTTagShort)nbtbase;
/* 170 */             short short1 = nbttagshort.getShort();
/* 171 */             this.idCounts.put(s, Short.valueOf(short1));
/*     */           }
/*     */         
/*     */         } 
/*     */       } 
/* 176 */     } catch (Exception exception) {
/*     */       
/* 178 */       exception.printStackTrace();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getUniqueDataId(String key) {
/* 187 */     Short oshort = this.idCounts.get(key);
/*     */     
/* 189 */     if (oshort == null) {
/*     */       
/* 191 */       oshort = Short.valueOf((short)0);
/*     */     }
/*     */     else {
/*     */       
/* 195 */       oshort = Short.valueOf((short)(oshort.shortValue() + 1));
/*     */     } 
/*     */     
/* 198 */     this.idCounts.put(key, oshort);
/*     */     
/* 200 */     if (this.saveHandler == null)
/*     */     {
/* 202 */       return oshort.shortValue();
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     try {
/* 208 */       File file1 = this.saveHandler.getMapFileFromName("idcounts");
/*     */       
/* 210 */       if (file1 != null)
/*     */       {
/* 212 */         NBTTagCompound nbttagcompound = new NBTTagCompound();
/*     */         
/* 214 */         for (Map.Entry<String, Short> entry : this.idCounts.entrySet()) {
/*     */           
/* 216 */           short short1 = ((Short)entry.getValue()).shortValue();
/* 217 */           nbttagcompound.setShort(entry.getKey(), short1);
/*     */         } 
/*     */         
/* 220 */         DataOutputStream dataoutputstream = new DataOutputStream(new FileOutputStream(file1));
/* 221 */         CompressedStreamTools.write(nbttagcompound, dataoutputstream);
/* 222 */         dataoutputstream.close();
/*     */       }
/*     */     
/* 225 */     } catch (Exception exception) {
/*     */       
/* 227 */       exception.printStackTrace();
/*     */     } 
/*     */     
/* 230 */     return oshort.shortValue();
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\minecraft\world\storage\MapStorage.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */