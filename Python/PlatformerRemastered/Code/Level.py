import pygame

from Settings import *
from Sprites import *
from Player import Player

class Level:
    def __init__(self, tmx_map):
        self.display_surface = pygame.display.get_surface()

        # Groups
        self.all_sprites = pygame.sprite.Group()
        self.collision_sprites = pygame.sprite.Group()
        self.semi_collision_sprites = pygame.sprite.Group()

        self.setup(tmx_map)

    def setup(self, tmx_map):
        # Tiles
        for x, y, surface in tmx_map.get_layer_by_name("Terrain").tiles():
            Sprite((x * TILE_SIZE, y * TILE_SIZE), surface, (self.all_sprites, self.collision_sprites))

        # Objects
        for obj in tmx_map.get_layer_by_name("Objects"):
            if obj.name == "Player":
                Player((obj.x, obj.y), self.all_sprites, self.collision_sprites, self.semi_collision_sprites)

        # Moving Objects
        for obj in tmx_map.get_layer_by_name("Moving Objects"):
            if obj.name == "Helicopter":
                if obj.width > obj.height:
                    move_direction = "X"
                    start_position = (obj.x, obj.y + obj.height / 2)
                    end_position = (obj.x + obj.width, obj.y + obj.height / 2)
                else:
                    move_direction = "Y"
                    start_position = (obj.x + obj.width / 2, obj.y)
                    end_position = (obj.x + obj.width / 2, obj.y + obj.height)

                speed = obj.properties["Speed"]
                MovingSprite((self.all_sprites, self.semi_collision_sprites), start_position, end_position, move_direction, speed)

    def run(self, dt):
        self.all_sprites.update(dt)
        self.display_surface.fill("Black")
        self.all_sprites.draw(self.display_surface)