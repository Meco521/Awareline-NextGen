/*     */ package awareline.main.mod.implement.visual.ctype;
/*     */ 
/*     */ import awareline.main.event.EventHandler;
/*     */ import awareline.main.event.events.world.RLEEvent;
/*     */ import awareline.main.event.events.world.renderEvents.RenderArmEvent;
/*     */ import awareline.main.mod.Module;
/*     */ import awareline.main.mod.ModuleType;
/*     */ import awareline.main.mod.implement.visual.sucks.WingRenderer.ColorUtils;
/*     */ import awareline.main.mod.values.Mode;
/*     */ import awareline.main.mod.values.Numbers;
/*     */ import awareline.main.mod.values.Option;
/*     */ import awareline.main.mod.values.Value;
/*     */ import awareline.main.utility.timer.TimerUtil;
/*     */ import java.awt.Color;
/*     */ import net.minecraft.client.renderer.GlStateManager;
/*     */ import net.minecraft.client.renderer.entity.Render;
/*     */ import net.minecraft.client.renderer.entity.RendererLivingEntity;
/*     */ import net.minecraft.entity.Entity;
/*     */ import org.lwjgl.opengl.GL11;
/*     */ 
/*     */ public class Chams
/*     */   extends Module {
/*  23 */   private final String[] modes = new String[] { "Custom", "Rainbow", "Health", "Team" };
/*  24 */   private final TimerUtil pulseTimer = new TimerUtil();
/*     */   private boolean pulsing;
/*     */   private float nope;
/*  27 */   private final Option<Boolean> coloredValue = new Option("Colored", Boolean.valueOf(false));
/*  28 */   private final Option<Boolean> pulseValue = new Option("Pulse", Boolean.valueOf(false));
/*  29 */   private final Option<Boolean> handValue = new Option("Hand", Boolean.valueOf(true));
/*  30 */   private final Option<Boolean> fillValue = new Option("Fill", Boolean.valueOf(true));
/*  31 */   private final Numbers<Number> redValue = new Numbers("Red", Double.valueOf(255.0D), Double.valueOf(0.0D), Double.valueOf(255.0D), Double.valueOf(5.0D));
/*  32 */   private final Numbers<Number> greenValue = new Numbers("Green", Double.valueOf(255.0D), Double.valueOf(0.0D), Double.valueOf(255.0D), Double.valueOf(5.0D));
/*  33 */   private final Numbers<Number> blueValue = new Numbers("Blue", Double.valueOf(255.0D), Double.valueOf(0.0D), Double.valueOf(255.0D), Double.valueOf(5.0D));
/*  34 */   private final Numbers<Number> alphaValue = new Numbers("Alpha", Double.valueOf(35.0D), Double.valueOf(0.0D), Double.valueOf(255.0D), Double.valueOf(5.0D));
/*  35 */   private final Mode<String> colorModeValue = new Mode("ColorMode", this.modes, this.modes[0]);
/*     */   
/*     */   public Chams() {
/*  38 */     super("Chams", new String[] { "Chams" }, ModuleType.Render);
/*  39 */     addSettings(new Value[] { (Value)this.colorModeValue, (Value)this.coloredValue, (Value)this.pulseValue, (Value)this.handValue, (Value)this.fillValue, (Value)this.redValue, (Value)this.greenValue, (Value)this.blueValue, (Value)this.alphaValue });
/*     */   }
/*     */ 
/*     */   
/*     */   public void onEnable() {
/*  44 */     super.onEnable();
/*  45 */     this.pulseTimer.reset();
/*  46 */     this.pulsing = false;
/*  47 */     this.nope = 0.0F;
/*     */   }
/*     */   
/*     */   @EventHandler(3)
/*     */   public void onRenderModel(RenderArmEvent event) {
/*  52 */     if (!((Boolean)this.handValue.get()).booleanValue()) {
/*     */       return;
/*     */     }
/*     */     try {
/*  56 */       if (event.getEntity() == mc.thePlayer && mc.gameSettings.thirdPersonView == 0) {
/*  57 */         if (event.isPre()) {
/*  58 */           Color rain, blend; String text; GlStateManager.enableBlend();
/*  59 */           GlStateManager.disableDepth();
/*  60 */           GlStateManager.disableLighting();
/*  61 */           GlStateManager.disableTexture2D();
/*  62 */           mc.entityRenderer.disableLightmap();
/*  63 */           Color color = getColor2();
/*  64 */           switch ((String)this.colorModeValue.getValue()) {
/*     */             case "Rainbow":
/*  66 */               rain = ColorUtils.getRainbow(10.0F, 1.0F, 1.0F);
/*  67 */               color = new Color(rain.getRed(), rain.getGreen(), rain.getBlue(), ((Number)this.alphaValue.getValue()).intValue());
/*     */               break;
/*     */             
/*     */             case "Health":
/*  71 */               blend = ColorUtils.getBlendColor(mc.thePlayer.getHealth(), mc.thePlayer.getMaxHealth());
/*  72 */               color = new Color(blend.getRed(), blend.getGreen(), blend.getBlue(), ((Number)this.alphaValue.getValue()).intValue());
/*     */               break;
/*     */             
/*     */             case "Team":
/*  76 */               text = mc.thePlayer.getDisplayName().getFormattedText();
/*  77 */               if (Character.toLowerCase(text.charAt(0)) == '§') {
/*  78 */                 char oneMore = Character.toLowerCase(text.charAt(1));
/*  79 */                 int colorCode = "0123456789abcdefklmnorg".indexOf(oneMore);
/*  80 */                 if (colorCode >= 16)
/*     */                   break;  try {
/*  82 */                   int newColor = mc.fontRendererObj.colorCode[colorCode];
/*  83 */                   color = new Color(newColor >> 16, newColor >> 8 & 0xFF, newColor & 0xFF, ((Number)this.alphaValue.getValue()).intValue());
/*  84 */                 } catch (ArrayIndexOutOfBoundsException arrayIndexOutOfBoundsException) {}
/*     */                 
/*     */                 break;
/*     */               } 
/*  88 */               color = new Color(255, 255, 255, ((Number)this.alphaValue.getValue()).intValue());
/*     */               break;
/*     */           } 
/*     */           
/*  92 */           GlStateManager.color(0.003921569F * color.getRed(), 0.003921569F * color.getGreen(), 0.003921569F * color.getBlue(), 0.003921569F * color.getAlpha());
/*     */         } 
/*  94 */         if (event.isPost()) {
/*  95 */           GlStateManager.resetColor();
/*  96 */           mc.entityRenderer.enableLightmap();
/*  97 */           GlStateManager.enableLighting();
/*  98 */           GlStateManager.enableDepth();
/*  99 */           GlStateManager.disableBlend();
/* 100 */           GlStateManager.enableTexture2D();
/*     */         } 
/*     */       } 
/* 103 */     } catch (Exception e) {
/* 104 */       e.printStackTrace();
/*     */     } 
/*     */   }
/*     */   
/*     */   @EventHandler(3)
/*     */   private void onRLE(RLEEvent e) {
/* 110 */     if (e.getEntity() instanceof net.minecraft.entity.player.EntityPlayer && e.isPre()) {
/* 111 */       if (((Boolean)this.coloredValue.getValue()).booleanValue()) {
/* 112 */         e.setCancelled(true);
/*     */         try {
/* 114 */           Render render = mc.getRenderManager().getEntityRenderObject((Entity)e.getEntity());
/* 115 */           if (render != null && (mc.getRenderManager()).renderEngine != null && render instanceof RendererLivingEntity) {
/*     */             Color rain, blend; String text;
/* 117 */             RendererLivingEntity rendererLivingEntity = (RendererLivingEntity)render;
/* 118 */             GlStateManager.pushMatrix();
/* 119 */             GlStateManager.disableTexture2D();
/* 120 */             GlStateManager.disableAlpha();
/* 121 */             mc.entityRenderer.disableLightmap();
/* 122 */             GlStateManager.enableBlend();
/* 123 */             if (((Boolean)this.fillValue.getValue()).booleanValue()) {
/* 124 */               GlStateManager.disableLighting();
/*     */             }
/* 126 */             Color color = getColor2();
/* 127 */             switch ((String)this.colorModeValue.getValue()) {
/*     */               case "Rainbow":
/* 129 */                 rain = ColorUtils.getRainbow(10.0F, 1.0F, 1.0F);
/* 130 */                 color = new Color(rain.getRed(), rain.getGreen(), rain.getBlue(), ((Number)this.alphaValue.getValue()).intValue());
/*     */                 break;
/*     */               
/*     */               case "Health":
/* 134 */                 blend = ColorUtils.getBlendColor(e.getEntity().getHealth(), e.getEntity().getMaxHealth());
/* 135 */                 color = new Color(blend.getRed(), blend.getGreen(), blend.getBlue(), ((Number)this.alphaValue.getValue()).intValue());
/*     */                 break;
/*     */               
/*     */               case "Team":
/* 139 */                 text = e.getEntity().getDisplayName().getFormattedText();
/* 140 */                 if (Character.toLowerCase(text.charAt(0)) == '§') {
/* 141 */                   char oneMore = Character.toLowerCase(text.charAt(1));
/* 142 */                   int colorCode = "0123456789abcdefklmnorg".indexOf(oneMore);
/* 143 */                   if (colorCode >= 16)
/*     */                     break;  try {
/* 145 */                     int newColor = mc.fontRendererObj.colorCode[colorCode];
/* 146 */                     color = new Color(newColor >> 16, newColor >> 8 & 0xFF, newColor & 0xFF, ((Number)this.alphaValue.getValue()).intValue());
/* 147 */                   } catch (ArrayIndexOutOfBoundsException e2) {
/* 148 */                     e2.printStackTrace();
/*     */                   } 
/*     */                   break;
/*     */                 } 
/* 152 */                 color = new Color(255, 255, 255, ((Number)this.alphaValue.getValue()).intValue());
/*     */                 break;
/*     */             } 
/*     */             
/* 156 */             if (((Boolean)this.pulseValue.getValue()).booleanValue() && this.pulseTimer.hasReached(15.0D)) {
/* 157 */               if (this.pulsing && this.nope >= 0.5F) {
/* 158 */                 this.pulsing = false;
/*     */               }
/* 160 */               if (!this.pulsing && this.nope <= 0.0F) {
/* 161 */                 this.pulsing = true;
/*     */               }
/* 163 */               this.nope = this.pulsing ? (this.nope + 0.02F) : (this.nope - 0.015F);
/* 164 */               if (this.nope > 1.0F) {
/* 165 */                 this.nope = 1.0F;
/* 166 */               } else if (this.nope < 0.0F) {
/* 167 */                 this.nope = 0.0F;
/*     */               } 
/* 169 */               this.pulseTimer.reset();
/*     */             } 
/* 171 */             GlStateManager.color(0.003921569F * color.getRed(), 0.003921569F * color.getGreen(), 0.003921569F * color.getBlue(), ((Boolean)this.pulseValue.getValue()).booleanValue() ? this.nope : (0.003921569F * color.getAlpha()));
/* 172 */             GlStateManager.disableDepth();
/* 173 */             rendererLivingEntity.renderModel(e.getEntity(), e.getLimbSwing(), e.getLimbSwingAmount(), e.getAgeInTicks(), e.getRotationYawHead(), e.getRotationPitch(), e.getOffset());
/* 174 */             GlStateManager.enableDepth();
/* 175 */             rendererLivingEntity.renderModel(e.getEntity(), e.getLimbSwing(), e.getLimbSwingAmount(), e.getAgeInTicks(), e.getRotationYawHead(), e.getRotationPitch(), e.getOffset());
/* 176 */             GlStateManager.resetColor();
/* 177 */             if (((Boolean)this.fillValue.getValue()).booleanValue()) {
/* 178 */               GlStateManager.enableLighting();
/*     */             }
/* 180 */             GlStateManager.disableBlend();
/* 181 */             mc.entityRenderer.enableLightmap();
/* 182 */             GlStateManager.enableAlpha();
/* 183 */             GlStateManager.enableTexture2D();
/* 184 */             GlStateManager.popMatrix();
/* 185 */             rendererLivingEntity.renderLayers(e.getEntity(), e.getLimbSwing(), e.getLimbSwingAmount(), e.getTick(), e.getAgeInTicks(), e.getRotationYawHead(), e.getRotationPitch(), e.getOffset());
/* 186 */             GlStateManager.popMatrix();
/*     */           } 
/* 188 */         } catch (Exception e2) {
/* 189 */           e2.printStackTrace();
/*     */         } 
/*     */       } else {
/* 192 */         GL11.glEnable(32823);
/* 193 */         GL11.glPolygonOffset(1.0F, -1100000.0F);
/*     */       } 
/* 195 */     } else if (!((Boolean)this.coloredValue.getValue()).booleanValue() && e.getEntity() instanceof net.minecraft.entity.player.EntityPlayer && e.isPost()) {
/* 196 */       GL11.glDisable(32823);
/* 197 */       GL11.glPolygonOffset(1.0F, 1100000.0F);
/*     */     } 
/*     */   }
/*     */   
/*     */   public Color getColor2() {
/* 202 */     return new Color(((Number)this.redValue.getValue()).intValue(), ((Number)this.greenValue.getValue()).intValue(), ((Number)this.blueValue.getValue()).intValue(), ((Number)this.alphaValue.getValue()).intValue());
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\awareline\main\mod\implement\visual\ctype\Chams.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */