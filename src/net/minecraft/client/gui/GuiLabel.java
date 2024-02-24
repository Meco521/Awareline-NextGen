/*    */ package net.minecraft.client.gui;
/*    */ 
/*    */ import com.google.common.collect.Lists;
/*    */ import java.util.List;
/*    */ import net.minecraft.client.Minecraft;
/*    */ import net.minecraft.client.renderer.GlStateManager;
/*    */ import net.minecraft.client.resources.I18n;
/*    */ 
/*    */ public class GuiLabel
/*    */   extends Gui
/*    */ {
/* 12 */   protected int field_146167_a = 200;
/* 13 */   protected int field_146161_f = 20;
/*    */   
/*    */   public int field_146162_g;
/*    */   public int field_146174_h;
/*    */   private final List<String> field_146173_k;
/*    */   public int field_175204_i;
/*    */   private boolean centered;
/*    */   public boolean visible = true;
/*    */   private final boolean labelBgEnabled;
/*    */   private final int field_146168_n;
/*    */   private final int field_146169_o;
/*    */   private final int field_146166_p;
/*    */   private final int field_146165_q;
/*    */   private final FontRenderer fontRenderer;
/*    */   private final int field_146163_s;
/*    */   
/*    */   public GuiLabel(FontRenderer fontRendererObj, int p_i45540_2_, int p_i45540_3_, int p_i45540_4_, int p_i45540_5_, int p_i45540_6_, int p_i45540_7_) {
/* 30 */     this.fontRenderer = fontRendererObj;
/* 31 */     this.field_175204_i = p_i45540_2_;
/* 32 */     this.field_146162_g = p_i45540_3_;
/* 33 */     this.field_146174_h = p_i45540_4_;
/* 34 */     this.field_146167_a = p_i45540_5_;
/* 35 */     this.field_146161_f = p_i45540_6_;
/* 36 */     this.field_146173_k = Lists.newArrayList();
/* 37 */     this.centered = false;
/* 38 */     this.labelBgEnabled = false;
/* 39 */     this.field_146168_n = p_i45540_7_;
/* 40 */     this.field_146169_o = -1;
/* 41 */     this.field_146166_p = -1;
/* 42 */     this.field_146165_q = -1;
/* 43 */     this.field_146163_s = 0;
/*    */   }
/*    */ 
/*    */   
/*    */   public void func_175202_a(String p_175202_1_) {
/* 48 */     this.field_146173_k.add(I18n.format(p_175202_1_, new Object[0]));
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public GuiLabel setCentered() {
/* 56 */     this.centered = true;
/* 57 */     return this;
/*    */   }
/*    */ 
/*    */   
/*    */   public void drawLabel(Minecraft mc, int mouseX, int mouseY) {
/* 62 */     if (this.visible) {
/*    */       
/* 64 */       GlStateManager.enableBlend();
/* 65 */       GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
/* 66 */       drawLabelBackground(mc, mouseX, mouseY);
/* 67 */       int i = this.field_146174_h + this.field_146161_f / 2 + this.field_146163_s / 2;
/* 68 */       int j = i - this.field_146173_k.size() * 10 / 2;
/*    */       
/* 70 */       for (int k = 0; k < this.field_146173_k.size(); k++) {
/*    */         
/* 72 */         if (this.centered) {
/*    */           
/* 74 */           drawCenteredString(this.fontRenderer, this.field_146173_k.get(k), this.field_146162_g + this.field_146167_a / 2, j + k * 10, this.field_146168_n);
/*    */         }
/*    */         else {
/*    */           
/* 78 */           drawString(this.fontRenderer, this.field_146173_k.get(k), this.field_146162_g, j + k * 10, this.field_146168_n);
/*    */         } 
/*    */       } 
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   protected void drawLabelBackground(Minecraft mcIn, int p_146160_2_, int p_146160_3_) {
/* 86 */     if (this.labelBgEnabled) {
/*    */       
/* 88 */       int i = this.field_146167_a + (this.field_146163_s << 1);
/* 89 */       int j = this.field_146161_f + (this.field_146163_s << 1);
/* 90 */       int k = this.field_146162_g - this.field_146163_s;
/* 91 */       int l = this.field_146174_h - this.field_146163_s;
/* 92 */       drawRect(k, l, k + i, l + j, this.field_146169_o);
/* 93 */       drawHorizontalLine(k, k + i, l, this.field_146166_p);
/* 94 */       drawHorizontalLine(k, k + i, l + j, this.field_146165_q);
/* 95 */       drawVerticalLine(k, l, l + j, this.field_146166_p);
/* 96 */       drawVerticalLine(k + i, l, l + j, this.field_146165_q);
/*    */     } 
/*    */   }
/*    */   
/*    */   public void drawLabel(int mouseX, int mouseY) {}
/*    */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\minecraft\client\gui\GuiLabel.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */