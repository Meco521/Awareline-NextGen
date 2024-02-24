/*     */ package net.minecraft.client.renderer.entity;
/*     */ import awareline.main.event.Event;
/*     */ import awareline.main.event.EventManager;
/*     */ import awareline.main.event.events.world.renderEvents.RenderArmEvent;
/*     */ import awareline.main.mod.implement.visual.PlayerSize;
/*     */ import net.minecraft.client.entity.AbstractClientPlayer;
/*     */ import net.minecraft.client.model.ModelBase;
/*     */ import net.minecraft.client.model.ModelPlayer;
/*     */ import net.minecraft.client.renderer.GlStateManager;
/*     */ import net.minecraft.client.renderer.entity.layers.LayerBipedArmor;
/*     */ import net.minecraft.client.renderer.entity.layers.LayerCustomHead;
/*     */ import net.minecraft.client.renderer.entity.layers.LayerDeadmau5Head;
/*     */ import net.minecraft.client.renderer.entity.layers.LayerHeldItem;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.EntityLivingBase;
/*     */ import net.minecraft.entity.player.EnumPlayerModelParts;
/*     */ import net.minecraft.item.EnumAction;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.scoreboard.Score;
/*     */ import net.minecraft.scoreboard.ScoreObjective;
/*     */ import net.minecraft.scoreboard.Scoreboard;
/*     */ import net.minecraft.util.ResourceLocation;
/*     */ 
/*     */ public class RenderPlayer extends RendererLivingEntity<AbstractClientPlayer> {
/*     */   public RenderPlayer(RenderManager renderManager) {
/*  26 */     this(renderManager, false);
/*     */   }
/*     */   private final boolean smallArms;
/*     */   
/*     */   public RenderPlayer(RenderManager renderManager, boolean useSmallArms) {
/*  31 */     super(renderManager, (ModelBase)new ModelPlayer(0.0F, useSmallArms), 0.5F);
/*  32 */     this.smallArms = useSmallArms;
/*  33 */     addLayer(new LayerBipedArmor(this));
/*  34 */     addLayer(new LayerHeldItem(this));
/*  35 */     addLayer(new LayerArrow(this));
/*  36 */     addLayer(new LayerDeadmau5Head(this));
/*  37 */     addLayer(new LayerCape(this));
/*  38 */     addLayer(new LayerCustomHead((getMainModel()).bipedHead));
/*     */   }
/*     */ 
/*     */   
/*     */   public ModelPlayer getMainModel() {
/*  43 */     return (ModelPlayer)super.getMainModel();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void doRender(AbstractClientPlayer entity, double x, double y, double z, float entityYaw, float partialTicks) {
/*  52 */     if (!entity.isUser() || this.renderManager.livingPlayer == entity) {
/*     */       
/*  54 */       double d0 = y;
/*     */       
/*  56 */       if (entity.isSneaking() && !(entity instanceof net.minecraft.client.entity.EntityPlayerSP))
/*     */       {
/*  58 */         d0 = y - 0.125D;
/*     */       }
/*     */       
/*  61 */       setModelVisibilities(entity);
/*  62 */       super.doRender(entity, x, d0, z, entityYaw, partialTicks);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private void setModelVisibilities(AbstractClientPlayer clientPlayer) {
/*  69 */     ModelPlayer modelplayer = getMainModel();
/*     */     
/*  71 */     if (clientPlayer.isSpectator()) {
/*     */       
/*  73 */       modelplayer.setInvisible(false);
/*  74 */       modelplayer.bipedHead.showModel = true;
/*  75 */       modelplayer.bipedHeadwear.showModel = true;
/*     */     }
/*     */     else {
/*     */       
/*  79 */       ItemStack itemstack = clientPlayer.inventory.getCurrentItem();
/*  80 */       modelplayer.setInvisible(true);
/*  81 */       modelplayer.bipedHeadwear.showModel = clientPlayer.isWearing(EnumPlayerModelParts.HAT);
/*  82 */       modelplayer.bipedBodyWear.showModel = clientPlayer.isWearing(EnumPlayerModelParts.JACKET);
/*  83 */       modelplayer.bipedLeftLegwear.showModel = clientPlayer.isWearing(EnumPlayerModelParts.LEFT_PANTS_LEG);
/*  84 */       modelplayer.bipedRightLegwear.showModel = clientPlayer.isWearing(EnumPlayerModelParts.RIGHT_PANTS_LEG);
/*  85 */       modelplayer.bipedLeftArmwear.showModel = clientPlayer.isWearing(EnumPlayerModelParts.LEFT_SLEEVE);
/*  86 */       modelplayer.bipedRightArmwear.showModel = clientPlayer.isWearing(EnumPlayerModelParts.RIGHT_SLEEVE);
/*  87 */       modelplayer.heldItemLeft = 0;
/*  88 */       modelplayer.aimedBow = false;
/*  89 */       modelplayer.isSneak = clientPlayer.isSneaking();
/*     */       
/*  91 */       if (itemstack == null) {
/*     */         
/*  93 */         modelplayer.heldItemRight = 0;
/*     */       }
/*     */       else {
/*     */         
/*  97 */         modelplayer.heldItemRight = 1;
/*     */         
/*  99 */         if (clientPlayer.getItemInUseCount() > 0) {
/*     */           
/* 101 */           EnumAction enumaction = itemstack.getItemUseAction();
/*     */           
/* 103 */           if (enumaction == EnumAction.BLOCK) {
/*     */             
/* 105 */             modelplayer.heldItemRight = 3;
/*     */           }
/* 107 */           else if (enumaction == EnumAction.BOW) {
/*     */             
/* 109 */             modelplayer.aimedBow = true;
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected ResourceLocation getEntityTexture(AbstractClientPlayer entity) {
/* 121 */     return entity.getLocationSkin();
/*     */   }
/*     */ 
/*     */   
/*     */   public void transformHeldFull3DItemLayer() {
/* 126 */     GlStateManager.translate(0.0F, 0.1875F, 0.0F);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void preRenderCallback(AbstractClientPlayer entitylivingbaseIn, float partialTickTime) {
/* 135 */     float f = 0.9375F;
/* 136 */     GlStateManager.scale(f, f, f);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void renderOffsetLivingLabel(AbstractClientPlayer entityIn, double x, double y, double z, String str, float p_177069_9_, double p_177069_10_) {
/* 141 */     if (p_177069_10_ < 100.0D) {
/*     */       
/* 143 */       Scoreboard scoreboard = entityIn.getWorldScoreboard();
/* 144 */       ScoreObjective scoreobjective = scoreboard.getObjectiveInDisplaySlot(2);
/*     */       
/* 146 */       if (scoreobjective != null) {
/*     */         
/* 148 */         Score score = scoreboard.getValueFromObjective(entityIn.getName(), scoreobjective);
/* 149 */         renderLivingLabel(entityIn, score.getScorePoints() + " " + scoreobjective.getDisplayName(), x, y, z, 64);
/* 150 */         y += ((getFontRendererFromRenderManager()).FONT_HEIGHT * 1.15F * p_177069_9_);
/*     */       } 
/*     */     } 
/*     */     
/* 154 */     super.renderOffsetLivingLabel(entityIn, x, y, z, str, p_177069_9_, p_177069_10_);
/*     */   }
/*     */   
/*     */   public void renderRightArm(AbstractClientPlayer clientPlayer) {
/* 158 */     float f = 1.0F;
/* 159 */     GlStateManager.color(f, f, f);
/* 160 */     ModelPlayer modelplayer = getMainModel();
/* 161 */     setModelVisibilities(clientPlayer);
/* 162 */     modelplayer.swingProgress = 0.0F;
/* 163 */     modelplayer.isSneak = false;
/* 164 */     modelplayer.setRotationAngles(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0625F, (Entity)clientPlayer);
/* 165 */     RenderArmEvent renderModelEvent = new RenderArmEvent((Entity)clientPlayer, true);
/* 166 */     RenderArmEvent renderModelEventPost = new RenderArmEvent((Entity)clientPlayer, false);
/* 167 */     EventManager.call((Event)renderModelEvent);
/* 168 */     if (!renderModelEvent.isCancelled()) {
/* 169 */       modelplayer.renderRightArm();
/*     */     }
/* 171 */     EventManager.call((Event)renderModelEventPost);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void renderLeftArm(AbstractClientPlayer clientPlayer) {
/* 177 */     float f = 1.0F;
/* 178 */     GlStateManager.color(f, f, f);
/* 179 */     ModelPlayer modelplayer = getMainModel();
/* 180 */     setModelVisibilities(clientPlayer);
/* 181 */     modelplayer.isSneak = false;
/* 182 */     modelplayer.swingProgress = 0.0F;
/* 183 */     modelplayer.setRotationAngles(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0625F, (Entity)clientPlayer);
/* 184 */     modelplayer.renderLeftArm();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void renderLivingAt(AbstractClientPlayer entityLivingBaseIn, double x, double y, double z) {
/* 192 */     if (entityLivingBaseIn.isEntityAlive() && entityLivingBaseIn.isPlayerSleeping()) {
/*     */       
/* 194 */       super.renderLivingAt(entityLivingBaseIn, x + entityLivingBaseIn.renderOffsetX, y + entityLivingBaseIn.renderOffsetY, z + entityLivingBaseIn.renderOffsetZ);
/*     */     }
/*     */     else {
/*     */       
/* 198 */       PlayerSize.setSize((Entity)entityLivingBaseIn);
/* 199 */       super.renderLivingAt(entityLivingBaseIn, x, y, z);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   protected void rotateCorpse(AbstractClientPlayer bat, float p_77043_2_, float p_77043_3_, float partialTicks) {
/* 205 */     if (bat.isEntityAlive() && bat.isPlayerSleeping()) {
/*     */       
/* 207 */       GlStateManager.rotate(bat.getBedOrientationInDegrees(), 0.0F, 1.0F, 0.0F);
/* 208 */       GlStateManager.rotate(getDeathMaxRotation(bat), 0.0F, 0.0F, 1.0F);
/* 209 */       GlStateManager.rotate(270.0F, 0.0F, 1.0F, 0.0F);
/*     */     }
/*     */     else {
/*     */       
/* 213 */       super.rotateCorpse(bat, p_77043_2_, p_77043_3_, partialTicks);
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\minecraft\client\renderer\entity\RenderPlayer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */