#!/usr/bin/env python3
# keep WOODS in sync with BarrelWood.java, run from repo root
import json, os
WOODS = ["oak","spruce","birch","jungle","acacia","dark_oak","mangrove","cherry","bamboo","crimson","warped"]
ROOT = "common/src/main/resources"
A, D = f"{ROOT}/assets/simplebarrels", f"{ROOT}/data/simplebarrels"
def w(path, obj):
    os.makedirs(os.path.dirname(path), exist_ok=True)
    with open(path, "w") as f: json.dump(obj, f, indent=2); f.write("\n")
def adv(name, has, base=None, extra=None):
    base = base or f"{D}"
    def pred(i):
        if v21: return {"items": i}
        return {"tag": i[1:]} if i.startswith("#") else {"items": [i]}
    crit = {"has_the_recipe": {"trigger": "minecraft:recipe_unlocked", "conditions": {"recipe": f"simplebarrels:{name}"}}}
    for i in has:
        crit["has_" + i.split(":")[-1].replace("/", "_")] = {"trigger": "minecraft:inventory_changed", "conditions": {"items": [pred(i)]}}
    a = dict(extra or {})
    a.update({"parent": "minecraft:recipes/root", "criteria": crit, "requirements": [list(crit)], "rewards": {"recipes": [f"simplebarrels:{name}"]}})
    w(f"{base}/{ADV}/recipes/misc/{name}.json", a)
def title(s): return " ".join(p.capitalize() for p in s.split("_"))
import sys
# modes: 1.21 (default), 26 (26.1.x)
v26 = "26" in sys.argv[1:]
v21 = True
def ing(x):
    if v26: return x
    return {"tag": x[1:]} if x.startswith("#") else {"item": x}
def item_def(name, model=None):
    if v26: w(f"{A}/items/{name}.json", {"model": model or {"type": "minecraft:model", "model": f"simplebarrels:item/{name}"}})
ADV = "advancement" if v21 else "advancements"
lang = {"itemGroup.simplebarrels": "Simple Barrels"}
for wood in WOODS:
    bid = f"{wood}_barrel"
    planks = f"minecraft:block/{'bamboo_planks' if wood=='bamboo' else wood+'_planks'}"
    rot = {"east": 0, "south": 90, "west": 180, "north": 270}
    w(f"{A}/blockstates/{bid}.json", {"variants": {f"facing={f}": ({"model": f"simplebarrels:block/{bid}", "y": y} if y else {"model": f"simplebarrels:block/{bid}"}) for f, y in rot.items()}})
    w(f"{A}/models/block/{bid}.json", {"parent": "simplebarrels:block/barrel_base", "textures": {"particle": planks, "texture": planks}})
    w(f"{A}/models/item/{bid}.json", {"parent": f"simplebarrels:block/{bid}"})
    item_def(bid)
    w(f"{D}/loot_table/blocks/{bid}.json", {"type": "minecraft:block", "pools": [{"rolls": 1, "bonus_rolls": 0,
        "entries": [{"type": "minecraft:item", "name": f"simplebarrels:{bid}",
        "functions": [{"function": "minecraft:copy_components", "source": "block_entity", "include": ["simplebarrels:upgrades"]}]}], "conditions": [{"condition": "minecraft:survives_explosion"}]}],
        "random_sequence": f"simplebarrels:blocks/{bid}"})
    w(f"{D}/recipe/{bid}.json", {"type": "minecraft:crafting_shaped", "category": "misc", "group": f"simplebarrels:{bid}", "pattern": ["WIW", " C ", "WIW"],
        "key": {"W": ing(f"minecraft:{wood}_planks"), "I": ing("#c:ingots/iron"), "C": ing("#c:chests/wooden")},
        "result": {"id": f"simplebarrels:{bid}", "count": 1}})
    adv(bid, ["minecraft:chest", f"minecraft:{wood}_planks"])
    for frame, count in [(True, False), (False, True), (True, True)]:
        suffix = ("_frame" if frame else "") + ("_counter" if count else "")
        key = {"W": ing(f"minecraft:{wood}_planks"), "I": ing("#c:ingots/iron"), "C": ing("#c:chests/wooden")}
        if frame: key["F"] = ing("minecraft:item_frame")
        if count: key["N"] = ing("simplebarrels:counter")
        flags = {k: True for k, v in [("show_count", count), ("show_item", frame)] if v}
        w(f"{D}/recipe/{bid}{suffix}.json", {"type": "minecraft:crafting_shaped", "category": "misc", "group": f"simplebarrels:{bid}",
            "pattern": ["WIW", ("F" if frame else " ") + "C" + ("N" if count else " "), "WIW"], "key": key,
            "result": {"id": f"simplebarrels:{bid}", "count": 1, "components": {"simplebarrels:upgrades": flags}}})
        adv(bid + suffix, ["minecraft:chest", f"minecraft:{wood}_planks"], None)
    lang[f"block.simplebarrels.{bid}"] = f"{title(wood)} Barrel"
def shaped(name, pattern, key, group=None, has=("#simplebarrels:barrels",)):
    adv(name, list(has))
    r = {"type": "minecraft:crafting_shaped", "category": "misc"}
    if group: r["group"] = group
    r.update({"pattern": pattern, "key": key, "result": {"id": f"simplebarrels:{name}", "count": 1}})
    w(f"{D}/recipe/{name}.json", r)
items = {"dolly": "Dolly", "counter": "Barrel Counter", "item_frame_upgrade": "Item Frame Upgrade"}
for i in range(1, 5): items[f"mark{i}_compressor"] = f"Mark {i} Compressor Upgrade"
for name, label in items.items():
    if name != "dolly":  # dolly has a hand made 3d model
        w(f"{A}/models/item/{name}.json", {"parent": "minecraft:item/generated", "textures": {"layer0": f"simplebarrels:item/{name}"}})
        item_def(name)
    lang[f"item.simplebarrels.{name}"] = label
plus = [" A ", "ABA", " A "]
shaped("counter", ["RSR", "SPS", "RSR"], {"R": ing("minecraft:redstone"), "S": ing("minecraft:stick"), "P": ing("minecraft:paper")},
       has=["minecraft:paper", "#simplebarrels:barrels"])
shaped("item_frame_upgrade", plus, {"A": ing("minecraft:stick"), "B": ing("minecraft:item_frame")})
shaped("mark1_compressor", plus, {"A": ing("minecraft:stick"), "B": ing("minecraft:piston")}, "simplebarrels:compressor")
for i, mid in [(2, "c:ingots/iron"), (3, "c:ingots/gold"), (4, "c:gems/diamond")]:
    shaped(f"mark{i}_compressor", plus, {"A": ing(f"simplebarrels:mark{i-1}_compressor"), "B": ing("#" + mid)}, "simplebarrels:compressor")
shaped("dolly", ["I  ", "IS ", "III"], {"I": ing("#c:ingots/iron"), "S": ing("minecraft:stick")})

# modded woods, recipes only load when the mod is there. planks ids checked against the mod jars
MODS = {"biomesoplenty": "Biomes O' Plenty", "regions_unexplored": "Regions Unexplored", "natures_spirit": "Nature's Spirit"}
MODDED = {
    "biomesoplenty": "dead empyreal fir hellbark jacaranda magic mahogany maple palm pine redwood umbran willow",
    "regions_unexplored": "alpha baobab blackwood blue_bioshroom brimwood cobalt cypress dead eucalyptus green_bioshroom joshua kapok larch magnolia maple "
        + ("wisteria" if v21 else "mauve") + " palm pine pink_bioshroom redwood socotra willow yellow_bioshroom",
    "natures_spirit": "aspen cedar coconut cypress fir ghaf joshua larch mahogany maple olive palo_verde redwood saxaul sugi willow wisteria",
}
MODDED = {m: sorted(v.split()) for m, v in MODDED.items()}
def when(mod):
    if v21: return {"neoforge:conditions": [{"type": "neoforge:mod_loaded", "modid": mod}],
                    "fabric:load_conditions": [{"condition": "fabric:all_mods_loaded", "values": [mod]}]}
    return {"conditions": [{"type": "forge:mod_loaded", "modid": mod}],
            "fabric:load_conditions": [{"condition": "fabric:all_mods_loaded", "values": [mod]}]}
for mod, woods in MODDED.items():
    lang[f"mod.simplebarrels.{mod}"] = MODS[mod]
    for wood in woods:
        planks, name = f"{mod}:{wood}_planks", f"{mod}/{wood}_barrel"
        lang[f"wood.simplebarrels.{mod}.{wood}"] = title(wood)
        for frame, count in [(False, False), (True, False), (False, True), (True, True)]:
            suffix = ("_frame" if frame else "") + ("_counter" if count else "")
            key = {"W": ing(planks), "I": ing("#c:ingots/iron"), "C": ing("#c:chests/wooden")}
            if frame: key["F"] = ing("minecraft:item_frame")
            if count: key["N"] = ing("simplebarrels:counter")
            comps = {"simplebarrels:wood": planks}
            flags = {k: True for k, v in [("show_count", count), ("show_item", frame)] if v}
            if flags: comps["simplebarrels:upgrades"] = flags
            r = dict(when(mod))
            r.update({"type": "minecraft:crafting_shaped", "category": "misc", "group": f"simplebarrels:{name}",
                "pattern": ["WIW", ("F" if frame else " ") + "C" + ("N" if count else " "), "WIW"], "key": key,
                "result": {"id": "simplebarrels:modded_barrel", "count": 1, "components": comps}})
            w(f"{D}/recipe/{name}{suffix}.json", r)
            # chest only, the advancement must not name items that may not exist
            adv(name + suffix, ["minecraft:chest"], extra=when(mod))
lang["block.simplebarrels.modded_barrel"] = "Modded Barrel"
lang["block.simplebarrels.modded_barrel.named"] = "%s Barrel"
lang["tooltip.simplebarrels.missing_mod"] = "%s (not installed)"
with open("common/src/main/java/com/workshopcraft/simplebarrels/registry/ModdedWoods.java", "w") as f:
    f.write("package com.workshopcraft.simplebarrels.registry;\n\n// generated by tools/gen_resources.py\npublic final class ModdedWoods {\n")
    f.write("    public static final String[] PLANKS = {\n")
    for mod, woods in MODDED.items():
        for i in range(0, len(woods), 4):
            f.write("            " + " ".join(f'"{mod}:{x}_planks",' for x in woods[i:i + 4]) + "\n")
    f.write("    };\n\n    private ModdedWoods() {}\n}\n")
# fluid barrels share the wooden barrel straps, keep FLUID_TIERS in sync with FluidTier.java. steel needs Simple Machines for its ingot
FLUID_TIERS = ["copper", "iron", "steel"]
fbase = json.load(open(f"{A}/models/block/barrel_base.json"))
fbase["textures"] = {"particle": "#texture", "texture": "simplebarrels:block/copper_fluid_barrel", "barrelband": "simplebarrels:block/barrelband",
    "front": "simplebarrels:block/copper_fluid_barrel_front", "top": "simplebarrels:block/copper_fluid_barrel_top"}
# the front stave carries the window, the lid boards carry the port
fbase["elements"][27]["faces"]["east"] = {"uv": [4, 3, 12, 13], "texture": "#front"}
for i in range(46, 51): fbase["elements"][i]["faces"]["up"]["texture"] = "#top"
w(f"{A}/models/block/fluid_barrel_base.json", fbase)
for tier in FLUID_TIERS:
    bid = f"{tier}_fluid_barrel"
    tex = f"simplebarrels:block/{bid}"
    rot = {"east": 0, "south": 90, "west": 180, "north": 270}
    w(f"{A}/blockstates/{bid}.json", {"variants": {f"facing={f}": ({"model": f"simplebarrels:block/{bid}", "y": y} if y else {"model": f"simplebarrels:block/{bid}"}) for f, y in rot.items()}})
    w(f"{A}/models/block/{bid}.json", {"parent": "simplebarrels:block/fluid_barrel_base", "textures": {"particle": tex, "texture": tex,
        "barrelband": "simplebarrels:block/barrelband", "front": f"{tex}_front", "top": f"{tex}_top"}})
    w(f"{A}/models/item/{bid}.json", {"parent": f"simplebarrels:block/{bid}"})
    item_def(bid)
    w(f"{D}/loot_table/blocks/{bid}.json", {"type": "minecraft:block", "pools": [{"rolls": 1, "bonus_rolls": 0,
        "entries": [{"type": "minecraft:item", "name": f"simplebarrels:{bid}",
        "functions": [{"function": "minecraft:copy_components", "source": "block_entity", "include": ["simplebarrels:fluid", "simplebarrels:upgrades"]}]}],
        "conditions": [{"condition": "minecraft:survives_explosion"}]}], "random_sequence": f"simplebarrels:blocks/{bid}"})
    r = dict(when("simplemachines")) if tier == "steel" else {}
    r.update({"type": "minecraft:crafting_shaped", "category": "misc", "group": "simplebarrels:fluid_barrel", "pattern": ["TIT", " G ", "TIT"],
        "key": {"T": ing(f"#c:ingots/{tier}"), "I": ing("#c:ingots/iron"), "G": ing("#c:glass_panes")},
        "result": {"id": f"simplebarrels:{bid}", "count": 1}})
    w(f"{D}/recipe/{bid}.json", r)
    adv(bid, ["#c:glass_panes"] if tier == "steel" else ["#c:glass_panes", f"#c:ingots/{tier}"], extra=when("simplemachines") if tier == "steel" else None)
    lang[f"block.simplebarrels.{bid}"] = f"{title(tier)} Fluid Barrel"
lang["tooltip.simplebarrels.fluid_barrel"] = "%s: %s / %s mB"
lang["tooltip.simplebarrels.fluid_barrel_empty"] = "Empty, holds %s buckets of one fluid"
fluid_ids = [f"simplebarrels:{t}_fluid_barrel" for t in FLUID_TIERS]

ids = [f"simplebarrels:{x}_barrel" for x in WOODS] + ["simplebarrels:modded_barrel"]
w(f"{D}/tags/block/barrels.json", {"values": ids})
w(f"{ROOT}/data/minecraft/tags/block/mineable/axe.json", {"values": ids})
w(f"{ROOT}/data/minecraft/tags/block/mineable/pickaxe.json", {"values": fluid_ids})
w(f"{D}/tags/item/barrels.json", {"values": ids})
w(f"{A}/lang/en_us.json", lang)

w(f"{A}/blockstates/modded_barrel.json", {"variants": {"": {"model": "simplebarrels:block/modded_barrel"}}})
w(f"{A}/models/block/modded_barrel.json", {"textures": {"particle": "minecraft:block/oak_planks"}})
if v26:
    # drawn by ModdedBarrelItemRenderer, this model only gives the display transforms
    w(f"{A}/models/item/modded_barrel.json", {"gui_light": "side", "textures": {"particle": "minecraft:block/oak_planks"},
        "display": json.load(open("tools/models/block_display.json"))})
    item_def("modded_barrel", {"type": "minecraft:special", "base": "simplebarrels:item/modded_barrel", "model": {"type": "simplebarrels:modded_barrel"}})
else:
    w(f"{A}/models/item/modded_barrel.json", {"parent": "builtin/entity", "gui_light": "side", "textures": {"particle": "minecraft:block/oak_planks"},
        "display": json.load(open("tools/models/block_display.json"))})
w(f"{D}/loot_table/blocks/modded_barrel.json", {"type": "minecraft:block", "pools": [{"rolls": 1, "bonus_rolls": 0,
    "entries": [{"type": "minecraft:item", "name": "simplebarrels:modded_barrel",
    "functions": [{"function": "minecraft:copy_components", "source": "block_entity", "include": ["simplebarrels:upgrades", "simplebarrels:wood"]}]}],
    "conditions": [{"condition": "minecraft:survives_explosion"}]}], "random_sequence": "simplebarrels:blocks/modded_barrel"})

# loaded dolly: dolly model plus a scaled barrel on the plate, picked by the simplebarrels:carried predicate
dolly = json.load(open("tools/models/dolly_base.json"))
barrel = json.load(open(f"{A}/models/block/barrel_base.json"))
S, OFF = 0.6, (3.2, 1.6, 1.4)
def fit(p): return [round(p[i] * S + OFF[i], 4) for i in range(3)]
barrel_elems = []
for e in barrel["elements"]:
    e = json.loads(json.dumps(e))
    e["from"], e["to"] = fit(e["from"]), fit(e["to"])
    for f in e["faces"].values():
        f["texture"] = "#planks" if f["texture"] == "#texture" else f["texture"]
    barrel_elems.append(e)
overrides = []
for i, wood in enumerate(WOODS):
    m = json.loads(json.dumps(dolly))
    planks = f"minecraft:block/{wood}_planks"
    m["textures"].update({"planks": planks, "barrelband": "simplebarrels:block/barrelband"})
    m["elements"] = m["elements"] + barrel_elems
    w(f"{A}/models/item/dolly_{wood}.json", m)
    overrides.append({"predicate": {"simplebarrels:carried": round((i + 1) / 16, 4)}, "model": f"simplebarrels:item/dolly_{wood}"})
if v26:
    # chests and modded barrels: plain dolly plus DollyLoadRenderer on the plate
    loaded = {"type": "minecraft:composite", "models": [{"type": "minecraft:model", "model": "simplebarrels:item/dolly"},
        {"type": "minecraft:special", "base": "simplebarrels:item/dolly", "model": {"type": "simplebarrels:dolly_load"}}]}
    cases = [{"when": wood, "model": {"type": "minecraft:model", "model": f"simplebarrels:item/dolly_{wood}"}} for wood in WOODS]
    cases.append({"when": ["chest", "trapped_chest", "ender_chest", "modded"], "model": loaded})
    item_def("dolly", {"type": "minecraft:select", "property": "simplebarrels:carried", "cases": cases,
        "fallback": {"type": "minecraft:model", "model": "simplebarrels:item/dolly"}})
    w(f"{A}/models/item/dolly.json", dolly)
    sys.exit()
# chests are drawn by DollyChestRenderer, these models only switch to the custom renderer
for i, name in enumerate(["chest", "trapped_chest", "ender_chest"]):
    w(f"{A}/models/item/dolly_{name}.json", {"parent": "builtin/entity", "gui_light": "front",
        "textures": {"particle": "simplebarrels:block/dolly"}, "display": dolly["display"]})
    overrides.append({"predicate": {"simplebarrels:carried": round((12 + i) / 16, 4)}, "model": f"simplebarrels:item/dolly_{name}"})
# modded barrels go through ModdedBarrelModel
w(f"{A}/models/item/dolly_modded.json", {"parent": "builtin/entity", "gui_light": "front",
    "textures": {"particle": "simplebarrels:block/dolly"}, "display": dolly["display"]})
overrides.append({"predicate": {"simplebarrels:carried": round(15 / 16, 4)}, "model": "simplebarrels:item/dolly_modded"})
dolly["overrides"] = overrides
w(f"{A}/models/item/dolly.json", dolly)
