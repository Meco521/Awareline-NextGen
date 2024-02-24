/*     */ package net.minecraft.client.renderer.entity;
/*     */ 
/*     */ import awareline.main.mod.implement.globals.ItemPhysic;
/*     */ import awareline.main.mod.implement.world.utils.DelayTimer;
/*     */ import java.util.Random;
/*     */ import net.minecraft.client.renderer.GlStateManager;
/*     */ import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
/*     */ import net.minecraft.client.renderer.texture.TextureMap;
/*     */ import net.minecraft.client.resources.model.IBakedModel;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.item.EntityItem;
/*     */ import net.minecraft.item.Item;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.util.MathHelper;
/*     */ import net.minecraft.util.ResourceLocation;
/*     */ 
/*     */ public class RenderEntityItem
/*     */   extends Render<EntityItem> {
/*     */   private final RenderItem itemRenderer;
/*  20 */   private final Random field_177079_e = new Random();
/*     */   private final DelayTimer delayTimer;
/*     */   
/*  23 */   public RenderEntityItem(RenderManager renderManagerIn, RenderItem p_i46167_2_) { super(renderManagerIn);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  28 */     this.delayTimer = new DelayTimer(); this.itemRenderer = p_i46167_2_;
/*     */     this.shadowSize = 0.15F;
/*  30 */     this.shadowOpaque = 0.75F; } private int func_177077_a(EntityItem itemIn, double p_177077_2_, double p_177077_4_, double p_177077_6_, float p_177077_8_, IBakedModel p_177077_9_) { ItemStack itemstack = itemIn.getEntityItem();
/*  31 */     Item item = itemstack.getItem();
/*     */     
/*  33 */     if (item == null) {
/*  34 */       return 0;
/*     */     }
/*  36 */     if (ItemPhysic.getInstance.isEnabled()) {
/*  37 */       boolean var12 = p_177077_9_.isAmbientOcclusion();
/*  38 */       int var13 = func_177078_a(itemstack);
/*  39 */       if (!(item instanceof net.minecraft.item.ItemBlock)) {
/*  40 */         GlStateManager.translate((float)p_177077_2_, (float)p_177077_4_ + 0.1D, (float)p_177077_6_);
/*     */       } else {
/*  42 */         GlStateManager.translate((float)p_177077_2_, (float)p_177077_4_ + 0.2D, (float)p_177077_6_);
/*     */       } 
/*     */ 
/*     */       
/*  46 */       float pitch = itemIn.onGround ? 90.0F : itemIn.rotationPitch;
/*     */       
/*  48 */       if (this.delayTimer.hasPassed(5.0D)) {
/*  49 */         itemIn.rotationPitch++;
/*     */       }
/*     */       
/*  52 */       if (itemIn.rotationPitch > 180.0F) {
/*  53 */         itemIn.rotationPitch = -180.0F;
/*     */       }
/*  55 */       GlStateManager.rotate(pitch, 1.0F, 0.0F, 0.0F);
/*     */       
/*  57 */       GlStateManager.rotate(itemIn.rotationYaw, 0.0F, 0.0F, 1.0F);
/*     */       
/*  59 */       if (!var12) {
/*  60 */         float var16 = -0.0F * (var13 - 1) * 0.5F;
/*  61 */         float var17 = -0.0F * (var13 - 1) * 0.5F;
/*  62 */         float var18 = -0.046875F * (var13 - 1) * 0.5F;
/*  63 */         GlStateManager.translate(var16, var17, var18);
/*     */       } 
/*     */       
/*  66 */       GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
/*  67 */       return var13;
/*     */     } 
/*  69 */     boolean flag = p_177077_9_.isGui3d();
/*  70 */     int i = func_177078_a(itemstack);
/*  71 */     float f = 0.25F;
/*  72 */     float f1 = MathHelper.sin((itemIn.getAge() + p_177077_8_) / 10.0F + itemIn.hoverStart) * 0.1F + 0.1F;
/*  73 */     float f2 = (p_177077_9_.getItemCameraTransforms().getTransform(ItemCameraTransforms.TransformType.GROUND)).scale.y;
/*  74 */     GlStateManager.translate((float)p_177077_2_, (float)p_177077_4_ + f1 + 0.25F * f2, (float)p_177077_6_);
/*     */     
/*  76 */     if (flag || this.renderManager.options != null) {
/*  77 */       float f3 = ((itemIn.getAge() + p_177077_8_) / 20.0F + itemIn.hoverStart) * 57.295776F;
/*  78 */       GlStateManager.rotate(f3, 0.0F, 1.0F, 0.0F);
/*     */     } 
/*     */     
/*  81 */     if (!flag) {
/*  82 */       float f6 = -0.0F * (i - 1) * 0.5F;
/*  83 */       float f4 = -0.0F * (i - 1) * 0.5F;
/*  84 */       float f5 = -0.046875F * (i - 1) * 0.5F;
/*  85 */       GlStateManager.translate(f6, f4, f5);
/*     */     } 
/*     */     
/*  88 */     GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
/*  89 */     return i; }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private int func_177078_a(ItemStack stack) {
/*  95 */     int i = 1;
/*     */     
/*  97 */     if (stack.stackSize > 48) {
/*  98 */       i = 5;
/*  99 */     } else if (stack.stackSize > 32) {
/* 100 */       i = 4;
/* 101 */     } else if (stack.stackSize > 16) {
/* 102 */       i = 3;
/* 103 */     } else if (stack.stackSize > 1) {
/* 104 */       i = 2;
/*     */     } 
/*     */     
/* 107 */     return i;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void doRender(EntityItem entity, double x, double y, double z, float entityYaw, float partialTicks) {
/* 114 */     ItemStack itemstack = entity.getEntityItem();
/* 115 */     this.field_177079_e.setSeed(187L);
/* 116 */     boolean flag = false;
/*     */     
/* 118 */     if (bindEntityTexture(entity)) {
/* 119 */       this.renderManager.renderEngine.getTexture(getEntityTexture(entity)).setBlurMipmap(false, false);
/* 120 */       flag = true;
/*     */     } 
/*     */     
/* 123 */     GlStateManager.enableRescaleNormal();
/* 124 */     GlStateManager.alphaFunc(516, 0.1F);
/* 125 */     GlStateManager.enableBlend();
/* 126 */     GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
/* 127 */     GlStateManager.pushMatrix();
/* 128 */     IBakedModel ibakedmodel = this.itemRenderer.getItemModelMesher().getItemModel(itemstack);
/* 129 */     int i = func_177077_a(entity, x, y, z, partialTicks, ibakedmodel);
/*     */     
/* 131 */     for (int j = 0; j < i; j++) {
/* 132 */       if (ibakedmodel.isGui3d()) {
/* 133 */         GlStateManager.pushMatrix();
/*     */         
/* 135 */         if (j > 0) {
/* 136 */           float f = (this.field_177079_e.nextFloat() * 2.0F - 1.0F) * 0.15F;
/* 137 */           float f1 = (this.field_177079_e.nextFloat() * 2.0F - 1.0F) * 0.15F;
/* 138 */           float f2 = (this.field_177079_e.nextFloat() * 2.0F - 1.0F) * 0.15F;
/* 139 */           GlStateManager.translate(f, f1, f2);
/*     */         } 
/*     */         
/* 142 */         GlStateManager.scale(0.5F, 0.5F, 0.5F);
/* 143 */         ibakedmodel.getItemCameraTransforms().applyTransform(ItemCameraTransforms.TransformType.GROUND);
/* 144 */         this.itemRenderer.renderItem(itemstack, ibakedmodel);
/* 145 */         GlStateManager.popMatrix();
/*     */       } else {
/* 147 */         GlStateManager.pushMatrix();
/* 148 */         ibakedmodel.getItemCameraTransforms().applyTransform(ItemCameraTransforms.TransformType.GROUND);
/* 149 */         this.itemRenderer.renderItem(itemstack, ibakedmodel);
/* 150 */         GlStateManager.popMatrix();
/* 151 */         float f3 = (ibakedmodel.getItemCameraTransforms()).ground.scale.x;
/* 152 */         float f4 = (ibakedmodel.getItemCameraTransforms()).ground.scale.y;
/* 153 */         float f5 = (ibakedmodel.getItemCameraTransforms()).ground.scale.z;
/* 154 */         GlStateManager.translate(0.0F * f3, 0.0F * f4, 0.046875F * f5);
/*     */       } 
/*     */     } 
/*     */     
/* 158 */     GlStateManager.popMatrix();
/* 159 */     GlStateManager.disableRescaleNormal();
/* 160 */     GlStateManager.disableBlend();
/* 161 */     bindEntityTexture(entity);
/*     */     
/* 163 */     if (flag) {
/* 164 */       this.renderManager.renderEngine.getTexture(getEntityTexture(entity)).restoreLastBlurMipmap();
/*     */     }
/*     */     
/* 167 */     super.doRender(entity, x, y, z, entityYaw, partialTicks);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected ResourceLocation getEntityTexture(EntityItem entity) {
/* 174 */     return TextureMap.locationBlocksTexture;
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\minecraft\client\renderer\entity\RenderEntityItem.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */