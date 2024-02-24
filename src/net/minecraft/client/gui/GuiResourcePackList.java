/*    */ package net.minecraft.client.gui;
/*    */ 
/*    */ import java.util.List;
/*    */ import net.minecraft.client.Minecraft;
/*    */ import net.minecraft.client.renderer.Tessellator;
/*    */ import net.minecraft.client.resources.ResourcePackListEntry;
/*    */ import net.minecraft.util.EnumChatFormatting;
/*    */ 
/*    */ 
/*    */ public abstract class GuiResourcePackList
/*    */   extends GuiListExtended
/*    */ {
/*    */   protected final Minecraft mc;
/*    */   protected final List<ResourcePackListEntry> field_148204_l;
/*    */   
/*    */   public GuiResourcePackList(Minecraft mcIn, int p_i45055_2_, int p_i45055_3_, List<ResourcePackListEntry> p_i45055_4_) {
/* 17 */     super(mcIn, p_i45055_2_, p_i45055_3_, 32, p_i45055_3_ - 55 + 4, 36);
/* 18 */     this.mc = mcIn;
/* 19 */     this.field_148204_l = p_i45055_4_;
/* 20 */     this.field_148163_i = false;
/* 21 */     setHasListHeader(true, (int)(mcIn.fontRendererObj.FONT_HEIGHT * 1.5F));
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected void drawListHeader(int p_148129_1_, int p_148129_2_, Tessellator p_148129_3_) {
/* 29 */     String s = EnumChatFormatting.UNDERLINE + "" + EnumChatFormatting.BOLD + getListHeader();
/* 30 */     this.mc.fontRendererObj.drawString(s, p_148129_1_ + this.width / 2 - this.mc.fontRendererObj.getStringWidth(s) / 2, Math.min(this.top + 3, p_148129_2_), 16777215);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public List<ResourcePackListEntry> getList() {
/* 37 */     return this.field_148204_l;
/*    */   }
/*    */ 
/*    */   
/*    */   protected int getSize() {
/* 42 */     return this.field_148204_l.size();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public ResourcePackListEntry getListEntry(int index) {
/* 50 */     return this.field_148204_l.get(index);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public int getListWidth() {
/* 58 */     return this.width;
/*    */   }
/*    */ 
/*    */   
/*    */   protected int getScrollBarX() {
/* 63 */     return this.right - 6;
/*    */   }
/*    */   
/*    */   protected abstract String getListHeader();
/*    */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\minecraft\client\gui\GuiResourcePackList.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */