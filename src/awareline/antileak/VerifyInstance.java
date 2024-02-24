/*   */ package awareline.antileak;
/*   */ 
/*   */ import awareline.main.Client;
/*   */ import net.minecraft.client.Minecraft;
/*   */ 
/*   */ public interface VerifyInstance
/*   */ {
/* 8 */   public static final Minecraft mc = Minecraft.getMinecraft();
/* 9 */   public static final Client instance = Client.instance;
/*   */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\awareline\antileak\VerifyInstance.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */