/*     */ package net.optifine;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import java.util.Properties;
/*     */ import net.minecraft.block.state.BlockStateBase;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.src.Config;
/*     */ import net.minecraft.util.EnumWorldBlockLayer;
/*     */ import net.optifine.config.ConnectedParser;
/*     */ import net.optifine.config.MatchBlock;
/*     */ import net.optifine.shaders.BlockAliases;
/*     */ import net.optifine.util.PropertiesOrdered;
/*     */ import net.optifine.util.ResUtils;
/*     */ 
/*     */ 
/*     */ public class CustomBlockLayers
/*     */ {
/*  19 */   private static EnumWorldBlockLayer[] renderLayers = null;
/*     */   
/*     */   public static boolean active = false;
/*     */   
/*     */   public static EnumWorldBlockLayer getRenderLayer(IBlockState blockState) {
/*  24 */     if (renderLayers == null)
/*     */     {
/*  26 */       return null;
/*     */     }
/*  28 */     if (blockState.getBlock().isOpaqueCube())
/*     */     {
/*  30 */       return null;
/*     */     }
/*  32 */     if (!(blockState instanceof BlockStateBase))
/*     */     {
/*  34 */       return null;
/*     */     }
/*     */ 
/*     */     
/*  38 */     BlockStateBase blockstatebase = (BlockStateBase)blockState;
/*  39 */     int i = blockstatebase.getBlockId();
/*  40 */     return (i > 0 && i < renderLayers.length) ? renderLayers[i] : null;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static void update() {
/*  46 */     renderLayers = null;
/*  47 */     active = false;
/*  48 */     List<EnumWorldBlockLayer> list = new ArrayList<>();
/*  49 */     String s = "optifine/block.properties";
/*  50 */     Properties properties = ResUtils.readProperties(s, "CustomBlockLayers");
/*     */     
/*  52 */     if (properties != null)
/*     */     {
/*  54 */       readLayers(s, properties, list);
/*     */     }
/*     */     
/*  57 */     if (Config.isShaders()) {
/*     */       
/*  59 */       PropertiesOrdered propertiesordered = BlockAliases.getBlockLayerPropertes();
/*     */       
/*  61 */       if (propertiesordered != null) {
/*     */         
/*  63 */         String s1 = "shaders/block.properties";
/*  64 */         readLayers(s1, (Properties)propertiesordered, list);
/*     */       } 
/*     */     } 
/*     */     
/*  68 */     if (!list.isEmpty()) {
/*     */       
/*  70 */       renderLayers = list.<EnumWorldBlockLayer>toArray(new EnumWorldBlockLayer[0]);
/*  71 */       active = true;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private static void readLayers(String pathProps, Properties props, List<EnumWorldBlockLayer> list) {
/*  77 */     Config.dbg("CustomBlockLayers: " + pathProps);
/*  78 */     readLayer("solid", EnumWorldBlockLayer.SOLID, props, list);
/*  79 */     readLayer("cutout", EnumWorldBlockLayer.CUTOUT, props, list);
/*  80 */     readLayer("cutout_mipped", EnumWorldBlockLayer.CUTOUT_MIPPED, props, list);
/*  81 */     readLayer("translucent", EnumWorldBlockLayer.TRANSLUCENT, props, list);
/*     */   }
/*     */ 
/*     */   
/*     */   private static void readLayer(String name, EnumWorldBlockLayer layer, Properties props, List<EnumWorldBlockLayer> listLayers) {
/*  86 */     String s = "layer." + name;
/*  87 */     String s1 = props.getProperty(s);
/*     */     
/*  89 */     if (s1 != null) {
/*     */       
/*  91 */       ConnectedParser connectedparser = new ConnectedParser("CustomBlockLayers");
/*  92 */       MatchBlock[] amatchblock = connectedparser.parseMatchBlocks(s1);
/*     */       
/*  94 */       if (amatchblock != null)
/*     */       {
/*  96 */         for (int i = 0; i < amatchblock.length; i++) {
/*     */           
/*  98 */           MatchBlock matchblock = amatchblock[i];
/*  99 */           int j = matchblock.getBlockId();
/*     */           
/* 101 */           if (j > 0) {
/*     */             
/* 103 */             while (listLayers.size() < j + 1)
/*     */             {
/* 105 */               listLayers.add(null);
/*     */             }
/*     */             
/* 108 */             if (listLayers.get(j) != null)
/*     */             {
/* 110 */               Config.warn("CustomBlockLayers: Block layer is already set, block: " + j + ", layer: " + name);
/*     */             }
/*     */             
/* 113 */             listLayers.set(j, layer);
/*     */           } 
/*     */         } 
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public static boolean isActive() {
/* 122 */     return active;
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\optifine\CustomBlockLayers.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */