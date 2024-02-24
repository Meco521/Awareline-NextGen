/*     */ package net.minecraft.nbt;
/*     */ import java.io.DataInput;
/*     */ import java.io.DataInputStream;
/*     */ import java.io.DataOutput;
/*     */ import java.io.DataOutputStream;
/*     */ import java.io.File;
/*     */ import java.io.FileOutputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.OutputStream;
/*     */ import java.util.zip.GZIPInputStream;
/*     */ import net.minecraft.crash.CrashReport;
/*     */ import net.minecraft.crash.CrashReportCategory;
/*     */ 
/*     */ public class CompressedStreamTools {
/*     */   public static NBTTagCompound readCompressed(InputStream is) throws IOException {
/*     */     NBTTagCompound nbttagcompound;
/*  18 */     DataInputStream datainputstream = new DataInputStream(new BufferedInputStream(new GZIPInputStream(is)));
/*     */ 
/*     */ 
/*     */     
/*     */     try {
/*  23 */       nbttagcompound = read(datainputstream, NBTSizeTracker.INFINITE);
/*     */     }
/*     */     finally {
/*     */       
/*  27 */       datainputstream.close();
/*     */     } 
/*     */     
/*  30 */     return nbttagcompound;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void writeCompressed(NBTTagCompound p_74799_0_, OutputStream outputStream) throws IOException {
/*  38 */     DataOutputStream dataoutputstream = new DataOutputStream(new BufferedOutputStream(new GZIPOutputStream(outputStream)));
/*     */ 
/*     */     
/*     */     try {
/*  42 */       write(p_74799_0_, dataoutputstream);
/*     */     }
/*     */     finally {
/*     */       
/*  46 */       dataoutputstream.close();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public static void safeWrite(NBTTagCompound p_74793_0_, File p_74793_1_) throws IOException {
/*  52 */     File file1 = new File(p_74793_1_.getAbsolutePath() + "_tmp");
/*     */     
/*  54 */     if (file1.exists())
/*     */     {
/*  56 */       file1.delete();
/*     */     }
/*     */     
/*  59 */     write(p_74793_0_, file1);
/*     */     
/*  61 */     if (p_74793_1_.exists())
/*     */     {
/*  63 */       p_74793_1_.delete();
/*     */     }
/*     */     
/*  66 */     if (p_74793_1_.exists())
/*     */     {
/*  68 */       throw new IOException("Failed to delete " + p_74793_1_);
/*     */     }
/*     */ 
/*     */     
/*  72 */     file1.renameTo(p_74793_1_);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static void write(NBTTagCompound p_74795_0_, File p_74795_1_) throws IOException {
/*  78 */     DataOutputStream dataoutputstream = new DataOutputStream(new FileOutputStream(p_74795_1_));
/*     */ 
/*     */     
/*     */     try {
/*  82 */       write(p_74795_0_, dataoutputstream);
/*     */     }
/*     */     finally {
/*     */       
/*  86 */       dataoutputstream.close();
/*     */     } 
/*     */   }
/*     */   
/*     */   public static NBTTagCompound read(File p_74797_0_) throws IOException {
/*     */     NBTTagCompound nbttagcompound;
/*  92 */     if (!p_74797_0_.exists())
/*     */     {
/*  94 */       return null;
/*     */     }
/*     */ 
/*     */     
/*  98 */     DataInputStream datainputstream = new DataInputStream(new FileInputStream(p_74797_0_));
/*     */ 
/*     */ 
/*     */     
/*     */     try {
/* 103 */       nbttagcompound = read(datainputstream, NBTSizeTracker.INFINITE);
/*     */     }
/*     */     finally {
/*     */       
/* 107 */       datainputstream.close();
/*     */     } 
/*     */     
/* 110 */     return nbttagcompound;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static NBTTagCompound read(DataInputStream inputStream) throws IOException {
/* 119 */     return read(inputStream, NBTSizeTracker.INFINITE);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static NBTTagCompound read(DataInput p_152456_0_, NBTSizeTracker p_152456_1_) throws IOException {
/* 127 */     NBTBase nbtbase = func_152455_a(p_152456_0_, 0, p_152456_1_);
/*     */     
/* 129 */     if (nbtbase instanceof NBTTagCompound)
/*     */     {
/* 131 */       return (NBTTagCompound)nbtbase;
/*     */     }
/*     */ 
/*     */     
/* 135 */     throw new IOException("Root tag must be a named compound tag");
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static void write(NBTTagCompound p_74800_0_, DataOutput p_74800_1_) throws IOException {
/* 141 */     writeTag(p_74800_0_, p_74800_1_);
/*     */   }
/*     */ 
/*     */   
/*     */   private static void writeTag(NBTBase p_150663_0_, DataOutput p_150663_1_) throws IOException {
/* 146 */     p_150663_1_.writeByte(p_150663_0_.getId());
/*     */     
/* 148 */     if (p_150663_0_.getId() != 0) {
/*     */       
/* 150 */       p_150663_1_.writeUTF("");
/* 151 */       p_150663_0_.write(p_150663_1_);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private static NBTBase func_152455_a(DataInput p_152455_0_, int p_152455_1_, NBTSizeTracker p_152455_2_) throws IOException {
/* 157 */     byte b0 = p_152455_0_.readByte();
/*     */     
/* 159 */     if (b0 == 0)
/*     */     {
/* 161 */       return new NBTTagEnd();
/*     */     }
/*     */ 
/*     */     
/* 165 */     p_152455_0_.readUTF();
/* 166 */     NBTBase nbtbase = NBTBase.createNewByType(b0);
/*     */ 
/*     */     
/*     */     try {
/* 170 */       nbtbase.read(p_152455_0_, p_152455_1_, p_152455_2_);
/* 171 */       return nbtbase;
/*     */     }
/* 173 */     catch (IOException ioexception) {
/*     */       
/* 175 */       CrashReport crashreport = CrashReport.makeCrashReport(ioexception, "Loading NBT data");
/* 176 */       CrashReportCategory crashreportcategory = crashreport.makeCategory("NBT Tag");
/* 177 */       crashreportcategory.addCrashSection("Tag name", "[UNNAMED TAG]");
/* 178 */       crashreportcategory.addCrashSection("Tag type", Byte.valueOf(b0));
/* 179 */       throw new ReportedException(crashreport);
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\minecraft\nbt\CompressedStreamTools.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */