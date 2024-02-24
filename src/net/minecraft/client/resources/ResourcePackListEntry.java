/*     */ package net.minecraft.client.resources;
/*     */ import awareline.main.ui.font.FontUtils;
/*     */ import awareline.main.ui.font.fastuni.FontLoader;
/*     */ import java.util.List;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.gui.Gui;
/*     */ import net.minecraft.client.gui.GuiScreen;
/*     */ import net.minecraft.client.gui.GuiScreenResourcePacks;
/*     */ import net.minecraft.client.gui.GuiYesNoCallback;
/*     */ import net.minecraft.client.renderer.GlStateManager;
/*     */ import net.minecraft.util.ChatComponentTranslation;
/*     */ import net.minecraft.util.IChatComponent;
/*     */ import net.minecraft.util.ResourceLocation;
/*     */ 
/*     */ public abstract class ResourcePackListEntry implements GuiListExtended.IGuiListEntry {
/*  16 */   private static final ResourceLocation RESOURCE_PACKS_TEXTURE = new ResourceLocation("textures/gui/resource_packs.png");
/*  17 */   private static final IChatComponent field_183020_d = (IChatComponent)new ChatComponentTranslation("resourcePack.incompatible", new Object[0]);
/*  18 */   private static final IChatComponent field_183021_e = (IChatComponent)new ChatComponentTranslation("resourcePack.incompatible.old", new Object[0]);
/*  19 */   private static final IChatComponent field_183022_f = (IChatComponent)new ChatComponentTranslation("resourcePack.incompatible.new", new Object[0]);
/*     */   
/*     */   protected final Minecraft mc;
/*     */   protected final GuiScreenResourcePacks resourcePacksGUI;
/*     */   
/*     */   public ResourcePackListEntry(GuiScreenResourcePacks resourcePacksGUIIn) {
/*  25 */     this.resourcePacksGUI = resourcePacksGUIIn;
/*  26 */     this.mc = Minecraft.getMinecraft();
/*     */   }
/*     */ 
/*     */   
/*     */   public void drawEntry(int slotIndex, int x, int y, int listWidth, int slotHeight, int mouseX, int mouseY, boolean isSelected) {
/*  31 */     int i = func_183019_a();
/*     */     
/*  33 */     if (i != 1) {
/*     */       
/*  35 */       GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
/*  36 */       Gui.drawRect(x - 1, y - 1, x + listWidth - 9, y + slotHeight + 1, -8978432);
/*     */     } 
/*     */     
/*  39 */     func_148313_c();
/*  40 */     GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
/*  41 */     Gui.drawModalRectWithCustomSizedTexture(x, y, 0.0F, 0.0F, 32, 32, 32.0F, 32.0F);
/*  42 */     String s = func_148312_b();
/*  43 */     String s1 = func_148311_a();
/*     */     
/*  45 */     if ((this.mc.gameSettings.touchscreen || isSelected) && func_148310_d()) {
/*     */       
/*  47 */       this.mc.getTextureManager().bindTexture(RESOURCE_PACKS_TEXTURE);
/*  48 */       Gui.drawRect(x, y, x + 32, y + 32, -1601138544);
/*  49 */       GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
/*  50 */       int j = mouseX - x;
/*  51 */       int k = mouseY - y;
/*     */       
/*  53 */       if (i < 1) {
/*     */         
/*  55 */         s = field_183020_d.getFormattedText();
/*  56 */         s1 = field_183021_e.getFormattedText();
/*     */       }
/*  58 */       else if (i > 1) {
/*     */         
/*  60 */         s = field_183020_d.getFormattedText();
/*  61 */         s1 = field_183022_f.getFormattedText();
/*     */       } 
/*     */       
/*  64 */       if (func_148309_e()) {
/*     */         
/*  66 */         if (j < 32)
/*     */         {
/*  68 */           Gui.drawModalRectWithCustomSizedTexture(x, y, 0.0F, 32.0F, 32, 32, 256.0F, 256.0F);
/*     */         }
/*     */         else
/*     */         {
/*  72 */           Gui.drawModalRectWithCustomSizedTexture(x, y, 0.0F, 0.0F, 32, 32, 256.0F, 256.0F);
/*     */         }
/*     */       
/*     */       } else {
/*     */         
/*  77 */         if (func_148308_f())
/*     */         {
/*  79 */           if (j < 16) {
/*     */             
/*  81 */             Gui.drawModalRectWithCustomSizedTexture(x, y, 32.0F, 32.0F, 32, 32, 256.0F, 256.0F);
/*     */           }
/*     */           else {
/*     */             
/*  85 */             Gui.drawModalRectWithCustomSizedTexture(x, y, 32.0F, 0.0F, 32, 32, 256.0F, 256.0F);
/*     */           } 
/*     */         }
/*     */         
/*  89 */         if (func_148314_g())
/*     */         {
/*  91 */           if (j < 32 && j > 16 && k < 16) {
/*     */             
/*  93 */             Gui.drawModalRectWithCustomSizedTexture(x, y, 96.0F, 32.0F, 32, 32, 256.0F, 256.0F);
/*     */           }
/*     */           else {
/*     */             
/*  97 */             Gui.drawModalRectWithCustomSizedTexture(x, y, 96.0F, 0.0F, 32, 32, 256.0F, 256.0F);
/*     */           } 
/*     */         }
/*     */         
/* 101 */         if (func_148307_h())
/*     */         {
/* 103 */           if (j < 32 && j > 16 && k > 16) {
/*     */             
/* 105 */             Gui.drawModalRectWithCustomSizedTexture(x, y, 64.0F, 32.0F, 32, 32, 256.0F, 256.0F);
/*     */           }
/*     */           else {
/*     */             
/* 109 */             Gui.drawModalRectWithCustomSizedTexture(x, y, 64.0F, 0.0F, 32, 32, 256.0F, 256.0F);
/*     */           } 
/*     */         }
/*     */       } 
/*     */     } 
/* 114 */     int i1 = FontLoader.PF16.getStringWidth(s);
/*     */     
/* 116 */     if (i1 > 157)
/*     */     {
/* 118 */       s = FontUtils.trimStringToWidth(FontLoader.PF16, s, 157 - this.mc.fontRendererObj.getStringWidth("...")) + "...";
/*     */     }
/*     */     
/* 121 */     FontLoader.PF16.drawStringWithShadow(s, (x + 34), (y + 1), 16777215);
/*     */     
/* 123 */     List<String> list = FontUtils.listFormattedStringToWidth(FontLoader.PF16, s1, 157);
/*     */     
/* 125 */     for (int l = 0; l < 2 && l < list.size(); l++)
/*     */     {
/* 127 */       FontLoader.PF16.drawStringWithShadow(list.get(l), (x + 32 + 2), (y + 12 + 10 * l), 8421504);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected abstract int func_183019_a();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected abstract String func_148311_a();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected abstract String func_148312_b();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected abstract void func_148313_c();
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean func_148310_d() {
/* 159 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   protected boolean func_148309_e() {
/* 164 */     return !this.resourcePacksGUI.hasResourcePackEntry(this);
/*     */   }
/*     */ 
/*     */   
/*     */   protected boolean func_148308_f() {
/* 169 */     return this.resourcePacksGUI.hasResourcePackEntry(this);
/*     */   }
/*     */ 
/*     */   
/*     */   protected boolean func_148314_g() {
/* 174 */     List<ResourcePackListEntry> list = this.resourcePacksGUI.getListContaining(this);
/* 175 */     int i = list.indexOf(this);
/* 176 */     return (i > 0 && ((ResourcePackListEntry)list.get(i - 1)).func_148310_d());
/*     */   }
/*     */ 
/*     */   
/*     */   protected boolean func_148307_h() {
/* 181 */     List<ResourcePackListEntry> list = this.resourcePacksGUI.getListContaining(this);
/* 182 */     int i = list.indexOf(this);
/* 183 */     return (i >= 0 && i < list.size() - 1 && ((ResourcePackListEntry)list.get(i + 1)).func_148310_d());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean mousePressed(int slotIndex, int p_148278_2_, int p_148278_3_, int p_148278_4_, int p_148278_5_, int p_148278_6_) {
/* 191 */     if (func_148310_d() && p_148278_5_ <= 32) {
/*     */       
/* 193 */       if (func_148309_e()) {
/*     */         
/* 195 */         this.resourcePacksGUI.markChanged();
/* 196 */         int j = func_183019_a();
/*     */         
/* 198 */         if (j != 1) {
/*     */           
/* 200 */           String s1 = I18n.format("resourcePack.incompatible.confirm.title", new Object[0]);
/* 201 */           String s = I18n.format("resourcePack.incompatible.confirm." + ((j > 1) ? "new" : "old"), new Object[0]);
/* 202 */           this.mc.displayGuiScreen((GuiScreen)new GuiYesNo(new GuiYesNoCallback()
/*     */                 {
/*     */                   public void confirmClicked(boolean result, int id)
/*     */                   {
/* 206 */                     List<ResourcePackListEntry> list2 = ResourcePackListEntry.this.resourcePacksGUI.getListContaining(ResourcePackListEntry.this);
/* 207 */                     ResourcePackListEntry.this.mc.displayGuiScreen((GuiScreen)ResourcePackListEntry.this.resourcePacksGUI);
/*     */                     
/* 209 */                     if (result)
/*     */                     {
/* 211 */                       list2.remove(ResourcePackListEntry.this);
/* 212 */                       ResourcePackListEntry.this.resourcePacksGUI.getSelectedResourcePacks().add(0, ResourcePackListEntry.this);
/*     */                     }
/*     */                   
/*     */                   }
/*     */                 }s1, s, 0));
/*     */         } else {
/*     */           
/* 219 */           this.resourcePacksGUI.getListContaining(this).remove(this);
/* 220 */           this.resourcePacksGUI.getSelectedResourcePacks().add(0, this);
/*     */         } 
/*     */         
/* 223 */         return true;
/*     */       } 
/*     */       
/* 226 */       if (p_148278_5_ < 16 && func_148308_f()) {
/*     */         
/* 228 */         this.resourcePacksGUI.getListContaining(this).remove(this);
/* 229 */         this.resourcePacksGUI.getAvailableResourcePacks().add(0, this);
/* 230 */         this.resourcePacksGUI.markChanged();
/* 231 */         return true;
/*     */       } 
/*     */       
/* 234 */       if (p_148278_5_ > 16 && p_148278_6_ < 16 && func_148314_g()) {
/*     */         
/* 236 */         List<ResourcePackListEntry> list1 = this.resourcePacksGUI.getListContaining(this);
/* 237 */         int k = list1.indexOf(this);
/* 238 */         list1.remove(this);
/* 239 */         list1.add(k - 1, this);
/* 240 */         this.resourcePacksGUI.markChanged();
/* 241 */         return true;
/*     */       } 
/*     */       
/* 244 */       if (p_148278_5_ > 16 && p_148278_6_ > 16 && func_148307_h()) {
/*     */         
/* 246 */         List<ResourcePackListEntry> list = this.resourcePacksGUI.getListContaining(this);
/* 247 */         int i = list.indexOf(this);
/* 248 */         list.remove(this);
/* 249 */         list.add(i + 1, this);
/* 250 */         this.resourcePacksGUI.markChanged();
/* 251 */         return true;
/*     */       } 
/*     */     } 
/*     */     
/* 255 */     return false;
/*     */   }
/*     */   
/*     */   public void setSelected(int p_178011_1_, int p_178011_2_, int p_178011_3_) {}
/*     */   
/*     */   public void mouseReleased(int slotIndex, int x, int y, int mouseEvent, int relativeX, int relativeY) {}
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\minecraft\client\resources\ResourcePackListEntry.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */