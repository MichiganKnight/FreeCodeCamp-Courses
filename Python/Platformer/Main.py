import os
import random
import math
import pygame

from os import listdir
from os.path import isfile, join

from Globals import *
from Objects.Block import Block
from Player import handle_move, Player

pygame.init()
pygame.display.set_caption("Platformer")

window = pygame.display.set_mode((WIDTH, HEIGHT))

def get_background(name):
    image = pygame.image.load(join("Assets", "Background", name))
    _, _, width, height = image.get_rect()
    tiles = []

    for i in range(WIDTH // width + 1):
        for j in range(HEIGHT // height + 1):
            pos = [i * width, j * height]
            tiles.append(pos)

    return tiles, image

def draw(window, background, bg_image, player, objects):
    for tile in background:
        window.blit(bg_image, tile)

    for obj in objects:
        obj.draw(window)

    player.draw(window)

    pygame.display.update()

def main(window):
    clock = pygame.time.Clock()
    background, bg_image = get_background("Blue.png")

    block_size = 96

    player = Player(100, 100, 50, 50)
    floor = [Block(i * block_size, HEIGHT - block_size, block_size) for i in range(-WIDTH // block_size, WIDTH * 2 // block_size)]

    run = True
    while run:
        clock.tick(FPS)

        for event in pygame.event.get():
            if event.type == pygame.QUIT:
                run = False
                break

            if event.type == pygame.KEYDOWN:
                if event.key == pygame.K_SPACE and player.jump_count < 2:
                    player.jump()

        player.loop(FPS)
        handle_move(player, floor)
        draw(window, background, bg_image, player, floor)

    pygame.quit()
    quit()

if __name__ == "__main__":
    main(window)