/*     */ package net.minecraft.client;
/*     */ 
/*     */ import net.minecraft.client.gui.Gui;
/*     */ import net.minecraft.client.gui.ScaledResolution;
/*     */ import net.minecraft.client.renderer.GlStateManager;
/*     */ import net.minecraft.client.renderer.OpenGlHelper;
/*     */ import net.minecraft.client.renderer.Tessellator;
/*     */ import net.minecraft.client.renderer.WorldRenderer;
/*     */ import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
/*     */ import net.minecraft.client.shader.Framebuffer;
/*     */ import net.minecraft.util.IProgressUpdate;
/*     */ import net.minecraft.util.MinecraftError;
/*     */ import net.optifine.CustomLoadingScreen;
/*     */ import net.optifine.CustomLoadingScreens;
/*     */ import net.optifine.reflect.Reflector;
/*     */ 
/*     */ public class LoadingScreenRenderer
/*     */   implements IProgressUpdate {
/*  19 */   private String message = "";
/*     */ 
/*     */ 
/*     */   
/*     */   private final Minecraft mc;
/*     */ 
/*     */ 
/*     */   
/*  27 */   private String currentlyDisplayedText = "";
/*     */ 
/*     */   
/*  30 */   private long systemTime = Minecraft.getSystemTime();
/*     */   
/*     */   private boolean loadingSuccess;
/*     */   
/*     */   private final ScaledResolution scaledResolution;
/*     */   
/*     */   private final Framebuffer framebuffer;
/*     */   
/*     */   public LoadingScreenRenderer(Minecraft mcIn) {
/*  39 */     this.mc = mcIn;
/*  40 */     this.scaledResolution = new ScaledResolution(mcIn);
/*  41 */     this.framebuffer = new Framebuffer(mcIn.displayWidth, mcIn.displayHeight, false);
/*  42 */     this.framebuffer.setFramebufferFilter(9728);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void resetProgressAndMessage(String message) {
/*  51 */     this.loadingSuccess = false;
/*  52 */     displayString(message);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void displaySavingString(String message) {
/*  60 */     this.loadingSuccess = true;
/*  61 */     displayString(message);
/*     */   }
/*     */ 
/*     */   
/*     */   private void displayString(String message) {
/*  66 */     this.currentlyDisplayedText = message;
/*     */     
/*  68 */     if (!this.mc.running) {
/*     */       
/*  70 */       if (!this.loadingSuccess)
/*     */       {
/*  72 */         throw new MinecraftError();
/*     */       }
/*     */     }
/*     */     else {
/*     */       
/*  77 */       GlStateManager.clear(256);
/*  78 */       GlStateManager.matrixMode(5889);
/*  79 */       GlStateManager.loadIdentity();
/*     */       
/*  81 */       if (OpenGlHelper.isFramebufferEnabled()) {
/*     */         
/*  83 */         int i = this.scaledResolution.getScaleFactor();
/*  84 */         GlStateManager.ortho(0.0D, (this.scaledResolution.getScaledWidth() * i), (this.scaledResolution.getScaledHeight() * i), 0.0D, 100.0D, 300.0D);
/*     */       }
/*     */       else {
/*     */         
/*  88 */         ScaledResolution scaledresolution = new ScaledResolution(this.mc);
/*  89 */         GlStateManager.ortho(0.0D, scaledresolution.getScaledWidth_double(), scaledresolution.getScaledHeight_double(), 0.0D, 100.0D, 300.0D);
/*     */       } 
/*     */       
/*  92 */       GlStateManager.matrixMode(5888);
/*  93 */       GlStateManager.loadIdentity();
/*  94 */       GlStateManager.translate(0.0F, 0.0F, -200.0F);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void displayLoadingString(String message) {
/* 103 */     if (!this.mc.running) {
/*     */       
/* 105 */       if (!this.loadingSuccess)
/*     */       {
/* 107 */         throw new MinecraftError();
/*     */       }
/*     */     }
/*     */     else {
/*     */       
/* 112 */       this.systemTime = 0L;
/* 113 */       this.message = message;
/* 114 */       setLoadingProgress(-1);
/* 115 */       this.systemTime = 0L;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setLoadingProgress(int progress) {
/* 124 */     if (!this.mc.running) {
/*     */       
/* 126 */       if (!this.loadingSuccess)
/*     */       {
/* 128 */         throw new MinecraftError();
/*     */       }
/*     */     }
/*     */     else {
/*     */       
/* 133 */       long i = Minecraft.getSystemTime();
/*     */       
/* 135 */       if (i - this.systemTime >= 100L) {
/*     */         
/* 137 */         this.systemTime = i;
/* 138 */         ScaledResolution scaledresolution = new ScaledResolution(this.mc);
/* 139 */         int j = scaledresolution.getScaleFactor();
/* 140 */         int k = scaledresolution.getScaledWidth();
/* 141 */         int l = scaledresolution.getScaledHeight();
/*     */         
/* 143 */         if (OpenGlHelper.isFramebufferEnabled()) {
/*     */           
/* 145 */           this.framebuffer.framebufferClear();
/*     */         }
/*     */         else {
/*     */           
/* 149 */           GlStateManager.clear(256);
/*     */         } 
/*     */         
/* 152 */         this.framebuffer.bindFramebuffer(false);
/* 153 */         GlStateManager.matrixMode(5889);
/* 154 */         GlStateManager.loadIdentity();
/* 155 */         GlStateManager.ortho(0.0D, scaledresolution.getScaledWidth_double(), scaledresolution.getScaledHeight_double(), 0.0D, 100.0D, 300.0D);
/* 156 */         GlStateManager.matrixMode(5888);
/* 157 */         GlStateManager.loadIdentity();
/* 158 */         GlStateManager.translate(0.0F, 0.0F, -200.0F);
/*     */         
/* 160 */         if (!OpenGlHelper.isFramebufferEnabled())
/*     */         {
/* 162 */           GlStateManager.clear(16640);
/*     */         }
/*     */         
/* 165 */         boolean flag = true;
/*     */         
/* 167 */         if (Reflector.FMLClientHandler_handleLoadingScreen.exists()) {
/*     */           
/* 169 */           Object object = Reflector.call(Reflector.FMLClientHandler_instance, new Object[0]);
/*     */           
/* 171 */           if (object != null)
/*     */           {
/* 173 */             flag = !Reflector.callBoolean(object, Reflector.FMLClientHandler_handleLoadingScreen, new Object[] { scaledresolution });
/*     */           }
/*     */         } 
/*     */         
/* 177 */         if (flag) {
/*     */           
/* 179 */           Tessellator tessellator = Tessellator.getInstance();
/* 180 */           WorldRenderer worldrenderer = tessellator.getWorldRenderer();
/* 181 */           CustomLoadingScreen customloadingscreen = CustomLoadingScreens.getCustomLoadingScreen();
/*     */           
/* 183 */           if (customloadingscreen != null) {
/*     */             
/* 185 */             customloadingscreen.drawBackground(scaledresolution.getScaledWidth(), scaledresolution.getScaledHeight());
/*     */           }
/*     */           else {
/*     */             
/* 189 */             this.mc.getTextureManager().bindTexture(Gui.optionsBackground);
/* 190 */             float f = 32.0F;
/* 191 */             worldrenderer.begin(7, DefaultVertexFormats.POSITION_TEX_COLOR);
/* 192 */             worldrenderer.pos(0.0D, l, 0.0D).tex(0.0D, (l / f)).color(64, 64, 64, 255).endVertex();
/* 193 */             worldrenderer.pos(k, l, 0.0D).tex((k / f), (l / f)).color(64, 64, 64, 255).endVertex();
/* 194 */             worldrenderer.pos(k, 0.0D, 0.0D).tex((k / f), 0.0D).color(64, 64, 64, 255).endVertex();
/* 195 */             worldrenderer.pos(0.0D, 0.0D, 0.0D).tex(0.0D, 0.0D).color(64, 64, 64, 255).endVertex();
/* 196 */             tessellator.draw();
/*     */           } 
/*     */           
/* 199 */           if (progress >= 0) {
/*     */             
/* 201 */             int l1 = 100;
/* 202 */             int i1 = 2;
/* 203 */             int j1 = k / 2 - l1 / 2;
/* 204 */             int k1 = l / 2 + 16;
/* 205 */             GlStateManager.disableTexture2D();
/* 206 */             worldrenderer.begin(7, DefaultVertexFormats.POSITION_COLOR);
/* 207 */             worldrenderer.pos(j1, k1, 0.0D).color(128, 128, 128, 255).endVertex();
/* 208 */             worldrenderer.pos(j1, (k1 + i1), 0.0D).color(128, 128, 128, 255).endVertex();
/* 209 */             worldrenderer.pos((j1 + l1), (k1 + i1), 0.0D).color(128, 128, 128, 255).endVertex();
/* 210 */             worldrenderer.pos((j1 + l1), k1, 0.0D).color(128, 128, 128, 255).endVertex();
/* 211 */             worldrenderer.pos(j1, k1, 0.0D).color(128, 255, 128, 255).endVertex();
/* 212 */             worldrenderer.pos(j1, (k1 + i1), 0.0D).color(128, 255, 128, 255).endVertex();
/* 213 */             worldrenderer.pos((j1 + progress), (k1 + i1), 0.0D).color(128, 255, 128, 255).endVertex();
/* 214 */             worldrenderer.pos((j1 + progress), k1, 0.0D).color(128, 255, 128, 255).endVertex();
/* 215 */             tessellator.draw();
/* 216 */             GlStateManager.enableTexture2D();
/*     */           } 
/*     */           
/* 219 */           GlStateManager.enableBlend();
/* 220 */           GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
/* 221 */           this.mc.fontRendererObj.drawStringWithShadow(this.currentlyDisplayedText, ((k - this.mc.fontRendererObj.getStringWidth(this.currentlyDisplayedText)) / 2), (l / 2 - 4 - 16), 16777215);
/* 222 */           this.mc.fontRendererObj.drawStringWithShadow(this.message, ((k - this.mc.fontRendererObj.getStringWidth(this.message)) / 2), (l / 2 - 4 + 8), 16777215);
/*     */         } 
/*     */         
/* 225 */         this.framebuffer.unbindFramebuffer();
/*     */         
/* 227 */         if (OpenGlHelper.isFramebufferEnabled())
/*     */         {
/* 229 */           this.framebuffer.framebufferRender(k * j, l * j);
/*     */         }
/*     */         
/* 232 */         this.mc.updateDisplay();
/*     */ 
/*     */         
/*     */         try {
/* 236 */           Thread.yield();
/*     */         }
/* 238 */         catch (Exception exception) {}
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   public void setDoneWorking() {}
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\minecraft\client\LoadingScreenRenderer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */