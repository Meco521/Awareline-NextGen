/*     */ package net.minecraft.client.renderer;
/*     */ 
/*     */ import java.nio.FloatBuffer;
/*     */ import net.minecraft.util.AxisAlignedBB;
/*     */ import net.minecraft.util.Vec3;
/*     */ import org.lwjgl.opengl.GL11;
/*     */ 
/*     */ 
/*     */ 
/*     */ public class RenderHelper
/*     */ {
/*  12 */   private static final FloatBuffer colorBuffer = GLAllocation.createDirectFloatBuffer(16);
/*  13 */   private static final Vec3 LIGHT0_POS = (new Vec3(0.20000000298023224D, 1.0D, -0.699999988079071D)).normalize();
/*  14 */   private static final Vec3 LIGHT1_POS = (new Vec3(-0.20000000298023224D, 1.0D, 0.699999988079071D)).normalize();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void disableStandardItemLighting() {
/*  21 */     GlStateManager.disableLighting();
/*  22 */     GlStateManager.disableLight(0);
/*  23 */     GlStateManager.disableLight(1);
/*  24 */     GlStateManager.disableColorMaterial();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void enableStandardItemLighting() {
/*  32 */     GlStateManager.enableLighting();
/*  33 */     GlStateManager.enableLight(0);
/*  34 */     GlStateManager.enableLight(1);
/*  35 */     GlStateManager.enableColorMaterial();
/*  36 */     GlStateManager.colorMaterial(1032, 5634);
/*  37 */     float f = 0.4F;
/*  38 */     float f1 = 0.6F;
/*  39 */     float f2 = 0.0F;
/*  40 */     GL11.glLight(16384, 4611, setColorBuffer(LIGHT0_POS.xCoord, LIGHT0_POS.yCoord, LIGHT0_POS.zCoord, 0.0D));
/*  41 */     GL11.glLight(16384, 4609, setColorBuffer(f1, f1, f1, 1.0F));
/*  42 */     GL11.glLight(16384, 4608, setColorBuffer(0.0F, 0.0F, 0.0F, 1.0F));
/*  43 */     GL11.glLight(16384, 4610, setColorBuffer(f2, f2, f2, 1.0F));
/*  44 */     GL11.glLight(16385, 4611, setColorBuffer(LIGHT1_POS.xCoord, LIGHT1_POS.yCoord, LIGHT1_POS.zCoord, 0.0D));
/*  45 */     GL11.glLight(16385, 4609, setColorBuffer(f1, f1, f1, 1.0F));
/*  46 */     GL11.glLight(16385, 4608, setColorBuffer(0.0F, 0.0F, 0.0F, 1.0F));
/*  47 */     GL11.glLight(16385, 4610, setColorBuffer(f2, f2, f2, 1.0F));
/*  48 */     GlStateManager.shadeModel(7424);
/*  49 */     GL11.glLightModel(2899, setColorBuffer(f, f, f, 1.0F));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static FloatBuffer setColorBuffer(double p_74517_0_, double p_74517_2_, double p_74517_4_, double p_74517_6_) {
/*  57 */     return setColorBuffer((float)p_74517_0_, (float)p_74517_2_, (float)p_74517_4_, (float)p_74517_6_);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static FloatBuffer setColorBuffer(float p_74521_0_, float p_74521_1_, float p_74521_2_, float p_74521_3_) {
/*  65 */     colorBuffer.clear();
/*  66 */     colorBuffer.put(p_74521_0_).put(p_74521_1_).put(p_74521_2_).put(p_74521_3_);
/*  67 */     colorBuffer.flip();
/*  68 */     return colorBuffer;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void enableGUIStandardItemLighting() {
/*  76 */     GlStateManager.pushMatrix();
/*  77 */     GlStateManager.rotate(-30.0F, 0.0F, 1.0F, 0.0F);
/*  78 */     GlStateManager.rotate(165.0F, 1.0F, 0.0F, 0.0F);
/*  79 */     enableStandardItemLighting();
/*  80 */     GlStateManager.popMatrix();
/*     */   }
/*     */   public static void drawBox(AxisAlignedBB box) {
/*  83 */     if (box == null) {
/*     */       return;
/*     */     }
/*  86 */     GL11.glBegin(7);
/*  87 */     GL11.glVertex3d(box.minX, box.minY, box.maxZ);
/*  88 */     GL11.glVertex3d(box.maxX, box.minY, box.maxZ);
/*  89 */     GL11.glVertex3d(box.maxX, box.maxY, box.maxZ);
/*  90 */     GL11.glVertex3d(box.minX, box.maxY, box.maxZ);
/*  91 */     GL11.glEnd();
/*  92 */     GL11.glBegin(7);
/*  93 */     GL11.glVertex3d(box.maxX, box.minY, box.maxZ);
/*  94 */     GL11.glVertex3d(box.minX, box.minY, box.maxZ);
/*  95 */     GL11.glVertex3d(box.minX, box.maxY, box.maxZ);
/*  96 */     GL11.glVertex3d(box.maxX, box.maxY, box.maxZ);
/*  97 */     GL11.glEnd();
/*  98 */     GL11.glBegin(7);
/*  99 */     GL11.glVertex3d(box.minX, box.minY, box.minZ);
/* 100 */     GL11.glVertex3d(box.minX, box.minY, box.maxZ);
/* 101 */     GL11.glVertex3d(box.minX, box.maxY, box.maxZ);
/* 102 */     GL11.glVertex3d(box.minX, box.maxY, box.minZ);
/* 103 */     GL11.glEnd();
/* 104 */     GL11.glBegin(7);
/* 105 */     GL11.glVertex3d(box.minX, box.minY, box.maxZ);
/* 106 */     GL11.glVertex3d(box.minX, box.minY, box.minZ);
/* 107 */     GL11.glVertex3d(box.minX, box.maxY, box.minZ);
/* 108 */     GL11.glVertex3d(box.minX, box.maxY, box.maxZ);
/* 109 */     GL11.glEnd();
/* 110 */     GL11.glBegin(7);
/* 111 */     GL11.glVertex3d(box.maxX, box.minY, box.maxZ);
/* 112 */     GL11.glVertex3d(box.maxX, box.minY, box.minZ);
/* 113 */     GL11.glVertex3d(box.maxX, box.maxY, box.minZ);
/* 114 */     GL11.glVertex3d(box.maxX, box.maxY, box.maxZ);
/* 115 */     GL11.glEnd();
/* 116 */     GL11.glBegin(7);
/* 117 */     GL11.glVertex3d(box.maxX, box.minY, box.minZ);
/* 118 */     GL11.glVertex3d(box.maxX, box.minY, box.maxZ);
/* 119 */     GL11.glVertex3d(box.maxX, box.maxY, box.maxZ);
/* 120 */     GL11.glVertex3d(box.maxX, box.maxY, box.minZ);
/* 121 */     GL11.glEnd();
/* 122 */     GL11.glBegin(7);
/* 123 */     GL11.glVertex3d(box.minX, box.minY, box.minZ);
/* 124 */     GL11.glVertex3d(box.maxX, box.minY, box.minZ);
/* 125 */     GL11.glVertex3d(box.maxX, box.maxY, box.minZ);
/* 126 */     GL11.glVertex3d(box.minX, box.maxY, box.minZ);
/* 127 */     GL11.glEnd();
/* 128 */     GL11.glBegin(7);
/* 129 */     GL11.glVertex3d(box.maxX, box.minY, box.minZ);
/* 130 */     GL11.glVertex3d(box.minX, box.minY, box.minZ);
/* 131 */     GL11.glVertex3d(box.minX, box.maxY, box.minZ);
/* 132 */     GL11.glVertex3d(box.maxX, box.maxY, box.minZ);
/* 133 */     GL11.glEnd();
/* 134 */     GL11.glBegin(7);
/* 135 */     GL11.glVertex3d(box.minX, box.maxY, box.minZ);
/* 136 */     GL11.glVertex3d(box.maxX, box.maxY, box.minZ);
/* 137 */     GL11.glVertex3d(box.maxX, box.maxY, box.maxZ);
/* 138 */     GL11.glVertex3d(box.minX, box.maxY, box.maxZ);
/* 139 */     GL11.glEnd();
/* 140 */     GL11.glBegin(7);
/* 141 */     GL11.glVertex3d(box.maxX, box.maxY, box.minZ);
/* 142 */     GL11.glVertex3d(box.minX, box.maxY, box.minZ);
/* 143 */     GL11.glVertex3d(box.minX, box.maxY, box.maxZ);
/* 144 */     GL11.glVertex3d(box.maxX, box.maxY, box.maxZ);
/* 145 */     GL11.glEnd();
/* 146 */     GL11.glBegin(7);
/* 147 */     GL11.glVertex3d(box.minX, box.minY, box.minZ);
/* 148 */     GL11.glVertex3d(box.maxX, box.minY, box.minZ);
/* 149 */     GL11.glVertex3d(box.maxX, box.minY, box.maxZ);
/* 150 */     GL11.glVertex3d(box.minX, box.minY, box.maxZ);
/* 151 */     GL11.glEnd();
/* 152 */     GL11.glBegin(7);
/* 153 */     GL11.glVertex3d(box.maxX, box.minY, box.minZ);
/* 154 */     GL11.glVertex3d(box.minX, box.minY, box.minZ);
/* 155 */     GL11.glVertex3d(box.minX, box.minY, box.maxZ);
/* 156 */     GL11.glVertex3d(box.maxX, box.minY, box.maxZ);
/* 157 */     GL11.glEnd();
/*     */   }
/*     */   
/*     */   public static void glColor(int hex) {
/* 161 */     float alpha = (hex >> 24 & 0xFF) / 255.0F;
/* 162 */     float red = (hex >> 16 & 0xFF) / 255.0F;
/* 163 */     float green = (hex >> 8 & 0xFF) / 255.0F;
/* 164 */     float blue = (hex & 0xFF) / 255.0F;
/* 165 */     GL11.glColor4f(red, green, blue, alpha);
/*     */   }
/*     */   public static void drawCompleteBoxFilled(AxisAlignedBB axisalignedbb, float width, int insideColor) {
/* 168 */     GL11.glLineWidth(width);
/* 169 */     GL11.glEnable(2848);
/* 170 */     GL11.glEnable(2881);
/* 171 */     GL11.glHint(3154, 4354);
/* 172 */     GL11.glHint(3155, 4354);
/* 173 */     glColor(insideColor);
/* 174 */     drawBox(axisalignedbb);
/* 175 */     GL11.glDisable(2848);
/* 176 */     GL11.glDisable(2881);
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\minecraft\client\renderer\RenderHelper.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */