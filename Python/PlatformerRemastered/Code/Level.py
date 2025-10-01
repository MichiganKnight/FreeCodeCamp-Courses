import pygame

from random import uniform

from Settings import *
from Sprites import *
from Player import Player
from Groups import AllSprites

class Level:
    def __init__(self, tmx_map, level_frames):
        self.display_surface = pygame.display.get_surface()

        # Groups
        self.all_sprites = AllSprites()
        self.collision_sprites = pygame.sprite.Group()
        self.semi_collision_sprites = pygame.sprite.Group()
        self.damage_sprites = pygame.sprite.Group()

        self.setup(tmx_map, level_frames)

    def setup(self, tmx_map, level_frames):
        # Tiles
        for layer in ["BG", "Terrain", "FG", "Platforms"]:
            for x, y, surface in tmx_map.get_layer_by_name(layer).tiles():
                groups = [self.all_sprites]
                if layer == "Terrain": groups.append(self.collision_sprites)
                elif layer == "Platforms": groups.append(self.semi_collision_sprites)
                match layer:
                    case "BG": z = Z_LAYERS["BG Tiles"]
                    case "FG": z = Z_LAYERS["FG"]
                    case _: z = Z_LAYERS["Main"]
                Sprite((x * TILE_SIZE, y * TILE_SIZE), surface, groups, z)

        # BG Details
        for obj in tmx_map.get_layer_by_name("BG Details"):
            if obj.name == "Static":
                Sprite((obj.x, obj.y), obj.image, self.all_sprites, Z_LAYERS["BG Tiles"])
            else:
                AnimatedSprite((obj.x, obj.y), level_frames[obj.name], self.all_sprites, Z_LAYERS["BG Tiles"])
                if obj.name == "Candle":
                    AnimatedSprite((obj.x, obj.y) + vector(-20, -20), level_frames["Candle_Light"], self.all_sprites, Z_LAYERS["BG Tiles"])

        # Objects
        for obj in tmx_map.get_layer_by_name("Objects"):
            if obj.name == "Player":
                self.player = Player((obj.x, obj.y), self.all_sprites, self.collision_sprites, self.semi_collision_sprites)
            else:
                if obj.name in ("Barrel", "Crate"):
                    Sprite((obj.x, obj.y), obj.image, (self.all_sprites, self.collision_sprites))
                else:
                    # Frames
                    frames = level_frames[obj.name] if not "Palm" in obj.name else level_frames["Palms"][obj.name]
                    if obj.name == "Floor_Spike" and obj.properties["Inverted"]:
                        frames = [pygame.transform.flip(frame, False, True) for frame in frames]

                    # Groups
                    groups = [self.all_sprites]
                    if obj.name in("Palm_Small", "Palm_Large"): groups.append(self.semi_collision_sprites)
                    if obj.name in("Saw", "Floor_Spike"): groups.append(self.damage_sprites)

                    # Z Index
                    z = Z_LAYERS["Main"] if not "BG" in obj.name else Z_LAYERS["BG Details"]

                    # Animation Speed
                    animation_speed = ANIMATION_SPEED if not "Palm" in obj.name else ANIMATION_SPEED + uniform(-1, 1)

                    AnimatedSprite((obj.x, obj.y), frames, self.all_sprites, z, animation_speed)

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
        self.all_sprites.draw(self.player.hitbox_rect.center)