/*     */ package net.minecraft.nbt;
/*     */ 
/*     */ import com.google.common.collect.Maps;
/*     */ import java.io.DataInput;
/*     */ import java.io.DataOutput;
/*     */ import java.io.IOException;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ import java.util.concurrent.Callable;
/*     */ import net.minecraft.crash.CrashReport;
/*     */ import net.minecraft.crash.CrashReportCategory;
/*     */ import net.minecraft.util.ReportedException;
/*     */ 
/*     */ 
/*     */ public class NBTTagCompound
/*     */   extends NBTBase
/*     */ {
/*  18 */   Map<String, NBTBase> tagMap = Maps.newHashMap();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   void write(DataOutput output) throws IOException {
/*  25 */     for (Map.Entry<String, NBTBase> entry : this.tagMap.entrySet()) {
/*     */       
/*  27 */       NBTBase nbtbase = entry.getValue();
/*  28 */       writeEntry(entry.getKey(), nbtbase, output);
/*     */     } 
/*     */     
/*  31 */     output.writeByte(0);
/*     */   }
/*     */ 
/*     */   
/*     */   void read(DataInput input, int depth, NBTSizeTracker sizeTracker) throws IOException {
/*  36 */     sizeTracker.read(384L);
/*     */     
/*  38 */     if (depth > 512)
/*     */     {
/*  40 */       throw new RuntimeException("Tried to read NBT tag with too high complexity, depth > 512");
/*     */     }
/*     */ 
/*     */     
/*  44 */     this.tagMap.clear();
/*     */     
/*     */     byte b0;
/*  47 */     while ((b0 = readType(input, sizeTracker)) != 0) {
/*     */       
/*  49 */       String s = readKey(input, sizeTracker);
/*  50 */       sizeTracker.read((224 + 16 * s.length()));
/*  51 */       NBTBase nbtbase = readNBT(b0, s, input, depth + 1, sizeTracker);
/*     */       
/*  53 */       if (this.tagMap.put(s, nbtbase) != null)
/*     */       {
/*  55 */         sizeTracker.read(288L);
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Set<String> getKeySet() {
/*  63 */     return this.tagMap.keySet();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public byte getId() {
/*  71 */     return 10;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setTag(String key, NBTBase value) {
/*  79 */     this.tagMap.put(key, value);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setByte(String key, byte value) {
/*  87 */     this.tagMap.put(key, new NBTTagByte(value));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setShort(String key, short value) {
/*  95 */     this.tagMap.put(key, new NBTTagShort(value));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setInteger(String key, int value) {
/* 103 */     this.tagMap.put(key, new NBTTagInt(value));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setLong(String key, long value) {
/* 111 */     this.tagMap.put(key, new NBTTagLong(value));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setFloat(String key, float value) {
/* 119 */     this.tagMap.put(key, new NBTTagFloat(value));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setDouble(String key, double value) {
/* 127 */     this.tagMap.put(key, new NBTTagDouble(value));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setString(String key, String value) {
/* 135 */     this.tagMap.put(key, new NBTTagString(value));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setByteArray(String key, byte[] value) {
/* 143 */     this.tagMap.put(key, new NBTTagByteArray(value));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setIntArray(String key, int[] value) {
/* 151 */     this.tagMap.put(key, new NBTTagIntArray(value));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setBoolean(String key, boolean value) {
/* 159 */     setByte(key, (byte)(value ? 1 : 0));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public NBTBase getTag(String key) {
/* 167 */     return this.tagMap.get(key);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public byte getTagId(String key) {
/* 175 */     NBTBase nbtbase = this.tagMap.get(key);
/* 176 */     return (nbtbase != null) ? nbtbase.getId() : 0;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean hasKey(String key) {
/* 184 */     return this.tagMap.containsKey(key);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean hasKey(String key, int type) {
/* 189 */     int i = getTagId(key);
/*     */     
/* 191 */     if (i == type)
/*     */     {
/* 193 */       return true;
/*     */     }
/* 195 */     if (type != 99) {
/*     */       
/* 197 */       if (i > 0);
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 202 */       return false;
/*     */     } 
/*     */ 
/*     */     
/* 206 */     return (i == 1 || i == 2 || i == 3 || i == 4 || i == 5 || i == 6);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public byte getByte(String key) {
/*     */     try {
/* 217 */       return !hasKey(key, 99) ? 0 : ((NBTBase.NBTPrimitive)this.tagMap.get(key)).getByte();
/*     */     }
/* 219 */     catch (ClassCastException var3) {
/*     */       
/* 221 */       return 0;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public short getShort(String key) {
/*     */     try {
/* 232 */       return !hasKey(key, 99) ? 0 : ((NBTBase.NBTPrimitive)this.tagMap.get(key)).getShort();
/*     */     }
/* 234 */     catch (ClassCastException var3) {
/*     */       
/* 236 */       return 0;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getInteger(String key) {
/*     */     try {
/* 247 */       return !hasKey(key, 99) ? 0 : ((NBTBase.NBTPrimitive)this.tagMap.get(key)).getInt();
/*     */     }
/* 249 */     catch (ClassCastException var3) {
/*     */       
/* 251 */       return 0;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public long getLong(String key) {
/*     */     try {
/* 262 */       return !hasKey(key, 99) ? 0L : ((NBTBase.NBTPrimitive)this.tagMap.get(key)).getLong();
/*     */     }
/* 264 */     catch (ClassCastException var3) {
/*     */       
/* 266 */       return 0L;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public float getFloat(String key) {
/*     */     try {
/* 277 */       return !hasKey(key, 99) ? 0.0F : ((NBTBase.NBTPrimitive)this.tagMap.get(key)).getFloat();
/*     */     }
/* 279 */     catch (ClassCastException var3) {
/*     */       
/* 281 */       return 0.0F;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double getDouble(String key) {
/*     */     try {
/* 292 */       return !hasKey(key, 99) ? 0.0D : ((NBTBase.NBTPrimitive)this.tagMap.get(key)).getDouble();
/*     */     }
/* 294 */     catch (ClassCastException var3) {
/*     */       
/* 296 */       return 0.0D;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getString(String key) {
/*     */     try {
/* 307 */       return !hasKey(key, 8) ? "" : ((NBTBase)this.tagMap.get(key)).getString();
/*     */     }
/* 309 */     catch (ClassCastException var3) {
/*     */       
/* 311 */       return "";
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public byte[] getByteArray(String key) {
/*     */     try {
/* 322 */       return !hasKey(key, 7) ? new byte[0] : ((NBTTagByteArray)this.tagMap.get(key)).getByteArray();
/*     */     }
/* 324 */     catch (ClassCastException classcastexception) {
/*     */       
/* 326 */       throw new ReportedException(createCrashReport(key, 7, classcastexception));
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int[] getIntArray(String key) {
/*     */     try {
/* 337 */       return !hasKey(key, 11) ? new int[0] : ((NBTTagIntArray)this.tagMap.get(key)).getIntArray();
/*     */     }
/* 339 */     catch (ClassCastException classcastexception) {
/*     */       
/* 341 */       throw new ReportedException(createCrashReport(key, 11, classcastexception));
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public NBTTagCompound getCompoundTag(String key) {
/*     */     try {
/* 353 */       return !hasKey(key, 10) ? new NBTTagCompound() : (NBTTagCompound)this.tagMap.get(key);
/*     */     }
/* 355 */     catch (ClassCastException classcastexception) {
/*     */       
/* 357 */       throw new ReportedException(createCrashReport(key, 10, classcastexception));
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public NBTTagList getTagList(String key, int type) {
/*     */     try {
/* 368 */       if (getTagId(key) != 9)
/*     */       {
/* 370 */         return new NBTTagList();
/*     */       }
/*     */ 
/*     */       
/* 374 */       NBTTagList nbttaglist = (NBTTagList)this.tagMap.get(key);
/* 375 */       return (nbttaglist.tagCount() > 0 && nbttaglist.getTagType() != type) ? new NBTTagList() : nbttaglist;
/*     */     
/*     */     }
/* 378 */     catch (ClassCastException classcastexception) {
/*     */       
/* 380 */       throw new ReportedException(createCrashReport(key, 9, classcastexception));
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean getBoolean(String key) {
/* 390 */     return (getByte(key) != 0);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void removeTag(String key) {
/* 398 */     this.tagMap.remove(key);
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/* 403 */     StringBuilder stringbuilder = new StringBuilder("{");
/*     */     
/* 405 */     for (Map.Entry<String, NBTBase> entry : this.tagMap.entrySet()) {
/*     */       
/* 407 */       if (stringbuilder.length() != 1)
/*     */       {
/* 409 */         stringbuilder.append(',');
/*     */       }
/*     */       
/* 412 */       stringbuilder.append(entry.getKey()).append(':').append(entry.getValue());
/*     */     } 
/*     */     
/* 415 */     return stringbuilder.append('}').toString();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean hasNoTags() {
/* 423 */     return this.tagMap.isEmpty();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private CrashReport createCrashReport(final String key, final int expectedType, ClassCastException ex) {
/* 431 */     CrashReport crashreport = CrashReport.makeCrashReport(ex, "Reading NBT data");
/* 432 */     CrashReportCategory crashreportcategory = crashreport.makeCategoryDepth("Corrupt NBT tag", 1);
/* 433 */     crashreportcategory.addCrashSectionCallable("Tag type found", new Callable<String>()
/*     */         {
/*     */           public String call() {
/* 436 */             return NBTBase.NBT_TYPES[((NBTBase)NBTTagCompound.this.tagMap.get(key)).getId()];
/*     */           }
/*     */         });
/* 439 */     crashreportcategory.addCrashSectionCallable("Tag type expected", new Callable<String>()
/*     */         {
/*     */           public String call() {
/* 442 */             return NBTBase.NBT_TYPES[expectedType];
/*     */           }
/*     */         });
/* 445 */     crashreportcategory.addCrashSection("Tag name", key);
/* 446 */     return crashreport;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public NBTBase copy() {
/* 454 */     NBTTagCompound nbttagcompound = new NBTTagCompound();
/*     */     
/* 456 */     for (Map.Entry<String, NBTBase> entry : this.tagMap.entrySet())
/*     */     {
/* 458 */       nbttagcompound.setTag(entry.getKey(), ((NBTBase)entry.getValue()).copy());
/*     */     }
/*     */     
/* 461 */     return nbttagcompound;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object p_equals_1_) {
/* 466 */     if (super.equals(p_equals_1_)) {
/*     */       
/* 468 */       NBTTagCompound nbttagcompound = (NBTTagCompound)p_equals_1_;
/* 469 */       return this.tagMap.entrySet().equals(nbttagcompound.tagMap.entrySet());
/*     */     } 
/*     */ 
/*     */     
/* 473 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 479 */     return super.hashCode() ^ this.tagMap.hashCode();
/*     */   }
/*     */ 
/*     */   
/*     */   private static void writeEntry(String name, NBTBase data, DataOutput output) throws IOException {
/* 484 */     output.writeByte(data.getId());
/*     */     
/* 486 */     if (data.getId() != 0) {
/*     */       
/* 488 */       output.writeUTF(name);
/* 489 */       data.write(output);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private static byte readType(DataInput input, NBTSizeTracker sizeTracker) throws IOException {
/* 495 */     return input.readByte();
/*     */   }
/*     */ 
/*     */   
/*     */   private static String readKey(DataInput input, NBTSizeTracker sizeTracker) throws IOException {
/* 500 */     return input.readUTF();
/*     */   }
/*     */   
/*     */   static NBTBase readNBT(byte id, String key, DataInput input, int depth, NBTSizeTracker sizeTracker) {
/* 504 */     NBTBase nbtbase = NBTBase.createNewByType(id);
/*     */ 
/*     */     
/*     */     try {
/* 508 */       nbtbase.read(input, depth, sizeTracker);
/* 509 */       return nbtbase;
/*     */     }
/* 511 */     catch (IOException ioexception) {
/*     */       
/* 513 */       CrashReport crashreport = CrashReport.makeCrashReport(ioexception, "Loading NBT data");
/* 514 */       CrashReportCategory crashreportcategory = crashreport.makeCategory("NBT Tag");
/* 515 */       crashreportcategory.addCrashSection("Tag name", key);
/* 516 */       crashreportcategory.addCrashSection("Tag type", Byte.valueOf(id));
/* 517 */       throw new ReportedException(crashreport);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void merge(NBTTagCompound other) {
/* 527 */     for (Map.Entry<String, NBTBase> entry : other.tagMap.entrySet()) {
/*     */       
/* 529 */       String s = entry.getKey();
/* 530 */       NBTBase nbtbase = entry.getValue();
/*     */       
/* 532 */       if (nbtbase.getId() == 10) {
/*     */         
/* 534 */         if (hasKey(s, 10)) {
/*     */           
/* 536 */           NBTTagCompound nbttagcompound = getCompoundTag(s);
/* 537 */           nbttagcompound.merge((NBTTagCompound)nbtbase);
/*     */           
/*     */           continue;
/*     */         } 
/* 541 */         setTag(s, nbtbase.copy());
/*     */         
/*     */         continue;
/*     */       } 
/*     */       
/* 546 */       setTag(s, nbtbase.copy());
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\minecraft\nbt\NBTTagCompound.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */