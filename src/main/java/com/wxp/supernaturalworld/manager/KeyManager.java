package com.wxp.supernaturalworld.manager;

import net.minecraft.client.settings.KeyBinding;
import org.lwjgl.input.Keyboard;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/** @author wxp */
public class KeyManager {
  public static KeyBinding KEY_SHOW_SUPERNATURAL;

  private static List<KeyBinding> keyBindingList;

  public static void initKey() {
    keyBindingList = new ArrayList<>();
    KEY_SHOW_SUPERNATURAL =
        new KeyBinding("key.supernatural", Keyboard.KEY_P, "key.category.supernatural");
    keyBindingList.add(KEY_SHOW_SUPERNATURAL);
  }

  public static Collection<KeyBinding> getInitializedKey() {
    return keyBindingList;
  }
}
