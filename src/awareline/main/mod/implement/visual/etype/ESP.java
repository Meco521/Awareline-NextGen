/*      */ package awareline.main.mod.implement.visual.etype;
/*      */ import awareline.main.Client;
/*      */ import awareline.main.event.EventHandler;
/*      */ import awareline.main.event.events.world.renderEvents.EventRender2D;
/*      */ import awareline.main.event.events.world.renderEvents.EventRender3D;
/*      */ import awareline.main.mod.Module;
/*      */ import awareline.main.mod.implement.misc.Teams;
/*      */ import awareline.main.mod.implement.visual.HUD;
/*      */ import awareline.main.mod.implement.visual.sucks.WingRenderer.ColorUtils;
/*      */ import awareline.main.mod.values.Mode;
/*      */ import awareline.main.mod.values.Option;
/*      */ import awareline.main.mod.values.Value;
/*      */ import awareline.main.utility.render.RenderUtil;
/*      */ import awareline.main.utility.render.RenderUtils;
/*      */ import awareline.main.utility.render.color.Colors;
/*      */ import awareline.main.utility.vec.Vec3f;
/*      */ import java.awt.Color;
/*      */ import java.io.Serializable;
/*      */ import java.nio.FloatBuffer;
/*      */ import java.nio.IntBuffer;
/*      */ import java.text.NumberFormat;
/*      */ import java.util.ArrayList;
/*      */ import java.util.Arrays;
/*      */ import java.util.HashMap;
/*      */ import java.util.List;
/*      */ import java.util.Map;
/*      */ import javax.vecmath.Vector3d;
/*      */ import javax.vecmath.Vector4d;
/*      */ import net.minecraft.client.gui.Gui;
/*      */ import net.minecraft.client.gui.ScaledResolution;
/*      */ import net.minecraft.client.model.ModelRenderer;
/*      */ import net.minecraft.client.renderer.EntityRenderer;
/*      */ import net.minecraft.client.renderer.GLAllocation;
/*      */ import net.minecraft.client.renderer.GlStateManager;
/*      */ import net.minecraft.client.renderer.Tessellator;
/*      */ import net.minecraft.client.renderer.WorldRenderer;
/*      */ import net.minecraft.client.renderer.entity.RenderManager;
/*      */ import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
/*      */ import net.minecraft.entity.Entity;
/*      */ import net.minecraft.entity.EntityLivingBase;
/*      */ import net.minecraft.entity.player.EntityPlayer;
/*      */ import net.minecraft.potion.Potion;
/*      */ import net.minecraft.potion.PotionEffect;
/*      */ import net.minecraft.util.AxisAlignedBB;
/*      */ import org.lwjgl.opengl.Display;
/*      */ import org.lwjgl.opengl.GL11;
/*      */ import org.lwjgl.util.glu.Cylinder;
/*      */ import org.lwjgl.util.glu.GLU;
/*      */ 
/*      */ public class ESP extends Module {
/*   51 */   private static final Map<EntityPlayer, float[][]> entities = (Map)new HashMap<>();
/*   52 */   public final Mode<String> mode = new Mode("Mode", new String[] { "Box", "2DBox", "ExBox", "CornerA", "CornerB", "Csgo", "Other2D", "Outline", "TwoDimensional", "Flat", "Point" }, "ExBox");
/*      */ 
/*      */   
/*   55 */   public final List<Entity> collectedEntities = new ArrayList<>();
/*   56 */   private final Option<Boolean> xyzRender = new Option("XYZRender", Boolean.valueOf(false), () -> Boolean.valueOf(this.mode.is("Other2D")));
/*   57 */   private final Option<Boolean> self = new Option("Self", Boolean.valueOf(false));
/*   58 */   private final Option<Boolean> HEALTH = new Option("Health", Boolean.valueOf(true));
/*   59 */   private final Option<Boolean> invis = new Option("Invisible", Boolean.valueOf(false));
/*      */   private final Map<EntityLivingBase, double[]> entityConvertedPointsMap;
/*   61 */   private final IntBuffer viewport = GLAllocation.createDirectIntBuffer(16);
/*   62 */   private final FloatBuffer modelview = GLAllocation.createDirectFloatBuffer(16);
/*   63 */   private final FloatBuffer projection = GLAllocation.createDirectFloatBuffer(16);
/*   64 */   private final FloatBuffer vector = GLAllocation.createDirectFloatBuffer(4);
/*   65 */   private final int color = Color.WHITE.getRGB();
/*      */   float h;
/*      */   public static ESP getInstance;
/*      */   
/*      */   public ESP() {
/*   70 */     super("ESP", ModuleType.Render);
/*   71 */     addSettings(new Value[] { (Value)this.mode, (Value)this.xyzRender, (Value)this.self, (Value)this.HEALTH, (Value)this.invis });
/*   72 */     getInstance = this;
/*   73 */     int i = 0;
/*   74 */     while (i < 8) {
/*   75 */       ArrayList<Vec3f> points = new ArrayList<>();
/*   76 */       points.add(new Vec3f());
/*   77 */       i++;
/*      */     } 
/*   79 */     int i2 = 0;
/*   80 */     while (i2 < 8) {
/*   81 */       ArrayList<Vec3f> points = new ArrayList<>();
/*   82 */       points.add(new Vec3f());
/*   83 */       i2++;
/*      */     } 
/*   85 */     this.entityConvertedPointsMap = (Map)new HashMap<>();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void addEntity(EntityPlayer e, ModelRenderer bipedLeftLeg, ModelRenderer bipedLeftLegwear, ModelRenderer bipedRightLeg, ModelRenderer bipedLeftArm, ModelRenderer bipedLeftArmwear, ModelRenderer bipedRightArm, ModelRenderer bipedRightArmwear, ModelRenderer bipedBody, ModelRenderer bipedBodyWear, ModelRenderer bipedHead) {
/*   92 */     entities.put(e, new float[][] { { bipedHead.rotateAngleX, bipedHead.rotateAngleY, bipedHead.rotateAngleZ }, { bipedRightArm.rotateAngleX, bipedRightArm.rotateAngleY, bipedRightArm.rotateAngleZ }, { bipedLeftArm.rotateAngleX, bipedLeftArm.rotateAngleY, bipedLeftArm.rotateAngleZ }, { bipedRightLeg.rotateAngleX, bipedRightLeg.rotateAngleY, bipedRightLeg.rotateAngleZ }, { bipedLeftLeg.rotateAngleX, bipedLeftLeg.rotateAngleY, bipedLeftLeg.rotateAngleZ } });
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @EventHandler(4)
/*      */   private void onUpdate(EventPreUpdate e) {
/*  102 */     setSuffix((Serializable)this.mode.get());
/*      */   }
/*      */   
/*      */   @EventHandler(3)
/*      */   public void onScreen(EventRender2D event) {
/*  107 */     if (!isEnabled()) {
/*      */       return;
/*      */     }
/*  110 */     collectEntities();
/*  111 */     if (this.mode.is("TwoDimensional")) {
/*  112 */       GL11.glPushMatrix();
/*      */       
/*  114 */       float partialTicks = event.getPartialTicks();
/*  115 */       ScaledResolution scaledResolution = event.getResolution();
/*  116 */       int scaleFactor = scaledResolution.getScaleFactor();
/*  117 */       double scaling = scaleFactor / Math.pow(scaleFactor, 2.0D);
/*  118 */       GL11.glScaled(scaling, scaling, scaling);
/*  119 */       int color = this.color;
/*  120 */       RenderManager renderMng = mc.getRenderManager();
/*  121 */       EntityRenderer entityRenderer = mc.entityRenderer;
/*  122 */       List<Entity> collectedEntities = this.collectedEntities;
/*  123 */       for (Entity collectedEntity : collectedEntities) {
/*      */         
/*  125 */         if (!isValid(collectedEntity))
/*      */           continue; 
/*  127 */         double x = RenderUtils.interpolate(collectedEntity.posX, collectedEntity.lastTickPosX, partialTicks);
/*  128 */         double y = RenderUtils.interpolate(collectedEntity.posY, collectedEntity.lastTickPosY, partialTicks);
/*  129 */         double z = RenderUtils.interpolate(collectedEntity.posZ, collectedEntity.lastTickPosZ, partialTicks);
/*  130 */         double width = collectedEntity.width / 1.5D;
/*  131 */         double height = collectedEntity.height + (collectedEntity.isSneaking() ? -0.3D : 0.2D);
/*  132 */         AxisAlignedBB aabb = new AxisAlignedBB(x - width, y, z - width, x + width, y + height, z + width);
/*  133 */         List<Vector3d> vectors = Arrays.asList(new Vector3d[] { new Vector3d(aabb.minX, aabb.minY, aabb.minZ), new Vector3d(aabb.minX, aabb.maxY, aabb.minZ), new Vector3d(aabb.maxX, aabb.minY, aabb.minZ), new Vector3d(aabb.maxX, aabb.maxY, aabb.minZ), new Vector3d(aabb.minX, aabb.minY, aabb.maxZ), new Vector3d(aabb.minX, aabb.maxY, aabb.maxZ), new Vector3d(aabb.maxX, aabb.minY, aabb.maxZ), new Vector3d(aabb.maxX, aabb.maxY, aabb.maxZ) });
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*  138 */         entityRenderer.setupCameraTransform(partialTicks, 0);
/*  139 */         Vector4d position = null;
/*  140 */         for (Vector3d vector : vectors) {
/*  141 */           vector = project2D(scaleFactor, vector.x - renderMng.viewerPosX, vector.y - renderMng.viewerPosY, vector.z - renderMng.viewerPosZ);
/*      */           
/*  143 */           if (vector == null || vector.z < 0.0D || vector.z >= 1.0D)
/*      */             continue; 
/*  145 */           if (position == null) {
/*  146 */             position = new Vector4d(vector.x, vector.y, vector.z, 0.0D);
/*      */           }
/*  148 */           position.x = Math.min(vector.x, position.x);
/*  149 */           position.y = Math.min(vector.y, position.y);
/*  150 */           position.z = Math.max(vector.x, position.z);
/*  151 */           position.w = Math.max(vector.y, position.w);
/*      */         } 
/*  153 */         if (position == null)
/*      */           continue; 
/*  155 */         entityRenderer.setupOverlayRendering();
/*  156 */         double posX = position.x;
/*  157 */         double posY = position.y;
/*  158 */         double endPosX = position.z;
/*  159 */         double endPosY = position.w;
/*  160 */         Gui.drawRect(posX - 0.5D, posY, posX + 0.5D - 0.5D, endPosY, color);
/*  161 */         Gui.drawRect(posX, endPosY - 0.5D, endPosX, endPosY, color);
/*  162 */         Gui.drawRect(posX - 0.5D, posY, endPosX, posY + 0.5D, color);
/*  163 */         Gui.drawRect(endPosX - 0.5D, posY, endPosX, endPosY, color);
/*  164 */         if (collectedEntity instanceof EntityLivingBase) {
/*  165 */           EntityLivingBase entityLivingBase = (EntityLivingBase)collectedEntity;
/*      */           
/*  167 */           float hp = entityLivingBase.getHealth(); float maxHealth;
/*  168 */           if (hp > (maxHealth = entityLivingBase.getMaxHealth())) {
/*  169 */             hp = maxHealth;
/*      */           }
/*  171 */           double hpPercentage = (hp / maxHealth);
/*  172 */           double hpHeight = (endPosY - posY) * hpPercentage;
/*  173 */           if (hp > 0.0F) {
/*  174 */             int healthColor = ColorUtils.getHealthColorint(hp, maxHealth).getRGB();
/*  175 */             Gui.drawRect(posX - 3.0D, endPosY, posX - 2.5D, endPosY - hpHeight, healthColor);
/*      */           } 
/*      */         } 
/*      */       } 
/*  179 */       GL11.glPopMatrix();
/*  180 */       GlStateManager.enableBlend();
/*  181 */       entityRenderer.setupOverlayRendering();
/*  182 */     } else if (this.mode.is("Flat")) {
/*  183 */       GL11.glPushMatrix();
/*      */       
/*  185 */       float partialTicks = event.getPartialTicks();
/*  186 */       ScaledResolution scaledResolution = event.getResolution();
/*  187 */       int scaleFactor = scaledResolution.getScaleFactor();
/*  188 */       double scaling = scaleFactor / Math.pow(scaleFactor, 2.0D);
/*  189 */       GL11.glScaled(scaling, scaling, scaling);
/*  190 */       int color = (new Color(255, 255, 255, 102)).getRGB();
/*  191 */       RenderManager renderMng = mc.getRenderManager();
/*  192 */       EntityRenderer entityRenderer = mc.entityRenderer;
/*      */       
/*  194 */       List<Entity> collectedEntities = this.collectedEntities;
/*  195 */       for (Entity entity : collectedEntities) {
/*  196 */         if (!isValid(entity))
/*      */           continue; 
/*  198 */         double x = RenderUtil.interpolate(entity.posX, entity.lastTickPosX, partialTicks);
/*  199 */         double y = RenderUtil.interpolate(entity.posY, entity.lastTickPosY, partialTicks);
/*  200 */         double z = RenderUtil.interpolate(entity.posZ, entity.lastTickPosZ, partialTicks);
/*  201 */         double width = entity.width / 1.5D;
/*  202 */         double height = entity.height + (entity.isSneaking() ? -0.3D : 0.2D);
/*  203 */         AxisAlignedBB aabb = new AxisAlignedBB(x - width, y, z - width, x + width, y + height, z + width);
/*  204 */         List<Vector3d> vectors = Arrays.asList(new Vector3d[] { new Vector3d(aabb.minX, aabb.minY, aabb.minZ), new Vector3d(aabb.minX, aabb.maxY, aabb.minZ), new Vector3d(aabb.maxX, aabb.minY, aabb.minZ), new Vector3d(aabb.maxX, aabb.maxY, aabb.minZ), new Vector3d(aabb.minX, aabb.minY, aabb.maxZ), new Vector3d(aabb.minX, aabb.maxY, aabb.maxZ), new Vector3d(aabb.maxX, aabb.minY, aabb.maxZ), new Vector3d(aabb.maxX, aabb.maxY, aabb.maxZ) });
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*  209 */         entityRenderer.setupCameraTransform(partialTicks, 0);
/*  210 */         Vector4d position = null;
/*  211 */         for (Vector3d vector : vectors) {
/*  212 */           vector = project2D(scaleFactor, vector.x - renderMng.viewerPosX, vector.y - renderMng.viewerPosY, vector.z - renderMng.viewerPosZ);
/*      */           
/*  214 */           if (vector == null || vector.z < 0.0D || vector.z >= 1.0D)
/*      */             continue; 
/*  216 */           if (position == null) {
/*  217 */             position = new Vector4d(vector.x, vector.y, vector.z, 0.0D);
/*      */           }
/*  219 */           position.x = Math.min(vector.x, position.x);
/*  220 */           position.y = Math.min(vector.y, position.y);
/*  221 */           position.z = Math.max(vector.x, position.z);
/*  222 */           position.w = Math.max(vector.y, position.w);
/*      */         } 
/*  224 */         if (position == null)
/*      */           continue; 
/*  226 */         entityRenderer.setupOverlayRendering();
/*  227 */         double posX = position.x;
/*  228 */         double posY = position.y;
/*  229 */         double endPosX = position.z;
/*  230 */         double endPosY = position.w;
/*  231 */         Gui.drawRect(posX, posY, endPosX, endPosY, color);
/*      */       } 
/*  233 */       GL11.glPopMatrix();
/*  234 */       GlStateManager.enableBlend();
/*  235 */       entityRenderer.setupOverlayRendering();
/*      */     } 
/*      */   }
/*      */   
/*      */   @EventHandler(3)
/*      */   public void onRender(EventRender3D event) {
/*  241 */     if (!isEnabled()) {
/*      */       return;
/*      */     }
/*      */     try {
/*  245 */       updatePositions();
/*  246 */     } catch (Exception e) {
/*  247 */       e.printStackTrace();
/*      */     } 
/*      */   }
/*      */   
/*      */   @EventHandler(3)
/*      */   public void onRender2D(EventRender2D event) {
/*  253 */     if (!isEnabled()) {
/*      */       return;
/*      */     }
/*  256 */     if (this.mode.is("ExBox") || this.mode.is("CornerA") || this.mode.is("CornerB")) {
/*  257 */       GlStateManager.pushMatrix();
/*  258 */       for (Entity entity : this.entityConvertedPointsMap.keySet()) {
/*  259 */         EntityPlayer ent = (EntityPlayer)entity;
/*  260 */         double[] renderPositions = this.entityConvertedPointsMap.get(ent);
/*  261 */         double[] renderPositionsBottom = { renderPositions[4], renderPositions[5], renderPositions[6] };
/*  262 */         double[] renderPositionsX = { renderPositions[7], renderPositions[8], renderPositions[9] };
/*  263 */         double[] renderPositionsX2 = { renderPositions[10], renderPositions[11], renderPositions[12] };
/*  264 */         double[] renderPositionsZ = { renderPositions[13], renderPositions[14], renderPositions[15] };
/*  265 */         double[] renderPositionsZ2 = { renderPositions[16], renderPositions[17], renderPositions[18] };
/*  266 */         double[] renderPositionsTop1 = { renderPositions[19], renderPositions[20], renderPositions[21] };
/*  267 */         double[] renderPositionsTop2 = { renderPositions[22], renderPositions[23], renderPositions[24] };
/*  268 */         GlStateManager.pushMatrix();
/*  269 */         GlStateManager.scale(0.5D, 0.5D, 0.5D);
/*  270 */         if ((((Boolean)this.invis.get()).booleanValue() || !ent.isInvisible()) && ent != null && !(ent instanceof net.minecraft.client.entity.EntityPlayerSP)) {
/*      */           try {
/*  272 */             double[] xValues = { renderPositions[0], renderPositionsBottom[0], renderPositionsX[0], renderPositionsX2[0], renderPositionsZ[0], renderPositionsZ2[0], renderPositionsTop1[0], renderPositionsTop2[0] };
/*  273 */             double[] yValues = { renderPositions[1], renderPositionsBottom[1], renderPositionsX[1], renderPositionsX2[1], renderPositionsZ[1], renderPositionsZ2[1], renderPositionsTop1[1], renderPositionsTop2[1] };
/*  274 */             double x2 = renderPositions[0];
/*  275 */             double y2 = renderPositions[1];
/*  276 */             double endx = renderPositionsBottom[0];
/*  277 */             double endy = renderPositionsBottom[1];
/*  278 */             int length = xValues.length;
/*  279 */             int j2 = 0;
/*  280 */             while (j2 < length) {
/*  281 */               double bdubs = xValues[j2];
/*  282 */               if (bdubs < x2) {
/*  283 */                 x2 = bdubs;
/*      */               }
/*  285 */               j2++;
/*      */             } 
/*  287 */             int length2 = xValues.length;
/*  288 */             int k2 = 0;
/*  289 */             while (k2 < length2) {
/*  290 */               double bdubs = xValues[k2];
/*  291 */               if (bdubs > endx) {
/*  292 */                 endx = bdubs;
/*      */               }
/*  294 */               k2++;
/*      */             } 
/*  296 */             int length3 = yValues.length;
/*  297 */             int l2 = 0;
/*  298 */             while (l2 < length3) {
/*  299 */               double bdubs = yValues[l2];
/*  300 */               if (bdubs < y2) {
/*  301 */                 y2 = bdubs;
/*      */               }
/*  303 */               l2++;
/*      */             } 
/*  305 */             int length4 = yValues.length;
/*  306 */             int n2 = 0;
/*  307 */             while (n2 < length4) {
/*  308 */               double bdubs = yValues[n2];
/*  309 */               if (bdubs > endy) {
/*  310 */                 endy = bdubs;
/*      */               }
/*  312 */               n2++;
/*      */             } 
/*  314 */             double xDiff = (endx - x2) / 4.0D;
/*  315 */             double x2Diff = (endx - x2) / 4.0D;
/*      */             
/*  317 */             int color = Teams.getInstance.isOnSameTeam((Entity)ent) ? Colors.getColor(0, 255, 0, 255) : ((ent.hurtTime > 0) ? Colors.getColor(255, 0, 0, 255) : (ent.isInvisible() ? Colors.getColor(255, 255, 0, 255) : Colors.getColor(255, 255, 255, 255)));
/*  318 */             if (this.mode.is("ExBox")) {
/*  319 */               RenderUtil.rectangleBordered(x2 + 0.5D, y2 + 0.5D, endx - 0.5D, endy - 0.5D, 1.0D, Colors.getColor(0, 0, 0, 0), color);
/*  320 */               RenderUtil.rectangleBordered(x2 - 0.5D, y2 - 0.5D, endx + 0.5D, endy + 0.5D, 1.0D, Colors.getColor(0, 0), Colors.getColor(0, 150));
/*  321 */               RenderUtil.rectangleBordered(x2 + 1.5D, y2 + 1.5D, endx - 1.5D, endy - 1.5D, 1.0D, Colors.getColor(0, 0), Colors.getColor(0, 150));
/*  322 */             } else if (this.mode.is("CornerB")) {
/*  323 */               RenderUtil.rectangle(x2 + 0.5D, y2 + 0.5D, x2 + 1.5D, y2 + xDiff + 0.5D, color);
/*  324 */               RenderUtil.rectangle(x2 + 0.5D, endy - 0.5D, x2 + 1.5D, endy - xDiff - 0.5D, color);
/*  325 */               RenderUtil.rectangle(x2 - 0.5D, y2 + 0.5D, x2 + 0.5D, y2 + xDiff + 0.5D, Colors.getColor(0, 150));
/*  326 */               RenderUtil.rectangle(x2 + 1.5D, y2 + 2.5D, x2 + 2.5D, y2 + xDiff + 0.5D, Colors.getColor(0, 150));
/*  327 */               RenderUtil.rectangle(x2 - 0.5D, y2 + xDiff + 0.5D, x2 + 2.5D, y2 + xDiff + 1.5D, Colors.getColor(0, 150));
/*  328 */               RenderUtil.rectangle(x2 - 0.5D, endy - 0.5D, x2 + 0.5D, endy - xDiff - 0.5D, Colors.getColor(0, 150));
/*  329 */               RenderUtil.rectangle(x2 + 1.5D, endy - 2.5D, x2 + 2.5D, endy - xDiff - 0.5D, Colors.getColor(0, 150));
/*  330 */               RenderUtil.rectangle(x2 - 0.5D, endy - xDiff - 0.5D, x2 + 2.5D, endy - xDiff - 1.5D, Colors.getColor(0, 150));
/*  331 */               RenderUtil.rectangle(x2 + 1.0D, y2 + 0.5D, x2 + x2Diff, y2 + 1.5D, color);
/*  332 */               RenderUtil.rectangle(x2 - 0.5D, y2 - 0.5D, x2 + x2Diff, y2 + 0.5D, Colors.getColor(0, 150));
/*  333 */               RenderUtil.rectangle(x2 + 1.5D, y2 + 1.5D, x2 + x2Diff, y2 + 2.5D, Colors.getColor(0, 150));
/*  334 */               RenderUtil.rectangle(x2 + x2Diff, y2 - 0.5D, x2 + x2Diff + 1.0D, y2 + 2.5D, Colors.getColor(0, 150));
/*  335 */               RenderUtil.rectangle(x2 + 1.0D, endy - 0.5D, x2 + x2Diff, endy - 1.5D, color);
/*  336 */               RenderUtil.rectangle(x2 - 0.5D, endy + 0.5D, x2 + x2Diff, endy - 0.5D, Colors.getColor(0, 150));
/*  337 */               RenderUtil.rectangle(x2 + 1.5D, endy - 1.5D, x2 + x2Diff, endy - 2.5D, Colors.getColor(0, 150));
/*  338 */               RenderUtil.rectangle(x2 + x2Diff, endy + 0.5D, x2 + x2Diff + 1.0D, endy - 2.5D, Colors.getColor(0, 150));
/*  339 */               RenderUtil.rectangle(endx - 0.5D, y2 + 0.5D, endx - 1.5D, y2 + xDiff + 0.5D, color);
/*  340 */               RenderUtil.rectangle(endx - 0.5D, endy - 0.5D, endx - 1.5D, endy - xDiff - 0.5D, color);
/*  341 */               RenderUtil.rectangle(endx + 0.5D, y2 + 0.5D, endx - 0.5D, y2 + xDiff + 0.5D, Colors.getColor(0, 150));
/*  342 */               RenderUtil.rectangle(endx - 1.5D, y2 + 2.5D, endx - 2.5D, y2 + xDiff + 0.5D, Colors.getColor(0, 150));
/*  343 */               RenderUtil.rectangle(endx + 0.5D, y2 + xDiff + 0.5D, endx - 2.5D, y2 + xDiff + 1.5D, Colors.getColor(0, 150));
/*  344 */               RenderUtil.rectangle(endx + 0.5D, endy - 0.5D, endx - 0.5D, endy - xDiff - 0.5D, Colors.getColor(0, 150));
/*  345 */               RenderUtil.rectangle(endx - 1.5D, endy - 2.5D, endx - 2.5D, endy - xDiff - 0.5D, Colors.getColor(0, 150));
/*  346 */               RenderUtil.rectangle(endx + 0.5D, endy - xDiff - 0.5D, endx - 2.5D, endy - xDiff - 1.5D, Colors.getColor(0, 150));
/*  347 */               RenderUtil.rectangle(endx - 1.0D, y2 + 0.5D, endx - x2Diff, y2 + 1.5D, color);
/*  348 */               RenderUtil.rectangle(endx + 0.5D, y2 - 0.5D, endx - x2Diff, y2 + 0.5D, Colors.getColor(0, 150));
/*  349 */               RenderUtil.rectangle(endx - 1.5D, y2 + 1.5D, endx - x2Diff, y2 + 2.5D, Colors.getColor(0, 150));
/*  350 */               RenderUtil.rectangle(endx - x2Diff, y2 - 0.5D, endx - x2Diff - 1.0D, y2 + 2.5D, Colors.getColor(0, 150));
/*  351 */               RenderUtil.rectangle(endx - 1.0D, endy - 0.5D, endx - x2Diff, endy - 1.5D, color);
/*  352 */               RenderUtil.rectangle(endx + 0.5D, endy + 0.5D, endx - x2Diff, endy - 0.5D, Colors.getColor(0, 150));
/*  353 */               RenderUtil.rectangle(endx - 1.5D, endy - 1.5D, endx - x2Diff, endy - 2.5D, Colors.getColor(0, 150));
/*  354 */               RenderUtil.rectangle(endx - x2Diff, endy + 0.5D, endx - x2Diff - 1.0D, endy - 2.5D, Colors.getColor(0, 150));
/*      */             } 
/*  356 */             if (((Boolean)this.HEALTH.get()).booleanValue() && (this.mode
/*  357 */               .is("ExBox") || this.mode.is("CornerA") || this.mode.is("CornerB"))) {
/*  358 */               float health = ent.getHealth();
/*  359 */               float[] fractions = { 0.0F, 0.5F, 1.0F };
/*  360 */               Color[] colors = { Color.RED, Color.YELLOW, Color.GREEN };
/*  361 */               float progress = health / ent.getMaxHealth();
/*  362 */               Color customColor = (health >= 0.0F) ? blendColors(fractions, colors, progress).brighter() : Color.RED;
/*  363 */               double difference = y2 - endy + 0.5D;
/*  364 */               double healthLocation = endy + difference * progress;
/*  365 */               RenderUtil.rectangleBordered(x2 - 6.5D, y2 - 0.5D, x2 - 2.5D, endy, 1.0D, Colors.getColor(0, 100), Colors.getColor(0, 150));
/*  366 */               RenderUtil.rectangle(x2 - 5.5D, endy - 1.0D, x2 - 3.5D, healthLocation, customColor.getRGB());
/*  367 */               if (-difference > 50.0D) {
/*  368 */                 int i2 = 1;
/*  369 */                 while (i2 < 10) {
/*  370 */                   double dThing = difference / 10.0D * i2;
/*  371 */                   RenderUtil.rectangle(x2 - 6.5D, endy - 0.5D + dThing, x2 - 2.5D, endy - 0.5D + dThing - 1.0D, Colors.getColor(0));
/*  372 */                   i2++;
/*      */                 } 
/*      */               } 
/*  375 */               if ((int)getIncremental((progress * 100.0F), 1.0D) <= 40) {
/*  376 */                 GlStateManager.pushMatrix();
/*  377 */                 GlStateManager.scale(2.0F, 2.0F, 2.0F);
/*  378 */                 GlStateManager.popMatrix();
/*      */               } 
/*      */             } 
/*  381 */           } catch (Exception xValues) {
/*  382 */             xValues.printStackTrace();
/*      */           } 
/*      */         }
/*  385 */         GlStateManager.popMatrix();
/*  386 */         GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
/*      */       } 
/*  388 */       GL11.glScalef(1.0F, 1.0F, 1.0F);
/*  389 */       GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
/*  390 */       GlStateManager.popMatrix();
/*  391 */       RenderUtil.rectangle(0.0D, 0.0D, 0.0D, 0.0D, -1);
/*      */     } 
/*      */   }
/*      */   
/*      */   @EventHandler(3)
/*      */   public void onRender1(EventRender3D event) {
/*  397 */     if (!isEnabled()) {
/*      */       return;
/*      */     }
/*  400 */     if (this.h > 255.0F) {
/*  401 */       this.h = 0.0F;
/*      */     }
/*  403 */     this.h = (float)(this.h + 0.1D);
/*  404 */     if (this.mode.is("Point")) {
/*  405 */       collectEntities();
/*  406 */       List<Entity> collectedEntities = this.collectedEntities;
/*  407 */       for (Entity collectedEntity : collectedEntities) {
/*  408 */         EntityLivingBase entity = (EntityLivingBase)collectedEntity;
/*  409 */         if (entity instanceof EntityPlayer) {
/*  410 */           drawCircle(entity, (new Color(((Double)HUD.r.get()).intValue(), ((Double)HUD.g.get()).intValue(), ((Double)HUD.b
/*  411 */                 .get()).intValue())).getRGB(), event);
/*      */         }
/*      */       } 
/*      */     } 
/*  415 */     if (this.mode.is("Csgo")) {
/*  416 */       csgo();
/*  417 */     } else if (this.mode.is("Box")) {
/*  418 */       doBoxESP(event);
/*  419 */     } else if (this.mode.is("Other2D")) {
/*  420 */       doOther2DESP(event);
/*  421 */     } else if (this.mode.is("2DBox")) {
/*  422 */       doCornerESP(event);
/*      */     } 
/*      */   }
/*      */   
/*      */   private void doCornerESP(EventRender3D e) {
/*  427 */     if (!isEnabled()) {
/*      */       return;
/*      */     }
/*  430 */     for (EntityPlayer entity : mc.theWorld.playerEntities) {
/*  431 */       if (entity != mc.thePlayer) {
/*  432 */         Color color; if (!isValid((EntityLivingBase)entity) && !entity.isInvisible()) {
/*      */           return;
/*      */         }
/*  435 */         if (entity.isInvisible() && entity != mc.thePlayer) {
/*      */           return;
/*      */         }
/*  438 */         GL11.glPushMatrix();
/*  439 */         GL11.glEnable(3042);
/*  440 */         GL11.glDisable(2929);
/*  441 */         GL11.glNormal3f(0.0F, 1.0F, 0.0F);
/*  442 */         GlStateManager.enableBlend();
/*  443 */         GL11.glBlendFunc(770, 771);
/*  444 */         GL11.glDisable(3553);
/*  445 */         double renderPosX = (mc.getRenderManager()).viewerPosX;
/*  446 */         double renderPosY = (mc.getRenderManager()).viewerPosY;
/*  447 */         double renderPosZ = (mc.getRenderManager()).viewerPosZ;
/*  448 */         float partialTicks = e.getPartialTicks();
/*  449 */         double x = entity.lastTickPosX + (entity.posX - entity.lastTickPosX) * partialTicks - renderPosX;
/*      */         
/*  451 */         double y = entity.lastTickPosY + (entity.posY - entity.lastTickPosY) * partialTicks - renderPosY;
/*      */         
/*  453 */         double z = entity.lastTickPosZ + (entity.posZ - entity.lastTickPosZ) * partialTicks - renderPosZ;
/*      */         
/*  455 */         float DISTANCE = mc.thePlayer.getDistanceToEntity((Entity)entity);
/*  456 */         float SCALE = 0.035F;
/*  457 */         SCALE /= 2.0F;
/*  458 */         GlStateManager.translate((float)x, (float)y + entity.height + 0.5F - (
/*  459 */             entity.isChild() ? (entity.height / 2.0F) : 0.0F), (float)z);
/*  460 */         GL11.glNormal3f(0.0F, 1.0F, 0.0F);
/*  461 */         GlStateManager.rotate(-(mc.getRenderManager()).playerViewY, 0.0F, 1.0F, 0.0F);
/*  462 */         GL11.glScalef(-SCALE, -SCALE, -SCALE);
/*      */ 
/*      */         
/*  465 */         if (entity.hurtTime > 0) {
/*  466 */           color = new Color(Color.RED.getRGB());
/*      */         } else {
/*  468 */           color = new Color(Color.WHITE.getRGB());
/*      */         } 
/*      */         
/*  471 */         Color gray = new Color(0, 0, 0);
/*  472 */         double thickness = (2.0F + DISTANCE * 0.08F);
/*  473 */         double xLeft = -30.0D;
/*  474 */         double xRight = 30.0D;
/*  475 */         double yUp = 20.0D;
/*  476 */         double yDown = 130.0D;
/*  477 */         double size = 10.0D;
/*      */         
/*  479 */         drawVerticalLine(xLeft + size / 2.0D - 1.0D, yUp + 1.0D, size / 2.0D, thickness, gray);
/*  480 */         drawHorizontalLine(xLeft + 1.0D, yUp + size, size, thickness, gray);
/*      */         
/*  482 */         drawVerticalLine(xLeft + size / 2.0D - 1.0D, yUp, size / 2.0D, thickness, color);
/*  483 */         drawHorizontalLine(xLeft, yUp + size, size, thickness, color);
/*      */         
/*  485 */         drawVerticalLine(xRight - size / 2.0D + 1.0D, yUp + 1.0D, size / 2.0D, thickness, gray);
/*  486 */         drawHorizontalLine(xRight - 1.0D, yUp + size, size, thickness, gray);
/*      */         
/*  488 */         drawVerticalLine(xRight - size / 2.0D + 1.0D, yUp, size / 2.0D, thickness, color);
/*  489 */         drawHorizontalLine(xRight, yUp + size, size, thickness, color);
/*      */         
/*  491 */         drawVerticalLine(xLeft + size / 2.0D - 1.0D, yDown - 1.0D, size / 2.0D, thickness, gray);
/*  492 */         drawHorizontalLine(xLeft + 1.0D, yDown - size, size, thickness, gray);
/*      */         
/*  494 */         drawVerticalLine(xLeft + size / 2.0D - 1.0D, yDown, size / 2.0D, thickness, color);
/*  495 */         drawHorizontalLine(xLeft, yDown - size, size, thickness, color);
/*      */         
/*  497 */         drawVerticalLine(xRight - size / 2.0D + 1.0D, yDown - 1.0D, size / 2.0D, thickness, gray);
/*  498 */         drawHorizontalLine(xRight - 1.0D, yDown - size, size, thickness, gray);
/*      */         
/*  500 */         drawVerticalLine(xRight - size / 2.0D + 1.0D, yDown, size / 2.0D, thickness, color);
/*  501 */         drawHorizontalLine(xRight, yDown - size, size, thickness, color);
/*  502 */         GL11.glEnable(3553);
/*  503 */         GL11.glEnable(2929);
/*  504 */         GlStateManager.disableBlend();
/*  505 */         GL11.glDisable(3042);
/*  506 */         GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
/*  507 */         GL11.glNormal3f(1.0F, 1.0F, 1.0F);
/*  508 */         GL11.glPopMatrix();
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   private void doBoxESP(EventRender3D event) {
/*  515 */     if (!isEnabled()) {
/*      */       return;
/*      */     }
/*  518 */     GL11.glBlendFunc(770, 771);
/*  519 */     GL11.glEnable(3042);
/*  520 */     GL11.glEnable(2848);
/*  521 */     GL11.glLineWidth(2.0F);
/*  522 */     GL11.glDisable(3553);
/*  523 */     GL11.glDisable(2929);
/*  524 */     GL11.glDepthMask(false);
/*  525 */     for (EntityPlayer o : mc.theWorld.playerEntities) {
/*  526 */       if (o != null && o != mc.thePlayer) {
/*  527 */         if (Teams.getInstance.isOnSameTeam((Entity)o)) {
/*  528 */           RenderUtil.entityESPBox((Entity)o, new Color(0, 255, 0), event); continue;
/*  529 */         }  if (o.hurtTime > 0) {
/*  530 */           RenderUtil.entityESPBox((Entity)o, new Color(255, 0, 0), event); continue;
/*      */         } 
/*  532 */         if (Client.instance.friendManager.isFriend(o.getName())) {
/*  533 */           RenderUtil.entityESPBox((Entity)o, new Color(0.078431375F, 0.19607843F, 1.0F), event);
/*      */           continue;
/*      */         } 
/*  536 */         RenderUtil.entityESPBox((Entity)o, new Color(1.0F, 1.0F, 1.0F), event);
/*      */       } 
/*      */     } 
/*      */ 
/*      */     
/*  541 */     GL11.glDisable(2848);
/*  542 */     GL11.glEnable(3553);
/*  543 */     GL11.glEnable(2929);
/*  544 */     GL11.glDepthMask(true);
/*  545 */     GL11.glDisable(3042);
/*      */   }
/*      */   
/*      */   public boolean isValid(EntityLivingBase entity) {
/*  549 */     if (entity instanceof EntityPlayer && entity.getHealth() >= 0.0F) {
/*  550 */       return (entity != mc.thePlayer || ((Boolean)this.self.get()).booleanValue());
/*      */     }
/*  552 */     return false;
/*      */   }
/*      */   
/*      */   private void doOther2DESP(EventRender3D e) {
/*  556 */     if (!isEnabled()) {
/*      */       return;
/*      */     }
/*  559 */     for (EntityPlayer entity : mc.theWorld.playerEntities) {
/*  560 */       int COLOR, c; if (!isValid((EntityLivingBase)entity) || 
/*  561 */         !entity.isEntityAlive() || (
/*  562 */         mc.gameSettings.thirdPersonView == 0 && 
/*  563 */         entity == mc.thePlayer)) {
/*      */         continue;
/*      */       }
/*      */       
/*  567 */       GL11.glPushMatrix();
/*  568 */       GL11.glEnable(3042);
/*  569 */       GL11.glDisable(2929);
/*  570 */       GL11.glNormal3f(0.0F, 1.0F, 0.0F);
/*  571 */       GlStateManager.enableBlend();
/*  572 */       GL11.glBlendFunc(770, 771);
/*  573 */       GL11.glDisable(3553);
/*  574 */       double renderPosX = (mc.getRenderManager()).viewerPosX;
/*  575 */       double renderPosY = (mc.getRenderManager()).viewerPosY;
/*  576 */       double renderPosZ = (mc.getRenderManager()).viewerPosZ;
/*  577 */       float partialTicks = e.getPartialTicks();
/*      */       
/*  579 */       double x = entity.lastTickPosX + (entity.posX - entity.lastTickPosX) * partialTicks - renderPosX;
/*      */       
/*  581 */       double y = entity.lastTickPosY + (entity.posY - entity.lastTickPosY) * partialTicks - renderPosY;
/*      */       
/*  583 */       double z = entity.lastTickPosZ + (entity.posZ - entity.lastTickPosZ) * partialTicks - renderPosZ;
/*  584 */       float DISTANCE = mc.thePlayer.getDistanceToEntity((Entity)entity);
/*  585 */       float SCALE = 0.035F;
/*  586 */       SCALE /= 2.0F;
/*  587 */       entity.isChild();
/*  588 */       GlStateManager.translate((float)x, (float)y + entity.height + 0.5F - (
/*  589 */           entity.isChild() ? (entity.height / 2.0F) : 0.0F), (float)z);
/*      */       
/*  591 */       GL11.glNormal3f(0.0F, 1.0F, 0.0F);
/*  592 */       GlStateManager.rotate(-(mc.getRenderManager()).playerViewY, 0.0F, 1.0F, 0.0F);
/*  593 */       GL11.glScalef(-SCALE, -SCALE, -SCALE);
/*  594 */       float HEALTH = entity.getHealth();
/*      */       
/*  596 */       if (HEALTH > 20.0D) {
/*  597 */         COLOR = -65292;
/*  598 */       } else if (HEALTH >= 10.0D) {
/*  599 */         COLOR = -16711936;
/*  600 */       } else if (HEALTH >= 3.0D) {
/*  601 */         COLOR = -23296;
/*      */       } else {
/*  603 */         COLOR = -65536;
/*      */       } 
/*  605 */       new Color(0, 0, 0);
/*  606 */       double thickness = (1.5F + DISTANCE * 0.01F);
/*  607 */       double xLeft = -20.0D;
/*  608 */       double xRight = 20.0D;
/*  609 */       double yUp = 27.0D;
/*  610 */       double yDown = 130.0D;
/*  611 */       Color color = new Color(255, 255, 255);
/*  612 */       if (entity.hurtTime > 0) {
/*  613 */         color = new Color(255, 0, 0);
/*  614 */       } else if (Teams.getInstance.isOnSameTeam((Entity)entity)) {
/*  615 */         color = new Color(0, 255, 0);
/*      */       } 
/*      */       
/*  618 */       drawBorderedRect((float)xLeft, (float)yUp, (float)xRight, (float)yDown, (float)thickness + 0.5F, Color.BLACK.getRGB(), 0);
/*  619 */       drawBorderedRect((float)xLeft, (float)yUp, (float)xRight, (float)yDown, (float)thickness, color.getRGB(), 0);
/*      */       
/*  621 */       drawBorderedRect((float)xLeft - 3.0F - DISTANCE * 0.2F, (float)yDown - (float)(yDown - yUp), (float)xLeft - 2.0F, (float)yDown, 0.15F, Color.BLACK.getRGB(), (new Color(100, 100, 100)).getRGB());
/*  622 */       drawBorderedRect((float)xLeft - 3.0F - DISTANCE * 0.2F, (float)yDown - (float)(yDown - yUp) * Math.min(1.0F, entity.getHealth() / 20.0F), (float)xLeft - 2.0F, (float)yDown, 0.15F, Color.BLACK.getRGB(), COLOR);
/*      */       
/*  624 */       if (entity.getHealth() < 5.0F) {
/*  625 */         c = (new Color(255, 20, 10)).getRGB();
/*  626 */       } else if (entity.getHealth() < 12.5D) {
/*  627 */         c = (new Color(16774441)).getRGB();
/*      */       } else {
/*  629 */         c = (new Color(0, 255, 0)).getRGB();
/*      */       } 
/*  631 */       mc.fontRendererObj.drawStringWithShadowX2(String.valueOf((int)entity.getHealth()), (float)xLeft - mc.fontRendererObj.getStringWidth(String.valueOf((int)entity.getHealth())) - 10.0F, (float)yDown / 2.0F, c);
/*  632 */       mc.fontRendererObj.drawStringWithShadowX2((entity.getHeldItem() == null) ? "" : entity.getHeldItem().getDisplayName(), (int)(xLeft / 2.0D - (mc.fontRendererObj.getStringWidth((entity.getHeldItem() == null) ? "" : entity.getHeldItem().getDisplayName()) / 2)), ((int)yDown + 10), (new Color(0, 255, 0)).getRGB());
/*  633 */       if (((Boolean)this.xyzRender.get()).booleanValue()) {
/*  634 */         String svar = "XYZ: " + (int)entity.posX + " " + (int)entity.posY + " " + (int)entity.posZ + " 锟斤拷锟斤拷: " + (int)DISTANCE + "m";
/*  635 */         mc.fontRendererObj.drawCenteredStringWithShadowX2(svar, (float)(xRight - mc.fontRendererObj.getStringWidth(svar)) / 8.0F, ((int)yDown + ((entity.getHeldItem() == null) ? 10 : 20)), (new Color(255, 255, 255)).getRGB());
/*      */       } 
/*  637 */       int y2 = 0;
/*  638 */       for (PotionEffect effect : entity.getActivePotionEffects()) {
/*  639 */         Potion potion = Potion.potionTypes[effect.getPotionID()];
/*  640 */         String PType = I18n.format(potion.getName(), new Object[0]);
/*  641 */         switch (effect.getAmplifier()) {
/*      */           case 1:
/*  643 */             PType = PType + " II";
/*      */             break;
/*      */           
/*      */           case 2:
/*  647 */             PType = PType + " III";
/*      */             break;
/*      */           
/*      */           case 3:
/*  651 */             PType = PType + " IV";
/*      */             break;
/*      */         } 
/*  654 */         if (effect.getDuration() < 600 && effect.getDuration() > 300) {
/*  655 */           PType = PType + "§7:§6 " + Potion.getDurationString(effect);
/*  656 */         } else if (effect.getDuration() < 300) {
/*  657 */           PType = PType + "§7:§c " + Potion.getDurationString(effect);
/*  658 */         } else if (effect.getDuration() > 600) {
/*  659 */           PType = PType + "§7:§7 " + Potion.getDurationString(effect);
/*      */         } 
/*  661 */         mc.fontRendererObj.drawStringWithShadow(PType, (float)xLeft - mc.fontRendererObj.getStringWidth(PType) - 5.0F, (float)yUp - mc.fontRendererObj.FONT_HEIGHT + y2 + 20.0F, potion.getLiquidColor());
/*  662 */         y2 -= 10;
/*      */       } 
/*      */ 
/*      */       
/*  666 */       GL11.glEnable(3553);
/*  667 */       GL11.glEnable(2929);
/*  668 */       GlStateManager.disableBlend();
/*  669 */       GL11.glDisable(3042);
/*  670 */       GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
/*  671 */       GL11.glNormal3f(1.0F, 1.0F, 1.0F);
/*  672 */       GL11.glPopMatrix();
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public void drawBorderedRect(float x, float y, float x2, float y2, float l1, int col1, int col2) {
/*  678 */     drawRect(x, y, x2, y2, col2);
/*  679 */     float f = (col1 >> 24 & 0xFF) / 255.0F;
/*  680 */     float f1 = (col1 >> 16 & 0xFF) / 255.0F;
/*  681 */     float f2 = (col1 >> 8 & 0xFF) / 255.0F;
/*  682 */     float f3 = (col1 & 0xFF) / 255.0F;
/*  683 */     GL11.glEnable(3042);
/*  684 */     GL11.glDisable(3553);
/*  685 */     GL11.glBlendFunc(770, 771);
/*  686 */     GL11.glEnable(2848);
/*  687 */     GL11.glPushMatrix();
/*  688 */     GL11.glColor4f(f1, f2, f3, f);
/*  689 */     GL11.glLineWidth(l1);
/*  690 */     GL11.glBegin(1);
/*  691 */     GL11.glVertex2d(x, y);
/*  692 */     GL11.glVertex2d(x, y2);
/*  693 */     GL11.glVertex2d(x2, y2);
/*  694 */     GL11.glVertex2d(x2, y);
/*  695 */     GL11.glVertex2d(x, y);
/*  696 */     GL11.glVertex2d(x2, y);
/*  697 */     GL11.glVertex2d(x, y2);
/*  698 */     GL11.glVertex2d(x2, y2);
/*  699 */     GL11.glEnd();
/*  700 */     GL11.glPopMatrix();
/*  701 */     GL11.glEnable(3553);
/*  702 */     GL11.glDisable(3042);
/*  703 */     GL11.glDisable(2848);
/*      */   }
/*      */   
/*      */   public void drawRect(float g, float h, float i, float j, int col1) {
/*  707 */     float f = (col1 >> 24 & 0xFF) / 255.0F;
/*  708 */     float f1 = (col1 >> 16 & 0xFF) / 255.0F;
/*  709 */     float f2 = (col1 >> 8 & 0xFF) / 255.0F;
/*  710 */     float f3 = (col1 & 0xFF) / 255.0F;
/*  711 */     GL11.glEnable(3042);
/*  712 */     GL11.glDisable(3553);
/*  713 */     GL11.glBlendFunc(770, 771);
/*  714 */     GL11.glEnable(2848);
/*  715 */     GL11.glPushMatrix();
/*  716 */     GL11.glColor4f(f1, f2, f3, f);
/*  717 */     GL11.glBegin(7);
/*  718 */     GL11.glVertex2d(i, h);
/*  719 */     GL11.glVertex2d(g, h);
/*  720 */     GL11.glVertex2d(g, j);
/*  721 */     GL11.glVertex2d(i, j);
/*  722 */     GL11.glEnd();
/*  723 */     GL11.glPopMatrix();
/*  724 */     GL11.glEnable(3553);
/*  725 */     GL11.glDisable(3042);
/*  726 */     GL11.glDisable(2848);
/*      */   }
/*      */   
/*      */   private void drawVerticalLine(double xPos, double yPos, double xSize, double thickness, Color color) {
/*  730 */     Tessellator tesselator = Tessellator.getInstance();
/*  731 */     WorldRenderer worldRenderer = tesselator.getWorldRenderer();
/*  732 */     worldRenderer.begin(7, DefaultVertexFormats.POSITION_COLOR);
/*  733 */     worldRenderer.pos(xPos - xSize, yPos - thickness / 2.0D, 0.0D).color(color.getRed() / 255.0F, color
/*  734 */         .getGreen() / 255.0F, color.getBlue() / 255.0F, color.getAlpha() / 255.0F)
/*  735 */       .endVertex();
/*  736 */     worldRenderer.pos(xPos - xSize, yPos + thickness / 2.0D, 0.0D).color(color.getRed() / 255.0F, color
/*  737 */         .getGreen() / 255.0F, color.getBlue() / 255.0F, color.getAlpha() / 255.0F)
/*  738 */       .endVertex();
/*  739 */     worldRenderer.pos(xPos + xSize, yPos + thickness / 2.0D, 0.0D).color(color.getRed() / 255.0F, color
/*  740 */         .getGreen() / 255.0F, color.getBlue() / 255.0F, color.getAlpha() / 255.0F)
/*  741 */       .endVertex();
/*  742 */     worldRenderer.pos(xPos + xSize, yPos - thickness / 2.0D, 0.0D).color(color.getRed() / 255.0F, color
/*  743 */         .getGreen() / 255.0F, color.getBlue() / 255.0F, color.getAlpha() / 255.0F)
/*  744 */       .endVertex();
/*  745 */     tesselator.draw();
/*      */   }
/*      */   
/*      */   private void drawHorizontalLine(double xPos, double yPos, double ySize, double thickness, Color color) {
/*  749 */     Tessellator tesselator = Tessellator.getInstance();
/*  750 */     WorldRenderer worldRenderer = tesselator.getWorldRenderer();
/*  751 */     worldRenderer.begin(7, DefaultVertexFormats.POSITION_COLOR);
/*  752 */     worldRenderer.pos(xPos - thickness / 2.0D, yPos - ySize, 0.0D).color(color.getRed() / 255.0F, color
/*  753 */         .getGreen() / 255.0F, color.getBlue() / 255.0F, color.getAlpha() / 255.0F)
/*  754 */       .endVertex();
/*  755 */     worldRenderer.pos(xPos - thickness / 2.0D, yPos + ySize, 0.0D).color(color.getRed() / 255.0F, color
/*  756 */         .getGreen() / 255.0F, color.getBlue() / 255.0F, color.getAlpha() / 255.0F)
/*  757 */       .endVertex();
/*  758 */     worldRenderer.pos(xPos + thickness / 2.0D, yPos + ySize, 0.0D).color(color.getRed() / 255.0F, color
/*  759 */         .getGreen() / 255.0F, color.getBlue() / 255.0F, color.getAlpha() / 255.0F)
/*  760 */       .endVertex();
/*  761 */     worldRenderer.pos(xPos + thickness / 2.0D, yPos - ySize, 0.0D).color(color.getRed() / 255.0F, color
/*  762 */         .getGreen() / 255.0F, color.getBlue() / 255.0F, color.getAlpha() / 255.0F)
/*  763 */       .endVertex();
/*  764 */     tesselator.draw();
/*      */   }
/*      */   
/*      */   public double getIncremental(double val, double inc) {
/*  768 */     double one = 1.0D / inc;
/*  769 */     return Math.round(val * one) / one;
/*      */   }
/*      */ 
/*      */   
/*      */   public Color blendColors(float[] fractions, Color[] colors, float progress) {
/*  774 */     if (fractions == null) {
/*  775 */       throw new IllegalArgumentException("Fractions can't be null");
/*      */     }
/*  777 */     if (colors == null) {
/*  778 */       throw new IllegalArgumentException("Colours can't be null");
/*      */     }
/*  780 */     if (fractions.length != colors.length) {
/*  781 */       throw new IllegalArgumentException("Fractions and colours must have equal number of elements");
/*      */     }
/*  783 */     int[] indicies = getFractionIndicies(fractions, progress);
/*  784 */     float[] range = { fractions[indicies[0]], fractions[indicies[1]] };
/*  785 */     Color[] colorRange = { colors[indicies[0]], colors[indicies[1]] };
/*  786 */     float max = range[1] - range[0];
/*  787 */     float value = progress - range[0];
/*  788 */     float weight = value / max;
/*  789 */     return blend(colorRange[0], colorRange[1], (1.0F - weight));
/*      */   }
/*      */   
/*      */   public Color blend(Color color1, Color color2, double ratio) {
/*  793 */     float r2 = (float)ratio;
/*  794 */     float ir2 = 1.0F - r2;
/*  795 */     float[] rgb1 = new float[3];
/*  796 */     float[] rgb2 = new float[3];
/*  797 */     color1.getColorComponents(rgb1);
/*  798 */     color2.getColorComponents(rgb2);
/*  799 */     float red = rgb1[0] * r2 + rgb2[0] * ir2;
/*  800 */     float green = rgb1[1] * r2 + rgb2[1] * ir2;
/*  801 */     float blue = rgb1[2] * r2 + rgb2[2] * ir2;
/*  802 */     if (red < 0.0F) {
/*  803 */       red = 0.0F;
/*  804 */     } else if (red > 255.0F) {
/*  805 */       red = 255.0F;
/*      */     } 
/*  807 */     if (green < 0.0F) {
/*  808 */       green = 0.0F;
/*  809 */     } else if (green > 255.0F) {
/*  810 */       green = 255.0F;
/*      */     } 
/*  812 */     if (blue < 0.0F) {
/*  813 */       blue = 0.0F;
/*  814 */     } else if (blue > 255.0F) {
/*  815 */       blue = 255.0F;
/*      */     } 
/*  817 */     Color color3 = null;
/*      */     try {
/*  819 */       color3 = new Color(red, green, blue);
/*  820 */     } catch (IllegalArgumentException exp) {
/*  821 */       NumberFormat nf = NumberFormat.getNumberInstance();
/*  822 */       System.out.println(nf.format(red) + "; " + nf.format(green) + "; " + nf.format(blue));
/*  823 */       exp.printStackTrace();
/*      */     } 
/*  825 */     return color3;
/*      */   }
/*      */   
/*      */   public int[] getFractionIndicies(float[] fractions, float progress) {
/*  829 */     int[] range = new int[2];
/*  830 */     int startPoint = 0;
/*  831 */     while (startPoint < fractions.length && fractions[startPoint] <= progress) {
/*  832 */       startPoint++;
/*      */     }
/*  834 */     if (startPoint >= fractions.length) {
/*  835 */       startPoint = fractions.length - 1;
/*      */     }
/*  837 */     range[0] = startPoint - 1;
/*  838 */     range[1] = startPoint;
/*  839 */     return range;
/*      */   }
/*      */   
/*      */   private void updatePositions() {
/*  843 */     this.entityConvertedPointsMap.clear();
/*  844 */     float pTicks = mc.timer.renderPartialTicks;
/*  845 */     for (Entity e2 : mc.theWorld.getLoadedEntityList()) {
/*      */       EntityPlayer ent;
/*      */       
/*  848 */       if (!(e2 instanceof EntityPlayer) || (ent = (EntityPlayer)e2) == mc.thePlayer)
/*  849 */         continue;  double x2 = ent.lastTickPosX + (ent.posX - ent.lastTickPosX) * pTicks - (mc.getRenderManager()).viewerPosX + 0.36D;
/*  850 */       double y2 = ent.lastTickPosY + (ent.posY - ent.lastTickPosY) * pTicks - (mc.getRenderManager()).viewerPosY;
/*  851 */       double z2 = ent.lastTickPosZ + (ent.posZ - ent.lastTickPosZ) * pTicks - (mc.getRenderManager()).viewerPosZ + 0.36D;
/*  852 */       double topY = y2 + ent.height + 0.15D;
/*  853 */       double[] convertedPoints = RenderUtil.convertTo2D(x2, y2, z2);
/*  854 */       double[] convertedPoints2 = RenderUtil.convertTo2D(x2 - 0.36D, y2, z2 - 0.36D);
/*  855 */       if (((convertedPoints2 != null) ? convertedPoints2[2] : 0.0D) < 0.0D || ((convertedPoints2 != null) ? convertedPoints2[2] : 0.0D) >= 1.0D)
/*      */         continue; 
/*  857 */       x2 = ent.lastTickPosX + (ent.posX - ent.lastTickPosX) * pTicks - (mc.getRenderManager()).viewerPosX - 0.36D;
/*  858 */       z2 = ent.lastTickPosZ + (ent.posZ - ent.lastTickPosZ) * pTicks - (mc.getRenderManager()).viewerPosZ - 0.36D;
/*  859 */       double[] convertedPointsBottom = RenderUtil.convertTo2D(x2, y2, z2);
/*  860 */       y2 = ent.lastTickPosY + (ent.posY - ent.lastTickPosY) * pTicks - (mc.getRenderManager()).viewerPosY - 0.05D;
/*  861 */       double[] convertedPointsx = RenderUtil.convertTo2D(x2, y2, z2);
/*  862 */       x2 = ent.lastTickPosX + (ent.posX - ent.lastTickPosX) * pTicks - (mc.getRenderManager()).viewerPosX - 0.36D;
/*  863 */       z2 = ent.lastTickPosZ + (ent.posZ - ent.lastTickPosZ) * pTicks - (mc.getRenderManager()).viewerPosZ + 0.36D;
/*  864 */       double[] convertedPointsTop1 = RenderUtil.convertTo2D(x2, topY, z2);
/*  865 */       double[] convertedPointsx2 = RenderUtil.convertTo2D(x2, y2, z2);
/*  866 */       x2 = ent.lastTickPosX + (ent.posX - ent.lastTickPosX) * pTicks - (mc.getRenderManager()).viewerPosX + 0.36D;
/*  867 */       z2 = ent.lastTickPosZ + (ent.posZ - ent.lastTickPosZ) * pTicks - (mc.getRenderManager()).viewerPosZ + 0.36D;
/*  868 */       double[] convertedPointsz = RenderUtil.convertTo2D(x2, y2, z2);
/*  869 */       x2 = ent.lastTickPosX + (ent.posX - ent.lastTickPosX) * pTicks - (mc.getRenderManager()).viewerPosX + 0.36D;
/*  870 */       z2 = ent.lastTickPosZ + (ent.posZ - ent.lastTickPosZ) * pTicks - (mc.getRenderManager()).viewerPosZ - 0.36D;
/*  871 */       double[] convertedPointsTop2 = RenderUtil.convertTo2D(x2, topY, z2);
/*  872 */       double[] convertedPointsz2 = RenderUtil.convertTo2D(x2, y2, z2);
/*  873 */       if (convertedPoints != null && 
/*  874 */         convertedPointsBottom != null && 
/*  875 */         convertedPointsx != null && 
/*  876 */         convertedPointsx2 != null && 
/*  877 */         convertedPointsTop2 != null && 
/*  878 */         convertedPointsz2 != null && 
/*  879 */         convertedPointsz != null && 
/*  880 */         convertedPointsTop1 != null) {
/*  881 */         this.entityConvertedPointsMap.put(ent, new double[] { convertedPoints[0], convertedPoints[1], 0.0D, convertedPoints[2], convertedPointsBottom[0], convertedPointsBottom[1], convertedPointsBottom[2], convertedPointsx[0], convertedPointsx[1], convertedPointsx[2], convertedPointsx2[0], convertedPointsx2[1], convertedPointsx2[2], convertedPointsz[0], convertedPointsz[1], convertedPointsz[2], convertedPointsz2[0], convertedPointsz2[1], convertedPointsz2[2], convertedPointsTop1[0], convertedPointsTop1[1], convertedPointsTop1[2], convertedPointsTop2[0], convertedPointsTop2[1], convertedPointsTop2[2] });
/*      */       }
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void csgo() {
/*  898 */     if (!isEnabled()) {
/*      */       return;
/*      */     }
/*  901 */     for (Object o : mc.theWorld.loadedEntityList) {
/*  902 */       if (o instanceof EntityPlayer && o != mc.thePlayer) {
/*  903 */         EntityPlayer ent = (EntityPlayer)o;
/*  904 */         double x = ent.lastTickPosX + (ent.posX - ent.lastTickPosX) * mc.timer.renderPartialTicks - (mc.getRenderManager()).renderPosX;
/*  905 */         double y = ent.lastTickPosY + (ent.posY - ent.lastTickPosY) * mc.timer.renderPartialTicks - (mc.getRenderManager()).renderPosY;
/*  906 */         double z = ent.lastTickPosZ + (ent.posZ - ent.lastTickPosZ) * mc.timer.renderPartialTicks - (mc.getRenderManager()).renderPosZ;
/*  907 */         x -= 0.275D;
/*  908 */         z -= 0.275D;
/*  909 */         y += ent.getEyeHeight() - 0.225D - (ent.isSneaking() ? 0.25D : 0.0D);
/*  910 */         double mid = 0.275D;
/*  911 */         GL11.glPushMatrix();
/*  912 */         GL11.glEnable(3042);
/*  913 */         GL11.glBlendFunc(770, 771);
/*  914 */         double rotAdd = -0.25D * (Math.abs(ent.rotationPitch) / 90.0F);
/*  915 */         GL11.glTranslated(0.0D, rotAdd, 0.0D);
/*  916 */         GL11.glTranslated(x + 0.275D, y + 0.275D, z + 0.275D);
/*  917 */         GL11.glRotated((-ent.rotationYaw % 360.0F), 0.0D, 1.0D, 0.0D);
/*  918 */         GL11.glTranslated(-(x + 0.275D), -(y + 0.275D), -(z + 0.275D));
/*  919 */         GL11.glTranslated(x + 0.275D, y + 0.275D, z + 0.275D);
/*  920 */         GL11.glRotated(ent.rotationPitch, 1.0D, 0.0D, 0.0D);
/*  921 */         GL11.glTranslated(-(x + 0.275D), -(y + 0.275D), -(z + 0.275D));
/*  922 */         GL11.glDisable(3553);
/*  923 */         GL11.glEnable(2848);
/*  924 */         GL11.glDisable(2929);
/*  925 */         GL11.glDepthMask(false);
/*  926 */         GL11.glColor4f(1.0F, 1.0F, 1.0F, 0.5F);
/*  927 */         RenderUtil.drawBoundingBox(new AxisAlignedBB(x - 0.0025D, y - 0.0025D, z - 0.0025D, x + 0.55D + 0.0025D, y + 0.55D + 0.0025D, z + 0.55D + 0.0025D));
/*  928 */         GL11.glDisable(2848);
/*  929 */         GL11.glEnable(3553);
/*  930 */         GL11.glEnable(2929);
/*  931 */         GL11.glDepthMask(true);
/*  932 */         GL11.glDisable(3042);
/*  933 */         GL11.glPopMatrix();
/*      */       } 
/*      */     } 
/*      */   }
/*      */   
/*      */   private boolean isValid(Entity entity) {
/*  939 */     if (entity == mc.thePlayer) {
/*  940 */       return false;
/*      */     }
/*  942 */     if (entity.isDead) {
/*  943 */       return false;
/*      */     }
/*  945 */     if (entity.isInvisible()) {
/*  946 */       return false;
/*      */     }
/*  948 */     if (entity instanceof net.minecraft.entity.item.EntityItem) {
/*  949 */       return false;
/*      */     }
/*  951 */     if (entity instanceof net.minecraft.entity.passive.EntityAnimal) {
/*  952 */       return false;
/*      */     }
/*  954 */     return entity instanceof EntityPlayer;
/*      */   }
/*      */ 
/*      */   
/*      */   public void drawCircle(EntityLivingBase entity, int color, EventRender3D e) {
/*  959 */     double x = entity.lastTickPosX + (entity.posX - entity.lastTickPosX) * e.getPartialTicks() - (mc.getRenderManager()).renderPosX;
/*      */     
/*  961 */     double y = entity.lastTickPosY + (entity.posY - entity.lastTickPosY) * e.getPartialTicks() - (mc.getRenderManager()).renderPosY;
/*      */     
/*  963 */     double z = entity.lastTickPosZ + (entity.posZ - entity.lastTickPosZ) * e.getPartialTicks() - (mc.getRenderManager()).renderPosZ;
/*  964 */     float radius = 0.2F;
/*  965 */     int side = 6;
/*  966 */     GL11.glPushMatrix();
/*  967 */     GL11.glTranslated(x, y + 2.0D, z);
/*  968 */     GL11.glRotatef(-entity.width, 0.0F, 1.0F, 0.0F);
/*  969 */     glColor(color);
/*  970 */     enableSmoothLine(1.0F);
/*  971 */     Cylinder c = new Cylinder();
/*  972 */     GL11.glRotatef(-90.0F, 1.0F, 0.0F, 0.0F);
/*      */     
/*  974 */     c.setDrawStyle(100012);
/*  975 */     c.draw(0.0F, radius, 0.3F, side, 1);
/*      */     
/*  977 */     c.setDrawStyle(100012);
/*  978 */     GL11.glTranslated(0.0D, 0.0D, 0.3D);
/*  979 */     c.draw(radius, 0.0F, 0.3F, side, 1);
/*      */ 
/*      */ 
/*      */     
/*  983 */     GL11.glRotatef(90.0F, 0.0F, 0.0F, 1.0F);
/*      */     
/*  985 */     c.setDrawStyle(100011);
/*  986 */     GL11.glTranslated(0.0D, 0.0D, -0.3D);
/*  987 */     c.draw(0.0F, radius, 0.3F, side, 1);
/*      */     
/*  989 */     c.setDrawStyle(100011);
/*  990 */     GL11.glTranslated(0.0D, 0.0D, 0.3D);
/*  991 */     c.draw(radius, 0.0F, 0.3F, side, 1);
/*      */     
/*  993 */     disableSmoothLine();
/*  994 */     GL11.glPopMatrix();
/*      */   }
/*      */   
/*      */   public void glColor(int hex) {
/*  998 */     float alpha = (hex >> 24 & 0xFF) / 255.0F;
/*  999 */     float red = (hex >> 16 & 0xFF) / 255.0F;
/* 1000 */     float green = (hex >> 8 & 0xFF) / 255.0F;
/* 1001 */     float blue = (hex & 0xFF) / 255.0F;
/* 1002 */     GL11.glColor4f(red, green, blue, (alpha == 0.0F) ? 1.0F : alpha);
/*      */   }
/*      */   
/*      */   public void enableSmoothLine(float width) {
/* 1006 */     GL11.glDisable(3008);
/* 1007 */     GL11.glEnable(3042);
/* 1008 */     GL11.glBlendFunc(770, 771);
/* 1009 */     GL11.glDisable(3553);
/* 1010 */     GL11.glDisable(2929);
/* 1011 */     GL11.glDepthMask(false);
/* 1012 */     GL11.glEnable(2884);
/* 1013 */     GL11.glEnable(2848);
/* 1014 */     GL11.glHint(3154, 4354);
/* 1015 */     GL11.glHint(3155, 4354);
/* 1016 */     GL11.glLineWidth(width);
/*      */   }
/*      */   
/*      */   public void disableSmoothLine() {
/* 1020 */     GL11.glEnable(3553);
/* 1021 */     GL11.glEnable(2929);
/* 1022 */     GL11.glDisable(3042);
/* 1023 */     GL11.glEnable(3008);
/* 1024 */     GL11.glDepthMask(true);
/* 1025 */     GL11.glCullFace(1029);
/* 1026 */     GL11.glDisable(2848);
/* 1027 */     GL11.glHint(3154, 4352);
/* 1028 */     GL11.glHint(3155, 4352);
/*      */   }
/*      */   
/*      */   private Vector3d project2D(int scaleFactor, double x, double y, double z) {
/* 1032 */     GL11.glGetFloat(2982, this.modelview);
/* 1033 */     GL11.glGetFloat(2983, this.projection);
/* 1034 */     GL11.glGetInteger(2978, this.viewport);
/* 1035 */     if (GLU.gluProject((float)x, (float)y, (float)z, this.modelview, this.projection, this.viewport, this.vector))
/*      */     {
/* 1037 */       return new Vector3d((this.vector.get(0) / scaleFactor), ((
/* 1038 */           Display.getHeight() - this.vector.get(1)) / scaleFactor), this.vector.get(2));
/*      */     }
/* 1040 */     return null;
/*      */   }
/*      */   
/*      */   private void collectEntities() {
/* 1044 */     this.collectedEntities.clear();
/* 1045 */     List playerEntities = mc.theWorld.loadedEntityList;
/* 1046 */     for (Object playerEntity : playerEntities) {
/* 1047 */       Entity entity = (Entity)playerEntity;
/* 1048 */       if (!isValid(entity))
/*      */         continue; 
/* 1050 */       this.collectedEntities.add(entity);
/*      */     } 
/*      */   }
/*      */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\awareline\main\mod\implement\visual\etype\ESP.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */