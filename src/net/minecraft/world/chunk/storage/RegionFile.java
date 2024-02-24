/*     */ package net.minecraft.world.chunk.storage;
/*     */ import com.google.common.collect.Lists;
/*     */ import java.io.BufferedInputStream;
/*     */ import java.io.ByteArrayInputStream;
/*     */ import java.io.DataInputStream;
/*     */ import java.io.DataOutputStream;
/*     */ import java.io.File;
/*     */ import java.io.IOException;
/*     */ import java.io.RandomAccessFile;
/*     */ import java.util.zip.GZIPInputStream;
/*     */ import java.util.zip.InflaterInputStream;
/*     */ 
/*     */ public class RegionFile {
/*  14 */   private static final byte[] emptySector = new byte[4096];
/*     */   private final File fileName;
/*     */   private RandomAccessFile dataFile;
/*  17 */   private final int[] offsets = new int[1024];
/*  18 */   private final int[] chunkTimestamps = new int[1024];
/*     */   
/*     */   private List<Boolean> sectorFree;
/*     */   
/*     */   private int sizeDelta;
/*     */   
/*     */   private long lastModified;
/*     */   
/*     */   public RegionFile(File fileNameIn) {
/*  27 */     this.fileName = fileNameIn;
/*  28 */     this.sizeDelta = 0;
/*     */ 
/*     */     
/*     */     try {
/*  32 */       if (fileNameIn.exists())
/*     */       {
/*  34 */         this.lastModified = fileNameIn.lastModified();
/*     */       }
/*     */       
/*  37 */       this.dataFile = new RandomAccessFile(fileNameIn, "rw");
/*     */       
/*  39 */       if (this.dataFile.length() < 4096L) {
/*     */         
/*  41 */         for (int i = 0; i < 1024; i++)
/*     */         {
/*  43 */           this.dataFile.writeInt(0);
/*     */         }
/*     */         
/*  46 */         for (int i1 = 0; i1 < 1024; i1++)
/*     */         {
/*  48 */           this.dataFile.writeInt(0);
/*     */         }
/*     */         
/*  51 */         this.sizeDelta += 8192;
/*     */       } 
/*     */       
/*  54 */       if ((this.dataFile.length() & 0xFFFL) != 0L)
/*     */       {
/*  56 */         for (int j1 = 0; j1 < (this.dataFile.length() & 0xFFFL); j1++)
/*     */         {
/*  58 */           this.dataFile.write(0);
/*     */         }
/*     */       }
/*     */       
/*  62 */       int k1 = (int)this.dataFile.length() / 4096;
/*  63 */       this.sectorFree = Lists.newArrayListWithCapacity(k1);
/*     */       
/*  65 */       for (int j = 0; j < k1; j++)
/*     */       {
/*  67 */         this.sectorFree.add(Boolean.valueOf(true));
/*     */       }
/*     */       
/*  70 */       this.sectorFree.set(0, Boolean.valueOf(false));
/*  71 */       this.sectorFree.set(1, Boolean.valueOf(false));
/*  72 */       this.dataFile.seek(0L);
/*     */       
/*  74 */       for (int l1 = 0; l1 < 1024; l1++) {
/*     */         
/*  76 */         int k = this.dataFile.readInt();
/*  77 */         this.offsets[l1] = k;
/*     */         
/*  79 */         if (k != 0 && (k >> 8) + (k & 0xFF) <= this.sectorFree.size())
/*     */         {
/*  81 */           for (int l = 0; l < (k & 0xFF); l++)
/*     */           {
/*  83 */             this.sectorFree.set((k >> 8) + l, Boolean.valueOf(false));
/*     */           }
/*     */         }
/*     */       } 
/*     */       
/*  88 */       for (int i2 = 0; i2 < 1024; i2++)
/*     */       {
/*  90 */         int j2 = this.dataFile.readInt();
/*  91 */         this.chunkTimestamps[i2] = j2;
/*     */       }
/*     */     
/*  94 */     } catch (IOException ioexception) {
/*     */       
/*  96 */       ioexception.printStackTrace();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public synchronized DataInputStream getChunkDataInputStream(int x, int z) {
/* 105 */     if (outOfBounds(x, z))
/*     */     {
/* 107 */       return null;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     try {
/* 113 */       int i = getOffset(x, z);
/*     */       
/* 115 */       if (i == 0)
/*     */       {
/* 117 */         return null;
/*     */       }
/*     */ 
/*     */       
/* 121 */       int j = i >> 8;
/* 122 */       int k = i & 0xFF;
/*     */       
/* 124 */       if (j + k > this.sectorFree.size())
/*     */       {
/* 126 */         return null;
/*     */       }
/*     */ 
/*     */       
/* 130 */       this.dataFile.seek((j << 12));
/* 131 */       int l = this.dataFile.readInt();
/*     */       
/* 133 */       if (l > 4096 * k)
/*     */       {
/* 135 */         return null;
/*     */       }
/* 137 */       if (l <= 0)
/*     */       {
/* 139 */         return null;
/*     */       }
/*     */ 
/*     */       
/* 143 */       byte b0 = this.dataFile.readByte();
/*     */       
/* 145 */       if (b0 == 1) {
/*     */         
/* 147 */         byte[] abyte1 = new byte[l - 1];
/* 148 */         this.dataFile.read(abyte1);
/* 149 */         return new DataInputStream(new BufferedInputStream(new GZIPInputStream(new ByteArrayInputStream(abyte1))));
/*     */       } 
/* 151 */       if (b0 == 2) {
/*     */         
/* 153 */         byte[] abyte = new byte[l - 1];
/* 154 */         this.dataFile.read(abyte);
/* 155 */         return new DataInputStream(new BufferedInputStream(new InflaterInputStream(new ByteArrayInputStream(abyte))));
/*     */       } 
/*     */ 
/*     */       
/* 159 */       return null;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     }
/* 165 */     catch (IOException var9) {
/*     */       
/* 167 */       return null;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public DataOutputStream getChunkDataOutputStream(int x, int z) {
/* 177 */     return outOfBounds(x, z) ? null : new DataOutputStream(new DeflaterOutputStream(new ChunkBuffer(x, z)));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected synchronized void write(int x, int z, byte[] data, int length) {
/*     */     try {
/* 187 */       int i = getOffset(x, z);
/* 188 */       int j = i >> 8;
/* 189 */       int k = i & 0xFF;
/* 190 */       int l = (length + 5) / 4096 + 1;
/*     */       
/* 192 */       if (l >= 256) {
/*     */         return;
/*     */       }
/*     */ 
/*     */       
/* 197 */       if (j != 0 && k == l) {
/*     */         
/* 199 */         write(j, data, length);
/*     */       }
/*     */       else {
/*     */         
/* 203 */         for (int i1 = 0; i1 < k; i1++)
/*     */         {
/* 205 */           this.sectorFree.set(j + i1, Boolean.valueOf(true));
/*     */         }
/*     */         
/* 208 */         int l1 = this.sectorFree.indexOf(Boolean.valueOf(true));
/* 209 */         int j1 = 0;
/*     */         
/* 211 */         if (l1 != -1)
/*     */         {
/* 213 */           for (int k1 = l1; k1 < this.sectorFree.size(); k1++) {
/*     */             
/* 215 */             if (j1 != 0) {
/*     */               
/* 217 */               if (((Boolean)this.sectorFree.get(k1)).booleanValue())
/*     */               {
/* 219 */                 j1++;
/*     */               }
/*     */               else
/*     */               {
/* 223 */                 j1 = 0;
/*     */               }
/*     */             
/* 226 */             } else if (((Boolean)this.sectorFree.get(k1)).booleanValue()) {
/*     */               
/* 228 */               l1 = k1;
/* 229 */               j1 = 1;
/*     */             } 
/*     */             
/* 232 */             if (j1 >= l) {
/*     */               break;
/*     */             }
/*     */           } 
/*     */         }
/*     */ 
/*     */         
/* 239 */         if (j1 >= l) {
/*     */           
/* 241 */           j = l1;
/* 242 */           setOffset(x, z, l1 << 8 | l);
/*     */           
/* 244 */           for (int j2 = 0; j2 < l; j2++)
/*     */           {
/* 246 */             this.sectorFree.set(j + j2, Boolean.valueOf(false));
/*     */           }
/*     */           
/* 249 */           write(j, data, length);
/*     */         }
/*     */         else {
/*     */           
/* 253 */           this.dataFile.seek(this.dataFile.length());
/* 254 */           j = this.sectorFree.size();
/*     */           
/* 256 */           for (int i2 = 0; i2 < l; i2++) {
/*     */             
/* 258 */             this.dataFile.write(emptySector);
/* 259 */             this.sectorFree.add(Boolean.valueOf(false));
/*     */           } 
/*     */           
/* 262 */           this.sizeDelta += 4096 * l;
/* 263 */           write(j, data, length);
/* 264 */           setOffset(x, z, j << 8 | l);
/*     */         } 
/*     */       } 
/*     */       
/* 268 */       setChunkTimestamp(x, z, (int)(MinecraftServer.getCurrentTimeMillis() / 1000L));
/*     */     }
/* 270 */     catch (IOException ioexception) {
/*     */       
/* 272 */       ioexception.printStackTrace();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void write(int sectorNumber, byte[] data, int length) throws IOException {
/* 281 */     this.dataFile.seek((sectorNumber << 12));
/* 282 */     this.dataFile.writeInt(length + 1);
/* 283 */     this.dataFile.writeByte(2);
/* 284 */     this.dataFile.write(data, 0, length);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean outOfBounds(int x, int z) {
/* 292 */     return (x < 0 || x >= 32 || z < 0 || z >= 32);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private int getOffset(int x, int z) {
/* 300 */     return this.offsets[x + (z << 5)];
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isChunkSaved(int x, int z) {
/* 308 */     return (getOffset(x, z) != 0);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void setOffset(int x, int z, int offset) throws IOException {
/* 316 */     this.offsets[x + (z << 5)] = offset;
/* 317 */     this.dataFile.seek((x + (z << 5) << 2));
/* 318 */     this.dataFile.writeInt(offset);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void setChunkTimestamp(int x, int z, int timestamp) throws IOException {
/* 326 */     this.chunkTimestamps[x + (z << 5)] = timestamp;
/* 327 */     this.dataFile.seek((4096 + (x + (z << 5) << 2)));
/* 328 */     this.dataFile.writeInt(timestamp);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void close() throws IOException {
/* 336 */     if (this.dataFile != null)
/*     */     {
/* 338 */       this.dataFile.close();
/*     */     }
/*     */   }
/*     */   
/*     */   class ChunkBuffer
/*     */     extends ByteArrayOutputStream
/*     */   {
/*     */     private final int chunkX;
/*     */     private final int chunkZ;
/*     */     
/*     */     public ChunkBuffer(int x, int z) {
/* 349 */       super(8096);
/* 350 */       this.chunkX = x;
/* 351 */       this.chunkZ = z;
/*     */     }
/*     */     
/*     */     public void close() {
/* 355 */       RegionFile.this.write(this.chunkX, this.chunkZ, this.buf, this.count);
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\minecraft\world\chunk\storage\RegionFile.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */