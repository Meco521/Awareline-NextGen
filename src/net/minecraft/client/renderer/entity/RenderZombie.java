/*    */ package net.minecraft.client.renderer.entity;
/*    */ import net.minecraft.client.model.ModelBase;
/*    */ import net.minecraft.client.model.ModelBiped;
/*    */ import net.minecraft.client.model.ModelZombie;
/*    */ import net.minecraft.client.model.ModelZombieVillager;
/*    */ import net.minecraft.client.renderer.entity.layers.LayerBipedArmor;
/*    */ import net.minecraft.client.renderer.entity.layers.LayerRenderer;
/*    */ import net.minecraft.entity.Entity;
/*    */ import net.minecraft.entity.EntityLiving;
/*    */ import net.minecraft.entity.EntityLivingBase;
/*    */ import net.minecraft.entity.monster.EntityZombie;
/*    */ import net.minecraft.util.ResourceLocation;
/*    */ 
/*    */ public class RenderZombie extends RenderBiped<EntityZombie> {
/* 15 */   private static final ResourceLocation zombieTextures = new ResourceLocation("textures/entity/zombie/zombie.png");
/* 16 */   private static final ResourceLocation zombieVillagerTextures = new ResourceLocation("textures/entity/zombie/zombie_villager.png");
/*    */   
/*    */   private final ModelBiped field_82434_o;
/*    */   private final ModelZombieVillager zombieVillagerModel;
/*    */   private final List<LayerRenderer<EntityZombie>> field_177121_n;
/*    */   private final List<LayerRenderer<EntityZombie>> field_177122_o;
/*    */   
/*    */   public RenderZombie(RenderManager renderManagerIn) {
/* 24 */     super(renderManagerIn, (ModelBiped)new ModelZombie(), 0.5F, 1.0F);
/* 25 */     LayerRenderer layerrenderer = this.layerRenderers.get(0);
/* 26 */     this.field_82434_o = this.modelBipedMain;
/* 27 */     this.zombieVillagerModel = new ModelZombieVillager();
/* 28 */     addLayer(new LayerHeldItem(this));
/* 29 */     LayerBipedArmor layerbipedarmor = new LayerBipedArmor(this)
/*    */       {
/*    */         protected void initArmor()
/*    */         {
/* 33 */           this.modelLeggings = (ModelBase)new ModelZombie(0.5F, true);
/* 34 */           this.modelArmor = (ModelBase)new ModelZombie(1.0F, true);
/*    */         }
/*    */       };
/* 37 */     addLayer(layerbipedarmor);
/* 38 */     this.field_177122_o = Lists.newArrayList(this.layerRenderers);
/*    */     
/* 40 */     if (layerrenderer instanceof LayerCustomHead) {
/*    */       
/* 42 */       removeLayer(layerrenderer);
/* 43 */       addLayer(new LayerCustomHead(this.zombieVillagerModel.bipedHead));
/*    */     } 
/*    */     
/* 46 */     removeLayer(layerbipedarmor);
/* 47 */     addLayer(new LayerVillagerArmor(this));
/* 48 */     this.field_177121_n = Lists.newArrayList(this.layerRenderers);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void doRender(EntityZombie entity, double x, double y, double z, float entityYaw, float partialTicks) {
/* 56 */     func_82427_a(entity);
/* 57 */     super.doRender(entity, x, y, z, entityYaw, partialTicks);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected ResourceLocation getEntityTexture(EntityZombie entity) {
/* 65 */     return entity.isVillager() ? zombieVillagerTextures : zombieTextures;
/*    */   }
/*    */ 
/*    */   
/*    */   private void func_82427_a(EntityZombie zombie) {
/* 70 */     if (zombie.isVillager()) {
/*    */       
/* 72 */       this.mainModel = (ModelBase)this.zombieVillagerModel;
/* 73 */       this.layerRenderers = this.field_177121_n;
/*    */     }
/*    */     else {
/*    */       
/* 77 */       this.mainModel = (ModelBase)this.field_82434_o;
/* 78 */       this.layerRenderers = this.field_177122_o;
/*    */     } 
/*    */     
/* 81 */     this.modelBipedMain = (ModelBiped)this.mainModel;
/*    */   }
/*    */ 
/*    */   
/*    */   protected void rotateCorpse(EntityZombie bat, float p_77043_2_, float p_77043_3_, float partialTicks) {
/* 86 */     if (bat.isConverting())
/*    */     {
/* 88 */       p_77043_3_ += (float)(Math.cos(bat.ticksExisted * 3.25D) * Math.PI * 0.25D);
/*    */     }
/*    */     
/* 91 */     super.rotateCorpse(bat, p_77043_2_, p_77043_3_, partialTicks);
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\minecraft\client\renderer\entity\RenderZombie.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */