/*    */ package net.minecraft.client.renderer.tileentity;
/*    */ 
/*    */ import com.mojang.authlib.GameProfile;
/*    */ import java.util.UUID;
/*    */ import net.minecraft.block.Block;
/*    */ import net.minecraft.client.renderer.GlStateManager;
/*    */ import net.minecraft.init.Blocks;
/*    */ import net.minecraft.init.Items;
/*    */ import net.minecraft.item.ItemStack;
/*    */ import net.minecraft.nbt.NBTBase;
/*    */ import net.minecraft.nbt.NBTTagCompound;
/*    */ import net.minecraft.nbt.NBTUtil;
/*    */ import net.minecraft.tileentity.TileEntity;
/*    */ import net.minecraft.tileentity.TileEntityBanner;
/*    */ import net.minecraft.tileentity.TileEntityChest;
/*    */ import net.minecraft.tileentity.TileEntityEnderChest;
/*    */ import net.minecraft.tileentity.TileEntitySkull;
/*    */ import net.minecraft.util.EnumFacing;
/*    */ 
/*    */ public class TileEntityItemStackRenderer {
/* 21 */   public static TileEntityItemStackRenderer instance = new TileEntityItemStackRenderer();
/* 22 */   private final TileEntityChest field_147717_b = new TileEntityChest(0);
/* 23 */   private final TileEntityChest field_147718_c = new TileEntityChest(1);
/* 24 */   private final TileEntityEnderChest enderChest = new TileEntityEnderChest();
/* 25 */   private final TileEntityBanner banner = new TileEntityBanner();
/* 26 */   private final TileEntitySkull skull = new TileEntitySkull();
/*    */ 
/*    */   
/*    */   public void renderByItem(ItemStack itemStackIn) {
/* 30 */     if (itemStackIn.getItem() == Items.banner) {
/*    */       
/* 32 */       this.banner.setItemValues(itemStackIn);
/* 33 */       TileEntityRendererDispatcher.instance.renderTileEntityAt((TileEntity)this.banner, 0.0D, 0.0D, 0.0D, 0.0F);
/*    */     }
/* 35 */     else if (itemStackIn.getItem() == Items.skull) {
/*    */       
/* 37 */       GameProfile gameprofile = null;
/*    */       
/* 39 */       if (itemStackIn.hasTagCompound()) {
/*    */         
/* 41 */         NBTTagCompound nbttagcompound = itemStackIn.getTagCompound();
/*    */         
/* 43 */         if (nbttagcompound.hasKey("SkullOwner", 10)) {
/*    */           
/* 45 */           gameprofile = NBTUtil.readGameProfileFromNBT(nbttagcompound.getCompoundTag("SkullOwner"));
/*    */         }
/* 47 */         else if (nbttagcompound.hasKey("SkullOwner", 8) && !nbttagcompound.getString("SkullOwner").isEmpty()) {
/*    */           
/* 49 */           gameprofile = new GameProfile((UUID)null, nbttagcompound.getString("SkullOwner"));
/* 50 */           gameprofile = TileEntitySkull.updateGameprofile(gameprofile);
/* 51 */           nbttagcompound.removeTag("SkullOwner");
/* 52 */           nbttagcompound.setTag("SkullOwner", (NBTBase)NBTUtil.writeGameProfile(new NBTTagCompound(), gameprofile));
/*    */         } 
/*    */       } 
/*    */       
/* 56 */       if (TileEntitySkullRenderer.instance != null)
/*    */       {
/* 58 */         GlStateManager.pushMatrix();
/* 59 */         GlStateManager.translate(-0.5F, 0.0F, -0.5F);
/* 60 */         GlStateManager.scale(2.0F, 2.0F, 2.0F);
/* 61 */         GlStateManager.disableCull();
/* 62 */         TileEntitySkullRenderer.instance.renderSkull(0.0F, 0.0F, 0.0F, EnumFacing.UP, 0.0F, itemStackIn.getMetadata(), gameprofile, -1);
/* 63 */         GlStateManager.enableCull();
/* 64 */         GlStateManager.popMatrix();
/*    */       }
/*    */     
/*    */     } else {
/*    */       
/* 69 */       Block block = Block.getBlockFromItem(itemStackIn.getItem());
/*    */       
/* 71 */       if (block == Blocks.ender_chest) {
/*    */         
/* 73 */         TileEntityRendererDispatcher.instance.renderTileEntityAt((TileEntity)this.enderChest, 0.0D, 0.0D, 0.0D, 0.0F);
/*    */       }
/* 75 */       else if (block == Blocks.trapped_chest) {
/*    */         
/* 77 */         TileEntityRendererDispatcher.instance.renderTileEntityAt((TileEntity)this.field_147718_c, 0.0D, 0.0D, 0.0D, 0.0F);
/*    */       }
/*    */       else {
/*    */         
/* 81 */         TileEntityRendererDispatcher.instance.renderTileEntityAt((TileEntity)this.field_147717_b, 0.0D, 0.0D, 0.0D, 0.0F);
/*    */       } 
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\minecraft\client\renderer\tileentity\TileEntityItemStackRenderer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */