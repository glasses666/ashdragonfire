package com.glasserdraco.ashdragonfire.ability;

import com.glasserdraco.ashdragonfire.AshDragonfireMod;
import com.glasserdraco.ashdragonfire.race.RaceAbility;
import net.minecraftforge.eventbus.api.IEventBus;

import java.util.HashMap;
import java.util.Map;

/**
 * 技能注册器
 * 
 * 管理所有种族技能和能力的注册
 * 提供技能查询和使用接口
 * 
 * 技能分类:
 * - 龙族技能: 火焰吐息、风暴召唤、岩浆喷发、闪电打击
 * - 人类技能: 火炮射击、机甲召唤、飞艇部署
 */
public class AbilityRegistry {
    
    /**
     * 技能注册表
     * Key: 技能ID
     * Value: 技能实例
     */
    private static final Map<String, RaceAbility> ABILITIES = new HashMap<>();
    
    /**
     * 注册技能
     * 
     * @param ability 技能实例
     */
    public static void registerAbility(RaceAbility ability) {
        String id = ability.getAbilityId();
        if (ABILITIES.containsKey(id)) {
            AshDragonfireMod.LOGGER.warn("技能 {} 已存在,将被覆盖", id);
        }
        ABILITIES.put(id, ability);
        AshDragonfireMod.LOGGER.debug("注册技能: {} ({})", ability.getAbilityName(), id);
    }
    
    /**
     * 根据ID获取技能
     * 
     * @param id 技能ID
     * @return 技能实例,如果不存在则返回null
     */
    public static RaceAbility getAbility(String id) {
        return ABILITIES.get(id);
    }
    
    /**
     * 检查技能是否存在
     * 
     * @param id 技能ID
     * @return 是否存在
     */
    public static boolean hasAbility(String id) {
        return ABILITIES.containsKey(id);
    }
    
    /**
     * 获取所有技能
     * 
     * @return 技能映射
     */
    public static Map<String, RaceAbility> getAllAbilities() {
        return new HashMap<>(ABILITIES);
    }
    
    /**
     * 注册方法
     * 在主类中调用,注册所有技能
     * 
     * @param eventBus Forge 事件总线
     */
    public static void register(IEventBus eventBus) {
        AshDragonfireMod.LOGGER.info("正在注册技能系统...");
        
        // TODO: 注册龙族技能
        // registerAbility(new FireBreathAbility());
        // registerAbility(new StormCallAbility());
        // registerAbility(new LavaEruptionAbility());
        // registerAbility(new LightningStrikeAbility());
        
        // TODO: 注册人类技能
        // registerAbility(new CannonFireAbility());
        // registerAbility(new MechSuitAbility());
        // registerAbility(new AirshipDeployAbility());
        
        AshDragonfireMod.LOGGER.info("技能系统注册完成: {} 个技能已注册", ABILITIES.size());
    }
}

