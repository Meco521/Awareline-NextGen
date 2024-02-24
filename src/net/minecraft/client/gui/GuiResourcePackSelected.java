/*    */ package net.minecraft.client.gui;
/*    */ 
/*    */ import java.util.List;
/*    */ import net.minecraft.client.Minecraft;
/*    */ import net.minecraft.client.resources.I18n;
/*    */ import net.minecraft.client.resources.ResourcePackListEntry;
/*    */ 
/*    */ 
/*    */ public class GuiResourcePackSelected
/*    */   extends GuiResourcePackList
/*    */ {
/*    */   public GuiResourcePackSelected(Minecraft mcIn, int p_i45056_2_, int p_i45056_3_, List<ResourcePackListEntry> p_i45056_4_) {
/* 13 */     super(mcIn, p_i45056_2_, p_i45056_3_, p_i45056_4_);
/*    */   }
/*    */ 
/*    */   
/*    */   protected String getListHeader() {
/* 18 */     return I18n.format("resourcePack.selected.title", new Object[0]);
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\minecraft\client\gui\GuiResourcePackSelected.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */