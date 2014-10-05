package com.meoyawn.remotelove;

import java.util.List;
import org.jetbrains.annotations.NotNull;

/**
 * Created by adelnizamutdinov on 10/5/14
 */
public class TestApp extends App {
  @NotNull @Override protected List<Object> getModules() {
    List<Object> modules = super.getModules();
    modules.add(new TestModule());
    return modules;
  }
}
