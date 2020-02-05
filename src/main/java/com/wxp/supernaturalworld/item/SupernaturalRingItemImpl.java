package com.wxp.supernaturalworld.item;

import com.wxp.supernaturalworld.SupernaturalMod;
import com.wxp.supernaturalworld.capability.BindingEntityI;
import com.wxp.supernaturalworld.manager.CapabilityManager;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.NonNullList;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.List;

/** @author wxp */
public class SupernaturalRingItemImpl extends Item implements SupernaturalRingItemI {
  private String name = "supernatural_ring";

  public SupernaturalRingItemImpl() {
    this(10);
  }

  public SupernaturalRingItemImpl(int year) {
    setUnlocalizedName(name);
    setRegistryName(name);
    setHasSubtypes(true);
  }

  @Override
  public String getUnlocalizedName(ItemStack stack) {
    RingLevel level = SupernaturalRingItemImpl.getRingLevel(stack);
    if (level == null) {
      return super.getUnlocalizedName();
    }
    return super.getUnlocalizedName() + "_" + level.name();
  }

  public static RingLevel getRingLevel(ItemStack stack) {
    if (stack.getItem() instanceof SupernaturalRingItemImpl) {
      int meta = stack.getMetadata() / 100;
      int levelAlias = meta / 10;
      return RingLevel.valueOf(levelAlias - 1);
    }
    return null;
  }

  public static int getYears(ItemStack stack) {
    if (stack.getItem() instanceof SupernaturalRingItemImpl) {
      int meta = stack.getMetadata() / 100;
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
      return base * (meta % 10);
    }
    return 0;
  }

  private static float calculateRingAttack(ItemStack stack, RingType type) {
    RingLevel level = getRingLevel(stack);
    float base = 0;
    if (level != null) {
      switch (level) {
        case TEN:
          base = 1;
          break;
        case HUNDRED:
          base = 1.5f;
          break;
        case THOUSAND:
          base = 2;
          break;
        case TEN_THOUSAND:
          base = 2.5f;
          break;
        case HUNDRED_THOUSAND:
          base = 3;
          break;
        default:
          base = 0;
      }
    }
    int yearMeta = stack.getMetadata() / 100;
    float attachAttack = base * (yearMeta % 10);
    switch (type) {
      case ATTACK:
        attachAttack = attachAttack + base;
        break;
      case SPEED:
        attachAttack -= base * 0.5f;
        break;
      case DEFENCE:
        return 0;
      default:
    }
    return attachAttack;
  }

  private static float calculateRingDefence(ItemStack stack, RingType ringType) {
    RingLevel level = getRingLevel(stack);
    float base = 0;
    if (level != null) {
      switch (level) {
        case TEN:
          base = 0.5f;
          break;
        case HUNDRED:
          base = 1f;
          break;
        case THOUSAND:
          base = 1.5f;
          break;
        case TEN_THOUSAND:
          base = 2f;
          break;
        case HUNDRED_THOUSAND:
          base = 2.5f;
          break;
        default:
          base = 0;
      }
    }
    int yearMeta = stack.getMetadata() / 100;
    float attachDefence = base * (yearMeta % 10);
    switch (ringType) {
      case SPEED:
        attachDefence -= base * 0.5f;
        break;
      case DEFENCE:
        attachDefence += base * yearMeta;
      case ATTACK:
      default:
    }
    return attachDefence;
  }

  private static String generateSkillDescription(RingSkill ringSkill) {
    TextComponentTranslation typeText =
        new TextComponentTranslation(
            "text.supernatural_ring.skill.type", ringSkill.getRingType().getName());
    TextComponentTranslation attackText =
        new TextComponentTranslation(
            "text.supernatural_ring.skill.attack",
            ringSkill.getAttack() > 0
                ? "\u00a7r\u00a7a+" + ringSkill.getAttack() + "\u00a7r\u00a77"
                : ringSkill.getAttack() < 0
                    ? "\u00a7r\u00a74-" + ringSkill.getAttack() + "\u00a7r\u00a77"
                    : ringSkill.getAttack());
    TextComponentTranslation defenceText =
        new TextComponentTranslation(
            "text.supernatural_ring.skill.defence",
            ringSkill.getDefence() > 0
                ? "\u00a7r\u00a7a+" + ringSkill.getDefence() + "\u00a7r"
                : ringSkill.getDefence() < 0
                    ? "\u00a7r\u00a74+" + ringSkill.getDefence() + "\u00a7r"
                    : ringSkill.getDefence());
    return "\n    "
        + typeText.getUnformattedText()
        + "\n    "
        + attackText.getUnformattedText()
        + "\n    "
        + defenceText.getUnformattedText();
  }

  public static RingSkill getRingSkill(ItemStack stack) {
    int skillMeta = stack.getMetadata() % 100;
    RingType type = RingType.valueOf(skillMeta);
    RingSkill ringSkill = new RingSkill();
    ringSkill.setRingType(type);
    ringSkill.setAttack(calculateRingAttack(stack, type));
    ringSkill.setDefence(calculateRingDefence(stack, type));
    ringSkill.setSkillDesc(generateSkillDescription(ringSkill));
    return ringSkill;
  }

  @Override
  public void getSubItems(CreativeTabs tab, NonNullList<ItemStack> items) {
    if (this.isInCreativeTab(tab)) {
      for (RingLevel ringLevel : RingLevel.values()) {
        int base = 0;
        switch (ringLevel) {
          case TEN:
            base = 10;
            break;
          case HUNDRED:
            base = 20;
            break;
          case THOUSAND:
            base = 30;
            break;
          case TEN_THOUSAND:
            base = 40;
            break;
          case HUNDRED_THOUSAND:
            base = 50;
            break;
          default:
            base = 0;
        }
        for (int i = 1; i <= 9; i++) {
          for (RingType ringType : RingType.values()) {
            int meta = (base + i) * 100 + ringType.ordinal();
            items.add(new ItemStack(this, 1, meta));
          }
        }
      }
    }
  }

  @Override
  public ActionResult<ItemStack> onItemRightClick(
      World worldIn, EntityPlayer playerIn, EnumHand handIn) {
    ItemStack itemStack = playerIn.getHeldItemMainhand();
    SupernaturalMod.logger.info(
        "Year:{} level:{}",
        SupernaturalRingItemImpl.getYears(itemStack),
        SupernaturalRingItemImpl.getRingLevel(itemStack));
    if (itemStack.hasCapability(CapabilityManager.bindingEntityICapability, null)) {
      BindingEntityI bindingEntity =
          itemStack.getCapability(CapabilityManager.bindingEntityICapability, null);
      if (bindingEntity != null) {
        if (bindingEntity.getBindingPlayerUuid() == null) {
          SupernaturalMod.logger.info("Not bind user");
        } else {
          SupernaturalMod.logger.info(
              "bindUser:{}", worldIn.getPlayerEntityByUUID(bindingEntity.getBindingPlayerUuid()));
        }
      }
    }
    return super.onItemRightClick(worldIn, playerIn, handIn);
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
    tooltip.add(descText.getUnformattedText());
  }
}
