import pygame

from os.path import join
from NewGame.Settings import tile_size
from NewGame.Tiles import Tile, StaticTile
from Utils import *

class Level:
    def __init__(self, level_data, surface):

        # General Setup
        self.display_surface = surface
        self.world_shift = 0

        # Terrain Setup
        terrain_layout = import_csv_layout(level_data['Terrain'])
        self.terrain_sprites = self.create_tile_group(terrain_layout, 'Terrain')

    def create_tile_group(self, layout, group_type):
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
                        sprite_group.add(sprite)

        return sprite_group

    def run(self):
        # Run Entire Game
        self.terrain_sprites.draw(self.display_surface)
        self.terrain_sprites.update(self.world_shift)