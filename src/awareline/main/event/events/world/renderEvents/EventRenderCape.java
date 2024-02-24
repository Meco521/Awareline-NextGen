/*    */ package awareline.main.event.events.world.renderEvents;
/*    */ import awareline.main.event.Event;
/*    */ import net.minecraft.entity.player.EntityPlayer;
/*    */ import net.minecraft.util.ResourceLocation;
/*    */ 
/*    */ public class EventRenderCape extends Event {
/*    */   private ResourceLocation capeLocation;
/*    */   
/*    */   public EntityPlayer getPlayer() {
/* 10 */     return this.player;
/*    */   }
/*    */   private final EntityPlayer player;
/*    */   public EventRenderCape(ResourceLocation capeLocation, EntityPlayer player) {
/* 14 */     this.capeLocation = capeLocation;
/* 15 */     this.player = player;
/*    */   }
/*    */   
/*    */   public ResourceLocation getLocation() {
/* 19 */     return this.capeLocation;
/*    */   }
/*    */   
/*    */   public void setLocation(ResourceLocation location) {
/* 23 */     this.capeLocation = location;
/*    */   }
/*    */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\awareline\main\event\events\world\renderEvents\EventRenderCape.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */