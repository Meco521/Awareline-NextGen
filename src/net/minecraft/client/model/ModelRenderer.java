/*     */ package net.minecraft.client.model;
/*     */ 
/*     */ import com.google.common.collect.Lists;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import net.minecraft.client.renderer.GLAllocation;
/*     */ import net.minecraft.client.renderer.GlStateManager;
/*     */ import net.minecraft.client.renderer.RenderGlobal;
/*     */ import net.minecraft.client.renderer.Tessellator;
/*     */ import net.minecraft.client.renderer.WorldRenderer;
/*     */ import net.minecraft.src.Config;
/*     */ import net.minecraft.util.ResourceLocation;
/*     */ import net.optifine.entity.model.anim.ModelUpdater;
/*     */ import net.optifine.model.ModelSprite;
/*     */ import net.optifine.shaders.Shaders;
/*     */ import org.lwjgl.opengl.GL11;
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
/*     */ public class ModelRenderer
/*     */ {
/*     */   public float textureWidth;
/*     */   public float textureHeight;
/*     */   private int textureOffsetX;
/*     */   private int textureOffsetY;
/*     */   public float rotationPointX;
/*     */   public float rotationPointY;
/*     */   public float rotationPointZ;
/*     */   public float rotateAngleX;
/*     */   public float rotateAngleY;
/*     */   public float rotateAngleZ;
/*     */   private boolean compiled;
/*     */   private int displayList;
/*     */   public boolean mirror;
/*     */   public boolean showModel;
/*     */   public boolean isHidden;
/*     */   public List<ModelBox> cubeList;
/*     */   public List<ModelRenderer> childModels;
/*     */   public final String boxName;
/*     */   private final ModelBase baseModel;
/*     */   public float offsetX;
/*     */   public float offsetY;
/*     */   public float offsetZ;
/*     */   public List spriteList;
/*     */   public boolean mirrorV;
/*     */   public float scaleX;
/*     */   public float scaleY;
/*     */   public float scaleZ;
/*     */   private int countResetDisplayList;
/*     */   private ResourceLocation textureLocation;
/*     */   private String id;
/*     */   private ModelUpdater modelUpdater;
/*     */   private final RenderGlobal renderGlobal;
/*     */   
/*     */   public ModelRenderer(ModelBase model, String boxNameIn) {
/*  63 */     this.spriteList = new ArrayList();
/*  64 */     this.mirrorV = false;
/*  65 */     this.scaleX = 1.0F;
/*  66 */     this.scaleY = 1.0F;
/*  67 */     this.scaleZ = 1.0F;
/*  68 */     this.textureLocation = null;
/*  69 */     this.id = null;
/*  70 */     this.renderGlobal = Config.getRenderGlobal();
/*  71 */     this.textureWidth = 64.0F;
/*  72 */     this.textureHeight = 32.0F;
/*  73 */     this.showModel = true;
/*  74 */     this.cubeList = Lists.newArrayList();
/*  75 */     this.baseModel = model;
/*  76 */     model.boxList.add(this);
/*  77 */     this.boxName = boxNameIn;
/*  78 */     setTextureSize(model.textureWidth, model.textureHeight);
/*     */   }
/*     */ 
/*     */   
/*     */   public ModelRenderer(ModelBase model) {
/*  83 */     this(model, (String)null);
/*     */   }
/*     */ 
/*     */   
/*     */   public ModelRenderer(ModelBase model, int texOffX, int texOffY) {
/*  88 */     this(model);
/*  89 */     setTextureOffset(texOffX, texOffY);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addChild(ModelRenderer renderer) {
/*  97 */     if (this.childModels == null)
/*     */     {
/*  99 */       this.childModels = Lists.newArrayList();
/*     */     }
/*     */     
/* 102 */     this.childModels.add(renderer);
/*     */   }
/*     */ 
/*     */   
/*     */   public ModelRenderer setTextureOffset(int x, int y) {
/* 107 */     this.textureOffsetX = x;
/* 108 */     this.textureOffsetY = y;
/* 109 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public ModelRenderer addBox(String partName, float offX, float offY, float offZ, int width, int height, int depth) {
/* 114 */     partName = this.boxName + "." + partName;
/* 115 */     TextureOffset textureoffset = this.baseModel.getTextureOffset(partName);
/* 116 */     setTextureOffset(textureoffset.textureOffsetX, textureoffset.textureOffsetY);
/* 117 */     this.cubeList.add((new ModelBox(this, this.textureOffsetX, this.textureOffsetY, offX, offY, offZ, width, height, depth, 0.0F)).setBoxName(partName));
/* 118 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public ModelRenderer addBox(float offX, float offY, float offZ, int width, int height, int depth) {
/* 123 */     this.cubeList.add(new ModelBox(this, this.textureOffsetX, this.textureOffsetY, offX, offY, offZ, width, height, depth, 0.0F));
/* 124 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public ModelRenderer addBox(float p_178769_1_, float p_178769_2_, float p_178769_3_, int p_178769_4_, int p_178769_5_, int p_178769_6_, boolean p_178769_7_) {
/* 129 */     this.cubeList.add(new ModelBox(this, this.textureOffsetX, this.textureOffsetY, p_178769_1_, p_178769_2_, p_178769_3_, p_178769_4_, p_178769_5_, p_178769_6_, 0.0F, p_178769_7_));
/* 130 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addBox(float p_78790_1_, float p_78790_2_, float p_78790_3_, int width, int height, int depth, float scaleFactor) {
/* 138 */     this.cubeList.add(new ModelBox(this, this.textureOffsetX, this.textureOffsetY, p_78790_1_, p_78790_2_, p_78790_3_, width, height, depth, scaleFactor));
/*     */   }
/*     */ 
/*     */   
/*     */   public void setRotationPoint(float rotationPointXIn, float rotationPointYIn, float rotationPointZIn) {
/* 143 */     this.rotationPointX = rotationPointXIn;
/* 144 */     this.rotationPointY = rotationPointYIn;
/* 145 */     this.rotationPointZ = rotationPointZIn;
/*     */   }
/*     */ 
/*     */   
/*     */   public void render(float p_78785_1_) {
/* 150 */     if (!this.isHidden && this.showModel) {
/*     */       
/* 152 */       checkResetDisplayList();
/*     */       
/* 154 */       if (!this.compiled)
/*     */       {
/* 156 */         compileDisplayList(p_78785_1_);
/*     */       }
/*     */       
/* 159 */       int i = 0;
/*     */       
/* 161 */       if (this.textureLocation != null && !this.renderGlobal.renderOverlayDamaged) {
/*     */         
/* 163 */         if (this.renderGlobal.renderOverlayEyes) {
/*     */           return;
/*     */         }
/*     */ 
/*     */         
/* 168 */         i = GlStateManager.getBoundTexture();
/* 169 */         Config.getTextureManager().bindTexture(this.textureLocation);
/*     */       } 
/*     */       
/* 172 */       if (this.modelUpdater != null)
/*     */       {
/* 174 */         this.modelUpdater.update();
/*     */       }
/*     */       
/* 177 */       boolean flag = (this.scaleX != 1.0F || this.scaleY != 1.0F || this.scaleZ != 1.0F);
/* 178 */       GlStateManager.translate(this.offsetX, this.offsetY, this.offsetZ);
/*     */       
/* 180 */       if (this.rotateAngleX == 0.0F && this.rotateAngleY == 0.0F && this.rotateAngleZ == 0.0F) {
/*     */         
/* 182 */         if (this.rotationPointX == 0.0F && this.rotationPointY == 0.0F && this.rotationPointZ == 0.0F)
/*     */         {
/* 184 */           if (flag)
/*     */           {
/* 186 */             GlStateManager.scale(this.scaleX, this.scaleY, this.scaleZ);
/*     */           }
/*     */           
/* 189 */           GlStateManager.callList(this.displayList);
/*     */           
/* 191 */           if (this.childModels != null)
/*     */           {
/* 193 */             for (int l = 0; l < this.childModels.size(); l++)
/*     */             {
/* 195 */               ((ModelRenderer)this.childModels.get(l)).render(p_78785_1_);
/*     */             }
/*     */           }
/*     */           
/* 199 */           if (flag)
/*     */           {
/* 201 */             GlStateManager.scale(1.0F / this.scaleX, 1.0F / this.scaleY, 1.0F / this.scaleZ);
/*     */           }
/*     */         }
/*     */         else
/*     */         {
/* 206 */           GlStateManager.translate(this.rotationPointX * p_78785_1_, this.rotationPointY * p_78785_1_, this.rotationPointZ * p_78785_1_);
/*     */           
/* 208 */           if (flag)
/*     */           {
/* 210 */             GlStateManager.scale(this.scaleX, this.scaleY, this.scaleZ);
/*     */           }
/*     */           
/* 213 */           GlStateManager.callList(this.displayList);
/*     */           
/* 215 */           if (this.childModels != null)
/*     */           {
/* 217 */             for (int k = 0; k < this.childModels.size(); k++)
/*     */             {
/* 219 */               ((ModelRenderer)this.childModels.get(k)).render(p_78785_1_);
/*     */             }
/*     */           }
/*     */           
/* 223 */           if (flag)
/*     */           {
/* 225 */             GlStateManager.scale(1.0F / this.scaleX, 1.0F / this.scaleY, 1.0F / this.scaleZ);
/*     */           }
/*     */           
/* 228 */           GlStateManager.translate(-this.rotationPointX * p_78785_1_, -this.rotationPointY * p_78785_1_, -this.rotationPointZ * p_78785_1_);
/*     */         }
/*     */       
/*     */       } else {
/*     */         
/* 233 */         GlStateManager.pushMatrix();
/* 234 */         GlStateManager.translate(this.rotationPointX * p_78785_1_, this.rotationPointY * p_78785_1_, this.rotationPointZ * p_78785_1_);
/*     */         
/* 236 */         if (this.rotateAngleZ != 0.0F)
/*     */         {
/* 238 */           GlStateManager.rotate(this.rotateAngleZ * 57.295776F, 0.0F, 0.0F, 1.0F);
/*     */         }
/*     */         
/* 241 */         if (this.rotateAngleY != 0.0F)
/*     */         {
/* 243 */           GlStateManager.rotate(this.rotateAngleY * 57.295776F, 0.0F, 1.0F, 0.0F);
/*     */         }
/*     */         
/* 246 */         if (this.rotateAngleX != 0.0F)
/*     */         {
/* 248 */           GlStateManager.rotate(this.rotateAngleX * 57.295776F, 1.0F, 0.0F, 0.0F);
/*     */         }
/*     */         
/* 251 */         if (flag)
/*     */         {
/* 253 */           GlStateManager.scale(this.scaleX, this.scaleY, this.scaleZ);
/*     */         }
/*     */         
/* 256 */         GlStateManager.callList(this.displayList);
/*     */         
/* 258 */         if (this.childModels != null)
/*     */         {
/* 260 */           for (int j = 0; j < this.childModels.size(); j++)
/*     */           {
/* 262 */             ((ModelRenderer)this.childModels.get(j)).render(p_78785_1_);
/*     */           }
/*     */         }
/*     */         
/* 266 */         GlStateManager.popMatrix();
/*     */       } 
/*     */       
/* 269 */       GlStateManager.translate(-this.offsetX, -this.offsetY, -this.offsetZ);
/*     */       
/* 271 */       if (i != 0)
/*     */       {
/* 273 */         GlStateManager.bindTexture(i);
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void renderWithRotation(float p_78791_1_) {
/* 280 */     if (!this.isHidden && this.showModel) {
/*     */       
/* 282 */       checkResetDisplayList();
/*     */       
/* 284 */       if (!this.compiled)
/*     */       {
/* 286 */         compileDisplayList(p_78791_1_);
/*     */       }
/*     */       
/* 289 */       int i = 0;
/*     */       
/* 291 */       if (this.textureLocation != null && !this.renderGlobal.renderOverlayDamaged) {
/*     */         
/* 293 */         if (this.renderGlobal.renderOverlayEyes) {
/*     */           return;
/*     */         }
/*     */ 
/*     */         
/* 298 */         i = GlStateManager.getBoundTexture();
/* 299 */         Config.getTextureManager().bindTexture(this.textureLocation);
/*     */       } 
/*     */       
/* 302 */       if (this.modelUpdater != null)
/*     */       {
/* 304 */         this.modelUpdater.update();
/*     */       }
/*     */       
/* 307 */       boolean flag = (this.scaleX != 1.0F || this.scaleY != 1.0F || this.scaleZ != 1.0F);
/* 308 */       GlStateManager.pushMatrix();
/* 309 */       GlStateManager.translate(this.rotationPointX * p_78791_1_, this.rotationPointY * p_78791_1_, this.rotationPointZ * p_78791_1_);
/*     */       
/* 311 */       if (this.rotateAngleY != 0.0F)
/*     */       {
/* 313 */         GlStateManager.rotate(this.rotateAngleY * 57.295776F, 0.0F, 1.0F, 0.0F);
/*     */       }
/*     */       
/* 316 */       if (this.rotateAngleX != 0.0F)
/*     */       {
/* 318 */         GlStateManager.rotate(this.rotateAngleX * 57.295776F, 1.0F, 0.0F, 0.0F);
/*     */       }
/*     */       
/* 321 */       if (this.rotateAngleZ != 0.0F)
/*     */       {
/* 323 */         GlStateManager.rotate(this.rotateAngleZ * 57.295776F, 0.0F, 0.0F, 1.0F);
/*     */       }
/*     */       
/* 326 */       if (flag)
/*     */       {
/* 328 */         GlStateManager.scale(this.scaleX, this.scaleY, this.scaleZ);
/*     */       }
/*     */       
/* 331 */       GlStateManager.callList(this.displayList);
/*     */       
/* 333 */       if (this.childModels != null)
/*     */       {
/* 335 */         for (int j = 0; j < this.childModels.size(); j++)
/*     */         {
/* 337 */           ((ModelRenderer)this.childModels.get(j)).render(p_78791_1_);
/*     */         }
/*     */       }
/*     */       
/* 341 */       GlStateManager.popMatrix();
/*     */       
/* 343 */       if (i != 0)
/*     */       {
/* 345 */         GlStateManager.bindTexture(i);
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void postRender(float scale) {
/* 355 */     if (!this.isHidden && this.showModel) {
/*     */       
/* 357 */       checkResetDisplayList();
/*     */       
/* 359 */       if (!this.compiled)
/*     */       {
/* 361 */         compileDisplayList(scale);
/*     */       }
/*     */       
/* 364 */       if (this.rotateAngleX == 0.0F && this.rotateAngleY == 0.0F && this.rotateAngleZ == 0.0F) {
/*     */         
/* 366 */         if (this.rotationPointX != 0.0F || this.rotationPointY != 0.0F || this.rotationPointZ != 0.0F)
/*     */         {
/* 368 */           GlStateManager.translate(this.rotationPointX * scale, this.rotationPointY * scale, this.rotationPointZ * scale);
/*     */         }
/*     */       }
/*     */       else {
/*     */         
/* 373 */         GlStateManager.translate(this.rotationPointX * scale, this.rotationPointY * scale, this.rotationPointZ * scale);
/*     */         
/* 375 */         if (this.rotateAngleZ != 0.0F)
/*     */         {
/* 377 */           GlStateManager.rotate(this.rotateAngleZ * 57.295776F, 0.0F, 0.0F, 1.0F);
/*     */         }
/*     */         
/* 380 */         if (this.rotateAngleY != 0.0F)
/*     */         {
/* 382 */           GlStateManager.rotate(this.rotateAngleY * 57.295776F, 0.0F, 1.0F, 0.0F);
/*     */         }
/*     */         
/* 385 */         if (this.rotateAngleX != 0.0F)
/*     */         {
/* 387 */           GlStateManager.rotate(this.rotateAngleX * 57.295776F, 1.0F, 0.0F, 0.0F);
/*     */         }
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void compileDisplayList(float scale) {
/* 398 */     if (this.displayList == 0)
/*     */     {
/* 400 */       this.displayList = GLAllocation.generateDisplayLists(1);
/*     */     }
/*     */     
/* 403 */     GL11.glNewList(this.displayList, 4864);
/* 404 */     WorldRenderer worldrenderer = Tessellator.getInstance().getWorldRenderer();
/*     */     
/* 406 */     for (int i = 0; i < this.cubeList.size(); i++)
/*     */     {
/* 408 */       ((ModelBox)this.cubeList.get(i)).render(worldrenderer, scale);
/*     */     }
/*     */     
/* 411 */     for (int j = 0; j < this.spriteList.size(); j++) {
/*     */       
/* 413 */       ModelSprite modelsprite = this.spriteList.get(j);
/* 414 */       modelsprite.render(Tessellator.getInstance(), scale);
/*     */     } 
/*     */     
/* 417 */     GL11.glEndList();
/* 418 */     this.compiled = true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ModelRenderer setTextureSize(int textureWidthIn, int textureHeightIn) {
/* 426 */     this.textureWidth = textureWidthIn;
/* 427 */     this.textureHeight = textureHeightIn;
/* 428 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public void addSprite(float p_addSprite_1_, float p_addSprite_2_, float p_addSprite_3_, int p_addSprite_4_, int p_addSprite_5_, int p_addSprite_6_, float p_addSprite_7_) {
/* 433 */     this.spriteList.add(new ModelSprite(this, this.textureOffsetX, this.textureOffsetY, p_addSprite_1_, p_addSprite_2_, p_addSprite_3_, p_addSprite_4_, p_addSprite_5_, p_addSprite_6_, p_addSprite_7_));
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean getCompiled() {
/* 438 */     return this.compiled;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getDisplayList() {
/* 443 */     return this.displayList;
/*     */   }
/*     */ 
/*     */   
/*     */   private void checkResetDisplayList() {
/* 448 */     if (this.countResetDisplayList != Shaders.countResetDisplayLists) {
/*     */       
/* 450 */       this.compiled = false;
/* 451 */       this.countResetDisplayList = Shaders.countResetDisplayLists;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public ResourceLocation getTextureLocation() {
/* 457 */     return this.textureLocation;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setTextureLocation(ResourceLocation p_setTextureLocation_1_) {
/* 462 */     this.textureLocation = p_setTextureLocation_1_;
/*     */   }
/*     */ 
/*     */   
/*     */   public String getId() {
/* 467 */     return this.id;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setId(String p_setId_1_) {
/* 472 */     this.id = p_setId_1_;
/*     */   }
/*     */ 
/*     */   
/*     */   public void addBox(int[][] p_addBox_1_, float p_addBox_2_, float p_addBox_3_, float p_addBox_4_, float p_addBox_5_, float p_addBox_6_, float p_addBox_7_, float p_addBox_8_) {
/* 477 */     this.cubeList.add(new ModelBox(this, p_addBox_1_, p_addBox_2_, p_addBox_3_, p_addBox_4_, p_addBox_5_, p_addBox_6_, p_addBox_7_, p_addBox_8_, this.mirror));
/*     */   }
/*     */ 
/*     */   
/*     */   public ModelRenderer getChild(String p_getChild_1_) {
/* 482 */     if (p_getChild_1_ == null)
/*     */     {
/* 484 */       return null;
/*     */     }
/*     */ 
/*     */     
/* 488 */     if (this.childModels != null)
/*     */     {
/* 490 */       for (int i = 0; i < this.childModels.size(); i++) {
/*     */         
/* 492 */         ModelRenderer modelrenderer = this.childModels.get(i);
/*     */         
/* 494 */         if (p_getChild_1_.equals(modelrenderer.id))
/*     */         {
/* 496 */           return modelrenderer;
/*     */         }
/*     */       } 
/*     */     }
/*     */     
/* 501 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public ModelRenderer getChildDeep(String p_getChildDeep_1_) {
/* 507 */     if (p_getChildDeep_1_ == null)
/*     */     {
/* 509 */       return null;
/*     */     }
/*     */ 
/*     */     
/* 513 */     ModelRenderer modelrenderer = getChild(p_getChildDeep_1_);
/*     */     
/* 515 */     if (modelrenderer != null)
/*     */     {
/* 517 */       return modelrenderer;
/*     */     }
/*     */ 
/*     */     
/* 521 */     if (this.childModels != null)
/*     */     {
/* 523 */       for (int i = 0; i < this.childModels.size(); i++) {
/*     */         
/* 525 */         ModelRenderer modelrenderer1 = this.childModels.get(i);
/* 526 */         ModelRenderer modelrenderer2 = modelrenderer1.getChildDeep(p_getChildDeep_1_);
/*     */         
/* 528 */         if (modelrenderer2 != null)
/*     */         {
/* 530 */           return modelrenderer2;
/*     */         }
/*     */       } 
/*     */     }
/*     */     
/* 535 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setModelUpdater(ModelUpdater p_setModelUpdater_1_) {
/* 542 */     this.modelUpdater = p_setModelUpdater_1_;
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/* 547 */     StringBuffer stringbuffer = new StringBuffer();
/* 548 */     stringbuffer.append("id: ").append(this.id).append(", boxes: ").append((this.cubeList != null) ? Integer.valueOf(this.cubeList.size()) : null).append(", submodels: ").append((this.childModels != null) ? Integer.valueOf(this.childModels.size()) : null);
/* 549 */     return stringbuffer.toString();
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\minecraft\client\model\ModelRenderer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */