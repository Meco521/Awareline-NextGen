/*    */ package net.minecraft.client.renderer.entity.layers;
/*    */ import net.minecraft.client.renderer.GlStateManager;
/*    */ import net.minecraft.client.renderer.entity.RenderWolf;
/*    */ import net.minecraft.entity.Entity;
/*    */ import net.minecraft.entity.EntityLivingBase;
/*    */ import net.minecraft.entity.passive.EntitySheep;
/*    */ import net.minecraft.entity.passive.EntityWolf;
/*    */ import net.minecraft.item.EnumDyeColor;
/*    */ import net.minecraft.src.Config;
/*    */ import net.minecraft.util.ResourceLocation;
/*    */ import net.optifine.CustomColors;
/*    */ 
/*    */ public class LayerWolfCollar implements LayerRenderer<EntityWolf> {
/* 14 */   private static final ResourceLocation WOLF_COLLAR = new ResourceLocation("textures/entity/wolf/wolf_collar.png");
/*    */   
/*    */   private final RenderWolf wolfRenderer;
/*    */   
/*    */   public LayerWolfCollar(RenderWolf wolfRendererIn) {
/* 19 */     this.wolfRenderer = wolfRendererIn;
/*    */   }
/*    */ 
/*    */   
/*    */   public void doRenderLayer(EntityWolf entitylivingbaseIn, float p_177141_2_, float p_177141_3_, float partialTicks, float p_177141_5_, float p_177141_6_, float p_177141_7_, float scale) {
/* 24 */     if (entitylivingbaseIn.isTamed() && !entitylivingbaseIn.isInvisible()) {
/*    */       
/* 26 */       this.wolfRenderer.bindTexture(WOLF_COLLAR);
/* 27 */       EnumDyeColor enumdyecolor = EnumDyeColor.byMetadata(entitylivingbaseIn.getCollarColor().getMetadata());
/* 28 */       float[] afloat = EntitySheep.getDyeRgb(enumdyecolor);
/*    */       
/* 30 */       if (Config.isCustomColors())
/*    */       {
/* 32 */         afloat = CustomColors.getWolfCollarColors(enumdyecolor, afloat);
/*    */       }
/*    */       
/* 35 */       GlStateManager.color(afloat[0], afloat[1], afloat[2]);
/* 36 */       this.wolfRenderer.getMainModel().render((Entity)entitylivingbaseIn, p_177141_2_, p_177141_3_, p_177141_5_, p_177141_6_, p_177141_7_, scale);
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean shouldCombineTextures() {
/* 42 */     return true;
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\minecraft\client\renderer\entity\layers\LayerWolfCollar.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */