/*     */ package net.minecraft.nbt;
/*     */ 
/*     */ import com.google.common.collect.Lists;
/*     */ import java.io.DataInput;
/*     */ import java.io.DataOutput;
/*     */ import java.io.IOException;
/*     */ import java.util.List;
/*     */ import org.apache.logging.log4j.LogManager;
/*     */ import org.apache.logging.log4j.Logger;
/*     */ 
/*     */ public class NBTTagList
/*     */   extends NBTBase
/*     */ {
/*  14 */   private static final Logger LOGGER = LogManager.getLogger();
/*  15 */   private List<NBTBase> tagList = Lists.newArrayList();
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  20 */   private byte tagType = 0;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   void write(DataOutput output) throws IOException {
/*  27 */     if (!this.tagList.isEmpty()) {
/*     */       
/*  29 */       this.tagType = ((NBTBase)this.tagList.get(0)).getId();
/*     */     }
/*     */     else {
/*     */       
/*  33 */       this.tagType = 0;
/*     */     } 
/*     */     
/*  36 */     output.writeByte(this.tagType);
/*  37 */     output.writeInt(this.tagList.size());
/*     */     
/*  39 */     for (int i = 0; i < this.tagList.size(); i++)
/*     */     {
/*  41 */       ((NBTBase)this.tagList.get(i)).write(output);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   void read(DataInput input, int depth, NBTSizeTracker sizeTracker) throws IOException {
/*  47 */     sizeTracker.read(296L);
/*     */     
/*  49 */     if (depth > 512)
/*     */     {
/*  51 */       throw new RuntimeException("Tried to read NBT tag with too high complexity, depth > 512");
/*     */     }
/*     */ 
/*     */     
/*  55 */     this.tagType = input.readByte();
/*  56 */     int i = input.readInt();
/*     */     
/*  58 */     if (this.tagType == 0 && i > 0)
/*     */     {
/*  60 */       throw new RuntimeException("Missing type on ListTag");
/*     */     }
/*     */ 
/*     */     
/*  64 */     sizeTracker.read(32L * i);
/*  65 */     this.tagList = Lists.newArrayListWithCapacity(i);
/*     */     
/*  67 */     for (int j = 0; j < i; j++) {
/*     */       
/*  69 */       NBTBase nbtbase = NBTBase.createNewByType(this.tagType);
/*  70 */       nbtbase.read(input, depth + 1, sizeTracker);
/*  71 */       this.tagList.add(nbtbase);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public byte getId() {
/*  82 */     return 9;
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/*  87 */     StringBuilder stringbuilder = new StringBuilder("[");
/*     */     
/*  89 */     for (int i = 0; i < this.tagList.size(); i++) {
/*     */       
/*  91 */       if (i != 0)
/*     */       {
/*  93 */         stringbuilder.append(',');
/*     */       }
/*     */       
/*  96 */       stringbuilder.append(i).append(':').append(this.tagList.get(i));
/*     */     } 
/*     */     
/*  99 */     return stringbuilder.append(']').toString();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void appendTag(NBTBase nbt) {
/* 108 */     if (nbt.getId() == 0) {
/*     */       
/* 110 */       LOGGER.warn("Invalid TagEnd added to ListTag");
/*     */     }
/*     */     else {
/*     */       
/* 114 */       if (this.tagType == 0) {
/*     */         
/* 116 */         this.tagType = nbt.getId();
/*     */       }
/* 118 */       else if (this.tagType != nbt.getId()) {
/*     */         
/* 120 */         LOGGER.warn("Adding mismatching tag types to tag list");
/*     */         
/*     */         return;
/*     */       } 
/* 124 */       this.tagList.add(nbt);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void set(int idx, NBTBase nbt) {
/* 133 */     if (nbt.getId() == 0) {
/*     */       
/* 135 */       LOGGER.warn("Invalid TagEnd added to ListTag");
/*     */     }
/* 137 */     else if (idx >= 0 && idx < this.tagList.size()) {
/*     */       
/* 139 */       if (this.tagType == 0) {
/*     */         
/* 141 */         this.tagType = nbt.getId();
/*     */       }
/* 143 */       else if (this.tagType != nbt.getId()) {
/*     */         
/* 145 */         LOGGER.warn("Adding mismatching tag types to tag list");
/*     */         
/*     */         return;
/*     */       } 
/* 149 */       this.tagList.set(idx, nbt);
/*     */     }
/*     */     else {
/*     */       
/* 153 */       LOGGER.warn("index out of bounds to set tag in tag list");
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public NBTBase removeTag(int i) {
/* 162 */     return this.tagList.remove(i);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean hasNoTags() {
/* 170 */     return this.tagList.isEmpty();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public NBTTagCompound getCompoundTagAt(int i) {
/* 178 */     if (i >= 0 && i < this.tagList.size()) {
/*     */       
/* 180 */       NBTBase nbtbase = this.tagList.get(i);
/* 181 */       return (nbtbase.getId() == 10) ? (NBTTagCompound)nbtbase : new NBTTagCompound();
/*     */     } 
/*     */ 
/*     */     
/* 185 */     return new NBTTagCompound();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int[] getIntArrayAt(int i) {
/* 191 */     if (i >= 0 && i < this.tagList.size()) {
/*     */       
/* 193 */       NBTBase nbtbase = this.tagList.get(i);
/* 194 */       return (nbtbase.getId() == 11) ? ((NBTTagIntArray)nbtbase).getIntArray() : new int[0];
/*     */     } 
/*     */ 
/*     */     
/* 198 */     return new int[0];
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public double getDoubleAt(int i) {
/* 204 */     if (i >= 0 && i < this.tagList.size()) {
/*     */       
/* 206 */       NBTBase nbtbase = this.tagList.get(i);
/* 207 */       return (nbtbase.getId() == 6) ? ((NBTTagDouble)nbtbase).getDouble() : 0.0D;
/*     */     } 
/*     */ 
/*     */     
/* 211 */     return 0.0D;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public float getFloatAt(int i) {
/* 217 */     if (i >= 0 && i < this.tagList.size()) {
/*     */       
/* 219 */       NBTBase nbtbase = this.tagList.get(i);
/* 220 */       return (nbtbase.getId() == 5) ? ((NBTTagFloat)nbtbase).getFloat() : 0.0F;
/*     */     } 
/*     */ 
/*     */     
/* 224 */     return 0.0F;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getStringTagAt(int i) {
/* 233 */     if (i >= 0 && i < this.tagList.size()) {
/*     */       
/* 235 */       NBTBase nbtbase = this.tagList.get(i);
/* 236 */       return (nbtbase.getId() == 8) ? nbtbase.getString() : nbtbase.toString();
/*     */     } 
/*     */ 
/*     */     
/* 240 */     return "";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public NBTBase get(int idx) {
/* 249 */     return (idx >= 0 && idx < this.tagList.size()) ? this.tagList.get(idx) : new NBTTagEnd();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int tagCount() {
/* 257 */     return this.tagList.size();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public NBTBase copy() {
/* 265 */     NBTTagList nbttaglist = new NBTTagList();
/* 266 */     nbttaglist.tagType = this.tagType;
/*     */     
/* 268 */     for (NBTBase nbtbase : this.tagList) {
/*     */       
/* 270 */       NBTBase nbtbase1 = nbtbase.copy();
/* 271 */       nbttaglist.tagList.add(nbtbase1);
/*     */     } 
/*     */     
/* 274 */     return nbttaglist;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object p_equals_1_) {
/* 279 */     if (super.equals(p_equals_1_)) {
/*     */       
/* 281 */       NBTTagList nbttaglist = (NBTTagList)p_equals_1_;
/*     */       
/* 283 */       if (this.tagType == nbttaglist.tagType)
/*     */       {
/* 285 */         return this.tagList.equals(nbttaglist.tagList);
/*     */       }
/*     */     } 
/*     */     
/* 289 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 294 */     return super.hashCode() ^ this.tagList.hashCode();
/*     */   }
/*     */ 
/*     */   
/*     */   public int getTagType() {
/* 299 */     return this.tagType;
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\minecraft\nbt\NBTTagList.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */