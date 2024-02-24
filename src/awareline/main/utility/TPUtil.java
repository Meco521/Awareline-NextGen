/*     */ package awareline.main.utility;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.Comparator;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.block.material.Material;
/*     */ import net.minecraft.init.Blocks;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.util.Vec3;
/*     */ 
/*     */ 
/*     */ public class TPUtil
/*     */   implements Utils
/*     */ {
/*     */   public static ArrayList<Vec3> computePath(Vec3 from, Vec3 to, double dashDistance, boolean isTeleport) {
/*  16 */     if (!canPassThrow(new BlockPos(from))) {
/*  17 */       from = from.addVector(0.0D, 1.0D, 0.0D);
/*     */     }
/*  19 */     TPUtil$1 pathfinder = new TPUtil$1(from, to);
/*  20 */     pathfinder.compute();
/*     */     
/*  22 */     int i = 0;
/*  23 */     Vec3 lastLoc = null;
/*  24 */     Vec3 lastDashLoc = null;
/*  25 */     ArrayList<Vec3> path = new ArrayList<>();
/*  26 */     ArrayList<Vec3> pathFinderPath = pathfinder.getPath();
/*  27 */     label44: for (Vec3 pathElm : pathFinderPath) {
/*  28 */       if (i == 0 || i == pathFinderPath.size() - 1) {
/*  29 */         if (lastLoc != null) {
/*  30 */           path.add(lastLoc.addVector(0.5D, 0.0D, 0.5D));
/*     */         }
/*  32 */         path.add(pathElm.addVector(0.5D, 0.0D, 0.5D));
/*  33 */         lastDashLoc = pathElm;
/*     */       } else {
/*  35 */         boolean canContinue = true;
/*  36 */         if (pathElm.squareDistanceTo(lastDashLoc) > (isTeleport ? dashDistance : (dashDistance * dashDistance))) {
/*  37 */           canContinue = false;
/*     */         } else {
/*  39 */           double smallX = Math.min(lastDashLoc.xCoord, pathElm.xCoord);
/*  40 */           double smallY = Math.min(lastDashLoc.yCoord, pathElm.yCoord);
/*  41 */           double smallZ = Math.min(lastDashLoc.zCoord, pathElm.zCoord);
/*  42 */           double bigX = Math.max(lastDashLoc.xCoord, pathElm.xCoord);
/*  43 */           double bigY = Math.max(lastDashLoc.yCoord, pathElm.yCoord);
/*  44 */           double bigZ = Math.max(lastDashLoc.zCoord, pathElm.zCoord);
/*     */           
/*  46 */           for (int x = (int)smallX; x <= bigX; x++) {
/*  47 */             for (int y = (int)smallY; y <= bigY; y++) {
/*  48 */               for (int z = (int)smallZ; z <= bigZ; z++) {
/*  49 */                 if (!TPUtil$1.checkPositionValidity(x, y, z)) {
/*  50 */                   canContinue = false;
/*     */                   continue label44;
/*     */                 } 
/*     */               } 
/*     */             } 
/*     */           } 
/*     */         } 
/*  57 */         if (!canContinue) {
/*  58 */           path.add(lastLoc.addVector(0.5D, 0.0D, 0.5D));
/*  59 */           lastDashLoc = lastLoc;
/*     */         } 
/*     */       } 
/*  62 */       lastLoc = pathElm;
/*  63 */       i++;
/*     */     } 
/*  65 */     return path;
/*     */   }
/*     */   
/*     */   private static boolean canPassThrow(BlockPos pos) {
/*  69 */     Block block = mc.theWorld.getBlockState(new BlockPos(pos.getX(), pos.getY(), pos.getZ())).getBlock();
/*  70 */     return (block.getMaterial() != Material.air && block.getMaterial() != Material.plants && block.getMaterial() != Material.vine && block != Blocks.ladder && block != Blocks.water && block != Blocks.flowing_water && block != Blocks.wall_sign && block != Blocks.standing_sign);
/*     */   }
/*     */   
/*     */   public static class TPUtil$1 {
/*     */     private final Vec3 startVec3;
/*     */     private final Vec3 endVec3;
/*  76 */     private ArrayList<Vec3> path = new ArrayList<>(); public ArrayList<Vec3> getPath() { return this.path; }
/*     */     
/*  78 */     private final ArrayList<Hub> hubs = new ArrayList<>();
/*  79 */     private final ArrayList<Hub> hubsToWork = new ArrayList<>();
/*     */     
/*  81 */     private static final Vec3[] flatCardinalDirections = new Vec3[] { new Vec3(1.0D, 0.0D, 0.0D), new Vec3(-1.0D, 0.0D, 0.0D), new Vec3(0.0D, 0.0D, 1.0D), new Vec3(0.0D, 0.0D, -1.0D) };
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     TPUtil$1(Vec3 startVec3, Vec3 endVec3) {
/*  89 */       this.startVec3 = startVec3.addVector(0.0D, 0.0D, 0.0D);
/*  90 */       this.endVec3 = endVec3.addVector(0.0D, 0.0D, 0.0D);
/*     */     }
/*     */     
/*     */     void compute() {
/*  94 */       this.path.clear();
/*  95 */       this.hubsToWork.clear();
/*  96 */       ArrayList<Vec3> initPath = new ArrayList<>();
/*  97 */       initPath.add(this.startVec3);
/*  98 */       this.hubsToWork.add(new Hub(this.startVec3, null, initPath, this.startVec3.squareDistanceTo(this.endVec3), 0.0D, 0.0D));
/*     */       int i;
/* 100 */       label37: for (i = 0; i < 1000; i++) {
/* 101 */         this.hubsToWork.sort(new CompareHub());
/* 102 */         int j = 0;
/* 103 */         if (this.hubsToWork.isEmpty()) {
/*     */           break;
/*     */         }
/* 106 */         for (Hub hub : new ArrayList(this.hubsToWork)) {
/* 107 */           j++;
/* 108 */           if (j > 4) {
/*     */             break;
/*     */           }
/* 111 */           this.hubsToWork.remove(hub);
/* 112 */           this.hubs.add(hub);
/*     */           
/* 114 */           for (Vec3 direction : flatCardinalDirections) {
/* 115 */             Vec3 loc = hub.getLoc().add(direction);
/* 116 */             if (checkPositionValidity(loc) && 
/* 117 */               addHub(hub, loc)) {
/*     */               break label37;
/*     */             }
/*     */           } 
/*     */ 
/*     */           
/* 123 */           Vec3 loc1 = hub.getLoc().addVector(0.0D, 1.0D, 0.0D);
/* 124 */           if (checkPositionValidity(loc1) && 
/* 125 */             addHub(hub, loc1)) {
/*     */             break label37;
/*     */           }
/*     */ 
/*     */           
/* 130 */           Vec3 loc2 = hub.getLoc().addVector(0.0D, -1.0D, 0.0D);
/* 131 */           if (checkPositionValidity(loc2) && 
/* 132 */             addHub(hub, loc2)) {
/*     */             break label37;
/*     */           }
/*     */         } 
/*     */       } 
/*     */ 
/*     */       
/* 139 */       this.hubs.sort(new CompareHub());
/* 140 */       this.path = ((Hub)this.hubs.get(0)).getPath();
/*     */     }
/*     */     
/*     */     static boolean checkPositionValidity(Vec3 loc) {
/* 144 */       return checkPositionValidity((int)loc.xCoord, (int)loc.yCoord, (int)loc.zCoord);
/*     */     }
/*     */     
/*     */     static boolean checkPositionValidity(int x, int y, int z) {
/* 148 */       BlockPos block1 = new BlockPos(x, y, z);
/* 149 */       BlockPos block2 = new BlockPos(x, y + 1, z);
/* 150 */       BlockPos block3 = new BlockPos(x, y - 1, z);
/* 151 */       return (isNotBlockSolid(block1) && isNotBlockSolid(block2) && isSafeToWalkOn(block3));
/*     */     }
/*     */     
/*     */     private static boolean isNotBlockSolid(BlockPos block) {
/* 155 */       return (!Utils.mc.theWorld.getBlockState(new BlockPos(block.getX(), block.getY(), block.getZ())).getBlock().isSolidFullCube() && !(Utils.mc.theWorld.getBlockState(new BlockPos(block.getX(), block.getY(), block.getZ())).getBlock() instanceof net.minecraft.block.BlockSlab) && !(Utils.mc.theWorld.getBlockState(new BlockPos(block.getX(), block.getY(), block.getZ())).getBlock() instanceof net.minecraft.block.BlockStairs) && !(Utils.mc.theWorld.getBlockState(new BlockPos(block.getX(), block.getY(), block.getZ())).getBlock() instanceof net.minecraft.block.BlockCactus) && !(Utils.mc.theWorld.getBlockState(new BlockPos(block.getX(), block.getY(), block.getZ())).getBlock() instanceof net.minecraft.block.BlockChest) && !(Utils.mc.theWorld.getBlockState(new BlockPos(block.getX(), block.getY(), block.getZ())).getBlock() instanceof net.minecraft.block.BlockEnderChest) && !(Utils.mc.theWorld.getBlockState(new BlockPos(block.getX(), block.getY(), block.getZ())).getBlock() instanceof net.minecraft.block.BlockSkull) && !(Utils.mc.theWorld.getBlockState(new BlockPos(block.getX(), block.getY(), block.getZ())).getBlock() instanceof net.minecraft.block.BlockPane) && !(Utils.mc.theWorld.getBlockState(new BlockPos(block.getX(), block.getY(), block.getZ())).getBlock() instanceof net.minecraft.block.BlockFence) && !(Utils.mc.theWorld.getBlockState(new BlockPos(block.getX(), block.getY(), block.getZ())).getBlock() instanceof net.minecraft.block.BlockWall) && !(Utils.mc.theWorld.getBlockState(new BlockPos(block.getX(), block.getY(), block.getZ())).getBlock() instanceof net.minecraft.block.BlockGlass) && !(Utils.mc.theWorld.getBlockState(new BlockPos(block.getX(), block.getY(), block.getZ())).getBlock() instanceof net.minecraft.block.BlockPistonBase) && !(Utils.mc.theWorld.getBlockState(new BlockPos(block.getX(), block.getY(), block.getZ())).getBlock() instanceof net.minecraft.block.BlockPistonExtension) && !(Utils.mc.theWorld.getBlockState(new BlockPos(block.getX(), block.getY(), block.getZ())).getBlock() instanceof net.minecraft.block.BlockPistonMoving) && !(Utils.mc.theWorld.getBlockState(new BlockPos(block.getX(), block.getY(), block.getZ())).getBlock() instanceof net.minecraft.block.BlockStainedGlass) && !(Utils.mc.theWorld.getBlockState(new BlockPos(block.getX(), block.getY(), block.getZ())).getBlock() instanceof net.minecraft.block.BlockTrapDoor));
/*     */     }
/*     */     
/*     */     private static boolean isSafeToWalkOn(BlockPos block) {
/* 159 */       return (!(Utils.mc.theWorld.getBlockState(new BlockPos(block.getX(), block.getY(), block.getZ())).getBlock() instanceof net.minecraft.block.BlockFence) && 
/* 160 */         !(Utils.mc.theWorld.getBlockState(new BlockPos(block.getX(), block.getY(), block.getZ())).getBlock() instanceof net.minecraft.block.BlockWall));
/*     */     }
/*     */     
/*     */     Hub isHubExisting(Vec3 loc) {
/* 164 */       for (Hub hub : this.hubs) {
/* 165 */         if ((hub.getLoc()).xCoord == loc.xCoord && (hub.getLoc()).yCoord == loc.yCoord && (hub.getLoc()).zCoord == loc.zCoord) {
/* 166 */           return hub;
/*     */         }
/*     */       } 
/* 169 */       for (Hub hub : this.hubsToWork) {
/* 170 */         if ((hub.getLoc()).xCoord == loc.xCoord && (hub.getLoc()).yCoord == loc.yCoord && (hub.getLoc()).zCoord == loc.zCoord) {
/* 171 */           return hub;
/*     */         }
/*     */       } 
/* 174 */       return null;
/*     */     }
/*     */     
/*     */     boolean addHub(Hub parent, Vec3 loc) {
/* 178 */       Hub existingHub = isHubExisting(loc);
/* 179 */       double totalCost = 0.0D;
/* 180 */       if (parent != null) {
/* 181 */         totalCost += parent.getTotalCost();
/*     */       }
/* 183 */       assert parent != null;
/* 184 */       if (existingHub == null) {
/* 185 */         double minDistanceSquared = 9.0D;
/* 186 */         if ((loc.xCoord == this.endVec3.xCoord && loc.yCoord == this.endVec3.yCoord && loc.zCoord == this.endVec3.zCoord) || loc.squareDistanceTo(this.endVec3) <= minDistanceSquared) {
/* 187 */           this.path.clear();
/* 188 */           this.path = parent.getPath();
/* 189 */           this.path.add(loc);
/* 190 */           return true;
/*     */         } 
/* 192 */         ArrayList<Vec3> path = new ArrayList<>(parent.getPath());
/* 193 */         path.add(loc);
/* 194 */         this.hubsToWork.add(new Hub(loc, parent, path, loc.squareDistanceTo(this.endVec3), 0.0D, totalCost));
/*     */       }
/* 196 */       else if (existingHub.getCost() > 0.0D) {
/* 197 */         ArrayList<Vec3> path = new ArrayList<>(parent.getPath());
/* 198 */         path.add(loc);
/* 199 */         existingHub.setLoc(loc);
/* 200 */         existingHub.setParent(parent);
/* 201 */         existingHub.setPath(path);
/* 202 */         existingHub.setSquareDistanceToFromTarget(loc.squareDistanceTo(this.endVec3));
/* 203 */         existingHub.setCost(0.0D);
/* 204 */         existingHub.setTotalCost(totalCost);
/*     */       } 
/* 206 */       return false;
/*     */     }
/*     */     private static class Hub { private Vec3 loc; private Hub parent; private ArrayList<Vec3> path;
/*     */       public Vec3 getLoc() {
/* 210 */         return this.loc;
/*     */       } private double squareDistanceToFromTarget; private double cost; private double totalCost; public Hub getParent() {
/* 212 */         return this.parent;
/*     */       } public ArrayList<Vec3> getPath() {
/* 214 */         return this.path;
/*     */       }
/*     */       public double getCost() {
/* 217 */         return this.cost;
/*     */       }
/*     */ 
/*     */       
/*     */       Hub(Vec3 loc, Hub parent, ArrayList<Vec3> path, double squareDistanceToFromTarget, double cost, double totalCost) {
/* 222 */         this.loc = loc;
/* 223 */         this.parent = parent;
/* 224 */         this.path = path;
/* 225 */         this.squareDistanceToFromTarget = squareDistanceToFromTarget;
/* 226 */         this.cost = cost;
/* 227 */         this.totalCost = totalCost;
/*     */       }
/*     */       
/*     */       double getSquareDistanceToFromTarget() {
/* 231 */         return this.squareDistanceToFromTarget;
/*     */       }
/*     */       
/*     */       public void setLoc(Vec3 loc) {
/* 235 */         this.loc = loc;
/*     */       }
/*     */       
/*     */       public void setParent(Hub parent) {
/* 239 */         this.parent = parent;
/*     */       }
/*     */       
/*     */       public void setPath(ArrayList<Vec3> path) {
/* 243 */         this.path = path;
/*     */       }
/*     */       
/*     */       void setSquareDistanceToFromTarget(double squareDistanceToFromTarget) {
/* 247 */         this.squareDistanceToFromTarget = squareDistanceToFromTarget;
/*     */       }
/*     */       
/*     */       public void setCost(double cost) {
/* 251 */         this.cost = cost;
/*     */       }
/*     */       
/*     */       double getTotalCost() {
/* 255 */         return this.totalCost;
/*     */       }
/*     */       
/*     */       void setTotalCost(double totalCost) {
/* 259 */         this.totalCost = totalCost;
/*     */       } }
/*     */ 
/*     */     
/*     */     public static class CompareHub
/*     */       implements Comparator<Hub> {
/*     */       public int compare(TPUtil.TPUtil$1.Hub o1, TPUtil.TPUtil$1.Hub o2) {
/* 266 */         return (int)(o1.getSquareDistanceToFromTarget() + o1.getTotalCost() - o2.getSquareDistanceToFromTarget() + o2.getTotalCost());
/*     */       }
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\awareline\mai\\utility\TPUtil.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */