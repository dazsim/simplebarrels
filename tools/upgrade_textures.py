#!/usr/bin/env python3
# draws the upgrade item textures, run from repo root
from PIL import Image
OUT = "common/src/main/resources/assets/simplebarrels/textures/item"
STICK, STICK_DARK = (0x8a, 0x66, 0x2e, 255), (0x4b, 0x37, 0x15, 255)

def base():
    im = Image.new("RGBA", (16, 16), (0, 0, 0, 0))
    px = im.load()
    for i in range(3, 13):
        for a, b in [(i, 2), (i, 13), (2, i), (13, i)]:
            px[a, b] = STICK if i % 3 else STICK_DARK
    return im, px

def corners(px, lit, mid, dark):
    for cx, cy in [(1, 1), (13, 1), (1, 13), (13, 13)]:
        for dx, dy in [(0, 0), (1, 0), (0, 1), (1, 1)]:
            px[cx + dx, cy + dy] = mid
        px[cx, cy] = lit
        px[cx + 1, cy + 1] = dark

def item_frame():
    im, px = base()
    LEATHER, LEATHER_DARK = (0x8c, 0x5a, 0x3c, 255), (0x6a, 0x42, 0x2a, 255)
    for x in range(4, 12):
        for y in range(4, 12):
            px[x, y] = LEATHER_DARK if x in (4, 11) or y in (4, 11) else LEATHER
    # little sword on the leather
    BLADE, EDGE, HILT = (0xe6, 0xe6, 0xe6, 255), (0xa8, 0xa8, 0xa8, 255), (0x49, 0x36, 0x15, 255)
    for i in range(4):
        px[7 + i, 8 - i] = BLADE
        if i < 3:
            px[8 + i, 8 - i] = EDGE
    px[6, 8], px[7, 9], px[6, 9] = HILT, HILT, HILT
    px[5, 10] = HILT
    corners(px, (0xd8, 0xa8, 0x60, 255), (0xa0, 0x74, 0x38, 255), (0x5e, 0x40, 0x1a, 255))
    return im

TIERS = [  # lit, mid, dark accents per compressor mark
    ((0xb0, 0xb0, 0xb0, 255), (0x80, 0x80, 0x80, 255), (0x4a, 0x4a, 0x4a, 255)),  # stone
    ((0xff, 0xff, 0xff, 255), (0xd8, 0xd8, 0xd8, 255), (0x8e, 0x8e, 0x8e, 255)),  # iron
    ((0xff, 0xf2, 0x60, 255), (0xf0, 0xc0, 0x20, 255), (0x9a, 0x6a, 0x08, 255)),  # gold
    ((0xb8, 0xff, 0xf6, 255), (0x4a, 0xe0, 0xd8, 255), (0x1a, 0x8c, 0x88, 255)),  # diamond
]

def compressor(mark):
    im, px = base()
    PLANK, PLANK_DARK = (0xb8, 0x94, 0x5a, 255), (0x8c, 0x6c, 0x3c, 255)
    STONE, STONE_DARK = (0x8a, 0x8a, 0x8a, 255), (0x5c, 0x5c, 0x5c, 255)
    # piston head
    for x in range(4, 12):
        px[x, 4] = PLANK
        px[x, 5] = PLANK_DARK
    px[7, 6] = px[8, 6] = PLANK_DARK
    # piston body
    for x in range(4, 12):
        for y in range(7, 10):
            px[x, y] = STONE_DARK if y == 9 or x in (4, 11) else STONE
    # tier pips
    lit, mid, dark = TIERS[mark - 1]
    for i in range(mark):
        px[4 + i * 2, 11] = mid
    corners(px, lit, mid, dark)
    return im

items = {"item_frame_upgrade": item_frame()}
for m in range(1, 5):
    items[f"mark{m}_compressor"] = compressor(m)
for name, im in items.items():
    im.save(f"{OUT}/{name}.png")
