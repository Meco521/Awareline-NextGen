/*    */ package net.optifine.entity.model;
/*    */ 
/*    */ import java.util.Iterator;
/*    */ import java.util.List;
/*    */ import net.minecraft.client.Minecraft;
/*    */ import net.minecraft.client.model.ModelBase;
/*    */ import net.minecraft.client.model.ModelSheep1;
/*    */ import net.minecraft.client.model.ModelSheep2;
/*    */ import net.minecraft.client.renderer.entity.Render;
/*    */ import net.minecraft.client.renderer.entity.RenderManager;
/*    */ import net.minecraft.client.renderer.entity.RenderSheep;
/*    */ import net.minecraft.client.renderer.entity.layers.LayerRenderer;
/*    */ import net.minecraft.client.renderer.entity.layers.LayerSheepWool;
/*    */ import net.minecraft.entity.passive.EntitySheep;
/*    */ import net.minecraft.src.Config;
/*    */ 
/*    */ 
/*    */ public class ModelAdapterSheepWool
/*    */   extends ModelAdapterQuadruped
/*    */ {
/*    */   public ModelAdapterSheepWool() {
/* 22 */     super(EntitySheep.class, "sheep_wool", 0.7F);
/*    */   }
/*    */ 
/*    */   
/*    */   public ModelBase makeModel() {
/* 27 */     return (ModelBase)new ModelSheep1();
/*    */   }
/*    */   
/*    */   public IEntityRenderer makeEntityRender(ModelBase modelBase, float shadowSize) {
/*    */     RenderSheep renderSheep1;
/* 32 */     RenderManager rendermanager = Minecraft.getMinecraft().getRenderManager();
/* 33 */     Render render = (Render)rendermanager.getEntityRenderMap().get(EntitySheep.class);
/*    */     
/* 35 */     if (!(render instanceof RenderSheep)) {
/*    */       
/* 37 */       Config.warn("Not a RenderSheep: " + render);
/* 38 */       return null;
/*    */     } 
/*    */ 
/*    */     
/* 42 */     if (render.getEntityClass() == null)
/*    */     {
/* 44 */       renderSheep1 = new RenderSheep(rendermanager, (ModelBase)new ModelSheep2(), 0.7F);
/*    */     }
/*    */     
/* 47 */     RenderSheep rendersheep = renderSheep1;
/* 48 */     List<LayerRenderer<EntitySheep>> list = rendersheep.getLayerRenderers();
/* 49 */     Iterator<LayerRenderer<EntitySheep>> iterator = list.iterator();
/*    */     
/* 51 */     while (iterator.hasNext()) {
/*    */       
/* 53 */       LayerRenderer layerrenderer = iterator.next();
/*    */       
/* 55 */       if (layerrenderer instanceof LayerSheepWool)
/*    */       {
/* 57 */         iterator.remove();
/*    */       }
/*    */     } 
/*    */     
/* 61 */     LayerSheepWool layersheepwool = new LayerSheepWool(rendersheep);
/* 62 */     layersheepwool.sheepModel = (ModelSheep1)modelBase;
/* 63 */     rendersheep.addLayer((LayerRenderer)layersheepwool);
/* 64 */     return (IEntityRenderer)rendersheep;
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\optifine\entity\model\ModelAdapterSheepWool.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */