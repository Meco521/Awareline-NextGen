/*     */ package awareline.main.mod.implement.visual.ctype;
/*     */ import awareline.main.event.EventHandler;
/*     */ import awareline.main.event.events.LBEvents.EventWorldChanged;
/*     */ import awareline.main.event.events.ketaShaderCall.EventShader;
/*     */ import awareline.main.mod.Module;
/*     */ import awareline.main.mod.ModuleType;
/*     */ import awareline.main.mod.values.Mode;
/*     */ import awareline.main.mod.values.Option;
/*     */ import awareline.main.mod.values.Value;
/*     */ import awareline.main.ui.gui.clickgui.mode.awareline.AwarelineUI;
/*     */ import awareline.main.ui.gui.clickgui.mode.box.FluxGui;
/*     */ import awareline.main.ui.gui.clickgui.mode.list.ClickyUI;
/*     */ import awareline.main.ui.gui.clickgui.mode.old.OldClickGui;
/*     */ import java.awt.Color;
/*     */ import java.util.List;
/*     */ import java.util.concurrent.CopyOnWriteArrayList;
/*     */ import net.minecraft.client.gui.Gui;
/*     */ import net.minecraft.client.gui.GuiScreen;
/*     */ import org.lwjgl.opengl.Display;
/*     */ 
/*     */ public class ClickGui extends Module {
/*  22 */   public static final Option<Boolean> Streamer = new Option("Streamer", Boolean.valueOf(true));
/*  23 */   public static final Option<Boolean> Blur = new Option("Blur", Boolean.valueOf(false));
/*  24 */   public static final Option<Boolean> Visitable = new Option("ValueVisitable", Boolean.valueOf(true));
/*  25 */   public static final Option<Boolean> fpsMouseWheel = new Option("FastWheel", Boolean.valueOf(false));
/*  26 */   public static final Option<Boolean> smoothFont = new Option("SmoothFont", Boolean.valueOf(true));
/*  27 */   public static final Option<Boolean> disableBlur = new Option("DisableBlur", Boolean.valueOf(true));
/*  28 */   public static List<Module> memoriseML = new CopyOnWriteArrayList<>();
/*     */   public static ModuleType memoriseCatecory;
/*     */   public static float startX;
/*     */   public static float startY;
/*     */   public static ModuleType currentModuleType;
/*     */   public static int tempWheel;
/*  34 */   public static float memoriseX = 30.0F;
/*  35 */   public static float memoriseY = 30.0F;
/*     */   public static int memoriseWheel;
/*  37 */   private final Mode<String> mode = new Mode("Mode", new String[] { "Box", "Awareline", "List", "Old" }, "Awareline");
/*     */   public static ClickGui getInstance;
/*     */   
/*     */   public ClickGui() {
/*  41 */     super("ClickGui", ModuleType.Render);
/*  42 */     if (getKey() == 0) {
/*  43 */       setKey(54);
/*     */     }
/*  45 */     addSettings(new Value[] { (Value)this.mode, (Value)Streamer, (Value)Visitable, (Value)fpsMouseWheel, (Value)smoothFont, (Value)disableBlur });
/*     */     
/*  47 */     getInstance = this;
/*     */   }
/*     */ 
/*     */   
/*     */   public void onEnable() {
/*  52 */     if (this.mode.is("List")) {
/*  53 */       mc.displayGuiScreen((GuiScreen)new ClickyUI());
/*  54 */     } else if (this.mode.is("Old")) {
/*  55 */       mc.displayGuiScreen((GuiScreen)new OldClickGui());
/*  56 */     } else if (this.mode.is("Awareline")) {
/*  57 */       mc.displayGuiScreen((GuiScreen)new AwarelineUI());
/*  58 */     } else if (this.mode.is("Box")) {
/*  59 */       mc.displayGuiScreen((GuiScreen)new FluxGui());
/*  60 */       FluxGui.setX(memoriseX);
/*  61 */       FluxGui.setY(memoriseY);
/*  62 */       FluxGui.setWheel(memoriseWheel);
/*  63 */       FluxGui.setInSetting(memoriseML);
/*  64 */       if (memoriseCatecory != null) {
/*  65 */         FluxGui.setCategory(memoriseCatecory);
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   @EventHandler
/*     */   private void onWorldChange(EventWorldChanged e) {
/*  72 */     setEnabled(false);
/*     */   }
/*     */   
/*     */   @EventHandler
/*     */   private void onBloom(EventShader e) {
/*  77 */     if (((Boolean)disableBlur.get()).booleanValue()) {
/*     */       return;
/*     */     }
/*  80 */     if (e.onBloom()) {
/*     */       return;
/*     */     }
/*  83 */     Gui.drawRect(0.0D, 0.0D, Display.getWidth(), Display.getHeight(), (new Color(0, 0, 0, 255)).getRGB());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onDisable() {
/* 144 */     super.onDisable();
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\awareline\main\mod\implement\visual\ctype\ClickGui.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */