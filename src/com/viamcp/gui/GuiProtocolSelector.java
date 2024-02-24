/*    */ package com.viamcp.gui;
/*    */ 
/*    */ import awareline.main.Client;
/*    */ import com.github.creeper123123321.viafabric.ViaFabric;
/*    */ import com.github.creeper123123321.viafabric.util.ProtocolUtils;
/*    */ import com.viamcp.utils.ProtocolSorter;
/*    */ import java.io.IOException;
/*    */ import net.minecraft.client.Minecraft;
/*    */ import net.minecraft.client.gui.GuiButton;
/*    */ import net.minecraft.client.gui.GuiScreen;
/*    */ import net.minecraft.client.gui.GuiSlot;
/*    */ import net.minecraft.util.EnumChatFormatting;
/*    */ import org.lwjgl.opengl.GL11;
/*    */ import us.myles.ViaVersion.api.protocol.ProtocolVersion;
/*    */ 
/*    */ 
/*    */ public class GuiProtocolSelector
/*    */   extends GuiScreen
/*    */ {
/*    */   public SlotList list;
/*    */   private final GuiScreen parent;
/*    */   public static boolean down;
/*    */   
/*    */   public GuiProtocolSelector(GuiScreen parent) {
/* 25 */     this.parent = parent;
/*    */   }
/*    */ 
/*    */   
/*    */   public void initGui() {
/* 30 */     super.initGui();
/* 31 */     this.buttonList.add(new GuiButton(1, this.width / 2 - 100, this.height - 27, 200, 20, "Back"));
/* 32 */     this.list = new SlotList(this.mc, this.width, this.height, 32, this.height - 32, 10);
/*    */   }
/*    */ 
/*    */   
/*    */   protected void actionPerformed(GuiButton button) {
/* 37 */     this.list.actionPerformed(button);
/*    */     
/* 39 */     if (button.id == 1) {
/* 40 */       this.mc.displayGuiScreen(this.parent);
/*    */     }
/*    */   }
/*    */   
/*    */   public void handleMouseInput() throws IOException {
/* 45 */     this.list.handleMouseInput();
/* 46 */     super.handleMouseInput();
/*    */   }
/*    */   
/*    */   public static boolean isHovered(float x, float y, float x2, float y2, int mouseX, int mouseY) {
/* 50 */     return (mouseX >= x && mouseX <= x2 && mouseY >= y && mouseY <= y2);
/*    */   }
/*    */ 
/*    */   
/*    */   public void drawScreen(int mouseX, int mouseY, float partialTicks) {
/* 55 */     this.list.drawScreen(mouseX, mouseY, partialTicks);
/*    */     
/* 57 */     GL11.glPushMatrix();
/* 58 */     GL11.glScalef(1.5F, 1.5F, 1.5F);
/* 59 */     Client.instance.FontLoaders.Comfortaa34.drawCenteredString(EnumChatFormatting.BOLD + "ViaMCP", (this.width / 3), 2.0F, 16777215);
/* 60 */     GL11.glPopMatrix();
/*    */     
/* 62 */     super.drawScreen(mouseX, mouseY, partialTicks);
/*    */   }
/*    */   
/*    */   class SlotList
/*    */     extends GuiSlot {
/*    */     public SlotList(Minecraft p_i1052_1_, int p_i1052_2_, int p_i1052_3_, int p_i1052_4_, int p_i1052_5_, int p_i1052_6_) {
/* 68 */       super(p_i1052_1_, p_i1052_2_, p_i1052_3_, p_i1052_4_, p_i1052_5_, p_i1052_6_);
/*    */     }
/*    */ 
/*    */     
/*    */     protected int getSize() {
/* 73 */       return ProtocolSorter.getProtocolVersions().size();
/*    */     }
/*    */ 
/*    */     
/*    */     protected void elementClicked(int i, boolean b, int i1, int i2) {
/* 78 */       ViaFabric.clientSideVersion = ((ProtocolVersion)ProtocolSorter.getProtocolVersions().get(i)).getVersion();
/*    */     }
/*    */ 
/*    */     
/*    */     protected boolean isSelected(int i) {
/* 83 */       return false;
/*    */     }
/*    */ 
/*    */     
/*    */     protected void drawBackground() {
/* 88 */       GuiProtocolSelector.this.drawDefaultBackground();
/*    */     }
/*    */ 
/*    */     
/*    */     protected void drawSlot(int i, int i1, int i2, int i3, int i4, int i5) {
/* 93 */       Client.instance.FontLoaders.Comfortaa18.drawCenteredStringWithShadow(((ViaFabric.clientSideVersion == ((ProtocolVersion)ProtocolSorter.getProtocolVersions().get(i)).getVersion()) ? EnumChatFormatting.GREEN.toString() : EnumChatFormatting.WHITE.toString()) + ProtocolUtils.getProtocolName(((ProtocolVersion)ProtocolSorter.getProtocolVersions().get(i)).getVersion()), (this.width / 2), (i2 + 2), -1);
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\com\viamcp\gui\GuiProtocolSelector.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */