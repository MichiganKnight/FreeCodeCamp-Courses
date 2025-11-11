import os
import pygame

from os.path import join

BASE_IMG_PATH = join("Assets", "Images")

def load_image(path):
    img = pygame.image.load(join(BASE_IMG_PATH, path)).convert()
    img.set_colorkey((0, 0, 0))

    return img