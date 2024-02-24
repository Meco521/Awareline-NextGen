/*     */ package net.minecraft.client.gui;
/*     */ import com.google.common.collect.Lists;
/*     */ import java.io.File;
/*     */ import java.io.IOException;
/*     */ import java.util.Collections;
/*     */ import java.util.List;
/*     */ import net.minecraft.client.resources.I18n;
/*     */ import net.minecraft.client.resources.ResourcePackListEntry;
/*     */ import net.minecraft.client.resources.ResourcePackListEntryDefault;
/*     */ import net.minecraft.client.resources.ResourcePackListEntryFound;
/*     */ import net.minecraft.client.resources.ResourcePackRepository;
/*     */ import net.minecraft.util.Util;
/*     */ import org.apache.logging.log4j.LogManager;
/*     */ import org.apache.logging.log4j.Logger;
/*     */ import org.lwjgl.Sys;
/*     */ 
/*     */ public class GuiScreenResourcePacks extends GuiScreen {
/*  18 */   private static final Logger logger = LogManager.getLogger();
/*     */   
/*     */   private final GuiScreen parentScreen;
/*     */   
/*     */   private List<ResourcePackListEntry> availableResourcePacks;
/*     */   
/*     */   private List<ResourcePackListEntry> selectedResourcePacks;
/*     */   
/*     */   private GuiResourcePackAvailable availableResourcePacksList;
/*     */   
/*     */   private GuiResourcePackSelected selectedResourcePacksList;
/*     */   private boolean changed;
/*     */   
/*     */   public GuiScreenResourcePacks(GuiScreen parentScreenIn) {
/*  32 */     this.parentScreen = parentScreenIn;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void initGui() {
/*  41 */     this.buttonList.add(new GuiOptionButton(2, this.width / 2 - 154, this.height - 48, I18n.format("resourcePack.openFolder", new Object[0])));
/*  42 */     this.buttonList.add(new GuiOptionButton(1, this.width / 2 + 4, this.height - 48, I18n.format("gui.done", new Object[0])));
/*     */     
/*  44 */     if (!this.changed) {
/*     */       
/*  46 */       this.availableResourcePacks = Lists.newArrayList();
/*  47 */       this.selectedResourcePacks = Lists.newArrayList();
/*  48 */       ResourcePackRepository resourcepackrepository = this.mc.getResourcePackRepository();
/*  49 */       resourcepackrepository.updateRepositoryEntriesAll();
/*  50 */       List<ResourcePackRepository.Entry> list = Lists.newArrayList(resourcepackrepository.getRepositoryEntriesAll());
/*  51 */       list.removeAll(resourcepackrepository.getRepositoryEntries());
/*     */       
/*  53 */       for (ResourcePackRepository.Entry resourcepackrepository$entry : list)
/*     */       {
/*  55 */         this.availableResourcePacks.add(new ResourcePackListEntryFound(this, resourcepackrepository$entry));
/*     */       }
/*     */       
/*  58 */       for (ResourcePackRepository.Entry resourcepackrepository$entry1 : Lists.reverse(resourcepackrepository.getRepositoryEntries()))
/*     */       {
/*  60 */         this.selectedResourcePacks.add(new ResourcePackListEntryFound(this, resourcepackrepository$entry1));
/*     */       }
/*     */       
/*  63 */       this.selectedResourcePacks.add(new ResourcePackListEntryDefault(this));
/*     */     } 
/*     */     
/*  66 */     this.availableResourcePacksList = new GuiResourcePackAvailable(this.mc, 200, this.height, this.availableResourcePacks);
/*  67 */     this.availableResourcePacksList.setSlotXBoundsFromLeft(this.width / 2 - 4 - 200);
/*  68 */     this.availableResourcePacksList.registerScrollButtons(7, 8);
/*  69 */     this.selectedResourcePacksList = new GuiResourcePackSelected(this.mc, 200, this.height, this.selectedResourcePacks);
/*  70 */     this.selectedResourcePacksList.setSlotXBoundsFromLeft(this.width / 2 + 4);
/*  71 */     this.selectedResourcePacksList.registerScrollButtons(7, 8);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void handleMouseInput() throws IOException {
/*  79 */     super.handleMouseInput();
/*  80 */     this.selectedResourcePacksList.handleMouseInput();
/*  81 */     this.availableResourcePacksList.handleMouseInput();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean hasResourcePackEntry(ResourcePackListEntry p_146961_1_) {
/*  86 */     return this.selectedResourcePacks.contains(p_146961_1_);
/*     */   }
/*     */ 
/*     */   
/*     */   public List<ResourcePackListEntry> getListContaining(ResourcePackListEntry p_146962_1_) {
/*  91 */     return hasResourcePackEntry(p_146962_1_) ? this.selectedResourcePacks : this.availableResourcePacks;
/*     */   }
/*     */ 
/*     */   
/*     */   public List<ResourcePackListEntry> getAvailableResourcePacks() {
/*  96 */     return this.availableResourcePacks;
/*     */   }
/*     */ 
/*     */   
/*     */   public List<ResourcePackListEntry> getSelectedResourcePacks() {
/* 101 */     return this.selectedResourcePacks;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void actionPerformed(GuiButton button) {
/* 108 */     if (button.enabled)
/*     */     {
/* 110 */       if (button.id == 2) {
/*     */         
/* 112 */         File file1 = this.mc.getResourcePackRepository().getDirResourcepacks();
/* 113 */         String s = file1.getAbsolutePath();
/*     */         
/* 115 */         if (Util.getOSType() == Util.EnumOS.OSX) {
/*     */ 
/*     */           
/*     */           try {
/* 119 */             logger.info(s);
/* 120 */             Runtime.getRuntime().exec(new String[] { "/usr/bin/open", s });
/*     */             
/*     */             return;
/* 123 */           } catch (IOException ioexception1) {
/*     */             
/* 125 */             logger.error("Couldn't open file", ioexception1);
/*     */           }
/*     */         
/* 128 */         } else if (Util.getOSType() == Util.EnumOS.WINDOWS) {
/*     */           
/* 130 */           String s1 = String.format("cmd.exe /C start \"Open file\" \"%s\"", new Object[] { s });
/*     */ 
/*     */           
/*     */           try {
/* 134 */             Runtime.getRuntime().exec(s1);
/*     */             
/*     */             return;
/* 137 */           } catch (IOException ioexception) {
/*     */             
/* 139 */             logger.error("Couldn't open file", ioexception);
/*     */           } 
/*     */         } 
/*     */         
/* 143 */         boolean flag = false;
/*     */ 
/*     */         
/*     */         try {
/* 147 */           Class<?> oclass = Class.forName("java.awt.Desktop");
/* 148 */           Object object = oclass.getMethod("getDesktop", new Class[0]).invoke(null, new Object[0]);
/* 149 */           oclass.getMethod("browse", new Class[] { URI.class }).invoke(object, new Object[] { file1.toURI() });
/*     */         }
/* 151 */         catch (Throwable throwable) {
/*     */           
/* 153 */           logger.error("Couldn't open link", throwable);
/* 154 */           flag = true;
/*     */         } 
/*     */         
/* 157 */         if (flag)
/*     */         {
/* 159 */           logger.info("Opening via system class!");
/* 160 */           Sys.openURL("file://" + s);
/*     */         }
/*     */       
/* 163 */       } else if (button.id == 1) {
/*     */         
/* 165 */         if (this.changed) {
/*     */           
/* 167 */           List<ResourcePackRepository.Entry> list = Lists.newArrayList();
/*     */           
/* 169 */           for (ResourcePackListEntry resourcepacklistentry : this.selectedResourcePacks) {
/*     */             
/* 171 */             if (resourcepacklistentry instanceof ResourcePackListEntryFound)
/*     */             {
/* 173 */               list.add(((ResourcePackListEntryFound)resourcepacklistentry).func_148318_i());
/*     */             }
/*     */           } 
/*     */           
/* 177 */           Collections.reverse(list);
/* 178 */           this.mc.getResourcePackRepository().setRepositories(list);
/* 179 */           this.mc.gameSettings.resourcePacks.clear();
/* 180 */           this.mc.gameSettings.incompatibleResourcePacks.clear();
/*     */           
/* 182 */           for (ResourcePackRepository.Entry resourcepackrepository$entry : list) {
/*     */             
/* 184 */             this.mc.gameSettings.resourcePacks.add(resourcepackrepository$entry.getResourcePackName());
/*     */             
/* 186 */             if (resourcepackrepository$entry.func_183027_f() != 1)
/*     */             {
/* 188 */               this.mc.gameSettings.incompatibleResourcePacks.add(resourcepackrepository$entry.getResourcePackName());
/*     */             }
/*     */           } 
/*     */           
/* 192 */           this.mc.gameSettings.saveOptions();
/* 193 */           this.mc.refreshResources();
/*     */         } 
/*     */         
/* 196 */         this.mc.displayGuiScreen(this.parentScreen);
/*     */       } 
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
/* 206 */     super.mouseClicked(mouseX, mouseY, mouseButton);
/* 207 */     this.availableResourcePacksList.mouseClicked(mouseX, mouseY, mouseButton);
/* 208 */     this.selectedResourcePacksList.mouseClicked(mouseX, mouseY, mouseButton);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void mouseReleased(int mouseX, int mouseY, int state) {
/* 216 */     super.mouseReleased(mouseX, mouseY, state);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void drawScreen(int mouseX, int mouseY, float partialTicks) {
/* 224 */     drawBackground(0);
/* 225 */     this.availableResourcePacksList.drawScreen(mouseX, mouseY, partialTicks);
/* 226 */     this.selectedResourcePacksList.drawScreen(mouseX, mouseY, partialTicks);
/* 227 */     drawCenteredString(this.fontRendererObj, I18n.format("resourcePack.title", new Object[0]), this.width / 2, 16, 16777215);
/* 228 */     drawCenteredString(this.fontRendererObj, I18n.format("resourcePack.folderInfo", new Object[0]), this.width / 2 - 77, this.height - 26, 8421504);
/* 229 */     super.drawScreen(mouseX, mouseY, partialTicks);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void markChanged() {
/* 237 */     this.changed = true;
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\minecraft\client\gui\GuiScreenResourcePacks.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */