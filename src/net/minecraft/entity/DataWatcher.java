/*     */ package net.minecraft.entity;
/*     */ 
/*     */ import com.google.common.collect.Lists;
/*     */ import com.google.common.collect.Maps;
/*     */ import java.io.IOException;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.concurrent.locks.ReadWriteLock;
/*     */ import java.util.concurrent.locks.ReentrantReadWriteLock;
/*     */ import net.minecraft.crash.CrashReport;
/*     */ import net.minecraft.crash.CrashReportCategory;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.network.PacketBuffer;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.util.ReportedException;
/*     */ import net.minecraft.util.Rotations;
/*     */ import net.minecraft.world.biome.BiomeGenBase;
/*     */ import org.apache.commons.lang3.ObjectUtils;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class DataWatcher
/*     */ {
/*     */   private final Entity owner;
/*     */   private boolean isBlank = true;
/*  27 */   private static final Map<Class<?>, Integer> dataTypes = Maps.newHashMap();
/*  28 */   private final Map<Integer, WatchableObject> watchedObjects = Maps.newHashMap();
/*     */   
/*     */   private boolean objectChanged;
/*     */   
/*  32 */   private final ReadWriteLock lock = new ReentrantReadWriteLock();
/*  33 */   public BiomeGenBase spawnBiome = BiomeGenBase.plains;
/*  34 */   public BlockPos spawnPosition = BlockPos.ORIGIN;
/*     */ 
/*     */   
/*     */   public DataWatcher(Entity owner) {
/*  38 */     this.owner = owner;
/*     */   }
/*     */ 
/*     */   
/*     */   public <T> void addObject(int id, T object) {
/*  43 */     Integer integer = dataTypes.get(object.getClass());
/*     */     
/*  45 */     if (integer == null)
/*     */     {
/*  47 */       throw new IllegalArgumentException("Unknown data type: " + object.getClass());
/*     */     }
/*  49 */     if (id > 31)
/*     */     {
/*  51 */       throw new IllegalArgumentException("Data value id is too big with " + id + "! (Max is " + '\037' + ")");
/*     */     }
/*  53 */     if (this.watchedObjects.containsKey(Integer.valueOf(id)))
/*     */     {
/*  55 */       throw new IllegalArgumentException("Duplicate id value for " + id + "!");
/*     */     }
/*     */ 
/*     */     
/*  59 */     WatchableObject datawatcher$watchableobject = new WatchableObject(integer.intValue(), id, object);
/*  60 */     this.lock.writeLock().lock();
/*  61 */     this.watchedObjects.put(Integer.valueOf(id), datawatcher$watchableobject);
/*  62 */     this.lock.writeLock().unlock();
/*  63 */     this.isBlank = false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addObjectByDataType(int id, int type) {
/*  72 */     WatchableObject datawatcher$watchableobject = new WatchableObject(type, id, null);
/*  73 */     this.lock.writeLock().lock();
/*  74 */     this.watchedObjects.put(Integer.valueOf(id), datawatcher$watchableobject);
/*  75 */     this.lock.writeLock().unlock();
/*  76 */     this.isBlank = false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public byte getWatchableObjectByte(int id) {
/*  84 */     return ((Byte)getWatchedObject(id).getObject()).byteValue();
/*     */   }
/*     */ 
/*     */   
/*     */   public short getWatchableObjectShort(int id) {
/*  89 */     return ((Short)getWatchedObject(id).getObject()).shortValue();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getWatchableObjectInt(int id) {
/*  97 */     return ((Integer)getWatchedObject(id).getObject()).intValue();
/*     */   }
/*     */ 
/*     */   
/*     */   public float getWatchableObjectFloat(int id) {
/* 102 */     return ((Float)getWatchedObject(id).getObject()).floatValue();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getWatchableObjectString(int id) {
/* 110 */     return (String)getWatchedObject(id).getObject();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ItemStack getWatchableObjectItemStack(int id) {
/* 118 */     return (ItemStack)getWatchedObject(id).getObject();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private WatchableObject getWatchedObject(int id) {
/*     */     WatchableObject datawatcher$watchableobject;
/* 126 */     this.lock.readLock().lock();
/*     */ 
/*     */ 
/*     */     
/*     */     try {
/* 131 */       datawatcher$watchableobject = this.watchedObjects.get(Integer.valueOf(id));
/*     */     }
/* 133 */     catch (Throwable throwable) {
/*     */       
/* 135 */       CrashReport crashreport = CrashReport.makeCrashReport(throwable, "Getting synched entity data");
/* 136 */       CrashReportCategory crashreportcategory = crashreport.makeCategory("Synched entity data");
/* 137 */       crashreportcategory.addCrashSection("Data ID", Integer.valueOf(id));
/* 138 */       throw new ReportedException(crashreport);
/*     */     } 
/*     */     
/* 141 */     this.lock.readLock().unlock();
/* 142 */     return datawatcher$watchableobject;
/*     */   }
/*     */ 
/*     */   
/*     */   public Rotations getWatchableObjectRotations(int id) {
/* 147 */     return (Rotations)getWatchedObject(id).getObject();
/*     */   }
/*     */ 
/*     */   
/*     */   public <T> void updateObject(int id, T newData) {
/* 152 */     WatchableObject datawatcher$watchableobject = getWatchedObject(id);
/*     */     
/* 154 */     if (ObjectUtils.notEqual(newData, datawatcher$watchableobject.getObject())) {
/*     */       
/* 156 */       datawatcher$watchableobject.setObject(newData);
/* 157 */       this.owner.onDataWatcherUpdate(id);
/* 158 */       datawatcher$watchableobject.setWatched(true);
/* 159 */       this.objectChanged = true;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void setObjectWatched(int id) {
/* 165 */     (getWatchedObject(id)).watched = true;
/* 166 */     this.objectChanged = true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean hasObjectChanged() {
/* 174 */     return this.objectChanged;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void writeWatchedListToPacketBuffer(List<WatchableObject> objectsList, PacketBuffer buffer) throws IOException {
/* 183 */     if (objectsList != null)
/*     */     {
/* 185 */       for (WatchableObject datawatcher$watchableobject : objectsList)
/*     */       {
/* 187 */         writeWatchableObjectToPacketBuffer(buffer, datawatcher$watchableobject);
/*     */       }
/*     */     }
/*     */     
/* 191 */     buffer.writeByte(127);
/*     */   }
/*     */ 
/*     */   
/*     */   public List<WatchableObject> getChanged() {
/* 196 */     List<WatchableObject> list = null;
/*     */     
/* 198 */     if (this.objectChanged) {
/*     */       
/* 200 */       this.lock.readLock().lock();
/*     */       
/* 202 */       for (WatchableObject datawatcher$watchableobject : this.watchedObjects.values()) {
/*     */         
/* 204 */         if (datawatcher$watchableobject.isWatched()) {
/*     */           
/* 206 */           datawatcher$watchableobject.setWatched(false);
/*     */           
/* 208 */           if (list == null)
/*     */           {
/* 210 */             list = Lists.newArrayList();
/*     */           }
/*     */           
/* 213 */           list.add(datawatcher$watchableobject);
/*     */         } 
/*     */       } 
/*     */       
/* 217 */       this.lock.readLock().unlock();
/*     */     } 
/*     */     
/* 220 */     this.objectChanged = false;
/* 221 */     return list;
/*     */   }
/*     */ 
/*     */   
/*     */   public void writeTo(PacketBuffer buffer) throws IOException {
/* 226 */     this.lock.readLock().lock();
/*     */     
/* 228 */     for (WatchableObject datawatcher$watchableobject : this.watchedObjects.values())
/*     */     {
/* 230 */       writeWatchableObjectToPacketBuffer(buffer, datawatcher$watchableobject);
/*     */     }
/*     */     
/* 233 */     this.lock.readLock().unlock();
/* 234 */     buffer.writeByte(127);
/*     */   }
/*     */ 
/*     */   
/*     */   public List<WatchableObject> getAllWatched() {
/* 239 */     List<WatchableObject> list = null;
/* 240 */     this.lock.readLock().lock();
/*     */     
/* 242 */     for (WatchableObject datawatcher$watchableobject : this.watchedObjects.values()) {
/*     */       
/* 244 */       if (list == null)
/*     */       {
/* 246 */         list = Lists.newArrayList();
/*     */       }
/*     */       
/* 249 */       list.add(datawatcher$watchableobject);
/*     */     } 
/*     */     
/* 252 */     this.lock.readLock().unlock();
/* 253 */     return list;
/*     */   }
/*     */ 
/*     */   
/*     */   private static void writeWatchableObjectToPacketBuffer(PacketBuffer buffer, WatchableObject object) {
/*     */     ItemStack itemstack;
/*     */     BlockPos blockpos;
/*     */     Rotations rotations;
/* 261 */     int i = (object.getObjectType() << 5 | object.getDataValueId() & 0x1F) & 0xFF;
/* 262 */     buffer.writeByte(i);
/*     */     
/* 264 */     switch (object.getObjectType()) {
/*     */       
/*     */       case 0:
/* 267 */         buffer.writeByte(((Byte)object.getObject()).byteValue());
/*     */         break;
/*     */       
/*     */       case 1:
/* 271 */         buffer.writeShort(((Short)object.getObject()).shortValue());
/*     */         break;
/*     */       
/*     */       case 2:
/* 275 */         buffer.writeInt(((Integer)object.getObject()).intValue());
/*     */         break;
/*     */       
/*     */       case 3:
/* 279 */         buffer.writeFloat(((Float)object.getObject()).floatValue());
/*     */         break;
/*     */       
/*     */       case 4:
/* 283 */         buffer.writeString((String)object.getObject());
/*     */         break;
/*     */       
/*     */       case 5:
/* 287 */         itemstack = (ItemStack)object.getObject();
/* 288 */         buffer.writeItemStackToBuffer(itemstack);
/*     */         break;
/*     */       
/*     */       case 6:
/* 292 */         blockpos = (BlockPos)object.getObject();
/* 293 */         buffer.writeInt(blockpos.getX());
/* 294 */         buffer.writeInt(blockpos.getY());
/* 295 */         buffer.writeInt(blockpos.getZ());
/*     */         break;
/*     */       
/*     */       case 7:
/* 299 */         rotations = (Rotations)object.getObject();
/* 300 */         buffer.writeFloat(rotations.getX());
/* 301 */         buffer.writeFloat(rotations.getY());
/* 302 */         buffer.writeFloat(rotations.getZ());
/*     */         break;
/*     */     } 
/*     */   }
/*     */   
/*     */   public static List<WatchableObject> readWatchedListFromPacketBuffer(PacketBuffer buffer) throws IOException {
/* 308 */     List<WatchableObject> list = null;
/*     */     
/* 310 */     for (int i = buffer.readByte(); i != 127; i = buffer.readByte()) {
/*     */       int l, i1, j1; float f, f1, f2;
/* 312 */       if (list == null)
/*     */       {
/* 314 */         list = Lists.newArrayList();
/*     */       }
/*     */       
/* 317 */       int j = (i & 0xE0) >> 5;
/* 318 */       int k = i & 0x1F;
/* 319 */       WatchableObject datawatcher$watchableobject = null;
/*     */       
/* 321 */       switch (j) {
/*     */         
/*     */         case 0:
/* 324 */           datawatcher$watchableobject = new WatchableObject(j, k, Byte.valueOf(buffer.readByte()));
/*     */           break;
/*     */         
/*     */         case 1:
/* 328 */           datawatcher$watchableobject = new WatchableObject(j, k, Short.valueOf(buffer.readShort()));
/*     */           break;
/*     */         
/*     */         case 2:
/* 332 */           datawatcher$watchableobject = new WatchableObject(j, k, Integer.valueOf(buffer.readInt()));
/*     */           break;
/*     */         
/*     */         case 3:
/* 336 */           datawatcher$watchableobject = new WatchableObject(j, k, Float.valueOf(buffer.readFloat()));
/*     */           break;
/*     */         
/*     */         case 4:
/* 340 */           datawatcher$watchableobject = new WatchableObject(j, k, buffer.readStringFromBuffer(32767));
/*     */           break;
/*     */         
/*     */         case 5:
/* 344 */           datawatcher$watchableobject = new WatchableObject(j, k, buffer.readItemStackFromBuffer());
/*     */           break;
/*     */         
/*     */         case 6:
/* 348 */           l = buffer.readInt();
/* 349 */           i1 = buffer.readInt();
/* 350 */           j1 = buffer.readInt();
/* 351 */           datawatcher$watchableobject = new WatchableObject(j, k, new BlockPos(l, i1, j1));
/*     */           break;
/*     */         
/*     */         case 7:
/* 355 */           f = buffer.readFloat();
/* 356 */           f1 = buffer.readFloat();
/* 357 */           f2 = buffer.readFloat();
/* 358 */           datawatcher$watchableobject = new WatchableObject(j, k, new Rotations(f, f1, f2));
/*     */           break;
/*     */       } 
/* 361 */       list.add(datawatcher$watchableobject);
/*     */     } 
/*     */     
/* 364 */     return list;
/*     */   }
/*     */ 
/*     */   
/*     */   public void updateWatchedObjectsFromList(List<WatchableObject> p_75687_1_) {
/* 369 */     this.lock.writeLock().lock();
/*     */     
/* 371 */     for (WatchableObject datawatcher$watchableobject : p_75687_1_) {
/*     */       
/* 373 */       WatchableObject datawatcher$watchableobject1 = this.watchedObjects.get(Integer.valueOf(datawatcher$watchableobject.getDataValueId()));
/*     */       
/* 375 */       if (datawatcher$watchableobject1 != null) {
/*     */         
/* 377 */         datawatcher$watchableobject1.setObject(datawatcher$watchableobject.getObject());
/* 378 */         this.owner.onDataWatcherUpdate(datawatcher$watchableobject.getDataValueId());
/*     */       } 
/*     */     } 
/*     */     
/* 382 */     this.lock.writeLock().unlock();
/* 383 */     this.objectChanged = true;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean getIsBlank() {
/* 388 */     return this.isBlank;
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_111144_e() {
/* 393 */     this.objectChanged = false;
/*     */   }
/*     */ 
/*     */   
/*     */   static {
/* 398 */     dataTypes.put(Byte.class, Integer.valueOf(0));
/* 399 */     dataTypes.put(Short.class, Integer.valueOf(1));
/* 400 */     dataTypes.put(Integer.class, Integer.valueOf(2));
/* 401 */     dataTypes.put(Float.class, Integer.valueOf(3));
/* 402 */     dataTypes.put(String.class, Integer.valueOf(4));
/* 403 */     dataTypes.put(ItemStack.class, Integer.valueOf(5));
/* 404 */     dataTypes.put(BlockPos.class, Integer.valueOf(6));
/* 405 */     dataTypes.put(Rotations.class, Integer.valueOf(7));
/*     */   }
/*     */ 
/*     */   
/*     */   public static class WatchableObject
/*     */   {
/*     */     private final int objectType;
/*     */     private final int dataValueId;
/*     */     private Object watchedObject;
/*     */     boolean watched;
/*     */     
/*     */     public WatchableObject(int type, int id, Object object) {
/* 417 */       this.dataValueId = id;
/* 418 */       this.watchedObject = object;
/* 419 */       this.objectType = type;
/* 420 */       this.watched = true;
/*     */     }
/*     */ 
/*     */     
/*     */     public int getDataValueId() {
/* 425 */       return this.dataValueId;
/*     */     }
/*     */ 
/*     */     
/*     */     public void setObject(Object object) {
/* 430 */       this.watchedObject = object;
/*     */     }
/*     */ 
/*     */     
/*     */     public Object getObject() {
/* 435 */       return this.watchedObject;
/*     */     }
/*     */ 
/*     */     
/*     */     public int getObjectType() {
/* 440 */       return this.objectType;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean isWatched() {
/* 445 */       return this.watched;
/*     */     }
/*     */ 
/*     */     
/*     */     public void setWatched(boolean watched) {
/* 450 */       this.watched = watched;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\minecraft\entity\DataWatcher.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */