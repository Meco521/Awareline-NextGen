/*     */ package net.minecraft.client.renderer.tileentity;
/*     */ 
/*     */ import com.mojang.authlib.GameProfile;
/*     */ import com.mojang.authlib.minecraft.MinecraftProfileTexture;
/*     */ import java.util.Map;
/*     */ import java.util.UUID;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.model.ModelHumanoidHead;
/*     */ import net.minecraft.client.model.ModelSkeletonHead;
/*     */ import net.minecraft.client.renderer.GlStateManager;
/*     */ import net.minecraft.client.resources.DefaultPlayerSkin;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.tileentity.TileEntity;
/*     */ import net.minecraft.tileentity.TileEntitySkull;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ import net.minecraft.util.ResourceLocation;
/*     */ 
/*     */ 
/*     */ public class TileEntitySkullRenderer
/*     */   extends TileEntitySpecialRenderer<TileEntitySkull>
/*     */ {
/*  23 */   private static final ResourceLocation SKELETON_TEXTURES = new ResourceLocation("textures/entity/skeleton/skeleton.png");
/*  24 */   private static final ResourceLocation WITHER_SKELETON_TEXTURES = new ResourceLocation("textures/entity/skeleton/wither_skeleton.png");
/*  25 */   private static final ResourceLocation ZOMBIE_TEXTURES = new ResourceLocation("textures/entity/zombie/zombie.png");
/*  26 */   private static final ResourceLocation CREEPER_TEXTURES = new ResourceLocation("textures/entity/creeper/creeper.png");
/*     */   public static TileEntitySkullRenderer instance;
/*  28 */   private final ModelSkeletonHead skeletonHead = new ModelSkeletonHead(0, 0, 64, 32);
/*  29 */   private final ModelSkeletonHead humanoidHead = (ModelSkeletonHead)new ModelHumanoidHead();
/*     */ 
/*     */   
/*     */   public void renderTileEntityAt(TileEntitySkull te, double x, double y, double z, float partialTicks, int destroyStage) {
/*  33 */     EnumFacing enumfacing = EnumFacing.getFront(te.getBlockMetadata() & 0x7);
/*  34 */     renderSkull((float)x, (float)y, (float)z, enumfacing, (te.getSkullRotation() * 360) / 16.0F, te.getSkullType(), te.getPlayerProfile(), destroyStage);
/*     */   }
/*     */ 
/*     */   
/*     */   public void setRendererDispatcher(TileEntityRendererDispatcher rendererDispatcherIn) {
/*  39 */     super.setRendererDispatcher(rendererDispatcherIn);
/*  40 */     instance = this;
/*     */   }
/*     */ 
/*     */   
/*     */   public void renderSkull(float p_180543_1_, float p_180543_2_, float p_180543_3_, EnumFacing p_180543_4_, float p_180543_5_, int p_180543_6_, GameProfile p_180543_7_, int p_180543_8_) {
/*  45 */     ModelSkeletonHead modelSkeletonHead = this.skeletonHead;
/*     */     
/*  47 */     if (p_180543_8_ >= 0) {
/*     */       
/*  49 */       bindTexture(DESTROY_STAGES[p_180543_8_]);
/*  50 */       GlStateManager.matrixMode(5890);
/*  51 */       GlStateManager.pushMatrix();
/*  52 */       GlStateManager.scale(4.0F, 2.0F, 1.0F);
/*  53 */       GlStateManager.translate(0.0625F, 0.0625F, 0.0625F);
/*  54 */       GlStateManager.matrixMode(5888);
/*     */     } else {
/*     */       ResourceLocation resourcelocation;
/*     */       
/*  58 */       switch (p_180543_6_) {
/*     */ 
/*     */         
/*     */         default:
/*  62 */           bindTexture(SKELETON_TEXTURES);
/*     */           break;
/*     */         
/*     */         case 1:
/*  66 */           bindTexture(WITHER_SKELETON_TEXTURES);
/*     */           break;
/*     */         
/*     */         case 2:
/*  70 */           bindTexture(ZOMBIE_TEXTURES);
/*  71 */           modelSkeletonHead = this.humanoidHead;
/*     */           break;
/*     */         
/*     */         case 3:
/*  75 */           modelSkeletonHead = this.humanoidHead;
/*  76 */           resourcelocation = DefaultPlayerSkin.getDefaultSkinLegacy();
/*     */           
/*  78 */           if (p_180543_7_ != null) {
/*     */             
/*  80 */             Minecraft minecraft = Minecraft.getMinecraft();
/*  81 */             Map<MinecraftProfileTexture.Type, MinecraftProfileTexture> map = minecraft.getSkinManager().loadSkinFromCache(p_180543_7_);
/*     */             
/*  83 */             if (map.containsKey(MinecraftProfileTexture.Type.SKIN)) {
/*     */               
/*  85 */               resourcelocation = minecraft.getSkinManager().loadSkin(map.get(MinecraftProfileTexture.Type.SKIN), MinecraftProfileTexture.Type.SKIN);
/*     */             }
/*     */             else {
/*     */               
/*  89 */               UUID uuid = EntityPlayer.getUUID(p_180543_7_);
/*  90 */               resourcelocation = DefaultPlayerSkin.getDefaultSkin(uuid);
/*     */             } 
/*     */           } 
/*     */           
/*  94 */           bindTexture(resourcelocation);
/*     */           break;
/*     */         
/*     */         case 4:
/*  98 */           bindTexture(CREEPER_TEXTURES);
/*     */           break;
/*     */       } 
/*     */     } 
/* 102 */     GlStateManager.pushMatrix();
/* 103 */     GlStateManager.disableCull();
/*     */     
/* 105 */     if (p_180543_4_ != EnumFacing.UP) {
/*     */       
/* 107 */       switch (p_180543_4_) {
/*     */         
/*     */         case NORTH:
/* 110 */           GlStateManager.translate(p_180543_1_ + 0.5F, p_180543_2_ + 0.25F, p_180543_3_ + 0.74F);
/*     */           break;
/*     */         
/*     */         case SOUTH:
/* 114 */           GlStateManager.translate(p_180543_1_ + 0.5F, p_180543_2_ + 0.25F, p_180543_3_ + 0.26F);
/* 115 */           p_180543_5_ = 180.0F;
/*     */           break;
/*     */         
/*     */         case WEST:
/* 119 */           GlStateManager.translate(p_180543_1_ + 0.74F, p_180543_2_ + 0.25F, p_180543_3_ + 0.5F);
/* 120 */           p_180543_5_ = 270.0F;
/*     */           break;
/*     */ 
/*     */         
/*     */         default:
/* 125 */           GlStateManager.translate(p_180543_1_ + 0.26F, p_180543_2_ + 0.25F, p_180543_3_ + 0.5F);
/* 126 */           p_180543_5_ = 90.0F;
/*     */           break;
/*     */       } 
/*     */     
/*     */     } else {
/* 131 */       GlStateManager.translate(p_180543_1_ + 0.5F, p_180543_2_, p_180543_3_ + 0.5F);
/*     */     } 
/*     */     
/* 134 */     float f = 0.0625F;
/* 135 */     GlStateManager.enableRescaleNormal();
/* 136 */     GlStateManager.scale(-1.0F, -1.0F, 1.0F);
/* 137 */     GlStateManager.enableAlpha();
/* 138 */     modelSkeletonHead.render((Entity)null, 0.0F, 0.0F, 0.0F, p_180543_5_, 0.0F, f);
/* 139 */     GlStateManager.popMatrix();
/*     */     
/* 141 */     if (p_180543_8_ >= 0) {
/*     */       
/* 143 */       GlStateManager.matrixMode(5890);
/* 144 */       GlStateManager.popMatrix();
/* 145 */       GlStateManager.matrixMode(5888);
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\minecraft\client\renderer\tileentity\TileEntitySkullRenderer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */