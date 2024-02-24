/*    */ package awareline.main.mod.implement.globals;
/*    */ import awareline.main.event.Event;
/*    */ import awareline.main.event.EventManager;
/*    */ import awareline.main.event.events.ketaShaderCall.EventShader;
/*    */ import awareline.main.mod.Module;
/*    */ import awareline.main.mod.ModuleType;
/*    */ import awareline.main.mod.values.Mode;
/*    */ import awareline.main.mod.values.Numbers;
/*    */ import awareline.main.mod.values.Option;
/*    */ import awareline.main.mod.values.Value;
/*    */ import awareline.main.ui.gui.clickgui.RenderUtil;
/*    */ import awareline.main.utility.render.render.blur.GaussianBlur;
/*    */ import awareline.main.utility.render.render.blur.KawaseBloom;
/*    */ import awareline.main.utility.render.render.blur.KawaseBlur;
/*    */ import awareline.main.utility.render.render.blur.StencilUtil;
/*    */ import net.minecraft.client.gui.ScaledResolution;
/*    */ import net.minecraft.client.shader.Framebuffer;
/*    */ 
/*    */ public class Shader extends Module {
/* 20 */   public final Option<Boolean> blur = new Option("Blur", Boolean.valueOf(true));
/* 21 */   public final Option<Boolean> shadow = new Option("Shadow", Boolean.valueOf(true));
/*    */   public static Shader getInstance;
/* 23 */   private final Mode<String> mode = new Mode("Mode", new String[] { "Kawase", "Gaussian" }, "Kawase", this.blur::getValue);
/* 24 */   private final Numbers<Double> giterations = new Numbers("GaussianBlurRadius", Double.valueOf(30.0D), Double.valueOf(1.0D), Double.valueOf(100.0D), Double.valueOf(1.0D), this.blur::getValue, () -> Boolean.valueOf(this.mode.is("Gaussian")));
/*    */   
/* 26 */   private final Numbers<Double> iterations = new Numbers("BlurRadius", Double.valueOf(2.0D), Double.valueOf(1.0D), Double.valueOf(8.0D), Double.valueOf(1.0D), this.blur::getValue, () -> Boolean.valueOf(this.mode.is("Kawase")));
/*    */   
/* 28 */   private final Numbers<Double> offset = new Numbers("BlurOffset", Double.valueOf(3.0D), Double.valueOf(1.0D), Double.valueOf(10.0D), Double.valueOf(1.0D), this.blur::getValue, () -> Boolean.valueOf(this.mode.is("Kawase")));
/*    */   
/* 30 */   private final Numbers<Double> shadowRadius = new Numbers("ShadowRadius", Double.valueOf(1.0D), Double.valueOf(1.0D), Double.valueOf(8.0D), Double.valueOf(1.0D), this.shadow::getValue);
/*    */   
/* 32 */   private final Numbers<Double> shadowOffset = new Numbers("ShadowOffset", Double.valueOf(2.0D), Double.valueOf(1.0D), Double.valueOf(10.0D), Double.valueOf(1.0D), this.shadow::getValue);
/*    */   
/* 34 */   private Framebuffer stencilFramebuffer = new Framebuffer(1, 1, false);
/*    */   
/*    */   public Shader() {
/* 37 */     super("Shader", ModuleType.Globals);
/* 38 */     addSettings(new Value[] { (Value)this.blur, (Value)this.mode, (Value)this.giterations, (Value)this.iterations, (Value)this.offset, (Value)this.shadow, (Value)this.shadowRadius, (Value)this.shadowOffset });
/*    */     
/* 40 */     getInstance = this;
/* 41 */     setHide(true);
/*    */   }
/*    */ 
/*    */   
/*    */   public void onEnable() {
/* 46 */     super.onEnable();
/*    */   }
/*    */   
/*    */   public void blurScreen(ScaledResolution scaledResolution) {
/* 50 */     if (!isEnabled()) {
/*    */       return;
/*    */     }
/* 53 */     if (!mc.gameSettings.ofFastRender) {
/* 54 */       if (((Boolean)this.shadow.getValue()).booleanValue()) {
/* 55 */         this.stencilFramebuffer = RenderUtil.createFrameBuffer(this.stencilFramebuffer);
/* 56 */         this.stencilFramebuffer.framebufferClear();
/* 57 */         this.stencilFramebuffer.bindFramebuffer(false);
/*    */         
/* 59 */         EventManager.call((Event)new EventShader(true, false, scaledResolution));
/*    */         
/* 61 */         this.stencilFramebuffer.unbindFramebuffer();
/* 62 */         KawaseBloom.renderBlur(this.stencilFramebuffer.framebufferTexture, ((Double)this.shadowRadius.getValue()).intValue(), ((Double)this.shadowOffset.getValue()).intValue());
/*    */       } 
/* 64 */       if (((Boolean)this.blur.getValue()).booleanValue())
/* 65 */         if (this.mode.is("Kawase")) {
/* 66 */           this.stencilFramebuffer = RenderUtil.createFrameBuffer(this.stencilFramebuffer);
/* 67 */           this.stencilFramebuffer.framebufferClear();
/* 68 */           this.stencilFramebuffer.bindFramebuffer(false);
/*    */           
/* 70 */           EventManager.call((Event)new EventShader(false, true, scaledResolution));
/*    */           
/* 72 */           this.stencilFramebuffer.unbindFramebuffer();
/* 73 */           KawaseBlur.renderBlur(this.stencilFramebuffer.framebufferTexture, ((Double)this.iterations.getValue()).intValue(), ((Double)this.offset.getValue()).intValue());
/* 74 */         } else if (this.mode.is("Gaussian")) {
/* 75 */           StencilUtil.initStencilToWrite();
/* 76 */           EventManager.call((Event)new EventShader(false, true, scaledResolution));
/* 77 */           StencilUtil.readStencilBuffer(1);
/* 78 */           GaussianBlur.renderBlur(((Double)this.giterations.getValue()).intValue());
/* 79 */           StencilUtil.uninitStencilBuffer();
/*    */         }  
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\awareline\main\mod\implement\globals\Shader.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */