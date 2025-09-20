import pygame

from Settings import tile_size

from Tiles import Tile
from Player import Player

class Level:
    def __init__(self, level_data, surface):

        # Level Setup
        self.display_surface = surface
        self.setup_level(level_data)

        self.world_shift = 0

    def setup_level(self, layout):
        self.tiles = pygame.sprite.Group()
        self.player = pygame.sprite.GroupSingle()

        for row_index, row in enumerate(layout):
            for col_index, cell in enumerate(row):
                #print(f'{row_index}, {col_index}:{cell}')
                if cell == 'X':
                    tile = Tile((col_index * tile_size, row_index * tile_size), tile_size)
                    self.tiles.add(tile)
                elif cell == 'P':
                    player_sprite = Player((col_index * tile_size, row_index * tile_size))
                    self.player.add(player_sprite)


    def run(self):
        # Level Tiles
        self.tiles.update(self.world_shift)
        self.tiles.draw(self.display_surface)

        # Player
        self.player.update()
        self.player.draw(self.display_surface)