/*     */ package awareline.main.mod.implement.visual.ctype;
/*     */ 
/*     */ import awareline.main.event.EventHandler;
/*     */ import awareline.main.event.events.world.packetEvents.EventPacketSend;
/*     */ import awareline.main.event.events.world.renderEvents.EventRender2D;
/*     */ import awareline.main.event.events.world.updateEvents.EventPreUpdate;
/*     */ import awareline.main.mod.Module;
/*     */ import awareline.main.mod.ModuleType;
/*     */ import awareline.main.mod.implement.combat.KillAura;
/*     */ import awareline.main.mod.implement.visual.HUD;
/*     */ import awareline.main.mod.values.Numbers;
/*     */ import awareline.main.mod.values.Option;
/*     */ import awareline.main.mod.values.Value;
/*     */ import awareline.main.ui.animations.AnimationUtil;
/*     */ import awareline.main.ui.gui.clickgui.RenderUtil;
/*     */ import awareline.main.utility.render.color.Colors;
/*     */ import java.awt.Color;
/*     */ import net.minecraft.client.gui.ScaledResolution;
/*     */ import net.minecraft.entity.EntityLivingBase;
/*     */ import org.lwjgl.input.Mouse;
/*     */ 
/*     */ 
/*     */ 
/*     */ public class Crosshair
/*     */   extends Module
/*     */ {
/*     */   private final Option<Boolean> dynamic;
/*     */   public final Numbers<Double> gap;
/*     */   private final Numbers<Double> width;
/*     */   public final Numbers<Double> size;
/*     */   public final Numbers<Double> dynamicSize;
/*     */   
/*     */   public Crosshair() {
/*  34 */     super("Crosshair", ModuleType.Render);
/*  35 */     this.dynamic = new Option("Dynamic", Boolean.valueOf(true));
/*  36 */     this.dynamicSize = new Numbers("DynamicSize", Double.valueOf(2.0D), Double.valueOf(1.0D), Double.valueOf(20.0D), Double.valueOf(1.0D), this.dynamic::get);
/*  37 */     this.mouseButton = new Option("MouseButtonAnim", Boolean.valueOf(true), this.dynamic::get);
/*  38 */     this.width = new Numbers("Width", Double.valueOf(2.0D), Double.valueOf(0.25D), Double.valueOf(10.0D), Double.valueOf(0.25D));
/*  39 */     this.gap = new Numbers("Gap", Double.valueOf(5.0D), Double.valueOf(0.25D), Double.valueOf(15.0D), Double.valueOf(0.25D));
/*  40 */     this.size = new Numbers("Size", Double.valueOf(7.0D), Double.valueOf(0.25D), Double.valueOf(15.0D), Double.valueOf(0.25D));
/*  41 */     addSettings(new Value[] { (Value)this.dynamic, (Value)this.dynamicSize, (Value)this.mouseButton, (Value)this.gap, (Value)this.width, (Value)this.size });
/*  42 */     getInstance = this;
/*     */   }
/*     */   private final Option<Boolean> mouseButton; public static Crosshair getInstance; private EntityLivingBase target; private boolean hit;
/*     */   private float animCircle;
/*     */   
/*     */   private void getTarget() {
/*  48 */     if (KillAura.getInstance.getTarget() != null) {
/*  49 */       this.target = KillAura.getInstance.getTarget();
/*     */     } else {
/*  51 */       this.target = null;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @EventHandler
/*     */   private void onUpdate(EventPreUpdate e) {
/*  60 */     if (((Boolean)this.dynamic.get()).booleanValue() && ((Boolean)this.mouseButton.get()).booleanValue()) {
/*  61 */       getTarget();
/*     */     }
/*     */   }
/*     */   
/*     */   @EventHandler
/*     */   private void onPacket(EventPacketSend e) {
/*  67 */     if (((Boolean)this.dynamic.get()).booleanValue() && ((Boolean)this.mouseButton.get()).booleanValue()) {
/*  68 */       if (this.target == null) {
/*  69 */         this.hit = false;
/*     */       }
/*  71 */       if (e.getPacket() instanceof net.minecraft.network.play.client.C0APacketAnimation && this.target != null) {
/*  72 */         this.hit = !this.hit;
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   @EventHandler
/*     */   public void onGui(EventRender2D e) {
/*  79 */     int red = ((Double)HUD.r.getValue()).intValue();
/*  80 */     int green = ((Double)HUD.g.getValue()).intValue();
/*  81 */     int blue = ((Double)HUD.b.getValue()).intValue();
/*  82 */     int alph = 255;
/*  83 */     double gap = ((Double)this.gap.getValue()).doubleValue();
/*  84 */     double width = ((Double)this.width.getValue()).doubleValue();
/*  85 */     double size = ((Double)this.size.getValue()).doubleValue();
/*  86 */     if (((Boolean)this.dynamic.get()).booleanValue()) {
/*  87 */       this.animCircle = AnimationUtil.moveUDSmooth(this.animCircle, 
/*  88 */           (float)(!((Boolean)this.mouseButton.get()).booleanValue() ? (isMoving() ? ((Double)this.dynamicSize.get()).doubleValue() : 0.0D) : (((this.target != null) ? this.hit : Mouse.isButtonDown(0)) ? ((Double)this.dynamicSize.get()).doubleValue() : 0.0D)));
/*     */     } else {
/*  90 */       this.animCircle = 0.0F;
/*     */     } 
/*  92 */     ScaledResolution scaledRes = e.getResolution();
/*  93 */     RenderUtil.rectangleBordered((scaledRes.getScaledWidth() / 2) - width, (scaledRes.getScaledHeight() / 2) - gap - size - this.animCircle, ((scaledRes.getScaledWidth() / 2) + 1.0F) + width, (scaledRes.getScaledHeight() / 2) - gap - this.animCircle, 0.5D, Colors.getColor(red, green, blue, 255), (new Color(0, 0, 0, 255)).getRGB());
/*  94 */     RenderUtil.rectangleBordered((scaledRes.getScaledWidth() / 2) - width, (scaledRes.getScaledHeight() / 2) + gap + 1.0D + this.animCircle - 0.15D, ((scaledRes.getScaledWidth() / 2) + 1.0F) + width, (scaledRes.getScaledHeight() / 2 + 1) + gap + size + this.animCircle - 0.15D, 0.5D, Colors.getColor(red, green, blue, 255), (new Color(0, 0, 0, 255)).getRGB());
/*  95 */     RenderUtil.rectangleBordered((scaledRes.getScaledWidth() / 2) - gap - size - this.animCircle + 0.15D, (scaledRes.getScaledHeight() / 2) - width, (scaledRes.getScaledWidth() / 2) - gap - this.animCircle + 0.15D, ((scaledRes.getScaledHeight() / 2) + 1.0F) + width, 0.5D, Colors.getColor(red, green, blue, 255), (new Color(0, 0, 0, 255)).getRGB());
/*  96 */     RenderUtil.rectangleBordered((scaledRes.getScaledWidth() / 2 + 1) + gap + this.animCircle, (scaledRes.getScaledHeight() / 2) - width, (scaledRes.getScaledWidth() / 2) + size + gap + 1.0D + this.animCircle, ((scaledRes.getScaledHeight() / 2) + 1.0F) + width, 0.5D, Colors.getColor(red, green, blue, 255), (new Color(0, 0, 0, 255)).getRGB());
/*     */   }
/*     */   
/*     */   public boolean isMoving() {
/* 100 */     if (((Boolean)this.dynamic.getValue()).booleanValue() && 
/* 101 */       !mc.thePlayer.isCollidedHorizontally && 
/* 102 */       !mc.thePlayer.isSneaking()) {
/* 103 */       if (mc.thePlayer.movementInput.moveForward == 0.0F) {
/* 104 */         return (mc.thePlayer.movementInput.moveStrafe != 0.0F);
/*     */       }
/* 106 */       return true;
/*     */     } 
/*     */ 
/*     */     
/* 110 */     return false;
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\awareline\main\mod\implement\visual\ctype\Crosshair.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */