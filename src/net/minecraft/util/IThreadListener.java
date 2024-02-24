package net.minecraft.util;

import com.google.common.util.concurrent.ListenableFuture;

public interface IThreadListener {
  ListenableFuture<Object> addScheduledTask(Runnable paramRunnable);
  
  boolean isCallingFromMinecraftThread();
}


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\minecraf\\util\IThreadListener.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */