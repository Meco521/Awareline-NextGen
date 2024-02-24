/*    */ package net.optifine.entity.model;
/*    */ 
/*    */ import net.minecraft.client.Minecraft;
/*    */ import net.minecraft.client.model.ModelArmorStand;
/*    */ import net.minecraft.client.model.ModelBase;
/*    */ import net.minecraft.client.model.ModelRenderer;
/*    */ import net.minecraft.client.renderer.entity.ArmorStandRenderer;
/*    */ import net.minecraft.client.renderer.entity.RenderManager;
/*    */ import net.minecraft.entity.item.EntityArmorStand;
/*    */ import net.minecraft.src.Config;
/*    */ 
/*    */ public class ModelAdapterArmorStand
/*    */   extends ModelAdapterBiped
/*    */ {
/*    */   public ModelAdapterArmorStand() {
/* 16 */     super(EntityArmorStand.class, "armor_stand", 0.0F);
/*    */   }
/*    */ 
/*    */   
/*    */   public ModelBase makeModel() {
/* 21 */     return (ModelBase)new ModelArmorStand();
/*    */   }
/*    */ 
/*    */   
/*    */   public ModelRenderer getModelRenderer(ModelBase model, String modelPart) {
/* 26 */     if (!(model instanceof ModelArmorStand))
/*    */     {
/* 28 */       return null;
/*    */     }
/*    */ 
/*    */     
/* 32 */     ModelArmorStand modelarmorstand = (ModelArmorStand)model;
/* 33 */     return modelPart.equals("right") ? modelarmorstand.standRightSide : (modelPart.equals("left") ? modelarmorstand.standLeftSide : (modelPart.equals("waist") ? modelarmorstand.standWaist : (modelPart.equals("base") ? modelarmorstand.standBase : super.getModelRenderer((ModelBase)modelarmorstand, modelPart))));
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public String[] getModelRendererNames() {
/* 39 */     String[] astring = super.getModelRendererNames();
/* 40 */     astring = (String[])Config.addObjectsToArray((Object[])astring, (Object[])new String[] { "right", "left", "waist", "base" });
/* 41 */     return astring;
/*    */   }
/*    */ 
/*    */   
/*    */   public IEntityRenderer makeEntityRender(ModelBase modelBase, float shadowSize) {
/* 46 */     RenderManager rendermanager = Minecraft.getMinecraft().getRenderManager();
/* 47 */     ArmorStandRenderer armorstandrenderer = new ArmorStandRenderer(rendermanager);
/* 48 */     armorstandrenderer.mainModel = modelBase;
/* 49 */     armorstandrenderer.shadowSize = shadowSize;
/* 50 */     return (IEntityRenderer)armorstandrenderer;
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\optifine\entity\model\ModelAdapterArmorStand.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */