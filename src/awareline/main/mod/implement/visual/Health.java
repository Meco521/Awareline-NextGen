/*     */ package awareline.main.mod.implement.visual;
/*     */ 
/*     */ import awareline.main.Client;
/*     */ import awareline.main.event.EventHandler;
/*     */ import awareline.main.event.events.ketaShaderCall.EventShader;
/*     */ import awareline.main.event.events.world.renderEvents.EventRender2D;
/*     */ import awareline.main.mod.Module;
/*     */ import awareline.main.mod.ModuleType;
/*     */ import awareline.main.mod.implement.globals.Shader;
/*     */ import awareline.main.mod.values.Mode;
/*     */ import awareline.main.mod.values.Option;
/*     */ import awareline.main.mod.values.Value;
/*     */ import awareline.main.ui.animations.AnimationUtil;
/*     */ import awareline.main.ui.gui.clickgui.RenderUtil;
/*     */ import awareline.main.utility.render.RoundedUtil;
/*     */ import java.awt.Color;
/*     */ import net.minecraft.client.gui.ScaledResolution;
/*     */ import net.minecraft.client.renderer.GlStateManager;
/*     */ import net.minecraft.client.renderer.OpenGlHelper;
/*     */ import net.minecraft.client.renderer.RenderHelper;
/*     */ import net.minecraft.util.MathHelper;
/*     */ import net.minecraft.util.ResourceLocation;
/*     */ import org.lwjgl.opengl.GL11;
/*     */ 
/*     */ public class Health extends Module {
/*     */   private float healthBarWidth;
/*     */   private float width;
/*  28 */   public final Option<Boolean> blur = new Option("Blur", Boolean.valueOf(true)); public final Option<Boolean> shadow = new Option("Shadow", Boolean.valueOf(true));
/*  29 */   private final Mode<String> modes = new Mode("Mode", new String[] { "Rect", "Rect2", "Minecraft", "Text" }, "Rect");
/*     */   public static Health getInstance;
/*     */   
/*     */   public Health() {
/*  33 */     super("Health", ModuleType.Render);
/*  34 */     addSettings(new Value[] { (Value)this.modes, (Value)this.blur, (Value)this.shadow });
/*  35 */     getInstance = this;
/*     */   }
/*     */   
/*     */   float x;
/*     */   float y;
/*     */   
/*     */   public void onDisable() {
/*  42 */     this.x = this.y = 0.0F;
/*     */   }
/*     */ 
/*     */   
/*     */   @EventHandler
/*     */   public void onRender(EventRender2D event) {
/*  48 */     if (mc.gameSettings.showDebugInfo) {
/*     */       return;
/*     */     }
/*  51 */     if (this.modes.is("Text")) {
/*  52 */       if (mc.thePlayer.getHealth() >= 0.0F && mc.thePlayer.getHealth() < 10.0F) {
/*  53 */         this.width = 3.0F;
/*     */       }
/*  55 */       if (mc.thePlayer.getHealth() >= 10.0F && mc.thePlayer.getHealth() < 100.0F) {
/*  56 */         this.width = 6.0F;
/*     */       }
/*  58 */       Client.instance.FontLoaders.bold18.drawStringWithShadow(String.valueOf(MathHelper.ceiling_float_int(mc.thePlayer.getHealth())), ((event
/*  59 */           .getResolution().getScaledWidth() / 2) - this.width), (event.getResolution().getScaledHeight() / 2 - 13), (mc.thePlayer.getHealth() <= 10.0F) ? (new Color(255, 0, 0)).getRGB() : (new Color(0, 255, 0)).getRGB());
/*  60 */     } else if (this.modes.is("Minecraft")) {
/*  61 */       ScaledResolution scaledResolution = event.getResolution();
/*  62 */       GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
/*  63 */       RenderHelper.enableGUIStandardItemLighting();
/*  64 */       mc.getTextureManager().bindTexture(new ResourceLocation("textures/gui/icons.png"));
/*  65 */       GL11.glDisable(2929);
/*  66 */       GL11.glEnable(3042);
/*  67 */       GL11.glDepthMask(false);
/*  68 */       OpenGlHelper.glBlendFunc(770, 771, 1, 0);
/*  69 */       float maxHealth = mc.thePlayer.getMaxHealth();
/*  70 */       for (int n = 0; n < maxHealth / 2.0F; n++) {
/*  71 */         mc.ingameGUI.drawTexturedModalRect((scaledResolution.getScaledWidth() / 2) + 1.0F - maxHealth / 2.0F * 10.0F / 2.0F + (n * 10), (scaledResolution.getScaledHeight() / 2 - 20 + 30), 16, 0, 9, 9);
/*     */       }
/*  73 */       float health = mc.thePlayer.getHealth();
/*  74 */       for (int n2 = 0; n2 < health / 2.0F; n2++) {
/*  75 */         mc.ingameGUI.drawTexturedModalRect((scaledResolution.getScaledWidth() / 2) + 1.0F - maxHealth / 2.0F * 10.0F / 2.0F + (n2 * 10), (scaledResolution.getScaledHeight() / 2 - 20 + 30), 52, 0, 9, 9);
/*     */       }
/*  77 */       GL11.glDepthMask(true);
/*  78 */       GL11.glDisable(3042);
/*  79 */       GL11.glEnable(2929);
/*  80 */       GlStateManager.disableBlend();
/*  81 */       GlStateManager.color(1.0F, 1.0F, 1.0F);
/*  82 */       RenderHelper.disableStandardItemLighting();
/*     */     } 
/*  84 */     if (this.modes.is("Rect") || this.modes.is("Rect2")) {
/*     */ 
/*     */       
/*  87 */       awareline.main.ui.draghud.component.impl.Health dwm = (awareline.main.ui.draghud.component.impl.Health)Client.instance.draggable.getDraggableComponentByClass(awareline.main.ui.draghud.component.impl.Health.class);
/*  88 */       dwm.setWidth(153.0F);
/*  89 */       dwm.setHeight(42);
/*  90 */       this.x = AnimationUtil.moveUDFaster(this.x, dwm.getX());
/*  91 */       this.y = AnimationUtil.moveUDFaster(this.y, dwm.getY());
/*     */       
/*  93 */       float health = mc.thePlayer.getHealth();
/*  94 */       float hpPercentage = mc.thePlayer.isDead ? 0.0F : (health / mc.thePlayer.getMaxHealth());
/*  95 */       float hpWidth = 152.0F * hpPercentage;
/*  96 */       Color healthColor = new Color(((Double)HUD.r.getValue()).intValue(), ((Double)HUD.g.getValue()).intValue(), ((Double)HUD.b.getValue()).intValue());
/*  97 */       this.healthBarWidth = AnimationUtil.moveUDSmooth(this.healthBarWidth, hpWidth);
/*     */       
/*  99 */       if (this.modes.is("Rect2")) {
/* 100 */         RenderUtil.drawGradientSidewaysForFloat(this.x + 40.0F, this.y + 21.0F, this.x + 192.0F, this.y + 27.0F, (new Color(0, 0, 0)).getRGB(), (new Color(0, 0, 0)).getRGB());
/* 101 */       } else if (this.modes.is("Rect")) {
/* 102 */         RoundedUtil.drawRound(this.x + 40.0F, this.y + 21.0F, 152.0F, 5.0F, 3.0F, new Color(0, 0, 0));
/*     */       } 
/* 104 */       RenderUtil.drawRect(0.0F, 0.0F, 0.0F, 0.0F, 0);
/*     */       
/* 106 */       if (this.modes.is("Rect")) {
/* 107 */         RoundedUtil.drawRound(this.x + 40.0F, this.y + 21.0F, this.healthBarWidth, 5.0F, 3.0F, healthColor);
/*     */       }
/* 109 */       else if (this.modes.is("Rect2")) {
/* 110 */         RoundedUtil.drawRound(this.x + 40.0F, this.y + 21.0F + 4.0F, this.healthBarWidth, 1.0F, 3.0F, healthColor);
/*     */       } 
/*     */       
/* 113 */       float fontX = 3.0F;
/* 114 */       Client.instance.FontLoaders.regular13.drawString(String.format("%.1f", new Object[] { Float.valueOf(hpPercentage * 100.0F) }) + "%", this.x + 112.5F + 3.0F - (Client.instance.FontLoaders.regular13
/* 115 */           .getStringWidth(String.format("%.1f", new Object[] { Float.valueOf(hpPercentage * 100.0F) }) + "%") / 2), this.y + 22.0F, -1);
/*     */       
/* 117 */       Client.instance.FontLoaders.NovICON12.drawString("s", this.x + 96.0F + 3.0F - (Client.instance.FontLoaders.NovICON10.getStringWidth("s") / 2), this.y + 23.5F, -1);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @EventHandler
/*     */   public void onBloom(EventShader event) {
/* 126 */     if (mc == null || mc.gameSettings.showDebugInfo || mc.theWorld == null || mc.thePlayer == null) {
/*     */       return;
/*     */     }
/* 129 */     if (!Shader.getInstance.isEnabled()) {
/*     */       return;
/*     */     }
/*     */     
/* 133 */     if (event.onBloom() && !((Boolean)this.shadow.getValue()).booleanValue()) {
/*     */       return;
/*     */     }
/*     */     
/* 137 */     if (event.onBlur() && !((Boolean)this.blur.getValue()).booleanValue()) {
/*     */       return;
/*     */     }
/*     */     
/* 141 */     if (this.modes.is("Rect") || this.modes.is("Rect2")) {
/*     */ 
/*     */       
/* 144 */       awareline.main.ui.draghud.component.impl.Health dwm = (awareline.main.ui.draghud.component.impl.Health)Client.instance.draggable.getDraggableComponentByClass(awareline.main.ui.draghud.component.impl.Health.class);
/* 145 */       dwm.setWidth(153.0F);
/* 146 */       dwm.setHeight(42);
/* 147 */       this.x = AnimationUtil.moveUDFaster(this.x, dwm.getX());
/* 148 */       this.y = AnimationUtil.moveUDFaster(this.y, dwm.getY());
/*     */       
/* 150 */       float health = mc.thePlayer.getHealth();
/* 151 */       float hpPercentage = mc.thePlayer.isDead ? 0.0F : (health / mc.thePlayer.getMaxHealth());
/* 152 */       float hpWidth = 152.0F * hpPercentage;
/* 153 */       Color healthColor = new Color(((Double)HUD.r.getValue()).intValue(), ((Double)HUD.g.getValue()).intValue(), ((Double)HUD.b.getValue()).intValue());
/* 154 */       this.healthBarWidth = AnimationUtil.moveUDSmooth(this.healthBarWidth, hpWidth);
/*     */       
/* 156 */       if (this.modes.is("Rect2")) {
/* 157 */         RenderUtil.drawGradientSidewaysForFloat(this.x + 40.0F, this.y + 21.0F, this.x + 192.0F, this.y + 27.0F, Client.instance
/* 158 */             .getClientColorNoHash(255).getRGB(), Client.instance.getClientColorNoHash(255).getRGB());
/* 159 */       } else if (this.modes.is("Rect")) {
/* 160 */         RoundedUtil.drawRound(this.x + 40.0F, this.y + 21.0F, 152.0F, 5.0F, 3.0F, Client.instance.getClientColorNoHash(255));
/*     */       } 
/* 162 */       RenderUtil.drawRect(0.0F, 0.0F, 0.0F, 0.0F, 0);
/*     */       
/* 164 */       if (this.modes.is("Rect")) {
/* 165 */         RoundedUtil.drawRound(this.x + 40.0F, this.y + 21.0F, this.healthBarWidth, 5.0F, 3.0F, healthColor);
/* 166 */       } else if (this.modes.is("Rect2")) {
/* 167 */         RoundedUtil.drawRound(this.x + 40.0F, this.y + 21.0F + 4.0F, this.healthBarWidth, 1.0F, 3.0F, healthColor);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\awareline\main\mod\implement\visual\Health.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */