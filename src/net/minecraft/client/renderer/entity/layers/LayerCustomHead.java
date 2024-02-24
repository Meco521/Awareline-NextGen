/*     */ package net.minecraft.client.renderer.entity.layers;
/*     */ 
/*     */ import com.mojang.authlib.GameProfile;
/*     */ import java.util.UUID;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.model.ModelRenderer;
/*     */ import net.minecraft.client.renderer.GlStateManager;
/*     */ import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
/*     */ import net.minecraft.client.renderer.tileentity.TileEntitySkullRenderer;
/*     */ import net.minecraft.entity.EntityLivingBase;
/*     */ import net.minecraft.entity.monster.EntityZombie;
/*     */ import net.minecraft.init.Items;
/*     */ import net.minecraft.item.Item;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.nbt.NBTBase;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.nbt.NBTUtil;
/*     */ import net.minecraft.tileentity.TileEntitySkull;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ import net.minecraft.util.StringUtils;
/*     */ 
/*     */ 
/*     */ 
/*     */ public class LayerCustomHead
/*     */   implements LayerRenderer<EntityLivingBase>
/*     */ {
/*     */   private final ModelRenderer field_177209_a;
/*     */   
/*     */   public LayerCustomHead(ModelRenderer p_i46120_1_) {
/*  30 */     this.field_177209_a = p_i46120_1_;
/*     */   }
/*     */ 
/*     */   
/*     */   public void doRenderLayer(EntityLivingBase entitylivingbaseIn, float p_177141_2_, float p_177141_3_, float partialTicks, float p_177141_5_, float p_177141_6_, float p_177141_7_, float scale) {
/*  35 */     ItemStack itemstack = entitylivingbaseIn.getCurrentArmor(3);
/*     */     
/*  37 */     if (itemstack != null && itemstack.getItem() != null) {
/*     */       
/*  39 */       Item item = itemstack.getItem();
/*  40 */       Minecraft minecraft = Minecraft.getMinecraft();
/*  41 */       GlStateManager.pushMatrix();
/*     */       
/*  43 */       if (entitylivingbaseIn.isSneaking())
/*     */       {
/*  45 */         GlStateManager.translate(0.0F, 0.2F, 0.0F);
/*     */       }
/*     */       
/*  48 */       boolean flag = (entitylivingbaseIn instanceof net.minecraft.entity.passive.EntityVillager || (entitylivingbaseIn instanceof EntityZombie && ((EntityZombie)entitylivingbaseIn).isVillager()));
/*     */       
/*  50 */       if (!flag && entitylivingbaseIn.isChild()) {
/*     */         
/*  52 */         float f = 2.0F;
/*  53 */         float f1 = 1.4F;
/*  54 */         GlStateManager.scale(f1 / f, f1 / f, f1 / f);
/*  55 */         GlStateManager.translate(0.0F, 16.0F * scale, 0.0F);
/*     */       } 
/*     */       
/*  58 */       this.field_177209_a.postRender(0.0625F);
/*  59 */       GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
/*     */       
/*  61 */       if (item instanceof net.minecraft.item.ItemBlock) {
/*     */         
/*  63 */         float f2 = 0.625F;
/*  64 */         GlStateManager.translate(0.0F, -0.25F, 0.0F);
/*  65 */         GlStateManager.rotate(180.0F, 0.0F, 1.0F, 0.0F);
/*  66 */         GlStateManager.scale(f2, -f2, -f2);
/*     */         
/*  68 */         if (flag)
/*     */         {
/*  70 */           GlStateManager.translate(0.0F, 0.1875F, 0.0F);
/*     */         }
/*     */         
/*  73 */         minecraft.getItemRenderer().renderItem(entitylivingbaseIn, itemstack, ItemCameraTransforms.TransformType.HEAD);
/*     */       }
/*  75 */       else if (item == Items.skull) {
/*     */         
/*  77 */         float f3 = 1.1875F;
/*  78 */         GlStateManager.scale(f3, -f3, -f3);
/*     */         
/*  80 */         if (flag)
/*     */         {
/*  82 */           GlStateManager.translate(0.0F, 0.0625F, 0.0F);
/*     */         }
/*     */         
/*  85 */         GameProfile gameprofile = null;
/*     */         
/*  87 */         if (itemstack.hasTagCompound()) {
/*     */           
/*  89 */           NBTTagCompound nbttagcompound = itemstack.getTagCompound();
/*     */           
/*  91 */           if (nbttagcompound.hasKey("SkullOwner", 10)) {
/*     */             
/*  93 */             gameprofile = NBTUtil.readGameProfileFromNBT(nbttagcompound.getCompoundTag("SkullOwner"));
/*     */           }
/*  95 */           else if (nbttagcompound.hasKey("SkullOwner", 8)) {
/*     */             
/*  97 */             String s = nbttagcompound.getString("SkullOwner");
/*     */             
/*  99 */             if (!StringUtils.isNullOrEmpty(s)) {
/*     */               
/* 101 */               gameprofile = TileEntitySkull.updateGameprofile(new GameProfile((UUID)null, s));
/* 102 */               nbttagcompound.setTag("SkullOwner", (NBTBase)NBTUtil.writeGameProfile(new NBTTagCompound(), gameprofile));
/*     */             } 
/*     */           } 
/*     */         } 
/*     */         
/* 107 */         TileEntitySkullRenderer.instance.renderSkull(-0.5F, 0.0F, -0.5F, EnumFacing.UP, 180.0F, itemstack.getMetadata(), gameprofile, -1);
/*     */       } 
/*     */       
/* 110 */       GlStateManager.popMatrix();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean shouldCombineTextures() {
/* 116 */     return true;
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\minecraft\client\renderer\entity\layers\LayerCustomHead.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */