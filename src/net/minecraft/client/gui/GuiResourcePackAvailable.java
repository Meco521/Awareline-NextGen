/*    */ package net.minecraft.client.gui;
/*    */ 
/*    */ import java.util.List;
/*    */ import net.minecraft.client.Minecraft;
/*    */ import net.minecraft.client.resources.I18n;
/*    */ import net.minecraft.client.resources.ResourcePackListEntry;
/*    */ 
/*    */ 
/*    */ public class GuiResourcePackAvailable
/*    */   extends GuiResourcePackList
/*    */ {
/*    */   public GuiResourcePackAvailable(Minecraft mcIn, int p_i45054_2_, int p_i45054_3_, List<ResourcePackListEntry> p_i45054_4_) {
/* 13 */     super(mcIn, p_i45054_2_, p_i45054_3_, p_i45054_4_);
/*    */   }
/*    */ 
/*    */   
/*    */   protected String getListHeader() {
/* 18 */     return I18n.format("resourcePack.available.title", new Object[0]);
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\minecraft\client\gui\GuiResourcePackAvailable.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */