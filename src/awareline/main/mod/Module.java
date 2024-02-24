/*     */ package awareline.main.mod;
/*     */ import awareline.main.Client;
/*     */ import awareline.main.InstanceAccess;
/*     */ import awareline.main.cmd.CommandModule;
/*     */ import awareline.main.event.EventManager;
/*     */ import awareline.main.mod.implement.visual.HUD;
/*     */ import awareline.main.mod.values.Mode;
/*     */ import awareline.main.mod.values.Value;
/*     */ import awareline.main.ui.animations.AnimationUtil;
/*     */ import awareline.main.ui.gui.hud.notification.ClientNotification;
/*     */ import awareline.main.ui.gui.hud.notification.ModNotification;
/*     */ import awareline.main.utility.chat.Helper;
/*     */ import com.google.common.cache.Cache;
/*     */ import com.google.common.cache.CacheBuilder;
/*     */ import java.io.Serializable;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collections;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Objects;
/*     */ import java.util.concurrent.ExecutionException;
/*     */ import java.util.concurrent.TimeUnit;
/*     */ import net.minecraft.client.gui.ScaledResolution;
/*     */ import net.minecraft.network.Packet;
/*     */ import net.minecraft.util.EnumChatFormatting;
/*     */ import org.lwjgl.input.Keyboard;
/*     */ 
/*     */ public class Module implements InstanceAccess {
/*  29 */   public final ScaledResolution sr = Client.instance.getScaledResolution(); public final String name; public final List<Value> values; public final ModuleType moduleType;
/*     */   private final String[] alias;
/*     */   public String splicedName;
/*     */   public String suffix;
/*     */   public String cusname;
/*     */   public float hoverOpacity;
/*     */   public float clickAnim;
/*     */   public float enableAnim;
/*     */   public float disableAnim;
/*  38 */   private final Cache<Class<? extends Module>, Module> moduleCache = CacheBuilder.newBuilder()
/*  39 */     .expireAfterAccess(1L, TimeUnit.MINUTES)
/*  40 */     .build(); public float openAnim; public float offsetX; public float offsetY; public float animX; public float musicAnim2;
/*     */   public boolean enabled;
/*     */   public boolean enabledOnStartup;
/*     */   
/*     */   public float getHoverOpacity() {
/*  45 */     return this.hoverOpacity; } public float getClickAnim() { return this.clickAnim; } public float getEnableAnim() { return this.enableAnim; } public float getDisableAnim() { return this.disableAnim; } public float getOpenAnim() { return this.openAnim; } public float getOffsetX() { return this.offsetX; } public float getOffsetY() { return this.offsetY; } public float getAnimX() { return this.animX; } public float getMusicAnim2() { return this.musicAnim2; }
/*     */   
/*     */   public boolean arraylistremove = true; public boolean hide;
/*  48 */   public boolean isEnabledOnStartup() { return this.enabledOnStartup; } public boolean expanded; private int key; public boolean isArraylistremove() { return this.arraylistremove; } public boolean isHide() { return this.hide; } public boolean isExpanded() { return this.expanded; }
/*     */    public int getKey() {
/*  50 */     return this.key;
/*     */   }
/*     */   
/*     */   public Module(String name, String[] alias, ModuleType moduleType) {
/*  54 */     this.name = name;
/*  55 */     this.cusname = null;
/*  56 */     this.alias = alias;
/*  57 */     this.moduleType = moduleType;
/*  58 */     this.splicedName = "";
/*  59 */     this.suffix = "";
/*  60 */     this.key = 0;
/*  61 */     this.hide = false;
/*  62 */     this.enabled = false;
/*  63 */     this.values = new ArrayList<>();
/*     */   }
/*     */   
/*     */   public Module(String name, ModuleType moduleType) {
/*  67 */     this.name = name;
/*  68 */     this.cusname = null;
/*  69 */     this.alias = new String[] { name.toLowerCase(), name.toUpperCase() };
/*  70 */     this.moduleType = moduleType;
/*  71 */     this.splicedName = "";
/*  72 */     this.suffix = "";
/*  73 */     this.key = 0;
/*  74 */     this.hide = false;
/*  75 */     this.enabled = false;
/*  76 */     this.values = new ArrayList<>();
/*     */   }
/*     */   
/*     */   public final String getBreakName(boolean usingBreakName) {
/*  80 */     if (this.cusname != null) {
/*  81 */       return this.cusname;
/*     */     }
/*  83 */     return !usingBreakName ? this.name : getSplicedName();
/*     */   }
/*     */ 
/*     */   
/*     */   public final String getSplicedName() {
/*  88 */     if (this.splicedName.isEmpty()) {
/*  89 */       StringBuilder sb = new StringBuilder();
/*  90 */       char[] arr = this.name.toCharArray();
/*  91 */       int j = 0;
/*  92 */       while (j < arr.length) {
/*  93 */         int i = j;
/*  94 */         j++;
/*  95 */         char char1 = arr[i];
/*  96 */         if (i != 0 && !Character.isLowerCase(char1) && Character.isLowerCase(arr[i - 1])) {
/*  97 */           sb.append(' ');
/*     */         }
/*  99 */         sb.append(char1);
/*     */       } 
/*     */       
/* 102 */       this.splicedName = sb.toString();
/*     */     } 
/*     */     
/* 105 */     return this.splicedName;
/*     */   }
/*     */   
/*     */   public final String getCustomName() {
/* 109 */     return this.cusname;
/*     */   }
/*     */   
/*     */   public void setCustomName(String name) {
/* 113 */     this.cusname = name;
/*     */   }
/*     */   
/*     */   public final String getName() {
/* 117 */     return this.name;
/*     */   }
/*     */   
/*     */   public final String getHUDName() {
/* 121 */     return ((Boolean)HUD.getInstance.fontLowerCase.get()).booleanValue() ? getBreakName(true).toLowerCase() : getBreakName(true);
/*     */   }
/*     */   
/*     */   public final String[] getAlias() {
/* 125 */     return this.alias;
/*     */   }
/*     */   
/*     */   public final ModuleType getModuleType() {
/* 129 */     return this.moduleType;
/*     */   }
/*     */   
/*     */   public final ModuleType getType() {
/* 133 */     return this.moduleType;
/*     */   }
/*     */   
/*     */   public final boolean isEnabled() {
/* 137 */     return this.enabled;
/*     */   }
/*     */   
/*     */   public void setEnabled(boolean enabled) {
/* 141 */     this.enabled = enabled;
/*     */     
/* 143 */     if (((Boolean)HUD.sound.get()).booleanValue() && mc.theWorld != null) {
/* 144 */       if (enabled) {
/* 145 */         mc.thePlayer.playSound("random.click", 0.5F, 1.0F);
/*     */       } else {
/* 147 */         mc.thePlayer.playSound("random.click", 0.4F, 0.8F);
/*     */       } 
/*     */     }
/*     */     
/* 151 */     if (this.name.contains("ClickGui")) {
/* 152 */       ClientNotification.sendClientMessage("ClickGui", this.name + (enabled ? " §7Enabled" : " §7Disabled"), 1600L, ClientNotification.Type.INFO);
/*     */     
/*     */     }
/* 155 */     else if (((Boolean)HUD.moduleNotification.get()).booleanValue()) {
/* 156 */       ModNotification.sendClientMessage(this.name + (enabled ? " has been enabled" : " has been disabled"), 1600L);
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 161 */     if (enabled) {
/* 162 */       onEnable();
/* 163 */       EventManager.register(new Object[] { this });
/*     */     } else {
/* 165 */       EventManager.unregister(new Object[] { this });
/* 166 */       onDisable();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setEnabledByConvention(boolean enabled) {
/* 176 */     this.enabled = enabled;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isEnabled(Class<? extends Module>... modules) {
/* 181 */     for (Class<? extends Module> mClass : modules) {
/* 182 */       Module module = getModule(mClass);
/* 183 */       if (module.enabled) {
/* 184 */         return true;
/*     */       }
/*     */     } 
/* 187 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setEnabled(boolean stage, Class<? extends Module>... modules) {
/* 192 */     for (Class<? extends Module> mClass : modules) {
/* 193 */       Module module = getModule(mClass);
/* 194 */       module.setEnabled(stage);
/*     */     } 
/*     */   }
/*     */   
/*     */   public boolean isEnabled(Class<? extends Module> cls) {
/* 199 */     for (Module m : Client.instance.getModuleManager().getModules()) {
/* 200 */       if (m.getClass() != cls) {
/*     */         continue;
/*     */       }
/* 203 */       return m.enabled;
/*     */     } 
/* 205 */     return false;
/*     */   }
/*     */   
/*     */   public final boolean wasHide() {
/* 209 */     return this.hide;
/*     */   }
/*     */   
/*     */   public final void setHide(boolean hided) {
/* 213 */     this.hide = hided;
/*     */   }
/*     */   
/*     */   public final String getSuffix() {
/* 217 */     return ((Boolean)HUD.getInstance.fontLowerCase.get()).booleanValue() ? this.suffix.toLowerCase() : this.suffix;
/*     */   }
/*     */   
/*     */   public final void setSuffix(Serializable obj) {
/* 221 */     if (mc.thePlayer.ticksExisted % 4 == 0) {
/* 222 */       String suffix = obj.toString();
/* 223 */       if (!Objects.equals(this.suffix, suffix)) {
/* 224 */         if (suffix.isEmpty()) {
/* 225 */           this.suffix = suffix;
/*     */         }
/* 227 */         else if (((Boolean)HUD.getInstance.suffixUI.get()).booleanValue()) {
/* 228 */           EnumChatFormatting color = ((Boolean)HUD.getInstance.suffixWhite.get()).booleanValue() ? EnumChatFormatting.WHITE : EnumChatFormatting.GRAY;
/* 229 */           if (HUD.getInstance.suffixMode.is("Binds")) {
/* 230 */             this.suffix = String.format(color + "§7§f%s", new Object[] { color }) + Keyboard.getKeyName(this.key);
/* 231 */           } else if (HUD.getInstance.suffixMode.is("Simple")) {
/* 232 */             this.suffix = String.format("§7§f%s", new Object[] { color + suffix });
/* 233 */           } else if (HUD.getInstance.suffixMode.is("Box")) {
/* 234 */             this.suffix = String.format(color + "[§f%s]", new Object[] { color + suffix });
/* 235 */           } else if (HUD.getInstance.suffixMode.is("Brackets")) {
/* 236 */             this.suffix = String.format(color + "(§f%s)", new Object[] { color + suffix });
/* 237 */           } else if (HUD.getInstance.suffixMode.is("XML")) {
/* 238 */             this.suffix = String.format(color + "<§f%s>", new Object[] { color + suffix });
/* 239 */           } else if (HUD.getInstance.suffixMode.is("VerticalBar")) {
/* 240 */             this.suffix = String.format(color + "| §f%s", new Object[] { color + suffix });
/* 241 */           } else if (HUD.getInstance.suffixMode.is("Hyphen")) {
/* 242 */             this.suffix = String.format(color + "- §f%s", new Object[] { color + suffix });
/*     */           } 
/*     */         } else {
/* 245 */           this.suffix = "";
/*     */         } 
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void setEnabledWithoutNotification(boolean enabled) {
/* 253 */     this.enabled = enabled;
/* 254 */     if (enabled) {
/* 255 */       onEnable();
/* 256 */       EventManager.register(new Object[] { this });
/*     */     } else {
/* 258 */       EventManager.unregister(new Object[] { this });
/* 259 */       onDisable();
/*     */     } 
/*     */   }
/*     */   
/*     */   protected void addSettings(Value... values) {
/* 264 */     Collections.addAll(this.values, values);
/*     */   }
/*     */   
/*     */   public final List<Value> getValues() {
/* 268 */     return this.values;
/*     */   }
/*     */   
/*     */   public boolean isMoving() {
/* 272 */     return mc.thePlayer.moving();
/*     */   }
/*     */   
/*     */   public void setKey(int key) {
/* 276 */     this.key = key;
/*     */   }
/*     */ 
/*     */   
/*     */   public void onEnable() {}
/*     */ 
/*     */   
/*     */   public void onDisable() {}
/*     */ 
/*     */   
/*     */   public void makeCommand() {
/* 287 */     if (!this.values.isEmpty()) {
/* 288 */       StringBuilder options = new StringBuilder();
/* 289 */       StringBuilder other = new StringBuilder();
/* 290 */       Iterator<Value> var4 = this.values.iterator();
/*     */       
/* 292 */       while (var4.hasNext()) {
/* 293 */         Value v = var4.next();
/* 294 */         if (!(v instanceof Mode)) {
/* 295 */           if (options.length() == 0) {
/* 296 */             options.append(v.getName()); continue;
/*     */           } 
/* 298 */           options.append(String.format(", %s", new Object[] { v.getName() }));
/*     */         } 
/*     */       } 
/*     */ 
/*     */       
/* 303 */       var4 = this.values.iterator();
/*     */ 
/*     */       
/*     */       while (true) {
/* 307 */         if (!var4.hasNext()) {
/* 308 */           Client.instance.getCommandManager().add((Command)new CommandModule(this, this.name, this.alias));
/*     */           
/*     */           return;
/*     */         } 
/* 312 */         Value v = var4.next();
/* 313 */         if (v instanceof Mode) {
/* 314 */           Mode mode = (Mode)v;
/*     */           String[] modes;
/* 316 */           int length = (modes = mode.getModes()).length;
/*     */           
/* 318 */           for (int i = 0; i < length; i++) {
/* 319 */             String e = modes[i];
/* 320 */             if (other.length() == 0) {
/* 321 */               other.append(e.toLowerCase());
/*     */             } else {
/* 323 */               other.append(String.format(", %s", new Object[] { e.toLowerCase() }));
/*     */             } 
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   public <Module extends Module> Module getModule(Class<? extends Module> moduleClass) {
/*     */     try {
/* 333 */       return (Module)this.moduleCache.get(moduleClass, () -> Client.instance.getModuleManager().getModuleByClass(moduleClass));
/* 334 */     } catch (ExecutionException e) {
/* 335 */       throw new RuntimeException(e);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void checkModule(Class<? extends Module>... modules) {
/* 341 */     for (Class<? extends Module> mClass : modules) {
/* 342 */       Module module = getModule(mClass);
/* 343 */       if (module.enabled) {
/* 344 */         module.toggle();
/* 345 */         ClientNotification.sendClientMessage("Warning", module.name + " was disabled to prevent flags/errors", 2000L, ClientNotification.Type.WARNING);
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   public void checkModule(Class<? extends Module> moduleClass) {
/* 351 */     Module module = Client.instance.getModuleManager().getModuleByClass(moduleClass);
/* 352 */     if (module != null && module.enabled) {
/* 353 */       ClientNotification.sendClientMessage("Warning", module.name + " was disabled to prevent flags/errors", 2000L, ClientNotification.Type.WARNING);
/* 354 */       module.toggle();
/*     */     } 
/*     */   }
/*     */   
/*     */   public float moveUD(float var, float target) {
/* 359 */     return AnimationUtil.moveUD(var, target);
/*     */   }
/*     */   
/*     */   public boolean toggle() {
/* 363 */     if (this.enabled) {
/* 364 */       setEnabled(false);
/* 365 */       return false;
/*     */     } 
/* 367 */     setEnabled(true);
/* 368 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public float getYAnim() {
/* 373 */     return this.offsetY;
/*     */   }
/*     */   
/*     */   public boolean wasArrayRemoved() {
/* 377 */     return this.arraylistremove;
/*     */   }
/*     */   
/*     */   public void setArrayRemoved(boolean arraylistremove) {
/* 381 */     this.arraylistremove = arraylistremove;
/*     */   }
/*     */   
/*     */   public void send(Packet<?> packet) {
/* 385 */     if (mc.thePlayer != null) {
/* 386 */       mc.getNetHandler().getNetworkManager().sendPacket(packet);
/*     */     }
/*     */   }
/*     */   
/*     */   public void sendNoEvent(Packet<?> packet) {
/* 391 */     if (mc.thePlayer != null) {
/* 392 */       mc.getNetHandler().getNetworkManager().sendPacketNoEvent(packet);
/*     */     }
/*     */   }
/*     */   
/*     */   public void sendPacket(Packet<?> packet) {
/* 397 */     if (mc.thePlayer != null) {
/* 398 */       mc.getNetHandler().getNetworkManager().sendPacket(packet);
/*     */     }
/*     */   }
/*     */   
/*     */   public void sendPacketNoEvent(Packet<?> packet) {
/* 403 */     if (mc.thePlayer != null) {
/* 404 */       mc.getNetHandler().getNetworkManager().sendPacketNoEvent(packet);
/*     */     }
/*     */   }
/*     */   
/*     */   public void msg(String message) {
/* 409 */     Helper.sendMessage(message);
/*     */   }
/*     */   
/*     */   public void msg(Objects message) {
/* 413 */     Helper.sendMessage(message.toString());
/*     */   }
/*     */   
/*     */   public void msg(String message, boolean prefix) {
/* 417 */     if (prefix) {
/* 418 */       Helper.sendMessage(message);
/*     */     } else {
/* 420 */       Helper.sendMessageWithoutPrefix(message);
/*     */     } 
/*     */   }
/*     */   
/*     */   public void noti(String title, String msg) {
/* 425 */     ClientNotification.sendClientMessage(title, msg, 3000L, ClientNotification.Type.INFO);
/*     */   }
/*     */   
/*     */   public void notiWarning(String title, String msg) {
/* 429 */     ClientNotification.sendClientMessage(title, msg, 3000L, ClientNotification.Type.WARNING);
/*     */   }
/*     */ 
/*     */   
/*     */   public void noti(String msg, boolean SUCCESS) {
/* 434 */     ClientNotification.sendClientMessage(getHUDName(), msg, 3000L, SUCCESS ? ClientNotification.Type.SUCCESS : ClientNotification.Type.ERROR);
/*     */   }
/*     */ 
/*     */   
/*     */   public void noti(String title, String msg, long time, ClientNotification.Type type) {
/* 439 */     ClientNotification.sendClientMessage(title, msg, time, type);
/*     */   }
/*     */   
/*     */   public boolean getState() {
/* 443 */     return this.enabled;
/*     */   }
/*     */   
/*     */   public boolean hasSword() {
/* 447 */     return (mc.thePlayer.getHeldItem() != null && mc.thePlayer.getHeldItem().getItem() instanceof net.minecraft.item.ItemSword);
/*     */   }
/*     */   
/*     */   public void setExpanded(boolean b) {
/* 451 */     this.expanded = b;
/*     */   }
/*     */   
/*     */   public boolean bad() {
/* 455 */     return Client.instance.badPacketsComponent.bad(true, true, true, true, true);
/*     */   }
/*     */   
/*     */   public boolean bad(boolean slot, boolean attack, boolean swing, boolean block, boolean inventory) {
/* 459 */     return Client.instance.badPacketsComponent.bad(slot, attack, swing, block, inventory);
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\awareline\main\mod\Module.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */