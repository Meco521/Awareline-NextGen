package com.jprocesses.info;

import com.jprocesses.model.JProcessesResponse;
import com.jprocesses.model.ProcessInfo;
import java.util.List;

public interface ProcessesService {
  List<ProcessInfo> getList();
  
  List<ProcessInfo> getList(boolean paramBoolean);
  
  List<ProcessInfo> getList(String paramString);
  
  List<ProcessInfo> getList(String paramString, boolean paramBoolean);
  
  ProcessInfo getProcess(int paramInt);
  
  ProcessInfo getProcess(int paramInt, boolean paramBoolean);
  
  JProcessesResponse killProcess(int paramInt);
  
  JProcessesResponse killProcessGracefully(int paramInt);
  
  JProcessesResponse changePriority(int paramInt1, int paramInt2);
}


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\com\jprocesses\info\ProcessesService.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */