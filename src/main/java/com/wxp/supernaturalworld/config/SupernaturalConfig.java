package com.wxp.supernaturalworld.config;

import net.minecraftforge.common.config.Config;

/**
 * @author wxp
 */
@Config(modid = SupernaturalConfig.MOD_ID)
public class SupernaturalConfig {
  @Config.Ignore
  public static final String MOD_ID = "supernatural_world";
  @Config.Ignore
  public static final String NAME = "Supernatural world";
  @Config.Ignore
  public static final String VERSION = "1.0";
  public static SupernaturalPowerConfig supernaturalPowerConfig = new SupernaturalPowerConfig();

  public static class SupernaturalPowerConfig {
    @Config.RangeInt(min = 1)
    @Config.Comment("power from 0 to 10 need powers")
    public int powerBetween0and10 = 10;
    @Config.RangeInt(min = 1)
    @Config.Comment("power from 11 to 20 need powers")
    public int powerBetween11and20 = 100;
    @Config.RangeInt(min = 1)
    @Config.Comment("power from 21 to 30 need powers")
    public int powerBetween21and30 = 200;
    @Config.RangeInt(min = 1)
    @Config.Comment("power from 31 to 40 need powers")
    public int powerBetween31and40 = 500;
    @Config.RangeInt(min = 1)
    @Config.Comment("power from 41 to 50 need powers")
    public int powerBetween41and50 = 800;
    @Config.RangeInt(min = 1)
    @Config.Comment("power from 51 to 60 need powers")
    public int powerBetween51and60 = 1000;
    @Config.RangeInt(min = 1)
    @Config.Comment("power from 61 to 70 need powers")
    public int powerBetween61and70 = 1500;
    @Config.RangeInt(min = 1)
    @Config.Comment("power from 71 to 80 need powers")
    public int powerBetween71and80 = 2000;
    @Config.RangeInt(min = 1)
    @Config.Comment("power from 81 to 90 need powers")
    public int powerBetween81and90 = 5000;
    @Config.RangeInt(min = 1)
    @Config.Comment("power from 91 to 95 need powers")
    public int powerBetween91and95 = 8000;
    @Config.RangeInt(min = 1)
    @Config.Comment("power from 96 to 100 need powers")
    public int powerBetween96and100 = 10000;
  }
}
