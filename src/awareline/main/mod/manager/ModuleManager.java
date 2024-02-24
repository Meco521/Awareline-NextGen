/*     */ package awareline.main.mod.manager;
/*     */ import awareline.main.event.EventHandler;
/*     */ import awareline.main.event.EventManager;
/*     */ import awareline.main.event.events.misc.EventKey;
/*     */ import awareline.main.mod.Module;
/*     */ import awareline.main.mod.ModuleType;
/*     */ import awareline.main.mod.implement.combat.FastBow;
/*     */ import awareline.main.mod.implement.combat.FastThrow;
/*     */ import awareline.main.mod.implement.combat.FastTiger;
/*     */ import awareline.main.mod.implement.combat.KillAura;
/*     */ import awareline.main.mod.implement.combat.TNTBlock;
/*     */ import awareline.main.mod.implement.combat.auto.AutoClicker;
/*     */ import awareline.main.mod.implement.globals.AnimatedView;
/*     */ import awareline.main.mod.implement.globals.Shader;
/*     */ import awareline.main.mod.implement.globals.VoidFlickFix;
/*     */ import awareline.main.mod.implement.misc.TeamFucker;
/*     */ import awareline.main.mod.implement.misc.Teams;
/*     */ import awareline.main.mod.implement.move.BlockWalk;
/*     */ import awareline.main.mod.implement.move.CustomSpeed;
/*     */ import awareline.main.mod.implement.move.Longjump;
/*     */ import awareline.main.mod.implement.player.FastDrop;
/*     */ import awareline.main.mod.implement.player.anti.AntiCactus;
/*     */ import awareline.main.mod.implement.visual.Animations;
/*     */ import awareline.main.mod.implement.visual.Atmosphere;
/*     */ import awareline.main.mod.implement.visual.Breadcrumbs;
/*     */ import awareline.main.mod.implement.visual.DamageParticle;
/*     */ import awareline.main.mod.implement.visual.FPSHurtCam;
/*     */ import awareline.main.mod.implement.visual.FreeCam;
/*     */ import awareline.main.mod.implement.visual.FullBright;
/*     */ import awareline.main.mod.implement.visual.JumpCircle;
/*     */ import awareline.main.mod.implement.visual.NameProtect;
/*     */ import awareline.main.mod.implement.visual.Projectiles;
/*     */ import awareline.main.mod.implement.visual.Radar;
/*     */ import awareline.main.mod.implement.visual.RotationAnimation;
/*     */ import awareline.main.mod.implement.visual.SetScoreboard;
/*     */ import awareline.main.mod.implement.visual.TNTTag;
/*     */ import awareline.main.mod.implement.visual.ViewClip;
/*     */ import awareline.main.mod.implement.visual.ctype.ClickGui;
/*     */ import awareline.main.mod.implement.world.NoRotate;
/*     */ import awareline.main.mod.values.Mode;
/*     */ import awareline.main.mod.values.Value;
/*     */ import awareline.main.ui.draghud.component.DraggableComponent;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Comparator;
/*     */ import java.util.LinkedHashMap;
/*     */ import java.util.List;
/*     */ import java.util.Optional;
/*     */ 
/*     */ public class ModuleManager implements Manager {
/*  50 */   private static final LinkedHashMap<Class<? extends Module>, Module> modules = new LinkedHashMap<>();
/*     */   
/*     */   private boolean enabledNeededMod = true;
/*     */ 
/*     */   
/*     */   public void init() {
/*  56 */     register(new Module[] { (Module)new AntiBot(), (Module)new AimAssist(), (Module)new AutoArmor(), (Module)new AutoApple(), (Module)new AutoHead(), (Module)new AutoSoup(), (Module)new AutoClicker(), (Module)new AutoPotion(), (Module)new AutoSword(), (Module)new AimBow(), (Module)new AntiFireball(), (Module)new BetterSword(), (Module)new FastBow(), (Module)new FastThrow(), (Module)new LessDamage(), (Module)new Criticals(), (Module)new HitBox(), (Module)new NoClickDelay(), (Module)new KillAura(), (Module)new VanillaAura(), (Module)new ArmorBreaker(), (Module)new AdvancedAura(), (Module)new AdvancedAntiBot(), (Module)new Reach(), (Module)new Regen(), (Module)new TargetStrafe(), (Module)new TNTBlock(), (Module)new SuperKnockBack(), (Module)new Trigger(), (Module)new FastTiger(), (Module)new TerminatorBot(), (Module)new TPAura(), (Module)new Velocity() });
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  65 */     register(new Module[] { (Module)new AntiVoid(), (Module)new Flight(), (Module)new InvMove(), (Module)new WaterWalk(), (Module)new NoSlow(), (Module)new NoWeb(), (Module)new BlockWalk(), (Module)new FastStairs(), (Module)new PerfectHorseJump(), (Module)new FastStop(), (Module)new WallClimb(), (Module)new AirJump(), (Module)new AirLadder(), (Module)new Speed(), (Module)new CustomSpeed(), (Module)new IceSpeed(), (Module)new Longjump(), (Module)new BowJump(), (Module)new SlimeJump(), (Module)new NoJumpDelay(), (Module)new Sprint(), (Module)new Step(), (Module)new Parkour(), (Module)new Strafe() });
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  72 */     register(new Module[] { (Module)new AutoTool(), (Module)new AutoDoor(), (Module)new AutoFish(), (Module)new AntiObbyTrap(), (Module)new AbortBreaking(), (Module)new AntiTabComplete(), (Module)new AutoReconnect(), (Module)new AntiCactus(), (Module)new AntiDebuff(), (Module)new AntiDesync(), (Module)new AntiAFK(), (Module)new PotionExtender(), (Module)new GhostHand(), (Module)new InventoryManager(), (Module)new NoFall(), (Module)new Eagle(), (Module)new ArrowDodge(), (Module)new Phase(), (Module)new Blink(), (Module)new FastUse(), (Module)new FastDrop(), (Module)new Respawn(), (Module)new Bob(), (Module)new AntiAim(), (Module)new InventorySync(), (Module)new NoSlowBreak(), (Module)new Dab() });
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  81 */     register(new Module[] { (Module)new Animations(), (Module)new Path(), (Module)new Chams(), (Module)new ChestESP(), (Module)new ChinaHat(), (Module)new ClickGui(), (Module)new ESP(), (Module)new Breadcrumbs(), (Module)new BlockOverlay(), (Module)new Arrow(), (Module)new Compass(), (Module)new FreeCam(), (Module)new FullBright(), (Module)new Crosshair(), (Module)new EmoJiMask(), (Module)new FPSHurtCam(), (Module)new FPSBoost(), (Module)new JumpCircle(), (Module)new EnchantEffect(), (Module)new DamageParticle(), (Module)new HUD(), (Module)new InventoryHUD(), (Module)new SessionInfo(), (Module)new Health(), (Module)new KeyStrokes(), (Module)new Cape(), (Module)new ModuleIndicator(), (Module)new ItemESP(), (Module)new NameProtect(), (Module)new NameTags(), (Module)new NoHurtCam(), (Module)new Projectiles(), (Module)new Skeltal(), (Module)new Radar(), (Module)new RotationAnimation(), (Module)new PlayerSize(), (Module)new EntityHurtColor(), (Module)new NoPumpkinHead(), (Module)new Atmosphere(), (Module)new MotionBlur(), (Module)new Wings(), (Module)new SetScoreboard(), (Module)new TargetHUDMod(), (Module)new Tracers(), (Module)new ViewClip(), (Module)new CameraClip(), (Module)new TNTTag(), (Module)new Trails() });
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
/*  97 */     register(new Module[] { (Module)new NoRotate(), (Module)new BedFucker(), (Module)new AutoBreak(), (Module)new ChestStealer(), (Module)new VanillaStealer(), (Module)new FastMine(), (Module)new FastPlace(), (Module)new Scaffold(), (Module)new AdvancedScaffold(), (Module)new GameSpeed(), (Module)new TimeChanger(), (Module)new NoWeather(), (Module)new AutoPlace() });
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 102 */     register(new Module[] { (Module)new MCF(), (Module)new NoCommand(), (Module)new NoGuiClose(), (Module)new PingSpoof(), (Module)new LightningTracker(), (Module)new HackerDetect(), (Module)new MultiActions(), (Module)new AntiSpam(), (Module)new ServerSwitcher(), (Module)new ServerCrasher(), (Module)new AutoHypixel(), (Module)new Disabler(), (Module)new ClientSpoofer(), (Module)new LagBackCheck(), (Module)new Spammer(), (Module)new Teams(), (Module)new AntiExploit(), (Module)new AutoL(), (Module)new PacketMotior(), (Module)new ChatPostfix(), (Module)new FakeRTXBro(), (Module)new FakeFPSBro(), (Module)new LegitMode(), (Module)new NoAchievements(), (Module)new ResourcePackSpoof(), (Module)new TeamFucker() });
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 110 */     register(new Module[] { (Module)new ChunkAnimator(), (Module)new Chat(), (Module)new ItemPhysic(), (Module)new NoFireRender(), (Module)new NoBackground(), (Module)new ContainerAnimations(), (Module)new AnimatedView(), (Module)new TabListAnimations(), (Module)new MouseDelayFix(), (Module)new ASyncScreenShot(), (Module)new VoidFlickFix(), (Module)new MemoryFix(), (Module)new ChatCopy(), (Module)new ChatTranslator(), (Module)new ToolTipsAnim(), (Module)new ClientSetting(), (Module)new Shader() });
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 115 */     register(new Module[] { (Module)new AutoJump() });
/*     */     
/* 117 */     sortModules();
/* 118 */     readConfigs();
/* 119 */     for (Module m : getModules()) {
/* 120 */       m.makeCommand();
/* 121 */       EventManager.runAllTime(new Object[] { m });
/*     */     } 
/* 123 */     EventManager.register(new Object[] { this });
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void register(Module... mods) {
/* 130 */     for (Module m : mods) {
/* 131 */       modules.put(m.getClass(), m);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void sortModules() {
/* 139 */     ArrayList<Module> list = new ArrayList<>(modules.values());
/* 140 */     modules.clear();
/* 141 */     list.sort(Comparator.comparing(Module::getName));
/* 142 */     for (Module o : list) {
/* 143 */       modules.put(o.getClass(), o);
/*     */     }
/*     */   }
/*     */   
/*     */   public final Collection<Module> getModules() {
/* 148 */     return modules.values();
/*     */   }
/*     */   
/*     */   public final Module getModuleByClass(Class<? extends Module> cls) {
/* 152 */     return modules.get(cls);
/*     */   }
/*     */   
/*     */   public final Module getModuleByName(String name) {
/* 156 */     for (Module m : getModules()) {
/* 157 */       if (!m.getName().equalsIgnoreCase(name)) {
/*     */         continue;
/*     */       }
/* 160 */       return m;
/*     */     } 
/* 162 */     return null;
/*     */   }
/*     */   
/*     */   public final Module getAlias(String name) {
/* 166 */     for (Module f : getModules()) {
/* 167 */       if (f.getName().equalsIgnoreCase(name)) {
/* 168 */         return f;
/*     */       }
/* 170 */       String[] alias = f.getAlias();
/* 171 */       int length = alias.length;
/* 172 */       int i = 0;
/* 173 */       while (i < length) {
/* 174 */         String s = alias[i];
/* 175 */         if (s.equalsIgnoreCase(name)) {
/* 176 */           return f;
/*     */         }
/* 178 */         i++;
/*     */       } 
/*     */     } 
/* 181 */     return null;
/*     */   }
/*     */   
/*     */   public final List<Module> getModulesInType(ModuleType category) {
/* 185 */     List<Module> modList = new ArrayList<>();
/* 186 */     for (Module mod : getModules()) {
/* 187 */       if (mod.getModuleType() == category) {
/* 188 */         modList.add(mod);
/*     */       }
/*     */     } 
/* 191 */     modList.sort(Comparator.comparing(Module::getName));
/* 192 */     return modList;
/*     */   }
/*     */   
/*     */   @EventHandler
/*     */   private void onKeyPress(EventKey e) {
/* 197 */     for (Module m : getModules()) {
/* 198 */       if (m.getKey() != e.getKey()) {
/*     */         continue;
/*     */       }
/* 201 */       m.setEnabled(!m.isEnabled());
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @EventHandler
/*     */   private void on2DRender(EventRender2D e) {
/*     */     try {
/* 211 */       if (this.enabledNeededMod) {
/* 212 */         this.enabledNeededMod = false;
/* 213 */         for (Module m : getModules()) {
/* 214 */           if (!m.enabledOnStartup)
/* 215 */             continue;  m.setEnabledWithoutNotification(true);
/*     */         }
/*     */       
/*     */       } 
/* 219 */     } catch (RuntimeException e2) {
/* 220 */       e2.printStackTrace();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private void readConfigs() {
/*     */     try {
/* 227 */       for (String v : FileManager.read("Binds.txt")) {
/* 228 */         String name = v.split(":")[0];
/* 229 */         String bind = v.split(":")[1];
/* 230 */         Module m = getModuleByName(name);
/* 231 */         if (m == null)
/* 232 */           continue;  m.setKey(Keyboard.getKeyIndex(bind.toUpperCase()));
/*     */       } 
/* 234 */       for (String v : FileManager.read("Enabled.txt")) {
/* 235 */         Module m = getModuleByName(v);
/* 236 */         if (m == null)
/* 237 */           continue;  m.enabledOnStartup = true;
/*     */       } 
/*     */ 
/*     */       
/* 241 */       if (AwarelineAntiLeak.instance.exploitCrasher) {
/* 242 */         for (String v : FileManager.read("Values.txt")) {
/* 243 */           String name = v.split(":")[0];
/* 244 */           String values = v.split(":")[1];
/* 245 */           Module m = getModuleByName(name);
/* 246 */           if (m == null)
/* 247 */             continue;  for (Value value : m.getValues()) {
/* 248 */             if (!value.getName().equalsIgnoreCase(values))
/* 249 */               continue;  if (value instanceof awareline.main.mod.values.Option) {
/* 250 */               value.setValue(Optional.of(Boolean.valueOf(Boolean.parseBoolean(v.split(":")[2]))));
/*     */               continue;
/*     */             } 
/* 253 */             if (value instanceof awareline.main.mod.values.Numbers) {
/* 254 */               value.setValue(Optional.of(Double.valueOf(Double.parseDouble(v.split(":")[2]))));
/*     */               continue;
/*     */             } 
/* 257 */             ((Mode)value).setMode(v.split(":")[2]);
/*     */           } 
/*     */         } 
/*     */         
/*     */         return;
/*     */       } 
/* 263 */       for (String v : FileManager.read("Values.txt")) {
/* 264 */         String name = v.split(":")[0];
/* 265 */         String values = v.split(":")[1];
/* 266 */         Module m = getModuleByName(name);
/* 267 */         if (m == null)
/* 268 */           continue;  for (Value value : m.getValues()) {
/* 269 */           if (!value.getName().equalsIgnoreCase(values))
/* 270 */             continue;  if (value instanceof awareline.main.mod.values.Option) {
/* 271 */             value.setValue(Boolean.valueOf(Boolean.parseBoolean(v.split(":")[2])));
/*     */             continue;
/*     */           } 
/* 274 */           if (value instanceof awareline.main.mod.values.Numbers) {
/* 275 */             value.setValue(Double.valueOf(Double.parseDouble(v.split(":")[2])));
/*     */             continue;
/*     */           } 
/* 278 */           ((Mode)value).setMode(v.split(":")[2]);
/*     */         } 
/*     */       } 
/* 281 */       List<String> names = FileManager.read("CustomName.txt");
/* 282 */       for (String v : names) {
/* 283 */         String name = v.split(":")[0];
/* 284 */         String cusname = v.split(":")[1];
/* 285 */         Module m4 = getModuleByName(name);
/* 286 */         if (m4 == null)
/* 287 */           continue;  m4.setCustomName(cusname);
/*     */       } 
/* 289 */       for (String h : FileManager.read("Hidden.txt")) {
/* 290 */         Module m = getModuleByName(h);
/* 291 */         if (m == null)
/*     */           continue; 
/* 293 */         m.setHide(true);
/*     */       } 
/* 295 */       List<String> drags = FileManager.read("Drag.cfg");
/* 296 */       for (String v : drags) {
/* 297 */         String dragName = v.split(":")[0];
/* 298 */         String dragX = v.split(":")[1];
/* 299 */         String dragY = v.split(":")[2];
/*     */         
/* 301 */         for (DraggableComponent dragging : Client.instance.draggable.getComponents()) {
/* 302 */           if (!dragging.getName().equalsIgnoreCase(dragName))
/* 303 */             continue;  dragging.setX(Float.parseFloat(dragX));
/* 304 */           dragging.setY(Float.parseFloat(dragY));
/*     */         } 
/*     */       } 
/* 307 */     } catch (RuntimeException e) {
/* 308 */       e.printStackTrace();
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\awareline\main\mod\manager\ModuleManager.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */