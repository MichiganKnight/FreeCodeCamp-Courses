import pygame

from os.path import join
from NewGame.Settings import tile_size
from NewGame.Tiles import *
from Utils import *


def create_tile_group(layout, group_type):
    sprite_group = pygame.sprite.Group()

    for row_index, row in enumerate(layout):
        for col_index, val in enumerate(row):
            if val != '-1':
                x = col_index * tile_size
                y = row_index * tile_size

                if group_type == 'Terrain':
                    terrain_tile_list = import_cut_graphics(join('NewGame', 'Assets', 'Terrain', 'terrain_tiles.png'))
                    tile_surface = terrain_tile_list[int(val)]

                    sprite = StaticTile(tile_size, x, y, tile_surface)
                elif group_type == 'Grass':
                    grass_tile_list = import_cut_graphics(join('NewGame', 'Assets', 'Decoration', 'Grass', 'grass.png'))
                    tile_surface = grass_tile_list[int(val)]

                    sprite = StaticTile(tile_size, x, y, tile_surface)
                elif group_type == 'Crates':
                    sprite = Crate(tile_size, x, y)

                sprite_group.add(sprite)

    return sprite_group


class Level:
    def __init__(self, level_data, surface):

        # General Setup
        self.display_surface = surface
        self.world_shift = -2

        # Terrain Setup
        terrain_layout = import_csv_layout(level_data['Terrain'])
        self.terrain_sprites = create_tile_group(terrain_layout, 'Terrain')

        # Grass Setup
        grass_layout = import_csv_layout(level_data['Grass'])
        self.grass_sprites = create_tile_group(grass_layout, 'Grass')

        # Crates Setup
        crate_layout = import_csv_layout(level_data['Crates'])
        self.crate_sprites = create_tile_group(crate_layout, 'Crates')

    def run(self):
        # Run Entire Game

        # Terrain
        self.terrain_sprites.update(self.world_shift)
        self.terrain_sprites.draw(self.display_surface)

        # Grass
        self.grass_sprites.update(self.world_shift)
        self.grass_sprites.draw(self.display_surface)

        # Crates
        self.crate_sprites.update(self.world_shift)
        self.crate_sprites.draw(self.display_surface)