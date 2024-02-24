/*      */ package awareline.main.utility.ghosthitutily;
/*      */ 
/*      */ import awareline.main.ui.font.fontmanager.color.ColorUtils;
/*      */ import awareline.main.ui.gui.clickgui.RenderUtil;
/*      */ import awareline.main.utility.render.color.ColourUtil;
/*      */ import awareline.main.utility.shader.ketaUtils.render.GLShader;
/*      */ import java.awt.Color;
/*      */ import java.nio.FloatBuffer;
/*      */ import java.nio.IntBuffer;
/*      */ import net.minecraft.client.Minecraft;
/*      */ import net.minecraft.client.gui.FontRenderer;
/*      */ import net.minecraft.client.gui.ScaledResolution;
/*      */ import net.minecraft.client.renderer.GLAllocation;
/*      */ import net.minecraft.client.renderer.GlStateManager;
/*      */ import net.minecraft.client.renderer.OpenGlHelper;
/*      */ import net.minecraft.client.renderer.RenderHelper;
/*      */ import net.minecraft.client.renderer.entity.RenderManager;
/*      */ import net.minecraft.enchantment.Enchantment;
/*      */ import net.minecraft.enchantment.EnchantmentHelper;
/*      */ import net.minecraft.entity.Entity;
/*      */ import net.minecraft.entity.EntityLivingBase;
/*      */ import net.minecraft.item.EnumRarity;
/*      */ import net.minecraft.item.ItemStack;
/*      */ import net.minecraft.util.AxisAlignedBB;
/*      */ import net.minecraft.util.ResourceLocation;
/*      */ import net.minecraft.util.Vec3;
/*      */ import net.optifine.util.MathUtils;
/*      */ import org.lwjgl.opengl.Display;
/*      */ import org.lwjgl.opengl.GL11;
/*      */ import org.lwjgl.opengl.GL14;
/*      */ import org.lwjgl.opengl.GL20;
/*      */ import org.lwjgl.util.glu.GLU;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public final class DrawUtil
/*      */ {
/*      */   public static final String VERTEX_SHADER = "#version 120 \n\nvoid main() {\n    gl_TexCoord[0] = gl_MultiTexCoord0;\n    gl_Position = gl_ModelViewProjectionMatrix * gl_Vertex;\n}";
/*   47 */   private static final FloatBuffer WND_POS_BUFFER = GLAllocation.createDirectFloatBuffer(4);
/*   48 */   private static final IntBuffer VIEWPORT_BUFFER = GLAllocation.createDirectIntBuffer(16);
/*   49 */   private static final FloatBuffer MODEL_MATRIX_BUFFER = GLAllocation.createDirectFloatBuffer(16);
/*   50 */   private static final FloatBuffer PROJECTION_MATRIX_BUFFER = GLAllocation.createDirectFloatBuffer(16);
/*   51 */   private static final IntBuffer SCISSOR_BUFFER = GLAllocation.createDirectIntBuffer(16);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static final String CIRCLE_FRAG_SHADER = "#version 120\n\nuniform float innerRadius;\nuniform vec4 colour;\n\nvoid main() {\n   vec2 pixel = gl_TexCoord[0].st;\n   vec2 centre = vec2(0.5, 0.5);\n   float d = length(pixel - centre);\n   float c = smoothstep(d+innerRadius, d+innerRadius+0.01, 0.5-innerRadius);\n   float a = smoothstep(0.0, 1.0, c) * colour.a;\n   gl_FragColor = vec4(colour.rgb, a);\n}\n";
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*   66 */   private static final GLShader CIRCLE_SHADER = new GLShader("#version 120 \n\nvoid main() {\n    gl_TexCoord[0] = gl_MultiTexCoord0;\n    gl_Position = gl_ModelViewProjectionMatrix * gl_Vertex;\n}", "#version 120\n\nuniform float innerRadius;\nuniform vec4 colour;\n\nvoid main() {\n   vec2 pixel = gl_TexCoord[0].st;\n   vec2 centre = vec2(0.5, 0.5);\n   float d = length(pixel - centre);\n   float c = smoothstep(d+innerRadius, d+innerRadius+0.01, 0.5-innerRadius);\n   float a = smoothstep(0.0, 1.0, c) * colour.a;\n   gl_FragColor = vec4(colour.rgb, a);\n}\n")
/*      */     {
/*      */       public void setupUniforms() {
/*   69 */         setupUniform("colour");
/*   70 */         setupUniform("innerRadius");
/*      */       }
/*      */     };
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static final String ROUNDED_QUAD_FRAG_SHADER = "#version 120\nuniform float width;\nuniform float height;\nuniform float radius;\nuniform vec4 colour;\n\nfloat SDRoundedRect(vec2 p, vec2 b, float r) {\n    vec2 q = abs(p) - b + r;\n    return min(max(q.x, q.y), 0.0) + length(max(q, 0.0)) - r;\n}\n\nvoid main() {\n    vec2 size = vec2(width, height);\n    vec2 pixel = gl_TexCoord[0].st * size;\n    vec2 centre = 0.5 * size;\n    float b = SDRoundedRect(pixel - centre, centre, radius);\n    float a = 1.0 - smoothstep(0, 1.0, b);\n    gl_FragColor = vec4(colour.rgb, colour.a * a);\n}";
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*   93 */   private static final GLShader ROUNDED_QUAD_SHADER = new GLShader("#version 120 \n\nvoid main() {\n    gl_TexCoord[0] = gl_MultiTexCoord0;\n    gl_Position = gl_ModelViewProjectionMatrix * gl_Vertex;\n}", "#version 120\nuniform float width;\nuniform float height;\nuniform float radius;\nuniform vec4 colour;\n\nfloat SDRoundedRect(vec2 p, vec2 b, float r) {\n    vec2 q = abs(p) - b + r;\n    return min(max(q.x, q.y), 0.0) + length(max(q, 0.0)) - r;\n}\n\nvoid main() {\n    vec2 size = vec2(width, height);\n    vec2 pixel = gl_TexCoord[0].st * size;\n    vec2 centre = 0.5 * size;\n    float b = SDRoundedRect(pixel - centre, centre, radius);\n    float a = 1.0 - smoothstep(0, 1.0, b);\n    gl_FragColor = vec4(colour.rgb, colour.a * a);\n}")
/*      */     {
/*      */       public void setupUniforms() {
/*   96 */         setupUniform("width");
/*   97 */         setupUniform("height");
/*   98 */         setupUniform("colour");
/*   99 */         setupUniform("radius");
/*      */       }
/*      */     };
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static final String RAINBOW_FRAG_SHADER = "#version 120\nuniform float width;\nuniform float height;\nuniform float radius;\nuniform float u_time;\n\nfloat SDRoundedRect(vec2 p, vec2 b, float r) {\n    vec2 q = abs(p) - b + r;\n    return min(max(q.x, q.y), 0.0) + length(max(q, 0.0)) - r;\n}\n\nvoid main() {\n    vec2 size = vec2(width, height);\n    vec2 pixel = gl_TexCoord[0].st * size;\n    vec2 centre = 0.5 * size;\n    float b = SDRoundedRect(pixel - centre, centre, radius);\n    float a = 1.0 - smoothstep(0, 1.0, b);\n    vec3 colour = 0.5 + 0.5*cos(u_time+gl_TexCoord[0].st.x+vec3(0,2,4));\n    gl_FragColor = vec4(colour, a);\n}";
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  123 */   private static final GLShader GL_COLOUR_SHADER = new GLShader("#version 120 \n\nvoid main() {\n    gl_TexCoord[0] = gl_MultiTexCoord0;\n    gl_Position = gl_ModelViewProjectionMatrix * gl_Vertex;\n}", "#version 120\nuniform float width;\nuniform float height;\nuniform float radius;\nuniform float u_time;\n\nfloat SDRoundedRect(vec2 p, vec2 b, float r) {\n    vec2 q = abs(p) - b + r;\n    return min(max(q.x, q.y), 0.0) + length(max(q, 0.0)) - r;\n}\n\nvoid main() {\n    vec2 size = vec2(width, height);\n    vec2 pixel = gl_TexCoord[0].st * size;\n    vec2 centre = 0.5 * size;\n    float b = SDRoundedRect(pixel - centre, centre, radius);\n    float a = 1.0 - smoothstep(0, 1.0, b);\n    vec3 colour = 0.5 + 0.5*cos(u_time+gl_TexCoord[0].st.x+vec3(0,2,4));\n    gl_FragColor = vec4(colour, a);\n}")
/*      */     {
/*  125 */       private final long initTime = System.currentTimeMillis();
/*      */ 
/*      */       
/*      */       public void setupUniforms() {
/*  129 */         setupUniform("width");
/*  130 */         setupUniform("height");
/*  131 */         setupUniform("radius");
/*  132 */         setupUniform("u_time");
/*      */       }
/*      */ 
/*      */       
/*      */       public void updateUniforms() {
/*  137 */         GL20.glUniform1f(GL20.glGetUniformLocation(getProgram(), "u_time"), (float)(System.currentTimeMillis() - this.initTime) / 1000.0F);
/*      */       }
/*      */     };
/*      */   
/*      */   public static float ticks;
/*      */   
/*      */   public static float ticksSinceClickgui;
/*      */   
/*      */   public static void renderEnchantText(ItemStack stack, int x, float y) {
/*  146 */     RenderHelper.disableStandardItemLighting();
/*  147 */     float enchantmentY = y + 24.0F;
/*  148 */     if (stack.getItem() instanceof net.minecraft.item.ItemArmor) {
/*  149 */       int protectionLevel = EnchantmentHelper.getEnchantmentLevel(Enchantment.protection.effectId, stack);
/*  150 */       int unbreakingLevel = EnchantmentHelper.getEnchantmentLevel(Enchantment.unbreaking.effectId, stack);
/*  151 */       int thornLevel = EnchantmentHelper.getEnchantmentLevel(Enchantment.thorns.effectId, stack);
/*  152 */       if (protectionLevel > 0) {
/*  153 */         drawEnchantTag("P" + ColorUtils.getColor(protectionLevel) + protectionLevel, x << 1, enchantmentY);
/*  154 */         enchantmentY += 8.0F;
/*      */       } 
/*  156 */       if (unbreakingLevel > 0) {
/*  157 */         drawEnchantTag("U" + ColorUtils.getColor(unbreakingLevel) + unbreakingLevel, x << 1, enchantmentY);
/*  158 */         enchantmentY += 8.0F;
/*      */       } 
/*  160 */       if (thornLevel > 0) {
/*  161 */         drawEnchantTag("T" + ColorUtils.getColor(thornLevel) + thornLevel, x << 1, enchantmentY);
/*  162 */         enchantmentY += 8.0F;
/*      */       } 
/*      */     } 
/*  165 */     if (stack.getItem() instanceof net.minecraft.item.ItemBow) {
/*  166 */       int powerLevel = EnchantmentHelper.getEnchantmentLevel(Enchantment.power.effectId, stack);
/*  167 */       int punchLevel = EnchantmentHelper.getEnchantmentLevel(Enchantment.punch.effectId, stack);
/*  168 */       int flameLevel = EnchantmentHelper.getEnchantmentLevel(Enchantment.flame.effectId, stack);
/*  169 */       int unbreakingLevel = EnchantmentHelper.getEnchantmentLevel(Enchantment.unbreaking.effectId, stack);
/*  170 */       if (powerLevel > 0) {
/*  171 */         drawEnchantTag("Pow" + ColorUtils.getColor(powerLevel) + powerLevel, x << 1, enchantmentY);
/*  172 */         enchantmentY += 8.0F;
/*      */       } 
/*  174 */       if (punchLevel > 0) {
/*  175 */         drawEnchantTag("Pun" + ColorUtils.getColor(punchLevel) + punchLevel, x << 1, enchantmentY);
/*  176 */         enchantmentY += 8.0F;
/*      */       } 
/*  178 */       if (flameLevel > 0) {
/*  179 */         drawEnchantTag("F" + ColorUtils.getColor(flameLevel) + flameLevel, x << 1, enchantmentY);
/*  180 */         enchantmentY += 8.0F;
/*      */       } 
/*  182 */       if (unbreakingLevel > 0) {
/*  183 */         drawEnchantTag("U" + ColorUtils.getColor(unbreakingLevel) + unbreakingLevel, x << 1, enchantmentY);
/*  184 */         enchantmentY += 8.0F;
/*      */       } 
/*      */     } 
/*  187 */     if (stack.getItem() instanceof net.minecraft.item.ItemSword) {
/*  188 */       int sharpnessLevel = EnchantmentHelper.getEnchantmentLevel(Enchantment.sharpness.effectId, stack);
/*  189 */       int knockbackLevel = EnchantmentHelper.getEnchantmentLevel(Enchantment.knockback.effectId, stack);
/*  190 */       int fireAspectLevel = EnchantmentHelper.getEnchantmentLevel(Enchantment.fireAspect.effectId, stack);
/*  191 */       int unbreakingLevel = EnchantmentHelper.getEnchantmentLevel(Enchantment.unbreaking.effectId, stack);
/*  192 */       if (sharpnessLevel > 0) {
/*  193 */         drawEnchantTag("S" + ColorUtils.getColor(sharpnessLevel) + sharpnessLevel, x << 1, enchantmentY);
/*  194 */         enchantmentY += 8.0F;
/*      */       } 
/*  196 */       if (knockbackLevel > 0) {
/*  197 */         drawEnchantTag("K" + ColorUtils.getColor(knockbackLevel) + knockbackLevel, x << 1, enchantmentY);
/*  198 */         enchantmentY += 8.0F;
/*      */       } 
/*  200 */       if (fireAspectLevel > 0) {
/*  201 */         drawEnchantTag("F" + ColorUtils.getColor(fireAspectLevel) + fireAspectLevel, x << 1, enchantmentY);
/*  202 */         enchantmentY += 8.0F;
/*      */       } 
/*  204 */       if (unbreakingLevel > 0) {
/*  205 */         drawEnchantTag("U" + ColorUtils.getColor(unbreakingLevel) + unbreakingLevel, x << 1, enchantmentY);
/*  206 */         enchantmentY += 8.0F;
/*      */       } 
/*      */     } 
/*  209 */     if (stack.getRarity() == EnumRarity.EPIC) {
/*  210 */       GlStateManager.pushMatrix();
/*  211 */       GlStateManager.disableDepth();
/*  212 */       GL11.glScalef(0.5F, 0.5F, 0.5F);
/*  213 */       FontRenderer.drawOutlinedStringCock((Minecraft.getMinecraft()).fontRendererObj, "God", (x << 1), enchantmentY, (new Color(255, 255, 0)).getRGB(), (new Color(100, 100, 0, 200)).getRGB());
/*  214 */       GL11.glScalef(1.0F, 1.0F, 1.0F);
/*  215 */       GlStateManager.enableDepth();
/*  216 */       GlStateManager.popMatrix();
/*      */     } 
/*      */   }
/*      */   
/*      */   private static void drawEnchantTag(String text, int x, float y) {
/*  221 */     GlStateManager.pushMatrix();
/*  222 */     GlStateManager.disableDepth();
/*  223 */     GL11.glScalef(0.5F, 0.5F, 0.5F);
/*  224 */     FontRenderer.drawOutlinedStringCock((Minecraft.getMinecraft()).fontRendererObj, text, x, y, -1, (new Color(0, 0, 0, 220)).darker().getRGB());
/*  225 */     GL11.glScalef(1.0F, 1.0F, 1.0F);
/*  226 */     GlStateManager.enableDepth();
/*  227 */     GlStateManager.popMatrix();
/*      */   }
/*      */   
/*      */   public static void drawModel(float yaw, float pitch, EntityLivingBase entityLivingBase) {
/*  231 */     GlStateManager.resetColor();
/*  232 */     GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
/*  233 */     GlStateManager.enableColorMaterial();
/*  234 */     GlStateManager.pushMatrix();
/*  235 */     GlStateManager.translate(0.0F, 0.0F, 50.0F);
/*  236 */     GlStateManager.scale(-50.0F, 50.0F, 50.0F);
/*  237 */     GlStateManager.rotate(180.0F, 0.0F, 0.0F, 1.0F);
/*  238 */     float renderYawOffset = entityLivingBase.renderYawOffset;
/*  239 */     float rotationYaw = entityLivingBase.rotationYaw;
/*  240 */     float rotationPitch = entityLivingBase.rotationPitch;
/*  241 */     float prevRotationYawHead = entityLivingBase.prevRotationYawHead;
/*  242 */     float rotationYawHead = entityLivingBase.rotationYawHead;
/*  243 */     GlStateManager.rotate(135.0F, 0.0F, 1.0F, 0.0F);
/*  244 */     RenderHelper.enableStandardItemLighting();
/*  245 */     GlStateManager.rotate(-135.0F, 0.0F, 1.0F, 0.0F);
/*  246 */     GlStateManager.rotate((float)(-Math.atan((pitch / 40.0F)) * 20.0D), 1.0F, 0.0F, 0.0F);
/*  247 */     entityLivingBase.renderYawOffset = yaw - 0.4F;
/*  248 */     entityLivingBase.rotationYaw = yaw - 0.2F;
/*  249 */     entityLivingBase.rotationPitch = pitch;
/*  250 */     entityLivingBase.rotationYawHead = entityLivingBase.rotationYaw;
/*  251 */     entityLivingBase.prevRotationYawHead = entityLivingBase.rotationYaw;
/*  252 */     GlStateManager.translate(0.0F, 0.0F, 0.0F);
/*  253 */     RenderManager renderManager = Minecraft.getMinecraft().getRenderManager();
/*  254 */     renderManager.setPlayerViewY(180.0F);
/*  255 */     renderManager.setRenderShadow(false);
/*  256 */     renderManager.renderEntityWithPosYaw((Entity)entityLivingBase, 0.0D, 0.0D, 0.0D, 0.0F, 1.0F);
/*  257 */     renderManager.setRenderShadow(true);
/*  258 */     entityLivingBase.renderYawOffset = renderYawOffset;
/*  259 */     entityLivingBase.rotationYaw = rotationYaw;
/*  260 */     entityLivingBase.rotationPitch = rotationPitch;
/*  261 */     entityLivingBase.prevRotationYawHead = prevRotationYawHead;
/*  262 */     entityLivingBase.rotationYawHead = rotationYawHead;
/*  263 */     GlStateManager.popMatrix();
/*  264 */     RenderHelper.disableStandardItemLighting();
/*  265 */     GlStateManager.disableRescaleNormal();
/*  266 */     GlStateManager.setActiveTexture(OpenGlHelper.lightmapTexUnit);
/*  267 */     GlStateManager.disableTexture2D();
/*  268 */     GlStateManager.setActiveTexture(OpenGlHelper.defaultTexUnit);
/*  269 */     GlStateManager.resetColor();
/*      */   }
/*      */   
/*      */   public static void skeetRect(double x, double y, double x1, double y1, double size) {
/*  273 */     RenderUtil.rectangleBordered(x, y - 4.0D, x1 + size, y1 + size, 0.5D, (new Color(60, 60, 60)).getRGB(), (new Color(10, 10, 10)).getRGB());
/*  274 */     RenderUtil.rectangleBordered(x + 1.0D, y - 3.0D, x1 + size - 1.0D, y1 + size - 1.0D, 1.0D, (new Color(40, 40, 40)).getRGB(), (new Color(40, 40, 40)).getRGB());
/*  275 */     RenderUtil.rectangleBordered(x + 2.5D, y - 1.5D, x1 + size - 2.5D, y1 + size - 2.5D, 0.5D, (new Color(40, 40, 40)).getRGB(), (new Color(60, 60, 60)).getRGB());
/*  276 */     RenderUtil.rectangleBordered(x + 2.5D, y - 1.5D, x1 + size - 2.5D, y1 + size - 2.5D, 0.5D, (new Color(22, 22, 22)).getRGB(), (new Color(255, 255, 255, 0)).getRGB());
/*      */   }
/*      */   
/*      */   public static void skeetRectSmall(double x, double y, double x1, double y1, double size) {
/*  280 */     RenderUtil.rectangleBordered(x + 4.35D, y + 0.5D, x1 + size - 84.5D, y1 + size - 4.35D, 0.5D, (new Color(48, 48, 48)).getRGB(), (new Color(10, 10, 10)).getRGB());
/*  281 */     RenderUtil.rectangleBordered(x + 5.0D, y + 1.0D, x1 + size - 85.0D, y1 + size - 5.0D, 0.5D, (new Color(17, 17, 17)).getRGB(), (new Color(255, 255, 255, 0)).getRGB());
/*      */   }
/*      */   
/*      */   public static void scissor(double x, double y, double width, double height) {
/*  285 */     ScaledResolution sr = new ScaledResolution(Minecraft.getMinecraft());
/*  286 */     double scale = sr.getScaleFactor();
/*      */     
/*  288 */     y = sr.getScaledHeight() - y;
/*      */     
/*  290 */     x *= scale;
/*  291 */     y *= scale;
/*  292 */     width *= scale;
/*  293 */     height *= scale;
/*      */     
/*  295 */     GL11.glScissor((int)x, (int)(y - height), (int)width, (int)height);
/*      */   }
/*      */   
/*      */   public static void circle(double x, double y, double radius, Color color) {
/*  299 */     polygon(x, y, radius, 360, color);
/*      */   }
/*      */   
/*      */   public static void polygon(double x, double y, double sideLength, double amountOfSides, boolean filled, Color color) {
/*  303 */     sideLength /= 2.0D;
/*  304 */     start();
/*  305 */     if (color != null)
/*  306 */       color(color); 
/*  307 */     if (!filled) GL11.glLineWidth(2.0F); 
/*  308 */     GL11.glEnable(2848);
/*  309 */     begin(filled ? 6 : 3);
/*      */     double i;
/*  311 */     for (i = 0.0D; i <= amountOfSides / 4.0D; i++) {
/*  312 */       double angle = i * 4.0D * 6.283185307179586D / 360.0D;
/*  313 */       vertex(x + sideLength * Math.cos(angle) + sideLength, y + sideLength * Math.sin(angle) + sideLength);
/*      */     } 
/*      */     
/*  316 */     end();
/*  317 */     GL11.glDisable(2848);
/*  318 */     stop();
/*      */   }
/*      */   
/*      */   public static void polygon(double x, double y, double sideLength, int amountOfSides, boolean filled) {
/*  322 */     polygon(x, y, sideLength, amountOfSides, filled, null);
/*      */   }
/*      */   
/*      */   public static void polygon(double x, double y, double sideLength, int amountOfSides, Color color) {
/*  326 */     polygon(x, y, sideLength, amountOfSides, true, color);
/*      */   }
/*      */   
/*      */   public static void polygon(double x, double y, double sideLength, int amountOfSides) {
/*  330 */     polygon(x, y, sideLength, amountOfSides, true, null);
/*      */   }
/*      */   
/*      */   public static void circle(double x, double y, double radius) {
/*  334 */     polygon(x, y, radius, 360);
/*      */   }
/*      */   
/*      */   public static void roundedRect(double x, double y, double width, double height, double edgeRadius, Color color) {
/*  338 */     double halfRadius = edgeRadius / 2.0D;
/*  339 */     width -= halfRadius;
/*  340 */     height -= halfRadius;
/*      */     
/*  342 */     float sideLength = (float)edgeRadius;
/*  343 */     sideLength /= 2.0F;
/*  344 */     start();
/*  345 */     if (color != null)
/*  346 */       color(color); 
/*  347 */     begin(6);
/*      */     
/*      */     double i;
/*  350 */     for (i = 180.0D; i <= 270.0D; i++) {
/*  351 */       double angle = i * 6.283185307179586D / 360.0D;
/*  352 */       vertex(x + sideLength * Math.cos(angle) + sideLength, y + sideLength * Math.sin(angle) + sideLength);
/*      */     } 
/*  354 */     vertex(x + sideLength, y + sideLength);
/*      */ 
/*      */     
/*  357 */     end();
/*  358 */     stop();
/*      */     
/*  360 */     sideLength = (float)edgeRadius;
/*  361 */     sideLength /= 2.0F;
/*  362 */     start();
/*  363 */     if (color != null)
/*  364 */       color(color); 
/*  365 */     GL11.glEnable(2848);
/*  366 */     begin(6);
/*      */ 
/*      */     
/*  369 */     for (i = 0.0D; i <= 90.0D; i++) {
/*  370 */       double angle = i * 6.283185307179586D / 360.0D;
/*  371 */       vertex(x + width + sideLength * Math.cos(angle), y + height + sideLength * Math.sin(angle));
/*      */     } 
/*  373 */     vertex(x + width, y + height);
/*      */ 
/*      */     
/*  376 */     end();
/*  377 */     GL11.glDisable(2848);
/*  378 */     stop();
/*      */     
/*  380 */     sideLength = (float)edgeRadius;
/*  381 */     sideLength /= 2.0F;
/*  382 */     start();
/*  383 */     if (color != null)
/*  384 */       color(color); 
/*  385 */     GL11.glEnable(2848);
/*  386 */     begin(6);
/*      */ 
/*      */     
/*  389 */     for (i = 270.0D; i <= 360.0D; i++) {
/*  390 */       double angle = i * 6.283185307179586D / 360.0D;
/*  391 */       vertex(x + width + sideLength * Math.cos(angle), y + sideLength * Math.sin(angle) + sideLength);
/*      */     } 
/*  393 */     vertex(x + width, y + sideLength);
/*      */ 
/*      */     
/*  396 */     end();
/*  397 */     GL11.glDisable(2848);
/*  398 */     stop();
/*      */     
/*  400 */     sideLength = (float)edgeRadius;
/*  401 */     sideLength /= 2.0F;
/*  402 */     start();
/*  403 */     if (color != null)
/*  404 */       color(color); 
/*  405 */     GL11.glEnable(2848);
/*  406 */     begin(6);
/*      */ 
/*      */     
/*  409 */     for (i = 90.0D; i <= 180.0D; i++) {
/*  410 */       double angle = i * 6.283185307179586D / 360.0D;
/*  411 */       vertex(x + sideLength * Math.cos(angle) + sideLength, y + height + sideLength * Math.sin(angle));
/*      */     } 
/*  413 */     vertex(x + sideLength, y + height);
/*      */ 
/*      */     
/*  416 */     end();
/*  417 */     GL11.glDisable(2848);
/*  418 */     stop();
/*      */ 
/*      */     
/*  421 */     rect(x + halfRadius, y + halfRadius, width - halfRadius, height - halfRadius, color);
/*      */ 
/*      */     
/*  424 */     rect(x, y + halfRadius, edgeRadius / 2.0D, height - halfRadius, color);
/*  425 */     rect(x + width, y + halfRadius, edgeRadius / 2.0D, height - halfRadius, color);
/*      */ 
/*      */     
/*  428 */     rect(x + halfRadius, y, width - halfRadius, halfRadius, color);
/*  429 */     rect(x + halfRadius, y + height, width - halfRadius, halfRadius, color);
/*      */   }
/*      */ 
/*      */   
/*      */   public static void enable(int glTarget) {
/*  434 */     GL11.glEnable(glTarget);
/*      */   }
/*      */   
/*      */   public static void disable(int glTarget) {
/*  438 */     GL11.glDisable(glTarget);
/*      */   }
/*      */   
/*      */   public static void start() {
/*  442 */     enable(3042);
/*  443 */     GL11.glBlendFunc(770, 771);
/*  444 */     disable(3553);
/*  445 */     disable(2884);
/*  446 */     GlStateManager.disableAlpha();
/*  447 */     GlStateManager.disableDepth();
/*      */   }
/*      */   
/*      */   public static void stop() {
/*  451 */     GlStateManager.enableAlpha();
/*  452 */     GlStateManager.enableDepth();
/*  453 */     enable(2884);
/*  454 */     enable(3553);
/*  455 */     disable(3042);
/*  456 */     color(Color.white);
/*      */   }
/*      */   
/*      */   public static void color(double red, double green, double blue, double alpha) {
/*  460 */     GL11.glColor4d(red, green, blue, alpha);
/*      */   }
/*      */   
/*      */   public static void color(Color color) {
/*  464 */     if (color == null)
/*  465 */       color = Color.white; 
/*  466 */     color((color.getRed() / 255.0F), (color.getGreen() / 255.0F), (color.getBlue() / 255.0F), (color.getAlpha() / 255.0F));
/*      */   }
/*      */   
/*      */   public static void begin(int glMode) {
/*  470 */     GL11.glBegin(glMode);
/*      */   }
/*      */   
/*      */   public static void end() {
/*  474 */     GL11.glEnd();
/*      */   }
/*      */   
/*      */   public static void vertex(double x, double y) {
/*  478 */     GL11.glVertex2d(x, y);
/*      */   }
/*      */   
/*      */   public static void rect(double x, double y, double width, double height, boolean filled, Color color) {
/*  482 */     start();
/*  483 */     if (color != null)
/*  484 */       color(color); 
/*  485 */     begin(filled ? 6 : 1);
/*      */ 
/*      */     
/*  488 */     vertex(x, y);
/*  489 */     vertex(x + width, y);
/*  490 */     vertex(x + width, y + height);
/*  491 */     vertex(x, y + height);
/*  492 */     if (!filled) {
/*  493 */       vertex(x, y);
/*  494 */       vertex(x, y + height);
/*  495 */       vertex(x + width, y);
/*  496 */       vertex(x + width, y + height);
/*      */     } 
/*      */     
/*  499 */     end();
/*  500 */     stop();
/*      */   }
/*      */   
/*      */   public static void rect(double x, double y, double width, double height, Color color) {
/*  504 */     rect(x, y, width, height, true, color);
/*      */   }
/*      */   
/*      */   public static void drawBorderedRect(float x, float y, float width, float height, float borderWidth, Color rectColor, Color borderColor) {
/*  508 */     drawBorderedRect(x, y, width, height, borderWidth, rectColor.getRGB(), borderColor.getRGB());
/*      */   }
/*      */   
/*      */   public static void drawBorderedRect(float x, float y, float x2, float y2, float l1, int col1, int col2) {
/*  512 */     drawRect(x, y, x2, y2, col2);
/*      */     
/*  514 */     float f = (col1 >> 24 & 0xFF) / 255.0F;
/*  515 */     float f1 = (col1 >> 16 & 0xFF) / 255.0F;
/*  516 */     float f2 = (col1 >> 8 & 0xFF) / 255.0F;
/*  517 */     float f3 = (col1 & 0xFF) / 255.0F;
/*      */     
/*  519 */     GL11.glEnable(3042);
/*  520 */     GL11.glDisable(3553);
/*  521 */     GL11.glBlendFunc(770, 771);
/*  522 */     GL11.glEnable(2848);
/*      */     
/*  524 */     GL11.glPushMatrix();
/*  525 */     GL11.glColor4f(f1, f2, f3, f);
/*  526 */     GL11.glLineWidth(l1);
/*  527 */     GL11.glBegin(1);
/*  528 */     GL11.glVertex2d(x, y);
/*  529 */     GL11.glVertex2d(x, y2);
/*  530 */     GL11.glVertex2d(x2, y2);
/*  531 */     GL11.glVertex2d(x2, y);
/*  532 */     GL11.glVertex2d(x, y);
/*  533 */     GL11.glVertex2d(x2, y);
/*  534 */     GL11.glVertex2d(x, y2);
/*  535 */     GL11.glVertex2d(x2, y2);
/*  536 */     GL11.glEnd();
/*  537 */     GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
/*  538 */     GL11.glPopMatrix();
/*      */     
/*  540 */     GlStateManager.enableTexture2D();
/*  541 */     GlStateManager.disableBlend();
/*  542 */     GL11.glColor4f(1.0F, 1.0F, 1.0F, 255.0F);
/*  543 */     GL11.glEnable(3553);
/*  544 */     GL11.glDisable(3042);
/*  545 */     GL11.glDisable(2848);
/*      */   }
/*      */   
/*      */   public static void drawBorderedRect(double x, double y, double x2, double y2, float l1, int col1, int col2) {
/*  549 */     drawRect((float)x, (float)y, (float)x2, (float)y2, col2);
/*      */     
/*  551 */     float f = (col1 >> 24 & 0xFF) / 255.0F;
/*  552 */     float f1 = (col1 >> 16 & 0xFF) / 255.0F;
/*  553 */     float f2 = (col1 >> 8 & 0xFF) / 255.0F;
/*  554 */     float f3 = (col1 & 0xFF) / 255.0F;
/*      */     
/*  556 */     GL11.glEnable(3042);
/*  557 */     GL11.glDisable(3553);
/*  558 */     GL11.glBlendFunc(770, 771);
/*  559 */     GL11.glEnable(2848);
/*      */     
/*  561 */     GL11.glPushMatrix();
/*  562 */     GL11.glColor4f(f1, f2, f3, f);
/*  563 */     GL11.glLineWidth(l1);
/*  564 */     GL11.glBegin(1);
/*  565 */     GL11.glVertex2d(x, y);
/*  566 */     GL11.glVertex2d(x, y2);
/*  567 */     GL11.glVertex2d(x2, y2);
/*  568 */     GL11.glVertex2d(x2, y);
/*  569 */     GL11.glVertex2d(x, y);
/*  570 */     GL11.glVertex2d(x2, y);
/*  571 */     GL11.glVertex2d(x, y2);
/*  572 */     GL11.glVertex2d(x2, y2);
/*  573 */     GL11.glEnd();
/*  574 */     GlStateManager.enableTexture2D();
/*  575 */     GlStateManager.disableBlend();
/*  576 */     GL11.glPopMatrix();
/*  577 */     GL11.glColor4f(255.0F, 1.0F, 1.0F, 255.0F);
/*  578 */     GL11.glEnable(3553);
/*  579 */     GL11.glDisable(3042);
/*  580 */     GL11.glDisable(2848);
/*      */   }
/*      */   
/*      */   public static void drawRect(float left, float top, float right, float bottom, int col1) {
/*  584 */     float f = (col1 >> 24 & 0xFF) / 255.0F;
/*  585 */     float f1 = (col1 >> 16 & 0xFF) / 255.0F;
/*  586 */     float f2 = (col1 >> 8 & 0xFF) / 255.0F;
/*  587 */     float f3 = (col1 & 0xFF) / 255.0F;
/*      */     
/*  589 */     GL11.glEnable(3042);
/*  590 */     GL11.glDisable(3553);
/*  591 */     GL11.glBlendFunc(770, 771);
/*  592 */     GL11.glEnable(2848);
/*      */     
/*  594 */     GL11.glPushMatrix();
/*  595 */     GL11.glColor4f(f1, f2, f3, f);
/*  596 */     GL11.glBegin(7);
/*  597 */     GL11.glVertex2d(right, top);
/*  598 */     GL11.glVertex2d(left, top);
/*  599 */     GL11.glVertex2d(left, bottom);
/*  600 */     GL11.glVertex2d(right, bottom);
/*  601 */     GL11.glEnd();
/*  602 */     GL11.glPopMatrix();
/*      */     
/*  604 */     GL11.glEnable(3553);
/*  605 */     GL11.glDisable(3042);
/*  606 */     GL11.glDisable(2848);
/*  607 */     GlStateManager.enableTexture2D();
/*  608 */     GlStateManager.disableBlend();
/*  609 */     GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
/*      */   }
/*      */   
/*      */   public static double animateProgress(double current, double target, double speed) {
/*  613 */     if (current < target) {
/*  614 */       Minecraft.getMinecraft(); double inc = 1.0D / Minecraft.getDebugFPS() * speed;
/*  615 */       if (target - current < inc) {
/*  616 */         return target;
/*      */       }
/*  618 */       return current + inc;
/*      */     } 
/*  620 */     if (current > target) {
/*  621 */       Minecraft.getMinecraft(); double inc = 1.0D / Minecraft.getDebugFPS() * speed;
/*  622 */       if (current - target < inc) {
/*  623 */         return target;
/*      */       }
/*  625 */       return current - inc;
/*      */     } 
/*      */ 
/*      */     
/*  629 */     return current;
/*      */   }
/*      */   
/*      */   public static double bezierBlendAnimation(double t) {
/*  633 */     return t * t * (3.0D - 2.0D * t);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void glDrawTriangle(double x, double y, double x1, double y1, double x2, double y2, int colour) {
/*  641 */     GL11.glDisable(3553);
/*      */     
/*  643 */     boolean restore = glEnableBlend();
/*      */     
/*  645 */     GL11.glEnable(2881);
/*  646 */     GL11.glHint(3155, 4354);
/*      */     
/*  648 */     glColour(colour);
/*      */ 
/*      */     
/*  651 */     GL11.glBegin(4);
/*      */     
/*  653 */     GL11.glVertex2d(x, y);
/*  654 */     GL11.glVertex2d(x1, y1);
/*  655 */     GL11.glVertex2d(x2, y2);
/*      */     
/*  657 */     GL11.glEnd();
/*      */ 
/*      */     
/*  660 */     GL11.glEnable(3553);
/*      */     
/*  662 */     glRestoreBlend(restore);
/*      */     
/*  664 */     GL11.glDisable(2881);
/*  665 */     GL11.glHint(3155, 4352);
/*      */   }
/*      */ 
/*      */   
/*      */   public static void glDrawFramebuffer(int framebufferTexture, int width, int height) {
/*  670 */     GL11.glBindTexture(3553, framebufferTexture);
/*      */     
/*  672 */     GL11.glDisable(3008);
/*      */     
/*  674 */     boolean restore = glEnableBlend();
/*      */     
/*  676 */     GL11.glBegin(7);
/*      */     
/*  678 */     GL11.glTexCoord2f(0.0F, 1.0F);
/*  679 */     GL11.glVertex2f(0.0F, 0.0F);
/*      */     
/*  681 */     GL11.glTexCoord2f(0.0F, 0.0F);
/*  682 */     GL11.glVertex2f(0.0F, height);
/*      */     
/*  684 */     GL11.glTexCoord2f(1.0F, 0.0F);
/*  685 */     GL11.glVertex2f(width, height);
/*      */     
/*  687 */     GL11.glTexCoord2f(1.0F, 1.0F);
/*  688 */     GL11.glVertex2f(width, 0.0F);
/*      */     
/*  690 */     GL11.glEnd();
/*      */     
/*  692 */     glRestoreBlend(restore);
/*      */     
/*  694 */     GL11.glEnable(3008);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void glDrawPlusSign(double x, double y, double size, double rotation, int colour) {
/*  703 */     GL11.glDisable(3553);
/*      */     
/*  705 */     boolean restore = glEnableBlend();
/*      */     
/*  707 */     GL11.glEnable(2848);
/*  708 */     GL11.glHint(3154, 4354);
/*      */     
/*  710 */     GL11.glLineWidth(1.0F);
/*      */     
/*  712 */     GL11.glPushMatrix();
/*      */     
/*  714 */     GL11.glTranslated(x, y, 0.0D);
/*      */     
/*  716 */     GL11.glRotated(rotation, 0.0D, 1.0D, 1.0D);
/*      */     
/*  718 */     GL11.glDisable(2929);
/*  719 */     GL11.glDepthMask(false);
/*      */     
/*  721 */     glColour(colour);
/*      */ 
/*      */     
/*  724 */     GL11.glBegin(1);
/*      */ 
/*      */     
/*  727 */     GL11.glVertex2d(-(size / 2.0D), 0.0D);
/*  728 */     GL11.glVertex2d(size / 2.0D, 0.0D);
/*      */     
/*  730 */     GL11.glVertex2d(0.0D, -(size / 2.0D));
/*  731 */     GL11.glVertex2d(0.0D, size / 2.0D);
/*      */     
/*  733 */     GL11.glEnd();
/*      */ 
/*      */     
/*  736 */     GL11.glEnable(2929);
/*  737 */     GL11.glDepthMask(true);
/*      */     
/*  739 */     GL11.glPopMatrix();
/*      */     
/*  741 */     GL11.glEnable(3553);
/*      */     
/*  743 */     glRestoreBlend(restore);
/*      */     
/*  745 */     GL11.glDisable(2848);
/*  746 */     GL11.glHint(3154, 4352);
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
/*      */   public static void glDrawFilledEllipse(double x, double y, double radius, int startIndex, int endIndex, int polygons, boolean smooth, int colour) {
/*  758 */     boolean restore = glEnableBlend();
/*      */     
/*  760 */     if (smooth) {
/*      */       
/*  762 */       GL11.glEnable(2881);
/*  763 */       GL11.glHint(3155, 4354);
/*      */     } 
/*      */     
/*  766 */     GL11.glDisable(3553);
/*      */     
/*  768 */     glColour(colour);
/*      */     
/*  770 */     GL11.glDisable(2884);
/*      */ 
/*      */     
/*  773 */     GL11.glBegin(9);
/*      */ 
/*      */     
/*  776 */     GL11.glVertex2d(x, y);
/*      */     double i;
/*  778 */     for (i = startIndex; i <= endIndex; i++) {
/*  779 */       double theta = 6.283185307179586D * i / polygons;
/*      */       
/*  781 */       GL11.glVertex2d(x + radius * Math.cos(theta), y + radius * Math.sin(theta));
/*      */     } 
/*      */ 
/*      */     
/*  785 */     GL11.glEnd();
/*      */ 
/*      */     
/*  788 */     glRestoreBlend(restore);
/*      */     
/*  790 */     if (smooth) {
/*      */       
/*  792 */       GL11.glDisable(2881);
/*  793 */       GL11.glHint(3155, 4352);
/*      */     } 
/*      */     
/*  796 */     GL11.glEnable(2884);
/*      */     
/*  798 */     GL11.glEnable(3553);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void glDrawFilledEllipse(double x, double y, float radius, int colour) {
/*  806 */     boolean restore = glEnableBlend();
/*      */     
/*  808 */     GL11.glEnable(2832);
/*  809 */     GL11.glHint(3153, 4354);
/*      */     
/*  811 */     GL11.glDisable(3553);
/*      */     
/*  813 */     glColour(colour);
/*      */     
/*  815 */     GL11.glPointSize(radius);
/*      */     
/*  817 */     GL11.glBegin(0);
/*      */     
/*  819 */     GL11.glVertex2d(x, y);
/*      */     
/*  821 */     GL11.glEnd();
/*      */ 
/*      */     
/*  824 */     glRestoreBlend(restore);
/*      */     
/*  826 */     GL11.glDisable(2832);
/*  827 */     GL11.glHint(3153, 4352);
/*      */     
/*  829 */     GL11.glEnable(3553);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public static void glScissorBox(double x, double y, double width, double height, ScaledResolution scaledResolution) {
/*  835 */     if (!GL11.glIsEnabled(3089)) {
/*  836 */       GL11.glEnable(3089);
/*      */     }
/*  838 */     int scaling = scaledResolution.getScaleFactor();
/*      */     
/*  840 */     GL11.glScissor((int)(x * scaling), 
/*  841 */         (int)((scaledResolution.getScaledHeight() - y + height) * scaling), (int)(width * scaling), (int)(height * scaling));
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public static void glRestoreScissor() {
/*  847 */     if (!GL11.glIsEnabled(3089)) {
/*  848 */       GL11.glEnable(3089);
/*      */     }
/*      */     
/*  851 */     GL11.glScissor(SCISSOR_BUFFER.get(0), SCISSOR_BUFFER.get(1), SCISSOR_BUFFER
/*  852 */         .get(2), SCISSOR_BUFFER.get(3));
/*      */   }
/*      */   
/*      */   public static void glEndScissor() {
/*  856 */     GL11.glDisable(3089);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static double[] worldToScreen(double[] positionVector, AxisAlignedBB boundingBox, double[] projection, double[] projectionBuffer) {
/*  863 */     double position[], bounds[][] = { { boundingBox.minX, boundingBox.minY, boundingBox.minZ }, { boundingBox.minX, boundingBox.maxY, boundingBox.minZ }, { boundingBox.minX, boundingBox.maxY, boundingBox.maxZ }, { boundingBox.minX, boundingBox.minY, boundingBox.maxZ }, { boundingBox.maxX, boundingBox.minY, boundingBox.minZ }, { boundingBox.maxX, boundingBox.maxY, boundingBox.minZ }, { boundingBox.maxX, boundingBox.maxY, boundingBox.maxZ }, { boundingBox.maxX, boundingBox.minY, boundingBox.maxZ } };
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  877 */     if (positionVector != null) {
/*  878 */       if (!worldToScreen(positionVector, projectionBuffer, projection[2])) {
/*  879 */         return null;
/*      */       }
/*  881 */       position = new double[] { projection[0], projection[1], -1.0D, -1.0D, projectionBuffer[0], projectionBuffer[1] };
/*      */     
/*      */     }
/*      */     else {
/*      */ 
/*      */       
/*  887 */       position = new double[] { projection[0], projection[1], -1.0D, -1.0D };
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  893 */     for (double[] vector : bounds) {
/*  894 */       if (worldToScreen(vector, projectionBuffer, projection[2])) {
/*  895 */         double projected_x = projectionBuffer[0];
/*  896 */         double projected_y = projectionBuffer[1];
/*      */         
/*  898 */         position[0] = Math.min(position[0], projected_x);
/*  899 */         position[1] = Math.min(position[1], projected_y);
/*  900 */         position[2] = Math.max(position[2], projected_x);
/*  901 */         position[3] = Math.max(position[3], projected_y);
/*      */       } 
/*      */     } 
/*      */     
/*  905 */     return position;
/*      */   }
/*      */   
/*      */   public static boolean worldToScreen(double[] in, double[] out, double scaling) {
/*  909 */     GL11.glGetFloat(2982, MODEL_MATRIX_BUFFER);
/*  910 */     GL11.glGetFloat(2983, PROJECTION_MATRIX_BUFFER);
/*  911 */     GL11.glGetInteger(2978, VIEWPORT_BUFFER);
/*      */     
/*  913 */     if (GLU.gluProject((float)in[0], (float)in[1], (float)in[2], MODEL_MATRIX_BUFFER, PROJECTION_MATRIX_BUFFER, VIEWPORT_BUFFER, WND_POS_BUFFER)) {
/*      */ 
/*      */       
/*  916 */       float zCoordinate = WND_POS_BUFFER.get(2);
/*      */       
/*  918 */       if (zCoordinate < 0.0F || zCoordinate > 1.0F) return false;
/*      */       
/*  920 */       out[0] = WND_POS_BUFFER.get(0) / scaling;
/*      */ 
/*      */       
/*  923 */       out[1] = (Display.getHeight() - WND_POS_BUFFER.get(1)) / scaling;
/*  924 */       return true;
/*      */     } 
/*      */     
/*  927 */     return false;
/*      */   }
/*      */   
/*      */   public static void glColour(int color) {
/*  931 */     GL11.glColor4ub((byte)(color >> 16 & 0xFF), (byte)(color >> 8 & 0xFF), (byte)(color & 0xFF), (byte)(color >> 24 & 0xFF));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void glDrawGradientLine(double x, double y, double x1, double y1, float lineWidth, int colour) {
/*  941 */     boolean restore = glEnableBlend();
/*      */     
/*  943 */     GL11.glDisable(3553);
/*      */     
/*  945 */     GL11.glLineWidth(lineWidth);
/*      */     
/*  947 */     GL11.glEnable(2848);
/*  948 */     GL11.glHint(3154, 4354);
/*      */     
/*  950 */     GL11.glShadeModel(7425);
/*      */     
/*  952 */     int noAlpha = ColourUtil.removeAlphaComponent(colour);
/*      */     
/*  954 */     GL11.glDisable(3008);
/*      */ 
/*      */     
/*  957 */     GL11.glBegin(3);
/*      */ 
/*      */     
/*  960 */     glColour(noAlpha);
/*  961 */     GL11.glVertex2d(x, y);
/*      */     
/*  963 */     double dif = x1 - x;
/*      */     
/*  965 */     glColour(colour);
/*  966 */     GL11.glVertex2d(x + dif * 0.4D, y);
/*      */     
/*  968 */     GL11.glVertex2d(x + dif * 0.6D, y);
/*      */     
/*  970 */     glColour(noAlpha);
/*  971 */     GL11.glVertex2d(x1, y1);
/*      */ 
/*      */     
/*  974 */     GL11.glEnd();
/*      */     
/*  976 */     GL11.glEnable(3008);
/*      */     
/*  978 */     GL11.glShadeModel(7424);
/*      */ 
/*      */     
/*  981 */     glRestoreBlend(restore);
/*      */     
/*  983 */     GL11.glDisable(2848);
/*  984 */     GL11.glHint(3154, 4352);
/*      */     
/*  986 */     GL11.glEnable(3553);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void glDrawLine(double x, double y, double x1, double y1, float lineWidth, boolean smoothed, int colour) {
/*  997 */     boolean restore = glEnableBlend();
/*      */     
/*  999 */     GL11.glDisable(3553);
/*      */     
/* 1001 */     GL11.glLineWidth(lineWidth);
/*      */     
/* 1003 */     if (smoothed) {
/*      */       
/* 1005 */       GL11.glEnable(2848);
/* 1006 */       GL11.glHint(3154, 4354);
/*      */     } 
/*      */     
/* 1009 */     glColour(colour);
/*      */ 
/*      */     
/* 1012 */     GL11.glBegin(1);
/*      */ 
/*      */     
/* 1015 */     GL11.glVertex2d(x, y);
/*      */     
/* 1017 */     GL11.glVertex2d(x1, y1);
/*      */ 
/*      */     
/* 1020 */     GL11.glEnd();
/*      */ 
/*      */     
/* 1023 */     glRestoreBlend(restore);
/* 1024 */     if (smoothed) {
/*      */       
/* 1026 */       GL11.glDisable(2848);
/* 1027 */       GL11.glHint(3154, 4352);
/*      */     } 
/*      */     
/* 1030 */     GL11.glEnable(3553);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void glDrawPlayerFace(double x, double y, double width, double height, ResourceLocation skinLocation) {
/* 1039 */     Minecraft.getMinecraft().getTextureManager().bindTexture(skinLocation);
/*      */     
/* 1041 */     GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
/* 1042 */     float eightPixelOff = 0.125F;
/*      */     
/* 1044 */     GL11.glBegin(7);
/*      */     
/* 1046 */     GL11.glTexCoord2f(0.125F, 0.125F);
/* 1047 */     GL11.glVertex2d(x, y);
/*      */     
/* 1049 */     GL11.glTexCoord2f(0.125F, 0.25F);
/* 1050 */     GL11.glVertex2d(x, y + height);
/*      */     
/* 1052 */     GL11.glTexCoord2f(0.25F, 0.25F);
/* 1053 */     GL11.glVertex2d(x + width, y + height);
/*      */     
/* 1055 */     GL11.glTexCoord2f(0.25F, 0.125F);
/* 1056 */     GL11.glVertex2d(x + width, y);
/*      */     
/* 1058 */     GL11.glEnd();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void glDrawSidewaysGradientRect(double x, double y, double width, double height, int startColour, int endColour) {
/* 1068 */     boolean restore = glEnableBlend();
/*      */     
/* 1070 */     GL11.glDisable(3553);
/*      */     
/* 1072 */     GL11.glShadeModel(7425);
/*      */ 
/*      */     
/* 1075 */     GL11.glBegin(7);
/*      */ 
/*      */     
/* 1078 */     glColour(startColour);
/* 1079 */     GL11.glVertex2d(x, y);
/* 1080 */     GL11.glVertex2d(x, y + height);
/*      */     
/* 1082 */     glColour(endColour);
/* 1083 */     GL11.glVertex2d(x + width, y + height);
/* 1084 */     GL11.glVertex2d(x + width, y);
/*      */ 
/*      */     
/* 1087 */     GL11.glEnd();
/*      */ 
/*      */     
/* 1090 */     GL11.glShadeModel(7424);
/*      */     
/* 1092 */     GL11.glEnable(3553);
/*      */     
/* 1094 */     glRestoreBlend(restore);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void glDrawFilledRect(double x, double y, double x1, double y1, int startColour, int endColour) {
/* 1104 */     boolean restore = glEnableBlend();
/*      */     
/* 1106 */     GL11.glDisable(3553);
/*      */     
/* 1108 */     GL11.glShadeModel(7425);
/*      */ 
/*      */     
/* 1111 */     GL11.glBegin(7);
/*      */ 
/*      */     
/* 1114 */     glColour(startColour);
/* 1115 */     GL11.glVertex2d(x, y);
/* 1116 */     glColour(endColour);
/* 1117 */     GL11.glVertex2d(x, y1);
/*      */     
/* 1119 */     GL11.glVertex2d(x1, y1);
/* 1120 */     glColour(startColour);
/* 1121 */     GL11.glVertex2d(x1, y);
/*      */ 
/*      */     
/* 1124 */     GL11.glEnd();
/*      */ 
/*      */     
/* 1127 */     GL11.glShadeModel(7424);
/*      */ 
/*      */     
/* 1130 */     GL11.glEnable(3553);
/*      */     
/* 1132 */     glRestoreBlend(restore);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void glDrawOutlinedQuad(double x, double y, double width, double height, float thickness, int colour) {
/* 1142 */     boolean restore = glEnableBlend();
/*      */     
/* 1144 */     GL11.glDisable(3553);
/*      */     
/* 1146 */     glColour(colour);
/*      */     
/* 1148 */     GL11.glLineWidth(thickness);
/*      */ 
/*      */     
/* 1151 */     GL11.glBegin(2);
/*      */     
/* 1153 */     GL11.glVertex2d(x, y);
/* 1154 */     GL11.glVertex2d(x, y + height);
/* 1155 */     GL11.glVertex2d(x + width, y + height);
/* 1156 */     GL11.glVertex2d(x + width, y);
/*      */ 
/*      */     
/* 1159 */     GL11.glEnd();
/*      */ 
/*      */     
/* 1162 */     GL11.glEnable(3553);
/*      */     
/* 1164 */     glRestoreBlend(restore);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void drawHollowRoundedRect(double x, double y, double width, double height, double cornerRadius, boolean smoothed, Color color) {
/* 1174 */     GL11.glDisable(3553);
/* 1175 */     GL11.glEnable(2848);
/* 1176 */     GL11.glEnable(3042);
/* 1177 */     GL11.glColor4f(color.getRed() / 255.0F, color.getGreen() / 255.0F, color.getBlue() / 255.0F, color.getAlpha() / 255.0F);
/* 1178 */     GL11.glLineWidth(1.0F);
/* 1179 */     GL11.glBegin(2);
/* 1180 */     double cornerX = x + width - cornerRadius;
/* 1181 */     double cornerY = y + height - cornerRadius; int i;
/* 1182 */     for (i = 0; i <= 90; i += 30)
/* 1183 */       GL11.glVertex2d(cornerX + Math.sin(i * Math.PI / 180.0D) * cornerRadius, cornerY + Math.cos(i * Math.PI / 180.0D) * cornerRadius); 
/* 1184 */     GL11.glEnd();
/* 1185 */     cornerX = x + width - cornerRadius;
/* 1186 */     cornerY = y + cornerRadius;
/* 1187 */     GL11.glBegin(2);
/* 1188 */     for (i = 90; i <= 180; i += 30)
/* 1189 */       GL11.glVertex2d(cornerX + Math.sin(i * Math.PI / 180.0D) * cornerRadius, cornerY + Math.cos(i * Math.PI / 180.0D) * cornerRadius); 
/* 1190 */     GL11.glEnd();
/* 1191 */     cornerX = x + cornerRadius;
/* 1192 */     cornerY = y + cornerRadius;
/* 1193 */     GL11.glBegin(2);
/* 1194 */     for (i = 180; i <= 270; i += 30)
/* 1195 */       GL11.glVertex2d(cornerX + Math.sin(i * Math.PI / 180.0D) * cornerRadius, cornerY + Math.cos(i * Math.PI / 180.0D) * cornerRadius); 
/* 1196 */     GL11.glEnd();
/* 1197 */     cornerX = x + cornerRadius;
/* 1198 */     cornerY = y + height - cornerRadius;
/* 1199 */     GL11.glBegin(2);
/* 1200 */     for (i = 270; i <= 360; i += 30)
/* 1201 */       GL11.glVertex2d(cornerX + Math.sin(i * Math.PI / 180.0D) * cornerRadius, cornerY + Math.cos(i * Math.PI / 180.0D) * cornerRadius); 
/* 1202 */     GL11.glEnd();
/* 1203 */     GL11.glDisable(3042);
/* 1204 */     GL11.glDisable(2848);
/* 1205 */     GL11.glEnable(3553);
/* 1206 */     glDrawLine(x + cornerRadius, y, x + width - cornerRadius, y, 1.0F, smoothed, color.getRGB());
/* 1207 */     glDrawLine(x + cornerRadius, y + height, x + width - cornerRadius, y + height, 1.0F, smoothed, color.getRGB());
/* 1208 */     glDrawLine(x, y + cornerRadius, x, y + height - cornerRadius, 1.0F, smoothed, color.getRGB());
/* 1209 */     glDrawLine(x + width, y + cornerRadius, x + width, y + height - cornerRadius, 1.0F, smoothed, color.getRGB());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void glDrawOutlinedQuadGradient(double x, double y, double width, double height, float thickness, int colour, int secondaryColour) {
/* 1219 */     boolean restore = glEnableBlend();
/*      */     
/* 1221 */     GL11.glDisable(3553);
/*      */     
/* 1223 */     GL11.glLineWidth(thickness);
/*      */ 
/*      */     
/* 1226 */     GL11.glShadeModel(7425);
/* 1227 */     GL11.glBegin(2);
/*      */ 
/*      */     
/* 1230 */     glColour(colour);
/* 1231 */     GL11.glVertex2d(x, y);
/* 1232 */     GL11.glVertex2d(x, y + height);
/*      */     
/* 1234 */     glColour(secondaryColour);
/* 1235 */     GL11.glVertex2d(x + width, y + height);
/* 1236 */     GL11.glVertex2d(x + width, y);
/*      */ 
/*      */     
/* 1239 */     GL11.glEnd();
/* 1240 */     GL11.glShadeModel(7424);
/*      */ 
/*      */     
/* 1243 */     GL11.glEnable(3553);
/*      */     
/* 1245 */     glRestoreBlend(restore);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void glDrawFilledQuad(double x, double y, double width, double height, int colour) {
/* 1254 */     boolean restore = glEnableBlend();
/*      */     
/* 1256 */     GL11.glDisable(3553);
/*      */     
/* 1258 */     glColour(colour);
/*      */ 
/*      */     
/* 1261 */     GL11.glBegin(7);
/*      */     
/* 1263 */     GL11.glVertex2d(x, y);
/* 1264 */     GL11.glVertex2d(x, y + height);
/* 1265 */     GL11.glVertex2d(x + width, y + height);
/* 1266 */     GL11.glVertex2d(x + width, y);
/*      */ 
/*      */     
/* 1269 */     GL11.glEnd();
/*      */ 
/*      */     
/* 1272 */     glRestoreBlend(restore);
/*      */     
/* 1274 */     GL11.glEnable(3553);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void glDrawFilledQuad(double x, double y, double width, double height, int startColour, int endColour) {
/* 1284 */     boolean restore = glEnableBlend();
/*      */     
/* 1286 */     GL11.glDisable(3553);
/*      */     
/* 1288 */     GL11.glShadeModel(7425);
/*      */ 
/*      */     
/* 1291 */     GL11.glBegin(7);
/*      */     
/* 1293 */     glColour(startColour);
/* 1294 */     GL11.glVertex2d(x, y);
/*      */     
/* 1296 */     glColour(endColour);
/* 1297 */     GL11.glVertex2d(x, y + height);
/* 1298 */     GL11.glVertex2d(x + width, y + height);
/*      */     
/* 1300 */     glColour(startColour);
/* 1301 */     GL11.glVertex2d(x + width, y);
/*      */ 
/*      */     
/* 1304 */     GL11.glEnd();
/*      */     
/* 1306 */     GL11.glShadeModel(7424);
/*      */ 
/*      */     
/* 1309 */     glRestoreBlend(restore);
/*      */     
/* 1311 */     GL11.glEnable(3553);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void glDrawFilledRect(double x, double y, double x1, double y1, int colour) {
/* 1320 */     boolean restore = glEnableBlend();
/*      */     
/* 1322 */     GL11.glDisable(3553);
/*      */     
/* 1324 */     glColour(colour);
/*      */ 
/*      */     
/* 1327 */     GL11.glBegin(7);
/*      */     
/* 1329 */     GL11.glVertex2d(x, y);
/* 1330 */     GL11.glVertex2d(x, y1);
/* 1331 */     GL11.glVertex2d(x1, y1);
/* 1332 */     GL11.glVertex2d(x1, y);
/*      */ 
/*      */     
/* 1335 */     GL11.glEnd();
/*      */ 
/*      */     
/* 1338 */     glRestoreBlend(restore);
/*      */     
/* 1340 */     GL11.glEnable(3553);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void glDrawArcFilled(double x, double y, float radius, float angleStart, float angleEnd, int segments, int colour) {
/* 1351 */     boolean restore = glEnableBlend();
/*      */     
/* 1353 */     GL11.glDisable(3553);
/*      */     
/* 1355 */     glColour(colour);
/*      */     
/* 1357 */     GL11.glDisable(2884);
/*      */     
/* 1359 */     GL11.glTranslated(x, y, 0.0D);
/*      */     
/* 1361 */     GL11.glBegin(9);
/*      */ 
/*      */     
/* 1364 */     GL11.glVertex2f(0.0F, 0.0F);
/*      */     
/* 1366 */     float[][] vertices = MathUtils.getArcVertices(radius, angleStart, angleEnd, segments);
/*      */     
/* 1368 */     for (float[] vertex : vertices)
/*      */     {
/* 1370 */       GL11.glVertex2f(vertex[0], vertex[1]);
/*      */     }
/*      */ 
/*      */     
/* 1374 */     GL11.glEnd();
/*      */     
/* 1376 */     GL11.glTranslated(-x, -y, 0.0D);
/*      */     
/* 1378 */     glRestoreBlend(restore);
/*      */     
/* 1380 */     GL11.glEnable(2884);
/*      */     
/* 1382 */     GL11.glEnable(3553);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void glDrawArcOutline(double x, double y, float radius, float angleStart, float angleEnd, float lineWidth, int colour) {
/* 1393 */     int segments = (int)(radius * 4.0F);
/*      */     
/* 1395 */     boolean restore = glEnableBlend();
/*      */     
/* 1397 */     GL11.glDisable(3553);
/*      */     
/* 1399 */     GL11.glLineWidth(lineWidth);
/*      */     
/* 1401 */     glColour(colour);
/*      */     
/* 1403 */     GL11.glEnable(2848);
/* 1404 */     GL11.glHint(3154, 4354);
/*      */     
/* 1406 */     GL11.glTranslated(x, y, 0.0D);
/*      */     
/* 1408 */     GL11.glBegin(3);
/*      */     
/* 1410 */     float[][] vertices = MathUtils.getArcVertices(radius, angleStart, angleEnd, segments);
/*      */     
/* 1412 */     for (float[] vertex : vertices)
/*      */     {
/* 1414 */       GL11.glVertex2f(vertex[0], vertex[1]);
/*      */     }
/*      */ 
/*      */     
/* 1418 */     GL11.glEnd();
/*      */     
/* 1420 */     GL11.glTranslated(-x, -y, 0.0D);
/*      */     
/* 1422 */     GL11.glDisable(2848);
/* 1423 */     GL11.glHint(3154, 4352);
/*      */     
/* 1425 */     glRestoreBlend(restore);
/*      */     
/* 1427 */     GL11.glEnable(3553);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void glDrawPoint(double x, double y, float radius, ScaledResolution scaledResolution, int colour) {
/* 1436 */     boolean restore = glEnableBlend();
/*      */     
/* 1438 */     GL11.glEnable(2832);
/* 1439 */     GL11.glHint(3153, 4354);
/*      */     
/* 1441 */     GL11.glDisable(3553);
/*      */     
/* 1443 */     glColour(colour);
/*      */     
/* 1445 */     GL11.glPointSize(radius * GL11.glGetFloat(2982) * scaledResolution.getScaleFactor());
/*      */     
/* 1447 */     GL11.glBegin(0);
/*      */     
/* 1449 */     GL11.glVertex2d(x, y);
/*      */     
/* 1451 */     GL11.glEnd();
/*      */ 
/*      */     
/* 1454 */     glRestoreBlend(restore);
/*      */     
/* 1456 */     GL11.glDisable(2832);
/* 1457 */     GL11.glHint(3153, 4352);
/*      */     
/* 1459 */     GL11.glEnable(3553);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void glDrawRoundedOutline(double x, double y, double width, double height, float lineWidth, RoundingMode roundingMode, float rounding, int colour) {
/* 1470 */     boolean bLeft = false;
/* 1471 */     boolean tLeft = false;
/* 1472 */     boolean bRight = false;
/* 1473 */     boolean tRight = false;
/*      */     
/* 1475 */     switch (roundingMode) {
/*      */       case TOP:
/* 1477 */         tLeft = true;
/* 1478 */         tRight = true;
/*      */         break;
/*      */       case BOTTOM:
/* 1481 */         bLeft = true;
/* 1482 */         bRight = true;
/*      */         break;
/*      */       case FULL:
/* 1485 */         tLeft = true;
/* 1486 */         tRight = true;
/* 1487 */         bLeft = true;
/* 1488 */         bRight = true;
/*      */         break;
/*      */       case LEFT:
/* 1491 */         bLeft = true;
/* 1492 */         tLeft = true;
/*      */         break;
/*      */       case RIGHT:
/* 1495 */         bRight = true;
/* 1496 */         tRight = true;
/*      */         break;
/*      */       case TOP_LEFT:
/* 1499 */         tLeft = true;
/*      */         break;
/*      */       case TOP_RIGHT:
/* 1502 */         tRight = true;
/*      */         break;
/*      */       case BOTTOM_LEFT:
/* 1505 */         bLeft = true;
/*      */         break;
/*      */       case BOTTOM_RIGHT:
/* 1508 */         bRight = true;
/*      */         break;
/*      */     } 
/*      */ 
/*      */     
/* 1513 */     GL11.glTranslated(x, y, 0.0D);
/*      */     
/* 1515 */     boolean restore = glEnableBlend();
/*      */     
/* 1517 */     if (tLeft)
/*      */     {
/* 1519 */       glDrawArcOutline(rounding, rounding, rounding, 270.0F, 360.0F, lineWidth, colour);
/*      */     }
/*      */ 
/*      */     
/* 1523 */     if (tRight)
/*      */     {
/* 1525 */       glDrawArcOutline(width - rounding, rounding, rounding, 0.0F, 90.0F, lineWidth, colour);
/*      */     }
/*      */ 
/*      */     
/* 1529 */     if (bLeft)
/*      */     {
/* 1531 */       glDrawArcOutline(rounding, height - rounding, rounding, 180.0F, 270.0F, lineWidth, colour);
/*      */     }
/*      */ 
/*      */     
/* 1535 */     if (bRight)
/*      */     {
/* 1537 */       glDrawArcOutline(width - rounding, height - rounding, rounding, 90.0F, 180.0F, lineWidth, colour);
/*      */     }
/*      */ 
/*      */ 
/*      */     
/* 1542 */     GL11.glDisable(3553);
/*      */     
/* 1544 */     glColour(colour);
/*      */ 
/*      */     
/* 1547 */     GL11.glBegin(1);
/*      */     
/* 1549 */     if (tLeft) {
/* 1550 */       GL11.glVertex2d(0.0D, rounding);
/*      */     } else {
/* 1552 */       GL11.glVertex2d(0.0D, 0.0D);
/*      */     } 
/*      */     
/* 1555 */     if (bLeft) {
/* 1556 */       GL11.glVertex2d(0.0D, height - rounding);
/* 1557 */       GL11.glVertex2d(rounding, height);
/*      */     } else {
/* 1559 */       GL11.glVertex2d(0.0D, height);
/* 1560 */       GL11.glVertex2d(0.0D, height);
/*      */     } 
/*      */     
/* 1563 */     if (bRight) {
/* 1564 */       GL11.glVertex2d(width - rounding, height);
/* 1565 */       GL11.glVertex2d(width, height - rounding);
/*      */     } else {
/* 1567 */       GL11.glVertex2d(width, height);
/* 1568 */       GL11.glVertex2d(width, height);
/*      */     } 
/*      */     
/* 1571 */     if (tRight) {
/* 1572 */       GL11.glVertex2d(width, rounding);
/* 1573 */       GL11.glVertex2d(width - rounding, 0.0D);
/*      */     } else {
/* 1575 */       GL11.glVertex2d(width, 0.0D);
/* 1576 */       GL11.glVertex2d(width, 0.0D);
/*      */     } 
/*      */     
/* 1579 */     if (tLeft) {
/* 1580 */       GL11.glVertex2d(rounding, 0.0D);
/*      */     } else {
/* 1582 */       GL11.glVertex2d(0.0D, 0.0D);
/*      */     } 
/*      */ 
/*      */     
/* 1586 */     GL11.glEnd();
/*      */ 
/*      */     
/* 1589 */     glRestoreBlend(restore);
/*      */     
/* 1591 */     GL11.glTranslated(-x, -y, 0.0D);
/*      */     
/* 1593 */     GL11.glEnable(3553);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void glDrawSemiCircle(double x, double y, double diameter, float innerRadius, double percentage, int colour) {
/* 1601 */     boolean restore = glEnableBlend();
/*      */     
/* 1603 */     boolean alphaTest = GL11.glIsEnabled(3008);
/* 1604 */     if (alphaTest) GL11.glDisable(3008);
/*      */     
/* 1606 */     GL20.glUseProgram(CIRCLE_SHADER.getProgram());
/* 1607 */     GL20.glUniform1f(CIRCLE_SHADER.getUniformLocation("innerRadius"), innerRadius);
/* 1608 */     GL20.glUniform4f(CIRCLE_SHADER.getUniformLocation("colour"), (colour >> 16 & 0xFF) / 255.0F, (colour >> 8 & 0xFF) / 255.0F, (colour & 0xFF) / 255.0F, (colour >> 24 & 0xFF) / 255.0F);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1614 */     GL11.glBegin(7);
/*      */     
/* 1616 */     GL11.glTexCoord2f(0.0F, 0.0F);
/* 1617 */     GL11.glVertex2d(x, y);
/*      */     
/* 1619 */     GL11.glTexCoord2f(0.0F, 1.0F);
/* 1620 */     GL11.glVertex2d(x, y + diameter);
/*      */     
/* 1622 */     GL11.glTexCoord2f(1.0F, 1.0F);
/* 1623 */     GL11.glVertex2d(x + diameter, y + diameter);
/*      */     
/* 1625 */     GL11.glTexCoord2f(1.0F, 0.0F);
/* 1626 */     GL11.glVertex2d(x + diameter, y);
/*      */     
/* 1628 */     GL11.glEnd();
/*      */     
/* 1630 */     GL20.glUseProgram(0);
/*      */     
/* 1632 */     if (alphaTest) GL11.glEnable(3008);
/*      */     
/* 1634 */     glRestoreBlend(restore);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void glDrawRoundedQuad(double x, double y, float width, float height, float radius, int colour) {
/* 1641 */     boolean restore = glEnableBlend();
/*      */     
/* 1643 */     boolean alphaTest = GL11.glIsEnabled(3008);
/* 1644 */     if (alphaTest) GL11.glDisable(3008);
/*      */     
/* 1646 */     GL20.glUseProgram(ROUNDED_QUAD_SHADER.getProgram());
/* 1647 */     GL20.glUniform1f(ROUNDED_QUAD_SHADER.getUniformLocation("width"), width);
/* 1648 */     GL20.glUniform1f(ROUNDED_QUAD_SHADER.getUniformLocation("height"), height);
/* 1649 */     GL20.glUniform1f(ROUNDED_QUAD_SHADER.getUniformLocation("radius"), radius);
/* 1650 */     GL20.glUniform4f(ROUNDED_QUAD_SHADER.getUniformLocation("colour"), (colour >> 16 & 0xFF) / 255.0F, (colour >> 8 & 0xFF) / 255.0F, (colour & 0xFF) / 255.0F, (colour >> 24 & 0xFF) / 255.0F);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1656 */     GL11.glDisable(3553);
/*      */     
/* 1658 */     GL11.glBegin(7);
/*      */     
/* 1660 */     GL11.glTexCoord2f(0.0F, 0.0F);
/* 1661 */     GL11.glVertex2d(x, y);
/*      */     
/* 1663 */     GL11.glTexCoord2f(0.0F, 1.0F);
/* 1664 */     GL11.glVertex2d(x, y + height);
/*      */     
/* 1666 */     GL11.glTexCoord2f(1.0F, 1.0F);
/* 1667 */     GL11.glVertex2d(x + width, y + height);
/*      */     
/* 1669 */     GL11.glTexCoord2f(1.0F, 0.0F);
/* 1670 */     GL11.glVertex2d(x + width, y);
/*      */     
/* 1672 */     GL11.glEnd();
/*      */     
/* 1674 */     GL20.glUseProgram(0);
/*      */     
/* 1676 */     GL11.glEnable(3553);
/*      */     
/* 1678 */     if (alphaTest) GL11.glEnable(3008);
/*      */     
/* 1680 */     glRestoreBlend(restore);
/*      */   }
/*      */   
/*      */   public static void glDrawRoundedQuadRainbow(double x, double y, float width, float height, float radius) {
/* 1684 */     boolean restore = glEnableBlend();
/*      */     
/* 1686 */     boolean alphaTest = GL11.glIsEnabled(3008);
/* 1687 */     if (alphaTest) GL11.glDisable(3008);
/*      */     
/* 1689 */     GL_COLOUR_SHADER.use();
/* 1690 */     GL20.glUniform1f(GL_COLOUR_SHADER.getUniformLocation("width"), width);
/* 1691 */     GL20.glUniform1f(GL_COLOUR_SHADER.getUniformLocation("height"), height);
/* 1692 */     GL20.glUniform1f(GL_COLOUR_SHADER.getUniformLocation("radius"), radius);
/*      */     
/* 1694 */     GL11.glDisable(3553);
/*      */     
/* 1696 */     GL11.glBegin(7);
/*      */     
/* 1698 */     GL11.glTexCoord2f(0.0F, 0.0F);
/* 1699 */     GL11.glVertex2d(x, y);
/*      */     
/* 1701 */     GL11.glTexCoord2f(0.0F, 1.0F);
/* 1702 */     GL11.glVertex2d(x, y + height);
/*      */     
/* 1704 */     GL11.glTexCoord2f(1.0F, 1.0F);
/* 1705 */     GL11.glVertex2d(x + width, y + height);
/*      */     
/* 1707 */     GL11.glTexCoord2f(1.0F, 0.0F);
/* 1708 */     GL11.glVertex2d(x + width, y);
/*      */ 
/*      */     
/* 1711 */     GL11.glEnd();
/*      */     
/* 1713 */     GL20.glUseProgram(0);
/*      */     
/* 1715 */     GL11.glEnable(3553);
/*      */     
/* 1717 */     if (alphaTest) GL11.glEnable(3008);
/*      */     
/* 1719 */     glRestoreBlend(restore);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void glDrawRoundedRect(double x, double y, double width, double height, RoundingMode roundingMode, float rounding, float scaleFactor, int colour) {
/* 1730 */     boolean bLeft = false;
/* 1731 */     boolean tLeft = false;
/* 1732 */     boolean bRight = false;
/* 1733 */     boolean tRight = false;
/*      */     
/* 1735 */     switch (roundingMode) {
/*      */       case TOP:
/* 1737 */         tLeft = true;
/* 1738 */         tRight = true;
/*      */         break;
/*      */       case BOTTOM:
/* 1741 */         bLeft = true;
/* 1742 */         bRight = true;
/*      */         break;
/*      */       case FULL:
/* 1745 */         tLeft = true;
/* 1746 */         tRight = true;
/* 1747 */         bLeft = true;
/* 1748 */         bRight = true;
/*      */         break;
/*      */       case LEFT:
/* 1751 */         bLeft = true;
/* 1752 */         tLeft = true;
/*      */         break;
/*      */       case RIGHT:
/* 1755 */         bRight = true;
/* 1756 */         tRight = true;
/*      */         break;
/*      */       case TOP_LEFT:
/* 1759 */         tLeft = true;
/*      */         break;
/*      */       case TOP_RIGHT:
/* 1762 */         tRight = true;
/*      */         break;
/*      */       case BOTTOM_LEFT:
/* 1765 */         bLeft = true;
/*      */         break;
/*      */       case BOTTOM_RIGHT:
/* 1768 */         bRight = true;
/*      */         break;
/*      */     } 
/*      */     
/* 1772 */     float alpha = (colour >> 24 & 0xFF) / 255.0F;
/*      */ 
/*      */     
/* 1775 */     boolean restore = glEnableBlend();
/*      */ 
/*      */     
/* 1778 */     glColour(colour);
/*      */ 
/*      */     
/* 1781 */     GL11.glTranslated(x, y, 0.0D);
/*      */     
/* 1783 */     GL11.glDisable(3553);
/*      */ 
/*      */     
/* 1786 */     GL11.glBegin(9);
/*      */     
/* 1788 */     if (tLeft) {
/* 1789 */       GL11.glVertex2d(rounding, rounding);
/* 1790 */       GL11.glVertex2d(0.0D, rounding);
/*      */     } else {
/* 1792 */       GL11.glVertex2d(0.0D, 0.0D);
/*      */     } 
/*      */     
/* 1795 */     if (bLeft) {
/* 1796 */       GL11.glVertex2d(0.0D, height - rounding);
/* 1797 */       GL11.glVertex2d(rounding, height - rounding);
/* 1798 */       GL11.glVertex2d(rounding, height);
/*      */     } else {
/* 1800 */       GL11.glVertex2d(0.0D, height);
/*      */     } 
/*      */     
/* 1803 */     if (bRight) {
/* 1804 */       GL11.glVertex2d(width - rounding, height);
/* 1805 */       GL11.glVertex2d(width - rounding, height - rounding);
/* 1806 */       GL11.glVertex2d(width, height - rounding);
/*      */     } else {
/* 1808 */       GL11.glVertex2d(width, height);
/*      */     } 
/*      */     
/* 1811 */     if (tRight) {
/* 1812 */       GL11.glVertex2d(width, rounding);
/* 1813 */       GL11.glVertex2d(width - rounding, rounding);
/* 1814 */       GL11.glVertex2d(width - rounding, 0.0D);
/*      */     } else {
/* 1816 */       GL11.glVertex2d(width, 0.0D);
/*      */     } 
/*      */     
/* 1819 */     if (tLeft) {
/* 1820 */       GL11.glVertex2d(rounding, 0.0D);
/*      */     }
/*      */ 
/*      */     
/* 1824 */     GL11.glEnd();
/*      */ 
/*      */     
/* 1827 */     GL11.glEnable(2832);
/* 1828 */     GL11.glHint(3153, 4354);
/*      */ 
/*      */     
/* 1831 */     GL11.glPointSize(rounding * 2.0F * GL11.glGetFloat(2982) * scaleFactor);
/*      */     
/* 1833 */     GL11.glBegin(0);
/*      */     
/* 1835 */     if (tLeft)
/*      */     {
/* 1837 */       GL11.glVertex2d(rounding, rounding);
/*      */     }
/*      */     
/* 1840 */     if (tRight)
/*      */     {
/* 1842 */       GL11.glVertex2d(width - rounding, rounding);
/*      */     }
/*      */     
/* 1845 */     if (bLeft)
/*      */     {
/* 1847 */       GL11.glVertex2d(rounding, height - rounding);
/*      */     }
/*      */     
/* 1850 */     if (bRight)
/*      */     {
/* 1852 */       GL11.glVertex2d(width - rounding, height - rounding);
/*      */     }
/*      */     
/* 1855 */     GL11.glEnd();
/*      */ 
/*      */     
/* 1858 */     GL11.glDisable(2832);
/* 1859 */     GL11.glHint(3153, 4352);
/*      */     
/* 1861 */     glRestoreBlend(restore);
/*      */     
/* 1863 */     GL11.glTranslated(-x, -y, 0.0D);
/*      */     
/* 1865 */     GL11.glEnable(3553);
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
/*      */   public static void glDrawRoundedRectEllipse(double x, double y, double width, double height, RoundingMode roundingMode, int roundingDef, double roundingLevel, int colour) {
/* 1878 */     boolean bLeft = false;
/* 1879 */     boolean tLeft = false;
/* 1880 */     boolean bRight = false;
/* 1881 */     boolean tRight = false;
/*      */     
/* 1883 */     switch (roundingMode) {
/*      */       case TOP:
/* 1885 */         tLeft = true;
/* 1886 */         tRight = true;
/*      */         break;
/*      */       case BOTTOM:
/* 1889 */         bLeft = true;
/* 1890 */         bRight = true;
/*      */         break;
/*      */       case FULL:
/* 1893 */         tLeft = true;
/* 1894 */         tRight = true;
/* 1895 */         bLeft = true;
/* 1896 */         bRight = true;
/*      */         break;
/*      */       case LEFT:
/* 1899 */         bLeft = true;
/* 1900 */         tLeft = true;
/*      */         break;
/*      */       case RIGHT:
/* 1903 */         bRight = true;
/* 1904 */         tRight = true;
/*      */         break;
/*      */       case TOP_LEFT:
/* 1907 */         tLeft = true;
/*      */         break;
/*      */       case TOP_RIGHT:
/* 1910 */         tRight = true;
/*      */         break;
/*      */       case BOTTOM_LEFT:
/* 1913 */         bLeft = true;
/*      */         break;
/*      */       case BOTTOM_RIGHT:
/* 1916 */         bRight = true;
/*      */         break;
/*      */     } 
/*      */ 
/*      */     
/* 1921 */     GL11.glTranslated(x, y, 0.0D);
/*      */     
/* 1923 */     GL11.glEnable(2881);
/* 1924 */     GL11.glHint(3154, 4354);
/*      */     
/* 1926 */     boolean restore = glEnableBlend();
/*      */     
/* 1928 */     if (tLeft)
/*      */     {
/* 1930 */       glDrawFilledEllipse(roundingLevel, roundingLevel, roundingLevel, (int)(roundingDef * 0.5D), (int)(roundingDef * 0.75D), roundingDef, false, colour);
/*      */     }
/*      */ 
/*      */ 
/*      */     
/* 1935 */     if (tRight)
/*      */     {
/* 1937 */       glDrawFilledEllipse(width - roundingLevel, roundingLevel, roundingLevel, (int)(roundingDef * 0.75D), roundingDef, roundingDef, false, colour);
/*      */     }
/*      */ 
/*      */ 
/*      */     
/* 1942 */     if (bLeft)
/*      */     {
/* 1944 */       glDrawFilledEllipse(roundingLevel, height - roundingLevel, roundingLevel, (int)(roundingDef * 0.25D), (int)(roundingDef * 0.5D), roundingDef, false, colour);
/*      */     }
/*      */ 
/*      */ 
/*      */     
/* 1949 */     if (bRight)
/*      */     {
/* 1951 */       glDrawFilledEllipse(width - roundingLevel, height - roundingLevel, roundingLevel, 0, (int)(roundingDef * 0.25D), roundingDef, false, colour);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1957 */     GL11.glDisable(2881);
/* 1958 */     GL11.glHint(3154, 4352);
/*      */ 
/*      */     
/* 1961 */     GL11.glDisable(3553);
/*      */     
/* 1963 */     glColour(colour);
/*      */ 
/*      */     
/* 1966 */     GL11.glBegin(9);
/*      */     
/* 1968 */     if (tLeft) {
/* 1969 */       GL11.glVertex2d(roundingLevel, roundingLevel);
/* 1970 */       GL11.glVertex2d(0.0D, roundingLevel);
/*      */     } else {
/* 1972 */       GL11.glVertex2d(0.0D, 0.0D);
/*      */     } 
/*      */     
/* 1975 */     if (bLeft) {
/* 1976 */       GL11.glVertex2d(0.0D, height - roundingLevel);
/* 1977 */       GL11.glVertex2d(roundingLevel, height - roundingLevel);
/* 1978 */       GL11.glVertex2d(roundingLevel, height);
/*      */     } else {
/* 1980 */       GL11.glVertex2d(0.0D, height);
/*      */     } 
/*      */     
/* 1983 */     if (bRight) {
/* 1984 */       GL11.glVertex2d(width - roundingLevel, height);
/* 1985 */       GL11.glVertex2d(width - roundingLevel, height - roundingLevel);
/* 1986 */       GL11.glVertex2d(width, height - roundingLevel);
/*      */     } else {
/* 1988 */       GL11.glVertex2d(width, height);
/*      */     } 
/*      */     
/* 1991 */     if (tRight) {
/* 1992 */       GL11.glVertex2d(width, roundingLevel);
/* 1993 */       GL11.glVertex2d(width - roundingLevel, roundingLevel);
/* 1994 */       GL11.glVertex2d(width - roundingLevel, 0.0D);
/*      */     } else {
/* 1996 */       GL11.glVertex2d(width, 0.0D);
/*      */     } 
/*      */     
/* 1999 */     if (tLeft) {
/* 2000 */       GL11.glVertex2d(roundingLevel, 0.0D);
/*      */     }
/*      */ 
/*      */     
/* 2004 */     GL11.glEnd();
/*      */ 
/*      */     
/* 2007 */     glRestoreBlend(restore);
/*      */     
/* 2009 */     GL11.glTranslated(-x, -y, 0.0D);
/*      */     
/* 2011 */     GL11.glEnable(3553);
/*      */   }
/*      */   
/*      */   public static boolean glEnableBlend() {
/* 2015 */     boolean wasEnabled = GL11.glIsEnabled(3042);
/*      */     
/* 2017 */     if (!wasEnabled) {
/* 2018 */       GL11.glEnable(3042);
/* 2019 */       GL14.glBlendFuncSeparate(770, 771, 1, 0);
/*      */     } 
/*      */     
/* 2022 */     return wasEnabled;
/*      */   }
/*      */   
/*      */   public static void glRestoreBlend(boolean wasEnabled) {
/* 2026 */     if (!wasEnabled) {
/* 2027 */       GL11.glDisable(3042);
/*      */     }
/*      */   }
/*      */   
/*      */   public static float interpolate(float old, float now, float progress) {
/* 2032 */     return old + (now - old) * progress;
/*      */   }
/*      */   
/*      */   public static double interpolate(double old, double now, double progress) {
/* 2036 */     return old + (now - old) * progress;
/*      */   }
/*      */   
/*      */   public static Vec3 interpolate(Vec3 old, Vec3 now, double progress) {
/* 2040 */     Vec3 difVec = now.subtract(old);
/* 2041 */     return new Vec3(old.xCoord + difVec.xCoord * progress, old.yCoord + difVec.yCoord * progress, old.zCoord + difVec.zCoord * progress);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public static double[] interpolate(Entity entity, float partialTicks) {
/* 2047 */     return new double[] {
/* 2048 */         interpolate(entity.prevPosX, entity.posX, partialTicks), 
/* 2049 */         interpolate(entity.prevPosY, entity.posY, partialTicks), 
/* 2050 */         interpolate(entity.prevPosZ, entity.posZ, partialTicks)
/*      */       };
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public static AxisAlignedBB interpolate(Entity entity, AxisAlignedBB boundingBox, float partialTicks) {
/* 2057 */     float invertedPT = 1.0F - partialTicks;
/* 2058 */     return boundingBox.offset((entity.posX - entity.prevPosX) * -invertedPT, (entity.posY - entity.prevPosY) * -invertedPT, (entity.posZ - entity.prevPosZ) * -invertedPT);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void glDrawBoundingBox(AxisAlignedBB bb, float lineWidth, boolean filled) {
/* 2068 */     if (filled) {
/*      */       
/* 2070 */       GL11.glBegin(8);
/*      */       
/* 2072 */       GL11.glVertex3d(bb.minX, bb.minY, bb.minZ);
/* 2073 */       GL11.glVertex3d(bb.minX, bb.maxY, bb.minZ);
/*      */       
/* 2075 */       GL11.glVertex3d(bb.maxX, bb.minY, bb.minZ);
/* 2076 */       GL11.glVertex3d(bb.maxX, bb.maxY, bb.minZ);
/*      */       
/* 2078 */       GL11.glVertex3d(bb.maxX, bb.minY, bb.maxZ);
/* 2079 */       GL11.glVertex3d(bb.maxX, bb.maxY, bb.maxZ);
/*      */       
/* 2081 */       GL11.glVertex3d(bb.minX, bb.minY, bb.maxZ);
/* 2082 */       GL11.glVertex3d(bb.minX, bb.maxY, bb.maxZ);
/*      */       
/* 2084 */       GL11.glVertex3d(bb.minX, bb.minY, bb.minZ);
/* 2085 */       GL11.glVertex3d(bb.minX, bb.maxY, bb.minZ);
/*      */       
/* 2087 */       GL11.glEnd();
/*      */ 
/*      */       
/* 2090 */       GL11.glBegin(7);
/*      */       
/* 2092 */       GL11.glVertex3d(bb.minX, bb.minY, bb.minZ);
/* 2093 */       GL11.glVertex3d(bb.maxX, bb.minY, bb.minZ);
/* 2094 */       GL11.glVertex3d(bb.maxX, bb.minY, bb.maxZ);
/* 2095 */       GL11.glVertex3d(bb.minX, bb.minY, bb.maxZ);
/*      */       
/* 2097 */       GL11.glEnd();
/*      */       
/* 2099 */       GL11.glCullFace(1028);
/*      */ 
/*      */       
/* 2102 */       GL11.glBegin(7);
/*      */       
/* 2104 */       GL11.glVertex3d(bb.minX, bb.maxY, bb.minZ);
/* 2105 */       GL11.glVertex3d(bb.maxX, bb.maxY, bb.minZ);
/* 2106 */       GL11.glVertex3d(bb.maxX, bb.maxY, bb.maxZ);
/* 2107 */       GL11.glVertex3d(bb.minX, bb.maxY, bb.maxZ);
/*      */       
/* 2109 */       GL11.glEnd();
/*      */       
/* 2111 */       GL11.glCullFace(1029);
/*      */     } 
/*      */ 
/*      */     
/* 2115 */     if (lineWidth > 0.0F) {
/* 2116 */       GL11.glLineWidth(lineWidth);
/*      */       
/* 2118 */       GL11.glEnable(2848);
/* 2119 */       GL11.glHint(3154, 4354);
/*      */       
/* 2121 */       GL11.glBegin(3);
/*      */ 
/*      */       
/* 2124 */       GL11.glVertex3d(bb.minX, bb.minY, bb.minZ);
/* 2125 */       GL11.glVertex3d(bb.maxX, bb.minY, bb.minZ);
/* 2126 */       GL11.glVertex3d(bb.maxX, bb.minY, bb.maxZ);
/* 2127 */       GL11.glVertex3d(bb.minX, bb.minY, bb.maxZ);
/* 2128 */       GL11.glVertex3d(bb.minX, bb.minY, bb.minZ);
/*      */ 
/*      */       
/* 2131 */       GL11.glVertex3d(bb.minX, bb.maxY, bb.minZ);
/* 2132 */       GL11.glVertex3d(bb.maxX, bb.maxY, bb.minZ);
/* 2133 */       GL11.glVertex3d(bb.maxX, bb.maxY, bb.maxZ);
/* 2134 */       GL11.glVertex3d(bb.minX, bb.maxY, bb.maxZ);
/* 2135 */       GL11.glVertex3d(bb.minX, bb.maxY, bb.minZ);
/*      */       
/* 2137 */       GL11.glEnd();
/*      */       
/* 2139 */       GL11.glBegin(1);
/*      */       
/* 2141 */       GL11.glVertex3d(bb.maxX, bb.minY, bb.minZ);
/* 2142 */       GL11.glVertex3d(bb.maxX, bb.maxY, bb.minZ);
/*      */       
/* 2144 */       GL11.glVertex3d(bb.maxX, bb.minY, bb.maxZ);
/* 2145 */       GL11.glVertex3d(bb.maxX, bb.maxY, bb.maxZ);
/*      */       
/* 2147 */       GL11.glVertex3d(bb.minX, bb.minY, bb.maxZ);
/* 2148 */       GL11.glVertex3d(bb.minX, bb.maxY, bb.maxZ);
/*      */       
/* 2150 */       GL11.glEnd();
/*      */       
/* 2152 */       GL11.glDisable(2848);
/* 2153 */       GL11.glHint(3154, 4352);
/*      */     } 
/*      */   }
/*      */   
/*      */   public static void renderGradientRectLeftRight(int i, int i1, int i2, int i3, int rgb, int i4) {
/* 2158 */     (Minecraft.getMinecraft()).ingameGUI.renderGradientRectLeftRight(i, i1, i2, i3, rgb, i4);
/*      */   }
/*      */   
/*      */   public void circle(double x, double y, double radius, boolean filled, Color color) {
/* 2162 */     polygon(x, y, radius, 360.0D, filled, color);
/*      */   }
/*      */   
/*      */   public void circle(double x, double y, double radius, boolean filled) {
/* 2166 */     polygon(x, y, radius, 360, filled);
/*      */   }
/*      */   
/*      */   public void push() {
/* 2170 */     GL11.glPushMatrix();
/*      */   }
/*      */   
/*      */   public void pop() {
/* 2174 */     GL11.glPopMatrix();
/*      */   }
/*      */   
/*      */   public void color(double red, double green, double blue) {
/* 2178 */     color(red, green, blue, 1.0D);
/*      */   }
/*      */   
/*      */   public void color(Color color, int alpha) {
/* 2182 */     if (color == null)
/* 2183 */       color = Color.white; 
/* 2184 */     color((color.getRed() / 255.0F), (color.getGreen() / 255.0F), (color.getBlue() / 255.0F), 0.5D);
/*      */   }
/*      */   
/*      */   public void lineWidth(double width) {
/* 2188 */     GL11.glLineWidth((float)width);
/*      */   }
/*      */   
/*      */   public void startSmooth() {
/* 2192 */     enable(2881);
/* 2193 */     enable(2848);
/* 2194 */     enable(2832);
/*      */   }
/*      */   
/*      */   public void endSmooth() {
/* 2198 */     disable(2832);
/* 2199 */     disable(2848);
/* 2200 */     disable(2881);
/*      */   }
/*      */   
/*      */   public void rect(double x, double y, double width, double height, boolean filled) {
/* 2204 */     rect(x, y, width, height, filled, null);
/*      */   }
/*      */   
/*      */   public void rect(double x, double y, double width, double height) {
/* 2208 */     rect(x, y, width, height, true, null);
/*      */   }
/*      */   
/*      */   public enum RoundingMode {
/* 2212 */     TOP_LEFT,
/* 2213 */     BOTTOM_LEFT,
/* 2214 */     TOP_RIGHT,
/* 2215 */     BOTTOM_RIGHT,
/*      */     
/* 2217 */     LEFT,
/* 2218 */     RIGHT,
/*      */     
/* 2220 */     TOP,
/* 2221 */     BOTTOM,
/*      */     
/* 2223 */     FULL;
/*      */   }
/*      */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\awareline\mai\\utility\ghosthitutily\DrawUtil.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */