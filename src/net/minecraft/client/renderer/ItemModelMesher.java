/*     */ package net.minecraft.client.renderer;
/*     */ 
/*     */ import com.google.common.collect.Maps;
/*     */ import java.util.Map;
/*     */ import net.minecraft.client.renderer.texture.TextureAtlasSprite;
/*     */ import net.minecraft.client.resources.model.IBakedModel;
/*     */ import net.minecraft.client.resources.model.ModelManager;
/*     */ import net.minecraft.client.resources.model.ModelResourceLocation;
/*     */ import net.minecraft.item.Item;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.src.Config;
/*     */ import net.minecraft.util.ResourceLocation;
/*     */ import net.minecraftforge.client.model.ISmartItemModel;
/*     */ import net.optifine.CustomItems;
/*     */ import net.optifine.reflect.Reflector;
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ItemModelMesher
/*     */ {
/*  21 */   private final Map<Integer, ModelResourceLocation> simpleShapes = Maps.newHashMap();
/*  22 */   private final Map<Integer, IBakedModel> simpleShapesCache = Maps.newHashMap();
/*  23 */   private final Map<Item, ItemMeshDefinition> shapers = Maps.newHashMap();
/*     */   
/*     */   private final ModelManager modelManager;
/*     */   
/*     */   public ItemModelMesher(ModelManager modelManager) {
/*  28 */     this.modelManager = modelManager;
/*     */   }
/*     */ 
/*     */   
/*     */   public TextureAtlasSprite getParticleIcon(Item item) {
/*  33 */     return getParticleIcon(item, 0);
/*     */   }
/*     */ 
/*     */   
/*     */   public TextureAtlasSprite getParticleIcon(Item item, int meta) {
/*  38 */     return getItemModel(new ItemStack(item, 1, meta)).getParticleTexture();
/*     */   }
/*     */ 
/*     */   
/*     */   public IBakedModel getItemModel(ItemStack stack) {
/*  43 */     Item item = stack.getItem();
/*  44 */     IBakedModel ibakedmodel = getItemModel(item, getMetadata(stack));
/*     */     
/*  46 */     if (ibakedmodel == null) {
/*     */       
/*  48 */       ItemMeshDefinition itemmeshdefinition = this.shapers.get(item);
/*     */       
/*  50 */       if (itemmeshdefinition != null)
/*     */       {
/*  52 */         ibakedmodel = this.modelManager.getModel(itemmeshdefinition.getModelLocation(stack));
/*     */       }
/*     */     } 
/*     */     
/*  56 */     if (Reflector.ForgeHooksClient.exists() && ibakedmodel instanceof ISmartItemModel)
/*     */     {
/*  58 */       ibakedmodel = ((ISmartItemModel)ibakedmodel).handleItemState(stack);
/*     */     }
/*     */     
/*  61 */     if (ibakedmodel == null)
/*     */     {
/*  63 */       ibakedmodel = this.modelManager.getMissingModel();
/*     */     }
/*     */     
/*  66 */     if (Config.isCustomItems())
/*     */     {
/*  68 */       ibakedmodel = CustomItems.getCustomItemModel(stack, ibakedmodel, (ResourceLocation)null, true);
/*     */     }
/*     */     
/*  71 */     return ibakedmodel;
/*     */   }
/*     */ 
/*     */   
/*     */   protected int getMetadata(ItemStack stack) {
/*  76 */     return stack.isItemStackDamageable() ? 0 : stack.getMetadata();
/*     */   }
/*     */ 
/*     */   
/*     */   protected IBakedModel getItemModel(Item item, int meta) {
/*  81 */     return this.simpleShapesCache.get(Integer.valueOf(getIndex(item, meta)));
/*     */   }
/*     */ 
/*     */   
/*     */   private int getIndex(Item item, int meta) {
/*  86 */     return Item.getIdFromItem(item) << 16 | meta;
/*     */   }
/*     */ 
/*     */   
/*     */   public void register(Item item, int meta, ModelResourceLocation location) {
/*  91 */     this.simpleShapes.put(Integer.valueOf(getIndex(item, meta)), location);
/*  92 */     this.simpleShapesCache.put(Integer.valueOf(getIndex(item, meta)), this.modelManager.getModel(location));
/*     */   }
/*     */ 
/*     */   
/*     */   public void register(Item item, ItemMeshDefinition definition) {
/*  97 */     this.shapers.put(item, definition);
/*     */   }
/*     */ 
/*     */   
/*     */   public ModelManager getModelManager() {
/* 102 */     return this.modelManager;
/*     */   }
/*     */ 
/*     */   
/*     */   public void rebuildCache() {
/* 107 */     this.simpleShapesCache.clear();
/*     */     
/* 109 */     for (Map.Entry<Integer, ModelResourceLocation> entry : this.simpleShapes.entrySet())
/*     */     {
/* 111 */       this.simpleShapesCache.put(entry.getKey(), this.modelManager.getModel(entry.getValue()));
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\minecraft\client\renderer\ItemModelMesher.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */