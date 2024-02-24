/*      */ package net.minecraft.client;
/*      */ import awareline.antileak.JumpMainMenuCheck;
/*      */ import awareline.antileak.hwid.HWIDUtilsMCStart;
/*      */ import awareline.main.Client;
/*      */ import awareline.main.event.Event;
/*      */ import awareline.main.event.EventManager;
/*      */ import awareline.main.mod.implement.globals.MemoryFix;
/*      */ import awareline.main.ui.font.fontmanager.utils.StringUtils;
/*      */ import awareline.main.ui.gui.guimainmenu.mainmenu.ClientMainMenu;
/*      */ import com.google.common.collect.Lists;
/*      */ import com.google.common.util.concurrent.Futures;
/*      */ import com.google.common.util.concurrent.ListenableFuture;
/*      */ import com.google.common.util.concurrent.ListenableFutureTask;
/*      */ import com.mojang.authlib.GameProfile;
/*      */ import com.mojang.authlib.minecraft.MinecraftSessionService;
/*      */ import com.mojang.authlib.properties.PropertyMap;
/*      */ import java.awt.Color;
/*      */ import java.awt.image.BufferedImage;
/*      */ import java.io.BufferedReader;
/*      */ import java.io.File;
/*      */ import java.io.IOException;
/*      */ import java.io.InputStream;
/*      */ import java.io.InputStreamReader;
/*      */ import java.net.HttpURLConnection;
/*      */ import java.net.Proxy;
/*      */ import java.net.SocketAddress;
/*      */ import java.net.URL;
/*      */ import java.net.URLConnection;
/*      */ import java.nio.ByteBuffer;
/*      */ import java.nio.ByteOrder;
/*      */ import java.text.DecimalFormat;
/*      */ import java.util.Iterator;
/*      */ import java.util.List;
/*      */ import java.util.Map;
/*      */ import java.util.Set;
/*      */ import java.util.concurrent.Callable;
/*      */ import javax.imageio.ImageIO;
/*      */ import net.minecraft.block.Block;
/*      */ import net.minecraft.block.material.Material;
/*      */ import net.minecraft.client.audio.MusicTicker;
/*      */ import net.minecraft.client.audio.SoundHandler;
/*      */ import net.minecraft.client.entity.EntityPlayerSP;
/*      */ import net.minecraft.client.gui.FontRenderer;
/*      */ import net.minecraft.client.gui.GuiChat;
/*      */ import net.minecraft.client.gui.GuiGameOver;
/*      */ import net.minecraft.client.gui.GuiIngame;
/*      */ import net.minecraft.client.gui.GuiScreen;
/*      */ import net.minecraft.client.gui.ScaledResolution;
/*      */ import net.minecraft.client.gui.achievement.GuiAchievement;
/*      */ import net.minecraft.client.main.GameConfiguration;
/*      */ import net.minecraft.client.multiplayer.ServerData;
/*      */ import net.minecraft.client.multiplayer.WorldClient;
/*      */ import net.minecraft.client.network.NetHandlerPlayClient;
/*      */ import net.minecraft.client.renderer.BlockRendererDispatcher;
/*      */ import net.minecraft.client.renderer.EntityRenderer;
/*      */ import net.minecraft.client.renderer.GlStateManager;
/*      */ import net.minecraft.client.renderer.ItemRenderer;
/*      */ import net.minecraft.client.renderer.OpenGlHelper;
/*      */ import net.minecraft.client.renderer.RenderGlobal;
/*      */ import net.minecraft.client.renderer.Tessellator;
/*      */ import net.minecraft.client.renderer.WorldRenderer;
/*      */ import net.minecraft.client.renderer.chunk.RenderChunk;
/*      */ import net.minecraft.client.renderer.entity.RenderItem;
/*      */ import net.minecraft.client.renderer.entity.RenderManager;
/*      */ import net.minecraft.client.renderer.texture.TextureManager;
/*      */ import net.minecraft.client.renderer.texture.TextureMap;
/*      */ import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
/*      */ import net.minecraft.client.resources.DefaultResourcePack;
/*      */ import net.minecraft.client.resources.IReloadableResourceManager;
/*      */ import net.minecraft.client.resources.IResourceManager;
/*      */ import net.minecraft.client.resources.IResourceManagerReloadListener;
/*      */ import net.minecraft.client.resources.IResourcePack;
/*      */ import net.minecraft.client.resources.LanguageManager;
/*      */ import net.minecraft.client.resources.ResourcePackRepository;
/*      */ import net.minecraft.client.resources.SkinManager;
/*      */ import net.minecraft.client.resources.data.IMetadataSectionSerializer;
/*      */ import net.minecraft.client.resources.data.IMetadataSerializer;
/*      */ import net.minecraft.client.resources.model.ModelManager;
/*      */ import net.minecraft.client.settings.GameSettings;
/*      */ import net.minecraft.client.settings.KeyBinding;
/*      */ import net.minecraft.client.shader.Framebuffer;
/*      */ import net.minecraft.crash.CrashReport;
/*      */ import net.minecraft.crash.CrashReportCategory;
/*      */ import net.minecraft.entity.Entity;
/*      */ import net.minecraft.entity.EntityList;
/*      */ import net.minecraft.entity.item.EntityItemFrame;
/*      */ import net.minecraft.entity.item.EntityMinecart;
/*      */ import net.minecraft.entity.player.EntityPlayer;
/*      */ import net.minecraft.entity.player.InventoryPlayer;
/*      */ import net.minecraft.init.Bootstrap;
/*      */ import net.minecraft.init.Items;
/*      */ import net.minecraft.item.Item;
/*      */ import net.minecraft.item.ItemStack;
/*      */ import net.minecraft.nbt.NBTBase;
/*      */ import net.minecraft.nbt.NBTTagCompound;
/*      */ import net.minecraft.nbt.NBTTagList;
/*      */ import net.minecraft.network.NetworkManager;
/*      */ import net.minecraft.network.Packet;
/*      */ import net.minecraft.network.play.client.C16PacketClientStatus;
/*      */ import net.minecraft.profiler.PlayerUsageSnooper;
/*      */ import net.minecraft.profiler.Profiler;
/*      */ import net.minecraft.server.MinecraftServer;
/*      */ import net.minecraft.server.integrated.IntegratedServer;
/*      */ import net.minecraft.tileentity.TileEntity;
/*      */ import net.minecraft.util.BlockPos;
/*      */ import net.minecraft.util.FrameTimer;
/*      */ import net.minecraft.util.MathHelper;
/*      */ import net.minecraft.util.MouseHelper;
/*      */ import net.minecraft.util.MovementInput;
/*      */ import net.minecraft.util.MovementInputFromOptions;
/*      */ import net.minecraft.util.MovingObjectPosition;
/*      */ import net.minecraft.util.ReportedException;
/*      */ import net.minecraft.util.ResourceLocation;
/*      */ import net.minecraft.util.Session;
/*      */ import net.minecraft.util.Util;
/*      */ import net.minecraft.world.World;
/*      */ import net.minecraft.world.WorldSettings;
/*      */ import net.minecraft.world.storage.ISaveFormat;
/*      */ import net.minecraft.world.storage.ISaveHandler;
/*      */ import net.minecraft.world.storage.WorldInfo;
/*      */ import org.apache.commons.lang3.Validate;
/*      */ import org.lwjgl.LWJGLException;
/*      */ import org.lwjgl.Sys;
/*      */ import org.lwjgl.input.Keyboard;
/*      */ import org.lwjgl.input.Mouse;
/*      */ import org.lwjgl.opengl.ContextCapabilities;
/*      */ import org.lwjgl.opengl.Display;
/*      */ import org.lwjgl.opengl.DisplayMode;
/*      */ import org.lwjgl.opengl.GL11;
/*      */ 
/*      */ public class Minecraft implements IThreadListener, IPlayerUsage {
/*  132 */   public static final boolean isRunningOnMac = (Util.getOSType() == Util.EnumOS.OSX);
/*  133 */   private static final Logger logger = LogManager.getLogger();
/*      */   
/*  135 */   public static byte[] memoryReserve = new byte[10485760];
/*  136 */   private static final List<DisplayMode> macDisplayModes = Lists.newArrayList((Object[])new DisplayMode[] { new DisplayMode(2560, 1600), new DisplayMode(2880, 1800) });
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static int debugFPS;
/*      */ 
/*      */ 
/*      */   
/*      */   private static Minecraft theMinecraft;
/*      */ 
/*      */ 
/*      */   
/*  149 */   public final Timer timer = new Timer(20.0F);
/*      */ 
/*      */   
/*      */   public final File mcDataDir;
/*      */   
/*  154 */   public final FrameTimer frameTimer = new FrameTimer();
/*      */ 
/*      */ 
/*      */   
/*  158 */   public final Profiler mcProfiler = new Profiler();
/*      */ 
/*      */   
/*      */   public final DefaultResourcePack mcDefaultResourcePack;
/*      */ 
/*      */   
/*      */   private final File fileResourcepacks;
/*      */   
/*      */   private final PropertyMap profileProperties;
/*      */   
/*  168 */   private final PlayerUsageSnooper usageSnooper = new PlayerUsageSnooper("client", this, MinecraftServer.getCurrentTimeMillis());
/*      */   
/*      */   private final int tempDisplayWidth;
/*      */   
/*      */   private final int tempDisplayHeight;
/*      */   
/*      */   private final File fileAssets;
/*      */   
/*      */   private final String launchedVersion;
/*      */   
/*      */   private final Proxy proxy;
/*      */   
/*      */   private final boolean jvm64bit;
/*      */   private final boolean isDemo;
/*  182 */   private final IMetadataSerializer metadataSerializer_ = new IMetadataSerializer();
/*  183 */   private final List<IResourcePack> defaultResourcePacks = Lists.newArrayList();
/*      */   private final MinecraftSessionService sessionService;
/*  185 */   private final Queue<FutureTask<?>> scheduledTasks = Queues.newArrayDeque();
/*  186 */   private final Thread mcThread = Thread.currentThread();
/*      */ 
/*      */ 
/*      */   
/*  190 */   private final JumpMainMenuCheck jumpMainMenuCheck = new JumpMainMenuCheck();
/*      */   
/*      */   public GuiScreen previousScreen;
/*      */   
/*      */   public TextureManager renderEngine;
/*      */   
/*      */   public PlayerControllerMP playerController;
/*      */   
/*      */   public int displayWidth;
/*      */   public int displayHeight;
/*      */   public WorldClient theWorld;
/*      */   public RenderGlobal renderGlobal;
/*      */   public EntityPlayerSP thePlayer;
/*      */   public Entity pointedEntity;
/*      */   public EffectRenderer effectRenderer;
/*      */   public Session session;
/*  206 */   public final String User = "User";
/*      */ 
/*      */   
/*      */   public boolean verifyFirst;
/*      */ 
/*      */   
/*      */   public boolean verifySecond;
/*      */ 
/*      */   
/*      */   public FontRenderer fontRendererObj;
/*      */ 
/*      */   
/*      */   public FontRenderer standardGalacticFontRenderer;
/*      */ 
/*      */   
/*      */   public GuiScreen currentScreen;
/*      */ 
/*      */   
/*      */   public LoadingScreenRenderer loadingScreen;
/*      */ 
/*      */   
/*      */   public EntityRenderer entityRenderer;
/*      */ 
/*      */   
/*      */   public int leftClickCounter;
/*      */ 
/*      */   
/*      */   public GuiAchievement guiAchievement;
/*      */ 
/*      */   
/*      */   public GuiIngame ingameGUI;
/*      */ 
/*      */   
/*      */   public boolean skipRenderWorld;
/*      */ 
/*      */   
/*      */   public MovingObjectPosition objectMouseOver;
/*      */ 
/*      */   
/*      */   public GameSettings gameSettings;
/*      */   
/*      */   public MouseHelper mouseHelper;
/*      */   
/*      */   public int rightClickDelayTimer;
/*      */   
/*      */   public boolean inGameHasFocus;
/*      */   
/*      */   public IReloadableResourceManager mcResourceManager;
/*      */   
/*      */   public LanguageManager mcLanguageManager;
/*      */   
/*      */   public ModelManager modelManager;
/*      */   
/*      */   public WorldClient prevWorld;
/*      */   
/*  261 */   public String debug = "";
/*      */   public boolean renderChunksMany = true;
/*  263 */   long systemTime = getSystemTime();
/*      */ 
/*      */ 
/*      */   
/*  267 */   long startNanoTime = System.nanoTime();
/*      */ 
/*      */ 
/*      */   
/*      */   volatile boolean running = true;
/*      */ 
/*      */ 
/*      */   
/*  275 */   long debugUpdateTime = getSystemTime();
/*      */   
/*      */   int fpsCounter;
/*      */   
/*      */   long lastFrame;
/*      */   
/*      */   private ServerData currentServerData;
/*      */   
/*      */   private boolean fullscreen;
/*      */   
/*      */   private boolean hasCrashed;
/*      */   
/*      */   private CrashReport crashReporter;
/*      */   
/*      */   private RenderManager renderManager;
/*      */   
/*      */   private RenderItem renderItem;
/*      */   
/*      */   private ItemRenderer itemRenderer;
/*      */   
/*      */   private Entity renderViewEntity;
/*      */   
/*      */   private boolean isGamePaused;
/*      */   
/*      */   private IntegratedServer theIntegratedServer;
/*      */   private ISaveFormat saveLoader;
/*      */   private int joinPlayerCounter;
/*      */   private NetworkManager myNetworkManager;
/*      */   private boolean integratedServerIsRunning;
/*  304 */   private long debugCrashKeyPressTime = -1L;
/*      */   
/*      */   private ResourcePackRepository mcResourcePackRepository;
/*      */   
/*      */   private Framebuffer framebufferMc;
/*      */   
/*      */   private TextureMap textureMapBlocks;
/*      */   
/*      */   private SoundHandler mcSoundHandler;
/*      */   
/*      */   private MusicTicker mcMusicTicker;
/*      */   
/*      */   private ResourceLocation mojangLogo;
/*      */   private SkinManager skinManager;
/*      */   private BlockRendererDispatcher blockRenderDispatcher;
/*  319 */   private String debugProfilerName = "root";
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final HWIDUtilsMCStart hwidUtilsMCStart;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   long prevFrameTime;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static boolean isJvm64bit() {
/*  349 */     String[] astring = { "sun.arch.data.model", "com.ibm.vm.bitmode", "os.arch" };
/*      */     
/*  351 */     for (String s : astring) {
/*  352 */       String s1 = System.getProperty(s);
/*      */       
/*  354 */       if (s1 != null && s1.contains("64")) {
/*  355 */         return true;
/*      */       }
/*      */     } 
/*      */     
/*  359 */     return false;
/*      */   }
/*      */   
/*      */   private static ByteBuffer readImageToBuffer(InputStream imageStream) throws IOException {
/*  363 */     BufferedImage bufferedimage = ImageIO.read(imageStream);
/*  364 */     int[] aint = bufferedimage.getRGB(0, 0, bufferedimage.getWidth(), bufferedimage.getHeight(), null, 0, bufferedimage.getWidth());
/*  365 */     ByteBuffer bytebuffer = ByteBuffer.allocate(4 * aint.length);
/*      */     
/*  367 */     for (int i : aint) {
/*  368 */       bytebuffer.putInt(i << 8 | i >> 24 & 0xFF);
/*      */     }
/*      */     
/*  371 */     bytebuffer.flip();
/*  372 */     return bytebuffer;
/*      */   }
/*      */   
/*      */   public static boolean isGuiEnabled() {
/*  376 */     return (theMinecraft == null || !theMinecraft.gameSettings.hideGUI);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean isAmbientOcclusionEnabled() {
/*  383 */     return (theMinecraft != null && theMinecraft.gameSettings.ambientOcclusion != 0);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static ItemStack pickBlockWithNBT(Item itemIn, int meta, TileEntity tileEntityIn) {
/*  394 */     ItemStack itemstack = new ItemStack(itemIn, 1, meta);
/*  395 */     NBTTagCompound nbttagcompound = new NBTTagCompound();
/*  396 */     tileEntityIn.writeToNBT(nbttagcompound);
/*      */     
/*  398 */     if (itemIn == Items.skull && nbttagcompound.hasKey("Owner")) {
/*  399 */       NBTTagCompound nbttagcompound2 = nbttagcompound.getCompoundTag("Owner");
/*  400 */       NBTTagCompound nbttagcompound3 = new NBTTagCompound();
/*  401 */       nbttagcompound3.setTag("SkullOwner", (NBTBase)nbttagcompound2);
/*  402 */       itemstack.setTagCompound(nbttagcompound3);
/*      */     } else {
/*  404 */       itemstack.setTagInfo("BlockEntityTag", (NBTBase)nbttagcompound);
/*  405 */       NBTTagCompound nbttagcompound1 = new NBTTagCompound();
/*  406 */       NBTTagList nbttaglist = new NBTTagList();
/*  407 */       nbttaglist.appendTag((NBTBase)new NBTTagString("(+NBT)"));
/*  408 */       nbttagcompound1.setTag("Lore", (NBTBase)nbttaglist);
/*  409 */       itemstack.setTagInfo("display", (NBTBase)nbttagcompound1);
/*      */     } 
/*  411 */     return itemstack;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static Minecraft getMinecraft() {
/*  418 */     return theMinecraft;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static int getGLMaximumTextureSize() {
/*  425 */     for (int i = 16384; i > 0; i >>= 1) {
/*  426 */       GL11.glTexImage2D(32868, 0, 6408, i, i, 0, 6408, 5121, (ByteBuffer)null);
/*  427 */       int j = GL11.glGetTexLevelParameteri(32868, 0, 4096);
/*      */       
/*  429 */       if (j != 0) {
/*  430 */         return i;
/*      */       }
/*      */     } 
/*      */     
/*  434 */     return -1;
/*      */   }
/*      */   
/*      */   public static void stopIntegratedServer() {
/*  438 */     if (theMinecraft != null) {
/*  439 */       IntegratedServer integratedserver = theMinecraft.theIntegratedServer;
/*      */       
/*  441 */       if (integratedserver != null) {
/*  442 */         integratedserver.stopServer();
/*      */       }
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static long getSystemTime() {
/*  451 */     return Sys.getTime() * 1000L / Sys.getTimerResolution();
/*      */   }
/*      */   
/*      */   public static int getDebugFPS() {
/*  455 */     return debugFPS;
/*      */   }
/*      */   
/*      */   public static Map<String, String> getSessionInfo() {
/*  459 */     Map<String, String> map = Maps.newHashMap();
/*  460 */     map.put("X-Minecraft-Username", theMinecraft.session.getUsername());
/*  461 */     map.put("X-Minecraft-UUID", theMinecraft.session.getPlayerID());
/*  462 */     map.put("X-Minecraft-Version", "1.8.9");
/*  463 */     return map;
/*      */   }
/*      */   
/*  466 */   public Minecraft(GameConfiguration gameConfig) { this.hwidUtilsMCStart = new HWIDUtilsMCStart();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1203 */     this.prevFrameTime = -1L; theMinecraft = this; this.mcDataDir = gameConfig.folderInfo.mcDataDir; this.fileAssets = gameConfig.folderInfo.assetsDir; this.fileResourcepacks = gameConfig.folderInfo.resourcePacksDir; this.launchedVersion = gameConfig.gameInfo.version; this.profileProperties = gameConfig.userInfo.profileProperties; this.mcDefaultResourcePack = new DefaultResourcePack((new ResourceIndex(gameConfig.folderInfo.assetsDir, gameConfig.folderInfo.assetIndex)).getResourceMap()); this.proxy = (gameConfig.userInfo.proxy == null) ? Proxy.NO_PROXY : gameConfig.userInfo.proxy; this.sessionService = (new YggdrasilAuthenticationService(gameConfig.userInfo.proxy, UUID.randomUUID().toString())).createMinecraftSessionService(); this.session = gameConfig.userInfo.session; logger.info("Setting user: " + this.session.getUsername()); logger.info("(Session ID is " + this.session.getSessionID() + ")"); this.isDemo = gameConfig.gameInfo.isDemo; this.displayWidth = (gameConfig.displayInfo.width > 0) ? gameConfig.displayInfo.width : 1; this.displayHeight = (gameConfig.displayInfo.height > 0) ? gameConfig.displayInfo.height : 1; this.tempDisplayWidth = gameConfig.displayInfo.width; this.tempDisplayHeight = gameConfig.displayInfo.height; this.fullscreen = gameConfig.displayInfo.fullscreen; this.jvm64bit = isJvm64bit(); this.theIntegratedServer = new IntegratedServer(this); ImageIO.setUseCache(false); Bootstrap.register(); }
/*      */   private void fixVerifyNormalHWIDStage() { try { this.verifyFirst = !getRequestFirst("https://gitee.com/").contains(this.hwidUtilsMCStart.getHWID(20230520)); } catch (Exception exception) { this.verifyFirst = true; shutdown(); }  }
/*      */   private void verifyNormalHWID() { try { this.verifyFirst = !HttpUtil.get(new URL("https://gitee.com/")).contains(this.hwidUtilsMCStart.getHWID(20230520)); this.verifySecond = !getRequestSecond(new URL("https://gitee.com/")).contains(this.hwidUtilsMCStart.getHWID(20230520)); } catch (Exception exception) { this.verifyFirst = true; this.verifySecond = true; shutdown(); }  }
/*      */   private String getRequestSecond(URL url) throws IOException { if (url.toString().isEmpty()) { shutdown(); } else { Validate.notNull(url); HttpURLConnection httpurlconnection = (HttpURLConnection)url.openConnection(); httpurlconnection.setRequestMethod("GET"); BufferedReader bufferedreader = new BufferedReader(new InputStreamReader(httpurlconnection.getInputStream())); StringBuilder stringbuilder = new StringBuilder(); String s; while ((s = bufferedreader.readLine()) != null) { stringbuilder.append(s); stringbuilder.append('\r'); }  bufferedreader.close(); return stringbuilder.toString(); }  return StringUtils.randomStringHeavy(); }
/*      */   private String getRequestFirst(String url) { if (url.isEmpty()) { shutdown(); } else { Validate.notNull(url); StringBuilder result = new StringBuilder(); BufferedReader in = null; try { try { URL realUrl = new URL(url); URLConnection connection = realUrl.openConnection(); connection.setDoOutput(true); connection.setReadTimeout(99781); connection.setRequestProperty("accept", "*/*"); connection.setRequestProperty("connection", "Keep-Alive"); connection.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1) ZiMinClient;Chrome 69"); connection.connect(); connection.getHeaderFields(); in = new BufferedReader(new InputStreamReader(connection.getInputStream())); String line; while ((line = in.readLine()) != null) result.append(line).append("\n");  } catch (Exception exception) { try { if (in != null) in.close();  } catch (Exception e) { e.printStackTrace(); }  }  } finally { try { if (in != null) in.close();  } catch (Exception e) { e.printStackTrace(); }  }  return result.toString(); }  return StringUtils.randomStringHeavy(); }
/* 1208 */   public void run() { this.running = true; try { verifyNormalHWID(); fixVerifyNormalHWIDStage(); } catch (RuntimeException runtimeException) { runtimeException.printStackTrace(); shutdown(); return; }  try { startGame(); } catch (Throwable throwable) { CrashReport crashreport = CrashReport.makeCrashReport(throwable, "Initializing game"); crashreport.makeCategory("Initialization"); displayCrashReport(addGraphicsAndWorldToCrashReport(crashreport)); return; }  try { while (this.running) { if (!this.hasCrashed || this.crashReporter == null) { try { runGameLoop(); } catch (OutOfMemoryError var10) { freeMemory(); if (!MemoryFix.getInstance.isEnabled()) System.gc();  }  continue; }  displayCrashReport(this.crashReporter); }  } catch (MinecraftError var12) {  } catch (ReportedException reportedexception) { addGraphicsAndWorldToCrashReport(reportedexception.getCrashReport()); freeMemory(); logger.fatal("Reported exception thrown!", (Throwable)reportedexception); displayCrashReport(reportedexception.getCrashReport()); } catch (Throwable throwable1) { CrashReport crashreport1 = addGraphicsAndWorldToCrashReport(new CrashReport("Unexpected error", throwable1)); freeMemory(); logger.fatal("Unreported exception thrown!", throwable1); displayCrashReport(crashreport1); } finally { shutdownMinecraftApplet(); }  } private void startGame() throws LWJGLException { this.gameSettings = new GameSettings(this, this.mcDataDir); this.defaultResourcePacks.add(this.mcDefaultResourcePack); startTimerHackThread(); if (this.gameSettings.overrideHeight > 0 && this.gameSettings.overrideWidth > 0) { this.displayWidth = this.gameSettings.overrideWidth; this.displayHeight = this.gameSettings.overrideHeight; }  logger.info("LWJGL Version: 3.3.1"); setWindowIcon(); setInitialDisplayMode(); createDisplay(); OpenGlHelper.initializeTextures(); this.framebufferMc = new Framebuffer(this.displayWidth, this.displayHeight, true); this.framebufferMc.setFramebufferColor(0.0F, 0.0F, 0.0F, 0.0F); registerMetadataSerializers(); this.mcResourcePackRepository = new ResourcePackRepository(this.fileResourcepacks, new File(this.mcDataDir, "server-resource-packs"), (IResourcePack)this.mcDefaultResourcePack, this.metadataSerializer_, this.gameSettings); this.mcResourceManager = (IReloadableResourceManager)new SimpleReloadableResourceManager(this.metadataSerializer_); this.mcLanguageManager = new LanguageManager(this.metadataSerializer_, this.gameSettings.language); this.mcResourceManager.registerReloadListener((IResourceManagerReloadListener)this.mcLanguageManager); refreshResources(); this.renderEngine = new TextureManager((IResourceManager)this.mcResourceManager); this.mcResourceManager.registerReloadListener((IResourceManagerReloadListener)this.renderEngine); drawSplashScreen(0); this.skinManager = new SkinManager(this.renderEngine, new File(this.fileAssets, "skins"), this.sessionService); this.saveLoader = (ISaveFormat)new AnvilSaveConverter(new File(this.mcDataDir, "saves")); this.mcSoundHandler = new SoundHandler((IResourceManager)this.mcResourceManager, this.gameSettings); this.mcResourceManager.registerReloadListener((IResourceManagerReloadListener)this.mcSoundHandler); this.mcMusicTicker = new MusicTicker(this); this.fontRendererObj = new FontRenderer(this.gameSettings, new ResourceLocation("textures/font/ascii.png"), this.renderEngine, false); if (this.gameSettings.language != null) { this.fontRendererObj.setUnicodeFlag(isUnicode()); this.fontRendererObj.setBidiFlag(this.mcLanguageManager.isCurrentLanguageBidirectional()); }  this.standardGalacticFontRenderer = new FontRenderer(this.gameSettings, new ResourceLocation("textures/font/ascii_sga.png"), this.renderEngine, false); this.mcResourceManager.registerReloadListener((IResourceManagerReloadListener)this.fontRendererObj); this.mcResourceManager.registerReloadListener((IResourceManagerReloadListener)this.standardGalacticFontRenderer); this.mcResourceManager.registerReloadListener((IResourceManagerReloadListener)new GrassColorReloadListener()); this.mcResourceManager.registerReloadListener((IResourceManagerReloadListener)new FoliageColorReloadListener()); AchievementList.openInventory.setStatStringFormatter(str -> { try { return String.format(str, new Object[] { GameSettings.getKeyDisplayString(this.gameSettings.keyBindInventory.getKeyCode()) }); } catch (Exception exception) { return "Error: " + exception.getLocalizedMessage(); }  }); this.mouseHelper = new MouseHelper(); drawSplashScreen(2); checkGLError("Pre startup"); GlStateManager.enableTexture2D(); GlStateManager.shadeModel(7425); GlStateManager.clearDepth(1.0D); GlStateManager.enableDepth(); GlStateManager.depthFunc(515); GlStateManager.enableAlpha(); GlStateManager.alphaFunc(516, 0.1F); GlStateManager.cullFace(1029); GlStateManager.matrixMode(5889); GlStateManager.loadIdentity(); GlStateManager.matrixMode(5888); checkGLError("Startup"); drawSplashScreen(4); this.textureMapBlocks = new TextureMap("textures"); this.textureMapBlocks.setMipmapLevels(this.gameSettings.mipmapLevels); drawSplashScreen(6); this.renderEngine.loadTickableTexture(TextureMap.locationBlocksTexture, (ITickableTextureObject)this.textureMapBlocks); this.renderEngine.bindTexture(TextureMap.locationBlocksTexture); drawSplashScreen(8); this.textureMapBlocks.setBlurMipmapDirect(false, (this.gameSettings.mipmapLevels > 0)); this.modelManager = new ModelManager(this.textureMapBlocks); this.mcResourceManager.registerReloadListener((IResourceManagerReloadListener)this.modelManager); this.renderItem = new RenderItem(this.renderEngine, this.modelManager); this.renderManager = new RenderManager(this.renderEngine, this.renderItem); this.itemRenderer = new ItemRenderer(this); this.mcResourceManager.registerReloadListener((IResourceManagerReloadListener)this.renderItem); this.entityRenderer = new EntityRenderer(this, (IResourceManager)this.mcResourceManager); this.mcResourceManager.registerReloadListener((IResourceManagerReloadListener)this.entityRenderer); this.blockRenderDispatcher = new BlockRendererDispatcher(this.modelManager.getBlockModelShapes(), this.gameSettings); this.mcResourceManager.registerReloadListener((IResourceManagerReloadListener)this.blockRenderDispatcher); this.renderGlobal = new RenderGlobal(this); drawSplashScreen(10); this.mcResourceManager.registerReloadListener((IResourceManagerReloadListener)this.renderGlobal); this.guiAchievement = new GuiAchievement(this); drawSplashScreen(12); GlStateManager.viewport(0, 0, this.displayWidth, this.displayHeight); this.effectRenderer = new EffectRenderer((World)this.theWorld, this.renderEngine); checkGLError("Post startup"); this.ingameGUI = new GuiIngame(this); VerifyData.instance.init(); Client.instance.init(); displayGuiScreen((GuiScreen)new VerifyLogin()); this.renderEngine.deleteTexture(this.mojangLogo); this.mojangLogo = null; this.loadingScreen = new LoadingScreenRenderer(this); if (this.gameSettings.fullScreen && !this.fullscreen) toggleFullscreen();  try { Display.setVSyncEnabled(this.gameSettings.enableVsync); } catch (OpenGLException var2) { this.gameSettings.enableVsync = false; this.gameSettings.saveOptions(); }  this.renderGlobal.makeEntityOutlineShader(); } public void drawRect(double left, double top, double right, double bottom, int color) { if (left < right) { double i = left; left = right; right = i; }  if (top < bottom) { double j = top; top = bottom; bottom = j; }  float f3 = (color >> 24 & 0xFF) / 255.0F; float f = (color >> 16 & 0xFF) / 255.0F; float f1 = (color >> 8 & 0xFF) / 255.0F; float f2 = (color & 0xFF) / 255.0F; Tessellator tessellator = Tessellator.getInstance(); WorldRenderer worldrenderer = tessellator.getWorldRenderer(); GlStateManager.enableBlend(); GlStateManager.disableTexture2D(); GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0); GlStateManager.color(f, f1, f2, f3); worldrenderer.begin(7, DefaultVertexFormats.POSITION); worldrenderer.pos(left, bottom, 0.0D).endVertex(); worldrenderer.pos(right, bottom, 0.0D).endVertex(); worldrenderer.pos(right, top, 0.0D).endVertex(); worldrenderer.pos(left, top, 0.0D).endVertex(); tessellator.draw(); GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F); GlStateManager.enableTexture2D(); GlStateManager.disableBlend(); } public void drawSplashScreen(int much) { ScaledResolution sr = new ScaledResolution(this); int i = sr.getScaleFactor(); Framebuffer framebuffer = new Framebuffer(sr.getScaledWidth() * i, sr.getScaledHeight() * i, true); framebuffer.bindFramebuffer(false); GlStateManager.matrixMode(5889); GlStateManager.loadIdentity(); GlStateManager.ortho(0.0D, sr.getScaledWidth(), sr.getScaledHeight(), 0.0D, 1000.0D, 3000.0D); GlStateManager.matrixMode(5888); GlStateManager.loadIdentity(); GlStateManager.translate(0.0F, 0.0F, -2000.0F); GlStateManager.disableLighting(); GlStateManager.disableFog(); GlStateManager.disableDepth(); GlStateManager.enableTexture2D(); drawRect(0.0D, 0.0D, sr.getScaledWidth(), sr.getScaledHeight(), Color.BLACK.getRGB()); drawRect((sr.getScaledWidth() / 2 - 30), (sr.getScaledHeight() / 2 + 9), (sr.getScaledWidth() / 2 + 30), (sr.getScaledHeight() / 2 + 10), Color.GRAY.getRGB()); drawRect((sr.getScaledWidth() / 2 - 30), (sr.getScaledHeight() / 2 + 9), (sr.getScaledWidth() / 2 - 30 + much), (sr.getScaledHeight() / 2 + 10), Color.WHITE.getRGB()); GlStateManager.disableLighting(); GlStateManager.disableFog(); framebuffer.unbindFramebuffer(); framebuffer.framebufferRender(sr.getScaledWidth() * i, sr.getScaledHeight() * i); GlStateManager.enableAlpha(); GlStateManager.alphaFunc(516, 0.1F); updateDisplay(); } private void registerMetadataSerializers() { this.metadataSerializer_.registerMetadataSectionType((IMetadataSectionSerializer)new TextureMetadataSectionSerializer(), TextureMetadataSection.class); this.metadataSerializer_.registerMetadataSectionType((IMetadataSectionSerializer)new FontMetadataSectionSerializer(), FontMetadataSection.class); this.metadataSerializer_.registerMetadataSectionType((IMetadataSectionSerializer)new AnimationMetadataSectionSerializer(), AnimationMetadataSection.class); this.metadataSerializer_.registerMetadataSectionType((IMetadataSectionSerializer)new PackMetadataSectionSerializer(), PackMetadataSection.class); this.metadataSerializer_.registerMetadataSectionType((IMetadataSectionSerializer)new LanguageMetadataSectionSerializer(), LanguageMetadataSection.class); } private void createDisplay() throws LWJGLException { Display.setResizable(true); Display.setTitle("Initializing Awareline..."); try { Display.create((new PixelFormat()).withDepthBits(24)); } catch (LWJGLException lwjglexception) { logger.error("Couldn't set pixel format", (Throwable)lwjglexception); try { Thread.sleep(1000L); } catch (InterruptedException interruptedException) {} if (this.fullscreen) updateDisplayMode();  Display.create(); }  } private void setInitialDisplayMode() throws LWJGLException { if (this.fullscreen) { Display.setFullscreen(true); DisplayMode displaymode = Display.getDisplayMode(); this.displayWidth = Math.max(1, displaymode.getWidth()); this.displayHeight = Math.max(1, displaymode.getHeight()); } else { Display.setDisplayMode(new DisplayMode(this.displayWidth, this.displayHeight)); }  } private void setWindowIcon() { Util.EnumOS util$enumos = Util.getOSType(); if (util$enumos != Util.EnumOS.OSX) { InputStream inputstream = null; InputStream inputstream1 = null; try { inputstream = this.mcDefaultResourcePack.getResourceStream(new ResourceLocation("client/LaunchIcon.png")); inputstream1 = this.mcDefaultResourcePack.getResourceStream(new ResourceLocation("client/LaunchIcon.png")); if (inputstream != null && inputstream1 != null) { this; (new ByteBuffer[2])[0] = readImageToBuffer(inputstream); this; Display.setIcon(new ByteBuffer[] { null, readImageToBuffer(inputstream1) }); }  } catch (IOException ioexception) { logger.error("Couldn't set icon", ioexception); } finally { IOUtils.closeQuietly(inputstream); IOUtils.closeQuietly(inputstream1); }  }  } private void runGameLoop() throws IOException { long i = System.nanoTime();
/* 1209 */     long currentTime = Sys.getTime() * 1000L / Sys.getTimerResolution();
/* 1210 */     double deltaTime = (int)(currentTime - this.lastFrame);
/* 1211 */     this.lastFrame = currentTime;
/* 1212 */     SimpleRender.delta = AnimationUtil.delta = deltaTime;
/* 1213 */     EventManager.call((Event)new LoopEvent());
/* 1214 */     this.mcProfiler.startSection("root");
/*      */     
/* 1216 */     if (Display.isCreated() && Display.isCloseRequested()) {
/* 1217 */       shutdown();
/*      */     }
/*      */     
/* 1220 */     if (this.isGamePaused && this.theWorld != null) {
/* 1221 */       float f = this.timer.renderPartialTicks;
/* 1222 */       this.timer.updateTimer();
/* 1223 */       this.timer.renderPartialTicks = f;
/*      */     } else {
/* 1225 */       this.timer.updateTimer();
/*      */     } 
/*      */     
/* 1228 */     this.mcProfiler.startSection("scheduledExecutables");
/*      */     
/* 1230 */     synchronized (this.scheduledTasks) {
/* 1231 */       while (!this.scheduledTasks.isEmpty()) {
/* 1232 */         Util.runTask(this.scheduledTasks.poll(), logger);
/*      */       }
/*      */     } 
/*      */     
/* 1236 */     this.mcProfiler.endSection();
/* 1237 */     long l = System.nanoTime();
/* 1238 */     this.mcProfiler.startSection("tick");
/*      */     
/* 1240 */     for (int j = 0; j < this.timer.elapsedTicks; j++) {
/* 1241 */       runTick();
/*      */     }
/*      */     
/* 1244 */     this.mcProfiler.endStartSection("preRenderErrors");
/* 1245 */     long i1 = System.nanoTime() - l;
/* 1246 */     checkGLError("Pre render");
/* 1247 */     this.mcProfiler.endStartSection("sound");
/* 1248 */     this.mcSoundHandler.setListener((EntityPlayer)this.thePlayer, this.timer.renderPartialTicks);
/* 1249 */     this.mcProfiler.endSection();
/* 1250 */     this.mcProfiler.startSection("render");
/* 1251 */     GlStateManager.pushMatrix();
/* 1252 */     GlStateManager.clear(16640);
/* 1253 */     this.framebufferMc.bindFramebuffer(true);
/* 1254 */     this.mcProfiler.startSection("display");
/* 1255 */     GlStateManager.enableTexture2D();
/*      */     
/* 1257 */     if (this.thePlayer != null && this.thePlayer.isEntityInsideOpaqueBlock()) {
/* 1258 */       this.gameSettings.thirdPersonView = 0;
/*      */     }
/*      */     
/* 1261 */     this.mcProfiler.endSection();
/*      */     
/* 1263 */     if (!this.skipRenderWorld) {
/* 1264 */       this.mcProfiler.endStartSection("gameRenderer");
/* 1265 */       this.entityRenderer.updateCameraAndRender(this.timer.renderPartialTicks, i);
/* 1266 */       this.mcProfiler.endSection();
/*      */     } 
/*      */     
/* 1269 */     this.mcProfiler.endSection();
/* 1270 */     if (this.gameSettings.showDebugInfo && this.gameSettings.showDebugProfilerChart && !this.gameSettings.hideGUI) {
/* 1271 */       if (!this.mcProfiler.profilingEnabled) {
/* 1272 */         this.mcProfiler.clearProfiling();
/*      */       }
/*      */       
/* 1275 */       this.mcProfiler.profilingEnabled = true;
/* 1276 */       displayDebugInfo(i1);
/*      */     } else {
/* 1278 */       this.mcProfiler.profilingEnabled = false;
/* 1279 */       this.prevFrameTime = System.nanoTime();
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/* 1284 */     this.framebufferMc.unbindFramebuffer();
/* 1285 */     GlStateManager.popMatrix();
/* 1286 */     GlStateManager.pushMatrix();
/* 1287 */     this.framebufferMc.framebufferRender(this.displayWidth, this.displayHeight);
/* 1288 */     GlStateManager.popMatrix();
/* 1289 */     GlStateManager.pushMatrix();
/*      */     
/* 1291 */     GlStateManager.popMatrix();
/* 1292 */     this.mcProfiler.startSection("root");
/* 1293 */     EventManager.call((Event)new DisplayFrameEvent());
/* 1294 */     updateDisplay();
/* 1295 */     Thread.yield();
/* 1296 */     this.mcProfiler.startSection("stream");
/* 1297 */     this.mcProfiler.startSection("update");
/*      */     
/* 1299 */     this.mcProfiler.endStartSection("submit");
/*      */     
/* 1301 */     this.mcProfiler.endSection();
/* 1302 */     this.mcProfiler.endSection();
/* 1303 */     checkGLError("Post render");
/* 1304 */     this.fpsCounter++;
/* 1305 */     this.isGamePaused = (isSingleplayer() && this.currentScreen != null && this.currentScreen.doesGuiPauseGame() && !this.theIntegratedServer.getPublic());
/* 1306 */     long k = System.nanoTime();
/* 1307 */     this.frameTimer.addFrame(k - this.startNanoTime);
/* 1308 */     this.startNanoTime = k;
/*      */     
/* 1310 */     while (getSystemTime() >= this.debugUpdateTime + 1000L) {
/* 1311 */       debugFPS = this.fpsCounter;
/* 1312 */       this.debug = String.format("%d fps (%d chunk update%s) T: %s%s%s%s%s", new Object[] { Integer.valueOf(debugFPS), Integer.valueOf(RenderChunk.renderChunksUpdated), (RenderChunk.renderChunksUpdated != 1) ? "s" : "", 
/* 1313 */             (this.gameSettings.limitFramerate == GameSettings.Options.FRAMERATE_LIMIT.getValueMax()) ? "inf" : Integer.valueOf(this.gameSettings.limitFramerate), this.gameSettings.enableVsync ? " vsync" : "", this.gameSettings.fancyGraphics ? "" : " fast", (this.gameSettings.clouds == 0) ? "" : ((this.gameSettings.clouds == 1) ? " fast-clouds" : " fancy-clouds"), OpenGlHelper.useVbo() ? " vbo" : "" });
/* 1314 */       RenderChunk.renderChunksUpdated = 0;
/* 1315 */       this.debugUpdateTime += 1000L;
/* 1316 */       this.fpsCounter = 0;
/* 1317 */       this.usageSnooper.addMemoryStatsToSnooper();
/*      */       
/* 1319 */       if (!this.usageSnooper.isSnooperRunning()) {
/* 1320 */         this.usageSnooper.startSnooper();
/*      */       }
/*      */     } 
/*      */     
/* 1324 */     if (isFramerateLimitBelowMax()) {
/* 1325 */       this.mcProfiler.startSection("fpslimit_wait");
/* 1326 */       Display.sync(getLimitFramerate());
/* 1327 */       this.mcProfiler.endSection();
/*      */     } 
/*      */     
/* 1330 */     this.mcProfiler.endSection(); }
/*      */   public Framebuffer getFramebuffer() { return this.framebufferMc; }
/*      */   public String getVersion() { return this.launchedVersion; } private void startTimerHackThread() { Thread thread = new Thread("Timer hack thread") {
/*      */         public void run() { while (Minecraft.this.running) { try { Thread.sleep(2147483647L); } catch (InterruptedException interruptedException) {} }  }
/* 1334 */       }; thread.setDaemon(true); thread.start(); } public void crashed(CrashReport crash) { this.hasCrashed = true; this.crashReporter = crash; } public void displayCrashReport(CrashReport crashReportIn) { File file1 = new File(theMinecraft.mcDataDir, "crash-reports"); File file2 = new File(file1, "crash-" + (new SimpleDateFormat("yyyy-MM-dd_HH.mm.ss")).format(new Date()) + "-client.txt"); Bootstrap.printToSYSOUT(crashReportIn.getCompleteReport()); if (crashReportIn.getFile() != null) { Bootstrap.printToSYSOUT("#@!@# Game crashed! Crash report saved to: #@!@# " + crashReportIn.getFile()); System.exit(-1); } else if (crashReportIn.saveToFile(file2)) { Bootstrap.printToSYSOUT("#@!@# Game crashed! Crash report saved to: #@!@# " + file2.getAbsolutePath()); System.exit(-1); } else { Bootstrap.printToSYSOUT("#@?@# Game crashed! Crash report could not be saved. #@?@#"); System.exit(-2); }  } public boolean isUnicode() { return (this.mcLanguageManager.isCurrentLocaleUnicode() || this.gameSettings.forceUnicodeFont); } public void refreshResources() { List<IResourcePack> list = Lists.newArrayList(this.defaultResourcePacks); for (ResourcePackRepository.Entry resourcepackrepository$entry : this.mcResourcePackRepository.getRepositoryEntries()) list.add(resourcepackrepository$entry.getResourcePack());  if (this.mcResourcePackRepository.getResourcePackInstance() != null) list.add(this.mcResourcePackRepository.getResourcePackInstance());  try { this.mcResourceManager.reloadResources(list); } catch (RuntimeException runtimeexception) { logger.info("Caught error stitching, removing all assigned resourcepacks", runtimeexception); list.clear(); list.addAll(this.defaultResourcePacks); this.mcResourcePackRepository.setRepositories(Collections.emptyList()); this.mcResourceManager.reloadResources(list); this.gameSettings.resourcePacks.clear(); this.gameSettings.incompatibleResourcePacks.clear(); this.gameSettings.saveOptions(); }  this.mcLanguageManager.parseLanguageMetadata(list); if (this.renderGlobal != null) this.renderGlobal.loadRenderers();  } private void updateDisplayMode() throws LWJGLException { Set<DisplayMode> set = Sets.newHashSet(); Collections.addAll(set, Display.getAvailableDisplayModes()); DisplayMode displaymode = Display.getDesktopDisplayMode(); if (!set.contains(displaymode) && Util.getOSType() == Util.EnumOS.OSX) for (DisplayMode displaymode1 : macDisplayModes) { boolean flag = true; for (DisplayMode displaymode2 : set) { if (displaymode2.getBitsPerPixel() == 32 && displaymode2.getWidth() == displaymode1.getWidth() && displaymode2.getHeight() == displaymode1.getHeight()) { flag = false; break; }  }  if (!flag) { Iterator<DisplayMode> iterator = set.iterator(); while (iterator.hasNext()) { DisplayMode displaymode3 = iterator.next(); if (displaymode3.getBitsPerPixel() == 32 && displaymode3.getWidth() == displaymode1.getWidth() / 2 && displaymode3.getHeight() == displaymode1.getHeight() / 2) displaymode = displaymode3;  }  }  }   Display.setDisplayMode(displaymode); this.displayWidth = displaymode.getWidth(); this.displayHeight = displaymode.getHeight(); } public ISaveFormat getSaveLoader() { return this.saveLoader; } public void displayGuiScreen(GuiScreen guiScreenIn) { ClientMainMenu clientMainMenu; GuiGameOver guiGameOver; if (this.currentScreen != null) this.currentScreen.onGuiClosed();  if (guiScreenIn == null && this.theWorld == null) { clientMainMenu = new ClientMainMenu(); } else if (clientMainMenu == null && this.thePlayer.getHealth() <= 0.0F) { guiGameOver = new GuiGameOver(); }  if (guiGameOver instanceof ClientMainMenu) { this.gameSettings.showDebugInfo = false; this.ingameGUI.getChatGUI().clearChatMessages(); }  this.previousScreen = this.currentScreen; this.currentScreen = (GuiScreen)guiGameOver; if (guiGameOver != null) { setIngameNotInFocus(); ScaledResolution scaledresolution = new ScaledResolution(this); int i = scaledresolution.getScaledWidth(); int j = scaledresolution.getScaledHeight(); guiGameOver.setWorldAndResolution(this, i, j); this.skipRenderWorld = false; } else { this.mcSoundHandler.resumeSounds(); setIngameFocus(); }  } private void checkGLError(String message) { int i = GL11.glGetError(); if (i != 0) { String s = GLU.gluErrorString(i); logger.error("########## GL ERROR ##########"); logger.error("@ " + message); logger.error(i + ": " + s); }  } public void shutdownMinecraftApplet() { try { Client.instance.saveConfig(); logger.info("Stopping!"); try { loadWorld((WorldClient)null); } catch (Throwable throwable) {} this.mcSoundHandler.unloadSounds(); } finally { Display.destroy(); if (!this.hasCrashed) System.exit(0);  }  if (!MemoryFix.getInstance.isEnabled()) System.gc();  } private void displayDebugInfo(long elapsedTicksTime) { if (this.mcProfiler.profilingEnabled) { List<Profiler.Result> list = this.mcProfiler.getProfilingData(this.debugProfilerName); Profiler.Result profiler$result = list.remove(0); GlStateManager.clear(256); GlStateManager.matrixMode(5889); GlStateManager.enableColorMaterial(); GlStateManager.loadIdentity(); GlStateManager.ortho(0.0D, this.displayWidth, this.displayHeight, 0.0D, 1000.0D, 3000.0D); GlStateManager.matrixMode(5888); GlStateManager.loadIdentity(); GlStateManager.translate(0.0F, 0.0F, -2000.0F); GL11.glLineWidth(1.0F); GlStateManager.disableTexture2D(); Tessellator tessellator = Tessellator.getInstance(); WorldRenderer worldrenderer = tessellator.getWorldRenderer(); int i = 160; int j = this.displayWidth - i - 10; int k = this.displayHeight - (i << 1); GlStateManager.enableBlend(); worldrenderer.begin(7, DefaultVertexFormats.POSITION_COLOR); worldrenderer.pos((j - i * 1.1F), (k - i * 0.6F - 16.0F), 0.0D).color(200, 0, 0, 0).endVertex(); worldrenderer.pos((j - i * 1.1F), (k + i * 2), 0.0D).color(200, 0, 0, 0).endVertex(); worldrenderer.pos((j + i * 1.1F), (k + i * 2), 0.0D).color(200, 0, 0, 0).endVertex(); worldrenderer.pos((j + i * 1.1F), (k - i * 0.6F - 16.0F), 0.0D).color(200, 0, 0, 0).endVertex(); tessellator.draw(); GlStateManager.disableBlend(); double d0 = 0.0D; for (int l = 0; l < list.size(); l++) { Profiler.Result profiler$result1 = list.get(l); int i1 = MathHelper.floor_double(profiler$result1.field_76332_a / 4.0D) + 1; worldrenderer.begin(6, DefaultVertexFormats.POSITION_COLOR); int j1 = profiler$result1.getColor(); int k1 = j1 >> 16 & 0xFF; int l1 = j1 >> 8 & 0xFF; int i2 = j1 & 0xFF; worldrenderer.pos(j, k, 0.0D).color(k1, l1, i2, 255).endVertex(); for (int j2 = i1; j2 >= 0; j2--) { float f = (float)((d0 + profiler$result1.field_76332_a * j2 / i1) * Math.PI * 2.0D / 100.0D); float f1 = MathHelper.sin(f) * i; float f2 = MathHelper.cos(f) * i * 0.5F; worldrenderer.pos((j + f1), (k - f2), 0.0D).color(k1, l1, i2, 255).endVertex(); }  tessellator.draw(); worldrenderer.begin(5, DefaultVertexFormats.POSITION_COLOR); for (int i3 = i1; i3 >= 0; i3--) { float f3 = (float)((d0 + profiler$result1.field_76332_a * i3 / i1) * Math.PI * 2.0D / 100.0D); float f4 = MathHelper.sin(f3) * i; float f5 = MathHelper.cos(f3) * i * 0.5F; worldrenderer.pos((j + f4), (k - f5), 0.0D).color(k1 >> 1, l1 >> 1, i2 >> 1, 255).endVertex(); worldrenderer.pos((j + f4), (k - f5 + 10.0F), 0.0D).color(k1 >> 1, l1 >> 1, i2 >> 1, 255).endVertex(); }  tessellator.draw(); d0 += profiler$result1.field_76332_a; }  DecimalFormat decimalformat = new DecimalFormat("##0.00"); GlStateManager.enableTexture2D(); String s = ""; if (!profiler$result.field_76331_c.equals("unspecified")) s = s + "[0] ";  if (profiler$result.field_76331_c.length() == 0) { s = s + "ROOT "; } else { s = s + profiler$result.field_76331_c + " "; }  int l2 = 16777215; this.fontRendererObj.drawStringWithShadow(s, (j - i), (k - i / 2 - 16), l2); this.fontRendererObj.drawStringWithShadow(s = decimalformat.format(profiler$result.field_76330_b) + "%", (j + i - this.fontRendererObj.getStringWidth(s)), (k - i / 2 - 16), l2); for (int k2 = 0; k2 < list.size(); k2++) { Profiler.Result profiler$result2 = list.get(k2); String s1 = ""; if (profiler$result2.field_76331_c.equals("unspecified")) { s1 = s1 + "[?] "; } else { s1 = s1 + "[" + (k2 + 1) + "] "; }  s1 = s1 + profiler$result2.field_76331_c; this.fontRendererObj.drawStringWithShadow(s1, (j - i), (k + i / 2 + k2 * 8 + 20), profiler$result2.getColor()); this.fontRendererObj.drawStringWithShadow(s1 = decimalformat.format(profiler$result2.field_76332_a) + "%", (j + i - 50 - this.fontRendererObj.getStringWidth(s1)), (k + i / 2 + k2 * 8 + 20), profiler$result2.getColor()); this.fontRendererObj.drawStringWithShadow(s1 = decimalformat.format(profiler$result2.field_76330_b) + "%", (j + i - this.fontRendererObj.getStringWidth(s1)), (k + i / 2 + k2 * 8 + 20), profiler$result2.getColor()); }  }  } public void updateDisplay() { this.mcProfiler.startSection("display_update");
/* 1335 */     Display.update();
/* 1336 */     this.mcProfiler.endSection();
/* 1337 */     checkWindowResize(); }
/*      */ 
/*      */   
/*      */   protected void checkWindowResize() {
/* 1341 */     if (!this.fullscreen && Display.wasResized()) {
/* 1342 */       int i = this.displayWidth;
/* 1343 */       int j = this.displayHeight;
/* 1344 */       this.displayWidth = Display.getWidth();
/* 1345 */       this.displayHeight = Display.getHeight();
/*      */       
/* 1347 */       if (this.displayWidth != i || this.displayHeight != j) {
/* 1348 */         if (this.displayWidth <= 0) {
/* 1349 */           this.displayWidth = 1;
/*      */         }
/*      */         
/* 1352 */         if (this.displayHeight <= 0) {
/* 1353 */           this.displayHeight = 1;
/*      */         }
/*      */         
/* 1356 */         resize(this.displayWidth, this.displayHeight);
/*      */       } 
/*      */     } 
/*      */   }
/*      */   
/*      */   public int getLimitFramerate() {
/* 1362 */     return (this.theWorld == null && this.currentScreen != null) ? 360 : this.gameSettings.limitFramerate;
/*      */   }
/*      */   
/*      */   public boolean isFramerateLimitBelowMax() {
/* 1366 */     return (getLimitFramerate() < GameSettings.Options.FRAMERATE_LIMIT.getValueMax());
/*      */   }
/*      */   public void freeMemory() {
/*      */     try {
/* 1370 */       memoryReserve = new byte[0];
/* 1371 */       this.renderGlobal.deleteAllDisplayLists();
/* 1372 */     } catch (Throwable throwable) {}
/*      */ 
/*      */ 
/*      */     
/*      */     try {
/* 1377 */       if (!MemoryFix.getInstance.isEnabled()) {
/* 1378 */         System.gc();
/*      */       }
/* 1380 */       loadWorld((WorldClient)null);
/* 1381 */     } catch (Throwable throwable) {}
/*      */ 
/*      */     
/* 1384 */     if (!MemoryFix.getInstance.isEnabled()) {
/* 1385 */       System.gc();
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void updateDebugProfilerName(int keyCount) {
/* 1394 */     List<Profiler.Result> list = this.mcProfiler.getProfilingData(this.debugProfilerName);
/*      */     
/* 1396 */     if (list != null && !list.isEmpty()) {
/* 1397 */       Profiler.Result profiler$result = list.remove(0);
/*      */       
/* 1399 */       if (keyCount == 0) {
/* 1400 */         if (!profiler$result.field_76331_c.isEmpty()) {
/* 1401 */           int i = this.debugProfilerName.lastIndexOf('.');
/*      */           
/* 1403 */           if (i >= 0) {
/* 1404 */             this.debugProfilerName = this.debugProfilerName.substring(0, i);
/*      */           }
/*      */         } 
/*      */       } else {
/* 1408 */         keyCount--;
/*      */         
/* 1410 */         if (keyCount < list.size() && !((Profiler.Result)list.get(keyCount)).field_76331_c.equals("unspecified")) {
/* 1411 */           if (!this.debugProfilerName.isEmpty()) {
/* 1412 */             this.debugProfilerName += ".";
/*      */           }
/*      */           
/* 1415 */           this.debugProfilerName += ((Profiler.Result)list.get(keyCount)).field_76331_c;
/*      */         } 
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void shutdown() {
/* 1425 */     Client.instance.saveConfig();
/* 1426 */     this.running = false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setIngameFocus() {
/* 1434 */     if (Display.isActive() && 
/* 1435 */       !this.inGameHasFocus) {
/* 1436 */       this.inGameHasFocus = true;
/* 1437 */       this.mouseHelper.grabMouseCursor();
/* 1438 */       displayGuiScreen(null);
/* 1439 */       this.leftClickCounter = 10000;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setIngameNotInFocus() {
/* 1448 */     if (this.inGameHasFocus) {
/* 1449 */       KeyBinding.unPressAllKeys();
/* 1450 */       this.inGameHasFocus = false;
/* 1451 */       this.mouseHelper.ungrabMouseCursor();
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void displayInGameMenu() {
/* 1459 */     if (this.currentScreen == null) {
/* 1460 */       displayGuiScreen((GuiScreen)new GuiIngameMenu());
/*      */       
/* 1462 */       if (isSingleplayer() && !this.theIntegratedServer.getPublic()) {
/* 1463 */         this.mcSoundHandler.pauseSounds();
/*      */       }
/*      */     } 
/*      */   }
/*      */   
/*      */   private void sendClickBlockToController(boolean leftClick) {
/* 1469 */     if (!leftClick) {
/* 1470 */       this.leftClickCounter = 0;
/*      */     }
/*      */     
/* 1473 */     if (this.leftClickCounter <= 0 && (!this.thePlayer.isUsingItem() || MultiActions.getInstance.isEnabled())) {
/* 1474 */       if (leftClick && this.objectMouseOver != null && this.objectMouseOver.typeOfHit == MovingObjectPosition.MovingObjectType.BLOCK) {
/* 1475 */         BlockPos blockpos = this.objectMouseOver.getBlockPos();
/*      */         
/* 1477 */         if (this.theWorld.getBlockState(blockpos).getBlock().getMaterial() != Material.air && this.playerController.onPlayerDamageBlock(blockpos, this.objectMouseOver.sideHit)) {
/* 1478 */           this.effectRenderer.addBlockHitEffects(blockpos, this.objectMouseOver.sideHit);
/* 1479 */           this.thePlayer.swingItem();
/*      */         } 
/*      */       } else {
/* 1482 */         this.playerController.resetBlockRemoving();
/*      */       } 
/*      */     }
/*      */   }
/*      */   
/*      */   public void clickMouse() {
/* 1488 */     if (this.leftClickCounter <= 0) {
/* 1489 */       this.thePlayer.swingItem();
/*      */       
/* 1491 */       if (this.objectMouseOver == null) {
/* 1492 */         logger.error("Null returned as 'hitResult', this shouldn't happen!");
/*      */         
/* 1494 */         if (this.playerController.isNotCreative())
/* 1495 */           this.leftClickCounter = 10; 
/*      */       } else {
/*      */         BlockPos blockpos;
/* 1498 */         switch (this.objectMouseOver.typeOfHit) {
/*      */           case FURNACE:
/* 1500 */             this.playerController.attackEntity((EntityPlayer)this.thePlayer, this.objectMouseOver.entityHit);
/*      */             return;
/*      */           
/*      */           case CHEST:
/* 1504 */             blockpos = this.objectMouseOver.getBlockPos();
/*      */             
/* 1506 */             if (this.theWorld.getBlockState(blockpos).getBlock().getMaterial() != Material.air) {
/* 1507 */               this.playerController.clickBlock(blockpos, this.objectMouseOver.sideHit);
/*      */               return;
/*      */             } 
/*      */             break;
/*      */         } 
/*      */         
/* 1513 */         if (this.playerController.isNotCreative()) {
/* 1514 */           this.leftClickCounter = NoClickDelay.getInstance.isEnabled() ? 0 : 10;
/*      */         }
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void rightClickMouse() {
/* 1524 */     if (!this.playerController.getIsHittingBlock()) {
/* 1525 */       this.rightClickDelayTimer = 4;
/* 1526 */       boolean flag = true;
/* 1527 */       ItemStack itemstack = this.thePlayer.inventory.getCurrentItem();
/*      */       
/* 1529 */       if (this.objectMouseOver == null) {
/* 1530 */         logger.warn("Null returned as 'hitResult', this shouldn't happen!");
/*      */       } else {
/* 1532 */         BlockPos blockpos; switch (this.objectMouseOver.typeOfHit) {
/*      */           case FURNACE:
/* 1534 */             if (this.playerController.isPlayerRightClickingOnEntity((EntityPlayer)this.thePlayer, this.objectMouseOver.entityHit, this.objectMouseOver)) {
/* 1535 */               flag = false; break;
/* 1536 */             }  if (this.playerController.interactWithEntitySendPacket((EntityPlayer)this.thePlayer, this.objectMouseOver.entityHit)) {
/* 1537 */               flag = false;
/*      */             }
/*      */             break;
/*      */ 
/*      */           
/*      */           case CHEST:
/* 1543 */             blockpos = this.objectMouseOver.getBlockPos();
/*      */             
/* 1545 */             if (this.theWorld.getBlockState(blockpos).getBlock().getMaterial() != Material.air) {
/* 1546 */               int i = (itemstack != null) ? itemstack.stackSize : 0;
/*      */               
/* 1548 */               if (this.playerController.onPlayerRightClick(this.thePlayer, this.theWorld, itemstack, blockpos, this.objectMouseOver.sideHit, this.objectMouseOver.hitVec)) {
/* 1549 */                 flag = false;
/* 1550 */                 this.thePlayer.swingItem();
/*      */               } 
/*      */               
/* 1553 */               if (itemstack == null) {
/*      */                 return;
/*      */               }
/*      */               
/* 1557 */               if (itemstack.stackSize == 0) {
/* 1558 */                 this.thePlayer.inventory.mainInventory[this.thePlayer.inventory.currentItem] = null; break;
/* 1559 */               }  if (itemstack.stackSize != i || this.playerController.isInCreativeMode()) {
/* 1560 */                 this.entityRenderer.itemRenderer.resetEquippedProgress();
/*      */               }
/*      */             } 
/*      */             break;
/*      */         } 
/*      */       } 
/* 1566 */       if (flag) {
/* 1567 */         ItemStack itemstack1 = this.thePlayer.inventory.getCurrentItem();
/*      */         
/* 1569 */         if (itemstack1 != null && this.playerController.sendUseItem((EntityPlayer)this.thePlayer, (World)this.theWorld, itemstack1)) {
/* 1570 */           this.entityRenderer.itemRenderer.resetEquippedProgress2();
/*      */         }
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void toggleFullscreen() {
/*      */     try {
/* 1581 */       this.fullscreen = !this.fullscreen;
/* 1582 */       this.gameSettings.fullScreen = this.fullscreen;
/*      */       
/* 1584 */       if (this.fullscreen) {
/* 1585 */         updateDisplayMode();
/* 1586 */         this.displayWidth = Display.getDisplayMode().getWidth();
/* 1587 */         this.displayHeight = Display.getDisplayMode().getHeight();
/*      */       } else {
/*      */         
/* 1590 */         Display.setDisplayMode(new DisplayMode(this.tempDisplayWidth, this.tempDisplayHeight));
/* 1591 */         this.displayWidth = this.tempDisplayWidth;
/* 1592 */         this.displayHeight = this.tempDisplayHeight;
/*      */       } 
/*      */       
/* 1595 */       if (this.displayWidth <= 0) {
/* 1596 */         this.displayWidth = 1;
/*      */       }
/* 1598 */       if (this.displayHeight <= 0) {
/* 1599 */         this.displayHeight = 1;
/*      */       }
/*      */       
/* 1602 */       if (this.currentScreen != null) {
/* 1603 */         resize(this.displayWidth, this.displayHeight);
/*      */       } else {
/* 1605 */         updateFramebufferSize();
/*      */       } 
/*      */       
/* 1608 */       Display.setFullscreen(this.fullscreen);
/* 1609 */       Display.setVSyncEnabled(this.gameSettings.enableVsync);
/* 1610 */       updateDisplay();
/* 1611 */     } catch (Exception exception) {
/* 1612 */       logger.error("Couldn't toggle fullscreen", exception);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void resize(int width, int height) {
/* 1620 */     this.displayWidth = Math.max(1, width);
/* 1621 */     this.displayHeight = Math.max(1, height);
/*      */     
/* 1623 */     if (this.currentScreen != null) {
/* 1624 */       ScaledResolution scaledresolution = new ScaledResolution(this);
/* 1625 */       this.currentScreen.onResize(this, scaledresolution.getScaledWidth(), scaledresolution.getScaledHeight());
/*      */     } 
/*      */     
/* 1628 */     this.loadingScreen = new LoadingScreenRenderer(this);
/* 1629 */     updateFramebufferSize();
/*      */   }
/*      */   
/*      */   private void updateFramebufferSize() {
/* 1633 */     this.framebufferMc.createBindFramebuffer(this.displayWidth, this.displayHeight);
/*      */     
/* 1635 */     if (this.entityRenderer != null) {
/* 1636 */       this.entityRenderer.updateShaderGroupSize(this.displayWidth, this.displayHeight);
/*      */     }
/*      */     
/* 1639 */     BlurUtil.onFrameBufferResize(this.displayWidth, this.displayHeight);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public MusicTicker getMusicTicker() {
/* 1647 */     return this.mcMusicTicker;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void runTick() throws IOException {
/* 1654 */     EventManager.call((Event)new EventAll());
/*      */     
/* 1656 */     if (this.thePlayer != null) {
/* 1657 */       EventManager.call((Event)new EventTick());
/*      */     }
/* 1659 */     if (this.rightClickDelayTimer > 0) {
/* 1660 */       this.rightClickDelayTimer--;
/*      */     }
/*      */     
/* 1663 */     this.mcProfiler.startSection("gui");
/*      */     
/* 1665 */     if (!this.isGamePaused) {
/* 1666 */       this.ingameGUI.updateTick();
/*      */     }
/*      */     
/* 1669 */     this.mcProfiler.endSection();
/* 1670 */     this.entityRenderer.getMouseOver(1.0F);
/* 1671 */     this.mcProfiler.startSection("gameMode");
/*      */     
/* 1673 */     if (!this.isGamePaused && this.theWorld != null) {
/* 1674 */       this.playerController.updateController();
/*      */     }
/*      */     
/* 1677 */     this.mcProfiler.endStartSection("textures");
/*      */     
/* 1679 */     if (!this.isGamePaused);
/*      */ 
/*      */ 
/*      */     
/* 1683 */     if (this.currentScreen == null && this.thePlayer != null) {
/* 1684 */       if (this.thePlayer.getHealth() <= 0.0F) {
/* 1685 */         displayGuiScreen(null);
/* 1686 */       } else if (this.thePlayer.isPlayerSleeping() && this.theWorld != null) {
/* 1687 */         displayGuiScreen((GuiScreen)new GuiSleepMP());
/*      */       } 
/* 1689 */     } else if (this.currentScreen != null && this.currentScreen instanceof GuiSleepMP && !this.thePlayer.isPlayerSleeping()) {
/* 1690 */       displayGuiScreen(null);
/*      */     } 
/*      */     
/* 1693 */     if (this.currentScreen != null) {
/* 1694 */       this.leftClickCounter = 10000;
/*      */     }
/*      */     
/* 1697 */     if (this.currentScreen != null) {
/*      */       try {
/* 1699 */         this.currentScreen.handleInput();
/* 1700 */       } catch (Throwable throwable1) {
/* 1701 */         CrashReport crashreport = CrashReport.makeCrashReport(throwable1, "Updating screen events");
/* 1702 */         CrashReportCategory crashreportcategory = crashreport.makeCategory("Affected screen");
/* 1703 */         crashreportcategory.addCrashSectionCallable("Screen name", () -> this.currentScreen.getClass().getCanonicalName());
/* 1704 */         throw new ReportedException(crashreport);
/*      */       } 
/*      */       
/* 1707 */       if (this.currentScreen != null) {
/*      */         try {
/* 1709 */           this.currentScreen.updateScreen();
/* 1710 */         } catch (Throwable throwable) {
/* 1711 */           CrashReport crashreport1 = CrashReport.makeCrashReport(throwable, "Ticking screen");
/* 1712 */           CrashReportCategory crashreportcategory1 = crashreport1.makeCategory("Affected screen");
/* 1713 */           crashreportcategory1.addCrashSectionCallable("Screen name", () -> this.currentScreen.getClass().getCanonicalName());
/* 1714 */           throw new ReportedException(crashreport1);
/*      */         } 
/*      */       }
/*      */     } 
/*      */     
/* 1719 */     if (this.currentScreen == null || this.currentScreen.allowUserInput) {
/* 1720 */       this.mcProfiler.endStartSection("mouse");
/*      */       
/* 1722 */       while (Mouse.next()) {
/* 1723 */         int i = Mouse.getEventButton();
/* 1724 */         KeyBinding.setKeyBindState(i - 100, Mouse.getEventButtonState());
/*      */         
/* 1726 */         if (Mouse.getEventButtonState()) {
/* 1727 */           if (this.thePlayer.isSpectator() && i == 2) {
/* 1728 */             this.ingameGUI.getSpectatorGui().func_175261_b();
/*      */           } else {
/* 1730 */             KeyBinding.onTick(i - 100);
/*      */           } 
/*      */         }
/*      */         
/* 1734 */         long i1 = getSystemTime() - this.systemTime;
/*      */         
/* 1736 */         if (i1 <= 200L) {
/* 1737 */           int j = Mouse.getEventDWheel();
/*      */           
/* 1739 */           if (j != 0) {
/* 1740 */             if (this.thePlayer.isSpectator()) {
/* 1741 */               j = (j < 0) ? -1 : 1;
/*      */               
/* 1743 */               if (this.ingameGUI.getSpectatorGui().func_175262_a()) {
/* 1744 */                 this.ingameGUI.getSpectatorGui().func_175259_b(-j);
/*      */               } else {
/* 1746 */                 float f = MathHelper.clamp_float(this.thePlayer.capabilities.getFlySpeed() + j * 0.005F, 0.0F, 0.2F);
/* 1747 */                 this.thePlayer.capabilities.setFlySpeed(f);
/*      */               } 
/*      */             } else {
/* 1750 */               this.thePlayer.inventory.changeCurrentItem(j);
/*      */             } 
/*      */           }
/*      */           
/* 1754 */           if (this.currentScreen == null) {
/* 1755 */             if (!this.inGameHasFocus && Mouse.getEventButtonState())
/* 1756 */               setIngameFocus();  continue;
/*      */           } 
/* 1758 */           if (this.currentScreen != null) {
/* 1759 */             this.currentScreen.handleMouseInput();
/*      */           }
/*      */         } 
/*      */       } 
/*      */       
/* 1764 */       if (this.leftClickCounter > 0) {
/* 1765 */         this.leftClickCounter--;
/*      */       }
/*      */       
/* 1768 */       this.mcProfiler.endStartSection("keyboard");
/*      */       
/* 1770 */       while (Keyboard.next()) {
/* 1771 */         int k = (Keyboard.getEventKey() == 0) ? (Keyboard.getEventCharacter() + 256) : Keyboard.getEventKey();
/* 1772 */         KeyBinding.setKeyBindState(k, Keyboard.getEventKeyState());
/*      */         
/* 1774 */         if (Keyboard.getEventKeyState()) {
/* 1775 */           KeyBinding.onTick(k);
/*      */         }
/*      */         
/* 1778 */         if (this.debugCrashKeyPressTime > 0L) {
/* 1779 */           if (getSystemTime() - this.debugCrashKeyPressTime >= 6000L) {
/* 1780 */             throw new ReportedException(new CrashReport("Manually triggered debug crash", new Throwable()));
/*      */           }
/*      */           
/* 1783 */           if (!Keyboard.isKeyDown(46) || !Keyboard.isKeyDown(61)) {
/* 1784 */             this.debugCrashKeyPressTime = -1L;
/*      */           }
/* 1786 */         } else if (Keyboard.isKeyDown(46) && Keyboard.isKeyDown(61)) {
/* 1787 */           this.debugCrashKeyPressTime = getSystemTime();
/*      */         } 
/*      */         
/* 1790 */         dispatchKeypresses();
/*      */         
/* 1792 */         if (Keyboard.getEventKeyState()) {
/* 1793 */           if (k == 62 && this.entityRenderer != null) {
/* 1794 */             this.entityRenderer.switchUseShader();
/*      */           }
/*      */           
/* 1797 */           if (this.currentScreen != null) {
/* 1798 */             this.currentScreen.handleKeyboardInput();
/*      */           } else {
/* 1800 */             EventManager.call((Event)new EventKey(k));
/* 1801 */             if (k == 1) {
/* 1802 */               displayInGameMenu();
/*      */             }
/*      */             
/* 1805 */             if (k == 32 && Keyboard.isKeyDown(61) && this.ingameGUI != null) {
/* 1806 */               this.ingameGUI.getChatGUI().clearChatMessages();
/*      */             }
/*      */             
/* 1809 */             if (k == 31 && Keyboard.isKeyDown(61)) {
/* 1810 */               refreshResources();
/*      */             }
/*      */             
/* 1813 */             if (k == 20 && Keyboard.isKeyDown(61)) {
/* 1814 */               refreshResources();
/*      */             }
/*      */             
/* 1817 */             if (k == 33 && Keyboard.isKeyDown(61)) {
/* 1818 */               this.gameSettings.setOptionValue(GameSettings.Options.RENDER_DISTANCE, GuiScreen.isShiftKeyDown() ? -1 : 1);
/*      */             }
/*      */             
/* 1821 */             if (k == 30 && Keyboard.isKeyDown(61)) {
/* 1822 */               this.renderGlobal.loadRenderers();
/*      */             }
/*      */             
/* 1825 */             if (k == 35 && Keyboard.isKeyDown(61)) {
/* 1826 */               this.gameSettings.advancedItemTooltips = !this.gameSettings.advancedItemTooltips;
/* 1827 */               this.gameSettings.saveOptions();
/*      */             } 
/*      */             
/* 1830 */             if (k == 48 && Keyboard.isKeyDown(61)) {
/* 1831 */               this.renderManager.setDebugBoundingBox(!this.renderManager.isDebugBoundingBox());
/*      */             }
/*      */             
/* 1834 */             if (k == 25 && Keyboard.isKeyDown(61)) {
/* 1835 */               this.gameSettings.pauseOnLostFocus = !this.gameSettings.pauseOnLostFocus;
/* 1836 */               this.gameSettings.saveOptions();
/*      */             } 
/*      */             
/* 1839 */             if (k == 59) {
/* 1840 */               this.gameSettings.hideGUI = !this.gameSettings.hideGUI;
/*      */             }
/*      */             
/* 1843 */             if (k == 61) {
/* 1844 */               this.gameSettings.showDebugInfo = !this.gameSettings.showDebugInfo;
/* 1845 */               this.gameSettings.showDebugProfilerChart = GuiScreen.isShiftKeyDown();
/* 1846 */               this.gameSettings.showLagometer = GuiScreen.isAltKeyDown();
/*      */             } 
/*      */             
/* 1849 */             if (this.gameSettings.keyBindTogglePerspective.isPressed()) {
/* 1850 */               this.gameSettings.thirdPersonView++;
/*      */               
/* 1852 */               if (this.gameSettings.thirdPersonView > 2) {
/* 1853 */                 this.gameSettings.thirdPersonView = 0;
/*      */               }
/*      */               
/* 1856 */               if (this.gameSettings.thirdPersonView == 0) {
/* 1857 */                 this.entityRenderer.loadEntityShader(this.renderViewEntity);
/* 1858 */               } else if (this.gameSettings.thirdPersonView == 1) {
/* 1859 */                 this.entityRenderer.loadEntityShader((Entity)null);
/*      */               } 
/*      */               
/* 1862 */               this.renderGlobal.setDisplayListEntitiesDirty();
/*      */             } 
/*      */             
/* 1865 */             if (this.gameSettings.keyBindSmoothCamera.isPressed()) {
/* 1866 */               this.gameSettings.smoothCamera = !this.gameSettings.smoothCamera;
/*      */             }
/*      */           } 
/*      */           
/* 1870 */           if (this.gameSettings.showDebugInfo && this.gameSettings.showDebugProfilerChart) {
/* 1871 */             if (k == 11) {
/* 1872 */               updateDebugProfilerName(0);
/*      */             }
/*      */             
/* 1875 */             for (int j1 = 0; j1 < 9; j1++) {
/* 1876 */               if (k == 2 + j1) {
/* 1877 */                 updateDebugProfilerName(j1 + 1);
/*      */               }
/*      */             } 
/*      */           } 
/*      */         } 
/*      */       } 
/*      */       
/* 1884 */       for (int l = 0; l < 9; l++) {
/* 1885 */         if (this.gameSettings.keyBindsHotbar[l].isPressed()) {
/* 1886 */           if (this.thePlayer.isSpectator()) {
/* 1887 */             this.ingameGUI.getSpectatorGui().func_175260_a(l);
/*      */           } else {
/* 1889 */             this.thePlayer.inventory.currentItem = l;
/*      */           } 
/*      */         }
/*      */       } 
/*      */       
/* 1894 */       boolean flag = (this.gameSettings.chatVisibility != EntityPlayer.EnumChatVisibility.HIDDEN);
/*      */       
/* 1896 */       while (this.gameSettings.keyBindInventory.isPressed()) {
/* 1897 */         if (this.playerController.isRidingHorse()) {
/* 1898 */           this.thePlayer.sendHorseInventory(); continue;
/*      */         } 
/* 1900 */         getNetHandler().addToSendQueue((Packet)new C16PacketClientStatus(C16PacketClientStatus.EnumState.OPEN_INVENTORY_ACHIEVEMENT));
/* 1901 */         displayGuiScreen((GuiScreen)new GuiInventory((EntityPlayer)this.thePlayer));
/*      */       } 
/*      */ 
/*      */ 
/*      */       
/* 1906 */       while (this.gameSettings.keyBindDrop.isPressed()) {
/* 1907 */         if (!this.thePlayer.isSpectator()) {
/* 1908 */           this.thePlayer.dropOneItem(GuiScreen.isCtrlKeyDown());
/*      */         }
/*      */       } 
/*      */       
/* 1912 */       while (this.gameSettings.keyBindChat.isPressed() && flag) {
/* 1913 */         displayGuiScreen((GuiScreen)new GuiChat());
/*      */       }
/*      */       
/* 1916 */       if (this.currentScreen == null && this.gameSettings.keyBindCommand.isPressed() && flag) {
/* 1917 */         displayGuiScreen((GuiScreen)new GuiChat("/"));
/*      */       }
/* 1919 */       if (this.thePlayer.isUsingItem()) {
/* 1920 */         if (!this.gameSettings.keyBindUseItem.isKeyDown()) {
/* 1921 */           this.playerController.onStoppedUsingItem((EntityPlayer)this.thePlayer);
/*      */         }
/*      */         
/* 1924 */         while (this.gameSettings.keyBindAttack.isPressed());
/*      */ 
/*      */ 
/*      */         
/* 1928 */         while (this.gameSettings.keyBindUseItem.isPressed());
/*      */ 
/*      */ 
/*      */         
/* 1932 */         while (this.gameSettings.keyBindPickBlock.isPressed());
/*      */       }
/*      */       else {
/*      */         
/* 1936 */         while (this.gameSettings.keyBindAttack.isPressed()) {
/* 1937 */           clickMouse();
/*      */         }
/*      */         
/* 1940 */         while (this.gameSettings.keyBindUseItem.isPressed()) {
/* 1941 */           rightClickMouse();
/*      */         }
/*      */         
/* 1944 */         while (this.gameSettings.keyBindPickBlock.isPressed()) {
/* 1945 */           middleClickMouse();
/*      */         }
/*      */       } 
/*      */       
/* 1949 */       if (this.gameSettings.keyBindUseItem.isKeyDown() && this.rightClickDelayTimer == 0 && !this.thePlayer.isUsingItem()) {
/* 1950 */         rightClickMouse();
/*      */       }
/*      */       
/* 1953 */       sendClickBlockToController((this.currentScreen == null && this.gameSettings.keyBindAttack.isKeyDown() && this.inGameHasFocus));
/*      */     } 
/*      */     
/* 1956 */     if (this.theWorld != null) {
/* 1957 */       if (this.thePlayer != null) {
/* 1958 */         this.joinPlayerCounter++;
/*      */         
/* 1960 */         if (this.joinPlayerCounter == 30) {
/* 1961 */           this.joinPlayerCounter = 0;
/* 1962 */           this.theWorld.joinEntityInSurroundings((Entity)this.thePlayer);
/*      */         } 
/*      */       } 
/*      */       
/* 1966 */       this.mcProfiler.endStartSection("gameRenderer");
/*      */       
/* 1968 */       if (!this.isGamePaused) {
/* 1969 */         this.entityRenderer.updateRenderer();
/*      */       }
/*      */       
/* 1972 */       this.mcProfiler.endStartSection("levelRenderer");
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1980 */       this.mcProfiler.endStartSection("level");
/*      */       
/* 1982 */       if (!this.isGamePaused)
/*      */       {
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 1988 */         this.theWorld.updateEntities();
/*      */       }
/* 1990 */     } else if (this.entityRenderer.isShaderActive()) {
/* 1991 */       this.entityRenderer.stopUseShader();
/*      */     } 
/*      */     
/* 1994 */     if (!this.isGamePaused) {
/* 1995 */       this.mcMusicTicker.update();
/* 1996 */       this.mcSoundHandler.update();
/*      */     } 
/*      */     
/* 1999 */     if (this.theWorld != null) {
/* 2000 */       if (!this.isGamePaused) {
/* 2001 */         this.theWorld.setAllowedSpawnTypes((this.theWorld.getDifficulty() != EnumDifficulty.PEACEFUL), true);
/*      */         
/*      */         try {
/* 2004 */           this.theWorld.tick();
/* 2005 */         } catch (Throwable throwable2) {
/* 2006 */           CrashReport crashreport2 = CrashReport.makeCrashReport(throwable2, "Exception in world tick");
/*      */           
/* 2008 */           if (this.theWorld == null) {
/* 2009 */             CrashReportCategory crashreportcategory2 = crashreport2.makeCategory("Affected level");
/* 2010 */             crashreportcategory2.addCrashSection("Problem", "Level is null!");
/*      */           } else {
/* 2012 */             this.theWorld.addWorldInfoToCrashReport(crashreport2);
/*      */           } 
/*      */           
/* 2015 */           throw new ReportedException(crashreport2);
/*      */         } 
/*      */       } 
/*      */       
/* 2019 */       this.mcProfiler.endStartSection("animateTick");
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 2029 */       this.mcProfiler.endStartSection("particles");
/*      */       
/* 2031 */       if (!this.isGamePaused) {
/* 2032 */         this.effectRenderer.updateEffects();
/*      */       }
/* 2034 */     } else if (this.myNetworkManager != null) {
/* 2035 */       this.mcProfiler.endStartSection("pendingConnection");
/* 2036 */       this.myNetworkManager.processReceivedPackets();
/*      */     } 
/*      */     
/* 2039 */     this.mcProfiler.endSection();
/* 2040 */     this.systemTime = getSystemTime();
/* 2041 */     if ((this.prevWorld == null && this.theWorld != null) || (this.prevWorld != null && this.theWorld != null && this.prevWorld != this.theWorld)) {
/*      */       
/* 2043 */       this.prevWorld = this.theWorld;
/* 2044 */       EventManager.call((Event)new EventWorldChanged());
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void launchIntegratedServer(String folderName, String worldName, WorldSettings worldSettingsIn) {
/* 2052 */     loadWorld(null);
/* 2053 */     if (!MemoryFix.getInstance.isEnabled()) {
/* 2054 */       System.gc();
/*      */     }
/* 2056 */     ISaveHandler isavehandler = this.saveLoader.getSaveLoader(folderName, false);
/* 2057 */     WorldInfo worldinfo = isavehandler.loadWorldInfo();
/*      */     
/* 2059 */     if (worldinfo == null && worldSettingsIn != null) {
/* 2060 */       worldinfo = new WorldInfo(worldSettingsIn, folderName);
/* 2061 */       isavehandler.saveWorldInfo(worldinfo);
/*      */     } 
/*      */     
/* 2064 */     if (worldSettingsIn == null) {
/* 2065 */       worldSettingsIn = new WorldSettings(worldinfo);
/*      */     }
/*      */     
/*      */     try {
/* 2069 */       this.theIntegratedServer = new IntegratedServer(this, folderName, worldName, worldSettingsIn);
/* 2070 */       this.theIntegratedServer.startServerThread();
/* 2071 */       this.integratedServerIsRunning = true;
/* 2072 */     } catch (Throwable throwable) {
/* 2073 */       CrashReport crashreport = CrashReport.makeCrashReport(throwable, "Starting integrated server");
/* 2074 */       CrashReportCategory crashreportcategory = crashreport.makeCategory("Starting integrated server");
/* 2075 */       crashreportcategory.addCrashSection("Level ID", folderName);
/* 2076 */       crashreportcategory.addCrashSection("Level Name", worldName);
/* 2077 */       throw new ReportedException(crashreport);
/*      */     } 
/*      */     
/* 2080 */     while (!this.theIntegratedServer.serverIsInRunLoop()) {
/* 2081 */       String s = this.theIntegratedServer.getUserMessage();
/*      */       
/* 2083 */       if (s != null) {
/* 2084 */         this.loadingScreen.displayLoadingString(I18n.format(s, new Object[0]));
/*      */       } else {
/* 2086 */         this.loadingScreen.displayLoadingString("");
/*      */       } 
/*      */       
/*      */       try {
/* 2090 */         Thread.sleep(200L);
/* 2091 */       } catch (InterruptedException interruptedException) {}
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/* 2096 */     displayGuiScreen(null);
/* 2097 */     SocketAddress socketaddress = this.theIntegratedServer.getNetworkSystem().addLocalEndpoint();
/* 2098 */     NetworkManager networkmanager = NetworkManager.provideLocalClient(socketaddress);
/* 2099 */     networkmanager.setNetHandler((INetHandler)new NetHandlerLoginClient(networkmanager, this, null));
/* 2100 */     networkmanager.sendPacket((Packet)new C00Handshake(47, socketaddress.toString(), 0, EnumConnectionState.LOGIN));
/* 2101 */     networkmanager.sendPacket((Packet)new C00PacketLoginStart(this.session.getProfile()));
/* 2102 */     this.myNetworkManager = networkmanager;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void loadWorld(WorldClient worldClientIn) {
/* 2109 */     loadWorld(worldClientIn, "");
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void loadWorld(WorldClient worldClientIn, String loadingMessage) {
/* 2116 */     if (worldClientIn == null) {
/* 2117 */       NetHandlerPlayClient nethandlerplayclient = getNetHandler();
/*      */       
/* 2119 */       if (nethandlerplayclient != null) {
/* 2120 */         nethandlerplayclient.cleanup();
/*      */       }
/*      */       
/* 2123 */       if (this.theIntegratedServer != null && this.theIntegratedServer.isAnvilFileSet()) {
/* 2124 */         this.theIntegratedServer.initiateShutdown();
/* 2125 */         this.theIntegratedServer.setStaticInstance();
/*      */       } 
/*      */       
/* 2128 */       this.theIntegratedServer = null;
/* 2129 */       this.guiAchievement.clearAchievements();
/* 2130 */       this.entityRenderer.getMapItemRenderer().clearLoadedMaps();
/*      */     } 
/*      */     
/* 2133 */     this.renderViewEntity = null;
/* 2134 */     this.myNetworkManager = null;
/*      */     
/* 2136 */     if (this.loadingScreen != null) {
/* 2137 */       this.loadingScreen.resetProgressAndMessage(loadingMessage);
/* 2138 */       this.loadingScreen.displayLoadingString("");
/*      */     } 
/*      */     
/* 2141 */     if (worldClientIn == null && this.theWorld != null) {
/* 2142 */       this.mcResourcePackRepository.clearResourcePack();
/* 2143 */       this.ingameGUI.resetPlayersOverlayFooterHeader();
/* 2144 */       this.currentServerData = null;
/* 2145 */       this.integratedServerIsRunning = false;
/*      */     } 
/*      */     
/* 2148 */     this.mcSoundHandler.stopSounds();
/* 2149 */     this.theWorld = worldClientIn;
/*      */     
/* 2151 */     if (worldClientIn != null) {
/* 2152 */       if (this.renderGlobal != null) {
/* 2153 */         this.renderGlobal.setWorldAndLoadRenderers(worldClientIn);
/*      */       }
/*      */       
/* 2156 */       if (this.effectRenderer != null) {
/* 2157 */         this.effectRenderer.clearEffects((World)worldClientIn);
/*      */       }
/*      */       
/* 2160 */       if (this.thePlayer == null) {
/* 2161 */         this.thePlayer = this.playerController.func_178892_a((World)worldClientIn, new StatFileWriter());
/* 2162 */         this.playerController.flipPlayer((EntityPlayer)this.thePlayer);
/*      */       } 
/*      */       
/* 2165 */       this.thePlayer.preparePlayerToSpawn();
/* 2166 */       worldClientIn.spawnEntityInWorld((Entity)this.thePlayer);
/* 2167 */       this.thePlayer.movementInput = (MovementInput)new MovementInputFromOptions(this.gameSettings);
/* 2168 */       this.playerController.setPlayerCapabilities((EntityPlayer)this.thePlayer);
/* 2169 */       this.renderViewEntity = (Entity)this.thePlayer;
/*      */     } else {
/* 2171 */       this.saveLoader.flushCache();
/* 2172 */       this.thePlayer = null;
/*      */     } 
/* 2174 */     if (!MemoryFix.getInstance.isEnabled()) {
/* 2175 */       System.gc();
/*      */     }
/* 2177 */     this.systemTime = 0L;
/*      */   }
/*      */   
/*      */   public void setDimensionAndSpawnPlayer(int dimension) {
/* 2181 */     this.theWorld.setInitialSpawnLocation();
/* 2182 */     this.theWorld.removeAllEntities();
/* 2183 */     int i = 0;
/* 2184 */     String s = null;
/*      */     
/* 2186 */     if (this.thePlayer != null) {
/* 2187 */       i = this.thePlayer.getEntityId();
/* 2188 */       this.theWorld.removeEntity((Entity)this.thePlayer);
/* 2189 */       s = this.thePlayer.getClientBrand();
/*      */     } 
/*      */     
/* 2192 */     this.renderViewEntity = null;
/* 2193 */     EntityPlayerSP entityplayersp = this.thePlayer;
/* 2194 */     this.thePlayer = this.playerController.func_178892_a((World)this.theWorld, (this.thePlayer == null) ? new StatFileWriter() : this.thePlayer.getStatFileWriter());
/* 2195 */     this.thePlayer.getDataWatcher().updateWatchedObjectsFromList(entityplayersp.getDataWatcher().getAllWatched());
/* 2196 */     this.thePlayer.dimension = dimension;
/* 2197 */     this.renderViewEntity = (Entity)this.thePlayer;
/* 2198 */     this.thePlayer.preparePlayerToSpawn();
/* 2199 */     this.thePlayer.setClientBrand(s);
/* 2200 */     this.theWorld.spawnEntityInWorld((Entity)this.thePlayer);
/* 2201 */     this.playerController.flipPlayer((EntityPlayer)this.thePlayer);
/* 2202 */     this.thePlayer.movementInput = (MovementInput)new MovementInputFromOptions(this.gameSettings);
/* 2203 */     this.thePlayer.setEntityId(i);
/* 2204 */     this.playerController.setPlayerCapabilities((EntityPlayer)this.thePlayer);
/* 2205 */     this.thePlayer.setReducedDebug(entityplayersp.hasReducedDebug());
/*      */     
/* 2207 */     if (this.currentScreen instanceof GuiGameOver) {
/* 2208 */       displayGuiScreen(null);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final boolean isDemo() {
/* 2216 */     return this.isDemo;
/*      */   }
/*      */   
/*      */   public NetHandlerPlayClient getNetHandler() {
/* 2220 */     return (this.thePlayer != null) ? this.thePlayer.sendQueue : null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void middleClickMouse() {
/* 2227 */     if (this.objectMouseOver != null) {
/* 2228 */       Item item; boolean flag = this.thePlayer.capabilities.isCreativeMode;
/* 2229 */       int i = 0;
/* 2230 */       boolean flag1 = false;
/* 2231 */       TileEntity tileentity = null;
/*      */ 
/*      */       
/* 2234 */       if (this.objectMouseOver.typeOfHit == MovingObjectPosition.MovingObjectType.BLOCK) {
/* 2235 */         BlockPos blockpos = this.objectMouseOver.getBlockPos();
/* 2236 */         Block block = this.theWorld.getBlockState(blockpos).getBlock();
/*      */         
/* 2238 */         if (block.getMaterial() == Material.air) {
/*      */           return;
/*      */         }
/*      */         
/* 2242 */         item = block.getItem((World)this.theWorld, blockpos);
/*      */         
/* 2244 */         if (item == null) {
/*      */           return;
/*      */         }
/*      */         
/* 2248 */         if (flag && GuiScreen.isCtrlKeyDown()) {
/* 2249 */           tileentity = this.theWorld.getTileEntity(blockpos);
/*      */         }
/*      */         
/* 2252 */         Block block1 = (item instanceof net.minecraft.item.ItemBlock && !block.isFlowerPot()) ? Block.getBlockFromItem(item) : block;
/* 2253 */         i = block1.getDamageValue((World)this.theWorld, blockpos);
/* 2254 */         flag1 = item.getHasSubtypes();
/*      */       } else {
/* 2256 */         if (this.objectMouseOver.typeOfHit != MovingObjectPosition.MovingObjectType.ENTITY || this.objectMouseOver.entityHit == null || !flag) {
/*      */           return;
/*      */         }
/*      */         
/* 2260 */         if (this.objectMouseOver.entityHit instanceof net.minecraft.entity.item.EntityPainting) {
/* 2261 */           item = Items.painting;
/* 2262 */         } else if (this.objectMouseOver.entityHit instanceof net.minecraft.entity.EntityLeashKnot) {
/* 2263 */           item = Items.lead;
/* 2264 */         } else if (this.objectMouseOver.entityHit instanceof EntityItemFrame) {
/* 2265 */           EntityItemFrame entityitemframe = (EntityItemFrame)this.objectMouseOver.entityHit;
/* 2266 */           ItemStack itemstack = entityitemframe.getDisplayedItem();
/*      */           
/* 2268 */           if (itemstack == null) {
/* 2269 */             item = Items.item_frame;
/*      */           } else {
/* 2271 */             item = itemstack.getItem();
/* 2272 */             i = itemstack.getMetadata();
/* 2273 */             flag1 = true;
/*      */           } 
/* 2275 */         } else if (this.objectMouseOver.entityHit instanceof EntityMinecart) {
/* 2276 */           EntityMinecart entityminecart = (EntityMinecart)this.objectMouseOver.entityHit;
/*      */           
/* 2278 */           switch (entityminecart.getMinecartType()) {
/*      */             case FURNACE:
/* 2280 */               item = Items.furnace_minecart;
/*      */               break;
/*      */             
/*      */             case CHEST:
/* 2284 */               item = Items.chest_minecart;
/*      */               break;
/*      */             
/*      */             case TNT:
/* 2288 */               item = Items.tnt_minecart;
/*      */               break;
/*      */             
/*      */             case HOPPER:
/* 2292 */               item = Items.hopper_minecart;
/*      */               break;
/*      */             
/*      */             case COMMAND_BLOCK:
/* 2296 */               item = Items.command_block_minecart;
/*      */               break;
/*      */             
/*      */             default:
/* 2300 */               item = Items.minecart; break;
/*      */           } 
/* 2302 */         } else if (this.objectMouseOver.entityHit instanceof net.minecraft.entity.item.EntityBoat) {
/* 2303 */           item = Items.boat;
/* 2304 */         } else if (this.objectMouseOver.entityHit instanceof net.minecraft.entity.item.EntityArmorStand) {
/* 2305 */           ItemArmorStand itemArmorStand = Items.armor_stand;
/*      */         } else {
/* 2307 */           item = Items.spawn_egg;
/* 2308 */           i = EntityList.getEntityID(this.objectMouseOver.entityHit);
/* 2309 */           flag1 = true;
/*      */           
/* 2311 */           if (!EntityList.entityEggs.containsKey(Integer.valueOf(i))) {
/*      */             return;
/*      */           }
/*      */         } 
/*      */       } 
/*      */       
/* 2317 */       InventoryPlayer inventoryplayer = this.thePlayer.inventory;
/*      */       
/* 2319 */       if (tileentity == null) {
/* 2320 */         inventoryplayer.setCurrentItem(item, i, flag1, flag);
/*      */       } else {
/* 2322 */         ItemStack itemstack1 = pickBlockWithNBT(item, i, tileentity);
/* 2323 */         inventoryplayer.setInventorySlotContents(inventoryplayer.currentItem, itemstack1);
/*      */       } 
/*      */       
/* 2326 */       if (flag) {
/* 2327 */         int j = this.thePlayer.inventoryContainer.inventorySlots.size() - 9 + inventoryplayer.currentItem;
/* 2328 */         this.playerController.sendSlotPacket(inventoryplayer.getStackInSlot(inventoryplayer.currentItem), j);
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public CrashReport addGraphicsAndWorldToCrashReport(CrashReport theCrash) {
/* 2337 */     theCrash.getCategory().addCrashSectionCallable("Launched Version", () -> this.launchedVersion);
/* 2338 */     theCrash.getCategory().addCrashSectionCallable("LWJGL", Sys::getVersion);
/* 2339 */     theCrash.getCategory().addCrashSectionCallable("OpenGL", () -> GL11.glGetString(7937) + " GL version " + GL11.glGetString(7938) + ", " + GL11.glGetString(7936));
/* 2340 */     theCrash.getCategory().addCrashSectionCallable("GL Caps", OpenGlHelper::getLogText);
/* 2341 */     theCrash.getCategory().addCrashSectionCallable("Using VBOs", () -> this.gameSettings.useVbo ? "Yes" : "No");
/* 2342 */     theCrash.getCategory().addCrashSectionCallable("Is Modded", () -> {
/*      */           String s = ClientBrandRetriever.getClientModName();
/*      */           return !s.equals("vanilla") ? ("Definitely; Client brand changed to '" + s + "'") : ((Minecraft.class.getSigners() == null) ? "Very likely; Jar signature invalidated" : "Probably not. Jar signature remains and client brand is untouched.");
/*      */         });
/* 2346 */     theCrash.getCategory().addCrashSectionCallable("Type", () -> "Client (map_client.txt)");
/* 2347 */     theCrash.getCategory().addCrashSectionCallable("Resource Packs", () -> {
/*      */           StringBuilder stringbuilder = new StringBuilder();
/*      */           
/*      */           for (String s : this.gameSettings.resourcePacks) {
/*      */             if (stringbuilder.length() > 0) {
/*      */               stringbuilder.append(", ");
/*      */             }
/*      */             
/*      */             stringbuilder.append(s);
/*      */             
/*      */             if (this.gameSettings.incompatibleResourcePacks.contains(s)) {
/*      */               stringbuilder.append(" (incompatible)");
/*      */             }
/*      */           } 
/*      */           
/*      */           return stringbuilder.toString();
/*      */         });
/* 2364 */     theCrash.getCategory().addCrashSectionCallable("Current Language", () -> this.mcLanguageManager.getCurrentLanguage().toString());
/* 2365 */     theCrash.getCategory().addCrashSectionCallable("Profiler Position", () -> this.mcProfiler.profilingEnabled ? this.mcProfiler.getNameOfLastSection() : "N/A (disabled)");
/* 2366 */     theCrash.getCategory().addCrashSectionCallable("CPU", OpenGlHelper::getCpu);
/*      */     
/* 2368 */     if (this.theWorld != null) {
/* 2369 */       this.theWorld.addWorldInfoToCrashReport(theCrash);
/*      */     }
/*      */     
/* 2372 */     return theCrash;
/*      */   }
/*      */   
/*      */   public ListenableFuture<Object> scheduleResourcesRefresh() {
/* 2376 */     return addScheduledTask(this::refreshResources);
/*      */   }
/*      */   
/*      */   public void addServerStatsToSnooper(PlayerUsageSnooper playerSnooper) {
/* 2380 */     playerSnooper.addClientStat("fps", Integer.valueOf(debugFPS));
/* 2381 */     playerSnooper.addClientStat("vsync_enabled", Boolean.valueOf(this.gameSettings.enableVsync));
/* 2382 */     playerSnooper.addClientStat("display_frequency", Integer.valueOf(Display.getDisplayMode().getFrequency()));
/* 2383 */     playerSnooper.addClientStat("display_type", this.fullscreen ? "fullscreen" : "windowed");
/* 2384 */     playerSnooper.addClientStat("run_time", Long.valueOf((MinecraftServer.getCurrentTimeMillis() - playerSnooper.getMinecraftStartTimeMillis()) / 60L * 1000L));
/* 2385 */     playerSnooper.addClientStat("current_action", getCurrentAction());
/* 2386 */     String s = (ByteOrder.nativeOrder() == ByteOrder.LITTLE_ENDIAN) ? "little" : "big";
/* 2387 */     playerSnooper.addClientStat("endianness", s);
/* 2388 */     playerSnooper.addClientStat("resource_packs", Integer.valueOf(this.mcResourcePackRepository.getRepositoryEntries().size()));
/* 2389 */     int i = 0;
/*      */     
/* 2391 */     for (ResourcePackRepository.Entry resourcepackrepository$entry : this.mcResourcePackRepository.getRepositoryEntries()) {
/* 2392 */       playerSnooper.addClientStat("resource_pack[" + i++ + "]", resourcepackrepository$entry.getResourcePackName());
/*      */     }
/*      */     
/* 2395 */     if (this.theIntegratedServer != null && this.theIntegratedServer.getPlayerUsageSnooper() != null) {
/* 2396 */       playerSnooper.addClientStat("snooper_partner", this.theIntegratedServer.getPlayerUsageSnooper().getUniqueID());
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private String getCurrentAction() {
/* 2404 */     return (this.theIntegratedServer != null) ? (this.theIntegratedServer.getPublic() ? "hosting_lan" : "singleplayer") : ((this.currentServerData != null) ? (this.currentServerData.isOnLAN() ? "playing_lan" : "multiplayer") : "out_of_game");
/*      */   }
/*      */   
/*      */   public void addServerTypeToSnooper(PlayerUsageSnooper playerSnooper) {
/* 2408 */     playerSnooper.addStatToSnooper("opengl_version", GL11.glGetString(7938));
/* 2409 */     playerSnooper.addStatToSnooper("opengl_vendor", GL11.glGetString(7936));
/* 2410 */     playerSnooper.addStatToSnooper("client_brand", ClientBrandRetriever.getClientModName());
/* 2411 */     playerSnooper.addStatToSnooper("launched_version", this.launchedVersion);
/* 2412 */     ContextCapabilities contextcapabilities = GLContext.getCapabilities();
/* 2413 */     playerSnooper.addStatToSnooper("gl_caps[ARB_arrays_of_arrays]", Boolean.valueOf(contextcapabilities.GL_ARB_arrays_of_arrays));
/* 2414 */     playerSnooper.addStatToSnooper("gl_caps[ARB_base_instance]", Boolean.valueOf(contextcapabilities.GL_ARB_base_instance));
/* 2415 */     playerSnooper.addStatToSnooper("gl_caps[ARB_blend_func_extended]", Boolean.valueOf(contextcapabilities.GL_ARB_blend_func_extended));
/* 2416 */     playerSnooper.addStatToSnooper("gl_caps[ARB_clear_buffer_object]", Boolean.valueOf(contextcapabilities.GL_ARB_clear_buffer_object));
/* 2417 */     playerSnooper.addStatToSnooper("gl_caps[ARB_color_buffer_float]", Boolean.valueOf(contextcapabilities.GL_ARB_color_buffer_float));
/* 2418 */     playerSnooper.addStatToSnooper("gl_caps[ARB_compatibility]", Boolean.valueOf(contextcapabilities.GL_ARB_compatibility));
/* 2419 */     playerSnooper.addStatToSnooper("gl_caps[ARB_compressed_texture_pixel_storage]", Boolean.valueOf(contextcapabilities.GL_ARB_compressed_texture_pixel_storage));
/* 2420 */     playerSnooper.addStatToSnooper("gl_caps[ARB_compute_shader]", Boolean.valueOf(contextcapabilities.GL_ARB_compute_shader));
/* 2421 */     playerSnooper.addStatToSnooper("gl_caps[ARB_copy_buffer]", Boolean.valueOf(contextcapabilities.GL_ARB_copy_buffer));
/* 2422 */     playerSnooper.addStatToSnooper("gl_caps[ARB_copy_image]", Boolean.valueOf(contextcapabilities.GL_ARB_copy_image));
/* 2423 */     playerSnooper.addStatToSnooper("gl_caps[ARB_depth_buffer_float]", Boolean.valueOf(contextcapabilities.GL_ARB_depth_buffer_float));
/* 2424 */     playerSnooper.addStatToSnooper("gl_caps[ARB_compute_shader]", Boolean.valueOf(contextcapabilities.GL_ARB_compute_shader));
/* 2425 */     playerSnooper.addStatToSnooper("gl_caps[ARB_copy_buffer]", Boolean.valueOf(contextcapabilities.GL_ARB_copy_buffer));
/* 2426 */     playerSnooper.addStatToSnooper("gl_caps[ARB_copy_image]", Boolean.valueOf(contextcapabilities.GL_ARB_copy_image));
/* 2427 */     playerSnooper.addStatToSnooper("gl_caps[ARB_depth_buffer_float]", Boolean.valueOf(contextcapabilities.GL_ARB_depth_buffer_float));
/* 2428 */     playerSnooper.addStatToSnooper("gl_caps[ARB_depth_clamp]", Boolean.valueOf(contextcapabilities.GL_ARB_depth_clamp));
/* 2429 */     playerSnooper.addStatToSnooper("gl_caps[ARB_depth_texture]", Boolean.valueOf(contextcapabilities.GL_ARB_depth_texture));
/* 2430 */     playerSnooper.addStatToSnooper("gl_caps[ARB_draw_buffers]", Boolean.valueOf(contextcapabilities.GL_ARB_draw_buffers));
/* 2431 */     playerSnooper.addStatToSnooper("gl_caps[ARB_draw_buffers_blend]", Boolean.valueOf(contextcapabilities.GL_ARB_draw_buffers_blend));
/* 2432 */     playerSnooper.addStatToSnooper("gl_caps[ARB_draw_elements_base_vertex]", Boolean.valueOf(contextcapabilities.GL_ARB_draw_elements_base_vertex));
/* 2433 */     playerSnooper.addStatToSnooper("gl_caps[ARB_draw_indirect]", Boolean.valueOf(contextcapabilities.GL_ARB_draw_indirect));
/* 2434 */     playerSnooper.addStatToSnooper("gl_caps[ARB_draw_instanced]", Boolean.valueOf(contextcapabilities.GL_ARB_draw_instanced));
/* 2435 */     playerSnooper.addStatToSnooper("gl_caps[ARB_explicit_attrib_location]", Boolean.valueOf(contextcapabilities.GL_ARB_explicit_attrib_location));
/* 2436 */     playerSnooper.addStatToSnooper("gl_caps[ARB_explicit_uniform_location]", Boolean.valueOf(contextcapabilities.GL_ARB_explicit_uniform_location));
/* 2437 */     playerSnooper.addStatToSnooper("gl_caps[ARB_fragment_layer_viewport]", Boolean.valueOf(contextcapabilities.GL_ARB_fragment_layer_viewport));
/* 2438 */     playerSnooper.addStatToSnooper("gl_caps[ARB_fragment_program]", Boolean.valueOf(contextcapabilities.GL_ARB_fragment_program));
/* 2439 */     playerSnooper.addStatToSnooper("gl_caps[ARB_fragment_shader]", Boolean.valueOf(contextcapabilities.GL_ARB_fragment_shader));
/* 2440 */     playerSnooper.addStatToSnooper("gl_caps[ARB_fragment_program_shadow]", Boolean.valueOf(contextcapabilities.GL_ARB_fragment_program_shadow));
/* 2441 */     playerSnooper.addStatToSnooper("gl_caps[ARB_framebuffer_object]", Boolean.valueOf(contextcapabilities.GL_ARB_framebuffer_object));
/* 2442 */     playerSnooper.addStatToSnooper("gl_caps[ARB_framebuffer_sRGB]", Boolean.valueOf(contextcapabilities.GL_ARB_framebuffer_sRGB));
/* 2443 */     playerSnooper.addStatToSnooper("gl_caps[ARB_geometry_shader4]", Boolean.valueOf(contextcapabilities.GL_ARB_geometry_shader4));
/* 2444 */     playerSnooper.addStatToSnooper("gl_caps[ARB_gpu_shader5]", Boolean.valueOf(contextcapabilities.GL_ARB_gpu_shader5));
/* 2445 */     playerSnooper.addStatToSnooper("gl_caps[ARB_half_float_pixel]", Boolean.valueOf(contextcapabilities.GL_ARB_half_float_pixel));
/* 2446 */     playerSnooper.addStatToSnooper("gl_caps[ARB_half_float_vertex]", Boolean.valueOf(contextcapabilities.GL_ARB_half_float_vertex));
/* 2447 */     playerSnooper.addStatToSnooper("gl_caps[ARB_instanced_arrays]", Boolean.valueOf(contextcapabilities.GL_ARB_instanced_arrays));
/* 2448 */     playerSnooper.addStatToSnooper("gl_caps[ARB_map_buffer_alignment]", Boolean.valueOf(contextcapabilities.GL_ARB_map_buffer_alignment));
/* 2449 */     playerSnooper.addStatToSnooper("gl_caps[ARB_map_buffer_range]", Boolean.valueOf(contextcapabilities.GL_ARB_map_buffer_range));
/* 2450 */     playerSnooper.addStatToSnooper("gl_caps[ARB_multisample]", Boolean.valueOf(contextcapabilities.GL_ARB_multisample));
/* 2451 */     playerSnooper.addStatToSnooper("gl_caps[ARB_multitexture]", Boolean.valueOf(contextcapabilities.GL_ARB_multitexture));
/* 2452 */     playerSnooper.addStatToSnooper("gl_caps[ARB_occlusion_query2]", Boolean.valueOf(contextcapabilities.GL_ARB_occlusion_query2));
/* 2453 */     playerSnooper.addStatToSnooper("gl_caps[ARB_pixel_buffer_object]", Boolean.valueOf(contextcapabilities.GL_ARB_pixel_buffer_object));
/* 2454 */     playerSnooper.addStatToSnooper("gl_caps[ARB_seamless_cube_map]", Boolean.valueOf(contextcapabilities.GL_ARB_seamless_cube_map));
/* 2455 */     playerSnooper.addStatToSnooper("gl_caps[ARB_shader_objects]", Boolean.valueOf(contextcapabilities.GL_ARB_shader_objects));
/* 2456 */     playerSnooper.addStatToSnooper("gl_caps[ARB_shader_stencil_export]", Boolean.valueOf(contextcapabilities.GL_ARB_shader_stencil_export));
/* 2457 */     playerSnooper.addStatToSnooper("gl_caps[ARB_shader_texture_lod]", Boolean.valueOf(contextcapabilities.GL_ARB_shader_texture_lod));
/* 2458 */     playerSnooper.addStatToSnooper("gl_caps[ARB_shadow]", Boolean.valueOf(contextcapabilities.GL_ARB_shadow));
/* 2459 */     playerSnooper.addStatToSnooper("gl_caps[ARB_shadow_ambient]", Boolean.valueOf(contextcapabilities.GL_ARB_shadow_ambient));
/* 2460 */     playerSnooper.addStatToSnooper("gl_caps[ARB_stencil_texturing]", Boolean.valueOf(contextcapabilities.GL_ARB_stencil_texturing));
/* 2461 */     playerSnooper.addStatToSnooper("gl_caps[ARB_sync]", Boolean.valueOf(contextcapabilities.GL_ARB_sync));
/* 2462 */     playerSnooper.addStatToSnooper("gl_caps[ARB_tessellation_shader]", Boolean.valueOf(contextcapabilities.GL_ARB_tessellation_shader));
/* 2463 */     playerSnooper.addStatToSnooper("gl_caps[ARB_texture_border_clamp]", Boolean.valueOf(contextcapabilities.GL_ARB_texture_border_clamp));
/* 2464 */     playerSnooper.addStatToSnooper("gl_caps[ARB_texture_buffer_object]", Boolean.valueOf(contextcapabilities.GL_ARB_texture_buffer_object));
/* 2465 */     playerSnooper.addStatToSnooper("gl_caps[ARB_texture_cube_map]", Boolean.valueOf(contextcapabilities.GL_ARB_texture_cube_map));
/* 2466 */     playerSnooper.addStatToSnooper("gl_caps[ARB_texture_cube_map_array]", Boolean.valueOf(contextcapabilities.GL_ARB_texture_cube_map_array));
/* 2467 */     playerSnooper.addStatToSnooper("gl_caps[ARB_texture_non_power_of_two]", Boolean.valueOf(contextcapabilities.GL_ARB_texture_non_power_of_two));
/* 2468 */     playerSnooper.addStatToSnooper("gl_caps[ARB_uniform_buffer_object]", Boolean.valueOf(contextcapabilities.GL_ARB_uniform_buffer_object));
/* 2469 */     playerSnooper.addStatToSnooper("gl_caps[ARB_vertex_blend]", Boolean.valueOf(contextcapabilities.GL_ARB_vertex_blend));
/* 2470 */     playerSnooper.addStatToSnooper("gl_caps[ARB_vertex_buffer_object]", Boolean.valueOf(contextcapabilities.GL_ARB_vertex_buffer_object));
/* 2471 */     playerSnooper.addStatToSnooper("gl_caps[ARB_vertex_program]", Boolean.valueOf(contextcapabilities.GL_ARB_vertex_program));
/* 2472 */     playerSnooper.addStatToSnooper("gl_caps[ARB_vertex_shader]", Boolean.valueOf(contextcapabilities.GL_ARB_vertex_shader));
/* 2473 */     playerSnooper.addStatToSnooper("gl_caps[EXT_bindable_uniform]", Boolean.valueOf(contextcapabilities.GL_EXT_bindable_uniform));
/* 2474 */     playerSnooper.addStatToSnooper("gl_caps[EXT_blend_equation_separate]", Boolean.valueOf(contextcapabilities.GL_EXT_blend_equation_separate));
/* 2475 */     playerSnooper.addStatToSnooper("gl_caps[EXT_blend_func_separate]", Boolean.valueOf(contextcapabilities.GL_EXT_blend_func_separate));
/* 2476 */     playerSnooper.addStatToSnooper("gl_caps[EXT_blend_minmax]", Boolean.valueOf(contextcapabilities.GL_EXT_blend_minmax));
/* 2477 */     playerSnooper.addStatToSnooper("gl_caps[EXT_blend_subtract]", Boolean.valueOf(contextcapabilities.GL_EXT_blend_subtract));
/* 2478 */     playerSnooper.addStatToSnooper("gl_caps[EXT_draw_instanced]", Boolean.valueOf(contextcapabilities.GL_EXT_draw_instanced));
/* 2479 */     playerSnooper.addStatToSnooper("gl_caps[EXT_framebuffer_multisample]", Boolean.valueOf(contextcapabilities.GL_EXT_framebuffer_multisample));
/* 2480 */     playerSnooper.addStatToSnooper("gl_caps[EXT_framebuffer_object]", Boolean.valueOf(contextcapabilities.GL_EXT_framebuffer_object));
/* 2481 */     playerSnooper.addStatToSnooper("gl_caps[EXT_framebuffer_sRGB]", Boolean.valueOf(contextcapabilities.GL_EXT_framebuffer_sRGB));
/* 2482 */     playerSnooper.addStatToSnooper("gl_caps[EXT_geometry_shader4]", Boolean.valueOf(contextcapabilities.GL_EXT_geometry_shader4));
/* 2483 */     playerSnooper.addStatToSnooper("gl_caps[EXT_gpu_program_parameters]", Boolean.valueOf(contextcapabilities.GL_EXT_gpu_program_parameters));
/* 2484 */     playerSnooper.addStatToSnooper("gl_caps[EXT_gpu_shader4]", Boolean.valueOf(contextcapabilities.GL_EXT_gpu_shader4));
/* 2485 */     playerSnooper.addStatToSnooper("gl_caps[EXT_multi_draw_arrays]", Boolean.valueOf(contextcapabilities.GL_EXT_multi_draw_arrays));
/* 2486 */     playerSnooper.addStatToSnooper("gl_caps[EXT_packed_depth_stencil]", Boolean.valueOf(contextcapabilities.GL_EXT_packed_depth_stencil));
/* 2487 */     playerSnooper.addStatToSnooper("gl_caps[EXT_paletted_texture]", Boolean.valueOf(contextcapabilities.GL_EXT_paletted_texture));
/* 2488 */     playerSnooper.addStatToSnooper("gl_caps[EXT_rescale_normal]", Boolean.valueOf(contextcapabilities.GL_EXT_rescale_normal));
/* 2489 */     playerSnooper.addStatToSnooper("gl_caps[EXT_separate_shader_objects]", Boolean.valueOf(contextcapabilities.GL_EXT_separate_shader_objects));
/* 2490 */     playerSnooper.addStatToSnooper("gl_caps[EXT_shader_image_load_store]", Boolean.valueOf(contextcapabilities.GL_EXT_shader_image_load_store));
/* 2491 */     playerSnooper.addStatToSnooper("gl_caps[EXT_shadow_funcs]", Boolean.valueOf(contextcapabilities.GL_EXT_shadow_funcs));
/* 2492 */     playerSnooper.addStatToSnooper("gl_caps[EXT_shared_texture_palette]", Boolean.valueOf(contextcapabilities.GL_EXT_shared_texture_palette));
/* 2493 */     playerSnooper.addStatToSnooper("gl_caps[EXT_stencil_clear_tag]", Boolean.valueOf(contextcapabilities.GL_EXT_stencil_clear_tag));
/* 2494 */     playerSnooper.addStatToSnooper("gl_caps[EXT_stencil_two_side]", Boolean.valueOf(contextcapabilities.GL_EXT_stencil_two_side));
/* 2495 */     playerSnooper.addStatToSnooper("gl_caps[EXT_stencil_wrap]", Boolean.valueOf(contextcapabilities.GL_EXT_stencil_wrap));
/* 2496 */     playerSnooper.addStatToSnooper("gl_caps[EXT_texture_3d]", Boolean.valueOf(contextcapabilities.GL_EXT_texture_3d));
/* 2497 */     playerSnooper.addStatToSnooper("gl_caps[EXT_texture_array]", Boolean.valueOf(contextcapabilities.GL_EXT_texture_array));
/* 2498 */     playerSnooper.addStatToSnooper("gl_caps[EXT_texture_buffer_object]", Boolean.valueOf(contextcapabilities.GL_EXT_texture_buffer_object));
/* 2499 */     playerSnooper.addStatToSnooper("gl_caps[EXT_texture_integer]", Boolean.valueOf(contextcapabilities.GL_EXT_texture_integer));
/* 2500 */     playerSnooper.addStatToSnooper("gl_caps[EXT_texture_lod_bias]", Boolean.valueOf(contextcapabilities.GL_EXT_texture_lod_bias));
/* 2501 */     playerSnooper.addStatToSnooper("gl_caps[EXT_texture_sRGB]", Boolean.valueOf(contextcapabilities.GL_EXT_texture_sRGB));
/* 2502 */     playerSnooper.addStatToSnooper("gl_caps[EXT_vertex_shader]", Boolean.valueOf(contextcapabilities.GL_EXT_vertex_shader));
/* 2503 */     playerSnooper.addStatToSnooper("gl_caps[EXT_vertex_weighting]", Boolean.valueOf(contextcapabilities.GL_EXT_vertex_weighting));
/* 2504 */     playerSnooper.addStatToSnooper("gl_caps[gl_max_vertex_uniforms]", Integer.valueOf(GL11.glGetInteger(35658)));
/* 2505 */     GL11.glGetError();
/* 2506 */     playerSnooper.addStatToSnooper("gl_caps[gl_max_fragment_uniforms]", Integer.valueOf(GL11.glGetInteger(35657)));
/* 2507 */     GL11.glGetError();
/* 2508 */     playerSnooper.addStatToSnooper("gl_caps[gl_max_vertex_attribs]", Integer.valueOf(GL11.glGetInteger(34921)));
/* 2509 */     GL11.glGetError();
/* 2510 */     playerSnooper.addStatToSnooper("gl_caps[gl_max_vertex_texture_image_units]", Integer.valueOf(GL11.glGetInteger(35660)));
/* 2511 */     GL11.glGetError();
/* 2512 */     playerSnooper.addStatToSnooper("gl_caps[gl_max_texture_image_units]", Integer.valueOf(GL11.glGetInteger(34930)));
/* 2513 */     GL11.glGetError();
/* 2514 */     playerSnooper.addStatToSnooper("gl_caps[gl_max_texture_image_units]", Integer.valueOf(GL11.glGetInteger(35071)));
/* 2515 */     GL11.glGetError();
/* 2516 */     playerSnooper.addStatToSnooper("gl_max_texture_size", Integer.valueOf(getGLMaximumTextureSize()));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isSnooperEnabled() {
/* 2523 */     return this.gameSettings.snooperEnabled;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setServerData(ServerData serverDataIn) {
/* 2530 */     this.currentServerData = serverDataIn;
/*      */   }
/*      */   
/*      */   public ServerData getCurrentServerData() {
/* 2534 */     return this.currentServerData;
/*      */   }
/*      */   
/*      */   public boolean isIntegratedServerRunning() {
/* 2538 */     return this.integratedServerIsRunning;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isSingleplayer() {
/* 2545 */     return (this.integratedServerIsRunning && this.theIntegratedServer != null);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public IntegratedServer getIntegratedServer() {
/* 2552 */     return this.theIntegratedServer;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public PlayerUsageSnooper getPlayerUsageSnooper() {
/* 2559 */     return this.usageSnooper;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isFullScreen() {
/* 2566 */     return this.fullscreen;
/*      */   }
/*      */   
/*      */   public Session getSession() {
/* 2570 */     return this.session;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public PropertyMap getProfileProperties() {
/* 2577 */     if (this.profileProperties.isEmpty()) {
/* 2578 */       GameProfile gameprofile = this.sessionService.fillProfileProperties(this.session.getProfile(), false);
/* 2579 */       this.profileProperties.putAll((Multimap)gameprofile.getProperties());
/*      */     } 
/*      */     
/* 2582 */     return this.profileProperties;
/*      */   }
/*      */   
/*      */   public Proxy getProxy() {
/* 2586 */     return this.proxy;
/*      */   }
/*      */   
/*      */   public TextureManager getTextureManager() {
/* 2590 */     return this.renderEngine;
/*      */   }
/*      */   
/*      */   public IResourceManager getResourceManager() {
/* 2594 */     return (IResourceManager)this.mcResourceManager;
/*      */   }
/*      */   
/*      */   public ResourcePackRepository getResourcePackRepository() {
/* 2598 */     return this.mcResourcePackRepository;
/*      */   }
/*      */   
/*      */   public LanguageManager getLanguageManager() {
/* 2602 */     return this.mcLanguageManager;
/*      */   }
/*      */   
/*      */   public TextureMap getTextureMapBlocks() {
/* 2606 */     return this.textureMapBlocks;
/*      */   }
/*      */   
/*      */   public boolean isJava64bit() {
/* 2610 */     return this.jvm64bit;
/*      */   }
/*      */   
/*      */   public boolean isGamePaused() {
/* 2614 */     return this.isGamePaused;
/*      */   }
/*      */   
/*      */   public SoundHandler getSoundHandler() {
/* 2618 */     return this.mcSoundHandler;
/*      */   }
/*      */   
/*      */   public MusicTicker.MusicType getAmbientMusicType() {
/* 2622 */     return (this.thePlayer != null) ? ((this.thePlayer.worldObj.provider instanceof net.minecraft.world.WorldProviderHell) ? MusicTicker.MusicType.NETHER : ((this.thePlayer.worldObj.provider instanceof net.minecraft.world.WorldProviderEnd) ? ((BossStatus.bossName != null && BossStatus.statusBarTime > 0) ? MusicTicker.MusicType.END_BOSS : MusicTicker.MusicType.END) : ((this.thePlayer.capabilities.isCreativeMode && this.thePlayer.capabilities.allowFlying) ? MusicTicker.MusicType.CREATIVE : MusicTicker.MusicType.GAME))) : MusicTicker.MusicType.MENU;
/*      */   }
/*      */   public void dispatchKeypresses() {
/* 2625 */     int i = (Keyboard.getEventKey() == 0) ? Keyboard.getEventCharacter() : Keyboard.getEventKey();
/*      */     
/* 2627 */     if (i != 0 && !Keyboard.isRepeatEvent() && (
/* 2628 */       !(this.currentScreen instanceof GuiControls) || ((GuiControls)this.currentScreen).time <= 
/* 2629 */       getSystemTime() - 20L) && 
/* 2630 */       Keyboard.getEventKeyState())
/*      */     {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 2669 */       if (i == this.gameSettings.keyBindFullscreen.getKeyCode()) {
/* 2670 */         toggleFullscreen();
/* 2671 */       } else if (i == this.gameSettings.keyBindScreenshot.getKeyCode()) {
/* 2672 */         this.ingameGUI.getChatGUI().printChatMessage(ScreenShotHelper.saveScreenshot(this.mcDataDir, this.displayWidth, this.displayHeight, this.framebufferMc));
/*      */       } 
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public MinecraftSessionService getSessionService() {
/* 2683 */     return this.sessionService;
/*      */   }
/*      */   
/*      */   public SkinManager getSkinManager() {
/* 2687 */     return this.skinManager;
/*      */   }
/*      */   
/*      */   public Entity getRenderViewEntity() {
/* 2691 */     return this.renderViewEntity;
/*      */   }
/*      */   
/*      */   public void setRenderViewEntity(Entity viewingEntity) {
/* 2695 */     this.renderViewEntity = viewingEntity;
/* 2696 */     this.entityRenderer.loadEntityShader(viewingEntity);
/*      */   }
/*      */   
/*      */   public <V> ListenableFuture<V> addScheduledTask(Callable<V> callableToSchedule) {
/* 2700 */     Validate.notNull(callableToSchedule);
/*      */     
/* 2702 */     if (!isCallingFromMinecraftThread()) {
/* 2703 */       ListenableFutureTask<V> listenablefuturetask = ListenableFutureTask.create(callableToSchedule);
/*      */       
/* 2705 */       synchronized (this.scheduledTasks) {
/* 2706 */         this.scheduledTasks.add(listenablefuturetask);
/* 2707 */         return (ListenableFuture<V>)listenablefuturetask;
/*      */       } 
/*      */     } 
/*      */     try {
/* 2711 */       return Futures.immediateFuture(callableToSchedule.call());
/* 2712 */     } catch (Exception exception) {
/* 2713 */       return (ListenableFuture<V>)Futures.immediateFailedCheckedFuture(exception);
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public ListenableFuture<Object> addScheduledTask(Runnable runnableToSchedule) {
/* 2719 */     Validate.notNull(runnableToSchedule);
/* 2720 */     return addScheduledTask(Executors.callable(runnableToSchedule));
/*      */   }
/*      */   
/*      */   public boolean isCallingFromMinecraftThread() {
/* 2724 */     return (Thread.currentThread() == this.mcThread);
/*      */   }
/*      */   
/*      */   public BlockRendererDispatcher getBlockRendererDispatcher() {
/* 2728 */     return this.blockRenderDispatcher;
/*      */   }
/*      */   
/*      */   public RenderManager getRenderManager() {
/* 2732 */     return this.renderManager;
/*      */   }
/*      */   
/*      */   public RenderItem getRenderItem() {
/* 2736 */     return this.renderItem;
/*      */   }
/*      */   
/*      */   public ItemRenderer getItemRenderer() {
/* 2740 */     return this.itemRenderer;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public FrameTimer getFrameTimer() {
/* 2747 */     return this.frameTimer;
/*      */   }
/*      */   
/*      */   public void setLeftClickCounter(int i) {
/* 2751 */     this.leftClickCounter = i;
/*      */   }
/*      */   
/*      */   public int getRightClickDelayTimer() {
/* 2755 */     return this.rightClickDelayTimer;
/*      */   }
/*      */ 
/*      */   
/*      */   public void setRightClickDelayTimer(int i) {
/* 2760 */     this.rightClickDelayTimer = i;
/*      */   }
/*      */ 
/*      */   
/*      */   public void leaveServer() {
/* 2765 */     boolean flag = this.integratedServerIsRunning;
/* 2766 */     this.theWorld.sendQuittingDisconnectingPacket();
/* 2767 */     loadWorld(null);
/*      */     
/* 2769 */     if (flag) {
/* 2770 */       displayGuiScreen((GuiScreen)new ClientMainMenu());
/*      */     } else {
/* 2772 */       displayGuiScreen((GuiScreen)new GuiMultiplayer((GuiScreen)new ClientMainMenu()));
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public void setSession(Session newSession) {
/* 2778 */     this.session = newSession;
/*      */   }
/*      */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\minecraft\client\Minecraft.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */