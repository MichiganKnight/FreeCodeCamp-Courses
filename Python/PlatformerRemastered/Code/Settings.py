import pygame, sys

from pygame.math import Vector2 as vector

WINDOW_WIDTH, WINDOW_HEIGHT = 1280, 720
TILE_SIZE = 64
ANIMATION_SPEED = 6

# Layers
Z_LAYERS = {
    "BG": 0,
    "Clouds": 1,
    "BG Tiles": 2,
    "Path": 3,
    "BG Details": 4,
    "Main": 5,
    "Water": 6,
    "FG": 7
}