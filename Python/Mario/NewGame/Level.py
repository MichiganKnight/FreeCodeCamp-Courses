from NewGame.Tiles import *
from Utils import *
from Enemy import Enemy
from Decoration import *

class Level:
    def __init__(self, level_data, surface):

        # General Setup
        self.display_surface = surface
        self.world_shift = -2

        # Player Setup
        player_layout = import_csv_layout(level_data['Player'])
        self.player = pygame.sprite.GroupSingle()
        self.goal = pygame.sprite.GroupSingle()
        self.player_setup(player_layout)

        # Terrain Setup
        terrain_layout = import_csv_layout(level_data['Terrain'])
        self.terrain_sprites = self.create_tile_group(terrain_layout, 'Terrain')

        # Grass Setup
        grass_layout = import_csv_layout(level_data['Grass'])
        self.grass_sprites = self.create_tile_group(grass_layout, 'Grass')

        # Crates Setup
        crate_layout = import_csv_layout(level_data['Crates'])
        self.crate_sprites = self.create_tile_group(crate_layout, 'Crates')

        # Coins Setup
        coin_layout = import_csv_layout(level_data['Coins'])
        self.coin_sprites = self.create_tile_group(coin_layout, 'Coins')

        # Foreground Palms
        fg_palm_layout = import_csv_layout(level_data['FG Palms'])
        self.fg_palm_sprites = self.create_tile_group(fg_palm_layout, 'FG Palms')

        # Background Palms
        bg_palm_layout = import_csv_layout(level_data['BG Palms'])
        self.bg_palm_sprites = self.create_tile_group(bg_palm_layout, 'BG Palms')

        # Enemies Setup
        enemy_layout = import_csv_layout(level_data['Enemies'])
        self.enemy_sprites = self.create_tile_group(enemy_layout, 'Enemies')

        # Constraints Setup
        constraint_layout = import_csv_layout(level_data['Constraints'])
        self.constraint_sprites = self.create_tile_group(constraint_layout, 'Constraints')

        # Decorations Setup
        self.sky = Sky(8)
        level_width = len(terrain_layout[0]) * tile_size
        self.water = Water(screen_height - 20, level_width)
        self.clouds = Clouds(400, level_width, 25)

    def player_setup(self, layout):
        for row_index, row in enumerate(layout):
            for col_index, val in enumerate(row):
                x = col_index * tile_size
                y = row_index * tile_size

                if val == '0':
                    print("Player Goes Here")
                elif val == '1':
                    hat_surface = pygame.image.load(join('NewGame', 'Assets', 'Character', 'hat.png')).convert_alpha()
                    sprite = StaticTile(tile_size, x, y, hat_surface)

                    self.goal.add(sprite)

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
                    elif group_type == 'Grass':
                        grass_tile_list = import_cut_graphics(join('NewGame', 'Assets', 'Decoration', 'Grass', 'grass.png'))
                        tile_surface = grass_tile_list[int(val)]

                        sprite = StaticTile(tile_size, x, y, tile_surface)
                    elif group_type == 'Crates':
                        sprite = Crate(tile_size, x, y)
                    elif group_type == 'Coins':
                        if val == '0':
                            sprite = Coin(tile_size, x, y, join('NewGame', 'Assets', 'Coins', 'Gold'))
                        elif val == '1':
                            sprite = Coin(tile_size, x, y, join('NewGame', 'Assets', 'Coins', 'Silver'))
                    elif group_type == 'FG Palms':
                        if val == '0':
                            sprite = Palm(tile_size, x, y, join('NewGame', 'Assets', 'Terrain', 'Palm_Small'), 38)
                        elif val == '1':
                            sprite = Palm(tile_size, x, y, join('NewGame', 'Assets', 'Terrain', 'Palm_Large'), 70)
                    elif group_type == 'BG Palms':
                        sprite = Palm(tile_size, x, y, join('NewGame', 'Assets', 'Terrain', 'Palm_BG'), 64)
                    elif group_type == 'Enemies':
                        sprite = Enemy(tile_size, x, y)
                    elif group_type == 'Constraints':
                        sprite = Tile(tile_size, x, y)

                    sprite_group.add(sprite)

        return sprite_group

    def enemy_collision_reverse(self):
        for enemy in self.enemy_sprites.sprites():
            if pygame.sprite.spritecollide(enemy, self.constraint_sprites, False):
                enemy.reverse()

    def run(self):
        # Run Entire Game

        # Sky
        self.sky.draw(self.display_surface)
        self.clouds.draw(self.display_surface, self.world_shift)

        # Background Palms
        self.bg_palm_sprites.update(self.world_shift)
        self.bg_palm_sprites.draw(self.display_surface)

        # Terrain
        self.terrain_sprites.update(self.world_shift)
        self.terrain_sprites.draw(self.display_surface)

        # Enemies
        self.enemy_sprites.update(self.world_shift)
        self.constraint_sprites.update(self.world_shift)
        self.enemy_collision_reverse()
        self.enemy_sprites.draw(self.display_surface)

        # Crates
        self.crate_sprites.update(self.world_shift)
        self.crate_sprites.draw(self.display_surface)

        # Grass
        self.grass_sprites.update(self.world_shift)
        self.grass_sprites.draw(self.display_surface)

        # Coins
        self.coin_sprites.update(self.world_shift)
        self.coin_sprites.draw(self.display_surface)

        # Foreground Palms
        self.fg_palm_sprites.update(self.world_shift)
        self.fg_palm_sprites.draw(self.display_surface)

        # Player Sprites
        self.goal.update(self.world_shift)
        self.goal.draw(self.display_surface)

        # Water
        self.water.draw(self.display_surface, self.world_shift)