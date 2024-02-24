/*     */ package net.optifine.entity.model;
/*     */ import com.google.gson.JsonObject;
/*     */ import com.google.gson.JsonParseException;
/*     */ import java.io.IOException;
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashMap;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ import net.minecraft.client.model.ModelBase;
/*     */ import net.minecraft.client.model.ModelRenderer;
/*     */ import net.minecraft.client.renderer.entity.Render;
/*     */ import net.minecraft.client.renderer.entity.RenderManager;
/*     */ import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
/*     */ import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
/*     */ import net.minecraft.src.Config;
/*     */ import net.minecraft.util.ResourceLocation;
/*     */ import net.optifine.entity.model.anim.ModelResolver;
/*     */ import net.optifine.entity.model.anim.ModelUpdater;
/*     */ 
/*     */ public class CustomEntityModels {
/*  23 */   private static Map<Class, Render> originalEntityRenderMap = null; private static boolean active = false;
/*  24 */   private static Map<Class, TileEntitySpecialRenderer> originalTileEntityRenderMap = null;
/*     */ 
/*     */   
/*     */   public static void update() {
/*  28 */     Map<Class<?>, Render> map = getEntityRenderMap();
/*  29 */     Map<Class<?>, TileEntitySpecialRenderer> map1 = getTileEntityRenderMap();
/*     */     
/*  31 */     if (map == null) {
/*     */       
/*  33 */       Config.warn("Entity render map not found, custom entity models are DISABLED.");
/*     */     }
/*  35 */     else if (map1 == null) {
/*     */       
/*  37 */       Config.warn("Tile entity render map not found, custom entity models are DISABLED.");
/*     */     }
/*     */     else {
/*     */       
/*  41 */       active = false;
/*  42 */       map.clear();
/*  43 */       map1.clear();
/*  44 */       map.putAll((Map)originalEntityRenderMap);
/*  45 */       map1.putAll((Map)originalTileEntityRenderMap);
/*     */       
/*  47 */       if (Config.isCustomEntityModels()) {
/*     */         
/*  49 */         ResourceLocation[] aresourcelocation = getModelLocations();
/*     */         
/*  51 */         for (int i = 0; i < aresourcelocation.length; i++) {
/*     */           
/*  53 */           ResourceLocation resourcelocation = aresourcelocation[i];
/*  54 */           Config.dbg("CustomEntityModel: " + resourcelocation.getResourcePath());
/*  55 */           IEntityRenderer ientityrenderer = parseEntityRender(resourcelocation);
/*     */           
/*  57 */           if (ientityrenderer != null) {
/*     */             
/*  59 */             Class<?> oclass = ientityrenderer.getEntityClass();
/*     */             
/*  61 */             if (oclass != null) {
/*     */               
/*  63 */               if (ientityrenderer instanceof Render) {
/*     */                 
/*  65 */                 map.put(oclass, (Render)ientityrenderer);
/*     */               }
/*  67 */               else if (ientityrenderer instanceof TileEntitySpecialRenderer) {
/*     */                 
/*  69 */                 map1.put(oclass, (TileEntitySpecialRenderer)ientityrenderer);
/*     */               }
/*     */               else {
/*     */                 
/*  73 */                 Config.warn("Unknown renderer type: " + ientityrenderer.getClass().getName());
/*     */               } 
/*     */               
/*  76 */               active = true;
/*     */             } 
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private static Map<Class, Render> getEntityRenderMap() {
/*  86 */     RenderManager rendermanager = Minecraft.getMinecraft().getRenderManager();
/*  87 */     Map<Class<?>, Render> map = rendermanager.getEntityRenderMap();
/*     */     
/*  89 */     if (map == null)
/*     */     {
/*  91 */       return null;
/*     */     }
/*     */ 
/*     */     
/*  95 */     if (originalEntityRenderMap == null)
/*     */     {
/*  97 */       originalEntityRenderMap = (Map)new HashMap<>(map);
/*     */     }
/*     */     
/* 100 */     return map;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static Map<Class, TileEntitySpecialRenderer> getTileEntityRenderMap() {
/* 106 */     Map<Class<?>, TileEntitySpecialRenderer> map = TileEntityRendererDispatcher.instance.mapSpecialRenderers;
/*     */     
/* 108 */     if (originalTileEntityRenderMap == null)
/*     */     {
/* 110 */       originalTileEntityRenderMap = (Map)new HashMap<>(map);
/*     */     }
/*     */     
/* 113 */     return map;
/*     */   }
/*     */ 
/*     */   
/*     */   private static ResourceLocation[] getModelLocations() {
/* 118 */     String s = "optifine/cem/";
/* 119 */     String s1 = ".jem";
/* 120 */     List<ResourceLocation> list = new ArrayList<>();
/* 121 */     String[] astring = CustomModelRegistry.getModelNames();
/*     */     
/* 123 */     for (int i = 0; i < astring.length; i++) {
/*     */       
/* 125 */       String s2 = astring[i];
/* 126 */       String s3 = s + s2 + s1;
/* 127 */       ResourceLocation resourcelocation = new ResourceLocation(s3);
/*     */       
/* 129 */       if (Config.hasResource(resourcelocation))
/*     */       {
/* 131 */         list.add(resourcelocation);
/*     */       }
/*     */     } 
/*     */     
/* 135 */     ResourceLocation[] aresourcelocation = list.<ResourceLocation>toArray(new ResourceLocation[0]);
/* 136 */     return aresourcelocation;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static IEntityRenderer parseEntityRender(ResourceLocation location) {
/*     */     try {
/* 143 */       JsonObject jsonobject = CustomEntityModelParser.loadJson(location);
/* 144 */       IEntityRenderer ientityrenderer = parseEntityRender(jsonobject, location.getResourcePath());
/* 145 */       return ientityrenderer;
/*     */     }
/* 147 */     catch (IOException ioexception) {
/*     */       
/* 149 */       Config.error(ioexception.getClass().getName() + ": " + ioexception.getMessage());
/* 150 */       return null;
/*     */     }
/* 152 */     catch (JsonParseException jsonparseexception) {
/*     */       
/* 154 */       Config.error(jsonparseexception.getClass().getName() + ": " + jsonparseexception.getMessage());
/* 155 */       return null;
/*     */     }
/* 157 */     catch (Exception exception) {
/*     */       
/* 159 */       exception.printStackTrace();
/* 160 */       return null;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private static IEntityRenderer parseEntityRender(JsonObject obj, String path) {
/* 166 */     CustomEntityRenderer customentityrenderer = CustomEntityModelParser.parseEntityRender(obj, path);
/* 167 */     String s = customentityrenderer.getName();
/* 168 */     ModelAdapter modeladapter = CustomModelRegistry.getModelAdapter(s);
/* 169 */     checkNull(modeladapter, "Entity not found: " + s);
/* 170 */     Class oclass = modeladapter.getEntityClass();
/* 171 */     checkNull(oclass, "Entity class not found: " + s);
/* 172 */     IEntityRenderer ientityrenderer = makeEntityRender(modeladapter, customentityrenderer);
/*     */     
/* 174 */     if (ientityrenderer == null)
/*     */     {
/* 176 */       return null;
/*     */     }
/*     */ 
/*     */     
/* 180 */     ientityrenderer.setEntityClass(oclass);
/* 181 */     return ientityrenderer;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static IEntityRenderer makeEntityRender(ModelAdapter modelAdapter, CustomEntityRenderer cer) {
/* 187 */     ResourceLocation resourcelocation = cer.getTextureLocation();
/* 188 */     CustomModelRenderer[] acustommodelrenderer = cer.getCustomModelRenderers();
/* 189 */     float f = cer.getShadowSize();
/*     */     
/* 191 */     if (f < 0.0F)
/*     */     {
/* 193 */       f = modelAdapter.getShadowSize();
/*     */     }
/*     */     
/* 196 */     ModelBase modelbase = modelAdapter.makeModel();
/*     */     
/* 198 */     if (modelbase == null)
/*     */     {
/* 200 */       return null;
/*     */     }
/*     */ 
/*     */     
/* 204 */     ModelResolver modelresolver = new ModelResolver(modelAdapter, modelbase, acustommodelrenderer);
/*     */     
/* 206 */     if (!modifyModel(modelAdapter, modelbase, acustommodelrenderer, modelresolver))
/*     */     {
/* 208 */       return null;
/*     */     }
/*     */ 
/*     */     
/* 212 */     IEntityRenderer ientityrenderer = modelAdapter.makeEntityRender(modelbase, f);
/*     */     
/* 214 */     if (ientityrenderer == null)
/*     */     {
/* 216 */       throw new JsonParseException("Entity renderer is null, model: " + modelAdapter.getName() + ", adapter: " + modelAdapter.getClass().getName());
/*     */     }
/*     */ 
/*     */     
/* 220 */     if (resourcelocation != null)
/*     */     {
/* 222 */       ientityrenderer.setLocationTextureCustom(resourcelocation);
/*     */     }
/*     */     
/* 225 */     return ientityrenderer;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static boolean modifyModel(ModelAdapter modelAdapter, ModelBase model, CustomModelRenderer[] modelRenderers, ModelResolver mr) {
/* 233 */     for (int i = 0; i < modelRenderers.length; i++) {
/*     */       
/* 235 */       CustomModelRenderer custommodelrenderer = modelRenderers[i];
/*     */       
/* 237 */       if (!modifyModel(modelAdapter, model, custommodelrenderer, mr))
/*     */       {
/* 239 */         return false;
/*     */       }
/*     */     } 
/*     */     
/* 243 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   private static boolean modifyModel(ModelAdapter modelAdapter, ModelBase model, CustomModelRenderer customModelRenderer, ModelResolver modelResolver) {
/* 248 */     String s = customModelRenderer.getModelPart();
/* 249 */     ModelRenderer modelrenderer = modelAdapter.getModelRenderer(model, s);
/*     */     
/* 251 */     if (modelrenderer == null) {
/*     */       
/* 253 */       Config.warn("Model part not found: " + s + ", model: " + model);
/* 254 */       return false;
/*     */     } 
/*     */ 
/*     */     
/* 258 */     if (!customModelRenderer.isAttach()) {
/*     */       
/* 260 */       if (modelrenderer.cubeList != null)
/*     */       {
/* 262 */         modelrenderer.cubeList.clear();
/*     */       }
/*     */       
/* 265 */       if (modelrenderer.spriteList != null)
/*     */       {
/* 267 */         modelrenderer.spriteList.clear();
/*     */       }
/*     */       
/* 270 */       if (modelrenderer.childModels != null) {
/*     */         
/* 272 */         ModelRenderer[] amodelrenderer = modelAdapter.getModelRenderers(model);
/* 273 */         Set<ModelRenderer> set = Collections.newSetFromMap(new IdentityHashMap<>());
/* 274 */         set.addAll(Arrays.asList(amodelrenderer));
/* 275 */         List<ModelRenderer> list = modelrenderer.childModels;
/* 276 */         Iterator<ModelRenderer> iterator = list.iterator();
/*     */         
/* 278 */         while (iterator.hasNext()) {
/*     */           
/* 280 */           ModelRenderer modelrenderer1 = iterator.next();
/*     */           
/* 282 */           if (!set.contains(modelrenderer1))
/*     */           {
/* 284 */             iterator.remove();
/*     */           }
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 290 */     modelrenderer.addChild(customModelRenderer.getModelRenderer());
/* 291 */     ModelUpdater modelupdater = customModelRenderer.getModelUpdater();
/*     */     
/* 293 */     if (modelupdater != null) {
/*     */       
/* 295 */       modelResolver.setThisModelRenderer(customModelRenderer.getModelRenderer());
/* 296 */       modelResolver.setPartModelRenderer(modelrenderer);
/*     */       
/* 298 */       if (!modelupdater.initialize((IModelResolver)modelResolver))
/*     */       {
/* 300 */         return false;
/*     */       }
/*     */       
/* 303 */       customModelRenderer.getModelRenderer().setModelUpdater(modelupdater);
/*     */     } 
/*     */     
/* 306 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static void checkNull(Object obj, String msg) {
/* 312 */     if (obj == null)
/*     */     {
/* 314 */       throw new JsonParseException(msg);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public static boolean isActive() {
/* 320 */     return active;
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\optifine\entity\model\CustomEntityModels.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */