import pygame, sys

from Settings import *

# Pygame Setup
pygame.init()
pygame.display.set_caption('Mario Platformer')

screen = pygame.display.set_mode((screen_width, screen_height))
clock = pygame.time.Clock()

while True:
    for event in pygame.event.get():
        if event.type == pygame.QUIT:
            pygame.quit()
            sys.exit()

    screen.fill('black')

    pygame.display.update()
    clock.tick(60)