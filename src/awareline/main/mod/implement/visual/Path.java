/*     */ package awareline.main.mod.implement.visual;
/*     */ 
/*     */ import awareline.main.event.EventHandler;
/*     */ import awareline.main.event.events.world.renderEvents.EventRender3D;
/*     */ import awareline.main.event.events.world.updateEvents.EventPreUpdate;
/*     */ import awareline.main.mod.Module;
/*     */ import awareline.main.mod.ModuleType;
/*     */ import awareline.main.mod.implement.visual.sucks.info.AStarNode;
/*     */ import awareline.main.ui.gui.clickgui.RenderUtil;
/*     */ import awareline.main.utility.render.color.Colors;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Iterator;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import org.lwjgl.opengl.GL11;
/*     */ 
/*     */ public class Path
/*     */   extends Module {
/*     */   public EntityPlayer player;
/*  21 */   private final ArrayList<AStarNode> openList = new ArrayList<>();
/*  22 */   private final ArrayList<AStarNode> closedList = new ArrayList<>();
/*  23 */   private final ArrayList<AStarNode> path = new ArrayList<>();
/*     */   private boolean startNextThread = true;
/*     */   
/*     */   public Path() {
/*  27 */     super("Path", ModuleType.Render);
/*     */   }
/*     */ 
/*     */   
/*     */   @EventHandler
/*     */   public void onUpdate(EventPreUpdate event) {
/*  33 */     setEntity();
/*  34 */     if (this.player != null && this.startNextThread) {
/*  35 */       setEntity();
/*  36 */       this.startNextThread = false;
/*  37 */       this.openList.clear();
/*  38 */       this.closedList.clear();
/*  39 */       this.path.clear();
/*  40 */       Runnable run = this::astar;
/*  41 */       (new Thread(run)).start();
/*     */     } 
/*     */   }
/*     */   
/*     */   @EventHandler
/*     */   public void onRender(EventRender3D event) {
/*  47 */     if (this.path.size() > 2) {
/*     */ 
/*     */       
/*  50 */       double x = this.player.lastTickPosX + (this.player.posX - this.player.lastTickPosX) * mc.timer.renderPartialTicks - (mc.getRenderManager()).renderPosX;
/*     */ 
/*     */       
/*  53 */       double y = this.player.lastTickPosY + (this.player.posY - this.player.lastTickPosY) * mc.timer.renderPartialTicks - (mc.getRenderManager()).renderPosY;
/*     */ 
/*     */       
/*  56 */       double z = this.player.lastTickPosZ + (this.player.posZ - this.player.lastTickPosZ) * mc.timer.renderPartialTicks - (mc.getRenderManager()).renderPosZ;
/*     */       
/*  58 */       double playerX = mc.thePlayer.lastTickPosX + (mc.thePlayer.posX - mc.thePlayer.lastTickPosX) * mc.timer.renderPartialTicks - (mc.getRenderManager()).renderPosX;
/*     */       
/*  60 */       double playerY = mc.thePlayer.lastTickPosY + (mc.thePlayer.posY - mc.thePlayer.lastTickPosY) * mc.timer.renderPartialTicks - (mc.getRenderManager()).renderPosY;
/*     */       
/*  62 */       double playerZ = mc.thePlayer.lastTickPosZ + (mc.thePlayer.posZ - mc.thePlayer.lastTickPosZ) * mc.timer.renderPartialTicks - (mc.getRenderManager()).renderPosZ;
/*  63 */       GL11.glPushMatrix();
/*  64 */       GL11.glEnable(3042);
/*  65 */       GL11.glEnable(2848);
/*  66 */       GL11.glDisable(2929);
/*  67 */       GL11.glDisable(3553);
/*  68 */       GL11.glBlendFunc(770, 771);
/*  69 */       GL11.glLineWidth(2.85F);
/*  70 */       RenderUtil.color(Colors.WHITE.c);
/*  71 */       GL11.glLoadIdentity();
/*  72 */       boolean bobbing = mc.gameSettings.viewBobbing;
/*  73 */       mc.gameSettings.viewBobbing = false;
/*  74 */       mc.entityRenderer.orientCamera(mc.timer.renderPartialTicks);
/*  75 */       GL11.glBegin(3);
/*  76 */       GL11.glVertex3d(x, y + this.player.getEyeHeight(), z);
/*  77 */       GL11.glVertex3d(x, y, z);
/*     */       
/*  79 */       for (AStarNode o : this.path) {
/*  80 */         GL11.glVertex3d(o.getX() - (mc.getRenderManager()).renderPosX, this.player.posY - 
/*  81 */             (mc.getRenderManager()).renderPosY, o
/*  82 */             .getZ() - (mc.getRenderManager()).renderPosZ);
/*     */       }
/*     */       
/*  85 */       GL11.glVertex3d(playerX, playerY, playerZ);
/*  86 */       GL11.glEnd();
/*  87 */       mc.gameSettings.viewBobbing = bobbing;
/*  88 */       GL11.glEnable(3553);
/*  89 */       GL11.glEnable(2929);
/*  90 */       GL11.glDisable(2848);
/*  91 */       GL11.glDisable(3042);
/*  92 */       GL11.glPopMatrix();
/*     */     } 
/*     */   }
/*     */   
/*     */   private void astar() {
/*  97 */     this.openList.clear();
/*  98 */     this.closedList.clear();
/*  99 */     this.path.clear();
/* 100 */     double pX = (int)mc.thePlayer.posX;
/* 101 */     double pZ = (int)mc.thePlayer.posZ;
/* 102 */     double eX = (int)this.player.posX;
/* 103 */     double eZ = (int)this.player.posZ;
/* 104 */     AStarNode startNode = new AStarNode(pX, pZ);
/* 105 */     this.openList.add(startNode);
/* 106 */     if (pX != eX || pZ != eZ) {
/* 107 */       long start = System.currentTimeMillis();
/*     */ 
/*     */       
/* 110 */       while (!this.openList.isEmpty()) {
/* 111 */         int nextNode = -1;
/* 112 */         int distance = Integer.MAX_VALUE;
/*     */ 
/*     */         
/* 115 */         int size = this.openList.size();
/* 116 */         for (int node = 0; node < size; node++) {
/* 117 */           AStarNode aStarNode = this.openList.get(node);
/* 118 */           aStarNode.setHeuristic(getHeuristic(eX, eZ, aStarNode.getX(), aStarNode.getZ()));
/* 119 */           if (nextNode == -1 || aStarNode.getHeuristic() < distance) {
/* 120 */             nextNode = node;
/* 121 */             distance = (int)aStarNode.getHeuristic();
/*     */           } 
/*     */         } 
/*     */         
/* 125 */         AStarNode var23 = this.openList.get(nextNode);
/* 126 */         this.closedList.add(var23);
/* 127 */         this.openList.remove(nextNode);
/* 128 */         AStarNode newNode = this.closedList.get(this.closedList.size() - 1);
/* 129 */         AStarNode lastNode = null;
/*     */         
/*     */         double pathPoint;
/* 132 */         label45: for (pathPoint = newNode.getX() - 1.0D; pathPoint <= newNode.getX() + 1.0D; 
/* 133 */           pathPoint++) {
/* 134 */           double z; for (z = newNode.getZ() - 1.0D; z <= newNode.getZ() + 1.0D; z++) {
/* 135 */             if (!isObsctacle(pathPoint, z) && !isInClosedList(pathPoint, z)) {
/* 136 */               AStarNode neighbourNode = new AStarNode(pathPoint, z);
/* 137 */               neighbourNode.setParent(newNode);
/* 138 */               this.openList.add(neighbourNode);
/* 139 */               if (pathPoint == eX && z == eZ) {
/* 140 */                 lastNode = neighbourNode;
/*     */                 
/*     */                 break label45;
/*     */               } 
/*     */             } 
/*     */           } 
/*     */         } 
/* 147 */         if (lastNode != null) {
/* 148 */           AStarNode var24 = lastNode;
/* 149 */           this.path.add(lastNode);
/*     */ 
/*     */           
/* 152 */           while ((var24 = var24.getParent()) != null)
/*     */           {
/*     */ 
/*     */             
/* 156 */             this.path.add(var24);
/*     */           }
/*     */           break;
/*     */         } 
/* 160 */         if (System.currentTimeMillis() - 1000L > start) {
/*     */           break;
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/* 166 */     this.startNextThread = true;
/*     */   }
/*     */   private boolean isInClosedList(double x, double z) {
/*     */     AStarNode node;
/* 170 */     Iterator<AStarNode> var6 = this.closedList.iterator();
/*     */ 
/*     */     
/*     */     do {
/* 174 */       if (!var6.hasNext()) {
/* 175 */         return false;
/*     */       }
/*     */       
/* 178 */       node = var6.next();
/* 179 */     } while (node.getX() != x || node.getZ() != z);
/*     */     
/* 181 */     return true;
/*     */   }
/*     */   
/*     */   private static int getHeuristic(double x1, double z1, double x2, double z2) {
/* 185 */     return (int)(Math.abs(x2 - x1) + Math.abs(z2 - z1));
/*     */   }
/*     */   
/*     */   private boolean isObsctacle(double x, double z) {
/* 189 */     BlockPos pos = new BlockPos(x, (int)this.player.posY, z);
/* 190 */     return (!(mc.theWorld.getBlockState(pos).getBlock() instanceof net.minecraft.block.BlockAir) || (mc.theWorld
/* 191 */       .getBlockState(pos.add(0, -1, 0)).getBlock() instanceof net.minecraft.block.BlockAir && mc.theWorld
/* 192 */       .getBlockState(pos.add(0, -2, 0)).getBlock() instanceof net.minecraft.block.BlockAir));
/*     */   }
/*     */   
/*     */   public void setEntity() {
/* 196 */     EntityPlayer newPlayer = null;
/* 197 */     for (EntityPlayer player : mc.theWorld.playerEntities) {
/* 198 */       if (mc.thePlayer != player && !player.isInvisible() && !player.isDead) {
/* 199 */         if (newPlayer == null) {
/* 200 */           newPlayer = player; continue;
/* 201 */         }  if (mc.thePlayer.getDistanceToEntity((Entity)player) < mc.thePlayer
/* 202 */           .getDistanceToEntity((Entity)newPlayer)) {
/* 203 */           newPlayer = player;
/*     */         }
/*     */       } 
/*     */     } 
/* 207 */     this.player = newPlayer;
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\awareline\main\mod\implement\visual\Path.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */