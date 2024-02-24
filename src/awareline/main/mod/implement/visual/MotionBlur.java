/*     */ package awareline.main.mod.implement.visual;
/*     */ 
/*     */ import awareline.antileak.VerifyData;
/*     */ import awareline.main.event.EventHandler;
/*     */ import awareline.main.event.events.display.DisplayFrameEvent;
/*     */ import awareline.main.event.events.world.EventTick;
/*     */ import awareline.main.mod.Module;
/*     */ import awareline.main.mod.ModuleType;
/*     */ import awareline.main.mod.values.Mode;
/*     */ import awareline.main.mod.values.Numbers;
/*     */ import awareline.main.mod.values.Value;
/*     */ import awareline.main.utility.chat.Helper;
/*     */ import awareline.main.utility.shader.motionblur.MotionBlurResourceManager;
/*     */ import java.util.Map;
/*     */ import net.minecraft.client.resources.SimpleReloadableResourceManager;
/*     */ import net.minecraft.util.ResourceLocation;
/*     */ import org.lwjgl.opengl.GL11;
/*     */ 
/*     */ public class MotionBlur
/*     */   extends Module {
/*  21 */   private final Mode<String> type = new Mode("Mode", new String[] { "GL", "Shader" }, "GL");
/*  22 */   public final Numbers<Double> multiplier = new Numbers("FrameMultiplier", Double.valueOf(0.5D), Double.valueOf(0.05D), Double.valueOf(0.99D), Double.valueOf(0.01D));
/*     */   
/*     */   double lastvalue;
/*     */   
/*     */   public MotionBlur() {
/*  27 */     super("MotionBlur", ModuleType.Render);
/*  28 */     addSettings(new Value[] { (Value)this.type, (Value)this.multiplier });
/*  29 */     getInstance = this;
/*     */   }
/*     */   public static MotionBlur getInstance; private Map domainResourceManagers;
/*     */   @EventHandler
/*     */   public void onClientTick(DisplayFrameEvent event) {
/*  34 */     if (this.type.is("GL")) {
/*  35 */       float n = ((Double)this.multiplier.getValue()).floatValue();
/*  36 */       GL11.glAccum(259, n);
/*  37 */       GL11.glAccum(256, 1.0F - n);
/*  38 */       GL11.glAccum(258, 1.0F);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void onDisable() {
/*  44 */     if (this.type.is("Shader")) {
/*  45 */       mc.entityRenderer.stopUseShader();
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void onEnable() {
/*  51 */     if (this.type.is("Shader")) {
/*  52 */       if (this.domainResourceManagers == null) {
/*  53 */         this.domainResourceManagers = ((SimpleReloadableResourceManager)mc.getResourceManager()).domainResourceManagers;
/*     */       }
/*     */       
/*  56 */       if (!this.domainResourceManagers.containsKey("motionblur")) {
/*  57 */         this.domainResourceManagers.put("motionblur", new MotionBlurResourceManager());
/*     */       }
/*     */       
/*  60 */       if (isFastRenderEnabled()) {
/*  61 */         Helper.sendMessage("Please don't open <MotionBlur> with <FastRender>");
/*  62 */         setEnabled(false);
/*     */         return;
/*     */       } 
/*  65 */       this.lastvalue = ((Double)this.multiplier.getValue()).intValue();
/*  66 */       applyShader();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isFastRenderEnabled() {
/*  74 */     return mc.gameSettings.ofFastRender;
/*     */   }
/*     */   
/*     */   public void applyShader() {
/*  78 */     if (this.type.is("Shader")) {
/*  79 */       mc.entityRenderer.loadShader(new ResourceLocation("motionblur", "motionblur"));
/*     */     }
/*     */   }
/*     */   
/*     */   @EventHandler
/*     */   public void onClientTick(EventTick event) {
/*  85 */     VerifyData.instance.getClass();
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  90 */     if (this.type.is("Shader") && 
/*  91 */       mc.thePlayer != null && mc.theWorld != null) {
/*  92 */       if ((!mc.entityRenderer.isShaderActive() || this.lastvalue != ((Double)this.multiplier
/*  93 */         .getValue()).doubleValue()) && mc.theWorld != null && 
/*  94 */         !isFastRenderEnabled()) {
/*  95 */         this.lastvalue = ((Double)this.multiplier.getValue()).doubleValue();
/*  96 */         applyShader();
/*     */       } 
/*  98 */       if (this.domainResourceManagers == null) {
/*  99 */         this
/* 100 */           .domainResourceManagers = ((SimpleReloadableResourceManager)mc.getResourceManager()).domainResourceManagers;
/*     */       }
/*     */       
/* 103 */       if (!this.domainResourceManagers.containsKey("motionblur")) {
/* 104 */         this.domainResourceManagers.put("motionblur", new MotionBlurResourceManager());
/*     */       }
/* 106 */       if (isFastRenderEnabled()) {
/* 107 */         Helper.sendMessage("Please don't open <MotionBlur> with <FastRender>");
/* 108 */         setEnabled(false);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\awareline\main\mod\implement\visual\MotionBlur.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */