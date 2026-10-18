#!/usr/bin/env python3
# keep WOODS in sync with BarrelWood.java, run from repo root
import json, os
WOODS = ["oak","spruce","birch","jungle","acacia","dark_oak","mangrove","cherry","bamboo","crimson","warped"]
ROOT = "common/src/main/resources"
A, D = f"{ROOT}/assets/simplebarrels", f"{ROOT}/data/simplebarrels"
def w(path, obj):
    os.makedirs(os.path.dirname(path), exist_ok=True)
    with open(path, "w") as f: json.dump(obj, f, indent=2); f.write("\n")
def title(s): return " ".join(p.capitalize() for p in s.split("_"))
lang = {"itemGroup.simplebarrels": "Simple Barrels"}
for wood in WOODS:
    bid = f"{wood}_barrel"
    planks = f"minecraft:block/{'bamboo_planks' if wood=='bamboo' else wood+'_planks'}"
    rot = {"east": 0, "south": 90, "west": 180, "north": 270}
    w(f"{A}/blockstates/{bid}.json", {"variants": {f"facing={f}": ({"model": f"simplebarrels:block/{bid}", "y": y} if y else {"model": f"simplebarrels:block/{bid}"}) for f, y in rot.items()}})
    w(f"{A}/models/block/{bid}.json", {"parent": "simplebarrels:block/barrel_base", "textures": {"particle": planks, "texture": planks}})
    w(f"{A}/models/item/{bid}.json", {"parent": f"simplebarrels:block/{bid}"})
    w(f"{D}/loot_table/blocks/{bid}.json", {"type": "minecraft:block", "pools": [{"rolls": 1, "bonus_rolls": 0,
        "entries": [{"type": "minecraft:item", "name": f"simplebarrels:{bid}"}], "conditions": [{"condition": "minecraft:survives_explosion"}]}],
        "random_sequence": f"simplebarrels:blocks/{bid}"})
    w(f"{D}/recipe/{bid}.json", {"type": "minecraft:crafting_shaped", "category": "misc", "pattern": ["WIW", " C ", "WIW"],
        "key": {"W": {"item": f"minecraft:{wood}_planks"}, "I": {"tag": "c:ingots/iron"}, "C": {"tag": "c:chests/wooden"}},
        "result": {"id": f"simplebarrels:{bid}", "count": 1}})
    lang[f"block.simplebarrels.{bid}"] = f"{title(wood)} Barrel"
ids = [f"simplebarrels:{x}_barrel" for x in WOODS]
w(f"{D}/tags/block/barrels.json", {"values": ids})
w(f"{ROOT}/data/minecraft/tags/block/mineable/axe.json", {"values": ids})
w(f"{D}/tags/item/barrels.json", {"values": ids})
w(f"{A}/lang/en_us.json", lang)
