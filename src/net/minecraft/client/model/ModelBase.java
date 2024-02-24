/*    */ package net.minecraft.client.model;
/*    */ 
/*    */ import com.google.common.collect.Lists;
/*    */ import com.google.common.collect.Maps;
/*    */ import java.util.List;
/*    */ import java.util.Map;
/*    */ import java.util.Random;
/*    */ import net.minecraft.entity.Entity;
/*    */ import net.minecraft.entity.EntityLivingBase;
/*    */ 
/*    */ 
/*    */ public abstract class ModelBase
/*    */ {
/*    */   public float swingProgress;
/*    */   public boolean isRiding;
/*    */   public boolean isChild = true;
/* 17 */   public List<ModelRenderer> boxList = Lists.newArrayList();
/* 18 */   private final Map<String, TextureOffset> modelTextureMap = Maps.newHashMap();
/* 19 */   public int textureWidth = 64;
/* 20 */   public int textureHeight = 32;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void render(Entity entityIn, float p_78088_2_, float p_78088_3_, float p_78088_4_, float p_78088_5_, float p_78088_6_, float scale) {}
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void setRotationAngles(float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scaleFactor, Entity entityIn) {}
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void setLivingAnimations(EntityLivingBase entitylivingbaseIn, float p_78086_2_, float p_78086_3_, float partialTickTime) {}
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public ModelRenderer getRandomModelBox(Random rand) {
/* 48 */     return this.boxList.get(rand.nextInt(this.boxList.size()));
/*    */   }
/*    */ 
/*    */   
/*    */   protected void setTextureOffset(String partName, int x, int y) {
/* 53 */     this.modelTextureMap.put(partName, new TextureOffset(x, y));
/*    */   }
/*    */ 
/*    */   
/*    */   public TextureOffset getTextureOffset(String partName) {
/* 58 */     return this.modelTextureMap.get(partName);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static void copyModelAngles(ModelRenderer source, ModelRenderer dest) {
/* 67 */     dest.rotateAngleX = source.rotateAngleX;
/* 68 */     dest.rotateAngleY = source.rotateAngleY;
/* 69 */     dest.rotateAngleZ = source.rotateAngleZ;
/* 70 */     dest.rotationPointX = source.rotationPointX;
/* 71 */     dest.rotationPointY = source.rotationPointY;
/* 72 */     dest.rotationPointZ = source.rotationPointZ;
/*    */   }
/*    */ 
/*    */   
/*    */   public void setModelAttributes(ModelBase model) {
/* 77 */     this.swingProgress = model.swingProgress;
/* 78 */     this.isRiding = model.isRiding;
/* 79 */     this.isChild = model.isChild;
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\minecraft\client\model\ModelBase.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */