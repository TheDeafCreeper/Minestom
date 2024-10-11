package com.thedeafcreeper

import net.kyori.adventure.sound.Sound
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer
import net.minestom.server.MinecraftServer
import net.minestom.server.coordinate.Pos
import net.minestom.server.entity.Entity
import net.minestom.server.entity.EntityCreature
import net.minestom.server.entity.EntityType
import net.minestom.server.entity.GameMode
import net.minestom.server.entity.LivingEntity
import net.minestom.server.entity.Player
import net.minestom.server.entity.damage.Damage
import net.minestom.server.event.entity.EntityAttackEvent
import net.minestom.server.event.player.AsyncPlayerConfigurationEvent
import net.minestom.server.event.player.PlayerBlockInteractEvent
import net.minestom.server.event.player.PlayerEntityInteractEvent
import net.minestom.server.event.player.PlayerUseItemEvent
import net.minestom.server.event.player.PlayerUseItemOnBlockEvent
import net.minestom.server.instance.Instance
import net.minestom.server.instance.LightingChunk
import net.minestom.server.instance.block.Block
import net.minestom.server.item.Material
import net.minestom.server.sound.SoundEvent


fun main() {
    println("Hello World!")

    val server = MinecraftServer.init()

    // Create Instance

    val instanceManager = MinecraftServer.getInstanceManager()
    val instanceContainer = instanceManager.createInstanceContainer()

    // Generate World
    instanceContainer.setGenerator { unit ->
        if (unit.absoluteStart().chunkZ() % 2 == 0) {
            if (unit.absoluteStart().chunkX() % 2 == 0)
                unit.modifier().fillHeight(0, 40, Block.WHITE_CONCRETE)
            else unit.modifier().fillHeight(0, 40, Block.BLACK_CONCRETE)
        } else {
            if (unit.absoluteStart().chunkX() % 2 == 0)
                unit.modifier().fillHeight(0, 40, Block.BLACK_CONCRETE)
            else unit.modifier().fillHeight(0, 40, Block.WHITE_CONCRETE)
        }
    }

    // Add Lighting
    instanceContainer.setChunkSupplier(::LightingChunk);

    // Add an event callback to specify the spawning instance (and the spawn position)
    val globalEventHandler = MinecraftServer.getGlobalEventHandler()
    globalEventHandler.addListener(
        AsyncPlayerConfigurationEvent::class.java
    ) { event: AsyncPlayerConfigurationEvent ->
        val player: Player = event.player
        event.spawningInstance = instanceContainer
        player.respawnPoint = Pos(0.0, 42.0, 0.0)
        player.gameMode = GameMode.CREATIVE
        println("${PlainTextComponentSerializer.plainText().serialize(player.name)} has joined the game!")
    }

    globalEventHandler.addListener(
        PlayerUseItemOnBlockEvent::class.java
    ) { event: PlayerUseItemOnBlockEvent ->
        val entity = when (event.itemStack.material()) {
            Material.ALLAY_SPAWN_EGG -> createEntity(EntityType.ALLAY)
            Material.ARMADILLO_SPAWN_EGG -> createEntity(EntityType.ARMADILLO)
            Material.AXOLOTL_SPAWN_EGG -> createEntity(EntityType.AXOLOTL)
            Material.BAT_SPAWN_EGG -> createEntity(EntityType.BAT)
            Material.BEE_SPAWN_EGG -> createEntity(EntityType.BEE)
            Material.BLAZE_SPAWN_EGG -> createEntity(EntityType.BLAZE)
            Material.BOGGED_SPAWN_EGG -> createEntity(EntityType.BOGGED)
            Material.BREEZE_SPAWN_EGG -> createEntity(EntityType.BREEZE)
            Material.CAMEL_SPAWN_EGG -> createEntity(EntityType.CAMEL)
            Material.CAT_SPAWN_EGG -> createEntity(EntityType.CAT)
            Material.CAVE_SPIDER_SPAWN_EGG -> createEntity(EntityType.CAVE_SPIDER)
            Material.CHICKEN_SPAWN_EGG -> createEntity(EntityType.CHICKEN)
            Material.COD_SPAWN_EGG -> createEntity(EntityType.COD)
            Material.COW_SPAWN_EGG -> createEntity(EntityType.COW)
            Material.CREEPER_SPAWN_EGG -> createEntity(EntityType.CREEPER)
            Material.DOLPHIN_SPAWN_EGG -> createEntity(EntityType.DOLPHIN)
            Material.DONKEY_SPAWN_EGG -> createEntity(EntityType.DONKEY)
            Material.DROWNED_SPAWN_EGG -> createEntity(EntityType.DROWNED)
            Material.ELDER_GUARDIAN_SPAWN_EGG -> createEntity(EntityType.ELDER_GUARDIAN)
            Material.ENDERMAN_SPAWN_EGG -> createEntity(EntityType.ENDERMAN)
            Material.ENDERMITE_SPAWN_EGG -> createEntity(EntityType.ENDERMITE)
            Material.EVOKER_SPAWN_EGG -> createEntity(EntityType.EVOKER)
            Material.FOX_SPAWN_EGG -> createEntity(EntityType.FOX )
            Material.FROG_SPAWN_EGG -> createEntity(EntityType.FROG)
            Material.GHAST_SPAWN_EGG -> createEntity(EntityType.GHAST)
            Material.GLOW_SQUID_SPAWN_EGG -> createEntity(EntityType.GLOW_SQUID)
            Material.GOAT_SPAWN_EGG -> createEntity(EntityType.GOAT)
            Material.GUARDIAN_SPAWN_EGG -> createEntity(EntityType.GUARDIAN)
            Material.HOGLIN_SPAWN_EGG -> createEntity(EntityType.HOGLIN)
            Material.HORSE_SPAWN_EGG -> createEntity(EntityType.HORSE)
            Material.HUSK_SPAWN_EGG -> createEntity(EntityType.HUSK)
            Material.IRON_GOLEM_SPAWN_EGG -> createEntity(EntityType.IRON_GOLEM)
            Material.LLAMA_SPAWN_EGG -> createEntity(EntityType.LLAMA)
            Material.MAGMA_CUBE_SPAWN_EGG -> createEntity(EntityType.MAGMA_CUBE)
            Material.MOOSHROOM_SPAWN_EGG -> createEntity(EntityType.MOOSHROOM)
            Material.MULE_SPAWN_EGG -> createEntity(EntityType.MULE)
            Material.OCELOT_SPAWN_EGG -> createEntity(EntityType.OCELOT)
            Material.PANDA_SPAWN_EGG -> createEntity(EntityType.PANDA)
            Material.PARROT_SPAWN_EGG -> createEntity(EntityType.PARROT)
            Material.PHANTOM_SPAWN_EGG -> createEntity(EntityType.PHANTOM)
            Material.PIG_SPAWN_EGG -> createEntity(EntityType.PIG)
            Material.PIGLIN_SPAWN_EGG -> createEntity(EntityType.PIGLIN)
            Material.PIGLIN_BRUTE_SPAWN_EGG -> createEntity(EntityType.PIGLIN_BRUTE)
            Material.PILLAGER_SPAWN_EGG -> createEntity(EntityType.PILLAGER)
            Material.POLAR_BEAR_SPAWN_EGG -> createEntity(EntityType.POLAR_BEAR)
            Material.PUFFERFISH_SPAWN_EGG -> createEntity(EntityType.PUFFERFISH)
            Material.RABBIT_SPAWN_EGG -> createEntity(EntityType.RABBIT)
            Material.RAVAGER_SPAWN_EGG -> createEntity(EntityType.RAVAGER)
            Material.SALMON_SPAWN_EGG -> createEntity(EntityType.SALMON)
            Material.SHEEP_SPAWN_EGG -> createEntity(EntityType.SHEEP)
            Material.SHULKER_SPAWN_EGG -> createEntity(EntityType.SHULKER)
            Material.SILVERFISH_SPAWN_EGG -> createEntity(EntityType.SILVERFISH)
            Material.SKELETON_SPAWN_EGG -> createEntity(EntityType.SKELETON)
            Material.SKELETON_HORSE_SPAWN_EGG -> createEntity(EntityType.SKELETON_HORSE)
            Material.SLIME_SPAWN_EGG -> createEntity(EntityType.SLIME)
            Material.SNIFFER_SPAWN_EGG -> createEntity(EntityType.SNIFFER)
            Material.SNOW_GOLEM_SPAWN_EGG -> createEntity(EntityType.SNOW_GOLEM)
            Material.SPIDER_SPAWN_EGG -> createEntity(EntityType.SPIDER)
            Material.SQUID_SPAWN_EGG -> createEntity(EntityType.SQUID)
            Material.STRAY_SPAWN_EGG -> createEntity(EntityType.STRAY)
            Material.STRIDER_SPAWN_EGG -> createEntity(EntityType.STRIDER)
            Material.TADPOLE_SPAWN_EGG -> createEntity(EntityType.TADPOLE)
            Material.TRADER_LLAMA_SPAWN_EGG -> createEntity(EntityType.TRADER_LLAMA)
            Material.TROPICAL_FISH_SPAWN_EGG -> createEntity(EntityType.TROPICAL_FISH)
            Material.TURTLE_SPAWN_EGG -> createEntity(EntityType.TURTLE)
            Material.VEX_SPAWN_EGG -> createEntity(EntityType.VEX)
            Material.VILLAGER_SPAWN_EGG -> createEntity(EntityType.VILLAGER)
            Material.VINDICATOR_SPAWN_EGG -> createEntity(EntityType.VINDICATOR)
            Material.WANDERING_TRADER_SPAWN_EGG -> createEntity(EntityType.WANDERING_TRADER)
            Material.WARDEN_SPAWN_EGG -> createEntity(EntityType.WARDEN)
            Material.WITCH_SPAWN_EGG -> createEntity(EntityType.WITCH)
            Material.WITHER_SKELETON_SPAWN_EGG -> createEntity(EntityType.WITHER_SKELETON)
            Material.WOLF_SPAWN_EGG -> createEntity(EntityType.WOLF)
            Material.ZOGLIN_SPAWN_EGG -> createEntity(EntityType.ZOGLIN)
            Material.ZOMBIE_SPAWN_EGG -> createEntity(EntityType.ZOMBIE)
            Material.ZOMBIE_HORSE_SPAWN_EGG -> createEntity(EntityType.ZOMBIE_HORSE)
            Material.ZOMBIE_VILLAGER_SPAWN_EGG -> createEntity(EntityType.ZOMBIE_VILLAGER)
            Material.ZOMBIFIED_PIGLIN_SPAWN_EGG -> createEntity(EntityType.ZOMBIFIED_PIGLIN)
            Material.WITHER_SPAWN_EGG -> createEntity(EntityType.WITHER)
            Material.ENDER_DRAGON_SPAWN_EGG -> createEntity(EntityType.ENDER_DRAGON)
            else -> return@addListener
        }

        val pos = event.position.relative(event.blockFace).add(0.5, 0.0, 0.5)
        entity.setInstance(instanceContainer, pos)
    }

    globalEventHandler.addListener(
        EntityAttackEvent::class.java
    ) { event: EntityAttackEvent ->
        val target = event.target

        if (target !is LivingEntity) return@addListener

        if (event.entity is Player && event.entity.velocity.y < 0.0) {
            target.damage(Damage.fromEntity(event.entity, 1.5f))
            event.entity.instance.playSound(Sound.sound(SoundEvent.ENTITY_PLAYER_ATTACK_CRIT.key(), Sound.Source.MASTER, 1f, 1f), target.position)
        } else target.damage(Damage.fromEntity(event.entity, 1f))

        if (target.health <= 0.0) target.kill()
    }

    server.start("0.0.0.0", 25565)
    println("Server Running!")
}

fun createEntity(entityType: EntityType): LivingEntity {
    val entity = LivingEntity(entityType)
    entity.health = 20f
    return entity
}