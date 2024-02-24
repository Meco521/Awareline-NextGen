/*    */ package net.optifine.entity.model;
/*    */ 
/*    */ import java.util.ArrayList;
/*    */ import java.util.List;
/*    */ import net.minecraft.client.model.ModelBase;
/*    */ import net.minecraft.client.model.ModelRenderer;
/*    */ 
/*    */ 
/*    */ 
/*    */ public abstract class ModelAdapter
/*    */ {
/*    */   private final Class entityClass;
/*    */   private final String name;
/*    */   private final float shadowSize;
/*    */   private String[] aliases;
/*    */   
/*    */   public ModelAdapter(Class entityClass, String name, float shadowSize) {
/* 18 */     this.entityClass = entityClass;
/* 19 */     this.name = name;
/* 20 */     this.shadowSize = shadowSize;
/*    */   }
/*    */ 
/*    */   
/*    */   public ModelAdapter(Class entityClass, String name, float shadowSize, String[] aliases) {
/* 25 */     this.entityClass = entityClass;
/* 26 */     this.name = name;
/* 27 */     this.shadowSize = shadowSize;
/* 28 */     this.aliases = aliases;
/*    */   }
/*    */ 
/*    */   
/*    */   public Class getEntityClass() {
/* 33 */     return this.entityClass;
/*    */   }
/*    */ 
/*    */   
/*    */   public String getName() {
/* 38 */     return this.name;
/*    */   }
/*    */ 
/*    */   
/*    */   public String[] getAliases() {
/* 43 */     return this.aliases;
/*    */   }
/*    */ 
/*    */   
/*    */   public float getShadowSize() {
/* 48 */     return this.shadowSize;
/*    */   }
/*    */ 
/*    */   
/*    */   public abstract ModelBase makeModel();
/*    */   
/*    */   public abstract ModelRenderer getModelRenderer(ModelBase paramModelBase, String paramString);
/*    */   
/*    */   public abstract String[] getModelRendererNames();
/*    */   
/*    */   public abstract IEntityRenderer makeEntityRender(ModelBase paramModelBase, float paramFloat);
/*    */   
/*    */   public ModelRenderer[] getModelRenderers(ModelBase model) {
/* 61 */     String[] astring = getModelRendererNames();
/* 62 */     List<ModelRenderer> list = new ArrayList<>();
/*    */     
/* 64 */     for (int i = 0; i < astring.length; i++) {
/*    */       
/* 66 */       String s = astring[i];
/* 67 */       ModelRenderer modelrenderer = getModelRenderer(model, s);
/*    */       
/* 69 */       if (modelrenderer != null)
/*    */       {
/* 71 */         list.add(modelrenderer);
/*    */       }
/*    */     } 
/*    */     
/* 75 */     ModelRenderer[] amodelrenderer = list.<ModelRenderer>toArray(new ModelRenderer[0]);
/* 76 */     return amodelrenderer;
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\optifine\entity\model\ModelAdapter.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */