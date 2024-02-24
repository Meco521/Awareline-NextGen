/*     */ package net.optifine;
/*     */ 
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.block.BlockAir;
/*     */ import net.minecraft.block.material.Material;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.client.settings.GameSettings;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.init.Blocks;
/*     */ import net.minecraft.src.Config;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.world.World;
/*     */ import net.minecraft.world.chunk.Chunk;
/*     */ import net.minecraft.world.chunk.IChunkProvider;
/*     */ 
/*     */ 
/*     */ public class ClearWater
/*     */ {
/*     */   public static void updateWaterOpacity(GameSettings settings, World world) {
/*  20 */     if (settings != null) {
/*     */       
/*  22 */       int i = 3;
/*     */       
/*  24 */       if (settings.ofClearWater)
/*     */       {
/*  26 */         i = 1;
/*     */       }
/*     */       
/*  29 */       BlockAir.setLightOpacity((Block)Blocks.water, i);
/*  30 */       BlockAir.setLightOpacity((Block)Blocks.flowing_water, i);
/*     */     } 
/*     */     
/*  33 */     if (world != null) {
/*     */       
/*  35 */       IChunkProvider ichunkprovider = world.getChunkProvider();
/*     */       
/*  37 */       if (ichunkprovider != null) {
/*     */         
/*  39 */         Entity entity = Config.getMinecraft().getRenderViewEntity();
/*     */         
/*  41 */         if (entity != null) {
/*     */           
/*  43 */           int j = (int)entity.posX / 16;
/*  44 */           int k = (int)entity.posZ / 16;
/*  45 */           int l = j - 512;
/*  46 */           int i1 = j + 512;
/*  47 */           int j1 = k - 512;
/*  48 */           int k1 = k + 512;
/*  49 */           int l1 = 0;
/*     */           
/*  51 */           for (int i2 = l; i2 < i1; i2++) {
/*     */             
/*  53 */             for (int j2 = j1; j2 < k1; j2++) {
/*     */               
/*  55 */               if (ichunkprovider.chunkExists(i2, j2)) {
/*     */                 
/*  57 */                 Chunk chunk = ichunkprovider.provideChunk(i2, j2);
/*     */                 
/*  59 */                 if (chunk != null && !(chunk instanceof net.minecraft.world.chunk.EmptyChunk)) {
/*     */                   
/*  61 */                   int k2 = i2 << 4;
/*  62 */                   int l2 = j2 << 4;
/*  63 */                   int i3 = k2 + 16;
/*  64 */                   int j3 = l2 + 16;
/*  65 */                   BlockPosM blockposm = new BlockPosM(0, 0, 0);
/*  66 */                   BlockPosM blockposm1 = new BlockPosM(0, 0, 0);
/*     */                   
/*  68 */                   for (int k3 = k2; k3 < i3; k3++) {
/*     */                     
/*  70 */                     for (int l3 = l2; l3 < j3; l3++) {
/*     */                       
/*  72 */                       blockposm.setXyz(k3, 0, l3);
/*  73 */                       BlockPos blockpos = world.getPrecipitationHeight(blockposm);
/*     */                       
/*  75 */                       for (int i4 = 0; i4 < blockpos.getY(); i4++) {
/*     */                         
/*  77 */                         blockposm1.setXyz(k3, i4, l3);
/*  78 */                         IBlockState iblockstate = world.getBlockState(blockposm1);
/*     */                         
/*  80 */                         if (iblockstate.getBlock().getMaterial() == Material.water) {
/*     */                           
/*  82 */                           world.markBlocksDirtyVertical(k3, l3, blockposm1.getY(), blockpos.getY());
/*  83 */                           l1++;
/*     */                           
/*     */                           break;
/*     */                         } 
/*     */                       } 
/*     */                     } 
/*     */                   } 
/*     */                 } 
/*     */               } 
/*     */             } 
/*     */           } 
/*  94 */           if (l1 > 0) {
/*     */             
/*  96 */             String s = "server";
/*     */             
/*  98 */             if (Config.isMinecraftThread())
/*     */             {
/* 100 */               s = "client";
/*     */             }
/*     */             
/* 103 */             Config.dbg("ClearWater (" + s + ") relighted " + l1 + " chunks");
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\optifine\ClearWater.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */