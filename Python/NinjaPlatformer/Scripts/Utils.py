import os
import pygame

from os.path import join
from os import listdir

BASE_IMG_PATH = join("Assets", "Images")

def load_image(path):
    img = pygame.image.load(join(BASE_IMG_PATH, path)).convert()
    img.set_colorkey((0, 0, 0))

    return img

def load_images(path):
    images = []

    for img_name in sorted(listdir(join(BASE_IMG_PATH, path))):
        images.append(load_image(join(path, img_name)))

    return images