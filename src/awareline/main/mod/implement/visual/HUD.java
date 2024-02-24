/*      */ package awareline.main.mod.implement.visual;
/*      */ 
/*      */ import awareline.antileak.VerifyData;
/*      */ import awareline.main.Client;
/*      */ import awareline.main.event.EventHandler;
/*      */ import awareline.main.event.events.ketaShaderCall.EventShader;
/*      */ import awareline.main.event.events.world.renderEvents.EventRender2D;
/*      */ import awareline.main.mod.Module;
/*      */ import awareline.main.mod.ModuleType;
/*      */ import awareline.main.mod.implement.combat.KillAura;
/*      */ import awareline.main.mod.implement.globals.Chat;
/*      */ import awareline.main.mod.implement.globals.Shader;
/*      */ import awareline.main.mod.implement.misc.fake.FakeFPSBro;
/*      */ import awareline.main.mod.implement.world.Scaffold;
/*      */ import awareline.main.mod.values.Mode;
/*      */ import awareline.main.mod.values.Numbers;
/*      */ import awareline.main.mod.values.Option;
/*      */ import awareline.main.mod.values.Value;
/*      */ import awareline.main.ui.animations.AnimationUtil;
/*      */ import awareline.main.ui.font.cfont.CFontRenderer;
/*      */ import awareline.main.ui.font.fontmanager.font.FontManager;
/*      */ import awareline.main.ui.font.fontmanager.font.GLUtils;
/*      */ import awareline.main.ui.font.fontmanager.font.TrueTypeFontDrawer;
/*      */ import awareline.main.ui.gui.clickgui.RenderUtil;
/*      */ import awareline.main.ui.gui.guimainmenu.mainmenu.ClientSetting;
/*      */ import awareline.main.ui.gui.hud.notification.ClientNotification;
/*      */ import awareline.main.ui.gui.hud.notification.ModNotification;
/*      */ import awareline.main.ui.simplecore.SimpleRender;
/*      */ import awareline.main.utility.MoveUtils;
/*      */ import awareline.main.utility.math.MathUtil;
/*      */ import awareline.main.utility.render.RenderUtil;
/*      */ import com.github.creeper123123321.viafabric.ViaFabric;
/*      */ import com.github.creeper123123321.viafabric.util.ProtocolUtils;
/*      */ import java.awt.Color;
/*      */ import java.io.Serializable;
/*      */ import java.text.SimpleDateFormat;
/*      */ import java.util.ArrayList;
/*      */ import java.util.Date;
/*      */ import java.util.List;
/*      */ import net.minecraft.client.Minecraft;
/*      */ import net.minecraft.client.gui.Gui;
/*      */ import net.minecraft.client.gui.GuiChat;
/*      */ import net.minecraft.client.gui.ScaledResolution;
/*      */ import net.minecraft.client.network.NetworkPlayerInfo;
/*      */ import net.minecraft.client.renderer.GlStateManager;
/*      */ import net.minecraft.client.renderer.RenderHelper;
/*      */ import net.minecraft.client.resources.I18n;
/*      */ import net.minecraft.item.ItemStack;
/*      */ import net.minecraft.potion.Potion;
/*      */ import net.minecraft.potion.PotionEffect;
/*      */ import net.minecraft.util.EnumChatFormatting;
/*      */ import net.minecraft.util.MathHelper;
/*      */ import net.minecraft.util.ResourceLocation;
/*      */ import org.lwjgl.opengl.GL11;
/*      */ 
/*      */ public class HUD extends Module {
/*   57 */   public static final String[] rainbows = new String[] { "Client", "Astolfo", "Sakura", "Chemical", "Destroyer", "Gothic", "BLight", "Rise", "Mexico", "Neon", "Dark", "Random" };
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*   63 */   public static final Option<Boolean> rainbow = new Option("Rainbow", Boolean.valueOf(false));
/*   64 */   public static final Mode<String> rainbowMode = new Mode("Rainbows", rainbows, rainbows[0], rainbow::get);
/*      */ 
/*      */   
/*   67 */   public static final Numbers<Double> r = new Numbers("Red", Double.valueOf(145.0D), Double.valueOf(0.0D), Double.valueOf(255.0D), Double.valueOf(5.0D));
/*   68 */   public static final Numbers<Double> g = new Numbers("Green", Double.valueOf(90.0D), Double.valueOf(0.0D), Double.valueOf(255.0D), Double.valueOf(5.0D));
/*   69 */   public static final Numbers<Double> b = new Numbers("Blue", Double.valueOf(169.0D), Double.valueOf(0.0D), Double.valueOf(255.0D), Double.valueOf(5.0D));
/*   70 */   public static final Numbers<Double> alpha = new Numbers("Alpha", Double.valueOf(100.0D), Double.valueOf(0.0D), Double.valueOf(255.0D), Double.valueOf(5.0D));
/*   71 */   public static final Option<Boolean> dynamicColor = new Option("DynamicColor", Boolean.valueOf(true));
/*      */ 
/*      */   
/*   74 */   public static final Option<Boolean> notifications = new Option("Notifications", Boolean.valueOf(true));
/*   75 */   public static final Option<Boolean> moduleNotification = new Option("ModNotifications", Boolean.valueOf(true));
/*      */   
/*   77 */   public static final Option<Boolean> sound = new Option("Sounds", Boolean.valueOf(false));
/*      */   
/*      */   public static HUD getInstance;
/*      */   private static int count;
/*   81 */   public final Option<Boolean> music = new Option("MusicPlay", Boolean.valueOf(false));
/*      */   
/*   83 */   public final String[] suffixModes = new String[] { "Simple", "Box", "Hyphen", "VerticalBar", "XML", "Brackets", "Binds" };
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*   88 */   public final Option<Boolean> tabGui = new Option("TabGui", Boolean.valueOf(true));
/*      */   
/*   90 */   public final String[] Fonts = new String[] { "Regular", "SFUI", "Sans", "Roboto", "Baloo" };
/*      */ 
/*      */ 
/*      */   
/*   94 */   public final Mode<String> UIFont = new Mode("Font", this.Fonts, "Sans");
/*   95 */   public final Option<Boolean> fontNoShadow = new Option("NoShadow", Boolean.valueOf(true)); public final Option<Boolean> fontLowerCase = new Option("LowerCase", 
/*   96 */       Boolean.valueOf(false));
/*      */   
/*   98 */   public final String[] ArrayLists = new String[] { "Simple", "Airline", "Airempty", "Rectround", "Rect", "Outline", "Sidebar", "AirSidebar", "Cleanbar" };
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  104 */   public final Option<Boolean> playerHUD = new Option("PlayerHUD", 
/*  105 */       Boolean.valueOf(true));
/*  106 */   public final Option<Boolean> noRenderType = new Option("NoRenders", Boolean.valueOf(true));
/*  107 */   public final Option<Boolean> clearCategory = new Option("NoOthers", Boolean.valueOf(false));
/*  108 */   public final Option<Boolean> blur = new Option("Blur", Boolean.valueOf(false));
/*  109 */   public final Option<Boolean> shadow = new Option("Shadow", Boolean.valueOf(false));
/*      */   
/*  111 */   private final String[] waterMarkModes = new String[] { "Text", "Big", "Fade", "Frame", "FadeNoDate", "Sense", "Weave", "RoundOutline", "Round", "Virtue" };
/*      */ 
/*      */ 
/*      */   
/*  115 */   private final Option<Boolean> waterMark = new Option("WaterMark", Boolean.valueOf(true));
/*  116 */   public final Mode<String> waterMarks = new Mode("WaterMarks", this.waterMarkModes, "Big", this.waterMark::get);
/*  117 */   private final Mode<String> arrayList = new Mode("ArrayList", this.ArrayLists, "Cleanbar");
/*      */   
/*  119 */   private final CFontRenderer sencefont = Client.instance.FontLoaders.regular14; private final CFontRenderer regular16 = Client.instance.FontLoaders.regular16; private final CFontRenderer regular20 = Client.instance.FontLoaders.regular20; private final CFontRenderer regular17 = Client.instance.FontLoaders.regular17; private final CFontRenderer regular24 = Client.instance.FontLoaders.regular24; public int alphaScaffold;
/*      */   public int color;
/*      */   public float y;
/*      */   public float hue;
/*      */   public float ychat;
/*  124 */   private final TrueTypeFontDrawer smoothChinese17 = FontManager.default18;
/*      */   public float textAnimtionY;
/*  126 */   public float a = -40.0F; public final Option<Boolean> suffixUI = new Option("Suffix", Boolean.valueOf(true)); public final Option<Boolean> suffixWhite = new Option("SuffixWhite", 
/*  127 */       Boolean.valueOf(true), this.suffixUI::get);
/*  128 */   int x2 = 0;
/*  129 */   private CFontRenderer arrayFont = this.regular17;
/*      */   public HUD() {
/*  131 */     super("Interface", new String[] { "HUD" }, ModuleType.Render);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1235 */     this.suffixMode = new Mode("Suffixs", this.suffixModes, this.suffixModes[0], this.suffixUI::get);
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1240 */     this
/* 1241 */       .information = new Option("Information", Boolean.valueOf(true)); this.showFPS = new Option("ShowFPS", Boolean.valueOf(true), this.information::get);
/* 1242 */     this.showUser = new Option("ShowUser", Boolean.valueOf(false), this.information::get); this.showVia = new Option("ShowVia", Boolean.valueOf(false), this.information::get);
/*      */     addSettings(new Value[] { 
/*      */           (Value)this.arrayList, (Value)this.UIFont, (Value)rainbowMode, (Value)this.waterMarks, (Value)this.suffixMode, (Value)this.suffixUI, (Value)this.suffixWhite, (Value)this.information, (Value)this.showFPS, (Value)this.showUser, 
/*      */           (Value)this.showVia, (Value)this.noRenderType, (Value)this.clearCategory, (Value)this.fontNoShadow, (Value)this.fontLowerCase, (Value)this.waterMark, (Value)dynamicColor, (Value)notifications, (Value)moduleNotification, (Value)this.playerHUD, 
/*      */           (Value)rainbow, (Value)r, (Value)g, (Value)b, (Value)alpha, (Value)this.blur, (Value)this.shadow, (Value)sound, (Value)this.music, (Value)this.tabGui });
/*      */     getInstance = this;
/*      */     setEnabledByConvention(true);
/*      */   }
/*      */   
/*      */   public final Mode<String> suffixMode;
/*      */   public final Option<Boolean> information;
/*      */   public final Option<Boolean> showFPS;
/*      */   public final Option<Boolean> showUser;
/*      */   public final Option<Boolean> showVia;
/*      */   
/*      */   private void showOther(EventRender2D event) {
/*      */     if (mc == null || mc.gameSettings.showDebugInfo || mc.theWorld == null || mc.thePlayer == null)
/*      */       return; 
/*      */     if (((Boolean)this.tabGui.get()).booleanValue())
/*      */       Client.instance.sideTabGui.renderTabGui(event); 
/*      */     Scaffold.getInstance.renderBlockCount(event.getResolution());
/*      */     if (((Boolean)this.music.get()).booleanValue()) {
/*      */       int x2 = 0;
/*      */       for (Module m : Client.instance.getModuleManager().getModules()) {
/*      */         if (x2 < 64) {
/*      */           m.musicAnim2 = AnimationUtil.moveUD(m.musicAnim2, MathUtil.getRandomInRange(-30.0F, 63.0F), SimpleRender.processFPS(0.006875F), SimpleRender.processFPS(0.0045F));
/*      */           Gui.drawRect((x2 * 6), (400.0F + m.musicAnim2 * 5.0F), (6 + x2 * 6), event.getWidth(), (new Color(0, 0, 0, 100)).getRGB());
/*      */         } 
/*      */         x2++;
/*      */       } 
/*      */       RenderUtil.drawImage(new ResourceLocation("client/music.png"), 2.0F, (event.getResolution().getScaledHeight() - 66), 64.0F, 64.0F);
/*      */       Client.instance.FontLoaders.regular26.drawString("Ransom (Remix)", 70.0F, (event.getResolution().getScaledHeight() - 60), -1);
/*      */       Client.instance.FontLoaders.regular20.drawString("Lil Tecca / Juice WRLD", 70.0F, (event.getResolution().getScaledHeight() - 40), new Color(233, 233, 233, 200));
/*      */     } 
/*      */     if (((Boolean)notifications.get()).booleanValue())
/*      */       ClientNotification.drawNotifications(event.getResolution(), false); 
/*      */     if (((Boolean)moduleNotification.get()).booleanValue())
/*      */       ModNotification.drawNotifications(event.getResolution(), false); 
/*      */     if (Scaffold.getInstance.isEnabled()) {
/*      */       if (this.alphaScaffold <= 255)
/*      */         this.alphaScaffold += 5; 
/*      */     } else if (this.alphaScaffold >= 0) {
/*      */       this.alphaScaffold -= 5;
/*      */     } 
/*      */     GL11.glPopMatrix();
/*      */     if (((Boolean)KillAura.getInstance.checkWinning.get()).booleanValue()) {
/*      */       if (KillAura.getInstance.target != null) {
/*      */         this.a = AnimationUtil.moveUD(this.a, 40.0F);
/*      */       } else {
/*      */         this.a = AnimationUtil.moveUD(this.a, -40.0F);
/*      */       } 
/*      */       ScaledResolution sr = event.getResolution();
/*      */       String str = (KillAura.getInstance.target != null) ? ((KillAura.getInstance.target.getHealth() > mc.thePlayer.getHealth()) ? "Maybe you will lose" : "Maybe you will win") : "Unknown";
/*      */       String test2 = ClientSetting.getInstance.getCPS() + " CPS attacking";
/*      */       SimpleRender.drawRectFloat((sr.getScaledWidth() / 2 - 80), this.a, (sr.getScaledWidth() / 2 + 80), this.a + 30.0F, (new Color(0, 0, 0, 150)).getRGB());
/*      */       Client.instance.FontLoaders.Comfortaa22.drawString(str, (sr.getScaledWidth() / 2 - Client.instance.FontLoaders.Comfortaa22.getStringWidth(str) / 2), this.a + 5.0F, -1);
/*      */       Client.instance.FontLoaders.Comfortaa18.drawString(test2, (sr.getScaledWidth() / 2 - Client.instance.FontLoaders.Comfortaa18.getStringWidth(test2) / 2), this.a + 18.0F, (new Color(180, 180, 180)).getRGB());
/*      */     } 
/*      */     GL11.glPushMatrix();
/*      */   }
/*      */   
/*      */   @EventHandler
/*      */   private void onBloom(EventShader event) {
/*      */     if (mc == null || mc.gameSettings.showDebugInfo || mc.theWorld == null || mc.thePlayer == null)
/*      */       return; 
/*      */     if (!Shader.getInstance.isEnabled())
/*      */       return; 
/*      */     if (event.onBloom() && !((Boolean)this.shadow.getValue()).booleanValue())
/*      */       return; 
/*      */     if (event.onBlur() && !((Boolean)this.blur.getValue()).booleanValue())
/*      */       return; 
/*      */     if (((Boolean)notifications.get()).booleanValue())
/*      */       ClientNotification.drawNotifications(event.getResolution(), true); 
/*      */     if (((Boolean)moduleNotification.get()).booleanValue())
/*      */       ModNotification.drawNotifications(event.getResolution(), true); 
/*      */     if (((Boolean)this.music.get()).booleanValue()) {
/*      */       int x2 = 0;
/*      */       for (Module m : Client.instance.getModuleManager().getModules()) {
/*      */         if (x2 < 64)
/*      */           Gui.drawRect((x2 * 6), (400.0F + m.musicAnim2 * 5.0F), (6 + x2 * 6), event.getResolution().getScaledWidth(), Client.instance.getClientColor(255)); 
/*      */         x2++;
/*      */       } 
/*      */     } 
/*      */     if (((Boolean)this.tabGui.get()).booleanValue()) {
/*      */       GLUtils.resetColor();
/*      */       Client.instance.sideTabGui.renderTabGuiBloom(event);
/*      */     } 
/*      */     if (Chat.getInstance.isEnabled() && Chat.getInstance.backgroundShadow.is("Normal")) {
/*      */       GLUtils.resetColor();
/*      */       mc.ingameGUI.getChatGUI().renderChatBox();
/*      */     } 
/*      */     if (SetScoreboard.getInstance.isEnabled()) {
/*      */       GLUtils.resetColor();
/*      */       mc.ingameGUI.renderScoreboardBlur(event.getResolution());
/*      */     } 
/*      */     if (Chat.getInstance.isEnabled() && mc.currentScreen instanceof GuiChat) {
/*      */       int i = 320;
/*      */       int m = 40;
/*      */       int k = MathHelper.floor_float(mc.gameSettings.chatWidth * 280.0F + 40.0F);
/*      */       Gui.drawRect2(2.0D, (event.getResolution().getScaledHeight() - 14.0F * GuiChat.openingAnimation.getOutput().floatValue()), (k + 6), 12.0D, Color.BLACK.getRGB());
/*      */     } 
/*      */     Scaffold.getInstance.renderBlockCountBloom(event.getResolution());
/*      */     this.y = 1.0F;
/*      */     if (((Boolean)this.waterMark.get()).booleanValue())
/*      */       showWaterMarkBloom(); 
/*      */     ArrayList<Module> sorted = new ArrayList<>();
/*      */     for (Module m : Client.instance.getModuleManager().getModules()) {
/*      */       if ((!m.isEnabled() && m.wasArrayRemoved()) || m.wasHide() || (m.getModuleType() == ModuleType.Render && ((Boolean)this.noRenderType.get()).booleanValue()) || ((m.getModuleType() == ModuleType.Player || m.getModuleType() == ModuleType.World || m.getModuleType() == ModuleType.Misc) && ((Boolean)this.clearCategory.get()).booleanValue()))
/*      */         continue; 
/*      */       sorted.add(m);
/*      */     } 
/*      */     sorted.sort((o1, o2) -> this.arrayFont.getStringWidth(o2.getSuffix().isEmpty() ? o2.getHUDName() : String.format("%s %s", new Object[] { o2.getHUDName(), o2.getSuffix() })) - this.arrayFont.getStringWidth(o1.getSuffix().isEmpty() ? o1.getHUDName() : String.format("%s %s", new Object[] { o1.getHUDName(), o1.getSuffix() })));
/*      */     if (((Boolean)rainbow.get()).booleanValue())
/*      */       count = 0; 
/*      */     long j = Minecraft.getSystemTime();
/*      */     float globalY = 3.0F, globalX = 3.0F, needX = 7.0F, needY = -7.0F;
/*      */     for (Module m : sorted) {
/*      */       int nextIndex;
/*      */       Module nextModule;
/*      */       String name = m.getSuffix().isEmpty() ? m.getHUDName() : String.format("%s %s", new Object[] { m.getHUDName(), m.getSuffix(), Float.valueOf(m.getYAnim()) });
/*      */       if (!((Boolean)dynamicColor.get()).booleanValue()) {
/*      */         this.color = ((Boolean)rainbow.get()).booleanValue() ? rainbow() : HUDColor();
/*      */       } else if (((Boolean)rainbow.get()).booleanValue()) {
/*      */         this.color = rainbow();
/*      */       } else {
/*      */         this.color = getArrayDynamic((float)j, 255);
/*      */         j -= 300L;
/*      */       } 
/*      */       float x = this.arrayList.is("Outline") ? (RenderUtil.width() - 1.0F - m.getAnimX()) : (RenderUtil.width() - m.getAnimX());
/*      */       switch (this.arrayList.getModeAsString()) {
/*      */         case "Simple":
/*      */           if (((Double)alpha.get()).intValue() > 0)
/*      */             SimpleRender.drawRectFloat(x - 6.0F, m.getYAnim() - 4.0F, RenderUtil.width(), m.getYAnim() + 8.0F, Client.instance.getClientColor(255)); 
/*      */           break;
/*      */         case "Airline":
/*      */         case "Airempty":
/*      */           if (this.arrayList.is("AirLine"))
/*      */             SimpleRender.drawRectFloat((RenderUtil.width() - this.arrayFont.getStringWidth(name) - 11) - 3.0F, 7.0F, (RenderUtil.width() - 4) - 3.0F, 6.0F, ((Boolean)rainbow.get()).booleanValue() ? rainbow() : HUDColor()); 
/*      */           if (((Double)alpha.get()).intValue() > 0)
/*      */             SimpleRender.drawRectFloat(x - 11.0F - 3.0F, m.getYAnim() + 3.0F, x + this.arrayFont.getStringWidth(name) - 4.0F - 3.0F, m.getYAnim() + 12.0F + 3.0F, Client.instance.getClientColor(255)); 
/*      */           break;
/*      */         case "Rectround":
/*      */           if (((Double)alpha.get()).intValue() > 0)
/*      */             RenderUtil.drawRoundedRect(x - 8.0F, m.getYAnim() - 3.0F, (RenderUtil.width() + 2), m.getYAnim() + 11.0F - 3.0F, this.color); 
/*      */           RenderUtil.drawBorderedRect(x - 6.0F, m.getYAnim() + 4.0F - 6.0F, x - 6.0F, m.getYAnim() + 11.0F - 5.0F, 2.0F, this.color, this.color);
/*      */           break;
/*      */         case "Rect":
/*      */           if (((Double)alpha.get()).intValue() > 0)
/*      */             SimpleRender.drawRectFloat(x - 7.0F, m.getYAnim() - 4.0F, RenderUtil.width(), m.getYAnim() + 8.0F, Client.instance.getClientColor(255)); 
/*      */           SimpleRender.drawRectFloat(x - 8.0F, m.getYAnim() - 4.0F, x - 7.0F, m.getYAnim() + 8.0F, this.color);
/*      */           break;
/*      */         case "Outline":
/*      */           nextIndex = sorted.indexOf(m) + 1;
/*      */           nextModule = null;
/*      */           if (sorted.size() > nextIndex)
/*      */             nextModule = getNextEnabledModule(sorted, nextIndex); 
/*      */           if (((Double)alpha.get()).intValue() > 0)
/*      */             SimpleRender.drawRectFloat(x - 6.0F, m.getYAnim() - 4.0F, RenderUtil.width(), m.getYAnim() + 8.0F, Client.instance.getClientColor(255)); 
/*      */           SimpleRender.drawRectFloat(x - 7.0F, m.getYAnim() - 4.0F, x - 6.0F, m.getYAnim() + 8.0F, this.color);
/*      */           if (nextModule != null) {
/*      */             if (!getNextEnabledModule(sorted, nextIndex).getSuffix().isEmpty()) {
/*      */               SimpleRender.drawRectFloat(x - 7.0F, m.getYAnim() + 8.0F, x + this.arrayFont.getStringWidth(name) - this.arrayFont.getStringWidth(getNextEnabledModule(sorted, nextIndex).getHUDName() + " " + getNextEnabledModule(sorted, nextIndex).getSuffix()) - 7.0F, m.getYAnim() + 9.0F, this.color);
/*      */               break;
/*      */             } 
/*      */             SimpleRender.drawRectFloat(x - 7.0F, m.getYAnim() + 8.0F, x + this.arrayFont.getStringWidth(name) - this.arrayFont.getStringWidth(getNextEnabledModule(sorted, nextIndex).getHUDName()) - 7.0F, m.getYAnim() + 9.0F, this.color);
/*      */             break;
/*      */           } 
/*      */           SimpleRender.drawRectFloat(x - 7.0F, m.getYAnim() + 8.0F, RenderUtil.width(), m.getYAnim() + 9.0F, this.color);
/*      */           break;
/*      */         case "Sidebar":
/*      */           if (((Double)alpha.get()).intValue() > 0)
/*      */             SimpleRender.drawRectFloat(x - 9.0F, m.getYAnim() - 4.0F, RenderUtil.width(), m.getYAnim() + 8.0F, Client.instance.getClientColor(255)); 
/*      */           SimpleRender.drawRectFloat(x + this.arrayFont.getStringWidth(name), m.getYAnim() - 5.0F, x + this.arrayFont.getStringWidth(name) - 1.5F, m.getYAnim() + 8.0F, Client.instance.getClientColor(255));
/*      */           break;
/*      */         case "AirSidebar":
/*      */           if (((Double)alpha.get()).intValue() > 0)
/*      */             SimpleRender.drawRectFloat(x - 11.0F - 3.0F, m.getYAnim() + 3.0F, x + this.arrayFont.getStringWidth(name) - 4.0F - 3.0F, m.getYAnim() + 12.0F + 3.0F, Client.instance.getClientColor(255)); 
/*      */           SimpleRender.drawRectFloat(x + this.arrayFont.getStringWidth(name) - 7.0F, m.getYAnim() - 5.0F - -7.0F + 1.0F, x + this.arrayFont.getStringWidth(name) - 1.5F - 7.0F, m.getYAnim() + 8.0F - -7.0F, Client.instance.getClientColor(255));
/*      */           break;
/*      */         case "Cleanbar":
/*      */           if (((Double)alpha.get()).intValue() > 0)
/*      */             SimpleRender.drawRectFloat(x - 11.0F - 3.0F, m.getYAnim() + 3.0F, x + this.arrayFont.getStringWidth(name) - 4.0F - 3.0F, m.getYAnim() + 12.0F + 3.0F, Client.instance.getClientColor(255)); 
/*      */           SimpleRender.drawRectFloat(x + this.arrayFont.getStringWidth(name) - 7.0F + 0.5F, m.getYAnim() - 4.0F - -7.0F + 1.0F, x + this.arrayFont.getStringWidth(name) - 1.0F - 7.0F, m.getYAnim() + 7.0F - -7.0F, Client.instance.getClientColor(255));
/*      */           break;
/*      */       } 
/*      */       this.y += 12.0F;
/*      */     } 
/*      */   }
/*      */   
/*      */   @EventHandler
/*      */   public void on2d(EventRender2D event) {
/*      */     if (mc == null || mc.gameSettings.showDebugInfo || mc.theWorld == null || mc.thePlayer == null)
/*      */       return; 
/*      */     if (mc.thePlayer.ticksExisted % 4 == 0) {
/*      */       setSuffix((Serializable)this.arrayList.get());
/*      */       if (this.UIFont.is("Regular")) {
/*      */         this.arrayFont = Client.instance.FontLoaders.regular17;
/*      */       } else if (this.UIFont.is("SFUI")) {
/*      */         this.arrayFont = Client.instance.FontLoaders.SF18;
/*      */       } else if (this.UIFont.is("Roboto")) {
/*      */         this.arrayFont = Client.instance.FontLoaders.Roboto18;
/*      */       } else if (this.UIFont.is("Baloo")) {
/*      */         this.arrayFont = Client.instance.FontLoaders.HUDCu16;
/*      */       } else if (this.UIFont.is("Sans")) {
/*      */         this.arrayFont = Client.instance.FontLoaders.Sans18;
/*      */       } 
/*      */     } 
/*      */     showOther(event);
/*      */     this.y = 1.0F;
/*      */     if (((Boolean)this.waterMark.get()).booleanValue())
/*      */       showWaterMarkModule(); 
/*      */     ArrayList<Module> sorted = new ArrayList<>();
/*      */     for (Module m : Client.instance.getModuleManager().getModules()) {
/*      */       if ((!m.isEnabled() && m.wasArrayRemoved()) || m.wasHide() || (m.getModuleType() == ModuleType.Render && ((Boolean)this.noRenderType.get()).booleanValue()) || ((m.getModuleType() == ModuleType.Player || m.getModuleType() == ModuleType.World || m.getModuleType() == ModuleType.Misc) && ((Boolean)this.clearCategory.get()).booleanValue()))
/*      */         continue; 
/*      */       sorted.add(m);
/*      */     } 
/*      */     sorted.sort((o1, o2) -> this.arrayFont.getStringWidth(o2.getSuffix().isEmpty() ? o2.getHUDName() : String.format("%s %s", new Object[] { o2.getHUDName(), o2.getSuffix() })) - this.arrayFont.getStringWidth(o1.getSuffix().isEmpty() ? o1.getHUDName() : String.format("%s %s", new Object[] { o1.getHUDName(), o1.getSuffix() })));
/*      */     if (((Boolean)rainbow.get()).booleanValue())
/*      */       count = 0; 
/*      */     long j = Minecraft.getSystemTime();
/*      */     float offX = (this.arrayList.is("Airline") || this.arrayList.is("Airempty") || this.arrayList.is("AirSidebar") || this.arrayList.is("Cleanbar")) ? 20.0F : 7.0F;
/*      */     double clamp = MathHelper.clamp_double((Minecraft.getDebugFPS() / 30.0F / 2.0F), 1.0D, 9999.0D);
/*      */     float globalY = 3.0F, globalX = 3.0F, needX = 7.0F, needY = -7.0F;
/*      */     for (Module m : sorted) {
/*      */       int nextIndex;
/*      */       Module nextModule;
/*      */       String name = m.getSuffix().isEmpty() ? m.getHUDName() : String.format("%s %s", new Object[] { m.getHUDName(), m.getSuffix(), Float.valueOf(m.getYAnim()) });
/*      */       if (m.isEnabled()) {
/*      */         m.setArrayRemoved(false);
/*      */         m.animX = (float)(m.animX + (this.arrayFont.getStringWidth(name) - m.animX) * 0.20000000298023224D / clamp);
/*      */       } else if (m.animX < 0.0F - offX - 1.0F) {
/*      */         m.setArrayRemoved(true);
/*      */       } else if (m.animX > -2.0F - offX) {
/*      */         m.animX = (float)(m.animX + (-2.0F - offX - m.animX) * 0.20000000298023224D / clamp);
/*      */       } 
/*      */       m.animX = MathHelper.clamp_float(m.animX, -offX, this.arrayFont.getStringWidth(name));
/*      */       m.offsetY = (float)(m.offsetY + (this.y - m.offsetY + 3.0F) * 0.20000000298023224D / clamp);
/*      */       if (!((Boolean)dynamicColor.get()).booleanValue()) {
/*      */         this.color = ((Boolean)rainbow.get()).booleanValue() ? rainbow() : HUDColor();
/*      */       } else if (((Boolean)rainbow.get()).booleanValue()) {
/*      */         this.color = rainbow();
/*      */       } else {
/*      */         this.color = getArrayDynamic((float)j, 255);
/*      */         j -= 300L;
/*      */       } 
/*      */       float x = this.arrayList.is("Outline") ? (RenderUtil.width() - 1.0F - m.getAnimX()) : (RenderUtil.width() - m.getAnimX());
/*      */       switch (this.arrayList.getModeAsString()) {
/*      */         case "Simple":
/*      */           if (((Double)alpha.get()).intValue() > 0)
/*      */             SimpleRender.drawRectFloat(x - 6.0F, m.getYAnim() - 4.0F, RenderUtil.width(), m.getYAnim() + 8.0F, (new Color(0, 0, 0, ((Double)alpha.get()).intValue())).getRGB()); 
/*      */           this.arrayFont.HUDDrawString(name, x - 3.0F, m.getYAnim() + (this.UIFont.is("Regular") ? 7.0F : 6.0F) - this.arrayFont.getStringHeight(), this.color);
/*      */           break;
/*      */         case "Airline":
/*      */         case "Airempty":
/*      */           if (this.arrayList.is("AirLine"))
/*      */             SimpleRender.drawRectFloat((RenderUtil.width() - this.arrayFont.getStringWidth(name) - 11) - 3.0F, 7.0F, (RenderUtil.width() - 4) - 3.0F, 6.0F, ((Boolean)rainbow.get()).booleanValue() ? rainbow() : HUDColor()); 
/*      */           if (((Double)alpha.get()).intValue() > 0)
/*      */             SimpleRender.drawRectFloat(x - 11.0F - 3.0F, m.getYAnim() + 3.0F, x + this.arrayFont.getStringWidth(name) - 4.0F - 3.0F, m.getYAnim() + 12.0F + 3.0F, (new Color(0, 0, 0, ((Double)alpha.get()).intValue())).getRGB()); 
/*      */           this.arrayFont.HUDDrawString(name, x - 8.0F - 3.0F, m.getYAnim() + 3.0F + (this.UIFont.is("Regular") ? 11.0F : 10.0F) - this.arrayFont.getStringHeight(), this.color);
/*      */           break;
/*      */         case "Rectround":
/*      */           if (((Double)alpha.get()).intValue() > 0)
/*      */             RenderUtil.drawRoundedRect(x - 8.0F, m.getYAnim() - 3.0F, (RenderUtil.width() + 2), m.getYAnim() + 11.0F - 3.0F, (new Color(0, 0, 0, ((Double)alpha.get()).intValue())).getRGB()); 
/*      */           this.arrayFont.HUDDrawString(name, x - 3.0F, m.getYAnim() + (this.UIFont.is("Regular") ? 7.0F : 6.0F) - this.arrayFont.getStringHeight(), this.color);
/*      */           RenderUtil.drawBorderedRect(x - 6.0F, m.getYAnim() + 4.0F - 6.0F, x - 6.0F, m.getYAnim() + 11.0F - 5.0F, 2.0F, this.color, this.color);
/*      */           break;
/*      */         case "Rect":
/*      */           if (((Double)alpha.get()).intValue() > 0)
/*      */             SimpleRender.drawRectFloat(x - 7.0F, m.getYAnim() - 4.0F, RenderUtil.width(), m.getYAnim() + 8.0F, (new Color(0, 0, 0, ((Double)alpha.get()).intValue())).getRGB()); 
/*      */           this.arrayFont.HUDDrawString(name, x - 3.0F, m.getYAnim() + (this.UIFont.is("Regular") ? 7.0F : 6.0F) - this.arrayFont.getStringHeight(), this.color);
/*      */           SimpleRender.drawRectFloat(x - 8.0F, m.getYAnim() - 4.0F, x - 7.0F, m.getYAnim() + 8.0F, this.color);
/*      */           break;
/*      */         case "Outline":
/*      */           nextIndex = sorted.indexOf(m) + 1;
/*      */           nextModule = null;
/*      */           if (sorted.size() > nextIndex)
/*      */             nextModule = getNextEnabledModule(sorted, nextIndex); 
/*      */           if (((Double)alpha.get()).intValue() > 0)
/*      */             SimpleRender.drawRectFloat(x - 6.0F, m.getYAnim() - 4.0F, RenderUtil.width(), m.getYAnim() + 8.0F, (new Color(0, 0, 0, ((Double)alpha.get()).intValue())).getRGB()); 
/*      */           SimpleRender.drawRectFloat(x - 7.0F, m.getYAnim() - 4.0F, x - 6.0F, m.getYAnim() + 8.0F, this.color);
/*      */           if (nextModule != null) {
/*      */             if (!getNextEnabledModule(sorted, nextIndex).getSuffix().isEmpty()) {
/*      */               SimpleRender.drawRectFloat(x - 7.0F, m.getYAnim() + 8.0F, x + this.arrayFont.getStringWidth(name) - this.arrayFont.getStringWidth(getNextEnabledModule(sorted, nextIndex).getHUDName() + " " + getNextEnabledModule(sorted, nextIndex).getSuffix()) - 7.0F, m.getYAnim() + 9.0F, this.color);
/*      */             } else {
/*      */               SimpleRender.drawRectFloat(x - 7.0F, m.getYAnim() + 8.0F, x + this.arrayFont.getStringWidth(name) - this.arrayFont.getStringWidth(getNextEnabledModule(sorted, nextIndex).getHUDName()) - 7.0F, m.getYAnim() + 9.0F, this.color);
/*      */             } 
/*      */           } else {
/*      */             SimpleRender.drawRectFloat(x - 7.0F, m.getYAnim() + 8.0F, RenderUtil.width(), m.getYAnim() + 9.0F, this.color);
/*      */           } 
/*      */           this.arrayFont.HUDDrawString(name, x - 2.5F, m.getYAnim() + (this.UIFont.is("Regular") ? 7.0F : 6.0F) - this.arrayFont.getStringHeight(), this.color);
/*      */           break;
/*      */         case "Sidebar":
/*      */           if (((Double)alpha.get()).intValue() > 0)
/*      */             SimpleRender.drawRectFloat(x - 9.0F, m.getYAnim() - 4.0F, RenderUtil.width(), m.getYAnim() + 8.0F, (new Color(0, 0, 0, ((Double)alpha.get()).intValue())).getRGB()); 
/*      */           this.arrayFont.HUDDrawString(name, x - 5.5F, m.getYAnim() + (this.UIFont.is("Regular") ? 7.0F : 6.0F) - this.arrayFont.getStringHeight(), this.color);
/*      */           SimpleRender.drawRectFloat(x + this.arrayFont.getStringWidth(name), m.getYAnim() - 5.0F, x + this.arrayFont.getStringWidth(name) - 1.5F, m.getYAnim() + 8.0F, Client.instance.getClientColor(255));
/*      */           break;
/*      */         case "AirSidebar":
/*      */           if (((Double)alpha.get()).intValue() > 0)
/*      */             SimpleRender.drawRectFloat(x - 11.0F - 3.0F, m.getYAnim() + 3.0F, x + this.arrayFont.getStringWidth(name) - 4.0F - 3.0F, m.getYAnim() + 12.0F + 3.0F, (new Color(0, 0, 0, ((Double)alpha.get()).intValue())).getRGB()); 
/*      */           this.arrayFont.HUDDrawString(name, x - 8.0F - 3.0F, m.getYAnim() + 3.0F + (this.UIFont.is("Regular") ? 11.0F : 10.0F) - this.arrayFont.getStringHeight(), this.color);
/*      */           SimpleRender.drawRectFloat(x + this.arrayFont.getStringWidth(name) - 7.0F, m.getYAnim() - 5.0F - -7.0F + 1.0F, x + this.arrayFont.getStringWidth(name) - 1.5F - 7.0F, m.getYAnim() + 8.0F - -7.0F, Client.instance.getClientColor(255));
/*      */           break;
/*      */         case "Cleanbar":
/*      */           if (((Double)alpha.get()).intValue() > 0)
/*      */             SimpleRender.drawRectFloat(x - 11.0F - 3.0F, m.getYAnim() + 3.0F, x + this.arrayFont.getStringWidth(name) - 4.0F - 3.0F, m.getYAnim() + 12.0F + 3.0F, (new Color(0, 0, 0, ((Double)alpha.get()).intValue())).getRGB()); 
/*      */           this.arrayFont.HUDDrawString(name, x - 8.0F - 3.0F, m.getYAnim() + 3.0F + (this.UIFont.is("Regular") ? 11.0F : 10.0F) - this.arrayFont.getStringHeight(), this.color);
/*      */           SimpleRender.drawRectFloat(x + this.arrayFont.getStringWidth(name) - 7.0F + 0.5F, m.getYAnim() - 4.0F - -7.0F + 1.0F, x + this.arrayFont.getStringWidth(name) - 1.0F - 7.0F, m.getYAnim() + 7.0F - -7.0F, Client.instance.getClientColor(255));
/*      */           break;
/*      */       } 
/*      */       if (((Boolean)rainbow.get()).booleanValue()) {
/*      */         count += 3;
/*      */         if (count > 100)
/*      */           count = 0; 
/*      */       } 
/*      */       this.y += 12.0F;
/*      */     } 
/*      */     if (((Boolean)moduleNotification.get()).booleanValue() && mc.thePlayer.ticksExisted <= 10)
/*      */       ModNotification.clear(); 
/*      */     ScaledResolution sr = event.getResolution();
/*      */     if (((Boolean)this.information.get()).booleanValue()) {
/*      */       if (((Boolean)this.showVia.get()).booleanValue()) {
/*      */         String viaversion = "ViaVerison " + ProtocolUtils.getProtocolName(ViaFabric.clientSideVersion);
/*      */         char[] viainfos = viaversion.toCharArray();
/*      */         int viaVersionFadeLength = 0;
/*      */         for (char charindexviaversion : viainfos) {
/*      */           if (!((Boolean)dynamicColor.get()).booleanValue()) {
/*      */             this.regular17.drawStringWithShadowByCustom(String.valueOf(charindexviaversion), (sr.getScaledWidth() - this.regular17.getStringWidth(viaversion) - 2 + viaVersionFadeLength), (RenderUtil.height() - 12), ((Boolean)rainbow.get()).booleanValue() ? rainbow() : HUDColor());
/*      */           } else {
/*      */             Color Ranbow = fade(new Color(((Double)r.get()).intValue(), ((Double)g.get()).intValue(), ((Double)b.get()).intValue()), 65, viaVersionFadeLength + 39);
/*      */             this.regular17.drawStringWithShadowByCustom(String.valueOf(charindexviaversion), (sr.getScaledWidth() - this.regular17.getStringWidth(viaversion) - 2 + viaVersionFadeLength), (RenderUtil.height() - 12), ((Boolean)rainbow.get()).booleanValue() ? rainbow() : Ranbow.getRGB());
/*      */           } 
/*      */           viaVersionFadeLength += this.regular17.getCharWidth(charindexviaversion);
/*      */         } 
/*      */       } 
/*      */       if (((Boolean)this.showUser.get()).booleanValue()) {
/*      */         String userInformation = "User - " + VerifyData.instance.UserName;
/*      */         char[] userinfoCharArray = userInformation.toCharArray();
/*      */         this.textAnimtionY = AnimationUtil.moveUDSmooth(this.textAnimtionY, !((Boolean)this.showVia.get()).booleanValue() ? 12.0F : 25.0F);
/*      */         int userLength = 0;
/*      */         for (char charUser : userinfoCharArray) {
/*      */           if (!((Boolean)dynamicColor.get()).booleanValue()) {
/*      */             this.regular17.drawStringWithShadowByCustom(String.valueOf(charUser), (sr.getScaledWidth() - this.regular17.getStringWidth(userInformation) - 2 + userLength), (RenderUtil.height() - this.textAnimtionY), ((Boolean)rainbow.get()).booleanValue() ? rainbow() : HUDColor());
/*      */           } else {
/*      */             Color Ranbow = fade(new Color(((Double)r.get()).intValue(), ((Double)g.get()).intValue(), ((Double)b.get()).intValue()), 65, userLength + 39);
/*      */             this.regular17.drawStringWithShadowByCustom(String.valueOf(charUser), (sr.getScaledWidth() - this.regular17.getStringWidth(userInformation) - 2 + userLength), (RenderUtil.height() - this.textAnimtionY), ((Boolean)rainbow.get()).booleanValue() ? rainbow() : Ranbow.getRGB());
/*      */           } 
/*      */           userLength += this.regular17.getCharWidth(charUser);
/*      */         } 
/*      */       } 
/*      */       GlStateManager.pushMatrix();
/*      */       GlStateManager.translate(0.0F, -15.0F * GuiChat.openingAnimation.getOutput().floatValue(), 0.0F);
/*      */       if (((Boolean)this.showFPS.get()).booleanValue() && !((Boolean)this.music.get()).booleanValue()) {
/*      */         String fpsInformation = " FPS ";
/*      */         char[] fpsInformationCharArray = " FPS ".toCharArray();
/*      */         int fpsLength = 0;
/*      */         for (char charFPS : fpsInformationCharArray) {
/*      */           if (!((Boolean)dynamicColor.get()).booleanValue()) {
/*      */             this.regular17.drawStringWithShadowByCustom(String.valueOf(charFPS), (2 + fpsLength), (RenderUtil.height() - 25), ((Boolean)rainbow.get()).booleanValue() ? rainbow() : HUDColor());
/*      */           } else {
/*      */             Color Ranbow = fade(new Color(((Double)r.get()).intValue(), ((Double)g.get()).intValue(), ((Double)b.get()).intValue()), 65, fpsLength + 39);
/*      */             this.regular17.drawStringWithShadowByCustom(String.valueOf(charFPS), (2 + fpsLength), (RenderUtil.height() - 25), ((Boolean)rainbow.get()).booleanValue() ? rainbow() : Ranbow.getRGB());
/*      */           } 
/*      */           fpsLength += this.regular17.getCharWidth(charFPS);
/*      */         } 
/*      */         this.regular17.drawStringWithShadowByCustom(EnumChatFormatting.GRAY + String.valueOf(getFPS()), (2 + this.regular17.getStringWidth(" FPS ")), (RenderUtil.height() - 25), -1);
/*      */       } 
/*      */       if (!((Boolean)this.music.get()).booleanValue()) {
/*      */         String xyzs = " XYZ ";
/*      */         String xyz2 = String.valueOf(EnumChatFormatting.GRAY) + MathHelper.floor_double(mc.thePlayer.posX) + " " + EnumChatFormatting.GRAY + EnumChatFormatting.GRAY + MathHelper.floor_double(mc.thePlayer.posY) + " " + EnumChatFormatting.GRAY + EnumChatFormatting.GRAY + MathHelper.floor_double(mc.thePlayer.posZ);
/*      */         char[] xyzsCharArray = " XYZ ".toCharArray();
/*      */         int xyzlength = 0;
/*      */         for (char xyzChar : xyzsCharArray) {
/*      */           if (!((Boolean)dynamicColor.get()).booleanValue()) {
/*      */             this.regular17.drawStringWithShadowByCustom(String.valueOf(xyzChar), (2 + xyzlength), (RenderUtil.height() - 12), ((Boolean)rainbow.get()).booleanValue() ? rainbow() : HUDColor());
/*      */           } else {
/*      */             Color Ranbow = fade(new Color(((Double)r.get()).intValue(), ((Double)g.get()).intValue(), ((Double)b.get()).intValue()), 65, xyzlength + 39);
/*      */             this.regular17.drawStringWithShadowByCustom(String.valueOf(xyzChar), (2 + xyzlength), (RenderUtil.height() - 12), ((Boolean)rainbow.get()).booleanValue() ? rainbow() : Ranbow.getRGB());
/*      */           } 
/*      */           xyzlength += this.regular17.getCharWidth(xyzChar);
/*      */         } 
/*      */         this.regular17.drawStringWithShadowByCustom(xyz2, (2 + this.regular17.getStringWidth(" XYZ ")), (RenderUtil.height() - 12), Color.GRAY.getRGB());
/*      */       } 
/*      */       GlStateManager.popMatrix();
/*      */     } 
/*      */     if (((Boolean)this.playerHUD.get()).booleanValue()) {
/*      */       renderArmor(sr);
/*      */       drawPotionStatus(sr);
/*      */     } 
/*      */   }
/*      */   
/*      */   private void renderItem(ItemStack itemStack, int x, int y) {
/*      */     if (itemStack == null)
/*      */       return; 
/*      */     GlStateManager.pushMatrix();
/*      */     RenderHelper.enableGUIStandardItemLighting();
/*      */     mc.getRenderItem().renderItemAndEffectIntoGUI(itemStack, x, y);
/*      */     mc.getRenderItem().renderItemOverlays(mc.fontRendererObj, itemStack, x, y);
/*      */     GlStateManager.enableAlpha();
/*      */     GlStateManager.disableBlend();
/*      */     RenderHelper.disableStandardItemLighting();
/*      */     GlStateManager.popMatrix();
/*      */   }
/*      */   
/*      */   public void renderArmor(ScaledResolution sr) {
/*      */     int x = sr.getScaledWidth() / 2 + 10, y = sr.getScaledHeight() - 55;
/*      */     for (int i = 3; i >= 0; i--) {
/*      */       ItemStack itemStack1 = mc.thePlayer.inventory.armorItemInSlot(i);
/*      */       renderItem(itemStack1, x, y);
/*      */       x += 17;
/*      */     } 
/*      */     ItemStack itemStack = mc.thePlayer.getCurrentEquippedItem();
/*      */     renderItem(itemStack, x, y);
/*      */   }
/*      */   
/*      */   public int getFPS() {
/*      */     return FakeFPSBro.getInstance.isEnabled() ? (Minecraft.getDebugFPS() << 1) : Minecraft.getDebugFPS();
/*      */   }
/*      */   
/*      */   private Module getNextEnabledModule(List<? extends Module> modules, int startingIndex) {
/*      */     if (this.arrayList.is("OutLine")) {
/*      */       int modulesSize = modules.size();
/*      */       for (int i = startingIndex; i < modulesSize; i++) {
/*      */         Module module = modules.get(i);
/*      */         if (module.isEnabled())
/*      */           return module; 
/*      */       } 
/*      */     } 
/*      */     return null;
/*      */   }
/*      */   
/*      */   private void drawPotionStatus(ScaledResolution sr) {
/*      */     float y2 = 7.0F;
/*      */     for (PotionEffect effect : mc.thePlayer.getActivePotionEffects()) {
/*      */       Potion potion = Potion.potionTypes[effect.getPotionID()];
/*      */       StringBuilder PType = new StringBuilder(I18n.format(potion.getName(), new Object[0]));
/*      */       switch (effect.getAmplifier()) {
/*      */         case 1:
/*      */           PType.append(" II");
/*      */           break;
/*      */         case 2:
/*      */           PType.append(" III");
/*      */           break;
/*      */         case 3:
/*      */           PType.append(" IV");
/*      */           break;
/*      */         case 4:
/*      */           PType.append(" V");
/*      */           break;
/*      */       } 
/*      */       StringBuilder PType2 = new StringBuilder();
/*      */       if (effect.getDuration() < 600 && effect.getDuration() > 300) {
/*      */         PType2.append("§7§6 ").append(Potion.getDurationString(effect));
/*      */       } else if (effect.getDuration() < 300) {
/*      */         PType2.append("§7§c ").append(Potion.getDurationString(effect));
/*      */       } else if (effect.getDuration() > 600) {
/*      */         PType2.append("§7§7 ").append(Potion.getDurationString(effect));
/*      */       } 
/*      */       String potionInfo = String.valueOf(PType);
/*      */       char[] charPotionInfo = potionInfo.toCharArray();
/*      */       int potionInfoLength = 0;
/*      */       for (char charIndexCharPotionInfo : charPotionInfo) {
/*      */         if (!((Boolean)dynamicColor.get()).booleanValue()) {
/*      */           this.smoothChinese17.drawString(String.valueOf(charIndexCharPotionInfo), (sr.getScaledWidth() / 2 + 95 - 1 + potionInfoLength), ((sr.getScaledHeight() - this.smoothChinese17.getHeight()) + y2 - 15.0F - this.ychat + 2.0F), ((Boolean)rainbow.get()).booleanValue() ? rainbow() : HUDColor());
/*      */         } else {
/*      */           Color Ranbow = fade(new Color(((Double)r.get()).intValue(), ((Double)g.get()).intValue(), ((Double)b.get()).intValue()), 65, potionInfoLength + 65);
/*      */           this.smoothChinese17.drawString(String.valueOf(charIndexCharPotionInfo), (sr.getScaledWidth() / 2 + 95 - 1 + potionInfoLength), ((sr.getScaledHeight() - this.smoothChinese17.getHeight()) + y2 - 15.0F - this.ychat + 2.0F), ((Boolean)rainbow.get()).booleanValue() ? rainbow() : Ranbow.getRGB());
/*      */         } 
/*      */         potionInfoLength += this.smoothChinese17.getCharWidth(charIndexCharPotionInfo);
/*      */       } 
/*      */       this.smoothChinese17.drawString(String.valueOf(PType2), (sr.getScaledWidth() / 2 + 94 - 1 + potionInfoLength), ((sr.getScaledHeight() - this.smoothChinese17.getHeight()) + y2 - 15.0F - this.ychat + 2.0F), HUDColor());
/*      */       y2 -= 10.0F;
/*      */     } 
/*      */   }
/*      */   
/*      */   private int HUDColor() {
/*      */     return (new Color(((Double)r.get()).intValue(), ((Double)g.get()).intValue(), ((Double)b.get()).intValue())).getRGB();
/*      */   }
/*      */   
/*      */   private void showWaterMarkBloom() {
/*      */     if (this.waterMarks.is("RoundOutline") || this.waterMarks.is("Round")) {
/*      */       NetworkPlayerInfo info = mc.thePlayer.sendQueue.getPlayerInfo(mc.thePlayer.getUniqueID());
/*      */       int ping = (info != null) ? info.getResponseTime() : 0;
/*      */       String pings = ping + "ms";
/*      */       String text = "| " + (mc.isSingleplayer() ? "SinglePlayer" : (mc.getCurrentServerData()).serverIP) + " | " + getFPS() + "fps | " + pings;
/*      */       RenderUtil.roundHUD(1.5F, 5.0F, 57.0F + this.regular16.getStringWidth(Client.instance.hudName) + Client.instance.FontLoaders.regular15.getStringWidth(text), 1.0F, this.waterMarks.is("RoundOutline"), true);
/*      */     } else if (this.waterMarks.is("Weave")) {
/*      */       String text = Client.instance.hudName + " | " + getFPS() + "FPS | " + (int)mc.thePlayer.getHealth() + "HP | " + (int)MoveUtils.INSTANCE.getDirectionForAura() + " Yaw";
/*      */       RenderUtil.Weave(1.5F, 5.0F, 220.0F, 23.0F, 1.0F, this.regular16.getStringWidth(text), true);
/*      */       Gui.drawRect(0, 0, 0, 0, 0);
/*      */     } else if (this.waterMarks.is("Sense")) {
/*      */       this.hue += SimpleRender.processFPS(0.05F);
/*      */       if (this.hue > 255.0F)
/*      */         this.hue = 0.0F; 
/*      */       float h1 = this.hue + 200.0F, h2 = this.hue + 85.0F, h3 = this.hue + 170.0F;
/*      */       Color color33 = Color.getHSBColor(h1 / 255.0F, 0.9F, 1.0F);
/*      */       Color color332 = Color.getHSBColor(h2 / 255.0F, 0.9F, 1.0F);
/*      */       Color color333 = Color.getHSBColor(h3 / 255.0F, 0.9F, 1.0F);
/*      */       NetworkPlayerInfo info = mc.thePlayer.sendQueue.getPlayerInfo(mc.thePlayer.getUniqueID());
/*      */       int ping = (info != null) ? info.getResponseTime() : 0;
/*      */       String pings = ping + "ms";
/*      */       String text = Client.instance.hudName + ".§cng§  | " + mc.thePlayer.getName() + " | " + (mc.isSingleplayer() ? "SinglePlayer" : (mc.getCurrentServerData()).serverIP) + " | " + getFPS() + "fps | " + pings + " | 20.0ticks";
/*      */       if (((Boolean)rainbow.get()).booleanValue()) {
/*      */         RenderUtil.Gamesense(1.5F, 5.0F, (this.sencefont.getStringWidth(text) + 15), 23.0F, 1.0F, rainbowMode.is("Client") ? color332.getRGB() : color33.getRGB(), rainbowMode.is("Client") ? color333.getRGB() : color332.getRGB(), true);
/*      */       } else {
/*      */         RenderUtil.Gamesense(1.5F, 5.0F, (this.sencefont.getStringWidth(text) + 15), 23.0F, 1.0F, 0.0F, 0.0F, true);
/*      */       } 
/*      */       Gui.drawRect(0, 0, 0, 0, 0);
/*      */     } else if (this.waterMarks.is("Frame")) {
/*      */       RenderUtil.watermarkForFrame(5.0F, 3.0F, 219.0F, 26.0F, 1.0F);
/*      */     } 
/*      */   }
/*      */   
/*      */   private void showWaterMarkModule() {
/*      */     if (this.waterMarks.is("RoundOutline") || this.waterMarks.is("Round")) {
/*      */       NetworkPlayerInfo info = mc.thePlayer.sendQueue.getPlayerInfo(mc.thePlayer.getUniqueID());
/*      */       int ping = (info != null) ? info.getResponseTime() : 0;
/*      */       String pings = ping + "ms";
/*      */       String text = "| " + (mc.isSingleplayer() ? "SinglePlayer" : (mc.getCurrentServerData()).serverIP) + " | " + getFPS() + "fps | " + pings;
/*      */       RenderUtil.roundHUD(1.5F, 5.0F, 57.0F + this.regular16.getStringWidth(Client.instance.hudName) + Client.instance.FontLoaders.regular15.getStringWidth(text), 1.0F, this.waterMarks.is("RoundOutline"), false);
/*      */       char[] hudNameCharArray = Client.instance.hudName.toCharArray();
/*      */       int length = 0;
/*      */       for (char c : hudNameCharArray) {
/*      */         if (!((Boolean)dynamicColor.get()).booleanValue()) {
/*      */           this.regular16.drawString(String.valueOf(c), (8 + length), 15.5F - this.regular16.getStringHeight() / 2.0F, ((Boolean)rainbow.get()).booleanValue() ? rainbow() : HUDColor());
/*      */         } else {
/*      */           Color Ranbow = fade(new Color(((Double)r.get()).intValue(), ((Double)g.get()).intValue(), ((Double)b.get()).intValue()), 65, length + 80);
/*      */           this.regular16.drawString(String.valueOf(c), (8 + length), 15.5F - this.regular16.getStringHeight() / 2.0F, ((Boolean)rainbow.get()).booleanValue() ? rainbow() : Ranbow.getRGB());
/*      */         } 
/*      */         length += this.regular16.getCharWidth(c);
/*      */       } 
/*      */       Client.instance.FontLoaders.regular15.drawString(text, (8 + (Client.instance.hudName.equals("Awareline") ? 0 : 1) + this.regular16.getStringWidth(Client.instance.hudName)), 16.0F - this.regular16.getStringHeight() / 2.0F, -1);
/*      */     } else if (this.waterMarks.is("Weave")) {
/*      */       String text = Client.instance.hudName + " | " + getFPS() + "FPS | " + (int)mc.thePlayer.getHealth() + "HP | " + (int)MoveUtils.INSTANCE.getDirectionForAura() + " Yaw";
/*      */       RenderUtil.Weave(1.5F, 5.0F, 220.0F, 23.0F, 1.0F, this.regular16.getStringWidth(text), false);
/*      */       this.regular16.drawString(text, 9.0F, 15.0F - (this.regular16.getStringHeight() / 2), -1);
/*      */       Gui.drawRect(0, 0, 0, 0, 0);
/*      */     } else if (this.waterMarks.is("Sense")) {
/*      */       this.hue += SimpleRender.processFPS(0.05F);
/*      */       if (this.hue > 255.0F)
/*      */         this.hue = 0.0F; 
/*      */       float h1 = this.hue + 200.0F, h2 = this.hue + 85.0F, h3 = this.hue + 170.0F;
/*      */       Color color33 = Color.getHSBColor(h1 / 255.0F, 0.9F, 1.0F);
/*      */       Color color332 = Color.getHSBColor(h2 / 255.0F, 0.9F, 1.0F);
/*      */       Color color333 = Color.getHSBColor(h3 / 255.0F, 0.9F, 1.0F);
/*      */       NetworkPlayerInfo info = mc.thePlayer.sendQueue.getPlayerInfo(mc.thePlayer.getUniqueID());
/*      */       int ping = (info != null) ? info.getResponseTime() : 0;
/*      */       String pings = ping + "ms";
/*      */       String text = Client.instance.hudName + ".§cng§  | " + mc.thePlayer.getName() + " | " + (mc.isSingleplayer() ? "SinglePlayer" : (mc.getCurrentServerData()).serverIP) + " | " + getFPS() + "fps | " + pings + " | 20.0ticks";
/*      */       if (((Boolean)rainbow.get()).booleanValue()) {
/*      */         RenderUtil.Gamesense(1.5F, 5.0F, (this.sencefont.getStringWidth(text) + 15), 23.0F, 1.0F, rainbowMode.is("Client") ? color332.getRGB() : color33.getRGB(), rainbowMode.is("Client") ? color333.getRGB() : color332.getRGB(), false);
/*      */       } else {
/*      */         RenderUtil.Gamesense(1.5F, 5.0F, (this.sencefont.getStringWidth(text) + 15), 23.0F, 1.0F, 0.0F, 0.0F, false);
/*      */       } 
/*      */       Gui.drawRect(0, 0, 0, 0, 0);
/*      */       this.sencefont.drawCenteredString(text, (this.sencefont.getStringWidth(text) / 2 + 9) - 1.5D, 13.0D, (new Color(255, 255, 255)).getRGB());
/*      */     } else if (this.waterMarks.is("Text")) {
/*      */       String append5 = Client.instance.hudName.substring(0, 1);
/*      */       String append6 = Client.instance.hudName.substring(1);
/*      */       int x2 = 5;
/*      */       if (!((Boolean)dynamicColor.get()).booleanValue()) {
/*      */         this.regular20.drawStringWithShadow(append5, 5.0D, 6.0D, ((Boolean)rainbow.get()).booleanValue() ? rainbow() : HUDColor());
/*      */       } else {
/*      */         Color Ranbow = fade(new Color(((Double)r.get()).intValue(), ((Double)g.get()).intValue(), ((Double)b.get()).intValue()), 70, 25);
/*      */         this.regular20.drawStringWithShadow(append5, 5.0D, 6.0D, ((Boolean)rainbow.get()).booleanValue() ? rainbow() : Ranbow.getRGB());
/*      */       } 
/*      */       this.regular20.drawStringWithShadow(append6, (5.0F + (Client.instance.hudName.equals("Awareline") ? 1.0F : 1.5F) + this.arrayFont.getStringWidth(append5)), 6.0D, -1);
/*      */     } else if (this.waterMarks.is("Fade")) {
/*      */       Client.instance.FontLoaders.regular19.drawStringWithShadow(EnumChatFormatting.GRAY + " 搂7(搂r" + (new SimpleDateFormat("HH:mm")).format(new Date()) + "搂7)搂r", (3 + this.regular20.getStringWidth(Client.instance.hudName)), 7.0D, -1);
/*      */       char[] hudNameCharArray = Client.instance.hudName.toCharArray();
/*      */       int length = 0;
/*      */       for (char c : hudNameCharArray) {
/*      */         if (!((Boolean)dynamicColor.get()).booleanValue()) {
/*      */           this.regular20.drawStringWithShadow(String.valueOf(c), (5.0F + length), 6.0D, ((Boolean)rainbow.get()).booleanValue() ? rainbow() : HUDColor());
/*      */         } else {
/*      */           Color Ranbow = fade(new Color(((Double)r.get()).intValue(), ((Double)g.get()).intValue(), ((Double)b.get()).intValue()), 65, length + 80);
/*      */           this.regular20.drawStringWithShadow(String.valueOf(c), (5.0F + length), 6.0D, ((Boolean)rainbow.get()).booleanValue() ? rainbow() : Ranbow.getRGB());
/*      */         } 
/*      */         length += this.regular20.getCharWidth(c);
/*      */       } 
/*      */     } else if (this.waterMarks.is("FadeNoDate")) {
/*      */       char[] hudNameCharArray = Client.instance.hudName.toCharArray();
/*      */       int length = 0;
/*      */       for (char c : hudNameCharArray) {
/*      */         if (!((Boolean)dynamicColor.get()).booleanValue()) {
/*      */           this.regular20.drawStringWithShadow(String.valueOf(c), (5.0F + length), 6.0D, ((Boolean)rainbow.get()).booleanValue() ? rainbow() : HUDColor());
/*      */         } else {
/*      */           Color Ranbow = fade(new Color(((Double)r.get()).intValue(), ((Double)g.get()).intValue(), ((Double)b.get()).intValue()), 65, length + 80);
/*      */           this.regular20.drawStringWithShadow(String.valueOf(c), (5.0F + length), 6.0D, ((Boolean)rainbow.get()).booleanValue() ? rainbow() : Ranbow.getRGB());
/*      */         } 
/*      */         length += this.regular20.getCharWidth(c);
/*      */       } 
/*      */     } else if (this.waterMarks.is("Frame")) {
/*      */       RenderUtil.watermarkForFrame(5.0F, 3.0F, 219.0F, 26.0F, 1.0F);
/*      */       String substring = Client.instance.hudName.substring(0, 1);
/*      */       if (!((Boolean)dynamicColor.get()).booleanValue()) {
/*      */         this.regular24.drawString(substring, 10.0F, 15.0F - (this.regular24.getStringHeight() / 2), ((Boolean)rainbow.get()).booleanValue() ? rainbow() : HUDColor());
/*      */       } else {
/*      */         Color Ranbow = fade(new Color(((Double)r.get()).intValue(), ((Double)g.get()).intValue(), ((Double)b.get()).intValue()), 70, 25);
/*      */         this.regular24.drawString(substring, 10.0F, 15.0F - (this.regular24.getStringHeight() / 2), ((Boolean)rainbow.get()).booleanValue() ? rainbow() : Ranbow.getRGB());
/*      */       } 
/*      */       String append6 = Client.instance.hudName.substring(1);
/*      */       this.regular24.drawString(append6, 10.0F + this.regular24.getStringWidth(substring), 15.0F - (this.regular24.getStringHeight() / 2), -1);
/*      */     } else if (this.waterMarks.is("Big")) {
/*      */       String substring = Client.instance.hudName.substring(0, 1);
/*      */       if (!((Boolean)dynamicColor.get()).booleanValue()) {
/*      */         this.regular24.drawString(substring, 5.0F, 15.0F - (this.regular24.getStringHeight() / 2), ((Boolean)rainbow.get()).booleanValue() ? rainbow() : HUDColor());
/*      */       } else {
/*      */         Color Ranbow = fade(new Color(((Double)r.get()).intValue(), ((Double)g.get()).intValue(), ((Double)b.get()).intValue()), 70, 25);
/*      */         this.regular24.drawString(substring, 5.0F, 15.0F - (this.regular24.getStringHeight() / 2), ((Boolean)rainbow.get()).booleanValue() ? rainbow() : Ranbow.getRGB());
/*      */       } 
/*      */       String append6 = Client.instance.hudName.substring(1);
/*      */       this.regular24.drawString(append6, (5 + this.regular24.getStringWidth(substring)), 15.0F - (this.regular24.getStringHeight() / 2), -1);
/*      */     } else if (this.waterMarks.is("Virtue")) {
/*      */       RenderUtil.rectangleBordered(1.5D, 5.0D, 52.0D, 40.0D, 0.5D, (new Color(80, 80, 80, 150)).getRGB(), (new Color(20, 20, 20, 255)).getRGB());
/*      */       mc.fontRendererObj.drawStringWithShadow("Virtue 6", 7.5F, 8.0F, -1);
/*      */       mc.fontRendererObj.drawStringWithShadow(EnumChatFormatting.GRAY + "FPS: " + Minecraft.getDebugFPS(), 6.5F, 19.0F, -1);
/*      */       mc.fontRendererObj.drawStringWithShadow(EnumChatFormatting.GRAY + "Ver: 1.8.9", 4.0F, 29.0F, -1);
/*      */     } 
/*      */   }
/*      */   
/*      */   public int rainbow() {
/*      */     if (!isEnabled())
/*      */       return Color.MAGENTA.hashCode(); 
/*      */     if (!rainbowMode.is("Client") && !rainbowMode.is("Dark") && !rainbowMode.is("Random")) {
/*      */       if (rainbowMode.is("Sakura")) {
/*      */         double rainbowDelay = Math.ceil((System.currentTimeMillis() / 5L + ++count * -50L * 40L / 360L));
/*      */         rainbowDelay %= 360.0D;
/*      */         Color rainbowcolor = Color.getHSBColor((float)(rainbowDelay / 255.0D), 0.6F, 1.0F);
/*      */         return (new Color(rainbowcolor.getRed(), 90, 220)).getRGB();
/*      */       } 
/*      */       if (rainbowMode.is("Chemical")) {
/*      */         double rainbowDelay = Math.ceil((System.currentTimeMillis() / 5L + ++count * -50L * 40L / 360L));
/*      */         rainbowDelay %= 360.0D;
/*      */         Color rainbowcolor = Color.getHSBColor((float)(rainbowDelay / 255.0D), 0.6F, 1.0F);
/*      */         return (new Color(155, rainbowcolor.getGreen(), 246)).getRGB();
/*      */       } 
/*      */       if (rainbowMode.is("Mexico")) {
/*      */         double rainbowDelay = Math.ceil((System.currentTimeMillis() / 5L + ++count * -50L * 40L / 360L));
/*      */         rainbowDelay %= 360.0D;
/*      */         Color rainbowcolor = Color.getHSBColor((float)(rainbowDelay / 255.0D), 0.6F, 1.0F);
/*      */         return (new Color(rainbowcolor.getRed(), 0, rainbowcolor.getBlue())).getRGB();
/*      */       } 
/*      */       if (rainbowMode.is("Neon")) {
/*      */         double rainbowDelay = Math.ceil((System.currentTimeMillis() / 5L + ++count * -50L * 40L / 360L));
/*      */         rainbowDelay %= 360.0D;
/*      */         Color rainbowcolor = Color.getHSBColor((float)(rainbowDelay / 255.0D), 0.4F, 0.8F);
/*      */         return (new Color(rainbowcolor.getRed(), rainbowcolor.getGreen(), 255)).getRGB();
/*      */       } 
/*      */       if (rainbowMode.is("BLight")) {
/*      */         double rainbowDelay = Math.ceil((System.currentTimeMillis() / 5L + ++count * -50L * 40L / 360L));
/*      */         rainbowDelay %= 360.0D;
/*      */         Color rainbowcolor = Color.getHSBColor((float)(rainbowDelay / 255.0D), 0.5F, 0.8F);
/*      */         return (new Color(rainbowcolor.getGreen() - 90, rainbowcolor.getBlue(), rainbowcolor.getGreen())).getRGB();
/*      */       } 
/*      */       if (rainbowMode.is("Rise")) {
/*      */         double rainbowDelay = Math.ceil((System.currentTimeMillis() / 5L + ++count * -50L * 40L / 360L));
/*      */         rainbowDelay %= 360.0D;
/*      */         Color rainbowcolor = Color.getHSBColor((float)(120.0D + rainbowDelay / 255.0D), 0.4F, 0.8F);
/*      */         return (new Color(rainbowcolor.getRed() - 120, rainbowcolor.getBlue(), rainbowcolor.getRed())).getRGB();
/*      */       } 
/*      */       if (rainbowMode.is("Gothic")) {
/*      */         double rainbowDelay = Math.ceil((System.currentTimeMillis() / 5L + ++count * -50L * 40L / 360L));
/*      */         rainbowDelay %= 360.0D;
/*      */         Color rainbowcolor = Color.getHSBColor((float)(rainbowDelay / 255.0D), 0.4F, 0.8F);
/*      */         return (new Color(rainbowcolor.getRed() - 60, rainbowcolor.getBlue() - 113, rainbowcolor.getRed() - 9)).getRGB();
/*      */       } 
/*      */       if (rainbowMode.is("Destroyer")) {
/*      */         double rainbowDelay = Math.ceil((System.currentTimeMillis() / 5L + ++count * -50L * 40L / 360L));
/*      */         rainbowDelay %= 360.0D;
/*      */         Color rainbowcolor = Color.getHSBColor((float)(rainbowDelay / 255.0D), 0.4F, 0.8F);
/*      */         return (new Color(rainbowcolor.getBlue() - 79, rainbowcolor.getRed() - 79, rainbowcolor.getRed())).getRGB();
/*      */       } 
/*      */       if (rainbowMode.is("Astolfo")) {
/*      */         double rainbowDelay = Math.ceil(((System.currentTimeMillis() + (long)(++count * -50)) / 8L) + -2.5D);
/*      */         return Color.getHSBColor(((float)((rainbowDelay %= 360.0D) / 360.0D) < 0.5D) ? -((float)(rainbowDelay / 360.0D)) : (float)(rainbowDelay / 360.0D), 0.5F, 1.0F).getRGB();
/*      */       } 
/*      */       float f = 0.42F;
/*      */       double d = Math.ceil(((System.currentTimeMillis() + (long)(++count * -50)) / 8L) + -2.5D);
/*      */       d %= 360.0D;
/*      */       return Color.getHSBColor((float)(d / 360.0D), f, 1.0F).getRGB();
/*      */     } 
/*      */     float rainbowalpha = 0.0F;
/*      */     if (rainbowMode.is("Client"))
/*      */       rainbowalpha = 0.42F; 
/*      */     if (rainbowMode.is("Dark"))
/*      */       rainbowalpha = 0.7F; 
/*      */     if (rainbowMode.is("Random"))
/*      */       rainbowalpha = 5.0F; 
/*      */     double rainbowState = Math.ceil(((System.currentTimeMillis() + (long)(++count * -50)) / 8L) + -2.5D);
/*      */     rainbowState %= 360.0D;
/*      */     return Color.getHSBColor((float)(rainbowState / 360.0D), rainbowalpha, 1.0F).getRGB();
/*      */   }
/*      */   
/*      */   public Color fade(Color color, int index, int count) {
/*      */     float[] hsb = new float[3];
/*      */     Color.RGBtoHSB(color.getRed(), color.getGreen(), color.getBlue(), hsb);
/*      */     float brightness = Math.abs(((float)(System.currentTimeMillis() % 2000L) / 1000.0F + index / count * 2.0F) % 2.0F - 1.0F);
/*      */     brightness = 0.5F + 0.5F * brightness;
/*      */     hsb[2] = brightness % 2.0F;
/*      */     return new Color(Color.HSBtoRGB(hsb[0], hsb[1], hsb[2]));
/*      */   }
/*      */   
/*      */   public Color rainbowToEffect() {
/*      */     if (!getInstance.isEnabled())
/*      */       return Color.MAGENTA; 
/*      */     if (!rainbowMode.is("Client") && !rainbowMode.is("Dark") && !rainbowMode.is("Random")) {
/*      */       if (rainbowMode.is("Sakura")) {
/*      */         double rainbowDelay = Math.ceil((System.currentTimeMillis() / 5L + ++count * -50L * 40L / 360L));
/*      */         rainbowDelay %= 360.0D;
/*      */         Color rainbowcolor = Color.getHSBColor((float)(rainbowDelay / 255.0D), 0.6F, 1.0F);
/*      */         return new Color(rainbowcolor.getRed(), 90, 220);
/*      */       } 
/*      */       if (rainbowMode.is("Chemical")) {
/*      */         double rainbowDelay = Math.ceil((System.currentTimeMillis() / 5L + ++count * -50L * 40L / 360L));
/*      */         rainbowDelay %= 360.0D;
/*      */         Color rainbowcolor = Color.getHSBColor((float)(rainbowDelay / 255.0D), 0.6F, 1.0F);
/*      */         return new Color(155, rainbowcolor.getGreen(), 246);
/*      */       } 
/*      */       if (rainbowMode.is("Mexico")) {
/*      */         double rainbowDelay = Math.ceil((System.currentTimeMillis() / 5L + ++count * -50L * 40L / 360L));
/*      */         rainbowDelay %= 360.0D;
/*      */         Color rainbowcolor = Color.getHSBColor((float)(rainbowDelay / 255.0D), 0.6F, 1.0F);
/*      */         return new Color(rainbowcolor.getRed(), 0, rainbowcolor.getBlue());
/*      */       } 
/*      */       if (rainbowMode.is("Neon")) {
/*      */         double rainbowDelay = Math.ceil((System.currentTimeMillis() / 5L + ++count * -50L * 40L / 360L));
/*      */         rainbowDelay %= 360.0D;
/*      */         Color rainbowcolor = Color.getHSBColor((float)(rainbowDelay / 255.0D), 0.4F, 0.8F);
/*      */         return new Color(rainbowcolor.getRed(), rainbowcolor.getGreen(), 255);
/*      */       } 
/*      */       if (rainbowMode.is("Destroyer")) {
/*      */         double rainbowDelay = Math.ceil((System.currentTimeMillis() / 5L + ++count * -50L * 40L / 360L));
/*      */         rainbowDelay %= 360.0D;
/*      */         Color rainbowcolor = Color.getHSBColor((float)(rainbowDelay / 255.0D), 0.4F, 0.8F);
/*      */         return new Color(rainbowcolor.getBlue() - 79, rainbowcolor.getRed() - 79, rainbowcolor.getRed());
/*      */       } 
/*      */       if (rainbowMode.is("Gothic")) {
/*      */         double rainbowDelay = Math.ceil((System.currentTimeMillis() / 5L + ++count * -50L * 40L / 360L));
/*      */         rainbowDelay %= 360.0D;
/*      */         Color rainbowcolor = Color.getHSBColor((float)(rainbowDelay / 255.0D), 0.4F, 0.8F);
/*      */         return new Color(rainbowcolor.getRed() - 60, rainbowcolor.getBlue() - 113, rainbowcolor.getRed() - 9);
/*      */       } 
/*      */       if (rainbowMode.is("Rise")) {
/*      */         double rainbowDelay = Math.ceil((System.currentTimeMillis() / 5L + ++count * -50L * 40L / 360L));
/*      */         rainbowDelay %= 360.0D;
/*      */         Color rainbowcolor = Color.getHSBColor((float)(120.0D + rainbowDelay / 255.0D), 0.4F, 0.8F);
/*      */         return new Color(rainbowcolor.getRed() - 120, rainbowcolor.getBlue(), rainbowcolor.getRed());
/*      */       } 
/*      */       if (rainbowMode.is("BLight")) {
/*      */         double rainbowDelay = Math.ceil((System.currentTimeMillis() / 5L + ++count * -50L * 40L / 360L));
/*      */         rainbowDelay %= 360.0D;
/*      */         Color rainbowcolor = Color.getHSBColor((float)(rainbowDelay / 255.0D), 0.5F, 0.8F);
/*      */         return new Color(rainbowcolor.getGreen() - 90, rainbowcolor.getBlue(), rainbowcolor.getGreen());
/*      */       } 
/*      */       if (rainbowMode.is("Astolfo")) {
/*      */         double rainbowDelay = Math.ceil(((System.currentTimeMillis() + (long)(++count * -50)) / 8L) + -2.5D);
/*      */         return Color.getHSBColor(((float)((rainbowDelay %= 360.0D) / 360.0D) < 0.5D) ? -((float)(rainbowDelay / 360.0D)) : (float)(rainbowDelay / 360.0D), 0.5F, 1.0F);
/*      */       } 
/*      */       float f = 0.42F;
/*      */       double d = Math.ceil(((System.currentTimeMillis() + (long)(++count * -50)) / 8L) + -2.5D);
/*      */       d %= 360.0D;
/*      */       return Color.getHSBColor((float)(d / 360.0D), f, 1.0F);
/*      */     } 
/*      */     float rainbowalpha = 0.0F;
/*      */     if (rainbowMode.is("Client"))
/*      */       rainbowalpha = 0.42F; 
/*      */     if (rainbowMode.is("Dark"))
/*      */       rainbowalpha = 0.7F; 
/*      */     if (rainbowMode.is("Random"))
/*      */       rainbowalpha = 5.0F; 
/*      */     double rainbowState = Math.ceil(((System.currentTimeMillis() + (long)(++count * -50)) / 8L) + -2.5D);
/*      */     rainbowState %= 360.0D;
/*      */     return Color.getHSBColor((float)(rainbowState / 360.0D), rainbowalpha, 1.0F);
/*      */   }
/*      */   
/*      */   public int getArrayDynamic(float counter, int alpha) {
/*      */     float brightness = 1.0F - MathHelper.abs((float)(Math.sin((counter % 6000.0F / 6000.0F) * Math.PI * 2.0D) * 0.6000000238418579D));
/*      */     Color color1 = new Color(((Double)r.get()).intValue(), ((Double)g.get()).intValue(), ((Double)b.get()).intValue());
/*      */     float[] hudHSB = Color.RGBtoHSB(((Double)r.get()).intValue(), ((Double)g.get()).intValue(), ((Double)b.get()).intValue(), new float[3]);
/*      */     Color color = Color.getHSBColor(hudHSB[0], hudHSB[1], brightness);
/*      */     return (new Color(color.getRed(), color.getGreen(), color.getBlue(), alpha)).getRGB();
/*      */   }
/*      */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\awareline\main\mod\implement\visual\HUD.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */