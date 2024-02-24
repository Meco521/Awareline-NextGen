/*     */ package net.minecraft.client.renderer.entity;
/*     */ import com.google.common.collect.Maps;
/*     */ import java.util.Map;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.model.ModelBase;
/*     */ import net.minecraft.client.model.ModelHorse;
/*     */ import net.minecraft.client.renderer.texture.ITextureObject;
/*     */ import net.minecraft.client.renderer.texture.LayeredTexture;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.EntityLivingBase;
/*     */ import net.minecraft.entity.passive.EntityHorse;
/*     */ import net.minecraft.util.ResourceLocation;
/*     */ 
/*     */ public class RenderHorse extends RenderLiving<EntityHorse> {
/*  15 */   private static final Map<String, ResourceLocation> field_110852_a = Maps.newHashMap();
/*  16 */   private static final ResourceLocation whiteHorseTextures = new ResourceLocation("textures/entity/horse/horse_white.png");
/*  17 */   private static final ResourceLocation muleTextures = new ResourceLocation("textures/entity/horse/mule.png");
/*  18 */   private static final ResourceLocation donkeyTextures = new ResourceLocation("textures/entity/horse/donkey.png");
/*  19 */   private static final ResourceLocation zombieHorseTextures = new ResourceLocation("textures/entity/horse/horse_zombie.png");
/*  20 */   private static final ResourceLocation skeletonHorseTextures = new ResourceLocation("textures/entity/horse/horse_skeleton.png");
/*     */ 
/*     */   
/*     */   public RenderHorse(RenderManager rendermanagerIn, ModelHorse model, float shadowSizeIn) {
/*  24 */     super(rendermanagerIn, (ModelBase)model, shadowSizeIn);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void preRenderCallback(EntityHorse entitylivingbaseIn, float partialTickTime) {
/*  33 */     float f = 1.0F;
/*  34 */     int i = entitylivingbaseIn.getHorseType();
/*     */     
/*  36 */     if (i == 1) {
/*     */       
/*  38 */       f *= 0.87F;
/*     */     }
/*  40 */     else if (i == 2) {
/*     */       
/*  42 */       f *= 0.92F;
/*     */     } 
/*     */     
/*  45 */     GlStateManager.scale(f, f, f);
/*  46 */     super.preRenderCallback(entitylivingbaseIn, partialTickTime);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected ResourceLocation getEntityTexture(EntityHorse entity) {
/*  54 */     if (!entity.func_110239_cn()) {
/*     */       
/*  56 */       switch (entity.getHorseType()) {
/*     */ 
/*     */         
/*     */         default:
/*  60 */           return whiteHorseTextures;
/*     */         
/*     */         case 1:
/*  63 */           return donkeyTextures;
/*     */         
/*     */         case 2:
/*  66 */           return muleTextures;
/*     */         
/*     */         case 3:
/*  69 */           return zombieHorseTextures;
/*     */         case 4:
/*     */           break;
/*  72 */       }  return skeletonHorseTextures;
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/*  77 */     return func_110848_b(entity);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private ResourceLocation func_110848_b(EntityHorse horse) {
/*  83 */     String s = horse.getHorseTexture();
/*     */     
/*  85 */     if (!horse.func_175507_cI())
/*     */     {
/*  87 */       return null;
/*     */     }
/*     */ 
/*     */     
/*  91 */     ResourceLocation resourcelocation = field_110852_a.get(s);
/*     */     
/*  93 */     if (resourcelocation == null) {
/*     */       
/*  95 */       resourcelocation = new ResourceLocation(s);
/*  96 */       Minecraft.getMinecraft().getTextureManager().loadTexture(resourcelocation, (ITextureObject)new LayeredTexture(horse.getVariantTexturePaths()));
/*  97 */       field_110852_a.put(s, resourcelocation);
/*     */     } 
/*     */     
/* 100 */     return resourcelocation;
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\minecraft\client\renderer\entity\RenderHorse.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */