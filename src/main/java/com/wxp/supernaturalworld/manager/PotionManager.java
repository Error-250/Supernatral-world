package com.wxp.supernaturalworld.manager;

import com.wxp.supernaturalworld.potion.PotionNotHungry;
import net.minecraft.potion.Potion;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/** @author wxp */
public class PotionManager {
  public static PotionNotHungry potionNotHungry = new PotionNotHungry();

  private static List<Potion> potions = new ArrayList<>();

  public static void initPotion() {
    potions.add(potionNotHungry);
  }

  public static Collection<Potion> getInitializedPotion() {
    return potions;
  }
}
