/*     */ package net.minecraft.client.model;
/*     */ 
/*     */ import awareline.main.mod.implement.visual.Skeltal;
/*     */ import net.minecraft.client.renderer.GlStateManager;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ 
/*     */ public class ModelPlayer
/*     */   extends ModelBiped {
/*     */   private final ModelRenderer bipedCape;
/*     */   private final ModelRenderer bipedDeadmau5Head;
/*     */   private final boolean smallArms;
/*     */   private final ModelRenderer left_leg;
/*     */   private final ModelRenderer right_leg;
/*     */   private final ModelRenderer body;
/*     */   public ModelRenderer bipedLeftArmwear;
/*     */   public ModelRenderer bipedRightArmwear;
/*     */   public ModelRenderer bipedLeftLegwear;
/*     */   public ModelRenderer bipedRightLegwear;
/*     */   public ModelRenderer bipedBodyWear;
/*     */   private ModelRenderer eye;
/*     */   
/*     */   public ModelPlayer(float p_i46304_1_, boolean p_i46304_2_) {
/*  24 */     super(p_i46304_1_, 0.0F, 64, 64);
/*  25 */     this.smallArms = p_i46304_2_;
/*  26 */     this.bipedDeadmau5Head = new ModelRenderer(this, 24, 0);
/*  27 */     this.bipedDeadmau5Head.addBox(-3.0F, -6.0F, -1.0F, 6, 6, 1, p_i46304_1_);
/*  28 */     this.bipedCape = new ModelRenderer(this, 0, 0);
/*  29 */     this.bipedCape.setTextureSize(64, 32);
/*  30 */     this.bipedCape.addBox(-5.0F, 0.0F, -1.0F, 10, 16, 1, p_i46304_1_);
/*     */     
/*  32 */     this.body = new ModelRenderer(this);
/*  33 */     this.eye = new ModelRenderer(this);
/*  34 */     this.body.setRotationPoint(0.0F, 0.0F, 0.0F);
/*  35 */     this.eye.setTextureOffset(0, 10).addBox(-3.0F, 7.0F, -4.0F, 6, 4, 1);
/*  36 */     this.body.setTextureOffset(34, 8).addBox(-4.0F, 6.0F, -3.0F, 8, 12, 6);
/*  37 */     this.body.setTextureOffset(15, 10).addBox(-3.0F, 9.0F, 3.0F, 6, 8, 3);
/*  38 */     this.body.setTextureOffset(26, 0).addBox(-3.0F, 5.0F, -3.0F, 6, 1, 6);
/*  39 */     this.left_leg = new ModelRenderer(this);
/*  40 */     this.left_leg.setRotationPoint(-2.0F, 18.0F, 0.0F);
/*  41 */     this.left_leg.setTextureOffset(0, 0).addBox(2.9F, 0.0F, -1.5F, 3, 6, 3, 0.0F);
/*  42 */     this.right_leg = new ModelRenderer(this);
/*  43 */     this.right_leg.setRotationPoint(2.0F, 18.0F, 0.0F);
/*  44 */     this.right_leg.setTextureOffset(13, 0).addBox(-5.9F, 0.0F, -1.5F, 3, 6, 3);
/*     */     
/*  46 */     if (p_i46304_2_) {
/*  47 */       this.bipedLeftArm = new ModelRenderer(this, 32, 48);
/*  48 */       this.bipedLeftArm.addBox(-1.0F, -2.0F, -2.0F, 3, 12, 4, p_i46304_1_);
/*  49 */       this.bipedLeftArm.setRotationPoint(5.0F, 2.5F, 0.0F);
/*  50 */       this.bipedRightArm = new ModelRenderer(this, 40, 16);
/*  51 */       this.bipedRightArm.addBox(-2.0F, -2.0F, -2.0F, 3, 12, 4, p_i46304_1_);
/*  52 */       this.bipedRightArm.setRotationPoint(-5.0F, 2.5F, 0.0F);
/*  53 */       this.bipedLeftArmwear = new ModelRenderer(this, 48, 48);
/*  54 */       this.bipedLeftArmwear.addBox(-1.0F, -2.0F, -2.0F, 3, 12, 4, p_i46304_1_ + 0.25F);
/*  55 */       this.bipedLeftArmwear.setRotationPoint(5.0F, 2.5F, 0.0F);
/*  56 */       this.bipedRightArmwear = new ModelRenderer(this, 40, 32);
/*  57 */       this.bipedRightArmwear.addBox(-2.0F, -2.0F, -2.0F, 3, 12, 4, p_i46304_1_ + 0.25F);
/*  58 */       this.bipedRightArmwear.setRotationPoint(-5.0F, 2.5F, 10.0F);
/*     */     } else {
/*  60 */       this.bipedLeftArm = new ModelRenderer(this, 32, 48);
/*  61 */       this.bipedLeftArm.addBox(-1.0F, -2.0F, -2.0F, 4, 12, 4, p_i46304_1_);
/*  62 */       this.bipedLeftArm.setRotationPoint(5.0F, 2.0F, 0.0F);
/*  63 */       this.bipedLeftArmwear = new ModelRenderer(this, 48, 48);
/*  64 */       this.bipedLeftArmwear.addBox(-1.0F, -2.0F, -2.0F, 4, 12, 4, p_i46304_1_ + 0.25F);
/*  65 */       this.bipedLeftArmwear.setRotationPoint(5.0F, 2.0F, 0.0F);
/*  66 */       this.bipedRightArmwear = new ModelRenderer(this, 40, 32);
/*  67 */       this.bipedRightArmwear.addBox(-3.0F, -2.0F, -2.0F, 4, 12, 4, p_i46304_1_ + 0.25F);
/*  68 */       this.bipedRightArmwear.setRotationPoint(-5.0F, 2.0F, 10.0F);
/*     */     } 
/*     */     
/*  71 */     this.bipedLeftLeg = new ModelRenderer(this, 16, 48);
/*  72 */     this.bipedLeftLeg.addBox(-2.0F, 0.0F, -2.0F, 4, 12, 4, p_i46304_1_);
/*  73 */     this.bipedLeftLeg.setRotationPoint(1.9F, 12.0F, 0.0F);
/*  74 */     this.bipedLeftLegwear = new ModelRenderer(this, 0, 48);
/*  75 */     this.bipedLeftLegwear.addBox(-2.0F, 0.0F, -2.0F, 4, 12, 4, p_i46304_1_ + 0.25F);
/*  76 */     this.bipedLeftLegwear.setRotationPoint(1.9F, 12.0F, 0.0F);
/*  77 */     this.bipedRightLegwear = new ModelRenderer(this, 0, 32);
/*  78 */     this.bipedRightLegwear.addBox(-2.0F, 0.0F, -2.0F, 4, 12, 4, p_i46304_1_ + 0.25F);
/*  79 */     this.bipedRightLegwear.setRotationPoint(-1.9F, 12.0F, 0.0F);
/*  80 */     this.bipedBodyWear = new ModelRenderer(this, 16, 32);
/*  81 */     this.bipedBodyWear.addBox(-4.0F, 0.0F, -2.0F, 8, 12, 4, p_i46304_1_ + 0.25F);
/*  82 */     this.bipedBodyWear.setRotationPoint(0.0F, 0.0F, 0.0F);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void render(Entity entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
/*  89 */     GlStateManager.pushMatrix();
/*  90 */     super.render(entityIn, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale);
/*  91 */     if (this.isChild) {
/*  92 */       float f = 2.0F;
/*  93 */       GlStateManager.scale(1.0F / f, 1.0F / f, 1.0F / f);
/*  94 */       GlStateManager.translate(0.0F, 24.0F * scale, 0.0F);
/*     */     }
/*  96 */     else if (entityIn.isSneaking()) {
/*  97 */       GlStateManager.translate(0.0F, 0.2F, 0.0F);
/*     */     } 
/*     */ 
/*     */     
/* 101 */     this.bipedLeftLegwear.render(scale);
/* 102 */     this.bipedRightLegwear.render(scale);
/* 103 */     this.bipedLeftArmwear.render(scale);
/* 104 */     this.bipedRightArmwear.render(scale);
/* 105 */     this.bipedBodyWear.render(scale);
/*     */     
/* 107 */     GlStateManager.popMatrix();
/*     */   }
/*     */   
/*     */   public void renderDeadmau5Head(float p_178727_1_) {
/* 111 */     copyModelAngles(this.bipedHead, this.bipedDeadmau5Head);
/* 112 */     this.bipedDeadmau5Head.rotationPointX = 0.0F;
/* 113 */     this.bipedDeadmau5Head.rotationPointY = 0.0F;
/* 114 */     this.bipedDeadmau5Head.render(p_178727_1_);
/*     */   }
/*     */   
/*     */   public void renderCape(float p_178728_1_) {
/* 118 */     this.bipedCape.render(p_178728_1_);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setRotationAngles(float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scaleFactor, Entity entityIn) {
/* 127 */     super.setRotationAngles(limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scaleFactor, entityIn);
/* 128 */     copyModelAngles(this.bipedLeftLeg, this.bipedLeftLegwear);
/* 129 */     copyModelAngles(this.bipedRightLeg, this.bipedRightLegwear);
/* 130 */     copyModelAngles(this.bipedLeftArm, this.bipedLeftArmwear);
/* 131 */     copyModelAngles(this.bipedRightArm, this.bipedRightArmwear);
/* 132 */     copyModelAngles(this.bipedBody, this.bipedBodyWear);
/* 133 */     if (Skeltal.getInstance.isEnabled() && entityIn instanceof EntityPlayer)
/*     */     {
/* 135 */       Skeltal.updateModel((EntityPlayer)entityIn, this);
/*     */     }
/*     */   }
/*     */   
/*     */   public void renderRightArm() {
/* 140 */     this.bipedRightArm.render(0.0625F);
/* 141 */     this.bipedRightArmwear.render(0.0625F);
/*     */   }
/*     */   
/*     */   public void renderLeftArm() {
/* 145 */     this.bipedLeftArm.render(0.0625F);
/* 146 */     this.bipedLeftArmwear.render(0.0625F);
/*     */   }
/*     */   
/*     */   public void setInvisible(boolean invisible) {
/* 150 */     super.setInvisible(invisible);
/* 151 */     this.bipedLeftArmwear.showModel = invisible;
/* 152 */     this.bipedRightArmwear.showModel = invisible;
/* 153 */     this.bipedLeftLegwear.showModel = invisible;
/* 154 */     this.bipedRightLegwear.showModel = invisible;
/* 155 */     this.bipedBodyWear.showModel = invisible;
/* 156 */     this.bipedCape.showModel = invisible;
/* 157 */     this.bipedDeadmau5Head.showModel = invisible;
/*     */   }
/*     */   
/*     */   public void postRenderArm(float scale) {
/* 161 */     if (this.smallArms) {
/* 162 */       this.bipedRightArm.rotationPointX++;
/* 163 */       this.bipedRightArm.postRender(scale);
/* 164 */       this.bipedRightArm.rotationPointX--;
/*     */     } else {
/* 166 */       this.bipedRightArm.postRender(scale);
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\minecraft\client\model\ModelPlayer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */