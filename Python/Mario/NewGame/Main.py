import pygame, sys

from Settings import *
from Level import Level
from GameData import level_0

# Pygame Setup
pygame.init()
pygame.display.set_caption('Mario Platformer')

screen = pygame.display.set_mode((screen_width, screen_height))
clock = pygame.time.Clock()

level = Level(level_0, screen)

while True:
    for event in pygame.event.get():
        if event.type == pygame.QUIT:
            pygame.quit()
            sys.exit()

    screen.fill('black')
    level.run()

    pygame.display.update()
    clock.tick(60)