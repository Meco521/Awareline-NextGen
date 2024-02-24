/*     */ package net.optifine.util;
/*     */ import net.minecraft.src.Config;
/*     */ import net.minecraft.tileentity.TileEntity;
/*     */ import net.minecraft.tileentity.TileEntityChest;
/*     */ import net.minecraft.tileentity.TileEntityDispenser;
/*     */ import net.minecraft.tileentity.TileEntityHopper;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.world.IBlockAccess;
/*     */ import net.minecraft.world.IWorldNameable;
/*     */ import net.optifine.reflect.Reflector;
/*     */ 
/*     */ public class TileEntityUtils {
/*     */   public static String getTileEntityName(IBlockAccess blockAccess, BlockPos blockPos) {
/*  14 */     TileEntity tileentity = blockAccess.getTileEntity(blockPos);
/*  15 */     return getTileEntityName(tileentity);
/*     */   }
/*     */ 
/*     */   
/*     */   public static String getTileEntityName(TileEntity te) {
/*  20 */     if (!(te instanceof IWorldNameable))
/*     */     {
/*  22 */       return null;
/*     */     }
/*     */ 
/*     */     
/*  26 */     IWorldNameable iworldnameable = (IWorldNameable)te;
/*  27 */     updateTileEntityName(te);
/*  28 */     return !iworldnameable.hasCustomName() ? null : iworldnameable.getName();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static void updateTileEntityName(TileEntity te) {
/*  34 */     BlockPos blockpos = te.getPos();
/*  35 */     String s = getTileEntityRawName(te);
/*     */     
/*  37 */     if (s == null) {
/*     */       
/*  39 */       String s1 = getServerTileEntityRawName(blockpos);
/*  40 */       s1 = Config.normalize(s1);
/*  41 */       setTileEntityRawName(te, s1);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public static String getServerTileEntityRawName(BlockPos blockPos) {
/*  47 */     TileEntity tileentity = IntegratedServerUtils.getTileEntity(blockPos);
/*  48 */     return (tileentity == null) ? null : getTileEntityRawName(tileentity);
/*     */   }
/*     */ 
/*     */   
/*     */   public static String getTileEntityRawName(TileEntity te) {
/*  53 */     if (te instanceof net.minecraft.tileentity.TileEntityBeacon)
/*     */     {
/*  55 */       return (String)Reflector.getFieldValue(te, Reflector.TileEntityBeacon_customName);
/*     */     }
/*  57 */     if (te instanceof net.minecraft.tileentity.TileEntityBrewingStand)
/*     */     {
/*  59 */       return (String)Reflector.getFieldValue(te, Reflector.TileEntityBrewingStand_customName);
/*     */     }
/*  61 */     if (te instanceof net.minecraft.tileentity.TileEntityEnchantmentTable)
/*     */     {
/*  63 */       return (String)Reflector.getFieldValue(te, Reflector.TileEntityEnchantmentTable_customName);
/*     */     }
/*  65 */     if (te instanceof net.minecraft.tileentity.TileEntityFurnace)
/*     */     {
/*  67 */       return (String)Reflector.getFieldValue(te, Reflector.TileEntityFurnace_customName);
/*     */     }
/*     */ 
/*     */     
/*  71 */     if (te instanceof IWorldNameable) {
/*     */       
/*  73 */       IWorldNameable iworldnameable = (IWorldNameable)te;
/*     */       
/*  75 */       if (iworldnameable.hasCustomName())
/*     */       {
/*  77 */         return iworldnameable.getName();
/*     */       }
/*     */     } 
/*     */     
/*  81 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean setTileEntityRawName(TileEntity te, String name) {
/*  87 */     if (te instanceof net.minecraft.tileentity.TileEntityBeacon)
/*     */     {
/*  89 */       return Reflector.setFieldValue(te, Reflector.TileEntityBeacon_customName, name);
/*     */     }
/*  91 */     if (te instanceof net.minecraft.tileentity.TileEntityBrewingStand)
/*     */     {
/*  93 */       return Reflector.setFieldValue(te, Reflector.TileEntityBrewingStand_customName, name);
/*     */     }
/*  95 */     if (te instanceof net.minecraft.tileentity.TileEntityEnchantmentTable)
/*     */     {
/*  97 */       return Reflector.setFieldValue(te, Reflector.TileEntityEnchantmentTable_customName, name);
/*     */     }
/*  99 */     if (te instanceof net.minecraft.tileentity.TileEntityFurnace)
/*     */     {
/* 101 */       return Reflector.setFieldValue(te, Reflector.TileEntityFurnace_customName, name);
/*     */     }
/* 103 */     if (te instanceof TileEntityChest) {
/*     */       
/* 105 */       ((TileEntityChest)te).setCustomName(name);
/* 106 */       return true;
/*     */     } 
/* 108 */     if (te instanceof TileEntityDispenser) {
/*     */       
/* 110 */       ((TileEntityDispenser)te).setCustomName(name);
/* 111 */       return true;
/*     */     } 
/* 113 */     if (te instanceof TileEntityHopper) {
/*     */       
/* 115 */       ((TileEntityHopper)te).setCustomName(name);
/* 116 */       return true;
/*     */     } 
/*     */ 
/*     */     
/* 120 */     return false;
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\optifin\\util\TileEntityUtils.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */