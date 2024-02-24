/*     */ package awareline.main.ui.gui.hud.notification;
/*     */ import awareline.main.Client;
/*     */ import awareline.main.mod.implement.move.antifallutily.TimeHelper;
/*     */ import awareline.main.ui.animations.AnimationUtil;
/*     */ import awareline.main.ui.gui.clickgui.RenderUtil;
/*     */ import awareline.main.ui.simplecore.SimpleRender;
/*     */ import java.awt.Color;
/*     */ import java.util.ArrayList;
/*     */ import net.minecraft.client.gui.ScaledResolution;
/*     */ 
/*     */ public class ClientNotification {
/*     */   private final String message;
/*     */   private final String message2;
/*     */   private final TimeHelper timer;
/*     */   private float posY;
/*     */   private final float width;
/*     */   private final float height;
/*     */   private float animationX;
/*     */   private String s;
/*     */   private final long stayTime;
/*     */   
/*     */   public float getHeight() {
/*  23 */     return this.height;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*  28 */   final CFontRenderer font = Client.instance.FontLoaders.Comfortaa16;
/*  29 */   private static final ArrayList<ClientNotification> notifications = new ArrayList<>();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static float startY;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private final long ms;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void sendClientMessage(String message2, String message, long delay, Type type) {
/*  53 */     notifications.add(new ClientNotification(message2, message, delay, type));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void drawNotifications(ScaledResolution scaledresolution, boolean onBloom) {
/*  60 */     boolean check = !((Boolean)HUD.moduleNotification.get()).booleanValue();
/*  61 */     if (check) {
/*  62 */       startY = scaledresolution.getScaledHeight() - 80.0F;
/*  63 */       for (int i = 0; i < notifications.size(); i++) {
/*  64 */         ClientNotification not = notifications.get(i);
/*     */         
/*  66 */         if (not.shouldDelete()) {
/*  67 */           notifications.remove(i);
/*     */         }
/*  69 */         not.draw(scaledresolution, startY, onBloom);
/*  70 */         ModNotification.startY -= ModNotification.height + 1.0F;
/*  71 */         startY -= not.height;
/*     */       } 
/*     */     } else {
/*  74 */       for (int i = 0; i < notifications.size(); i++) {
/*  75 */         ClientNotification not = notifications.get(i);
/*     */         
/*  77 */         if (not.shouldDelete()) {
/*  78 */           notifications.remove(i);
/*     */         }
/*  80 */         not.draw(scaledresolution, ModNotification.startY - 3.0F, onBloom);
/*     */         
/*  82 */         ModNotification.startY -= ModNotification.height + 1.0F;
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void draw(ScaledResolution sr, float getY, boolean onBloom) {
/*  89 */     if (!onBloom) {
/*  90 */       this.animationX = AnimationUtil.getAnimationState(this.animationX, isFinished() ? this.width : 0.0F, 
/*  91 */           AnimationUtil.moveUD(isFinished() ? 400.0F : 10.0F, Math.abs(this.animationX - (isFinished() ? this.width : 0.0F)) * 20.0F, 
/*  92 */             SimpleRender.processFPS(0.045F), SimpleRender.processFPS(0.04F)));
/*     */       
/*  94 */       this.posY = (this.posY == 0.0F) ? getY : AnimationUtil.moveUD(this.posY, getY);
/*     */     } 
/*     */     
/*  97 */     float startX = sr.getScaledWidth() - this.width + this.animationX;
/*  98 */     float outX = sr.getScaledWidth() + this.animationX;
/*  99 */     float topY = this.posY + 20.0F;
/* 100 */     float downY = topY + this.height - 5.0F;
/*     */ 
/*     */     
/* 103 */     RenderUtil.drawRectForFloat(startX, topY + 2.5F, outX, downY + 1.0F, onBloom ? Color.BLACK.getRGB() : (new Color(0, 0, 0, 180)).getRGB());
/* 104 */     if ((float)(this.stayTime - getElapsedTime()) / (float)this.stayTime > 0.0F) {
/* 105 */       switch (this.s) {
/*     */         case "WARNING":
/* 107 */           RenderUtil.drawGradientSidewaysForFloat(startX, downY, startX + this.width * (float)(this.stayTime - getElapsedTime()) / (float)this.stayTime, downY + 1.0F, (new Color(255, 210, 70))
/* 108 */               .getRGB(), (new Color(255, 210, 70, 180)).getRGB());
/*     */           break;
/*     */         case "INFO":
/* 111 */           RenderUtil.drawGradientSidewaysForFloat(startX, downY, startX + this.width * (float)(this.stayTime - getElapsedTime()) / (float)this.stayTime, downY + 1.0F, (new Color(255, 255, 255))
/* 112 */               .getRGB(), (new Color(255, 255, 255, 180)).getRGB());
/*     */           break;
/*     */         case "SUCCESS":
/* 115 */           RenderUtil.drawGradientSidewaysForFloat(startX, downY, startX + this.width * (float)(this.stayTime - getElapsedTime()) / (float)this.stayTime, downY + 1.0F, (new Color(0, 200, 0))
/* 116 */               .getRGB(), (new Color(0, 200, 0, 180)).getRGB());
/*     */           break;
/*     */       } 
/*     */     }
/* 120 */     switch (this.s) {
/*     */       case "INFO":
/* 122 */         Client.instance.FontLoaders.Noti45.drawString("B", (startX + 8.0F), topY + this.height / 2.5D - 10.0D + 6.0D, -1);
/*     */         break;
/*     */       case "SUCCESS":
/* 125 */         Client.instance.FontLoaders.novoicons45.drawString("H", startX + 3.0F, topY + this.height / 2.5F - 10.0F + 6.0F - 1.0F, (new Color(0, 200, 0)).getRGB());
/*     */         break;
/*     */       case "ERROR":
/* 128 */         RenderUtil.drawImage(new ResourceLocation("client/notifications/error.png"), startX + 3.0F, topY + this.height / 2.5F - 10.0F + 6.0F - 3.0F, 20.0F, 20.0F);
/*     */         break;
/*     */       
/*     */       case "WARNING":
/* 132 */         Client.instance.FontLoaders.novoicons45.drawString("J", (startX + 3.0F), topY + this.height / 2.5D - 10.0D + 6.0D, (new Color(255, 210, 70)).getRGB());
/*     */         break;
/*     */     } 
/* 135 */     if (!onBloom) {
/* 136 */       Client.instance.FontLoaders.thebold20.drawString(this.message2, startX + 27.0F, topY + this.height / 2.5F - 5.0F, -1);
/* 137 */       Client.instance.FontLoaders.regular16.drawString(this.message, startX + 27.0F, topY + this.height / 2.5F + 6.5F, -1);
/*     */     } 
/*     */   }
/*     */   
/*     */   public boolean shouldDelete() {
/* 142 */     return (isFinished() && this.animationX >= this.width);
/*     */   }
/*     */   
/*     */   private boolean isFinished() {
/* 146 */     return this.timer.isDelayComplete(this.stayTime);
/*     */   }
/*     */   
/*     */   public enum Type {
/* 150 */     SUCCESS,
/* 151 */     ERROR,
/* 152 */     WARNING,
/* 153 */     INFO;
/*     */   }
/*     */   
/* 156 */   public ClientNotification(String message2, String message, long delay, Type type) { this.ms = getCurrentMS(); this.message = message; this.message2 = message2; (this.timer = new TimeHelper()).reset(); this.width = this.font.getStringWidth(message + message2) + 90.0F; this.height = 35.0F; this.animationX = this.width; this.stayTime = delay; this.posY = 0.0F; if (type == Type.SUCCESS) { this.s = "SUCCESS"; } else if (type == Type.ERROR) { this.s = "ERROR"; }
/*     */     else if (type == Type.WARNING) { this.s = "WARNING"; }
/*     */     else if (type == Type.INFO) { this.s = "INFO"; }
/* 159 */      } private long getCurrentMS() { return System.currentTimeMillis(); }
/*     */ 
/*     */   
/*     */   public final long getElapsedTime() {
/* 163 */     return getCurrentMS() - this.ms;
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\awareline\mai\\ui\gui\hud\notification\ClientNotification.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */