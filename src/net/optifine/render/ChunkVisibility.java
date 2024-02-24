/*     */ package net.optifine.render;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.ConcurrentModificationException;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.tileentity.TileEntity;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.util.ClassInheritanceMultiMap;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ import net.minecraft.util.MathHelper;
/*     */ import net.minecraft.world.World;
/*     */ import net.minecraft.world.chunk.Chunk;
/*     */ import net.minecraft.world.chunk.storage.ExtendedBlockStorage;
/*     */ 
/*     */ 
/*     */ public class ChunkVisibility
/*     */ {
/*     */   public static final int MASK_FACINGS = 63;
/*  21 */   public static final EnumFacing[][] enumFacingArrays = makeEnumFacingArrays(false);
/*  22 */   public static final EnumFacing[][] enumFacingOppositeArrays = makeEnumFacingArrays(true);
/*  23 */   private static int counter = 0;
/*  24 */   private static int iMaxStatic = -1;
/*  25 */   private static int iMaxStaticFinal = 16;
/*  26 */   private static World worldLast = null;
/*  27 */   private static int pcxLast = Integer.MIN_VALUE;
/*  28 */   private static int pczLast = Integer.MIN_VALUE;
/*     */ 
/*     */   
/*     */   public static int getMaxChunkY(World world, Entity viewEntity, int renderDistanceChunks) {
/*  32 */     int i = MathHelper.floor_double(viewEntity.posX) >> 4;
/*  33 */     int j = MathHelper.floor_double(viewEntity.posY) >> 4;
/*  34 */     int k = MathHelper.floor_double(viewEntity.posZ) >> 4;
/*  35 */     Chunk chunk = world.getChunkFromChunkCoords(i, k);
/*  36 */     int l = i - renderDistanceChunks;
/*  37 */     int i1 = i + renderDistanceChunks;
/*  38 */     int j1 = k - renderDistanceChunks;
/*  39 */     int k1 = k + renderDistanceChunks;
/*     */     
/*  41 */     if (world != worldLast || i != pcxLast || k != pczLast) {
/*     */       
/*  43 */       counter = 0;
/*  44 */       iMaxStaticFinal = 16;
/*  45 */       worldLast = world;
/*  46 */       pcxLast = i;
/*  47 */       pczLast = k;
/*     */     } 
/*     */     
/*  50 */     if (counter == 0)
/*     */     {
/*  52 */       iMaxStatic = -1;
/*     */     }
/*     */     
/*  55 */     int l1 = iMaxStatic;
/*     */     
/*  57 */     switch (counter) {
/*     */       
/*     */       case 0:
/*  60 */         i1 = i;
/*  61 */         k1 = k;
/*     */         break;
/*     */       
/*     */       case 1:
/*  65 */         l = i;
/*  66 */         k1 = k;
/*     */         break;
/*     */       
/*     */       case 2:
/*  70 */         i1 = i;
/*  71 */         j1 = k;
/*     */         break;
/*     */       
/*     */       case 3:
/*  75 */         l = i;
/*  76 */         j1 = k;
/*     */         break;
/*     */     } 
/*  79 */     for (int i2 = l; i2 < i1; i2++) {
/*     */       
/*  81 */       for (int j2 = j1; j2 < k1; j2++) {
/*     */         
/*  83 */         Chunk chunk1 = world.getChunkFromChunkCoords(i2, j2);
/*     */         
/*  85 */         if (!chunk1.isEmpty()) {
/*     */           
/*  87 */           ExtendedBlockStorage[] aextendedblockstorage = chunk1.getBlockStorageArray();
/*     */           
/*  89 */           for (int k2 = aextendedblockstorage.length - 1; k2 > l1; k2--) {
/*     */             
/*  91 */             ExtendedBlockStorage extendedblockstorage = aextendedblockstorage[k2];
/*     */             
/*  93 */             if (extendedblockstorage != null && !extendedblockstorage.isEmpty()) {
/*     */               
/*  95 */               if (k2 > l1)
/*     */               {
/*  97 */                 l1 = k2;
/*     */               }
/*     */ 
/*     */               
/*     */               break;
/*     */             } 
/*     */           } 
/*     */           
/*     */           try {
/* 106 */             Map<BlockPos, TileEntity> map = chunk1.getTileEntityMap();
/*     */             
/* 108 */             if (!map.isEmpty())
/*     */             {
/* 110 */               for (BlockPos blockpos : map.keySet())
/*     */               {
/* 112 */                 int l2 = blockpos.getY() >> 4;
/*     */                 
/* 114 */                 if (l2 > l1)
/*     */                 {
/* 116 */                   l1 = l2;
/*     */                 }
/*     */               }
/*     */             
/*     */             }
/* 121 */           } catch (ConcurrentModificationException concurrentModificationException) {}
/*     */ 
/*     */ 
/*     */ 
/*     */           
/* 126 */           ClassInheritanceMultiMap[] arrayOfClassInheritanceMultiMap = chunk1.getEntityLists();
/*     */           
/* 128 */           for (int i3 = arrayOfClassInheritanceMultiMap.length - 1; i3 > l1; i3--) {
/*     */             
/* 130 */             ClassInheritanceMultiMap<Entity> classinheritancemultimap1 = arrayOfClassInheritanceMultiMap[i3];
/*     */             
/* 132 */             if (!classinheritancemultimap1.isEmpty() && (chunk1 != chunk || i3 != j || classinheritancemultimap1.size() != 1)) {
/*     */               
/* 134 */               if (i3 > l1)
/*     */               {
/* 136 */                 l1 = i3;
/*     */               }
/*     */               
/*     */               break;
/*     */             } 
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 146 */     if (counter < 3) {
/*     */       
/* 148 */       iMaxStatic = l1;
/* 149 */       l1 = iMaxStaticFinal;
/*     */     }
/*     */     else {
/*     */       
/* 153 */       iMaxStaticFinal = l1;
/* 154 */       iMaxStatic = -1;
/*     */     } 
/*     */     
/* 157 */     counter = (counter + 1) % 4;
/* 158 */     return l1 << 4;
/*     */   }
/*     */ 
/*     */   
/*     */   public static boolean isFinished() {
/* 163 */     return (counter == 0);
/*     */   }
/*     */ 
/*     */   
/*     */   private static EnumFacing[][] makeEnumFacingArrays(boolean opposite) {
/* 168 */     int i = 64;
/* 169 */     EnumFacing[][] aenumfacing = new EnumFacing[i][];
/*     */     
/* 171 */     for (int j = 0; j < i; j++) {
/*     */       
/* 173 */       List<EnumFacing> list = new ArrayList<>();
/*     */       
/* 175 */       for (int k = 0; k < EnumFacing.VALUES.length; k++) {
/*     */         
/* 177 */         EnumFacing enumfacing = EnumFacing.VALUES[k];
/* 178 */         EnumFacing enumfacing1 = opposite ? enumfacing.getOpposite() : enumfacing;
/* 179 */         int l = 1 << enumfacing1.ordinal();
/*     */         
/* 181 */         if ((j & l) != 0)
/*     */         {
/* 183 */           list.add(enumfacing);
/*     */         }
/*     */       } 
/*     */       
/* 187 */       EnumFacing[] aenumfacing1 = list.<EnumFacing>toArray(new EnumFacing[0]);
/* 188 */       aenumfacing[j] = aenumfacing1;
/*     */     } 
/*     */     
/* 191 */     return aenumfacing;
/*     */   }
/*     */ 
/*     */   
/*     */   public static EnumFacing[] getFacingsNotOpposite(int setDisabled) {
/* 196 */     int i = (setDisabled ^ 0xFFFFFFFF) & 0x3F;
/* 197 */     return enumFacingOppositeArrays[i];
/*     */   }
/*     */ 
/*     */   
/*     */   public static void reset() {
/* 202 */     worldLast = null;
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\optifine\render\ChunkVisibility.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */