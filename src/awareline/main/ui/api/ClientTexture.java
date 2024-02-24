package awareline.main.ui.api;

public interface ClientTexture<T> {
  T load();
  
  T bind();
  
  T draw(double paramDouble1, double paramDouble2, double paramDouble3, double paramDouble4);
  
  T draw(double paramDouble1, double paramDouble2, double paramDouble3, double paramDouble4, int paramInt);
}


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\awareline\mai\\ui\api\ClientTexture.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */