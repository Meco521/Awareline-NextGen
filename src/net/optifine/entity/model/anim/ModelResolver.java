/*     */ package net.optifine.entity.model.anim;
/*     */ 
/*     */ import net.minecraft.client.model.ModelBase;
/*     */ import net.minecraft.client.model.ModelRenderer;
/*     */ import net.minecraft.src.Config;
/*     */ import net.minecraft.tileentity.TileEntity;
/*     */ import net.optifine.entity.model.CustomModelRenderer;
/*     */ import net.optifine.entity.model.ModelAdapter;
/*     */ import net.optifine.expr.IExpression;
/*     */ 
/*     */ public class ModelResolver
/*     */   implements IModelResolver
/*     */ {
/*     */   private final ModelAdapter modelAdapter;
/*     */   private final ModelBase model;
/*     */   private final CustomModelRenderer[] customModelRenderers;
/*     */   private ModelRenderer thisModelRenderer;
/*     */   private ModelRenderer partModelRenderer;
/*     */   private final IRenderResolver renderResolver;
/*     */   
/*     */   public ModelResolver(ModelAdapter modelAdapter, ModelBase model, CustomModelRenderer[] customModelRenderers) {
/*  22 */     this.modelAdapter = modelAdapter;
/*  23 */     this.model = model;
/*  24 */     this.customModelRenderers = customModelRenderers;
/*  25 */     Class<?> oclass = modelAdapter.getEntityClass();
/*     */     
/*  27 */     if (TileEntity.class.isAssignableFrom(oclass)) {
/*     */       
/*  29 */       this.renderResolver = new RenderResolverTileEntity();
/*     */     }
/*     */     else {
/*     */       
/*  33 */       this.renderResolver = new RenderResolverEntity();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public IExpression getExpression(String name) {
/*  39 */     ModelVariableFloat modelVariableFloat = getModelVariable(name);
/*     */     
/*  41 */     if (modelVariableFloat != null)
/*     */     {
/*  43 */       return (IExpression)modelVariableFloat;
/*     */     }
/*     */ 
/*     */     
/*  47 */     IExpression iexpression1 = this.renderResolver.getParameter(name);
/*  48 */     return (iexpression1 != null) ? iexpression1 : null;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public ModelRenderer getModelRenderer(String name) {
/*  54 */     if (name == null)
/*     */     {
/*  56 */       return null;
/*     */     }
/*  58 */     if (name.indexOf(':') >= 0) {
/*     */       
/*  60 */       String[] astring = Config.tokenize(name, ":");
/*  61 */       ModelRenderer modelrenderer3 = getModelRenderer(astring[0]);
/*     */       
/*  63 */       for (int j = 1; j < astring.length; j++) {
/*     */         
/*  65 */         String s = astring[j];
/*  66 */         ModelRenderer modelrenderer4 = modelrenderer3.getChildDeep(s);
/*     */         
/*  68 */         if (modelrenderer4 == null)
/*     */         {
/*  70 */           return null;
/*     */         }
/*     */         
/*  73 */         modelrenderer3 = modelrenderer4;
/*     */       } 
/*     */       
/*  76 */       return modelrenderer3;
/*     */     } 
/*  78 */     if (this.thisModelRenderer != null && name.equals("this"))
/*     */     {
/*  80 */       return this.thisModelRenderer;
/*     */     }
/*  82 */     if (this.partModelRenderer != null && name.equals("part"))
/*     */     {
/*  84 */       return this.partModelRenderer;
/*     */     }
/*     */ 
/*     */     
/*  88 */     ModelRenderer modelrenderer = this.modelAdapter.getModelRenderer(this.model, name);
/*     */     
/*  90 */     if (modelrenderer != null)
/*     */     {
/*  92 */       return modelrenderer;
/*     */     }
/*     */ 
/*     */     
/*  96 */     for (int i = 0; i < this.customModelRenderers.length; i++) {
/*     */       
/*  98 */       CustomModelRenderer custommodelrenderer = this.customModelRenderers[i];
/*  99 */       ModelRenderer modelrenderer1 = custommodelrenderer.getModelRenderer();
/*     */       
/* 101 */       if (name.equals(modelrenderer1.getId()))
/*     */       {
/* 103 */         return modelrenderer1;
/*     */       }
/*     */       
/* 106 */       ModelRenderer modelrenderer2 = modelrenderer1.getChildDeep(name);
/*     */       
/* 108 */       if (modelrenderer2 != null)
/*     */       {
/* 110 */         return modelrenderer2;
/*     */       }
/*     */     } 
/*     */     
/* 114 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ModelVariableFloat getModelVariable(String name) {
/* 121 */     String[] astring = Config.tokenize(name, ".");
/*     */     
/* 123 */     if (astring.length != 2)
/*     */     {
/* 125 */       return null;
/*     */     }
/*     */ 
/*     */     
/* 129 */     String s = astring[0];
/* 130 */     String s1 = astring[1];
/* 131 */     ModelRenderer modelrenderer = getModelRenderer(s);
/*     */     
/* 133 */     if (modelrenderer == null)
/*     */     {
/* 135 */       return null;
/*     */     }
/*     */ 
/*     */     
/* 139 */     ModelVariableType modelvariabletype = ModelVariableType.parse(s1);
/* 140 */     return (modelvariabletype == null) ? null : new ModelVariableFloat(name, modelrenderer, modelvariabletype);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setPartModelRenderer(ModelRenderer partModelRenderer) {
/* 147 */     this.partModelRenderer = partModelRenderer;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setThisModelRenderer(ModelRenderer thisModelRenderer) {
/* 152 */     this.thisModelRenderer = thisModelRenderer;
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\optifine\entity\model\anim\ModelResolver.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */