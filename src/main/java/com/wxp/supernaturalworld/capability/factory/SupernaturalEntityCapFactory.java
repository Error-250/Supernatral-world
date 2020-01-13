package com.wxp.supernaturalworld.capability.factory;

import com.wxp.supernaturalworld.capability.SupernaturalEntityI;
import com.wxp.supernaturalworld.capability.impl.SupernaturalEntityImpl;

import java.util.concurrent.Callable;

/**
 * @author wxp
 */
public class SupernaturalEntityCapFactory implements Callable<SupernaturalEntityI> {
  @Override
  public SupernaturalEntityI call() throws Exception {
    return new SupernaturalEntityImpl();
  }
}
