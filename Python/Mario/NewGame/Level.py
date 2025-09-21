import pygame

from NewGame.Settings import tile_size
from NewGame.Tiles import Tile
from Utils import import_csv_layout

class Level:
    def __init__(self, level_data, surface):
        self.display_surface = surface

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
                        sprite = Tile(tile_size, x, y)
                        sprite_group.add(sprite)

        return sprite_group

    def run(self):
        # Run Entire Game
        self.terrain_sprites.draw(self.display_surface)
        self.terrain_sprites.update(-4)