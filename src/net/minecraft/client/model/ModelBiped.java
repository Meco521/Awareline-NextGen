/*     */ package net.minecraft.client.model;
/*     */ 
/*     */ import awareline.main.mod.implement.visual.Animations;
/*     */ import awareline.main.mod.implement.visual.Dab;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.renderer.GlStateManager;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.util.MathHelper;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ModelBiped
/*     */   extends ModelBase
/*     */ {
/*     */   public ModelRenderer bipedHead;
/*     */   public ModelRenderer bipedHeadwear;
/*     */   public ModelRenderer bipedBody;
/*     */   public ModelRenderer bipedRightArm;
/*     */   public ModelRenderer bipedLeftArm;
/*     */   public ModelRenderer bipedRightLeg;
/*     */   public ModelRenderer bipedLeftLeg;
/*     */   public int heldItemLeft;
/*     */   public int heldItemRight;
/*     */   public boolean isSneak;
/*     */   public boolean aimedBow;
/*     */   
/*     */   public ModelBiped() {
/*  46 */     this(0.0F);
/*     */   }
/*     */ 
/*     */   
/*     */   public ModelBiped(float modelSize) {
/*  51 */     this(modelSize, 0.0F, 64, 32);
/*     */   }
/*     */ 
/*     */   
/*     */   public ModelBiped(float modelSize, float p_i1149_2_, int textureWidthIn, int textureHeightIn) {
/*  56 */     this.textureWidth = textureWidthIn;
/*  57 */     this.textureHeight = textureHeightIn;
/*  58 */     this.bipedHead = new ModelRenderer(this, 0, 0);
/*  59 */     this.bipedHead.addBox(-4.0F, -8.0F, -4.0F, 8, 8, 8, modelSize);
/*  60 */     this.bipedHead.setRotationPoint(0.0F, 0.0F + p_i1149_2_, 0.0F);
/*  61 */     this.bipedHeadwear = new ModelRenderer(this, 32, 0);
/*  62 */     this.bipedHeadwear.addBox(-4.0F, -8.0F, -4.0F, 8, 8, 8, modelSize + 0.5F);
/*  63 */     this.bipedHeadwear.setRotationPoint(0.0F, 0.0F + p_i1149_2_, 0.0F);
/*  64 */     this.bipedBody = new ModelRenderer(this, 16, 16);
/*  65 */     this.bipedBody.addBox(-4.0F, 0.0F, -2.0F, 8, 12, 4, modelSize);
/*  66 */     this.bipedBody.setRotationPoint(0.0F, 0.0F + p_i1149_2_, 0.0F);
/*  67 */     this.bipedRightArm = new ModelRenderer(this, 40, 16);
/*  68 */     this.bipedRightArm.addBox(-3.0F, -2.0F, -2.0F, 4, 12, 4, modelSize);
/*  69 */     this.bipedRightArm.setRotationPoint(-5.0F, 2.0F + p_i1149_2_, 0.0F);
/*  70 */     this.bipedLeftArm = new ModelRenderer(this, 40, 16);
/*  71 */     this.bipedLeftArm.mirror = true;
/*  72 */     this.bipedLeftArm.addBox(-1.0F, -2.0F, -2.0F, 4, 12, 4, modelSize);
/*  73 */     this.bipedLeftArm.setRotationPoint(5.0F, 2.0F + p_i1149_2_, 0.0F);
/*  74 */     this.bipedRightLeg = new ModelRenderer(this, 0, 16);
/*  75 */     this.bipedRightLeg.addBox(-2.0F, 0.0F, -2.0F, 4, 12, 4, modelSize);
/*  76 */     this.bipedRightLeg.setRotationPoint(-1.9F, 12.0F + p_i1149_2_, 0.0F);
/*  77 */     this.bipedLeftLeg = new ModelRenderer(this, 0, 16);
/*  78 */     this.bipedLeftLeg.mirror = true;
/*  79 */     this.bipedLeftLeg.addBox(-2.0F, 0.0F, -2.0F, 4, 12, 4, modelSize);
/*  80 */     this.bipedLeftLeg.setRotationPoint(1.9F, 12.0F + p_i1149_2_, 0.0F);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void render(Entity entityIn, float p_78088_2_, float p_78088_3_, float p_78088_4_, float p_78088_5_, float p_78088_6_, float scale) {
/*  88 */     setRotationAngles(p_78088_2_, p_78088_3_, p_78088_4_, p_78088_5_, p_78088_6_, scale, entityIn);
/*  89 */     GlStateManager.pushMatrix();
/*     */     
/*  91 */     if (this.isChild) {
/*     */       
/*  93 */       float f = 2.0F;
/*  94 */       GlStateManager.scale(1.5F / f, 1.5F / f, 1.5F / f);
/*  95 */       GlStateManager.translate(0.0F, 16.0F * scale, 0.0F);
/*  96 */       this.bipedHead.render(scale);
/*  97 */       GlStateManager.popMatrix();
/*  98 */       GlStateManager.pushMatrix();
/*  99 */       GlStateManager.scale(1.0F / f, 1.0F / f, 1.0F / f);
/* 100 */       GlStateManager.translate(0.0F, 24.0F * scale, 0.0F);
/* 101 */       this.bipedBody.render(scale);
/* 102 */       this.bipedRightArm.render(scale);
/* 103 */       this.bipedLeftArm.render(scale);
/* 104 */       this.bipedRightLeg.render(scale);
/* 105 */       this.bipedLeftLeg.render(scale);
/* 106 */       this.bipedHeadwear.render(scale);
/*     */     }
/*     */     else {
/*     */       
/* 110 */       if (entityIn.isSneaking())
/*     */       {
/* 112 */         GlStateManager.translate(0.0F, 0.2F, 0.0F);
/*     */       }
/*     */       
/* 115 */       this.bipedHead.render(scale);
/* 116 */       this.bipedBody.render(scale);
/* 117 */       this.bipedRightArm.render(scale);
/* 118 */       this.bipedLeftArm.render(scale);
/* 119 */       this.bipedRightLeg.render(scale);
/* 120 */       this.bipedLeftLeg.render(scale);
/* 121 */       this.bipedHeadwear.render(scale);
/*     */     } 
/*     */     
/* 124 */     GlStateManager.popMatrix();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setRotationAngles(float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scaleFactor, Entity entityIn) {
/* 134 */     this.bipedHead.rotateAngleY = netHeadYaw / 57.295776F;
/* 135 */     this.bipedHead.rotateAngleX = headPitch / 57.295776F;
/* 136 */     this.bipedRightArm.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F + 3.1415927F) * 2.0F * limbSwingAmount * 0.5F;
/* 137 */     this.bipedLeftArm.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F) * 2.0F * limbSwingAmount * 0.5F;
/* 138 */     this.bipedRightArm.rotateAngleZ = 0.0F;
/* 139 */     this.bipedLeftArm.rotateAngleZ = 0.0F;
/* 140 */     this.bipedRightLeg.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F) * 1.4F * limbSwingAmount;
/* 141 */     this.bipedLeftLeg.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F + 3.1415927F) * 1.4F * limbSwingAmount;
/* 142 */     this.bipedRightLeg.rotateAngleY = 0.0F;
/* 143 */     this.bipedLeftLeg.rotateAngleY = 0.0F;
/*     */     
/* 145 */     if (this.isRiding) {
/*     */       
/* 147 */       this.bipedRightArm.rotateAngleX += -0.62831855F;
/* 148 */       this.bipedLeftArm.rotateAngleX += -0.62831855F;
/* 149 */       this.bipedRightLeg.rotateAngleX = -1.2566371F;
/* 150 */       this.bipedLeftLeg.rotateAngleX = -1.2566371F;
/* 151 */       this.bipedRightLeg.rotateAngleY = 0.31415927F;
/* 152 */       this.bipedLeftLeg.rotateAngleY = -0.31415927F;
/*     */     } 
/*     */     
/* 155 */     if (this.heldItemLeft != 0)
/*     */     {
/* 157 */       this.bipedLeftArm.rotateAngleX = this.bipedLeftArm.rotateAngleX * 0.5F - 0.31415927F * this.heldItemLeft;
/*     */     }
/*     */     
/* 160 */     this.bipedRightArm.rotateAngleY = 0.0F;
/* 161 */     this.bipedRightArm.rotateAngleZ = 0.0F;
/*     */     
/* 163 */     switch (this.heldItemRight) {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*     */       case 1:
/* 171 */         this.bipedRightArm.rotateAngleX = this.bipedRightArm.rotateAngleX * 0.5F - 0.31415927F * this.heldItemRight;
/*     */         break;
/*     */       
/*     */       case 3:
/* 175 */         this.bipedRightArm.rotateAngleX = this.bipedRightArm.rotateAngleX * 0.5F - 0.31415927F * this.heldItemRight;
/* 176 */         this.bipedRightArm.rotateAngleY = -0.5235988F;
/*     */         break;
/*     */     } 
/* 179 */     this.bipedLeftArm.rotateAngleY = 0.0F;
/* 180 */     if (Dab.getInstance.isEnabled() && entityIn
/* 181 */       .equals((Minecraft.getMinecraft()).thePlayer) && (Minecraft.getMinecraft()).gameSettings.thirdPersonView > 0) {
/* 182 */       this.bipedHead.rotateAngleX = 0.5F;
/* 183 */       this.bipedHead.rotateAngleY = 0.75F;
/* 184 */       this.bipedRightArm.rotateAngleX = 4.75F;
/* 185 */       this.bipedRightArm.rotateAngleY = -1.0F;
/* 186 */       this.bipedLeftArm.rotateAngleX = 4.5F;
/* 187 */       this.bipedLeftArm.rotateAngleY = -1.25F;
/*     */     } 
/* 189 */     if (this.heldItemRight == 3 && entityIn == (Minecraft.getMinecraft()).thePlayer && Animations.getInstance
/* 190 */       .isEnabled() && ((Boolean)Animations.getInstance.betterThirdPersonBlock
/* 191 */       .getValue()).booleanValue()) {
/* 192 */       this.bipedRightArm.rotateAngleY = 0.0F;
/*     */     }
/* 194 */     if (this.swingProgress > -9990.0F) {
/*     */       
/* 196 */       float f = this.swingProgress;
/* 197 */       this.bipedBody.rotateAngleY = MathHelper.sin(MathHelper.sqrt_float(f) * 3.1415927F * 2.0F) * 0.2F;
/* 198 */       this.bipedRightArm.rotationPointZ = MathHelper.sin(this.bipedBody.rotateAngleY) * 5.0F;
/* 199 */       this.bipedRightArm.rotationPointX = -MathHelper.cos(this.bipedBody.rotateAngleY) * 5.0F;
/* 200 */       this.bipedLeftArm.rotationPointZ = -MathHelper.sin(this.bipedBody.rotateAngleY) * 5.0F;
/* 201 */       this.bipedLeftArm.rotationPointX = MathHelper.cos(this.bipedBody.rotateAngleY) * 5.0F;
/* 202 */       this.bipedRightArm.rotateAngleY += this.bipedBody.rotateAngleY;
/* 203 */       this.bipedLeftArm.rotateAngleY += this.bipedBody.rotateAngleY;
/* 204 */       this.bipedLeftArm.rotateAngleX += this.bipedBody.rotateAngleY;
/* 205 */       f = 1.0F - this.swingProgress;
/* 206 */       f *= f;
/* 207 */       f *= f;
/* 208 */       f = 1.0F - f;
/* 209 */       float f1 = MathHelper.sin(f * 3.1415927F);
/* 210 */       float f2 = MathHelper.sin(this.swingProgress * 3.1415927F) * -(this.bipedHead.rotateAngleX - 0.7F) * 0.75F;
/* 211 */       this.bipedRightArm.rotateAngleX = (float)(this.bipedRightArm.rotateAngleX - f1 * 1.2D + f2);
/* 212 */       this.bipedRightArm.rotateAngleY += this.bipedBody.rotateAngleY * 2.0F;
/* 213 */       this.bipedRightArm.rotateAngleZ += MathHelper.sin(this.swingProgress * 3.1415927F) * -0.4F;
/*     */     } 
/*     */     
/* 216 */     if (this.isSneak) {
/*     */       
/* 218 */       this.bipedBody.rotateAngleX = 0.5F;
/* 219 */       this.bipedRightArm.rotateAngleX += 0.4F;
/* 220 */       this.bipedLeftArm.rotateAngleX += 0.4F;
/* 221 */       this.bipedRightLeg.rotationPointZ = 4.0F;
/* 222 */       this.bipedLeftLeg.rotationPointZ = 4.0F;
/* 223 */       this.bipedRightLeg.rotationPointY = 9.0F;
/* 224 */       this.bipedLeftLeg.rotationPointY = 9.0F;
/* 225 */       this.bipedHead.rotationPointY = 1.0F;
/*     */     }
/*     */     else {
/*     */       
/* 229 */       this.bipedBody.rotateAngleX = 0.0F;
/* 230 */       this.bipedRightLeg.rotationPointZ = 0.1F;
/* 231 */       this.bipedLeftLeg.rotationPointZ = 0.1F;
/* 232 */       this.bipedRightLeg.rotationPointY = 12.0F;
/* 233 */       this.bipedLeftLeg.rotationPointY = 12.0F;
/* 234 */       this.bipedHead.rotationPointY = 0.0F;
/*     */     } 
/*     */     
/* 237 */     this.bipedRightArm.rotateAngleZ += MathHelper.cos(ageInTicks * 0.09F) * 0.05F + 0.05F;
/* 238 */     this.bipedLeftArm.rotateAngleZ -= MathHelper.cos(ageInTicks * 0.09F) * 0.05F + 0.05F;
/* 239 */     this.bipedRightArm.rotateAngleX += MathHelper.sin(ageInTicks * 0.067F) * 0.05F;
/* 240 */     this.bipedLeftArm.rotateAngleX -= MathHelper.sin(ageInTicks * 0.067F) * 0.05F;
/*     */     
/* 242 */     if (this.aimedBow) {
/*     */       
/* 244 */       float f3 = 0.0F;
/* 245 */       float f4 = 0.0F;
/* 246 */       this.bipedRightArm.rotateAngleZ = 0.0F;
/* 247 */       this.bipedLeftArm.rotateAngleZ = 0.0F;
/* 248 */       this.bipedRightArm.rotateAngleY = -(0.1F - f3 * 0.6F) + this.bipedHead.rotateAngleY;
/* 249 */       this.bipedLeftArm.rotateAngleY = 0.1F - f3 * 0.6F + this.bipedHead.rotateAngleY + 0.4F;
/* 250 */       this.bipedRightArm.rotateAngleX = -1.5707964F + this.bipedHead.rotateAngleX;
/* 251 */       this.bipedLeftArm.rotateAngleX = -1.5707964F + this.bipedHead.rotateAngleX;
/* 252 */       this.bipedRightArm.rotateAngleX -= f3 * 1.2F - f4 * 0.4F;
/* 253 */       this.bipedLeftArm.rotateAngleX -= f3 * 1.2F - f4 * 0.4F;
/* 254 */       this.bipedRightArm.rotateAngleZ += MathHelper.cos(ageInTicks * 0.09F) * 0.05F + 0.05F;
/* 255 */       this.bipedLeftArm.rotateAngleZ -= MathHelper.cos(ageInTicks * 0.09F) * 0.05F + 0.05F;
/* 256 */       this.bipedRightArm.rotateAngleX += MathHelper.sin(ageInTicks * 0.067F) * 0.05F;
/* 257 */       this.bipedLeftArm.rotateAngleX -= MathHelper.sin(ageInTicks * 0.067F) * 0.05F;
/*     */     } 
/*     */     
/* 260 */     copyModelAngles(this.bipedHead, this.bipedHeadwear);
/*     */   }
/*     */ 
/*     */   
/*     */   public void setModelAttributes(ModelBase model) {
/* 265 */     super.setModelAttributes(model);
/*     */     
/* 267 */     if (model instanceof ModelBiped) {
/*     */       
/* 269 */       ModelBiped modelbiped = (ModelBiped)model;
/* 270 */       this.heldItemLeft = modelbiped.heldItemLeft;
/* 271 */       this.heldItemRight = modelbiped.heldItemRight;
/* 272 */       this.isSneak = modelbiped.isSneak;
/* 273 */       this.aimedBow = modelbiped.aimedBow;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void setInvisible(boolean invisible) {
/* 279 */     this.bipedHead.showModel = invisible;
/* 280 */     this.bipedHeadwear.showModel = invisible;
/* 281 */     this.bipedBody.showModel = invisible;
/* 282 */     this.bipedRightArm.showModel = invisible;
/* 283 */     this.bipedLeftArm.showModel = invisible;
/* 284 */     this.bipedRightLeg.showModel = invisible;
/* 285 */     this.bipedLeftLeg.showModel = invisible;
/*     */   }
/*     */ 
/*     */   
/*     */   public void postRenderArm(float scale) {
/* 290 */     this.bipedRightArm.postRender(scale);
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\minecraft\client\model\ModelBiped.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */