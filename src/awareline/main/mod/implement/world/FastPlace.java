/*    */ package awareline.main.mod.implement.world;
/*    */ 
/*    */ import awareline.main.event.EventHandler;
/*    */ import awareline.main.event.events.world.EventTick;
/*    */ import awareline.main.mod.Module;
/*    */ import awareline.main.mod.ModuleType;
/*    */ 
/*    */ public class FastPlace
/*    */   extends Module {
/*    */   private boolean shit;
/*    */   
/*    */   public FastPlace() {
/* 13 */     super("FastPlace", ModuleType.World);
/*    */   }
/*    */   
/*    */   @EventHandler
/*    */   public void onTick(EventTick event) {
/*    */     // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: pop
/*    */     //   2: getstatic awareline/main/mod/implement/world/FastPlace.mc : Lnet/minecraft/client/Minecraft;
/*    */     //   5: invokevirtual getRightClickDelayTimer : ()I
/*    */     //   8: iconst_4
/*    */     //   9: if_icmplt -> 31
/*    */     //   12: aload_0
/*    */     //   13: getfield shit : Z
/*    */     //   16: ifeq -> 56
/*    */     //   19: aload_0
/*    */     //   20: pop
/*    */     //   21: getstatic awareline/main/mod/implement/world/FastPlace.mc : Lnet/minecraft/client/Minecraft;
/*    */     //   24: invokevirtual getRightClickDelayTimer : ()I
/*    */     //   27: iconst_5
/*    */     //   28: if_icmpge -> 56
/*    */     //   31: aload_0
/*    */     //   32: pop
/*    */     //   33: getstatic awareline/main/mod/implement/world/FastPlace.mc : Lnet/minecraft/client/Minecraft;
/*    */     //   36: iconst_0
/*    */     //   37: invokevirtual setRightClickDelayTimer : (I)V
/*    */     //   40: aload_0
/*    */     //   41: aload_0
/*    */     //   42: getfield shit : Z
/*    */     //   45: ifne -> 52
/*    */     //   48: iconst_1
/*    */     //   49: goto -> 53
/*    */     //   52: iconst_0
/*    */     //   53: putfield shit : Z
/*    */     //   56: return
/*    */     // Line number table:
/*    */     //   Java source line number -> byte code offset
/*    */     //   #18	-> 0
/*    */     //   #19	-> 31
/*    */     //   #20	-> 40
/*    */     //   #22	-> 56
/*    */     // Local variable table:
/*    */     //   start	length	slot	name	descriptor
/*    */     //   0	57	0	this	Lawareline/main/mod/implement/world/FastPlace;
/*    */     //   0	57	1	event	Lawareline/main/event/events/world/EventTick;
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\awareline\main\mod\implement\world\FastPlace.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */