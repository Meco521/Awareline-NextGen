/*     */ package awareline.main.ui.gui.hud.notification;
/*     */ 
/*     */ import awareline.main.Client;
/*     */ import awareline.main.mod.implement.move.antifallutily.TimeHelper;
/*     */ import awareline.main.ui.animations.AnimationUtil;
/*     */ import awareline.main.ui.gui.clickgui.RenderUtil;
/*     */ import awareline.main.ui.simplecore.SimpleRender;
/*     */ import java.awt.Color;
/*     */ import java.util.ArrayList;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.gui.ScaledResolution;
/*     */ 
/*     */ public class ModNotification
/*     */ {
/*  15 */   public static final ArrayList<ModNotification> notifications = new ArrayList<>();
/*     */   public static float height;
/*     */   public static float startY;
/*     */   public static float modNotHeight;
/*  19 */   final ScaledResolution scaledresolution = new ScaledResolution(Minecraft.getMinecraft());
/*  20 */   final float maxX = 50.0F;
/*     */   private final String message;
/*     */   private final TimeHelper timer;
/*     */   private final float width;
/*     */   private final long stayTime;
/*     */   private float posY;
/*     */   private float animationX;
/*     */   
/*     */   public ModNotification(String message, long delay) {
/*  29 */     this.message = message;
/*  30 */     (this.timer = new TimeHelper()).reset();
/*  31 */     this.width = 190.0F;
/*  32 */     height = 35.0F;
/*  33 */     this.animationX = this.width;
/*  34 */     this.stayTime = delay;
/*  35 */     this.posY = 0.0F;
/*     */   }
/*     */   
/*     */   public static void sendClientMessage(String message, long delay) {
/*  39 */     notifications.add(new ModNotification(message, delay));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static void drawNotifications(ScaledResolution scaledresolution, boolean onBloom) {
/*  45 */     startY = scaledresolution.getScaledHeight() - 80.0F;
/*     */ 
/*     */ 
/*     */     
/*  49 */     for (int i = 0; i < notifications.size(); i++) {
/*  50 */       ModNotification not = notifications.get(i);
/*     */       
/*  52 */       if (not.shouldDelete()) {
/*  53 */         notifications.remove(i);
/*     */       }
/*  55 */       not.draw(scaledresolution, startY, onBloom);
/*     */       
/*  57 */       modNotHeight = height;
/*  58 */       startY -= height - 8.0F;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public static void clear() {
/*  64 */     notifications.clear();
/*     */   }
/*     */ 
/*     */   
/*     */   public void draw(ScaledResolution scaledresolution, float getY, boolean onBloom) {
/*  69 */     if (!onBloom) {
/*  70 */       float target = isFinished() ? this.width : 0.0F;
/*     */       
/*  72 */       this.animationX = AnimationUtil.getAnimationState(this.animationX, target, AnimationUtil.moveUD(isFinished() ? 400.0F : 10.0F, 
/*  73 */             Math.abs(this.animationX - target) * 20.0F, 
/*  74 */             SimpleRender.processFPS(0.045F), SimpleRender.processFPS(0.04F)));
/*     */       
/*  76 */       this.posY = (this.posY == 0.0F) ? getY : AnimationUtil.moveUD(this.posY, getY);
/*     */     } 
/*     */     
/*  79 */     float startX = scaledresolution.getScaledWidth() - this.width + this.animationX;
/*  80 */     float outX = scaledresolution.getScaledWidth() + this.animationX;
/*  81 */     float topY = this.posY + 20.0F;
/*  82 */     float downY = topY + height - 5.0F;
/*  83 */     float globalY = -5.0F;
/*     */     
/*  85 */     RenderUtil.drawRectForFloat(startX - 1.0F, topY - globalY, outX + 10.0F, downY + 2.0F, onBloom ? Client.instance
/*  86 */         .getClientColorAlpha(255) : (new Color(0, 0, 0, 180)).getRGB());
/*     */     
/*  88 */     RenderUtil.drawRectForFloat(startX - 1.0F, topY - globalY, outX - 136.0F - 50.0F, downY + 2.0F, Client.instance
/*  89 */         .getClientColorAlpha(255));
/*     */     
/*  91 */     if (!onBloom) {
/*  92 */       Client.instance.FontLoaders.regular19.drawString("Toggled", (startX + 9.0F), topY + height / 2.5D - 6.0D, -1);
/*     */       
/*  94 */       Client.instance.FontLoaders.regular17.drawString(this.message, (startX + 9.0F), topY + height / 2.5D + 8.0D, 10066329);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean shouldDelete() {
/* 100 */     return (isFinished() && this.animationX >= this.width);
/*     */   }
/*     */   
/*     */   private boolean isFinished() {
/* 104 */     return this.timer.isDelayComplete(this.stayTime);
/*     */   }
/*     */   
/*     */   public float getHeight() {
/* 108 */     return height;
/*     */   }
/*     */   
/*     */   public enum Type {
/* 112 */     Enabled,
/* 113 */     Disabled;
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\awareline\mai\\ui\gui\hud\notification\ModNotification.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */