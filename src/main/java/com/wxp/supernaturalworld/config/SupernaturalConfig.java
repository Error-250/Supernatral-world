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
  public static SupernaturalRingConfig supernaturalRingConfig = new SupernaturalRingConfig();
  public static SupernaturalEntityConfig supernaturalEntityConfig = new SupernaturalEntityConfig();

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

    @Config.RangeDouble(min = 0, max = 0.5)
    @Config.Comment("attack up between 0 and 20 per power level")
    public Double attackUpBetween0And20PerPowerLevel = 0.1;

    @Config.RangeDouble(min = 0, max = 0.5)
    @Config.Comment("attack up between 20 and 40 per power level")
    public Double attackUpBetween20And40PerPowerLevel = 0.2;

    @Config.RangeDouble(min = 0, max = 0.5)
    @Config.Comment("attack up between 40 and 70 per power level")
    public Double attackUpBetween40And70PerPowerLevel = 0.3;

    @Config.RangeDouble(min = 0, max = 0.5)
    @Config.Comment("attack up between 70 and 90 per power level")
    public Double attackUpBetween70And90PerPowerLevel = 0.4;

    @Config.RangeDouble(min = 0, max = 0.5)
    @Config.Comment("attack up between 90 and 100 per power level")
    public Double attackUpBetween90And100PerPowerLevel = 0.5;

    @Config.RangeDouble(min = 0)
    @Config.Comment("defence up per power level")
    public Double defenceUpPerPowerLevel = 0.25;

    @Config.RangeDouble(min = 0, max = 0.1)
    @Config.Comment("defence up per year for monster")
    public Double monsterDefenceUpPerYear = 0.005;
  }

  public static class SupernaturalRingConfig {
    @Config.RangeInt(min = 0, max = 99)
    @Config.Comment("min scale for supernatural ring drop.")
    public int minSupernaturalRingDropScale = 48;

    @Config.RangeInt(min = 0, max = 99)
    @Config.Comment("min scale for supernatural ring drop.")
    public int maxSupernaturalRingDropScale = 53;
  }

  public static class SupernaturalEntityConfig {
    @Config.RangeInt(min = 20)
    @Config.Comment("the base health for ten year monster.")
    public int tenYearMonsterBaseHealth = 2;

    @Config.RangeInt(min = 20)
    @Config.Comment("the base health for hundred year monster.")
    public int hundredYearMonsterBaseHealth = 4;

    @Config.RangeInt(min = 20)
    @Config.Comment("the base health for thousand year monster.")
    public int thousandYearMonsterBaseHealth = 8;

    @Config.RangeInt(min = 20)
    @Config.Comment("the base health for ten-thousand year monster.")
    public int tenThousandYearMonsterBaseHealth = 16;

    @Config.RangeInt(min = 20)
    @Config.Comment("the base health for hundred-thousand year monster.")
    public int hundredThousandYearMonsterBaseHealth = 32;

    @Config.RangeDouble(min = 0.1, max = 1)
    @Config.Comment("the rate for monster drop ring.")
    public double monsterDropRingRate = 0.5;
  }
}
