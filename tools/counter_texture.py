#!/usr/bin/env python3
# draws textures/item/counter.png, run from repo root
from PIL import Image
im = Image.new("RGBA", (16, 16), (0, 0, 0, 0))
px = im.load()
STICK, STICK_DARK = (0x8a, 0x66, 0x2e, 255), (0x4b, 0x37, 0x15, 255)
PAPER, PAPER_EDGE, INK = (0xe8, 0xe4, 0xd6, 255), (0xb8, 0xb2, 0x9c, 255), (0x4a, 0x44, 0x3a, 255)
RED, RED_DARK, RED_LIT = (0xc8, 0x10, 0x08, 255), (0x6e, 0x04, 0x02, 255), (0xff, 0x4a, 0x30, 255)
# stick frame
for i in range(3, 13):
    for a, b in [(i, 2), (i, 13), (2, i), (13, i)]:
        px[a, b] = STICK if (i % 3) else STICK_DARK
# paper label
for x in range(4, 12):
    for y in range(4, 12):
        px[x, y] = PAPER_EDGE if x in (4, 11) or y in (4, 11) else PAPER
# count digits on the label
for x, y in [(6, 6), (6, 7), (6, 8), (6, 9), (8, 6), (9, 6), (9, 7), (8, 8), (8, 9), (9, 9)]:
    px[x, y] = INK
# redstone corners
for cx, cy in [(1, 1), (13, 1), (1, 13), (13, 13)]:
    for dx, dy in [(0, 0), (1, 0), (0, 1), (1, 1)]:
        px[cx + dx, cy + dy] = RED
    px[cx, cy] = RED_LIT
    px[cx + 1, cy + 1] = RED_DARK
im.save("common/src/main/resources/assets/simplebarrels/textures/item/counter.png")
