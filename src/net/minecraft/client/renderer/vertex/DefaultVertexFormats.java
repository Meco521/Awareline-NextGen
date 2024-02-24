/*     */ package net.minecraft.client.renderer.vertex;
/*     */ 
/*     */ import java.lang.reflect.Field;
/*     */ import net.minecraft.src.Config;
/*     */ import net.optifine.reflect.ReflectorClass;
/*     */ import net.optifine.reflect.ReflectorField;
/*     */ import net.optifine.shaders.SVertexFormat;
/*     */ 
/*     */ 
/*     */ public class DefaultVertexFormats
/*     */ {
/*  12 */   public static VertexFormat BLOCK = new VertexFormat();
/*  13 */   public static VertexFormat ITEM = new VertexFormat();
/*  14 */   private static final VertexFormat BLOCK_VANILLA = BLOCK;
/*  15 */   private static final VertexFormat ITEM_VANILLA = ITEM;
/*  16 */   public static ReflectorClass Attributes = new ReflectorClass("net.minecraftforge.client.model.Attributes");
/*  17 */   public static ReflectorField Attributes_DEFAULT_BAKED_FORMAT = new ReflectorField(Attributes, "DEFAULT_BAKED_FORMAT");
/*  18 */   private static final VertexFormat FORGE_BAKED = SVertexFormat.duplicate((VertexFormat)getFieldValue(Attributes_DEFAULT_BAKED_FORMAT));
/*  19 */   public static final VertexFormat OLDMODEL_POSITION_TEX_NORMAL = new VertexFormat();
/*  20 */   public static final VertexFormat PARTICLE_POSITION_TEX_COLOR_LMAP = new VertexFormat();
/*  21 */   public static final VertexFormat POSITION = new VertexFormat();
/*  22 */   public static final VertexFormat POSITION_COLOR = new VertexFormat();
/*  23 */   public static final VertexFormat POSITION_TEX = new VertexFormat();
/*  24 */   public static final VertexFormat POSITION_NORMAL = new VertexFormat();
/*  25 */   public static final VertexFormat POSITION_TEX_COLOR = new VertexFormat();
/*  26 */   public static final VertexFormat POSITION_TEX_NORMAL = new VertexFormat();
/*  27 */   public static final VertexFormat POSITION_TEX_LMAP_COLOR = new VertexFormat();
/*  28 */   public static final VertexFormat POSITION_TEX_COLOR_NORMAL = new VertexFormat();
/*  29 */   public static final VertexFormatElement POSITION_3F = new VertexFormatElement(0, VertexFormatElement.EnumType.FLOAT, VertexFormatElement.EnumUsage.POSITION, 3);
/*  30 */   public static final VertexFormatElement COLOR_4UB = new VertexFormatElement(0, VertexFormatElement.EnumType.UBYTE, VertexFormatElement.EnumUsage.COLOR, 4);
/*  31 */   public static final VertexFormatElement TEX_2F = new VertexFormatElement(0, VertexFormatElement.EnumType.FLOAT, VertexFormatElement.EnumUsage.UV, 2);
/*  32 */   public static final VertexFormatElement TEX_2S = new VertexFormatElement(1, VertexFormatElement.EnumType.SHORT, VertexFormatElement.EnumUsage.UV, 2);
/*  33 */   public static final VertexFormatElement NORMAL_3B = new VertexFormatElement(0, VertexFormatElement.EnumType.BYTE, VertexFormatElement.EnumUsage.NORMAL, 3);
/*  34 */   public static final VertexFormatElement PADDING_1B = new VertexFormatElement(0, VertexFormatElement.EnumType.BYTE, VertexFormatElement.EnumUsage.PADDING, 1);
/*     */ 
/*     */   
/*     */   public static void updateVertexFormats() {
/*  38 */     if (Config.isShaders()) {
/*     */       
/*  40 */       BLOCK = SVertexFormat.makeDefVertexFormatBlock();
/*  41 */       ITEM = SVertexFormat.makeDefVertexFormatItem();
/*     */       
/*  43 */       if (Attributes_DEFAULT_BAKED_FORMAT.exists())
/*     */       {
/*  45 */         SVertexFormat.setDefBakedFormat((VertexFormat)Attributes_DEFAULT_BAKED_FORMAT.getValue());
/*     */       }
/*     */     }
/*     */     else {
/*     */       
/*  50 */       BLOCK = BLOCK_VANILLA;
/*  51 */       ITEM = ITEM_VANILLA;
/*     */       
/*  53 */       if (Attributes_DEFAULT_BAKED_FORMAT.exists())
/*     */       {
/*  55 */         SVertexFormat.copy(FORGE_BAKED, (VertexFormat)Attributes_DEFAULT_BAKED_FORMAT.getValue());
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static Object getFieldValue(ReflectorField p_getFieldValue_0_) {
/*     */     try {
/*  64 */       Field field = p_getFieldValue_0_.getTargetField();
/*     */       
/*  66 */       if (field == null)
/*     */       {
/*  68 */         return null;
/*     */       }
/*     */ 
/*     */       
/*  72 */       return field.get(null);
/*     */     
/*     */     }
/*  75 */     catch (Throwable throwable) {
/*     */       
/*  77 */       throwable.printStackTrace();
/*  78 */       return null;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   static {
/*  84 */     BLOCK.addElement(POSITION_3F);
/*  85 */     BLOCK.addElement(COLOR_4UB);
/*  86 */     BLOCK.addElement(TEX_2F);
/*  87 */     BLOCK.addElement(TEX_2S);
/*  88 */     ITEM.addElement(POSITION_3F);
/*  89 */     ITEM.addElement(COLOR_4UB);
/*  90 */     ITEM.addElement(TEX_2F);
/*  91 */     ITEM.addElement(NORMAL_3B);
/*  92 */     ITEM.addElement(PADDING_1B);
/*  93 */     OLDMODEL_POSITION_TEX_NORMAL.addElement(POSITION_3F);
/*  94 */     OLDMODEL_POSITION_TEX_NORMAL.addElement(TEX_2F);
/*  95 */     OLDMODEL_POSITION_TEX_NORMAL.addElement(NORMAL_3B);
/*  96 */     OLDMODEL_POSITION_TEX_NORMAL.addElement(PADDING_1B);
/*  97 */     PARTICLE_POSITION_TEX_COLOR_LMAP.addElement(POSITION_3F);
/*  98 */     PARTICLE_POSITION_TEX_COLOR_LMAP.addElement(TEX_2F);
/*  99 */     PARTICLE_POSITION_TEX_COLOR_LMAP.addElement(COLOR_4UB);
/* 100 */     PARTICLE_POSITION_TEX_COLOR_LMAP.addElement(TEX_2S);
/* 101 */     POSITION.addElement(POSITION_3F);
/* 102 */     POSITION_COLOR.addElement(POSITION_3F);
/* 103 */     POSITION_COLOR.addElement(COLOR_4UB);
/* 104 */     POSITION_TEX.addElement(POSITION_3F);
/* 105 */     POSITION_TEX.addElement(TEX_2F);
/* 106 */     POSITION_NORMAL.addElement(POSITION_3F);
/* 107 */     POSITION_NORMAL.addElement(NORMAL_3B);
/* 108 */     POSITION_NORMAL.addElement(PADDING_1B);
/* 109 */     POSITION_TEX_COLOR.addElement(POSITION_3F);
/* 110 */     POSITION_TEX_COLOR.addElement(TEX_2F);
/* 111 */     POSITION_TEX_COLOR.addElement(COLOR_4UB);
/* 112 */     POSITION_TEX_NORMAL.addElement(POSITION_3F);
/* 113 */     POSITION_TEX_NORMAL.addElement(TEX_2F);
/* 114 */     POSITION_TEX_NORMAL.addElement(NORMAL_3B);
/* 115 */     POSITION_TEX_NORMAL.addElement(PADDING_1B);
/* 116 */     POSITION_TEX_LMAP_COLOR.addElement(POSITION_3F);
/* 117 */     POSITION_TEX_LMAP_COLOR.addElement(TEX_2F);
/* 118 */     POSITION_TEX_LMAP_COLOR.addElement(TEX_2S);
/* 119 */     POSITION_TEX_LMAP_COLOR.addElement(COLOR_4UB);
/* 120 */     POSITION_TEX_COLOR_NORMAL.addElement(POSITION_3F);
/* 121 */     POSITION_TEX_COLOR_NORMAL.addElement(TEX_2F);
/* 122 */     POSITION_TEX_COLOR_NORMAL.addElement(COLOR_4UB);
/* 123 */     POSITION_TEX_COLOR_NORMAL.addElement(NORMAL_3B);
/* 124 */     POSITION_TEX_COLOR_NORMAL.addElement(PADDING_1B);
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\minecraft\client\renderer\vertex\DefaultVertexFormats.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */