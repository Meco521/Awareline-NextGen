/*    */ package net.minecraft.client.particle;
/*    */ 
/*    */ import net.minecraft.client.Minecraft;
/*    */ import net.minecraft.client.renderer.GlStateManager;
/*    */ import net.minecraft.client.renderer.OpenGlHelper;
/*    */ import net.minecraft.client.renderer.WorldRenderer;
/*    */ import net.minecraft.client.renderer.entity.RenderManager;
/*    */ import net.minecraft.entity.Entity;
/*    */ import net.minecraft.src.Config;
/*    */ import net.minecraft.world.World;
/*    */ import net.optifine.shaders.Program;
/*    */ import net.optifine.shaders.Shaders;
/*    */ 
/*    */ public class EntityPickupFX
/*    */   extends EntityFX {
/*    */   private final Entity field_174840_a;
/*    */   private final Entity field_174843_ax;
/*    */   private int age;
/*    */   private final int maxAge;
/*    */   private final float field_174841_aA;
/* 21 */   private final RenderManager field_174842_aB = Minecraft.getMinecraft().getRenderManager();
/*    */ 
/*    */   
/*    */   public EntityPickupFX(World worldIn, Entity p_i1233_2_, Entity p_i1233_3_, float p_i1233_4_) {
/* 25 */     super(worldIn, p_i1233_2_.posX, p_i1233_2_.posY, p_i1233_2_.posZ, p_i1233_2_.motionX, p_i1233_2_.motionY, p_i1233_2_.motionZ);
/* 26 */     this.field_174840_a = p_i1233_2_;
/* 27 */     this.field_174843_ax = p_i1233_3_;
/* 28 */     this.maxAge = 3;
/* 29 */     this.field_174841_aA = p_i1233_4_;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void renderParticle(WorldRenderer worldRendererIn, Entity entityIn, float partialTicks, float rotationX, float rotationZ, float rotationYZ, float rotationXY, float rotationXZ) {
/* 37 */     Program program = null;
/*    */     
/* 39 */     if (Config.isShaders()) {
/*    */       
/* 41 */       program = Shaders.activeProgram;
/* 42 */       Shaders.nextEntity(this.field_174840_a);
/*    */     } 
/*    */     
/* 45 */     float f = (this.age + partialTicks) / this.maxAge;
/* 46 */     f *= f;
/* 47 */     double d0 = this.field_174840_a.posX;
/* 48 */     double d1 = this.field_174840_a.posY;
/* 49 */     double d2 = this.field_174840_a.posZ;
/* 50 */     double d3 = this.field_174843_ax.lastTickPosX + (this.field_174843_ax.posX - this.field_174843_ax.lastTickPosX) * partialTicks;
/* 51 */     double d4 = this.field_174843_ax.lastTickPosY + (this.field_174843_ax.posY - this.field_174843_ax.lastTickPosY) * partialTicks + this.field_174841_aA;
/* 52 */     double d5 = this.field_174843_ax.lastTickPosZ + (this.field_174843_ax.posZ - this.field_174843_ax.lastTickPosZ) * partialTicks;
/* 53 */     double d6 = d0 + (d3 - d0) * f;
/* 54 */     double d7 = d1 + (d4 - d1) * f;
/* 55 */     double d8 = d2 + (d5 - d2) * f;
/* 56 */     int i = getBrightnessForRender(partialTicks);
/* 57 */     int j = i % 65536;
/* 58 */     int k = i / 65536;
/* 59 */     OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, j, k);
/* 60 */     GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
/* 61 */     d6 -= interpPosX;
/* 62 */     d7 -= interpPosY;
/* 63 */     d8 -= interpPosZ;
/* 64 */     this.field_174842_aB.renderEntityWithPosYaw(this.field_174840_a, (float)d6, (float)d7, (float)d8, this.field_174840_a.rotationYaw, partialTicks);
/*    */     
/* 66 */     if (Config.isShaders()) {
/*    */       
/* 68 */       Shaders.setEntityId((Entity)null);
/* 69 */       Shaders.useProgram(program);
/*    */     } 
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void onUpdate() {
/* 78 */     this.age++;
/*    */     
/* 80 */     if (this.age == this.maxAge)
/*    */     {
/* 82 */       setDead();
/*    */     }
/*    */   }
/*    */ 
/*    */   
/*    */   public int getFXLayer() {
/* 88 */     return 3;
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\minecraft\client\particle\EntityPickupFX.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */