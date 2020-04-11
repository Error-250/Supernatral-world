package com.wxp.supernaturalworld.item;

import com.wxp.supernaturalworld.SupernaturalMod;
import com.wxp.supernaturalworld.capability.BindingEntityI;
import com.wxp.supernaturalworld.manager.CapabilityManager;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.Arrays;
import java.util.List;

/** @author wxp */
public class SupernaturalRingItemImpl extends Item implements SupernaturalRingItemI {
  public static ItemStack tenYearAttackRing;
  private static ItemStack hundredYearAttackRing;
  private static ItemStack thousandYearAttackRing;
  private static ItemStack tenThousandYearAttackRing;
  private static ItemStack hundredThousandYearAttackRing;
  private static ItemStack tenYearDefenceRing;
  private static ItemStack hundredYearDefenceRing;
  private static ItemStack thousandYearDefenceRing;
  private static ItemStack tenThousandYearDefenceRing;
  private static ItemStack hundredThousandYearDefenceRing;
  private static String name = "supernatural_ring";

  public SupernaturalRingItemImpl() {
    setRegistryName(name);
    setHasSubtypes(true);
  }

  @Override
  public String getUnlocalizedName(ItemStack stack) {
    RingLevel level = SupernaturalRingItemImpl.getRingLevel(stack);
    if (level == null) {
      return String.format("item.%s_%s", name, RingLevel.TEN.name());
    }
    return String.format("item.%s_%s", name, level.name());
  }

  public static RingLevel getRingLevel(ItemStack stack) {
    if (0 == stack.getMetadata()) {
      return RingLevel.TEN;
    }
    return RingLevel.valueOf(stack.getMetadata() / 10000 - 1);
  }

  public static int getYears(ItemStack stack) {
    if (stack.getItem() instanceof SupernaturalRingItemImpl) {
      int base = 1;
      RingLevel ringLevel = getRingLevel(stack);
      if (ringLevel == null) {
        return 0;
      }
      switch (ringLevel) {
        case TEN:
          base = 10;
          break;
        case HUNDRED:
          base = 100;
          break;
        case THOUSAND:
          base = 1000;
          break;
        case TEN_THOUSAND:
          base = 10000;
          break;
        case HUNDRED_THOUSAND:
          base = 100000;
          break;
        default:
          base = 1;
      }
      return base * (stack.getMetadata() % 100);
    }
    return 0;
  }

  private static String generateSkillDescription(RingSkill ringSkill) {
    StringBuilder stringBuilder = new StringBuilder("\n");
    TextComponentTranslation typeText =
        new TextComponentTranslation(
            "text.supernatural_ring.skill.type", ringSkill.getRingType().getName());
    stringBuilder.append(String.format("    %s\n", typeText.getUnformattedText()));
    if (ringSkill.getAttack() != null) {
      TextComponentTranslation attackText =
          new TextComponentTranslation(
              "text.supernatural_ring.skill.attack",
              "\u00a7a+" + ringSkill.getAttack() + "\u00a77");
      stringBuilder.append(String.format("    %s\n", attackText.getUnformattedText()));
    }
    if (ringSkill.getAttackDoubleRate() != null) {
      TextComponentTranslation attackDoubleText =
          new TextComponentTranslation(
              "text.supernatural_ring.skill.attack_double",
              "\u00a7a*" + ringSkill.getAttackDoubleRate() + "\u00a77");
      stringBuilder.append(String.format("    %s\n", attackDoubleText.getUnformattedText()));
    }
    if (ringSkill.getDefence() != null) {
      TextComponentTranslation defenceText =
          new TextComponentTranslation(
              "text.supernatural_ring.skill.defence",
              "\u00a7a+" + ringSkill.getDefence() + "\u00a77");
      stringBuilder.append(String.format("    %s\n", defenceText.getUnformattedText()));
    }
    if (ringSkill.getDefenceDoubleRate() != null) {
      TextComponentTranslation defenceDoubleText =
          new TextComponentTranslation(
              "text.supernatural_ring.skill.defence_double",
              "\u00a7a*" + ringSkill.getDefenceDoubleRate() + "\u00a77");
      stringBuilder.append(String.format("    %s\n", defenceDoubleText.getUnformattedText()));
    }
    return stringBuilder.toString();
  }

  public static RingSkill getRingSkill(ItemStack stack) {
    RingSkill ringSkill = new RingSkill();
    if (stack.getMetadata() == tenYearAttackRing.getMetadata()) {
      ringSkill.setSkillType(RingSkill.SkillType.ATTACK_UP);
      ringSkill.setAttack(3f);
      ringSkill.setRingType(RingType.ATTACK);
      ringSkill.setSkillDesc(generateSkillDescription(ringSkill));
    } else if (stack.getMetadata() == hundredYearAttackRing.getMetadata()) {
      ringSkill.setSkillType(RingSkill.SkillType.ATTACK_UP);
      ringSkill.setAttack(5f);
      ringSkill.setRingType(RingType.ATTACK);
      ringSkill.setSkillDesc(generateSkillDescription(ringSkill));
    } else if (stack.getMetadata() == thousandYearAttackRing.getMetadata()) {
      ringSkill.setSkillType(RingSkill.SkillType.ATTACK_DOUBLE);
      ringSkill.setAttackDoubleRate(1.5f);
      ringSkill.setRingType(RingType.ATTACK);
      ringSkill.setSkillDesc(generateSkillDescription(ringSkill));
    } else if (stack.getMetadata() == tenThousandYearAttackRing.getMetadata()) {
      ringSkill.setSkillType(RingSkill.SkillType.ATTACK_DOUBLE);
      ringSkill.setAttackDoubleRate(2f);
      ringSkill.setRingType(RingType.ATTACK);
      ringSkill.setSkillDesc(generateSkillDescription(ringSkill));
    } else if (stack.getMetadata() == hundredThousandYearAttackRing.getMetadata()) {
      ringSkill.setSkillType(RingSkill.SkillType.ATTACK_UP_AND_DOUBLE);
      ringSkill.setAttack(10f);
      ringSkill.setAttackDoubleRate(2.5f);
      ringSkill.setRingType(RingType.ATTACK);
      ringSkill.setSkillDesc(generateSkillDescription(ringSkill));
    } else if (stack.getMetadata() == tenYearDefenceRing.getMetadata()) {
      ringSkill.setSkillType(RingSkill.SkillType.DEFENCE_UP);
      ringSkill.setDefence(1.5f);
      ringSkill.setRingType(RingType.DEFENCE);
      ringSkill.setSkillDesc(generateSkillDescription(ringSkill));
    } else if (stack.getMetadata() == hundredYearDefenceRing.getMetadata()) {
      ringSkill.setSkillType(RingSkill.SkillType.DEFENCE_UP);
      ringSkill.setDefence(2.5f);
      ringSkill.setRingType(RingType.DEFENCE);
      ringSkill.setSkillDesc(generateSkillDescription(ringSkill));
    } else if (stack.getMetadata() == thousandYearDefenceRing.getMetadata()) {
      ringSkill.setSkillType(RingSkill.SkillType.DEFENCE_DOUBLE);
      ringSkill.setDefenceDoubleRate(2f);
      ringSkill.setRingType(RingType.DEFENCE);
      ringSkill.setSkillDesc(generateSkillDescription(ringSkill));
    } else if (stack.getMetadata() == tenThousandYearDefenceRing.getMetadata()) {
      ringSkill.setSkillType(RingSkill.SkillType.DEFENCE_DOUBLE);
      ringSkill.setDefenceDoubleRate(2.5f);
      ringSkill.setRingType(RingType.DEFENCE);
      ringSkill.setSkillDesc(generateSkillDescription(ringSkill));
    } else if (stack.getMetadata() == hundredThousandYearDefenceRing.getMetadata()) {
      ringSkill.setSkillType(RingSkill.SkillType.DEFENCE_UP_AND_DOUBLE);
      ringSkill.setDefence(8f);
      ringSkill.setDefenceDoubleRate(3f);
      ringSkill.setRingType(RingType.DEFENCE);
      ringSkill.setSkillDesc(generateSkillDescription(ringSkill));
    }
    return ringSkill;
  }

  public static RingSkill.SkillType getSkillType(ItemStack itemStack) {
    int meta = itemStack.getMetadata();
    return RingSkill.SkillType.valueOfNumber(meta / 100 % 100);
  }

  @Override
  public void getSubItems(CreativeTabs tab, NonNullList<ItemStack> items) {
    if (this.isInCreativeTab(tab)) {
      initRingItem();

      items.add(tenYearAttackRing);
      items.add(hundredYearAttackRing);

      items.add(tenYearDefenceRing);
      items.add(hundredYearDefenceRing);

      items.add(thousandYearAttackRing);
      items.add(tenThousandYearAttackRing);

      items.add(thousandYearDefenceRing);
      items.add(tenThousandYearDefenceRing);

      items.add(hundredThousandYearAttackRing);
      items.add(hundredThousandYearDefenceRing);
    }
  }

  @Override
  public EnumRarity getRarity(ItemStack stack) {
    RingLevel level = SupernaturalRingItemImpl.getRingLevel(stack);
    if (level == null) {
      return EnumRarity.COMMON;
    }
    switch (level) {
      case THOUSAND:
      case TEN_THOUSAND:
        return EnumRarity.RARE;
      case HUNDRED_THOUSAND:
        return EnumRarity.EPIC;
      case TEN:
      case HUNDRED:
      default:
        return EnumRarity.COMMON;
    }
  }

  @Override
  public void addInformation(
      ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
    super.addInformation(stack, worldIn, tooltip, flagIn);
    TextComponentTranslation rarityDesc =
        new TextComponentTranslation(
            "text.supernatural_ring.rarity." + getRarity(stack).rarityName);
    TextComponentTranslation rarityText =
        new TextComponentTranslation(
            "text.supernatural_ring.rarity",
            getRarity(stack).rarityColor + rarityDesc.getUnformattedText());
    TextComponentTranslation yearsText =
        new TextComponentTranslation(
            "text.supernatural_ring.years", SupernaturalRingItemImpl.getYears(stack));
    BindingEntityI bindingEntityI =
        stack.getCapability(CapabilityManager.bindingEntityICapability, null);
    boolean isBind = bindingEntityI != null && bindingEntityI.getBindingPlayerUuid() != null;
    TextComponentTranslation bindText =
        new TextComponentTranslation("text.supernatural_ring.bind", isBind);
    TextComponentTranslation descText =
        new TextComponentTranslation(
            "text.supernatural_ring.skill.desc", getRingSkill(stack).getSkillDesc());
    tooltip.add(rarityText.getFormattedText());
    tooltip.add(yearsText.getFormattedText());
    tooltip.add(bindText.getFormattedText());
    tooltip.addAll(Arrays.asList(descText.getUnformattedText().split("\n")));
  }

  private void initRingItem() {
    if (tenYearAttackRing == null) {
      tenYearAttackRing = new ItemStack(this, 1, 10001);
    }
    if (hundredYearAttackRing == null) {
      hundredYearAttackRing = new ItemStack(this, 1, 20001);
    }
    if (thousandYearAttackRing == null) {
      thousandYearAttackRing = new ItemStack(this, 1, 30201);
    }
    if (tenThousandYearAttackRing == null) {
      tenThousandYearAttackRing = new ItemStack(this, 1, 40201);
    }
    if (hundredThousandYearAttackRing == null) {
      hundredThousandYearAttackRing = new ItemStack(this, 1, 50401);
    }
    if (tenYearDefenceRing == null) {
      tenYearDefenceRing = new ItemStack(this, 1, 10101);
    }
    if (hundredYearDefenceRing == null) {
      hundredYearDefenceRing = new ItemStack(this, 1, 20101);
    }
    if (thousandYearDefenceRing == null) {
      thousandYearDefenceRing = new ItemStack(this, 1, 30301);
    }
    if (tenThousandYearDefenceRing == null) {
      tenThousandYearDefenceRing = new ItemStack(this, 1, 40301);
    }
    if (hundredThousandYearDefenceRing == null) {
      hundredThousandYearDefenceRing = new ItemStack(this, 1, 50501);
    }
  }
}
