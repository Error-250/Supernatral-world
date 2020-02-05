package com.wxp.supernaturalworld.capability.factory;

import com.wxp.supernaturalworld.capability.BindingEntityI;
import com.wxp.supernaturalworld.capability.impl.BindingEntityImpl;

import java.util.concurrent.Callable;

/**
 * @author wxp
 */
public class BindingEntityCapFactory implements Callable<BindingEntityI> {
  @Override
  public BindingEntityI call() throws Exception {
    return new BindingEntityImpl();
  }
}
