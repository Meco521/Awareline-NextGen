/*     */ package net.minecraft.client.renderer.entity.layers;
/*     */ 
/*     */ import com.google.common.collect.Maps;
/*     */ import java.util.Map;
/*     */ import net.minecraft.client.model.ModelBase;
/*     */ import net.minecraft.client.renderer.GlStateManager;
/*     */ import net.minecraft.client.renderer.entity.RendererLivingEntity;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.EntityLivingBase;
/*     */ import net.minecraft.item.ItemArmor;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.src.Config;
/*     */ import net.minecraft.util.ResourceLocation;
/*     */ import net.optifine.CustomItems;
/*     */ import net.optifine.reflect.Reflector;
/*     */ import net.optifine.reflect.ReflectorForge;
/*     */ import net.optifine.shaders.Shaders;
/*     */ import net.optifine.shaders.ShadersRender;
/*     */ 
/*     */ public abstract class LayerArmorBase<T extends ModelBase>
/*     */   implements LayerRenderer<EntityLivingBase>
/*     */ {
/*  23 */   protected static final ResourceLocation ENCHANTED_ITEM_GLINT_RES = new ResourceLocation("textures/misc/enchanted_item_glint.png");
/*     */   protected T modelLeggings;
/*     */   protected T modelArmor;
/*     */   private final RendererLivingEntity<?> renderer;
/*  27 */   private final float alpha = 1.0F;
/*  28 */   private final float colorR = 1.0F;
/*  29 */   private final float colorG = 1.0F;
/*  30 */   private final float colorB = 1.0F;
/*     */   private boolean skipRenderGlint;
/*  32 */   private static final Map<String, ResourceLocation> ARMOR_TEXTURE_RES_MAP = Maps.newHashMap();
/*     */ 
/*     */   
/*     */   public LayerArmorBase(RendererLivingEntity<?> rendererIn) {
/*  36 */     this.renderer = rendererIn;
/*  37 */     initArmor();
/*     */   }
/*     */ 
/*     */   
/*     */   public void doRenderLayer(EntityLivingBase entitylivingbaseIn, float p_177141_2_, float p_177141_3_, float partialTicks, float p_177141_5_, float p_177141_6_, float p_177141_7_, float scale) {
/*  42 */     renderLayer(entitylivingbaseIn, p_177141_2_, p_177141_3_, partialTicks, p_177141_5_, p_177141_6_, p_177141_7_, scale, 4);
/*  43 */     renderLayer(entitylivingbaseIn, p_177141_2_, p_177141_3_, partialTicks, p_177141_5_, p_177141_6_, p_177141_7_, scale, 3);
/*  44 */     renderLayer(entitylivingbaseIn, p_177141_2_, p_177141_3_, partialTicks, p_177141_5_, p_177141_6_, p_177141_7_, scale, 2);
/*  45 */     renderLayer(entitylivingbaseIn, p_177141_2_, p_177141_3_, partialTicks, p_177141_5_, p_177141_6_, p_177141_7_, scale, 1);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean shouldCombineTextures() {
/*  50 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   private void renderLayer(EntityLivingBase entitylivingbaseIn, float p_177182_2_, float p_177182_3_, float partialTicks, float p_177182_5_, float p_177182_6_, float p_177182_7_, float scale, int armorSlot) {
/*  55 */     ItemStack itemstack = getCurrentArmor(entitylivingbaseIn, armorSlot);
/*     */     
/*  57 */     if (itemstack != null && itemstack.getItem() instanceof ItemArmor) {
/*     */       int i; float f, f1, f2;
/*  59 */       ItemArmor itemarmor = (ItemArmor)itemstack.getItem();
/*  60 */       T t = getArmorModel(armorSlot);
/*  61 */       t.setModelAttributes(this.renderer.getMainModel());
/*  62 */       t.setLivingAnimations(entitylivingbaseIn, p_177182_2_, p_177182_3_, partialTicks);
/*     */       
/*  64 */       if (Reflector.ForgeHooksClient.exists())
/*     */       {
/*  66 */         t = getArmorModelHook(entitylivingbaseIn, itemstack, armorSlot, t);
/*     */       }
/*     */       
/*  69 */       setModelPartVisible(t, armorSlot);
/*  70 */       boolean flag = isSlotForLeggings(armorSlot);
/*     */       
/*  72 */       if (!Config.isCustomItems() || !CustomItems.bindCustomArmorTexture(itemstack, flag ? 2 : 1, (String)null))
/*     */       {
/*  74 */         if (Reflector.ForgeHooksClient_getArmorTexture.exists()) {
/*     */           
/*  76 */           this.renderer.bindTexture(getArmorResource((Entity)entitylivingbaseIn, itemstack, flag ? 2 : 1, (String)null));
/*     */         }
/*     */         else {
/*     */           
/*  80 */           this.renderer.bindTexture(getArmorResource(itemarmor, flag));
/*     */         } 
/*     */       }
/*     */       
/*  84 */       if (Reflector.ForgeHooksClient_getArmorTexture.exists()) {
/*     */         
/*  86 */         if (ReflectorForge.armorHasOverlay(itemarmor, itemstack)) {
/*     */           
/*  88 */           int j = itemarmor.getColor(itemstack);
/*  89 */           float f3 = (j >> 16 & 0xFF) / 255.0F;
/*  90 */           float f4 = (j >> 8 & 0xFF) / 255.0F;
/*  91 */           float f5 = (j & 0xFF) / 255.0F;
/*  92 */           getClass(); getClass(); getClass(); getClass(); GlStateManager.color(1.0F * f3, 1.0F * f4, 1.0F * f5, 1.0F);
/*  93 */           t.render((Entity)entitylivingbaseIn, p_177182_2_, p_177182_3_, p_177182_5_, p_177182_6_, p_177182_7_, scale);
/*     */           
/*  95 */           if (!Config.isCustomItems() || !CustomItems.bindCustomArmorTexture(itemstack, flag ? 2 : 1, "overlay"))
/*     */           {
/*  97 */             this.renderer.bindTexture(getArmorResource((Entity)entitylivingbaseIn, itemstack, flag ? 2 : 1, "overlay"));
/*     */           }
/*     */         } 
/*     */         
/* 101 */         getClass(); getClass(); getClass(); getClass(); GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
/* 102 */         t.render((Entity)entitylivingbaseIn, p_177182_2_, p_177182_3_, p_177182_5_, p_177182_6_, p_177182_7_, scale);
/*     */         
/* 104 */         if (!this.skipRenderGlint && itemstack.hasEffect() && (!Config.isCustomItems() || !CustomItems.renderCustomArmorEffect(entitylivingbaseIn, itemstack, (ModelBase)t, p_177182_2_, p_177182_3_, partialTicks, p_177182_5_, p_177182_6_, p_177182_7_, scale)))
/*     */         {
/* 106 */           renderGlint(entitylivingbaseIn, t, p_177182_2_, p_177182_3_, partialTicks, p_177182_5_, p_177182_6_, p_177182_7_, scale);
/*     */         }
/*     */         
/*     */         return;
/*     */       } 
/*     */       
/* 112 */       switch (itemarmor.getArmorMaterial()) {
/*     */         
/*     */         case LEATHER:
/* 115 */           i = itemarmor.getColor(itemstack);
/* 116 */           f = (i >> 16 & 0xFF) / 255.0F;
/* 117 */           f1 = (i >> 8 & 0xFF) / 255.0F;
/* 118 */           f2 = (i & 0xFF) / 255.0F;
/* 119 */           getClass(); getClass(); getClass(); getClass(); GlStateManager.color(1.0F * f, 1.0F * f1, 1.0F * f2, 1.0F);
/* 120 */           t.render((Entity)entitylivingbaseIn, p_177182_2_, p_177182_3_, p_177182_5_, p_177182_6_, p_177182_7_, scale);
/*     */           
/* 122 */           if (!Config.isCustomItems() || !CustomItems.bindCustomArmorTexture(itemstack, flag ? 2 : 1, "overlay"))
/*     */           {
/* 124 */             this.renderer.bindTexture(getArmorResource(itemarmor, flag, "overlay"));
/*     */           }
/*     */         
/*     */         case CHAIN:
/*     */         case IRON:
/*     */         case GOLD:
/*     */         case DIAMOND:
/* 131 */           getClass(); getClass(); getClass(); getClass(); GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
/* 132 */           t.render((Entity)entitylivingbaseIn, p_177182_2_, p_177182_3_, p_177182_5_, p_177182_6_, p_177182_7_, scale);
/*     */           break;
/*     */       } 
/* 135 */       if (!this.skipRenderGlint && itemstack.isItemEnchanted() && (!Config.isCustomItems() || !CustomItems.renderCustomArmorEffect(entitylivingbaseIn, itemstack, (ModelBase)t, p_177182_2_, p_177182_3_, partialTicks, p_177182_5_, p_177182_6_, p_177182_7_, scale)))
/*     */       {
/* 137 */         renderGlint(entitylivingbaseIn, t, p_177182_2_, p_177182_3_, partialTicks, p_177182_5_, p_177182_6_, p_177182_7_, scale);
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public ItemStack getCurrentArmor(EntityLivingBase entitylivingbaseIn, int armorSlot) {
/* 144 */     return entitylivingbaseIn.getCurrentArmor(armorSlot - 1);
/*     */   }
/*     */ 
/*     */   
/*     */   public T getArmorModel(int armorSlot) {
/* 149 */     return isSlotForLeggings(armorSlot) ? this.modelLeggings : this.modelArmor;
/*     */   }
/*     */ 
/*     */   
/*     */   private boolean isSlotForLeggings(int armorSlot) {
/* 154 */     return (armorSlot == 2);
/*     */   }
/*     */ 
/*     */   
/*     */   private void renderGlint(EntityLivingBase entitylivingbaseIn, T modelbaseIn, float p_177183_3_, float p_177183_4_, float partialTicks, float p_177183_6_, float p_177183_7_, float p_177183_8_, float scale) {
/* 159 */     if (!Config.isShaders() || !Shaders.isShadowPass) {
/*     */       
/* 161 */       float f = entitylivingbaseIn.ticksExisted + partialTicks;
/* 162 */       this.renderer.bindTexture(ENCHANTED_ITEM_GLINT_RES);
/*     */       
/* 164 */       if (Config.isShaders())
/*     */       {
/* 166 */         ShadersRender.renderEnchantedGlintBegin();
/*     */       }
/*     */       
/* 169 */       GlStateManager.enableBlend();
/* 170 */       GlStateManager.depthFunc(514);
/* 171 */       GlStateManager.depthMask(false);
/* 172 */       float f1 = 0.5F;
/* 173 */       GlStateManager.color(f1, f1, f1, 1.0F);
/*     */       
/* 175 */       for (int i = 0; i < 2; i++) {
/*     */         
/* 177 */         GlStateManager.disableLighting();
/* 178 */         GlStateManager.blendFunc(768, 1);
/* 179 */         float f2 = 0.76F;
/* 180 */         GlStateManager.color(0.5F * f2, 0.25F * f2, 0.8F * f2, 1.0F);
/* 181 */         GlStateManager.matrixMode(5890);
/* 182 */         GlStateManager.loadIdentity();
/* 183 */         float f3 = 0.33333334F;
/* 184 */         GlStateManager.scale(f3, f3, f3);
/* 185 */         GlStateManager.rotate(30.0F - i * 60.0F, 0.0F, 0.0F, 1.0F);
/* 186 */         GlStateManager.translate(0.0F, f * (0.001F + i * 0.003F) * 20.0F, 0.0F);
/* 187 */         GlStateManager.matrixMode(5888);
/* 188 */         modelbaseIn.render((Entity)entitylivingbaseIn, p_177183_3_, p_177183_4_, p_177183_6_, p_177183_7_, p_177183_8_, scale);
/*     */       } 
/*     */       
/* 191 */       GlStateManager.matrixMode(5890);
/* 192 */       GlStateManager.loadIdentity();
/* 193 */       GlStateManager.matrixMode(5888);
/* 194 */       GlStateManager.enableLighting();
/* 195 */       GlStateManager.depthMask(true);
/* 196 */       GlStateManager.depthFunc(515);
/* 197 */       GlStateManager.disableBlend();
/*     */       
/* 199 */       if (Config.isShaders())
/*     */       {
/* 201 */         ShadersRender.renderEnchantedGlintEnd();
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private ResourceLocation getArmorResource(ItemArmor p_177181_1_, boolean p_177181_2_) {
/* 208 */     return getArmorResource(p_177181_1_, p_177181_2_, (String)null);
/*     */   }
/*     */ 
/*     */   
/*     */   private ResourceLocation getArmorResource(ItemArmor p_177178_1_, boolean p_177178_2_, String p_177178_3_) {
/* 213 */     String s = String.format("textures/models/armor/%s_layer_%d%s.png", new Object[] { p_177178_1_.getArmorMaterial().getName(), Integer.valueOf(p_177178_2_ ? 2 : 1), (p_177178_3_ == null) ? "" : String.format("_%s", new Object[] { p_177178_3_ }) });
/* 214 */     ResourceLocation resourcelocation = ARMOR_TEXTURE_RES_MAP.get(s);
/*     */     
/* 216 */     if (resourcelocation == null) {
/*     */       
/* 218 */       resourcelocation = new ResourceLocation(s);
/* 219 */       ARMOR_TEXTURE_RES_MAP.put(s, resourcelocation);
/*     */     } 
/*     */     
/* 222 */     return resourcelocation;
/*     */   }
/*     */ 
/*     */   
/*     */   protected abstract void initArmor();
/*     */   
/*     */   protected abstract void setModelPartVisible(T paramT, int paramInt);
/*     */   
/*     */   protected T getArmorModelHook(EntityLivingBase p_getArmorModelHook_1_, ItemStack p_getArmorModelHook_2_, int p_getArmorModelHook_3_, T p_getArmorModelHook_4_) {
/* 231 */     return p_getArmorModelHook_4_;
/*     */   }
/*     */ 
/*     */   
/*     */   public ResourceLocation getArmorResource(Entity p_getArmorResource_1_, ItemStack p_getArmorResource_2_, int p_getArmorResource_3_, String p_getArmorResource_4_) {
/* 236 */     ItemArmor itemarmor = (ItemArmor)p_getArmorResource_2_.getItem();
/* 237 */     String s = itemarmor.getArmorMaterial().getName();
/* 238 */     String s1 = "minecraft";
/* 239 */     int i = s.indexOf(':');
/*     */     
/* 241 */     if (i != -1) {
/*     */       
/* 243 */       s1 = s.substring(0, i);
/* 244 */       s = s.substring(i + 1);
/*     */     } 
/*     */     
/* 247 */     String s2 = String.format("%s:textures/models/armor/%s_layer_%d%s.png", new Object[] { s1, s, Integer.valueOf(isSlotForLeggings(p_getArmorResource_3_) ? 2 : 1), (p_getArmorResource_4_ == null) ? "" : String.format("_%s", new Object[] { p_getArmorResource_4_ }) });
/* 248 */     s2 = Reflector.callString(Reflector.ForgeHooksClient_getArmorTexture, new Object[] { p_getArmorResource_1_, p_getArmorResource_2_, s2, Integer.valueOf(p_getArmorResource_3_), p_getArmorResource_4_ });
/* 249 */     ResourceLocation resourcelocation = ARMOR_TEXTURE_RES_MAP.get(s2);
/*     */     
/* 251 */     if (resourcelocation == null) {
/*     */       
/* 253 */       resourcelocation = new ResourceLocation(s2);
/* 254 */       ARMOR_TEXTURE_RES_MAP.put(s2, resourcelocation);
/*     */     } 
/*     */     
/* 257 */     return resourcelocation;
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\minecraft\client\renderer\entity\layers\LayerArmorBase.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */